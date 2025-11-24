
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalTimeZonePrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarWithISODefaultNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalInstantNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstant;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstantObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalTimeZone;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalTimeZoneObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.OptionalLong;

public class TemporalTimeZonePrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalTimeZonePrototype> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalTimeZonePrototypeBuiltins();

    protected TemporalTimeZonePrototypeBuiltins() {
        super(JSTemporalTimeZone.PROTOTYPE_NAME, TemporalTimeZonePrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalTimeZonePrototype builtinEnum) {
        switch (builtinEnum) {
            case id: {
                return TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetterNodeGen.create(context, builtin, builtinEnum, TemporalTimeZonePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case getOffsetNanosecondsFor: {
                return TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetOffsetNanosecondsForNodeGen.create(context, builtin, TemporalTimeZonePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case getOffsetStringFor: {
                return TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetOffsetStringForNodeGen.create(context, builtin, TemporalTimeZonePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case getPlainDateTimeFor: {
                return TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetPlainDateTimeForNodeGen.create(context, builtin, TemporalTimeZonePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case getInstantFor: {
                return TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetInstantForNodeGen.create(context, builtin, TemporalTimeZonePrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case getPossibleInstantsFor: {
                return TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetPossibleInstantsForNodeGen.create(context, builtin, TemporalTimeZonePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case getNextTransition: {
                return TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetNextOrPreviousTransitionNodeGen.create(context, builtin, true, TemporalTimeZonePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case getPreviousTransition: {
                return TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneGetNextOrPreviousTransitionNodeGen.create(context, builtin, false, TemporalTimeZonePrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case toString: {
                return TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneToStringNodeGen.create(context, builtin, TemporalTimeZonePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toJSON: {
                return TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneToJSONNodeGen.create(context, builtin, TemporalTimeZonePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case valueOf: {
                return TemporalTimeZonePrototypeBuiltinsFactory.JSTemporalTimeZoneValueOfNodeGen.create(context, builtin, TemporalTimeZonePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalTimeZoneGetNextOrPreviousTransition
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        private final boolean isNext;

        protected JSTemporalTimeZoneGetNextOrPreviousTransition(JSContext context, JSBuiltin builtin, boolean isNext) {
            super(context, builtin);
            this.isNext = isNext;
        }

        @Specialization
        protected JSDynamicObject getTransition(Object thisObj, Object startingPointParam, @Cached(value="create(getContext())") ToTemporalInstantNode toTemporalInstantNode) {
            JSTemporalTimeZoneObject timeZone = this.requireTemporalTimeZone(thisObj);
            JSTemporalInstantObject startingPoint = toTemporalInstantNode.execute(startingPointParam);
            if (timeZone.getNanoseconds() != null) {
                return Null.instance;
            }
            OptionalLong transition = this.isNext ? TemporalUtil.getIANATimeZoneNextTransition(startingPoint.getNanoseconds(), timeZone.getIdentifier()) : TemporalUtil.getIANATimeZonePreviousTransition(startingPoint.getNanoseconds(), timeZone.getIdentifier());
            if (transition.isEmpty()) {
                return Null.instance;
            }
            return JSTemporalInstant.create(this.getContext(), this.getRealm(), BigInt.valueOf(transition.orElse(0L)));
        }
    }

    public static abstract class JSTemporalTimeZoneGetPossibleInstantsFor
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalTimeZoneGetPossibleInstantsFor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected JSDynamicObject getPossibleInstantsFor(Object thisObj, Object dateTimeParam, @Cached(value="create(getContext())") ToTemporalDateTimeNode toTemporalDateTime) {
            JSTemporalTimeZoneObject timeZone = this.requireTemporalTimeZone(thisObj);
            JSTemporalPlainDateTimeObject dateTime = (JSTemporalPlainDateTimeObject)toTemporalDateTime.executeDynamicObject(dateTimeParam, Undefined.instance);
            JSRealm realm = this.getRealm();
            if (timeZone.getNanoseconds() != null) {
                BigInteger epochNanoseconds = TemporalUtil.getEpochFromISOParts(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(), dateTime.getMillisecond(), dateTime.getMicrosecond(), dateTime.getNanosecond());
                JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), realm, new BigInt(epochNanoseconds.subtract(timeZone.getNanoseconds().bigIntegerValue())));
                ArrayList<JSTemporalInstantObject> list = new ArrayList<JSTemporalInstantObject>();
                list.add(instant);
                return JSRuntime.createArrayFromList(this.getContext(), realm, list);
            }
            List<BigInt> possibleEpochNanoseconds = TemporalUtil.getIANATimeZoneEpochValue(timeZone.getIdentifier(), dateTime.getYear(), dateTime.getMonth(), dateTime.getDay(), dateTime.getHour(), dateTime.getMinute(), dateTime.getSecond(), dateTime.getMillisecond(), dateTime.getMicrosecond(), dateTime.getNanosecond());
            ArrayList<JSTemporalInstantObject> possibleInstants = new ArrayList<JSTemporalInstantObject>();
            for (BigInt epochNanoseconds : possibleEpochNanoseconds) {
                JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), realm, epochNanoseconds);
                possibleInstants.add(instant);
            }
            return JSRuntime.createArrayFromList(this.getContext(), realm, possibleInstants);
        }
    }

    public static abstract class JSTemporalTimeZoneGetInstantFor
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalTimeZoneGetInstantFor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject getInstantFor(Object thisObj, Object dateTimeParam, Object optionsParam, @Cached(value="create(getContext())") ToTemporalDateTimeNode toTemporalDateTime, @Cached TruffleString.EqualNode equalNode) {
            JSTemporalTimeZoneObject timeZone = this.requireTemporalTimeZone(thisObj);
            JSTemporalPlainDateTimeObject dateTime = (JSTemporalPlainDateTimeObject)toTemporalDateTime.executeDynamicObject(dateTimeParam, Undefined.instance);
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            TemporalUtil.Disambiguation disambiguation = TemporalUtil.toTemporalDisambiguation(options, this.getOptionNode(), equalNode);
            return TemporalUtil.builtinTimeZoneGetInstantFor(this.getContext(), timeZone, dateTime, disambiguation);
        }
    }

    public static abstract class JSTemporalTimeZoneGetPlainDateTimeFor
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalTimeZoneGetPlainDateTimeFor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject getPlainDateTimeFor(Object thisObj, Object instantParam, Object calendarLike, @Cached(value="create(getContext())") ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode, @Cached(value="create(getContext())") ToTemporalInstantNode toTemporalInstantNode) {
            JSTemporalTimeZoneObject timeZone = this.requireTemporalTimeZone(thisObj);
            JSTemporalInstantObject instant = toTemporalInstantNode.execute(instantParam);
            JSDynamicObject calendar = toTemporalCalendarWithISODefaultNode.executeDynamicObject(calendarLike);
            return TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.getContext(), timeZone, instant, calendar);
        }
    }

    public static abstract class JSTemporalTimeZoneGetOffsetStringFor
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalTimeZoneGetOffsetStringFor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected TruffleString getOffsetStringFor(Object thisObj, Object instantParam, @Cached(value="create(getContext())") ToTemporalInstantNode toTemporalInstantNode) {
            JSTemporalTimeZoneObject timeZone = this.requireTemporalTimeZone(thisObj);
            JSTemporalInstantObject instant = toTemporalInstantNode.execute(instantParam);
            return TemporalUtil.builtinTimeZoneGetOffsetStringFor(timeZone, instant);
        }
    }

    public static abstract class JSTemporalTimeZoneGetOffsetNanosecondsFor
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalTimeZoneGetOffsetNanosecondsFor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected double getOffsetNanosecondsFor(Object thisObj, Object instantParam, @Cached(value="create(getContext())") ToTemporalInstantNode toTemporalInstantNode) {
            JSTemporalTimeZoneObject timeZone = this.requireTemporalTimeZone(thisObj);
            JSTemporalInstantObject instant = toTemporalInstantNode.execute(instantParam);
            if (timeZone.getNanoseconds() != null) {
                return timeZone.getNanoseconds().doubleValue();
            }
            return TemporalUtil.getIANATimeZoneOffsetNanoseconds(instant.getNanoseconds(), timeZone.getIdentifier());
        }
    }

    public static abstract class JSTemporalTimeZoneValueOf
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalTimeZoneValueOf(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object valueOf(Object thisObj) {
            throw Errors.createTypeError("Not supported.");
        }
    }

    public static abstract class JSTemporalTimeZoneToJSON
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalTimeZoneToJSON(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected TruffleString toJSON(Object thisObj, @Cached(value="create()") JSToStringNode toString) {
            JSTemporalTimeZoneObject timeZone = this.requireTemporalTimeZone(thisObj);
            return toString.executeString(timeZone);
        }
    }

    public static abstract class JSTemporalTimeZoneToString
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
        protected JSTemporalTimeZoneToString(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected TruffleString toString(Object thisObj) {
            return this.requireTemporalTimeZone(thisObj).getIdentifier();
        }
    }

    public static abstract class JSTemporalTimeZoneGetterNode
    extends JSBuiltinNode {
        public final TemporalTimeZonePrototype property;

        public JSTemporalTimeZoneGetterNode(JSContext context, JSBuiltin builtin, TemporalTimeZonePrototype property) {
            super(context, builtin);
            this.property = property;
        }

        @Specialization(guards={"isJSTemporalTimeZone(thisObj)"})
        protected TruffleString timeZoneGetter(Object thisObj, @Cached JSToStringNode toStringNode) {
            JSTemporalTimeZoneObject timeZone = (JSTemporalTimeZoneObject)thisObj;
            switch (this.property) {
                case id: {
                    return toStringNode.executeString(timeZone);
                }
            }
            CompilerDirectives.transferToInterpreter();
            throw Errors.shouldNotReachHere();
        }

        @Specialization(guards={"!isJSTemporalTimeZone(thisObj)"})
        protected static int error(Object thisObj) {
            throw TemporalErrors.createTypeErrorTemporalTimeZoneExpected();
        }
    }

    public static enum TemporalTimeZonePrototype implements BuiltinEnum<TemporalTimeZonePrototype>
    {
        id(0),
        getOffsetNanosecondsFor(1),
        getOffsetStringFor(1),
        getPlainDateTimeFor(1),
        getInstantFor(1),
        getPossibleInstantsFor(1),
        getNextTransition(1),
        getPreviousTransition(1),
        toString(0),
        toJSON(0),
        valueOf(0);

        private final int length;

        private TemporalTimeZonePrototype(int length) {
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

