package com.oracle.truffle.regex.tregex.buffer;

public abstract class AbstractArrayBuffer {
   int length;

   public void clear() {
      this.length = 0;
   }

   public boolean isEmpty() {
      return this.length == 0;
   }

   public int length() {
      return this.length;
   }

   public void setLength(int size) {
      this.length = size;
   }

   public void ensureCapacity(int newLength) {
      if (this.getBufferLength() < newLength) {
         int newBufferLength = this.getBufferLength() * 2;

         while (newBufferLength < newLength) {
            newBufferLength *= 2;
         }

         this.grow(newBufferLength);
      }
   }

   abstract int getBufferLength();

   abstract void grow(int newLength);
}
