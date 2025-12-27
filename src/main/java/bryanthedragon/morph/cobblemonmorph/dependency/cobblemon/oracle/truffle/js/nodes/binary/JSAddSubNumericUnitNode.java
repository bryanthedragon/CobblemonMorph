package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.Truncatable;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSOverloadedUnaryNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.Set;

public abstract class JSAddSubNumericUnitNode extends JSUnaryNode implements Truncatable {
   private final boolean isAddition;
   @CompilerDirectives.CompilationFinal
   boolean truncate;

   protected JSAddSubNumericUnitNode(JavaScriptNode operand, boolean isAddition, boolean truncate) {
      super(operand);
      this.isAddition = isAddition;
      this.truncate = truncate;
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.UnaryOperationTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("operator", this.isAddition ? "++" : "--");
   }

   public abstract Object execute(Object a);

   @Specialization(rewriteOn = ArithmeticException.class)
   protected int doInt(int a) {
      if (this.truncate) {
         return this.isAddition ? a + 1 : a - 1;
      } else {
         return this.isAddition ? Math.addExact(a, 1) : Math.subtractExact(a, 1);
      }
   }

   @Specialization(replaces = "doInt")
   protected double doDouble(double a) {
      return this.isAddition ? a + 1.0 : a - 1.0;
   }

   @Specialization
   protected BigInt doBigInt(BigInt a) {
      return this.isAddition ? a.add(BigInt.ONE) : a.subtract(BigInt.ONE);
   }

   @Specialization(guards = "isJavaNumber(a)")
   protected double doJavaNumber(Object a) {
      double doubleValue = JSRuntime.toDouble(a);
      return this.isAddition ? doubleValue + 1.0 : doubleValue - 1.0;
   }

   @Specialization
   protected Object doOverloaded(JSOverloadedOperatorsObject a, @Cached("create(getOverloadedOperatorName())") JSOverloadedUnaryNode overloadedOperatorNode) {
      return overloadedOperatorNode.execute(a);
   }

   protected TruffleString getOverloadedOperatorName() {
      return this.isAddition ? Strings.SYMBOL_PLUS_PLUS : Strings.SYMBOL_MINUS_MINUS;
   }

   @Override
   public void setTruncate() {
      CompilerAsserts.neverPartOfCompilation();
      if (!this.truncate) {
         this.truncate = true;
      }
   }

   public static JSAddSubNumericUnitNode create(JavaScriptNode operand, boolean isAddition, boolean truncate) {
      return JSAddSubNumericUnitNodeGen.create(operand, isAddition, truncate);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.getOperand(), materializedTags), this.isAddition, this.truncate);
   }
}
