package com.oracle.truffle.regex.tregex.matchers;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.CharMatchers;
import com.oracle.truffle.regex.tregex.util.MathUtil;

public final class RangeTreeMatcher extends InvertibleCharMatcher {
   private static final int EXPLODE_THRESHOLD = 16;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final int[] sortedRanges;

   public static RangeTreeMatcher create(boolean invert, int[] ranges) {
      return new RangeTreeMatcher(invert, ranges);
   }

   RangeTreeMatcher(boolean invert, int[] sortedRanges) {
      super(invert);
      this.sortedRanges = sortedRanges;
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
            return c > this.sortedRanges[(mid << 1) + 1] ? this.matchTree(mid + 1, toIndex, c) : this.result(true);
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
               return this.result(true);
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
      return "tree " + this.modifiersToString() + "[" + CharMatchers.rangesToString(this.sortedRanges) + "]";
   }
}
