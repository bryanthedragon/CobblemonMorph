package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;

@GeneratedBy(JSToNumericNode.class)
public final class JSToNumericNodeGen extends JSToNumericNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private JSToNumericNodeGen(boolean toNumericOperand) {
      super(toNumericOperand);
   }

   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return JSToNumericNode.doInt(arg0Value_);
      } else if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 1920) >>> 7, arg0Value)) {
         double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 1920) >>> 7, arg0Value);
         return JSToNumericNode.doDouble(arg0Value_);
      } else if ((state_0 & 4) != 0 && arg0Value instanceof BigInt) {
         BigInt arg0Value_ = (BigInt)arg0Value;
         return this.doBigInt(arg0Value_);
      } else if ((state_0 & 8) != 0 && JSGuards.isJSBigInt(arg0Value)) {
         return this.doJSBigInt(arg0Value);
      } else if ((state_0 & 16) != 0 && arg0Value instanceof JSOverloadedOperatorsObject) {
         JSOverloadedOperatorsObject arg0Value_ = (JSOverloadedOperatorsObject)arg0Value;

         assert this.isToNumericOperand();

         return this.doOverloaded(arg0Value_);
      } else {
         if ((state_0 & 96) != 0) {
            if ((state_0 & 32) != 0) {
               assert this.isToNumericOperand();

               if (!JSGuards.isJSBigInt(arg0Value) && !this.hasOverloadedOperators(arg0Value)) {
                  return this.doToNumericOperandOther(arg0Value);
               }
            }

            if ((state_0 & 64) != 0) {
               assert !this.isToNumericOperand();

               if (!JSGuards.isJSBigInt(arg0Value)) {
                  return this.doToNumericOther(arg0Value);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private Object executeAndSpecialize(Object arg0Value) {
      int state_0 = this.state_0_;
      if (arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         int var13;
         this.state_0_ = var13 = state_0 | 1;
         return JSToNumericNode.doInt(arg0Value_);
      } else {
         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
            double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
            state_0 |= doubleCast0 << 7;
            int var12;
            this.state_0_ = var12 = state_0 | 2;
            return JSToNumericNode.doDouble(arg0Value_);
         } else if (arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            int var10;
            this.state_0_ = var10 = state_0 | 4;
            return this.doBigInt(arg0Value_);
         } else if (JSGuards.isJSBigInt(arg0Value)) {
            int var9;
            this.state_0_ = var9 = state_0 | 8;
            return this.doJSBigInt(arg0Value);
         } else {
            if (arg0Value instanceof JSOverloadedOperatorsObject) {
               JSOverloadedOperatorsObject arg0Value_ = (JSOverloadedOperatorsObject)arg0Value;
               if (this.isToNumericOperand()) {
                  int var8;
                  this.state_0_ = var8 = state_0 | 16;
                  return this.doOverloaded(arg0Value_);
               }
            }

            if (this.isToNumericOperand() && !JSGuards.isJSBigInt(arg0Value) && !this.hasOverloadedOperators(arg0Value)) {
               int var7;
               this.state_0_ = var7 = state_0 | 32;
               return this.doToNumericOperandOther(arg0Value);
            } else if (!this.isToNumericOperand() && !JSGuards.isJSBigInt(arg0Value)) {
               int var6;
               this.state_0_ = var6 = state_0 | 64;
               return this.doToNumericOther(arg0Value);
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }
      }
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if ((state_0 & 127) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 127 & (state_0 & 127) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[8];
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
      s = new Object[]{"doJSBigInt", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doOverloaded", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doToNumericOperandOther", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doToNumericOther", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToNumericNode create(boolean toNumericOperand) {
      return new JSToNumericNodeGen(toNumericOperand);
   }

   @GeneratedBy(JSToNumericNode.JSToNumericWrapperNode.class)
   public static final class JSToNumericWrapperNodeGen extends JSToNumericNode.JSToNumericWrapperNode implements Introspection.Provider {
      private JSToNumericWrapperNodeGen(JavaScriptNode operand, boolean toNumericOperand) {
         super(operand, toNumericOperand);
      }

      @Override
      public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
         return this.doDefault(operandNodeValue);
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object operandNodeValue_ = super.operandNode.execute(frameValue);
         return this.doDefault(operandNodeValue_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doDefault", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static JSToNumericNode.JSToNumericWrapperNode create(JavaScriptNode operand, boolean toNumericOperand) {
         return new JSToNumericNodeGen.JSToNumericWrapperNodeGen(operand, toNumericOperand);
      }
   }
}
