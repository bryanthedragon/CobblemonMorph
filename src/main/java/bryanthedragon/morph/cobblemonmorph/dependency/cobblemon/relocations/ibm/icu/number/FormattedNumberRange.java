package com.cobblemon.mod.relocations.ibm.icu.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedStringBuilder;
import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedValueStringBuilderImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity;
import com.cobblemon.mod.relocations.ibm.icu.text.ConstrainedFieldPosition;
import com.cobblemon.mod.relocations.ibm.icu.text.FormattedValue;
import com.cobblemon.mod.relocations.ibm.icu.text.PluralRules;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUUncheckedIOException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;

public class FormattedNumberRange implements FormattedValue {
   final FormattedStringBuilder string;
   final DecimalQuantity quantity1;
   final DecimalQuantity quantity2;
   final NumberRangeFormatter.RangeIdentityResult identityResult;

   FormattedNumberRange(
      FormattedStringBuilder string, DecimalQuantity quantity1, DecimalQuantity quantity2, NumberRangeFormatter.RangeIdentityResult identityResult
   ) {
      this.string = string;
      this.quantity1 = quantity1;
      this.quantity2 = quantity2;
      this.identityResult = identityResult;
   }

   @Override
   public String toString() {
      return this.string.toString();
   }

   @Override
   public <A extends Appendable> A appendTo(A appendable) {
      try {
         appendable.append(this.string);
         return appendable;
      } catch (IOException var3) {
         throw new ICUUncheckedIOException(var3);
      }
   }

   @Override
   public char charAt(int index) {
      return this.string.charAt(index);
   }

   @Override
   public int length() {
      return this.string.length();
   }

   @Override
   public CharSequence subSequence(int start, int end) {
      return this.string.subString(start, end);
   }

   @Override
   public boolean nextPosition(ConstrainedFieldPosition cfpos) {
      return FormattedValueStringBuilderImpl.nextPosition(this.string, cfpos, null);
   }

   @Override
   public AttributedCharacterIterator toCharacterIterator() {
      return FormattedValueStringBuilderImpl.toCharacterIterator(this.string, null);
   }

   public BigDecimal getFirstBigDecimal() {
      return this.quantity1.toBigDecimal();
   }

   public BigDecimal getSecondBigDecimal() {
      return this.quantity2.toBigDecimal();
   }

   public NumberRangeFormatter.RangeIdentityResult getIdentityResult() {
      return this.identityResult;
   }

   @Override
   public int hashCode() {
      return Arrays.hashCode(this.string.toCharArray())
         ^ Arrays.hashCode(this.string.toFieldArray())
         ^ this.quantity1.toBigDecimal().hashCode()
         ^ this.quantity2.toBigDecimal().hashCode();
   }

   @Override
   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other == null) {
         return false;
      } else if (!(other instanceof FormattedNumberRange)) {
         return false;
      } else {
         FormattedNumberRange _other = (FormattedNumberRange)other;
         return this.string.contentEquals(_other.string)
            && this.quantity1.toBigDecimal().equals(_other.quantity1.toBigDecimal())
            && this.quantity2.toBigDecimal().equals(_other.quantity2.toBigDecimal());
      }
   }

   @Deprecated
   public PluralRules.IFixedDecimal getFirstFixedDecimal() {
      return this.quantity1;
   }

   @Deprecated
   public PluralRules.IFixedDecimal getSecondFixedDecimal() {
      return this.quantity2;
   }
}
