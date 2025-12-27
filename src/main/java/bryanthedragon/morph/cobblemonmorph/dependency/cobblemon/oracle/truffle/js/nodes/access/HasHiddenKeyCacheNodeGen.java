package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(HasHiddenKeyCacheNode.class)
public final class HasHiddenKeyCacheNodeGen extends HasHiddenKeyCacheNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private HasHiddenKeyCacheNodeGen.CachedData cached_cache;

   private HasHiddenKeyCacheNodeGen(HiddenKey key) {
      super(key);
   }

   @ExplodeLoop
   @Override
   public boolean executeHasHiddenKey(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 3) != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         if ((state_0 & 1) != 0) {
            for (HasHiddenKeyCacheNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               if (!Assumption.isValidAssumption(s0_.assumption0_)) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.removeCached_(s0_);
                  return this.executeAndSpecialize(arg0Value_);
               }

               if (s0_.cachedShape_.check(arg0Value_)) {
                  return HasHiddenKeyCacheNode.doCached(arg0Value_, s0_.cachedShape_, s0_.hasOwnProperty_, s0_.cacheLimit_);
               }
            }
         }

         if ((state_0 & 2) != 0 && JSGuards.isJSObject(arg0Value_)) {
            return this.doUncached(arg0Value_);
         }
      }

      if ((state_0 & 4) != 0 && !JSGuards.isJSObject(arg0Value)) {
         return HasHiddenKeyCacheNode.doNonObject(arg0Value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private boolean executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if (exclude == 0) {
               int count0_ = 0;
               HasHiddenKeyCacheNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (!s0_.cachedShape_.check(arg0Value_) || !Assumption.isValidAssumption(s0_.assumption0_))) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  Shape cachedShape__ = arg0Value_.getShape();
                  if (cachedShape__.check(arg0Value_)) {
                     Assumption assumption0 = cachedShape__.getValidAssumption();
                     if (Assumption.isValidAssumption(assumption0)) {
                        int cacheLimit__ = this.getPropertyCacheLimit();
                        if (count0_ < cacheLimit__) {
                           s0_ = new HasHiddenKeyCacheNodeGen.CachedData(this.cached_cache);
                           s0_.cachedShape_ = cachedShape__;
                           s0_.hasOwnProperty_ = this.doUncached(arg0Value_);
                           s0_.cacheLimit_ = cacheLimit__;
                           s0_.assumption0_ = assumption0;
                           VarHandle.storeStoreFence();
                           this.cached_cache = s0_;
                           this.state_0_ = state_0 |= 1;
                        }
                     }
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return HasHiddenKeyCacheNode.doCached(arg0Value_, s0_.cachedShape_, s0_.hasOwnProperty_, s0_.cacheLimit_);
               }
            }

            if (JSGuards.isJSObject(arg0Value_)) {
               int var18;
               this.exclude_ = var18 = exclude | 1;
               this.cached_cache = null;
               state_0 &= -2;
               int var17;
               this.state_0_ = var17 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doUncached(arg0Value_);
            }
         }

         if (JSGuards.isJSObject(arg0Value)) {
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
         } else {
            int var15;
            this.state_0_ = var15 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return HasHiddenKeyCacheNode.doNonObject(arg0Value);
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
            HasHiddenKeyCacheNodeGen.CachedData s0_ = this.cached_cache;
            if (s0_ == null || s0_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   void removeCached_(Object s0_) {
      Lock lock = this.getLock();
      lock.lock();

      try {
         HasHiddenKeyCacheNodeGen.CachedData prev = null;

         for (HasHiddenKeyCacheNodeGen.CachedData cur = this.cached_cache; cur != null; cur = cur.next_) {
            if (cur == s0_) {
               if (prev == null) {
                  this.cached_cache = cur.next_;
               } else {
                  prev.next_ = cur.next_;
               }
               break;
            }

            prev = cur;
         }

         if (this.cached_cache == null) {
            this.state_0_ &= -2;
         }
      } finally {
         lock.unlock();
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

         for (HasHiddenKeyCacheNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.cachedShape_, s0_.hasOwnProperty_, s0_.cacheLimit_));
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
      s = new Object[]{"doNonObject", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static HasHiddenKeyCacheNode create(HiddenKey key) {
      return new HasHiddenKeyCacheNodeGen(key);
   }

   @GeneratedBy(HasHiddenKeyCacheNode.class)
   private static final class CachedData {
      @CompilerDirectives.CompilationFinal
      HasHiddenKeyCacheNodeGen.CachedData next_;
      @CompilerDirectives.CompilationFinal
      Shape cachedShape_;
      @CompilerDirectives.CompilationFinal
      boolean hasOwnProperty_;
      @CompilerDirectives.CompilationFinal
      int cacheLimit_;
      @CompilerDirectives.CompilationFinal
      Assumption assumption0_;

      CachedData(HasHiddenKeyCacheNodeGen.CachedData next_) {
         this.next_ = next_;
      }
   }
}
