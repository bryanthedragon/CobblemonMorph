package com.oracle.truffle.api.strings;

final class StringAttributes {
   static long create(int codePointLength, int codeRange) {
      return (long)codePointLength << 32 | codeRange;
   }

   static int getCodePointLength(long stringAttributes) {
      return (int)(stringAttributes >>> 32);
   }

   static int getCodeRange(long stringAttributes) {
      return (int)stringAttributes;
   }
}
