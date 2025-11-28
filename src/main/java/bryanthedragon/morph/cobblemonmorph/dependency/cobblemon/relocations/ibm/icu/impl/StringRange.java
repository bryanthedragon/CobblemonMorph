package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.lang.CharSequences;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;

public class StringRange {
   private static final boolean DEBUG = false;
   public static final Comparator<int[]> COMPARE_INT_ARRAYS = new Comparator<int[]>() {
      public int compare(int[] o1, int[] o2) {
         int minIndex = Math.min(o1.length, o2.length);

         for (int i = 0; i < minIndex; i++) {
            int diff = o1[i] - o2[i];
            if (diff != 0) {
               return diff;
            }
         }

         return o1.length - o2.length;
      }
   };

   public static void compact(Set<String> source, StringRange.Adder adder, boolean shorterPairs, boolean moreCompact) {
      if (!moreCompact) {
         String start = null;
         String end = null;
         int lastCp = 0;
         int prefixLen = 0;

         for (String s : source) {
            if (start != null) {
               if (s.regionMatches(0, start, 0, prefixLen)) {
                  int currentCp = s.codePointAt(prefixLen);
                  if (currentCp == 1 + lastCp && s.length() == prefixLen + Character.charCount(currentCp)) {
                     end = s;
                     lastCp = currentCp;
                     continue;
                  }
               }

               adder.add(start, end == null ? null : (!shorterPairs ? end : end.substring(prefixLen, end.length())));
            }

            start = s;
            end = null;
            lastCp = s.codePointBefore(s.length());
            prefixLen = s.length() - Character.charCount(lastCp);
         }

         adder.add(start, end == null ? null : (!shorterPairs ? end : end.substring(prefixLen, end.length())));
      } else {
         Relation<Integer, StringRange.Ranges> lengthToArrays = Relation.of(new TreeMap<>(), TreeSet.class);

         for (String s : source) {
            StringRange.Ranges item = new StringRange.Ranges(s);
            lengthToArrays.put(item.size(), item);
         }

         for (Entry<Integer, Set<StringRange.Ranges>> entry : lengthToArrays.keyValuesSet()) {
            for (StringRange.Ranges ranges : compact(entry.getKey(), entry.getValue())) {
               adder.add(ranges.start(), ranges.end(shorterPairs));
            }
         }
      }
   }

   public static void compact(Set<String> source, StringRange.Adder adder, boolean shorterPairs) {
      compact(source, adder, shorterPairs, false);
   }

   private static LinkedList<StringRange.Ranges> compact(int size, Set<StringRange.Ranges> inputRanges) {
      LinkedList<StringRange.Ranges> ranges = new LinkedList<>(inputRanges);

      for (int i = size - 1; i >= 0; i--) {
         StringRange.Ranges last = null;
         Iterator<StringRange.Ranges> it = ranges.iterator();

         while (it.hasNext()) {
            StringRange.Ranges item = it.next();
            if (last == null) {
               last = item;
            } else if (last.merge(i, item)) {
               it.remove();
            } else {
               last = item;
            }
         }
      }

      return ranges;
   }

   public static Collection<String> expand(String start, String end, boolean requireSameLength, Collection<String> output) {
      if (start != null && end != null) {
         int[] startCps = CharSequences.codePoints(start);
         int[] endCps = CharSequences.codePoints(end);
         int startOffset = startCps.length - endCps.length;
         if (requireSameLength && startOffset != 0) {
            throw new ICUException("Range must have equal-length strings");
         } else if (startOffset < 0) {
            throw new ICUException("Range must have start-length ≥ end-length");
         } else if (endCps.length == 0) {
            throw new ICUException("Range must have end-length > 0");
         } else {
            StringBuilder builder = new StringBuilder();

            for (int i = 0; i < startOffset; i++) {
               builder.appendCodePoint(startCps[i]);
            }

            add(0, startOffset, startCps, endCps, builder, output);
            return output;
         }
      } else {
         throw new ICUException("Range must have 2 valid strings");
      }
   }

   private static void add(int endIndex, int startOffset, int[] starts, int[] ends, StringBuilder builder, Collection<String> output) {
      int start = starts[endIndex + startOffset];
      int end = ends[endIndex];
      if (start > end) {
         throw new ICUException("Range must have xᵢ ≤ yᵢ for each index i");
      } else {
         boolean last = endIndex == ends.length - 1;
         int startLen = builder.length();

         for (int i = start; i <= end; i++) {
            builder.appendCodePoint(i);
            if (last) {
               output.add(builder.toString());
            } else {
               add(endIndex + 1, startOffset, starts, ends, builder, output);
            }

            builder.setLength(startLen);
         }
      }
   }

   public interface Adder {
      void add(String var1, String var2);
   }

   static final class Range implements Comparable<StringRange.Range> {
      int min;
      int max;

      public Range(int min, int max) {
         this.min = min;
         this.max = max;
      }

      @Override
      public boolean equals(Object obj) {
         return this == obj || obj != null && obj instanceof StringRange.Range && this.compareTo((StringRange.Range)obj) == 0;
      }

      public int compareTo(StringRange.Range that) {
         int diff = this.min - that.min;
         return diff != 0 ? diff : this.max - that.max;
      }

      @Override
      public int hashCode() {
         return this.min * 37 + this.max;
      }

      @Override
      public String toString() {
         StringBuilder result = new StringBuilder().appendCodePoint(this.min);
         return this.min == this.max ? result.toString() : result.append('~').appendCodePoint(this.max).toString();
      }
   }

   static final class Ranges implements Comparable<StringRange.Ranges> {
      private final StringRange.Range[] ranges;

      public Ranges(String s) {
         int[] array = CharSequences.codePoints(s);
         this.ranges = new StringRange.Range[array.length];

         for (int i = 0; i < array.length; i++) {
            this.ranges[i] = new StringRange.Range(array[i], array[i]);
         }
      }

      public boolean merge(int pivot, StringRange.Ranges other) {
         for (int i = this.ranges.length - 1; i >= 0; i--) {
            if (i == pivot) {
               if (this.ranges[i].max != other.ranges[i].min - 1) {
                  return false;
               }
            } else if (!this.ranges[i].equals(other.ranges[i])) {
               return false;
            }
         }

         this.ranges[pivot].max = other.ranges[pivot].max;
         return true;
      }

      public String start() {
         StringBuilder result = new StringBuilder();

         for (int i = 0; i < this.ranges.length; i++) {
            result.appendCodePoint(this.ranges[i].min);
         }

         return result.toString();
      }

      public String end(boolean mostCompact) {
         int firstDiff = this.firstDifference();
         if (firstDiff == this.ranges.length) {
            return null;
         } else {
            StringBuilder result = new StringBuilder();

            for (int i = mostCompact ? firstDiff : 0; i < this.ranges.length; i++) {
               result.appendCodePoint(this.ranges[i].max);
            }

            return result.toString();
         }
      }

      public int firstDifference() {
         for (int i = 0; i < this.ranges.length; i++) {
            if (this.ranges[i].min != this.ranges[i].max) {
               return i;
            }
         }

         return this.ranges.length;
      }

      public Integer size() {
         return this.ranges.length;
      }

      public int compareTo(StringRange.Ranges other) {
         int diff = this.ranges.length - other.ranges.length;
         if (diff != 0) {
            return diff;
         } else {
            for (int i = 0; i < this.ranges.length; i++) {
               diff = this.ranges[i].compareTo(other.ranges[i]);
               if (diff != 0) {
                  return diff;
               }
            }

            return 0;
         }
      }

      @Override
      public String toString() {
         String start = this.start();
         String end = this.end(false);
         return end == null ? start : start + "~" + end;
      }
   }
}
