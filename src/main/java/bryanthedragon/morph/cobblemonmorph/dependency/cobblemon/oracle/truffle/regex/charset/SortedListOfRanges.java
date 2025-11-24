
package com.oracle.truffle.regex.charset;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.chardata.CharacterSet;
import com.oracle.truffle.regex.charset.Constants;
import com.oracle.truffle.regex.charset.Range;
import com.oracle.truffle.regex.charset.RangesBuffer;
import com.oracle.truffle.regex.tregex.string.Encodings;

public interface SortedListOfRanges
extends CharacterSet {
    public int getLo(int var1);

    public int getHi(int var1);

    public int size();

    public void appendRangesTo(RangesBuffer var1, int var2, int var3);

    default public boolean isEmpty() {
        return this.size() == 0;
    }

    default public boolean isSingle(int i) {
        return this.getLo(i) == this.getHi(i);
    }

    default public int size(int i) {
        return this.getHi(i) - this.getLo(i) + 1;
    }

    default public int sizeOfInverse(Encodings.Encoding encoding) {
        if (this.isEmpty()) {
            return 1;
        }
        return (this.getMin() <= encoding.getMinValue() ? 0 : 1) + this.size() - (this.getMax() >= encoding.getMaxValue() ? 1 : 0);
    }

    default public int getMin() {
        if (!1.$assertionsDisabled && this.isEmpty()) {
            throw new AssertionError();
        }
        return this.getLo(0);
    }

    default public int getMax() {
        if (!1.$assertionsDisabled && this.isEmpty()) {
            throw new AssertionError();
        }
        return this.getHi(this.size() - 1);
    }

    default public int inverseGetMin(Encodings.Encoding encoding) {
        if (!1.$assertionsDisabled && (this.isEmpty() || this.matchesEverything(encoding))) {
            throw new AssertionError();
        }
        return this.getMin() == encoding.getMinValue() ? this.getHi(0) + 1 : encoding.getMinValue();
    }

    default public int inverseGetMax(Encodings.Encoding encoding) {
        if (!1.$assertionsDisabled && (this.isEmpty() || this.matchesEverything(encoding))) {
            throw new AssertionError();
        }
        return this.getMax() == encoding.getMaxValue() ? this.getLo(this.size() - 1) - 1 : encoding.getMaxValue();
    }

    public static boolean contains(int aLo, int aHi, int bLo, int bHi) {
        return aLo <= bLo && aHi >= bHi;
    }

    default public boolean contains(int ia, SortedListOfRanges o, int ib) {
        return SortedListOfRanges.contains(this.getLo(ia), this.getHi(ia), o.getLo(ib), o.getHi(ib));
    }

    default public boolean contains(int ia, int bLo, int bHi) {
        return SortedListOfRanges.contains(this.getLo(ia), this.getHi(ia), bLo, bHi);
    }

    default public boolean containedBy(int ia, int bLo, int bHi) {
        return SortedListOfRanges.contains(bLo, bHi, this.getLo(ia), this.getHi(ia));
    }

    public static boolean intersects(int aLo, int aHi, int bLo, int bHi) {
        return aLo <= bHi && bLo <= aHi;
    }

    default public boolean intersects(int ia, SortedListOfRanges o, int ib) {
        return SortedListOfRanges.intersects(this.getLo(ia), this.getHi(ia), o.getLo(ib), o.getHi(ib));
    }

    default public boolean intersects(int ia, int bLo, int bHi) {
        return SortedListOfRanges.intersects(this.getLo(ia), this.getHi(ia), bLo, bHi);
    }

    public static boolean leftOf(int aLo, int aHi, int bLo, int bHi) {
        return aHi < bLo;
    }

    public static boolean leftOf(int aHi, int bLo) {
        return aHi < bLo;
    }

    default public boolean leftOf(int ia, SortedListOfRanges o, int ib) {
        return SortedListOfRanges.leftOf(this.getHi(ia), o.getLo(ib));
    }

    default public boolean leftOf(int ia, int bLo, int bHi) {
        return SortedListOfRanges.leftOf(this.getHi(ia), bLo);
    }

    public static boolean rightOf(int aLo, int aHi, int bLo, int bHi) {
        return aLo > bHi;
    }

    public static boolean rightOf(int aLo, int bHi) {
        return aLo > bHi;
    }

    default public boolean rightOf(int ia, SortedListOfRanges o, int ib) {
        return SortedListOfRanges.rightOf(this.getLo(ia), o.getHi(ib));
    }

    default public boolean rightOf(int ia, int bLo, int bHi) {
        return SortedListOfRanges.rightOf(this.getLo(ia), bHi);
    }

    public static boolean adjacent(int aLo, int aHi, int bLo, int bHi) {
        return aHi + 1 == bLo || aLo - 1 == bHi;
    }

    default public boolean adjacent(int ia, SortedListOfRanges o, int ib) {
        return SortedListOfRanges.adjacent(this.getLo(ia), this.getHi(ia), o.getLo(ib), o.getHi(ib));
    }

    default public boolean adjacent(int ia, int bLo, int bHi) {
        return SortedListOfRanges.adjacent(this.getLo(ia), this.getHi(ia), bLo, bHi);
    }

    default public boolean equal(int ia, SortedListOfRanges o, int ib) {
        return this.equal(ia, o.getLo(ib), o.getHi(ib));
    }

    default public boolean equal(int ia, int bLo, int bHi) {
        return this.getLo(ia) == bLo && this.getHi(ia) == bHi;
    }

    default public int binarySearch(int keyLo) {
        int low = 0;
        int high = this.size() - 1;
        while (low <= high) {
            int mid = low + high >>> 1;
            int midVal = this.getLo(mid);
            if (midVal < keyLo) {
                low = mid + 1;
                continue;
            }
            if (midVal > keyLo) {
                high = mid - 1;
                continue;
            }
            return mid;
        }
        return -(low + 1);
    }

    default public boolean binarySearchExactMatch(int searchResult, SortedListOfRanges o, int ib) {
        return this.binarySearchExactMatch(searchResult, o.getLo(ib), o.getHi(ib));
    }

    default public boolean binarySearchExactMatch(int searchResult, int bLo, int bHi) {
        return searchResult >= 0 && this.equal(searchResult, bLo, bHi);
    }

    default public int binarySearchGetFirstIntersecting(int searchResult, SortedListOfRanges o, int ib) {
        return this.binarySearchGetFirstIntersecting(searchResult, o.getLo(ib), o.getHi(ib));
    }

    default public int binarySearchGetFirstIntersecting(int searchResult, int bLo, int bHi) {
        return this.binarySearchGetFirstIntersectingOrAdjacent(searchResult, bLo, bHi, false);
    }

    default public int binarySearchGetFirstIntersectingOrAdjacent(int searchResult, int bLo, int bHi) {
        return this.binarySearchGetFirstIntersectingOrAdjacent(searchResult, bLo, bHi, true);
    }

    default public int binarySearchGetFirstIntersectingOrAdjacent(int searchResult, int oLo, int oHi, boolean includeAdjacent) {
        if (searchResult >= 0) {
            if (!1.$assertionsDisabled && this.equal(searchResult, oLo, oHi)) {
                throw new AssertionError();
            }
            return searchResult;
        }
        int insertionPoint = (searchResult + 1) * -1;
        if (insertionPoint > 0 && (this.intersects(insertionPoint - 1, oLo, oHi) || includeAdjacent && this.adjacent(insertionPoint - 1, oLo, oHi))) {
            return insertionPoint - 1;
        }
        return insertionPoint;
    }

    default public boolean binarySearchNoIntersectingFound(int firstIntersecting) {
        return firstIntersecting == this.size();
    }

    default public void addRangeTo(RangesBuffer buffer, int i) {
        buffer.appendRange(this.getLo(i), this.getHi(i));
    }

    default public boolean rangesAreSortedNonAdjacentAndDisjoint() {
        if (this.size() > 0 && this.getLo(0) > this.getHi(0)) {
            return false;
        }
        for (int i = 1; i < this.size(); ++i) {
            if (this.getLo(i) <= this.getHi(i) && this.leftOf(i - 1, this, i) && !this.intersects(i - 1, this, i) && !this.adjacent(i - 1, this, i)) continue;
            return false;
        }
        return true;
    }

    default public boolean rangesAreSortedAndDisjoint() {
        if (this.size() > 0 && this.getLo(0) > this.getHi(0)) {
            return false;
        }
        for (int i = 1; i < this.size(); ++i) {
            if (this.getLo(i) <= this.getHi(i) && this.leftOf(i - 1, this, i) && !this.intersects(i - 1, this, i)) continue;
            return false;
        }
        return true;
    }

    @Override
    default public boolean contains(int codePoint) {
        int low = 0;
        int high = this.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (codePoint < this.getLo(mid)) {
                high = mid - 1;
                continue;
            }
            if (codePoint > this.getHi(mid)) {
                low = mid + 1;
                continue;
            }
            return true;
        }
        return false;
    }

    default public boolean contains(SortedListOfRanges o) {
        if (o.matchesNothing()) {
            return true;
        }
        if (this.matchesNothing()) {
            return o.matchesNothing();
        }
        int ia = 0;
        int ib = 0;
        while (true) {
            if (this.leftOf(ia, o, ib)) {
                if (++ia < this.size()) continue;
                return false;
            }
            while (this.contains(ia, o, ib)) {
                if (++ib < o.size()) continue;
                return true;
            }
            if (o.leftOf(ib, this, ia) || this.intersects(ia, o, ib)) break;
        }
        return false;
    }

    default public boolean intersects(SortedListOfRanges o) {
        if (this.matchesNothing() || o.matchesNothing() || this.getHi(this.size() - 1) < o.getLo(0) || o.getHi(o.size() - 1) < this.getLo(0)) {
            return false;
        }
        SortedListOfRanges a = this;
        SortedListOfRanges b = o;
        if (this.size() > o.size()) {
            a = o;
            b = this;
        }
        for (int ia = 0; ia < a.size(); ++ia) {
            int search = b.binarySearch(a.getLo(ia));
            if (b.binarySearchExactMatch(search, a, ia)) {
                return true;
            }
            int firstIntersection = b.binarySearchGetFirstIntersecting(search, a, ia);
            if (b.binarySearchNoIntersectingFound(firstIntersection) || b.rightOf(firstIntersection, a, ia)) continue;
            return true;
        }
        return false;
    }

    public static void union(SortedListOfRanges a, SortedListOfRanges b, RangesBuffer target) {
        target.clear();
        int ia = 0;
        int ib = 0;
        while (ia < a.size() && ib < b.size()) {
            int iaInit = ia;
            while (ia < a.size() && a.leftOf(ia, b, ib) && !a.adjacent(ia, b, ib)) {
                ++ia;
            }
            a.appendRangesTo(target, iaInit, ia);
            if (ia == a.size()) break;
            int ibInit = ib;
            while (ib < b.size() && b.leftOf(ib, a, ia) && !a.adjacent(ia, b, ib)) {
                ++ib;
            }
            b.appendRangesTo(target, ibInit, ib);
            if (ib == b.size()) break;
            if (a.intersects(ia, b, ib) || a.adjacent(ia, b, ib)) {
                int tmpLo = Math.min(a.getLo(ia), b.getLo(ib));
                int tmpHi = Math.max(a.getHi(ia), b.getHi(ib));
                ++ia;
                ++ib;
                while (true) {
                    if (ia < a.size() && (a.intersects(ia, tmpLo, tmpHi) || a.adjacent(ia, tmpLo, tmpHi))) {
                        tmpLo = Math.min(a.getLo(ia), tmpLo);
                        tmpHi = Math.max(a.getHi(ia), tmpHi);
                        ++ia;
                        continue;
                    }
                    if (ib >= b.size() || !b.intersects(ib, tmpLo, tmpHi) && !b.adjacent(ib, tmpLo, tmpHi)) break;
                    tmpLo = Math.min(b.getLo(ib), tmpLo);
                    tmpHi = Math.max(b.getHi(ib), tmpHi);
                    ++ib;
                }
                target.appendRange(tmpLo, tmpHi);
                continue;
            }
            if (a.rightOf(ia, b, ib)) {
                b.addRangeTo(target, ib);
                ++ib;
                continue;
            }
            if (!1.$assertionsDisabled && !b.rightOf(ib, a, ia)) {
                throw new AssertionError();
            }
            a.addRangeTo(target, ia);
            ++ia;
        }
        if (ia < a.size()) {
            a.appendRangesTo(target, ia, a.size());
        }
        if (ib < b.size()) {
            b.appendRangesTo(target, ib, b.size());
        }
    }

    public static void intersect(SortedListOfRanges a, SortedListOfRanges b, RangesBuffer target) {
        target.clear();
        for (int ia = 0; ia < a.size(); ++ia) {
            int firstIntersection;
            int search = b.binarySearch(a.getLo(ia));
            if (b.binarySearchExactMatch(search, a, ia)) {
                a.addRangeTo(target, ia);
                continue;
            }
            for (int ib = firstIntersection = b.binarySearchGetFirstIntersecting(search, a, ia); ib < b.size() && !b.rightOf(ib, a, ia); ++ib) {
                if (!1.$assertionsDisabled && !a.intersects(ia, b, ib)) {
                    throw new AssertionError();
                }
                target.appendRange(Math.max(a.getLo(ia), b.getLo(ib)), Math.min(a.getHi(ia), b.getHi(ib)));
            }
        }
    }

    public static void invert(SortedListOfRanges a, Encodings.Encoding encoding, RangesBuffer target) {
        target.clear();
        if (a.isEmpty()) {
            target.appendRange(encoding.getMinValue(), encoding.getMaxValue());
            return;
        }
        if (a.getMin() > encoding.getMinValue()) {
            target.appendRange(encoding.getMinValue(), a.getMin() - 1);
        }
        for (int i = 1; i < a.size(); ++i) {
            target.appendRange(a.getHi(i - 1) + 1, a.getLo(i) - 1);
        }
        if (a.getMax() < encoding.getMaxValue()) {
            target.appendRange(a.getMax() + 1, encoding.getMaxValue());
        }
    }

    default public boolean matchesNothing() {
        return this.size() == 0;
    }

    default public boolean matchesSomething() {
        return !this.matchesNothing();
    }

    default public boolean matchesSingleChar() {
        return this.size() == 1 && this.isSingle(0);
    }

    default public boolean matchesSingleAscii() {
        return this.matchesSingleChar() && this.getLo(0) < 128;
    }

    default public boolean matchesMinAndMax(Encodings.Encoding encoding) {
        return this.matchesSomething() && this.getMin() == encoding.getMinValue() && this.getMax() == encoding.getMaxValue();
    }

    default public boolean matches2CharsWith1BitDifference() {
        if (this.matchesNothing() || this.size() > 2 || !this.valueCountEquals(2)) {
            return false;
        }
        return Integer.bitCount(this.getMin() ^ this.getMax()) == 1;
    }

    default public int valueCount() {
        int count = 0;
        for (int i = 0; i < this.size(); ++i) {
            count += this.size(i);
        }
        return count;
    }

    default public boolean valueCountEquals(int cmp) {
        int count = 0;
        for (int i = 0; i < this.size(); ++i) {
            if ((count += this.size(i)) <= cmp) continue;
            return false;
        }
        return count == cmp;
    }

    default public boolean valueCountMax(int cmp) {
        int count = 0;
        for (int i = 0; i < this.size(); ++i) {
            if ((count += this.size(i)) <= cmp) continue;
            return false;
        }
        return count <= cmp;
    }

    default public int inverseValueCount(Encodings.Encoding encoding) {
        return encoding.getMaxValue() - encoding.getMinValue() + 1 - this.valueCount();
    }

    default public boolean matchesEverything(Encodings.Encoding encoding) {
        return this.size() == 1 && this.getLo(0) == encoding.getMinValue() && this.getHi(0) == encoding.getMaxValue();
    }

    default public boolean equalsListOfRanges(SortedListOfRanges o) {
        if (o == null || this.size() != o.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); ++i) {
            if (this.equal(i, o, i)) continue;
            return false;
        }
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    default public String defaultToString() {
        if (this.equals(Constants.DOT)) {
            return ".";
        }
        if (this.equals(Constants.LINE_TERMINATOR)) {
            return "[\\r\\n\\u2028\\u2029]";
        }
        if (this.equals(Constants.DIGITS)) {
            return "\\d";
        }
        if (this.equals(Constants.NON_DIGITS)) {
            return "\\D";
        }
        if (this.equals(Constants.WORD_CHARS)) {
            return "\\w";
        }
        if (this.equals(Constants.NON_WORD_CHARS)) {
            return "\\W";
        }
        if (this.equals(Constants.WHITE_SPACE)) {
            return "\\s";
        }
        if (this.equals(Constants.NON_WHITE_SPACE)) {
            return "\\S";
        }
        if (this.matchesNothing()) {
            return "[]";
        }
        if (this.matchesSingleChar()) {
            return Range.toString(this.getLo(0), this.getHi(0));
        }
        return "[" + this.rangesToString() + "]";
    }

    @CompilerDirectives.TruffleBoundary
    default public String rangesToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.size(); ++i) {
            sb.append(Range.toString(this.getLo(i), this.getHi(i)));
        }
        return sb.toString();
    }

    @CompilerDirectives.TruffleBoundary
    default public String inverseRangesToString(Encodings.Encoding encoding) {
        StringBuilder sb = new StringBuilder();
        if (this.matchesNothing()) {
            sb.append(Range.toString(encoding.getMinValue(), encoding.getMaxValue()));
            return sb.toString();
        }
        if (this.getLo(0) > encoding.getMinValue()) {
            sb.append(Range.toString(encoding.getMinValue(), this.getLo(0) - 1));
        }
        for (int ia = 1; ia < this.size(); ++ia) {
            sb.append(Range.toString(this.getHi(ia - 1) + 1, this.getLo(ia) - 1));
        }
        if (this.getHi(this.size() - 1) < encoding.getMaxValue()) {
            sb.append(Range.toString(this.getHi(this.size() - 1) + 1, encoding.getMaxValue()));
        }
        return sb.toString();
    }

    static {
        if (1.$assertionsDisabled) {
            // empty if block
        }
    }
}

