package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainMonthDayFunctionBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainMonthDayPrototypeBuiltins;
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

public class JSTemporalPlainMonthDay extends JSNonProxy implements JSConstructorFactory.WithFunctionsAndSpecies, PrototypeSupplier {
   public static final JSTemporalPlainMonthDay INSTANCE = new JSTemporalPlainMonthDay();
   public static final TruffleString CLASS_NAME = Strings.constant("PlainMonthDay");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("PlainMonthDay.prototype");
   public static final TruffleString TO_STRING_TAG = Strings.constant("Temporal.PlainYearMonth");

   public static JSTemporalPlainMonthDayObject create(
      JSContext context, int isoMonth, int isoDay, JSDynamicObject calendar, int referenceISOYear, BranchProfile errorBranch
   ) {
      if (!TemporalUtil.validateISODate(referenceISOYear, isoMonth, isoDay)) {
         errorBranch.enter();
         throw TemporalErrors.createRangeErrorMonthDayOutsideRange();
      } else if (referenceISOYear >= -271821 && 275760 >= referenceISOYear) {
         JSRealm realm = JSRealm.get(null);
         JSObjectFactory factory = context.getTemporalPlainMonthDayFactory();
         JSTemporalPlainMonthDayObject obj = factory.initProto(
            new JSTemporalPlainMonthDayObject(factory.getShape(realm), isoMonth, isoDay, calendar, referenceISOYear), realm
         );
         return context.trackAllocation(obj);
      } else {
         errorBranch.enter();
         throw TemporalErrors.createRangeErrorMonthDayOutsideRange();
      }
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
         prototype, TemporalConstants.CALENDAR, realm.lookupAccessor(TemporalPlainMonthDayPrototypeBuiltins.BUILTINS, TemporalConstants.CALENDAR)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.MONTH_CODE, realm.lookupAccessor(TemporalPlainMonthDayPrototypeBuiltins.BUILTINS, TemporalConstants.MONTH_CODE)
      );
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.DAY, realm.lookupAccessor(TemporalPlainMonthDayPrototypeBuiltins.BUILTINS, TemporalConstants.DAY)
      );
      JSObjectUtil.putFunctionsFromContainer(realm, prototype, TemporalPlainMonthDayPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(prototype, TO_STRING_TAG);
      return prototype;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getTemporalPlainMonthDayPrototype();
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, TemporalPlainMonthDayFunctionBuiltins.BUILTINS);
   }

   public static boolean isJSTemporalPlainMonthDay(Object obj) {
      return obj instanceof JSTemporalPlainMonthDayObject;
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleString temporalMonthDayToString(JSTemporalPlainMonthDayObject md, TemporalUtil.ShowCalendar showCalendar) {
      TruffleString monthString = Strings.format("%1$02d", md.getMonth());
      TruffleString dayString = Strings.format("%1$02d", md.getDay());
      TruffleString calendarID = JSRuntime.toString(md.getCalendar());
      TruffleString result = Strings.format("%s-%s", monthString, dayString);
      if (showCalendar == TemporalUtil.ShowCalendar.ALWAYS || !TemporalConstants.ISO8601.equals(calendarID)) {
         TruffleString year = TemporalUtil.padISOYear(md.getYear());
         result = Strings.format("%s-%s", year, result);
      }

      TruffleString calendarString = TemporalUtil.formatCalendarAnnotation(calendarID, showCalendar);
      return calendarString.isEmpty() ? result : Strings.concat(result, calendarString);
   }
}
