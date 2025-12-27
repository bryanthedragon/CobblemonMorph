package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.cast.ToArrayIndexNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(CachedSetPropertyNode.class)
final class CachedSetPropertyNodeGen extends CachedSetPropertyNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private CachedSetPropertyNodeGen.CachedKeyData cachedKey_cache;
   @CompilerDirectives.CompilationFinal
   private JSClassProfile intIndex_jsclassProfile_;
   @Node.Child
   private CachedSetPropertyNodeGen.ArrayIndexData arrayIndex_cache;
   @Node.Child
   private JSProxyPropertySetNode proxy_proxySet_;
   @Node.Child
   private CachedSetPropertyNodeGen.GenericData generic_cache;

   private CachedSetPropertyNodeGen(JSContext context, boolean strict, boolean setOwn, boolean superProperty) {
      super(context, strict, setOwn, superProperty);
   }

   @ExplodeLoop
   @Override
   public void execute(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value, Object arg3Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            for (CachedSetPropertyNodeGen.CachedKeyData s0_ = this.cachedKey_cache; s0_ != null; s0_ = s0_.next_) {
               assert s0_.cachedKey_ != null;

               assert !JSRuntime.isArrayIndex(s0_.cachedKey_);

               if (JSRuntime.propertyKeyEquals(s0_.equalsNode_, s0_.cachedKey_, arg1Value)) {
                  this.doCachedKey(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedKey_, s0_.propertyNode_, s0_.equalsNode_);
                  return;
               }
            }
         }

         if ((state_0 & 2) != 0 && arg1Value instanceof Integer) {
            int arg1Value_ = (Integer)arg1Value;
            if (JSRuntime.isArrayIndex(arg1Value_) && !JSGuards.isJSProxy(arg0Value)) {
               this.doIntIndex(arg0Value, arg1Value_, arg2Value, arg3Value, this.intIndex_jsclassProfile_);
               return;
            }
         }

         if ((state_0 & 28) != 0) {
            if ((state_0 & 4) != 0 && !JSGuards.isJSProxy(arg0Value)) {
               for (CachedSetPropertyNodeGen.ArrayIndexData s2_ = this.arrayIndex_cache; s2_ != null; s2_ = s2_.next_) {
                  Object maybeIndex__ = s2_.toArrayIndexNode_.execute(arg1Value);
                  if (s2_.toArrayIndexNode_.isResultArrayIndex(maybeIndex__)) {
                     this.doArrayIndex(arg0Value, arg1Value, arg2Value, arg3Value, s2_.toArrayIndexNode_, maybeIndex__, s2_.jsclassProfile_);
                     return;
                  }
               }
            }

            if ((state_0 & 8) != 0 && JSGuards.isJSProxy(arg0Value)) {
               this.doProxy(arg0Value, arg1Value, arg2Value, arg3Value, this.proxy_proxySet_);
               return;
            }

            if ((state_0 & 16) != 0) {
               CachedSetPropertyNodeGen.GenericData s4_ = this.generic_cache;
               if (s4_ != null) {
                  this.doGeneric(
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     s4_.toArrayIndexNode_,
                     s4_.getType_,
                     s4_.jsclassProfile_,
                     s4_.highFrequency_,
                     s4_.hotKey_,
                     s4_.equalsNode_
                  );
                  return;
               }
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
   }

   private void executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value, Object arg3Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         int oldState_0 = state_0;

         try {
            if ((exclude & 1) == 0) {
               int count0_ = 0;
               CachedSetPropertyNodeGen.CachedKeyData s0_ = this.cachedKey_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null) {
                     assert s0_.cachedKey_ != null;

                     assert !JSRuntime.isArrayIndex(s0_.cachedKey_);

                     if (JSRuntime.propertyKeyEquals(s0_.equalsNode_, s0_.cachedKey_, arg1Value)) {
                        break;
                     }

                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  Object cachedKey__ = CachedGetPropertyNode.cachedPropertyKey(arg1Value);
                  if (cachedKey__ != null && !JSRuntime.isArrayIndex(cachedKey__)) {
                     TruffleString.EqualNode equalsNode__ = super.insert(TruffleString.EqualNode.create());
                     if (JSRuntime.propertyKeyEquals(equalsNode__, cachedKey__, arg1Value) && count0_ < 1) {
                        s0_ = super.insert(new CachedSetPropertyNodeGen.CachedKeyData(this.cachedKey_cache));
                        s0_.cachedKey_ = cachedKey__;
                        s0_.propertyNode_ = s0_.insertAccessor(this.createSet(cachedKey__));
                        s0_.equalsNode_ = s0_.insertAccessor(equalsNode__);
                        VarHandle.storeStoreFence();
                        this.cachedKey_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                     }
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  this.doCachedKey(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedKey_, s0_.propertyNode_, s0_.equalsNode_);
                  return;
               }
            }

            if ((exclude & 2) == 0 && arg1Value instanceof Integer) {
               int arg1Value_ = (Integer)arg1Value;
               if (JSRuntime.isArrayIndex(arg1Value_) && !JSGuards.isJSProxy(arg0Value)) {
                  this.intIndex_jsclassProfile_ = JSClassProfile.create();
                  int var26;
                  this.state_0_ = var26 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  this.doIntIndex(arg0Value, arg1Value_, arg2Value, arg3Value, this.intIndex_jsclassProfile_);
                  return;
               }
            }

            Object maybeIndex__ = null;
            if ((exclude & 4) == 0 && !JSGuards.isJSProxy(arg0Value)) {
               int count2_ = 0;
               CachedSetPropertyNodeGen.ArrayIndexData s2_ = this.arrayIndex_cache;
               if ((state_0 & 4) != 0) {
                  while (s2_ != null) {
                     maybeIndex__ = s2_.toArrayIndexNode_.execute(arg1Value);
                     if (s2_.toArrayIndexNode_.isResultArrayIndex(maybeIndex__)) {
                        break;
                     }

                     s2_ = s2_.next_;
                     count2_++;
                  }
               }

               if (s2_ == null) {
                  ToArrayIndexNode toArrayIndexNode__ = super.insert(ToArrayIndexNode.createNoToPropertyKey());
                  maybeIndex__ = toArrayIndexNode__.execute(arg1Value);
                  if (toArrayIndexNode__.isResultArrayIndex(maybeIndex__) && count2_ < 3) {
                     s2_ = super.insert(new CachedSetPropertyNodeGen.ArrayIndexData(this.arrayIndex_cache));
                     s2_.toArrayIndexNode_ = s2_.insertAccessor(toArrayIndexNode__);
                     s2_.jsclassProfile_ = JSClassProfile.create();
                     VarHandle.storeStoreFence();
                     this.arrayIndex_cache = s2_;
                     this.exclude_ = exclude |= 2;
                     int var22 = state_0 & -3;
                     this.state_0_ = state_0 = var22 | 4;
                  }
               }

               if (s2_ != null) {
                  lock.unlock();
                  hasLock = false;
                  this.doArrayIndex(arg0Value, arg1Value, arg2Value, arg3Value, s2_.toArrayIndexNode_, maybeIndex__, s2_.jsclassProfile_);
                  return;
               }
            }

            if ((exclude & 8) != 0 || !JSGuards.isJSProxy(arg0Value)) {
               maybeIndex__ = super.insert(new CachedSetPropertyNodeGen.GenericData());
               ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).toArrayIndexNode_ = ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).insertAccessor(
                  ToArrayIndexNode.create()
               );
               ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).getType_ = ConditionProfile.createBinaryProfile();
               ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).jsclassProfile_ = JSClassProfile.create();
               ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).highFrequency_ = ConditionProfile.createBinaryProfile();
               ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).hotKey_ = ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).insertAccessor(
                  FrequencyBasedPolymorphicAccessNode.createFrequencyBasedPropertySet(this.context, this.setOwn, this.strict, this.superProperty)
               );
               ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).equalsNode_ = ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).insertAccessor(
                  TruffleString.EqualNode.create()
               );
               VarHandle.storeStoreFence();
               this.generic_cache = (CachedSetPropertyNodeGen.GenericData)maybeIndex__;
               int var27;
               this.exclude_ = var27 = exclude | 15;
               this.cachedKey_cache = null;
               this.arrayIndex_cache = null;
               state_0 &= -16;
               int var25;
               this.state_0_ = var25 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               this.doGeneric(
                  arg0Value,
                  arg1Value,
                  arg2Value,
                  arg3Value,
                  ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).toArrayIndexNode_,
                  ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).getType_,
                  ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).jsclassProfile_,
                  ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).highFrequency_,
                  ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).hotKey_,
                  ((CachedSetPropertyNodeGen.GenericData)maybeIndex__).equalsNode_
               );
            } else {
               this.proxy_proxySet_ = super.insert(JSProxyPropertySetNode.create(this.context, this.strict));
               int var23;
               this.state_0_ = var23 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               this.doProxy(arg0Value, arg1Value, arg2Value, arg3Value, this.proxy_proxySet_);
            }
         } finally {
            if (oldState_0 != 0) {
               this.checkForPolymorphicSpecialize(oldState_0);
            }
         }
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }
   }

   private void checkForPolymorphicSpecialize(int oldState_0) {
      if ((oldState_0 & 16) == 0 && (this.state_0_ & 16) != 0) {
         this.reportPolymorphicSpecialize();
      }
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & state_0 - 1) == 0) {
            CachedSetPropertyNodeGen.CachedKeyData s0_ = this.cachedKey_cache;
            CachedSetPropertyNodeGen.ArrayIndexData s2_ = this.arrayIndex_cache;
            if ((s0_ == null || s0_.next_ == null) && (s2_ == null || s2_.next_ == null)) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[6];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doCachedKey", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (CachedSetPropertyNodeGen.CachedKeyData s0_ = this.cachedKey_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.cachedKey_, s0_.propertyNode_, s0_.equalsNode_));
         }

         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doIntIndex", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.intIndex_jsclassProfile_));
         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doArrayIndex", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (CachedSetPropertyNodeGen.ArrayIndexData s2_ = this.arrayIndex_cache; s2_ != null; s2_ = s2_.next_) {
            cached.add(Arrays.asList(s2_.toArrayIndexNode_, s2_.jsclassProfile_));
         }

         s[2] = cached;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doProxy", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.proxy_proxySet_));
         s[2] = cached;
      } else if ((exclude & 8) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doGeneric", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         CachedSetPropertyNodeGen.GenericData s4_ = this.generic_cache;
         if (s4_ != null) {
            cached.add(Arrays.asList(s4_.toArrayIndexNode_, s4_.getType_, s4_.jsclassProfile_, s4_.highFrequency_, s4_.hotKey_, s4_.equalsNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      return Introspection.Provider.create(data);
   }

   public static CachedSetPropertyNode create(JSContext context, boolean strict, boolean setOwn, boolean superProperty) {
      return new CachedSetPropertyNodeGen(context, strict, setOwn, superProperty);
   }

   @GeneratedBy(CachedSetPropertyNode.class)
   private static final class ArrayIndexData extends Node {
      @Node.Child
      CachedSetPropertyNodeGen.ArrayIndexData next_;
      @Node.Child
      ToArrayIndexNode toArrayIndexNode_;
      @CompilerDirectives.CompilationFinal
      JSClassProfile jsclassProfile_;

      ArrayIndexData(CachedSetPropertyNodeGen.ArrayIndexData next_) {
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

   @GeneratedBy(CachedSetPropertyNode.class)
   private static final class CachedKeyData extends Node {
      @Node.Child
      CachedSetPropertyNodeGen.CachedKeyData next_;
      @CompilerDirectives.CompilationFinal
      Object cachedKey_;
      @Node.Child
      PropertySetNode propertyNode_;
      @Node.Child
      TruffleString.EqualNode equalsNode_;

      CachedKeyData(CachedSetPropertyNodeGen.CachedKeyData next_) {
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

   @GeneratedBy(CachedSetPropertyNode.class)
   private static final class GenericData extends Node {
      @Node.Child
      ToArrayIndexNode toArrayIndexNode_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile getType_;
      @CompilerDirectives.CompilationFinal
      JSClassProfile jsclassProfile_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile highFrequency_;
      @Node.Child
      FrequencyBasedPolymorphicAccessNode.FrequencyBasedPropertySetNode hotKey_;
      @Node.Child
      TruffleString.EqualNode equalsNode_;

      GenericData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }
}
