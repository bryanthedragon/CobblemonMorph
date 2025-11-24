
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;

public abstract class TruncNode
extends MathOperation {
    public TruncNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected static double truncDouble(double value2) {
        return JSRuntime.truncateDouble(value2);
    }

    @Specialization(replaces={"truncDouble"})
    protected double trunc(Object a) {
        double d = this.toDouble(a);
        return TruncNode.truncDouble(d);
    }
}

