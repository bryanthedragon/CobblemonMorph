
package com.oracle.truffle.js.runtime.builtins;

import com.cobblemon.mod.relocations.ibm.icu.impl.Grego;
import com.cobblemon.mod.relocations.ibm.icu.text.DateFormat;
import com.cobblemon.mod.relocations.ibm.icu.util.TimeZone;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.DateFunctionBuiltins;
import com.oracle.truffle.js.builtins.DatePrototypeBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSDateObject;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

public final class JSDate
extends JSNonProxy
implements JSConstructorFactory.WithFunctions,
PrototypeSupplier {
    public static final TruffleString CLASS_NAME = Strings.constant("Date");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("Date.prototype");
    public static final JSDate INSTANCE = new JSDate();
    private static final int HOURS_PER_DAY = 24;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int SECONDS_PER_MINUTE = 60;
    private static final int MS_PER_SECOND = 1000;
    public static final int MS_PER_MINUTE = 60000;
    private static final int MS_PER_HOUR = 3600000;
    public static final int MS_PER_DAY = 86400000;
    public static final double MAX_DATE = 8.64E15;
    public static final double MAX_YEAR_VALUE = 300000.0;
    private static final int DAYS_IN_4_YEARS = 1461;
    private static final int DAYS_IN_100_YEARS = 36524;
    private static final int DAYS_IN_400_YEARS = 146097;
    private static final int DAYS_FROM_1970_TO_2000 = 10957;
    private static final int YEAR_SHIFT = 280000;
    private static final int DAY_SHIFT = 102267900;
    public static final TruffleString INVALID_DATE_STRING = Strings.constant("Invalid Date");

    private JSDate() {
    }

    public static void setTimeMillisField(JSDateObject obj, double timeMillis) {
        assert (JSDate.isJSDate(obj));
        obj.setTimeMillis(timeMillis);
    }

    public static double getTimeMillisField(JSDateObject obj) {
        assert (JSDate.isJSDate(obj));
        return obj.getTimeMillis();
    }

    public static boolean isJSDate(Object obj) {
        return obj instanceof JSDateObject;
    }

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return this.getClassName();
    }

    @Override
    public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
        return this.getClassName(object);
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
        JSObject datePrototype;
        JSContext ctx = realm.getContext();
        if (ctx.getEcmaScriptVersion() < 6) {
            Shape protoShape = JSShape.createPrototypeShape(realm.getContext(), INSTANCE, realm.getObjectPrototype());
            datePrototype = JSDateObject.create(protoShape, Double.NaN);
            JSObjectUtil.setOrVerifyPrototype(ctx, datePrototype, realm.getObjectPrototype());
        } else {
            datePrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        }
        JSObjectUtil.putConstructorProperty(ctx, datePrototype, ctor);
        JSObjectUtil.putFunctionsFromContainer(realm, datePrototype, DatePrototypeBuiltins.BUILTINS);
        if (ctx.isOptionAnnexB()) {
            Object utcStringFunction = JSDynamicObject.getOrNull(datePrototype, Strings.TO_UTC_STRING);
            JSObjectUtil.putDataProperty(ctx, datePrototype, Strings.TO_GMT_STRING, utcStringFunction, JSAttributes.getDefaultNotEnumerable());
        }
        return datePrototype;
    }

    @Override
    public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
        Shape initialShape = JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
        return initialShape;
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm, DateFunctionBuiltins.BUILTINS);
    }

    @CompilerDirectives.TruffleBoundary
    public static double executeConstructor(double[] argsEvaluated, boolean inputIsUTC) {
        double month;
        double year = argsEvaluated.length > 0 ? argsEvaluated[0] : Double.NaN;
        double d = month = argsEvaluated.length > 1 ? argsEvaluated[1] : 0.0;
        if (Double.isNaN(year) || Double.isInfinite(year) || Double.isNaN(month) || Double.isInfinite(month)) {
            return Double.NaN;
        }
        double day = JSDate.getArgOrDefault(argsEvaluated, 2, 1);
        double hour = JSDate.getArgOrDefault(argsEvaluated, 3, 0);
        double minute = JSDate.getArgOrDefault(argsEvaluated, 4, 0);
        double second = JSDate.getArgOrDefault(argsEvaluated, 5, 0);
        double ms = JSDate.getArgOrDefault(argsEvaluated, 6, 0);
        return JSDate.makeDate(JSDate.toFullYear(year), month, day, hour, minute, second, ms, inputIsUTC ? Integer.valueOf(0) : null);
    }

    private static double getArgOrDefault(double[] argsEvaluated, int index, int def) {
        if (argsEvaluated.length > index) {
            return argsEvaluated[index];
        }
        return def;
    }

    private static double day(double t) {
        return JSDate.floor(t / 8.64E7);
    }

    private static double timeWithinDay(double t) {
        return JSDate.secureNegativeModulo(t, 8.64E7);
    }

    public static int dayFromYear(int y) {
        return 365 * (y - 1970) + Math.floorDiv(y - 1969, 4) - Math.floorDiv(y - 1901, 100) + Math.floorDiv(y - 1601, 400);
    }

    @CompilerDirectives.TruffleBoundary
    public static int yearFromTime(long t) {
        long daysAfter1970 = Math.floorDiv((long)t, (int)86400000);
        assert (JSRuntime.longIsRepresentableAsInt(daysAfter1970));
        return JSDate.yearFromDays((int)daysAfter1970);
    }

    public static int yearFromDays(int daysAfter1970) {
        int daysAfter2000 = daysAfter1970 - 10957;
        int days = daysAfter2000 + 102267900;
        assert (days > 0);
        int year = 400 * (days / 146097);
        int remainingDays = days % 146097;
        year += 100 * (--remainingDays / 36524);
        remainingDays %= 36524;
        year += 4 * (++remainingDays / 1461);
        remainingDays %= 1461;
        return (year += --remainingDays / 365) - 280000 + 2000;
    }

    private static boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        }
        if (year % 100 != 0) {
            return true;
        }
        return year % 400 == 0;
    }

    @CompilerDirectives.TruffleBoundary
    public static int monthFromTime(double dt) {
        assert (JSRuntime.doubleIsRepresentableAsLong(dt));
        long t = (long)dt;
        int year = JSDate.yearFromTime(t);
        boolean leapYear = JSDate.isLeapYear(year);
        int day = JSDate.dayWithinYear(t, year);
        return JSDate.monthFromTimeIntl(leapYear, day);
    }

    private static int monthFromTimeIntl(boolean leapYear, int day) {
        assert (0 <= day && day < 365 + (leapYear ? 1 : 0)) : "should not reach here";
        if (day < 31) {
            return 0;
        }
        if (!leapYear) {
            if (day < 59) {
                return 1;
            }
            if (day < 90) {
                return 2;
            }
            if (day < 120) {
                return 3;
            }
            if (day < 151) {
                return 4;
            }
            if (day < 181) {
                return 5;
            }
            if (day < 212) {
                return 6;
            }
            if (day < 243) {
                return 7;
            }
            if (day < 273) {
                return 8;
            }
            if (day < 304) {
                return 9;
            }
            if (day < 334) {
                return 10;
            }
            return 11;
        }
        if (day < 60) {
            return 1;
        }
        if (day < 91) {
            return 2;
        }
        if (day < 121) {
            return 3;
        }
        if (day < 152) {
            return 4;
        }
        if (day < 182) {
            return 5;
        }
        if (day < 213) {
            return 6;
        }
        if (day < 244) {
            return 7;
        }
        if (day < 274) {
            return 8;
        }
        if (day < 305) {
            return 9;
        }
        if (day < 335) {
            return 10;
        }
        return 11;
    }

    private static int dayWithinYear(long t, int year) {
        return (int)Math.floorDiv((long)t, (int)86400000) - JSDate.dayFromYear(year);
    }

    @CompilerDirectives.TruffleBoundary
    public static int dateFromTime(double dt) {
        assert (JSRuntime.doubleIsRepresentableAsLong(dt));
        long t = (long)dt;
        int year = JSDate.yearFromTime(t);
        int day = JSDate.dayWithinYear(t, year);
        return JSDate.dateFromDayInYear(year, day);
    }

    public static int dateFromDayInYear(int year, int day) {
        if (day < 31) {
            return day + 1;
        }
        boolean leapYear = JSDate.isLeapYear(year);
        int dayMinusLeap = day - (leapYear ? 1 : 0);
        switch (JSDate.monthFromTimeIntl(leapYear, day)) {
            case 1: {
                return day - 30;
            }
            case 2: {
                return dayMinusLeap - 58;
            }
            case 3: {
                return dayMinusLeap - 89;
            }
            case 4: {
                return dayMinusLeap - 119;
            }
            case 5: {
                return dayMinusLeap - 150;
            }
            case 6: {
                return dayMinusLeap - 180;
            }
            case 7: {
                return dayMinusLeap - 211;
            }
            case 8: {
                return dayMinusLeap - 242;
            }
            case 9: {
                return dayMinusLeap - 272;
            }
            case 10: {
                return dayMinusLeap - 303;
            }
            case 11: {
                return dayMinusLeap - 333;
            }
        }
        assert (false) : "should not reach here";
        return -1;
    }

    public static double weekDay(double t) {
        int result = ((int)JSDate.day(t) + 4) % 7;
        return result >= 0 ? (double)result : (double)(result + 7);
    }

    public static double localTime(double t, Node node) {
        return t + (double)JSDate.localTZA(t, true, node);
    }

    private static double utc(double t, Node node) {
        return t - (double)JSDate.localTZA(t, false, node);
    }

    public static long localTZA(double t, boolean isUTC, Node node) {
        return JSDate.localTZA(t, isUTC, JSRealm.get(node).getLocalTimeZone());
    }

    private static int getOffset(TimeZone timeZone, long date, int[] fields) {
        Grego.timeToFields(date, fields);
        return timeZone.getOffset(1, fields[0], fields[1], fields[2], fields[3], fields[5]);
    }

    private static int getOffset(TimeZone timeZone, long t, boolean isUTC) {
        int dstOffset;
        int rawOffset = timeZone.getRawOffset();
        long date = isUTC ? t + (long)rawOffset : t;
        int[] fields = new int[6];
        int offset = JSDate.getOffset(timeZone, date, fields);
        if (isUTC) {
            return offset;
        }
        if (offset != rawOffset) {
            int dstOffset2 = offset - rawOffset;
            return JSDate.getOffset(timeZone, date - (long)dstOffset2, fields);
        }
        int dstSavings = timeZone.getDSTSavings();
        if (dstSavings == 0) {
            dstSavings = 3600000;
        }
        if ((dstOffset = (offset = JSDate.getOffset(timeZone, date - (long)dstSavings, fields)) - rawOffset) != 0 && dstOffset != dstSavings) {
            offset = JSDate.getOffset(timeZone, date - (long)dstOffset, fields);
        }
        return offset;
    }

    @CompilerDirectives.TruffleBoundary
    public static int localTZA(double t, boolean isUTC, TimeZone timeZone) {
        return JSDate.getOffset(timeZone, (long)t, isUTC);
    }

    @CompilerDirectives.TruffleBoundary
    public static int hourFromTime(double t) {
        return (int)JSDate.secureNegativeModulo(JSDate.floor(t / 3600000.0), 24.0);
    }

    @CompilerDirectives.TruffleBoundary
    public static int minFromTime(double t) {
        return (int)JSDate.secureNegativeModulo(JSDate.floor(t / 60000.0), 60.0);
    }

    @CompilerDirectives.TruffleBoundary
    public static int secFromTime(double t) {
        return (int)JSDate.secureNegativeModulo(JSDate.floor(t / 1000.0), 60.0);
    }

    @CompilerDirectives.TruffleBoundary
    public static int msFromTime(double t) {
        return (int)JSDate.secureNegativeModulo(t, 1000.0);
    }

    private static double secureNegativeModulo(double value2, double modulo) {
        double result = value2 % modulo;
        if (result >= 0.0) {
            return result;
        }
        return result + modulo;
    }

    @CompilerDirectives.TruffleBoundary
    public static double makeTime(double hour, double min2, double sec, double ms) {
        if (!(JSDate.isFinite(hour) && JSDate.isFinite(min2) && JSDate.isFinite(sec) && JSDate.isFinite(ms))) {
            return Double.NaN;
        }
        double h = JSRuntime.truncateDouble(hour);
        double m = JSRuntime.truncateDouble(min2);
        double s = JSRuntime.truncateDouble(sec);
        double milli = JSRuntime.truncateDouble(ms);
        return h * 3600000.0 + m * 60000.0 + s * 1000.0 + milli;
    }

    @CompilerDirectives.TruffleBoundary
    public static double makeDay(double year, double month, double date) {
        if (!(JSDate.isFinite(year) && JSDate.isFinite(month) && JSDate.isFinite(date))) {
            return Double.NaN;
        }
        double y = JSRuntime.truncateDouble(year);
        double m = JSRuntime.truncateDouble(month);
        double dt = JSRuntime.truncateDouble(date);
        double ym = y + JSDate.floor(m / 12.0);
        int mn = (int)(m % 12.0);
        if (mn < 0) {
            mn += 12;
        }
        if (ym < -300000.0 || ym > 300000.0) {
            return Double.NaN;
        }
        double t = LocalDate.of((int)ym, mn + 1, 1).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
        return JSDate.day(t) + dt - 1.0;
    }

    @CompilerDirectives.TruffleBoundary
    public static double makeDate(double day, double time) {
        if (!JSDate.isFinite(day) || !JSDate.isFinite(time)) {
            return Double.NaN;
        }
        return day * 8.64E7 + time;
    }

    @CompilerDirectives.TruffleBoundary
    public static double makeDate(double y, double m, double d, double h, double min2, double sec, double ms, Integer timezone) {
        double day = JSDate.makeDay(y, m, d);
        double time = JSDate.makeTime(h, min2, sec, ms);
        double date = JSDate.makeDate(day, time);
        date = timezone == null ? JSDate.utc(date, null) : (date -= (double)(timezone * 60000));
        return JSDate.timeClip(date);
    }

    public static double timeClip(double time) {
        if (Double.isInfinite(time) || Double.isNaN(time) || Math.abs(time) > 8.64E15) {
            return Double.NaN;
        }
        return Double.valueOf(time).longValue();
    }

    private static boolean isFinite(double d) {
        return !Double.isNaN(d) && !Double.isInfinite(d);
    }

    private static double floor(double d) {
        return Math.floor(d);
    }

    public static JSDateObject create(JSContext context, JSRealm realm, double timeMillis) {
        JSObjectFactory factory = context.getDateFactory();
        JSDateObject obj = JSDateObject.create(factory.getShape(realm), timeMillis);
        factory.initProto(obj, realm);
        return context.trackAllocation(obj);
    }

    public static double setTime(JSDateObject thisDate, double time) {
        double v = JSDate.timeClip(time);
        JSDate.setTimeMillisField(thisDate, v);
        return v;
    }

    public static double setMilliseconds(JSDateObject thisDate, double ms, boolean isUTC, Node node) {
        double t = JSDate.localTime(JSDate.getTimeMillisField(thisDate), isUTC, node);
        double time = JSDate.makeTime(JSDate.hourFromTime(t), JSDate.minFromTime(t), JSDate.secFromTime(t), ms);
        double u = JSDate.timeClip(JSDate.utc(JSDate.makeDate(JSDate.day(t), time), isUTC, node));
        JSDate.setTimeMillisField(thisDate, u);
        return u;
    }

    public static double setSeconds(JSDateObject thisDate, double s, double ms, boolean msSpecified, boolean isUTC, Node node) {
        double t = JSDate.localTime(JSDate.getTimeMillisField(thisDate), isUTC, node);
        double milli = msSpecified ? ms : (double)JSDate.msFromTime(t);
        double date = JSDate.makeDate(JSDate.day(t), JSDate.makeTime(JSDate.hourFromTime(t), JSDate.minFromTime(t), s, milli));
        double u = JSDate.timeClip(JSDate.utc(date, isUTC, node));
        JSDate.setTimeMillisField(thisDate, u);
        return u;
    }

    public static double setMinutes(JSDateObject thisDate, double m, double s, boolean sSpecified, double ms, boolean msSpecified, boolean isUTC, Node node) {
        double t = JSDate.localTime(JSDate.getTimeMillisField(thisDate), isUTC, node);
        double milli = msSpecified ? ms : (double)JSDate.msFromTime(t);
        double sec = sSpecified ? s : (double)JSDate.secFromTime(t);
        double date = JSDate.makeDate(JSDate.day(t), JSDate.makeTime(JSDate.hourFromTime(t), m, sec, milli));
        double u = JSDate.timeClip(JSDate.utc(date, isUTC, node));
        JSDate.setTimeMillisField(thisDate, u);
        return u;
    }

    public static double setHours(JSDateObject thisDate, double h, double m, boolean mSpecified, double s, boolean sSpecified, double ms, boolean msSpecified, boolean isUTC, Node node) {
        double t = JSDate.localTime(JSDate.getTimeMillisField(thisDate), isUTC, node);
        double milli = msSpecified ? ms : (double)JSDate.msFromTime(t);
        double sec = sSpecified ? s : (double)JSDate.secFromTime(t);
        double min2 = mSpecified ? m : (double)JSDate.minFromTime(t);
        double date = JSDate.makeDate(JSDate.day(t), JSDate.makeTime(h, min2, sec, milli));
        double u = JSDate.timeClip(JSDate.utc(date, isUTC, node));
        JSDate.setTimeMillisField(thisDate, u);
        return u;
    }

    public static double setDate(JSDateObject thisDate, double date, boolean isUTC, Node node) {
        double u;
        double t = JSDate.localTime(JSDate.getTimeMillisField(thisDate), isUTC, node);
        if (Double.isNaN(t)) {
            u = Double.NaN;
        } else {
            double newDate = JSDate.makeDate(JSDate.makeDay(JSDate.yearFromTime((long)t), JSDate.monthFromTime(t), date), JSDate.timeWithinDay(t));
            u = JSDate.timeClip(JSDate.utc(newDate, isUTC, node));
        }
        JSDate.setTimeMillisField(thisDate, u);
        return u;
    }

    public static double setMonth(JSDateObject thisDate, double month, double date, boolean dateSpecified, boolean isUTC, Node node) {
        double newDate;
        double t = JSDate.localTime(JSDate.getTimeMillisField(thisDate), isUTC, node);
        if (Double.isNaN(t)) {
            newDate = Double.NaN;
        } else {
            double dt = dateSpecified ? date : (double)JSDate.dateFromTime(t);
            newDate = JSDate.timeClip(JSDate.utc(JSDate.makeDate(JSDate.makeDay(JSDate.yearFromTime((long)t), month, dt), JSDate.timeWithinDay(t)), isUTC, node));
        }
        JSDate.setTimeMillisField(thisDate, newDate);
        return newDate;
    }

    public static double setFullYear(JSDateObject thisDate, double year, double month, boolean monthSpecified, double date, boolean dateSpecified, boolean isUTC, Node node) {
        double timeFieldValue = JSDate.getTimeMillisField(thisDate);
        double t = Double.isNaN(timeFieldValue) ? 0.0 : JSDate.localTime(timeFieldValue, isUTC, node);
        double dt = dateSpecified ? date : (double)JSDate.dateFromTime(t);
        double m = monthSpecified ? month : (double)JSDate.monthFromTime(t);
        double newDate = JSDate.makeDate(JSDate.makeDay(year, m, dt), JSDate.timeWithinDay(t));
        double u = JSDate.timeClip(JSDate.utc(newDate, isUTC, node));
        JSDate.setTimeMillisField(thisDate, u);
        return u;
    }

    public static double setYear(JSDateObject thisDate, double year, Node node) {
        double t = JSDate.getTimeMillisField(thisDate);
        double d = t = Double.isNaN(t) ? 0.0 : JSDate.localTime(t, node);
        if (Double.isNaN(year)) {
            JSDate.setTimeMillisField(thisDate, Double.NaN);
            return Double.NaN;
        }
        double fullYear = JSDate.toFullYear(year);
        double r5 = JSDate.makeDay(fullYear, JSDate.monthFromTime(t), JSDate.dateFromTime(t));
        double r6 = JSDate.timeClip(JSDate.utc(JSDate.makeDate(r5, JSDate.timeWithinDay(t)), node));
        JSDate.setTimeMillisField(thisDate, r6);
        return r6;
    }

    private static double toFullYear(double year) {
        if (-1.0 < year && year < 100.0) {
            return 1900 + (int)year;
        }
        return year;
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString format(DateFormat format, double time) {
        return Strings.fromJavaString(format.format(time));
    }

    public static TruffleString toString(double time, JSRealm realm) {
        if (Double.isNaN(time)) {
            return INVALID_DATE_STRING;
        }
        return JSDate.format(realm.getDateToStringFormat(), time);
    }

    public static TruffleString toISOStringIntl(double time, JSRealm realm) {
        return JSDate.format(realm.getJSDateISOFormat(time), time);
    }

    public static boolean isTimeValid(double time) {
        return !Double.isNaN(time) && !Double.isInfinite(time);
    }

    private static double localTime(double time, boolean isUTC, Node node) {
        return isUTC ? time : JSDate.localTime(time, node);
    }

    private static double utc(double time, boolean isUTC, Node node) {
        return isUTC ? time : JSDate.utc(time, node);
    }

    public static boolean isValidDate(JSDateObject date) {
        return !Double.isNaN(JSDate.getTimeMillisField(date));
    }

    @CompilerDirectives.TruffleBoundary
    public static Instant asInstant(JSDateObject date) {
        assert (JSDate.isValidDate(date));
        return Instant.ofEpochMilli((long)JSDate.getTimeMillisField(date));
    }

    @CompilerDirectives.TruffleBoundary
    public static LocalDate asLocalDate(JSDateObject date, JSRealm realm) {
        return LocalDate.from(JSDate.asInstant(date).atZone(realm.getLocalTimeZoneId()));
    }

    @CompilerDirectives.TruffleBoundary
    public static LocalTime asLocalTime(JSDateObject date, JSRealm realm) {
        return LocalTime.from(JSDate.asInstant(date).atZone(realm.getLocalTimeZoneId()));
    }

    public static double getDateValueFromInstant(Object receiver, InteropLibrary interop) {
        Instant instant;
        try {
            instant = interop.asInstant(receiver);
        }
        catch (UnsupportedMessageException e) {
            throw Errors.createTypeErrorInteropException(receiver, e, "asInstant", null);
        }
        try {
            return instant.toEpochMilli();
        }
        catch (ArithmeticException e) {
            return Double.NaN;
        }
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        double time = JSDate.getTimeMillisField((JSDateObject)obj);
        TruffleString formattedDate = JSDate.isTimeValid(time) ? JSDate.toISOStringIntl(time, JSRealm.get(null)) : INVALID_DATE_STRING;
        if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
            return Strings.concatAll(Strings.BRACKET_DATE_SPC, formattedDate, Strings.BRACKET_CLOSE);
        }
        return formattedDate;
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getDatePrototype();
    }
}

