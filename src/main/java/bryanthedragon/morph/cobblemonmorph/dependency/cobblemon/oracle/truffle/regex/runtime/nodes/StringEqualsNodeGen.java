package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(StringEqualsNode.class)
public final class StringEqualsNodeGen extends StringEqualsNode {
   private static final StringEqualsNodeGen.Uncached UNCACHED = new StringEqualsNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private StringEqualsNodeGen.CacheIdentityData cacheIdentity_cache;

   private StringEqualsNodeGen() {
   }

   @ExplodeLoop
   @Override
   public boolean execute(String arg0Value, String arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            for (StringEqualsNodeGen.CacheIdentityData s0_ = this.cacheIdentity_cache; s0_ != null; s0_ = s0_.next_) {
               if (arg0Value == s0_.cachedA_ && s0_.cachedA_.equals(arg1Value)) {
                  return StringEqualsNode.cacheIdentity(arg0Value, arg1Value, s0_.cachedA_);
               }
            }
         }

         if ((state_0 & 2) != 0) {
            return StringEqualsNode.doEquals(arg0Value, arg1Value);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private boolean executeAndSpecialize(String arg0Value, String arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude == 0) {
            int count0_ = 0;
            StringEqualsNodeGen.CacheIdentityData s0_ = this.cacheIdentity_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null && (arg0Value != s0_.cachedA_ || !s0_.cachedA_.equals(arg1Value))) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null && arg0Value.equals(arg1Value) && count0_ < 4) {
               s0_ = new StringEqualsNodeGen.CacheIdentityData(this.cacheIdentity_cache);
               s0_.cachedA_ = arg0Value;
               VarHandle.storeStoreFence();
               this.cacheIdentity_cache = s0_;
               this.state_0_ = state_0 |= 1;
            }

            if (s0_ != null) {
               lock.unlock();
               hasLock = false;
               return StringEqualsNode.cacheIdentity(arg0Value, arg1Value, s0_.cachedA_);
            }
         }

         int var15;
         this.exclude_ = var15 = exclude | 1;
         this.cacheIdentity_cache = null;
         state_0 &= -2;
         int var14;
         this.state_0_ = var14 = state_0 | 2;
         lock.unlock();
         hasLock = false;
         return StringEqualsNode.doEquals(arg0Value, arg1Value);
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
            StringEqualsNodeGen.CacheIdentityData s0_ = this.cacheIdentity_cache;
            if (s0_ == null || s0_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   public static StringEqualsNode create() {
      return new StringEqualsNodeGen();
   }

   public static StringEqualsNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(StringEqualsNode.class)
   private static final class CacheIdentityData {
      @CompilerDirectives.CompilationFinal
      StringEqualsNodeGen.CacheIdentityData next_;
      @CompilerDirectives.CompilationFinal
      String cachedA_;

      CacheIdentityData(StringEqualsNodeGen.CacheIdentityData next_) {
         this.next_ = next_;
      }
   }

   @GeneratedBy(StringEqualsNode.class)
   @DenyReplace
   private static final class Uncached extends StringEqualsNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean execute(String arg0Value, String arg1Value) {
         return StringEqualsNode.doEquals(arg0Value, arg1Value);
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
