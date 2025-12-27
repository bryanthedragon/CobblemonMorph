package com.oracle.truffle.regex.tregex.buffer;

import com.oracle.truffle.regex.util.EmptyArrays;
import java.util.Arrays;

public class ByteArrayBuffer extends AbstractArrayBuffer {
   private byte[] buf;

   public ByteArrayBuffer() {
      this(16);
   }

   public ByteArrayBuffer(int initialSize) {
      this.buf = new byte[initialSize];
   }

   @Override
   int getBufferLength() {
      return this.buf.length;
   }

   @Override
   void grow(int newSize) {
      this.buf = Arrays.copyOf(this.buf, newSize);
   }

   public byte get(int i) {
      return this.buf[i];
   }

   public void set(int i, byte b) {
      this.buf[i] = b;
   }

   public void add(byte b) {
      if (this.length == this.buf.length) {
         this.grow(this.length * 2);
      }

      this.buf[this.length] = b;
      this.length++;
   }

   public byte[] toArray() {
      return this.isEmpty() ? EmptyArrays.BYTE : Arrays.copyOf(this.buf, this.length);
   }
}
