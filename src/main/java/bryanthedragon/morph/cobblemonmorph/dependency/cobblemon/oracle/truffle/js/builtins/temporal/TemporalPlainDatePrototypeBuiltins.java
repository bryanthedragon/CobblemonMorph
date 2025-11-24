
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarGetterNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.TemporalMonthDayFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.TemporalYearMonthFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendarObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstantObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDate;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDayObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonthObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalTimeZoneObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.TemporalTime;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.EnumSet;
import java.util.List;

public class TemporalPlainDatePrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalPlainDatePrototype> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalPlainDatePrototypeBuiltins();

    protected TemporalPlainDatePrototypeBuiltins() {
        super(JSTemporalPlainDate.PROTOTYPE_NAME, TemporalPlainDatePrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalPlainDatePrototype builtinEnum) {
        switch (builtinEnum) {
            case calendar: 
            case year: 
            case month: 
            case monthCode: 
            case day: 
            case dayOfYear: 
            case dayOfWeek: 
            case weekOfYear: 
            case daysInWeek: 
            case daysInMonth: 
            case daysInYear: 
            case monthsInYear: 
            case inLeapYear: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateGetterNodeGen.create(context, builtin, builtinEnum, TemporalPlainDatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case add: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateAddNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case subtract: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateSubtractNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case with: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateWithNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case withCalendar: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateWithCalendarNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case until: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateUntilNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case since: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateSinceNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case equals: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateEqualsNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toPlainDateTime: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToPlainDateTimeNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toPlainYearMonth: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToPlainYearMonthNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toPlainMonthDay: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToPlainMonthDayNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toZonedDateTime: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToZonedDateTimeNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case getISOFields: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateGetISOFieldsNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toString: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToStringNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toLocaleString: 
            case toJSON: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateToLocaleStringNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case valueOf: {
                return TemporalPlainDatePrototypeBuiltinsFactory.JSTemporalPlainDateValueOfNodeGen.create(context, builtin, TemporalPlainDatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalPlainDateToZonedDateTimeNode
    extends JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateToZonedDateTimeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject toZonedDateTime(Object thisObj, Object item, @Cached ConditionProfile timeZoneIsUndefined, @Cached ConditionProfile timeIsUndefined, @Cached(value="create(getContext())") ToTemporalTimeNode toTemporalTime, @Cached(value="create(getContext())") ToTemporalTimeZoneNode toTemporalTimeZone) {
            JSTemporalPlainDateTimeObject temporalDateTime;
            Object temporalTime;
            JSTemporalTimeZoneObject timeZone;
            JSTemporalPlainDateObject td = this.requireTemporalDate(thisObj);
            if (this.isObject(item)) {
                JSDynamicObject itemObj = TemporalUtil.toJSDynamicObject(item, this.errorBranch);
                Object timeZoneLike = JSObject.get(itemObj, TemporalConstants.TIME_ZONE);
                if (timeZoneIsUndefined.profile(timeZoneLike == Undefined.instance)) {
                    timeZone = (JSTemporalTimeZoneObject)toTemporalTimeZone.executeDynamicObject(item);
                    temporalTime = Undefined.instance;
                } else {
                    timeZone = (JSTemporalTimeZoneObject)toTemporalTimeZone.executeDynamicObject(timeZoneLike);
                    temporalTime = JSObject.get(itemObj, TemporalConstants.PLAIN_TIME);
                }
            } else {
                timeZone = (JSTemporalTimeZoneObject)toTemporalTimeZone.executeDynamicObject(item);
                temporalTime = Undefined.instance;
            }
            if (timeIsUndefined.profile(temporalTime == Undefined.instance)) {
                temporalDateTime = JSTemporalPlainDateTime.create(this.getContext(), td.getYear(), td.getMonth(), td.getDay(), 0, 0, 0, 0, 0, 0, td.getCalendar(), this.errorBranch);
            } else {
                JSTemporalPlainTimeObject tt = (JSTemporalPlainTimeObject)toTemporalTime.executeDynamicObject(temporalTime, null);
                temporalDateTime = JSTemporalPlainDateTime.create(this.getContext(), td.getYear(), td.getMonth(), td.getDay(), tt.getHour(), tt.getMinute(), tt.getSecond(), tt.getMillisecond(), tt.getMicrosecond(), tt.getNanosecond(), td.getCalendar(), this.errorBranch);
            }
            JSTemporalInstantObject instant = TemporalUtil.builtinTimeZoneGetInstantFor(this.getContext(), timeZone, temporalDateTime, TemporalUtil.Disambiguation.COMPATIBLE);
            return JSTemporalZonedDateTime.create(this.getContext(), this.getRealm(), instant.getNanoseconds(), timeZone, td.getCalendar());
        }
    }

    public static abstract class JSTemporalPlainDateEquals
    extends JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateEquals(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public boolean equals(Object thisObj, Object otherParam, @Cached(value="create(getContext())") ToTemporalDateNode toTemporalDate, @Cached JSToStringNode toStringNode) {
            JSTemporalPlainDateObject temporalDate = this.requireTemporalDate(thisObj);
            JSTemporalPlainDateObject other = toTemporalDate.executeDynamicObject(otherParam, Undefined.instance);
            if (temporalDate.getYear() != other.getYear()) {
                return false;
            }
            if (temporalDate.getMonth() != other.getMonth()) {
                return false;
            }
            if (temporalDate.getDay() != other.getDay()) {
                return false;
            }
            return TemporalUtil.calendarEquals(temporalDate.getCalendar(), other.getCalendar(), toStringNode);
        }
    }

    public static abstract class JSTemporalPlainDateToPlainMonthDay
    extends JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateToPlainMonthDay(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject toPlainMonthDay(Object thisObj, @Cached(value="create(getContext())") TemporalMonthDayFromFieldsNode monthDayFromFieldsNode, @Cached(value="create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode) {
            JSTemporalPlainDateObject date = this.requireTemporalDate(thisObj);
            JSDynamicObject calendar = date.getCalendar();
            List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listDMC);
            JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.getContext(), date, fieldNames, TemporalUtil.listEmpty);
            return monthDayFromFieldsNode.execute(calendar, fields, Undefined.instance);
        }
    }

    public static abstract class JSTemporalPlainDateToPlainYearMonth
    extends JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateToPlainYearMonth(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject toPlainYearMonth(Object thisObj, @Cached(value="create(getContext())") TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode, @Cached(value="create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode) {
            JSTemporalPlainDateObject date = this.requireTemporalDate(thisObj);
            JSDynamicObject calendar = date.getCalendar();
            List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listMCY);
            JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.getContext(), date, fieldNames, TemporalUtil.listEmpty);
            return yearMonthFromFieldsNode.execute(calendar, fields, Undefined.instance);
        }
    }

    public static abstract class JSTemporalPlainDateToPlainDateTime
    extends JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateToPlainDateTime(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject toPlainDateTime(Object thisObj, Object temporalTimeObj, @Cached(value="create(getContext())") ToTemporalTimeNode toTemporalTime) {
            JSTemporalPlainDateObject date = this.requireTemporalDate(thisObj);
            if (temporalTimeObj == Undefined.instance) {
                return JSTemporalPlainDateTime.create(this.getContext(), date.getYear(), date.getMonth(), date.getDay(), 0, 0, 0, 0, 0, 0, date.getCalendar(), this.errorBranch);
            }
            TemporalTime time = (TemporalTime)((Object)toTemporalTime.executeDynamicObject(temporalTimeObj, null));
            return JSTemporalPlainDateTime.create(this.getContext(), date.getYear(), date.getMonth(), date.getDay(), time.getHour(), time.getMinute(), time.getSecond(), time.getMillisecond(), time.getMicrosecond(), time.getNanosecond(), date.getCalendar(), this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainDateValueOf
    extends JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateValueOf(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object valueOf(Object thisObj) {
            throw Errors.createTypeError("Not supported.");
        }
    }

    public static abstract class JSTemporalPlainDateToLocaleString
    extends JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateToLocaleString(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public TruffleString toLocaleString(Object thisObj) {
            JSTemporalPlainDateObject date = this.requireTemporalDate(thisObj);
            return JSTemporalPlainDate.temporalDateToString(date, TemporalUtil.ShowCalendar.AUTO);
        }
    }

    public static abstract class JSTemporalPlainDateToString
    extends JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateToString(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected TruffleString toString(Object thisObj, Object optionsParam, @Cached TruffleString.EqualNode equalNode) {
            JSTemporalPlainDateObject date = this.requireTemporalDate(thisObj);
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            TemporalUtil.ShowCalendar showCalendar = TemporalUtil.toShowCalendarOption(options, this.getOptionNode(), equalNode);
            return JSTemporalPlainDate.temporalDateToString(date, showCalendar);
        }
    }

    public static abstract class JSTemporalPlainDateGetISOFields
    extends JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateGetISOFields(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject getISOFields(Object thisObj) {
            JSTemporalPlainDateObject dt = this.requireTemporalDate(thisObj);
            JSObject obj = JSOrdinary.create(this.getContext(), this.getRealm());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.CALENDAR, dt.getCalendar());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_DAY, dt.getDay());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_MONTH, dt.getMonth());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_YEAR, dt.getYear());
            return obj;
        }
    }

    public static abstract class JSTemporalPlainDateUntil
    extends PlainDateOperation {
        protected JSTemporalPlainDateUntil(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject until(Object thisObj, Object otherObj, Object optionsParam, @Cached(value="create()") JSToNumberNode toNumber, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode, @Cached(value="create(getContext())") ToTemporalDateNode toTemporalDate, @Cached JSToStringNode toStringNode, @Cached TruffleString.EqualNode equalNode, @Cached(value="create(getContext())") TemporalRoundDurationNode roundDurationNode) {
            JSTemporalPlainDateObject temporalDate = this.requireTemporalDate(thisObj);
            return this.differenceTemporalPlainDate(1, temporalDate, otherObj, optionsParam, toNumber, namesNode, toTemporalDate, toStringNode, equalNode, roundDurationNode);
        }
    }

    public static abstract class JSTemporalPlainDateSince
    extends PlainDateOperation {
        protected JSTemporalPlainDateSince(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject since(Object thisObj, Object otherObj, Object optionsParam, @Cached(value="create()") JSToNumberNode toNumber, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode, @Cached(value="create(getContext())") ToTemporalDateNode toTemporalDate, @Cached JSToStringNode toStringNode, @Cached TruffleString.EqualNode equalNode, @Cached(value="create(getContext())") TemporalRoundDurationNode roundDurationNode) {
            JSTemporalPlainDateObject temporalDate = this.requireTemporalDate(thisObj);
            return this.differenceTemporalPlainDate(-1, temporalDate, otherObj, optionsParam, toNumber, namesNode, toTemporalDate, toStringNode, equalNode, roundDurationNode);
        }
    }

    public static abstract class PlainDateOperation
    extends JSTemporalBuiltinOperation {
        public PlainDateOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected JSTemporalDurationObject differenceTemporalPlainDate(int sign, JSTemporalPlainDateObject temporalDate, Object otherObj, Object optionsParam, JSToNumberNode toNumber, EnumerableOwnPropertyNamesNode namesNode, ToTemporalDateNode toTemporalDate, JSToStringNode toStringNode, TruffleString.EqualNode equalNode, TemporalRoundDurationNode roundDurationNode) {
            JSTemporalPlainDateObject other = toTemporalDate.executeDynamicObject(otherObj, Undefined.instance);
            if (!TemporalUtil.calendarEquals(temporalDate.getCalendar(), other.getCalendar(), toStringNode)) {
                this.errorBranch.enter();
                throw TemporalErrors.createRangeErrorIdenticalCalendarExpected();
            }
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            List<TruffleString> disallowedUnits = TemporalUtil.listTime;
            TemporalUtil.Unit smallestUnit = this.toSmallestTemporalUnit(options, disallowedUnits, TemporalConstants.DAY, equalNode);
            TemporalUtil.Unit defaultLargestUnit = TemporalUtil.largerOfTwoTemporalUnits(TemporalUtil.Unit.DAY, smallestUnit);
            TemporalUtil.Unit largestUnit = this.toLargestTemporalUnit(options, disallowedUnits, TemporalConstants.AUTO, defaultLargestUnit, equalNode);
            TemporalUtil.validateTemporalUnitRange(largestUnit, smallestUnit);
            TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(options, TemporalConstants.TRUNC, equalNode);
            if (sign == -1) {
                roundingMode = TemporalUtil.negateTemporalRoundingMode(roundingMode);
            }
            double roundingIncrement = TemporalUtil.toTemporalRoundingIncrement(options, null, false, this.isObjectNode, toNumber);
            JSDynamicObject untilOptions = TemporalUtil.mergeLargestUnitOption(this.getContext(), namesNode, options, largestUnit);
            JSTemporalDurationObject result = TemporalUtil.calendarDateUntil(temporalDate.getCalendar(), temporalDate, other, untilOptions, Undefined.instance);
            if (smallestUnit != TemporalUtil.Unit.DAY || roundingIncrement != 1.0) {
                JSTemporalDurationRecord result2 = roundDurationNode.execute(result.getYears(), result.getMonths(), result.getWeeks(), result.getDays(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, (long)roundingIncrement, smallestUnit, roundingMode, temporalDate);
                return JSTemporalDuration.createTemporalDuration(this.getContext(), (double)sign * result2.getYears(), (double)sign * result2.getMonths(), (double)sign * result2.getWeeks(), (double)sign * result2.getDays(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
            }
            return JSTemporalDuration.createTemporalDuration(this.getContext(), (double)sign * result.getYears(), (double)sign * result.getMonths(), (double)sign * result.getWeeks(), (double)sign * result.getDays(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainDateWithCalendar
    extends JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateWithCalendar(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject withCalendar(Object thisObj, Object calendarParam, @Cached(value="create(getContext())") ToTemporalCalendarNode toTemporalCalendar) {
            JSTemporalPlainDateObject td = this.requireTemporalDate(thisObj);
            JSDynamicObject calendar = toTemporalCalendar.executeDynamicObject(calendarParam);
            return JSTemporalPlainDate.create(this.getContext(), td.getYear(), td.getMonth(), td.getDay(), calendar, this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainDateWith
    extends JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateWith(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject with(Object thisObj, Object temporalDateLike, Object optParam, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode nameNode, @Cached(value="create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode, @Cached(value="create(getContext())") TemporalCalendarDateFromFieldsNode dateFromFieldsNode) {
            JSTemporalPlainDateObject temporalDate = this.requireTemporalDate(thisObj);
            if (!this.isObject(temporalDateLike)) {
                this.errorBranch.enter();
                throw Errors.createTypeError("Object expected");
            }
            JSDynamicObject temporalDateLikeObject = TemporalUtil.toJSDynamicObject(temporalDateLike, this.errorBranch);
            TemporalUtil.rejectTemporalCalendarType(temporalDateLikeObject, this.errorBranch);
            Object calendarProperty = JSObject.get(temporalDateLikeObject, TemporalConstants.CALENDAR);
            if (calendarProperty != Undefined.instance) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorUnexpectedCalendar();
            }
            Object timeZoneProperty = JSObject.get(temporalDateLikeObject, TemporalConstants.TIME_ZONE);
            if (timeZoneProperty != Undefined.instance) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorUnexpectedTimeZone();
            }
            JSDynamicObject calendar = temporalDate.getCalendar();
            List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listDMMCY);
            JSDynamicObject partialDate = TemporalUtil.preparePartialTemporalFields(this.getContext(), temporalDateLikeObject, fieldNames);
            JSDynamicObject options = this.getOptionsObject(optParam);
            JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.getContext(), temporalDate, fieldNames, TemporalUtil.listEmpty);
            fields = TemporalUtil.calendarMergeFields(this.getContext(), nameNode, this.errorBranch, calendar, fields, partialDate);
            fields = TemporalUtil.prepareTemporalFields(this.getContext(), fields, fieldNames, TemporalUtil.listEmpty);
            return dateFromFieldsNode.execute(calendar, fields, options);
        }
    }

    public static abstract class JSTemporalPlainDateSubtract
    extends JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateSubtract(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject subtract(Object thisObj, Object temporalDurationLike, Object optParam, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode, @Cached(value="create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
            JSTemporalPlainDateObject date = this.requireTemporalDate(thisObj);
            JSTemporalDurationRecord duration = toLimitedTemporalDurationNode.executeDynamicObject(temporalDurationLike, TemporalUtil.listEmpty);
            JSDynamicObject options = this.getOptionsObject(optParam);
            JSTemporalDurationRecord balanceResult = TemporalUtil.balanceDuration(this.getContext(), namesNode, duration.getDays(), duration.getHours(), duration.getMinutes(), duration.getSeconds(), duration.getMilliseconds(), duration.getMicroseconds(), duration.getNanoseconds(), TemporalUtil.Unit.DAY);
            JSTemporalDurationObject balancedDuration = JSTemporalDuration.createTemporalDuration(this.getContext(), -duration.getYears(), -duration.getMonths(), -duration.getWeeks(), -balanceResult.getDays(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
            return TemporalUtil.calendarDateAdd(date.getCalendar(), date, balancedDuration, options);
        }
    }

    public static abstract class JSTemporalPlainDateAdd
    extends JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateAdd(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject add(Object thisObj, Object temporalDurationLike, Object optParam, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode, @Cached(value="create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
            JSTemporalPlainDateObject date = this.requireTemporalDate(thisObj);
            JSTemporalDurationRecord duration = toLimitedTemporalDurationNode.executeDynamicObject(temporalDurationLike, TemporalUtil.listEmpty);
            JSDynamicObject options = this.getOptionsObject(optParam);
            JSTemporalDurationRecord balanceResult = TemporalUtil.balanceDuration(this.getContext(), namesNode, duration.getDays(), duration.getHours(), duration.getMinutes(), duration.getSeconds(), duration.getMilliseconds(), duration.getMicroseconds(), duration.getNanoseconds(), TemporalUtil.Unit.DAY);
            JSTemporalDurationObject balancedDuration = JSTemporalDuration.createTemporalDuration(this.getContext(), duration.getYears(), duration.getMonths(), duration.getWeeks(), balanceResult.getDays(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
            return TemporalUtil.calendarDateAdd(date.getCalendar(), date, balancedDuration, options, Undefined.instance);
        }
    }

    public static abstract class JSTemporalBuiltinOperation
    extends JSBuiltinNode {
        protected final BranchProfile errorBranch = BranchProfile.create();
        protected final ConditionProfile optionUndefined = ConditionProfile.createBinaryProfile();
        @Node.Child
        protected IsObjectNode isObjectNode = IsObjectNode.create();
        @Node.Child
        private TemporalGetOptionNode getOptionNode;

        public JSTemporalBuiltinOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected JSTemporalPlainDateObject requireTemporalDate(Object obj) {
            if (!(obj instanceof JSTemporalPlainDateObject)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalDateExpected();
            }
            return (JSTemporalPlainDateObject)obj;
        }

        protected TemporalTime requireTemporalTime(Object obj) {
            if (!(obj instanceof TemporalTime)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalTimeExpected();
            }
            return (TemporalTime)obj;
        }

        protected JSTemporalPlainDateTimeObject requireTemporalDateTime(Object obj) {
            if (!(obj instanceof JSTemporalPlainDateTimeObject)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalDateTimeExpected();
            }
            return (JSTemporalPlainDateTimeObject)obj;
        }

        protected JSTemporalPlainMonthDayObject requireTemporalMonthDay(Object obj) {
            if (!(obj instanceof JSTemporalPlainMonthDayObject)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalPlainMonthDayExpected();
            }
            return (JSTemporalPlainMonthDayObject)obj;
        }

        protected JSTemporalPlainYearMonthObject requireTemporalYearMonth(Object obj) {
            if (!(obj instanceof JSTemporalPlainYearMonthObject)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalPlainYearMonthExpected();
            }
            return (JSTemporalPlainYearMonthObject)obj;
        }

        protected JSTemporalInstantObject requireTemporalInstant(Object obj) {
            if (!(obj instanceof JSTemporalInstantObject)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalInstantExpected();
            }
            return (JSTemporalInstantObject)obj;
        }

        protected JSTemporalZonedDateTimeObject requireTemporalZonedDateTime(Object obj) {
            if (!(obj instanceof JSTemporalZonedDateTimeObject)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalZonedDateTimeExpected();
            }
            return (JSTemporalZonedDateTimeObject)obj;
        }

        protected JSTemporalCalendarObject requireTemporalCalendar(Object obj) {
            if (!(obj instanceof JSTemporalCalendarObject)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalCalendarExpected();
            }
            return (JSTemporalCalendarObject)obj;
        }

        protected JSTemporalDurationObject requireTemporalDuration(Object obj) {
            if (!(obj instanceof JSTemporalDurationObject)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalDurationExpected();
            }
            return (JSTemporalDurationObject)obj;
        }

        protected JSTemporalTimeZoneObject requireTemporalTimeZone(Object obj) {
            if (!(obj instanceof JSTemporalTimeZoneObject)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalTimeZoneExpected();
            }
            return (JSTemporalTimeZoneObject)obj;
        }

        protected JSDynamicObject getOptionsObject(Object options) {
            if (this.optionUndefined.profile(options == Undefined.instance)) {
                return JSOrdinary.createWithNullPrototype(this.getContext());
            }
            if (this.isObject(options)) {
                return TemporalUtil.toJSDynamicObject(options, this.errorBranch);
            }
            this.errorBranch.enter();
            throw TemporalErrors.createTypeErrorOptions();
        }

        protected boolean isObject(Object obj) {
            return this.isObjectNode.executeBoolean(obj);
        }

        protected TemporalUtil.Unit toLargestTemporalUnit(JSDynamicObject normalizedOptions, List<TruffleString> disallowedUnits, TruffleString fallback, TemporalUtil.Unit autoValue, TruffleString.EqualNode equalNode) {
            assert (fallback == null || !disallowedUnits.contains(fallback) && !disallowedUnits.contains(TemporalConstants.AUTO));
            TruffleString largestUnit = (TruffleString)this.getOption(normalizedOptions, TemporalConstants.LARGEST_UNIT, TemporalUtil.OptionType.STRING, TemporalUtil.listAllDateTimeAuto, fallback);
            if (largestUnit != null && largestUnit.equals(TemporalConstants.AUTO) && autoValue != null) {
                return autoValue;
            }
            if (largestUnit != null && Boundaries.setContains(TemporalUtil.pluralUnits, largestUnit)) {
                largestUnit = Boundaries.mapGet(TemporalUtil.pluralToSingular, largestUnit);
            }
            if (largestUnit != null && Boundaries.listContains(disallowedUnits, largestUnit)) {
                this.errorBranch.enter();
                throw Errors.createRangeError("Largest unit is not allowed.");
            }
            return TemporalUtil.toUnit(largestUnit, equalNode);
        }

        protected TemporalUtil.Unit toSmallestTemporalUnit(JSDynamicObject normalizedOptions, List<TruffleString> disallowedUnits, TruffleString fallback, TruffleString.EqualNode equalNode) {
            TruffleString smallestUnit = (TruffleString)this.getOption(normalizedOptions, TemporalConstants.SMALLEST_UNIT, TemporalUtil.OptionType.STRING, TemporalUtil.listAllDateTime, fallback);
            if (smallestUnit != null && Boundaries.setContains(TemporalUtil.pluralUnits, smallestUnit)) {
                smallestUnit = Boundaries.mapGet(TemporalUtil.pluralToSingular, smallestUnit);
            }
            if (smallestUnit != null && Boundaries.listContains(disallowedUnits, smallestUnit)) {
                this.errorBranch.enter();
                throw Errors.createRangeError("Smallest unit not allowed.");
            }
            return TemporalUtil.toUnit(smallestUnit, equalNode);
        }

        protected Object getOption(JSDynamicObject option, TruffleString properties2, TemporalUtil.OptionType type, List<TruffleString> values, TruffleString fallback) {
            return this.getOptionNode().execute(option, properties2, type, values, fallback);
        }

        protected TemporalUtil.Unit toTemporalDurationTotalUnit(JSDynamicObject normalizedOptions, TruffleString.EqualNode equalNode) {
            TruffleString unit = (TruffleString)this.getOption(normalizedOptions, TemporalConstants.UNIT, TemporalUtil.OptionType.STRING, TemporalUtil.listAllDateTime, null);
            if (unit != null && Boundaries.setContains(TemporalUtil.pluralUnits, unit)) {
                unit = Boundaries.mapGet(TemporalUtil.pluralToSingular, unit);
            }
            return TemporalUtil.toUnit(unit, equalNode);
        }

        protected TemporalUtil.RoundingMode toTemporalRoundingMode(JSDynamicObject options, TruffleString fallback, TruffleString.EqualNode equalNode) {
            return TemporalUtil.toRoundingMode((TruffleString)this.getOption(options, TemporalConstants.ROUNDING_MODE, TemporalUtil.OptionType.STRING, TemporalUtil.listRoundingMode, fallback), equalNode);
        }

        protected TemporalGetOptionNode getOptionNode() {
            if (this.getOptionNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getOptionNode = this.insert(TemporalGetOptionNode.create());
            }
            return this.getOptionNode;
        }
    }

    public static abstract class JSTemporalPlainDateGetterNode
    extends JSBuiltinNode {
        public final TemporalPlainDatePrototype property;

        public JSTemporalPlainDateGetterNode(JSContext context, JSBuiltin builtin, TemporalPlainDatePrototype property) {
            super(context, builtin);
            this.property = property;
        }

        @Specialization(guards={"isJSTemporalDate(thisObj)"})
        protected Object dateGetter(Object thisObj, @Cached(value="create(getContext())") TemporalCalendarGetterNode calendarGetterNode) {
            JSTemporalPlainDateObject temporalDT = (JSTemporalPlainDateObject)thisObj;
            switch (this.property) {
                case calendar: {
                    return temporalDT.getCalendar();
                }
                case year: {
                    return TemporalUtil.calendarYear(calendarGetterNode, temporalDT.getCalendar(), temporalDT);
                }
                case month: {
                    return TemporalUtil.calendarMonth(calendarGetterNode, temporalDT.getCalendar(), temporalDT);
                }
                case day: {
                    return TemporalUtil.calendarDay(calendarGetterNode, temporalDT.getCalendar(), temporalDT);
                }
                case dayOfWeek: {
                    return TemporalUtil.calendarDayOfWeek(calendarGetterNode, temporalDT.getCalendar(), temporalDT);
                }
                case dayOfYear: {
                    return TemporalUtil.calendarDayOfYear(calendarGetterNode, temporalDT.getCalendar(), temporalDT);
                }
                case monthCode: {
                    return TemporalUtil.calendarMonthCode(calendarGetterNode, temporalDT.getCalendar(), temporalDT);
                }
                case weekOfYear: {
                    return TemporalUtil.calendarWeekOfYear(calendarGetterNode, temporalDT.getCalendar(), temporalDT);
                }
                case daysInWeek: {
                    return TemporalUtil.calendarDaysInWeek(calendarGetterNode, temporalDT.getCalendar(), temporalDT);
                }
                case daysInMonth: {
                    return TemporalUtil.calendarDaysInMonth(calendarGetterNode, temporalDT.getCalendar(), temporalDT);
                }
                case daysInYear: {
                    return TemporalUtil.calendarDaysInYear(calendarGetterNode, temporalDT.getCalendar(), temporalDT);
                }
                case monthsInYear: {
                    return TemporalUtil.calendarMonthsInYear(calendarGetterNode, temporalDT.getCalendar(), temporalDT);
                }
                case inLeapYear: {
                    return TemporalUtil.calendarInLeapYear(calendarGetterNode, temporalDT.getCalendar(), temporalDT);
                }
            }
            CompilerDirectives.transferToInterpreter();
            throw Errors.shouldNotReachHere();
        }

        @Specialization(guards={"!isJSTemporalDate(thisObj)"})
        protected static int error(Object thisObj) {
            throw TemporalErrors.createTypeErrorTemporalDateExpected();
        }
    }

    public static enum TemporalPlainDatePrototype implements BuiltinEnum<TemporalPlainDatePrototype>
    {
        calendar(0),
        year(0),
        month(0),
        monthCode(0),
        day(0),
        dayOfYear(0),
        dayOfWeek(0),
        weekOfYear(0),
        daysInWeek(0),
        daysInMonth(0),
        daysInYear(0),
        monthsInYear(0),
        inLeapYear(0),
        toPlainYearMonth(0),
        toPlainMonthDay(0),
        getISOFields(0),
        add(1),
        subtract(1),
        with(1),
        withCalendar(1),
        until(1),
        since(1),
        equals(1),
        toPlainDateTime(0),
        toZonedDateTime(1),
        toString(0),
        toLocaleString(0),
        toJSON(0),
        valueOf(0);

        private final int length;

        private TemporalPlainDatePrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isGetter() {
            return EnumSet.of(calendar, new TemporalPlainDatePrototype[]{year, month, monthCode, day, dayOfYear, dayOfWeek, weekOfYear, daysInWeek, daysInMonth, daysInYear, monthsInYear, inLeapYear}).contains(this);
        }
    }
}

