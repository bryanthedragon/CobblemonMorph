package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.Truncatable;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.Set;

public abstract class JSToInt32Node extends JSUnaryNode {
   protected final boolean bitwiseOr;

   protected JSToInt32Node(JavaScriptNode operand, boolean bitwiseOr) {
      super(operand);
      this.bitwiseOr = bitwiseOr;
   }

   @Override
   public final Object execute(VirtualFrame frame) {
      return this.executeInt(frame);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.UnaryOperationTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("operator", this.getClass().getAnnotation(NodeInfo.class).shortName());
   }

   @Override
   public abstract int executeInt(VirtualFrame frame);

   public abstract int executeInt(Object operand);

   public static JavaScriptNode create(JavaScriptNode child) {
      return create(child, false);
   }

   public static JavaScriptNode create(JavaScriptNode child, boolean bitwiseOr) {
      if (child != null) {
         if (child.isResultAlwaysOfType(int.class)) {
            return child;
         }

         Truncatable.truncate(child);
         if (child instanceof JSConstantNode) {
            Object constantOperand = ((JSConstantNode)child).getValue();
            if (constantOperand != null && !(constantOperand instanceof Symbol) && JSRuntime.isJSPrimitive(constantOperand)) {
               return JSConstantNode.createInt(JSRuntime.toInt32(constantOperand));
            }
         }
      }

      return JSToInt32NodeGen.create(child, bitwiseOr);
   }

   public static JSToInt32Node create() {
      return JSToInt32NodeGen.create(null, false);
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return !this.bitwiseOr && clazz == int.class;
   }

   @Specialization
   protected int doInteger(int value) {
      return value;
   }

   @Specialization
   protected int doSafeInteger(SafeInteger value) {
      return value.intValue();
   }

   @Specialization
   protected int doBoolean(boolean value) {
      return JSRuntime.booleanToNumber(value);
   }

   @Specialization(guards = "isLongRepresentableAsInt32(value)")
   protected int doLong(long value) {
      return (int)value;
   }

   @Specialization(guards = "!isDoubleLargerThan2e32(value)")
   protected int doDoubleFitsInt(double value) {
      return (int)((long)value);
   }

   @Specialization(guards = {"isDoubleLargerThan2e32(value)", "isDoubleRepresentableAsLong(value)", "isDoubleSafeInteger(value)"})
   protected int doDoubleRepresentableAsSafeInteger(double value) {
      assert !Double.isFinite(value) || value % 1.0 == 0.0;

      assert !Double.isNaN(value);

      assert !JSRuntime.isNegativeZero(value);

      return (int)((long)value);
   }

   @Specialization(guards = {"isDoubleLargerThan2e32(value)", "isDoubleRepresentableAsLong(value)"}, replaces = "doDoubleRepresentableAsSafeInteger")
   protected int doDoubleRepresentableAsLong(double value) {
      assert !Double.isFinite(value) || value % 1.0 == 0.0;

      return JSRuntime.toInt32NoTruncate(value);
   }

   @Specialization(guards = {"isDoubleLargerThan2e32(value)", "!isDoubleRepresentableAsLong(value)"})
   protected int doDouble(double value) {
      return JSRuntime.toInt32(value);
   }

   @Specialization(guards = "isUndefined(value)")
   protected int doUndefined(Object value) {
      return 0;
   }

   @Specialization(guards = "isJSNull(value)")
   protected int doNull(Object value) {
      return 0;
   }

   @Specialization
   protected int doString(TruffleString value, @Cached("create()") JSStringToNumberNode stringToNumberNode) {
      return doubleToInt32(stringToNumberNode.executeString(value));
   }

   @Specialization
   protected final int doSymbol(Symbol value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
   }

   @Specialization
   protected int doBigInt(BigInt value) {
      throw Errors.createTypeErrorCannotConvertBigIntToNumber(this);
   }

   public boolean isBitwiseOr() {
      return this.bitwiseOr;
   }

   @Specialization(guards = "isBitwiseOr()")
   protected Object doOverloadedOperator(
      JSOverloadedOperatorsObject value, @Cached("createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode
   ) {
      return overloadedOperatorNode.execute(value, 0);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.SYMBOL_PIPE;
   }

   @Specialization(guards = "!isBitwiseOr() || !hasOverloadedOperators(value)")
   protected int doJSObject(JSObject value, @Cached("create()") JSToDoubleNode toDoubleNode) {
      return doubleToInt32(toDoubleNode.executeDouble(value));
   }

   private static int doubleToInt32(double d) {
      return !Double.isInfinite(d) && !Double.isNaN(d) && d != 0.0 ? JSRuntime.toInt32(d) : 0;
   }

   @Specialization(guards = "isForeignObject(object)")
   protected static int doForeignObject(
      Object object, @Cached("createHintNumber()") JSToPrimitiveNode toPrimitiveNode, @Cached("create()") JSToInt32Node toInt32Node
   ) {
      return toInt32Node.executeInt(toPrimitiveNode.execute(object));
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSToInt32NodeGen.create(cloneUninitialized(this.getOperand(), materializedTags), this.bitwiseOr);
   }
}
