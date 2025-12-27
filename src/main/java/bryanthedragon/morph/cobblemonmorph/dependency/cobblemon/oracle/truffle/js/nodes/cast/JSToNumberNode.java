package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.Set;

public abstract class JSToNumberNode extends JavaScriptBaseNode {
   public abstract Object execute(Object value);

   public final Number executeNumber(Object value) {
      return (Number)this.execute(value);
   }

   public static JSToNumberNode create() {
      return JSToNumberNodeGen.create();
   }

   public static JavaScriptNode create(JavaScriptNode child) {
      return (JavaScriptNode)(!child.isResultAlwaysOfType(Number.class) && !child.isResultAlwaysOfType(int.class) && !child.isResultAlwaysOfType(double.class)
         ? JSToNumberNodeGen.JSToNumberUnaryNodeGen.create(child)
         : child);
   }

   @Specialization
   protected static int doInteger(int value) {
      return value;
   }

   @Specialization
   protected static int doBoolean(boolean value) {
      return JSRuntime.booleanToNumber(value);
   }

   @Specialization
   protected static double doDouble(double value) {
      return value;
   }

   @Specialization(guards = "isJSNull(value)")
   protected static int doNull(Object value) {
      return 0;
   }

   @Specialization(guards = "isUndefined(value)")
   protected static double doUndefined(Object value) {
      return Double.NaN;
   }

   @Specialization
   protected Number doString(TruffleString value, @Cached JSStringToNumberNode stringToNumberNode) {
      double doubleValue = stringToNumberNode.executeString(value);
      return JSRuntime.doubleToNarrowestNumber(doubleValue);
   }

   @Specialization
   protected Number doJSObject(
      JSObject value,
      @Cached.Shared("toPrimitiveHintNumberNode") @Cached("createHintNumber()") JSToPrimitiveNode toPrimitiveNode,
      @Cached.Shared("toNumberNode") @Cached JSToNumberNode toNumberNode
   ) {
      return toNumberNode.executeNumber(toPrimitiveNode.execute(value));
   }

   @Specialization
   protected final Number doSymbol(Symbol value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
   }

   @Specialization
   protected final Number doBigInt(BigInt value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a BigInt value", this);
   }

   @Specialization(guards = "isForeignObject(value)")
   protected Number doForeignObject(
      Object value,
      @Cached.Shared("toPrimitiveHintNumberNode") @Cached("createHintNumber()") JSToPrimitiveNode toPrimitiveNode,
      @Cached.Shared("toNumberNode") @Cached JSToNumberNode toNumberNode
   ) {
      return toNumberNode.executeNumber(toPrimitiveNode.execute(value));
   }

   @Specialization(guards = "isJavaNumber(value)")
   protected static double doJavaObject(Object value) {
      return JSRuntime.doubleValue((Number)value);
   }

   public abstract static class JSToNumberUnaryNode extends JSUnaryNode {
      @Node.Child
      private JSToNumberNode toNumberNode;

      protected JSToNumberUnaryNode(JavaScriptNode operand) {
         super(operand);
      }

      @Specialization
      protected Object doDefault(Object value) {
         if (this.toNumberNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toNumberNode = this.insert(JSToNumberNode.create());
         }

         return this.toNumberNode.executeNumber(value);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return JSToNumberNode.create(cloneUninitialized(this.getOperand(), materializedTags));
      }

      @Override
      public boolean isResultAlwaysOfType(Class<?> clazz) {
         return super.isResultAlwaysOfType(Number.class);
      }

      @Override
      public String expressionToString() {
         return this.getOperand().expressionToString();
      }
   }
}
