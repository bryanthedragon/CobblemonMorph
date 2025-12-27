package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.TruffleObject;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Objects;

final class PolyglotFunctionProxyHandler implements InvocationHandler, PolyglotWrapper {
   final Object functionObj;
   final PolyglotLanguageContext languageContext;
   private final Method functionMethod;
   private final CallTarget target;

   PolyglotFunctionProxyHandler(Object obj, Method functionMethod, PolyglotLanguageContext languageContext) {
      this.functionObj = obj;
      this.languageContext = languageContext;
      this.functionMethod = functionMethod;
      this.target = PolyglotFunctionProxyHandler.FunctionProxyNode.lookup(languageContext, obj.getClass(), functionMethod);
   }

   @CompilerDirectives.TruffleBoundary
   static <T> T create(Class<T> functionalType, Object function, PolyglotLanguageContext languageContext) {
      assert isFunctionalInterface(functionalType);

      Method functionalInterfaceMethod = functionalInterfaceMethod(functionalType);
      PolyglotFunctionProxyHandler handler = new PolyglotFunctionProxyHandler(function, functionalInterfaceMethod, languageContext);
      Object obj = Proxy.newProxyInstance(functionalType.getClassLoader(), new Class[]{functionalType}, handler);
      return functionalType.cast(obj);
   }

   static Method functionalInterfaceMethod(Class<?> functionalInterface) {
      if (!functionalInterface.isInterface()) {
         return null;
      } else {
         Method found = null;

         for (Method m : functionalInterface.getMethods()) {
            if (Modifier.isAbstract(m.getModifiers()) && !isObjectMethodOverride(m)) {
               if (found != null) {
                  return null;
               }

               found = m;
            }
         }

         return found;
      }
   }

   static boolean isObjectMethodOverride(Method m) {
      return m.getParameterCount() == 0 && (m.getName().equals("hashCode") || m.getName().equals("toString"))
         || m.getParameterCount() == 1 && m.getName().equals("equals") && m.getParameterTypes()[0] == Object.class;
   }

   @CompilerDirectives.TruffleBoundary
   static boolean isFunctionalInterface(Class<?> type) {
      if (!type.isInterface() || type == TruffleObject.class) {
         return false;
      } else {
         return type.getAnnotation(FunctionalInterface.class) != null ? true : functionalInterfaceMethod(type) != null;
      }
   }

   @Override
   public Object getGuestObject() {
      return this.functionObj;
   }

   @Override
   public PolyglotContextImpl getContext() {
      return this.languageContext.context;
   }

   @Override
   public PolyglotLanguageContext getLanguageContext() {
      return this.languageContext;
   }

   @Override
   public int hashCode() {
      return PolyglotWrapper.hashCode(this.languageContext, this.functionObj);
   }

   @Override
   public boolean equals(Object o) {
      return o instanceof PolyglotFunctionProxyHandler
         ? PolyglotWrapper.equals(this.languageContext, this.functionObj, ((PolyglotFunctionProxyHandler)o).functionObj)
         : false;
   }

   @Override
   public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
      CompilerAsserts.neverPartOfCompilation();
      Object[] resolvedArguments = arguments == null ? PolyglotObjectProxyHandler.EMPTY : arguments;
      return method.equals(this.functionMethod)
         ? this.target.call(this.languageContext, this.functionObj, this.spreadVarArgsArray(resolvedArguments))
         : invokeDefault(this, proxy, method, resolvedArguments);
   }

   private Object[] spreadVarArgsArray(Object[] arguments) {
      if (!this.functionMethod.isVarArgs()) {
         return arguments;
      } else if (arguments.length == 1) {
         return (Object[])arguments[0];
      } else {
         int allButOne = arguments.length - 1;
         Object[] last = (Object[])arguments[allButOne];
         Object[] merge = new Object[allButOne + last.length];
         System.arraycopy(arguments, 0, merge, 0, allButOne);
         System.arraycopy(last, 0, merge, allButOne, last.length);
         return merge;
      }
   }

   static Object invokeDefault(PolyglotWrapper host, Object proxy, Method method, Object[] arguments) throws Throwable {
      if (method.getDeclaringClass() == Object.class) {
         String var8 = method.getName();
         switch (var8) {
            case "equals":
               return PolyglotWrapper.equalsProxy(host, arguments[0]);
            case "hashCode":
               return PolyglotWrapper.hashCode(host.getLanguageContext(), host.getGuestObject());
            case "toString":
               return PolyglotWrapper.toString(host);
            default:
               throw new UnsupportedOperationException(method.getName());
         }
      } else if (TruffleOptions.AOT) {
         throw new UnsupportedOperationException("calling default method " + method.getName() + " is not yet supported on SubstrateVM");
      } else {
         Class<?> declaringClass = method.getDeclaringClass();

         assert declaringClass.isInterface() : declaringClass;

         MethodHandle mh;
         try {
            Truffle.class.getModule().addReads(declaringClass.getModule());
            mh = MethodHandles.lookup()
               .findSpecial(declaringClass, method.getName(), MethodType.methodType(method.getReturnType(), method.getParameterTypes()), declaringClass);
         } catch (IllegalAccessException var7) {
            throw new UnsupportedOperationException(method.getName(), var7);
         }

         return mh.bindTo(proxy).invokeWithArguments(arguments);
      }
   }

   @ImportStatic(PolyglotObjectProxyHandler.ProxyInvokeNode.class)
   abstract static class FunctionProxyNode extends HostToGuestRootNode {
      final Class<?> receiverClass;
      final Method method;

      FunctionProxyNode(PolyglotLanguageInstance languageInstance, Class<?> receiverType, Method method) {
         super(languageInstance);
         this.receiverClass = receiverType;
         this.method = method;
      }

      @Override
      protected Class<? extends TruffleObject> getReceiverType() {
         return (Class<? extends TruffleObject>)this.receiverClass;
      }

      @Override
      public final String getName() {
         return "FunctionalInterfaceProxy<" + this.receiverClass + ", " + this.method + ">";
      }

      @Specialization
      protected Object doCached(
         PolyglotLanguageContext languageContext,
         TruffleObject function,
         Object[] args,
         @Cached("getMethodReturnType(method)") Class<?> returnClass,
         @Cached("getMethodGenericReturnType(method)") Type returnType,
         @Cached PolyglotExecuteNode executeNode
      ) {
         return executeNode.execute(languageContext, function, args[2], returnClass, returnType, Object[].class, Object[].class);
      }

      @Override
      public int hashCode() {
         int result = 1;
         result = 31 * result + Objects.hashCode(this.receiverClass);
         return 31 * result + Objects.hashCode(this.method);
      }

      @Override
      public boolean equals(Object obj) {
         if (!(obj instanceof PolyglotFunctionProxyHandler.FunctionProxyNode)) {
            return false;
         } else {
            PolyglotFunctionProxyHandler.FunctionProxyNode other = (PolyglotFunctionProxyHandler.FunctionProxyNode)obj;
            return this.receiverClass == other.receiverClass && this.method.equals(other.method);
         }
      }

      static CallTarget lookup(PolyglotLanguageContext languageContext, Class<?> receiverClass, Method method) {
         PolyglotFunctionProxyHandler.FunctionProxyNode node = PolyglotFunctionProxyHandlerFactory.FunctionProxyNodeGen.create(
            languageContext.getLanguageInstance(), receiverClass, method
         );
         CallTarget target = lookupHostCodeCache(languageContext, node, CallTarget.class);
         if (target == null) {
            target = installHostCodeCache(languageContext, node, node.getCallTarget(), CallTarget.class);
         }

         return target;
      }
   }
}
