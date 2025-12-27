package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class AsinNode extends MathOperation {
   public AsinNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @CompilerDirectives.TruffleBoundary
   @Specialization
   protected static double asinDouble(double a) {
      return Math.asin(a);
   }

   @Specialization
   protected double asinGeneric(Object a) {
      return asinDouble(this.toDouble(a));
   }
}
