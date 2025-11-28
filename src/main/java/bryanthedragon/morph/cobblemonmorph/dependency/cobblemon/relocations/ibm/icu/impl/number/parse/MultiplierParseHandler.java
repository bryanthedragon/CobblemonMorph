package com.cobblemon.mod.relocations.ibm.icu.impl.number.parse;

import com.cobblemon.mod.relocations.ibm.icu.number.Scale;

public class MultiplierParseHandler extends ValidationMatcher {
   private final Scale multiplier;

   public MultiplierParseHandler(Scale multiplier) {
      this.multiplier = multiplier;
   }

   @Override
   public void postProcess(ParsedNumber result) {
      if (result.quantity != null) {
         this.multiplier.applyReciprocalTo(result.quantity);
      }
   }

   @Override
   public String toString() {
      return "<MultiplierHandler " + this.multiplier + ">";
   }
}
