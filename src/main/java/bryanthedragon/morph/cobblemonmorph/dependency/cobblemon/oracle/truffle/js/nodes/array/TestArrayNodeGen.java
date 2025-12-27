package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TestArrayNode.class)
public final class TestArrayNodeGen extends TestArrayNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private TestArrayNodeGen.CachedData cached_cache;

   private TestArrayNodeGen(TestArrayNode.Test test) {
      super(test);
   }

   @ExplodeLoop
   @Override
   public boolean executeBoolean(JSDynamicObject arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            for (TestArrayNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.arrayType_.isInstance(TestArrayNode.getArrayType(arg0Value))) {
                  return this.doCached(arg0Value, s0_.arrayType_);
               }
            }
         }

         if ((state_0 & 2) != 0) {
            return this.doUncached(arg0Value);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   private boolean executeAndSpecialize(JSDynamicObject arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude == 0) {
            int count0_ = 0;
            TestArrayNodeGen.CachedData s0_ = this.cached_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null && !s0_.arrayType_.isInstance(TestArrayNode.getArrayType(arg0Value))) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null) {
               ScriptArray arrayType__ = TestArrayNode.getArrayType(arg0Value);
               if (arrayType__.isInstance(TestArrayNode.getArrayType(arg0Value)) && count0_ < 4) {
                  s0_ = new TestArrayNodeGen.CachedData(this.cached_cache);
                  s0_.arrayType_ = arrayType__;
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }
            }

            if (s0_ != null) {
               lock.unlock();
               hasLock = false;
               return this.doCached(arg0Value, s0_.arrayType_);
            }
         }

         int var14;
         this.exclude_ = var14 = exclude | 1;
         this.cached_cache = null;
         state_0 &= -2;
         int var13;
         this.state_0_ = var13 = state_0 | 2;
         lock.unlock();
         hasLock = false;
         return this.doUncached(arg0Value);
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
            TestArrayNodeGen.CachedData s0_ = this.cached_cache;
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

         for (TestArrayNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
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

   public static TestArrayNode create(TestArrayNode.Test test) {
      return new TestArrayNodeGen(test);
   }

   @GeneratedBy(TestArrayNode.class)
   private static final class CachedData {
      @CompilerDirectives.CompilationFinal
      TestArrayNodeGen.CachedData next_;
      @CompilerDirectives.CompilationFinal
      ScriptArray arrayType_;

      CachedData(TestArrayNodeGen.CachedData next_) {
         this.next_ = next_;
      }
   }
}
