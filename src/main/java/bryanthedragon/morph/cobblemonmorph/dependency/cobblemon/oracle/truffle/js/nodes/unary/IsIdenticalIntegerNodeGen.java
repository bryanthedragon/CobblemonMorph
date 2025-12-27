package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.BigInt;

@GeneratedBy(IsIdenticalIntegerNode.class)
public final class IsIdenticalIntegerNodeGen extends IsIdenticalIntegerNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private IsIdenticalIntegerNodeGen(JavaScriptNode operand, int integer, boolean leftConstant) {
      super(operand, integer, leftConstant);
   }

   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;
         return this.doInt(operandNodeValue_);
      } else if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 480) >>> 5, operandNodeValue)) {
         double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 480) >>> 5, operandNodeValue);
         return this.doDouble(operandNodeValue_);
      } else if ((state_0 & 4) != 0 && operandNodeValue instanceof BigInt) {
         BigInt operandNodeValue_ = (BigInt)operandNodeValue;
         return this.doBigInt(operandNodeValue_);
      } else {
         if ((state_0 & 24) != 0) {
            if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(operandNodeValue)) {
               return this.doJavaNumber(operandNodeValue);
            }

            if ((state_0 & 16) != 0 && fallbackGuard_(state_0, operandNodeValue)) {
               return this.doOther(operandNodeValue);
            }
         }

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

      return this.doInt(operandNodeValue_);
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
         return this.doInt(operandNodeValue__);
      } else if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 480) >>> 5, operandNodeValue_)) {
         double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 480) >>> 5, operandNodeValue_);
         return this.doDouble(operandNodeValue__);
      } else if ((state_0 & 4) != 0 && operandNodeValue_ instanceof BigInt) {
         BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
         return this.doBigInt(operandNodeValue__);
      } else {
         if ((state_0 & 24) != 0) {
            if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(operandNodeValue_)) {
               return this.doJavaNumber(operandNodeValue_);
            }

            if ((state_0 & 16) != 0 && fallbackGuard_(state_0, operandNodeValue_)) {
               return this.doOther(operandNodeValue_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 30) == 0 && (state_0 & 31) != 0) {
         return this.executeBoolean_int3(state_0, frameValue);
      } else {
         return (state_0 & 29) == 0 && (state_0 & 31) != 0
            ? this.executeBoolean_double4(state_0, frameValue)
            : this.executeBoolean_generic5(state_0, frameValue);
      }
   }

   private boolean executeBoolean_int3(int state_0, VirtualFrame frameValue) {
      int operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeInt(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 1) != 0;

      return this.doInt(operandNodeValue_);
   }

   private boolean executeBoolean_double4(int state_0, VirtualFrame frameValue) {
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

   private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Integer) {
         int operandNodeValue__ = (Integer)operandNodeValue_;
         return this.doInt(operandNodeValue__);
      } else if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 480) >>> 5, operandNodeValue_)) {
         double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 480) >>> 5, operandNodeValue_);
         return this.doDouble(operandNodeValue__);
      } else if ((state_0 & 4) != 0 && operandNodeValue_ instanceof BigInt) {
         BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
         return this.doBigInt(operandNodeValue__);
      } else {
         if ((state_0 & 24) != 0) {
            if ((state_0 & 8) != 0 && JSGuards.isJavaNumber(operandNodeValue_)) {
               return this.doJavaNumber(operandNodeValue_);
            }

            if ((state_0 & 16) != 0 && fallbackGuard_(state_0, operandNodeValue_)) {
               return this.doOther(operandNodeValue_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.executeBoolean(frameValue);
   }

   private boolean executeAndSpecialize(Object operandNodeValue) {
      int state_0 = this.state_0_;
      if (operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;
         int var11;
         this.state_0_ = var11 = state_0 | 1;
         return this.doInt(operandNodeValue_);
      } else {
         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
            state_0 |= doubleCast0 << 5;
            int var10;
            this.state_0_ = var10 = state_0 | 2;
            return this.doDouble(operandNodeValue_);
         } else if (operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            int var8;
            this.state_0_ = var8 = state_0 | 4;
            return this.doBigInt(operandNodeValue_);
         } else if (JSGuards.isJavaNumber(operandNodeValue)) {
            int var7;
            this.state_0_ = var7 = state_0 | 8;
            return this.doJavaNumber(operandNodeValue);
         } else {
            int var6;
            this.state_0_ = var6 = state_0 | 16;
            return this.doOther(operandNodeValue);
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
      s = new Object[]{"doJavaNumber", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doOther", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(int state_0, Object operandNodeValue) {
      if (JSTypesGen.isImplicitDouble(operandNodeValue)) {
         return false;
      } else {
         return (state_0 & 4) == 0 && operandNodeValue instanceof BigInt ? false : (state_0 & 8) != 0 || !JSGuards.isJavaNumber(operandNodeValue);
      }
   }

   public static IsIdenticalIntegerNode create(JavaScriptNode operand, int integer, boolean leftConstant) {
      return new IsIdenticalIntegerNodeGen(operand, integer, leftConstant);
   }
}
