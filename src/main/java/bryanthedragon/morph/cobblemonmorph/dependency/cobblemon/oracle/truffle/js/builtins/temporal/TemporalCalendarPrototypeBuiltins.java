
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalCalendarPrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.access.GetIteratorBaseNode;
import com.oracle.truffle.js.nodes.access.IteratorCloseNode;
import com.oracle.truffle.js.nodes.access.IteratorStepNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerOrInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDurationNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendar;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendarObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDateTimeRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDate;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDay;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDayObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonth;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonthObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalYearMonthDayRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.TemporalMonth;
import com.oracle.truffle.js.runtime.builtins.temporal.TemporalYear;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.ArrayList;
import java.util.EnumSet;

public class TemporalCalendarPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalCalendarPrototype> {
    public static final TemporalCalendarPrototypeBuiltins BUILTINS = new TemporalCalendarPrototypeBuiltins();

    protected TemporalCalendarPrototypeBuiltins() {
        super(JSTemporalCalendar.PROTOTYPE_NAME, TemporalCalendarPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalCalendarPrototype builtinEnum) {
        switch (builtinEnum) {
            case id: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarGetterNodeGen.create(context, builtin, builtinEnum, TemporalCalendarPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case mergeFields: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMergeFieldsNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case fields: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarFieldsNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case dateFromFields: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDateFromFieldsNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case yearMonthFromFields: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarYearMonthFromFieldsNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(3).createArgumentNodes(context));
            }
            case monthDayFromFields: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMonthDayFromFieldsNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(3).createArgumentNodes(context));
            }
            case dateAdd: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDateAddNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(4).createArgumentNodes(context));
            }
            case dateUntil: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDateUntilNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(3).createArgumentNodes(context));
            }
            case year: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarYearNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case month: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMonthNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case monthCode: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMonthCodeNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case day: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDayNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case dayOfWeek: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDayOfWeekNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case dayOfYear: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDayOfYearNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case daysInWeek: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDaysInWeekNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case weekOfYear: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarWeekOfYearNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case daysInMonth: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDaysInMonthNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case daysInYear: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarDaysInYearNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case monthsInYear: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarMonthsInYearNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case inLeapYear: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarInLeapYearNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toString: 
            case toJSON: {
                return TemporalCalendarPrototypeBuiltinsFactory.JSTemporalCalendarToStringNodeGen.create(context, builtin, TemporalCalendarPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalCalendarToString
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalCalendarToString(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public TruffleString toString(Object thisObj) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            return calendar.getId();
        }
    }

    public static abstract class JSTemporalCalendarInLeapYear
    extends JSTemporalCalendarOperation {
        protected JSTemporalCalendarInLeapYear(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public boolean inLeapYear(Object thisObj, Object temporalDateLike) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            int year = 0;
            if (JSTemporalPlainDate.isJSTemporalPlainDate(temporalDateLike)) {
                year = ((JSTemporalPlainDateObject)temporalDateLike).getYear();
            } else if (JSTemporalPlainYearMonth.isJSTemporalPlainYearMonth(temporalDateLike)) {
                year = ((JSTemporalPlainYearMonthObject)temporalDateLike).getYear();
            } else {
                JSTemporalPlainDateObject dateLike = (JSTemporalPlainDateObject)this.toTemporalDate(temporalDateLike, Undefined.instance);
                year = dateLike.getYear();
            }
            return TemporalUtil.isISOLeapYear(year);
        }
    }

    public static abstract class JSTemporalCalendarMonthsInYear
    extends JSTemporalCalendarOperation {
        protected JSTemporalCalendarMonthsInYear(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public long monthsInYear(Object thisObj, Object temporalDateLike) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            if (!JSTemporalPlainDate.isJSTemporalPlainDate(temporalDateLike) && !JSTemporalPlainYearMonth.isJSTemporalPlainYearMonth(temporalDateLike)) {
                this.toTemporalDate(temporalDateLike, Undefined.instance);
            }
            return 12L;
        }
    }

    public static abstract class JSTemporalCalendarDaysInYear
    extends JSTemporalCalendarOperation {
        protected JSTemporalCalendarDaysInYear(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public int daysInYear(Object thisObj, Object temporalDateLike) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            int year = 0;
            if (JSTemporalPlainDate.isJSTemporalPlainDate(temporalDateLike)) {
                year = ((JSTemporalPlainDateObject)temporalDateLike).getYear();
            } else if (JSTemporalPlainYearMonth.isJSTemporalPlainYearMonth(temporalDateLike)) {
                year = ((JSTemporalPlainYearMonthObject)temporalDateLike).getYear();
            } else {
                JSTemporalPlainDateObject dateLike = (JSTemporalPlainDateObject)this.toTemporalDate(temporalDateLike, Undefined.instance);
                year = dateLike.getYear();
            }
            return TemporalUtil.isoDaysInYear(year);
        }
    }

    public static abstract class JSTemporalCalendarDaysInMonth
    extends JSTemporalCalendarOperation {
        protected JSTemporalCalendarDaysInMonth(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public long daysInMonth(Object thisObj, Object temporalDateLike) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            Object dateLike = temporalDateLike;
            if (!this.isObject(dateLike) || !JSTemporalPlainDate.isJSTemporalPlainDate(dateLike) && !JSTemporalPlainYearMonth.isJSTemporalPlainYearMonth(temporalDateLike)) {
                dateLike = this.toTemporalDate(dateLike, Undefined.instance);
            }
            return TemporalUtil.isoDaysInMonth(((TemporalYear)dateLike).getYear(), ((TemporalMonth)dateLike).getMonth());
        }
    }

    public static abstract class JSTemporalCalendarDaysInWeek
    extends JSTemporalCalendarOperation {
        protected JSTemporalCalendarDaysInWeek(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public long daysInWeek(Object thisObj, Object temporalDateLike) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            this.toTemporalDate(temporalDateLike, Undefined.instance);
            return 7L;
        }
    }

    public static abstract class JSTemporalCalendarWeekOfYear
    extends JSTemporalCalendarOperation {
        protected JSTemporalCalendarWeekOfYear(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public long weekOfYear(Object thisObj, Object temporalDateLike) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            JSTemporalPlainDateObject date = (JSTemporalPlainDateObject)this.toTemporalDate(temporalDateLike, Undefined.instance);
            return TemporalUtil.toISOWeekOfYear(date.getYear(), date.getMonth(), date.getDay());
        }
    }

    public static abstract class JSTemporalCalendarDayOfYear
    extends JSTemporalCalendarOperation {
        protected JSTemporalCalendarDayOfYear(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public long dayOfYear(Object thisObj, Object temporalDateLike) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            JSTemporalPlainDateObject date = (JSTemporalPlainDateObject)this.toTemporalDate(temporalDateLike, Undefined.instance);
            return TemporalUtil.toISODayOfYear(date.getYear(), date.getMonth(), date.getDay());
        }
    }

    public static abstract class JSTemporalCalendarDayOfWeek
    extends JSTemporalCalendarOperation {
        protected JSTemporalCalendarDayOfWeek(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public long dayOfWeek(Object thisObj, Object temporalDateLike) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            JSTemporalPlainDateObject date = (JSTemporalPlainDateObject)this.toTemporalDate(temporalDateLike, Undefined.instance);
            return TemporalUtil.toISODayOfWeek(date.getYear(), date.getMonth(), date.getDay());
        }
    }

    public static abstract class JSTemporalCalendarDay
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalCalendarDay(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public long day(Object thisObj, Object temporalDateLike, @Cached(value="create(getContext())") ToTemporalDateNode toTemporalDate) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            JSDynamicObject tdl = Undefined.instance;
            tdl = !JSTemporalPlainDate.isJSTemporalPlainDate(temporalDateLike) && !JSTemporalPlainDateTime.isJSTemporalPlainDateTime(temporalDateLike) && !JSTemporalPlainMonthDay.isJSTemporalPlainMonthDay(temporalDateLike) ? toTemporalDate.executeDynamicObject(temporalDateLike, Undefined.instance) : (JSDynamicObject)temporalDateLike;
            return TemporalUtil.isoDay(tdl);
        }
    }

    public static abstract class JSTemporalCalendarMonthCode
    extends JSTemporalCalendarOperation {
        protected JSTemporalCalendarMonthCode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public TruffleString monthCode(Object thisObj, Object temporalDateLike) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            Object dateLike = temporalDateLike;
            if (!(this.isObject(dateLike) && (JSTemporalPlainDate.isJSTemporalPlainDate(dateLike) || JSTemporalPlainDateTime.isJSTemporalPlainDateTime(temporalDateLike) || dateLike instanceof JSTemporalPlainMonthDayObject || JSTemporalPlainYearMonth.isJSTemporalPlainYearMonth(dateLike)))) {
                dateLike = this.toTemporalDate(dateLike, Undefined.instance);
            }
            return TemporalUtil.isoMonthCode((TemporalMonth)dateLike);
        }
    }

    public static abstract class JSTemporalCalendarMonth
    extends JSTemporalCalendarOperation {
        protected JSTemporalCalendarMonth(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public long month(Object thisObj, Object temporalDateLike) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            Object dateLike = temporalDateLike;
            if (JSTemporalPlainMonthDay.isJSTemporalPlainMonthDay(dateLike)) {
                throw Errors.createTypeError("PlainMonthDay not expected");
            }
            if (!this.isObject(dateLike) || !JSTemporalPlainDate.isJSTemporalPlainDate(dateLike) && !JSTemporalPlainDateTime.isJSTemporalPlainDateTime(dateLike) && !JSTemporalPlainYearMonth.isJSTemporalPlainYearMonth(dateLike)) {
                dateLike = this.toTemporalDate(dateLike, Undefined.instance);
            }
            assert (dateLike instanceof TemporalMonth);
            return ((TemporalMonth)dateLike).getMonth();
        }
    }

    public static abstract class JSTemporalCalendarYear
    extends JSTemporalCalendarOperation {
        protected JSTemporalCalendarYear(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public long year(Object thisObj, Object temporalDateLike) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            if (JSTemporalPlainDate.isJSTemporalPlainDate(temporalDateLike)) {
                return ((JSTemporalPlainDateObject)temporalDateLike).getYear();
            }
            if (JSTemporalPlainYearMonth.isJSTemporalPlainYearMonth(temporalDateLike)) {
                return ((JSTemporalPlainYearMonthObject)temporalDateLike).getYear();
            }
            JSTemporalPlainDateObject td = (JSTemporalPlainDateObject)this.toTemporalDate(temporalDateLike, Undefined.instance);
            return td.getYear();
        }
    }

    public static abstract class JSTemporalCalendarDateUntil
    extends JSTemporalCalendarOperation {
        protected JSTemporalCalendarDateUntil(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object dateUntil(Object thisObj, Object oneObj, Object twoObj, Object optionsParam, @Cached TruffleString.EqualNode equalNode) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            JSTemporalPlainDateObject one = (JSTemporalPlainDateObject)this.toTemporalDate(oneObj, Undefined.instance);
            JSTemporalPlainDateObject two = (JSTemporalPlainDateObject)this.toTemporalDate(twoObj, Undefined.instance);
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            TemporalUtil.Unit largestUnit = this.toLargestTemporalUnit(options, TemporalUtil.listTime, TemporalConstants.AUTO, TemporalUtil.Unit.DAY, equalNode);
            JSTemporalDurationRecord result = JSTemporalPlainDate.differenceISODate(one.getYear(), one.getMonth(), one.getDay(), two.getYear(), two.getMonth(), two.getDay(), largestUnit);
            return JSTemporalDuration.createTemporalDuration(this.getContext(), result.getYears(), result.getMonths(), result.getWeeks(), result.getDays(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch);
        }
    }

    public static abstract class JSTemporalCalendarDateAdd
    extends JSTemporalCalendarOperation {
        protected final ConditionProfile needConstrain = ConditionProfile.createBinaryProfile();

        protected JSTemporalCalendarDateAdd(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object dateAdd(Object thisObj, Object dateObj, Object durationObj, Object optionsParam, @Cached(value="create(getContext())") ToTemporalDurationNode toTemporalDurationNode, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            JSTemporalPlainDateObject date = (JSTemporalPlainDateObject)this.toTemporalDate(dateObj, Undefined.instance);
            JSTemporalDurationObject duration = (JSTemporalDurationObject)toTemporalDurationNode.executeDynamicObject(durationObj);
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            TemporalUtil.Overflow overflow = TemporalUtil.toTemporalOverflow(options, this.getOptionNode());
            JSTemporalDurationRecord balanceResult = TemporalUtil.balanceDuration(this.getContext(), namesNode, duration.getDays(), duration.getHours(), duration.getMinutes(), duration.getSeconds(), duration.getMilliseconds(), duration.getMicroseconds(), duration.getNanoseconds(), TemporalUtil.Unit.DAY);
            JSTemporalDateTimeRecord result = TemporalUtil.addISODate(date.getYear(), date.getMonth(), date.getDay(), this.dtoiConstrain(duration.getYears()), this.dtoiConstrain(duration.getMonths()), this.dtoiConstrain(duration.getWeeks()), this.dtoiConstrain(balanceResult.getDays()), overflow);
            return JSTemporalPlainDate.create(this.getContext(), result.getYear(), result.getMonth(), result.getDay(), calendar, this.errorBranch);
        }

        protected int dtoiConstrain(double d) {
            if (this.needConstrain.profile(JSRuntime.doubleIsRepresentableAsInt(d))) {
                return (int)d;
            }
            return d > 0.0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }
    }

    public static abstract class JSTemporalCalendarMonthDayFromFields
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalCalendarMonthDayFromFields(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object monthDayFromFields(Object thisObj, Object fields, Object optionsParam, @Cached(value="createSameValue()") JSIdenticalNode identicalNode, @Cached(value="create()") TemporalGetOptionNode getOptionNode, @Cached(value="create()") JSToIntegerOrInfinityNode toIntOrInfinityNode) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            if (!this.isObject(fields)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorFieldsNotAnObject();
            }
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            JSTemporalYearMonthDayRecord result = TemporalUtil.isoMonthDayFromFields((JSDynamicObject)fields, options, this.getContext(), this.isObjectNode, getOptionNode, toIntOrInfinityNode, identicalNode);
            return JSTemporalPlainMonthDay.create(this.getContext(), result.getMonth(), result.getDay(), calendar, result.getYear(), this.errorBranch);
        }
    }

    public static abstract class JSTemporalCalendarYearMonthFromFields
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalCalendarYearMonthFromFields(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object yearMonthFromFields(Object thisObj, Object fields, Object optionsParam, @Cached(value="createSameValue()") JSIdenticalNode identicalNode, @Cached(value="create()") TemporalGetOptionNode getOptionNode, @Cached(value="create()") JSToIntegerOrInfinityNode toIntOrInfinityNode) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            if (!this.isObject(fields)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorFieldsNotAnObject();
            }
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            JSTemporalYearMonthDayRecord result = TemporalUtil.isoYearMonthFromFields((JSDynamicObject)fields, options, this.getContext(), this.isObjectNode, getOptionNode, toIntOrInfinityNode, identicalNode);
            return JSTemporalPlainYearMonth.create(this.getContext(), result.getYear(), result.getMonth(), calendar, result.getDay(), this.errorBranch);
        }
    }

    public static abstract class JSTemporalCalendarDateFromFields
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalCalendarDateFromFields(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object dateFromFields(Object thisObj, Object fields, Object optionsParam, @Cached(value="createSameValue()") JSIdenticalNode identicalNode, @Cached(value="create()") TemporalGetOptionNode getOptionNode, @Cached(value="create()") JSToIntegerOrInfinityNode toIntOrInfinityNode) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            if (!this.isObject(fields)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorFieldsNotAnObject();
            }
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            JSTemporalDateTimeRecord result = TemporalUtil.isoDateFromFields((JSDynamicObject)fields, options, this.getContext(), this.isObjectNode, getOptionNode, toIntOrInfinityNode, identicalNode);
            return JSTemporalPlainDate.create(this.getContext(), result.getYear(), result.getMonth(), result.getDay(), calendar, this.errorBranch);
        }
    }

    public static abstract class JSTemporalCalendarFields
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        @Node.Child
        private IteratorCloseNode iteratorCloseNode;
        @Node.Child
        private GetIteratorBaseNode getIteratorNode;
        @Node.Child
        private IteratorValueNode getIteratorValueNode;
        @Node.Child
        private IteratorStepNode iteratorStepNode;

        protected void iteratorCloseAbrupt(JSDynamicObject iterator) {
            if (this.iteratorCloseNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.iteratorCloseNode = this.insert(IteratorCloseNode.create(this.getContext()));
            }
            this.iteratorCloseNode.executeAbrupt(iterator);
        }

        protected IteratorRecord getIterator(Object iterator) {
            if (this.getIteratorNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getIteratorNode = this.insert(GetIteratorBaseNode.create());
            }
            return this.getIteratorNode.execute(iterator);
        }

        protected Object getIteratorValue(JSDynamicObject iteratorResult) {
            if (this.getIteratorValueNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getIteratorValueNode = this.insert(IteratorValueNode.create());
            }
            return this.getIteratorValueNode.execute(iteratorResult);
        }

        protected Object iteratorStep(IteratorRecord iterator) {
            if (this.iteratorStepNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.iteratorStepNode = this.insert(IteratorStepNode.create());
            }
            return this.iteratorStepNode.execute(iterator);
        }

        protected JSTemporalCalendarFields(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject fields(Object thisObj, Object fieldsParam) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            IteratorRecord iter = this.getIterator(fieldsParam);
            ArrayList<TruffleString> fieldNames = new ArrayList<TruffleString>();
            Object next = Boolean.TRUE;
            while (next != Boolean.FALSE) {
                next = this.iteratorStep(iter);
                if (next == Boolean.FALSE) continue;
                Object nextValue = this.getIteratorValue((JSDynamicObject)next);
                if (!Strings.isTString(nextValue)) {
                    this.iteratorCloseAbrupt(iter.getIterator());
                    throw Errors.createTypeError("string expected");
                }
                TruffleString str = JSRuntime.toString(nextValue);
                if (str != null && Boundaries.listContains(fieldNames, str)) {
                    this.iteratorCloseAbrupt(iter.getIterator());
                    throw Errors.createRangeError("");
                }
                if (!(TemporalConstants.YEAR.equals(str) || TemporalConstants.MONTH.equals(str) || TemporalConstants.MONTH_CODE.equals(str) || TemporalConstants.DAY.equals(str) || TemporalConstants.HOUR.equals(str) || TemporalConstants.MINUTE.equals(str) || TemporalConstants.SECOND.equals(str) || TemporalConstants.MILLISECOND.equals(str) || TemporalConstants.MICROSECOND.equals(str) || TemporalConstants.NANOSECOND.equals(str))) {
                    this.iteratorCloseAbrupt(iter.getIterator());
                    throw Errors.createRangeError("");
                }
                fieldNames.add(str);
            }
            return JSRuntime.createArrayFromList(this.getContext(), this.getRealm(), fieldNames);
        }
    }

    public static abstract class JSTemporalCalendarMergeFields
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalCalendarMergeFields(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject mergeFields(Object thisObj, Object fieldsParam, Object additionalFieldsParam, @Cached(value="createToObject(getContext())") JSToObjectNode toObject, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode) {
            JSTemporalCalendarObject calendar = this.requireTemporalCalendar(thisObj);
            assert (calendar.getId().equals(TemporalConstants.ISO8601));
            JSDynamicObject fields = (JSDynamicObject)toObject.execute(fieldsParam);
            JSDynamicObject additionalFields = (JSDynamicObject)toObject.execute(additionalFieldsParam);
            return TemporalUtil.defaultMergeFields(this.getContext(), namesNode, fields, additionalFields);
        }
    }

    public static abstract class JSTemporalCalendarOperation
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        @Node.Child
        private ToTemporalDateNode toTemporalDate;

        public JSTemporalCalendarOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected JSDynamicObject toTemporalDate(Object item, JSDynamicObject options) {
            if (this.toTemporalDate == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toTemporalDate = this.insert(ToTemporalDateNode.create(this.getContext()));
            }
            return this.toTemporalDate.executeDynamicObject(item, options);
        }
    }

    public static abstract class JSTemporalCalendarGetterNode
    extends JSBuiltinNode {
        public final TemporalCalendarPrototype property;

        public JSTemporalCalendarGetterNode(JSContext context, JSBuiltin builtin, TemporalCalendarPrototype property) {
            super(context, builtin);
            this.property = property;
        }

        @Specialization(guards={"isJSTemporalCalendar(thisObj)"})
        protected Object durationGetter(Object thisObj, @Cached JSToStringNode toStringNode) {
            switch (this.property) {
                case id: {
                    return toStringNode.executeString(thisObj);
                }
            }
            CompilerDirectives.transferToInterpreter();
            throw Errors.shouldNotReachHere();
        }

        @Specialization(guards={"!isJSTemporalCalendar(thisObj)"})
        protected static int error(Object thisObj) {
            throw TemporalErrors.createTypeErrorTemporalCalendarExpected();
        }
    }

    public static enum TemporalCalendarPrototype implements BuiltinEnum<TemporalCalendarPrototype>
    {
        id(0),
        mergeFields(2),
        fields(1),
        dateFromFields(1),
        yearMonthFromFields(1),
        monthDayFromFields(1),
        dateAdd(2),
        dateUntil(2),
        year(1),
        month(1),
        monthCode(1),
        day(1),
        dayOfWeek(1),
        dayOfYear(1),
        weekOfYear(1),
        daysInWeek(1),
        daysInMonth(1),
        daysInYear(1),
        monthsInYear(1),
        inLeapYear(1),
        toString(0),
        toJSON(0);

        private final int length;

        private TemporalCalendarPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isGetter() {
            return EnumSet.of(id).contains(this);
        }
    }
}

