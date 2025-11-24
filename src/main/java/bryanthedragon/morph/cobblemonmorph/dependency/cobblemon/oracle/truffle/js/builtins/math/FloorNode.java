
package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.math.MathOperation;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;

public abstract class FloorNode
extends MathOperation {
    public FloorNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected static int floorInt(int a) {
        return a;
    }

    @Specialization
    protected static SafeInteger floorSafeInt(SafeInteger a) {
        return a;
    }

    @Specialization
    protected static Object floorDouble(double d, @Cached(value="createBinaryProfile()") @Cached.Shared(value="isZero") ConditionProfile isZero, @Cached(value="createBinaryProfile()") @Cached.Shared(value="fitsInt") ConditionProfile fitsInt, @Cached(value="createBinaryProfile()") @Cached.Shared(value="fitsSafeLong") ConditionProfile fitsSafeLong, @Cached(value="createBinaryProfile()") @Cached.Shared(value="smaller") ConditionProfile smaller) {
        if (isZero.profile(d == 0.0)) {
            return d;
        }
        if (fitsInt.profile(d >= -2.147483648E9 && d <= 2.147483647E9)) {
            int i = (int)d;
            return smaller.profile(d < (double)i) ? i - 1 : i;
        }
        if (fitsSafeLong.profile(JSRuntime.isSafeInteger(d))) {
            long i = (long)d;
            long result = smaller.profile(d < (double)i) ? i - 1L : i;
            return SafeInteger.valueOf(result);
        }
        return Math.floor(d);
    }

    @Specialization(replaces={"floorDouble"})
    protected Object floorToDouble(Object a, @Cached(value="createBinaryProfile()") @Cached.Shared(value="isZero") ConditionProfile isZero, @Cached(value="createBinaryProfile()") @Cached.Shared(value="fitsInt") ConditionProfile fitsInt, @Cached(value="createBinaryProfile()") @Cached.Shared(value="fitsSafeLong") ConditionProfile fitsSafeLong, @Cached(value="createBinaryProfile()") @Cached.Shared(value="smaller") ConditionProfile smaller) {
        double d = this.toDouble(a);
        return FloorNode.floorDouble(d, isZero, fitsInt, fitsSafeLong, smaller);
    }
}

