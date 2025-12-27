package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(TemporalDurationAddNode.class)
public final class TemporalDurationAddNodeGen extends TemporalDurationAddNode implements Introspection.Provider {
   private TemporalDurationAddNodeGen(JSContext ctx) {
      super(ctx);
   }

   @Override
   public JSTemporalDurationRecord execute(
      double arg0Value,
      double arg1Value,
      double arg2Value,
      double arg3Value,
      double arg4Value,
      double arg5Value,
      double arg6Value,
      double arg7Value,
      double arg8Value,
      double arg9Value,
      double arg10Value,
      double arg11Value,
      double arg12Value,
      double arg13Value,
      double arg14Value,
      double arg15Value,
      double arg16Value,
      double arg17Value,
      double arg18Value,
      double arg19Value,
      JSDynamicObject arg20Value
   ) {
      return this.add(
         arg0Value,
         arg1Value,
         arg2Value,
         arg3Value,
         arg4Value,
         arg5Value,
         arg6Value,
         arg7Value,
         arg8Value,
         arg9Value,
         arg10Value,
         arg11Value,
         arg12Value,
         arg13Value,
         arg14Value,
         arg15Value,
         arg16Value,
         arg17Value,
         arg18Value,
         arg19Value,
         arg20Value
      );
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      Object[] s = new Object[]{"add", (byte)1, null};
      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static TemporalDurationAddNode create(JSContext ctx) {
      return new TemporalDurationAddNodeGen(ctx);
   }
}
