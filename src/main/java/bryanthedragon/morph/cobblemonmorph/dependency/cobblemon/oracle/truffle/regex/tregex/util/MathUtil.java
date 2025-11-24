
package com.oracle.truffle.regex.tregex.util;

public final class MathUtil {
    public static int log2floor(int x) {
        return 31 - Integer.numberOfLeadingZeros(x);
    }

    public static int log2ceil(int x) {
        return 32 - Integer.numberOfLeadingZeros(x - 1);
    }
}

