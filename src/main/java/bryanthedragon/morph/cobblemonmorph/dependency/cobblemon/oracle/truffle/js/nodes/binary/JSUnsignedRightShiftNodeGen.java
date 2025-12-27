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
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32Node;
import com.oracle.truffle.js.runtime.BigInt;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSUnsignedRightShiftNode.class)
public final class JSUnsignedRightShiftNodeGen extends JSUnsignedRightShiftNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile double_returnType_;
   @Node.Child
   private JSToUInt32Node intDouble_rvalToUint32Node_;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile intDouble_returnType_;
   @Node.Child
   private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSUnsignedRightShiftNodeGen.GenericData generic_cache;

   private JSUnsignedRightShiftNodeGen(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   @Override
   public Object execute(Object leftNodeValue, Object rightNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 31) != 0 && rightNodeValue instanceof Integer) {
         int rightNodeValue_ = (Integer)rightNodeValue;
         if ((state_0 & 7) != 0 && leftNodeValue instanceof Integer) {
            int leftNodeValue_ = (Integer)leftNodeValue;
            if ((state_0 & 1) != 0 && JSUnsignedRightShiftNode.rvalZero(rightNodeValue_) && leftNodeValue_ >= 0) {
               return this.doIntegerFast(leftNodeValue_, rightNodeValue_);
            }

            if ((state_0 & 2) != 0 && leftNodeValue_ >= 0) {
               return this.doInteger(leftNodeValue_, rightNodeValue_);
            }

            if ((state_0 & 4) != 0 && !JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
               return this.doIntegerNegative(leftNodeValue_, rightNodeValue_);
            }
         }

         if ((state_0 & 24) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue)) {
            double leftNodeValue_x = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue);
            if ((state_0 & 8) != 0 && JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
               return this.doDoubleZero(leftNodeValue_x, rightNodeValue_);
            }

            if ((state_0 & 16) != 0 && !JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
               return this.doDouble(leftNodeValue_x, rightNodeValue_, this.double_returnType_);
            }
         }
      }

      if ((state_0 & 96) != 0 && JSTypesGen.isImplicitDouble((state_0 & 245760) >>> 14, rightNodeValue)) {
         double rightNodeValue_x = JSTypesGen.asImplicitDouble((state_0 & 245760) >>> 14, rightNodeValue);
         if ((state_0 & 32) != 0 && leftNodeValue instanceof Integer) {
            int leftNodeValue_xx = (Integer)leftNodeValue;
            return this.doIntDouble(leftNodeValue_xx, rightNodeValue_x, this.intDouble_rvalToUint32Node_, this.intDouble_returnType_);
         }

         if ((state_0 & 64) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue)) {
            double leftNodeValue_xx = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue);
            return this.doDoubleDouble(leftNodeValue_xx, rightNodeValue_x);
         }
      }

      if ((state_0 & 128) != 0 && leftNodeValue instanceof BigInt) {
         BigInt leftNodeValue_xx = (BigInt)leftNodeValue;
         if (rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_xx = (BigInt)rightNodeValue;
            return this.doBigInt(leftNodeValue_xx, rightNodeValue_xx);
         }
      }

      if ((state_0 & 768) != 0) {
         if ((state_0 & 256) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
         }

         if ((state_0 & 512) != 0) {
            JSUnsignedRightShiftNodeGen.GenericData s9_ = this.generic_cache;
            if (s9_ != null
               && !this.hasOverloadedOperators(leftNodeValue)
               && !this.hasOverloadedOperators(rightNodeValue)
               && !JSUnsignedRightShiftNode.isHandled(leftNodeValue, rightNodeValue)) {
               return this.doGeneric(leftNodeValue, rightNodeValue, s9_.lvalToNumericNode_, s9_.rvalToNumericNode_, s9_.innerShiftNode_, s9_.mixedNumericTypes_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1016) == 0 && (state_0 & 1023) != 0) {
         return this.execute_int_int0(state_0, frameValue);
      } else if ((state_0 & 999) == 0 && (state_0 & 1023) != 0) {
         return this.execute_double_int1(state_0, frameValue);
      } else if ((state_0 & 991) == 0 && (state_0 & 1023) != 0) {
         return this.execute_int_double2(state_0, frameValue);
      } else {
         return (state_0 & 959) == 0 && (state_0 & 1023) != 0 ? this.execute_double_double3(state_0, frameValue) : this.execute_generic4(state_0, frameValue);
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

      if ((state_0 & 1) != 0 && JSUnsignedRightShiftNode.rvalZero(rightNodeValue_) && leftNodeValue_ >= 0) {
         return this.doIntegerFast(leftNodeValue_, rightNodeValue_);
      } else if ((state_0 & 2) != 0 && leftNodeValue_ >= 0) {
         return this.doInteger(leftNodeValue_, rightNodeValue_);
      } else if ((state_0 & 4) != 0 && !JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
         return this.doIntegerNegative(leftNodeValue_, rightNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   private Object execute_double_int1(int state_0, VirtualFrame frameValue) {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 14336) == 0 && (state_0 & 1023) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 13312) == 0 && (state_0 & 1023) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 7168) == 0 && (state_0 & 1023) != 0) {
            leftNodeValue_long = super.leftNode.executeLong(frameValue);
            leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
         } else {
            Object leftNodeValue__ = super.leftNode.execute(frameValue);
            leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue__);
         }
      } catch (UnexpectedResultException var11) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var11.getResult(), rightNodeValue);
      }

      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var10) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 13312) == 0 && (state_0 & 1023) != 0
               ? leftNodeValue_int
               : ((state_0 & 7168) == 0 && (state_0 & 1023) != 0 ? leftNodeValue_long : leftNodeValue_),
            var10.getResult()
         );
      }

      if ((state_0 & 8) != 0 && JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
         return this.doDoubleZero(leftNodeValue_, rightNodeValue_);
      } else if ((state_0 & 16) != 0 && !JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
         return this.doDouble(leftNodeValue_, rightNodeValue_, this.double_returnType_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 13312) == 0 && (state_0 & 1023) != 0
               ? leftNodeValue_int
               : ((state_0 & 7168) == 0 && (state_0 & 1023) != 0 ? leftNodeValue_long : leftNodeValue_),
            rightNodeValue_
         );
      }
   }

   private Object execute_int_double2(int state_0, VirtualFrame frameValue) {
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
         if ((state_0 & 229376) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 212992) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 114688) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 245760) >>> 14, rightNodeValue__);
         }
      } catch (UnexpectedResultException var11) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var11.getResult());
      }

      assert (state_0 & 32) != 0;

      return this.doIntDouble(leftNodeValue_, rightNodeValue_, this.intDouble_rvalToUint32Node_, this.intDouble_returnType_);
   }

   private Object execute_double_double3(int state_0, VirtualFrame frameValue) {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 14336) == 0 && (state_0 & 1023) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 13312) == 0 && (state_0 & 1023) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 7168) == 0 && (state_0 & 1023) != 0) {
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
         if ((state_0 & 229376) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 212992) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 114688) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 245760) >>> 14, rightNodeValue__);
         }
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 13312) == 0 && (state_0 & 1023) != 0
               ? leftNodeValue_int
               : ((state_0 & 7168) == 0 && (state_0 & 1023) != 0 ? leftNodeValue_long : leftNodeValue_),
            var14.getResult()
         );
      }

      assert (state_0 & 64) != 0;

      return this.doDoubleDouble(leftNodeValue_, rightNodeValue_);
   }

   private Object execute_generic4(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 31) != 0 && rightNodeValue_ instanceof Integer) {
         int rightNodeValue__ = (Integer)rightNodeValue_;
         if ((state_0 & 7) != 0 && leftNodeValue_ instanceof Integer) {
            int leftNodeValue__ = (Integer)leftNodeValue_;
            if ((state_0 & 1) != 0 && JSUnsignedRightShiftNode.rvalZero(rightNodeValue__) && leftNodeValue__ >= 0) {
               return this.doIntegerFast(leftNodeValue__, rightNodeValue__);
            }

            if ((state_0 & 2) != 0 && leftNodeValue__ >= 0) {
               return this.doInteger(leftNodeValue__, rightNodeValue__);
            }

            if ((state_0 & 4) != 0 && !JSUnsignedRightShiftNode.rvalZero(rightNodeValue__)) {
               return this.doIntegerNegative(leftNodeValue__, rightNodeValue__);
            }
         }

         if ((state_0 & 24) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue_)) {
            double leftNodeValue__x = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue_);
            if ((state_0 & 8) != 0 && JSUnsignedRightShiftNode.rvalZero(rightNodeValue__)) {
               return this.doDoubleZero(leftNodeValue__x, rightNodeValue__);
            }

            if ((state_0 & 16) != 0 && !JSUnsignedRightShiftNode.rvalZero(rightNodeValue__)) {
               return this.doDouble(leftNodeValue__x, rightNodeValue__, this.double_returnType_);
            }
         }
      }

      if ((state_0 & 96) != 0 && JSTypesGen.isImplicitDouble((state_0 & 245760) >>> 14, rightNodeValue_)) {
         double rightNodeValue__x = JSTypesGen.asImplicitDouble((state_0 & 245760) >>> 14, rightNodeValue_);
         if ((state_0 & 32) != 0 && leftNodeValue_ instanceof Integer) {
            int leftNodeValue__xx = (Integer)leftNodeValue_;
            return this.doIntDouble(leftNodeValue__xx, rightNodeValue__x, this.intDouble_rvalToUint32Node_, this.intDouble_returnType_);
         }

         if ((state_0 & 64) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue_)) {
            double leftNodeValue__xx = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue_);
            return this.doDoubleDouble(leftNodeValue__xx, rightNodeValue__x);
         }
      }

      if ((state_0 & 128) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__xx = (BigInt)leftNodeValue_;
         if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__xx = (BigInt)rightNodeValue_;
            return this.doBigInt(leftNodeValue__xx, rightNodeValue__xx);
         }
      }

      if ((state_0 & 768) != 0) {
         if ((state_0 & 256) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
            return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
         }

         if ((state_0 & 512) != 0) {
            JSUnsignedRightShiftNodeGen.GenericData s9_ = this.generic_cache;
            if (s9_ != null
               && !this.hasOverloadedOperators(leftNodeValue_)
               && !this.hasOverloadedOperators(rightNodeValue_)
               && !JSUnsignedRightShiftNode.isHandled(leftNodeValue_, rightNodeValue_)) {
               return this.doGeneric(
                  leftNodeValue_, rightNodeValue_, s9_.lvalToNumericNode_, s9_.rvalToNumericNode_, s9_.innerShiftNode_, s9_.mixedNumericTypes_
               );
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 944) != 0) {
         return JSTypesGen.expectDouble(this.execute(frameValue));
      } else if ((state_0 & 64) == 0 && (state_0 & 72) != 0) {
         return this.executeDouble_double_int5(state_0, frameValue);
      } else {
         return (state_0 & 8) == 0 && (state_0 & 72) != 0
            ? this.executeDouble_double_double6(state_0, frameValue)
            : this.executeDouble_generic7(state_0, frameValue);
      }
   }

   private double executeDouble_double_int5(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 14336) == 0 && (state_0 & 1023) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 13312) == 0 && (state_0 & 1023) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 7168) == 0 && (state_0 & 1023) != 0) {
            leftNodeValue_long = super.leftNode.executeLong(frameValue);
            leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
         } else {
            Object leftNodeValue__ = super.leftNode.execute(frameValue);
            leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue__);
         }
      } catch (UnexpectedResultException var11) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return JSTypesGen.expectDouble(this.executeAndSpecialize(var11.getResult(), rightNodeValue));
      }

      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var10) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectDouble(
            this.executeAndSpecialize(
               (state_0 & 13312) == 0 && (state_0 & 1023) != 0
                  ? leftNodeValue_int
                  : ((state_0 & 7168) == 0 && (state_0 & 1023) != 0 ? leftNodeValue_long : leftNodeValue_),
               var10.getResult()
            )
         );
      }

      assert (state_0 & 8) != 0;

      if (JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
         return this.doDoubleZero(leftNodeValue_, rightNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectDouble(
            this.executeAndSpecialize(
               (state_0 & 13312) == 0 && (state_0 & 1023) != 0
                  ? leftNodeValue_int
                  : ((state_0 & 7168) == 0 && (state_0 & 1023) != 0 ? leftNodeValue_long : leftNodeValue_),
               rightNodeValue_
            )
         );
      }
   }

   private double executeDouble_double_double6(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 14336) == 0 && (state_0 & 1023) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 13312) == 0 && (state_0 & 1023) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 7168) == 0 && (state_0 & 1023) != 0) {
            leftNodeValue_long = super.leftNode.executeLong(frameValue);
            leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
         } else {
            Object leftNodeValue__ = super.leftNode.execute(frameValue);
            leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue__);
         }
      } catch (UnexpectedResultException var15) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return JSTypesGen.expectDouble(this.executeAndSpecialize(var15.getResult(), rightNodeValue));
      }

      long rightNodeValue_long = 0L;
      int rightNodeValue_int = 0;

      double rightNodeValue_;
      try {
         if ((state_0 & 229376) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 212992) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 114688) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 245760) >>> 14, rightNodeValue__);
         }
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectDouble(
            this.executeAndSpecialize(
               (state_0 & 13312) == 0 && (state_0 & 1023) != 0
                  ? leftNodeValue_int
                  : ((state_0 & 7168) == 0 && (state_0 & 1023) != 0 ? leftNodeValue_long : leftNodeValue_),
               var14.getResult()
            )
         );
      }

      assert (state_0 & 64) != 0;

      return this.doDoubleDouble(leftNodeValue_, rightNodeValue_);
   }

   private double executeDouble_generic7(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 14336) == 0 && (state_0 & 1023) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 13312) == 0 && (state_0 & 1023) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 7168) == 0 && (state_0 & 1023) != 0) {
            leftNodeValue_long = super.leftNode.executeLong(frameValue);
            leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
         } else {
            Object leftNodeValue__ = super.leftNode.execute(frameValue);
            leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 15360) >>> 10, leftNodeValue__);
         }
      } catch (UnexpectedResultException var11) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return JSTypesGen.expectDouble(this.executeAndSpecialize(var11.getResult(), rightNodeValue));
      }

      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 8) != 0 && rightNodeValue_ instanceof Integer) {
         int rightNodeValue__ = (Integer)rightNodeValue_;
         if (JSUnsignedRightShiftNode.rvalZero(rightNodeValue__)) {
            return this.doDoubleZero(leftNodeValue_, rightNodeValue__);
         }
      }

      if ((state_0 & 64) != 0 && JSTypesGen.isImplicitDouble((state_0 & 245760) >>> 14, rightNodeValue_)) {
         double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 245760) >>> 14, rightNodeValue_);
         return this.doDoubleDouble(leftNodeValue_, rightNodeValue__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectDouble(
            this.executeAndSpecialize(
               (state_0 & 13312) == 0 && (state_0 & 1023) != 0
                  ? leftNodeValue_int
                  : ((state_0 & 7168) == 0 && (state_0 & 1023) != 0 ? leftNodeValue_long : leftNodeValue_),
               rightNodeValue_
            )
         );
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 944) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else {
         int leftNodeValue_;
         try {
            leftNodeValue_ = super.leftNode.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object rightNodeValue = super.rightNode.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(var7.getResult(), rightNodeValue));
         }

         int rightNodeValue_;
         try {
            rightNodeValue_ = super.rightNode.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, var6.getResult()));
         }

         if ((state_0 & 1) != 0 && JSUnsignedRightShiftNode.rvalZero(rightNodeValue_) && leftNodeValue_ >= 0) {
            return this.doIntegerFast(leftNodeValue_, rightNodeValue_);
         } else if ((state_0 & 2) != 0 && leftNodeValue_ >= 0) {
            return this.doInteger(leftNodeValue_, rightNodeValue_);
         } else if ((state_0 & 4) != 0 && !JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
            return this.doIntegerNegative(leftNodeValue_, rightNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
         }
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 1016) == 0 && (state_0 & 1023) != 0) {
            this.executeInt(frameValue);
         } else if ((state_0 & 951) == 0 && (state_0 & 1023) != 0) {
            this.executeDouble(frameValue);
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
         if (rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;
            if (leftNodeValue instanceof Integer) {
               int leftNodeValue_ = (Integer)leftNodeValue;
               if (JSUnsignedRightShiftNode.rvalZero(rightNodeValue_) && leftNodeValue_ >= 0) {
                  int var30;
                  this.state_0_ = var30 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.doIntegerFast(leftNodeValue_, rightNodeValue_);
               }

               if (leftNodeValue_ >= 0) {
                  int var29;
                  this.state_0_ = var29 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doInteger(leftNodeValue_, rightNodeValue_);
               }

               if (!JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
                  int var28;
                  this.state_0_ = var28 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doIntegerNegative(leftNodeValue_, rightNodeValue_);
               }
            }

            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
               double leftNodeValue_x = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
               if (JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
                  state_0 |= doubleCast0 << 10;
                  int var27;
                  this.state_0_ = var27 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.doDoubleZero(leftNodeValue_x, rightNodeValue_);
               }

               if (!JSUnsignedRightShiftNode.rvalZero(rightNodeValue_)) {
                  this.double_returnType_ = ConditionProfile.createBinaryProfile();
                  state_0 |= doubleCast0 << 10;
                  int var25;
                  this.state_0_ = var25 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.doDouble(leftNodeValue_x, rightNodeValue_, this.double_returnType_);
               }
            }
         }

         int doubleCast1;
         if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
            double rightNodeValue_x = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
            if (leftNodeValue instanceof Integer) {
               int leftNodeValue_xx = (Integer)leftNodeValue;
               this.intDouble_rvalToUint32Node_ = super.insert(JSToUInt32Node.create());
               this.intDouble_returnType_ = ConditionProfile.createBinaryProfile();
               state_0 |= doubleCast1 << 14;
               int var23;
               this.state_0_ = var23 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.doIntDouble(leftNodeValue_xx, rightNodeValue_x, this.intDouble_rvalToUint32Node_, this.intDouble_returnType_);
            }

            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
               double leftNodeValue_xx = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
               state_0 |= doubleCast0 << 10;
               state_0 |= doubleCast1 << 14;
               int var21;
               this.state_0_ = var21 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.doDoubleDouble(leftNodeValue_xx, rightNodeValue_x);
            }
         }

         if (leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_xx = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_xx = (BigInt)rightNodeValue;
               int var18;
               this.state_0_ = var18 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(leftNodeValue_xx, rightNodeValue_xx);
            }
         }

         if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
            int var16;
            this.state_0_ = var16 = state_0 | 256;
            lock.unlock();
            hasLock = false;
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
         } else if (this.hasOverloadedOperators(leftNodeValue)
            || this.hasOverloadedOperators(rightNodeValue)
            || JSUnsignedRightShiftNode.isHandled(leftNodeValue, rightNodeValue)) {
            throw new UnsupportedSpecializationException(this, new Node[]{super.leftNode, super.rightNode}, leftNodeValue, rightNodeValue);
         } else {
            JSUnsignedRightShiftNodeGen.GenericData s9_ = super.insert(new JSUnsignedRightShiftNodeGen.GenericData());
            s9_.lvalToNumericNode_ = s9_.insertAccessor(JSToNumericNode.create());
            s9_.rvalToNumericNode_ = s9_.insertAccessor(JSToNumericNode.create());
            s9_.innerShiftNode_ = s9_.insertAccessor(JSUnsignedRightShiftNode.create());
            s9_.mixedNumericTypes_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.generic_cache = s9_;
            int var17;
            this.state_0_ = var17 = state_0 | 512;
            lock.unlock();
            hasLock = false;
            return this.doGeneric(leftNodeValue, rightNodeValue, s9_.lvalToNumericNode_, s9_.rvalToNumericNode_, s9_.innerShiftNode_, s9_.mixedNumericTypes_);
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
      if ((state_0 & 1023) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 1023 & (state_0 & 1023) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[11];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doIntegerFast", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doInteger", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doIntegerNegative", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doDoubleZero", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.double_returnType_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doIntDouble", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.intDouble_rvalToUint32Node_, this.intDouble_returnType_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doDoubleDouble", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doOverloaded", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doGeneric", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSUnsignedRightShiftNodeGen.GenericData s9_ = this.generic_cache;
         if (s9_ != null) {
            cached.add(Arrays.asList(s9_.lvalToNumericNode_, s9_.rvalToNumericNode_, s9_.innerShiftNode_, s9_.mixedNumericTypes_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      return Introspection.Provider.create(data);
   }

   public static JSUnsignedRightShiftNode create(JavaScriptNode left, JavaScriptNode right) {
      return new JSUnsignedRightShiftNodeGen(left, right);
   }

   @GeneratedBy(JSUnsignedRightShiftNode.class)
   private static final class GenericData extends Node {
      @Node.Child
      JSToNumericNode lvalToNumericNode_;
      @Node.Child
      JSToNumericNode rvalToNumericNode_;
      @Node.Child
      JSUnsignedRightShiftNode innerShiftNode_;
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
