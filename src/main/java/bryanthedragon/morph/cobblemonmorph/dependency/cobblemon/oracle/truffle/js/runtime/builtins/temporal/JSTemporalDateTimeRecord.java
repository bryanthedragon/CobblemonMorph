package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.strings.TruffleString;

public class JSTemporalDateTimeRecord {
   private final int year;
   private final int month;
   private final int day;
   private final int hour;
   private final int minute;
   private final int second;
   private final int millisecond;
   private final int microsecond;
   private final int nanosecond;
   private final TruffleString calendar;
   private final boolean hasCalendar;

   protected JSTemporalDateTimeRecord(
      int year,
      int month,
      int day,
      int hour,
      int minute,
      int second,
      int millisecond,
      int microsecond,
      int nanosecond,
      TruffleString calendar,
      boolean hasCalendar
   ) {
      this.year = year;
      this.month = month;
      this.day = day;
      this.hour = hour;
      this.minute = minute;
      this.second = second;
      this.millisecond = millisecond;
      this.microsecond = microsecond;
      this.nanosecond = nanosecond;
      this.calendar = calendar;
      this.hasCalendar = hasCalendar;
   }

   public static JSTemporalDateTimeRecord create(
      int year, int month, int day, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond
   ) {
      return new JSTemporalDateTimeRecord(year, month, day, hour, minute, second, millisecond, microsecond, nanosecond, null, false);
   }

   public static JSTemporalDateTimeRecord createCalendar(
      int year, int month, int day, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond, TruffleString calendar
   ) {
      return new JSTemporalDateTimeRecord(year, month, day, hour, minute, second, millisecond, microsecond, nanosecond, calendar, true);
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

   public int getHour() {
      return this.hour;
   }

   public int getMinute() {
      return this.minute;
   }

   public int getSecond() {
      return this.second;
   }

   public int getMillisecond() {
      return this.millisecond;
   }

   public int getMicrosecond() {
      return this.microsecond;
   }

   public int getNanosecond() {
      return this.nanosecond;
   }

   public TruffleString getCalendar() {
      return this.hasCalendar ? this.calendar : null;
   }

   public boolean hasCalendar() {
      return this.hasCalendar && this.calendar != null;
   }
}
