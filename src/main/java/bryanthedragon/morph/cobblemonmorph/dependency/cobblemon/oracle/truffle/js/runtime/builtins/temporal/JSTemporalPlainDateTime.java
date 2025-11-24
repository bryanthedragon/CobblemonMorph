
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDateTimeFunctionBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDateTimePrototypeBuiltins;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTimeObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public final class JSTemporalPlainDateTime
extends JSNonProxy
implements JSConstructorFactory.WithFunctionsAndSpecies,
PrototypeSupplier {
    public static final JSTemporalPlainDateTime INSTANCE = new JSTemporalPlainDateTime();
    public static final TruffleString CLASS_NAME = Strings.constant("PlainDateTime");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("PlainDateTime.prototype");
    public static final TruffleString TO_STRING_TAG = Strings.constant("Temporal.PlainDateTime");

    private JSTemporalPlainDateTime() {
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return TO_STRING_TAG;
    }

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    public static JSTemporalPlainDateTimeObject create(JSContext context, int y, int m, int d, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond, JSDynamicObject calendar, BranchProfile errorBranch) {
        if (!TemporalUtil.isValidISODate(y, m, d)) {
            errorBranch.enter();
            throw TemporalErrors.createRangeErrorDateTimeOutsideRange();
        }
        if (!TemporalUtil.isValidTime(hour, minute, second, millisecond, microsecond, nanosecond)) {
            errorBranch.enter();
            throw TemporalErrors.createRangeErrorDateTimeOutsideRange();
        }
        if (!TemporalUtil.isoDateTimeWithinLimits(y, m, d, hour, minute, second, millisecond, microsecond, nanosecond)) {
            errorBranch.enter();
            throw TemporalErrors.createRangeErrorDateTimeOutsideRange();
        }
        return JSTemporalPlainDateTime.createIntl(context, y, m, d, hour, minute, second, millisecond, microsecond, nanosecond, calendar);
    }

    public static JSTemporalPlainDateTimeObject create(JSContext context, int y, int m, int d, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond, JSDynamicObject calendar) {
        if (!TemporalUtil.isValidISODate(y, m, d)) {
            throw TemporalErrors.createRangeErrorDateTimeOutsideRange();
        }
        if (!TemporalUtil.isValidTime(hour, minute, second, millisecond, microsecond, nanosecond)) {
            throw TemporalErrors.createRangeErrorDateTimeOutsideRange();
        }
        if (!TemporalUtil.isoDateTimeWithinLimits(y, m, d, hour, minute, second, millisecond, microsecond, nanosecond)) {
            throw TemporalErrors.createRangeErrorDateTimeOutsideRange();
        }
        return JSTemporalPlainDateTime.createIntl(context, y, m, d, hour, minute, second, millisecond, microsecond, nanosecond, calendar);
    }

    private static JSTemporalPlainDateTimeObject createIntl(JSContext context, int y, int m, int d, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond, JSDynamicObject calendar) {
        JSRealm realm = JSRealm.get(null);
        JSObjectFactory factory = context.getTemporalPlainDateTimeFactory();
        JSTemporalPlainDateTimeObject object = factory.initProto(new JSTemporalPlainDateTimeObject(factory.getShape(realm), y, m, d, hour, minute, second, millisecond, microsecond, nanosecond, calendar), realm);
        return context.trackAllocation(object);
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject constructor) {
        JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, prototype, constructor);
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.HOUR, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.HOUR));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.MINUTE, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.MINUTE));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.SECOND, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.SECOND));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.MILLISECOND, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.MILLISECOND));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.MICROSECOND, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.MICROSECOND));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.NANOSECOND, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.NANOSECOND));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.CALENDAR, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.CALENDAR));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.YEAR, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.YEAR));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.MONTH, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.MONTH));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.MONTH_CODE, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.MONTH_CODE));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.DAY, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.DAY));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.DAY_OF_WEEK, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.DAY_OF_WEEK));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.DAY_OF_YEAR, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.DAY_OF_YEAR));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.WEEK_OF_YEAR, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.WEEK_OF_YEAR));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.DAYS_IN_WEEK, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.DAYS_IN_WEEK));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.DAYS_IN_MONTH, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.DAYS_IN_MONTH));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.DAYS_IN_YEAR, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.DAYS_IN_YEAR));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.MONTHS_IN_YEAR, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.MONTHS_IN_YEAR));
        JSObjectUtil.putBuiltinAccessorProperty(prototype, TemporalConstants.IN_LEAP_YEAR, realm.lookupAccessor(TemporalPlainDateTimePrototypeBuiltins.BUILTINS, TemporalConstants.IN_LEAP_YEAR));
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, TemporalPlainDateTimePrototypeBuiltins.BUILTINS);
        JSObjectUtil.putToStringTag(prototype, TO_STRING_TAG);
        return prototype;
    }

    @Override
    public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getTemporalPlainDateTimePrototype();
    }

    @Override
    public void fillConstructor(JSRealm realm, JSDynamicObject constructor) {
        JSConstructorFactory.WithFunctionsAndSpecies.super.fillConstructor(realm, constructor);
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm, TemporalPlainDateTimeFunctionBuiltins.BUILTINS);
    }

    public static boolean isJSTemporalPlainDateTime(Object obj) {
        return obj instanceof JSTemporalPlainDateTimeObject;
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString temporalDateTimeToString(int year, int month, int day, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond, JSDynamicObject calendar, Object precision, TemporalUtil.ShowCalendar showCalendar) {
        TruffleString yearString = TemporalUtil.padISOYear(year);
        TruffleString monthString = Strings.format("%1$02d", month);
        TruffleString dayString = Strings.format("%1$02d", day);
        TruffleString hourString = Strings.format("%1$02d", hour);
        TruffleString minuteString = Strings.format("%1$02d", minute);
        TruffleString secondString = TemporalUtil.formatSecondsStringPart(second, millisecond, microsecond, nanosecond, precision);
        TruffleString calendarID = JSRuntime.toString(calendar);
        TruffleString calendarString = TemporalUtil.formatCalendarAnnotation(calendarID, showCalendar);
        return Strings.format("%s-%s-%sT%s:%s%s%s", yearString, monthString, dayString, hourString, minuteString, secondString, calendarString);
    }
}

