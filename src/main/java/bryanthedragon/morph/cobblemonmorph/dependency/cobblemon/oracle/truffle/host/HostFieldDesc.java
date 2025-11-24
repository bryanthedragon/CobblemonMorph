
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.host.HostInteropReflect;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

abstract class HostFieldDesc {
    private final boolean isFinal;
    private final Class<?> type;
    private final Type genericType;
    private final String name;

    private HostFieldDesc(Class<?> type, Type genericType, String name, boolean isFinal) {
        this.isFinal = isFinal;
        this.type = type;
        this.genericType = genericType;
        this.name = name;
    }

    public final boolean isFinal() {
        return this.isFinal;
    }

    public final Class<?> getType() {
        return this.type;
    }

    public final Type getGenericType() {
        return this.genericType;
    }

    public final String getName() {
        return this.name;
    }

    public abstract Object get(Object var1);

    public abstract void set(Object var1, Object var2) throws ClassCastException, NullPointerException, IllegalArgumentException;

    static HostFieldDesc unreflect(Field reflectionField) {
        assert (HostFieldDesc.isAccessible(reflectionField));
        if (TruffleOptions.AOT) {
            return new ReflectImpl(reflectionField);
        }
        return new MHImpl(reflectionField);
    }

    static boolean isAccessible(Field field) {
        return Modifier.isPublic(field.getModifiers()) && Modifier.isPublic(field.getDeclaringClass().getModifiers());
    }

    static final class SyntheticArrayLengthField
    extends HostFieldDesc {
        static final SyntheticArrayLengthField SINGLETON = new SyntheticArrayLengthField();

        private SyntheticArrayLengthField() {
            super(Integer.TYPE, Integer.TYPE, "length", true);
        }

        @Override
        public Object get(Object receiver) {
            try {
                return Array.getLength(receiver);
            }
            catch (IllegalArgumentException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }

        @Override
        public void set(Object receiver, Object value2) {
            CompilerDirectives.shouldNotReachHere();
        }

        public String toString() {
            return "Field[length]";
        }
    }

    private static final class MHImpl
    extends HostFieldDesc {
        private final Field field;
        @CompilerDirectives.CompilationFinal
        private MethodHandle getHandle;
        @CompilerDirectives.CompilationFinal
        private MethodHandle setHandle;

        MHImpl(Field field) {
            super(field.getType(), field.getGenericType(), field.getName(), Modifier.isFinal(field.getModifiers()));
            this.field = field;
        }

        @Override
        public Object get(Object receiver) {
            if (this.getHandle == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getHandle = this.makeGetMethodHandle();
            }
            try {
                return MHImpl.invokeGetHandle(this.getHandle, receiver);
            }
            catch (Throwable e) {
                throw HostInteropReflect.rethrow(e);
            }
        }

        @Override
        public void set(Object receiver, Object value2) {
            if (this.setHandle == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.setHandle = this.makeSetMethodHandle();
            }
            try {
                MHImpl.invokeSetHandle(this.setHandle, receiver, value2);
            }
            catch (Throwable e) {
                throw HostInteropReflect.rethrow(e);
            }
        }

        @CompilerDirectives.TruffleBoundary(allowInlining=true)
        private static Object invokeGetHandle(MethodHandle invokeHandle, Object receiver) throws Throwable {
            return invokeHandle.invokeExact(receiver);
        }

        @CompilerDirectives.TruffleBoundary(allowInlining=true)
        private static void invokeSetHandle(MethodHandle invokeHandle, Object receiver, Object value2) throws Throwable {
            invokeHandle.invokeExact(receiver, value2);
        }

        private MethodHandle makeGetMethodHandle() {
            CompilerAsserts.neverPartOfCompilation();
            try {
                MethodHandle getter = MethodHandles.publicLookup().unreflectGetter(this.field);
                if (Modifier.isStatic(this.field.getModifiers())) {
                    return MethodHandles.dropArguments(getter.asType(MethodType.methodType(Object.class)), 0, Object.class);
                }
                return getter.asType(MethodType.methodType(Object.class, Object.class));
            }
            catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }

        private MethodHandle makeSetMethodHandle() {
            CompilerAsserts.neverPartOfCompilation();
            try {
                MethodHandle setter = MethodHandles.publicLookup().unreflectSetter(this.field);
                if (Modifier.isStatic(this.field.getModifiers())) {
                    return MethodHandles.dropArguments(setter.asType(MethodType.methodType(Void.TYPE, Object.class)), 0, Object.class);
                }
                return setter.asType(MethodType.methodType(Void.TYPE, Object.class, Object.class));
            }
            catch (IllegalAccessException e) {
                throw new IllegalStateException(e);
            }
        }

        public String toString() {
            return "Field[" + this.field.toString() + "]";
        }
    }

    private static final class ReflectImpl
    extends HostFieldDesc {
        private final Field field;

        ReflectImpl(Field field) {
            super(field.getType(), field.getGenericType(), field.getName(), Modifier.isFinal(field.getModifiers()));
            this.field = field;
        }

        @Override
        public Object get(Object receiver) {
            try {
                return ReflectImpl.reflectGet(this.field, receiver);
            }
            catch (IllegalAccessException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }

        @Override
        public void set(Object receiver, Object value2) {
            try {
                ReflectImpl.reflectSet(this.field, receiver, value2);
            }
            catch (IllegalAccessException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }

        @CompilerDirectives.TruffleBoundary
        private static Object reflectGet(Field field, Object receiver) throws IllegalArgumentException, IllegalAccessException {
            return field.get(receiver);
        }

        @CompilerDirectives.TruffleBoundary
        private static void reflectSet(Field field, Object receiver, Object value2) throws IllegalArgumentException, IllegalAccessException {
            field.set(receiver, value2);
        }

        public String toString() {
            return "Field[" + this.field.toString() + "]";
        }
    }
}

