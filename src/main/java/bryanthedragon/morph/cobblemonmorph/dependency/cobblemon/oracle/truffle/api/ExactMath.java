package com.oracle.truffle.api;

public final class ExactMath {
   private ExactMath() {
   }

   public static int multiplyHigh(int x, int y) {
      long r = (long)x * y;
      return (int)(r >> 32);
   }

   public static int multiplyHighUnsigned(int x, int y) {
      long xl = x & 4294967295L;
      long yl = y & 4294967295L;
      long r = xl * yl;
      return (int)(r >> 32);
   }

   public static long multiplyHigh(long x, long y) {
      long x0 = x & 4294967295L;
      long x1 = x >> 32;
      long y0 = y & 4294967295L;
      long y1 = y >> 32;
      long z0 = x0 * y0;
      long t = x1 * y0 + (z0 >>> 32);
      long z1 = t & 4294967295L;
      long z2 = t >> 32;
      z1 += x0 * y1;
      return x1 * y1 + z2 + (z1 >> 32);
   }

   public static long multiplyHighUnsigned(long x, long y) {
      long x0 = x & 4294967295L;
      long x1 = x >>> 32;
      long y0 = y & 4294967295L;
      long y1 = y >>> 32;
      long z0 = x0 * y0;
      long t = x1 * y0 + (z0 >>> 32);
      long z1 = t & 4294967295L;
      long z2 = t >>> 32;
      z1 += x0 * y1;
      return x1 * y1 + z2 + (z1 >>> 32);
   }

   public static float truncate(float x) {
      return (float)truncate((double)x);
   }

   public static double truncate(double x) {
      return x < 0.0 ? Math.ceil(x) : Math.floor(x);
   }
}
