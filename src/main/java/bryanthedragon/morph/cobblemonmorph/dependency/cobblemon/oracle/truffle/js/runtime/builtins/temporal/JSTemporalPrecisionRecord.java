
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.js.runtime.util.TemporalUtil;

public final class JSTemporalPrecisionRecord {
    private final Object precision;
    private final TemporalUtil.Unit unit;
    private final double increment;

    private JSTemporalPrecisionRecord(Object precision, TemporalUtil.Unit unit, double increment) {
        this.precision = precision;
        this.unit = unit;
        this.increment = increment;
    }

    public static JSTemporalPrecisionRecord create(Object precision, TemporalUtil.Unit unit, double increment) {
        return new JSTemporalPrecisionRecord(precision, unit, increment);
    }

    public Object getPrecision() {
        return this.precision;
    }

    public TemporalUtil.Unit getUnit() {
        return this.unit;
    }

    public double getIncrement() {
        return this.increment;
    }
}

