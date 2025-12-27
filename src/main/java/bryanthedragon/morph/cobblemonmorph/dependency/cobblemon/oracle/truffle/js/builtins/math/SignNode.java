package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class SignNode extends MathOperation {
   public SignNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected static int sign(int a) {
      return a > 0 ? 1 : (a < 0 ? -1 : 0);
   }

   @Specialization
   protected static double sign(double a) {
      return a > 0.0 ? 1.0 : (a < 0.0 ? -1.0 : a);
   }

   @Specialization
   protected double sign(Object a) {
      return sign(this.toDouble(a));
   }
}
