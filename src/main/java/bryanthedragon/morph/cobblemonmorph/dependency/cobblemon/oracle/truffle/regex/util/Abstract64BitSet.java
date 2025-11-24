
package com.oracle.truffle.regex.util;

import java.util.PrimitiveIterator;

public final class Abstract64BitSet {
    private static long toBit(int b) {
        return 1L << b;
    }

    public static boolean isEmpty(long bs) {
        return bs == 0L;
    }

    public static boolean isFull(long bs) {
        return bs == -1L;
    }

    public static int size(long bs) {
        return Long.bitCount(bs);
    }

    public static boolean get(long bs, int b) {
        return b < 64 && (bs & Abstract64BitSet.toBit(b)) != 0L;
    }

    public static long set(long bs, int b) {
        assert (b < 64);
        return bs | Abstract64BitSet.toBit(b);
    }

    public static long clear(long bs, int b) {
        assert (b < 64);
        return bs & (Abstract64BitSet.toBit(b) ^ 0xFFFFFFFFFFFFFFFFL);
    }

    public static boolean intersects(long bs1, long bs2) {
        return !Abstract64BitSet.isDisjoint(bs1, bs2);
    }

    public static boolean isDisjoint(long bs1, long bs2) {
        return (bs1 & bs2) == 0L;
    }

    public static boolean contains(long bs1, long bs2) {
        return (bs1 & bs2) == bs2;
    }

    public static int hashCode(long bs) {
        long h = 0x4D2L ^ bs;
        return (int)(h >> 32 ^ h);
    }

    public static boolean equals(long bs1, long bs2) {
        return bs1 == bs2;
    }

    public static PrimitiveIterator.OfInt iterator(long bs) {
        return new Abstract64BitSetIterator(bs);
    }

    private static final class Abstract64BitSetIterator
    implements PrimitiveIterator.OfInt {
        private long bs;
        private int i = -1;

        Abstract64BitSetIterator(long bs) {
            this.bs = bs;
        }

        @Override
        public boolean hasNext() {
            return this.bs != 0L;
        }

        @Override
        public int nextInt() {
            assert (this.hasNext());
            int trailingZeros = Long.numberOfTrailingZeros(this.bs);
            this.bs >>>= trailingZeros;
            this.bs >>>= 1;
            this.i += trailingZeros + 1;
            return this.i;
        }
    }
}

