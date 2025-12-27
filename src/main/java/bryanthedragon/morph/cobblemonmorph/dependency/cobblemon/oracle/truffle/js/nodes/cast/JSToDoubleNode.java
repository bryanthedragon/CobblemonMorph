package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;

public abstract class JSToDoubleNode extends JavaScriptBaseNode {
   @Node.Child
   private JSToDoubleNode toDoubleNode;

   public abstract Object execute(Object value);

   public abstract double executeDouble(Object value);

   public static JSToDoubleNode create() {
      return JSToDoubleNodeGen.create();
   }

   @Specialization
   protected static double doInteger(int value) {
      return value;
   }

   @Specialization
   protected static double doBoolean(boolean value) {
      return JSRuntime.booleanToNumber(value);
   }

   @Specialization
   protected static double doDouble(double value) {
      return value;
   }

   @Specialization
   protected final double doBigInt(BigInt value) {
      throw Errors.createTypeErrorCannotConvertBigIntToNumber(this);
   }

   @Specialization(guards = "isJSNull(value)")
   protected static double doNull(Object value) {
      return 0.0;
   }

   @Specialization(guards = "isUndefined(value)")
   protected static double doUndefined(Object value) {
      return Double.NaN;
   }

   @Specialization
   protected static double doStringDouble(TruffleString value, @Cached("create()") JSStringToNumberNode stringToNumberNode) {
      return stringToNumberNode.executeString(value);
   }

   @Specialization
   protected double doJSObject(JSObject value, @Cached("createHintNumber()") JSToPrimitiveNode toPrimitiveNode) {
      return this.getToDoubleNode().executeDouble(toPrimitiveNode.execute(value));
   }

   @Specialization
   protected final double doSymbol(Symbol value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
   }

   @Specialization(guards = "isForeignObject(object)")
   protected double doForeignObject(Object object, @Cached("createHintNumber()") JSToPrimitiveNode toPrimitiveNode) {
      return this.getToDoubleNode().executeDouble(toPrimitiveNode.execute(object));
   }

   private JSToDoubleNode getToDoubleNode() {
      if (this.toDoubleNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.toDoubleNode = this.insert(create());
      }

      return this.toDoubleNode;
   }

   @Specialization(guards = "isJavaNumber(value)")
   protected static double doJavaNumber(Object value) {
      return JSRuntime.doubleValue((Number)value);
   }
}
