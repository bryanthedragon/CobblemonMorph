
package org.graalvm.shadowed.org.jcodings.specific;

import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;
import org.graalvm.shadowed.org.jcodings.unicode.UnicodeEncoding;

abstract class BaseUTF8Encoding
extends UnicodeEncoding {
    static final boolean USE_INVALID_CODE_SCHEME = true;
    private static final int INVALID_CODE_FE = -2;
    private static final int INVALID_CODE_FF = -1;
    private static final int VALID_CODE_LIMIT = 0x10FFFF;

    protected BaseUTF8Encoding(int[] EncLen, int[][] Trans) {
        super("UTF-8", 1, 4, EncLen, Trans);
        this.isUTF8 = true;
    }

    @Override
    public String getCharsetName() {
        return "UTF-8";
    }

    @Override
    public boolean isNewLine(byte[] bytes, int p, int end2) {
        return p < end2 && bytes[p] == 10;
    }

    @Override
    public int codeToMbcLength(int code) {
        if ((code & 0xFFFFFF80) == 0) {
            return 1;
        }
        if ((code & 0xFFFFF800) == 0) {
            return 2;
        }
        if ((code & 0xFFFF0000) == 0) {
            return 3;
        }
        if (((long)code & 0xFFFFFFFFL) <= 0x10FFFFL) {
            return 4;
        }
        if (code == -2) {
            return 1;
        }
        if (code == -1) {
            return 1;
        }
        return -401;
    }

    @Override
    public int mbcToCode(byte[] bytes, int p, int end2) {
        int len = this.length(bytes, p, end2);
        int c = bytes[p++] & 0xFF;
        if (len > 1) {
            int n = c & (1 << 6 - --len) - 1;
            while (len-- != 0) {
                c = bytes[p++] & 0xFF;
                n = n << 6 | c & 0x3F;
            }
            return n;
        }
        if (c > 253) {
            return c == 254 ? -2 : -1;
        }
        return c;
    }

    static byte trailS(int code, int shift2) {
        return (byte)(code >>> shift2 & 0x3F | 0x80);
    }

    static byte trail0(int code) {
        return (byte)(code & 0x3F | 0x80);
    }

    @Override
    public int codeToMbc(int code, byte[] bytes, int p) {
        int p_ = p;
        if ((code & 0xFFFFFF80) == 0) {
            bytes[p_] = (byte)code;
            return 1;
        }
        if ((code & 0xFFFFF800) == 0) {
            bytes[p_++] = (byte)(code >>> 6 & 0x1F | 0xC0);
        } else if ((code & 0xFFFF0000) == 0) {
            bytes[p_++] = (byte)(code >>> 12 & 0xF | 0xE0);
            bytes[p_++] = BaseUTF8Encoding.trailS(code, 6);
        } else if (((long)code & 0xFFFFFFFFL) <= 0x10FFFFL) {
            bytes[p_++] = (byte)(code >>> 18 & 7 | 0xF0);
            bytes[p_++] = BaseUTF8Encoding.trailS(code, 12);
            bytes[p_++] = BaseUTF8Encoding.trailS(code, 6);
        } else {
            if (code == -2) {
                bytes[p_] = -2;
                return 1;
            }
            if (code == -1) {
                bytes[p_] = -1;
                return 1;
            }
            return -401;
        }
        bytes[p_++] = BaseUTF8Encoding.trail0(code);
        return p_ - p;
    }

    @Override
    public int mbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end2, byte[] fold) {
        int p = pp.value++;
        int foldP = 0;
        if (BaseUTF8Encoding.isMbcAscii(bytes[p])) {
            fold[foldP] = AsciiTables.ToLowerCaseTable[bytes[p] & 0xFF];
            return 1;
        }
        return super.mbcCaseFold(flag, bytes, pp, end2, fold);
    }

    @Override
    public int[] ctypeCodeRange(int ctype, IntHolder sbOut) {
        sbOut.value = 128;
        return super.ctypeCodeRange(ctype);
    }

    private static boolean utf8IsLead(int c) {
        return (c & 0xC0 & 0xFF) != 128;
    }

    @Override
    public int leftAdjustCharHead(byte[] bytes, int p, int s, int end2) {
        int p_;
        if (s <= p) {
            return s;
        }
        for (p_ = s; !BaseUTF8Encoding.utf8IsLead(bytes[p_] & 0xFF) && p_ > p; --p_) {
        }
        return p_;
    }

    @Override
    public boolean isReverseMatchAllowed(byte[] bytes, int p, int end2) {
        return true;
    }
}

