package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class CoshNode extends MathOperation {
   public CoshNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected static double cosh(double x) {
      return Math.cosh(x);
   }

   @Specialization
   protected double cosh(Object a) {
      return cosh(this.toDouble(a));
   }
}
