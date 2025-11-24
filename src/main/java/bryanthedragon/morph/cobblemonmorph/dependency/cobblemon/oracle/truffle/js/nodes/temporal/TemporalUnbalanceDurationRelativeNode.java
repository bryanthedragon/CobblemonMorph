
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
import com.oracle.truffle.js.nodes.temporal.TemporalMoveRelativeDateNode;
import com.oracle.truffle.js.nodes.temporal.TemporalUnbalanceDurationRelativeNodeGen;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.runtime.Errors;
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
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public abstract class TemporalUnbalanceDurationRelativeNode
extends JavaScriptBaseNode {
    protected final JSContext ctx;
    private final BranchProfile errorBranch = BranchProfile.create();
    @Node.Child
    private GetMethodNode getMethodDateAddNode;
    @Node.Child
    private JSFunctionCallNode callDateAddNode;
    @Node.Child
    private GetMethodNode getMethodDateUntilNode;
    @Node.Child
    private JSFunctionCallNode callDateUntilNode;
    @Node.Child
    private TemporalMoveRelativeDateNode moveRelativeDateNode;

    protected TemporalUnbalanceDurationRelativeNode(JSContext ctx) {
        this.ctx = ctx;
    }

    public static TemporalUnbalanceDurationRelativeNode create(JSContext ctx) {
        return TemporalUnbalanceDurationRelativeNodeGen.create(ctx);
    }

    public abstract JSTemporalDurationRecord execute(double var1, double var3, double var5, double var7, TemporalUtil.Unit var9, JSDynamicObject var10);

    @Specialization
    protected JSTemporalDurationRecord unbalanceDurationRelative(double y, double m, double w, double d, TemporalUtil.Unit largestUnit, JSDynamicObject relTo, @Cached ConditionProfile unitIsYear, @Cached ConditionProfile unitIsWeek, @Cached ConditionProfile unitIsMonth, @Cached ConditionProfile relativeToAvailable, @Cached(value="create(ctx)") ToTemporalDateNode toTemporalDateNode) {
        long years = TemporalUtil.dtol(y);
        long months = TemporalUtil.dtol(m);
        long weeks = TemporalUtil.dtol(w);
        long days = TemporalUtil.dtol(d);
        JSDynamicObject relativeTo = relTo;
        if (unitIsYear.profile(TemporalUtil.Unit.YEAR == largestUnit || years == 0L && months == 0L && weeks == 0L && days == 0L)) {
            return JSTemporalDurationRecord.createWeeks(years, months, weeks, days, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        }
        long sign = TemporalUtil.durationSign(years, months, weeks, days, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        assert (sign != 0L);
        JSTemporalDurationObject oneYear = JSTemporalDuration.createTemporalDuration(this.ctx, sign, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
        JSTemporalDurationObject oneMonth = JSTemporalDuration.createTemporalDuration(this.ctx, 0.0, sign, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
        JSTemporalDurationObject oneWeek = JSTemporalDuration.createTemporalDuration(this.ctx, 0.0, 0.0, sign, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
        JSDynamicObject calendar = Undefined.instance;
        if (relativeToAvailable.profile(relativeTo != Undefined.instance)) {
            JSTemporalPlainDateObject pdo = toTemporalDateNode.executeDynamicObject(relativeTo, Undefined.instance);
            relativeTo = pdo;
            calendar = pdo.getCalendar();
        }
        if (unitIsMonth.profile(TemporalUtil.Unit.MONTH == largestUnit)) {
            return this.unitIsMonth(years, months, weeks, days, relativeTo, sign, oneYear, calendar);
        }
        if (unitIsWeek.profile(TemporalUtil.Unit.WEEK == largestUnit)) {
            return this.unitIsWeek(years, months, weeks, days, relativeTo, sign, oneYear, oneMonth, calendar);
        }
        return this.unitIsDay(years, months, weeks, days, relativeTo, sign, oneYear, oneMonth, oneWeek, calendar);
    }

    private JSTemporalDurationRecord unitIsDay(long yearsP, long monthsP, long weeksP, long daysP, JSDynamicObject relativeToP, long sign, JSDynamicObject oneYear, JSDynamicObject oneMonth, JSDynamicObject oneWeek, JSDynamicObject calendar) {
        long years = yearsP;
        long months = monthsP;
        long weeks = weeksP;
        long days = daysP;
        JSDynamicObject relativeTo = relativeToP;
        if (years != 0L || months != 0L || weeks != 0L) {
            JSTemporalRelativeDateRecord moveResult;
            if (calendar == Undefined.instance) {
                this.errorBranch.enter();
                throw Errors.createRangeError("Calendar should not be undefined.");
            }
            while (Math.abs(years) > 0L) {
                moveResult = this.moveRelativeDate(calendar, relativeTo, oneYear);
                relativeTo = moveResult.getRelativeTo();
                long oneYearDays = moveResult.getDays();
                years -= sign;
                days += oneYearDays;
            }
            while (Math.abs(months) > 0L) {
                moveResult = this.moveRelativeDate(calendar, relativeTo, oneMonth);
                relativeTo = moveResult.getRelativeTo();
                long oneMonthDays = moveResult.getDays();
                months -= sign;
                days += oneMonthDays;
            }
            while (Math.abs(weeks) > 0L) {
                moveResult = this.moveRelativeDate(calendar, relativeTo, oneWeek);
                relativeTo = moveResult.getRelativeTo();
                long oneWeekDays = moveResult.getDays();
                weeks -= sign;
                days += oneWeekDays;
            }
        }
        return JSTemporalDurationRecord.createWeeks(years, months, weeks, days, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    private JSTemporalDurationRecord unitIsWeek(long yearsP, long monthsP, long weeks, long daysP, JSDynamicObject relativeToP, long sign, JSDynamicObject oneYear, JSDynamicObject oneMonth, JSDynamicObject calendar) {
        JSTemporalRelativeDateRecord moveResult;
        long years = yearsP;
        long months = monthsP;
        long days = daysP;
        JSDynamicObject relativeTo = relativeToP;
        if (calendar == Undefined.instance) {
            this.errorBranch.enter();
            throw Errors.createRangeError("Calendar should not be undefined.");
        }
        while (Math.abs(years) > 0L) {
            moveResult = this.moveRelativeDate(calendar, relativeTo, oneYear);
            relativeTo = moveResult.getRelativeTo();
            long oneYearDays = moveResult.getDays();
            years -= sign;
            days += oneYearDays;
        }
        while (Math.abs(months) > 0L) {
            moveResult = this.moveRelativeDate(calendar, relativeTo, oneMonth);
            relativeTo = moveResult.getRelativeTo();
            long oneMonthDays = moveResult.getDays();
            months -= sign;
            days += oneMonthDays;
        }
        return JSTemporalDurationRecord.createWeeks(years, months, weeks, days, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }

    private JSTemporalDurationRecord unitIsMonth(long yearsP, long monthsP, long weeks, long days, JSDynamicObject relativeToP, long sign, JSDynamicObject oneYear, JSDynamicObject calendar) {
        long years = yearsP;
        long months = monthsP;
        JSDynamicObject relativeTo = relativeToP;
        if (calendar == Undefined.instance) {
            this.errorBranch.enter();
            throw Errors.createRangeError("No calendar provided.");
        }
        if (this.getMethodDateAddNode == null || this.getMethodDateUntilNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getMethodDateAddNode = this.insert(GetMethodNode.create(this.ctx, TemporalConstants.DATE_ADD));
            this.getMethodDateUntilNode = this.insert(GetMethodNode.create(this.ctx, TemporalConstants.DATE_UNTIL));
        }
        Object dateAdd = this.getMethodDateAddNode.executeWithTarget(calendar);
        Object dateUntil = this.getMethodDateUntilNode.executeWithTarget(calendar);
        while (Math.abs(years) > 0L) {
            JSObject addOptions = JSOrdinary.createWithNullPrototype(this.ctx);
            JSTemporalPlainDateObject newRelativeTo = this.calendarDateAdd(calendar, relativeTo, oneYear, addOptions, dateAdd);
            JSObject untilOptions = JSOrdinary.createWithNullPrototype(this.ctx);
            JSObjectUtil.putDataProperty(this.ctx, untilOptions, (Object)TemporalConstants.LARGEST_UNIT, TemporalConstants.MONTH);
            JSTemporalDurationObject untilResult = this.calendarDateUntil(calendar, relativeTo, newRelativeTo, untilOptions, dateUntil);
            long oneYearMonths = TemporalUtil.dtol(untilResult.getMonths());
            relativeTo = newRelativeTo;
            years -= sign;
            months += oneYearMonths;
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
}

