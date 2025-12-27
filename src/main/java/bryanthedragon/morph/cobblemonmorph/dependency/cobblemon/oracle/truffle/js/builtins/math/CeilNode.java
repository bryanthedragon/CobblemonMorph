package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;

public abstract class CeilNode extends MathOperation {
   public CeilNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected static int ceilInt(int a) {
      return a;
   }

   @Specialization
   protected static SafeInteger ceilSafeInt(SafeInteger a) {
      return a;
   }

   @Specialization
   protected static Object ceilDouble(
      double d,
      @Cached("createBinaryProfile()") @Cached.Shared("isZero") ConditionProfile isZero,
      @Cached("createBinaryProfile()") @Cached.Shared("requiresNegativeZero") ConditionProfile requiresNegativeZero,
      @Cached("createBinaryProfile()") @Cached.Shared("fitsInt") ConditionProfile fitsInt,
      @Cached("createBinaryProfile()") @Cached.Shared("fitsSafeLong") ConditionProfile fitsSafeLong
   ) {
      if (isZero.profile(d == 0.0)) {
         return d;
      } else if (fitsInt.profile(d >= -2.1474836E9F && d <= 2.147483647E9)) {
         int i = (int)d;
         int result = d > i ? i + 1 : i;
         return requiresNegativeZero.profile(result == 0 && d < 0.0) ? -0.0 : result;
      } else if (fitsSafeLong.profile(JSRuntime.isSafeInteger(d))) {
         long i = (long)d;
         long result = d > i ? i + 1L : i;
         return requiresNegativeZero.profile(result == 0L && d < 0.0) ? -0.0 : SafeInteger.valueOf(result);
      } else {
         return Math.ceil(d);
      }
   }

   @Specialization(replaces = "ceilDouble")
   protected Object ceilToDouble(
      Object a,
      @Cached("createBinaryProfile()") @Cached.Shared("isZero") ConditionProfile isZero,
      @Cached("createBinaryProfile()") @Cached.Shared("requiresNegativeZero") ConditionProfile requiresNegativeZero,
      @Cached("createBinaryProfile()") @Cached.Shared("fitsInt") ConditionProfile fitsInt,
      @Cached("createBinaryProfile()") @Cached.Shared("fitsSafeLong") ConditionProfile fitsSafeLong
   ) {
      double d = this.toDouble(a);
      return ceilDouble(d, isZero, requiresNegativeZero, fitsInt, fitsSafeLong);
   }
}
