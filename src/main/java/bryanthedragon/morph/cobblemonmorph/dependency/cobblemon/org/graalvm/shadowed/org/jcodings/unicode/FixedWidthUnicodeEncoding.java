
package org.graalvm.shadowed.org.jcodings.unicode;

import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.unicode.UnicodeEncoding;
import org.graalvm.shadowed.org.jcodings.util.Macros;

public abstract class FixedWidthUnicodeEncoding
extends UnicodeEncoding {
    protected final int shift;

    protected FixedWidthUnicodeEncoding(String name, int width) {
        super(name, width, width, (int[])null);
        this.shift = FixedWidthUnicodeEncoding.log2(width);
    }

    @Override
    public final int length(byte c) {
        return this.minLength;
    }

    @Override
    public int length(byte[] bytes, int p, int e) {
        if (e < p) {
            return Macros.CONSTRUCT_MBCLEN_INVALID();
        }
        if (e - p < 4) {
            return Macros.CONSTRUCT_MBCLEN_NEEDMORE(4 - e - p);
        }
        int c = this.mbcToCode(bytes, p, e);
        if (!Macros.UNICODE_VALID_CODEPOINT_P(c)) {
            return Macros.CONSTRUCT_MBCLEN_INVALID();
        }
        return Macros.CONSTRUCT_MBCLEN_CHARFOUND(4);
    }

    @Override
    public final int strLength(byte[] bytes, int p, int end2) {
        return end2 - p >>> this.shift;
    }

    @Override
    public final int strCodeAt(byte[] bytes, int p, int end2, int index) {
        return this.mbcToCode(bytes, p + (index << this.shift), end2);
    }

    @Override
    public final int codeToMbcLength(int code) {
        return this.minLength;
    }

    @Override
    public final int[] ctypeCodeRange(int ctype, IntHolder sbOut) {
        sbOut.value = 0;
        return super.ctypeCodeRange(ctype);
    }

    @Override
    public final int leftAdjustCharHead(byte[] bytes, int p, int s, int end2) {
        if (s <= p) {
            return s;
        }
        return s - (s - p) % this.maxLength;
    }

    @Override
    public final boolean isReverseMatchAllowed(byte[] bytes, int p, int end2) {
        return false;
    }

    private static int log2(int n) {
        int log = 0;
        while ((n >>>= 1) != 0) {
            ++log;
        }
        return log;
    }
}

