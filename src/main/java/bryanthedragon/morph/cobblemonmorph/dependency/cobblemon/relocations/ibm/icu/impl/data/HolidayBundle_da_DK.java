package com.cobblemon.mod.relocations.ibm.icu.impl.data;

import com.cobblemon.mod.relocations.ibm.icu.util.EasterHoliday;
import com.cobblemon.mod.relocations.ibm.icu.util.Holiday;
import com.cobblemon.mod.relocations.ibm.icu.util.SimpleHoliday;
import java.util.ListResourceBundle;

public class HolidayBundle_da_DK extends ListResourceBundle {
   private static final Holiday[] fHolidays = new Holiday[]{
      SimpleHoliday.NEW_YEARS_DAY,
      new SimpleHoliday(3, 30, -6, "General Prayer Day"),
      new SimpleHoliday(5, 5, "Constitution Day"),
      SimpleHoliday.CHRISTMAS_EVE,
      SimpleHoliday.CHRISTMAS,
      SimpleHoliday.BOXING_DAY,
      SimpleHoliday.NEW_YEARS_EVE,
      EasterHoliday.MAUNDY_THURSDAY,
      EasterHoliday.GOOD_FRIDAY,
      EasterHoliday.EASTER_SUNDAY,
      EasterHoliday.EASTER_MONDAY,
      EasterHoliday.ASCENSION,
      EasterHoliday.WHIT_MONDAY
   };
   private static final Object[][] fContents = new Object[][]{{"holidays", fHolidays}};

   @Override
   public synchronized Object[][] getContents() {
      return fContents;
   }
}
