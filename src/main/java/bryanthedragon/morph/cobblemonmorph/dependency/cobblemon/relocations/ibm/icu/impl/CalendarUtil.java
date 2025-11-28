package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.TreeMap;

public final class CalendarUtil {
   private static final String CALKEY = "calendar";
   private static final String DEFCAL = "gregorian";

   public static String getCalendarType(ULocale loc) {
      String calType = loc.getKeywordValue("calendar");
      if (calType != null) {
         return calType.toLowerCase(Locale.ROOT);
      } else {
         ULocale canonical = ULocale.createCanonical(loc.toString());
         calType = canonical.getKeywordValue("calendar");
         if (calType != null) {
            return calType;
         } else {
            String region = ULocale.getRegionForSupplementalData(canonical, true);
            return CalendarUtil.CalendarPreferences.INSTANCE.getCalendarTypeForRegion(region);
         }
      }
   }

   private static final class CalendarPreferences extends UResource.Sink {
      private static final CalendarUtil.CalendarPreferences INSTANCE = new CalendarUtil.CalendarPreferences();
      Map<String, String> prefs = new TreeMap<>();

      CalendarPreferences() {
         try {
            ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance(
               "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", "supplementalData"
            );
            rb.getAllItemsWithFallback("calendarPreferenceData", this);
         } catch (MissingResourceException var2) {
         }
      }

      String getCalendarTypeForRegion(String region) {
         String type = this.prefs.get(region);
         return type == null ? "gregorian" : type;
      }

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table calendarPreferenceData = value.getTable();

         for (int i = 0; calendarPreferenceData.getKeyAndValue(i, key, value); i++) {
            UResource.Array types = value.getArray();
            if (types.getValue(0, value)) {
               String type = value.getString();
               if (!type.equals("gregorian")) {
                  this.prefs.put(key.toString(), type);
               }
            }
         }
      }
   }
}
