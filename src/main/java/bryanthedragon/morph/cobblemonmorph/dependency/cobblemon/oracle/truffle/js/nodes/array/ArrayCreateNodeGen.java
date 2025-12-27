package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;

@GeneratedBy(ArrayCreateNode.class)
public final class ArrayCreateNodeGen extends ArrayCreateNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private ArrayCreateNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public JSArrayObject execute(long arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && JSRuntime.isValidArrayLength(arg0Value) && arg0Value <= 2147483647L) {
            return this.doDefault(arg0Value);
         }

         if ((state_0 & 2) != 0 && JSRuntime.isValidArrayLength(arg0Value) && arg0Value > 2147483647L) {
            return this.doLargeLength(arg0Value);
         }

         if ((state_0 & 4) != 0 && !JSRuntime.isValidArrayLength(arg0Value)) {
            return this.doInvalidLength(arg0Value);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   private JSArrayObject executeAndSpecialize(long arg0Value) {
      int state_0 = this.state_0_;
      if (JSRuntime.isValidArrayLength(arg0Value) && arg0Value <= 2147483647L) {
         int var6;
         this.state_0_ = var6 = state_0 | 1;
         return this.doDefault(arg0Value);
      } else if (JSRuntime.isValidArrayLength(arg0Value) && arg0Value > 2147483647L) {
         int var5;
         this.state_0_ = var5 = state_0 | 2;
         return this.doLargeLength(arg0Value);
      } else if (!JSRuntime.isValidArrayLength(arg0Value)) {
         int var4;
         this.state_0_ = var4 = state_0 | 4;
         return this.doInvalidLength(arg0Value);
      } else {
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
      Object[] data = new Object[4];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doDefault", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doLargeLength", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doInvalidLength", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static ArrayCreateNode create(JSContext context) {
      return new ArrayCreateNodeGen(context);
   }
}
