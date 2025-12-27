package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IsJSObjectNode.class)
public final class IsJSObjectNodeGen extends IsJSObjectNode implements Introspection.Provider {
   private static final IsJSObjectNodeGen.Uncached UNCACHED = new IsJSObjectNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private Class<?> isObjectCached_cachedClass_;
   @CompilerDirectives.CompilationFinal
   private boolean isObjectCached_cachedResult_;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile isObject_resultProfile_;

   private IsJSObjectNodeGen() {
   }

   @Override
   public boolean executeBoolean(Object arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            assert this.isObjectCached_cachedClass_ != null;

            if (CompilerDirectives.isExact(arg0Value, this.isObjectCached_cachedClass_)) {
               return IsJSObjectNode.isObjectCached(arg0Value, this.isObjectCached_cachedClass_, this.isObjectCached_cachedResult_);
            }
         }

         if ((state_0 & 2) != 0) {
            return this.isObject(arg0Value, this.isObject_resultProfile_);
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
         if (exclude == 0) {
            boolean IsObjectCached_duplicateFound_ = false;
            if ((state_0 & 1) != 0) {
               assert this.isObjectCached_cachedClass_ != null;

               if (CompilerDirectives.isExact(arg0Value, this.isObjectCached_cachedClass_)) {
                  IsObjectCached_duplicateFound_ = true;
               }
            }

            if (!IsObjectCached_duplicateFound_) {
               Class<?> isObjectCached_cachedClass__ = JSGuards.getClassIfJSDynamicObject(arg0Value);
               if (isObjectCached_cachedClass__ != null && CompilerDirectives.isExact(arg0Value, isObjectCached_cachedClass__) && (state_0 & 1) == 0) {
                  this.isObjectCached_cachedClass_ = isObjectCached_cachedClass__;
                  this.isObjectCached_cachedResult_ = JSGuards.isJSObject(arg0Value);
                  this.state_0_ = state_0 |= 1;
                  IsObjectCached_duplicateFound_ = true;
               }
            }

            if (IsObjectCached_duplicateFound_) {
               lock.unlock();
               hasLock = false;
               return IsJSObjectNode.isObjectCached(arg0Value, this.isObjectCached_cachedClass_, this.isObjectCached_cachedResult_);
            }
         }

         this.isObject_resultProfile_ = ConditionProfile.create();
         int var13;
         this.exclude_ = var13 = exclude | 1;
         state_0 &= -2;
         int var12;
         this.state_0_ = var12 = state_0 | 2;
         lock.unlock();
         hasLock = false;
         return this.isObject(arg0Value, this.isObject_resultProfile_);
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
         return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null, null};
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"isObjectCached", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.isObjectCached_cachedClass_, this.isObjectCached_cachedResult_));
         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"isObject", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.isObject_resultProfile_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static IsJSObjectNode create() {
      return new IsJSObjectNodeGen();
   }

   public static IsJSObjectNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(IsJSObjectNode.class)
   @DenyReplace
   private static final class Uncached extends IsJSObjectNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean executeBoolean(Object arg0Value) {
         return this.isObject(arg0Value, ConditionProfile.getUncached());
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
