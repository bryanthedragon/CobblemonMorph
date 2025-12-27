package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.Truncatable;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.Set;

@NodeInfo(shortName = "~")
public abstract class JSComplementNode extends JSUnaryNode {
   protected JSComplementNode(JavaScriptNode operand) {
      super(operand);
   }

   public static JSComplementNode create(JavaScriptNode operand) {
      Truncatable.truncate(operand);
      return JSComplementNodeGen.create(operand);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.UnaryOperationTag.class ? true : super.hasTag(tag);
   }

   @Specialization
   protected int doInteger(int a) {
      return ~a;
   }

   @Specialization
   protected int doSafeInteger(SafeInteger a) {
      return ~a.intValue();
   }

   @Specialization
   protected int doDouble(double a, @Cached("create()") JSToInt32Node toInt32Node) {
      return this.doInteger(toInt32Node.executeInt(a));
   }

   @Specialization
   protected BigInt doBigInt(BigInt a) {
      return a.not();
   }

   @Specialization
   protected Object doOverloaded(JSOverloadedOperatorsObject a, @Cached("create(getOverloadedOperatorName())") JSOverloadedUnaryNode overloadedOperatorNode) {
      return overloadedOperatorNode.execute(a);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.SYMBOL_TILDE;
   }

   @Specialization(guards = "!hasOverloadedOperators(value)", replaces = {"doInteger", "doSafeInteger", "doDouble", "doBigInt"})
   protected Object doGeneric(VirtualFrame frame, Object value, @Cached JSToNumericNode toNumericNode, @Cached("createInner()") JSComplementNode recursive) {
      Object number = toNumericNode.execute(value);
      return recursive.execute(frame, number);
   }

   static JSComplementNode createInner() {
      return JSComplementNodeGen.create(null);
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return clazz == Number.class;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSComplementNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags));
   }
}
