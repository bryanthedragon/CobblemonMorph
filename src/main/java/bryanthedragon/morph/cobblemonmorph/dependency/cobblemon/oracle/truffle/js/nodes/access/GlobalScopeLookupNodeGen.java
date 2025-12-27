package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(GlobalScopeLookupNode.class)
public final class GlobalScopeLookupNodeGen extends GlobalScopeLookupNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private Assumption absent_assumption_;
   @CompilerDirectives.CompilationFinal
   private GlobalScopeLookupNodeGen.CachedData cached_cache;
   @CompilerDirectives.CompilationFinal
   private BranchProfile uncached_errorBranch_;

   private GlobalScopeLookupNodeGen(TruffleString varName, boolean write) {
      super(varName, write);
   }

   @ExplodeLoop
   @Override
   public boolean execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         if ((state_0 & 1) != 0) {
            if (!Assumption.isValidAssumption(this.absent_assumption_)) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.removeAbsent_();
               return this.executeAndSpecialize(arg0Value_);
            }

            return GlobalScopeLookupNode.doAbsent(arg0Value_, this.absent_assumption_);
         }

         if ((state_0 & 2) != 0) {
            for (GlobalScopeLookupNodeGen.CachedData s1_ = this.cached_cache; s1_ != null; s1_ = s1_.next_) {
               if (!Assumption.isValidAssumption(s1_.assumption0_)) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.removeCached_(s1_);
                  return this.executeAndSpecialize(arg0Value_);
               }

               if (arg0Value_.getShape() == s1_.cachedShape_) {
                  return this.doCached(arg0Value_, s1_.cachedShape_, s1_.exists_, s1_.dead_, s1_.constAssignment_, s1_.cacheLimit_);
               }
            }
         }

         if ((state_0 & 4) != 0) {
            return this.doUncached(arg0Value_, this.uncached_errorBranch_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   private boolean executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (!(arg0Value instanceof JSDynamicObject)) {
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
         } else {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if ((exclude & 1) == 0) {
               Assumption absent_assumption__ = this.getAbsentPropertyAssumption(arg0Value_.getShape());
               if (Assumption.isValidAssumption(absent_assumption__)) {
                  this.absent_assumption_ = absent_assumption__;
                  int var18;
                  this.state_0_ = var18 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return GlobalScopeLookupNode.doAbsent(arg0Value_, absent_assumption__);
               }
            }

            if ((exclude & 2) == 0) {
               int count1_ = 0;
               GlobalScopeLookupNodeGen.CachedData s1_ = this.cached_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null && (arg0Value_.getShape() != s1_.cachedShape_ || !Assumption.isValidAssumption(s1_.assumption0_))) {
                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null) {
                  Shape cachedShape__ = arg0Value_.getShape();
                  if (arg0Value_.getShape() == cachedShape__) {
                     Assumption assumption0 = cachedShape__.getValidAssumption();
                     if (Assumption.isValidAssumption(assumption0)) {
                        int cacheLimit__ = this.getPropertyCacheLimit();
                        if (count1_ < cacheLimit__) {
                           s1_ = new GlobalScopeLookupNodeGen.CachedData(this.cached_cache);
                           s1_.cachedShape_ = cachedShape__;
                           s1_.exists_ = cachedShape__.hasProperty(this.varName);
                           s1_.dead_ = this.isDead(cachedShape__);
                           s1_.constAssignment_ = this.isConstAssignment(cachedShape__);
                           s1_.cacheLimit_ = cacheLimit__;
                           s1_.assumption0_ = assumption0;
                           VarHandle.storeStoreFence();
                           this.cached_cache = s1_;
                           this.exclude_ = exclude |= 1;
                           int var15 = state_0 & -2;
                           this.state_0_ = state_0 = var15 | 2;
                        }
                     }
                  }
               }

               if (s1_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value_, s1_.cachedShape_, s1_.exists_, s1_.dead_, s1_.constAssignment_, s1_.cacheLimit_);
               }
            }

            this.uncached_errorBranch_ = BranchProfile.create();
            int var19;
            this.exclude_ = var19 = exclude | 3;
            this.cached_cache = null;
            state_0 &= -4;
            int var17;
            this.state_0_ = var17 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doUncached(arg0Value_, this.uncached_errorBranch_);
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
            GlobalScopeLookupNodeGen.CachedData s1_ = this.cached_cache;
            if (s1_ == null || s1_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   void removeAbsent_() {
      Lock lock = this.getLock();
      lock.lock();

      try {
         this.state_0_ &= -2;
      } finally {
         lock.unlock();
      }
   }

   void removeCached_(Object s1_) {
      Lock lock = this.getLock();
      lock.lock();

      try {
         GlobalScopeLookupNodeGen.CachedData prev = null;

         for (GlobalScopeLookupNodeGen.CachedData cur = this.cached_cache; cur != null; cur = cur.next_) {
            if (cur == s1_) {
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
            this.state_0_ &= -3;
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
      Object[] s = new Object[]{"doAbsent", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.absent_assumption_));
         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doCached", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (GlobalScopeLookupNodeGen.CachedData s1_ = this.cached_cache; s1_ != null; s1_ = s1_.next_) {
            cached.add(Arrays.asList(s1_.cachedShape_, s1_.exists_, s1_.dead_, s1_.constAssignment_, s1_.cacheLimit_));
         }

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
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.uncached_errorBranch_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static GlobalScopeLookupNode create(TruffleString varName, boolean write) {
      return new GlobalScopeLookupNodeGen(varName, write);
   }

   @GeneratedBy(GlobalScopeLookupNode.class)
   private static final class CachedData {
      @CompilerDirectives.CompilationFinal
      GlobalScopeLookupNodeGen.CachedData next_;
      @CompilerDirectives.CompilationFinal
      Shape cachedShape_;
      @CompilerDirectives.CompilationFinal
      boolean exists_;
      @CompilerDirectives.CompilationFinal
      boolean dead_;
      @CompilerDirectives.CompilationFinal
      boolean constAssignment_;
      @CompilerDirectives.CompilationFinal
      int cacheLimit_;
      @CompilerDirectives.CompilationFinal
      Assumption assumption0_;

      CachedData(GlobalScopeLookupNodeGen.CachedData next_) {
         this.next_ = next_;
      }
   }
}
