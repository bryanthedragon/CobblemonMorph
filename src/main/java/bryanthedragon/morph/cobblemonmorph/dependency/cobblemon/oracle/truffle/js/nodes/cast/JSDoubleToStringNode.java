package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;

@GenerateUncached
public abstract class JSDoubleToStringNode extends JavaScriptBaseNode {
   public static JSDoubleToStringNode create() {
      return JSDoubleToStringNodeGen.create();
   }

   public abstract TruffleString executeString(Object operand);

   @Specialization
   protected static TruffleString doInt(int i, @Cached @Cached.Shared("fromLongNode") TruffleString.FromLongNode fromLongNode) {
      return Strings.fromLong(fromLongNode, i);
   }

   @Specialization
   protected static TruffleString doLong(long i, @Cached @Cached.Shared("fromLongNode") TruffleString.FromLongNode fromLongNode) {
      return Strings.fromLong(fromLongNode, i);
   }

   @Specialization
   protected static TruffleString doDouble(
      double d,
      @Cached @Cached.Shared("fromLongNode") TruffleString.FromLongNode fromLongNode,
      @Cached ConditionProfile isInt,
      @Cached ConditionProfile isNaN,
      @Cached ConditionProfile isPositiveInfinity,
      @Cached ConditionProfile isNegativeInfinity,
      @Cached ConditionProfile isZero,
      @Cached TruffleString.FromJavaStringNode fromJavaStringNode
   ) {
      if (isZero.profile(d == 0.0)) {
         return Strings.ZERO;
      } else if (isInt.profile(JSRuntime.doubleIsRepresentableAsInt(d, true))) {
         return doInt((int)d, fromLongNode);
      } else if (isNaN.profile(Double.isNaN(d))) {
         return Strings.NAN;
      } else if (isPositiveInfinity.profile(d == Double.POSITIVE_INFINITY)) {
         return Strings.INFINITY;
      } else {
         return isNegativeInfinity.profile(d == Double.NEGATIVE_INFINITY)
            ? Strings.NEGATIVE_INFINITY
            : Strings.fromJavaString(fromJavaStringNode, JSRuntime.formatDtoA(d));
      }
   }
}
