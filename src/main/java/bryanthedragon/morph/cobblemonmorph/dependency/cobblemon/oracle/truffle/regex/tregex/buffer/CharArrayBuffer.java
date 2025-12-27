package com.oracle.truffle.regex.tregex.buffer;

import com.oracle.truffle.regex.util.EmptyArrays;
import java.util.Arrays;

public class CharArrayBuffer extends AbstractArrayBuffer {
   protected char[] buf;

   public CharArrayBuffer() {
      this(16);
   }

   public CharArrayBuffer(int initialSize) {
      this.buf = new char[initialSize];
   }

   @Override
   int getBufferLength() {
      return this.buf.length;
   }

   @Override
   void grow(int newSize) {
      this.buf = Arrays.copyOf(this.buf, newSize);
   }

   public char[] getBuffer() {
      return this.buf;
   }

   public char get(int i) {
      return this.buf[i];
   }

   public void set(int i, char c) {
      this.buf[i] = c;
   }

   public void add(char c) {
      if (this.length == this.buf.length) {
         this.grow(this.length * 2);
      }

      this.buf[this.length++] = c;
   }

   public char[] toArray() {
      return this.isEmpty() ? EmptyArrays.CHAR : Arrays.copyOf(this.buf, this.length);
   }
}
