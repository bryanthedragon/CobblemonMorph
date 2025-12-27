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
public abstract class JSAddConstantRightNumberNode extends JSUnaryNode implements Truncatable {
   @CompilerDirectives.CompilationFinal
   boolean truncate;
   private final double rightDouble;
   private final int rightInt;
   protected final boolean isInt;

   protected JSAddConstantRightNumberNode(JavaScriptNode left, Number rightValue, boolean truncate) {
      super(left);
      this.truncate = truncate;
      this.rightDouble = rightValue.doubleValue();
      if (!(rightValue instanceof Integer) && !JSRuntime.doubleIsRepresentableAsInt(this.rightDouble)) {
         this.isInt = false;
         this.rightInt = 0;
      } else {
         this.isInt = true;
         this.rightInt = rightValue.intValue();
      }
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.BinaryOperationTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("operator", this.getClass().getAnnotation(NodeInfo.class).shortName());
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (materializedTags.contains(JSTags.BinaryOperationTag.class)) {
         JSConstantNode constantNode = this.isInt ? JSConstantNode.createInt(this.rightInt) : JSConstantNode.createDouble(this.rightDouble);
         JavaScriptNode node = JSAddNode.createUnoptimized(cloneUninitialized(this.getOperand(), materializedTags), constantNode, this.truncate);
         transferSourceSectionAddExpressionTag(this, constantNode);
         transferSourceSectionAndTags(this, node);
         return node;
      } else {
         return this;
      }
   }

   public abstract Object execute(Object a);

   public Number getRightValue() {
      return this.isInt ? this.rightInt : this.rightDouble;
   }

   @Specialization(guards = {"truncate", "isInt"})
   protected int doIntTruncate(int left) {
      return left + this.rightInt;
   }

   @Specialization(guards = {"!truncate", "isInt"}, rewriteOn = ArithmeticException.class)
   protected int doInt(int left) {
      return Math.addExact(left, this.rightInt);
   }

   @Specialization(guards = {"!truncate", "isInt"}, rewriteOn = ArithmeticException.class)
   protected Object doIntOverflow(int left) {
      long result = (long)left + this.rightInt;
      return JSAddNode.doIntOverflowStaticLong(result);
   }

   @Specialization(guards = "isInt", rewriteOn = ArithmeticException.class)
   protected SafeInteger doSafeInteger(SafeInteger left) {
      return left.addExact(SafeInteger.valueOf(this.rightInt));
   }

   @Specialization
   protected double doDouble(double left) {
      return left + this.rightDouble;
   }

   @Specialization
   protected TruffleString doStringNumber(
      TruffleString a, @Cached("rightValueToString()") TruffleString rightString, @Cached("create()") JSConcatStringsNode createLazyString
   ) {
      return createLazyString.executeTString(a, rightString);
   }

   @Specialization
   protected Object doOverloaded(
      JSOverloadedOperatorsObject a, @Cached("createHintDefault(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode
   ) {
      return overloadedOperatorNode.execute(a, this.getRightValue());
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.SYMBOL_PLUS;
   }

   @Specialization(guards = "!hasOverloadedOperators(a)", replaces = {"doInt", "doDouble", "doStringNumber"})
   protected Object doPrimitiveConversion(
      Object a,
      @Cached("createHintDefault()") JSToPrimitiveNode toPrimitiveA,
      @Cached("create()") JSToNumberNode toNumberA,
      @Cached("rightValueToString()") TruffleString rightString,
      @Cached("create()") JSConcatStringsNode createLazyString,
      @Cached("createBinaryProfile()") ConditionProfile profileA
   ) {
      Object primitiveA = toPrimitiveA.execute(a);
      return profileA.profile(JSGuards.isString(primitiveA))
         ? createLazyString.executeTString((TruffleString)primitiveA, rightString)
         : JSRuntime.doubleValue(toNumberA.executeNumber(primitiveA)) + this.rightDouble;
   }

   protected TruffleString rightValueToString() {
      return JSRuntime.toString(this.getRightValue());
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
      return JSAddConstantRightNumberNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags), this.getRightValue(), this.truncate);
   }

   @Override
   public String expressionToString() {
      return this.getOperand() != null
         ? "("
            + Objects.toString(this.getOperand().expressionToString(), "(intermediate value)")
            + " + "
            + JSRuntime.numberToString(this.getRightValue())
            + ")"
         : null;
   }
}
