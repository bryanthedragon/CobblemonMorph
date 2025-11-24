
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class SinhNode
extends MathOperation {
    public SinhNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected double sinh(double x) {
        return Math.sinh(x);
    }

    @Specialization
    protected double sinh(Object a) {
        return this.sinh(this.toDouble(a));
    }
}

