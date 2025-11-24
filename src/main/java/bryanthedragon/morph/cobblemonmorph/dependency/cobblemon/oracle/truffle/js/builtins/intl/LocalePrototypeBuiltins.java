
package com.oracle.truffle.js.builtins.intl;

import com.cobblemon.mod.relocations.ibm.icu.text.DateTimePatternGenerator;
import com.cobblemon.mod.relocations.ibm.icu.text.NumberingSystem;
import com.cobblemon.mod.relocations.ibm.icu.util.Calendar;
import com.cobblemon.mod.relocations.ibm.icu.util.TimeZone;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.intl.LocalePrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.access.CreateDataPropertyNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.intl.JSLocale;
import com.oracle.truffle.js.runtime.builtins.intl.JSLocaleObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import java.util.Set;

public final class LocalePrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<LocalePrototype> {
    public static final JSBuiltinsContainer BUILTINS = new LocalePrototypeBuiltins();

    protected LocalePrototypeBuiltins() {
        super(JSLocale.PROTOTYPE_NAME, LocalePrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, LocalePrototype builtinEnum) {
        switch (builtinEnum) {
            case maximize: {
                return LocalePrototypeBuiltinsFactory.JSLocaleMaximizeNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case minimize: {
                return LocalePrototypeBuiltinsFactory.JSLocaleMinimizeNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case toString: {
                return LocalePrototypeBuiltinsFactory.JSLocaleToStringNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case baseName: {
                return LocalePrototypeBuiltinsFactory.JSLocaleBaseNameAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case calendar: {
                return LocalePrototypeBuiltinsFactory.JSLocaleCalendarAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case caseFirst: {
                return LocalePrototypeBuiltinsFactory.JSLocaleCaseFirstAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case collation: {
                return LocalePrototypeBuiltinsFactory.JSLocaleCollationAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case hourCycle: {
                return LocalePrototypeBuiltinsFactory.JSLocaleHourCycleAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case numeric: {
                return LocalePrototypeBuiltinsFactory.JSLocaleNumericAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case numberingSystem: {
                return LocalePrototypeBuiltinsFactory.JSLocaleNumberingSystemAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case language: {
                return LocalePrototypeBuiltinsFactory.JSLocaleLanguageAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case script: {
                return LocalePrototypeBuiltinsFactory.JSLocaleScriptAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case region: {
                return LocalePrototypeBuiltinsFactory.JSLocaleRegionAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case calendars: {
                return LocalePrototypeBuiltinsFactory.JSLocaleCalendarsAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case collations: {
                return LocalePrototypeBuiltinsFactory.JSLocaleCollationsAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case hourCycles: {
                return LocalePrototypeBuiltinsFactory.JSLocaleHourCyclesAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case numberingSystems: {
                return LocalePrototypeBuiltinsFactory.JSLocaleNumberingSystemsAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case timeZones: {
                return LocalePrototypeBuiltinsFactory.JSLocaleTimeZonesAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case textInfo: {
                return LocalePrototypeBuiltinsFactory.JSLocaleTextInfoAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case weekInfo: {
                return LocalePrototypeBuiltinsFactory.JSLocaleWeekInfoAccessorNodeGen.create(context, builtin, LocalePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSLocaleWeekInfoAccessor
    extends JSBuiltinNode {
        @Node.Child
        CreateDataPropertyNode createFirstDayNode = CreateDataPropertyNode.create(this.getContext(), IntlUtil.KEY_FIRST_DAY);
        @Node.Child
        CreateDataPropertyNode createWeekendNode = CreateDataPropertyNode.create(this.getContext(), IntlUtil.KEY_WEEKEND);
        @Node.Child
        CreateDataPropertyNode createMinimalDaysNode = CreateDataPropertyNode.create(this.getContext(), IntlUtil.KEY_MINIMAL_DAYS);

        public JSLocaleWeekInfoAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            Calendar.WeekData weekData = JSLocaleWeekInfoAccessor.weekData(localeObject);
            int firstDay = JSLocaleWeekInfoAccessor.calendarToECMAScriptDay(weekData.firstDayOfWeek);
            int minimalDays = weekData.minimalDaysInFirstWeek;
            SimpleArrayList<Integer> weekendList = new SimpleArrayList<Integer>(7);
            int weekendCease = weekData.weekendCease;
            int day = weekData.weekendOnset;
            while (true) {
                weekendList.add(JSLocaleWeekInfoAccessor.calendarToECMAScriptDay(day), null);
                if (day == weekendCease) break;
                if (day == 7) {
                    day = 1;
                    continue;
                }
                ++day;
            }
            JSContext context = this.getContext();
            JSRealm realm = this.getRealm();
            JSArrayObject weekend = JSArray.createConstantObjectArray(context, realm, weekendList.toArray());
            JSObject weekInfo = JSOrdinary.create(context, realm);
            this.createFirstDayNode.executeVoid(weekInfo, firstDay);
            this.createWeekendNode.executeVoid(weekInfo, weekend);
            this.createMinimalDaysNode.executeVoid(weekInfo, minimalDays);
            return weekInfo;
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }

        @CompilerDirectives.TruffleBoundary
        private static Calendar.WeekData weekData(JSLocaleObject localeObject) {
            ULocale locale = JSLocale.getInternalState(localeObject).getULocale();
            return Calendar.getInstance(locale).getWeekData();
        }

        private static int calendarToECMAScriptDay(int day) {
            return day == 1 ? 7 : day - 1;
        }
    }

    public static abstract class JSLocaleTextInfoAccessor
    extends JSBuiltinNode {
        @Node.Child
        CreateDataPropertyNode createDirectionNode = CreateDataPropertyNode.create(this.getContext(), IntlUtil.KEY_DIRECTION);

        public JSLocaleTextInfoAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            JSObject textInfo = JSOrdinary.create(this.getContext(), this.getRealm());
            this.createDirectionNode.executeVoid(textInfo, JSLocaleTextInfoAccessor.direction(localeObject));
            return textInfo;
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }

        @CompilerDirectives.TruffleBoundary
        private static TruffleString direction(JSLocaleObject localeObject) {
            ULocale locale = JSLocale.getInternalState(localeObject).getULocale();
            String orientation = locale.getCharacterOrientation();
            return "right-to-left".equals(orientation) ? IntlUtil.KEY_RTL : IntlUtil.KEY_LTR;
        }
    }

    public static abstract class JSLocaleTimeZonesAccessor
    extends JSBuiltinNode {
        public JSLocaleTimeZonesAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            ULocale locale = JSLocale.getInternalState(localeObject).getULocale();
            String region = locale.getCountry();
            if (region.isEmpty()) {
                return Undefined.instance;
            }
            Set<String> timeZoneSet = TimeZone.getAvailableIDs(TimeZone.SystemTimeZoneType.CANONICAL, region, null);
            Object[] timeZones = new Object[timeZoneSet.size()];
            int i = 0;
            for (String timeZone : timeZoneSet) {
                timeZones[i++] = Strings.fromJavaString(timeZone);
            }
            return JSArray.createConstantObjectArray(this.getContext(), this.getRealm(), timeZones);
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleNumberingSystemsAccessor
    extends JSBuiltinNode {
        public JSLocaleNumberingSystemsAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            ULocale locale = JSLocale.getInternalState(localeObject).getULocale();
            String numberingSystem = locale.getUnicodeLocaleType("nu");
            if (numberingSystem == null) {
                numberingSystem = NumberingSystem.getInstance(locale).getName();
            }
            return JSArray.createConstantObjectArray(this.getContext(), this.getRealm(), new Object[]{Strings.fromJavaString(numberingSystem)});
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleHourCyclesAccessor
    extends JSBuiltinNode {
        public JSLocaleHourCyclesAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            ULocale locale = JSLocale.getInternalState(localeObject).getULocale();
            String hourCycle = locale.getUnicodeLocaleType("hc");
            if (hourCycle == null) {
                DateTimePatternGenerator patternGenerator = DateTimePatternGenerator.getInstance(locale);
                hourCycle = IntlUtil.toJSHourCycle(patternGenerator.getDefaultHourCycle());
            }
            return JSArray.createConstantObjectArray(this.getContext(), this.getRealm(), new Object[]{Strings.fromJavaString(hourCycle)});
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleCollationsAccessor
    extends JSBuiltinNode {
        public JSLocaleCollationsAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            ULocale locale = JSLocale.getInternalState(localeObject).getULocale();
            String collation = locale.getUnicodeLocaleType("co");
            String[] collations = collation == null ? IntlUtil.availableCollations(locale, true) : new String[]{collation};
            return JSArray.createConstantObjectArray(this.getContext(), this.getRealm(), Strings.convertJavaStringArray(collations));
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleCalendarsAccessor
    extends JSBuiltinNode {
        public JSLocaleCalendarsAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            ULocale locale = JSLocale.getInternalState(localeObject).getULocale();
            String calendar = locale.getUnicodeLocaleType("ca");
            String[] calendars = calendar == null ? IntlUtil.availableCalendars(locale, true) : new String[]{calendar};
            return JSArray.createConstantObjectArray(this.getContext(), this.getRealm(), Strings.convertJavaStringArray(calendars));
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleRegionAccessor
    extends JSBuiltinNode {
        public JSLocaleRegionAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            String region = JSLocale.getInternalState(localeObject).getRegion();
            return region.isEmpty() ? Undefined.instance : Strings.fromJavaString(region);
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleScriptAccessor
    extends JSBuiltinNode {
        public JSLocaleScriptAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            String script = JSLocale.getInternalState(localeObject).getScript();
            return script.isEmpty() ? Undefined.instance : Strings.fromJavaString(script);
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleLanguageAccessor
    extends JSBuiltinNode {
        public JSLocaleLanguageAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            String language = JSLocale.getInternalState(localeObject).getLanguage();
            return language.isEmpty() ? Undefined.instance : Strings.fromJavaString(language);
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleNumberingSystemAccessor
    extends JSBuiltinNode {
        public JSLocaleNumberingSystemAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            return JSRuntime.nullToUndefined(Strings.fromJavaString(JSLocale.getInternalState(localeObject).getNumberingSystem()));
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleNumericAccessor
    extends JSBuiltinNode {
        public JSLocaleNumericAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public boolean doLocale(JSLocaleObject localeObject) {
            return JSLocale.getInternalState(localeObject).getNumeric();
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public boolean doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleHourCycleAccessor
    extends JSBuiltinNode {
        public JSLocaleHourCycleAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            return JSRuntime.nullToUndefined(Strings.fromJavaString(JSLocale.getInternalState(localeObject).getHourCycle()));
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleCollationAccessor
    extends JSBuiltinNode {
        public JSLocaleCollationAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            return JSRuntime.nullToUndefined(Strings.fromJavaString(JSLocale.getInternalState(localeObject).getCollation()));
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleCaseFirstAccessor
    extends JSBuiltinNode {
        public JSLocaleCaseFirstAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            return JSRuntime.nullToUndefined(Strings.fromJavaString(JSLocale.getInternalState(localeObject).getCaseFirst()));
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleCalendarAccessor
    extends JSBuiltinNode {
        public JSLocaleCalendarAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            return JSRuntime.nullToUndefined(Strings.fromJavaString(JSLocale.getInternalState(localeObject).getCalendar()));
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleBaseNameAccessor
    extends JSBuiltinNode {
        public JSLocaleBaseNameAccessor(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public TruffleString doLocale(JSLocaleObject localeObject) {
            return Strings.fromJavaString(JSLocale.getInternalState(localeObject).getBaseName());
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public String doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleToStringNode
    extends JSBuiltinNode {
        public JSLocaleToStringNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public TruffleString doLocale(JSLocaleObject localeObject) {
            return Strings.fromJavaString(JSLocale.getInternalState(localeObject).getLocale());
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public TruffleString doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleMinimizeNode
    extends JSBuiltinNode {
        public JSLocaleMinimizeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            String minimizedLocale = JSLocale.getInternalState(localeObject).minimize();
            return JSFunction.construct(this.getRealm().getLocaleConstructor(), new Object[]{Strings.fromJavaString(minimizedLocale)});
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static abstract class JSLocaleMaximizeNode
    extends JSBuiltinNode {
        public JSLocaleMaximizeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doLocale(JSLocaleObject localeObject) {
            String maximizedLocale = JSLocale.getInternalState(localeObject).maximize();
            return JSFunction.construct(this.getRealm().getLocaleConstructor(), new Object[]{Strings.fromJavaString(maximizedLocale)});
        }

        @Specialization(guards={"!isJSLocale(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorLocaleExpected();
        }
    }

    public static enum LocalePrototype implements BuiltinEnum<LocalePrototype>
    {
        maximize(0),
        minimize(0),
        toString(0),
        baseName(0),
        calendar(0),
        caseFirst(0),
        collation(0),
        hourCycle(0),
        numeric(0),
        numberingSystem(0),
        language(0),
        script(0),
        region(0),
        calendars(0),
        collations(0),
        hourCycles(0),
        numberingSystems(0),
        timeZones(0),
        textInfo(0),
        weekInfo(0);

        private final int length;

        private LocalePrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isGetter() {
            return baseName.ordinal() <= this.ordinal();
        }
    }
}

