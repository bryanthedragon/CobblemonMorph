
package com.oracle.truffle.js.runtime.builtins.intl;

import com.cobblemon.mod.relocations.ibm.icu.text.ConstrainedFieldPosition;
import com.cobblemon.mod.relocations.ibm.icu.text.DateFormat;
import com.cobblemon.mod.relocations.ibm.icu.text.DateIntervalFormat;
import com.cobblemon.mod.relocations.ibm.icu.text.DateTimePatternGenerator;
import com.cobblemon.mod.relocations.ibm.icu.text.NumberingSystem;
import com.cobblemon.mod.relocations.ibm.icu.text.SimpleDateFormat;
import com.cobblemon.mod.relocations.ibm.icu.util.Calendar;
import com.cobblemon.mod.relocations.ibm.icu.util.GregorianCalendar;
import com.cobblemon.mod.relocations.ibm.icu.util.TimeZone;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.DateTimeFormatFunctionBuiltins;
import com.oracle.truffle.js.builtins.intl.DateTimeFormatPrototypeBuiltins;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSDate;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormatObject;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import com.oracle.truffle.js.runtime.util.LazyValue;
import java.text.AttributedCharacterIterator;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.UnmodifiableEconomicMap;

public final class JSDateTimeFormat
extends JSNonProxy
implements JSConstructorFactory.WithFunctions,
PrototypeSupplier {
    public static final TruffleString CLASS_NAME = Strings.constant("DateTimeFormat");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("DateTimeFormat.prototype");
    public static final TruffleString TO_STRING_TAG = Strings.constant("Intl.DateTimeFormat");
    public static final TruffleString GET_FORMAT_NAME = Strings.constant("get format");
    static final HiddenKey BOUND_OBJECT_KEY = new HiddenKey(Strings.toJavaString(CLASS_NAME));
    public static final JSDateTimeFormat INSTANCE = new JSDateTimeFormat();
    private static final LazyValue<UnmodifiableEconomicMap<String, String>> canonicalTimeZoneIDMap = new LazyValue<UnmodifiableEconomicMap>(JSDateTimeFormat::initCanonicalTimeZoneIDMap);
    private static final LazyValue<UnmodifiableEconomicMap<DateFormat.Field, String>> fieldToTypeMap = new LazyValue<UnmodifiableEconomicMap>(JSDateTimeFormat::initializeFieldToTypeMap);

    private JSDateTimeFormat() {
    }

    public static boolean isJSDateTimeFormat(Object obj) {
        return obj instanceof JSDateTimeFormatObject;
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
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
        JSContext ctx = realm.getContext();
        JSObject numberFormatPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, numberFormatPrototype, ctor);
        JSObjectUtil.putFunctionsFromContainer(realm, numberFormatPrototype, DateTimeFormatPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putBuiltinAccessorProperty((JSDynamicObject)numberFormatPrototype, (Object)Strings.FORMAT, JSDateTimeFormat.createFormatFunctionGetter(realm, ctx), Undefined.instance);
        JSObjectUtil.putToStringTag(numberFormatPrototype, TO_STRING_TAG);
        return numberFormatPrototype;
    }

    @Override
    public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
        Shape initialShape = JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
        return initialShape;
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm, DateTimeFormatFunctionBuiltins.BUILTINS);
    }

    public static JSDateTimeFormatObject create(JSContext context, JSRealm realm) {
        InternalState state = new InternalState();
        JSObjectFactory factory = context.getDateTimeFormatFactory();
        JSDateTimeFormatObject obj = new JSDateTimeFormatObject(factory.getShape(realm), state);
        factory.initProto(obj, realm);
        return context.trackAllocation(obj);
    }

    @CompilerDirectives.TruffleBoundary
    public static void setupInternalDateTimeFormat(JSContext ctx, InternalState state, String[] locales, String weekdayOpt, String eraOpt, String yearOpt, String monthOpt, String dayOpt, String dayPeriodOpt, String hourOpt, String hcOpt, Boolean hour12Opt, String minuteOpt, String secondOpt, int fractionalSecondDigitsOpt, String tzNameOpt, TimeZone timeZone, String calendarOpt, String numberingSystemOpt, String dateStyleOpt, String timeStyleOpt) {
        DateFormat dateFormat;
        String nuType;
        String hc;
        Locale selectedLocale = IntlUtil.selectedLocale(ctx, locales);
        Locale strippedLocale = selectedLocale.stripExtensions();
        Locale.Builder builder = new Locale.Builder();
        builder.setLocale(strippedLocale);
        if (hour12Opt == null) {
            String hcType = selectedLocale.getUnicodeLocaleType("hc");
            if (hcType != null && (hcOpt == null || hcOpt.equals(hcType)) && JSDateTimeFormat.isValidHCType(hcType)) {
                hc = hcType;
                builder.setUnicodeLocaleKeyword("hc", hcType);
            } else {
                hc = hcOpt;
            }
        } else {
            hc = null;
        }
        String caType = IntlUtil.normalizeCAType(selectedLocale.getUnicodeLocaleType("ca"));
        String normCalendarOpt = IntlUtil.normalizeCAType(calendarOpt);
        if (caType != null && (normCalendarOpt == null || normCalendarOpt.equals(caType)) && JSDateTimeFormat.isValidCAType(strippedLocale, caType)) {
            state.calendar = caType;
            builder.setUnicodeLocaleKeyword("ca", caType);
        }
        if ((nuType = selectedLocale.getUnicodeLocaleType("nu")) != null && IntlUtil.isValidNumberingSystem(nuType) && (numberingSystemOpt == null || numberingSystemOpt.equals(nuType))) {
            state.numberingSystem = nuType;
            builder.setUnicodeLocaleKeyword("nu", nuType);
        }
        state.locale = builder.build().toLanguageTag();
        if (normCalendarOpt != null && JSDateTimeFormat.isValidCAType(strippedLocale, normCalendarOpt)) {
            state.calendar = normCalendarOpt;
            builder.setUnicodeLocaleKeyword("ca", normCalendarOpt);
        }
        if (numberingSystemOpt != null && IntlUtil.isValidNumberingSystem(numberingSystemOpt)) {
            state.numberingSystem = numberingSystemOpt;
            builder.setUnicodeLocaleKeyword("nu", numberingSystemOpt);
        }
        Locale javaLocale = builder.build();
        if (state.numberingSystem == null) {
            state.numberingSystem = IntlUtil.defaultNumberingSystemName(ctx, javaLocale);
        }
        state.dateStyle = dateStyleOpt;
        state.timeStyle = timeStyleOpt;
        DateTimePatternGenerator patternGenerator = DateTimePatternGenerator.getInstance(javaLocale);
        String hcDefault = IntlUtil.toJSHourCycle(patternGenerator.getDefaultHourCycle());
        if (hc == null) {
            hc = hcDefault;
        }
        if (hour12Opt != null) {
            boolean h11or23;
            boolean bl = h11or23 = "h11".equals(hcDefault) || "h23".equals(hcDefault);
            if (hour12Opt.booleanValue()) {
                hc = h11or23 ? "h11" : "h12";
            } else {
                String string = hc = h11or23 ? "h23" : "h24";
            }
        }
        if (timeStyleOpt == null) {
            if (dateStyleOpt == null) {
                String skeleton = JSDateTimeFormat.makeSkeleton(weekdayOpt, eraOpt, yearOpt, monthOpt, dayOpt, dayPeriodOpt, hourOpt, hc, minuteOpt, secondOpt, fractionalSecondDigitsOpt, tzNameOpt);
                String bestPattern = patternGenerator.getBestPattern(skeleton, 2048);
                if (JSDateTimeFormat.containsOneOf(bestPattern, "eEc")) {
                    state.weekday = weekdayOpt;
                }
                if (bestPattern.contains("G")) {
                    state.era = eraOpt;
                }
                if (JSDateTimeFormat.containsOneOf(bestPattern, "YyUu")) {
                    state.year = yearOpt;
                }
                if (JSDateTimeFormat.containsOneOf(bestPattern, "ML")) {
                    state.month = monthOpt;
                }
                if (JSDateTimeFormat.containsOneOf(bestPattern, "dDFg")) {
                    state.day = dayOpt;
                }
                if (JSDateTimeFormat.containsOneOf(bestPattern, "Bb")) {
                    state.dayPeriod = dayPeriodOpt;
                }
                if (JSDateTimeFormat.containsOneOf(bestPattern, "hHKk")) {
                    state.hour = bestPattern.contains("hh") || bestPattern.contains("HH") || bestPattern.contains("KK") || bestPattern.contains("kk") ? "2-digit" : "numeric";
                    state.hourCycle = hc;
                }
                if (bestPattern.contains("m")) {
                    String string = state.minute = bestPattern.contains("mm") ? "2-digit" : "numeric";
                }
                if (bestPattern.contains("s")) {
                    String string = state.second = bestPattern.contains("ss") ? "2-digit" : "numeric";
                }
                if (JSDateTimeFormat.containsOneOf(bestPattern, "SA")) {
                    state.fractionalSecondDigits = fractionalSecondDigitsOpt;
                }
                dateFormat = new SimpleDateFormat(bestPattern, javaLocale);
            } else {
                dateFormat = DateFormat.getDateInstance(JSDateTimeFormat.dateFormatStyle(dateStyleOpt), javaLocale);
            }
        } else {
            dateFormat = dateStyleOpt == null ? DateFormat.getTimeInstance(JSDateTimeFormat.dateFormatStyle(timeStyleOpt), javaLocale) : DateFormat.getDateTimeInstance(JSDateTimeFormat.dateFormatStyle(dateStyleOpt), JSDateTimeFormat.dateFormatStyle(timeStyleOpt), javaLocale);
            state.hourCycle = hc;
        }
        String pattern = ((SimpleDateFormat)dateFormat).toPattern();
        String skeleton = patternGenerator.getSkeleton(pattern);
        if (!Objects.equals(state.hourCycle, JSDateTimeFormat.hourCycleFromPattern(pattern))) {
            skeleton = JSDateTimeFormat.replaceHourCycle(skeleton, hc);
            String bestPattern = patternGenerator.getBestPattern(skeleton, 2048);
            dateFormat = new SimpleDateFormat(JSDateTimeFormat.replaceHourCycle(bestPattern, hc), javaLocale);
        }
        state.dateFormat = dateFormat;
        Locale intervalFormatLocale = new Locale.Builder().setLocale(javaLocale).setUnicodeLocaleKeyword("hc", hc).build();
        try {
            state.dateIntervalFormat = DateIntervalFormat.getInstance(skeleton, intervalFormatLocale);
        }
        catch (IllegalArgumentException iaex) {
            state.dateIntervalFormat = DateIntervalFormat.getInstance(JSDateTimeFormat.patchSkeletonToAvoidICU21939(skeleton), intervalFormatLocale);
        }
        if (state.calendar == null) {
            state.calendar = IntlUtil.normalizeCAType(Calendar.getInstance(javaLocale).getType());
        }
        if ("gregory".equals(state.calendar)) {
            Calendar calendar = dateFormat.getCalendar();
            if (!(calendar instanceof GregorianCalendar)) {
                calendar = new GregorianCalendar(javaLocale);
                dateFormat.setCalendar(calendar);
            }
            ((GregorianCalendar)calendar).setGregorianChange(new Date(Long.MIN_VALUE));
        }
        if (tzNameOpt != null && !tzNameOpt.isEmpty()) {
            state.timeZoneName = tzNameOpt;
        }
        state.dateFormat.setTimeZone(timeZone);
        state.timeZone = timeZone.getID();
        state.initialized = true;
    }

    private static String patchSkeletonToAvoidICU21939(String skeleton) {
        StringBuilder sb = new StringBuilder();
        block4: for (char c : skeleton.toCharArray()) {
            switch (c) {
                case 'B': 
                case 'a': 
                case 'b': {
                    continue block4;
                }
                case 'U': 
                case 'Y': 
                case 'r': 
                case 'u': {
                    sb.append('y');
                    continue block4;
                }
                default: {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    private static int dateFormatStyle(String style) {
        if ("full".equals(style)) {
            return 0;
        }
        if ("long".equals(style)) {
            return 1;
        }
        if ("medium".equals(style)) {
            return 2;
        }
        assert ("short".equals(style));
        return 3;
    }

    private static String hourCycleFromPattern(String pattern) {
        boolean quoted = false;
        for (char c : pattern.toCharArray()) {
            if (c == '\'') {
                quoted = !quoted;
                continue;
            }
            if (quoted) continue;
            switch (c) {
                case 'K': {
                    return "h11";
                }
                case 'h': {
                    return "h12";
                }
                case 'H': {
                    return "h23";
                }
                case 'k': {
                    return "h24";
                }
            }
        }
        return null;
    }

    /*
     * Unable to fully structure code
     */
    private static String replaceHourCycle(String pattern, String hourCycle) {
        sb = new StringBuilder();
        if ("h11".equals(hourCycle)) {
            replacement = 'K';
        } else if ("h12".equals(hourCycle)) {
            replacement = 'h';
        } else if ("h23".equals(hourCycle)) {
            replacement = 'H';
        } else {
            if (!JSDateTimeFormat.$assertionsDisabled && !"h24".equals(hourCycle)) {
                throw new AssertionError();
            }
            replacement = 'k';
        }
        quoted = false;
        block3: for (char c : pattern.toCharArray()) {
            block11: {
                if (c != '\'') break block11;
                quoted = quoted == false;
                ** GOTO lbl-1000
            }
            if (quoted) ** GOTO lbl-1000
            switch (c) {
                case 'H': 
                case 'K': 
                case 'h': 
                case 'k': {
                    sb.append(replacement);
                    continue block3;
                }
                default: lbl-1000:
                // 3 sources

                {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    private static boolean isValidHCType(String hcType) {
        return "h11".equals(hcType) || "h12".equals(hcType) || "h23".equals(hcType) || "h24".equals(hcType);
    }

    private static boolean isValidCAType(Locale locale, String calendar) {
        String[] validValues;
        assert (Objects.equals(calendar, IntlUtil.normalizeCAType(calendar)));
        for (String validValue : validValues = Calendar.getKeywordValuesForLocale("ca", ULocale.forLocale(locale), false)) {
            if (!IntlUtil.normalizeCAType(validValue).equals(calendar)) continue;
            return true;
        }
        return false;
    }

    private static String weekdayOptToSkeleton(String weekdayOpt) {
        if (weekdayOpt == null) {
            return "";
        }
        switch (weekdayOpt) {
            case "narrow": {
                return "eeeee";
            }
            case "short": {
                return "eee";
            }
            case "long": {
                return "eeee";
            }
        }
        return "";
    }

    private static String eraOptToSkeleton(String eraOpt) {
        if (eraOpt == null) {
            return "";
        }
        switch (eraOpt) {
            case "narrow": {
                return "GGGGG";
            }
            case "short": {
                return "GGG";
            }
            case "long": {
                return "GGGG";
            }
        }
        return "";
    }

    private static String yearOptToSkeleton(String yearOpt) {
        if (yearOpt == null) {
            return "";
        }
        switch (yearOpt) {
            case "2-digit": {
                return "yy";
            }
            case "numeric": {
                return "y";
            }
        }
        return "";
    }

    private static String monthOptToSkeleton(String monthOpt) {
        if (monthOpt == null) {
            return "";
        }
        switch (monthOpt) {
            case "2-digit": {
                return "MM";
            }
            case "numeric": {
                return "M";
            }
            case "narrow": {
                return "MMMMM";
            }
            case "short": {
                return "MMM";
            }
            case "long": {
                return "MMMM";
            }
        }
        return "";
    }

    private static String dayOptToSkeleton(String dayOpt) {
        if (dayOpt == null) {
            return "";
        }
        switch (dayOpt) {
            case "2-digit": {
                return "dd";
            }
            case "numeric": {
                return "d";
            }
        }
        return "";
    }

    private static String dayPeriodOptToSkeleton(String dayPeriodOpt) {
        if (dayPeriodOpt == null) {
            return "";
        }
        switch (dayPeriodOpt) {
            case "narrow": {
                return "BBBBB";
            }
            case "short": {
                return "B";
            }
            case "long": {
                return "BBBB";
            }
        }
        return "";
    }

    private static String hourOptToSkeleton(String hourOpt, String hcOpt) {
        if (hourOpt == null) {
            return "";
        }
        switch (hourOpt) {
            case "2-digit": {
                switch (hcOpt) {
                    case "h11": {
                        return "KK";
                    }
                    case "h12": {
                        return "hh";
                    }
                    case "h23": {
                        return "HH";
                    }
                    case "h24": {
                        return "kk";
                    }
                }
                break;
            }
            case "numeric": {
                switch (hcOpt) {
                    case "h11": {
                        return "K";
                    }
                    case "h12": {
                        return "h";
                    }
                    case "h23": {
                        return "H";
                    }
                    case "h24": {
                        return "k";
                    }
                }
            }
        }
        return "";
    }

    private static String minuteOptToSkeleton(String minuteOpt) {
        if (minuteOpt == null) {
            return "";
        }
        switch (minuteOpt) {
            case "2-digit": {
                return "mm";
            }
            case "numeric": {
                return "m";
            }
        }
        return "";
    }

    private static String secondOptToSkeleton(String secondOpt, int fractionalSecondDigitsOpt) {
        StringBuilder skeleton = new StringBuilder();
        if (secondOpt != null) {
            if ("numeric".equals(secondOpt)) {
                skeleton.append("s");
            } else {
                assert ("2-digit".equals(secondOpt));
                skeleton.append("ss");
            }
        }
        for (int i = 0; i < fractionalSecondDigitsOpt; ++i) {
            skeleton.append('S');
        }
        return skeleton.toString();
    }

    private static String timeZoneNameOptToSkeleton(String timeZoneNameOpt) {
        if (timeZoneNameOpt == null) {
            return "";
        }
        switch (timeZoneNameOpt) {
            case "short": {
                return "z";
            }
            case "long": {
                return "zzzz";
            }
            case "shortOffset": {
                return "O";
            }
            case "longOffset": {
                return "OOOO";
            }
            case "shortGeneric": {
                return "v";
            }
            case "longGeneric": {
                return "vvvv";
            }
        }
        return "";
    }

    private static String makeSkeleton(String weekdayOpt, String eraOpt, String yearOpt, String monthOpt, String dayOpt, String dayPeriodOpt, String hourOpt, String hcOpt, String minuteOpt, String secondOpt, int fractionalSecondDigitsOpt, String timeZoneNameOpt) {
        return JSDateTimeFormat.weekdayOptToSkeleton(weekdayOpt) + JSDateTimeFormat.eraOptToSkeleton(eraOpt) + JSDateTimeFormat.yearOptToSkeleton(yearOpt) + JSDateTimeFormat.monthOptToSkeleton(monthOpt) + JSDateTimeFormat.dayOptToSkeleton(dayOpt) + JSDateTimeFormat.dayPeriodOptToSkeleton(dayPeriodOpt) + JSDateTimeFormat.hourOptToSkeleton(hourOpt, hcOpt) + JSDateTimeFormat.minuteOptToSkeleton(minuteOpt) + JSDateTimeFormat.secondOptToSkeleton(secondOpt, fractionalSecondDigitsOpt) + JSDateTimeFormat.timeZoneNameOptToSkeleton(timeZoneNameOpt);
    }

    private static UnmodifiableEconomicMap<String, String> initCanonicalTimeZoneIDMap() {
        CompilerAsserts.neverPartOfCompilation();
        EconomicMap<String, String> map = EconomicMap.create();
        for (String available : TimeZone.getAvailableIDs()) {
            map.put(IntlUtil.toUpperCase(available), TimeZone.getCanonicalID(available));
        }
        return map;
    }

    @CompilerDirectives.TruffleBoundary
    public static String canonicalizeTimeZoneName(TruffleString tzId) {
        String ucTzId = IntlUtil.toUpperCase(Strings.toJavaString(tzId));
        String canTzId = canonicalTimeZoneIDMap.get().get(ucTzId);
        if (canTzId == null) {
            return null;
        }
        if (canTzId.equals("Etc/UTC") || canTzId.equals("Etc/GMT")) {
            return "UTC";
        }
        return canTzId;
    }

    private static boolean containsOneOf(String suspect, String containees) {
        for (byte c : containees.getBytes()) {
            if (suspect.indexOf(c) <= -1) continue;
            return true;
        }
        return false;
    }

    public static DateFormat getDateFormatProperty(JSDynamicObject obj) {
        return JSDateTimeFormat.getInternalState((JSDynamicObject)obj).dateFormat;
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString format(JSDynamicObject numberFormatObj, Object n) {
        DateFormat dateFormat = JSDateTimeFormat.getDateFormatProperty(numberFormatObj);
        return Strings.fromJavaString(dateFormat.format(JSDateTimeFormat.timeClip(n)));
    }

    private static double timeClip(Object n) {
        double x;
        if (n == Undefined.instance) {
            x = JSRealm.get(null).currentTimeMillis();
        } else {
            x = JSDate.timeClip(JSRuntime.toDouble(n));
            if (Double.isNaN(x)) {
                JSDateTimeFormat.throwDateOutOfRange();
            }
        }
        return x;
    }

    private static void throwDateOutOfRange() throws JSException {
        throw Errors.createRangeError("Provided date is not in valid range.");
    }

    private static UnmodifiableEconomicMap<DateFormat.Field, String> initializeFieldToTypeMap() {
        CompilerAsserts.neverPartOfCompilation();
        EconomicMap<DateFormat.Field, String> map = EconomicMap.create(14);
        map.put(DateFormat.Field.AM_PM, "dayPeriod");
        map.put(DateFormat.Field.AM_PM_MIDNIGHT_NOON, "dayPeriod");
        map.put(DateFormat.Field.FLEXIBLE_DAY_PERIOD, "dayPeriod");
        map.put(DateFormat.Field.ERA, "era");
        map.put(DateFormat.Field.YEAR, "year");
        map.put(DateFormat.Field.RELATED_YEAR, "relatedYear");
        map.put(DateFormat.Field.MONTH, "month");
        map.put(DateFormat.Field.DOW_LOCAL, "weekday");
        map.put(DateFormat.Field.DAY_OF_WEEK, "weekday");
        map.put(DateFormat.Field.DAY_OF_MONTH, "day");
        map.put(DateFormat.Field.HOUR0, "hour");
        map.put(DateFormat.Field.HOUR1, "hour");
        map.put(DateFormat.Field.HOUR_OF_DAY0, "hour");
        map.put(DateFormat.Field.HOUR_OF_DAY1, "hour");
        map.put(DateFormat.Field.MINUTE, "minute");
        map.put(DateFormat.Field.SECOND, "second");
        map.put(DateFormat.Field.MILLISECOND, "fractionalSecond");
        map.put(DateFormat.Field.MILLISECONDS_IN_DAY, "fractionalSecond");
        map.put(DateFormat.Field.TIME_ZONE, "timeZoneName");
        return map;
    }

    private static String fieldToType(DateFormat.Field field) {
        return fieldToTypeMap.get().get(field);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSDynamicObject formatToParts(JSContext context, JSRealm realm, JSDynamicObject numberFormatObj, Object n, String source) {
        DateFormat dateFormat = JSDateTimeFormat.getDateFormatProperty(numberFormatObj);
        String yearPattern = JSDateTimeFormat.yearRelatedSubpattern(dateFormat);
        int yearPatternIndex = 0;
        double x = JSDateTimeFormat.timeClip(n);
        ArrayList<Object> resultParts = new ArrayList<Object>();
        AttributedCharacterIterator fit = dateFormat.formatToCharacterIterator(x);
        String formatted = dateFormat.format(x);
        int i = fit.getBeginIndex();
        while (i < fit.getEndIndex()) {
            fit.setIndex(i);
            Map<AttributedCharacterIterator.Attribute, Object> attributes = fit.getAttributes();
            Set<AttributedCharacterIterator.Attribute> attKeySet = attributes.keySet();
            if (!attKeySet.isEmpty()) {
                Iterator<AttributedCharacterIterator.Attribute> iterator = attKeySet.iterator();
                if (!iterator.hasNext()) continue;
                AttributedCharacterIterator.Attribute a = iterator.next();
                if (a instanceof DateFormat.Field) {
                    String type;
                    String value2 = formatted.substring(fit.getRunStart(), fit.getRunLimit());
                    if (a == DateFormat.Field.YEAR) {
                        type = yearPatternIndex < yearPattern.length() && yearPattern.charAt(yearPatternIndex) == 'U' ? "yearName" : "year";
                        ++yearPatternIndex;
                    } else {
                        type = JSDateTimeFormat.fieldToType((DateFormat.Field)a);
                        assert (type != null) : a;
                    }
                    resultParts.add(JSDateTimeFormat.makePart(context, realm, type, value2, source));
                    i = fit.getRunLimit();
                    continue;
                }
                throw Errors.shouldNotReachHere();
            }
            String value3 = formatted.substring(fit.getRunStart(), fit.getRunLimit());
            resultParts.add(JSDateTimeFormat.makePart(context, realm, "literal", value3, source));
            i = fit.getRunLimit();
        }
        return JSArray.createConstant(context, realm, resultParts.toArray());
    }

    private static DateIntervalFormat.FormattedDateInterval formatRangeImpl(JSDynamicObject dateTimeFormat, double startDate, double endDate) {
        InternalState state = JSDateTimeFormat.getInternalState(dateTimeFormat);
        DateFormat dateFormat = state.dateFormat;
        Calendar calendar = dateFormat.getCalendar();
        Calendar fromCalendar = (Calendar)calendar.clone();
        Calendar toCalendar = (Calendar)calendar.clone();
        fromCalendar.setTimeInMillis((long)startDate);
        toCalendar.setTimeInMillis((long)endDate);
        return state.dateIntervalFormat.formatToValue(fromCalendar, toCalendar);
    }

    @CompilerDirectives.TruffleBoundary
    public static TruffleString formatRange(JSDynamicObject dateTimeFormat, double startDate, double endDate) {
        DateIntervalFormat.FormattedDateInterval formattedRange = JSDateTimeFormat.formatRangeImpl(dateTimeFormat, startDate, endDate);
        if (JSDateTimeFormat.dateFieldsPracticallyEqual(formattedRange)) {
            return JSDateTimeFormat.format(dateTimeFormat, startDate);
        }
        return Strings.fromJavaString(formattedRange.toString());
    }

    private static boolean dateFieldsPracticallyEqual(DateIntervalFormat.FormattedDateInterval formattedRange) {
        ConstrainedFieldPosition cfPos = new ConstrainedFieldPosition();
        while (formattedRange.nextPosition(cfPos)) {
            if (!(cfPos.getField() instanceof DateIntervalFormat.SpanField)) continue;
            return false;
        }
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    public static JSDynamicObject formatRangeToParts(JSContext context, JSRealm realm, JSDynamicObject dateTimeFormat, double startDate, double endDate) {
        DateIntervalFormat.FormattedDateInterval formattedRange = JSDateTimeFormat.formatRangeImpl(dateTimeFormat, startDate, endDate);
        if (JSDateTimeFormat.dateFieldsPracticallyEqual(formattedRange)) {
            return JSDateTimeFormat.formatToParts(context, realm, dateTimeFormat, startDate, "shared");
        }
        String formattedString = formattedRange.toString();
        String digits = null;
        ArrayList<Object> parts = new ArrayList<Object>();
        int startRangeStart = 0;
        int startRangeLimit = 0;
        int endRangeStart = 0;
        int endRangeLimit = 0;
        int lastLimit = 0;
        ConstrainedFieldPosition cfPos = new ConstrainedFieldPosition();
        while (formattedRange.nextPosition(cfPos)) {
            Format.Field field;
            int start2 = cfPos.getStart();
            int limit = cfPos.getLimit();
            if (lastLimit < start2) {
                String literal = formattedString.substring(lastLimit, start2);
                String source = IntlUtil.sourceString(lastLimit, start2, startRangeStart, startRangeLimit, endRangeStart, endRangeLimit);
                parts.add(JSDateTimeFormat.makePart(context, realm, "literal", literal, source));
                lastLimit = start2;
            }
            if ((field = cfPos.getField()) instanceof DateIntervalFormat.SpanField) {
                Object fieldValue = cfPos.getFieldValue();
                if (fieldValue.equals(0)) {
                    startRangeStart = start2;
                    startRangeLimit = limit;
                    continue;
                }
                if (fieldValue.equals(1)) {
                    endRangeStart = start2;
                    endRangeLimit = limit;
                    continue;
                }
                throw Errors.shouldNotReachHere(fieldValue.toString());
            }
            if (field instanceof DateFormat.Field) {
                String type;
                String value2 = formattedString.substring(start2, limit);
                if (field == DateFormat.Field.YEAR) {
                    if (digits == null) {
                        String numberingSystem = JSDateTimeFormat.getInternalState((JSDynamicObject)dateTimeFormat).numberingSystem;
                        digits = NumberingSystem.getInstanceByName(numberingSystem).getDescription();
                    }
                    boolean year = value2.length() > 0 && digits.indexOf(value2.charAt(0)) != -1;
                    type = year ? "year" : "yearName";
                } else {
                    type = JSDateTimeFormat.fieldToType((DateFormat.Field)field);
                }
                String source = IntlUtil.sourceString(start2, limit, startRangeStart, startRangeLimit, endRangeStart, endRangeLimit);
                parts.add(JSDateTimeFormat.makePart(context, realm, type, value2, source));
                lastLimit = limit;
                continue;
            }
            throw Errors.shouldNotReachHere(field.toString());
        }
        int length = formattedString.length();
        if (lastLimit < length) {
            String literal = formattedString.substring(lastLimit, length);
            String source = IntlUtil.sourceString(lastLimit, length, startRangeStart, startRangeLimit, endRangeStart, endRangeLimit);
            parts.add(JSDateTimeFormat.makePart(context, realm, "literal", literal, source));
        }
        return JSArray.createConstant(context, realm, parts.toArray());
    }

    private static String yearRelatedSubpattern(DateFormat dateFormat) {
        if (dateFormat instanceof SimpleDateFormat) {
            String pattern = ((SimpleDateFormat)dateFormat).toPattern();
            StringBuilder sb = new StringBuilder();
            boolean quoted = false;
            for (char c : pattern.toCharArray()) {
                if (c == '\'') {
                    quoted = !quoted;
                    continue;
                }
                if (quoted || c != 'y' && c != 'Y' && c != 'u' && c != 'U') continue;
                sb.append(c);
            }
            return sb.toString();
        }
        return "";
    }

    private static Object makePart(JSContext context, JSRealm realm, String type, String value2, String source) {
        JSObject p = JSOrdinary.create(context, realm);
        JSObject.set((JSDynamicObject)p, IntlUtil.KEY_TYPE, (Object)Strings.fromJavaString(type));
        JSObject.set((JSDynamicObject)p, IntlUtil.KEY_VALUE, (Object)Strings.fromJavaString(value2));
        if (source != null) {
            JSObject.set((JSDynamicObject)p, IntlUtil.KEY_SOURCE, (Object)Strings.fromJavaString(source));
        }
        return p;
    }

    @CompilerDirectives.TruffleBoundary
    public static JSDynamicObject resolvedOptions(JSContext context, JSRealm realm, JSDynamicObject numberFormatObj) {
        InternalState state = JSDateTimeFormat.getInternalState(numberFormatObj);
        return state.toResolvedOptionsObject(context, realm);
    }

    public static InternalState getInternalState(JSDynamicObject obj) {
        assert (JSDateTimeFormat.isJSDateTimeFormat(obj));
        return ((JSDateTimeFormatObject)obj).getInternalState();
    }

    private static CallTarget createGetFormatCallTarget(final JSContext context) {
        return new JavaScriptRootNode(context.getLanguage(), null, null){
            private final BranchProfile errorBranch;
            @Node.Child
            private PropertySetNode setBoundObjectNode;
            {
                super(lang, sourceSection, frameDescriptor);
                this.errorBranch = BranchProfile.create();
                this.setBoundObjectNode = PropertySetNode.createSetHidden(BOUND_OBJECT_KEY, context);
            }

            @Override
            public Object execute(VirtualFrame frame) {
                Object[] frameArgs = frame.getArguments();
                Object dateTimeFormatObj = JSArguments.getThisObject(frameArgs);
                if (JSDateTimeFormat.isJSDateTimeFormat(dateTimeFormatObj)) {
                    InternalState state = JSDateTimeFormat.getInternalState((JSDynamicObject)dateTimeFormatObj);
                    if (state == null || !state.initialized) {
                        this.errorBranch.enter();
                        throw Errors.createTypeErrorMethodCalledOnNonObjectOrWrongType("format");
                    }
                    if (state.boundFormatFunction == null) {
                        JSFunctionData formatFunctionData = context.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.DateTimeFormatFormat, c -> JSDateTimeFormat.createFormatFunctionData(c));
                        JSFunctionObject formatFn = JSFunction.create(this.getRealm(), formatFunctionData);
                        this.setBoundObjectNode.setValue(formatFn, dateTimeFormatObj);
                        state.boundFormatFunction = formatFn;
                    }
                    return state.boundFormatFunction;
                }
                this.errorBranch.enter();
                throw Errors.createTypeErrorTypeXExpected(CLASS_NAME);
            }
        }.getCallTarget();
    }

    private static JSFunctionData createFormatFunctionData(final JSContext context) {
        return JSFunctionData.createCallOnly(context, new JavaScriptRootNode(context.getLanguage(), null, null){
            @Node.Child
            private PropertyGetNode getBoundObjectNode;
            {
                super(lang, sourceSection, frameDescriptor);
                this.getBoundObjectNode = PropertyGetNode.createGetHidden(BOUND_OBJECT_KEY, context);
            }

            @Override
            public Object execute(VirtualFrame frame) {
                Object[] arguments = frame.getArguments();
                JSDynamicObject thisObj = (JSDynamicObject)this.getBoundObjectNode.getValue(JSArguments.getFunctionObject(arguments));
                assert (JSDateTimeFormat.isJSDateTimeFormat(thisObj));
                JSDynamicObject n = JSArguments.getUserArgumentCount(arguments) > 0 ? JSArguments.getUserArgument(arguments, 0) : Undefined.instance;
                return JSDateTimeFormat.format(thisObj, n);
            }
        }.getCallTarget(), 1, Strings.EMPTY_STRING);
    }

    private static JSDynamicObject createFormatFunctionGetter(JSRealm realm, JSContext context) {
        JSFunctionData fd = realm.getContext().getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.DateTimeFormatGetFormat, c -> {
            CallTarget ct = JSDateTimeFormat.createGetFormatCallTarget(context);
            return JSFunctionData.create(context, ct, ct, 0, GET_FORMAT_NAME, false, false, false, true);
        });
        return JSFunction.create(realm, fd);
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getDateTimeFormatPrototype();
    }

    public static class InternalState {
        private boolean initialized = false;
        private DateFormat dateFormat;
        private DateIntervalFormat dateIntervalFormat;
        private JSDynamicObject boundFormatFunction = null;
        private String locale;
        private String calendar;
        private String numberingSystem;
        private String weekday;
        private String era;
        private String year;
        private String month;
        private String day;
        private String dayPeriod;
        private String hour;
        private String minute;
        private String second;
        private int fractionalSecondDigits;
        private String hourCycle;
        private String timeZoneName;
        private String timeZone;
        private String dateStyle;
        private String timeStyle;

        JSObject toResolvedOptionsObject(JSContext context, JSRealm realm) {
            JSObject result = JSOrdinary.create(context, realm);
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_LOCALE, Strings.fromJavaString(this.locale), JSAttributes.getDefault());
            if (this.calendar != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_CALENDAR, Strings.fromJavaString(this.calendar), JSAttributes.getDefault());
            }
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_NUMBERING_SYSTEM, Strings.fromJavaString(this.numberingSystem), JSAttributes.getDefault());
            if (this.timeZone != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_TIME_ZONE, Strings.fromJavaString(this.timeZone), JSAttributes.getDefault());
            }
            if (this.hourCycle != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_HOUR_CYCLE, Strings.fromJavaString(this.hourCycle), JSAttributes.getDefault());
                boolean hour12 = "h11".equals(this.hourCycle) || "h12".equals(this.hourCycle);
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_HOUR12, hour12, JSAttributes.getDefault());
            }
            if (this.weekday != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_WEEKDAY, Strings.fromJavaString(this.weekday), JSAttributes.getDefault());
            }
            if (this.era != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_ERA, Strings.fromJavaString(this.era), JSAttributes.getDefault());
            }
            if (this.year != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_YEAR, Strings.fromJavaString(this.year), JSAttributes.getDefault());
            }
            if (this.month != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_MONTH, Strings.fromJavaString(this.month), JSAttributes.getDefault());
            }
            if (this.day != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_DAY, Strings.fromJavaString(this.day), JSAttributes.getDefault());
            }
            if (this.dayPeriod != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_DAY_PERIOD, Strings.fromJavaString(this.dayPeriod), JSAttributes.getDefault());
            }
            if (this.hour != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_HOUR, Strings.fromJavaString(this.hour), JSAttributes.getDefault());
            }
            if (this.minute != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_MINUTE, Strings.fromJavaString(this.minute), JSAttributes.getDefault());
            }
            if (this.second != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_SECOND, Strings.fromJavaString(this.second), JSAttributes.getDefault());
            }
            if (this.fractionalSecondDigits != 0) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_FRACTIONAL_SECOND_DIGITS, this.fractionalSecondDigits, JSAttributes.getDefault());
            }
            if (this.timeZoneName != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_TIME_ZONE_NAME, Strings.fromJavaString(this.timeZoneName), JSAttributes.getDefault());
            }
            if (this.dateStyle != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_DATE_STYLE, Strings.fromJavaString(this.dateStyle), JSAttributes.getDefault());
            }
            if (this.timeStyle != null) {
                JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_TIME_STYLE, Strings.fromJavaString(this.timeStyle), JSAttributes.getDefault());
            }
            return result;
        }
    }
}

