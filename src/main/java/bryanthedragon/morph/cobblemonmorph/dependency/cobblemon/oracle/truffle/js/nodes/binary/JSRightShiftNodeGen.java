package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32Node;
import com.oracle.truffle.js.runtime.BigInt;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSRightShiftNode.class)
public final class JSRightShiftNodeGen extends JSRightShiftNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSLeftShiftNode bigInt_leftShift_;
   @Node.Child
   private JSRightShiftNodeGen.DoubleData double_cache;
   @Node.Child
   private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSRightShiftNodeGen.GenericData generic_cache;

   private JSRightShiftNodeGen(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   @Override
   public Object execute(Object leftNodeValue, Object rightNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && leftNodeValue instanceof Integer) {
         int leftNodeValue_ = (Integer)leftNodeValue;
         if (rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;
            return this.doInteger(leftNodeValue_, rightNodeValue_);
         }
      }

      if ((state_0 & 2) != 0 && leftNodeValue instanceof BigInt) {
         BigInt leftNodeValue_ = (BigInt)leftNodeValue;
         if (rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_ = (BigInt)rightNodeValue;
            return this.doBigInt(leftNodeValue_, rightNodeValue_, this.bigInt_leftShift_);
         }
      }

      if ((state_0 & 12) != 0 && JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, rightNodeValue)) {
         double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, rightNodeValue);
         if ((state_0 & 4) != 0 && leftNodeValue instanceof Integer) {
            int leftNodeValue_ = (Integer)leftNodeValue;
            if (!JSBinaryNode.largerThan2e32(rightNodeValue_)) {
               return this.doIntDouble(leftNodeValue_, rightNodeValue_);
            }
         }

         if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue)) {
            double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue);
            JSRightShiftNodeGen.DoubleData s3_ = this.double_cache;
            if (s3_ != null) {
               return this.doDouble(leftNodeValue_, rightNodeValue_, s3_.rightShift_, s3_.leftInt32_, s3_.rightUInt32_);
            }
         }
      }

      if ((state_0 & 48) != 0) {
         if ((state_0 & 16) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
         }

         if ((state_0 & 32) != 0) {
            JSRightShiftNodeGen.GenericData s5_ = this.generic_cache;
            if (s5_ != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
               return this.doGeneric(leftNodeValue, rightNodeValue, s5_.rightShift_, s5_.leftToNumeric_, s5_.rightToNumeric_, s5_.mixedNumericTypes_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 62) == 0 && (state_0 & 63) != 0) {
         return this.execute_int_int0(state_0, frameValue);
      } else if ((state_0 & 59) == 0 && (state_0 & 63) != 0) {
         return this.execute_int_double1(state_0, frameValue);
      } else {
         return (state_0 & 55) == 0 && (state_0 & 63) != 0 ? this.execute_double_double2(state_0, frameValue) : this.execute_generic3(state_0, frameValue);
      }
   }

   private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
      int leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeInt(frameValue);
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var7.getResult(), rightNodeValue);
      }

      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var6.getResult());
      }

      assert (state_0 & 1) != 0;

      return this.doInteger(leftNodeValue_, rightNodeValue_);
   }

   private Object execute_int_double1(int state_0, VirtualFrame frameValue) {
      int leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeInt(frameValue);
      } catch (UnexpectedResultException var10) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var10.getResult(), rightNodeValue);
      }

      long rightNodeValue_long = 0L;
      int rightNodeValue_int = 0;

      double rightNodeValue_;
      try {
         if ((state_0 & 896) == 0 && (state_0 & 63) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 832) == 0 && (state_0 & 63) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 448) == 0 && (state_0 & 63) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 960) >>> 6, rightNodeValue__);
         }
      } catch (UnexpectedResultException var11) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var11.getResult());
      }

      assert (state_0 & 4) != 0;

      if (!JSBinaryNode.largerThan2e32(rightNodeValue_)) {
         return this.doIntDouble(leftNodeValue_, rightNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            leftNodeValue_,
            (state_0 & 832) == 0 && (state_0 & 63) != 0
               ? rightNodeValue_int
               : ((state_0 & 448) == 0 && (state_0 & 63) != 0 ? rightNodeValue_long : rightNodeValue_)
         );
      }
   }

   private Object execute_double_double2(int state_0, VirtualFrame frameValue) {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 14336) == 0 && (state_0 & 63) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 13312) == 0 && (state_0 & 63) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 7168) == 0 && (state_0 & 63) != 0) {
            leftNodeValue_long = super.leftNode.executeLong(frameValue);
            leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
         } else {
            Object leftNodeValue__ = super.leftNode.execute(frameValue);
            leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue__);
         }
      } catch (UnexpectedResultException var15) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var15.getResult(), rightNodeValue);
      }

      long rightNodeValue_long = 0L;
      int rightNodeValue_int = 0;

      double rightNodeValue_;
      try {
         if ((state_0 & 896) == 0 && (state_0 & 63) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 832) == 0 && (state_0 & 63) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 448) == 0 && (state_0 & 63) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 960) >>> 6, rightNodeValue__);
         }
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 13312) == 0 && (state_0 & 63) != 0
               ? leftNodeValue_int
               : ((state_0 & 7168) == 0 && (state_0 & 63) != 0 ? leftNodeValue_long : leftNodeValue_),
            var14.getResult()
         );
      }

      assert (state_0 & 8) != 0;

      JSRightShiftNodeGen.DoubleData s3_ = this.double_cache;
      if (s3_ != null) {
         return this.doDouble(leftNodeValue_, rightNodeValue_, s3_.rightShift_, s3_.leftInt32_, s3_.rightUInt32_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 13312) == 0 && (state_0 & 63) != 0
               ? leftNodeValue_int
               : ((state_0 & 7168) == 0 && (state_0 & 63) != 0 ? leftNodeValue_long : leftNodeValue_),
            (state_0 & 832) == 0 && (state_0 & 63) != 0
               ? rightNodeValue_int
               : ((state_0 & 448) == 0 && (state_0 & 63) != 0 ? rightNodeValue_long : rightNodeValue_)
         );
      }
   }

   private Object execute_generic3(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 1) != 0 && leftNodeValue_ instanceof Integer) {
         int leftNodeValue__ = (Integer)leftNodeValue_;
         if (rightNodeValue_ instanceof Integer) {
            int rightNodeValue__ = (Integer)rightNodeValue_;
            return this.doInteger(leftNodeValue__, rightNodeValue__);
         }
      }

      if ((state_0 & 2) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
         if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            return this.doBigInt(leftNodeValue__, rightNodeValue__, this.bigInt_leftShift_);
         }
      }

      if ((state_0 & 12) != 0 && JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, rightNodeValue_)) {
         double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, rightNodeValue_);
         if ((state_0 & 4) != 0 && leftNodeValue_ instanceof Integer) {
            int leftNodeValue__ = (Integer)leftNodeValue_;
            if (!JSBinaryNode.largerThan2e32(rightNodeValue__)) {
               return this.doIntDouble(leftNodeValue__, rightNodeValue__);
            }
         }

         if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue_)) {
            double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue_);
            JSRightShiftNodeGen.DoubleData s3_ = this.double_cache;
            if (s3_ != null) {
               return this.doDouble(leftNodeValue__, rightNodeValue__, s3_.rightShift_, s3_.leftInt32_, s3_.rightUInt32_);
            }
         }
      }

      if ((state_0 & 48) != 0) {
         if ((state_0 & 16) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
            return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
         }

         if ((state_0 & 32) != 0) {
            JSRightShiftNodeGen.GenericData s5_ = this.generic_cache;
            if (s5_ != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
               return this.doGeneric(leftNodeValue_, rightNodeValue_, s5_.rightShift_, s5_.leftToNumeric_, s5_.rightToNumeric_, s5_.mixedNumericTypes_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 56) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else {
         int leftNodeValue_;
         try {
            leftNodeValue_ = super.leftNode.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = super.rightNode.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(var6.getResult(), rightNodeValue));
         }

         if ((state_0 & 4) == 0 && (state_0 & 5) != 0) {
            return this.executeInt_int4(state_0, frameValue, leftNodeValue_);
         } else {
            return (state_0 & 1) == 0 && (state_0 & 5) != 0
               ? this.executeInt_double5(state_0, frameValue, leftNodeValue_)
               : this.executeInt_generic6(state_0, frameValue, leftNodeValue_);
         }
      }
   }

   private int executeInt_int4(int state_0, VirtualFrame frameValue, int leftNodeValue_) throws UnexpectedResultException {
      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, var6.getResult()));
      }

      assert (state_0 & 1) != 0;

      return this.doInteger(leftNodeValue_, rightNodeValue_);
   }

   private int executeInt_double5(int state_0, VirtualFrame frameValue, int leftNodeValue_) throws UnexpectedResultException {
      long rightNodeValue_long = 0L;
      int rightNodeValue_int = 0;

      double rightNodeValue_;
      try {
         if ((state_0 & 896) == 0 && (state_0 & 63) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 832) == 0 && (state_0 & 63) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 448) == 0 && (state_0 & 63) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 960) >>> 6, rightNodeValue__);
         }
      } catch (UnexpectedResultException var10) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, var10.getResult()));
      }

      assert (state_0 & 4) != 0;

      if (!JSBinaryNode.largerThan2e32(rightNodeValue_)) {
         return this.doIntDouble(leftNodeValue_, rightNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(
            this.executeAndSpecialize(
               leftNodeValue_,
               (state_0 & 832) == 0 && (state_0 & 63) != 0
                  ? rightNodeValue_int
                  : ((state_0 & 448) == 0 && (state_0 & 63) != 0 ? rightNodeValue_long : rightNodeValue_)
            )
         );
      }
   }

   private int executeInt_generic6(int state_0, VirtualFrame frameValue, int leftNodeValue_) throws UnexpectedResultException {
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 1) != 0 && rightNodeValue_ instanceof Integer) {
         int rightNodeValue__ = (Integer)rightNodeValue_;
         return this.doInteger(leftNodeValue_, rightNodeValue__);
      } else {
         if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, rightNodeValue_);
            if (!JSBinaryNode.largerThan2e32(rightNodeValue__)) {
               return this.doIntDouble(leftNodeValue_, rightNodeValue__);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 58) == 0 && (state_0 & 63) != 0) {
            this.executeInt(frameValue);
         } else {
            this.execute(frameValue);
         }
      } catch (UnexpectedResultException var4) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }
   }

   private Object executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if ((exclude & 1) == 0 && leftNodeValue instanceof Integer) {
            int leftNodeValue_ = (Integer)leftNodeValue;
            if (rightNodeValue instanceof Integer) {
               int rightNodeValue_ = (Integer)rightNodeValue;
               int var27;
               this.state_0_ = var27 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doInteger(leftNodeValue_, rightNodeValue_);
            }
         }

         if ((exclude & 2) == 0 && leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_ = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_ = (BigInt)rightNodeValue;
               this.bigInt_leftShift_ = super.insert(JSLeftShiftNode.create());
               int var26;
               this.state_0_ = var26 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(leftNodeValue_, rightNodeValue_, this.bigInt_leftShift_);
            }
         }

         int doubleCast1;
         if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
            double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
            if ((exclude & 4) == 0 && leftNodeValue instanceof Integer) {
               int leftNodeValue_ = (Integer)leftNodeValue;
               if (!JSBinaryNode.largerThan2e32(rightNodeValue_)) {
                  state_0 |= doubleCast1 << 6;
                  int var25;
                  this.state_0_ = var25 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doIntDouble(leftNodeValue_, rightNodeValue_);
               }
            }

            int doubleCast0;
            if ((exclude & 8) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
               double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
               JSRightShiftNodeGen.DoubleData s3_ = super.insert(new JSRightShiftNodeGen.DoubleData());
               s3_.rightShift_ = s3_.insertAccessor(JSRightShiftNode.create());
               s3_.leftInt32_ = s3_.insertAccessor(JSToInt32Node.create());
               s3_.rightUInt32_ = s3_.insertAccessor(JSToUInt32Node.create());
               VarHandle.storeStoreFence();
               this.double_cache = s3_;
               state_0 |= doubleCast0 << 10;
               state_0 |= doubleCast1 << 6;
               int var23;
               this.state_0_ = var23 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doDouble(leftNodeValue_, rightNodeValue_, s3_.rightShift_, s3_.leftInt32_, s3_.rightUInt32_);
            }
         }

         if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
            int var18;
            this.state_0_ = var18 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
         } else if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            throw new UnsupportedSpecializationException(this, new Node[]{super.leftNode, super.rightNode}, leftNodeValue, rightNodeValue);
         } else {
            JSRightShiftNodeGen.GenericData s5_ = super.insert(new JSRightShiftNodeGen.GenericData());
            s5_.rightShift_ = s5_.insertAccessor(JSRightShiftNode.create());
            s5_.leftToNumeric_ = s5_.insertAccessor(JSToNumericNode.create());
            s5_.rightToNumeric_ = s5_.insertAccessor(JSToNumericNode.create());
            s5_.mixedNumericTypes_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.generic_cache = s5_;
            int var28;
            this.exclude_ = var28 = exclude | 15;
            this.double_cache = null;
            state_0 &= -16;
            int var20;
            this.state_0_ = var20 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doGeneric(leftNodeValue, rightNodeValue, s5_.rightShift_, s5_.leftToNumeric_, s5_.rightToNumeric_, s5_.mixedNumericTypes_);
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
      if ((state_0 & 63) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 63 & (state_0 & 63) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[7];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doInteger", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.bigInt_leftShift_));
         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doIntDouble", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSRightShiftNodeGen.DoubleData s3_ = this.double_cache;
         if (s3_ != null) {
            cached.add(Arrays.asList(s3_.rightShift_, s3_.leftInt32_, s3_.rightUInt32_));
         }

         s[2] = cached;
      } else if ((exclude & 8) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doOverloaded", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doGeneric", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSRightShiftNodeGen.GenericData s5_ = this.generic_cache;
         if (s5_ != null) {
            cached.add(Arrays.asList(s5_.rightShift_, s5_.leftToNumeric_, s5_.rightToNumeric_, s5_.mixedNumericTypes_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      return Introspection.Provider.create(data);
   }

   public static JSRightShiftNode create(JavaScriptNode left, JavaScriptNode right) {
      return new JSRightShiftNodeGen(left, right);
   }

   @GeneratedBy(JSRightShiftNode.class)
   private static final class DoubleData extends Node {
      @Node.Child
      JSRightShiftNode rightShift_;
      @Node.Child
      JSToInt32Node leftInt32_;
      @Node.Child
      JSToUInt32Node rightUInt32_;

      DoubleData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(JSRightShiftNode.class)
   private static final class GenericData extends Node {
      @Node.Child
      JSRightShiftNode rightShift_;
      @Node.Child
      JSToNumericNode leftToNumeric_;
      @Node.Child
      JSToNumericNode rightToNumeric_;
      @CompilerDirectives.CompilationFinal
      BranchProfile mixedNumericTypes_;

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
