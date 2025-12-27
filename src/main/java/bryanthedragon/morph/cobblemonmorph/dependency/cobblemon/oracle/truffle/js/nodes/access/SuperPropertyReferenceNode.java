package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import java.util.Set;

@GenerateWrapper
public class SuperPropertyReferenceNode extends JSTargetableNode implements RepeatableNode {
   @Node.Child
   private JavaScriptNode baseValueNode;
   @Node.Child
   private JavaScriptNode thisValueNode;

   private SuperPropertyReferenceNode(JavaScriptNode baseNode, JavaScriptNode thisValueNode) {
      this.baseValueNode = baseNode;
      this.thisValueNode = thisValueNode;
   }

   SuperPropertyReferenceNode(SuperPropertyReferenceNode copy) {
      this.baseValueNode = copy.baseValueNode;
      this.thisValueNode = copy.thisValueNode;
   }

   public static JSTargetableNode create(JavaScriptNode baseNode, JavaScriptNode thisValueNode) {
      assert baseNode instanceof RepeatableNode && thisValueNode instanceof RepeatableNode;

      return new SuperPropertyReferenceNode(baseNode, thisValueNode);
   }

   public JavaScriptNode getBaseValue() {
      return this.baseValueNode;
   }

   @Override
   public Object executeWithTarget(VirtualFrame frame, Object target) {
      return this.execute(frame);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      this.thisValueNode.executeVoid(frame);
      return this.baseValueNode.execute(frame);
   }

   @Override
   public Object evaluateTarget(VirtualFrame frame) {
      return this.thisValueNode.execute(frame);
   }

   public JavaScriptNode getThisValue() {
      return this.thisValueNode;
   }

   @Override
   public JavaScriptNode getTarget() {
      return this.thisValueNode;
   }

   @Override
   public InstrumentableNode.WrapperNode createWrapper(ProbeNode probe) {
      return new SuperPropertyReferenceNodeWrapper(this, this, probe);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new SuperPropertyReferenceNode(cloneUninitialized(this.baseValueNode, materializedTags), cloneUninitialized(this.thisValueNode, materializedTags));
   }
}
