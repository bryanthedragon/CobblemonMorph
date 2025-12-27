package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.util.List;

@GenerateUncached
public abstract class TemporalGetOptionNode extends JavaScriptBaseNode {
   protected TemporalGetOptionNode() {
   }

   public static TemporalGetOptionNode create() {
      return TemporalGetOptionNodeGen.create();
   }

   public static TemporalGetOptionNode getUncached() {
      return TemporalGetOptionNodeGen.getUncached();
   }

   public abstract Object execute(JSDynamicObject options, TruffleString property, TemporalUtil.OptionType types, List<?> values, Object fallback);

   @Specialization
   protected Object getOption(
      JSDynamicObject options,
      TruffleString property,
      TemporalUtil.OptionType types,
      List<?> values,
      Object fallback,
      @Cached BranchProfile errorBranch,
      @Cached ConditionProfile isFallbackProfile,
      @Cached JSToBooleanNode toBooleanNode,
      @Cached(uncached = "createEmptyToString()") JSToStringNode toStringNode,
      @Cached(uncached = "createEmptyToNumber()") JSToNumberNode toNumberNode
   ) {
      assert JSRuntime.isObject(options);

      Object value = JSObject.get(options, property);
      if (isFallbackProfile.profile(value == Undefined.instance)) {
         return fallback;
      } else {
         TemporalUtil.OptionType type;
         if (value instanceof Boolean && types.allowsBoolean()) {
            type = TemporalUtil.OptionType.BOOLEAN;
         } else if (Strings.isTString(value) && types.allowsString()) {
            type = TemporalUtil.OptionType.STRING;
         } else if (JSRuntime.isNumber(value) && types.allowsNumber()) {
            type = TemporalUtil.OptionType.NUMBER;
         } else {
            type = types.getLast();
         }

         if (type.allowsBoolean()) {
            value = toBooleanNode.executeBoolean(value);
         } else if (type.allowsNumber()) {
            value = toNumberNode == null ? JSRuntime.toNumber(value) : toNumberNode.executeNumber(value);
            if (JSRuntime.isNaN(value)) {
               errorBranch.enter();
               throw TemporalErrors.createRangeErrorNumberIsNaN();
            }
         } else if (type.allowsString()) {
            value = toStringNode == null ? JSRuntime.toString(value) : toStringNode.executeString(value);
         }

         if (value != Undefined.instance && values != null && !Boundaries.listContainsUnchecked(values, value)) {
            errorBranch.enter();
            throw TemporalErrors.createRangeErrorOptionsNotContained(values, value);
         } else {
            return value;
         }
      }
   }

   protected JSToStringNode createEmptyToString() {
      return null;
   }

   protected JSToNumberNode createEmptyToNumber() {
      return null;
   }
}
