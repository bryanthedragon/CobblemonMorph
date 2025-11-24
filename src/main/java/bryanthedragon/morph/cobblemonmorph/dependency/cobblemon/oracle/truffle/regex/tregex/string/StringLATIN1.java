
package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.string.AbstractString;
import com.oracle.truffle.regex.tregex.string.AbstractStringIterator;
import java.util.Arrays;

public final class StringLATIN1
implements AbstractString {
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final byte[] str;

    public StringLATIN1(byte[] str) {
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
    public StringLATIN1 substring(int start2, int end2) {
        return new StringLATIN1(Arrays.copyOfRange(this.str, start2, end2));
    }

    @Override
    public boolean regionMatches(int offset, AbstractString other, int ooffset, int encodedLength) {
        byte[] o = ((StringLATIN1)other).str;
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
        return TruffleString.fromByteArrayUncached(this.str, 0, this.str.length, TruffleString.Encoding.ISO_8859_1, false);
    }

    @Override
    public TruffleString.WithMask asTStringMask(TruffleString pattern) {
        return TruffleString.WithMask.createUncached(pattern, this.str, TruffleString.Encoding.ISO_8859_1);
    }

    @Override
    public AbstractStringIterator iterator() {
        return new StringLATIN1Iterator(this.str);
    }

    private static final class StringLATIN1Iterator
    extends AbstractStringIterator {
        private final byte[] str;

        private StringLATIN1Iterator(byte[] str) {
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

