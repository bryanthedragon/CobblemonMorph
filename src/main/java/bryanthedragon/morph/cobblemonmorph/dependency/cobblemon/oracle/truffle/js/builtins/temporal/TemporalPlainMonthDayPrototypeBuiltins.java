
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainMonthDayPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarGetterNode;
import com.oracle.truffle.js.nodes.temporal.TemporalMonthDayFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalMonthDayNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDay;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.EnumSet;
import java.util.List;

public class TemporalPlainMonthDayPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalPlainMonthDayPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalPlainMonthDayPrototypeBuiltins();

    protected TemporalPlainMonthDayPrototypeBuiltins() {
        super(JSTemporalPlainMonthDay.PROTOTYPE_NAME, TemporalPlainMonthDayPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalPlainMonthDayPrototype builtinEnum) {
        switch (builtinEnum) {
            case calendar: 
            case monthCode: 
            case day: {
                return TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayGetterNodeGen.create(context, builtin, builtinEnum, TemporalPlainMonthDayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case with: {
                return TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayWithNodeGen.create(context, builtin, TemporalPlainMonthDayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case equals: {
                return TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayEqualsNodeGen.create(context, builtin, TemporalPlainMonthDayPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toPlainDate: {
                return TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayToPlainDateNodeGen.create(context, builtin, TemporalPlainMonthDayPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case getISOFields: {
                return TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayGetISOFieldsNodeGen.create(context, builtin, TemporalPlainMonthDayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toString: {
                return TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayToStringNodeGen.create(context, builtin, TemporalPlainMonthDayPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toLocaleString: 
            case toJSON: {
                return TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayToLocaleStringNodeGen.create(context, builtin, TemporalPlainMonthDayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case valueOf: {
                return TemporalPlainMonthDayPrototypeBuiltinsFactory.JSTemporalPlainMonthDayValueOfNodeGen.create(context, builtin, TemporalPlainMonthDayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalPlainMonthDayEqualsNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainMonthDayEqualsNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean equals(Object thisObj, Object otherParam, @Cached JSToStringNode toStringNode, @Cached(value="create(getContext())") ToTemporalMonthDayNode toTemporalMonthDayNode) {
            JSTemporalPlainMonthDayObject md = this.requireTemporalMonthDay(thisObj);
            JSTemporalPlainMonthDayObject other = toTemporalMonthDayNode.executeDynamicObject(otherParam, Undefined.instance);
            if (md.getMonth() != other.getMonth()) {
                return false;
            }
            if (md.getDay() != other.getDay()) {
                return false;
            }
            if (md.getYear() != other.getYear()) {
                return false;
            }
            return TemporalUtil.calendarEquals(md.getCalendar(), other.getCalendar(), toStringNode);
        }
    }

    public static abstract class JSTemporalPlainMonthDayWithNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainMonthDayWithNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject with(Object thisObj, Object temporalMonthDayLike, Object optParam, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode, @Cached(value="create(getContext())") TemporalMonthDayFromFieldsNode monthDayFromFieldsNode, @Cached(value="create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode) {
            JSTemporalPlainMonthDayObject md = this.requireTemporalMonthDay(thisObj);
            if (!this.isObject(temporalMonthDayLike)) {
                this.errorBranch.enter();
                throw Errors.createTypeError("Object expected");
            }
            JSDynamicObject mdLikeObj = (JSDynamicObject)temporalMonthDayLike;
            TemporalUtil.rejectTemporalCalendarType(mdLikeObj, this.errorBranch);
            Object calendarProperty = JSObject.get(mdLikeObj, TemporalConstants.CALENDAR);
            if (calendarProperty != Undefined.instance) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorUnexpectedCalendar();
            }
            Object timezoneProperty = JSObject.get(mdLikeObj, TemporalConstants.TIME_ZONE);
            if (timezoneProperty != Undefined.instance) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorUnexpectedTimeZone();
            }
            JSDynamicObject calendar = md.getCalendar();
            List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listDMMCY);
            JSDynamicObject partialMonthDay = TemporalUtil.preparePartialTemporalFields(this.getContext(), mdLikeObj, fieldNames);
            JSDynamicObject options = this.getOptionsObject(optParam);
            JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.getContext(), md, fieldNames, TemporalUtil.listEmpty);
            fields = TemporalUtil.calendarMergeFields(this.getContext(), namesNode, this.errorBranch, calendar, fields, partialMonthDay);
            fields = TemporalUtil.prepareTemporalFields(this.getContext(), fields, fieldNames, TemporalUtil.listEmpty);
            return monthDayFromFieldsNode.execute(calendar, fields, options);
        }
    }

    public static abstract class JSTemporalPlainMonthDayGetISOFields
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainMonthDayGetISOFields(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public JSDynamicObject getISOFields(Object thisObj) {
            JSTemporalPlainMonthDayObject md = this.requireTemporalMonthDay(thisObj);
            JSObject obj = JSOrdinary.create(this.getContext(), this.getRealm());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.CALENDAR, md.getCalendar());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_DAY, md.getDay());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_MONTH, md.getMonth());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_YEAR, md.getYear());
            return obj;
        }
    }

    public static abstract class JSTemporalPlainMonthDayToPlainDateNode
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainMonthDayToPlainDateNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object toPlainDate(Object thisObj, Object item, @Cached(value="createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode, @Cached(value="create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode, @Cached(value="create(getContext())") TemporalCalendarDateFromFieldsNode dateFromFieldsNode) {
            JSTemporalPlainMonthDayObject monthDay = this.requireTemporalMonthDay(thisObj);
            if (!this.isObject(item)) {
                this.errorBranch.enter();
                throw TemporalErrors.createTypeErrorTemporalPlainMonthDayExpected();
            }
            JSDynamicObject calendar = monthDay.getCalendar();
            List<TruffleString> receiverFieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listDMC);
            JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.getContext(), monthDay, receiverFieldNames, TemporalUtil.listEmpty);
            List<TruffleString> inputFieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listY);
            JSDynamicObject inputFields = TemporalUtil.prepareTemporalFields(this.getContext(), TemporalUtil.toJSDynamicObject(item, this.errorBranch), inputFieldNames, TemporalUtil.listEmpty);
            JSDynamicObject mergedFields = TemporalUtil.calendarMergeFields(this.getContext(), namesNode, this.errorBranch, calendar, fields, inputFields);
            List<TruffleString> mergedFieldNames = TemporalUtil.listJoinRemoveDuplicates(receiverFieldNames, inputFieldNames);
            mergedFields = TemporalUtil.prepareTemporalFields(this.getContext(), mergedFields, mergedFieldNames, TemporalUtil.listEmpty);
            JSObject options = JSOrdinary.createWithNullPrototype(this.getContext());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), options, TemporalConstants.OVERFLOW, TemporalConstants.REJECT);
            return dateFromFieldsNode.execute(calendar, mergedFields, options);
        }
    }

    public static abstract class JSTemporalPlainMonthDayValueOf
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainMonthDayValueOf(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object valueOf(Object thisObj) {
            throw Errors.createTypeError("Not supported.");
        }
    }

    public static abstract class JSTemporalPlainMonthDayToLocaleString
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainMonthDayToLocaleString(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public TruffleString toLocaleString(Object thisObj) {
            JSTemporalPlainMonthDayObject time = this.requireTemporalMonthDay(thisObj);
            return JSTemporalPlainMonthDay.temporalMonthDayToString(time, TemporalUtil.ShowCalendar.AUTO);
        }
    }

    public static abstract class JSTemporalPlainMonthDayToString
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalPlainMonthDayToString(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected TruffleString toString(Object thisObj, Object optParam, @Cached TruffleString.EqualNode equalNode) {
            JSTemporalPlainMonthDayObject md = this.requireTemporalMonthDay(thisObj);
            JSDynamicObject options = this.getOptionsObject(optParam);
            TemporalUtil.ShowCalendar showCalendar = TemporalUtil.toShowCalendarOption(options, this.getOptionNode(), equalNode);
            return JSTemporalPlainMonthDay.temporalMonthDayToString(md, showCalendar);
        }
    }

    public static abstract class JSTemporalPlainMonthDayGetterNode
    extends JSBuiltinNode {
        public final TemporalPlainMonthDayPrototype property;

        public JSTemporalPlainMonthDayGetterNode(JSContext context, JSBuiltin builtin, TemporalPlainMonthDayPrototype property) {
            super(context, builtin);
            this.property = property;
        }

        @Specialization(guards={"isJSTemporalMonthDay(thisObj)"})
        protected Object monthDayGetter(Object thisObj, @Cached(value="create(getContext())") TemporalCalendarGetterNode calendarGetterNode) {
            JSTemporalPlainMonthDayObject plainMD = (JSTemporalPlainMonthDayObject)thisObj;
            switch (this.property) {
                case day: {
                    return TemporalUtil.calendarDay(calendarGetterNode, plainMD.getCalendar(), plainMD);
                }
                case monthCode: {
                    return TemporalUtil.calendarMonthCode(calendarGetterNode, plainMD.getCalendar(), plainMD);
                }
                case calendar: {
                    return plainMD.getCalendar();
                }
            }
            CompilerDirectives.transferToInterpreter();
            throw Errors.shouldNotReachHere();
        }

        @Specialization(guards={"!isJSTemporalMonthDay(thisObj)"})
        protected static int error(Object thisObj) {
            throw TemporalErrors.createTypeErrorTemporalPlainMonthDayExpected();
        }
    }

    public static enum TemporalPlainMonthDayPrototype implements BuiltinEnum<TemporalPlainMonthDayPrototype>
    {
        calendar(0),
        monthCode(0),
        day(0),
        with(1),
        equals(1),
        toPlainDate(1),
        getISOFields(0),
        toString(0),
        toLocaleString(0),
        toJSON(0),
        valueOf(0);

        private final int length;

        private TemporalPlainMonthDayPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isGetter() {
            return EnumSet.of(calendar, monthCode, day).contains(this);
        }
    }
}

