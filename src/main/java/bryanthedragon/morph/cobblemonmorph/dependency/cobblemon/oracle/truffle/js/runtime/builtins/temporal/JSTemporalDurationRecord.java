
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDateTimeRecord;

public final class JSTemporalDurationRecord {
    private final double years;
    private final double months;
    private final double days;
    private final double hours;
    private final double minutes;
    private final double seconds;
    private final double milliseconds;
    private final double microseconds;
    private final double nanoseconds;
    private final double weeks;
    private final double remainder;

    private JSTemporalDurationRecord(double years, double months, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds, double weeks, double remainder) {
        this.years = years;
        this.months = months;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
        this.microseconds = microseconds;
        this.nanoseconds = nanoseconds;
        this.weeks = weeks;
        this.remainder = remainder;
    }

    public static JSTemporalDurationRecord create(double years, double months, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds) {
        return new JSTemporalDurationRecord(years, months, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, 0.0, 0.0);
    }

    public static JSTemporalDurationRecord createWeeks(double years, double months, double weeks, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds) {
        return new JSTemporalDurationRecord(years, months, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, weeks, 0.0);
    }

    public static JSTemporalDurationRecord createWeeksRemainder(double years, double months, double weeks, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds, double remainder) {
        return new JSTemporalDurationRecord(years, months, days, hours, minutes, seconds, milliseconds, microseconds, nanoseconds, weeks, remainder);
    }

    public double getYears() {
        return this.years;
    }

    public double getMonths() {
        return this.months;
    }

    public double getDays() {
        return this.days;
    }

    public double getHours() {
        return this.hours;
    }

    public double getMinutes() {
        return this.minutes;
    }

    public double getSeconds() {
        return this.seconds;
    }

    public double getMilliseconds() {
        return this.milliseconds;
    }

    public double getMicroseconds() {
        return this.microseconds;
    }

    public double getNanoseconds() {
        return this.nanoseconds;
    }

    public double getWeeks() {
        return this.weeks;
    }

    public double getRemainder() {
        return this.remainder;
    }

    public static JSTemporalDurationRecord create(JSTemporalDateTimeRecord r) {
        return JSTemporalDurationRecord.create(r.getYear(), r.getMonth(), r.getDay(), r.getHour(), r.getMinute(), r.getSecond(), r.getMillisecond(), r.getMicrosecond(), r.getNanosecond());
    }

    public JSTemporalDurationRecord copyNegated() {
        return JSTemporalDurationRecord.createWeeks(-this.getYears(), -this.getMonths(), -this.getWeeks(), -this.getDays(), -this.getHours(), -this.getMinutes(), -this.getSeconds(), -this.getMilliseconds(), -this.getMicroseconds(), -this.getNanoseconds());
    }
}

