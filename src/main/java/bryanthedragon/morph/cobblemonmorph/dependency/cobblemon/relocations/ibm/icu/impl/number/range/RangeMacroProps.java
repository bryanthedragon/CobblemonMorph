package com.cobblemon.mod.relocations.ibm.icu.impl.number.range;

import com.cobblemon.mod.relocations.ibm.icu.number.NumberRangeFormatter;
import com.cobblemon.mod.relocations.ibm.icu.number.UnlocalizedNumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.util.Objects;

public class RangeMacroProps {
   public UnlocalizedNumberFormatter formatter1;
   public UnlocalizedNumberFormatter formatter2;
   public int sameFormatters = -1;
   public NumberRangeFormatter.RangeCollapse collapse;
   public NumberRangeFormatter.RangeIdentityFallback identityFallback;
   public ULocale loc;

   @Override
   public int hashCode() {
      return Objects.hash(this.formatter1, this.formatter2, this.collapse, this.identityFallback, this.loc);
   }

   @Override
   public boolean equals(Object _other) {
      if (_other == null) {
         return false;
      } else if (this == _other) {
         return true;
      } else if (!(_other instanceof RangeMacroProps)) {
         return false;
      } else {
         RangeMacroProps other = (RangeMacroProps)_other;
         return Objects.equals(this.formatter1, other.formatter1)
            && Objects.equals(this.formatter2, other.formatter2)
            && Objects.equals(this.collapse, other.collapse)
            && Objects.equals(this.identityFallback, other.identityFallback)
            && Objects.equals(this.loc, other.loc);
      }
   }
}
