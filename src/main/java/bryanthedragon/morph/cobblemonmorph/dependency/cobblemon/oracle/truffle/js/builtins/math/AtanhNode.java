
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;

public abstract class AtanhNode
extends MathOperation {
    public AtanhNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected static double atanh(double x) {
        if (JSRuntime.isNegativeZero(x)) {
            return -0.0;
        }
        return Math.log((1.0 + x) / (1.0 - x)) / 2.0;
    }

    @Specialization
    protected double atanh(Object a) {
        return AtanhNode.atanh(this.toDouble(a));
    }
}

