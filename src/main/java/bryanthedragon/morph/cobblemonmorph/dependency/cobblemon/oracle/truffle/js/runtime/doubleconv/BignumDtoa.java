package com.oracle.truffle.js.runtime.doubleconv;

class BignumDtoa {
   private static int normalizedExponent(long significand, int exponent) {
      assert significand != 0L;

      while ((significand & 4503599627370496L) == 0L) {
         significand <<= 1;
         exponent--;
      }

      return exponent;
   }

   static void bignumDtoa(final double v, final DtoaMode mode, final int requested_digits, final DtoaBuffer buffer) {
      assert v > 0.0;

      assert !IeeeDouble.isSpecial(IeeeDouble.doubleToLong(v));

      long l = IeeeDouble.doubleToLong(v);
      long significand = IeeeDouble.significand(l);
      int exponent = IeeeDouble.exponent(l);
      boolean lower_boundary_is_closer = IeeeDouble.lowerBoundaryIsCloser(l);
      boolean need_boundary_deltas = mode == DtoaMode.SHORTEST;
      boolean is_even = (significand & 1L) == 0L;

      assert significand != 0L;

      int normalizedExponent = normalizedExponent(significand, exponent);
      int estimated_power = estimatePower(normalizedExponent);
      if (mode == DtoaMode.FIXED && -estimated_power - 1 > requested_digits) {
         buffer.reset();
         buffer.decimalPoint = -requested_digits;
      } else {
         Bignum numerator = new Bignum();
         Bignum denominator = new Bignum();
         Bignum delta_minus = new Bignum();
         Bignum delta_plus = new Bignum();
         initialScaledStartValues(
            significand, exponent, lower_boundary_is_closer, estimated_power, need_boundary_deltas, numerator, denominator, delta_minus, delta_plus
         );
         buffer.decimalPoint = fixupMultiply10(estimated_power, is_even, numerator, denominator, delta_minus, delta_plus);
         switch (mode) {
            case SHORTEST:
               generateShortestDigits(numerator, denominator, delta_minus, delta_plus, is_even, buffer);
               break;
            case FIXED:
               bignumToFixed(requested_digits, numerator, denominator, buffer);
               break;
            case PRECISION:
               generateCountedDigits(requested_digits, numerator, denominator, buffer);
               break;
            default:
               throw new RuntimeException();
         }
      }
   }

   static void generateShortestDigits(
      final Bignum numerator, final Bignum denominator, final Bignum delta_minus, Bignum delta_plus, final boolean is_even, final DtoaBuffer buffer
   ) {
      if (Bignum.equal(delta_minus, delta_plus)) {
         delta_plus = delta_minus;
      }

      while (true) {
         char digit = numerator.divideModuloIntBignum(denominator);

         assert digit <= '\t';

         buffer.append((char)(digit + '0'));
         boolean in_delta_room_minus;
         if (is_even) {
            in_delta_room_minus = Bignum.lessEqual(numerator, delta_minus);
         } else {
            in_delta_room_minus = Bignum.less(numerator, delta_minus);
         }

         boolean in_delta_room_plus;
         if (is_even) {
            in_delta_room_plus = Bignum.plusCompare(numerator, delta_plus, denominator) >= 0;
         } else {
            in_delta_room_plus = Bignum.plusCompare(numerator, delta_plus, denominator) > 0;
         }

         if (in_delta_room_minus || in_delta_room_plus) {
            if (in_delta_room_minus && in_delta_room_plus) {
               int compare = Bignum.plusCompare(numerator, numerator, denominator);
               if (compare >= 0) {
                  if (compare > 0) {
                     assert buffer.chars[buffer.length - 1] != '9';

                     buffer.chars[buffer.length - 1]++;
                  } else if ((buffer.chars[buffer.length - 1] - '0') % 2 != 0) {
                     assert buffer.chars[buffer.length - 1] != '9';

                     buffer.chars[buffer.length - 1]++;
                  }
               }

               return;
            } else if (in_delta_room_minus) {
               return;
            } else {
               assert buffer.chars[buffer.length - 1] != '9';

               buffer.chars[buffer.length - 1]++;
               return;
            }
         }

         numerator.times10();
         delta_minus.times10();
         if (delta_minus != delta_plus) {
            delta_plus.times10();
         }
      }
   }

   static void generateCountedDigits(final int count, final Bignum numerator, final Bignum denominator, final DtoaBuffer buffer) {
      assert count >= 0;

      for (int i = 0; i < count - 1; i++) {
         char digit = numerator.divideModuloIntBignum(denominator);

         assert digit <= '\t';

         buffer.chars[i] = (char)(digit + '0');
         numerator.times10();
      }

      char digit = numerator.divideModuloIntBignum(denominator);
      if (Bignum.plusCompare(numerator, numerator, denominator) >= 0) {
         digit++;
      }

      assert digit <= '\n';

      buffer.chars[count - 1] = (char)(digit + '0');

      for (int i = count - 1; i > 0 && buffer.chars[i] == ':'; i--) {
         buffer.chars[i] = '0';
         buffer.chars[i - 1]++;
      }

      if (buffer.chars[0] == ':') {
         buffer.chars[0] = '1';
         buffer.decimalPoint++;
      }

      buffer.length = count;
   }

   static void bignumToFixed(final int requested_digits, final Bignum numerator, final Bignum denominator, final DtoaBuffer buffer) {
      if (-buffer.decimalPoint > requested_digits) {
         buffer.decimalPoint = -requested_digits;
         buffer.length = 0;
      } else if (-buffer.decimalPoint == requested_digits) {
         assert buffer.decimalPoint == -requested_digits;

         denominator.times10();
         if (Bignum.plusCompare(numerator, numerator, denominator) >= 0) {
            buffer.chars[0] = '1';
            buffer.length = 1;
            buffer.decimalPoint++;
         } else {
            buffer.length = 0;
         }
      } else {
         int needed_digits = buffer.decimalPoint + requested_digits;
         generateCountedDigits(needed_digits, numerator, denominator, buffer);
      }
   }

   static int estimatePower(final int exponent) {
      double k1Log10 = 0.30102999566398114;
      int kSignificandSize = 53;
      double estimate = Math.ceil((exponent + 53 - 1) * 0.30102999566398114 - 1.0E-10);
      return (int)estimate;
   }

   static void initialScaledStartValuesPositiveExponent(
      final long significand,
      final int exponent,
      final int estimated_power,
      final boolean need_boundary_deltas,
      final Bignum numerator,
      final Bignum denominator,
      final Bignum delta_minus,
      final Bignum delta_plus
   ) {
      assert estimated_power >= 0;

      numerator.assignUInt64(significand);
      numerator.shiftLeft(exponent);
      denominator.assignPowerUInt16(10, estimated_power);
      if (need_boundary_deltas) {
         denominator.shiftLeft(1);
         numerator.shiftLeft(1);
         delta_plus.assignUInt16('\u0001');
         delta_plus.shiftLeft(exponent);
         delta_minus.assignUInt16('\u0001');
         delta_minus.shiftLeft(exponent);
      }
   }

   static void initialScaledStartValuesNegativeExponentPositivePower(
      final long significand,
      final int exponent,
      final int estimated_power,
      final boolean need_boundary_deltas,
      final Bignum numerator,
      final Bignum denominator,
      final Bignum delta_minus,
      final Bignum delta_plus
   ) {
      numerator.assignUInt64(significand);
      denominator.assignPowerUInt16(10, estimated_power);
      denominator.shiftLeft(-exponent);
      if (need_boundary_deltas) {
         denominator.shiftLeft(1);
         numerator.shiftLeft(1);
         delta_plus.assignUInt16('\u0001');
         delta_minus.assignUInt16('\u0001');
      }
   }

   static void initialScaledStartValuesNegativeExponentNegativePower(
      final long significand,
      final int exponent,
      final int estimated_power,
      final boolean need_boundary_deltas,
      final Bignum numerator,
      final Bignum denominator,
      final Bignum delta_minus,
      final Bignum delta_plus
   ) {
      numerator.assignPowerUInt16(10, -estimated_power);
      if (need_boundary_deltas) {
         delta_plus.assignBignum(numerator);
         delta_minus.assignBignum(numerator);
      }

      assert numerator == numerator;

      numerator.multiplyByUInt64(significand);
      denominator.assignUInt16('\u0001');
      denominator.shiftLeft(-exponent);
      if (need_boundary_deltas) {
         numerator.shiftLeft(1);
         denominator.shiftLeft(1);
      }
   }

   static void initialScaledStartValues(
      final long significand,
      final int exponent,
      final boolean lower_boundary_is_closer,
      final int estimated_power,
      final boolean need_boundary_deltas,
      final Bignum numerator,
      final Bignum denominator,
      final Bignum delta_minus,
      final Bignum delta_plus
   ) {
      if (exponent >= 0) {
         initialScaledStartValuesPositiveExponent(significand, exponent, estimated_power, need_boundary_deltas, numerator, denominator, delta_minus, delta_plus);
      } else if (estimated_power >= 0) {
         initialScaledStartValuesNegativeExponentPositivePower(
            significand, exponent, estimated_power, need_boundary_deltas, numerator, denominator, delta_minus, delta_plus
         );
      } else {
         initialScaledStartValuesNegativeExponentNegativePower(
            significand, exponent, estimated_power, need_boundary_deltas, numerator, denominator, delta_minus, delta_plus
         );
      }

      if (need_boundary_deltas && lower_boundary_is_closer) {
         denominator.shiftLeft(1);
         numerator.shiftLeft(1);
         delta_plus.shiftLeft(1);
      }
   }

   static int fixupMultiply10(
      final int estimated_power, final boolean is_even, final Bignum numerator, final Bignum denominator, final Bignum delta_minus, final Bignum delta_plus
   ) {
      boolean in_range;
      if (is_even) {
         in_range = Bignum.plusCompare(numerator, delta_plus, denominator) >= 0;
      } else {
         in_range = Bignum.plusCompare(numerator, delta_plus, denominator) > 0;
      }

      int decimal_point;
      if (in_range) {
         decimal_point = estimated_power + 1;
      } else {
         decimal_point = estimated_power;
         numerator.times10();
         if (Bignum.equal(delta_minus, delta_plus)) {
            delta_minus.times10();
            delta_plus.assignBignum(delta_minus);
         } else {
            delta_minus.times10();
            delta_plus.times10();
         }
      }

      return decimal_point;
   }
}
