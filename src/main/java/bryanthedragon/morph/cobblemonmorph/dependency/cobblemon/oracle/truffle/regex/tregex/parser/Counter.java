package com.oracle.truffle.regex.tregex.parser;

import com.oracle.truffle.regex.UnsupportedRegexException;

public class Counter {
   protected int count = 0;

   public int getCount() {
      return this.count;
   }

   public void reset() {
      this.count = 0;
   }

   public int inc() {
      return this.inc(1);
   }

   public int inc(int i) {
      int ret = this.count;
      this.count += i;
      return ret;
   }

   public int dec() {
      return this.dec(1);
   }

   public int dec(int i) {
      int ret = this.count;
      this.count -= i;
      return ret;
   }

   public static class ThreadSafeCounter extends Counter {
      @Override
      public int inc() {
         int c = this.count;
         if (c < Integer.MAX_VALUE) {
            this.count = c + 1;
         }

         return this.count;
      }

      @Override
      public int inc(int i) {
         int c = this.count;
         if (c <= Integer.MAX_VALUE - i) {
            this.count = c + i;
         }

         return this.count;
      }

      @Override
      public int dec() {
         int c = this.count;
         if (c > Integer.MIN_VALUE) {
            this.count = c - 1;
         }

         return this.count;
      }

      @Override
      public int dec(int i) {
         int c = this.count;
         if (c >= Integer.MIN_VALUE + i) {
            this.count = c - i;
         }

         return this.count;
      }
   }

   public static class ThresholdCounter extends Counter {
      private final int max;
      private final String errorMsg;

      public ThresholdCounter(int max, String errorMsg) {
         this.max = max;
         this.errorMsg = errorMsg;
      }

      @Override
      public int inc(int i) {
         int ret = super.inc(i);
         if (this.getCount() > this.max) {
            throw new UnsupportedRegexException(this.errorMsg);
         } else {
            return ret;
         }
      }
   }
}
