
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class LogNode
extends MathOperation {
    public LogNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected static double log(double a) {
        return Math.log(a);
    }

    @Specialization
    protected double log(Object a) {
        return LogNode.log(this.toDouble(a));
    }
}

