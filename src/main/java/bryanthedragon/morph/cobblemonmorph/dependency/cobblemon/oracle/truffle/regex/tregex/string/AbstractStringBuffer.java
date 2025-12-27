package com.oracle.truffle.regex.tregex.string;

public interface AbstractStringBuffer {
   Encodings.Encoding getEncoding();

   void append(int codepoint);

   void appendOR(int c1, int c2);

   void appendXOR(int c1, int c2);

   void clear();

   AbstractString materialize();
}
