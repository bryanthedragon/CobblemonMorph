package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanUnaryNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import java.util.Set;

@NodeInfo(shortName = "!")
public abstract class JSNotNode extends JSUnaryNode {
   protected JSNotNode(JavaScriptNode operand) {
      super(operand);
   }

   public static JavaScriptNode create(JavaScriptNode operand) {
      if (operand instanceof JSNotNode) {
         JSNotNode childNode = (JSNotNode)operand;
         JavaScriptNode childOperand = childNode.getOperand();
         if (!childOperand.hasSourceSection() && childNode.hasSourceSection()) {
            transferSourceSectionAndTags(childNode, childOperand);
         }

         return childOperand.isResultAlwaysOfType(boolean.class) ? childOperand : JSToBooleanUnaryNode.create(childOperand);
      } else if (operand instanceof JSConstantNode.JSConstantBooleanNode) {
         boolean value = (Boolean)operand.execute(null);
         return JSConstantNode.createBoolean(!value);
      } else if (operand instanceof JSConstantNode.JSConstantIntegerNode) {
         int value = (Integer)operand.execute(null);
         return JSConstantNode.createBoolean(value == 0);
      } else {
         return JSNotNodeGen.create(operand);
      }
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.UnaryOperationTag.class ? true : super.hasTag(tag);
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (!this.getOperand().hasSourceSection()) {
         transferSourceSectionAndTags(this, this.getOperand());
      }

      return this;
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("operator", this.getClass().getAnnotation(NodeInfo.class).shortName());
   }

   @Specialization
   protected boolean doBoolean(boolean a) {
      return !a;
   }

   @Specialization
   protected boolean doNonBoolean(Object a, @Cached("create()") JSToBooleanNode toBooleanNode) {
      return !toBooleanNode.executeBoolean(a);
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return clazz == boolean.class;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSNotNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags));
   }
}
