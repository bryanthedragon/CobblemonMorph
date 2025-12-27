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
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;

public abstract class JSToIntegerAsIntNode extends JavaScriptBaseNode {
   @Node.Child
   private JSToNumberNode toNumberNode;

   public static JSToIntegerAsIntNode create() {
      return JSToIntegerAsIntNodeGen.create();
   }

   public abstract int executeInt(Object operand);

   @Specialization
   protected static int doInteger(int value) {
      return value;
   }

   @Specialization
   protected static int doBoolean(boolean value) {
      return JSRuntime.booleanToNumber(value);
   }

   @Specialization(guards = "isLongRepresentableAsInt32(value.longValue())")
   protected static int doSafeIntegerInt32Range(SafeInteger value) {
      return value.intValue();
   }

   @Specialization(guards = "!isLongRepresentableAsInt32(value.longValue())")
   protected static int doSafeIntegerOther(SafeInteger value) {
      return value.isNegative() ? Integer.MIN_VALUE : Integer.MAX_VALUE;
   }

   protected static boolean inInt32Range(double value) {
      return value <= 2.147483647E9 && value >= -2.1474836E9F;
   }

   @Specialization(guards = "inInt32Range(value)")
   protected static int doDoubleInt32Range(double value) {
      return (int)((long)value & 4294967295L);
   }

   @Specialization(guards = "!inInt32Range(value)")
   protected static int doDoubleOther(double value) {
      if (Double.isNaN(value)) {
         return 0;
      } else {
         return value > 0.0 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
      }
   }

   @Specialization(guards = "isUndefined(value)")
   protected static int doUndefined(Object value) {
      return 0;
   }

   @Specialization(guards = "isJSNull(value)")
   protected static int doNull(Object value) {
      return 0;
   }

   @Specialization
   protected final int doSymbol(Symbol value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
   }

   @Specialization
   protected final int doBigInt(BigInt value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a BigInt value", this);
   }

   @Specialization
   protected int doString(
      TruffleString value, @Cached("create()") JSToIntegerAsIntNode nestedToIntegerNode, @Cached("create()") JSStringToNumberNode stringToNumberNode
   ) {
      return nestedToIntegerNode.executeInt(stringToNumberNode.executeString(value));
   }

   @Specialization
   protected int doJSObject(JSObject value) {
      return JSRuntime.toInt32(this.getToNumberNode().executeNumber(value));
   }

   @Specialization(guards = "isForeignObject(object)")
   protected int doForeignObject(Object object) {
      return JSRuntime.toInt32(this.getToNumberNode().executeNumber(object));
   }

   private JSToNumberNode getToNumberNode() {
      if (this.toNumberNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.toNumberNode = this.insert(JSToNumberNode.create());
      }

      return this.toNumberNode;
   }
}
