package com.oracle.truffle.js.runtime.doubleconv;

class FixedDtoa {
   static final int kDoubleSignificandSize = 53;

   static void fillDigits32FixedLength(int number, final int requested_length, final DtoaBuffer buffer) {
      for (int i = requested_length - 1; i >= 0; i--) {
         buffer.chars[buffer.length + i] = (char)(48 + Integer.remainderUnsigned(number, 10));
         number = Integer.divideUnsigned(number, 10);
      }

      buffer.length += requested_length;
   }

   static void fillDigits32(int number, final DtoaBuffer buffer) {
      int number_length;
      for (number_length = 0; number != 0; number_length++) {
         int digit = Integer.remainderUnsigned(number, 10);
         number = Integer.divideUnsigned(number, 10);
         buffer.chars[buffer.length + number_length] = (char)(48 + digit);
      }

      int i = buffer.length;

      for (int j = buffer.length + number_length - 1; i < j; j--) {
         char tmp = buffer.chars[i];
         buffer.chars[i] = buffer.chars[j];
         buffer.chars[j] = tmp;
         i++;
      }

      buffer.length += number_length;
   }

   static void fillDigits64FixedLength(long number, final DtoaBuffer buffer) {
      int kTen7 = 10000000;
      int part2 = (int)Long.remainderUnsigned(number, 10000000L);
      number = Long.divideUnsigned(number, 10000000L);
      int part1 = (int)Long.remainderUnsigned(number, 10000000L);
      int part0 = (int)Long.divideUnsigned(number, 10000000L);
      fillDigits32FixedLength(part0, 3, buffer);
      fillDigits32FixedLength(part1, 7, buffer);
      fillDigits32FixedLength(part2, 7, buffer);
   }

   static void FillDigits64(long number, final DtoaBuffer buffer) {
      int kTen7 = 10000000;
      int part2 = (int)Long.remainderUnsigned(number, 10000000L);
      number = Long.divideUnsigned(number, 10000000L);
      int part1 = (int)Long.remainderUnsigned(number, 10000000L);
      int part0 = (int)Long.divideUnsigned(number, 10000000L);
      if (part0 != 0) {
         fillDigits32(part0, buffer);
         fillDigits32FixedLength(part1, 7, buffer);
         fillDigits32FixedLength(part2, 7, buffer);
      } else if (part1 != 0) {
         fillDigits32(part1, buffer);
         fillDigits32FixedLength(part2, 7, buffer);
      } else {
         fillDigits32(part2, buffer);
      }
   }

   static void roundUp(final DtoaBuffer buffer) {
      if (buffer.length == 0) {
         buffer.chars[0] = '1';
         buffer.decimalPoint = 1;
         buffer.length = 1;
      } else {
         buffer.chars[buffer.length - 1]++;

         for (int i = buffer.length - 1; i > 0; i--) {
            if (buffer.chars[i] != ':') {
               return;
            }

            buffer.chars[i] = '0';
            buffer.chars[i - 1]++;
         }

         if (buffer.chars[0] == ':') {
            buffer.chars[0] = '1';
            buffer.decimalPoint++;
         }
      }
   }

   static void fillFractionals(long fractionals, final int exponent, final int fractional_count, final DtoaBuffer buffer) {
      assert -128 <= exponent && exponent <= 0;

      if (-exponent <= 64) {
         assert fractionals >>> 56 == 0L;

         int point = -exponent;

         for (int i = 0; i < fractional_count && fractionals != 0L; i++) {
            fractionals *= 5L;
            int digit = (int)(fractionals >>> --point);

            assert digit <= 9;

            buffer.chars[buffer.length] = (char)(48 + digit);
            buffer.length++;
            fractionals -= (long)digit << point;
         }

         assert fractionals == 0L || point - 1 >= 0;

         if (fractionals != 0L && (fractionals >>> point - 1 & 1L) == 1L) {
            roundUp(buffer);
         }
      } else {
         assert 64 < -exponent && -exponent <= 128;

         FixedDtoa.UInt128 fractionals128 = new FixedDtoa.UInt128(fractionals, 0L);
         fractionals128.shift(-exponent - 64);
         int point = 128;

         for (int i = 0; i < fractional_count && !fractionals128.isZero(); i++) {
            fractionals128.multiply(5);
            int digit = fractionals128.divModPowerOf2(--point);

            assert digit <= 9;

            buffer.chars[buffer.length] = (char)(48 + digit);
            buffer.length++;
         }

         if (fractionals128.bitAt(point - 1) == 1) {
            roundUp(buffer);
         }
      }
   }

   static void trimZeros(final DtoaBuffer buffer) {
      while (buffer.length > 0 && buffer.chars[buffer.length - 1] == '0') {
         buffer.length--;
      }

      int first_non_zero = 0;

      while (first_non_zero < buffer.length && buffer.chars[first_non_zero] == '0') {
         first_non_zero++;
      }

      if (first_non_zero != 0) {
         for (int i = first_non_zero; i < buffer.length; i++) {
            buffer.chars[i - first_non_zero] = buffer.chars[i];
         }

         buffer.length -= first_non_zero;
         buffer.decimalPoint -= first_non_zero;
      }
   }

   static boolean fastFixedDtoa(final double v, final int fractional_count, final DtoaBuffer buffer) {
      long kMaxUInt32 = 4294967295L;
      long l = IeeeDouble.doubleToLong(v);
      long significand = IeeeDouble.significand(l);
      int exponent = IeeeDouble.exponent(l);
      if (exponent > 20) {
         return false;
      } else if (fractional_count > 20) {
         return false;
      } else {
         if (exponent + 53 > 64) {
            long kFive17 = 762939453125L;
            long divisor = 762939453125L;
            int divisor_power = 17;
            int quotient;
            long remainder;
            if (exponent > 17) {
               long dividend = significand << exponent - 17;
               quotient = (int)Long.divideUnsigned(dividend, divisor);
               remainder = Long.remainderUnsigned(dividend, divisor) << 17;
            } else {
               divisor <<= 17 - exponent;
               quotient = (int)Long.divideUnsigned(significand, divisor);
               remainder = Long.remainderUnsigned(significand, divisor) << exponent;
            }

            fillDigits32(quotient, buffer);
            fillDigits64FixedLength(remainder, buffer);
            buffer.decimalPoint = buffer.length;
         } else if (exponent >= 0) {
            significand <<= exponent;
            FillDigits64(significand, buffer);
            buffer.decimalPoint = buffer.length;
         } else if (exponent > -53) {
            long integrals = significand >>> -exponent;
            long fractionals = significand - (integrals << -exponent);
            if (Long.compareUnsigned(integrals, 4294967295L) > 0) {
               FillDigits64(integrals, buffer);
            } else {
               fillDigits32((int)integrals, buffer);
            }

            buffer.decimalPoint = buffer.length;
            fillFractionals(fractionals, exponent, fractional_count, buffer);
         } else if (exponent < -128) {
            assert fractional_count <= 20;

            buffer.reset();
            buffer.decimalPoint = -fractional_count;
         } else {
            buffer.decimalPoint = 0;
            fillFractionals(significand, exponent, fractional_count, buffer);
         }

         trimZeros(buffer);
         if (buffer.length == 0) {
            buffer.decimalPoint = -fractional_count;
         }

         return true;
      }
   }

   static class UInt128 {
      private static final long kMask32 = 4294967295L;
      private long high_bits_;
      private long low_bits_;

      UInt128(final long high_bits, final long low_bits) {
         this.high_bits_ = high_bits;
         this.low_bits_ = low_bits;
      }

      void multiply(final int multiplicand) {
         long accumulator = (this.low_bits_ & 4294967295L) * multiplicand;
         long part = accumulator & 4294967295L;
         accumulator >>>= 32;
         accumulator += (this.low_bits_ >>> 32) * multiplicand;
         this.low_bits_ = (accumulator << 32) + part;
         accumulator >>>= 32;
         accumulator += (this.high_bits_ & 4294967295L) * multiplicand;
         part = accumulator & 4294967295L;
         accumulator >>>= 32;
         accumulator += (this.high_bits_ >>> 32) * multiplicand;
         this.high_bits_ = (accumulator << 32) + part;

         assert accumulator >>> 32 == 0L;
      }

      void shift(final int shift_amount) {
         assert -64 <= shift_amount && shift_amount <= 64;

         if (shift_amount != 0) {
            if (shift_amount == -64) {
               this.high_bits_ = this.low_bits_;
               this.low_bits_ = 0L;
            } else if (shift_amount == 64) {
               this.low_bits_ = this.high_bits_;
               this.high_bits_ = 0L;
            } else if (shift_amount <= 0) {
               this.high_bits_ <<= -shift_amount;
               this.high_bits_ = this.high_bits_ + (this.low_bits_ >>> 64 + shift_amount);
               this.low_bits_ <<= -shift_amount;
            } else {
               this.low_bits_ >>>= shift_amount;
               this.low_bits_ = this.low_bits_ + (this.high_bits_ << 64 - shift_amount);
               this.high_bits_ >>>= shift_amount;
            }
         }
      }

      int divModPowerOf2(final int power) {
         if (power >= 64) {
            int result = (int)(this.high_bits_ >>> power - 64);
            this.high_bits_ -= (long)result << power - 64;
            return result;
         } else {
            long part_low = this.low_bits_ >>> power;
            long part_high = this.high_bits_ << 64 - power;
            int result = (int)(part_low + part_high);
            this.high_bits_ = 0L;
            this.low_bits_ -= part_low << power;
            return result;
         }
      }

      boolean isZero() {
         return this.high_bits_ == 0L && this.low_bits_ == 0L;
      }

      int bitAt(final int position) {
         return position >= 64 ? (int)(this.high_bits_ >>> position - 64) & 1 : (int)(this.low_bits_ >>> position) & 1;
      }
   }
}
