package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Objects;

final class PolyglotObjectProxyHandler implements InvocationHandler, PolyglotWrapper {
   static final Object[] EMPTY = new Object[0];
   final Object obj;
   final PolyglotLanguageContext languageContext;
   final CallTarget invoke;

   PolyglotObjectProxyHandler(Object obj, PolyglotLanguageContext languageContext, Class<?> interfaceClass) {
      this.obj = obj;
      this.languageContext = languageContext;
      this.invoke = PolyglotObjectProxyHandler.ObjectProxyNode.lookup(languageContext, obj.getClass(), interfaceClass);
   }

   @Override
   public Object getGuestObject() {
      return this.obj;
   }

   @Override
   public PolyglotLanguageContext getLanguageContext() {
      return this.languageContext;
   }

   @Override
   public PolyglotContextImpl getContext() {
      return this.languageContext.context;
   }

   @Override
   public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
      CompilerAsserts.neverPartOfCompilation();
      Object[] resolvedArguments = arguments == null ? EMPTY : arguments;

      try {
         return this.invoke.call(this.languageContext, this.obj, method, resolvedArguments);
      } catch (UnsupportedOperationException var8) {
         try {
            return PolyglotFunctionProxyHandler.invokeDefault(this, proxy, method, resolvedArguments);
         } catch (Exception var7) {
            var8.addSuppressed(var7);
            throw var8;
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   static Object newProxyInstance(Class<?> clazz, Object obj, PolyglotLanguageContext languageContext) throws IllegalArgumentException {
      return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new PolyglotObjectProxyHandler(obj, languageContext, clazz));
   }

   static final class ObjectProxyNode extends HostToGuestRootNode {
      final Class<?> receiverClass;
      final Class<?> interfaceType;
      @Node.Child
      private PolyglotObjectProxyHandler.ProxyInvokeNode proxyInvoke = PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.create();
      @Node.Child
      private PolyglotLanguageContext.ToGuestValuesNode toGuests = PolyglotLanguageContext.ToGuestValuesNode.create();

      ObjectProxyNode(PolyglotLanguageInstance languageInstance, Class<?> receiverType, Class<?> interfaceType) {
         super(languageInstance);
         this.receiverClass = receiverType;
         this.interfaceType = interfaceType;
      }

      @Override
      protected Class<? extends TruffleObject> getReceiverType() {
         return (Class<? extends TruffleObject>)this.receiverClass;
      }

      @Override
      public String getName() {
         return "InterfaceProxy<" + this.receiverClass + ">";
      }

      @Override
      protected Object executeImpl(PolyglotLanguageContext languageContext, Object receiver, Object[] args) {
         Method method = (Method)args[2];
         Object[] arguments = this.toGuests.apply(languageContext, (Object[])args[3]);
         return this.proxyInvoke.execute(languageContext, receiver, method, arguments);
      }

      @Override
      public int hashCode() {
         int result = 1;
         result = 31 * result + Objects.hashCode(this.receiverClass);
         return 31 * result + Objects.hashCode(this.interfaceType);
      }

      @Override
      public boolean equals(Object obj) {
         if (!(obj instanceof PolyglotObjectProxyHandler.ObjectProxyNode)) {
            return false;
         } else {
            PolyglotObjectProxyHandler.ObjectProxyNode other = (PolyglotObjectProxyHandler.ObjectProxyNode)obj;
            return this.receiverClass == other.receiverClass && this.interfaceType == other.interfaceType;
         }
      }

      static CallTarget lookup(PolyglotLanguageContext languageContext, Class<?> receiverClass, Class<?> interfaceClass) {
         PolyglotObjectProxyHandler.ObjectProxyNode node = new PolyglotObjectProxyHandler.ObjectProxyNode(
            languageContext.getLanguageInstance(), receiverClass, interfaceClass
         );
         CallTarget target = lookupHostCodeCache(languageContext, node, CallTarget.class);
         if (target == null) {
            target = installHostCodeCache(languageContext, node, node.getCallTarget(), CallTarget.class);
         }

         return target;
      }
   }

   abstract static class ProxyInvokeNode extends Node {
      protected static final int LIMIT = Integer.MAX_VALUE;
      @CompilerDirectives.CompilationFinal
      private boolean invokeFailed;

      public abstract Object execute(PolyglotLanguageContext languageContext, Object receiver, Method method, Object[] arguments);

      @Specialization(guards = "cachedMethod == method", limit = "LIMIT")
      protected Object doCachedMethod(
         PolyglotLanguageContext languageContext,
         Object receiver,
         Method method,
         Object[] arguments,
         @Cached("method") Method cachedMethod,
         @Cached("method.getName()") String name,
         @Cached("getMethodReturnType(method)") Class<?> returnClass,
         @Cached("getMethodGenericReturnType(method)") Type returnType,
         @CachedLibrary("receiver") InteropLibrary receivers,
         @CachedLibrary(limit = "LIMIT") InteropLibrary members,
         @Cached ConditionProfile branchProfile,
         @Cached PolyglotToHostNode toHost,
         @Cached BranchProfile error
      ) {
         Object result = this.invokeOrExecute(languageContext, receiver, arguments, name, receivers, members, branchProfile, error);
         return toHost.execute(languageContext, result, returnClass, returnType);
      }

      static Class<?> getMethodReturnType(Method method) {
         return method != null && method.getReturnType() != void.class ? method.getReturnType() : Object.class;
      }

      static Type getMethodGenericReturnType(Method method) {
         return (Type)(method != null && method.getReturnType() != void.class ? method.getGenericReturnType() : Object.class);
      }

      private Object invokeOrExecute(
         PolyglotLanguageContext polyglotContext,
         Object receiver,
         Object[] arguments,
         String member,
         InteropLibrary receivers,
         InteropLibrary members,
         ConditionProfile invokeProfile,
         BranchProfile error
      ) {
         try {
            boolean localInvokeFailed = this.invokeFailed;
            if (!localInvokeFailed) {
               try {
                  return receivers.invokeMember(receiver, member, arguments);
               } catch (UnknownIdentifierException | UnsupportedMessageException var11) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  localInvokeFailed = true;
                  this.invokeFailed = true;
               }
            }

            if (localInvokeFailed) {
               if (invokeProfile.profile(receivers.isMemberInvocable(receiver, member))) {
                  return receivers.invokeMember(receiver, member, arguments);
               }

               if (receivers.isMemberReadable(receiver, member)) {
                  Object readMember = receivers.readMember(receiver, member);
                  if (members.isExecutable(readMember)) {
                     return members.execute(readMember, arguments);
                  }

                  if (arguments.length == 0) {
                     return readMember;
                  }
               }
            }

            error.enter();
            throw PolyglotInteropErrors.invokeUnsupported(polyglotContext, receiver, member);
         } catch (UnknownIdentifierException var12) {
            error.enter();
            throw PolyglotInteropErrors.invokeUnsupported(polyglotContext, receiver, member);
         } catch (UnsupportedTypeException var13) {
            error.enter();
            throw PolyglotInteropErrors.invalidExecuteArgumentType(polyglotContext, receiver, var13.getSuppliedValues());
         } catch (ArityException var14) {
            error.enter();
            throw PolyglotInteropErrors.invalidExecuteArity(
               polyglotContext, receiver, arguments, var14.getExpectedMinArity(), var14.getExpectedMaxArity(), var14.getActualArity()
            );
         } catch (UnsupportedMessageException var15) {
            error.enter();
            throw PolyglotInteropErrors.invokeUnsupported(polyglotContext, receiver, member);
         }
      }
   }
}
