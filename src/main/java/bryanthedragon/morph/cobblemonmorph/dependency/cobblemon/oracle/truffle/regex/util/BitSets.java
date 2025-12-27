package com.oracle.truffle.regex.util;

import com.oracle.truffle.api.CompilerDirectives;
import java.util.Arrays;
import java.util.PrimitiveIterator.OfInt;

public final class BitSets {
   public static int highByte(int c) {
      return c >> 8;
   }

   public static int lowByte(int c) {
      return c & 0xFF;
   }

   public static long[] createBitSetArray(int nbits) {
      return new long[wordIndex(nbits - 1) + 1];
   }

   public static int wordIndex(int i) {
      return i >> 6;
   }

   public static long toBit(int index) {
      return 1L << index;
   }

   public static boolean isEmpty(long[] bs) {
      for (long word : bs) {
         if (word != 0L) {
            return false;
         }
      }

      return true;
   }

   public static boolean isFull(long[] bs) {
      for (long word : bs) {
         if (word != -1L) {
            return false;
         }
      }

      return true;
   }

   public static int size(long[] bs) {
      int ret = 0;

      for (long w : bs) {
         ret += Long.bitCount(w);
      }

      return ret;
   }

   public static boolean get(long[] bs, int index) {
      return wordIndex(index) < bs.length && (bs[wordIndex(index)] & toBit(index)) != 0L;
   }

   public static void set(long[] bs, int index) {
      bs[wordIndex(index)] |= toBit(index);
   }

   public static boolean add(long[] bs, int index) {
      long old = bs[wordIndex(index)];
      set(bs, index);
      return bs[wordIndex(index)] != old;
   }

   public static void setRange(long[] bs, int lo, int hi) {
      int wordIndexLo = wordIndex(lo);
      int wordIndexHi = wordIndex(hi);
      long rangeLo = -1L << lo;
      long rangeHi = -1L >>> 63 - (hi & 63);
      if (wordIndexLo == wordIndexHi) {
         bs[wordIndexLo] |= rangeLo & rangeHi;
      } else {
         bs[wordIndexLo] |= rangeLo;

         for (int i = wordIndexLo + 1; i < wordIndexHi; i++) {
            bs[i] = -1L;
         }

         bs[wordIndexHi] |= rangeHi;
      }
   }

   public static void clearRange(long[] bs, int lo, int hi) {
      int wordIndexLo = wordIndex(lo);
      int wordIndexHi = wordIndex(hi);
      long rangeLo = -1L << lo;
      long rangeHi = -1L >>> 63 - (hi & 63);
      if (wordIndexLo == wordIndexHi) {
         bs[wordIndexLo] &= ~(rangeLo & rangeHi);
      } else {
         bs[wordIndexLo] &= ~rangeLo;

         for (int i = wordIndexLo + 1; i < wordIndexHi; i++) {
            bs[i] = 0L;
         }

         bs[wordIndexHi] &= ~rangeHi;
      }
   }

   public static void clear(long[] bs) {
      Arrays.fill(bs, 0L);
   }

   public static void clear(long[] bs, int index) {
      bs[wordIndex(index)] &= ~toBit(index);
   }

   public static boolean remove(long[] bs, int index) {
      long old = bs[wordIndex(index)];
      clear(bs, index);
      return bs[wordIndex(index)] != old;
   }

   public static void invert(long[] bs) {
      for (int i = 0; i < bs.length; i++) {
         bs[i] = ~bs[i];
      }
   }

   public static long[] createInverse(long[] bs) {
      long[] ret = new long[bs.length];

      for (int i = 0; i < bs.length; i++) {
         ret[i] = ~bs[i];
      }

      return ret;
   }

   public static void intersect(long[] bs1, long[] bs2) {
      int i;
      for (i = 0; i < Math.min(bs1.length, bs2.length); i++) {
         bs1[i] &= bs2[i];
      }

      while (i < bs1.length) {
         bs1[i] = 0L;
         i++;
      }
   }

   public static int retainAll(long[] bs1, long[] bs2) {
      int size = 0;

      int i;
      for (i = 0; i < Math.min(bs1.length, bs2.length); i++) {
         bs1[i] &= bs2[i];
         size += Long.bitCount(bs1[i]);
      }

      while (i < bs1.length) {
         bs1[i] = 0L;
         i++;
      }

      return size;
   }

   public static void subtract(long[] bs1, long[] bs2) {
      for (int i = 0; i < Math.min(bs1.length, bs2.length); i++) {
         bs1[i] &= ~bs2[i];
      }
   }

   public static int removeAll(long[] bs1, long[] bs2) {
      int size = 0;

      for (int i = 0; i < Math.min(bs1.length, bs2.length); i++) {
         bs1[i] &= ~bs2[i];
         size += Long.bitCount(bs1[i]);
      }

      return size;
   }

   public static void union(long[] bs1, long[] bs2) {
      assert bs1.length >= bs2.length;

      for (int i = 0; i < Math.min(bs1.length, bs2.length); i++) {
         bs1[i] |= bs2[i];
      }
   }

   public static int addAll(long[] bs1, long[] bs2) {
      assert bs1.length >= bs2.length;

      int size = 0;

      for (int i = 0; i < Math.min(bs1.length, bs2.length); i++) {
         bs1[i] |= bs2[i];
         size += Long.bitCount(bs1[i]);
      }

      return size;
   }

   public static boolean isDisjoint(long[] bs1, long[] bs2) {
      for (int i = 0; i < Math.min(bs1.length, bs2.length); i++) {
         if ((bs1[i] & bs2[i]) != 0L) {
            return false;
         }
      }

      return true;
   }

   public static boolean contains(long[] bs1, long[] bs2) {
      for (int i = 0; i < bs2.length; i++) {
         if (i >= bs1.length) {
            if (bs2[i] != 0L) {
               return false;
            }
         } else if ((bs1[i] & bs2[i]) != bs2[i]) {
            return false;
         }
      }

      return true;
   }

   public static boolean equals(long[] bs1, long[] bs2) {
      if (bs1.length == bs2.length) {
         return Arrays.equals(bs1, bs2);
      } else {
         for (int i = 0; i < Math.min(bs1.length, bs2.length); i++) {
            if (bs1[i] != bs2[i]) {
               return false;
            }
         }

         for (int ix = bs1.length; ix < bs2.length; ix++) {
            if (bs1[ix] != 0L) {
               return false;
            }
         }

         for (int ixx = bs2.length; ixx < bs1.length; ixx++) {
            if (bs2[ixx] != 0L) {
               return false;
            }
         }

         return true;
      }
   }

   public static int hashCode(long[] bs) {
      long h = 1234L;
      int i = bs.length;

      while (--i >= 0) {
         h ^= bs[i] * (i + 1);
      }

      return (int)(h >> 32 ^ h);
   }

   public static OfInt iterator(long[] bs) {
      return new BitSets.BitSetIterator(bs);
   }

   @CompilerDirectives.TruffleBoundary
   public static String toString(long[] bs) {
      StringBuilder sb = new StringBuilder("[ ");
      int last = -2;
      int rangeBegin = -2;
      OfInt it = new BitSets.BitSetIterator(bs);

      while (it.hasNext()) {
         int b = it.nextInt();
         if (b != last + 1) {
            appendRange(sb, rangeBegin, last);
            rangeBegin = b;
         }

         last = b;
      }

      appendRange(sb, rangeBegin, last);
      sb.append(']');
      return sb.toString();
   }

   @CompilerDirectives.TruffleBoundary
   public static String toString(Iterable<Integer> bs) {
      StringBuilder sb = new StringBuilder("[ ");
      int last = -2;
      int rangeBegin = -2;

      for (int b : bs) {
         if (b != last + 1) {
            appendRange(sb, rangeBegin, last);
            rangeBegin = b;
         }

         last = b;
      }

      appendRange(sb, rangeBegin, last);
      sb.append(']');
      return sb.toString();
   }

   @CompilerDirectives.TruffleBoundary
   private static void appendRange(StringBuilder sb, int rangeBegin, int last) {
      if (rangeBegin >= 0 && rangeBegin < last) {
         sb.append(String.format("0x%02x", rangeBegin));
         if (rangeBegin + 1 < last) {
            sb.append("-");
         } else {
            sb.append(" ");
         }
      }

      if (last >= 0) {
         sb.append(String.format("0x%02x ", last));
      }
   }

   private static final class BitSetIterator implements OfInt {
      private final long[] words;
      private int wordIndex = 0;
      private byte bitIndex = 0;
      private long curWord;
      private int last;

      private BitSetIterator(long[] words) {
         this.words = words;
         if (this.hasNext()) {
            this.curWord = words[0];
         }

         this.findNext();
      }

      private void findNext() {
         while (this.curWord == 0L) {
            this.wordIndex++;
            this.bitIndex = 0;
            if (!this.hasNext()) {
               return;
            }

            this.curWord = this.words[this.wordIndex];
         }

         assert this.hasNext();

         assert this.curWord != 0L;

         int trailingZeros = Long.numberOfTrailingZeros(this.curWord);
         this.curWord >>>= trailingZeros;
         this.bitIndex = (byte)(this.bitIndex + trailingZeros);
      }

      @Override
      public boolean hasNext() {
         return this.wordIndex < this.words.length;
      }

      @Override
      public int nextInt() {
         assert this.hasNext();

         this.last = this.wordIndex * 64 + this.bitIndex;
         this.curWord >>>= 1;
         this.bitIndex++;
         this.findNext();
         return this.last;
      }

      @Override
      public void remove() {
         BitSets.clear(this.words, this.last);
      }
   }
}
