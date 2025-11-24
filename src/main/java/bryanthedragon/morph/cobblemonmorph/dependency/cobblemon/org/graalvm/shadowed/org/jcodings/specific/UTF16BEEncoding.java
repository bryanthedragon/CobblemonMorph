
package org.graalvm.shadowed.org.jcodings.specific;

import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;
import org.graalvm.shadowed.org.jcodings.unicode.UnicodeEncoding;

public final class UTF16BEEncoding
extends UnicodeEncoding {
    static final int[] UTF16EncLen = new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4, 4, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
    public static final UTF16BEEncoding INSTANCE = new UTF16BEEncoding();

    protected UTF16BEEncoding() {
        super("UTF-16BE", 2, 4, UTF16EncLen);
    }

    @Override
    public int length(byte[] bytes, int p, int end2) {
        int b = bytes[p] & 0xFF;
        if (!UTF16BEEncoding.isSurrogate(b)) {
            return end2 - p >= 2 ? 2 : this.missing(1);
        }
        if (UTF16BEEncoding.isSurrogateFirst(b)) {
            switch (end2 - p) {
                case 1: {
                    return this.missing(3);
                }
                case 2: {
                    return this.missing(2);
                }
                case 3: {
                    if (!UTF16BEEncoding.isSurrogateSecond(bytes[p + 2] & 0xFF)) break;
                    return this.missing(1);
                }
            }
            if (UTF16BEEncoding.isSurrogateSecond(bytes[p + 2] & 0xFF)) {
                return 4;
            }
        }
        return -1;
    }

    @Override
    public boolean isNewLine(byte[] bytes, int p, int end2) {
        return p + 1 < end2 && bytes[p + 1] == 10 && bytes[p] == 0;
    }

    @Override
    public int mbcToCode(byte[] bytes, int p, int end2) {
        int code = UTF16BEEncoding.isSurrogateFirst(bytes[p] & 0xFF) ? ((((bytes[p + 0] & 0xFF) << 8) + (bytes[p + 1] & 0xFF) & 0x3FF) << 10) + (((bytes[p + 2] & 0xFF) << 8) + (bytes[p + 3] & 0xFF) & 0x3FF) + 65536 : (bytes[p + 0] & 0xFF) * 256 + (bytes[p + 1] & 0xFF);
        return code;
    }

    @Override
    public int codeToMbcLength(int code) {
        return code > 65535 ? 4 : 2;
    }

    @Override
    public int codeToMbc(int code, byte[] bytes, int p) {
        int p_ = p;
        if (code > 65535) {
            int high = (code >>> 10) + 55232;
            int low = (code & 0x3FF) + 56320;
            bytes[p_++] = (byte)(high >>> 8 & 0xFF);
            bytes[p_++] = (byte)(high & 0xFF);
            bytes[p_++] = (byte)(low >>> 8 & 0xFF);
            bytes[p_] = (byte)(low & 0xFF);
            return 4;
        }
        bytes[p_++] = (byte)((code & 0xFF00) >>> 8);
        bytes[p_++] = (byte)(code & 0xFF);
        return 2;
    }

    @Override
    public int mbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end2, byte[] fold) {
        int p = pp.value;
        int foldP = 0;
        if (UTF16BEEncoding.isAscii(bytes[p + 1] & 0xFF) && bytes[p] == 0) {
            fold[foldP++] = 0;
            fold[foldP] = AsciiTables.ToLowerCaseTable[bytes[++p] & 0xFF];
            pp.value += 2;
            return 2;
        }
        return super.mbcCaseFold(flag, bytes, pp, end2, fold);
    }

    @Override
    public int[] ctypeCodeRange(int ctype, IntHolder sbOut) {
        sbOut.value = 0;
        return super.ctypeCodeRange(ctype);
    }

    @Override
    public int leftAdjustCharHead(byte[] bytes, int p, int s, int end2) {
        if (s <= p) {
            return s;
        }
        if ((s - p) % 2 == 1) {
            --s;
        }
        if (UTF16BEEncoding.isSurrogateSecond(bytes[s] & 0xFF) && s > p + 1) {
            s -= 2;
        }
        return s;
    }

    @Override
    public boolean isReverseMatchAllowed(byte[] bytes, int p, int end2) {
        return false;
    }

    private static boolean isSurrogateFirst(int c) {
        return (c & 0xFC) == 216;
    }

    private static boolean isSurrogateSecond(int c) {
        return (c & 0xFC) == 220;
    }

    private static boolean isSurrogate(int c) {
        return (c & 0xF8) == 216;
    }
}

