package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.time.LocalDate;
import java.time.LocalTime;

@ExportLibrary(InteropLibrary.class)
public class JSTemporalPlainDateTimeObject extends JSNonProxyObject implements TemporalMonth, TemporalYear, TemporalDay, TemporalCalendar {
   private final int hours;
   private final int minutes;
   private final int seconds;
   private final int milliseconds;
   private final int microseconds;
   private final int nanoseconds;
   private final int year;
   private final int month;
   private final int day;
   private final JSDynamicObject calendar;

   protected JSTemporalPlainDateTimeObject(
      Shape shape,
      int year,
      int month,
      int day,
      int hours,
      int minutes,
      int seconds,
      int milliseconds,
      int microseconds,
      int nanoseconds,
      JSDynamicObject calendar
   ) {
      super(shape);
      this.hours = hours;
      this.minutes = minutes;
      this.seconds = seconds;
      this.milliseconds = milliseconds;
      this.microseconds = microseconds;
      this.nanoseconds = nanoseconds;
      this.year = year;
      this.month = month;
      this.day = day;
      this.calendar = calendar;
   }

   public int getHour() {
      return this.hours;
   }

   public int getMinute() {
      return this.minutes;
   }

   public int getSecond() {
      return this.seconds;
   }

   public int getMillisecond() {
      return this.milliseconds;
   }

   public int getMicrosecond() {
      return this.microseconds;
   }

   public int getNanosecond() {
      return this.nanoseconds;
   }

   @Override
   public int getYear() {
      return this.year;
   }

   @Override
   public int getMonth() {
      return this.month;
   }

   @Override
   public int getDay() {
      return this.day;
   }

   @Override
   public JSDynamicObject getCalendar() {
      return this.calendar;
   }

   @ExportMessage
   final boolean isTime() {
      return true;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   final LocalTime asTime() {
      int ns = this.milliseconds * 1000000 + this.microseconds * 1000 + this.nanoseconds;
      return LocalTime.of(this.hours, this.minutes, this.seconds, ns);
   }

   @ExportMessage
   final boolean isDate() {
      return true;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   final LocalDate asDate() {
      return LocalDate.of(this.year, this.month, this.day);
   }
}
