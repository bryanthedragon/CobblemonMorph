package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.Set;

@NodeInfo(shortName = "+")
public abstract class JSUnaryPlusNode extends JSUnaryNode {
   protected JSUnaryPlusNode(JavaScriptNode operand) {
      super(operand);
   }

   public static JSUnaryPlusNode create(JavaScriptNode operand) {
      return JSUnaryPlusNodeGen.create(operand);
   }

   @Specialization
   protected Object doOverloaded(JSOverloadedOperatorsObject value, @Cached("create(getOverloadedOperatorName())") JSOverloadedUnaryNode overloadedOperatorNode) {
      return overloadedOperatorNode.execute(value);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.POS;
   }

   @Specialization(guards = "!hasOverloadedOperators(value)")
   protected Object doDefault(Object value, @Cached("create()") JSToNumberNode toNumberNode) {
      return toNumberNode.executeNumber(value);
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
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSUnaryPlusNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags));
   }

   @Override
   public String expressionToString() {
      return "(+" + this.getOperand().expressionToString() + ")";
   }
}
