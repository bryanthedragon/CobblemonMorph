package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBase;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ArrayElementInfoNode.class)
public final class ArrayElementInfoNodeGen extends ArrayElementInfoNode implements Introspection.Provider {
   private static final ArrayElementInfoNodeGen.Uncached UNCACHED = new ArrayElementInfoNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private ArrayElementInfoNodeGen.CachedData cached_cache;

   private ArrayElementInfoNodeGen() {
   }

   @ExplodeLoop
   @Override
   public TriState execute(JSArrayBase arg0Value, long arg1Value, int arg2Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            for (ArrayElementInfoNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.arrayType_.isInstance(arg0Value.getArrayType())) {
                  return ArrayElementInfoNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrayType_);
               }
            }
         }

         if ((state_0 & 2) != 0) {
            return ArrayElementInfoNode.doUncached(arg0Value, arg1Value, arg2Value);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
   }

   private TriState executeAndSpecialize(JSArrayBase arg0Value, long arg1Value, int arg2Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude == 0) {
            int count0_ = 0;
            ArrayElementInfoNodeGen.CachedData s0_ = this.cached_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null && !s0_.arrayType_.isInstance(arg0Value.getArrayType())) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null) {
               ScriptArray arrayType__ = arg0Value.getArrayType();
               if (arrayType__.isInstance(arg0Value.getArrayType()) && count0_ < 5) {
                  s0_ = new ArrayElementInfoNodeGen.CachedData(this.cached_cache);
                  s0_.arrayType_ = arrayType__;
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }
            }

            if (s0_ != null) {
               lock.unlock();
               hasLock = false;
               return ArrayElementInfoNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrayType_);
            }
         }

         int var17;
         this.exclude_ = var17 = exclude | 1;
         this.cached_cache = null;
         state_0 &= -2;
         int var16;
         this.state_0_ = var16 = state_0 | 2;
         lock.unlock();
         hasLock = false;
         return ArrayElementInfoNode.doUncached(arg0Value, arg1Value, arg2Value);
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
            ArrayElementInfoNodeGen.CachedData s0_ = this.cached_cache;
            if (s0_ == null || s0_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null, null};
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doCached", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (ArrayElementInfoNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.arrayType_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doUncached", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static ArrayElementInfoNode create() {
      return new ArrayElementInfoNodeGen();
   }

   public static ArrayElementInfoNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(ArrayElementInfoNode.class)
   private static final class CachedData {
      @CompilerDirectives.CompilationFinal
      ArrayElementInfoNodeGen.CachedData next_;
      @CompilerDirectives.CompilationFinal
      ScriptArray arrayType_;

      CachedData(ArrayElementInfoNodeGen.CachedData next_) {
         this.next_ = next_;
      }
   }

   @GeneratedBy(ArrayElementInfoNode.class)
   @DenyReplace
   private static final class Uncached extends ArrayElementInfoNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public TriState execute(JSArrayBase arg0Value, long arg1Value, int arg2Value) {
         return ArrayElementInfoNode.doUncached(arg0Value, arg1Value, arg2Value);
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
