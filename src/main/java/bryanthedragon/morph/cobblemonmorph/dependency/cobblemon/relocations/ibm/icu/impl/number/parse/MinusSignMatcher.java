package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.StaticUnicodeSets;
import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;
import com.cobblemon.mod.relocations.ibm.icu.text.DecimalFormatSymbols;

public class MinusSignMatcher extends SymbolMatcher {
   private static final MinusSignMatcher DEFAULT = new MinusSignMatcher(false);
   private static final MinusSignMatcher DEFAULT_ALLOW_TRAILING = new MinusSignMatcher(true);
   private final boolean allowTrailing;

   public static MinusSignMatcher getInstance(DecimalFormatSymbols symbols, boolean allowTrailing) {
      String symbolString = symbols.getMinusSignString();
      if (DEFAULT.uniSet.contains(symbolString)) {
         return allowTrailing ? DEFAULT_ALLOW_TRAILING : DEFAULT;
      } else {
         return new MinusSignMatcher(symbolString, allowTrailing);
      }
   }

   private MinusSignMatcher(String symbolString, boolean allowTrailing) {
      super(symbolString, DEFAULT.uniSet);
      this.allowTrailing = allowTrailing;
   }

   private MinusSignMatcher(boolean allowTrailing) {
      super(StaticUnicodeSets.Key.MINUS_SIGN);
      this.allowTrailing = allowTrailing;
   }

   @Override
   protected boolean isDisabled(ParsedNumber result) {
      return !this.allowTrailing && result.seenNumber();
   }

   @Override
   protected void accept(StringSegment segment, ParsedNumber result) {
      result.flags |= 1;
      result.setCharsConsumed(segment);
   }

   @Override
   public String toString() {
      return "<MinusSignMatcher>";
   }
}
