
package com.oracle.truffle.js.runtime.builtins.temporal;

public final class JSTemporalYearMonthDayRecord {
    private final int year;
    private final int month;
    private final int day;

    private JSTemporalYearMonthDayRecord(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public static JSTemporalYearMonthDayRecord create(int year, int month, int day) {
        return new JSTemporalYearMonthDayRecord(year, month, day);
    }

    public static JSTemporalYearMonthDayRecord create(int year, int month) {
        return new JSTemporalYearMonthDayRecord(year, month, 0);
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

    public int getDay() {
        return this.day;
    }
}

