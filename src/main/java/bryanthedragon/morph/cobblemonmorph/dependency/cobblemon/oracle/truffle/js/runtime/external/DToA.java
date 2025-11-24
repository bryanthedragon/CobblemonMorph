
package com.oracle.truffle.js.runtime.external;

import java.math.BigInteger;

public class DToA {
    private static final int Frac_mask = 1048575;
    private static final int Exp_shift = 20;
    private static final int Exp_msk1 = 0x100000;
    private static final long Frac_maskL = 0xFFFFFFFFFFFFFL;
    private static final int Exp_shiftL = 52;
    private static final long Exp_msk1L = 0x10000000000000L;
    private static final int Bias = 1023;
    private static final int P = 53;
    private static final int Exp_shift1 = 20;
    private static final int Exp_mask = 0x7FF00000;
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
        if ((x & 7) != 0) {
            if ((x & 1) != 0) {
                return 0;
            }
            if ((x & 2) != 0) {
                return 1;
            }
            return 2;
        }
        int k = 0;
        if ((x & 0xFFFF) == 0) {
            k = 16;
            x >>>= 16;
        }
        if ((x & 0xFF) == 0) {
            k += 8;
            x >>>= 8;
        }
        if ((x & 0xF) == 0) {
            k += 4;
            x >>>= 4;
        }
        if ((x & 3) == 0) {
            k += 2;
            x >>>= 2;
        }
        if ((x & 1) == 0) {
            ++k;
            if (((x >>>= 1) & 1) == 0) {
                return 32;
            }
        }
        return k;
    }

    private static int hi0bits(int xParam) {
        int x = xParam;
        int k = 0;
        if ((x & 0xFFFF0000) == 0) {
            k = 16;
            x <<= 16;
        }
        if ((x & 0xFF000000) == 0) {
            k += 8;
            x <<= 8;
        }
        if ((x & 0xF0000000) == 0) {
            k += 4;
            x <<= 4;
        }
        if ((x & 0xC0000000) == 0) {
            k += 2;
            x <<= 2;
        }
        if ((x & Integer.MIN_VALUE) == 0) {
            ++k;
            if ((x & 0x40000000) == 0) {
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
        int i;
        int k;
        byte[] dblBits;
        int y;
        long dBits = Double.doubleToLongBits(d);
        int d0 = (int)(dBits >>> 32);
        int d1 = (int)dBits;
        int z = d0 & 0xFFFFF;
        int de = (d0 &= Integer.MAX_VALUE) >>> 20;
        if (de != 0) {
            z |= 0x100000;
        }
        if ((y = d1) != 0) {
            dblBits = new byte[8];
            k = DToA.lo0bits(y);
            y >>>= k;
            if (k != 0) {
                DToA.stuffBits(dblBits, 4, y | z << 32 - k);
                z >>= k;
            } else {
                DToA.stuffBits(dblBits, 4, y);
            }
            DToA.stuffBits(dblBits, 0, z);
            i = z != 0 ? 2 : 1;
        } else {
            dblBits = new byte[4];
            k = DToA.lo0bits(z);
            DToA.stuffBits(dblBits, 0, z >>>= k);
            k += 32;
            i = 1;
        }
        if (de != 0) {
            e[0] = de - 1023 - 52 + k;
            bits[0] = 53 - k;
        } else {
            e[0] = de - 1023 - 52 + 1 + k;
            bits[0] = 32 * i - DToA.hi0bits(z);
        }
        return new BigInteger(dblBits);
    }

    public static String jsDtobasestr(int base, double dParam) {
        BigInteger mlo;
        String intDigits;
        boolean negative;
        if (2 > base || base > 36) {
            throw new IllegalArgumentException("Bad base: " + base);
        }
        double d = dParam;
        if (Double.isNaN(d)) {
            return "NaN";
        }
        if (Double.isInfinite(d)) {
            return d > 0.0 ? "Infinity" : "-Infinity";
        }
        if (d == 0.0) {
            return "0";
        }
        if (d >= 0.0) {
            negative = false;
        } else {
            negative = true;
            d = -d;
        }
        double dfloor = Math.floor(d);
        long lfloor = (long)dfloor;
        if ((double)lfloor == dfloor) {
            intDigits = lfloor == 0L && negative ? "-0" : Long.toString(negative ? -lfloor : lfloor, base);
        } else {
            long floorBits = Double.doubleToLongBits(dfloor);
            int exp = (int)(floorBits >> 52) & 0x7FF;
            long mantissa = exp == 0 ? (floorBits & 0xFFFFFFFFFFFFFL) << 1 : floorBits & 0xFFFFFFFFFFFFFL | 0x10000000000000L;
            if (negative) {
                mantissa = -mantissa;
            }
            BigInteger x = BigInteger.valueOf(mantissa);
            if ((exp -= 1075) > 0) {
                x = x.shiftLeft(exp);
            } else if (exp < 0) {
                x = x.shiftRight(-exp);
            }
            intDigits = x.toString(base);
        }
        if (d == dfloor) {
            return intDigits;
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append(intDigits).append('.');
        double df = d - dfloor;
        long dBits = Double.doubleToLongBits(d);
        int word0 = (int)(dBits >> 32);
        int word1 = (int)dBits;
        int[] e = new int[1];
        int[] bbits = new int[1];
        BigInteger b = DToA.d2b(df, e, bbits);
        int s2 = -(word0 >>> 20 & 0x7FF);
        if (s2 == 0) {
            s2 = -1;
        }
        s2 += 1076;
        BigInteger mhi = mlo = BigInteger.ONE;
        if (word1 == 0 && (word0 & 0xFFFFF) == 0 && (word0 & 0x7FE00000) != 0) {
            ++s2;
            mhi = BigInteger.valueOf(2L);
        }
        b = b.shiftLeft(e[0] + s2);
        BigInteger s = BigInteger.ONE;
        s = s.shiftLeft(s2);
        BigInteger bigBase = BigInteger.valueOf(base);
        boolean done = false;
        do {
            int j1;
            b = b.multiply(bigBase);
            BigInteger[] divResult = b.divideAndRemainder(s);
            b = divResult[1];
            int digit = divResult[0].intValue();
            if (mlo == mhi) {
                mlo = mhi = mlo.multiply(bigBase);
            } else {
                mlo = mlo.multiply(bigBase);
                mhi = mhi.multiply(bigBase);
            }
            int j = b.compareTo(mlo);
            BigInteger delta = s.subtract(mhi);
            int n = j1 = delta.signum() <= 0 ? 1 : b.compareTo(delta);
            if (j1 == 0 && (word1 & 1) == 0) {
                if (j > 0) {
                    ++digit;
                }
                done = true;
            } else if (j < 0 || j == 0 && (word1 & 1) == 0) {
                if (j1 > 0 && (j1 = (b = b.shiftLeft(1)).compareTo(s)) > 0) {
                    ++digit;
                }
                done = true;
            } else if (j1 > 0) {
                ++digit;
                done = true;
            }
            buffer.append(DToA.basedigit(digit));
        } while (!done);
        return buffer.toString();
    }
}

