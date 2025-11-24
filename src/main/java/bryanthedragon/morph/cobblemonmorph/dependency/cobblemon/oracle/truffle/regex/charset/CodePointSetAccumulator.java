
package com.oracle.truffle.regex.charset;

import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.charset.Range;
import com.oracle.truffle.regex.charset.SortedListOfRanges;
import com.oracle.truffle.regex.tregex.buffer.IntRangesBuffer;
import com.oracle.truffle.regex.tregex.string.Encodings;
import java.util.Iterator;

public class CodePointSetAccumulator
implements Iterable<Range> {
    private IntRangesBuffer acc = new IntRangesBuffer();
    private IntRangesBuffer tmp;

    public IntRangesBuffer get() {
        return this.acc;
    }

    public IntRangesBuffer getTmp() {
        if (this.tmp == null) {
            this.tmp = new IntRangesBuffer();
        }
        return this.tmp;
    }

    public void addRange(int lo, int hi) {
        this.acc.addRange(lo, hi);
    }

    public void addCodePoint(int cp) {
        this.addRange(cp, cp);
    }

    public void appendRange(Range r) {
        this.appendRange(r.lo, r.hi);
    }

    public void appendRange(int lo, int hi) {
        this.acc.appendRange(lo, hi);
    }

    public void addSet(SortedListOfRanges set2) {
        IntRangesBuffer t = this.getTmp();
        this.tmp = this.acc;
        this.acc = t;
        SortedListOfRanges.union(this.tmp, set2, this.acc);
    }

    public void clear() {
        this.acc.clear();
    }

    public boolean isEmpty() {
        return this.acc.isEmpty();
    }

    public boolean matchesSingleChar() {
        return this.acc.matchesSingleChar();
    }

    public void copyTo(CodePointSetAccumulator other) {
        other.clear();
        this.acc.appendRangesTo(other.acc, 0, this.acc.size());
    }

    public CodePointSet toCodePointSet() {
        return CodePointSet.create(this.acc);
    }

    public void invert(Encodings.Encoding encoding) {
        IntRangesBuffer t = this.getTmp();
        this.tmp = this.acc;
        this.acc = t;
        SortedListOfRanges.invert(this.tmp, encoding, this.acc);
    }

    public void intersectWith(SortedListOfRanges other) {
        IntRangesBuffer t = this.getTmp();
        this.tmp = this.acc;
        this.acc = t;
        SortedListOfRanges.intersect(this.tmp, other, this.acc);
    }

    @Override
    public Iterator<Range> iterator() {
        return this.acc.rangesIterator();
    }
}

