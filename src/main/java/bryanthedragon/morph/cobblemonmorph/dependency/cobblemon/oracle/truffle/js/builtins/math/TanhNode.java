package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class TanhNode extends MathOperation {
   public TanhNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected double tanh(double x) {
      return Math.tanh(x);
   }

   @Specialization
   protected double tanh(Object a) {
      return this.tanh(this.toDouble(a));
   }
}
