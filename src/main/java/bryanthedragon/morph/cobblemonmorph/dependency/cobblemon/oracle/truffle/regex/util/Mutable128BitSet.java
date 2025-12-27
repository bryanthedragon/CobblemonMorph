package com.oracle.truffle.regex.util;

import java.util.PrimitiveIterator.OfInt;

public final class Mutable128BitSet extends Abstract128BitSet implements Iterable<Integer> {
   private long lo;
   private long hi;

   public Mutable128BitSet() {
   }

   public Mutable128BitSet(long lo, long hi) {
      this.lo = lo;
      this.hi = hi;
   }

   public Mutable128BitSet copy() {
      return new Mutable128BitSet(this.lo, this.hi);
   }

   @Override
   public long getLo() {
      return this.lo;
   }

   @Override
   public long getHi() {
      return this.hi;
   }

   public void clear() {
      this.lo = 0L;
      this.hi = 0L;
   }

   public void set(int b) {
      assert b < 128;

      if (b < 64) {
         this.lo = this.lo | toBit(b);
      } else {
         this.hi = this.hi | toBit(b);
      }
   }

   public void clear(int b) {
      assert b < 128;

      if (b < 64) {
         this.lo = this.lo & ~toBit(b);
      } else {
         this.hi = this.hi & ~toBit(b);
      }
   }

   public boolean add(int b) {
      assert b < 128;

      if (b < 64) {
         long old = this.lo;
         this.lo = this.lo | toBit(b);
         return this.lo != old;
      } else {
         long old = this.hi;
         this.hi = this.hi | toBit(b);
         return this.hi != old;
      }
   }

   public boolean remove(int b) {
      assert b < 128;

      if (b < 64) {
         long old = this.lo;
         this.lo = this.lo & ~toBit(b);
         return this.lo != old;
      } else {
         long old = this.hi;
         this.hi = this.hi & ~toBit(b);
         return this.hi != old;
      }
   }

   public void setRange(int rangeLo, int rangeHi) {
      assert rangeLo < 128 && rangeHi < 128;

      long bitRangeLo = -1L << rangeLo;
      long bitRangeHi = -1L >>> 63 - (rangeHi & 63);
      if (rangeLo < 64) {
         if (rangeHi < 64) {
            this.lo |= bitRangeLo & bitRangeHi;
         } else {
            this.lo |= bitRangeLo;
            this.hi |= bitRangeHi;
         }
      } else {
         assert rangeHi >= 64;

         this.hi |= bitRangeLo & bitRangeHi;
      }
   }

   public void invert() {
      this.lo = ~this.lo;
      this.hi = ~this.hi;
   }

   public void intersect(Mutable128BitSet other) {
      this.lo = this.lo & other.lo;
      this.hi = this.hi & other.hi;
   }

   public void subtract(Mutable128BitSet other) {
      this.lo = this.lo & ~other.lo;
      this.hi = this.hi & ~other.hi;
   }

   public void union(Immutable128BitSet other) {
      this.lo = this.lo | other.getLo();
      this.hi = this.hi | other.getHi();
   }

   public void union(Mutable128BitSet other) {
      this.lo = this.lo | other.lo;
      this.hi = this.hi | other.hi;
   }

   public boolean addAll(Mutable128BitSet other) {
      long oldLo = this.lo;
      long oldHi = this.hi;
      this.union(other);
      return this.lo != oldLo || this.hi != oldHi;
   }

   public boolean retainAll(Mutable128BitSet other) {
      long oldLo = this.lo;
      long oldHi = this.hi;
      this.intersect(other);
      return this.lo != oldLo || this.hi != oldHi;
   }

   public boolean removeAll(Mutable128BitSet other) {
      long oldLo = this.lo;
      long oldHi = this.hi;
      this.subtract(other);
      return this.lo != oldLo || this.hi != oldHi;
   }

   public Immutable128BitSet toImmutable() {
      return new Immutable128BitSet(this.lo, this.hi);
   }

   @Override
   public OfInt iterator() {
      return new Mutable128BitSet.Mutable128BitSetIterator(this, this.lo, this.hi);
   }

   static final class Mutable128BitSetIterator implements OfInt {
      private final Mutable128BitSet set;
      private long curWord;
      private long nextWord;
      private int i = -1;

      Mutable128BitSetIterator(Mutable128BitSet set, long lo, long hi) {
         this.set = set;
         this.curWord = lo;
         this.nextWord = hi;
      }

      @Override
      public boolean hasNext() {
         return this.curWord != 0L || this.nextWord != 0L;
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

      @Override
      public void remove() {
         this.set.clear(this.i);
      }
   }
}
