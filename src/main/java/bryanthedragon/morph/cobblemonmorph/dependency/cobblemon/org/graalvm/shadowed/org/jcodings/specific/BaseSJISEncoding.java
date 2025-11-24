
package org.graalvm.shadowed.org.jcodings.specific;

import org.graalvm.shadowed.org.jcodings.CanBeTrailTableEncoding;
import org.graalvm.shadowed.org.jcodings.CodeRange;
import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;
import org.graalvm.shadowed.org.jcodings.exception.InternalException;
import org.graalvm.shadowed.org.jcodings.util.BytesHash;

abstract class BaseSJISEncoding
extends CanBeTrailTableEncoding {
    private static final int[] CR_Hiragana = new int[]{1, 33439, 33521};
    private static final int[] CR_Katakana = new int[]{4, 166, 175, 177, 221, 33600, 33662, 33664, 33686};
    private static final int[][] PropertyList = new int[][]{CR_Hiragana, CR_Katakana};
    private static final BytesHash<Integer> CTypeNameHash = new BytesHash();
    static final boolean[] SJIS_CAN_BE_TRAIL_TABLE;
    static final int[] SjisEncLen;

    protected BaseSJISEncoding(String name, int[][] Trans) {
        super(name, 1, 2, SjisEncLen, Trans, AsciiTables.AsciiCtypeTable, SJIS_CAN_BE_TRAIL_TABLE);
    }

    @Override
    public String getCharsetName() {
        return "windows-31j";
    }

    @Override
    public int mbcToCode(byte[] bytes, int p, int end2) {
        return this.mbnMbcToCode(bytes, p, end2);
    }

    @Override
    public int codeToMbcLength(int code) {
        if (code < 256) {
            return SjisEncLen[code] == 1 ? 1 : -400;
        }
        if (code <= 65535) {
            int low = code & 0xFF;
            if (!BaseSJISEncoding.SJIS_ISMB_TRAIL(low)) {
                return -400;
            }
            return 2;
        }
        return -400;
    }

    private static boolean SJIS_ISMB_TRAIL(int code) {
        return SJIS_CAN_BE_TRAIL_TABLE[code];
    }

    @Override
    public int codeToMbc(int code, byte[] bytes, int p) {
        int p_ = p;
        if ((code & 0xFF00) != 0) {
            bytes[p_++] = (byte)(code >> 8 & 0xFF);
        }
        bytes[p_++] = (byte)(code & 0xFF);
        return p_ - p;
    }

    private static int getLowerCase(int code) {
        if (BaseSJISEncoding.isInRange(code, 33376, 33401)) {
            return code + 33;
        }
        if (BaseSJISEncoding.isInRange(code, 33695, 33718)) {
            return code + 32;
        }
        if (BaseSJISEncoding.isInRange(code, 33856, 33888)) {
            int d = code >= 33871 ? 1 : 0;
            return code + (48 + d);
        }
        return code;
    }

    @Override
    public int mbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end2, byte[] lower) {
        if (BaseSJISEncoding.isAscii(bytes[pp.value])) {
            return this.asciiMbcCaseFold(flag, bytes, pp, end2, lower);
        }
        int lowerP = 0;
        int code = BaseSJISEncoding.getLowerCase(this.mbcToCode(bytes, pp.value, end2));
        int len = this.codeToMbc(code, lower, lowerP);
        pp.value += len;
        return len;
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
            return BaseSJISEncoding.isWordGraphPrint(ctype);
        }
        if ((ctype -= 15) >= PropertyList.length) {
            throw new InternalException("undefined type (bug)");
        }
        return CodeRange.isInCodeRange(PropertyList[ctype], code);
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
        CTypeNameHash.put("Hiragana".getBytes(), 15);
        CTypeNameHash.put("Katakana".getBytes(), 16);
        SJIS_CAN_BE_TRAIL_TABLE = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false};
        SjisEncLen = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1};
    }
}

