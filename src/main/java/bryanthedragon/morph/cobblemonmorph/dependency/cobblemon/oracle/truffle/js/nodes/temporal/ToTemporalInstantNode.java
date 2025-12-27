package com.oracle.truffle.js.nodes.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstant;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstantObject;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTimeObject;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public abstract class ToTemporalInstantNode extends JavaScriptBaseNode {
   protected final JSContext ctx;

   protected ToTemporalInstantNode(JSContext context) {
      this.ctx = context;
   }

   public static ToTemporalInstantNode create(JSContext context) {
      return ToTemporalInstantNodeGen.create(context);
   }

   public abstract JSTemporalInstantObject execute(Object value);

   @Specialization
   public JSTemporalInstantObject toTemporalDateTime(
      Object item,
      @Cached("create()") IsObjectNode isObjectNode,
      @Cached("create()") JSToStringNode toStringNode,
      @Cached("createBinaryProfile()") ConditionProfile isObjectProfile
   ) {
      if (isObjectProfile.profile(isObjectNode.executeBoolean(item))) {
         if (TemporalUtil.isTemporalInstant(item)) {
            return (JSTemporalInstantObject)item;
         }

         if (TemporalUtil.isTemporalZonedDateTime(item)) {
            return JSTemporalInstant.create(this.ctx, this.getRealm(), ((JSTemporalZonedDateTimeObject)item).getNanoseconds());
         }
      }

      TruffleString string = toStringNode.executeString(item);
      BigInt epochNanoseconds = TemporalUtil.parseTemporalInstant(string);
      return JSTemporalInstant.create(this.ctx, this.getRealm(), epochNanoseconds);
   }
}
