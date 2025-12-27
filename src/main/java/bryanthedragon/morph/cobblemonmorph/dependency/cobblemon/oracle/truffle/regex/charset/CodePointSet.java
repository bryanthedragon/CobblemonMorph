package com.oracle.truffle.regex.charset;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.IntRangesBuffer;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import com.oracle.truffle.regex.util.EmptyArrays;
import java.util.Arrays;

public final class CodePointSet extends ImmutableSortedListOfIntRanges implements Comparable<CodePointSet>, JsonConvertible {
   private static final CodePointSet CONSTANT_EMPTY = new CodePointSet(EmptyArrays.INT);
   private static final CodePointSet[] CONSTANT_ASCII = new CodePointSet[128];
   private static final CodePointSet[] CONSTANT_CASE_FOLD_ASCII = new CodePointSet[26];

   private CodePointSet(int[] ranges) {
      super(ranges);

      assert ranges.length == 0 || ranges[0] >= 0 && ranges[ranges.length - 1] >= 0;
   }

   public int[] getRanges() {
      return this.ranges;
   }

   public static CodePointSet getEmpty() {
      return CONSTANT_EMPTY;
   }

   public static CodePointSet createNoDedup(int... ranges) {
      return new CodePointSet(ranges);
   }

   public static CodePointSet create(int single) {
      return single < 128 ? CONSTANT_ASCII[single] : new CodePointSet(new int[]{single, single});
   }

   public static CodePointSet create(int... ranges) {
      CodePointSet constant = checkConstants(ranges, ranges.length);
      return constant == null ? new CodePointSet(ranges) : constant;
   }

   public static CodePointSet create(IntRangesBuffer buf) {
      CodePointSet constant = checkConstants(buf.getBuffer(), buf.length());
      return constant == null ? new CodePointSet(buf.toArray()) : constant;
   }

   private static CodePointSet checkConstants(int[] ranges, int length) {
      if (length == 0) {
         return CONSTANT_EMPTY;
      } else if (length == 2 && ranges[0] == ranges[1] && ranges[0] < 128) {
         return CONSTANT_ASCII[ranges[0]];
      } else if (length == 4 && ranges[0] == ranges[1] && ranges[0] >= 65 && ranges[0] <= 90 && ranges[2] == ranges[3] && ranges[2] == (ranges[0] | 32)) {
         return CONSTANT_CASE_FOLD_ASCII[ranges[0] - 65];
      } else {
         for (CodePointSet predefCC : Constants.CONSTANT_CODE_POINT_SETS) {
            if (predefCC.ranges.length == length && rangesEqual(predefCC.ranges, ranges, length)) {
               return predefCC;
            }
         }

         return null;
      }
   }

   public CodePointSet createEmpty() {
      return getEmpty();
   }

   public CodePointSet create(RangesBuffer buffer) {
      assert buffer instanceof IntRangesBuffer;

      return create((IntRangesBuffer)buffer);
   }

   @Override
   public boolean equalsBuffer(RangesBuffer buffer) {
      assert buffer instanceof IntRangesBuffer;

      IntRangesBuffer buf = (IntRangesBuffer)buffer;
      return this.ranges.length == buf.length() && rangesEqual(this.ranges, buf.getBuffer(), this.ranges.length);
   }

   public CodePointSet createInverse(Encodings.Encoding encoding) {
      return createInverse(this, encoding);
   }

   public static CodePointSet createInverse(SortedListOfRanges src, Encodings.Encoding encoding) {
      return src.matchesNothing() ? encoding.getFullSet() : new CodePointSet(createInverseArray(src, encoding));
   }

   @Override
   public <T extends ImmutableSortedListOfRanges> T createIntersectionSingleRange(T o) {
      assert this.size() == 1 && !o.isEmpty();

      if (this.getMin() <= o.getMin() && this.getMax() >= o.getMax()) {
         return o;
      } else {
         int iLo = 0;
         int iHi = o.size() - 1;

         while (iLo < o.size() && o.getHi(iLo) < this.getMin()) {
            iLo++;
         }

         while (iHi >= 0 && o.getLo(iHi) > this.getMax()) {
            iHi--;
         }

         if (iHi < iLo) {
            return (T)this.createEmpty();
         } else {
            int[] intersection = Arrays.copyOfRange(((CodePointSet)o).ranges, iLo * 2, (iHi + 1) * 2);
            intersection[0] = Math.max(intersection[0], this.getMin());
            intersection[intersection.length - 1] = Math.min(intersection[intersection.length - 1], this.getMax());
            return (T)create(intersection);
         }
      }
   }

   public int compareTo(CodePointSet o) {
      if (this == o) {
         return 0;
      } else {
         int cmp = this.size() - o.size();
         if (cmp != 0) {
            return cmp;
         } else {
            for (int i = 0; i < this.size(); i++) {
               cmp = this.getLo(i) - o.getLo(i);
               if (cmp != 0) {
                  return cmp;
               }
            }

            return cmp;
         }
      }
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof CodePointSet) {
         return Arrays.equals(this.ranges, ((CodePointSet)obj).ranges);
      } else {
         return obj instanceof SortedListOfRanges ? this.equalsListOfRanges((SortedListOfRanges)obj) : false;
      }
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.ranges);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return Json.array(this.ranges);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return this.defaultToString();
   }

   @CompilerDirectives.TruffleBoundary
   public String dumpRaw() {
      StringBuilder sb = new StringBuilder(this.size() * 20);

      for (int i = 0; i < this.size(); i++) {
         if (i > 0) {
            sb.append(",");
         }

         sb.append(String.format("0x%06x, 0x%06x", this.getLo(i), this.getHi(i)));
      }

      return sb.toString();
   }

   @Override
   public int[] toArray() {
      return this.getRanges();
   }

   public byte[] inverseToByteArray(Encodings.Encoding encoding) {
      byte[] array = new byte[this.inverseValueCount(encoding)];
      int index = 0;
      int lastHi = -1;

      for (int i = 0; i < this.size(); i++) {
         for (int j = lastHi + 1; j < this.getLo(i); j++) {
            assert j <= 255;

            array[index++] = (byte)j;
         }

         lastHi = this.getHi(i);
      }

      for (int j = lastHi + 1; j <= encoding.getMaxValue(); j++) {
         assert j <= 255;

         array[index++] = (byte)j;
      }

      return array;
   }

   public char[] inverseToCharArray(Encodings.Encoding encoding) {
      char[] array = new char[this.inverseValueCount(encoding)];
      int index = 0;
      int lastHi = -1;

      for (int i = 0; i < this.size(); i++) {
         for (int j = lastHi + 1; j < this.getLo(i); j++) {
            assert j <= 65535;

            array[index++] = (char)j;
         }

         lastHi = this.getHi(i);
      }

      for (int j = lastHi + 1; j <= encoding.getMaxValue(); j++) {
         assert j <= 65535;

         array[index++] = (char)j;
      }

      return array;
   }

   public int[] inverseToIntArray(Encodings.Encoding encoding) {
      int[] array = new int[this.inverseValueCount(encoding)];
      int index = 0;
      int lastHi = -1;

      for (int i = 0; i < this.size(); i++) {
         int j = lastHi + 1;

         while (j < this.getLo(i)) {
            array[index++] = j++;
         }

         lastHi = this.getHi(i);
      }

      int j = lastHi + 1;

      while (j <= encoding.getMaxValue()) {
         array[index++] = j++;
      }

      return array;
   }

   static {
      CONSTANT_ASCII[0] = new CodePointSet(new int[]{0, 0});

      for (int i = 1; i < 128; i++) {
         CONSTANT_ASCII[i] = new CodePointSet(new int[]{i, i});
      }

      for (int i = 65; i <= 90; i++) {
         CONSTANT_CASE_FOLD_ASCII[i - 65] = new CodePointSet(new int[]{i, i, Character.toLowerCase(i), Character.toLowerCase(i)});
      }
   }
}
