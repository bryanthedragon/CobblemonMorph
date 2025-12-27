package com.oracle.truffle.regex.tregex.matchers;

public abstract class InvertibleCharMatcher extends CharMatcher {
   final boolean invert;

   protected InvertibleCharMatcher(boolean invert) {
      this.invert = invert;
   }

   protected boolean result(boolean result) {
      return result(this.invert, result);
   }

   public static boolean result(boolean invert, boolean result) {
      return result != invert;
   }

   protected String modifiersToString() {
      return this.invert ? "!" : "";
   }

   static int highByte(int i) {
      return i >> 8;
   }

   static int lowByte(int i) {
      return i & 0xFF;
   }
}
