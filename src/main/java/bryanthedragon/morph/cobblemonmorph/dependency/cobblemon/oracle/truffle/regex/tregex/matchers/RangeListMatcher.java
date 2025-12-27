package com.oracle.truffle.regex.tregex.matchers;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.regex.charset.CharMatchers;

public final class RangeListMatcher extends InvertibleCharMatcher {
   public static final int MAX_NUMBER_OF_RANGES = 6;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final int[] ranges;

   RangeListMatcher(boolean invert, int[] ranges) {
      super(invert);
      this.ranges = ranges;

      assert ranges.length <= 12 : "this matcher should only be used for short lists, to keep code size under control";
   }

   public static RangeListMatcher create(boolean invert, int[] ranges) {
      return new RangeListMatcher(invert, ranges);
   }

   @ExplodeLoop(kind = ExplodeLoop.LoopExplosionKind.FULL_UNROLL)
   @Override
   public boolean match(int c) {
      for (int i = 0; i < this.ranges.length; i += 2) {
         int lo = this.ranges[i];
         int hi = this.ranges[i + 1];
         if (isSingleChar(lo, hi)) {
            if (lo == c) {
               return this.result(true);
            }
         } else if (isTwoChars(lo, hi)) {
            if (c == lo || c == hi) {
               return this.result(true);
            }
         } else {
            if (lo > c) {
               return this.result(false);
            }

            if (hi >= c) {
               return this.result(true);
            }
         }
      }

      return this.result(false);
   }

   private static boolean isSingleChar(int lo, int hi) {
      CompilerAsserts.partialEvaluationConstant(lo);
      CompilerAsserts.partialEvaluationConstant(hi);
      return lo == hi;
   }

   private static boolean isTwoChars(int lo, int hi) {
      CompilerAsserts.partialEvaluationConstant(lo);
      CompilerAsserts.partialEvaluationConstant(hi);
      return lo + 1 == hi;
   }

   @Override
   public int estimatedCost() {
      return this.ranges.length;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return "list " + this.modifiersToString() + "[" + CharMatchers.rangesToString(this.ranges) + "]";
   }
}
