
package com.oracle.truffle.js.runtime.util;

import java.util.Locale;

public final class TimeUtil {
    private TimeUtil() {
    }

    public static String format(long time) {
        if (time < 1000000L) {
            return String.format(Locale.ROOT, "%.2f\u00b5s", (double)time / 1000.0);
        }
        if (time < 1000000000L) {
            return String.format(Locale.ROOT, "%.2fms", (double)time / 1000000.0);
        }
        return String.format(Locale.ROOT, "%.2fs", (double)time / 1.0E9);
    }
}

