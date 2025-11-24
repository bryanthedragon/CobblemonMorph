
package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.temporal.TemporalDurationAddNodeGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDate;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTimeObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public abstract class TemporalDurationAddNode
extends JavaScriptBaseNode {
    protected final JSContext ctx;
    @Node.Child
    private GetMethodNode getMethodDateAddNode;
    @Node.Child
    private JSFunctionCallNode callDateAddNode;
    @Node.Child
    private GetMethodNode getMethodDateUntilNode;
    @Node.Child
    private JSFunctionCallNode callDateUntilNode;
    @Node.Child
    EnumerableOwnPropertyNamesNode namesNode;
    private final BranchProfile errorBranch = BranchProfile.create();
    private final BranchProfile relativeToUndefinedBranch = BranchProfile.create();
    private final BranchProfile relativeToPlainDateBranch = BranchProfile.create();
    private final BranchProfile relativeToZonedDateTimeBranch = BranchProfile.create();
    private final ConditionProfile largetUnitYMWDProfile = ConditionProfile.createBinaryProfile();

    protected TemporalDurationAddNode(JSContext ctx) {
        this.ctx = ctx;
        this.getMethodDateAddNode = GetMethodNode.create(ctx, TemporalConstants.DATE_ADD);
        this.getMethodDateUntilNode = GetMethodNode.create(ctx, TemporalConstants.DATE_UNTIL);
        this.namesNode = EnumerableOwnPropertyNamesNode.createKeys(ctx);
    }

    public static TemporalDurationAddNode create(JSContext ctx) {
        return TemporalDurationAddNodeGen.create(ctx);
    }

    public abstract JSTemporalDurationRecord execute(double var1, double var3, double var5, double var7, double var9, double var11, double var13, double var15, double var17, double var19, double var21, double var23, double var25, double var27, double var29, double var31, double var33, double var35, double var37, double var39, JSDynamicObject var41);

    @Specialization
    protected JSTemporalDurationRecord add(double y1, double mon1, double w1, double d1, double h1, double min1, double s1, double ms1, double mus1, double ns1, double y2, double mon2, double w2, double d2, double h2, double min2, double s2, double ms2, double mus2, double ns2, JSDynamicObject relativeTo) {
        assert (TemporalUtil.doubleIsInteger(y1) && TemporalUtil.doubleIsInteger(mon1) && TemporalUtil.doubleIsInteger(w1) && TemporalUtil.doubleIsInteger(d1));
        assert (TemporalUtil.doubleIsInteger(h1) && TemporalUtil.doubleIsInteger(min1) && TemporalUtil.doubleIsInteger(s1) && TemporalUtil.doubleIsInteger(ms1) && TemporalUtil.doubleIsInteger(mus1) && TemporalUtil.doubleIsInteger(ns1));
        assert (TemporalUtil.doubleIsInteger(y2) && TemporalUtil.doubleIsInteger(mon2) && TemporalUtil.doubleIsInteger(w2) && TemporalUtil.doubleIsInteger(d2));
        assert (TemporalUtil.doubleIsInteger(h2) && TemporalUtil.doubleIsInteger(min2) && TemporalUtil.doubleIsInteger(s2) && TemporalUtil.doubleIsInteger(ms2) && TemporalUtil.doubleIsInteger(mus2) && TemporalUtil.doubleIsInteger(ns2));
        TemporalUtil.Unit largestUnit1 = TemporalUtil.defaultTemporalLargestUnit(y1, mon1, w1, d1, h1, min1, s1, ms1, mus1);
        TemporalUtil.Unit largestUnit2 = TemporalUtil.defaultTemporalLargestUnit(y2, mon2, w2, d2, h2, min2, s2, ms2, mus2);
        TemporalUtil.Unit largestUnit = TemporalUtil.largerOfTwoTemporalUnits(largestUnit1, largestUnit2);
        if (relativeTo == Undefined.instance) {
            this.relativeToUndefinedBranch.enter();
            if (largestUnit == TemporalUtil.Unit.YEAR || largestUnit == TemporalUtil.Unit.MONTH || largestUnit == TemporalUtil.Unit.WEEK) {
                this.errorBranch.enter();
                throw Errors.createRangeError("Largest unit allowed with no relative is 'days'.");
            }
            JSTemporalDurationRecord result = TemporalUtil.balanceDuration(this.ctx, this.namesNode, d1 + d2, h1 + h2, min1 + min2, s1 + s2, ms1 + ms2, mus1 + mus2, ns1 + ns2, largestUnit);
            return TemporalUtil.createDurationRecord(0.0, 0.0, 0.0, result.getDays(), result.getHours(), result.getMinutes(), result.getSeconds(), result.getMilliseconds(), result.getMicroseconds(), result.getNanoseconds());
        }
        if (JSTemporalPlainDate.isJSTemporalPlainDate(relativeTo)) {
            this.relativeToPlainDateBranch.enter();
            JSTemporalPlainDateObject date = (JSTemporalPlainDateObject)relativeTo;
            JSDynamicObject calendar = date.getCalendar();
            JSTemporalDurationObject dateDuration1 = JSTemporalDuration.createTemporalDuration(this.ctx, y1, mon1, w1, d1, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
            JSTemporalDurationObject dateDuration2 = JSTemporalDuration.createTemporalDuration(this.ctx, y2, mon2, w2, d2, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
            Object dateAdd = this.getMethodDateAddNode.executeWithTarget(calendar);
            JSTemporalPlainDateObject intermediate = this.calendarDateAdd(calendar, date, dateDuration1, Undefined.instance, dateAdd);
            JSTemporalPlainDateObject end2 = this.calendarDateAdd(calendar, intermediate, dateDuration2, Undefined.instance, dateAdd);
            TemporalUtil.Unit dateLargestUnit = TemporalUtil.largerOfTwoTemporalUnits(TemporalUtil.Unit.DAY, largestUnit);
            JSObject differenceOptions = JSOrdinary.createWithNullPrototype(this.ctx);
            JSObjectUtil.putDataProperty(this.ctx, differenceOptions, (Object)TemporalConstants.LARGEST_UNIT, dateLargestUnit.toTruffleString());
            JSTemporalDurationObject dateDifference = this.calendarDateUntil(calendar, date, end2, differenceOptions, Undefined.instance);
            JSTemporalDurationRecord result = TemporalUtil.balanceDuration(this.ctx, this.namesNode, TemporalUtil.dtol(dateDifference.getDays()), h1 + h2, min1 + min2, s1 + s2, ms1 + ms2, mus1 + mus2, ns1 + ns2, largestUnit);
            return TemporalUtil.createDurationRecord(dateDifference.getYears(), dateDifference.getMonths(), dateDifference.getWeeks(), result.getDays(), result.getHours(), result.getMinutes(), result.getSeconds(), result.getMilliseconds(), result.getMicroseconds(), result.getNanoseconds());
        }
        this.relativeToZonedDateTimeBranch.enter();
        assert (TemporalUtil.isTemporalZonedDateTime(relativeTo));
        JSTemporalZonedDateTimeObject zdt = (JSTemporalZonedDateTimeObject)relativeTo;
        JSDynamicObject timeZone = zdt.getTimeZone();
        JSDynamicObject calendar = zdt.getCalendar();
        BigInt intermediateNs = TemporalUtil.addZonedDateTime(this.ctx, zdt.getNanoseconds(), timeZone, calendar, TemporalUtil.dtol(y1), TemporalUtil.dtol(mon1), TemporalUtil.dtol(w1), TemporalUtil.dtol(d1), TemporalUtil.dtol(h1), TemporalUtil.dtol(min1), TemporalUtil.dtol(s1), TemporalUtil.dtol(ms1), TemporalUtil.dtol(mus1), TemporalUtil.dtol(ns1));
        BigInt endNs = TemporalUtil.addZonedDateTime(this.ctx, intermediateNs, timeZone, calendar, TemporalUtil.dtol(y2), TemporalUtil.dtol(mon2), TemporalUtil.dtol(w2), TemporalUtil.dtol(d2), TemporalUtil.dtol(h2), TemporalUtil.dtol(min2), TemporalUtil.dtol(s2), TemporalUtil.dtol(ms2), TemporalUtil.dtol(mus2), TemporalUtil.dtol(ns2));
        if (this.largetUnitYMWDProfile.profile(TemporalUtil.Unit.YEAR != largestUnit && TemporalUtil.Unit.MONTH != largestUnit && TemporalUtil.Unit.WEEK != largestUnit && TemporalUtil.Unit.DAY != largestUnit)) {
            long diffNs = TemporalUtil.bitol(TemporalUtil.differenceInstant(zdt.getNanoseconds(), endNs, 1.0, TemporalUtil.Unit.NANOSECOND, TemporalUtil.RoundingMode.HALF_EXPAND));
            JSTemporalDurationRecord result = TemporalUtil.balanceDuration(this.ctx, this.namesNode, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, diffNs, largestUnit);
            return TemporalUtil.createDurationRecord(0.0, 0.0, 0.0, 0.0, result.getHours(), result.getMinutes(), result.getSeconds(), result.getMilliseconds(), result.getMicroseconds(), result.getNanoseconds());
        }
        return TemporalUtil.differenceZonedDateTime(this.ctx, this.namesNode, zdt.getNanoseconds(), endNs, timeZone, calendar, largestUnit);
    }

    protected JSTemporalPlainDateObject calendarDateAdd(JSDynamicObject calendar, JSDynamicObject date, JSDynamicObject duration, JSDynamicObject options, Object dateAddPrepared) {
        if (this.callDateAddNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callDateAddNode = this.insert(JSFunctionCallNode.createCall());
        }
        Object addedDate = this.callDateAddNode.executeCall(JSArguments.create(calendar, dateAddPrepared, date, duration, options));
        return TemporalUtil.requireTemporalDate(addedDate, this.errorBranch);
    }

    protected JSTemporalDurationObject calendarDateUntil(JSDynamicObject calendar, JSDynamicObject date, JSDynamicObject duration, JSDynamicObject options, Object dateUntil) {
        Object dateUntilPrepared = dateUntil;
        if (dateUntilPrepared == Undefined.instance) {
            dateUntilPrepared = this.getMethodDateUntilNode.executeWithTarget(calendar);
        }
        if (this.callDateUntilNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callDateUntilNode = this.insert(JSFunctionCallNode.createCall());
        }
        Object addedDate = this.callDateUntilNode.executeCall(JSArguments.create(calendar, dateUntilPrepared, date, duration, options));
        return TemporalUtil.requireTemporalDuration(addedDate);
    }
}

