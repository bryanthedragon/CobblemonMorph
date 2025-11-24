
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;

public abstract class AsinhNode
extends MathOperation {
    private final ConditionProfile isNegative = ConditionProfile.createBinaryProfile();

    public AsinhNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected double asinhDouble(double x) {
        if (JSRuntime.isNegativeZero(x)) {
            return -0.0;
        }
        if (x < 0.0 && Double.isInfinite(x)) {
            return x;
        }
        if (this.isNegative.profile(x < 0.0)) {
            return -AsinhNode.asinhImpl(-x);
        }
        return AsinhNode.asinhImpl(x);
    }

    private static double asinhImpl(double x) {
        return Math.log(x + Math.sqrt(x * x + 1.0));
    }

    @Specialization
    protected double asinhGeneric(Object a) {
        return this.asinhDouble(this.toDouble(a));
    }
}

