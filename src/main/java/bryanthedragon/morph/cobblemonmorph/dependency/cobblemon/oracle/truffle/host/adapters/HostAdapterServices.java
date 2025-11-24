
package com.oracle.truffle.host.adapters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.CallSite;
import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;
import org.graalvm.polyglot.Value;

final class HostAdapterServices {
    private static final MethodType VALUE_EXECUTE_METHOD_TYPE = MethodType.methodType(Value.class, Object[].class);
    private static final MethodType VALUE_EXECUTE_VOID_METHOD_TYPE = MethodType.methodType(Void.TYPE, Object[].class);
    private static final MethodType VALUE_INVOKE_MEMBER_METHOD_TYPE = MethodType.methodType(Value.class, String.class, Object[].class);
    private static final MethodType VALUE_AS_METHOD_TYPE = MethodType.methodType(Object.class, Class.class);
    private static final MethodType CONCAT_ARRAYS_METHOD_TYPE = MethodType.methodType(Object[].class, Object[].class, Object.class);
    static final int BOOTSTRAP_VALUE_INVOKE_MEMBER = 1;
    static final int BOOTSTRAP_VALUE_EXECUTE = 2;
    static final int BOOTSTRAP_VARARGS = 4;

    private HostAdapterServices() {
    }

    public static Value getClassOverrides(ClassLoader classLoader) {
        return Objects.requireNonNull((Value)((Supplier)((Object)classLoader)).get());
    }

    public static boolean hasMethod(Value obj, String name) {
        return obj.canInvokeMember(name);
    }

    public static boolean hasOwnMethod(Value obj, String name) {
        return obj.canInvokeMember(name);
    }

    public static boolean isFunction(Object obj) {
        return obj instanceof Value && ((Value)obj).canExecute();
    }

    public static UnsupportedOperationException unsupported(String methodName) {
        return new UnsupportedOperationException(methodName);
    }

    public static RuntimeException wrapThrowable(Throwable t) {
        return new RuntimeException(t);
    }

    private static MethodHandle createReturnValueConverter(MethodHandles.Lookup lookup, Class<?> returnType) throws NoSuchMethodException, IllegalAccessException {
        return MethodHandles.insertArguments(lookup.findVirtual(Value.class, "as", VALUE_AS_METHOD_TYPE), 1, returnType);
    }

    public static CallSite bootstrap(MethodHandles.Lookup lookup, String methodName, MethodType type, int flags) throws NoSuchMethodException, IllegalAccessException {
        boolean varargs;
        MethodHandle target;
        if ((flags & 1) != 0) {
            target = lookup.findVirtual(Value.class, "invokeMember", VALUE_INVOKE_MEMBER_METHOD_TYPE);
            target = MethodHandles.insertArguments(target, 1, methodName);
        } else {
            assert ((flags & 2) != 0);
            target = type.returnType() == Void.TYPE ? lookup.findVirtual(Value.class, "executeVoid", VALUE_EXECUTE_VOID_METHOD_TYPE) : lookup.findVirtual(Value.class, "execute", VALUE_EXECUTE_METHOD_TYPE);
        }
        boolean bl = varargs = (flags & 4) != 0;
        if (varargs) {
            Class<?> varargsParameter = type.parameterType(type.parameterCount() - 1);
            if (type.parameterCount() != 2 || varargsParameter != Object[].class) {
                MethodHandle fixedCollector = MethodHandles.identity(Object[].class).asCollector(Object[].class, type.parameterCount() - 2);
                MethodType fixedCollectorType = MethodType.methodType(Object[].class, Arrays.copyOfRange(type.parameterArray(), 1, type.parameterCount() - 1));
                fixedCollector = fixedCollector.asType(fixedCollectorType);
                MethodHandle concatArray = lookup.findStatic(HostAdapterServices.class, "concatArrays", CONCAT_ARRAYS_METHOD_TYPE);
                concatArray = concatArray.asType(CONCAT_ARRAYS_METHOD_TYPE.changeParameterType(1, varargsParameter));
                MethodHandle collector = MethodHandles.collectArguments(concatArray, 0, fixedCollector);
                target = MethodHandles.collectArguments(target, 1, collector);
            }
        } else {
            target = target.asCollector(Object[].class, type.parameterCount() - 1);
        }
        if (type.returnType() != Void.TYPE) {
            target = MethodHandles.filterReturnValue(target, HostAdapterServices.createReturnValueConverter(lookup, type.returnType()));
        }
        target = target.asType(type);
        return new ConstantCallSite(target);
    }

    public static Object[] concatArrays(Object[] fixed, Object va) {
        int fixedLen = fixed.length;
        int vaLen = Array.getLength(va);
        Object[] concat = Arrays.copyOf(fixed, fixedLen + vaLen);
        for (int i = 0; i < vaLen; ++i) {
            concat[fixedLen + i] = Array.get(va, i);
        }
        return concat;
    }

    @Target(value={ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD})
    @Retention(value=RetentionPolicy.RUNTIME)
    static @interface Export {
    }
}

