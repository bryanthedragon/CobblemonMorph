package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.util.Calendar;
import com.cobblemon.mod.relocations.ibm.icu.util.ChineseCalendar;
import com.cobblemon.mod.relocations.ibm.icu.util.TimeZone;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import java.io.InvalidObjectException;
import java.text.FieldPosition;
import java.util.Locale;

@Deprecated
public class ChineseDateFormat extends SimpleDateFormat {
   static final long serialVersionUID = -4610300753104099899L;

   @Deprecated
   public ChineseDateFormat(String pattern, Locale locale) {
      this(pattern, ULocale.forLocale(locale));
   }

   @Deprecated
   public ChineseDateFormat(String pattern, ULocale locale) {
      this(pattern, null, locale);
   }

   @Deprecated
   public ChineseDateFormat(String pattern, String override, ULocale locale) {
      super(pattern, new ChineseDateFormatSymbols(locale), new ChineseCalendar(TimeZone.getDefault(), locale), locale, true, override);
   }

   @Deprecated
   @Override
   protected void subFormat(
      StringBuffer buf,
      char ch,
      int count,
      int beginOffset,
      int fieldNum,
      DisplayContext capitalizationContext,
      FieldPosition pos,
      char patternCharToOutput,
      Calendar cal
   ) {
      super.subFormat(buf, ch, count, beginOffset, fieldNum, capitalizationContext, pos, patternCharToOutput, cal);
   }

   @Deprecated
   @Override
   protected int subParse(String text, int start, char ch, int count, boolean obeyCount, boolean allowNegative, boolean[] ambiguousYear, Calendar cal) {
      return super.subParse(text, start, ch, count, obeyCount, allowNegative, ambiguousYear, cal);
   }

   @Deprecated
   @Override
   protected DateFormat.Field patternCharToDateFormatField(char ch) {
      return super.patternCharToDateFormatField(ch);
   }

   @Deprecated
   public static class Field extends DateFormat.Field {
      private static final long serialVersionUID = -5102130532751400330L;
      @Deprecated
      public static final ChineseDateFormat.Field IS_LEAP_MONTH = new ChineseDateFormat.Field("is leap month", 22);

      @Deprecated
      protected Field(String name, int calendarField) {
         super(name, calendarField);
      }

      @Deprecated
      public static DateFormat.Field ofCalendarField(int calendarField) {
         return (DateFormat.Field)(calendarField == 22 ? IS_LEAP_MONTH : DateFormat.Field.ofCalendarField(calendarField));
      }

      @Deprecated
      @Override
      protected Object readResolve() throws InvalidObjectException {
         if (this.getClass() != ChineseDateFormat.Field.class) {
            throw new InvalidObjectException("A subclass of ChineseDateFormat.Field must implement readResolve.");
         } else if (this.getName().equals(IS_LEAP_MONTH.getName())) {
            return IS_LEAP_MONTH;
         } else {
            throw new InvalidObjectException("Unknown attribute name.");
         }
      }
   }
}
