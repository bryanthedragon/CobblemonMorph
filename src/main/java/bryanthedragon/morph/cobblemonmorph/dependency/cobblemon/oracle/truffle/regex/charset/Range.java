package com.oracle.truffle.regex.charset;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.util.DebugUtil;

public final class Range {
   public final int lo;
   public final int hi;

   public Range(int lo, int hi) {
      this.lo = lo;
      this.hi = hi;
   }

   public boolean isSingle() {
      return this.lo == this.hi;
   }

   public int size() {
      return this.hi - this.lo + 1;
   }

   @Override
   public String toString() {
      return toString(this.lo, this.hi);
   }

   @CompilerDirectives.TruffleBoundary
   public static String toString(int lo, int hi) {
      return lo == hi ? DebugUtil.charToString(lo) : DebugUtil.charToString(lo) + "-" + DebugUtil.charToString(hi);
   }
}
