
package com.oracle.truffle.js.runtime.doubleconv;

import com.oracle.truffle.js.runtime.doubleconv.BignumDtoa;
import com.oracle.truffle.js.runtime.doubleconv.DtoaBuffer;
import com.oracle.truffle.js.runtime.doubleconv.DtoaMode;
import com.oracle.truffle.js.runtime.doubleconv.FastDtoa;
import com.oracle.truffle.js.runtime.doubleconv.FixedDtoa;

public final class DoubleConversion {
    private static final int kMaxFixedDigitsBeforePoint = 60;
    private static final int kMaxFixedDigitsAfterPoint = 60;
    private static final int kMaxExponentialDigits = 120;
    private static final int kBase10MaximalLength = 17;

    private DoubleConversion() {
    }

    public static String toShortest(double value2) {
        assert (Double.isFinite(value2)) : value2;
        DtoaBuffer buffer = new DtoaBuffer(17);
        DoubleConversion.dtoaShortest(value2, buffer);
        return buffer.format(DtoaMode.SHORTEST, 0);
    }

    private static void dtoaShortest(double value2, DtoaBuffer buffer) {
        double absValue = Math.abs(value2);
        if (value2 < 0.0) {
            buffer.isNegative = true;
        }
        if (value2 == 0.0) {
            buffer.append('0');
            buffer.decimalPoint = 1;
        } else if (!DoubleConversion.fastDtoaShortest(absValue, buffer)) {
            buffer.reset();
            DoubleConversion.bignumDtoa(absValue, DtoaMode.SHORTEST, 0, buffer);
        }
    }

    public static String toFixed(double value2, int requestedDigits) {
        assert (Double.isFinite(value2)) : value2;
        DtoaBuffer buffer = new DtoaBuffer(120);
        double absValue = Math.abs(value2);
        if (value2 < 0.0) {
            buffer.isNegative = true;
        }
        if (value2 == 0.0) {
            buffer.append('0');
            buffer.decimalPoint = 1;
        } else if (!DoubleConversion.fixedDtoa(absValue, requestedDigits, buffer)) {
            buffer.reset();
            DoubleConversion.bignumDtoa(absValue, DtoaMode.FIXED, requestedDigits, buffer);
        }
        return buffer.format(DtoaMode.FIXED, requestedDigits);
    }

    public static String toPrecision(double value2, int precision) {
        assert (Double.isFinite(value2)) : value2;
        DtoaBuffer buffer = new DtoaBuffer(precision);
        DoubleConversion.dtoaPrecision(value2, precision, buffer);
        return buffer.format(DtoaMode.PRECISION, 0);
    }

    private static void dtoaPrecision(double value2, int precision, DtoaBuffer buffer) {
        double absValue = Math.abs(value2);
        if (value2 < 0.0) {
            buffer.isNegative = true;
        }
        if (value2 == 0.0) {
            for (int i = 0; i < precision; ++i) {
                buffer.append('0');
            }
            buffer.decimalPoint = 1;
        } else if (!DoubleConversion.fastDtoaCounted(absValue, precision, buffer)) {
            buffer.reset();
            DoubleConversion.bignumDtoa(absValue, DtoaMode.PRECISION, precision, buffer);
        }
    }

    public static void bignumDtoa(double v, DtoaMode mode, int digits, DtoaBuffer buffer) {
        assert (v > 0.0 && !Double.isNaN(v) && !Double.isInfinite(v)) : v;
        BignumDtoa.bignumDtoa(v, mode, digits, buffer);
    }

    public static boolean fastDtoaShortest(double v, DtoaBuffer buffer) {
        assert (v > 0.0 && !Double.isNaN(v) && !Double.isInfinite(v)) : v;
        return FastDtoa.grisu3(v, buffer);
    }

    public static boolean fastDtoaCounted(double v, int precision, DtoaBuffer buffer) {
        assert (v > 0.0 && !Double.isNaN(v) && !Double.isInfinite(v)) : v;
        return FastDtoa.grisu3Counted(v, precision, buffer);
    }

    public static boolean fixedDtoa(double v, int digits, DtoaBuffer buffer) {
        assert (v > 0.0 && !Double.isNaN(v) && !Double.isInfinite(v)) : v;
        return FixedDtoa.fastFixedDtoa(v, digits, buffer);
    }

    public static String toExponential(double value2, int requestedDigits) {
        return DoubleConversion.toExponential(value2, requestedDigits, true);
    }

    public static String toExponential(double value2, int requestedDigits, boolean uniqueZero) {
        assert (Double.isFinite(value2)) : value2;
        assert (requestedDigits >= -1 && requestedDigits <= 120) : requestedDigits;
        boolean sign = value2 < 0.0;
        double absValue = Math.abs(value2);
        int kDecimalRepCapacity = 121;
        assert (kDecimalRepCapacity > 17);
        DtoaBuffer buffer = new DtoaBuffer(kDecimalRepCapacity);
        if (requestedDigits == -1) {
            DoubleConversion.dtoaShortest(absValue, buffer);
        } else {
            DoubleConversion.dtoaPrecision(absValue, requestedDigits + 1, buffer);
            assert (buffer.getLength() <= requestedDigits + 1);
            for (int i = buffer.getLength(); i < requestedDigits + 1; ++i) {
                buffer.append('0');
            }
            assert (buffer.getLength() == requestedDigits + 1);
        }
        StringBuilder resultBuilder = new StringBuilder();
        if (sign && (value2 != 0.0 || !uniqueZero)) {
            resultBuilder.append('-');
        }
        buffer.toExponentialFormat(resultBuilder);
        return resultBuilder.toString();
    }
}

