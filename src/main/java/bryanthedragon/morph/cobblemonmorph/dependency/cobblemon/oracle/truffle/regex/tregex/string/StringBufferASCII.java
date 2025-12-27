package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.regex.tregex.buffer.ByteArrayBuffer;

public final class StringBufferASCII extends ByteArrayBuffer implements AbstractStringBuffer {
   public StringBufferASCII() {
      this(16);
   }

   public StringBufferASCII(int capacity) {
      super(capacity);
   }

   @Override
   public Encodings.Encoding getEncoding() {
      return Encodings.ASCII;
   }

   @Override
   public void append(int codepoint) {
      assert codepoint <= Encodings.ASCII.getMaxValue();

      this.add((byte)codepoint);
   }

   @Override
   public void appendOR(int c1, int c2) {
      assert c1 <= Encodings.ASCII.getMaxValue();

      assert c2 <= Encodings.ASCII.getMaxValue();

      this.add((byte)(c1 | c2));
   }

   @Override
   public void appendXOR(int c1, int c2) {
      assert c1 <= Encodings.ASCII.getMaxValue();

      assert c2 <= Encodings.ASCII.getMaxValue();

      this.add((byte)(c1 ^ c2));
   }

   public StringASCII materialize() {
      return new StringASCII(this.toArray());
   }
}
