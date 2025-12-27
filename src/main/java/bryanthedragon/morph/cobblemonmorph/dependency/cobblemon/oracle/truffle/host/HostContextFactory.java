package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(HostContext.class)
final class HostContextFactory {
   @GeneratedBy(HostContext.ToGuestValueNode.class)
   static final class ToGuestValueNodeGen extends HostContext.ToGuestValueNode {
      private static final HostContextFactory.ToGuestValueNodeGen.Uncached UNCACHED = new HostContextFactory.ToGuestValueNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private HostContextFactory.ToGuestValueNodeGen.CachedData cached_cache;

      private ToGuestValueNodeGen() {
      }

      @ExplodeLoop
      @Override
      Object execute(HostContext arg0Value, Object arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg1Value == null) {
               return this.doNull(arg0Value, arg1Value);
            }

            if ((state_0 & 2) != 0 && arg1Value != null) {
               for (HostContextFactory.ToGuestValueNodeGen.CachedData s1_ = this.cached_cache; s1_ != null; s1_ = s1_.next_) {
                  if (arg1Value.getClass() == s1_.cachedReceiver_) {
                     return this.doCached(arg0Value, arg1Value, s1_.cachedReceiver_);
                  }
               }
            }

            if ((state_0 & 4) != 0) {
               return this.doUncached(arg0Value, arg1Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private Object executeAndSpecialize(HostContext arg0Value, Object arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg1Value == null) {
               int var15;
               this.state_0_ = var15 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doNull(arg0Value, arg1Value);
            } else {
               if (exclude == 0 && arg1Value != null) {
                  int count1_ = 0;
                  HostContextFactory.ToGuestValueNodeGen.CachedData s1_ = this.cached_cache;
                  if ((state_0 & 2) != 0) {
                     while (s1_ != null && arg1Value.getClass() != s1_.cachedReceiver_) {
                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null) {
                     Class<?> cachedReceiver__ = arg1Value.getClass();
                     if (arg1Value.getClass() == cachedReceiver__ && count1_ < 3) {
                        s1_ = new HostContextFactory.ToGuestValueNodeGen.CachedData(this.cached_cache);
                        s1_.cachedReceiver_ = cachedReceiver__;
                        VarHandle.storeStoreFence();
                        this.cached_cache = s1_;
                        this.state_0_ = state_0 |= 2;
                     }
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doCached(arg0Value, arg1Value, s1_.cachedReceiver_);
                  }
               }

               int var16;
               this.exclude_ = var16 = exclude | 1;
               this.cached_cache = null;
               state_0 &= -3;
               int var14;
               this.state_0_ = var14 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doUncached(arg0Value, arg1Value);
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
               HostContextFactory.ToGuestValueNodeGen.CachedData s1_ = this.cached_cache;
               if (s1_ == null || s1_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static HostContext.ToGuestValueNode create() {
         return new HostContextFactory.ToGuestValueNodeGen();
      }

      public static HostContext.ToGuestValueNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostContext.ToGuestValueNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         HostContextFactory.ToGuestValueNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         Class<?> cachedReceiver_;

         CachedData(HostContextFactory.ToGuestValueNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(HostContext.ToGuestValueNode.class)
      @DenyReplace
      private static final class Uncached extends HostContext.ToGuestValueNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         Object execute(HostContext arg0Value, Object arg1Value) {
            return arg1Value == null ? this.doNull(arg0Value, arg1Value) : this.doUncached(arg0Value, arg1Value);
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }
}
