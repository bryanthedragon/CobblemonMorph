package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerThrowOnInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarGetterNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.TemporalYearMonthFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalYearMonthNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonth;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonthObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.EnumSet;
import java.util.List;

public class TemporalPlainYearMonthPrototypeBuiltins
   extends JSBuiltinsContainer.SwitchEnum<TemporalPlainYearMonthPrototypeBuiltins.TemporalPlainYearMonthPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new TemporalPlainYearMonthPrototypeBuiltins();

   protected TemporalPlainYearMonthPrototypeBuiltins() {
      super(JSTemporalPlainYearMonth.PROTOTYPE_NAME, TemporalPlainYearMonthPrototypeBuiltins.TemporalPlainYearMonthPrototype.class);
   }

   protected Object createNode(
      JSContext context,
      JSBuiltin builtin,
      boolean construct,
      boolean newTarget,
      TemporalPlainYearMonthPrototypeBuiltins.TemporalPlainYearMonthPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case calendar:
         case year:
         case month:
         case monthCode:
         case daysInMonth:
         case daysInYear:
         case monthsInYear:
         case inLeapYear:
            return TemporalPlainYearMonthPrototypeBuiltinsFactory.JSTemporalPlainYearMonthGetterNodeGen.create(
               context, builtin, builtinEnum, args().withThis().createArgumentNodes(context)
            );
         case add:
            return TemporalPlainYearMonthPrototypeBuiltinsFactory.JSTemporalPlainYearMonthAddNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case subtract:
            return TemporalPlainYearMonthPrototypeBuiltinsFactory.JSTemporalPlainYearMonthSubtractNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case until:
            return TemporalPlainYearMonthPrototypeBuiltinsFactory.JSTemporalPlainYearMonthUntilNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case since:
            return TemporalPlainYearMonthPrototypeBuiltinsFactory.JSTemporalPlainYearMonthSinceNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case with:
            return TemporalPlainYearMonthPrototypeBuiltinsFactory.JSTemporalPlainYearMonthWithNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         case equals:
            return TemporalPlainYearMonthPrototypeBuiltinsFactory.JSTemporalPlainYearMonthEqualsNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case toPlainDate:
            return TemporalPlainYearMonthPrototypeBuiltinsFactory.JSTemporalPlainYearMonthToPlainDateNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case getISOFields:
            return TemporalPlainYearMonthPrototypeBuiltinsFactory.JSTemporalPlainYearMonthGetISOFieldsNodeGen.create(
               context, builtin, args().withThis().createArgumentNodes(context)
            );
         case toString:
            return TemporalPlainYearMonthPrototypeBuiltinsFactory.JSTemporalPlainYearMonthToStringNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case toLocaleString:
         case toJSON:
            return TemporalPlainYearMonthPrototypeBuiltinsFactory.JSTemporalPlainYearMonthToLocaleStringNodeGen.create(
               context, builtin, args().withThis().createArgumentNodes(context)
            );
         case valueOf:
            return TemporalPlainYearMonthPrototypeBuiltinsFactory.JSTemporalPlainYearMonthValueOfNodeGen.create(
               context, builtin, args().withThis().createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public abstract static class JSTemporalPlainYearMonthAddNode extends TemporalPlainYearMonthPrototypeBuiltins.PlainYearMonthOperation {
      protected JSTemporalPlainYearMonthAddNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected JSDynamicObject add(
         Object thisObj,
         Object temporalDurationLike,
         Object optParam,
         @Cached("createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode,
         @Cached("create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode,
         @Cached("create(getContext())") TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode,
         @Cached("create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode,
         @Cached("create(getContext())") TemporalCalendarGetterNode calendarGetterNode,
         @Cached JSToIntegerThrowOnInfinityNode toIntNode,
         @Cached("create(getContext())") TemporalCalendarDateFromFieldsNode dateFromFieldsNode
      ) {
         JSTemporalPlainYearMonthObject ym = this.requireTemporalYearMonth(thisObj);
         return this.addDurationToOrSubtractDurationFromPlainYearMont(
            1,
            ym,
            temporalDurationLike,
            optParam,
            namesNode,
            toLimitedTemporalDurationNode,
            yearMonthFromFieldsNode,
            calendarFieldsNode,
            calendarGetterNode,
            toIntNode,
            dateFromFieldsNode
         );
      }
   }

   public abstract static class JSTemporalPlainYearMonthEqualsNode extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainYearMonthEqualsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected boolean equals(
         Object thisObj,
         Object otherParam,
         @Cached JSToStringNode toStringNode,
         @Cached("create(getContext())") ToTemporalYearMonthNode toTemporalYearMonthNode
      ) {
         JSTemporalPlainYearMonthObject md = this.requireTemporalYearMonth(thisObj);
         JSTemporalPlainYearMonthObject other = toTemporalYearMonthNode.executeDynamicObject(otherParam, Undefined.instance);
         if (md.getMonth() != other.getMonth()) {
            return false;
         } else if (md.getDay() != other.getDay()) {
            return false;
         } else {
            return md.getYear() != other.getYear() ? false : TemporalUtil.calendarEquals(md.getCalendar(), other.getCalendar(), toStringNode);
         }
      }
   }

   public abstract static class JSTemporalPlainYearMonthGetISOFields extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainYearMonthGetISOFields(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public JSDynamicObject getISOFields(Object thisObj) {
         JSTemporalPlainYearMonthObject ym = this.requireTemporalYearMonth(thisObj);
         JSObject obj = JSOrdinary.create(this.getContext(), this.getRealm());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.CALENDAR, ym.getCalendar());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_DAY, ym.getDay());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_MONTH, ym.getMonth());
         TemporalUtil.createDataPropertyOrThrow(this.getContext(), obj, TemporalConstants.ISO_YEAR, ym.getYear());
         return obj;
      }
   }

   public abstract static class JSTemporalPlainYearMonthGetterNode extends JSBuiltinNode {
      public final TemporalPlainYearMonthPrototypeBuiltins.TemporalPlainYearMonthPrototype property;

      public JSTemporalPlainYearMonthGetterNode(
         JSContext context, JSBuiltin builtin, TemporalPlainYearMonthPrototypeBuiltins.TemporalPlainYearMonthPrototype property
      ) {
         super(context, builtin);
         this.property = property;
      }

      @Specialization(guards = "isJSTemporalYearMonth(thisObj)")
      protected Object dateGetter(Object thisObj, @Cached("create(getContext())") TemporalCalendarGetterNode calendarGetterNode) {
         JSTemporalPlainYearMonthObject temporalYM = (JSTemporalPlainYearMonthObject)thisObj;
         switch (this.property) {
            case calendar:
               return temporalYM.getCalendar();
            case year:
               return TemporalUtil.calendarYear(calendarGetterNode, temporalYM.getCalendar(), temporalYM);
            case month:
               return TemporalUtil.calendarMonth(calendarGetterNode, temporalYM.getCalendar(), temporalYM);
            case monthCode:
               return TemporalUtil.calendarMonthCode(calendarGetterNode, temporalYM.getCalendar(), temporalYM);
            case daysInMonth:
               return TemporalUtil.calendarDaysInMonth(calendarGetterNode, temporalYM.getCalendar(), temporalYM);
            case daysInYear:
               return TemporalUtil.calendarDaysInYear(calendarGetterNode, temporalYM.getCalendar(), temporalYM);
            case monthsInYear:
               return TemporalUtil.calendarMonthsInYear(calendarGetterNode, temporalYM.getCalendar(), temporalYM);
            case inLeapYear:
               return TemporalUtil.calendarInLeapYear(calendarGetterNode, temporalYM.getCalendar(), temporalYM);
            default:
               CompilerDirectives.transferToInterpreter();
               throw Errors.shouldNotReachHere();
         }
      }

      @Specialization(guards = "!isJSTemporalYearMonth(thisObj)")
      protected static int error(Object thisObj) {
         throw TemporalErrors.createTypeErrorTemporalPlainYearMonthExpected();
      }
   }

   public abstract static class JSTemporalPlainYearMonthSinceNode extends TemporalPlainYearMonthPrototypeBuiltins.PlainYearMonthOperation {
      protected JSTemporalPlainYearMonthSinceNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object since(
         Object thisObj,
         Object otherParam,
         Object optParam,
         @Cached ConditionProfile unitIsMonth,
         @Cached JSToNumberNode toNumberNode,
         @Cached("createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode,
         @Cached JSToStringNode toStringNode,
         @Cached TruffleString.EqualNode equalNode,
         @Cached("create(getContext())") TemporalRoundDurationNode roundDurationNode,
         @Cached("create(getContext())") ToTemporalYearMonthNode toTemporalYearMonthNode,
         @Cached("create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode,
         @Cached("create(getContext())") TemporalCalendarDateFromFieldsNode dateFromFieldsNode
      ) {
         JSTemporalPlainYearMonthObject ym = this.requireTemporalYearMonth(thisObj);
         return this.differenceTemporalPlainYearMonth(
            -1,
            ym,
            otherParam,
            optParam,
            unitIsMonth,
            toStringNode,
            toNumberNode,
            namesNode,
            equalNode,
            roundDurationNode,
            toTemporalYearMonthNode,
            calendarFieldsNode,
            dateFromFieldsNode
         );
      }
   }

   public abstract static class JSTemporalPlainYearMonthSubtractNode extends TemporalPlainYearMonthPrototypeBuiltins.PlainYearMonthOperation {
      protected JSTemporalPlainYearMonthSubtractNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected JSDynamicObject subtract(
         Object thisObj,
         Object temporalDurationLike,
         Object optParam,
         @Cached("createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode,
         @Cached("create()") ToLimitedTemporalDurationNode toLimitedTemporalDurationNode,
         @Cached("create(getContext())") TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode,
         @Cached("create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode,
         @Cached("create(getContext())") TemporalCalendarGetterNode calendarGetterNode,
         @Cached JSToIntegerThrowOnInfinityNode toIntNode,
         @Cached("create(getContext())") TemporalCalendarDateFromFieldsNode dateFromFieldsNode
      ) {
         JSTemporalPlainYearMonthObject ym = this.requireTemporalYearMonth(thisObj);
         return this.addDurationToOrSubtractDurationFromPlainYearMont(
            -1,
            ym,
            temporalDurationLike,
            optParam,
            namesNode,
            toLimitedTemporalDurationNode,
            yearMonthFromFieldsNode,
            calendarFieldsNode,
            calendarGetterNode,
            toIntNode,
            dateFromFieldsNode
         );
      }
   }

   public abstract static class JSTemporalPlainYearMonthToLocaleString extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainYearMonthToLocaleString(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      public TruffleString toLocaleString(Object thisObj) {
         JSTemporalPlainYearMonthObject time = this.requireTemporalYearMonth(thisObj);
         return JSTemporalPlainYearMonth.temporalYearMonthToString(time, TemporalUtil.ShowCalendar.AUTO);
      }
   }

   public abstract static class JSTemporalPlainYearMonthToPlainDateNode extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainYearMonthToPlainDateNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object toPlainDate(
         Object thisObj,
         Object item,
         @Cached("createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode,
         @Cached("create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode,
         @Cached("create(getContext())") TemporalCalendarDateFromFieldsNode dateFromFieldsNode
      ) {
         JSTemporalPlainYearMonthObject yearMonth = this.requireTemporalYearMonth(thisObj);
         if (!JSRuntime.isObject(item)) {
            this.errorBranch.enter();
            throw TemporalErrors.createTypeErrorTemporalPlainYearMonthExpected();
         } else {
            JSDynamicObject calendar = yearMonth.getCalendar();
            List<TruffleString> receiverFieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listMCY);
            JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.getContext(), yearMonth, receiverFieldNames, TemporalUtil.listEmpty);
            List<TruffleString> inputFieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listD);
            JSDynamicObject inputFields = TemporalUtil.prepareTemporalFields(
               this.getContext(), TemporalUtil.toJSDynamicObject(item, this.errorBranch), inputFieldNames, TemporalUtil.listEmpty
            );
            JSDynamicObject mergedFields = TemporalUtil.calendarMergeFields(this.getContext(), namesNode, this.errorBranch, calendar, fields, inputFields);
            List<TruffleString> mergedFieldNames = TemporalUtil.listJoinRemoveDuplicates(receiverFieldNames, inputFieldNames);
            mergedFields = TemporalUtil.prepareTemporalFields(this.getContext(), mergedFields, mergedFieldNames, TemporalUtil.listEmpty);
            JSDynamicObject options = JSOrdinary.createWithNullPrototype(this.getContext());
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), options, TemporalConstants.OVERFLOW, TemporalConstants.REJECT);
            return dateFromFieldsNode.execute(calendar, mergedFields, options);
         }
      }
   }

   public abstract static class JSTemporalPlainYearMonthToString extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainYearMonthToString(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected TruffleString toString(Object thisObj, Object optParam, @Cached TruffleString.EqualNode equalNode) {
         JSTemporalPlainYearMonthObject md = this.requireTemporalYearMonth(thisObj);
         JSDynamicObject options = this.getOptionsObject(optParam);
         TemporalUtil.ShowCalendar showCalendar = TemporalUtil.toShowCalendarOption(options, this.getOptionNode(), equalNode);
         return JSTemporalPlainYearMonth.temporalYearMonthToString(md, showCalendar);
      }
   }

   public abstract static class JSTemporalPlainYearMonthUntilNode extends TemporalPlainYearMonthPrototypeBuiltins.PlainYearMonthOperation {
      protected JSTemporalPlainYearMonthUntilNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object until(
         Object thisObj,
         Object otherParam,
         Object optParam,
         @Cached ConditionProfile unitIsMonth,
         @Cached JSToStringNode toStringNode,
         @Cached JSToBooleanNode toBooleanNode,
         @Cached JSToNumberNode toNumberNode,
         @Cached("createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode,
         @Cached TemporalGetOptionNode getOptionNode,
         @Cached TruffleString.EqualNode equalNode,
         @Cached("create(getContext())") TemporalRoundDurationNode roundDurationNode,
         @Cached("create(getContext())") ToTemporalYearMonthNode toTemporalYearMonthNode,
         @Cached("create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode,
         @Cached("create(getContext())") TemporalCalendarDateFromFieldsNode dateFromFieldsNode
      ) {
         JSTemporalPlainYearMonthObject ym = this.requireTemporalYearMonth(thisObj);
         return this.differenceTemporalPlainYearMonth(
            1,
            ym,
            otherParam,
            optParam,
            unitIsMonth,
            toStringNode,
            toNumberNode,
            namesNode,
            equalNode,
            roundDurationNode,
            toTemporalYearMonthNode,
            calendarFieldsNode,
            dateFromFieldsNode
         );
      }
   }

   public abstract static class JSTemporalPlainYearMonthValueOf extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainYearMonthValueOf(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object valueOf(Object thisObj) {
         throw Errors.createTypeError("Not supported.");
      }
   }

   public abstract static class JSTemporalPlainYearMonthWithNode extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      protected JSTemporalPlainYearMonthWithNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected JSDynamicObject with(
         Object thisObj,
         Object temporalYearMonthLike,
         Object optParam,
         @Cached("createKeys(getContext())") EnumerableOwnPropertyNamesNode namesNode,
         @Cached("create(getContext())") TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode,
         @Cached("create(getContext())") TemporalCalendarFieldsNode calendarFieldsNode
      ) {
         JSTemporalPlainYearMonthObject ym = this.requireTemporalYearMonth(thisObj);
         if (!this.isObjectNode.executeBoolean(temporalYearMonthLike)) {
            this.errorBranch.enter();
            throw Errors.createTypeError("Object expected");
         } else {
            JSDynamicObject ymLikeObj = (JSDynamicObject)temporalYearMonthLike;
            TemporalUtil.rejectTemporalCalendarType(ymLikeObj, this.errorBranch);
            Object calendarProperty = JSObject.get(ymLikeObj, TemporalConstants.CALENDAR);
            if (calendarProperty != Undefined.instance) {
               this.errorBranch.enter();
               throw TemporalErrors.createTypeErrorUnexpectedCalendar();
            } else {
               Object timezoneProperty = JSObject.get(ymLikeObj, TemporalConstants.TIME_ZONE);
               if (timezoneProperty != Undefined.instance) {
                  this.errorBranch.enter();
                  throw TemporalErrors.createTypeErrorUnexpectedTimeZone();
               } else {
                  JSDynamicObject calendar = ym.getCalendar();
                  List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listMMCY);
                  JSDynamicObject partialMonthDay = TemporalUtil.preparePartialTemporalFields(this.getContext(), ymLikeObj, fieldNames);
                  JSDynamicObject options = this.getOptionsObject(optParam);
                  JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.getContext(), ym, fieldNames, TemporalUtil.listEmpty);
                  fields = TemporalUtil.calendarMergeFields(this.getContext(), namesNode, this.errorBranch, calendar, fields, partialMonthDay);
                  fields = TemporalUtil.prepareTemporalFields(this.getContext(), fields, fieldNames, TemporalUtil.listEmpty);
                  return yearMonthFromFieldsNode.execute(calendar, fields, options);
               }
            }
         }
      }
   }

   public abstract static class PlainYearMonthOperation extends TemporalPlainDatePrototypeBuiltins.JSTemporalBuiltinOperation {
      public PlainYearMonthOperation(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      protected JSTemporalPlainYearMonthObject addDurationToOrSubtractDurationFromPlainYearMont(
         int operation,
         JSTemporalPlainYearMonthObject ym,
         Object temporalDurationLike,
         Object optParam,
         EnumerableOwnPropertyNamesNode namesNode,
         ToLimitedTemporalDurationNode toLimitedTemporalDurationNode,
         TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode,
         TemporalCalendarFieldsNode calendarFieldsNode,
         TemporalCalendarGetterNode calendarGetterNode,
         JSToIntegerThrowOnInfinityNode toIntNode,
         TemporalCalendarDateFromFieldsNode dateFromFieldsNode
      ) {
         JSTemporalDurationRecord duration = toLimitedTemporalDurationNode.executeDynamicObject(temporalDurationLike, TemporalUtil.listEmpty);
         if (operation == -1) {
            duration = TemporalUtil.createNegatedTemporalDuration(duration);
         }

         JSTemporalDurationRecord balanceResult = TemporalUtil.balanceDuration(
            this.getContext(),
            namesNode,
            duration.getDays(),
            duration.getHours(),
            duration.getMinutes(),
            duration.getSeconds(),
            duration.getMilliseconds(),
            duration.getMicroseconds(),
            duration.getNanoseconds(),
            TemporalUtil.Unit.DAY
         );
         JSDynamicObject options = this.getOptionsObject(optParam);
         JSDynamicObject calendar = ym.getCalendar();
         List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listMCY);
         JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.getContext(), ym, fieldNames, TemporalUtil.listEmpty);
         int sign = TemporalUtil.durationSign(
            duration.getYears(), duration.getMonths(), duration.getWeeks(), balanceResult.getDays(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0
         );
         int day = 0;
         if (sign < 0) {
            Object dayFromCalendar = TemporalUtil.calendarDaysInMonth(calendarGetterNode, calendar, ym);
            day = TemporalUtil.toPositiveIntegerConstrainInt(dayFromCalendar, toIntNode, this.errorBranch);
         } else {
            day = 1;
         }

         TemporalUtil.createDataPropertyOrThrow(this.getContext(), fields, TemporalConstants.DAY, day);
         JSDynamicObject date = dateFromFieldsNode.execute(calendar, fields, Undefined.instance);
         JSDynamicObject durationToAdd = JSTemporalDuration.createTemporalDuration(
            this.getContext(),
            duration.getYears(),
            duration.getMonths(),
            duration.getWeeks(),
            balanceResult.getDays(),
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            0.0,
            this.errorBranch
         );
         JSDynamicObject addedDate = TemporalUtil.calendarDateAdd(calendar, date, durationToAdd, options, Undefined.instance);
         JSDynamicObject addedDateFields = TemporalUtil.prepareTemporalFields(this.getContext(), addedDate, fieldNames, TemporalUtil.listEmpty);
         return yearMonthFromFieldsNode.execute(calendar, addedDateFields, options);
      }

      protected JSTemporalDurationObject differenceTemporalPlainYearMonth(
         int sign,
         JSTemporalPlainYearMonthObject ym,
         Object otherParam,
         Object optParam,
         ConditionProfile unitIsMonth,
         JSToStringNode toStringNode,
         JSToNumberNode toNumberNode,
         EnumerableOwnPropertyNamesNode namesNode,
         TruffleString.EqualNode equalNode,
         TemporalRoundDurationNode roundDurationNode,
         ToTemporalYearMonthNode toTemporalYearMonthNode,
         TemporalCalendarFieldsNode calendarFieldsNode,
         TemporalCalendarDateFromFieldsNode dateFromFieldsNode
      ) {
         JSTemporalPlainYearMonthObject other = toTemporalYearMonthNode.executeDynamicObject(otherParam, Undefined.instance);
         JSDynamicObject calendar = ym.getCalendar();
         if (!TemporalUtil.calendarEquals(calendar, other.getCalendar(), toStringNode)) {
            this.errorBranch.enter();
            throw TemporalErrors.createRangeErrorIdenticalCalendarExpected();
         } else {
            JSDynamicObject options = this.getOptionsObject(optParam);
            List<TruffleString> disallowedUnits = TemporalUtil.listWDHMSMMN;
            TemporalUtil.Unit smallestUnit = this.toSmallestTemporalUnit(options, disallowedUnits, TemporalConstants.MONTH, equalNode);
            TemporalUtil.Unit largestUnit = this.toLargestTemporalUnit(options, disallowedUnits, TemporalConstants.AUTO, TemporalUtil.Unit.YEAR, equalNode);
            TemporalUtil.validateTemporalUnitRange(largestUnit, smallestUnit);
            TemporalUtil.RoundingMode roundingMode = this.toTemporalRoundingMode(options, TemporalConstants.TRUNC, equalNode);
            if (sign == -1) {
               roundingMode = TemporalUtil.negateTemporalRoundingMode(roundingMode);
            }

            double roundingIncrement = TemporalUtil.toTemporalRoundingIncrement(options, null, false, this.isObjectNode, toNumberNode);
            List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listMCY);
            JSDynamicObject otherFields = TemporalUtil.prepareTemporalFields(this.getContext(), other, fieldNames, TemporalUtil.listEmpty);
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), otherFields, TemporalConstants.DAY, 1);
            JSDynamicObject otherDate = dateFromFieldsNode.execute(calendar, otherFields, Undefined.instance);
            JSDynamicObject thisFields = TemporalUtil.prepareTemporalFields(this.getContext(), ym, fieldNames, TemporalUtil.listEmpty);
            TemporalUtil.createDataPropertyOrThrow(this.getContext(), thisFields, TemporalConstants.DAY, 1);
            JSTemporalPlainDateObject thisDate = dateFromFieldsNode.execute(calendar, thisFields, Undefined.instance);
            JSDynamicObject untilOptions = TemporalUtil.mergeLargestUnitOption(this.getContext(), namesNode, options, largestUnit);
            JSTemporalDurationObject result = TemporalUtil.calendarDateUntil(calendar, thisDate, otherDate, untilOptions, Undefined.instance);
            if (unitIsMonth.profile(TemporalUtil.Unit.MONTH == smallestUnit && roundingIncrement == 1.0)) {
               return JSTemporalDuration.createTemporalDuration(
                  this.getContext(), sign * result.getYears(), sign * result.getMonths(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch
               );
            } else {
               JSTemporalDurationRecord result2 = roundDurationNode.execute(
                  result.getYears(), result.getMonths(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, (long)roundingIncrement, smallestUnit, roundingMode, thisDate
               );
               return JSTemporalDuration.createTemporalDuration(
                  this.getContext(), sign * result2.getYears(), sign * result2.getMonths(), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, this.errorBranch
               );
            }
         }
      }
   }

   public static enum TemporalPlainYearMonthPrototype implements BuiltinEnum<TemporalPlainYearMonthPrototypeBuiltins.TemporalPlainYearMonthPrototype> {
      calendar(0),
      year(0),
      month(0),
      monthCode(0),
      daysInMonth(0),
      daysInYear(0),
      monthsInYear(0),
      inLeapYear(0),
      with(1),
      add(1),
      subtract(1),
      until(1),
      since(1),
      equals(1),
      toString(0),
      toLocaleString(0),
      toJSON(0),
      valueOf(0),
      toPlainDate(1),
      getISOFields(0);

      private final int length;

      private TemporalPlainYearMonthPrototype(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }

      @Override
      public boolean isGetter() {
         return EnumSet.of(calendar, year, month, monthCode, daysInMonth, daysInYear, monthsInYear, inLeapYear).contains(this);
      }
   }
}
