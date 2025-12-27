package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.JSOverloadedUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSAddSubNumericUnitNode.class)
public final class JSAddSubNumericUnitNodeGen extends JSAddSubNumericUnitNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSOverloadedUnaryNode overloaded_overloadedOperatorNode_;

   private JSAddSubNumericUnitNodeGen(JavaScriptNode operand, boolean isAddition, boolean truncate) {
      super(operand, isAddition, truncate);
   }

   @Override
   public Object execute(Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;

         try {
            return this.doInt(operandNodeValue_);
         } catch (ArithmeticException var10) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();

            try {
               this.exclude_ |= 1;
               this.state_0_ &= -2;
            } finally {
               lock.unlock();
            }

            return this.executeAndSpecialize(operandNodeValue_);
         }
      } else if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 480) >>> 5, operandNodeValue)) {
         double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 480) >>> 5, operandNodeValue);
         return this.doDouble(operandNodeValue_);
      } else if ((state_0 & 4) != 0 && operandNodeValue instanceof BigInt) {
         BigInt operandNodeValue_ = (BigInt)operandNodeValue;
         return this.doBigInt(operandNodeValue_);
      } else if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(operandNodeValue)) {
         return this.doJavaNumber(operandNodeValue);
      } else if ((state_0 & 16) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
         return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;

         try {
            return this.doInt(operandNodeValue_);
         } catch (ArithmeticException var11) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();

            try {
               this.exclude_ |= 1;
               this.state_0_ &= -2;
            } finally {
               lock.unlock();
            }

            return this.executeAndSpecialize(operandNodeValue_);
         }
      } else if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 480) >>> 5, operandNodeValue)) {
         double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 480) >>> 5, operandNodeValue);
         return this.doDouble(operandNodeValue_);
      } else if ((state_0 & 4) != 0 && operandNodeValue instanceof BigInt) {
         BigInt operandNodeValue_ = (BigInt)operandNodeValue;
         return this.doBigInt(operandNodeValue_);
      } else if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(operandNodeValue)) {
         return this.doJavaNumber(operandNodeValue);
      } else if ((state_0 & 16) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
         return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 30) == 0 && (state_0 & 31) != 0) {
         return this.execute_int0(state_0, frameValue);
      } else {
         return (state_0 & 29) == 0 && (state_0 & 31) != 0 ? this.execute_double1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
      }
   }

   private Object execute_int0(int state_0, VirtualFrame frameValue) {
      int operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeInt(frameValue);
      } catch (UnexpectedResultException var12) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var12.getResult());
      }

      assert (state_0 & 1) != 0;

      try {
         return this.doInt(operandNodeValue_);
      } catch (ArithmeticException var11) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Lock lock = this.getLock();
         lock.lock();

         try {
            this.exclude_ |= 1;
            this.state_0_ &= -2;
         } finally {
            lock.unlock();
         }

         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   private Object execute_double1(int state_0, VirtualFrame frameValue) {
      long operandNodeValue_long = 0L;
      int operandNodeValue_int = 0;

      double operandNodeValue_;
      try {
         if ((state_0 & 448) == 0 && (state_0 & 31) != 0) {
            operandNodeValue_ = super.operandNode.executeDouble(frameValue);
         } else if ((state_0 & 416) == 0 && (state_0 & 31) != 0) {
            operandNodeValue_int = super.operandNode.executeInt(frameValue);
            operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
         } else if ((state_0 & 224) == 0 && (state_0 & 31) != 0) {
            operandNodeValue_long = super.operandNode.executeLong(frameValue);
            operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
         } else {
            Object operandNodeValue__ = super.operandNode.execute(frameValue);
            operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 480) >>> 5, operandNodeValue__);
         }
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var9.getResult());
      }

      assert (state_0 & 2) != 0;

      return this.doDouble(operandNodeValue_);
   }

   private Object execute_generic2(int state_0, VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
         int operandNodeValue__ = (Integer)operandNodeValue_;

         try {
            return this.doInt(operandNodeValue__);
         } catch (ArithmeticException var11) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();

            try {
               this.exclude_ |= 1;
               this.state_0_ &= -2;
            } finally {
               lock.unlock();
            }

            return this.executeAndSpecialize(operandNodeValue__);
         }
      } else if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 480) >>> 5, operandNodeValue_)) {
         double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 480) >>> 5, operandNodeValue_);
         return this.doDouble(operandNodeValue__);
      } else if ((state_0 & 4) != 0 && operandNodeValue_ instanceof BigInt) {
         BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
         return this.doBigInt(operandNodeValue__);
      } else if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(operandNodeValue_)) {
         return this.doJavaNumber(operandNodeValue_);
      } else if ((state_0 & 16) != 0 && operandNodeValue_ instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue__ = (JSOverloadedOperatorsObject)operandNodeValue_;
         return this.doOverloaded(operandNodeValue__, this.overloaded_overloadedOperatorNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 16) != 0) {
         return JSTypesGen.expectDouble(this.execute(frameValue));
      } else {
         return (state_0 & 8) == 0 && (state_0 & 10) != 0 ? this.executeDouble_double3(state_0, frameValue) : this.executeDouble_generic4(state_0, frameValue);
      }
   }

   private double executeDouble_double3(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      long operandNodeValue_long = 0L;
      int operandNodeValue_int = 0;

      double operandNodeValue_;
      try {
         if ((state_0 & 448) == 0 && (state_0 & 31) != 0) {
            operandNodeValue_ = super.operandNode.executeDouble(frameValue);
         } else if ((state_0 & 416) == 0 && (state_0 & 31) != 0) {
            operandNodeValue_int = super.operandNode.executeInt(frameValue);
            operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
         } else if ((state_0 & 224) == 0 && (state_0 & 31) != 0) {
            operandNodeValue_long = super.operandNode.executeLong(frameValue);
            operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
         } else {
            Object operandNodeValue__ = super.operandNode.execute(frameValue);
            operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 480) >>> 5, operandNodeValue__);
         }
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectDouble(this.executeAndSpecialize(var9.getResult()));
      }

      assert (state_0 & 2) != 0;

      return this.doDouble(operandNodeValue_);
   }

   private double executeDouble_generic4(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 480) >>> 5, operandNodeValue_)) {
         double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 480) >>> 5, operandNodeValue_);
         return this.doDouble(operandNodeValue__);
      } else if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(operandNodeValue_)) {
         return this.doJavaNumber(operandNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectDouble(this.executeAndSpecialize(operandNodeValue_));
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 16) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else {
         int operandNodeValue_;
         try {
            operandNodeValue_ = super.operandNode.executeInt(frameValue);
         } catch (UnexpectedResultException var12) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(var12.getResult()));
         }

         if ((state_0 & 1) != 0) {
            try {
               return this.doInt(operandNodeValue_);
            } catch (ArithmeticException var11) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 1;
                  this.state_0_ &= -2;
               } finally {
                  lock.unlock();
               }

               return JSTypesGen.expectInteger(this.executeAndSpecialize(operandNodeValue_));
            }
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(operandNodeValue_));
         }
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 30) == 0 && (state_0 & 31) != 0) {
            this.executeInt(frameValue);
         } else if ((state_0 & 21) == 0 && (state_0 & 31) != 0) {
            this.executeDouble(frameValue);
         } else {
            this.execute(frameValue);
         }
      } catch (UnexpectedResultException var4) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }
   }

   private Object executeAndSpecialize(Object operandNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var8;
      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude != 0 || !(operandNodeValue instanceof Integer)) {
            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
               double operandNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
               int var27;
               this.exclude_ = var27 = exclude | 1;
               state_0 &= -2;
               state_0 |= doubleCast0 << 5;
               int var26;
               this.state_0_ = var26 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doDouble(operandNodeValue_);
            }

            if (operandNodeValue instanceof BigInt) {
               BigInt operandNodeValue_ = (BigInt)operandNodeValue;
               int var23;
               this.state_0_ = var23 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(operandNodeValue_);
            }

            if (!JSGuards.isJavaNumber(operandNodeValue)) {
               if (!(operandNodeValue instanceof JSOverloadedOperatorsObject)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{super.operandNode}, operandNodeValue);
               }

               JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
               this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedUnaryNode.create(this.getOverloadedOperatorName()));
               int var22;
               this.state_0_ = var22 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
            }

            int var21;
            this.state_0_ = var21 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.doJavaNumber(operandNodeValue);
         }

         int operandNodeValue_ = (Integer)operandNodeValue;
         int var20;
         this.state_0_ = var20 = state_0 | 1;

         try {
            lock.unlock();
            hasLock = false;
            return this.doInt(operandNodeValue_);
         } catch (ArithmeticException var18) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            lock.lock();

            try {
               this.exclude_ |= 1;
               this.state_0_ &= -2;
            } finally {
               lock.unlock();
            }

            var8 = this.executeAndSpecialize(operandNodeValue_);
         }
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var8;
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if ((state_0 & 31) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 31 & (state_0 & 31) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[6];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doInt", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doJavaNumber", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
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
      return Introspection.Provider.create(data);
   }

   public static JSAddSubNumericUnitNode create(JavaScriptNode operand, boolean isAddition, boolean truncate) {
      return new JSAddSubNumericUnitNodeGen(operand, isAddition, truncate);
   }
}
