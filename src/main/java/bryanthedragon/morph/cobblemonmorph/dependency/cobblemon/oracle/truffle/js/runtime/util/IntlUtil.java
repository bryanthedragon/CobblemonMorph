
package com.oracle.truffle.js.runtime.util;

import com.cobblemon.mod.relocations.ibm.icu.text.CaseMap;
import com.cobblemon.mod.relocations.ibm.icu.text.Collator;
import com.cobblemon.mod.relocations.ibm.icu.text.CurrencyMetaInfo;
import com.cobblemon.mod.relocations.ibm.icu.text.DateFormat;
import com.cobblemon.mod.relocations.ibm.icu.text.NumberingSystem;
import com.cobblemon.mod.relocations.ibm.icu.util.Calendar;
import com.cobblemon.mod.relocations.ibm.icu.util.TimeZone;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.util.LazyValue;
import com.oracle.truffle.js.runtime.util.UTS35Validator;
import com.oracle.truffle.js.runtime.util.ZoneRulesBasedTimeZone;
import java.time.ZoneId;
import java.time.zone.ZoneRulesProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.IllformedLocaleException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

public final class IntlUtil {
    public static final String _2_DIGIT = "2-digit";
    public static final String ACCENT = "accent";
    public static final String ACCOUNTING = "accounting";
    public static final String ALWAYS = "always";
    public static final String AUTO = "auto";
    public static final String BEST_FIT = "best fit";
    public static final String BASE = "base";
    public static final String BASE_NAME = "baseName";
    public static final String BASIC = "basic";
    public static final String BREAK_TYPE = "breakType";
    public static final String CALENDAR = "calendar";
    public static final String CALENDARS = "calendars";
    public static final String CARDINAL = "cardinal";
    public static final String CASE = "case";
    public static final String CASE_FIRST = "caseFirst";
    public static final String CEIL = "ceil";
    public static final String CODE = "code";
    public static final String COLLATION = "collation";
    public static final String COLLATIONS = "collations";
    public static final String COMPACT = "compact";
    public static final String COMPACT_DISPLAY = "compactDisplay";
    public static final String CONJUNCTION = "conjunction";
    public static final String CURRENCY = "currency";
    public static final String CURRENCY_DISPLAY = "currencyDisplay";
    public static final String CURRENCY_SIGN = "currencySign";
    public static final String DATE_STYLE = "dateStyle";
    public static final String DATE_TIME_FIELD = "dateTimeField";
    public static final String DAY = "day";
    public static final String DAY_PERIOD = "dayPeriod";
    public static final String DEFAULT = "default";
    public static final String DECIMAL = "decimal";
    public static final String DIALECT = "dialect";
    public static final String DIRECTION = "direction";
    public static final String DISJUNCTION = "disjunction";
    public static final String ELEMENT = "element";
    public static final String END_RANGE = "endRange";
    public static final String ENGINEERING = "engineering";
    public static final String ERA = "era";
    public static final String EXCEPT_ZERO = "exceptZero";
    public static final String EXPAND = "expand";
    public static final String FALLBACK = "fallback";
    public static final String FALSE = "false";
    public static final String FIRST_DAY = "firstDay";
    public static final String FLOOR = "floor";
    public static final String FORMAT_MATCHER = "formatMatcher";
    public static final String FRACTIONAL_SECOND_DIGITS = "fractionalSecondDigits";
    public static final String FRACTION_DIGITS = "fractionDigits";
    public static final String FULL = "full";
    public static final String GRANULARITY = "granularity";
    public static final String GRAPHEME = "grapheme";
    public static final String H11 = "h11";
    public static final String H12 = "h12";
    public static final String H23 = "h23";
    public static final String H24 = "h24";
    public static final String HALF_CEIL = "halfCeil";
    public static final String HALF_EVEN = "halfEven";
    public static final String HALF_EXPAND = "halfExpand";
    public static final String HALF_FLOOR = "halfFloor";
    public static final String HALF_TRUNC = "halfTrunc";
    public static final String HOUR = "hour";
    public static final String HOUR_CYCLE = "hourCycle";
    public static final String HOUR_CYCLES = "hourCycles";
    public static final String HOUR12 = "hour12";
    public static final String INDEX = "index";
    public static final String INFINITY = "infinity";
    public static final String INPUT = "input";
    public static final String INTEGER = "integer";
    public static final String IS_WORD_LIKE = "isWordLike";
    public static final String IGNORE_PUNCTUATION = "ignorePunctuation";
    public static final String LANGUAGE = "language";
    public static final String LANGUAGE_DISPLAY = "languageDisplay";
    public static final String LESS_PRECISION = "lessPrecision";
    public static final String LITERAL = "literal";
    public static final String LOCALE = "locale";
    public static final String LOCALE_MATCHER = "localeMatcher";
    public static final String LONG = "long";
    public static final String LONG_GENERIC = "longGeneric";
    public static final String LONG_OFFSET = "longOffset";
    public static final String LOOKUP = "lookup";
    public static final String LOOSE = "loose";
    public static final String LOWER = "lower";
    public static final String LTR = "ltr";
    public static final String MAXIMUM_FRACTION_DIGITS = "maximumFractionDigits";
    public static final String MAXIMUM_SIGNIFICANT_DIGITS = "maximumSignificantDigits";
    public static final String MEDIUM = "medium";
    public static final String MIN2 = "min2";
    public static final String MINIMAL_DAYS = "minimalDays";
    public static final String MINIMUM_FRACTION_DIGITS = "minimumFractionDigits";
    public static final String MINIMUM_INTEGER_DIGITS = "minimumIntegerDigits";
    public static final String MINIMUM_SIGNIFICANT_DIGITS = "minimumSignificantDigits";
    public static final String MINUS_SIGN = "minusSign";
    public static final String MINUTE = "minute";
    public static final String MONTH = "month";
    public static final String MORE_PRECISION = "morePrecision";
    public static final String NAME = "name";
    public static final String NAN = "nan";
    public static final String NARROW = "narrow";
    public static final String NARROW_SYMBOL = "narrowSymbol";
    public static final String NEGATIVE = "negative";
    public static final String NEVER = "never";
    public static final String NONE = "none";
    public static final String NORMAL = "normal";
    public static final String NOTATION = "notation";
    public static final String NUMERIC = "numeric";
    public static final String NUMBERING_SYSTEM = "numberingSystem";
    public static final String NUMBERING_SYSTEMS = "numberingSystems";
    public static final String OR = "or";
    public static final String OR_NARROW = "or-narrow";
    public static final String OR_SHORT = "or-short";
    public static final String ORDINAL = "ordinal";
    public static final String PERCENT = "percent";
    public static final String PLURAL_CATEGORIES = "pluralCategories";
    public static final String PLUS_SIGN = "plusSign";
    public static final String QUARTER = "quarter";
    public static final String REGION = "region";
    public static final String ROUNDING_INCREMENT = "roundingIncrement";
    public static final String ROUNDING_MODE = "roundingMode";
    public static final String ROUNDING_PRIORITY = "roundingPriority";
    public static final String RTL = "rtl";
    public static final String SCIENTIFIC = "scientific";
    public static final String SCRIPT = "script";
    public static final String SEARCH = "search";
    public static final String SEP = "sep";
    public static final String SECOND = "second";
    public static final String SEGMENT = "segment";
    public static final String SENTENCE = "sentence";
    public static final String SENSITIVITY = "sensitivity";
    public static final String SHARED = "shared";
    public static final String SHORT = "short";
    public static final String SHORT_GENERIC = "shortGeneric";
    public static final String SHORT_OFFSET = "shortOffset";
    public static final String SIGNIFICANT_DIGITS = "significantDigits";
    public static final String SIGN_DISPLAY = "signDisplay";
    public static final String SORT = "sort";
    public static final String SOURCE = "source";
    public static final String STANDARD = "standard";
    public static final String STANDARD_NARROW = "standard-narrow";
    public static final String STANDARD_SHORT = "standard-short";
    public static final String START_RANGE = "startRange";
    public static final String STRICT = "strict";
    public static final String STRIP_IF_INTEGER = "stripIfInteger";
    public static final String STYLE = "style";
    public static final String SYMBOL = "symbol";
    public static final String TERM = "term";
    public static final String TEXT_INFO = "textInfo";
    public static final String TIME_STYLE = "timeStyle";
    public static final String TIME_ZONE = "timeZone";
    public static final String TIME_ZONES = "timeZones";
    public static final String TIME_ZONE_NAME = "timeZoneName";
    public static final String TRAILING_ZERO_DISPLAY = "trailingZeroDisplay";
    public static final String TRUNC = "trunc";
    public static final String TYPE = "type";
    public static final String UND = "und";
    public static final String UNIT = "unit";
    public static final String UNIT_DISPLAY = "unitDisplay";
    public static final String UNIT_NARROW = "unit-narrow";
    public static final String UNIT_SHORT = "unit-short";
    public static final String UPPER = "upper";
    public static final String USAGE = "usage";
    public static final String USE_GROUPING = "useGrouping";
    public static final String VALUE = "value";
    public static final String VARIANT = "variant";
    public static final String WORD = "word";
    public static final String WEEKDAY = "weekday";
    public static final String WEEKEND = "weekend";
    public static final String WEEK_INFO = "weekInfo";
    public static final String WEEK_OF_YEAR = "weekOfYear";
    public static final String YEAR = "year";
    public static final String YEAR_NAME = "yearName";
    public static final TruffleString KEY__2_DIGIT = Strings.constant("2-digit");
    public static final TruffleString KEY_ACCENT = Strings.constant("accent");
    public static final TruffleString KEY_ACCOUNTING = Strings.constant("accounting");
    public static final TruffleString KEY_ALWAYS = Strings.constant("always");
    public static final TruffleString KEY_AUTO = Strings.constant("auto");
    public static final TruffleString KEY_BEST_FIT = Strings.constant("best fit");
    public static final TruffleString KEY_BASE = Strings.constant("base");
    public static final TruffleString KEY_BASE_NAME = Strings.constant("baseName");
    public static final TruffleString KEY_BASIC = Strings.constant("basic");
    public static final TruffleString KEY_BREAK_TYPE = Strings.constant("breakType");
    public static final TruffleString KEY_CALENDAR = Strings.constant("calendar");
    public static final TruffleString KEY_CALENDARS = Strings.constant("calendars");
    public static final TruffleString KEY_CARDINAL = Strings.constant("cardinal");
    public static final TruffleString KEY_CASE = Strings.constant("case");
    public static final TruffleString KEY_CASE_FIRST = Strings.constant("caseFirst");
    public static final TruffleString KEY_CEIL = Strings.constant("ceil");
    public static final TruffleString KEY_CODE = Strings.constant("code");
    public static final TruffleString KEY_COLLATION = Strings.constant("collation");
    public static final TruffleString KEY_COLLATIONS = Strings.constant("collations");
    public static final TruffleString KEY_COMPACT = Strings.constant("compact");
    public static final TruffleString KEY_COMPACT_DISPLAY = Strings.constant("compactDisplay");
    public static final TruffleString KEY_CONJUNCTION = Strings.constant("conjunction");
    public static final TruffleString KEY_CURRENCY = Strings.constant("currency");
    public static final TruffleString KEY_CURRENCY_DISPLAY = Strings.constant("currencyDisplay");
    public static final TruffleString KEY_CURRENCY_SIGN = Strings.constant("currencySign");
    public static final TruffleString KEY_DATE_STYLE = Strings.constant("dateStyle");
    public static final TruffleString KEY_DATE_TIME_FIELD = Strings.constant("dateTimeField");
    public static final TruffleString KEY_DAY = Strings.constant("day");
    public static final TruffleString KEY_DAY_PERIOD = Strings.constant("dayPeriod");
    public static final TruffleString KEY_DEFAULT = Strings.constant("default");
    public static final TruffleString KEY_DECIMAL = Strings.constant("decimal");
    public static final TruffleString KEY_DIALECT = Strings.constant("dialect");
    public static final TruffleString KEY_DIRECTION = Strings.constant("direction");
    public static final TruffleString KEY_DISJUNCTION = Strings.constant("disjunction");
    public static final TruffleString KEY_ELEMENT = Strings.constant("element");
    public static final TruffleString KEY_END_RANGE = Strings.constant("endRange");
    public static final TruffleString KEY_ENGINEERING = Strings.constant("engineering");
    public static final TruffleString KEY_ERA = Strings.constant("era");
    public static final TruffleString KEY_EXCEPT_ZERO = Strings.constant("exceptZero");
    public static final TruffleString KEY_EXPAND = Strings.constant("expand");
    public static final TruffleString KEY_FALLBACK = Strings.constant("fallback");
    public static final TruffleString KEY_FALSE = Strings.constant("false");
    public static final TruffleString KEY_FIRST_DAY = Strings.constant("firstDay");
    public static final TruffleString KEY_FLOOR = Strings.constant("floor");
    public static final TruffleString KEY_FORMAT_MATCHER = Strings.constant("formatMatcher");
    public static final TruffleString KEY_FRACTIONAL_SECOND_DIGITS = Strings.constant("fractionalSecondDigits");
    public static final TruffleString KEY_FRACTION_DIGITS = Strings.constant("fractionDigits");
    public static final TruffleString KEY_FULL = Strings.constant("full");
    public static final TruffleString KEY_GRANULARITY = Strings.constant("granularity");
    public static final TruffleString KEY_GRAPHEME = Strings.constant("grapheme");
    public static final TruffleString KEY_H11 = Strings.constant("h11");
    public static final TruffleString KEY_H12 = Strings.constant("h12");
    public static final TruffleString KEY_H23 = Strings.constant("h23");
    public static final TruffleString KEY_H24 = Strings.constant("h24");
    public static final TruffleString KEY_HALF_CEIL = Strings.constant("halfCeil");
    public static final TruffleString KEY_HALF_EVEN = Strings.constant("halfEven");
    public static final TruffleString KEY_HALF_EXPAND = Strings.constant("halfExpand");
    public static final TruffleString KEY_HALF_FLOOR = Strings.constant("halfFloor");
    public static final TruffleString KEY_HALF_TRUNC = Strings.constant("halfTrunc");
    public static final TruffleString KEY_HOUR = Strings.constant("hour");
    public static final TruffleString KEY_HOUR_CYCLE = Strings.constant("hourCycle");
    public static final TruffleString KEY_HOUR_CYCLES = Strings.constant("hourCycles");
    public static final TruffleString KEY_HOUR12 = Strings.constant("hour12");
    public static final TruffleString KEY_INDEX = Strings.constant("index");
    public static final TruffleString KEY_INPUT = Strings.constant("input");
    public static final TruffleString KEY_IS_WORD_LIKE = Strings.constant("isWordLike");
    public static final TruffleString KEY_IGNORE_PUNCTUATION = Strings.constant("ignorePunctuation");
    public static final TruffleString KEY_LANGUAGE = Strings.constant("language");
    public static final TruffleString KEY_LANGUAGE_DISPLAY = Strings.constant("languageDisplay");
    public static final TruffleString KEY_LESS_PRECISION = Strings.constant("lessPrecision");
    public static final TruffleString KEY_LITERAL = Strings.constant("literal");
    public static final TruffleString KEY_LOCALE = Strings.constant("locale");
    public static final TruffleString KEY_LOCALE_MATCHER = Strings.constant("localeMatcher");
    public static final TruffleString KEY_LONG = Strings.constant("long");
    public static final TruffleString KEY_LONG_GENERIC = Strings.constant("longGeneric");
    public static final TruffleString KEY_LONG_OFFSET = Strings.constant("longOffset");
    public static final TruffleString KEY_LOOKUP = Strings.constant("lookup");
    public static final TruffleString KEY_LOOSE = Strings.constant("loose");
    public static final TruffleString KEY_LOWER = Strings.constant("lower");
    public static final TruffleString KEY_LTR = Strings.constant("ltr");
    public static final TruffleString KEY_MAXIMUM_FRACTION_DIGITS = Strings.constant("maximumFractionDigits");
    public static final TruffleString KEY_MAXIMUM_SIGNIFICANT_DIGITS = Strings.constant("maximumSignificantDigits");
    public static final TruffleString KEY_MEDIUM = Strings.constant("medium");
    public static final TruffleString KEY_MIN2 = Strings.constant("min2");
    public static final TruffleString KEY_MINIMAL_DAYS = Strings.constant("minimalDays");
    public static final TruffleString KEY_MINIMUM_FRACTION_DIGITS = Strings.constant("minimumFractionDigits");
    public static final TruffleString KEY_MINIMUM_INTEGER_DIGITS = Strings.constant("minimumIntegerDigits");
    public static final TruffleString KEY_MINIMUM_SIGNIFICANT_DIGITS = Strings.constant("minimumSignificantDigits");
    public static final TruffleString KEY_MINUTE = Strings.constant("minute");
    public static final TruffleString KEY_MONTH = Strings.constant("month");
    public static final TruffleString KEY_MORE_PRECISION = Strings.constant("morePrecision");
    public static final TruffleString KEY_NAME = Strings.constant("name");
    public static final TruffleString KEY_NARROW = Strings.constant("narrow");
    public static final TruffleString KEY_NARROW_SYMBOL = Strings.constant("narrowSymbol");
    public static final TruffleString KEY_NEGATIVE = Strings.constant("negative");
    public static final TruffleString KEY_NEVER = Strings.constant("never");
    public static final TruffleString KEY_NONE = Strings.constant("none");
    public static final TruffleString KEY_NORMAL = Strings.constant("normal");
    public static final TruffleString KEY_NOTATION = Strings.constant("notation");
    public static final TruffleString KEY_NUMERIC = Strings.constant("numeric");
    public static final TruffleString KEY_NUMBERING_SYSTEM = Strings.constant("numberingSystem");
    public static final TruffleString KEY_NUMBERING_SYSTEMS = Strings.constant("numberingSystems");
    public static final TruffleString KEY_OR = Strings.constant("or");
    public static final TruffleString KEY_OR_NARROW = Strings.constant("or-narrow");
    public static final TruffleString KEY_OR_SHORT = Strings.constant("or-short");
    public static final TruffleString KEY_ORDINAL = Strings.constant("ordinal");
    public static final TruffleString KEY_PERCENT = Strings.constant("percent");
    public static final TruffleString KEY_PLURAL_CATEGORIES = Strings.constant("pluralCategories");
    public static final TruffleString KEY_QUARTER = Strings.constant("quarter");
    public static final TruffleString KEY_REGION = Strings.constant("region");
    public static final TruffleString KEY_ROUNDING_INCREMENT = Strings.constant("roundingIncrement");
    public static final TruffleString KEY_ROUNDING_MODE = Strings.constant("roundingMode");
    public static final TruffleString KEY_ROUNDING_PRIORITY = Strings.constant("roundingPriority");
    public static final TruffleString KEY_RTL = Strings.constant("rtl");
    public static final TruffleString KEY_SCIENTIFIC = Strings.constant("scientific");
    public static final TruffleString KEY_SCRIPT = Strings.constant("script");
    public static final TruffleString KEY_SEARCH = Strings.constant("search");
    public static final TruffleString KEY_SEP = Strings.constant("sep");
    public static final TruffleString KEY_SECOND = Strings.constant("second");
    public static final TruffleString KEY_SEGMENT = Strings.constant("segment");
    public static final TruffleString KEY_SENTENCE = Strings.constant("sentence");
    public static final TruffleString KEY_SENSITIVITY = Strings.constant("sensitivity");
    public static final TruffleString KEY_SHARED = Strings.constant("shared");
    public static final TruffleString KEY_SHORT = Strings.constant("short");
    public static final TruffleString KEY_SHORT_GENERIC = Strings.constant("shortGeneric");
    public static final TruffleString KEY_SHORT_OFFSET = Strings.constant("shortOffset");
    public static final TruffleString KEY_SIGNIFICANT_DIGITS = Strings.constant("significantDigits");
    public static final TruffleString KEY_SIGN_DISPLAY = Strings.constant("signDisplay");
    public static final TruffleString KEY_SORT = Strings.constant("sort");
    public static final TruffleString KEY_SOURCE = Strings.constant("source");
    public static final TruffleString KEY_STANDARD = Strings.constant("standard");
    public static final TruffleString KEY_STANDARD_NARROW = Strings.constant("standard-narrow");
    public static final TruffleString KEY_STANDARD_SHORT = Strings.constant("standard-short");
    public static final TruffleString KEY_START_RANGE = Strings.constant("startRange");
    public static final TruffleString KEY_STRICT = Strings.constant("strict");
    public static final TruffleString KEY_STRIP_IF_INTEGER = Strings.constant("stripIfInteger");
    public static final TruffleString KEY_STYLE = Strings.constant("style");
    public static final TruffleString KEY_SYMBOL = Strings.constant("symbol");
    public static final TruffleString KEY_TERM = Strings.constant("term");
    public static final TruffleString KEY_TEXT_INFO = Strings.constant("textInfo");
    public static final TruffleString KEY_TIME_STYLE = Strings.constant("timeStyle");
    public static final TruffleString KEY_TIME_ZONE = Strings.constant("timeZone");
    public static final TruffleString KEY_TIME_ZONES = Strings.constant("timeZones");
    public static final TruffleString KEY_TIME_ZONE_NAME = Strings.constant("timeZoneName");
    public static final TruffleString KEY_TRAILING_ZERO_DISPLAY = Strings.constant("trailingZeroDisplay");
    public static final TruffleString KEY_TRUNC = Strings.constant("trunc");
    public static final TruffleString KEY_TYPE = Strings.constant("type");
    public static final TruffleString KEY_UND = Strings.constant("und");
    public static final TruffleString KEY_UNIT = Strings.constant("unit");
    public static final TruffleString KEY_UNIT_DISPLAY = Strings.constant("unitDisplay");
    public static final TruffleString KEY_UNIT_NARROW = Strings.constant("unit-narrow");
    public static final TruffleString KEY_UNIT_SHORT = Strings.constant("unit-short");
    public static final TruffleString KEY_UPPER = Strings.constant("upper");
    public static final TruffleString KEY_USAGE = Strings.constant("usage");
    public static final TruffleString KEY_USE_GROUPING = Strings.constant("useGrouping");
    public static final TruffleString KEY_VALUE = Strings.constant("value");
    public static final TruffleString KEY_VARIANT = Strings.constant("variant");
    public static final TruffleString KEY_WORD = Strings.constant("word");
    public static final TruffleString KEY_WEEKDAY = Strings.constant("weekday");
    public static final TruffleString KEY_WEEKEND = Strings.constant("weekend");
    public static final TruffleString KEY_WEEK_INFO = Strings.constant("weekInfo");
    public static final TruffleString KEY_WEEK_OF_YEAR = Strings.constant("weekOfYear");
    public static final TruffleString KEY_YEAR = Strings.constant("year");
    public static final TruffleString KEY_YEAR_NAME = Strings.constant("yearName");
    private static final Set<String> SANCTIONED_SIMPLE_UNIT_IDENTIFIERS = new HashSet<String>(Arrays.asList("acre", "bit", "byte", "celsius", "centimeter", "day", "degree", "fahrenheit", "fluid-ounce", "foot", "gallon", "gigabit", "gigabyte", "gram", "hectare", "hour", "inch", "kilobit", "kilobyte", "kilogram", "kilometer", "liter", "megabit", "megabyte", "meter", "mile", "mile-scandinavian", "milliliter", "millimeter", "millisecond", "minute", "month", "ounce", "percent", "petabyte", "pound", "second", "stone", "terabit", "terabyte", "week", "yard", "year"));
    private static final LazyValue<Set<Locale>> AVAILABLE_LOCALES = new LazyValue<Set>(IntlUtil::initAvailableLocales);
    private static final String YES_PLACEHOLDER = "yes31415";
    private static final String TRUE_PLACEHOLDER = "true2718";

    private IntlUtil() {
    }

    public static Locale selectedLocale(JSContext ctx, String[] locales) {
        return IntlUtil.lookupMatcher(ctx, locales);
    }

    public static Locale bestAvailableLocale(JSContext context, Locale locale) {
        Locale candidate = locale;
        while (!IntlUtil.isAvailableLocale(context, candidate)) {
            String candidateLanguageTag = candidate.toLanguageTag();
            int pos = candidateLanguageTag.lastIndexOf(45);
            if (pos == -1) {
                return null;
            }
            if (pos >= 2 && candidateLanguageTag.charAt(pos - 2) == '-') {
                pos -= 2;
            }
            candidateLanguageTag = candidateLanguageTag.substring(0, pos);
            candidate = Locale.forLanguageTag(candidateLanguageTag);
        }
        return candidate;
    }

    @CompilerDirectives.TruffleBoundary
    public static Locale lookupMatcher(JSContext ctx, String[] requestedLocales) {
        for (String locale : requestedLocales) {
            Locale requestedLocale = Locale.forLanguageTag(locale);
            Locale noExtensionsLocale = requestedLocale.stripExtensions();
            Locale availableLocale = IntlUtil.bestAvailableLocale(ctx, noExtensionsLocale);
            if (availableLocale == null) continue;
            String unicodeExtension = requestedLocale.getExtension('u');
            if (unicodeExtension != null) {
                availableLocale = new Locale.Builder().setLocale(availableLocale).setExtension('u', unicodeExtension).build();
            }
            return availableLocale;
        }
        return ctx.getLocale();
    }

    @CompilerDirectives.TruffleBoundary
    public static List<Object> supportedLocales(JSContext ctx, String[] requestedLocales, String matcher) {
        ArrayList<Object> result = new ArrayList<Object>();
        for (String locale : requestedLocales) {
            Locale noExtensionsLocale = Locale.forLanguageTag(locale).stripExtensions();
            Locale availableLocale = IntlUtil.bestAvailableLocale(ctx, noExtensionsLocale);
            if (availableLocale == null) continue;
            result.add(Strings.fromJavaString(locale));
        }
        return result;
    }

    private static boolean isAvailableLocale(JSContext ctx, Locale locale) {
        return IntlUtil.getAvailableLocales().contains(locale) || ctx.getLocale().equals(locale);
    }

    private static Set<Locale> getAvailableLocales() {
        return AVAILABLE_LOCALES.get();
    }

    private static Set<Locale> initAvailableLocales() {
        HashSet<Locale> result = new HashSet<Locale>();
        try {
            for (ULocale ul : ULocale.getAvailableLocales()) {
                result.add(ul.toLocale());
                if (ul.getScript().isEmpty()) continue;
                result.add(new Locale(ul.getLanguage(), ul.getCountry()));
            }
        }
        catch (MissingResourceException e) {
            throw Errors.createICU4JDataError(e);
        }
        return result;
    }

    public static boolean isValidNumberingSystem(String numberingSystem) {
        return Arrays.asList(NumberingSystem.getAvailableNames()).contains(numberingSystem);
    }

    public static String defaultNumberingSystemName(JSContext context, Locale locale) {
        if (context.isOptionV8CompatibilityMode() && "ar".equals(locale.toLanguageTag())) {
            return "latn";
        }
        return NumberingSystem.getInstance(locale).getName();
    }

    public static void validateUnicodeLocaleIdentifierType(String type, BranchProfile errorBranch) {
        if (!UTS35Validator.isStructurallyValidType(type)) {
            errorBranch.enter();
            throw Errors.createRangeErrorFormat("Invalid option: %s", null, type);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static String normalizeUnicodeLocaleIdentifierType(String type) {
        return type.toLowerCase();
    }

    @CompilerDirectives.TruffleBoundary
    public static Locale withoutUnicodeExtension(Locale originalLocale, String key) {
        if (!originalLocale.getUnicodeLocaleKeys().contains(key)) {
            return originalLocale;
        }
        String value2 = originalLocale.getUnicodeLocaleType(key);
        String originalTag = originalLocale.toLanguageTag();
        String toRemove = "-u-" + key + "-" + value2;
        String strippedTag = originalTag.replace(toRemove, "");
        return new Locale(strippedTag);
    }

    public static boolean isWellFormedCurrencyCode(String currency) {
        return currency.length() == 3 && UTS35Validator.isAlpha(currency.charAt(0)) && UTS35Validator.isAlpha(currency.charAt(1)) && UTS35Validator.isAlpha(currency.charAt(2));
    }

    public static void ensureIsWellFormedCurrencyCode(String currency) {
        if (!IntlUtil.isWellFormedCurrencyCode(currency)) {
            throw Errors.createRangeErrorCurrencyNotWellFormed(currency);
        }
    }

    public static void ensureIsStructurallyValidLanguageSubtag(String region) {
        if (!UTS35Validator.isStructurallyValidLanguageSubtag(region)) {
            throw Errors.createRangeErrorInvalidLanguage(region);
        }
    }

    public static void ensureIsStructurallyValidRegionSubtag(String region) {
        if (!UTS35Validator.isStructurallyValidRegionSubtag(region)) {
            throw Errors.createRangeErrorInvalidRegion(region);
        }
    }

    public static void ensureIsStructurallyValidScriptSubtag(String script) {
        if (!UTS35Validator.isStructurallyValidScriptSubtag(script)) {
            throw Errors.createRangeErrorInvalidScript(script);
        }
    }

    public static void ensureIsStructurallyValidCalendar(String calendar) {
        if (!UTS35Validator.isStructurallyValidType(calendar)) {
            throw Errors.createRangeErrorInvalidCalendar(calendar);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static String validateAndCanonicalizeLanguageTag(String languageTag) {
        try {
            Set<Character> extensions;
            if (!UTS35Validator.isWellFormedUnicodeBCP47LocaleIdentifier(languageTag)) {
                throw Errors.createRangeErrorFormat("Language tag is not well-formed: %s", null, languageTag);
            }
            ULocale locale = ULocale.createCanonical(ULocale.getName(languageTag));
            ULocale.Builder builder = new ULocale.Builder().setLocale(locale);
            String variant = locale.getVariant();
            if (variant.indexOf(95) != -1 || variant.indexOf(45) != -1) {
                Object[] variants = variant.split("[_-]");
                if (new HashSet<String>(Arrays.asList(variants)).size() != variants.length) {
                    throw Errors.createRangeErrorFormat("Language tag with duplicate variants: %s", null, languageTag);
                }
                Arrays.sort(variants);
                StringBuilder sb = new StringBuilder((String)variants[0]);
                for (int i = 1; i < variants.length; ++i) {
                    sb.append('-').append((String)variants[i]);
                }
                builder.setVariant(sb.toString());
            }
            if (!(extensions = locale.getExtensionKeys()).isEmpty()) {
                String tag = languageTag.toLowerCase();
                int privateExtIdx = extensions.contains(Character.valueOf('x')) ? tag.indexOf("-x-") : tag.length();
                for (Character c : extensions) {
                    int idx;
                    String extDelimiter;
                    int idx2;
                    if (c.charValue() == 'x' || (idx2 = tag.indexOf(extDelimiter = "-" + c + "-", (idx = tag.indexOf(extDelimiter)) + 1)) == -1 || idx2 >= privateExtIdx) continue;
                    throw Errors.createRangeErrorFormat("Language tag with duplicate singletons: %s", null, languageTag);
                }
                Locale loc = new Locale.Builder().setLanguageTag(languageTag).build();
                for (String key : loc.getUnicodeLocaleKeys()) {
                    String type = loc.getUnicodeLocaleType(key);
                    if (!(!"yes".equals(type) || "kb".equals(key) || "kc".equals(key) || "kh".equals(key) || "kk".equals(key) || "kn".equals(key))) {
                        type = YES_PLACEHOLDER;
                    }
                    if ("rg".equals(key) || "sd".equals(key)) {
                        type = IntlUtil.normalizeRGType(type);
                    }
                    builder.setUnicodeLocaleKeyword(key, type);
                }
                String string = new ULocale(languageTag).getExtension('t');
                if (string != null) {
                    builder.setExtension('t', IntlUtil.normalizeTransformedExtension(string));
                }
            }
            String result = IntlUtil.maybeAppendMissingLanguageSubTag(builder.build().toLanguageTag());
            return result.replaceAll("-yes31415", "-yes").replaceAll("-true2718", "-true");
        }
        catch (IllformedLocaleException e) {
            throw Errors.createRangeError(e.getMessage());
        }
    }

    public static String normalizeCAType(String type) {
        if ("gregorian".equals(type)) {
            return "gregory";
        }
        if ("ethiopic-amete-alem".equals(type)) {
            return "ethioaa";
        }
        if ("islamicc".equals(type)) {
            return "islamic-civil";
        }
        return type;
    }

    private static String normalizeRGType(String type) {
        if ("cn11".equals(type)) {
            return "cnbj";
        }
        if ("cz10a".equals(type)) {
            return "cz110";
        }
        if ("fra".equals(type) || "frg".equals(type)) {
            return "frges";
        }
        if ("lud".equals(type)) {
            return "lucl";
        }
        if ("no23".equals(type)) {
            return "no50";
        }
        return type;
    }

    private static String normalizeTransformedExtension(String extension) {
        String tlang = null;
        TreeMap<String, String> fields = new TreeMap<String, String>();
        boolean seenDash = true;
        String lastKey = null;
        int lastValueStart = -1;
        for (int i = 0; i < extension.length() - 1; ++i) {
            if (seenDash && UTS35Validator.isAlpha(extension.charAt(i)) && UTS35Validator.isDigit(extension.charAt(i + 1)) && (i + 2 == extension.length() || extension.charAt(i + 2) == '-')) {
                if (lastKey == null) {
                    tlang = extension.substring(0, Math.max(0, i - 1));
                } else {
                    fields.put(lastKey, extension.substring(lastValueStart, i - 1));
                }
                lastKey = extension.substring(i, i + 2);
                lastValueStart = i + 3;
            }
            seenDash = extension.charAt(i) == '-';
        }
        if (tlang == null) {
            tlang = extension;
        }
        if (lastKey != null) {
            fields.put(lastKey, extension.substring(lastValueStart));
        }
        StringBuilder normalized = new StringBuilder();
        if (!tlang.isEmpty()) {
            tlang = IntlUtil.validateAndCanonicalizeLanguageTag(tlang);
            normalized.append(tlang);
        }
        for (Map.Entry entry : fields.entrySet()) {
            String value2;
            if (normalized.length() != 0) {
                normalized.append('-');
            }
            if ("names".equalsIgnoreCase(value2 = (String)entry.getValue())) {
                value2 = "prprname";
            }
            if ("true".equals(value2)) {
                value2 = TRUE_PLACEHOLDER;
            }
            normalized.append((String)entry.getKey()).append('-').append(value2);
        }
        return normalized.toString();
    }

    public static String maybeAppendMissingLanguageSubTag(String tag) {
        return tag.startsWith("x-") ? "und-" + tag : tag;
    }

    @CompilerDirectives.TruffleBoundary
    public static String toUpperCase(String in) {
        StringBuilder result = new StringBuilder(in.length());
        for (int i = 0; i < in.length(); ++i) {
            int c = in.codePointAt(i);
            if (c >= 97 && c <= 122) {
                result.append((char)(c - 32));
                continue;
            }
            result.append((char)c);
        }
        return result.toString();
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString toLowerCase(JSContext ctx, String s, String[] locales) {
        Locale strippedLocale = IntlUtil.selectedLocaleStripped(ctx, locales);
        StringBuilder result = new StringBuilder();
        CaseMap.Lower tr = CaseMap.toLower();
        tr.apply(strippedLocale, s, result, null);
        return Strings.fromJavaString(result.toString());
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString toUpperCase(JSContext ctx, String s, String[] locales) {
        Locale strippedLocale = IntlUtil.selectedLocaleStripped(ctx, locales);
        StringBuilder result = new StringBuilder();
        CaseMap.Upper tr = CaseMap.toUpper();
        tr.apply(strippedLocale, s, result, null);
        return Strings.fromJavaString(result.toString());
    }

    @CompilerDirectives.TruffleBoundary
    public static Locale selectedLocaleStripped(JSContext ctx, String[] locales) {
        return IntlUtil.selectedLocale(ctx, locales).stripExtensions();
    }

    public static JSObject makePart(JSContext context, JSRealm realm, String type, String value2) {
        return IntlUtil.makePart(context, realm, type, value2, null);
    }

    public static JSObject makePart(JSContext context, JSRealm realm, String type, String value2, String unit) {
        return IntlUtil.makePart(context, realm, type, value2, unit, null);
    }

    public static JSObject makePart(JSContext context, JSRealm realm, String type, String value2, String unit, String source) {
        JSObject p = JSOrdinary.create(context, realm);
        JSObject.set((JSDynamicObject)p, KEY_TYPE, (Object)Strings.fromJavaString(type));
        JSObject.set((JSDynamicObject)p, KEY_VALUE, (Object)Strings.fromJavaString(value2));
        if (unit != null) {
            JSObject.set((JSDynamicObject)p, KEY_UNIT, (Object)Strings.fromJavaString(unit));
        }
        if (source != null) {
            JSObject.set((JSDynamicObject)p, KEY_SOURCE, (Object)Strings.fromJavaString(source));
        }
        return p;
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean isSanctionedSimpleUnitIdentifier(String unitIdentifier) {
        return SANCTIONED_SIMPLE_UNIT_IDENTIFIERS.contains(unitIdentifier);
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean isWellFormedUnitIdentifier(String unitIdentifier) {
        if (IntlUtil.isSanctionedSimpleUnitIdentifier(unitIdentifier)) {
            return true;
        }
        String per = "-per-";
        int index = unitIdentifier.indexOf(per);
        if (index == -1) {
            return false;
        }
        String numerator = unitIdentifier.substring(0, index);
        String denominator = unitIdentifier.substring(index + per.length());
        return IntlUtil.isSanctionedSimpleUnitIdentifier(numerator) && IntlUtil.isSanctionedSimpleUnitIdentifier(denominator);
    }

    public static void ensureIsWellFormedUnitIdentifier(String unitIdentifier) {
        if (!IntlUtil.isWellFormedUnitIdentifier(unitIdentifier)) {
            throw Errors.createRangeErrorInvalidUnitIdentifier(unitIdentifier);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static TimeZone getICUTimeZone(String tzId, JSContext context) {
        assert (tzId != null);
        if (context.getContextOptions().hasZoneRulesBasedTimeZones()) {
            return new ZoneRulesBasedTimeZone(tzId, ZoneRulesProvider.getRules(tzId, false));
        }
        return TimeZone.getTimeZone(tzId);
    }

    @CompilerDirectives.TruffleBoundary
    public static TimeZone getICUTimeZone(ZoneId zoneId, JSContext context) {
        if (context.getContextOptions().hasZoneRulesBasedTimeZones()) {
            return new ZoneRulesBasedTimeZone(zoneId.getId(), zoneId.getRules());
        }
        return TimeZone.getTimeZone(IntlUtil.toICUTimeZoneId(zoneId));
    }

    private static String toICUTimeZoneId(ZoneId zoneId) {
        Object tzid = zoneId.getId();
        char c = ((String)tzid).charAt(0);
        if (c == '+' || c == '-') {
            tzid = "GMT" + (String)tzid;
        } else if (c == 'Z' && ((String)tzid).length() == 1) {
            tzid = "UTC";
        } else if (((String)tzid).startsWith("UTC")) {
            tzid = "GMT" + ((String)tzid).substring(3);
        } else if (((String)tzid).startsWith("UT")) {
            tzid = "GMT" + ((String)tzid).substring(2);
        }
        return tzid;
    }

    public static String toJSHourCycle(DateFormat.HourCycle hourCycle) {
        switch (hourCycle) {
            case HOUR_CYCLE_11: {
                return H11;
            }
            case HOUR_CYCLE_12: {
                return H12;
            }
            case HOUR_CYCLE_23: {
                return H23;
            }
            case HOUR_CYCLE_24: {
                return H24;
            }
        }
        throw Errors.shouldNotReachHere(Objects.toString((Object)hourCycle));
    }

    @CompilerDirectives.TruffleBoundary
    public static String[] availableCalendars(ULocale locale, boolean commonlyUsed) {
        String[] calendars = Calendar.getKeywordValuesForLocale(CALENDAR, locale, commonlyUsed);
        int length = 0;
        for (String calendar : calendars) {
            if ("unknown".equals(calendar)) continue;
            calendars[length++] = IntlUtil.normalizeCAType(calendar);
        }
        if (length != calendars.length) {
            String[] trimmed = new String[length];
            System.arraycopy(calendars, 0, trimmed, 0, length);
            calendars = trimmed;
        }
        return calendars;
    }

    @CompilerDirectives.TruffleBoundary
    public static String[] availableCalendars() {
        Object[] calendars = IntlUtil.availableCalendars(ULocale.ROOT, false);
        Arrays.sort(calendars);
        return calendars;
    }

    public static String normalizeCollation(String collation) {
        String normalizedCollation;
        switch (collation) {
            case "dictionary": {
                normalizedCollation = "dict";
                break;
            }
            case "gb2312han": {
                normalizedCollation = "gb2312";
                break;
            }
            case "phonebook": {
                normalizedCollation = "phonebk";
                break;
            }
            case "traditional": {
                normalizedCollation = "trad";
                break;
            }
            default: {
                normalizedCollation = collation;
            }
        }
        return normalizedCollation;
    }

    @CompilerDirectives.TruffleBoundary
    public static String[] availableCollations(ULocale locale, boolean commonOnly) {
        String[] collations = locale == null ? Collator.getKeywordValues(COLLATION) : Collator.getKeywordValuesForLocale(COLLATION, locale, commonOnly);
        int length = 0;
        for (String element : collations) {
            if (SEARCH.equals(element) || STANDARD.equals(element)) continue;
            collations[length++] = IntlUtil.normalizeCollation(element);
        }
        if (length != collations.length) {
            String[] trimmed = new String[length];
            System.arraycopy(collations, 0, trimmed, 0, length);
            collations = trimmed;
        }
        return collations;
    }

    @CompilerDirectives.TruffleBoundary
    public static String[] availableCollations() {
        Object[] collations = IntlUtil.availableCollations(null, false);
        Arrays.sort(collations);
        return collations;
    }

    @CompilerDirectives.TruffleBoundary
    public static String[] availableCurrencies() {
        List<String> list = CurrencyMetaInfo.getInstance().currencies(CurrencyMetaInfo.CurrencyFilter.all());
        Object[] currencies = list.toArray(new String[list.size()]);
        Arrays.sort(currencies);
        return currencies;
    }

    @CompilerDirectives.TruffleBoundary
    public static String[] availableNumberingSystems(JSContext context) {
        Object[] numberingSystems = NumberingSystem.getAvailableNames();
        if (context.isOptionV8CompatibilityMode()) {
            int length = 0;
            for (Object numberingSystem : numberingSystems) {
                if (NumberingSystem.getInstanceByName((String)numberingSystem).isAlgorithmic()) continue;
                numberingSystems[length++] = numberingSystem;
            }
            if (length != numberingSystems.length) {
                String[] trimmed = new String[length];
                System.arraycopy(numberingSystems, 0, trimmed, 0, length);
                numberingSystems = trimmed;
            }
        }
        Arrays.sort(numberingSystems);
        return numberingSystems;
    }

    @CompilerDirectives.TruffleBoundary
    public static String[] availableTimeZones() {
        Set<String> set2 = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL_LOCATION, null, null);
        Object[] timeZones = set2.toArray(new String[set2.size()]);
        Arrays.sort(timeZones);
        return timeZones;
    }

    @CompilerDirectives.TruffleBoundary
    public static String[] availableUnits() {
        Set<String> set2 = SANCTIONED_SIMPLE_UNIT_IDENTIFIERS;
        Object[] units = set2.toArray(new String[set2.size()]);
        Arrays.sort(units);
        return units;
    }

    public static String sourceString(int start2, int limit, int startRangeStart, int startRangeLimit, int endRangeStart, int endRangeLimit) {
        String source = startRangeStart <= start2 && limit <= startRangeLimit ? START_RANGE : (endRangeStart <= start2 && limit <= endRangeLimit ? END_RANGE : SHARED);
        return source;
    }
}

