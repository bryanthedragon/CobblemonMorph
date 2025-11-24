
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.TSCodeRange;
import com.oracle.truffle.api.strings.TStringInternalNodes;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import java.nio.ByteOrder;

final class TStringGuards {
    TStringGuards() {
    }

    static boolean isEmpty(AbstractTruffleString a) {
        return a.isEmpty();
    }

    static boolean is7Bit(int codeRange) {
        return TSCodeRange.is7Bit(codeRange);
    }

    static boolean is8Bit(int codeRange) {
        return TSCodeRange.is8Bit(codeRange);
    }

    static boolean is7Or8Bit(int codeRange) {
        return TSCodeRange.is7Or8Bit(codeRange);
    }

    static boolean isUpTo16Bit(int codeRange) {
        return TSCodeRange.isUpTo16Bit(codeRange);
    }

    static boolean is16Bit(int codeRange) {
        return TSCodeRange.is16Bit(codeRange);
    }

    static boolean isValidFixedWidth(int codeRange) {
        return TSCodeRange.isValidFixedWidth(codeRange);
    }

    static boolean isUpToValidFixedWidth(int codeRange) {
        return TSCodeRange.isUpToValidFixedWidth(codeRange);
    }

    static boolean isBrokenFixedWidth(int codeRange) {
        return TSCodeRange.isBrokenFixedWidth(codeRange);
    }

    static boolean isValidMultiByte(int codeRange) {
        return TSCodeRange.isValidMultiByte(codeRange);
    }

    static boolean isBrokenMultiByte(int codeRange) {
        return TSCodeRange.isBrokenMultiByte(codeRange);
    }

    static boolean isBrokenMultiByte(TruffleStringBuilder sb) {
        return TSCodeRange.isBrokenMultiByte(sb.getCodeRange());
    }

    static boolean isUnknown(int codeRange) {
        return TSCodeRange.isUnknown(codeRange);
    }

    static boolean isBrokenMultiByteOrUnknown(int codeRange) {
        return TSCodeRange.isBrokenMultiByteOrUnknown(codeRange);
    }

    public static boolean isValidBrokenOrUnknownMultiByte(int codeRange) {
        return TSCodeRange.isValidBrokenOrUnknownMultiByte(codeRange);
    }

    static boolean isFixedWidth(int codeRange) {
        return TSCodeRange.isFixedWidth(codeRange);
    }

    static boolean isFixedWidth(int codeRangeA, int codeRangeB) {
        return TStringGuards.isFixedWidth(codeRangeA) && TStringGuards.isFixedWidth(codeRangeB);
    }

    static boolean indexOfCannotMatch(int codeRangeA, AbstractTruffleString b, int codeRangeB, int regionLength, TStringInternalNodes.GetCodePointLengthNode getCodePointLengthNodeB) {
        return regionLength < getCodePointLengthNodeB.execute(b) || TStringGuards.codeRangesCannotMatch(codeRangeA, codeRangeB, null);
    }

    static boolean indexOfCannotMatch(int codeRangeA, AbstractTruffleString b, int codeRangeB, byte[] mask, int regionLength) {
        return regionLength < b.length() || TStringGuards.codeRangesCannotMatch(codeRangeA, codeRangeB, mask);
    }

    private static boolean codeRangesCannotMatch(int codeRangeA, int codeRangeB, byte[] mask) {
        return mask == null && !TSCodeRange.isBrokenMultiByteOrUnknown(codeRangeA) && !TSCodeRange.isBrokenMultiByteOrUnknown(codeRangeB) && TSCodeRange.isMoreRestrictiveThan(codeRangeA, codeRangeB);
    }

    static boolean isAscii(int enc) {
        return enc == TruffleString.Encoding.US_ASCII.id;
    }

    static boolean isAscii(TruffleString.Encoding enc) {
        return enc == TruffleString.Encoding.US_ASCII;
    }

    static boolean isBytes(int enc) {
        return enc == TruffleString.Encoding.BYTES.id;
    }

    static boolean isBytes(TruffleString.Encoding enc) {
        return enc == TruffleString.Encoding.BYTES;
    }

    static boolean isLatin1(int enc) {
        return enc == TruffleString.Encoding.ISO_8859_1.id;
    }

    static boolean isLatin1(TruffleString.Encoding enc) {
        return enc == TruffleString.Encoding.ISO_8859_1;
    }

    static boolean isAsciiBytesOrLatin1(int enc) {
        return TStringGuards.isAscii(enc) || TStringGuards.isLatin1(enc) || TStringGuards.isBytes(enc);
    }

    static boolean isAsciiBytesOrLatin1(TruffleString.Encoding enc) {
        return TStringGuards.isAscii(enc) || TStringGuards.isLatin1(enc) || TStringGuards.isBytes(enc);
    }

    static boolean isUTF8(int enc) {
        return enc == TruffleString.Encoding.UTF_8.id;
    }

    static boolean isUTF8(TruffleString.Encoding enc) {
        return enc == TruffleString.Encoding.UTF_8;
    }

    static boolean isUTF16(int enc) {
        return enc == TruffleString.Encoding.UTF_16.id;
    }

    static boolean isUTF16(TruffleString.Encoding enc) {
        return enc == TruffleString.Encoding.UTF_16;
    }

    static boolean isUTF16(TruffleStringBuilder sb) {
        return TStringGuards.isUTF16(sb.getEncoding());
    }

    static boolean isUTF32(int enc) {
        return enc == TruffleString.Encoding.UTF_32.id;
    }

    static boolean isUTF32(TruffleString.Encoding enc) {
        return enc == TruffleString.Encoding.UTF_32;
    }

    static boolean isUTF16Or32(TruffleString.Encoding enc) {
        assert (TruffleString.Encoding.UTF_32.id == 0);
        assert (TruffleString.Encoding.UTF_16.id == 1);
        return enc.id <= 1;
    }

    static boolean identical(Object a, Object b) {
        return a == b;
    }

    static boolean isSupportedEncoding(int encoding) {
        return TruffleString.Encoding.isSupported(encoding);
    }

    static boolean isSupportedEncoding(TruffleString.Encoding encoding) {
        return encoding.isSupported();
    }

    static boolean isUnsupportedEncoding(int encoding) {
        return TruffleString.Encoding.isUnsupported(encoding);
    }

    static boolean isUnsupportedEncoding(TruffleString.Encoding encoding) {
        return encoding.isUnsupported();
    }

    static int stride(AbstractTruffleString a) {
        return a.stride();
    }

    static int length(AbstractTruffleString a) {
        return a.length();
    }

    static boolean isStride0(AbstractTruffleString a) {
        return a.stride() == 0;
    }

    static boolean isStride1(AbstractTruffleString a) {
        return a.stride() == 1;
    }

    static boolean isStride2(AbstractTruffleString a) {
        return a.stride() == 2;
    }

    static boolean isStride0(TruffleStringBuilder sb) {
        return sb.getStride() == 0;
    }

    static boolean isStride1(TruffleStringBuilder sb) {
        return sb.getStride() == 1;
    }

    static boolean isStride2(TruffleStringBuilder sb) {
        return sb.getStride() == 2;
    }

    static boolean is7BitCompatible(TruffleString.Encoding encoding) {
        return encoding.is7BitCompatible();
    }

    static boolean is8BitCompatible(TruffleString.Encoding encoding) {
        return encoding.is8BitCompatible();
    }

    static boolean isBestEffort(TruffleString.ErrorHandling errorHandling) {
        return errorHandling == TruffleString.ErrorHandling.BEST_EFFORT;
    }

    static boolean isReturnNegative(TruffleString.ErrorHandling errorHandling) {
        return errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE;
    }

    static boolean bigEndian() {
        return ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }

    static boolean littleEndian() {
        return ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
    }

    static boolean isInlinedJavaString(Object string) {
        return false;
    }
}

