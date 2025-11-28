package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;

public abstract class ValidationMatcher implements NumberParseMatcher {
   @Override
   public boolean match(StringSegment segment, ParsedNumber result) {
      return false;
   }

   @Override
   public boolean smokeTest(StringSegment segment) {
      return false;
   }
}
