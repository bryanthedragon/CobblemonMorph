package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.OrdinaryToPrimitiveNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.intl.InitializeDateTimeFormatNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSDate;
import com.oracle.truffle.js.runtime.builtins.JSDateObject;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormat;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import java.util.EnumSet;

public final class DatePrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<DatePrototypeBuiltins.DatePrototype> {
   public static final JSBuiltinsContainer BUILTINS = new DatePrototypeBuiltins();
   private static final boolean UTC = true;
   private static final boolean NO_UTC = false;

   protected DatePrototypeBuiltins() {
      super(JSDate.PROTOTYPE_NAME, DatePrototypeBuiltins.DatePrototype.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, DatePrototypeBuiltins.DatePrototype builtinEnum) {
      switch (builtinEnum) {
         case valueOf:
         case getTime:
            return DatePrototypeBuiltinsFactory.JSDateValueOfNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case toString:
            return DatePrototypeBuiltinsFactory.JSDateToStringNodeGen.create(context, builtin, false, args().withThis().createArgumentNodes(context));
         case toDateString:
            return DatePrototypeBuiltinsFactory.JSDateToDateStringNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case toTimeString:
            return DatePrototypeBuiltinsFactory.JSDateToTimeStringNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case toLocaleString:
            if (context.isOptionIntl402()) {
               return DatePrototypeBuiltinsFactory.JSDateToStringIntlNodeGen.create(
                  context, builtin, false, args().withThis().fixedArgs(2).createArgumentNodes(context)
               );
            }

            return DatePrototypeBuiltinsFactory.JSDateToStringNodeGen.create(context, builtin, false, args().withThis().createArgumentNodes(context));
         case toLocaleDateString:
            if (context.isOptionIntl402()) {
               return DatePrototypeBuiltinsFactory.JSDateToLocaleDateStringIntlNodeGen.create(
                  context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
               );
            }

            return DatePrototypeBuiltinsFactory.JSDateToLocaleDateStringNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case toLocaleTimeString:
            if (context.isOptionIntl402()) {
               return DatePrototypeBuiltinsFactory.JSDateToLocaleTimeStringIntlNodeGen.create(
                  context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
               );
            }

            return DatePrototypeBuiltinsFactory.JSDateToLocaleTimeStringNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case toUTCString:
            return DatePrototypeBuiltinsFactory.JSDateToStringNodeGen.create(context, builtin, true, args().withThis().createArgumentNodes(context));
         case toISOString:
            return DatePrototypeBuiltinsFactory.JSDateToISOStringNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case getFullYear:
            return DatePrototypeBuiltinsFactory.JSDateGetFullYearNodeGen.create(context, builtin, false, args().withThis().createArgumentNodes(context));
         case getYear:
            return DatePrototypeBuiltinsFactory.JSDateGetYearNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case getUTCFullYear:
            return DatePrototypeBuiltinsFactory.JSDateGetFullYearNodeGen.create(context, builtin, true, args().withThis().createArgumentNodes(context));
         case getMonth:
            return DatePrototypeBuiltinsFactory.JSDateGetMonthNodeGen.create(context, builtin, false, args().withThis().createArgumentNodes(context));
         case getUTCMonth:
            return DatePrototypeBuiltinsFactory.JSDateGetMonthNodeGen.create(context, builtin, true, args().withThis().createArgumentNodes(context));
         case getDate:
            return DatePrototypeBuiltinsFactory.JSDateGetDateNodeGen.create(context, builtin, false, args().withThis().createArgumentNodes(context));
         case getUTCDate:
            return DatePrototypeBuiltinsFactory.JSDateGetDateNodeGen.create(context, builtin, true, args().withThis().createArgumentNodes(context));
         case getDay:
            return DatePrototypeBuiltinsFactory.JSDateGetDayNodeGen.create(context, builtin, false, args().withThis().createArgumentNodes(context));
         case getUTCDay:
            return DatePrototypeBuiltinsFactory.JSDateGetDayNodeGen.create(context, builtin, true, args().withThis().createArgumentNodes(context));
         case getHours:
            return DatePrototypeBuiltinsFactory.JSDateGetHoursNodeGen.create(context, builtin, false, args().withThis().createArgumentNodes(context));
         case getUTCHours:
            return DatePrototypeBuiltinsFactory.JSDateGetHoursNodeGen.create(context, builtin, true, args().withThis().createArgumentNodes(context));
         case getMinutes:
            return DatePrototypeBuiltinsFactory.JSDateGetMinutesNodeGen.create(context, builtin, false, args().withThis().createArgumentNodes(context));
         case getUTCMinutes:
            return DatePrototypeBuiltinsFactory.JSDateGetMinutesNodeGen.create(context, builtin, true, args().withThis().createArgumentNodes(context));
         case getSeconds:
            return DatePrototypeBuiltinsFactory.JSDateGetSecondsNodeGen.create(context, builtin, false, args().withThis().createArgumentNodes(context));
         case getUTCSeconds:
            return DatePrototypeBuiltinsFactory.JSDateGetSecondsNodeGen.create(context, builtin, true, args().withThis().createArgumentNodes(context));
         case getMilliseconds:
            return DatePrototypeBuiltinsFactory.JSDateGetMillisecondsNodeGen.create(context, builtin, false, args().withThis().createArgumentNodes(context));
         case getUTCMilliseconds:
            return DatePrototypeBuiltinsFactory.JSDateGetMillisecondsNodeGen.create(context, builtin, true, args().withThis().createArgumentNodes(context));
         case setTime:
            return DatePrototypeBuiltinsFactory.JSDateSetTimeNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case setDate:
            return DatePrototypeBuiltinsFactory.JSDateSetDateNodeGen.create(
               context, builtin, false, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case setUTCDate:
            return DatePrototypeBuiltinsFactory.JSDateSetDateNodeGen.create(context, builtin, true, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case setYear:
            return DatePrototypeBuiltinsFactory.JSDateSetYearNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case setFullYear:
            return DatePrototypeBuiltinsFactory.JSDateSetFullYearNodeGen.create(
               context, builtin, false, args().withThis().varArgs().createArgumentNodes(context)
            );
         case setUTCFullYear:
            return DatePrototypeBuiltinsFactory.JSDateSetFullYearNodeGen.create(
               context, builtin, true, args().withThis().varArgs().createArgumentNodes(context)
            );
         case setMonth:
            return DatePrototypeBuiltinsFactory.JSDateSetMonthNodeGen.create(context, builtin, false, args().withThis().varArgs().createArgumentNodes(context));
         case setUTCMonth:
            return DatePrototypeBuiltinsFactory.JSDateSetMonthNodeGen.create(context, builtin, true, args().withThis().varArgs().createArgumentNodes(context));
         case setHours:
            return DatePrototypeBuiltinsFactory.JSDateSetHoursNodeGen.create(context, builtin, false, args().withThis().varArgs().createArgumentNodes(context));
         case setUTCHours:
            return DatePrototypeBuiltinsFactory.JSDateSetHoursNodeGen.create(context, builtin, true, args().withThis().varArgs().createArgumentNodes(context));
         case setMinutes:
            return DatePrototypeBuiltinsFactory.JSDateSetMinutesNodeGen.create(
               context, builtin, false, args().withThis().varArgs().createArgumentNodes(context)
            );
         case setUTCMinutes:
            return DatePrototypeBuiltinsFactory.JSDateSetMinutesNodeGen.create(context, builtin, true, args().withThis().varArgs().createArgumentNodes(context));
         case setSeconds:
            return DatePrototypeBuiltinsFactory.JSDateSetSecondsNodeGen.create(
               context, builtin, false, args().withThis().varArgs().createArgumentNodes(context)
            );
         case setUTCSeconds:
            return DatePrototypeBuiltinsFactory.JSDateSetSecondsNodeGen.create(context, builtin, true, args().withThis().varArgs().createArgumentNodes(context));
         case setMilliseconds:
            return DatePrototypeBuiltinsFactory.JSDateSetMillisecondsNodeGen.create(
               context, builtin, false, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case setUTCMilliseconds:
            return DatePrototypeBuiltinsFactory.JSDateSetMillisecondsNodeGen.create(
               context, builtin, true, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case getTimezoneOffset:
            return DatePrototypeBuiltinsFactory.JSDateGetTimezoneOffsetNodeGen.create(context, builtin, args().withThis().createArgumentNodes(context));
         case toJSON:
            return DatePrototypeBuiltinsFactory.JSDateToJSONNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         case _toPrimitive:
            return DatePrototypeBuiltinsFactory.JSDateToPrimitiveNodeGen.create(context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context));
         default:
            return null;
      }
   }

   public static enum DatePrototype implements BuiltinEnum<DatePrototypeBuiltins.DatePrototype> {
      valueOf(0),
      toString(0),
      toDateString(0),
      toTimeString(0),
      toLocaleString(0),
      toLocaleDateString(0),
      toLocaleTimeString(0),
      toUTCString(0),
      toISOString(0),
      getTime(0),
      getFullYear(0),
      getUTCFullYear(0),
      getMonth(0),
      getUTCMonth(0),
      getDate(0),
      getUTCDate(0),
      getDay(0),
      getUTCDay(0),
      getHours(0),
      getUTCHours(0),
      getMinutes(0),
      getUTCMinutes(0),
      getSeconds(0),
      getUTCSeconds(0),
      getMilliseconds(0),
      getUTCMilliseconds(0),
      setTime(1),
      setDate(1),
      setUTCDate(1),
      setFullYear(3),
      setUTCFullYear(3),
      setMonth(2),
      setUTCMonth(2),
      setHours(4),
      setUTCHours(4),
      setMinutes(3),
      setUTCMinutes(3),
      setSeconds(2),
      setUTCSeconds(2),
      setMilliseconds(1),
      setUTCMilliseconds(1),
      getTimezoneOffset(0),
      toJSON(1),
      _toPrimitive(1) {
         @Override
         public Object getKey() {
            return Symbol.SYMBOL_TO_PRIMITIVE;
         }

         @Override
         public boolean isWritable() {
            return false;
         }
      },
      getYear(0),
      setYear(1);

      private final int length;

      private DatePrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public boolean isAnnexB() {
         return EnumSet.of(getYear, setYear).contains(this);
      }

      @Override
      public int getECMAScriptVersion() {
         return this == _toPrimitive ? 6 : BuiltinEnum.super.getECMAScriptVersion();
      }
   }

   public abstract static class JSDateGetDateNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateGetDateNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         if (this.isNaN.profile(Double.isNaN(t))) {
            return Double.NaN;
         } else {
            t = this.isUTC ? t : JSDate.localTime(t, this);
            return JSDate.dateFromTime(t);
         }
      }
   }

   public abstract static class JSDateGetDayNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateGetDayNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         if (this.isNaN.profile(Double.isNaN(t))) {
            return Double.NaN;
         } else {
            t = this.isUTC ? t : JSDate.localTime(t, this);
            return JSDate.weekDay(t);
         }
      }
   }

   public abstract static class JSDateGetFullYearNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateGetFullYearNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         if (this.isNaN.profile(Double.isNaN(t))) {
            return Double.NaN;
         } else {
            t = this.isUTC ? t : JSDate.localTime(t, this);
            return JSDate.yearFromTime((long)t);
         }
      }
   }

   public abstract static class JSDateGetHoursNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateGetHoursNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         if (this.isNaN.profile(Double.isNaN(t))) {
            return Double.NaN;
         } else {
            if (!this.isUTC) {
               t = JSDate.localTime(t, this);
            }

            return JSDate.hourFromTime(t);
         }
      }
   }

   public abstract static class JSDateGetMillisecondsNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateGetMillisecondsNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         return this.isNaN.profile(Double.isNaN(t)) ? Double.NaN : JSDate.msFromTime(t);
      }
   }

   public abstract static class JSDateGetMinutesNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateGetMinutesNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         if (this.isNaN.profile(Double.isNaN(t))) {
            return Double.NaN;
         } else {
            if (!this.isUTC) {
               t = JSDate.localTime(t, this);
            }

            return JSDate.minFromTime(t);
         }
      }
   }

   public abstract static class JSDateGetMonthNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateGetMonthNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         if (Double.isNaN(t)) {
            return Double.NaN;
         } else {
            t = this.isUTC ? t : JSDate.localTime(t, this);
            return JSDate.monthFromTime(t);
         }
      }
   }

   public abstract static class JSDateGetSecondsNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateGetSecondsNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         if (this.isNaN.profile(Double.isNaN(t))) {
            return Double.NaN;
         } else {
            if (!this.isUTC) {
               t = JSDate.localTime(t, this);
            }

            return JSDate.secFromTime(t);
         }
      }
   }

   public abstract static class JSDateGetTimezoneOffsetNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateGetTimezoneOffsetNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin, false);
      }

      @Specialization
      protected double getTimezoneOffset(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         return this.isNaN.profile(Double.isNaN(t)) ? Double.NaN : (t - JSDate.localTime(t, this)) / 60000.0;
      }
   }

   public abstract static class JSDateGetYearNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateGetYearNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin, false);
      }

      @Specialization
      protected double doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         if (this.isNaN.profile(Double.isNaN(t))) {
            return Double.NaN;
         } else {
            t = JSDate.localTime(t, this);
            return JSDate.yearFromTime((long)t) - 1900.0;
         }
      }
   }

   public abstract static class JSDateOperation extends JSBuiltinNode {
      protected final boolean isUTC;
      private final ConditionProfile isDate = ConditionProfile.createBinaryProfile();
      protected final ConditionProfile isNaN = ConditionProfile.createBinaryProfile();
      @Node.Child
      private InteropLibrary interopLibrary;

      public JSDateOperation(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin);
         this.isUTC = isUTC;
      }

      protected final JSDateObject asDate(Object object) {
         if (this.isDate.profile(JSDate.isJSDate(object))) {
            return (JSDateObject)object;
         } else {
            throw Errors.createTypeErrorNotADate();
         }
      }

      protected final double asDateMillis(Object thisDate) {
         if (this.isDate.profile(JSDate.isJSDate(thisDate))) {
            return JSDate.getTimeMillisField((JSDateObject)thisDate);
         } else {
            InteropLibrary interop = this.interopLibrary;
            if (interop == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               interop = this.insert(InteropLibrary.getFactory().createDispatched(5));
               this.interopLibrary = interop;
            }

            if (interop.isInstant(thisDate)) {
               return JSDate.getDateValueFromInstant(thisDate, interop);
            } else {
               throw Errors.createTypeErrorNotADate();
            }
         }
      }

      protected static void checkTimeValid(double time) {
         if (!JSDate.isTimeValid(time)) {
            throw Errors.createRangeError("time value is not a finite number");
         }
      }

      protected JSDynamicObject createDateTimeFormat(InitializeDateTimeFormatNode initDateTimeFormatNode, Object locales, Object options) {
         JSDynamicObject dateTimeFormatObj = JSDateTimeFormat.create(this.getContext(), this.getRealm());
         initDateTimeFormatNode.executeInit(dateTimeFormatObj, locales, options);
         return dateTimeFormatObj;
      }
   }

   public abstract static class JSDateOperationWithToNumberNode extends DatePrototypeBuiltins.JSDateOperation {
      @Node.Child
      protected JSToNumberNode toNumberNode = JSToNumberNode.create();

      public JSDateOperationWithToNumberNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      protected double toDouble(Object target) {
         return JSRuntime.doubleValue(this.toNumberNode.executeNumber(target));
      }
   }

   public abstract static class JSDateSetDateNode extends DatePrototypeBuiltins.JSDateOperationWithToNumberNode {
      public JSDateSetDateNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double doOperation(Object thisDate, Object date) {
         return JSDate.setDate(this.asDate(thisDate), this.toDouble(date), this.isUTC, this);
      }
   }

   public abstract static class JSDateSetFullYearNode extends DatePrototypeBuiltins.JSDateOperationWithToNumberNode {
      public JSDateSetFullYearNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double setFullYear(Object thisDate, Object[] args) {
         JSDateObject asDate = this.asDate(thisDate);
         double iYear = this.toDouble(JSRuntime.getArgOrUndefined(args, 0));
         double iMonth = this.toDouble(JSRuntime.getArgOrUndefined(args, 1));
         double iDay = this.toDouble(JSRuntime.getArgOrUndefined(args, 2));
         return JSDate.setFullYear(asDate, iYear, iMonth, args.length >= 2, iDay, args.length >= 3, this.isUTC, this);
      }
   }

   public abstract static class JSDateSetHoursNode extends DatePrototypeBuiltins.JSDateOperationWithToNumberNode {
      public JSDateSetHoursNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double setHours(Object thisDate, Object[] args) {
         JSDateObject date = this.asDate(thisDate);
         double hour = this.toDouble(JSRuntime.getArgOrUndefined(args, 0));
         double min = this.toDouble(JSRuntime.getArgOrUndefined(args, 1));
         double sec = this.toDouble(JSRuntime.getArgOrUndefined(args, 2));
         double ms = this.toDouble(JSRuntime.getArgOrUndefined(args, 3));
         return JSDate.setHours(date, hour, min, args.length >= 2, sec, args.length >= 3, ms, args.length >= 4, this.isUTC, this);
      }
   }

   public abstract static class JSDateSetMillisecondsNode extends DatePrototypeBuiltins.JSDateOperationWithToNumberNode {
      public JSDateSetMillisecondsNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double setMilliseconds(Object thisDate, Object ms) {
         return JSDate.setMilliseconds(this.asDate(thisDate), this.toDouble(ms), this.isUTC, this);
      }
   }

   public abstract static class JSDateSetMinutesNode extends DatePrototypeBuiltins.JSDateOperationWithToNumberNode {
      public JSDateSetMinutesNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double doOperation(Object thisDate, Object[] args) {
         JSDateObject date = this.asDate(thisDate);
         double min = this.toDouble(JSRuntime.getArgOrUndefined(args, 0));
         double sec = this.toDouble(JSRuntime.getArgOrUndefined(args, 1));
         double ms = this.toDouble(JSRuntime.getArgOrUndefined(args, 2));
         return JSDate.setMinutes(date, min, sec, args.length >= 2, ms, args.length >= 3, this.isUTC, this);
      }
   }

   public abstract static class JSDateSetMonthNode extends DatePrototypeBuiltins.JSDateOperationWithToNumberNode {
      public JSDateSetMonthNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double setMonth(Object thisDate, Object[] args) {
         JSDateObject date = this.asDate(thisDate);
         double month = this.toDouble(JSRuntime.getArgOrUndefined(args, 0));
         double date2 = this.toDouble(JSRuntime.getArgOrUndefined(args, 1));
         return JSDate.setMonth(date, month, date2, args.length >= 2, this.isUTC, this);
      }
   }

   public abstract static class JSDateSetSecondsNode extends DatePrototypeBuiltins.JSDateOperationWithToNumberNode {
      public JSDateSetSecondsNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected double setSeconds(Object thisDate, Object[] args) {
         JSDateObject date = this.asDate(thisDate);
         double sec = this.toDouble(JSRuntime.getArgOrUndefined(args, 0));
         double ms = this.toDouble(JSRuntime.getArgOrUndefined(args, 1));
         return JSDate.setSeconds(date, sec, ms, args.length >= 2, this.isUTC, this);
      }
   }

   public abstract static class JSDateSetTimeNode extends DatePrototypeBuiltins.JSDateOperationWithToNumberNode {
      public JSDateSetTimeNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin, false);
      }

      @Specialization
      protected double doOperation(Object thisDate, Object time) {
         return JSDate.setTime(this.asDate(thisDate), this.toDouble(time));
      }
   }

   public abstract static class JSDateSetYearNode extends DatePrototypeBuiltins.JSDateOperationWithToNumberNode {
      public JSDateSetYearNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin, false);
      }

      @Specialization
      protected double setYear(Object thisDate, Object year) {
         return JSDate.setYear(this.asDate(thisDate), this.toDouble(year), this);
      }
   }

   public abstract static class JSDateToDateStringNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateToDateStringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin, false);
      }

      @Specialization
      protected TruffleString doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         return this.isNaN.profile(Double.isNaN(t)) ? JSDate.INVALID_DATE_STRING : JSDate.format(this.getRealm().getJSShortDateFormat(), t);
      }
   }

   public abstract static class JSDateToISOStringNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateToISOStringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin, false);
      }

      @Specialization
      protected TruffleString doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         checkTimeValid(t);
         return JSDate.toISOStringIntl(t, this.getRealm());
      }
   }

   public abstract static class JSDateToJSONNode extends JSBuiltinNode {
      @Node.Child
      private PropertyGetNode getToISOStringFnNode;
      @Node.Child
      private JSFunctionCallNode callToISOStringFnNode;
      @Node.Child
      private JSToObjectNode toObjectNode;
      @Node.Child
      private JSToPrimitiveNode toPrimitiveNode;

      public JSDateToJSONNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.toObjectNode = JSToObjectNode.createToObject(context);
         this.toPrimitiveNode = JSToPrimitiveNode.createHintNumber();
      }

      @Specialization
      protected Object toJSON(Object thisDate, Object key) {
         Object o = this.toObjectNode.execute(thisDate);
         Object tv = this.toPrimitiveNode.execute(o);
         if (JSRuntime.isNumber(tv)) {
            double d = JSRuntime.doubleValue((Number)tv);
            if (Double.isInfinite(d) || Double.isNaN(d)) {
               return Null.instance;
            }
         }

         Object toISO = this.getToISOStringFn(o);
         return this.getCallToISOStringFnNode().executeCall(JSArguments.create(o, toISO, JSArguments.EMPTY_ARGUMENTS_ARRAY));
      }

      private Object getToISOStringFn(Object obj) {
         if (this.getToISOStringFnNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getToISOStringFnNode = this.insert(PropertyGetNode.create(Strings.TO_ISO_STRING, false, this.getContext()));
         }

         return this.getToISOStringFnNode.getValue(obj);
      }

      private JSFunctionCallNode getCallToISOStringFnNode() {
         if (this.callToISOStringFnNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callToISOStringFnNode = this.insert(JSFunctionCallNode.createCall());
         }

         return this.callToISOStringFnNode;
      }
   }

   public abstract static class JSDateToLocaleDateStringIntlNode extends DatePrototypeBuiltins.JSDateOperation {
      @Node.Child
      InitializeDateTimeFormatNode initDateTimeFormatNode;

      public JSDateToLocaleDateStringIntlNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin, false);
         this.initDateTimeFormatNode = InitializeDateTimeFormatNode.createInitalizeDateTimeFormatNode(context, "date", "date");
      }

      @Specialization
      protected TruffleString doOperation(Object thisDate, Object locales, Object options) {
         double t = this.asDateMillis(thisDate);
         if (this.isNaN.profile(Double.isNaN(t))) {
            return JSDate.INVALID_DATE_STRING;
         } else {
            JSDynamicObject formatter = this.createDateTimeFormat(this.initDateTimeFormatNode, locales, options);
            return JSDateTimeFormat.format(formatter, t);
         }
      }
   }

   public abstract static class JSDateToLocaleDateStringNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateToLocaleDateStringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin, false);
      }

      @Specialization
      protected TruffleString doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         return this.isNaN.profile(Double.isNaN(t)) ? JSDate.INVALID_DATE_STRING : JSDate.format(this.getRealm().getJSShortDateLocalFormat(), t);
      }
   }

   public abstract static class JSDateToLocaleTimeStringIntlNode extends DatePrototypeBuiltins.JSDateOperation {
      @Node.Child
      InitializeDateTimeFormatNode initDateTimeFormatNode;

      public JSDateToLocaleTimeStringIntlNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin, false);
         this.initDateTimeFormatNode = InitializeDateTimeFormatNode.createInitalizeDateTimeFormatNode(context, "time", "time");
      }

      @Specialization
      protected TruffleString doOperation(Object thisDate, Object locales, Object options) {
         double t = this.asDateMillis(thisDate);
         if (this.isNaN.profile(Double.isNaN(t))) {
            return JSDate.INVALID_DATE_STRING;
         } else {
            JSDynamicObject formatter = this.createDateTimeFormat(this.initDateTimeFormatNode, locales, options);
            return JSDateTimeFormat.format(formatter, t);
         }
      }
   }

   public abstract static class JSDateToLocaleTimeStringNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateToLocaleTimeStringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin, false);
      }

      @Specialization
      protected TruffleString doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         return this.isNaN.profile(Double.isNaN(t)) ? JSDate.INVALID_DATE_STRING : JSDate.format(this.getRealm().getJSShortTimeLocalFormat(), t);
      }
   }

   public abstract static class JSDateToPrimitiveNode extends JSBuiltinNode {
      private final ConditionProfile isHintNumber = ConditionProfile.createBinaryProfile();
      private final ConditionProfile isHintStringOrDefault = ConditionProfile.createBinaryProfile();
      @Node.Child
      private IsJSObjectNode isObjectNode = IsJSObjectNode.create();
      @Node.Child
      private OrdinaryToPrimitiveNode ordinaryToPrimitiveHintNumber;
      @Node.Child
      private OrdinaryToPrimitiveNode ordinaryToPrimitiveHintString;

      public JSDateToPrimitiveNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object toPrimitive(Object obj, Object hint) {
         if (!this.isObjectNode.executeBoolean(obj)) {
            throw Errors.createTypeErrorNotAnObject(obj);
         } else if (this.isHintNumber.profile(Strings.HINT_NUMBER.equals(hint))) {
            if (this.ordinaryToPrimitiveHintNumber == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.ordinaryToPrimitiveHintNumber = this.insert(OrdinaryToPrimitiveNode.createHintNumber());
            }

            return this.ordinaryToPrimitiveHintNumber.execute(obj);
         } else if (this.isHintStringOrDefault.profile(Strings.HINT_STRING.equals(hint) || Strings.HINT_DEFAULT.equals(hint))) {
            if (this.ordinaryToPrimitiveHintString == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.ordinaryToPrimitiveHintString = this.insert(OrdinaryToPrimitiveNode.createHintString());
            }

            return this.ordinaryToPrimitiveHintString.execute(obj);
         } else {
            throw Errors.createTypeError("invalid hint");
         }
      }
   }

   public abstract static class JSDateToStringIntlNode extends DatePrototypeBuiltins.JSDateOperation {
      @Node.Child
      InitializeDateTimeFormatNode initDateTimeFormatNode;

      public JSDateToStringIntlNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
         this.initDateTimeFormatNode = InitializeDateTimeFormatNode.createInitalizeDateTimeFormatNode(context, "any", "all");
      }

      @Specialization
      protected TruffleString doOperation(Object thisDate, Object locales, Object options) {
         double t = this.asDateMillis(thisDate);
         if (this.isNaN.profile(Double.isNaN(t))) {
            return JSDate.INVALID_DATE_STRING;
         } else {
            JSDynamicObject formatter = this.createDateTimeFormat(this.initDateTimeFormatNode, locales, options);
            return JSDateTimeFormat.format(formatter, t);
         }
      }
   }

   public abstract static class JSDateToStringNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateToStringNode(JSContext context, JSBuiltin builtin, boolean isUTC) {
         super(context, builtin, isUTC);
      }

      @Specialization
      protected TruffleString doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         if (this.isUTC) {
            return this.isNaN.profile(Double.isNaN(t)) ? JSDate.INVALID_DATE_STRING : JSDate.format(this.getRealm().getJSDateUTCFormat(), t);
         } else {
            return JSDate.toString(t, this.getRealm());
         }
      }
   }

   public abstract static class JSDateToTimeStringNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateToTimeStringNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin, false);
      }

      @Specialization
      protected TruffleString doOperation(Object thisDate) {
         double t = this.asDateMillis(thisDate);
         return this.isNaN.profile(Double.isNaN(t)) ? JSDate.INVALID_DATE_STRING : JSDate.format(this.getRealm().getJSShortTimeFormat(), t);
      }
   }

   public abstract static class JSDateValueOfNode extends DatePrototypeBuiltins.JSDateOperation {
      public JSDateValueOfNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin, false);
      }

      @Specialization
      protected double doOperation(Object thisDate) {
         return this.asDateMillis(thisDate);
      }
   }
}
