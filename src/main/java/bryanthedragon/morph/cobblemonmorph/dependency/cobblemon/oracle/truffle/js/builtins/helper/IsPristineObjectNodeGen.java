package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IsPristineObjectNode.class)
public final class IsPristineObjectNodeGen extends IsPristineObjectNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private IsPristineObjectNodeGen.CachedData cached_cache;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private Assumption[] dynamic_assumption0_;

   private IsPristineObjectNodeGen(JSClass jsClass, Shape initialPrototypeShape, Object... propertyKeys) {
      super(jsClass, initialPrototypeShape, propertyKeys);
   }

   @ExplodeLoop
   @Override
   public boolean execute(JSDynamicObject arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            for (IsPristineObjectNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               if (!Assumption.isValidAssumption(s0_.assumption0_)) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.removeCached_(s0_);
                  return this.executeAndSpecialize(arg0Value);
               }

               if (s0_.cachedShape_.check(arg0Value)) {
                  return this.doCached(arg0Value, s0_.cachedShape_, s0_.isInstanceAndDoesNotOverwriteProps_);
               }
            }
         }

         if ((state_0 & 2) != 0) {
            if (!Assumption.isValidAssumption(this.dynamic_assumption0_)) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.removeDynamic_();
               return this.executeAndSpecialize(arg0Value);
            }

            return this.doDynamic(arg0Value);
         }

         if ((state_0 & 4) != 0) {
            return this.doAssumptionsInvalid(arg0Value);
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
            IsPristineObjectNodeGen.CachedData s0_ = this.cached_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null && (!s0_.cachedShape_.check(arg0Value) || !Assumption.isValidAssumption(s0_.assumption0_))) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null) {
               Shape cachedShape__ = arg0Value.getShape();
               if (cachedShape__.check(arg0Value)) {
                  Assumption[] assumption0 = this.getPropertyFinalAssumptions();
                  if (Assumption.isValidAssumption(assumption0) && count0_ < 3) {
                     s0_ = new IsPristineObjectNodeGen.CachedData(this.cached_cache);
                     s0_.cachedShape_ = cachedShape__;
                     s0_.isInstanceAndDoesNotOverwriteProps_ = this.isInstanceAndDoesNotOverwriteProps(cachedShape__);
                     s0_.assumption0_ = assumption0;
                     VarHandle.storeStoreFence();
                     this.cached_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }
            }

            if (s0_ != null) {
               lock.unlock();
               hasLock = false;
               return this.doCached(arg0Value, s0_.cachedShape_, s0_.isInstanceAndDoesNotOverwriteProps_);
            }
         }

         Assumption[] dynamic_assumption0 = this.getPropertyFinalAssumptions();
         if (!Assumption.isValidAssumption(dynamic_assumption0)) {
            int var15;
            this.state_0_ = var15 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doAssumptionsInvalid(arg0Value);
         } else {
            this.dynamic_assumption0_ = dynamic_assumption0;
            int var16;
            this.exclude_ = var16 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var14;
            this.state_0_ = var14 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doDynamic(arg0Value);
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
            IsPristineObjectNodeGen.CachedData s0_ = this.cached_cache;
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
         IsPristineObjectNodeGen.CachedData prev = null;

         for (IsPristineObjectNodeGen.CachedData cur = this.cached_cache; cur != null; cur = cur.next_) {
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

   void removeDynamic_() {
      Lock lock = this.getLock();
      lock.lock();

      try {
         this.state_0_ &= -3;
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

         for (IsPristineObjectNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.cachedShape_, s0_.isInstanceAndDoesNotOverwriteProps_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doDynamic", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doAssumptionsInvalid", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static IsPristineObjectNode create(JSClass jsClass, Shape initialPrototypeShape, Object... propertyKeys) {
      return new IsPristineObjectNodeGen(jsClass, initialPrototypeShape, propertyKeys);
   }

   @GeneratedBy(IsPristineObjectNode.class)
   private static final class CachedData {
      @CompilerDirectives.CompilationFinal
      IsPristineObjectNodeGen.CachedData next_;
      @CompilerDirectives.CompilationFinal
      Shape cachedShape_;
      @CompilerDirectives.CompilationFinal
      boolean isInstanceAndDoesNotOverwriteProps_;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      Assumption[] assumption0_;

      CachedData(IsPristineObjectNodeGen.CachedData next_) {
         this.next_ = next_;
      }
   }
}
