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

@GeneratedBy(JSLeftShiftNode.class)
public final class JSLeftShiftNodeGen extends JSLeftShiftNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSLeftShiftNodeGen.DoubleData double_cache;
   @Node.Child
   private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSLeftShiftNodeGen.GenericData generic_cache;

   private JSLeftShiftNodeGen(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   @Override
   public Object executeObject(Object leftNodeValue, Object rightNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 3) != 0 && leftNodeValue instanceof Integer) {
         int leftNodeValue_ = (Integer)leftNodeValue;
         if ((state_0 & 1) != 0 && rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;
            return this.doInteger(leftNodeValue_, rightNodeValue_);
         }

         if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, rightNodeValue)) {
            double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, rightNodeValue);
            if (!JSBinaryNode.largerThan2e32(rightNodeValue_)) {
               return this.doIntegerDouble(leftNodeValue_, rightNodeValue_);
            }
         }
      }

      if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue)) {
         double leftNodeValue_x = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue);
         if (JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, rightNodeValue)) {
            double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, rightNodeValue);
            JSLeftShiftNodeGen.DoubleData s2_ = this.double_cache;
            if (s2_ != null) {
               return this.doDouble(leftNodeValue_x, rightNodeValue_, s2_.leftShift_, s2_.leftInt32_, s2_.rightUInt32_);
            }
         }
      }

      if ((state_0 & 8) != 0 && leftNodeValue instanceof BigInt) {
         BigInt leftNodeValue_x = (BigInt)leftNodeValue;
         if (rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_ = (BigInt)rightNodeValue;
            return this.doBigInt(leftNodeValue_x, rightNodeValue_);
         }
      }

      if ((state_0 & 48) != 0) {
         if ((state_0 & 16) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
         }

         if ((state_0 & 32) != 0) {
            JSLeftShiftNodeGen.GenericData s5_ = this.generic_cache;
            if (s5_ != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
               return this.doGeneric(leftNodeValue, rightNodeValue, s5_.leftShift_, s5_.leftToNumeric_, s5_.rightToNumeric_, s5_.mixedNumericTypes_);
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
      } else if ((state_0 & 61) == 0 && (state_0 & 63) != 0) {
         return this.execute_int_double1(state_0, frameValue);
      } else {
         return (state_0 & 59) == 0 && (state_0 & 63) != 0 ? this.execute_double_double2(state_0, frameValue) : this.execute_generic3(state_0, frameValue);
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

      assert (state_0 & 2) != 0;

      if (!JSBinaryNode.largerThan2e32(rightNodeValue_)) {
         return this.doIntegerDouble(leftNodeValue_, rightNodeValue_);
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

      assert (state_0 & 4) != 0;

      JSLeftShiftNodeGen.DoubleData s2_ = this.double_cache;
      if (s2_ != null) {
         return this.doDouble(leftNodeValue_, rightNodeValue_, s2_.leftShift_, s2_.leftInt32_, s2_.rightUInt32_);
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
      if ((state_0 & 3) != 0 && leftNodeValue_ instanceof Integer) {
         int leftNodeValue__ = (Integer)leftNodeValue_;
         if ((state_0 & 1) != 0 && rightNodeValue_ instanceof Integer) {
            int rightNodeValue__ = (Integer)rightNodeValue_;
            return this.doInteger(leftNodeValue__, rightNodeValue__);
         }

         if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, rightNodeValue_);
            if (!JSBinaryNode.largerThan2e32(rightNodeValue__)) {
               return this.doIntegerDouble(leftNodeValue__, rightNodeValue__);
            }
         }
      }

      if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue_)) {
         double leftNodeValue__x = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue_);
         if (JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, rightNodeValue_);
            JSLeftShiftNodeGen.DoubleData s2_ = this.double_cache;
            if (s2_ != null) {
               return this.doDouble(leftNodeValue__x, rightNodeValue__, s2_.leftShift_, s2_.leftInt32_, s2_.rightUInt32_);
            }
         }
      }

      if ((state_0 & 8) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__x = (BigInt)leftNodeValue_;
         if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            return this.doBigInt(leftNodeValue__x, rightNodeValue__);
         }
      }

      if ((state_0 & 48) != 0) {
         if ((state_0 & 16) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
            return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
         }

         if ((state_0 & 32) != 0) {
            JSLeftShiftNodeGen.GenericData s5_ = this.generic_cache;
            if (s5_ != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
               return this.doGeneric(leftNodeValue_, rightNodeValue_, s5_.leftShift_, s5_.leftToNumeric_, s5_.rightToNumeric_, s5_.mixedNumericTypes_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 52) != 0) {
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

         if ((state_0 & 2) == 0 && (state_0 & 3) != 0) {
            return this.executeInt_int4(state_0, frameValue, leftNodeValue_);
         } else {
            return (state_0 & 1) == 0 && (state_0 & 3) != 0
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

      assert (state_0 & 2) != 0;

      if (!JSBinaryNode.largerThan2e32(rightNodeValue_)) {
         return this.doIntegerDouble(leftNodeValue_, rightNodeValue_);
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
         if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, rightNodeValue_);
            if (!JSBinaryNode.largerThan2e32(rightNodeValue__)) {
               return this.doIntegerDouble(leftNodeValue_, rightNodeValue__);
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
         if ((state_0 & 60) == 0 && (state_0 & 63) != 0) {
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
         if (leftNodeValue instanceof Integer) {
            int leftNodeValue_ = (Integer)leftNodeValue;
            if ((exclude & 1) == 0 && rightNodeValue instanceof Integer) {
               int rightNodeValue_ = (Integer)rightNodeValue;
               int var27;
               this.state_0_ = var27 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doInteger(leftNodeValue_, rightNodeValue_);
            }

            int doubleCast1;
            if ((exclude & 2) == 0 && (doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               if (!JSBinaryNode.largerThan2e32(rightNodeValue_)) {
                  state_0 |= doubleCast1 << 6;
                  int var26;
                  this.state_0_ = var26 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doIntegerDouble(leftNodeValue_, rightNodeValue_);
               }
            }
         }

         int doubleCast0;
         if ((exclude & 4) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
            double leftNodeValue_x = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
            int doubleCast1;
            if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               JSLeftShiftNodeGen.DoubleData s2_ = super.insert(new JSLeftShiftNodeGen.DoubleData());
               s2_.leftShift_ = s2_.insertAccessor(JSLeftShiftNode.create());
               s2_.leftInt32_ = s2_.insertAccessor(JSToInt32Node.create());
               s2_.rightUInt32_ = s2_.insertAccessor(JSToUInt32Node.create());
               VarHandle.storeStoreFence();
               this.double_cache = s2_;
               state_0 |= doubleCast0 << 10;
               state_0 |= doubleCast1 << 6;
               int var24;
               this.state_0_ = var24 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doDouble(leftNodeValue_x, rightNodeValue_, s2_.leftShift_, s2_.leftInt32_, s2_.rightUInt32_);
            }
         }

         if ((exclude & 8) == 0 && leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_x = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_ = (BigInt)rightNodeValue;
               int var21;
               this.state_0_ = var21 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(leftNodeValue_x, rightNodeValue_);
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
            JSLeftShiftNodeGen.GenericData s5_ = super.insert(new JSLeftShiftNodeGen.GenericData());
            s5_.leftShift_ = s5_.insertAccessor(JSLeftShiftNode.create());
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
            return this.doGeneric(leftNodeValue, rightNodeValue, s5_.leftShift_, s5_.leftToNumeric_, s5_.rightToNumeric_, s5_.mixedNumericTypes_);
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
      s = new Object[]{"doIntegerDouble", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSLeftShiftNodeGen.DoubleData s2_ = this.double_cache;
         if (s2_ != null) {
            cached.add(Arrays.asList(s2_.leftShift_, s2_.leftInt32_, s2_.rightUInt32_));
         }

         s[2] = cached;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
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
         JSLeftShiftNodeGen.GenericData s5_ = this.generic_cache;
         if (s5_ != null) {
            cached.add(Arrays.asList(s5_.leftShift_, s5_.leftToNumeric_, s5_.rightToNumeric_, s5_.mixedNumericTypes_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      return Introspection.Provider.create(data);
   }

   public static JSLeftShiftNode create(JavaScriptNode left, JavaScriptNode right) {
      return new JSLeftShiftNodeGen(left, right);
   }

   @GeneratedBy(JSLeftShiftNode.class)
   private static final class DoubleData extends Node {
      @Node.Child
      JSLeftShiftNode leftShift_;
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

   @GeneratedBy(JSLeftShiftNode.class)
   private static final class GenericData extends Node {
      @Node.Child
      JSLeftShiftNode leftShift_;
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
