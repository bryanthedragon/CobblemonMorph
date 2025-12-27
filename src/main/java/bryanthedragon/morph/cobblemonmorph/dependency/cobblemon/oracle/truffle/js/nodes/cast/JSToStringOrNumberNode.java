package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;

public abstract class JSToStringOrNumberNode extends JavaScriptBaseNode {
   public abstract Object execute(Object operand);

   public static JSToStringOrNumberNode create() {
      return JSToStringOrNumberNodeGen.create();
   }

   @Specialization
   protected int doInteger(int value) {
      return value;
   }

   @Specialization
   protected SafeInteger doSafeInteger(SafeInteger value) {
      return value;
   }

   @Specialization
   protected int doBoolean(boolean value) {
      return doBooleanStatic(value);
   }

   private static int doBooleanStatic(boolean value) {
      return JSRuntime.booleanToNumber(value);
   }

   @Specialization
   protected double doDouble(double value) {
      return value;
   }

   @Specialization
   protected TruffleString doString(TruffleString value) {
      return value;
   }

   @Specialization
   protected double doJSObject(JSObject value, @Cached("create()") JSToDoubleNode toDoubleNode) {
      return toDoubleNode.executeDouble(value);
   }

   @Specialization(guards = "isJSNull(value)")
   protected int doNull(Object value) {
      return 0;
   }

   @Specialization
   protected Object doSymbol(Symbol value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
   }

   @Specialization(guards = "isUndefined(value)")
   protected double doUndefined(Object value) {
      return Double.NaN;
   }

   @Specialization
   protected static BigInt doBigInt(BigInt value) {
      return value;
   }
}
