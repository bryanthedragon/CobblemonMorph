
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.host.HostAdapterFactory;
import com.oracle.truffle.host.HostClassDesc;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostEngineException;
import com.oracle.truffle.host.HostExecuteNodeGen;
import com.oracle.truffle.host.HostFieldDesc;
import com.oracle.truffle.host.HostInteropErrors;
import com.oracle.truffle.host.HostMethodDesc;
import com.oracle.truffle.host.HostObject;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.graalvm.collections.EconomicSet;

final class HostInteropReflect {
    static final Object[] EMPTY = new Object[0];
    static final String STATIC_TO_CLASS = "class";
    static final String CLASS_TO_STATIC = "static";
    static final String ADAPTER_SUPER_MEMBER = "super";
    static final String ADAPTER_DELEGATE_MEMBER = "this";

    private HostInteropReflect() {
    }

    @CompilerDirectives.TruffleBoundary
    static Class<?> findInnerClass(Class<?> clazz, String name) {
        if (Modifier.isPublic(clazz.getModifiers())) {
            for (Class<?> t : clazz.getClasses()) {
                if (!HostInteropReflect.isStaticTypeOrInterface(t) || !t.getSimpleName().equals(name)) continue;
                return t;
            }
        }
        return null;
    }

    private static boolean isSignature(String name) {
        return name.length() > 0 && name.charAt(name.length() - 1) == ')' && name.indexOf(40) != -1;
    }

    private static boolean isJNIName(String name) {
        return name.contains("__");
    }

    @CompilerDirectives.TruffleBoundary
    static HostMethodDesc findMethod(HostContext context, Class<?> clazz, String name, boolean onlyStatic) {
        HostClassDesc classDesc = HostClassDesc.forClass(context, clazz);
        HostMethodDesc foundMethod = classDesc.lookupMethod(name, onlyStatic);
        if (foundMethod == null && HostInteropReflect.isSignature(name)) {
            foundMethod = classDesc.lookupMethodBySignature(name, onlyStatic);
        }
        if (foundMethod == null && HostInteropReflect.isJNIName(name)) {
            foundMethod = classDesc.lookupMethodByJNIName(name, onlyStatic);
        }
        return foundMethod;
    }

    @CompilerDirectives.TruffleBoundary
    static HostFieldDesc findField(HostContext context, Class<?> clazz, String name, boolean onlyStatic) {
        HostClassDesc classDesc = HostClassDesc.forClass(context, clazz);
        return classDesc.lookupField(name, onlyStatic);
    }

    private static Method functionalInterfaceMethod(Class<?> functionalInterface) {
        if (!functionalInterface.isInterface()) {
            return null;
        }
        Method found = null;
        for (Method m : functionalInterface.getMethods()) {
            if (!Modifier.isAbstract(m.getModifiers()) || HostClassDesc.isObjectMethodOverride(m)) continue;
            if (found != null) {
                return null;
            }
            found = m;
        }
        return found;
    }

    @CompilerDirectives.TruffleBoundary
    static boolean isFunctionalInterface(Class<?> type) {
        if (!type.isInterface() || type == TruffleObject.class) {
            return false;
        }
        if (type.getAnnotation(FunctionalInterface.class) != null) {
            return true;
        }
        return HostInteropReflect.functionalInterfaceMethod(type) != null;
    }

    @CompilerDirectives.TruffleBoundary
    static boolean isReadable(HostObject object, Class<?> clazz, String name, boolean onlyStatic, boolean isClass) {
        HostClassDesc classDesc = HostClassDesc.forClass(object.context, clazz);
        HostMethodDesc foundMethod = classDesc.lookupMethod(name, onlyStatic);
        if (foundMethod != null) {
            return true;
        }
        if (HostInteropReflect.isSignature(name) ? (foundMethod = classDesc.lookupMethodBySignature(name, onlyStatic)) != null : HostInteropReflect.isJNIName(name) && (foundMethod = classDesc.lookupMethodByJNIName(name, onlyStatic)) != null) {
            return true;
        }
        HostFieldDesc foundField = classDesc.lookupField(name, onlyStatic);
        if (foundField != null) {
            return true;
        }
        if (onlyStatic) {
            if (STATIC_TO_CLASS.equals(name)) {
                return true;
            }
            Class<?> innerClass = HostInteropReflect.findInnerClass(clazz, name);
            if (innerClass != null) {
                return true;
            }
        }
        return isClass && CLASS_TO_STATIC.equals(name);
    }

    @CompilerDirectives.TruffleBoundary
    static boolean isModifiable(HostObject object, Class<?> clazz, String name, boolean onlyStatic) {
        HostClassDesc classDesc = HostClassDesc.forClass(object.context, clazz);
        HostFieldDesc foundField = classDesc.lookupField(name, onlyStatic);
        return foundField != null && !foundField.isFinal();
    }

    @CompilerDirectives.TruffleBoundary
    static boolean isInvokable(HostObject object, Class<?> clazz, String name, boolean onlyStatic) {
        HostClassDesc classDesc = HostClassDesc.forClass(object.context, clazz);
        HostMethodDesc foundMethod = classDesc.lookupMethod(name, onlyStatic);
        if (foundMethod != null) {
            return true;
        }
        return HostInteropReflect.isSignature(name) ? (foundMethod = classDesc.lookupMethodBySignature(name, onlyStatic)) != null : HostInteropReflect.isJNIName(name) && (foundMethod = classDesc.lookupMethodByJNIName(name, onlyStatic)) != null;
    }

    @CompilerDirectives.TruffleBoundary
    static boolean isInternal(HostObject object, Class<?> clazz, String name, boolean onlyStatic) {
        HostClassDesc classDesc = HostClassDesc.forClass(object.context, clazz);
        HostMethodDesc foundMethod = classDesc.lookupMethod(name, onlyStatic);
        if (foundMethod != null) {
            return false;
        }
        return HostInteropReflect.isSignature(name) ? (foundMethod = classDesc.lookupMethodBySignature(name, onlyStatic)) != null : HostInteropReflect.isJNIName(name) && (foundMethod = classDesc.lookupMethodByJNIName(name, onlyStatic)) != null;
    }

    @CompilerDirectives.TruffleBoundary
    static Object newAdapterInstance(HostContext hostContext, Class<?> clazz, Object obj) throws IllegalArgumentException {
        if (TruffleOptions.AOT) {
            throw HostEngineException.unsupported(hostContext.access, "Unsupported target type.");
        }
        HostClassDesc classDesc = HostClassDesc.forClass(hostContext, clazz);
        HostAdapterFactory.AdapterResult adapter2 = classDesc.getAdapter(hostContext);
        if (!adapter2.isAutoConvertible()) {
            throw HostEngineException.illegalArgument(hostContext.access, "Cannot convert to " + clazz);
        }
        HostMethodDesc.SingleMethod adapterConstructor = adapter2.getValueConstructor();
        Object[] arguments = new Object[]{obj};
        try {
            return ((HostObject)HostExecuteNodeGen.getUncached().execute((HostMethodDesc)adapterConstructor, null, (Object[])arguments, (HostContext)hostContext)).obj;
        }
        catch (UnsupportedTypeException e) {
            throw HostInteropErrors.invalidExecuteArgumentType(hostContext, null, e.getSuppliedValues());
        }
        catch (ArityException e) {
            throw HostInteropErrors.invalidExecuteArity(hostContext, null, arguments, e.getExpectedMinArity(), e.getExpectedMaxArity(), e.getActualArity());
        }
    }

    private static boolean isStaticTypeOrInterface(Class<?> t) {
        return Modifier.isPublic(t.getModifiers()) && (t.isInterface() || t.isEnum() || Modifier.isStatic(t.getModifiers()));
    }

    static boolean isAbstractType(Class<?> targetType) {
        return targetType.isInterface() || !TruffleOptions.AOT && Modifier.isAbstract(targetType.getModifiers()) && !targetType.isArray() && !targetType.isPrimitive() && !Number.class.isAssignableFrom(targetType);
    }

    static boolean isExtensibleType(Class<?> targetType) {
        return targetType.isInterface() || !TruffleOptions.AOT && !Modifier.isFinal(targetType.getModifiers()) && !targetType.isArray() && !targetType.isPrimitive() && !Number.class.isAssignableFrom(targetType);
    }

    @CompilerDirectives.TruffleBoundary
    static String[] findUniquePublicMemberNames(HostContext context, Class<?> clazz, boolean isStatic, boolean isClass, boolean includeInternal) throws SecurityException {
        HostClassDesc classDesc = HostClassDesc.forClass(context, clazz);
        EconomicSet<String> names = EconomicSet.create();
        names.addAll(classDesc.getFieldNames(isStatic));
        names.addAll(classDesc.getMethodNames(isStatic, includeInternal));
        if (isStatic) {
            names.add(STATIC_TO_CLASS);
            if (!TruffleOptions.AOT && Modifier.isPublic(clazz.getModifiers())) {
                for (Class<?> t : clazz.getClasses()) {
                    if (!HostInteropReflect.isStaticTypeOrInterface(t)) continue;
                    names.add(t.getSimpleName());
                }
            }
        } else if (isClass) {
            names.add(CLASS_TO_STATIC);
        }
        return names.toArray(new String[names.size()]);
    }

    static <E extends Throwable> RuntimeException rethrow(Throwable ex) throws E {
        throw ex;
    }

    static String toNameAndSignature(Method m) {
        StringBuilder sb = new StringBuilder();
        sb.append(m.getName());
        sb.append('(');
        Class<?>[] arr = m.getParameterTypes();
        for (int i = 0; i < arr.length; ++i) {
            if (i != 0) {
                sb.append(',');
            }
            sb.append(arr[i].getTypeName());
        }
        sb.append(')');
        return sb.toString();
    }

    static String jniName(Method m) {
        StringBuilder sb = new StringBuilder();
        HostInteropReflect.noUnderscore(sb, m.getName()).append("__");
        HostInteropReflect.appendType(sb, m.getReturnType());
        Class<?>[] arr = m.getParameterTypes();
        for (int i = 0; i < arr.length; ++i) {
            HostInteropReflect.appendType(sb, arr[i]);
        }
        return sb.toString();
    }

    private static StringBuilder noUnderscore(StringBuilder sb, String name) {
        return sb.append(name.replace("_", "_1").replace('.', '_'));
    }

    private static void appendType(StringBuilder sb, Class<?> type) {
        if (type == Integer.TYPE) {
            sb.append('I');
            return;
        }
        if (type == Long.TYPE) {
            sb.append('J');
            return;
        }
        if (type == Double.TYPE) {
            sb.append('D');
            return;
        }
        if (type == Float.TYPE) {
            sb.append('F');
            return;
        }
        if (type == Byte.TYPE) {
            sb.append('B');
            return;
        }
        if (type == Boolean.TYPE) {
            sb.append('Z');
            return;
        }
        if (type == Short.TYPE) {
            sb.append('S');
            return;
        }
        if (type == Void.TYPE) {
            sb.append('V');
            return;
        }
        if (type == Character.TYPE) {
            sb.append('C');
            return;
        }
        if (type.isArray()) {
            sb.append("_3");
            HostInteropReflect.appendType(sb, type.getComponentType());
            return;
        }
        HostInteropReflect.noUnderscore(sb.append('L'), type.getName());
        sb.append("_2");
    }
}

