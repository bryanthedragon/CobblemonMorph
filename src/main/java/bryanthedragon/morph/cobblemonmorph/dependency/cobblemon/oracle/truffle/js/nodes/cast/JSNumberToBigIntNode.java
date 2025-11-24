
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSNumberToBigIntNodeGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import java.math.BigInteger;

public abstract class JSNumberToBigIntNode
extends JavaScriptBaseNode {
    public abstract Object execute(Object var1);

    public final BigInt executeBigInt(Object value2) {
        return (BigInt)this.execute(value2);
    }

    public static JSNumberToBigIntNode create() {
        return JSNumberToBigIntNodeGen.create();
    }

    @Specialization
    protected BigInt doInteger(int value2) {
        return BigInt.valueOf(value2);
    }

    protected boolean doubleRepresentsSameValueAsLong(double value2) {
        return JSRuntime.doubleIsRepresentableAsLong(value2) && value2 != 9.223372036854776E18;
    }

    @Specialization(guards={"doubleRepresentsSameValueAsLong(value)"})
    protected BigInt doDoubleAsLong(double value2) {
        return BigInt.valueOf((long)value2);
    }

    @CompilerDirectives.TruffleBoundary
    @Specialization(guards={"!doubleRepresentsSameValueAsLong(value)"})
    protected BigInt doDoubleOther(double value2) {
        if (!JSRuntime.isInteger(value2)) {
            throw Errors.createRangeError("BigInt out of range");
        }
        long bits = Double.doubleToRawLongBits(value2);
        boolean negative = (bits & Long.MIN_VALUE) != 0L;
        int exponentOffset = 1023;
        int mantissaLength = 52;
        int exponent = (int)((bits & 0x7FF0000000000000L) >> mantissaLength) - exponentOffset - mantissaLength;
        long mantissa = bits & 0xFFFFFFFFFFFFFL | 0x10000000000000L;
        BigInteger bigInteger = BigInteger.valueOf(negative ? -mantissa : mantissa).shiftLeft(exponent);
        return new BigInt(bigInteger);
    }

    @Specialization(guards={"isJSNull(value)"})
    protected static BigInt doNull(Object value2) {
        return BigInt.ZERO;
    }
}

