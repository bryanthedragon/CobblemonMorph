
package org.graalvm.shadowed.org.jcodings;

import org.graalvm.shadowed.org.jcodings.ApplyAllCaseFoldFunction;
import org.graalvm.shadowed.org.jcodings.CaseFoldCodeItem;
import org.graalvm.shadowed.org.jcodings.Encoding;
import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;
import org.graalvm.shadowed.org.jcodings.constants.PosixBracket;
import org.graalvm.shadowed.org.jcodings.exception.CharacterPropertyException;
import org.graalvm.shadowed.org.jcodings.exception.EncodingError;

abstract class AbstractEncoding
extends Encoding {
    private final short[] CTypeTable;

    protected AbstractEncoding(String name, int minLength, int maxLength, short[] CTypeTable) {
        super(name, minLength, maxLength);
        this.CTypeTable = CTypeTable;
    }

    private static int CTypeToBit(int ctype) {
        return 1 << ctype;
    }

    protected final boolean isCodeCTypeInternal(int code, int ctype) {
        return (this.CTypeTable[code] & AbstractEncoding.CTypeToBit(ctype)) != 0;
    }

    @Override
    public boolean isNewLine(byte[] bytes, int p, int end2) {
        return p < end2 ? bytes[p] == 10 : false;
    }

    protected final int asciiMbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end2, byte[] lower) {
        lower[0] = AsciiTables.ToLowerCaseTable[bytes[pp.value] & 0xFF];
        ++pp.value;
        return 1;
    }

    @Override
    public int mbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end2, byte[] lower) {
        return this.asciiMbcCaseFold(flag, bytes, pp, end2, lower);
    }

    protected final void asciiApplyAllCaseFold(int flag, ApplyAllCaseFoldFunction fun, Object arg) {
        int[] code = new int[]{0};
        for (int i = 0; i < AsciiTables.LowerMap.length; ++i) {
            code[0] = AsciiTables.LowerMap[i][1];
            fun.apply(AsciiTables.LowerMap[i][0], code, 1, arg);
            code[0] = AsciiTables.LowerMap[i][0];
            fun.apply(AsciiTables.LowerMap[i][1], code, 1, arg);
        }
    }

    @Override
    public void applyAllCaseFold(int flag, ApplyAllCaseFoldFunction fun, Object arg) {
        this.asciiApplyAllCaseFold(flag, fun, arg);
    }

    protected final CaseFoldCodeItem[] asciiCaseFoldCodesByString(int flag, byte[] bytes, int p, int end2) {
        int b = bytes[p] & 0xFF;
        if (65 <= b && b <= 90) {
            return new CaseFoldCodeItem[]{CaseFoldCodeItem.create(1, b + 32)};
        }
        if (97 <= b && b <= 122) {
            return new CaseFoldCodeItem[]{CaseFoldCodeItem.create(1, b - 32)};
        }
        return CaseFoldCodeItem.EMPTY_FOLD_CODES;
    }

    @Override
    public CaseFoldCodeItem[] caseFoldCodesByString(int flag, byte[] bytes, int p, int end2) {
        return this.asciiCaseFoldCodesByString(flag, bytes, p, end2);
    }

    int asciiOnlyCaseMap(IntHolder flagP, byte[] bytes, IntHolder pp, int end2, byte[] to, int toP, int toEnd) {
        int toStart = toP;
        int flags = flagP.value;
        while (pp.value < end2 && toP < toEnd) {
            int length = this.length(bytes, pp.value, end2);
            if (length < 0) {
                return length;
            }
            int code = this.mbcToCode(bytes, pp.value, end2);
            pp.value += length;
            if (code >= 97 && code <= 122 && (flags & 0x2000) != 0) {
                flags |= 0x40000;
                code -= 32;
            } else if (code >= 65 && code <= 90 && (flags & 0x84000) != 0) {
                flags |= 0x40000;
                code += 32;
            }
            toP += this.codeToMbc(code, to, toP);
            if ((flags & 0x8000) == 0) continue;
            flags ^= 0xE000;
        }
        flagP.value = flags;
        return toP - toStart;
    }

    int singleByteAsciiOnlyCaseMap(IntHolder flagP, byte[] bytes, IntHolder pp, int end2, byte[] to, int toP, int toEnd) {
        int toStart = toP;
        int flags = flagP.value;
        while (pp.value < end2 && toP < toEnd) {
            int code;
            if ((code = bytes[pp.value++] & 0xFF) >= 97 && code <= 122 && (flags & 0x2000) != 0) {
                flags |= 0x40000;
                code -= 32;
            } else if (code >= 65 && code <= 90 && (flags & 0x84000) != 0) {
                flags |= 0x40000;
                code += 32;
            }
            to[toP++] = (byte)code;
            if ((flags & 0x8000) == 0) continue;
            flags ^= 0xE000;
        }
        flagP.value = flags;
        return toP - toStart;
    }

    @Override
    public int propertyNameToCType(byte[] bytes, int p, int end2) {
        Integer ctype = PosixBracket.PBSTableUpper.get(bytes, p, end2);
        if (ctype != null) {
            return ctype;
        }
        throw new CharacterPropertyException(EncodingError.ERR_INVALID_CHAR_PROPERTY_NAME, bytes, p, end2 - p);
    }
}

