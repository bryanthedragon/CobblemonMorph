package com.cobblemon.mod.relocations.ibm.icu.number;

import com.cobblemon.mod.relocations.ibm.icu.util.Currency;

public abstract class CurrencyPrecision extends Precision {
   CurrencyPrecision() {
   }

   public Precision withCurrency(Currency currency) {
      if (currency != null) {
         Precision retval = constructFromCurrency(this, currency);
         retval.trailingZeroDisplay = this.trailingZeroDisplay;
         return retval;
      } else {
         throw new IllegalArgumentException("Currency must not be null");
      }
   }
}
