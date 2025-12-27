package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;

public class JSURLDecoder {
   private final boolean isSpecial;

   public JSURLDecoder(boolean isSpecial) {
      this.isSpecial = isSpecial;
   }

   @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException = false)
   public TruffleString decode(TruffleString string) {
      int strLen = Strings.length(string);
      TruffleStringBuilder sb = null;
      CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();

      for (int k = 0; k < strLen; k++) {
         char c = Strings.charAt(string, k);
         if (c != '%') {
            if (sb != null) {
               Strings.builderAppend(sb, c);
            }
         } else {
            if (sb == null) {
               sb = JSURLEncoder.allocStringBuilder(string, k, strLen);
            }

            k = this.decodeConvert(string, strLen, k, sb, decoder);
         }
      }

      return sb != null ? Strings.builderToString(sb) : string;
   }

   private int decodeConvert(TruffleString string, int strLen, int start, TruffleStringBuilder buffer, CharsetDecoder decoder) {
      if (start + 2 >= strLen) {
         throw Errors.createURIError("illegal escape sequence");
      } else {
         int hex1 = getHexValue(Strings.charAt(string, start + 1));
         int hex2 = getHexValue(Strings.charAt(string, start + 2));
         byte b = (byte)((hex1 << 4) + hex2);
         int k = start + 2;
         if ((b & 128) == 0) {
            char c = (char)b;
            if (!this.isReserved(c)) {
               Strings.builderAppend(buffer, c);
            } else {
               Strings.builderAppend(buffer, string, start, k + 1);
            }
         } else {
            k = this.decodeConvertIntl(string, strLen, k, b, buffer, decoder);
         }

         return k;
      }
   }

   private int decodeConvertIntl(TruffleString string, int strLen, int kParam, byte b, TruffleStringBuilder buffer, CharsetDecoder decoder) {
      int k = kParam;
      int n = findN(b);
      if (n != 1 && n <= 4) {
         byte[] octetsB = new byte[n];
         octetsB[0] = b;
         if (kParam + 3 * (n - 1) >= strLen) {
            throw invalidEncodingError();
         } else {
            for (int j = 1; j < n; j++) {
               if (Strings.charAt(string, ++k) != '%') {
                  throw invalidEncodingError();
               }

               int hex3 = getHexValue(Strings.charAt(string, k + 1));
               int hex4 = getHexValue(Strings.charAt(string, k + 2));
               byte b2 = (byte)((hex3 << 4) + hex4);
               if ((b2 & 192) != 128) {
                  throw invalidEncodingError();
               }

               k += 2;
               octetsB[j] = b2;
            }

            ByteBuffer bb = ByteBuffer.wrap(octetsB);
            CharBuffer cb = CharBuffer.wrap(new char[2]);
            decoder.reset();
            cb.rewind();
            CoderResult coderResult = decoder.decode(bb, cb, true);
            if (coderResult.isError()) {
               throw invalidEncodingError();
            } else {
               if (cb.position() == 1) {
                  assert !this.isReserved(cb.get(0));

                  Strings.builderAppend(buffer, cb.get(0));
               } else {
                  Strings.builderAppend(buffer, cb.get(0));
                  Strings.builderAppend(buffer, cb.get(1));
               }

               return k;
            }
         }
      } else {
         throw invalidEncodingError();
      }
   }

   private static JSException invalidEncodingError() {
      throw Errors.createURIError("invalid encoding");
   }

   private static int getHexValue(char digit) {
      int value = JSRuntime.valueInHex(digit);
      if (value < 0) {
         throw Errors.createURIError("decode: Illegal hex characters in escape (%) pattern");
      } else {
         return value;
      }
   }

   private boolean isReserved(char c) {
      return this.isSpecial ? JSURLEncoder.reservedURISet.get(c) : false;
   }

   private static int findN(byte b) {
      if ((b & 64) == 0) {
         return 1;
      } else if ((b & 32) == 0) {
         return 2;
      } else if ((b & 16) == 0) {
         return 3;
      } else if ((b & 8) == 0) {
         return 4;
      } else if ((b & 4) == 0) {
         return 5;
      } else if ((b & 2) == 0) {
         return 6;
      } else {
         return (b & 1) == 0 ? 7 : 8;
      }
   }
}
