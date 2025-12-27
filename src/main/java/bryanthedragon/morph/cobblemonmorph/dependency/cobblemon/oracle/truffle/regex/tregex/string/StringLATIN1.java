package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Arrays;

public final class StringLATIN1 implements AbstractString {
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final byte[] str;

   public StringLATIN1(byte[] str) {
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

   public StringLATIN1 substring(int start, int end) {
      return new StringLATIN1(Arrays.copyOfRange(this.str, start, end));
   }

   @Override
   public boolean regionMatches(int offset, AbstractString other, int ooffset, int encodedLength) {
      byte[] o = ((StringLATIN1)other).str;
      if (offset + encodedLength <= this.str.length && ooffset + encodedLength <= o.length) {
         for (int i = 0; i < encodedLength; i++) {
            if (this.str[offset + i] != o[ooffset + i]) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public TruffleString asTString() {
      return TruffleString.fromByteArrayUncached(this.str, 0, this.str.length, TruffleString.Encoding.ISO_8859_1, false);
   }

   @Override
   public TruffleString.WithMask asTStringMask(TruffleString pattern) {
      return TruffleString.WithMask.createUncached(pattern, this.str, TruffleString.Encoding.ISO_8859_1);
   }

   @Override
   public AbstractStringIterator iterator() {
      return new StringLATIN1.StringLATIN1Iterator(this.str);
   }

   private static final class StringLATIN1Iterator extends AbstractStringIterator {
      private final byte[] str;

      private StringLATIN1Iterator(byte[] str) {
         this.str = str;
      }

      @Override
      public boolean hasNext() {
         return this.i < this.str.length;
      }

      @Override
      public int nextInt() {
         return Byte.toUnsignedInt(this.str[this.i++]);
      }
   }
}
