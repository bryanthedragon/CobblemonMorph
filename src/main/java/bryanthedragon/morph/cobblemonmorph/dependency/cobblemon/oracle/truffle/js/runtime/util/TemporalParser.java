
package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalParserRecord;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TemporalParser {
    private static final String patternDate = "^([+\\-\\u2212]\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d)[\\-]?(\\d\\d)[\\-]?(\\d\\d)";
    private static final String patternTime = "^(\\d\\d)(:?(\\d\\d):?(?:(\\d\\d)(?:[\\.,]([\\d]*)?)?)?)?";
    private static final String patternCalendar = "^(\\[u-ca=([^\\]]*)\\])";
    private static final String patternCalendarName = "^(\\w*)$";
    private static final String patternTimeZoneBracketedAnnotation = "^(\\[([^\\]]*)\\])";
    private static final String patternTimeZoneNumericUTCOffset = "^([+\\-\\u2212])(\\d\\d):?((\\d\\d):?(?:(\\d\\d)(?:[\\.,]([\\d]*)?)?)?)?";
    private static final String patternDateSpecYearMonth = "^([+\\-\\u2212]\\d\\d\\d\\d\\d\\d|\\d\\d\\d\\d)[\\-]?(\\d\\d)";
    private static final String patternDateSpecMonthDay = "^(?:\\-\\-)?(\\d\\d)[\\-]?(\\d\\d)";
    private static final String patternTimeZoneIANANameComponent = "^([A-Za-z_]+(/[A-Za-z\\-_]+)*)";
    private final JSContext context = JavaScriptLanguage.get(null).getJSContext();
    private static final TruffleString UC_T = Strings.constant("T");
    private static final TruffleString T = Strings.constant("t");
    private static final TruffleString U_CA_EQUALS = Strings.constant("u-ca=");
    private static final TruffleString ETC_GMT = Strings.constant("Etc/GMT");
    private final TruffleString input;
    private TruffleString rest;
    private int pos;
    private TruffleString year;
    private TruffleString month;
    private TruffleString day;
    private TruffleString hour;
    private TruffleString minute;
    private TruffleString second;
    private TruffleString fraction;
    private TruffleString calendar;
    private TruffleString timeZoneIANAName;
    private TruffleString timeZoneUTCOffsetName;
    private TruffleString timeZoneNumericUTCOffset;
    private TruffleString timeZoneEtcName;
    private TruffleString utcDesignator;
    private TruffleString offsetSign;
    private TruffleString offsetHour;
    private TruffleString offsetMinute;
    private TruffleString offsetSecond;
    private TruffleString offsetFraction;

    public TemporalParser(TruffleString input) {
        this.input = input;
    }

    public JSTemporalParserRecord parseISODateTime() {
        JSTemporalParserRecord rec = this.parseCalendarDateTime();
        if (rec != null) {
            return rec;
        }
        rec = this.parseCalendarTime();
        if (rec != null) {
            return rec;
        }
        rec = this.parseCalendarDateTimeTimeRequired();
        if (rec != null) {
            return rec;
        }
        rec = this.parseDateSpecYearMonth();
        if (rec != null) {
            return rec;
        }
        rec = this.parseDateSpecMonthDay();
        if (rec != null) {
            return rec;
        }
        rec = this.parseTemporalInstantString();
        if (rec != null) {
            return rec;
        }
        rec = this.parseZonedDateTimeString();
        if (rec != null) {
            return rec;
        }
        return null;
    }

    private JSTemporalParserRecord parseCalendarTime() {
        boolean hasTimeDesignator;
        this.reset();
        boolean bl = hasTimeDesignator = Strings.startsWith(this.rest, UC_T) || Strings.startsWith(this.rest, T);
        if (hasTimeDesignator) {
            this.move(1);
        }
        if (this.tryParseTimeSpec()) {
            this.parseTimeZone();
            boolean hasCalendar = this.parseCalendar();
            if ((hasTimeDesignator || hasCalendar) && this.atEnd()) {
                return this.result();
            }
        }
        this.reset();
        JSTemporalParserRecord rec = this.parseTimeSpecWithOptionalTimeZoneNotAmbiguous();
        return rec;
    }

    private JSTemporalParserRecord parseTimeSpecWithOptionalTimeZoneNotAmbiguous() {
        this.reset();
        if (this.tryParseHour()) {
            this.tryParseTimeZoneNumericUTCOffset(true);
            this.tryParseTimeZoneBracketedAnnotation();
            if (this.atEnd()) {
                return this.result();
            }
        }
        this.reset();
        if (this.tryParseTimeHourNotValidMonth() && this.parseTimeZone() && this.atEnd()) {
            return this.result();
        }
        this.reset();
        TruffleString previousRest = this.rest;
        if (this.tryParseTimeSpec()) {
            long h = TemporalParser.getNumber(this.hour);
            long min2 = TemporalParser.getNumber(this.minute);
            long s = TemporalParser.getNumber(this.second);
            if (s < 0L && Strings.length(previousRest) >= 3 && Strings.charAt(previousRest, 2) == ':' && TemporalParser.isValidMinute(min2)) {
                this.parseTimeZone();
                if (this.atEnd()) {
                    return this.result();
                }
            }
            if (s < 0L && Strings.length(previousRest) >= 3 && Strings.charAt(previousRest, 2) != ':') {
                boolean ok = false;
                if ((h == 0L || 13L <= h && h <= 23L) && TemporalParser.isValidMinute(min2)) {
                    ok = true;
                }
                if (TemporalParser.isValidHour(h) && (min2 == 0L || 32L <= min2 && min2 <= 60L)) {
                    ok = true;
                }
                if (min2 == 31L && (h == 2L || h == 4L || h == 6L || h == 9L || h == 11L)) {
                    ok = true;
                }
                if (h == 2L && min2 == 30L) {
                    ok = true;
                }
                if (ok) {
                    this.tryParseTimeZoneBracketedAnnotation();
                    if (this.atEnd()) {
                        return this.result();
                    }
                }
            }
            if (s < 0L && this.tryParseTimeZoneNumericUTCOffset(true)) {
                this.tryParseTimeZoneBracketedAnnotation();
                if (this.atEnd()) {
                    return this.result();
                }
            }
            if (this.tryParseNegativeTimeHourNotValidMonth()) {
                this.tryParseTimeZoneBracketedAnnotation();
                if (this.atEnd()) {
                    return this.result();
                }
            }
            if (Strings.length(previousRest) > 5 && Strings.charAt(previousRest, 2) == ':' && Strings.charAt(previousRest, 5) == ':') {
                this.parseTimeZone();
                if (this.atEnd()) {
                    return this.result();
                }
            }
            if (Strings.length(previousRest) > 2 && Strings.charAt(previousRest, 2) != ':' && (s == 0L || 13L <= s && s <= 60L)) {
                this.parseTimeZone();
                if (this.atEnd()) {
                    return this.result();
                }
            }
            if (Strings.length(previousRest) > 2 && Strings.charAt(previousRest, 2) != ':' && this.fraction != null) {
                this.parseTimeZone();
                if (this.atEnd()) {
                    return this.result();
                }
            }
        }
        return null;
    }

    private boolean tryParseNegativeTimeHourNotValidMonth() {
        int h;
        if (Strings.length(this.rest) > 0 && Strings.charAt(this.rest, 0) == '-' && (0 == (h = this.parseTwoDigits(1)) || 13 <= h && h <= 23)) {
            this.offsetHour = Strings.lazySubstring(this.rest, 1, 2);
            this.move(3);
            return true;
        }
        return false;
    }

    private static boolean isValidMinute(long min2) {
        return 0L <= min2 && min2 <= 59L;
    }

    private static boolean isValidHour(long h) {
        return 0L <= h && h <= 23L;
    }

    private static long getNumber(TruffleString s) {
        if (s == null) {
            return -1L;
        }
        try {
            return Strings.parseLong(s);
        }
        catch (TruffleString.NumberFormatException ex) {
            return -1L;
        }
    }

    private boolean tryParseHour() {
        int num = this.parseTwoDigits(0);
        if (0 <= num && num <= 23) {
            this.hour = Strings.lazySubstring(this.rest, 0, 2);
            this.move(2);
            return true;
        }
        return false;
    }

    private boolean tryParseTimeHourNotValidMonth() {
        int num = this.parseTwoDigits(0);
        if (num == 0 || 13 <= num && num <= 23) {
            this.hour = Strings.lazySubstring(this.rest, 0, 2);
            this.move(2);
            return true;
        }
        return false;
    }

    private int parseTwoDigits(int at) {
        if (Strings.length(this.rest) >= at + 2) {
            char next0 = Strings.charAt(this.rest, at + 0);
            char next1 = Strings.charAt(this.rest, at + 1);
            if (TemporalParser.isDigit(next0) && TemporalParser.isDigit(next1)) {
                return TemporalParser.toDigit(next0) * 10 + TemporalParser.toDigit(next1);
            }
        }
        return -1;
    }

    private static boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private static int toDigit(char c) {
        assert (TemporalParser.isDigit(c));
        return c - 48;
    }

    public JSTemporalParserRecord parseCalendarDateTime() {
        this.reset();
        if (this.parseDateTime()) {
            this.parseCalendar();
            if (this.atEnd()) {
                return this.result();
            }
        }
        return null;
    }

    private boolean parseDateTime() {
        if (!this.parseDate()) {
            return false;
        }
        this.parseTimeSpecSeparator(true);
        this.parseTimeZone();
        return true;
    }

    private JSTemporalParserRecord parseCalendarDateTimeTimeRequired() {
        this.reset();
        if (this.parseDate()) {
            if (!this.parseTimeSpecSeparator(false)) {
                return null;
            }
            this.parseTimeZone();
            this.parseCalendar();
            if (this.atEnd()) {
                return this.result();
            }
        }
        return null;
    }

    private boolean parseTimeSpecSeparator(boolean optional) {
        int posBackup = this.pos;
        TruffleString restBackup = this.rest;
        if (!this.tryParseDateTimeSeparator()) {
            return optional;
        }
        if (!this.tryParseTimeSpec()) {
            this.pos = posBackup;
            this.rest = restBackup;
            return false;
        }
        return true;
    }

    public JSTemporalParserRecord parseYearMonth() {
        JSTemporalParserRecord rec = this.parseDateSpecYearMonth();
        if (rec != null) {
            return rec;
        }
        rec = this.parseCalendarDateTime();
        if (rec != null) {
            return rec;
        }
        return null;
    }

    private JSTemporalParserRecord parseDateSpecYearMonth() {
        this.reset();
        if (this.tryParseDateSpecYearMonth() && this.atEnd()) {
            return this.result();
        }
        return null;
    }

    public JSTemporalParserRecord parseMonthDay() {
        JSTemporalParserRecord rec = this.parseDateSpecMonthDay();
        if (rec != null) {
            return rec;
        }
        rec = this.parseCalendarDateTime();
        if (rec != null) {
            return rec;
        }
        return null;
    }

    private JSTemporalParserRecord parseDateSpecMonthDay() {
        this.reset();
        if (this.tryParseDateSpecMonthDay() && this.atEnd()) {
            return this.result();
        }
        return null;
    }

    public JSTemporalParserRecord parseTimeZoneString() {
        this.reset();
        if (this.parseTimeZoneIdentifier() && this.atEnd()) {
            return this.result();
        }
        this.reset();
        if (this.parseDate() && this.parseTimeSpecSeparator(true) && this.parseTimeZone()) {
            this.parseCalendar();
            if (this.atEnd()) {
                return this.result();
            }
        }
        return null;
    }

    private boolean parseTimeZoneIANAName() {
        TruffleString ianaName = this.rest;
        if (Strings.startsWith(this.rest, ETC_GMT)) {
            this.move(Strings.length(ETC_GMT));
            if (Strings.charAt(this.rest, 0) == '+' || Strings.charAt(this.rest, 0) == '-') {
                this.move(1);
                try {
                    int unpaddedHour = this.rest.parseIntUncached();
                    if (0 <= unpaddedHour && unpaddedHour <= 23) {
                        this.timeZoneIANAName = ianaName;
                        return true;
                    }
                }
                catch (TruffleString.NumberFormatException unpaddedHour) {
                    // empty catch block
                }
            }
        }
        this.reset();
        Matcher matcher = TemporalParser.createMatch(patternTimeZoneIANANameComponent, this.rest);
        if (matcher.matches()) {
            this.timeZoneIANAName = this.group(this.rest, matcher, 1);
            assert (this.timeZoneIANAName == null || TemporalParser.isTZLeadingChar(Strings.charAt(this.timeZoneIANAName, 0)));
            this.move(matcher.end(1));
            return true;
        }
        return false;
    }

    public JSTemporalParserRecord parseTimeZoneNumericUTCOffset() {
        this.reset();
        if (this.tryParseTimeZoneNumericUTCOffset(false) && this.atEnd()) {
            return this.result();
        }
        return null;
    }

    public JSTemporalParserRecord parseCalendarString() {
        JSTemporalParserRecord rec = this.parseCalendarName();
        if (rec != null) {
            return rec;
        }
        rec = this.parseTemporalInstantString();
        if (rec != null) {
            return rec;
        }
        rec = this.parseCalendarDateTime();
        if (rec != null) {
            return rec;
        }
        rec = this.parseCalendarTime();
        if (rec != null) {
            return rec;
        }
        rec = this.parseDateSpecYearMonth();
        if (rec != null) {
            return rec;
        }
        rec = this.parseDateSpecMonthDay();
        if (rec != null) {
            return rec;
        }
        return null;
    }

    private JSTemporalParserRecord parseTemporalInstantString() {
        this.reset();
        if (this.parseDate() && this.parseTimeSpecSeparator(true) && this.tryParseTimeZoneOffsetRequired() && this.atEnd()) {
            return this.result();
        }
        return null;
    }

    private boolean tryParseTimeZoneOffsetRequired() {
        if (!this.tryParseTimeZoneUTCOffset()) {
            return false;
        }
        this.tryParseTimeZoneBracketedAnnotation();
        return true;
    }

    private JSTemporalParserRecord parseCalendarName() {
        this.reset();
        if (this.tryParseCalendarName()) {
            return this.result();
        }
        return null;
    }

    private JSTemporalParserRecord parseZonedDateTimeString() {
        this.reset();
        if (this.parseDate()) {
            this.tryParseDateTimeSeparator();
            this.tryParseTimeSpec();
            if (!this.tryParseTimeZoneNameRequired()) {
                return null;
            }
            this.parseCalendar();
            if (this.atEnd()) {
                return this.result();
            }
        }
        return null;
    }

    public boolean isTemporalZonedDateTimeString() {
        return this.parseZonedDateTimeString() != null;
    }

    public boolean isTemporalDateTimeString() {
        this.reset();
        JSTemporalParserRecord rec = this.parseCalendarDateTime();
        return rec != null;
    }

    private boolean tryParseTimeZoneNameRequired() {
        this.tryParseTimeZoneUTCOffset();
        return this.tryParseTimeZoneBracketedAnnotation();
    }

    private boolean tryParseCalendarName() {
        Matcher matcher = TemporalParser.createMatch(patternCalendarName, this.rest);
        if (matcher.matches()) {
            this.calendar = this.group(this.rest, matcher, 1);
            this.move(matcher.end(1));
            return true;
        }
        return false;
    }

    public JSTemporalParserRecord result() {
        try {
            return new JSTemporalParserRecord(this.utcDesignator != null, TemporalParser.prepare(this.year, Long.MAX_VALUE, true), TemporalParser.prepare(this.month, 12L), TemporalParser.prepare(this.day, 31L), TemporalParser.prepare(this.hour, 23L), TemporalParser.prepare(this.minute, 59L), TemporalParser.prepare(this.second, 60L), this.fraction, this.offsetSign, TemporalParser.prepare(this.offsetHour, 23L), TemporalParser.prepare(this.offsetMinute, 59L), TemporalParser.prepare(this.offsetSecond, 59L), this.offsetFraction, this.timeZoneIANAName, this.timeZoneEtcName, this.timeZoneUTCOffsetName, this.calendar, this.timeZoneNumericUTCOffset);
        }
        catch (Exception ex) {
            return null;
        }
    }

    private static long prepare(TruffleString value2, long max2) {
        return TemporalParser.prepare(value2, max2, false);
    }

    private static long prepare(TruffleString value2, long max2, boolean canBeNegative) {
        if (value2 == null) {
            return Long.MIN_VALUE;
        }
        long l = 0L;
        try {
            l = Strings.parseLong(value2);
        }
        catch (TruffleString.NumberFormatException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
        if (!canBeNegative && l < 0L || l > max2) {
            throw new RuntimeException("date value out of bounds");
        }
        return l;
    }

    private boolean atEnd() {
        return this.pos >= Strings.length(this.input);
    }

    private void reset() {
        this.pos = 0;
        this.rest = this.input;
        this.year = null;
        this.month = null;
        this.day = null;
        this.hour = null;
        this.minute = null;
        this.second = null;
        this.fraction = null;
        this.calendar = null;
        this.timeZoneIANAName = null;
        this.timeZoneUTCOffsetName = null;
        this.timeZoneNumericUTCOffset = null;
        this.timeZoneEtcName = null;
        this.utcDesignator = null;
        this.offsetSign = null;
        this.offsetHour = null;
        this.offsetMinute = null;
        this.offsetSecond = null;
        this.offsetFraction = null;
    }

    private void move(int newPos) {
        this.pos += newPos;
        this.rest = this.pos >= 0 && Strings.length(this.input) > this.pos ? Strings.lazySubstring(this.input, this.pos) : Strings.EMPTY_STRING;
    }

    private boolean tryParseDateSpecYearMonth() {
        Matcher matcher = TemporalParser.createMatch(patternDateSpecYearMonth, this.rest);
        if (matcher.matches()) {
            this.year = this.group(this.rest, matcher, 1);
            this.month = this.group(this.rest, matcher, 2);
            if (Strings.charAt(this.year, 0) == '\u2212') {
                this.year = Strings.concat(Strings.DASH, Strings.lazySubstring(this.year, 1));
            }
            this.move(matcher.end(2));
            return true;
        }
        return false;
    }

    private boolean tryParseDateSpecMonthDay() {
        Matcher matcher = TemporalParser.createMatch(patternDateSpecMonthDay, this.rest);
        if (matcher.matches()) {
            this.month = this.group(this.rest, matcher, 1);
            this.day = this.group(this.rest, matcher, 2);
            this.move(matcher.end(2));
            return true;
        }
        return false;
    }

    private boolean parseDate() {
        Matcher matcher = TemporalParser.createMatch(patternDate, this.rest);
        if (matcher.matches()) {
            this.year = this.group(this.rest, matcher, 1);
            this.month = this.group(this.rest, matcher, 2);
            this.day = this.group(this.rest, matcher, 3);
            if (Strings.charAt(this.year, 0) == '\u2212') {
                this.year = Strings.concat(Strings.DASH, Strings.lazySubstring(this.year, 1));
            }
            this.move(matcher.end(3));
            return true;
        }
        return false;
    }

    private boolean tryParseTimeSpec() {
        Matcher matcher = TemporalParser.createMatch(patternTime, this.rest);
        if (matcher.matches()) {
            this.hour = this.group(this.rest, matcher, 1);
            this.minute = this.group(this.rest, matcher, 3);
            this.second = this.group(this.rest, matcher, 4);
            this.fraction = this.group(this.rest, matcher, 5);
            this.move(matcher.end(2) < 0 ? matcher.end(1) : matcher.end(2));
            return true;
        }
        return false;
    }

    private boolean tryParseDateTimeSeparator() {
        if (Strings.length(this.rest) <= 0) {
            return false;
        }
        char ch = Strings.charAt(this.rest, 0);
        if (ch == 't' || ch == 'T' || ch == ' ') {
            this.move(1);
            return true;
        }
        return false;
    }

    private boolean parseCalendar() {
        Matcher matcher = TemporalParser.createMatch(patternCalendar, this.rest);
        if (matcher.matches()) {
            this.calendar = this.group(this.rest, matcher, 2);
            this.move(matcher.end(1));
            return true;
        }
        return false;
    }

    private boolean parseTimeZone() {
        if (this.tryParseTimeZoneUTCOffset()) {
            this.tryParseTimeZoneBracketedAnnotation();
            if (this.atEnd()) {
                return true;
            }
        }
        return this.tryParseTimeZoneBracketedAnnotation();
    }

    private boolean tryParseTimeZoneUTCOffset() {
        if (this.tryParseTimeZoneNumericUTCOffset(false)) {
            return true;
        }
        return this.tryParseUTCDesignator();
    }

    private boolean tryParseUTCDesignator() {
        if (Strings.startsWith(this.rest, Strings.UC_Z) || Strings.startsWith(this.rest, Strings.Z)) {
            this.move(1);
            this.timeZoneIANAName = TemporalConstants.UTC;
            this.utcDesignator = Strings.Z;
            return true;
        }
        return false;
    }

    private boolean tryParseTimeZoneNumericUTCOffset(boolean nonAmbiguous) {
        Matcher matcher = TemporalParser.createMatch(patternTimeZoneNumericUTCOffset, this.rest, true);
        if (matcher.matches()) {
            this.offsetSign = this.group(this.rest, matcher, 1);
            this.offsetHour = this.group(this.rest, matcher, 2);
            this.offsetMinute = this.group(this.rest, matcher, 4);
            this.offsetSecond = this.group(this.rest, matcher, 5);
            this.offsetFraction = this.group(this.rest, matcher, 6);
            this.timeZoneNumericUTCOffset = Strings.substring(this.context, this.rest, matcher.start(1), matcher.end(3) != -1 ? matcher.end(3) : Strings.length(this.rest));
            if (this.offsetHour == null) {
                return false;
            }
            if (nonAmbiguous && matcher.start(3) < 0 && Strings.charAt(this.rest, 0) == '-') {
                return false;
            }
            this.move(this.offsetMinute != null ? matcher.end(3) : matcher.end(2));
            return true;
        }
        return false;
    }

    private boolean parseTimeZoneIdentifier() {
        this.reset();
        if (this.parseTimeZoneIANAName()) {
            return true;
        }
        this.reset();
        return this.tryParseTimeZoneNumericUTCOffset(false);
    }

    private boolean tryParseTimeZoneBracketedAnnotation() {
        Matcher matcher = TemporalParser.createMatch(patternTimeZoneBracketedAnnotation, this.rest);
        if (matcher.matches()) {
            TruffleString content = this.group(this.rest, matcher, 2);
            if (Strings.startsWith(content, U_CA_EQUALS)) {
                return false;
            }
            if (content != null) {
                if (Strings.startsWith(content, Strings.UC_ETC)) {
                    this.timeZoneEtcName = content;
                } else if (TemporalParser.isSign(Strings.charAt(content, 0))) {
                    this.timeZoneUTCOffsetName = content;
                } else {
                    assert (TemporalParser.isTZLeadingChar(Strings.charAt(content, 0)));
                    this.timeZoneIANAName = content;
                }
            }
            this.move(matcher.end(1));
            return true;
        }
        return false;
    }

    private static boolean isTZLeadingChar(char c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || c == '.' || c == '_';
    }

    private static boolean isSign(char c) {
        return c == '+' || c == '-' || c == '\u2212';
    }

    private static Matcher createMatch(String pattern, TruffleString input) {
        return TemporalParser.createMatch(pattern, input, true);
    }

    private static Matcher createMatch(String pattern, TruffleString input, boolean addMatchAll) {
        Pattern patternObj = Pattern.compile(pattern + (addMatchAll ? ".*" : ""));
        return patternObj.matcher(Strings.toJavaString(input));
    }

    private TruffleString group(TruffleString string, Matcher matcher, int groupNumber) {
        int start2 = matcher.start(groupNumber);
        return start2 < 0 ? null : Strings.substring(this.context, string, start2, matcher.end(groupNumber) - start2);
    }
}

