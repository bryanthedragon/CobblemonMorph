package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSException;
import java.util.List;

public final class TemporalErrors {
   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorOptions() {
      return Errors.createTypeError("Options is not undefined and not an object.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorOptionsUndefined() {
      return Errors.createTypeError("Options object expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorInvalidPlainDateTime() {
      return Errors.createRangeError("invalid PlainDateTime");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorInvalidRelativeToString() {
      return Errors.createRangeError("invalid relativeTo string");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorSmallestUnitOutOfRange() {
      return Errors.createRangeError("Smallest unit is out of range.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorFieldsNotAnObject() {
      return Errors.createTypeError("Given fields is not an object.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorRelativeToNotUndefined(TemporalUtil.Unit unit) {
      return Errors.createRangeError(String.format("RelativeTo object should be not undefined if unit is %s.", unit.toString()));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorRelativeToNotUndefined() {
      return Errors.createRangeError("RelativeTo object should be not undefined.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorDisallowedField(TruffleString property) {
      return Errors.createRangeError(String.format("Property %s is a disallowed field and not 0.", property));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorOptionsNotContained(List<?> values, Object value) {
      return Errors.createRangeError(String.format("Given options value: %s is not contained in values: %s", value, values));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorPropertyRequired(TruffleString property) {
      return Errors.createTypeError(String.format("Property %s is required.", property));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorTimeOutsideRange() {
      return Errors.createRangeError("Given Time outside the range.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorDateOutsideRange() {
      return Errors.createRangeError("Given Date outside the range.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorDateTimeOutsideRange() {
      return Errors.createRangeError("Given DateTime outside the range.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorYearMonthOutsideRange() {
      return Errors.createRangeError("Given YearMonth outside the range.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorMonthDayOutsideRange() {
      return Errors.createRangeError("Given MonthDay outside the range.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTemporalTimeExpected() {
      return Errors.createTypeError("Temporal.PlainTime expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTemporalDateExpected() {
      return Errors.createTypeError("Temporal.PlainDate expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTemporalDateTimeExpected() {
      return Errors.createTypeError("Temporal.PlainDateTime expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTemporalDurationExpected() {
      return Errors.createTypeError("Temporal.Duration expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTemporalCalendarExpected() {
      return Errors.createTypeError("Temporal.Calendar expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTemporalPlainYearMonthExpected() {
      return Errors.createTypeError("Temporal.PlainYearMonth expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTemporalPlainMonthDayExpected() {
      return Errors.createTypeError("Temporal.PlainMonthDay expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorTemporalISO8601Expected() {
      return Errors.createRangeError("iso8601 expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTemporalTimePropertyExpected() {
      return Errors.createTypeError("No Temporal.Time property found in given object.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorCalendarNotSupported() {
      return Errors.createRangeError("Given calendar id not supported.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTemporalYearNotPresent() {
      return Errors.createTypeError("Year not present.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTemporalDayNotPresent() {
      return Errors.createTypeError("Day not present.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorIdenticalCalendarExpected() {
      return Errors.createRangeError("identical calendar expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorIdenticalTimeZoneExpected() {
      return Errors.createRangeError("identical timeZone expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorTemporalMalformedDuration() {
      return Errors.createRangeError("malformed Duration");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorDurationOutsideRange() {
      throw Errors.createRangeError("Given duration outside range.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTemporalInstantExpected() {
      return Errors.createTypeError("Temporal.Instant expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorInvalidNanoseconds() {
      return Errors.createRangeError("invalid nanoseconds value");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorSmallestUnitExpected() {
      return Errors.createRangeError("smallestUnit expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorUnexpectedCalendar() {
      return Errors.createTypeError("Unexpected calendar property");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorUnexpectedTimeZone() {
      return Errors.createTypeError("Unexpected timeZone property");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTemporalTimeZoneExpected() {
      return Errors.createTypeError("Temporal.TimeZone expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorInvalidTimeZoneString() {
      return Errors.createRangeError("invalid timeZone string");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTemporalZonedDateTimeExpected() {
      return Errors.createTypeError("Temporal.ZonedDateTime expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorObjectExpected() {
      return Errors.createTypeError("Object expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorUnexpectedUTCDesignator() {
      return Errors.createRangeError("UTCDesignator Z not allowed");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorTimeZoneOffsetExpected() {
      return Errors.createRangeError("TimeZone offset expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorCalendarUnknown() {
      return Errors.createRangeError("unknown Calendar");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorNumberIsNaN() {
      return Errors.createRangeError("Number value cannot be NaN");
   }
}
