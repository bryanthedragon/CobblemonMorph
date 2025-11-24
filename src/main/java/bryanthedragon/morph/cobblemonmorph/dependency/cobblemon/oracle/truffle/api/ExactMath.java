
package com.oracle.truffle.api;

public final class ExactMath {
    private ExactMath() {
    }

    public static int multiplyHigh(int x, int y) {
        long r = (long)x * (long)y;
        return (int)(r >> 32);
    }

    public static int multiplyHighUnsigned(int x, int y) {
        long xl = (long)x & 0xFFFFFFFFL;
        long yl = (long)y & 0xFFFFFFFFL;
        long r = xl * yl;
        return (int)(r >> 32);
    }

    public static long multiplyHigh(long x, long y) {
        long x0 = x & 0xFFFFFFFFL;
        long x1 = x >> 32;
        long y0 = y & 0xFFFFFFFFL;
        long y1 = y >> 32;
        long z0 = x0 * y0;
        long t = x1 * y0 + (z0 >>> 32);
        long z1 = t & 0xFFFFFFFFL;
        long z2 = t >> 32;
        return x1 * y1 + z2 + ((z1 += x0 * y1) >> 32);
    }

    public static long multiplyHighUnsigned(long x, long y) {
        long x0 = x & 0xFFFFFFFFL;
        long x1 = x >>> 32;
        long y0 = y & 0xFFFFFFFFL;
        long y1 = y >>> 32;
        long z0 = x0 * y0;
        long t = x1 * y0 + (z0 >>> 32);
        long z1 = t & 0xFFFFFFFFL;
        long z2 = t >>> 32;
        return x1 * y1 + z2 + ((z1 += x0 * y1) >>> 32);
    }

    public static float truncate(float x) {
        return (float)ExactMath.truncate((double)x);
    }

    public static double truncate(double x) {
        return x < 0.0 ? Math.ceil(x) : Math.floor(x);
    }
}

