
package com.oracle.truffle.host;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.host.GuestToHostCodeCache;
import com.oracle.truffle.host.GuestToHostRootNode;
import com.oracle.truffle.host.HostContext;
import com.oracle.truffle.host.HostObject;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.StringJoiner;

abstract class HostMethodDesc {
    HostMethodDesc() {
    }

    abstract String getName();

    abstract String getDeclaringClassName();

    abstract SingleMethod[] getOverloads();

    boolean isInternal() {
        return false;
    }

    abstract boolean isMethod();

    abstract boolean isConstructor();

    static final class OverloadedMethod
    extends HostMethodDesc {
        private final SingleMethod[] overloads;

        OverloadedMethod(SingleMethod[] overloads) {
            this.overloads = overloads;
            assert (overloads.length >= 2);
        }

        @Override
        public SingleMethod[] getOverloads() {
            return this.overloads;
        }

        @Override
        public String getName() {
            return this.getOverloads()[0].getName();
        }

        @Override
        String getDeclaringClassName() {
            return this.getOverloads()[0].getDeclaringClassName();
        }

        @Override
        public boolean isMethod() {
            return this.getOverloads()[0].isMethod();
        }

        @Override
        public boolean isConstructor() {
            return this.getOverloads()[0].isConstructor();
        }

        public String toString() {
            StringJoiner sj = new StringJoiner(", ", "Method[", "]");
            for (SingleMethod overload : this.getOverloads()) {
                sj.add(overload.getReflectionMethod().toString());
            }
            return sj.toString();
        }

        @Override
        public boolean isInternal() {
            for (SingleMethod overload : this.overloads) {
                if (overload.isInternal()) continue;
                return false;
            }
            return true;
        }
    }

    static abstract class SingleMethod
    extends HostMethodDesc {
        static final int[] EMTPY_SCOPED_PARAMETERS = new int[0];
        static final int NO_SCOPE = -1;
        private final boolean varArgs;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final Class<?>[] parameterTypes;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final Type[] genericParameterTypes;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final int[] scopedStaticParameters;
        private final int scopedStaticParameterCount;
        private final boolean onlyVisibleFromJniName;
        private static final Class<?>[] UNSCOPED_TYPES = new Class[]{Boolean.class, Byte.class, Short.class, Character.class, Integer.class, Long.class, Float.class, Double.class, String.class};
        private static final Class<? extends Annotation> callerSensitiveClass = SingleMethod.getCallerSensitiveClass();

        protected SingleMethod(Executable executable, boolean parametersScoped, boolean onlyVisibleFromJniName) {
            this.varArgs = executable.isVarArgs();
            this.parameterTypes = executable.getParameterTypes();
            this.genericParameterTypes = executable.getGenericParameterTypes();
            int[] scopedParams = null;
            int count = 0;
            if (parametersScoped) {
                scopedParams = new int[this.parameterTypes.length];
                for (int i = 0; i < this.parameterTypes.length; ++i) {
                    scopedParams[i] = SingleMethod.isScoped(this.parameterTypes[i]) ? count++ : -1;
                }
            }
            this.scopedStaticParameterCount = count;
            if (count > 0) {
                assert (scopedParams != null);
                this.scopedStaticParameters = scopedParams;
            } else {
                this.scopedStaticParameters = EMTPY_SCOPED_PARAMETERS;
            }
            this.onlyVisibleFromJniName = onlyVisibleFromJniName;
        }

        private SingleMethod(boolean varArgs, Class<?>[] parameterTypes, Type[] genericParameterTypes, int[] scopedStaticParameters, int scopedStaticParameterCount) {
            this.varArgs = varArgs;
            this.parameterTypes = parameterTypes;
            this.genericParameterTypes = genericParameterTypes;
            this.scopedStaticParameters = scopedStaticParameters;
            this.scopedStaticParameterCount = scopedStaticParameterCount;
            this.onlyVisibleFromJniName = false;
        }

        private static boolean isScoped(Class<?> c) {
            if (c.isPrimitive()) {
                return false;
            }
            for (Class<?> unscopedType : UNSCOPED_TYPES) {
                if (!c.isAssignableFrom(unscopedType)) continue;
                return false;
            }
            return true;
        }

        public boolean isOnlyVisibleFromJniName() {
            return this.onlyVisibleFromJniName;
        }

        public abstract Executable getReflectionMethod();

        public final boolean isVarArgs() {
            return this.varArgs;
        }

        public final Class<?>[] getParameterTypes() {
            return this.parameterTypes;
        }

        public final int getParameterCount() {
            return this.parameterTypes.length;
        }

        public Type[] getGenericParameterTypes() {
            return this.genericParameterTypes;
        }

        public final boolean hasScopedParameters() {
            return this.scopedStaticParameterCount > 0;
        }

        public final int[] getScopedParameters() {
            return this.scopedStaticParameters;
        }

        public final int getScopedParameterCount() {
            return this.scopedStaticParameterCount;
        }

        @Override
        public String getName() {
            return this.getReflectionMethod().getName();
        }

        @Override
        String getDeclaringClassName() {
            return this.getReflectionMethod().getDeclaringClass().getName();
        }

        @Override
        public SingleMethod[] getOverloads() {
            return new SingleMethod[]{this};
        }

        public abstract Object invoke(Object var1, Object[] var2) throws Throwable;

        public abstract Object invokeGuestToHost(Object var1, Object[] var2, GuestToHostCodeCache var3, HostContext var4, Node var5);

        @Override
        public boolean isMethod() {
            return this.getReflectionMethod() instanceof Method;
        }

        @Override
        public boolean isConstructor() {
            return this.getReflectionMethod() instanceof Constructor;
        }

        static SingleMethod unreflect(Method reflectionMethod, boolean scoped, boolean onlyVisibleFromJniName) {
            assert (SingleMethod.isAccessible(reflectionMethod));
            if (TruffleOptions.AOT || SingleMethod.isCallerSensitive(reflectionMethod)) {
                return new MethodReflectImpl(reflectionMethod, scoped, onlyVisibleFromJniName);
            }
            return new MethodMHImpl(reflectionMethod, scoped, onlyVisibleFromJniName);
        }

        static SingleMethod unreflect(Constructor<?> reflectionConstructor, boolean scoped) {
            assert (SingleMethod.isAccessible(reflectionConstructor));
            if (TruffleOptions.AOT || SingleMethod.isCallerSensitive(reflectionConstructor)) {
                return new ConstructorReflectImpl(reflectionConstructor, scoped);
            }
            return new ConstructorMHImpl(reflectionConstructor, scoped);
        }

        static boolean isAccessible(Executable method) {
            return Modifier.isPublic(method.getModifiers()) && Modifier.isPublic(method.getDeclaringClass().getModifiers());
        }

        private static Class<? extends Annotation> getCallerSensitiveClass() {
            Class<?> tmpCallerSensitiveClass = null;
            try {
                tmpCallerSensitiveClass = Class.forName("jdk.internal.reflect.CallerSensitive");
            }
            catch (ClassNotFoundException e) {
                try {
                    tmpCallerSensitiveClass = Class.forName("sun.reflect.CallerSensitive");
                }
                catch (ClassNotFoundException classNotFoundException) {
                    // empty catch block
                }
            }
            return tmpCallerSensitiveClass;
        }

        static boolean isCallerSensitive(Executable method) {
            return callerSensitiveClass != null && method.isAnnotationPresent(callerSensitiveClass);
        }

        public String toString() {
            return "Method[" + this.getReflectionMethod().toString() + "]";
        }

        static final class SyntheticArrayCloneMethod
        extends SingleMethod {
            static final SyntheticArrayCloneMethod SINGLETON = new SyntheticArrayCloneMethod();

            private SyntheticArrayCloneMethod() {
                super(false, new Class[0], new Type[0], EMTPY_SCOPED_PARAMETERS, 0);
            }

            @Override
            public String getName() {
                return "clone";
            }

            @Override
            String getDeclaringClassName() {
                return null;
            }

            @Override
            public String toString() {
                return "Method[clone]";
            }

            @Override
            public Executable getReflectionMethod() {
                throw CompilerDirectives.shouldNotReachHere();
            }

            @Override
            public Object invoke(Object receiver, Object[] arguments) {
                assert (receiver != null && receiver.getClass().isArray() && arguments.length == 0);
                int length = Array.getLength(receiver);
                Object copy = Array.newInstance(receiver.getClass().getComponentType(), length);
                System.arraycopy(receiver, 0, copy, 0, length);
                return copy;
            }

            @Override
            public Object invokeGuestToHost(Object receiver, Object[] arguments, GuestToHostCodeCache cache, HostContext context, Node node) {
                return HostObject.forObject(this.invoke(receiver, arguments), context);
            }
        }

        private static final class ConstructorMHImpl
        extends MHBase {
            private final Constructor<?> reflectionConstructor;

            ConstructorMHImpl(Constructor<?> reflectionConstructor, boolean scoped) {
                super(reflectionConstructor, scoped, false);
                this.reflectionConstructor = reflectionConstructor;
            }

            @Override
            public Constructor<?> getReflectionMethod() {
                CompilerAsserts.neverPartOfCompilation();
                return this.reflectionConstructor;
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            protected MethodHandle makeMethodHandle() {
                CompilerAsserts.neverPartOfCompilation();
                try {
                    MethodHandle methodHandle = MethodHandles.publicLookup().unreflectConstructor(this.reflectionConstructor);
                    return ConstructorMHImpl.adaptSignature(methodHandle, true, this.getParameterCount());
                }
                catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        }

        private static final class MethodMHImpl
        extends MHBase {
            private final Method reflectionMethod;

            MethodMHImpl(Method reflectionMethod, boolean scoped, boolean onlyVisibleFromJniName) {
                super(reflectionMethod, scoped, onlyVisibleFromJniName);
                this.reflectionMethod = reflectionMethod;
            }

            @Override
            public Method getReflectionMethod() {
                CompilerAsserts.neverPartOfCompilation();
                return this.reflectionMethod;
            }

            @Override
            public boolean isInternal() {
                return this.getReflectionMethod().getDeclaringClass() == Object.class;
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            protected MethodHandle makeMethodHandle() {
                try {
                    Method m = this.reflectionMethod;
                    MethodHandle methodHandle = MethodHandles.publicLookup().unreflect(m);
                    return MethodMHImpl.adaptSignature(methodHandle, Modifier.isStatic(m.getModifiers()), m.getParameterCount());
                }
                catch (IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }
        }

        static abstract class MHBase
        extends SingleMethod {
            @CompilerDirectives.CompilationFinal
            private MethodHandle methodHandle;

            MHBase(Executable executable, boolean scoped, boolean onlyVisibleFromJniName) {
                super(executable, scoped, onlyVisibleFromJniName);
            }

            @Override
            public final Object invoke(Object receiver, Object[] arguments) throws Throwable {
                MethodHandle handle2 = this.methodHandle;
                if (handle2 == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.methodHandle = handle2 = this.makeMethodHandle();
                }
                return MHBase.invokeHandle(handle2, receiver, arguments);
            }

            @CompilerDirectives.TruffleBoundary(allowInlining=true)
            static Object invokeHandle(MethodHandle invokeHandle, Object receiver, Object[] arguments) throws Throwable {
                return invokeHandle.invokeExact(receiver, arguments);
            }

            protected abstract MethodHandle makeMethodHandle();

            @CompilerDirectives.TruffleBoundary
            private MethodHandle makeMethodHandleBoundary() {
                return this.makeMethodHandle();
            }

            protected static MethodHandle adaptSignature(MethodHandle originalHandle, boolean isStatic, int parameterCount) {
                MethodHandle adaptedHandle = originalHandle;
                adaptedHandle = adaptedHandle.asType(adaptedHandle.type().changeReturnType(Object.class));
                adaptedHandle = isStatic ? MethodHandles.dropArguments(adaptedHandle, 0, Object.class) : adaptedHandle.asType(adaptedHandle.type().changeParameterType(0, Object.class));
                adaptedHandle = adaptedHandle.asSpreader(Object[].class, parameterCount);
                return adaptedHandle;
            }

            @Override
            public Object invokeGuestToHost(Object receiver, Object[] arguments, GuestToHostCodeCache cache, HostContext hostContext, Node node) {
                MethodHandle handle2 = this.methodHandle;
                if (handle2 == null) {
                    if (CompilerDirectives.isPartialEvaluationConstant(this)) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                    }
                    this.methodHandle = handle2 = this.makeMethodHandleBoundary();
                }
                CallTarget target = cache.methodHandleHostInvoke;
                CompilerAsserts.partialEvaluationConstant(target);
                return GuestToHostRootNode.guestToHostCall(node, target, hostContext, receiver, handle2, arguments);
            }
        }

        private static final class ConstructorReflectImpl
        extends ReflectBase {
            private final Constructor<?> reflectionConstructor;

            ConstructorReflectImpl(Constructor<?> reflectionConstructor, boolean scoped) {
                super(reflectionConstructor, scoped, false);
                this.reflectionConstructor = reflectionConstructor;
            }

            @Override
            public Constructor<?> getReflectionMethod() {
                CompilerAsserts.neverPartOfCompilation();
                return this.reflectionConstructor;
            }

            @Override
            public Object invoke(Object receiver, Object[] arguments) throws Throwable {
                try {
                    return ConstructorReflectImpl.reflectNewInstance(this.reflectionConstructor, arguments);
                }
                catch (InvocationTargetException e) {
                    throw e.getCause();
                }
            }

            @CompilerDirectives.TruffleBoundary
            private static Object reflectNewInstance(Constructor<?> reflectionConstructor, Object[] arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                return reflectionConstructor.newInstance(arguments);
            }
        }

        private static final class MethodReflectImpl
        extends ReflectBase {
            private final Method reflectionMethod;

            MethodReflectImpl(Method reflectionMethod, boolean scoped, boolean onlyVisibleFromJniName) {
                super(reflectionMethod, scoped, onlyVisibleFromJniName);
                this.reflectionMethod = reflectionMethod;
            }

            @Override
            public Method getReflectionMethod() {
                CompilerAsserts.neverPartOfCompilation();
                return this.reflectionMethod;
            }

            @Override
            public Object invoke(Object receiver, Object[] arguments) throws Throwable {
                try {
                    return MethodReflectImpl.reflectInvoke(this.reflectionMethod, receiver, arguments);
                }
                catch (InvocationTargetException e) {
                    throw e.getCause();
                }
            }

            @CompilerDirectives.TruffleBoundary
            private static Object reflectInvoke(Method reflectionMethod, Object receiver, Object[] arguments) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                return reflectionMethod.invoke(receiver, arguments);
            }

            @Override
            public boolean isInternal() {
                return this.getReflectionMethod().getDeclaringClass() == Object.class;
            }
        }

        static abstract class ReflectBase
        extends SingleMethod {
            ReflectBase(Executable executable, boolean scoped, boolean onlyVisibleFromJniName) {
                super(executable, scoped, onlyVisibleFromJniName);
            }

            @Override
            public Object invokeGuestToHost(Object receiver, Object[] arguments, GuestToHostCodeCache cache, HostContext hostContext, Node node) {
                CallTarget target = cache.reflectionHostInvoke;
                return GuestToHostRootNode.guestToHostCall(node, target, hostContext, receiver, this, arguments);
            }
        }
    }
}

