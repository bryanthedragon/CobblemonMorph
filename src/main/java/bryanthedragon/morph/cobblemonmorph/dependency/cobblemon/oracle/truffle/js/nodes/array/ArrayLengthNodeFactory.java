package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBase;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ArrayLengthNode.class)
public final class ArrayLengthNodeFactory {
   @GeneratedBy(ArrayLengthNode.ArrayLengthReadNode.class)
   public static final class ArrayLengthReadNodeGen extends ArrayLengthNode.ArrayLengthReadNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private ScriptArray intLength_arrayType_;

      private ArrayLengthReadNodeGen() {
      }

      @Override
      public Object executeObject(JSDynamicObject arg0Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0 && arg0Value instanceof JSTypedArrayObject) {
            JSTypedArrayObject arg0Value_ = (JSTypedArrayObject)arg0Value;
            return ArrayLengthNode.ArrayLengthReadNode.doTypedArray(arg0Value_);
         } else {
            if ((state_0 & 14) != 0 && arg0Value instanceof JSArrayBase) {
               JSArrayBase arg0Value_ = (JSArrayBase)arg0Value;
               if ((state_0 & 2) != 0 && this.intLength_arrayType_.isInstance(arg0Value_.getArrayType())) {
                  assert ArrayLengthNode.ArrayLengthReadNode.isLengthAlwaysInt(this.intLength_arrayType_);

                  return ArrayLengthNode.ArrayLengthReadNode.doIntLength(arg0Value_, this.intLength_arrayType_);
               }

               if ((state_0 & 4) != 0) {
                  try {
                     return ArrayLengthNode.ArrayLengthReadNode.doUncachedIntLength(arg0Value_);
                  } catch (UnexpectedResultException var10) {
                     CompilerDirectives.transferToInterpreterAndInvalidate();
                     Lock lock = this.getLock();
                     lock.lock();

                     try {
                        this.exclude_ |= 2;
                        this.state_0_ &= -5;
                     } finally {
                        lock.unlock();
                     }

                     return var10.getResult();
                  }
               }

               if ((state_0 & 8) != 0) {
                  return ArrayLengthNode.ArrayLengthReadNode.doUncachedLongLength(arg0Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }

      @Override
      public int executeInt(JSDynamicObject arg0Value) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0 && arg0Value instanceof JSTypedArrayObject) {
            JSTypedArrayObject arg0Value_ = (JSTypedArrayObject)arg0Value;
            return ArrayLengthNode.ArrayLengthReadNode.doTypedArray(arg0Value_);
         } else {
            if ((state_0 & 6) != 0 && arg0Value instanceof JSArrayBase) {
               JSArrayBase arg0Value_ = (JSArrayBase)arg0Value;
               if ((state_0 & 2) != 0 && this.intLength_arrayType_.isInstance(arg0Value_.getArrayType())) {
                  assert ArrayLengthNode.ArrayLengthReadNode.isLengthAlwaysInt(this.intLength_arrayType_);

                  return ArrayLengthNode.ArrayLengthReadNode.doIntLength(arg0Value_, this.intLength_arrayType_);
               }

               if ((state_0 & 4) != 0) {
                  try {
                     return ArrayLengthNode.ArrayLengthReadNode.doUncachedIntLength(arg0Value_);
                  } catch (UnexpectedResultException var10) {
                     CompilerDirectives.transferToInterpreterAndInvalidate();
                     Lock lock = this.getLock();
                     lock.lock();

                     try {
                        this.exclude_ |= 2;
                        this.state_0_ &= -5;
                     } finally {
                        lock.unlock();
                     }

                     return JSTypesGen.expectInteger(var10.getResult());
                  }
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arg0Value));
         }
      }

      private Object executeAndSpecialize(JSDynamicObject arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         ScriptArray intLength_arrayType__;
         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof JSTypedArrayObject) {
               JSTypedArrayObject arg0Value_ = (JSTypedArrayObject)arg0Value;
               int var24;
               this.state_0_ = var24 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return ArrayLengthNode.ArrayLengthReadNode.doTypedArray(arg0Value_);
            }

            if (!(arg0Value instanceof JSArrayBase)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            JSArrayBase arg0Value_ = (JSArrayBase)arg0Value;
            if ((exclude & 1) == 0) {
               boolean IntLength_duplicateFound_ = false;
               if ((state_0 & 2) != 0 && this.intLength_arrayType_.isInstance(arg0Value_.getArrayType())) {
                  assert ArrayLengthNode.ArrayLengthReadNode.isLengthAlwaysInt(this.intLength_arrayType_);

                  IntLength_duplicateFound_ = true;
               }

               if (!IntLength_duplicateFound_) {
                  intLength_arrayType__ = ArrayLengthNode.getArrayType(arg0Value_);
                  if (intLength_arrayType__.isInstance(arg0Value_.getArrayType())
                     && ArrayLengthNode.ArrayLengthReadNode.isLengthAlwaysInt(intLength_arrayType__)
                     && (state_0 & 2) == 0) {
                     this.intLength_arrayType_ = intLength_arrayType__;
                     this.state_0_ = state_0 |= 2;
                     IntLength_duplicateFound_ = true;
                  }
               }

               if (IntLength_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return ArrayLengthNode.ArrayLengthReadNode.doIntLength(arg0Value_, this.intLength_arrayType_);
               }
            }

            if ((exclude & 2) != 0) {
               int var26;
               this.exclude_ = var26 = exclude | 3;
               state_0 &= -7;
               int var23;
               this.state_0_ = var23 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return ArrayLengthNode.ArrayLengthReadNode.doUncachedLongLength(arg0Value_);
            }

            int var25;
            this.exclude_ = var25 = exclude | 1;
            state_0 &= -3;
            int var21;
            this.state_0_ = var21 = state_0 | 4;

            try {
               lock.unlock();
               hasLock = false;
               return ArrayLengthNode.ArrayLengthReadNode.doUncachedIntLength(arg0Value_);
            } catch (UnexpectedResultException var18) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               lock.lock();

               try {
                  this.exclude_ |= 2;
                  this.state_0_ &= -5;
               } finally {
                  lock.unlock();
               }

               intLength_arrayType__ = (ScriptArray)var18.getResult();
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return intLength_arrayType__;
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
         Object[] data = new Object[5];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"doTypedArray", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doIntLength", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.intLength_arrayType_));
            s[2] = cached;
         } else if ((exclude & 1) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doUncachedIntLength", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else if ((exclude & 2) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"doUncachedLongLength", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         return Introspection.Provider.create(data);
      }

      public static ArrayLengthNode.ArrayLengthReadNode create() {
         return new ArrayLengthNodeFactory.ArrayLengthReadNodeGen();
      }
   }

   @GeneratedBy(ArrayLengthNode.SetArrayLengthNode.class)
   public static final class SetArrayLengthNodeGen extends ArrayLengthNode.SetArrayLengthNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private ArrayLengthNodeFactory.SetArrayLengthNodeGen.CachedData cached_cache;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile generic_sealedProfile_;
      @CompilerDirectives.CompilationFinal
      private ScriptArray.ProfileHolder generic_setLengthProfile_;

      private SetArrayLengthNodeGen(boolean strict) {
         super(strict);
      }

      @ExplodeLoop
      @Override
      public void executeVoid(JSDynamicObject arg0Value, int arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (ArrayLengthNodeFactory.SetArrayLengthNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.arrayType_.isInstance(ArrayLengthNode.getArrayType(arg0Value))) {
                     this.doCached(arg0Value, arg1Value, s0_.arrayType_, s0_.setLengthProfile_);
                     return;
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               this.doGeneric(arg0Value, arg1Value, this.generic_sealedProfile_, this.generic_setLengthProfile_);
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private void executeAndSpecialize(JSDynamicObject arg0Value, int arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               ArrayLengthNodeFactory.SetArrayLengthNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && !s0_.arrayType_.isInstance(ArrayLengthNode.getArrayType(arg0Value))) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  ScriptArray arrayType__ = ArrayLengthNode.getArrayType(arg0Value);
                  if (arrayType__.isInstance(ArrayLengthNode.getArrayType(arg0Value)) && count0_ < 4) {
                     s0_ = new ArrayLengthNodeFactory.SetArrayLengthNodeGen.CachedData(this.cached_cache);
                     s0_.arrayType_ = arrayType__;
                     s0_.setLengthProfile_ = ScriptArray.createSetLengthProfile();
                     VarHandle.storeStoreFence();
                     this.cached_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  this.doCached(arg0Value, arg1Value, s0_.arrayType_, s0_.setLengthProfile_);
                  return;
               }
            }

            this.generic_sealedProfile_ = ConditionProfile.createBinaryProfile();
            this.generic_setLengthProfile_ = ScriptArray.createSetLengthProfile();
            int var15;
            this.exclude_ = var15 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var14;
            this.state_0_ = var14 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            this.doGeneric(arg0Value, arg1Value, this.generic_sealedProfile_, this.generic_setLengthProfile_);
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
               ArrayLengthNodeFactory.SetArrayLengthNodeGen.CachedData s0_ = this.cached_cache;
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

            for (ArrayLengthNodeFactory.SetArrayLengthNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               cached.add(Arrays.asList(s0_.arrayType_, s0_.setLengthProfile_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doGeneric", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.generic_sealedProfile_, this.generic_setLengthProfile_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ArrayLengthNode.SetArrayLengthNode create(boolean strict) {
         return new ArrayLengthNodeFactory.SetArrayLengthNodeGen(strict);
      }

      @GeneratedBy(ArrayLengthNode.SetArrayLengthNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         ArrayLengthNodeFactory.SetArrayLengthNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         ScriptArray arrayType_;
         @CompilerDirectives.CompilationFinal
         ScriptArray.ProfileHolder setLengthProfile_;

         CachedData(ArrayLengthNodeFactory.SetArrayLengthNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }
   }

   @GeneratedBy(ArrayLengthNode.SetArrayLengthOrDeleteNode.class)
   public static final class SetArrayLengthOrDeleteNodeGen extends ArrayLengthNode.SetArrayLengthOrDeleteNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private ArrayLengthNodeFactory.SetArrayLengthOrDeleteNodeGen.CachedData cached_cache;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile generic_mustDeleteProfile_;
      @CompilerDirectives.CompilationFinal
      private ScriptArray.ProfileHolder generic_setLengthProfile_;

      private SetArrayLengthOrDeleteNodeGen(boolean strict) {
         super(strict);
      }

      @ExplodeLoop
      @Override
      public void executeVoid(JSDynamicObject arg0Value, int arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (ArrayLengthNodeFactory.SetArrayLengthOrDeleteNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.arrayType_.isInstance(ArrayLengthNode.getArrayType(arg0Value))) {
                     this.doCached(arg0Value, arg1Value, s0_.arrayType_, s0_.setLengthProfile_);
                     return;
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               this.doGeneric(arg0Value, arg1Value, this.generic_mustDeleteProfile_, this.generic_setLengthProfile_);
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private void executeAndSpecialize(JSDynamicObject arg0Value, int arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               ArrayLengthNodeFactory.SetArrayLengthOrDeleteNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && !s0_.arrayType_.isInstance(ArrayLengthNode.getArrayType(arg0Value))) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  ScriptArray arrayType__ = ArrayLengthNode.getArrayType(arg0Value);
                  if (arrayType__.isInstance(ArrayLengthNode.getArrayType(arg0Value)) && count0_ < 4) {
                     s0_ = new ArrayLengthNodeFactory.SetArrayLengthOrDeleteNodeGen.CachedData(this.cached_cache);
                     s0_.arrayType_ = arrayType__;
                     s0_.setLengthProfile_ = ScriptArray.createSetLengthProfile();
                     VarHandle.storeStoreFence();
                     this.cached_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  this.doCached(arg0Value, arg1Value, s0_.arrayType_, s0_.setLengthProfile_);
                  return;
               }
            }

            this.generic_mustDeleteProfile_ = ConditionProfile.createBinaryProfile();
            this.generic_setLengthProfile_ = ScriptArray.createSetLengthProfile();
            int var15;
            this.exclude_ = var15 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var14;
            this.state_0_ = var14 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            this.doGeneric(arg0Value, arg1Value, this.generic_mustDeleteProfile_, this.generic_setLengthProfile_);
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
               ArrayLengthNodeFactory.SetArrayLengthOrDeleteNodeGen.CachedData s0_ = this.cached_cache;
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

            for (ArrayLengthNodeFactory.SetArrayLengthOrDeleteNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               cached.add(Arrays.asList(s0_.arrayType_, s0_.setLengthProfile_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doGeneric", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.generic_mustDeleteProfile_, this.generic_setLengthProfile_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ArrayLengthNode.SetArrayLengthOrDeleteNode create(boolean strict) {
         return new ArrayLengthNodeFactory.SetArrayLengthOrDeleteNodeGen(strict);
      }

      @GeneratedBy(ArrayLengthNode.SetArrayLengthOrDeleteNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         ArrayLengthNodeFactory.SetArrayLengthOrDeleteNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         ScriptArray arrayType_;
         @CompilerDirectives.CompilationFinal
         ScriptArray.ProfileHolder setLengthProfile_;

         CachedData(ArrayLengthNodeFactory.SetArrayLengthOrDeleteNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }
   }
}
