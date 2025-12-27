package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class AtanNode extends MathOperation {
   public AtanNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @CompilerDirectives.TruffleBoundary
   @Specialization
   protected static double atan(double a) {
      return Math.atan(a);
   }

   @Specialization
   protected double atan(Object a) {
      return atan(this.toDouble(a));
   }
}
