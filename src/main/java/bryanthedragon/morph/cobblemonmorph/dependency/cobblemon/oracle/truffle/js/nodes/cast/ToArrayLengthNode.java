
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.ToArrayLengthNodeGen;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;

@ImportStatic(value={JSRuntime.class})
public abstract class ToArrayLengthNode
extends JavaScriptBaseNode {
    private static final long RANGE_ERROR = -1L;
    private static final long TYPE_NOT_NUMBER = Long.MIN_VALUE;

    ToArrayLengthNode() {
    }

    public static ToArrayLengthNode create() {
        return ToArrayLengthNodeGen.create();
    }

    public abstract long executeLong(Object var1);

    public boolean isTypeNumber(long result) {
        return result != Long.MIN_VALUE;
    }

    @Specialization
    protected static long doInt(int value2) {
        return value2;
    }

    @Specialization(guards={"isValidArrayLength(value.longValue())"})
    protected static long doSafeInteger(SafeInteger value2) {
        return JSRuntime.toUInt32(value2.longValue());
    }

    @Specialization(guards={"!isValidArrayLength(value.longValue())"})
    protected static long rangeError(SafeInteger value2) {
        return -1L;
    }

    @Specialization(guards={"isValidArrayLength(value)"})
    protected static long doLong(long value2) {
        return JSRuntime.toUInt32(value2);
    }

    @Specialization(guards={"!isValidArrayLength(value)"})
    protected static long rangeError(long value2) {
        return -1L;
    }

    @Specialization(guards={"isValidArrayLength(value)"})
    protected static long doDouble(double value2) {
        return JSRuntime.toUInt32((long)value2);
    }

    @Specialization(guards={"!isValidArrayLength(value)"})
    protected static long rangeError(double value2) {
        return -1L;
    }

    @Specialization(guards={"!isNumber(value)"})
    protected static long typeNotNumber(Object value2) {
        return Long.MIN_VALUE;
    }
}

