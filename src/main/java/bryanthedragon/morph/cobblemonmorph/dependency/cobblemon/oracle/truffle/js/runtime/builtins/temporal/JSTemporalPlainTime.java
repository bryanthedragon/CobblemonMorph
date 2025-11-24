
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainTimeFunctionBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainTimePrototypeBuiltins;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerThrowOnInfinityNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendarObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTimeObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public final class JSTemporalPlainTime
extends JSNonProxy
implements JSConstructorFactory.WithFunctionsAndSpecies,
PrototypeSupplier {
    public static final JSTemporalPlainTime INSTANCE = new JSTemporalPlainTime();
    public static final TruffleString CLASS_NAME = Strings.constant("PlainTime");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("PlainTime.prototype");
    public static final TruffleString TO_STRING_TAG = Strings.constant("Temporal.PlainTime");

    private JSTemporalPlainTime() {
    }

    public static JSTemporalPlainTimeObject create(JSContext context, int hours, int minutes, int seconds, int milliseconds, int microseconds, int nanoseconds, BranchProfile errorBranch) {
        if (!TemporalUtil.isValidTime(hours, minutes, seconds, milliseconds, microseconds, nanoseconds)) {
            errorBranch.enter();
            throw TemporalErrors.createRangeErrorTimeOutsideRange();
        }
        JSRealm realm = JSRealm.get(null);
        JSTemporalCalendarObject calendar = TemporalUtil.getISO8601Calendar(context, realm, errorBranch);
        JSObjectFactory factory = context.getTemporalPlainTimeFactory();
        JSTemporalPlainTimeObject obj = factory.initProto(new JSTemporalPlainTimeObject(factory.getShape(realm), hours, minutes, seconds, milliseconds, microseconds, nanoseconds, calendar), realm);
        return context.trackAllocation(obj);
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return TO_STRING_TAG;
    }

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject constructor) {
        JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, prototype, constructor);
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, TemporalPlainTimePrototypeBuiltins.BUILTINS);
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.CALENDAR, realm.lookupAccessor(TemporalPlainTimePrototypeBuiltins.BUILTINS, TemporalConstants.CALENDAR));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.HOUR, realm.lookupAccessor(TemporalPlainTimePrototypeBuiltins.BUILTINS, TemporalConstants.HOUR));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.MINUTE, realm.lookupAccessor(TemporalPlainTimePrototypeBuiltins.BUILTINS, TemporalConstants.MINUTE));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.SECOND, realm.lookupAccessor(TemporalPlainTimePrototypeBuiltins.BUILTINS, TemporalConstants.SECOND));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.MILLISECOND, realm.lookupAccessor(TemporalPlainTimePrototypeBuiltins.BUILTINS, TemporalConstants.MILLISECOND));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.MICROSECOND, realm.lookupAccessor(TemporalPlainTimePrototypeBuiltins.BUILTINS, TemporalConstants.MICROSECOND));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.NANOSECOND, realm.lookupAccessor(TemporalPlainTimePrototypeBuiltins.BUILTINS, TemporalConstants.NANOSECOND));
        JSObjectUtil.putToStringTag(prototype, TO_STRING_TAG);
        return prototype;
    }

    @Override
    public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getTemporalPlainTimePrototype();
    }

    @Override
    public void fillConstructor(JSRealm realm, JSDynamicObject constructor) {
        JSConstructorFactory.WithFunctionsAndSpecies.super.fillConstructor(realm, constructor);
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm, TemporalPlainTimeFunctionBuiltins.BUILTINS);
    }

    public static boolean isJSTemporalPlainTime(Object obj) {
        return obj instanceof JSTemporalPlainTimeObject;
    }

    public static JSDynamicObject toPartialTime(JSDynamicObject temporalTimeLike, IsObjectNode isObject, JSToIntegerThrowOnInfinityNode toInt, JSContext ctx) {
        if (!isObject.executeBoolean(temporalTimeLike)) {
            throw TemporalErrors.createTypeErrorTemporalTimeExpected();
        }
        JSRealm realm = JSRealm.get(null);
        JSObject result = JSOrdinary.create(ctx, realm);
        boolean any = false;
        for (TruffleString property : TemporalUtil.TIME_LIKE_PROPERTIES) {
            Object value2 = JSObject.get(temporalTimeLike, property);
            if (value2 == Undefined.instance) continue;
            any = true;
            value2 = toInt.executeDouble(value2);
            JSObjectUtil.putDataProperty(ctx, result, (Object)property, value2);
        }
        if (!any) {
            throw TemporalErrors.createTypeErrorTemporalTimePropertyExpected();
        }
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString temporalTimeToString(long hour, long minute, long second, long millisecond, long microsecond, long nanosecond, Object precision) {
        TruffleString hourString = Strings.format("%1$02d", hour);
        TruffleString minuteString = Strings.format("%1$02d", minute);
        TruffleString secondString = TemporalUtil.formatSecondsStringPart(second, millisecond, microsecond, nanosecond, precision);
        return Strings.format("%s:%s%s", hourString, minuteString, secondString);
    }
}

