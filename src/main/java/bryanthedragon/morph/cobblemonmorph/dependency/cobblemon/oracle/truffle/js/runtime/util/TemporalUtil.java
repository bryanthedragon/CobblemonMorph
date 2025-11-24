
package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.ExactMath;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerOrInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerThrowOnInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerWithoutRoundingNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarGetterNode;
import com.oracle.truffle.js.nodes.temporal.TemporalDurationAddNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSDate;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormat;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendar;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendarObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDateTimeRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstant;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstantObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalNanosecondsDaysRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalParserRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDate;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDayObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonthObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPrecisionRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalTimeZone;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalTimeZoneRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalYearMonthDayRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTimeRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.TemporalCalendar;
import com.oracle.truffle.js.runtime.builtins.temporal.TemporalDay;
import com.oracle.truffle.js.runtime.builtins.temporal.TemporalMonth;
import com.oracle.truffle.js.runtime.builtins.temporal.TemporalYear;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalParser;
import com.oracle.truffle.js.runtime.util.UnmodifiableArrayList;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.zone.ZoneOffsetTransition;
import java.time.zone.ZoneRules;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalLong;
import java.util.Set;
import java.util.function.Function;

public final class TemporalUtil {
    private static final Function<Object, Object> toIntegerThrowOnInfinity = TemporalUtil::toIntegerThrowOnInfinity;
    private static final Function<Object, Object> toPositiveInteger = TemporalUtil::toPositiveInteger;
    private static final Function<Object, Object> toString = JSRuntime::toString;
    public static final Set<TruffleString> pluralUnits = Set.of((Object)TemporalConstants.YEARS, (Object)TemporalConstants.MONTHS, (Object)TemporalConstants.WEEKS, (Object)TemporalConstants.DAYS, (Object)TemporalConstants.HOURS, (Object)TemporalConstants.MINUTES, (Object)TemporalConstants.SECONDS, (Object)TemporalConstants.MILLISECONDS, (Object)TemporalConstants.MICROSECONDS, (Object)TemporalConstants.NANOSECONDS);
    public static final Map<TruffleString, TruffleString> pluralToSingular = TemporalUtil.toMap(new TruffleString[]{TemporalConstants.YEARS, TemporalConstants.MONTHS, TemporalConstants.WEEKS, TemporalConstants.DAYS, TemporalConstants.HOURS, TemporalConstants.MINUTES, TemporalConstants.SECONDS, TemporalConstants.MILLISECONDS, TemporalConstants.MICROSECONDS, TemporalConstants.NANOSECONDS}, new TruffleString[]{TemporalConstants.YEAR, TemporalConstants.MONTH, TemporalConstants.WEEK, TemporalConstants.DAY, TemporalConstants.HOUR, TemporalConstants.MINUTE, TemporalConstants.SECOND, TemporalConstants.MILLISECOND, TemporalConstants.MICROSECOND, TemporalConstants.NANOSECOND});
    private static final Map<TruffleString, Function<Object, Object>> temporalFieldConversion = TemporalUtil.toMap(new TruffleString[]{TemporalConstants.YEAR, TemporalConstants.MONTH, TemporalConstants.MONTH_CODE, TemporalConstants.DAY, TemporalConstants.HOUR, TemporalConstants.MINUTE, TemporalConstants.SECOND, TemporalConstants.MILLISECOND, TemporalConstants.MICROSECOND, TemporalConstants.NANOSECOND, TemporalConstants.OFFSET, TemporalConstants.ERA, TemporalConstants.ERA_YEAR}, new Function[]{toIntegerThrowOnInfinity, toPositiveInteger, toString, toPositiveInteger, toIntegerThrowOnInfinity, toIntegerThrowOnInfinity, toIntegerThrowOnInfinity, toIntegerThrowOnInfinity, toIntegerThrowOnInfinity, toIntegerThrowOnInfinity, toString, toString, toIntegerThrowOnInfinity});
    public static final Map<TruffleString, Object> temporalFieldDefaults = TemporalUtil.toMap(new TruffleString[]{TemporalConstants.YEAR, TemporalConstants.MONTH, TemporalConstants.MONTH_CODE, TemporalConstants.DAY, TemporalConstants.HOUR, TemporalConstants.MINUTE, TemporalConstants.SECOND, TemporalConstants.MILLISECOND, TemporalConstants.MICROSECOND, TemporalConstants.NANOSECOND, TemporalConstants.YEARS, TemporalConstants.MONTHS, TemporalConstants.WEEKS, TemporalConstants.DAYS, TemporalConstants.HOURS, TemporalConstants.MINUTES, TemporalConstants.SECONDS, TemporalConstants.MILLISECONDS, TemporalConstants.MICROSECONDS, TemporalConstants.NANOSECONDS, TemporalConstants.OFFSET, TemporalConstants.ERA, TemporalConstants.ERA_YEAR}, new Object[]{Undefined.instance, Undefined.instance, Undefined.instance, Undefined.instance, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, Undefined.instance, Undefined.instance, Undefined.instance});
    public static final List<TruffleString> listEmpty = List.of();
    public static final List<TruffleString> listYMWD = List.of((Object)TemporalConstants.YEAR, (Object)TemporalConstants.MONTH, (Object)TemporalConstants.WEEK, (Object)TemporalConstants.DAY);
    public static final List<TruffleString> listPluralYMWD = List.of((Object)TemporalConstants.YEARS, (Object)TemporalConstants.MONTHS, (Object)TemporalConstants.WEEKS, (Object)TemporalConstants.DAYS);
    public static final List<TruffleString> listYMW = List.of((Object)TemporalConstants.YEAR, (Object)TemporalConstants.MONTH, (Object)TemporalConstants.WEEK);
    public static final List<TruffleString> listYMWDH = List.of((Object)TemporalConstants.YEAR, (Object)TemporalConstants.MONTH, (Object)TemporalConstants.WEEK, (Object)TemporalConstants.DAY, (Object)TemporalConstants.HOUR);
    public static final List<TruffleString> listTime = List.of((Object)TemporalConstants.HOUR, (Object)TemporalConstants.MINUTE, (Object)TemporalConstants.SECOND, (Object)TemporalConstants.MILLISECOND, (Object)TemporalConstants.MICROSECOND, (Object)TemporalConstants.NANOSECOND);
    public static final List<TruffleString> listDMMCY = List.of((Object)TemporalConstants.DAY, (Object)TemporalConstants.MONTH, (Object)TemporalConstants.MONTH_CODE, (Object)TemporalConstants.YEAR);
    public static final List<TruffleString> listMMCY = List.of((Object)TemporalConstants.MONTH, (Object)TemporalConstants.MONTH_CODE, (Object)TemporalConstants.YEAR);
    public static final List<TruffleString> listMCY = List.of((Object)TemporalConstants.MONTH_CODE, (Object)TemporalConstants.YEAR);
    public static final List<TruffleString> listDMC = List.of((Object)TemporalConstants.DAY, (Object)TemporalConstants.MONTH_CODE);
    public static final List<TruffleString> listYD = List.of((Object)TemporalConstants.YEAR, (Object)TemporalConstants.DAY);
    public static final List<TruffleString> listY = List.of((Object)TemporalConstants.YEAR);
    public static final List<TruffleString> listD = List.of((Object)TemporalConstants.DAY);
    public static final List<TruffleString> listWDHMSMMN = List.of((Object)TemporalConstants.WEEK, (Object)TemporalConstants.DAY, (Object)TemporalConstants.HOUR, (Object)TemporalConstants.MINUTE, (Object)TemporalConstants.SECOND, (Object)TemporalConstants.MILLISECOND, (Object)TemporalConstants.MICROSECOND, (Object)TemporalConstants.NANOSECOND);
    public static final List<TruffleString> listAllDateTime = List.of((Object[])new TruffleString[]{TemporalConstants.YEARS, TemporalConstants.YEAR, TemporalConstants.MONTHS, TemporalConstants.MONTH, TemporalConstants.WEEKS, TemporalConstants.WEEK, TemporalConstants.DAYS, TemporalConstants.DAY, TemporalConstants.HOURS, TemporalConstants.HOUR, TemporalConstants.MINUTES, TemporalConstants.MINUTE, TemporalConstants.SECONDS, TemporalConstants.SECOND, TemporalConstants.MILLISECONDS, TemporalConstants.MILLISECOND, TemporalConstants.MICROSECONDS, TemporalConstants.MICROSECOND, TemporalConstants.NANOSECONDS, TemporalConstants.NANOSECOND});
    public static final List<TruffleString> listAllDateTimeAuto = List.of((Object[])new TruffleString[]{TemporalConstants.AUTO, TemporalConstants.YEARS, TemporalConstants.YEAR, TemporalConstants.MONTHS, TemporalConstants.MONTH, TemporalConstants.WEEKS, TemporalConstants.WEEK, TemporalConstants.DAYS, TemporalConstants.DAY, TemporalConstants.HOURS, TemporalConstants.HOUR, TemporalConstants.MINUTES, TemporalConstants.MINUTE, TemporalConstants.SECONDS, TemporalConstants.SECOND, TemporalConstants.MILLISECONDS, TemporalConstants.MILLISECOND, TemporalConstants.MICROSECONDS, TemporalConstants.MICROSECOND, TemporalConstants.NANOSECONDS, TemporalConstants.NANOSECOND});
    public static final List<TruffleString> listDHMMMMMNSY = List.of((Object)TemporalConstants.DAY, (Object)TemporalConstants.HOUR, (Object)TemporalConstants.MICROSECOND, (Object)TemporalConstants.MILLISECOND, (Object)TemporalConstants.MINUTE, (Object)TemporalConstants.MONTH, (Object)TemporalConstants.MONTH_CODE, (Object)TemporalConstants.NANOSECOND, (Object)TemporalConstants.SECOND, (Object)TemporalConstants.YEAR);
    public static final List<TruffleString> listAuto = List.of((Object)TemporalConstants.AUTO);
    public static final List<TruffleString> listAutoNever = List.of((Object)TemporalConstants.AUTO, (Object)TemporalConstants.NEVER);
    public static final List<TruffleString> listAutoAlwaysNever = List.of((Object)TemporalConstants.AUTO, (Object)TemporalConstants.ALWAYS, (Object)TemporalConstants.NEVER);
    public static final List<TruffleString> listConstrainReject = List.of((Object)TemporalConstants.CONSTRAIN, (Object)TemporalConstants.REJECT);
    public static final List<TruffleString> listTimeZone = List.of((Object)TemporalConstants.TIME_ZONE);
    public static final List<TruffleString> listTimeZoneOffset = List.of((Object)TemporalConstants.TIME_ZONE, (Object)TemporalConstants.OFFSET);
    public static final List<TruffleString> listRoundingMode = List.of((Object)TemporalConstants.CEIL, (Object)TemporalConstants.FLOOR, (Object)TemporalConstants.TRUNC, (Object)TemporalConstants.HALF_EXPAND);
    public static final List<TruffleString> listOffset = List.of((Object)TemporalConstants.PREFER, (Object)TemporalConstants.USE, (Object)TemporalConstants.IGNORE, (Object)TemporalConstants.REJECT);
    public static final List<TruffleString> listDisambiguation = List.of((Object)TemporalConstants.COMPATIBLE, (Object)TemporalConstants.EARLIER, (Object)TemporalConstants.LATER, (Object)TemporalConstants.REJECT);
    public static final TruffleString[] TIME_LIKE_PROPERTIES = new TruffleString[]{TemporalConstants.HOUR, TemporalConstants.MICROSECOND, TemporalConstants.MILLISECOND, TemporalConstants.MINUTE, TemporalConstants.NANOSECOND, TemporalConstants.SECOND};
    public static final UnitPlural[] DURATION_PROPERTIES = new UnitPlural[]{UnitPlural.DAYS, UnitPlural.HOURS, UnitPlural.MICROSECONDS, UnitPlural.MILLISECONDS, UnitPlural.MINUTES, UnitPlural.MONTHS, UnitPlural.NANOSECONDS, UnitPlural.SECONDS, UnitPlural.WEEKS, UnitPlural.YEARS};
    private static final BigInt upperEpochNSLimit = new BigInt(BigInteger.valueOf(86400L).multiply(BigInteger.valueOf(10L).pow(17)));
    private static final BigInt lowerEpochNSLimit = upperEpochNSLimit.negate();
    private static final BigInteger isoTimeUpperBound = new BigInteger("8640000086400000000000");
    private static final BigInteger isoTimeLowerBound = isoTimeUpperBound.negate();
    private static final int isoTimeBoundYears = 270000;
    private static final BigInteger BI_8_64_13 = new BigInteger("86400000000000");
    public static final BigInteger BI_36_10_POW_11 = new BigInteger("3600000000000");
    public static final BigInteger BI_6_10_POW_10 = new BigInteger("60000000000");
    public static final BigInteger BI_10_POW_9 = new BigInteger("1000000000");
    public static final BigInteger BI_10_POW_6 = new BigInteger("1000000");
    public static final BigInteger BI_1000 = new BigInteger("1000");
    public static final BigDecimal BD_10 = new BigDecimal("10");
    public static final BigDecimal BD_60 = new BigDecimal("60");
    public static final BigDecimal BD_1000 = new BigDecimal("1000");
    public static final BigDecimal BD_10_POW_M_3 = new BigDecimal("0.001");
    public static final BigDecimal BD_10_POW_M_6 = new BigDecimal("0.000001");
    public static final BigDecimal BD_10_POW_M_9 = new BigDecimal("0.000000001");
    public static final char UNICODE_MINUS_SIGN = '\u2212';
    public static final MathContext mc_20_floor = new MathContext(20, java.math.RoundingMode.FLOOR);
    public static final TruffleString FRACTIONAL_SECOND_DIGITS = Strings.constant("fractionalSecondDigits");
    public static final TruffleString ZEROS = Strings.constant("000000000");
    public static final TruffleString OFFSET_ZERO = Strings.constant("+00:00");
    public static final TruffleString CALENDAR_NAME = Strings.constant("calendarName");
    public static final TruffleString BRACKET_U_CA_EQUALS = Strings.constant("[u-ca=");
    public static final TruffleString GET_OFFSET_NANOSECONDS_FOR = Strings.constant("getOffsetNanosecondsFor");
    public static final TruffleString YEAR_MONTH_FROM_FIELDS = Strings.constant("yearMonthFromFields");
    public static final TruffleString MONTH_DAY_FROM_FIELDS = Strings.constant("monthDayFromFields");
    public static final TruffleString GET_POSSIBLE_INSTANTS_FOR = Strings.constant("getPossibleInstantsFor");
    public static final int HOURS_PER_DAY = 24;
    public static final int MINUTES_PER_HOUR = 60;
    public static final int SECONDS_PER_MINUTE = 60;
    public static final double MS_PER_DAY = 8.64E7;
    public static final double NS_PER_DAY = 8.64E13;
    public static final int SINCE = -1;
    public static final int UNTIL = 1;
    public static final int SUBTRACT = -1;
    public static final int ADD = 1;

    public static double defaultNumberOptions(Object value2, double minimum, double maximum, double fallback, JSToNumberNode toNumber) {
        if (value2 == Undefined.instance) {
            return fallback;
        }
        double numberValue = JSRuntime.doubleValue(toNumber.executeNumber(value2));
        if (Double.isNaN(numberValue) || numberValue < minimum || numberValue > maximum || Double.isInfinite(numberValue) && Double.isInfinite(maximum)) {
            throw Errors.createRangeError("Numeric value out of range.");
        }
        return Math.floor(numberValue);
    }

    public static double getNumberOption(JSDynamicObject options, TruffleString property, double minimum, double maximum, double fallback, IsObjectNode isObject, JSToNumberNode numberNode) {
        assert (isObject.executeBoolean(options));
        Object value2 = JSObject.get(options, property);
        return TemporalUtil.defaultNumberOptions(value2, minimum, maximum, fallback, numberNode);
    }

    public static Object getStringOrNumberOption(JSDynamicObject options, TruffleString property, List<TruffleString> stringValues, double minimum, double maximum, Object fallback, JSToStringNode toStringNode, TemporalGetOptionNode getOptionNode) {
        assert (JSRuntime.isObject(options));
        Object value2 = getOptionNode.execute(options, property, OptionType.NUMBER_AND_STRING, null, fallback);
        if (value2 instanceof Number) {
            double numberValue = JSRuntime.doubleValue((Number)value2);
            if (Double.isNaN(numberValue) || numberValue < minimum || numberValue > maximum) {
                throw Errors.createRangeError("Numeric value out of range.");
            }
            return Math.floor(numberValue);
        }
        value2 = toStringNode.executeString(value2);
        if (stringValues != null && !Boundaries.listContainsUnchecked(stringValues, value2)) {
            throw Errors.createRangeError("Given string value is not in string values");
        }
        return value2;
    }

    public static double toTemporalRoundingIncrement(JSDynamicObject options, Double dividend, boolean inclusive, IsObjectNode isObject, JSToNumberNode toNumber) {
        double maximum;
        double dDividend = Double.NaN;
        if (dividend == null) {
            maximum = Double.POSITIVE_INFINITY;
        } else {
            dDividend = JSRuntime.doubleValue(dividend);
            maximum = inclusive ? dDividend : (dDividend > 1.0 ? dDividend - 1.0 : 1.0);
        }
        double increment = TemporalUtil.getNumberOption(options, TemporalConstants.ROUNDING_INCREMENT, 1.0, maximum, 1.0, isObject, toNumber);
        if (dividend != null && dDividend % increment != 0.0) {
            throw Errors.createRangeError("Increment out of range.");
        }
        return increment;
    }

    public static JSTemporalPrecisionRecord toSecondsStringPrecision(JSDynamicObject options, JSToStringNode toStringNode, TemporalGetOptionNode getOptionNode, TruffleString.EqualNode equalNode) {
        Unit smallestUnit = TemporalUtil.toSmallestTemporalUnit(options, listYMWDH, null, getOptionNode, equalNode);
        if (Unit.MINUTE == smallestUnit) {
            return JSTemporalPrecisionRecord.create(TemporalConstants.MINUTE, Unit.MINUTE, 1.0);
        }
        if (Unit.SECOND == smallestUnit) {
            return JSTemporalPrecisionRecord.create(0, Unit.SECOND, 1.0);
        }
        if (Unit.MILLISECOND == smallestUnit) {
            return JSTemporalPrecisionRecord.create(3, Unit.MILLISECOND, 1.0);
        }
        if (Unit.MICROSECOND == smallestUnit) {
            return JSTemporalPrecisionRecord.create(6, Unit.MICROSECOND, 1.0);
        }
        if (Unit.NANOSECOND == smallestUnit) {
            return JSTemporalPrecisionRecord.create(9, Unit.NANOSECOND, 1.0);
        }
        assert (smallestUnit == Unit.EMPTY);
        Object digits = TemporalUtil.getStringOrNumberOption(options, FRACTIONAL_SECOND_DIGITS, listAuto, 0.0, 9.0, TemporalConstants.AUTO, toStringNode, getOptionNode);
        if (Boundaries.equals(digits, TemporalConstants.AUTO)) {
            return JSTemporalPrecisionRecord.create(TemporalConstants.AUTO, Unit.NANOSECOND, 1.0);
        }
        int iDigit = JSRuntime.intValue((Number)digits);
        if (iDigit == 0) {
            return JSTemporalPrecisionRecord.create(0, Unit.SECOND, 1.0);
        }
        if (iDigit == 1 || iDigit == 2 || iDigit == 3) {
            return JSTemporalPrecisionRecord.create(digits, Unit.MILLISECOND, Math.pow(10.0, 3L - TemporalUtil.toLong(digits)));
        }
        if (iDigit == 4 || iDigit == 5 || iDigit == 6) {
            return JSTemporalPrecisionRecord.create(digits, Unit.MICROSECOND, Math.pow(10.0, 6L - TemporalUtil.toLong(digits)));
        }
        assert (iDigit == 7 || iDigit == 8 || iDigit == 9);
        return JSTemporalPrecisionRecord.create(digits, Unit.NANOSECOND, Math.pow(10.0, 9L - TemporalUtil.toLong(digits)));
    }

    @CompilerDirectives.TruffleBoundary
    private static long toLong(Object digits) {
        if (digits instanceof Number) {
            return ((Number)digits).longValue();
        }
        return JSRuntime.toNumber(digits).longValue();
    }

    public static Unit toSmallestTemporalUnit(JSDynamicObject normalizedOptions, List<TruffleString> disallowedUnits, TruffleString fallback, TemporalGetOptionNode getOptionNode, TruffleString.EqualNode equalNode) {
        TruffleString smallestUnit = (TruffleString)getOptionNode.execute(normalizedOptions, TemporalConstants.SMALLEST_UNIT, OptionType.STRING, listAllDateTime, fallback);
        if (smallestUnit != null && Boundaries.setContains(pluralUnits, smallestUnit)) {
            smallestUnit = Boundaries.mapGet(pluralToSingular, smallestUnit);
        }
        if (smallestUnit != null && Boundaries.listContains(disallowedUnits, smallestUnit)) {
            throw Errors.createRangeError("Smallest unit not allowed.");
        }
        return TemporalUtil.toUnit(smallestUnit, equalNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalZonedDateTimeRecord parseTemporalRelativeToString(TruffleString isoString) {
        if (!new TemporalParser(isoString).isTemporalDateTimeString()) {
            throw TemporalErrors.createRangeErrorInvalidRelativeToString();
        }
        JSTemporalDateTimeRecord result = TemporalUtil.parseISODateTime(isoString, false, false);
        boolean z = false;
        TruffleString offsetString = null;
        TruffleString timeZone = null;
        if (!isoString.isEmpty()) {
            try {
                JSTemporalTimeZoneRecord timeZoneResult = TemporalUtil.parseTemporalTimeZoneString(isoString);
                z = timeZoneResult.isZ();
                offsetString = timeZoneResult.getOffsetString();
                timeZone = timeZoneResult.getName();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return JSTemporalZonedDateTimeRecord.create(result.getYear(), result.getMonth(), result.getDay(), result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), result.getNanosecond(), result.getCalendar(), z, offsetString, timeZone);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalDateTimeRecord parseTemporalMonthDayString(TruffleString string) {
        JSTemporalParserRecord rec = new TemporalParser(string).parseMonthDay();
        if (rec != null) {
            int d;
            if (rec.getZ()) {
                throw TemporalErrors.createRangeErrorUnexpectedUTCDesignator();
            }
            if (rec.getYear() == 0L && (Strings.indexOf(string, TemporalConstants.MINUS_000000) >= 0 || Strings.indexOf(string, TemporalConstants.UNICODE_MINUS_SIGN_000000) >= 0)) {
                throw TemporalErrors.createRangeErrorInvalidPlainDateTime();
            }
            int y = rec.getYear() == Long.MIN_VALUE ? Integer.MIN_VALUE : TemporalUtil.ltoi(rec.getYear());
            int m = rec.getMonth() == Long.MIN_VALUE ? 1 : TemporalUtil.ltoi(rec.getMonth());
            int n = d = rec.getDay() == Long.MIN_VALUE ? 1 : TemporalUtil.ltoi(rec.getDay());
            if (!TemporalUtil.isValidISODate(y, m, d)) {
                throw TemporalErrors.createRangeErrorDateOutsideRange();
            }
            return JSTemporalDateTimeRecord.createCalendar(y, m, d, 0, 0, 0, 0, 0, 0, rec.getCalendar());
        }
        throw Errors.createRangeError("cannot parse MonthDay");
    }

    private static JSTemporalDateTimeRecord parseISODateTime(TruffleString string) {
        return TemporalUtil.parseISODateTime(string, false, false);
    }

    @CompilerDirectives.TruffleBoundary
    private static JSTemporalDateTimeRecord parseISODateTime(TruffleString string, boolean failWithUTCDesignator, boolean timeExpected) {
        JSTemporalParserRecord rec = new TemporalParser(string).parseISODateTime();
        if (rec != null) {
            if (failWithUTCDesignator && rec.getZ()) {
                throw TemporalErrors.createRangeErrorUnexpectedUTCDesignator();
            }
            if (timeExpected && rec.getHour() == Long.MIN_VALUE) {
                throw Errors.createRangeError("cannot parse the ISO date time string");
            }
            return TemporalUtil.parseISODateTimeIntl(string, rec);
        }
        throw Errors.createRangeError("cannot parse the ISO date time string");
    }

    private static JSTemporalDateTimeRecord parseISODateTimeIntl(TruffleString string, JSTemporalParserRecord rec) {
        TruffleString fraction = rec.getFraction();
        fraction = fraction == null ? ZEROS : Strings.concat(fraction, ZEROS);
        if (rec.getYear() == 0L && (Strings.indexOf(string, TemporalConstants.MINUS_000000) >= 0 || Strings.indexOf(string, TemporalConstants.UNICODE_MINUS_SIGN_000000) >= 0)) {
            throw TemporalErrors.createRangeErrorInvalidPlainDateTime();
        }
        int y = rec.getYear() == Long.MIN_VALUE ? 0 : TemporalUtil.ltoi(rec.getYear());
        int m = rec.getMonth() == Long.MIN_VALUE ? 1 : TemporalUtil.ltoi(rec.getMonth());
        int d = rec.getDay() == Long.MIN_VALUE ? 1 : TemporalUtil.ltoi(rec.getDay());
        int h = rec.getHour() == Long.MIN_VALUE ? 0 : TemporalUtil.ltoi(rec.getHour());
        int min2 = rec.getMinute() == Long.MIN_VALUE ? 0 : TemporalUtil.ltoi(rec.getMinute());
        int s = rec.getSecond() == Long.MIN_VALUE ? 0 : TemporalUtil.ltoi(rec.getSecond());
        int ms = 0;
        int mus = 0;
        int ns = 0;
        try {
            ms = (int)Strings.parseLong(Strings.lazySubstring(fraction, 0, 3));
            mus = (int)Strings.parseLong(Strings.lazySubstring(fraction, 3, 3));
            ns = (int)Strings.parseLong(Strings.lazySubstring(fraction, 6, 3));
        }
        catch (TruffleString.NumberFormatException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
        if (s == 60) {
            s = 59;
        }
        if (!TemporalUtil.isValidISODate(y, m, d)) {
            throw TemporalErrors.createRangeErrorDateOutsideRange();
        }
        if (!TemporalUtil.isValidTime(h, min2, s, ms, mus, ns)) {
            throw TemporalErrors.createRangeErrorTimeOutsideRange();
        }
        return JSTemporalDateTimeRecord.createCalendar(y, m, d, h, min2, s, ms, mus, ns, rec.getCalendar());
    }

    public static void validateTemporalUnitRange(Unit largestUnit, Unit smallestUnit) {
        boolean error = false;
        switch (smallestUnit) {
            case YEAR: {
                if (largestUnit == Unit.YEAR) break;
                error = true;
                break;
            }
            case MONTH: {
                if (largestUnit == Unit.YEAR || largestUnit == Unit.MONTH) break;
                error = true;
                break;
            }
            case WEEK: {
                if (largestUnit == Unit.YEAR || largestUnit == Unit.MONTH || largestUnit == Unit.WEEK) break;
                error = true;
                break;
            }
            case DAY: {
                if (largestUnit == Unit.YEAR || largestUnit == Unit.MONTH || largestUnit == Unit.WEEK || largestUnit == Unit.DAY) break;
                error = true;
                break;
            }
            case HOUR: {
                if (largestUnit == Unit.YEAR || largestUnit == Unit.MONTH || largestUnit == Unit.WEEK || largestUnit == Unit.DAY || largestUnit == Unit.HOUR) break;
                error = true;
                break;
            }
            case MINUTE: {
                if (largestUnit != Unit.SECOND && largestUnit != Unit.MILLISECOND && largestUnit != Unit.MICROSECOND && largestUnit != Unit.NANOSECOND) break;
                error = true;
                break;
            }
            case SECOND: {
                if (largestUnit != Unit.MILLISECOND && largestUnit != Unit.MICROSECOND && largestUnit != Unit.NANOSECOND) break;
                error = true;
                break;
            }
            case MILLISECOND: {
                if (largestUnit != Unit.MICROSECOND && largestUnit != Unit.NANOSECOND) break;
                error = true;
                break;
            }
            case MICROSECOND: {
                if (largestUnit != Unit.NANOSECOND) break;
                error = true;
            }
        }
        if (error) {
            throw TemporalErrors.createRangeErrorSmallestUnitOutOfRange();
        }
    }

    public static Double maximumTemporalDurationRoundingIncrement(Unit unit) {
        if (unit == Unit.YEAR || unit == Unit.MONTH || unit == Unit.WEEK || unit == Unit.DAY) {
            return null;
        }
        if (unit == Unit.HOUR) {
            return 24.0;
        }
        if (unit == Unit.MINUTE || unit == Unit.SECOND) {
            return 60.0;
        }
        assert (unit == Unit.MILLISECOND || unit == Unit.MICROSECOND || unit == Unit.NANOSECOND);
        return 1000.0;
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString formatSecondsStringPart(long second, long millisecond, long microsecond, long nanosecond, Object precision) {
        if (precision.equals(TemporalConstants.MINUTE)) {
            return Strings.EMPTY_STRING;
        }
        TruffleString secondString = Strings.format(":%1$02d", second);
        long fraction = millisecond * 1000000L + microsecond * 1000L + nanosecond;
        TruffleString fractionString = Strings.EMPTY_STRING;
        if (precision.equals(TemporalConstants.AUTO)) {
            if (fraction == 0L) {
                return secondString;
            }
            fractionString = Strings.concatAll(fractionString, Strings.format("%1$03d", millisecond), Strings.format("%1$03d", microsecond), Strings.format("%1$03d", nanosecond));
            fractionString = TemporalUtil.longestSubstring(fractionString);
        } else {
            if (precision.equals(0)) {
                return secondString;
            }
            fractionString = Strings.concatAll(fractionString, Strings.format("%1$03d", millisecond), Strings.format("%1$03d", microsecond), Strings.format("%1$03d", nanosecond));
            fractionString = Strings.lazySubstring(fractionString, 0, (int)TemporalUtil.toLong(precision));
        }
        return Strings.concatAll(secondString, Strings.DOT, fractionString);
    }

    private static TruffleString longestSubstring(TruffleString str) {
        int length;
        for (length = Strings.length(str); length > 0 && Strings.charAt(str, length - 1) == '0'; --length) {
        }
        if (length == 0) {
            return Strings.EMPTY_STRING;
        }
        if (length == Strings.length(str)) {
            return str;
        }
        assert (Strings.length(str) <= 9);
        return Strings.lazySubstring(str, 0, length);
    }

    public static double nonNegativeModulo(double x, double y) {
        double result = x % y;
        if (result == 0.0) {
            return 0.0;
        }
        if (result < 0.0) {
            result += y;
        }
        return result;
    }

    public static int constrainToRange(long value2, int minimum, int maximum) {
        return (int)Math.min(Math.max(value2, (long)minimum), (long)maximum);
    }

    public static UnsignedRoundingMode getUnsignedRoundingMode(RoundingMode rm, boolean isNegative) {
        switch (rm) {
            case CEIL: {
                return isNegative ? UnsignedRoundingMode.ZERO : UnsignedRoundingMode.INFINITY;
            }
            case FLOOR: {
                return isNegative ? UnsignedRoundingMode.INFINITY : UnsignedRoundingMode.ZERO;
            }
            case EXPAND: {
                return UnsignedRoundingMode.INFINITY;
            }
            case TRUNC: {
                return UnsignedRoundingMode.ZERO;
            }
            case HALF_CEIL: {
                return isNegative ? UnsignedRoundingMode.HALF_ZERO : UnsignedRoundingMode.HALF_INFINITY;
            }
            case HALF_FLOOR: {
                return isNegative ? UnsignedRoundingMode.HALF_INFINITY : UnsignedRoundingMode.HALF_ZERO;
            }
            case HALF_EXPAND: {
                return UnsignedRoundingMode.HALF_INFINITY;
            }
            case HALF_TRUNC: {
                return UnsignedRoundingMode.HALF_ZERO;
            }
            case HALF_EVEN: {
                return UnsignedRoundingMode.HALF_EVEN;
            }
        }
        return UnsignedRoundingMode.EMPTY;
    }

    public static double applyUnsignedRoundingMode(double x, double r1, double r2, UnsignedRoundingMode urm) {
        if (x == r1) {
            return r1;
        }
        assert (r1 < x && x < r2);
        assert (urm != UnsignedRoundingMode.EMPTY);
        if (urm == UnsignedRoundingMode.ZERO) {
            return r1;
        }
        if (urm == UnsignedRoundingMode.INFINITY) {
            return r2;
        }
        double d1 = x - r1;
        double d2 = r2 - x;
        if (d1 < d2) {
            return r1;
        }
        if (d2 < d1) {
            return r2;
        }
        assert (d1 == d2);
        if (urm == UnsignedRoundingMode.HALF_ZERO) {
            return r1;
        }
        if (urm == UnsignedRoundingMode.HALF_INFINITY) {
            return r2;
        }
        assert (urm == UnsignedRoundingMode.HALF_EVEN);
        double cardinality = r1 / (r2 - r1) % 2.0;
        if (cardinality == 0.0) {
            return r1;
        }
        return r2;
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInteger roundNumberToIncrement(BigDecimal x, BigDecimal increment, RoundingMode roundingMode) {
        int sign;
        assert (roundingMode == RoundingMode.CEIL || roundingMode == RoundingMode.FLOOR || roundingMode == RoundingMode.TRUNC || roundingMode == RoundingMode.HALF_EXPAND);
        BigDecimal[] divRes = x.divideAndRemainder(increment);
        BigDecimal quotient = divRes[0];
        BigDecimal remainder = divRes[1];
        int n = sign = remainder.signum() < 0 ? -1 : 1;
        if (roundingMode == RoundingMode.CEIL) {
            if (sign > 0) {
                quotient = quotient.add(BigDecimal.ONE);
            }
        } else if (roundingMode == RoundingMode.FLOOR) {
            if (sign < 0) {
                quotient = quotient.add(BigDecimal.valueOf(-1L));
            }
        } else if (roundingMode != RoundingMode.TRUNC) {
            assert (roundingMode == RoundingMode.HALF_EXPAND);
            if (remainder.multiply(BigDecimal.valueOf(2L)).abs().compareTo(increment) >= 0) {
                quotient = quotient.add(BigDecimal.valueOf(sign));
            }
        }
        BigDecimal result = quotient.multiply(increment);
        return result.toBigInteger();
    }

    @CompilerDirectives.TruffleBoundary
    public static double roundNumberToIncrement(double x, double increment, RoundingMode roundingMode) {
        assert (roundingMode == RoundingMode.CEIL || roundingMode == RoundingMode.FLOOR || roundingMode == RoundingMode.TRUNC || roundingMode == RoundingMode.HALF_EXPAND);
        double quotient = x / increment;
        double rounded = 0.0;
        if (roundingMode == RoundingMode.CEIL) {
            rounded = -Math.floor(-quotient);
        } else if (roundingMode == RoundingMode.FLOOR) {
            rounded = Math.floor(quotient);
        } else if (roundingMode == RoundingMode.TRUNC) {
            rounded = quotient > 0.0 ? Math.floor(quotient) : Math.ceil(quotient);
        } else if (roundingMode == RoundingMode.HALF_EXPAND) {
            rounded = TemporalUtil.roundHalfAwayFromZero(quotient);
        }
        return rounded * increment;
    }

    @CompilerDirectives.TruffleBoundary
    public static double roundHalfAwayFromZero(double x) {
        if (x >= 0.0) {
            return Math.round(x);
        }
        return -Math.round(-x);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString parseTemporalCalendarString(TruffleString string) {
        JSTemporalParserRecord rec = new TemporalParser(string).parseCalendarString();
        if (rec == null) {
            throw Errors.createRangeError("cannot parse Calendar");
        }
        TruffleString id = rec.getCalendar();
        if (id == null) {
            return TemporalConstants.ISO8601;
        }
        return id;
    }

    public static double toPositiveInteger(Object value2) {
        double result = JSRuntime.doubleValue(TemporalUtil.toIntegerThrowOnInfinity(value2));
        if (result <= 0.0) {
            throw Errors.createRangeError("positive value expected");
        }
        return result;
    }

    public static int toPositiveIntegerConstrainInt(Object value2, JSToIntegerThrowOnInfinityNode toIntegerThrowOnInfinityNode, BranchProfile errorBranch) {
        int integer = toIntegerThrowOnInfinityNode.executeIntOrThrow(value2);
        if (integer <= 0) {
            errorBranch.enter();
            throw Errors.createRangeError("positive value expected");
        }
        return integer;
    }

    @CompilerDirectives.TruffleBoundary
    public static JSDynamicObject prepareTemporalFields(JSContext ctx, JSDynamicObject fields, List<TruffleString> fieldNames, List<TruffleString> requiredFields) {
        JSObject result = JSOrdinary.createWithNullPrototype(ctx);
        for (TruffleString property : fieldNames) {
            Object value2 = JSObject.get(fields, property);
            assert (value2 != null);
            if (value2 == Undefined.instance) {
                if (requiredFields.contains(property)) {
                    throw TemporalErrors.createTypeErrorPropertyRequired(property);
                }
                if (temporalFieldDefaults.containsKey(property)) {
                    value2 = temporalFieldDefaults.get(property);
                }
            } else if (temporalFieldConversion.containsKey(property)) {
                Function<Object, Object> conversion = temporalFieldConversion.get(property);
                value2 = conversion.apply(value2);
            }
            TemporalUtil.createDataPropertyOrThrow(ctx, result, property, value2);
        }
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    public static JSDynamicObject preparePartialTemporalFields(JSContext ctx, JSDynamicObject fields, List<TruffleString> fieldNames) {
        JSObject result = JSOrdinary.createWithNullPrototype(ctx);
        boolean any = false;
        for (TruffleString property : fieldNames) {
            Object value2 = JSObject.get(fields, property);
            assert (value2 != null);
            if (value2 != Undefined.instance) {
                any = true;
                if (temporalFieldConversion.containsKey(property)) {
                    Function<Object, Object> conversion = temporalFieldConversion.get(property);
                    value2 = conversion.apply(value2);
                }
            }
            TemporalUtil.createDataPropertyOrThrow(ctx, result, property, value2);
        }
        if (!any) {
            throw Errors.createTypeError("Given dateTime like object has no relevant properties.");
        }
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    private static <T, I> Map<T, I> toMap(T[] keys, I[] values) {
        HashMap<T, I> map = new HashMap<T, I>();
        for (int i = 0; i < keys.length; ++i) {
            map.put(keys[i], values[i]);
        }
        return map;
    }

    public static JSTemporalYearMonthDayRecord regulateISOYearMonth(int year, int month, Overflow overflow) {
        assert (Overflow.CONSTRAIN == overflow || Overflow.REJECT == overflow);
        if (Overflow.CONSTRAIN == overflow) {
            return TemporalUtil.constrainISOYearMonth(year, month);
        }
        assert (Overflow.REJECT == overflow);
        if (!TemporalUtil.isValidISOMonth(month)) {
            throw Errors.createRangeError("validation of year and month failed");
        }
        return JSTemporalYearMonthDayRecord.create(year, month);
    }

    private static boolean isValidISOMonth(int month) {
        return 1 <= month && month <= 12;
    }

    private static JSTemporalYearMonthDayRecord constrainISOYearMonth(int year, int month) {
        int monthPrepared = TemporalUtil.constrainToRange(month, 1, 12);
        return JSTemporalYearMonthDayRecord.create(TemporalUtil.ltoi(year), monthPrepared);
    }

    public static long toISODayOfWeek(int year, int month, int day) {
        int weekDay;
        int m = month - 2;
        if (m == -1) {
            m = 11;
        } else if (m == 0) {
            m = 12;
        }
        int c = Math.floorDiv(year, 100);
        int y = Math.floorMod(year, 100);
        if (m == 11 || m == 12) {
            --y;
        }
        if ((weekDay = Math.floorMod((long)((long)day + (long)Math.floor(2.6 * (double)m - 0.2) - (long)(2 * c) + (long)y + (long)Math.floorDiv(y, 4) + (long)Math.floorDiv(c, 4)), (int)7)) == 0) {
            return 7L;
        }
        return weekDay;
    }

    public static int toISODayOfYear(int year, int month, int day) {
        int days = 0;
        for (int m = 1; m < month; ++m) {
            days += TemporalUtil.isoDaysInMonth(year, m);
        }
        return days + day;
    }

    public static long toISOWeekOfYear(int year, int month, int day) {
        long doy = TemporalUtil.toISODayOfYear(year, month, day);
        long dow = TemporalUtil.toISODayOfWeek(year, month, day);
        long doj = TemporalUtil.toISODayOfWeek(year, 1, 1);
        long week = Math.floorDiv((long)(doy - dow + 10L), (int)7);
        if (week < 1L) {
            if (doj == 5L || doj == 6L && TemporalUtil.isISOLeapYear(year - 1)) {
                return 53L;
            }
            return 52L;
        }
        if (week == 53L && (long)TemporalUtil.isoDaysInYear(year) - doy < 4L - dow) {
            return 1L;
        }
        return week;
    }

    public static boolean isISOLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    public static int isoDaysInYear(int year) {
        if (TemporalUtil.isISOLeapYear(year)) {
            return 366;
        }
        return 365;
    }

    public static int isoDaysInMonth(int year, int month) {
        assert (month >= 1 && month <= 12);
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if (TemporalUtil.isISOLeapYear(year)) {
            return 29;
        }
        return 28;
    }

    public static JSTemporalDateTimeRecord balanceISOYearMonth(int year, int month) {
        if (year == Integer.MAX_VALUE || year == Integer.MIN_VALUE || month == Integer.MAX_VALUE || month == Integer.MIN_VALUE) {
            throw Errors.createRangeError("value out of range");
        }
        int yearPrepared = (int)((double)year + Math.floor(((double)month - 1.0) / 12.0));
        int monthPrepared = (int)TemporalUtil.nonNegativeModulo(month - 1, 12.0) + 1;
        return JSTemporalDateTimeRecord.create(yearPrepared, monthPrepared, 0, 0, 0, 0, 0, 0, 0);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isBuiltinCalendar(TruffleString id) {
        return id.equals(TemporalConstants.ISO8601) || id.equals(TemporalConstants.GREGORY) || id.equals(TemporalConstants.JAPANESE);
    }

    public static JSTemporalCalendarObject getISO8601Calendar(JSContext ctx, JSRealm realm, BranchProfile errorBranch) {
        return TemporalUtil.getBuiltinCalendar(TemporalConstants.ISO8601, ctx, realm, errorBranch);
    }

    public static JSTemporalCalendarObject getISO8601Calendar(JSContext ctx, JSRealm realm) {
        return TemporalUtil.getBuiltinCalendar(TemporalConstants.ISO8601, ctx, realm);
    }

    public static JSTemporalCalendarObject getBuiltinCalendar(TruffleString id, JSContext ctx, JSRealm realm, BranchProfile errorBranch) {
        if (!TemporalUtil.isBuiltinCalendar(id)) {
            errorBranch.enter();
            throw TemporalErrors.createRangeErrorCalendarNotSupported();
        }
        return JSTemporalCalendar.create(ctx, realm, id, errorBranch);
    }

    public static JSTemporalCalendarObject getBuiltinCalendar(TruffleString id, JSContext ctx, JSRealm realm) {
        if (!TemporalUtil.isBuiltinCalendar(id)) {
            throw TemporalErrors.createRangeErrorCalendarNotSupported();
        }
        return JSTemporalCalendar.create(ctx, realm, id);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSDynamicObject toTemporalCalendar(JSContext ctx, Object temporalCalendarLikeParam) {
        TruffleString identifier;
        Object temporalCalendarLike = temporalCalendarLikeParam;
        if (JSRuntime.isObject(temporalCalendarLike)) {
            JSDynamicObject tclObj;
            JSDynamicObject obj = TemporalUtil.toJSDynamicObject(temporalCalendarLike, null);
            if (temporalCalendarLike instanceof TemporalCalendar) {
                return ((TemporalCalendar)temporalCalendarLike).getCalendar();
            }
            if (!JSObject.hasProperty(obj, TemporalConstants.CALENDAR)) {
                return obj;
            }
            temporalCalendarLike = JSObject.get(obj, TemporalConstants.CALENDAR);
            if (JSRuntime.isObject(temporalCalendarLike) && !JSObject.hasProperty(tclObj = TemporalUtil.toJSDynamicObject(temporalCalendarLike, null), TemporalConstants.CALENDAR)) {
                return tclObj;
            }
        }
        if (!TemporalUtil.isBuiltinCalendar(identifier = JSRuntime.toString(temporalCalendarLike)) && !TemporalUtil.isBuiltinCalendar(identifier = TemporalUtil.parseTemporalCalendarString(identifier))) {
            throw TemporalErrors.createRangeErrorCalendarUnknown();
        }
        return JSTemporalCalendar.create(ctx, null, identifier);
    }

    @CompilerDirectives.TruffleBoundary
    public static List<TruffleString> iterableToListOfTypeString(JSDynamicObject items) {
        IteratorRecord iter = JSRuntime.getIterator(items);
        ArrayList<TruffleString> values = new ArrayList<TruffleString>();
        Object next = Boolean.TRUE;
        while (next != Boolean.FALSE) {
            next = JSRuntime.iteratorStep(iter);
            if (next == Boolean.FALSE) continue;
            Object nextValue = JSRuntime.iteratorValue((JSDynamicObject)next);
            if (!Strings.isTString(nextValue)) {
                JSRuntime.iteratorClose(iter.getIterator());
                throw Errors.createTypeError("string expected");
            }
            TruffleString str = JSRuntime.toString(nextValue);
            values.add(str);
        }
        return values;
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalDateTimeRecord parseTemporalDateTimeString(TruffleString string) {
        JSTemporalParserRecord rec = new TemporalParser(string).parseCalendarDateTime();
        if (rec == null) {
            throw Errors.createRangeError("cannot parse the date string");
        }
        if (rec.getZ()) {
            throw TemporalErrors.createRangeErrorUnexpectedUTCDesignator();
        }
        JSTemporalDateTimeRecord result = TemporalUtil.parseISODateTime(string, true, false);
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalDateTimeRecord parseTemporalDateString(TruffleString string) {
        JSTemporalDateTimeRecord rec = TemporalUtil.parseTemporalDateTimeString(string);
        return JSTemporalDateTimeRecord.createCalendar(rec.getYear(), rec.getMonth(), rec.getDay(), 0, 0, 0, 0, 0, 0, rec.getCalendar());
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalDateTimeRecord parseTemporalTimeString(TruffleString string) {
        JSTemporalDateTimeRecord result = TemporalUtil.parseISODateTime(string, true, true);
        if (result.hasCalendar()) {
            return JSTemporalDateTimeRecord.createCalendar(0, 0, 0, result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), result.getNanosecond(), result.getCalendar());
        }
        return JSTemporalDateTimeRecord.create(0, 0, 0, result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), result.getNanosecond());
    }

    @CompilerDirectives.TruffleBoundary
    public static Object buildISOMonthCode(int month) {
        TruffleString numberPart = Strings.fromInt(month);
        assert (1 <= Strings.length(numberPart) && Strings.length(numberPart) <= 2);
        return Strings.concat(Strings.length(numberPart) >= 2 ? Strings.UC_M : Strings.UC_M0, numberPart);
    }

    public static TruffleString isoMonthCode(TemporalMonth date) {
        long month = date.getMonth();
        return TemporalUtil.buildISOMonthCode(month);
    }

    @CompilerDirectives.TruffleBoundary
    private static TruffleString buildISOMonthCode(long month) {
        TruffleString monthCode = Strings.format("%1$02d", month);
        return Strings.concat(TemporalConstants.M, monthCode);
    }

    public static JSDynamicObject toTemporalTimeZone(JSContext ctx, Object temporalTimeZoneLikeParam) {
        TruffleString identifier;
        JSTemporalTimeZoneRecord parseResult;
        Object temporalTimeZoneLike = temporalTimeZoneLikeParam;
        if (JSRuntime.isObject(temporalTimeZoneLike)) {
            JSDynamicObject tzObj = TemporalUtil.toJSDynamicObject(temporalTimeZoneLike, null);
            if (TemporalUtil.isTemporalZonedDateTime(tzObj)) {
                return ((JSTemporalZonedDateTimeObject)tzObj).getTimeZone();
            }
            if (!JSObject.hasProperty(tzObj, TemporalConstants.TIME_ZONE)) {
                return tzObj;
            }
            temporalTimeZoneLike = JSObject.get(tzObj, TemporalConstants.TIME_ZONE);
            if (JSRuntime.isObject(temporalTimeZoneLike) && !JSObject.hasProperty(tzObj = TemporalUtil.toJSDynamicObject(temporalTimeZoneLike, null), TemporalConstants.TIME_ZONE)) {
                return tzObj;
            }
        }
        if ((parseResult = TemporalUtil.parseTemporalTimeZoneString(identifier = JSRuntime.toString(temporalTimeZoneLike))).getName() != null) {
            boolean canParse = TemporalUtil.canParseAsTimeZoneNumericUTCOffset(parseResult.getName());
            if (canParse ? parseResult.getOffsetString() != null && TemporalUtil.parseTimeZoneOffsetString(parseResult.getOffsetString()) != TemporalUtil.parseTimeZoneOffsetString(parseResult.getName()) : !TemporalUtil.isValidTimeZoneName(parseResult.getName())) {
                throw TemporalErrors.createRangeErrorInvalidTimeZoneString();
            }
            return TemporalUtil.createTemporalTimeZone(ctx, TemporalUtil.canonicalizeTimeZoneName(parseResult.getName()));
        }
        if (parseResult.isZ()) {
            return TemporalUtil.createTemporalTimeZone(ctx, TemporalConstants.UTC);
        }
        return TemporalUtil.createTemporalTimeZone(ctx, parseResult.getOffsetString());
    }

    public static JSDynamicObject createTemporalTimeZone(JSContext ctx, TruffleString identifier) {
        BigInt offsetNs;
        TruffleString newIdentifier = identifier;
        try {
            long result = TemporalUtil.parseTimeZoneOffsetString(identifier);
            newIdentifier = TemporalUtil.formatTimeZoneOffsetString(result);
            offsetNs = BigInt.valueOf(result);
        }
        catch (Exception ex) {
            assert (TemporalUtil.canonicalizeTimeZoneName(identifier).equals(identifier));
            offsetNs = null;
        }
        return JSTemporalTimeZone.create(ctx, offsetNs, newIdentifier);
    }

    public static TruffleString canonicalizeTimeZoneName(TruffleString timeZone) {
        assert (TemporalUtil.isValidTimeZoneName(timeZone));
        return Strings.fromJavaString(JSDateTimeFormat.canonicalizeTimeZoneName(timeZone));
    }

    public static boolean isValidTimeZoneName(TruffleString timeZone) {
        return JSDateTimeFormat.canonicalizeTimeZoneName(timeZone) != null;
    }

    @CompilerDirectives.TruffleBoundary
    public static double getDouble(JSDynamicObject ob, TruffleString key, double defaultValue) {
        Object value2 = JSObject.get(ob, key);
        if (value2 == Undefined.instance) {
            return defaultValue;
        }
        Number n = (Number)value2;
        return n.longValue();
    }

    public static boolean isoDateTimeWithinLimits(int year, int month, int day, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond) {
        if (-270000 <= year && year <= 270000) {
            return true;
        }
        return TemporalUtil.isoDateTimeWithinLimitsIntl(year, month, day, hour, minute, second, millisecond, microsecond, nanosecond);
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean isoDateTimeWithinLimitsIntl(int year, int month, int day, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond) {
        BigInteger ns = TemporalUtil.getEpochFromISOParts(year, month, day, hour, minute, second, millisecond, microsecond, nanosecond);
        return ns.compareTo(isoTimeLowerBound) > 0 && ns.compareTo(isoTimeUpperBound) < 0;
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInteger getEpochFromISOParts(int year, int month, int day, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond) {
        double time;
        assert (TemporalUtil.isValidISODate(year, month, day));
        assert (TemporalUtil.isValidTime(hour, minute, second, millisecond, microsecond, nanosecond));
        double date = JSDate.makeDay(year, month - 1, day);
        double ms = JSDate.makeDate(date, time = JSDate.makeTime(hour, minute, second, millisecond));
        if (Double.isNaN(ms)) {
            throw TemporalErrors.createRangeErrorDateOutsideRange();
        }
        assert (TemporalUtil.isFinite(ms));
        BigInteger bi = BigInteger.valueOf((long)ms).multiply(BI_10_POW_6);
        BigInteger bims = BigInteger.valueOf(microsecond).multiply(BI_1000);
        BigInteger biresult = bi.add(bims).add(BigInteger.valueOf(nanosecond));
        return biresult;
    }

    private static boolean isFinite(double d) {
        return !Double.isNaN(d) && !Double.isInfinite(d);
    }

    public static Overflow toTemporalOverflow(JSDynamicObject options, TemporalGetOptionNode getOptionNode) {
        if (options == Undefined.instance) {
            return Overflow.CONSTRAIN;
        }
        TruffleString result = (TruffleString)getOptionNode.execute(options, TemporalConstants.OVERFLOW, OptionType.STRING, listConstrainReject, TemporalConstants.CONSTRAIN);
        return TemporalUtil.toOverflow(result);
    }

    @CompilerDirectives.TruffleBoundary
    private static Overflow toOverflow(TruffleString result) {
        if (TemporalConstants.CONSTRAIN.equals(result)) {
            return Overflow.CONSTRAIN;
        }
        if (TemporalConstants.REJECT.equals(result)) {
            return Overflow.REJECT;
        }
        CompilerDirectives.transferToInterpreter();
        throw Errors.shouldNotReachHere("unknown overflow type: " + result);
    }

    public static JSTemporalDateTimeRecord interpretTemporalDateTimeFields(JSDynamicObject calendar, JSDynamicObject fields, JSDynamicObject options, TemporalGetOptionNode getOptionNode, TemporalCalendarDateFromFieldsNode dateFromFieldsNode) {
        JSTemporalDateTimeRecord timeResult = TemporalUtil.toTemporalTimeRecord(fields);
        JSTemporalPlainDateObject date = dateFromFieldsNode.execute(calendar, fields, options);
        Overflow overflow = TemporalUtil.toTemporalOverflow(options, getOptionNode);
        JSTemporalDurationRecord timeResult2 = TemporalUtil.regulateTime(timeResult.getHour(), timeResult.getMinute(), timeResult.getSecond(), timeResult.getMillisecond(), timeResult.getMicrosecond(), timeResult.getNanosecond(), overflow);
        return JSTemporalDateTimeRecord.create(date.getYear(), date.getMonth(), date.getDay(), TemporalUtil.dtoi(timeResult2.getHours()), TemporalUtil.dtoi(timeResult2.getMinutes()), TemporalUtil.dtoi(timeResult2.getSeconds()), TemporalUtil.dtoi(timeResult2.getMilliseconds()), TemporalUtil.dtoi(timeResult2.getMicroseconds()), TemporalUtil.dtoi(timeResult2.getNanoseconds()));
    }

    public static JSTemporalDurationRecord regulateTime(double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds, Overflow overflow) {
        assert (overflow == Overflow.CONSTRAIN || overflow == Overflow.REJECT);
        if (overflow == Overflow.CONSTRAIN) {
            return TemporalUtil.constrainTime(TemporalUtil.dtoi(hours), TemporalUtil.dtoi(minutes), TemporalUtil.dtoi(seconds), TemporalUtil.dtoi(milliseconds), TemporalUtil.dtoi(microseconds), TemporalUtil.dtoi(nanoseconds));
        }
        if (!TemporalUtil.isValidTime(TemporalUtil.dtoi(hours), TemporalUtil.dtoi(minutes), TemporalUtil.dtoi(seconds), TemporalUtil.dtoi(milliseconds), TemporalUtil.dtoi(microseconds), TemporalUtil.dtoi(nanoseconds))) {
            throw Errors.createRangeError("Given time outside the range.");
        }
        return JSTemporalDurationRecord.create(0.0, 0.0, 0.0, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
    }

    public static JSTemporalDurationRecord regulateTime(int hours, int minutes, int seconds, int milliseconds, int microseconds, int nanoseconds, Overflow overflow) {
        assert (overflow == Overflow.CONSTRAIN || overflow == Overflow.REJECT);
        if (overflow == Overflow.CONSTRAIN) {
            return TemporalUtil.constrainTime(hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
        }
        if (!TemporalUtil.isValidTime(hours, minutes, seconds, milliseconds, microseconds, nanoseconds)) {
            throw Errors.createRangeError("Given time outside the range.");
        }
        return JSTemporalDurationRecord.create(0.0, 0.0, 0.0, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
    }

    public static JSTemporalDurationRecord constrainTime(int hours, int minutes, int seconds, int milliseconds, int microseconds, int nanoseconds) {
        return JSTemporalDurationRecord.create(0.0, 0.0, 0.0, TemporalUtil.constrainToRange(hours, 0, 23), TemporalUtil.constrainToRange(minutes, 0, 59), TemporalUtil.constrainToRange(seconds, 0, 59), TemporalUtil.constrainToRange(milliseconds, 0, 999), TemporalUtil.constrainToRange(microseconds, 0, 999), TemporalUtil.constrainToRange(nanoseconds, 0, 999));
    }

    public static JSTemporalDateTimeRecord toTemporalTimeRecord(JSDynamicObject temporalTimeLike) {
        boolean any = false;
        int hour = 0;
        int minute = 0;
        int second = 0;
        int millisecond = 0;
        int microsecond = 0;
        int nanosecond = 0;
        for (TruffleString property : TIME_LIKE_PROPERTIES) {
            Object val = JSObject.get(temporalTimeLike, property);
            int iVal = 0;
            if (val == Undefined.instance) {
                iVal = 0;
            } else {
                any = true;
                iVal = JSRuntime.intValue(TemporalUtil.toIntegerThrowOnInfinity(val));
            }
            if (TemporalConstants.HOUR.equals(property)) {
                hour = iVal;
                continue;
            }
            if (TemporalConstants.MINUTE.equals(property)) {
                minute = iVal;
                continue;
            }
            if (TemporalConstants.SECOND.equals(property)) {
                second = iVal;
                continue;
            }
            if (TemporalConstants.MILLISECOND.equals(property)) {
                millisecond = iVal;
                continue;
            }
            if (TemporalConstants.MICROSECOND.equals(property)) {
                microsecond = iVal;
                continue;
            }
            if (!TemporalConstants.NANOSECOND.equals(property)) continue;
            nanosecond = iVal;
        }
        if (!any) {
            throw Errors.createTypeError("at least one time-like field expected");
        }
        return JSTemporalDateTimeRecord.create(0, 0, 0, hour, minute, second, millisecond, microsecond, nanosecond);
    }

    public static Number toIntegerThrowOnInfinity(Object value2) {
        Number integer = TemporalUtil.toIntegerOrInfinity(value2);
        if (Double.isInfinite(JSRuntime.doubleValue(integer))) {
            throw Errors.createRangeError("value outside bounds");
        }
        return integer;
    }

    public static double toIntegerWithoutRounding(Object argument) {
        Number number = JSRuntime.toNumber(argument);
        double dNumber = JSRuntime.doubleValue(number);
        if (Double.isNaN(dNumber) || dNumber == 0.0) {
            return 0.0;
        }
        if (!JSRuntime.isIntegralNumber(dNumber)) {
            throw Errors.createRangeError("value expected to be integer");
        }
        return dNumber;
    }

    @CompilerDirectives.TruffleBoundary
    public static Number toIntegerOrInfinity(Object value2) {
        Number number = JSRuntime.toNumber(value2);
        double d = number.doubleValue();
        if (d == 0.0 || Double.isNaN(d)) {
            return 0L;
        }
        if (Double.isInfinite(d)) {
            return d;
        }
        return number;
    }

    public static JSDynamicObject calendarDateAdd(JSDynamicObject calendar, JSDynamicObject datePart, JSDynamicObject dateDuration, JSDynamicObject options) {
        return TemporalUtil.calendarDateAdd(calendar, datePart, dateDuration, options, Undefined.instance);
    }

    public static JSTemporalPlainDateObject calendarDateAdd(JSDynamicObject calendar, JSDynamicObject date, JSDynamicObject duration, JSDynamicObject options, Object dateAdd) {
        Object dateAddPrepared = dateAdd;
        if (dateAddPrepared == Undefined.instance) {
            dateAddPrepared = JSObject.getMethod(calendar, TemporalConstants.DATE_ADD);
        }
        Object addedDate = JSRuntime.call(dateAddPrepared, calendar, new Object[]{date, duration, options});
        return TemporalUtil.requireTemporalDate(addedDate);
    }

    public static JSTemporalDurationObject calendarDateUntil(JSDynamicObject calendar, JSDynamicObject one, JSDynamicObject two, JSDynamicObject options) {
        return TemporalUtil.calendarDateUntil(calendar, one, two, options, Undefined.instance);
    }

    public static JSTemporalDurationObject calendarDateUntil(JSDynamicObject calendar, JSDynamicObject one, JSDynamicObject two, JSDynamicObject options, Object dateUntil) {
        Object dateUntilPrepared = dateUntil;
        if (dateUntilPrepared == Undefined.instance) {
            dateUntilPrepared = JSObject.getMethod(calendar, TemporalConstants.DATE_UNTIL);
        }
        Object date = JSRuntime.call(dateUntilPrepared, calendar, new Object[]{one, two, options});
        return TemporalUtil.requireTemporalDuration(date);
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInteger roundTemporalInstant(BigInt ns, double increment, Unit unit, RoundingMode roundingMode) {
        return TemporalUtil.roundTemporalInstant(new BigDecimal(ns.bigIntegerValue()), increment, unit, roundingMode);
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInteger roundTemporalInstant(BigDecimal ns, double increment, Unit unit, RoundingMode roundingMode) {
        BigDecimal incrementNs = BigDecimal.valueOf(increment);
        if (Unit.HOUR == unit) {
            incrementNs = incrementNs.multiply(BigDecimal.valueOf(3600000000000L));
        } else if (Unit.MINUTE == unit) {
            incrementNs = incrementNs.multiply(BigDecimal.valueOf(60000000000L));
        } else if (Unit.SECOND == unit) {
            incrementNs = incrementNs.multiply(BigDecimal.valueOf(1000000000L));
        } else if (Unit.MILLISECOND == unit) {
            incrementNs = incrementNs.multiply(BigDecimal.valueOf(1000000L));
        } else if (Unit.MICROSECOND == unit) {
            incrementNs = incrementNs.multiply(BigDecimal.valueOf(1000L));
        } else assert (Unit.NANOSECOND == unit);
        return TemporalUtil.roundNumberToIncrement(ns, incrementNs, roundingMode);
    }

    public static boolean validateISODate(int year, int month, int day) {
        if (month < 1 || month > 12) {
            return false;
        }
        long daysInMonth = TemporalUtil.isoDaysInMonth(year, month);
        return 1 <= day && (long)day <= daysInMonth;
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalDateTimeRecord regulateISODate(int yearParam, int monthParam, int dayParam, Overflow overflow) {
        assert (overflow == Overflow.CONSTRAIN || overflow == Overflow.REJECT);
        int month = monthParam;
        int day = dayParam;
        if (overflow == Overflow.REJECT) {
            if (!TemporalUtil.isValidISODate(yearParam, month, day)) {
                throw TemporalErrors.createRangeErrorDateOutsideRange();
            }
        } else {
            assert (overflow == Overflow.CONSTRAIN);
            month = TemporalUtil.constrainToRange(month, 1, 12);
            day = TemporalUtil.constrainToRange(day, 1, TemporalUtil.isoDaysInMonth(yearParam, month));
        }
        return JSTemporalDateTimeRecord.create(yearParam, month, day, 0, 0, 0, 0, 0, 0);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalDateTimeRecord balanceISODate(int yearParam, int monthParam, int dayParam) {
        double epochDays = JSDate.makeDay(yearParam, monthParam - 1, dayParam);
        assert (Double.isFinite(epochDays));
        double ms = JSDate.makeDate(epochDays, 0.0);
        return JSTemporalPlainDate.toRecord(JSDate.yearFromTime((long)ms), JSDate.monthFromTime(ms) + 1, JSDate.dateFromTime(ms));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalDateTimeRecord addISODate(int year, int month, int day, int years, int months, int weeks, int daysP, Overflow overflow) {
        assert (overflow == Overflow.CONSTRAIN || overflow == Overflow.REJECT);
        int days = daysP;
        JSTemporalDateTimeRecord intermediate = TemporalUtil.balanceISOYearMonth(TemporalUtil.add(year, years, overflow), TemporalUtil.add(month, months, overflow));
        intermediate = TemporalUtil.regulateISODate(intermediate.getYear(), intermediate.getMonth(), day, overflow);
        int d = TemporalUtil.add(intermediate.getDay(), days += 7 * weeks, overflow);
        intermediate = TemporalUtil.balanceISODate(intermediate.getYear(), intermediate.getMonth(), d);
        return TemporalUtil.regulateISODate(intermediate.getYear(), intermediate.getMonth(), intermediate.getDay(), overflow);
    }

    public static int compareISODate(int y1, int m1, int d1, int y2, int m2, int d2) {
        if (y1 > y2) {
            return 1;
        }
        if (y1 < y2) {
            return -1;
        }
        if (m1 > m2) {
            return 1;
        }
        if (m1 < m2) {
            return -1;
        }
        if (d1 > d2) {
            return 1;
        }
        if (d1 < d2) {
            return -1;
        }
        return 0;
    }

    public static JSTemporalPlainDateObject requireTemporalDate(Object obj, BranchProfile errorBranch) {
        if (!(obj instanceof JSTemporalPlainDateObject)) {
            errorBranch.enter();
            throw TemporalErrors.createTypeErrorTemporalDateExpected();
        }
        return (JSTemporalPlainDateObject)obj;
    }

    public static JSTemporalPlainDateObject requireTemporalDate(Object obj) {
        if (!(obj instanceof JSTemporalPlainDateObject)) {
            throw TemporalErrors.createTypeErrorTemporalDateExpected();
        }
        return (JSTemporalPlainDateObject)obj;
    }

    public static JSTemporalDurationObject requireTemporalDuration(Object obj) {
        if (!(obj instanceof JSTemporalDurationObject)) {
            throw TemporalErrors.createTypeErrorTemporalDurationExpected();
        }
        return (JSTemporalDurationObject)obj;
    }

    public static boolean isTemporalZonedDateTime(Object obj) {
        return JSTemporalZonedDateTime.isJSTemporalZonedDateTime(obj);
    }

    public static ShowCalendar toShowCalendarOption(JSDynamicObject options, TemporalGetOptionNode getOptionNode, TruffleString.EqualNode equalNode) {
        return TemporalUtil.toShowCalendar((TruffleString)getOptionNode.execute(options, CALENDAR_NAME, OptionType.STRING, listAutoAlwaysNever, TemporalConstants.AUTO), equalNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString padISOYear(int year) {
        if (0 <= year && year <= 9999) {
            return Strings.format("%1$04d", year);
        }
        TruffleString sign = year > 0 ? Strings.SYMBOL_PLUS : Strings.SYMBOL_MINUS;
        long y = Math.abs(year);
        return Strings.concat(sign, Strings.format("%1$06d", y));
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString formatCalendarAnnotation(TruffleString id, ShowCalendar showCalendar) {
        if (ShowCalendar.NEVER == showCalendar) {
            return Strings.EMPTY_STRING;
        }
        if (ShowCalendar.AUTO == showCalendar && TemporalConstants.ISO8601.equals(id)) {
            return Strings.EMPTY_STRING;
        }
        return Strings.concatAll(BRACKET_U_CA_EQUALS, id, Strings.BRACKET_CLOSE);
    }

    public static RoundingMode negateTemporalRoundingMode(RoundingMode roundingMode) {
        if (RoundingMode.CEIL == roundingMode) {
            return RoundingMode.FLOOR;
        }
        if (RoundingMode.FLOOR == roundingMode) {
            return RoundingMode.CEIL;
        }
        return roundingMode;
    }

    public static boolean calendarEquals(JSDynamicObject one, JSDynamicObject two, JSToStringNode toStringNode) {
        if (one == two) {
            return true;
        }
        return Boundaries.equals(toStringNode.executeString(one), toStringNode.executeString(two));
    }

    public static void rejectTemporalCalendarType(JSDynamicObject obj, BranchProfile errorBranch) {
        if (obj instanceof JSTemporalPlainDateObject || obj instanceof JSTemporalPlainDateTimeObject || obj instanceof JSTemporalPlainMonthDayObject || obj instanceof JSTemporalPlainTimeObject || obj instanceof JSTemporalPlainYearMonthObject || TemporalUtil.isTemporalZonedDateTime(obj)) {
            errorBranch.enter();
            throw Errors.createTypeError("rejecting calendar types");
        }
    }

    public static double remainder(double x, double y) {
        double magnitude = x % y;
        return magnitude;
    }

    public static double getPropertyFromRecord(JSTemporalDurationRecord d, UnitPlural unit) {
        switch (unit) {
            case YEARS: {
                return d.getYears();
            }
            case MONTHS: {
                return d.getMonths();
            }
            case WEEKS: {
                return d.getWeeks();
            }
            case DAYS: {
                return d.getDays();
            }
            case HOURS: {
                return d.getHours();
            }
            case MINUTES: {
                return d.getMinutes();
            }
            case SECONDS: {
                return d.getSeconds();
            }
            case MILLISECONDS: {
                return d.getMilliseconds();
            }
            case MICROSECONDS: {
                return d.getMicroseconds();
            }
            case NANOSECONDS: {
                return d.getNanoseconds();
            }
        }
        CompilerDirectives.transferToInterpreter();
        throw Errors.createTypeError("unknown property");
    }

    public static JSDynamicObject calendarMergeFields(JSContext ctx, EnumerableOwnPropertyNamesNode namesNode, BranchProfile errorBranch, JSDynamicObject calendar, JSDynamicObject fields, JSDynamicObject additionalFields) {
        Object mergeFields = JSObject.getMethod(calendar, TemporalConstants.MERGE_FIELDS);
        if (mergeFields == Undefined.instance) {
            return TemporalUtil.defaultMergeFields(ctx, namesNode, fields, additionalFields);
        }
        Object result = JSRuntime.call(mergeFields, calendar, new Object[]{fields, additionalFields});
        if (!JSRuntime.isObject(result)) {
            throw TemporalErrors.createTypeErrorObjectExpected();
        }
        return TemporalUtil.toJSDynamicObject(result, errorBranch);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSDynamicObject defaultMergeFields(JSContext ctx, EnumerableOwnPropertyNamesNode namesNode, JSDynamicObject fields, JSDynamicObject additionalFields) {
        JSRealm realm = JSRealm.get(null);
        JSObject merged = JSOrdinary.create(ctx, realm);
        UnmodifiableArrayList<? extends Object> originalKeys = namesNode.execute(fields);
        for (Object object : originalKeys) {
            Object propValue;
            if (TemporalConstants.MONTH.equals(object) || TemporalConstants.MONTH_CODE.equals(object) || (propValue = JSObject.get(fields, object)) == Undefined.instance) continue;
            TemporalUtil.createDataPropertyOrThrow(ctx, merged, JSRuntime.toString(object), propValue);
        }
        boolean hasMonthOrMonthCode = false;
        UnmodifiableArrayList<? extends Object> unmodifiableArrayList = namesNode.execute(additionalFields);
        for (Object object : unmodifiableArrayList) {
            Object propValue = JSObject.get(additionalFields, object);
            if (propValue == Undefined.instance) continue;
            TemporalUtil.createDataPropertyOrThrow(ctx, merged, JSRuntime.toString(object), propValue);
            if (!TemporalConstants.MONTH.equals(object) && !TemporalConstants.MONTH_CODE.equals(object)) continue;
            hasMonthOrMonthCode = true;
        }
        if (!hasMonthOrMonthCode) {
            Object object;
            Object month = JSObject.get(fields, TemporalConstants.MONTH);
            if (month != Undefined.instance) {
                TemporalUtil.createDataPropertyOrThrow(ctx, merged, TemporalConstants.MONTH, month);
            }
            if ((object = JSObject.get(fields, TemporalConstants.MONTH_CODE)) != Undefined.instance) {
                TemporalUtil.createDataPropertyOrThrow(ctx, merged, TemporalConstants.MONTH_CODE, object);
            }
        }
        return merged;
    }

    public static void createDataPropertyOrThrow(JSContext ctx, JSDynamicObject obj, TruffleString key, Object value2) {
        JSObjectUtil.defineDataProperty(ctx, obj, key, value2, JSAttributes.configurableEnumerableWritable());
    }

    @CompilerDirectives.TruffleBoundary
    public static List<TruffleString> listJoinRemoveDuplicates(List<TruffleString> first, List<TruffleString> second) {
        ArrayList<TruffleString> newList = new ArrayList<TruffleString>(first.size() + second.size());
        newList.addAll(first);
        for (TruffleString elem : second) {
            if (first.contains(elem)) continue;
            newList.add(elem);
        }
        return newList;
    }

    public static Unit largerOfTwoTemporalUnits(Unit a, Unit b) {
        if (Unit.YEAR == a || Unit.YEAR == b) {
            return Unit.YEAR;
        }
        if (Unit.MONTH == a || Unit.MONTH == b) {
            return Unit.MONTH;
        }
        if (Unit.WEEK == a || Unit.WEEK == b) {
            return Unit.WEEK;
        }
        if (Unit.DAY == a || Unit.DAY == b) {
            return Unit.DAY;
        }
        if (Unit.HOUR == a || Unit.HOUR == b) {
            return Unit.HOUR;
        }
        if (Unit.MINUTE == a || Unit.MINUTE == b) {
            return Unit.MINUTE;
        }
        if (Unit.SECOND == a || Unit.SECOND == b) {
            return Unit.SECOND;
        }
        if (Unit.MILLISECOND == a || Unit.MILLISECOND == b) {
            return Unit.MILLISECOND;
        }
        if (Unit.MICROSECOND == a || Unit.MICROSECOND == b) {
            return Unit.MICROSECOND;
        }
        return Unit.NANOSECOND;
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalDurationRecord differenceISODateTime(JSContext ctx, EnumerableOwnPropertyNamesNode namesNode, int y1, int mon1, int d1, int h1, int min1, int s1, int ms1, int mus1, int ns1, int y2, int mon2, int d2, int h2, int min2, int s2, int ms2, int mus2, int ns2, JSDynamicObject calendar, Unit largestUnit, JSDynamicObject options) {
        assert (options != null);
        JSTemporalDurationRecord timeDifference = TemporalUtil.differenceTime(h1, min1, s1, ms1, mus1, ns1, h2, min2, s2, ms2, mus2, ns2);
        int timeSign = TemporalUtil.durationSign(0.0, 0.0, 0.0, timeDifference.getDays(), timeDifference.getHours(), timeDifference.getMinutes(), timeDifference.getSeconds(), timeDifference.getMilliseconds(), timeDifference.getMicroseconds(), timeDifference.getNanoseconds());
        int dateSign = TemporalUtil.compareISODate(y2, mon2, d2, y1, mon1, d1);
        JSTemporalDateTimeRecord balanceResult = TemporalUtil.balanceISODate(TemporalUtil.dtoi(y1), TemporalUtil.dtoi(mon1), TemporalUtil.dtoi(d1) + TemporalUtil.dtoi(timeDifference.getDays()));
        if (timeSign == -dateSign) {
            balanceResult = TemporalUtil.balanceISODate(balanceResult.getYear(), balanceResult.getMonth(), balanceResult.getDay() - timeSign);
            timeDifference = TemporalUtil.balanceDuration(ctx, namesNode, -timeSign, timeDifference.getHours(), timeDifference.getMinutes(), timeDifference.getSeconds(), timeDifference.getMilliseconds(), timeDifference.getMicroseconds(), timeDifference.getNanoseconds(), largestUnit);
        }
        JSTemporalPlainDateObject date1 = JSTemporalPlainDate.create(ctx, balanceResult.getYear(), balanceResult.getMonth(), balanceResult.getDay(), calendar);
        JSTemporalPlainDateObject date2 = JSTemporalPlainDate.create(ctx, y2, mon2, d2, calendar);
        Unit dateLargestUnit = TemporalUtil.largerOfTwoTemporalUnits(Unit.DAY, largestUnit);
        JSDynamicObject untilOptions = TemporalUtil.mergeLargestUnitOption(ctx, namesNode, options, dateLargestUnit);
        JSTemporalDurationObject dateDifference = TemporalUtil.calendarDateUntil(calendar, date1, date2, untilOptions, Undefined.instance);
        JSTemporalDurationRecord result = TemporalUtil.balanceDuration(ctx, namesNode, dateDifference.getDays(), timeDifference.getHours(), timeDifference.getMinutes(), timeDifference.getSeconds(), timeDifference.getMilliseconds(), timeDifference.getMicroseconds(), timeDifference.getNanoseconds(), largestUnit);
        return JSTemporalDurationRecord.createWeeks(dateDifference.getYears(), dateDifference.getMonths(), dateDifference.getWeeks(), result.getDays(), result.getHours(), result.getMinutes(), result.getSeconds(), result.getMilliseconds(), result.getMicroseconds(), result.getNanoseconds());
    }

    @CompilerDirectives.TruffleBoundary
    public static JSDynamicObject mergeLargestUnitOption(JSContext ctx, EnumerableOwnPropertyNamesNode namesNode, JSDynamicObject options, Unit largestUnit) {
        JSObject merged = JSOrdinary.createWithNullPrototype(ctx);
        UnmodifiableArrayList<? extends Object> keys = namesNode.execute(options);
        for (Object object : keys) {
            if (!(object instanceof TruffleString)) continue;
            TruffleString key = (TruffleString)object;
            Object propValue = JSObject.get(options, key);
            TemporalUtil.createDataPropertyOrThrow(ctx, merged, key, propValue);
        }
        TemporalUtil.createDataPropertyOrThrow(ctx, merged, TemporalConstants.LARGEST_UNIT, largestUnit.toTruffleString());
        return merged;
    }

    public static int durationSign(double years, double months, double weeks, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds) {
        if (years < 0.0) {
            return -1;
        }
        if (years > 0.0) {
            return 1;
        }
        if (months < 0.0) {
            return -1;
        }
        if (months > 0.0) {
            return 1;
        }
        if (weeks < 0.0) {
            return -1;
        }
        if (weeks > 0.0) {
            return 1;
        }
        if (days < 0.0) {
            return -1;
        }
        if (days > 0.0) {
            return 1;
        }
        if (hours < 0.0) {
            return -1;
        }
        if (hours > 0.0) {
            return 1;
        }
        if (minutes < 0.0) {
            return -1;
        }
        if (minutes > 0.0) {
            return 1;
        }
        if (seconds < 0.0) {
            return -1;
        }
        if (seconds > 0.0) {
            return 1;
        }
        if (milliseconds < 0.0) {
            return -1;
        }
        if (milliseconds > 0.0) {
            return 1;
        }
        if (microseconds < 0.0) {
            return -1;
        }
        if (microseconds > 0.0) {
            return 1;
        }
        if (nanoseconds < 0.0) {
            return -1;
        }
        if (nanoseconds > 0.0) {
            return 1;
        }
        return 0;
    }

    @CompilerDirectives.TruffleBoundary
    public static void rejectDurationSign(double years, double months, double weeks, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds) {
        long sign = TemporalUtil.durationSign(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
        if (years < 0.0 && sign > 0L) {
            throw Errors.createRangeError("Years is negative but it should be positive.");
        }
        if (years > 0.0 && sign < 0L) {
            throw Errors.createRangeError("Years is positive but it should be negative.");
        }
        if (months < 0.0 && sign > 0L) {
            throw Errors.createRangeError("Months is negative but it should be positive.");
        }
        if (months > 0.0 && sign < 0L) {
            throw Errors.createRangeError("Months is positive but it should be negative.");
        }
        if (weeks < 0.0 && sign > 0L) {
            throw Errors.createRangeError("Weeks is negative but it should be positive.");
        }
        if (weeks > 0.0 && sign < 0L) {
            throw Errors.createRangeError("Weeks is positive but it should be negative.");
        }
        if (days < 0.0 && sign > 0L) {
            throw Errors.createRangeError("Days is negative but it should be positive.");
        }
        if (days > 0.0 && sign < 0L) {
            throw Errors.createRangeError("Days is positive but it should be negative.");
        }
        if (hours < 0.0 && sign > 0L) {
            throw Errors.createRangeError("Hours is negative but it should be positive.");
        }
        if (hours > 0.0 && sign < 0L) {
            throw Errors.createRangeError("Hours is positive but it should be negative.");
        }
        if (minutes < 0.0 && sign > 0L) {
            throw Errors.createRangeError("Minutes is negative but it should be positive.");
        }
        if (minutes > 0.0 && sign < 0L) {
            throw Errors.createRangeError("Minutes is positive but it should be negative.");
        }
        if (seconds < 0.0 && sign > 0L) {
            throw Errors.createRangeError("Seconds is negative but it should be positive.");
        }
        if (seconds > 0.0 && sign < 0L) {
            throw Errors.createRangeError("Seconds is positive but it should be negative.");
        }
        if (milliseconds < 0.0 && sign > 0L) {
            throw Errors.createRangeError("Milliseconds is negative but it should be positive.");
        }
        if (milliseconds > 0.0 && sign < 0L) {
            throw Errors.createRangeError("Milliseconds is positive but it should be negative.");
        }
        if (microseconds < 0.0 && sign > 0L) {
            throw Errors.createRangeError("Microseconds is negative but it should be positive.");
        }
        if (microseconds > 0.0 && sign < 0L) {
            throw Errors.createRangeError("Microseconds is positive but it should be negative.");
        }
        if (nanoseconds < 0.0 && sign > 0L) {
            throw Errors.createRangeError("Nanoseconds is negative but it should be positive.");
        }
        if (nanoseconds > 0.0 && sign < 0L) {
            throw Errors.createRangeError("Nanoseconds is positive but it should be negative.");
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalDurationRecord balanceDuration(JSContext ctx, EnumerableOwnPropertyNamesNode namesNode, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds, Unit largestUnit) {
        return TemporalUtil.balanceDuration(ctx, namesNode, days, hours, minutes, seconds, milliseconds, microseconds, BigInteger.valueOf(TemporalUtil.dtol(nanoseconds)), largestUnit, Undefined.instance);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalDurationRecord balanceDuration(JSContext ctx, EnumerableOwnPropertyNamesNode namesNode, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, BigInteger nanoseconds, Unit largestUnit, JSDynamicObject relativeTo) {
        double d;
        BigInt nsBi;
        if (TemporalUtil.isTemporalZonedDateTime(relativeTo)) {
            JSTemporalZonedDateTimeObject zdt = (JSTemporalZonedDateTimeObject)relativeTo;
            BigInt endNs = TemporalUtil.addZonedDateTime(ctx, zdt.getNanoseconds(), zdt.getTimeZone(), zdt.getCalendar(), 0L, 0L, 0L, TemporalUtil.dtol(days, true), TemporalUtil.dtol(hours, true), TemporalUtil.dtol(minutes, true), TemporalUtil.dtol(seconds, true), TemporalUtil.dtol(milliseconds, true), TemporalUtil.dtol(microseconds, true), nanoseconds, Undefined.instance);
            nsBi = endNs.subtract(zdt.getNanoseconds());
        } else {
            nsBi = new BigInt(TemporalUtil.totalDurationNanoseconds(days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds));
        }
        if (largestUnit == Unit.YEAR || largestUnit == Unit.MONTH || largestUnit == Unit.WEEK || largestUnit == Unit.DAY) {
            JSTemporalNanosecondsDaysRecord result = TemporalUtil.nanosecondsToDays(ctx, namesNode, nsBi, relativeTo);
            d = TemporalUtil.bitod(result.getDays());
            nsBi = new BigInt(result.getNanoseconds());
        } else {
            d = 0.0;
        }
        double h = 0.0;
        double min2 = 0.0;
        double s = 0.0;
        double ms = 0.0;
        double mus = 0.0;
        BigInteger nsBi2 = nsBi.bigIntegerValue();
        double sign = nsBi2.compareTo(BigInteger.ZERO) < 0 ? -1.0 : 1.0;
        nsBi2 = nsBi2.abs();
        if (largestUnit == Unit.YEAR || largestUnit == Unit.MONTH || largestUnit == Unit.WEEK || largestUnit == Unit.DAY || largestUnit == Unit.HOUR) {
            BigInteger[] res = nsBi2.divideAndRemainder(BI_1000);
            mus = TemporalUtil.bitod(res[0]);
            nsBi2 = res[1];
            ms = Math.floor(mus / 1000.0);
            mus %= 1000.0;
            s = Math.floor(ms / 1000.0);
            ms %= 1000.0;
            min2 = Math.floor(s / 60.0);
            s %= 60.0;
            h = Math.floor(min2 / 60.0);
            min2 %= 60.0;
        } else if (largestUnit == Unit.MINUTE) {
            BigInteger[] res = nsBi2.divideAndRemainder(BI_1000);
            mus = TemporalUtil.bitod(res[0]);
            nsBi2 = res[1];
            ms = Math.floor(mus / 1000.0);
            mus %= 1000.0;
            s = Math.floor(ms / 1000.0);
            ms %= 1000.0;
            min2 = Math.floor(s / 60.0);
            s %= 60.0;
        } else if (largestUnit == Unit.SECOND) {
            BigInteger[] res = nsBi2.divideAndRemainder(BI_1000);
            mus = TemporalUtil.bitod(res[0]);
            nsBi2 = res[1];
            ms = Math.floor(mus / 1000.0);
            mus %= 1000.0;
            s = Math.floor(ms / 1000.0);
            ms %= 1000.0;
        } else if (largestUnit == Unit.MILLISECOND) {
            BigInteger[] res = nsBi2.divideAndRemainder(BI_1000);
            mus = TemporalUtil.bitod(res[0]);
            nsBi2 = res[1];
            ms = Math.floor(mus / 1000.0);
            mus %= 1000.0;
        } else if (largestUnit == Unit.MICROSECOND) {
            BigInteger[] res = nsBi2.divideAndRemainder(BI_1000);
            mus = TemporalUtil.bitod(res[0]);
            nsBi2 = res[1];
        } else assert (largestUnit == Unit.NANOSECOND);
        return JSTemporalDurationRecord.create(0.0, 0.0, d, h * sign, min2 * sign, s * sign, ms * sign, mus * sign, sign < 0.0 ? TemporalUtil.bitod(nsBi2.negate()) : TemporalUtil.bitod(nsBi2));
    }

    public static JSDynamicObject toDynamicObject(Object obj) {
        if (obj instanceof JSDynamicObject) {
            return (JSDynamicObject)obj;
        }
        throw Errors.createTypeErrorNotAnObject(obj);
    }

    public static JSDynamicObject toJSDynamicObject(Object item, BranchProfile errorBranch) {
        if (item instanceof JSDynamicObject) {
            return (JSDynamicObject)item;
        }
        if (errorBranch != null) {
            errorBranch.enter();
        }
        throw Errors.createTypeError("Interop types not supported in Temporal");
    }

    public static boolean doubleIsInteger(double l) {
        return Math.rint(l) == l;
    }

    public static JSTemporalDurationRecord differenceZonedDateTime(JSContext ctx, EnumerableOwnPropertyNamesNode namesNode, BigInt ns1, BigInt ns2, JSDynamicObject timeZone, JSDynamicObject calendar, Unit largestUnit) {
        return TemporalUtil.differenceZonedDateTime(ctx, namesNode, ns1, ns2, timeZone, calendar, largestUnit, Undefined.instance);
    }

    public static JSTemporalDurationRecord differenceZonedDateTime(JSContext ctx, EnumerableOwnPropertyNamesNode namesNode, BigInt ns1, BigInt ns2, JSDynamicObject timeZone, JSDynamicObject calendar, Unit largestUnit, JSDynamicObject options) {
        if (ns1.equals(ns2)) {
            return JSTemporalDurationRecord.createWeeks(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        }
        JSTemporalInstantObject startInstant = JSTemporalInstant.create(ctx, ns1);
        JSTemporalPlainDateTimeObject startDateTime = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(ctx, timeZone, startInstant, calendar);
        JSTemporalInstantObject endInstant = JSTemporalInstant.create(ctx, ns2);
        JSTemporalPlainDateTimeObject endDateTime = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(ctx, timeZone, endInstant, calendar);
        JSTemporalDurationRecord dateDifference = TemporalUtil.differenceISODateTime(ctx, namesNode, startDateTime.getYear(), startDateTime.getMonth(), startDateTime.getDay(), startDateTime.getHour(), startDateTime.getMinute(), startDateTime.getSecond(), startDateTime.getMillisecond(), startDateTime.getMicrosecond(), startDateTime.getNanosecond(), endDateTime.getYear(), endDateTime.getMonth(), endDateTime.getDay(), endDateTime.getHour(), endDateTime.getMinute(), endDateTime.getSecond(), endDateTime.getMillisecond(), endDateTime.getMicrosecond(), endDateTime.getNanosecond(), calendar, largestUnit, options);
        BigInt intermediateNs = TemporalUtil.addZonedDateTime(ctx, ns1, timeZone, calendar, TemporalUtil.dtol(dateDifference.getYears()), TemporalUtil.dtol(dateDifference.getMonths()), TemporalUtil.dtol(dateDifference.getWeeks()), 0L, 0L, 0L, 0L, 0L, 0L, 0L);
        BigInt timeRemainderNs = ns2.subtract(intermediateNs);
        JSTemporalZonedDateTimeObject intermediate = JSTemporalZonedDateTime.create(ctx, intermediateNs, timeZone, calendar);
        JSTemporalNanosecondsDaysRecord result = TemporalUtil.nanosecondsToDays(ctx, namesNode, timeRemainderNs, intermediate);
        JSTemporalDurationRecord timeDifference = TemporalUtil.balanceDuration(ctx, namesNode, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, result.getNanoseconds(), Unit.HOUR, Undefined.instance);
        return JSTemporalDurationRecord.createWeeks(dateDifference.getYears(), dateDifference.getMonths(), dateDifference.getWeeks(), TemporalUtil.bitod(result.getDays()), timeDifference.getHours(), timeDifference.getMinutes(), timeDifference.getSeconds(), timeDifference.getMilliseconds(), timeDifference.getMicroseconds(), timeDifference.getNanoseconds());
    }

    public static boolean isValidDuration(double years, double months, double weeks, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds) {
        int sign = TemporalUtil.durationSign(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
        if (years < 0.0 && sign > 0) {
            return false;
        }
        if (years > 0.0 && sign < 0) {
            return false;
        }
        if (months < 0.0 && sign > 0) {
            return false;
        }
        if (months > 0.0 && sign < 0) {
            return false;
        }
        if (weeks < 0.0 && sign > 0) {
            return false;
        }
        if (weeks > 0.0 && sign < 0) {
            return false;
        }
        if (days < 0.0 && sign > 0) {
            return false;
        }
        if (days > 0.0 && sign < 0) {
            return false;
        }
        if (hours < 0.0 && sign > 0) {
            return false;
        }
        if (hours > 0.0 && sign < 0) {
            return false;
        }
        if (minutes < 0.0 && sign > 0) {
            return false;
        }
        if (minutes > 0.0 && sign < 0) {
            return false;
        }
        if (seconds < 0.0 && sign > 0) {
            return false;
        }
        if (seconds > 0.0 && sign < 0) {
            return false;
        }
        if (milliseconds < 0.0 && sign > 0) {
            return false;
        }
        if (milliseconds > 0.0 && sign < 0) {
            return false;
        }
        if (microseconds < 0.0 && sign > 0) {
            return false;
        }
        if (microseconds > 0.0 && sign < 0) {
            return false;
        }
        if (nanoseconds < 0.0 && sign > 0) {
            return false;
        }
        return !(nanoseconds > 0.0) || sign >= 0;
    }

    public static Unit defaultTemporalLargestUnit(double years, double months, double weeks, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds) {
        if (years != 0.0) {
            return Unit.YEAR;
        }
        if (months != 0.0) {
            return Unit.MONTH;
        }
        if (weeks != 0.0) {
            return Unit.WEEK;
        }
        if (days != 0.0) {
            return Unit.DAY;
        }
        if (hours != 0.0) {
            return Unit.HOUR;
        }
        if (minutes != 0.0) {
            return Unit.MINUTE;
        }
        if (seconds != 0.0) {
            return Unit.SECOND;
        }
        if (milliseconds != 0.0) {
            return Unit.MILLISECOND;
        }
        if (microseconds != 0.0) {
            return Unit.MICROSECOND;
        }
        return Unit.NANOSECOND;
    }

    public static JSDynamicObject toPartialDuration(Object temporalDurationLike, JSContext ctx, IsObjectNode isObjectNode, JSToIntegerWithoutRoundingNode toInt, BranchProfile errorBranch) {
        if (!isObjectNode.executeBoolean(temporalDurationLike)) {
            errorBranch.enter();
            throw Errors.createTypeError("Given duration like is not a object.");
        }
        JSDynamicObject temporalDurationLikeObj = TemporalUtil.toJSDynamicObject(temporalDurationLike, errorBranch);
        JSRealm realm = JSRealm.get(null);
        JSObject result = JSOrdinary.create(ctx, realm);
        boolean any = false;
        for (UnitPlural unit : DURATION_PROPERTIES) {
            Object value2 = JSObject.get(temporalDurationLikeObj, unit.toTruffleString());
            if (value2 == Undefined.instance) continue;
            any = true;
            JSObjectUtil.putDataProperty(ctx, result, (Object)unit.toTruffleString(), toInt.executeDouble(value2));
        }
        if (!any) {
            errorBranch.enter();
            throw Errors.createTypeError("Given duration like object has no duration properties.");
        }
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    public static double roundDurationFractionalSecondsSubtract(double seconds, BigDecimal fractionalSeconds) {
        return fractionalSeconds.subtract(BigDecimal.valueOf(seconds)).doubleValue();
    }

    @CompilerDirectives.TruffleBoundary
    public static double roundDurationFractionalDecondsDiv60(BigDecimal fractionalSeconds) {
        return fractionalSeconds.divide(BigDecimal.valueOf(60L), mc_20_floor).doubleValue();
    }

    @CompilerDirectives.TruffleBoundary
    public static BigDecimal roundDurationCalculateFractionalSeconds(double seconds, double microseconds, double milliseconds, double nanoseconds) {
        BigDecimal part1 = BigDecimal.valueOf(nanoseconds).multiply(BD_10_POW_M_9);
        BigDecimal part2 = BigDecimal.valueOf(microseconds).multiply(BD_10_POW_M_6);
        BigDecimal part3 = BigDecimal.valueOf(milliseconds).multiply(BD_10_POW_M_3);
        return part1.add(part2).add(part3).add(BigDecimal.valueOf(seconds));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalNanosecondsDaysRecord nanosecondsToDays(JSContext ctx, EnumerableOwnPropertyNamesNode namesNode, BigInt nanosecondsParam, JSDynamicObject relativeTo) {
        BigInteger nanoseconds = nanosecondsParam.bigIntegerValue();
        long sign = nanoseconds.signum();
        BigInteger signBI = BigInteger.valueOf(sign);
        BigInteger dayLengthNs = BI_8_64_13;
        if (sign == 0L) {
            return JSTemporalNanosecondsDaysRecord.create(BigInteger.ZERO, BigInteger.ZERO, dayLengthNs);
        }
        if (!TemporalUtil.isTemporalZonedDateTime(relativeTo)) {
            BigInteger val = nanoseconds.divide(dayLengthNs);
            BigInteger val2 = nanoseconds.abs().mod(dayLengthNs).multiply(signBI);
            return JSTemporalNanosecondsDaysRecord.create(val, val2, dayLengthNs);
        }
        JSTemporalZonedDateTimeObject relativeZDT = (JSTemporalZonedDateTimeObject)relativeTo;
        BigInt startNs = relativeZDT.getNanoseconds();
        JSTemporalInstantObject startInstant = JSTemporalInstant.create(ctx, startNs);
        JSTemporalPlainDateTimeObject startDateTime = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(ctx, relativeZDT.getTimeZone(), startInstant, relativeZDT.getCalendar());
        BigInt endNs = startNs.add(nanosecondsParam);
        JSTemporalInstantObject endInstant = JSTemporalInstant.create(ctx, endNs);
        JSTemporalPlainDateTimeObject endDateTime = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(ctx, relativeZDT.getTimeZone(), endInstant, relativeZDT.getCalendar());
        JSTemporalDurationRecord dateDifference = TemporalUtil.differenceISODateTime(ctx, namesNode, startDateTime.getYear(), startDateTime.getMonth(), startDateTime.getDay(), startDateTime.getHour(), startDateTime.getMinute(), startDateTime.getSecond(), startDateTime.getMillisecond(), startDateTime.getMicrosecond(), startDateTime.getNanosecond(), endDateTime.getYear(), endDateTime.getMonth(), endDateTime.getDay(), endDateTime.getHour(), endDateTime.getMinute(), endDateTime.getSecond(), endDateTime.getMillisecond(), endDateTime.getMicrosecond(), endDateTime.getNanosecond(), relativeZDT.getCalendar(), Unit.DAY, Undefined.instance);
        long days = TemporalUtil.dtol(dateDifference.getDays());
        BigInt intermediateNs = TemporalUtil.addZonedDateTime(ctx, startNs, relativeZDT.getTimeZone(), relativeZDT.getCalendar(), 0L, 0L, 0L, days, 0L, 0L, 0L, 0L, 0L, 0L);
        if (sign == 1L) {
            while (days > 0L && intermediateNs.compareTo(endNs) > 0) {
                intermediateNs = TemporalUtil.addZonedDateTime(ctx, startNs, relativeZDT.getTimeZone(), relativeZDT.getCalendar(), 0L, 0L, 0L, --days, 0L, 0L, 0L, 0L, 0L, 0L);
            }
        }
        nanoseconds = endNs.subtract(intermediateNs).bigIntegerValue();
        boolean done = false;
        while (!done) {
            BigInteger oneDayFartherNs = TemporalUtil.addZonedDateTime(ctx, intermediateNs, relativeZDT.getTimeZone(), relativeZDT.getCalendar(), 0L, 0L, 0L, sign, 0L, 0L, 0L, 0L, 0L, 0L).bigIntegerValue();
            dayLengthNs = oneDayFartherNs.subtract(intermediateNs.bigIntegerValue());
            if (nanoseconds.subtract(dayLengthNs).multiply(signBI).compareTo(BigInteger.ZERO) >= 0) {
                nanoseconds = nanoseconds.subtract(dayLengthNs);
                intermediateNs = new BigInt(oneDayFartherNs);
                days += sign;
                continue;
            }
            done = true;
        }
        return JSTemporalNanosecondsDaysRecord.create(BigInteger.valueOf(days), nanoseconds, dayLengthNs.abs());
    }

    public static JSTemporalDurationRecord adjustRoundedDurationDays(JSContext ctx, EnumerableOwnPropertyNamesNode namesNode, TemporalDurationAddNode durationAddNode, double years, double months, double weeks, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds, double increment, Unit unit, RoundingMode roundingMode, JSDynamicObject relativeToParam) {
        if (!TemporalUtil.isTemporalZonedDateTime(relativeToParam) || unit == Unit.YEAR || unit == Unit.MONTH || unit == Unit.WEEK || unit == Unit.DAY || unit == Unit.NANOSECOND && increment == 1.0) {
            return JSTemporalDurationRecord.createWeeks(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
        }
        JSTemporalZonedDateTimeObject relativeTo = (JSTemporalZonedDateTimeObject)relativeToParam;
        long timeRemainderNs = TemporalUtil.dtol(TemporalUtil.totalDurationNanoseconds(0.0, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, 0.0));
        long direction = Long.signum(timeRemainderNs);
        BigInt dayStart = TemporalUtil.addZonedDateTime(ctx, relativeTo.getNanoseconds(), relativeTo.getTimeZone(), relativeTo.getCalendar(), TemporalUtil.dtol(years), TemporalUtil.dtol(months), TemporalUtil.dtol(weeks), TemporalUtil.dtol(days), 0L, 0L, 0L, 0L, 0L, 0L);
        BigInt dayEnd = TemporalUtil.addZonedDateTime(ctx, dayStart, relativeTo.getTimeZone(), relativeTo.getCalendar(), 0L, 0L, 0L, direction, 0L, 0L, 0L, 0L, 0L, 0L);
        long dayLengthNs = TemporalUtil.bigIntToLong(dayEnd.subtract(dayStart));
        if ((timeRemainderNs - dayLengthNs) * direction < 0L) {
            return JSTemporalDurationRecord.createWeeks(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
        }
        BigInteger timeRemainderNsBi = TemporalUtil.roundTemporalInstant(Boundaries.bigDecimalValueOf(timeRemainderNs - dayLengthNs), increment, unit, roundingMode);
        JSTemporalDurationRecord add2 = durationAddNode.execute(TemporalUtil.dtol(years), TemporalUtil.dtol(months), TemporalUtil.dtol(weeks), TemporalUtil.dtol(days), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, direction, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, relativeToParam);
        JSTemporalDurationRecord atd = TemporalUtil.balanceDuration(ctx, namesNode, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, timeRemainderNsBi, Unit.HOUR, Undefined.instance);
        return JSTemporalDurationRecord.createWeeks(add2.getYears(), add2.getMonths(), add2.getWeeks(), add2.getDays(), atd.getHours(), atd.getMinutes(), atd.getSeconds(), atd.getMilliseconds(), atd.getMicroseconds(), atd.getNanoseconds());
    }

    public static double totalDurationNanoseconds(double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds, double offsetShift) {
        double ns = nanoseconds;
        if (days != 0.0) {
            ns -= offsetShift;
        }
        double h = hours + days * 24.0;
        double min2 = minutes + h * 60.0;
        double s = seconds + min2 * 60.0;
        double ms = milliseconds + s * 1000.0;
        double mus = microseconds + ms * 1000.0;
        return ns + mus * 1000.0;
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInteger totalDurationNanoseconds(double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, BigInteger nanoseconds) {
        double h = hours + days * 24.0;
        double min2 = minutes + h * 60.0;
        double s = seconds + min2 * 60.0;
        double ms = milliseconds + s * 1000.0;
        double mus = microseconds + ms * 1000.0;
        return nanoseconds.add(BigDecimal.valueOf(mus).toBigInteger().multiply(BI_1000));
    }

    @CompilerDirectives.TruffleBoundary
    public static double calculateOffsetShift(JSContext ctx, JSDynamicObject relativeTo, double y, double mon, double w, double d, double h, double min2, double s, double ms, double mus, double ns) {
        if (!TemporalUtil.isTemporalZonedDateTime(relativeTo)) {
            return 0.0;
        }
        JSTemporalZonedDateTimeObject relativeToZDT = (JSTemporalZonedDateTimeObject)relativeTo;
        JSTemporalInstantObject instant = JSTemporalInstant.create(ctx, relativeToZDT.getNanoseconds());
        long offsetBefore = TemporalUtil.getOffsetNanosecondsFor(relativeToZDT.getTimeZone(), instant);
        BigInt after2 = TemporalUtil.addZonedDateTime(ctx, relativeToZDT.getNanoseconds(), relativeToZDT.getTimeZone(), relativeToZDT.getCalendar(), TemporalUtil.dtol(y), TemporalUtil.dtol(mon), TemporalUtil.dtol(w), TemporalUtil.dtol(d), TemporalUtil.dtol(h), TemporalUtil.dtol(min2), TemporalUtil.dtol(s), TemporalUtil.dtol(ms), TemporalUtil.dtol(mus), TemporalUtil.dtol(ns));
        JSTemporalInstantObject instantAfter = JSTemporalInstant.create(ctx, after2);
        long offsetAfter = TemporalUtil.getOffsetNanosecondsFor(relativeToZDT.getTimeZone(), instantAfter);
        return offsetAfter - offsetBefore;
    }

    @CompilerDirectives.TruffleBoundary
    public static long daysUntil(JSDynamicObject earlier, JSDynamicObject later) {
        double epochDays1 = JSDate.makeDay(((TemporalYear)((Object)earlier)).getYear(), ((TemporalMonth)((Object)earlier)).getMonth() - 1, ((TemporalDay)((Object)earlier)).getDay());
        assert (Double.isFinite(epochDays1));
        double epochDays2 = JSDate.makeDay(((TemporalYear)((Object)later)).getYear(), ((TemporalMonth)((Object)later)).getMonth() - 1, ((TemporalDay)((Object)later)).getDay());
        assert (Double.isFinite(epochDays2));
        return TemporalUtil.dtol(epochDays2 - epochDays1);
    }

    public static JSTemporalDurationRecord differenceTime(int h1, int min1, int s1, int ms1, int mus1, int ns1, int h2, int min2, int s2, int ms2, int mus2, int ns2) {
        int hours = h2 - h1;
        int minutes = min2 - min1;
        int seconds = s2 - s1;
        int milliseconds = ms2 - ms1;
        int microseconds = mus2 - mus1;
        int nanoseconds = ns2 - ns1;
        int sign = TemporalUtil.durationSign(0.0, 0.0, 0.0, 0.0, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
        JSTemporalDurationRecord bt = TemporalUtil.balanceTime(hours * sign, minutes * sign, seconds * sign, milliseconds * sign, microseconds * sign, nanoseconds * sign);
        return JSTemporalDurationRecord.create(0.0, 0.0, bt.getDays() * (double)sign, bt.getHours() * (double)sign, bt.getMinutes() * (double)sign, bt.getSeconds() * (double)sign, bt.getMilliseconds() * (double)sign, bt.getMicroseconds() * (double)sign, bt.getNanoseconds() * (double)sign);
    }

    public static JSTemporalDurationRecord roundTime(int hours, int minutes, int seconds, int milliseconds, int microseconds, int nanoseconds, double increment, Unit unit, RoundingMode roundingMode, Long dayLengthNsParam) {
        double quantity;
        double fractionalSecond = (double)nanoseconds / 1.0E9 + (double)microseconds / 1000000.0 + (double)milliseconds / 1000.0 + (double)seconds;
        if (unit == Unit.DAY) {
            long dayLengthNs = dayLengthNsParam == null ? 86300000000000L : dayLengthNsParam;
            quantity = (double)(((((hours * 60 + minutes) * 60 + seconds) * 1000 + milliseconds) * 1000 + microseconds) * 1000 + nanoseconds) / (double)dayLengthNs;
        } else if (unit == Unit.HOUR) {
            quantity = (fractionalSecond / 60.0 + (double)minutes) / 60.0 + (double)hours;
        } else if (unit == Unit.MINUTE) {
            quantity = fractionalSecond / 60.0 + (double)minutes;
        } else if (unit == Unit.SECOND) {
            quantity = fractionalSecond;
        } else if (unit == Unit.MILLISECOND) {
            quantity = (double)nanoseconds / 1000000.0 + (double)microseconds / 1000.0 + (double)milliseconds;
        } else if (unit == Unit.MICROSECOND) {
            quantity = (double)nanoseconds / 1000.0 + (double)microseconds;
        } else {
            assert (unit == Unit.NANOSECOND);
            quantity = nanoseconds;
        }
        long result = TemporalUtil.dtol(TemporalUtil.roundNumberToIncrement(quantity, increment, roundingMode));
        if (unit == Unit.DAY) {
            return JSTemporalDurationRecord.create(0.0, 0.0, result, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        }
        if (unit == Unit.HOUR) {
            return TemporalUtil.balanceTime(result, 0L, 0L, 0L, 0L, 0L);
        }
        if (unit == Unit.MINUTE) {
            return TemporalUtil.balanceTime(hours, result, 0L, 0L, 0L, 0L);
        }
        if (unit == Unit.SECOND) {
            return TemporalUtil.balanceTime(hours, minutes, result, 0L, 0L, 0L);
        }
        if (unit == Unit.MILLISECOND) {
            return TemporalUtil.balanceTime(hours, minutes, seconds, result, 0L, 0L);
        }
        if (unit == Unit.MICROSECOND) {
            return TemporalUtil.balanceTime(hours, minutes, seconds, milliseconds, result, 0L);
        }
        assert (unit == Unit.NANOSECOND);
        return TemporalUtil.balanceTime(hours, minutes, seconds, milliseconds, microseconds, result);
    }

    public static JSTemporalDurationRecord balanceTimeDouble(double h, double min2, double sec, double mils, double mics, double ns) {
        if (h == Double.POSITIVE_INFINITY || h == Double.NEGATIVE_INFINITY || min2 == Double.POSITIVE_INFINITY || min2 == Double.NEGATIVE_INFINITY || sec == Double.POSITIVE_INFINITY || sec == Double.NEGATIVE_INFINITY || mils == Double.POSITIVE_INFINITY || mils == Double.NEGATIVE_INFINITY || mics == Double.POSITIVE_INFINITY || mics == Double.NEGATIVE_INFINITY || ns == Double.POSITIVE_INFINITY || ns == Double.NEGATIVE_INFINITY) {
            throw Errors.createRangeError("Time is infinite");
        }
        double microseconds = mics;
        double milliseconds = mils;
        double nanoseconds = ns;
        double seconds = sec;
        double minutes = min2;
        double hours = h;
        microseconds += Math.floor(nanoseconds / 1000.0);
        nanoseconds = TemporalUtil.nonNegativeModulo(nanoseconds, 1000.0);
        milliseconds += Math.floor(microseconds / 1000.0);
        microseconds = TemporalUtil.nonNegativeModulo(microseconds, 1000.0);
        seconds += Math.floor(milliseconds / 1000.0);
        milliseconds = TemporalUtil.nonNegativeModulo(milliseconds, 1000.0);
        minutes += Math.floor(seconds / 60.0);
        seconds = TemporalUtil.nonNegativeModulo(seconds, 60.0);
        hours += Math.floor(minutes / 60.0);
        minutes = TemporalUtil.nonNegativeModulo(minutes, 60.0);
        double days = Math.floor(hours / 24.0);
        hours = TemporalUtil.nonNegativeModulo(hours, 24.0);
        return JSTemporalDurationRecord.create(0.0, 0.0, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
    }

    public static JSTemporalDurationRecord balanceTime(long h, long min2, long sec, long mils, long mics, long ns) {
        long microseconds = mics;
        long milliseconds = mils;
        long nanoseconds = ns;
        long seconds = sec;
        long minutes = min2;
        long hours = h;
        microseconds += (long)Math.floor((double)nanoseconds / 1000.0);
        nanoseconds = (long)TemporalUtil.nonNegativeModulo(nanoseconds, 1000.0);
        milliseconds += (long)Math.floor((double)microseconds / 1000.0);
        microseconds = (long)TemporalUtil.nonNegativeModulo(microseconds, 1000.0);
        seconds += (long)Math.floor((double)milliseconds / 1000.0);
        milliseconds = (long)TemporalUtil.nonNegativeModulo(milliseconds, 1000.0);
        minutes += (long)Math.floor((double)seconds / 60.0);
        seconds = (long)TemporalUtil.nonNegativeModulo(seconds, 60.0);
        hours += (long)Math.floor((double)minutes / 60.0);
        minutes = (long)TemporalUtil.nonNegativeModulo(minutes, 60.0);
        long days = (long)Math.floor((double)hours / 24.0);
        hours = (long)TemporalUtil.nonNegativeModulo(hours, 24.0);
        return JSTemporalDurationRecord.create(0.0, 0.0, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
    }

    public static int compareTemporalTime(int h1, int min1, int s1, int ms1, int mus1, int ns1, int h2, int min2, int s2, int ms2, int mus2, int ns2) {
        if (h1 > h2) {
            return 1;
        }
        if (h1 < h2) {
            return -1;
        }
        if (min1 > min2) {
            return 1;
        }
        if (min1 < min2) {
            return -1;
        }
        if (s1 > s2) {
            return 1;
        }
        if (s1 < s2) {
            return -1;
        }
        if (ms1 > ms2) {
            return 1;
        }
        if (ms1 < ms2) {
            return -1;
        }
        if (mus1 > mus2) {
            return 1;
        }
        if (mus1 < mus2) {
            return -1;
        }
        if (ns1 > ns2) {
            return 1;
        }
        if (ns1 < ns2) {
            return -1;
        }
        return 0;
    }

    public static JSTemporalDurationRecord addTimeDouble(int hour, int minute, int second, int millisecond, int microsecond, double nanosecond, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds) {
        return TemporalUtil.balanceTimeDouble((double)hour + hours, (double)minute + minutes, (double)second + seconds, (double)millisecond + milliseconds, (double)microsecond + microseconds, nanosecond + nanoseconds);
    }

    public static JSTemporalDurationRecord roundISODateTime(int year, int month, int day, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond, double increment, Unit unit, RoundingMode roundingMode, Long dayLength) {
        JSTemporalDurationRecord rt = TemporalUtil.roundTime(hour, minute, second, millisecond, microsecond, nanosecond, increment, unit, roundingMode, dayLength);
        JSTemporalDateTimeRecord br = TemporalUtil.balanceISODate(year, month, day + TemporalUtil.dtoi(rt.getDays()));
        return JSTemporalDurationRecord.create(br.getYear(), br.getMonth(), br.getDay(), rt.getHours(), rt.getMinutes(), rt.getSeconds(), rt.getMilliseconds(), rt.getMicroseconds(), rt.getNanoseconds());
    }

    public static double toTemporalDateTimeRoundingIncrement(JSDynamicObject options, Unit smallestUnit, IsObjectNode isObject, JSToNumberNode toNumber) {
        int maximum = 0;
        if (Unit.DAY == smallestUnit) {
            maximum = 1;
        } else if (Unit.HOUR == smallestUnit) {
            maximum = 24;
        } else if (Unit.MINUTE == smallestUnit || Unit.SECOND == smallestUnit) {
            maximum = 60;
        } else {
            assert (Unit.MILLISECOND == smallestUnit || Unit.MICROSECOND == smallestUnit || Unit.NANOSECOND == smallestUnit);
            maximum = 1000;
        }
        return TemporalUtil.toTemporalRoundingIncrement(options, Double.valueOf(maximum), false, isObject, toNumber);
    }

    public static boolean isValidTime(int hours, int minutes, int seconds, int milliseconds, int microseconds, int nanoseconds) {
        if (hours < 0 || hours > 23) {
            return false;
        }
        if (minutes < 0 || minutes > 59) {
            return false;
        }
        if (seconds < 0 || seconds > 59) {
            return false;
        }
        if (milliseconds < 0 || milliseconds > 999) {
            return false;
        }
        if (microseconds < 0 || microseconds > 999) {
            return false;
        }
        return nanoseconds >= 0 && nanoseconds <= 999;
    }

    public static boolean isValidISODate(int year, int month, int day) {
        if (month < 1 || month > 12) {
            return false;
        }
        return day >= 1 && day <= TemporalUtil.isoDaysInMonth(year, month);
    }

    public static JSTemporalPlainDateTimeObject systemDateTime(Object temporalTimeZoneLike, Object calendarLike, JSContext ctx) {
        JSDynamicObject timeZone = null;
        timeZone = temporalTimeZoneLike == Undefined.instance ? TemporalUtil.systemTimeZone(ctx) : TemporalUtil.toTemporalTimeZone(ctx, temporalTimeZoneLike);
        JSDynamicObject calendar = TemporalUtil.toTemporalCalendar(ctx, calendarLike);
        JSDynamicObject instant = TemporalUtil.systemInstant(ctx);
        return TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(ctx, timeZone, instant, calendar);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalPlainDateTimeObject builtinTimeZoneGetPlainDateTimeFor(JSContext ctx, JSDynamicObject timeZone, JSDynamicObject instant, JSDynamicObject calendar) {
        long offsetNanoseconds = TemporalUtil.getOffsetNanosecondsFor(timeZone, instant);
        JSTemporalDateTimeRecord result = TemporalUtil.getISOPartsFromEpoch(((JSTemporalInstantObject)instant).getNanoseconds());
        JSTemporalDateTimeRecord result2 = TemporalUtil.balanceISODateTime(result.getYear(), result.getMonth(), result.getDay(), result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), (long)result.getNanosecond() + offsetNanoseconds);
        return JSTemporalPlainDateTime.create(ctx, result2.getYear(), result2.getMonth(), result2.getDay(), result2.getHour(), result2.getMinute(), result2.getSecond(), result2.getMillisecond(), result2.getMicrosecond(), result2.getNanosecond(), calendar);
    }

    public static JSTemporalDateTimeRecord balanceISODateTime(int year, int month, int day, int hour, int minute, int second, int millisecond, int microsecond, long nanosecond) {
        JSTemporalDurationRecord bt = TemporalUtil.balanceTime(hour, minute, second, millisecond, microsecond, nanosecond);
        JSTemporalDateTimeRecord bd = TemporalUtil.balanceISODate(year, month, day + TemporalUtil.dtoi(bt.getDays()));
        return JSTemporalDateTimeRecord.create(bd.getYear(), bd.getMonth(), bd.getDay(), TemporalUtil.dtoi(bt.getHours()), TemporalUtil.dtoi(bt.getMinutes()), TemporalUtil.dtoi(bt.getSeconds()), TemporalUtil.dtoi(bt.getMilliseconds()), TemporalUtil.dtoi(bt.getMicroseconds()), TemporalUtil.dtoi(bt.getNanoseconds()));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalDateTimeRecord getISOPartsFromEpoch(BigInt epochNanoseconds) {
        long epochMilliseconds;
        long remainderNs;
        if (epochNanoseconds.fitsInLong()) {
            remainderNs = epochNanoseconds.longValue() % 1000000L;
            epochMilliseconds = (epochNanoseconds.longValue() - remainderNs) / 1000000L;
        } else {
            BigInteger[] result = epochNanoseconds.bigIntegerValue().divideAndRemainder(BI_10_POW_6);
            remainderNs = result[1].longValue();
            epochMilliseconds = result[0].longValue();
        }
        int year = JSDate.yearFromTime(epochMilliseconds);
        int month = JSDate.monthFromTime(epochMilliseconds) + 1;
        int day = JSDate.dateFromTime(epochMilliseconds);
        int hour = JSDate.hourFromTime(epochMilliseconds);
        int minute = JSDate.minFromTime(epochMilliseconds);
        int second = JSDate.secFromTime(epochMilliseconds);
        int millisecond = JSDate.msFromTime(epochMilliseconds);
        int microsecond = (int)(remainderNs / 1000L % 1000L);
        int nanosecond = (int)(remainderNs % 1000L);
        return JSTemporalDateTimeRecord.create(year, month, day, hour, minute, second, millisecond, microsecond, nanosecond);
    }

    @CompilerDirectives.TruffleBoundary
    public static long getOffsetNanosecondsFor(JSDynamicObject timeZone, JSDynamicObject instant) {
        Object getOffsetNanosecondsFor = JSObject.getMethod(timeZone, GET_OFFSET_NANOSECONDS_FOR);
        Object offsetNanoseconds = JSRuntime.call(getOffsetNanosecondsFor, timeZone, new Object[]{instant});
        if (!JSRuntime.isNumber(offsetNanoseconds)) {
            throw Errors.createTypeError("Number expected");
        }
        Double nanos = ((Number)offsetNanoseconds).doubleValue();
        if (!JSRuntime.isInteger(nanos) || Math.abs(nanos) > 8.64E13) {
            throw Errors.createRangeError("out-of-range Number");
        }
        return nanos.longValue();
    }

    public static JSDynamicObject systemZonedDateTime(Object temporalTimeZoneLike, Object calendarLike, JSContext ctx) {
        JSDynamicObject timeZone = null;
        timeZone = temporalTimeZoneLike == Undefined.instance ? TemporalUtil.systemTimeZone(ctx) : TemporalUtil.toTemporalTimeZone(ctx, temporalTimeZoneLike);
        JSDynamicObject calendar = TemporalUtil.toTemporalCalendar(ctx, calendarLike);
        BigInt ns = TemporalUtil.systemUTCEpochNanoseconds();
        return JSTemporalZonedDateTime.create(ctx, ns, timeZone, calendar);
    }

    public static JSDynamicObject systemInstant(JSContext ctx) {
        BigInt ns = TemporalUtil.systemUTCEpochNanoseconds();
        return JSTemporalInstant.create(ctx, ns);
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInt systemUTCEpochNanoseconds() {
        JSRealm realm = JSRealm.get(null);
        BigInt ns = BigInt.valueOf(realm.nanoTimeWallClock());
        assert (ns.compareTo(upperEpochNSLimit) <= 0 && ns.compareTo(lowerEpochNSLimit) >= 0);
        return ns;
    }

    public static JSDynamicObject systemTimeZone(JSContext ctx) {
        TruffleString identifier = TemporalUtil.defaultTimeZone();
        return TemporalUtil.createTemporalTimeZone(ctx, identifier);
    }

    public static TruffleString defaultTimeZone() {
        return TemporalConstants.UTC;
    }

    public static boolean isTemporalInstant(Object obj) {
        return JSTemporalInstant.isJSTemporalInstant(obj);
    }

    public static int compareEpochNanoseconds(BigInt one, BigInt two) {
        return one.compareTo(two);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean isValidEpochNanoseconds(BigInt nanoseconds) {
        if (nanoseconds == null) {
            return true;
        }
        return nanoseconds.compareTo(lowerEpochNSLimit) >= 0 && nanoseconds.compareTo(upperEpochNSLimit) <= 0;
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInt addInstant(BigInt epochNanoseconds, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds) {
        return TemporalUtil.addInstant(epochNanoseconds, TemporalUtil.dtol(hours), TemporalUtil.dtol(minutes), TemporalUtil.dtol(seconds), TemporalUtil.dtol(milliseconds), TemporalUtil.dtol(microseconds), BigInteger.valueOf(TemporalUtil.dtol(nanoseconds)));
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInt addInstant(BigInt epochNanoseconds, long hours, long minutes, long seconds, long milliseconds, long microseconds, BigInteger nanoseconds) {
        BigInteger res = epochNanoseconds.bigIntegerValue().add(nanoseconds);
        res = res.add(BigInteger.valueOf(microseconds).multiply(BI_1000));
        res = res.add(BigInteger.valueOf(milliseconds).multiply(BI_10_POW_6));
        res = res.add(BigInteger.valueOf(seconds).multiply(BI_10_POW_9));
        res = res.add(BigInteger.valueOf(minutes).multiply(BI_6_10_POW_10));
        BigInt result = new BigInt(res = res.add(BigInteger.valueOf(hours).multiply(BI_36_10_POW_11)));
        if (!TemporalUtil.isValidEpochNanoseconds(result)) {
            throw TemporalErrors.createRangeErrorInvalidNanoseconds();
        }
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInteger differenceInstant(BigInt ns1, BigInt ns2, double roundingIncrement, Unit smallestUnit, RoundingMode roundingMode) {
        return TemporalUtil.roundTemporalInstant(ns2.subtract(ns1), roundingIncrement, smallestUnit, roundingMode);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString temporalInstantToString(JSContext ctx, JSRealm realm, JSDynamicObject instant, JSDynamicObject timeZone, Object precision) {
        JSDynamicObject outputTimeZone = timeZone;
        if (outputTimeZone == Undefined.instance) {
            outputTimeZone = TemporalUtil.createTemporalTimeZone(ctx, TemporalConstants.UTC);
        }
        JSTemporalCalendarObject isoCalendar = TemporalUtil.getISO8601Calendar(ctx, realm);
        JSTemporalPlainDateTimeObject dateTime = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(ctx, outputTimeZone, instant, isoCalendar);
        TruffleString dateTimeString = JSTemporalPlainDateTime.temporalDateTimeToString(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(), dateTime.getMillisecond(), dateTime.getMicrosecond(), dateTime.getNanosecond(), Undefined.instance, precision, ShowCalendar.NEVER);
        TruffleString timeZoneString = null;
        if (timeZone == Undefined.instance) {
            timeZoneString = Strings.UC_Z;
        } else {
            long offsetNs = TemporalUtil.getOffsetNanosecondsFor(timeZone, instant);
            timeZoneString = TemporalUtil.formatISOTimeZoneOffsetString(offsetNs);
        }
        return Strings.concat(dateTimeString, timeZoneString);
    }

    public static TruffleString builtinTimeZoneGetOffsetStringFor(JSDynamicObject timeZone, JSDynamicObject instant) {
        long offsetNanoseconds = TemporalUtil.getOffsetNanosecondsFor(timeZone, instant);
        return TemporalUtil.formatTimeZoneOffsetString(offsetNanoseconds);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString formatTimeZoneOffsetString(long offsetNanosecondsParam) {
        TruffleString sign = offsetNanosecondsParam >= 0L ? Strings.SYMBOL_PLUS : Strings.SYMBOL_MINUS;
        long offsetNanoseconds = Math.abs(offsetNanosecondsParam);
        long nanoseconds = offsetNanoseconds % 1000000000L;
        double s1 = Math.floor((double)offsetNanoseconds / 1.0E9) % 60.0;
        double m1 = Math.floor((double)offsetNanoseconds / 6.0E10) % 60.0;
        double h1 = Math.floor((double)offsetNanoseconds / 3.6E12);
        long seconds = (long)s1;
        long minutes = (long)m1;
        long hours = (long)h1;
        TruffleString h = Strings.format("%1$02d", hours);
        TruffleString m = Strings.format("%1$02d", minutes);
        TruffleString s = Strings.format("%1$02d", seconds);
        TruffleString post2 = Strings.EMPTY_STRING;
        if (nanoseconds != 0L) {
            TruffleString fraction = TemporalUtil.longestSubstring(Strings.format("%1$09d", nanoseconds));
            post2 = Strings.concatAll(Strings.COLON, s, Strings.DOT, fraction);
        } else if (seconds != 0L) {
            post2 = Strings.concat(Strings.COLON, s);
        }
        return Strings.concatAll(sign, h, Strings.COLON, m, post2);
    }

    @CompilerDirectives.TruffleBoundary
    public static long parseTimeZoneOffsetString(TruffleString string) {
        long nanoseconds;
        JSTemporalParserRecord rec = new TemporalParser(string).parseTimeZoneNumericUTCOffset();
        if (rec == null) {
            throw Errors.createRangeError("TemporalTimeZoneNumericUTCOffset expected");
        }
        if (rec.getOffsetFraction() == null) {
            nanoseconds = 0L;
        } else {
            TruffleString fraction = Strings.concat(rec.getOffsetFraction(), ZEROS);
            fraction = Strings.lazySubstring(fraction, 0, 9);
            try {
                nanoseconds = Strings.parseLong(fraction, 10);
            }
            catch (TruffleString.NumberFormatException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }
        TruffleString signS = rec.getOffsetSign();
        int sign = Strings.SYMBOL_MINUS.equals(signS) || Strings.UNICODE_MINUS_SIGN.equals(signS) ? -1 : 1;
        long hours = rec.getOffsetHour() == Long.MIN_VALUE ? 0L : rec.getOffsetHour();
        long minutes = rec.getOffsetMinute() == Long.MIN_VALUE ? 0L : rec.getOffsetMinute();
        long seconds = rec.getOffsetSecond() == Long.MIN_VALUE ? 0L : rec.getOffsetSecond();
        return (long)sign * (((hours * 60L + minutes) * 60L + seconds) * 1000000000L + nanoseconds);
    }

    public static JSTemporalTimeZoneRecord parseTemporalTimeZoneString(TruffleString string) {
        return TemporalUtil.parseTemporalTimeZoneString(string, false);
    }

    @CompilerDirectives.TruffleBoundary
    private static JSTemporalTimeZoneRecord parseTemporalTimeZoneString(TruffleString string, boolean offsetRequired) {
        JSTemporalParserRecord rec = new TemporalParser(string).parseTimeZoneString();
        if (rec == null) {
            throw Errors.createRangeError("TemporalTimeZoneString expected");
        }
        if (offsetRequired && rec.getOffsetHour() == Long.MIN_VALUE && !rec.getZ()) {
            throw TemporalErrors.createRangeErrorTimeZoneOffsetExpected();
        }
        TruffleString name = rec.getTimeZoneIANAName();
        TruffleString offsetString = rec.getTimeZoneNumericUTCOffset();
        if (rec.getZ()) {
            return JSTemporalTimeZoneRecord.create(true, null, name);
        }
        return JSTemporalTimeZoneRecord.create(false, offsetString, name);
    }

    public static Disambiguation toTemporalDisambiguation(JSDynamicObject options, TemporalGetOptionNode getOptionNode, TruffleString.EqualNode equalNode) {
        if (options == Undefined.instance) {
            return Disambiguation.COMPATIBLE;
        }
        return TemporalUtil.toDisambiguation((TruffleString)getOptionNode.execute(options, TemporalConstants.DISAMBIGUATION, OptionType.STRING, listDisambiguation, TemporalConstants.COMPATIBLE), equalNode);
    }

    public static OffsetOption toTemporalOffset(JSDynamicObject options, TruffleString fallback, TemporalGetOptionNode getOptionNode, TruffleString.EqualNode equalNode) {
        TruffleString result = fallback;
        if (options != Undefined.instance) {
            result = (TruffleString)getOptionNode.execute(options, TemporalConstants.OFFSET, OptionType.STRING, listOffset, fallback);
        }
        return TemporalUtil.toOffsetOption(result, equalNode);
    }

    public static TruffleString toShowTimeZoneNameOption(JSDynamicObject options, TemporalGetOptionNode getOptionNode) {
        return (TruffleString)getOptionNode.execute(options, TemporalConstants.TIME_ZONE_NAME, OptionType.STRING, listAutoNever, TemporalConstants.AUTO);
    }

    public static TruffleString toShowOffsetOption(JSDynamicObject options, TemporalGetOptionNode getOptionNode) {
        return (TruffleString)getOptionNode.execute(options, TemporalConstants.OFFSET, OptionType.STRING, listAutoNever, TemporalConstants.AUTO);
    }

    public static TruffleString temporalZonedDateTimeToString(JSContext ctx, JSRealm realm, JSDynamicObject zonedDateTime, Object precision, ShowCalendar showCalendar, TruffleString showTimeZone, TruffleString showOffset) {
        return TemporalUtil.temporalZonedDateTimeToString(ctx, realm, zonedDateTime, precision, showCalendar, showTimeZone, showOffset, null, Unit.EMPTY, RoundingMode.EMPTY);
    }

    public static JSTemporalDateTimeRecord addDateTime(JSContext ctx, int year, int month, int day, int hour, int minute, int second, int millisecond, int microsecond, double nanosecond, JSDynamicObject calendar, double years, double months, double weeks, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds, JSDynamicObject options) {
        JSTemporalDurationRecord timeResult = TemporalUtil.addTimeDouble(hour, minute, second, millisecond, microsecond, nanosecond, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
        JSTemporalPlainDateObject datePart = JSTemporalPlainDate.create(ctx, year, month, day, calendar);
        JSTemporalDurationObject dateDuration = JSTemporalDuration.createTemporalDuration(ctx, years, months, weeks, days + timeResult.getDays(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        JSTemporalPlainDateObject addedDate = (JSTemporalPlainDateObject)TemporalUtil.calendarDateAdd(calendar, datePart, dateDuration, options);
        return JSTemporalDateTimeRecord.create(addedDate.getYear(), addedDate.getMonth(), addedDate.getDay(), TemporalUtil.dtoi(timeResult.getHours()), TemporalUtil.dtoi(timeResult.getMinutes()), TemporalUtil.dtoi(timeResult.getSeconds()), TemporalUtil.dtoi(timeResult.getMilliseconds()), TemporalUtil.dtoi(timeResult.getMicroseconds()), TemporalUtil.dtoi(timeResult.getNanoseconds()));
    }

    public static int compareISODateTime(int year, int month, int day, int hours, int minutes, int seconds, int milliseconds, int microseconds, int nanoseconds, int year2, int month2, int day2, int hours2, int minutes2, int seconds2, int milliseconds2, int microseconds2, int nanoseconds2) {
        int date = TemporalUtil.compareISODate(year, month, day, year2, month2, day2);
        if (date == 0) {
            return TemporalUtil.compareTemporalTime(hours, minutes, seconds, milliseconds, microseconds, nanoseconds, hours2, minutes2, seconds2, milliseconds2, microseconds2, nanoseconds2);
        }
        return date;
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalDateTimeRecord parseTemporalYearMonthString(TruffleString string) {
        JSTemporalParserRecord rec = new TemporalParser(string).parseYearMonth();
        if (rec != null) {
            if (rec.getZ()) {
                throw TemporalErrors.createRangeErrorUnexpectedUTCDesignator();
            }
            if (rec.getYear() == 0L && (Strings.indexOf(string, TemporalConstants.MINUS_000000) >= 0 || Strings.indexOf(string, TemporalConstants.UNICODE_MINUS_SIGN_000000) >= 0)) {
                throw TemporalErrors.createRangeErrorInvalidPlainDateTime();
            }
            int y = rec.getYear() == Long.MIN_VALUE ? 0 : TemporalUtil.ltoi(rec.getYear());
            int m = rec.getMonth() == Long.MIN_VALUE ? 0 : TemporalUtil.ltoi(rec.getMonth());
            int d = rec.getDay() == Long.MIN_VALUE ? 1 : TemporalUtil.ltoi(rec.getDay());
            return JSTemporalDateTimeRecord.createCalendar(y, m, d, 0, 0, 0, 0, 0, 0, rec.getCalendar());
        }
        throw Errors.createRangeError("cannot parse YearMonth");
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString temporalZonedDateTimeToString(JSContext ctx, JSRealm realm, JSDynamicObject zonedDateTimeParam, Object precision, ShowCalendar showCalendar, TruffleString showTimeZone, TruffleString showOffset, Double incrementParam, Unit unitParam, RoundingMode roundingModeParam) {
        assert (TemporalUtil.isTemporalZonedDateTime(zonedDateTimeParam));
        assert (unitParam != null && roundingModeParam != null);
        JSTemporalZonedDateTimeObject zonedDateTime = (JSTemporalZonedDateTimeObject)zonedDateTimeParam;
        double increment = incrementParam == null ? 1.0 : incrementParam;
        Unit unit = unitParam == Unit.EMPTY ? Unit.NANOSECOND : unitParam;
        RoundingMode roundingMode = roundingModeParam == RoundingMode.EMPTY ? RoundingMode.TRUNC : roundingModeParam;
        BigInteger ns = TemporalUtil.roundTemporalInstant(zonedDateTime.getNanoseconds(), (double)((long)increment), unit, roundingMode);
        JSDynamicObject timeZone = zonedDateTime.getTimeZone();
        JSTemporalInstantObject instant = JSTemporalInstant.create(ctx, new BigInt(ns));
        JSTemporalCalendarObject isoCalendar = TemporalUtil.getISO8601Calendar(ctx, realm);
        JSTemporalPlainDateTimeObject temporalDateTime = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(ctx, timeZone, instant, isoCalendar);
        TruffleString dateTimeString = JSTemporalPlainDateTime.temporalDateTimeToString(temporalDateTime.getYear(), temporalDateTime.getMonth(), temporalDateTime.getDay(), temporalDateTime.getHour(), temporalDateTime.getMinute(), temporalDateTime.getSecond(), temporalDateTime.getMillisecond(), temporalDateTime.getMicrosecond(), temporalDateTime.getNanosecond(), isoCalendar, precision, ShowCalendar.NEVER);
        TruffleString offsetString = null;
        TruffleString timeZoneString = null;
        if (TemporalConstants.NEVER.equals(showOffset)) {
            offsetString = Strings.EMPTY_STRING;
        } else {
            long offsetNs = TemporalUtil.getOffsetNanosecondsFor(timeZone, instant);
            offsetString = TemporalUtil.formatISOTimeZoneOffsetString(offsetNs);
        }
        if (TemporalConstants.NEVER.equals(showTimeZone)) {
            timeZoneString = Strings.EMPTY_STRING;
        } else {
            TruffleString timeZoneID = JSRuntime.toString(timeZone);
            timeZoneString = Strings.addBrackets(timeZoneID);
        }
        TruffleString calendarID = JSRuntime.toString(zonedDateTime.getCalendar());
        TruffleString calendarString = TemporalUtil.formatCalendarAnnotation(calendarID, showCalendar);
        return Strings.concatAll(dateTimeString, offsetString, timeZoneString, calendarString);
    }

    @CompilerDirectives.TruffleBoundary
    private static TruffleString formatISOTimeZoneOffsetString(long offsetNs) {
        long offsetNanoseconds = TemporalUtil.dtol(TemporalUtil.roundNumberToIncrement(offsetNs, 6.0E10, RoundingMode.HALF_EXPAND));
        TruffleString sign = Strings.EMPTY_STRING;
        sign = offsetNanoseconds >= 0L ? Strings.SYMBOL_PLUS : Strings.SYMBOL_MINUS;
        offsetNanoseconds = Math.abs(offsetNanoseconds);
        long minutes = offsetNanoseconds / 60000000000L % 60L;
        long hours = (long)Math.floor(offsetNanoseconds / 3600000000000L);
        TruffleString h = Strings.format("%1$02d", hours);
        TruffleString m = Strings.format("%1$02d", minutes);
        return Strings.concatAll(sign, h, Strings.COLON, m);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalZonedDateTimeRecord parseTemporalZonedDateTimeString(TruffleString string) {
        JSTemporalDateTimeRecord result;
        if (!new TemporalParser(string).isTemporalZonedDateTimeString()) {
            throw Errors.createRangeError("cannot be parsed as TemporalZonedDateTimeString");
        }
        try {
            result = TemporalUtil.parseISODateTime(string);
        }
        catch (Exception ex) {
            throw Errors.createRangeError("cannot be parsed as TemporalZonedDateTimeString");
        }
        JSTemporalTimeZoneRecord timeZoneResult = TemporalUtil.parseTemporalTimeZoneString(string);
        return JSTemporalZonedDateTimeRecord.create(result.getYear(), result.getMonth(), result.getDay(), result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), result.getNanosecond(), result.getCalendar(), timeZoneResult.isZ(), timeZoneResult.getOffsetString(), timeZoneResult.getName());
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInt parseTemporalInstant(TruffleString string) {
        long offsetNanoseconds;
        JSTemporalZonedDateTimeRecord result = TemporalUtil.parseTemporalInstantString(string);
        TruffleString offsetString = result.getTimeZoneOffsetString();
        assert (offsetString != null);
        BigInteger utc = TemporalUtil.getEpochFromISOParts(result.getYear(), result.getMonth(), result.getDay(), result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), result.getNanosecond());
        BigInt instant = new BigInt(utc.subtract(BigInteger.valueOf(offsetNanoseconds = TemporalUtil.parseTimeZoneOffsetString(offsetString))));
        if (!TemporalUtil.isValidEpochNanoseconds(instant)) {
            throw TemporalErrors.createRangeErrorInvalidNanoseconds();
        }
        return instant;
    }

    @CompilerDirectives.TruffleBoundary
    private static JSTemporalZonedDateTimeRecord parseTemporalInstantString(TruffleString string) {
        try {
            JSTemporalDateTimeRecord result = TemporalUtil.parseISODateTime(string);
            JSTemporalTimeZoneRecord timeZoneResult = TemporalUtil.parseTemporalTimeZoneString(string, true);
            TruffleString offsetString = timeZoneResult.getOffsetString();
            if (timeZoneResult.isZ()) {
                offsetString = OFFSET_ZERO;
            }
            assert (offsetString != null);
            return JSTemporalZonedDateTimeRecord.create(result.getYear(), result.getMonth(), result.getDay(), result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), result.getNanosecond(), null, false, offsetString, null);
        }
        catch (Exception ex) {
            throw Errors.createRangeError("Instant cannot be parsed");
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalInstantObject builtinTimeZoneGetInstantFor(JSContext ctx, JSDynamicObject timeZone, JSTemporalPlainDateTimeObject dateTime, Disambiguation disambiguation) {
        List<JSTemporalInstantObject> possibleInstants = TemporalUtil.getPossibleInstantsFor(timeZone, dateTime);
        return TemporalUtil.disambiguatePossibleInstants(ctx, possibleInstants, timeZone, dateTime, disambiguation);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSTemporalInstantObject disambiguatePossibleInstants(JSContext ctx, List<JSTemporalInstantObject> possibleInstants, JSDynamicObject timeZone, JSTemporalPlainDateTimeObject dateTime, Disambiguation disambiguation) {
        int n = possibleInstants.size();
        if (n == 1) {
            return possibleInstants.get(0);
        }
        if (n != 0) {
            if (Disambiguation.EARLIER == disambiguation || Disambiguation.COMPATIBLE == disambiguation) {
                return possibleInstants.get(0);
            }
            if (Disambiguation.LATER == disambiguation) {
                return possibleInstants.get(n - 1);
            }
            assert (Disambiguation.REJECT == disambiguation);
            throw Errors.createRangeError("invalid disambiguation");
        }
        assert (n == 0);
        if (Disambiguation.REJECT == disambiguation) {
            throw Errors.createRangeError("disambiguation failed");
        }
        BigInteger epochNanoseconds = TemporalUtil.getEpochFromISOParts(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(), dateTime.getMillisecond(), dateTime.getMicrosecond(), dateTime.getNanosecond());
        JSTemporalInstantObject dayBefore = JSTemporalInstant.create(ctx, new BigInt(epochNanoseconds.subtract(BI_8_64_13)));
        JSTemporalInstantObject dayAfter = JSTemporalInstant.create(ctx, new BigInt(epochNanoseconds.add(BI_8_64_13)));
        long offsetBefore = TemporalUtil.getOffsetNanosecondsFor(timeZone, dayBefore);
        long offsetAfter = TemporalUtil.getOffsetNanosecondsFor(timeZone, dayAfter);
        long nanoseconds = offsetAfter - offsetBefore;
        if (Disambiguation.EARLIER == disambiguation) {
            JSTemporalDateTimeRecord earlier = TemporalUtil.addDateTime(ctx, dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(), dateTime.getMillisecond(), dateTime.getMicrosecond(), dateTime.getNanosecond(), dateTime.getCalendar(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, -nanoseconds, Undefined.instance);
            JSTemporalPlainDateTimeObject earlierDateTime = JSTemporalPlainDateTime.create(ctx, earlier.getYear(), earlier.getMonth(), earlier.getDay(), earlier.getHour(), earlier.getMinute(), earlier.getSecond(), earlier.getMillisecond(), earlier.getMicrosecond(), earlier.getNanosecond(), dateTime.getCalendar());
            List<JSTemporalInstantObject> possibleInstants2 = TemporalUtil.getPossibleInstantsFor(timeZone, earlierDateTime);
            if (possibleInstants2.size() == 0) {
                throw Errors.createRangeError("nothing found");
            }
            return possibleInstants2.get(0);
        }
        assert (Disambiguation.LATER == disambiguation || Disambiguation.COMPATIBLE == disambiguation);
        JSTemporalDateTimeRecord later = TemporalUtil.addDateTime(ctx, dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(), dateTime.getMillisecond(), dateTime.getMicrosecond(), dateTime.getNanosecond(), dateTime.getCalendar(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, nanoseconds, Undefined.instance);
        JSTemporalPlainDateTimeObject laterDateTime = JSTemporalPlainDateTime.create(ctx, later.getYear(), later.getMonth(), later.getDay(), later.getHour(), later.getMinute(), later.getSecond(), later.getMillisecond(), later.getMicrosecond(), later.getNanosecond(), dateTime.getCalendar());
        List<JSTemporalInstantObject> possibleInstants2 = TemporalUtil.getPossibleInstantsFor(timeZone, laterDateTime);
        n = possibleInstants2.size();
        if (n == 0) {
            throw Errors.createRangeError("nothing found");
        }
        return possibleInstants2.get(n - 1);
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInt interpretISODateTimeOffset(JSContext ctx, JSRealm realm, int year, int month, int day, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond, OffsetBehaviour offsetBehaviour, Object offsetNanosecondsParam, JSDynamicObject timeZone, Disambiguation disambiguation, OffsetOption offsetOption, MatchBehaviour matchBehaviour) {
        double offsetNs = offsetNanosecondsParam == null || offsetNanosecondsParam == Undefined.instance ? Double.NaN : ((Number)offsetNanosecondsParam).doubleValue();
        JSTemporalCalendarObject calendar = TemporalUtil.getISO8601Calendar(ctx, realm);
        JSTemporalPlainDateTimeObject dateTime = JSTemporalPlainDateTime.create(ctx, year, month, day, hour, minute, second, millisecond, microsecond, nanosecond, calendar);
        if (offsetBehaviour == OffsetBehaviour.WALL || OffsetOption.IGNORE == offsetOption) {
            JSTemporalInstantObject instant = TemporalUtil.builtinTimeZoneGetInstantFor(ctx, timeZone, dateTime, disambiguation);
            return instant.getNanoseconds();
        }
        if (offsetBehaviour == OffsetBehaviour.EXACT || OffsetOption.USE == offsetOption) {
            BigInteger epochNanoseconds = TemporalUtil.getEpochFromISOParts(year, month, day, hour, minute, second, millisecond, microsecond, nanosecond);
            return new BigInt(epochNanoseconds.subtract(BigInteger.valueOf((long)offsetNs)));
        }
        assert (offsetBehaviour == OffsetBehaviour.OPTION);
        assert (OffsetOption.PREFER == offsetOption || OffsetOption.REJECT == offsetOption);
        List<JSTemporalInstantObject> possibleInstants = TemporalUtil.getPossibleInstantsFor(timeZone, dateTime);
        for (JSTemporalInstantObject candidate : possibleInstants) {
            long roundedCandidateNanoseconds;
            long candidateNanoseconds = TemporalUtil.getOffsetNanosecondsFor(timeZone, candidate);
            if ((double)candidateNanoseconds == offsetNs) {
                return candidate.getNanoseconds();
            }
            if (matchBehaviour != MatchBehaviour.MATCH_MINUTES || (double)(roundedCandidateNanoseconds = TemporalUtil.dtol(TemporalUtil.roundNumberToIncrement(candidateNanoseconds, 6.0E10, RoundingMode.HALF_EXPAND))) != offsetNs) continue;
            return candidate.getNanoseconds();
        }
        if (OffsetOption.REJECT == offsetOption) {
            throw Errors.createRangeError("cannot interpret DateTime offset");
        }
        JSTemporalInstantObject instant = TemporalUtil.builtinTimeZoneGetInstantFor(ctx, timeZone, dateTime, disambiguation);
        return instant.getNanoseconds();
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInt addZonedDateTime(JSContext ctx, BigInt epochNanoseconds, JSDynamicObject timeZone, JSDynamicObject calendar, long years, long months, long weeks, long days, long hours, long minutes, long seconds, long milliseconds, long microseconds, long nanoseconds) {
        return TemporalUtil.addZonedDateTime(ctx, epochNanoseconds, timeZone, calendar, years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, BigInteger.valueOf(nanoseconds), Undefined.instance);
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInt addZonedDateTime(JSContext ctx, BigInt epochNanoseconds, JSDynamicObject timeZone, JSDynamicObject calendar, long years, long months, long weeks, long days, long hours, long minutes, long seconds, long milliseconds, long microseconds, BigInteger nanoseconds, JSDynamicObject options) {
        if (years == 0L && months == 0L && weeks == 0L && days == 0L) {
            return TemporalUtil.addInstant(epochNanoseconds, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
        }
        JSTemporalInstantObject instant = JSTemporalInstant.create(ctx, epochNanoseconds);
        JSTemporalPlainDateTimeObject temporalDateTime = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(ctx, timeZone, instant, calendar);
        JSTemporalPlainDateObject datePart = JSTemporalPlainDate.create(ctx, temporalDateTime.getYear(), temporalDateTime.getMonth(), temporalDateTime.getDay(), calendar);
        JSTemporalDurationObject dateDuration = JSTemporalDuration.createTemporalDuration(ctx, years, months, weeks, days, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        JSTemporalPlainDateObject addedDate = (JSTemporalPlainDateObject)TemporalUtil.calendarDateAdd(calendar, datePart, dateDuration, options);
        JSTemporalPlainDateTimeObject intermediateDateTime = JSTemporalPlainDateTime.create(ctx, addedDate.getYear(), addedDate.getMonth(), addedDate.getDay(), temporalDateTime.getHour(), temporalDateTime.getMinute(), temporalDateTime.getSecond(), temporalDateTime.getMillisecond(), temporalDateTime.getMicrosecond(), temporalDateTime.getNanosecond(), calendar);
        JSTemporalInstantObject intermediateInstant = TemporalUtil.builtinTimeZoneGetInstantFor(ctx, timeZone, intermediateDateTime, Disambiguation.COMPATIBLE);
        return TemporalUtil.addInstant(intermediateInstant.getNanoseconds(), hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
    }

    public static JSDynamicObject moveRelativeZonedDateTime(JSContext ctx, JSDynamicObject zonedDateTime, long years, long months, long weeks, long days) {
        JSTemporalZonedDateTimeObject zdt = (JSTemporalZonedDateTimeObject)zonedDateTime;
        BigInt intermediateNs = TemporalUtil.addZonedDateTime(ctx, zdt.getNanoseconds(), zdt.getTimeZone(), zdt.getCalendar(), years, months, weeks, days, 0L, 0L, 0L, 0L, 0L, 0L);
        return JSTemporalZonedDateTime.create(ctx, intermediateNs, zdt.getTimeZone(), zdt.getCalendar());
    }

    public static boolean timeZoneEquals(JSDynamicObject tz1, JSDynamicObject tz2, JSToStringNode toStringNode) {
        if (tz1 == tz2) {
            return true;
        }
        TruffleString s1 = toStringNode.executeString(tz1);
        TruffleString s2 = toStringNode.executeString(tz2);
        return Boundaries.equals(s1, s2);
    }

    public static JSDynamicObject consolidateCalendars(JSDynamicObject one, JSDynamicObject two, JSToStringNode toStringNode) {
        if (one == two) {
            return two;
        }
        TruffleString s1 = toStringNode.executeString(one);
        TruffleString s2 = toStringNode.executeString(two);
        return TemporalUtil.consolidateCalendarsIntl(one, two, s1, s2);
    }

    @CompilerDirectives.TruffleBoundary
    private static JSDynamicObject consolidateCalendarsIntl(JSDynamicObject one, JSDynamicObject two, TruffleString s1, TruffleString s2) {
        if (s1.equals(s2)) {
            return two;
        }
        if (TemporalConstants.ISO8601.equals(s1)) {
            return two;
        }
        if (TemporalConstants.ISO8601.equals(s2)) {
            return one;
        }
        throw Errors.createRangeError("cannot consolidate calendars");
    }

    private static List<JSTemporalInstantObject> getPossibleInstantsFor(JSDynamicObject timeZone, JSDynamicObject dateTime) {
        Object fn = JSObject.get(timeZone, GET_POSSIBLE_INSTANTS_FOR);
        JSDynamicObject possibleInstants = TemporalUtil.toDynamicObject(JSRuntime.call(fn, timeZone, new Object[]{dateTime}));
        IteratorRecord iteratorRecord = JSRuntime.getIterator(possibleInstants);
        ArrayList<JSTemporalInstantObject> list = new ArrayList<JSTemporalInstantObject>();
        Object next = true;
        while (next != Boolean.FALSE) {
            next = JSRuntime.iteratorStep(iteratorRecord);
            if (next == Boolean.FALSE) continue;
            Object nextValue = JSRuntime.iteratorValue((JSDynamicObject)next);
            if (!TemporalUtil.isTemporalInstant(nextValue)) {
                JSRuntime.iteratorClose(possibleInstants);
                throw Errors.createTypeError("unexpected value");
            }
            list.add((JSTemporalInstantObject)nextValue);
        }
        return list;
    }

    @CompilerDirectives.TruffleBoundary
    public static List<BigInt> getIANATimeZoneEpochValue(TruffleString identifier, long isoYear, long isoMonth, long isoDay, long hours, long minutes, long seconds, long milliseconds, long microseconds, long nanoseconds) {
        ArrayList<BigInt> list;
        block2: {
            list = new ArrayList<BigInt>();
            try {
                ZoneId zoneId = ZoneId.of(Strings.toJavaString(identifier));
                long fractions = milliseconds * 1000000L + microseconds * 1000L + nanoseconds;
                ZonedDateTime zdt = ZonedDateTime.of((int)isoYear, (int)isoMonth, (int)isoDay, (int)hours, (int)minutes, (int)seconds, (int)fractions, zoneId);
                list.add(BigInt.valueOf(zdt.toEpochSecond() * 1000000000L + fractions));
            }
            catch (Exception ex) {
                if ($assertionsDisabled) break block2;
                throw new AssertionError();
            }
        }
        return list;
    }

    @CompilerDirectives.TruffleBoundary
    public static double getIANATimeZoneOffsetNanoseconds(BigInt nanoseconds, TruffleString identifier) {
        try {
            Instant instant = Instant.ofEpochSecond(0L, nanoseconds.longValue());
            ZoneId zoneId = ZoneId.of(Strings.toJavaString(identifier));
            ZoneRules zoneRule = zoneId.getRules();
            ZoneOffset offset = zoneRule.getOffset(instant);
            return (double)offset.getTotalSeconds() * 1.0E9;
        }
        catch (Exception ex) {
            assert (false);
            return -9.223372036854776E18;
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static OptionalLong getIANATimeZoneNextTransition(BigInt nanoseconds, TruffleString identifier) {
        try {
            BigInteger[] sec = nanoseconds.bigIntegerValue().divideAndRemainder(BI_10_POW_9);
            Instant instant = Instant.ofEpochSecond(sec[0].longValue(), sec[1].longValue());
            ZoneId zoneId = ZoneId.of(Strings.toJavaString(identifier));
            ZoneRules zoneRule = zoneId.getRules();
            ZoneOffsetTransition nextTransition = zoneRule.nextTransition(instant);
            if (nextTransition == null) {
                return OptionalLong.empty();
            }
            return OptionalLong.of(nextTransition.toEpochSecond() * 1000000000L);
        }
        catch (Exception ex) {
            assert (false);
            return OptionalLong.of(Long.MIN_VALUE);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static OptionalLong getIANATimeZonePreviousTransition(BigInt nanoseconds, TruffleString identifier) {
        try {
            BigInteger[] sec = nanoseconds.bigIntegerValue().divideAndRemainder(BI_10_POW_9);
            Instant instant = Instant.ofEpochSecond(sec[0].longValue(), sec[1].longValue());
            ZoneId zoneId = ZoneId.of(Strings.toJavaString(identifier));
            ZoneRules zoneRule = zoneId.getRules();
            ZoneOffsetTransition previousTransition = zoneRule.previousTransition(instant);
            if (previousTransition == null) {
                return OptionalLong.empty();
            }
            return OptionalLong.of(previousTransition.toEpochSecond() * 1000000000L);
        }
        catch (Exception ex) {
            assert (false);
            return OptionalLong.empty();
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean canParseAsTimeZoneNumericUTCOffset(TruffleString string) {
        try {
            JSTemporalParserRecord rec = new TemporalParser(string).parseTimeZoneNumericUTCOffset();
            return rec != null;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static boolean isoYearMonthWithinLimits(int year, int month) {
        if (year < -271821 || year > 275760) {
            return false;
        }
        if (year == -271821 && month < 4) {
            return false;
        }
        return year != 275760 || month <= 9;
    }

    public static Number calendarYear(TemporalCalendarGetterNode getterNode, JSDynamicObject calendar, JSDynamicObject dateLike) {
        return getterNode.executeInteger(calendar, dateLike, TemporalConstants.YEAR);
    }

    public static Number calendarMonth(TemporalCalendarGetterNode getterNode, JSDynamicObject calendar, JSDynamicObject dateLike) {
        return getterNode.executeInteger(calendar, dateLike, TemporalConstants.MONTH);
    }

    public static TruffleString calendarMonthCode(TemporalCalendarGetterNode getterNode, JSDynamicObject calendar, JSDynamicObject dateLike) {
        return getterNode.executeString(calendar, dateLike, TemporalConstants.MONTH_CODE);
    }

    public static Number calendarDay(TemporalCalendarGetterNode getterNode, JSDynamicObject calendar, JSDynamicObject dateLike) {
        return getterNode.executeInteger(calendar, dateLike, TemporalConstants.DAY);
    }

    public static Object calendarDayOfWeek(TemporalCalendarGetterNode getterNode, JSDynamicObject calendar, JSDynamicObject dateLike) {
        return getterNode.execute(calendar, dateLike, TemporalConstants.DAY_OF_WEEK);
    }

    public static Object calendarDayOfYear(TemporalCalendarGetterNode getterNode, JSDynamicObject calendar, JSDynamicObject dateLike) {
        return getterNode.execute(calendar, dateLike, TemporalConstants.DAY_OF_YEAR);
    }

    public static Object calendarWeekOfYear(TemporalCalendarGetterNode getterNode, JSDynamicObject calendar, JSDynamicObject dateLike) {
        return getterNode.execute(calendar, dateLike, TemporalConstants.WEEK_OF_YEAR);
    }

    public static Object calendarDaysInWeek(TemporalCalendarGetterNode getterNode, JSDynamicObject calendar, JSDynamicObject dateLike) {
        return getterNode.execute(calendar, dateLike, TemporalConstants.DAYS_IN_WEEK);
    }

    public static Object calendarDaysInMonth(TemporalCalendarGetterNode getterNode, JSDynamicObject calendar, JSDynamicObject dateLike) {
        return getterNode.execute(calendar, dateLike, TemporalConstants.DAYS_IN_MONTH);
    }

    public static Object calendarDaysInYear(TemporalCalendarGetterNode getterNode, JSDynamicObject calendar, JSDynamicObject dateLike) {
        return getterNode.execute(calendar, dateLike, TemporalConstants.DAYS_IN_YEAR);
    }

    public static Object calendarMonthsInYear(TemporalCalendarGetterNode getterNode, JSDynamicObject calendar, JSDynamicObject dateLike) {
        return getterNode.execute(calendar, dateLike, TemporalConstants.MONTHS_IN_YEAR);
    }

    public static Object calendarInLeapYear(TemporalCalendarGetterNode getterNode, JSDynamicObject calendar, JSDynamicObject dateLike) {
        return getterNode.execute(calendar, dateLike, TemporalConstants.IN_LEAP_YEAR);
    }

    public static Object resolveISOMonth(JSContext ctx, JSDynamicObject fields, JSToIntegerOrInfinityNode toIntegerOrInfinity, JSIdenticalNode identicalNode) {
        double m1;
        Object month = JSObject.get(fields, TemporalConstants.MONTH);
        Object monthCode = JSObject.get(fields, TemporalConstants.MONTH_CODE);
        if (monthCode == Undefined.instance) {
            if (month == Undefined.instance) {
                throw Errors.createTypeError("No month or month code present.");
            }
            return month;
        }
        assert (monthCode instanceof TruffleString);
        int monthLength = Strings.length((TruffleString)monthCode);
        if (monthLength != 3) {
            throw Errors.createRangeError("Month code should be in 3 character code.");
        }
        TruffleString numberPart = Strings.substring(ctx, (TruffleString)monthCode, 1);
        double numberPart2 = JSRuntime.doubleValue(toIntegerOrInfinity.executeNumber(numberPart));
        if (Double.isNaN(numberPart2)) {
            throw Errors.createRangeError("The last character of the monthCode should be a number.");
        }
        if (numberPart2 < 1.0 || numberPart2 > 12.0) {
            throw Errors.createRangeError("monthCode out of bounds");
        }
        double d = m1 = month == Undefined.instance ? -1.0 : JSRuntime.doubleValue(toIntegerOrInfinity.executeNumber(month));
        if (month != Undefined.instance && m1 != numberPart2) {
            throw Errors.createRangeError("Month does not equal the month code.");
        }
        if (!identicalNode.executeBoolean(monthCode, TemporalUtil.buildISOMonthCode((int)numberPart2))) {
            throw Errors.createRangeError("Not same value");
        }
        return (long)numberPart2;
    }

    public static JSTemporalDateTimeRecord isoDateFromFields(JSDynamicObject fields, JSDynamicObject options, JSContext ctx, IsObjectNode isObject, TemporalGetOptionNode getOptionNode, JSToIntegerOrInfinityNode toIntOrInfinityNode, JSIdenticalNode identicalNode) {
        assert (isObject.executeBoolean(fields));
        Overflow overflow = TemporalUtil.toTemporalOverflow(options, getOptionNode);
        JSDynamicObject preparedFields = TemporalUtil.prepareTemporalFields(ctx, fields, listDMMCY, listYD);
        Object year = JSObject.get(preparedFields, TemporalConstants.YEAR);
        Object month = TemporalUtil.resolveISOMonth(ctx, preparedFields, toIntOrInfinityNode, identicalNode);
        Object day = JSObject.get(preparedFields, TemporalConstants.DAY);
        return TemporalUtil.regulateISODate(TemporalUtil.dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(year))), TemporalUtil.dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(month))), TemporalUtil.dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(day))), overflow);
    }

    public static JSTemporalYearMonthDayRecord isoYearMonthFromFields(JSDynamicObject fields, JSDynamicObject options, JSContext ctx, IsObjectNode isObject, TemporalGetOptionNode getOptionNode, JSToIntegerOrInfinityNode toIntOrInfinityNode, JSIdenticalNode identicalNode) {
        assert (isObject.executeBoolean(fields));
        Overflow overflow = TemporalUtil.toTemporalOverflow(options, getOptionNode);
        JSDynamicObject preparedFields = TemporalUtil.prepareTemporalFields(ctx, fields, listMMCY, listY);
        Object year = JSObject.get(preparedFields, TemporalConstants.YEAR);
        Object month = TemporalUtil.resolveISOMonth(ctx, preparedFields, toIntOrInfinityNode, identicalNode);
        JSTemporalYearMonthDayRecord result = TemporalUtil.regulateISOYearMonth(TemporalUtil.dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(year))), TemporalUtil.dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(month))), overflow);
        return JSTemporalYearMonthDayRecord.create(result.getYear(), result.getMonth(), 1);
    }

    public static JSTemporalYearMonthDayRecord isoMonthDayFromFields(JSDynamicObject fields, JSDynamicObject options, JSContext ctx, IsObjectNode isObject, TemporalGetOptionNode getOptionNode, JSToIntegerOrInfinityNode toIntOrInfinityNode, JSIdenticalNode identicalNode) {
        assert (isObject.executeBoolean(fields));
        Overflow overflow = TemporalUtil.toTemporalOverflow(options, getOptionNode);
        JSDynamicObject preparedFields = TemporalUtil.prepareTemporalFields(ctx, fields, listDMMCY, listD);
        Object month = JSObject.get(preparedFields, TemporalConstants.MONTH);
        Object monthCode = JSObject.get(preparedFields, TemporalConstants.MONTH_CODE);
        Object year = JSObject.get(preparedFields, TemporalConstants.YEAR);
        if (month != Undefined.instance && monthCode == Undefined.instance && year == Undefined.instance) {
            throw Errors.createTypeError("A year or a month code should be present.");
        }
        month = TemporalUtil.resolveISOMonth(ctx, preparedFields, toIntOrInfinityNode, identicalNode);
        Object day = JSObject.get(preparedFields, TemporalConstants.DAY);
        int referenceISOYear = 1972;
        JSTemporalDateTimeRecord result = null;
        result = monthCode == Undefined.instance ? TemporalUtil.regulateISODate(TemporalUtil.dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(year))), TemporalUtil.dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(month))), TemporalUtil.dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(day))), overflow) : TemporalUtil.regulateISODate(referenceISOYear, TemporalUtil.dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(month))), TemporalUtil.dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(day))), overflow);
        return JSTemporalYearMonthDayRecord.create(referenceISOYear, result.getMonth(), result.getDay());
    }

    public static long isoDay(JSDynamicObject temporalObject) {
        TemporalDay day = (TemporalDay)((Object)temporalObject);
        return day.getDay();
    }

    public static JSTemporalDurationRecord createDurationRecord(double years, double months, double weeks, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds) {
        if (!TemporalUtil.isValidDuration(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds)) {
            throw TemporalErrors.createTypeErrorDurationOutsideRange();
        }
        return JSTemporalDurationRecord.createWeeks(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
    }

    public static long dtol(double d) {
        assert (JSRuntime.doubleIsRepresentableAsLong(d));
        return (long)d;
    }

    public static int dtoi(double d) {
        if (d == 0.0) {
            return 0;
        }
        assert (JSRuntime.doubleIsRepresentableAsInt(d));
        return (int)d;
    }

    @CompilerDirectives.TruffleBoundary
    public static long dtol(double d, boolean failOnError) {
        if (failOnError && !JSRuntime.doubleIsRepresentableAsLong(d)) {
            throw Errors.createRangeError("value out of range");
        }
        return (long)d;
    }

    @CompilerDirectives.TruffleBoundary
    public static int ltoi(long l) {
        if (!JSRuntime.longIsRepresentableAsInt(l)) {
            throw Errors.createRangeError("value out of range");
        }
        return (int)l;
    }

    @CompilerDirectives.TruffleBoundary
    public static int bitoi(BigInteger bi) {
        double value2 = bi.doubleValue();
        assert (Double.isFinite(value2));
        assert (JSRuntime.doubleIsRepresentableAsInt(value2));
        return bi.intValue();
    }

    @CompilerDirectives.TruffleBoundary
    public static double bitod(BigInteger bi) {
        double value2 = bi.doubleValue();
        assert (Double.isFinite(value2));
        return value2;
    }

    @CompilerDirectives.TruffleBoundary
    public static long bitol(BigInteger bi) {
        long value2 = bi.longValueExact();
        return value2;
    }

    @CompilerDirectives.TruffleBoundary
    public static long bigIntToLong(BigInt val) {
        return val.longValueExact();
    }

    @CompilerDirectives.TruffleBoundary
    private static int add(int a, int b, Overflow overflow) {
        try {
            return Math.addExact(a, b);
        }
        catch (ArithmeticException ex) {
            if (overflow == Overflow.REJECT) {
                throw TemporalErrors.createRangeErrorDateOutsideRange();
            }
            assert (overflow == Overflow.CONSTRAIN);
            return Integer.MAX_VALUE;
        }
    }

    public static JSTemporalDurationRecord createNegatedTemporalDuration(JSTemporalDurationRecord d) {
        return d.copyNegated();
    }

    public static Unit toUnit(TruffleString unit, TruffleString.EqualNode equalNode) {
        if (unit == null) {
            return Unit.EMPTY;
        }
        if (equalNode.execute(unit, TemporalConstants.YEAR, TruffleString.Encoding.UTF_16)) {
            return Unit.YEAR;
        }
        if (equalNode.execute(unit, TemporalConstants.MONTH, TruffleString.Encoding.UTF_16)) {
            return Unit.MONTH;
        }
        if (equalNode.execute(unit, TemporalConstants.WEEK, TruffleString.Encoding.UTF_16)) {
            return Unit.WEEK;
        }
        if (equalNode.execute(unit, TemporalConstants.DAY, TruffleString.Encoding.UTF_16)) {
            return Unit.DAY;
        }
        if (equalNode.execute(unit, TemporalConstants.HOUR, TruffleString.Encoding.UTF_16)) {
            return Unit.HOUR;
        }
        if (equalNode.execute(unit, TemporalConstants.MINUTE, TruffleString.Encoding.UTF_16)) {
            return Unit.MINUTE;
        }
        if (equalNode.execute(unit, TemporalConstants.SECOND, TruffleString.Encoding.UTF_16)) {
            return Unit.SECOND;
        }
        if (equalNode.execute(unit, TemporalConstants.MILLISECOND, TruffleString.Encoding.UTF_16)) {
            return Unit.MILLISECOND;
        }
        if (equalNode.execute(unit, TemporalConstants.MICROSECOND, TruffleString.Encoding.UTF_16)) {
            return Unit.MICROSECOND;
        }
        if (equalNode.execute(unit, TemporalConstants.NANOSECOND, TruffleString.Encoding.UTF_16)) {
            return Unit.NANOSECOND;
        }
        if (equalNode.execute(unit, TemporalConstants.AUTO, TruffleString.Encoding.UTF_16)) {
            return Unit.AUTO;
        }
        throw Errors.createTypeError("unexpected unit");
    }

    @CompilerDirectives.TruffleBoundary
    public static RoundingMode toRoundingMode(TruffleString mode, TruffleString.EqualNode equalNode) {
        if (mode == null) {
            return RoundingMode.EMPTY;
        }
        if (equalNode.execute(mode, TemporalConstants.FLOOR, TruffleString.Encoding.UTF_16)) {
            return RoundingMode.FLOOR;
        }
        if (equalNode.execute(mode, TemporalConstants.CEIL, TruffleString.Encoding.UTF_16)) {
            return RoundingMode.CEIL;
        }
        if (equalNode.execute(mode, TemporalConstants.HALF_EXPAND, TruffleString.Encoding.UTF_16)) {
            return RoundingMode.HALF_EXPAND;
        }
        if (equalNode.execute(mode, TemporalConstants.TRUNC, TruffleString.Encoding.UTF_16)) {
            return RoundingMode.TRUNC;
        }
        throw Errors.createTypeError("unexpected roundingMode");
    }

    @CompilerDirectives.TruffleBoundary
    public static Disambiguation toDisambiguation(TruffleString disambiguation, TruffleString.EqualNode equalNode) {
        if (equalNode.execute(disambiguation, TemporalConstants.EARLIER, TruffleString.Encoding.UTF_16)) {
            return Disambiguation.EARLIER;
        }
        if (equalNode.execute(disambiguation, TemporalConstants.LATER, TruffleString.Encoding.UTF_16)) {
            return Disambiguation.LATER;
        }
        if (equalNode.execute(disambiguation, TemporalConstants.COMPATIBLE, TruffleString.Encoding.UTF_16)) {
            return Disambiguation.COMPATIBLE;
        }
        if (equalNode.execute(disambiguation, TemporalConstants.REJECT, TruffleString.Encoding.UTF_16)) {
            return Disambiguation.REJECT;
        }
        throw Errors.createTypeError("unexpected disambiguation");
    }

    @CompilerDirectives.TruffleBoundary
    public static OffsetOption toOffsetOption(TruffleString offsetOption, TruffleString.EqualNode equalNode) {
        if (equalNode.execute(offsetOption, TemporalConstants.USE, TruffleString.Encoding.UTF_16)) {
            return OffsetOption.USE;
        }
        if (equalNode.execute(offsetOption, TemporalConstants.IGNORE, TruffleString.Encoding.UTF_16)) {
            return OffsetOption.IGNORE;
        }
        if (equalNode.execute(offsetOption, TemporalConstants.PREFER, TruffleString.Encoding.UTF_16)) {
            return OffsetOption.PREFER;
        }
        if (equalNode.execute(offsetOption, TemporalConstants.REJECT, TruffleString.Encoding.UTF_16)) {
            return OffsetOption.REJECT;
        }
        throw Errors.createTypeError("unexpected offsetOption");
    }

    @CompilerDirectives.TruffleBoundary
    public static ShowCalendar toShowCalendar(TruffleString showCalendar, TruffleString.EqualNode equalNode) {
        if (equalNode.execute(showCalendar, TemporalConstants.AUTO, TruffleString.Encoding.UTF_16)) {
            return ShowCalendar.AUTO;
        }
        if (equalNode.execute(showCalendar, TemporalConstants.NEVER, TruffleString.Encoding.UTF_16)) {
            return ShowCalendar.NEVER;
        }
        if (equalNode.execute(showCalendar, TemporalConstants.ALWAYS, TruffleString.Encoding.UTF_16)) {
            return ShowCalendar.ALWAYS;
        }
        throw Errors.createTypeError("unexpected showCalendar");
    }

    public static double roundTowardsZero(double d) {
        return ExactMath.truncate(d);
    }

    public static enum ShowCalendar {
        AUTO,
        ALWAYS,
        NEVER;

    }

    public static enum OffsetOption {
        USE,
        IGNORE,
        PREFER,
        REJECT;

    }

    public static enum Disambiguation {
        EARLIER,
        LATER,
        COMPATIBLE,
        REJECT;

    }

    public static enum UnsignedRoundingMode {
        EMPTY,
        ZERO,
        INFINITY,
        HALF_INFINITY,
        HALF_ZERO,
        HALF_EVEN;

    }

    public static enum RoundingMode {
        EMPTY,
        CEIL,
        FLOOR,
        EXPAND,
        TRUNC,
        HALF_EXPAND,
        HALF_TRUNC,
        HALF_EVEN,
        HALF_FLOOR,
        HALF_CEIL;

    }

    public static enum UnitPlural {
        YEARS(TemporalConstants.YEARS),
        MONTHS(TemporalConstants.MONTHS),
        WEEKS(TemporalConstants.WEEKS),
        DAYS(TemporalConstants.DAYS),
        HOURS(TemporalConstants.HOURS),
        MINUTES(TemporalConstants.MINUTES),
        SECONDS(TemporalConstants.SECONDS),
        MILLISECONDS(TemporalConstants.MILLISECONDS),
        MICROSECONDS(TemporalConstants.MICROSECONDS),
        NANOSECONDS(TemporalConstants.NANOSECONDS);

        private final TruffleString name;

        private UnitPlural(TruffleString name) {
            this.name = name;
        }

        public TruffleString toTruffleString() {
            return this.name;
        }
    }

    public static enum Unit {
        EMPTY(Strings.EMPTY_STRING),
        AUTO(TemporalConstants.AUTO),
        YEAR(TemporalConstants.YEAR),
        MONTH(TemporalConstants.MONTH),
        WEEK(TemporalConstants.WEEK),
        DAY(TemporalConstants.DAY),
        HOUR(TemporalConstants.HOUR),
        MINUTE(TemporalConstants.MINUTE),
        SECOND(TemporalConstants.SECOND),
        MILLISECOND(TemporalConstants.MILLISECOND),
        MICROSECOND(TemporalConstants.MICROSECOND),
        NANOSECOND(TemporalConstants.NANOSECOND);

        private final TruffleString name;

        private Unit(TruffleString name) {
            this.name = name;
        }

        public TruffleString toTruffleString() {
            return this.name;
        }
    }

    public static enum OptionType {
        STRING,
        NUMBER,
        BOOLEAN,
        NUMBER_AND_STRING;


        public boolean allowsNumber() {
            return this == NUMBER || this == NUMBER_AND_STRING;
        }

        public boolean allowsString() {
            return this == STRING || this == NUMBER_AND_STRING;
        }

        public boolean allowsBoolean() {
            return this == BOOLEAN;
        }

        public OptionType getLast() {
            switch (this) {
                case STRING: 
                case NUMBER_AND_STRING: {
                    return STRING;
                }
                case NUMBER: {
                    return NUMBER;
                }
                case BOOLEAN: {
                    return BOOLEAN;
                }
            }
            throw Errors.shouldNotReachHere();
        }
    }

    public static enum MatchBehaviour {
        MATCH_EXACTLY,
        MATCH_MINUTES;

    }

    public static enum OffsetBehaviour {
        OPTION,
        WALL,
        EXACT;

    }

    public static enum Overflow {
        CONSTRAIN,
        REJECT;

    }
}

