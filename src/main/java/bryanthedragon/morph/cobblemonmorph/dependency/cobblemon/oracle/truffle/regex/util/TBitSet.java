package com.oracle.truffle.regex.util;

import com.oracle.truffle.api.CompilerDirectives;
import java.util.Arrays;
import java.util.Spliterators;
import java.util.PrimitiveIterator.OfInt;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

public class TBitSet implements Iterable<Integer> {
   private static final TBitSet[] STATIC_INSTANCES = new TBitSet[16];
   private long[] words;

   public static TBitSet valueOf(int... values) {
      assert values.length > 0;

      assert Assertions.isSorted(values);

      TBitSet bs = new TBitSet(values[values.length - 1]);

      for (int v : values) {
         bs.set(v);
      }

      return bs;
   }

   public TBitSet(int nbits) {
      this.words = BitSets.createBitSetArray(nbits);
   }

   private TBitSet(long[] words) {
      this.words = words;
   }

   private TBitSet(TBitSet copy) {
      this.words = Arrays.copyOf(copy.words, copy.words.length);
   }

   public static TBitSet getEmptyInstance() {
      return STATIC_INSTANCES[0];
   }

   public static TBitSet getStaticInstance(int i) {
      return STATIC_INSTANCES[i];
   }

   public static int getNumberOfStaticInstances() {
      return STATIC_INSTANCES.length;
   }

   public int getStaticCacheKey() {
      for (int i = 1; i < this.words.length; i++) {
         if (this.words[i] != 0L) {
            return -1;
         }
      }

      if (this.words.length == 0) {
         return 0;
      } else {
         return 0L <= this.words[0] && this.words[0] < STATIC_INSTANCES.length ? (int)this.words[0] : -1;
      }
   }

   public TBitSet copy() {
      return new TBitSet(this);
   }

   public long[] toLongArray() {
      return Arrays.copyOf(this.words, this.words.length);
   }

   private void ensureCapacity(int nWords) {
      if (this.words.length < nWords) {
         this.words = Arrays.copyOf(this.words, Math.max(2 * this.words.length, nWords));
      }
   }

   public boolean isEmpty() {
      return BitSets.isEmpty(this.words);
   }

   public boolean isFull() {
      return BitSets.isFull(this.words);
   }

   public int numberOfSetBits() {
      return BitSets.size(this.words);
   }

   public boolean get(int b) {
      return BitSets.get(this.words, b);
   }

   public void set(int b) {
      this.ensureCapacity(BitSets.wordIndex(b) + 1);
      BitSets.set(this.words, b);
   }

   public void setRange(int lo, int hi) {
      this.ensureCapacity(BitSets.wordIndex(hi) + 1);
      BitSets.setRange(this.words, lo, hi);
   }

   public void clearRange(int lo, int hi) {
      this.ensureCapacity(BitSets.wordIndex(hi) + 1);
      BitSets.clearRange(this.words, lo, hi);
   }

   public void clear() {
      BitSets.clear(this.words);
   }

   public void clear(int index) {
      this.ensureCapacity(BitSets.wordIndex(index) + 1);
      BitSets.clear(this.words, index);
   }

   public void invert() {
      BitSets.invert(this.words);
   }

   public void intersect(TBitSet other) {
      BitSets.intersect(this.words, other.words);
   }

   public void subtract(TBitSet other) {
      BitSets.subtract(this.words, other.words);
   }

   public void union(TBitSet other) {
      this.ensureCapacity(other.words.length);
      BitSets.union(this.words, other.words);
   }

   public void union(Abstract128BitSet bs) {
      this.ensureCapacity(2);
      this.words[0] = this.words[0] | bs.getLo();
      this.words[1] = this.words[1] | bs.getHi();
   }

   public boolean isDisjoint(TBitSet other) {
      return BitSets.isDisjoint(this.words, other.words);
   }

   public boolean contains(TBitSet other) {
      return BitSets.contains(this.words, other.words);
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof TBitSet)) {
         return false;
      } else {
         TBitSet o = (TBitSet)obj;
         return BitSets.equals(this.words, o.words);
      }
   }

   @Override
   public int hashCode() {
      return BitSets.hashCode(this.words);
   }

   public OfInt iterator() {
      return BitSets.iterator(this.words);
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

   static {
      for (int i = 0; i < STATIC_INSTANCES.length; i++) {
         STATIC_INSTANCES[i] = new TBitSet(new long[]{i});
      }
   }
}
