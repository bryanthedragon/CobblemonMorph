
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class Expm1Node
extends MathOperation {
    public Expm1Node(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected double expm1(Object a) {
        double b = this.toDouble(a);
        return Math.expm1(b);
    }
}

