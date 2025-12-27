package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.locks.Lock;

@GeneratedBy(PolyglotFunctionProxyHandler.class)
final class PolyglotFunctionProxyHandlerFactory {
   @GeneratedBy(PolyglotFunctionProxyHandler.FunctionProxyNode.class)
   static final class FunctionProxyNodeGen extends PolyglotFunctionProxyHandler.FunctionProxyNode {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private PolyglotFunctionProxyHandlerFactory.FunctionProxyNodeGen.CachedData cached_cache;

      private FunctionProxyNodeGen(PolyglotLanguageInstance languageInstance, Class<?> receiverType, Method method) {
         super(languageInstance, receiverType, method);
      }

      @Override
      protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0 && arg1Value instanceof TruffleObject) {
            TruffleObject arg1Value_ = (TruffleObject)arg1Value;
            PolyglotFunctionProxyHandlerFactory.FunctionProxyNodeGen.CachedData s0_ = this.cached_cache;
            if (s0_ != null) {
               return this.doCached(arg0Value, arg1Value_, arg2Value, s0_.returnClass_, s0_.returnType_, s0_.executeNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var9;
         try {
            int state_0 = this.state_0_;
            if (!(arg1Value instanceof TruffleObject)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }

            TruffleObject arg1Value_ = (TruffleObject)arg1Value;
            PolyglotFunctionProxyHandlerFactory.FunctionProxyNodeGen.CachedData s0_ = super.insert(
               new PolyglotFunctionProxyHandlerFactory.FunctionProxyNodeGen.CachedData()
            );
            s0_.returnClass_ = PolyglotObjectProxyHandler.ProxyInvokeNode.getMethodReturnType(this.method);
            s0_.returnType_ = PolyglotObjectProxyHandler.ProxyInvokeNode.getMethodGenericReturnType(this.method);
            s0_.executeNode_ = s0_.insertAccessor(PolyglotExecuteNodeGen.create());
            VarHandle.storeStoreFence();
            this.cached_cache = s0_;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = this.doCached(arg0Value, arg1Value_, arg2Value, s0_.returnClass_, s0_.returnType_, s0_.executeNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var9;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      public static PolyglotFunctionProxyHandler.FunctionProxyNode create(PolyglotLanguageInstance languageInstance, Class<?> receiverType, Method method) {
         return new PolyglotFunctionProxyHandlerFactory.FunctionProxyNodeGen(languageInstance, receiverType, method);
      }

      @GeneratedBy(PolyglotFunctionProxyHandler.FunctionProxyNode.class)
      private static final class CachedData extends Node {
         @CompilerDirectives.CompilationFinal
         Class<?> returnClass_;
         @CompilerDirectives.CompilationFinal
         Type returnType_;
         @Node.Child
         PolyglotExecuteNode executeNode_;

         CachedData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }
}
