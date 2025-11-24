
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.strings.TruffleString;

public final class JSTemporalParserRecord {
    private final boolean z;
    private final long year;
    private final long month;
    private final long day;
    private final long hour;
    private final long minute;
    private final long second;
    private final TruffleString fraction;
    private final TruffleString calendar;
    private final TruffleString timeZoneIANAName;
    private final TruffleString timeZoneEtcName;
    private final TruffleString timeZoneUTCOffsetName;
    private final TruffleString timeZoneNumericUTCOffset;
    private final TruffleString offsetSign;
    private final long offsetHour;
    private final long offsetMinute;
    private final long offsetSecond;
    private final TruffleString offsetFraction;

    public JSTemporalParserRecord(boolean z, long year, long month, long day, long hour, long minute, long second, TruffleString fraction, TruffleString offsetSign, long offsetHour, long offsetMinute, long offsetSecond, TruffleString offsetFraction, TruffleString timeZoneIANAName, TruffleString timeZoneEtcName, TruffleString timeZoneUTCOffsetName, TruffleString calendar, TruffleString timeZoneNumericUTCOffset) {
        this.z = z;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.fraction = fraction;
        this.timeZoneNumericUTCOffset = timeZoneNumericUTCOffset;
        this.offsetSign = offsetSign;
        this.offsetHour = offsetHour;
        this.offsetMinute = offsetMinute;
        this.offsetSecond = offsetSecond;
        this.offsetFraction = offsetFraction;
        this.calendar = calendar;
        this.timeZoneIANAName = timeZoneIANAName;
        this.timeZoneEtcName = timeZoneEtcName;
        this.timeZoneUTCOffsetName = timeZoneUTCOffsetName;
    }

    public boolean getZ() {
        return this.z;
    }

    public TruffleString getOffsetSign() {
        return this.offsetSign;
    }

    public long getHour() {
        return this.hour;
    }

    public long getMinute() {
        return this.minute;
    }

    public long getSecond() {
        return this.second;
    }

    public TruffleString getFraction() {
        return this.fraction;
    }

    public TruffleString getTimeZoneIANAName() {
        return this.timeZoneIANAName;
    }

    public TruffleString getTimeZoneUTCOffsetName() {
        return this.timeZoneIANAName;
    }

    public TruffleString getTimeZoneEtcName() {
        return this.timeZoneIANAName;
    }

    public TruffleString getTimeZoneANYName() {
        if (this.timeZoneIANAName != null) {
            return this.timeZoneIANAName;
        }
        if (this.timeZoneUTCOffsetName != null) {
            return this.timeZoneUTCOffsetName;
        }
        if (this.timeZoneEtcName != null) {
            return this.timeZoneEtcName;
        }
        return null;
    }

    public long getYear() {
        return this.year;
    }

    public long getMonth() {
        return this.month;
    }

    public long getDay() {
        return this.day;
    }

    public TruffleString getCalendar() {
        return this.calendar;
    }

    public long getOffsetHour() {
        return this.offsetHour;
    }

    public long getOffsetMinute() {
        return this.offsetMinute;
    }

    public long getOffsetSecond() {
        return this.offsetSecond;
    }

    public TruffleString getOffsetFraction() {
        return this.offsetFraction;
    }

    public TruffleString getTimeZoneNumericUTCOffset() {
        return this.timeZoneNumericUTCOffset;
    }
}

