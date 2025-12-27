package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class CbrtNode extends MathOperation {
   public CbrtNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected double cbrt(Object a) {
      double b = this.toDouble(a);
      return Math.cbrt(b);
   }
}
