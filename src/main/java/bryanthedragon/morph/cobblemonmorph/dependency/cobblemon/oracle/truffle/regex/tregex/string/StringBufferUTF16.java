
package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.regex.tregex.buffer.CharArrayBuffer;
import com.oracle.truffle.regex.tregex.string.AbstractStringBuffer;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.tregex.string.StringUTF16;

public final class StringBufferUTF16
extends CharArrayBuffer
implements AbstractStringBuffer {
    public StringBufferUTF16() {
    }

    public StringBufferUTF16(int initialCapacity) {
        super(initialCapacity);
    }

    @Override
    public Encodings.Encoding getEncoding() {
        return Encodings.UTF_16;
    }

    @Override
    public void append(int codepoint) {
        int n = this.getEncoding().getEncodedSize(codepoint);
        int newLength = this.length() + n;
        this.ensureCapacity(newLength);
        if (n == 1) {
            this.set(this.length(), (char)codepoint);
        } else {
            this.set(this.length(), Character.highSurrogate(codepoint));
            this.set(this.length() + 1, Character.lowSurrogate(codepoint));
        }
        this.setLength(newLength);
    }

    @Override
    public void appendOR(int c1, int c2) {
        int n = this.getEncoding().getEncodedSize(c1);
        assert (this.getEncoding().getEncodedSize(c2) == n);
        int newLength = this.length() + n;
        this.ensureCapacity(newLength);
        if (n == 1) {
            this.set(this.length(), (char)(c1 | c2));
        } else {
            this.set(this.length(), (char)(Character.highSurrogate(c1) | Character.highSurrogate(c2)));
            this.set(this.length() + 1, (char)(Character.lowSurrogate(c1) | Character.lowSurrogate(c2)));
        }
        this.setLength(newLength);
    }

    @Override
    public void appendXOR(int c1, int c2) {
        int n = this.getEncoding().getEncodedSize(c1);
        assert (this.getEncoding().getEncodedSize(c2) == n);
        int newLength = this.length() + n;
        this.ensureCapacity(newLength);
        if (n == 1) {
            this.set(this.length(), (char)(c1 ^ c2));
        } else {
            this.set(this.length(), (char)(Character.highSurrogate(c1) ^ Character.highSurrogate(c2)));
            this.set(this.length() + 1, (char)(Character.lowSurrogate(c1) ^ Character.lowSurrogate(c2)));
            assert (Integer.bitCount(this.get(this.length())) + Integer.bitCount(this.get(this.length() + 1)) == 1);
        }
        this.setLength(newLength);
    }

    @Override
    public StringUTF16 materialize() {
        return new StringUTF16(this.toArray());
    }
}

