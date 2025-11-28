package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedValueFieldPositionIteratorImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.SimpleCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.SimpleFormatterImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.Utility;
import com.cobblemon.mod.relocations.ibm.icu.util.Calendar;
import com.cobblemon.mod.relocations.ibm.icu.util.DateInterval;
import com.cobblemon.mod.relocations.ibm.icu.util.Output;
import com.cobblemon.mod.relocations.ibm.icu.util.TimeZone;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DateIntervalFormat extends UFormat {
   private static final long serialVersionUID = 1L;
   private static ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>> LOCAL_PATTERN_CACHE = new SimpleCache<>();
   private DateIntervalInfo fInfo;
   private SimpleDateFormat fDateFormat;
   private Calendar fFromCalendar;
   private Calendar fToCalendar;
   private String fSkeleton = null;
   private boolean isDateIntervalInfoDefault;
   private transient Map<String, DateIntervalInfo.PatternInfo> fIntervalPatterns = null;
   private String fDatePattern = null;
   private String fTimePattern = null;
   private String fDateTimeFormat = null;
   private DisplayContext fCapitalizationSetting = DisplayContext.CAPITALIZATION_NONE;

   private DateIntervalFormat() {
   }

   @Deprecated
   public DateIntervalFormat(String skeleton, DateIntervalInfo dtItvInfo, SimpleDateFormat simpleDateFormat) {
      this.fDateFormat = simpleDateFormat;
      dtItvInfo.freeze();
      this.fSkeleton = skeleton;
      this.fInfo = dtItvInfo;
      this.isDateIntervalInfoDefault = false;
      this.fFromCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
      this.fToCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
      this.initializePattern(null);
   }

   private DateIntervalFormat(String skeleton, ULocale locale, SimpleDateFormat simpleDateFormat) {
      this.fDateFormat = simpleDateFormat;
      this.fSkeleton = skeleton;
      this.fInfo = new DateIntervalInfo(locale).freeze();
      this.isDateIntervalInfoDefault = true;
      this.fFromCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
      this.fToCalendar = (Calendar)this.fDateFormat.getCalendar().clone();
      this.initializePattern(LOCAL_PATTERN_CACHE);
   }

   public static final DateIntervalFormat getInstance(String skeleton) {
      return getInstance(skeleton, ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public static final DateIntervalFormat getInstance(String skeleton, Locale locale) {
      return getInstance(skeleton, ULocale.forLocale(locale));
   }

   public static final DateIntervalFormat getInstance(String skeleton, ULocale locale) {
      DateTimePatternGenerator generator = DateTimePatternGenerator.getInstance(locale);
      return new DateIntervalFormat(skeleton, locale, new SimpleDateFormat(generator.getBestPattern(skeleton), locale));
   }

   public static final DateIntervalFormat getInstance(String skeleton, DateIntervalInfo dtitvinf) {
      return getInstance(skeleton, ULocale.getDefault(ULocale.Category.FORMAT), dtitvinf);
   }

   public static final DateIntervalFormat getInstance(String skeleton, Locale locale, DateIntervalInfo dtitvinf) {
      return getInstance(skeleton, ULocale.forLocale(locale), dtitvinf);
   }

   public static final DateIntervalFormat getInstance(String skeleton, ULocale locale, DateIntervalInfo dtitvinf) {
      dtitvinf = (DateIntervalInfo)dtitvinf.clone();
      DateTimePatternGenerator generator = DateTimePatternGenerator.getInstance(locale);
      return new DateIntervalFormat(skeleton, dtitvinf, new SimpleDateFormat(generator.getBestPattern(skeleton), locale));
   }

   @Override
   public synchronized Object clone() {
      DateIntervalFormat other = (DateIntervalFormat)super.clone();
      other.fDateFormat = (SimpleDateFormat)this.fDateFormat.clone();
      other.fInfo = (DateIntervalInfo)this.fInfo.clone();
      other.fFromCalendar = (Calendar)this.fFromCalendar.clone();
      other.fToCalendar = (Calendar)this.fToCalendar.clone();
      other.fDatePattern = this.fDatePattern;
      other.fTimePattern = this.fTimePattern;
      other.fDateTimeFormat = this.fDateTimeFormat;
      other.fCapitalizationSetting = this.fCapitalizationSetting;
      return other;
   }

   @Override
   public final StringBuffer format(Object obj, StringBuffer appendTo, FieldPosition fieldPosition) {
      if (obj instanceof DateInterval) {
         return this.format((DateInterval)obj, appendTo, fieldPosition);
      } else {
         throw new IllegalArgumentException("Cannot format given Object (" + obj.getClass().getName() + ") as a DateInterval");
      }
   }

   public final StringBuffer format(DateInterval dtInterval, StringBuffer appendTo, FieldPosition fieldPosition) {
      return this.formatIntervalImpl(dtInterval, appendTo, fieldPosition, null, null);
   }

   public DateIntervalFormat.FormattedDateInterval formatToValue(DateInterval dtInterval) {
      StringBuffer sb = new StringBuffer();
      FieldPosition ignore = new FieldPosition(0);
      DateIntervalFormat.FormatOutput output = new DateIntervalFormat.FormatOutput();
      List<FieldPosition> attributes = new ArrayList<>();
      this.formatIntervalImpl(dtInterval, sb, ignore, output, attributes);
      if (output.firstIndex != -1) {
         FormattedValueFieldPositionIteratorImpl.addOverlapSpans(attributes, DateIntervalFormat.SpanField.DATE_INTERVAL_SPAN, output.firstIndex);
         FormattedValueFieldPositionIteratorImpl.sort(attributes);
      }

      return new DateIntervalFormat.FormattedDateInterval(sb, attributes);
   }

   private synchronized StringBuffer formatIntervalImpl(
      DateInterval dtInterval, StringBuffer appendTo, FieldPosition pos, DateIntervalFormat.FormatOutput output, List<FieldPosition> attributes
   ) {
      this.fFromCalendar.setTimeInMillis(dtInterval.getFromDate());
      this.fToCalendar.setTimeInMillis(dtInterval.getToDate());
      return this.formatImpl(this.fFromCalendar, this.fToCalendar, appendTo, pos, output, attributes);
   }

   @Deprecated
   public String getPatterns(Calendar fromCalendar, Calendar toCalendar, Output<String> part2) {
      int field;
      if (fromCalendar.get(0) != toCalendar.get(0)) {
         field = 0;
      } else if (fromCalendar.get(1) != toCalendar.get(1)) {
         field = 1;
      } else if (fromCalendar.get(2) != toCalendar.get(2)) {
         field = 2;
      } else if (fromCalendar.get(5) != toCalendar.get(5)) {
         field = 5;
      } else if (fromCalendar.get(9) != toCalendar.get(9)) {
         field = 9;
      } else if (fromCalendar.get(10) != toCalendar.get(10)) {
         field = 10;
      } else if (fromCalendar.get(12) != toCalendar.get(12)) {
         field = 12;
      } else if (fromCalendar.get(13) != toCalendar.get(13)) {
         field = 13;
      } else {
         if (fromCalendar.get(14) == toCalendar.get(14)) {
            return null;
         }

         field = 14;
      }

      DateIntervalInfo.PatternInfo intervalPattern = this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field]);
      part2.value = intervalPattern.getSecondPart();
      return intervalPattern.getFirstPart();
   }

   public final StringBuffer format(Calendar fromCalendar, Calendar toCalendar, StringBuffer appendTo, FieldPosition pos) {
      return this.formatImpl(fromCalendar, toCalendar, appendTo, pos, null, null);
   }

   public DateIntervalFormat.FormattedDateInterval formatToValue(Calendar fromCalendar, Calendar toCalendar) {
      StringBuffer sb = new StringBuffer();
      FieldPosition ignore = new FieldPosition(0);
      DateIntervalFormat.FormatOutput output = new DateIntervalFormat.FormatOutput();
      List<FieldPosition> attributes = new ArrayList<>();
      this.formatImpl(fromCalendar, toCalendar, sb, ignore, output, attributes);
      if (output.firstIndex != -1) {
         FormattedValueFieldPositionIteratorImpl.addOverlapSpans(attributes, DateIntervalFormat.SpanField.DATE_INTERVAL_SPAN, output.firstIndex);
         FormattedValueFieldPositionIteratorImpl.sort(attributes);
      }

      return new DateIntervalFormat.FormattedDateInterval(sb, attributes);
   }

   private synchronized StringBuffer formatImpl(
      Calendar fromCalendar,
      Calendar toCalendar,
      StringBuffer appendTo,
      FieldPosition pos,
      DateIntervalFormat.FormatOutput output,
      List<FieldPosition> attributes
   ) {
      if (!fromCalendar.isEquivalentTo(toCalendar)) {
         throw new IllegalArgumentException("can not format on two different calendars");
      } else {
         this.fDateFormat.setContext(this.fCapitalizationSetting);
         int field = -1;
         byte var13;
         if (fromCalendar.get(0) != toCalendar.get(0)) {
            var13 = 0;
         } else if (fromCalendar.get(1) != toCalendar.get(1)) {
            var13 = 1;
         } else if (fromCalendar.get(2) != toCalendar.get(2)) {
            var13 = 2;
         } else if (fromCalendar.get(5) != toCalendar.get(5)) {
            var13 = 5;
         } else if (fromCalendar.get(9) != toCalendar.get(9)) {
            var13 = 9;
         } else if (fromCalendar.get(10) != toCalendar.get(10)) {
            var13 = 10;
         } else if (fromCalendar.get(12) != toCalendar.get(12)) {
            var13 = 12;
         } else if (fromCalendar.get(13) != toCalendar.get(13)) {
            var13 = 13;
         } else {
            if (fromCalendar.get(14) == toCalendar.get(14)) {
               return this.fDateFormat.format(fromCalendar, appendTo, pos, attributes);
            }

            var13 = 14;
         }

         boolean fromToOnSameDay = var13 == 9 || var13 == 10 || var13 == 12 || var13 == 13 || var13 == 14;
         DateIntervalInfo.PatternInfo intervalPattern = this.fIntervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[var13]);
         if (intervalPattern == null) {
            return this.fDateFormat.isFieldUnitIgnored(var13)
               ? this.fDateFormat.format(fromCalendar, appendTo, pos, attributes)
               : this.fallbackFormat(fromCalendar, toCalendar, fromToOnSameDay, appendTo, pos, output, attributes);
         } else if (intervalPattern.getFirstPart() == null) {
            return this.fallbackFormat(fromCalendar, toCalendar, fromToOnSameDay, appendTo, pos, output, attributes, intervalPattern.getSecondPart());
         } else {
            Calendar firstCal;
            Calendar secondCal;
            if (intervalPattern.firstDateInPtnIsLaterDate()) {
               if (output != null) {
                  output.register(1);
               }

               firstCal = toCalendar;
               secondCal = fromCalendar;
            } else {
               if (output != null) {
                  output.register(0);
               }

               firstCal = fromCalendar;
               secondCal = toCalendar;
            }

            String originalPattern = this.fDateFormat.toPattern();
            this.fDateFormat.applyPattern(intervalPattern.getFirstPart());
            this.fDateFormat.format(firstCal, appendTo, pos, attributes);
            if (pos.getEndIndex() > 0) {
               pos = new FieldPosition(0);
            }

            if (intervalPattern.getSecondPart() != null) {
               this.fDateFormat.applyPattern(intervalPattern.getSecondPart());
               this.fDateFormat.setContext(DisplayContext.CAPITALIZATION_NONE);
               this.fDateFormat.format(secondCal, appendTo, pos, attributes);
            }

            this.fDateFormat.applyPattern(originalPattern);
            return appendTo;
         }
      }
   }

   private final void fallbackFormatRange(
      Calendar fromCalendar,
      Calendar toCalendar,
      StringBuffer appendTo,
      StringBuilder patternSB,
      FieldPosition pos,
      DateIntervalFormat.FormatOutput output,
      List<FieldPosition> attributes
   ) {
      String compiledPattern = SimpleFormatterImpl.compileToStringMinMaxArguments(this.fInfo.getFallbackIntervalPattern(), patternSB, 2, 2);
      long state = 0L;

      while (true) {
         state = SimpleFormatterImpl.IterInternal.step(state, compiledPattern, appendTo);
         if (state == -1L) {
            return;
         }

         if (SimpleFormatterImpl.IterInternal.getArgIndex(state) == 0) {
            if (output != null) {
               output.register(0);
            }

            this.fDateFormat.format(fromCalendar, appendTo, pos, attributes);
         } else {
            if (output != null) {
               output.register(1);
            }

            this.fDateFormat.format(toCalendar, appendTo, pos, attributes);
         }

         if (pos.getEndIndex() > 0) {
            pos = new FieldPosition(0);
         }

         this.fDateFormat.setContext(DisplayContext.CAPITALIZATION_NONE);
      }
   }

   private final StringBuffer fallbackFormat(
      Calendar fromCalendar,
      Calendar toCalendar,
      boolean fromToOnSameDay,
      StringBuffer appendTo,
      FieldPosition pos,
      DateIntervalFormat.FormatOutput output,
      List<FieldPosition> attributes
   ) {
      StringBuilder patternSB = new StringBuilder();
      boolean formatDatePlusTimeRange = fromToOnSameDay && this.fDatePattern != null && this.fTimePattern != null;
      if (formatDatePlusTimeRange) {
         String compiledPattern = SimpleFormatterImpl.compileToStringMinMaxArguments(this.fDateTimeFormat, patternSB, 2, 2);
         String fullPattern = this.fDateFormat.toPattern();
         long state = 0L;

         while (true) {
            state = SimpleFormatterImpl.IterInternal.step(state, compiledPattern, appendTo);
            if (state == -1L) {
               this.fDateFormat.applyPattern(fullPattern);
               break;
            }

            if (SimpleFormatterImpl.IterInternal.getArgIndex(state) == 0) {
               this.fDateFormat.applyPattern(this.fTimePattern);
               this.fallbackFormatRange(fromCalendar, toCalendar, appendTo, patternSB, pos, output, attributes);
            } else {
               this.fDateFormat.applyPattern(this.fDatePattern);
               this.fDateFormat.format(fromCalendar, appendTo, pos, attributes);
            }

            if (pos.getEndIndex() > 0) {
               pos = new FieldPosition(0);
            }

            this.fDateFormat.setContext(DisplayContext.CAPITALIZATION_NONE);
         }
      } else {
         this.fallbackFormatRange(fromCalendar, toCalendar, appendTo, patternSB, pos, output, attributes);
      }

      return appendTo;
   }

   private final StringBuffer fallbackFormat(
      Calendar fromCalendar,
      Calendar toCalendar,
      boolean fromToOnSameDay,
      StringBuffer appendTo,
      FieldPosition pos,
      DateIntervalFormat.FormatOutput output,
      List<FieldPosition> attributes,
      String fullPattern
   ) {
      String originalPattern = this.fDateFormat.toPattern();
      this.fDateFormat.applyPattern(fullPattern);
      this.fallbackFormat(fromCalendar, toCalendar, fromToOnSameDay, appendTo, pos, output, attributes);
      this.fDateFormat.applyPattern(originalPattern);
      return appendTo;
   }

   @Deprecated
   @Override
   public Object parseObject(String source, ParsePosition parse_pos) {
      throw new UnsupportedOperationException("parsing is not supported");
   }

   public DateIntervalInfo getDateIntervalInfo() {
      return (DateIntervalInfo)this.fInfo.clone();
   }

   public void setDateIntervalInfo(DateIntervalInfo newItvPattern) {
      this.fInfo = (DateIntervalInfo)newItvPattern.clone();
      this.isDateIntervalInfoDefault = false;
      this.fInfo.freeze();
      if (this.fDateFormat != null) {
         this.initializePattern(null);
      }
   }

   public TimeZone getTimeZone() {
      return this.fDateFormat != null ? (TimeZone)this.fDateFormat.getTimeZone().clone() : TimeZone.getDefault();
   }

   public void setTimeZone(TimeZone zone) {
      TimeZone zoneToSet = (TimeZone)zone.clone();
      if (this.fDateFormat != null) {
         this.fDateFormat.setTimeZone(zoneToSet);
      }

      if (this.fFromCalendar != null) {
         this.fFromCalendar.setTimeZone(zoneToSet);
      }

      if (this.fToCalendar != null) {
         this.fToCalendar.setTimeZone(zoneToSet);
      }
   }

   public void setContext(DisplayContext context) {
      if (context.type() == DisplayContext.Type.CAPITALIZATION) {
         this.fCapitalizationSetting = context;
      }
   }

   public DisplayContext getContext(DisplayContext.Type type) {
      return type == DisplayContext.Type.CAPITALIZATION && this.fCapitalizationSetting != null
         ? this.fCapitalizationSetting
         : DisplayContext.CAPITALIZATION_NONE;
   }

   public synchronized DateFormat getDateFormat() {
      return (DateFormat)this.fDateFormat.clone();
   }

   private void initializePattern(ICUCache<String, Map<String, DateIntervalInfo.PatternInfo>> cache) {
      String fullPattern = this.fDateFormat.toPattern();
      ULocale locale = this.fDateFormat.getLocale();
      String key = null;
      Map<String, DateIntervalInfo.PatternInfo> patterns = null;
      if (cache != null) {
         if (this.fSkeleton != null) {
            key = locale.toString() + "+" + fullPattern + "+" + this.fSkeleton;
         } else {
            key = locale.toString() + "+" + fullPattern;
         }

         patterns = cache.get(key);
      }

      if (patterns == null) {
         Map<String, DateIntervalInfo.PatternInfo> intervalPatterns = this.initializeIntervalPattern(fullPattern, locale);
         patterns = Collections.unmodifiableMap(intervalPatterns);
         if (cache != null) {
            cache.put(key, patterns);
         }
      }

      this.fIntervalPatterns = patterns;
   }

   private Map<String, DateIntervalInfo.PatternInfo> initializeIntervalPattern(String fullPattern, ULocale locale) {
      DateTimePatternGenerator dtpng = DateTimePatternGenerator.getInstance(locale);
      if (this.fSkeleton == null) {
         this.fSkeleton = dtpng.getSkeleton(fullPattern);
      }

      String skeleton = this.normalizeHourMetacharacters(this.fSkeleton, locale);
      HashMap<String, DateIntervalInfo.PatternInfo> intervalPatterns = new HashMap<>();
      StringBuilder date = new StringBuilder(skeleton.length());
      StringBuilder normalizedDate = new StringBuilder(skeleton.length());
      StringBuilder time = new StringBuilder(skeleton.length());
      StringBuilder normalizedTime = new StringBuilder(skeleton.length());
      getDateTimeSkeleton(skeleton, date, normalizedDate, time, normalizedTime);
      String dateSkeleton = date.toString();
      String timeSkeleton = time.toString();
      String normalizedDateSkeleton = normalizedDate.toString();
      String normalizedTimeSkeleton = normalizedTime.toString();
      if (time.length() != 0 && date.length() != 0) {
         this.fDateTimeFormat = this.getConcatenationPattern(locale);
      }

      boolean found = this.genSeparateDateTimePtn(normalizedDateSkeleton, normalizedTimeSkeleton, intervalPatterns, dtpng);
      if (!found) {
         if (time.length() != 0 && date.length() == 0) {
            timeSkeleton = "yMd" + timeSkeleton;
            String pattern = dtpng.getBestPattern(timeSkeleton);
            DateIntervalInfo.PatternInfo ptn = new DateIntervalInfo.PatternInfo(null, pattern, this.fInfo.getDefaultOrder());
            intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], ptn);
            intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], ptn);
            intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], ptn);
            pattern = dtpng.getBestPattern(timeSkeleton + "G");
            ptn = new DateIntervalInfo.PatternInfo(null, pattern, this.fInfo.getDefaultOrder());
            intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[0], ptn);
         }

         return intervalPatterns;
      } else {
         if (time.length() != 0) {
            if (date.length() == 0) {
               timeSkeleton = "yMd" + timeSkeleton;
               String pattern = dtpng.getBestPattern(timeSkeleton);
               DateIntervalInfo.PatternInfo ptn = new DateIntervalInfo.PatternInfo(null, pattern, this.fInfo.getDefaultOrder());
               intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5], ptn);
               intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2], ptn);
               intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1], ptn);
               pattern = dtpng.getBestPattern(timeSkeleton + "G");
               ptn = new DateIntervalInfo.PatternInfo(null, pattern, this.fInfo.getDefaultOrder());
               intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[0], ptn);
            } else {
               if (!fieldExistsInSkeleton(5, dateSkeleton)) {
                  skeleton = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[5] + skeleton;
                  this.genFallbackPattern(5, skeleton, intervalPatterns, dtpng);
               }

               if (!fieldExistsInSkeleton(2, dateSkeleton)) {
                  skeleton = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[2] + skeleton;
                  this.genFallbackPattern(2, skeleton, intervalPatterns, dtpng);
               }

               if (!fieldExistsInSkeleton(1, dateSkeleton)) {
                  skeleton = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[1] + skeleton;
                  this.genFallbackPattern(1, skeleton, intervalPatterns, dtpng);
               }

               if (!fieldExistsInSkeleton(0, dateSkeleton)) {
                  skeleton = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[0] + skeleton;
                  this.genFallbackPattern(0, skeleton, intervalPatterns, dtpng);
               }

               if (this.fDateTimeFormat == null) {
                  this.fDateTimeFormat = "{1} {0}";
               }

               String datePattern = dtpng.getBestPattern(dateSkeleton);
               this.concatSingleDate2TimeInterval(this.fDateTimeFormat, datePattern, 9, intervalPatterns);
               this.concatSingleDate2TimeInterval(this.fDateTimeFormat, datePattern, 10, intervalPatterns);
               this.concatSingleDate2TimeInterval(this.fDateTimeFormat, datePattern, 12, intervalPatterns);
            }
         }

         return intervalPatterns;
      }
   }

   private String getConcatenationPattern(ULocale locale) {
      ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", locale);
      ICUResourceBundle dtPatternsRb = rb.getWithFallback("calendar/gregorian/DateTimePatterns");
      ICUResourceBundle concatenationPatternRb = (ICUResourceBundle)dtPatternsRb.get(8);
      return concatenationPatternRb.getType() == 0 ? concatenationPatternRb.getString() : concatenationPatternRb.getString(0);
   }

   private void genFallbackPattern(int field, String skeleton, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns, DateTimePatternGenerator dtpng) {
      String pattern = dtpng.getBestPattern(skeleton);
      DateIntervalInfo.PatternInfo ptn = new DateIntervalInfo.PatternInfo(null, pattern, this.fInfo.getDefaultOrder());
      intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], ptn);
   }

   private String normalizeHourMetacharacters(String skeleton, ULocale locale) {
      StringBuilder result = new StringBuilder(skeleton);
      char hourMetachar = 0;
      char dayPeriodChar = 0;
      int metacharStart = 0;
      int metacharCount = 0;

      for (int i = 0; i < result.length(); i++) {
         char c = result.charAt(i);
         if (c != 'j' && c != 'J' && c != 'C' && c != 'h' && c != 'H' && c != 'k' && c != 'K') {
            if (c != 'a' && c != 'b' && c != 'B') {
               if (hourMetachar != 0) {
                  break;
               }
            } else {
               if (dayPeriodChar == 0) {
                  dayPeriodChar = c;
               }

               metacharCount++;
            }
         } else {
            if (hourMetachar == 0) {
               hourMetachar = c;
               metacharStart = i;
            }

            metacharCount++;
         }
      }

      if (hourMetachar != 0) {
         char hourChar = 'H';
         DateTimePatternGenerator dtptng = DateTimePatternGenerator.getInstance(locale);
         String convertedPattern = dtptng.getBestPattern(String.valueOf(hourMetachar));

         int firstQuotePos;
         while ((firstQuotePos = convertedPattern.indexOf(39)) != -1) {
            int secondQuotePos = convertedPattern.indexOf(39, firstQuotePos + 1);
            if (secondQuotePos == -1) {
               secondQuotePos = firstQuotePos;
            }

            convertedPattern = convertedPattern.substring(0, firstQuotePos) + convertedPattern.substring(secondQuotePos + 1);
         }

         if (convertedPattern.indexOf(104) != -1) {
            hourChar = 'h';
         } else if (convertedPattern.indexOf(75) != -1) {
            hourChar = 'K';
         } else if (convertedPattern.indexOf(107) != -1) {
            hourChar = 'k';
         }

         if (convertedPattern.indexOf(98) != -1) {
            dayPeriodChar = 'b';
         } else if (convertedPattern.indexOf(66) != -1) {
            dayPeriodChar = 'B';
         } else if (dayPeriodChar == 0) {
            dayPeriodChar = 'a';
         }

         if (hourChar != 'H' && hourChar != 'k') {
            StringBuilder hourAndDayPeriod = new StringBuilder();
            hourAndDayPeriod.append(hourChar);
            switch (metacharCount) {
               case 1:
               case 2:
               default:
                  hourAndDayPeriod.append(dayPeriodChar);
                  break;
               case 3:
               case 4:
                  for (int ix = 0; ix < 4; ix++) {
                     hourAndDayPeriod.append(dayPeriodChar);
                  }
                  break;
               case 5:
               case 6:
                  for (int ix = 0; ix < 5; ix++) {
                     hourAndDayPeriod.append(dayPeriodChar);
                  }
            }

            result.replace(metacharStart, metacharStart + metacharCount, hourAndDayPeriod.toString());
         } else {
            result.replace(metacharStart, metacharStart + metacharCount, String.valueOf(hourChar));
         }
      }

      return result.toString();
   }

   private static void getDateTimeSkeleton(
      String skeleton, StringBuilder dateSkeleton, StringBuilder normalizedDateSkeleton, StringBuilder timeSkeleton, StringBuilder normalizedTimeSkeleton
   ) {
      int ECount = 0;
      int dCount = 0;
      int MCount = 0;
      int yCount = 0;
      int mCount = 0;
      int vCount = 0;
      int zCount = 0;
      char hourChar = 0;

      for (int i = 0; i < skeleton.length(); i++) {
         char ch = skeleton.charAt(i);
         switch (ch) {
            case 'A':
            case 'B':
            case 'S':
            case 'V':
            case 'Z':
            case 'a':
            case 'b':
            case 'j':
            case 's':
               timeSkeleton.append(ch);
               normalizedTimeSkeleton.append(ch);
            case 'C':
            case 'I':
            case 'J':
            case 'N':
            case 'O':
            case 'P':
            case 'R':
            case 'T':
            case 'X':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'f':
            case 'i':
            case 'n':
            case 'o':
            case 'p':
            case 't':
            case 'x':
            default:
               break;
            case 'D':
            case 'F':
            case 'G':
            case 'L':
            case 'Q':
            case 'U':
            case 'W':
            case 'Y':
            case 'c':
            case 'e':
            case 'g':
            case 'l':
            case 'q':
            case 'r':
            case 'u':
            case 'w':
               normalizedDateSkeleton.append(ch);
               dateSkeleton.append(ch);
               break;
            case 'E':
               dateSkeleton.append(ch);
               ECount++;
               break;
            case 'H':
            case 'K':
            case 'h':
            case 'k':
               timeSkeleton.append(ch);
               if (hourChar == 0) {
                  hourChar = ch;
               }
               break;
            case 'M':
               dateSkeleton.append(ch);
               MCount++;
               break;
            case 'd':
               dateSkeleton.append(ch);
               dCount++;
               break;
            case 'm':
               timeSkeleton.append(ch);
               mCount++;
               break;
            case 'v':
               vCount++;
               timeSkeleton.append(ch);
               break;
            case 'y':
               dateSkeleton.append(ch);
               yCount++;
               break;
            case 'z':
               zCount++;
               timeSkeleton.append(ch);
         }
      }

      if (yCount != 0) {
         for (int var15 = 0; var15 < yCount; var15++) {
            normalizedDateSkeleton.append('y');
         }
      }

      if (MCount != 0) {
         if (MCount < 3) {
            normalizedDateSkeleton.append('M');
         } else {
            for (int var16 = 0; var16 < MCount && var16 < 5; var16++) {
               normalizedDateSkeleton.append('M');
            }
         }
      }

      if (ECount != 0) {
         if (ECount <= 3) {
            normalizedDateSkeleton.append('E');
         } else {
            for (int var17 = 0; var17 < ECount && var17 < 5; var17++) {
               normalizedDateSkeleton.append('E');
            }
         }
      }

      if (dCount != 0) {
         normalizedDateSkeleton.append('d');
      }

      if (hourChar != 0) {
         normalizedTimeSkeleton.append(hourChar);
      }

      if (mCount != 0) {
         normalizedTimeSkeleton.append('m');
      }

      if (zCount != 0) {
         normalizedTimeSkeleton.append('z');
      }

      if (vCount != 0) {
         normalizedTimeSkeleton.append('v');
      }
   }

   private boolean genSeparateDateTimePtn(
      String dateSkeleton, String timeSkeleton, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns, DateTimePatternGenerator dtpng
   ) {
      String skeleton;
      if (timeSkeleton.length() != 0) {
         skeleton = timeSkeleton;
      } else {
         skeleton = dateSkeleton;
      }

      DateIntervalFormat.BestMatchInfo retValue = this.fInfo.getBestSkeleton(skeleton);
      String bestSkeleton = retValue.bestMatchSkeleton;
      int differenceInfo = retValue.bestMatchDistanceInfo;
      if (dateSkeleton.length() != 0) {
         this.fDatePattern = dtpng.getBestPattern(dateSkeleton);
      }

      if (timeSkeleton.length() != 0) {
         this.fTimePattern = dtpng.getBestPattern(timeSkeleton);
      }

      if (differenceInfo == -1) {
         return false;
      } else {
         if (timeSkeleton.length() == 0) {
            this.genIntervalPattern(5, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
            DateIntervalFormat.SkeletonAndItsBestMatch skeletons = this.genIntervalPattern(2, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
            if (skeletons != null) {
               bestSkeleton = skeletons.skeleton;
               skeleton = skeletons.bestMatchSkeleton;
            }

            this.genIntervalPattern(1, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
            this.genIntervalPattern(0, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
         } else {
            this.genIntervalPattern(12, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
            this.genIntervalPattern(10, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
            this.genIntervalPattern(9, skeleton, bestSkeleton, differenceInfo, intervalPatterns);
         }

         return true;
      }
   }

   private DateIntervalFormat.SkeletonAndItsBestMatch genIntervalPattern(
      int field, String skeleton, String bestSkeleton, int differenceInfo, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns
   ) {
      DateIntervalFormat.SkeletonAndItsBestMatch retValue = null;
      DateIntervalInfo.PatternInfo pattern = this.fInfo.getIntervalPattern(bestSkeleton, field);
      if (pattern == null) {
         if (SimpleDateFormat.isFieldUnitIgnored(bestSkeleton, field)) {
            DateIntervalInfo.PatternInfo ptnInfo = new DateIntervalInfo.PatternInfo(this.fDateFormat.toPattern(), null, this.fInfo.getDefaultOrder());
            intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], ptnInfo);
            return null;
         }

         if (field == 9) {
            pattern = this.fInfo.getIntervalPattern(bestSkeleton, 10);
            if (pattern != null) {
               boolean suppressDayPeriodField = this.fSkeleton.indexOf(74) != -1;
               String part1 = adjustFieldWidth(skeleton, bestSkeleton, pattern.getFirstPart(), differenceInfo, suppressDayPeriodField);
               String part2 = adjustFieldWidth(skeleton, bestSkeleton, pattern.getSecondPart(), differenceInfo, suppressDayPeriodField);
               pattern = new DateIntervalInfo.PatternInfo(part1, part2, pattern.firstDateInPtnIsLaterDate());
               intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], pattern);
            }

            return null;
         }

         String fieldLetter = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field];
         bestSkeleton = fieldLetter + bestSkeleton;
         skeleton = fieldLetter + skeleton;
         pattern = this.fInfo.getIntervalPattern(bestSkeleton, field);
         if (pattern == null && differenceInfo == 0) {
            DateIntervalFormat.BestMatchInfo tmpRetValue = this.fInfo.getBestSkeleton(skeleton);
            String tmpBestSkeleton = tmpRetValue.bestMatchSkeleton;
            differenceInfo = tmpRetValue.bestMatchDistanceInfo;
            if (tmpBestSkeleton.length() != 0 && differenceInfo != -1) {
               pattern = this.fInfo.getIntervalPattern(tmpBestSkeleton, field);
               bestSkeleton = tmpBestSkeleton;
            }
         }

         if (pattern != null) {
            retValue = new DateIntervalFormat.SkeletonAndItsBestMatch(skeleton, bestSkeleton);
         }
      }

      if (pattern != null) {
         if (differenceInfo != 0) {
            boolean suppressDayPeriodField = this.fSkeleton.indexOf(74) != -1;
            String part1 = adjustFieldWidth(skeleton, bestSkeleton, pattern.getFirstPart(), differenceInfo, suppressDayPeriodField);
            String part2 = adjustFieldWidth(skeleton, bestSkeleton, pattern.getSecondPart(), differenceInfo, suppressDayPeriodField);
            pattern = new DateIntervalInfo.PatternInfo(part1, part2, pattern.firstDateInPtnIsLaterDate());
         }

         intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], pattern);
      }

      return retValue;
   }

   private static String adjustFieldWidth(
      String inputSkeleton, String bestMatchSkeleton, String bestMatchIntervalPattern, int differenceInfo, boolean suppressDayPeriodField
   ) {
      if (bestMatchIntervalPattern == null) {
         return null;
      } else {
         int[] inputSkeletonFieldWidth = new int[58];
         int[] bestMatchSkeletonFieldWidth = new int[58];
         int PATTERN_CHAR_BASE = 65;
         DateIntervalInfo.parseSkeleton(inputSkeleton, inputSkeletonFieldWidth);
         DateIntervalInfo.parseSkeleton(bestMatchSkeleton, bestMatchSkeletonFieldWidth);
         if (suppressDayPeriodField) {
            if (bestMatchIntervalPattern.indexOf(" a") != -1) {
               bestMatchIntervalPattern = findReplaceInPattern(bestMatchIntervalPattern, " a", "");
            } else if (bestMatchIntervalPattern.indexOf("a ") != -1) {
               bestMatchIntervalPattern = findReplaceInPattern(bestMatchIntervalPattern, "a ", "");
            }

            bestMatchIntervalPattern = findReplaceInPattern(bestMatchIntervalPattern, "a", "");
         }

         if (differenceInfo == 2) {
            if (inputSkeleton.indexOf(122) != -1) {
               bestMatchIntervalPattern = findReplaceInPattern(bestMatchIntervalPattern, "v", "z");
            }

            if (inputSkeleton.indexOf(75) != -1) {
               bestMatchIntervalPattern = findReplaceInPattern(bestMatchIntervalPattern, "h", "K");
            }

            if (inputSkeleton.indexOf(107) != -1) {
               bestMatchIntervalPattern = findReplaceInPattern(bestMatchIntervalPattern, "H", "k");
            }

            if (inputSkeleton.indexOf(98) != -1) {
               bestMatchIntervalPattern = findReplaceInPattern(bestMatchIntervalPattern, "a", "b");
            }
         }

         if (bestMatchIntervalPattern.indexOf(97) != -1 && bestMatchSkeletonFieldWidth[97 - PATTERN_CHAR_BASE] == 0) {
            bestMatchSkeletonFieldWidth[97 - PATTERN_CHAR_BASE] = 1;
         }

         if (bestMatchIntervalPattern.indexOf(98) != -1 && bestMatchSkeletonFieldWidth[98 - PATTERN_CHAR_BASE] == 0) {
            bestMatchSkeletonFieldWidth[98 - PATTERN_CHAR_BASE] = 1;
         }

         StringBuilder adjustedPtn = new StringBuilder(bestMatchIntervalPattern);
         boolean inQuote = false;
         char prevCh = 0;
         int count = 0;
         int adjustedPtnLength = adjustedPtn.length();

         for (int i = 0; i < adjustedPtnLength; i++) {
            char ch = adjustedPtn.charAt(i);
            if (ch != prevCh && count > 0) {
               char skeletonChar = prevCh;
               if (prevCh == 'L') {
                  skeletonChar = 'M';
               }

               int fieldCount = bestMatchSkeletonFieldWidth[skeletonChar - PATTERN_CHAR_BASE];
               int inputFieldCount = inputSkeletonFieldWidth[skeletonChar - PATTERN_CHAR_BASE];
               if (fieldCount == count && inputFieldCount > fieldCount) {
                  count = inputFieldCount - fieldCount;

                  for (int j = 0; j < count; j++) {
                     adjustedPtn.insert(i, prevCh);
                  }

                  i += count;
                  adjustedPtnLength += count;
               }

               count = 0;
            }

            if (ch == '\'') {
               if (i + 1 < adjustedPtn.length() && adjustedPtn.charAt(i + 1) == '\'') {
                  i++;
               } else {
                  inQuote = !inQuote;
               }
            } else if (!inQuote && (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z')) {
               prevCh = ch;
               count++;
            }
         }

         if (count > 0) {
            char skeletonCharx = prevCh;
            if (prevCh == 'L') {
               skeletonCharx = 'M';
            }

            int fieldCount = bestMatchSkeletonFieldWidth[skeletonCharx - PATTERN_CHAR_BASE];
            int inputFieldCount = inputSkeletonFieldWidth[skeletonCharx - PATTERN_CHAR_BASE];
            if (fieldCount == count && inputFieldCount > fieldCount) {
               count = inputFieldCount - fieldCount;

               for (int j = 0; j < count; j++) {
                  adjustedPtn.append(prevCh);
               }
            }
         }

         return adjustedPtn.toString();
      }
   }

   private static String findReplaceInPattern(String targetString, String strToReplace, String strToReplaceWith) {
      int firstQuoteIndex = targetString.indexOf("'");
      if (firstQuoteIndex < 0) {
         return targetString.replace(strToReplace, strToReplaceWith);
      } else {
         StringBuilder result = new StringBuilder();

         String source;
         for (source = targetString; firstQuoteIndex >= 0; firstQuoteIndex = source.indexOf("'")) {
            int secondQuoteIndex = source.indexOf("'", firstQuoteIndex + 1);
            if (secondQuoteIndex < 0) {
               secondQuoteIndex = source.length() - 1;
            }

            String unquotedText = source.substring(0, firstQuoteIndex);
            String quotedText = source.substring(firstQuoteIndex, secondQuoteIndex + 1);
            result.append(unquotedText.replace(strToReplace, strToReplaceWith));
            result.append(quotedText);
            source = source.substring(secondQuoteIndex + 1);
         }

         result.append(source.replace(strToReplace, strToReplaceWith));
         return result.toString();
      }
   }

   private void concatSingleDate2TimeInterval(String dtfmt, String datePattern, int field, Map<String, DateIntervalInfo.PatternInfo> intervalPatterns) {
      DateIntervalInfo.PatternInfo timeItvPtnInfo = intervalPatterns.get(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field]);
      if (timeItvPtnInfo != null) {
         String timeIntervalPattern = timeItvPtnInfo.getFirstPart() + timeItvPtnInfo.getSecondPart();
         String pattern = SimpleFormatterImpl.formatRawPattern(dtfmt, 2, 2, timeIntervalPattern, datePattern);
         timeItvPtnInfo = DateIntervalInfo.genPatternInfo(pattern, timeItvPtnInfo.firstDateInPtnIsLaterDate());
         intervalPatterns.put(DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field], timeItvPtnInfo);
      }
   }

   private static boolean fieldExistsInSkeleton(int field, String skeleton) {
      String fieldChar = DateIntervalInfo.CALENDAR_FIELD_TO_PATTERN_LETTER[field];
      return skeleton.indexOf(fieldChar) != -1;
   }

   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      this.initializePattern(this.isDateIntervalInfoDefault ? LOCAL_PATTERN_CACHE : null);
      if (this.fCapitalizationSetting == null) {
         this.fCapitalizationSetting = DisplayContext.CAPITALIZATION_NONE;
      }
   }

   @Deprecated
   public Map<String, DateIntervalInfo.PatternInfo> getRawPatterns() {
      return this.fIntervalPatterns;
   }

   static final class BestMatchInfo {
      final String bestMatchSkeleton;
      final int bestMatchDistanceInfo;

      BestMatchInfo(String bestSkeleton, int difference) {
         this.bestMatchSkeleton = bestSkeleton;
         this.bestMatchDistanceInfo = difference;
      }
   }

   private static final class FormatOutput {
      int firstIndex = -1;

      private FormatOutput() {
      }

      public void register(int i) {
         if (this.firstIndex == -1) {
            this.firstIndex = i;
         }
      }
   }

   public static final class FormattedDateInterval implements FormattedValue {
      private final String string;
      private final List<FieldPosition> attributes;

      FormattedDateInterval(CharSequence cs, List<FieldPosition> attributes) {
         this.string = cs.toString();
         this.attributes = Collections.unmodifiableList(attributes);
      }

      @Override
      public String toString() {
         return this.string;
      }

      @Override
      public int length() {
         return this.string.length();
      }

      @Override
      public char charAt(int index) {
         return this.string.charAt(index);
      }

      @Override
      public CharSequence subSequence(int start, int end) {
         return this.string.subSequence(start, end);
      }

      @Override
      public <A extends Appendable> A appendTo(A appendable) {
         return Utility.appendTo(this.string, appendable);
      }

      @Override
      public boolean nextPosition(ConstrainedFieldPosition cfpos) {
         return FormattedValueFieldPositionIteratorImpl.nextPosition(this.attributes, cfpos);
      }

      @Override
      public AttributedCharacterIterator toCharacterIterator() {
         return FormattedValueFieldPositionIteratorImpl.toCharacterIterator(this.string, this.attributes);
      }
   }

   private static final class SkeletonAndItsBestMatch {
      final String skeleton;
      final String bestMatchSkeleton;

      SkeletonAndItsBestMatch(String skeleton, String bestMatch) {
         this.skeleton = skeleton;
         this.bestMatchSkeleton = bestMatch;
      }
   }

   public static final class SpanField extends UFormat.SpanField {
      private static final long serialVersionUID = -6330879259553618133L;
      public static final DateIntervalFormat.SpanField DATE_INTERVAL_SPAN = new DateIntervalFormat.SpanField("date-interval-span");

      private SpanField(String name) {
         super(name);
      }

      @Deprecated
      @Override
      protected Object readResolve() throws InvalidObjectException {
         if (this.getName().equals(DATE_INTERVAL_SPAN.getName())) {
            return DATE_INTERVAL_SPAN;
         } else {
            throw new InvalidObjectException("An invalid object.");
         }
      }
   }
}
