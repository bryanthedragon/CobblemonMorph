
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;

public abstract class CeilNode
extends MathOperation {
    public CeilNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected static int ceilInt(int a) {
        return a;
    }

    @Specialization
    protected static SafeInteger ceilSafeInt(SafeInteger a) {
        return a;
    }

    @Specialization
    protected static Object ceilDouble(double d, @Cached(value="createBinaryProfile()") @Cached.Shared(value="isZero") ConditionProfile isZero, @Cached(value="createBinaryProfile()") @Cached.Shared(value="requiresNegativeZero") ConditionProfile requiresNegativeZero, @Cached(value="createBinaryProfile()") @Cached.Shared(value="fitsInt") ConditionProfile fitsInt, @Cached(value="createBinaryProfile()") @Cached.Shared(value="fitsSafeLong") ConditionProfile fitsSafeLong) {
        if (isZero.profile(d == 0.0)) {
            return d;
        }
        if (fitsInt.profile(d >= -2.147483648E9 && d <= 2.147483647E9)) {
            int i = (int)d;
            int result = d > (double)i ? i + 1 : i;
            if (requiresNegativeZero.profile(result == 0 && d < 0.0)) {
                return -0.0;
            }
            return result;
        }
        if (fitsSafeLong.profile(JSRuntime.isSafeInteger(d))) {
            long i = (long)d;
            long result = d > (double)i ? i + 1L : i;
            if (requiresNegativeZero.profile(result == 0L && d < 0.0)) {
                return -0.0;
            }
            return SafeInteger.valueOf(result);
        }
        return Math.ceil(d);
    }

    @Specialization(replaces={"ceilDouble"})
    protected Object ceilToDouble(Object a, @Cached(value="createBinaryProfile()") @Cached.Shared(value="isZero") ConditionProfile isZero, @Cached(value="createBinaryProfile()") @Cached.Shared(value="requiresNegativeZero") ConditionProfile requiresNegativeZero, @Cached(value="createBinaryProfile()") @Cached.Shared(value="fitsInt") ConditionProfile fitsInt, @Cached(value="createBinaryProfile()") @Cached.Shared(value="fitsSafeLong") ConditionProfile fitsSafeLong) {
        double d = this.toDouble(a);
        return CeilNode.ceilDouble(d, isZero, requiresNegativeZero, fitsInt, fitsSafeLong);
    }
}

