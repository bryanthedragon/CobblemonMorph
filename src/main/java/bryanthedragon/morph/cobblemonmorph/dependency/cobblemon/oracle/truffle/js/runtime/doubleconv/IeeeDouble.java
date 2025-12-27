package com.oracle.truffle.js.runtime.doubleconv;

class IeeeDouble {
   static final long kSignMask = Long.MIN_VALUE;
   static final long kExponentMask = 9218868437227405312L;
   static final long kSignificandMask = 4503599627370495L;
   static final long kHiddenBit = 4503599627370496L;
   static final int kPhysicalSignificandSize = 52;
   static final int kSignificandSize = 53;
   private static final int kExponentBias = 1075;
   private static final int kDenormalExponent = -1074;
   private static final int kMaxExponent = 972;
   private static final long kInfinity = 9218868437227405312L;
   private static final long kNaN = 9221120237041090560L;

   static long doubleToLong(final double d) {
      return Double.doubleToRawLongBits(d);
   }

   static double longToDouble(final long d64) {
      return Double.longBitsToDouble(d64);
   }

   static DiyFp asDiyFp(final long d64) {
      assert !isSpecial(d64);

      return new DiyFp(significand(d64), exponent(d64));
   }

   static DiyFp asNormalizedDiyFp(final long d64) {
      assert value(d64) > 0.0;

      long f = significand(d64);

      int e;
      for (e = exponent(d64); (f & 4503599627370496L) == 0L; e--) {
         f <<= 1;
      }

      f <<= 11;
      e -= 11;
      return new DiyFp(f, e);
   }

   static double nextDouble(final long d64) {
      if (d64 == 9218868437227405312L) {
         return longToDouble(9218868437227405312L);
      } else if (sign(d64) < 0 && significand(d64) == 0L) {
         return 0.0;
      } else {
         return sign(d64) < 0 ? longToDouble(d64 - 1L) : longToDouble(d64 + 1L);
      }
   }

   static double previousDouble(final long d64) {
      if (d64 == -4503599627370496L) {
         return -longToDouble(9218868437227405312L);
      } else if (sign(d64) < 0) {
         return longToDouble(d64 + 1L);
      } else {
         return significand(d64) == 0L ? -0.0 : longToDouble(d64 - 1L);
      }
   }

   static int exponent(final long d64) {
      if (isDenormal(d64)) {
         return -1074;
      } else {
         int biased_e = (int)((d64 & 9218868437227405312L) >>> 52);
         return biased_e - 1075;
      }
   }

   static long significand(final long d64) {
      long significand = d64 & 4503599627370495L;
      return !isDenormal(d64) ? significand + 4503599627370496L : significand;
   }

   static boolean isDenormal(final long d64) {
      return (d64 & 9218868437227405312L) == 0L;
   }

   static boolean isSpecial(final long d64) {
      return (d64 & 9218868437227405312L) == 9218868437227405312L;
   }

   static boolean isNaN(final long d64) {
      return (d64 & 9218868437227405312L) == 9218868437227405312L && (d64 & 4503599627370495L) != 0L;
   }

   static boolean isInfinite(final long d64) {
      return (d64 & 9218868437227405312L) == 9218868437227405312L && (d64 & 4503599627370495L) == 0L;
   }

   static int sign(final long d64) {
      return (d64 & Long.MIN_VALUE) == 0L ? 1 : -1;
   }

   static void normalizedBoundaries(final long d64, final DiyFp m_minus, final DiyFp m_plus) {
      assert value(d64) > 0.0;

      DiyFp v = asDiyFp(d64);
      m_plus.setF((v.f() << 1) + 1L);
      m_plus.setE(v.e() - 1);
      m_plus.normalize();
      if (lowerBoundaryIsCloser(d64)) {
         m_minus.setF((v.f() << 2) - 1L);
         m_minus.setE(v.e() - 2);
      } else {
         m_minus.setF((v.f() << 1) - 1L);
         m_minus.setE(v.e() - 1);
      }

      m_minus.setF(m_minus.f() << m_minus.e() - m_plus.e());
      m_minus.setE(m_plus.e());
   }

   static boolean lowerBoundaryIsCloser(final long d64) {
      boolean physical_significand_is_zero = (d64 & 4503599627370495L) == 0L;
      return physical_significand_is_zero && exponent(d64) != -1074;
   }

   static double value(final long d64) {
      return longToDouble(d64);
   }

   static int significandSizeForOrderOfMagnitude(final int order) {
      if (order >= -1021) {
         return 53;
      } else {
         return order <= -1074 ? 0 : order - -1074;
      }
   }

   static double Infinity() {
      return longToDouble(9218868437227405312L);
   }

   static double NaN() {
      return longToDouble(9221120237041090560L);
   }
}
