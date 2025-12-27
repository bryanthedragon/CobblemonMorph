package com.oracle.truffle.regex.charset;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.chardata.CharacterSet;
import com.oracle.truffle.regex.tregex.string.Encodings;

public interface SortedListOfRanges extends CharacterSet {
   int getLo(int i);

   int getHi(int i);

   int size();

   void appendRangesTo(RangesBuffer buffer, int startIndex, int endIndex);

   default boolean isEmpty() {
      return this.size() == 0;
   }

   default boolean isSingle(int i) {
      return this.getLo(i) == this.getHi(i);
   }

   default int size(int i) {
      return this.getHi(i) - this.getLo(i) + 1;
   }

   default int sizeOfInverse(Encodings.Encoding encoding) {
      return this.isEmpty() ? 1 : (this.getMin() <= encoding.getMinValue() ? 0 : 1) + this.size() - (this.getMax() >= encoding.getMaxValue() ? 1 : 0);
   }

   default int getMin() {
      if (!<unrepresentable>.$assertionsDisabled && this.isEmpty()) {
         throw new AssertionError();
      } else {
         return this.getLo(0);
      }
   }

   default int getMax() {
      if (!<unrepresentable>.$assertionsDisabled && this.isEmpty()) {
         throw new AssertionError();
      } else {
         return this.getHi(this.size() - 1);
      }
   }

   default int inverseGetMin(Encodings.Encoding encoding) {
      if (<unrepresentable>.$assertionsDisabled || !this.isEmpty() && !this.matchesEverything(encoding)) {
         return this.getMin() == encoding.getMinValue() ? this.getHi(0) + 1 : encoding.getMinValue();
      } else {
         throw new AssertionError();
      }
   }

   default int inverseGetMax(Encodings.Encoding encoding) {
      if (<unrepresentable>.$assertionsDisabled || !this.isEmpty() && !this.matchesEverything(encoding)) {
         return this.getMax() == encoding.getMaxValue() ? this.getLo(this.size() - 1) - 1 : encoding.getMaxValue();
      } else {
         throw new AssertionError();
      }
   }

   static boolean contains(int aLo, int aHi, int bLo, int bHi) {
      return aLo <= bLo && aHi >= bHi;
   }

   default boolean contains(int ia, SortedListOfRanges o, int ib) {
      return contains(this.getLo(ia), this.getHi(ia), o.getLo(ib), o.getHi(ib));
   }

   default boolean contains(int ia, int bLo, int bHi) {
      return contains(this.getLo(ia), this.getHi(ia), bLo, bHi);
   }

   default boolean containedBy(int ia, int bLo, int bHi) {
      return contains(bLo, bHi, this.getLo(ia), this.getHi(ia));
   }

   static boolean intersects(int aLo, int aHi, int bLo, int bHi) {
      return aLo <= bHi && bLo <= aHi;
   }

   default boolean intersects(int ia, SortedListOfRanges o, int ib) {
      return intersects(this.getLo(ia), this.getHi(ia), o.getLo(ib), o.getHi(ib));
   }

   default boolean intersects(int ia, int bLo, int bHi) {
      return intersects(this.getLo(ia), this.getHi(ia), bLo, bHi);
   }

   static boolean leftOf(int aLo, int aHi, int bLo, int bHi) {
      return aHi < bLo;
   }

   static boolean leftOf(int aHi, int bLo) {
      return aHi < bLo;
   }

   default boolean leftOf(int ia, SortedListOfRanges o, int ib) {
      return leftOf(this.getHi(ia), o.getLo(ib));
   }

   default boolean leftOf(int ia, int bLo, int bHi) {
      return leftOf(this.getHi(ia), bLo);
   }

   static boolean rightOf(int aLo, int aHi, int bLo, int bHi) {
      return aLo > bHi;
   }

   static boolean rightOf(int aLo, int bHi) {
      return aLo > bHi;
   }

   default boolean rightOf(int ia, SortedListOfRanges o, int ib) {
      return rightOf(this.getLo(ia), o.getHi(ib));
   }

   default boolean rightOf(int ia, int bLo, int bHi) {
      return rightOf(this.getLo(ia), bHi);
   }

   static boolean adjacent(int aLo, int aHi, int bLo, int bHi) {
      return aHi + 1 == bLo || aLo - 1 == bHi;
   }

   default boolean adjacent(int ia, SortedListOfRanges o, int ib) {
      return adjacent(this.getLo(ia), this.getHi(ia), o.getLo(ib), o.getHi(ib));
   }

   default boolean adjacent(int ia, int bLo, int bHi) {
      return adjacent(this.getLo(ia), this.getHi(ia), bLo, bHi);
   }

   default boolean equal(int ia, SortedListOfRanges o, int ib) {
      return this.equal(ia, o.getLo(ib), o.getHi(ib));
   }

   default boolean equal(int ia, int bLo, int bHi) {
      return this.getLo(ia) == bLo && this.getHi(ia) == bHi;
   }

   default int binarySearch(int keyLo) {
      int low = 0;
      int high = this.size() - 1;

      while (low <= high) {
         int mid = low + high >>> 1;
         int midVal = this.getLo(mid);
         if (midVal < keyLo) {
            low = mid + 1;
         } else {
            if (midVal <= keyLo) {
               return mid;
            }

            high = mid - 1;
         }
      }

      return -(low + 1);
   }

   default boolean binarySearchExactMatch(int searchResult, SortedListOfRanges o, int ib) {
      return this.binarySearchExactMatch(searchResult, o.getLo(ib), o.getHi(ib));
   }

   default boolean binarySearchExactMatch(int searchResult, int bLo, int bHi) {
      return searchResult >= 0 && this.equal(searchResult, bLo, bHi);
   }

   default int binarySearchGetFirstIntersecting(int searchResult, SortedListOfRanges o, int ib) {
      return this.binarySearchGetFirstIntersecting(searchResult, o.getLo(ib), o.getHi(ib));
   }

   default int binarySearchGetFirstIntersecting(int searchResult, int bLo, int bHi) {
      return this.binarySearchGetFirstIntersectingOrAdjacent(searchResult, bLo, bHi, false);
   }

   default int binarySearchGetFirstIntersectingOrAdjacent(int searchResult, int bLo, int bHi) {
      return this.binarySearchGetFirstIntersectingOrAdjacent(searchResult, bLo, bHi, true);
   }

   default int binarySearchGetFirstIntersectingOrAdjacent(int searchResult, int oLo, int oHi, boolean includeAdjacent) {
      if (searchResult >= 0) {
         if (!<unrepresentable>.$assertionsDisabled && this.equal(searchResult, oLo, oHi)) {
            throw new AssertionError();
         } else {
            return searchResult;
         }
      } else {
         int insertionPoint = (searchResult + 1) * -1;
         return insertionPoint <= 0 || !this.intersects(insertionPoint - 1, oLo, oHi) && (!includeAdjacent || !this.adjacent(insertionPoint - 1, oLo, oHi))
            ? insertionPoint
            : insertionPoint - 1;
      }
   }

   default boolean binarySearchNoIntersectingFound(int firstIntersecting) {
      return firstIntersecting == this.size();
   }

   default void addRangeTo(RangesBuffer buffer, int i) {
      buffer.appendRange(this.getLo(i), this.getHi(i));
   }

   default boolean rangesAreSortedNonAdjacentAndDisjoint() {
      if (this.size() > 0 && this.getLo(0) > this.getHi(0)) {
         return false;
      } else {
         for (int i = 1; i < this.size(); i++) {
            if (this.getLo(i) > this.getHi(i) || !this.leftOf(i - 1, this, i) || this.intersects(i - 1, this, i) || this.adjacent(i - 1, this, i)) {
               return false;
            }
         }

         return true;
      }
   }

   default boolean rangesAreSortedAndDisjoint() {
      if (this.size() > 0 && this.getLo(0) > this.getHi(0)) {
         return false;
      } else {
         for (int i = 1; i < this.size(); i++) {
            if (this.getLo(i) > this.getHi(i) || !this.leftOf(i - 1, this, i) || this.intersects(i - 1, this, i)) {
               return false;
            }
         }

         return true;
      }
   }

   @Override
   default boolean contains(int codePoint) {
      int low = 0;
      int high = this.size() - 1;

      while (low <= high) {
         int mid = (low + high) / 2;
         if (codePoint < this.getLo(mid)) {
            high = mid - 1;
         } else {
            if (codePoint <= this.getHi(mid)) {
               return true;
            }

            low = mid + 1;
         }
      }

      return false;
   }

   default boolean contains(SortedListOfRanges o) {
      if (o.matchesNothing()) {
         return true;
      } else if (this.matchesNothing()) {
         return o.matchesNothing();
      } else {
         int ia = 0;
         int ib = 0;

         do {
            while (this.leftOf(ia, o, ib)) {
               if (++ia >= this.size()) {
                  return false;
               }
            }

            while (this.contains(ia, o, ib)) {
               if (++ib >= o.size()) {
                  return true;
               }
            }
         } while (!o.leftOf(ib, this, ia) && !this.intersects(ia, o, ib));

         return false;
      }
   }

   default boolean intersects(SortedListOfRanges o) {
      if (!this.matchesNothing() && !o.matchesNothing() && this.getHi(this.size() - 1) >= o.getLo(0) && o.getHi(o.size() - 1) >= this.getLo(0)) {
         SortedListOfRanges a = this;
         SortedListOfRanges b = o;
         if (this.size() > o.size()) {
            a = o;
            b = this;
         }

         for (int ia = 0; ia < a.size(); ia++) {
            int search = b.binarySearch(a.getLo(ia));
            if (b.binarySearchExactMatch(search, a, ia)) {
               return true;
            }

            int firstIntersection = b.binarySearchGetFirstIntersecting(search, a, ia);
            if (!b.binarySearchNoIntersectingFound(firstIntersection) && !b.rightOf(firstIntersection, a, ia)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   static void union(SortedListOfRanges a, SortedListOfRanges b, RangesBuffer target) {
      target.clear();
      int ia = 0;
      int ib = 0;

      while (ia < a.size() && ib < b.size()) {
         int iaInit = ia;

         while (ia < a.size() && a.leftOf(ia, b, ib) && !a.adjacent(ia, b, ib)) {
            ia++;
         }

         a.appendRangesTo(target, iaInit, ia);
         if (ia == a.size()) {
            break;
         }

         int ibInit = ib;

         while (ib < b.size() && b.leftOf(ib, a, ia) && !a.adjacent(ia, b, ib)) {
            ib++;
         }

         b.appendRangesTo(target, ibInit, ib);
         if (ib == b.size()) {
            break;
         }

         if (!a.intersects(ia, b, ib) && !a.adjacent(ia, b, ib)) {
            if (a.rightOf(ia, b, ib)) {
               b.addRangeTo(target, ib);
               ib++;
            } else {
               if (!<unrepresentable>.$assertionsDisabled && !b.rightOf(ib, a, ia)) {
                  throw new AssertionError();
               }

               a.addRangeTo(target, ia);
               ia++;
            }
         } else {
            int tmpLo = Math.min(a.getLo(ia), b.getLo(ib));
            int tmpHi = Math.max(a.getHi(ia), b.getHi(ib));
            ia++;
            ib++;

            while (true) {
               if (ia < a.size() && (a.intersects(ia, tmpLo, tmpHi) || a.adjacent(ia, tmpLo, tmpHi))) {
                  tmpLo = Math.min(a.getLo(ia), tmpLo);
                  tmpHi = Math.max(a.getHi(ia), tmpHi);
                  ia++;
               } else {
                  if (ib >= b.size() || !b.intersects(ib, tmpLo, tmpHi) && !b.adjacent(ib, tmpLo, tmpHi)) {
                     target.appendRange(tmpLo, tmpHi);
                     break;
                  }

                  tmpLo = Math.min(b.getLo(ib), tmpLo);
                  tmpHi = Math.max(b.getHi(ib), tmpHi);
                  ib++;
               }
            }
         }
      }

      if (ia < a.size()) {
         a.appendRangesTo(target, ia, a.size());
      }

      if (ib < b.size()) {
         b.appendRangesTo(target, ib, b.size());
      }
   }

   static void intersect(SortedListOfRanges a, SortedListOfRanges b, RangesBuffer target) {
      target.clear();

      for (int ia = 0; ia < a.size(); ia++) {
         int search = b.binarySearch(a.getLo(ia));
         if (b.binarySearchExactMatch(search, a, ia)) {
            a.addRangeTo(target, ia);
         } else {
            int firstIntersection = b.binarySearchGetFirstIntersecting(search, a, ia);

            for (int ib = firstIntersection; ib < b.size() && !b.rightOf(ib, a, ia); ib++) {
               if (!<unrepresentable>.$assertionsDisabled && !a.intersects(ia, b, ib)) {
                  throw new AssertionError();
               }

               target.appendRange(Math.max(a.getLo(ia), b.getLo(ib)), Math.min(a.getHi(ia), b.getHi(ib)));
            }
         }
      }
   }

   static void invert(SortedListOfRanges a, Encodings.Encoding encoding, RangesBuffer target) {
      target.clear();
      if (a.isEmpty()) {
         target.appendRange(encoding.getMinValue(), encoding.getMaxValue());
      } else {
         if (a.getMin() > encoding.getMinValue()) {
            target.appendRange(encoding.getMinValue(), a.getMin() - 1);
         }

         for (int i = 1; i < a.size(); i++) {
            target.appendRange(a.getHi(i - 1) + 1, a.getLo(i) - 1);
         }

         if (a.getMax() < encoding.getMaxValue()) {
            target.appendRange(a.getMax() + 1, encoding.getMaxValue());
         }
      }
   }

   default boolean matchesNothing() {
      return this.size() == 0;
   }

   default boolean matchesSomething() {
      return !this.matchesNothing();
   }

   default boolean matchesSingleChar() {
      return this.size() == 1 && this.isSingle(0);
   }

   default boolean matchesSingleAscii() {
      return this.matchesSingleChar() && this.getLo(0) < 128;
   }

   default boolean matchesMinAndMax(Encodings.Encoding encoding) {
      return this.matchesSomething() && this.getMin() == encoding.getMinValue() && this.getMax() == encoding.getMaxValue();
   }

   default boolean matches2CharsWith1BitDifference() {
      return !this.matchesNothing() && this.size() <= 2 && this.valueCountEquals(2) ? Integer.bitCount(this.getMin() ^ this.getMax()) == 1 : false;
   }

   default int valueCount() {
      int count = 0;

      for (int i = 0; i < this.size(); i++) {
         count += this.size(i);
      }

      return count;
   }

   default boolean valueCountEquals(int cmp) {
      int count = 0;

      for (int i = 0; i < this.size(); i++) {
         count += this.size(i);
         if (count > cmp) {
            return false;
         }
      }

      return count == cmp;
   }

   default boolean valueCountMax(int cmp) {
      int count = 0;

      for (int i = 0; i < this.size(); i++) {
         count += this.size(i);
         if (count > cmp) {
            return false;
         }
      }

      return count <= cmp;
   }

   default int inverseValueCount(Encodings.Encoding encoding) {
      return encoding.getMaxValue() - encoding.getMinValue() + 1 - this.valueCount();
   }

   default boolean matchesEverything(Encodings.Encoding encoding) {
      return this.size() == 1 && this.getLo(0) == encoding.getMinValue() && this.getHi(0) == encoding.getMaxValue();
   }

   default boolean equalsListOfRanges(SortedListOfRanges o) {
      if (o != null && this.size() == o.size()) {
         for (int i = 0; i < this.size(); i++) {
            if (!this.equal(i, o, i)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   default String defaultToString() {
      if (this.equals(Constants.DOT)) {
         return ".";
      } else if (this.equals(Constants.LINE_TERMINATOR)) {
         return "[\\r\\n\\u2028\\u2029]";
      } else if (this.equals(Constants.DIGITS)) {
         return "\\d";
      } else if (this.equals(Constants.NON_DIGITS)) {
         return "\\D";
      } else if (this.equals(Constants.WORD_CHARS)) {
         return "\\w";
      } else if (this.equals(Constants.NON_WORD_CHARS)) {
         return "\\W";
      } else if (this.equals(Constants.WHITE_SPACE)) {
         return "\\s";
      } else if (this.equals(Constants.NON_WHITE_SPACE)) {
         return "\\S";
      } else if (this.matchesNothing()) {
         return "[]";
      } else {
         return this.matchesSingleChar() ? Range.toString(this.getLo(0), this.getHi(0)) : "[" + this.rangesToString() + "]";
      }
   }

   @CompilerDirectives.TruffleBoundary
   default String rangesToString() {
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < this.size(); i++) {
         sb.append(Range.toString(this.getLo(i), this.getHi(i)));
      }

      return sb.toString();
   }

   @CompilerDirectives.TruffleBoundary
   default String inverseRangesToString(Encodings.Encoding encoding) {
      StringBuilder sb = new StringBuilder();
      if (this.matchesNothing()) {
         sb.append(Range.toString(encoding.getMinValue(), encoding.getMaxValue()));
         return sb.toString();
      } else {
         if (this.getLo(0) > encoding.getMinValue()) {
            sb.append(Range.toString(encoding.getMinValue(), this.getLo(0) - 1));
         }

         for (int ia = 1; ia < this.size(); ia++) {
            sb.append(Range.toString(this.getHi(ia - 1) + 1, this.getLo(ia) - 1));
         }

         if (this.getHi(this.size() - 1) < encoding.getMaxValue()) {
            sb.append(Range.toString(this.getHi(this.size() - 1) + 1, encoding.getMaxValue()));
         }

         return sb.toString();
      }
   }

   static {
      if (<unrepresentable>.$assertionsDisabled) {
      }
   }
}
