package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormat;

@GeneratedBy(SetNumberFormatDigitOptionsNode.class)
public final class SetNumberFormatDigitOptionsNodeGen extends SetNumberFormatDigitOptionsNode implements Introspection.Provider {
   private SetNumberFormatDigitOptionsNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public Object execute(JSNumberFormat.BasicInternalState arg0Value, Object arg1Value, int arg2Value, int arg3Value, boolean arg4Value) {
      return this.setNumberFormatDigitOptions(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      Object[] s = new Object[]{"setNumberFormatDigitOptions", (byte)1, null};
      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static SetNumberFormatDigitOptionsNode create(JSContext context) {
      return new SetNumberFormatDigitOptionsNodeGen(context);
   }
}
