package com.oracle.truffle.regex.charset;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.buffer.IntRangesBuffer;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.util.BitSets;

public abstract class ImmutableSortedListOfIntRanges implements ImmutableSortedListOfRanges {
   protected final int[] ranges;

   protected ImmutableSortedListOfIntRanges(int[] ranges) {
      this.ranges = ranges;

      assert (ranges.length & 1) == 0 : "ranges array must have an even length!";

      assert this.rangesAreSortedNonAdjacentAndDisjoint();
   }

   @Override
   public int getLo(int i) {
      return this.ranges[i * 2];
   }

   @Override
   public int getHi(int i) {
      return this.ranges[i * 2 + 1];
   }

   @Override
   public int size() {
      return this.ranges.length / 2;
   }

   public IntRangesBuffer getBuffer1(CompilationBuffer compilationBuffer) {
      return compilationBuffer.getIntRangesBuffer1();
   }

   public IntRangesBuffer getBuffer2(CompilationBuffer compilationBuffer) {
      return compilationBuffer.getIntRangesBuffer2();
   }

   public IntRangesBuffer getBuffer3(CompilationBuffer compilationBuffer) {
      return compilationBuffer.getIntRangesBuffer3();
   }

   public IntRangesBuffer createTempBuffer() {
      return new IntRangesBuffer();
   }

   @Override
   public void appendRangesTo(RangesBuffer buffer, int startIndex, int endIndex) {
      int bulkLength = (endIndex - startIndex) * 2;
      if (bulkLength != 0) {
         assert buffer instanceof IntRangesBuffer;

         IntRangesBuffer buf = (IntRangesBuffer)buffer;
         int newSize = buf.length() + bulkLength;
         buf.ensureCapacity(newSize);

         assert buf.isEmpty() || this.rightOf(startIndex, buf, buf.size() - 1);

         System.arraycopy(this.ranges, startIndex * 2, buf.getBuffer(), buf.length(), bulkLength);
         buf.setLength(newSize);
      }
   }

   public abstract int[] toArray();

   public boolean inverseIsSameHighByte(Encodings.Encoding encoding) {
      if (this.isEmpty()) {
         return false;
      } else {
         return BitSets.highByte(this.getMin()) == BitSets.highByte(this.getMax())
            ? false
            : this.matchesMinAndMax(encoding) && BitSets.highByte(this.getHi(0) + 1) == BitSets.highByte(this.getLo(this.size() - 1) - 1);
      }
   }

   protected static int[] createInverseArray(SortedListOfRanges src, Encodings.Encoding encoding) {
      int[] invRanges = new int[src.sizeOfInverse(encoding) * 2];
      int i = 0;
      if (src.getMin() > encoding.getMinValue()) {
         setRange(invRanges, i++, encoding.getMinValue(), src.getMin() - 1);
      }

      for (int ia = 1; ia < src.size(); ia++) {
         setRange(invRanges, i++, src.getHi(ia - 1) + 1, src.getLo(ia) - 1);
      }

      if (src.getMax() < encoding.getMaxValue()) {
         setRange(invRanges, i++, src.getMax() + 1, encoding.getMaxValue());
      }

      return invRanges;
   }

   private static void setRange(int[] arr, int i, int lo, int hi) {
      arr[i * 2] = lo;
      arr[i * 2 + 1] = hi;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return this.defaultToString();
   }

   protected static boolean rangesEqual(int[] a, int[] b, int length) {
      for (int i = 0; i < length; i++) {
         if (a[i] != b[i]) {
            return false;
         }
      }

      return true;
   }
}
