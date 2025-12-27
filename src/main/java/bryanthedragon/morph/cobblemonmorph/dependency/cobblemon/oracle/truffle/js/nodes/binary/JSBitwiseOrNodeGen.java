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
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSBitwiseOrNode.class)
public final class JSBitwiseOrNodeGen extends JSBitwiseOrNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSToInt32Node double_leftInt32_;
   @Node.Child
   private JSToInt32Node double_rightInt32_;
   @Node.Child
   private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSBitwiseOrNodeGen.GenericData generic_cache;

   private JSBitwiseOrNodeGen(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   @Override
   public Object executeObject(Object leftNodeValue, Object rightNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 3) != 0 && rightNodeValue instanceof Integer) {
         int rightNodeValue_ = (Integer)rightNodeValue;
         if ((state_0 & 1) != 0 && leftNodeValue instanceof Integer) {
            int leftNodeValue_ = (Integer)leftNodeValue;
            return this.doInteger(leftNodeValue_, rightNodeValue_);
         }

         if ((state_0 & 2) != 0 && leftNodeValue instanceof SafeInteger) {
            SafeInteger leftNodeValue_ = (SafeInteger)leftNodeValue;
            return this.doSafeIntegerInt(leftNodeValue_, rightNodeValue_);
         }
      }

      if ((state_0 & 12) != 0 && rightNodeValue instanceof SafeInteger) {
         SafeInteger rightNodeValue_x = (SafeInteger)rightNodeValue;
         if ((state_0 & 4) != 0 && leftNodeValue instanceof Integer) {
            int leftNodeValue_ = (Integer)leftNodeValue;
            return this.doIntSafeInteger(leftNodeValue_, rightNodeValue_x);
         }

         if ((state_0 & 8) != 0 && leftNodeValue instanceof SafeInteger) {
            SafeInteger leftNodeValue_ = (SafeInteger)leftNodeValue;
            return this.doSafeInteger(leftNodeValue_, rightNodeValue_x);
         }
      }

      if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3840) >>> 8, leftNodeValue)) {
         double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 3840) >>> 8, leftNodeValue);
         if (JSTypesGen.isImplicitDouble((state_0 & 61440) >>> 12, rightNodeValue)) {
            double rightNodeValue_xx = JSTypesGen.asImplicitDouble((state_0 & 61440) >>> 12, rightNodeValue);
            return this.doDouble(leftNodeValue_, rightNodeValue_xx, this.double_leftInt32_, this.double_rightInt32_);
         }
      }

      if ((state_0 & 32) != 0 && leftNodeValue instanceof BigInt) {
         BigInt leftNodeValue_ = (BigInt)leftNodeValue;
         if (rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_xx = (BigInt)rightNodeValue;
            return this.doBigInt(leftNodeValue_, rightNodeValue_xx);
         }
      }

      if ((state_0 & 192) != 0) {
         if ((state_0 & 64) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
         }

         if ((state_0 & 128) != 0) {
            JSBitwiseOrNodeGen.GenericData s7_ = this.generic_cache;
            if (s7_ != null && !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
               return this.doGeneric(leftNodeValue, rightNodeValue, s7_.leftNumeric_, s7_.rightNumeric_, s7_.or_, s7_.mixedNumericTypes_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 254) == 0 && (state_0 & 0xFF) != 0) {
         return this.execute_int_int0(state_0, frameValue);
      } else if ((state_0 & 239) == 0 && (state_0 & 0xFF) != 0) {
         return this.execute_double_double1(state_0, frameValue);
      } else if ((state_0 & 253) == 0 && (state_0 & 0xFF) != 0) {
         return this.execute_int2(state_0, frameValue);
      } else {
         return (state_0 & 251) == 0 && (state_0 & 0xFF) != 0 ? this.execute_int3(state_0, frameValue) : this.execute_generic4(state_0, frameValue);
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

   private Object execute_double_double1(int state_0, VirtualFrame frameValue) {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 3584) == 0 && (state_0 & 0xFF) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 3328) == 0 && (state_0 & 0xFF) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 1792) == 0 && (state_0 & 0xFF) != 0) {
            leftNodeValue_long = super.leftNode.executeLong(frameValue);
            leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
         } else {
            Object leftNodeValue__ = super.leftNode.execute(frameValue);
            leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 3840) >>> 8, leftNodeValue__);
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
         if ((state_0 & 57344) == 0 && (state_0 & 0xFF) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 53248) == 0 && (state_0 & 0xFF) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 28672) == 0 && (state_0 & 0xFF) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 61440) >>> 12, rightNodeValue__);
         }
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 3328) == 0 && (state_0 & 0xFF) != 0
               ? leftNodeValue_int
               : ((state_0 & 1792) == 0 && (state_0 & 0xFF) != 0 ? leftNodeValue_long : leftNodeValue_),
            var14.getResult()
         );
      }

      assert (state_0 & 16) != 0;

      return this.doDouble(leftNodeValue_, rightNodeValue_, this.double_leftInt32_, this.double_rightInt32_);
   }

   private Object execute_int2(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);

      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var6.getResult());
      }

      assert (state_0 & 2) != 0;

      if (leftNodeValue_ instanceof SafeInteger) {
         SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
         return this.doSafeIntegerInt(leftNodeValue__, rightNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   private Object execute_int3(int state_0, VirtualFrame frameValue) {
      int leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeInt(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var6.getResult(), rightNodeValue);
      }

      Object rightNodeValue_ = super.rightNode.execute(frameValue);

      assert (state_0 & 4) != 0;

      if (rightNodeValue_ instanceof SafeInteger) {
         SafeInteger rightNodeValue__ = (SafeInteger)rightNodeValue_;
         return this.doIntSafeInteger(leftNodeValue_, rightNodeValue__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   private Object execute_generic4(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 3) != 0 && rightNodeValue_ instanceof Integer) {
         int rightNodeValue__ = (Integer)rightNodeValue_;
         if ((state_0 & 1) != 0 && leftNodeValue_ instanceof Integer) {
            int leftNodeValue__ = (Integer)leftNodeValue_;
            return this.doInteger(leftNodeValue__, rightNodeValue__);
         }

         if ((state_0 & 2) != 0 && leftNodeValue_ instanceof SafeInteger) {
            SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
            return this.doSafeIntegerInt(leftNodeValue__, rightNodeValue__);
         }
      }

      if ((state_0 & 12) != 0 && rightNodeValue_ instanceof SafeInteger) {
         SafeInteger rightNodeValue__x = (SafeInteger)rightNodeValue_;
         if ((state_0 & 4) != 0 && leftNodeValue_ instanceof Integer) {
            int leftNodeValue__ = (Integer)leftNodeValue_;
            return this.doIntSafeInteger(leftNodeValue__, rightNodeValue__x);
         }

         if ((state_0 & 8) != 0 && leftNodeValue_ instanceof SafeInteger) {
            SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
            return this.doSafeInteger(leftNodeValue__, rightNodeValue__x);
         }
      }

      if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3840) >>> 8, leftNodeValue_)) {
         double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 3840) >>> 8, leftNodeValue_);
         if (JSTypesGen.isImplicitDouble((state_0 & 61440) >>> 12, rightNodeValue_)) {
            double rightNodeValue__xx = JSTypesGen.asImplicitDouble((state_0 & 61440) >>> 12, rightNodeValue_);
            return this.doDouble(leftNodeValue__, rightNodeValue__xx, this.double_leftInt32_, this.double_rightInt32_);
         }
      }

      if ((state_0 & 32) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
         if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__xx = (BigInt)rightNodeValue_;
            return this.doBigInt(leftNodeValue__, rightNodeValue__xx);
         }
      }

      if ((state_0 & 192) != 0) {
         if ((state_0 & 64) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
            return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_);
         }

         if ((state_0 & 128) != 0) {
            JSBitwiseOrNodeGen.GenericData s7_ = this.generic_cache;
            if (s7_ != null && !this.hasOverloadedOperators(leftNodeValue_) && !this.hasOverloadedOperators(rightNodeValue_)) {
               return this.doGeneric(leftNodeValue_, rightNodeValue_, s7_.leftNumeric_, s7_.rightNumeric_, s7_.or_, s7_.mixedNumericTypes_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 192) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else if ((state_0 & 30) == 0 && (state_0 & 31) != 0) {
         return this.executeInt_int_int5(state_0, frameValue);
      } else if ((state_0 & 15) == 0 && (state_0 & 31) != 0) {
         return this.executeInt_double_double6(state_0, frameValue);
      } else if ((state_0 & 29) == 0 && (state_0 & 31) != 0) {
         return this.executeInt_int7(state_0, frameValue);
      } else {
         return (state_0 & 27) == 0 && (state_0 & 31) != 0 ? this.executeInt_int8(state_0, frameValue) : this.executeInt_generic9(state_0, frameValue);
      }
   }

   private int executeInt_int_int5(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
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

      assert (state_0 & 1) != 0;

      return this.doInteger(leftNodeValue_, rightNodeValue_);
   }

   private int executeInt_double_double6(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 3584) == 0 && (state_0 & 0xFF) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 3328) == 0 && (state_0 & 0xFF) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 1792) == 0 && (state_0 & 0xFF) != 0) {
            leftNodeValue_long = super.leftNode.executeLong(frameValue);
            leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
         } else {
            Object leftNodeValue__ = super.leftNode.execute(frameValue);
            leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 3840) >>> 8, leftNodeValue__);
         }
      } catch (UnexpectedResultException var15) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return JSTypesGen.expectInteger(this.executeAndSpecialize(var15.getResult(), rightNodeValue));
      }

      long rightNodeValue_long = 0L;
      int rightNodeValue_int = 0;

      double rightNodeValue_;
      try {
         if ((state_0 & 57344) == 0 && (state_0 & 0xFF) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 53248) == 0 && (state_0 & 0xFF) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 28672) == 0 && (state_0 & 0xFF) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 61440) >>> 12, rightNodeValue__);
         }
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(
            this.executeAndSpecialize(
               (state_0 & 3328) == 0 && (state_0 & 0xFF) != 0
                  ? leftNodeValue_int
                  : ((state_0 & 1792) == 0 && (state_0 & 0xFF) != 0 ? leftNodeValue_long : leftNodeValue_),
               var14.getResult()
            )
         );
      }

      assert (state_0 & 16) != 0;

      return this.doDouble(leftNodeValue_, rightNodeValue_, this.double_leftInt32_, this.double_rightInt32_);
   }

   private int executeInt_int7(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);

      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, var6.getResult()));
      }

      assert (state_0 & 2) != 0;

      if (leftNodeValue_ instanceof SafeInteger) {
         SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
         return this.doSafeIntegerInt(leftNodeValue__, rightNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
      }
   }

   private int executeInt_int8(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      int leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeInt(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return JSTypesGen.expectInteger(this.executeAndSpecialize(var6.getResult(), rightNodeValue));
      }

      Object rightNodeValue_ = super.rightNode.execute(frameValue);

      assert (state_0 & 4) != 0;

      if (rightNodeValue_ instanceof SafeInteger) {
         SafeInteger rightNodeValue__ = (SafeInteger)rightNodeValue_;
         return this.doIntSafeInteger(leftNodeValue_, rightNodeValue__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
      }
   }

   private int executeInt_generic9(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 3) != 0 && rightNodeValue_ instanceof Integer) {
         int rightNodeValue__ = (Integer)rightNodeValue_;
         if ((state_0 & 1) != 0 && leftNodeValue_ instanceof Integer) {
            int leftNodeValue__ = (Integer)leftNodeValue_;
            return this.doInteger(leftNodeValue__, rightNodeValue__);
         }

         if ((state_0 & 2) != 0 && leftNodeValue_ instanceof SafeInteger) {
            SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
            return this.doSafeIntegerInt(leftNodeValue__, rightNodeValue__);
         }
      }

      if ((state_0 & 12) != 0 && rightNodeValue_ instanceof SafeInteger) {
         SafeInteger rightNodeValue__x = (SafeInteger)rightNodeValue_;
         if ((state_0 & 4) != 0 && leftNodeValue_ instanceof Integer) {
            int leftNodeValue__ = (Integer)leftNodeValue_;
            return this.doIntSafeInteger(leftNodeValue__, rightNodeValue__x);
         }

         if ((state_0 & 8) != 0 && leftNodeValue_ instanceof SafeInteger) {
            SafeInteger leftNodeValue__ = (SafeInteger)leftNodeValue_;
            return this.doSafeInteger(leftNodeValue__, rightNodeValue__x);
         }
      }

      if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3840) >>> 8, leftNodeValue_)) {
         double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 3840) >>> 8, leftNodeValue_);
         if (JSTypesGen.isImplicitDouble((state_0 & 61440) >>> 12, rightNodeValue_)) {
            double rightNodeValue__xx = JSTypesGen.asImplicitDouble((state_0 & 61440) >>> 12, rightNodeValue_);
            return this.doDouble(leftNodeValue__, rightNodeValue__xx, this.double_leftInt32_, this.double_rightInt32_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return JSTypesGen.expectInteger(this.executeAndSpecialize(leftNodeValue_, rightNodeValue_));
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 224) == 0 && (state_0 & 0xFF) != 0) {
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
         if (rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;
            if ((exclude & 1) == 0 && leftNodeValue instanceof Integer) {
               int leftNodeValue_ = (Integer)leftNodeValue;
               int var27;
               this.state_0_ = var27 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doInteger(leftNodeValue_, rightNodeValue_);
            }

            if ((exclude & 2) == 0 && leftNodeValue instanceof SafeInteger) {
               SafeInteger leftNodeValue_ = (SafeInteger)leftNodeValue;
               int var26;
               this.state_0_ = var26 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doSafeIntegerInt(leftNodeValue_, rightNodeValue_);
            }
         }

         if (rightNodeValue instanceof SafeInteger) {
            SafeInteger rightNodeValue_x = (SafeInteger)rightNodeValue;
            if ((exclude & 4) == 0 && leftNodeValue instanceof Integer) {
               int leftNodeValue_ = (Integer)leftNodeValue;
               int var25;
               this.state_0_ = var25 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doIntSafeInteger(leftNodeValue_, rightNodeValue_x);
            }

            if ((exclude & 8) == 0 && leftNodeValue instanceof SafeInteger) {
               SafeInteger leftNodeValue_ = (SafeInteger)leftNodeValue;
               int var24;
               this.state_0_ = var24 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doSafeInteger(leftNodeValue_, rightNodeValue_x);
            }
         }

         int doubleCast0;
         if ((exclude & 16) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
            double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
            int doubleCast1;
            if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_xx = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               this.double_leftInt32_ = super.insert(JSToInt32Node.create());
               this.double_rightInt32_ = super.insert(JSToInt32Node.create());
               state_0 |= doubleCast0 << 8;
               state_0 |= doubleCast1 << 12;
               int var23;
               this.state_0_ = var23 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doDouble(leftNodeValue_, rightNodeValue_xx, this.double_leftInt32_, this.double_rightInt32_);
            }
         }

         if ((exclude & 32) == 0 && leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_ = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_xx = (BigInt)rightNodeValue;
               int var20;
               this.state_0_ = var20 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(leftNodeValue_, rightNodeValue_xx);
            }
         }

         if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
            int var17;
            this.state_0_ = var17 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_);
         } else if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            throw new UnsupportedSpecializationException(this, new Node[]{super.leftNode, super.rightNode}, leftNodeValue, rightNodeValue);
         } else {
            JSBitwiseOrNodeGen.GenericData s7_ = super.insert(new JSBitwiseOrNodeGen.GenericData());
            s7_.leftNumeric_ = s7_.insertAccessor(JSToNumericNode.create());
            s7_.rightNumeric_ = s7_.insertAccessor(JSToNumericNode.create());
            s7_.or_ = s7_.insertAccessor(JSBitwiseOrNode.createInner());
            s7_.mixedNumericTypes_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.generic_cache = s7_;
            int var28;
            this.exclude_ = var28 = exclude | 63;
            state_0 &= -64;
            int var19;
            this.state_0_ = var19 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return this.doGeneric(leftNodeValue, rightNodeValue, s7_.leftNumeric_, s7_.rightNumeric_, s7_.or_, s7_.mixedNumericTypes_);
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
      if ((state_0 & 0xFF) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 0xFF & (state_0 & 0xFF) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[9];
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
      s = new Object[]{"doSafeIntegerInt", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doIntSafeInteger", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 8) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.double_leftInt32_, this.double_rightInt32_));
         s[2] = cached;
      } else if ((exclude & 16) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 32) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doOverloaded", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doGeneric", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSBitwiseOrNodeGen.GenericData s7_ = this.generic_cache;
         if (s7_ != null) {
            cached.add(Arrays.asList(s7_.leftNumeric_, s7_.rightNumeric_, s7_.or_, s7_.mixedNumericTypes_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      return Introspection.Provider.create(data);
   }

   public static JSBitwiseOrNode create(JavaScriptNode left, JavaScriptNode right) {
      return new JSBitwiseOrNodeGen(left, right);
   }

   @GeneratedBy(JSBitwiseOrNode.class)
   private static final class GenericData extends Node {
      @Node.Child
      JSToNumericNode leftNumeric_;
      @Node.Child
      JSToNumericNode rightNumeric_;
      @Node.Child
      JSBitwiseOrNode or_;
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
