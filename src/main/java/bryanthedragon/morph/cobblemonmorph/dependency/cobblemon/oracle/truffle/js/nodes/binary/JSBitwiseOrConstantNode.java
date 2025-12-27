package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.Objects;
import java.util.Set;

@NodeInfo(shortName = "|")
public abstract class JSBitwiseOrConstantNode extends JSUnaryNode {
   protected final int rightIntValue;
   protected final BigInt rightBigIntValue;
   protected final boolean isInt;

   protected JSBitwiseOrConstantNode(JavaScriptNode left, Object rightValue) {
      super(left);
      if (rightValue instanceof BigInt) {
         this.isInt = false;
         this.rightBigIntValue = (BigInt)rightValue;
         this.rightIntValue = 0;
      } else {
         this.isInt = true;
         this.rightBigIntValue = null;
         this.rightIntValue = (Integer)rightValue;
      }
   }

   public static JavaScriptNode create(JavaScriptNode left, Object rightValue) {
      return JSBitwiseOrConstantNodeGen.create(left, rightValue);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.BinaryOperationTag.class ? true : super.hasTag(tag);
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (materializedTags.contains(JSTags.BinaryOperationTag.class)) {
         JSConstantNode constantNode = JSConstantNode.create(this.isInt ? this.rightIntValue : this.rightBigIntValue);
         JavaScriptNode node = JSBitwiseOrNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags), constantNode);
         transferSourceSectionAddExpressionTag(this, constantNode);
         transferSourceSectionAndTags(this, node);
         return node;
      } else {
         return this;
      }
   }

   public abstract Object executeObject(Object a);

   @Specialization(guards = "isInt")
   protected int doInteger(int a) {
      return a | this.rightIntValue;
   }

   @Specialization(guards = "isInt")
   protected int doSafeInteger(SafeInteger a) {
      return this.doInteger(a.intValue());
   }

   @Specialization(guards = "isInt")
   protected int doDouble(double a, @Cached("create()") JSToInt32Node leftInt32) {
      return this.doInteger(leftInt32.executeInt(a));
   }

   @Specialization(guards = "!isInt")
   protected void doIntegerThrows(int a) {
      throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
   }

   @Specialization(guards = "!isInt")
   protected void doDoubleThrows(double a) {
      throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
   }

   @Specialization(guards = "isInt")
   protected void doBigIntThrows(BigInt a) {
      throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
   }

   @Specialization(guards = "!isInt")
   protected BigInt doBigInt(BigInt a) {
      return a.or(a);
   }

   @Specialization
   protected Object doOverloaded(
      JSOverloadedOperatorsObject a, @Cached("createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode
   ) {
      return overloadedOperatorNode.execute(a, this.isInt ? this.rightIntValue : this.rightBigIntValue);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.SYMBOL_PIPE;
   }

   @Specialization(guards = {"!hasOverloadedOperators(a)", "isInt"}, replaces = {"doInteger", "doSafeInteger", "doDouble", "doBigIntThrows"})
   protected Object doGenericIntCase(
      Object a,
      @Cached("create()") JSToNumericNode toNumeric,
      @Cached("createBinaryProfile()") ConditionProfile profileIsBigInt,
      @Cached("makeCopy()") JavaScriptNode innerOrNode
   ) {
      Object numericA = toNumeric.execute(a);
      if (profileIsBigInt.profile(JSRuntime.isBigInt(numericA))) {
         throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
      } else {
         return ((JSBitwiseOrConstantNode)innerOrNode).executeObject(numericA);
      }
   }

   protected JSBitwiseOrConstantNode makeCopy() {
      return (JSBitwiseOrConstantNode)this.copyUninitialized(null);
   }

   protected final boolean isInt() {
      return this.isInt;
   }

   @Specialization(guards = {"!hasOverloadedOperators(a)", "!isInt()"}, replaces = {"doIntegerThrows", "doDoubleThrows", "doBigInt"})
   protected BigInt doGenericBigIntCase(
      Object a, @Cached("create()") JSToNumericNode toNumeric, @Cached("createBinaryProfile()") ConditionProfile profileIsBigInt
   ) {
      Object numericA = toNumeric.execute(a);
      if (profileIsBigInt.profile(JSRuntime.isBigInt(numericA))) {
         return this.doBigInt((BigInt)numericA);
      } else {
         throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSBitwiseOrConstantNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags), this.isInt ? this.rightIntValue : this.rightBigIntValue);
   }

   @Override
   public String expressionToString() {
      return this.getOperand() != null
         ? "("
            + Objects.toString(this.getOperand().expressionToString(), "(intermediate value)")
            + " | "
            + (this.isInt ? this.rightIntValue : this.rightBigIntValue)
            + ")"
         : null;
   }
}
