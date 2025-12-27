package com.oracle.truffle.regex.tregex.string;

import com.oracle.truffle.regex.tregex.buffer.ByteArrayBuffer;

public final class StringBufferLATIN1 extends ByteArrayBuffer implements AbstractStringBuffer {
   public StringBufferLATIN1() {
      this(16);
   }

   public StringBufferLATIN1(int capacity) {
      super(capacity);
   }

   @Override
   public Encodings.Encoding getEncoding() {
      return Encodings.LATIN_1;
   }

   @Override
   public void append(int codepoint) {
      assert codepoint <= Encodings.LATIN_1.getMaxValue();

      this.add((byte)codepoint);
   }

   @Override
   public void appendOR(int c1, int c2) {
      assert c1 <= Encodings.LATIN_1.getMaxValue();

      assert c2 <= Encodings.LATIN_1.getMaxValue();

      this.add((byte)(c1 | c2));
   }

   @Override
   public void appendXOR(int c1, int c2) {
      assert c1 <= Encodings.LATIN_1.getMaxValue();

      assert c2 <= Encodings.LATIN_1.getMaxValue();

      this.add((byte)(c1 ^ c2));
   }

   public StringLATIN1 materialize() {
      return new StringLATIN1(this.toArray());
   }
}
