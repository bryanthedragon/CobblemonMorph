package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSArrayPreviousElementIndexNode.class)
public final class JSArrayPreviousElementIndexNodeGen extends JSArrayPreviousElementIndexNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private JSArrayPreviousElementIndexNodeGen.WithoutHolesCachedData withoutHolesCached_cache;
   @Node.Child
   private JSArrayPreviousElementIndexNodeGen.PreviousWithHolesCachedData previousWithHolesCached_cache;
   @Node.Child
   private JSArrayPreviousElementIndexNodeGen.PreviousWithHolesUncachedData previousWithHolesUncached_cache;
   @Node.Child
   private JSHasPropertyNode previousObjectViaEnumeration_hasPropertyNode_;
   @Node.Child
   private JSHasPropertyNode previousObjectViaFullEnumeration_hasPropertyNode_;
   @Node.Child
   private JSHasPropertyNode previousObjectViaIteration_hasPropertyNode_;

   private JSArrayPreviousElementIndexNodeGen(JSContext context) {
      super(context);
   }

   @ExplodeLoop
   @Override
   public long executeLong(Object arg0Value, long arg1Value, boolean arg2Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 63) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if ((state_0 & 1) != 0 && arg2Value && !this.hasPrototypeElements(arg0Value_)) {
               for (JSArrayPreviousElementIndexNodeGen.WithoutHolesCachedData s0_ = this.withoutHolesCached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (JSArrayElementIndexNode.getArrayType(arg0Value_) == s0_.cachedArrayType_ && !s0_.cachedArrayType_.hasHoles(arg0Value_)) {
                     return this.doWithoutHolesCached(arg0Value_, arg1Value, arg2Value, s0_.cachedArrayType_);
                  }
               }
            }

            if ((state_0 & 2) != 0 && arg2Value && !this.hasPrototypeElements(arg0Value_) && !JSArrayElementIndexNode.hasHoles(arg0Value_)) {
               return this.doWithoutHolesUncached(arg0Value_, arg1Value, arg2Value);
            }

            if ((state_0 & 4) != 0 && arg2Value && !this.hasPrototypeElements(arg0Value_)) {
               for (JSArrayPreviousElementIndexNodeGen.PreviousWithHolesCachedData s2_ = this.previousWithHolesCached_cache; s2_ != null; s2_ = s2_.next_) {
                  if (JSArrayElementIndexNode.getArrayType(arg0Value_) == s2_.cachedArrayType_ && s2_.cachedArrayType_.hasHoles(arg0Value_)) {
                     return this.previousWithHolesCached(arg0Value_, arg1Value, arg2Value, s2_.cachedArrayType_, s2_.previousElementIndexNode_, s2_.isMinusOne_);
                  }
               }
            }

            if ((state_0 & 8) != 0) {
               JSArrayPreviousElementIndexNodeGen.PreviousWithHolesUncachedData s3_ = this.previousWithHolesUncached_cache;
               if (s3_ != null && arg2Value && (this.hasPrototypeElements(arg0Value_) || JSArrayElementIndexNode.hasHoles(arg0Value_))) {
                  return this.previousWithHolesUncached(arg0Value_, arg1Value, arg2Value, s3_.previousElementIndexNode_, s3_.isMinusOne_, s3_.arrayTypeProfile_);
               }
            }

            if ((state_0 & 16) != 0 && !arg2Value && this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg1Value)) {
               return this.previousObjectViaEnumeration(arg0Value_, arg1Value, arg2Value, this.previousObjectViaEnumeration_hasPropertyNode_);
            }

            if ((state_0 & 32) != 0
               && !arg2Value
               && !this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg1Value)
               && JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value_, arg1Value)) {
               return this.previousObjectViaFullEnumeration(arg0Value_, arg1Value, arg2Value, this.previousObjectViaFullEnumeration_hasPropertyNode_);
            }
         }

         if ((state_0 & 64) != 0 && !arg2Value && !JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value, arg1Value)) {
            return this.previousObjectViaIteration(arg0Value, arg1Value, arg2Value, this.previousObjectViaIteration_hasPropertyNode_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
   }

   private long executeAndSpecialize(Object arg0Value, long arg1Value, boolean arg2Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if ((exclude & 1) == 0 && arg2Value && !this.hasPrototypeElements(arg0Value_)) {
               int count0_ = 0;
               JSArrayPreviousElementIndexNodeGen.WithoutHolesCachedData s0_ = this.withoutHolesCached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (JSArrayElementIndexNode.getArrayType(arg0Value_) != s0_.cachedArrayType_ || s0_.cachedArrayType_.hasHoles(arg0Value_))) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  ScriptArray cachedArrayType__ = JSArrayElementIndexNode.getArrayTypeIfArray(arg0Value_, arg2Value);
                  if (JSArrayElementIndexNode.getArrayType(arg0Value_) == cachedArrayType__ && !cachedArrayType__.hasHoles(arg0Value_) && count0_ < 4) {
                     s0_ = new JSArrayPreviousElementIndexNodeGen.WithoutHolesCachedData(this.withoutHolesCached_cache);
                     s0_.cachedArrayType_ = cachedArrayType__;
                     VarHandle.storeStoreFence();
                     this.withoutHolesCached_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doWithoutHolesCached(arg0Value_, arg1Value, arg2Value, s0_.cachedArrayType_);
               }
            }

            if (arg2Value && !this.hasPrototypeElements(arg0Value_) && !JSArrayElementIndexNode.hasHoles(arg0Value_)) {
               int var25;
               this.exclude_ = var25 = exclude | 1;
               this.withoutHolesCached_cache = null;
               state_0 &= -2;
               int var23;
               this.state_0_ = var23 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doWithoutHolesUncached(arg0Value_, arg1Value, arg2Value);
            }

            if ((exclude & 2) == 0 && arg2Value && !this.hasPrototypeElements(arg0Value_)) {
               int count2_ = 0;
               JSArrayPreviousElementIndexNodeGen.PreviousWithHolesCachedData s2_ = this.previousWithHolesCached_cache;
               if ((state_0 & 4) != 0) {
                  while (
                     s2_ != null && (JSArrayElementIndexNode.getArrayType(arg0Value_) != s2_.cachedArrayType_ || !s2_.cachedArrayType_.hasHoles(arg0Value_))
                  ) {
                     s2_ = s2_.next_;
                     count2_++;
                  }
               }

               if (s2_ == null) {
                  ScriptArray cachedArrayType__1 = JSArrayElementIndexNode.getArrayTypeIfArray(arg0Value_, arg2Value);
                  if (JSArrayElementIndexNode.getArrayType(arg0Value_) == cachedArrayType__1 && cachedArrayType__1.hasHoles(arg0Value_) && count2_ < 4) {
                     s2_ = super.insert(new JSArrayPreviousElementIndexNodeGen.PreviousWithHolesCachedData(this.previousWithHolesCached_cache));
                     s2_.cachedArrayType_ = cachedArrayType__1;
                     s2_.previousElementIndexNode_ = s2_.insertAccessor(JSArrayPreviousElementIndexNode.create(this.context));
                     s2_.isMinusOne_ = ConditionProfile.createBinaryProfile();
                     VarHandle.storeStoreFence();
                     this.previousWithHolesCached_cache = s2_;
                     this.state_0_ = state_0 |= 4;
                  }
               }

               if (s2_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.previousWithHolesCached(arg0Value_, arg1Value, arg2Value, s2_.cachedArrayType_, s2_.previousElementIndexNode_, s2_.isMinusOne_);
               }
            }

            if (arg2Value && (this.hasPrototypeElements(arg0Value_) || JSArrayElementIndexNode.hasHoles(arg0Value_))) {
               JSArrayPreviousElementIndexNodeGen.PreviousWithHolesUncachedData s3_ = super.insert(
                  new JSArrayPreviousElementIndexNodeGen.PreviousWithHolesUncachedData()
               );
               s3_.previousElementIndexNode_ = s3_.insertAccessor(JSArrayPreviousElementIndexNode.create(this.context));
               s3_.isMinusOne_ = ConditionProfile.createBinaryProfile();
               s3_.arrayTypeProfile_ = ValueProfile.createClassProfile();
               VarHandle.storeStoreFence();
               this.previousWithHolesUncached_cache = s3_;
               int var24;
               this.exclude_ = var24 = exclude | 2;
               this.previousWithHolesCached_cache = null;
               state_0 &= -5;
               int var21;
               this.state_0_ = var21 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.previousWithHolesUncached(arg0Value_, arg1Value, arg2Value, s3_.previousElementIndexNode_, s3_.isMinusOne_, s3_.arrayTypeProfile_);
            }

            if (!arg2Value && this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg1Value)) {
               this.previousObjectViaEnumeration_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
               int var19;
               this.state_0_ = var19 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.previousObjectViaEnumeration(arg0Value_, arg1Value, arg2Value, this.previousObjectViaEnumeration_hasPropertyNode_);
            }

            if (!arg2Value
               && !this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg1Value)
               && JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value_, arg1Value)) {
               this.previousObjectViaFullEnumeration_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
               int var18;
               this.state_0_ = var18 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.previousObjectViaFullEnumeration(arg0Value_, arg1Value, arg2Value, this.previousObjectViaFullEnumeration_hasPropertyNode_);
            }
         }

         if (arg2Value || JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value, arg1Value)) {
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
         } else {
            this.previousObjectViaIteration_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
            int var17;
            this.state_0_ = var17 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return this.previousObjectViaIteration(arg0Value, arg1Value, arg2Value, this.previousObjectViaIteration_hasPropertyNode_);
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
            JSArrayPreviousElementIndexNodeGen.WithoutHolesCachedData s0_ = this.withoutHolesCached_cache;
            JSArrayPreviousElementIndexNodeGen.PreviousWithHolesCachedData s2_ = this.previousWithHolesCached_cache;
            if ((s0_ == null || s0_.next_ == null) && (s2_ == null || s2_.next_ == null)) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[8];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doWithoutHolesCached", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSArrayPreviousElementIndexNodeGen.WithoutHolesCachedData s0_ = this.withoutHolesCached_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.cachedArrayType_));
         }

         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doWithoutHolesUncached", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"previousWithHolesCached", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSArrayPreviousElementIndexNodeGen.PreviousWithHolesCachedData s2_ = this.previousWithHolesCached_cache; s2_ != null; s2_ = s2_.next_) {
            cached.add(Arrays.asList(s2_.cachedArrayType_, s2_.previousElementIndexNode_, s2_.isMinusOne_));
         }

         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"previousWithHolesUncached", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSArrayPreviousElementIndexNodeGen.PreviousWithHolesUncachedData s3_ = this.previousWithHolesUncached_cache;
         if (s3_ != null) {
            cached.add(Arrays.asList(s3_.previousElementIndexNode_, s3_.isMinusOne_, s3_.arrayTypeProfile_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"previousObjectViaEnumeration", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.previousObjectViaEnumeration_hasPropertyNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"previousObjectViaFullEnumeration", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.previousObjectViaFullEnumeration_hasPropertyNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"previousObjectViaIteration", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.previousObjectViaIteration_hasPropertyNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      return Introspection.Provider.create(data);
   }

   public static JSArrayPreviousElementIndexNode create(JSContext context) {
      return new JSArrayPreviousElementIndexNodeGen(context);
   }

   @GeneratedBy(JSArrayPreviousElementIndexNode.class)
   private static final class PreviousWithHolesCachedData extends Node {
      @Node.Child
      JSArrayPreviousElementIndexNodeGen.PreviousWithHolesCachedData next_;
      @CompilerDirectives.CompilationFinal
      ScriptArray cachedArrayType_;
      @Node.Child
      JSArrayPreviousElementIndexNode previousElementIndexNode_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile isMinusOne_;

      PreviousWithHolesCachedData(JSArrayPreviousElementIndexNodeGen.PreviousWithHolesCachedData next_) {
         this.next_ = next_;
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(JSArrayPreviousElementIndexNode.class)
   private static final class PreviousWithHolesUncachedData extends Node {
      @Node.Child
      JSArrayPreviousElementIndexNode previousElementIndexNode_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile isMinusOne_;
      @CompilerDirectives.CompilationFinal
      ValueProfile arrayTypeProfile_;

      PreviousWithHolesUncachedData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(JSArrayPreviousElementIndexNode.class)
   private static final class WithoutHolesCachedData {
      @CompilerDirectives.CompilationFinal
      JSArrayPreviousElementIndexNodeGen.WithoutHolesCachedData next_;
      @CompilerDirectives.CompilationFinal
      ScriptArray cachedArrayType_;

      WithoutHolesCachedData(JSArrayPreviousElementIndexNodeGen.WithoutHolesCachedData next_) {
         this.next_ = next_;
      }
   }
}
