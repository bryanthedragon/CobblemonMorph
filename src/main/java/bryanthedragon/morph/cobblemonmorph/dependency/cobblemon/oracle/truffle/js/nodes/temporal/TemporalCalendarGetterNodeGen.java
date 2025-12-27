package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(TemporalCalendarGetterNode.class)
public final class TemporalCalendarGetterNodeGen extends TemporalCalendarGetterNode implements Introspection.Provider {
   private TemporalCalendarGetterNodeGen(JSContext ctx) {
      super(ctx);
   }

   @Override
   public Object execute(JSDynamicObject arg0Value, JSDynamicObject arg1Value, TruffleString arg2Value) {
      return this.calendarGetter(arg0Value, arg1Value, arg2Value);
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      Object[] s = new Object[]{"calendarGetter", (byte)1, null};
      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static TemporalCalendarGetterNode create(JSContext ctx) {
      return new TemporalCalendarGetterNodeGen(ctx);
   }
}
