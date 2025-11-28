package org.graalvm.shadowed.org.jcodings.unicode;

import org.graalvm.shadowed.org.jcodings.IntHolder;
import org.graalvm.shadowed.org.jcodings.util.Macros;

public abstract class FixedWidthUnicodeEncoding extends UnicodeEncoding {
   protected final int shift;

   protected FixedWidthUnicodeEncoding(String name, int width) {
      super(name, width, width, null);
      this.shift = log2(width);
   }

   @Override
   public final int length(byte c) {
      return this.minLength;
   }

   @Override
   public int length(byte[] bytes, int p, int e) {
      if (e < p) {
         return Macros.CONSTRUCT_MBCLEN_INVALID();
      } else if (e - p < 4) {
         return Macros.CONSTRUCT_MBCLEN_NEEDMORE(4 - e - p);
      } else {
         int c = this.mbcToCode(bytes, p, e);
         return !Macros.UNICODE_VALID_CODEPOINT_P(c) ? Macros.CONSTRUCT_MBCLEN_INVALID() : Macros.CONSTRUCT_MBCLEN_CHARFOUND(4);
      }
   }

   @Override
   public final int strLength(byte[] bytes, int p, int end) {
      return end - p >>> this.shift;
   }

   @Override
   public final int strCodeAt(byte[] bytes, int p, int end, int index) {
      return this.mbcToCode(bytes, p + (index << this.shift), end);
   }

   @Override
   public final int codeToMbcLength(int code) {
      return this.minLength;
   }

   @Override
   public final int[] ctypeCodeRange(int ctype, IntHolder sbOut) {
      sbOut.value = 0;
      return super.ctypeCodeRange(ctype);
   }

   @Override
   public final int leftAdjustCharHead(byte[] bytes, int p, int s, int end) {
      return s <= p ? s : s - (s - p) % this.maxLength;
   }

   @Override
   public final boolean isReverseMatchAllowed(byte[] bytes, int p, int end) {
      return false;
   }

   private static int log2(int n) {
      int log = 0;

      while ((n >>>= 1) != 0) {
         log++;
      }

      return log;
   }
}
