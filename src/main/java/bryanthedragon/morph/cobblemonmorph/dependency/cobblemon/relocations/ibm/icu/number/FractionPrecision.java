package com.cobblemon.mod.relocations.ibm.icu.number;

public abstract class FractionPrecision extends Precision {
   FractionPrecision() {
   }

   public Precision withSignificantDigits(int minSignificantDigits, int maxSignificantDigits, NumberFormatter.RoundingPriority priority) {
      if (maxSignificantDigits >= 1 && maxSignificantDigits >= minSignificantDigits && maxSignificantDigits <= 999) {
         return constructFractionSignificant(this, minSignificantDigits, maxSignificantDigits, priority, false);
      } else {
         throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
      }
   }

   public Precision withMinDigits(int minSignificantDigits) {
      if (minSignificantDigits >= 1 && minSignificantDigits <= 999) {
         return constructFractionSignificant(this, 1, minSignificantDigits, NumberFormatter.RoundingPriority.RELAXED, true);
      } else {
         throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
      }
   }

   public Precision withMaxDigits(int maxSignificantDigits) {
      if (maxSignificantDigits >= 1 && maxSignificantDigits <= 999) {
         return constructFractionSignificant(this, 1, maxSignificantDigits, NumberFormatter.RoundingPriority.STRICT, true);
      } else {
         throw new IllegalArgumentException("Significant digits must be between 1 and 999 (inclusive)");
      }
   }
}
