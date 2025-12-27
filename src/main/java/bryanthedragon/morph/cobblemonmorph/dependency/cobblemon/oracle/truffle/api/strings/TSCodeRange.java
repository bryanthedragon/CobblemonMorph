package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;

final class TSCodeRange {
   private static final int CR_7BIT = 0;
   private static final int CR_8BIT = 1;
   private static final int CR_16BIT = 2;
   private static final int CR_VALID_FIXED_WIDTH = 3;
   private static final int CR_BROKEN_FIXED_WIDTH = 4;
   private static final int CR_VALID_MULTIBYTE = 5;
   private static final int CR_BROKEN_MULTIBYTE = 6;
   private static final int CR_UNKNOWN = 7;

   private static int maxCodePoint(int codeRange) {
      return codeRange == 0 ? 127 : (codeRange == 1 ? 255 : (codeRange == 2 ? 65535 : 1114111));
   }

   static boolean isCodeRange(int codeRange) {
      return 0 <= codeRange && codeRange <= 7;
   }

   static int get7Bit() {
      return 0;
   }

   static int get8Bit() {
      return 1;
   }

   static int get16Bit() {
      return 2;
   }

   static int getValidFixedWidth() {
      return 3;
   }

   static int getBrokenFixedWidth() {
      return 4;
   }

   static int getValidMultiByte() {
      return 5;
   }

   static int getBrokenMultiByte() {
      return 6;
   }

   static int getUnknown() {
      return 7;
   }

   static boolean isUnknown(int codeRange) {
      return codeRange == 7;
   }

   static boolean is7Bit(int codeRange) {
      return codeRange == 0;
   }

   static boolean is7Or8Bit(int codeRange) {
      return codeRange <= 1;
   }

   static boolean isUpTo16Bit(int codeRange) {
      return codeRange <= 2;
   }

   static boolean is8Bit(int codeRange) {
      return codeRange == 1;
   }

   static boolean is16Bit(int codeRange) {
      return codeRange == 2;
   }

   static boolean isValidFixedWidth(int codeRange) {
      return codeRange == 3;
   }

   static boolean isBrokenFixedWidth(int codeRange) {
      return codeRange == 4;
   }

   static boolean isValidMultiByte(int codeRange) {
      return codeRange == 5;
   }

   static boolean isBrokenMultiByte(int codeRange) {
      return codeRange == 6;
   }

   static boolean isBrokenMultiByteOrUnknown(int codeRange) {
      return isBrokenMultiByte(codeRange) || isUnknown(codeRange);
   }

   static boolean isValidBrokenOrUnknownMultiByte(int codeRange) {
      return codeRange >= 5 && codeRange <= 7;
   }

   static boolean isKnown(int codeRange) {
      return !isUnknown(codeRange);
   }

   static boolean isKnown(int aCodeRange, int bCodeRange) {
      return isKnown(aCodeRange) && isKnown(bCodeRange);
   }

   static int commonCodeRange(int a, int b) {
      return Math.max(a, b);
   }

   static boolean isMoreRestrictiveThan(int a, int b) {
      return a < b;
   }

   static boolean isMoreRestrictiveOrEqual(int a, int b) {
      return a <= b;
   }

   static boolean isMoreGeneralThan(int a, int b) {
      return a > b;
   }

   static boolean isUpToValidFixedWidth(int codeRange) {
      return codeRange <= 3;
   }

   static boolean isFixedWidth(int codeRange) {
      return codeRange <= 4;
   }

   static boolean isInCodeRange(int codepoint, int codeRange) {
      return Integer.toUnsignedLong(codepoint) <= maxCodePoint(codeRange);
   }

   static int toStrideUTF16(int codeRange) {
      return codeRange <= get8Bit() ? 0 : 1;
   }

   static int toStrideUTF32(int codeRange) {
      assert isFixedWidth(codeRange);

      if (codeRange > 2) {
         return 2;
      } else {
         return codeRange == 2 ? 1 : 0;
      }
   }

   static int asciiLatinBytesNonAsciiCodeRange(int encoding) {
      if (TStringGuards.isAscii(encoding)) {
         return getBrokenFixedWidth();
      } else if (TStringGuards.isLatin1(encoding)) {
         return get8Bit();
      } else {
         return TStringGuards.isBytes(encoding) ? getValidFixedWidth() : getUnknown();
      }
   }

   static int asciiLatinBytesNonAsciiCodeRange(TruffleString.Encoding encoding) {
      if (TStringGuards.isAscii(encoding)) {
         return getBrokenFixedWidth();
      } else if (TStringGuards.isLatin1(encoding)) {
         return get8Bit();
      } else {
         return TStringGuards.isBytes(encoding) ? getValidFixedWidth() : getUnknown();
      }
   }

   static int getAsciiCodeRange(TruffleString.Encoding encoding) {
      if (TStringGuards.is7BitCompatible(encoding)) {
         return get7Bit();
      } else {
         return JCodings.getInstance().isSingleByte(encoding.jCoding) ? getValidFixedWidth() : getValidMultiByte();
      }
   }

   private static void staticAssertions() {
      assert toStrideUTF32(0) == 0;

      assert toStrideUTF32(1) == 0;

      assert toStrideUTF32(2) == 1;

      assert toStrideUTF32(3) == 2;

      assert toStrideUTF32(4) == 2;

      assert maxCodePoint(0) == 127;

      assert maxCodePoint(1) == 255;

      assert maxCodePoint(2) == 65535;

      assert maxCodePoint(3) == 1114111;

      assert maxCodePoint(4) == 1114111;

      assert maxCodePoint(5) == 1114111;

      assert maxCodePoint(6) == 1114111;

      assert maxCodePoint(7) == 1114111;
   }

   @CompilerDirectives.TruffleBoundary
   static String toString(int codeRange) {
      switch (codeRange) {
         case 0:
            return "7Bit";
         case 1:
            return "8Bit";
         case 2:
            return "16Bit";
         case 3:
            return "ValidFixedWidth";
         case 4:
            return "BrokenFixedWidth";
         case 5:
            return "ValidMultiByte";
         case 6:
            return "BrokenMultiByte";
         case 7:
            return "Unknown";
         default:
            throw CompilerDirectives.shouldNotReachHere();
      }
   }

   static {
      staticAssertions();
   }
}
