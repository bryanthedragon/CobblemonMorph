
package com.oracle.truffle.regex.tregex.buffer;

import com.oracle.truffle.regex.tregex.buffer.AbstractArrayBuffer;
import com.oracle.truffle.regex.util.EmptyArrays;
import java.util.Arrays;
import java.util.PrimitiveIterator;

public class IntArrayBuffer
extends AbstractArrayBuffer
implements Iterable<Integer> {
    protected int[] buf;

    public IntArrayBuffer() {
        this(8);
    }

    public IntArrayBuffer(int initialSize) {
        this.buf = new int[initialSize];
    }

    @Override
    int getBufferLength() {
        return this.buf.length;
    }

    @Override
    void grow(int newSize) {
        this.buf = Arrays.copyOf(this.buf, newSize);
    }

    public int[] getBuffer() {
        return this.buf;
    }

    public void add(int c) {
        if (this.length == this.buf.length) {
            this.grow(this.length * 2);
        }
        this.buf[this.length++] = c;
    }

    public IntArrayBuffer asFixedSizeArray(int size, int initialValue) {
        this.ensureCapacity(size);
        Arrays.fill(this.buf, 0, size, initialValue);
        this.length = size;
        return this;
    }

    public int get(int index) {
        assert (index < this.length);
        return this.buf[index];
    }

    public void inc(int index) {
        assert (index < this.length);
        int n = index;
        this.buf[n] = this.buf[n] + 1;
    }

    public void set(int index, int value2) {
        assert (index < this.length);
        this.buf[index] = value2;
    }

    public void addAll(IntArrayBuffer o) {
        this.ensureCapacity(this.length + o.length);
        System.arraycopy(o.buf, 0, this.buf, this.length, o.length);
    }

    public int[] toArray() {
        return this.isEmpty() ? EmptyArrays.INT : Arrays.copyOf(this.buf, this.length);
    }

    public PrimitiveIterator.OfInt iterator() {
        return new IntArrayBufferIterator(this.buf, this.length);
    }

    private static final class IntArrayBufferIterator
    implements PrimitiveIterator.OfInt {
        private final int[] buf;
        private final int size;
        private int i = 0;

        private IntArrayBufferIterator(int[] buf, int size) {
            this.buf = buf;
            this.size = size;
        }

        @Override
        public boolean hasNext() {
            return this.i < this.size;
        }

        @Override
        public int nextInt() {
            return this.buf[this.i++];
        }
    }
}

