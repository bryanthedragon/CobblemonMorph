package com.cobblemon.mod.relocations.ibm.icu.number;

import com.cobblemon.mod.relocations.ibm.icu.text.UFormat;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.io.InvalidObjectException;
import java.util.Locale;

public abstract class NumberRangeFormatter {
   private static final UnlocalizedNumberRangeFormatter BASE = new UnlocalizedNumberRangeFormatter();

   public static UnlocalizedNumberRangeFormatter with() {
      return BASE;
   }

   public static LocalizedNumberRangeFormatter withLocale(Locale locale) {
      return BASE.locale(locale);
   }

   public static LocalizedNumberRangeFormatter withLocale(ULocale locale) {
      return BASE.locale(locale);
   }

   private NumberRangeFormatter() {
   }

   public static enum RangeCollapse {
      AUTO,
      NONE,
      UNIT,
      ALL;
   }

   public static enum RangeIdentityFallback {
      SINGLE_VALUE,
      APPROXIMATELY_OR_SINGLE_VALUE,
      APPROXIMATELY,
      RANGE;
   }

   public static enum RangeIdentityResult {
      EQUAL_BEFORE_ROUNDING,
      EQUAL_AFTER_ROUNDING,
      NOT_EQUAL;
   }

   public static final class SpanField extends UFormat.SpanField {
      private static final long serialVersionUID = 8750397196515368729L;
      public static final NumberRangeFormatter.SpanField NUMBER_RANGE_SPAN = new NumberRangeFormatter.SpanField("number-range-span");

      private SpanField(String name) {
         super(name);
      }

      @Deprecated
      @Override
      protected Object readResolve() throws InvalidObjectException {
         if (this.getName().equals(NUMBER_RANGE_SPAN.getName())) {
            return NUMBER_RANGE_SPAN;
         } else {
            throw new InvalidObjectException("An invalid object.");
         }
      }
   }
}
