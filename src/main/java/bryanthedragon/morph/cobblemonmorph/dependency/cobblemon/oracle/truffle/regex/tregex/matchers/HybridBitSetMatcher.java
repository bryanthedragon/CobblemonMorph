package com.oracle.truffle.regex.tregex.matchers;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.CompressedCodePointSet;
import com.oracle.truffle.regex.charset.Range;
import com.oracle.truffle.regex.tregex.util.MathUtil;
import com.oracle.truffle.regex.util.BitSets;

public final class HybridBitSetMatcher extends InvertibleCharMatcher {
   private static final int EXPLODE_THRESHOLD = 16;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final int[] sortedRanges;
   @CompilerDirectives.CompilationFinal(dimensions = 2)
   private final long[][] bitSets;

   HybridBitSetMatcher(boolean invert, int[] sortedRanges, long[][] bitSets) {
      super(invert);
      this.sortedRanges = sortedRanges;
      this.bitSets = bitSets;

      assert bitSets.length == sortedRanges.length / 2;
   }

   public static HybridBitSetMatcher create(boolean invert, CompressedCodePointSet ccps) {
      return new HybridBitSetMatcher(invert, ccps.getRanges(), ccps.getBitSets());
   }

   @Override
   public boolean match(int c) {
      CompilerAsserts.partialEvaluationConstant(this);
      return this.sortedRanges.length / 2 > 16 ? this.matchLoop(c) : this.matchTree(0, (this.sortedRanges.length >>> 1) - 1, c);
   }

   private boolean matchTree(int fromIndex, int toIndex, int c) {
      CompilerAsserts.partialEvaluationConstant(fromIndex);
      CompilerAsserts.partialEvaluationConstant(toIndex);
      if (fromIndex > toIndex) {
         return this.result(false);
      } else {
         int mid = fromIndex + toIndex >>> 1;
         CompilerAsserts.partialEvaluationConstant(mid);
         if (c < this.sortedRanges[mid << 1]) {
            return this.matchTree(fromIndex, mid - 1, c);
         } else {
            return c > this.sortedRanges[(mid << 1) + 1]
               ? this.matchTree(mid + 1, toIndex, c)
               : this.result(this.bitSets[mid] == null || BitSets.get(this.bitSets[mid], lowByte(c)));
         }
      }
   }

   @CompilerDirectives.TruffleBoundary(allowInlining = true)
   private boolean matchLoop(final int c) {
      int fromIndex = 0;
      int toIndex = (this.sortedRanges.length >>> 1) - 1;

      while (fromIndex <= toIndex) {
         int mid = fromIndex + toIndex >>> 1;
         if (c < this.sortedRanges[mid << 1]) {
            toIndex = mid - 1;
         } else {
            if (c <= this.sortedRanges[(mid << 1) + 1]) {
               return this.result(this.bitSets[mid] == null || BitSets.get(this.bitSets[mid], lowByte(c)));
            }

            fromIndex = mid + 1;
         }
      }

      return this.result(false);
   }

   @Override
   public int estimatedCost() {
      return 2 * (MathUtil.log2ceil(this.sortedRanges.length / 2) - 1);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("hybrid ").append(this.modifiersToString()).append("[");

      for (int i = 0; i < this.sortedRanges.length; i += 2) {
         if (this.bitSets[i / 2] == null) {
            sb.append(Range.toString(this.sortedRanges[i], this.sortedRanges[i + 1]));
         } else {
            sb.append("[range: ")
               .append(Range.toString(this.sortedRanges[i], this.sortedRanges[i + 1]))
               .append(", bs: ")
               .append(BitSets.toString(this.bitSets[i / 2]))
               .append("]");
         }
      }

      return sb.append("]").toString();
   }
}
