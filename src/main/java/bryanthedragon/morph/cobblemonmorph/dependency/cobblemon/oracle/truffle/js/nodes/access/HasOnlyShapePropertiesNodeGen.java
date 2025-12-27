package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(HasOnlyShapePropertiesNode.class)
public final class HasOnlyShapePropertiesNodeGen extends HasOnlyShapePropertiesNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private HasOnlyShapePropertiesNodeGen.CachedData cached_cache;
   @CompilerDirectives.CompilationFinal
   private JSContext objectPrototype_context_;

   private HasOnlyShapePropertiesNodeGen() {
   }

   @ExplodeLoop
   @Override
   public boolean execute(JSDynamicObject arg0Value, JSClass arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            for (HasOnlyShapePropertiesNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               if (arg1Value == s0_.cachedJSClass_) {
                  assert !HasOnlyShapePropertiesNode.isJSObjectPrototype(s0_.cachedJSClass_);

                  return HasOnlyShapePropertiesNode.doCached(arg0Value, arg1Value, s0_.cachedJSClass_);
               }
            }
         }

         if ((state_0 & 2) != 0 && HasOnlyShapePropertiesNode.isJSObjectPrototype(arg1Value)) {
            return HasOnlyShapePropertiesNode.doObjectPrototype(arg0Value, arg1Value, this.objectPrototype_context_);
         }

         if ((state_0 & 4) != 0) {
            return HasOnlyShapePropertiesNode.doUncached(arg0Value, arg1Value);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private boolean executeAndSpecialize(JSDynamicObject arg0Value, JSClass arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if ((exclude & 1) == 0) {
            int count0_ = 0;
            HasOnlyShapePropertiesNodeGen.CachedData s0_ = this.cached_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null) {
                  if (arg1Value == s0_.cachedJSClass_) {
                     assert !HasOnlyShapePropertiesNode.isJSObjectPrototype(s0_.cachedJSClass_);
                     break;
                  }

                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null && !HasOnlyShapePropertiesNode.isJSObjectPrototype(arg1Value) && count0_ < 5) {
               s0_ = new HasOnlyShapePropertiesNodeGen.CachedData(this.cached_cache);
               s0_.cachedJSClass_ = arg1Value;
               VarHandle.storeStoreFence();
               this.cached_cache = s0_;
               this.state_0_ = state_0 |= 1;
            }

            if (s0_ != null) {
               lock.unlock();
               hasLock = false;
               return HasOnlyShapePropertiesNode.doCached(arg0Value, arg1Value, s0_.cachedJSClass_);
            }
         }

         if ((exclude & 2) != 0 || !HasOnlyShapePropertiesNode.isJSObjectPrototype(arg1Value)) {
            int var16;
            this.exclude_ = var16 = exclude | 3;
            this.cached_cache = null;
            state_0 &= -4;
            int var15;
            this.state_0_ = var15 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return HasOnlyShapePropertiesNode.doUncached(arg0Value, arg1Value);
         } else {
            this.objectPrototype_context_ = JSObject.getJSContext(arg0Value);
            int var13;
            this.state_0_ = var13 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return HasOnlyShapePropertiesNode.doObjectPrototype(arg0Value, arg1Value, this.objectPrototype_context_);
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
            HasOnlyShapePropertiesNodeGen.CachedData s0_ = this.cached_cache;
            if (s0_ == null || s0_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[4];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doCached", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (HasOnlyShapePropertiesNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.cachedJSClass_));
         }

         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doObjectPrototype", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.objectPrototype_context_));
         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doUncached", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static HasOnlyShapePropertiesNode create() {
      return new HasOnlyShapePropertiesNodeGen();
   }

   @GeneratedBy(HasOnlyShapePropertiesNode.class)
   private static final class CachedData {
      @CompilerDirectives.CompilationFinal
      HasOnlyShapePropertiesNodeGen.CachedData next_;
      @CompilerDirectives.CompilationFinal
      JSClass cachedJSClass_;

      CachedData(HasOnlyShapePropertiesNodeGen.CachedData next_) {
         this.next_ = next_;
      }
   }
}
