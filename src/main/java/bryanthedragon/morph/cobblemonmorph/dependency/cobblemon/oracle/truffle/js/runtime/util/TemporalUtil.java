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
   public static final Set<TruffleString> pluralUnits = Set.of(
      TemporalConstants.YEARS,
      TemporalConstants.MONTHS,
      TemporalConstants.WEEKS,
      TemporalConstants.DAYS,
      TemporalConstants.HOURS,
      TemporalConstants.MINUTES,
      TemporalConstants.SECONDS,
      TemporalConstants.MILLISECONDS,
      TemporalConstants.MICROSECONDS,
      TemporalConstants.NANOSECONDS
   );
   public static final Map<TruffleString, TruffleString> pluralToSingular = toMap(
      new TruffleString[]{
         TemporalConstants.YEARS,
         TemporalConstants.MONTHS,
         TemporalConstants.WEEKS,
         TemporalConstants.DAYS,
         TemporalConstants.HOURS,
         TemporalConstants.MINUTES,
         TemporalConstants.SECONDS,
         TemporalConstants.MILLISECONDS,
         TemporalConstants.MICROSECONDS,
         TemporalConstants.NANOSECONDS
      },
      new TruffleString[]{
         TemporalConstants.YEAR,
         TemporalConstants.MONTH,
         TemporalConstants.WEEK,
         TemporalConstants.DAY,
         TemporalConstants.HOUR,
         TemporalConstants.MINUTE,
         TemporalConstants.SECOND,
         TemporalConstants.MILLISECOND,
         TemporalConstants.MICROSECOND,
         TemporalConstants.NANOSECOND
      }
   );
   private static final Map<TruffleString, Function<Object, Object>> temporalFieldConversion = toMap(
      new TruffleString[]{
         TemporalConstants.YEAR,
         TemporalConstants.MONTH,
         TemporalConstants.MONTH_CODE,
         TemporalConstants.DAY,
         TemporalConstants.HOUR,
         TemporalConstants.MINUTE,
         TemporalConstants.SECOND,
         TemporalConstants.MILLISECOND,
         TemporalConstants.MICROSECOND,
         TemporalConstants.NANOSECOND,
         TemporalConstants.OFFSET,
         TemporalConstants.ERA,
         TemporalConstants.ERA_YEAR
      },
      new Function[]{
         toIntegerThrowOnInfinity,
         toPositiveInteger,
         toString,
         toPositiveInteger,
         toIntegerThrowOnInfinity,
         toIntegerThrowOnInfinity,
         toIntegerThrowOnInfinity,
         toIntegerThrowOnInfinity,
         toIntegerThrowOnInfinity,
         toIntegerThrowOnInfinity,
         toString,
         toString,
         toIntegerThrowOnInfinity
      }
   );
   public static final Map<TruffleString, Object> temporalFieldDefaults = toMap(
      new TruffleString[]{
         TemporalConstants.YEAR,
         TemporalConstants.MONTH,
         TemporalConstants.MONTH_CODE,
         TemporalConstants.DAY,
         TemporalConstants.HOUR,
         TemporalConstants.MINUTE,
         TemporalConstants.SECOND,
         TemporalConstants.MILLISECOND,
         TemporalConstants.MICROSECOND,
         TemporalConstants.NANOSECOND,
         TemporalConstants.YEARS,
         TemporalConstants.MONTHS,
         TemporalConstants.WEEKS,
         TemporalConstants.DAYS,
         TemporalConstants.HOURS,
         TemporalConstants.MINUTES,
         TemporalConstants.SECONDS,
         TemporalConstants.MILLISECONDS,
         TemporalConstants.MICROSECONDS,
         TemporalConstants.NANOSECONDS,
         TemporalConstants.OFFSET,
         TemporalConstants.ERA,
         TemporalConstants.ERA_YEAR
      },
      new Object[]{
         Undefined.instance,
         Undefined.instance,
         Undefined.instance,
         Undefined.instance,
         0,
         0,
         0,
         0,
         0,
         0,
         0,
         0,
         0,
         0,
         0,
         0,
         0,
         0,
         0,
         0,
         Undefined.instance,
         Undefined.instance,
         Undefined.instance
      }
   );
   public static final List<TruffleString> listEmpty = List.of();
   public static final List<TruffleString> listYMWD = List.of(TemporalConstants.YEAR, TemporalConstants.MONTH, TemporalConstants.WEEK, TemporalConstants.DAY);
   public static final List<TruffleString> listPluralYMWD = List.of(
      TemporalConstants.YEARS, TemporalConstants.MONTHS, TemporalConstants.WEEKS, TemporalConstants.DAYS
   );
   public static final List<TruffleString> listYMW = List.of(TemporalConstants.YEAR, TemporalConstants.MONTH, TemporalConstants.WEEK);
   public static final List<TruffleString> listYMWDH = List.of(
      TemporalConstants.YEAR, TemporalConstants.MONTH, TemporalConstants.WEEK, TemporalConstants.DAY, TemporalConstants.HOUR
   );
   public static final List<TruffleString> listTime = List.of(
      TemporalConstants.HOUR,
      TemporalConstants.MINUTE,
      TemporalConstants.SECOND,
      TemporalConstants.MILLISECOND,
      TemporalConstants.MICROSECOND,
      TemporalConstants.NANOSECOND
   );
   public static final List<TruffleString> listDMMCY = List.of(
      TemporalConstants.DAY, TemporalConstants.MONTH, TemporalConstants.MONTH_CODE, TemporalConstants.YEAR
   );
   public static final List<TruffleString> listMMCY = List.of(TemporalConstants.MONTH, TemporalConstants.MONTH_CODE, TemporalConstants.YEAR);
   public static final List<TruffleString> listMCY = List.of(TemporalConstants.MONTH_CODE, TemporalConstants.YEAR);
   public static final List<TruffleString> listDMC = List.of(TemporalConstants.DAY, TemporalConstants.MONTH_CODE);
   public static final List<TruffleString> listYD = List.of(TemporalConstants.YEAR, TemporalConstants.DAY);
   public static final List<TruffleString> listY = List.of(TemporalConstants.YEAR);
   public static final List<TruffleString> listD = List.of(TemporalConstants.DAY);
   public static final List<TruffleString> listWDHMSMMN = List.of(
      TemporalConstants.WEEK,
      TemporalConstants.DAY,
      TemporalConstants.HOUR,
      TemporalConstants.MINUTE,
      TemporalConstants.SECOND,
      TemporalConstants.MILLISECOND,
      TemporalConstants.MICROSECOND,
      TemporalConstants.NANOSECOND
   );
   public static final List<TruffleString> listAllDateTime = List.of(
      TemporalConstants.YEARS,
      TemporalConstants.YEAR,
      TemporalConstants.MONTHS,
      TemporalConstants.MONTH,
      TemporalConstants.WEEKS,
      TemporalConstants.WEEK,
      TemporalConstants.DAYS,
      TemporalConstants.DAY,
      TemporalConstants.HOURS,
      TemporalConstants.HOUR,
      TemporalConstants.MINUTES,
      TemporalConstants.MINUTE,
      TemporalConstants.SECONDS,
      TemporalConstants.SECOND,
      TemporalConstants.MILLISECONDS,
      TemporalConstants.MILLISECOND,
      TemporalConstants.MICROSECONDS,
      TemporalConstants.MICROSECOND,
      TemporalConstants.NANOSECONDS,
      TemporalConstants.NANOSECOND
   );
   public static final List<TruffleString> listAllDateTimeAuto = List.of(
      TemporalConstants.AUTO,
      TemporalConstants.YEARS,
      TemporalConstants.YEAR,
      TemporalConstants.MONTHS,
      TemporalConstants.MONTH,
      TemporalConstants.WEEKS,
      TemporalConstants.WEEK,
      TemporalConstants.DAYS,
      TemporalConstants.DAY,
      TemporalConstants.HOURS,
      TemporalConstants.HOUR,
      TemporalConstants.MINUTES,
      TemporalConstants.MINUTE,
      TemporalConstants.SECONDS,
      TemporalConstants.SECOND,
      TemporalConstants.MILLISECONDS,
      TemporalConstants.MILLISECOND,
      TemporalConstants.MICROSECONDS,
      TemporalConstants.MICROSECOND,
      TemporalConstants.NANOSECONDS,
      TemporalConstants.NANOSECOND
   );
   public static final List<TruffleString> listDHMMMMMNSY = List.of(
      TemporalConstants.DAY,
      TemporalConstants.HOUR,
      TemporalConstants.MICROSECOND,
      TemporalConstants.MILLISECOND,
      TemporalConstants.MINUTE,
      TemporalConstants.MONTH,
      TemporalConstants.MONTH_CODE,
      TemporalConstants.NANOSECOND,
      TemporalConstants.SECOND,
      TemporalConstants.YEAR
   );
   public static final List<TruffleString> listAuto = List.of(TemporalConstants.AUTO);
   public static final List<TruffleString> listAutoNever = List.of(TemporalConstants.AUTO, TemporalConstants.NEVER);
   public static final List<TruffleString> listAutoAlwaysNever = List.of(TemporalConstants.AUTO, TemporalConstants.ALWAYS, TemporalConstants.NEVER);
   public static final List<TruffleString> listConstrainReject = List.of(TemporalConstants.CONSTRAIN, TemporalConstants.REJECT);
   public static final List<TruffleString> listTimeZone = List.of(TemporalConstants.TIME_ZONE);
   public static final List<TruffleString> listTimeZoneOffset = List.of(TemporalConstants.TIME_ZONE, TemporalConstants.OFFSET);
   public static final List<TruffleString> listRoundingMode = List.of(
      TemporalConstants.CEIL, TemporalConstants.FLOOR, TemporalConstants.TRUNC, TemporalConstants.HALF_EXPAND
   );
   public static final List<TruffleString> listOffset = List.of(
      TemporalConstants.PREFER, TemporalConstants.USE, TemporalConstants.IGNORE, TemporalConstants.REJECT
   );
   public static final List<TruffleString> listDisambiguation = List.of(
      TemporalConstants.COMPATIBLE, TemporalConstants.EARLIER, TemporalConstants.LATER, TemporalConstants.REJECT
   );
   public static final TruffleString[] TIME_LIKE_PROPERTIES = new TruffleString[]{
      TemporalConstants.HOUR,
      TemporalConstants.MICROSECOND,
      TemporalConstants.MILLISECOND,
      TemporalConstants.MINUTE,
      TemporalConstants.NANOSECOND,
      TemporalConstants.SECOND
   };
   public static final TemporalUtil.UnitPlural[] DURATION_PROPERTIES = new TemporalUtil.UnitPlural[]{
      TemporalUtil.UnitPlural.DAYS,
      TemporalUtil.UnitPlural.HOURS,
      TemporalUtil.UnitPlural.MICROSECONDS,
      TemporalUtil.UnitPlural.MILLISECONDS,
      TemporalUtil.UnitPlural.MINUTES,
      TemporalUtil.UnitPlural.MONTHS,
      TemporalUtil.UnitPlural.NANOSECONDS,
      TemporalUtil.UnitPlural.SECONDS,
      TemporalUtil.UnitPlural.WEEKS,
      TemporalUtil.UnitPlural.YEARS
   };
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
   public static final char UNICODE_MINUS_SIGN = '−';
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

   public static double defaultNumberOptions(Object value, double minimum, double maximum, double fallback, JSToNumberNode toNumber) {
      if (value == Undefined.instance) {
         return fallback;
      } else {
         double numberValue = JSRuntime.doubleValue(toNumber.executeNumber(value));
         if (!Double.isNaN(numberValue)
            && !(numberValue < minimum)
            && !(numberValue > maximum)
            && (!Double.isInfinite(numberValue) || !Double.isInfinite(maximum))) {
            return Math.floor(numberValue);
         } else {
            throw Errors.createRangeError("Numeric value out of range.");
         }
      }
   }

   public static double getNumberOption(
      JSDynamicObject options, TruffleString property, double minimum, double maximum, double fallback, IsObjectNode isObject, JSToNumberNode numberNode
   ) {
      assert isObject.executeBoolean(options);

      Object value = JSObject.get(options, property);
      return defaultNumberOptions(value, minimum, maximum, fallback, numberNode);
   }

   public static Object getStringOrNumberOption(
      JSDynamicObject options,
      TruffleString property,
      List<TruffleString> stringValues,
      double minimum,
      double maximum,
      Object fallback,
      JSToStringNode toStringNode,
      TemporalGetOptionNode getOptionNode
   ) {
      assert JSRuntime.isObject(options);

      Object value = getOptionNode.execute(options, property, TemporalUtil.OptionType.NUMBER_AND_STRING, null, fallback);
      if (value instanceof Number) {
         double numberValue = JSRuntime.doubleValue((Number)value);
         if (!Double.isNaN(numberValue) && !(numberValue < minimum) && !(numberValue > maximum)) {
            return Math.floor(numberValue);
         } else {
            throw Errors.createRangeError("Numeric value out of range.");
         }
      } else {
         value = toStringNode.executeString(value);
         if (stringValues != null && !Boundaries.listContainsUnchecked(stringValues, value)) {
            throw Errors.createRangeError("Given string value is not in string values");
         } else {
            return value;
         }
      }
   }

   public static double toTemporalRoundingIncrement(JSDynamicObject options, Double dividend, boolean inclusive, IsObjectNode isObject, JSToNumberNode toNumber) {
      double dDividend = Double.NaN;
      double maximum;
      if (dividend == null) {
         maximum = Double.POSITIVE_INFINITY;
      } else {
         dDividend = JSRuntime.doubleValue(dividend);
         if (inclusive) {
            maximum = dDividend;
         } else if (dDividend > 1.0) {
            maximum = dDividend - 1.0;
         } else {
            maximum = 1.0;
         }
      }

      double increment = getNumberOption(options, TemporalConstants.ROUNDING_INCREMENT, 1.0, maximum, 1.0, isObject, toNumber);
      if (dividend != null && dDividend % increment != 0.0) {
         throw Errors.createRangeError("Increment out of range.");
      } else {
         return increment;
      }
   }

   public static JSTemporalPrecisionRecord toSecondsStringPrecision(
      JSDynamicObject options, JSToStringNode toStringNode, TemporalGetOptionNode getOptionNode, TruffleString.EqualNode equalNode
   ) {
      TemporalUtil.Unit smallestUnit = toSmallestTemporalUnit(options, listYMWDH, null, getOptionNode, equalNode);
      if (TemporalUtil.Unit.MINUTE == smallestUnit) {
         return JSTemporalPrecisionRecord.create(TemporalConstants.MINUTE, TemporalUtil.Unit.MINUTE, 1.0);
      } else if (TemporalUtil.Unit.SECOND == smallestUnit) {
         return JSTemporalPrecisionRecord.create(0, TemporalUtil.Unit.SECOND, 1.0);
      } else if (TemporalUtil.Unit.MILLISECOND == smallestUnit) {
         return JSTemporalPrecisionRecord.create(3, TemporalUtil.Unit.MILLISECOND, 1.0);
      } else if (TemporalUtil.Unit.MICROSECOND == smallestUnit) {
         return JSTemporalPrecisionRecord.create(6, TemporalUtil.Unit.MICROSECOND, 1.0);
      } else if (TemporalUtil.Unit.NANOSECOND == smallestUnit) {
         return JSTemporalPrecisionRecord.create(9, TemporalUtil.Unit.NANOSECOND, 1.0);
      } else {
         assert smallestUnit == TemporalUtil.Unit.EMPTY;

         Object digits = getStringOrNumberOption(options, FRACTIONAL_SECOND_DIGITS, listAuto, 0.0, 9.0, TemporalConstants.AUTO, toStringNode, getOptionNode);
         if (Boundaries.equals(digits, TemporalConstants.AUTO)) {
            return JSTemporalPrecisionRecord.create(TemporalConstants.AUTO, TemporalUtil.Unit.NANOSECOND, 1.0);
         } else {
            int iDigit = JSRuntime.intValue((Number)digits);
            if (iDigit == 0) {
               return JSTemporalPrecisionRecord.create(0, TemporalUtil.Unit.SECOND, 1.0);
            } else if (iDigit == 1 || iDigit == 2 || iDigit == 3) {
               return JSTemporalPrecisionRecord.create(digits, TemporalUtil.Unit.MILLISECOND, Math.pow(10.0, 3L - toLong(digits)));
            } else if (iDigit == 4 || iDigit == 5 || iDigit == 6) {
               return JSTemporalPrecisionRecord.create(digits, TemporalUtil.Unit.MICROSECOND, Math.pow(10.0, 6L - toLong(digits)));
            } else {
               assert iDigit == 7 || iDigit == 8 || iDigit == 9;

               return JSTemporalPrecisionRecord.create(digits, TemporalUtil.Unit.NANOSECOND, Math.pow(10.0, 9L - toLong(digits)));
            }
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static long toLong(Object digits) {
      return digits instanceof Number ? ((Number)digits).longValue() : JSRuntime.toNumber(digits).longValue();
   }

   public static TemporalUtil.Unit toSmallestTemporalUnit(
      JSDynamicObject normalizedOptions,
      List<TruffleString> disallowedUnits,
      TruffleString fallback,
      TemporalGetOptionNode getOptionNode,
      TruffleString.EqualNode equalNode
   ) {
      TruffleString smallestUnit = (TruffleString)getOptionNode.execute(
         normalizedOptions, TemporalConstants.SMALLEST_UNIT, TemporalUtil.OptionType.STRING, listAllDateTime, fallback
      );
      if (smallestUnit != null && Boundaries.setContains(pluralUnits, smallestUnit)) {
         smallestUnit = Boundaries.mapGet(pluralToSingular, smallestUnit);
      }

      if (smallestUnit != null && Boundaries.listContains(disallowedUnits, smallestUnit)) {
         throw Errors.createRangeError("Smallest unit not allowed.");
      } else {
         return toUnit(smallestUnit, equalNode);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalZonedDateTimeRecord parseTemporalRelativeToString(TruffleString isoString) {
      if (!new TemporalParser(isoString).isTemporalDateTimeString()) {
         throw TemporalErrors.createRangeErrorInvalidRelativeToString();
      } else {
         JSTemporalDateTimeRecord result = parseISODateTime(isoString, false, false);
         boolean z = false;
         TruffleString offsetString = null;
         TruffleString timeZone = null;
         if (!isoString.isEmpty()) {
            try {
               JSTemporalTimeZoneRecord timeZoneResult = parseTemporalTimeZoneString(isoString);
               z = timeZoneResult.isZ();
               offsetString = timeZoneResult.getOffsetString();
               timeZone = timeZoneResult.getName();
            } catch (Exception var6) {
            }
         }

         return JSTemporalZonedDateTimeRecord.create(
            result.getYear(),
            result.getMonth(),
            result.getDay(),
            result.getHour(),
            result.getMinute(),
            result.getSecond(),
            result.getMillisecond(),
            result.getMicrosecond(),
            result.getNanosecond(),
            result.getCalendar(),
            z,
            offsetString,
            timeZone
         );
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalDateTimeRecord parseTemporalMonthDayString(TruffleString string) {
      JSTemporalParserRecord rec = new TemporalParser(string).parseMonthDay();
      if (rec != null) {
         if (rec.getZ()) {
            throw TemporalErrors.createRangeErrorUnexpectedUTCDesignator();
         } else if (rec.getYear() != 0L
            || Strings.indexOf(string, TemporalConstants.MINUS_000000) < 0 && Strings.indexOf(string, TemporalConstants.UNICODE_MINUS_SIGN_000000) < 0) {
            int y = rec.getYear() == Long.MIN_VALUE ? Integer.MIN_VALUE : ltoi(rec.getYear());
            int m = rec.getMonth() == Long.MIN_VALUE ? 1 : ltoi(rec.getMonth());
            int d = rec.getDay() == Long.MIN_VALUE ? 1 : ltoi(rec.getDay());
            if (!isValidISODate(y, m, d)) {
               throw TemporalErrors.createRangeErrorDateOutsideRange();
            } else {
               return JSTemporalDateTimeRecord.createCalendar(y, m, d, 0, 0, 0, 0, 0, 0, rec.getCalendar());
            }
         } else {
            throw TemporalErrors.createRangeErrorInvalidPlainDateTime();
         }
      } else {
         throw Errors.createRangeError("cannot parse MonthDay");
      }
   }

   private static JSTemporalDateTimeRecord parseISODateTime(TruffleString string) {
      return parseISODateTime(string, false, false);
   }

   @CompilerDirectives.TruffleBoundary
   private static JSTemporalDateTimeRecord parseISODateTime(TruffleString string, boolean failWithUTCDesignator, boolean timeExpected) {
      JSTemporalParserRecord rec = new TemporalParser(string).parseISODateTime();
      if (rec != null) {
         if (failWithUTCDesignator && rec.getZ()) {
            throw TemporalErrors.createRangeErrorUnexpectedUTCDesignator();
         } else if (timeExpected && rec.getHour() == Long.MIN_VALUE) {
            throw Errors.createRangeError("cannot parse the ISO date time string");
         } else {
            return parseISODateTimeIntl(string, rec);
         }
      } else {
         throw Errors.createRangeError("cannot parse the ISO date time string");
      }
   }

   private static JSTemporalDateTimeRecord parseISODateTimeIntl(TruffleString string, JSTemporalParserRecord rec) {
      TruffleString fraction = rec.getFraction();
      if (fraction == null) {
         fraction = ZEROS;
      } else {
         fraction = Strings.concat(fraction, ZEROS);
      }

      if (rec.getYear() != 0L
         || Strings.indexOf(string, TemporalConstants.MINUS_000000) < 0 && Strings.indexOf(string, TemporalConstants.UNICODE_MINUS_SIGN_000000) < 0) {
         int y = rec.getYear() == Long.MIN_VALUE ? 0 : ltoi(rec.getYear());
         int m = rec.getMonth() == Long.MIN_VALUE ? 1 : ltoi(rec.getMonth());
         int d = rec.getDay() == Long.MIN_VALUE ? 1 : ltoi(rec.getDay());
         int h = rec.getHour() == Long.MIN_VALUE ? 0 : ltoi(rec.getHour());
         int min = rec.getMinute() == Long.MIN_VALUE ? 0 : ltoi(rec.getMinute());
         int s = rec.getSecond() == Long.MIN_VALUE ? 0 : ltoi(rec.getSecond());
         int ms = 0;
         int mus = 0;
         int ns = 0;

         try {
            ms = (int)Strings.parseLong(Strings.lazySubstring(fraction, 0, 3));
            mus = (int)Strings.parseLong(Strings.lazySubstring(fraction, 3, 3));
            ns = (int)Strings.parseLong(Strings.lazySubstring(fraction, 6, 3));
         } catch (TruffleString.NumberFormatException var13) {
            throw CompilerDirectives.shouldNotReachHere(var13);
         }

         if (s == 60) {
            s = 59;
         }

         if (!isValidISODate(y, m, d)) {
            throw TemporalErrors.createRangeErrorDateOutsideRange();
         } else if (!isValidTime(h, min, s, ms, mus, ns)) {
            throw TemporalErrors.createRangeErrorTimeOutsideRange();
         } else {
            return JSTemporalDateTimeRecord.createCalendar(y, m, d, h, min, s, ms, mus, ns, rec.getCalendar());
         }
      } else {
         throw TemporalErrors.createRangeErrorInvalidPlainDateTime();
      }
   }

   public static void validateTemporalUnitRange(TemporalUtil.Unit largestUnit, TemporalUtil.Unit smallestUnit) {
      boolean error = false;
      switch (smallestUnit) {
         case YEAR:
            if (largestUnit != TemporalUtil.Unit.YEAR) {
               error = true;
            }
            break;
         case MONTH:
            if (largestUnit != TemporalUtil.Unit.YEAR && largestUnit != TemporalUtil.Unit.MONTH) {
               error = true;
            }
            break;
         case WEEK:
            if (largestUnit != TemporalUtil.Unit.YEAR && largestUnit != TemporalUtil.Unit.MONTH && largestUnit != TemporalUtil.Unit.WEEK) {
               error = true;
            }
            break;
         case DAY:
            if (largestUnit != TemporalUtil.Unit.YEAR
               && largestUnit != TemporalUtil.Unit.MONTH
               && largestUnit != TemporalUtil.Unit.WEEK
               && largestUnit != TemporalUtil.Unit.DAY) {
               error = true;
            }
            break;
         case HOUR:
            if (largestUnit != TemporalUtil.Unit.YEAR
               && largestUnit != TemporalUtil.Unit.MONTH
               && largestUnit != TemporalUtil.Unit.WEEK
               && largestUnit != TemporalUtil.Unit.DAY
               && largestUnit != TemporalUtil.Unit.HOUR) {
               error = true;
            }
            break;
         case MINUTE:
            if (largestUnit == TemporalUtil.Unit.SECOND
               || largestUnit == TemporalUtil.Unit.MILLISECOND
               || largestUnit == TemporalUtil.Unit.MICROSECOND
               || largestUnit == TemporalUtil.Unit.NANOSECOND) {
               error = true;
            }
            break;
         case SECOND:
            if (largestUnit == TemporalUtil.Unit.MILLISECOND || largestUnit == TemporalUtil.Unit.MICROSECOND || largestUnit == TemporalUtil.Unit.NANOSECOND) {
               error = true;
            }
            break;
         case MILLISECOND:
            if (largestUnit == TemporalUtil.Unit.MICROSECOND || largestUnit == TemporalUtil.Unit.NANOSECOND) {
               error = true;
            }
            break;
         case MICROSECOND:
            if (largestUnit == TemporalUtil.Unit.NANOSECOND) {
               error = true;
            }
      }

      if (error) {
         throw TemporalErrors.createRangeErrorSmallestUnitOutOfRange();
      }
   }

   public static Double maximumTemporalDurationRoundingIncrement(TemporalUtil.Unit unit) {
      if (unit == TemporalUtil.Unit.YEAR || unit == TemporalUtil.Unit.MONTH || unit == TemporalUtil.Unit.WEEK || unit == TemporalUtil.Unit.DAY) {
         return null;
      } else if (unit == TemporalUtil.Unit.HOUR) {
         return 24.0;
      } else if (unit == TemporalUtil.Unit.MINUTE || unit == TemporalUtil.Unit.SECOND) {
         return 60.0;
      } else {
         assert unit == TemporalUtil.Unit.MILLISECOND || unit == TemporalUtil.Unit.MICROSECOND || unit == TemporalUtil.Unit.NANOSECOND;

         return 1000.0;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString formatSecondsStringPart(long second, long millisecond, long microsecond, long nanosecond, Object precision) {
      if (precision.equals(TemporalConstants.MINUTE)) {
         return Strings.EMPTY_STRING;
      } else {
         TruffleString secondString = Strings.format(":%1$02d", second);
         long fraction = millisecond * 1000000L + microsecond * 1000L + nanosecond;
         TruffleString fractionString = Strings.EMPTY_STRING;
         if (precision.equals(TemporalConstants.AUTO)) {
            if (fraction == 0L) {
               return secondString;
            }

            fractionString = Strings.concatAll(
               fractionString, Strings.format("%1$03d", millisecond), Strings.format("%1$03d", microsecond), Strings.format("%1$03d", nanosecond)
            );
            fractionString = longestSubstring(fractionString);
         } else {
            if (precision.equals(0)) {
               return secondString;
            }

            fractionString = Strings.concatAll(
               fractionString, Strings.format("%1$03d", millisecond), Strings.format("%1$03d", microsecond), Strings.format("%1$03d", nanosecond)
            );
            fractionString = Strings.lazySubstring(fractionString, 0, (int)toLong(precision));
         }

         return Strings.concatAll(secondString, Strings.DOT, fractionString);
      }
   }

   private static TruffleString longestSubstring(TruffleString str) {
      int length = Strings.length(str);

      while (length > 0 && Strings.charAt(str, length - 1) == '0') {
         length--;
      }

      if (length == 0) {
         return Strings.EMPTY_STRING;
      } else if (length == Strings.length(str)) {
         return str;
      } else {
         assert Strings.length(str) <= 9;

         return Strings.lazySubstring(str, 0, length);
      }
   }

   public static double nonNegativeModulo(double x, double y) {
      double result = x % y;
      if (result == 0.0) {
         return 0.0;
      } else {
         if (result < 0.0) {
            result += y;
         }

         return result;
      }
   }

   public static int constrainToRange(long value, int minimum, int maximum) {
      return (int)Math.min(Math.max(value, (long)minimum), (long)maximum);
   }

   public static TemporalUtil.UnsignedRoundingMode getUnsignedRoundingMode(TemporalUtil.RoundingMode rm, boolean isNegative) {
      switch (rm) {
         case CEIL:
            return isNegative ? TemporalUtil.UnsignedRoundingMode.ZERO : TemporalUtil.UnsignedRoundingMode.INFINITY;
         case FLOOR:
            return isNegative ? TemporalUtil.UnsignedRoundingMode.INFINITY : TemporalUtil.UnsignedRoundingMode.ZERO;
         case EXPAND:
            return TemporalUtil.UnsignedRoundingMode.INFINITY;
         case TRUNC:
            return TemporalUtil.UnsignedRoundingMode.ZERO;
         case HALF_CEIL:
            return isNegative ? TemporalUtil.UnsignedRoundingMode.HALF_ZERO : TemporalUtil.UnsignedRoundingMode.HALF_INFINITY;
         case HALF_FLOOR:
            return isNegative ? TemporalUtil.UnsignedRoundingMode.HALF_INFINITY : TemporalUtil.UnsignedRoundingMode.HALF_ZERO;
         case HALF_EXPAND:
            return TemporalUtil.UnsignedRoundingMode.HALF_INFINITY;
         case HALF_TRUNC:
            return TemporalUtil.UnsignedRoundingMode.HALF_ZERO;
         case HALF_EVEN:
            return TemporalUtil.UnsignedRoundingMode.HALF_EVEN;
         default:
            return TemporalUtil.UnsignedRoundingMode.EMPTY;
      }
   }

   public static double applyUnsignedRoundingMode(double x, double r1, double r2, TemporalUtil.UnsignedRoundingMode urm) {
      if (x == r1) {
         return r1;
      } else {
         assert r1 < x && x < r2;

         assert urm != TemporalUtil.UnsignedRoundingMode.EMPTY;

         if (urm == TemporalUtil.UnsignedRoundingMode.ZERO) {
            return r1;
         } else if (urm == TemporalUtil.UnsignedRoundingMode.INFINITY) {
            return r2;
         } else {
            double d1 = x - r1;
            double d2 = r2 - x;
            if (d1 < d2) {
               return r1;
            } else if (d2 < d1) {
               return r2;
            } else {
               assert d1 == d2;

               if (urm == TemporalUtil.UnsignedRoundingMode.HALF_ZERO) {
                  return r1;
               } else if (urm == TemporalUtil.UnsignedRoundingMode.HALF_INFINITY) {
                  return r2;
               } else {
                  assert urm == TemporalUtil.UnsignedRoundingMode.HALF_EVEN;

                  double cardinality = r1 / (r2 - r1) % 2.0;
                  return cardinality == 0.0 ? r1 : r2;
               }
            }
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInteger roundNumberToIncrement(BigDecimal x, BigDecimal increment, TemporalUtil.RoundingMode roundingMode) {
      assert roundingMode == TemporalUtil.RoundingMode.CEIL
         || roundingMode == TemporalUtil.RoundingMode.FLOOR
         || roundingMode == TemporalUtil.RoundingMode.TRUNC
         || roundingMode == TemporalUtil.RoundingMode.HALF_EXPAND;

      BigDecimal[] divRes = x.divideAndRemainder(increment);
      BigDecimal quotient = divRes[0];
      BigDecimal remainder = divRes[1];
      int sign = remainder.signum() < 0 ? -1 : 1;
      if (roundingMode == TemporalUtil.RoundingMode.CEIL) {
         if (sign > 0) {
            quotient = quotient.add(BigDecimal.ONE);
         }
      } else if (roundingMode == TemporalUtil.RoundingMode.FLOOR) {
         if (sign < 0) {
            quotient = quotient.add(BigDecimal.valueOf(-1L));
         }
      } else if (roundingMode != TemporalUtil.RoundingMode.TRUNC) {
         assert roundingMode == TemporalUtil.RoundingMode.HALF_EXPAND;

         if (remainder.multiply(BigDecimal.valueOf(2L)).abs().compareTo(increment) >= 0) {
            quotient = quotient.add(BigDecimal.valueOf((long)sign));
         }
      }

      BigDecimal result = quotient.multiply(increment);
      return result.toBigInteger();
   }

   @CompilerDirectives.TruffleBoundary
   public static double roundNumberToIncrement(double x, double increment, TemporalUtil.RoundingMode roundingMode) {
      assert roundingMode == TemporalUtil.RoundingMode.CEIL
         || roundingMode == TemporalUtil.RoundingMode.FLOOR
         || roundingMode == TemporalUtil.RoundingMode.TRUNC
         || roundingMode == TemporalUtil.RoundingMode.HALF_EXPAND;

      double quotient = x / increment;
      double rounded = 0.0;
      if (roundingMode == TemporalUtil.RoundingMode.CEIL) {
         rounded = -Math.floor(-quotient);
      } else if (roundingMode == TemporalUtil.RoundingMode.FLOOR) {
         rounded = Math.floor(quotient);
      } else if (roundingMode == TemporalUtil.RoundingMode.TRUNC) {
         if (quotient > 0.0) {
            rounded = Math.floor(quotient);
         } else {
            rounded = Math.ceil(quotient);
         }
      } else if (roundingMode == TemporalUtil.RoundingMode.HALF_EXPAND) {
         rounded = roundHalfAwayFromZero(quotient);
      }

      return rounded * increment;
   }

   @CompilerDirectives.TruffleBoundary
   public static double roundHalfAwayFromZero(double x) {
      return x >= 0.0 ? Math.round(x) : -Math.round(-x);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString parseTemporalCalendarString(TruffleString string) {
      JSTemporalParserRecord rec = new TemporalParser(string).parseCalendarString();
      if (rec == null) {
         throw Errors.createRangeError("cannot parse Calendar");
      } else {
         TruffleString id = rec.getCalendar();
         return id == null ? TemporalConstants.ISO8601 : id;
      }
   }

   public static double toPositiveInteger(Object value) {
      double result = JSRuntime.doubleValue(toIntegerThrowOnInfinity(value));
      if (result <= 0.0) {
         throw Errors.createRangeError("positive value expected");
      } else {
         return result;
      }
   }

   public static int toPositiveIntegerConstrainInt(Object value, JSToIntegerThrowOnInfinityNode toIntegerThrowOnInfinityNode, BranchProfile errorBranch) {
      int integer = toIntegerThrowOnInfinityNode.executeIntOrThrow(value);
      if (integer <= 0) {
         errorBranch.enter();
         throw Errors.createRangeError("positive value expected");
      } else {
         return integer;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject prepareTemporalFields(
      JSContext ctx, JSDynamicObject fields, List<TruffleString> fieldNames, List<TruffleString> requiredFields
   ) {
      JSDynamicObject result = JSOrdinary.createWithNullPrototype(ctx);

      for (TruffleString property : fieldNames) {
         Object value = JSObject.get(fields, property);

         assert value != null;

         if (value == Undefined.instance) {
            if (requiredFields.contains(property)) {
               throw TemporalErrors.createTypeErrorPropertyRequired(property);
            }

            if (temporalFieldDefaults.containsKey(property)) {
               value = temporalFieldDefaults.get(property);
            }
         } else if (temporalFieldConversion.containsKey(property)) {
            Function<Object, Object> conversion = temporalFieldConversion.get(property);
            value = conversion.apply(value);
         }

         createDataPropertyOrThrow(ctx, result, property, value);
      }

      return result;
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject preparePartialTemporalFields(JSContext ctx, JSDynamicObject fields, List<TruffleString> fieldNames) {
      JSDynamicObject result = JSOrdinary.createWithNullPrototype(ctx);
      boolean any = false;

      for (TruffleString property : fieldNames) {
         Object value = JSObject.get(fields, property);

         assert value != null;

         if (value != Undefined.instance) {
            any = true;
            if (temporalFieldConversion.containsKey(property)) {
               Function<Object, Object> conversion = temporalFieldConversion.get(property);
               value = conversion.apply(value);
            }
         }

         createDataPropertyOrThrow(ctx, result, property, value);
      }

      if (!any) {
         throw Errors.createTypeError("Given dateTime like object has no relevant properties.");
      } else {
         return result;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static <T, I> Map<T, I> toMap(T[] keys, I[] values) {
      Map<T, I> map = new HashMap<>();

      for (int i = 0; i < keys.length; i++) {
         map.put(keys[i], values[i]);
      }

      return map;
   }

   public static JSTemporalYearMonthDayRecord regulateISOYearMonth(int year, int month, TemporalUtil.Overflow overflow) {
      assert TemporalUtil.Overflow.CONSTRAIN == overflow || TemporalUtil.Overflow.REJECT == overflow;

      if (TemporalUtil.Overflow.CONSTRAIN == overflow) {
         return constrainISOYearMonth(year, month);
      } else {
         assert TemporalUtil.Overflow.REJECT == overflow;

         if (!isValidISOMonth(month)) {
            throw Errors.createRangeError("validation of year and month failed");
         } else {
            return JSTemporalYearMonthDayRecord.create(year, month);
         }
      }
   }

   private static boolean isValidISOMonth(int month) {
      return 1 <= month && month <= 12;
   }

   private static JSTemporalYearMonthDayRecord constrainISOYearMonth(int year, int month) {
      int monthPrepared = constrainToRange(month, 1, 12);
      return JSTemporalYearMonthDayRecord.create(ltoi(year), monthPrepared);
   }

   public static long toISODayOfWeek(int year, int month, int day) {
      int m = month - 2;
      if (m == -1) {
         m = 11;
      } else if (m == 0) {
         m = 12;
      }

      int c = Math.floorDiv(year, 100);
      int y = Math.floorMod(year, 100);
      if (m == 11 || m == 12) {
         y--;
      }

      int weekDay = Math.floorMod(day + (long)Math.floor(2.6 * m - 0.2) - 2 * c + y + Math.floorDiv(y, 4) + Math.floorDiv(c, 4), 7);
      return weekDay == 0 ? 7L : weekDay;
   }

   public static int toISODayOfYear(int year, int month, int day) {
      int days = 0;

      for (int m = 1; m < month; m++) {
         days += isoDaysInMonth(year, m);
      }

      return days + day;
   }

   public static long toISOWeekOfYear(int year, int month, int day) {
      long doy = toISODayOfYear(year, month, day);
      long dow = toISODayOfWeek(year, month, day);
      long doj = toISODayOfWeek(year, 1, 1);
      long week = Math.floorDiv(doy - dow + 10L, 7);
      if (week >= 1L) {
         return week == 53L && isoDaysInYear(year) - doy < 4L - dow ? 1L : week;
      } else {
         return doj != 5L && (doj != 6L || !isISOLeapYear(year - 1)) ? 52L : 53L;
      }
   }

   public static boolean isISOLeapYear(int year) {
      return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
   }

   public static int isoDaysInYear(int year) {
      return isISOLeapYear(year) ? 366 : 365;
   }

   public static int isoDaysInMonth(int year, int month) {
      assert month >= 1 && month <= 12;

      if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
         return 31;
      } else if (month == 4 || month == 6 || month == 9 || month == 11) {
         return 30;
      } else {
         return isISOLeapYear(year) ? 29 : 28;
      }
   }

   public static JSTemporalDateTimeRecord balanceISOYearMonth(int year, int month) {
      if (year != Integer.MAX_VALUE && year != Integer.MIN_VALUE && month != Integer.MAX_VALUE && month != Integer.MIN_VALUE) {
         int yearPrepared = (int)(year + Math.floor((month - 1.0) / 12.0));
         int monthPrepared = (int)nonNegativeModulo(month - 1, 12.0) + 1;
         return JSTemporalDateTimeRecord.create(yearPrepared, monthPrepared, 0, 0, 0, 0, 0, 0, 0);
      } else {
         throw Errors.createRangeError("value out of range");
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean isBuiltinCalendar(TruffleString id) {
      return id.equals(TemporalConstants.ISO8601) || id.equals(TemporalConstants.GREGORY) || id.equals(TemporalConstants.JAPANESE);
   }

   public static JSTemporalCalendarObject getISO8601Calendar(JSContext ctx, JSRealm realm, BranchProfile errorBranch) {
      return getBuiltinCalendar(TemporalConstants.ISO8601, ctx, realm, errorBranch);
   }

   public static JSTemporalCalendarObject getISO8601Calendar(JSContext ctx, JSRealm realm) {
      return getBuiltinCalendar(TemporalConstants.ISO8601, ctx, realm);
   }

   public static JSTemporalCalendarObject getBuiltinCalendar(TruffleString id, JSContext ctx, JSRealm realm, BranchProfile errorBranch) {
      if (!isBuiltinCalendar(id)) {
         errorBranch.enter();
         throw TemporalErrors.createRangeErrorCalendarNotSupported();
      } else {
         return JSTemporalCalendar.create(ctx, realm, id, errorBranch);
      }
   }

   public static JSTemporalCalendarObject getBuiltinCalendar(TruffleString id, JSContext ctx, JSRealm realm) {
      if (!isBuiltinCalendar(id)) {
         throw TemporalErrors.createRangeErrorCalendarNotSupported();
      } else {
         return JSTemporalCalendar.create(ctx, realm, id);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject toTemporalCalendar(JSContext ctx, Object temporalCalendarLikeParam) {
      Object temporalCalendarLike = temporalCalendarLikeParam;
      if (JSRuntime.isObject(temporalCalendarLikeParam)) {
         JSDynamicObject obj = toJSDynamicObject(temporalCalendarLikeParam, null);
         if (temporalCalendarLikeParam instanceof TemporalCalendar) {
            return ((TemporalCalendar)temporalCalendarLikeParam).getCalendar();
         }

         if (!JSObject.hasProperty(obj, TemporalConstants.CALENDAR)) {
            return obj;
         }

         temporalCalendarLike = JSObject.get(obj, TemporalConstants.CALENDAR);
         if (JSRuntime.isObject(temporalCalendarLike)) {
            JSDynamicObject tclObj = toJSDynamicObject(temporalCalendarLike, null);
            if (!JSObject.hasProperty(tclObj, TemporalConstants.CALENDAR)) {
               return tclObj;
            }
         }
      }

      TruffleString identifier = JSRuntime.toString(temporalCalendarLike);
      if (!isBuiltinCalendar(identifier)) {
         identifier = parseTemporalCalendarString(identifier);
         if (!isBuiltinCalendar(identifier)) {
            throw TemporalErrors.createRangeErrorCalendarUnknown();
         }
      }

      return JSTemporalCalendar.create(ctx, null, identifier);
   }

   @CompilerDirectives.TruffleBoundary
   public static List<TruffleString> iterableToListOfTypeString(JSDynamicObject items) {
      IteratorRecord iter = JSRuntime.getIterator(items);
      List<TruffleString> values = new ArrayList<>();
      Object next = Boolean.TRUE;

      while (next != Boolean.FALSE) {
         next = JSRuntime.iteratorStep(iter);
         if (next != Boolean.FALSE) {
            Object nextValue = JSRuntime.iteratorValue((JSDynamicObject)next);
            if (!Strings.isTString(nextValue)) {
               JSRuntime.iteratorClose(iter.getIterator());
               throw Errors.createTypeError("string expected");
            }

            TruffleString str = JSRuntime.toString(nextValue);
            values.add(str);
         }
      }

      return values;
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalDateTimeRecord parseTemporalDateTimeString(TruffleString string) {
      JSTemporalParserRecord rec = new TemporalParser(string).parseCalendarDateTime();
      if (rec == null) {
         throw Errors.createRangeError("cannot parse the date string");
      } else if (rec.getZ()) {
         throw TemporalErrors.createRangeErrorUnexpectedUTCDesignator();
      } else {
         return parseISODateTime(string, true, false);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalDateTimeRecord parseTemporalDateString(TruffleString string) {
      JSTemporalDateTimeRecord rec = parseTemporalDateTimeString(string);
      return JSTemporalDateTimeRecord.createCalendar(rec.getYear(), rec.getMonth(), rec.getDay(), 0, 0, 0, 0, 0, 0, rec.getCalendar());
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalDateTimeRecord parseTemporalTimeString(TruffleString string) {
      JSTemporalDateTimeRecord result = parseISODateTime(string, true, true);
      return result.hasCalendar()
         ? JSTemporalDateTimeRecord.createCalendar(
            0,
            0,
            0,
            result.getHour(),
            result.getMinute(),
            result.getSecond(),
            result.getMillisecond(),
            result.getMicrosecond(),
            result.getNanosecond(),
            result.getCalendar()
         )
         : JSTemporalDateTimeRecord.create(
            0, 0, 0, result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), result.getNanosecond()
         );
   }

   @CompilerDirectives.TruffleBoundary
   public static Object buildISOMonthCode(int month) {
      TruffleString numberPart = Strings.fromInt(month);

      assert 1 <= Strings.length(numberPart) && Strings.length(numberPart) <= 2;

      return Strings.concat(Strings.length(numberPart) >= 2 ? Strings.UC_M : Strings.UC_M0, numberPart);
   }

   public static TruffleString isoMonthCode(TemporalMonth date) {
      long month = date.getMonth();
      return buildISOMonthCode(month);
   }

   @CompilerDirectives.TruffleBoundary
   private static TruffleString buildISOMonthCode(long month) {
      TruffleString monthCode = Strings.format("%1$02d", month);
      return Strings.concat(TemporalConstants.M, monthCode);
   }

   public static JSDynamicObject toTemporalTimeZone(JSContext ctx, Object temporalTimeZoneLikeParam) {
      Object temporalTimeZoneLike = temporalTimeZoneLikeParam;
      if (JSRuntime.isObject(temporalTimeZoneLikeParam)) {
         JSDynamicObject tzObj = toJSDynamicObject(temporalTimeZoneLikeParam, null);
         if (isTemporalZonedDateTime(tzObj)) {
            return ((JSTemporalZonedDateTimeObject)tzObj).getTimeZone();
         }

         if (!JSObject.hasProperty(tzObj, TemporalConstants.TIME_ZONE)) {
            return tzObj;
         }

         temporalTimeZoneLike = JSObject.get(tzObj, TemporalConstants.TIME_ZONE);
         if (JSRuntime.isObject(temporalTimeZoneLike)) {
            tzObj = toJSDynamicObject(temporalTimeZoneLike, null);
            if (!JSObject.hasProperty(tzObj, TemporalConstants.TIME_ZONE)) {
               return tzObj;
            }
         }
      }

      TruffleString identifier = JSRuntime.toString(temporalTimeZoneLike);
      JSTemporalTimeZoneRecord parseResult = parseTemporalTimeZoneString(identifier);
      if (parseResult.getName() != null) {
         boolean canParse = canParseAsTimeZoneNumericUTCOffset(parseResult.getName());
         if (canParse) {
            if (parseResult.getOffsetString() != null
               && parseTimeZoneOffsetString(parseResult.getOffsetString()) != parseTimeZoneOffsetString(parseResult.getName())) {
               throw TemporalErrors.createRangeErrorInvalidTimeZoneString();
            }
         } else if (!isValidTimeZoneName(parseResult.getName())) {
            throw TemporalErrors.createRangeErrorInvalidTimeZoneString();
         }

         return createTemporalTimeZone(ctx, canonicalizeTimeZoneName(parseResult.getName()));
      } else {
         return parseResult.isZ() ? createTemporalTimeZone(ctx, TemporalConstants.UTC) : createTemporalTimeZone(ctx, parseResult.getOffsetString());
      }
   }

   public static JSDynamicObject createTemporalTimeZone(JSContext ctx, TruffleString identifier) {
      TruffleString newIdentifier = identifier;

      BigInt offsetNs;
      try {
         long result = parseTimeZoneOffsetString(identifier);
         newIdentifier = formatTimeZoneOffsetString(result);
         offsetNs = BigInt.valueOf(result);
      } catch (Exception var6) {
         assert canonicalizeTimeZoneName(identifier).equals(identifier);

         offsetNs = null;
      }

      return JSTemporalTimeZone.create(ctx, offsetNs, newIdentifier);
   }

   public static TruffleString canonicalizeTimeZoneName(TruffleString timeZone) {
      assert isValidTimeZoneName(timeZone);

      return Strings.fromJavaString(JSDateTimeFormat.canonicalizeTimeZoneName(timeZone));
   }

   public static boolean isValidTimeZoneName(TruffleString timeZone) {
      return JSDateTimeFormat.canonicalizeTimeZoneName(timeZone) != null;
   }

   @CompilerDirectives.TruffleBoundary
   public static double getDouble(JSDynamicObject ob, TruffleString key, double defaultValue) {
      Object value = JSObject.get(ob, key);
      if (value == Undefined.instance) {
         return defaultValue;
      } else {
         Number n = (Number)value;
         return n.longValue();
      }
   }

   public static boolean isoDateTimeWithinLimits(
      int year, int month, int day, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond
   ) {
      return -270000 <= year && year <= 270000
         ? true
         : isoDateTimeWithinLimitsIntl(year, month, day, hour, minute, second, millisecond, microsecond, nanosecond);
   }

   @CompilerDirectives.TruffleBoundary
   private static boolean isoDateTimeWithinLimitsIntl(
      int year, int month, int day, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond
   ) {
      BigInteger ns = getEpochFromISOParts(year, month, day, hour, minute, second, millisecond, microsecond, nanosecond);
      return ns.compareTo(isoTimeLowerBound) > 0 && ns.compareTo(isoTimeUpperBound) < 0;
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInteger getEpochFromISOParts(
      int year, int month, int day, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond
   ) {
      assert isValidISODate(year, month, day);

      assert isValidTime(hour, minute, second, millisecond, microsecond, nanosecond);

      double date = JSDate.makeDay(year, month - 1, day);
      double time = JSDate.makeTime(hour, minute, second, millisecond);
      double ms = JSDate.makeDate(date, time);
      if (Double.isNaN(ms)) {
         throw TemporalErrors.createRangeErrorDateOutsideRange();
      } else {
         assert isFinite(ms);

         BigInteger bi = BigInteger.valueOf((long)ms).multiply(BI_10_POW_6);
         BigInteger bims = BigInteger.valueOf(microsecond).multiply(BI_1000);
         return bi.add(bims).add(BigInteger.valueOf(nanosecond));
      }
   }

   private static boolean isFinite(double d) {
      return !Double.isNaN(d) && !Double.isInfinite(d);
   }

   public static TemporalUtil.Overflow toTemporalOverflow(JSDynamicObject options, TemporalGetOptionNode getOptionNode) {
      if (options == Undefined.instance) {
         return TemporalUtil.Overflow.CONSTRAIN;
      } else {
         TruffleString result = (TruffleString)getOptionNode.execute(
            options, TemporalConstants.OVERFLOW, TemporalUtil.OptionType.STRING, listConstrainReject, TemporalConstants.CONSTRAIN
         );
         return toOverflow(result);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static TemporalUtil.Overflow toOverflow(TruffleString result) {
      if (TemporalConstants.CONSTRAIN.equals(result)) {
         return TemporalUtil.Overflow.CONSTRAIN;
      } else if (TemporalConstants.REJECT.equals(result)) {
         return TemporalUtil.Overflow.REJECT;
      } else {
         CompilerDirectives.transferToInterpreter();
         throw Errors.shouldNotReachHere("unknown overflow type: " + result);
      }
   }

   public static JSTemporalDateTimeRecord interpretTemporalDateTimeFields(
      JSDynamicObject calendar,
      JSDynamicObject fields,
      JSDynamicObject options,
      TemporalGetOptionNode getOptionNode,
      TemporalCalendarDateFromFieldsNode dateFromFieldsNode
   ) {
      JSTemporalDateTimeRecord timeResult = toTemporalTimeRecord(fields);
      JSTemporalPlainDateObject date = dateFromFieldsNode.execute(calendar, fields, options);
      TemporalUtil.Overflow overflow = toTemporalOverflow(options, getOptionNode);
      JSTemporalDurationRecord timeResult2 = regulateTime(
         timeResult.getHour(),
         timeResult.getMinute(),
         timeResult.getSecond(),
         timeResult.getMillisecond(),
         timeResult.getMicrosecond(),
         timeResult.getNanosecond(),
         overflow
      );
      return JSTemporalDateTimeRecord.create(
         date.getYear(),
         date.getMonth(),
         date.getDay(),
         dtoi(timeResult2.getHours()),
         dtoi(timeResult2.getMinutes()),
         dtoi(timeResult2.getSeconds()),
         dtoi(timeResult2.getMilliseconds()),
         dtoi(timeResult2.getMicroseconds()),
         dtoi(timeResult2.getNanoseconds())
      );
   }

   public static JSTemporalDurationRecord regulateTime(
      double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds, TemporalUtil.Overflow overflow
   ) {
      assert overflow == TemporalUtil.Overflow.CONSTRAIN || overflow == TemporalUtil.Overflow.REJECT;

      if (overflow == TemporalUtil.Overflow.CONSTRAIN) {
         return constrainTime(dtoi(hours), dtoi(minutes), dtoi(seconds), dtoi(milliseconds), dtoi(microseconds), dtoi(nanoseconds));
      } else if (!isValidTime(dtoi(hours), dtoi(minutes), dtoi(seconds), dtoi(milliseconds), dtoi(microseconds), dtoi(nanoseconds))) {
         throw Errors.createRangeError("Given time outside the range.");
      } else {
         return JSTemporalDurationRecord.create(0.0, 0.0, 0.0, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
      }
   }

   public static JSTemporalDurationRecord regulateTime(
      int hours, int minutes, int seconds, int milliseconds, int microseconds, int nanoseconds, TemporalUtil.Overflow overflow
   ) {
      assert overflow == TemporalUtil.Overflow.CONSTRAIN || overflow == TemporalUtil.Overflow.REJECT;

      if (overflow == TemporalUtil.Overflow.CONSTRAIN) {
         return constrainTime(hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
      } else if (!isValidTime(hours, minutes, seconds, milliseconds, microseconds, nanoseconds)) {
         throw Errors.createRangeError("Given time outside the range.");
      } else {
         return JSTemporalDurationRecord.create(0.0, 0.0, 0.0, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
      }
   }

   public static JSTemporalDurationRecord constrainTime(int hours, int minutes, int seconds, int milliseconds, int microseconds, int nanoseconds) {
      return JSTemporalDurationRecord.create(
         0.0,
         0.0,
         0.0,
         constrainToRange(hours, 0, 23),
         constrainToRange(minutes, 0, 59),
         constrainToRange(seconds, 0, 59),
         constrainToRange(milliseconds, 0, 999),
         constrainToRange(microseconds, 0, 999),
         constrainToRange(nanoseconds, 0, 999)
      );
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
            iVal = JSRuntime.intValue(toIntegerThrowOnInfinity(val));
         }

         if (TemporalConstants.HOUR.equals(property)) {
            hour = iVal;
         } else if (TemporalConstants.MINUTE.equals(property)) {
            minute = iVal;
         } else if (TemporalConstants.SECOND.equals(property)) {
            second = iVal;
         } else if (TemporalConstants.MILLISECOND.equals(property)) {
            millisecond = iVal;
         } else if (TemporalConstants.MICROSECOND.equals(property)) {
            microsecond = iVal;
         } else if (TemporalConstants.NANOSECOND.equals(property)) {
            nanosecond = iVal;
         }
      }

      if (!any) {
         throw Errors.createTypeError("at least one time-like field expected");
      } else {
         return JSTemporalDateTimeRecord.create(0, 0, 0, hour, minute, second, millisecond, microsecond, nanosecond);
      }
   }

   public static Number toIntegerThrowOnInfinity(Object value) {
      Number integer = toIntegerOrInfinity(value);
      if (Double.isInfinite(JSRuntime.doubleValue(integer))) {
         throw Errors.createRangeError("value outside bounds");
      } else {
         return integer;
      }
   }

   public static double toIntegerWithoutRounding(Object argument) {
      Number number = JSRuntime.toNumber(argument);
      double dNumber = JSRuntime.doubleValue(number);
      if (Double.isNaN(dNumber) || dNumber == 0.0) {
         return 0.0;
      } else if (!JSRuntime.isIntegralNumber(dNumber)) {
         throw Errors.createRangeError("value expected to be integer");
      } else {
         return dNumber;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static Number toIntegerOrInfinity(Object value) {
      Number number = JSRuntime.toNumber(value);
      double d = number.doubleValue();
      if (d == 0.0 || Double.isNaN(d)) {
         return 0L;
      } else {
         return (Number)(Double.isInfinite(d) ? d : number);
      }
   }

   public static JSDynamicObject calendarDateAdd(JSDynamicObject calendar, JSDynamicObject datePart, JSDynamicObject dateDuration, JSDynamicObject options) {
      return calendarDateAdd(calendar, datePart, dateDuration, options, Undefined.instance);
   }

   public static JSTemporalPlainDateObject calendarDateAdd(
      JSDynamicObject calendar, JSDynamicObject date, JSDynamicObject duration, JSDynamicObject options, Object dateAdd
   ) {
      Object dateAddPrepared = dateAdd;
      if (dateAdd == Undefined.instance) {
         dateAddPrepared = JSObject.getMethod(calendar, TemporalConstants.DATE_ADD);
      }

      Object addedDate = JSRuntime.call(dateAddPrepared, calendar, new Object[]{date, duration, options});
      return requireTemporalDate(addedDate);
   }

   public static JSTemporalDurationObject calendarDateUntil(JSDynamicObject calendar, JSDynamicObject one, JSDynamicObject two, JSDynamicObject options) {
      return calendarDateUntil(calendar, one, two, options, Undefined.instance);
   }

   public static JSTemporalDurationObject calendarDateUntil(
      JSDynamicObject calendar, JSDynamicObject one, JSDynamicObject two, JSDynamicObject options, Object dateUntil
   ) {
      Object dateUntilPrepared = dateUntil;
      if (dateUntil == Undefined.instance) {
         dateUntilPrepared = JSObject.getMethod(calendar, TemporalConstants.DATE_UNTIL);
      }

      Object date = JSRuntime.call(dateUntilPrepared, calendar, new Object[]{one, two, options});
      return requireTemporalDuration(date);
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInteger roundTemporalInstant(BigInt ns, double increment, TemporalUtil.Unit unit, TemporalUtil.RoundingMode roundingMode) {
      return roundTemporalInstant(new BigDecimal(ns.bigIntegerValue()), increment, unit, roundingMode);
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInteger roundTemporalInstant(BigDecimal ns, double increment, TemporalUtil.Unit unit, TemporalUtil.RoundingMode roundingMode) {
      BigDecimal incrementNs = BigDecimal.valueOf(increment);
      if (TemporalUtil.Unit.HOUR == unit) {
         incrementNs = incrementNs.multiply(BigDecimal.valueOf(3600000000000L));
      } else if (TemporalUtil.Unit.MINUTE == unit) {
         incrementNs = incrementNs.multiply(BigDecimal.valueOf(60000000000L));
      } else if (TemporalUtil.Unit.SECOND == unit) {
         incrementNs = incrementNs.multiply(BigDecimal.valueOf(1000000000L));
      } else if (TemporalUtil.Unit.MILLISECOND == unit) {
         incrementNs = incrementNs.multiply(BigDecimal.valueOf(1000000L));
      } else if (TemporalUtil.Unit.MICROSECOND == unit) {
         incrementNs = incrementNs.multiply(BigDecimal.valueOf(1000L));
      } else {
         assert TemporalUtil.Unit.NANOSECOND == unit;
      }

      return roundNumberToIncrement(ns, incrementNs, roundingMode);
   }

   public static boolean validateISODate(int year, int month, int day) {
      if (month >= 1 && month <= 12) {
         long daysInMonth = isoDaysInMonth(year, month);
         return 1 <= day && day <= daysInMonth;
      } else {
         return false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalDateTimeRecord regulateISODate(int yearParam, int monthParam, int dayParam, TemporalUtil.Overflow overflow) {
      assert overflow == TemporalUtil.Overflow.CONSTRAIN || overflow == TemporalUtil.Overflow.REJECT;

      int month = monthParam;
      int day = dayParam;
      if (overflow == TemporalUtil.Overflow.REJECT) {
         if (!isValidISODate(yearParam, monthParam, dayParam)) {
            throw TemporalErrors.createRangeErrorDateOutsideRange();
         }
      } else {
         assert overflow == TemporalUtil.Overflow.CONSTRAIN;

         month = constrainToRange(monthParam, 1, 12);
         day = constrainToRange(dayParam, 1, isoDaysInMonth(yearParam, month));
      }

      return JSTemporalDateTimeRecord.create(yearParam, month, day, 0, 0, 0, 0, 0, 0);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalDateTimeRecord balanceISODate(int yearParam, int monthParam, int dayParam) {
      double epochDays = JSDate.makeDay(yearParam, monthParam - 1, dayParam);

      assert Double.isFinite(epochDays);

      double ms = JSDate.makeDate(epochDays, 0.0);
      return JSTemporalPlainDate.toRecord(JSDate.yearFromTime((long)ms), JSDate.monthFromTime(ms) + 1, JSDate.dateFromTime(ms));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalDateTimeRecord addISODate(int year, int month, int day, int years, int months, int weeks, int daysP, TemporalUtil.Overflow overflow) {
      assert overflow == TemporalUtil.Overflow.CONSTRAIN || overflow == TemporalUtil.Overflow.REJECT;

      JSTemporalDateTimeRecord intermediate = balanceISOYearMonth(add(year, years, overflow), add(month, months, overflow));
      intermediate = regulateISODate(intermediate.getYear(), intermediate.getMonth(), day, overflow);
      int days = daysP + 7 * weeks;
      int d = add(intermediate.getDay(), days, overflow);
      intermediate = balanceISODate(intermediate.getYear(), intermediate.getMonth(), d);
      return regulateISODate(intermediate.getYear(), intermediate.getMonth(), intermediate.getDay(), overflow);
   }

   public static int compareISODate(int y1, int m1, int d1, int y2, int m2, int d2) {
      if (y1 > y2) {
         return 1;
      } else if (y1 < y2) {
         return -1;
      } else if (m1 > m2) {
         return 1;
      } else if (m1 < m2) {
         return -1;
      } else if (d1 > d2) {
         return 1;
      } else {
         return d1 < d2 ? -1 : 0;
      }
   }

   public static JSTemporalPlainDateObject requireTemporalDate(Object obj, BranchProfile errorBranch) {
      if (!(obj instanceof JSTemporalPlainDateObject)) {
         errorBranch.enter();
         throw TemporalErrors.createTypeErrorTemporalDateExpected();
      } else {
         return (JSTemporalPlainDateObject)obj;
      }
   }

   public static JSTemporalPlainDateObject requireTemporalDate(Object obj) {
      if (!(obj instanceof JSTemporalPlainDateObject)) {
         throw TemporalErrors.createTypeErrorTemporalDateExpected();
      } else {
         return (JSTemporalPlainDateObject)obj;
      }
   }

   public static JSTemporalDurationObject requireTemporalDuration(Object obj) {
      if (!(obj instanceof JSTemporalDurationObject)) {
         throw TemporalErrors.createTypeErrorTemporalDurationExpected();
      } else {
         return (JSTemporalDurationObject)obj;
      }
   }

   public static boolean isTemporalZonedDateTime(Object obj) {
      return JSTemporalZonedDateTime.isJSTemporalZonedDateTime(obj);
   }

   public static TemporalUtil.ShowCalendar toShowCalendarOption(JSDynamicObject options, TemporalGetOptionNode getOptionNode, TruffleString.EqualNode equalNode) {
      return toShowCalendar(
         (TruffleString)getOptionNode.execute(options, CALENDAR_NAME, TemporalUtil.OptionType.STRING, listAutoAlwaysNever, TemporalConstants.AUTO), equalNode
      );
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString padISOYear(int year) {
      if (0 <= year && year <= 9999) {
         return Strings.format("%1$04d", year);
      } else {
         TruffleString sign = year > 0 ? Strings.SYMBOL_PLUS : Strings.SYMBOL_MINUS;
         long y = Math.abs(year);
         return Strings.concat(sign, Strings.format("%1$06d", y));
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString formatCalendarAnnotation(TruffleString id, TemporalUtil.ShowCalendar showCalendar) {
      if (TemporalUtil.ShowCalendar.NEVER == showCalendar) {
         return Strings.EMPTY_STRING;
      } else {
         return TemporalUtil.ShowCalendar.AUTO == showCalendar && TemporalConstants.ISO8601.equals(id)
            ? Strings.EMPTY_STRING
            : Strings.concatAll(BRACKET_U_CA_EQUALS, id, Strings.BRACKET_CLOSE);
      }
   }

   public static TemporalUtil.RoundingMode negateTemporalRoundingMode(TemporalUtil.RoundingMode roundingMode) {
      if (TemporalUtil.RoundingMode.CEIL == roundingMode) {
         return TemporalUtil.RoundingMode.FLOOR;
      } else {
         return TemporalUtil.RoundingMode.FLOOR == roundingMode ? TemporalUtil.RoundingMode.CEIL : roundingMode;
      }
   }

   public static boolean calendarEquals(JSDynamicObject one, JSDynamicObject two, JSToStringNode toStringNode) {
      return one == two ? true : Boundaries.equals(toStringNode.executeString(one), toStringNode.executeString(two));
   }

   public static void rejectTemporalCalendarType(JSDynamicObject obj, BranchProfile errorBranch) {
      if (obj instanceof JSTemporalPlainDateObject
         || obj instanceof JSTemporalPlainDateTimeObject
         || obj instanceof JSTemporalPlainMonthDayObject
         || obj instanceof JSTemporalPlainTimeObject
         || obj instanceof JSTemporalPlainYearMonthObject
         || isTemporalZonedDateTime(obj)) {
         errorBranch.enter();
         throw Errors.createTypeError("rejecting calendar types");
      }
   }

   public static double remainder(double x, double y) {
      return x % y;
   }

   public static double getPropertyFromRecord(JSTemporalDurationRecord d, TemporalUtil.UnitPlural unit) {
      switch (unit) {
         case YEARS:
            return d.getYears();
         case MONTHS:
            return d.getMonths();
         case WEEKS:
            return d.getWeeks();
         case DAYS:
            return d.getDays();
         case HOURS:
            return d.getHours();
         case MINUTES:
            return d.getMinutes();
         case SECONDS:
            return d.getSeconds();
         case MILLISECONDS:
            return d.getMilliseconds();
         case MICROSECONDS:
            return d.getMicroseconds();
         case NANOSECONDS:
            return d.getNanoseconds();
         default:
            CompilerDirectives.transferToInterpreter();
            throw Errors.createTypeError("unknown property");
      }
   }

   public static JSDynamicObject calendarMergeFields(
      JSContext ctx,
      EnumerableOwnPropertyNamesNode namesNode,
      BranchProfile errorBranch,
      JSDynamicObject calendar,
      JSDynamicObject fields,
      JSDynamicObject additionalFields
   ) {
      Object mergeFields = JSObject.getMethod(calendar, TemporalConstants.MERGE_FIELDS);
      if (mergeFields == Undefined.instance) {
         return defaultMergeFields(ctx, namesNode, fields, additionalFields);
      } else {
         Object result = JSRuntime.call(mergeFields, calendar, new Object[]{fields, additionalFields});
         if (!JSRuntime.isObject(result)) {
            throw TemporalErrors.createTypeErrorObjectExpected();
         } else {
            return toJSDynamicObject(result, errorBranch);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject defaultMergeFields(
      JSContext ctx, EnumerableOwnPropertyNamesNode namesNode, JSDynamicObject fields, JSDynamicObject additionalFields
   ) {
      JSRealm realm = JSRealm.get(null);
      JSDynamicObject merged = JSOrdinary.create(ctx, realm);

      for (Object nextKey : namesNode.execute(fields)) {
         if (!TemporalConstants.MONTH.equals(nextKey) && !TemporalConstants.MONTH_CODE.equals(nextKey)) {
            Object propValue = JSObject.get(fields, nextKey);
            if (propValue != Undefined.instance) {
               createDataPropertyOrThrow(ctx, merged, JSRuntime.toString(nextKey), propValue);
            }
         }
      }

      boolean hasMonthOrMonthCode = false;

      for (Object nextKeyx : namesNode.execute(additionalFields)) {
         Object propValue = JSObject.get(additionalFields, nextKeyx);
         if (propValue != Undefined.instance) {
            createDataPropertyOrThrow(ctx, merged, JSRuntime.toString(nextKeyx), propValue);
            if (TemporalConstants.MONTH.equals(nextKeyx) || TemporalConstants.MONTH_CODE.equals(nextKeyx)) {
               hasMonthOrMonthCode = true;
            }
         }
      }

      if (!hasMonthOrMonthCode) {
         Object month = JSObject.get(fields, TemporalConstants.MONTH);
         if (month != Undefined.instance) {
            createDataPropertyOrThrow(ctx, merged, TemporalConstants.MONTH, month);
         }

         Object monthCode = JSObject.get(fields, TemporalConstants.MONTH_CODE);
         if (monthCode != Undefined.instance) {
            createDataPropertyOrThrow(ctx, merged, TemporalConstants.MONTH_CODE, monthCode);
         }
      }

      return merged;
   }

   public static void createDataPropertyOrThrow(JSContext ctx, JSDynamicObject obj, TruffleString key, Object value) {
      JSObjectUtil.defineDataProperty(ctx, obj, key, value, JSAttributes.configurableEnumerableWritable());
   }

   @CompilerDirectives.TruffleBoundary
   public static List<TruffleString> listJoinRemoveDuplicates(List<TruffleString> first, List<TruffleString> second) {
      List<TruffleString> newList = new ArrayList<>(first.size() + second.size());
      newList.addAll(first);

      for (TruffleString elem : second) {
         if (!first.contains(elem)) {
            newList.add(elem);
         }
      }

      return newList;
   }

   public static TemporalUtil.Unit largerOfTwoTemporalUnits(TemporalUtil.Unit a, TemporalUtil.Unit b) {
      if (TemporalUtil.Unit.YEAR == a || TemporalUtil.Unit.YEAR == b) {
         return TemporalUtil.Unit.YEAR;
      } else if (TemporalUtil.Unit.MONTH == a || TemporalUtil.Unit.MONTH == b) {
         return TemporalUtil.Unit.MONTH;
      } else if (TemporalUtil.Unit.WEEK == a || TemporalUtil.Unit.WEEK == b) {
         return TemporalUtil.Unit.WEEK;
      } else if (TemporalUtil.Unit.DAY == a || TemporalUtil.Unit.DAY == b) {
         return TemporalUtil.Unit.DAY;
      } else if (TemporalUtil.Unit.HOUR == a || TemporalUtil.Unit.HOUR == b) {
         return TemporalUtil.Unit.HOUR;
      } else if (TemporalUtil.Unit.MINUTE == a || TemporalUtil.Unit.MINUTE == b) {
         return TemporalUtil.Unit.MINUTE;
      } else if (TemporalUtil.Unit.SECOND == a || TemporalUtil.Unit.SECOND == b) {
         return TemporalUtil.Unit.SECOND;
      } else if (TemporalUtil.Unit.MILLISECOND == a || TemporalUtil.Unit.MILLISECOND == b) {
         return TemporalUtil.Unit.MILLISECOND;
      } else {
         return TemporalUtil.Unit.MICROSECOND != a && TemporalUtil.Unit.MICROSECOND != b ? TemporalUtil.Unit.NANOSECOND : TemporalUtil.Unit.MICROSECOND;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalDurationRecord differenceISODateTime(
      JSContext ctx,
      EnumerableOwnPropertyNamesNode namesNode,
      int y1,
      int mon1,
      int d1,
      int h1,
      int min1,
      int s1,
      int ms1,
      int mus1,
      int ns1,
      int y2,
      int mon2,
      int d2,
      int h2,
      int min2,
      int s2,
      int ms2,
      int mus2,
      int ns2,
      JSDynamicObject calendar,
      TemporalUtil.Unit largestUnit,
      JSDynamicObject options
   ) {
      assert options != null;

      JSTemporalDurationRecord timeDifference = differenceTime(h1, min1, s1, ms1, mus1, ns1, h2, min2, s2, ms2, mus2, ns2);
      int timeSign = durationSign(
         0.0,
         0.0,
         0.0,
         timeDifference.getDays(),
         timeDifference.getHours(),
         timeDifference.getMinutes(),
         timeDifference.getSeconds(),
         timeDifference.getMilliseconds(),
         timeDifference.getMicroseconds(),
         timeDifference.getNanoseconds()
      );
      int dateSign = compareISODate(y2, mon2, d2, y1, mon1, d1);
      JSTemporalDateTimeRecord balanceResult = balanceISODate(dtoi(y1), dtoi(mon1), dtoi(d1) + dtoi(timeDifference.getDays()));
      if (timeSign == -dateSign) {
         balanceResult = balanceISODate(balanceResult.getYear(), balanceResult.getMonth(), balanceResult.getDay() - timeSign);
         timeDifference = balanceDuration(
            ctx,
            namesNode,
            -timeSign,
            timeDifference.getHours(),
            timeDifference.getMinutes(),
            timeDifference.getSeconds(),
            timeDifference.getMilliseconds(),
            timeDifference.getMicroseconds(),
            timeDifference.getNanoseconds(),
            largestUnit
         );
      }

      JSDynamicObject date1 = JSTemporalPlainDate.create(ctx, balanceResult.getYear(), balanceResult.getMonth(), balanceResult.getDay(), calendar);
      JSDynamicObject date2 = JSTemporalPlainDate.create(ctx, y2, mon2, d2, calendar);
      TemporalUtil.Unit dateLargestUnit = largerOfTwoTemporalUnits(TemporalUtil.Unit.DAY, largestUnit);
      JSDynamicObject untilOptions = mergeLargestUnitOption(ctx, namesNode, options, dateLargestUnit);
      JSTemporalDurationObject dateDifference = calendarDateUntil(calendar, date1, date2, untilOptions, Undefined.instance);
      JSTemporalDurationRecord result = balanceDuration(
         ctx,
         namesNode,
         dateDifference.getDays(),
         timeDifference.getHours(),
         timeDifference.getMinutes(),
         timeDifference.getSeconds(),
         timeDifference.getMilliseconds(),
         timeDifference.getMicroseconds(),
         timeDifference.getNanoseconds(),
         largestUnit
      );
      return JSTemporalDurationRecord.createWeeks(
         dateDifference.getYears(),
         dateDifference.getMonths(),
         dateDifference.getWeeks(),
         result.getDays(),
         result.getHours(),
         result.getMinutes(),
         result.getSeconds(),
         result.getMilliseconds(),
         result.getMicroseconds(),
         result.getNanoseconds()
      );
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject mergeLargestUnitOption(
      JSContext ctx, EnumerableOwnPropertyNamesNode namesNode, JSDynamicObject options, TemporalUtil.Unit largestUnit
   ) {
      JSDynamicObject merged = JSOrdinary.createWithNullPrototype(ctx);

      for (Object nextKey : namesNode.execute(options)) {
         if (nextKey instanceof TruffleString) {
            TruffleString key = (TruffleString)nextKey;
            Object propValue = JSObject.get(options, key);
            createDataPropertyOrThrow(ctx, merged, key, propValue);
         }
      }

      createDataPropertyOrThrow(ctx, merged, TemporalConstants.LARGEST_UNIT, largestUnit.toTruffleString());
      return merged;
   }

   public static int durationSign(
      double years,
      double months,
      double weeks,
      double days,
      double hours,
      double minutes,
      double seconds,
      double milliseconds,
      double microseconds,
      double nanoseconds
   ) {
      if (years < 0.0) {
         return -1;
      } else if (years > 0.0) {
         return 1;
      } else if (months < 0.0) {
         return -1;
      } else if (months > 0.0) {
         return 1;
      } else if (weeks < 0.0) {
         return -1;
      } else if (weeks > 0.0) {
         return 1;
      } else if (days < 0.0) {
         return -1;
      } else if (days > 0.0) {
         return 1;
      } else if (hours < 0.0) {
         return -1;
      } else if (hours > 0.0) {
         return 1;
      } else if (minutes < 0.0) {
         return -1;
      } else if (minutes > 0.0) {
         return 1;
      } else if (seconds < 0.0) {
         return -1;
      } else if (seconds > 0.0) {
         return 1;
      } else if (milliseconds < 0.0) {
         return -1;
      } else if (milliseconds > 0.0) {
         return 1;
      } else if (microseconds < 0.0) {
         return -1;
      } else if (microseconds > 0.0) {
         return 1;
      } else if (nanoseconds < 0.0) {
         return -1;
      } else {
         return nanoseconds > 0.0 ? 1 : 0;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static void rejectDurationSign(
      double years,
      double months,
      double weeks,
      double days,
      double hours,
      double minutes,
      double seconds,
      double milliseconds,
      double microseconds,
      double nanoseconds
   ) {
      long sign = durationSign(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
      if (years < 0.0 && sign > 0L) {
         throw Errors.createRangeError("Years is negative but it should be positive.");
      } else if (years > 0.0 && sign < 0L) {
         throw Errors.createRangeError("Years is positive but it should be negative.");
      } else if (months < 0.0 && sign > 0L) {
         throw Errors.createRangeError("Months is negative but it should be positive.");
      } else if (months > 0.0 && sign < 0L) {
         throw Errors.createRangeError("Months is positive but it should be negative.");
      } else if (weeks < 0.0 && sign > 0L) {
         throw Errors.createRangeError("Weeks is negative but it should be positive.");
      } else if (weeks > 0.0 && sign < 0L) {
         throw Errors.createRangeError("Weeks is positive but it should be negative.");
      } else if (days < 0.0 && sign > 0L) {
         throw Errors.createRangeError("Days is negative but it should be positive.");
      } else if (days > 0.0 && sign < 0L) {
         throw Errors.createRangeError("Days is positive but it should be negative.");
      } else if (hours < 0.0 && sign > 0L) {
         throw Errors.createRangeError("Hours is negative but it should be positive.");
      } else if (hours > 0.0 && sign < 0L) {
         throw Errors.createRangeError("Hours is positive but it should be negative.");
      } else if (minutes < 0.0 && sign > 0L) {
         throw Errors.createRangeError("Minutes is negative but it should be positive.");
      } else if (minutes > 0.0 && sign < 0L) {
         throw Errors.createRangeError("Minutes is positive but it should be negative.");
      } else if (seconds < 0.0 && sign > 0L) {
         throw Errors.createRangeError("Seconds is negative but it should be positive.");
      } else if (seconds > 0.0 && sign < 0L) {
         throw Errors.createRangeError("Seconds is positive but it should be negative.");
      } else if (milliseconds < 0.0 && sign > 0L) {
         throw Errors.createRangeError("Milliseconds is negative but it should be positive.");
      } else if (milliseconds > 0.0 && sign < 0L) {
         throw Errors.createRangeError("Milliseconds is positive but it should be negative.");
      } else if (microseconds < 0.0 && sign > 0L) {
         throw Errors.createRangeError("Microseconds is negative but it should be positive.");
      } else if (microseconds > 0.0 && sign < 0L) {
         throw Errors.createRangeError("Microseconds is positive but it should be negative.");
      } else if (nanoseconds < 0.0 && sign > 0L) {
         throw Errors.createRangeError("Nanoseconds is negative but it should be positive.");
      } else if (nanoseconds > 0.0 && sign < 0L) {
         throw Errors.createRangeError("Nanoseconds is positive but it should be negative.");
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalDurationRecord balanceDuration(
      JSContext ctx,
      EnumerableOwnPropertyNamesNode namesNode,
      double days,
      double hours,
      double minutes,
      double seconds,
      double milliseconds,
      double microseconds,
      double nanoseconds,
      TemporalUtil.Unit largestUnit
   ) {
      return balanceDuration(
         ctx, namesNode, days, hours, minutes, seconds, milliseconds, microseconds, BigInteger.valueOf(dtol(nanoseconds)), largestUnit, Undefined.instance
      );
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalDurationRecord balanceDuration(
      JSContext ctx,
      EnumerableOwnPropertyNamesNode namesNode,
      double days,
      double hours,
      double minutes,
      double seconds,
      double milliseconds,
      double microseconds,
      BigInteger nanoseconds,
      TemporalUtil.Unit largestUnit,
      JSDynamicObject relativeTo
   ) {
      BigInt nsBi;
      if (isTemporalZonedDateTime(relativeTo)) {
         JSTemporalZonedDateTimeObject zdt = (JSTemporalZonedDateTimeObject)relativeTo;
         BigInt endNs = addZonedDateTime(
            ctx,
            zdt.getNanoseconds(),
            zdt.getTimeZone(),
            zdt.getCalendar(),
            0L,
            0L,
            0L,
            dtol(days, true),
            dtol(hours, true),
            dtol(minutes, true),
            dtol(seconds, true),
            dtol(milliseconds, true),
            dtol(microseconds, true),
            nanoseconds,
            Undefined.instance
         );
         nsBi = endNs.subtract(zdt.getNanoseconds());
      } else {
         nsBi = new BigInt(totalDurationNanoseconds(days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds));
      }

      double d;
      if (largestUnit != TemporalUtil.Unit.YEAR
         && largestUnit != TemporalUtil.Unit.MONTH
         && largestUnit != TemporalUtil.Unit.WEEK
         && largestUnit != TemporalUtil.Unit.DAY) {
         d = 0.0;
      } else {
         JSTemporalNanosecondsDaysRecord result = nanosecondsToDays(ctx, namesNode, nsBi, relativeTo);
         d = bitod(result.getDays());
         nsBi = new BigInt(result.getNanoseconds());
      }

      double h = 0.0;
      double min = 0.0;
      double s = 0.0;
      double ms = 0.0;
      double mus = 0.0;
      BigInteger nsBi2 = nsBi.bigIntegerValue();
      double sign = nsBi2.compareTo(BigInteger.ZERO) < 0 ? -1.0 : 1.0;
      nsBi2 = nsBi2.abs();
      if (largestUnit == TemporalUtil.Unit.YEAR
         || largestUnit == TemporalUtil.Unit.MONTH
         || largestUnit == TemporalUtil.Unit.WEEK
         || largestUnit == TemporalUtil.Unit.DAY
         || largestUnit == TemporalUtil.Unit.HOUR) {
         BigInteger[] res = nsBi2.divideAndRemainder(BI_1000);
         mus = bitod(res[0]);
         nsBi2 = res[1];
         ms = Math.floor(mus / 1000.0);
         mus %= 1000.0;
         s = Math.floor(ms / 1000.0);
         ms %= 1000.0;
         min = Math.floor(s / 60.0);
         s %= 60.0;
         h = Math.floor(min / 60.0);
         min %= 60.0;
      } else if (largestUnit == TemporalUtil.Unit.MINUTE) {
         BigInteger[] res = nsBi2.divideAndRemainder(BI_1000);
         mus = bitod(res[0]);
         nsBi2 = res[1];
         ms = Math.floor(mus / 1000.0);
         mus %= 1000.0;
         s = Math.floor(ms / 1000.0);
         ms %= 1000.0;
         min = Math.floor(s / 60.0);
         s %= 60.0;
      } else if (largestUnit == TemporalUtil.Unit.SECOND) {
         BigInteger[] res = nsBi2.divideAndRemainder(BI_1000);
         mus = bitod(res[0]);
         nsBi2 = res[1];
         ms = Math.floor(mus / 1000.0);
         mus %= 1000.0;
         s = Math.floor(ms / 1000.0);
         ms %= 1000.0;
      } else if (largestUnit == TemporalUtil.Unit.MILLISECOND) {
         BigInteger[] res = nsBi2.divideAndRemainder(BI_1000);
         mus = bitod(res[0]);
         nsBi2 = res[1];
         ms = Math.floor(mus / 1000.0);
         mus %= 1000.0;
      } else if (largestUnit == TemporalUtil.Unit.MICROSECOND) {
         BigInteger[] res = nsBi2.divideAndRemainder(BI_1000);
         mus = bitod(res[0]);
         nsBi2 = res[1];
      } else {
         assert largestUnit == TemporalUtil.Unit.NANOSECOND;
      }

      return JSTemporalDurationRecord.create(
         0.0, 0.0, d, h * sign, min * sign, s * sign, ms * sign, mus * sign, sign < 0.0 ? bitod(nsBi2.negate()) : bitod(nsBi2)
      );
   }

   public static JSDynamicObject toDynamicObject(Object obj) {
      if (obj instanceof JSDynamicObject) {
         return (JSDynamicObject)obj;
      } else {
         throw Errors.createTypeErrorNotAnObject(obj);
      }
   }

   public static JSDynamicObject toJSDynamicObject(Object item, BranchProfile errorBranch) {
      if (item instanceof JSDynamicObject) {
         return (JSDynamicObject)item;
      } else {
         if (errorBranch != null) {
            errorBranch.enter();
         }

         throw Errors.createTypeError("Interop types not supported in Temporal");
      }
   }

   public static boolean doubleIsInteger(double l) {
      return Math.rint(l) == l;
   }

   public static JSTemporalDurationRecord differenceZonedDateTime(
      JSContext ctx,
      EnumerableOwnPropertyNamesNode namesNode,
      BigInt ns1,
      BigInt ns2,
      JSDynamicObject timeZone,
      JSDynamicObject calendar,
      TemporalUtil.Unit largestUnit
   ) {
      return differenceZonedDateTime(ctx, namesNode, ns1, ns2, timeZone, calendar, largestUnit, Undefined.instance);
   }

   public static JSTemporalDurationRecord differenceZonedDateTime(
      JSContext ctx,
      EnumerableOwnPropertyNamesNode namesNode,
      BigInt ns1,
      BigInt ns2,
      JSDynamicObject timeZone,
      JSDynamicObject calendar,
      TemporalUtil.Unit largestUnit,
      JSDynamicObject options
   ) {
      if (ns1.equals(ns2)) {
         return JSTemporalDurationRecord.createWeeks(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      } else {
         JSTemporalInstantObject startInstant = JSTemporalInstant.create(ctx, ns1);
         JSTemporalPlainDateTimeObject startDateTime = builtinTimeZoneGetPlainDateTimeFor(ctx, timeZone, startInstant, calendar);
         JSTemporalInstantObject endInstant = JSTemporalInstant.create(ctx, ns2);
         JSTemporalPlainDateTimeObject endDateTime = builtinTimeZoneGetPlainDateTimeFor(ctx, timeZone, endInstant, calendar);
         JSTemporalDurationRecord dateDifference = differenceISODateTime(
            ctx,
            namesNode,
            startDateTime.getYear(),
            startDateTime.getMonth(),
            startDateTime.getDay(),
            startDateTime.getHour(),
            startDateTime.getMinute(),
            startDateTime.getSecond(),
            startDateTime.getMillisecond(),
            startDateTime.getMicrosecond(),
            startDateTime.getNanosecond(),
            endDateTime.getYear(),
            endDateTime.getMonth(),
            endDateTime.getDay(),
            endDateTime.getHour(),
            endDateTime.getMinute(),
            endDateTime.getSecond(),
            endDateTime.getMillisecond(),
            endDateTime.getMicrosecond(),
            endDateTime.getNanosecond(),
            calendar,
            largestUnit,
            options
         );
         BigInt intermediateNs = addZonedDateTime(
            ctx,
            ns1,
            timeZone,
            calendar,
            dtol(dateDifference.getYears()),
            dtol(dateDifference.getMonths()),
            dtol(dateDifference.getWeeks()),
            0L,
            0L,
            0L,
            0L,
            0L,
            0L,
            0L
         );
         BigInt timeRemainderNs = ns2.subtract(intermediateNs);
         JSDynamicObject intermediate = JSTemporalZonedDateTime.create(ctx, intermediateNs, timeZone, calendar);
         JSTemporalNanosecondsDaysRecord result = nanosecondsToDays(ctx, namesNode, timeRemainderNs, intermediate);
         JSTemporalDurationRecord timeDifference = balanceDuration(
            ctx, namesNode, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, result.getNanoseconds(), TemporalUtil.Unit.HOUR, Undefined.instance
         );
         return JSTemporalDurationRecord.createWeeks(
            dateDifference.getYears(),
            dateDifference.getMonths(),
            dateDifference.getWeeks(),
            bitod(result.getDays()),
            timeDifference.getHours(),
            timeDifference.getMinutes(),
            timeDifference.getSeconds(),
            timeDifference.getMilliseconds(),
            timeDifference.getMicroseconds(),
            timeDifference.getNanoseconds()
         );
      }
   }

   public static boolean isValidDuration(
      double years,
      double months,
      double weeks,
      double days,
      double hours,
      double minutes,
      double seconds,
      double milliseconds,
      double microseconds,
      double nanoseconds
   ) {
      int sign = durationSign(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
      if (years < 0.0 && sign > 0) {
         return false;
      } else if (years > 0.0 && sign < 0) {
         return false;
      } else if (months < 0.0 && sign > 0) {
         return false;
      } else if (months > 0.0 && sign < 0) {
         return false;
      } else if (weeks < 0.0 && sign > 0) {
         return false;
      } else if (weeks > 0.0 && sign < 0) {
         return false;
      } else if (days < 0.0 && sign > 0) {
         return false;
      } else if (days > 0.0 && sign < 0) {
         return false;
      } else if (hours < 0.0 && sign > 0) {
         return false;
      } else if (hours > 0.0 && sign < 0) {
         return false;
      } else if (minutes < 0.0 && sign > 0) {
         return false;
      } else if (minutes > 0.0 && sign < 0) {
         return false;
      } else if (seconds < 0.0 && sign > 0) {
         return false;
      } else if (seconds > 0.0 && sign < 0) {
         return false;
      } else if (milliseconds < 0.0 && sign > 0) {
         return false;
      } else if (milliseconds > 0.0 && sign < 0) {
         return false;
      } else if (microseconds < 0.0 && sign > 0) {
         return false;
      } else if (microseconds > 0.0 && sign < 0) {
         return false;
      } else {
         return nanoseconds < 0.0 && sign > 0 ? false : !(nanoseconds > 0.0) || sign >= 0;
      }
   }

   public static TemporalUtil.Unit defaultTemporalLargestUnit(
      double years, double months, double weeks, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds
   ) {
      if (years != 0.0) {
         return TemporalUtil.Unit.YEAR;
      } else if (months != 0.0) {
         return TemporalUtil.Unit.MONTH;
      } else if (weeks != 0.0) {
         return TemporalUtil.Unit.WEEK;
      } else if (days != 0.0) {
         return TemporalUtil.Unit.DAY;
      } else if (hours != 0.0) {
         return TemporalUtil.Unit.HOUR;
      } else if (minutes != 0.0) {
         return TemporalUtil.Unit.MINUTE;
      } else if (seconds != 0.0) {
         return TemporalUtil.Unit.SECOND;
      } else if (milliseconds != 0.0) {
         return TemporalUtil.Unit.MILLISECOND;
      } else {
         return microseconds != 0.0 ? TemporalUtil.Unit.MICROSECOND : TemporalUtil.Unit.NANOSECOND;
      }
   }

   public static JSDynamicObject toPartialDuration(
      Object temporalDurationLike, JSContext ctx, IsObjectNode isObjectNode, JSToIntegerWithoutRoundingNode toInt, BranchProfile errorBranch
   ) {
      if (!isObjectNode.executeBoolean(temporalDurationLike)) {
         errorBranch.enter();
         throw Errors.createTypeError("Given duration like is not a object.");
      } else {
         JSDynamicObject temporalDurationLikeObj = toJSDynamicObject(temporalDurationLike, errorBranch);
         JSRealm realm = JSRealm.get(null);
         JSDynamicObject result = JSOrdinary.create(ctx, realm);
         boolean any = false;

         for (TemporalUtil.UnitPlural unit : DURATION_PROPERTIES) {
            Object value = JSObject.get(temporalDurationLikeObj, unit.toTruffleString());
            if (value != Undefined.instance) {
               any = true;
               JSObjectUtil.putDataProperty(ctx, result, unit.toTruffleString(), toInt.executeDouble(value));
            }
         }

         if (!any) {
            errorBranch.enter();
            throw Errors.createTypeError("Given duration like object has no duration properties.");
         } else {
            return result;
         }
      }
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
   public static JSTemporalNanosecondsDaysRecord nanosecondsToDays(
      JSContext ctx, EnumerableOwnPropertyNamesNode namesNode, BigInt nanosecondsParam, JSDynamicObject relativeTo
   ) {
      BigInteger nanoseconds = nanosecondsParam.bigIntegerValue();
      long sign = nanoseconds.signum();
      BigInteger signBI = BigInteger.valueOf(sign);
      BigInteger dayLengthNs = BI_8_64_13;
      if (sign == 0L) {
         return JSTemporalNanosecondsDaysRecord.create(BigInteger.ZERO, BigInteger.ZERO, dayLengthNs);
      } else if (!isTemporalZonedDateTime(relativeTo)) {
         BigInteger val = nanoseconds.divide(dayLengthNs);
         BigInteger val2 = nanoseconds.abs().mod(dayLengthNs).multiply(signBI);
         return JSTemporalNanosecondsDaysRecord.create(val, val2, dayLengthNs);
      } else {
         JSTemporalZonedDateTimeObject relativeZDT = (JSTemporalZonedDateTimeObject)relativeTo;
         BigInt startNs = relativeZDT.getNanoseconds();
         JSTemporalInstantObject startInstant = JSTemporalInstant.create(ctx, startNs);
         JSTemporalPlainDateTimeObject startDateTime = builtinTimeZoneGetPlainDateTimeFor(
            ctx, relativeZDT.getTimeZone(), startInstant, relativeZDT.getCalendar()
         );
         BigInt endNs = startNs.add(nanosecondsParam);
         JSTemporalInstantObject endInstant = JSTemporalInstant.create(ctx, endNs);
         JSTemporalPlainDateTimeObject endDateTime = builtinTimeZoneGetPlainDateTimeFor(ctx, relativeZDT.getTimeZone(), endInstant, relativeZDT.getCalendar());
         JSTemporalDurationRecord dateDifference = differenceISODateTime(
            ctx,
            namesNode,
            startDateTime.getYear(),
            startDateTime.getMonth(),
            startDateTime.getDay(),
            startDateTime.getHour(),
            startDateTime.getMinute(),
            startDateTime.getSecond(),
            startDateTime.getMillisecond(),
            startDateTime.getMicrosecond(),
            startDateTime.getNanosecond(),
            endDateTime.getYear(),
            endDateTime.getMonth(),
            endDateTime.getDay(),
            endDateTime.getHour(),
            endDateTime.getMinute(),
            endDateTime.getSecond(),
            endDateTime.getMillisecond(),
            endDateTime.getMicrosecond(),
            endDateTime.getNanosecond(),
            relativeZDT.getCalendar(),
            TemporalUtil.Unit.DAY,
            Undefined.instance
         );
         long days = dtol(dateDifference.getDays());
         BigInt intermediateNs = addZonedDateTime(ctx, startNs, relativeZDT.getTimeZone(), relativeZDT.getCalendar(), 0L, 0L, 0L, days, 0L, 0L, 0L, 0L, 0L, 0L);
         if (sign == 1L) {
            while (days > 0L && intermediateNs.compareTo(endNs) > 0) {
               intermediateNs = addZonedDateTime(ctx, startNs, relativeZDT.getTimeZone(), relativeZDT.getCalendar(), 0L, 0L, 0L, --days, 0L, 0L, 0L, 0L, 0L, 0L);
            }
         }

         nanoseconds = endNs.subtract(intermediateNs).bigIntegerValue();
         boolean done = false;

         while (!done) {
            BigInteger oneDayFartherNs = addZonedDateTime(
                  ctx, intermediateNs, relativeZDT.getTimeZone(), relativeZDT.getCalendar(), 0L, 0L, 0L, sign, 0L, 0L, 0L, 0L, 0L, 0L
               )
               .bigIntegerValue();
            dayLengthNs = oneDayFartherNs.subtract(intermediateNs.bigIntegerValue());
            if (nanoseconds.subtract(dayLengthNs).multiply(signBI).compareTo(BigInteger.ZERO) >= 0) {
               nanoseconds = nanoseconds.subtract(dayLengthNs);
               intermediateNs = new BigInt(oneDayFartherNs);
               days += sign;
            } else {
               done = true;
            }
         }

         return JSTemporalNanosecondsDaysRecord.create(BigInteger.valueOf(days), nanoseconds, dayLengthNs.abs());
      }
   }

   public static JSTemporalDurationRecord adjustRoundedDurationDays(
      JSContext ctx,
      EnumerableOwnPropertyNamesNode namesNode,
      TemporalDurationAddNode durationAddNode,
      double years,
      double months,
      double weeks,
      double days,
      double hours,
      double minutes,
      double seconds,
      double milliseconds,
      double microseconds,
      double nanoseconds,
      double increment,
      TemporalUtil.Unit unit,
      TemporalUtil.RoundingMode roundingMode,
      JSDynamicObject relativeToParam
   ) {
      if (isTemporalZonedDateTime(relativeToParam)
         && unit != TemporalUtil.Unit.YEAR
         && unit != TemporalUtil.Unit.MONTH
         && unit != TemporalUtil.Unit.WEEK
         && unit != TemporalUtil.Unit.DAY
         && (unit != TemporalUtil.Unit.NANOSECOND || increment != 1.0)) {
         JSTemporalZonedDateTimeObject relativeTo = (JSTemporalZonedDateTimeObject)relativeToParam;
         long timeRemainderNs = dtol(totalDurationNanoseconds(0.0, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, 0.0));
         long direction = Long.signum(timeRemainderNs);
         BigInt dayStart = addZonedDateTime(
            ctx,
            relativeTo.getNanoseconds(),
            relativeTo.getTimeZone(),
            relativeTo.getCalendar(),
            dtol(years),
            dtol(months),
            dtol(weeks),
            dtol(days),
            0L,
            0L,
            0L,
            0L,
            0L,
            0L
         );
         BigInt dayEnd = addZonedDateTime(ctx, dayStart, relativeTo.getTimeZone(), relativeTo.getCalendar(), 0L, 0L, 0L, direction, 0L, 0L, 0L, 0L, 0L, 0L);
         long dayLengthNs = bigIntToLong(dayEnd.subtract(dayStart));
         if ((timeRemainderNs - dayLengthNs) * direction < 0L) {
            return JSTemporalDurationRecord.createWeeks(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
         } else {
            BigInteger timeRemainderNsBi = roundTemporalInstant(Boundaries.bigDecimalValueOf(timeRemainderNs - dayLengthNs), increment, unit, roundingMode);
            JSTemporalDurationRecord add = durationAddNode.execute(
               dtol(years),
               dtol(months),
               dtol(weeks),
               dtol(days),
               0.0,
               0.0,
               0.0,
               0.0,
               0.0,
               0.0,
               0.0,
               0.0,
               0.0,
               direction,
               0.0,
               0.0,
               0.0,
               0.0,
               0.0,
               0.0,
               relativeToParam
            );
            JSTemporalDurationRecord atd = balanceDuration(
               ctx, namesNode, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, timeRemainderNsBi, TemporalUtil.Unit.HOUR, Undefined.instance
            );
            return JSTemporalDurationRecord.createWeeks(
               add.getYears(),
               add.getMonths(),
               add.getWeeks(),
               add.getDays(),
               atd.getHours(),
               atd.getMinutes(),
               atd.getSeconds(),
               atd.getMilliseconds(),
               atd.getMicroseconds(),
               atd.getNanoseconds()
            );
         }
      } else {
         return JSTemporalDurationRecord.createWeeks(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
      }
   }

   public static double totalDurationNanoseconds(
      double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds, double offsetShift
   ) {
      double ns = nanoseconds;
      if (days != 0.0) {
         ns = nanoseconds - offsetShift;
      }

      double h = hours + days * 24.0;
      double min = minutes + h * 60.0;
      double s = seconds + min * 60.0;
      double ms = milliseconds + s * 1000.0;
      double mus = microseconds + ms * 1000.0;
      return ns + mus * 1000.0;
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInteger totalDurationNanoseconds(
      double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, BigInteger nanoseconds
   ) {
      double h = hours + days * 24.0;
      double min = minutes + h * 60.0;
      double s = seconds + min * 60.0;
      double ms = milliseconds + s * 1000.0;
      double mus = microseconds + ms * 1000.0;
      return nanoseconds.add(BigDecimal.valueOf(mus).toBigInteger().multiply(BI_1000));
   }

   @CompilerDirectives.TruffleBoundary
   public static double calculateOffsetShift(
      JSContext ctx, JSDynamicObject relativeTo, double y, double mon, double w, double d, double h, double min, double s, double ms, double mus, double ns
   ) {
      if (!isTemporalZonedDateTime(relativeTo)) {
         return 0.0;
      } else {
         JSTemporalZonedDateTimeObject relativeToZDT = (JSTemporalZonedDateTimeObject)relativeTo;
         JSDynamicObject instant = JSTemporalInstant.create(ctx, relativeToZDT.getNanoseconds());
         long offsetBefore = getOffsetNanosecondsFor(relativeToZDT.getTimeZone(), instant);
         BigInt after = addZonedDateTime(
            ctx,
            relativeToZDT.getNanoseconds(),
            relativeToZDT.getTimeZone(),
            relativeToZDT.getCalendar(),
            dtol(y),
            dtol(mon),
            dtol(w),
            dtol(d),
            dtol(h),
            dtol(min),
            dtol(s),
            dtol(ms),
            dtol(mus),
            dtol(ns)
         );
         JSDynamicObject instantAfter = JSTemporalInstant.create(ctx, after);
         long offsetAfter = getOffsetNanosecondsFor(relativeToZDT.getTimeZone(), instantAfter);
         return offsetAfter - offsetBefore;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static long daysUntil(JSDynamicObject earlier, JSDynamicObject later) {
      double epochDays1 = JSDate.makeDay(((TemporalYear)earlier).getYear(), ((TemporalMonth)earlier).getMonth() - 1, ((TemporalDay)earlier).getDay());

      assert Double.isFinite(epochDays1);

      double epochDays2 = JSDate.makeDay(((TemporalYear)later).getYear(), ((TemporalMonth)later).getMonth() - 1, ((TemporalDay)later).getDay());

      assert Double.isFinite(epochDays2);

      return dtol(epochDays2 - epochDays1);
   }

   public static JSTemporalDurationRecord differenceTime(
      int h1, int min1, int s1, int ms1, int mus1, int ns1, int h2, int min2, int s2, int ms2, int mus2, int ns2
   ) {
      int hours = h2 - h1;
      int minutes = min2 - min1;
      int seconds = s2 - s1;
      int milliseconds = ms2 - ms1;
      int microseconds = mus2 - mus1;
      int nanoseconds = ns2 - ns1;
      int sign = durationSign(0.0, 0.0, 0.0, 0.0, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
      JSTemporalDurationRecord bt = balanceTime(hours * sign, minutes * sign, seconds * sign, milliseconds * sign, microseconds * sign, nanoseconds * sign);
      return JSTemporalDurationRecord.create(
         0.0,
         0.0,
         bt.getDays() * sign,
         bt.getHours() * sign,
         bt.getMinutes() * sign,
         bt.getSeconds() * sign,
         bt.getMilliseconds() * sign,
         bt.getMicroseconds() * sign,
         bt.getNanoseconds() * sign
      );
   }

   public static JSTemporalDurationRecord roundTime(
      int hours,
      int minutes,
      int seconds,
      int milliseconds,
      int microseconds,
      int nanoseconds,
      double increment,
      TemporalUtil.Unit unit,
      TemporalUtil.RoundingMode roundingMode,
      Long dayLengthNsParam
   ) {
      double fractionalSecond = nanoseconds / 1.0E9 + microseconds / 1000000.0 + milliseconds / 1000.0 + seconds;
      double quantity;
      if (unit == TemporalUtil.Unit.DAY) {
         long dayLengthNs = dayLengthNsParam == null ? 86300000000000L : dayLengthNsParam;
         quantity = (double)(((((hours * 60 + minutes) * 60 + seconds) * 1000 + milliseconds) * 1000 + microseconds) * 1000 + nanoseconds) / dayLengthNs;
      } else if (unit == TemporalUtil.Unit.HOUR) {
         quantity = (fractionalSecond / 60.0 + minutes) / 60.0 + hours;
      } else if (unit == TemporalUtil.Unit.MINUTE) {
         quantity = fractionalSecond / 60.0 + minutes;
      } else if (unit == TemporalUtil.Unit.SECOND) {
         quantity = fractionalSecond;
      } else if (unit == TemporalUtil.Unit.MILLISECOND) {
         quantity = nanoseconds / 1000000.0 + microseconds / 1000.0 + milliseconds;
      } else if (unit == TemporalUtil.Unit.MICROSECOND) {
         quantity = nanoseconds / 1000.0 + microseconds;
      } else {
         assert unit == TemporalUtil.Unit.NANOSECOND;

         quantity = nanoseconds;
      }

      long result = dtol(roundNumberToIncrement(quantity, increment, roundingMode));
      if (unit == TemporalUtil.Unit.DAY) {
         return JSTemporalDurationRecord.create(0.0, 0.0, result, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      } else if (unit == TemporalUtil.Unit.HOUR) {
         return balanceTime(result, 0L, 0L, 0L, 0L, 0L);
      } else if (unit == TemporalUtil.Unit.MINUTE) {
         return balanceTime(hours, result, 0L, 0L, 0L, 0L);
      } else if (unit == TemporalUtil.Unit.SECOND) {
         return balanceTime(hours, minutes, result, 0L, 0L, 0L);
      } else if (unit == TemporalUtil.Unit.MILLISECOND) {
         return balanceTime(hours, minutes, seconds, result, 0L, 0L);
      } else if (unit == TemporalUtil.Unit.MICROSECOND) {
         return balanceTime(hours, minutes, seconds, milliseconds, result, 0L);
      } else {
         assert unit == TemporalUtil.Unit.NANOSECOND;

         return balanceTime(hours, minutes, seconds, milliseconds, microseconds, result);
      }
   }

   public static JSTemporalDurationRecord balanceTimeDouble(double h, double min, double sec, double mils, double mics, double ns) {
      if (h != Double.POSITIVE_INFINITY
         && h != Double.NEGATIVE_INFINITY
         && min != Double.POSITIVE_INFINITY
         && min != Double.NEGATIVE_INFINITY
         && sec != Double.POSITIVE_INFINITY
         && sec != Double.NEGATIVE_INFINITY
         && mils != Double.POSITIVE_INFINITY
         && mils != Double.NEGATIVE_INFINITY
         && mics != Double.POSITIVE_INFINITY
         && mics != Double.NEGATIVE_INFINITY
         && ns != Double.POSITIVE_INFINITY
         && ns != Double.NEGATIVE_INFINITY) {
         double microseconds = mics + Math.floor(ns / 1000.0);
         double nanoseconds = nonNegativeModulo(ns, 1000.0);
         double milliseconds = mils + Math.floor(microseconds / 1000.0);
         microseconds = nonNegativeModulo(microseconds, 1000.0);
         double seconds = sec + Math.floor(milliseconds / 1000.0);
         milliseconds = nonNegativeModulo(milliseconds, 1000.0);
         double minutes = min + Math.floor(seconds / 60.0);
         seconds = nonNegativeModulo(seconds, 60.0);
         double hours = h + Math.floor(minutes / 60.0);
         minutes = nonNegativeModulo(minutes, 60.0);
         double days = Math.floor(hours / 24.0);
         hours = nonNegativeModulo(hours, 24.0);
         return JSTemporalDurationRecord.create(0.0, 0.0, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
      } else {
         throw Errors.createRangeError("Time is infinite");
      }
   }

   public static JSTemporalDurationRecord balanceTime(long h, long min, long sec, long mils, long mics, long ns) {
      long microseconds = mics + (long)Math.floor(ns / 1000.0);
      long nanoseconds = (long)nonNegativeModulo(ns, 1000.0);
      long milliseconds = mils + (long)Math.floor(microseconds / 1000.0);
      microseconds = (long)nonNegativeModulo(microseconds, 1000.0);
      long seconds = sec + (long)Math.floor(milliseconds / 1000.0);
      milliseconds = (long)nonNegativeModulo(milliseconds, 1000.0);
      long minutes = min + (long)Math.floor(seconds / 60.0);
      seconds = (long)nonNegativeModulo(seconds, 60.0);
      long hours = h + (long)Math.floor(minutes / 60.0);
      minutes = (long)nonNegativeModulo(minutes, 60.0);
      long days = (long)Math.floor(hours / 24.0);
      hours = (long)nonNegativeModulo(hours, 24.0);
      return JSTemporalDurationRecord.create(0.0, 0.0, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
   }

   public static int compareTemporalTime(int h1, int min1, int s1, int ms1, int mus1, int ns1, int h2, int min2, int s2, int ms2, int mus2, int ns2) {
      if (h1 > h2) {
         return 1;
      } else if (h1 < h2) {
         return -1;
      } else if (min1 > min2) {
         return 1;
      } else if (min1 < min2) {
         return -1;
      } else if (s1 > s2) {
         return 1;
      } else if (s1 < s2) {
         return -1;
      } else if (ms1 > ms2) {
         return 1;
      } else if (ms1 < ms2) {
         return -1;
      } else if (mus1 > mus2) {
         return 1;
      } else if (mus1 < mus2) {
         return -1;
      } else if (ns1 > ns2) {
         return 1;
      } else {
         return ns1 < ns2 ? -1 : 0;
      }
   }

   public static JSTemporalDurationRecord addTimeDouble(
      int hour,
      int minute,
      int second,
      int millisecond,
      int microsecond,
      double nanosecond,
      double hours,
      double minutes,
      double seconds,
      double milliseconds,
      double microseconds,
      double nanoseconds
   ) {
      return balanceTimeDouble(
         hour + hours, minute + minutes, second + seconds, millisecond + milliseconds, microsecond + microseconds, nanosecond + nanoseconds
      );
   }

   public static JSTemporalDurationRecord roundISODateTime(
      int year,
      int month,
      int day,
      int hour,
      int minute,
      int second,
      int millisecond,
      int microsecond,
      int nanosecond,
      double increment,
      TemporalUtil.Unit unit,
      TemporalUtil.RoundingMode roundingMode,
      Long dayLength
   ) {
      JSTemporalDurationRecord rt = roundTime(hour, minute, second, millisecond, microsecond, nanosecond, increment, unit, roundingMode, dayLength);
      JSTemporalDateTimeRecord br = balanceISODate(year, month, day + dtoi(rt.getDays()));
      return JSTemporalDurationRecord.create(
         br.getYear(),
         br.getMonth(),
         br.getDay(),
         rt.getHours(),
         rt.getMinutes(),
         rt.getSeconds(),
         rt.getMilliseconds(),
         rt.getMicroseconds(),
         rt.getNanoseconds()
      );
   }

   public static double toTemporalDateTimeRoundingIncrement(
      JSDynamicObject options, TemporalUtil.Unit smallestUnit, IsObjectNode isObject, JSToNumberNode toNumber
   ) {
      int maximum = 0;
      short var5;
      if (TemporalUtil.Unit.DAY == smallestUnit) {
         var5 = 1;
      } else if (TemporalUtil.Unit.HOUR == smallestUnit) {
         var5 = 24;
      } else if (TemporalUtil.Unit.MINUTE != smallestUnit && TemporalUtil.Unit.SECOND != smallestUnit) {
         assert TemporalUtil.Unit.MILLISECOND == smallestUnit || TemporalUtil.Unit.MICROSECOND == smallestUnit || TemporalUtil.Unit.NANOSECOND == smallestUnit;

         var5 = 1000;
      } else {
         var5 = 60;
      }

      return toTemporalRoundingIncrement(options, (double)var5, false, isObject, toNumber);
   }

   public static boolean isValidTime(int hours, int minutes, int seconds, int milliseconds, int microseconds, int nanoseconds) {
      if (hours < 0 || hours > 23) {
         return false;
      } else if (minutes < 0 || minutes > 59) {
         return false;
      } else if (seconds < 0 || seconds > 59) {
         return false;
      } else if (milliseconds < 0 || milliseconds > 999) {
         return false;
      } else {
         return microseconds < 0 || microseconds > 999 ? false : nanoseconds >= 0 && nanoseconds <= 999;
      }
   }

   public static boolean isValidISODate(int year, int month, int day) {
      return month < 1 || month > 12 ? false : day >= 1 && day <= isoDaysInMonth(year, month);
   }

   public static JSTemporalPlainDateTimeObject systemDateTime(Object temporalTimeZoneLike, Object calendarLike, JSContext ctx) {
      JSDynamicObject timeZone = null;
      if (temporalTimeZoneLike == Undefined.instance) {
         timeZone = systemTimeZone(ctx);
      } else {
         timeZone = toTemporalTimeZone(ctx, temporalTimeZoneLike);
      }

      JSDynamicObject calendar = toTemporalCalendar(ctx, calendarLike);
      JSDynamicObject instant = systemInstant(ctx);
      return builtinTimeZoneGetPlainDateTimeFor(ctx, timeZone, instant, calendar);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalPlainDateTimeObject builtinTimeZoneGetPlainDateTimeFor(
      JSContext ctx, JSDynamicObject timeZone, JSDynamicObject instant, JSDynamicObject calendar
   ) {
      long offsetNanoseconds = getOffsetNanosecondsFor(timeZone, instant);
      JSTemporalDateTimeRecord result = getISOPartsFromEpoch(((JSTemporalInstantObject)instant).getNanoseconds());
      JSTemporalDateTimeRecord result2 = balanceISODateTime(
         result.getYear(),
         result.getMonth(),
         result.getDay(),
         result.getHour(),
         result.getMinute(),
         result.getSecond(),
         result.getMillisecond(),
         result.getMicrosecond(),
         result.getNanosecond() + offsetNanoseconds
      );
      return JSTemporalPlainDateTime.create(
         ctx,
         result2.getYear(),
         result2.getMonth(),
         result2.getDay(),
         result2.getHour(),
         result2.getMinute(),
         result2.getSecond(),
         result2.getMillisecond(),
         result2.getMicrosecond(),
         result2.getNanosecond(),
         calendar
      );
   }

   public static JSTemporalDateTimeRecord balanceISODateTime(
      int year, int month, int day, int hour, int minute, int second, int millisecond, int microsecond, long nanosecond
   ) {
      JSTemporalDurationRecord bt = balanceTime(hour, minute, second, millisecond, microsecond, nanosecond);
      JSTemporalDateTimeRecord bd = balanceISODate(year, month, day + dtoi(bt.getDays()));
      return JSTemporalDateTimeRecord.create(
         bd.getYear(),
         bd.getMonth(),
         bd.getDay(),
         dtoi(bt.getHours()),
         dtoi(bt.getMinutes()),
         dtoi(bt.getSeconds()),
         dtoi(bt.getMilliseconds()),
         dtoi(bt.getMicroseconds()),
         dtoi(bt.getNanoseconds())
      );
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalDateTimeRecord getISOPartsFromEpoch(BigInt epochNanoseconds) {
      long remainderNs;
      long epochMilliseconds;
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
      } else {
         Double nanos = ((Number)offsetNanoseconds).doubleValue();
         if (JSRuntime.isInteger(nanos) && !(Math.abs(nanos) > 8.64E13)) {
            return nanos.longValue();
         } else {
            throw Errors.createRangeError("out-of-range Number");
         }
      }
   }

   public static JSDynamicObject systemZonedDateTime(Object temporalTimeZoneLike, Object calendarLike, JSContext ctx) {
      JSDynamicObject timeZone = null;
      if (temporalTimeZoneLike == Undefined.instance) {
         timeZone = systemTimeZone(ctx);
      } else {
         timeZone = toTemporalTimeZone(ctx, temporalTimeZoneLike);
      }

      JSDynamicObject calendar = toTemporalCalendar(ctx, calendarLike);
      BigInt ns = systemUTCEpochNanoseconds();
      return JSTemporalZonedDateTime.create(ctx, ns, timeZone, calendar);
   }

   public static JSDynamicObject systemInstant(JSContext ctx) {
      BigInt ns = systemUTCEpochNanoseconds();
      return JSTemporalInstant.create(ctx, ns);
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInt systemUTCEpochNanoseconds() {
      JSRealm realm = JSRealm.get(null);
      BigInt ns = BigInt.valueOf(realm.nanoTimeWallClock());

      assert ns.compareTo(upperEpochNSLimit) <= 0 && ns.compareTo(lowerEpochNSLimit) >= 0;

      return ns;
   }

   public static JSDynamicObject systemTimeZone(JSContext ctx) {
      TruffleString identifier = defaultTimeZone();
      return createTemporalTimeZone(ctx, identifier);
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
      return nanoseconds == null ? true : nanoseconds.compareTo(lowerEpochNSLimit) >= 0 && nanoseconds.compareTo(upperEpochNSLimit) <= 0;
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInt addInstant(
      BigInt epochNanoseconds, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds
   ) {
      return addInstant(
         epochNanoseconds, dtol(hours), dtol(minutes), dtol(seconds), dtol(milliseconds), dtol(microseconds), BigInteger.valueOf(dtol(nanoseconds))
      );
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInt addInstant(
      BigInt epochNanoseconds, long hours, long minutes, long seconds, long milliseconds, long microseconds, BigInteger nanoseconds
   ) {
      BigInteger res = epochNanoseconds.bigIntegerValue().add(nanoseconds);
      res = res.add(BigInteger.valueOf(microseconds).multiply(BI_1000));
      res = res.add(BigInteger.valueOf(milliseconds).multiply(BI_10_POW_6));
      res = res.add(BigInteger.valueOf(seconds).multiply(BI_10_POW_9));
      res = res.add(BigInteger.valueOf(minutes).multiply(BI_6_10_POW_10));
      res = res.add(BigInteger.valueOf(hours).multiply(BI_36_10_POW_11));
      BigInt result = new BigInt(res);
      if (!isValidEpochNanoseconds(result)) {
         throw TemporalErrors.createRangeErrorInvalidNanoseconds();
      } else {
         return result;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInteger differenceInstant(
      BigInt ns1, BigInt ns2, double roundingIncrement, TemporalUtil.Unit smallestUnit, TemporalUtil.RoundingMode roundingMode
   ) {
      return roundTemporalInstant(ns2.subtract(ns1), roundingIncrement, smallestUnit, roundingMode);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString temporalInstantToString(JSContext ctx, JSRealm realm, JSDynamicObject instant, JSDynamicObject timeZone, Object precision) {
      JSDynamicObject outputTimeZone = timeZone;
      if (timeZone == Undefined.instance) {
         outputTimeZone = createTemporalTimeZone(ctx, TemporalConstants.UTC);
      }

      JSDynamicObject isoCalendar = getISO8601Calendar(ctx, realm);
      JSTemporalPlainDateTimeObject dateTime = builtinTimeZoneGetPlainDateTimeFor(ctx, outputTimeZone, instant, isoCalendar);
      TruffleString dateTimeString = JSTemporalPlainDateTime.temporalDateTimeToString(
         dateTime.getYear(),
         dateTime.getMonth(),
         dateTime.getDay(),
         dateTime.getHour(),
         dateTime.getMinute(),
         dateTime.getSecond(),
         dateTime.getMillisecond(),
         dateTime.getMicrosecond(),
         dateTime.getNanosecond(),
         Undefined.instance,
         precision,
         TemporalUtil.ShowCalendar.NEVER
      );
      TruffleString timeZoneString = null;
      if (timeZone == Undefined.instance) {
         timeZoneString = Strings.UC_Z;
      } else {
         long offsetNs = getOffsetNanosecondsFor(timeZone, instant);
         timeZoneString = formatISOTimeZoneOffsetString(offsetNs);
      }

      return Strings.concat(dateTimeString, timeZoneString);
   }

   public static TruffleString builtinTimeZoneGetOffsetStringFor(JSDynamicObject timeZone, JSDynamicObject instant) {
      long offsetNanoseconds = getOffsetNanosecondsFor(timeZone, instant);
      return formatTimeZoneOffsetString(offsetNanoseconds);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString formatTimeZoneOffsetString(long offsetNanosecondsParam) {
      TruffleString sign = offsetNanosecondsParam >= 0L ? Strings.SYMBOL_PLUS : Strings.SYMBOL_MINUS;
      long offsetNanoseconds = Math.abs(offsetNanosecondsParam);
      long nanoseconds = offsetNanoseconds % 1000000000L;
      double s1 = Math.floor(offsetNanoseconds / 1.0E9) % 60.0;
      double m1 = Math.floor(offsetNanoseconds / 6.0E10) % 60.0;
      double h1 = Math.floor(offsetNanoseconds / 3.6E12);
      long seconds = (long)s1;
      long minutes = (long)m1;
      long hours = (long)h1;
      TruffleString h = Strings.format("%1$02d", hours);
      TruffleString m = Strings.format("%1$02d", minutes);
      TruffleString s = Strings.format("%1$02d", seconds);
      TruffleString post = Strings.EMPTY_STRING;
      if (nanoseconds != 0L) {
         TruffleString fraction = longestSubstring(Strings.format("%1$09d", nanoseconds));
         post = Strings.concatAll(Strings.COLON, s, Strings.DOT, fraction);
      } else if (seconds != 0L) {
         post = Strings.concat(Strings.COLON, s);
      }

      return Strings.concatAll(sign, h, Strings.COLON, m, post);
   }

   @CompilerDirectives.TruffleBoundary
   public static long parseTimeZoneOffsetString(TruffleString string) {
      JSTemporalParserRecord rec = new TemporalParser(string).parseTimeZoneNumericUTCOffset();
      if (rec == null) {
         throw Errors.createRangeError("TemporalTimeZoneNumericUTCOffset expected");
      } else {
         long nanoseconds;
         if (rec.getOffsetFraction() == null) {
            nanoseconds = 0L;
         } else {
            TruffleString fraction = Strings.concat(rec.getOffsetFraction(), ZEROS);
            fraction = Strings.lazySubstring(fraction, 0, 9);

            try {
               nanoseconds = Strings.parseLong(fraction, 10);
            } catch (TruffleString.NumberFormatException var12) {
               throw CompilerDirectives.shouldNotReachHere(var12);
            }
         }

         TruffleString signS = rec.getOffsetSign();
         int sign = !Strings.SYMBOL_MINUS.equals(signS) && !Strings.UNICODE_MINUS_SIGN.equals(signS) ? 1 : -1;
         long hours = rec.getOffsetHour() == Long.MIN_VALUE ? 0L : rec.getOffsetHour();
         long minutes = rec.getOffsetMinute() == Long.MIN_VALUE ? 0L : rec.getOffsetMinute();
         long seconds = rec.getOffsetSecond() == Long.MIN_VALUE ? 0L : rec.getOffsetSecond();
         return sign * (((hours * 60L + minutes) * 60L + seconds) * 1000000000L + nanoseconds);
      }
   }

   public static JSTemporalTimeZoneRecord parseTemporalTimeZoneString(TruffleString string) {
      return parseTemporalTimeZoneString(string, false);
   }

   @CompilerDirectives.TruffleBoundary
   private static JSTemporalTimeZoneRecord parseTemporalTimeZoneString(TruffleString string, boolean offsetRequired) {
      JSTemporalParserRecord rec = new TemporalParser(string).parseTimeZoneString();
      if (rec == null) {
         throw Errors.createRangeError("TemporalTimeZoneString expected");
      } else if (offsetRequired && rec.getOffsetHour() == Long.MIN_VALUE && !rec.getZ()) {
         throw TemporalErrors.createRangeErrorTimeZoneOffsetExpected();
      } else {
         TruffleString name = rec.getTimeZoneIANAName();
         TruffleString offsetString = rec.getTimeZoneNumericUTCOffset();
         return rec.getZ() ? JSTemporalTimeZoneRecord.create(true, null, name) : JSTemporalTimeZoneRecord.create(false, offsetString, name);
      }
   }

   public static TemporalUtil.Disambiguation toTemporalDisambiguation(
      JSDynamicObject options, TemporalGetOptionNode getOptionNode, TruffleString.EqualNode equalNode
   ) {
      return options == Undefined.instance
         ? TemporalUtil.Disambiguation.COMPATIBLE
         : toDisambiguation(
            (TruffleString)getOptionNode.execute(
               options, TemporalConstants.DISAMBIGUATION, TemporalUtil.OptionType.STRING, listDisambiguation, TemporalConstants.COMPATIBLE
            ),
            equalNode
         );
   }

   public static TemporalUtil.OffsetOption toTemporalOffset(
      JSDynamicObject options, TruffleString fallback, TemporalGetOptionNode getOptionNode, TruffleString.EqualNode equalNode
   ) {
      TruffleString result = fallback;
      if (options != Undefined.instance) {
         result = (TruffleString)getOptionNode.execute(options, TemporalConstants.OFFSET, TemporalUtil.OptionType.STRING, listOffset, fallback);
      }

      return toOffsetOption(result, equalNode);
   }

   public static TruffleString toShowTimeZoneNameOption(JSDynamicObject options, TemporalGetOptionNode getOptionNode) {
      return (TruffleString)getOptionNode.execute(
         options, TemporalConstants.TIME_ZONE_NAME, TemporalUtil.OptionType.STRING, listAutoNever, TemporalConstants.AUTO
      );
   }

   public static TruffleString toShowOffsetOption(JSDynamicObject options, TemporalGetOptionNode getOptionNode) {
      return (TruffleString)getOptionNode.execute(options, TemporalConstants.OFFSET, TemporalUtil.OptionType.STRING, listAutoNever, TemporalConstants.AUTO);
   }

   public static TruffleString temporalZonedDateTimeToString(
      JSContext ctx,
      JSRealm realm,
      JSDynamicObject zonedDateTime,
      Object precision,
      TemporalUtil.ShowCalendar showCalendar,
      TruffleString showTimeZone,
      TruffleString showOffset
   ) {
      return temporalZonedDateTimeToString(
         ctx, realm, zonedDateTime, precision, showCalendar, showTimeZone, showOffset, null, TemporalUtil.Unit.EMPTY, TemporalUtil.RoundingMode.EMPTY
      );
   }

   public static JSTemporalDateTimeRecord addDateTime(
      JSContext ctx,
      int year,
      int month,
      int day,
      int hour,
      int minute,
      int second,
      int millisecond,
      int microsecond,
      double nanosecond,
      JSDynamicObject calendar,
      double years,
      double months,
      double weeks,
      double days,
      double hours,
      double minutes,
      double seconds,
      double milliseconds,
      double microseconds,
      double nanoseconds,
      JSDynamicObject options
   ) {
      JSTemporalDurationRecord timeResult = addTimeDouble(
         hour, minute, second, millisecond, microsecond, nanosecond, hours, minutes, seconds, milliseconds, microseconds, nanoseconds
      );
      JSTemporalPlainDateObject datePart = JSTemporalPlainDate.create(ctx, year, month, day, calendar);
      JSDynamicObject dateDuration = JSTemporalDuration.createTemporalDuration(
         ctx, years, months, weeks, days + timeResult.getDays(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
      );
      JSTemporalPlainDateObject addedDate = (JSTemporalPlainDateObject)calendarDateAdd(calendar, datePart, dateDuration, options);
      return JSTemporalDateTimeRecord.create(
         addedDate.getYear(),
         addedDate.getMonth(),
         addedDate.getDay(),
         dtoi(timeResult.getHours()),
         dtoi(timeResult.getMinutes()),
         dtoi(timeResult.getSeconds()),
         dtoi(timeResult.getMilliseconds()),
         dtoi(timeResult.getMicroseconds()),
         dtoi(timeResult.getNanoseconds())
      );
   }

   public static int compareISODateTime(
      int year,
      int month,
      int day,
      int hours,
      int minutes,
      int seconds,
      int milliseconds,
      int microseconds,
      int nanoseconds,
      int year2,
      int month2,
      int day2,
      int hours2,
      int minutes2,
      int seconds2,
      int milliseconds2,
      int microseconds2,
      int nanoseconds2
   ) {
      int date = compareISODate(year, month, day, year2, month2, day2);
      return date == 0
         ? compareTemporalTime(
            hours, minutes, seconds, milliseconds, microseconds, nanoseconds, hours2, minutes2, seconds2, milliseconds2, microseconds2, nanoseconds2
         )
         : date;
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalDateTimeRecord parseTemporalYearMonthString(TruffleString string) {
      JSTemporalParserRecord rec = new TemporalParser(string).parseYearMonth();
      if (rec != null) {
         if (rec.getZ()) {
            throw TemporalErrors.createRangeErrorUnexpectedUTCDesignator();
         } else if (rec.getYear() != 0L
            || Strings.indexOf(string, TemporalConstants.MINUS_000000) < 0 && Strings.indexOf(string, TemporalConstants.UNICODE_MINUS_SIGN_000000) < 0) {
            int y = rec.getYear() == Long.MIN_VALUE ? 0 : ltoi(rec.getYear());
            int m = rec.getMonth() == Long.MIN_VALUE ? 0 : ltoi(rec.getMonth());
            int d = rec.getDay() == Long.MIN_VALUE ? 1 : ltoi(rec.getDay());
            return JSTemporalDateTimeRecord.createCalendar(y, m, d, 0, 0, 0, 0, 0, 0, rec.getCalendar());
         } else {
            throw TemporalErrors.createRangeErrorInvalidPlainDateTime();
         }
      } else {
         throw Errors.createRangeError("cannot parse YearMonth");
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString temporalZonedDateTimeToString(
      JSContext ctx,
      JSRealm realm,
      JSDynamicObject zonedDateTimeParam,
      Object precision,
      TemporalUtil.ShowCalendar showCalendar,
      TruffleString showTimeZone,
      TruffleString showOffset,
      Double incrementParam,
      TemporalUtil.Unit unitParam,
      TemporalUtil.RoundingMode roundingModeParam
   ) {
      assert isTemporalZonedDateTime(zonedDateTimeParam);

      assert unitParam != null && roundingModeParam != null;

      JSTemporalZonedDateTimeObject zonedDateTime = (JSTemporalZonedDateTimeObject)zonedDateTimeParam;
      double increment = incrementParam == null ? 1.0 : incrementParam;
      TemporalUtil.Unit unit = unitParam == TemporalUtil.Unit.EMPTY ? TemporalUtil.Unit.NANOSECOND : unitParam;
      TemporalUtil.RoundingMode roundingMode = roundingModeParam == TemporalUtil.RoundingMode.EMPTY ? TemporalUtil.RoundingMode.TRUNC : roundingModeParam;
      BigInteger ns = roundTemporalInstant(zonedDateTime.getNanoseconds(), (double)((long)increment), unit, roundingMode);
      JSDynamicObject timeZone = zonedDateTime.getTimeZone();
      JSTemporalInstantObject instant = JSTemporalInstant.create(ctx, new BigInt(ns));
      JSTemporalCalendarObject isoCalendar = getISO8601Calendar(ctx, realm);
      JSTemporalPlainDateTimeObject temporalDateTime = builtinTimeZoneGetPlainDateTimeFor(ctx, timeZone, instant, isoCalendar);
      TruffleString dateTimeString = JSTemporalPlainDateTime.temporalDateTimeToString(
         temporalDateTime.getYear(),
         temporalDateTime.getMonth(),
         temporalDateTime.getDay(),
         temporalDateTime.getHour(),
         temporalDateTime.getMinute(),
         temporalDateTime.getSecond(),
         temporalDateTime.getMillisecond(),
         temporalDateTime.getMicrosecond(),
         temporalDateTime.getNanosecond(),
         isoCalendar,
         precision,
         TemporalUtil.ShowCalendar.NEVER
      );
      TruffleString offsetString = null;
      TruffleString timeZoneString = null;
      if (TemporalConstants.NEVER.equals(showOffset)) {
         offsetString = Strings.EMPTY_STRING;
      } else {
         long offsetNs = getOffsetNanosecondsFor(timeZone, instant);
         offsetString = formatISOTimeZoneOffsetString(offsetNs);
      }

      if (TemporalConstants.NEVER.equals(showTimeZone)) {
         timeZoneString = Strings.EMPTY_STRING;
      } else {
         TruffleString timeZoneID = JSRuntime.toString(timeZone);
         timeZoneString = Strings.addBrackets(timeZoneID);
      }

      TruffleString calendarID = JSRuntime.toString(zonedDateTime.getCalendar());
      TruffleString calendarString = formatCalendarAnnotation(calendarID, showCalendar);
      return Strings.concatAll(dateTimeString, offsetString, timeZoneString, calendarString);
   }

   @CompilerDirectives.TruffleBoundary
   private static TruffleString formatISOTimeZoneOffsetString(long offsetNs) {
      long offsetNanoseconds = dtol(roundNumberToIncrement(offsetNs, 6.0E10, TemporalUtil.RoundingMode.HALF_EXPAND));
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
      if (!new TemporalParser(string).isTemporalZonedDateTimeString()) {
         throw Errors.createRangeError("cannot be parsed as TemporalZonedDateTimeString");
      } else {
         JSTemporalDateTimeRecord result;
         try {
            result = parseISODateTime(string);
         } catch (Exception var3) {
            throw Errors.createRangeError("cannot be parsed as TemporalZonedDateTimeString");
         }

         JSTemporalTimeZoneRecord timeZoneResult = parseTemporalTimeZoneString(string);
         return JSTemporalZonedDateTimeRecord.create(
            result.getYear(),
            result.getMonth(),
            result.getDay(),
            result.getHour(),
            result.getMinute(),
            result.getSecond(),
            result.getMillisecond(),
            result.getMicrosecond(),
            result.getNanosecond(),
            result.getCalendar(),
            timeZoneResult.isZ(),
            timeZoneResult.getOffsetString(),
            timeZoneResult.getName()
         );
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInt parseTemporalInstant(TruffleString string) {
      JSTemporalZonedDateTimeRecord result = parseTemporalInstantString(string);
      TruffleString offsetString = result.getTimeZoneOffsetString();

      assert offsetString != null;

      BigInteger utc = getEpochFromISOParts(
         result.getYear(),
         result.getMonth(),
         result.getDay(),
         result.getHour(),
         result.getMinute(),
         result.getSecond(),
         result.getMillisecond(),
         result.getMicrosecond(),
         result.getNanosecond()
      );
      long offsetNanoseconds = parseTimeZoneOffsetString(offsetString);
      BigInt instant = new BigInt(utc.subtract(BigInteger.valueOf(offsetNanoseconds)));
      if (!isValidEpochNanoseconds(instant)) {
         throw TemporalErrors.createRangeErrorInvalidNanoseconds();
      } else {
         return instant;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static JSTemporalZonedDateTimeRecord parseTemporalInstantString(TruffleString string) {
      try {
         JSTemporalDateTimeRecord result = parseISODateTime(string);
         JSTemporalTimeZoneRecord timeZoneResult = parseTemporalTimeZoneString(string, true);
         TruffleString offsetString = timeZoneResult.getOffsetString();
         if (timeZoneResult.isZ()) {
            offsetString = OFFSET_ZERO;
         }

         assert offsetString != null;

         return JSTemporalZonedDateTimeRecord.create(
            result.getYear(),
            result.getMonth(),
            result.getDay(),
            result.getHour(),
            result.getMinute(),
            result.getSecond(),
            result.getMillisecond(),
            result.getMicrosecond(),
            result.getNanosecond(),
            null,
            false,
            offsetString,
            null
         );
      } catch (Exception var4) {
         throw Errors.createRangeError("Instant cannot be parsed");
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalInstantObject builtinTimeZoneGetInstantFor(
      JSContext ctx, JSDynamicObject timeZone, JSTemporalPlainDateTimeObject dateTime, TemporalUtil.Disambiguation disambiguation
   ) {
      List<JSTemporalInstantObject> possibleInstants = getPossibleInstantsFor(timeZone, dateTime);
      return disambiguatePossibleInstants(ctx, possibleInstants, timeZone, dateTime, disambiguation);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSTemporalInstantObject disambiguatePossibleInstants(
      JSContext ctx,
      List<JSTemporalInstantObject> possibleInstants,
      JSDynamicObject timeZone,
      JSTemporalPlainDateTimeObject dateTime,
      TemporalUtil.Disambiguation disambiguation
   ) {
      int n = possibleInstants.size();
      if (n == 1) {
         return possibleInstants.get(0);
      } else if (n != 0) {
         if (TemporalUtil.Disambiguation.EARLIER == disambiguation || TemporalUtil.Disambiguation.COMPATIBLE == disambiguation) {
            return possibleInstants.get(0);
         } else if (TemporalUtil.Disambiguation.LATER == disambiguation) {
            return possibleInstants.get(n - 1);
         } else {
            assert TemporalUtil.Disambiguation.REJECT == disambiguation;

            throw Errors.createRangeError("invalid disambiguation");
         }
      } else {
         assert n == 0;

         if (TemporalUtil.Disambiguation.REJECT == disambiguation) {
            throw Errors.createRangeError("disambiguation failed");
         } else {
            BigInteger epochNanoseconds = getEpochFromISOParts(
               dateTime.getYear(),
               dateTime.getMonth(),
               dateTime.getDay(),
               dateTime.getHour(),
               dateTime.getMinute(),
               dateTime.getSecond(),
               dateTime.getMillisecond(),
               dateTime.getMicrosecond(),
               dateTime.getNanosecond()
            );
            JSTemporalInstantObject dayBefore = JSTemporalInstant.create(ctx, new BigInt(epochNanoseconds.subtract(BI_8_64_13)));
            JSTemporalInstantObject dayAfter = JSTemporalInstant.create(ctx, new BigInt(epochNanoseconds.add(BI_8_64_13)));
            long offsetBefore = getOffsetNanosecondsFor(timeZone, dayBefore);
            long offsetAfter = getOffsetNanosecondsFor(timeZone, dayAfter);
            long nanoseconds = offsetAfter - offsetBefore;
            if (TemporalUtil.Disambiguation.EARLIER == disambiguation) {
               JSTemporalDateTimeRecord earlier = addDateTime(
                  ctx,
                  dateTime.getYear(),
                  dateTime.getMonth(),
                  dateTime.getDay(),
                  dateTime.getHour(),
                  dateTime.getMinute(),
                  dateTime.getSecond(),
                  dateTime.getMillisecond(),
                  dateTime.getMicrosecond(),
                  dateTime.getNanosecond(),
                  dateTime.getCalendar(),
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  -nanoseconds,
                  Undefined.instance
               );
               JSTemporalPlainDateTimeObject earlierDateTime = JSTemporalPlainDateTime.create(
                  ctx,
                  earlier.getYear(),
                  earlier.getMonth(),
                  earlier.getDay(),
                  earlier.getHour(),
                  earlier.getMinute(),
                  earlier.getSecond(),
                  earlier.getMillisecond(),
                  earlier.getMicrosecond(),
                  earlier.getNanosecond(),
                  dateTime.getCalendar()
               );
               List<JSTemporalInstantObject> possibleInstants2 = getPossibleInstantsFor(timeZone, earlierDateTime);
               if (possibleInstants2.size() == 0) {
                  throw Errors.createRangeError("nothing found");
               } else {
                  return possibleInstants2.get(0);
               }
            } else {
               assert TemporalUtil.Disambiguation.LATER == disambiguation || TemporalUtil.Disambiguation.COMPATIBLE == disambiguation;

               JSTemporalDateTimeRecord later = addDateTime(
                  ctx,
                  dateTime.getYear(),
                  dateTime.getMonth(),
                  dateTime.getDay(),
                  dateTime.getHour(),
                  dateTime.getMinute(),
                  dateTime.getSecond(),
                  dateTime.getMillisecond(),
                  dateTime.getMicrosecond(),
                  dateTime.getNanosecond(),
                  dateTime.getCalendar(),
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  nanoseconds,
                  Undefined.instance
               );
               JSTemporalPlainDateTimeObject laterDateTime = JSTemporalPlainDateTime.create(
                  ctx,
                  later.getYear(),
                  later.getMonth(),
                  later.getDay(),
                  later.getHour(),
                  later.getMinute(),
                  later.getSecond(),
                  later.getMillisecond(),
                  later.getMicrosecond(),
                  later.getNanosecond(),
                  dateTime.getCalendar()
               );
               List<JSTemporalInstantObject> possibleInstants2 = getPossibleInstantsFor(timeZone, laterDateTime);
               n = possibleInstants2.size();
               if (n == 0) {
                  throw Errors.createRangeError("nothing found");
               } else {
                  return possibleInstants2.get(n - 1);
               }
            }
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInt interpretISODateTimeOffset(
      JSContext ctx,
      JSRealm realm,
      int year,
      int month,
      int day,
      int hour,
      int minute,
      int second,
      int millisecond,
      int microsecond,
      int nanosecond,
      TemporalUtil.OffsetBehaviour offsetBehaviour,
      Object offsetNanosecondsParam,
      JSDynamicObject timeZone,
      TemporalUtil.Disambiguation disambiguation,
      TemporalUtil.OffsetOption offsetOption,
      TemporalUtil.MatchBehaviour matchBehaviour
   ) {
      double offsetNs = offsetNanosecondsParam != null && offsetNanosecondsParam != Undefined.instance
         ? ((Number)offsetNanosecondsParam).doubleValue()
         : Double.NaN;
      JSDynamicObject calendar = getISO8601Calendar(ctx, realm);
      JSTemporalPlainDateTimeObject dateTime = JSTemporalPlainDateTime.create(
         ctx, year, month, day, hour, minute, second, millisecond, microsecond, nanosecond, calendar
      );
      if (offsetBehaviour != TemporalUtil.OffsetBehaviour.WALL && TemporalUtil.OffsetOption.IGNORE != offsetOption) {
         if (offsetBehaviour != TemporalUtil.OffsetBehaviour.EXACT && TemporalUtil.OffsetOption.USE != offsetOption) {
            assert offsetBehaviour == TemporalUtil.OffsetBehaviour.OPTION;

            assert TemporalUtil.OffsetOption.PREFER == offsetOption || TemporalUtil.OffsetOption.REJECT == offsetOption;

            for (JSTemporalInstantObject candidate : getPossibleInstantsFor(timeZone, dateTime)) {
               long candidateNanoseconds = getOffsetNanosecondsFor(timeZone, candidate);
               if (candidateNanoseconds == offsetNs) {
                  return candidate.getNanoseconds();
               }

               if (matchBehaviour == TemporalUtil.MatchBehaviour.MATCH_MINUTES) {
                  long roundedCandidateNanoseconds = dtol(roundNumberToIncrement(candidateNanoseconds, 6.0E10, TemporalUtil.RoundingMode.HALF_EXPAND));
                  if (roundedCandidateNanoseconds == offsetNs) {
                     return candidate.getNanoseconds();
                  }
               }
            }

            if (TemporalUtil.OffsetOption.REJECT == offsetOption) {
               throw Errors.createRangeError("cannot interpret DateTime offset");
            } else {
               JSTemporalInstantObject instant = builtinTimeZoneGetInstantFor(ctx, timeZone, dateTime, disambiguation);
               return instant.getNanoseconds();
            }
         } else {
            BigInteger epochNanoseconds = getEpochFromISOParts(year, month, day, hour, minute, second, millisecond, microsecond, nanosecond);
            return new BigInt(epochNanoseconds.subtract(BigInteger.valueOf((long)offsetNs)));
         }
      } else {
         JSTemporalInstantObject instant = builtinTimeZoneGetInstantFor(ctx, timeZone, dateTime, disambiguation);
         return instant.getNanoseconds();
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInt addZonedDateTime(
      JSContext ctx,
      BigInt epochNanoseconds,
      JSDynamicObject timeZone,
      JSDynamicObject calendar,
      long years,
      long months,
      long weeks,
      long days,
      long hours,
      long minutes,
      long seconds,
      long milliseconds,
      long microseconds,
      long nanoseconds
   ) {
      return addZonedDateTime(
         ctx,
         epochNanoseconds,
         timeZone,
         calendar,
         years,
         months,
         weeks,
         days,
         hours,
         minutes,
         seconds,
         milliseconds,
         microseconds,
         BigInteger.valueOf(nanoseconds),
         Undefined.instance
      );
   }

   @CompilerDirectives.TruffleBoundary
   public static BigInt addZonedDateTime(
      JSContext ctx,
      BigInt epochNanoseconds,
      JSDynamicObject timeZone,
      JSDynamicObject calendar,
      long years,
      long months,
      long weeks,
      long days,
      long hours,
      long minutes,
      long seconds,
      long milliseconds,
      long microseconds,
      BigInteger nanoseconds,
      JSDynamicObject options
   ) {
      if (years == 0L && months == 0L && weeks == 0L && days == 0L) {
         return addInstant(epochNanoseconds, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
      } else {
         JSTemporalInstantObject instant = JSTemporalInstant.create(ctx, epochNanoseconds);
         JSTemporalPlainDateTimeObject temporalDateTime = builtinTimeZoneGetPlainDateTimeFor(ctx, timeZone, instant, calendar);
         JSTemporalPlainDateObject datePart = JSTemporalPlainDate.create(
            ctx, temporalDateTime.getYear(), temporalDateTime.getMonth(), temporalDateTime.getDay(), calendar
         );
         JSTemporalDurationObject dateDuration = JSTemporalDuration.createTemporalDuration(ctx, years, months, weeks, days, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
         JSTemporalPlainDateObject addedDate = (JSTemporalPlainDateObject)calendarDateAdd(calendar, datePart, dateDuration, options);
         JSTemporalPlainDateTimeObject intermediateDateTime = JSTemporalPlainDateTime.create(
            ctx,
            addedDate.getYear(),
            addedDate.getMonth(),
            addedDate.getDay(),
            temporalDateTime.getHour(),
            temporalDateTime.getMinute(),
            temporalDateTime.getSecond(),
            temporalDateTime.getMillisecond(),
            temporalDateTime.getMicrosecond(),
            temporalDateTime.getNanosecond(),
            calendar
         );
         JSTemporalInstantObject intermediateInstant = builtinTimeZoneGetInstantFor(ctx, timeZone, intermediateDateTime, TemporalUtil.Disambiguation.COMPATIBLE);
         return addInstant(intermediateInstant.getNanoseconds(), hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
      }
   }

   public static JSDynamicObject moveRelativeZonedDateTime(JSContext ctx, JSDynamicObject zonedDateTime, long years, long months, long weeks, long days) {
      JSTemporalZonedDateTimeObject zdt = (JSTemporalZonedDateTimeObject)zonedDateTime;
      BigInt intermediateNs = addZonedDateTime(
         ctx, zdt.getNanoseconds(), zdt.getTimeZone(), zdt.getCalendar(), years, months, weeks, days, 0L, 0L, 0L, 0L, 0L, 0L
      );
      return JSTemporalZonedDateTime.create(ctx, intermediateNs, zdt.getTimeZone(), zdt.getCalendar());
   }

   public static boolean timeZoneEquals(JSDynamicObject tz1, JSDynamicObject tz2, JSToStringNode toStringNode) {
      if (tz1 == tz2) {
         return true;
      } else {
         TruffleString s1 = toStringNode.executeString(tz1);
         TruffleString s2 = toStringNode.executeString(tz2);
         return Boundaries.equals(s1, s2);
      }
   }

   public static JSDynamicObject consolidateCalendars(JSDynamicObject one, JSDynamicObject two, JSToStringNode toStringNode) {
      if (one == two) {
         return two;
      } else {
         TruffleString s1 = toStringNode.executeString(one);
         TruffleString s2 = toStringNode.executeString(two);
         return consolidateCalendarsIntl(one, two, s1, s2);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static JSDynamicObject consolidateCalendarsIntl(JSDynamicObject one, JSDynamicObject two, TruffleString s1, TruffleString s2) {
      if (s1.equals(s2)) {
         return two;
      } else if (TemporalConstants.ISO8601.equals(s1)) {
         return two;
      } else if (TemporalConstants.ISO8601.equals(s2)) {
         return one;
      } else {
         throw Errors.createRangeError("cannot consolidate calendars");
      }
   }

   private static List<JSTemporalInstantObject> getPossibleInstantsFor(JSDynamicObject timeZone, JSDynamicObject dateTime) {
      Object fn = JSObject.get(timeZone, GET_POSSIBLE_INSTANTS_FOR);
      JSDynamicObject possibleInstants = toDynamicObject(JSRuntime.call(fn, timeZone, new Object[]{dateTime}));
      IteratorRecord iteratorRecord = JSRuntime.getIterator(possibleInstants);
      List<JSTemporalInstantObject> list = new ArrayList<>();
      Object next = true;

      while (next != Boolean.FALSE) {
         next = JSRuntime.iteratorStep(iteratorRecord);
         if (next != Boolean.FALSE) {
            Object nextValue = JSRuntime.iteratorValue((JSDynamicObject)next);
            if (!isTemporalInstant(nextValue)) {
               JSRuntime.iteratorClose(possibleInstants);
               throw Errors.createTypeError("unexpected value");
            }

            list.add((JSTemporalInstantObject)nextValue);
         }
      }

      return list;
   }

   @CompilerDirectives.TruffleBoundary
   public static List<BigInt> getIANATimeZoneEpochValue(
      TruffleString identifier,
      long isoYear,
      long isoMonth,
      long isoDay,
      long hours,
      long minutes,
      long seconds,
      long milliseconds,
      long microseconds,
      long nanoseconds
   ) {
      List<BigInt> list = new ArrayList<>();

      try {
         ZoneId zoneId = ZoneId.of(Strings.toJavaString(identifier));
         long fractions = milliseconds * 1000000L + microseconds * 1000L + nanoseconds;
         ZonedDateTime zdt = ZonedDateTime.of((int)isoYear, (int)isoMonth, (int)isoDay, (int)hours, (int)minutes, (int)seconds, (int)fractions, zoneId);
         list.add(BigInt.valueOf(zdt.toEpochSecond() * 1000000000L + fractions));
      } catch (Exception var24) {
         assert false;
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
         return offset.getTotalSeconds() * 1.0E9;
      } catch (Exception var6) {
         assert false;

         return -9.223372E18F;
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
         return nextTransition == null ? OptionalLong.empty() : OptionalLong.of(nextTransition.toEpochSecond() * 1000000000L);
      } catch (Exception var7) {
         assert false;

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
         return previousTransition == null ? OptionalLong.empty() : OptionalLong.of(previousTransition.toEpochSecond() * 1000000000L);
      } catch (Exception var7) {
         assert false;

         return OptionalLong.empty();
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean canParseAsTimeZoneNumericUTCOffset(TruffleString string) {
      try {
         JSTemporalParserRecord rec = new TemporalParser(string).parseTimeZoneNumericUTCOffset();
         return rec != null;
      } catch (Exception var2) {
         return false;
      }
   }

   public static boolean isoYearMonthWithinLimits(int year, int month) {
      if (year < -271821 || year > 275760) {
         return false;
      } else {
         return year == -271821 && month < 4 ? false : year != 275760 || month <= 9;
      }
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
      Object month = JSObject.get(fields, TemporalConstants.MONTH);
      Object monthCode = JSObject.get(fields, TemporalConstants.MONTH_CODE);
      if (monthCode == Undefined.instance) {
         if (month == Undefined.instance) {
            throw Errors.createTypeError("No month or month code present.");
         } else {
            return month;
         }
      } else {
         assert monthCode instanceof TruffleString;

         int monthLength = Strings.length((TruffleString)monthCode);
         if (monthLength != 3) {
            throw Errors.createRangeError("Month code should be in 3 character code.");
         } else {
            TruffleString numberPart = Strings.substring(ctx, (TruffleString)monthCode, 1);
            double numberPart2 = JSRuntime.doubleValue(toIntegerOrInfinity.executeNumber(numberPart));
            if (Double.isNaN(numberPart2)) {
               throw Errors.createRangeError("The last character of the monthCode should be a number.");
            } else if (!(numberPart2 < 1.0) && !(numberPart2 > 12.0)) {
               double m1 = month == Undefined.instance ? -1.0 : JSRuntime.doubleValue(toIntegerOrInfinity.executeNumber(month));
               if (month != Undefined.instance && m1 != numberPart2) {
                  throw Errors.createRangeError("Month does not equal the month code.");
               } else if (!identicalNode.executeBoolean(monthCode, buildISOMonthCode((int)numberPart2))) {
                  throw Errors.createRangeError("Not same value");
               } else {
                  return (long)numberPart2;
               }
            } else {
               throw Errors.createRangeError("monthCode out of bounds");
            }
         }
      }
   }

   public static JSTemporalDateTimeRecord isoDateFromFields(
      JSDynamicObject fields,
      JSDynamicObject options,
      JSContext ctx,
      IsObjectNode isObject,
      TemporalGetOptionNode getOptionNode,
      JSToIntegerOrInfinityNode toIntOrInfinityNode,
      JSIdenticalNode identicalNode
   ) {
      assert isObject.executeBoolean(fields);

      TemporalUtil.Overflow overflow = toTemporalOverflow(options, getOptionNode);
      JSDynamicObject preparedFields = prepareTemporalFields(ctx, fields, listDMMCY, listYD);
      Object year = JSObject.get(preparedFields, TemporalConstants.YEAR);
      Object month = resolveISOMonth(ctx, preparedFields, toIntOrInfinityNode, identicalNode);
      Object day = JSObject.get(preparedFields, TemporalConstants.DAY);
      return regulateISODate(
         dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(year))),
         dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(month))),
         dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(day))),
         overflow
      );
   }

   public static JSTemporalYearMonthDayRecord isoYearMonthFromFields(
      JSDynamicObject fields,
      JSDynamicObject options,
      JSContext ctx,
      IsObjectNode isObject,
      TemporalGetOptionNode getOptionNode,
      JSToIntegerOrInfinityNode toIntOrInfinityNode,
      JSIdenticalNode identicalNode
   ) {
      assert isObject.executeBoolean(fields);

      TemporalUtil.Overflow overflow = toTemporalOverflow(options, getOptionNode);
      JSDynamicObject preparedFields = prepareTemporalFields(ctx, fields, listMMCY, listY);
      Object year = JSObject.get(preparedFields, TemporalConstants.YEAR);
      Object month = resolveISOMonth(ctx, preparedFields, toIntOrInfinityNode, identicalNode);
      JSTemporalYearMonthDayRecord result = regulateISOYearMonth(
         dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(year))), dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(month))), overflow
      );
      return JSTemporalYearMonthDayRecord.create(result.getYear(), result.getMonth(), 1);
   }

   public static JSTemporalYearMonthDayRecord isoMonthDayFromFields(
      JSDynamicObject fields,
      JSDynamicObject options,
      JSContext ctx,
      IsObjectNode isObject,
      TemporalGetOptionNode getOptionNode,
      JSToIntegerOrInfinityNode toIntOrInfinityNode,
      JSIdenticalNode identicalNode
   ) {
      assert isObject.executeBoolean(fields);

      TemporalUtil.Overflow overflow = toTemporalOverflow(options, getOptionNode);
      JSDynamicObject preparedFields = prepareTemporalFields(ctx, fields, listDMMCY, listD);
      Object month = JSObject.get(preparedFields, TemporalConstants.MONTH);
      Object monthCode = JSObject.get(preparedFields, TemporalConstants.MONTH_CODE);
      Object year = JSObject.get(preparedFields, TemporalConstants.YEAR);
      if (month != Undefined.instance && monthCode == Undefined.instance && year == Undefined.instance) {
         throw Errors.createTypeError("A year or a month code should be present.");
      } else {
         month = resolveISOMonth(ctx, preparedFields, toIntOrInfinityNode, identicalNode);
         Object day = JSObject.get(preparedFields, TemporalConstants.DAY);
         int referenceISOYear = 1972;
         JSTemporalDateTimeRecord result = null;
         if (monthCode == Undefined.instance) {
            result = regulateISODate(
               dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(year))),
               dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(month))),
               dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(day))),
               overflow
            );
         } else {
            result = regulateISODate(
               referenceISOYear,
               dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(month))),
               dtoi(JSRuntime.doubleValue(toIntOrInfinityNode.executeNumber(day))),
               overflow
            );
         }

         return JSTemporalYearMonthDayRecord.create(referenceISOYear, result.getMonth(), result.getDay());
      }
   }

   public static long isoDay(JSDynamicObject temporalObject) {
      TemporalDay day = (TemporalDay)temporalObject;
      return day.getDay();
   }

   public static JSTemporalDurationRecord createDurationRecord(
      double years,
      double months,
      double weeks,
      double days,
      double hours,
      double minutes,
      double seconds,
      double milliseconds,
      double microseconds,
      double nanoseconds
   ) {
      if (!isValidDuration(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds)) {
         throw TemporalErrors.createTypeErrorDurationOutsideRange();
      } else {
         return JSTemporalDurationRecord.createWeeks(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds);
      }
   }

   public static long dtol(double d) {
      assert JSRuntime.doubleIsRepresentableAsLong(d);

      return (long)d;
   }

   public static int dtoi(double d) {
      if (d == 0.0) {
         return 0;
      } else {
         assert JSRuntime.doubleIsRepresentableAsInt(d);

         return (int)d;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static long dtol(double d, boolean failOnError) {
      if (failOnError && !JSRuntime.doubleIsRepresentableAsLong(d)) {
         throw Errors.createRangeError("value out of range");
      } else {
         return (long)d;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static int ltoi(long l) {
      if (!JSRuntime.longIsRepresentableAsInt(l)) {
         throw Errors.createRangeError("value out of range");
      } else {
         return (int)l;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static int bitoi(BigInteger bi) {
      double value = bi.doubleValue();

      assert Double.isFinite(value);

      assert JSRuntime.doubleIsRepresentableAsInt(value);

      return bi.intValue();
   }

   @CompilerDirectives.TruffleBoundary
   public static double bitod(BigInteger bi) {
      double value = bi.doubleValue();

      assert Double.isFinite(value);

      return value;
   }

   @CompilerDirectives.TruffleBoundary
   public static long bitol(BigInteger bi) {
      return bi.longValueExact();
   }

   @CompilerDirectives.TruffleBoundary
   public static long bigIntToLong(BigInt val) {
      return val.longValueExact();
   }

   @CompilerDirectives.TruffleBoundary
   private static int add(int a, int b, TemporalUtil.Overflow overflow) {
      try {
         return Math.addExact(a, b);
      } catch (ArithmeticException var4) {
         if (overflow == TemporalUtil.Overflow.REJECT) {
            throw TemporalErrors.createRangeErrorDateOutsideRange();
         } else {
            assert overflow == TemporalUtil.Overflow.CONSTRAIN;

            return Integer.MAX_VALUE;
         }
      }
   }

   public static JSTemporalDurationRecord createNegatedTemporalDuration(JSTemporalDurationRecord d) {
      return d.copyNegated();
   }

   public static TemporalUtil.Unit toUnit(TruffleString unit, TruffleString.EqualNode equalNode) {
      if (unit == null) {
         return TemporalUtil.Unit.EMPTY;
      } else if (equalNode.execute(unit, TemporalConstants.YEAR, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Unit.YEAR;
      } else if (equalNode.execute(unit, TemporalConstants.MONTH, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Unit.MONTH;
      } else if (equalNode.execute(unit, TemporalConstants.WEEK, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Unit.WEEK;
      } else if (equalNode.execute(unit, TemporalConstants.DAY, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Unit.DAY;
      } else if (equalNode.execute(unit, TemporalConstants.HOUR, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Unit.HOUR;
      } else if (equalNode.execute(unit, TemporalConstants.MINUTE, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Unit.MINUTE;
      } else if (equalNode.execute(unit, TemporalConstants.SECOND, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Unit.SECOND;
      } else if (equalNode.execute(unit, TemporalConstants.MILLISECOND, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Unit.MILLISECOND;
      } else if (equalNode.execute(unit, TemporalConstants.MICROSECOND, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Unit.MICROSECOND;
      } else if (equalNode.execute(unit, TemporalConstants.NANOSECOND, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Unit.NANOSECOND;
      } else if (equalNode.execute(unit, TemporalConstants.AUTO, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Unit.AUTO;
      } else {
         throw Errors.createTypeError("unexpected unit");
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TemporalUtil.RoundingMode toRoundingMode(TruffleString mode, TruffleString.EqualNode equalNode) {
      if (mode == null) {
         return TemporalUtil.RoundingMode.EMPTY;
      } else if (equalNode.execute(mode, TemporalConstants.FLOOR, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.RoundingMode.FLOOR;
      } else if (equalNode.execute(mode, TemporalConstants.CEIL, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.RoundingMode.CEIL;
      } else if (equalNode.execute(mode, TemporalConstants.HALF_EXPAND, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.RoundingMode.HALF_EXPAND;
      } else if (equalNode.execute(mode, TemporalConstants.TRUNC, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.RoundingMode.TRUNC;
      } else {
         throw Errors.createTypeError("unexpected roundingMode");
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TemporalUtil.Disambiguation toDisambiguation(TruffleString disambiguation, TruffleString.EqualNode equalNode) {
      if (equalNode.execute(disambiguation, TemporalConstants.EARLIER, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Disambiguation.EARLIER;
      } else if (equalNode.execute(disambiguation, TemporalConstants.LATER, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Disambiguation.LATER;
      } else if (equalNode.execute(disambiguation, TemporalConstants.COMPATIBLE, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Disambiguation.COMPATIBLE;
      } else if (equalNode.execute(disambiguation, TemporalConstants.REJECT, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.Disambiguation.REJECT;
      } else {
         throw Errors.createTypeError("unexpected disambiguation");
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TemporalUtil.OffsetOption toOffsetOption(TruffleString offsetOption, TruffleString.EqualNode equalNode) {
      if (equalNode.execute(offsetOption, TemporalConstants.USE, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.OffsetOption.USE;
      } else if (equalNode.execute(offsetOption, TemporalConstants.IGNORE, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.OffsetOption.IGNORE;
      } else if (equalNode.execute(offsetOption, TemporalConstants.PREFER, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.OffsetOption.PREFER;
      } else if (equalNode.execute(offsetOption, TemporalConstants.REJECT, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.OffsetOption.REJECT;
      } else {
         throw Errors.createTypeError("unexpected offsetOption");
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TemporalUtil.ShowCalendar toShowCalendar(TruffleString showCalendar, TruffleString.EqualNode equalNode) {
      if (equalNode.execute(showCalendar, TemporalConstants.AUTO, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.ShowCalendar.AUTO;
      } else if (equalNode.execute(showCalendar, TemporalConstants.NEVER, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.ShowCalendar.NEVER;
      } else if (equalNode.execute(showCalendar, TemporalConstants.ALWAYS, TruffleString.Encoding.UTF_16)) {
         return TemporalUtil.ShowCalendar.ALWAYS;
      } else {
         throw Errors.createTypeError("unexpected showCalendar");
      }
   }

   public static double roundTowardsZero(double d) {
      return ExactMath.truncate(d);
   }

   public static enum Disambiguation {
      EARLIER,
      LATER,
      COMPATIBLE,
      REJECT;
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

   public static enum OffsetOption {
      USE,
      IGNORE,
      PREFER,
      REJECT;
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

      public TemporalUtil.OptionType getLast() {
         switch (this) {
            case STRING:
            case NUMBER_AND_STRING:
               return STRING;
            case NUMBER:
               return NUMBER;
            case BOOLEAN:
               return BOOLEAN;
            default:
               throw Errors.shouldNotReachHere();
         }
      }
   }

   public static enum Overflow {
      CONSTRAIN,
      REJECT;
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

   public static enum ShowCalendar {
      AUTO,
      ALWAYS,
      NEVER;
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

   public static enum UnsignedRoundingMode {
      EMPTY,
      ZERO,
      INFINITY,
      HALF_INFINITY,
      HALF_ZERO,
      HALF_EVEN;
   }
}
