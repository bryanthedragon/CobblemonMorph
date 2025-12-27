package com.oracle.truffle.js.runtime.external;

import java.math.BigInteger;

public class DToA {
   private static final int Frac_mask = 1048575;
   private static final int Exp_shift = 20;
   private static final int Exp_msk1 = 1048576;
   private static final long Frac_maskL = 4503599627370495L;
   private static final int Exp_shiftL = 52;
   private static final long Exp_msk1L = 4503599627370496L;
   private static final int Bias = 1023;
   private static final int P = 53;
   private static final int Exp_shift1 = 20;
   private static final int Exp_mask = 2146435072;
   private static final int Exp_mask_shifted = 2047;
   private static final int Bndry_mask = 1048575;
   private static final int Log2P = 1;

   private DToA() {
   }

   private static char basedigit(int digit) {
      return (char)(digit >= 10 ? 87 + digit : 48 + digit);
   }

   private static int lo0bits(int y) {
      int x = y;
      if ((y & 7) != 0) {
         if ((y & 1) != 0) {
            return 0;
         } else {
            return (y & 2) != 0 ? 1 : 2;
         }
      } else {
         int k = 0;
         if ((y & 65535) == 0) {
            k = 16;
            x = y >>> 16;
         }

         if ((x & 0xFF) == 0) {
            k += 8;
            x >>>= 8;
         }

         if ((x & 15) == 0) {
            k += 4;
            x >>>= 4;
         }

         if ((x & 3) == 0) {
            k += 2;
            x >>>= 2;
         }

         if ((x & 1) == 0) {
            k++;
            x >>>= 1;
            if ((x & 1) == 0) {
               return 32;
            }
         }

         return k;
      }
   }

   private static int hi0bits(int xParam) {
      int x = xParam;
      int k = 0;
      if ((xParam & -65536) == 0) {
         k = 16;
         x = xParam << 16;
      }

      if ((x & 0xFF000000) == 0) {
         k += 8;
         x <<= 8;
      }

      if ((x & -268435456) == 0) {
         k += 4;
         x <<= 4;
      }

      if ((x & -1073741824) == 0) {
         k += 2;
         x <<= 2;
      }

      if ((x & -2147483648) == 0) {
         k++;
         if ((x & 1073741824) == 0) {
            return 32;
         }
      }

      return k;
   }

   private static void stuffBits(byte[] bits, int offset, int val) {
      bits[offset] = (byte)(val >> 24);
      bits[offset + 1] = (byte)(val >> 16);
      bits[offset + 2] = (byte)(val >> 8);
      bits[offset + 3] = (byte)val;
   }

   private static BigInteger d2b(double d, int[] e, int[] bits) {
      long dBits = Double.doubleToLongBits(d);
      int d0 = (int)(dBits >>> 32);
      int d1 = (int)dBits;
      int z = d0 & 1048575;
      d0 &= Integer.MAX_VALUE;
      int de;
      if ((de = d0 >>> 20) != 0) {
         z |= 1048576;
      }

      byte[] dblBits;
      int i;
      int k;
      if (d1 != 0) {
         dblBits = new byte[8];
         k = lo0bits(d1);
         int y = d1 >>> k;
         if (k != 0) {
            stuffBits(dblBits, 4, y | z << 32 - k);
            z >>= k;
         } else {
            stuffBits(dblBits, 4, y);
         }

         stuffBits(dblBits, 0, z);
         i = z != 0 ? 2 : 1;
      } else {
         dblBits = new byte[4];
         k = lo0bits(z);
         z >>>= k;
         stuffBits(dblBits, 0, z);
         k += 32;
         i = 1;
      }

      if (de != 0) {
         e[0] = de - 1023 - 52 + k;
         bits[0] = 53 - k;
      } else {
         e[0] = de - 1023 - 52 + 1 + k;
         bits[0] = 32 * i - hi0bits(z);
      }

      return new BigInteger(dblBits);
   }

   public static String jsDtobasestr(int base, double dParam) {
      if (2 <= base && base <= 36) {
         double d = dParam;
         if (Double.isNaN(dParam)) {
            return "NaN";
         } else if (Double.isInfinite(dParam)) {
            return dParam > 0.0 ? "Infinity" : "-Infinity";
         } else if (dParam == 0.0) {
            return "0";
         } else {
            boolean negative;
            if (dParam >= 0.0) {
               negative = false;
            } else {
               negative = true;
               d = -dParam;
            }

            double dfloor = Math.floor(d);
            long lfloor = (long)dfloor;
            String intDigits;
            if (lfloor == dfloor) {
               if (lfloor == 0L && negative) {
                  intDigits = "-0";
               } else {
                  intDigits = Long.toString(negative ? -lfloor : lfloor, base);
               }
            } else {
               long floorBits = Double.doubleToLongBits(dfloor);
               int exp = (int)(floorBits >> 52) & 2047;
               long mantissa;
               if (exp == 0) {
                  mantissa = (floorBits & 4503599627370495L) << 1;
               } else {
                  mantissa = floorBits & 4503599627370495L | 4503599627370496L;
               }

               if (negative) {
                  mantissa = -mantissa;
               }

               exp -= 1075;
               BigInteger x = BigInteger.valueOf(mantissa);
               if (exp > 0) {
                  x = x.shiftLeft(exp);
               } else if (exp < 0) {
                  x = x.shiftRight(-exp);
               }

               intDigits = x.toString(base);
            }

            if (d == dfloor) {
               return intDigits;
            } else {
               StringBuilder buffer = new StringBuilder();
               buffer.append(intDigits).append('.');
               double df = d - dfloor;
               long dBits = Double.doubleToLongBits(d);
               int word0 = (int)(dBits >> 32);
               int word1 = (int)dBits;
               int[] e = new int[1];
               int[] bbits = new int[1];
               BigInteger b = d2b(df, e, bbits);
               int s2 = -(word0 >>> 20 & 2047);
               if (s2 == 0) {
                  s2 = -1;
               }

               s2 += 1076;
               BigInteger mlo = BigInteger.ONE;
               BigInteger mhi = mlo;
               if (word1 == 0 && (word0 & 1048575) == 0 && (word0 & 2145386496) != 0) {
                  s2++;
                  mhi = BigInteger.valueOf(2L);
               }

               b = b.shiftLeft(e[0] + s2);
               BigInteger s = BigInteger.ONE;
               s = s.shiftLeft(s2);
               BigInteger bigBase = BigInteger.valueOf(base);
               boolean done = false;

               do {
                  b = b.multiply(bigBase);
                  BigInteger[] divResult = b.divideAndRemainder(s);
                  b = divResult[1];
                  int digit = (char)divResult[0].intValue();
                  if (mlo == mhi) {
                     mlo = mhi = mlo.multiply(bigBase);
                  } else {
                     mlo = mlo.multiply(bigBase);
                     mhi = mhi.multiply(bigBase);
                  }

                  int j = b.compareTo(mlo);
                  BigInteger delta = s.subtract(mhi);
                  int j1 = delta.signum() <= 0 ? 1 : b.compareTo(delta);
                  if (j1 == 0 && (word1 & 1) == 0) {
                     if (j > 0) {
                        digit++;
                     }

                     done = true;
                  } else if (j < 0 || j == 0 && (word1 & 1) == 0) {
                     if (j1 > 0) {
                        b = b.shiftLeft(1);
                        j1 = b.compareTo(s);
                        if (j1 > 0) {
                           digit++;
                        }
                     }

                     done = true;
                  } else if (j1 > 0) {
                     digit++;
                     done = true;
                  }

                  buffer.append(basedigit(digit));
               } while (!done);

               return buffer.toString();
            }
         }
      } else {
         throw new IllegalArgumentException("Bad base: " + base);
      }
   }
}
