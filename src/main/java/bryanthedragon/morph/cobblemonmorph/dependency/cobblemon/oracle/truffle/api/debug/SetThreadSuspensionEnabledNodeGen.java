package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(SetThreadSuspensionEnabledNode.class)
final class SetThreadSuspensionEnabledNodeGen extends SetThreadSuspensionEnabledNode {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private SetThreadSuspensionEnabledNodeGen.ExecuteCachedData executeCached_cache;

   private SetThreadSuspensionEnabledNodeGen() {
   }

   @ExplodeLoop
   @Override
   protected void execute(boolean arg0Value, Breakpoint.SessionList arg1Value, long arg2Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && arg1Value.next == null) {
            for (SetThreadSuspensionEnabledNodeGen.ExecuteCachedData s0_ = this.executeCached_cache; s0_ != null; s0_ = s0_.next_) {
               if (arg2Value == s0_.currentThreadId_) {
                  this.executeCached(arg0Value, arg1Value, arg2Value, s0_.currentThreadId_, s0_.threadSuspension_);
                  return;
               }
            }
         }

         if ((state_0 & 2) != 0) {
            this.executeGeneric(arg0Value, arg1Value, arg2Value);
            return;
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
   }

   private void executeAndSpecialize(boolean arg0Value, Breakpoint.SessionList arg1Value, long arg2Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude == 0 && arg1Value.next == null) {
            int count0_ = 0;
            SetThreadSuspensionEnabledNodeGen.ExecuteCachedData s0_ = this.executeCached_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null && arg2Value != s0_.currentThreadId_) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null) {
               long currentThreadId__ = SetThreadSuspensionEnabledNode.currentThreadId();
               if (arg2Value == currentThreadId__ && count0_ < 10) {
                  s0_ = new SetThreadSuspensionEnabledNodeGen.ExecuteCachedData(this.executeCached_cache);
                  s0_.currentThreadId_ = currentThreadId__;
                  s0_.threadSuspension_ = this.getThreadSuspension(arg1Value);
                  VarHandle.storeStoreFence();
                  this.executeCached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }
            }

            if (s0_ != null) {
               lock.unlock();
               hasLock = false;
               this.executeCached(arg0Value, arg1Value, arg2Value, s0_.currentThreadId_, s0_.threadSuspension_);
               return;
            }
         }

         int var18;
         this.exclude_ = var18 = exclude | 1;
         this.executeCached_cache = null;
         state_0 &= -2;
         int var17;
         this.state_0_ = var17 = state_0 | 2;
         lock.unlock();
         hasLock = false;
         this.executeGeneric(arg0Value, arg1Value, arg2Value);
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
            SetThreadSuspensionEnabledNodeGen.ExecuteCachedData s0_ = this.executeCached_cache;
            if (s0_ == null || s0_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   public static SetThreadSuspensionEnabledNode create() {
      return new SetThreadSuspensionEnabledNodeGen();
   }

   @GeneratedBy(SetThreadSuspensionEnabledNode.class)
   private static final class ExecuteCachedData {
      @CompilerDirectives.CompilationFinal
      SetThreadSuspensionEnabledNodeGen.ExecuteCachedData next_;
      @CompilerDirectives.CompilationFinal
      long currentThreadId_;
      @CompilerDirectives.CompilationFinal
      DebuggerSession.ThreadSuspension threadSuspension_;

      ExecuteCachedData(SetThreadSuspensionEnabledNodeGen.ExecuteCachedData next_) {
         this.next_ = next_;
      }
   }
}
