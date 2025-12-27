package com.oracle.truffle.regex.charset;

public interface RangesBuffer extends SortedListOfRanges {
   void clear();

   void appendRange(int lo, int hi);

   void insertRange(int index, int lo, int hi);

   void replaceRanges(int fromIndex, int toIndex, int lo, int hi);

   <T extends RangesBuffer> T create();

   default void addRange(int lo, int hi) {
      int search = this.binarySearch(lo);
      if (!this.binarySearchExactMatch(search, lo, hi)) {
         int firstIntersection = this.binarySearchGetFirstIntersectingOrAdjacent(search, lo, hi);
         if (this.binarySearchNoIntersectingFound(firstIntersection)) {
            this.appendRange(lo, hi);
         } else if (this.rightOf(firstIntersection, lo, hi) && !this.adjacent(firstIntersection, lo, hi)) {
            this.insertRange(firstIntersection, lo, hi);
         } else {
            int newLo = Math.min(lo, this.getLo(firstIntersection));
            int lastIntersection = firstIntersection + 1;

            while (lastIntersection < this.size() && (this.intersects(lastIntersection, lo, hi) || this.adjacent(lastIntersection, lo, hi))) {
               lastIntersection++;
            }

            int newHi = Math.max(hi, this.getHi(lastIntersection - 1));
            this.replaceRanges(firstIntersection, lastIntersection, newLo, newHi);
         }
      }
   }
}
