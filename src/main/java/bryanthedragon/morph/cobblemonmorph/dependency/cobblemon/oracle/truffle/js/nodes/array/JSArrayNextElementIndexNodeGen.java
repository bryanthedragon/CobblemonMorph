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

@GeneratedBy(JSArrayNextElementIndexNode.class)
public final class JSArrayNextElementIndexNodeGen extends JSArrayNextElementIndexNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private JSArrayNextElementIndexNodeGen.WithoutHolesCachedData withoutHolesCached_cache;
   @Node.Child
   private JSArrayNextElementIndexNodeGen.NextWithHolesCachedData nextWithHolesCached_cache;
   @Node.Child
   private JSArrayNextElementIndexNodeGen.NextWithHolesUncachedData nextWithHolesUncached_cache;
   @Node.Child
   private JSHasPropertyNode nextObjectViaEnumeration_hasPropertyNode_;
   @Node.Child
   private JSHasPropertyNode nextObjectViaFullEnumeration_hasPropertyNode_;
   @Node.Child
   private JSHasPropertyNode nextObjectViaPolling_hasPropertyNode_;

   private JSArrayNextElementIndexNodeGen(JSContext context) {
      super(context);
   }

   @ExplodeLoop
   @Override
   public long executeLong(Object arg0Value, long arg1Value, long arg2Value, boolean arg3Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 63) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if ((state_0 & 1) != 0 && arg3Value && !this.hasPrototypeElements(arg0Value_)) {
               for (JSArrayNextElementIndexNodeGen.WithoutHolesCachedData s0_ = this.withoutHolesCached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (JSArrayElementIndexNode.getArrayType(arg0Value_) == s0_.cachedArrayType_ && !s0_.cachedArrayType_.hasHoles(arg0Value_)) {
                     return this.doWithoutHolesCached(arg0Value_, arg1Value, arg2Value, arg3Value, s0_.cachedArrayType_);
                  }
               }
            }

            if ((state_0 & 2) != 0 && arg3Value && !this.hasPrototypeElements(arg0Value_) && !JSArrayElementIndexNode.hasHoles(arg0Value_)) {
               return this.doWithoutHolesUncached(arg0Value_, arg1Value, arg2Value, arg3Value);
            }

            if ((state_0 & 4) != 0 && arg3Value && !this.hasPrototypeElements(arg0Value_)) {
               for (JSArrayNextElementIndexNodeGen.NextWithHolesCachedData s2_ = this.nextWithHolesCached_cache; s2_ != null; s2_ = s2_.next_) {
                  if (JSArrayElementIndexNode.getArrayType(arg0Value_) == s2_.cachedArrayType_ && s2_.cachedArrayType_.hasHoles(arg0Value_)) {
                     return this.nextWithHolesCached(
                        arg0Value_, arg1Value, arg2Value, arg3Value, s2_.cachedArrayType_, s2_.nextElementIndexNode_, s2_.isPlusOne_
                     );
                  }
               }
            }

            if ((state_0 & 8) != 0) {
               JSArrayNextElementIndexNodeGen.NextWithHolesUncachedData s3_ = this.nextWithHolesUncached_cache;
               if (s3_ != null && arg3Value && (this.hasPrototypeElements(arg0Value_) || JSArrayElementIndexNode.hasHoles(arg0Value_))) {
                  return this.nextWithHolesUncached(
                     arg0Value_, arg1Value, arg2Value, arg3Value, s3_.nextElementIndexNode_, s3_.isPlusOne_, s3_.arrayTypeProfile_
                  );
               }
            }

            if ((state_0 & 16) != 0 && !arg3Value && this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg2Value)) {
               return this.nextObjectViaEnumeration(arg0Value_, arg1Value, arg2Value, arg3Value, this.nextObjectViaEnumeration_hasPropertyNode_);
            }

            if ((state_0 & 32) != 0
               && !arg3Value
               && !this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg2Value)
               && JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value_, arg2Value)) {
               return this.nextObjectViaFullEnumeration(arg0Value_, arg1Value, arg2Value, arg3Value, this.nextObjectViaFullEnumeration_hasPropertyNode_);
            }
         }

         if ((state_0 & 64) != 0 && !arg3Value && !JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value, arg2Value)) {
            return this.nextObjectViaPolling(arg0Value, arg1Value, arg2Value, arg3Value, this.nextObjectViaPolling_hasPropertyNode_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
   }

   private long executeAndSpecialize(Object arg0Value, long arg1Value, long arg2Value, boolean arg3Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if ((exclude & 1) == 0 && arg3Value && !this.hasPrototypeElements(arg0Value_)) {
               int count0_ = 0;
               JSArrayNextElementIndexNodeGen.WithoutHolesCachedData s0_ = this.withoutHolesCached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (JSArrayElementIndexNode.getArrayType(arg0Value_) != s0_.cachedArrayType_ || s0_.cachedArrayType_.hasHoles(arg0Value_))) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  ScriptArray cachedArrayType__ = JSArrayElementIndexNode.getArrayTypeIfArray(arg0Value_, arg3Value);
                  if (JSArrayElementIndexNode.getArrayType(arg0Value_) == cachedArrayType__ && !cachedArrayType__.hasHoles(arg0Value_) && count0_ < 4) {
                     s0_ = new JSArrayNextElementIndexNodeGen.WithoutHolesCachedData(this.withoutHolesCached_cache);
                     s0_.cachedArrayType_ = cachedArrayType__;
                     VarHandle.storeStoreFence();
                     this.withoutHolesCached_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doWithoutHolesCached(arg0Value_, arg1Value, arg2Value, arg3Value, s0_.cachedArrayType_);
               }
            }

            if (arg3Value && !this.hasPrototypeElements(arg0Value_) && !JSArrayElementIndexNode.hasHoles(arg0Value_)) {
               int var27;
               this.exclude_ = var27 = exclude | 1;
               this.withoutHolesCached_cache = null;
               state_0 &= -2;
               int var25;
               this.state_0_ = var25 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doWithoutHolesUncached(arg0Value_, arg1Value, arg2Value, arg3Value);
            }

            if ((exclude & 2) == 0 && arg3Value && !this.hasPrototypeElements(arg0Value_)) {
               int count2_ = 0;
               JSArrayNextElementIndexNodeGen.NextWithHolesCachedData s2_ = this.nextWithHolesCached_cache;
               if ((state_0 & 4) != 0) {
                  while (
                     s2_ != null && (JSArrayElementIndexNode.getArrayType(arg0Value_) != s2_.cachedArrayType_ || !s2_.cachedArrayType_.hasHoles(arg0Value_))
                  ) {
                     s2_ = s2_.next_;
                     count2_++;
                  }
               }

               if (s2_ == null) {
                  ScriptArray cachedArrayType__1 = JSArrayElementIndexNode.getArrayTypeIfArray(arg0Value_, arg3Value);
                  if (JSArrayElementIndexNode.getArrayType(arg0Value_) == cachedArrayType__1 && cachedArrayType__1.hasHoles(arg0Value_) && count2_ < 4) {
                     s2_ = super.insert(new JSArrayNextElementIndexNodeGen.NextWithHolesCachedData(this.nextWithHolesCached_cache));
                     s2_.cachedArrayType_ = cachedArrayType__1;
                     s2_.nextElementIndexNode_ = s2_.insertAccessor(JSArrayNextElementIndexNode.create(this.context));
                     s2_.isPlusOne_ = ConditionProfile.createBinaryProfile();
                     VarHandle.storeStoreFence();
                     this.nextWithHolesCached_cache = s2_;
                     this.state_0_ = state_0 |= 4;
                  }
               }

               if (s2_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.nextWithHolesCached(arg0Value_, arg1Value, arg2Value, arg3Value, s2_.cachedArrayType_, s2_.nextElementIndexNode_, s2_.isPlusOne_);
               }
            }

            if (arg3Value && (this.hasPrototypeElements(arg0Value_) || JSArrayElementIndexNode.hasHoles(arg0Value_))) {
               JSArrayNextElementIndexNodeGen.NextWithHolesUncachedData s3_ = super.insert(new JSArrayNextElementIndexNodeGen.NextWithHolesUncachedData());
               s3_.nextElementIndexNode_ = s3_.insertAccessor(JSArrayNextElementIndexNode.create(this.context));
               s3_.isPlusOne_ = ConditionProfile.createBinaryProfile();
               s3_.arrayTypeProfile_ = ValueProfile.createClassProfile();
               VarHandle.storeStoreFence();
               this.nextWithHolesUncached_cache = s3_;
               int var26;
               this.exclude_ = var26 = exclude | 2;
               this.nextWithHolesCached_cache = null;
               state_0 &= -5;
               int var23;
               this.state_0_ = var23 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.nextWithHolesUncached(arg0Value_, arg1Value, arg2Value, arg3Value, s3_.nextElementIndexNode_, s3_.isPlusOne_, s3_.arrayTypeProfile_);
            }

            if (!arg3Value && this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg2Value)) {
               this.nextObjectViaEnumeration_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
               int var21;
               this.state_0_ = var21 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.nextObjectViaEnumeration(arg0Value_, arg1Value, arg2Value, arg3Value, this.nextObjectViaEnumeration_hasPropertyNode_);
            }

            if (!arg3Value
               && !this.isSuitableForEnumBasedProcessingUsingOwnKeys(arg0Value_, arg2Value)
               && JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value_, arg2Value)) {
               this.nextObjectViaFullEnumeration_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
               int var20;
               this.state_0_ = var20 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.nextObjectViaFullEnumeration(arg0Value_, arg1Value, arg2Value, arg3Value, this.nextObjectViaFullEnumeration_hasPropertyNode_);
            }
         }

         if (arg3Value || JSArrayElementIndexNode.isSuitableForEnumBasedProcessing(arg0Value, arg2Value)) {
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
         } else {
            this.nextObjectViaPolling_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
            int var19;
            this.state_0_ = var19 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return this.nextObjectViaPolling(arg0Value, arg1Value, arg2Value, arg3Value, this.nextObjectViaPolling_hasPropertyNode_);
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
            JSArrayNextElementIndexNodeGen.WithoutHolesCachedData s0_ = this.withoutHolesCached_cache;
            JSArrayNextElementIndexNodeGen.NextWithHolesCachedData s2_ = this.nextWithHolesCached_cache;
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

         for (JSArrayNextElementIndexNodeGen.WithoutHolesCachedData s0_ = this.withoutHolesCached_cache; s0_ != null; s0_ = s0_.next_) {
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
      s = new Object[]{"nextWithHolesCached", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSArrayNextElementIndexNodeGen.NextWithHolesCachedData s2_ = this.nextWithHolesCached_cache; s2_ != null; s2_ = s2_.next_) {
            cached.add(Arrays.asList(s2_.cachedArrayType_, s2_.nextElementIndexNode_, s2_.isPlusOne_));
         }

         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"nextWithHolesUncached", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSArrayNextElementIndexNodeGen.NextWithHolesUncachedData s3_ = this.nextWithHolesUncached_cache;
         if (s3_ != null) {
            cached.add(Arrays.asList(s3_.nextElementIndexNode_, s3_.isPlusOne_, s3_.arrayTypeProfile_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"nextObjectViaEnumeration", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.nextObjectViaEnumeration_hasPropertyNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"nextObjectViaFullEnumeration", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.nextObjectViaFullEnumeration_hasPropertyNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"nextObjectViaPolling", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.nextObjectViaPolling_hasPropertyNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      return Introspection.Provider.create(data);
   }

   public static JSArrayNextElementIndexNode create(JSContext context) {
      return new JSArrayNextElementIndexNodeGen(context);
   }

   @GeneratedBy(JSArrayNextElementIndexNode.class)
   private static final class NextWithHolesCachedData extends Node {
      @Node.Child
      JSArrayNextElementIndexNodeGen.NextWithHolesCachedData next_;
      @CompilerDirectives.CompilationFinal
      ScriptArray cachedArrayType_;
      @Node.Child
      JSArrayNextElementIndexNode nextElementIndexNode_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile isPlusOne_;

      NextWithHolesCachedData(JSArrayNextElementIndexNodeGen.NextWithHolesCachedData next_) {
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

   @GeneratedBy(JSArrayNextElementIndexNode.class)
   private static final class NextWithHolesUncachedData extends Node {
      @Node.Child
      JSArrayNextElementIndexNode nextElementIndexNode_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile isPlusOne_;
      @CompilerDirectives.CompilationFinal
      ValueProfile arrayTypeProfile_;

      NextWithHolesUncachedData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(JSArrayNextElementIndexNode.class)
   private static final class WithoutHolesCachedData {
      @CompilerDirectives.CompilationFinal
      JSArrayNextElementIndexNodeGen.WithoutHolesCachedData next_;
      @CompilerDirectives.CompilationFinal
      ScriptArray cachedArrayType_;

      WithoutHolesCachedData(JSArrayNextElementIndexNodeGen.WithoutHolesCachedData next_) {
         this.next_ = next_;
      }
   }
}
