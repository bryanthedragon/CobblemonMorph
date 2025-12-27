package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(GetStringOptionNode.class)
public final class GetStringOptionNodeGen extends GetStringOptionNode implements Introspection.Provider {
   private GetStringOptionNodeGen(JSContext context, TruffleString property, String[] values, String fallback) {
      super(context, property, values, fallback);
   }

   @Override
   public String executeValue(Object arg0Value) {
      return this.getOption(arg0Value);
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      Object[] s = new Object[]{"getOption", (byte)1, null};
      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static GetStringOptionNode create(JSContext context, TruffleString property, String[] values, String fallback) {
      return new GetStringOptionNodeGen(context, property, values, fallback);
   }
}
