package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.DateNumberFormat;
import com.cobblemon.mod.relocations.ibm.icu.impl.DayPeriodRules;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.PatternProps;
import com.cobblemon.mod.relocations.ibm.icu.impl.SimpleCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.SimpleFormatterImpl;
import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import com.cobblemon.mod.relocations.ibm.icu.util.BasicTimeZone;
import com.cobblemon.mod.relocations.ibm.icu.util.Calendar;
import com.cobblemon.mod.relocations.ibm.icu.util.HebrewCalendar;
import com.cobblemon.mod.relocations.ibm.icu.util.Output;
import com.cobblemon.mod.relocations.ibm.icu.util.TimeZone;
import com.cobblemon.mod.relocations.ibm.icu.util.TimeZoneTransition;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.Format.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.UUID;

public class SimpleDateFormat extends DateFormat {
   private static final long serialVersionUID = 4774881970558875024L;
   static final int currentSerialVersion = 2;
   static boolean DelayedHebrewMonthCheck = false;
   private static final int[] CALENDAR_FIELD_TO_LEVEL = new int[]{0, 10, 20, 20, 30, 30, 20, 30, 30, 40, 50, 50, 60, 70, 80, 0, 0, 10, 30, 10, 0, 40, 0, 0};
   private static final int[] PATTERN_CHAR_TO_LEVEL = new int[]{
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      40,
      -1,
      -1,
      20,
      30,
      30,
      0,
      50,
      -1,
      -1,
      50,
      20,
      20,
      -1,
      0,
      -1,
      20,
      -1,
      80,
      -1,
      10,
      0,
      30,
      0,
      10,
      0,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      40,
      -1,
      30,
      30,
      30,
      -1,
      0,
      50,
      -1,
      -1,
      50,
      -1,
      60,
      -1,
      -1,
      -1,
      20,
      10,
      70,
      -1,
      10,
      0,
      20,
      0,
      10,
      0,
      -1,
      -1,
      -1,
      -1,
      -1
   };
   private static final boolean[] PATTERN_CHAR_IS_SYNTAX = new boolean[]{
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      false,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      false,
      false,
      false,
      false,
      false,
      false,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      true,
      false,
      false,
      false,
      false,
      false
   };
   private static final int HEBREW_CAL_CUR_MILLENIUM_START_YEAR = 5000;
   private static final int HEBREW_CAL_CUR_MILLENIUM_END_YEAR = 6000;
   private int serialVersionOnStream = 2;
   private String pattern;
   private String override;
   private HashMap<String, NumberFormat> numberFormatters;
   private HashMap<Character, String> overrideMap;
   private DateFormatSymbols formatData;
   private transient ULocale locale;
   private Date defaultCenturyStart;
   private transient int defaultCenturyStartYear;
   private transient long defaultCenturyBase;
   private static final int millisPerHour = 3600000;
   private static final int ISOSpecialEra = -32000;
   private static final String SUPPRESS_NEGATIVE_PREFIX = "\uab00";
   private transient boolean useFastFormat;
   private volatile TimeZoneFormat tzFormat;
   private transient BreakIterator capitalizationBrkIter = null;
   private transient boolean hasMinute;
   private transient boolean hasSecond;
   private transient boolean hasHanYearChar;
   private static ULocale cachedDefaultLocale = null;
   private static String cachedDefaultPattern = null;
   private static final String FALLBACKPATTERN = "yy/MM/dd HH:mm";
   private static final int[] PATTERN_CHAR_TO_INDEX = new int[]{
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      22,
      36,
      -1,
      10,
      9,
      11,
      0,
      5,
      -1,
      -1,
      16,
      26,
      2,
      -1,
      31,
      -1,
      27,
      -1,
      8,
      -1,
      30,
      29,
      13,
      32,
      18,
      23,
      -1,
      -1,
      -1,
      -1,
      -1,
      -1,
      14,
      35,
      25,
      3,
      19,
      -1,
      21,
      15,
      -1,
      -1,
      4,
      -1,
      6,
      -1,
      -1,
      -1,
      28,
      34,
      7,
      -1,
      20,
      24,
      12,
      33,
      1,
      17,
      -1,
      -1,
      -1,
      -1,
      -1
   };
   private static final int[] PATTERN_INDEX_TO_CALENDAR_FIELD = new int[]{
      0, 1, 2, 5, 11, 11, 12, 13, 14, 7, 6, 8, 3, 4, 9, 10, 10, 15, 17, 18, 19, 20, 21, 15, 15, 18, 2, 2, 2, 15, 1, 15, 15, 15, 19, -1, -2
   };
   private static final int[] PATTERN_INDEX_TO_DATE_FORMAT_FIELD = new int[]{
      0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37
   };
   private static final DateFormat.Field[] PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE = new DateFormat.Field[]{
      DateFormat.Field.ERA,
      DateFormat.Field.YEAR,
      DateFormat.Field.MONTH,
      DateFormat.Field.DAY_OF_MONTH,
      DateFormat.Field.HOUR_OF_DAY1,
      DateFormat.Field.HOUR_OF_DAY0,
      DateFormat.Field.MINUTE,
      DateFormat.Field.SECOND,
      DateFormat.Field.MILLISECOND,
      DateFormat.Field.DAY_OF_WEEK,
      DateFormat.Field.DAY_OF_YEAR,
      DateFormat.Field.DAY_OF_WEEK_IN_MONTH,
      DateFormat.Field.WEEK_OF_YEAR,
      DateFormat.Field.WEEK_OF_MONTH,
      DateFormat.Field.AM_PM,
      DateFormat.Field.HOUR1,
      DateFormat.Field.HOUR0,
      DateFormat.Field.TIME_ZONE,
      DateFormat.Field.YEAR_WOY,
      DateFormat.Field.DOW_LOCAL,
      DateFormat.Field.EXTENDED_YEAR,
      DateFormat.Field.JULIAN_DAY,
      DateFormat.Field.MILLISECONDS_IN_DAY,
      DateFormat.Field.TIME_ZONE,
      DateFormat.Field.TIME_ZONE,
      DateFormat.Field.DAY_OF_WEEK,
      DateFormat.Field.MONTH,
      DateFormat.Field.QUARTER,
      DateFormat.Field.QUARTER,
      DateFormat.Field.TIME_ZONE,
      DateFormat.Field.YEAR,
      DateFormat.Field.TIME_ZONE,
      DateFormat.Field.TIME_ZONE,
      DateFormat.Field.TIME_ZONE,
      DateFormat.Field.RELATED_YEAR,
      DateFormat.Field.AM_PM_MIDNIGHT_NOON,
      DateFormat.Field.FLEXIBLE_DAY_PERIOD,
      DateFormat.Field.TIME_SEPARATOR
   };
   private static ICUCache<String, Object[]> PARSED_PATTERN_CACHE = new SimpleCache<>();
   private transient Object[] patternItems;
   private transient boolean useLocalZeroPaddingNumberFormat;
   private transient char[] decDigits;
   private transient char[] decimalBuf;
   private static final int DECIMAL_BUF_SIZE = 10;
   private static final String NUMERIC_FORMAT_CHARS = "ADdFgHhKkmrSsuWwYy";
   private static final String NUMERIC_FORMAT_CHARS2 = "ceLMQq";
   private static final long MAX_DAYLIGHT_DETECTION_RANGE = 946080000000L;
   static final UnicodeSet DATE_PATTERN_TYPE = new UnicodeSet("[GyYuUQqMLlwWd]").freeze();

   private static int getLevelFromChar(char ch) {
      return ch < PATTERN_CHAR_TO_LEVEL.length ? PATTERN_CHAR_TO_LEVEL[ch & 0xFF] : -1;
   }

   private static boolean isSyntaxChar(char ch) {
      return ch < PATTERN_CHAR_IS_SYNTAX.length ? PATTERN_CHAR_IS_SYNTAX[ch & 255] : false;
   }

   public SimpleDateFormat() {
      this(getDefaultPattern(), null, null, null, null, true, null);
   }

   public SimpleDateFormat(String pattern) {
      this(pattern, null, null, null, null, true, null);
   }

   public SimpleDateFormat(String pattern, Locale loc) {
      this(pattern, null, null, null, ULocale.forLocale(loc), true, null);
   }

   public SimpleDateFormat(String pattern, ULocale loc) {
      this(pattern, null, null, null, loc, true, null);
   }

   public SimpleDateFormat(String pattern, String override, ULocale loc) {
      this(pattern, null, null, null, loc, false, override);
   }

   public SimpleDateFormat(String pattern, DateFormatSymbols formatData) {
      this(pattern, (DateFormatSymbols)formatData.clone(), null, null, null, true, null);
   }

   @Deprecated
   public SimpleDateFormat(String pattern, DateFormatSymbols formatData, ULocale loc) {
      this(pattern, (DateFormatSymbols)formatData.clone(), null, null, loc, true, null);
   }

   SimpleDateFormat(String pattern, DateFormatSymbols formatData, Calendar calendar, ULocale locale, boolean useFastFormat, String override) {
      this(pattern, (DateFormatSymbols)formatData.clone(), (Calendar)calendar.clone(), null, locale, useFastFormat, override);
   }

   private SimpleDateFormat(
      String pattern, DateFormatSymbols formatData, Calendar calendar, NumberFormat numberFormat, ULocale locale, boolean useFastFormat, String override
   ) {
      this.pattern = pattern;
      this.formatData = formatData;
      this.calendar = calendar;
      this.numberFormat = numberFormat;
      this.locale = locale;
      this.useFastFormat = useFastFormat;
      this.override = override;
      this.initialize();
   }

   @Deprecated
   public static SimpleDateFormat getInstance(Calendar.FormatConfiguration formatConfig) {
      String ostr = formatConfig.getOverrideString();
      boolean useFast = ostr != null && ostr.length() > 0;
      return new SimpleDateFormat(
         formatConfig.getPatternString(),
         formatConfig.getDateFormatSymbols(),
         formatConfig.getCalendar(),
         null,
         formatConfig.getLocale(),
         useFast,
         formatConfig.getOverrideString()
      );
   }

   private void initialize() {
      if (this.locale == null) {
         this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
      }

      if (this.formatData == null) {
         this.formatData = new DateFormatSymbols(this.locale);
      }

      if (this.calendar == null) {
         this.calendar = Calendar.getInstance(this.locale);
      }

      if (this.numberFormat == null) {
         NumberingSystem ns = NumberingSystem.getInstance(this.locale);
         String digitString = ns.getDescription();
         if (!ns.isAlgorithmic() && digitString.length() == 10) {
            String nsName = ns.getName();
            this.numberFormat = new DateNumberFormat(this.locale, digitString, nsName);
         } else {
            this.numberFormat = NumberFormat.getInstance(this.locale);
         }
      }

      if (this.numberFormat instanceof DecimalFormat) {
         fixNumberFormatForDates(this.numberFormat);
      }

      this.defaultCenturyBase = System.currentTimeMillis();
      this.setLocale(this.calendar.getLocale(ULocale.VALID_LOCALE), this.calendar.getLocale(ULocale.ACTUAL_LOCALE));
      this.initLocalZeroPaddingNumberFormat();
      this.parsePattern();
      if (this.override == null
         && this.hasHanYearChar
         && this.calendar != null
         && this.calendar.getType().equals("japanese")
         && this.locale != null
         && this.locale.getLanguage().equals("ja")) {
         this.override = "y=jpanyear";
      }

      if (this.override != null) {
         this.initNumberFormatters(this.locale);
      }
   }

   private synchronized void initializeTimeZoneFormat(boolean bForceUpdate) {
      if (bForceUpdate || this.tzFormat == null) {
         this.tzFormat = TimeZoneFormat.getInstance(this.locale);
         String digits = null;
         if (!(this.numberFormat instanceof DecimalFormat)) {
            if (this.numberFormat instanceof DateNumberFormat) {
               digits = new String(((DateNumberFormat)this.numberFormat).getDigits());
            }
         } else {
            DecimalFormatSymbols decsym = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols();
            String[] strDigits = decsym.getDigitStringsLocal();
            StringBuilder digitsBuf = new StringBuilder();

            for (String digit : strDigits) {
               digitsBuf.append(digit);
            }

            digits = digitsBuf.toString();
         }

         if (digits != null && !this.tzFormat.getGMTOffsetDigits().equals(digits)) {
            if (this.tzFormat.isFrozen()) {
               this.tzFormat = this.tzFormat.cloneAsThawed();
            }

            this.tzFormat.setGMTOffsetDigits(digits);
         }
      }
   }

   private TimeZoneFormat tzFormat() {
      if (this.tzFormat == null) {
         this.initializeTimeZoneFormat(false);
      }

      return this.tzFormat;
   }

   private static synchronized String getDefaultPattern() {
      ULocale defaultLocale = ULocale.getDefault(ULocale.Category.FORMAT);
      if (!defaultLocale.equals(cachedDefaultLocale)) {
         cachedDefaultLocale = defaultLocale;
         Calendar cal = Calendar.getInstance(cachedDefaultLocale);

         try {
            ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance(
               "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", cachedDefaultLocale
            );
            String resourcePath = "calendar/" + cal.getType() + "/DateTimePatterns";
            ICUResourceBundle patternsRb = rb.findWithFallback(resourcePath);
            if (patternsRb == null) {
               patternsRb = rb.findWithFallback("calendar/gregorian/DateTimePatterns");
            }

            if (patternsRb != null && patternsRb.getSize() >= 9) {
               int defaultIndex = 8;
               if (patternsRb.getSize() >= 13) {
                  defaultIndex += 4;
               }

               String basePattern = patternsRb.getString(defaultIndex);
               cachedDefaultPattern = SimpleFormatterImpl.formatRawPattern(basePattern, 2, 2, patternsRb.getString(3), patternsRb.getString(7));
            } else {
               cachedDefaultPattern = "yy/MM/dd HH:mm";
            }
         } catch (MissingResourceException var7) {
            cachedDefaultPattern = "yy/MM/dd HH:mm";
         }
      }

      return cachedDefaultPattern;
   }

   private void parseAmbiguousDatesAsAfter(Date startDate) {
      this.defaultCenturyStart = startDate;
      this.calendar.setTime(startDate);
      this.defaultCenturyStartYear = this.calendar.get(1);
   }

   private void initializeDefaultCenturyStart(long baseTime) {
      this.defaultCenturyBase = baseTime;
      Calendar tmpCal = (Calendar)this.calendar.clone();
      tmpCal.setTimeInMillis(baseTime);
      tmpCal.add(1, -80);
      this.defaultCenturyStart = tmpCal.getTime();
      this.defaultCenturyStartYear = tmpCal.get(1);
   }

   private Date getDefaultCenturyStart() {
      if (this.defaultCenturyStart == null) {
         this.initializeDefaultCenturyStart(this.defaultCenturyBase);
      }

      return this.defaultCenturyStart;
   }

   private int getDefaultCenturyStartYear() {
      if (this.defaultCenturyStart == null) {
         this.initializeDefaultCenturyStart(this.defaultCenturyBase);
      }

      return this.defaultCenturyStartYear;
   }

   public void set2DigitYearStart(Date startDate) {
      this.parseAmbiguousDatesAsAfter(startDate);
   }

   public Date get2DigitYearStart() {
      return this.getDefaultCenturyStart();
   }

   @Override
   public void setContext(DisplayContext context) {
      super.setContext(context);
      if (this.capitalizationBrkIter == null
         && (
            context == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE
               || context == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU
               || context == DisplayContext.CAPITALIZATION_FOR_STANDALONE
         )) {
         this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
      }
   }

   @Override
   public StringBuffer format(Calendar cal, StringBuffer toAppendTo, FieldPosition pos) {
      return this.format(cal, toAppendTo, pos, null);
   }

   StringBuffer format(Calendar cal, StringBuffer toAppendTo, FieldPosition pos, List<FieldPosition> attributes) {
      TimeZone backupTZ = null;
      if (cal != this.calendar && !cal.getType().equals(this.calendar.getType())) {
         this.calendar.setTimeInMillis(cal.getTimeInMillis());
         backupTZ = this.calendar.getTimeZone();
         this.calendar.setTimeZone(cal.getTimeZone());
         cal = this.calendar;
      }

      StringBuffer result = this.format(cal, this.getContext(DisplayContext.Type.CAPITALIZATION), toAppendTo, pos, attributes);
      if (backupTZ != null) {
         this.calendar.setTimeZone(backupTZ);
      }

      return result;
   }

   private StringBuffer format(Calendar cal, DisplayContext capitalizationContext, StringBuffer toAppendTo, FieldPosition pos, List<FieldPosition> attributes) {
      pos.setBeginIndex(0);
      pos.setEndIndex(0);
      Object[] items = this.getPatternItems();

      for (int i = 0; i < items.length; i++) {
         if (items[i] instanceof String) {
            toAppendTo.append((String)items[i]);
         } else {
            SimpleDateFormat.PatternItem item = (SimpleDateFormat.PatternItem)items[i];
            int start = 0;
            if (attributes != null) {
               start = toAppendTo.length();
            }

            if (this.useFastFormat) {
               this.subFormat(toAppendTo, item.type, item.length, toAppendTo.length(), i, capitalizationContext, pos, item.type, cal);
            } else {
               toAppendTo.append(this.subFormat(item.type, item.length, toAppendTo.length(), i, capitalizationContext, pos, item.type, cal));
            }

            if (attributes != null) {
               int end = toAppendTo.length();
               if (end - start > 0) {
                  DateFormat.Field attr = this.patternCharToDateFormatField(item.type);
                  FieldPosition fp = new FieldPosition(attr);
                  fp.setBeginIndex(start);
                  fp.setEndIndex(end);
                  attributes.add(fp);
               }
            }
         }
      }

      return toAppendTo;
   }

   private static int getIndexFromChar(char ch) {
      return ch < PATTERN_CHAR_TO_INDEX.length ? PATTERN_CHAR_TO_INDEX[ch & 0xFF] : -1;
   }

   protected DateFormat.Field patternCharToDateFormatField(char ch) {
      int patternCharIndex = getIndexFromChar(ch);
      return patternCharIndex != -1 ? PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE[patternCharIndex] : null;
   }

   protected String subFormat(char ch, int count, int beginOffset, FieldPosition pos, DateFormatSymbols fmtData, Calendar cal) throws IllegalArgumentException {
      return this.subFormat(ch, count, beginOffset, 0, DisplayContext.CAPITALIZATION_NONE, pos, ch, cal);
   }

   @Deprecated
   protected String subFormat(
      char ch, int count, int beginOffset, int fieldNum, DisplayContext capitalizationContext, FieldPosition pos, char patternCharToOutput, Calendar cal
   ) {
      StringBuffer buf = new StringBuffer();
      this.subFormat(buf, ch, count, beginOffset, fieldNum, capitalizationContext, pos, patternCharToOutput, cal);
      return buf.toString();
   }

   @Deprecated
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
      int maxIntCount = Integer.MAX_VALUE;
      int bufstart = buf.length();
      TimeZone tz = cal.getTimeZone();
      long date = cal.getTimeInMillis();
      String result = null;
      int patternCharIndex = getIndexFromChar(ch);
      if (patternCharIndex == -1) {
         if (ch != 'l') {
            throw new IllegalArgumentException("Illegal pattern character '" + ch + "' in \"" + this.pattern + '"');
         }
      } else {
         int field = PATTERN_INDEX_TO_CALENDAR_FIELD[patternCharIndex];
         int value = 0;
         if (field >= 0) {
            value = patternCharIndex != 34 ? cal.get(field) : cal.getRelatedYear();
         }

         NumberFormat currentNumberFormat = this.getNumberFormat(ch);
         DateFormatSymbols.CapitalizationContextUsage capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.OTHER;
         switch (patternCharIndex) {
            case 0:
               if (cal.getType().equals("chinese") || cal.getType().equals("dangi")) {
                  this.zeroPaddingNumber(currentNumberFormat, buf, value, 1, 9);
               } else if (count == 5) {
                  safeAppend(this.formatData.narrowEras, value, buf);
                  capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.ERA_NARROW;
               } else if (count == 4) {
                  safeAppend(this.formatData.eraNames, value, buf);
                  capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.ERA_WIDE;
               } else {
                  safeAppend(this.formatData.eras, value, buf);
                  capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.ERA_ABBREV;
               }
               break;
            case 2:
            case 26:
               if (cal.getType().equals("hebrew")) {
                  boolean isLeap = HebrewCalendar.isLeapYear(cal.get(1));
                  if (isLeap && value == 6 && count >= 3) {
                     value = 13;
                  }

                  if (!isLeap && value >= 6 && count < 3) {
                     value--;
                  }
               }

               int isLeapMonth = this.formatData.leapMonthPatterns != null && this.formatData.leapMonthPatterns.length >= 7 ? cal.get(22) : 0;
               if (count == 5) {
                  if (patternCharIndex == 2) {
                     safeAppendWithMonthPattern(this.formatData.narrowMonths, value, buf, isLeapMonth != 0 ? this.formatData.leapMonthPatterns[2] : null);
                  } else {
                     safeAppendWithMonthPattern(
                        this.formatData.standaloneNarrowMonths, value, buf, isLeapMonth != 0 ? this.formatData.leapMonthPatterns[5] : null
                     );
                  }

                  capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.MONTH_NARROW;
               } else if (count == 4) {
                  if (patternCharIndex == 2) {
                     safeAppendWithMonthPattern(this.formatData.months, value, buf, isLeapMonth != 0 ? this.formatData.leapMonthPatterns[0] : null);
                     capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.MONTH_FORMAT;
                  } else {
                     safeAppendWithMonthPattern(this.formatData.standaloneMonths, value, buf, isLeapMonth != 0 ? this.formatData.leapMonthPatterns[3] : null);
                     capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.MONTH_STANDALONE;
                  }
               } else if (count == 3) {
                  if (patternCharIndex == 2) {
                     safeAppendWithMonthPattern(this.formatData.shortMonths, value, buf, isLeapMonth != 0 ? this.formatData.leapMonthPatterns[1] : null);
                     capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.MONTH_FORMAT;
                  } else {
                     safeAppendWithMonthPattern(
                        this.formatData.standaloneShortMonths, value, buf, isLeapMonth != 0 ? this.formatData.leapMonthPatterns[4] : null
                     );
                     capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.MONTH_STANDALONE;
                  }
               } else {
                  StringBuffer monthNumber = new StringBuffer();
                  this.zeroPaddingNumber(currentNumberFormat, monthNumber, value + 1, count, Integer.MAX_VALUE);
                  String[] monthNumberStrings = new String[]{monthNumber.toString()};
                  safeAppendWithMonthPattern(monthNumberStrings, 0, buf, isLeapMonth != 0 ? this.formatData.leapMonthPatterns[6] : null);
               }
               break;
            case 3:
            case 5:
            case 6:
            case 7:
            case 10:
            case 11:
            case 12:
            case 13:
            case 16:
            case 20:
            case 21:
            case 22:
            case 34:
            default:
               this.zeroPaddingNumber(currentNumberFormat, buf, value, count, Integer.MAX_VALUE);
               break;
            case 4:
               if (value == 0) {
                  this.zeroPaddingNumber(currentNumberFormat, buf, cal.getMaximum(11) + 1, count, Integer.MAX_VALUE);
               } else {
                  this.zeroPaddingNumber(currentNumberFormat, buf, value, count, Integer.MAX_VALUE);
               }
               break;
            case 8:
               this.numberFormat.setMinimumIntegerDigits(Math.min(3, count));
               this.numberFormat.setMaximumIntegerDigits(Integer.MAX_VALUE);
               if (count == 1) {
                  value /= 100;
               } else if (count == 2) {
                  value /= 10;
               }

               FieldPosition p = new FieldPosition(-1);
               this.numberFormat.format((long)value, buf, p);
               if (count > 3) {
                  this.numberFormat.setMinimumIntegerDigits(count - 3);
                  this.numberFormat.format(0L, buf, p);
               }
               break;
            case 14:
               if (count >= 5 && this.formatData.ampmsNarrow != null) {
                  safeAppend(this.formatData.ampmsNarrow, value, buf);
               } else {
                  safeAppend(this.formatData.ampms, value, buf);
               }
               break;
            case 15:
               if (value == 0) {
                  this.zeroPaddingNumber(currentNumberFormat, buf, cal.getLeastMaximum(10) + 1, count, Integer.MAX_VALUE);
               } else {
                  this.zeroPaddingNumber(currentNumberFormat, buf, value, count, Integer.MAX_VALUE);
               }
               break;
            case 17:
               if (count < 4) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.SPECIFIC_SHORT, tz, date);
                  capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.METAZONE_SHORT;
               } else {
                  result = this.tzFormat().format(TimeZoneFormat.Style.SPECIFIC_LONG, tz, date);
                  capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.METAZONE_LONG;
               }

               buf.append(result);
               break;
            case 19:
               if (count < 3) {
                  this.zeroPaddingNumber(currentNumberFormat, buf, value, count, Integer.MAX_VALUE);
                  break;
               } else {
                  value = cal.get(7);
               }
            case 9:
               if (count == 5) {
                  safeAppend(this.formatData.narrowWeekdays, value, buf);
                  capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.DAY_NARROW;
               } else if (count == 4) {
                  safeAppend(this.formatData.weekdays, value, buf);
                  capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
               } else if (count == 6 && this.formatData.shorterWeekdays != null) {
                  safeAppend(this.formatData.shorterWeekdays, value, buf);
                  capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
               } else {
                  safeAppend(this.formatData.shortWeekdays, value, buf);
                  capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.DAY_FORMAT;
               }
               break;
            case 23:
               if (count < 4) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL, tz, date);
               } else if (count == 5) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FULL, tz, date);
               } else {
                  result = this.tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT, tz, date);
               }

               buf.append(result);
               break;
            case 24:
               if (count == 1) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.GENERIC_SHORT, tz, date);
                  capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.METAZONE_SHORT;
               } else if (count == 4) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.GENERIC_LONG, tz, date);
                  capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.METAZONE_LONG;
               }

               buf.append(result);
               break;
            case 25:
               if (count < 3) {
                  this.zeroPaddingNumber(currentNumberFormat, buf, value, 1, Integer.MAX_VALUE);
               } else {
                  value = cal.get(7);
                  if (count == 5) {
                     safeAppend(this.formatData.standaloneNarrowWeekdays, value, buf);
                     capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.DAY_NARROW;
                  } else if (count == 4) {
                     safeAppend(this.formatData.standaloneWeekdays, value, buf);
                     capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                  } else if (count == 6 && this.formatData.standaloneShorterWeekdays != null) {
                     safeAppend(this.formatData.standaloneShorterWeekdays, value, buf);
                     capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                  } else {
                     safeAppend(this.formatData.standaloneShortWeekdays, value, buf);
                     capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.DAY_STANDALONE;
                  }
               }
               break;
            case 27:
               if (count >= 5) {
                  safeAppend(this.formatData.narrowQuarters, value / 3, buf);
               } else if (count == 4) {
                  safeAppend(this.formatData.quarters, value / 3, buf);
               } else if (count == 3) {
                  safeAppend(this.formatData.shortQuarters, value / 3, buf);
               } else {
                  this.zeroPaddingNumber(currentNumberFormat, buf, value / 3 + 1, count, Integer.MAX_VALUE);
               }
               break;
            case 28:
               if (count >= 5) {
                  safeAppend(this.formatData.standaloneNarrowQuarters, value / 3, buf);
               } else if (count == 4) {
                  safeAppend(this.formatData.standaloneQuarters, value / 3, buf);
               } else if (count == 3) {
                  safeAppend(this.formatData.standaloneShortQuarters, value / 3, buf);
               } else {
                  this.zeroPaddingNumber(currentNumberFormat, buf, value / 3 + 1, count, Integer.MAX_VALUE);
               }
               break;
            case 29:
               if (count == 1) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.ZONE_ID_SHORT, tz, date);
               } else if (count == 2) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.ZONE_ID, tz, date);
               } else if (count == 3) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.EXEMPLAR_LOCATION, tz, date);
               } else if (count == 4) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.GENERIC_LOCATION, tz, date);
                  capContextUsageType = DateFormatSymbols.CapitalizationContextUsage.ZONE_LONG;
               }

               buf.append(result);
               break;
            case 30:
               if (this.formatData.shortYearNames != null && value <= this.formatData.shortYearNames.length) {
                  safeAppend(this.formatData.shortYearNames, value - 1, buf);
                  break;
               }
            case 1:
            case 18:
               if (this.override != null && (this.override.compareTo("hebr") == 0 || this.override.indexOf("y=hebr") >= 0) && value > 5000 && value < 6000) {
                  value -= 5000;
               }

               if (count == 2) {
                  this.zeroPaddingNumber(currentNumberFormat, buf, value, 2, 2);
               } else {
                  this.zeroPaddingNumber(currentNumberFormat, buf, value, count, Integer.MAX_VALUE);
               }
               break;
            case 31:
               if (count == 1) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT_SHORT, tz, date);
               } else if (count == 4) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.LOCALIZED_GMT, tz, date);
               }

               buf.append(result);
               break;
            case 32:
               if (count == 1) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_SHORT, tz, date);
               } else if (count == 2) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_FIXED, tz, date);
               } else if (count == 3) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FIXED, tz, date);
               } else if (count == 4) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_FULL, tz, date);
               } else if (count == 5) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_FULL, tz, date);
               }

               buf.append(result);
               break;
            case 33:
               if (count == 1) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_SHORT, tz, date);
               } else if (count == 2) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FIXED, tz, date);
               } else if (count == 3) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FIXED, tz, date);
               } else if (count == 4) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL, tz, date);
               } else if (count == 5) {
                  result = this.tzFormat().format(TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FULL, tz, date);
               }

               buf.append(result);
               break;
            case 35:
               int hour = cal.get(11);
               String toAppend = null;
               if (hour == 12 && (!this.hasMinute || cal.get(12) == 0) && (!this.hasSecond || cal.get(13) == 0)) {
                  value = cal.get(9);
                  if (count <= 3) {
                     toAppend = this.formatData.abbreviatedDayPeriods[value];
                  } else if (count != 4 && count <= 5) {
                     toAppend = this.formatData.narrowDayPeriods[value];
                  } else {
                     toAppend = this.formatData.wideDayPeriods[value];
                  }
               }

               if (toAppend == null) {
                  this.subFormat(buf, 'a', count, beginOffset, fieldNum, capitalizationContext, pos, 'b', cal);
               } else {
                  buf.append(toAppend);
               }
               break;
            case 36:
               DayPeriodRules ruleSet = DayPeriodRules.getInstance(this.getLocale());
               if (ruleSet == null) {
                  this.subFormat(buf, 'a', count, beginOffset, fieldNum, capitalizationContext, pos, 'B', cal);
                  return;
               }

               int hourx = cal.get(11);
               int minute = 0;
               int second = 0;
               if (this.hasMinute) {
                  minute = cal.get(12);
               }

               if (this.hasSecond) {
                  second = cal.get(13);
               }

               DayPeriodRules.DayPeriod periodType;
               if (hourx == 0 && minute == 0 && second == 0 && ruleSet.hasMidnight()) {
                  periodType = DayPeriodRules.DayPeriod.MIDNIGHT;
               } else if (hourx == 12 && minute == 0 && second == 0 && ruleSet.hasNoon()) {
                  periodType = DayPeriodRules.DayPeriod.NOON;
               } else {
                  periodType = ruleSet.getDayPeriodForHour(hourx);
               }

               assert periodType != null;

               String toAppendx = null;
               if (periodType != DayPeriodRules.DayPeriod.AM && periodType != DayPeriodRules.DayPeriod.PM && periodType != DayPeriodRules.DayPeriod.MIDNIGHT) {
                  int index = periodType.ordinal();
                  if (count <= 3) {
                     toAppendx = this.formatData.abbreviatedDayPeriods[index];
                  } else if (count != 4 && count <= 5) {
                     toAppendx = this.formatData.narrowDayPeriods[index];
                  } else {
                     toAppendx = this.formatData.wideDayPeriods[index];
                  }
               }

               if (toAppendx == null && (periodType == DayPeriodRules.DayPeriod.MIDNIGHT || periodType == DayPeriodRules.DayPeriod.NOON)) {
                  periodType = ruleSet.getDayPeriodForHour(hourx);
                  int index = periodType.ordinal();
                  if (count <= 3) {
                     toAppendx = this.formatData.abbreviatedDayPeriods[index];
                  } else if (count != 4 && count <= 5) {
                     toAppendx = this.formatData.narrowDayPeriods[index];
                  } else {
                     toAppendx = this.formatData.wideDayPeriods[index];
                  }
               }

               if (periodType == DayPeriodRules.DayPeriod.AM || periodType == DayPeriodRules.DayPeriod.PM || toAppendx == null) {
                  this.subFormat(buf, 'a', count, beginOffset, fieldNum, capitalizationContext, pos, 'B', cal);
                  return;
               }

               buf.append(toAppendx);
               break;
            case 37:
               buf.append(this.formatData.getTimeSeparatorString());
         }

         if (fieldNum == 0 && capitalizationContext != null && buf.length() > bufstart && UCharacter.isLowerCase(buf.codePointAt(bufstart))) {
            boolean titlecase = false;
            switch (capitalizationContext) {
               case CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE:
                  titlecase = true;
                  break;
               case CAPITALIZATION_FOR_UI_LIST_OR_MENU:
               case CAPITALIZATION_FOR_STANDALONE:
                  if (this.formatData.capitalization != null) {
                     boolean[] transforms = this.formatData.capitalization.get(capContextUsageType);
                     titlecase = capitalizationContext == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU ? transforms[0] : transforms[1];
                  }
            }

            if (titlecase) {
               if (this.capitalizationBrkIter == null) {
                  this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
               }

               BreakIterator mutableCapitalizationBrkIter = (BreakIterator)this.capitalizationBrkIter.clone();
               String firstField = buf.substring(bufstart);
               String firstFieldTitleCase = UCharacter.toTitleCase(this.locale, firstField, mutableCapitalizationBrkIter, 768);
               buf.replace(bufstart, buf.length(), firstFieldTitleCase);
            }
         }

         int outputCharIndex = getIndexFromChar(patternCharToOutput);
         if (pos.getBeginIndex() == pos.getEndIndex()) {
            if (pos.getField() == PATTERN_INDEX_TO_DATE_FORMAT_FIELD[outputCharIndex]) {
               pos.setBeginIndex(beginOffset);
               pos.setEndIndex(beginOffset + buf.length() - bufstart);
            } else if (pos.getFieldAttribute() == PATTERN_INDEX_TO_DATE_FORMAT_ATTRIBUTE[outputCharIndex]) {
               pos.setBeginIndex(beginOffset);
               pos.setEndIndex(beginOffset + buf.length() - bufstart);
            }
         }
      }
   }

   private static void safeAppend(String[] array, int value, StringBuffer appendTo) {
      if (array != null && value >= 0 && value < array.length) {
         appendTo.append(array[value]);
      }
   }

   private static void safeAppendWithMonthPattern(String[] array, int value, StringBuffer appendTo, String monthPattern) {
      if (array != null && value >= 0 && value < array.length) {
         if (monthPattern == null) {
            appendTo.append(array[value]);
         } else {
            String s = SimpleFormatterImpl.formatRawPattern(monthPattern, 1, 1, array[value]);
            appendTo.append(s);
         }
      }
   }

   private Object[] getPatternItems() {
      if (this.patternItems != null) {
         return this.patternItems;
      } else {
         this.patternItems = PARSED_PATTERN_CACHE.get(this.pattern);
         if (this.patternItems != null) {
            return this.patternItems;
         } else {
            boolean isPrevQuote = false;
            boolean inQuote = false;
            StringBuilder text = new StringBuilder();
            char itemType = 0;
            int itemLength = 1;
            List<Object> items = new ArrayList<>();

            for (int i = 0; i < this.pattern.length(); i++) {
               char ch = this.pattern.charAt(i);
               if (ch == '\'') {
                  if (isPrevQuote) {
                     text.append('\'');
                     isPrevQuote = false;
                  } else {
                     isPrevQuote = true;
                     if (itemType != 0) {
                        items.add(new SimpleDateFormat.PatternItem(itemType, itemLength));
                        itemType = 0;
                     }
                  }

                  inQuote = !inQuote;
               } else {
                  isPrevQuote = false;
                  if (inQuote) {
                     text.append(ch);
                  } else if (isSyntaxChar(ch)) {
                     if (ch == itemType) {
                        itemLength++;
                     } else {
                        if (itemType == 0) {
                           if (text.length() > 0) {
                              items.add(text.toString());
                              text.setLength(0);
                           }
                        } else {
                           items.add(new SimpleDateFormat.PatternItem(itemType, itemLength));
                        }

                        itemType = ch;
                        itemLength = 1;
                     }
                  } else {
                     if (itemType != 0) {
                        items.add(new SimpleDateFormat.PatternItem(itemType, itemLength));
                        itemType = 0;
                     }

                     text.append(ch);
                  }
               }
            }

            if (itemType == 0) {
               if (text.length() > 0) {
                  items.add(text.toString());
                  text.setLength(0);
               }
            } else {
               items.add(new SimpleDateFormat.PatternItem(itemType, itemLength));
            }

            this.patternItems = items.toArray(new Object[items.size()]);
            PARSED_PATTERN_CACHE.put(this.pattern, this.patternItems);
            return this.patternItems;
         }
      }
   }

   @Deprecated
   protected void zeroPaddingNumber(NumberFormat nf, StringBuffer buf, int value, int minDigits, int maxDigits) {
      if (this.useLocalZeroPaddingNumberFormat && value >= 0) {
         this.fastZeroPaddingNumber(buf, value, minDigits, maxDigits);
      } else {
         nf.setMinimumIntegerDigits(minDigits);
         nf.setMaximumIntegerDigits(maxDigits);
         nf.format((long)value, buf, new FieldPosition(-1));
      }
   }

   @Override
   public void setNumberFormat(NumberFormat newNumberFormat) {
      super.setNumberFormat(newNumberFormat);
      this.initLocalZeroPaddingNumberFormat();
      this.initializeTimeZoneFormat(true);
      if (this.numberFormatters != null) {
         this.numberFormatters = null;
      }

      if (this.overrideMap != null) {
         this.overrideMap = null;
      }
   }

   private void initLocalZeroPaddingNumberFormat() {
      if (this.numberFormat instanceof DecimalFormat) {
         DecimalFormatSymbols tmpDecfs = ((DecimalFormat)this.numberFormat).getDecimalFormatSymbols();
         String[] tmpDigits = tmpDecfs.getDigitStringsLocal();
         this.useLocalZeroPaddingNumberFormat = true;
         this.decDigits = new char[10];

         for (int i = 0; i < 10; i++) {
            if (tmpDigits[i].length() > 1) {
               this.useLocalZeroPaddingNumberFormat = false;
               break;
            }

            this.decDigits[i] = tmpDigits[i].charAt(0);
         }
      } else if (this.numberFormat instanceof DateNumberFormat) {
         this.decDigits = ((DateNumberFormat)this.numberFormat).getDigits();
         this.useLocalZeroPaddingNumberFormat = true;
      } else {
         this.useLocalZeroPaddingNumberFormat = false;
      }

      if (this.useLocalZeroPaddingNumberFormat) {
         this.decimalBuf = new char[10];
      }
   }

   private void fastZeroPaddingNumber(StringBuffer buf, int value, int minDigits, int maxDigits) {
      int limit = this.decimalBuf.length < maxDigits ? this.decimalBuf.length : maxDigits;
      int index = limit - 1;

      while (true) {
         this.decimalBuf[index] = this.decDigits[value % 10];
         value /= 10;
         if (index == 0 || value == 0) {
            int padding;
            for (padding = minDigits - (limit - index); padding > 0 && index > 0; padding--) {
               this.decimalBuf[--index] = this.decDigits[0];
            }

            while (padding > 0) {
               buf.append(this.decDigits[0]);
               padding--;
            }

            buf.append(this.decimalBuf, index, limit - index);
            return;
         }

         index--;
      }
   }

   protected String zeroPaddingNumber(long value, int minDigits, int maxDigits) {
      this.numberFormat.setMinimumIntegerDigits(minDigits);
      this.numberFormat.setMaximumIntegerDigits(maxDigits);
      return this.numberFormat.format(value);
   }

   private static final boolean isNumeric(char formatChar, int count) {
      return "ADdFgHhKkmrSsuWwYy".indexOf(formatChar) >= 0 || count <= 2 && "ceLMQq".indexOf(formatChar) >= 0;
   }

   @Override
   public void parse(String text, Calendar cal, ParsePosition parsePos) {
      TimeZone backupTZ = null;
      Calendar resultCal = null;
      if (cal != this.calendar && !cal.getType().equals(this.calendar.getType())) {
         this.calendar.setTimeInMillis(cal.getTimeInMillis());
         backupTZ = this.calendar.getTimeZone();
         this.calendar.setTimeZone(cal.getTimeZone());
         resultCal = cal;
         cal = this.calendar;
      }

      int pos = parsePos.getIndex();
      if (pos < 0) {
         parsePos.setErrorIndex(0);
      } else {
         int start = pos;
         Output<DayPeriodRules.DayPeriod> dayPeriod = new Output<>(null);
         Output<TimeZoneFormat.TimeType> tzTimeType = new Output<>(TimeZoneFormat.TimeType.UNKNOWN);
         boolean[] ambiguousYear = new boolean[]{false};
         int numericFieldStart = -1;
         int numericFieldLength = 0;
         int numericStartPos = 0;
         MessageFormat numericLeapMonthFormatter = null;
         if (this.formatData.leapMonthPatterns != null && this.formatData.leapMonthPatterns.length >= 7) {
            numericLeapMonthFormatter = new MessageFormat(this.formatData.leapMonthPatterns[6], this.locale);
         }

         Object[] items = this.getPatternItems();
         int i = 0;

         while (i < items.length) {
            if (items[i] instanceof SimpleDateFormat.PatternItem) {
               SimpleDateFormat.PatternItem field = (SimpleDateFormat.PatternItem)items[i];
               if (field.isNumeric
                  && numericFieldStart == -1
                  && i + 1 < items.length
                  && items[i + 1] instanceof SimpleDateFormat.PatternItem
                  && ((SimpleDateFormat.PatternItem)items[i + 1]).isNumeric) {
                  numericFieldStart = i;
                  numericFieldLength = field.length;
                  numericStartPos = pos;
               }

               if (numericFieldStart != -1) {
                  int len = field.length;
                  if (numericFieldStart == i) {
                     len = numericFieldLength;
                  }

                  pos = this.subParse(text, pos, field.type, len, true, false, ambiguousYear, cal, numericLeapMonthFormatter, tzTimeType);
                  if (pos < 0) {
                     if (--numericFieldLength == 0) {
                        parsePos.setIndex(start);
                        parsePos.setErrorIndex(pos);
                        if (backupTZ != null) {
                           this.calendar.setTimeZone(backupTZ);
                        }

                        return;
                     }

                     i = numericFieldStart;
                     pos = numericStartPos;
                     continue;
                  }
               } else if (field.type != 'l') {
                  numericFieldStart = -1;
                  int s = pos;
                  pos = this.subParse(text, pos, field.type, field.length, false, true, ambiguousYear, cal, numericLeapMonthFormatter, tzTimeType, dayPeriod);
                  if (pos < 0) {
                     if (pos != -32000) {
                        parsePos.setIndex(start);
                        parsePos.setErrorIndex(s);
                        if (backupTZ != null) {
                           this.calendar.setTimeZone(backupTZ);
                        }

                        return;
                     }

                     pos = s;
                     if (i + 1 < items.length) {
                        String patl = null;

                        try {
                           patl = (String)items[i + 1];
                        } catch (ClassCastException var32) {
                           parsePos.setIndex(start);
                           parsePos.setErrorIndex(s);
                           if (backupTZ != null) {
                              this.calendar.setTimeZone(backupTZ);
                           }

                           return;
                        }

                        if (patl == null) {
                           patl = (String)items[i + 1];
                        }

                        int plen = patl.length();

                        int idx;
                        for (idx = 0; idx < plen; idx++) {
                           char pch = patl.charAt(idx);
                           if (!PatternProps.isWhiteSpace(pch)) {
                              break;
                           }
                        }

                        if (idx == plen) {
                           i++;
                        }
                     }
                  }
               }
            } else {
               numericFieldStart = -1;
               boolean[] complete = new boolean[1];
               pos = this.matchLiteral(text, pos, items, i, complete);
               if (!complete[0]) {
                  parsePos.setIndex(start);
                  parsePos.setErrorIndex(pos);
                  if (backupTZ != null) {
                     this.calendar.setTimeZone(backupTZ);
                  }

                  return;
               }
            }

            i++;
         }

         if (pos < text.length()) {
            char extra = text.charAt(pos);
            if (extra == '.' && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE) && items.length != 0) {
               Object lastItem = items[items.length - 1];
               if (lastItem instanceof SimpleDateFormat.PatternItem && !((SimpleDateFormat.PatternItem)lastItem).isNumeric) {
                  pos++;
               }
            }
         }

         if (dayPeriod.value != null) {
            DayPeriodRules ruleSet = DayPeriodRules.getInstance(this.getLocale());
            if (!cal.isSet(10) && !cal.isSet(11)) {
               double midPoint = ruleSet.getMidPointForDayPeriod(dayPeriod.value);
               int midPointHour = (int)midPoint;
               int midPointMinute = midPoint - midPointHour > 0.0 ? 30 : 0;
               cal.set(11, midPointHour);
               cal.set(12, midPointMinute);
            } else {
               int hourOfDay;
               if (cal.isSet(11)) {
                  hourOfDay = cal.get(11);
               } else {
                  hourOfDay = cal.get(10);
                  if (hourOfDay == 0) {
                     hourOfDay = 12;
                  }
               }

               assert 0 <= hourOfDay && hourOfDay <= 23;

               if (hourOfDay != 0 && (13 > hourOfDay || hourOfDay > 23)) {
                  if (hourOfDay == 12) {
                     hourOfDay = 0;
                  }

                  double currentHour = hourOfDay + cal.get(12) / 60.0;
                  double midPointHour = ruleSet.getMidPointForDayPeriod(dayPeriod.value);
                  double hoursAheadMidPoint = currentHour - midPointHour;
                  if (-6.0 <= hoursAheadMidPoint && hoursAheadMidPoint < 6.0) {
                     cal.set(9, 0);
                  } else {
                     cal.set(9, 1);
                  }
               } else {
                  cal.set(11, hourOfDay);
               }
            }
         }

         parsePos.setIndex(pos);

         try {
            TimeZoneFormat.TimeType tztype = tzTimeType.value;
            if (ambiguousYear[0] || tztype != TimeZoneFormat.TimeType.UNKNOWN) {
               if (ambiguousYear[0]) {
                  Calendar copy = (Calendar)cal.clone();
                  Date parsedDate = copy.getTime();
                  if (parsedDate.before(this.getDefaultCenturyStart())) {
                     cal.set(1, this.getDefaultCenturyStartYear() + 100);
                  }
               }

               if (tztype != TimeZoneFormat.TimeType.UNKNOWN) {
                  Calendar copy = (Calendar)cal.clone();
                  TimeZone tz = copy.getTimeZone();
                  BasicTimeZone btz = null;
                  if (tz instanceof BasicTimeZone) {
                     btz = (BasicTimeZone)tz;
                  }

                  copy.set(15, 0);
                  copy.set(16, 0);
                  long localMillis = copy.getTimeInMillis();
                  int[] offsets = new int[2];
                  if (btz != null) {
                     if (tztype == TimeZoneFormat.TimeType.STANDARD) {
                        btz.getOffsetFromLocal(localMillis, BasicTimeZone.LocalOption.STANDARD_FORMER, BasicTimeZone.LocalOption.STANDARD_LATTER, offsets);
                     } else {
                        btz.getOffsetFromLocal(localMillis, BasicTimeZone.LocalOption.DAYLIGHT_FORMER, BasicTimeZone.LocalOption.DAYLIGHT_LATTER, offsets);
                     }
                  } else {
                     tz.getOffset(localMillis, true, offsets);
                     if (tztype == TimeZoneFormat.TimeType.STANDARD && offsets[1] != 0 || tztype == TimeZoneFormat.TimeType.DAYLIGHT && offsets[1] == 0) {
                        tz.getOffset(localMillis - 86400000L, true, offsets);
                     }
                  }

                  int resolvedSavings = offsets[1];
                  if (tztype == TimeZoneFormat.TimeType.STANDARD) {
                     if (offsets[1] != 0) {
                        resolvedSavings = 0;
                     }
                  } else if (offsets[1] == 0) {
                     if (btz != null) {
                        long baseTime = localMillis + offsets[0];
                        long time = baseTime;
                        long limit = baseTime + 946080000000L;
                        TimeZoneTransition trs = null;

                        while (time < limit) {
                           trs = btz.getNextTransition(time, false);
                           if (trs == null) {
                              break;
                           }

                           resolvedSavings = trs.getTo().getDSTSavings();
                           if (resolvedSavings != 0) {
                              break;
                           }

                           time = trs.getTime();
                        }

                        if (resolvedSavings == 0) {
                           time = baseTime;
                           limit = baseTime - 946080000000L;

                           while (time > limit) {
                              trs = btz.getPreviousTransition(time, true);
                              if (trs == null) {
                                 break;
                              }

                              resolvedSavings = trs.getFrom().getDSTSavings();
                              if (resolvedSavings != 0) {
                                 break;
                              }

                              time = trs.getTime() - 1L;
                           }

                           if (resolvedSavings == 0) {
                              resolvedSavings = btz.getDSTSavings();
                           }
                        }
                     } else {
                        resolvedSavings = tz.getDSTSavings();
                     }

                     if (resolvedSavings == 0) {
                        resolvedSavings = 3600000;
                     }
                  }

                  cal.set(15, offsets[0]);
                  cal.set(16, resolvedSavings);
               }
            }
         } catch (IllegalArgumentException var33) {
            parsePos.setErrorIndex(pos);
            parsePos.setIndex(start);
            if (backupTZ != null) {
               this.calendar.setTimeZone(backupTZ);
            }

            return;
         }

         if (resultCal != null) {
            resultCal.setTimeZone(cal.getTimeZone());
            resultCal.setTimeInMillis(cal.getTimeInMillis());
         }

         if (backupTZ != null) {
            this.calendar.setTimeZone(backupTZ);
         }
      }
   }

   private int matchLiteral(String text, int pos, Object[] items, int itemIndex, boolean[] complete) {
      int originalPos = pos;
      String patternLiteral = (String)items[itemIndex];
      int plen = patternLiteral.length();
      int tlen = text.length();
      int idx = 0;

      while (idx < plen && pos < tlen) {
         char pch = patternLiteral.charAt(idx);
         char ich = text.charAt(pos);
         if (PatternProps.isWhiteSpace(pch) && PatternProps.isWhiteSpace(ich)) {
            while (idx + 1 < plen && PatternProps.isWhiteSpace(patternLiteral.charAt(idx + 1))) {
               idx++;
            }

            while (pos + 1 < tlen && PatternProps.isWhiteSpace(text.charAt(pos + 1))) {
               pos++;
            }
         } else if (pch != ich) {
            if (ich == '.' && pos == originalPos && 0 < itemIndex && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE)) {
               Object before = items[itemIndex - 1];
               if (!(before instanceof SimpleDateFormat.PatternItem)) {
                  break;
               }

               boolean isNumeric = ((SimpleDateFormat.PatternItem)before).isNumeric;
               if (isNumeric) {
                  break;
               }

               pos++;
               continue;
            }

            if ((pch == ' ' || pch == '.') && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE)) {
               idx++;
               continue;
            }

            if (pos == originalPos || !this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH)) {
               break;
            }

            idx++;
            continue;
         }

         idx++;
         pos++;
      }

      complete[0] = idx == plen;
      if (!complete[0]
         && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_WHITESPACE)
         && 0 < itemIndex
         && itemIndex < items.length - 1
         && originalPos < tlen) {
         Object beforex = items[itemIndex - 1];
         Object after = items[itemIndex + 1];
         if (beforex instanceof SimpleDateFormat.PatternItem && after instanceof SimpleDateFormat.PatternItem) {
            char beforeType = ((SimpleDateFormat.PatternItem)beforex).type;
            char afterType = ((SimpleDateFormat.PatternItem)after).type;
            if (DATE_PATTERN_TYPE.contains(beforeType) != DATE_PATTERN_TYPE.contains(afterType)) {
               int newPos;
               for (newPos = originalPos; newPos < tlen; newPos++) {
                  char ich = text.charAt(newPos);
                  if (!PatternProps.isWhiteSpace(ich)) {
                     break;
                  }
               }

               complete[0] = newPos > originalPos;
               pos = newPos;
            }
         }
      }

      return pos;
   }

   protected int matchString(String text, int start, int field, String[] data, Calendar cal) {
      return this.matchString(text, start, field, data, null, cal);
   }

   @Deprecated
   private int matchString(String text, int start, int field, String[] data, String monthPattern, Calendar cal) {
      int i = 0;
      int count = data.length;
      if (field == 7) {
         i = 1;
      }

      int bestMatchLength = 0;
      int bestMatch = -1;
      int isLeapMonth = 0;

      for (int matchLength = 0; i < count; i++) {
         int length = data[i].length();
         if (length > bestMatchLength && (matchLength = this.regionMatchesWithOptionalDot(text, start, data[i], length)) >= 0) {
            bestMatch = i;
            bestMatchLength = matchLength;
            isLeapMonth = 0;
         }

         if (monthPattern != null) {
            String leapMonthName = SimpleFormatterImpl.formatRawPattern(monthPattern, 1, 1, data[i]);
            length = leapMonthName.length();
            if (length > bestMatchLength && (matchLength = this.regionMatchesWithOptionalDot(text, start, leapMonthName, length)) >= 0) {
               bestMatch = i;
               bestMatchLength = matchLength;
               isLeapMonth = 1;
            }
         }
      }

      if (bestMatch >= 0) {
         if (field >= 0) {
            if (field == 1) {
               bestMatch++;
            }

            cal.set(field, bestMatch);
            if (monthPattern != null) {
               cal.set(22, isLeapMonth);
            }
         }

         return start + bestMatchLength;
      } else {
         return ~start;
      }
   }

   private int regionMatchesWithOptionalDot(String text, int start, String data, int length) {
      boolean matches = text.regionMatches(true, start, data, 0, length);
      if (matches) {
         return length;
      } else {
         return data.length() > 0 && data.charAt(data.length() - 1) == 46 && text.regionMatches(true, start, data, 0, length - 1) ? length - 1 : -1;
      }
   }

   protected int matchQuarterString(String text, int start, int field, String[] data, Calendar cal) {
      int i = 0;
      int count = data.length;
      int bestMatchLength = 0;
      int bestMatch = -1;

      for (int matchLength = 0; i < count; i++) {
         int length = data[i].length();
         if (length > bestMatchLength && (matchLength = this.regionMatchesWithOptionalDot(text, start, data[i], length)) >= 0) {
            bestMatch = i;
            bestMatchLength = matchLength;
         }
      }

      if (bestMatch >= 0) {
         cal.set(field, bestMatch * 3);
         return start + bestMatchLength;
      } else {
         return -start;
      }
   }

   private int matchDayPeriodString(String text, int start, String[] data, int dataLength, Output<DayPeriodRules.DayPeriod> dayPeriod) {
      int bestMatchLength = 0;
      int bestMatch = -1;
      int matchLength = 0;

      for (int i = 0; i < dataLength; i++) {
         if (data[i] != null) {
            int length = data[i].length();
            if (length > bestMatchLength && (matchLength = this.regionMatchesWithOptionalDot(text, start, data[i], length)) >= 0) {
               bestMatch = i;
               bestMatchLength = matchLength;
            }
         }
      }

      if (bestMatch >= 0) {
         dayPeriod.value = DayPeriodRules.DayPeriod.VALUES[bestMatch];
         return start + bestMatchLength;
      } else {
         return -start;
      }
   }

   protected int subParse(String text, int start, char ch, int count, boolean obeyCount, boolean allowNegative, boolean[] ambiguousYear, Calendar cal) {
      return this.subParse(text, start, ch, count, obeyCount, allowNegative, ambiguousYear, cal, null, null);
   }

   private int subParse(
      String text,
      int start,
      char ch,
      int count,
      boolean obeyCount,
      boolean allowNegative,
      boolean[] ambiguousYear,
      Calendar cal,
      MessageFormat numericLeapMonthFormatter,
      Output<TimeZoneFormat.TimeType> tzTimeType
   ) {
      return this.subParse(text, start, ch, count, obeyCount, allowNegative, ambiguousYear, cal, null, null, null);
   }

   @Deprecated
   private int subParse(
      String text,
      int start,
      char ch,
      int count,
      boolean obeyCount,
      boolean allowNegative,
      boolean[] ambiguousYear,
      Calendar cal,
      MessageFormat numericLeapMonthFormatter,
      Output<TimeZoneFormat.TimeType> tzTimeType,
      Output<DayPeriodRules.DayPeriod> dayPeriod
   ) {
      Number number = null;
      NumberFormat currentNumberFormat = null;
      int value = 0;
      ParsePosition pos = new ParsePosition(0);
      int patternCharIndex = getIndexFromChar(ch);
      if (patternCharIndex == -1) {
         return ~start;
      } else {
         currentNumberFormat = this.getNumberFormat(ch);
         int field = PATTERN_INDEX_TO_CALENDAR_FIELD[patternCharIndex];
         if (numericLeapMonthFormatter != null) {
            numericLeapMonthFormatter.setFormatByArgumentIndex(0, currentNumberFormat);
         }

         boolean isChineseCalendar = cal.getType().equals("chinese") || cal.getType().equals("dangi");

         while (start < text.length()) {
            int c = UTF16.charAt(text, start);
            if (!UCharacter.isUWhiteSpace(c) || !PatternProps.isWhiteSpace(c)) {
               pos.setIndex(start);
               if (patternCharIndex == 4
                  || patternCharIndex == 15
                  || patternCharIndex == 2 && count <= 2
                  || patternCharIndex == 26
                  || patternCharIndex == 19
                  || patternCharIndex == 25
                  || patternCharIndex == 1
                  || patternCharIndex == 18
                  || patternCharIndex == 30
                  || patternCharIndex == 0 && isChineseCalendar
                  || patternCharIndex == 27
                  || patternCharIndex == 28
                  || patternCharIndex == 8) {
                  boolean parsedNumericLeapMonth = false;
                  if (numericLeapMonthFormatter != null && (patternCharIndex == 2 || patternCharIndex == 26)) {
                     Object[] args = numericLeapMonthFormatter.parse(text, pos);
                     if (args != null && pos.getIndex() > start && args[0] instanceof Number) {
                        parsedNumericLeapMonth = true;
                        number = (Number)args[0];
                        cal.set(22, 1);
                     } else {
                        pos.setIndex(start);
                        cal.set(22, 0);
                     }
                  }

                  if (!parsedNumericLeapMonth) {
                     if (obeyCount) {
                        if (start + count > text.length()) {
                           return ~start;
                        }

                        number = this.parseInt(text, count, pos, allowNegative, currentNumberFormat);
                     } else {
                        number = this.parseInt(text, pos, allowNegative, currentNumberFormat);
                     }

                     if (number == null && !this.allowNumericFallback(patternCharIndex)) {
                        return ~start;
                     }
                  }

                  if (number != null) {
                     value = number.intValue();
                  }
               }

               switch (patternCharIndex) {
                  case 0:
                     if (isChineseCalendar) {
                        cal.set(0, value);
                        return pos.getIndex();
                     }

                     c = 0;
                     if (count == 5) {
                        c = this.matchString(text, start, 0, this.formatData.narrowEras, null, cal);
                     } else if (count == 4) {
                        c = this.matchString(text, start, 0, this.formatData.eraNames, null, cal);
                     } else {
                        c = this.matchString(text, start, 0, this.formatData.eras, null, cal);
                     }

                     if (c == ~start) {
                        c = -32000;
                     }

                     return c;
                  case 1:
                  case 18:
                     if (this.override != null && (this.override.compareTo("hebr") == 0 || this.override.indexOf("y=hebr") >= 0) && value < 1000) {
                        value += 5000;
                     } else if (count == 2 && countDigits(text, start, pos.getIndex()) == 2 && cal.haveDefaultCentury()) {
                        int ambiguousTwoDigitYear = this.getDefaultCenturyStartYear() % 100;
                        ambiguousYear[0] = value == ambiguousTwoDigitYear;
                        value += this.getDefaultCenturyStartYear() / 100 * 100 + (value < ambiguousTwoDigitYear ? 100 : 0);
                     }

                     cal.set(field, value);
                     if (DelayedHebrewMonthCheck) {
                        if (!HebrewCalendar.isLeapYear(value)) {
                           cal.add(2, 1);
                        }

                        DelayedHebrewMonthCheck = false;
                     }

                     return pos.getIndex();
                  case 2:
                  case 26:
                     if (count > 2 && (number == null || !this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC))) {
                        boolean haveMonthPat = this.formatData.leapMonthPatterns != null && this.formatData.leapMonthPatterns.length >= 7;
                        int newStartxxxxxxx = 0;
                        if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 4) {
                           newStartxxxxxxx = patternCharIndex == 2
                              ? this.matchString(text, start, 2, this.formatData.months, haveMonthPat ? this.formatData.leapMonthPatterns[0] : null, cal)
                              : this.matchString(
                                 text, start, 2, this.formatData.standaloneMonths, haveMonthPat ? this.formatData.leapMonthPatterns[3] : null, cal
                              );
                           if (newStartxxxxxxx > 0) {
                              return newStartxxxxxxx;
                           }
                        }

                        if (!this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) && count != 3) {
                           return newStartxxxxxxx;
                        }

                        return patternCharIndex == 2
                           ? this.matchString(text, start, 2, this.formatData.shortMonths, haveMonthPat ? this.formatData.leapMonthPatterns[1] : null, cal)
                           : this.matchString(
                              text, start, 2, this.formatData.standaloneShortMonths, haveMonthPat ? this.formatData.leapMonthPatterns[4] : null, cal
                           );
                     }

                     cal.set(2, value - 1);
                     if (cal.getType().equals("hebrew") && value >= 6) {
                        if (cal.isSet(1)) {
                           if (!HebrewCalendar.isLeapYear(cal.get(1))) {
                              cal.set(2, value);
                           }
                        } else {
                           DelayedHebrewMonthCheck = true;
                        }
                     }

                     return pos.getIndex();
                  case 3:
                  case 5:
                  case 6:
                  case 7:
                  case 10:
                  case 11:
                  case 12:
                  case 13:
                  case 16:
                  case 20:
                  case 21:
                  case 22:
                  case 34:
                  default:
                     if (obeyCount) {
                        if (start + count > text.length()) {
                           return -start;
                        }

                        number = this.parseInt(text, count, pos, allowNegative, currentNumberFormat);
                     } else {
                        number = this.parseInt(text, pos, allowNegative, currentNumberFormat);
                     }

                     if (!this.isLenient() && pos.getIndex() < start + count) {
                        return -start;
                     }

                     if (number != null) {
                        if (patternCharIndex != 34) {
                           cal.set(field, number.intValue());
                        } else {
                           cal.setRelatedYear(number.intValue());
                        }

                        return pos.getIndex();
                     }

                     return ~start;
                  case 4:
                     if (value == cal.getMaximum(11) + 1) {
                        value = 0;
                     }

                     cal.set(11, value);
                     return pos.getIndex();
                  case 8:
                     int i = countDigits(text, start, pos.getIndex());
                     if (i < 3) {
                        while (i < 3) {
                           value *= 10;
                           i++;
                        }
                     } else {
                        int a;
                        for (a = 1; i > 3; i--) {
                           a *= 10;
                        }

                        value /= a;
                     }

                     cal.set(14, value);
                     return pos.getIndex();
                  case 14:
                     int newStartxxxxx = 0;
                     if ((
                           this.formatData.ampmsNarrow == null
                              || count < 5
                              || this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH)
                        )
                        && (newStartxxxxx = this.matchString(text, start, 9, this.formatData.ampms, null, cal)) > 0) {
                        return newStartxxxxx;
                     }

                     if (this.formatData.ampmsNarrow != null
                        && (count >= 5 || this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH))
                        && (newStartxxxxx = this.matchString(text, start, 9, this.formatData.ampmsNarrow, null, cal)) > 0) {
                        return newStartxxxxx;
                     }

                     return ~start;
                  case 15:
                     if (value == cal.getLeastMaximum(10) + 1) {
                        value = 0;
                     }

                     cal.set(10, value);
                     return pos.getIndex();
                  case 17:
                     TimeZoneFormat.Style style = count < 4 ? TimeZoneFormat.Style.SPECIFIC_SHORT : TimeZoneFormat.Style.SPECIFIC_LONG;
                     TimeZone tz = this.tzFormat().parse(style, text, pos, tzTimeType);
                     if (tz != null) {
                        cal.setTimeZone(tz);
                        return pos.getIndex();
                     }

                     return ~start;
                  case 19:
                     if (count <= 2 || number != null && this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC)) {
                        cal.set(field, value);
                        return pos.getIndex();
                     }
                  case 9:
                     int newStartxxxxxx = 0;
                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 4)
                        && (newStartxxxxxx = this.matchString(text, start, 7, this.formatData.weekdays, null, cal)) > 0) {
                        return newStartxxxxxx;
                     }

                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 3)
                        && (newStartxxxxxx = this.matchString(text, start, 7, this.formatData.shortWeekdays, null, cal)) > 0) {
                        return newStartxxxxxx;
                     }

                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 6)
                        && this.formatData.shorterWeekdays != null
                        && (newStartxxxxxx = this.matchString(text, start, 7, this.formatData.shorterWeekdays, null, cal)) > 0) {
                        return newStartxxxxxx;
                     }

                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 5)
                        && this.formatData.narrowWeekdays != null
                        && (newStartxxxxxx = this.matchString(text, start, 7, this.formatData.narrowWeekdays, null, cal)) > 0) {
                        return newStartxxxxxx;
                     }

                     return newStartxxxxxx;
                  case 23:
                     TimeZoneFormat.Style style = count < 4
                        ? TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL
                        : (count == 5 ? TimeZoneFormat.Style.ISO_EXTENDED_FULL : TimeZoneFormat.Style.LOCALIZED_GMT);
                     TimeZone tz = this.tzFormat().parse(style, text, pos, tzTimeType);
                     if (tz != null) {
                        cal.setTimeZone(tz);
                        return pos.getIndex();
                     }

                     return ~start;
                  case 24:
                     TimeZoneFormat.Style style = count < 4 ? TimeZoneFormat.Style.GENERIC_SHORT : TimeZoneFormat.Style.GENERIC_LONG;
                     TimeZone tz = this.tzFormat().parse(style, text, pos, tzTimeType);
                     if (tz != null) {
                        cal.setTimeZone(tz);
                        return pos.getIndex();
                     }

                     return ~start;
                  case 25:
                     if (count != 1 && (number == null || !this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC))) {
                        int newStartxxxx = 0;
                        if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 4)
                           && (newStartxxxx = this.matchString(text, start, 7, this.formatData.standaloneWeekdays, null, cal)) > 0) {
                           return newStartxxxx;
                        }

                        if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 3)
                           && (newStartxxxx = this.matchString(text, start, 7, this.formatData.standaloneShortWeekdays, null, cal)) > 0) {
                           return newStartxxxx;
                        }

                        if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 6)
                           && this.formatData.standaloneShorterWeekdays != null) {
                           return this.matchString(text, start, 7, this.formatData.standaloneShorterWeekdays, null, cal);
                        }

                        return newStartxxxx;
                     }

                     cal.set(field, value);
                     return pos.getIndex();
                  case 27:
                     if (count <= 2 && number != null) {
                        cal.set(2, (value - 1) * 3);
                        return pos.getIndex();
                     }

                     int newStartxxx = 0;
                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 4)
                        && (newStartxxx = this.matchQuarterString(text, start, 2, this.formatData.quarters, cal)) > 0) {
                        return newStartxxx;
                     }

                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 3)
                        && (newStartxxx = this.matchQuarterString(text, start, 2, this.formatData.shortQuarters, cal)) > 0) {
                        return newStartxxx;
                     }

                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 5)
                        && (newStartxxx = this.matchQuarterString(text, start, 2, this.formatData.narrowQuarters, cal)) > 0) {
                        return newStartxxx;
                     }

                     if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC) && number != null) {
                        cal.set(2, (value - 1) * 3);
                        return pos.getIndex();
                     }

                     return newStartxxx;
                  case 28:
                     if (count <= 2 && number != null) {
                        cal.set(2, (value - 1) * 3);
                        return pos.getIndex();
                     }

                     int newStartxx = 0;
                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 4)
                        && (newStartxx = this.matchQuarterString(text, start, 2, this.formatData.standaloneQuarters, cal)) > 0) {
                        return newStartxx;
                     }

                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 3)
                        && (newStartxx = this.matchQuarterString(text, start, 2, this.formatData.standaloneShortQuarters, cal)) > 0) {
                        return newStartxx;
                     }

                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 5)
                        && (newStartxx = this.matchQuarterString(text, start, 2, this.formatData.standaloneNarrowQuarters, cal)) > 0) {
                        return newStartxx;
                     }

                     if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC) && number != null) {
                        cal.set(2, (value - 1) * 3);
                        return pos.getIndex();
                     }

                     return newStartxx;
                  case 29:
                     TimeZoneFormat.Style style = null;
                     switch (count) {
                        case 1:
                           style = TimeZoneFormat.Style.ZONE_ID_SHORT;
                           break;
                        case 2:
                           style = TimeZoneFormat.Style.ZONE_ID;
                           break;
                        case 3:
                           style = TimeZoneFormat.Style.EXEMPLAR_LOCATION;
                           break;
                        default:
                           style = TimeZoneFormat.Style.GENERIC_LOCATION;
                     }

                     TimeZone tz = this.tzFormat().parse(style, text, pos, tzTimeType);
                     if (tz != null) {
                        cal.setTimeZone(tz);
                        return pos.getIndex();
                     }

                     return ~start;
                  case 30:
                     if (this.formatData.shortYearNames != null) {
                        int newStartxx = this.matchString(text, start, 1, this.formatData.shortYearNames, null, cal);
                        if (newStartxx > 0) {
                           return newStartxx;
                        }
                     }

                     if (number == null
                        || !this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_ALLOW_NUMERIC)
                           && this.formatData.shortYearNames != null
                           && value <= this.formatData.shortYearNames.length) {
                        return ~start;
                     }

                     cal.set(1, value);
                     return pos.getIndex();
                  case 31:
                     TimeZoneFormat.Style style = count < 4 ? TimeZoneFormat.Style.LOCALIZED_GMT_SHORT : TimeZoneFormat.Style.LOCALIZED_GMT;
                     TimeZone tz = this.tzFormat().parse(style, text, pos, tzTimeType);
                     if (tz != null) {
                        cal.setTimeZone(tz);
                        return pos.getIndex();
                     }

                     return ~start;
                  case 32:
                     TimeZoneFormat.Style style;
                     switch (count) {
                        case 1:
                           style = TimeZoneFormat.Style.ISO_BASIC_SHORT;
                           break;
                        case 2:
                           style = TimeZoneFormat.Style.ISO_BASIC_FIXED;
                           break;
                        case 3:
                           style = TimeZoneFormat.Style.ISO_EXTENDED_FIXED;
                           break;
                        case 4:
                           style = TimeZoneFormat.Style.ISO_BASIC_FULL;
                           break;
                        default:
                           style = TimeZoneFormat.Style.ISO_EXTENDED_FULL;
                     }

                     TimeZone tz = this.tzFormat().parse(style, text, pos, tzTimeType);
                     if (tz != null) {
                        cal.setTimeZone(tz);
                        return pos.getIndex();
                     }

                     return ~start;
                  case 33:
                     TimeZoneFormat.Style style;
                     switch (count) {
                        case 1:
                           style = TimeZoneFormat.Style.ISO_BASIC_LOCAL_SHORT;
                           break;
                        case 2:
                           style = TimeZoneFormat.Style.ISO_BASIC_LOCAL_FIXED;
                           break;
                        case 3:
                           style = TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FIXED;
                           break;
                        case 4:
                           style = TimeZoneFormat.Style.ISO_BASIC_LOCAL_FULL;
                           break;
                        default:
                           style = TimeZoneFormat.Style.ISO_EXTENDED_LOCAL_FULL;
                     }

                     TimeZone tz = this.tzFormat().parse(style, text, pos, tzTimeType);
                     if (tz != null) {
                        cal.setTimeZone(tz);
                        return pos.getIndex();
                     }

                     return ~start;
                  case 35:
                     int ampmStart = this.subParse(
                        text, start, 'a', count, obeyCount, allowNegative, ambiguousYear, cal, numericLeapMonthFormatter, tzTimeType, dayPeriod
                     );
                     if (ampmStart > 0) {
                        return ampmStart;
                     }

                     int newStartx = 0;
                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 3)
                        && (newStartx = this.matchDayPeriodString(text, start, this.formatData.abbreviatedDayPeriods, 2, dayPeriod)) > 0) {
                        return newStartx;
                     }

                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 4)
                        && (newStartx = this.matchDayPeriodString(text, start, this.formatData.wideDayPeriods, 2, dayPeriod)) > 0) {
                        return newStartx;
                     }

                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 4)
                        && (newStartx = this.matchDayPeriodString(text, start, this.formatData.narrowDayPeriods, 2, dayPeriod)) > 0) {
                        return newStartx;
                     }

                     return newStartx;
                  case 36:
                     int newStart = 0;
                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 3)
                        && (
                              newStart = this.matchDayPeriodString(
                                 text, start, this.formatData.abbreviatedDayPeriods, this.formatData.abbreviatedDayPeriods.length, dayPeriod
                              )
                           )
                           > 0) {
                        return newStart;
                     }

                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 4)
                        && (newStart = this.matchDayPeriodString(text, start, this.formatData.wideDayPeriods, this.formatData.wideDayPeriods.length, dayPeriod))
                           > 0) {
                        return newStart;
                     }

                     if ((this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_MULTIPLE_PATTERNS_FOR_MATCH) || count == 4)
                        && (
                              newStart = this.matchDayPeriodString(
                                 text, start, this.formatData.narrowDayPeriods, this.formatData.narrowDayPeriods.length, dayPeriod
                              )
                           )
                           > 0) {
                        return newStart;
                     }

                     return newStart;
                  case 37:
                     ArrayList<String> data = new ArrayList<>(3);
                     data.add(this.formatData.getTimeSeparatorString());
                     if (!this.formatData.getTimeSeparatorString().equals(":")) {
                        data.add(":");
                     }

                     if (this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH)
                        && !this.formatData.getTimeSeparatorString().equals(".")) {
                        data.add(".");
                     }

                     return this.matchString(text, start, -1, data.toArray(new String[0]), cal);
               }
            }

            start += UTF16.getCharCount(c);
         }

         return ~start;
      }
   }

   private boolean allowNumericFallback(int patternCharIndex) {
      return patternCharIndex == 26
         || patternCharIndex == 19
         || patternCharIndex == 25
         || patternCharIndex == 30
         || patternCharIndex == 27
         || patternCharIndex == 28;
   }

   private Number parseInt(String text, ParsePosition pos, boolean allowNegative, NumberFormat fmt) {
      return this.parseInt(text, -1, pos, allowNegative, fmt);
   }

   private Number parseInt(String text, int maxDigits, ParsePosition pos, boolean allowNegative, NumberFormat fmt) {
      int oldPos = pos.getIndex();
      Number number;
      if (allowNegative) {
         number = fmt.parse(text, pos);
      } else if (fmt instanceof DecimalFormat) {
         String oldPrefix = ((DecimalFormat)fmt).getNegativePrefix();
         ((DecimalFormat)fmt).setNegativePrefix("\uab00");
         number = fmt.parse(text, pos);
         ((DecimalFormat)fmt).setNegativePrefix(oldPrefix);
      } else {
         boolean dateNumberFormat = fmt instanceof DateNumberFormat;
         if (dateNumberFormat) {
            ((DateNumberFormat)fmt).setParsePositiveOnly(true);
         }

         number = fmt.parse(text, pos);
         if (dateNumberFormat) {
            ((DateNumberFormat)fmt).setParsePositiveOnly(false);
         }
      }

      if (maxDigits > 0) {
         int nDigits = pos.getIndex() - oldPos;
         if (nDigits > maxDigits) {
            double val = number.doubleValue();

            for (int var13 = nDigits - maxDigits; var13 > 0; var13--) {
               val /= 10.0;
            }

            pos.setIndex(oldPos + maxDigits);
            number = (int)val;
         }
      }

      return number;
   }

   private static int countDigits(String text, int start, int end) {
      int numDigits = 0;
      int idx = start;

      while (idx < end) {
         int cp = text.codePointAt(idx);
         if (UCharacter.isDigit(cp)) {
            numDigits++;
         }

         idx += UCharacter.charCount(cp);
      }

      return numDigits;
   }

   private String translatePattern(String pat, String from, String to) {
      StringBuilder result = new StringBuilder();
      boolean inQuote = false;

      for (int i = 0; i < pat.length(); i++) {
         char c = pat.charAt(i);
         if (inQuote) {
            if (c == '\'') {
               inQuote = false;
            }
         } else if (c == '\'') {
            inQuote = true;
         } else if (isSyntaxChar(c)) {
            int ci = from.indexOf(c);
            if (ci != -1) {
               c = to.charAt(ci);
            }
         }

         result.append(c);
      }

      if (inQuote) {
         throw new IllegalArgumentException("Unfinished quote in pattern");
      } else {
         return result.toString();
      }
   }

   public String toPattern() {
      return this.pattern;
   }

   public String toLocalizedPattern() {
      return this.translatePattern(this.pattern, "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB", this.formatData.localPatternChars);
   }

   public void applyPattern(String pat) {
      this.pattern = pat;
      this.parsePattern();
      this.setLocale(null, null);
      this.patternItems = null;
      if (this.calendar != null && this.calendar.getType().equals("japanese") && this.locale != null && this.locale.getLanguage().equals("ja")) {
         if (this.override != null && this.override.equals("y=jpanyear") && !this.hasHanYearChar) {
            this.numberFormatters = null;
            this.overrideMap = null;
            this.override = null;
         } else if (this.override == null && this.hasHanYearChar) {
            this.numberFormatters = new HashMap<>();
            this.overrideMap = new HashMap<>();
            this.overrideMap.put('y', "jpanyear");
            ULocale ovrLoc = new ULocale(this.locale.getBaseName() + "@numbers=jpanyear");
            NumberFormat nf = NumberFormat.createInstance(ovrLoc, 0);
            nf.setGroupingUsed(false);
            this.useLocalZeroPaddingNumberFormat = false;
            this.numberFormatters.put("jpanyear", nf);
            this.override = "y=jpanyear";
         }
      }
   }

   public void applyLocalizedPattern(String pat) {
      this.pattern = this.translatePattern(pat, this.formatData.localPatternChars, "GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB");
      this.setLocale(null, null);
   }

   public DateFormatSymbols getDateFormatSymbols() {
      return (DateFormatSymbols)this.formatData.clone();
   }

   public void setDateFormatSymbols(DateFormatSymbols newFormatSymbols) {
      this.formatData = (DateFormatSymbols)newFormatSymbols.clone();
   }

   protected DateFormatSymbols getSymbols() {
      return this.formatData;
   }

   public TimeZoneFormat getTimeZoneFormat() {
      return this.tzFormat().freeze();
   }

   public void setTimeZoneFormat(TimeZoneFormat tzfmt) {
      if (tzfmt.isFrozen()) {
         this.tzFormat = tzfmt;
      } else {
         this.tzFormat = tzfmt.cloneAsThawed().freeze();
      }
   }

   @Override
   public Object clone() {
      SimpleDateFormat other = (SimpleDateFormat)super.clone();
      other.formatData = (DateFormatSymbols)this.formatData.clone();
      if (this.decimalBuf != null) {
         other.decimalBuf = new char[10];
      }

      return other;
   }

   @Override
   public int hashCode() {
      return this.pattern.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if (!super.equals(obj)) {
         return false;
      } else {
         SimpleDateFormat that = (SimpleDateFormat)obj;
         return this.pattern.equals(that.pattern) && this.formatData.equals(that.formatData);
      }
   }

   private void writeObject(ObjectOutputStream stream) throws IOException {
      if (this.defaultCenturyStart == null) {
         this.initializeDefaultCenturyStart(this.defaultCenturyBase);
      }

      this.initializeTimeZoneFormat(false);
      stream.defaultWriteObject();
      stream.writeInt(this.getContext(DisplayContext.Type.CAPITALIZATION).value());
   }

   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      int capitalizationSettingValue = this.serialVersionOnStream > 1 ? stream.readInt() : -1;
      if (this.serialVersionOnStream < 1) {
         this.defaultCenturyBase = System.currentTimeMillis();
      } else {
         this.parseAmbiguousDatesAsAfter(this.defaultCenturyStart);
      }

      this.serialVersionOnStream = 2;
      this.locale = this.getLocale(ULocale.VALID_LOCALE);
      if (this.locale == null) {
         this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
      }

      this.initLocalZeroPaddingNumberFormat();
      this.setContext(DisplayContext.CAPITALIZATION_NONE);
      if (capitalizationSettingValue >= 0) {
         for (DisplayContext context : DisplayContext.values()) {
            if (context.value() == capitalizationSettingValue) {
               this.setContext(context);
               break;
            }
         }
      }

      if (!this.getBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_MATCH)) {
         this.setBooleanAttribute(DateFormat.BooleanAttribute.PARSE_PARTIAL_LITERAL_MATCH, false);
      }

      this.parsePattern();
   }

   @Override
   public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
      Calendar cal = this.calendar;
      if (obj instanceof Calendar) {
         cal = (Calendar)obj;
      } else if (obj instanceof Date) {
         this.calendar.setTime((Date)obj);
      } else {
         if (!(obj instanceof Number)) {
            throw new IllegalArgumentException("Cannot format given Object as a Date");
         }

         this.calendar.setTimeInMillis(((Number)obj).longValue());
      }

      StringBuffer toAppendTo = new StringBuffer();
      FieldPosition pos = new FieldPosition(0);
      List<FieldPosition> attributes = new ArrayList<>();
      this.format(cal, this.getContext(DisplayContext.Type.CAPITALIZATION), toAppendTo, pos, attributes);
      AttributedString as = new AttributedString(toAppendTo.toString());

      for (int i = 0; i < attributes.size(); i++) {
         FieldPosition fp = attributes.get(i);
         Field attribute = fp.getFieldAttribute();
         as.addAttribute(attribute, attribute, fp.getBeginIndex(), fp.getEndIndex());
      }

      return as.getIterator();
   }

   ULocale getLocale() {
      return this.locale;
   }

   boolean isFieldUnitIgnored(int field) {
      return isFieldUnitIgnored(this.pattern, field);
   }

   static boolean isFieldUnitIgnored(String pattern, int field) {
      int fieldLevel = CALENDAR_FIELD_TO_LEVEL[field];
      boolean inQuote = false;
      char prevCh = 0;
      int count = 0;

      for (int i = 0; i < pattern.length(); i++) {
         char ch = pattern.charAt(i);
         if (ch != prevCh && count > 0) {
            int level = getLevelFromChar(prevCh);
            if (fieldLevel <= level) {
               return false;
            }

            count = 0;
         }

         if (ch == '\'') {
            if (i + 1 < pattern.length() && pattern.charAt(i + 1) == '\'') {
               i++;
            } else {
               inQuote = !inQuote;
            }
         } else if (!inQuote && isSyntaxChar(ch)) {
            prevCh = ch;
            count++;
         }
      }

      if (count > 0) {
         int level = getLevelFromChar(prevCh);
         if (fieldLevel <= level) {
            return false;
         }
      }

      return true;
   }

   @Deprecated
   public final StringBuffer intervalFormatByAlgorithm(Calendar fromCalendar, Calendar toCalendar, StringBuffer appendTo, FieldPosition pos) throws IllegalArgumentException {
      if (!fromCalendar.isEquivalentTo(toCalendar)) {
         throw new IllegalArgumentException("can not format on two different calendars");
      } else {
         Object[] items = this.getPatternItems();
         int diffBegin = -1;
         int diffEnd = -1;

         try {
            for (int i = 0; i < items.length; i++) {
               if (this.diffCalFieldValue(fromCalendar, toCalendar, items, i)) {
                  diffBegin = i;
                  break;
               }
            }

            if (diffBegin == -1) {
               return this.format(fromCalendar, appendTo, pos);
            }

            for (int ix = items.length - 1; ix >= diffBegin; ix--) {
               if (this.diffCalFieldValue(fromCalendar, toCalendar, items, ix)) {
                  diffEnd = ix;
                  break;
               }
            }
         } catch (IllegalArgumentException var14) {
            throw new IllegalArgumentException(var14.toString());
         }

         if (diffBegin == 0 && diffEnd == items.length - 1) {
            this.format(fromCalendar, appendTo, pos);
            appendTo.append(" – ");
            this.format(toCalendar, appendTo, pos);
            return appendTo;
         } else {
            int highestLevel = 1000;

            for (int ixx = diffBegin; ixx <= diffEnd; ixx++) {
               if (!(items[ixx] instanceof String)) {
                  SimpleDateFormat.PatternItem item = (SimpleDateFormat.PatternItem)items[ixx];
                  char ch = item.type;
                  int patternCharIndex = getIndexFromChar(ch);
                  if (patternCharIndex == -1) {
                     throw new IllegalArgumentException("Illegal pattern character '" + ch + "' in \"" + this.pattern + '"');
                  }

                  if (patternCharIndex < highestLevel) {
                     highestLevel = patternCharIndex;
                  }
               }
            }

            try {
               for (int ixxx = 0; ixxx < diffBegin; ixxx++) {
                  if (this.lowerLevel(items, ixxx, highestLevel)) {
                     diffBegin = ixxx;
                     break;
                  }
               }

               for (int ixxxx = items.length - 1; ixxxx > diffEnd; ixxxx--) {
                  if (this.lowerLevel(items, ixxxx, highestLevel)) {
                     diffEnd = ixxxx;
                     break;
                  }
               }
            } catch (IllegalArgumentException var13) {
               throw new IllegalArgumentException(var13.toString());
            }

            if (diffBegin == 0 && diffEnd == items.length - 1) {
               this.format(fromCalendar, appendTo, pos);
               appendTo.append(" – ");
               this.format(toCalendar, appendTo, pos);
               return appendTo;
            } else {
               pos.setBeginIndex(0);
               pos.setEndIndex(0);
               DisplayContext capSetting = this.getContext(DisplayContext.Type.CAPITALIZATION);

               for (int ixxxxx = 0; ixxxxx <= diffEnd; ixxxxx++) {
                  if (items[ixxxxx] instanceof String) {
                     appendTo.append((String)items[ixxxxx]);
                  } else {
                     SimpleDateFormat.PatternItem itemx = (SimpleDateFormat.PatternItem)items[ixxxxx];
                     if (this.useFastFormat) {
                        this.subFormat(appendTo, itemx.type, itemx.length, appendTo.length(), ixxxxx, capSetting, pos, itemx.type, fromCalendar);
                     } else {
                        appendTo.append(this.subFormat(itemx.type, itemx.length, appendTo.length(), ixxxxx, capSetting, pos, itemx.type, fromCalendar));
                     }
                  }
               }

               appendTo.append(" – ");

               for (int ixxxxxx = diffBegin; ixxxxxx < items.length; ixxxxxx++) {
                  if (items[ixxxxxx] instanceof String) {
                     appendTo.append((String)items[ixxxxxx]);
                  } else {
                     SimpleDateFormat.PatternItem itemx = (SimpleDateFormat.PatternItem)items[ixxxxxx];
                     if (this.useFastFormat) {
                        this.subFormat(appendTo, itemx.type, itemx.length, appendTo.length(), ixxxxxx, capSetting, pos, itemx.type, toCalendar);
                     } else {
                        appendTo.append(this.subFormat(itemx.type, itemx.length, appendTo.length(), ixxxxxx, capSetting, pos, itemx.type, toCalendar));
                     }
                  }
               }

               return appendTo;
            }
         }
      }
   }

   private boolean diffCalFieldValue(Calendar fromCalendar, Calendar toCalendar, Object[] items, int i) throws IllegalArgumentException {
      if (items[i] instanceof String) {
         return false;
      } else {
         SimpleDateFormat.PatternItem item = (SimpleDateFormat.PatternItem)items[i];
         char ch = item.type;
         int patternCharIndex = getIndexFromChar(ch);
         if (patternCharIndex == -1) {
            throw new IllegalArgumentException("Illegal pattern character '" + ch + "' in \"" + this.pattern + '"');
         } else {
            int field = PATTERN_INDEX_TO_CALENDAR_FIELD[patternCharIndex];
            if (field >= 0) {
               int value = fromCalendar.get(field);
               int value_2 = toCalendar.get(field);
               if (value != value_2) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private boolean lowerLevel(Object[] items, int i, int level) throws IllegalArgumentException {
      if (items[i] instanceof String) {
         return false;
      } else {
         SimpleDateFormat.PatternItem item = (SimpleDateFormat.PatternItem)items[i];
         char ch = item.type;
         int patternCharIndex = getLevelFromChar(ch);
         if (patternCharIndex == -1) {
            throw new IllegalArgumentException("Illegal pattern character '" + ch + "' in \"" + this.pattern + '"');
         } else {
            return patternCharIndex >= level;
         }
      }
   }

   public void setNumberFormat(String fields, NumberFormat overrideNF) {
      overrideNF.setGroupingUsed(false);
      String nsName = "$" + UUID.randomUUID().toString();
      if (this.numberFormatters == null) {
         this.numberFormatters = new HashMap<>();
      }

      if (this.overrideMap == null) {
         this.overrideMap = new HashMap<>();
      }

      for (int i = 0; i < fields.length(); i++) {
         char field = fields.charAt(i);
         if ("GyMdkHmsSEDFwWahKzYeugAZvcLQqVUOXxrbB".indexOf(field) == -1) {
            throw new IllegalArgumentException("Illegal field character '" + field + "' in setNumberFormat.");
         }

         this.overrideMap.put(field, nsName);
         this.numberFormatters.put(nsName, overrideNF);
      }

      this.useLocalZeroPaddingNumberFormat = false;
   }

   public NumberFormat getNumberFormat(char field) {
      Character ovrField = field;
      if (this.overrideMap != null && this.overrideMap.containsKey(ovrField)) {
         String nsName = this.overrideMap.get(ovrField).toString();
         return this.numberFormatters.get(nsName);
      } else {
         return this.numberFormat;
      }
   }

   private void initNumberFormatters(ULocale loc) {
      this.numberFormatters = new HashMap<>();
      this.overrideMap = new HashMap<>();
      this.processOverrideString(loc, this.override);
   }

   private void processOverrideString(ULocale loc, String str) {
      if (str != null && str.length() != 0) {
         int start = 0;
         boolean moreToProcess = true;

         while (moreToProcess) {
            int delimiterPosition = str.indexOf(";", start);
            int end;
            if (delimiterPosition == -1) {
               moreToProcess = false;
               end = str.length();
            } else {
               end = delimiterPosition;
            }

            String currentString = str.substring(start, end);
            int equalSignPosition = currentString.indexOf("=");
            String nsName;
            boolean fullOverride;
            if (equalSignPosition == -1) {
               nsName = currentString;
               fullOverride = true;
            } else {
               nsName = currentString.substring(equalSignPosition + 1);
               Character ovrField = currentString.charAt(0);
               this.overrideMap.put(ovrField, nsName);
               fullOverride = false;
            }

            ULocale ovrLoc = new ULocale(loc.getBaseName() + "@numbers=" + nsName);
            NumberFormat nf = NumberFormat.createInstance(ovrLoc, 0);
            nf.setGroupingUsed(false);
            if (fullOverride) {
               this.setNumberFormat(nf);
            } else {
               this.useLocalZeroPaddingNumberFormat = false;
            }

            if (!fullOverride && !this.numberFormatters.containsKey(nsName)) {
               this.numberFormatters.put(nsName, nf);
            }

            start = delimiterPosition + 1;
         }
      }
   }

   private void parsePattern() {
      this.hasMinute = false;
      this.hasSecond = false;
      this.hasHanYearChar = false;
      boolean inQuote = false;

      for (int i = 0; i < this.pattern.length(); i++) {
         char ch = this.pattern.charAt(i);
         if (ch == '\'') {
            inQuote = !inQuote;
         }

         if (ch == 24180) {
            this.hasHanYearChar = true;
         }

         if (!inQuote) {
            if (ch == 'm') {
               this.hasMinute = true;
            }

            if (ch == 's') {
               this.hasSecond = true;
            }
         }
      }
   }

   private static enum ContextValue {
      UNKNOWN,
      CAPITALIZATION_FOR_MIDDLE_OF_SENTENCE,
      CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE,
      CAPITALIZATION_FOR_UI_LIST_OR_MENU,
      CAPITALIZATION_FOR_STANDALONE;
   }

   private static class PatternItem {
      final char type;
      final int length;
      final boolean isNumeric;

      PatternItem(char type, int length) {
         this.type = type;
         this.length = length;
         this.isNumeric = SimpleDateFormat.isNumeric(type, length);
      }
   }
}
