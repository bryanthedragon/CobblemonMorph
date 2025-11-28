package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;

public interface NumberParseMatcher {
   boolean match(StringSegment var1, ParsedNumber var2);

   boolean smokeTest(StringSegment var1);

   void postProcess(ParsedNumber var1);

   public interface Flexible {
   }
}
