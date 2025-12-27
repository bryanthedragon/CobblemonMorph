package com.oracle.truffle.api.utilities;

import com.oracle.truffle.api.CompilerDirectives;
import java.util.Arrays;
import java.util.BitSet;

public final class FinalBitSet {
   public static final FinalBitSet EMPTY = new FinalBitSet(new long[0]);
   private static final int ADDRESS_BITS_PER_WORD = 6;
   private static final int BITS_PER_WORD = 64;
   private static final long WORD_MASK = -1L;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private long[] words;

   private static int wordIndex(int bitIndex) {
      return bitIndex >> 6;
   }

   private FinalBitSet(long[] words) {
      this.words = words;
   }

   public long[] toLongArray() {
      return Arrays.copyOf(this.words, this.words.length);
   }

   public boolean get(int bitIndex) {
      if (bitIndex < 0) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IndexOutOfBoundsException(String.format("bitIndex < 0: %s", bitIndex));
      } else {
         int wordIndex = wordIndex(bitIndex);
         return wordIndex >= this.words.length ? false : (this.words[wordIndex] & 1L << bitIndex) != 0L;
      }
   }

   public int length() {
      return this.words.length == 0 ? 0 : 64 * (this.words.length - 1) + (64 - Long.numberOfLeadingZeros(this.words[this.words.length - 1]));
   }

   public boolean isEmpty() {
      return this.words.length == 0;
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof FinalBitSet)) {
         return false;
      } else if (this == obj) {
         return true;
      } else {
         FinalBitSet set = (FinalBitSet)obj;
         if (this.words.length != set.words.length) {
            return false;
         } else {
            for (int i = 0; i < this.words.length; i++) {
               if (this.words[i] != set.words[i]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   @Override
   public int hashCode() {
      long h = 1234L;
      int i = this.words.length;

      while (--i >= 0) {
         h ^= this.words[i] * (i + 1);
      }

      return (int)(h >> 32 ^ h);
   }

   public int size() {
      return this.words.length * 64;
   }

   public int cardinality() {
      int sum = 0;

      for (int i = 0; i < this.words.length; i++) {
         sum += Long.bitCount(this.words[i]);
      }

      return sum;
   }

   public int nextSetBit(int fromIndex) {
      if (fromIndex < 0) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IndexOutOfBoundsException("fromIndex < 0: " + fromIndex);
      } else {
         int u = wordIndex(fromIndex);
         if (u >= this.words.length) {
            return -1;
         } else {
            long word;
            for (word = this.words[u] & -1L << fromIndex; word == 0L; word = this.words[u]) {
               if (++u == this.words.length) {
                  return -1;
               }
            }

            return u * 64 + Long.numberOfTrailingZeros(word);
         }
      }
   }

   public int nextClearBit(int fromIndex) {
      if (fromIndex < 0) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IndexOutOfBoundsException("fromIndex < 0: " + fromIndex);
      } else {
         int u = wordIndex(fromIndex);
         if (u >= this.words.length) {
            return fromIndex;
         } else {
            long word;
            for (word = ~this.words[u] & -1L << fromIndex; word == 0L; word = ~this.words[u]) {
               if (++u == this.words.length) {
                  return this.words.length * 64;
               }
            }

            return u * 64 + Long.numberOfTrailingZeros(word);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      int numBits = this.words.length > 128 ? this.cardinality() : this.words.length * 64;
      StringBuilder b = new StringBuilder(6 * numBits + 2);
      b.append('{');
      int i = this.nextSetBit(0);
      if (i != -1) {
         b.append(i);

         while (true) {
            i++;
            if (i < 0 || (i = this.nextSetBit(i)) < 0) {
               break;
            }

            int endOfRun = this.nextClearBit(i);

            do {
               b.append(", ").append(i);
            } while (++i != endOfRun);
         }
      }

      b.append('}');
      return b.toString();
   }

   public static FinalBitSet valueOf(long[] longs) {
      int n = longs.length;

      while (n > 0 && longs[n - 1] == 0L) {
         n--;
      }

      return n == 0 ? EMPTY : new FinalBitSet(Arrays.copyOf(longs, n));
   }

   @CompilerDirectives.TruffleBoundary
   public static FinalBitSet valueOf(BitSet originalBitSet) {
      long[] array = originalBitSet.toLongArray();
      return array.length == 0 ? EMPTY : new FinalBitSet(originalBitSet.toLongArray());
   }
}
