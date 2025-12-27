package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSArrayDeleteIndexNode.class)
public final class JSArrayDeleteIndexNodeGen extends JSArrayDeleteIndexNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private JSArrayDeleteIndexNodeGen.CachedData cached_cache;

   private JSArrayDeleteIndexNodeGen(JSContext context, boolean strict) {
      super(context, strict);
   }

   @ExplodeLoop
   @Override
   public boolean execute(JSDynamicObject arg0Value, ScriptArray arg1Value, long arg2Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            for (JSArrayDeleteIndexNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.cachedArrayType_.isInstance(arg1Value)) {
                  return this.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedArrayType_);
               }
            }
         }

         if ((state_0 & 2) != 0) {
            return this.doUncached(arg0Value, arg1Value, arg2Value);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
   }

   private boolean executeAndSpecialize(JSDynamicObject arg0Value, ScriptArray arg1Value, long arg2Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude == 0) {
            int count0_ = 0;
            JSArrayDeleteIndexNodeGen.CachedData s0_ = this.cached_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null && !s0_.cachedArrayType_.isInstance(arg1Value)) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null && arg1Value.isInstance(arg1Value) && count0_ < 5) {
               s0_ = new JSArrayDeleteIndexNodeGen.CachedData(this.cached_cache);
               s0_.cachedArrayType_ = arg1Value;
               VarHandle.storeStoreFence();
               this.cached_cache = s0_;
               this.state_0_ = state_0 |= 1;
            }

            if (s0_ != null) {
               lock.unlock();
               hasLock = false;
               return this.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedArrayType_);
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
         return this.doUncached(arg0Value, arg1Value, arg2Value);
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
            JSArrayDeleteIndexNodeGen.CachedData s0_ = this.cached_cache;
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

         for (JSArrayDeleteIndexNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.cachedArrayType_));
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

   public static JSArrayDeleteIndexNode create(JSContext context, boolean strict) {
      return new JSArrayDeleteIndexNodeGen(context, strict);
   }

   @GeneratedBy(JSArrayDeleteIndexNode.class)
   private static final class CachedData {
      @CompilerDirectives.CompilationFinal
      JSArrayDeleteIndexNodeGen.CachedData next_;
      @CompilerDirectives.CompilationFinal
      ScriptArray cachedArrayType_;

      CachedData(JSArrayDeleteIndexNodeGen.CachedData next_) {
         this.next_ = next_;
      }
   }
}
