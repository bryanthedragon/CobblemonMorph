package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public class JSTemporalPlainYearMonthObject extends JSNonProxyObject implements TemporalYear, TemporalMonth, TemporalCalendar {
   private final int isoYear;
   private final int isoMonth;
   private final int isoDay;
   private final JSDynamicObject calendar;

   protected JSTemporalPlainYearMonthObject(Shape shape, int isoYear, int isoMonth, int isoDay, JSDynamicObject calendar) {
      super(shape);
      this.isoYear = isoYear;
      this.isoMonth = isoMonth;
      this.isoDay = isoDay;
      this.calendar = calendar;
   }

   @Override
   public int getYear() {
      return this.isoYear;
   }

   @Override
   public int getMonth() {
      return this.isoMonth;
   }

   public int getDay() {
      return this.isoDay;
   }

   @Override
   public JSDynamicObject getCalendar() {
      return this.calendar;
   }
}
