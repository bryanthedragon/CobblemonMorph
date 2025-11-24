
package com.oracle.truffle.regex.tregex.buffer;

public abstract class AbstractArrayBuffer {
    int length;

    public void clear() {
        this.length = 0;
    }

    public boolean isEmpty() {
        return this.length == 0;
    }

    public int length() {
        return this.length;
    }

    public void setLength(int size) {
        this.length = size;
    }

    public void ensureCapacity(int newLength) {
        if (this.getBufferLength() < newLength) {
            int newBufferLength;
            for (newBufferLength = this.getBufferLength() * 2; newBufferLength < newLength; newBufferLength *= 2) {
            }
            this.grow(newBufferLength);
        }
    }

    abstract int getBufferLength();

    abstract void grow(int var1);
}

