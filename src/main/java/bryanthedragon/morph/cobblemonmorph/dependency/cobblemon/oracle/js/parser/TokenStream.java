package com.oracle.js.parser;

public class TokenStream {
   private static final int INITIAL_SIZE = 256;
   private long[] buffer = new long[256];
   private int count = 0;
   private int in = 0;
   private int out = 0;
   private int base = 0;

   private int next(final int position) {
      int next = position + 1;
      if (next >= this.buffer.length) {
         next = 0;
      }

      return next;
   }

   private int index(final int k) {
      int index = k - (this.base - this.out);
      if (index >= this.buffer.length) {
         index -= this.buffer.length;
      }

      return index;
   }

   public boolean isEmpty() {
      return this.count == 0;
   }

   public boolean isFull() {
      return this.count == this.buffer.length;
   }

   public int last() {
      return this.base + this.count - 1;
   }

   public void put(final long token) {
      if (this.count == this.buffer.length) {
         this.grow();
      }

      this.buffer[this.in] = token;
      this.count++;
      this.in = this.next(this.in);
   }

   public long get(final int k) {
      return this.buffer[this.index(k)];
   }

   public void commit(final int k) {
      this.out = this.index(k);
      this.count = this.count - (k - this.base);
      this.base = k;
   }

   public void grow() {
      long[] newBuffer = new long[this.buffer.length * 2];
      if (this.in > this.out) {
         System.arraycopy(this.buffer, this.out, newBuffer, 0, this.count);
      } else {
         int portion = this.buffer.length - this.out;
         System.arraycopy(this.buffer, this.out, newBuffer, 0, portion);
         System.arraycopy(this.buffer, 0, newBuffer, portion, this.count - portion);
      }

      this.out = 0;
      this.in = this.count;
      this.buffer = newBuffer;
   }

   void reset() {
      this.in = this.out = this.count = this.base = 0;
   }
}
