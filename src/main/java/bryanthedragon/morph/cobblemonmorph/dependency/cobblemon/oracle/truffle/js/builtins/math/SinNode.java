package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class SinNode extends MathOperation {
   public SinNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected static double sin(double a) {
      return Math.sin(a);
   }

   @Specialization
   protected double sin(Object a) {
      return sin(this.toDouble(a));
   }
}
