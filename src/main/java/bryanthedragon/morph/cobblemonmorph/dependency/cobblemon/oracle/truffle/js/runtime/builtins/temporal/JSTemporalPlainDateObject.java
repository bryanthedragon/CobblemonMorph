package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.time.LocalDate;

@ExportLibrary(InteropLibrary.class)
public class JSTemporalPlainDateObject extends JSNonProxyObject implements TemporalMonth, TemporalYear, TemporalDay, TemporalCalendar {
   private final int year;
   private final int month;
   private final int day;
   private final JSDynamicObject calendar;

   public JSTemporalPlainDateObject(Shape shape, int year, int month, int day, JSDynamicObject calendar) {
      super(shape);
      this.year = year;
      this.month = month;
      this.day = day;
      this.calendar = calendar;
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
   final boolean isDate() {
      return true;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   final LocalDate asDate() {
      return LocalDate.of(this.year, this.month, this.day);
   }
}
