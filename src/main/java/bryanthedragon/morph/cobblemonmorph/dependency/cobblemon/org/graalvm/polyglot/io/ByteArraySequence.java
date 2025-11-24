
package org.graalvm.polyglot.io;

import java.util.Arrays;
import org.graalvm.polyglot.io.ByteSequence;

final class ByteArraySequence
implements ByteSequence {
    private final byte[] buffer;
    private final int start;
    private final int length;
    private int hash;

    ByteArraySequence(byte[] buffer, int start2, int length) {
        assert (buffer.length >= start2 + length);
        assert (start2 >= 0);
        assert (length >= 0);
        this.buffer = buffer;
        this.start = start2;
        this.length = length;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public byte byteAt(int index) {
        int resolvedIndex = this.start + index;
        if (resolvedIndex >= this.start + this.length) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        return this.buffer[resolvedIndex];
    }

    @Override
    public byte[] toByteArray() {
        return Arrays.copyOfRange(this.buffer, this.start, this.start + this.length);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ByteArraySequence) {
            ByteArraySequence other = (ByteArraySequence)obj;
            if (this.buffer == other.buffer) {
                return this.start == other.start && this.length == other.length;
            }
            if (this.length != other.length) {
                return false;
            }
            int thisHash = this.hash;
            int otherHash = other.hash;
            if (thisHash != 0 && otherHash != 0 && thisHash != otherHash) {
                return false;
            }
            int otherStart = other.start;
            for (int i = 0; i < this.length; ++i) {
                if (this.buffer[this.start + i] == other.buffer[otherStart + i]) continue;
                return false;
            }
            return true;
        }
        if (obj instanceof ByteSequence) {
            ByteSequence other = (ByteSequence)obj;
            if (this.length != other.length()) {
                return false;
            }
            for (int i = 0; i < this.length; ++i) {
                if (this.buffer[this.start + i] == other.byteAt(i)) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        int h = this.hash;
        if (h == 0 && this.length > 0) {
            int end2 = this.start + this.length;
            h = 1;
            int i = this.start;
            while (i + 3 < end2) {
                int h0 = this.buffer[i + 0] & 0xFF;
                int h1 = this.buffer[i + 1] & 0xFF00;
                int h2 = this.buffer[i + 2] & 0xFF0000;
                int h3 = this.buffer[i + 3] & 0xFF000000;
                h = 31 * h + (h0 | h1 | h2 | h3);
                i += 4;
            }
            while (i < end2) {
                h = 31 * h + this.buffer[i];
                ++i;
            }
            this.hash = h;
        }
        return h;
    }

    @Override
    public ByteSequence subSequence(int startIndex, int endIndex) {
        int l = endIndex - startIndex;
        if (l < 0) {
            throw new IndexOutOfBoundsException(String.valueOf(l));
        }
        int realStartIndex = this.start + startIndex;
        if (realStartIndex < 0) {
            throw new IndexOutOfBoundsException(String.valueOf(startIndex));
        }
        if (endIndex > this.length()) {
            throw new IndexOutOfBoundsException(String.valueOf(realStartIndex + l));
        }
        return new ByteArraySequence(this.buffer, realStartIndex, l);
    }
}

