package com.oracle.truffle.host;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.nodes.Node;
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
   abstract String getName();

   abstract String getDeclaringClassName();

   abstract HostMethodDesc.SingleMethod[] getOverloads();

   boolean isInternal() {
      return false;
   }

   abstract boolean isMethod();

   abstract boolean isConstructor();

   static final class OverloadedMethod extends HostMethodDesc {
      private final HostMethodDesc.SingleMethod[] overloads;

      OverloadedMethod(HostMethodDesc.SingleMethod[] overloads) {
         this.overloads = overloads;

         assert overloads.length >= 2;
      }

      @Override
      public HostMethodDesc.SingleMethod[] getOverloads() {
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

      @Override
      public String toString() {
         StringJoiner sj = new StringJoiner(", ", "Method[", "]");

         for (HostMethodDesc.SingleMethod overload : this.getOverloads()) {
            sj.add(overload.getReflectionMethod().toString());
         }

         return sj.toString();
      }

      @Override
      public boolean isInternal() {
         for (HostMethodDesc.SingleMethod overload : this.overloads) {
            if (!overload.isInternal()) {
               return false;
            }
         }

         return true;
      }
   }

   abstract static class SingleMethod extends HostMethodDesc {
      static final int[] EMTPY_SCOPED_PARAMETERS = new int[0];
      static final int NO_SCOPE = -1;
      private final boolean varArgs;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final Class<?>[] parameterTypes;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final Type[] genericParameterTypes;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final int[] scopedStaticParameters;
      private final int scopedStaticParameterCount;
      private final boolean onlyVisibleFromJniName;
      private static final Class<?>[] UNSCOPED_TYPES = new Class[]{
         Boolean.class, Byte.class, Short.class, Character.class, Integer.class, Long.class, Float.class, Double.class, String.class
      };
      private static final Class<? extends Annotation> callerSensitiveClass = getCallerSensitiveClass();

      protected SingleMethod(Executable executable, boolean parametersScoped, boolean onlyVisibleFromJniName) {
         this.varArgs = executable.isVarArgs();
         this.parameterTypes = executable.getParameterTypes();
         this.genericParameterTypes = executable.getGenericParameterTypes();
         int[] scopedParams = null;
         int count = 0;
         if (parametersScoped) {
            scopedParams = new int[this.parameterTypes.length];

            for (int i = 0; i < this.parameterTypes.length; i++) {
               if (isScoped(this.parameterTypes[i])) {
                  scopedParams[i] = count++;
               } else {
                  scopedParams[i] = -1;
               }
            }
         }

         this.scopedStaticParameterCount = count;
         if (count > 0) {
            assert scopedParams != null;

            this.scopedStaticParameters = scopedParams;
         } else {
            this.scopedStaticParameters = EMTPY_SCOPED_PARAMETERS;
         }

         this.onlyVisibleFromJniName = onlyVisibleFromJniName;
      }

      private SingleMethod(
         boolean varArgs, Class<?>[] parameterTypes, Type[] genericParameterTypes, int[] scopedStaticParameters, int scopedStaticParameterCount
      ) {
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
         } else {
            for (Class<?> unscopedType : UNSCOPED_TYPES) {
               if (c.isAssignableFrom(unscopedType)) {
                  return false;
               }
            }

            return true;
         }
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
      public HostMethodDesc.SingleMethod[] getOverloads() {
         return new HostMethodDesc.SingleMethod[]{this};
      }

      public abstract Object invoke(Object receiver, Object[] arguments) throws Throwable;

      public abstract Object invokeGuestToHost(Object receiver, Object[] arguments, GuestToHostCodeCache cache, HostContext context, Node node);

      @Override
      public boolean isMethod() {
         return this.getReflectionMethod() instanceof Method;
      }

      @Override
      public boolean isConstructor() {
         return this.getReflectionMethod() instanceof Constructor;
      }

      static HostMethodDesc.SingleMethod unreflect(Method reflectionMethod, boolean scoped, boolean onlyVisibleFromJniName) {
         assert isAccessible(reflectionMethod);

         return (HostMethodDesc.SingleMethod)(!TruffleOptions.AOT && !isCallerSensitive(reflectionMethod)
            ? new HostMethodDesc.SingleMethod.MethodMHImpl(reflectionMethod, scoped, onlyVisibleFromJniName)
            : new HostMethodDesc.SingleMethod.MethodReflectImpl(reflectionMethod, scoped, onlyVisibleFromJniName));
      }

      static HostMethodDesc.SingleMethod unreflect(Constructor<?> reflectionConstructor, boolean scoped) {
         assert isAccessible(reflectionConstructor);

         return (HostMethodDesc.SingleMethod)(!TruffleOptions.AOT && !isCallerSensitive(reflectionConstructor)
            ? new HostMethodDesc.SingleMethod.ConstructorMHImpl(reflectionConstructor, scoped)
            : new HostMethodDesc.SingleMethod.ConstructorReflectImpl(reflectionConstructor, scoped));
      }

      static boolean isAccessible(Executable method) {
         return Modifier.isPublic(method.getModifiers()) && Modifier.isPublic(method.getDeclaringClass().getModifiers());
      }

      private static Class<? extends Annotation> getCallerSensitiveClass() {
         Class<? extends Annotation> tmpCallerSensitiveClass = null;

         try {
            tmpCallerSensitiveClass = (Class<? extends Annotation>)Class.forName("jdk.internal.reflect.CallerSensitive");
         } catch (ClassNotFoundException var4) {
            try {
               tmpCallerSensitiveClass = (Class<? extends Annotation>)Class.forName("sun.reflect.CallerSensitive");
            } catch (ClassNotFoundException var3) {
            }
         }

         return tmpCallerSensitiveClass;
      }

      static boolean isCallerSensitive(Executable method) {
         return callerSensitiveClass != null && method.isAnnotationPresent(callerSensitiveClass);
      }

      @Override
      public String toString() {
         return "Method[" + this.getReflectionMethod().toString() + "]";
      }

      private static final class ConstructorMHImpl extends HostMethodDesc.SingleMethod.MHBase {
         private final Constructor<?> reflectionConstructor;

         ConstructorMHImpl(Constructor<?> reflectionConstructor, boolean scoped) {
            super(reflectionConstructor, scoped, false);
            this.reflectionConstructor = reflectionConstructor;
         }

         public Constructor<?> getReflectionMethod() {
            CompilerAsserts.neverPartOfCompilation();
            return this.reflectionConstructor;
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         protected MethodHandle makeMethodHandle() {
            CompilerAsserts.neverPartOfCompilation();

            try {
               MethodHandle methodHandle = MethodHandles.publicLookup().unreflectConstructor(this.reflectionConstructor);
               return adaptSignature(methodHandle, true, this.getParameterCount());
            } catch (IllegalAccessException var2) {
               throw new IllegalStateException(var2);
            }
         }
      }

      private static final class ConstructorReflectImpl extends HostMethodDesc.SingleMethod.ReflectBase {
         private final Constructor<?> reflectionConstructor;

         ConstructorReflectImpl(Constructor<?> reflectionConstructor, boolean scoped) {
            super(reflectionConstructor, scoped, false);
            this.reflectionConstructor = reflectionConstructor;
         }

         public Constructor<?> getReflectionMethod() {
            CompilerAsserts.neverPartOfCompilation();
            return this.reflectionConstructor;
         }

         @Override
         public Object invoke(Object receiver, Object[] arguments) throws Throwable {
            try {
               return reflectNewInstance(this.reflectionConstructor, arguments);
            } catch (InvocationTargetException var4) {
               throw var4.getCause();
            }
         }

         @CompilerDirectives.TruffleBoundary
         private static Object reflectNewInstance(Constructor<?> reflectionConstructor, Object[] arguments) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            return reflectionConstructor.newInstance(arguments);
         }
      }

      abstract static class MHBase extends HostMethodDesc.SingleMethod {
         @CompilerDirectives.CompilationFinal
         private MethodHandle methodHandle;

         MHBase(Executable executable, boolean scoped, boolean onlyVisibleFromJniName) {
            super(executable, scoped, onlyVisibleFromJniName);
         }

         @Override
         public final Object invoke(Object receiver, Object[] arguments) throws Throwable {
            MethodHandle handle = this.methodHandle;
            if (handle == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               handle = this.makeMethodHandle();
               this.methodHandle = handle;
            }

            return invokeHandle(handle, receiver, arguments);
         }

         @CompilerDirectives.TruffleBoundary(allowInlining = true)
         static Object invokeHandle(MethodHandle invokeHandle, Object receiver, Object[] arguments) throws Throwable {
            return (Object)invokeHandle.invokeExact((Object)receiver, (Object[])arguments);
         }

         protected abstract MethodHandle makeMethodHandle();

         @CompilerDirectives.TruffleBoundary
         private MethodHandle makeMethodHandleBoundary() {
            return this.makeMethodHandle();
         }

         protected static MethodHandle adaptSignature(MethodHandle originalHandle, boolean isStatic, int parameterCount) {
            MethodHandle adaptedHandle = originalHandle.asType(originalHandle.type().changeReturnType(Object.class));
            if (isStatic) {
               adaptedHandle = MethodHandles.dropArguments(adaptedHandle, 0, Object.class);
            } else {
               adaptedHandle = adaptedHandle.asType(adaptedHandle.type().changeParameterType(0, Object.class));
            }

            return adaptedHandle.asSpreader(Object[].class, parameterCount);
         }

         @Override
         public Object invokeGuestToHost(Object receiver, Object[] arguments, GuestToHostCodeCache cache, HostContext hostContext, Node node) {
            MethodHandle handle = this.methodHandle;
            if (handle == null) {
               if (CompilerDirectives.isPartialEvaluationConstant(this)) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
               }

               this.methodHandle = handle = this.makeMethodHandleBoundary();
            }

            CallTarget target = cache.methodHandleHostInvoke;
            CompilerAsserts.partialEvaluationConstant(target);
            return GuestToHostRootNode.guestToHostCall(node, target, hostContext, receiver, handle, arguments);
         }
      }

      private static final class MethodMHImpl extends HostMethodDesc.SingleMethod.MHBase {
         private final Method reflectionMethod;

         MethodMHImpl(Method reflectionMethod, boolean scoped, boolean onlyVisibleFromJniName) {
            super(reflectionMethod, scoped, onlyVisibleFromJniName);
            this.reflectionMethod = reflectionMethod;
         }

         public Method getReflectionMethod() {
            CompilerAsserts.neverPartOfCompilation();
            return this.reflectionMethod;
         }

         @Override
         public boolean isInternal() {
            return this.getReflectionMethod().getDeclaringClass() == Object.class;
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         protected MethodHandle makeMethodHandle() {
            try {
               Method m = this.reflectionMethod;
               MethodHandle methodHandle = MethodHandles.publicLookup().unreflect(m);
               return adaptSignature(methodHandle, Modifier.isStatic(m.getModifiers()), m.getParameterCount());
            } catch (IllegalAccessException var3) {
               throw new IllegalStateException(var3);
            }
         }
      }

      private static final class MethodReflectImpl extends HostMethodDesc.SingleMethod.ReflectBase {
         private final Method reflectionMethod;

         MethodReflectImpl(Method reflectionMethod, boolean scoped, boolean onlyVisibleFromJniName) {
            super(reflectionMethod, scoped, onlyVisibleFromJniName);
            this.reflectionMethod = reflectionMethod;
         }

         public Method getReflectionMethod() {
            CompilerAsserts.neverPartOfCompilation();
            return this.reflectionMethod;
         }

         @Override
         public Object invoke(Object receiver, Object[] arguments) throws Throwable {
            try {
               return reflectInvoke(this.reflectionMethod, receiver, arguments);
            } catch (InvocationTargetException var4) {
               throw var4.getCause();
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

      abstract static class ReflectBase extends HostMethodDesc.SingleMethod {
         ReflectBase(Executable executable, boolean scoped, boolean onlyVisibleFromJniName) {
            super(executable, scoped, onlyVisibleFromJniName);
         }

         @Override
         public Object invokeGuestToHost(Object receiver, Object[] arguments, GuestToHostCodeCache cache, HostContext hostContext, Node node) {
            CallTarget target = cache.reflectionHostInvoke;
            return GuestToHostRootNode.guestToHostCall(node, target, hostContext, receiver, this, arguments);
         }
      }

      static final class SyntheticArrayCloneMethod extends HostMethodDesc.SingleMethod {
         static final HostMethodDesc.SingleMethod.SyntheticArrayCloneMethod SINGLETON = new HostMethodDesc.SingleMethod.SyntheticArrayCloneMethod();

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
            assert receiver != null && receiver.getClass().isArray() && arguments.length == 0;

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
   }
}
