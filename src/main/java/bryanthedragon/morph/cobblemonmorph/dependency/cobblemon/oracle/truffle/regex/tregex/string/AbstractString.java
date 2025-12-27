package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.api.strings.TruffleString;

public interface AbstractString extends Iterable<Integer> {
   AbstractStringIterator iterator();

   int encodedLength();

   Object content();

   AbstractString substring(int start, int end);

   boolean regionMatches(int offset, AbstractString other, int ooffset, int encodedLength);

   TruffleString asTString();

   TruffleString.WithMask asTStringMask(TruffleString pattern);

   default String defaultToString() {
      StringBufferUTF16 sb = new StringBufferUTF16(this.encodedLength() * 2);

      for (int c : this) {
         sb.append(c);
      }

      return sb.materialize().toString();
   }
}
