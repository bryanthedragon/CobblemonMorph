
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.strings.TStringConstants;

public final class InternalByteArray {
    static final InternalByteArray EMPTY = new InternalByteArray(TStringConstants.EMPTY_BYTES, 0, 0);
    private final byte[] array;
    private final int offset;
    private final int length;

    InternalByteArray(byte[] array, int offset, int length) {
        this.array = array;
        this.offset = offset;
        this.length = length;
    }

    public byte[] getArray() {
        return this.array;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getLength() {
        return this.length;
    }

    public int getEnd() {
        return this.offset + this.length;
    }

    public byte get(int index) {
        return this.array[this.offset + index];
    }
}

