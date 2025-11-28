package com.cobblemon.mod.relocations.ibm.icu.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class CodePointMap implements Iterable<CodePointMap.Range> {
   protected CodePointMap() {
   }

   public abstract int get(int var1);

   public abstract boolean getRange(int var1, CodePointMap.ValueFilter var2, CodePointMap.Range var3);

   public boolean getRange(int start, CodePointMap.RangeOption option, int surrogateValue, CodePointMap.ValueFilter filter, CodePointMap.Range range) {
      assert option != null;

      if (!this.getRange(start, filter, range)) {
         return false;
      } else if (option == CodePointMap.RangeOption.NORMAL) {
         return true;
      } else {
         int surrEnd = option == CodePointMap.RangeOption.FIXED_ALL_SURROGATES ? '\udfff' : '\udbff';
         int end = range.end;
         if (end >= 55295 && start <= surrEnd) {
            if (range.value == surrogateValue) {
               if (end >= surrEnd) {
                  return true;
               }
            } else {
               if (start <= 55295) {
                  range.end = 55295;
                  return true;
               }

               range.value = surrogateValue;
               if (end > surrEnd) {
                  range.end = surrEnd;
                  return true;
               }
            }

            if (this.getRange(surrEnd + 1, filter, range) && range.value == surrogateValue) {
               range.start = start;
               return true;
            } else {
               range.start = start;
               range.end = surrEnd;
               range.value = surrogateValue;
               return true;
            }
         } else {
            return true;
         }
      }
   }

   @Override
   public Iterator<CodePointMap.Range> iterator() {
      return new CodePointMap.RangeIterator();
   }

   public CodePointMap.StringIterator stringIterator(CharSequence s, int sIndex) {
      return new CodePointMap.StringIterator(s, sIndex);
   }

   public static final class Range {
      private int start;
      private int end;
      private int value;

      public Range() {
         this.start = this.end = -1;
         this.value = 0;
      }

      public int getStart() {
         return this.start;
      }

      public int getEnd() {
         return this.end;
      }

      public int getValue() {
         return this.value;
      }

      public void set(int start, int end, int value) {
         this.start = start;
         this.end = end;
         this.value = value;
      }
   }

   private final class RangeIterator implements Iterator<CodePointMap.Range> {
      private CodePointMap.Range range = new CodePointMap.Range();

      private RangeIterator() {
      }

      @Override
      public boolean hasNext() {
         return -1 <= this.range.end && this.range.end < 1114111;
      }

      public CodePointMap.Range next() {
         if (CodePointMap.this.getRange(this.range.end + 1, null, this.range)) {
            return this.range;
         } else {
            throw new NoSuchElementException();
         }
      }

      @Override
      public final void remove() {
         throw new UnsupportedOperationException();
      }
   }

   public static enum RangeOption {
      NORMAL,
      FIXED_LEAD_SURROGATES,
      FIXED_ALL_SURROGATES;
   }

   public class StringIterator {
      @Deprecated
      protected CharSequence s;
      @Deprecated
      protected int sIndex;
      @Deprecated
      protected int c;
      @Deprecated
      protected int value;

      @Deprecated
      protected StringIterator(CharSequence s, int sIndex) {
         this.s = s;
         this.sIndex = sIndex;
         this.c = -1;
         this.value = 0;
      }

      public void reset(CharSequence s, int sIndex) {
         this.s = s;
         this.sIndex = sIndex;
         this.c = -1;
         this.value = 0;
      }

      public boolean next() {
         if (this.sIndex >= this.s.length()) {
            return false;
         } else {
            this.c = Character.codePointAt(this.s, this.sIndex);
            this.sIndex = this.sIndex + Character.charCount(this.c);
            this.value = CodePointMap.this.get(this.c);
            return true;
         }
      }

      public boolean previous() {
         if (this.sIndex <= 0) {
            return false;
         } else {
            this.c = Character.codePointBefore(this.s, this.sIndex);
            this.sIndex = this.sIndex - Character.charCount(this.c);
            this.value = CodePointMap.this.get(this.c);
            return true;
         }
      }

      public final int getIndex() {
         return this.sIndex;
      }

      public final int getCodePoint() {
         return this.c;
      }

      public final int getValue() {
         return this.value;
      }
   }

   public interface ValueFilter {
      int apply(int var1);
   }
}
