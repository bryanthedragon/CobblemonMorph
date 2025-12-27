package com.oracle.truffle.api.interop;

final class NumberUtils {
   private static final double DOUBLE_MAX_SAFE_INTEGER = 9.007199254740991E15;
   static final long LONG_MAX_SAFE_DOUBLE = 9007199254740991L;
   private static final float FLOAT_MAX_SAFE_INTEGER = 1.6777215E7F;
   static final int INT_MAX_SAFE_FLOAT = 16777215;

   private NumberUtils() {
   }

   static boolean inSafeIntegerRange(double d) {
      return d >= -9.007199254740991E15 && d <= 9.007199254740991E15;
   }

   static boolean inSafeDoubleRange(long l) {
      return l >= -9007199254740991L && l <= 9007199254740991L;
   }

   static boolean inSafeFloatRange(int i) {
      return i >= -16777215 && i <= 16777215;
   }

   static boolean inSafeIntegerRange(float f) {
      return f >= -1.6777215E7F && f <= 1.6777215E7F;
   }

   static boolean inSafeFloatRange(long l) {
      return l >= -16777215L && l <= 16777215L;
   }

   static boolean isNegativeZero(double d) {
      return Double.doubleToRawLongBits(d) == Double.doubleToRawLongBits(-0.0);
   }

   static boolean isNegativeZero(float f) {
      return Float.floatToRawIntBits(f) == Float.floatToRawIntBits(-0.0F);
   }
}
