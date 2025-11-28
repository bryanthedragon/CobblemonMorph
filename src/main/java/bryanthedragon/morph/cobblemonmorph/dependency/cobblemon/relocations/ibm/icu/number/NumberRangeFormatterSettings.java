package com.cobblemon.mod.relocations.ibm.icu.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.number.range.RangeMacroProps;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;

public abstract class NumberRangeFormatterSettings<T extends NumberRangeFormatterSettings<?>> {
   static final int KEY_MACROS = 0;
   static final int KEY_LOCALE = 1;
   static final int KEY_FORMATTER_1 = 2;
   static final int KEY_FORMATTER_2 = 3;
   static final int KEY_SAME_FORMATTERS = 4;
   static final int KEY_COLLAPSE = 5;
   static final int KEY_IDENTITY_FALLBACK = 6;
   static final int KEY_MAX = 7;
   private final NumberRangeFormatterSettings<?> parent;
   private final int key;
   private final Object value;
   private volatile RangeMacroProps resolvedMacros;

   NumberRangeFormatterSettings(NumberRangeFormatterSettings<?> parent, int key, Object value) {
      this.parent = parent;
      this.key = key;
      this.value = value;
   }

   public T numberFormatterBoth(UnlocalizedNumberFormatter formatter) {
      return (T)this.create(4, true).create(2, formatter);
   }

   public T numberFormatterFirst(UnlocalizedNumberFormatter formatterFirst) {
      return (T)this.create(4, false).create(2, formatterFirst);
   }

   public T numberFormatterSecond(UnlocalizedNumberFormatter formatterSecond) {
      return (T)this.create(4, false).create(3, formatterSecond);
   }

   public T collapse(NumberRangeFormatter.RangeCollapse collapse) {
      return this.create(5, collapse);
   }

   public T identityFallback(NumberRangeFormatter.RangeIdentityFallback identityFallback) {
      return this.create(6, identityFallback);
   }

   abstract T create(int var1, Object var2);

   RangeMacroProps resolve() {
      if (this.resolvedMacros != null) {
         return this.resolvedMacros;
      } else {
         RangeMacroProps macros = new RangeMacroProps();
         long seen = 0L;
         NumberRangeFormatterSettings<?> current = this;

         while (current != null) {
            long keyBitmask = 1L << current.key;
            if (0L != (seen & keyBitmask)) {
               current = current.parent;
            } else {
               seen |= keyBitmask;
               switch (current.key) {
                  case 0:
                     break;
                  case 1:
                     macros.loc = (ULocale)current.value;
                     break;
                  case 2:
                     macros.formatter1 = (UnlocalizedNumberFormatter)current.value;
                     break;
                  case 3:
                     macros.formatter2 = (UnlocalizedNumberFormatter)current.value;
                     break;
                  case 4:
                     macros.sameFormatters = (Boolean)current.value ? 1 : 0;
                     break;
                  case 5:
                     macros.collapse = (NumberRangeFormatter.RangeCollapse)current.value;
                     break;
                  case 6:
                     macros.identityFallback = (NumberRangeFormatter.RangeIdentityFallback)current.value;
                     break;
                  default:
                     throw new AssertionError("Unknown key: " + current.key);
               }

               current = current.parent;
            }
         }

         if (macros.formatter1 != null) {
            macros.formatter1.resolve().loc = macros.loc;
         }

         if (macros.formatter2 != null) {
            macros.formatter2.resolve().loc = macros.loc;
         }

         this.resolvedMacros = macros;
         return macros;
      }
   }

   @Override
   public int hashCode() {
      return this.resolve().hashCode();
   }

   @Override
   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (other == null) {
         return false;
      } else {
         return !(other instanceof NumberRangeFormatterSettings) ? false : this.resolve().equals(((NumberRangeFormatterSettings)other).resolve());
      }
   }
}
