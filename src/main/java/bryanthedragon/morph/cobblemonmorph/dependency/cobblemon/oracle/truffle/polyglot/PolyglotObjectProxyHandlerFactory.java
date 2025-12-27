package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.locks.Lock;

@GeneratedBy(PolyglotObjectProxyHandler.class)
final class PolyglotObjectProxyHandlerFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(PolyglotObjectProxyHandler.ProxyInvokeNode.class)
   static final class ProxyInvokeNodeGen extends PolyglotObjectProxyHandler.ProxyInvokeNode {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod0Data cachedMethod0_cache;
      @Node.Child
      private PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod1Data cachedMethod1_cache;

      private ProxyInvokeNodeGen() {
      }

      @ExplodeLoop
      @Override
      public Object execute(PolyglotLanguageContext arg0Value, Object arg1Value, Method arg2Value, Object[] arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod0Data s0_ = this.cachedMethod0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.receivers_.accepts(arg1Value) && s0_.cachedMethod_ == arg2Value) {
                     return this.doCachedMethod(
                        arg0Value,
                        arg1Value,
                        arg2Value,
                        arg3Value,
                        s0_.cachedMethod_,
                        s0_.name_,
                        s0_.returnClass_,
                        s0_.returnType_,
                        s0_.receivers_,
                        s0_.members_,
                        s0_.branchProfile_,
                        s0_.toHost_,
                        s0_.error_
                     );
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               for (PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod1Data s1_ = this.cachedMethod1_cache; s1_ != null; s1_ = s1_.next_) {
                  if (s1_.cachedMethod_ == arg2Value) {
                     return this.cachedMethod1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value, arg3Value);
                  }
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      @CompilerDirectives.TruffleBoundary
      private Object cachedMethod1Boundary(
         int state_0,
         PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod1Data s1_,
         PolyglotLanguageContext arg0Value,
         Object arg1Value,
         Method arg2Value,
         Object[] arg3Value
      ) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var10;
         try {
            InteropLibrary receivers__ = PolyglotObjectProxyHandlerFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
            var10 = this.doCachedMethod(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               s1_.cachedMethod_,
               s1_.name_,
               s1_.returnClass_,
               s1_.returnType_,
               receivers__,
               s1_.members_,
               s1_.branchProfile_,
               s1_.toHost_,
               s1_.error_
            );
         } finally {
            encapsulating_.set(prev_);
         }

         return var10;
      }

      private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Method arg2Value, Object[] arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod0Data s0_ = this.cachedMethod0_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (!s0_.receivers_.accepts(arg1Value) || s0_.cachedMethod_ != arg2Value)) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && count0_ < Integer.MAX_VALUE) {
                  s0_ = super.insert(new PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod0Data(this.cachedMethod0_cache));
                  s0_.cachedMethod_ = arg2Value;
                  s0_.name_ = arg2Value.getName();
                  s0_.returnClass_ = PolyglotObjectProxyHandler.ProxyInvokeNode.getMethodReturnType(arg2Value);
                  s0_.returnType_ = PolyglotObjectProxyHandler.ProxyInvokeNode.getMethodGenericReturnType(arg2Value);
                  s0_.receivers_ = s0_.insertAccessor(PolyglotObjectProxyHandlerFactory.INTEROP_LIBRARY_.create(arg1Value));
                  s0_.members_ = s0_.insertAccessor(PolyglotObjectProxyHandlerFactory.INTEROP_LIBRARY_.createDispatched(Integer.MAX_VALUE));
                  s0_.branchProfile_ = ConditionProfile.create();
                  s0_.toHost_ = s0_.insertAccessor(PolyglotToHostNodeGen.create());
                  s0_.error_ = BranchProfile.create();
                  VarHandle.storeStoreFence();
                  this.cachedMethod0_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doCachedMethod(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     s0_.cachedMethod_,
                     s0_.name_,
                     s0_.returnClass_,
                     s0_.returnType_,
                     s0_.receivers_,
                     s0_.members_,
                     s0_.branchProfile_,
                     s0_.toHost_,
                     s0_.error_
                  );
               }
            }

            InteropLibrary receivers__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               int count1_ = 0;
               PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod1Data s1_ = this.cachedMethod1_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null) {
                     if (s1_.cachedMethod_ == arg2Value) {
                        receivers__ = PolyglotObjectProxyHandlerFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                        break;
                     }

                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null && count1_ < Integer.MAX_VALUE) {
                  s1_ = super.insert(new PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod1Data(this.cachedMethod1_cache));
                  s1_.cachedMethod_ = arg2Value;
                  s1_.name_ = arg2Value.getName();
                  s1_.returnClass_ = PolyglotObjectProxyHandler.ProxyInvokeNode.getMethodReturnType(arg2Value);
                  s1_.returnType_ = PolyglotObjectProxyHandler.ProxyInvokeNode.getMethodGenericReturnType(arg2Value);
                  receivers__ = PolyglotObjectProxyHandlerFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  s1_.members_ = s1_.insertAccessor(PolyglotObjectProxyHandlerFactory.INTEROP_LIBRARY_.createDispatched(Integer.MAX_VALUE));
                  s1_.branchProfile_ = ConditionProfile.create();
                  s1_.toHost_ = s1_.insertAccessor(PolyglotToHostNodeGen.create());
                  s1_.error_ = BranchProfile.create();
                  VarHandle.storeStoreFence();
                  this.cachedMethod1_cache = s1_;
                  int var25;
                  this.exclude_ = var25 = exclude | 1;
                  this.cachedMethod0_cache = null;
                  state_0 &= -2;
                  int var24;
                  this.state_0_ = var24 = state_0 | 2;
               }

               if (s1_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doCachedMethod(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     s1_.cachedMethod_,
                     s1_.name_,
                     s1_.returnClass_,
                     s1_.returnType_,
                     receivers__,
                     s1_.members_,
                     s1_.branchProfile_,
                     s1_.toHost_,
                     s1_.error_
                  );
               }
            } finally {
               encapsulating_.set(prev_);
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod0Data s0_ = this.cachedMethod0_cache;
               PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod1Data s1_ = this.cachedMethod1_cache;
               if ((s0_ == null || s0_.next_ == null) && (s1_ == null || s1_.next_ == null)) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static PolyglotObjectProxyHandler.ProxyInvokeNode create() {
         return new PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen();
      }

      @GeneratedBy(PolyglotObjectProxyHandler.ProxyInvokeNode.class)
      private static final class CachedMethod0Data extends Node {
         @Node.Child
         PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod0Data next_;
         @CompilerDirectives.CompilationFinal
         Method cachedMethod_;
         @CompilerDirectives.CompilationFinal
         String name_;
         @CompilerDirectives.CompilationFinal
         Class<?> returnClass_;
         @CompilerDirectives.CompilationFinal
         Type returnType_;
         @Node.Child
         InteropLibrary receivers_;
         @Node.Child
         InteropLibrary members_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile branchProfile_;
         @Node.Child
         PolyglotToHostNode toHost_;
         @CompilerDirectives.CompilationFinal
         BranchProfile error_;

         CachedMethod0Data(PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod0Data next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(PolyglotObjectProxyHandler.ProxyInvokeNode.class)
      private static final class CachedMethod1Data extends Node {
         @Node.Child
         PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod1Data next_;
         @CompilerDirectives.CompilationFinal
         Method cachedMethod_;
         @CompilerDirectives.CompilationFinal
         String name_;
         @CompilerDirectives.CompilationFinal
         Class<?> returnClass_;
         @CompilerDirectives.CompilationFinal
         Type returnType_;
         @Node.Child
         InteropLibrary members_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile branchProfile_;
         @Node.Child
         PolyglotToHostNode toHost_;
         @CompilerDirectives.CompilationFinal
         BranchProfile error_;

         CachedMethod1Data(PolyglotObjectProxyHandlerFactory.ProxyInvokeNodeGen.CachedMethod1Data next_) {
            this.next_ = next_;
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
