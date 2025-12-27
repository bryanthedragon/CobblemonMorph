package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerThrowOnInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstantObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPrecisionRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.TemporalTime;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.EnumSet;

public class TemporalPlainTimePrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<TemporalPlainTimePrototypeBuiltins.TemporalPlainTimePrototype> {
   public static final JSBuiltinsContainer BUILTINS = new TemporalPlainTimePrototypeBuiltins();

   protected TemporalPlainTimePrototypeBuiltins() {
      super(JSTemporalPlainTime.PROTOTYPE_NAME, TemporalPlainTimePrototypeBuiltins.TemporalPlainTimePrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalPlainTimePrototypeBuiltins.TemporalPlainTimePrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case calendar:
         case hour:
         case minute:
         case second:
         case millisecond:
         case microsecond:
         case nanosecond:
            return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeGetterNodeGen.create(
               context, builtin, builtinEnum, args().withThis().createArgumentNodes(context)
            );
         case add:
            return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeAddNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case subtract:
            return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeSubtractNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case with:
            return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeWithNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case until:
            return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeUntilNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case since:
            return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeSinceNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case round:
            return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeRoundNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case equals:
            return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeEqualsNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case toPlainDateTime:
            return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeToPlainDateTimeNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case toZonedDateTime:
            return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeToZonedDateTimeNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case getISOFields:
            return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeGetISOFieldsNodeGen.create(
               context, builtin, args().withThis().createArgumentNodes(context)
            );
         case toString:
            return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeToStringNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case toLocaleString:
         case toJSON:
            return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeToLocaleStringNodeGen.create(
               context, builtin, args().withThis().createArgumentNodes(context)
            );
         case valueOf:
            return TemporalPlainTimePrototypeBuiltinsFactory.JSTemporalPlainTimeValueOfNodeGen.create(
               context, builtin, args().withThis().createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public abstract static class JSTemporalPlainTimeAdd extends TemporalPlainTimePrototypeBuiltins.PlainTimeOperation {
      protected JSTemporalPlainTimeAdd(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public JSDynamicObject add(Object thisObj, Object temporalDurationLike, @Cached("create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode) {
         TemporalTime temporalTime = this.requireTemporalTime(thisObj);
         return this.addDurationToOrSubtractDurationFromPlainTime(1, temporalTime, temporalDurationLike, toLimitedTemporalDurationNode);
      }
   }

   public abstract static class JSTemporalPlainTimeEquals extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainTimeEquals(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization(guards = "isJSTemporalTime(otherObj)")
      protected boolean equalsOtherObj(Object thisObj, JSDynamicObject otherObj) {
         TemporalTime temporalTime = this.requireTemporalTime(thisObj);
         return equalsIntl(temporalTime, (TemporalTime)otherObj);
      }

      @Specialization(guards = "!isJSTemporalTime(other)")
      protected boolean equalsGeneric(Object thisObj, Object other, @Cached("create(getContext())") ToTemporalTimeNode toTemporalTime) {
         TemporalTime temporalTime = this.requireTemporalTime(thisObj);
         TemporalTime otherTime = (TemporalTime)toTemporalTime.executeDynamicObject(other, null);
         return equalsIntl(temporalTime, otherTime);
      }

      private static boolean equalsIntl(TemporalTime thisTime, TemporalTime otherTime) {
         if (thisTime.getHour() != otherTime.getHour()) {
            return false;
         } else if (thisTime.getMinute() != otherTime.getMinute()) {
            return false;
         } else if (thisTime.getSecond() != otherTime.getSecond()) {
            return false;
         } else if (thisTime.getMillisecond() != otherTime.getMillisecond()) {
            return false;
         } else {
            return thisTime.getMicrosecond() != otherTime.getMicrosecond() ? false : thisTime.getNanosecond() == otherTime.getNanosecond();
         }
      }
   }

   public abstract static class JSTemporalPlainTimeGetISOFields extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainTimeGetISOFields(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object getISOFields(Object thisObj) {
         TemporalTime time = this.requireTemporalTime(thisObj);
         JSObject fields = JSOrdinary.create(this.getContext(), this.getRealm());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.CALENDAR, time.getCalendar());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.ISO_HOUR, time.getHour());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.ISO_MICROSECOND, time.getMicrosecond());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.ISO_MILLISECOND, time.getMillisecond());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.ISO_MINUTE, time.getMinute());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.ISO_NANOSECOND, time.getNanosecond());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.ISO_SECOND, time.getSecond());
         return fields;
      }
   }

   public abstract static class JSTemporalPlainTimeGetterNode extends JSBuiltinNode {
      public final TemporalPlainTimePrototypeBuiltins.TemporalPlainTimePrototype property;

      public JSTemporalPlainTimeGetterNode(JSContext context, JSBuiltin builtin, TemporalPlainTimePrototypeBuiltins.TemporalPlainTimePrototype property) {
         super(context, builtin);
         this.property = property;
      }

      @Specialization(guards = "isJSTemporalTime(thisObj)")
      protected Object timeGetter(Object thisObj) {
         TemporalTime temporalTime = (TemporalTime)thisObj;
         switch (this.property) {
            case calendar:
               return temporalTime.getCalendar();
            case hour:
               return temporalTime.getHour();
            case minute:
               return temporalTime.getMinute();
            case second:
               return temporalTime.getSecond();
            case millisecond:
               return temporalTime.getMillisecond();
            case microsecond:
               return temporalTime.getMicrosecond();
            case nanosecond:
               return temporalTime.getNanosecond();
            default:
               CompilerDirectives.transferToInterpreter();
               throw Errors.shouldNotReachHere();
         }
      }

      @Specialization(guards = "!isJSTemporalTime(thisObj)")
      protected static int error(Object thisObj) {
         throw TemporalErrors.createTypeErrorTemporalDateTimeExpected();
      }
   }

   public abstract static class JSTemporalPlainTimeRound extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainTimeRound(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected JSDynamicObject round(
         Object thisObj, Object roundToParam, @Cached("create()") JSToNumberNode toNumber, @Cached TruffleString.EqualNode equalNode
      ) {
         TemporalTime temporalTime = this.requireTemporalTime(thisObj);
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

            TemporalUtil.Unit smallestUnit = this.toSmallestTemporalUnit(roundTo, TemporalUtil.listYMWD, null, equalNode);
            if (smallestUnit == TemporalUtil.Unit.EMPTY) {
               this.errorBranch.enter();
               throw TemporalErrors.createRangeErrorSmallestUnitExpected();
            } else {
               TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(roundTo, TemporalConstants.HALF_EXPAND, equalNode);
               int maximum;
               if (smallestUnit == TemporalUtil.Unit.HOUR) {
                  maximum = 24;
               } else if (smallestUnit != TemporalUtil.Unit.MINUTE && smallestUnit != TemporalUtil.Unit.SECOND) {
                  maximum = 1000;
               } else {
                  maximum = 60;
               }

               double roundingIncrement = TemporalUtil.toTemporalRoundingIncrement(roundTo, (double)maximum, false, this.isObjectNode, toNumber);
               JSTemporalDurationRecord result = TemporalUtil.roundTime(
                  temporalTime.getHour(),
                  temporalTime.getMinute(),
                  temporalTime.getSecond(),
                  temporalTime.getMillisecond(),
                  temporalTime.getMicrosecond(),
                  temporalTime.getNanosecond(),
                  roundingIncrement,
                  smallestUnit,
                  roundingMode,
                  null
               );
               return JSTemporalPlainTime.create(
                  this.getContext(),
                  TemporalUtil.dtoi(result.getHours()),
                  TemporalUtil.dtoi(result.getMinutes()),
                  TemporalUtil.dtoi(result.getSeconds()),
                  TemporalUtil.dtoi(result.getMilliseconds()),
                  TemporalUtil.dtoi(result.getMicroseconds()),
                  TemporalUtil.dtoi(result.getNanoseconds()),
                  this.errorBranch
               );
            }
         }
      }
   }

   public abstract static class JSTemporalPlainTimeSince extends TemporalPlainTimePrototypeBuiltins.PlainTimeOperation {
      protected JSTemporalPlainTimeSince(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public JSDynamicObject since(
         Object thisObj,
         Object otherObj,
         Object optionsParam,
         @Cached("create()") JSToNumberNode toNumber,
         @Cached("createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode,
         @Cached("create(getContext())") ToTemporalTimeNode toTemporalTime,
         @Cached TruffleString.EqualNode equalNode,
         @Cached("create(getContext())") TemporalRoundDurationNode roundDurationNode
      ) {
         TemporalTime temporalTime = this.requireTemporalTime(thisObj);
         return this.differenceTemporalPlainTime(-1, temporalTime, otherObj, optionsParam, toNumber, namesNode, toTemporalTime, equalNode, roundDurationNode);
      }
   }

   public abstract static class JSTemporalPlainTimeSubtract extends TemporalPlainTimePrototypeBuiltins.PlainTimeOperation {
      protected JSTemporalPlainTimeSubtract(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public JSDynamicObject subtract(
         Object thisObj, Object temporalDurationLike, @Cached("create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode
      ) {
         TemporalTime temporalTime = this.requireTemporalTime(thisObj);
         return this.addDurationToOrSubtractDurationFromPlainTime(-1, temporalTime, temporalDurationLike, toLimitedTemporalDurationNode);
      }
   }

   public abstract static class JSTemporalPlainTimeToLocaleString extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainTimeToLocaleString(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public TruffleString toLocaleString(Object thisObj) {
         TemporalTime time = this.requireTemporalTime(thisObj);
         return JSTemporalPlainTime.temporalTimeToString(
            time.getHour(), time.getMinute(), time.getSecond(), time.getMillisecond(), time.getMicrosecond(), time.getNanosecond(), TemporalConstants.AUTO
         );
      }
   }

   public abstract static class JSTemporalPlainTimeToPlainDateTime extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainTimeToPlainDateTime(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public JSDynamicObject toPlainDateTime(Object thisObj, Object temporalDateObj, @Cached("create(getContext())") ToTemporalDateNode toTemporalDate) {
         TemporalTime time = this.requireTemporalTime(thisObj);
         JSDynamicObject temporalDate = toTemporalDate.executeDynamicObject(temporalDateObj, Undefined.instance);
         JSTemporalPlainDateObject date = (JSTemporalPlainDateObject)temporalDate;
         return JSTemporalPlainDateTime.create(
            this.getContext(),
            date.getYear(),
            date.getMonth(),
            date.getDay(),
            time.getHour(),
            time.getMinute(),
            time.getSecond(),
            time.getMillisecond(),
            time.getMicrosecond(),
            time.getNanosecond(),
            date.getCalendar(),
            this.errorBranch
         );
      }
   }

   public abstract static class JSTemporalPlainTimeToString extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainTimeToString(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected TruffleString toString(Object thisObj, Object optionsParam, @Cached JSToStringNode toStringNode, @Cached TruffleString.EqualNode equalNode) {
         TemporalTime time = this.requireTemporalTime(thisObj);
         JSDynamicObject options = this.getOptionsObject(optionsParam);
         JSTemporalPrecisionRecord precision = TemporalUtil.toSecondsStringPrecision(options, toStringNode, this.getOptionNode(), equalNode);
         TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(options, TemporalConstants.TRUNC, equalNode);
         JSTemporalDurationRecord roundResult = TemporalUtil.roundTime(
            time.getHour(),
            time.getMinute(),
            time.getSecond(),
            time.getMillisecond(),
            time.getMicrosecond(),
            time.getNanosecond(),
            precision.getIncrement(),
            precision.getUnit(),
            roundingMode,
            null
         );
         return JSTemporalPlainTime.temporalTimeToString(
            TemporalUtil.dtol(roundResult.getHours()),
            TemporalUtil.dtol(roundResult.getMinutes()),
            TemporalUtil.dtol(roundResult.getSeconds()),
            TemporalUtil.dtol(roundResult.getMilliseconds()),
            TemporalUtil.dtol(roundResult.getMicroseconds()),
            TemporalUtil.dtol(roundResult.getNanoseconds()),
            precision.getPrecision()
         );
      }
   }

   public abstract static class JSTemporalPlainTimeToZonedDateTime extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainTimeToZonedDateTime(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public JSDynamicObject toZonedDateTime(
         Object thisObj,
         Object itemParam,
         @Cached("create(getContext())") ToTemporalDateNode toTemporalDate,
         @Cached("create(getContext())") ToTemporalTimeZoneNode toTemporalTimeZone
      ) {
         TemporalTime time = this.requireTemporalTime(thisObj);
         if (!JSRuntime.isObject(itemParam)) {
            throw Errors.createTypeErrorNotAnObject(itemParam);
         } else {
            JSDynamicObject item = (JSDynamicObject)itemParam;
            Object temporalDateLike = JSObject.get(item, TemporalConstants.PLAIN_DATE);
            if (temporalDateLike == Undefined.instance) {
               this.errorBranch.enter();
               throw TemporalErrors.createTypeErrorTemporalDateExpected();
            } else {
               JSTemporalPlainDateObject date = toTemporalDate.executeDynamicObject(temporalDateLike, Undefined.instance);
               Object temporalTimeZoneLike = JSObject.get(item, TemporalConstants.TIME_ZONE);
               if (temporalTimeZoneLike != Undefined.instance && temporalTimeZoneLike != null) {
                  JSDynamicObject timeZone = toTemporalTimeZone.executeDynamicObject(temporalTimeZoneLike);
                  JSTemporalPlainDateTimeObject temporalDateTime = JSTemporalPlainDateTime.create(
                     this.getContext(),
                     date.getYear(),
                     date.getMonth(),
                     date.getDay(),
                     time.getHour(),
                     time.getMinute(),
                     time.getSecond(),
                     time.getMillisecond(),
                     time.getMicrosecond(),
                     time.getNanosecond(),
                     date.getCalendar(),
                     this.errorBranch
                  );
                  JSTemporalInstantObject instant = TemporalUtil.builtinTimeZoneGetInstantFor(
                     this.getContext(), timeZone, temporalDateTime, TemporalUtil.Disambiguation.COMPATIBLE
                  );
                  return JSTemporalZonedDateTime.create(this.getContext(), this.getRealm(), instant.getNanoseconds(), timeZone, date.getCalendar());
               } else {
                  this.errorBranch.enter();
                  throw Errors.createTypeError("TimeZone expected");
               }
            }
         }
      }
   }

   public abstract static class JSTemporalPlainTimeUntil extends TemporalPlainTimePrototypeBuiltins.PlainTimeOperation {
      protected JSTemporalPlainTimeUntil(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public JSDynamicObject until(
         Object thisObj,
         Object otherObj,
         Object optionsParam,
         @Cached("create()") JSToNumberNode toNumber,
         @Cached("createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode,
         @Cached("create(getContext())") ToTemporalTimeNode toTemporalTime,
         @Cached TruffleString.EqualNode equalNode,
         @Cached("create(getContext())") TemporalRoundDurationNode roundDurationNode
      ) {
         TemporalTime temporalTime = this.requireTemporalTime(thisObj);
         return this.differenceTemporalPlainTime(1, temporalTime, otherObj, optionsParam, toNumber, namesNode, toTemporalTime, equalNode, roundDurationNode);
      }
   }

   public abstract static class JSTemporalPlainTimeValueOf extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainTimeValueOf(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object valueOf(Object thisObj) {
         throw Errors.createTypeError("Not supported.");
      }
   }

   public abstract static class JSTemporalPlainTimeWith extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainTimeWith(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected JSDynamicObject with(
         Object thisObj,
         Object temporalTimeLike,
         Object options,
         @Cached("create()") JSToIntegerThrowOnInfinityNode toIntThrows,
         @Cached("create()") JSToIntegerAsIntNode toInt
      ) {
         TemporalTime temporalTime = this.requireTemporalTime(thisObj);
         if (!this.isObject(temporalTimeLike)) {
            this.errorBranch.enter();
            throw Errors.createTypeError("Temporal.Time like object expected.");
         } else {
            JSDynamicObject timeLikeObj = (JSDynamicObject)temporalTimeLike;
            TemporalUtil.rejectTemporalCalendarType(timeLikeObj, this.errorBranch);
            Object calendarProperty = JSObject.get(timeLikeObj, TemporalConstants.CALENDAR);
            if (calendarProperty != Undefined.instance) {
               this.errorBranch.enter();
               throw TemporalErrors.createTypeErrorUnexpectedCalendar();
            } else {
               Object timeZoneProperty = JSObject.get(timeLikeObj, TemporalConstants.TIME_ZONE);
               if (timeZoneProperty != Undefined.instance) {
                  this.errorBranch.enter();
                  throw TemporalErrors.createTypeErrorUnexpectedTimeZone();
               } else {
                  JSDynamicObject partialTime = JSTemporalPlainTime.toPartialTime(timeLikeObj, this.isObjectNode, toIntThrows, this.getContext());
                  JSDynamicObject normalizedOptions = this.getOptionsObject(options);
                  TemporalUtil.Overflow overflow = TemporalUtil.toTemporalOverflow(normalizedOptions, this.getOptionNode());
                  Object tempValue = JSObject.get(partialTime, TemporalConstants.HOUR);
                  int hour;
                  if (tempValue != Undefined.instance) {
                     hour = toInt.executeInt(tempValue);
                  } else {
                     hour = temporalTime.getHour();
                  }

                  tempValue = JSObject.get(partialTime, TemporalConstants.MINUTE);
                  int minute;
                  if (tempValue != Undefined.instance) {
                     minute = toInt.executeInt(tempValue);
                  } else {
                     minute = temporalTime.getMinute();
                  }

                  tempValue = JSObject.get(partialTime, TemporalConstants.SECOND);
                  int second;
                  if (tempValue != Undefined.instance) {
                     second = toInt.executeInt(tempValue);
                  } else {
                     second = temporalTime.getSecond();
                  }

                  tempValue = JSObject.get(partialTime, TemporalConstants.MILLISECOND);
                  int millisecond;
                  if (tempValue != Undefined.instance) {
                     millisecond = toInt.executeInt(tempValue);
                  } else {
                     millisecond = temporalTime.getMillisecond();
                  }

                  tempValue = JSObject.get(partialTime, TemporalConstants.MICROSECOND);
                  int microsecond;
                  if (tempValue != Undefined.instance) {
                     microsecond = toInt.executeInt(tempValue);
                  } else {
                     microsecond = temporalTime.getMicrosecond();
                  }

                  tempValue = JSObject.get(partialTime, TemporalConstants.NANOSECOND);
                  int nanosecond;
                  if (tempValue != Undefined.instance) {
                     nanosecond = toInt.executeInt(tempValue);
                  } else {
                     nanosecond = temporalTime.getNanosecond();
                  }

                  JSTemporalDurationRecord result = TemporalUtil.regulateTime(hour, minute, second, millisecond, microsecond, nanosecond, overflow);
                  return JSTemporalPlainTime.create(
                     this.getContext(),
                     TemporalUtil.dtoi(result.getHours()),
                     TemporalUtil.dtoi(result.getMinutes()),
                     TemporalUtil.dtoi(result.getSeconds()),
                     TemporalUtil.dtoi(result.getMilliseconds()),
                     TemporalUtil.dtoi(result.getMicroseconds()),
                     TemporalUtil.dtoi(result.getNanoseconds()),
                     this.errorBranch
                  );
               }
            }
         }
      }
   }

   public abstract static class PlainTimeOperation extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      public PlainTimeOperation(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected JSTemporalPlainTimeObject addDurationToOrSubtractDurationFromPlainTime(
         int sign, TemporalTime temporalTime, Object temporalDurationLike, ToLimitedTemporalDurationNode toLimitedTemporalDurationNode
      ) {
         JSTemporalDurationRecord duration = toLimitedTemporalDurationNode.executeDynamicObject(temporalDurationLike, TemporalUtil.listEmpty);
         JSTemporalDurationRecord result = TemporalUtil.addTimeDouble(
            temporalTime.getHour(),
            temporalTime.getMinute(),
            temporalTime.getSecond(),
            temporalTime.getMillisecond(),
            temporalTime.getMicrosecond(),
            temporalTime.getNanosecond(),
            sign * duration.getHours(),
            sign * duration.getMinutes(),
            sign * duration.getSeconds(),
            sign * duration.getMilliseconds(),
            sign * duration.getMicroseconds(),
            sign * duration.getNanoseconds()
         );

         assert TemporalUtil.isValidTime(
            TemporalUtil.dtoi(result.getHours()),
            TemporalUtil.dtoi(result.getMinutes()),
            TemporalUtil.dtoi(result.getSeconds()),
            TemporalUtil.dtoi(result.getMilliseconds()),
            TemporalUtil.dtoi(result.getMicroseconds()),
            TemporalUtil.dtoi(result.getNanoseconds())
         );

         return JSTemporalPlainTime.create(
            this.getContext(),
            TemporalUtil.dtoi(result.getHours()),
            TemporalUtil.dtoi(result.getMinutes()),
            TemporalUtil.dtoi(result.getSeconds()),
            TemporalUtil.dtoi(result.getMilliseconds()),
            TemporalUtil.dtoi(result.getMicroseconds()),
            TemporalUtil.dtoi(result.getNanoseconds()),
            this.errorBranch
         );
      }

      protected JSTemporalDurationObject differenceTemporalPlainTime(
         int sign,
         TemporalTime temporalTime,
         Object otherObj,
         Object optionsParam,
         JSToNumberNode toNumber,
         EnumerableOwnPropertyNamesNode namesNode,
         ToTemporalTimeNode toTemporalTime,
         TruffleString.EqualNode equalNode,
         TemporalRoundDurationNode roundDurationNode
      ) {
         JSTemporalPlainTimeObject other = (JSTemporalPlainTimeObject)toTemporalTime.executeDynamicObject(otherObj, null);
         JSDynamicObject options = this.getOptionsObject(optionsParam);
         TemporalUtil.Unit smallestUnit = this.toSmallestTemporalUnit(options, TemporalUtil.listYMWD, TemporalConstants.NANOSECOND, equalNode);
         TemporalUtil.Unit largestUnit = this.toLargestTemporalUnit(options, TemporalUtil.listYMWD, TemporalConstants.AUTO, TemporalUtil.Unit.HOUR, equalNode);
         TemporalUtil.validateTemporalUnitRange(largestUnit, smallestUnit);
         TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(options, TemporalConstants.TRUNC, equalNode);
         if (sign == -1) {
            roundingMode = TemporalUtil.negateTemporalRoundingMode(roundingMode);
         }

         Double maximum = TemporalUtil.maximumTemporalDurationRoundingIncrement(smallestUnit);
         long roundingIncrement = (long)TemporalUtil.toTemporalRoundingIncrement(options, maximum, false, this.isObjectNode, toNumber);
         JSTemporalDurationRecord result = TemporalUtil.differenceTime(
            temporalTime.getHour(),
            temporalTime.getMinute(),
            temporalTime.getSecond(),
            temporalTime.getMillisecond(),
            temporalTime.getMicrosecond(),
            temporalTime.getNanosecond(),
            other.getHour(),
            other.getMinute(),
            other.getSecond(),
            other.getMillisecond(),
            other.getMicrosecond(),
            other.getNanosecond()
         );
         JSTemporalDurationRecord result2 = roundDurationNode.execute(
            0.0,
            0.0,
            0.0,
            0.0,
            result.getHours(),
            result.getMinutes(),
            result.getSeconds(),
            result.getMilliseconds(),
            result.getMicroseconds(),
            result.getNanoseconds(),
            roundingIncrement,
            smallestUnit,
            roundingMode,
            Undefined.instance
         );
         JSTemporalDurationRecord result3 = TemporalUtil.balanceDuration(
            this.getContext(),
            namesNode,
            0.0,
            result2.getHours(),
            result2.getMinutes(),
            result2.getSeconds(),
            result2.getMilliseconds(),
            result2.getMicroseconds(),
            result2.getNanoseconds(),
            largestUnit
         );
         return JSTemporalDuration.createTemporalDuration(
            this.getContext(),
            0.0,
            0.0,
            0.0,
            0.0,
            sign * result3.getHours(),
            sign * result3.getMinutes(),
            sign * result3.getSeconds(),
            sign * result3.getMilliseconds(),
            sign * result3.getMicroseconds(),
            sign * result3.getNanoseconds(),
            this.errorBranch
         );
      }
   }

   public static enum TemporalPlainTimePrototype implements BuiltinEnum<TemporalPlainTimePrototypeBuiltins.TemporalPlainTimePrototype> {
      calendar(0),
      hour(0),
      minute(0),
      second(0),
      millisecond(0),
      microsecond(0),
      nanosecond(0),
      add(1),
      subtract(1),
      with(1),
      until(1),
      since(1),
      round(1),
      equals(1),
      toPlainDateTime(1),
      toZonedDateTime(1),
      getISOFields(0),
      toString(0),
      toLocaleString(0),
      toJSON(0),
      valueOf(0);

      private final int length;

      private TemporalPlainTimePrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public boolean isGetter() {
         return EnumSet.of(calendar, hour, minute, second, millisecond, microsecond, nanosecond).contains(this);
      }
   }
}
