
package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.string.AbstractString;
import com.oracle.truffle.regex.tregex.string.AbstractStringIterator;
import java.util.Arrays;

public final class StringASCII
implements AbstractString {
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final byte[] str;

    public StringASCII(byte[] str) {
        this.str = str;
    }

    @Override
    public int encodedLength() {
        return this.str.length;
    }

    @Override
    public Object content() {
        return this.str;
    }

    public String toString() {
        return this.defaultToString();
    }

    @Override
    public StringASCII substring(int start2, int end2) {
        return new StringASCII(Arrays.copyOfRange(this.str, start2, end2));
    }

    @Override
    public boolean regionMatches(int offset, AbstractString other, int ooffset, int encodedLength) {
        byte[] o = ((StringASCII)other).str;
        if (offset + encodedLength > this.str.length || ooffset + encodedLength > o.length) {
            return false;
        }
        for (int i = 0; i < encodedLength; ++i) {
            if (this.str[offset + i] == o[ooffset + i]) continue;
            return false;
        }
        return true;
    }

    @Override
    public TruffleString asTString() {
        return TruffleString.fromByteArrayUncached(this.str, 0, this.str.length, TruffleString.Encoding.US_ASCII, false);
    }

    @Override
    public TruffleString.WithMask asTStringMask(TruffleString pattern) {
        return TruffleString.WithMask.createUncached(pattern, this.str, TruffleString.Encoding.US_ASCII);
    }

    @Override
    public AbstractStringIterator iterator() {
        return new StringASCIIIterator(this.str);
    }

    private static final class StringASCIIIterator
    extends AbstractStringIterator {
        private final byte[] str;

        private StringASCIIIterator(byte[] str) {
            this.str = str;
        }

        @Override
        public boolean hasNext() {
            return this.i < this.str.length;
        }

        @Override
        public int nextInt() {
            return Byte.toUnsignedInt(this.str[this.i++]);
        }
    }
}

