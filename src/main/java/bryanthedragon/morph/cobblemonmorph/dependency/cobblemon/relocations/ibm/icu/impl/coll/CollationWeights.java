package com.cobblemon.mod.relocations.ibm.icu.impl.coll;

import java.util.Arrays;

public final class CollationWeights {
   private int middleLength;
   private int[] minBytes = new int[5];
   private int[] maxBytes = new int[5];
   private CollationWeights.WeightRange[] ranges = new CollationWeights.WeightRange[7];
   private int rangeIndex;
   private int rangeCount;

   public void initForPrimary(boolean compressible) {
      this.middleLength = 1;
      this.minBytes[1] = 3;
      this.maxBytes[1] = 255;
      if (compressible) {
         this.minBytes[2] = 4;
         this.maxBytes[2] = 254;
      } else {
         this.minBytes[2] = 2;
         this.maxBytes[2] = 255;
      }

      this.minBytes[3] = 2;
      this.maxBytes[3] = 255;
      this.minBytes[4] = 2;
      this.maxBytes[4] = 255;
   }

   public void initForSecondary() {
      this.middleLength = 3;
      this.minBytes[1] = 0;
      this.maxBytes[1] = 0;
      this.minBytes[2] = 0;
      this.maxBytes[2] = 0;
      this.minBytes[3] = 2;
      this.maxBytes[3] = 255;
      this.minBytes[4] = 2;
      this.maxBytes[4] = 255;
   }

   public void initForTertiary() {
      this.middleLength = 3;
      this.minBytes[1] = 0;
      this.maxBytes[1] = 0;
      this.minBytes[2] = 0;
      this.maxBytes[2] = 0;
      this.minBytes[3] = 2;
      this.maxBytes[3] = 63;
      this.minBytes[4] = 2;
      this.maxBytes[4] = 63;
   }

   public boolean allocWeights(long lowerLimit, long upperLimit, int n) {
      if (!this.getWeightRanges(lowerLimit, upperLimit)) {
         return false;
      } else {
         while (true) {
            int minLength = this.ranges[0].length;
            if (this.allocWeightsInShortRanges(n, minLength)) {
               break;
            }

            if (minLength == 4) {
               return false;
            }

            if (this.allocWeightsInMinLengthRanges(n, minLength)) {
               break;
            }

            for (int i = 0; i < this.rangeCount && this.ranges[i].length == minLength; i++) {
               this.lengthenRange(this.ranges[i]);
            }
         }

         this.rangeIndex = 0;
         if (this.rangeCount < this.ranges.length) {
            this.ranges[this.rangeCount] = null;
         }

         return true;
      }
   }

   public long nextWeight() {
      if (this.rangeIndex >= this.rangeCount) {
         return 4294967295L;
      } else {
         CollationWeights.WeightRange range = this.ranges[this.rangeIndex];
         long weight = range.start;
         if (--range.count == 0) {
            this.rangeIndex++;
         } else {
            range.start = this.incWeight(weight, range.length);

            assert range.start <= range.end;
         }

         return weight;
      }
   }

   public static int lengthOfWeight(long weight) {
      if ((weight & 16777215L) == 0L) {
         return 1;
      } else if ((weight & 65535L) == 0L) {
         return 2;
      } else {
         return (weight & 255L) == 0L ? 3 : 4;
      }
   }

   private static int getWeightTrail(long weight, int length) {
      return (int)(weight >> 8 * (4 - length)) & 0xFF;
   }

   private static long setWeightTrail(long weight, int length, int trail) {
      length = 8 * (4 - length);
      return weight & 4294967040L << length | (long)trail << length;
   }

   private static int getWeightByte(long weight, int idx) {
      return getWeightTrail(weight, idx);
   }

   private static long setWeightByte(long weight, int idx, int b) {
      idx *= 8;
      long mask;
      if (idx < 32) {
         mask = 4294967295L >> idx;
      } else {
         mask = 0L;
      }

      idx = 32 - idx;
      mask |= 4294967040L << idx;
      return weight & mask | (long)b << idx;
   }

   private static long truncateWeight(long weight, int length) {
      return weight & 4294967295L << 8 * (4 - length);
   }

   private static long incWeightTrail(long weight, int length) {
      return weight + (1L << 8 * (4 - length));
   }

   private static long decWeightTrail(long weight, int length) {
      return weight - (1L << 8 * (4 - length));
   }

   private int countBytes(int idx) {
      return this.maxBytes[idx] - this.minBytes[idx] + 1;
   }

   private long incWeight(long weight, int length) {
      do {
         int b = getWeightByte(weight, length);
         if (b < this.maxBytes[length]) {
            return setWeightByte(weight, length, b + 1);
         }

         weight = setWeightByte(weight, length, this.minBytes[length]);
      } while ($assertionsDisabled || --length > 0);

      throw new AssertionError();
   }

   private long incWeightByOffset(long weight, int length, int offset) {
      do {
         offset += getWeightByte(weight, length);
         if (offset <= this.maxBytes[length]) {
            return setWeightByte(weight, length, offset);
         }

         offset -= this.minBytes[length];
         weight = setWeightByte(weight, length, this.minBytes[length] + offset % this.countBytes(length));
         offset /= this.countBytes(length);
      } while ($assertionsDisabled || --length > 0);

      throw new AssertionError();
   }

   private void lengthenRange(CollationWeights.WeightRange range) {
      int length = range.length + 1;
      range.start = setWeightTrail(range.start, length, this.minBytes[length]);
      range.end = setWeightTrail(range.end, length, this.maxBytes[length]);
      range.count = range.count * this.countBytes(length);
      range.length = length;
   }

   private boolean getWeightRanges(long lowerLimit, long upperLimit) {
      assert lowerLimit != 0L;

      assert upperLimit != 0L;

      int lowerLength = lengthOfWeight(lowerLimit);
      int upperLength = lengthOfWeight(upperLimit);

      assert lowerLength >= this.middleLength;

      if (lowerLimit >= upperLimit) {
         return false;
      } else if (lowerLength < upperLength && lowerLimit == truncateWeight(upperLimit, lowerLength)) {
         return false;
      } else {
         CollationWeights.WeightRange[] lower = new CollationWeights.WeightRange[5];
         CollationWeights.WeightRange middle = new CollationWeights.WeightRange();
         CollationWeights.WeightRange[] upper = new CollationWeights.WeightRange[5];
         long weight = lowerLimit;

         for (int length = lowerLength; length > this.middleLength; length--) {
            int trail = getWeightTrail(weight, length);
            if (trail < this.maxBytes[length]) {
               lower[length] = new CollationWeights.WeightRange();
               lower[length].start = incWeightTrail(weight, length);
               lower[length].end = setWeightTrail(weight, length, this.maxBytes[length]);
               lower[length].length = length;
               lower[length].count = this.maxBytes[length] - trail;
            }

            weight = truncateWeight(weight, length - 1);
         }

         if (weight < 4278190080L) {
            middle.start = incWeightTrail(weight, this.middleLength);
         } else {
            middle.start = 4294967295L;
         }

         weight = upperLimit;

         for (int length = upperLength; length > this.middleLength; length--) {
            int trail = getWeightTrail(weight, length);
            if (trail > this.minBytes[length]) {
               upper[length] = new CollationWeights.WeightRange();
               upper[length].start = setWeightTrail(weight, length, this.minBytes[length]);
               upper[length].end = decWeightTrail(weight, length);
               upper[length].length = length;
               upper[length].count = trail - this.minBytes[length];
            }

            weight = truncateWeight(weight, length - 1);
         }

         middle.end = decWeightTrail(weight, this.middleLength);
         middle.length = this.middleLength;
         if (middle.end >= middle.start) {
            middle.count = (int)(middle.end - middle.start >> 8 * (4 - this.middleLength)) + 1;
         } else {
            for (int length = 4; length > this.middleLength; length--) {
               if (lower[length] != null && upper[length] != null && lower[length].count > 0 && upper[length].count > 0) {
                  long lowerEnd = lower[length].end;
                  long upperStart = upper[length].start;
                  boolean merged = false;
                  if (lowerEnd > upperStart) {
                     assert truncateWeight(lowerEnd, length - 1) == truncateWeight(upperStart, length - 1);

                     lower[length].end = upper[length].end;
                     lower[length].count = getWeightTrail(lower[length].end, length) - getWeightTrail(lower[length].start, length) + 1;
                     merged = true;
                  } else if (lowerEnd == upperStart) {
                     assert this.minBytes[length] < this.maxBytes[length];
                  } else if (this.incWeight(lowerEnd, length) == upperStart) {
                     lower[length].end = upper[length].end;
                     lower[length].count = lower[length].count + upper[length].count;
                     merged = true;
                  }

                  if (merged) {
                     upper[length].count = 0;

                     while (--length > this.middleLength) {
                        lower[length] = upper[length] = null;
                     }
                     break;
                  }
               }
            }
         }

         this.rangeCount = 0;
         if (middle.count > 0) {
            this.ranges[0] = middle;
            this.rangeCount = 1;
         }

         for (int lengthx = this.middleLength + 1; lengthx <= 4; lengthx++) {
            if (upper[lengthx] != null && upper[lengthx].count > 0) {
               this.ranges[this.rangeCount++] = upper[lengthx];
            }

            if (lower[lengthx] != null && lower[lengthx].count > 0) {
               this.ranges[this.rangeCount++] = lower[lengthx];
            }
         }

         return this.rangeCount > 0;
      }
   }

   private boolean allocWeightsInShortRanges(int n, int minLength) {
      for (int i = 0; i < this.rangeCount && this.ranges[i].length <= minLength + 1; i++) {
         if (n <= this.ranges[i].count) {
            if (this.ranges[i].length > minLength) {
               this.ranges[i].count = n;
            }

            this.rangeCount = i + 1;
            if (this.rangeCount > 1) {
               Arrays.sort(this.ranges, 0, this.rangeCount);
            }

            return true;
         }

         n -= this.ranges[i].count;
      }

      return false;
   }

   private boolean allocWeightsInMinLengthRanges(int n, int minLength) {
      int count = 0;

      int minLengthRangeCount;
      for (minLengthRangeCount = 0; minLengthRangeCount < this.rangeCount && this.ranges[minLengthRangeCount].length == minLength; minLengthRangeCount++) {
         count += this.ranges[minLengthRangeCount].count;
      }

      int nextCountBytes = this.countBytes(minLength + 1);
      if (n > count * nextCountBytes) {
         return false;
      } else {
         long start = this.ranges[0].start;
         long end = this.ranges[0].end;

         for (int i = 1; i < minLengthRangeCount; i++) {
            if (this.ranges[i].start < start) {
               start = this.ranges[i].start;
            }

            if (this.ranges[i].end > end) {
               end = this.ranges[i].end;
            }
         }

         int count2 = (n - count) / (nextCountBytes - 1);
         int count1 = count - count2;
         if (count2 == 0 || count1 + count2 * nextCountBytes < n) {
            count2++;

            assert --count1 + count2 * nextCountBytes >= n;
         }

         this.ranges[0].start = start;
         if (count1 == 0) {
            this.ranges[0].end = end;
            this.ranges[0].count = count;
            this.lengthenRange(this.ranges[0]);
            this.rangeCount = 1;
         } else {
            this.ranges[0].end = this.incWeightByOffset(start, minLength, count1 - 1);
            this.ranges[0].count = count1;
            if (this.ranges[1] == null) {
               this.ranges[1] = new CollationWeights.WeightRange();
            }

            this.ranges[1].start = this.incWeight(this.ranges[0].end, minLength);
            this.ranges[1].end = end;
            this.ranges[1].length = minLength;
            this.ranges[1].count = count2;
            this.lengthenRange(this.ranges[1]);
            this.rangeCount = 2;
         }

         return true;
      }
   }

   private static final class WeightRange implements Comparable<CollationWeights.WeightRange> {
      long start;
      long end;
      int length;
      int count;

      private WeightRange() {
      }

      public int compareTo(CollationWeights.WeightRange other) {
         long l = this.start;
         long r = other.start;
         if (l < r) {
            return -1;
         } else {
            return l > r ? 1 : 0;
         }
      }
   }
}
