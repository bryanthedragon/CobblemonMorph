package com.oracle.truffle.regex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.Range;
import java.util.Iterator;
import java.util.Spliterators;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public abstract class Abstract128BitSet implements Iterable<Integer> {
   public abstract long getLo();

   public abstract long getHi();

   static long toBit(int b) {
      return 1L << b;
   }

   public boolean isEmpty() {
      return this.getLo() == 0L && this.getHi() == 0L;
   }

   public boolean isFull() {
      return this.getLo() == -1L && this.getHi() == -1L;
   }

   public int size() {
      return Long.bitCount(this.getLo()) + Long.bitCount(this.getHi());
   }

   public boolean get(int b) {
      return b < 128 && ((b < 64 ? this.getLo() : this.getHi()) & toBit(b)) != 0L;
   }

   public boolean intersects(Abstract128BitSet other) {
      return !this.isDisjoint(other);
   }

   public boolean isDisjoint(Abstract128BitSet other) {
      return (this.getLo() & other.getLo() | this.getHi() & other.getHi()) == 0L;
   }

   public boolean contains(Abstract128BitSet other) {
      return (this.getLo() & other.getLo()) == other.getLo() && (this.getHi() & other.getHi()) == other.getHi();
   }

   @Override
   public int hashCode() {
      long h = 1234L ^ this.getLo() ^ this.getHi() << 1;
      return (int)(h >> 32 ^ h);
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof Abstract128BitSet && this.getLo() == ((Abstract128BitSet)obj).getLo() && this.getHi() == ((Abstract128BitSet)obj).getHi();
   }

   public boolean matches2CharsWith1BitDifference() {
      if (this.size() != 2) {
         return false;
      } else {
         int c1 = this.getMin();
         int c2 = this.getMax();
         return Integer.bitCount(c1 ^ c2) == 1;
      }
   }

   public boolean isSingleRange() {
      if (this.isEmpty()) {
         return false;
      } else if (this.getHi() == 0L) {
         return isSingleRange(this.getLo());
      } else if (this.getLo() == 0L) {
         return isSingleRange(this.getHi());
      } else {
         int rangeLo = Long.numberOfTrailingZeros(this.getLo());
         int rangeHi = Long.numberOfLeadingZeros(this.getHi());
         return this.getLo() == -1L << rangeLo && this.getHi() == -1L >>> rangeHi;
      }
   }

   private static boolean isSingleRange(long bs) {
      int rangeLo = Long.numberOfTrailingZeros(bs);
      int rangeHi = Long.numberOfLeadingZeros(bs);
      long bitRange = -1L << rangeLo & -1L >>> rangeHi;
      return bs == bitRange;
   }

   public int getMin() {
      assert !this.isEmpty();

      return this.getLo() == 0L ? Long.numberOfTrailingZeros(this.getHi()) + 64 : Long.numberOfTrailingZeros(this.getLo());
   }

   public int getMax() {
      assert !this.isEmpty();

      return this.getHi() == 0L ? 63 - Long.numberOfLeadingZeros(this.getLo()) : 127 - Long.numberOfLeadingZeros(this.getHi());
   }

   public int getMinInverse() {
      assert !this.isFull();

      return this.getLo() == -1L ? Long.numberOfTrailingZeros(~this.getHi()) + 64 : Long.numberOfTrailingZeros(~this.getLo());
   }

   public OfInt iterator() {
      return new Abstract128BitSet.Abstract128BitSetIterator(this.getLo(), this.getHi());
   }

   public int numberOfRanges() {
      int n = 0;

      for (Iterator<Range> it = this.rangesIterator(); it.hasNext(); n++) {
         it.next();
      }

      return n;
   }

   public Iterator<Range> rangesIterator() {
      return new Abstract128BitSet.Abstract128BitSetRangesIterator(this.getLo(), this.getHi());
   }

   @CompilerDirectives.TruffleBoundary
   public java.util.Spliterator.OfInt spliterator() {
      return Spliterators.spliteratorUnknownSize(this.iterator(), 277);
   }

   @CompilerDirectives.TruffleBoundary
   public IntStream stream() {
      return StreamSupport.intStream(this.spliterator(), false);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return BitSets.toString(this);
   }

   @CompilerDirectives.TruffleBoundary
   public String dumpRaw() {
      return this.isEmpty() ? "Immutable128BitSet.getEmpty()" : String.format("0x%016xL, 0x%016xL", this.getLo(), this.getHi());
   }

   static final class Abstract128BitSetIterator implements OfInt {
      private long curWord;
      private long nextWord;
      private int i = -1;

      Abstract128BitSetIterator(long lo, long hi) {
         this.curWord = lo;
         this.nextWord = hi;
      }

      @Override
      public boolean hasNext() {
         return (this.curWord | this.nextWord) != 0L;
      }

      @Override
      public int nextInt() {
         assert this.hasNext();

         if (this.curWord == 0L) {
            this.i = 63;
            this.curWord = this.nextWord;
            this.nextWord = 0L;
         }

         int trailingZeros = Long.numberOfTrailingZeros(this.curWord);
         this.curWord >>>= trailingZeros;
         this.curWord >>>= 1;
         this.i += trailingZeros + 1;
         return this.i;
      }
   }

   static final class Abstract128BitSetRangesIterator implements Iterator<Range> {
      private long curWord;
      private long nextWord;
      private int lastHi = 0;

      Abstract128BitSetRangesIterator(long lo, long hi) {
         if (lo == 0L) {
            this.lastHi = 64;
            this.curWord = hi;
            this.nextWord = 0L;
         } else {
            this.curWord = lo;
            this.nextWord = hi;
         }
      }

      @Override
      public boolean hasNext() {
         return this.curWord != 0L;
      }

      public Range next() {
         assert this.curWord != 0L;

         int trailingZeros = Long.numberOfTrailingZeros(this.curWord);
         this.curWord >>>= trailingZeros;
         int lo = this.lastHi + trailingZeros;
         int trailingOnes = Long.numberOfTrailingZeros(~this.curWord);
         this.curWord >>>= trailingOnes - 1;
         this.curWord >>>= 1;
         int hi = lo + trailingOnes;
         this.lastHi = hi;
         if (this.curWord == 0L) {
            this.lastHi = 64;
            this.curWord = this.nextWord;
            this.nextWord = 0L;
            if (hi == 64 && (this.curWord & 1L) != 0L) {
               int trailingOnesNext = Long.numberOfTrailingZeros(~this.curWord);
               this.curWord >>>= trailingOnesNext - 1;
               this.curWord >>>= 1;
               hi += trailingOnesNext;
               this.lastHi = hi;
            }
         }

         return new Range(lo, hi - 1);
      }
   }
}
