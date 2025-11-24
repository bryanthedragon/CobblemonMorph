
package com.oracle.truffle.regex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.Range;
import com.oracle.truffle.regex.util.BitSets;
import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public abstract class Abstract128BitSet
implements Iterable<Integer> {
    public abstract long getLo();

    public abstract long getHi();

    static long toBit(int b) {
        return 1L << b;
    }

    public boolean isEmpty() {
        return this.getLo() == 0L && this.getHi() == 0L;
    }

    public boolean isFull() {
        return this.getLo() == -1L && this.getHi() == -1L;
    }

    public int size() {
        return Long.bitCount(this.getLo()) + Long.bitCount(this.getHi());
    }

    public boolean get(int b) {
        return b < 128 && ((b < 64 ? this.getLo() : this.getHi()) & Abstract128BitSet.toBit(b)) != 0L;
    }

    public boolean intersects(Abstract128BitSet other) {
        return !this.isDisjoint(other);
    }

    public boolean isDisjoint(Abstract128BitSet other) {
        return (this.getLo() & other.getLo() | this.getHi() & other.getHi()) == 0L;
    }

    public boolean contains(Abstract128BitSet other) {
        return (this.getLo() & other.getLo()) == other.getLo() && (this.getHi() & other.getHi()) == other.getHi();
    }

    public int hashCode() {
        long h = 0x4D2L ^ this.getLo() ^ this.getHi() << 1;
        return (int)(h >> 32 ^ h);
    }

    public boolean equals(Object obj) {
        return obj instanceof Abstract128BitSet && this.getLo() == ((Abstract128BitSet)obj).getLo() && this.getHi() == ((Abstract128BitSet)obj).getHi();
    }

    public boolean matches2CharsWith1BitDifference() {
        int c2;
        if (this.size() != 2) {
            return false;
        }
        int c1 = this.getMin();
        return Integer.bitCount(c1 ^ (c2 = this.getMax())) == 1;
    }

    public boolean isSingleRange() {
        if (this.isEmpty()) {
            return false;
        }
        if (this.getHi() == 0L) {
            return Abstract128BitSet.isSingleRange(this.getLo());
        }
        if (this.getLo() == 0L) {
            return Abstract128BitSet.isSingleRange(this.getHi());
        }
        int rangeLo = Long.numberOfTrailingZeros(this.getLo());
        int rangeHi = Long.numberOfLeadingZeros(this.getHi());
        return this.getLo() == -1L << rangeLo && this.getHi() == -1L >>> rangeHi;
    }

    private static boolean isSingleRange(long bs) {
        int rangeHi;
        int rangeLo = Long.numberOfTrailingZeros(bs);
        long bitRange = -1L << rangeLo & -1L >>> (rangeHi = Long.numberOfLeadingZeros(bs));
        return bs == bitRange;
    }

    public int getMin() {
        assert (!this.isEmpty());
        return this.getLo() == 0L ? Long.numberOfTrailingZeros(this.getHi()) + 64 : Long.numberOfTrailingZeros(this.getLo());
    }

    public int getMax() {
        assert (!this.isEmpty());
        return this.getHi() == 0L ? 63 - Long.numberOfLeadingZeros(this.getLo()) : 127 - Long.numberOfLeadingZeros(this.getHi());
    }

    public int getMinInverse() {
        assert (!this.isFull());
        return this.getLo() == -1L ? Long.numberOfTrailingZeros(this.getHi() ^ 0xFFFFFFFFFFFFFFFFL) + 64 : Long.numberOfTrailingZeros(this.getLo() ^ 0xFFFFFFFFFFFFFFFFL);
    }

    public PrimitiveIterator.OfInt iterator() {
        return new Abstract128BitSetIterator(this.getLo(), this.getHi());
    }

    public int numberOfRanges() {
        int n = 0;
        Iterator<Range> it = this.rangesIterator();
        while (it.hasNext()) {
            it.next();
            ++n;
        }
        return n;
    }

    public Iterator<Range> rangesIterator() {
        return new Abstract128BitSetRangesIterator(this.getLo(), this.getHi());
    }

    @CompilerDirectives.TruffleBoundary
    public Spliterator.OfInt spliterator() {
        return Spliterators.spliteratorUnknownSize(this.iterator(), 277);
    }

    @CompilerDirectives.TruffleBoundary
    public IntStream stream() {
        return StreamSupport.intStream(this.spliterator(), false);
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return BitSets.toString(this);
    }

    @CompilerDirectives.TruffleBoundary
    public String dumpRaw() {
        return this.isEmpty() ? "Immutable128BitSet.getEmpty()" : String.format("0x%016xL, 0x%016xL", this.getLo(), this.getHi());
    }

    static final class Abstract128BitSetRangesIterator
    implements Iterator<Range> {
        private long curWord;
        private long nextWord;
        private int lastHi = 0;

        Abstract128BitSetRangesIterator(long lo, long hi) {
            if (lo == 0L) {
                this.lastHi = 64;
                this.curWord = hi;
                this.nextWord = 0L;
            } else {
                this.curWord = lo;
                this.nextWord = hi;
            }
        }

        @Override
        public boolean hasNext() {
            return this.curWord != 0L;
        }

        @Override
        public Range next() {
            int hi;
            assert (this.curWord != 0L);
            int trailingZeros = Long.numberOfTrailingZeros(this.curWord);
            this.curWord >>>= trailingZeros;
            int lo = this.lastHi + trailingZeros;
            int trailingOnes = Long.numberOfTrailingZeros(this.curWord ^ 0xFFFFFFFFFFFFFFFFL);
            this.curWord >>>= trailingOnes - 1;
            this.curWord >>>= 1;
            this.lastHi = hi = lo + trailingOnes;
            if (this.curWord == 0L) {
                this.lastHi = 64;
                this.curWord = this.nextWord;
                this.nextWord = 0L;
                if (hi == 64 && (this.curWord & 1L) != 0L) {
                    int trailingOnesNext = Long.numberOfTrailingZeros(this.curWord ^ 0xFFFFFFFFFFFFFFFFL);
                    this.curWord >>>= trailingOnesNext - 1;
                    this.curWord >>>= 1;
                    this.lastHi = hi += trailingOnesNext;
                }
            }
            return new Range(lo, hi - 1);
        }
    }

    static final class Abstract128BitSetIterator
    implements PrimitiveIterator.OfInt {
        private long curWord;
        private long nextWord;
        private int i = -1;

        Abstract128BitSetIterator(long lo, long hi) {
            this.curWord = lo;
            this.nextWord = hi;
        }

        @Override
        public boolean hasNext() {
            return (this.curWord | this.nextWord) != 0L;
        }

        @Override
        public int nextInt() {
            assert (this.hasNext());
            if (this.curWord == 0L) {
                this.i = 63;
                this.curWord = this.nextWord;
                this.nextWord = 0L;
            }
            int trailingZeros = Long.numberOfTrailingZeros(this.curWord);
            this.curWord >>>= trailingZeros;
            this.curWord >>>= 1;
            this.i += trailingZeros + 1;
            return this.i;
        }
    }
}

