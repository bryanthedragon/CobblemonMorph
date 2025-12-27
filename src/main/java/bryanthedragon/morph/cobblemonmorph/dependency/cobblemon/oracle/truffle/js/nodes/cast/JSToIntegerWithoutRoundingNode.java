package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;

@ImportStatic(JSGuards.class)
public abstract class JSToIntegerWithoutRoundingNode extends JavaScriptBaseNode {
   public abstract Object execute(Object value);

   public final double executeDouble(Object value) {
      return (Double)this.execute(value);
   }

   public static JSToIntegerWithoutRoundingNode create() {
      return JSToIntegerWithoutRoundingNodeGen.create();
   }

   @Specialization
   protected static double doInteger(int value) {
      return value;
   }

   @Specialization
   protected static double doLong(long value) {
      return value;
   }

   @Specialization
   protected static double doBoolean(boolean value) {
      return JSRuntime.booleanToNumber(value);
   }

   @Specialization
   protected static double doSafeInteger(SafeInteger value) {
      return value.longValue();
   }

   @Specialization
   protected static double doDoubleInfinite(double value, @Cached("create()") BranchProfile errorBranch) {
      if (Double.isNaN(value) || value == 0.0) {
         return 0.0;
      } else if (!JSRuntime.isIntegralNumber(value)) {
         errorBranch.enter();
         throw Errors.createRangeError("integral number expected");
      } else {
         return value;
      }
   }

   @Specialization(guards = "isJSNull(value)")
   protected static double doNull(Object value) {
      return 0.0;
   }

   @Specialization(guards = "isUndefined(value)")
   protected static double doUndefined(Object value) {
      return 0.0;
   }

   @Specialization
   protected final long doSymbol(Symbol value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
   }

   @Specialization
   protected final long doBigInt(BigInt value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a BigInt value", this);
   }

   @Specialization
   protected double doString(
      TruffleString value,
      @Cached.Shared("recToIntOrInf") @Cached("create()") JSToIntegerWithoutRoundingNode toIntOrInf,
      @Cached("create()") JSStringToNumberNode stringToNumberNode
   ) {
      return toIntOrInf.executeDouble(stringToNumberNode.executeString(value));
   }

   @Specialization(guards = "isForeignObject(value)||isJSObject(value)")
   protected double doJSOrForeignObject(
      Object value,
      @Cached.Shared("recToIntOrInf") @Cached("create()") JSToIntegerWithoutRoundingNode toIntOrInf,
      @Cached("create()") JSToNumberNode toNumberNode
   ) {
      return toIntOrInf.executeDouble(toNumberNode.executeNumber(value));
   }
}
