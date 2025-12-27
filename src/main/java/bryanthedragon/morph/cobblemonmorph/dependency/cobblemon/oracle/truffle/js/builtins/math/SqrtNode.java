package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class SqrtNode extends MathOperation {
   public SqrtNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected static double sqrtDouble(double a) {
      return Math.sqrt(a);
   }

   @Specialization
   protected double sqrtGeneric(Object a) {
      return Math.sqrt(this.toDouble(a));
   }
}
