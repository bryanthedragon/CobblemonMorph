package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;

public abstract class TruncNode extends MathOperation {
   public TruncNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected static double truncDouble(double value) {
      return JSRuntime.truncateDouble(value);
   }

   @Specialization(replaces = "truncDouble")
   protected double trunc(Object a) {
      double d = this.toDouble(a);
      return truncDouble(d);
   }
}
