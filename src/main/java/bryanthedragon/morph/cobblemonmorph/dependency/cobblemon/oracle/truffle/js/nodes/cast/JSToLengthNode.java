
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToLengthNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;

public abstract class JSToLengthNode
extends JavaScriptBaseNode {
    public static JSToLengthNode create() {
        return JSToLengthNodeGen.create();
    }

    public abstract long executeLong(Object var1);

    @Specialization
    protected static long doInt(int value2, @Cached @Cached.Shared(value="negativeBranch") BranchProfile negativeBranch) {
        if (value2 < 0) {
            negativeBranch.enter();
            return 0L;
        }
        return value2;
    }

    @Specialization
    protected static long doSafeInteger(SafeInteger value2, @Cached @Cached.Shared(value="negativeBranch") BranchProfile negativeBranch) {
        long longValue = value2.longValue();
        if (longValue < 0L) {
            negativeBranch.enter();
            return 0L;
        }
        return longValue;
    }

    @Specialization
    protected static long doDouble(double value2, @Cached @Cached.Shared(value="negativeBranch") BranchProfile negativeBranch, @Cached @Cached.Shared(value="tooLargeBranch") BranchProfile tooLargeBranch) {
        return JSToLengthNode.doLong((long)value2, negativeBranch, tooLargeBranch);
    }

    @Specialization(guards={"isUndefined(value)"})
    protected static long doUndefined(Object value2) {
        return 0L;
    }

    @Specialization
    protected static long doObject(Object value2, @Cached(value="create()") JSToNumberNode toNumberNode, @Cached @Cached.Shared(value="negativeBranch") BranchProfile negativeBranch, @Cached @Cached.Shared(value="tooLargeBranch") BranchProfile tooLargeBranch) {
        Number result = (Number)toNumberNode.execute(value2);
        return JSToLengthNode.doLong(JSRuntime.toInteger(result), negativeBranch, tooLargeBranch);
    }

    private static long doLong(long value2, BranchProfile negativeBranch, BranchProfile tooLargeBranch) {
        if (value2 < 0L) {
            negativeBranch.enter();
            return 0L;
        }
        if (value2 > JSRuntime.MAX_SAFE_INTEGER_LONG) {
            tooLargeBranch.enter();
            return JSRuntime.MAX_SAFE_INTEGER_LONG;
        }
        return value2;
    }
}

