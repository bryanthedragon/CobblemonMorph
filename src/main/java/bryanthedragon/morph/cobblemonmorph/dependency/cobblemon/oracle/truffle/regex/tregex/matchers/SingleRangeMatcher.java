package com.oracle.truffle.regex.tregex.matchers;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.Range;

public final class SingleRangeMatcher extends InvertibleCharMatcher {
   private final int lo;
   private final int hi;

   SingleRangeMatcher(boolean invert, int lo, int hi) {
      super(invert);
      this.lo = lo;
      this.hi = hi;
   }

   public static SingleRangeMatcher create(boolean invert, int lo, int hi) {
      return new SingleRangeMatcher(invert, lo, hi);
   }

   public int getLo() {
      return this.lo;
   }

   public int getHi() {
      return this.hi;
   }

   @Override
   public boolean match(int c) {
      return this.result(this.lo <= c && this.hi >= c);
   }

   @Override
   public int estimatedCost() {
      return 2;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return this.modifiersToString() + Range.toString(this.lo, this.hi);
   }
}
