
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.AbstractTruffleString;
import com.oracle.truffle.api.strings.TSCodeRange;
import com.oracle.truffle.api.strings.TStringGuards;
import com.oracle.truffle.api.strings.TruffleString;

final class TStringConstants {
    static final int MAX_ARRAY_SIZE = 0x7FFFFFF7;
    static final int MAX_ARRAY_SIZE_S1 = 0x3FFFFFFB;
    static final int MAX_ARRAY_SIZE_S2 = 0x1FFFFFFD;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    static final byte[] EMPTY_BYTES;
    @CompilerDirectives.CompilationFinal(dimensions=2)
    private static final byte[][] SINGLE_BYTE_ARRAYS;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private static final byte[] INFINITY_BYTES;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private static final byte[] NaN_BYTES;
    private static final TruffleString INFINITY;
    private static final TruffleString NaN;
    @CompilerDirectives.CompilationFinal(dimensions=2)
    private static final TruffleString[][] SINGLE_BYTE;
    static final int LAZY_CONCAT_MIN_LENGTH = 40;

    TStringConstants() {
    }

    private static int nonAsciiCodeRange(TruffleString.Encoding encoding) {
        if (TStringGuards.isAsciiBytesOrLatin1(encoding)) {
            return TSCodeRange.asciiLatinBytesNonAsciiCodeRange(encoding);
        }
        if (TStringGuards.isUTF8(encoding)) {
            return TSCodeRange.getBrokenMultiByte();
        }
        return TSCodeRange.get8Bit();
    }

    static TruffleString getInfinity(int encoding) {
        if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS) {
            return TStringConstants.createAscii(INFINITY_BYTES, TruffleString.Encoding.get(encoding));
        }
        return INFINITY;
    }

    static TruffleString getNaN(int encoding) {
        if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS) {
            return TStringConstants.createAscii(NaN_BYTES, TruffleString.Encoding.get(encoding));
        }
        return NaN;
    }

    static TruffleString getSingleByteAscii(TruffleString.Encoding encoding, int value2) {
        if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS) {
            return TStringConstants.createAscii(SINGLE_BYTE_ARRAYS[value2], encoding);
        }
        if (TStringGuards.isUnsupportedEncoding(encoding)) {
            return SINGLE_BYTE[TruffleString.Encoding.US_ASCII.id][value2];
        }
        return SINGLE_BYTE[encoding.id][value2];
    }

    static TruffleString getSingleByte(TruffleString.Encoding encoding, int value2) {
        if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS && value2 <= 127) {
            return TStringConstants.createAscii(SINGLE_BYTE_ARRAYS[value2], encoding);
        }
        return SINGLE_BYTE[encoding.id][value2];
    }

    private static TruffleString createAscii(byte[] array, TruffleString.Encoding encoding) {
        return TruffleString.createFromByteArray(array, array.length, 0, encoding, array.length, TSCodeRange.getAsciiCodeRange(encoding), true);
    }

    static void truffleSafePointPoll(Node location, int loopCount) {
        if ((loopCount & 0xFFFFF) == 0) {
            TruffleSafepoint.poll(location);
            LoopNode.reportLoopCount(location, 0x100000);
        }
    }

    static {
        int j;
        int i;
        EMPTY_BYTES = new byte[0];
        SINGLE_BYTE_ARRAYS = new byte[256][1];
        INFINITY_BYTES = new byte[]{73, 110, 102, 105, 110, 105, 116, 121};
        NaN_BYTES = new byte[]{78, 97, 78};
        INFINITY = TruffleString.createConstant(INFINITY_BYTES, INFINITY_BYTES.length, 0, TruffleString.Encoding.US_ASCII, INFINITY_BYTES.length, TSCodeRange.get7Bit());
        NaN = TruffleString.createConstant(NaN_BYTES, NaN_BYTES.length, 0, TruffleString.Encoding.US_ASCII, NaN_BYTES.length, TSCodeRange.get7Bit());
        SINGLE_BYTE = new TruffleString[6][];
        for (i = 0; i < 6; ++i) {
            TStringConstants.SINGLE_BYTE[i] = new TruffleString[256];
        }
        for (i = 0; i < 128; ++i) {
            TStringConstants.SINGLE_BYTE_ARRAYS[i][0] = (byte)i;
            TStringConstants.SINGLE_BYTE[0][i] = TruffleString.createConstant(SINGLE_BYTE_ARRAYS[i], 1, 0, TruffleString.Encoding.US_ASCII, 1, TSCodeRange.get7Bit());
            for (j = 1; j < 6; ++j) {
                TStringConstants.SINGLE_BYTE[j][i] = SINGLE_BYTE[0][i];
            }
        }
        for (i = 128; i < 256; ++i) {
            TStringConstants.SINGLE_BYTE_ARRAYS[i][0] = (byte)i;
            for (j = 0; j < 6; ++j) {
                TStringConstants.SINGLE_BYTE[j][i] = TruffleString.createConstant(SINGLE_BYTE_ARRAYS[i], 1, 0, TruffleString.Encoding.get(j), 1, TStringConstants.nonAsciiCodeRange(TruffleString.Encoding.get(j)));
            }
        }
    }
}

