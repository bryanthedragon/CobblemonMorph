package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDateTimeRecord;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTimeRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.List;

public abstract class ToTemporalZonedDateTimeNode extends JavaScriptBaseNode {
   private final BranchProfile errorBranch = BranchProfile.create();
   private final ConditionProfile isObjectProfile = ConditionProfile.createBinaryProfile();
   private final ConditionProfile isZonedDateTimeProfile = ConditionProfile.createBinaryProfile();
   protected final JSContext ctx;

   protected ToTemporalZonedDateTimeNode(JSContext context) {
      this.ctx = context;
   }

   public static ToTemporalZonedDateTimeNode create(JSContext context) {
      return ToTemporalZonedDateTimeNodeGen.create(context);
   }

   public abstract JSDynamicObject executeDynamicObject(Object value, JSDynamicObject options);

   @Specialization
   public JSDynamicObject toTemporalZonedDateTime(
      Object item,
      JSDynamicObject options,
      @Cached("create()") IsObjectNode isObjectNode,
      @Cached("create()") JSToStringNode toStringNode,
      @Cached("create()") TemporalGetOptionNode getOptionNode,
      @Cached("create(ctx)") ToTemporalTimeZoneNode toTemporalTimeZone,
      @Cached("create(ctx)") GetTemporalCalendarWithISODefaultNode getTemporalCalendarNode,
      @Cached("create(ctx)") ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode,
      @Cached("create(ctx)") TemporalCalendarFieldsNode calendarFieldsNode,
      @Cached TruffleString.EqualNode equalNode,
      @Cached("create(ctx)") TemporalCalendarDateFromFieldsNode dateFromFieldsNode
   ) {
      assert options != null;

      TruffleString offsetString = null;
      JSDynamicObject timeZone = null;
      JSDynamicObject calendar = null;
      JSRealm realm = JSRealm.get(this);
      TemporalUtil.OffsetBehaviour offsetBehaviour = TemporalUtil.OffsetBehaviour.OPTION;
      TemporalUtil.MatchBehaviour matchBehaviour = TemporalUtil.MatchBehaviour.MATCH_EXACTLY;
      JSTemporalDateTimeRecord result;
      if (this.isObjectProfile.profile(isObjectNode.executeBoolean(item))) {
         JSDynamicObject itemObj = (JSDynamicObject)item;
         if (this.isZonedDateTimeProfile.profile(TemporalUtil.isTemporalZonedDateTime(itemObj))) {
            return itemObj;
         }

         calendar = getTemporalCalendarNode.executeDynamicObject(itemObj);
         List<TruffleString> fieldNames = calendarFieldsNode.execute(calendar, TemporalUtil.listDHMMMMMNSY);
         Boundaries.listAdd(fieldNames, TemporalConstants.TIME_ZONE);
         Boundaries.listAdd(fieldNames, TemporalConstants.OFFSET);
         JSDynamicObject fields = TemporalUtil.prepareTemporalFields(this.ctx, itemObj, fieldNames, TemporalUtil.listTimeZone);
         Object timeZoneObj = JSObject.get(fields, TemporalConstants.TIME_ZONE);
         timeZone = toTemporalTimeZone.executeDynamicObject(timeZoneObj);
         Object offsetStringObj = JSObject.get(fields, TemporalConstants.OFFSET);
         if (offsetStringObj == Undefined.instance) {
            offsetBehaviour = TemporalUtil.OffsetBehaviour.WALL;
         } else {
            offsetString = toStringNode.executeString(offsetStringObj);
         }

         result = TemporalUtil.interpretTemporalDateTimeFields(calendar, fields, options, getOptionNode, dateFromFieldsNode);
      } else {
         TemporalUtil.toTemporalOverflow(options, getOptionNode);
         TruffleString string = toStringNode.executeString(item);
         JSTemporalZonedDateTimeRecord resultZDT = TemporalUtil.parseTemporalZonedDateTimeString(string);
         result = resultZDT;
         TruffleString timeZoneName = resultZDT.getTimeZoneName();

         assert timeZoneName != null;

         if (!TemporalUtil.canParseAsTimeZoneNumericUTCOffset(timeZoneName)) {
            if (!TemporalUtil.isValidTimeZoneName(timeZoneName)) {
               this.errorBranch.enter();
               throw TemporalErrors.createRangeErrorInvalidTimeZoneString();
            }

            timeZoneName = TemporalUtil.canonicalizeTimeZoneName(timeZoneName);
         }

         offsetString = resultZDT.getTimeZoneOffsetString();
         if (resultZDT.getTimeZoneZ()) {
            offsetBehaviour = TemporalUtil.OffsetBehaviour.EXACT;
         } else {
            offsetBehaviour = TemporalUtil.OffsetBehaviour.WALL;
         }

         timeZone = TemporalUtil.createTemporalTimeZone(this.ctx, timeZoneName);
         calendar = toTemporalCalendarWithISODefaultNode.executeDynamicObject(resultZDT.getCalendar());
         matchBehaviour = TemporalUtil.MatchBehaviour.MATCH_MINUTES;
      }

      long offsetNanoseconds = 0L;
      if (offsetBehaviour == TemporalUtil.OffsetBehaviour.OPTION) {
         offsetNanoseconds = TemporalUtil.parseTimeZoneOffsetString(offsetString);
      }

      TemporalUtil.Disambiguation disambiguation = TemporalUtil.toTemporalDisambiguation(options, getOptionNode, equalNode);
      TemporalUtil.OffsetOption offset = TemporalUtil.toTemporalOffset(options, TemporalConstants.REJECT, getOptionNode, equalNode);
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
         offsetNanoseconds,
         timeZone,
         disambiguation,
         offset,
         matchBehaviour
      );
      return JSTemporalZonedDateTime.create(this.ctx, this.getRealm(), epochNanoseconds, timeZone, calendar);
   }
}
