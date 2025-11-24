
package org.graalvm.shadowed.org.jcodings.specific;

import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;
import org.graalvm.shadowed.org.jcodings.unicode.FixedWidthUnicodeEncoding;

public final class UTF32LEEncoding
extends FixedWidthUnicodeEncoding {
    public static UTF32LEEncoding INSTANCE = new UTF32LEEncoding();

    protected UTF32LEEncoding() {
        super("UTF-32LE", 4);
    }

    @Override
    public boolean isNewLine(byte[] bytes, int p, int end2) {
        return p + 3 < end2 && bytes[p + 3] == 0 && bytes[p + 2] == 0 && bytes[p + 1] == 0 && bytes[p] == 10;
    }

    @Override
    public int mbcToCode(byte[] bytes, int p, int end2) {
        return (((bytes[p + 3] & 0xFF) * 256 + (bytes[p + 2] & 0xFF)) * 256 + (bytes[p + 1] & 0xFF)) * 256 + (bytes[p] & 0xFF);
    }

    @Override
    public int codeToMbc(int code, byte[] bytes, int p) {
        int p_ = p;
        bytes[p_++] = (byte)(code & 0xFF);
        bytes[p_++] = (byte)((code & 0xFF00) >>> 8);
        bytes[p_++] = (byte)((code & 0xFF0000) >>> 16);
        bytes[p_++] = (byte)((code & 0xFF000000) >>> 24);
        return 4;
    }

    @Override
    public int mbcCaseFold(int flag, byte[] bytes, IntHolder pp, int end2, byte[] fold) {
        int p = pp.value;
        int foldP = 0;
        if (UTF32LEEncoding.isAscii(bytes[p] & 0xFF) && bytes[p + 1] == 0 && bytes[p + 2] == 0 && bytes[p + 3] == 0) {
            fold[foldP++] = AsciiTables.ToLowerCaseTable[bytes[p] & 0xFF];
            fold[foldP++] = 0;
            fold[foldP++] = 0;
            fold[foldP] = 0;
            pp.value += 4;
            return 4;
        }
        return super.mbcCaseFold(flag, bytes, pp, end2, fold);
    }
}

