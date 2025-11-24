
package org.graalvm.shadowed.org.jcodings;

import org.graalvm.shadowed.org.jcodings.AbstractEncoding;
import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;

public abstract class MultiByteEncoding
extends AbstractEncoding {
    protected final int[] EncLen;
    protected static final int A = -1;
    protected static final int F = -2;
    protected final int[][] Trans;
    protected final int[] TransZero;

    protected MultiByteEncoding(String name, int minLength, int maxLength, int[] EncLen, int[][] Trans, short[] CTypeTable) {
        super(name, minLength, maxLength, CTypeTable);
        this.EncLen = EncLen;
        this.Trans = Trans;
        this.TransZero = Trans != null ? Trans[0] : null;
    }

    @Override
    public int length(byte c) {
        return this.EncLen[c & 0xFF];
    }

    protected final int missing(int n) {
        return -1 - n;
    }

    protected final int missing(int b, int delta) {
        return this.missing(this.EncLen[b] - delta);
    }

    protected final int safeLengthForUptoFour(byte[] bytes, int p, int end2) {
        int b = bytes[p] & 0xFF;
        int s = this.TransZero[b];
        if (s < 0) {
            return s == -1 ? 1 : -1;
        }
        return this.lengthForTwoUptoFour(bytes, p, end2, b, s);
    }

    protected final int lengthForTwoUptoFour(byte[] bytes, int p, int end2, int b, int s) {
        if (++p == end2) {
            return this.missing(b, 1);
        }
        if ((s = this.Trans[s][bytes[p] & 0xFF]) < 0) {
            return s == -1 ? 2 : -1;
        }
        return this.lengthForThreeUptoFour(bytes, p, end2, b, s);
    }

    private int lengthForThreeUptoFour(byte[] bytes, int p, int end2, int b, int s) {
        if (++p == end2) {
            return this.missing(b, 2);
        }
        if ((s = this.Trans[s][bytes[p] & 0xFF]) < 0) {
            return s == -1 ? 3 : -1;
        }
        if (++p == end2) {
            return this.missing(b, 3);
        }
        return (s = this.Trans[s][bytes[p] & 0xFF]) == -1 ? 4 : -1;
    }

    protected final int safeLengthForUptoThree(byte[] bytes, int p, int end2) {
        int b = bytes[p] & 0xFF;
        int s = this.TransZero[b];
        if (s < 0) {
            return s == -1 ? 1 : -1;
        }
        return this.lengthForTwoUptoThree(bytes, p, end2, b, s);
    }

    private int lengthForTwoUptoThree(byte[] bytes, int p, int end2, int b, int s) {
        if (++p == end2) {
            return this.missing(b, 1);
        }
        if ((s = this.Trans[s][bytes[p] & 0xFF]) < 0) {
            return s == -1 ? 2 : -1;
        }
        return this.lengthForThree(bytes, p, end2, b, s);
    }

    private int lengthForThree(byte[] bytes, int p, int end2, int b, int s) {
        if (++p == end2) {
            return this.missing(b, 2);
        }
        return (s = this.Trans[s][bytes[p] & 0xFF]) == -1 ? 3 : -1;
    }

    protected final int safeLengthForUptoTwo(byte[] bytes, int p, int end2) {
        int b = bytes[p] & 0xFF;
        int s = this.TransZero[b];
        if (s < 0) {
            return s == -1 ? 1 : -1;
        }
        return this.lengthForTwo(bytes, p, end2, b, s);
    }

    private int lengthForTwo(byte[] bytes, int p, int end2, int b, int s) {
        if (++p == end2) {
            return this.missing(b, 1);
        }
        return (s = this.Trans[s][bytes[p] & 0xFF]) == -1 ? 2 : -1;
    }

    protected final int mbnMbcToCode(byte[] bytes, int p, int end2) {
        int len = this.length(bytes, p, end2);
        int n = bytes[p++] & 0xFF;
        if (len == 1) {
            return n;
        }
        for (int i = 1; i < len && p < end2; ++i) {
            int c = bytes[p++] & 0xFF;
            n <<= 8;
            n += c;
        }
        return n;
    }

    @Override
    public int caseMap(IntHolder flagP, byte[] bytes, IntHolder pp, int end2, byte[] to, int toP, int toEnd) {
        return this.asciiOnlyCaseMap(flagP, bytes, pp, end2, to, toP, toEnd);
    }

    protected final int mbnMbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end2, byte[] lower) {
        int p = pp.value++;
        int lowerP = 0;
        if (MultiByteEncoding.isAscii(bytes[p] & 0xFF)) {
            lower[lowerP] = AsciiTables.ToLowerCaseTable[bytes[p] & 0xFF];
            return 1;
        }
        int len = this.length(bytes, p, end2);
        for (int i = 0; i < len; ++i) {
            lower[lowerP++] = bytes[p++];
        }
        pp.value += len;
        return len;
    }

    protected final int mb2CodeToMbcLength(int code) {
        return (code & 0xFF00) != 0 ? 2 : 1;
    }

    protected final int mb4CodeToMbcLength(int code) {
        if ((code & 0xFF000000) != 0) {
            return 4;
        }
        if ((code & 0xFF0000) != 0) {
            return 3;
        }
        if ((code & 0xFF00) != 0) {
            return 2;
        }
        return 1;
    }

    protected final int mb2CodeToMbc(int code, byte[] bytes, int p) {
        int p_ = p;
        if ((code & 0xFF00) != 0) {
            bytes[p_++] = (byte)(code >>> 8 & 0xFF);
        }
        bytes[p_++] = (byte)(code & 0xFF);
        if (this.length(bytes, p, p_) != p_ - p) {
            return -400;
        }
        return p_ - p;
    }

    protected final int mb4CodeToMbc(int code, byte[] bytes, int p) {
        int p_ = p;
        if ((code & 0xFF000000) != 0) {
            bytes[p_++] = (byte)(code >>> 24 & 0xFF);
        }
        if ((code & 0xFF0000) != 0 || p_ != p) {
            bytes[p_++] = (byte)(code >>> 16 & 0xFF);
        }
        if ((code & 0xFF00) != 0 || p_ != p) {
            bytes[p_++] = (byte)(code >>> 8 & 0xFF);
        }
        bytes[p_++] = (byte)(code & 0xFF);
        if (this.length(bytes, p, p_) != p_ - p) {
            return -400;
        }
        return p_ - p;
    }

    protected final boolean mb2IsCodeCType(int code, int ctype) {
        if (((long)code & 0xFFFFFFFFL) < 128L) {
            return this.isCodeCTypeInternal(code, ctype);
        }
        if (MultiByteEncoding.isWordGraphPrint(ctype)) {
            return this.codeToMbcLength(code) > 1;
        }
        return false;
    }

    protected final boolean mb4IsCodeCType(int code, int ctype) {
        return this.mb2IsCodeCType(code, ctype);
    }

    @Override
    public int strLength(byte[] bytes, int p, int end2) {
        int n = 0;
        int q = p;
        while (q < end2) {
            q += this.length(bytes, q, end2);
            ++n;
        }
        return n;
    }

    @Override
    public int strCodeAt(byte[] bytes, int p, int end2, int index) {
        int n = 0;
        int q = p;
        while (q < end2) {
            if (n == index) {
                return this.mbcToCode(bytes, q, end2);
            }
            q += this.length(bytes, q, end2);
            ++n;
        }
        return -1;
    }

    public static boolean isInRange(int code, int from, int to) {
        return code - from >= 0 && to - code >= 0;
    }
}

