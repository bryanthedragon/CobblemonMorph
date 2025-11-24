
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.SlowPathException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.builtins.math.PowNodeGen;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SuppressFBWarnings;

public abstract class PowNode
extends MathOperation {
    @CompilerDirectives.CompilationFinal
    private boolean hasSeenOne = false;
    @CompilerDirectives.CompilationFinal
    private boolean hasSeenTwo = false;
    @CompilerDirectives.CompilationFinal
    private boolean hasSeenThree = false;
    @CompilerDirectives.CompilationFinal
    private boolean hasSeenZeroPointFive = false;
    @CompilerDirectives.CompilationFinal
    private boolean hasSeenOnePointFive = false;
    @CompilerDirectives.CompilationFinal
    private boolean hasSeenTwoPointFive = false;

    public PowNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    public abstract double execute(Object var1, Object var2);

    protected PowNode create(JSContext context) {
        return PowNodeGen.create(context, null, null);
    }

    @Specialization(rewriteOn={SlowPathException.class})
    protected double pow(double a, double b) throws SlowPathException {
        if (this.hasSeenOne && b == 1.0) {
            return a;
        }
        if (this.hasSeenTwo && b == 2.0) {
            return a * a;
        }
        if (this.hasSeenThree && b == 3.0) {
            return a * a * a;
        }
        if ((this.hasSeenZeroPointFive || this.hasSeenOnePointFive || this.hasSeenTwoPointFive) && (a < 0.0 || a == -0.0)) {
            return PowNode.powIntl(a, b);
        }
        if (this.hasSeenZeroPointFive && b == 0.5) {
            return Math.sqrt(a);
        }
        if (this.hasSeenOnePointFive && b == 1.5) {
            return a * Math.sqrt(a);
        }
        if (this.hasSeenTwoPointFive && b == 2.5) {
            return a * a * Math.sqrt(a);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        if (b == 1.0) {
            this.hasSeenOne = true;
        } else if (b == 2.0) {
            this.hasSeenTwo = true;
        } else if (b == 3.0) {
            this.hasSeenThree = true;
        } else if (b == 0.5) {
            this.hasSeenZeroPointFive = true;
        } else if (b == 1.5) {
            this.hasSeenOnePointFive = true;
        } else if (b == 2.5) {
            this.hasSeenTwoPointFive = true;
        } else {
            throw JSNodeUtil.slowPathException();
        }
        return this.pow(a, b);
    }

    private static double positivePow(double operand, int castExponent) {
        int exponent = castExponent;
        double result = 1.0;
        double base = operand;
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result *= base;
            }
            exponent >>= 1;
            base *= base;
        }
        return result;
    }

    @Specialization(rewriteOn={SlowPathException.class})
    protected double pow2(double a, double b) throws SlowPathException {
        if (JSRuntime.doubleIsRepresentableAsInt(b, true) && b > 0.0) {
            return PowNode.positivePow(a, (int)b);
        }
        throw JSNodeUtil.slowPathException();
    }

    @Specialization
    @SuppressFBWarnings(value={"FE_FLOATING_POINT_EQUALITY"}, justification="not necessary in this case")
    protected double pow3(double a, double b, @Cached(value="createBinaryProfile()") ConditionProfile branch1, @Cached(value="createBinaryProfile()") ConditionProfile branch2) {
        int ib = (int)b;
        if (branch1.profile(JSRuntime.doubleIsRepresentableAsInt(b, true) && b > 0.0)) {
            return PowNode.positivePow(a, ib);
        }
        if (branch2.profile((double)ib + 0.5 == b && b > 0.0 && a > 0.0 && a != -0.0)) {
            return PowNode.positivePow(a, ib) * Math.sqrt(a);
        }
        return PowNode.powIntl(a, b);
    }

    @Specialization
    protected Object pow(Object a, Object b, @Cached(value="create(getContext())") PowNode powNode) {
        return JSRuntime.doubleToNarrowestNumber(powNode.execute(this.toDouble(a), this.toDouble(b)));
    }

    @CompilerDirectives.TruffleBoundary
    private static double powIntl(double a, double b) {
        return Math.pow(a, b);
    }
}

