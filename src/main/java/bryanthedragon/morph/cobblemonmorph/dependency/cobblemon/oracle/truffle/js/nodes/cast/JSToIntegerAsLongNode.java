package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;

@ImportStatic(Double.class)
public abstract class JSToIntegerAsLongNode extends JavaScriptBaseNode {
   public static JSToIntegerAsLongNode create() {
      return JSToIntegerAsLongNodeGen.create();
   }

   public abstract long executeLong(Object operand);

   @Specialization
   protected static long doInteger(int value) {
      return value;
   }

   @Specialization
   protected static long doBoolean(boolean value) {
      return JSRuntime.booleanToNumber(value);
   }

   @Specialization
   protected static long doSafeInteger(SafeInteger value) {
      return value.longValue();
   }

   @Specialization(guards = "!isInfinite(value)")
   protected static long doDouble(double value) {
      return (long)value;
   }

   @Specialization(guards = "isInfinite(value)")
   protected static long doDoubleInfinite(double value) {
      return value > 0.0 ? Long.MAX_VALUE : Long.MIN_VALUE;
   }

   @Specialization(guards = "isUndefined(value)")
   protected static long doUndefined(Object value) {
      return 0L;
   }

   @Specialization(guards = "isJSNull(value)")
   protected static long doNull(Object value) {
      return 0L;
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
   protected long doString(
      TruffleString value, @Cached("create()") JSToIntegerAsLongNode nestedToIntegerNode, @Cached("create()") JSStringToNumberNode stringToNumberNode
   ) {
      return nestedToIntegerNode.executeLong(stringToNumberNode.executeString(value));
   }

   @Specialization
   protected long doJSObject(JSObject value, @Cached("create()") JSToNumberNode toNumberNode) {
      return JSRuntime.toInteger(toNumberNode.executeNumber(value));
   }

   @Specialization(guards = "isForeignObject(value)")
   protected long doForeignObject(Object value, @Cached("create()") JSToNumberNode toNumberNode) {
      return JSRuntime.toInteger(toNumberNode.executeNumber(value));
   }
}
