package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.cast.ToArrayIndexNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSGetOwnPropertyNode.class)
public final class JSGetOwnPropertyNodeGen extends JSGetOwnPropertyNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode usesOrdinaryGetOwnProperty;
   @Node.Child
   private JSGetOwnPropertyNodeGen.ArrayData array_cache;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile getOwnPropertyString_stringCaseProfile_;
   @Node.Child
   private JSGetOwnPropertyNodeGen.CachedOrdinaryData cachedOrdinary_cache;
   @CompilerDirectives.CompilationFinal
   private JSClassProfile generic_jsclassProfile_;

   private JSGetOwnPropertyNodeGen(boolean needValue, boolean needEnumerability, boolean needConfigurability, boolean needWritability, boolean allowCaching) {
      super(needValue, needEnumerability, needConfigurability, needWritability, allowCaching);
   }

   @ExplodeLoop
   @Override
   public PropertyDescriptor execute(JSDynamicObject arg0Value, Object arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            JSGetOwnPropertyNodeGen.ArrayData s0_ = this.array_cache;
            if (s0_ != null && JSGuards.isJSArray(arg0Value)) {
               return this.array(arg0Value, arg1Value, s0_.toArrayIndexNode_, s0_.noSuchElementBranch_, s0_.typeProfile_);
            }
         }

         if ((state_0 & 2) != 0 && JSGuards.isJSString(arg0Value)) {
            return this.getOwnPropertyString(arg0Value, arg1Value, this.getOwnPropertyString_stringCaseProfile_);
         }

         if ((state_0 & 4) != 0) {
            assert this.allowCaching;

            for (JSGetOwnPropertyNodeGen.CachedOrdinaryData s2_ = this.cachedOrdinary_cache; s2_ != null; s2_ = s2_.next_) {
               if (!Assumption.isValidAssumption(s2_.assumption0_)) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.removeCachedOrdinary_(s2_);
                  return this.executeAndSpecialize(arg0Value, arg1Value);
               }

               assert s2_.cachedJSClass_ != null;

               if (JSRuntime.propertyKeyEquals(s2_.equalsNode_, s2_.cachedPropertyKey_, arg1Value) && s2_.cachedShape_ == arg0Value.getShape()) {
                  return this.cachedOrdinary(
                     arg0Value, arg1Value, s2_.cachedJSClass_, s2_.cachedShape_, s2_.cachedPropertyKey_, s2_.cachedProperty_, s2_.equalsNode_
                  );
               }
            }
         }

         if ((state_0 & 8) != 0 && this.usesOrdinaryGetOwnProperty.execute(arg0Value)) {
            return this.uncachedOrdinary(arg0Value, arg1Value, this.usesOrdinaryGetOwnProperty);
         }

         if ((state_0 & 16) != 0 && !this.usesOrdinaryGetOwnProperty.execute(arg0Value) && !JSGuards.isJSArray(arg0Value) && !JSGuards.isJSString(arg0Value)) {
            return JSGetOwnPropertyNode.generic(arg0Value, arg1Value, this.generic_jsclassProfile_, this.usesOrdinaryGetOwnProperty);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private PropertyDescriptor executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (JSGuards.isJSArray(arg0Value)) {
            JSGetOwnPropertyNodeGen.ArrayData s0_ = super.insert(new JSGetOwnPropertyNodeGen.ArrayData());
            s0_.toArrayIndexNode_ = s0_.insertAccessor(ToArrayIndexNode.create());
            s0_.noSuchElementBranch_ = BranchProfile.create();
            s0_.typeProfile_ = ValueProfile.createIdentityProfile();
            VarHandle.storeStoreFence();
            this.array_cache = s0_;
            int var20;
            this.state_0_ = var20 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.array(arg0Value, arg1Value, s0_.toArrayIndexNode_, s0_.noSuchElementBranch_, s0_.typeProfile_);
         } else if (JSGuards.isJSString(arg0Value)) {
            this.getOwnPropertyString_stringCaseProfile_ = ConditionProfile.createBinaryProfile();
            int var19;
            this.state_0_ = var19 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.getOwnPropertyString(arg0Value, arg1Value, this.getOwnPropertyString_stringCaseProfile_);
         } else {
            if (exclude == 0 && this.allowCaching) {
               int count2_ = 0;
               JSGetOwnPropertyNodeGen.CachedOrdinaryData s2_ = this.cachedOrdinary_cache;
               if ((state_0 & 4) != 0) {
                  while (s2_ != null) {
                     assert s2_.cachedJSClass_ != null;

                     if (JSRuntime.propertyKeyEquals(s2_.equalsNode_, s2_.cachedPropertyKey_, arg1Value)
                        && s2_.cachedShape_ == arg0Value.getShape()
                        && Assumption.isValidAssumption(s2_.assumption0_)) {
                        break;
                     }

                     s2_ = s2_.next_;
                     count2_++;
                  }
               }

               if (s2_ == null) {
                  JSClass cachedJSClass__ = JSGetOwnPropertyNode.getJSClassIfOrdinary(arg0Value);
                  if (cachedJSClass__ != null) {
                     TruffleString.EqualNode equalsNode__ = super.insert(TruffleString.EqualNode.create());
                     if (JSRuntime.propertyKeyEquals(equalsNode__, arg1Value, arg1Value)) {
                        Shape cachedShape__ = arg0Value.getShape();
                        if (cachedShape__ == arg0Value.getShape()) {
                           Assumption assumption0 = cachedShape__.getValidAssumption();
                           if (Assumption.isValidAssumption(assumption0) && count2_ < 3) {
                              s2_ = super.insert(new JSGetOwnPropertyNodeGen.CachedOrdinaryData(this.cachedOrdinary_cache));
                              s2_.cachedJSClass_ = cachedJSClass__;
                              s2_.cachedShape_ = cachedShape__;
                              s2_.cachedPropertyKey_ = arg1Value;
                              s2_.cachedProperty_ = cachedShape__.getProperty(arg1Value);
                              s2_.equalsNode_ = s2_.insertAccessor(equalsNode__);
                              s2_.assumption0_ = assumption0;
                              VarHandle.storeStoreFence();
                              this.cachedOrdinary_cache = s2_;
                              this.state_0_ = state_0 |= 4;
                           }
                        }
                     }
                  }
               }

               if (s2_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.cachedOrdinary(
                     arg0Value, arg1Value, s2_.cachedJSClass_, s2_.cachedShape_, s2_.cachedPropertyKey_, s2_.cachedProperty_, s2_.equalsNode_
                  );
               }
            }

            boolean UncachedOrdinary_duplicateFound_ = false;
            if ((state_0 & 8) != 0 && this.usesOrdinaryGetOwnProperty.execute(arg0Value)) {
               UncachedOrdinary_duplicateFound_ = true;
            }

            if (!UncachedOrdinary_duplicateFound_) {
               JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode uncachedOrdinary_usesOrdinaryGetOwnProperty__ = super.insert(
                  this.usesOrdinaryGetOwnProperty == null ? JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.create() : this.usesOrdinaryGetOwnProperty
               );
               if (uncachedOrdinary_usesOrdinaryGetOwnProperty__.execute(arg0Value) && (state_0 & 8) == 0) {
                  if (this.usesOrdinaryGetOwnProperty == null) {
                     JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode uncachedOrdinary_usesOrdinaryGetOwnProperty___check = super.insert(
                        uncachedOrdinary_usesOrdinaryGetOwnProperty__
                     );
                     if (uncachedOrdinary_usesOrdinaryGetOwnProperty___check == null) {
                        throw new AssertionError(
                           "Specialization 'uncachedOrdinary(JSDynamicObject, Object, UsesOrdinaryGetOwnPropertyNode)' contains a shared cache with name 'usesOrdinaryGetOwnProperty' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                        );
                     }

                     this.usesOrdinaryGetOwnProperty = uncachedOrdinary_usesOrdinaryGetOwnProperty___check;
                  }

                  int var21;
                  this.exclude_ = var21 = exclude | 1;
                  this.cachedOrdinary_cache = null;
                  int var17 = state_0 & -5;
                  this.state_0_ = state_0 = var17 | 8;
                  UncachedOrdinary_duplicateFound_ = true;
               }
            }

            if (!UncachedOrdinary_duplicateFound_) {
               boolean Generic_duplicateFound_ = false;
               if ((state_0 & 16) != 0
                  && !this.usesOrdinaryGetOwnProperty.execute(arg0Value)
                  && !JSGuards.isJSArray(arg0Value)
                  && !JSGuards.isJSString(arg0Value)) {
                  Generic_duplicateFound_ = true;
               }

               if (!Generic_duplicateFound_) {
                  JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode generic_usesOrdinaryGetOwnProperty__ = super.insert(
                     this.usesOrdinaryGetOwnProperty == null ? JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.create() : this.usesOrdinaryGetOwnProperty
                  );
                  if (!generic_usesOrdinaryGetOwnProperty__.execute(arg0Value)
                     && !JSGuards.isJSArray(arg0Value)
                     && !JSGuards.isJSString(arg0Value)
                     && (state_0 & 16) == 0) {
                     this.generic_jsclassProfile_ = JSClassProfile.create();
                     if (this.usesOrdinaryGetOwnProperty == null) {
                        JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode generic_usesOrdinaryGetOwnProperty___check = super.insert(
                           generic_usesOrdinaryGetOwnProperty__
                        );
                        if (generic_usesOrdinaryGetOwnProperty___check == null) {
                           throw new AssertionError(
                              "Specialization 'generic(JSDynamicObject, Object, JSClassProfile, UsesOrdinaryGetOwnPropertyNode)' contains a shared cache with name 'usesOrdinaryGetOwnProperty' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.usesOrdinaryGetOwnProperty = generic_usesOrdinaryGetOwnProperty___check;
                     }

                     int var18;
                     this.state_0_ = var18 = state_0 | 16;
                     Generic_duplicateFound_ = true;
                  }
               }

               if (!Generic_duplicateFound_) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
               } else {
                  lock.unlock();
                  hasLock = false;
                  return JSGetOwnPropertyNode.generic(arg0Value, arg1Value, this.generic_jsclassProfile_, this.usesOrdinaryGetOwnProperty);
               }
            } else {
               lock.unlock();
               hasLock = false;
               return this.uncachedOrdinary(arg0Value, arg1Value, this.usesOrdinaryGetOwnProperty);
            }
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
            JSGetOwnPropertyNodeGen.CachedOrdinaryData s2_ = this.cachedOrdinary_cache;
            if (s2_ == null || s2_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   void removeCachedOrdinary_(Object s2_) {
      Lock lock = this.getLock();
      lock.lock();

      try {
         JSGetOwnPropertyNodeGen.CachedOrdinaryData prev = null;

         for (JSGetOwnPropertyNodeGen.CachedOrdinaryData cur = this.cachedOrdinary_cache; cur != null; cur = cur.next_) {
            if (cur == s2_) {
               if (prev == null) {
                  this.cachedOrdinary_cache = cur.next_;
                  this.adoptChildren();
               } else {
                  prev.next_ = cur.next_;
                  prev.adoptChildren();
               }
               break;
            }

            prev = cur;
         }

         if (this.cachedOrdinary_cache == null) {
            this.state_0_ &= -5;
         }
      } finally {
         lock.unlock();
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[6];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"array", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSGetOwnPropertyNodeGen.ArrayData s0_ = this.array_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.toArrayIndexNode_, s0_.noSuchElementBranch_, s0_.typeProfile_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"getOwnPropertyString", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.getOwnPropertyString_stringCaseProfile_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"cachedOrdinary", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSGetOwnPropertyNodeGen.CachedOrdinaryData s2_ = this.cachedOrdinary_cache; s2_ != null; s2_ = s2_.next_) {
            cached.add(Arrays.asList(s2_.cachedJSClass_, s2_.cachedShape_, s2_.cachedPropertyKey_, s2_.cachedProperty_, s2_.equalsNode_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"uncachedOrdinary", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.usesOrdinaryGetOwnProperty));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"generic", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.generic_jsclassProfile_, this.usesOrdinaryGetOwnProperty));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      return Introspection.Provider.create(data);
   }

   public static JSGetOwnPropertyNode create(
      boolean needValue, boolean needEnumerability, boolean needConfigurability, boolean needWritability, boolean allowCaching
   ) {
      return new JSGetOwnPropertyNodeGen(needValue, needEnumerability, needConfigurability, needWritability, allowCaching);
   }

   @GeneratedBy(JSGetOwnPropertyNode.class)
   private static final class ArrayData extends Node {
      @Node.Child
      ToArrayIndexNode toArrayIndexNode_;
      @CompilerDirectives.CompilationFinal
      BranchProfile noSuchElementBranch_;
      @CompilerDirectives.CompilationFinal
      ValueProfile typeProfile_;

      ArrayData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(JSGetOwnPropertyNode.class)
   private static final class CachedOrdinaryData extends Node {
      @Node.Child
      JSGetOwnPropertyNodeGen.CachedOrdinaryData next_;
      @CompilerDirectives.CompilationFinal
      JSClass cachedJSClass_;
      @CompilerDirectives.CompilationFinal
      Shape cachedShape_;
      @CompilerDirectives.CompilationFinal
      Object cachedPropertyKey_;
      @CompilerDirectives.CompilationFinal
      Property cachedProperty_;
      @Node.Child
      TruffleString.EqualNode equalsNode_;
      @CompilerDirectives.CompilationFinal
      Assumption assumption0_;

      CachedOrdinaryData(JSGetOwnPropertyNodeGen.CachedOrdinaryData next_) {
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

   @GeneratedBy(JSGetOwnPropertyNode.GetPropertyProxyValueNode.class)
   public static final class GetPropertyProxyValueNodeGen extends JSGetOwnPropertyNode.GetPropertyProxyValueNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private JSGetOwnPropertyNodeGen.GetPropertyProxyValueNodeGen.CachedData cached_cache;

      private GetPropertyProxyValueNodeGen() {
      }

      @ExplodeLoop
      @Override
      public Object execute(JSDynamicObject arg0Value, Object arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (JSGetOwnPropertyNodeGen.GetPropertyProxyValueNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg1Value.getClass() == s0_.cachedClass_) {
                     return JSGetOwnPropertyNode.GetPropertyProxyValueNode.doCached(arg0Value, arg1Value, s0_.cachedClass_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return JSGetOwnPropertyNode.GetPropertyProxyValueNode.doUncached(arg0Value, arg1Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private Object executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               JSGetOwnPropertyNodeGen.GetPropertyProxyValueNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && arg1Value.getClass() != s0_.cachedClass_) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  Class<?> cachedClass__ = arg1Value.getClass();
                  if (arg1Value.getClass() == cachedClass__ && count0_ < 5) {
                     s0_ = new JSGetOwnPropertyNodeGen.GetPropertyProxyValueNodeGen.CachedData(this.cached_cache);
                     s0_.cachedClass_ = cachedClass__;
                     VarHandle.storeStoreFence();
                     this.cached_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return JSGetOwnPropertyNode.GetPropertyProxyValueNode.doCached(arg0Value, arg1Value, s0_.cachedClass_);
               }
            }

            int var15;
            this.exclude_ = var15 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var14;
            this.state_0_ = var14 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return JSGetOwnPropertyNode.GetPropertyProxyValueNode.doUncached(arg0Value, arg1Value);
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
               JSGetOwnPropertyNodeGen.GetPropertyProxyValueNodeGen.CachedData s0_ = this.cached_cache;
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

            for (JSGetOwnPropertyNodeGen.GetPropertyProxyValueNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               cached.add(Arrays.asList(s0_.cachedClass_));
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

      public static JSGetOwnPropertyNode.GetPropertyProxyValueNode create() {
         return new JSGetOwnPropertyNodeGen.GetPropertyProxyValueNodeGen();
      }

      @GeneratedBy(JSGetOwnPropertyNode.GetPropertyProxyValueNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         JSGetOwnPropertyNodeGen.GetPropertyProxyValueNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         Class<?> cachedClass_;

         CachedData(JSGetOwnPropertyNodeGen.GetPropertyProxyValueNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }
   }

   @GeneratedBy(JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.class)
   public static final class UsesOrdinaryGetOwnPropertyNodeGen extends JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private JSGetOwnPropertyNodeGen.UsesOrdinaryGetOwnPropertyNodeGen.CachedData cached_cache;

      private UsesOrdinaryGetOwnPropertyNodeGen() {
      }

      @ExplodeLoop
      @Override
      public boolean execute(Object arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (JSGetOwnPropertyNodeGen.UsesOrdinaryGetOwnPropertyNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (JSGuards.isReferenceEquals(arg0Value, s0_.cachedJSClass_)) {
                     return JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.doCached(arg0Value, s0_.cachedJSClass_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.doObjectPrototype(arg0Value);
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
               int count0_ = 0;
               JSGetOwnPropertyNodeGen.UsesOrdinaryGetOwnPropertyNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && !JSGuards.isReferenceEquals(arg0Value, s0_.cachedJSClass_)) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  JSClass cachedJSClass__ = JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.asJSClass(arg0Value);
                  if (JSGuards.isReferenceEquals(arg0Value, cachedJSClass__) && count0_ < 7) {
                     s0_ = new JSGetOwnPropertyNodeGen.UsesOrdinaryGetOwnPropertyNodeGen.CachedData(this.cached_cache);
                     s0_.cachedJSClass_ = cachedJSClass__;
                     VarHandle.storeStoreFence();
                     this.cached_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.doCached(arg0Value, s0_.cachedJSClass_);
               }
            }

            int var14;
            this.exclude_ = var14 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var13;
            this.state_0_ = var13 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.doObjectPrototype(arg0Value);
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
               JSGetOwnPropertyNodeGen.UsesOrdinaryGetOwnPropertyNodeGen.CachedData s0_ = this.cached_cache;
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

            for (JSGetOwnPropertyNodeGen.UsesOrdinaryGetOwnPropertyNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               cached.add(Arrays.asList(s0_.cachedJSClass_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doObjectPrototype", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode create() {
         return new JSGetOwnPropertyNodeGen.UsesOrdinaryGetOwnPropertyNodeGen();
      }

      @GeneratedBy(JSGetOwnPropertyNode.UsesOrdinaryGetOwnPropertyNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         JSGetOwnPropertyNodeGen.UsesOrdinaryGetOwnPropertyNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         JSClass cachedJSClass_;

         CachedData(JSGetOwnPropertyNodeGen.UsesOrdinaryGetOwnPropertyNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }
   }
}
