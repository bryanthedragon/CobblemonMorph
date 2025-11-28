package org.graalvm.shadowed.org.jcodings.util;

public class Macros {
   public static final int MBCLEN_INVALID = -1;

   public static int CONSTRUCT_MBCLEN_INVALID() {
      return -1;
   }

   public static boolean MBCLEN_NEEDMORE_P(int r) {
      return r < -1;
   }

   public static int CONSTRUCT_MBCLEN_NEEDMORE(int n) {
      return -1 - n;
   }

   public static int MBCLEN_NEEDMORE_LEN(int r) {
      return -1 - r;
   }

   public static boolean MBCLEN_INVALID_P(int r) {
      return r == -1;
   }

   public static int MBCLEN_CHARFOUND_LEN(int r) {
      return r;
   }

   public static boolean MBCLEN_CHARFOUND_P(int r) {
      return 0 < r;
   }

   public static int CONSTRUCT_MBCLEN_CHARFOUND(int n) {
      return n;
   }

   public static boolean UNICODE_VALID_CODEPOINT_P(int c) {
      return c <= 1114111 && (c >= 65536 || !UTF16_IS_SURROGATE(c >> 8));
   }

   public static boolean UTF16_IS_SURROGATE_FIRST(int c) {
      return (c & 252) == 216;
   }

   public static boolean UTF16_IS_SURROGATE_SECOND(int c) {
      return (c & 252) == 220;
   }

   public static boolean UTF16_IS_SURROGATE(int c) {
      return (c & 248) == 216;
   }
}
