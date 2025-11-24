
package com.oracle.truffle.js.nodes.intl;

import com.cobblemon.mod.relocations.ibm.icu.util.TimeZone;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.intl.GetBooleanOptionNode;
import com.oracle.truffle.js.nodes.intl.GetNumberOptionNode;
import com.oracle.truffle.js.nodes.intl.GetStringOptionNode;
import com.oracle.truffle.js.nodes.intl.InitializeDateTimeFormatNodeGen;
import com.oracle.truffle.js.nodes.intl.JSToCanonicalizedLocaleListNode;
import com.oracle.truffle.js.nodes.intl.ToDateTimeOptionsNode;
import com.oracle.truffle.js.nodes.intl.ToDateTimeOptionsNodeGen;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormat;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.MissingResourceException;

public abstract class InitializeDateTimeFormatNode
extends JavaScriptBaseNode {
    String required;
    String defaults;
    @Node.Child
    JSToCanonicalizedLocaleListNode toCanonicalizedLocaleListNode;
    @Node.Child
    ToDateTimeOptionsNode createOptionsNode;
    @Node.Child
    PropertyGetNode getTimeZoneNode;
    @Node.Child
    GetStringOptionNode getLocaleMatcherOption;
    @Node.Child
    GetStringOptionNode getFormatMatcherOption;
    @Node.Child
    GetStringOptionNode getHourCycleOption;
    @Node.Child
    GetStringOptionNode getCalendarOption;
    @Node.Child
    GetStringOptionNode getNumberingSystemOption;
    @Node.Child
    GetBooleanOptionNode getHour12Option;
    @Node.Child
    GetStringOptionNode getWeekdayOption;
    @Node.Child
    GetStringOptionNode getEraOption;
    @Node.Child
    GetStringOptionNode getYearOption;
    @Node.Child
    GetStringOptionNode getMonthOption;
    @Node.Child
    GetStringOptionNode getDayOption;
    @Node.Child
    GetStringOptionNode getDayPeriodOption;
    @Node.Child
    GetStringOptionNode getHourOption;
    @Node.Child
    GetStringOptionNode getMinuteOption;
    @Node.Child
    GetStringOptionNode getSecondOption;
    @Node.Child
    GetNumberOptionNode getFractionalSecondDigitsOption;
    @Node.Child
    GetStringOptionNode getTimeZoneNameOption;
    @Node.Child
    GetStringOptionNode getDateStyleOption;
    @Node.Child
    GetStringOptionNode getTimeStyleOption;
    @Node.Child
    private JSToStringNode toStringNode;
    private final BranchProfile errorBranch = BranchProfile.create();
    private final JSContext context;

    protected InitializeDateTimeFormatNode(JSContext context, String required, String defaults) {
        this.context = context;
        this.required = required;
        this.defaults = defaults;
        this.toCanonicalizedLocaleListNode = JSToCanonicalizedLocaleListNode.create(context);
        this.createOptionsNode = ToDateTimeOptionsNodeGen.create(context);
        this.getTimeZoneNode = PropertyGetNode.create(IntlUtil.KEY_TIME_ZONE, context);
        this.getLocaleMatcherOption = GetStringOptionNode.create(context, IntlUtil.KEY_LOCALE_MATCHER, new String[]{"lookup", "best fit"}, "best fit");
        this.getFormatMatcherOption = GetStringOptionNode.create(context, IntlUtil.KEY_FORMAT_MATCHER, new String[]{"basic", "best fit"}, "best fit");
        this.getHourCycleOption = GetStringOptionNode.create(context, IntlUtil.KEY_HOUR_CYCLE, new String[]{"h11", "h12", "h23", "h24"}, null);
        this.getCalendarOption = GetStringOptionNode.create(context, IntlUtil.KEY_CALENDAR, null, null);
        this.getNumberingSystemOption = GetStringOptionNode.create(context, IntlUtil.KEY_NUMBERING_SYSTEM, null, null);
        this.getHour12Option = GetBooleanOptionNode.create(context, IntlUtil.KEY_HOUR12, null);
        this.getWeekdayOption = GetStringOptionNode.create(context, IntlUtil.KEY_WEEKDAY, new String[]{"narrow", "short", "long"}, null);
        this.getEraOption = GetStringOptionNode.create(context, IntlUtil.KEY_ERA, new String[]{"narrow", "short", "long"}, null);
        this.getYearOption = GetStringOptionNode.create(context, IntlUtil.KEY_YEAR, new String[]{"2-digit", "numeric"}, null);
        this.getMonthOption = GetStringOptionNode.create(context, IntlUtil.KEY_MONTH, new String[]{"2-digit", "numeric", "narrow", "short", "long"}, null);
        this.getDayOption = GetStringOptionNode.create(context, IntlUtil.KEY_DAY, new String[]{"2-digit", "numeric"}, null);
        this.getDayPeriodOption = GetStringOptionNode.create(context, IntlUtil.KEY_DAY_PERIOD, new String[]{"narrow", "short", "long"}, null);
        this.getHourOption = GetStringOptionNode.create(context, IntlUtil.KEY_HOUR, new String[]{"2-digit", "numeric"}, null);
        this.getMinuteOption = GetStringOptionNode.create(context, IntlUtil.KEY_MINUTE, new String[]{"2-digit", "numeric"}, null);
        this.getSecondOption = GetStringOptionNode.create(context, IntlUtil.KEY_SECOND, new String[]{"2-digit", "numeric"}, null);
        this.getFractionalSecondDigitsOption = GetNumberOptionNode.create(context, IntlUtil.KEY_FRACTIONAL_SECOND_DIGITS);
        this.getTimeZoneNameOption = GetStringOptionNode.create(context, IntlUtil.KEY_TIME_ZONE_NAME, InitializeDateTimeFormatNode.timeZoneNameOptions(context), null);
        this.getDateStyleOption = GetStringOptionNode.create(context, IntlUtil.KEY_DATE_STYLE, new String[]{"full", "long", "medium", "short"}, null);
        this.getTimeStyleOption = GetStringOptionNode.create(context, IntlUtil.KEY_TIME_STYLE, new String[]{"full", "long", "medium", "short"}, null);
        this.toStringNode = JSToStringNode.create();
    }

    public abstract JSDynamicObject executeInit(JSDynamicObject var1, Object var2, Object var3);

    public static InitializeDateTimeFormatNode createInitalizeDateTimeFormatNode(JSContext context, String required, String defaults) {
        return InitializeDateTimeFormatNodeGen.create(context, required, defaults);
    }

    @Specialization
    public JSDynamicObject initializeDateTimeFormat(JSDynamicObject dateTimeFormatObj, Object localesArg, Object optionsArg) {
        try {
            String numberingSystemOpt;
            JSDateTimeFormat.InternalState state = JSDateTimeFormat.getInternalState(dateTimeFormatObj);
            String[] locales = this.toCanonicalizedLocaleListNode.executeLanguageTags(localesArg);
            JSDynamicObject options = this.createOptionsNode.execute(optionsArg, this.required, this.defaults);
            this.getLocaleMatcherOption.executeValue(options);
            String calendarOpt = this.getCalendarOption.executeValue(options);
            if (calendarOpt != null) {
                IntlUtil.validateUnicodeLocaleIdentifierType(calendarOpt, this.errorBranch);
                calendarOpt = IntlUtil.normalizeUnicodeLocaleIdentifierType(calendarOpt);
            }
            if ((numberingSystemOpt = this.getNumberingSystemOption.executeValue(options)) != null) {
                IntlUtil.validateUnicodeLocaleIdentifierType(numberingSystemOpt, this.errorBranch);
                numberingSystemOpt = IntlUtil.normalizeUnicodeLocaleIdentifierType(numberingSystemOpt);
            }
            Boolean hour12Opt = this.getHour12Option.executeValue(options);
            String hcOpt = this.getHourCycleOption.executeValue(options);
            Object timeZoneValue = this.getTimeZoneNode.getValue(options);
            TimeZone timeZone = this.toTimeZone(timeZoneValue);
            String weekdayOpt = this.getWeekdayOption.executeValue(options);
            String eraOpt = this.getEraOption.executeValue(options);
            String yearOpt = this.getYearOption.executeValue(options);
            String monthOpt = this.getMonthOption.executeValue(options);
            String dayOpt = this.getDayOption.executeValue(options);
            String dayPeriodOpt = this.getDayPeriodOption.executeValue(options);
            String hourOpt = this.getHourOption.executeValue(options);
            String minuteOpt = this.getMinuteOption.executeValue(options);
            String secondOpt = this.getSecondOption.executeValue(options);
            int fractionalSecondDigitsOpt = this.getFractionalSecondDigitsOption.executeInt(options, 1, 3, 0);
            String tzNameOpt = this.getTimeZoneNameOption.executeValue(options);
            this.getFormatMatcherOption.executeValue(options);
            String dateStyleOpt = this.getDateStyleOption.executeValue(options);
            String timeStyleOpt = this.getTimeStyleOption.executeValue(options);
            if (!(dateStyleOpt == null && timeStyleOpt == null || weekdayOpt == null && eraOpt == null && yearOpt == null && monthOpt == null && dayOpt == null && dayPeriodOpt == null && hourOpt == null && minuteOpt == null && secondOpt == null && fractionalSecondDigitsOpt == 0 && tzNameOpt == null)) {
                this.errorBranch.enter();
                throw Errors.createTypeError("dateStyle and timeStyle options cannot be mixed with other date/time options");
            }
            JSDateTimeFormat.setupInternalDateTimeFormat(this.context, state, locales, weekdayOpt, eraOpt, yearOpt, monthOpt, dayOpt, dayPeriodOpt, hourOpt, hcOpt, hour12Opt, minuteOpt, secondOpt, fractionalSecondDigitsOpt, tzNameOpt, timeZone, calendarOpt, numberingSystemOpt, dateStyleOpt, timeStyleOpt);
        }
        catch (MissingResourceException e) {
            this.errorBranch.enter();
            throw Errors.createICU4JDataError(e);
        }
        return dateTimeFormatObj;
    }

    private TimeZone toTimeZone(Object timeZoneValue) {
        if (timeZoneValue != Undefined.instance) {
            TruffleString name = this.toStringNode.executeString(timeZoneValue);
            String tzId = JSDateTimeFormat.canonicalizeTimeZoneName(name);
            if (tzId == null) {
                this.errorBranch.enter();
                throw Errors.createRangeErrorInvalidTimeZone(name);
            }
            return IntlUtil.getICUTimeZone(tzId, this.context);
        }
        return this.getRealm().getLocalTimeZone();
    }

    private static String[] timeZoneNameOptions(JSContext context) {
        if (context.getEcmaScriptVersion() >= 13) {
            return new String[]{"short", "long", "shortOffset", "longOffset", "shortGeneric", "longGeneric"};
        }
        return new String[]{"short", "long"};
    }
}

