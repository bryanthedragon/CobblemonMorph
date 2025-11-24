
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.JCodings;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TruffleString;

final class TSCodeRange {
    private static final int CR_7BIT = 0;
    private static final int CR_8BIT = 1;
    private static final int CR_16BIT = 2;
    private static final int CR_VALID_FIXED_WIDTH = 3;
    private static final int CR_BROKEN_FIXED_WIDTH = 4;
    private static final int CR_VALID_MULTIBYTE = 5;
    private static final int CR_BROKEN_MULTIBYTE = 6;
    private static final int CR_UNKNOWN = 7;

    TSCodeRange() {
    }

    private static int maxCodePoint(int codeRange) {
        return codeRange == 0 ? 127 : (codeRange == 1 ? 255 : (codeRange == 2 ? 65535 : 0x10FFFF));
    }

    static boolean isCodeRange(int codeRange) {
        return 0 <= codeRange && codeRange <= 7;
    }

    static int get7Bit() {
        return 0;
    }

    static int get8Bit() {
        return 1;
    }

    static int get16Bit() {
        return 2;
    }

    static int getValidFixedWidth() {
        return 3;
    }

    static int getBrokenFixedWidth() {
        return 4;
    }

    static int getValidMultiByte() {
        return 5;
    }

    static int getBrokenMultiByte() {
        return 6;
    }

    static int getUnknown() {
        return 7;
    }

    static boolean isUnknown(int codeRange) {
        return codeRange == 7;
    }

    static boolean is7Bit(int codeRange) {
        return codeRange == 0;
    }

    static boolean is7Or8Bit(int codeRange) {
        return codeRange <= 1;
    }

    static boolean isUpTo16Bit(int codeRange) {
        return codeRange <= 2;
    }

    static boolean is8Bit(int codeRange) {
        return codeRange == 1;
    }

    static boolean is16Bit(int codeRange) {
        return codeRange == 2;
    }

    static boolean isValidFixedWidth(int codeRange) {
        return codeRange == 3;
    }

    static boolean isBrokenFixedWidth(int codeRange) {
        return codeRange == 4;
    }

    static boolean isValidMultiByte(int codeRange) {
        return codeRange == 5;
    }

    static boolean isBrokenMultiByte(int codeRange) {
        return codeRange == 6;
    }

    static boolean isBrokenMultiByteOrUnknown(int codeRange) {
        return TSCodeRange.isBrokenMultiByte(codeRange) || TSCodeRange.isUnknown(codeRange);
    }

    static boolean isValidBrokenOrUnknownMultiByte(int codeRange) {
        return codeRange >= 5 && codeRange <= 7;
    }

    static boolean isKnown(int codeRange) {
        return !TSCodeRange.isUnknown(codeRange);
    }

    static boolean isKnown(int aCodeRange, int bCodeRange) {
        return TSCodeRange.isKnown(aCodeRange) && TSCodeRange.isKnown(bCodeRange);
    }

    static int commonCodeRange(int a, int b) {
        return Math.max(a, b);
    }

    static boolean isMoreRestrictiveThan(int a, int b) {
        return a < b;
    }

    static boolean isMoreRestrictiveOrEqual(int a, int b) {
        return a <= b;
    }

    static boolean isMoreGeneralThan(int a, int b) {
        return a > b;
    }

    static boolean isUpToValidFixedWidth(int codeRange) {
        return codeRange <= 3;
    }

    static boolean isFixedWidth(int codeRange) {
        return codeRange <= 4;
    }

    static boolean isInCodeRange(int codepoint, int codeRange) {
        return Integer.toUnsignedLong(codepoint) <= (long)TSCodeRange.maxCodePoint(codeRange);
    }

    static int toStrideUTF16(int codeRange) {
        return codeRange <= TSCodeRange.get8Bit() ? 0 : 1;
    }

    static int toStrideUTF32(int codeRange) {
        assert (TSCodeRange.isFixedWidth(codeRange));
        if (codeRange > 2) {
            return 2;
        }
        if (codeRange == 2) {
            return 1;
        }
        return 0;
    }

    static int asciiLatinBytesNonAsciiCodeRange(int encoding) {
        if (TStringGuards.isAscii(encoding)) {
            return TSCodeRange.getBrokenFixedWidth();
        }
        if (TStringGuards.isLatin1(encoding)) {
            return TSCodeRange.get8Bit();
        }
        if (TStringGuards.isBytes(encoding)) {
            return TSCodeRange.getValidFixedWidth();
        }
        return TSCodeRange.getUnknown();
    }

    static int asciiLatinBytesNonAsciiCodeRange(TruffleString.Encoding encoding) {
        if (TStringGuards.isAscii(encoding)) {
            return TSCodeRange.getBrokenFixedWidth();
        }
        if (TStringGuards.isLatin1(encoding)) {
            return TSCodeRange.get8Bit();
        }
        if (TStringGuards.isBytes(encoding)) {
            return TSCodeRange.getValidFixedWidth();
        }
        return TSCodeRange.getUnknown();
    }

    static int getAsciiCodeRange(TruffleString.Encoding encoding) {
        if (TStringGuards.is7BitCompatible(encoding)) {
            return TSCodeRange.get7Bit();
        }
        if (JCodings.getInstance().isSingleByte(encoding.jCoding)) {
            return TSCodeRange.getValidFixedWidth();
        }
        return TSCodeRange.getValidMultiByte();
    }

    private static void staticAssertions() {
        assert (TSCodeRange.toStrideUTF32(0) == 0);
        assert (TSCodeRange.toStrideUTF32(1) == 0);
        assert (TSCodeRange.toStrideUTF32(2) == 1);
        assert (TSCodeRange.toStrideUTF32(3) == 2);
        assert (TSCodeRange.toStrideUTF32(4) == 2);
        assert (TSCodeRange.maxCodePoint(0) == 127);
        assert (TSCodeRange.maxCodePoint(1) == 255);
        assert (TSCodeRange.maxCodePoint(2) == 65535);
        assert (TSCodeRange.maxCodePoint(3) == 0x10FFFF);
        assert (TSCodeRange.maxCodePoint(4) == 0x10FFFF);
        assert (TSCodeRange.maxCodePoint(5) == 0x10FFFF);
        assert (TSCodeRange.maxCodePoint(6) == 0x10FFFF);
        assert (TSCodeRange.maxCodePoint(7) == 0x10FFFF);
    }

    @CompilerDirectives.TruffleBoundary
    static String toString(int codeRange) {
        switch (codeRange) {
            case 0: {
                return "7Bit";
            }
            case 1: {
                return "8Bit";
            }
            case 2: {
                return "16Bit";
            }
            case 3: {
                return "ValidFixedWidth";
            }
            case 4: {
                return "BrokenFixedWidth";
            }
            case 5: {
                return "ValidMultiByte";
            }
            case 6: {
                return "BrokenMultiByte";
            }
            case 7: {
                return "Unknown";
            }
        }
        throw CompilerDirectives.shouldNotReachHere();
    }

    static {
        TSCodeRange.staticAssertions();
    }
}

