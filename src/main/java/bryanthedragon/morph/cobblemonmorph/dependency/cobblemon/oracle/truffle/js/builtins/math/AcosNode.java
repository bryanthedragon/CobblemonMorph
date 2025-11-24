
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class AcosNode
extends MathOperation {
    public AcosNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @CompilerDirectives.TruffleBoundary
    @Specialization
    protected static double acosDouble(double a) {
        return Math.acos(a);
    }

    @Specialization
    protected double acosGeneric(Object a) {
        return AcosNode.acosDouble(this.toDouble(a));
    }
}

