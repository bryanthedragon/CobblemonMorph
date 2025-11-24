
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class HypotNode
extends MathOperation {
    public HypotNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected double hypot(Object ... args) {
        int length = args.length;
        double[] values = new double[length];
        boolean isInfinite = false;
        double max2 = 0.0;
        for (int i = 0; i < length; ++i) {
            double value2 = this.toDouble(args[i]);
            boolean bl = isInfinite = isInfinite || Double.isInfinite(value2);
            if (value2 > max2) {
                max2 = value2;
            }
            values[i] = value2;
        }
        if (isInfinite) {
            return Double.POSITIVE_INFINITY;
        }
        if (max2 == 0.0) {
            max2 = 1.0;
        }
        double sum = 0.0;
        double compensation = 0.0;
        for (double value3 : values) {
            double normalizedValue = value3 / max2;
            double square = normalizedValue * normalizedValue;
            double compensatedValue = square - compensation;
            double nextSum = sum + compensatedValue;
            compensation = nextSum - sum - compensatedValue;
            sum = nextSum;
        }
        return Math.sqrt(sum) * max2;
    }
}

