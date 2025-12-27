package com.oracle.truffle.regex.util;

public final class Immutable128BitSet extends Abstract128BitSet {
   private final long lo;
   private final long hi;

   public static Immutable128BitSet create(int... values) {
      long lo = 0L;
      long hi = 0L;

      for (int v : values) {
         assert v < 128;

         if (v >= 64) {
            hi |= 1L << v;
         } else {
            lo |= 1L << v;
         }
      }

      return new Immutable128BitSet(lo, hi);
   }

   public static Immutable128BitSet createDirect(long lo, long hi) {
      return new Immutable128BitSet(lo, hi);
   }

   Immutable128BitSet(long lo, long hi) {
      this.lo = lo;
      this.hi = hi;
   }

   @Override
   public long getLo() {
      return this.lo;
   }

   @Override
   public long getHi() {
      return this.hi;
   }

   public static Immutable128BitSet getEmpty() {
      return new Immutable128BitSet(0L, 0L);
   }

   public static Immutable128BitSet getFull() {
      return new Immutable128BitSet(-1L, -1L);
   }

   public Immutable128BitSet set(int b) {
      assert b < 128;

      return b < 64 ? new Immutable128BitSet(this.lo | toBit(b), this.hi) : new Immutable128BitSet(this.lo, this.hi | toBit(b));
   }

   public Immutable128BitSet clear(int b) {
      assert b < 128;

      return b < 64 ? new Immutable128BitSet(this.lo & ~toBit(b), this.hi) : new Immutable128BitSet(this.lo, this.hi & ~toBit(b));
   }

   public Immutable128BitSet invert() {
      return new Immutable128BitSet(~this.lo, ~this.hi);
   }

   public Immutable128BitSet intersect(Immutable128BitSet other) {
      return new Immutable128BitSet(this.lo & other.lo, this.hi & other.hi);
   }

   public Immutable128BitSet subtract(Immutable128BitSet other) {
      return new Immutable128BitSet(this.lo & ~other.lo, this.hi & ~other.hi);
   }

   public Immutable128BitSet union(Immutable128BitSet other) {
      return new Immutable128BitSet(this.lo | other.lo, this.hi | other.hi);
   }

   public Immutable128BitSet.IntersectAndSubtractResult intersectAndSubtract(Immutable128BitSet o) {
      long intersectionLo = this.lo & o.lo;
      long intersectionHi = this.hi & o.hi;
      return new Immutable128BitSet.IntersectAndSubtractResult(
         new Immutable128BitSet(this.lo & ~intersectionLo, this.hi & ~intersectionHi),
         new Immutable128BitSet(o.lo & ~intersectionLo, o.hi & ~intersectionHi),
         new Immutable128BitSet(intersectionLo, intersectionHi)
      );
   }

   public static final class IntersectAndSubtractResult {
      public final Immutable128BitSet subtractedA;
      public final Immutable128BitSet subtractedB;
      public final Immutable128BitSet intersection;

      public IntersectAndSubtractResult(Immutable128BitSet subtractedA, Immutable128BitSet subtractedB, Immutable128BitSet intersection) {
         this.subtractedA = subtractedA;
         this.subtractedB = subtractedB;
         this.intersection = intersection;
      }
   }
}
