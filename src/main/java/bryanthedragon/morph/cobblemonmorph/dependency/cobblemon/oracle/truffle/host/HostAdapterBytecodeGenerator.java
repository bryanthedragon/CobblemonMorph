package com.oracle.truffle.host;

import com.oracle.truffle.api.impl.asm.ClassWriter;
import com.oracle.truffle.api.impl.asm.FieldVisitor;
import com.oracle.truffle.api.impl.asm.Handle;
import com.oracle.truffle.api.impl.asm.Label;
import com.oracle.truffle.api.impl.asm.Type;
import com.oracle.truffle.api.impl.asm.commons.InstructionAdapter;
import java.lang.annotation.Annotation;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
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
   private static final String GET_CLASS_LOADER_DESCRIPTOR = Type.getMethodDescriptor(CLASS_LOADER_TYPE);
   private static final Handle BOOTSTRAP_HANDLE = new Handle(
      6,
      SERVICES_CLASS_TYPE_NAME,
      "bootstrap",
      MethodType.methodType(CallSite.class, Lookup.class, String.class, MethodType.class, int.class).toMethodDescriptorString(),
      false
   );
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
   private final Set<String> abstractMethodNames = new HashSet<>();
   private final String samName;
   private final Set<HostAdapterBytecodeGenerator.MethodInfo> finalMethods = new HashSet<>();
   private final Set<HostAdapterBytecodeGenerator.MethodInfo> methodInfos = new HashSet<>();
   private final boolean autoConvertibleFromFunction;
   private final boolean hasSuperMethods;
   private final boolean hasPublicDelegateField;
   private final ClassWriter cw;
   private static final Class<? extends Annotation> CALLER_SENSITIVE_ANNOTATION_CLASS = findCallerSensitiveAnnotationClass();

   HostAdapterBytecodeGenerator(
      final Class<?> superClass, final List<Class<?>> interfaces, final ClassLoader commonLoader, HostClassCache hostClassCache, final boolean classOverride
   ) {
      assert superClass != null && !superClass.isInterface();

      assert interfaces != null;

      this.superClass = superClass;
      this.interfaces = interfaces;
      this.commonLoader = commonLoader;
      this.hostClassCache = hostClassCache;
      this.classOverride = classOverride;
      this.superClassName = Type.getInternalName(superClass);
      this.generatedClassName = getGeneratedClassName(superClass, interfaces);
      this.cw = new ClassWriter(3) {
         @Override
         protected String getCommonSuperClass(final String type1, final String type2) {
            return HostAdapterBytecodeGenerator.this.getCommonSuperClass(type1, type2);
         }
      };
      this.cw.visit(52, 33, this.generatedClassName, null, this.superClassName, getInternalTypeNames(interfaces));
      this.generatePrivateField("delegate", POLYGLOT_VALUE_TYPE_DESCRIPTOR);
      this.hasPublicDelegateField = !classOverride;
      if (this.hasPublicDelegateField) {
         this.generatePublicDelegateField();
      }

      this.gatherMethods(superClass);
      this.gatherMethods(interfaces);
      if (this.abstractMethodNames.size() == 1) {
         this.samName = this.abstractMethodNames.iterator().next();
         this.generatePrivateField("isFunction", BOOLEAN_TYPE_DESCRIPTOR);
      } else {
         this.samName = null;
      }

      this.generateClassInit();
      this.autoConvertibleFromFunction = this.generateConstructors();
      this.generateMethods();
      this.hasSuperMethods = this.generateSuperMethods();
      this.cw.visitEnd();
   }

   private void generatePrivateField(final String name, final String fieldDesc) {
      this.cw.visitField(18 | (this.classOverride ? 8 : 0), name, fieldDesc, null, null).visitEnd();
   }

   private void generatePublicDelegateField() {
      FieldVisitor fw = this.cw.visitField(17, "this", POLYGLOT_VALUE_TYPE_DESCRIPTOR, null, null);
      fw.visitEnd();
   }

   private void loadField(final InstructionAdapter mv, final String name, final String desc) {
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

   private static String getGeneratedClassName(final Class<?> superType, final List<Class<?>> interfaces) {
      Class<?> namingType = superType == Object.class ? (interfaces.isEmpty() ? Object.class : interfaces.get(0)) : superType;
      String namingTypeName = namingType.getSimpleName();
      if (namingTypeName.isEmpty()) {
         namingTypeName = "Adapter";
      }

      StringBuilder buf = new StringBuilder();
      buf.append("com/oracle/truffle/host/adapters/").append(namingTypeName);
      Iterator<Class<?>> it = interfaces.iterator();
      if (superType == Object.class && it.hasNext()) {
         it.next();
      }

      while (it.hasNext()) {
         buf.append("$$").append(it.next().getSimpleName());
      }

      buf.append("$$Adapter");
      return buf.toString().substring(0, Math.min(255, buf.length()));
   }

   private static String[] getInternalTypeNames(final List<Class<?>> classes) {
      int interfaceCount = classes.size();
      String[] interfaceNames = new String[interfaceCount];

      for (int i = 0; i < interfaceCount; i++) {
         interfaceNames[i] = Type.getInternalName(classes.get(i));
      }

      return interfaceNames;
   }

   private void generateClassInit() {
      InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(8, "<clinit>", Type.getMethodDescriptor(Type.VOID_TYPE), null, null));
      if (this.classOverride) {
         mv.visitLdcInsn(this.getGeneratedClassAsType());
         mv.invokevirtual(CLASS_TYPE_NAME, "getClassLoader", GET_CLASS_LOADER_DESCRIPTOR, false);
         mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "getClassOverrides", GET_CLASS_OVERRIDES_METHOD_DESCRIPTOR, false);
         if (this.samName != null) {
            mv.dup();
            emitIsFunction(mv);
            mv.putstatic(this.generatedClassName, "isFunction", BOOLEAN_TYPE_DESCRIPTOR);
         }

         mv.putstatic(this.generatedClassName, "delegate", POLYGLOT_VALUE_TYPE_DESCRIPTOR);
      }

      endInitMethod(mv);
   }

   private Type getGeneratedClassAsType() {
      return Type.getType("L" + this.generatedClassName + ";");
   }

   private static void emitIsFunction(final InstructionAdapter mv) {
      mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "isFunction", Type.getMethodDescriptor(Type.getType(boolean.class), OBJECT_TYPE), false);
   }

   private boolean generateConstructors() {
      boolean gotCtor = false;
      boolean canBeAutoConverted = false;

      for (Constructor<?> ctor : this.superClass.getDeclaredConstructors()) {
         int modifier = ctor.getModifiers();
         if ((modifier & 5) != 0 && !isCallerSensitive(ctor)) {
            canBeAutoConverted |= this.generateConstructors(ctor);
            gotCtor = true;
         }
      }

      if (!gotCtor) {
         throw new IllegalArgumentException("No accessible constructor: " + this.superClass.getCanonicalName());
      } else {
         return canBeAutoConverted;
      }
   }

   private boolean generateConstructors(final Constructor<?> ctor) {
      if (this.classOverride) {
         this.generateDelegatingConstructor(ctor);
         return false;
      } else {
         boolean fromFunction = this.samName != null;
         this.generateOverridingConstructor(ctor, fromFunction);
         return !fromFunction ? false : ctor.getParameterCount() == 0;
      }
   }

   private void generateDelegatingConstructor(final Constructor<?> ctor) {
      Type originalCtorType = Type.getType(ctor);
      Type[] argTypes = originalCtorType.getArgumentTypes();
      String methodDescriptor = Type.getMethodDescriptor(originalCtorType.getReturnType(), argTypes);
      InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(1 | (ctor.isVarArgs() ? 128 : 0), "<init>", methodDescriptor, null, null));
      mv.visitCode();
      this.emitSuperConstructorCall(mv, originalCtorType.getDescriptor());
      endInitMethod(mv);
   }

   private void generateOverridingConstructor(final Constructor<?> ctor, final boolean fromFunction) {
      assert !this.classOverride;

      Type originalCtorType = Type.getType(ctor);
      Type[] originalArgTypes = originalCtorType.getArgumentTypes();
      int argLen = originalArgTypes.length;
      Type[] newArgTypes = new Type[argLen + 1];
      Type extraArgumentType = POLYGLOT_VALUE_TYPE;
      newArgTypes[argLen] = extraArgumentType;
      System.arraycopy(originalArgTypes, 0, newArgTypes, 0, argLen);
      String signature = Type.getMethodDescriptor(originalCtorType.getReturnType(), newArgTypes);
      InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(1, "<init>", signature, null, null));
      mv.visitCode();
      int offset = this.emitSuperConstructorCall(mv, originalCtorType.getDescriptor());
      if (fromFunction) {
         Label notAFunction = new Label();
         Label end = new Label();
         mv.visitVarInsn(25, offset);
         emitIsFunction(mv);
         mv.ifeq(notAFunction);
         mv.visitVarInsn(25, 0);
         mv.iconst(1);
         mv.putfield(this.generatedClassName, "isFunction", BOOLEAN_TYPE_DESCRIPTOR);
         mv.goTo(end);
         mv.visitLabel(notAFunction);
         mv.visitVarInsn(25, 0);
         mv.iconst(0);
         mv.putfield(this.generatedClassName, "isFunction", BOOLEAN_TYPE_DESCRIPTOR);
         mv.visitLabel(end);
      }

      mv.visitVarInsn(25, 0);
      mv.visitVarInsn(25, offset);
      mv.putfield(this.generatedClassName, "delegate", POLYGLOT_VALUE_TYPE_DESCRIPTOR);
      if (this.hasPublicDelegateField) {
         mv.visitVarInsn(25, 0);
         mv.visitVarInsn(25, offset);
         mv.putfield(this.generatedClassName, "this", POLYGLOT_VALUE_TYPE_DESCRIPTOR);
      }

      endInitMethod(mv);
   }

   private static void endInitMethod(final InstructionAdapter mv) {
      mv.visitInsn(177);
      endMethod(mv);
   }

   private static void endMethod(final InstructionAdapter mv) {
      mv.visitMaxs(0, 0);
      mv.visitEnd();
   }

   private void generateMethods() {
      for (HostAdapterBytecodeGenerator.MethodInfo mi : this.methodInfos) {
         this.generateMethod(mi);
      }
   }

   private void generateMethod(final HostAdapterBytecodeGenerator.MethodInfo mi) {
      Method method = mi.method;
      Class<?>[] declaredExceptions = method.getExceptionTypes();
      String[] exceptionNames = getExceptionNames(declaredExceptions);
      MethodType type = mi.type;
      String methodDesc = type.toMethodDescriptorString();
      String name = mi.getName();
      Type asmType = Type.getMethodType(methodDesc);
      Type[] asmArgTypes = asmType.getArgumentTypes();
      InstructionAdapter mv = new InstructionAdapter(this.cw.visitMethod(getAccessModifiers(method), name, methodDesc, null, exceptionNames));
      mv.visitCode();
      Type asmReturnType = Type.getType(type.returnType());
      int bootstrapFlags = method.isVarArgs() ? 4 : 0;
      Label defaultBehavior = new Label();
      Label hasMethod = new Label();
      List<HostAdapterBytecodeGenerator.TryBlock> tryBlocks = new ArrayList<>();
      this.loadField(mv, "delegate", POLYGLOT_VALUE_TYPE_DESCRIPTOR);
      mv.ifnull(defaultBehavior);
      if (this.samName != null) {
         this.loadField(mv, "isFunction", BOOLEAN_TYPE_DESCRIPTOR);
         if (name.equals(this.samName)) {
            Label notFunction = new Label();
            mv.ifeq(notFunction);
            this.loadField(mv, "delegate", POLYGLOT_VALUE_TYPE_DESCRIPTOR);
            loadParams(mv, asmArgTypes, 1);
            Label tryBlockStart = new Label();
            mv.visitLabel(tryBlockStart);
            mv.visitInvokeDynamicInsn(name, type.insertParameterTypes(0, Value.class).toMethodDescriptorString(), BOOTSTRAP_HANDLE, 2 | bootstrapFlags);
            Label tryBlockEnd = new Label();
            mv.visitLabel(tryBlockEnd);
            tryBlocks.add(new HostAdapterBytecodeGenerator.TryBlock(tryBlockStart, tryBlockEnd));
            mv.areturn(asmReturnType);
            mv.visitLabel(notFunction);
         } else {
            mv.ifne(defaultBehavior);
         }
      }

      this.loadField(mv, "delegate", POLYGLOT_VALUE_TYPE_DESCRIPTOR);
      if (name.equals("toString")) {
         Label hasNoToString = new Label();
         mv.dup();
         mv.visitLdcInsn(name);
         mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "hasOwnMethod", HAS_OWN_METHOD_DESCRIPTOR, false);
         mv.ifne(hasNoToString);
         mv.pop();
         mv.goTo(defaultBehavior);
         mv.visitLabel(hasNoToString);
      }

      mv.dup();
      mv.visitLdcInsn(name);
      mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "hasMethod", HAS_METHOD_DESCRIPTOR, false);
      mv.ifne(hasMethod);
      mv.pop();
      mv.visitLabel(defaultBehavior);
      if (Modifier.isAbstract(method.getModifiers())) {
         mv.visitLdcInsn(name);
         mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "unsupported", UNSUPPORTED_METHOD_DESCRIPTOR, false);
         mv.athrow();
      } else {
         this.emitSuperCall(mv, method.getDeclaringClass(), name, methodDesc);
         mv.areturn(Type.getMethodType(methodDesc).getReturnType());
      }

      mv.visitLabel(hasMethod);
      loadParams(mv, asmArgTypes, 1);
      Label tryBlockStart = new Label();
      mv.visitLabel(tryBlockStart);
      mv.visitInvokeDynamicInsn(name, type.insertParameterTypes(0, Value.class).toMethodDescriptorString(), BOOTSTRAP_HANDLE, 1 | bootstrapFlags);
      Label tryBlockEnd = new Label();
      mv.visitLabel(tryBlockEnd);
      tryBlocks.add(new HostAdapterBytecodeGenerator.TryBlock(tryBlockStart, tryBlockEnd));
      mv.areturn(asmReturnType);
      emitTryCatchBlocks(mv, declaredExceptions, tryBlocks);
      endMethod(mv);
   }

   private static void emitTryCatchBlocks(
      final InstructionAdapter mv, final Class<?>[] declaredExceptions, final List<HostAdapterBytecodeGenerator.TryBlock> tryBlocks
   ) {
      if (!isThrowableDeclared(declaredExceptions)) {
         Label rethrowHandler = new Label();
         mv.visitLabel(rethrowHandler);
         mv.athrow();
         Label throwableHandler = new Label();
         mv.visitLabel(throwableHandler);
         wrapThrowable(mv);
         mv.athrow();

         for (HostAdapterBytecodeGenerator.TryBlock tryBlock : tryBlocks) {
            mv.visitTryCatchBlock(tryBlock.start, tryBlock.end, rethrowHandler, RUNTIME_EXCEPTION_TYPE_NAME);
            mv.visitTryCatchBlock(tryBlock.start, tryBlock.end, rethrowHandler, ERROR_TYPE_NAME);

            for (Class<?> exception : declaredExceptions) {
               mv.visitTryCatchBlock(tryBlock.start, tryBlock.end, rethrowHandler, Type.getInternalName(exception));
            }

            mv.visitTryCatchBlock(tryBlock.start, tryBlock.end, throwableHandler, THROWABLE_TYPE_NAME);
         }
      }
   }

   private static void wrapThrowable(InstructionAdapter mv) {
      mv.invokestatic(SERVICES_CLASS_TYPE_NAME, "wrapThrowable", WRAP_THROWABLE_METHOD_DESCRIPTOR, false);
   }

   private static boolean isThrowableDeclared(final Class<?>[] exceptions) {
      for (Class<?> exception : exceptions) {
         if (exception == Throwable.class) {
            return true;
         }
      }

      return false;
   }

   private boolean generateSuperMethods() {
      boolean hasAccessibleNonAbstractSuperMethods = false;

      for (HostAdapterBytecodeGenerator.MethodInfo mi : this.methodInfos) {
         if (!Modifier.isAbstract(mi.method.getModifiers()) && this.hostClassCache.allowsAccess(mi.method)) {
            this.generateSuperMethod(mi);
            hasAccessibleNonAbstractSuperMethods = true;
         }
      }

      return hasAccessibleNonAbstractSuperMethods;
   }

   private void generateSuperMethod(HostAdapterBytecodeGenerator.MethodInfo mi) {
      Method method = mi.method;
      String methodDesc = mi.type.toMethodDescriptorString();
      String name = mi.getName();
      InstructionAdapter mv = new InstructionAdapter(
         this.cw.visitMethod(getAccessModifiers(method), "super$" + name, methodDesc, null, getExceptionNames(method.getExceptionTypes()))
      );
      mv.visitCode();
      this.emitSuperCall(mv, method.getDeclaringClass(), name, methodDesc);
      mv.areturn(Type.getMethodType(methodDesc).getReturnType());
      endMethod(mv);
   }

   private Class<?> findInvokespecialOwnerFor(final Class<?> owner) {
      assert Modifier.isInterface(owner.getModifiers()) : owner + " is not an interface";

      if (owner.isAssignableFrom(this.superClass)) {
         return this.superClass;
      } else {
         for (Class<?> iface : this.interfaces) {
            if (owner.isAssignableFrom(iface)) {
               return iface;
            }
         }

         throw new AssertionError("Cannot find the class/interface that extends " + owner);
      }
   }

   private int emitSuperConstructorCall(final InstructionAdapter mv, final String methodDesc) {
      return this.emitSuperCall(mv, null, "<init>", methodDesc, true);
   }

   private int emitSuperCall(final InstructionAdapter mv, final Class<?> owner, final String name, final String methodDesc) {
      return this.emitSuperCall(mv, owner, name, methodDesc, false);
   }

   private int emitSuperCall(final InstructionAdapter mv, final Class<?> owner, final String name, final String methodDesc, boolean constructor) {
      mv.visitVarInsn(25, 0);
      int nextParam = loadParams(mv, Type.getMethodType(methodDesc).getArgumentTypes(), 1);
      if (!constructor && Modifier.isInterface(owner.getModifiers())) {
         Class<?> superType = this.findInvokespecialOwnerFor(owner);
         mv.invokespecial(Type.getInternalName(superType), name, methodDesc, Modifier.isInterface(superType.getModifiers()));
      } else {
         mv.invokespecial(this.superClassName, name, methodDesc, false);
      }

      return nextParam;
   }

   private static int loadParams(final InstructionAdapter mv, final Type[] paramTypes, final int paramOffset) {
      int varOffset = paramOffset;

      for (Type t : paramTypes) {
         mv.load(varOffset, t);
         varOffset += t.getSize();
      }

      return varOffset;
   }

   private static String[] getExceptionNames(final Class<?>[] exceptions) {
      String[] exceptionNames = new String[exceptions.length];

      for (int i = 0; i < exceptions.length; i++) {
         exceptionNames[i] = Type.getInternalName(exceptions[i]);
      }

      return exceptionNames;
   }

   private static int getAccessModifiers(final Method method) {
      return 1 | (method.isVarArgs() ? 128 : 0);
   }

   private void gatherMethods(final Class<?> type) {
      if (Modifier.isPublic(type.getModifiers())) {
         Method[] typeMethods = type.isInterface() ? type.getMethods() : type.getDeclaredMethods();

         for (Method typeMethod : typeMethods) {
            String name = typeMethod.getName();
            if (!name.startsWith("super$")) {
               int mod = typeMethod.getModifiers();
               if (!Modifier.isStatic(mod) && (Modifier.isPublic(mod) || Modifier.isProtected(mod))) {
                  HostAdapterBytecodeGenerator.MethodInfo mi = new HostAdapterBytecodeGenerator.MethodInfo(typeMethod);
                  if (Modifier.isFinal(mod) || isExcluded(typeMethod) || isCallerSensitive(typeMethod)) {
                     this.finalMethods.add(mi);
                  } else if (!this.finalMethods.contains(mi) && this.methodInfos.add(mi) && Modifier.isAbstract(mod)) {
                     this.abstractMethodNames.add(mi.getName());
                  }
               }
            }
         }
      }

      if (!type.isInterface()) {
         Class<?> superType = type.getSuperclass();
         if (superType != null) {
            this.gatherMethods(superType);
         }

         for (Class<?> itf : type.getInterfaces()) {
            this.gatherMethods(itf);
         }
      }
   }

   private void gatherMethods(final List<Class<?>> classes) {
      for (Class<?> c : classes) {
         this.gatherMethods(c);
      }
   }

   private static boolean isExcluded(Method method) {
      if (method.getParameterCount() == 0) {
         String var1 = method.getName();
         switch (var1) {
            case "finalize":
               return true;
            case "clone":
               return true;
         }
      }

      return false;
   }

   private String getCommonSuperClass(final String type1, final String type2) {
      try {
         Class<?> c1 = Class.forName(type1.replace('/', '.'), false, this.commonLoader);
         Class<?> c2 = Class.forName(type2.replace('/', '.'), false, this.commonLoader);
         if (c1.isAssignableFrom(c2)) {
            return type1;
         } else if (c2.isAssignableFrom(c1)) {
            return type2;
         } else {
            return !c1.isInterface() && !c2.isInterface() ? assignableSuperClass(c1, c2).getName().replace('.', '/') : OBJECT_TYPE_NAME;
         }
      } catch (ClassNotFoundException var5) {
         throw new RuntimeException(var5);
      }
   }

   private static Class<?> assignableSuperClass(final Class<?> c1, final Class<?> c2) {
      Class<?> superClass = c1.getSuperclass();
      return superClass.isAssignableFrom(c2) ? superClass : assignableSuperClass(superClass, c2);
   }

   private static boolean isCallerSensitive(final Executable e) {
      return CALLER_SENSITIVE_ANNOTATION_CLASS != null && e.isAnnotationPresent(CALLER_SENSITIVE_ANNOTATION_CLASS);
   }

   private static Class<? extends Annotation> findCallerSensitiveAnnotationClass() {
      try {
         return Class.forName("sun.reflect.CallerSensitive").asSubclass(Annotation.class);
      } catch (ClassNotFoundException var2) {
         try {
            return Class.forName("jdk.internal.reflect.CallerSensitive").asSubclass(Annotation.class);
         } catch (ClassNotFoundException var1) {
            return null;
         }
      }
   }

   private static final class MethodInfo {
      private final Method method;
      private final MethodType type;

      private MethodInfo(final Method method) {
         this.method = method;
         this.type = MethodType.methodType(method.getReturnType(), method.getParameterTypes());
      }

      @Override
      public boolean equals(final Object obj) {
         return obj instanceof HostAdapterBytecodeGenerator.MethodInfo && this.equals((HostAdapterBytecodeGenerator.MethodInfo)obj);
      }

      private boolean equals(final HostAdapterBytecodeGenerator.MethodInfo other) {
         return this.getName().equals(other.getName()) && this.type.equals(other.type);
      }

      String getName() {
         return this.method.getName();
      }

      @Override
      public int hashCode() {
         return this.getName().hashCode() ^ this.type.hashCode();
      }

      @Override
      public String toString() {
         return this.method.toString();
      }
   }

   private static final class TryBlock {
      final Label start;
      final Label end;

      TryBlock(Label start, Label end) {
         this.start = start;
         this.end = end;
      }
   }
}
