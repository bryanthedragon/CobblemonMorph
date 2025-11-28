package com.cobblemon.mod.relocations.ibm.icu.number;

public class IntegerWidth {
   static final IntegerWidth DEFAULT = new IntegerWidth(1, -1);
   final int minInt;
   final int maxInt;

   private IntegerWidth(int minInt, int maxInt) {
      this.minInt = minInt;
      this.maxInt = maxInt;
   }

   public static IntegerWidth zeroFillTo(int minInt) {
      if (minInt == 1) {
         return DEFAULT;
      } else if (minInt >= 0 && minInt <= 999) {
         return new IntegerWidth(minInt, -1);
      } else {
         throw new IllegalArgumentException("Integer digits must be between 0 and 999 (inclusive)");
      }
   }

   public IntegerWidth truncateAt(int maxInt) {
      if (maxInt == this.maxInt) {
         return this;
      } else if (maxInt >= 0 && maxInt <= 999 && maxInt >= this.minInt) {
         return new IntegerWidth(this.minInt, maxInt);
      } else if (this.minInt == 1 && maxInt == -1) {
         return DEFAULT;
      } else if (maxInt == -1) {
         return new IntegerWidth(this.minInt, -1);
      } else {
         throw new IllegalArgumentException("Integer digits must be between -1 and 999 (inclusive)");
      }
   }
}
