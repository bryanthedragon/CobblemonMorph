package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class HypotNode extends MathOperation {
   public HypotNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected double hypot(Object... args) {
      int length = args.length;
      double[] values = new double[length];
      boolean isInfinite = false;
      double max = 0.0;

      for (int i = 0; i < length; i++) {
         double value = this.toDouble(args[i]);
         isInfinite = isInfinite || Double.isInfinite(value);
         if (value > max) {
            max = value;
         }

         values[i] = value;
      }

      if (isInfinite) {
         return Double.POSITIVE_INFINITY;
      } else {
         if (max == 0.0) {
            max = 1.0;
         }

         double sum = 0.0;
         double compensation = 0.0;

         for (double value : values) {
            double normalizedValue = value / max;
            double square = normalizedValue * normalizedValue;
            double compensatedValue = square - compensation;
            double nextSum = sum + compensatedValue;
            compensation = nextSum - sum - compensatedValue;
            sum = nextSum;
         }

         return Math.sqrt(sum) * max;
      }
   }
}
