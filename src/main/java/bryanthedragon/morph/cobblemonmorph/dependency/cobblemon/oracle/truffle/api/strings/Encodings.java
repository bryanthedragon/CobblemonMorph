package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;

final class Encodings {
   static final int SUPPORTED_ENCODINGS_MIN_NUM = 0;
   static final int SUPPORTED_ENCODINGS_MAX_NUM = 6;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private static final int[] UTF_8_MIN_CODEPOINT = new int[]{0, 0, 128, 2048, 65536};

   static boolean isUTF16Surrogate(int c) {
      return c >> 11 == 27;
   }

   static boolean isUTF16HighSurrogate(int c) {
      return c >> 10 == 54;
   }

   static boolean isUTF16LowSurrogate(int c) {
      return c >> 10 == 55;
   }

   static boolean isUTF8ContinuationByte(int b) {
      return (b & 192) == 128;
   }

   static int invalidCodepoint() {
      return 65533;
   }

   static int utf8CodePointLength(int firstByte) {
      return Integer.numberOfLeadingZeros(~(firstByte << 24));
   }

   static int utf8EncodedSize(int codepoint) {
      if (codepoint < 128) {
         return 1;
      } else if (codepoint < 2048) {
         return 2;
      } else {
         return codepoint < 65536 ? 3 : 4;
      }
   }

   private static boolean isUTF8ContinuationByte(AbstractTruffleString a, Object arrayA, int index) {
      return isUTF8ContinuationByte(TStringOps.readS0(a, arrayA, index));
   }

   static byte[] utf8Encode(int codepoint) {
      int n = utf8EncodedSize(codepoint);
      byte[] ret = new byte[n];
      if (n == 1) {
         ret[0] = (byte)codepoint;
         return ret;
      } else {
         utf8Encode(codepoint, n, ret, 0);
         return ret;
      }
   }

   static byte[] utf8EncodeNonAscii(int codepoint, int encodedSize) {
      assert encodedSize == utf8EncodedSize(codepoint);

      assert encodedSize > 1;

      byte[] ret = new byte[encodedSize];
      utf8Encode(codepoint, encodedSize, ret, 0);
      return ret;
   }

   static void utf8Encode(int codepoint, byte[] buffer, int index, int length) {
      assert length == utf8EncodedSize(codepoint);

      if (length == 1) {
         buffer[index] = (byte)codepoint;
      } else {
         utf8Encode(codepoint, length, buffer, index);
      }
   }

   private static void utf8Encode(int codepoint, int encodedLength, byte[] buffer, int index) {
      assert index >= 0;

      assert 2 <= encodedLength && encodedLength <= 4;

      int i = index + encodedLength;
      int c = codepoint;
      switch (encodedLength) {
         case 4:
            buffer[--i] = (byte)(128 | codepoint & 63);
            c = codepoint >>> 6;
         case 3:
            buffer[--i] = (byte)(128 | c & 63);
            c >>>= 6;
         default:
            buffer[--i] = (byte)(128 | c & 63);
            c >>>= 6;
            buffer[--i] = (byte)(3840 >>> encodedLength | c);
      }
   }

   static int utf8CodePointToByteIndex(Node location, AbstractTruffleString a, Object arrayA, int codePointIndex) {
      int iCP = 0;
      int iBytes = 0;

      while (CompilerDirectives.injectBranchProbability(0.75, iBytes < a.length())) {
         if ((TStringOps.readS0(a, arrayA, iBytes) & 192) != 128) {
            if (CompilerDirectives.injectBranchProbability(0.01, iCP >= codePointIndex)) {
               break;
            }

            iCP++;
         }

         TStringConstants.truffleSafePointPoll(location, ++iBytes);
      }

      if (iBytes >= a.length()) {
         throw InternalErrors.indexOutOfBounds();
      } else {
         return iBytes;
      }
   }

   static int utf8DecodeValid(AbstractTruffleString a, Object arrayA, int i) {
      int b = TStringOps.readS0(a, arrayA, i);
      if (b < 128) {
         return b;
      } else {
         int nBytes = utf8CodePointLength(b);
         int codepoint = b & 255 >>> nBytes;

         assert 1 < nBytes && nBytes < 5 : nBytes;

         assert i + nBytes <= a.length();

         int j = i + 1;
         switch (nBytes) {
            case 4:
               assert isUTF8ContinuationByte(a, arrayA, j);

               codepoint = codepoint << 6 | TStringOps.readS0(a, arrayA, j++) & 63;
            case 3:
               assert isUTF8ContinuationByte(a, arrayA, j);

               codepoint = codepoint << 6 | TStringOps.readS0(a, arrayA, j++) & 63;
            default:
               assert isUTF8ContinuationByte(a, arrayA, j);

               return codepoint << 6 | TStringOps.readS0(a, arrayA, j) & 63;
         }
      }
   }

   static int utf8DecodeBroken(AbstractTruffleString a, Object arrayA, int i, TruffleString.ErrorHandling errorHandling) {
      int b = TStringOps.readS0(a, arrayA, i);
      if (b < 128) {
         return b;
      } else {
         int nBytes = utf8CodePointLength(b);
         int codepoint = b & 255 >>> nBytes;
         int j = i + 1;
         switch (nBytes) {
            case 4:
               if (j >= a.length() || !isUTF8ContinuationByte(a, arrayA, j)) {
                  return invalidCodepointReturnValue(errorHandling);
               } else {
                  codepoint = codepoint << 6 | TStringOps.readS0(a, arrayA, j++) & 63;
               }
            case 3:
               if (j >= a.length() || !isUTF8ContinuationByte(a, arrayA, j)) {
                  return invalidCodepointReturnValue(errorHandling);
               } else {
                  codepoint = codepoint << 6 | TStringOps.readS0(a, arrayA, j++) & 63;
               }
            case 2:
               if (j < a.length() && isUTF8ContinuationByte(a, arrayA, j)) {
                  codepoint = codepoint << 6 | TStringOps.readS0(a, arrayA, j) & 63;
                  if (utf8IsInvalidCodePoint(codepoint, nBytes)) {
                     return invalidCodepointReturnValue(errorHandling);
                  }

                  return codepoint;
               }

               return invalidCodepointReturnValue(errorHandling);
            default:
               return invalidCodepointReturnValue(errorHandling);
         }
      }
   }

   static int utf8GetCodePointLength(AbstractTruffleString a, Object arrayA, int i, TruffleString.ErrorHandling errorHandling) {
      return utf8GetCodePointLength(arrayA, a.offset(), a.length(), i, errorHandling);
   }

   static int utf8GetCodePointLength(Object arrayA, int offset, int length, int i, TruffleString.ErrorHandling errorHandling) {
      int b = TStringOps.readS0(arrayA, offset, length, i);
      if (b < 128) {
         return 1;
      } else {
         int nBytes = utf8CodePointLength(b);
         int codepoint = b & 255 >>> nBytes;
         int j = i + 1;
         if (i + nBytes > length) {
            if (errorHandling == TruffleString.ErrorHandling.BEST_EFFORT) {
               return 1;
            } else {
               assert errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE;

               if (nBytes >= 2 && nBytes <= 4) {
                  if (j == length && codepoint == 0) {
                     return nBytes == 2 ? -1 : -nBytes;
                  } else {
                     for (; j < i + nBytes; j++) {
                        codepoint <<= 6;
                        if (j < length) {
                           int continuationByte = TStringOps.readS0(arrayA, offset, length, j);
                           if (!isUTF8ContinuationByte(continuationByte)) {
                              return -1;
                           }

                           codepoint |= continuationByte & 63;
                        }
                     }

                     return utf8IsInvalidCodePoint(codepoint, nBytes) ? -1 : length - (i + nBytes) - 1;
                  }
               } else {
                  return -1;
               }
            }
         } else {
            switch (nBytes) {
               case 4:
                  int continuationBytexx = TStringOps.readS0(arrayA, offset, length, j++);
                  if (!isUTF8ContinuationByte(continuationBytexx)) {
                     return invalidCodepointReturnValue(1, errorHandling);
                  } else {
                     codepoint = codepoint << 6 | continuationBytexx & 63;
                  }
               case 3:
                  int continuationBytex = TStringOps.readS0(arrayA, offset, length, j++);
                  if (!isUTF8ContinuationByte(continuationBytex)) {
                     return invalidCodepointReturnValue(1, errorHandling);
                  } else {
                     codepoint = codepoint << 6 | continuationBytex & 63;
                  }
               case 2:
                  int continuationByte = TStringOps.readS0(arrayA, offset, length, j);
                  if (!isUTF8ContinuationByte(continuationByte)) {
                     return invalidCodepointReturnValue(1, errorHandling);
                  } else {
                     codepoint = codepoint << 6 | continuationByte & 63;
                     if (utf8IsInvalidCodePoint(codepoint, nBytes)) {
                        return invalidCodepointReturnValue(1, errorHandling);
                     }

                     return nBytes;
                  }
               default:
                  return invalidCodepointReturnValue(1, errorHandling);
            }
         }
      }
   }

   static boolean utf8IsInvalidCodePoint(int codepoint, int nBytes) {
      return isUTF16Surrogate(codepoint) || codepoint < UTF_8_MIN_CODEPOINT[nBytes] || codepoint > 1114111;
   }

   static int invalidCodepointReturnValue(TruffleString.ErrorHandling errorHandling) {
      return invalidCodepointReturnValue(invalidCodepoint(), errorHandling);
   }

   static int invalidCodepointReturnValue(int bestEffortValue, TruffleString.ErrorHandling errorHandling) {
      if (errorHandling == TruffleString.ErrorHandling.BEST_EFFORT) {
         return bestEffortValue;
      } else {
         assert errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE;

         return -1;
      }
   }

   static int utf16EncodedSize(int codepoint) {
      return codepoint < 65536 ? 1 : 2;
   }

   static int utf16BrokenGetCodePointByteLength(AbstractTruffleString a, Object arrayA, int i, TruffleString.ErrorHandling errorHandling) {
      return utf16BrokenGetCodePointByteLength(arrayA, a.offset(), a.length(), i, errorHandling);
   }

   static int utf16BrokenGetCodePointByteLength(Object arrayA, int offset, int length, int i, TruffleString.ErrorHandling errorHandling) {
      char c = TStringOps.readS1(arrayA, offset, length, i);
      if (errorHandling != TruffleString.ErrorHandling.BEST_EFFORT) {
         assert errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE;

         if (isUTF16Surrogate(c)) {
            if (isUTF16HighSurrogate(c)) {
               if (i + 1 == length) {
                  return -3;
               }

               if (isUTF16LowSurrogate(TStringOps.readS1(arrayA, offset, length, i + 1))) {
                  return 4;
               }
            }

            return -1;
         } else {
            return 2;
         }
      } else {
         return isUTF16HighSurrogate(c) && i + 1 < length && isUTF16LowSurrogate(TStringOps.readS1(arrayA, offset, length, i + 1)) ? 4 : 2;
      }
   }

   static int utf16Encode(int codepoint, byte[] bytes, int index) {
      if (codepoint < 65536) {
         TStringOps.writeToByteArray(bytes, 1, index, codepoint);
         return 1;
      } else {
         utf16EncodeSurrogatePair(codepoint, bytes, index);
         return 2;
      }
   }

   static void utf16EncodeSurrogatePair(int codepoint, byte[] bytes, int index) {
      assert codepoint > 65535;

      char c1 = Character.highSurrogate(codepoint);
      char c2 = Character.lowSurrogate(codepoint);
      TStringOps.writeToByteArray(bytes, 1, index, c1);
      TStringOps.writeToByteArray(bytes, 1, index + 1, c2);
   }

   static int utf16ValidCodePointToCharIndex(Node location, AbstractTruffleString a, Object arrayA, int codePointIndex) {
      int iCP = 0;
      int iChars = 0;

      while (CompilerDirectives.injectBranchProbability(0.75, iChars < a.length())) {
         if ((TStringOps.readS1(a, arrayA, iChars) & 'ﰀ') != 56320) {
            if (CompilerDirectives.injectBranchProbability(0.01, iCP >= codePointIndex)) {
               break;
            }

            iCP++;
         }

         TStringConstants.truffleSafePointPoll(location, ++iChars);
      }

      if (iChars >= a.length()) {
         throw InternalErrors.indexOutOfBounds();
      } else {
         return iChars;
      }
   }

   static int utf16BrokenCodePointToCharIndex(Node location, AbstractTruffleString a, Object arrayA, int codePointIndex) {
      int iCP = 0;
      int iChars = 0;

      while (iCP < codePointIndex) {
         if (isUTF16HighSurrogate(TStringOps.readS1(a, arrayA, iChars))
            && iChars + 1 < a.length()
            && isUTF16LowSurrogate(TStringOps.readS1(a, arrayA, iChars + 1))) {
            iChars++;
         }

         iChars++;
         TStringConstants.truffleSafePointPoll(location, ++iCP);
      }

      if (iChars >= a.length()) {
         throw InternalErrors.indexOutOfBounds();
      } else {
         return iChars;
      }
   }

   static int utf16DecodeValid(AbstractTruffleString a, Object arrayA, int i) {
      char c = TStringOps.readS1(a, arrayA, i);
      if (isUTF16HighSurrogate(c)) {
         assert i + 1 < a.length();

         assert isUTF16LowSurrogate(TStringOps.readS1(a, arrayA, i + 1));

         return Character.toCodePoint(c, TStringOps.readS1(a, arrayA, i + 1));
      } else {
         return c;
      }
   }

   static int utf16DecodeBroken(AbstractTruffleString a, Object arrayA, int i, TruffleString.ErrorHandling errorHandling) {
      char c = TStringOps.readS1(a, arrayA, i);
      if (errorHandling == TruffleString.ErrorHandling.BEST_EFFORT) {
         if (isUTF16HighSurrogate(c) && i + 1 < a.length()) {
            char c2 = TStringOps.readS1(a, arrayA, i + 1);
            if (isUTF16LowSurrogate(c2)) {
               return Character.toCodePoint(c, c2);
            }
         }
      } else {
         assert errorHandling == TruffleString.ErrorHandling.RETURN_NEGATIVE;

         if (isUTF16Surrogate(c)) {
            if (!isUTF16LowSurrogate(c) && i + 1 < a.length()) {
               char c2 = TStringOps.readS1(a, arrayA, i + 1);
               if (!isUTF16LowSurrogate(c2)) {
                  return -1;
               }

               return Character.toCodePoint(c, c2);
            }

            return -1;
         }
      }

      return c;
   }

   static boolean isValidUnicodeCodepoint(int codepoint) {
      return !isUTF16Surrogate(codepoint) && Integer.toUnsignedLong(codepoint) <= 1114111L;
   }
}
