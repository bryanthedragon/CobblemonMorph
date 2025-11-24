
package org.graalvm.shadowed.org.jcodings;

import org.graalvm.shadowed.org.jcodings.CaseFoldMapEncoding;
import org.graalvm.shadowed.org.jcodings.IntHolder;

public abstract class ISOEncoding
extends CaseFoldMapEncoding {
    public static int SHARP_s = 223;

    protected ISOEncoding(String name, short[] CTypeTable, byte[] LowerCaseTable, int[][] CaseFoldMap) {
        this(name, CTypeTable, LowerCaseTable, CaseFoldMap, true);
    }

    protected ISOEncoding(String name, short[] CTypeTable, byte[] LowerCaseTable, int[][] CaseFoldMap, boolean foldFlag) {
        super(name, CTypeTable, LowerCaseTable, CaseFoldMap, foldFlag);
    }

    @Override
    public String getCharsetName() {
        return new String(this.getName());
    }

    @Override
    public int mbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end2, byte[] lower) {
        int p = pp.value++;
        int lowerP = 0;
        if (bytes[p] == -33 && (flag & 0x40000000) != 0) {
            lower[lowerP++] = 115;
            lower[lowerP] = 115;
            return 2;
        }
        lower[lowerP] = this.LowerCaseTable[bytes[p] & 0xFF];
        ++pp.value;
        return 1;
    }

    @Override
    public boolean isCodeCType(int code, int ctype) {
        return code < 256 ? this.isCodeCTypeInternal(code, ctype) : false;
    }
}

