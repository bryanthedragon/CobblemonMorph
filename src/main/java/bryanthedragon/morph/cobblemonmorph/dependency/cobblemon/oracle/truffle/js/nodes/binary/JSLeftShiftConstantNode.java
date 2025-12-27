package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.Truncatable;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.Objects;
import java.util.Set;

@NodeInfo(shortName = "<<")
public abstract class JSLeftShiftConstantNode extends JSUnaryNode {
   protected final int shiftValue;

   protected JSLeftShiftConstantNode(JavaScriptNode operand, int shiftValue) {
      super(operand);
      this.shiftValue = shiftValue;
   }

   public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
      assert right instanceof JSConstantNode.JSConstantIntegerNode;

      int shiftValue = ((JSConstantNode.JSConstantIntegerNode)right).executeInt(null);
      if (left instanceof JSConstantNode.JSConstantIntegerNode) {
         int leftValue = ((JSConstantNode.JSConstantIntegerNode)left).executeInt(null);
         return JSConstantNode.createInt(leftValue << shiftValue);
      } else {
         Truncatable.truncate(left);
         return JSLeftShiftConstantNodeGen.create(left, shiftValue);
      }
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.BinaryOperationTag.class ? true : super.hasTag(tag);
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (materializedTags.contains(JSTags.BinaryOperationTag.class)) {
         JSConstantNode constantNode = JSConstantNode.createInt(this.shiftValue);
         JavaScriptNode node = JSLeftShiftNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags), constantNode);
         transferSourceSectionAddExpressionTag(this, constantNode);
         transferSourceSectionAndTags(this, node);
         return node;
      } else {
         return this;
      }
   }

   public abstract int executeInt(Object a);

   @Specialization
   protected int doInteger(int a) {
      return a << this.shiftValue;
   }

   @Specialization
   protected int doSafeInteger(SafeInteger a) {
      return a.intValue() << this.shiftValue;
   }

   @Specialization
   protected int doDouble(double a, @Cached("create()") JSToInt32Node leftInt32Node) {
      return leftInt32Node.executeInt(a) << this.shiftValue;
   }

   @Specialization
   protected void doBigInt(BigInt a) {
      throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
   }

   @Specialization
   protected Object doOverloaded(
      JSOverloadedOperatorsObject a, @Cached("createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode
   ) {
      return overloadedOperatorNode.execute(a, this.shiftValue);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.ANGLE_BRACKET_OPEN_2;
   }

   @Specialization(guards = "!hasOverloadedOperators(a)", replaces = {"doInteger", "doSafeInteger", "doDouble", "doBigInt"})
   protected Object doGeneric(Object a, @Cached("create()") JSToNumericNode leftToNumericNode, @Cached("makeCopy()") JSLeftShiftConstantNode innerShiftNode) {
      Object numericLeft = leftToNumericNode.execute(a);
      return innerShiftNode.executeInt(numericLeft);
   }

   protected JSLeftShiftConstantNode makeCopy() {
      return (JSLeftShiftConstantNode)this.copyUninitialized(null);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSLeftShiftConstantNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags), this.shiftValue);
   }

   @Override
   public String expressionToString() {
      return this.getOperand() != null
         ? "(" + Objects.toString(this.getOperand().expressionToString(), "(intermediate value)") + " << " + this.shiftValue + ")"
         : null;
   }
}
