package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.api.ArrayUtils;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Arrays;

public final class StringUTF8 implements AbstractString {
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final byte[] str;

   public StringUTF8(byte[] str) {
      this.str = str;
   }

   @Override
   public int encodedLength() {
      return this.str.length;
   }

   @Override
   public Object content() {
      return this.str;
   }

   @Override
   public String toString() {
      return this.defaultToString();
   }

   public StringUTF8 substring(int start, int end) {
      return new StringUTF8(Arrays.copyOfRange(this.str, start, end));
   }

   @Override
   public boolean regionMatches(int offset, AbstractString other, int ooffset, int encodedLength) {
      return ArrayUtils.regionEqualsWithOrMask(this.str, offset, ((StringUTF8)other).str, ooffset, encodedLength, null);
   }

   @Override
   public TruffleString asTString() {
      return TruffleString.fromByteArrayUncached(this.str, 0, this.str.length, TruffleString.Encoding.UTF_8, false);
   }

   @Override
   public TruffleString.WithMask asTStringMask(TruffleString pattern) {
      return TruffleString.WithMask.createUncached(pattern, this.str, TruffleString.Encoding.UTF_8);
   }

   @Override
   public AbstractStringIterator iterator() {
      return new StringUTF8.StringUTF8Iterator(this.str);
   }

   private static final class StringUTF8Iterator extends AbstractStringIterator {
      private final byte[] str;

      private StringUTF8Iterator(byte[] str) {
         this.str = str;
      }

      @Override
      public boolean hasNext() {
         return this.i < this.str.length;
      }

      @Override
      public int nextInt() {
         byte b = this.str[this.i++];
         if (Byte.toUnsignedInt(b) < 128) {
            return b;
         } else {
            int nBytes = Integer.numberOfLeadingZeros(~(b << 24));
            int codepoint = b & 255 >>> nBytes;

            assert 1 < nBytes && nBytes < 5 : nBytes;

            switch (nBytes) {
               case 4:
                  assert this.hasNext() && (this.str[this.i] & 192) == 128;

                  codepoint = codepoint << 6 | this.str[this.i++] & 63;
               case 3:
                  assert this.hasNext() && (this.str[this.i] & 192) == 128;

                  codepoint = codepoint << 6 | this.str[this.i++] & 63;
               default:
                  assert this.hasNext() && (this.str[this.i] & 192) == 128;

                  return codepoint << 6 | this.str[this.i++] & 63;
            }
         }
      }
   }
}
