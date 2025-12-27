package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;

public abstract class RoundNode extends MathOperation {
   private final ConditionProfile shiftProfile = ConditionProfile.createBinaryProfile();
   private final BranchProfile negativeLongBitsProfile = BranchProfile.create();
   private static final int EXP_BIAS = 1023;
   private static final int SIGNIFICAND_WIDTH = 53;
   private static final long EXP_BIT_MASK = 9218868437227405312L;
   private static final long SIGNIF_BIT_MASK = 4503599627370495L;

   RoundNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   public static RoundNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
      return RoundNodeGen.create(context, builtin, createCast(arguments));
   }

   protected static JavaScriptNode[] createCast(JavaScriptNode[] argumentNodes) {
      argumentNodes[0] = JSToNumberNode.create(argumentNodes[0]);
      return argumentNodes;
   }

   protected static boolean isCornercase(double d) {
      return Double.isNaN(d) || JSRuntime.isNegativeZero(d);
   }

   @Specialization
   protected static int roundInt(int a) {
      return a;
   }

   @Specialization(guards = "isCornercase(value)")
   protected static double roundCornercase(double value) {
      return value;
   }

   private long round(double a) {
      long longBits = Double.doubleToRawLongBits(a);
      long biasedExp = (longBits & 9218868437227405312L) >> 52;
      long shift = 1074L - biasedExp;
      if (this.shiftProfile.profile((shift & -64L) == 0L)) {
         long r = longBits & 4503599627370495L | 4503599627370496L;
         if (longBits < 0L) {
            this.negativeLongBitsProfile.enter();
            r = -r;
         }

         return (r >> (int)shift) + 1L >> 1;
      } else {
         return (long)a;
      }
   }

   @Specialization(guards = {"!isCornercase(value)", "isDoubleInInt32Range(value)"}, rewriteOn = ArithmeticException.class)
   protected int roundDoubleInt(double value) {
      long longValue = this.round(value);
      if (longValue == 0L && value < 0.0) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new ArithmeticException();
      } else {
         assert JSRuntime.longIsRepresentableAsInt(longValue);

         return (int)longValue;
      }
   }

   @Specialization(guards = "!isCornercase(value)", replaces = "roundDoubleInt")
   protected double roundDouble(
      double value, @Cached("createBinaryProfile()") ConditionProfile profileA, @Cached("createBinaryProfile()") ConditionProfile profileB
   ) {
      long longValue = this.round(value);
      if (profileA.profile(longValue == Long.MIN_VALUE || longValue == Long.MAX_VALUE)) {
         return value;
      } else {
         return profileB.profile(longValue == 0L && value < 0.0) ? -0.0 : longValue;
      }
   }
}
