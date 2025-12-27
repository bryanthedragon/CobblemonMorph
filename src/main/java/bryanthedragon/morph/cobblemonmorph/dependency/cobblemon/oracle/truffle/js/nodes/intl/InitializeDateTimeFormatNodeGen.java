package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(InitializeDateTimeFormatNode.class)
public final class InitializeDateTimeFormatNodeGen extends InitializeDateTimeFormatNode implements Introspection.Provider {
   private InitializeDateTimeFormatNodeGen(JSContext context, String required, String defaults) {
      super(context, required, defaults);
   }

   @Override
   public JSDynamicObject executeInit(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value) {
      return this.initializeDateTimeFormat(arg0Value, arg1Value, arg2Value);
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      Object[] s = new Object[]{"initializeDateTimeFormat", (byte)1, null};
      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static InitializeDateTimeFormatNode create(JSContext context, String required, String defaults) {
      return new InitializeDateTimeFormatNodeGen(context, required, defaults);
   }
}
