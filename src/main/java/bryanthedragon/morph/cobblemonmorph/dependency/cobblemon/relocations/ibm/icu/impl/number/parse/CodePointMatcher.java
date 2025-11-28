package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.impl.StringSegment;

public class CodePointMatcher implements NumberParseMatcher {
   private final int cp;

   public static CodePointMatcher getInstance(int cp) {
      return new CodePointMatcher(cp);
   }

   private CodePointMatcher(int cp) {
      this.cp = cp;
   }

   @Override
   public boolean match(StringSegment segment, ParsedNumber result) {
      if (segment.startsWith(this.cp)) {
         segment.adjustOffsetByCodePoint();
         result.setCharsConsumed(segment);
      }

      return false;
   }

   @Override
   public boolean smokeTest(StringSegment segment) {
      return segment.startsWith(this.cp);
   }

   @Override
   public void postProcess(ParsedNumber result) {
   }

   @Override
   public String toString() {
      return "<CodePointMatcher U+" + Integer.toHexString(this.cp) + ">";
   }
}
