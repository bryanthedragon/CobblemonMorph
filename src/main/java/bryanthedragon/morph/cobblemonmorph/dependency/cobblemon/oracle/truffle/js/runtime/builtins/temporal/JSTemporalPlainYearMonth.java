package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainYearMonthFunctionBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainYearMonthPrototypeBuiltins;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
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

public final class JSTemporalPlainYearMonth extends JSNonProxy implements JSConstructorFactory.WithFunctionsAndSpecies, PrototypeSupplier {
   public static final JSTemporalPlainYearMonth INSTANCE = new JSTemporalPlainYearMonth();
   public static final TruffleString CLASS_NAME = Strings.constant("PlainYearMonth");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("PlainYearMonth.prototype");
   public static final TruffleString TO_STRING_TAG = Strings.constant("Temporal.PlainYearMonth");

   private JSTemporalPlainYearMonth() {
   }

   public static JSTemporalPlainYearMonthObject create(
      JSContext context, int isoYear, int isoMonth, JSDynamicObject calendar, int referenceISODay, BranchProfile errorBranch
   ) {
      if (!TemporalUtil.validateISODate(isoYear, isoMonth, referenceISODay)) {
         errorBranch.enter();
         throw TemporalErrors.createRangeErrorDateOutsideRange();
      } else if (!TemporalUtil.isoYearMonthWithinLimits(isoYear, isoMonth)) {
         errorBranch.enter();
         throw TemporalErrors.createRangeErrorYearMonthOutsideRange();
      } else {
         return createIntl(context, isoYear, isoMonth, calendar, referenceISODay);
      }
   }

   private static JSTemporalPlainYearMonthObject createIntl(JSContext context, int isoYear, int isoMonth, JSDynamicObject calendar, int referenceISODay) {
      JSRealm realm = JSRealm.get(null);
      JSObjectFactory factory = context.getTemporalPlainYearMonthFactory();
      JSTemporalPlainYearMonthObject obj = factory.initProto(
         new JSTemporalPlainYearMonthObject(factory.getShape(realm), isoYear, isoMonth, referenceISODay, calendar), realm
      );
      return context.trackAllocation(obj);
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
         prototype, TemporalConstants.CALENDAR, realm.lookupAccessor(TemporalPlainYearMonthPrototypeBuiltins.BUILTINS, TemporalConstants.CALENDAR)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.YEAR, realm.lookupAccessor(TemporalPlainYearMonthPrototypeBuiltins.BUILTINS, TemporalConstants.YEAR)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.MONTH, realm.lookupAccessor(TemporalPlainYearMonthPrototypeBuiltins.BUILTINS, TemporalConstants.MONTH)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.MONTH_CODE, realm.lookupAccessor(TemporalPlainYearMonthPrototypeBuiltins.BUILTINS, TemporalConstants.MONTH_CODE)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.DAYS_IN_YEAR, realm.lookupAccessor(TemporalPlainYearMonthPrototypeBuiltins.BUILTINS, TemporalConstants.DAYS_IN_YEAR)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.DAYS_IN_MONTH, realm.lookupAccessor(TemporalPlainYearMonthPrototypeBuiltins.BUILTINS, TemporalConstants.DAYS_IN_MONTH)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.MONTHS_IN_YEAR, realm.lookupAccessor(TemporalPlainYearMonthPrototypeBuiltins.BUILTINS, TemporalConstants.MONTHS_IN_YEAR)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.IN_LEAP_YEAR, realm.lookupAccessor(TemporalPlainYearMonthPrototypeBuiltins.BUILTINS, TemporalConstants.IN_LEAP_YEAR)
      );
      JSObjectUtil.putFunctionsFromContainer(realm, prototype, TemporalPlainYearMonthPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(prototype, TO_STRING_TAG);
      return prototype;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getTemporalPlainYearMonthPrototype();
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, TemporalPlainYearMonthFunctionBuiltins.BUILTINS);
   }

   public static boolean isJSTemporalPlainYearMonth(Object obj) {
      return obj instanceof JSTemporalPlainYearMonthObject;
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString temporalYearMonthToString(JSTemporalPlainYearMonthObject ym, TemporalUtil.ShowCalendar showCalendar) {
      TruffleString year = TemporalUtil.padISOYear(ym.getYear());
      TruffleString month = Strings.format("%1$02d", ym.getMonth());
      TruffleString result = Strings.concatAll(year, Strings.DASH, month);
      TruffleString calendarID = JSRuntime.toString(ym.getCalendar());
      if (showCalendar == TemporalUtil.ShowCalendar.ALWAYS || !TemporalConstants.ISO8601.equals(calendarID)) {
         TruffleString day = Strings.format("%1$02d", ym.getDay());
         result = Strings.concatAll(result, Strings.DASH, day);
      }

      TruffleString calendarString = TemporalUtil.formatCalendarAnnotation(calendarID, showCalendar);
      if (!calendarString.isEmpty()) {
         result = Strings.concat(result, calendarString);
      }

      return result;
   }
}
