package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Type;
import java.util.concurrent.locks.Lock;

@GeneratedBy(PolyglotExecuteNode.class)
final class PolyglotExecuteNodeGen extends PolyglotExecuteNode {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private PolyglotExecuteNodeGen.Cached0Data cached0_cache;
   @Node.Child
   private PolyglotExecuteNodeGen.Cached1Data cached1_cache;

   private PolyglotExecuteNodeGen() {
   }

   @ExplodeLoop
   @Override
   protected Object executeImpl(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value, Class<?> arg3Value, Type arg4Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            for (PolyglotExecuteNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.interop_.accepts(arg1Value)) {
                  return this.doCached(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     arg4Value,
                     s0_.interop_,
                     s0_.toHost_,
                     s0_.executableCondition_,
                     s0_.instantiableCondition_,
                     s0_.unsupportedError_,
                     s0_.arityError_,
                     s0_.unsupportedArgumentError_
                  );
               }
            }
         }

         if ((state_0 & 2) != 0) {
            PolyglotExecuteNodeGen.Cached1Data s1_ = this.cached1_cache;
            if (s1_ != null) {
               return this.cached1Boundary(state_0, s1_, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
   }

   @CompilerDirectives.TruffleBoundary
   private Object cached1Boundary(
      int state_0,
      PolyglotExecuteNodeGen.Cached1Data s1_,
      PolyglotLanguageContext arg0Value,
      Object arg1Value,
      Object[] arg2Value,
      Class<?> arg3Value,
      Type arg4Value
   ) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Object var11;
      try {
         InteropLibrary interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
         var11 = this.doCached(
            arg0Value,
            arg1Value,
            arg2Value,
            arg3Value,
            arg4Value,
            interop__,
            s1_.toHost_,
            s1_.executableCondition_,
            s1_.instantiableCondition_,
            s1_.unsupportedError_,
            s1_.arityError_,
            s1_.unsupportedArgumentError_
         );
      } finally {
         encapsulating_.set(prev_);
      }

      return var11;
   }

   private Object executeAndSpecialize(PolyglotLanguageContext arg0Value, Object arg1Value, Object[] arg2Value, Class<?> arg3Value, Type arg4Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude == 0) {
            int count0_ = 0;
            PolyglotExecuteNodeGen.Cached0Data s0_ = this.cached0_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null && !s0_.interop_.accepts(arg1Value)) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null && count0_ < 5) {
               s0_ = super.insert(new PolyglotExecuteNodeGen.Cached0Data(this.cached0_cache));
               s0_.interop_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
               s0_.toHost_ = s0_.insertAccessor(PolyglotToHostNodeGen.create());
               s0_.executableCondition_ = ConditionProfile.create();
               s0_.instantiableCondition_ = ConditionProfile.create();
               s0_.unsupportedError_ = BranchProfile.create();
               s0_.arityError_ = BranchProfile.create();
               s0_.unsupportedArgumentError_ = BranchProfile.create();
               VarHandle.storeStoreFence();
               this.cached0_cache = s0_;
               this.state_0_ = state_0 |= 1;
            }

            if (s0_ != null) {
               lock.unlock();
               hasLock = false;
               return this.doCached(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  arg4Value,
                  s0_.interop_,
                  s0_.toHost_,
                  s0_.executableCondition_,
                  s0_.instantiableCondition_,
                  s0_.unsupportedError_,
                  s0_.arityError_,
                  s0_.unsupportedArgumentError_
               );
            }
         }

         InteropLibrary interop__ = null;
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         try {
            PolyglotExecuteNodeGen.Cached1Data s1_ = super.insert(new PolyglotExecuteNodeGen.Cached1Data());
            interop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
            s1_.toHost_ = s1_.insertAccessor(PolyglotToHostNodeGen.create());
            s1_.executableCondition_ = ConditionProfile.create();
            s1_.instantiableCondition_ = ConditionProfile.create();
            s1_.unsupportedError_ = BranchProfile.create();
            s1_.arityError_ = BranchProfile.create();
            s1_.unsupportedArgumentError_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.cached1_cache = s1_;
            int var25;
            this.exclude_ = var25 = exclude | 1;
            this.cached0_cache = null;
            state_0 &= -2;
            int var24;
            this.state_0_ = var24 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doCached(
               arg0Value,
               arg1Value,
               arg2Value,
               arg3Value,
               arg4Value,
               interop__,
               s1_.toHost_,
               s1_.executableCondition_,
               s1_.instantiableCondition_,
               s1_.unsupportedError_,
               s1_.arityError_,
               s1_.unsupportedArgumentError_
            );
         } finally {
            encapsulating_.set(prev_);
         }
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
            PolyglotExecuteNodeGen.Cached0Data s0_ = this.cached0_cache;
            if (s0_ == null || s0_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   public static PolyglotExecuteNode create() {
      return new PolyglotExecuteNodeGen();
   }

   @GeneratedBy(PolyglotExecuteNode.class)
   private static final class Cached0Data extends Node {
      @Node.Child
      PolyglotExecuteNodeGen.Cached0Data next_;
      @Node.Child
      InteropLibrary interop_;
      @Node.Child
      PolyglotToHostNode toHost_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile executableCondition_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile instantiableCondition_;
      @CompilerDirectives.CompilationFinal
      BranchProfile unsupportedError_;
      @CompilerDirectives.CompilationFinal
      BranchProfile arityError_;
      @CompilerDirectives.CompilationFinal
      BranchProfile unsupportedArgumentError_;

      Cached0Data(PolyglotExecuteNodeGen.Cached0Data next_) {
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

   @GeneratedBy(PolyglotExecuteNode.class)
   private static final class Cached1Data extends Node {
      @Node.Child
      PolyglotToHostNode toHost_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile executableCondition_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile instantiableCondition_;
      @CompilerDirectives.CompilationFinal
      BranchProfile unsupportedError_;
      @CompilerDirectives.CompilationFinal
      BranchProfile arityError_;
      @CompilerDirectives.CompilationFinal
      BranchProfile unsupportedArgumentError_;

      Cached1Data() {
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
