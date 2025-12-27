package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToStringOrNumberNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSLessThanNode.class)
public final class JSLessThanNodeGen extends JSLessThanNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private TruffleString.CompareCharsUTF16Node string_compareNode_;
   @Node.Child
   private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSToBooleanNode overloaded_toBooleanNode_;
   @Node.Child
   private JSLessThanNodeGen.GenericData generic_cache;

   private JSLessThanNodeGen(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   @Override
   public boolean executeBoolean(Object leftNodeValue, Object rightNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 3) != 0 && leftNodeValue instanceof Integer) {
         int leftNodeValue_ = (Integer)leftNodeValue;
         if ((state_0 & 1) != 0 && rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;
            return this.doInt(leftNodeValue_, rightNodeValue_);
         }

         if ((state_0 & 2) != 0 && rightNodeValue instanceof SafeInteger) {
            SafeInteger rightNodeValue_ = (SafeInteger)rightNodeValue;
            return this.doSafeInteger(leftNodeValue_, rightNodeValue_);
         }
      }

      if ((state_0 & 12) != 0 && leftNodeValue instanceof SafeInteger) {
         SafeInteger leftNodeValue_x = (SafeInteger)leftNodeValue;
         if ((state_0 & 4) != 0 && rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;
            return this.doSafeInteger(leftNodeValue_x, rightNodeValue_);
         }

         if ((state_0 & 8) != 0 && rightNodeValue instanceof SafeInteger) {
            SafeInteger rightNodeValue_ = (SafeInteger)rightNodeValue;
            return this.doSafeInteger(leftNodeValue_x, rightNodeValue_);
         }
      }

      if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3932160) >>> 18, leftNodeValue)) {
         double leftNodeValue_xx = JSTypesGen.asImplicitDouble((state_0 & 3932160) >>> 18, leftNodeValue);
         if (JSTypesGen.isImplicitDouble((state_0 & 62914560) >>> 22, rightNodeValue)) {
            double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 62914560) >>> 22, rightNodeValue);
            return this.doDouble(leftNodeValue_xx, rightNodeValue_);
         }
      }

      if ((state_0 & 96) != 0 && leftNodeValue instanceof TruffleString) {
         TruffleString leftNodeValue_xx = (TruffleString)leftNodeValue;
         if ((state_0 & 32) != 0 && rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
            return this.doString(leftNodeValue_xx, rightNodeValue_, this.string_compareNode_);
         }

         if ((state_0 & 64) != 0 && JSTypesGen.isImplicitDouble((state_0 & 62914560) >>> 22, rightNodeValue)) {
            double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 62914560) >>> 22, rightNodeValue);
            return this.doStringDouble(leftNodeValue_xx, rightNodeValue_);
         }
      }

      if ((state_0 & 128) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3932160) >>> 18, leftNodeValue)) {
         double leftNodeValue_xxx = JSTypesGen.asImplicitDouble((state_0 & 3932160) >>> 18, leftNodeValue);
         if (rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
            return this.doDoubleString(leftNodeValue_xxx, rightNodeValue_);
         }
      }

      if ((state_0 & 256) != 0 && leftNodeValue instanceof TruffleString) {
         TruffleString leftNodeValue_xxx = (TruffleString)leftNodeValue;
         if (rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_ = (BigInt)rightNodeValue;
            return this.doStringBigInt(leftNodeValue_xxx, rightNodeValue_);
         }
      }

      if ((state_0 & 7680) != 0 && leftNodeValue instanceof BigInt) {
         BigInt leftNodeValue_xxx = (BigInt)leftNodeValue;
         if ((state_0 & 512) != 0 && rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
            return this.doBigIntString(leftNodeValue_xxx, rightNodeValue_);
         }

         if ((state_0 & 1024) != 0 && rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_ = (BigInt)rightNodeValue;
            return this.doBigInt(leftNodeValue_xxx, rightNodeValue_);
         }

         if ((state_0 & 2048) != 0 && rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;
            return this.doBigIntAndInt(leftNodeValue_xxx, rightNodeValue_);
         }

         if ((state_0 & 4096) != 0 && JSTypesGen.isImplicitDouble((state_0 & 62914560) >>> 22, rightNodeValue)) {
            double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 62914560) >>> 22, rightNodeValue);
            return this.doBigIntAndNumber(leftNodeValue_xxx, rightNodeValue_);
         }
      }

      if ((state_0 & 24576) != 0 && rightNodeValue instanceof BigInt) {
         BigInt rightNodeValue_ = (BigInt)rightNodeValue;
         if ((state_0 & 8192) != 0 && leftNodeValue instanceof Integer) {
            int leftNodeValue_xxxx = (Integer)leftNodeValue;
            return this.doIntAndBigInt(leftNodeValue_xxxx, rightNodeValue_);
         }

         if ((state_0 & 16384) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3932160) >>> 18, leftNodeValue)) {
            double leftNodeValue_xxxx = JSTypesGen.asImplicitDouble((state_0 & 3932160) >>> 18, leftNodeValue);
            return this.doNumberAndBigInt(leftNodeValue_xxxx, rightNodeValue_);
         }
      }

      if ((state_0 & 229376) != 0) {
         if ((state_0 & 32768) != 0 && JSGuards.isJavaNumber(leftNodeValue) && JSGuards.isJavaNumber(rightNodeValue)) {
            return this.doJavaNumber(leftNodeValue, rightNodeValue);
         }

         if ((state_0 & 65536) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_);
         }

         if ((state_0 & 131072) != 0) {
            JSLessThanNodeGen.GenericData s17_ = this.generic_cache;
            if (s17_ != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
               return this.doGeneric(
                  leftNodeValue, rightNodeValue, s17_.toStringOrNumber1_, s17_.toPrimitive1_, s17_.toStringOrNumber2_, s17_.toPrimitive2_, s17_.lessThanNode_
               );
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 262142) == 0 && (state_0 & 262143) != 0) {
         return this.executeBoolean_int_int0(state_0, frameValue);
      } else if ((state_0 & 262127) == 0 && (state_0 & 262143) != 0) {
         return this.executeBoolean_double_double1(state_0, frameValue);
      } else if ((state_0 & 253949) == 0 && (state_0 & 262143) != 0) {
         return this.executeBoolean_int2(state_0, frameValue);
      } else if ((state_0 & 260091) == 0 && (state_0 & 262143) != 0) {
         return this.executeBoolean_int3(state_0, frameValue);
      } else if ((state_0 & 257983) == 0 && (state_0 & 262143) != 0) {
         return this.executeBoolean_double4(state_0, frameValue);
      } else {
         return (state_0 & 245631) == 0 && (state_0 & 262143) != 0
            ? this.executeBoolean_double5(state_0, frameValue)
            : this.executeBoolean_generic6(state_0, frameValue);
      }
   }

   private boolean executeBoolean_int_int0(int state_0, VirtualFrame frameValue) {
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

      return this.doInt(leftNodeValue_, rightNodeValue_);
   }

   private boolean executeBoolean_double_double1(int state_0, VirtualFrame frameValue) {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 3670016) == 0 && (state_0 & 262143) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 3407872) == 0 && (state_0 & 262143) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 1835008) == 0 && (state_0 & 262143) != 0) {
            leftNodeValue_long = super.leftNode.executeLong(frameValue);
            leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
         } else {
            Object leftNodeValue__ = super.leftNode.execute(frameValue);
            leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 3932160) >>> 18, leftNodeValue__);
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
         if ((state_0 & 58720256) == 0 && (state_0 & 262143) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 54525952) == 0 && (state_0 & 262143) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 29360128) == 0 && (state_0 & 262143) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 62914560) >>> 22, rightNodeValue__);
         }
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 3407872) == 0 && (state_0 & 262143) != 0
               ? leftNodeValue_int
               : ((state_0 & 1835008) == 0 && (state_0 & 262143) != 0 ? leftNodeValue_long : leftNodeValue_),
            var14.getResult()
         );
      }

      assert (state_0 & 16) != 0;

      return this.doDouble(leftNodeValue_, rightNodeValue_);
   }

   private boolean executeBoolean_int2(int state_0, VirtualFrame frameValue) {
      int leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeInt(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var6.getResult(), rightNodeValue);
      }

      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 2) != 0 && rightNodeValue_ instanceof SafeInteger) {
         SafeInteger rightNodeValue__ = (SafeInteger)rightNodeValue_;
         return this.doSafeInteger(leftNodeValue_, rightNodeValue__);
      } else if ((state_0 & 8192) != 0 && rightNodeValue_ instanceof BigInt) {
         BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
         return this.doIntAndBigInt(leftNodeValue_, rightNodeValue__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   private boolean executeBoolean_int3(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);

      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var6.getResult());
      }

      if ((state_0 & 4) != 0 && leftNodeValue_ instanceof SafeInteger) {
         SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
         return this.doSafeInteger(leftNodeValue__, rightNodeValue_);
      } else if ((state_0 & 2048) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
         return this.doBigIntAndInt(leftNodeValue__, rightNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   private boolean executeBoolean_double4(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      long rightNodeValue_long = 0L;
      int rightNodeValue_int = 0;

      double rightNodeValue_;
      try {
         if ((state_0 & 58720256) == 0 && (state_0 & 262143) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 54525952) == 0 && (state_0 & 262143) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 29360128) == 0 && (state_0 & 262143) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 62914560) >>> 22, rightNodeValue__);
         }
      } catch (UnexpectedResultException var10) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var10.getResult());
      }

      if ((state_0 & 64) != 0 && leftNodeValue_ instanceof TruffleString) {
         TruffleString leftNodeValue__ = (TruffleString)leftNodeValue_;
         return this.doStringDouble(leftNodeValue__, rightNodeValue_);
      } else if ((state_0 & 4096) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
         return this.doBigIntAndNumber(leftNodeValue__, rightNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            leftNodeValue_,
            (state_0 & 54525952) == 0 && (state_0 & 262143) != 0
               ? rightNodeValue_int
               : ((state_0 & 29360128) == 0 && (state_0 & 262143) != 0 ? rightNodeValue_long : rightNodeValue_)
         );
      }
   }

   private boolean executeBoolean_double5(int state_0, VirtualFrame frameValue) {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 3670016) == 0 && (state_0 & 262143) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 3407872) == 0 && (state_0 & 262143) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 1835008) == 0 && (state_0 & 262143) != 0) {
            leftNodeValue_long = super.leftNode.executeLong(frameValue);
            leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
         } else {
            Object leftNodeValue__ = super.leftNode.execute(frameValue);
            leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 3932160) >>> 18, leftNodeValue__);
         }
      } catch (UnexpectedResultException var10) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var10.getResult(), rightNodeValue);
      }

      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 128) != 0 && rightNodeValue_ instanceof TruffleString) {
         TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
         return this.doDoubleString(leftNodeValue_, rightNodeValue__);
      } else if ((state_0 & 16384) != 0 && rightNodeValue_ instanceof BigInt) {
         BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
         return this.doNumberAndBigInt(leftNodeValue_, rightNodeValue__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 3407872) == 0 && (state_0 & 262143) != 0
               ? leftNodeValue_int
               : ((state_0 & 1835008) == 0 && (state_0 & 262143) != 0 ? leftNodeValue_long : leftNodeValue_),
            rightNodeValue_
         );
      }
   }

   private boolean executeBoolean_generic6(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 3) != 0 && leftNodeValue_ instanceof Integer) {
         int leftNodeValue__ = (Integer)leftNodeValue_;
         if ((state_0 & 1) != 0 && rightNodeValue_ instanceof Integer) {
            int rightNodeValue__ = (Integer)rightNodeValue_;
            return this.doInt(leftNodeValue__, rightNodeValue__);
         }

         if ((state_0 & 2) != 0 && rightNodeValue_ instanceof SafeInteger) {
            SafeInteger rightNodeValue__ = (SafeInteger)rightNodeValue_;
            return this.doSafeInteger(leftNodeValue__, rightNodeValue__);
         }
      }

      if ((state_0 & 12) != 0 && leftNodeValue_ instanceof SafeInteger) {
         SafeInteger leftNodeValue__x = (SafeInteger)leftNodeValue_;
         if ((state_0 & 4) != 0 && rightNodeValue_ instanceof Integer) {
            int rightNodeValue__ = (Integer)rightNodeValue_;
            return this.doSafeInteger(leftNodeValue__x, rightNodeValue__);
         }

         if ((state_0 & 8) != 0 && rightNodeValue_ instanceof SafeInteger) {
            SafeInteger rightNodeValue__ = (SafeInteger)rightNodeValue_;
            return this.doSafeInteger(leftNodeValue__x, rightNodeValue__);
         }
      }

      if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3932160) >>> 18, leftNodeValue_)) {
         double leftNodeValue__xx = JSTypesGen.asImplicitDouble((state_0 & 3932160) >>> 18, leftNodeValue_);
         if (JSTypesGen.isImplicitDouble((state_0 & 62914560) >>> 22, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 62914560) >>> 22, rightNodeValue_);
            return this.doDouble(leftNodeValue__xx, rightNodeValue__);
         }
      }

      if ((state_0 & 96) != 0 && leftNodeValue_ instanceof TruffleString) {
         TruffleString leftNodeValue__xx = (TruffleString)leftNodeValue_;
         if ((state_0 & 32) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
            return this.doString(leftNodeValue__xx, rightNodeValue__, this.string_compareNode_);
         }

         if ((state_0 & 64) != 0 && JSTypesGen.isImplicitDouble((state_0 & 62914560) >>> 22, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 62914560) >>> 22, rightNodeValue_);
            return this.doStringDouble(leftNodeValue__xx, rightNodeValue__);
         }
      }

      if ((state_0 & 128) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3932160) >>> 18, leftNodeValue_)) {
         double leftNodeValue__xxx = JSTypesGen.asImplicitDouble((state_0 & 3932160) >>> 18, leftNodeValue_);
         if (rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
            return this.doDoubleString(leftNodeValue__xxx, rightNodeValue__);
         }
      }

      if ((state_0 & 256) != 0 && leftNodeValue_ instanceof TruffleString) {
         TruffleString leftNodeValue__xxx = (TruffleString)leftNodeValue_;
         if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            return this.doStringBigInt(leftNodeValue__xxx, rightNodeValue__);
         }
      }

      if ((state_0 & 7680) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__xxx = (BigInt)leftNodeValue_;
         if ((state_0 & 512) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
            return this.doBigIntString(leftNodeValue__xxx, rightNodeValue__);
         }

         if ((state_0 & 1024) != 0 && rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            return this.doBigInt(leftNodeValue__xxx, rightNodeValue__);
         }

         if ((state_0 & 2048) != 0 && rightNodeValue_ instanceof Integer) {
            int rightNodeValue__ = (Integer)rightNodeValue_;
            return this.doBigIntAndInt(leftNodeValue__xxx, rightNodeValue__);
         }

         if ((state_0 & 4096) != 0 && JSTypesGen.isImplicitDouble((state_0 & 62914560) >>> 22, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 62914560) >>> 22, rightNodeValue_);
            return this.doBigIntAndNumber(leftNodeValue__xxx, rightNodeValue__);
         }
      }

      if ((state_0 & 24576) != 0 && rightNodeValue_ instanceof BigInt) {
         BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
         if ((state_0 & 8192) != 0 && leftNodeValue_ instanceof Integer) {
            int leftNodeValue__xxxx = (Integer)leftNodeValue_;
            return this.doIntAndBigInt(leftNodeValue__xxxx, rightNodeValue__);
         }

         if ((state_0 & 16384) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3932160) >>> 18, leftNodeValue_)) {
            double leftNodeValue__xxxx = JSTypesGen.asImplicitDouble((state_0 & 3932160) >>> 18, leftNodeValue_);
            return this.doNumberAndBigInt(leftNodeValue__xxxx, rightNodeValue__);
         }
      }

      if ((state_0 & 229376) != 0) {
         if ((state_0 & 32768) != 0 && JSGuards.isJavaNumber(leftNodeValue_) && JSGuards.isJavaNumber(rightNodeValue_)) {
            return this.doJavaNumber(leftNodeValue_, rightNodeValue_);
         }

         if ((state_0 & 65536) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
            return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_);
         }

         if ((state_0 & 131072) != 0) {
            JSLessThanNodeGen.GenericData s17_ = this.generic_cache;
            if (s17_ != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
               return this.doGeneric(
                  leftNodeValue_, rightNodeValue_, s17_.toStringOrNumber1_, s17_.toPrimitive1_, s17_.toStringOrNumber2_, s17_.toPrimitive2_, s17_.lessThanNode_
               );
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.executeBoolean(frameValue);
   }

   private boolean executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
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
               int var41;
               this.state_0_ = var41 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doInt(leftNodeValue_, rightNodeValue_);
            }

            if (rightNodeValue instanceof SafeInteger) {
               SafeInteger rightNodeValue_ = (SafeInteger)rightNodeValue;
               int var40;
               this.state_0_ = var40 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doSafeInteger(leftNodeValue_, rightNodeValue_);
            }
         }

         if (leftNodeValue instanceof SafeInteger) {
            SafeInteger leftNodeValue_x = (SafeInteger)leftNodeValue;
            if (rightNodeValue instanceof Integer) {
               int rightNodeValue_ = (Integer)rightNodeValue;
               int var39;
               this.state_0_ = var39 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doSafeInteger(leftNodeValue_x, rightNodeValue_);
            }

            if (rightNodeValue instanceof SafeInteger) {
               SafeInteger rightNodeValue_ = (SafeInteger)rightNodeValue;
               int var38;
               this.state_0_ = var38 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doSafeInteger(leftNodeValue_x, rightNodeValue_);
            }
         }

         int doubleCast0;
         if ((exclude & 2) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
            double leftNodeValue_xx = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
            int doubleCast1;
            if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               state_0 |= doubleCast0 << 18;
               state_0 |= doubleCast1 << 22;
               int var37;
               this.state_0_ = var37 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doDouble(leftNodeValue_xx, rightNodeValue_);
            }
         }

         if (leftNodeValue instanceof TruffleString) {
            TruffleString leftNodeValue_xx = (TruffleString)leftNodeValue;
            if ((exclude & 4) == 0 && rightNodeValue instanceof TruffleString) {
               TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
               this.string_compareNode_ = super.insert(TruffleString.CompareCharsUTF16Node.create());
               int var34;
               this.state_0_ = var34 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.doString(leftNodeValue_xx, rightNodeValue_, this.string_compareNode_);
            }

            int doubleCast1;
            if ((exclude & 8) == 0 && (doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               state_0 |= doubleCast1 << 22;
               int var33;
               this.state_0_ = var33 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.doStringDouble(leftNodeValue_xx, rightNodeValue_);
            }
         }

         if ((exclude & 16) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
            double leftNodeValue_xxx = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
            if (rightNodeValue instanceof TruffleString) {
               TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
               state_0 |= doubleCast0 << 18;
               int var31;
               this.state_0_ = var31 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return this.doDoubleString(leftNodeValue_xxx, rightNodeValue_);
            }
         }

         if (leftNodeValue instanceof TruffleString) {
            TruffleString leftNodeValue_xxx = (TruffleString)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_ = (BigInt)rightNodeValue;
               int var29;
               this.state_0_ = var29 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return this.doStringBigInt(leftNodeValue_xxx, rightNodeValue_);
            }
         }

         if (leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_xxx = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof TruffleString) {
               TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
               int var28;
               this.state_0_ = var28 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               return this.doBigIntString(leftNodeValue_xxx, rightNodeValue_);
            }

            if ((exclude & 32) == 0 && rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_ = (BigInt)rightNodeValue;
               int var27;
               this.state_0_ = var27 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(leftNodeValue_xxx, rightNodeValue_);
            }

            if (rightNodeValue instanceof Integer) {
               int rightNodeValue_ = (Integer)rightNodeValue;
               int var26;
               this.state_0_ = var26 = state_0 | 2048;
               lock.unlock();
               hasLock = false;
               return this.doBigIntAndInt(leftNodeValue_xxx, rightNodeValue_);
            }

            int doubleCast1;
            if ((exclude & 64) == 0 && (doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               state_0 |= doubleCast1 << 22;
               int var25;
               this.state_0_ = var25 = state_0 | 4096;
               lock.unlock();
               hasLock = false;
               return this.doBigIntAndNumber(leftNodeValue_xxx, rightNodeValue_);
            }
         }

         if (rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_ = (BigInt)rightNodeValue;
            if (leftNodeValue instanceof Integer) {
               int leftNodeValue_xxxx = (Integer)leftNodeValue;
               int var23;
               this.state_0_ = var23 = state_0 | 8192;
               lock.unlock();
               hasLock = false;
               return this.doIntAndBigInt(leftNodeValue_xxxx, rightNodeValue_);
            }

            int doubleCast0x;
            if ((exclude & 128) == 0 && (doubleCast0x = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
               double leftNodeValue_xxxx = JSTypesGen.asImplicitDouble(doubleCast0x, leftNodeValue);
               state_0 |= doubleCast0x << 18;
               int var22;
               this.state_0_ = var22 = state_0 | 16384;
               lock.unlock();
               hasLock = false;
               return this.doNumberAndBigInt(leftNodeValue_xxxx, rightNodeValue_);
            }
         }

         if ((exclude & 256) == 0 && JSGuards.isJavaNumber(leftNodeValue) && JSGuards.isJavaNumber(rightNodeValue)) {
            int var20;
            this.state_0_ = var20 = state_0 | 32768;
            lock.unlock();
            hasLock = false;
            return this.doJavaNumber(leftNodeValue, rightNodeValue);
         } else if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createHintNumberLeftToRight(this.getOverloadedOperatorName()));
            this.overloaded_toBooleanNode_ = super.insert(JSToBooleanNode.create());
            int var17;
            this.state_0_ = var17 = state_0 | 65536;
            lock.unlock();
            hasLock = false;
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_);
         } else if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            throw new UnsupportedSpecializationException(this, new Node[]{super.leftNode, super.rightNode}, leftNodeValue, rightNodeValue);
         } else {
            JSLessThanNodeGen.GenericData s17_ = super.insert(new JSLessThanNodeGen.GenericData());
            s17_.toStringOrNumber1_ = s17_.insertAccessor(JSToStringOrNumberNode.create());
            s17_.toPrimitive1_ = s17_.insertAccessor(JSToPrimitiveNode.createHintNumber());
            s17_.toStringOrNumber2_ = s17_.insertAccessor(JSToStringOrNumberNode.create());
            s17_.toPrimitive2_ = s17_.insertAccessor(JSToPrimitiveNode.createHintNumber());
            s17_.lessThanNode_ = s17_.insertAccessor(JSLessThanNode.create());
            VarHandle.storeStoreFence();
            this.generic_cache = s17_;
            int var42;
            this.exclude_ = var42 = exclude | 511;
            state_0 &= -54514;
            int var19;
            this.state_0_ = var19 = state_0 | 131072;
            lock.unlock();
            hasLock = false;
            return this.doGeneric(
               leftNodeValue, rightNodeValue, s17_.toStringOrNumber1_, s17_.toPrimitive1_, s17_.toStringOrNumber2_, s17_.toPrimitive2_, s17_.lessThanNode_
            );
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
      if ((state_0 & 262143) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 262143 & (state_0 & 262143) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[19];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doInt", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.string_compareNode_));
         s[2] = cached;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doStringDouble", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 8) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doDoubleString", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 16) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doStringBigInt", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doBigIntString", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 32) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doBigIntAndInt", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      s = new Object[]{"doBigIntAndNumber", null, null};
      if ((state_0 & 4096) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 64) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      s = new Object[]{"doIntAndBigInt", null, null};
      if ((state_0 & 8192) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[14] = s;
      s = new Object[]{"doNumberAndBigInt", null, null};
      if ((state_0 & 16384) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 128) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[15] = s;
      s = new Object[]{"doJavaNumber", null, null};
      if ((state_0 & 32768) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 256) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[16] = s;
      s = new Object[]{"doOverloaded", null, null};
      if ((state_0 & 65536) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[17] = s;
      s = new Object[]{"doGeneric", null, null};
      if ((state_0 & 131072) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSLessThanNodeGen.GenericData s17_ = this.generic_cache;
         if (s17_ != null) {
            cached.add(Arrays.asList(s17_.toStringOrNumber1_, s17_.toPrimitive1_, s17_.toStringOrNumber2_, s17_.toPrimitive2_, s17_.lessThanNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[18] = s;
      return Introspection.Provider.create(data);
   }

   public static JSLessThanNode create(JavaScriptNode left, JavaScriptNode right) {
      return new JSLessThanNodeGen(left, right);
   }

   @GeneratedBy(JSLessThanNode.class)
   private static final class GenericData extends Node {
      @Node.Child
      JSToStringOrNumberNode toStringOrNumber1_;
      @Node.Child
      JSToPrimitiveNode toPrimitive1_;
      @Node.Child
      JSToStringOrNumberNode toStringOrNumber2_;
      @Node.Child
      JSToPrimitiveNode toPrimitive2_;
      @Node.Child
      JSLessThanNode lessThanNode_;

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
