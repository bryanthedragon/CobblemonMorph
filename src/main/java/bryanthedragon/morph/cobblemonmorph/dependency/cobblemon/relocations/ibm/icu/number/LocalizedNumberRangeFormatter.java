package com.cobblemon.mod.relocations.ibm.icu.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;

public class LocalizedNumberRangeFormatter extends NumberRangeFormatterSettings<LocalizedNumberRangeFormatter> {
   private volatile NumberRangeFormatterImpl fImpl;

   LocalizedNumberRangeFormatter(NumberRangeFormatterSettings<?> parent, int key, Object value) {
      super(parent, key, value);
   }

   public FormattedNumberRange formatRange(int first, int second) {
      DecimalQuantity dq1 = new DecimalQuantity_DualStorageBCD(first);
      DecimalQuantity dq2 = new DecimalQuantity_DualStorageBCD(second);
      return this.formatImpl(dq1, dq2, first == second);
   }

   public FormattedNumberRange formatRange(double first, double second) {
      DecimalQuantity dq1 = new DecimalQuantity_DualStorageBCD(first);
      DecimalQuantity dq2 = new DecimalQuantity_DualStorageBCD(second);
      return this.formatImpl(dq1, dq2, first == second);
   }

   public FormattedNumberRange formatRange(Number first, Number second) {
      if (first != null && second != null) {
         DecimalQuantity dq1 = new DecimalQuantity_DualStorageBCD(first);
         DecimalQuantity dq2 = new DecimalQuantity_DualStorageBCD(second);
         return this.formatImpl(dq1, dq2, first.equals(second));
      } else {
         throw new IllegalArgumentException("Cannot format null values in range");
      }
   }

   FormattedNumberRange formatImpl(DecimalQuantity first, DecimalQuantity second, boolean equalBeforeRounding) {
      if (this.fImpl == null) {
         this.fImpl = new NumberRangeFormatterImpl(this.resolve());
      }

      return this.fImpl.format(first, second, equalBeforeRounding);
   }

   LocalizedNumberRangeFormatter create(int key, Object value) {
      return new LocalizedNumberRangeFormatter(this, key, value);
   }
}
