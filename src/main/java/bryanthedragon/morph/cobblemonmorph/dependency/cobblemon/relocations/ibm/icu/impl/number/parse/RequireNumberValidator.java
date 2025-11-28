package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

public class RequireNumberValidator extends ValidationMatcher {
   @Override
   public void postProcess(ParsedNumber result) {
      if (!result.seenNumber()) {
         result.flags |= 256;
      }
   }

   @Override
   public String toString() {
      return "<RequireNumber>";
   }
}
