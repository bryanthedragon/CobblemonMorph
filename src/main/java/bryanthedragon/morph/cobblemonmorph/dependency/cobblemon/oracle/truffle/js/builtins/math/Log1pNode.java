
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class Log1pNode
extends MathOperation {
    public Log1pNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected static double log1p(double a) {
        return Math.log1p(a);
    }

    @Specialization
    protected double log1p(Object a) {
        return Log1pNode.log1p(this.toDouble(a));
    }
}

