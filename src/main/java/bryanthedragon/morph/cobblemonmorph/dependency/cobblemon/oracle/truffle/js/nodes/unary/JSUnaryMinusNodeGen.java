package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSUnaryMinusNode.class)
public final class JSUnaryMinusNodeGen extends JSUnaryMinusNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSOverloadedUnaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSToNumericNode generic_toNumericNode_;
   @Node.Child
   private JSUnaryMinusNode generic_recursiveUnaryMinus_;

   private JSUnaryMinusNodeGen(JavaScriptNode operand) {
      super(operand);
   }

   @Override
   public Object execute(Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;
         if (JSUnaryMinusNode.isInt(operandNodeValue_)) {
            return JSUnaryMinusNode.doInt(operandNodeValue_);
         }
      }

      if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 480) >>> 5, operandNodeValue)) {
         double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 480) >>> 5, operandNodeValue);
         return JSUnaryMinusNode.doDouble(operandNodeValue_);
      } else if ((state_0 & 4) != 0 && operandNodeValue instanceof BigInt) {
         BigInt operandNodeValue_ = (BigInt)operandNodeValue;
         return JSUnaryMinusNode.doBigInt(operandNodeValue_);
      } else if ((state_0 & 8) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
         return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
      } else if ((state_0 & 16) != 0 && !this.hasOverloadedOperators(operandNodeValue)) {
         return JSUnaryMinusNode.doGeneric(operandNodeValue, this.generic_toNumericNode_, this.generic_recursiveUnaryMinus_);
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
         if (JSUnaryMinusNode.isInt(operandNodeValue_)) {
            return JSUnaryMinusNode.doInt(operandNodeValue_);
         }
      }

      if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 480) >>> 5, operandNodeValue)) {
         double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 480) >>> 5, operandNodeValue);
         return JSUnaryMinusNode.doDouble(operandNodeValue_);
      } else if ((state_0 & 4) != 0 && operandNodeValue instanceof BigInt) {
         BigInt operandNodeValue_ = (BigInt)operandNodeValue;
         return JSUnaryMinusNode.doBigInt(operandNodeValue_);
      } else if ((state_0 & 8) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
         return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
      } else if ((state_0 & 16) != 0 && !this.hasOverloadedOperators(operandNodeValue)) {
         return JSUnaryMinusNode.doGeneric(operandNodeValue, this.generic_toNumericNode_, this.generic_recursiveUnaryMinus_);
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
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 1) != 0;

      if (JSUnaryMinusNode.isInt(operandNodeValue_)) {
         return JSUnaryMinusNode.doInt(operandNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
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

      return JSUnaryMinusNode.doDouble(operandNodeValue_);
   }

   private Object execute_generic2(int state_0, VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
         int operandNodeValue__ = (Integer)operandNodeValue_;
         if (JSUnaryMinusNode.isInt(operandNodeValue__)) {
            return JSUnaryMinusNode.doInt(operandNodeValue__);
         }
      }

      if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 480) >>> 5, operandNodeValue_)) {
         double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 480) >>> 5, operandNodeValue_);
         return JSUnaryMinusNode.doDouble(operandNodeValue__);
      } else if ((state_0 & 4) != 0 && operandNodeValue_ instanceof BigInt) {
         BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
         return JSUnaryMinusNode.doBigInt(operandNodeValue__);
      } else if ((state_0 & 8) != 0 && operandNodeValue_ instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue__ = (JSOverloadedOperatorsObject)operandNodeValue_;
         return this.doOverloaded(operandNodeValue__, this.overloaded_overloadedOperatorNode_);
      } else if ((state_0 & 16) != 0 && !this.hasOverloadedOperators(operandNodeValue_)) {
         return JSUnaryMinusNode.doGeneric(operandNodeValue_, this.generic_toNumericNode_, this.generic_recursiveUnaryMinus_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 24) != 0) {
         return JSTypesGen.expectDouble(this.execute(frameValue));
      } else {
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

         if ((state_0 & 2) != 0) {
            return JSUnaryMinusNode.doDouble(operandNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(
               this.executeAndSpecialize(
                  (state_0 & 416) == 0 && (state_0 & 31) != 0
                     ? operandNodeValue_int
                     : ((state_0 & 224) == 0 && (state_0 & 31) != 0 ? operandNodeValue_long : operandNodeValue_)
               )
            );
         }
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 24) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else {
         int operandNodeValue_;
         try {
            operandNodeValue_ = super.operandNode.executeInt(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(var5.getResult()));
         }

         if ((state_0 & 1) != 0 && JSUnaryMinusNode.isInt(operandNodeValue_)) {
            return JSUnaryMinusNode.doInt(operandNodeValue_);
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
         } else if ((state_0 & 29) == 0 && (state_0 & 31) != 0) {
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

      try {
         int state_0 = this.state_0_;
         if (operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            if (JSUnaryMinusNode.isInt(operandNodeValue_)) {
               int var17;
               this.state_0_ = var17 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return JSUnaryMinusNode.doInt(operandNodeValue_);
            }
         }

         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
            state_0 |= doubleCast0 << 5;
            int var16;
            this.state_0_ = var16 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return JSUnaryMinusNode.doDouble(operandNodeValue_);
         } else if (operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            int var14;
            this.state_0_ = var14 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return JSUnaryMinusNode.doBigInt(operandNodeValue_);
         } else if (operandNodeValue instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
            this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedUnaryNode.create(this.getOverloadedOperatorName()));
            int var12;
            this.state_0_ = var12 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
         } else if (this.hasOverloadedOperators(operandNodeValue)) {
            throw new UnsupportedSpecializationException(this, new Node[]{super.operandNode}, operandNodeValue);
         } else {
            this.generic_toNumericNode_ = super.insert(JSToNumericNode.create());
            this.generic_recursiveUnaryMinus_ = super.insert(JSUnaryMinusNode.create());
            int var13;
            this.state_0_ = var13 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return JSUnaryMinusNode.doGeneric(operandNodeValue, this.generic_toNumericNode_, this.generic_recursiveUnaryMinus_);
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
      Object[] s = new Object[]{"doInt", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
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
      s = new Object[]{"doOverloaded", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doGeneric", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.generic_toNumericNode_, this.generic_recursiveUnaryMinus_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      return Introspection.Provider.create(data);
   }

   public static JSUnaryMinusNode create(JavaScriptNode operand) {
      return new JSUnaryMinusNodeGen(operand);
   }
}
