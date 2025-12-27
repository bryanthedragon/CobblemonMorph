package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalNanosecondsDaysRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalRelativeDateRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.math.BigDecimal;

public abstract class TemporalRoundDurationNode extends JavaScriptBaseNode {
   protected final JSContext ctx;
   private final BranchProfile errorBranch = BranchProfile.create();
   private final ConditionProfile hasRelativeTo = ConditionProfile.createBinaryProfile();
   private final ConditionProfile unitYMWD = ConditionProfile.createBinaryProfile();
   private final ValueProfile unitValueProfile = ValueProfile.createIdentityProfile();
   @Node.Child
   private TemporalMoveRelativeDateNode moveRelativeDateNode;
   @Node.Child
   EnumerableOwnPropertyNamesNode namesNode;
   @Node.Child
   ToTemporalDateNode toTemporalDateNode;

   protected TemporalRoundDurationNode(JSContext ctx) {
      this.ctx = ctx;
      this.namesNode = EnumerableOwnPropertyNamesNode.createKeys(ctx);
      this.toTemporalDateNode = ToTemporalDateNode.create(ctx);
   }

   public static TemporalRoundDurationNode create(JSContext ctx) {
      return TemporalRoundDurationNodeGen.create(ctx);
   }

   public abstract JSTemporalDurationRecord execute(
      double y,
      double m,
      double w,
      double d,
      double h,
      double min,
      double sec,
      double milsec,
      double micsec,
      double nsec,
      double increment,
      TemporalUtil.Unit unit,
      TemporalUtil.RoundingMode roundingMode,
      JSDynamicObject relTo
   );

   @Specialization
   protected JSTemporalDurationRecord add(
      double years,
      double months,
      double weeks,
      double d,
      double h,
      double min,
      double sec,
      double milsec,
      double micsec,
      double nsec,
      double increment,
      TemporalUtil.Unit unit,
      TemporalUtil.RoundingMode roundingMode,
      JSDynamicObject relTo
   ) {
      double days = d;
      double hours = h;
      double minutes = min;
      double seconds = sec;
      double microseconds = micsec;
      double milliseconds = milsec;
      double nanoseconds = nsec;
      JSDynamicObject relativeTo = relTo;
      if ((unit == TemporalUtil.Unit.YEAR || unit == TemporalUtil.Unit.MONTH || unit == TemporalUtil.Unit.WEEK) && relTo == Undefined.instance) {
         this.errorBranch.enter();
         throw TemporalErrors.createRangeErrorRelativeToNotUndefined(unit);
      } else {
         JSDynamicObject zonedRelativeTo = Undefined.instance;
         JSDynamicObject calendar = Undefined.instance;
         BigDecimal fractionalSeconds = BigDecimal.ZERO;
         if (this.hasRelativeTo.profile(relTo != Undefined.instance)) {
            if (TemporalUtil.isTemporalZonedDateTime(relTo)) {
               zonedRelativeTo = relTo;
               relativeTo = this.toTemporalDateNode.executeDynamicObject(relTo, Undefined.instance);
            } else {
               TemporalUtil.requireTemporalDate(relTo, this.errorBranch);
            }

            calendar = ((JSTemporalPlainDateObject)relativeTo).getCalendar();
         }

         if (this.unitYMWD
            .profile(unit == TemporalUtil.Unit.YEAR || unit == TemporalUtil.Unit.MONTH || unit == TemporalUtil.Unit.WEEK || unit == TemporalUtil.Unit.DAY)) {
            nanoseconds = TemporalUtil.totalDurationNanoseconds(0.0, h, min, sec, milsec, micsec, nsec, 0.0);
            JSDynamicObject intermediate = Undefined.instance;
            if (zonedRelativeTo != Undefined.instance) {
               intermediate = TemporalUtil.moveRelativeZonedDateTime(
                  this.ctx, zonedRelativeTo, TemporalUtil.dtol(years), TemporalUtil.dtol(months), TemporalUtil.dtol(weeks), TemporalUtil.dtol(d)
               );
            }

            JSTemporalNanosecondsDaysRecord result = TemporalUtil.nanosecondsToDays(
               this.ctx, this.namesNode, BigInt.valueOf(TemporalUtil.dtol(nanoseconds)), intermediate
            );
            days = calculateDays(d, result);
            hours = 0.0;
            minutes = 0.0;
            seconds = 0.0;
            milliseconds = 0.0;
            microseconds = 0.0;
            nanoseconds = 0.0;
         } else {
            fractionalSeconds = TemporalUtil.roundDurationCalculateFractionalSeconds(sec, micsec, milsec, nsec);
         }

         switch ((TemporalUtil.Unit)this.unitValueProfile.profile(unit)) {
            case YEAR:
               return this.getUnitYear(
                  increment, roundingMode, years, months, weeks, days, hours, minutes, seconds, microseconds, milliseconds, nanoseconds, relativeTo, calendar
               );
            case MONTH:
               return this.getUnitMonth(
                  increment, roundingMode, years, months, weeks, days, hours, minutes, seconds, microseconds, milliseconds, nanoseconds, relativeTo, calendar
               );
            case WEEK:
               return this.getUnitWeek(
                  increment, roundingMode, years, months, weeks, days, hours, minutes, seconds, microseconds, milliseconds, nanoseconds, relativeTo, calendar
               );
            case DAY:
               return getUnitDay(increment, roundingMode, years, months, weeks, days, hours, minutes, seconds, microseconds, milliseconds, nanoseconds);
            case HOUR:
               return getUnitHour(increment, roundingMode, years, months, weeks, days, hours, minutes, fractionalSeconds);
            case MINUTE:
               return getUnitMinute(increment, roundingMode, years, months, weeks, days, hours, minutes, fractionalSeconds);
            case SECOND:
               return getUnitSecond(increment, roundingMode, years, months, weeks, days, hours, minutes, fractionalSeconds);
            case MILLISECOND:
               return getUnitMillisecond(increment, roundingMode, years, months, weeks, days, hours, minutes, seconds, microseconds, milliseconds, nanoseconds);
            case MICROSECOND:
               return getUnitMicrosecond(increment, roundingMode, years, months, weeks, days, hours, minutes, seconds, microseconds, milliseconds, nanoseconds);
            case NANOSECOND:
               return getUnitNanosecond(increment, roundingMode, years, months, weeks, days, hours, minutes, seconds, microseconds, milliseconds, nanoseconds);
            default:
               CompilerDirectives.transferToInterpreter();
               throw Errors.shouldNotReachHere();
         }
      }
   }

   private static JSTemporalDurationRecord getUnitNanosecond(
      double increment,
      TemporalUtil.RoundingMode roundingMode,
      final double years,
      final double months,
      final double weeks,
      final double days,
      final double hours,
      final double minutes,
      final double seconds,
      final double microseconds,
      final double milliseconds,
      final double nanosecondsP
   ) {
      double nanoseconds = TemporalUtil.roundNumberToIncrement(nanosecondsP, increment, roundingMode);
      double remainder = nanosecondsP - nanoseconds;
      return JSTemporalDurationRecord.createWeeksRemainder(
         years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, remainder
      );
   }

   private static JSTemporalDurationRecord getUnitMicrosecond(
      double increment,
      TemporalUtil.RoundingMode roundingMode,
      final double years,
      final double months,
      final double weeks,
      final double days,
      final double hours,
      final double minutes,
      final double seconds,
      final double microsecondsP,
      final double milliseconds,
      final double nanoseconds
   ) {
      double fractionalMicroseconds = nanoseconds * 0.001 + microsecondsP;
      double microseconds = TemporalUtil.roundNumberToIncrement(fractionalMicroseconds, increment, roundingMode);
      double remainder = fractionalMicroseconds - microseconds;
      return JSTemporalDurationRecord.createWeeksRemainder(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, 0.0, remainder);
   }

   private static JSTemporalDurationRecord getUnitMillisecond(
      double increment,
      TemporalUtil.RoundingMode roundingMode,
      final double years,
      final double months,
      final double weeks,
      final double days,
      final double hours,
      final double minutes,
      final double seconds,
      final double microseconds,
      final double millisecondsP,
      final double nanoseconds
   ) {
      double fractionalMilliseconds = nanoseconds * 1.0E-6 + microseconds * 0.001 + millisecondsP;
      double milliseconds = TemporalUtil.roundNumberToIncrement(fractionalMilliseconds, increment, roundingMode);
      double remainder = fractionalMilliseconds - milliseconds;
      return JSTemporalDurationRecord.createWeeksRemainder(years, months, weeks, days, hours, minutes, seconds, milliseconds, 0.0, 0.0, remainder);
   }

   private static JSTemporalDurationRecord getUnitMinute(
      double increment,
      TemporalUtil.RoundingMode roundingMode,
      final double years,
      final double months,
      final double weeks,
      final double days,
      final double hours,
      final double minutesP,
      BigDecimal fractionalSeconds
   ) {
      double secondsPart = TemporalUtil.roundDurationFractionalDecondsDiv60(fractionalSeconds);
      double fractionalMinutes = secondsPart + minutesP;
      double minutes = TemporalUtil.roundNumberToIncrement(fractionalMinutes, increment, roundingMode);
      double remainder = fractionalMinutes - minutes;
      return JSTemporalDurationRecord.createWeeksRemainder(years, months, weeks, days, hours, minutes, 0.0, 0.0, 0.0, 0.0, remainder);
   }

   private static JSTemporalDurationRecord getUnitHour(
      double increment,
      TemporalUtil.RoundingMode roundingMode,
      final double years,
      final double months,
      final double weeks,
      final double days,
      final double hoursP,
      final double minutes,
      BigDecimal fractionalSeconds
   ) {
      double secondsPart = TemporalUtil.roundDurationFractionalDecondsDiv60(fractionalSeconds);
      double fractionalHours = (secondsPart + minutes) / 60.0 + hoursP;
      double hours = TemporalUtil.roundNumberToIncrement(fractionalHours, increment, roundingMode);
      double remainder = fractionalHours - hours;
      return JSTemporalDurationRecord.createWeeksRemainder(years, months, weeks, days, hours, 0.0, 0.0, 0.0, 0.0, 0.0, remainder);
   }

   private static JSTemporalDurationRecord getUnitDay(
      double increment,
      TemporalUtil.RoundingMode roundingMode,
      final double years,
      final double months,
      final double weeks,
      final double daysP,
      final double hours,
      final double minutes,
      final double seconds,
      final double microseconds,
      final double milliseconds,
      final double nanoseconds
   ) {
      double days = TemporalUtil.roundNumberToIncrement(daysP, increment, roundingMode);
      double remainder = daysP - days;
      return JSTemporalDurationRecord.createWeeksRemainder(
         years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, remainder
      );
   }

   private JSTemporalDurationRecord getUnitWeek(
      double increment,
      TemporalUtil.RoundingMode roundingMode,
      final double years,
      final double months,
      final double weeksP,
      final double daysP,
      final double hours,
      final double minutes,
      final double seconds,
      final double microseconds,
      final double milliseconds,
      final double nanoseconds,
      JSDynamicObject relativeToP,
      JSDynamicObject calendar
   ) {
      double weeks = weeksP;
      double days = daysP;
      double sign = daysP >= 0.0 ? 1.0 : -1.0;
      JSDynamicObject oneWeek = JSTemporalDuration.createTemporalDuration(this.ctx, 0.0, 0.0, sign, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      JSTemporalRelativeDateRecord moveResult = this.moveRelativeDate(calendar, relativeToP, oneWeek);
      JSDynamicObject relativeTo = moveResult.getRelativeTo();
      double oneWeekDays = moveResult.getDays();

      while (Math.abs(days) >= Math.abs(oneWeekDays)) {
         weeks -= sign;
         days -= oneWeekDays;
         moveResult = this.moveRelativeDate(calendar, relativeTo, oneWeek);
         relativeTo = moveResult.getRelativeTo();
         oneWeekDays = moveResult.getDays();
      }

      double fractionalWeeks = weeks + days / Math.abs(oneWeekDays);
      weeks = TemporalUtil.roundNumberToIncrement(fractionalWeeks, increment, roundingMode);
      double remainder = fractionalWeeks - weeks;
      return JSTemporalDurationRecord.createWeeksRemainder(
         years, months, weeks, 0.0, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, remainder
      );
   }

   private JSTemporalDurationRecord getUnitMonth(
      double increment,
      TemporalUtil.RoundingMode roundingMode,
      final double years,
      final double monthsP,
      final double weeks,
      final double daysP,
      final double hours,
      final double minutes,
      final double seconds,
      final double microseconds,
      final double milliseconds,
      final double nanoseconds,
      JSDynamicObject relativeToP,
      JSDynamicObject calendar
   ) {
      double months = monthsP;
      JSDynamicObject yearsMonths = JSTemporalDuration.createTemporalDuration(this.ctx, years, monthsP, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      Object dateAdd = JSObject.getMethod(calendar, TemporalConstants.DATE_ADD);
      JSTemporalPlainDateObject yearsMonthsLater = TemporalUtil.calendarDateAdd(calendar, relativeToP, yearsMonths, Undefined.instance, dateAdd);
      JSDynamicObject yearsMonthsWeeks = JSTemporalDuration.createTemporalDuration(this.ctx, years, monthsP, weeks, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      JSTemporalPlainDateObject yearsMonthsWeeksLater = TemporalUtil.calendarDateAdd(calendar, relativeToP, yearsMonthsWeeks, Undefined.instance, dateAdd);
      double weeksInDays = TemporalUtil.daysUntil(yearsMonthsLater, yearsMonthsWeeksLater);
      double days = daysP + weeksInDays;
      double sign = days >= 0.0 ? 1.0 : -1.0;
      JSDynamicObject oneMonth = JSTemporalDuration.createTemporalDuration(this.ctx, 0.0, sign, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
      JSTemporalRelativeDateRecord moveResult = this.moveRelativeDate(calendar, yearsMonthsLater, oneMonth);
      JSDynamicObject relativeTo = moveResult.getRelativeTo();
      double oneMonthDays = moveResult.getDays();

      while (Math.abs(days) >= Math.abs(oneMonthDays)) {
         months += sign;
         days -= oneMonthDays;
         moveResult = this.moveRelativeDate(calendar, relativeTo, oneMonth);
         relativeTo = moveResult.getRelativeTo();
         oneMonthDays = moveResult.getDays();
      }

      double fractionalMonths = months + days / Math.abs(oneMonthDays);
      months = TemporalUtil.roundNumberToIncrement(fractionalMonths, increment, roundingMode);
      double remainder = fractionalMonths - months;
      return JSTemporalDurationRecord.createWeeksRemainder(years, months, 0.0, 0.0, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, remainder);
   }

   @CompilerDirectives.TruffleBoundary
   private static JSTemporalDurationRecord getUnitSecond(
      double increment,
      TemporalUtil.RoundingMode roundingMode,
      double years,
      double months,
      double weeks,
      double days,
      double hours,
      double minutes,
      BigDecimal fractionalSeconds
   ) {
      double seconds = TemporalUtil.bitod(TemporalUtil.roundNumberToIncrement(fractionalSeconds, new BigDecimal(increment), roundingMode));
      double remainder = TemporalUtil.roundDurationFractionalSecondsSubtract(seconds, fractionalSeconds);
      return JSTemporalDurationRecord.createWeeksRemainder(years, months, weeks, days, hours, minutes, seconds, 0.0, 0.0, 0.0, remainder);
   }

   private JSTemporalDurationRecord getUnitYear(
      final double increment,
      TemporalUtil.RoundingMode roundingMode,
      final double yearsP,
      final double months,
      final double weeks,
      final double daysP,
      final double hours,
      final double minutes,
      final double seconds,
      final double microseconds,
      final double milliseconds,
      final double nanoseconds,
      JSDynamicObject relativeToP,
      JSDynamicObject calendar
   ) {
      JSDynamicObject yearsDuration = JSTemporalDuration.createTemporalDuration(this.ctx, yearsP, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
      Object dateAdd = JSObject.getMethod(calendar, TemporalConstants.DATE_ADD);
      JSDynamicObject yearsLater = TemporalUtil.calendarDateAdd(calendar, relativeToP, yearsDuration, Undefined.instance, dateAdd);
      JSDynamicObject yearsMonthsWeeks = JSTemporalDuration.createTemporalDuration(
         this.ctx, yearsP, months, weeks, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch
      );
      JSDynamicObject yearsMonthsWeeksLater = TemporalUtil.calendarDateAdd(calendar, relativeToP, yearsMonthsWeeks, Undefined.instance, dateAdd);
      double monthsWeeksInDays = TemporalUtil.daysUntil(yearsLater, yearsMonthsWeeksLater);
      double days = daysP + monthsWeeksInDays;
      JSDynamicObject daysDuration = JSTemporalDuration.createTemporalDuration(this.ctx, 0.0, 0.0, 0.0, days, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
      JSDynamicObject daysLater = TemporalUtil.calendarDateAdd(calendar, yearsLater, daysDuration, Undefined.instance, dateAdd);
      JSDynamicObject untilOptions = JSOrdinary.createWithNullPrototype(this.ctx);
      TemporalUtil.createDataPropertyOrThrow(this.ctx, untilOptions, TemporalConstants.LARGEST_UNIT, TemporalConstants.YEAR);
      JSTemporalDurationObject timePassed = TemporalUtil.calendarDateUntil(calendar, yearsLater, daysLater, untilOptions);
      double yearsPassed = TemporalUtil.dtol(timePassed.getYears());
      double years = yearsP + yearsPassed;
      yearsDuration = JSTemporalDuration.createTemporalDuration(this.ctx, yearsPassed, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
      JSDynamicObject relativeTo = TemporalUtil.calendarDateAdd(calendar, yearsLater, yearsDuration, Undefined.instance, dateAdd);
      double daysPassed = TemporalUtil.daysUntil(yearsLater, relativeTo);
      days -= daysPassed;
      double sign = days >= 0.0 ? 1.0 : -1.0;
      JSDynamicObject oneYear = JSTemporalDuration.createTemporalDuration(this.ctx, sign, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
      JSTemporalRelativeDateRecord moveResult = this.moveRelativeDate(calendar, relativeTo, oneYear);
      double oneYearDays = moveResult.getDays();
      double fractionalYears = years + days / Math.abs(oneYearDays);
      years = TemporalUtil.roundNumberToIncrement(fractionalYears, increment, roundingMode);
      double remainder = fractionalYears - years;
      return JSTemporalDurationRecord.createWeeksRemainder(years, 0.0, 0.0, 0.0, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, remainder);
   }

   @CompilerDirectives.TruffleBoundary
   private static double calculateDays(double days, JSTemporalNanosecondsDaysRecord result) {
      return days + TemporalUtil.bitod(result.getDays().add(result.getNanoseconds().divide(result.getDayLength().abs())));
   }

   private JSTemporalRelativeDateRecord moveRelativeDate(JSDynamicObject calendar, JSDynamicObject relativeTo, JSDynamicObject oneMonth) {
      if (this.moveRelativeDateNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.moveRelativeDateNode = this.insert(TemporalMoveRelativeDateNode.create(this.ctx));
      }

      return this.moveRelativeDateNode.execute(calendar, relativeTo, oneMonth);
   }
}
