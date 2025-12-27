package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Arrays;

public final class StringUTF32 implements AbstractString {
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final int[] str;

   public StringUTF32(int[] str) {
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

   public StringUTF32 substring(int start, int end) {
      return new StringUTF32(Arrays.copyOfRange(this.str, start, end));
   }

   @Override
   public boolean regionMatches(int offset, AbstractString other, int ooffset, int encodedLength) {
      int[] o = ((StringUTF32)other).str;
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
      return TruffleString.fromIntArrayUTF32Uncached(this.str, 0, this.str.length);
   }

   @Override
   public TruffleString.WithMask asTStringMask(TruffleString pattern) {
      return TruffleString.WithMask.createUTF32Uncached(pattern, this.str);
   }

   @Override
   public AbstractStringIterator iterator() {
      return new StringUTF32.StringUTF32Iterator(this.str);
   }

   private static final class StringUTF32Iterator extends AbstractStringIterator {
      private final int[] str;

      private StringUTF32Iterator(int[] str) {
         this.str = str;
      }

      @Override
      public boolean hasNext() {
         return this.i < this.str.length;
      }

      @Override
      public int nextInt() {
         return this.str[this.i++];
      }
   }
}
