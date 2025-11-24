
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalDurationPrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSNumberToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerWithoutRoundingNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.TemporalBalanceDurationRelativeNode;
import com.oracle.truffle.js.nodes.temporal.TemporalDurationAddNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.TemporalUnbalanceDurationRelativeNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToRelativeTemporalObjectNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPrecisionRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.EnumSet;

public class TemporalDurationPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalDurationPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalDurationPrototypeBuiltins();

    protected TemporalDurationPrototypeBuiltins() {
        super(JSTemporalDuration.PROTOTYPE_NAME, TemporalDurationPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalDurationPrototype builtinEnum) {
        switch (builtinEnum) {
            case years: 
            case months: 
            case weeks: 
            case days: 
            case hours: 
            case minutes: 
            case seconds: 
            case milliseconds: 
            case microseconds: 
            case nanoseconds: 
            case sign: 
            case blank: {
                return TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationGetterNodeGen.create(context, builtin, builtinEnum, TemporalDurationPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case with: {
                return TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationWithNodeGen.create(context, builtin, TemporalDurationPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case negated: {
                return TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationNegatedNodeGen.create(context, builtin, TemporalDurationPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case abs: {
                return TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationAbsNodeGen.create(context, builtin, TemporalDurationPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case add: {
                return TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationAddNodeGen.create(context, builtin, TemporalDurationPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case subtract: {
                return TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationSubtractNodeGen.create(context, builtin, TemporalDurationPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case round: {
                return TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationRoundNodeGen.create(context, builtin, TemporalDurationPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case total: {
                return TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationTotalNodeGen.create(context, builtin, TemporalDurationPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toJSON: 
            case toLocaleString: {
                return TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationToLocaleStringNodeGen.create(context, builtin, TemporalDurationPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case toString: {
                return TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationToStringNodeGen.create(context, builtin, TemporalDurationPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case valueOf: {
                return TemporalDurationPrototypeBuiltinsFactory.JSTemporalDurationValueOfNodeGen.create(context, builtin, TemporalDurationPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalDurationValueOf
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalDurationValueOf(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object valueOf(Object thisObj) {
            throw Errors.createTypeError("Not supported.");
        }
    }

    public static abstract class JSTemporalDurationToString
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalDurationToString(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected TruffleString toString(Object duration, Object opt, @Cached JSNumberToBigIntNode toBigIntNode, @Cached JSToStringNode toStringNode, @Cached TruffleString.EqualNode equalNode, @Cached(value="create(getContext())") TemporalRoundDurationNode roundDurationNode) {
            JSTemporalDurationObject dur = this.requireTemporalDuration(duration);
            JSDynamicObject options = this.getOptionsObject(opt);
            JSTemporalPrecisionRecord precision = TemporalUtil.toSecondsStringPrecision(options, toStringNode, this.getOptionNode(), equalNode);
            if (precision.getUnit() == TemporalUtil.Unit.MINUTE) {
                this.errorBranch.enter();
                throw Errors.createRangeError("unexpected precision minute");
            }
            TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(options, TemporalConstants.TRUNC, equalNode);
            JSTemporalDurationRecord result = roundDurationNode.execute(dur.getYears(), dur.getMonths(), dur.getWeeks(), dur.getDays(), dur.getHours(), dur.getMinutes(), dur.getSeconds(), dur.getMilliseconds(), dur.getMicroseconds(), dur.getNanoseconds(), (long)precision.getIncrement(), precision.getUnit(), roundingMode, Undefined.instance);
            return JSTemporalDuration.temporalDurationToString(result.getYears(), result.getMonths(), result.getWeeks(), result.getDays(), result.getHours(), result.getMinutes(), result.getSeconds(), result.getMilliseconds(), result.getMicroseconds(), result.getNanoseconds(), precision.getPrecision(), toBigIntNode);
        }
    }

    public static abstract class JSTemporalDurationToLocaleString
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalDurationToLocaleString(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected TruffleString toString(Object thisObj, @Cached JSNumberToBigIntNode toBigIntNode) {
            JSTemporalDurationObject duration = this.requireTemporalDuration(thisObj);
            return JSTemporalDuration.temporalDurationToString(TemporalUtil.dtol(duration.getYears()), TemporalUtil.dtol(duration.getMonths()), TemporalUtil.dtol(duration.getWeeks()), TemporalUtil.dtol(duration.getDays()), TemporalUtil.dtol(duration.getHours()), TemporalUtil.dtol(duration.getMinutes()), TemporalUtil.dtol(duration.getSeconds()), TemporalUtil.dtol(duration.getMilliseconds()), TemporalUtil.dtol(duration.getMicroseconds()), TemporalUtil.dtol(duration.getNanoseconds()), TemporalConstants.AUTO, toBigIntNode);
        }
    }

    public static abstract class JSTemporalDurationTotal
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalDurationTotal(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected double total(Object thisObj, Object totalOfParam, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode, @Cached JSNumberToBigIntNode toBigIntNode, @Cached TruffleString.EqualNode equalNode, @Cached(value="create(getContext())") ToRelativeTemporalObjectNode toRelativeTemporalObjectNode, @Cached(value="create(getContext())") TemporalRoundDurationNode roundDurationNode, @Cached(value="create(getContext())") TemporalUnbalanceDurationRelativeNode unbalanceDurationRelativeNode) {
            JSDynamicObject totalOf;
            JSTemporalDurationObject duration = this.requireTemporalDuration(thisObj);
            if (totalOfParam == Undefined.instance) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorOptionsUndefined();
            }
            if (Strings.isTString(totalOfParam)) {
                totalOf = JSOrdinary.createWithNullPrototype(this.getContext());
                JSRuntime.createDataPropertyOrThrow(totalOf, TemporalConstants.UNIT, JSRuntime.toStringIsString(totalOfParam));
            } else {
                totalOf = this.getOptionsObject(totalOfParam);
            }
            JSDynamicObject relativeTo = toRelativeTemporalObjectNode.execute(totalOf);
            TemporalUtil.Unit unit = this.toTemporalDurationTotalUnit(totalOf, equalNode);
            JSTemporalDurationRecord unbalanceResult = unbalanceDurationRelativeNode.execute(duration.getYears(), duration.getMonths(), duration.getWeeks(), duration.getDays(), unit, relativeTo);
            JSDynamicObject intermediate = Undefined.instance;
            if (TemporalUtil.isTemporalZonedDateTime(relativeTo)) {
                intermediate = TemporalUtil.moveRelativeZonedDateTime(this.getContext(), relativeTo, TemporalUtil.dtol(unbalanceResult.getYears()), TemporalUtil.dtol(unbalanceResult.getMonths()), TemporalUtil.dtol(unbalanceResult.getWeeks()), 0L);
            }
            JSTemporalDurationRecord balanceResult = TemporalUtil.balanceDuration(this.getContext(), namesNode, unbalanceResult.getDays(), duration.getHours(), duration.getMinutes(), duration.getSeconds(), duration.getMilliseconds(), duration.getMicroseconds(), toBigIntNode.executeBigInt(duration.getNanoseconds()).bigIntegerValue(), unit, intermediate);
            JSTemporalDurationRecord roundResult = roundDurationNode.execute(unbalanceResult.getYears(), unbalanceResult.getMonths(), unbalanceResult.getWeeks(), balanceResult.getDays(), balanceResult.getHours(), balanceResult.getMinutes(), balanceResult.getSeconds(), balanceResult.getMilliseconds(), balanceResult.getMicroseconds(), balanceResult.getNanoseconds(), 1.0, unit, TemporalUtil.RoundingMode.TRUNC, relativeTo);
            double whole = 0.0;
            if (unit == TemporalUtil.Unit.YEAR) {
                whole = roundResult.getYears();
            } else if (unit == TemporalUtil.Unit.MONTH) {
                whole = roundResult.getMonths();
            } else if (unit == TemporalUtil.Unit.WEEK) {
                whole = roundResult.getWeeks();
            } else if (unit == TemporalUtil.Unit.DAY) {
                whole = roundResult.getDays();
            } else if (unit == TemporalUtil.Unit.HOUR) {
                whole = roundResult.getHours();
            } else if (unit == TemporalUtil.Unit.MINUTE) {
                whole = roundResult.getMinutes();
            } else if (unit == TemporalUtil.Unit.SECOND) {
                whole = roundResult.getSeconds();
            } else if (unit == TemporalUtil.Unit.MILLISECOND) {
                whole = roundResult.getMilliseconds();
            } else if (unit == TemporalUtil.Unit.MICROSECOND) {
                whole = roundResult.getMicroseconds();
            } else {
                assert (TemporalUtil.Unit.NANOSECOND == unit);
                whole = roundResult.getNanoseconds();
            }
            return whole + roundResult.getRemainder();
        }
    }

    public static abstract class JSTemporalDurationRound
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalDurationRound(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject round(Object thisObj, Object roundToParam, @Cached(value="create()") JSToNumberNode toNumber, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode, @Cached JSNumberToBigIntNode toBigInt, @Cached TruffleString.EqualNode equalNode, @Cached(value="create(getContext())") TemporalDurationAddNode durationAddNode, @Cached ConditionProfile roundToIsTString, @Cached ConditionProfile realtiveToIsZonedDateTime, @Cached(value="create(getContext())") ToRelativeTemporalObjectNode toRelativeTemporalObjectNode, @Cached(value="create(getContext())") TemporalRoundDurationNode roundDurationNode, @Cached(value="create(getContext())") TemporalUnbalanceDurationRelativeNode unbalanceDurationRelativeNode, @Cached(value="create(getContext())") TemporalBalanceDurationRelativeNode balanceDurationRelativeNode) {
            JSDynamicObject roundTo;
            JSTemporalDurationObject duration = this.requireTemporalDuration(thisObj);
            if (roundToParam == Undefined.instance) {
                throw TemporalErrors.createTypeErrorOptionsUndefined();
            }
            if (roundToIsTString.profile(Strings.isTString(roundToParam))) {
                roundTo = JSOrdinary.createWithNullPrototype(this.getContext());
                JSRuntime.createDataPropertyOrThrow(roundTo, TemporalConstants.SMALLEST_UNIT, JSRuntime.toStringIsString(roundToParam));
            } else {
                roundTo = this.getOptionsObject(roundToParam);
            }
            boolean smallestUnitPresent = true;
            boolean largestUnitPresent = true;
            TemporalUtil.Unit smallestUnit = this.toSmallestTemporalUnit(roundTo, TemporalUtil.listEmpty, null, equalNode);
            if (smallestUnit == TemporalUtil.Unit.EMPTY) {
                smallestUnitPresent = false;
                smallestUnit = TemporalUtil.Unit.NANOSECOND;
            }
            TemporalUtil.Unit defaultLargestUnit = TemporalUtil.defaultTemporalLargestUnit(duration.getYears(), duration.getMonths(), duration.getWeeks(), duration.getDays(), duration.getHours(), duration.getMinutes(), duration.getSeconds(), duration.getMilliseconds(), duration.getMicroseconds());
            defaultLargestUnit = TemporalUtil.largerOfTwoTemporalUnits(defaultLargestUnit, smallestUnit);
            TemporalUtil.Unit largestUnit = this.toLargestTemporalUnit(roundTo, TemporalUtil.listEmpty, null, null, equalNode);
            if (largestUnit == TemporalUtil.Unit.EMPTY) {
                largestUnitPresent = false;
                largestUnit = defaultLargestUnit;
            } else if (TemporalUtil.Unit.AUTO == largestUnit) {
                largestUnit = defaultLargestUnit;
            }
            if (!smallestUnitPresent && !largestUnitPresent) {
                this.errorBranch.enter();
                throw Errors.createRangeError("unit expected");
            }
            TemporalUtil.validateTemporalUnitRange(largestUnit, smallestUnit);
            TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(roundTo, TemporalConstants.HALF_EXPAND, equalNode);
            Double maximum = TemporalUtil.maximumTemporalDurationRoundingIncrement(smallestUnit);
            double roundingIncrement = TemporalUtil.toTemporalRoundingIncrement(roundTo, maximum, false, this.isObjectNode, toNumber);
            JSDynamicObject relativeTo = toRelativeTemporalObjectNode.execute(roundTo);
            JSTemporalDurationRecord unbalanceResult = unbalanceDurationRelativeNode.execute(duration.getYears(), duration.getMonths(), duration.getWeeks(), duration.getDays(), largestUnit, relativeTo);
            JSTemporalDurationRecord roundResult = roundDurationNode.execute(unbalanceResult.getYears(), unbalanceResult.getMonths(), unbalanceResult.getWeeks(), unbalanceResult.getDays(), duration.getHours(), duration.getMinutes(), duration.getSeconds(), duration.getMilliseconds(), duration.getMicroseconds(), duration.getNanoseconds(), (long)roundingIncrement, smallestUnit, roundingMode, relativeTo);
            JSTemporalDurationRecord adjustResult = TemporalUtil.adjustRoundedDurationDays(this.getContext(), namesNode, durationAddNode, roundResult.getYears(), roundResult.getMonths(), roundResult.getWeeks(), roundResult.getDays(), roundResult.getHours(), roundResult.getMinutes(), roundResult.getSeconds(), roundResult.getMilliseconds(), roundResult.getMicroseconds(), roundResult.getNanoseconds(), (long)roundingIncrement, smallestUnit, roundingMode, relativeTo);
            JSTemporalDurationRecord balanceResult = balanceDurationRelativeNode.execute(adjustResult.getYears(), adjustResult.getMonths(), adjustResult.getWeeks(), adjustResult.getDays(), largestUnit, relativeTo);
            if (realtiveToIsZonedDateTime.profile(TemporalUtil.isTemporalZonedDateTime(relativeTo))) {
                relativeTo = TemporalUtil.moveRelativeZonedDateTime(this.getContext(), relativeTo, TemporalUtil.dtol(balanceResult.getYears()), TemporalUtil.dtol(balanceResult.getMonths()), TemporalUtil.dtol(balanceResult.getWeeks()), 0L);
            }
            JSTemporalDurationRecord result = TemporalUtil.balanceDuration(this.getContext(), namesNode, balanceResult.getDays(), adjustResult.getHours(), adjustResult.getMinutes(), adjustResult.getSeconds(), adjustResult.getMilliseconds(), adjustResult.getMicroseconds(), toBigInt.executeBigInt(adjustResult.getNanoseconds()).bigIntegerValue(), largestUnit, relativeTo);
            return JSTemporalDuration.createTemporalDuration(this.getContext(), balanceResult.getYears(), balanceResult.getMonths(), balanceResult.getWeeks(), result.getDays(), result.getHours(), result.getMinutes(), result.getSeconds(), result.getMilliseconds(), result.getMicroseconds(), result.getNanoseconds(), this.errorBranch);
        }
    }

    public static abstract class JSTemporalDurationSubtract
    extends DurationOperation {
        protected JSTemporalDurationSubtract(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject subtract(Object thisObj, Object other, Object options, @Cached(value="create(getContext())") TemporalDurationAddNode durationAddNode, @Cached(value="create(getContext())") ToRelativeTemporalObjectNode toRelativeTemporalObjectNode, @Cached(value="create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
            JSTemporalDurationObject duration = this.requireTemporalDuration(thisObj);
            return this.addDurationToOrSubtractDurationFromDuration(-1, duration, other, options, durationAddNode, toRelativeTemporalObjectNode, toLimitedTemporalDurationNode);
        }
    }

    public static abstract class JSTemporalDurationAdd
    extends DurationOperation {
        protected JSTemporalDurationAdd(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject add(Object thisObj, Object other, Object options, @Cached(value="create(getContext())") TemporalDurationAddNode durationAddNode, @Cached(value="create(getContext())") ToRelativeTemporalObjectNode toRelativeTemporalObjectNode, @Cached(value="create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
            JSTemporalDurationObject duration = this.requireTemporalDuration(thisObj);
            return this.addDurationToOrSubtractDurationFromDuration(1, duration, other, options, durationAddNode, toRelativeTemporalObjectNode, toLimitedTemporalDurationNode);
        }
    }

    public static abstract class JSTemporalDurationAbs
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalDurationAbs(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject abs(Object thisObj) {
            JSTemporalDurationObject duration = this.requireTemporalDuration(thisObj);
            return JSTemporalDuration.createTemporalDuration(this.getContext(), Math.abs(duration.getYears()), Math.abs(duration.getMonths()), Math.abs(duration.getWeeks()), Math.abs(duration.getDays()), Math.abs(duration.getHours()), Math.abs(duration.getMinutes()), Math.abs(duration.getSeconds()), Math.abs(duration.getMilliseconds()), Math.abs(duration.getMicroseconds()), Math.abs(duration.getNanoseconds()), this.errorBranch);
        }
    }

    public static abstract class JSTemporalDurationNegated
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalDurationNegated(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject negated(Object thisObj) {
            JSTemporalDurationObject duration = this.requireTemporalDuration(thisObj);
            return JSTemporalDuration.createTemporalDuration(this.getContext(), -duration.getYears(), -duration.getMonths(), -duration.getWeeks(), -duration.getDays(), -duration.getHours(), -duration.getMinutes(), -duration.getSeconds(), -duration.getMilliseconds(), -duration.getMicroseconds(), -duration.getNanoseconds(), this.errorBranch);
        }
    }

    public static abstract class JSTemporalDurationWith
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalDurationWith(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject with(Object thisObj, Object temporalDurationLike, @Cached(value="create()") JSToIntegerWithoutRoundingNode toInt) {
            JSTemporalDurationObject duration = this.requireTemporalDuration(thisObj);
            JSDynamicObject durationLike = TemporalUtil.toPartialDuration(temporalDurationLike, this.getContext(), this.isObjectNode, toInt, this.errorBranch);
            double years = TemporalUtil.getDouble(durationLike, TemporalConstants.YEARS, duration.getYears());
            double months = TemporalUtil.getDouble(durationLike, TemporalConstants.MONTHS, duration.getMonths());
            double weeks = TemporalUtil.getDouble(durationLike, TemporalConstants.WEEKS, duration.getWeeks());
            double days = TemporalUtil.getDouble(durationLike, TemporalConstants.DAYS, duration.getDays());
            double hours = TemporalUtil.getDouble(durationLike, TemporalConstants.HOURS, duration.getHours());
            double minutes = TemporalUtil.getDouble(durationLike, TemporalConstants.MINUTES, duration.getMinutes());
            double seconds = TemporalUtil.getDouble(durationLike, TemporalConstants.SECONDS, duration.getSeconds());
            double milliseconds = TemporalUtil.getDouble(durationLike, TemporalConstants.MILLISECONDS, duration.getMilliseconds());
            double microseconds = TemporalUtil.getDouble(durationLike, TemporalConstants.MICROSECONDS, duration.getMicroseconds());
            double nanoseconds = TemporalUtil.getDouble(durationLike, TemporalConstants.NANOSECONDS, duration.getNanoseconds());
            return JSTemporalDuration.createTemporalDuration(this.getContext(), years, months, weeks, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, this.errorBranch);
        }
    }

    public static abstract class DurationOperation
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        public DurationOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected JSTemporalDurationObject addDurationToOrSubtractDurationFromDuration(int sign, JSTemporalDurationObject duration, Object other, Object options, TemporalDurationAddNode durationAddNode, ToRelativeTemporalObjectNode toRelativeTemporalObjectNode, ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
            JSTemporalDurationRecord otherDuration = toLimitedTemporalDurationNode.executeDynamicObject(other, TemporalUtil.listEmpty);
            JSDynamicObject normalizedOptions = this.getOptionsObject(options);
            JSDynamicObject relativeTo = toRelativeTemporalObjectNode.execute(normalizedOptions);
            JSTemporalDurationRecord result = durationAddNode.execute(duration.getYears(), duration.getMonths(), duration.getWeeks(), duration.getDays(), duration.getHours(), duration.getMinutes(), duration.getSeconds(), duration.getMilliseconds(), duration.getMicroseconds(), duration.getNanoseconds(), (double)sign * otherDuration.getYears(), (double)sign * otherDuration.getMonths(), (double)sign * otherDuration.getWeeks(), (double)sign * otherDuration.getDays(), (double)sign * otherDuration.getHours(), (double)sign * otherDuration.getMinutes(), (double)sign * otherDuration.getSeconds(), (double)sign * otherDuration.getMilliseconds(), (double)sign * otherDuration.getMicroseconds(), (double)sign * otherDuration.getNanoseconds(), relativeTo);
            return JSTemporalDuration.createTemporalDuration(this.getContext(), result.getYears(), result.getMonths(), result.getWeeks(), result.getDays(), result.getHours(), result.getMinutes(), result.getSeconds(), result.getMilliseconds(), result.getMicroseconds(), result.getNanoseconds(), this.errorBranch);
        }
    }

    public static abstract class JSTemporalDurationGetterNode
    extends JSBuiltinNode {
        public final TemporalDurationPrototype property;

        public JSTemporalDurationGetterNode(JSContext context, JSBuiltin builtin, TemporalDurationPrototype property) {
            super(context, builtin);
            this.property = property;
        }

        @Specialization(guards={"isJSTemporalDuration(thisObj)"})
        protected Object durationGetter(Object thisObj) {
            JSTemporalDurationObject temporalD = (JSTemporalDurationObject)thisObj;
            switch (this.property) {
                case hours: {
                    return temporalD.getHours();
                }
                case minutes: {
                    return temporalD.getMinutes();
                }
                case seconds: {
                    return temporalD.getSeconds();
                }
                case milliseconds: {
                    return temporalD.getMilliseconds();
                }
                case microseconds: {
                    return temporalD.getMicroseconds();
                }
                case nanoseconds: {
                    return temporalD.getNanoseconds();
                }
                case years: {
                    return temporalD.getYears();
                }
                case months: {
                    return temporalD.getMonths();
                }
                case weeks: {
                    return temporalD.getWeeks();
                }
                case days: {
                    return temporalD.getDays();
                }
                case sign: {
                    return TemporalUtil.durationSign(temporalD.getYears(), temporalD.getMonths(), temporalD.getWeeks(), temporalD.getDays(), temporalD.getHours(), temporalD.getMinutes(), temporalD.getSeconds(), temporalD.getMilliseconds(), temporalD.getMicroseconds(), temporalD.getNanoseconds());
                }
                case blank: {
                    int sign = TemporalUtil.durationSign(temporalD.getYears(), temporalD.getMonths(), temporalD.getWeeks(), temporalD.getDays(), temporalD.getHours(), temporalD.getMinutes(), temporalD.getSeconds(), temporalD.getMilliseconds(), temporalD.getMicroseconds(), temporalD.getNanoseconds());
                    return sign == 0;
                }
            }
            CompilerDirectives.transferToInterpreter();
            throw Errors.shouldNotReachHere();
        }

        @Specialization(guards={"!isJSTemporalDuration(thisObj)"})
        protected static int error(Object thisObj) {
            throw TemporalErrors.createTypeErrorTemporalDurationExpected();
        }
    }

    public static enum TemporalDurationPrototype implements BuiltinEnum<TemporalDurationPrototype>
    {
        years(0),
        months(0),
        weeks(0),
        days(0),
        hours(0),
        minutes(0),
        seconds(0),
        milliseconds(0),
        microseconds(0),
        nanoseconds(0),
        sign(0),
        blank(0),
        with(1),
        negated(0),
        abs(0),
        add(1),
        subtract(1),
        round(1),
        total(1),
        toJSON(0),
        toString(0),
        toLocaleString(0),
        valueOf(0);

        private final int length;

        private TemporalDurationPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isGetter() {
            return EnumSet.of(hours, new TemporalDurationPrototype[]{minutes, seconds, milliseconds, microseconds, nanoseconds, years, months, weeks, days, sign, blank}).contains(this);
        }
    }
}

