package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDurationRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class ToTemporalDurationNode extends JavaScriptBaseNode {
   protected final JSContext ctx;
   private final ConditionProfile isObjectProfile = ConditionProfile.createBinaryProfile();
   private final BranchProfile errorBranch = BranchProfile.create();

   protected ToTemporalDurationNode(JSContext context) {
      this.ctx = context;
   }

   public static ToTemporalDurationNode create(JSContext context) {
      return ToTemporalDurationNodeGen.create(context);
   }

   public abstract JSDynamicObject executeDynamicObject(Object item);

   @Specialization
   protected JSDynamicObject toTemporalDuration(Object item, @Cached("create()") IsObjectNode isObjectNode, @Cached("create()") JSToStringNode toStringNode) {
      JSTemporalDurationRecord result;
      if (this.isObjectProfile.profile(isObjectNode.executeBoolean(item))) {
         JSDynamicObject itemObj = (JSDynamicObject)item;
         if (JSTemporalDuration.isJSTemporalDuration(itemObj)) {
            return itemObj;
         }

         result = JSTemporalDuration.toTemporalDurationRecord(itemObj);
      } else {
         TruffleString string = toStringNode.executeString(item);
         result = JSTemporalDuration.parseTemporalDurationString(string);
      }

      return JSTemporalDuration.createTemporalDuration(
         this.ctx,
         result.getYears(),
         result.getMonths(),
         result.getWeeks(),
         result.getDays(),
         result.getHours(),
         result.getMinutes(),
         result.getSeconds(),
         result.getMilliseconds(),
         result.getMicroseconds(),
         result.getNanoseconds(),
         this.errorBranch
      );
   }
}
