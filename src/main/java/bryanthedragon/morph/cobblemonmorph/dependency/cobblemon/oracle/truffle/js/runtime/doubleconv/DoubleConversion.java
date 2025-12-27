package com.oracle.truffle.js.runtime.doubleconv;

public final class DoubleConversion {
   private static final int kMaxFixedDigitsBeforePoint = 60;
   private static final int kMaxFixedDigitsAfterPoint = 60;
   private static final int kMaxExponentialDigits = 120;
   private static final int kBase10MaximalLength = 17;

   private DoubleConversion() {
   }

   public static String toShortest(final double value) {
      assert Double.isFinite(value) : value;

      DtoaBuffer buffer = new DtoaBuffer(17);
      dtoaShortest(value, buffer);
      return buffer.format(DtoaMode.SHORTEST, 0);
   }

   private static void dtoaShortest(final double value, final DtoaBuffer buffer) {
      double absValue = Math.abs(value);
      if (value < 0.0) {
         buffer.isNegative = true;
      }

      if (value == 0.0) {
         buffer.append('0');
         buffer.decimalPoint = 1;
      } else if (!fastDtoaShortest(absValue, buffer)) {
         buffer.reset();
         bignumDtoa(absValue, DtoaMode.SHORTEST, 0, buffer);
      }
   }

   public static String toFixed(final double value, final int requestedDigits) {
      assert Double.isFinite(value) : value;

      DtoaBuffer buffer = new DtoaBuffer(120);
      double absValue = Math.abs(value);
      if (value < 0.0) {
         buffer.isNegative = true;
      }

      if (value == 0.0) {
         buffer.append('0');
         buffer.decimalPoint = 1;
      } else if (!fixedDtoa(absValue, requestedDigits, buffer)) {
         buffer.reset();
         bignumDtoa(absValue, DtoaMode.FIXED, requestedDigits, buffer);
      }

      return buffer.format(DtoaMode.FIXED, requestedDigits);
   }

   public static String toPrecision(final double value, final int precision) {
      assert Double.isFinite(value) : value;

      DtoaBuffer buffer = new DtoaBuffer(precision);
      dtoaPrecision(value, precision, buffer);
      return buffer.format(DtoaMode.PRECISION, 0);
   }

   private static void dtoaPrecision(final double value, final int precision, final DtoaBuffer buffer) {
      double absValue = Math.abs(value);
      if (value < 0.0) {
         buffer.isNegative = true;
      }

      if (value == 0.0) {
         for (int i = 0; i < precision; i++) {
            buffer.append('0');
         }

         buffer.decimalPoint = 1;
      } else if (!fastDtoaCounted(absValue, precision, buffer)) {
         buffer.reset();
         bignumDtoa(absValue, DtoaMode.PRECISION, precision, buffer);
      }
   }

   public static void bignumDtoa(final double v, final DtoaMode mode, final int digits, final DtoaBuffer buffer) {
      assert v > 0.0 && !Double.isNaN(v) && !Double.isInfinite(v) : v;

      BignumDtoa.bignumDtoa(v, mode, digits, buffer);
   }

   public static boolean fastDtoaShortest(final double v, final DtoaBuffer buffer) {
      assert v > 0.0 && !Double.isNaN(v) && !Double.isInfinite(v) : v;

      return FastDtoa.grisu3(v, buffer);
   }

   public static boolean fastDtoaCounted(final double v, final int precision, final DtoaBuffer buffer) {
      assert v > 0.0 && !Double.isNaN(v) && !Double.isInfinite(v) : v;

      return FastDtoa.grisu3Counted(v, precision, buffer);
   }

   public static boolean fixedDtoa(final double v, final int digits, final DtoaBuffer buffer) {
      assert v > 0.0 && !Double.isNaN(v) && !Double.isInfinite(v) : v;

      return FixedDtoa.fastFixedDtoa(v, digits, buffer);
   }

   public static String toExponential(double value, int requestedDigits) {
      return toExponential(value, requestedDigits, true);
   }

   public static String toExponential(double value, int requestedDigits, boolean uniqueZero) {
      assert Double.isFinite(value) : value;

      assert requestedDigits >= -1 && requestedDigits <= 120 : requestedDigits;

      boolean sign = value < 0.0;
      double absValue = Math.abs(value);
      int kDecimalRepCapacity = 121;

      assert kDecimalRepCapacity > 17;

      DtoaBuffer buffer = new DtoaBuffer(kDecimalRepCapacity);
      if (requestedDigits == -1) {
         dtoaShortest(absValue, buffer);
      } else {
         dtoaPrecision(absValue, requestedDigits + 1, buffer);

         assert buffer.getLength() <= requestedDigits + 1;

         for (int i = buffer.getLength(); i < requestedDigits + 1; i++) {
            buffer.append('0');
         }

         assert buffer.getLength() == requestedDigits + 1;
      }

      StringBuilder resultBuilder = new StringBuilder();
      if (sign && (value != 0.0 || !uniqueZero)) {
         resultBuilder.append('-');
      }

      buffer.toExponentialFormat(resultBuilder);
      return resultBuilder.toString();
   }
}
