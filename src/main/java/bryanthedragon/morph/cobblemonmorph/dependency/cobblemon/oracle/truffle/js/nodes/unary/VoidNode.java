package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

@NodeInfo(shortName = "void", cost = NodeCost.NONE)
public abstract class VoidNode extends JSUnaryNode {
   protected VoidNode(JavaScriptNode operand) {
      super(operand);
   }

   public static JavaScriptNode create(JavaScriptNode operand) {
      if (operand.isResultAlwaysOfType(Undefined.class)) {
         return operand;
      } else {
         return (JavaScriptNode)(operand instanceof JSConstantNode ? JSConstantNode.createUndefined() : VoidNodeGen.create(operand));
      }
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
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return clazz == Undefined.class;
   }

   @Specialization
   protected Object doGeneric(Object operand) {
      return Undefined.instance;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return VoidNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags));
   }
}
