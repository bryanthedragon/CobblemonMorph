package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

public class RequireCurrencyValidator extends ValidationMatcher {
   @Override
   public void postProcess(ParsedNumber result) {
      if (result.currencyCode == null) {
         result.flags |= 256;
      }
   }

   @Override
   public String toString() {
      return "<RequireCurrency>";
   }
}
