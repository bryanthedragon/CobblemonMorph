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
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalTimeZoneRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public abstract class ToTemporalTimeZoneNode extends JavaScriptBaseNode {
   private final ConditionProfile parseNameEmpty = ConditionProfile.createBinaryProfile();
   private final ConditionProfile parseIsZ = ConditionProfile.createBinaryProfile();
   private final ConditionProfile isObjectProfile = ConditionProfile.createBinaryProfile();
   private final ConditionProfile isTimeZoneProfile = ConditionProfile.createBinaryProfile();
   private final ConditionProfile hasProperty1Profile = ConditionProfile.createBinaryProfile();
   private final ConditionProfile hasProperty2Profile = ConditionProfile.createBinaryProfile();
   private final BranchProfile errorBranch = BranchProfile.create();
   private final JSContext ctx;
   @Node.Child
   protected PropertyGetNode getTimeZoneNode;

   protected ToTemporalTimeZoneNode(JSContext context) {
      this.ctx = context;
   }

   public static ToTemporalTimeZoneNode create(JSContext context) {
      return ToTemporalTimeZoneNodeGen.create(context);
   }

   public abstract JSDynamicObject executeDynamicObject(Object temporalTimeZoneLike);

   @Specialization
   protected JSDynamicObject toTemporalTimeZone(
      Object temporalTimeZoneLikeParam, @Cached("create()") IsObjectNode isObjectNode, @Cached("create()") JSToStringNode toStringNode
   ) {
      Object temporalTimeZoneLike = temporalTimeZoneLikeParam;
      if (this.isObjectProfile.profile(isObjectNode.executeBoolean(temporalTimeZoneLikeParam))) {
         JSDynamicObject tzObj = (JSDynamicObject)temporalTimeZoneLikeParam;
         if (this.isTimeZoneProfile.profile(TemporalUtil.isTemporalZonedDateTime(tzObj))) {
            return (JSDynamicObject)this.getTimeZone(tzObj);
         }

         if (this.hasProperty1Profile.profile(!JSObject.hasProperty(tzObj, TemporalConstants.TIME_ZONE))) {
            return tzObj;
         }

         temporalTimeZoneLike = this.getTimeZone(tzObj);
         if (this.hasProperty2Profile
            .profile(
               isObjectNode.executeBoolean(temporalTimeZoneLike) && !JSObject.hasProperty((JSDynamicObject)temporalTimeZoneLike, TemporalConstants.TIME_ZONE)
            )) {
            return (JSDynamicObject)temporalTimeZoneLike;
         }
      }

      TruffleString identifier = toStringNode.executeString(temporalTimeZoneLike);
      JSTemporalTimeZoneRecord parseResult = TemporalUtil.parseTemporalTimeZoneString(identifier);
      if (this.parseNameEmpty.profile(parseResult.getName() != null)) {
         boolean canParse = TemporalUtil.canParseAsTimeZoneNumericUTCOffset(parseResult.getName());
         if (canParse) {
            if (parseResult.getOffsetString() != null
               && TemporalUtil.parseTimeZoneOffsetString(parseResult.getOffsetString()) != TemporalUtil.parseTimeZoneOffsetString(parseResult.getName())) {
               this.errorBranch.enter();
               throw TemporalErrors.createRangeErrorInvalidTimeZoneString();
            }
         } else if (!TemporalUtil.isValidTimeZoneName(parseResult.getName())) {
            this.errorBranch.enter();
            throw TemporalErrors.createRangeErrorInvalidTimeZoneString();
         }

         return TemporalUtil.createTemporalTimeZone(this.ctx, TemporalUtil.canonicalizeTimeZoneName(parseResult.getName()));
      } else {
         return this.parseIsZ.profile(parseResult.isZ())
            ? TemporalUtil.createTemporalTimeZone(this.ctx, TemporalConstants.UTC)
            : TemporalUtil.createTemporalTimeZone(this.ctx, parseResult.getOffsetString());
      }
   }

   private Object getTimeZone(JSDynamicObject obj) {
      if (this.getTimeZoneNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getTimeZoneNode = this.insert(PropertyGetNode.create(TemporalConstants.TIME_ZONE, false, this.ctx));
      }

      return this.getTimeZoneNode.getValue(obj);
   }
}
