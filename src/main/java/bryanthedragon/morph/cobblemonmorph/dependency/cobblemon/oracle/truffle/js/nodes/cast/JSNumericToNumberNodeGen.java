package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.BigInt;

@GeneratedBy(JSNumericToNumberNode.class)
public final class JSNumericToNumberNodeGen extends JSNumericToNumberNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private JSNumericToNumberNodeGen() {
   }

   @Override
   public Number executeNumeric(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof BigInt) {
         BigInt arg0Value_ = (BigInt)arg0Value;
         return JSNumericToNumberNode.doBigInt(arg0Value_);
      } else {
         if ((state_0 & 2) != 0 && arg0Value instanceof Number) {
            Number arg0Value_ = (Number)arg0Value;
            if (!JSGuards.isBigInt(arg0Value_)) {
               return JSNumericToNumberNode.doOther(arg0Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private Number executeAndSpecialize(Object arg0Value) {
      int state_0 = this.state_0_;
      if (arg0Value instanceof BigInt) {
         BigInt arg0Value_ = (BigInt)arg0Value;
         int var5;
         this.state_0_ = var5 = state_0 | 1;
         return JSNumericToNumberNode.doBigInt(arg0Value_);
      } else {
         if (arg0Value instanceof Number) {
            Number arg0Value_ = (Number)arg0Value;
            if (!JSGuards.isBigInt(arg0Value_)) {
               int var4;
               this.state_0_ = var4 = state_0 | 2;
               return JSNumericToNumberNode.doOther(arg0Value_);
            }
         }

         throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
      }
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null, null};
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doOther", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static JSNumericToNumberNode create() {
      return new JSNumericToNumberNodeGen();
   }
}
