package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSNumberToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarGetterNode;
import com.oracle.truffle.js.nodes.temporal.TemporalDurationAddNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.TemporalMonthDayFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.TemporalYearMonthFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalZonedDateTimeNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDateTimeRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstant;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstantObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDate;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPrecisionRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTimeObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.math.BigInteger;
import java.util.EnumSet;
import java.util.List;

public class TemporalZonedDateTimePrototypeBuiltins
   extends JSBuiltinsContainer.SwitchEnum<TemporalZonedDateTimePrototypeBuiltins.TemporalZonedDateTimePrototype> {
   public static final JSBuiltinsContainer BUILTINS = new TemporalZonedDateTimePrototypeBuiltins();

   protected TemporalZonedDateTimePrototypeBuiltins() {
      super(JSTemporalZonedDateTime.PROTOTYPE_NAME, TemporalZonedDateTimePrototypeBuiltins.TemporalZonedDateTimePrototype.class);
   }

   protected Object createNode(
      JSContext context,
      JSBuiltin builtin,
      boolean construct,
      boolean newTarget,
      TemporalZonedDateTimePrototypeBuiltins.TemporalZonedDateTimePrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case calendar:
         case timeZone:
         case year:
         case month:
         case monthCode:
         case day:
         case hour:
         case minute:
         case second:
         case millisecond:
         case microsecond:
         case nanosecond:
         case epochSeconds:
         case epochMilliseconds:
         case epochMicroseconds:
         case epochNanoseconds:
         case dayOfWeek:
         case dayOfYear:
         case weekOfYear:
         case hoursInDay:
         case daysInWeek:
         case daysInMonth:
         case daysInYear:
         case monthsInYear:
         case inLeapYear:
         case offsetNanoseconds:
         case offset:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeGetterNodeGen.create(
               context, builtin, builtinEnum, args().withThis().createArgumentNodes(context)
            );
         case toString:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToStringNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case toLocaleString:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToLocaleStringNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case toJSON:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToLocaleStringNodeGen.create(
               context, builtin, args().withThis().createArgumentNodes(context)
            );
         case valueOf:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeValueOfNodeGen.create(
               context, builtin, args().withThis().createArgumentNodes(context)
            );
         case with:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case withPlainTime:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithPlainTimeNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case withPlainDate:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithPlainDateNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case withTimeZone:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithTimeZoneNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case withCalendar:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeWithCalendarNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case add:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeAddNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case subtract:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeSubtractNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case until:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeUntilNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case since:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeSinceNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case round:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeRoundNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case equals:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeEqualsNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case startOfDay:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeStartOfDayNodeGen.create(
               context, builtin, args().withThis().fixedArgs(0).createArgumentNodes(context)
            );
         case toInstant:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToInstantNodeGen.create(
               context, builtin, args().withThis().fixedArgs(0).createArgumentNodes(context)
            );
         case toPlainDate:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToPlainDateNodeGen.create(
               context, builtin, args().withThis().fixedArgs(0).createArgumentNodes(context)
            );
         case toPlainTime:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToPlainTimeNodeGen.create(
               context, builtin, args().withThis().fixedArgs(0).createArgumentNodes(context)
            );
         case toPlainDateTime:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToPlainDateTimeNodeGen.create(
               context, builtin, args().withThis().fixedArgs(0).createArgumentNodes(context)
            );
         case toPlainYearMonth:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToPlainYearMonthNodeGen.create(
               context, builtin, args().withThis().fixedArgs(0).createArgumentNodes(context)
            );
         case toPlainMonthDay:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeToPlainMonthDayNodeGen.create(
               context, builtin, args().withThis().fixedArgs(0).createArgumentNodes(context)
            );
         case getISOFields:
            return TemporalZonedDateTimePrototypeBuiltinsFactory.JSTemporalZonedDateTimeGetISOFieldsNodeGen.create(
               context, builtin, args().withThis().fixedArgs(0).createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public abstract static class JSTemporalZonedDateTimeAdd extends TemporalZonedDateTimePrototypeBuiltins.ZonedDateTimeOperation {
      protected JSTemporalZonedDateTimeAdd(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public JSDynamicObject add(
         Object thisObj,
         Object temporalDurationLike,
         Object optionsParam,
         @Cached JSNumberToBigIntNode toBigInt,
         @Cached("create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode
      ) {
         JSTemporalZonedDateTimeObject zonedDateTime = this.requireTemporalZonedDateTime(thisObj);
         return this.addDurationToOrSubtractDurationFromZonedDateTime(
            1, zonedDateTime, temporalDurationLike, optionsParam, toBigInt, toLimitedTemporalDurationNode
         );
      }
   }

   public abstract static class JSTemporalZonedDateTimeEquals extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeEquals(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object equals(
         Object thisObj,
         Object otherParam,
         @Cached("create(getContext())") ToTemporalZonedDateTimeNode toTemporalZonedDateTime,
         @Cached JSToStringNode toStringNode
      ) {
         JSTemporalZonedDateTimeObject zdt = this.requireTemporalZonedDateTime(thisObj);
         JSTemporalZonedDateTimeObject other = (JSTemporalZonedDateTimeObject)toTemporalZonedDateTime.executeDynamicObject(otherParam, Undefined.instance);
         if (!zdt.getNanoseconds().equals(other.getNanoseconds())) {
            return false;
         } else {
            return !TemporalUtil.timeZoneEquals(zdt.getTimeZone(), other.getTimeZone(), toStringNode)
               ? false
               : TemporalUtil.calendarEquals(zdt.getCalendar(), other.getCalendar(), toStringNode);
         }
      }
   }

   public abstract static class JSTemporalZonedDateTimeGetISOFields extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeGetISOFields(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object getISOFields(Object thisObj) {
         JSTemporalZonedDateTimeObject zdt = this.requireTemporalZonedDateTime(thisObj);
         JSRealm realm = this.getRealm();
         JSObject obj = JSOrdinary.create(this.getContext(), realm);
         JSDynamicObject timeZone = zdt.getTimeZone();
         JSDynamicObject instant = JSTemporalInstant.create(this.getContext(), realm, zdt.getNanoseconds());
         JSDynamicObject calendar = zdt.getCalendar();
         JSTemporalPlainDateTimeObject dt = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.getContext(), timeZone, instant, calendar);
         TruffleString offset = TemporalUtil.builtinTimeZoneGetOffsetStringFor(timeZone, instant);
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.CALENDAR, calendar);
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_DAY, dt.getDay());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_HOUR, dt.getHour());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_MICROSECOND, dt.getMicrosecond());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_MILLISECOND, dt.getMillisecond());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_MINUTE, dt.getMinute());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_MONTH, dt.getMonth());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_NANOSECOND, dt.getNanosecond());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_SECOND, dt.getSecond());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_YEAR, dt.getYear());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.OFFSET, offset);
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.TIME_ZONE, timeZone);
         return obj;
      }
   }

   public abstract static class JSTemporalZonedDateTimeGetterNode extends JSBuiltinNode {
      public final TemporalZonedDateTimePrototypeBuiltins.TemporalZonedDateTimePrototype property;
      @Node.Child
      private TemporalCalendarGetterNode calendarGetterNode;

      public JSTemporalZonedDateTimeGetterNode(
         JSContext context, JSBuiltin builtin, TemporalZonedDateTimePrototypeBuiltins.TemporalZonedDateTimePrototype property
      ) {
         super(context, builtin);
         this.property = property;
      }

      @Specialization(guards = "isJSTemporalZonedDateTime(thisObj)")
      protected Object zonedDateTimeGetter(Object thisObj) {
         JSTemporalZonedDateTimeObject zdt = (JSTemporalZonedDateTimeObject)thisObj;
         switch (this.property) {
            case calendar:
               return zdt.getCalendar();
            case timeZone:
               return zdt.getTimeZone();
            case year:
            case month:
            case monthCode:
            case day:
            case hour:
            case minute:
            case second:
            case millisecond:
            case microsecond:
            case nanosecond:
            case dayOfWeek:
            case dayOfYear:
            case weekOfYear:
            case daysInWeek:
            case daysInMonth:
            case daysInYear:
            case monthsInYear:
            case inLeapYear:
               return this.getterCalendarDetails(zdt);
            case epochSeconds:
            case epochMilliseconds:
            case epochMicroseconds:
            case epochNanoseconds:
            case hoursInDay:
            case offsetNanoseconds:
            case offset:
               return this.zonedDateTimeGetterIntl(zdt);
            default:
               throw Errors.shouldNotReachHere();
         }
      }

      @CompilerDirectives.TruffleBoundary
      protected Object zonedDateTimeGetterIntl(JSTemporalZonedDateTimeObject zdt) {
         switch (this.property) {
            case epochSeconds:
               return getterEpoch(zdt, BigInt.valueOf(1000000000L)).bigIntegerValue().longValue();
            case epochMilliseconds:
               return getterEpoch(zdt, BigInt.valueOf(1000000L)).bigIntegerValue().longValue();
            case epochMicroseconds:
               return getterEpoch(zdt, BigInt.valueOf(1000L));
            case epochNanoseconds:
               return getterEpoch(zdt, BigInt.ONE);
            case dayOfWeek:
            case dayOfYear:
            case weekOfYear:
            case daysInWeek:
            case daysInMonth:
            case daysInYear:
            case monthsInYear:
            case inLeapYear:
            default:
               throw Errors.shouldNotReachHere();
            case hoursInDay:
               return this.getterHoursInDay(zdt);
            case offsetNanoseconds:
               return this.getterOffsetNanoseconds(zdt);
            case offset:
               return this.getterOffset(zdt);
         }
      }

      private Object getterOffset(JSTemporalZonedDateTimeObject zdt) {
         JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), this.getRealm(), zdt.getNanoseconds());
         return TemporalUtil.builtinTimeZoneGetOffsetStringFor(zdt.getTimeZone(), instant);
      }

      private Object getterOffsetNanoseconds(JSTemporalZonedDateTimeObject zdt) {
         JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), this.getRealm(), zdt.getNanoseconds());
         return TemporalUtil.getOffsetNanosecondsFor(zdt.getTimeZone(), instant);
      }

      private Object getterHoursInDay(JSTemporalZonedDateTimeObject zdt) {
         JSDynamicObject timeZone = zdt.getTimeZone();
         JSRealm realm = this.getRealm();
         JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), realm, zdt.getNanoseconds());
         JSDynamicObject isoCalendar = TemporalUtil.getISO8601Calendar(this.getContext(), realm);
         JSTemporalPlainDateTimeObject temporalDateTime = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.getContext(), timeZone, instant, isoCalendar);
         int year = temporalDateTime.getYear();
         int month = temporalDateTime.getMonth();
         int day = temporalDateTime.getDay();
         JSTemporalPlainDateTimeObject today = JSTemporalPlainDateTime.create(this.getContext(), year, month, day, 0, 0, 0, 0, 0, 0, isoCalendar);
         JSTemporalDateTimeRecord tomorrowFields = TemporalUtil.addISODate(year, month, day, 0, 0, 0, 1, TemporalUtil.Overflow.REJECT);
         JSTemporalPlainDateTimeObject tomorrow = JSTemporalPlainDateTime.create(
            this.getContext(), tomorrowFields.getYear(), tomorrowFields.getMonth(), tomorrowFields.getDay(), 0, 0, 0, 0, 0, 0, isoCalendar
         );
         JSTemporalInstantObject todayInstant = TemporalUtil.builtinTimeZoneGetInstantFor(
            this.getContext(), timeZone, today, TemporalUtil.Disambiguation.COMPATIBLE
         );
         JSTemporalInstantObject tomorrowInstant = TemporalUtil.builtinTimeZoneGetInstantFor(
            this.getContext(), timeZone, tomorrow, TemporalUtil.Disambiguation.COMPATIBLE
         );
         BigInt diffNs = tomorrowInstant.getNanoseconds().subtract(todayInstant.getNanoseconds());
         return diffNs.divide(BigInt.valueOf(36000000000000L));
      }

      private static BigInt getterEpoch(JSTemporalZonedDateTimeObject zdt, BigInt factor) {
         BigInt ns = zdt.getNanoseconds();
         return ns.divide(factor);
      }

      private Object getterCalendarDetails(JSTemporalZonedDateTimeObject zdt) {
         JSDynamicObject timeZone = zdt.getTimeZone();
         JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), this.getRealm(), zdt.getNanoseconds());
         JSDynamicObject calendar = zdt.getCalendar();
         JSTemporalPlainDateTimeObject tdt = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.getContext(), timeZone, instant, calendar);
         if (this.calendarGetterNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.calendarGetterNode = this.insert(TemporalCalendarGetterNode.create(this.getContext()));
         }

         switch (this.property) {
            case year:
               return TemporalUtil.calendarYear(this.calendarGetterNode, calendar, tdt);
            case month:
               return TemporalUtil.calendarMonth(this.calendarGetterNode, calendar, tdt);
            case monthCode:
               return TemporalUtil.calendarMonthCode(this.calendarGetterNode, calendar, tdt);
            case day:
               return TemporalUtil.calendarDay(this.calendarGetterNode, calendar, tdt);
            case hour:
               return tdt.getHour();
            case minute:
               return tdt.getMinute();
            case second:
               return tdt.getSecond();
            case millisecond:
               return tdt.getMillisecond();
            case microsecond:
               return tdt.getMicrosecond();
            case nanosecond:
               return tdt.getNanosecond();
            case epochSeconds:
            case epochMilliseconds:
            case epochMicroseconds:
            case epochNanoseconds:
            case hoursInDay:
            default:
               CompilerDirectives.transferToInterpreter();
               throw Errors.shouldNotReachHere();
            case dayOfWeek:
               return TemporalUtil.calendarDayOfWeek(this.calendarGetterNode, calendar, tdt);
            case dayOfYear:
               return TemporalUtil.calendarDayOfYear(this.calendarGetterNode, calendar, tdt);
            case weekOfYear:
               return TemporalUtil.calendarWeekOfYear(this.calendarGetterNode, calendar, tdt);
            case daysInWeek:
               return TemporalUtil.calendarWeekOfYear(this.calendarGetterNode, calendar, tdt);
            case daysInMonth:
               return TemporalUtil.calendarDaysInMonth(this.calendarGetterNode, calendar, tdt);
            case daysInYear:
               return TemporalUtil.calendarDaysInYear(this.calendarGetterNode, calendar, tdt);
            case monthsInYear:
               return TemporalUtil.calendarMonthsInYear(this.calendarGetterNode, calendar, tdt);
            case inLeapYear:
               return TemporalUtil.calendarInLeapYear(this.calendarGetterNode, calendar, tdt);
         }
      }

      @Specialization(guards = "!isJSTemporalZonedDateTime(thisObj)")
      protected static int error(Object thisObj) {
         throw TemporalErrors.createTypeErrorTemporalZonedDateTimeExpected();
      }
   }

   public abstract static class JSTemporalZonedDateTimeRound extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeRound(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public JSDynamicObject round(Object thisObj, Object roundToParam, @Cached("create()") JSToNumberNode toNumber, @Cached TruffleString.EqualNode equalNode) {
         JSTemporalZonedDateTimeObject zonedDateTime = this.requireTemporalZonedDateTime(thisObj);
         if (roundToParam == Undefined.instance) {
            this.errorBranch.enter();
            throw TemporalErrors.createTypeErrorOptionsUndefined();
         } else {
            JSDynamicObject roundTo;
            if (Strings.isTString(roundToParam)) {
               roundTo = JSOrdinary.createWithNullPrototype(this.getContext());
               JSRuntime.createDataPropertyOrThrow(roundTo, TemporalConstants.SMALLEST_UNIT, JSRuntime.toStringIsString(roundToParam));
            } else {
               roundTo = this.getOptionsObject(roundToParam);
            }

            TemporalUtil.Unit smallestUnit = this.toSmallestTemporalUnit(roundTo, TemporalUtil.listYMW, null, equalNode);
            if (smallestUnit == TemporalUtil.Unit.EMPTY) {
               this.errorBranch.enter();
               throw TemporalErrors.createRangeErrorSmallestUnitExpected();
            } else {
               TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(roundTo, TemporalConstants.HALF_EXPAND, equalNode);
               double roundingIncrement = TemporalUtil.toTemporalDateTimeRoundingIncrement(roundTo, smallestUnit, this.isObjectNode, toNumber);
               JSDynamicObject timeZone = zonedDateTime.getTimeZone();
               JSRealm realm = this.getRealm();
               JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), realm, zonedDateTime.getNanoseconds());
               JSDynamicObject calendar = zonedDateTime.getCalendar();
               JSTemporalPlainDateTimeObject tdt = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.getContext(), timeZone, instant, calendar);
               JSDynamicObject isoCalendar = TemporalUtil.getISO8601Calendar(this.getContext(), realm, this.errorBranch);
               JSTemporalPlainDateTimeObject dtStart = JSTemporalPlainDateTime.create(
                  this.getContext(), tdt.getYear(), tdt.getMonth(), tdt.getDay(), 0, 0, 0, 0, 0, 0, isoCalendar, this.errorBranch
               );
               JSTemporalInstantObject instantStart = TemporalUtil.builtinTimeZoneGetInstantFor(
                  this.getContext(), timeZone, dtStart, TemporalUtil.Disambiguation.COMPATIBLE
               );
               BigInt startNs = instantStart.getNanoseconds();
               BigInt endNs = TemporalUtil.addZonedDateTime(
                  this.getContext(), startNs, timeZone, zonedDateTime.getCalendar(), 0L, 0L, 0L, 1L, 0L, 0L, 0L, 0L, 0L, 0L
               );
               BigInt dayLengthNs = endNs.subtract(startNs);
               if (dayLengthNs.compareValueTo(0L) == 0) {
                  this.errorBranch.enter();
                  throw Errors.createRangeError("day length of zero now allowed");
               } else {
                  JSTemporalDurationRecord roundResult = TemporalUtil.roundISODateTime(
                     tdt.getYear(),
                     tdt.getMonth(),
                     tdt.getDay(),
                     tdt.getHour(),
                     tdt.getMinute(),
                     tdt.getSecond(),
                     tdt.getMillisecond(),
                     tdt.getMicrosecond(),
                     tdt.getNanosecond(),
                     roundingIncrement,
                     smallestUnit,
                     roundingMode,
                     TemporalUtil.bigIntToLong(dayLengthNs)
                  );
                  long offsetNanoseconds = TemporalUtil.getOffsetNanosecondsFor(timeZone, instant);
                  BigInt epochNanoseconds = TemporalUtil.interpretISODateTimeOffset(
                     this.getContext(),
                     realm,
                     TemporalUtil.dtoi(roundResult.getYears()),
                     TemporalUtil.dtoi(roundResult.getMonths()),
                     TemporalUtil.dtoi(roundResult.getDays()),
                     TemporalUtil.dtoi(roundResult.getHours()),
                     TemporalUtil.dtoi(roundResult.getMinutes()),
                     TemporalUtil.dtoi(roundResult.getSeconds()),
                     TemporalUtil.dtoi(roundResult.getMilliseconds()),
                     TemporalUtil.dtoi(roundResult.getMicroseconds()),
                     TemporalUtil.dtoi(roundResult.getNanoseconds()),
                     TemporalUtil.OffsetBehaviour.OPTION,
                     offsetNanoseconds,
                     timeZone,
                     TemporalUtil.Disambiguation.COMPATIBLE,
                     TemporalUtil.OffsetOption.PREFER,
                     TemporalUtil.MatchBehaviour.MATCH_EXACTLY
                  );
                  return JSTemporalZonedDateTime.create(this.getContext(), realm, epochNanoseconds, timeZone, calendar);
               }
            }
         }
      }
   }

   public abstract static class JSTemporalZonedDateTimeSince extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeSince(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object since(
         Object thisObj,
         Object otherParam,
         Object optionsParam,
         @Cached("create()") JSToNumberNode toNumber,
         @Cached("createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode,
         @Cached("create(getContext())") ToTemporalZonedDateTimeNode toTemporalZonedDateTime,
         @Cached JSToStringNode toStringNode,
         @Cached TruffleString.EqualNode equalNode,
         @Cached("create(getContext())") TemporalDurationAddNode durationAddNode,
         @Cached("create(getContext())") TemporalRoundDurationNode roundDurationNode
      ) {
         JSTemporalZonedDateTimeObject zonedDateTime = this.requireTemporalZonedDateTime(thisObj);
         JSTemporalZonedDateTimeObject other = (JSTemporalZonedDateTimeObject)toTemporalZonedDateTime.executeDynamicObject(otherParam, Undefined.instance);
         if (!TemporalUtil.calendarEquals(zonedDateTime.getCalendar(), other.getCalendar(), toStringNode)) {
            this.errorBranch.enter();
            throw TemporalErrors.createRangeErrorIdenticalCalendarExpected();
         } else {
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            TemporalUtil.Unit smallestUnit = this.toSmallestTemporalUnit(options, TemporalUtil.listEmpty, TemporalConstants.NANOSECOND, equalNode);
            TemporalUtil.Unit defaultLargestUnit = TemporalUtil.largerOfTwoTemporalUnits(TemporalUtil.Unit.HOUR, smallestUnit);
            TemporalUtil.Unit largestUnit = this.toLargestTemporalUnit(options, TemporalUtil.listEmpty, TemporalConstants.AUTO, defaultLargestUnit, equalNode);
            TemporalUtil.validateTemporalUnitRange(largestUnit, smallestUnit);
            TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(options, TemporalConstants.TRUNC, equalNode);
            roundingMode = TemporalUtil.negateTemporalRoundingMode(roundingMode);
            Double maximum = TemporalUtil.maximumTemporalDurationRoundingIncrement(smallestUnit);
            double roundingIncrement = TemporalUtil.toTemporalRoundingIncrement(options, maximum, false, this.isObjectNode, toNumber);
            if (TemporalUtil.Unit.YEAR != largestUnit
               && TemporalUtil.Unit.MONTH != largestUnit
               && TemporalUtil.Unit.WEEK != largestUnit
               && TemporalUtil.Unit.DAY != largestUnit) {
               BigInteger differenceNs = TemporalUtil.differenceInstant(
                  zonedDateTime.getNanoseconds(), other.getNanoseconds(), roundingIncrement, smallestUnit, roundingMode
               );
               JSTemporalDurationRecord balanceResult = TemporalUtil.balanceDuration(
                  this.getContext(), namesNode, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, differenceNs, largestUnit, Undefined.instance
               );
               return JSTemporalDuration.createTemporalDuration(
                  this.getContext(),
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  -balanceResult.getHours(),
                  -balanceResult.getMinutes(),
                  -balanceResult.getSeconds(),
                  -balanceResult.getMilliseconds(),
                  -balanceResult.getMicroseconds(),
                  -balanceResult.getNanoseconds(),
                  this.errorBranch
               );
            } else if (!TemporalUtil.timeZoneEquals(zonedDateTime.getTimeZone(), other.getTimeZone(), toStringNode)) {
               this.errorBranch.enter();
               throw TemporalErrors.createRangeErrorIdenticalTimeZoneExpected();
            } else {
               JSDynamicObject untilOptions = TemporalUtil.mergeLargestUnitOption(this.getContext(), namesNode, options, largestUnit);
               JSTemporalDurationRecord difference = TemporalUtil.differenceZonedDateTime(
                  this.getContext(),
                  namesNode,
                  zonedDateTime.getNanoseconds(),
                  other.getNanoseconds(),
                  zonedDateTime.getTimeZone(),
                  zonedDateTime.getCalendar(),
                  largestUnit,
                  untilOptions
               );
               JSTemporalDurationRecord roundResult = roundDurationNode.execute(
                  difference.getYears(),
                  difference.getMonths(),
                  difference.getWeeks(),
                  difference.getDays(),
                  difference.getHours(),
                  difference.getMinutes(),
                  difference.getSeconds(),
                  difference.getMilliseconds(),
                  difference.getMicroseconds(),
                  difference.getNanoseconds(),
                  (long)roundingIncrement,
                  smallestUnit,
                  roundingMode,
                  zonedDateTime
               );
               JSTemporalDurationRecord result = TemporalUtil.adjustRoundedDurationDays(
                  this.getContext(),
                  namesNode,
                  durationAddNode,
                  roundResult.getYears(),
                  roundResult.getMonths(),
                  roundResult.getWeeks(),
                  roundResult.getDays(),
                  roundResult.getHours(),
                  roundResult.getMinutes(),
                  roundResult.getSeconds(),
                  roundResult.getMilliseconds(),
                  roundResult.getMicroseconds(),
                  roundResult.getNanoseconds(),
                  (long)roundingIncrement,
                  smallestUnit,
                  roundingMode,
                  zonedDateTime
               );
               return JSTemporalDuration.createTemporalDuration(
                  this.getContext(),
                  -result.getYears(),
                  -result.getMonths(),
                  -result.getWeeks(),
                  -result.getDays(),
                  -result.getHours(),
                  -result.getMinutes(),
                  -result.getSeconds(),
                  -result.getMilliseconds(),
                  -result.getMicroseconds(),
                  -result.getNanoseconds(),
                  this.errorBranch
               );
            }
         }
      }
   }

   public abstract static class JSTemporalZonedDateTimeStartOfDay extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeStartOfDay(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object startOfDay(Object thisObj) {
         JSTemporalZonedDateTimeObject zdt = this.requireTemporalZonedDateTime(thisObj);
         JSDynamicObject timeZone = zdt.getTimeZone();
         JSDynamicObject calendar = zdt.getCalendar();
         JSRealm realm = this.getRealm();
         JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), realm, zdt.getNanoseconds());
         JSTemporalPlainDateTimeObject dt = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.getContext(), timeZone, instant, calendar);
         JSTemporalPlainDateTimeObject startDateTime = JSTemporalPlainDateTime.create(
            this.getContext(), dt.getYear(), dt.getMonth(), dt.getDay(), 0, 0, 0, 0, 0, 0, calendar, this.errorBranch
         );
         JSTemporalInstantObject startInstant = TemporalUtil.builtinTimeZoneGetInstantFor(
            this.getContext(), timeZone, startDateTime, TemporalUtil.Disambiguation.COMPATIBLE
         );
         return JSTemporalZonedDateTime.create(this.getContext(), realm, startInstant.getNanoseconds(), timeZone, calendar);
      }
   }

   public abstract static class JSTemporalZonedDateTimeSubtract extends TemporalZonedDateTimePrototypeBuiltins.ZonedDateTimeOperation {
      protected JSTemporalZonedDateTimeSubtract(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public JSDynamicObject subtract(
         Object thisObj,
         Object temporalDurationLike,
         Object optionsParam,
         @Cached JSNumberToBigIntNode toBigInt,
         @Cached("create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode
      ) {
         JSTemporalZonedDateTimeObject zonedDateTime = this.requireTemporalZonedDateTime(thisObj);
         return this.addDurationToOrSubtractDurationFromZonedDateTime(
            -1, zonedDateTime, temporalDurationLike, optionsParam, toBigInt, toLimitedTemporalDurationNode
         );
      }
   }

   public abstract static class JSTemporalZonedDateTimeToInstant extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeToInstant(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object toInstant(Object thisObj) {
         JSTemporalZonedDateTimeObject zonedDateTime = this.requireTemporalZonedDateTime(thisObj);
         return JSTemporalInstant.create(this.getContext(), this.getRealm(), zonedDateTime.getNanoseconds());
      }
   }

   public abstract static class JSTemporalZonedDateTimeToLocaleString extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeToLocaleString(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public TruffleString toLocaleString(Object thisObj) {
         JSDynamicObject zonedDateTime = this.requireTemporalZonedDateTime(thisObj);
         return TemporalUtil.temporalZonedDateTimeToString(
            this.getContext(),
            this.getRealm(),
            zonedDateTime,
            TemporalConstants.AUTO,
            TemporalUtil.ShowCalendar.AUTO,
            TemporalConstants.AUTO,
            TemporalConstants.AUTO
         );
      }
   }

   public abstract static class JSTemporalZonedDateTimeToPlainDate extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeToPlainDate(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object toPlainDate(Object thisObj) {
         JSTemporalZonedDateTimeObject zdt = this.requireTemporalZonedDateTime(thisObj);
         JSDynamicObject timeZone = zdt.getTimeZone();
         JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), this.getRealm(), zdt.getNanoseconds());
         JSDynamicObject calendar = zdt.getCalendar();
         JSTemporalPlainDateTimeObject dt = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.getContext(), timeZone, instant, calendar);
         return JSTemporalPlainDate.create(this.getContext(), dt.getYear(), dt.getMonth(), dt.getDay(), calendar, this.errorBranch);
      }
   }

   public abstract static class JSTemporalZonedDateTimeToPlainDateTime extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeToPlainDateTime(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object toPlainDateTime(Object thisObj) {
         JSTemporalZonedDateTimeObject zdt = this.requireTemporalZonedDateTime(thisObj);
         JSDynamicObject timeZone = zdt.getTimeZone();
         JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), this.getRealm(), zdt.getNanoseconds());
         JSDynamicObject calendar = zdt.getCalendar();
         return TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.getContext(), timeZone, instant, calendar);
      }
   }

   public abstract static class JSTemporalZonedDateTimeToPlainMonthDay extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeToPlainMonthDay(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object toPlainMonthDay(
         Object thisObj,
         @Cached("create(getContext())") TemporalMonthDayFromFieldsNode monthDayFromFieldsNode,
         @Cached("create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode
      ) {
         JSTemporalZonedDateTimeObject zdt = this.requireTemporalZonedDateTime(thisObj);
         JSDynamicObject timeZone = zdt.getTimeZone();
         JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), this.getRealm(), zdt.getNanoseconds());
         JSDynamicObject calendar = zdt.getCalendar();
         JSTemporalPlainDateTimeObject temporalDateTime = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.getContext(), timeZone, instant, calendar);
         List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listDMC);
         JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.getContext(), temporalDateTime, fieldNames, TemporalUtil.listEmpty);
         return monthDayFromFieldsNode.execute(calendar, fields, Undefined.instance);
      }
   }

   public abstract static class JSTemporalZonedDateTimeToPlainTime extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeToPlainTime(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object toPlainTime(Object thisObj) {
         JSTemporalZonedDateTimeObject zdt = this.requireTemporalZonedDateTime(thisObj);
         JSDynamicObject timeZone = zdt.getTimeZone();
         JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), this.getRealm(), zdt.getNanoseconds());
         JSDynamicObject calendar = zdt.getCalendar();
         JSTemporalPlainDateTimeObject dt = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.getContext(), timeZone, instant, calendar);
         return JSTemporalPlainTime.create(
            this.getContext(), dt.getHour(), dt.getMinute(), dt.getSecond(), dt.getMillisecond(), dt.getMicrosecond(), dt.getNanosecond(), this.errorBranch
         );
      }
   }

   public abstract static class JSTemporalZonedDateTimeToPlainYearMonth extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeToPlainYearMonth(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object toPlainYearMonth(
         Object thisObj,
         @Cached("create(getContext())") TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode,
         @Cached("create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode
      ) {
         JSTemporalZonedDateTimeObject zdt = this.requireTemporalZonedDateTime(thisObj);
         JSDynamicObject timeZone = zdt.getTimeZone();
         JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), this.getRealm(), zdt.getNanoseconds());
         JSDynamicObject calendar = zdt.getCalendar();
         JSTemporalPlainDateTimeObject temporalDateTime = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.getContext(), timeZone, instant, calendar);
         List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listMCY);
         JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.getContext(), temporalDateTime, fieldNames, TemporalUtil.listEmpty);
         return yearMonthFromFieldsNode.execute(calendar, fields, Undefined.instance);
      }
   }

   public abstract static class JSTemporalZonedDateTimeToString extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeToString(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected TruffleString toString(Object thisObj, Object optionsParam, @Cached JSToStringNode toStringNode, @Cached TruffleString.EqualNode equalNode) {
         JSDynamicObject zonedDateTime = this.requireTemporalZonedDateTime(thisObj);
         JSDynamicObject options = this.getOptionsObject(optionsParam);
         JSTemporalPrecisionRecord precision = TemporalUtil.toSecondsStringPrecision(options, toStringNode, this.getOptionNode(), equalNode);
         TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(options, TemporalConstants.TRUNC, equalNode);
         TemporalUtil.ShowCalendar showCalendar = TemporalUtil.toShowCalendarOption(options, this.getOptionNode(), equalNode);
         TruffleString showTimeZone = TemporalUtil.toShowTimeZoneNameOption(options, this.getOptionNode());
         TruffleString showOffset = TemporalUtil.toShowOffsetOption(options, this.getOptionNode());
         return TemporalUtil.temporalZonedDateTimeToString(
            this.getContext(),
            this.getRealm(),
            zonedDateTime,
            precision.getPrecision(),
            showCalendar,
            showTimeZone,
            showOffset,
            precision.getIncrement(),
            precision.getUnit(),
            roundingMode
         );
      }
   }

   public abstract static class JSTemporalZonedDateTimeUntil extends TemporalZonedDateTimePrototypeBuiltins.ZonedDateTimeOperation {
      protected JSTemporalZonedDateTimeUntil(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object until(
         Object thisObj,
         Object otherParam,
         Object optionsParam,
         @Cached("create()") JSToNumberNode toNumber,
         @Cached("createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode,
         @Cached("create(getContext())") ToTemporalZonedDateTimeNode toTemporalZonedDateTime,
         @Cached JSToStringNode toStringNode,
         @Cached TruffleString.EqualNode equalNode,
         @Cached("create(getContext())") TemporalDurationAddNode durationAddNode,
         @Cached("create(getContext())") TemporalRoundDurationNode roundDurationNode
      ) {
         JSTemporalZonedDateTimeObject zonedDateTime = this.requireTemporalZonedDateTime(thisObj);
         return this.differenceTemporalZonedDateTime(
            1,
            zonedDateTime,
            otherParam,
            optionsParam,
            toNumber,
            namesNode,
            toTemporalZonedDateTime,
            toStringNode,
            equalNode,
            durationAddNode,
            roundDurationNode
         );
      }
   }

   public abstract static class JSTemporalZonedDateTimeValueOf extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeValueOf(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object valueOf(Object thisObj) {
         throw Errors.createTypeError("Not supported.");
      }
   }

   public abstract static class JSTemporalZonedDateTimeWith extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeWith(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public Object with(
         Object thisObj,
         Object temporalZonedDateTimeLike,
         Object optionsParam,
         @Cached("createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode,
         @Cached TemporalGetOptionNode getOptionNode,
         @Cached TruffleString.EqualNode equalNode,
         @Cached("create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode,
         @Cached("create(getContext())") TemporalCalendarDateFromFieldsNode dateFromFieldsNode
      ) {
         JSTemporalZonedDateTimeObject zonedDateTime = this.requireTemporalZonedDateTime(thisObj);
         if (!this.isObject(temporalZonedDateTimeLike)) {
            this.errorBranch.enter();
            throw Errors.createTypeError("object expected");
         } else {
            JSDynamicObject temporalZDTLike = TemporalUtil.toJSDynamicObject(temporalZonedDateTimeLike, this.errorBranch);
            TemporalUtil.rejectTemporalCalendarType(temporalZDTLike, this.errorBranch);
            Object calendarProperty = JSObject.get(temporalZDTLike, TemporalConstants.CALENDAR);
            if (calendarProperty != Undefined.instance) {
               this.errorBranch.enter();
               throw TemporalErrors.createTypeErrorUnexpectedCalendar();
            } else {
               Object timeZoneProperty = JSObject.get(temporalZDTLike, TemporalConstants.TIME_ZONE);
               if (timeZoneProperty != Undefined.instance) {
                  this.errorBranch.enter();
                  throw TemporalErrors.createTypeErrorUnexpectedTimeZone();
               } else {
                  JSDynamicObject calendar = zonedDateTime.getCalendar();
                  List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listDHMMMMMNSY);
                  Boundaries.listAdd(fieldNames, TemporalConstants.OFFSET);
                  JSDynamicObject partialZonedDateTime = TemporalUtil.preparePartialTemporalFields(this.getContext(), temporalZDTLike, fieldNames);
                  JSDynamicObject options = this.getOptionsObject(optionsParam);
                  TemporalUtil.Disambiguation disambiguation = TemporalUtil.toTemporalDisambiguation(options, this.getOptionNode(), equalNode);
                  TemporalUtil.OffsetOption offset = TemporalUtil.toTemporalOffset(options, TemporalConstants.PREFER, this.getOptionNode(), equalNode);
                  JSDynamicObject timeZone = zonedDateTime.getTimeZone();
                  Boundaries.listAdd(fieldNames, TemporalConstants.TIME_ZONE);
                  JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.getContext(), zonedDateTime, fieldNames, TemporalUtil.listTimeZoneOffset);
                  fields = TemporalUtil.calendarMergeFields(this.getContext(), namesNode, this.errorBranch, calendar, fields, partialZonedDateTime);
                  fields = TemporalUtil.prepareTemporalFields(this.getContext(), fields, fieldNames, TemporalUtil.listTimeZone);
                  Object offsetString = JSObject.get(fields, TemporalConstants.OFFSET);
                  JSTemporalDateTimeRecord dateTimeResult = TemporalUtil.interpretTemporalDateTimeFields(
                     calendar, fields, options, getOptionNode, dateFromFieldsNode
                  );
                  long offsetNanoseconds = TemporalUtil.parseTimeZoneOffsetString((TruffleString)offsetString);
                  JSRealm realm = this.getRealm();
                  BigInt epochNanoseconds = TemporalUtil.interpretISODateTimeOffset(
                     this.getContext(),
                     realm,
                     dateTimeResult.getYear(),
                     dateTimeResult.getMonth(),
                     dateTimeResult.getDay(),
                     dateTimeResult.getHour(),
                     dateTimeResult.getMinute(),
                     dateTimeResult.getSecond(),
                     dateTimeResult.getMillisecond(),
                     dateTimeResult.getMicrosecond(),
                     dateTimeResult.getNanosecond(),
                     TemporalUtil.OffsetBehaviour.OPTION,
                     offsetNanoseconds,
                     timeZone,
                     disambiguation,
                     offset,
                     TemporalUtil.MatchBehaviour.MATCH_EXACTLY
                  );
                  return JSTemporalZonedDateTime.create(this.getContext(), realm, epochNanoseconds, timeZone, calendar);
               }
            }
         }
      }
   }

   public abstract static class JSTemporalZonedDateTimeWithCalendar extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeWithCalendar(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public JSDynamicObject withCalendar(Object thisObj, Object calendarLike, @Cached("create(getContext())") ToTemporalCalendarNode toTemporalCalendar) {
         JSTemporalZonedDateTimeObject zonedDateTime = this.requireTemporalZonedDateTime(thisObj);
         JSDynamicObject calendar = toTemporalCalendar.executeDynamicObject(calendarLike);
         return JSTemporalZonedDateTime.create(this.getContext(), this.getRealm(), zonedDateTime.getNanoseconds(), zonedDateTime.getTimeZone(), calendar);
      }
   }

   public abstract static class JSTemporalZonedDateTimeWithPlainDate extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeWithPlainDate(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public JSDynamicObject withPlainDate(
         Object thisObj, Object plainDateLike, @Cached("create(getContext())") ToTemporalDateNode toTemporalDate, @Cached JSToStringNode toStringNode
      ) {
         JSTemporalZonedDateTimeObject zonedDateTime = this.requireTemporalZonedDateTime(thisObj);
         JSTemporalPlainDateObject plainDate = toTemporalDate.executeDynamicObject(plainDateLike, Undefined.instance);
         JSDynamicObject timeZone = zonedDateTime.getTimeZone();
         JSRealm realm = this.getRealm();
         JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), realm, zonedDateTime.getNanoseconds());
         JSTemporalPlainDateTimeObject plainDateTime = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(
            this.getContext(), timeZone, instant, zonedDateTime.getCalendar()
         );
         JSDynamicObject calendar = TemporalUtil.consolidateCalendars(zonedDateTime.getCalendar(), plainDate.getCalendar(), toStringNode);
         JSTemporalPlainDateTimeObject resultPlainDateTime = JSTemporalPlainDateTime.create(
            this.getContext(),
            plainDate.getYear(),
            plainDate.getMonth(),
            plainDate.getDay(),
            plainDateTime.getHour(),
            plainDateTime.getMinute(),
            plainDateTime.getSecond(),
            plainDateTime.getMillisecond(),
            plainDateTime.getMicrosecond(),
            plainDateTime.getNanosecond(),
            calendar,
            this.errorBranch
         );
         instant = TemporalUtil.builtinTimeZoneGetInstantFor(this.getContext(), timeZone, resultPlainDateTime, TemporalUtil.Disambiguation.COMPATIBLE);
         return JSTemporalZonedDateTime.create(this.getContext(), realm, instant.getNanoseconds(), timeZone, calendar);
      }
   }

   public abstract static class JSTemporalZonedDateTimeWithPlainTime extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeWithPlainTime(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public JSDynamicObject withPlainTime(Object thisObj, Object plainTimeLike, @Cached("create(getContext())") ToTemporalTimeNode toTemporalTime) {
         JSTemporalZonedDateTimeObject zonedDateTime = this.requireTemporalZonedDateTime(thisObj);
         JSTemporalPlainTimeObject plainTime = null;
         if (plainTimeLike == Undefined.instance) {
            plainTime = JSTemporalPlainTime.create(this.getContext(), 0, 0, 0, 0, 0, 0, this.errorBranch);
         } else {
            plainTime = (JSTemporalPlainTimeObject)toTemporalTime.executeDynamicObject(plainTimeLike, null);
         }

         JSDynamicObject timeZone = zonedDateTime.getTimeZone();
         JSRealm realm = this.getRealm();
         JSTemporalInstantObject instant = JSTemporalInstant.create(this.getContext(), realm, zonedDateTime.getNanoseconds());
         JSDynamicObject calendar = zonedDateTime.getCalendar();
         JSTemporalPlainDateTimeObject plainDateTime = TemporalUtil.builtinTimeZoneGetPlainDateTimeFor(this.getContext(), timeZone, instant, calendar);
         JSTemporalPlainDateTimeObject resultPlainDateTime = JSTemporalPlainDateTime.create(
            this.getContext(),
            plainDateTime.getYear(),
            plainDateTime.getMonth(),
            plainDateTime.getDay(),
            plainTime.getHour(),
            plainTime.getMinute(),
            plainTime.getSecond(),
            plainTime.getMillisecond(),
            plainTime.getMicrosecond(),
            plainTime.getNanosecond(),
            calendar,
            this.errorBranch
         );
         instant = TemporalUtil.builtinTimeZoneGetInstantFor(this.getContext(), timeZone, resultPlainDateTime, TemporalUtil.Disambiguation.COMPATIBLE);
         return JSTemporalZonedDateTime.create(this.getContext(), realm, instant.getNanoseconds(), timeZone, calendar);
      }
   }

   public abstract static class JSTemporalZonedDateTimeWithTimeZone extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalZonedDateTimeWithTimeZone(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public JSDynamicObject withTimeZone(Object thisObj, Object timeZoneLike, @Cached("create(getContext())") ToTemporalTimeZoneNode toTemporalTimeZone) {
         JSTemporalZonedDateTimeObject zonedDateTime = this.requireTemporalZonedDateTime(thisObj);
         JSDynamicObject timeZone = toTemporalTimeZone.executeDynamicObject(timeZoneLike);
         return JSTemporalZonedDateTime.create(this.getContext(), this.getRealm(), zonedDateTime.getNanoseconds(), timeZone, zonedDateTime.getCalendar());
      }
   }

   public static enum TemporalZonedDateTimePrototype implements BuiltinEnum<TemporalZonedDateTimePrototypeBuiltins.TemporalZonedDateTimePrototype> {
      calendar(0),
      timeZone(0),
      year(0),
      month(0),
      monthCode(0),
      day(0),
      hour(0),
      minute(0),
      second(0),
      millisecond(0),
      microsecond(0),
      nanosecond(0),
      epochSeconds(0),
      epochMilliseconds(0),
      epochMicroseconds(0),
      epochNanoseconds(0),
      dayOfWeek(0),
      dayOfYear(0),
      weekOfYear(0),
      hoursInDay(0),
      daysInWeek(0),
      daysInMonth(0),
      daysInYear(0),
      monthsInYear(0),
      inLeapYear(0),
      offsetNanoseconds(0),
      offset(0),
      with(1),
      withPlainTime(0),
      withPlainDate(1),
      withTimeZone(1),
      withCalendar(1),
      add(1),
      subtract(1),
      until(1),
      since(1),
      round(1),
      equals(1),
      toString(0),
      toLocaleString(0),
      toJSON(0),
      valueOf(0),
      startOfDay(0),
      toInstant(0),
      toPlainDate(0),
      toPlainTime(0),
      toPlainDateTime(0),
      toPlainYearMonth(0),
      toPlainMonthDay(0),
      getISOFields(0);

      private final int length;

      private TemporalZonedDateTimePrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public boolean isGetter() {
         return EnumSet.range(calendar, offset).contains(this);
      }
   }

   public abstract static class ZonedDateTimeOperation extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      public ZonedDateTimeOperation(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected JSTemporalZonedDateTimeObject addDurationToOrSubtractDurationFromZonedDateTime(
         int sign,
         JSTemporalZonedDateTimeObject zonedDateTime,
         Object temporalDurationLike,
         Object optionsParam,
         JSNumberToBigIntNode toBigInt,
         ToLimitedTemporalDurationNode toLimitedTemporalDurationNode
      ) {
         JSTemporalDurationRecord duration = toLimitedTemporalDurationNode.executeDynamicObject(temporalDurationLike, TemporalUtil.listEmpty);
         JSDynamicObject options = this.getOptionsObject(optionsParam);
         JSDynamicObject timeZone = zonedDateTime.getTimeZone();
         JSDynamicObject calendar = zonedDateTime.getCalendar();
         BigInt epochNanoseconds = TemporalUtil.addZonedDateTime(
            this.getContext(),
            zonedDateTime.getNanoseconds(),
            timeZone,
            calendar,
            sign * TemporalUtil.dtol(duration.getYears()),
            sign * TemporalUtil.dtol(duration.getMonths()),
            sign * TemporalUtil.dtol(duration.getWeeks()),
            sign * TemporalUtil.dtol(duration.getDays()),
            sign * TemporalUtil.dtol(duration.getHours()),
            sign * TemporalUtil.dtol(duration.getMinutes()),
            sign * TemporalUtil.dtol(duration.getSeconds()),
            sign * TemporalUtil.dtol(duration.getMilliseconds()),
            sign * TemporalUtil.dtol(duration.getMicroseconds()),
            toBigInt.executeBigInt(sign * duration.getNanoseconds()).bigIntegerValue(),
            options
         );
         return JSTemporalZonedDateTime.create(this.getContext(), this.getRealm(), epochNanoseconds, timeZone, calendar);
      }

      protected JSTemporalDurationObject differenceTemporalZonedDateTime(
         int sign,
         JSTemporalZonedDateTimeObject zonedDateTime,
         Object otherParam,
         Object optionsParam,
         JSToNumberNode toNumber,
         EnumerableOwnPropertyNamesNode namesNode,
         ToTemporalZonedDateTimeNode toTemporalZonedDateTime,
         JSToStringNode toStringNode,
         TruffleString.EqualNode equalNode,
         TemporalDurationAddNode durationAddNode,
         TemporalRoundDurationNode roundDurationNode
      ) {
         JSTemporalZonedDateTimeObject other = (JSTemporalZonedDateTimeObject)toTemporalZonedDateTime.executeDynamicObject(otherParam, Undefined.instance);
         if (!TemporalUtil.calendarEquals(zonedDateTime.getCalendar(), other.getCalendar(), toStringNode)) {
            this.errorBranch.enter();
            throw TemporalErrors.createRangeErrorIdenticalCalendarExpected();
         } else {
            JSDynamicObject options = this.getOptionsObject(optionsParam);
            TemporalUtil.Unit smallestUnit = this.toSmallestTemporalUnit(options, TemporalUtil.listEmpty, TemporalConstants.NANOSECOND, equalNode);
            TemporalUtil.Unit defaultLargestUnit = TemporalUtil.largerOfTwoTemporalUnits(TemporalUtil.Unit.HOUR, smallestUnit);
            TemporalUtil.Unit largestUnit = this.toLargestTemporalUnit(options, TemporalUtil.listEmpty, TemporalConstants.AUTO, defaultLargestUnit, equalNode);
            TemporalUtil.validateTemporalUnitRange(largestUnit, smallestUnit);
            TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(options, TemporalConstants.TRUNC, equalNode);
            if (sign == -1) {
               roundingMode = TemporalUtil.negateTemporalRoundingMode(roundingMode);
            }

            Double maximum = TemporalUtil.maximumTemporalDurationRoundingIncrement(smallestUnit);
            double roundingIncrement = TemporalUtil.toTemporalRoundingIncrement(options, maximum, false, this.isObjectNode, toNumber);
            if (TemporalUtil.Unit.YEAR != largestUnit
               && TemporalUtil.Unit.MONTH != largestUnit
               && TemporalUtil.Unit.WEEK != largestUnit
               && TemporalUtil.Unit.DAY != largestUnit) {
               long differenceNs = TemporalUtil.bitol(
                  TemporalUtil.differenceInstant(zonedDateTime.getNanoseconds(), other.getNanoseconds(), roundingIncrement, smallestUnit, roundingMode)
               );
               JSTemporalDurationRecord balanceResult = TemporalUtil.balanceDuration(
                  this.getContext(), namesNode, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, differenceNs, largestUnit
               );
               return JSTemporalDuration.createTemporalDuration(
                  this.getContext(),
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  sign * balanceResult.getHours(),
                  sign * balanceResult.getMinutes(),
                  sign * balanceResult.getSeconds(),
                  sign * balanceResult.getMilliseconds(),
                  sign * balanceResult.getMicroseconds(),
                  sign * balanceResult.getNanoseconds(),
                  this.errorBranch
               );
            } else if (!TemporalUtil.timeZoneEquals(zonedDateTime.getTimeZone(), other.getTimeZone(), toStringNode)) {
               this.errorBranch.enter();
               throw TemporalErrors.createRangeErrorIdenticalTimeZoneExpected();
            } else {
               JSDynamicObject untilOptions = TemporalUtil.mergeLargestUnitOption(this.getContext(), namesNode, options, largestUnit);
               JSTemporalDurationRecord difference = TemporalUtil.differenceZonedDateTime(
                  this.getContext(),
                  namesNode,
                  zonedDateTime.getNanoseconds(),
                  other.getNanoseconds(),
                  zonedDateTime.getTimeZone(),
                  zonedDateTime.getCalendar(),
                  largestUnit,
                  untilOptions
               );
               JSTemporalDurationRecord roundResult = roundDurationNode.execute(
                  difference.getYears(),
                  difference.getMonths(),
                  difference.getWeeks(),
                  difference.getDays(),
                  difference.getHours(),
                  difference.getMinutes(),
                  difference.getSeconds(),
                  difference.getMilliseconds(),
                  difference.getMicroseconds(),
                  difference.getNanoseconds(),
                  (long)roundingIncrement,
                  smallestUnit,
                  roundingMode,
                  zonedDateTime
               );
               JSTemporalDurationRecord result = TemporalUtil.adjustRoundedDurationDays(
                  this.getContext(),
                  namesNode,
                  durationAddNode,
                  roundResult.getYears(),
                  roundResult.getMonths(),
                  roundResult.getWeeks(),
                  roundResult.getDays(),
                  roundResult.getHours(),
                  roundResult.getMinutes(),
                  roundResult.getSeconds(),
                  roundResult.getMilliseconds(),
                  roundResult.getMicroseconds(),
                  roundResult.getNanoseconds(),
                  (long)roundingIncrement,
                  smallestUnit,
                  roundingMode,
                  zonedDateTime
               );
               return JSTemporalDuration.createTemporalDuration(
                  this.getContext(),
                  sign * result.getYears(),
                  sign * result.getMonths(),
                  sign * result.getWeeks(),
                  sign * result.getDays(),
                  sign * result.getHours(),
                  sign * result.getMinutes(),
                  sign * result.getSeconds(),
                  sign * result.getMilliseconds(),
                  sign * result.getMicroseconds(),
                  sign * result.getNanoseconds(),
                  this.errorBranch
               );
            }
         }
      }
   }
}
