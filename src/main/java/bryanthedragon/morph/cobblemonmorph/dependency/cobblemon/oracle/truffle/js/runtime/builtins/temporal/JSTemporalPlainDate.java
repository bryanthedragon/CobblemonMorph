package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDateFunctionBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSDate;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public final class JSTemporalPlainDate extends JSNonProxy implements JSConstructorFactory.WithFunctionsAndSpecies, PrototypeSupplier {
   public static final JSTemporalPlainDate INSTANCE = new JSTemporalPlainDate();
   public static final TruffleString CLASS_NAME = Strings.constant("PlainDate");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("PlainDate.prototype");
   public static final TruffleString TO_STRING_TAG = Strings.constant("Temporal.PlainDate");

   private JSTemporalPlainDate() {
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return TO_STRING_TAG;
   }

   @Override
   public TruffleString getClassName() {
      return CLASS_NAME;
   }

   @Override
   public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject constructor) {
      JSContext ctx = realm.getContext();
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(ctx, prototype, constructor);
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.CALENDAR, realm.lookupAccessor(TemporalPlainDatePrototypeBuiltins.BUILTINS, TemporalConstants.CALENDAR)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.YEAR, realm.lookupAccessor(TemporalPlainDatePrototypeBuiltins.BUILTINS, TemporalConstants.YEAR)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.MONTH, realm.lookupAccessor(TemporalPlainDatePrototypeBuiltins.BUILTINS, TemporalConstants.MONTH)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.MONTH_CODE, realm.lookupAccessor(TemporalPlainDatePrototypeBuiltins.BUILTINS, TemporalConstants.MONTH_CODE)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.DAY, realm.lookupAccessor(TemporalPlainDatePrototypeBuiltins.BUILTINS, TemporalConstants.DAY)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.DAY_OF_WEEK, realm.lookupAccessor(TemporalPlainDatePrototypeBuiltins.BUILTINS, TemporalConstants.DAY_OF_WEEK)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.DAY_OF_YEAR, realm.lookupAccessor(TemporalPlainDatePrototypeBuiltins.BUILTINS, TemporalConstants.DAY_OF_YEAR)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.WEEK_OF_YEAR, realm.lookupAccessor(TemporalPlainDatePrototypeBuiltins.BUILTINS, TemporalConstants.WEEK_OF_YEAR)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.DAYS_IN_WEEK, realm.lookupAccessor(TemporalPlainDatePrototypeBuiltins.BUILTINS, TemporalConstants.DAYS_IN_WEEK)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.DAYS_IN_MONTH, realm.lookupAccessor(TemporalPlainDatePrototypeBuiltins.BUILTINS, TemporalConstants.DAYS_IN_MONTH)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.DAYS_IN_YEAR, realm.lookupAccessor(TemporalPlainDatePrototypeBuiltins.BUILTINS, TemporalConstants.DAYS_IN_YEAR)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.MONTHS_IN_YEAR, realm.lookupAccessor(TemporalPlainDatePrototypeBuiltins.BUILTINS, TemporalConstants.MONTHS_IN_YEAR)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.IN_LEAP_YEAR, realm.lookupAccessor(TemporalPlainDatePrototypeBuiltins.BUILTINS, TemporalConstants.IN_LEAP_YEAR)
      );
      JSObjectUtil.putFunctionsFromContainer(realm, prototype, TemporalPlainDatePrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(prototype, TO_STRING_TAG);
      return prototype;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getTemporalPlainDatePrototype();
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, TemporalPlainDateFunctionBuiltins.BUILTINS);
   }

   public static boolean isJSTemporalPlainDate(Object obj) {
      return obj instanceof JSTemporalPlainDateObject;
   }

   public static JSTemporalPlainDateObject create(JSContext context, int year, int month, int day, JSDynamicObject calendar, BranchProfile errorBranch) {
      if (!TemporalUtil.validateISODate(year, month, day)) {
         errorBranch.enter();
         throw TemporalErrors.createRangeErrorDateTimeOutsideRange();
      } else if (!TemporalUtil.isoDateTimeWithinLimits(year, month, day, 12, 0, 0, 0, 0, 0)) {
         errorBranch.enter();
         throw TemporalErrors.createRangeErrorDateOutsideRange();
      } else {
         return createIntl(context, year, month, day, calendar);
      }
   }

   public static JSTemporalPlainDateObject create(JSContext context, int year, int month, int day, JSDynamicObject calendar) {
      if (!TemporalUtil.validateISODate(year, month, day)) {
         throw TemporalErrors.createRangeErrorDateTimeOutsideRange();
      } else if (!TemporalUtil.isoDateTimeWithinLimits(year, month, day, 12, 0, 0, 0, 0, 0)) {
         throw TemporalErrors.createRangeErrorDateOutsideRange();
      } else {
         return createIntl(context, year, month, day, calendar);
      }
   }

   private static JSTemporalPlainDateObject createIntl(JSContext context, int year, int month, int day, JSDynamicObject calendar) {
      JSRealm realm = JSRealm.get(null);
      JSObjectFactory factory = context.getTemporalPlainDateFactory();
      JSTemporalPlainDateObject object = factory.initProto(new JSTemporalPlainDateObject(factory.getShape(realm), year, month, day, calendar), realm);
      return context.trackAllocation(object);
   }

   public static JSTemporalDurationRecord differenceISODate(int y1, int m1, int d1, int y2, int m2, int d2, TemporalUtil.Unit largestUnit) {
      assert largestUnit == TemporalUtil.Unit.YEAR
         || largestUnit == TemporalUtil.Unit.MONTH
         || largestUnit == TemporalUtil.Unit.WEEK
         || largestUnit == TemporalUtil.Unit.DAY;

      if (largestUnit == TemporalUtil.Unit.YEAR || largestUnit == TemporalUtil.Unit.MONTH) {
         int sign = -TemporalUtil.compareISODate(y1, m1, d1, y2, m2, d2);
         if (sign == 0) {
            return toRecordWeeksPlural(0L, 0L, 0L, 0L);
         } else {
            JSTemporalDateTimeRecord start = toRecord(y1, m1, d1);
            JSTemporalDateTimeRecord end = toRecord(y2, m2, d2);
            int years = end.getYear() - start.getYear();
            JSTemporalDateTimeRecord mid = TemporalUtil.addISODate(y1, m1, d1, years, 0, 0, 0, TemporalUtil.Overflow.CONSTRAIN);
            int midSign = -TemporalUtil.compareISODate(mid.getYear(), mid.getMonth(), mid.getDay(), y2, m2, d2);
            if (midSign == 0) {
               return largestUnit == TemporalUtil.Unit.YEAR ? toRecordWeeksPlural(years, 0L, 0L, 0L) : toRecordWeeksPlural(0L, years * 12, 0L, 0L);
            } else {
               int months = end.getMonth() - start.getMonth();
               if (midSign != sign) {
                  years -= sign;
                  months += sign * 12;
               }

               mid = TemporalUtil.addISODate(y1, m1, d1, years, months, 0, 0, TemporalUtil.Overflow.CONSTRAIN);
               midSign = -TemporalUtil.compareISODate(mid.getYear(), mid.getMonth(), mid.getDay(), y2, m2, d2);
               if (midSign == 0) {
                  return largestUnit == TemporalUtil.Unit.YEAR ? toRecordPlural(years, months, 0L) : toRecordWeeksPlural(0L, months + years * 12, 0L, 0L);
               } else {
                  if (midSign != sign) {
                     months -= sign;
                     if (months == -sign) {
                        years -= sign;
                        months = 11 * sign;
                     }

                     mid = TemporalUtil.addISODate(y1, m1, d1, years, months, 0, 0, TemporalUtil.Overflow.CONSTRAIN);
                     midSign = -TemporalUtil.compareISODate(mid.getYear(), mid.getMonth(), mid.getDay(), y2, m2, d2);
                  }

                  int days = 0;
                  if (mid.getMonth() == end.getMonth() && mid.getYear() == end.getYear()) {
                     days = end.getDay() - mid.getDay();
                  } else if (sign < 0) {
                     days = -mid.getDay() - (TemporalUtil.isoDaysInMonth(end.getYear(), end.getMonth()) - end.getDay());
                  } else {
                     days = end.getDay() + (TemporalUtil.isoDaysInMonth(mid.getYear(), mid.getMonth()) - mid.getDay());
                  }

                  if (largestUnit == TemporalUtil.Unit.MONTH) {
                     months += years * 12;
                     years = 0;
                  }

                  return toRecordWeeksPlural(years, months, 0L, days);
               }
            }
         }
      } else if (largestUnit != TemporalUtil.Unit.DAY && largestUnit != TemporalUtil.Unit.WEEK) {
         CompilerDirectives.transferToInterpreter();
         throw Errors.shouldNotReachHere("unexpected largest unit: " + largestUnit);
      } else {
         double epochDays1 = JSDate.makeDay(y1, m1 - 1, d1);
         double epochDays2 = JSDate.makeDay(y2, m2 - 1, d2);
         int daysx = TemporalUtil.dtoi(epochDays2 - epochDays1);
         int weeks = 0;
         if (largestUnit == TemporalUtil.Unit.WEEK) {
            weeks = (int)TemporalUtil.roundTowardsZero(daysx / 7.0);
            daysx %= 7;
         }

         return toRecordWeeksPlural(0L, 0L, weeks, daysx);
      }
   }

   private static JSTemporalDurationRecord toRecordPlural(long year, long month, long day) {
      return JSTemporalDurationRecord.create(year, month, day, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   }

   private static JSTemporalDurationRecord toRecordWeeksPlural(long year, long month, long weeks, long day) {
      return JSTemporalDurationRecord.createWeeks(year, month, weeks, day, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   }

   public static JSTemporalDateTimeRecord toRecord(int year, int month, int day) {
      return JSTemporalDateTimeRecord.create(year, month, day, 0, 0, 0, 0, 0, 0);
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString temporalDateToString(JSTemporalPlainDateObject date, TemporalUtil.ShowCalendar showCalendar) {
      TruffleString yearString = TemporalUtil.padISOYear(date.getYear());
      TruffleString monthString = Strings.format("%1$02d", date.getMonth());
      TruffleString dayString = Strings.format("%1$02d", date.getDay());
      TruffleString calendarID = JSRuntime.toString(date.getCalendar());
      Object calendar = TemporalUtil.formatCalendarAnnotation(calendarID, showCalendar);
      return Strings.format("%s-%s-%s%s", yearString, monthString, dayString, calendar);
   }
}
