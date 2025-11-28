package com.cobblemon.mod.relocations.ibm.icu.text;

class MultiplierSubstitution extends NFSubstitution {
   long divisor;

   MultiplierSubstitution(int pos, NFRule rule, NFRuleSet ruleSet, String description) {
      super(pos, ruleSet, description);
      this.divisor = rule.getDivisor();
      if (this.divisor == 0L) {
         throw new IllegalStateException("Substitution with divisor 0 " + description.substring(0, pos) + " | " + description.substring(pos));
      }
   }

   @Override
   public void setDivisor(int radix, short exponent) {
      this.divisor = NFRule.power(radix, exponent);
      if (this.divisor == 0L) {
         throw new IllegalStateException("Substitution with divisor 0");
      }
   }

   @Override
   public boolean equals(Object that) {
      return super.equals(that) && this.divisor == ((MultiplierSubstitution)that).divisor;
   }

   @Override
   public long transformNumber(long number) {
      return (long)Math.floor(number / this.divisor);
   }

   @Override
   public double transformNumber(double number) {
      return this.ruleSet == null ? number / this.divisor : Math.floor(number / this.divisor);
   }

   @Override
   public double composeRuleValue(double newRuleValue, double oldRuleValue) {
      return newRuleValue * this.divisor;
   }

   @Override
   public double calcUpperBound(double oldUpperBound) {
      return this.divisor;
   }

   @Override
   char tokenChar() {
      return '<';
   }
}
