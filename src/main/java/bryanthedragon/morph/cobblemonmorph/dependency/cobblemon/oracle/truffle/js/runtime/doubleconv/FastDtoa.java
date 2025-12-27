package com.oracle.truffle.js.runtime.doubleconv;

class FastDtoa {
   static final int kFastDtoaMaximalLength = 17;
   static final int kMinimalTargetExponent = -60;
   static final int kMaximalTargetExponent = -32;
   static final int[] kSmallPowersOfTen = new int[]{0, 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};

   static boolean roundWeed(
      final DtoaBuffer buffer, final long distance_too_high_w, final long unsafe_interval, long rest, final long ten_kappa, final long unit
   ) {
      long small_distance = distance_too_high_w - unit;
      long big_distance = distance_too_high_w + unit;

      assert Long.compareUnsigned(rest, unsafe_interval) <= 0;

      while (
         Long.compareUnsigned(rest, small_distance) < 0
            && Long.compareUnsigned(unsafe_interval - rest, ten_kappa) >= 0
            && (
               Long.compareUnsigned(rest + ten_kappa, small_distance) < 0
                  || Long.compareUnsigned(small_distance - rest, rest + ten_kappa - small_distance) >= 0
            )
      ) {
         buffer.chars[buffer.length - 1]--;
         rest += ten_kappa;
      }

      return Long.compareUnsigned(rest, big_distance) >= 0
            || Long.compareUnsigned(unsafe_interval - rest, ten_kappa) < 0
            || Long.compareUnsigned(rest + ten_kappa, big_distance) >= 0 && Long.compareUnsigned(big_distance - rest, rest + ten_kappa - big_distance) <= 0
         ? Long.compareUnsigned(2L * unit, rest) <= 0 && Long.compareUnsigned(rest, unsafe_interval - 4L * unit) <= 0
         : false;
   }

   static int roundWeedCounted(final char[] buffer, final int length, final long rest, final long ten_kappa, final long unit) {
      assert Long.compareUnsigned(rest, ten_kappa) < 0;

      if (Long.compareUnsigned(unit, ten_kappa) >= 0) {
         return 0;
      } else if (Long.compareUnsigned(ten_kappa - unit, unit) <= 0) {
         return 0;
      } else if (Long.compareUnsigned(ten_kappa - rest, rest) > 0 && Long.compareUnsigned(ten_kappa - 2L * rest, 2L * unit) >= 0) {
         return 1;
      } else if (Long.compareUnsigned(rest, unit) > 0 && Long.compareUnsigned(ten_kappa - (rest - unit), rest - unit) <= 0) {
         buffer[length - 1]++;

         for (int i = length - 1; i > 0 && buffer[i] == ':'; i--) {
            buffer[i] = '0';
            buffer[i - 1]++;
         }

         if (buffer[0] == ':') {
            buffer[0] = '1';
            return 2;
         } else {
            return 1;
         }
      } else {
         return 0;
      }
   }

   static long biggestPowerTen(final int number, final int number_bits) {
      assert ((long)number & 4294967295L) < 1L << number_bits + 1;

      int exponent_plus_one_guess = (number_bits + 1) * 1233 >>> 12;
      if (number < kSmallPowersOfTen[++exponent_plus_one_guess]) {
         exponent_plus_one_guess--;
      }

      int power = kSmallPowersOfTen[exponent_plus_one_guess];
      return (long)power << 32 | exponent_plus_one_guess;
   }

   static boolean digitGen(final DiyFp low, final DiyFp w, final DiyFp high, final DtoaBuffer buffer, final int mk) {
      assert low.e() == w.e() && w.e() == high.e();

      assert Long.compareUnsigned(low.f() + 1L, high.f() - 1L) <= 0;

      assert -60 <= w.e() && w.e() <= -32;

      long unit = 1L;
      DiyFp too_low = new DiyFp(low.f() - unit, low.e());
      DiyFp too_high = new DiyFp(high.f() + unit, high.e());
      DiyFp unsafe_interval = DiyFp.minus(too_high, too_low);
      DiyFp one = new DiyFp(1L << -w.e(), w.e());
      int integrals = (int)(too_high.f() >>> -one.e());
      long fractionals = too_high.f() & one.f() - 1L;
      long result = biggestPowerTen(integrals, 64 - -one.e());
      int divisor = (int)(result >>> 32);
      int divisor_exponent_plus_one = (int)result;

      int kappa;
      for (kappa = divisor_exponent_plus_one; kappa > 0; divisor /= 10) {
         int digit = integrals / divisor;

         assert digit <= 9;

         buffer.append((char)(48 + digit));
         integrals %= divisor;
         kappa--;
         long rest = ((long)integrals << -one.e()) + fractionals;
         if (Long.compareUnsigned(rest, unsafe_interval.f()) < 0) {
            buffer.decimalPoint = buffer.length - mk + kappa;
            return roundWeed(buffer, DiyFp.minus(too_high, w).f(), unsafe_interval.f(), rest, (long)divisor << -one.e(), unit);
         }
      }

      assert one.e() >= -60;

      assert fractionals < one.f();

      assert Long.compareUnsigned(Long.divideUnsigned(-1L, 10L), one.f()) >= 0;

      do {
         fractionals *= 10L;
         unit *= 10L;
         unsafe_interval.setF(unsafe_interval.f() * 10L);
         int digitx = (int)(fractionals >>> -one.e());

         assert digitx <= 9;

         buffer.append((char)(48 + digitx));
         fractionals &= one.f() - 1L;
         kappa--;
      } while (Long.compareUnsigned(fractionals, unsafe_interval.f()) >= 0);

      buffer.decimalPoint = buffer.length - mk + kappa;
      return roundWeed(buffer, DiyFp.minus(too_high, w).f() * unit, unsafe_interval.f(), fractionals, one.f(), unit);
   }

   static boolean digitGenCounted(final DiyFp w, int requested_digits, final DtoaBuffer buffer, final int mk) {
      assert -60 <= w.e() && w.e() <= -32;

      long w_error = 1L;
      DiyFp one = new DiyFp(1L << -w.e(), w.e());
      int integrals = (int)(w.f() >>> -one.e());
      long fractionals = w.f() & one.f() - 1L;
      long biggestPower = biggestPowerTen(integrals, 64 - -one.e());
      int divisor = (int)(biggestPower >>> 32);
      int divisor_exponent_plus_one = (int)biggestPower;

      int kappa;
      for (kappa = divisor_exponent_plus_one; kappa > 0; divisor /= 10) {
         int digit = integrals / divisor;

         assert digit <= 9;

         buffer.append((char)(48 + digit));
         requested_digits--;
         integrals %= divisor;
         kappa--;
         if (requested_digits == 0) {
            break;
         }
      }

      if (requested_digits == 0) {
         long rest = ((long)integrals << -one.e()) + fractionals;
         int result = roundWeedCounted(buffer.chars, buffer.length, rest, (long)divisor << -one.e(), w_error);
         buffer.decimalPoint = buffer.length - mk + kappa + (result == 2 ? 1 : 0);
         return result > 0;
      } else {
         assert one.e() >= -60;

         assert fractionals < one.f();

         assert Long.compareUnsigned(Long.divideUnsigned(-1L, 10L), one.f()) >= 0;

         while (requested_digits > 0 && fractionals > w_error) {
            fractionals *= 10L;
            w_error *= 10L;
            int digitx = (int)(fractionals >>> -one.e());

            assert digitx <= 9;

            buffer.append((char)(48 + digitx));
            requested_digits--;
            fractionals &= one.f() - 1L;
            kappa--;
         }

         if (requested_digits != 0) {
            return false;
         } else {
            int result = roundWeedCounted(buffer.chars, buffer.length, fractionals, one.f(), w_error);
            buffer.decimalPoint = buffer.length - mk + kappa + (result == 2 ? 1 : 0);
            return result > 0;
         }
      }
   }

   static boolean grisu3(final double v, final DtoaBuffer buffer) {
      long d64 = IeeeDouble.doubleToLong(v);
      DiyFp w = IeeeDouble.asNormalizedDiyFp(d64);
      DiyFp boundary_minus = new DiyFp();
      DiyFp boundary_plus = new DiyFp();
      IeeeDouble.normalizedBoundaries(d64, boundary_minus, boundary_plus);

      assert boundary_plus.e() == w.e();

      DiyFp ten_mk = new DiyFp();
      int ten_mk_minimal_binary_exponent = -60 - (w.e() + 64);
      int ten_mk_maximal_binary_exponent = -32 - (w.e() + 64);
      int mk = CachedPowers.getCachedPowerForBinaryExponentRange(ten_mk_minimal_binary_exponent, ten_mk_maximal_binary_exponent, ten_mk);

      assert -60 <= w.e() + ten_mk.e() + 64 && -32 >= w.e() + ten_mk.e() + 64;

      DiyFp scaled_w = DiyFp.times(w, ten_mk);

      assert scaled_w.e() == boundary_plus.e() + ten_mk.e() + 64;

      DiyFp scaled_boundary_minus = DiyFp.times(boundary_minus, ten_mk);
      DiyFp scaled_boundary_plus = DiyFp.times(boundary_plus, ten_mk);
      return digitGen(scaled_boundary_minus, scaled_w, scaled_boundary_plus, buffer, mk);
   }

   static boolean grisu3Counted(final double v, final int requested_digits, final DtoaBuffer buffer) {
      long d64 = IeeeDouble.doubleToLong(v);
      DiyFp w = IeeeDouble.asNormalizedDiyFp(d64);
      DiyFp ten_mk = new DiyFp();
      int ten_mk_minimal_binary_exponent = -60 - (w.e() + 64);
      int ten_mk_maximal_binary_exponent = -32 - (w.e() + 64);
      int mk = CachedPowers.getCachedPowerForBinaryExponentRange(ten_mk_minimal_binary_exponent, ten_mk_maximal_binary_exponent, ten_mk);

      assert -60 <= w.e() + ten_mk.e() + 64 && -32 >= w.e() + ten_mk.e() + 64;

      DiyFp scaled_w = DiyFp.times(w, ten_mk);
      return digitGenCounted(scaled_w, requested_digits, buffer, mk);
   }
}
