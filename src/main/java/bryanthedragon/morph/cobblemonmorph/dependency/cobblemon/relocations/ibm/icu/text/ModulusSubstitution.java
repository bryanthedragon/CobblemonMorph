package com.cobblemon.mod.relocations.ibm.icu.text;

import java.text.ParsePosition;

class ModulusSubstitution extends NFSubstitution {
   long divisor;
   private final NFRule ruleToUse;

   ModulusSubstitution(int pos, NFRule rule, NFRule rulePredecessor, NFRuleSet ruleSet, String description) {
      super(pos, ruleSet, description);
      this.divisor = rule.getDivisor();
      if (this.divisor == 0L) {
         throw new IllegalStateException(
            "Substitution with bad divisor (" + this.divisor + ") " + description.substring(0, pos) + " | " + description.substring(pos)
         );
      } else {
         if (description.equals(">>>")) {
            this.ruleToUse = rulePredecessor;
         } else {
            this.ruleToUse = null;
         }
      }
   }

   @Override
   public void setDivisor(int radix, short exponent) {
      this.divisor = NFRule.power(radix, exponent);
      if (this.divisor == 0L) {
         throw new IllegalStateException("Substitution with bad divisor");
      }
   }

   @Override
   public boolean equals(Object that) {
      if (super.equals(that)) {
         ModulusSubstitution that2 = (ModulusSubstitution)that;
         return this.divisor == that2.divisor;
      } else {
         return false;
      }
   }

   @Override
   public void doSubstitution(long number, StringBuilder toInsertInto, int position, int recursionCount) {
      if (this.ruleToUse == null) {
         super.doSubstitution(number, toInsertInto, position, recursionCount);
      } else {
         long numberToFormat = this.transformNumber(number);
         this.ruleToUse.doFormat(numberToFormat, toInsertInto, position + this.pos, recursionCount);
      }
   }

   @Override
   public void doSubstitution(double number, StringBuilder toInsertInto, int position, int recursionCount) {
      if (this.ruleToUse == null) {
         super.doSubstitution(number, toInsertInto, position, recursionCount);
      } else {
         double numberToFormat = this.transformNumber(number);
         this.ruleToUse.doFormat(numberToFormat, toInsertInto, position + this.pos, recursionCount);
      }
   }

   @Override
   public long transformNumber(long number) {
      return number % this.divisor;
   }

   @Override
   public double transformNumber(double number) {
      return Math.floor(number % this.divisor);
   }

   @Override
   public Number doParse(String text, ParsePosition parsePosition, double baseValue, double upperBound, boolean lenientParse, int nonNumericalExecutedRuleMask) {
      if (this.ruleToUse == null) {
         return super.doParse(text, parsePosition, baseValue, upperBound, lenientParse, nonNumericalExecutedRuleMask);
      } else {
         Number tempResult = this.ruleToUse.doParse(text, parsePosition, false, upperBound, nonNumericalExecutedRuleMask);
         if (parsePosition.getIndex() != 0) {
            double result = tempResult.doubleValue();
            result = this.composeRuleValue(result, baseValue);
            return (Number)(result == (long)result ? (long)result : new Double(result));
         } else {
            return tempResult;
         }
      }
   }

   @Override
   public double composeRuleValue(double newRuleValue, double oldRuleValue) {
      return oldRuleValue - oldRuleValue % this.divisor + newRuleValue;
   }

   @Override
   public double calcUpperBound(double oldUpperBound) {
      return this.divisor;
   }

   @Override
   public boolean isModulusSubstitution() {
      return true;
   }

   @Override
   char tokenChar() {
      return '>';
   }
}
