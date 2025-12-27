package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalRelativeDateRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(TemporalMoveRelativeDateNode.class)
public final class TemporalMoveRelativeDateNodeGen extends TemporalMoveRelativeDateNode implements Introspection.Provider {
   private TemporalMoveRelativeDateNodeGen(JSContext ctx) {
      super(ctx);
   }

   @Override
   public JSTemporalRelativeDateRecord execute(JSDynamicObject arg0Value, JSDynamicObject arg1Value, JSDynamicObject arg2Value) {
      return this.moveRelativeDate(arg0Value, arg1Value, arg2Value);
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      Object[] s = new Object[]{"moveRelativeDate", (byte)1, null};
      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static TemporalMoveRelativeDateNode create(JSContext ctx) {
      return new TemporalMoveRelativeDateNodeGen(ctx);
   }
}
