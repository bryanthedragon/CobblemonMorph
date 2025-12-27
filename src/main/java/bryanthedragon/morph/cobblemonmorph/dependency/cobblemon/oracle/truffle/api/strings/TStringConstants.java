package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;

final class TStringConstants {
   static final int MAX_ARRAY_SIZE = 2147483639;
   static final int MAX_ARRAY_SIZE_S1 = 1073741819;
   static final int MAX_ARRAY_SIZE_S2 = 536870909;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   static final byte[] EMPTY_BYTES = new byte[0];
   @CompilerDirectives.CompilationFinal(dimensions = 2)
   private static final byte[][] SINGLE_BYTE_ARRAYS = new byte[256][1];
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private static final byte[] INFINITY_BYTES = new byte[]{73, 110, 102, 105, 110, 105, 116, 121};
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private static final byte[] NaN_BYTES = new byte[]{78, 97, 78};
   private static final TruffleString INFINITY = TruffleString.createConstant(
      INFINITY_BYTES, INFINITY_BYTES.length, 0, TruffleString.Encoding.US_ASCII, INFINITY_BYTES.length, TSCodeRange.get7Bit()
   );
   private static final TruffleString NaN = TruffleString.createConstant(
      NaN_BYTES, NaN_BYTES.length, 0, TruffleString.Encoding.US_ASCII, NaN_BYTES.length, TSCodeRange.get7Bit()
   );
   @CompilerDirectives.CompilationFinal(dimensions = 2)
   private static final TruffleString[][] SINGLE_BYTE = new TruffleString[6][];
   static final int LAZY_CONCAT_MIN_LENGTH = 40;

   private static int nonAsciiCodeRange(TruffleString.Encoding encoding) {
      if (TStringGuards.isAsciiBytesOrLatin1(encoding)) {
         return TSCodeRange.asciiLatinBytesNonAsciiCodeRange(encoding);
      } else {
         return TStringGuards.isUTF8(encoding) ? TSCodeRange.getBrokenMultiByte() : TSCodeRange.get8Bit();
      }
   }

   static TruffleString getInfinity(int encoding) {
      return AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS ? createAscii(INFINITY_BYTES, TruffleString.Encoding.get(encoding)) : INFINITY;
   }

   static TruffleString getNaN(int encoding) {
      return AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS ? createAscii(NaN_BYTES, TruffleString.Encoding.get(encoding)) : NaN;
   }

   static TruffleString getSingleByteAscii(TruffleString.Encoding encoding, int value) {
      if (AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS) {
         return createAscii(SINGLE_BYTE_ARRAYS[value], encoding);
      } else {
         return TStringGuards.isUnsupportedEncoding(encoding) ? SINGLE_BYTE[TruffleString.Encoding.US_ASCII.id][value] : SINGLE_BYTE[encoding.id][value];
      }
   }

   static TruffleString getSingleByte(TruffleString.Encoding encoding, int value) {
      return AbstractTruffleString.DEBUG_STRICT_ENCODING_CHECKS && value <= 127
         ? createAscii(SINGLE_BYTE_ARRAYS[value], encoding)
         : SINGLE_BYTE[encoding.id][value];
   }

   private static TruffleString createAscii(byte[] array, TruffleString.Encoding encoding) {
      return TruffleString.createFromByteArray(array, array.length, 0, encoding, array.length, TSCodeRange.getAsciiCodeRange(encoding), true);
   }

   static void truffleSafePointPoll(Node location, int loopCount) {
      if ((loopCount & 1048575) == 0) {
         TruffleSafepoint.poll(location);
         LoopNode.reportLoopCount(location, 1048576);
      }
   }

   static {
      for (int i = 0; i < 6; i++) {
         SINGLE_BYTE[i] = new TruffleString[256];
      }

      for (int i = 0; i < 128; i++) {
         SINGLE_BYTE_ARRAYS[i][0] = (byte)i;
         SINGLE_BYTE[0][i] = TruffleString.createConstant(SINGLE_BYTE_ARRAYS[i], 1, 0, TruffleString.Encoding.US_ASCII, 1, TSCodeRange.get7Bit());

         for (int j = 1; j < 6; j++) {
            SINGLE_BYTE[j][i] = SINGLE_BYTE[0][i];
         }
      }

      for (int i = 128; i < 256; i++) {
         SINGLE_BYTE_ARRAYS[i][0] = (byte)i;

         for (int j = 0; j < 6; j++) {
            SINGLE_BYTE[j][i] = TruffleString.createConstant(
               SINGLE_BYTE_ARRAYS[i], 1, 0, TruffleString.Encoding.get(j), 1, nonAsciiCodeRange(TruffleString.Encoding.get(j))
            );
         }
      }
   }
}
