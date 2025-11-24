
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.InternalErrors;
import com.oracle.truffle.api.strings.TStringConstants;
import com.oracle.truffle.api.strings.TStringOps;
import com.oracle.truffle.api.strings.TruffleString;

final class Encodings {
    static final int SUPPORTED_ENCODINGS_MIN_NUM = 0;
    static final int SUPPORTED_ENCODINGS_MAX_NUM = 6;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private static final int[] UTF_8_MIN_CODEPOINT = new int[]{0, 0, 128, 2048, 65536};

    Encodings() {
    }

    static boolean isUTF16Surrogate(int c) {
        return c >> 11 == 27;
    }

    static boolean isUTF16HighSurrogate(int c) {
        return c >> 10 == 54;
    }

    static boolean isUTF16LowSurrogate(int c) {
        return c >> 10 == 55;
    }

    static boolean isUTF8ContinuationByte(int b) {
        return (b & 0xC0) == 128;
    }

    static int invalidCodepoint() {
        return 65533;
    }

    static int utf8CodePointLength(int firstByte) {
        return Integer.numberOfLeadingZeros(~(firstByte << 24));
    }

    static int utf8EncodedSize(int codepoint) {
        if (codepoint < 128) {
            return 1;
        }
        if (codepoint < 2048) {
            return 2;
        }
        if (codepoint < 65536) {
            return 3;
        }
        return 4;
    }

    private static boolean isUTF8ContinuationByte(AbstractTruffleString a, Object arrayA, int index) {
        return Encodings.isUTF8ContinuationByte(TStringOps.readS0(a, arrayA, index));
    }

    static byte[] utf8Encode(int codepoint) {
        int n = Encodings.utf8EncodedSize(codepoint);
        byte[] ret = new byte[n];
        if (n == 1) {
            ret[0] = (byte)codepoint;
            return ret;
        }
        Encodings.utf8Encode(codepoint, n, ret, 0);
        return ret;
    }

    static byte[] utf8EncodeNonAscii(int codepoint, int encodedSize) {
        assert (encodedSize == Encodings.utf8EncodedSize(codepoint));
        assert (encodedSize > 1);
        byte[] ret = new byte[encodedSize];
        Encodings.utf8Encode(codepoint, encodedSize, ret, 0);
        return ret;
    }

    static void utf8Encode(int codepoint, byte[] buffer, int index, int length) {
        assert (length == Encodings.utf8EncodedSize(codepoint));
        if (length == 1) {
            buffer[index] = (byte)codepoint;
        } else {
            Encodings.utf8Encode(codepoint, length, buffer, index);
        }
    }

    private static void utf8Encode(int codepoint, int encodedLength, byte[] buffer, int index) {
        assert (index >= 0);
        assert (2 <= encodedLength && encodedLength <= 4);
        int i = index + encodedLength;
        int c = codepoint;
        switch (encodedLength) {
            case 4: {
                buffer[--i] = (byte)(0x80 | c & 0x3F);
                c >>>= 6;
            }
            case 3: {
                buffer[--i] = (byte)(0x80 | c & 0x3F);
                c >>>= 6;
            }
        }
        buffer[--i] = (byte)(0x80 | c & 0x3F);
        buffer[--i] = (byte)(3840 >>> encodedLength | (c >>>= 6));
    }

    static int utf8CodePointToByteIndex(Node location, AbstractTruffleString a, Object arrayA, int codePointIndex) {
        int iCP = 0;
        int iBytes = 0;
        while (CompilerDirectives.injectBranchProbability(0.75, iBytes < a.length())) {
            if ((TStringOps.readS0(a, arrayA, iBytes) & 0xC0) != 128) {
                if (CompilerDirectives.injectBranchProbability(0.01, iCP >= codePointIndex)) break;
                ++iCP;
            }
            TStringConstants.truffleSafePointPoll(location, ++iBytes);
        }
        if (iBytes >= a.length()) {
            throw InternalErrors.indexOutOfBounds();
        }
        return iBytes;
    }

    static int utf8DecodeValid(AbstractTruffleString a, Object arrayA, int i) {
        int b = TStringOps.readS0(a, arrayA, i);
        if (b < 128) {
            return b;
        }
        int nBytes = Encodings.utf8CodePointLength(b);
        int codepoint = b & 255 >>> nBytes;
        assert (1 < nBytes && nBytes < 5) : nBytes;
        assert (i + nBytes <= a.length());
        int j = i + 1;
        switch (nBytes) {
            case 4: {
                assert (Encodings.isUTF8ContinuationByte(a, arrayA, j));
                codepoint = codepoint << 6 | TStringOps.readS0(a, arrayA, j++) & 0x3F;
            }
            case 3: {
                assert (Encodings.isUTF8ContinuationByte(a, arrayA, j));
                codepoint = codepoint << 6 | TStringOps.readS0(a, arrayA, j++) & 0x3F;
            }
        }
        assert (Encodings.isUTF8ContinuationByte(a, arrayA, j));
        codepoint = codepoint << 6 | TStringOps.readS0(a, arrayA, j) & 0x3F;
        return codepoint;
    }

    static int utf8DecodeBroken(AbstractTruffleString a, Object arrayA, int i, TruffleString.ErrorHandling errorHandling) {
        int b = TStringOps.readS0(a, arrayA, i);
        if (b < 128) {
            return b;
        }
        int nBytes = Encodings.utf8CodePointLength(b);
        int codepoint = b & 255 >>> nBytes;
        int j = i + 1;
        switch (nBytes) {
            case 4: {
                if (j >= a.length() || !Encodings.isUTF8ContinuationByte(a, arrayA, j)) {
                    return Encodings.invalidCodepointReturnValue(errorHandling);
                }
                codepoint = codepoint << 6 | TStringOps.readS0(a, arrayA, j++) & 0x3F;
            }
            case 3: {
                if (j >= a.length() || !Encodings.isUTF8ContinuationByte(a, arrayA, j)) {
                    return Encodings.invalidCodepointReturnValue(errorHandling);
                }
                codepoint = codepoint << 6 | TStringOps.readS0(a, arrayA, j++) & 0x3F;
            }
            case 2: {
                if (j >= a.length() || !Encodings.isUTF8ContinuationByte(a, arrayA, j)) {
                    return Encodings.invalidCodepointReturnValue(errorHandling);
                }
                codepoint = codepoint << 6 | TStringOps.readS0(a, arrayA, j) & 0x3F;
                break;
            }
            default: {
                return Encodings.invalidCodepointReturnValue(errorHandling);
            }
        }
        if (Encodings.utf8IsInvalidCodePoint(codepoint, nBytes)) {
            return Encodings.invalidCodepointReturnValue(errorHandling);
        }
        return codepoint;
    }

    static int utf8GetCodePointLength(AbstractTruffleString a, Object arrayA, int i, TruffleString.ErrorHandling errorHandling) {
        return Encodings.utf8GetCodePointLength(arrayA, a.offset(), a.length(), i, errorHandling);
    }

    static int utf8GetCodePointLength(Object arrayA, int offset, int length, int i, TruffleString.ErrorHandling errorHandling) {
        int j;
        int b = TStringOps.readS0(arrayA, offset, length, i);
        if (b < 128) {
            return 1;
        }
        int nBytes = Encodings.utf8CodePointLength(b);
        int codepoint = b & 255 >>> nBytes;
        if (i + nBytes > length) {
            if (errorHandling == TruffleString.ErrorHandling.BEST_EFFORT) {
                return 1;
            }
            assert (errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE);
            if (nBytes < 2 || nBytes > 4) {
                return -1;
            }
            if (j == length && codepoint == 0) {
                return nBytes == 2 ? -1 : -nBytes;
            }
            for (j = i + 1; j < i + nBytes; ++j) {
                codepoint <<= 6;
                if (j >= length) continue;
                int continuationByte = TStringOps.readS0(arrayA, offset, length, j);
                if (!Encodings.isUTF8ContinuationByte(continuationByte)) {
                    return -1;
                }
                codepoint |= continuationByte & 0x3F;
            }
            return Encodings.utf8IsInvalidCodePoint(codepoint, nBytes) ? -1 : length - (i + nBytes) - 1;
        }
        switch (nBytes) {
            case 4: {
                int continuationByte = TStringOps.readS0(arrayA, offset, length, j++);
                if (!Encodings.isUTF8ContinuationByte(continuationByte)) {
                    return Encodings.invalidCodepointReturnValue(1, errorHandling);
                }
                codepoint = codepoint << 6 | continuationByte & 0x3F;
            }
            case 3: {
                int continuationByte = TStringOps.readS0(arrayA, offset, length, j++);
                if (!Encodings.isUTF8ContinuationByte(continuationByte)) {
                    return Encodings.invalidCodepointReturnValue(1, errorHandling);
                }
                codepoint = codepoint << 6 | continuationByte & 0x3F;
            }
            case 2: {
                int continuationByte = TStringOps.readS0(arrayA, offset, length, j);
                if (!Encodings.isUTF8ContinuationByte(continuationByte)) {
                    return Encodings.invalidCodepointReturnValue(1, errorHandling);
                }
                codepoint = codepoint << 6 | continuationByte & 0x3F;
                break;
            }
            default: {
                return Encodings.invalidCodepointReturnValue(1, errorHandling);
            }
        }
        if (Encodings.utf8IsInvalidCodePoint(codepoint, nBytes)) {
            return Encodings.invalidCodepointReturnValue(1, errorHandling);
        }
        return nBytes;
    }

    static boolean utf8IsInvalidCodePoint(int codepoint, int nBytes) {
        return Encodings.isUTF16Surrogate(codepoint) || codepoint < UTF_8_MIN_CODEPOINT[nBytes] || codepoint > 0x10FFFF;
    }

    static int invalidCodepointReturnValue(TruffleString.ErrorHandling errorHandling) {
        return Encodings.invalidCodepointReturnValue(Encodings.invalidCodepoint(), errorHandling);
    }

    static int invalidCodepointReturnValue(int bestEffortValue, TruffleString.ErrorHandling errorHandling) {
        if (errorHandling == TruffleString.ErrorHandling.BEST_EFFORT) {
            return bestEffortValue;
        }
        assert (errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE);
        return -1;
    }

    static int utf16EncodedSize(int codepoint) {
        return codepoint < 65536 ? 1 : 2;
    }

    static int utf16BrokenGetCodePointByteLength(AbstractTruffleString a, Object arrayA, int i, TruffleString.ErrorHandling errorHandling) {
        return Encodings.utf16BrokenGetCodePointByteLength(arrayA, a.offset(), a.length(), i, errorHandling);
    }

    static int utf16BrokenGetCodePointByteLength(Object arrayA, int offset, int length, int i, TruffleString.ErrorHandling errorHandling) {
        char c = TStringOps.readS1(arrayA, offset, length, i);
        if (errorHandling == TruffleString.ErrorHandling.BEST_EFFORT) {
            return Encodings.isUTF16HighSurrogate(c) && i + 1 < length && Encodings.isUTF16LowSurrogate(TStringOps.readS1(arrayA, offset, length, i + 1)) ? 4 : 2;
        }
        assert (errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE);
        if (Encodings.isUTF16Surrogate(c)) {
            if (Encodings.isUTF16HighSurrogate(c)) {
                if (i + 1 == length) {
                    return -3;
                }
                if (Encodings.isUTF16LowSurrogate(TStringOps.readS1(arrayA, offset, length, i + 1))) {
                    return 4;
                }
            }
            return -1;
        }
        return 2;
    }

    static int utf16Encode(int codepoint, byte[] bytes, int index) {
        if (codepoint < 65536) {
            TStringOps.writeToByteArray(bytes, 1, index, codepoint);
            return 1;
        }
        Encodings.utf16EncodeSurrogatePair(codepoint, bytes, index);
        return 2;
    }

    static void utf16EncodeSurrogatePair(int codepoint, byte[] bytes, int index) {
        assert (codepoint > 65535);
        char c1 = Character.highSurrogate(codepoint);
        char c2 = Character.lowSurrogate(codepoint);
        TStringOps.writeToByteArray(bytes, 1, index, c1);
        TStringOps.writeToByteArray(bytes, 1, index + 1, c2);
    }

    static int utf16ValidCodePointToCharIndex(Node location, AbstractTruffleString a, Object arrayA, int codePointIndex) {
        int iCP = 0;
        int iChars = 0;
        while (CompilerDirectives.injectBranchProbability(0.75, iChars < a.length())) {
            if ((TStringOps.readS1(a, arrayA, iChars) & 0xFC00) != 56320) {
                if (CompilerDirectives.injectBranchProbability(0.01, iCP >= codePointIndex)) break;
                ++iCP;
            }
            TStringConstants.truffleSafePointPoll(location, ++iChars);
        }
        if (iChars >= a.length()) {
            throw InternalErrors.indexOutOfBounds();
        }
        return iChars;
    }

    static int utf16BrokenCodePointToCharIndex(Node location, AbstractTruffleString a, Object arrayA, int codePointIndex) {
        int iCP = 0;
        int iChars = 0;
        while (iCP < codePointIndex) {
            if (Encodings.isUTF16HighSurrogate(TStringOps.readS1(a, arrayA, iChars)) && iChars + 1 < a.length() && Encodings.isUTF16LowSurrogate(TStringOps.readS1(a, arrayA, iChars + 1))) {
                ++iChars;
            }
            ++iChars;
            TStringConstants.truffleSafePointPoll(location, ++iCP);
        }
        if (iChars >= a.length()) {
            throw InternalErrors.indexOutOfBounds();
        }
        return iChars;
    }

    static int utf16DecodeValid(AbstractTruffleString a, Object arrayA, int i) {
        char c = TStringOps.readS1(a, arrayA, i);
        if (Encodings.isUTF16HighSurrogate(c)) {
            assert (i + 1 < a.length());
            assert (Encodings.isUTF16LowSurrogate(TStringOps.readS1(a, arrayA, i + 1)));
            return Character.toCodePoint(c, TStringOps.readS1(a, arrayA, i + 1));
        }
        return c;
    }

    static int utf16DecodeBroken(AbstractTruffleString a, Object arrayA, int i, TruffleString.ErrorHandling errorHandling) {
        char c = TStringOps.readS1(a, arrayA, i);
        if (errorHandling == TruffleString.ErrorHandling.BEST_EFFORT) {
            char c2;
            if (Encodings.isUTF16HighSurrogate(c) && i + 1 < a.length() && Encodings.isUTF16LowSurrogate(c2 = TStringOps.readS1(a, arrayA, i + 1))) {
                return Character.toCodePoint(c, c2);
            }
        } else {
            assert (errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE);
            if (Encodings.isUTF16Surrogate(c)) {
                if (Encodings.isUTF16LowSurrogate(c) || i + 1 >= a.length()) {
                    return -1;
                }
                char c2 = TStringOps.readS1(a, arrayA, i + 1);
                if (!Encodings.isUTF16LowSurrogate(c2)) {
                    return -1;
                }
                return Character.toCodePoint(c, c2);
            }
        }
        return c;
    }

    static boolean isValidUnicodeCodepoint(int codepoint) {
        return !Encodings.isUTF16Surrogate(codepoint) && Integer.toUnsignedLong(codepoint) <= 0x10FFFFL;
    }
}

