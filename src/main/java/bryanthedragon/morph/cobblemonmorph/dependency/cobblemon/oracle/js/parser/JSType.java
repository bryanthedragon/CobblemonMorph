package com.oracle.js.parser;

public final class JSType {
   private JSType() {
   }

   public static boolean isRepresentableAsInt(final double number) {
      return (int)number == number;
   }

   public static boolean isStrictlyRepresentableAsInt(final double number) {
      return isRepresentableAsInt(number) && isNotNegativeZero(number);
   }

   public static boolean isRepresentableAsLong(final double number) {
      return (long)number == number;
   }

   public static boolean isStrictlyRepresentableAsLong(final double number) {
      return isRepresentableAsLong(number) && isNotNegativeZero(number);
   }

   private static boolean isNotNegativeZero(final double number) {
      return Double.doubleToRawLongBits(number) != Long.MIN_VALUE;
   }
}
