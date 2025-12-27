package com.oracle.truffle.js.nodes.binary;

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
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSLeftShiftConstantNode.class)
public final class JSLeftShiftConstantNodeGen extends JSLeftShiftConstantNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSToInt32Node double_leftInt32Node_;
   @Node.Child
   private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSToNumericNode generic_leftToNumericNode_;
   @Node.Child
   private JSLeftShiftConstantNode generic_innerShiftNode_;

   private JSLeftShiftConstantNodeGen(JavaScriptNode operand, int shiftValue) {
      super(operand, shiftValue);
   }

   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;
         return this.doInteger(operandNodeValue_);
      } else if ((state_0 & 2) != 0 && operandNodeValue instanceof SafeInteger) {
         SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
         return this.doSafeInteger(operandNodeValue_);
      } else if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, operandNodeValue)) {
         double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, operandNodeValue);
         return this.doDouble(operandNodeValue_, this.double_leftInt32Node_);
      } else if ((state_0 & 16) != 0 && operandNodeValue instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue_ = (JSOverloadedOperatorsObject)operandNodeValue;
         return this.doOverloaded(operandNodeValue_, this.overloaded_overloadedOperatorNode_);
      } else if ((state_0 & 32) != 0 && !this.hasOverloadedOperators(operandNodeValue)) {
         return this.doGeneric(operandNodeValue, this.generic_leftToNumericNode_, this.generic_innerShiftNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 54) == 0 && (state_0 & 55) != 0) {
         return this.execute_int0(state_0, frameValue);
      } else {
         return (state_0 & 51) == 0 && (state_0 & 55) != 0 ? this.execute_double1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
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

      return this.doInteger(operandNodeValue_);
   }

   private Object execute_double1(int state_0, VirtualFrame frameValue) {
      long operandNodeValue_long = 0L;
      int operandNodeValue_int = 0;

      double operandNodeValue_;
      try {
         if ((state_0 & 896) == 0 && (state_0 & 63) != 0) {
            operandNodeValue_ = super.operandNode.executeDouble(frameValue);
         } else if ((state_0 & 832) == 0 && (state_0 & 63) != 0) {
            operandNodeValue_int = super.operandNode.executeInt(frameValue);
            operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
         } else if ((state_0 & 448) == 0 && (state_0 & 63) != 0) {
            operandNodeValue_long = super.operandNode.executeLong(frameValue);
            operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
         } else {
            Object operandNodeValue__ = super.operandNode.execute(frameValue);
            operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 960) >>> 6, operandNodeValue__);
         }
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var9.getResult());
      }

      assert (state_0 & 4) != 0;

      return this.doDouble(operandNodeValue_, this.double_leftInt32Node_);
   }

   private Object execute_generic2(int state_0, VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
         int operandNodeValue__ = (Integer)operandNodeValue_;
         return this.doInteger(operandNodeValue__);
      } else if ((state_0 & 2) != 0 && operandNodeValue_ instanceof SafeInteger) {
         SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;
         return this.doSafeInteger(operandNodeValue__);
      } else if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, operandNodeValue_)) {
         double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, operandNodeValue_);
         return this.doDouble(operandNodeValue__, this.double_leftInt32Node_);
      } else if ((state_0 & 16) != 0 && operandNodeValue_ instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject operandNodeValue__ = (JSOverloadedOperatorsObject)operandNodeValue_;
         return this.doOverloaded(operandNodeValue__, this.overloaded_overloadedOperatorNode_);
      } else if ((state_0 & 32) != 0 && !this.hasOverloadedOperators(operandNodeValue_)) {
         return this.doGeneric(operandNodeValue_, this.generic_leftToNumericNode_, this.generic_innerShiftNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 48) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else if ((state_0 & 6) == 0 && (state_0 & 7) != 0) {
         return this.executeInt_int3(state_0, frameValue);
      } else {
         return (state_0 & 3) == 0 && (state_0 & 7) != 0 ? this.executeInt_double4(state_0, frameValue) : this.executeInt_generic5(state_0, frameValue);
      }
   }

   private int executeInt_int3(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      int operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeInt(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(var5.getResult()));
      }

      assert (state_0 & 1) != 0;

      return this.doInteger(operandNodeValue_);
   }

   private int executeInt_double4(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      long operandNodeValue_long = 0L;
      int operandNodeValue_int = 0;

      double operandNodeValue_;
      try {
         if ((state_0 & 896) == 0 && (state_0 & 63) != 0) {
            operandNodeValue_ = super.operandNode.executeDouble(frameValue);
         } else if ((state_0 & 832) == 0 && (state_0 & 63) != 0) {
            operandNodeValue_int = super.operandNode.executeInt(frameValue);
            operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
         } else if ((state_0 & 448) == 0 && (state_0 & 63) != 0) {
            operandNodeValue_long = super.operandNode.executeLong(frameValue);
            operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
         } else {
            Object operandNodeValue__ = super.operandNode.execute(frameValue);
            operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 960) >>> 6, operandNodeValue__);
         }
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(var9.getResult()));
      }

      assert (state_0 & 4) != 0;

      return this.doDouble(operandNodeValue_, this.double_leftInt32Node_);
   }

   private int executeInt_generic5(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
         int operandNodeValue__ = (Integer)operandNodeValue_;
         return this.doInteger(operandNodeValue__);
      } else if ((state_0 & 2) != 0 && operandNodeValue_ instanceof SafeInteger) {
         SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;
         return this.doSafeInteger(operandNodeValue__);
      } else if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, operandNodeValue_)) {
         double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, operandNodeValue_);
         return this.doDouble(operandNodeValue__, this.double_leftInt32Node_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(operandNodeValue_));
      }
   }

   @Override
   public int executeInt(Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 48) != 0) {
         return (Integer)this.execute(null, operandNodeValue);
      } else if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;
         return this.doInteger(operandNodeValue_);
      } else if ((state_0 & 2) != 0 && operandNodeValue instanceof SafeInteger) {
         SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
         return this.doSafeInteger(operandNodeValue_);
      } else if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, operandNodeValue)) {
         double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, operandNodeValue);
         return this.doDouble(operandNodeValue_, this.double_leftInt32Node_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return (Integer)this.executeAndSpecialize(operandNodeValue);
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 56) == 0 && (state_0 & 63) != 0) {
            this.executeInt(frameValue);
            return;
         }

         if ((state_0 & 55) != 0) {
            this.execute(frameValue);
            return;
         }
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return;
      }

      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 8) != 0 && operandNodeValue_ instanceof BigInt) {
         BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
         this.doBigInt(operandNodeValue__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(operandNodeValue_);
      }
   }

   private Object executeAndSpecialize(Object operandNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object operandNodeValue_;
      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if ((exclude & 1) == 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_x = (Integer)operandNodeValue;
            int var20;
            this.state_0_ = var20 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doInteger(operandNodeValue_x);
         }

         if ((exclude & 2) == 0 && operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_x = (SafeInteger)operandNodeValue;
            int var19;
            this.state_0_ = var19 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doSafeInteger(operandNodeValue_x);
         }

         int doubleCast0;
         if ((exclude & 4) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
            double operandNodeValue_x = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
            this.double_leftInt32Node_ = super.insert(JSToInt32Node.create());
            state_0 |= doubleCast0 << 6;
            int var18;
            this.state_0_ = var18 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doDouble(operandNodeValue_x, this.double_leftInt32Node_);
         }

         if ((exclude & 8) != 0 || !(operandNodeValue instanceof BigInt)) {
            if (!(operandNodeValue instanceof JSOverloadedOperatorsObject)) {
               if (this.hasOverloadedOperators(operandNodeValue)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{super.operandNode}, operandNodeValue);
               }

               this.generic_leftToNumericNode_ = super.insert(JSToNumericNode.create());
               this.generic_innerShiftNode_ = super.insert(this.makeCopy());
               int var21;
               this.exclude_ = var21 = exclude | 15;
               state_0 &= -16;
               int var16;
               this.state_0_ = var16 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.doGeneric(operandNodeValue, this.generic_leftToNumericNode_, this.generic_innerShiftNode_);
            }

            JSOverloadedOperatorsObject operandNodeValue_x = (JSOverloadedOperatorsObject)operandNodeValue;
            this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createNumeric(this.getOverloadedOperatorName()));
            int var14;
            this.state_0_ = var14 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return this.doOverloaded(operandNodeValue_x, this.overloaded_overloadedOperatorNode_);
         }

         BigInt operandNodeValue_x = (BigInt)operandNodeValue;
         int var13;
         this.state_0_ = var13 = state_0 | 8;
         lock.unlock();
         hasLock = false;
         this.doBigInt(operandNodeValue_x);
         operandNodeValue_ = null;
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return operandNodeValue_;
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
      s = new Object[]{"doSafeInteger", null, null};
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
         cached.add(Arrays.asList(this.double_leftInt32Node_));
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
         cached.add(Arrays.asList(this.generic_leftToNumericNode_, this.generic_innerShiftNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      return Introspection.Provider.create(data);
   }

   public static JSLeftShiftConstantNode create(JavaScriptNode operand, int shiftValue) {
      return new JSLeftShiftConstantNodeGen(operand, shiftValue);
   }
}
