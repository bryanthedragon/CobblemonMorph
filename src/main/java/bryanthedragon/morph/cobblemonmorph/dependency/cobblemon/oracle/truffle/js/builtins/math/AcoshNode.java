
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class AcoshNode
extends MathOperation {
    public AcoshNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected static double acoshDouble(double x) {
        return Math.log(x + Math.sqrt(x * x - 1.0));
    }

    @Specialization
    protected double acoshGeneric(Object a) {
        return AcoshNode.acoshDouble(this.toDouble(a));
    }
}

