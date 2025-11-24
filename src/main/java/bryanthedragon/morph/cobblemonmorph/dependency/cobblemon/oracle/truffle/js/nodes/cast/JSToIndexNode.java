
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToIndexNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;

public abstract class JSToIndexNode
extends JavaScriptBaseNode {
    public static JSToIndexNode create() {
        return JSToIndexNodeGen.create();
    }

    public abstract long executeLong(Object var1);

    @Specialization
    protected long doInt(int value2, @Cached @Cached.Shared(value="negativeIndexBranch") BranchProfile negativeIndexBranch) {
        if (value2 < 0) {
            negativeIndexBranch.enter();
            throw Errors.createRangeErrorIndexNegative(this);
        }
        return value2;
    }

    @Specialization
    protected long doSafeInteger(SafeInteger value2, @Cached @Cached.Shared(value="negativeIndexBranch") BranchProfile negativeIndexBranch) {
        long longValue = value2.longValue();
        if (longValue < 0L) {
            negativeIndexBranch.enter();
            throw Errors.createRangeErrorIndexNegative(this);
        }
        return longValue;
    }

    @Specialization
    protected long doDouble(double value2, @Cached @Cached.Shared(value="negativeIndexBranch") BranchProfile negativeIndexBranch, @Cached BranchProfile tooLargeIndexBranch) {
        long integerIndex = (long)value2;
        if (integerIndex < 0L) {
            negativeIndexBranch.enter();
            throw Errors.createRangeErrorIndexNegative(this);
        }
        if (integerIndex > JSRuntime.MAX_SAFE_INTEGER_LONG) {
            tooLargeIndexBranch.enter();
            throw Errors.createRangeErrorIndexTooLarge(this);
        }
        return integerIndex;
    }

    @Specialization(guards={"isUndefined(value)"})
    protected static long doUndefined(Object value2) {
        return 0L;
    }

    @Specialization
    protected static long doObject(Object value2, @Cached(value="create()") JSToNumberNode toNumberNode, @Cached(value="create()") JSToIndexNode recursiveToIndexNode) {
        Number number = (Number)toNumberNode.execute(value2);
        assert (number instanceof Integer || number instanceof Double);
        return recursiveToIndexNode.executeLong(number);
    }
}

