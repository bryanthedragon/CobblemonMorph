package com.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.CacheBase;
import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedStringBuilder;
import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedValueStringBuilderImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.SimpleFormatterImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.SoftCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.StandardPlural;
import com.cobblemon.mod.relocations.ibm.icu.impl.UResource;
import com.cobblemon.mod.relocations.ibm.icu.impl.Utility;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import com.cobblemon.mod.relocations.ibm.icu.util.ICUException;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.cobblemon.mod.relocations.ibm.icu.util.UResourceBundle;
import java.io.InvalidObjectException;
import java.text.AttributedCharacterIterator;
import java.text.Format;
import java.util.EnumMap;
import java.util.Locale;

public final class RelativeDateTimeFormatter {
   private int[] styleToDateFormatSymbolsWidth = new int[]{1, 3, 2};
   private final EnumMap<RelativeDateTimeFormatter.Style, EnumMap<RelativeDateTimeFormatter.AbsoluteUnit, EnumMap<RelativeDateTimeFormatter.Direction, String>>> qualitativeUnitMap;
   private final EnumMap<RelativeDateTimeFormatter.Style, EnumMap<RelativeDateTimeFormatter.RelativeUnit, String[][]>> patternMap;
   private final String combinedDateAndTime;
   private final PluralRules pluralRules;
   private final NumberFormat numberFormat;
   private final RelativeDateTimeFormatter.Style style;
   private final DisplayContext capitalizationContext;
   private final BreakIterator breakIterator;
   private final ULocale locale;
   private final DateFormatSymbols dateFormatSymbols;
   private static final RelativeDateTimeFormatter.Style[] fallbackCache = new RelativeDateTimeFormatter.Style[3];
   private static final RelativeDateTimeFormatter.Cache cache = new RelativeDateTimeFormatter.Cache();

   public static RelativeDateTimeFormatter getInstance() {
      return getInstance(ULocale.getDefault(), null, RelativeDateTimeFormatter.Style.LONG, DisplayContext.CAPITALIZATION_NONE);
   }

   public static RelativeDateTimeFormatter getInstance(ULocale locale) {
      return getInstance(locale, null, RelativeDateTimeFormatter.Style.LONG, DisplayContext.CAPITALIZATION_NONE);
   }

   public static RelativeDateTimeFormatter getInstance(Locale locale) {
      return getInstance(ULocale.forLocale(locale));
   }

   public static RelativeDateTimeFormatter getInstance(ULocale locale, NumberFormat nf) {
      return getInstance(locale, nf, RelativeDateTimeFormatter.Style.LONG, DisplayContext.CAPITALIZATION_NONE);
   }

   public static RelativeDateTimeFormatter getInstance(
      ULocale locale, NumberFormat nf, RelativeDateTimeFormatter.Style style, DisplayContext capitalizationContext
   ) {
      RelativeDateTimeFormatter.RelativeDateTimeFormatterData data = cache.get(locale);
      if (nf == null) {
         nf = NumberFormat.getInstance(locale);
      } else {
         nf = (NumberFormat)nf.clone();
      }

      return new RelativeDateTimeFormatter(
         data.qualitativeUnitMap,
         data.relUnitPatternMap,
         SimpleFormatterImpl.compileToStringMinMaxArguments(data.dateTimePattern, new StringBuilder(), 2, 2),
         PluralRules.forLocale(locale),
         nf,
         style,
         capitalizationContext,
         capitalizationContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE ? BreakIterator.getSentenceInstance(locale) : null,
         locale
      );
   }

   public static RelativeDateTimeFormatter getInstance(Locale locale, NumberFormat nf) {
      return getInstance(ULocale.forLocale(locale), nf);
   }

   public String format(double quantity, RelativeDateTimeFormatter.Direction direction, RelativeDateTimeFormatter.RelativeUnit unit) {
      FormattedStringBuilder output = this.formatImpl(quantity, direction, unit);
      return this.adjustForContext(output.toString());
   }

   public RelativeDateTimeFormatter.FormattedRelativeDateTime formatToValue(
      double quantity, RelativeDateTimeFormatter.Direction direction, RelativeDateTimeFormatter.RelativeUnit unit
   ) {
      this.checkNoAdjustForContext();
      return new RelativeDateTimeFormatter.FormattedRelativeDateTime(this.formatImpl(quantity, direction, unit));
   }

   private FormattedStringBuilder formatImpl(double quantity, RelativeDateTimeFormatter.Direction direction, RelativeDateTimeFormatter.RelativeUnit unit) {
      if (direction != RelativeDateTimeFormatter.Direction.LAST && direction != RelativeDateTimeFormatter.Direction.NEXT) {
         throw new IllegalArgumentException("direction must be NEXT or LAST");
      } else {
         int pastFutureIndex = direction == RelativeDateTimeFormatter.Direction.NEXT ? 1 : 0;
         FormattedStringBuilder output = new FormattedStringBuilder();
         String pluralKeyword;
         if (this.numberFormat instanceof DecimalFormat) {
            DecimalQuantity dq = new DecimalQuantity_DualStorageBCD(quantity);
            ((DecimalFormat)this.numberFormat).toNumberFormatter().formatImpl(dq, output);
            pluralKeyword = this.pluralRules.select(dq);
         } else {
            String result = this.numberFormat.format(quantity);
            output.append(result, null);
            pluralKeyword = this.pluralRules.select(quantity);
         }

         StandardPlural pluralForm = StandardPlural.orOtherFromString(pluralKeyword);
         String compiledPattern = this.getRelativeUnitPluralPattern(this.style, unit, pastFutureIndex, pluralForm);
         SimpleFormatterImpl.formatPrefixSuffix(compiledPattern, RelativeDateTimeFormatter.Field.LITERAL, 0, output.length(), output);
         return output;
      }
   }

   public String formatNumeric(double offset, RelativeDateTimeFormatter.RelativeDateTimeUnit unit) {
      FormattedStringBuilder output = this.formatNumericImpl(offset, unit);
      return this.adjustForContext(output.toString());
   }

   public RelativeDateTimeFormatter.FormattedRelativeDateTime formatNumericToValue(double offset, RelativeDateTimeFormatter.RelativeDateTimeUnit unit) {
      this.checkNoAdjustForContext();
      return new RelativeDateTimeFormatter.FormattedRelativeDateTime(this.formatNumericImpl(offset, unit));
   }

   private FormattedStringBuilder formatNumericImpl(double offset, RelativeDateTimeFormatter.RelativeDateTimeUnit unit) {
      RelativeDateTimeFormatter.RelativeUnit relunit = RelativeDateTimeFormatter.RelativeUnit.SECONDS;
      switch (unit) {
         case YEAR:
            relunit = RelativeDateTimeFormatter.RelativeUnit.YEARS;
            break;
         case QUARTER:
            relunit = RelativeDateTimeFormatter.RelativeUnit.QUARTERS;
            break;
         case MONTH:
            relunit = RelativeDateTimeFormatter.RelativeUnit.MONTHS;
            break;
         case WEEK:
            relunit = RelativeDateTimeFormatter.RelativeUnit.WEEKS;
            break;
         case DAY:
            relunit = RelativeDateTimeFormatter.RelativeUnit.DAYS;
            break;
         case HOUR:
            relunit = RelativeDateTimeFormatter.RelativeUnit.HOURS;
            break;
         case MINUTE:
            relunit = RelativeDateTimeFormatter.RelativeUnit.MINUTES;
         case SECOND:
            break;
         default:
            throw new UnsupportedOperationException("formatNumeric does not currently support RelativeUnit.SUNDAY..SATURDAY");
      }

      RelativeDateTimeFormatter.Direction direction = RelativeDateTimeFormatter.Direction.NEXT;
      if (Double.compare(offset, 0.0) < 0) {
         direction = RelativeDateTimeFormatter.Direction.LAST;
         offset = -offset;
      }

      return this.formatImpl(offset, direction, relunit);
   }

   public String format(RelativeDateTimeFormatter.Direction direction, RelativeDateTimeFormatter.AbsoluteUnit unit) {
      String result = this.formatAbsoluteImpl(direction, unit);
      return result != null ? this.adjustForContext(result) : null;
   }

   public RelativeDateTimeFormatter.FormattedRelativeDateTime formatToValue(
      RelativeDateTimeFormatter.Direction direction, RelativeDateTimeFormatter.AbsoluteUnit unit
   ) {
      this.checkNoAdjustForContext();
      String string = this.formatAbsoluteImpl(direction, unit);
      if (string == null) {
         return null;
      } else {
         FormattedStringBuilder nsb = new FormattedStringBuilder();
         nsb.append(string, RelativeDateTimeFormatter.Field.LITERAL);
         return new RelativeDateTimeFormatter.FormattedRelativeDateTime(nsb);
      }
   }

   private String formatAbsoluteImpl(RelativeDateTimeFormatter.Direction direction, RelativeDateTimeFormatter.AbsoluteUnit unit) {
      if (unit == RelativeDateTimeFormatter.AbsoluteUnit.NOW && direction != RelativeDateTimeFormatter.Direction.PLAIN) {
         throw new IllegalArgumentException("NOW can only accept direction PLAIN.");
      } else {
         String result;
         if (direction == RelativeDateTimeFormatter.Direction.PLAIN
            && RelativeDateTimeFormatter.AbsoluteUnit.SUNDAY.ordinal() <= unit.ordinal()
            && unit.ordinal() <= RelativeDateTimeFormatter.AbsoluteUnit.SATURDAY.ordinal()) {
            int dateSymbolsDayOrdinal = unit.ordinal() - RelativeDateTimeFormatter.AbsoluteUnit.SUNDAY.ordinal() + 1;
            String[] dayNames = this.dateFormatSymbols.getWeekdays(1, this.styleToDateFormatSymbolsWidth[this.style.ordinal()]);
            result = dayNames[dateSymbolsDayOrdinal];
         } else {
            result = this.getAbsoluteUnitString(this.style, unit, direction);
         }

         return result;
      }
   }

   public String format(double offset, RelativeDateTimeFormatter.RelativeDateTimeUnit unit) {
      return this.adjustForContext(this.formatRelativeImpl(offset, unit).toString());
   }

   public RelativeDateTimeFormatter.FormattedRelativeDateTime formatToValue(double offset, RelativeDateTimeFormatter.RelativeDateTimeUnit unit) {
      this.checkNoAdjustForContext();
      CharSequence cs = this.formatRelativeImpl(offset, unit);
      FormattedStringBuilder nsb;
      if (cs instanceof FormattedStringBuilder) {
         nsb = (FormattedStringBuilder)cs;
      } else {
         nsb = new FormattedStringBuilder();
         nsb.append(cs, RelativeDateTimeFormatter.Field.LITERAL);
      }

      return new RelativeDateTimeFormatter.FormattedRelativeDateTime(nsb);
   }

   private CharSequence formatRelativeImpl(double offset, RelativeDateTimeFormatter.RelativeDateTimeUnit unit) {
      boolean useNumeric = true;
      RelativeDateTimeFormatter.Direction direction = RelativeDateTimeFormatter.Direction.THIS;
      if (offset > -2.1 && offset < 2.1) {
         double offsetx100 = offset * 100.0;
         int intoffsetx100 = offsetx100 < 0.0 ? (int)(offsetx100 - 0.5) : (int)(offsetx100 + 0.5);
         switch (intoffsetx100) {
            case -200:
               direction = RelativeDateTimeFormatter.Direction.LAST_2;
               useNumeric = false;
               break;
            case -100:
               direction = RelativeDateTimeFormatter.Direction.LAST;
               useNumeric = false;
               break;
            case 0:
               useNumeric = false;
               break;
            case 100:
               direction = RelativeDateTimeFormatter.Direction.NEXT;
               useNumeric = false;
               break;
            case 200:
               direction = RelativeDateTimeFormatter.Direction.NEXT_2;
               useNumeric = false;
         }
      }

      RelativeDateTimeFormatter.AbsoluteUnit absunit = RelativeDateTimeFormatter.AbsoluteUnit.NOW;
      switch (unit) {
         case YEAR:
            absunit = RelativeDateTimeFormatter.AbsoluteUnit.YEAR;
            break;
         case QUARTER:
            absunit = RelativeDateTimeFormatter.AbsoluteUnit.QUARTER;
            break;
         case MONTH:
            absunit = RelativeDateTimeFormatter.AbsoluteUnit.MONTH;
            break;
         case WEEK:
            absunit = RelativeDateTimeFormatter.AbsoluteUnit.WEEK;
            break;
         case DAY:
            absunit = RelativeDateTimeFormatter.AbsoluteUnit.DAY;
            break;
         case HOUR:
            absunit = RelativeDateTimeFormatter.AbsoluteUnit.HOUR;
            break;
         case MINUTE:
            absunit = RelativeDateTimeFormatter.AbsoluteUnit.MINUTE;
            break;
         case SECOND:
            if (direction == RelativeDateTimeFormatter.Direction.THIS) {
               direction = RelativeDateTimeFormatter.Direction.PLAIN;
            } else {
               useNumeric = true;
            }
            break;
         case SUNDAY:
            absunit = RelativeDateTimeFormatter.AbsoluteUnit.SUNDAY;
            break;
         case MONDAY:
            absunit = RelativeDateTimeFormatter.AbsoluteUnit.MONDAY;
            break;
         case TUESDAY:
            absunit = RelativeDateTimeFormatter.AbsoluteUnit.TUESDAY;
            break;
         case WEDNESDAY:
            absunit = RelativeDateTimeFormatter.AbsoluteUnit.WEDNESDAY;
            break;
         case THURSDAY:
            absunit = RelativeDateTimeFormatter.AbsoluteUnit.THURSDAY;
            break;
         case FRIDAY:
            absunit = RelativeDateTimeFormatter.AbsoluteUnit.FRIDAY;
            break;
         case SATURDAY:
            absunit = RelativeDateTimeFormatter.AbsoluteUnit.SATURDAY;
            break;
         default:
            useNumeric = true;
      }

      if (!useNumeric) {
         String result = this.formatAbsoluteImpl(direction, absunit);
         if (result != null && result.length() > 0) {
            return result;
         }
      }

      return this.formatNumericImpl(offset, unit);
   }

   private String getAbsoluteUnitString(
      RelativeDateTimeFormatter.Style style, RelativeDateTimeFormatter.AbsoluteUnit unit, RelativeDateTimeFormatter.Direction direction
   ) {
      do {
         EnumMap<RelativeDateTimeFormatter.AbsoluteUnit, EnumMap<RelativeDateTimeFormatter.Direction, String>> unitMap = this.qualitativeUnitMap.get(style);
         if (unitMap != null) {
            EnumMap<RelativeDateTimeFormatter.Direction, String> dirMap = unitMap.get(unit);
            if (dirMap != null) {
               String result = dirMap.get(direction);
               if (result != null) {
                  return result;
               }
            }
         }
      } while ((style = fallbackCache[style.ordinal()]) != null);

      return null;
   }

   public String combineDateAndTime(String relativeDateString, String timeString) {
      return SimpleFormatterImpl.formatCompiledPattern(this.combinedDateAndTime, timeString, relativeDateString);
   }

   public NumberFormat getNumberFormat() {
      synchronized (this.numberFormat) {
         return (NumberFormat)this.numberFormat.clone();
      }
   }

   public DisplayContext getCapitalizationContext() {
      return this.capitalizationContext;
   }

   public RelativeDateTimeFormatter.Style getFormatStyle() {
      return this.style;
   }

   private String adjustForContext(String originalFormattedString) {
      if (this.breakIterator != null && originalFormattedString.length() != 0 && UCharacter.isLowerCase(UCharacter.codePointAt(originalFormattedString, 0))) {
         synchronized (this.breakIterator) {
            return UCharacter.toTitleCase(this.locale, originalFormattedString, this.breakIterator, 768);
         }
      } else {
         return originalFormattedString;
      }
   }

   private void checkNoAdjustForContext() {
      if (this.breakIterator != null) {
         throw new UnsupportedOperationException("Capitalization context is not supported in formatV");
      }
   }

   private RelativeDateTimeFormatter(
      EnumMap<RelativeDateTimeFormatter.Style, EnumMap<RelativeDateTimeFormatter.AbsoluteUnit, EnumMap<RelativeDateTimeFormatter.Direction, String>>> qualitativeUnitMap,
      EnumMap<RelativeDateTimeFormatter.Style, EnumMap<RelativeDateTimeFormatter.RelativeUnit, String[][]>> patternMap,
      String combinedDateAndTime,
      PluralRules pluralRules,
      NumberFormat numberFormat,
      RelativeDateTimeFormatter.Style style,
      DisplayContext capitalizationContext,
      BreakIterator breakIterator,
      ULocale locale
   ) {
      this.qualitativeUnitMap = qualitativeUnitMap;
      this.patternMap = patternMap;
      this.combinedDateAndTime = combinedDateAndTime;
      this.pluralRules = pluralRules;
      this.numberFormat = numberFormat;
      this.style = style;
      if (capitalizationContext.type() != DisplayContext.Type.CAPITALIZATION) {
         throw new IllegalArgumentException(capitalizationContext.toString());
      } else {
         this.capitalizationContext = capitalizationContext;
         this.breakIterator = breakIterator;
         this.locale = locale;
         this.dateFormatSymbols = new DateFormatSymbols(locale);
      }
   }

   private String getRelativeUnitPluralPattern(
      RelativeDateTimeFormatter.Style style, RelativeDateTimeFormatter.RelativeUnit unit, int pastFutureIndex, StandardPlural pluralForm
   ) {
      if (pluralForm != StandardPlural.OTHER) {
         String formatter = this.getRelativeUnitPattern(style, unit, pastFutureIndex, pluralForm);
         if (formatter != null) {
            return formatter;
         }
      }

      return this.getRelativeUnitPattern(style, unit, pastFutureIndex, StandardPlural.OTHER);
   }

   private String getRelativeUnitPattern(
      RelativeDateTimeFormatter.Style style, RelativeDateTimeFormatter.RelativeUnit unit, int pastFutureIndex, StandardPlural pluralForm
   ) {
      int pluralIndex = pluralForm.ordinal();

      do {
         EnumMap<RelativeDateTimeFormatter.RelativeUnit, String[][]> unitMap = this.patternMap.get(style);
         if (unitMap != null) {
            String[][] spfCompiledPatterns = unitMap.get(unit);
            if (spfCompiledPatterns != null && spfCompiledPatterns[pastFutureIndex][pluralIndex] != null) {
               return spfCompiledPatterns[pastFutureIndex][pluralIndex];
            }
         }
      } while ((style = fallbackCache[style.ordinal()]) != null);

      return null;
   }

   private static RelativeDateTimeFormatter.Direction keyToDirection(UResource.Key key) {
      if (key.contentEquals("-2")) {
         return RelativeDateTimeFormatter.Direction.LAST_2;
      } else if (key.contentEquals("-1")) {
         return RelativeDateTimeFormatter.Direction.LAST;
      } else if (key.contentEquals("0")) {
         return RelativeDateTimeFormatter.Direction.THIS;
      } else if (key.contentEquals("1")) {
         return RelativeDateTimeFormatter.Direction.NEXT;
      } else {
         return key.contentEquals("2") ? RelativeDateTimeFormatter.Direction.NEXT_2 : null;
      }
   }

   public static enum AbsoluteUnit {
      SUNDAY,
      MONDAY,
      TUESDAY,
      WEDNESDAY,
      THURSDAY,
      FRIDAY,
      SATURDAY,
      DAY,
      WEEK,
      MONTH,
      YEAR,
      NOW,
      QUARTER,
      HOUR,
      MINUTE;
   }

   private static class Cache {
      private final CacheBase<String, RelativeDateTimeFormatter.RelativeDateTimeFormatterData, ULocale> cache = new SoftCache<String, RelativeDateTimeFormatter.RelativeDateTimeFormatterData, ULocale>(
         
      ) {
         protected RelativeDateTimeFormatter.RelativeDateTimeFormatterData createInstance(String key, ULocale locale) {
            return new RelativeDateTimeFormatter.Loader(locale).load();
         }
      };

      private Cache() {
      }

      public RelativeDateTimeFormatter.RelativeDateTimeFormatterData get(ULocale locale) {
         String key = locale.toString();
         return this.cache.getInstance(key, locale);
      }
   }

   public static enum Direction {
      LAST_2,
      LAST,
      THIS,
      NEXT,
      NEXT_2,
      PLAIN;
   }

   public static class Field extends Format.Field {
      private static final long serialVersionUID = -5327685528663492325L;
      public static final RelativeDateTimeFormatter.Field LITERAL = new RelativeDateTimeFormatter.Field("literal");
      public static final RelativeDateTimeFormatter.Field NUMERIC = new RelativeDateTimeFormatter.Field("numeric");

      private Field(String fieldName) {
         super(fieldName);
      }

      @Deprecated
      @Override
      protected Object readResolve() throws InvalidObjectException {
         if (this.getName().equals(LITERAL.getName())) {
            return LITERAL;
         } else if (this.getName().equals(NUMERIC.getName())) {
            return NUMERIC;
         } else {
            throw new InvalidObjectException("An invalid object.");
         }
      }
   }

   public static class FormattedRelativeDateTime implements FormattedValue {
      private final FormattedStringBuilder string;

      private FormattedRelativeDateTime(FormattedStringBuilder string) {
         this.string = string;
      }

      @Override
      public String toString() {
         return this.string.toString();
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
         return this.string.subString(start, end);
      }

      @Override
      public <A extends Appendable> A appendTo(A appendable) {
         return Utility.appendTo(this.string, appendable);
      }

      @Override
      public boolean nextPosition(ConstrainedFieldPosition cfpos) {
         return FormattedValueStringBuilderImpl.nextPosition(this.string, cfpos, RelativeDateTimeFormatter.Field.NUMERIC);
      }

      @Override
      public AttributedCharacterIterator toCharacterIterator() {
         return FormattedValueStringBuilderImpl.toCharacterIterator(this.string, RelativeDateTimeFormatter.Field.NUMERIC);
      }
   }

   private static class Loader {
      private final ULocale ulocale;

      public Loader(ULocale ulocale) {
         this.ulocale = ulocale;
      }

      private String getDateTimePattern(ICUResourceBundle r) {
         String calType = r.getStringWithFallback("calendar/default");
         if (calType == null || calType.equals("")) {
            calType = "gregorian";
         }

         String resourcePath = "calendar/" + calType + "/DateTimePatterns";
         ICUResourceBundle patternsRb = r.findWithFallback(resourcePath);
         if (patternsRb == null && calType.equals("gregorian")) {
            patternsRb = r.findWithFallback("calendar/gregorian/DateTimePatterns");
         }

         if (patternsRb != null && patternsRb.getSize() >= 9) {
            int elementType = patternsRb.get(8).getType();
            return elementType == 8 ? patternsRb.get(8).getString(0) : patternsRb.getString(8);
         } else {
            return "{1} {0}";
         }
      }

      public RelativeDateTimeFormatter.RelativeDateTimeFormatterData load() {
         RelativeDateTimeFormatter.RelDateTimeDataSink sink = new RelativeDateTimeFormatter.RelDateTimeDataSink();
         ICUResourceBundle r = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", this.ulocale);
         r.getAllItemsWithFallback("fields", sink);

         for (RelativeDateTimeFormatter.Style testStyle : RelativeDateTimeFormatter.Style.values()) {
            RelativeDateTimeFormatter.Style newStyle1 = RelativeDateTimeFormatter.fallbackCache[testStyle.ordinal()];
            if (newStyle1 != null) {
               RelativeDateTimeFormatter.Style newStyle2 = RelativeDateTimeFormatter.fallbackCache[newStyle1.ordinal()];
               if (newStyle2 != null && RelativeDateTimeFormatter.fallbackCache[newStyle2.ordinal()] != null) {
                  throw new IllegalStateException("Style fallback too deep");
               }
            }
         }

         return new RelativeDateTimeFormatter.RelativeDateTimeFormatterData(sink.qualitativeUnitMap, sink.styleRelUnitPatterns, this.getDateTimePattern(r));
      }
   }

   private static final class RelDateTimeDataSink extends UResource.Sink {
      EnumMap<RelativeDateTimeFormatter.Style, EnumMap<RelativeDateTimeFormatter.AbsoluteUnit, EnumMap<RelativeDateTimeFormatter.Direction, String>>> qualitativeUnitMap = new EnumMap<>(
         RelativeDateTimeFormatter.Style.class
      );
      EnumMap<RelativeDateTimeFormatter.Style, EnumMap<RelativeDateTimeFormatter.RelativeUnit, String[][]>> styleRelUnitPatterns = new EnumMap<>(
         RelativeDateTimeFormatter.Style.class
      );
      StringBuilder sb = new StringBuilder();
      int pastFutureIndex;
      RelativeDateTimeFormatter.Style style;
      RelativeDateTimeFormatter.RelDateTimeDataSink.DateTimeUnit unit;

      private RelativeDateTimeFormatter.Style styleFromKey(UResource.Key key) {
         if (key.endsWith("-short")) {
            return RelativeDateTimeFormatter.Style.SHORT;
         } else {
            return key.endsWith("-narrow") ? RelativeDateTimeFormatter.Style.NARROW : RelativeDateTimeFormatter.Style.LONG;
         }
      }

      private RelativeDateTimeFormatter.Style styleFromAlias(UResource.Value value) {
         String s = value.getAliasString();
         if (s.endsWith("-short")) {
            return RelativeDateTimeFormatter.Style.SHORT;
         } else {
            return s.endsWith("-narrow") ? RelativeDateTimeFormatter.Style.NARROW : RelativeDateTimeFormatter.Style.LONG;
         }
      }

      private static int styleSuffixLength(RelativeDateTimeFormatter.Style style) {
         switch (style) {
            case SHORT:
               return 6;
            case NARROW:
               return 7;
            default:
               return 0;
         }
      }

      public void consumeTableRelative(UResource.Key key, UResource.Value value) {
         UResource.Table unitTypesTable = value.getTable();

         for (int i = 0; unitTypesTable.getKeyAndValue(i, key, value); i++) {
            if (value.getType() == 0) {
               String valueString = value.getString();
               EnumMap<RelativeDateTimeFormatter.AbsoluteUnit, EnumMap<RelativeDateTimeFormatter.Direction, String>> absMap = this.qualitativeUnitMap
                  .get(this.style);
               if (this.unit.relUnit == RelativeDateTimeFormatter.RelativeUnit.SECONDS && key.contentEquals("0")) {
                  EnumMap<RelativeDateTimeFormatter.Direction, String> unitStrings = absMap.get(RelativeDateTimeFormatter.AbsoluteUnit.NOW);
                  if (unitStrings == null) {
                     unitStrings = new EnumMap<>(RelativeDateTimeFormatter.Direction.class);
                     absMap.put(RelativeDateTimeFormatter.AbsoluteUnit.NOW, unitStrings);
                  }

                  if (unitStrings.get(RelativeDateTimeFormatter.Direction.PLAIN) == null) {
                     unitStrings.put(RelativeDateTimeFormatter.Direction.PLAIN, valueString);
                  }
               } else {
                  RelativeDateTimeFormatter.Direction keyDirection = RelativeDateTimeFormatter.keyToDirection(key);
                  if (keyDirection != null) {
                     RelativeDateTimeFormatter.AbsoluteUnit absUnit = this.unit.absUnit;
                     if (absUnit != null) {
                        if (absMap == null) {
                           absMap = new EnumMap<>(RelativeDateTimeFormatter.AbsoluteUnit.class);
                           this.qualitativeUnitMap.put(this.style, absMap);
                        }

                        EnumMap<RelativeDateTimeFormatter.Direction, String> dirMap = absMap.get(absUnit);
                        if (dirMap == null) {
                           dirMap = new EnumMap<>(RelativeDateTimeFormatter.Direction.class);
                           absMap.put(absUnit, dirMap);
                        }

                        if (dirMap.get(keyDirection) == null) {
                           dirMap.put(keyDirection, value.getString());
                        }
                     }
                  }
               }
            }
         }
      }

      public void consumeTableRelativeTime(UResource.Key key, UResource.Value value) {
         if (this.unit.relUnit != null) {
            UResource.Table unitTypesTable = value.getTable();

            for (int i = 0; unitTypesTable.getKeyAndValue(i, key, value); i++) {
               if (key.contentEquals("past")) {
                  this.pastFutureIndex = 0;
               } else {
                  if (!key.contentEquals("future")) {
                     continue;
                  }

                  this.pastFutureIndex = 1;
               }

               this.consumeTimeDetail(key, value);
            }
         }
      }

      public void consumeTimeDetail(UResource.Key key, UResource.Value value) {
         UResource.Table unitTypesTable = value.getTable();
         EnumMap<RelativeDateTimeFormatter.RelativeUnit, String[][]> unitPatterns = this.styleRelUnitPatterns.get(this.style);
         if (unitPatterns == null) {
            unitPatterns = new EnumMap<>(RelativeDateTimeFormatter.RelativeUnit.class);
            this.styleRelUnitPatterns.put(this.style, unitPatterns);
         }

         String[][] patterns = unitPatterns.get(this.unit.relUnit);
         if (patterns == null) {
            patterns = new String[2][StandardPlural.COUNT];
            unitPatterns.put(this.unit.relUnit, patterns);
         }

         for (int i = 0; unitTypesTable.getKeyAndValue(i, key, value); i++) {
            if (value.getType() == 0) {
               int pluralIndex = StandardPlural.indexFromString(key.toString());
               if (patterns[this.pastFutureIndex][pluralIndex] == null) {
                  patterns[this.pastFutureIndex][pluralIndex] = SimpleFormatterImpl.compileToStringMinMaxArguments(value.getString(), this.sb, 0, 1);
               }
            }
         }
      }

      private void handlePlainDirection(UResource.Key key, UResource.Value value) {
         RelativeDateTimeFormatter.AbsoluteUnit absUnit = this.unit.absUnit;
         if (absUnit != null) {
            EnumMap<RelativeDateTimeFormatter.AbsoluteUnit, EnumMap<RelativeDateTimeFormatter.Direction, String>> unitMap = this.qualitativeUnitMap
               .get(this.style);
            if (unitMap == null) {
               unitMap = new EnumMap<>(RelativeDateTimeFormatter.AbsoluteUnit.class);
               this.qualitativeUnitMap.put(this.style, unitMap);
            }

            EnumMap<RelativeDateTimeFormatter.Direction, String> dirMap = unitMap.get(absUnit);
            if (dirMap == null) {
               dirMap = new EnumMap<>(RelativeDateTimeFormatter.Direction.class);
               unitMap.put(absUnit, dirMap);
            }

            if (dirMap.get(RelativeDateTimeFormatter.Direction.PLAIN) == null) {
               dirMap.put(RelativeDateTimeFormatter.Direction.PLAIN, value.toString());
            }
         }
      }

      public void consumeTimeUnit(UResource.Key key, UResource.Value value) {
         UResource.Table unitTypesTable = value.getTable();

         for (int i = 0; unitTypesTable.getKeyAndValue(i, key, value); i++) {
            if (key.contentEquals("dn") && value.getType() == 0) {
               this.handlePlainDirection(key, value);
            }

            if (value.getType() == 2) {
               if (key.contentEquals("relative")) {
                  this.consumeTableRelative(key, value);
               } else if (key.contentEquals("relativeTime")) {
                  this.consumeTableRelativeTime(key, value);
               }
            }
         }
      }

      private void handleAlias(UResource.Key key, UResource.Value value, boolean noFallback) {
         RelativeDateTimeFormatter.Style sourceStyle = this.styleFromKey(key);
         int limit = key.length() - styleSuffixLength(sourceStyle);
         RelativeDateTimeFormatter.RelDateTimeDataSink.DateTimeUnit unit = RelativeDateTimeFormatter.RelDateTimeDataSink.DateTimeUnit.orNullFromString(
            key.substring(0, limit)
         );
         if (unit != null) {
            RelativeDateTimeFormatter.Style targetStyle = this.styleFromAlias(value);
            if (sourceStyle == targetStyle) {
               throw new ICUException("Invalid style fallback from " + sourceStyle + " to itself");
            } else {
               if (RelativeDateTimeFormatter.fallbackCache[sourceStyle.ordinal()] == null) {
                  RelativeDateTimeFormatter.fallbackCache[sourceStyle.ordinal()] = targetStyle;
               } else if (RelativeDateTimeFormatter.fallbackCache[sourceStyle.ordinal()] != targetStyle) {
                  throw new ICUException("Inconsistent style fallback for style " + sourceStyle + " to " + targetStyle);
               }
            }
         }
      }

      @Override
      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         if (value.getType() != 3) {
            UResource.Table table = value.getTable();

            for (int i = 0; table.getKeyAndValue(i, key, value); i++) {
               if (value.getType() == 3) {
                  this.handleAlias(key, value, noFallback);
               } else {
                  this.style = this.styleFromKey(key);
                  int limit = key.length() - styleSuffixLength(this.style);
                  this.unit = RelativeDateTimeFormatter.RelDateTimeDataSink.DateTimeUnit.orNullFromString(key.substring(0, limit));
                  if (this.unit != null) {
                     this.consumeTimeUnit(key, value);
                  }
               }
            }
         }
      }

      RelDateTimeDataSink() {
      }

      private static enum DateTimeUnit {
         SECOND(RelativeDateTimeFormatter.RelativeUnit.SECONDS, null),
         MINUTE(RelativeDateTimeFormatter.RelativeUnit.MINUTES, RelativeDateTimeFormatter.AbsoluteUnit.MINUTE),
         HOUR(RelativeDateTimeFormatter.RelativeUnit.HOURS, RelativeDateTimeFormatter.AbsoluteUnit.HOUR),
         DAY(RelativeDateTimeFormatter.RelativeUnit.DAYS, RelativeDateTimeFormatter.AbsoluteUnit.DAY),
         WEEK(RelativeDateTimeFormatter.RelativeUnit.WEEKS, RelativeDateTimeFormatter.AbsoluteUnit.WEEK),
         MONTH(RelativeDateTimeFormatter.RelativeUnit.MONTHS, RelativeDateTimeFormatter.AbsoluteUnit.MONTH),
         QUARTER(RelativeDateTimeFormatter.RelativeUnit.QUARTERS, RelativeDateTimeFormatter.AbsoluteUnit.QUARTER),
         YEAR(RelativeDateTimeFormatter.RelativeUnit.YEARS, RelativeDateTimeFormatter.AbsoluteUnit.YEAR),
         SUNDAY(null, RelativeDateTimeFormatter.AbsoluteUnit.SUNDAY),
         MONDAY(null, RelativeDateTimeFormatter.AbsoluteUnit.MONDAY),
         TUESDAY(null, RelativeDateTimeFormatter.AbsoluteUnit.TUESDAY),
         WEDNESDAY(null, RelativeDateTimeFormatter.AbsoluteUnit.WEDNESDAY),
         THURSDAY(null, RelativeDateTimeFormatter.AbsoluteUnit.THURSDAY),
         FRIDAY(null, RelativeDateTimeFormatter.AbsoluteUnit.FRIDAY),
         SATURDAY(null, RelativeDateTimeFormatter.AbsoluteUnit.SATURDAY);

         RelativeDateTimeFormatter.RelativeUnit relUnit;
         RelativeDateTimeFormatter.AbsoluteUnit absUnit;

         private DateTimeUnit(RelativeDateTimeFormatter.RelativeUnit relUnit, RelativeDateTimeFormatter.AbsoluteUnit absUnit) {
            this.relUnit = relUnit;
            this.absUnit = absUnit;
         }

         private static final RelativeDateTimeFormatter.RelDateTimeDataSink.DateTimeUnit orNullFromString(CharSequence keyword) {
            switch (keyword.length()) {
               case 3:
                  if ("day".contentEquals(keyword)) {
                     return DAY;
                  }

                  if ("sun".contentEquals(keyword)) {
                     return SUNDAY;
                  }

                  if ("mon".contentEquals(keyword)) {
                     return MONDAY;
                  }

                  if ("tue".contentEquals(keyword)) {
                     return TUESDAY;
                  }

                  if ("wed".contentEquals(keyword)) {
                     return WEDNESDAY;
                  }

                  if ("thu".contentEquals(keyword)) {
                     return THURSDAY;
                  }

                  if ("fri".contentEquals(keyword)) {
                     return FRIDAY;
                  }

                  if ("sat".contentEquals(keyword)) {
                     return SATURDAY;
                  }
                  break;
               case 4:
                  if ("hour".contentEquals(keyword)) {
                     return HOUR;
                  }

                  if ("week".contentEquals(keyword)) {
                     return WEEK;
                  }

                  if ("year".contentEquals(keyword)) {
                     return YEAR;
                  }
                  break;
               case 5:
                  if ("month".contentEquals(keyword)) {
                     return MONTH;
                  }
                  break;
               case 6:
                  if ("minute".contentEquals(keyword)) {
                     return MINUTE;
                  }

                  if ("second".contentEquals(keyword)) {
                     return SECOND;
                  }
                  break;
               case 7:
                  if ("quarter".contentEquals(keyword)) {
                     return QUARTER;
                  }
            }

            return null;
         }
      }
   }

   private static class RelativeDateTimeFormatterData {
      public final EnumMap<RelativeDateTimeFormatter.Style, EnumMap<RelativeDateTimeFormatter.AbsoluteUnit, EnumMap<RelativeDateTimeFormatter.Direction, String>>> qualitativeUnitMap;
      EnumMap<RelativeDateTimeFormatter.Style, EnumMap<RelativeDateTimeFormatter.RelativeUnit, String[][]>> relUnitPatternMap;
      public final String dateTimePattern;

      public RelativeDateTimeFormatterData(
         EnumMap<RelativeDateTimeFormatter.Style, EnumMap<RelativeDateTimeFormatter.AbsoluteUnit, EnumMap<RelativeDateTimeFormatter.Direction, String>>> qualitativeUnitMap,
         EnumMap<RelativeDateTimeFormatter.Style, EnumMap<RelativeDateTimeFormatter.RelativeUnit, String[][]>> relUnitPatternMap,
         String dateTimePattern
      ) {
         this.qualitativeUnitMap = qualitativeUnitMap;
         this.relUnitPatternMap = relUnitPatternMap;
         this.dateTimePattern = dateTimePattern;
      }
   }

   public static enum RelativeDateTimeUnit {
      YEAR,
      QUARTER,
      MONTH,
      WEEK,
      DAY,
      HOUR,
      MINUTE,
      SECOND,
      SUNDAY,
      MONDAY,
      TUESDAY,
      WEDNESDAY,
      THURSDAY,
      FRIDAY,
      SATURDAY;
   }

   public static enum RelativeUnit {
      SECONDS,
      MINUTES,
      HOURS,
      DAYS,
      WEEKS,
      MONTHS,
      YEARS,
      @Deprecated
      QUARTERS;
   }

   public static enum Style {
      LONG,
      SHORT,
      NARROW;

      private static final int INDEX_COUNT = 3;
   }
}
