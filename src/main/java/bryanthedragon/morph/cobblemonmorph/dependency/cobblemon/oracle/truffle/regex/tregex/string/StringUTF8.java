
package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.api.ArrayUtils;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.string.AbstractString;
import com.oracle.truffle.regex.tregex.string.AbstractStringIterator;
import java.util.Arrays;

public final class StringUTF8
implements AbstractString {
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final byte[] str;

    public StringUTF8(byte[] str) {
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
    public StringUTF8 substring(int start2, int end2) {
        return new StringUTF8(Arrays.copyOfRange(this.str, start2, end2));
    }

    @Override
    public boolean regionMatches(int offset, AbstractString other, int ooffset, int encodedLength) {
        return ArrayUtils.regionEqualsWithOrMask(this.str, offset, ((StringUTF8)other).str, ooffset, encodedLength, null);
    }

    @Override
    public TruffleString asTString() {
        return TruffleString.fromByteArrayUncached(this.str, 0, this.str.length, TruffleString.Encoding.UTF_8, false);
    }

    @Override
    public TruffleString.WithMask asTStringMask(TruffleString pattern) {
        return TruffleString.WithMask.createUncached(pattern, this.str, TruffleString.Encoding.UTF_8);
    }

    @Override
    public AbstractStringIterator iterator() {
        return new StringUTF8Iterator(this.str);
    }

    private static final class StringUTF8Iterator
    extends AbstractStringIterator {
        private final byte[] str;

        private StringUTF8Iterator(byte[] str) {
            this.str = str;
        }

        @Override
        public boolean hasNext() {
            return this.i < this.str.length;
        }

        @Override
        public int nextInt() {
            byte b;
            if (Byte.toUnsignedInt(b = this.str[this.i++]) < 128) {
                return b;
            }
            int nBytes = Integer.numberOfLeadingZeros(~(b << 24));
            int codepoint = b & 255 >>> nBytes;
            assert (1 < nBytes && nBytes < 5) : nBytes;
            switch (nBytes) {
                case 4: {
                    assert (this.hasNext() && (this.str[this.i] & 0xC0) == 128);
                    codepoint = codepoint << 6 | this.str[this.i++] & 0x3F;
                }
                case 3: {
                    assert (this.hasNext() && (this.str[this.i] & 0xC0) == 128);
                    codepoint = codepoint << 6 | this.str[this.i++] & 0x3F;
                }
            }
            assert (this.hasNext() && (this.str[this.i] & 0xC0) == 128);
            codepoint = codepoint << 6 | this.str[this.i++] & 0x3F;
            return codepoint;
        }
    }
}

