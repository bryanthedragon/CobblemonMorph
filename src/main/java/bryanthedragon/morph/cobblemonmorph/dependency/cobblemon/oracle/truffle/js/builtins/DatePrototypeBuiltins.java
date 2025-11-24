
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.DatePrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.OrdinaryToPrimitiveNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.intl.InitializeDateTimeFormatNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSDate;
import com.oracle.truffle.js.runtime.builtins.JSDateObject;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormatObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import java.util.EnumSet;

public final class DatePrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<DatePrototype> {
    public static final JSBuiltinsContainer BUILTINS = new DatePrototypeBuiltins();
    private static final boolean UTC = true;
    private static final boolean NO_UTC = false;

    protected DatePrototypeBuiltins() {
        super(JSDate.PROTOTYPE_NAME, DatePrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, DatePrototype builtinEnum) {
        switch (builtinEnum) {
            case valueOf: 
            case getTime: {
                return DatePrototypeBuiltinsFactory.JSDateValueOfNodeGen.create(context, builtin, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toString: {
                return DatePrototypeBuiltinsFactory.JSDateToStringNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toDateString: {
                return DatePrototypeBuiltinsFactory.JSDateToDateStringNodeGen.create(context, builtin, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toTimeString: {
                return DatePrototypeBuiltinsFactory.JSDateToTimeStringNodeGen.create(context, builtin, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toLocaleString: {
                if (context.isOptionIntl402()) {
                    return DatePrototypeBuiltinsFactory.JSDateToStringIntlNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
                }
                return DatePrototypeBuiltinsFactory.JSDateToStringNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toLocaleDateString: {
                if (context.isOptionIntl402()) {
                    return DatePrototypeBuiltinsFactory.JSDateToLocaleDateStringIntlNodeGen.create(context, builtin, DatePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
                }
                return DatePrototypeBuiltinsFactory.JSDateToLocaleDateStringNodeGen.create(context, builtin, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toLocaleTimeString: {
                if (context.isOptionIntl402()) {
                    return DatePrototypeBuiltinsFactory.JSDateToLocaleTimeStringIntlNodeGen.create(context, builtin, DatePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
                }
                return DatePrototypeBuiltinsFactory.JSDateToLocaleTimeStringNodeGen.create(context, builtin, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toUTCString: {
                return DatePrototypeBuiltinsFactory.JSDateToStringNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toISOString: {
                return DatePrototypeBuiltinsFactory.JSDateToISOStringNodeGen.create(context, builtin, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getFullYear: {
                return DatePrototypeBuiltinsFactory.JSDateGetFullYearNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getYear: {
                return DatePrototypeBuiltinsFactory.JSDateGetYearNodeGen.create(context, builtin, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getUTCFullYear: {
                return DatePrototypeBuiltinsFactory.JSDateGetFullYearNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getMonth: {
                return DatePrototypeBuiltinsFactory.JSDateGetMonthNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getUTCMonth: {
                return DatePrototypeBuiltinsFactory.JSDateGetMonthNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getDate: {
                return DatePrototypeBuiltinsFactory.JSDateGetDateNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getUTCDate: {
                return DatePrototypeBuiltinsFactory.JSDateGetDateNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getDay: {
                return DatePrototypeBuiltinsFactory.JSDateGetDayNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getUTCDay: {
                return DatePrototypeBuiltinsFactory.JSDateGetDayNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getHours: {
                return DatePrototypeBuiltinsFactory.JSDateGetHoursNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getUTCHours: {
                return DatePrototypeBuiltinsFactory.JSDateGetHoursNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getMinutes: {
                return DatePrototypeBuiltinsFactory.JSDateGetMinutesNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getUTCMinutes: {
                return DatePrototypeBuiltinsFactory.JSDateGetMinutesNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getSeconds: {
                return DatePrototypeBuiltinsFactory.JSDateGetSecondsNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getUTCSeconds: {
                return DatePrototypeBuiltinsFactory.JSDateGetSecondsNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getMilliseconds: {
                return DatePrototypeBuiltinsFactory.JSDateGetMillisecondsNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getUTCMilliseconds: {
                return DatePrototypeBuiltinsFactory.JSDateGetMillisecondsNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case setTime: {
                return DatePrototypeBuiltinsFactory.JSDateSetTimeNodeGen.create(context, builtin, DatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case setDate: {
                return DatePrototypeBuiltinsFactory.JSDateSetDateNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case setUTCDate: {
                return DatePrototypeBuiltinsFactory.JSDateSetDateNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case setYear: {
                return DatePrototypeBuiltinsFactory.JSDateSetYearNodeGen.create(context, builtin, DatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case setFullYear: {
                return DatePrototypeBuiltinsFactory.JSDateSetFullYearNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case setUTCFullYear: {
                return DatePrototypeBuiltinsFactory.JSDateSetFullYearNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case setMonth: {
                return DatePrototypeBuiltinsFactory.JSDateSetMonthNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case setUTCMonth: {
                return DatePrototypeBuiltinsFactory.JSDateSetMonthNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case setHours: {
                return DatePrototypeBuiltinsFactory.JSDateSetHoursNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case setUTCHours: {
                return DatePrototypeBuiltinsFactory.JSDateSetHoursNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case setMinutes: {
                return DatePrototypeBuiltinsFactory.JSDateSetMinutesNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case setUTCMinutes: {
                return DatePrototypeBuiltinsFactory.JSDateSetMinutesNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case setSeconds: {
                return DatePrototypeBuiltinsFactory.JSDateSetSecondsNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case setUTCSeconds: {
                return DatePrototypeBuiltinsFactory.JSDateSetSecondsNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case setMilliseconds: {
                return DatePrototypeBuiltinsFactory.JSDateSetMillisecondsNodeGen.create(context, builtin, false, DatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case setUTCMilliseconds: {
                return DatePrototypeBuiltinsFactory.JSDateSetMillisecondsNodeGen.create(context, builtin, true, DatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case getTimezoneOffset: {
                return DatePrototypeBuiltinsFactory.JSDateGetTimezoneOffsetNodeGen.create(context, builtin, DatePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toJSON: {
                return DatePrototypeBuiltinsFactory.JSDateToJSONNodeGen.create(context, builtin, DatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case _toPrimitive: {
                return DatePrototypeBuiltinsFactory.JSDateToPrimitiveNodeGen.create(context, builtin, DatePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSDateToPrimitiveNode
    extends JSBuiltinNode {
        private final ConditionProfile isHintNumber = ConditionProfile.createBinaryProfile();
        private final ConditionProfile isHintStringOrDefault = ConditionProfile.createBinaryProfile();
        @Node.Child
        private IsJSObjectNode isObjectNode = IsJSObjectNode.create();
        @Node.Child
        private OrdinaryToPrimitiveNode ordinaryToPrimitiveHintNumber;
        @Node.Child
        private OrdinaryToPrimitiveNode ordinaryToPrimitiveHintString;

        public JSDateToPrimitiveNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object toPrimitive(Object obj, Object hint) {
            if (!this.isObjectNode.executeBoolean(obj)) {
                throw Errors.createTypeErrorNotAnObject(obj);
            }
            if (this.isHintNumber.profile(Strings.HINT_NUMBER.equals(hint))) {
                if (this.ordinaryToPrimitiveHintNumber == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.ordinaryToPrimitiveHintNumber = this.insert(OrdinaryToPrimitiveNode.createHintNumber());
                }
                return this.ordinaryToPrimitiveHintNumber.execute(obj);
            }
            if (this.isHintStringOrDefault.profile(Strings.HINT_STRING.equals(hint) || Strings.HINT_DEFAULT.equals(hint))) {
                if (this.ordinaryToPrimitiveHintString == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.ordinaryToPrimitiveHintString = this.insert(OrdinaryToPrimitiveNode.createHintString());
                }
                return this.ordinaryToPrimitiveHintString.execute(obj);
            }
            throw Errors.createTypeError("invalid hint");
        }
    }

    public static abstract class JSDateToJSONNode
    extends JSBuiltinNode {
        @Node.Child
        private PropertyGetNode getToISOStringFnNode;
        @Node.Child
        private JSFunctionCallNode callToISOStringFnNode;
        @Node.Child
        private JSToObjectNode toObjectNode;
        @Node.Child
        private JSToPrimitiveNode toPrimitiveNode;

        public JSDateToJSONNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.toObjectNode = JSToObjectNode.createToObject(context);
            this.toPrimitiveNode = JSToPrimitiveNode.createHintNumber();
        }

        @Specialization
        protected Object toJSON(Object thisDate, Object key) {
            double d;
            Object o = this.toObjectNode.execute(thisDate);
            Object tv = this.toPrimitiveNode.execute(o);
            if (JSRuntime.isNumber(tv) && (Double.isInfinite(d = JSRuntime.doubleValue((Number)tv)) || Double.isNaN(d))) {
                return Null.instance;
            }
            Object toISO = this.getToISOStringFn(o);
            return this.getCallToISOStringFnNode().executeCall(JSArguments.create(o, toISO, JSArguments.EMPTY_ARGUMENTS_ARRAY));
        }

        private Object getToISOStringFn(Object obj) {
            if (this.getToISOStringFnNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getToISOStringFnNode = this.insert(PropertyGetNode.create(Strings.TO_ISO_STRING, false, this.getContext()));
            }
            return this.getToISOStringFnNode.getValue(obj);
        }

        private JSFunctionCallNode getCallToISOStringFnNode() {
            if (this.callToISOStringFnNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.callToISOStringFnNode = this.insert(JSFunctionCallNode.createCall());
            }
            return this.callToISOStringFnNode;
        }
    }

    public static abstract class JSDateGetTimezoneOffsetNode
    extends JSDateOperation {
        public JSDateGetTimezoneOffsetNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
        }

        @Specialization
        protected double getTimezoneOffset(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return Double.NaN;
            }
            return (t - JSDate.localTime(t, this)) / 60000.0;
        }
    }

    public static abstract class JSDateSetMillisecondsNode
    extends JSDateOperationWithToNumberNode {
        public JSDateSetMillisecondsNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double setMilliseconds(Object thisDate, Object ms) {
            return JSDate.setMilliseconds(this.asDate(thisDate), this.toDouble(ms), this.isUTC, this);
        }
    }

    public static abstract class JSDateSetSecondsNode
    extends JSDateOperationWithToNumberNode {
        public JSDateSetSecondsNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double setSeconds(Object thisDate, Object[] args) {
            JSDateObject date = this.asDate(thisDate);
            double sec = this.toDouble(JSRuntime.getArgOrUndefined(args, 0));
            double ms = this.toDouble(JSRuntime.getArgOrUndefined(args, 1));
            return JSDate.setSeconds(date, sec, ms, args.length >= 2, this.isUTC, this);
        }
    }

    public static abstract class JSDateSetMinutesNode
    extends JSDateOperationWithToNumberNode {
        public JSDateSetMinutesNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double doOperation(Object thisDate, Object[] args) {
            JSDateObject date = this.asDate(thisDate);
            double min2 = this.toDouble(JSRuntime.getArgOrUndefined(args, 0));
            double sec = this.toDouble(JSRuntime.getArgOrUndefined(args, 1));
            double ms = this.toDouble(JSRuntime.getArgOrUndefined(args, 2));
            return JSDate.setMinutes(date, min2, sec, args.length >= 2, ms, args.length >= 3, this.isUTC, this);
        }
    }

    public static abstract class JSDateSetHoursNode
    extends JSDateOperationWithToNumberNode {
        public JSDateSetHoursNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double setHours(Object thisDate, Object[] args) {
            JSDateObject date = this.asDate(thisDate);
            double hour = this.toDouble(JSRuntime.getArgOrUndefined(args, 0));
            double min2 = this.toDouble(JSRuntime.getArgOrUndefined(args, 1));
            double sec = this.toDouble(JSRuntime.getArgOrUndefined(args, 2));
            double ms = this.toDouble(JSRuntime.getArgOrUndefined(args, 3));
            return JSDate.setHours(date, hour, min2, args.length >= 2, sec, args.length >= 3, ms, args.length >= 4, this.isUTC, this);
        }
    }

    public static abstract class JSDateSetMonthNode
    extends JSDateOperationWithToNumberNode {
        public JSDateSetMonthNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double setMonth(Object thisDate, Object[] args) {
            JSDateObject date = this.asDate(thisDate);
            double month = this.toDouble(JSRuntime.getArgOrUndefined(args, 0));
            double date2 = this.toDouble(JSRuntime.getArgOrUndefined(args, 1));
            return JSDate.setMonth(date, month, date2, args.length >= 2, this.isUTC, this);
        }
    }

    public static abstract class JSDateSetFullYearNode
    extends JSDateOperationWithToNumberNode {
        public JSDateSetFullYearNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double setFullYear(Object thisDate, Object[] args) {
            JSDateObject asDate = this.asDate(thisDate);
            double iYear = this.toDouble(JSRuntime.getArgOrUndefined(args, 0));
            double iMonth = this.toDouble(JSRuntime.getArgOrUndefined(args, 1));
            double iDay = this.toDouble(JSRuntime.getArgOrUndefined(args, 2));
            return JSDate.setFullYear(asDate, iYear, iMonth, args.length >= 2, iDay, args.length >= 3, this.isUTC, this);
        }
    }

    public static abstract class JSDateSetYearNode
    extends JSDateOperationWithToNumberNode {
        public JSDateSetYearNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
        }

        @Specialization
        protected double setYear(Object thisDate, Object year) {
            return JSDate.setYear(this.asDate(thisDate), this.toDouble(year), this);
        }
    }

    public static abstract class JSDateSetDateNode
    extends JSDateOperationWithToNumberNode {
        public JSDateSetDateNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double doOperation(Object thisDate, Object date) {
            return JSDate.setDate(this.asDate(thisDate), this.toDouble(date), this.isUTC, this);
        }
    }

    public static abstract class JSDateSetTimeNode
    extends JSDateOperationWithToNumberNode {
        public JSDateSetTimeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
        }

        @Specialization
        protected double doOperation(Object thisDate, Object time) {
            return JSDate.setTime(this.asDate(thisDate), this.toDouble(time));
        }
    }

    public static abstract class JSDateGetMillisecondsNode
    extends JSDateOperation {
        public JSDateGetMillisecondsNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return Double.NaN;
            }
            return JSDate.msFromTime(t);
        }
    }

    public static abstract class JSDateGetSecondsNode
    extends JSDateOperation {
        public JSDateGetSecondsNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return Double.NaN;
            }
            if (!this.isUTC) {
                t = JSDate.localTime(t, this);
            }
            return JSDate.secFromTime(t);
        }
    }

    public static abstract class JSDateGetMinutesNode
    extends JSDateOperation {
        public JSDateGetMinutesNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return Double.NaN;
            }
            if (!this.isUTC) {
                t = JSDate.localTime(t, this);
            }
            return JSDate.minFromTime(t);
        }
    }

    public static abstract class JSDateGetHoursNode
    extends JSDateOperation {
        public JSDateGetHoursNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return Double.NaN;
            }
            if (!this.isUTC) {
                t = JSDate.localTime(t, this);
            }
            return JSDate.hourFromTime(t);
        }
    }

    public static abstract class JSDateGetDayNode
    extends JSDateOperation {
        public JSDateGetDayNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return Double.NaN;
            }
            t = this.isUTC ? t : JSDate.localTime(t, this);
            return JSDate.weekDay(t);
        }
    }

    public static abstract class JSDateGetDateNode
    extends JSDateOperation {
        public JSDateGetDateNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return Double.NaN;
            }
            t = this.isUTC ? t : JSDate.localTime(t, this);
            return JSDate.dateFromTime(t);
        }
    }

    public static abstract class JSDateGetMonthNode
    extends JSDateOperation {
        public JSDateGetMonthNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (Double.isNaN(t)) {
                return Double.NaN;
            }
            t = this.isUTC ? t : JSDate.localTime(t, this);
            return JSDate.monthFromTime(t);
        }
    }

    public static abstract class JSDateGetYearNode
    extends JSDateOperation {
        public JSDateGetYearNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
        }

        @Specialization
        protected double doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return Double.NaN;
            }
            t = JSDate.localTime(t, this);
            return (double)JSDate.yearFromTime((long)t) - 1900.0;
        }
    }

    public static abstract class JSDateGetFullYearNode
    extends JSDateOperation {
        public JSDateGetFullYearNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected double doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return Double.NaN;
            }
            t = this.isUTC ? t : JSDate.localTime(t, this);
            return JSDate.yearFromTime((long)t);
        }
    }

    public static abstract class JSDateToISOStringNode
    extends JSDateOperation {
        public JSDateToISOStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
        }

        @Specialization
        protected TruffleString doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            JSDateToISOStringNode.checkTimeValid(t);
            return JSDate.toISOStringIntl(t, this.getRealm());
        }
    }

    public static abstract class JSDateToLocaleTimeStringIntlNode
    extends JSDateOperation {
        @Node.Child
        InitializeDateTimeFormatNode initDateTimeFormatNode;

        public JSDateToLocaleTimeStringIntlNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
            this.initDateTimeFormatNode = InitializeDateTimeFormatNode.createInitalizeDateTimeFormatNode(context, "time", "time");
        }

        @Specialization
        protected TruffleString doOperation(Object thisDate, Object locales, Object options) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return JSDate.INVALID_DATE_STRING;
            }
            JSDynamicObject formatter = this.createDateTimeFormat(this.initDateTimeFormatNode, locales, options);
            return JSDateTimeFormat.format(formatter, t);
        }
    }

    public static abstract class JSDateToLocaleTimeStringNode
    extends JSDateOperation {
        public JSDateToLocaleTimeStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
        }

        @Specialization
        protected TruffleString doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return JSDate.INVALID_DATE_STRING;
            }
            return JSDate.format(this.getRealm().getJSShortTimeLocalFormat(), t);
        }
    }

    public static abstract class JSDateToLocaleDateStringIntlNode
    extends JSDateOperation {
        @Node.Child
        InitializeDateTimeFormatNode initDateTimeFormatNode;

        public JSDateToLocaleDateStringIntlNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
            this.initDateTimeFormatNode = InitializeDateTimeFormatNode.createInitalizeDateTimeFormatNode(context, "date", "date");
        }

        @Specialization
        protected TruffleString doOperation(Object thisDate, Object locales, Object options) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return JSDate.INVALID_DATE_STRING;
            }
            JSDynamicObject formatter = this.createDateTimeFormat(this.initDateTimeFormatNode, locales, options);
            return JSDateTimeFormat.format(formatter, t);
        }
    }

    public static abstract class JSDateToLocaleDateStringNode
    extends JSDateOperation {
        public JSDateToLocaleDateStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
        }

        @Specialization
        protected TruffleString doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return JSDate.INVALID_DATE_STRING;
            }
            return JSDate.format(this.getRealm().getJSShortDateLocalFormat(), t);
        }
    }

    public static abstract class JSDateToTimeStringNode
    extends JSDateOperation {
        public JSDateToTimeStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
        }

        @Specialization
        protected TruffleString doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return JSDate.INVALID_DATE_STRING;
            }
            return JSDate.format(this.getRealm().getJSShortTimeFormat(), t);
        }
    }

    public static abstract class JSDateToDateStringNode
    extends JSDateOperation {
        public JSDateToDateStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
        }

        @Specialization
        protected TruffleString doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return JSDate.INVALID_DATE_STRING;
            }
            return JSDate.format(this.getRealm().getJSShortDateFormat(), t);
        }
    }

    public static abstract class JSDateToStringIntlNode
    extends JSDateOperation {
        @Node.Child
        InitializeDateTimeFormatNode initDateTimeFormatNode;

        public JSDateToStringIntlNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
            this.initDateTimeFormatNode = InitializeDateTimeFormatNode.createInitalizeDateTimeFormatNode(context, "any", "all");
        }

        @Specialization
        protected TruffleString doOperation(Object thisDate, Object locales, Object options) {
            double t = this.asDateMillis(thisDate);
            if (this.isNaN.profile(Double.isNaN(t))) {
                return JSDate.INVALID_DATE_STRING;
            }
            JSDynamicObject formatter = this.createDateTimeFormat(this.initDateTimeFormatNode, locales, options);
            return JSDateTimeFormat.format(formatter, t);
        }
    }

    public static abstract class JSDateToStringNode
    extends JSDateOperation {
        public JSDateToStringNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        @Specialization
        protected TruffleString doOperation(Object thisDate) {
            double t = this.asDateMillis(thisDate);
            if (this.isUTC) {
                if (this.isNaN.profile(Double.isNaN(t))) {
                    return JSDate.INVALID_DATE_STRING;
                }
                return JSDate.format(this.getRealm().getJSDateUTCFormat(), t);
            }
            return JSDate.toString(t, this.getRealm());
        }
    }

    public static abstract class JSDateValueOfNode
    extends JSDateOperation {
        public JSDateValueOfNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, false);
        }

        @Specialization
        protected double doOperation(Object thisDate) {
            return this.asDateMillis(thisDate);
        }
    }

    public static abstract class JSDateOperationWithToNumberNode
    extends JSDateOperation {
        @Node.Child
        protected JSToNumberNode toNumberNode = JSToNumberNode.create();

        public JSDateOperationWithToNumberNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin, isUTC);
        }

        protected double toDouble(Object target) {
            return JSRuntime.doubleValue(this.toNumberNode.executeNumber(target));
        }
    }

    public static abstract class JSDateOperation
    extends JSBuiltinNode {
        protected final boolean isUTC;
        private final ConditionProfile isDate = ConditionProfile.createBinaryProfile();
        protected final ConditionProfile isNaN = ConditionProfile.createBinaryProfile();
        @Node.Child
        private InteropLibrary interopLibrary;

        public JSDateOperation(JSContext context, JSBuiltin builtin, boolean isUTC) {
            super(context, builtin);
            this.isUTC = isUTC;
        }

        protected final JSDateObject asDate(Object object) {
            if (this.isDate.profile(JSDate.isJSDate(object))) {
                return (JSDateObject)object;
            }
            throw Errors.createTypeErrorNotADate();
        }

        protected final double asDateMillis(Object thisDate) {
            if (this.isDate.profile(JSDate.isJSDate(thisDate))) {
                return JSDate.getTimeMillisField((JSDateObject)thisDate);
            }
            InteropLibrary interop = this.interopLibrary;
            if (interop == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.interopLibrary = interop = this.insert(InteropLibrary.getFactory().createDispatched(5));
            }
            if (interop.isInstant(thisDate)) {
                return JSDate.getDateValueFromInstant(thisDate, interop);
            }
            throw Errors.createTypeErrorNotADate();
        }

        protected static void checkTimeValid(double time) {
            if (!JSDate.isTimeValid(time)) {
                throw Errors.createRangeError("time value is not a finite number");
            }
        }

        protected JSDynamicObject createDateTimeFormat(InitializeDateTimeFormatNode initDateTimeFormatNode, Object locales, Object options) {
            JSDateTimeFormatObject dateTimeFormatObj = JSDateTimeFormat.create(this.getContext(), this.getRealm());
            initDateTimeFormatNode.executeInit(dateTimeFormatObj, locales, options);
            return dateTimeFormatObj;
        }
    }

    public static enum DatePrototype implements BuiltinEnum<DatePrototype>
    {
        valueOf(0),
        toString(0),
        toDateString(0),
        toTimeString(0),
        toLocaleString(0),
        toLocaleDateString(0),
        toLocaleTimeString(0),
        toUTCString(0),
        toISOString(0),
        getTime(0),
        getFullYear(0),
        getUTCFullYear(0),
        getMonth(0),
        getUTCMonth(0),
        getDate(0),
        getUTCDate(0),
        getDay(0),
        getUTCDay(0),
        getHours(0),
        getUTCHours(0),
        getMinutes(0),
        getUTCMinutes(0),
        getSeconds(0),
        getUTCSeconds(0),
        getMilliseconds(0),
        getUTCMilliseconds(0),
        setTime(1),
        setDate(1),
        setUTCDate(1),
        setFullYear(3),
        setUTCFullYear(3),
        setMonth(2),
        setUTCMonth(2),
        setHours(4),
        setUTCHours(4),
        setMinutes(3),
        setUTCMinutes(3),
        setSeconds(2),
        setUTCSeconds(2),
        setMilliseconds(1),
        setUTCMilliseconds(1),
        getTimezoneOffset(0),
        toJSON(1),
        _toPrimitive(1){

            @Override
            public Object getKey() {
                return Symbol.SYMBOL_TO_PRIMITIVE;
            }

            @Override
            public boolean isWritable() {
                return false;
            }
        }
        ,
        getYear(0),
        setYear(1);

        private final int length;

        private DatePrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isAnnexB() {
            return EnumSet.of(getYear, setYear).contains(this);
        }

        @Override
        public int getECMAScriptVersion() {
            if (this == _toPrimitive) {
                return 6;
            }
            return BuiltinEnum.super.getECMAScriptVersion();
        }
    }
}

