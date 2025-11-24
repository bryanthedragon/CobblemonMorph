
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.temporal.TemporalMoveRelativeDateNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNodeGen;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
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

public abstract class TemporalRoundDurationNode
extends JavaScriptBaseNode {
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

    public abstract JSTemporalDurationRecord execute(double var1, double var3, double var5, double var7, double var9, double var11, double var13, double var15, double var17, double var19, double var21, TemporalUtil.Unit var23, TemporalUtil.RoundingMode var24, JSDynamicObject var25);

    @Specialization
    protected JSTemporalDurationRecord add(double years, double months, double weeks, double d, double h, double min2, double sec, double milsec, double micsec, double nsec, double increment, TemporalUtil.Unit unit, TemporalUtil.RoundingMode roundingMode, JSDynamicObject relTo) {
        double days = d;
        double hours = h;
        double minutes = min2;
        double seconds = sec;
        double microseconds = micsec;
        double milliseconds = milsec;
        double nanoseconds = nsec;
        JSDynamicObject relativeTo = relTo;
        if ((unit == TemporalUtil.Unit.YEAR || unit == TemporalUtil.Unit.MONTH || unit == TemporalUtil.Unit.WEEK) && relativeTo == Undefined.instance) {
            this.errorBranch.enter();
            throw TemporalErrors.createRangeErrorRelativeToNotUndefined(unit);
        }
        JSDynamicObject zonedRelativeTo = Undefined.instance;
        JSDynamicObject calendar = Undefined.instance;
        BigDecimal fractionalSeconds = BigDecimal.ZERO;
        if (this.hasRelativeTo.profile(relativeTo != Undefined.instance)) {
            if (TemporalUtil.isTemporalZonedDateTime(relativeTo)) {
                zonedRelativeTo = relativeTo;
                relativeTo = this.toTemporalDateNode.executeDynamicObject(relativeTo, Undefined.instance);
            } else {
                TemporalUtil.requireTemporalDate(relativeTo, this.errorBranch);
            }
            calendar = ((JSTemporalPlainDateObject)relativeTo).getCalendar();
        }
        if (this.unitYMWD.profile(unit == TemporalUtil.Unit.YEAR || unit == TemporalUtil.Unit.MONTH || unit == TemporalUtil.Unit.WEEK || unit == TemporalUtil.Unit.DAY)) {
            nanoseconds = TemporalUtil.totalDurationNanoseconds(0.0, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, 0.0);
            JSDynamicObject intermediate = Undefined.instance;
            if (zonedRelativeTo != Undefined.instance) {
                intermediate = TemporalUtil.moveRelativeZonedDateTime(this.ctx, zonedRelativeTo, TemporalUtil.dtol(years), TemporalUtil.dtol(months), TemporalUtil.dtol(weeks), TemporalUtil.dtol(days));
            }
            JSTemporalNanosecondsDaysRecord result = TemporalUtil.nanosecondsToDays(this.ctx, this.namesNode, BigInt.valueOf(TemporalUtil.dtol(nanoseconds)), intermediate);
            days = TemporalRoundDurationNode.calculateDays(days, result);
            hours = 0.0;
            minutes = 0.0;
            seconds = 0.0;
            milliseconds = 0.0;
            microseconds = 0.0;
            nanoseconds = 0.0;
        } else {
            fractionalSeconds = TemporalUtil.roundDurationCalculateFractionalSeconds(seconds, microseconds, milliseconds, nanoseconds);
        }
        switch (this.unitValueProfile.profile(unit)) {
            case YEAR: {
                return this.getUnitYear(increment, roundingMode, years, months, weeks, days, hours, minutes, seconds, microseconds, milliseconds, nanoseconds, relativeTo, calendar);
            }
            case MONTH: {
                return this.getUnitMonth(increment, roundingMode, years, months, weeks, days, hours, minutes, seconds, microseconds, milliseconds, nanoseconds, relativeTo, calendar);
            }
            case WEEK: {
                return this.getUnitWeek(increment, roundingMode, years, months, weeks, days, hours, minutes, seconds, microseconds, milliseconds, nanoseconds, relativeTo, calendar);
            }
            case DAY: {
                return TemporalRoundDurationNode.getUnitDay(increment, roundingMode, years, months, weeks, days, hours, minutes, seconds, microseconds, milliseconds, nanoseconds);
            }
            case HOUR: {
                return TemporalRoundDurationNode.getUnitHour(increment, roundingMode, years, months, weeks, days, hours, minutes, fractionalSeconds);
            }
            case MINUTE: {
                return TemporalRoundDurationNode.getUnitMinute(increment, roundingMode, years, months, weeks, days, hours, minutes, fractionalSeconds);
            }
            case SECOND: {
                return TemporalRoundDurationNode.getUnitSecond(increment, roundingMode, years, months, weeks, days, hours, minutes, fractionalSeconds);
            }
            case MILLISECOND: {
                return TemporalRoundDurationNode.getUnitMillisecond(increment, roundingMode, years, months, weeks, days, hours, minutes, seconds, microseconds, milliseconds, nanoseconds);
            }
            case MICROSECOND: {
                return TemporalRoundDurationNode.getUnitMicrosecond(increment, roundingMode, years, months, weeks, days, hours, minutes, seconds, microseconds, milliseconds, nanoseconds);
            }
            case NANOSECOND: {
                return TemporalRoundDurationNode.getUnitNanosecond(increment, roundingMode, years, months, weeks, days, hours, minutes, seconds, microseconds, milliseconds, nanoseconds);
            }
        }
        CompilerDirectives.transferToInterpreter();
        throw Errors.shouldNotReachHere();
    }

    private static JSTemporalDurationRecord getUnitNanosecond(double increment, TemporalUtil.RoundingMode roundingMode, double years, double months, double weeks, double days, double hours, double minutes, double seconds, double microseconds, double milliseconds, double nanosecondsP) {
        double nanoseconds;
        double remainder = nanoseconds = nanosecondsP;
        nanoseconds = TemporalUtil.roundNumberToIncrement(nanoseconds, increment, roundingMode);
        return JSTemporalDurationRecord.createWeeksRemainder(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, remainder -= nanoseconds);
    }

    private static JSTemporalDurationRecord getUnitMicrosecond(double increment, TemporalUtil.RoundingMode roundingMode, double years, double months, double weeks, double days, double hours, double minutes, double seconds, double microsecondsP, double milliseconds, double nanoseconds) {
        double microseconds = microsecondsP;
        double fractionalMicroseconds = nanoseconds * 0.001 + microseconds;
        microseconds = TemporalUtil.roundNumberToIncrement(fractionalMicroseconds, increment, roundingMode);
        double remainder = fractionalMicroseconds - microseconds;
        return JSTemporalDurationRecord.createWeeksRemainder(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, 0.0, remainder);
    }

    private static JSTemporalDurationRecord getUnitMillisecond(double increment, TemporalUtil.RoundingMode roundingMode, double years, double months, double weeks, double days, double hours, double minutes, double seconds, double microseconds, double millisecondsP, double nanoseconds) {
        double milliseconds = millisecondsP;
        double fractionalMilliseconds = nanoseconds * 1.0E-6 + microseconds * 0.001 + milliseconds;
        milliseconds = TemporalUtil.roundNumberToIncrement(fractionalMilliseconds, increment, roundingMode);
        double remainder = fractionalMilliseconds - milliseconds;
        return JSTemporalDurationRecord.createWeeksRemainder(years, months, weeks, days, hours, minutes, seconds, milliseconds, 0.0, 0.0, remainder);
    }

    private static JSTemporalDurationRecord getUnitMinute(double increment, TemporalUtil.RoundingMode roundingMode, double years, double months, double weeks, double days, double hours, double minutesP, BigDecimal fractionalSeconds) {
        double minutes = minutesP;
        double secondsPart = TemporalUtil.roundDurationFractionalDecondsDiv60(fractionalSeconds);
        double fractionalMinutes = secondsPart + minutes;
        minutes = TemporalUtil.roundNumberToIncrement(fractionalMinutes, increment, roundingMode);
        double remainder = fractionalMinutes - minutes;
        return JSTemporalDurationRecord.createWeeksRemainder(years, months, weeks, days, hours, minutes, 0.0, 0.0, 0.0, 0.0, remainder);
    }

    private static JSTemporalDurationRecord getUnitHour(double increment, TemporalUtil.RoundingMode roundingMode, double years, double months, double weeks, double days, double hoursP, double minutes, BigDecimal fractionalSeconds) {
        double hours = hoursP;
        double secondsPart = TemporalUtil.roundDurationFractionalDecondsDiv60(fractionalSeconds);
        double fractionalHours = (secondsPart + minutes) / 60.0 + hours;
        hours = TemporalUtil.roundNumberToIncrement(fractionalHours, increment, roundingMode);
        double remainder = fractionalHours - hours;
        return JSTemporalDurationRecord.createWeeksRemainder(years, months, weeks, days, hours, 0.0, 0.0, 0.0, 0.0, 0.0, remainder);
    }

    private static JSTemporalDurationRecord getUnitDay(double increment, TemporalUtil.RoundingMode roundingMode, double years, double months, double weeks, double daysP, double hours, double minutes, double seconds, double microseconds, double milliseconds, double nanoseconds) {
        double fractionalDays = daysP;
        double days = TemporalUtil.roundNumberToIncrement(daysP, increment, roundingMode);
        double remainder = fractionalDays - days;
        return JSTemporalDurationRecord.createWeeksRemainder(years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, remainder);
    }

    private JSTemporalDurationRecord getUnitWeek(double increment, TemporalUtil.RoundingMode roundingMode, double years, double months, double weeksP, double daysP, double hours, double minutes, double seconds, double microseconds, double milliseconds, double nanoseconds, JSDynamicObject relativeToP, JSDynamicObject calendar) {
        double weeks = weeksP;
        double days = daysP;
        JSDynamicObject relativeTo = relativeToP;
        double sign = days >= 0.0 ? 1.0 : -1.0;
        JSTemporalDurationObject oneWeek = JSTemporalDuration.createTemporalDuration(this.ctx, 0.0, 0.0, sign, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        JSTemporalRelativeDateRecord moveResult = this.moveRelativeDate(calendar, relativeTo, oneWeek);
        relativeTo = moveResult.getRelativeTo();
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
        return JSTemporalDurationRecord.createWeeksRemainder(years, months, weeks, 0.0, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, remainder);
    }

    private JSTemporalDurationRecord getUnitMonth(double increment, TemporalUtil.RoundingMode roundingMode, double years, double monthsP, double weeks, double daysP, double hours, double minutes, double seconds, double microseconds, double milliseconds, double nanoseconds, JSDynamicObject relativeToP, JSDynamicObject calendar) {
        double months = monthsP;
        double days = daysP;
        JSDynamicObject relativeTo = relativeToP;
        JSTemporalDurationObject yearsMonths = JSTemporalDuration.createTemporalDuration(this.ctx, years, months, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        Object dateAdd = JSObject.getMethod(calendar, TemporalConstants.DATE_ADD);
        JSTemporalPlainDateObject yearsMonthsLater = TemporalUtil.calendarDateAdd(calendar, relativeTo, yearsMonths, Undefined.instance, dateAdd);
        JSTemporalDurationObject yearsMonthsWeeks = JSTemporalDuration.createTemporalDuration(this.ctx, years, months, weeks, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        JSTemporalPlainDateObject yearsMonthsWeeksLater = TemporalUtil.calendarDateAdd(calendar, relativeTo, yearsMonthsWeeks, Undefined.instance, dateAdd);
        double weeksInDays = TemporalUtil.daysUntil(yearsMonthsLater, yearsMonthsWeeksLater);
        relativeTo = yearsMonthsLater;
        double sign = (days += weeksInDays) >= 0.0 ? 1.0 : -1.0;
        JSTemporalDurationObject oneMonth = JSTemporalDuration.createTemporalDuration(this.ctx, 0.0, sign, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        JSTemporalRelativeDateRecord moveResult = this.moveRelativeDate(calendar, relativeTo, oneMonth);
        relativeTo = moveResult.getRelativeTo();
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
    private static JSTemporalDurationRecord getUnitSecond(double increment, TemporalUtil.RoundingMode roundingMode, double years, double months, double weeks, double days, double hours, double minutes, BigDecimal fractionalSeconds) {
        double seconds = TemporalUtil.bitod(TemporalUtil.roundNumberToIncrement(fractionalSeconds, new BigDecimal(increment), roundingMode));
        double remainder = TemporalUtil.roundDurationFractionalSecondsSubtract(seconds, fractionalSeconds);
        return JSTemporalDurationRecord.createWeeksRemainder(years, months, weeks, days, hours, minutes, seconds, 0.0, 0.0, 0.0, remainder);
    }

    private JSTemporalDurationRecord getUnitYear(double increment, TemporalUtil.RoundingMode roundingMode, double yearsP, double months, double weeks, double daysP, double hours, double minutes, double seconds, double microseconds, double milliseconds, double nanoseconds, JSDynamicObject relativeToP, JSDynamicObject calendar) {
        double years = yearsP;
        double days = daysP;
        JSDynamicObject relativeTo = relativeToP;
        JSTemporalDurationObject yearsDuration = JSTemporalDuration.createTemporalDuration(this.ctx, years, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
        Object dateAdd = JSObject.getMethod(calendar, TemporalConstants.DATE_ADD);
        JSTemporalPlainDateObject yearsLater = TemporalUtil.calendarDateAdd(calendar, relativeTo, yearsDuration, Undefined.instance, dateAdd);
        JSTemporalDurationObject yearsMonthsWeeks = JSTemporalDuration.createTemporalDuration(this.ctx, years, months, weeks, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
        JSTemporalPlainDateObject yearsMonthsWeeksLater = TemporalUtil.calendarDateAdd(calendar, relativeTo, yearsMonthsWeeks, Undefined.instance, dateAdd);
        double monthsWeeksInDays = TemporalUtil.daysUntil(yearsLater, yearsMonthsWeeksLater);
        relativeTo = yearsLater;
        JSTemporalDurationObject daysDuration = JSTemporalDuration.createTemporalDuration(this.ctx, 0.0, 0.0, 0.0, days += monthsWeeksInDays, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
        JSTemporalPlainDateObject daysLater = TemporalUtil.calendarDateAdd(calendar, relativeTo, daysDuration, Undefined.instance, dateAdd);
        JSObject untilOptions = JSOrdinary.createWithNullPrototype(this.ctx);
        TemporalUtil.createDataPropertyOrThrow(this.ctx, untilOptions, TemporalConstants.LARGEST_UNIT, TemporalConstants.YEAR);
        JSTemporalDurationObject timePassed = TemporalUtil.calendarDateUntil(calendar, relativeTo, daysLater, untilOptions);
        double yearsPassed = TemporalUtil.dtol(timePassed.getYears());
        years += yearsPassed;
        JSDynamicObject oldRelativeTo = relativeTo;
        yearsDuration = JSTemporalDuration.createTemporalDuration(this.ctx, yearsPassed, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
        relativeTo = TemporalUtil.calendarDateAdd(calendar, relativeTo, yearsDuration, Undefined.instance, dateAdd);
        double daysPassed = TemporalUtil.daysUntil(oldRelativeTo, relativeTo);
        double sign = (days -= daysPassed) >= 0.0 ? 1.0 : -1.0;
        JSTemporalDurationObject oneYear = JSTemporalDuration.createTemporalDuration(this.ctx, sign, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
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

