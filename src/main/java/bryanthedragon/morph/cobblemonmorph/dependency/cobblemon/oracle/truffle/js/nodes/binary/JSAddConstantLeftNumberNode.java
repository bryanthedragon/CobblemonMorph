package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.Truncatable;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.Objects;
import java.util.Set;

@NodeInfo(shortName = "+")
public abstract class JSAddConstantLeftNumberNode extends JSUnaryNode implements Truncatable {
   @CompilerDirectives.CompilationFinal
   boolean truncate;
   private final double leftDouble;
   private final int leftInt;
   protected final boolean isInt;
   protected final boolean isSafeLong;

   protected JSAddConstantLeftNumberNode(Number leftValue, JavaScriptNode right, boolean truncate) {
      super(right);
      this.truncate = truncate;
      this.leftDouble = leftValue.doubleValue();
      this.leftInt = (int)leftValue.longValue();
      this.isSafeLong = JSRuntime.doubleIsRepresentableAsLong(this.leftDouble) && JSRuntime.isSafeInteger(this.leftDouble);
      this.isInt = leftValue instanceof Integer || JSRuntime.doubleIsRepresentableAsInt(this.leftDouble);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.BinaryOperationTag.class ? true : super.hasTag(tag);
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (materializedTags.contains(JSTags.BinaryOperationTag.class)) {
         JSConstantNode constantNode = this.isInt ? JSConstantNode.createInt(this.leftInt) : JSConstantNode.createDouble(this.leftDouble);
         JavaScriptNode node = JSAddNode.createUnoptimized(constantNode, cloneUninitialized(this.getOperand(), materializedTags), this.truncate);
         transferSourceSectionAddExpressionTag(this, constantNode);
         transferSourceSectionAndTags(this, node);
         return node;
      } else {
         return this;
      }
   }

   public abstract Object execute(Object a);

   public Number getLeftValue() {
      return this.isInt ? this.leftInt : this.leftDouble;
   }

   @Specialization(guards = {"truncate", "isInt"})
   protected int doIntTruncate(int right) {
      return this.leftInt + right;
   }

   @Specialization(guards = {"!truncate", "isInt"}, rewriteOn = ArithmeticException.class)
   protected int doInt(int right) {
      return Math.addExact(this.leftInt, right);
   }

   @Specialization(guards = {"!truncate", "isSafeLong"}, rewriteOn = ArithmeticException.class)
   protected Object doIntOverflow(int right) {
      long result = (long)this.leftDouble + right;
      return JSAddNode.doIntOverflowStaticLong(result);
   }

   @Specialization(guards = "isInt", rewriteOn = ArithmeticException.class)
   protected SafeInteger doSafeInteger(SafeInteger right) {
      return SafeInteger.valueOf(this.leftInt).addExact(right);
   }

   @Specialization
   protected double doDouble(double right) {
      return this.leftDouble + right;
   }

   @Specialization
   protected Object doNumberString(
      TruffleString right, @Cached("leftValueToString()") TruffleString leftString, @Cached("create()") JSConcatStringsNode createLazyString
   ) {
      return createLazyString.executeTString(leftString, right);
   }

   @Specialization
   protected Object doOverloaded(
      JSOverloadedOperatorsObject right, @Cached("createHintDefault(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode
   ) {
      return overloadedOperatorNode.execute(this.getLeftValue(), right);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.SYMBOL_PLUS;
   }

   @Specialization(guards = "!hasOverloadedOperators(right)", replaces = {"doInt", "doDouble", "doNumberString"})
   protected Object doPrimitiveConversion(
      Object right,
      @Cached("createHintDefault()") JSToPrimitiveNode toPrimitiveB,
      @Cached("create()") JSToNumberNode toNumberB,
      @Cached("leftValueToString()") TruffleString leftString,
      @Cached("create()") JSConcatStringsNode createLazyString,
      @Cached("createBinaryProfile()") ConditionProfile profileB
   ) {
      Object primitiveRight = toPrimitiveB.execute(right);
      return profileB.profile(JSGuards.isString(primitiveRight))
         ? createLazyString.executeTString(leftString, (TruffleString)primitiveRight)
         : this.leftDouble + JSRuntime.doubleValue(toNumberB.executeNumber(primitiveRight));
   }

   protected TruffleString leftValueToString() {
      return JSRuntime.toString(this.getLeftValue());
   }

   @Override
   public void setTruncate() {
      CompilerAsserts.neverPartOfCompilation();
      if (!this.truncate) {
         this.truncate = true;
         if (this.isInt) {
            Truncatable.truncate(this.getOperand());
         }
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSAddConstantLeftNumberNodeGen.create(this.getLeftValue(), cloneUninitialized(this.getOperand(), materializedTags), this.truncate);
   }

   @Override
   public String expressionToString() {
      return this.getOperand() != null
         ? "(" + JSRuntime.numberToString(this.getLeftValue()) + " + " + Objects.toString(this.getOperand().expressionToString(), "(intermediate value)") + ")"
         : null;
   }
}
