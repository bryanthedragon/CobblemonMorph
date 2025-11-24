
package com.oracle.truffle.regex.util;

import com.oracle.truffle.regex.util.Abstract128BitSet;
import com.oracle.truffle.regex.util.Immutable128BitSet;
import java.util.PrimitiveIterator;

public final class Mutable128BitSet
extends Abstract128BitSet
implements Iterable<Integer> {
    private long lo;
    private long hi;

    public Mutable128BitSet() {
    }

    public Mutable128BitSet(long lo, long hi) {
        this.lo = lo;
        this.hi = hi;
    }

    public Mutable128BitSet copy() {
        return new Mutable128BitSet(this.lo, this.hi);
    }

    @Override
    public long getLo() {
        return this.lo;
    }

    @Override
    public long getHi() {
        return this.hi;
    }

    public void clear() {
        this.lo = 0L;
        this.hi = 0L;
    }

    public void set(int b) {
        assert (b < 128);
        if (b < 64) {
            this.lo |= Mutable128BitSet.toBit(b);
        } else {
            this.hi |= Mutable128BitSet.toBit(b);
        }
    }

    public void clear(int b) {
        assert (b < 128);
        if (b < 64) {
            this.lo &= Mutable128BitSet.toBit(b) ^ 0xFFFFFFFFFFFFFFFFL;
        } else {
            this.hi &= Mutable128BitSet.toBit(b) ^ 0xFFFFFFFFFFFFFFFFL;
        }
    }

    public boolean add(int b) {
        assert (b < 128);
        if (b < 64) {
            long old = this.lo;
            this.lo |= Mutable128BitSet.toBit(b);
            return this.lo != old;
        }
        long old = this.hi;
        this.hi |= Mutable128BitSet.toBit(b);
        return this.hi != old;
    }

    public boolean remove(int b) {
        assert (b < 128);
        if (b < 64) {
            long old = this.lo;
            this.lo &= Mutable128BitSet.toBit(b) ^ 0xFFFFFFFFFFFFFFFFL;
            return this.lo != old;
        }
        long old = this.hi;
        this.hi &= Mutable128BitSet.toBit(b) ^ 0xFFFFFFFFFFFFFFFFL;
        return this.hi != old;
    }

    public void setRange(int rangeLo, int rangeHi) {
        assert (rangeLo < 128 && rangeHi < 128);
        long bitRangeLo = -1L << rangeLo;
        long bitRangeHi = -1L >>> 63 - (rangeHi & 0x3F);
        if (rangeLo < 64) {
            if (rangeHi < 64) {
                this.lo |= bitRangeLo & bitRangeHi;
            } else {
                this.lo |= bitRangeLo;
                this.hi |= bitRangeHi;
            }
        } else {
            assert (rangeHi >= 64);
            this.hi |= bitRangeLo & bitRangeHi;
        }
    }

    public void invert() {
        this.lo ^= 0xFFFFFFFFFFFFFFFFL;
        this.hi ^= 0xFFFFFFFFFFFFFFFFL;
    }

    public void intersect(Mutable128BitSet other) {
        this.lo &= other.lo;
        this.hi &= other.hi;
    }

    public void subtract(Mutable128BitSet other) {
        this.lo &= other.lo ^ 0xFFFFFFFFFFFFFFFFL;
        this.hi &= other.hi ^ 0xFFFFFFFFFFFFFFFFL;
    }

    public void union(Immutable128BitSet other) {
        this.lo |= other.getLo();
        this.hi |= other.getHi();
    }

    public void union(Mutable128BitSet other) {
        this.lo |= other.lo;
        this.hi |= other.hi;
    }

    public boolean addAll(Mutable128BitSet other) {
        long oldLo = this.lo;
        long oldHi = this.hi;
        this.union(other);
        return this.lo != oldLo || this.hi != oldHi;
    }

    public boolean retainAll(Mutable128BitSet other) {
        long oldLo = this.lo;
        long oldHi = this.hi;
        this.intersect(other);
        return this.lo != oldLo || this.hi != oldHi;
    }

    public boolean removeAll(Mutable128BitSet other) {
        long oldLo = this.lo;
        long oldHi = this.hi;
        this.subtract(other);
        return this.lo != oldLo || this.hi != oldHi;
    }

    public Immutable128BitSet toImmutable() {
        return new Immutable128BitSet(this.lo, this.hi);
    }

    @Override
    public PrimitiveIterator.OfInt iterator() {
        return new Mutable128BitSetIterator(this, this.lo, this.hi);
    }

    static final class Mutable128BitSetIterator
    implements PrimitiveIterator.OfInt {
        private final Mutable128BitSet set;
        private long curWord;
        private long nextWord;
        private int i = -1;

        Mutable128BitSetIterator(Mutable128BitSet set2, long lo, long hi) {
            this.set = set2;
            this.curWord = lo;
            this.nextWord = hi;
        }

        @Override
        public boolean hasNext() {
            return this.curWord != 0L || this.nextWord != 0L;
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

        @Override
        public void remove() {
            this.set.clear(this.i);
        }
    }
}

