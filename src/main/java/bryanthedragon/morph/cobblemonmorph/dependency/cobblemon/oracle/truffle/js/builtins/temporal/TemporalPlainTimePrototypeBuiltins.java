
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainTimePrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerThrowOnInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstantObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPrecisionRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.TemporalTime;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.EnumSet;

public class TemporalPlainTimePrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalPlainTimePrototype> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalPlainTimePrototypeBuiltins();

    protected TemporalPlainTimePrototypeBuiltins() {
        super(JSTemporalPlainTime.PROTOTYPE_NAME, TemporalPlainTimePrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalPlainTimePrototype builtinEnum) {
        switch (builtinEnum) {
            case calendar: 
            case hour: 
            case minute: 
            case second: 
            case millisecond: 
            case microsecond: 
            case nanosecond: {
                return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeGetterNodeGen.create(context, builtin, builtinEnum, TemporalPlainTimePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case add: {
                return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeAddNodeGen.create(context, builtin, TemporalPlainTimePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case subtract: {
                return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeSubtractNodeGen.create(context, builtin, TemporalPlainTimePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case with: {
                return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeWithNodeGen.create(context, builtin, TemporalPlainTimePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case until: {
                return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeUntilNodeGen.create(context, builtin, TemporalPlainTimePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case since: {
                return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeSinceNodeGen.create(context, builtin, TemporalPlainTimePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case round: {
                return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeRoundNodeGen.create(context, builtin, TemporalPlainTimePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case equals: {
                return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeEqualsNodeGen.create(context, builtin, TemporalPlainTimePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toPlainDateTime: {
                return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeToPlainDateTimeNodeGen.create(context, builtin, TemporalPlainTimePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toZonedDateTime: {
                return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeToZonedDateTimeNodeGen.create(context, builtin, TemporalPlainTimePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case getISOFields: {
                return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeGetISOFieldsNodeGen.create(context, builtin, TemporalPlainTimePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toString: {
                return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeToStringNodeGen.create(context, builtin, TemporalPlainTimePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toLocaleString: 
            case toJSON: {
                return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeToLocaleStringNodeGen.create(context, builtin, TemporalPlainTimePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case valueOf: {
                return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeValueOfNodeGen.create(context, builtin, TemporalPlainTimePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalPlainTimeValueOf
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainTimeValueOf(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object valueOf(Object thisObj) {
            throw Errors.createTypeError("Not supported.");
        }
    }

    public static abstract class JSTemporalPlainTimeToLocaleString
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainTimeToLocaleString(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public TruffleString toLocaleString(Object thisObj) {
            TemporalTime time = this.requireTemporalTime(thisObj);
            return JSTemporalPlainTime.temporalTimeToString(time.getHour(), time.getMinute(), time.getSecond(), time.getMillisecond(), time.getMicrosecond(), time.getNanosecond(), TemporalConstants.AUTO);
        }
    }

    public static abstract class JSTemporalPlainTimeToString
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainTimeToString(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected TruffleString toString(Object thisObj, Object optionsParam, @Cached JSToStringNode toStringNode, @Cached TruffleString.EqualNode equalNode) {
            TemporalTime time = this.requireTemporalTime(thisObj);
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            JSTemporalPrecisionRecord precision = TemporalUtil.toSecondsStringPrecision(options, toStringNode, this.getOptionNode(), equalNode);
            TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(options, TemporalConstants.TRUNC, equalNode);
            JSTemporalDurationRecord roundResult = TemporalUtil.roundTime(time.getHour(), time.getMinute(), time.getSecond(), time.getMillisecond(), time.getMicrosecond(), time.getNanosecond(), precision.getIncrement(), precision.getUnit(), roundingMode, null);
            return JSTemporalPlainTime.temporalTimeToString(TemporalUtil.dtol(roundResult.getHours()), TemporalUtil.dtol(roundResult.getMinutes()), TemporalUtil.dtol(roundResult.getSeconds()), TemporalUtil.dtol(roundResult.getMilliseconds()), TemporalUtil.dtol(roundResult.getMicroseconds()), TemporalUtil.dtol(roundResult.getNanoseconds()), precision.getPrecision());
        }
    }

    public static abstract class JSTemporalPlainTimeGetISOFields
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainTimeGetISOFields(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object getISOFields(Object thisObj) {
            TemporalTime time = this.requireTemporalTime(thisObj);
            JSObject fields = JSOrdinary.create(this.getContext(), this.getRealm());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.CALENDAR, time.getCalendar());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.ISO_HOUR, time.getHour());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.ISO_MICROSECOND, time.getMicrosecond());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.ISO_MILLISECOND, time.getMillisecond());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.ISO_MINUTE, time.getMinute());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.ISO_NANOSECOND, time.getNanosecond());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.ISO_SECOND, time.getSecond());
            return fields;
        }
    }

    public static abstract class JSTemporalPlainTimeToZonedDateTime
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainTimeToZonedDateTime(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject toZonedDateTime(Object thisObj, Object itemParam, @Cached(value="create(getContext())") ToTemporalDateNode toTemporalDate, @Cached(value="create(getContext())") ToTemporalTimeZoneNode toTemporalTimeZone) {
            TemporalTime time = this.requireTemporalTime(thisObj);
            if (!JSRuntime.isObject(itemParam)) {
                throw Errors.createTypeErrorNotAnObject(itemParam);
            }
            JSDynamicObject item = (JSDynamicObject)itemParam;
            Object temporalDateLike = JSObject.get(item, TemporalConstants.PLAIN_DATE);
            if (temporalDateLike == Undefined.instance) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalDateExpected();
            }
            JSTemporalPlainDateObject date = toTemporalDate.executeDynamicObject(temporalDateLike, Undefined.instance);
            Object temporalTimeZoneLike = JSObject.get(item, TemporalConstants.TIME_ZONE);
            if (temporalTimeZoneLike == Undefined.instance || temporalTimeZoneLike == null) {
                this.errorBranch.enter();
                throw Errors.createTypeError("TimeZone expected");
            }
            JSDynamicObject timeZone = toTemporalTimeZone.executeDynamicObject(temporalTimeZoneLike);
            JSTemporalPlainDateTimeObject temporalDateTime = JSTemporalPlainDateTime.create(this.getContext(), date.getYear(), date.getMonth(), date.getDay(), time.getHour(), time.getMinute(), time.getSecond(), time.getMillisecond(), time.getMicrosecond(), time.getNanosecond(), date.getCalendar(), this.errorBranch);
            JSTemporalInstantObject instant = TemporalUtil.builtinTimeZoneGetInstantFor(this.getContext(), timeZone, temporalDateTime, TemporalUtil.Disambiguation.COMPATIBLE);
            return JSTemporalZonedDateTime.create(this.getContext(), this.getRealm(), instant.getNanoseconds(), timeZone, date.getCalendar());
        }
    }

    public static abstract class JSTemporalPlainTimeToPlainDateTime
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainTimeToPlainDateTime(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject toPlainDateTime(Object thisObj, Object temporalDateObj, @Cached(value="create(getContext())") ToTemporalDateNode toTemporalDate) {
            JSTemporalPlainDateObject temporalDate;
            TemporalTime time = this.requireTemporalTime(thisObj);
            JSTemporalPlainDateObject date = temporalDate = toTemporalDate.executeDynamicObject(temporalDateObj, Undefined.instance);
            return JSTemporalPlainDateTime.create(this.getContext(), date.getYear(), date.getMonth(), date.getDay(), time.getHour(), time.getMinute(), time.getSecond(), time.getMillisecond(), time.getMicrosecond(), time.getNanosecond(), date.getCalendar(), this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainTimeEquals
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainTimeEquals(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSTemporalTime(otherObj)"})
        protected boolean equalsOtherObj(Object thisObj, JSDynamicObject otherObj) {
            TemporalTime temporalTime = this.requireTemporalTime(thisObj);
            return JSTemporalPlainTimeEquals.equalsIntl(temporalTime, (TemporalTime)((Object)otherObj));
        }

        @Specialization(guards={"!isJSTemporalTime(other)"})
        protected boolean equalsGeneric(Object thisObj, Object other, @Cached(value="create(getContext())") ToTemporalTimeNode toTemporalTime) {
            TemporalTime temporalTime = this.requireTemporalTime(thisObj);
            TemporalTime otherTime = (TemporalTime)((Object)toTemporalTime.executeDynamicObject(other, null));
            return JSTemporalPlainTimeEquals.equalsIntl(temporalTime, otherTime);
        }

        private static boolean equalsIntl(TemporalTime thisTime, TemporalTime otherTime) {
            if (thisTime.getHour() != otherTime.getHour()) {
                return false;
            }
            if (thisTime.getMinute() != otherTime.getMinute()) {
                return false;
            }
            if (thisTime.getSecond() != otherTime.getSecond()) {
                return false;
            }
            if (thisTime.getMillisecond() != otherTime.getMillisecond()) {
                return false;
            }
            if (thisTime.getMicrosecond() != otherTime.getMicrosecond()) {
                return false;
            }
            return thisTime.getNanosecond() == otherTime.getNanosecond();
        }
    }

    public static abstract class JSTemporalPlainTimeRound
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainTimeRound(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject round(Object thisObj, Object roundToParam, @Cached(value="create()") JSToNumberNode toNumber, @Cached TruffleString.EqualNode equalNode) {
            JSDynamicObject roundTo;
            TemporalTime temporalTime = this.requireTemporalTime(thisObj);
            if (roundToParam == Undefined.instance) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorOptionsUndefined();
            }
            if (Strings.isTString(roundToParam)) {
                roundTo = JSOrdinary.createWithNullPrototype(this.getContext());
                JSRuntime.createDataPropertyOrThrow(roundTo, TemporalConstants.SMALLEST_UNIT, JSRuntime.toStringIsString(roundToParam));
            } else {
                roundTo = this.getOptionsObject(roundToParam);
            }
            TemporalUtil.Unit smallestUnit = this.toSmallestTemporalUnit(roundTo, TemporalUtil.listYMWD, null, equalNode);
            if (smallestUnit == TemporalUtil.Unit.EMPTY) {
                this.errorBranch.enter();
                throw TemporalErrors.createRangeErrorSmallestUnitExpected();
            }
            TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(roundTo, TemporalConstants.HALF_EXPAND, equalNode);
            int maximum = smallestUnit == TemporalUtil.Unit.HOUR ? 24 : (smallestUnit == TemporalUtil.Unit.MINUTE || smallestUnit == TemporalUtil.Unit.SECOND ? 60 : 1000);
            double roundingIncrement = TemporalUtil.toTemporalRoundingIncrement(roundTo, Double.valueOf(maximum), false, this.isObjectNode, toNumber);
            JSTemporalDurationRecord result = TemporalUtil.roundTime(temporalTime.getHour(), temporalTime.getMinute(), temporalTime.getSecond(), temporalTime.getMillisecond(), temporalTime.getMicrosecond(), temporalTime.getNanosecond(), roundingIncrement, smallestUnit, roundingMode, null);
            return JSTemporalPlainTime.create(this.getContext(), TemporalUtil.dtoi(result.getHours()), TemporalUtil.dtoi(result.getMinutes()), TemporalUtil.dtoi(result.getSeconds()), TemporalUtil.dtoi(result.getMilliseconds()), TemporalUtil.dtoi(result.getMicroseconds()), TemporalUtil.dtoi(result.getNanoseconds()), this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainTimeSince
    extends PlainTimeOperation {
        protected JSTemporalPlainTimeSince(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject since(Object thisObj, Object otherObj, Object optionsParam, @Cached(value="create()") JSToNumberNode toNumber, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode, @Cached(value="create(getContext())") ToTemporalTimeNode toTemporalTime, @Cached TruffleString.EqualNode equalNode, @Cached(value="create(getContext())") TemporalRoundDurationNode roundDurationNode) {
            TemporalTime temporalTime = this.requireTemporalTime(thisObj);
            return this.differenceTemporalPlainTime(-1, temporalTime, otherObj, optionsParam, toNumber, namesNode, toTemporalTime, equalNode, roundDurationNode);
        }
    }

    public static abstract class JSTemporalPlainTimeUntil
    extends PlainTimeOperation {
        protected JSTemporalPlainTimeUntil(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject until(Object thisObj, Object otherObj, Object optionsParam, @Cached(value="create()") JSToNumberNode toNumber, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode, @Cached(value="create(getContext())") ToTemporalTimeNode toTemporalTime, @Cached TruffleString.EqualNode equalNode, @Cached(value="create(getContext())") TemporalRoundDurationNode roundDurationNode) {
            TemporalTime temporalTime = this.requireTemporalTime(thisObj);
            return this.differenceTemporalPlainTime(1, temporalTime, otherObj, optionsParam, toNumber, namesNode, toTemporalTime, equalNode, roundDurationNode);
        }
    }

    public static abstract class JSTemporalPlainTimeWith
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainTimeWith(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject with(Object thisObj, Object temporalTimeLike, Object options, @Cached(value="create()") JSToIntegerThrowOnInfinityNode toIntThrows, @Cached(value="create()") JSToIntegerAsIntNode toInt) {
            TemporalTime temporalTime = this.requireTemporalTime(thisObj);
            if (!this.isObject(temporalTimeLike)) {
                this.errorBranch.enter();
                throw Errors.createTypeError("Temporal.Time like object expected.");
            }
            JSDynamicObject timeLikeObj = (JSDynamicObject)temporalTimeLike;
            TemporalUtil.rejectTemporalCalendarType(timeLikeObj, this.errorBranch);
            Object calendarProperty = JSObject.get(timeLikeObj, TemporalConstants.CALENDAR);
            if (calendarProperty != Undefined.instance) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorUnexpectedCalendar();
            }
            Object timeZoneProperty = JSObject.get(timeLikeObj, TemporalConstants.TIME_ZONE);
            if (timeZoneProperty != Undefined.instance) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorUnexpectedTimeZone();
            }
            JSDynamicObject partialTime = JSTemporalPlainTime.toPartialTime(timeLikeObj, this.isObjectNode, toIntThrows, this.getContext());
            JSDynamicObject normalizedOptions = this.getOptionsObject(options);
            TemporalUtil.Overflow overflow = TemporalUtil.toTemporalOverflow(normalizedOptions, this.getOptionNode());
            Object tempValue = JSObject.get(partialTime, TemporalConstants.HOUR);
            int hour = tempValue != Undefined.instance ? toInt.executeInt(tempValue) : temporalTime.getHour();
            tempValue = JSObject.get(partialTime, TemporalConstants.MINUTE);
            int minute = tempValue != Undefined.instance ? toInt.executeInt(tempValue) : temporalTime.getMinute();
            tempValue = JSObject.get(partialTime, TemporalConstants.SECOND);
            int second = tempValue != Undefined.instance ? toInt.executeInt(tempValue) : temporalTime.getSecond();
            tempValue = JSObject.get(partialTime, TemporalConstants.MILLISECOND);
            int millisecond = tempValue != Undefined.instance ? toInt.executeInt(tempValue) : temporalTime.getMillisecond();
            tempValue = JSObject.get(partialTime, TemporalConstants.MICROSECOND);
            int microsecond = tempValue != Undefined.instance ? toInt.executeInt(tempValue) : temporalTime.getMicrosecond();
            tempValue = JSObject.get(partialTime, TemporalConstants.NANOSECOND);
            int nanosecond = tempValue != Undefined.instance ? toInt.executeInt(tempValue) : temporalTime.getNanosecond();
            JSTemporalDurationRecord result = TemporalUtil.regulateTime(hour, minute, second, millisecond, microsecond, nanosecond, overflow);
            return JSTemporalPlainTime.create(this.getContext(), TemporalUtil.dtoi(result.getHours()), TemporalUtil.dtoi(result.getMinutes()), TemporalUtil.dtoi(result.getSeconds()), TemporalUtil.dtoi(result.getMilliseconds()), TemporalUtil.dtoi(result.getMicroseconds()), TemporalUtil.dtoi(result.getNanoseconds()), this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainTimeSubtract
    extends PlainTimeOperation {
        protected JSTemporalPlainTimeSubtract(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject subtract(Object thisObj, Object temporalDurationLike, @Cached(value="create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
            TemporalTime temporalTime = this.requireTemporalTime(thisObj);
            return this.addDurationToOrSubtractDurationFromPlainTime(-1, temporalTime, temporalDurationLike, toLimitedTemporalDurationNode);
        }
    }

    public static abstract class JSTemporalPlainTimeAdd
    extends PlainTimeOperation {
        protected JSTemporalPlainTimeAdd(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject add(Object thisObj, Object temporalDurationLike, @Cached(value="create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
            TemporalTime temporalTime = this.requireTemporalTime(thisObj);
            return this.addDurationToOrSubtractDurationFromPlainTime(1, temporalTime, temporalDurationLike, toLimitedTemporalDurationNode);
        }
    }

    public static abstract class PlainTimeOperation
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        public PlainTimeOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected JSTemporalPlainTimeObject addDurationToOrSubtractDurationFromPlainTime(int sign, TemporalTime temporalTime, Object temporalDurationLike, ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
            JSTemporalDurationRecord duration = toLimitedTemporalDurationNode.executeDynamicObject(temporalDurationLike, TemporalUtil.listEmpty);
            JSTemporalDurationRecord result = TemporalUtil.addTimeDouble(temporalTime.getHour(), temporalTime.getMinute(), temporalTime.getSecond(), temporalTime.getMillisecond(), temporalTime.getMicrosecond(), temporalTime.getNanosecond(), (double)sign * duration.getHours(), (double)sign * duration.getMinutes(), (double)sign * duration.getSeconds(), (double)sign * duration.getMilliseconds(), (double)sign * duration.getMicroseconds(), (double)sign * duration.getNanoseconds());
            assert (TemporalUtil.isValidTime(TemporalUtil.dtoi(result.getHours()), TemporalUtil.dtoi(result.getMinutes()), TemporalUtil.dtoi(result.getSeconds()), TemporalUtil.dtoi(result.getMilliseconds()), TemporalUtil.dtoi(result.getMicroseconds()), TemporalUtil.dtoi(result.getNanoseconds())));
            return JSTemporalPlainTime.create(this.getContext(), TemporalUtil.dtoi(result.getHours()), TemporalUtil.dtoi(result.getMinutes()), TemporalUtil.dtoi(result.getSeconds()), TemporalUtil.dtoi(result.getMilliseconds()), TemporalUtil.dtoi(result.getMicroseconds()), TemporalUtil.dtoi(result.getNanoseconds()), this.errorBranch);
        }

        protected JSTemporalDurationObject differenceTemporalPlainTime(int sign, TemporalTime temporalTime, Object otherObj, Object optionsParam, JSToNumberNode toNumber, EnumerableOwnPropertyNamesNode namesNode, ToTemporalTimeNode toTemporalTime, TruffleString.EqualNode equalNode, TemporalRoundDurationNode roundDurationNode) {
            JSTemporalPlainTimeObject other = (JSTemporalPlainTimeObject)toTemporalTime.executeDynamicObject(otherObj, null);
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            TemporalUtil.Unit smallestUnit = this.toSmallestTemporalUnit(options, TemporalUtil.listYMWD, TemporalConstants.NANOSECOND, equalNode);
            TemporalUtil.Unit largestUnit = this.toLargestTemporalUnit(options, TemporalUtil.listYMWD, TemporalConstants.AUTO, TemporalUtil.Unit.HOUR, equalNode);
            TemporalUtil.validateTemporalUnitRange(largestUnit, smallestUnit);
            TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(options, TemporalConstants.TRUNC, equalNode);
            if (sign == -1) {
                roundingMode = TemporalUtil.negateTemporalRoundingMode(roundingMode);
            }
            Double maximum = TemporalUtil.maximumTemporalDurationRoundingIncrement(smallestUnit);
            long roundingIncrement = (long)TemporalUtil.toTemporalRoundingIncrement(options, maximum, false, this.isObjectNode, toNumber);
            JSTemporalDurationRecord result = TemporalUtil.differenceTime(temporalTime.getHour(), temporalTime.getMinute(), temporalTime.getSecond(), temporalTime.getMillisecond(), temporalTime.getMicrosecond(), temporalTime.getNanosecond(), other.getHour(), other.getMinute(), other.getSecond(), other.getMillisecond(), other.getMicrosecond(), other.getNanosecond());
            JSTemporalDurationRecord result2 = roundDurationNode.execute(0.0, 0.0, 0.0, 0.0, result.getHours(), result.getMinutes(), result.getSeconds(), result.getMilliseconds(), result.getMicroseconds(), result.getNanoseconds(), roundingIncrement, smallestUnit, roundingMode, Undefined.instance);
            JSTemporalDurationRecord result3 = TemporalUtil.balanceDuration(this.getContext(), namesNode, 0.0, result2.getHours(), result2.getMinutes(), result2.getSeconds(), result2.getMilliseconds(), result2.getMicroseconds(), result2.getNanoseconds(), largestUnit);
            return JSTemporalDuration.createTemporalDuration(this.getContext(), 0.0, 0.0, 0.0, 0.0, (double)sign * result3.getHours(), (double)sign * result3.getMinutes(), (double)sign * result3.getSeconds(), (double)sign * result3.getMilliseconds(), (double)sign * result3.getMicroseconds(), (double)sign * result3.getNanoseconds(), this.errorBranch);
        }
    }

    public static abstract class JSTemporalPlainTimeGetterNode
    extends JSBuiltinNode {
        public final TemporalPlainTimePrototype property;

        public JSTemporalPlainTimeGetterNode(JSContext context, JSBuiltin builtin, TemporalPlainTimePrototype property) {
            super(context, builtin);
            this.property = property;
        }

        @Specialization(guards={"isJSTemporalTime(thisObj)"})
        protected Object timeGetter(Object thisObj) {
            TemporalTime temporalTime = (TemporalTime)thisObj;
            switch (this.property) {
                case calendar: {
                    return temporalTime.getCalendar();
                }
                case hour: {
                    return temporalTime.getHour();
                }
                case minute: {
                    return temporalTime.getMinute();
                }
                case second: {
                    return temporalTime.getSecond();
                }
                case millisecond: {
                    return temporalTime.getMillisecond();
                }
                case microsecond: {
                    return temporalTime.getMicrosecond();
                }
                case nanosecond: {
                    return temporalTime.getNanosecond();
                }
            }
            CompilerDirectives.transferToInterpreter();
            throw Errors.shouldNotReachHere();
        }

        @Specialization(guards={"!isJSTemporalTime(thisObj)"})
        protected static int error(Object thisObj) {
            throw TemporalErrors.createTypeErrorTemporalDateTimeExpected();
        }
    }

    public static enum TemporalPlainTimePrototype implements BuiltinEnum<TemporalPlainTimePrototype>
    {
        calendar(0),
        hour(0),
        minute(0),
        second(0),
        millisecond(0),
        microsecond(0),
        nanosecond(0),
        add(1),
        subtract(1),
        with(1),
        until(1),
        since(1),
        round(1),
        equals(1),
        toPlainDateTime(1),
        toZonedDateTime(1),
        getISOFields(0),
        toString(0),
        toLocaleString(0),
        toJSON(0),
        valueOf(0);

        private final int length;

        private TemporalPlainTimePrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isGetter() {
            return EnumSet.of(calendar, new TemporalPlainTimePrototype[]{hour, minute, second, millisecond, microsecond, nanosecond}).contains(this);
        }
    }
}

