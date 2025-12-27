package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class TanNode extends MathOperation {
   public TanNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected static double tan(double a) {
      return Math.tan(a);
   }

   @Specialization
   protected double tan(Object a) {
      return tan(this.toDouble(a));
   }
}
