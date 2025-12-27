package com.oracle.truffle.regex.tregex.buffer;

import com.oracle.truffle.regex.util.EmptyArrays;
import java.util.Arrays;
import java.util.PrimitiveIterator.OfInt;

public class ShortArrayBuffer extends AbstractArrayBuffer implements Iterable<Integer> {
   private short[] buf;

   public ShortArrayBuffer() {
      this(16);
   }

   public ShortArrayBuffer(int initialSize) {
      this.buf = new short[initialSize];
   }

   @Override
   int getBufferLength() {
      return this.buf.length;
   }

   @Override
   void grow(int newSize) {
      this.buf = Arrays.copyOf(this.buf, newSize);
   }

   public short get(int i) {
      return this.buf[i];
   }

   public void add(short s) {
      if (this.length == this.buf.length) {
         this.grow(this.length * 2);
      }

      this.buf[this.length] = s;
      this.length++;
   }

   public void addAll(short[] values, int valuesLength) {
      this.ensureCapacity(this.length + valuesLength);
      System.arraycopy(values, 0, this.buf, this.length, valuesLength);
      this.length += valuesLength;
   }

   public Object peek() {
      return this.buf[this.length - 1];
   }

   public Object pop() {
      return this.buf[--this.length];
   }

   public short[] toArray() {
      return this.isEmpty() ? EmptyArrays.SHORT : Arrays.copyOf(this.buf, this.length);
   }

   public OfInt iterator() {
      return new ShortArrayBuffer.ShortArrayBufferIterator(this.buf, this.length);
   }

   private static final class ShortArrayBufferIterator implements OfInt {
      private final short[] buf;
      private final int size;
      private int i = 0;

      private ShortArrayBufferIterator(short[] buf, int size) {
         this.buf = buf;
         this.size = size;
      }

      @Override
      public boolean hasNext() {
         return this.i < this.size;
      }

      @Override
      public int nextInt() {
         return this.buf[this.i++];
      }
   }
}
