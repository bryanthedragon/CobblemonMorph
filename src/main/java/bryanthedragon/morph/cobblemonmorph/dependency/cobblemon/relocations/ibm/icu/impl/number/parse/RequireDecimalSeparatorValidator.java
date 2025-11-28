package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

public class RequireDecimalSeparatorValidator extends ValidationMatcher {
   private static final RequireDecimalSeparatorValidator A = new RequireDecimalSeparatorValidator(true);
   private static final RequireDecimalSeparatorValidator B = new RequireDecimalSeparatorValidator(false);
   private final boolean patternHasDecimalSeparator;

   public static RequireDecimalSeparatorValidator getInstance(boolean patternHasDecimalSeparator) {
      return patternHasDecimalSeparator ? A : B;
   }

   private RequireDecimalSeparatorValidator(boolean patternHasDecimalSeparator) {
      this.patternHasDecimalSeparator = patternHasDecimalSeparator;
   }

   @Override
   public void postProcess(ParsedNumber result) {
      boolean parseHasDecimalSeparator = 0 != (result.flags & 32);
      if (parseHasDecimalSeparator != this.patternHasDecimalSeparator) {
         result.flags |= 256;
      }
   }

   @Override
   public String toString() {
      return "<RequireDecimalSeparator>";
   }
}
