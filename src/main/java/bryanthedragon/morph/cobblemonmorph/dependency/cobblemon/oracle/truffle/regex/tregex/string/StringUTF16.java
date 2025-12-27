package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.api.strings.TruffleString;

public final class StringUTF16 implements AbstractString {
   private final String str;

   public StringUTF16(char[] str) {
      this(new String(str));
   }

   public StringUTF16(String str) {
      this.str = str;
   }

   @Override
   public int encodedLength() {
      return this.str.length();
   }

   public char charAt(int i) {
      return this.str.charAt(i);
   }

   @Override
   public String toString() {
      return this.str;
   }

   @Override
   public Object content() {
      return this.str;
   }

   public StringUTF16 substring(int start, int end) {
      return new StringUTF16(this.str.substring(start, end));
   }

   @Override
   public boolean regionMatches(int offset, AbstractString other, int ooffset, int encodedLength) {
      return this.str.regionMatches(offset, ((StringUTF16)other).str, ooffset, encodedLength);
   }

   @Override
   public TruffleString asTString() {
      return TruffleString.fromJavaStringUncached(this.str, TruffleString.Encoding.UTF_16);
   }

   @Override
   public TruffleString.WithMask asTStringMask(TruffleString pattern) {
      return TruffleString.WithMask.createUTF16Uncached(pattern, this.str.toCharArray());
   }

   @Override
   public AbstractStringIterator iterator() {
      return new StringUTF16.StringUTF16Iterator(this.str);
   }

   private static final class StringUTF16Iterator extends AbstractStringIterator {
      private final String str;

      private StringUTF16Iterator(String str) {
         this.str = str;
      }

      @Override
      public boolean hasNext() {
         return this.i < this.str.length();
      }

      @Override
      public int nextInt() {
         char c = this.str.charAt(this.i++);
         return Encodings.Encoding.UTF16.isHighSurrogate(c) && this.hasNext() && Encodings.Encoding.UTF16.isLowSurrogate(this.str.charAt(this.i))
            ? Character.toCodePoint(c, this.str.charAt(this.i++))
            : c;
      }
   }
}
