package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDateTimeRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDate;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTimeObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTimeRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.List;

public abstract class ToRelativeTemporalObjectNode extends JavaScriptBaseNode {
   protected final JSContext ctx;
   @Node.Child
   private ToTemporalTimeZoneNode toTemporalTimeZoneNode;
   @Node.Child
   private PropertyGetNode getRelativeToNode;
   @Node.Child
   private PropertyGetNode getOffsetNode;
   @Node.Child
   private PropertyGetNode getTimeZoneNode;
   @Node.Child
   private GetTemporalCalendarWithISODefaultNode getTemporalCalendarWithISODefaultNode;

   protected ToRelativeTemporalObjectNode(JSContext ctx) {
      this.ctx = ctx;
      this.getRelativeToNode = PropertyGetNode.create(TemporalConstants.RELATIVE_TO, ctx);
      this.getOffsetNode = PropertyGetNode.create(TemporalConstants.OFFSET, ctx);
      this.getTimeZoneNode = PropertyGetNode.create(TemporalConstants.TIME_ZONE, ctx);
   }

   public static ToRelativeTemporalObjectNode create(JSContext ctx) {
      return ToRelativeTemporalObjectNodeGen.create(ctx);
   }

   public abstract JSDynamicObject execute(JSDynamicObject options);

   @Specialization
   protected JSDynamicObject toRelativeTemporalObject(
      JSDynamicObject options,
      @Cached BranchProfile errorBranch,
      @Cached("createBinaryProfile()") ConditionProfile valueIsObject,
      @Cached("createBinaryProfile()") ConditionProfile valueIsUndefined,
      @Cached("createBinaryProfile()") ConditionProfile valueIsPlainDate,
      @Cached("createBinaryProfile()") ConditionProfile valueIsPlainDateTime,
      @Cached("createBinaryProfile()") ConditionProfile timeZoneAvailable,
      @Cached JSToStringNode toStringNode,
      @Cached IsObjectNode isObjectNode,
      @Cached("create(ctx)") ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode,
      @Cached("create(ctx)") TemporalCalendarFieldsNode calendarFieldsNode,
      @Cached("create(ctx)") TemporalCalendarDateFromFieldsNode dateFromFieldsNode,
      @Cached("create()") TemporalGetOptionNode getOptionNode
   ) {
      Object value = this.getRelativeToNode.getValue(options);
      if (valueIsUndefined.profile(value == Undefined.instance)) {
         return Undefined.instance;
      } else {
         JSDynamicObject timeZone = Undefined.instance;
         TemporalUtil.OffsetBehaviour offsetBehaviour = TemporalUtil.OffsetBehaviour.OPTION;
         TemporalUtil.MatchBehaviour matchBehaviour = TemporalUtil.MatchBehaviour.MATCH_EXACTLY;
         JSTemporalDateTimeRecord result;
         JSDynamicObject calendar;
         Object offset;
         if (valueIsObject.profile(isObjectNode.executeBoolean(value))) {
            JSDynamicObject valueObj = (JSDynamicObject)value;
            if (valueIsPlainDate.profile(valueObj instanceof JSTemporalPlainDateObject || valueObj instanceof JSTemporalZonedDateTimeObject)) {
               return valueObj;
            }

            if (valueIsPlainDateTime.profile(valueObj instanceof JSTemporalPlainDateTimeObject)) {
               JSTemporalPlainDateTimeObject pd = (JSTemporalPlainDateTimeObject)valueObj;
               return JSTemporalPlainDate.create(this.ctx, pd.getYear(), pd.getMonth(), pd.getDay(), pd.getCalendar(), errorBranch);
            }

            calendar = this.getTemporalCalendarWithISODefault(valueObj);
            List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listDHMMMMMNSY);
            JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.ctx, valueObj, fieldNames, TemporalUtil.listEmpty);
            JSDynamicObject dateOptions = JSOrdinary.createWithNullPrototype(this.ctx);
            JSObjectUtil.putDataProperty(this.ctx, dateOptions, TemporalConstants.OVERFLOW, TemporalConstants.CONSTRAIN);
            result = TemporalUtil.interpretTemporalDateTimeFields(calendar, fields, dateOptions, getOptionNode, dateFromFieldsNode);
            offset = this.getOffsetNode.getValue(valueObj);
            Object timeZoneTemp = this.getTimeZoneNode.getValue(valueObj);
            if (timeZoneTemp != Undefined.instance) {
               timeZone = this.toTemporalTimeZone(timeZoneTemp);
            }

            if (offset == Undefined.instance) {
               offsetBehaviour = TemporalUtil.OffsetBehaviour.WALL;
            }
         } else {
            TruffleString string = toStringNode.executeString(value);
            JSTemporalZonedDateTimeRecord resultZDT = TemporalUtil.parseTemporalRelativeToString(string);
            result = resultZDT;
            calendar = toTemporalCalendarWithISODefaultNode.executeDynamicObject(resultZDT.getCalendar());
            offset = resultZDT.getTimeZoneOffsetString();
            TruffleString timeZoneName = resultZDT.getTimeZoneName();
            if (timeZoneName != null) {
               if (!TemporalUtil.isValidTimeZoneName(timeZoneName)) {
                  errorBranch.enter();
                  throw TemporalErrors.createRangeErrorInvalidTimeZoneString();
               }

               timeZoneName = TemporalUtil.canonicalizeTimeZoneName(timeZoneName);
               timeZone = TemporalUtil.createTemporalTimeZone(this.ctx, timeZoneName);
            }

            if (resultZDT.getTimeZoneZ()) {
               offsetBehaviour = TemporalUtil.OffsetBehaviour.EXACT;
            } else {
               offsetBehaviour = TemporalUtil.OffsetBehaviour.WALL;
            }

            matchBehaviour = TemporalUtil.MatchBehaviour.MATCH_MINUTES;
         }

         assert timeZone != null;

         if (timeZoneAvailable.profile(timeZone != Undefined.instance)) {
            Object offsetNs = 0;
            if (offsetBehaviour == TemporalUtil.OffsetBehaviour.OPTION) {
               offsetNs = TemporalUtil.parseTimeZoneOffsetString(toStringNode.executeString(offset));
            } else {
               offsetNs = Undefined.instance;
            }

            JSRealm realm = this.getRealm();
            BigInt epochNanoseconds = TemporalUtil.interpretISODateTimeOffset(
               this.ctx,
               realm,
               result.getYear(),
               result.getMonth(),
               result.getDay(),
               result.getHour(),
               result.getMinute(),
               result.getSecond(),
               result.getMillisecond(),
               result.getMicrosecond(),
               result.getNanosecond(),
               offsetBehaviour,
               offsetNs,
               timeZone,
               TemporalUtil.Disambiguation.COMPATIBLE,
               TemporalUtil.OffsetOption.REJECT,
               matchBehaviour
            );
            return JSTemporalZonedDateTime.create(this.ctx, realm, epochNanoseconds, timeZone, calendar);
         } else {
            return JSTemporalPlainDate.create(this.ctx, result.getYear(), result.getMonth(), result.getDay(), calendar, errorBranch);
         }
      }
   }

   private JSDynamicObject getTemporalCalendarWithISODefault(JSDynamicObject timeZoneLike) {
      if (this.getTemporalCalendarWithISODefaultNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getTemporalCalendarWithISODefaultNode = this.insert(GetTemporalCalendarWithISODefaultNode.create(this.ctx));
      }

      return this.getTemporalCalendarWithISODefaultNode.executeDynamicObject(timeZoneLike);
   }

   private JSDynamicObject toTemporalTimeZone(Object timeZone) {
      if (this.toTemporalTimeZoneNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.toTemporalTimeZoneNode = this.insert(ToTemporalTimeZoneNode.create(this.ctx));
      }

      return this.toTemporalTimeZoneNode.executeDynamicObject(timeZone);
   }
}
