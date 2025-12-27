package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32Node;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.Objects;
import java.util.Set;

@NodeInfo(shortName = ">>>")
public abstract class JSUnsignedRightShiftConstantNode extends JSUnaryNode {
   protected final int shiftValue;
   protected final int rightValue;

   protected JSUnsignedRightShiftConstantNode(JavaScriptNode operand, int shiftValue, int rightValue) {
      super(operand);

      assert shiftValue > 0;

      this.shiftValue = shiftValue;
      this.rightValue = rightValue;
   }

   public abstract int executeInt(Object a);

   public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
      assert right instanceof JSConstantNode.JSConstantIntegerNode;

      int rightValue = ((JSConstantNode.JSConstantIntegerNode)right).executeInt(null);
      int shiftValue = rightValue & 31;
      if (shiftValue == 0) {
         return JSToUInt32Node.JSToUInt32WrapperNode.create(left, true, rightValue);
      } else if (left instanceof JSConstantNode.JSConstantIntegerNode) {
         int leftValue = ((JSConstantNode.JSConstantIntegerNode)left).executeInt(null);
         return JSConstantNode.createInt(leftValue >>> shiftValue);
      } else {
         return JSUnsignedRightShiftConstantNodeGen.create(left, shiftValue, rightValue);
      }
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.BinaryOperationTag.class ? true : super.hasTag(tag);
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (materializedTags.contains(JSTags.BinaryOperationTag.class)) {
         JSConstantNode constantNode = JSConstantNode.createInt(this.rightValue);
         JavaScriptNode node = JSUnsignedRightShiftNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags), constantNode);
         transferSourceSectionAddExpressionTag(this, constantNode);
         transferSourceSectionAndTags(this, node);
         return node;
      } else {
         return this;
      }
   }

   @Specialization
   protected int doInteger(int a) {
      return a >>> this.shiftValue;
   }

   @Specialization
   protected int doSafeInteger(SafeInteger a) {
      return a.intValue() >>> this.shiftValue;
   }

   @Specialization
   protected int doDouble(double a, @Cached("create()") JSToUInt32Node toUInt32Node) {
      assert this.shiftValue > 0;

      return (int)(toUInt32Node.executeLong(a) >>> this.shiftValue);
   }

   @Specialization
   protected int doBigInt(BigInt a) {
      throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
   }

   @Specialization
   protected Object doOverloaded(
      JSOverloadedOperatorsObject a, @Cached("createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode
   ) {
      return overloadedOperatorNode.execute(a, this.rightValue);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.ANGLE_BRACKET_CLOSE_3;
   }

   @Specialization(guards = {"!hasOverloadedOperators(lval)", "!isHandled(lval)"})
   protected int doGeneric(
      Object lval, @Cached("create()") JSToNumericNode leftToNumeric, @Cached("createInner()") JSUnsignedRightShiftConstantNode innerShiftNode
   ) {
      Object leftOperand = leftToNumeric.execute(lval);
      return innerShiftNode.executeInt(leftOperand);
   }

   protected static boolean isHandled(Object lval) {
      return lval instanceof Integer || lval instanceof Double || lval instanceof SafeInteger || lval instanceof BigInt;
   }

   protected JSUnsignedRightShiftConstantNode createInner() {
      return JSUnsignedRightShiftConstantNodeGen.create(null, this.shiftValue, this.rightValue);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSUnsignedRightShiftConstantNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags), this.shiftValue, this.rightValue);
   }

   @Override
   public String expressionToString() {
      return this.getOperand() != null
         ? "(" + Objects.toString(this.getOperand().expressionToString(), "(intermediate value)") + " >>> " + this.rightValue + ")"
         : null;
   }
}
