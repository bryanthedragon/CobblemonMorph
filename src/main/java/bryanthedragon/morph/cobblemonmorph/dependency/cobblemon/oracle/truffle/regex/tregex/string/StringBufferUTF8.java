
package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.regex.tregex.buffer.ByteArrayBuffer;
import com.oracle.truffle.regex.tregex.string.AbstractStringBuffer;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.tregex.string.StringUTF8;

public final class StringBufferUTF8
extends ByteArrayBuffer
implements AbstractStringBuffer {
    public StringBufferUTF8() {
        this(16);
    }

    public StringBufferUTF8(int capacity) {
        super(capacity);
    }

    @Override
    public Encodings.Encoding getEncoding() {
        return Encodings.UTF_8;
    }

    @Override
    public void append(int codepoint) {
        int n = this.getEncoding().getEncodedSize(codepoint);
        int newLength = this.length() + n;
        this.ensureCapacity(newLength);
        this.setLength(newLength);
        int i = newLength;
        if (n == 1) {
            this.set(--i, (byte)codepoint);
            return;
        }
        int c = codepoint;
        switch (n) {
            case 4: {
                this.set(--i, (byte)(0x80 | c & 0x3F));
                c >>>= 6;
            }
            case 3: {
                this.set(--i, (byte)(0x80 | c & 0x3F));
                c >>>= 6;
            }
        }
        this.set(--i, (byte)(0x80 | c & 0x3F));
        this.set(--i, (byte)(3840 >>> n | (c >>>= 6)));
    }

    @Override
    public void appendOR(int cp1, int cp2) {
        int n = this.getEncoding().getEncodedSize(cp1);
        assert (this.getEncoding().getEncodedSize(cp2) == n);
        int newLength = this.length() + n;
        this.ensureCapacity(newLength);
        this.setLength(newLength);
        int i = newLength;
        if (n == 1) {
            this.set(--i, (byte)(cp1 | cp2));
            return;
        }
        int c1 = cp1;
        int c2 = cp2;
        switch (n) {
            case 4: {
                this.set(--i, (byte)(0x80 | (c1 | c2) & 0x3F));
                c1 >>>= 6;
                c2 >>>= 6;
            }
            case 3: {
                this.set(--i, (byte)(0x80 | (c1 | c2) & 0x3F));
                c1 >>>= 6;
                c2 >>>= 6;
            }
        }
        this.set(--i, (byte)(0x80 | (c1 | c2) & 0x3F));
        this.set(--i, (byte)(3840 >>> n | ((c1 >>>= 6) | (c2 >>>= 6))));
    }

    @Override
    public void appendXOR(int cp1, int cp2) {
        int n = this.getEncoding().getEncodedSize(cp1);
        assert (this.getEncoding().getEncodedSize(cp2) == n);
        int newLength = this.length() + n;
        this.ensureCapacity(newLength);
        this.setLength(newLength);
        int i = newLength;
        if (n == 1) {
            this.set(--i, (byte)(cp1 ^ cp2));
            return;
        }
        int c1 = cp1;
        int c2 = cp2;
        switch (n) {
            case 4: {
                this.set(--i, (byte)((c1 ^ c2) & 0x3F));
                c1 >>>= 6;
                c2 >>>= 6;
            }
            case 3: {
                this.set(--i, (byte)((c1 ^ c2) & 0x3F));
                c1 >>>= 6;
                c2 >>>= 6;
            }
        }
        this.set(--i, (byte)((c1 ^ c2) & 0x3F));
        this.set(--i, (byte)((c1 >>>= 6) ^ (c2 >>>= 6)));
    }

    @Override
    public StringUTF8 materialize() {
        return new StringUTF8(this.toArray());
    }
}

