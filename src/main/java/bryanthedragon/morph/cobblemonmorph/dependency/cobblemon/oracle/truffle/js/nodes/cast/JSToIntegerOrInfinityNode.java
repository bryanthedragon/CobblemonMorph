package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;

@ImportStatic(JSGuards.class)
public abstract class JSToIntegerOrInfinityNode extends JavaScriptBaseNode {
   public abstract Object execute(Object value);

   public final Number executeNumber(Object value) {
      return (Number)this.execute(value);
   }

   public static JSToIntegerOrInfinityNode create() {
      return JSToIntegerOrInfinityNodeGen.create();
   }

   @Specialization
   protected static int doInteger(int value) {
      return value;
   }

   @Specialization
   protected static long doLong(long value) {
      return value;
   }

   @Specialization
   protected static int doBoolean(boolean value) {
      return JSRuntime.booleanToNumber(value);
   }

   @Specialization
   protected static SafeInteger doSafeInteger(SafeInteger value) {
      return value;
   }

   @Specialization(guards = "shouldConvertToZero(value)")
   protected static int doDoubleNegativeZero(double value) {
      return 0;
   }

   @Specialization(guards = "!shouldConvertToZero(value)")
   protected double doDouble(double value) {
      return JSRuntime.truncateDouble(value);
   }

   @Specialization(guards = "isJSNull(value)")
   protected static int doNull(Object value) {
      return 0;
   }

   @Specialization(guards = "isUndefined(value)")
   protected static int doUndefined(Object value) {
      return 0;
   }

   @Specialization
   protected final Number doSymbol(Symbol value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
   }

   @Specialization
   protected final Number doBigInt(BigInt value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a BigInt value", this);
   }

   @Specialization
   protected Number doString(
      TruffleString value,
      @Cached.Shared("recToIntOrInf") @Cached("create()") JSToIntegerOrInfinityNode toIntOrInf,
      @Cached("create()") JSStringToNumberNode stringToNumberNode
   ) {
      return toIntOrInf.executeNumber(stringToNumberNode.executeString(value));
   }

   @Specialization(guards = "isForeignObject(value)||isJSObject(value)")
   protected Number doJSOrForeignObject(
      Object value, @Cached.Shared("recToIntOrInf") @Cached("create()") JSToIntegerOrInfinityNode toIntOrInf, @Cached("create()") JSToNumberNode toNumberNode
   ) {
      return toIntOrInf.executeNumber(toNumberNode.executeNumber(value));
   }

   protected static boolean shouldConvertToZero(double value) {
      return JSRuntime.isNaN(value) || JSRuntime.isNegativeZero(value);
   }
}
