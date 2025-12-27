package com.oracle.truffle.regex.tregex.buffer;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.Range;
import com.oracle.truffle.regex.charset.RangesBuffer;
import java.util.Iterator;

public class IntRangesBuffer extends IntArrayBuffer implements RangesBuffer {
   public IntRangesBuffer() {
      this(16);
   }

   public IntRangesBuffer(int initialSize) {
      super(initialSize);
   }

   @Override
   public int getLo(int i) {
      return this.buf[i * 2];
   }

   @Override
   public int getHi(int i) {
      return this.buf[i * 2 + 1];
   }

   @Override
   public int size() {
      return this.length() / 2;
   }

   @Override
   public void appendRange(int lo, int hi) {
      assert this.isEmpty() || this.leftOf(this.size() - 1, lo, hi) && !this.adjacent(this.size() - 1, lo, hi);

      this.add(lo);
      this.add(hi);
   }

   public void appendRangeAllowAdjacent(int lo, int hi) {
      assert this.isEmpty() || this.leftOf(this.size() - 1, lo, hi);

      this.add(lo);
      this.add(hi);
   }

   @Override
   public void insertRange(int index, int lo, int hi) {
      assert index >= 0 && index < this.size();

      assert index == 0 || this.leftOf(index - 1, lo, hi) && !this.adjacent(index - 1, lo, hi);

      assert this.rightOf(index, lo, hi) && !this.adjacent(index, lo, hi);

      this.ensureCapacity(this.length + 2);
      int i = index * 2;
      System.arraycopy(this.buf, i, this.buf, i + 2, this.length - i);
      this.buf[i] = lo;
      this.buf[i + 1] = hi;
      this.length += 2;
   }

   @Override
   public void replaceRanges(int fromIndex, int toIndex, int lo, int hi) {
      assert fromIndex >= 0 && fromIndex < toIndex && toIndex >= 0 && toIndex <= this.size();

      assert fromIndex == 0 || this.leftOf(fromIndex - 1, lo, hi) && !this.adjacent(fromIndex - 1, lo, hi);

      assert toIndex == this.size() || this.rightOf(toIndex, lo, hi) && !this.adjacent(toIndex, lo, hi);

      this.buf[fromIndex * 2] = lo;
      this.buf[fromIndex * 2 + 1] = hi;
      if (toIndex < this.size()) {
         System.arraycopy(this.buf, toIndex * 2, this.buf, fromIndex * 2 + 2, this.length - toIndex * 2);
      }

      this.length -= (toIndex - (fromIndex + 1)) * 2;
   }

   @Override
   public void appendRangesTo(RangesBuffer buffer, int startIndex, int endIndex) {
      assert buffer instanceof IntRangesBuffer;

      int bulkLength = (endIndex - startIndex) * 2;
      if (bulkLength != 0) {
         IntRangesBuffer o = (IntRangesBuffer)buffer;
         int newSize = o.length() + bulkLength;
         o.ensureCapacity(newSize);

         assert o.isEmpty() || this.rightOf(startIndex, o, o.size() - 1);

         System.arraycopy(this.buf, startIndex * 2, o.getBuffer(), o.length(), bulkLength);
         o.setLength(newSize);
      }
   }

   public IntRangesBuffer create() {
      return new IntRangesBuffer(this.buf.length);
   }

   public Iterator<Range> rangesIterator() {
      return new IntRangesBuffer.IntRangesBufferRangesIterator(this);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return this.defaultToString();
   }

   private static final class IntRangesBufferRangesIterator implements Iterator<Range> {
      private final IntRangesBuffer buf;
      private int i = 0;

      private IntRangesBufferRangesIterator(IntRangesBuffer buf) {
         this.buf = buf;
      }

      @Override
      public boolean hasNext() {
         return this.i < this.buf.size();
      }

      public Range next() {
         Range ret = new Range(this.buf.getLo(this.i), this.buf.getHi(this.i));
         this.i++;
         return ret;
      }
   }
}
