
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.builtins.math.RoundNodeGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;

public abstract class RoundNode
extends MathOperation {
    private final ConditionProfile shiftProfile = ConditionProfile.createBinaryProfile();
    private final BranchProfile negativeLongBitsProfile = BranchProfile.create();
    private static final int EXP_BIAS = 1023;
    private static final int SIGNIFICAND_WIDTH = 53;
    private static final long EXP_BIT_MASK = 0x7FF0000000000000L;
    private static final long SIGNIF_BIT_MASK = 0xFFFFFFFFFFFFFL;

    RoundNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    public static RoundNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
        return RoundNodeGen.create(context, builtin, RoundNode.createCast(arguments));
    }

    protected static JavaScriptNode[] createCast(JavaScriptNode[] argumentNodes) {
        argumentNodes[0] = JSToNumberNode.create(argumentNodes[0]);
        return argumentNodes;
    }

    protected static boolean isCornercase(double d) {
        return Double.isNaN(d) || JSRuntime.isNegativeZero(d);
    }

    @Specialization
    protected static int roundInt(int a) {
        return a;
    }

    @Specialization(guards={"isCornercase(value)"})
    protected static double roundCornercase(double value2) {
        return value2;
    }

    private long round(double a) {
        long longBits = Double.doubleToRawLongBits(a);
        long biasedExp = (longBits & 0x7FF0000000000000L) >> 52;
        long shift2 = 1074L - biasedExp;
        if (this.shiftProfile.profile((shift2 & 0xFFFFFFFFFFFFFFC0L) == 0L)) {
            long r = longBits & 0xFFFFFFFFFFFFFL | 0x10000000000000L;
            if (longBits < 0L) {
                this.negativeLongBitsProfile.enter();
                r = -r;
            }
            return (r >> (int)shift2) + 1L >> 1;
        }
        return (long)a;
    }

    @Specialization(guards={"!isCornercase(value)", "isDoubleInInt32Range(value)"}, rewriteOn={ArithmeticException.class})
    protected int roundDoubleInt(double value2) {
        long longValue = this.round(value2);
        if (longValue == 0L && value2 < 0.0) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new ArithmeticException();
        }
        assert (JSRuntime.longIsRepresentableAsInt(longValue));
        return (int)longValue;
    }

    @Specialization(guards={"!isCornercase(value)"}, replaces={"roundDoubleInt"})
    protected double roundDouble(double value2, @Cached(value="createBinaryProfile()") ConditionProfile profileA, @Cached(value="createBinaryProfile()") ConditionProfile profileB) {
        long longValue = this.round(value2);
        if (profileA.profile(longValue == Long.MIN_VALUE || longValue == Long.MAX_VALUE)) {
            return value2;
        }
        if (profileB.profile(longValue == 0L && value2 < 0.0)) {
            return -0.0;
        }
        return longValue;
    }
}

