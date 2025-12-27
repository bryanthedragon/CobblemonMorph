package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;

public abstract class MaxNode extends MathOperation {
   private final ConditionProfile leftSmaller = ConditionProfile.createBinaryProfile();
   private final ConditionProfile rightSmaller = ConditionProfile.createBinaryProfile();
   private final ConditionProfile bothEqual = ConditionProfile.createBinaryProfile();
   private final ConditionProfile negativeZero = ConditionProfile.createBinaryProfile();

   public MaxNode(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   private double maxDoubleDouble(double a, double b) {
      if (this.leftSmaller.profile(a > b)) {
         return a;
      } else if (this.rightSmaller.profile(b > a)) {
         return b;
      } else if (this.bothEqual.profile(a == b)) {
         return this.negativeZero.profile(JSRuntime.isNegativeZero(a)) ? b : a;
      } else {
         return Double.NaN;
      }
   }

   protected static boolean caseIntInt(Object[] args) {
      assert args.length == 2;

      return args[0] instanceof Integer && args[1] instanceof Integer;
   }

   @Specialization(guards = "args.length == 0")
   protected static double max0Param(Object[] args) {
      return Double.NEGATIVE_INFINITY;
   }

   @Specialization(guards = "args.length == 1")
   protected double max1Param(Object[] args) {
      return this.toDouble(args[0]);
   }

   @Specialization(guards = {"args.length == 2", "caseIntInt(args)"})
   protected static int max2ParamInt(Object[] args, @Cached("createBinaryProfile()") ConditionProfile maxProfile) {
      int i1 = (Integer)args[0];
      int i2 = (Integer)args[1];
      return max(i1, i2, maxProfile);
   }

   @Specialization(guards = {"args.length == 2", "!caseIntInt(args)"})
   protected Object max2Param(
      Object[] args,
      @Cached("createBinaryProfile()") ConditionProfile isIntBranch,
      @Cached("createBinaryProfile()") ConditionProfile maxProfile,
      @Cached("create()") JSToNumberNode toNumber1Node,
      @Cached("create()") JSToNumberNode toNumber2Node
   ) {
      Number n1 = toNumber1Node.executeNumber(args[0]);
      Number n2 = toNumber2Node.executeNumber(args[1]);
      if (isIntBranch.profile(n1 instanceof Integer && n2 instanceof Integer)) {
         return max((Integer)n1, (Integer)n2, maxProfile);
      } else {
         double d1 = JSRuntime.doubleValue(n1);
         double d2 = JSRuntime.doubleValue(n2);
         return this.maxDoubleDouble(d1, d2);
      }
   }

   @Specialization(guards = "args.length >= 3")
   protected double max(Object[] args) {
      double largest = this.maxDoubleDouble(this.toDouble(args[0]), this.toDouble(args[1]));

      for (int i = 2; i < args.length; i++) {
         largest = this.maxDoubleDouble(largest, this.toDouble(args[i]));
      }

      return largest;
   }

   private static int max(int a, int b, ConditionProfile maxProfile) {
      return maxProfile.profile(a >= b) ? a : b;
   }
}
