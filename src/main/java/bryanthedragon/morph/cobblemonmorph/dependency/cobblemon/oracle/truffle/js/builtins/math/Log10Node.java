package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class Log10Node extends MathOperation {
   public Log10Node(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected static double log10(double a) {
      return Math.log10(a);
   }

   @Specialization
   protected double log10(Object a) {
      return log10(this.toDouble(a));
   }
}
