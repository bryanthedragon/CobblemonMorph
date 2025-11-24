
package com.oracle.truffle.js.runtime.builtins.temporal;

import java.math.BigInteger;

public final class JSTemporalNanosecondsDaysRecord {
    private final BigInteger days;
    private final BigInteger nanoseconds;
    private final BigInteger dayLength;

    private JSTemporalNanosecondsDaysRecord(BigInteger days, BigInteger nanoseconds, BigInteger dayLength) {
        this.days = days;
        this.nanoseconds = nanoseconds;
        this.dayLength = dayLength;
    }

    public static JSTemporalNanosecondsDaysRecord create(BigInteger days, BigInteger nanoseconds, BigInteger dayLength) {
        return new JSTemporalNanosecondsDaysRecord(days, nanoseconds, dayLength);
    }

    public BigInteger getDays() {
        return this.days;
    }

    public BigInteger getNanoseconds() {
        return this.nanoseconds;
    }

    public BigInteger getDayLength() {
        return this.dayLength;
    }
}

