
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.mod.relocations.ibm.icu.text;

import com.cobblemon.mod.relocations.ibm.icu.impl.CacheBase;
import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedStringBuilder;
import com.cobblemon.mod.relocations.ibm.icu.impl.FormattedValueStringBuilderImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.ICUResourceBundle;
import com.cobblemon.mod.relocations.ibm.icu.impl.SimpleFormatterImpl;
import com.cobblemon.mod.relocations.ibm.icu.impl.SoftCache;
import com.cobblemon.mod.relocations.ibm.icu.impl.StandardPlural;
import com.cobblemon.mod.relocations.ibm.icu.impl.UResource;
import com.cobblemon.mod.relocations.ibm.icu.impl.Utility;
import com.cobblemon.mod.relocations.ibm.icu.impl.number.DecimalQuantity_DualStorageBCD;
import com.cobblemon.mod.relocations.ibm.icu.lang.UCharacter;
import com.cobblemon.mod.relocations.ibm.icu.text.BreakIterator;
import com.cobblemon.mod.relocations.ibm.icu.text.ConstrainedFieldPosition;
import com.cobblemon.mod.relocations.ibm.icu.text.DateFormatSymbols;
import com.cobblemon.mod.relocations.ibm.icu.text.DecimalFormat;
import com.cobblemon.mod.relocations.ibm.icu.text.DisplayContext;
import com.cobblemon.mod.relocations.ibm.icu.text.FormattedValue;
import com.cobblemon.mod.relocations.ibm.icu.text.NumberFormat;
import com.cobblemon.mod.relocations.ibm.icu.text.PluralRules;
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
    private final EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap;
    private final EnumMap<Style, EnumMap<RelativeUnit, String[][]>> patternMap;
    private final String combinedDateAndTime;
    private final PluralRules pluralRules;
    private final NumberFormat numberFormat;
    private final Style style;
    private final DisplayContext capitalizationContext;
    private final BreakIterator breakIterator;
    private final ULocale locale;
    private final DateFormatSymbols dateFormatSymbols;
    private static final Style[] fallbackCache = new Style[3];
    private static final Cache cache = new Cache();

    public static RelativeDateTimeFormatter getInstance() {
        return RelativeDateTimeFormatter.getInstance(ULocale.getDefault(), null, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }

    public static RelativeDateTimeFormatter getInstance(ULocale locale) {
        return RelativeDateTimeFormatter.getInstance(locale, null, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }

    public static RelativeDateTimeFormatter getInstance(Locale locale) {
        return RelativeDateTimeFormatter.getInstance(ULocale.forLocale(locale));
    }

    public static RelativeDateTimeFormatter getInstance(ULocale locale, NumberFormat nf) {
        return RelativeDateTimeFormatter.getInstance(locale, nf, Style.LONG, DisplayContext.CAPITALIZATION_NONE);
    }

    public static RelativeDateTimeFormatter getInstance(ULocale locale, NumberFormat nf, Style style, DisplayContext capitalizationContext) {
        RelativeDateTimeFormatterData data = cache.get(locale);
        nf = nf == null ? NumberFormat.getInstance(locale) : (NumberFormat)nf.clone();
        return new RelativeDateTimeFormatter(data.qualitativeUnitMap, data.relUnitPatternMap, SimpleFormatterImpl.compileToStringMinMaxArguments(data.dateTimePattern, new StringBuilder(), 2, 2), PluralRules.forLocale(locale), nf, style, capitalizationContext, capitalizationContext == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE ? BreakIterator.getSentenceInstance(locale) : null, locale);
    }

    public static RelativeDateTimeFormatter getInstance(Locale locale, NumberFormat nf) {
        return RelativeDateTimeFormatter.getInstance(ULocale.forLocale(locale), nf);
    }

    public String format(double quantity, Direction direction, RelativeUnit unit) {
        FormattedStringBuilder output = this.formatImpl(quantity, direction, unit);
        return this.adjustForContext(output.toString());
    }

    public FormattedRelativeDateTime formatToValue(double quantity, Direction direction, RelativeUnit unit) {
        this.checkNoAdjustForContext();
        return new FormattedRelativeDateTime(this.formatImpl(quantity, direction, unit));
    }

    private FormattedStringBuilder formatImpl(double quantity, Direction direction, RelativeUnit unit) {
        String pluralKeyword;
        if (direction != Direction.LAST && direction != Direction.NEXT) {
            throw new IllegalArgumentException("direction must be NEXT or LAST");
        }
        int pastFutureIndex = direction == Direction.NEXT ? 1 : 0;
        FormattedStringBuilder output = new FormattedStringBuilder();
        if (this.numberFormat instanceof DecimalFormat) {
            DecimalQuantity_DualStorageBCD dq = new DecimalQuantity_DualStorageBCD(quantity);
            ((DecimalFormat)this.numberFormat).toNumberFormatter().formatImpl(dq, output);
            pluralKeyword = this.pluralRules.select(dq);
        } else {
            String result = this.numberFormat.format(quantity);
            output.append(result, null);
            pluralKeyword = this.pluralRules.select(quantity);
        }
        StandardPlural pluralForm = StandardPlural.orOtherFromString(pluralKeyword);
        String compiledPattern = this.getRelativeUnitPluralPattern(this.style, unit, pastFutureIndex, pluralForm);
        SimpleFormatterImpl.formatPrefixSuffix(compiledPattern, Field.LITERAL, 0, output.length(), output);
        return output;
    }

    public String formatNumeric(double offset, RelativeDateTimeUnit unit) {
        FormattedStringBuilder output = this.formatNumericImpl(offset, unit);
        return this.adjustForContext(output.toString());
    }

    public FormattedRelativeDateTime formatNumericToValue(double offset, RelativeDateTimeUnit unit) {
        this.checkNoAdjustForContext();
        return new FormattedRelativeDateTime(this.formatNumericImpl(offset, unit));
    }

    private FormattedStringBuilder formatNumericImpl(double offset, RelativeDateTimeUnit unit) {
        RelativeUnit relunit = RelativeUnit.SECONDS;
        switch (unit) {
            case YEAR: {
                relunit = RelativeUnit.YEARS;
                break;
            }
            case QUARTER: {
                relunit = RelativeUnit.QUARTERS;
                break;
            }
            case MONTH: {
                relunit = RelativeUnit.MONTHS;
                break;
            }
            case WEEK: {
                relunit = RelativeUnit.WEEKS;
                break;
            }
            case DAY: {
                relunit = RelativeUnit.DAYS;
                break;
            }
            case HOUR: {
                relunit = RelativeUnit.HOURS;
                break;
            }
            case MINUTE: {
                relunit = RelativeUnit.MINUTES;
                break;
            }
            case SECOND: {
                break;
            }
            default: {
                throw new UnsupportedOperationException("formatNumeric does not currently support RelativeUnit.SUNDAY..SATURDAY");
            }
        }
        Direction direction = Direction.NEXT;
        if (Double.compare(offset, 0.0) < 0) {
            direction = Direction.LAST;
            offset = -offset;
        }
        return this.formatImpl(offset, direction, relunit);
    }

    public String format(Direction direction, AbsoluteUnit unit) {
        String result = this.formatAbsoluteImpl(direction, unit);
        return result != null ? this.adjustForContext(result) : null;
    }

    public FormattedRelativeDateTime formatToValue(Direction direction, AbsoluteUnit unit) {
        this.checkNoAdjustForContext();
        String string = this.formatAbsoluteImpl(direction, unit);
        if (string == null) {
            return null;
        }
        FormattedStringBuilder nsb = new FormattedStringBuilder();
        nsb.append(string, Field.LITERAL);
        return new FormattedRelativeDateTime(nsb);
    }

    private String formatAbsoluteImpl(Direction direction, AbsoluteUnit unit) {
        String result;
        if (unit == AbsoluteUnit.NOW && direction != Direction.PLAIN) {
            throw new IllegalArgumentException("NOW can only accept direction PLAIN.");
        }
        if (direction == Direction.PLAIN && AbsoluteUnit.SUNDAY.ordinal() <= unit.ordinal() && unit.ordinal() <= AbsoluteUnit.SATURDAY.ordinal()) {
            int dateSymbolsDayOrdinal = unit.ordinal() - AbsoluteUnit.SUNDAY.ordinal() + 1;
            String[] dayNames = this.dateFormatSymbols.getWeekdays(1, this.styleToDateFormatSymbolsWidth[this.style.ordinal()]);
            result = dayNames[dateSymbolsDayOrdinal];
        } else {
            result = this.getAbsoluteUnitString(this.style, unit, direction);
        }
        return result;
    }

    public String format(double offset, RelativeDateTimeUnit unit) {
        return this.adjustForContext(this.formatRelativeImpl(offset, unit).toString());
    }

    public FormattedRelativeDateTime formatToValue(double offset, RelativeDateTimeUnit unit) {
        FormattedStringBuilder nsb;
        this.checkNoAdjustForContext();
        CharSequence cs = this.formatRelativeImpl(offset, unit);
        if (cs instanceof FormattedStringBuilder) {
            nsb = (FormattedStringBuilder)cs;
        } else {
            nsb = new FormattedStringBuilder();
            nsb.append(cs, Field.LITERAL);
        }
        return new FormattedRelativeDateTime(nsb);
    }

    private CharSequence formatRelativeImpl(double offset, RelativeDateTimeUnit unit) {
        String result;
        boolean useNumeric = true;
        Direction direction = Direction.THIS;
        if (offset > -2.1 && offset < 2.1) {
            double offsetx100 = offset * 100.0;
            int intoffsetx100 = offsetx100 < 0.0 ? (int)(offsetx100 - 0.5) : (int)(offsetx100 + 0.5);
            switch (intoffsetx100) {
                case -200: {
                    direction = Direction.LAST_2;
                    useNumeric = false;
                    break;
                }
                case -100: {
                    direction = Direction.LAST;
                    useNumeric = false;
                    break;
                }
                case 0: {
                    useNumeric = false;
                    break;
                }
                case 100: {
                    direction = Direction.NEXT;
                    useNumeric = false;
                    break;
                }
                case 200: {
                    direction = Direction.NEXT_2;
                    useNumeric = false;
                    break;
                }
            }
        }
        AbsoluteUnit absunit = AbsoluteUnit.NOW;
        switch (unit) {
            case YEAR: {
                absunit = AbsoluteUnit.YEAR;
                break;
            }
            case QUARTER: {
                absunit = AbsoluteUnit.QUARTER;
                break;
            }
            case MONTH: {
                absunit = AbsoluteUnit.MONTH;
                break;
            }
            case WEEK: {
                absunit = AbsoluteUnit.WEEK;
                break;
            }
            case DAY: {
                absunit = AbsoluteUnit.DAY;
                break;
            }
            case SUNDAY: {
                absunit = AbsoluteUnit.SUNDAY;
                break;
            }
            case MONDAY: {
                absunit = AbsoluteUnit.MONDAY;
                break;
            }
            case TUESDAY: {
                absunit = AbsoluteUnit.TUESDAY;
                break;
            }
            case WEDNESDAY: {
                absunit = AbsoluteUnit.WEDNESDAY;
                break;
            }
            case THURSDAY: {
                absunit = AbsoluteUnit.THURSDAY;
                break;
            }
            case FRIDAY: {
                absunit = AbsoluteUnit.FRIDAY;
                break;
            }
            case SATURDAY: {
                absunit = AbsoluteUnit.SATURDAY;
                break;
            }
            case HOUR: {
                absunit = AbsoluteUnit.HOUR;
                break;
            }
            case MINUTE: {
                absunit = AbsoluteUnit.MINUTE;
                break;
            }
            case SECOND: {
                if (direction == Direction.THIS) {
                    direction = Direction.PLAIN;
                    break;
                }
                useNumeric = true;
                break;
            }
            default: {
                useNumeric = true;
            }
        }
        if (!useNumeric && (result = this.formatAbsoluteImpl(direction, absunit)) != null && result.length() > 0) {
            return result;
        }
        return this.formatNumericImpl(offset, unit);
    }

    private String getAbsoluteUnitString(Style style, AbsoluteUnit unit, Direction direction) {
        do {
            String result;
            EnumMap<Direction, String> dirMap;
            EnumMap<AbsoluteUnit, EnumMap<Direction, String>> unitMap;
            if ((unitMap = this.qualitativeUnitMap.get((Object)style)) == null || (dirMap = unitMap.get((Object)unit)) == null || (result = dirMap.get((Object)direction)) == null) continue;
            return result;
        } while ((style = fallbackCache[style.ordinal()]) != null);
        return null;
    }

    public String combineDateAndTime(String relativeDateString, String timeString) {
        return SimpleFormatterImpl.formatCompiledPattern(this.combinedDateAndTime, timeString, relativeDateString);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public NumberFormat getNumberFormat() {
        NumberFormat numberFormat = this.numberFormat;
        synchronized (numberFormat) {
            return (NumberFormat)this.numberFormat.clone();
        }
    }

    public DisplayContext getCapitalizationContext() {
        return this.capitalizationContext;
    }

    public Style getFormatStyle() {
        return this.style;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private String adjustForContext(String originalFormattedString) {
        if (this.breakIterator == null || originalFormattedString.length() == 0 || !UCharacter.isLowerCase(UCharacter.codePointAt(originalFormattedString, 0))) {
            return originalFormattedString;
        }
        BreakIterator breakIterator = this.breakIterator;
        synchronized (breakIterator) {
            return UCharacter.toTitleCase(this.locale, originalFormattedString, this.breakIterator, 768);
        }
    }

    private void checkNoAdjustForContext() {
        if (this.breakIterator != null) {
            throw new UnsupportedOperationException("Capitalization context is not supported in formatV");
        }
    }

    private RelativeDateTimeFormatter(EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap, EnumMap<Style, EnumMap<RelativeUnit, String[][]>> patternMap, String combinedDateAndTime, PluralRules pluralRules, NumberFormat numberFormat, Style style, DisplayContext capitalizationContext, BreakIterator breakIterator, ULocale locale) {
        this.qualitativeUnitMap = qualitativeUnitMap;
        this.patternMap = patternMap;
        this.combinedDateAndTime = combinedDateAndTime;
        this.pluralRules = pluralRules;
        this.numberFormat = numberFormat;
        this.style = style;
        if (capitalizationContext.type() != DisplayContext.Type.CAPITALIZATION) {
            throw new IllegalArgumentException(capitalizationContext.toString());
        }
        this.capitalizationContext = capitalizationContext;
        this.breakIterator = breakIterator;
        this.locale = locale;
        this.dateFormatSymbols = new DateFormatSymbols(locale);
    }

    private String getRelativeUnitPluralPattern(Style style, RelativeUnit unit, int pastFutureIndex, StandardPlural pluralForm) {
        String formatter;
        if (pluralForm != StandardPlural.OTHER && (formatter = this.getRelativeUnitPattern(style, unit, pastFutureIndex, pluralForm)) != null) {
            return formatter;
        }
        return this.getRelativeUnitPattern(style, unit, pastFutureIndex, StandardPlural.OTHER);
    }

    private String getRelativeUnitPattern(Style style, RelativeUnit unit, int pastFutureIndex, StandardPlural pluralForm) {
        int pluralIndex = pluralForm.ordinal();
        do {
            String[][] spfCompiledPatterns;
            EnumMap<RelativeUnit, String[][]> unitMap;
            if ((unitMap = this.patternMap.get((Object)style)) == null || (spfCompiledPatterns = unitMap.get((Object)unit)) == null || spfCompiledPatterns[pastFutureIndex][pluralIndex] == null) continue;
            return spfCompiledPatterns[pastFutureIndex][pluralIndex];
        } while ((style = fallbackCache[style.ordinal()]) != null);
        return null;
    }

    private static Direction keyToDirection(UResource.Key key) {
        if (key.contentEquals("-2")) {
            return Direction.LAST_2;
        }
        if (key.contentEquals("-1")) {
            return Direction.LAST;
        }
        if (key.contentEquals("0")) {
            return Direction.THIS;
        }
        if (key.contentEquals("1")) {
            return Direction.NEXT;
        }
        if (key.contentEquals("2")) {
            return Direction.NEXT_2;
        }
        return null;
    }

    private static class Loader {
        private final ULocale ulocale;

        public Loader(ULocale ulocale) {
            this.ulocale = ulocale;
        }

        private String getDateTimePattern(ICUResourceBundle r) {
            String resourcePath;
            ICUResourceBundle patternsRb;
            String calType = r.getStringWithFallback("calendar/default");
            if (calType == null || calType.equals("")) {
                calType = "gregorian";
            }
            if ((patternsRb = r.findWithFallback(resourcePath = "calendar/" + calType + "/DateTimePatterns")) == null && calType.equals("gregorian")) {
                patternsRb = r.findWithFallback("calendar/gregorian/DateTimePatterns");
            }
            if (patternsRb == null || patternsRb.getSize() < 9) {
                return "{1} {0}";
            }
            int elementType = patternsRb.get(8).getType();
            if (elementType == 8) {
                return patternsRb.get(8).getString(0);
            }
            return patternsRb.getString(8);
        }

        public RelativeDateTimeFormatterData load() {
            RelDateTimeDataSink sink = new RelDateTimeDataSink();
            ICUResourceBundle r = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b", this.ulocale);
            r.getAllItemsWithFallback("fields", sink);
            for (Style testStyle : Style.values()) {
                Style newStyle2;
                Style newStyle1 = fallbackCache[testStyle.ordinal()];
                if (newStyle1 == null || (newStyle2 = fallbackCache[newStyle1.ordinal()]) == null || fallbackCache[newStyle2.ordinal()] == null) continue;
                throw new IllegalStateException("Style fallback too deep");
            }
            return new RelativeDateTimeFormatterData(sink.qualitativeUnitMap, sink.styleRelUnitPatterns, this.getDateTimePattern(r));
        }
    }

    private static final class RelDateTimeDataSink
    extends UResource.Sink {
        EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap = new EnumMap(Style.class);
        EnumMap<Style, EnumMap<RelativeUnit, String[][]>> styleRelUnitPatterns = new EnumMap(Style.class);
        StringBuilder sb = new StringBuilder();
        int pastFutureIndex;
        Style style;
        DateTimeUnit unit;

        private Style styleFromKey(UResource.Key key) {
            if (key.endsWith("-short")) {
                return Style.SHORT;
            }
            if (key.endsWith("-narrow")) {
                return Style.NARROW;
            }
            return Style.LONG;
        }

        private Style styleFromAlias(UResource.Value value2) {
            String s = value2.getAliasString();
            if (s.endsWith("-short")) {
                return Style.SHORT;
            }
            if (s.endsWith("-narrow")) {
                return Style.NARROW;
            }
            return Style.LONG;
        }

        private static int styleSuffixLength(Style style) {
            switch (style) {
                case SHORT: {
                    return 6;
                }
                case NARROW: {
                    return 7;
                }
            }
            return 0;
        }

        public void consumeTableRelative(UResource.Key key, UResource.Value value2) {
            UResource.Table unitTypesTable = value2.getTable();
            int i = 0;
            while (unitTypesTable.getKeyAndValue(i, key, value2)) {
                if (value2.getType() == 0) {
                    String valueString = value2.getString();
                    EnumMap<AbsoluteUnit, EnumMap<Direction, String>> absMap = this.qualitativeUnitMap.get((Object)this.style);
                    if (this.unit.relUnit == RelativeUnit.SECONDS && key.contentEquals("0")) {
                        EnumMap<Direction, String> unitStrings = absMap.get((Object)AbsoluteUnit.NOW);
                        if (unitStrings == null) {
                            unitStrings = new EnumMap(Direction.class);
                            absMap.put(AbsoluteUnit.NOW, unitStrings);
                        }
                        if (unitStrings.get((Object)Direction.PLAIN) == null) {
                            unitStrings.put(Direction.PLAIN, valueString);
                        }
                    } else {
                        AbsoluteUnit absUnit;
                        Direction keyDirection = RelativeDateTimeFormatter.keyToDirection(key);
                        if (keyDirection != null && (absUnit = this.unit.absUnit) != null) {
                            EnumMap<Direction, String> dirMap;
                            if (absMap == null) {
                                absMap = new EnumMap(AbsoluteUnit.class);
                                this.qualitativeUnitMap.put(this.style, absMap);
                            }
                            if ((dirMap = absMap.get((Object)absUnit)) == null) {
                                dirMap = new EnumMap(Direction.class);
                                absMap.put(absUnit, dirMap);
                            }
                            if (dirMap.get((Object)keyDirection) == null) {
                                dirMap.put(keyDirection, value2.getString());
                            }
                        }
                    }
                }
                ++i;
            }
        }

        public void consumeTableRelativeTime(UResource.Key key, UResource.Value value2) {
            if (this.unit.relUnit == null) {
                return;
            }
            UResource.Table unitTypesTable = value2.getTable();
            int i = 0;
            while (unitTypesTable.getKeyAndValue(i, key, value2)) {
                block7: {
                    block6: {
                        block5: {
                            if (!key.contentEquals("past")) break block5;
                            this.pastFutureIndex = 0;
                            break block6;
                        }
                        if (!key.contentEquals("future")) break block7;
                        this.pastFutureIndex = 1;
                    }
                    this.consumeTimeDetail(key, value2);
                }
                ++i;
            }
        }

        public void consumeTimeDetail(UResource.Key key, UResource.Value value2) {
            String[][] patterns;
            UResource.Table unitTypesTable = value2.getTable();
            EnumMap<RelativeUnit, Object> unitPatterns = this.styleRelUnitPatterns.get((Object)this.style);
            if (unitPatterns == null) {
                unitPatterns = new EnumMap(RelativeUnit.class);
                this.styleRelUnitPatterns.put(this.style, unitPatterns);
            }
            if ((patterns = unitPatterns.get((Object)this.unit.relUnit)) == null) {
                patterns = new String[2][StandardPlural.COUNT];
                unitPatterns.put(this.unit.relUnit, (String[][])patterns);
            }
            int i = 0;
            while (unitTypesTable.getKeyAndValue(i, key, value2)) {
                int pluralIndex;
                if (value2.getType() == 0 && patterns[this.pastFutureIndex][pluralIndex = StandardPlural.indexFromString(key.toString())] == null) {
                    patterns[this.pastFutureIndex][pluralIndex] = SimpleFormatterImpl.compileToStringMinMaxArguments(value2.getString(), this.sb, 0, 1);
                }
                ++i;
            }
        }

        private void handlePlainDirection(UResource.Key key, UResource.Value value2) {
            EnumMap<Direction, String> dirMap;
            AbsoluteUnit absUnit = this.unit.absUnit;
            if (absUnit == null) {
                return;
            }
            EnumMap<AbsoluteUnit, EnumMap<Direction, String>> unitMap = this.qualitativeUnitMap.get((Object)this.style);
            if (unitMap == null) {
                unitMap = new EnumMap(AbsoluteUnit.class);
                this.qualitativeUnitMap.put(this.style, unitMap);
            }
            if ((dirMap = unitMap.get((Object)absUnit)) == null) {
                dirMap = new EnumMap(Direction.class);
                unitMap.put(absUnit, dirMap);
            }
            if (dirMap.get((Object)Direction.PLAIN) == null) {
                dirMap.put(Direction.PLAIN, value2.toString());
            }
        }

        public void consumeTimeUnit(UResource.Key key, UResource.Value value2) {
            UResource.Table unitTypesTable = value2.getTable();
            int i = 0;
            while (unitTypesTable.getKeyAndValue(i, key, value2)) {
                if (key.contentEquals("dn") && value2.getType() == 0) {
                    this.handlePlainDirection(key, value2);
                }
                if (value2.getType() == 2) {
                    if (key.contentEquals("relative")) {
                        this.consumeTableRelative(key, value2);
                    } else if (key.contentEquals("relativeTime")) {
                        this.consumeTableRelativeTime(key, value2);
                    }
                }
                ++i;
            }
        }

        private void handleAlias(UResource.Key key, UResource.Value value2, boolean noFallback) {
            Style sourceStyle = this.styleFromKey(key);
            int limit = key.length() - RelDateTimeDataSink.styleSuffixLength(sourceStyle);
            DateTimeUnit unit = DateTimeUnit.orNullFromString(key.substring(0, limit));
            if (unit != null) {
                Style targetStyle = this.styleFromAlias(value2);
                if (sourceStyle == targetStyle) {
                    throw new ICUException("Invalid style fallback from " + (Object)((Object)sourceStyle) + " to itself");
                }
                if (fallbackCache[sourceStyle.ordinal()] == null) {
                    fallbackCache[sourceStyle.ordinal()] = targetStyle;
                } else if (fallbackCache[sourceStyle.ordinal()] != targetStyle) {
                    throw new ICUException("Inconsistent style fallback for style " + (Object)((Object)sourceStyle) + " to " + (Object)((Object)targetStyle));
                }
                return;
            }
        }

        @Override
        public void put(UResource.Key key, UResource.Value value2, boolean noFallback) {
            if (value2.getType() == 3) {
                return;
            }
            UResource.Table table = value2.getTable();
            int i = 0;
            while (table.getKeyAndValue(i, key, value2)) {
                if (value2.getType() == 3) {
                    this.handleAlias(key, value2, noFallback);
                } else {
                    this.style = this.styleFromKey(key);
                    int limit = key.length() - RelDateTimeDataSink.styleSuffixLength(this.style);
                    this.unit = DateTimeUnit.orNullFromString(key.substring(0, limit));
                    if (this.unit != null) {
                        this.consumeTimeUnit(key, value2);
                    }
                }
                ++i;
            }
        }

        RelDateTimeDataSink() {
        }

        private static enum DateTimeUnit {
            SECOND(RelativeUnit.SECONDS, null),
            MINUTE(RelativeUnit.MINUTES, AbsoluteUnit.MINUTE),
            HOUR(RelativeUnit.HOURS, AbsoluteUnit.HOUR),
            DAY(RelativeUnit.DAYS, AbsoluteUnit.DAY),
            WEEK(RelativeUnit.WEEKS, AbsoluteUnit.WEEK),
            MONTH(RelativeUnit.MONTHS, AbsoluteUnit.MONTH),
            QUARTER(RelativeUnit.QUARTERS, AbsoluteUnit.QUARTER),
            YEAR(RelativeUnit.YEARS, AbsoluteUnit.YEAR),
            SUNDAY(null, AbsoluteUnit.SUNDAY),
            MONDAY(null, AbsoluteUnit.MONDAY),
            TUESDAY(null, AbsoluteUnit.TUESDAY),
            WEDNESDAY(null, AbsoluteUnit.WEDNESDAY),
            THURSDAY(null, AbsoluteUnit.THURSDAY),
            FRIDAY(null, AbsoluteUnit.FRIDAY),
            SATURDAY(null, AbsoluteUnit.SATURDAY);

            RelativeUnit relUnit;
            AbsoluteUnit absUnit;

            private DateTimeUnit(RelativeUnit relUnit, AbsoluteUnit absUnit) {
                this.relUnit = relUnit;
                this.absUnit = absUnit;
            }

            private static final DateTimeUnit orNullFromString(CharSequence keyword) {
                switch (keyword.length()) {
                    case 3: {
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
                        if (!"sat".contentEquals(keyword)) break;
                        return SATURDAY;
                    }
                    case 4: {
                        if ("hour".contentEquals(keyword)) {
                            return HOUR;
                        }
                        if ("week".contentEquals(keyword)) {
                            return WEEK;
                        }
                        if (!"year".contentEquals(keyword)) break;
                        return YEAR;
                    }
                    case 5: {
                        if (!"month".contentEquals(keyword)) break;
                        return MONTH;
                    }
                    case 6: {
                        if ("minute".contentEquals(keyword)) {
                            return MINUTE;
                        }
                        if (!"second".contentEquals(keyword)) break;
                        return SECOND;
                    }
                    case 7: {
                        if (!"quarter".contentEquals(keyword)) break;
                        return QUARTER;
                    }
                }
                return null;
            }
        }
    }

    private static class Cache {
        private final CacheBase<String, RelativeDateTimeFormatterData, ULocale> cache = new SoftCache<String, RelativeDateTimeFormatterData, ULocale>(){

            @Override
            protected RelativeDateTimeFormatterData createInstance(String key, ULocale locale) {
                return new Loader(locale).load();
            }
        };

        private Cache() {
        }

        public RelativeDateTimeFormatterData get(ULocale locale) {
            String key = locale.toString();
            return this.cache.getInstance(key, locale);
        }
    }

    private static class RelativeDateTimeFormatterData {
        public final EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap;
        EnumMap<Style, EnumMap<RelativeUnit, String[][]>> relUnitPatternMap;
        public final String dateTimePattern;

        public RelativeDateTimeFormatterData(EnumMap<Style, EnumMap<AbsoluteUnit, EnumMap<Direction, String>>> qualitativeUnitMap, EnumMap<Style, EnumMap<RelativeUnit, String[][]>> relUnitPatternMap, String dateTimePattern) {
            this.qualitativeUnitMap = qualitativeUnitMap;
            this.relUnitPatternMap = relUnitPatternMap;
            this.dateTimePattern = dateTimePattern;
        }
    }

    public static class FormattedRelativeDateTime
    implements FormattedValue {
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
        public CharSequence subSequence(int start2, int end2) {
            return this.string.subString(start2, end2);
        }

        @Override
        public <A extends Appendable> A appendTo(A appendable) {
            return Utility.appendTo(this.string, appendable);
        }

        @Override
        public boolean nextPosition(ConstrainedFieldPosition cfpos) {
            return FormattedValueStringBuilderImpl.nextPosition(this.string, cfpos, Field.NUMERIC);
        }

        @Override
        public AttributedCharacterIterator toCharacterIterator() {
            return FormattedValueStringBuilderImpl.toCharacterIterator(this.string, Field.NUMERIC);
        }
    }

    public static class Field
    extends Format.Field {
        private static final long serialVersionUID = -5327685528663492325L;
        public static final Field LITERAL = new Field("literal");
        public static final Field NUMERIC = new Field("numeric");

        private Field(String fieldName) {
            super(fieldName);
        }

        @Override
        @Deprecated
        protected Object readResolve() throws InvalidObjectException {
            if (this.getName().equals(LITERAL.getName())) {
                return LITERAL;
            }
            if (this.getName().equals(NUMERIC.getName())) {
                return NUMERIC;
            }
            throw new InvalidObjectException("An invalid object.");
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

    public static enum Direction {
        LAST_2,
        LAST,
        THIS,
        NEXT,
        NEXT_2,
        PLAIN;

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

    public static enum RelativeUnit {
        SECONDS,
        MINUTES,
        HOURS,
        DAYS,
        WEEKS,
        MONTHS,
        YEARS,
        QUARTERS;

    }

    public static enum Style {
        LONG,
        SHORT,
        NARROW;

        private static final int INDEX_COUNT = 3;
    }
}

