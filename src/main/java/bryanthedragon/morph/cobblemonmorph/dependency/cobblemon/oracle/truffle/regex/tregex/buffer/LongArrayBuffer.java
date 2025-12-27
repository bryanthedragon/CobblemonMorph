package com.oracle.truffle.regex.tregex.buffer;

import com.oracle.truffle.regex.util.EmptyArrays;
import java.util.Arrays;

public class LongArrayBuffer extends AbstractArrayBuffer {
   protected long[] buf;

   public LongArrayBuffer(int initialSize) {
      this.buf = new long[initialSize];
   }

   @Override
   int getBufferLength() {
      return this.buf.length;
   }

   @Override
   void grow(int newSize) {
      this.buf = Arrays.copyOf(this.buf, newSize);
   }

   public long get(int i) {
      return this.buf[i];
   }

   public void add(long v) {
      if (this.length == this.buf.length) {
         this.grow(this.length * 2);
      }

      this.buf[this.length++] = v;
   }

   public long pop() {
      return this.buf[--this.length];
   }

   public long peek() {
      return this.buf[this.length - 1];
   }

   public LongArrayBuffer asFixedSizeArray(int size) {
      this.ensureCapacity(size);
      this.length = size;
      return this;
   }

   public LongArrayBuffer asFixedSizeArray(int size, int initialValue) {
      this.ensureCapacity(size);
      Arrays.fill(this.buf, 0, size, (long)initialValue);
      this.length = size;
      return this;
   }

   public long[] toArray() {
      return this.isEmpty() ? EmptyArrays.LONG : Arrays.copyOf(this.buf, this.length);
   }
}
