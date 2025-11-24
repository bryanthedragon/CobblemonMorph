
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalInstantPrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalInstantNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendarObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstant;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstantObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPrecisionRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTime;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.math.BigInteger;
import java.util.EnumSet;

public class TemporalInstantPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalInstantPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalInstantPrototypeBuiltins();

    protected TemporalInstantPrototypeBuiltins() {
        super(JSTemporalInstant.PROTOTYPE_NAME, TemporalInstantPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalInstantPrototype builtinEnum) {
        switch (builtinEnum) {
            case epochSeconds: 
            case epochMilliseconds: 
            case epochMicroseconds: 
            case epochNanoseconds: {
                return TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantGetterNodeGen.create(context, builtin, builtinEnum, TemporalInstantPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case add: {
                return TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantAddNodeGen.create(context, builtin, TemporalInstantPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case subtract: {
                return TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantSubtractNodeGen.create(context, builtin, TemporalInstantPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case until: {
                return TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantUntilSinceNodeGen.create(context, builtin, true, TemporalInstantPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case since: {
                return TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantUntilSinceNodeGen.create(context, builtin, false, TemporalInstantPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case round: {
                return TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantRoundNodeGen.create(context, builtin, TemporalInstantPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case equals: {
                return TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantEqualsNodeGen.create(context, builtin, TemporalInstantPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toString: {
                return TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantToStringNodeGen.create(context, builtin, TemporalInstantPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toLocaleString: 
            case toJSON: {
                return TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantToLocaleStringNodeGen.create(context, builtin, TemporalInstantPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case valueOf: {
                return TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantValueOfNodeGen.create(context, builtin, TemporalInstantPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toZonedDateTime: {
                return TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantToZonedDateTimeNodeGen.create(context, builtin, TemporalInstantPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toZonedDateTimeISO: {
                return TemporalInstantPrototypeBuiltinsFactory.JSTemporalInstantToZonedDateTimeISONodeGen.create(context, builtin, TemporalInstantPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalInstantToZonedDateTimeISONode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalInstantToZonedDateTimeISONode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object toZonedDateTimeISO(Object thisObj, Object itemParam, @Cached(value="create(getContext())") ToTemporalTimeZoneNode toTemporalTimeZone) {
            JSDynamicObject itemObj;
            Object timeZoneProperty;
            JSTemporalInstantObject instant = this.requireTemporalInstant(thisObj);
            Object item = itemParam;
            if (this.isObject(item) && (timeZoneProperty = JSObject.get(itemObj = TemporalUtil.toJSDynamicObject(item, this.errorBranch), TemporalConstants.TIME_ZONE)) != Undefined.instance) {
                item = timeZoneProperty;
            }
            JSDynamicObject timeZone = toTemporalTimeZone.executeDynamicObject(item);
            JSRealm realm = this.getRealm();
            JSTemporalCalendarObject calendar = TemporalUtil.getISO8601Calendar(this.getContext(), realm, this.errorBranch);
            return JSTemporalZonedDateTime.create(this.getContext(), realm, instant.getNanoseconds(), timeZone, calendar);
        }
    }

    public static abstract class JSTemporalInstantToZonedDateTimeNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalInstantToZonedDateTimeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject toZonedDateTime(Object thisObj, Object item, @Cached(value="create(getContext())") ToTemporalCalendarNode toTemporalCalendar, @Cached(value="create(getContext())") ToTemporalTimeZoneNode toTemporalTimeZone) {
            JSTemporalInstantObject instant = this.requireTemporalInstant(thisObj);
            if (!this.isObject(item)) {
                this.errorBranch.enter();
                throw Errors.createTypeError("object expected");
            }
            JSDynamicObject itemObj = TemporalUtil.toJSDynamicObject(item, this.errorBranch);
            Object calendarLike = JSObject.get(itemObj, TemporalConstants.CALENDAR);
            if (calendarLike == Undefined.instance) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalCalendarExpected();
            }
            JSDynamicObject calendar = toTemporalCalendar.executeDynamicObject(calendarLike);
            Object timeZoneLike = JSObject.get(itemObj, TemporalConstants.TIME_ZONE);
            if (timeZoneLike == Undefined.instance) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalTimeZoneExpected();
            }
            JSDynamicObject timeZone = toTemporalTimeZone.executeDynamicObject(timeZoneLike);
            return JSTemporalZonedDateTime.create(this.getContext(), this.getRealm(), instant.getNanoseconds(), timeZone, calendar);
        }
    }

    public static abstract class JSTemporalInstantValueOf
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalInstantValueOf(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object valueOf(Object thisObj) {
            throw Errors.createTypeError("Not supported.");
        }
    }

    public static abstract class JSTemporalInstantToLocaleString
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalInstantToLocaleString(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public TruffleString toLocaleString(Object thisObj) {
            JSTemporalInstantObject instant = this.requireTemporalInstant(thisObj);
            return TemporalUtil.temporalInstantToString(this.getContext(), this.getRealm(), instant, Undefined.instance, TemporalConstants.AUTO);
        }
    }

    public static abstract class JSTemporalInstantToString
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalInstantToString(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected TruffleString toString(Object thisObj, Object optionsParam, @Cached(value="create(getContext())") ToTemporalTimeZoneNode toTemporalTimeZone, @Cached JSToStringNode toStringNode, @Cached TruffleString.EqualNode equalNode) {
            JSTemporalInstantObject instant = this.requireTemporalInstant(thisObj);
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            Object timeZoneRaw = JSObject.get(options, TemporalConstants.TIME_ZONE);
            JSDynamicObject timeZone = Undefined.instance;
            if (timeZoneRaw != Undefined.instance) {
                timeZone = toTemporalTimeZone.executeDynamicObject(timeZoneRaw);
            }
            JSTemporalPrecisionRecord precision = TemporalUtil.toSecondsStringPrecision(options, toStringNode, this.getOptionNode(), equalNode);
            TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(options, TemporalConstants.TRUNC, equalNode);
            BigInt ns = instant.getNanoseconds();
            BigInteger roundedNs = TemporalUtil.roundTemporalInstant(ns, (double)((long)precision.getIncrement()), precision.getUnit(), roundingMode);
            JSRealm realm = this.getRealm();
            JSTemporalInstantObject roundedInstant = JSTemporalInstant.create(this.getContext(), realm, new BigInt(roundedNs));
            return TemporalUtil.temporalInstantToString(this.getContext(), realm, roundedInstant, timeZone, precision.getPrecision());
        }
    }

    public static abstract class JSTemporalInstantEquals
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalInstantEquals(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public boolean equals(Object thisObj, Object otherObj, @Cached(value="create(getContext())") ToTemporalInstantNode toTemporalInstantNode) {
            JSTemporalInstantObject instant = this.requireTemporalInstant(thisObj);
            JSTemporalInstantObject other = toTemporalInstantNode.execute(otherObj);
            return instant.getNanoseconds().compareTo(other.getNanoseconds()) == 0;
        }
    }

    public static abstract class JSTemporalInstantRound
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalInstantRound(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject round(Object thisObj, Object roundToParam, @Cached(value="create()") JSToNumberNode toNumber, @Cached TruffleString.EqualNode equalNode) {
            double maximum;
            JSDynamicObject roundTo;
            JSTemporalInstantObject instant = this.requireTemporalInstant(thisObj);
            if (roundToParam == Undefined.instance) {
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
            if (TemporalUtil.Unit.HOUR == smallestUnit) {
                maximum = 24.0;
            } else if (TemporalUtil.Unit.MINUTE == smallestUnit) {
                maximum = 1440.0;
            } else if (TemporalUtil.Unit.SECOND == smallestUnit) {
                maximum = 86400.0;
            } else if (TemporalUtil.Unit.MILLISECOND == smallestUnit) {
                maximum = 8.64E7;
            } else if (TemporalUtil.Unit.MICROSECOND == smallestUnit) {
                maximum = 8.64E10;
            } else {
                assert (TemporalUtil.Unit.NANOSECOND == smallestUnit);
                maximum = 8.64E13;
            }
            double roundingIncrement = TemporalUtil.toTemporalRoundingIncrement(roundTo, maximum, true, this.isObjectNode, toNumber);
            BigInteger roundedNs = TemporalUtil.roundTemporalInstant(instant.getNanoseconds(), (double)((long)roundingIncrement), smallestUnit, roundingMode);
            return JSTemporalInstant.create(this.getContext(), this.getRealm(), new BigInt(roundedNs));
        }
    }

    public static abstract class JSTemporalInstantUntilSinceNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        private final boolean isUntil;

        protected JSTemporalInstantUntilSinceNode(JSContext context, JSBuiltin builtin, boolean isUntil) {
            super(context, builtin);
            this.isUntil = isUntil;
        }

        @Specialization
        public JSDynamicObject untilOrSince(Object thisObj, Object otherObj, Object optionsParam, @Cached(value="create()") JSToNumberNode toNumber, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode, @Cached TruffleString.EqualNode equalNode, @Cached(value="create(getContext())") ToTemporalInstantNode toTemporalInstantNode) {
            JSTemporalInstantObject instant = this.requireTemporalInstant(thisObj);
            JSTemporalInstantObject other = toTemporalInstantNode.execute(otherObj);
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            TemporalUtil.Unit smallestUnit = this.toSmallestTemporalUnit(options, TemporalUtil.listYMWD, TemporalConstants.NANOSECOND, equalNode);
            TemporalUtil.Unit defaultLargestUnit = TemporalUtil.largerOfTwoTemporalUnits(TemporalUtil.Unit.SECOND, smallestUnit);
            TemporalUtil.Unit largestUnit = this.toLargestTemporalUnit(options, TemporalUtil.listYMWD, TemporalConstants.AUTO, defaultLargestUnit, equalNode);
            TemporalUtil.validateTemporalUnitRange(largestUnit, smallestUnit);
            TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(options, TemporalConstants.TRUNC, equalNode);
            Double maximum = TemporalUtil.maximumTemporalDurationRoundingIncrement(smallestUnit);
            double roundingIncrement = TemporalUtil.toTemporalRoundingIncrement(options, maximum, false, this.isObjectNode, toNumber);
            BigInt one = this.isUntil ? instant.getNanoseconds() : other.getNanoseconds();
            BigInt two = this.isUntil ? other.getNanoseconds() : instant.getNanoseconds();
            BigInteger roundedNs = TemporalUtil.differenceInstant(one, two, roundingIncrement, smallestUnit, roundingMode);
            JSTemporalDurationRecord result = TemporalUtil.balanceDuration(this.getContext(), namesNode, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, roundedNs, largestUnit, Undefined.instance);
            return JSTemporalDuration.createTemporalDuration(this.getContext(), 0.0, 0.0, 0.0, 0.0, result.getHours(), result.getMinutes(), result.getSeconds(), result.getMilliseconds(), result.getMicroseconds(), result.getNanoseconds(), this.errorBranch);
        }
    }

    public static abstract class JSTemporalInstantSubtract
    extends InstantOperation {
        protected JSTemporalInstantSubtract(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject subtract(Object thisObj, Object temporalDurationLike, @Cached(value="create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
            JSTemporalInstantObject instant = this.requireTemporalInstant(thisObj);
            return this.addDurationToOrSubtractDurationFromInstant(-1, instant, temporalDurationLike, toLimitedTemporalDurationNode);
        }
    }

    public static abstract class JSTemporalInstantAdd
    extends InstantOperation {
        protected JSTemporalInstantAdd(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject add(Object thisObj, Object temporalDurationLike, @Cached(value="create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
            JSTemporalInstantObject instant = this.requireTemporalInstant(thisObj);
            return this.addDurationToOrSubtractDurationFromInstant(1, instant, temporalDurationLike, toLimitedTemporalDurationNode);
        }
    }

    public static abstract class InstantOperation
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        public InstantOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected JSTemporalInstantObject addDurationToOrSubtractDurationFromInstant(int sign, JSTemporalInstantObject instant, Object temporalDurationLike, ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
            JSTemporalDurationRecord duration = toLimitedTemporalDurationNode.executeDynamicObject(temporalDurationLike, TemporalUtil.listPluralYMWD);
            BigInt ns = TemporalUtil.addInstant(instant.getNanoseconds(), (double)sign * duration.getHours(), (double)sign * duration.getMinutes(), (double)sign * duration.getSeconds(), (double)sign * duration.getMilliseconds(), (double)sign * duration.getMicroseconds(), (double)sign * duration.getNanoseconds());
            return JSTemporalInstant.create(this.getContext(), this.getRealm(), ns);
        }
    }

    public static abstract class JSTemporalInstantGetterNode
    extends JSBuiltinNode {
        public final TemporalInstantPrototype property;

        public JSTemporalInstantGetterNode(JSContext context, JSBuiltin builtin, TemporalInstantPrototype property) {
            super(context, builtin);
            this.property = property;
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization(guards={"isJSTemporalInstant(thisObj)"})
        protected Object instantGetter(Object thisObj) {
            JSTemporalInstantObject instant = (JSTemporalInstantObject)thisObj;
            BigInteger ns = instant.getNanoseconds().bigIntegerValue();
            switch (this.property) {
                case epochSeconds: {
                    return ns.divide(TemporalUtil.BI_10_POW_9).doubleValue();
                }
                case epochMilliseconds: {
                    return ns.divide(TemporalUtil.BI_10_POW_6).doubleValue();
                }
                case epochMicroseconds: {
                    return new BigInt(ns.divide(TemporalUtil.BI_1000));
                }
                case epochNanoseconds: {
                    return instant.getNanoseconds();
                }
            }
            CompilerDirectives.transferToInterpreter();
            throw Errors.shouldNotReachHere();
        }

        @Specialization(guards={"!isJSTemporalInstant(thisObj)"})
        protected static int error(Object thisObj) {
            throw TemporalErrors.createTypeErrorTemporalInstantExpected();
        }
    }

    public static enum TemporalInstantPrototype implements BuiltinEnum<TemporalInstantPrototype>
    {
        epochSeconds(0),
        epochMilliseconds(0),
        epochMicroseconds(0),
        epochNanoseconds(0),
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
        toZonedDateTime(1),
        toZonedDateTimeISO(1);

        private final int length;

        private TemporalInstantPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isGetter() {
            return EnumSet.of(epochSeconds, epochMilliseconds, epochMicroseconds, epochNanoseconds).contains(this);
        }
    }
}

