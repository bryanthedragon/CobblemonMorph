
package com.oracle.truffle.host;

import com.oracle.truffle.api.impl.asm.ClassWriter;
import com.oracle.truffle.api.impl.asm.FieldVisitor;
import com.oracle.truffle.api.impl.asm.Handle;
import com.oracle.truffle.api.impl.asm.Label;
import com.oracle.truffle.api.impl.asm.Type;
import com.oracle.truffle.api.impl.asm.commons.InstructionAdapter;
import com.oracle.truffle.host.HostAdapterClassLoader;
import com.oracle.truffle.host.HostClassCache;
import java.lang.annotation.Annotation;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.graalvm.polyglot.Value;

final class HostAdapterBytecodeGenerator {
    private static final String INIT = "<init>";
    private static final String CLASS_INIT = "<clinit>";
    private static final Type OBJECT_TYPE = Type.getType(Object.class);
    private static final String OBJECT_TYPE_NAME = OBJECT_TYPE.getInternalName();
    private static final Type POLYGLOT_VALUE_TYPE = Type.getType(Value.class);
    private static final String POLYGLOT_VALUE_TYPE_DESCRIPTOR = POLYGLOT_VALUE_TYPE.getDescriptor();
    private static final String BOOLEAN_TYPE_DESCRIPTOR = Type.BOOLEAN_TYPE.getDescriptor();
    private static final Type STRING_TYPE = Type.getType(String.class);
    private static final Type CLASS_LOADER_TYPE = Type.getType(ClassLoader.class);
    private static final String HAS_METHOD_NAME = "hasMethod";
    private static final String HAS_METHOD_DESCRIPTOR = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, POLYGLOT_VALUE_TYPE, STRING_TYPE);
    private static final String HAS_OWN_METHOD_NAME = "hasOwnMethod";
    private static final String HAS_OWN_METHOD_DESCRIPTOR = Type.getMethodDescriptor(Type.BOOLEAN_TYPE, POLYGLOT_VALUE_TYPE, STRING_TYPE);
    private static final String GET_CLASS_OVERRIDES_METHOD_NAME = "getClassOverrides";
    private static final String GET_CLASS_OVERRIDES_METHOD_DESCRIPTOR = Type.getMethodDescriptor(POLYGLOT_VALUE_TYPE, CLASS_LOADER_TYPE);
    private static final Type RUNTIME_EXCEPTION_TYPE = Type.getType(RuntimeException.class);
    private static final Type THROWABLE_TYPE = Type.getType(Throwable.class);
    private static final Type UNSUPPORTED_OPERATION_TYPE = Type.getType(UnsupportedOperationException.class);
    private static final String UNSUPPORTED_METHOD_NAME = "unsupported";
    private static final String UNSUPPORTED_METHOD_DESCRIPTOR = Type.getMethodDescriptor(UNSUPPORTED_OPERATION_TYPE, STRING_TYPE);
    private static final String WRAP_THROWABLE_METHOD_NAME = "wrapThrowable";
    private static final String WRAP_THROWABLE_METHOD_DESCRIPTOR = Type.getMethodDescriptor(RUNTIME_EXCEPTION_TYPE, THROWABLE_TYPE);
    private static final String SERVICES_CLASS_TYPE_NAME = "com.oracle.truffle.host.adapters.HostAdapterServices".replace('.', '/');
    private static final String RUNTIME_EXCEPTION_TYPE_NAME = RUNTIME_EXCEPTION_TYPE.getInternalName();
    private static final String ERROR_TYPE_NAME = Type.getInternalName(Error.class);
    private static final String THROWABLE_TYPE_NAME = THROWABLE_TYPE.getInternalName();
    private static final String CLASS_TYPE_NAME = Type.getInternalName(Class.class);
    private static final String GET_CLASS_LOADER_NAME = "getClassLoader";
    private static final String GET_CLASS_LOADER_DESCRIPTOR = Type.getMethodDescriptor(CLASS_LOADER_TYPE, new Type[0]);
    private static final Handle BOOTSTRAP_HANDLE = new Handle(6, SERVICES_CLASS_TYPE_NAME, "bootstrap", MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, Integer.TYPE).toMethodDescriptorString(), false);
    static final int BOOTSTRAP_VALUE_INVOKE_MEMBER = 1;
    static final int BOOTSTRAP_VALUE_EXECUTE = 2;
    static final int BOOTSTRAP_VARARGS = 4;
    private static final String ADAPTER_PACKAGE_PREFIX = "com/oracle/truffle/host/adapters/";
    private static final String ADAPTER_CLASS_NAME_SUFFIX = "$$Adapter";
    private static final int MAX_GENERATED_TYPE_NAME_LENGTH = 255;
    private static final String DELEGATE_FIELD_NAME = "delegate";
    private static final String IS_FUNCTION_FIELD_NAME = "isFunction";
    private static final String PUBLIC_DELEGATE_FIELD_NAME = "this";
    static final String SUPER_PREFIX = "super$";
    private final Class<?> superClass;
    private final List<Class<?>> interfaces;
    private final ClassLoader commonLoader;
    private final HostClassCache hostClassCache;
    private final boolean classOverride;
    private final String superClassName;
    private final String generatedClassName;
    private final Set<String> abstractMethodNames = new HashSet<String>();
    private final String samName;
    private final Set<MethodInfo> finalMethods = new HashSet<MethodInfo>();
    private final Set<MethodInfo> methodInfos = new HashSet<MethodInfo>();
    private final boolean autoConvertibleFromFunction;
    private final boolean hasSuperMethods;
    private final boolean hasPublicDelegateField;
    private final ClassWriter cw;
    private static final Class<? extends Annotation> CALLER_SENSITIVE_ANNOTATION_CLASS = HostAdapterBytecodeGenerator.findCallerSensitiveAnnotationClass();

    HostAdapterBytecodeGenerator(Class<?> superClass, List<Class<?>> interfaces, ClassLoader commonLoader, HostClassCache hostClassCache, boolean classOverride) {
        assert (superClass != null && !superClass.isInterface());
        assert (interfaces != null);
        this.superClass = superClass;
        this.interfaces = interfaces;
        this.commonLoader = commonLoader;
        this.hostClassCache = hostClassCache;
        this.classOverride = classOverride;
        this.superClassName = Type.getInternalName(superClass);
        this.generatedClassName = HostAdapterBytecodeGenerator.getGeneratedClassName(superClass, interfaces);
        this.cw = new ClassWriter(3){

            @Override
            protected String getCommonSuperClass(String type1, String type2) {
                return HostAdapterBytecodeGenerator.this.getCommonSuperClass(type1, type2);
            }
        };
        this.cw.visit(52, 33, this.generatedClassName, null, this.superClassName, HostAdapterBytecodeGenerator.getInternalTypeNames(interfaces));
        this.generatePrivateField(DELEGATE_FIELD_NAME, POLYGLOT_VALUE_TYPE_DESCRIPTOR);
        boolean bl = this.hasPublicDelegateField = !classOverride;
        if (this.hasPublicDelegateField) {
            this.generatePublicDelegateField();
        }
        this.gatherMethods(superClass);
        this.gatherMethods(interfaces);
        if (this.abstractMethodNames.size() == 1) {
            this.samName = this.abstractMethodNames.iterator().next();
            this.generatePrivateField(IS_FUNCTION_FIELD_NAME, BOOLEAN_TYPE_DESCRIPTOR);
        } else {
            this.samName = null;
        }
        this.generateClassInit();
        this.autoConvertibleFromFunction = this.generateConstructors();
        this.generateMethods();
        this.hasSuperMethods = this.generateSuperMethods();
        this.cw.visitEnd();
    }

    private void generatePrivateField(String name, String fieldDesc) {
        this.cw.visitField(0x12 | (this.classOverride ? 8 : 0), name, fieldDesc, null, null).visitEnd();
    }

    private void generatePublicDelegateField() {
        FieldVisitor fw = this.cw.visitField(17, PUBLIC_DELEGATE_FIELD_NAME, POLYGLOT_VALUE_TYPE_DESCRIPTOR, null, null);
        fw.visitEnd();
    }

    private void loadField(InstructionAdapter mv, String name, String desc) {
        if (this.classOverride) {
            mv.getstatic(this.generatedClassName, name, desc);
        } else {
            mv.visitVarInsn(25, 0);
            mv.getfield(this.generatedClassName, name, desc);
        }
    }

    HostAdapterClassLoader createAdapterClassLoader() {
        return new HostAdapterClassLoader(this.generatedClassName, this.cw.toByteArray());
    }

    boolean isAutoConvertibleFromFunction() {
        return this.autoConvertibleFromFunction;
    }

    boolean hasSuperMethods() {
        return this.hasSuperMethods;
    }

    private static String getGeneratedClassName(Class<?> superType, List<Class<?>> interfaces) {
        Class<?> namingType = superType == Object.class ? (interfaces.isEmpty() ? Object.class : interfaces.get(0)) : superType;
        String namingTypeName = namingType.getSimpleName();
        if (namingTypeName.isEmpty()) {
            namingTypeName = "Adapter";
        }
        StringBuilder buf = new StringBuilder();
        buf.append(ADAPTER_PACKAGE_PREFIX).append(namingTypeName);
        Iterator<Class<?>> it = interfaces.iterator();
        if (superType == Object.class && it.hasNext()) {
            it.next();
        }
        while (it.hasNext()) {
            buf.append("$$").append(it.next().getSimpleName());
        }
        buf.append(ADAPTER_CLASS_NAME_SUFFIX);
        return buf.toString().substring(0, Math.min(255, buf.length()));
    }

    private static String[] getInternalTypeNames(List<Class<?>> classes) {
        int interfaceCount = classes.size();
        String[] interfaceNames = new String[interfaceCount];
        for (int i = 0; i < interfaceCount; ++i) {
            interfaceNames[i] = Type.getInternalName(classes.get(i));
        }
        return interfaceNames;
    }

    private void generateClassInit() {
        InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(8, CLASS_INIT, Type.getMethodDescriptor(Type.VOID_TYPE, new Type[0]), null, null));
        if (this.classOverride) {
            mv.visitLdcInsn(this.getGeneratedClassAsType());
            mv.invokevirtual(CLASS_TYPE_NAME, GET_CLASS_LOADER_NAME, GET_CLASS_LOADER_DESCRIPTOR, false);
            mv.invokestatic(SERVICES_CLASS_TYPE_NAME, GET_CLASS_OVERRIDES_METHOD_NAME, GET_CLASS_OVERRIDES_METHOD_DESCRIPTOR, false);
            if (this.samName != null) {
                mv.dup();
                HostAdapterBytecodeGenerator.emitIsFunction(mv);
                mv.putstatic(this.generatedClassName, IS_FUNCTION_FIELD_NAME, BOOLEAN_TYPE_DESCRIPTOR);
            }
            mv.putstatic(this.generatedClassName, DELEGATE_FIELD_NAME, POLYGLOT_VALUE_TYPE_DESCRIPTOR);
        }
        HostAdapterBytecodeGenerator.endInitMethod(mv);
    }

    private Type getGeneratedClassAsType() {
        return Type.getType("L" + this.generatedClassName + ";");
    }

    private static void emitIsFunction(InstructionAdapter mv) {
        mv.invokestatic(SERVICES_CLASS_TYPE_NAME, IS_FUNCTION_FIELD_NAME, Type.getMethodDescriptor(Type.getType(Boolean.TYPE), OBJECT_TYPE), false);
    }

    private boolean generateConstructors() {
        boolean gotCtor = false;
        boolean canBeAutoConverted = false;
        for (Constructor<?> ctor : this.superClass.getDeclaredConstructors()) {
            int modifier = ctor.getModifiers();
            if ((modifier & 5) == 0 || HostAdapterBytecodeGenerator.isCallerSensitive(ctor)) continue;
            canBeAutoConverted |= this.generateConstructors(ctor);
            gotCtor = true;
        }
        if (!gotCtor) {
            throw new IllegalArgumentException("No accessible constructor: " + this.superClass.getCanonicalName());
        }
        return canBeAutoConverted;
    }

    private boolean generateConstructors(Constructor<?> ctor) {
        if (this.classOverride) {
            this.generateDelegatingConstructor(ctor);
            return false;
        }
        boolean fromFunction = this.samName != null;
        this.generateOverridingConstructor(ctor, fromFunction);
        if (!fromFunction) {
            return false;
        }
        return ctor.getParameterCount() == 0;
    }

    private void generateDelegatingConstructor(Constructor<?> ctor) {
        Type originalCtorType = Type.getType(ctor);
        Type[] argTypes = originalCtorType.getArgumentTypes();
        String methodDescriptor = Type.getMethodDescriptor(originalCtorType.getReturnType(), argTypes);
        InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(1 | (ctor.isVarArgs() ? 128 : 0), INIT, methodDescriptor, null, null));
        mv.visitCode();
        this.emitSuperConstructorCall(mv, originalCtorType.getDescriptor());
        HostAdapterBytecodeGenerator.endInitMethod(mv);
    }

    private void generateOverridingConstructor(Constructor<?> ctor, boolean fromFunction) {
        Type extraArgumentType;
        assert (!this.classOverride);
        Type originalCtorType = Type.getType(ctor);
        Type[] originalArgTypes = originalCtorType.getArgumentTypes();
        int argLen = originalArgTypes.length;
        Type[] newArgTypes = new Type[argLen + 1];
        newArgTypes[argLen] = extraArgumentType = POLYGLOT_VALUE_TYPE;
        System.arraycopy(originalArgTypes, 0, newArgTypes, 0, argLen);
        String signature = Type.getMethodDescriptor(originalCtorType.getReturnType(), newArgTypes);
        InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(1, INIT, signature, null, null));
        mv.visitCode();
        int offset = this.emitSuperConstructorCall(mv, originalCtorType.getDescriptor());
        if (fromFunction) {
            Label notAFunction = new Label();
            Label end2 = new Label();
            mv.visitVarInsn(25, offset);
            HostAdapterBytecodeGenerator.emitIsFunction(mv);
            mv.ifeq(notAFunction);
            mv.visitVarInsn(25, 0);
            mv.iconst(1);
            mv.putfield(this.generatedClassName, IS_FUNCTION_FIELD_NAME, BOOLEAN_TYPE_DESCRIPTOR);
            mv.goTo(end2);
            mv.visitLabel(notAFunction);
            mv.visitVarInsn(25, 0);
            mv.iconst(0);
            mv.putfield(this.generatedClassName, IS_FUNCTION_FIELD_NAME, BOOLEAN_TYPE_DESCRIPTOR);
            mv.visitLabel(end2);
        }
        mv.visitVarInsn(25, 0);
        mv.visitVarInsn(25, offset);
        mv.putfield(this.generatedClassName, DELEGATE_FIELD_NAME, POLYGLOT_VALUE_TYPE_DESCRIPTOR);
        if (this.hasPublicDelegateField) {
            mv.visitVarInsn(25, 0);
            mv.visitVarInsn(25, offset);
            mv.putfield(this.generatedClassName, PUBLIC_DELEGATE_FIELD_NAME, POLYGLOT_VALUE_TYPE_DESCRIPTOR);
        }
        HostAdapterBytecodeGenerator.endInitMethod(mv);
    }

    private static void endInitMethod(InstructionAdapter mv) {
        mv.visitInsn(177);
        HostAdapterBytecodeGenerator.endMethod(mv);
    }

    private static void endMethod(InstructionAdapter mv) {
        mv.visitMaxs(0, 0);
        mv.visitEnd();
    }

    private void generateMethods() {
        for (MethodInfo mi : this.methodInfos) {
            this.generateMethod(mi);
        }
    }

    private void generateMethod(MethodInfo mi) {
        Method method = mi.method;
        Class<?>[] declaredExceptions = method.getExceptionTypes();
        String[] exceptionNames = HostAdapterBytecodeGenerator.getExceptionNames(declaredExceptions);
        MethodType type = mi.type;
        String methodDesc = type.toMethodDescriptorString();
        String name = mi.getName();
        Type asmType = Type.getMethodType(methodDesc);
        Type[] asmArgTypes = asmType.getArgumentTypes();
        InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(HostAdapterBytecodeGenerator.getAccessModifiers(method), name, methodDesc, null, exceptionNames));
        mv.visitCode();
        Type asmReturnType = Type.getType(type.returnType());
        int bootstrapFlags = method.isVarArgs() ? 4 : 0;
        Label defaultBehavior = new Label();
        Label hasMethod = new Label();
        ArrayList<TryBlock> tryBlocks = new ArrayList<TryBlock>();
        this.loadField(mv, DELEGATE_FIELD_NAME, POLYGLOT_VALUE_TYPE_DESCRIPTOR);
        mv.ifnull(defaultBehavior);
        if (this.samName != null) {
            this.loadField(mv, IS_FUNCTION_FIELD_NAME, BOOLEAN_TYPE_DESCRIPTOR);
            if (name.equals(this.samName)) {
                Label notFunction = new Label();
                mv.ifeq(notFunction);
                this.loadField(mv, DELEGATE_FIELD_NAME, POLYGLOT_VALUE_TYPE_DESCRIPTOR);
                HostAdapterBytecodeGenerator.loadParams(mv, asmArgTypes, 1);
                Label tryBlockStart = new Label();
                mv.visitLabel(tryBlockStart);
                mv.visitInvokeDynamicInsn(name, type.insertParameterTypes(0, Value.class).toMethodDescriptorString(), BOOTSTRAP_HANDLE, 2 | bootstrapFlags);
                Label tryBlockEnd = new Label();
                mv.visitLabel(tryBlockEnd);
                tryBlocks.add(new TryBlock(tryBlockStart, tryBlockEnd));
                mv.areturn(asmReturnType);
                mv.visitLabel(notFunction);
            } else {
                mv.ifne(defaultBehavior);
            }
        }
        this.loadField(mv, DELEGATE_FIELD_NAME, POLYGLOT_VALUE_TYPE_DESCRIPTOR);
        if (name.equals("toString")) {
            Label hasNoToString = new Label();
            mv.dup();
            mv.visitLdcInsn(name);
            mv.invokestatic(SERVICES_CLASS_TYPE_NAME, HAS_OWN_METHOD_NAME, HAS_OWN_METHOD_DESCRIPTOR, false);
            mv.ifne(hasNoToString);
            mv.pop();
            mv.goTo(defaultBehavior);
            mv.visitLabel(hasNoToString);
        }
        mv.dup();
        mv.visitLdcInsn(name);
        mv.invokestatic(SERVICES_CLASS_TYPE_NAME, HAS_METHOD_NAME, HAS_METHOD_DESCRIPTOR, false);
        mv.ifne(hasMethod);
        mv.pop();
        mv.visitLabel(defaultBehavior);
        if (Modifier.isAbstract(method.getModifiers())) {
            mv.visitLdcInsn(name);
            mv.invokestatic(SERVICES_CLASS_TYPE_NAME, UNSUPPORTED_METHOD_NAME, UNSUPPORTED_METHOD_DESCRIPTOR, false);
            mv.athrow();
        } else {
            this.emitSuperCall(mv, method.getDeclaringClass(), name, methodDesc);
            mv.areturn(Type.getMethodType(methodDesc).getReturnType());
        }
        mv.visitLabel(hasMethod);
        HostAdapterBytecodeGenerator.loadParams(mv, asmArgTypes, 1);
        Label tryBlockStart = new Label();
        mv.visitLabel(tryBlockStart);
        mv.visitInvokeDynamicInsn(name, type.insertParameterTypes(0, Value.class).toMethodDescriptorString(), BOOTSTRAP_HANDLE, 1 | bootstrapFlags);
        Label tryBlockEnd = new Label();
        mv.visitLabel(tryBlockEnd);
        tryBlocks.add(new TryBlock(tryBlockStart, tryBlockEnd));
        mv.areturn(asmReturnType);
        HostAdapterBytecodeGenerator.emitTryCatchBlocks(mv, declaredExceptions, tryBlocks);
        HostAdapterBytecodeGenerator.endMethod(mv);
    }

    private static void emitTryCatchBlocks(InstructionAdapter mv, Class<?>[] declaredExceptions, List<TryBlock> tryBlocks) {
        if (HostAdapterBytecodeGenerator.isThrowableDeclared(declaredExceptions)) {
            return;
        }
        Label rethrowHandler = new Label();
        mv.visitLabel(rethrowHandler);
        mv.athrow();
        Label throwableHandler = new Label();
        mv.visitLabel(throwableHandler);
        HostAdapterBytecodeGenerator.wrapThrowable(mv);
        mv.athrow();
        for (TryBlock tryBlock : tryBlocks) {
            mv.visitTryCatchBlock(tryBlock.start, tryBlock.end, rethrowHandler, RUNTIME_EXCEPTION_TYPE_NAME);
            mv.visitTryCatchBlock(tryBlock.start, tryBlock.end, rethrowHandler, ERROR_TYPE_NAME);
            for (Class<?> exception : declaredExceptions) {
                mv.visitTryCatchBlock(tryBlock.start, tryBlock.end, rethrowHandler, Type.getInternalName(exception));
            }
            mv.visitTryCatchBlock(tryBlock.start, tryBlock.end, throwableHandler, THROWABLE_TYPE_NAME);
        }
    }

    private static void wrapThrowable(InstructionAdapter mv) {
        mv.invokestatic(SERVICES_CLASS_TYPE_NAME, WRAP_THROWABLE_METHOD_NAME, WRAP_THROWABLE_METHOD_DESCRIPTOR, false);
    }

    private static boolean isThrowableDeclared(Class<?>[] exceptions) {
        for (Class<?> exception : exceptions) {
            if (exception != Throwable.class) continue;
            return true;
        }
        return false;
    }

    private boolean generateSuperMethods() {
        boolean hasAccessibleNonAbstractSuperMethods = false;
        for (MethodInfo mi : this.methodInfos) {
            if (Modifier.isAbstract(mi.method.getModifiers()) || !this.hostClassCache.allowsAccess(mi.method)) continue;
            this.generateSuperMethod(mi);
            hasAccessibleNonAbstractSuperMethods = true;
        }
        return hasAccessibleNonAbstractSuperMethods;
    }

    private void generateSuperMethod(MethodInfo mi) {
        Method method = mi.method;
        String methodDesc = mi.type.toMethodDescriptorString();
        String name = mi.getName();
        InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(HostAdapterBytecodeGenerator.getAccessModifiers(method), SUPER_PREFIX + name, methodDesc, null, HostAdapterBytecodeGenerator.getExceptionNames(method.getExceptionTypes())));
        mv.visitCode();
        this.emitSuperCall(mv, method.getDeclaringClass(), name, methodDesc);
        mv.areturn(Type.getMethodType(methodDesc).getReturnType());
        HostAdapterBytecodeGenerator.endMethod(mv);
    }

    private Class<?> findInvokespecialOwnerFor(Class<?> owner) {
        assert (Modifier.isInterface(owner.getModifiers())) : owner + " is not an interface";
        if (owner.isAssignableFrom(this.superClass)) {
            return this.superClass;
        }
        for (Class<?> iface : this.interfaces) {
            if (!owner.isAssignableFrom(iface)) continue;
            return iface;
        }
        throw new AssertionError((Object)("Cannot find the class/interface that extends " + owner));
    }

    private int emitSuperConstructorCall(InstructionAdapter mv, String methodDesc) {
        return this.emitSuperCall(mv, null, INIT, methodDesc, true);
    }

    private int emitSuperCall(InstructionAdapter mv, Class<?> owner, String name, String methodDesc) {
        return this.emitSuperCall(mv, owner, name, methodDesc, false);
    }

    private int emitSuperCall(InstructionAdapter mv, Class<?> owner, String name, String methodDesc, boolean constructor) {
        mv.visitVarInsn(25, 0);
        int nextParam = HostAdapterBytecodeGenerator.loadParams(mv, Type.getMethodType(methodDesc).getArgumentTypes(), 1);
        if (!constructor && Modifier.isInterface(owner.getModifiers())) {
            Class<?> superType = this.findInvokespecialOwnerFor(owner);
            mv.invokespecial(Type.getInternalName(superType), name, methodDesc, Modifier.isInterface(superType.getModifiers()));
        } else {
            mv.invokespecial(this.superClassName, name, methodDesc, false);
        }
        return nextParam;
    }

    private static int loadParams(InstructionAdapter mv, Type[] paramTypes, int paramOffset) {
        int varOffset = paramOffset;
        for (Type t : paramTypes) {
            mv.load(varOffset, t);
            varOffset += t.getSize();
        }
        return varOffset;
    }

    private static String[] getExceptionNames(Class<?>[] exceptions) {
        String[] exceptionNames = new String[exceptions.length];
        for (int i = 0; i < exceptions.length; ++i) {
            exceptionNames[i] = Type.getInternalName(exceptions[i]);
        }
        return exceptionNames;
    }

    private static int getAccessModifiers(Method method) {
        return 1 | (method.isVarArgs() ? 128 : 0);
    }

    private void gatherMethods(Class<?> type) {
        if (Modifier.isPublic(type.getModifiers())) {
            Method[] typeMethods = type.isInterface() ? type.getMethods() : type.getDeclaredMethods();
            for (GenericDeclaration genericDeclaration : typeMethods) {
                int mod;
                String name = ((Method)genericDeclaration).getName();
                if (name.startsWith(SUPER_PREFIX) || Modifier.isStatic(mod = ((Method)genericDeclaration).getModifiers()) || !Modifier.isPublic(mod) && !Modifier.isProtected(mod)) continue;
                MethodInfo mi = new MethodInfo((Method)genericDeclaration);
                if (Modifier.isFinal(mod) || HostAdapterBytecodeGenerator.isExcluded((Method)genericDeclaration) || HostAdapterBytecodeGenerator.isCallerSensitive((Executable)genericDeclaration)) {
                    this.finalMethods.add(mi);
                    continue;
                }
                if (this.finalMethods.contains(mi) || !this.methodInfos.add(mi) || !Modifier.isAbstract(mod)) continue;
                this.abstractMethodNames.add(mi.getName());
            }
        }
        if (!type.isInterface()) {
            Class<?> superType = type.getSuperclass();
            if (superType != null) {
                this.gatherMethods(superType);
            }
            for (GenericDeclaration genericDeclaration : type.getInterfaces()) {
                this.gatherMethods((Class<?>)genericDeclaration);
            }
        }
    }

    private void gatherMethods(List<Class<?>> classes) {
        for (Class<?> c : classes) {
            this.gatherMethods(c);
        }
    }

    private static boolean isExcluded(Method method) {
        if (method.getParameterCount() == 0) {
            switch (method.getName()) {
                case "finalize": {
                    return true;
                }
                case "clone": {
                    return true;
                }
            }
        }
        return false;
    }

    private String getCommonSuperClass(String type1, String type2) {
        try {
            Class<?> c1 = Class.forName(type1.replace('/', '.'), false, this.commonLoader);
            Class<?> c2 = Class.forName(type2.replace('/', '.'), false, this.commonLoader);
            if (c1.isAssignableFrom(c2)) {
                return type1;
            }
            if (c2.isAssignableFrom(c1)) {
                return type2;
            }
            if (c1.isInterface() || c2.isInterface()) {
                return OBJECT_TYPE_NAME;
            }
            return HostAdapterBytecodeGenerator.assignableSuperClass(c1, c2).getName().replace('.', '/');
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> assignableSuperClass(Class<?> c1, Class<?> c2) {
        Class<?> superClass = c1.getSuperclass();
        return superClass.isAssignableFrom(c2) ? superClass : HostAdapterBytecodeGenerator.assignableSuperClass(superClass, c2);
    }

    private static boolean isCallerSensitive(Executable e) {
        return CALLER_SENSITIVE_ANNOTATION_CLASS != null && e.isAnnotationPresent(CALLER_SENSITIVE_ANNOTATION_CLASS);
    }

    private static Class<? extends Annotation> findCallerSensitiveAnnotationClass() {
        try {
            return Class.forName("sun.reflect.CallerSensitive").asSubclass(Annotation.class);
        }
        catch (ClassNotFoundException classNotFoundException) {
            try {
                return Class.forName("jdk.internal.reflect.CallerSensitive").asSubclass(Annotation.class);
            }
            catch (ClassNotFoundException classNotFoundException2) {
                return null;
            }
        }
    }

    private static final class TryBlock {
        final Label start;
        final Label end;

        TryBlock(Label start2, Label end2) {
            this.start = start2;
            this.end = end2;
        }
    }

    private static final class MethodInfo {
        private final Method method;
        private final MethodType type;

        private MethodInfo(Method method) {
            this.method = method;
            this.type = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
        }

        public boolean equals(Object obj) {
            return obj instanceof MethodInfo && this.equals((MethodInfo)obj);
        }

        private boolean equals(MethodInfo other) {
            return this.getName().equals(other.getName()) && this.type.equals((Object)other.type);
        }

        String getName() {
            return this.method.getName();
        }

        public int hashCode() {
            return this.getName().hashCode() ^ this.type.hashCode();
        }

        public String toString() {
            return this.method.toString();
        }
    }
}

