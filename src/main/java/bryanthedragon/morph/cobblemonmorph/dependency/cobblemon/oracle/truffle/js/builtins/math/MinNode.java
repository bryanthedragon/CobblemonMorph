
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;

public abstract class MinNode
extends MathOperation {
    private final ConditionProfile leftSmaller = ConditionProfile.createBinaryProfile();
    private final ConditionProfile rightSmaller = ConditionProfile.createBinaryProfile();
    private final ConditionProfile bothEqual = ConditionProfile.createBinaryProfile();
    private final ConditionProfile negativeZero = ConditionProfile.createBinaryProfile();

    public MinNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    private double minDoubleDouble(double a, double b) {
        if (this.leftSmaller.profile(a < b)) {
            return a;
        }
        if (this.rightSmaller.profile(b < a)) {
            return b;
        }
        if (this.bothEqual.profile(a == b)) {
            if (this.negativeZero.profile(JSRuntime.isNegativeZero(b))) {
                return b;
            }
            return a;
        }
        return Double.NaN;
    }

    protected static boolean caseIntInt(Object[] args) {
        assert (args.length == 2);
        return args[0] instanceof Integer && args[1] instanceof Integer;
    }

    @Specialization(guards={"args.length == 0"})
    protected static double min0Param(Object[] args) {
        return Double.POSITIVE_INFINITY;
    }

    @Specialization(guards={"args.length == 1"})
    protected double min1Param(Object[] args) {
        return this.toDouble(args[0]);
    }

    @Specialization(guards={"args.length == 2", "caseIntInt(args)"})
    protected static int min2ParamInt(Object[] args, @Cached.Shared(value="minProfile") @Cached(value="createBinaryProfile()") ConditionProfile minProfile) {
        int i1 = (Integer)args[0];
        int i2 = (Integer)args[1];
        return MinNode.min(i1, i2, minProfile);
    }

    @Specialization(guards={"args.length == 2", "!caseIntInt(args)"})
    protected Object min2Param(Object[] args, @Cached(value="createBinaryProfile()") ConditionProfile isIntBranch, @Cached.Shared(value="minProfile") @Cached(value="createBinaryProfile()") ConditionProfile minProfile, @Cached(value="create()") JSToNumberNode toNumber1Node, @Cached(value="create()") JSToNumberNode toNumber2Node) {
        Number n1 = toNumber1Node.executeNumber(args[0]);
        Number n2 = toNumber2Node.executeNumber(args[1]);
        if (isIntBranch.profile(n1 instanceof Integer && n2 instanceof Integer)) {
            return MinNode.min((Integer)n1, (Integer)n2, minProfile);
        }
        double d1 = JSRuntime.doubleValue(n1);
        double d2 = JSRuntime.doubleValue(n2);
        return this.minDoubleDouble(d1, d2);
    }

    protected static boolean caseIntIntInt(Object[] args) {
        assert (args.length == 3);
        return args[0] instanceof Integer && args[1] instanceof Integer && args[2] instanceof Integer;
    }

    @Specialization(guards={"args.length == 3", "caseIntIntInt(args)"})
    protected int min3ParamInt(Object[] args) {
        return Math.min(Math.min((Integer)args[0], (Integer)args[1]), (Integer)args[2]);
    }

    @Specialization(guards={"args.length == 3", "!caseIntIntInt(args)"})
    protected double min3ParamOther(Object[] args) {
        double smallest = this.minDoubleDouble(this.toDouble(args[0]), this.toDouble(args[1]));
        return this.minDoubleDouble(smallest, this.toDouble(args[2]));
    }

    @Specialization(guards={"args.length >= 4"})
    protected double minGeneric(Object[] args) {
        double smallest = this.minDoubleDouble(this.toDouble(args[0]), this.toDouble(args[1]));
        for (int i = 2; i < args.length; ++i) {
            smallest = this.minDoubleDouble(smallest, this.toDouble(args[i]));
        }
        return smallest;
    }

    private static int min(int a, int b, ConditionProfile minProfile) {
        return minProfile.profile(a <= b) ? a : b;
    }
}

