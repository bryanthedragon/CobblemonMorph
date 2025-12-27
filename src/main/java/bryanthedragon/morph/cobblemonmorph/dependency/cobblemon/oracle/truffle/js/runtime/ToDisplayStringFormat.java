package com.oracle.truffle.js.runtime;

public final class ToDisplayStringFormat {
   private static final int TO_STRING_MAX_DEPTH = 3;
   private static final ToDisplayStringFormat DEFAULT_FORMAT = new ToDisplayStringFormat(3, 20, false, true);
   private static final ToDisplayStringFormat ARRAY_FORMAT = new ToDisplayStringFormat(3, Integer.MAX_VALUE, false, false);
   private final int maxDepth;
   private final int maxElements;
   private final boolean quoteString;
   private final boolean includeArrayLength;

   private ToDisplayStringFormat(int maxDepth, int maxElements, boolean quoteString, boolean includeArrayLength) {
      this.maxDepth = maxDepth;
      this.maxElements = maxElements;
      this.quoteString = quoteString;
      this.includeArrayLength = includeArrayLength;
   }

   public int getMaxDepth() {
      return this.maxDepth;
   }

   public int getMaxElements() {
      return this.maxElements;
   }

   public boolean quoteString() {
      return this.quoteString;
   }

   public boolean includeArrayLength() {
      return this.includeArrayLength;
   }

   public ToDisplayStringFormat withQuoteString(boolean quote) {
      return new ToDisplayStringFormat(this.maxDepth, this.maxElements, quote, this.includeArrayLength);
   }

   public static ToDisplayStringFormat getDefaultFormat() {
      return DEFAULT_FORMAT;
   }

   public static ToDisplayStringFormat getArrayFormat() {
      return ARRAY_FORMAT;
   }
}
