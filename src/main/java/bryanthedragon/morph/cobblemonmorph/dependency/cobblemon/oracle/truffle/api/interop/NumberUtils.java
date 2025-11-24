
package com.oracle.truffle.api.interop;

final class NumberUtils {
    private static final double DOUBLE_MAX_SAFE_INTEGER = 9.007199254740991E15;
    static final long LONG_MAX_SAFE_DOUBLE = 0x1FFFFFFFFFFFFFL;
    private static final float FLOAT_MAX_SAFE_INTEGER = 1.6777215E7f;
    static final int INT_MAX_SAFE_FLOAT = 0xFFFFFF;

    private NumberUtils() {
    }

    static boolean inSafeIntegerRange(double d) {
        return d >= -9.007199254740991E15 && d <= 9.007199254740991E15;
    }

    static boolean inSafeDoubleRange(long l) {
        return l >= -9007199254740991L && l <= 0x1FFFFFFFFFFFFFL;
    }

    static boolean inSafeFloatRange(int i) {
        return i >= -16777215 && i <= 0xFFFFFF;
    }

    static boolean inSafeIntegerRange(float f) {
        return f >= -1.6777215E7f && f <= 1.6777215E7f;
    }

    static boolean inSafeFloatRange(long l) {
        return l >= -16777215L && l <= 0xFFFFFFL;
    }

    static boolean isNegativeZero(double d) {
        return Double.doubleToRawLongBits(d) == Double.doubleToRawLongBits(-0.0);
    }

    static boolean isNegativeZero(float f) {
        return Float.floatToRawIntBits(f) == Float.floatToRawIntBits(-0.0f);
    }
}

