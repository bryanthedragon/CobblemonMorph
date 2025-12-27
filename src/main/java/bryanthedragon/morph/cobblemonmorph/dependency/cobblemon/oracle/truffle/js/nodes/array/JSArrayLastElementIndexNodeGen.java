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

@GeneratedBy(JSArrayLastElementIndexNode.class)
public final class JSArrayLastElementIndexNodeGen extends JSArrayLastElementIndexNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private JSArrayLastElementIndexNodeGen.WithoutHolesCachedData withoutHolesCached_cache;
   @Node.Child
   private JSArrayLastElementIndexNodeGen.WithHolesCachedData withHolesCached_cache;
   @Node.Child
   private JSArrayLastElementIndexNodeGen.WithHolesUncachedData withHolesUncached_cache;
   @Node.Child
   private JSHasPropertyNode objectViaEnumeration_hasPropertyNode_;
   @Node.Child
   private JSHasPropertyNode objectViaFullEnumeration_hasPropertyNode_;
   @Node.Child
   private JSHasPropertyNode object_hasPropertyNode_;

   private JSArrayLastElementIndexNodeGen(JSContext context) {
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
               for (JSArrayLastElementIndexNodeGen.WithoutHolesCachedData s0_ = this.withoutHolesCached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (JSArrayElementIndexNode.getArrayType(arg0Value_) == s0_.cachedArrayType_ && !s0_.cachedArrayType_.hasHoles(arg0Value_)) {
                     return this.doWithoutHolesCached(arg0Value_, arg1Value, arg2Value, s0_.cachedArrayType_);
                  }
               }
            }

            if ((state_0 & 2) != 0 && arg2Value && !this.hasPrototypeElements(arg0Value_) && !JSArrayElementIndexNode.hasHoles(arg0Value_)) {
               return this.doWithoutHolesUncached(arg0Value_, arg1Value, arg2Value);
            }

            if ((state_0 & 4) != 0 && arg2Value && !this.hasPrototypeElements(arg0Value_)) {
               for (JSArrayLastElementIndexNodeGen.WithHolesCachedData s2_ = this.withHolesCached_cache; s2_ != null; s2_ = s2_.next_) {
                  if (JSArrayElementIndexNode.getArrayType(arg0Value_) == s2_.cachedArrayType_ && s2_.cachedArrayType_.hasHoles(arg0Value_)) {
                     return this.doWithHolesCached(arg0Value_, arg1Value, arg2Value, s2_.cachedArrayType_, s2_.previousElementIndexNode_, s2_.isLengthMinusOne_);
                  }
               }
            }

            if ((state_0 & 8) != 0) {
               JSArrayLastElementIndexNodeGen.WithHolesUncachedData s3_ = this.withHolesUncached_cache;
               if (s3_ != null && arg2Value && (this.hasPrototypeElements(arg0Value_) || JSArrayElementIndexNode.hasHoles(arg0Value_))) {
                  return this.doWithHolesUncached(arg0Value_, arg1Value, arg2Value, s3_.previousElementIndexNode_, s3_.isLengthMinusOne_, s3_.arrayTypeProfile_);
               }
            }

            if ((state_0 & 16) != 0 && !arg2Value && this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg1Value)) {
               return this.doObjectViaEnumeration(arg0Value_, arg1Value, arg2Value, this.objectViaEnumeration_hasPropertyNode_);
            }

            if ((state_0 & 32) != 0
               && !arg2Value
               && !this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg1Value)
               && JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value_, arg1Value)) {
               return this.doObjectViaFullEnumeration(arg0Value_, arg1Value, arg2Value, this.objectViaFullEnumeration_hasPropertyNode_);
            }
         }

         if ((state_0 & 64) != 0 && !arg2Value && !JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value, arg1Value)) {
            return this.doObject(arg0Value, arg1Value, arg2Value, this.object_hasPropertyNode_);
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
               JSArrayLastElementIndexNodeGen.WithoutHolesCachedData s0_ = this.withoutHolesCached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (JSArrayElementIndexNode.getArrayType(arg0Value_) != s0_.cachedArrayType_ || s0_.cachedArrayType_.hasHoles(arg0Value_))) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  ScriptArray cachedArrayType__ = JSArrayElementIndexNode.getArrayTypeIfArray(arg0Value_, arg2Value);
                  if (JSArrayElementIndexNode.getArrayType(arg0Value_) == cachedArrayType__ && !cachedArrayType__.hasHoles(arg0Value_) && count0_ < 4) {
                     s0_ = new JSArrayLastElementIndexNodeGen.WithoutHolesCachedData(this.withoutHolesCached_cache);
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
               JSArrayLastElementIndexNodeGen.WithHolesCachedData s2_ = this.withHolesCached_cache;
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
                     s2_ = super.insert(new JSArrayLastElementIndexNodeGen.WithHolesCachedData(this.withHolesCached_cache));
                     s2_.cachedArrayType_ = cachedArrayType__1;
                     s2_.previousElementIndexNode_ = s2_.insertAccessor(JSArrayPreviousElementIndexNode.create(this.context));
                     s2_.isLengthMinusOne_ = ConditionProfile.createBinaryProfile();
                     VarHandle.storeStoreFence();
                     this.withHolesCached_cache = s2_;
                     this.state_0_ = state_0 |= 4;
                  }
               }

               if (s2_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doWithHolesCached(arg0Value_, arg1Value, arg2Value, s2_.cachedArrayType_, s2_.previousElementIndexNode_, s2_.isLengthMinusOne_);
               }
            }

            if (arg2Value && (this.hasPrototypeElements(arg0Value_) || JSArrayElementIndexNode.hasHoles(arg0Value_))) {
               JSArrayLastElementIndexNodeGen.WithHolesUncachedData s3_ = super.insert(new JSArrayLastElementIndexNodeGen.WithHolesUncachedData());
               s3_.previousElementIndexNode_ = s3_.insertAccessor(JSArrayPreviousElementIndexNode.create(this.context));
               s3_.isLengthMinusOne_ = ConditionProfile.createBinaryProfile();
               s3_.arrayTypeProfile_ = ValueProfile.createClassProfile();
               VarHandle.storeStoreFence();
               this.withHolesUncached_cache = s3_;
               int var24;
               this.exclude_ = var24 = exclude | 2;
               this.withHolesCached_cache = null;
               state_0 &= -5;
               int var21;
               this.state_0_ = var21 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doWithHolesUncached(arg0Value_, arg1Value, arg2Value, s3_.previousElementIndexNode_, s3_.isLengthMinusOne_, s3_.arrayTypeProfile_);
            }

            if (!arg2Value && this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg1Value)) {
               this.objectViaEnumeration_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
               int var19;
               this.state_0_ = var19 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doObjectViaEnumeration(arg0Value_, arg1Value, arg2Value, this.objectViaEnumeration_hasPropertyNode_);
            }

            if (!arg2Value
               && !this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg1Value)
               && JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value_, arg1Value)) {
               this.objectViaFullEnumeration_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
               int var18;
               this.state_0_ = var18 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.doObjectViaFullEnumeration(arg0Value_, arg1Value, arg2Value, this.objectViaFullEnumeration_hasPropertyNode_);
            }
         }

         if (arg2Value || JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value, arg1Value)) {
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
         } else {
            this.object_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
            int var17;
            this.state_0_ = var17 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return this.doObject(arg0Value, arg1Value, arg2Value, this.object_hasPropertyNode_);
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
            JSArrayLastElementIndexNodeGen.WithoutHolesCachedData s0_ = this.withoutHolesCached_cache;
            JSArrayLastElementIndexNodeGen.WithHolesCachedData s2_ = this.withHolesCached_cache;
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

         for (JSArrayLastElementIndexNodeGen.WithoutHolesCachedData s0_ = this.withoutHolesCached_cache; s0_ != null; s0_ = s0_.next_) {
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
      s = new Object[]{"doWithHolesCached", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSArrayLastElementIndexNodeGen.WithHolesCachedData s2_ = this.withHolesCached_cache; s2_ != null; s2_ = s2_.next_) {
            cached.add(Arrays.asList(s2_.cachedArrayType_, s2_.previousElementIndexNode_, s2_.isLengthMinusOne_));
         }

         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doWithHolesUncached", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSArrayLastElementIndexNodeGen.WithHolesUncachedData s3_ = this.withHolesUncached_cache;
         if (s3_ != null) {
            cached.add(Arrays.asList(s3_.previousElementIndexNode_, s3_.isLengthMinusOne_, s3_.arrayTypeProfile_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doObjectViaEnumeration", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.objectViaEnumeration_hasPropertyNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doObjectViaFullEnumeration", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.objectViaFullEnumeration_hasPropertyNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doObject", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.object_hasPropertyNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      return Introspection.Provider.create(data);
   }

   public static JSArrayLastElementIndexNode create(JSContext context) {
      return new JSArrayLastElementIndexNodeGen(context);
   }

   @GeneratedBy(JSArrayLastElementIndexNode.class)
   private static final class WithHolesCachedData extends Node {
      @Node.Child
      JSArrayLastElementIndexNodeGen.WithHolesCachedData next_;
      @CompilerDirectives.CompilationFinal
      ScriptArray cachedArrayType_;
      @Node.Child
      JSArrayPreviousElementIndexNode previousElementIndexNode_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile isLengthMinusOne_;

      WithHolesCachedData(JSArrayLastElementIndexNodeGen.WithHolesCachedData next_) {
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

   @GeneratedBy(JSArrayLastElementIndexNode.class)
   private static final class WithHolesUncachedData extends Node {
      @Node.Child
      JSArrayPreviousElementIndexNode previousElementIndexNode_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile isLengthMinusOne_;
      @CompilerDirectives.CompilationFinal
      ValueProfile arrayTypeProfile_;

      WithHolesUncachedData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(JSArrayLastElementIndexNode.class)
   private static final class WithoutHolesCachedData {
      @CompilerDirectives.CompilationFinal
      JSArrayLastElementIndexNodeGen.WithoutHolesCachedData next_;
      @CompilerDirectives.CompilationFinal
      ScriptArray cachedArrayType_;

      WithoutHolesCachedData(JSArrayLastElementIndexNodeGen.WithoutHolesCachedData next_) {
         this.next_ = next_;
      }
   }
}
