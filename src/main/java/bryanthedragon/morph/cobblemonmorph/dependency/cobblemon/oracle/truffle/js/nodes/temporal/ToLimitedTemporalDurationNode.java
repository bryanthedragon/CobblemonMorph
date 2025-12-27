package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.List;

public abstract class ToLimitedTemporalDurationNode extends JavaScriptBaseNode {
   private final ConditionProfile isObjectProfile = ConditionProfile.createBinaryProfile();
   private final ConditionProfile hasDisallowedFields = ConditionProfile.createBinaryProfile();
   private final BranchProfile errorBranch = BranchProfile.create();

   protected ToLimitedTemporalDurationNode() {
   }

   public static ToLimitedTemporalDurationNode create() {
      return ToLimitedTemporalDurationNodeGen.create();
   }

   public abstract JSTemporalDurationRecord executeDynamicObject(Object temporalDurationLike, List<TruffleString> disallowedFields);

   @Specialization
   protected JSTemporalDurationRecord toLimitedTemporalDuration(
      Object temporalDurationLike,
      List<TruffleString> disallowedFields,
      @Cached("create()") IsObjectNode isObjectNode,
      @Cached("create()") JSToStringNode toStringNode
   ) {
      JSTemporalDurationRecord d;
      if (this.isObjectProfile.profile(!isObjectNode.executeBoolean(temporalDurationLike))) {
         TruffleString str = toStringNode.executeString(temporalDurationLike);
         d = JSTemporalDuration.parseTemporalDurationString(str);
      } else {
         d = JSTemporalDuration.toTemporalDurationRecord((JSDynamicObject)temporalDurationLike);
      }

      if (this.hasDisallowedFields.profile(disallowedFields != TemporalUtil.listEmpty)) {
         for (TemporalUtil.UnitPlural unit : TemporalUtil.DURATION_PROPERTIES) {
            double value = TemporalUtil.getPropertyFromRecord(d, unit);
            if (value != 0.0 && Boundaries.listContains(disallowedFields, unit.toTruffleString())) {
               this.errorBranch.enter();
               throw TemporalErrors.createRangeErrorDisallowedField(unit.toTruffleString());
            }
         }
      }

      return d;
   }
}
