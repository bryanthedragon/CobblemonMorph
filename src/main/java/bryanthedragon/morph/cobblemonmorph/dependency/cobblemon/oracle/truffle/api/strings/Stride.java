package com.oracle.truffle.api.strings;

final class Stride {
   static boolean isStride(int stride) {
      return 0 <= stride && stride <= 2;
   }

   static int fromCodeRange(int codeRange, TruffleString.Encoding encoding) {
      if (TStringGuards.isUTF16(encoding)) {
         return fromCodeRangeUTF16(codeRange);
      } else {
         return TStringGuards.isUTF32(encoding) ? fromCodeRangeUTF32(codeRange) : 0;
      }
   }

   static int fromCodeRangeUTF16(int codeRange) {
      return TSCodeRange.toStrideUTF16(codeRange);
   }

   static int fromCodeRangeUTF32(int codeRange) {
      return TSCodeRange.toStrideUTF32(codeRange);
   }
}
