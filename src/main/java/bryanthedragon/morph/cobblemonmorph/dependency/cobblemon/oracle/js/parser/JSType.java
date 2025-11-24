
package com.oracle.js.parser;

public final class JSType {
    private JSType() {
    }

    public static boolean isRepresentableAsInt(double number) {
        return (double)((int)number) == number;
    }

    public static boolean isStrictlyRepresentableAsInt(double number) {
        return JSType.isRepresentableAsInt(number) && JSType.isNotNegativeZero(number);
    }

    public static boolean isRepresentableAsLong(double number) {
        return (double)((long)number) == number;
    }

    public static boolean isStrictlyRepresentableAsLong(double number) {
        return JSType.isRepresentableAsLong(number) && JSType.isNotNegativeZero(number);
    }

    private static boolean isNotNegativeZero(double number) {
        return Double.doubleToRawLongBits(number) != Long.MIN_VALUE;
    }
}

