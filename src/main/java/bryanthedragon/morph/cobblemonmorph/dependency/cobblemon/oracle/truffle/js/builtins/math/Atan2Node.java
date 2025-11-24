
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

public abstract class Atan2Node
extends MathOperation {
    public Atan2Node(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @CompilerDirectives.TruffleBoundary
    @Specialization
    protected static double atan2Double(double a, double b) {
        return Math.atan2(a, b);
    }

    @Specialization
    protected double atan2Generic(Object a, Object b) {
        return Atan2Node.atan2Double(this.toDouble(a), this.toDouble(b));
    }
}

