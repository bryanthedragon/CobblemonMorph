package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

public class RequireAffixValidator extends ValidationMatcher {
   @Override
   public void postProcess(ParsedNumber result) {
      if (result.prefix == null || result.suffix == null) {
         result.flags |= 256;
      }
   }

   @Override
   public String toString() {
      return "<RequireAffix>";
   }
}
