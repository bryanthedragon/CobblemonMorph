package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class ExpNode extends MathOperation {
   public ExpNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected static double exp(double a) {
      return Math.exp(a);
   }

   @Specialization
   protected double exp(Object a) {
      return exp(this.toDouble(a));
   }
}
