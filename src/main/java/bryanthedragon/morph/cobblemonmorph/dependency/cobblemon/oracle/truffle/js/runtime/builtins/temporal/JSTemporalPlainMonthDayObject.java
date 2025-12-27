package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public class JSTemporalPlainMonthDayObject extends JSNonProxyObject implements TemporalMonth, TemporalDay, TemporalCalendar {
   private final int isoMonth;
   private final int isoDay;
   private final JSDynamicObject calendar;
   private final int isoYear;

   protected JSTemporalPlainMonthDayObject(Shape shape, int isoMonth, int isoDay, JSDynamicObject calendar, int isoYear) {
      super(shape);
      this.isoMonth = isoMonth;
      this.isoDay = isoDay;
      this.calendar = calendar;
      this.isoYear = isoYear;
   }

   @Override
   public int getMonth() {
      return this.isoMonth;
   }

   @Override
   public int getDay() {
      return this.isoDay;
   }

   public int getYear() {
      return this.isoYear;
   }

   @Override
   public JSDynamicObject getCalendar() {
      return this.calendar;
   }
}
