
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TStringInternalNodes;
import com.oracle.truffle.api.strings.TStringOps;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringIterator;

final class NumberConversion {
    private static final byte[] INT_MIN_VALUE_BYTES = new byte[]{45, 50, 49, 52, 55, 52, 56, 51, 54, 52, 56};
    private static final byte[] LONG_MIN_VALUE_BYTES = new byte[]{45, 57, 50, 50, 51, 51, 55, 50, 48, 51, 54, 56, 53, 52, 55, 55, 53, 56, 48, 56};
    private static final byte[] DIGITS = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};
    private static final byte[] DIGIT_TENS = new byte[]{48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 49, 49, 49, 49, 49, 49, 49, 49, 49, 49, 50, 50, 50, 50, 50, 50, 50, 50, 50, 50, 51, 51, 51, 51, 51, 51, 51, 51, 51, 51, 52, 52, 52, 52, 52, 52, 52, 52, 52, 52, 53, 53, 53, 53, 53, 53, 53, 53, 53, 53, 54, 54, 54, 54, 54, 54, 54, 54, 54, 54, 55, 55, 55, 55, 55, 55, 55, 55, 55, 55, 56, 56, 56, 56, 56, 56, 56, 56, 56, 56, 57, 57, 57, 57, 57, 57, 57, 57, 57, 57};
    private static final byte[] DIGIT_ONES = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57};
    private static final double MAX_SAFE_INTEGER = Math.pow(2.0, 53.0) - 1.0;
    private static final double MIN_SAFE_INTEGER = -MAX_SAFE_INTEGER;
    private static final long MAX_SAFE_INTEGER_LONG = (long)Math.pow(2.0, 53.0) - 1L;
    private static final long MIN_SAFE_INTEGER_LONG = (long)MIN_SAFE_INTEGER;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private static final long[] LONG_LENGTH_TABLE = new long[]{0x1FFFFFFFFFFFF6L, 0x1FFFFFFFFFFFF6L, 0x1FFFFFFFFFFFF6L, 0x1FFFFFFFFFFFF6L, 13510798882111438L, 13510798882111438L, 13510798882111438L, 18014398509481484L, 18014398509481734L, 18014398509481734L, 22517998136849980L, 22517998136849980L, 22517998136851230L, 22517998136851230L, 27021597764210476L, 27021597764210476L, 27021597764216726L, 31525197391530972L, 31525197391530972L, 31525197391530972L, 36028797018651468L, 36028797018651468L, 36028797018651468L, 36028797018651468L, 40532396644771964L, 40532396644771964L, 40532396644771964L, 45035996258079960L, 45035996265892460L, 45035996265892460L, 49539595822950456L, 49539595822950456L, 49539595862012956L, 49539595862012956L, 54043195137820952L, 54043195137820952L, 54043195333133452L, 58546793202691448L, 58546793202691448L, 58546793202691448L, 63050385017561944L, 63050385017561944L, 63050385017561944L, 63050385017561944L, 67553945582432440L, 67553945582432440L, 67553945582432440L, 72057105756677936L, 72057349897302936L, 72057349897302936L, 76558752259048432L, 76558752259048432L, 76559972962173432L, 76559972962173432L, 81052586261418928L, 81052586261418928L, 81058689777043928L, 85507357763789424L, 85507357763789424L, 85507357763789424L, 89766816766159920L, 89766816766159920L, 89766816766159920L, 89766816766159920L};
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private static final long[] INT_LENGTH_TABLE = new long[]{0x100000000L, 0x1FFFFFFF6L, 0x1FFFFFFF6L, 0x1FFFFFFF6L, 12884901788L, 12884901788L, 12884901788L, 17179868184L, 17179868184L, 17179868184L, 21474826480L, 21474826480L, 21474826480L, 21474826480L, 25769703776L, 25769703776L, 25769703776L, 30063771072L, 30063771072L, 30063771072L, 34349738368L, 34349738368L, 34349738368L, 34349738368L, 38554705664L, 38554705664L, 38554705664L, 41949672960L, 41949672960L, 41949672960L, 0xA00000000L, 0xA00000000L};

    NumberConversion() {
    }

    static int parseInt(TruffleStringIterator it, int radix, BranchProfile errorProfile, TruffleStringIterator.NextNode nextNode) throws TruffleString.NumberFormatException {
        return (int)NumberConversion.parseNum(it, radix, errorProfile, Integer.MIN_VALUE, Integer.MAX_VALUE, nextNode);
    }

    static long parseLong(TruffleStringIterator it, int radix, BranchProfile errorProfile, TruffleStringIterator.NextNode nextNode) throws TruffleString.NumberFormatException {
        return NumberConversion.parseNum(it, radix, errorProfile, Long.MIN_VALUE, Long.MAX_VALUE, nextNode);
    }

    static int parseInt7Bit(AbstractTruffleString a, Object ptrA, int stride, int radix, BranchProfile errorProfile) throws TruffleString.NumberFormatException {
        return (int)NumberConversion.parseNum7Bit(a, ptrA, stride, radix, errorProfile, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    static long parseLong7Bit(AbstractTruffleString a, Object ptrA, int stride, int radix, BranchProfile errorProfile) throws TruffleString.NumberFormatException {
        return NumberConversion.parseNum7Bit(a, ptrA, stride, radix, errorProfile, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    static boolean isSafeInteger(long value2) {
        return MIN_SAFE_INTEGER_LONG <= value2 && value2 <= MAX_SAFE_INTEGER_LONG;
    }

    private static long parseNum(TruffleStringIterator it, int radix, BranchProfile errorProfile, long min2, long max2, TruffleStringIterator.NextNode nextNode) throws TruffleString.NumberFormatException {
        NumberConversion.checkArgs(it, radix, errorProfile);
        long result = 0L;
        boolean negative = false;
        long limit = -max2;
        if (it.hasNext()) {
            int firstChar = nextNode.execute(it);
            if (firstChar < 48) {
                if (firstChar == 45) {
                    negative = true;
                    limit = min2;
                } else if (firstChar != 43) {
                    errorProfile.enter();
                    throw NumberConversion.numberFormatException(it, TruffleString.NumberFormatException.Reason.INVALID_CODEPOINT);
                }
                if (!it.hasNext()) {
                    errorProfile.enter();
                    throw NumberConversion.numberFormatException(it, TruffleString.NumberFormatException.Reason.LONE_SIGN);
                }
            } else {
                int digit = Character.digit(firstChar, radix);
                if (digit < 0) {
                    errorProfile.enter();
                    throw NumberConversion.numberFormatException(it, TruffleString.NumberFormatException.Reason.INVALID_CODEPOINT);
                }
                assert (result >= limit + (long)digit);
                result -= (long)digit;
            }
            long multmin = limit / (long)radix;
            while (it.hasNext()) {
                int digit = Character.digit(nextNode.execute(it), radix);
                if (digit < 0) {
                    errorProfile.enter();
                    throw NumberConversion.numberFormatException(it, TruffleString.NumberFormatException.Reason.INVALID_CODEPOINT);
                }
                if (result < multmin) {
                    errorProfile.enter();
                    throw NumberConversion.numberFormatException(it, TruffleString.NumberFormatException.Reason.OVERFLOW);
                }
                if ((result *= (long)radix) < limit + (long)digit) {
                    errorProfile.enter();
                    throw NumberConversion.numberFormatException(it, TruffleString.NumberFormatException.Reason.OVERFLOW);
                }
                result -= (long)digit;
            }
        } else {
            errorProfile.enter();
            throw NumberConversion.numberFormatException(it, TruffleString.NumberFormatException.Reason.EMPTY);
        }
        return negative ? result : -result;
    }

    private static long parseNum7Bit(AbstractTruffleString a, Object arrayA, int stride, int radix, BranchProfile errorProfile, long min2, long max2) throws TruffleString.NumberFormatException {
        CompilerAsserts.partialEvaluationConstant(stride);
        assert (TStringGuards.is7Bit(TStringInternalNodes.GetCodeRangeNode.getUncached().execute(a)));
        NumberConversion.checkRadix(a, radix, errorProfile);
        NumberConversion.checkEmptyStr(a, errorProfile);
        long result = 0L;
        boolean negative = false;
        long limit = -max2;
        int i = 0;
        int firstChar = TStringOps.readValue(a, arrayA, stride, i);
        if (firstChar < 48) {
            if (firstChar == 45) {
                negative = true;
                limit = min2;
            } else if (firstChar != 43) {
                errorProfile.enter();
                throw NumberConversion.numberFormatException(a, i, TruffleString.NumberFormatException.Reason.INVALID_CODEPOINT);
            }
            if (a.length() == 1) {
                errorProfile.enter();
                throw NumberConversion.numberFormatException(a, i, TruffleString.NumberFormatException.Reason.LONE_SIGN);
            }
            ++i;
        }
        long multmin = limit / (long)radix;
        while (i < a.length()) {
            int c = TStringOps.readValue(a, arrayA, stride, i++);
            int digit = NumberConversion.parseDigit7Bit(a, i, radix, errorProfile, c);
            if (result < multmin) {
                errorProfile.enter();
                throw NumberConversion.numberFormatException(a, i, TruffleString.NumberFormatException.Reason.OVERFLOW);
            }
            if ((result *= (long)radix) < limit + (long)digit) {
                errorProfile.enter();
                throw NumberConversion.numberFormatException(a, i, TruffleString.NumberFormatException.Reason.OVERFLOW);
            }
            result -= (long)digit;
        }
        return negative ? result : -result;
    }

    private static int parseDigit7Bit(AbstractTruffleString a, int i, int radix, BranchProfile errorProfile, int c) throws TruffleString.NumberFormatException {
        int lc;
        if (48 <= c && c <= Math.min(radix - 1 + 48, 57)) {
            return c & 0xF;
        }
        if (radix > 10 && 97 <= (lc = c | 0x20) && lc <= radix - 11 + 97) {
            return lc - 87;
        }
        errorProfile.enter();
        throw NumberConversion.numberFormatException(a, i, TruffleString.NumberFormatException.Reason.INVALID_CODEPOINT);
    }

    private static void checkArgs(TruffleStringIterator it, int radix, BranchProfile errorProfile) throws TruffleString.NumberFormatException {
        assert (it != null);
        NumberConversion.checkRadix(it.a, radix, errorProfile);
    }

    private static void checkRadix(AbstractTruffleString a, int radix, BranchProfile errorProfile) throws TruffleString.NumberFormatException {
        if (radix < 2 || radix > 36) {
            errorProfile.enter();
            throw NumberConversion.numberFormatException(a, TruffleString.NumberFormatException.Reason.UNSUPPORTED_RADIX);
        }
    }

    private static void checkEmptyStr(AbstractTruffleString a, BranchProfile errorProfile) throws TruffleString.NumberFormatException {
        if (a.isEmpty()) {
            errorProfile.enter();
            throw NumberConversion.numberFormatException(a, TruffleString.NumberFormatException.Reason.EMPTY);
        }
    }

    @CompilerDirectives.TruffleBoundary
    static TruffleString.NumberFormatException numberFormatException(AbstractTruffleString a, TruffleString.NumberFormatException.Reason msg) {
        return new TruffleString.NumberFormatException(a, msg);
    }

    @CompilerDirectives.TruffleBoundary
    static TruffleString.NumberFormatException numberFormatException(TruffleStringIterator it, TruffleString.NumberFormatException.Reason msg) {
        return new TruffleString.NumberFormatException(it.a, it.getRawIndex(), 1, msg);
    }

    @CompilerDirectives.TruffleBoundary
    static TruffleString.NumberFormatException numberFormatException(AbstractTruffleString a, int regionOffset, TruffleString.NumberFormatException.Reason msg) {
        return new TruffleString.NumberFormatException(a, regionOffset, 1, msg);
    }

    @CompilerDirectives.TruffleBoundary
    static TruffleString.NumberFormatException numberFormatException(AbstractTruffleString a, int regionOffset, int regionLength, TruffleString.NumberFormatException.Reason msg) {
        return new TruffleString.NumberFormatException(a, regionOffset, regionLength, msg);
    }

    static byte[] longToString(long i, int length) {
        if (i == Long.MIN_VALUE) {
            return LONG_MIN_VALUE_BYTES;
        }
        byte[] buf = new byte[length];
        NumberConversion.writeLongToBytesIntl(i, length, buf, 0);
        return buf;
    }

    private static int floorLog2(long n) {
        return 0x3F ^ Long.numberOfLeadingZeros(n | 1L);
    }

    static int stringLengthInt(long intValue) {
        int sign;
        assert (Integer.MIN_VALUE <= intValue && intValue <= Integer.MAX_VALUE);
        long n = intValue;
        if (CompilerDirectives.injectBranchProbability(0.25, n < 0L)) {
            sign = 1;
            n = -n;
        } else {
            sign = 0;
        }
        int digits = (int)(n + INT_LENGTH_TABLE[NumberConversion.floorLog2(n)] >>> 32);
        return sign + digits;
    }

    static int stringLengthLong(long longValue) {
        int sign;
        if (longValue == Long.MIN_VALUE) {
            return LONG_MIN_VALUE_BYTES.length;
        }
        long n = longValue;
        if (CompilerDirectives.injectBranchProbability(0.25, n < 0L)) {
            sign = 1;
            n = -n;
        } else {
            sign = 0;
        }
        int bits = NumberConversion.floorLog2(n);
        int digits = (int)(LONG_LENGTH_TABLE[bits] + (n >> (bits >> 2)) >> 52);
        return sign + digits;
    }

    static void writeLongToBytes(Node location, long i, byte[] buf, int stride, int fromIndex, int length) {
        if (i == Long.MIN_VALUE) {
            TStringOps.arraycopyWithStride(location, LONG_MIN_VALUE_BYTES, 0, 0, 0, buf, 0, stride, fromIndex, LONG_MIN_VALUE_BYTES.length);
        } else {
            NumberConversion.writeLongToBytesIntl(i, fromIndex + length, buf, stride);
        }
    }

    private static void writeLongToBytesIntl(long value2, int index, byte[] buf, int stride) {
        int q2;
        int r;
        long i = value2;
        int bytePos = index;
        byte sign = 0;
        if (i < 0L) {
            sign = 45;
            i = -i;
        }
        while (i > Integer.MAX_VALUE) {
            long q = i / 100L;
            r = (int)(i - ((q << 6) + (q << 5) + (q << 2)));
            i = q;
            NumberConversion.writeInt(buf, stride, --bytePos, DIGIT_ONES[r]);
            NumberConversion.writeInt(buf, stride, --bytePos, DIGIT_TENS[r]);
        }
        int i2 = (int)i;
        while (i2 >= 65536) {
            q2 = i2 / 100;
            r = i2 - ((q2 << 6) + (q2 << 5) + (q2 << 2));
            i2 = q2;
            NumberConversion.writeInt(buf, stride, --bytePos, DIGIT_ONES[r]);
            NumberConversion.writeInt(buf, stride, --bytePos, DIGIT_TENS[r]);
        }
        do {
            q2 = i2 * 52429 >>> 19;
            r = i2 - ((q2 << 3) + (q2 << 1));
            NumberConversion.writeInt(buf, stride, --bytePos, DIGITS[r]);
        } while ((i2 = q2) != 0);
        if (sign != 0) {
            NumberConversion.writeInt(buf, stride, --bytePos, sign);
        }
    }

    static void writeIntToBytes(Node location, int i, byte[] buf, int stride, int fromIndex, int length) {
        if (i == Integer.MIN_VALUE) {
            TStringOps.arraycopyWithStride(location, INT_MIN_VALUE_BYTES, 0, 0, 0, buf, 0, stride, fromIndex, INT_MIN_VALUE_BYTES.length);
        } else {
            NumberConversion.writeIntToBytesIntl(i, fromIndex + length, buf, stride);
        }
    }

    private static void writeIntToBytesIntl(int value2, int index, byte[] buf, int stride) {
        int r;
        int q;
        int i = value2;
        int bytePos = index;
        byte sign = 0;
        if (i < 0) {
            sign = 45;
            i = -i;
        }
        while (i >= 65536) {
            q = i / 100;
            r = i - ((q << 6) + (q << 5) + (q << 2));
            i = q;
            NumberConversion.writeInt(buf, stride, --bytePos, DIGIT_ONES[r]);
            NumberConversion.writeInt(buf, stride, --bytePos, DIGIT_TENS[r]);
        }
        do {
            q = i * 52429 >>> 19;
            r = i - ((q << 3) + (q << 1));
            NumberConversion.writeInt(buf, stride, --bytePos, DIGITS[r]);
        } while ((i = q) != 0);
        if (sign != 0) {
            NumberConversion.writeInt(buf, stride, --bytePos, sign);
        }
    }

    private static void writeInt(byte[] buf, int stride, int bytePos, byte value2) {
        TStringOps.writeToByteArray(buf, stride, bytePos, value2);
    }
}

