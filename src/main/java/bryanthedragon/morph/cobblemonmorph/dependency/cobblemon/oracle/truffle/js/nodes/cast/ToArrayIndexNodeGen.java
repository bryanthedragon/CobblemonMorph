package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ToArrayIndexNode.class)
public final class ToArrayIndexNodeGen extends ToArrayIndexNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private ToArrayIndexNodeGen.ConvertFromStringData convertFromString_cache;
   @Node.Child
   private ToArrayIndexNodeGen.InteropArrayIndex0Data interopArrayIndex0_cache;
   @Node.Child
   private ToArrayIndexNodeGen.NonArrayIndex0Data nonArrayIndex0_cache;
   @Node.Child
   private JSToPropertyKeyNode nonArrayIndex1_toPropertyKey_;
   @Node.Child
   private ToArrayIndexNode nonArrayIndex1_recursive_;

   private ToArrayIndexNodeGen(boolean convertToPropertyKey, boolean convertStringToIndex) {
      super(convertToPropertyKey, convertStringToIndex);
   }

   @ExplodeLoop
   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         if (JSGuards.isIntArrayIndex(arg0Value_)) {
            return ToArrayIndexNode.doInteger(arg0Value_);
         }
      }

      if ((state_0 & 2) != 0 && arg0Value instanceof Long) {
         long arg0Value_ = (Long)arg0Value;
         if (JSGuards.isLongArrayIndex(arg0Value_)) {
            return ToArrayIndexNode.doLong(arg0Value_);
         }
      }

      if ((state_0 & 12) != 0 && JSTypesGen.isImplicitDouble((state_0 & 61440) >>> 12, arg0Value)) {
         double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 61440) >>> 12, arg0Value);
         if ((state_0 & 4) != 0 && ToArrayIndexNode.doubleIsIntIndex(arg0Value_)) {
            return ToArrayIndexNode.doDoubleAsIntIndex(arg0Value_);
         }

         if ((state_0 & 8) != 0 && ToArrayIndexNode.doubleIsUintIndex(arg0Value_)) {
            return ToArrayIndexNode.doDoubleAsUintIndex(arg0Value_);
         }
      }

      if ((state_0 & 16) != 0 && arg0Value instanceof Symbol) {
         Symbol arg0Value_x = (Symbol)arg0Value;
         return ToArrayIndexNode.doSymbol(arg0Value_x);
      } else {
         if ((state_0 & 32) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_x = (BigInt)arg0Value;
            if (JSGuards.isBigIntArrayIndex(arg0Value_x)) {
               return ToArrayIndexNode.doBigInt(arg0Value_x);
            }
         }

         if ((state_0 & 192) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_x = (TruffleString)arg0Value;
            if ((state_0 & 64) != 0) {
               ToArrayIndexNodeGen.ConvertFromStringData s6_ = this.convertFromString_cache;
               if (s6_ != null) {
                  assert this.convertStringToIndex;

                  if (JSRuntime.arrayIndexLengthInRange(arg0Value_x)) {
                     return ToArrayIndexNode.convertFromString(arg0Value_x, s6_.startsWithDigitBranch_, s6_.isArrayIndexBranch_, s6_.stringReadNode_);
                  }
               }
            }

            if ((state_0 & 128) != 0 && (!this.convertStringToIndex || !JSRuntime.arrayIndexLengthInRange(arg0Value_x))) {
               return ToArrayIndexNode.convertFromStringNotInRange(arg0Value_x);
            }
         }

         if ((state_0 & 3840) != 0) {
            if ((state_0 & 256) != 0) {
               for (ToArrayIndexNodeGen.InteropArrayIndex0Data s8_ = this.interopArrayIndex0_cache; s8_ != null; s8_ = s8_.next_) {
                  if (s8_.interop_.accepts(arg0Value) && ToArrayIndexNode.notArrayIndex(arg0Value)) {
                     long index__ = ToArrayIndexNode.toArrayIndex(arg0Value, s8_.interop_);
                     if (index__ >= 0L) {
                        return ToArrayIndexNode.doInteropArrayIndex(arg0Value, s8_.interop_, index__);
                     }
                  }
               }
            }

            if ((state_0 & 512) != 0) {
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (ToArrayIndexNode.notArrayIndex(arg0Value)) {
                     InteropLibrary interopArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached();
                     long interopArrayIndex1_index__ = ToArrayIndexNode.toArrayIndex(arg0Value, interopArrayIndex1_interop__);
                     if (interopArrayIndex1_index__ >= 0L) {
                        return this.interopArrayIndex1Boundary(state_0, arg0Value);
                     }
                  }
               } finally {
                  encapsulating_.set(prev_);
               }
            }

            if ((state_0 & 1024) != 0) {
               for (ToArrayIndexNodeGen.NonArrayIndex0Data s10_ = this.nonArrayIndex0_cache; s10_ != null; s10_ = s10_.next_) {
                  if (s10_.interop_.accepts(arg0Value)
                     && ToArrayIndexNode.notArrayIndex(arg0Value)
                     && ToArrayIndexNode.toArrayIndex(arg0Value, s10_.interop_) < 0L) {
                     return this.doNonArrayIndex(arg0Value, s10_.interop_, s10_.toPropertyKey_, s10_.recursive_);
                  }
               }
            }

            label314:
            if ((state_0 & 2048) != 0) {
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               Object var30;
               try {
                  if (!ToArrayIndexNode.notArrayIndex(arg0Value)) {
                     break label314;
                  }

                  InteropLibrary nonArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached();
                  if (ToArrayIndexNode.toArrayIndex(arg0Value, nonArrayIndex1_interop__) >= 0L) {
                     break label314;
                  }

                  var30 = this.nonArrayIndex1Boundary(state_0, arg0Value);
               } finally {
                  encapsulating_.set(prev_);
               }

               return var30;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object interopArrayIndex1Boundary(int state_0, Object arg0Value) {
      InteropLibrary interopArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached();
      long interopArrayIndex1_index__ = ToArrayIndexNode.toArrayIndex(arg0Value, interopArrayIndex1_interop__);
      return ToArrayIndexNode.doInteropArrayIndex(arg0Value, interopArrayIndex1_interop__, interopArrayIndex1_index__);
   }

   @CompilerDirectives.TruffleBoundary
   private Object nonArrayIndex1Boundary(int state_0, Object arg0Value) {
      InteropLibrary nonArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached();
      return this.doNonArrayIndex(arg0Value, nonArrayIndex1_interop__, this.nonArrayIndex1_toPropertyKey_, this.nonArrayIndex1_recursive_);
   }

   @ExplodeLoop
   @Override
   public long executeLong(Object arg0Value) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 3136) != 0) {
         return JSTypesGen.expectLong(this.execute(arg0Value));
      } else {
         if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            if (JSGuards.isIntArrayIndex(arg0Value_)) {
               return ToArrayIndexNode.doInteger(arg0Value_);
            }
         }

         if ((state_0 & 2) != 0 && arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            if (JSGuards.isLongArrayIndex(arg0Value_)) {
               return ToArrayIndexNode.doLong(arg0Value_);
            }
         }

         if ((state_0 & 12) != 0 && JSTypesGen.isImplicitDouble((state_0 & 61440) >>> 12, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 61440) >>> 12, arg0Value);
            if ((state_0 & 4) != 0 && ToArrayIndexNode.doubleIsIntIndex(arg0Value_)) {
               return ToArrayIndexNode.doDoubleAsIntIndex(arg0Value_);
            }

            if ((state_0 & 8) != 0 && ToArrayIndexNode.doubleIsUintIndex(arg0Value_)) {
               return ToArrayIndexNode.doDoubleAsUintIndex(arg0Value_);
            }
         }

         if ((state_0 & 32) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_x = (BigInt)arg0Value;
            if (JSGuards.isBigIntArrayIndex(arg0Value_x)) {
               return ToArrayIndexNode.doBigInt(arg0Value_x);
            }
         }

         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0) {
               for (ToArrayIndexNodeGen.InteropArrayIndex0Data s8_ = this.interopArrayIndex0_cache; s8_ != null; s8_ = s8_.next_) {
                  if (s8_.interop_.accepts(arg0Value) && ToArrayIndexNode.notArrayIndex(arg0Value)) {
                     long index__ = ToArrayIndexNode.toArrayIndex(arg0Value, s8_.interop_);
                     if (index__ >= 0L) {
                        return ToArrayIndexNode.doInteropArrayIndex(arg0Value, s8_.interop_, index__);
                     }
                  }
               }
            }

            label131:
            if ((state_0 & 512) != 0) {
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               long var8;
               try {
                  if (!ToArrayIndexNode.notArrayIndex(arg0Value)) {
                     break label131;
                  }

                  InteropLibrary interopArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached();
                  long interopArrayIndex1_index__ = ToArrayIndexNode.toArrayIndex(arg0Value, interopArrayIndex1_interop__);
                  if (interopArrayIndex1_index__ < 0L) {
                     break label131;
                  }

                  var8 = this.interopArrayIndex1Boundary0(state_0, arg0Value);
               } finally {
                  encapsulating_.set(prev_);
               }

               return var8;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectLong(this.executeAndSpecialize(arg0Value));
      }
   }

   @CompilerDirectives.TruffleBoundary
   private long interopArrayIndex1Boundary0(int state_0, Object arg0Value) throws UnexpectedResultException {
      InteropLibrary interopArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached();
      long interopArrayIndex1_index__ = ToArrayIndexNode.toArrayIndex(arg0Value, interopArrayIndex1_interop__);
      return ToArrayIndexNode.doInteropArrayIndex(arg0Value, interopArrayIndex1_interop__, interopArrayIndex1_index__);
   }

   private Object executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            if (JSGuards.isIntArrayIndex(arg0Value_)) {
               int var41;
               this.state_0_ = var41 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return ToArrayIndexNode.doInteger(arg0Value_);
            }
         }

         if (arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            if (JSGuards.isLongArrayIndex(arg0Value_)) {
               int var40;
               this.state_0_ = var40 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return ToArrayIndexNode.doLong(arg0Value_);
            }
         }

         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
            double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
            if ((exclude & 1) == 0 && ToArrayIndexNode.doubleIsIntIndex(arg0Value_)) {
               state_0 |= doubleCast0 << 12;
               int var39;
               this.state_0_ = var39 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return ToArrayIndexNode.doDoubleAsIntIndex(arg0Value_);
            }

            if (ToArrayIndexNode.doubleIsUintIndex(arg0Value_)) {
               int var44;
               this.exclude_ = var44 = exclude | 1;
               state_0 &= -5;
               state_0 |= doubleCast0 << 12;
               int var37;
               this.state_0_ = var37 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return ToArrayIndexNode.doDoubleAsUintIndex(arg0Value_);
            }
         }

         if (arg0Value instanceof Symbol) {
            Symbol arg0Value_x = (Symbol)arg0Value;
            int var34;
            this.state_0_ = var34 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return ToArrayIndexNode.doSymbol(arg0Value_x);
         } else {
            if (arg0Value instanceof BigInt) {
               BigInt arg0Value_x = (BigInt)arg0Value;
               if (JSGuards.isBigIntArrayIndex(arg0Value_x)) {
                  int var33;
                  this.state_0_ = var33 = state_0 | 32;
                  lock.unlock();
                  hasLock = false;
                  return ToArrayIndexNode.doBigInt(arg0Value_x);
               }
            }

            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_x = (TruffleString)arg0Value;
               if (this.convertStringToIndex && JSRuntime.arrayIndexLengthInRange(arg0Value_x)) {
                  ToArrayIndexNodeGen.ConvertFromStringData s6_ = super.insert(new ToArrayIndexNodeGen.ConvertFromStringData());
                  s6_.startsWithDigitBranch_ = ConditionProfile.create();
                  s6_.isArrayIndexBranch_ = BranchProfile.create();
                  s6_.stringReadNode_ = s6_.insertAccessor(TruffleString.ReadCharUTF16Node.create());
                  VarHandle.storeStoreFence();
                  this.convertFromString_cache = s6_;
                  int var32;
                  this.state_0_ = var32 = state_0 | 64;
                  lock.unlock();
                  hasLock = false;
                  return ToArrayIndexNode.convertFromString(arg0Value_x, s6_.startsWithDigitBranch_, s6_.isArrayIndexBranch_, s6_.stringReadNode_);
               }

               if (!this.convertStringToIndex || !JSRuntime.arrayIndexLengthInRange(arg0Value_x)) {
                  int var31;
                  this.state_0_ = var31 = state_0 | 128;
                  lock.unlock();
                  hasLock = false;
                  return ToArrayIndexNode.convertFromStringNotInRange(arg0Value_x);
               }
            }

            long index__ = 0L;
            if ((exclude & 2) == 0) {
               int count8_ = 0;
               ToArrayIndexNodeGen.InteropArrayIndex0Data s8_ = this.interopArrayIndex0_cache;
               if ((state_0 & 256) != 0) {
                  while (s8_ != null) {
                     if (s8_.interop_.accepts(arg0Value) && ToArrayIndexNode.notArrayIndex(arg0Value)) {
                        index__ = ToArrayIndexNode.toArrayIndex(arg0Value, s8_.interop_);
                        if (index__ >= 0L) {
                           break;
                        }
                     }

                     s8_ = s8_.next_;
                     count8_++;
                  }
               }

               if (s8_ == null && ToArrayIndexNode.notArrayIndex(arg0Value)) {
                  InteropLibrary interop__ = super.insert(INTEROP_LIBRARY_.create(arg0Value));
                  index__ = ToArrayIndexNode.toArrayIndex(arg0Value, interop__);
                  if (index__ >= 0L && count8_ < 5) {
                     s8_ = super.insert(new ToArrayIndexNodeGen.InteropArrayIndex0Data(this.interopArrayIndex0_cache));
                     s8_.interop_ = s8_.insertAccessor(interop__);
                     VarHandle.storeStoreFence();
                     this.interopArrayIndex0_cache = s8_;
                     this.state_0_ = state_0 |= 256;
                  }
               }

               if (s8_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return ToArrayIndexNode.doInteropArrayIndex(arg0Value, s8_.interop_, index__);
               }
            }

            long interopArrayIndex1_index__ = 0L;
            InteropLibrary interopArrayIndex1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               if (ToArrayIndexNode.notArrayIndex(arg0Value)) {
                  interopArrayIndex1_interop__ = INTEROP_LIBRARY_.getUncached();
                  long var51 = ToArrayIndexNode.toArrayIndex(arg0Value, interopArrayIndex1_interop__);
                  if (var51 >= 0L) {
                     int var43;
                     this.exclude_ = var43 = exclude | 2;
                     this.interopArrayIndex0_cache = null;
                     state_0 &= -257;
                     int var30;
                     this.state_0_ = var30 = state_0 | 512;
                     lock.unlock();
                     hasLock = false;
                     return ToArrayIndexNode.doInteropArrayIndex(arg0Value, interopArrayIndex1_interop__, var51);
                  }
               }
            } finally {
               encapsulating_.set(prev_);
            }

            if ((exclude & 4) == 0) {
               doubleCast0 = 0;
               ToArrayIndexNodeGen.NonArrayIndex0Data s10_ = this.nonArrayIndex0_cache;
               if ((state_0 & 1024) != 0) {
                  while (
                     s10_ != null
                        && (
                           !s10_.interop_.accepts(arg0Value)
                              || !ToArrayIndexNode.notArrayIndex(arg0Value)
                              || ToArrayIndexNode.toArrayIndex(arg0Value, s10_.interop_) >= 0L
                        )
                  ) {
                     s10_ = s10_.next_;
                     doubleCast0++;
                  }
               }

               if (s10_ == null && ToArrayIndexNode.notArrayIndex(arg0Value)) {
                  interopArrayIndex1_interop__ = super.insert(INTEROP_LIBRARY_.create(arg0Value));
                  if (ToArrayIndexNode.toArrayIndex(arg0Value, interopArrayIndex1_interop__) < 0L && doubleCast0 < 5) {
                     s10_ = super.insert(new ToArrayIndexNodeGen.NonArrayIndex0Data(this.nonArrayIndex0_cache));
                     s10_.interop_ = s10_.insertAccessor(interopArrayIndex1_interop__);
                     s10_.toPropertyKey_ = s10_.insertAccessor(JSToPropertyKeyNode.create());
                     s10_.recursive_ = s10_.insertAccessor(ToArrayIndexNode.createNoToPropertyKey());
                     VarHandle.storeStoreFence();
                     this.nonArrayIndex0_cache = s10_;
                     this.state_0_ = state_0 |= 1024;
                  }
               }

               if (s10_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doNonArrayIndex(arg0Value, s10_.interop_, s10_.toPropertyKey_, s10_.recursive_);
               }
            }

            InteropLibrary nonArrayIndex1_interop__ = null;
            EncapsulatingNodeReference encapsulating_x = EncapsulatingNodeReference.getCurrent();
            Node prev_x = encapsulating_x.set(this);

            try {
               if (ToArrayIndexNode.notArrayIndex(arg0Value)) {
                  InteropLibrary var54 = INTEROP_LIBRARY_.getUncached();
                  if (ToArrayIndexNode.toArrayIndex(arg0Value, var54) < 0L) {
                     this.nonArrayIndex1_toPropertyKey_ = super.insert(JSToPropertyKeyNode.create());
                     this.nonArrayIndex1_recursive_ = super.insert(ToArrayIndexNode.createNoToPropertyKey());
                     int var42;
                     this.exclude_ = var42 = exclude | 4;
                     this.nonArrayIndex0_cache = null;
                     state_0 &= -1025;
                     int var28;
                     this.state_0_ = var28 = state_0 | 2048;
                     lock.unlock();
                     hasLock = false;
                     return this.doNonArrayIndex(arg0Value, var54, this.nonArrayIndex1_toPropertyKey_, this.nonArrayIndex1_recursive_);
                  }
               }
            } finally {
               encapsulating_x.set(prev_x);
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
      if ((state_0 & 4095) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & 4095 & (state_0 & 4095) - 1) == 0) {
            ToArrayIndexNodeGen.InteropArrayIndex0Data s8_ = this.interopArrayIndex0_cache;
            ToArrayIndexNodeGen.NonArrayIndex0Data s10_ = this.nonArrayIndex0_cache;
            if ((s8_ == null || s8_.next_ == null) && (s10_ == null || s10_.next_ == null)) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[13];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doInteger", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doLong", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doDoubleAsIntIndex", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doDoubleAsUintIndex", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"convertFromString", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         ToArrayIndexNodeGen.ConvertFromStringData s6_ = this.convertFromString_cache;
         if (s6_ != null) {
            cached.add(Arrays.asList(s6_.startsWithDigitBranch_, s6_.isArrayIndexBranch_, s6_.stringReadNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"convertFromStringNotInRange", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doInteropArrayIndex", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (ToArrayIndexNodeGen.InteropArrayIndex0Data s8_ = this.interopArrayIndex0_cache; s8_ != null; s8_ = s8_.next_) {
            cached.add(Arrays.asList(s8_.interop_));
         }

         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doInteropArrayIndex", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList());
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doNonArrayIndex", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (ToArrayIndexNodeGen.NonArrayIndex0Data s10_ = this.nonArrayIndex0_cache; s10_ != null; s10_ = s10_.next_) {
            cached.add(Arrays.asList(s10_.interop_, s10_.toPropertyKey_, s10_.recursive_));
         }

         s[2] = cached;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doNonArrayIndex", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.nonArrayIndex1_toPropertyKey_, this.nonArrayIndex1_recursive_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      return Introspection.Provider.create(data);
   }

   public static ToArrayIndexNode create(boolean convertToPropertyKey, boolean convertStringToIndex) {
      return new ToArrayIndexNodeGen(convertToPropertyKey, convertStringToIndex);
   }

   @GeneratedBy(ToArrayIndexNode.class)
   private static final class ConvertFromStringData extends Node {
      @CompilerDirectives.CompilationFinal
      ConditionProfile startsWithDigitBranch_;
      @CompilerDirectives.CompilationFinal
      BranchProfile isArrayIndexBranch_;
      @Node.Child
      TruffleString.ReadCharUTF16Node stringReadNode_;

      ConvertFromStringData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(ToArrayIndexNode.class)
   private static final class InteropArrayIndex0Data extends Node {
      @Node.Child
      ToArrayIndexNodeGen.InteropArrayIndex0Data next_;
      @Node.Child
      InteropLibrary interop_;

      InteropArrayIndex0Data(ToArrayIndexNodeGen.InteropArrayIndex0Data next_) {
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

   @GeneratedBy(ToArrayIndexNode.class)
   private static final class NonArrayIndex0Data extends Node {
      @Node.Child
      ToArrayIndexNodeGen.NonArrayIndex0Data next_;
      @Node.Child
      InteropLibrary interop_;
      @Node.Child
      JSToPropertyKeyNode toPropertyKey_;
      @Node.Child
      ToArrayIndexNode recursive_;

      NonArrayIndex0Data(ToArrayIndexNodeGen.NonArrayIndex0Data next_) {
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
}
