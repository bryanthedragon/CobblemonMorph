package com.cobblemon.mod.relocations.ibm.icu.impl.number;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.number.NumberFormatter;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;

public class Grouper {
   private static final Grouper GROUPER_NEVER = new Grouper((short)-1, (short)-1, (short)-2);
   private static final Grouper GROUPER_MIN2 = new Grouper((short)-2, (short)-2, (short)-3);
   private static final Grouper GROUPER_AUTO = new Grouper((short)-2, (short)-2, (short)-2);
   private static final Grouper GROUPER_ON_ALIGNED = new Grouper((short)-4, (short)-4, (short)1);
   private static final Grouper GROUPER_WESTERN = new Grouper((short)3, (short)3, (short)1);
   private static final Grouper GROUPER_INDIC = new Grouper((short)3, (short)2, (short)1);
   private static final Grouper GROUPER_WESTERN_MIN2 = new Grouper((short)3, (short)3, (short)2);
   private static final Grouper GROUPER_INDIC_MIN2 = new Grouper((short)3, (short)2, (short)2);
   private final short grouping1;
   private final short grouping2;
   private final short minGrouping;

   public static Grouper forStrategy(NumberFormatter.GroupingStrategy grouping) {
      switch (grouping) {
         case OFF:
            return GROUPER_NEVER;
         case MIN2:
            return GROUPER_MIN2;
         case AUTO:
            return GROUPER_AUTO;
         case ON_ALIGNED:
            return GROUPER_ON_ALIGNED;
         case THOUSANDS:
            return GROUPER_WESTERN;
         default:
            throw new AssertionError();
      }
   }

   public static Grouper forProperties(DecimalFormatProperties properties) {
      if (!properties.getGroupingUsed()) {
         return GROUPER_NEVER;
      } else {
         short grouping1 = (short)properties.getGroupingSize();
         short grouping2 = (short)properties.getSecondaryGroupingSize();
         short minGrouping = (short)properties.getMinimumGroupingDigits();
         grouping1 = grouping1 > 0 ? grouping1 : (grouping2 > 0 ? grouping2 : grouping1);
         grouping2 = grouping2 > 0 ? grouping2 : grouping1;
         return getInstance(grouping1, grouping2, minGrouping);
      }
   }

   public static Grouper getInstance(short grouping1, short grouping2, short minGrouping) {
      if (grouping1 == -1) {
         return GROUPER_NEVER;
      } else if (grouping1 == 3 && grouping2 == 3 && minGrouping == 1) {
         return GROUPER_WESTERN;
      } else if (grouping1 == 3 && grouping2 == 2 && minGrouping == 1) {
         return GROUPER_INDIC;
      } else if (grouping1 == 3 && grouping2 == 3 && minGrouping == 2) {
         return GROUPER_WESTERN_MIN2;
      } else {
         return grouping1 == 3 && grouping2 == 2 && minGrouping == 2 ? GROUPER_INDIC_MIN2 : new Grouper(grouping1, grouping2, minGrouping);
      }
   }

   private static short getMinGroupingForLocale(ULocale locale) {
      ICUResourceBundle resource = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", locale);
      String result = resource.getStringWithFallback("NumberElements/minimumGroupingDigits");
      return Short.valueOf(result);
   }

   private Grouper(short grouping1, short grouping2, short minGrouping) {
      this.grouping1 = grouping1;
      this.grouping2 = grouping2;
      this.minGrouping = minGrouping;
   }

   public Grouper withLocaleData(ULocale locale, PatternStringParser.ParsedPatternInfo patternInfo) {
      short minGrouping;
      if (this.minGrouping == -2) {
         minGrouping = getMinGroupingForLocale(locale);
      } else if (this.minGrouping == -3) {
         minGrouping = (short)Math.max(2, getMinGroupingForLocale(locale));
      } else {
         minGrouping = this.minGrouping;
      }

      if (this.grouping1 == -2 || this.grouping2 == -4) {
         short grouping1 = (short)(patternInfo.positive.groupingSizes & 65535L);
         short grouping2 = (short)(patternInfo.positive.groupingSizes >>> 16 & 65535L);
         short grouping3 = (short)(patternInfo.positive.groupingSizes >>> 32 & 65535L);
         if (grouping2 == -1) {
            grouping1 = (short)(this.grouping1 == -4 ? 3 : -1);
         }

         if (grouping3 == -1) {
            grouping2 = grouping1;
         }

         return getInstance(grouping1, grouping2, minGrouping);
      } else {
         return minGrouping == this.minGrouping ? this : getInstance(this.grouping1, this.grouping2, minGrouping);
      }
   }

   public boolean groupAtPosition(int position, DecimalQuantity value) {
      assert this.grouping1 != -2 && this.grouping1 != -4;

      if (this.grouping1 != -1 && this.grouping1 != 0) {
         position -= this.grouping1;
         return position >= 0 && position % this.grouping2 == 0 && value.getUpperDisplayMagnitude() - this.grouping1 + 1 >= this.minGrouping;
      } else {
         return false;
      }
   }

   public short getPrimary() {
      return this.grouping1;
   }

   public short getSecondary() {
      return this.grouping2;
   }
}
