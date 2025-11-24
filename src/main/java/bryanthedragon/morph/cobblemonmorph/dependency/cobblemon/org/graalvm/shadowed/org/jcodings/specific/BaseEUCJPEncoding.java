
package org.graalvm.shadowed.org.jcodings.specific;

import org.graalvm.shadowed.org.jcodings.CodeRange;
import org.graalvm.shadowed.org.jcodings.EucEncoding;
import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;
import org.graalvm.shadowed.org.jcodings.exception.InternalException;
import org.graalvm.shadowed.org.jcodings.util.CaseInsensitiveBytesHash;

abstract class BaseEUCJPEncoding
extends EucEncoding {
    private static final int[] CR_Hiragana = new int[]{1, 42145, 42227};
    private static final int[] CR_Katakana = new int[]{3, 36518, 36527, 36529, 36573, 42401, 42486};
    private static final int[] CR_Han = new int[]{4, 41400, 41400, 45217, 53203, 53409, 62630, 9416865, 9432563};
    private static final int[] CR_Latin = new int[]{4, 65, 90, 97, 122, 41921, 41946, 41953, 41978};
    private static final int[] CR_Greek = new int[]{2, 42657, 42680, 42689, 42712};
    private static final int[] CR_Cyrillic = new int[]{2, 42913, 42945, 42961, 42993};
    private static final int[][] PropertyList = new int[][]{CR_Hiragana, CR_Katakana, CR_Han, CR_Latin, CR_Greek, CR_Cyrillic};
    private static final CaseInsensitiveBytesHash<Integer> CTypeNameHash = new CaseInsensitiveBytesHash();
    static final int[] EUCJPEncLen;

    protected BaseEUCJPEncoding(int[][] Trans) {
        super("EUC-JP", 1, 3, EUCJPEncLen, Trans, AsciiTables.AsciiCtypeTable);
    }

    @Override
    public int mbcToCode(byte[] bytes, int p, int end2) {
        return this.mbnMbcToCode(bytes, p, end2);
    }

    @Override
    public int codeToMbcLength(int code) {
        if (BaseEUCJPEncoding.isAscii(code)) {
            return 1;
        }
        if (code > 0xFFFFFF) {
            return -401;
        }
        if ((code & 0xFF808080) == 0x808080) {
            return 3;
        }
        if ((code & 0xFFFF8080) == 32896) {
            return 2;
        }
        return -400;
    }

    @Override
    public int codeToMbc(int code, byte[] bytes, int p) {
        int p_ = p;
        if ((code & 0xFF0000) != 0) {
            bytes[p_++] = (byte)(code >> 16 & 0xFF);
        }
        if ((code & 0xFF00) != 0) {
            bytes[p_++] = (byte)(code >> 8 & 0xFF);
        }
        bytes[p_++] = (byte)(code & 0xFF);
        if (this.length(bytes, p, p_) != p_ - p) {
            return -400;
        }
        return p_ - p;
    }

    private static int getLowerCase(int code) {
        if (BaseEUCJPEncoding.isInRange(code, 41921, 41946)) {
            return code + 32;
        }
        if (BaseEUCJPEncoding.isInRange(code, 42657, 42680)) {
            return code + 32;
        }
        if (BaseEUCJPEncoding.isInRange(code, 42913, 42945)) {
            return code + 48;
        }
        return code;
    }

    @Override
    public int mbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end2, byte[] lower) {
        int p = pp.value++;
        int lowerP = 0;
        if (BaseEUCJPEncoding.isMbcAscii(bytes[p])) {
            lower[lowerP] = AsciiTables.ToLowerCaseTable[bytes[p] & 0xFF];
            return 1;
        }
        int code = BaseEUCJPEncoding.getLowerCase(this.mbcToCode(bytes, pp.value, end2));
        int len = this.codeToMbc(code, lower, lowerP);
        if (len == -400) {
            len = 1;
        }
        pp.value += len;
        return len;
    }

    @Override
    protected boolean isLead(int c) {
        return (c - 161 & 0xFF) > 93;
    }

    @Override
    public boolean isReverseMatchAllowed(byte[] bytes, int p, int end2) {
        int c = bytes[p] & 0xFF;
        return c <= 126 || c == 142 || c == 143;
    }

    @Override
    public int propertyNameToCType(byte[] bytes, int p, int end2) {
        Integer ctype = CTypeNameHash.get(bytes, p, end2);
        if (ctype == null) {
            return super.propertyNameToCType(bytes, p, end2);
        }
        return ctype;
    }

    @Override
    public boolean isCodeCType(int code, int ctype) {
        if (ctype <= 14) {
            if (code < 128) {
                return this.isCodeCTypeInternal(code, ctype);
            }
            if (BaseEUCJPEncoding.isWordGraphPrint(ctype)) {
                return this.codeToMbcLength(code) > 1;
            }
        } else {
            if ((ctype -= 15) >= PropertyList.length) {
                throw new InternalException("undefined type (bug)");
            }
            return CodeRange.isInCodeRange(PropertyList[ctype], code);
        }
        return false;
    }

    @Override
    public int[] ctypeCodeRange(int ctype, IntHolder sbOut) {
        if (ctype <= 14) {
            return null;
        }
        sbOut.value = 128;
        if ((ctype -= 15) >= PropertyList.length) {
            throw new InternalException("undefined type (bug)");
        }
        return PropertyList[ctype];
    }

    static {
        String[] names = new String[]{"Hiragana", "Katakana", "Han", "Latin", "Greek", "Cyrillic"};
        for (int i = 0; i < names.length; ++i) {
            CTypeNameHash.put(names[i].getBytes(), i + 1 + 14);
        }
        EUCJPEncLen = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1};
    }
}

