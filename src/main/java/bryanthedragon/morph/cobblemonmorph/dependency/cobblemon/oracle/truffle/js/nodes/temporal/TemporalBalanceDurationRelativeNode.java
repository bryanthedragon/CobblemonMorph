
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.temporal.TemporalBalanceDurationRelativeNodeGen;
import com.oracle.truffle.js.nodes.temporal.TemporalMoveRelativeDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalRelativeDateRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public abstract class TemporalBalanceDurationRelativeNode
extends JavaScriptBaseNode {
    protected final JSContext ctx;
    @Node.Child
    private JSFunctionCallNode callDateAddNode;
    @Node.Child
    private GetMethodNode getMethodDateAddNode;
    @Node.Child
    private JSFunctionCallNode callDateUntilNode;
    @Node.Child
    private GetMethodNode getMethodDateUntilNode;
    @Node.Child
    private TemporalMoveRelativeDateNode moveRelativeDateNode;
    private final BranchProfile errorBranch = BranchProfile.create();

    protected TemporalBalanceDurationRelativeNode(JSContext ctx) {
        this.ctx = ctx;
    }

    public static TemporalBalanceDurationRelativeNode create(JSContext ctx) {
        return TemporalBalanceDurationRelativeNodeGen.create(ctx);
    }

    public abstract JSTemporalDurationRecord execute(double var1, double var3, double var5, double var7, TemporalUtil.Unit var9, JSDynamicObject var10);

    @Specialization
    protected JSTemporalDurationRecord balanceDurationRelative(double y, double m, double w, double d, TemporalUtil.Unit largestUnit, JSDynamicObject relTo, @Cached(value="createBinaryProfile()") ConditionProfile unitIsYear, @Cached(value="createBinaryProfile()") ConditionProfile unitIsMonth, @Cached(value="createBinaryProfile()") ConditionProfile unitIsDay, @Cached(value="create(ctx)") ToTemporalDateNode toTemporalDateNode) {
        long years = TemporalUtil.dtol(y);
        long months = TemporalUtil.dtol(m);
        long weeks = TemporalUtil.dtol(w);
        long days = TemporalUtil.dtol(d);
        if (unitIsDay.profile(largestUnit != TemporalUtil.Unit.YEAR && largestUnit != TemporalUtil.Unit.MONTH && largestUnit != TemporalUtil.Unit.WEEK || years == 0L && months == 0L && weeks == 0L && days == 0L)) {
            return JSTemporalDurationRecord.createWeeks(years, months, weeks, days, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        }
        if (relTo == Undefined.instance) {
            this.errorBranch.enter();
            throw TemporalErrors.createRangeErrorRelativeToNotUndefined();
        }
        long sign = TemporalUtil.durationSign(years, months, weeks, days, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        assert (sign != 0L);
        JSTemporalDurationObject oneYear = JSTemporalDuration.createTemporalDuration(this.ctx, sign, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        JSTemporalDurationObject oneMonth = JSTemporalDuration.createTemporalDuration(this.ctx, 0.0, sign, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        JSTemporalDurationObject oneWeek = JSTemporalDuration.createTemporalDuration(this.ctx, 0.0, 0.0, sign, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        JSTemporalPlainDateObject relativeTo = toTemporalDateNode.executeDynamicObject(relTo, Undefined.instance);
        JSDynamicObject calendar = relativeTo.getCalendar();
        if (unitIsYear.profile(largestUnit == TemporalUtil.Unit.YEAR)) {
            return this.getUnitYear(years, months, weeks, days, sign, oneYear, oneMonth, relativeTo, calendar);
        }
        if (unitIsMonth.profile(largestUnit == TemporalUtil.Unit.MONTH)) {
            return this.getUnitMonth(years, months, weeks, days, sign, oneMonth, relativeTo, calendar);
        }
        return this.getUnitWeek(largestUnit, years, months, weeks, days, sign, oneWeek, relativeTo, calendar);
    }

    private JSTemporalDurationRecord getUnitYear(long yearsP, long monthsP, long weeks, long daysP, long sign, JSDynamicObject oneYear, JSDynamicObject oneMonth, JSDynamicObject relativeToP, JSDynamicObject calendar) {
        long years = yearsP;
        long months = monthsP;
        long days = daysP;
        JSDynamicObject relativeTo = relativeToP;
        JSTemporalRelativeDateRecord moveResult = this.moveRelativeDate(calendar, relativeTo, oneYear);
        relativeTo = moveResult.getRelativeTo();
        long oneYearDays = moveResult.getDays();
        while (Math.abs(days) >= Math.abs(oneYearDays)) {
            days -= oneYearDays;
            years += sign;
            moveResult = this.moveRelativeDate(calendar, relativeTo, oneYear);
            relativeTo = moveResult.getRelativeTo();
            oneYearDays = moveResult.getDays();
        }
        moveResult = this.moveRelativeDate(calendar, relativeTo, oneMonth);
        relativeTo = moveResult.getRelativeTo();
        long oneMonthDays = moveResult.getDays();
        while (Math.abs(days) >= Math.abs(oneMonthDays)) {
            days -= oneMonthDays;
            months += sign;
            moveResult = this.moveRelativeDate(calendar, relativeTo, oneMonth);
            relativeTo = moveResult.getRelativeTo();
            oneMonthDays = moveResult.getDays();
        }
        Object dateAdd = this.getDateAdd(calendar);
        JSTemporalPlainDateObject newRelativeTo = this.calendarDateAdd(calendar, relativeTo, oneYear, Undefined.instance, dateAdd);
        Object dateUntil = this.getDateUntil(calendar);
        JSObject untilOptions = JSOrdinary.createWithNullPrototype(this.ctx);
        JSObjectUtil.putDataProperty(this.ctx, untilOptions, (Object)TemporalConstants.LARGEST_UNIT, TemporalConstants.MONTH);
        JSTemporalDurationObject untilResult = this.calendarDateUntil(calendar, relativeTo, newRelativeTo, untilOptions, dateUntil);
        long oneYearMonths = TemporalUtil.dtol(untilResult.getMonths());
        while (Math.abs(months) >= Math.abs(oneYearMonths)) {
            months -= oneYearMonths;
            years += sign;
            relativeTo = newRelativeTo;
            newRelativeTo = this.calendarDateAdd(calendar, relativeTo, oneYear, Undefined.instance, dateAdd);
            untilOptions = JSOrdinary.createWithNullPrototype(this.ctx);
            JSObjectUtil.putDataProperty(this.ctx, untilOptions, (Object)TemporalConstants.LARGEST_UNIT, TemporalConstants.MONTH);
            untilResult = this.calendarDateUntil(calendar, relativeTo, newRelativeTo, untilOptions, dateUntil);
            oneYearMonths = TemporalUtil.dtol(untilResult.getMonths());
        }
        return JSTemporalDurationRecord.createWeeks(years, months, weeks, days, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    private JSTemporalDurationRecord getUnitMonth(long years, long monthsP, long weeks, long daysP, long sign, JSDynamicObject oneMonth, JSDynamicObject relativeToP, JSDynamicObject calendar) {
        long months = monthsP;
        long days = daysP;
        JSDynamicObject relativeTo = relativeToP;
        JSTemporalRelativeDateRecord moveResult = this.moveRelativeDate(calendar, relativeTo, oneMonth);
        relativeTo = moveResult.getRelativeTo();
        long oneMonthDays = moveResult.getDays();
        while (Math.abs(days) >= Math.abs(oneMonthDays)) {
            days -= oneMonthDays;
            months += sign;
            moveResult = this.moveRelativeDate(calendar, relativeTo, oneMonth);
            relativeTo = moveResult.getRelativeTo();
            oneMonthDays = moveResult.getDays();
        }
        return JSTemporalDurationRecord.createWeeks(years, months, weeks, days, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    private JSTemporalDurationRecord getUnitWeek(TemporalUtil.Unit largestUnit, long years, long months, long weeksP, long daysP, long sign, JSDynamicObject oneWeek, JSDynamicObject relativeToP, JSDynamicObject calendar) {
        long weeks = weeksP;
        long days = daysP;
        JSDynamicObject relativeTo = relativeToP;
        assert (largestUnit == TemporalUtil.Unit.WEEK);
        JSTemporalRelativeDateRecord moveResult = this.moveRelativeDate(calendar, relativeTo, oneWeek);
        relativeTo = moveResult.getRelativeTo();
        long oneWeekDays = moveResult.getDays();
        while (Math.abs(days) >= Math.abs(oneWeekDays)) {
            days -= oneWeekDays;
            weeks += sign;
            moveResult = this.moveRelativeDate(calendar, relativeTo, oneWeek);
            relativeTo = moveResult.getRelativeTo();
            oneWeekDays = moveResult.getDays();
        }
        return JSTemporalDurationRecord.createWeeks(years, months, weeks, days, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    protected JSTemporalPlainDateObject calendarDateAdd(JSDynamicObject calendar, JSDynamicObject date, JSDynamicObject duration, JSDynamicObject options, Object dateAdd) {
        if (this.callDateAddNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callDateAddNode = this.insert(JSFunctionCallNode.createCall());
        }
        Object addedDate = this.callDateAddNode.executeCall(JSArguments.create(calendar, dateAdd, date, duration, options));
        return TemporalUtil.requireTemporalDate(addedDate, this.errorBranch);
    }

    protected JSTemporalDurationObject calendarDateUntil(JSDynamicObject calendar, JSDynamicObject date, JSDynamicObject duration, JSDynamicObject options, Object dateUntil) {
        if (this.callDateUntilNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callDateUntilNode = this.insert(JSFunctionCallNode.createCall());
        }
        Object addedDate = this.callDateUntilNode.executeCall(JSArguments.create(calendar, dateUntil, date, duration, options));
        return TemporalUtil.requireTemporalDuration(addedDate);
    }

    private JSTemporalRelativeDateRecord moveRelativeDate(JSDynamicObject calendar, JSDynamicObject relativeTo, JSDynamicObject oneMonth) {
        if (this.moveRelativeDateNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.moveRelativeDateNode = this.insert(TemporalMoveRelativeDateNode.create(this.ctx));
        }
        return this.moveRelativeDateNode.execute(calendar, relativeTo, oneMonth);
    }

    private Object getDateAdd(JSDynamicObject obj) {
        if (this.getMethodDateAddNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getMethodDateAddNode = this.insert(GetMethodNode.create(this.ctx, TemporalConstants.DATE_ADD));
        }
        return this.getMethodDateAddNode.executeWithTarget(obj);
    }

    private Object getDateUntil(JSDynamicObject obj) {
        if (this.getMethodDateUntilNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getMethodDateUntilNode = this.insert(GetMethodNode.create(this.ctx, TemporalConstants.DATE_UNTIL));
        }
        return this.getMethodDateUntilNode.executeWithTarget(obj);
    }
}

