
package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.regex.tregex.buffer.IntArrayBuffer;
import com.oracle.truffle.regex.tregex.string.AbstractStringBuffer;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.tregex.string.StringUTF32;

public final class StringBufferUTF32
extends IntArrayBuffer
implements AbstractStringBuffer {
    public StringBufferUTF32() {
        this(16);
    }

    public StringBufferUTF32(int capacity) {
        super(capacity);
    }

    @Override
    public Encodings.Encoding getEncoding() {
        return Encodings.UTF_32;
    }

    @Override
    public void append(int codepoint) {
        this.add(codepoint);
    }

    @Override
    public void appendOR(int c1, int c2) {
        this.add(c1 | c2);
    }

    @Override
    public void appendXOR(int c1, int c2) {
        this.add(c1 ^ c2);
    }

    @Override
    public StringUTF32 materialize() {
        return new StringUTF32(this.toArray());
    }
}

