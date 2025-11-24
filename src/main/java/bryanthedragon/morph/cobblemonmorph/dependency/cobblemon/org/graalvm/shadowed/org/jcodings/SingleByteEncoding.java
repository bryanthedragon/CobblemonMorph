
package org.graalvm.shadowed.org.jcodings;

import org.graalvm.shadowed.org.jcodings.AbstractEncoding;
import org.graalvm.shadowed.org.jcodings.IntHolder;

public abstract class SingleByteEncoding
extends AbstractEncoding {
    public static final int MAX_BYTE = 255;
    protected final byte[] LowerCaseTable;

    protected SingleByteEncoding(String name, short[] CTypeTable, byte[] LowerCaseTable) {
        super(name, 1, 1, CTypeTable);
        this.LowerCaseTable = LowerCaseTable;
    }

    @Override
    public int length(byte c) {
        return 1;
    }

    @Override
    public int length(byte[] bytes, int p, int end2) {
        return 1;
    }

    @Override
    public final int strLength(byte[] bytes, int p, int end2) {
        return end2 - p;
    }

    @Override
    public int strCodeAt(byte[] bytes, int p, int end2, int index) {
        return bytes[index] & 0xFF;
    }

    @Override
    public int caseMap(IntHolder flagP, byte[] bytes, IntHolder pp, int end2, byte[] to, int toP, int toEnd) {
        return this.singleByteAsciiOnlyCaseMap(flagP, bytes, pp, end2, to, toP, toEnd);
    }

    @Override
    public int mbcToCode(byte[] bytes, int p, int end2) {
        return bytes[p] & 0xFF;
    }

    @Override
    public int codeToMbcLength(int code) {
        return 1;
    }

    @Override
    public final int codeToMbc(int code, byte[] bytes, int p) {
        if (code > 255) {
            return -401;
        }
        bytes[p] = (byte)code;
        return 1;
    }

    @Override
    public final int[] ctypeCodeRange(int ctype, IntHolder sbOut) {
        return null;
    }

    @Override
    public final int leftAdjustCharHead(byte[] bytes, int p, int s, int end2) {
        return s;
    }

    @Override
    public final boolean isReverseMatchAllowed(byte[] bytes, int p, int end2) {
        return true;
    }
}

