
package com.oracle.truffle.regex.charset;

import com.oracle.truffle.regex.charset.SortedListOfRanges;

public interface RangesBuffer
extends SortedListOfRanges {
    public void clear();

    public void appendRange(int var1, int var2);

    public void insertRange(int var1, int var2, int var3);

    public void replaceRanges(int var1, int var2, int var3, int var4);

    public <T extends RangesBuffer> T create();

    default public void addRange(int lo, int hi) {
        int search = this.binarySearch(lo);
        if (this.binarySearchExactMatch(search, lo, hi)) {
            return;
        }
        int firstIntersection = this.binarySearchGetFirstIntersectingOrAdjacent(search, lo, hi);
        if (this.binarySearchNoIntersectingFound(firstIntersection)) {
            this.appendRange(lo, hi);
        } else if (this.rightOf(firstIntersection, lo, hi) && !this.adjacent(firstIntersection, lo, hi)) {
            this.insertRange(firstIntersection, lo, hi);
        } else {
            int lastIntersection;
            int newLo = Math.min(lo, this.getLo(firstIntersection));
            for (lastIntersection = firstIntersection + 1; lastIntersection < this.size() && (this.intersects(lastIntersection, lo, hi) || this.adjacent(lastIntersection, lo, hi)); ++lastIntersection) {
            }
            int newHi = Math.max(hi, this.getHi(lastIntersection - 1));
            this.replaceRanges(firstIntersection, lastIntersection, newLo, newHi);
        }
    }
}

