package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.StaticUnicodeSets;
import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;
import com.cobblemon.mod.relocations.ibm.icu.text.DecimalFormatSymbols;

public class InfinityMatcher extends SymbolMatcher {
   private static final InfinityMatcher DEFAULT = new InfinityMatcher();

   public static InfinityMatcher getInstance(DecimalFormatSymbols symbols) {
      String symbolString = symbols.getInfinity();
      return DEFAULT.uniSet.contains(symbolString) ? DEFAULT : new InfinityMatcher(symbolString);
   }

   private InfinityMatcher(String symbolString) {
      super(symbolString, DEFAULT.uniSet);
   }

   private InfinityMatcher() {
      super(StaticUnicodeSets.Key.INFINITY_SIGN);
   }

   @Override
   protected boolean isDisabled(ParsedNumber result) {
      return 0 != (result.flags & 128);
   }

   @Override
   protected void accept(StringSegment segment, ParsedNumber result) {
      result.flags |= 128;
      result.setCharsConsumed(segment);
   }

   @Override
   public String toString() {
      return "<InfinityMatcher>";
   }
}
