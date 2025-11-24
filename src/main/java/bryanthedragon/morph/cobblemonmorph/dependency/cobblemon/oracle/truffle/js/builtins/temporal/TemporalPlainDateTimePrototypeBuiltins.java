
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDateTimePrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsLongNode;
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
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDateTimeRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstantObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDate;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPrecisionRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalTimeZoneObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTime;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.EnumSet;
import java.util.List;

public class TemporalPlainDateTimePrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalPlainDateTimePrototype> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalPlainDateTimePrototypeBuiltins();

    protected TemporalPlainDateTimePrototypeBuiltins() {
        super(JSTemporalPlainDateTime.PROTOTYPE_NAME, TemporalPlainDateTimePrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalPlainDateTimePrototype builtinEnum) {
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
            case inLeapYear: 
            case hour: 
            case minute: 
            case second: 
            case millisecond: 
            case microsecond: 
            case nanosecond: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeGetterNodeGen.create(context, builtin, builtinEnum, TemporalPlainDateTimePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case add: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeAddNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case subtract: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeSubtractNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case with: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeWithNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case withPlainTime: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeWithPlainTimeNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case withPlainDate: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeWithPlainDateNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case withCalendar: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeWithCalendarNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case until: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeUntilNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case since: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeSinceNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case round: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeRoundNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case equals: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeEqualsNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toPlainTime: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeToPlainTimeNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(0).createArgumentNodes(context));
            }
            case toPlainDate: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeToPlainDateNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(0).createArgumentNodes(context));
            }
            case toZonedDateTime: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeToZonedDateTimeNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case toPlainYearMonth: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeToPlainYearMonthNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(0).createArgumentNodes(context));
            }
            case toPlainMonthDay: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeToPlainMonthDayNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(0).createArgumentNodes(context));
            }
            case getISOFields: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeGetISOFieldsNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toString: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeToStringNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toLocaleString: 
            case toJSON: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeToLocaleStringNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case valueOf: {
                return TemporalPlainDateTimePrototypeBuiltinsFactory.JSTemporalPlainDateTimeValueOfNodeGen.create(context, builtin, TemporalPlainDateTimePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalPlainDateTimeWithCalendarNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeWithCalendarNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject withCalendar(Object thisObj, Object calendarParam, @Cached(value="create(getContext())") ToTemporalCalendarNode toTemporalCalendar) {
            JSTemporalPlainDateTimeObject temporalDateTime = this.requireTemporalDateTime(thisObj);
            JSDynamicObject calendar = toTemporalCalendar.executeDynamicObject(calendarParam);
            return JSTemporalPlainDateTime.create(this.getContext(), temporalDateTime.getYear(), temporalDateTime.getMonth(), temporalDateTime.getDay(), temporalDateTime.getHour(), temporalDateTime.getMinute(), temporalDateTime.getSecond(), temporalDateTime.getMillisecond(), temporalDateTime.getMicrosecond(), temporalDateTime.getNanosecond(), calendar, this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainDateTimeWithPlainDateNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeWithPlainDateNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject withPlainDate(Object thisObj, Object plainDateLike, @Cached(value="create(getContext())") ToTemporalDateNode toTemporalDate, @Cached JSToStringNode toStringNode) {
            JSTemporalPlainDateTimeObject temporalDateTime = this.requireTemporalDateTime(thisObj);
            JSTemporalPlainDateObject plainDate = toTemporalDate.executeDynamicObject(plainDateLike, Undefined.instance);
            JSDynamicObject calendar = TemporalUtil.consolidateCalendars(temporalDateTime.getCalendar(), plainDate.getCalendar(), toStringNode);
            return JSTemporalPlainDateTime.create(this.getContext(), plainDate.getYear(), plainDate.getMonth(), plainDate.getDay(), temporalDateTime.getHour(), temporalDateTime.getMinute(), temporalDateTime.getSecond(), temporalDateTime.getMillisecond(), temporalDateTime.getMicrosecond(), temporalDateTime.getNanosecond(), calendar, this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainDateTimeWithPlainTimeNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeWithPlainTimeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject withPlainTime(Object thisObj, Object plainTimeLike, @Cached(value="create(getContext())") ToTemporalTimeNode toTemporalTime) {
            JSTemporalPlainDateTimeObject temporalDateTime = this.requireTemporalDateTime(thisObj);
            if (plainTimeLike == Undefined.instance) {
                return JSTemporalPlainDateTime.create(this.getContext(), temporalDateTime.getYear(), temporalDateTime.getMonth(), temporalDateTime.getDay(), 0, 0, 0, 0, 0, 0, temporalDateTime.getCalendar(), this.errorBranch);
            }
            JSTemporalPlainTimeObject plainTime = (JSTemporalPlainTimeObject)toTemporalTime.executeDynamicObject(plainTimeLike, null);
            return JSTemporalPlainDateTime.create(this.getContext(), temporalDateTime.getYear(), temporalDateTime.getMonth(), temporalDateTime.getDay(), plainTime.getHour(), plainTime.getMinute(), plainTime.getSecond(), plainTime.getMillisecond(), plainTime.getMicrosecond(), plainTime.getNanosecond(), temporalDateTime.getCalendar(), this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainDateTimeToPlainMonthDayNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeToPlainMonthDayNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject toPlainMonthDay(Object thisObj, @Cached(value="create(getContext())") TemporalMonthDayFromFieldsNode monthDayFromFieldsNode, @Cached(value="create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode) {
            JSTemporalPlainDateTimeObject dateTime = this.requireTemporalDateTime(thisObj);
            JSDynamicObject calendar = dateTime.getCalendar();
            List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listDMC);
            JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.getContext(), dateTime, fieldNames, TemporalUtil.listEmpty);
            return monthDayFromFieldsNode.execute(calendar, fields, Undefined.instance);
        }
    }

    public static abstract class JSTemporalPlainDateTimeToPlainYearMonthNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeToPlainYearMonthNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject toPlainYearMonth(Object thisObj, @Cached(value="create(getContext())") TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode, @Cached(value="create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode) {
            JSTemporalPlainDateTimeObject dateTime = this.requireTemporalDateTime(thisObj);
            JSDynamicObject calendar = dateTime.getCalendar();
            List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listMCY);
            JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.getContext(), dateTime, fieldNames, TemporalUtil.listEmpty);
            return yearMonthFromFieldsNode.execute(calendar, fields, Undefined.instance);
        }
    }

    public static abstract class JSTemporalPlainDateTimeToZonedDateTimeNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeToZonedDateTimeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject toZonedDateTime(Object thisObj, Object temporalTimeZoneLike, Object optionsParam, @Cached(value="create(getContext())") ToTemporalTimeZoneNode toTemporalTimeZone, @Cached TruffleString.EqualNode equalNode) {
            JSTemporalPlainDateTimeObject dateTime = this.requireTemporalDateTime(thisObj);
            JSTemporalTimeZoneObject timeZone = (JSTemporalTimeZoneObject)toTemporalTimeZone.executeDynamicObject(temporalTimeZoneLike);
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            TemporalUtil.Disambiguation disambiguation = TemporalUtil.toTemporalDisambiguation(options, this.getOptionNode(), equalNode);
            JSTemporalInstantObject instant = TemporalUtil.builtinTimeZoneGetInstantFor(this.getContext(), timeZone, dateTime, disambiguation);
            return JSTemporalZonedDateTime.create(this.getContext(), this.getRealm(), instant.getNanoseconds(), timeZone, dateTime.getCalendar());
        }
    }

    public static abstract class JSTemporalPlainDateTimeToPlainDateNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeToPlainDateNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject toPlainDate(Object thisObj) {
            JSTemporalPlainDateTimeObject dt = this.requireTemporalDateTime(thisObj);
            return JSTemporalPlainDate.create(this.getContext(), dt.getYear(), dt.getMonth(), dt.getDay(), dt.getCalendar(), this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainDateTimeToPlainTimeNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeToPlainTimeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject toPlainTime(Object thisObj) {
            JSTemporalPlainDateTimeObject dt = this.requireTemporalDateTime(thisObj);
            return JSTemporalPlainTime.create(this.getContext(), dt.getHour(), dt.getMinute(), dt.getSecond(), dt.getMillisecond(), dt.getMicrosecond(), dt.getNanosecond(), this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainDateTimeRoundNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeRoundNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject round(Object thisObj, Object roundToParam, @Cached(value="create()") JSToNumberNode toNumberNode, @Cached TruffleString.EqualNode equalNode) {
            JSDynamicObject roundTo;
            JSTemporalPlainDateTimeObject dt = this.requireTemporalDateTime(thisObj);
            if (roundToParam == Undefined.instance) {
                throw TemporalErrors.createTypeErrorOptionsUndefined();
            }
            if (Strings.isTString(roundToParam)) {
                roundTo = JSOrdinary.createWithNullPrototype(this.getContext());
                JSRuntime.createDataPropertyOrThrow(roundTo, TemporalConstants.SMALLEST_UNIT, JSRuntime.toStringIsString(roundToParam));
            } else {
                roundTo = this.getOptionsObject(roundToParam);
            }
            TemporalUtil.Unit smallestUnit = this.toSmallestTemporalUnit(roundTo, TemporalUtil.listYMW, null, equalNode);
            if (smallestUnit == TemporalUtil.Unit.EMPTY) {
                this.errorBranch.enter();
                throw TemporalErrors.createRangeErrorSmallestUnitOutOfRange();
            }
            TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(roundTo, TemporalConstants.HALF_EXPAND, equalNode);
            double roundingIncrement = TemporalUtil.toTemporalDateTimeRoundingIncrement(roundTo, smallestUnit, this.isObjectNode, toNumberNode);
            JSTemporalDurationRecord result = TemporalUtil.roundISODateTime(dt.getYear(), dt.getMonth(), dt.getDay(), dt.getHour(), dt.getMinute(), dt.getSecond(), dt.getMillisecond(), dt.getMicrosecond(), dt.getNanosecond(), roundingIncrement, smallestUnit, roundingMode, null);
            return JSTemporalPlainDateTime.create(this.getContext(), TemporalUtil.dtoi(result.getYears()), TemporalUtil.dtoi(result.getMonths()), TemporalUtil.dtoi(result.getDays()), TemporalUtil.dtoi(result.getHours()), TemporalUtil.dtoi(result.getMinutes()), TemporalUtil.dtoi(result.getSeconds()), TemporalUtil.dtoi(result.getMilliseconds()), TemporalUtil.dtoi(result.getMicroseconds()), TemporalUtil.dtoi(result.getNanoseconds()), dt.getCalendar(), this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainDateTimeEquals
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeEquals(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSTemporalDateTime(otherObj)"})
        protected boolean equalsOtherObj(Object thisObj, JSDynamicObject otherObj, @Cached.Shared(value="toString") @Cached JSToStringNode toStringNode) {
            JSTemporalPlainDateTimeObject temporalDT = this.requireTemporalDateTime(thisObj);
            return JSTemporalPlainDateTimeEquals.equalsIntl(temporalDT, (JSTemporalPlainDateTimeObject)otherObj, toStringNode);
        }

        @Specialization(guards={"!isJSTemporalDateTime(other)"})
        protected boolean equalsGeneric(Object thisObj, Object other, @Cached(value="create(getContext())") ToTemporalDateTimeNode toTemporalDateTime, @Cached.Shared(value="toString") @Cached JSToStringNode toStringNode) {
            JSTemporalPlainDateTimeObject one = this.requireTemporalDateTime(thisObj);
            JSTemporalPlainDateTimeObject two = (JSTemporalPlainDateTimeObject)toTemporalDateTime.executeDynamicObject(other, Undefined.instance);
            return JSTemporalPlainDateTimeEquals.equalsIntl(one, two, toStringNode);
        }

        private static boolean equalsIntl(JSTemporalPlainDateTimeObject one, JSTemporalPlainDateTimeObject two, JSToStringNode toStringNode) {
            int result = TemporalUtil.compareISODateTime(one.getYear(), one.getMonth(), one.getDay(), one.getHour(), one.getMinute(), one.getSecond(), one.getMillisecond(), one.getMicrosecond(), one.getNanosecond(), two.getYear(), two.getMonth(), two.getDay(), two.getHour(), two.getMinute(), two.getSecond(), two.getMillisecond(), two.getMicrosecond(), two.getNanosecond());
            if (result != 0) {
                return false;
            }
            return TemporalUtil.calendarEquals(one.getCalendar(), two.getCalendar(), toStringNode);
        }
    }

    public static abstract class JSTemporalPlainDateTimeWith
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeWith(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject with(Object thisObj, Object temporalDateTimeLike, Object optParam, @Cached(value="create()") JSToStringNode toString, @Cached(value="create()") JSToIntegerAsLongNode toInt, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode, @Cached TemporalGetOptionNode getOptionNode, @Cached(value="create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode, @Cached(value="create(getContext())") TemporalCalendarDateFromFieldsNode dateFromFieldsNode) {
            JSTemporalPlainDateTimeObject dateTime = this.requireTemporalDateTime(thisObj);
            if (!this.isObject(temporalDateTimeLike)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalDateTimeExpected();
            }
            JSDynamicObject temporalDTObj = (JSDynamicObject)temporalDateTimeLike;
            Object calendarProperty = JSObject.get(temporalDTObj, TemporalConstants.CALENDAR);
            if (calendarProperty != Undefined.instance) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorUnexpectedCalendar();
            }
            Object timeZoneProperty = JSObject.get(temporalDTObj, TemporalConstants.TIME_ZONE);
            if (timeZoneProperty != Undefined.instance) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorUnexpectedTimeZone();
            }
            JSDynamicObject calendar = dateTime.getCalendar();
            List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listDHMMMMMNSY);
            JSDynamicObject partialDateTime = TemporalUtil.preparePartialTemporalFields(this.getContext(), temporalDTObj, fieldNames);
            JSDynamicObject options = this.getOptionsObject(optParam);
            JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.getContext(), dateTime, fieldNames, TemporalUtil.listEmpty);
            fields = TemporalUtil.calendarMergeFields(this.getContext(), namesNode, this.errorBranch, calendar, fields, partialDateTime);
            fields = TemporalUtil.prepareTemporalFields(this.getContext(), fields, fieldNames, TemporalUtil.listEmpty);
            JSTemporalDateTimeRecord result = TemporalUtil.interpretTemporalDateTimeFields(calendar, fields, options, getOptionNode, dateFromFieldsNode);
            assert (TemporalUtil.isValidISODate(result.getYear(), result.getMonth(), result.getDay()));
            assert (TemporalUtil.isValidTime(result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), result.getNanosecond()));
            return JSTemporalPlainDateTime.create(this.getContext(), result.getYear(), result.getMonth(), result.getDay(), result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), result.getNanosecond(), calendar, this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainDateTimeValueOf
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeValueOf(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object valueOf(Object thisObj) {
            throw Errors.createTypeError("Not supported.");
        }
    }

    public static abstract class JSTemporalPlainDateTimeToLocaleString
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeToLocaleString(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public TruffleString toLocaleString(Object thisObj) {
            JSTemporalPlainDateTimeObject dt = this.requireTemporalDateTime(thisObj);
            return JSTemporalPlainDateTime.temporalDateTimeToString(dt.getYear(), dt.getMonth(), dt.getDay(), dt.getHour(), dt.getMinute(), dt.getSecond(), dt.getMillisecond(), dt.getMicrosecond(), dt.getNanosecond(), dt.getCalendar(), TemporalConstants.AUTO, TemporalUtil.ShowCalendar.AUTO);
        }
    }

    public static abstract class JSTemporalPlainDateTimeToString
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeToString(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected TruffleString toString(Object thisObj, Object optionsParam, @Cached JSToStringNode toStringNode, @Cached TruffleString.EqualNode equalNode) {
            JSTemporalPlainDateTimeObject dt = this.requireTemporalDateTime(thisObj);
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            JSTemporalPrecisionRecord precision = TemporalUtil.toSecondsStringPrecision(options, toStringNode, this.getOptionNode(), equalNode);
            TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(options, TemporalConstants.TRUNC, equalNode);
            TemporalUtil.ShowCalendar showCalendar = TemporalUtil.toShowCalendarOption(options, this.getOptionNode(), equalNode);
            JSTemporalDurationRecord result = TemporalUtil.roundISODateTime(dt.getYear(), dt.getMonth(), dt.getDay(), dt.getHour(), dt.getMinute(), dt.getSecond(), dt.getMillisecond(), dt.getMicrosecond(), dt.getNanosecond(), precision.getIncrement(), precision.getUnit(), roundingMode, null);
            return JSTemporalPlainDateTime.temporalDateTimeToString(TemporalUtil.dtoi(result.getYears()), TemporalUtil.dtoi(result.getMonths()), TemporalUtil.dtoi(result.getDays()), TemporalUtil.dtoi(result.getHours()), TemporalUtil.dtoi(result.getMinutes()), TemporalUtil.dtoi(result.getSeconds()), TemporalUtil.dtoi(result.getMilliseconds()), TemporalUtil.dtoi(result.getMicroseconds()), TemporalUtil.dtoi(result.getNanoseconds()), dt.getCalendar(), precision.getPrecision(), showCalendar);
        }
    }

    public static abstract class JSTemporalPlainDateTimeGetISOFields
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainDateTimeGetISOFields(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject getISOFields(Object thisObj) {
            JSTemporalPlainDateTimeObject dt = this.requireTemporalDateTime(thisObj);
            JSObject obj = JSOrdinary.create(this.getContext(), this.getRealm());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.CALENDAR, dt.getCalendar());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_DAY, dt.getDay());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_HOUR, dt.getHour());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_MICROSECOND, dt.getMicrosecond());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_MILLISECOND, dt.getMillisecond());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_MINUTE, dt.getMinute());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_MONTH, dt.getMonth());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_NANOSECOND, dt.getNanosecond());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_SECOND, dt.getSecond());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_YEAR, dt.getYear());
            return obj;
        }
    }

    public static abstract class JSTemporalPlainDateTimeUntilNode
    extends PlainDateTimeOperation {
        protected JSTemporalPlainDateTimeUntilNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject until(Object thisObj, Object otherObj, Object optionsParam, @Cached(value="create()") JSToNumberNode toNumber, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode, @Cached(value="create(getContext())") ToTemporalDateTimeNode toTemporalDateTime, @Cached JSToStringNode toStringNode, @Cached TruffleString.EqualNode equalNode, @Cached(value="create(getContext())") TemporalRoundDurationNode roundDurationNode) {
            JSTemporalPlainDateTimeObject dateTime = this.requireTemporalDateTime(thisObj);
            return this.differenceTemporalPlainDateTime(1, dateTime, otherObj, optionsParam, toNumber, namesNode, toTemporalDateTime, toStringNode, equalNode, roundDurationNode);
        }
    }

    public static abstract class JSTemporalPlainDateTimeSinceNode
    extends PlainDateTimeOperation {
        protected JSTemporalPlainDateTimeSinceNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject since(Object thisObj, Object otherObj, Object optionsParam, @Cached(value="create()") JSToNumberNode toNumber, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode, @Cached(value="create(getContext())") ToTemporalDateTimeNode toTemporalDateTime, @Cached JSToStringNode toStringNode, @Cached TruffleString.EqualNode equalNode, @Cached(value="create(getContext())") TemporalRoundDurationNode roundDurationNode) {
            JSTemporalPlainDateTimeObject dateTime = this.requireTemporalDateTime(thisObj);
            return this.differenceTemporalPlainDateTime(-1, dateTime, otherObj, optionsParam, toNumber, namesNode, toTemporalDateTime, toStringNode, equalNode, roundDurationNode);
        }
    }

    public static abstract class JSTemporalPlainDateTimeSubtractNode
    extends PlainDateTimeOperation {
        protected JSTemporalPlainDateTimeSubtractNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject subtract(Object thisObj, Object temporalDurationLike, Object optParam, @Cached(value="create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
            JSTemporalPlainDateTimeObject dateTime = this.requireTemporalDateTime(thisObj);
            return this.addDurationToOrSubtractDurationFromPlainDateTime(-1, dateTime, temporalDurationLike, optParam, toLimitedTemporalDurationNode);
        }
    }

    public static abstract class JSTemporalPlainDateTimeAdd
    extends PlainDateTimeOperation {
        protected JSTemporalPlainDateTimeAdd(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject add(Object thisObj, Object temporalDurationLike, Object optParam, @Cached(value="create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
            JSTemporalPlainDateTimeObject dateTime = this.requireTemporalDateTime(thisObj);
            return this.addDurationToOrSubtractDurationFromPlainDateTime(1, dateTime, temporalDurationLike, optParam, toLimitedTemporalDurationNode);
        }
    }

    public static abstract class PlainDateTimeOperation
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        public PlainDateTimeOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected JSTemporalPlainDateTimeObject addDurationToOrSubtractDurationFromPlainDateTime(int sign, JSTemporalPlainDateTimeObject dateTime, Object temporalDurationLike, Object optParam, ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
            JSTemporalDurationRecord duration = toLimitedTemporalDurationNode.executeDynamicObject(temporalDurationLike, TemporalUtil.listEmpty);
            TemporalUtil.rejectDurationSign(duration.getYears(), duration.getMonths(), duration.getWeeks(), duration.getDays(), duration.getHours(), duration.getMinutes(), duration.getSeconds(), duration.getMilliseconds(), duration.getMicroseconds(), duration.getNanoseconds());
            JSDynamicObject options = this.getOptionsObject(optParam);
            JSTemporalDateTimeRecord result = TemporalUtil.addDateTime(this.getContext(), dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(), dateTime.getMillisecond(), dateTime.getMicrosecond(), dateTime.getNanosecond(), dateTime.getCalendar(), (double)sign * duration.getYears(), (double)sign * duration.getMonths(), (double)sign * duration.getWeeks(), (double)sign * duration.getDays(), (double)sign * duration.getHours(), (double)sign * duration.getMinutes(), (double)sign * duration.getSeconds(), (double)sign * duration.getMilliseconds(), (double)sign * duration.getMicroseconds(), (double)sign * duration.getNanoseconds(), options);
            return JSTemporalPlainDateTime.create(this.getContext(), result.getYear(), result.getMonth(), result.getDay(), result.getHour(), result.getMinute(), result.getSecond(), result.getMillisecond(), result.getMicrosecond(), result.getNanosecond(), dateTime.getCalendar(), this.errorBranch);
        }

        protected JSTemporalDurationObject differenceTemporalPlainDateTime(int sign, JSTemporalPlainDateTimeObject dateTime, Object otherObj, Object optionsParam, JSToNumberNode toNumber, EnumerableOwnPropertyNamesNode namesNode, ToTemporalDateTimeNode toTemporalDateTime, JSToStringNode toStringNode, TruffleString.EqualNode equalNode, TemporalRoundDurationNode roundDurationNode) {
            JSTemporalPlainDateTimeObject other = (JSTemporalPlainDateTimeObject)toTemporalDateTime.executeDynamicObject(otherObj, Undefined.instance);
            if (!TemporalUtil.calendarEquals(dateTime.getCalendar(), other.getCalendar(), toStringNode)) {
                this.errorBranch.enter();
                throw TemporalErrors.createRangeErrorIdenticalCalendarExpected();
            }
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            TemporalUtil.Unit smallestUnit = this.toSmallestTemporalUnit(options, TemporalUtil.listEmpty, TemporalConstants.NANOSECOND, equalNode);
            TemporalUtil.Unit defaultLargestUnit = TemporalUtil.largerOfTwoTemporalUnits(TemporalUtil.Unit.DAY, smallestUnit);
            TemporalUtil.Unit largestUnit = this.toLargestTemporalUnit(options, TemporalUtil.listEmpty, TemporalConstants.AUTO, defaultLargestUnit, equalNode);
            TemporalUtil.validateTemporalUnitRange(largestUnit, smallestUnit);
            TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(options, TemporalConstants.TRUNC, equalNode);
            if (sign == -1) {
                roundingMode = TemporalUtil.negateTemporalRoundingMode(roundingMode);
            }
            Double maximum = TemporalUtil.maximumTemporalDurationRoundingIncrement(smallestUnit);
            double roundingIncrement = TemporalUtil.toTemporalRoundingIncrement(options, maximum, false, this.isObjectNode, toNumber);
            JSTemporalDurationRecord diff = TemporalUtil.differenceISODateTime(this.getContext(), namesNode, dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(), dateTime.getMillisecond(), dateTime.getMicrosecond(), dateTime.getNanosecond(), other.getYear(), other.getMonth(), other.getDay(), other.getHour(), other.getMinute(), other.getSecond(), other.getMillisecond(), other.getMicrosecond(), other.getNanosecond(), dateTime.getCalendar(), largestUnit, options);
            JSTemporalPlainDateObject relativeTo = JSTemporalPlainDate.create(this.getContext(), dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getCalendar(), this.errorBranch);
            JSTemporalDurationRecord roundResult = roundDurationNode.execute(diff.getYears(), diff.getMonths(), diff.getWeeks(), diff.getDays(), diff.getHours(), diff.getMinutes(), diff.getSeconds(), diff.getMilliseconds(), diff.getMicroseconds(), diff.getNanoseconds(), (long)roundingIncrement, smallestUnit, roundingMode, relativeTo);
            JSTemporalDurationRecord result = TemporalUtil.balanceDuration(this.getContext(), namesNode, roundResult.getDays(), roundResult.getHours(), roundResult.getMinutes(), roundResult.getSeconds(), roundResult.getMilliseconds(), roundResult.getMicroseconds(), roundResult.getNanoseconds(), largestUnit);
            return JSTemporalDuration.createTemporalDuration(this.getContext(), (double)sign * roundResult.getYears(), (double)sign * roundResult.getMonths(), (double)sign * roundResult.getWeeks(), (double)sign * result.getDays(), (double)sign * result.getHours(), (double)sign * result.getMinutes(), (double)sign * result.getSeconds(), (double)sign * result.getMilliseconds(), (double)sign * result.getMicroseconds(), (double)sign * result.getNanoseconds(), this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainDateTimeGetterNode
    extends JSBuiltinNode {
        public final TemporalPlainDateTimePrototype property;

        public JSTemporalPlainDateTimeGetterNode(JSContext context, JSBuiltin builtin, TemporalPlainDateTimePrototype property) {
            super(context, builtin);
            this.property = property;
        }

        @Specialization(guards={"isJSTemporalDateTime(thisObj)"})
        protected Object dateTimeGetter(Object thisObj, @Cached(value="create(getContext())") TemporalCalendarGetterNode calendarGetterNode) {
            JSTemporalPlainDateTimeObject temporalDT = (JSTemporalPlainDateTimeObject)thisObj;
            switch (this.property) {
                case calendar: {
                    return temporalDT.getCalendar();
                }
                case hour: {
                    return temporalDT.getHour();
                }
                case minute: {
                    return temporalDT.getMinute();
                }
                case second: {
                    return temporalDT.getSecond();
                }
                case millisecond: {
                    return temporalDT.getMillisecond();
                }
                case microsecond: {
                    return temporalDT.getMicrosecond();
                }
                case nanosecond: {
                    return temporalDT.getNanosecond();
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

        @Specialization(guards={"!isJSTemporalDateTime(thisObj)"})
        protected static int error(Object thisObj) {
            throw TemporalErrors.createTypeErrorTemporalDateTimeExpected();
        }
    }

    public static enum TemporalPlainDateTimePrototype implements BuiltinEnum<TemporalPlainDateTimePrototype>
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
        hour(0),
        minute(0),
        second(0),
        millisecond(0),
        microsecond(0),
        nanosecond(0),
        with(1),
        withPlainTime(0),
        withPlainDate(1),
        withCalendar(1),
        add(1),
        subtract(1),
        until(1),
        since(1),
        round(1),
        equals(1),
        toString(0),
        toLocaleString(0),
        toJSON(0),
        valueOf(0),
        toPlainDate(0),
        toPlainYearMonth(0),
        toPlainMonthDay(0),
        toPlainTime(0),
        toZonedDateTime(1),
        getISOFields(0);

        private final int length;

        private TemporalPlainDateTimePrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isGetter() {
            return EnumSet.of(calendar, new TemporalPlainDateTimePrototype[]{hour, minute, second, millisecond, microsecond, nanosecond, year, month, monthCode, day, dayOfYear, dayOfWeek, weekOfYear, daysInWeek, daysInMonth, daysInYear, monthsInYear, inLeapYear}).contains(this);
        }
    }
}

