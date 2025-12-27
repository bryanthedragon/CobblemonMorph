package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.Errors;
import java.util.Set;

public final class GeneratorWrapperNode extends JavaScriptNode implements RepeatingNode {
   @Node.Child
   private JavaScriptNode childNode;
   private final int stateSlot;

   private GeneratorWrapperNode(JavaScriptNode childNode, int stateSlot) {
      assert childNode instanceof ResumableNode : childNode;

      this.childNode = childNode;
      this.stateSlot = stateSlot;
   }

   public static JavaScriptNode createWrapper(JavaScriptNode child, int stateSlot) {
      JavaScriptNode wrapper = new GeneratorWrapperNode(child, stateSlot);
      JavaScriptNode.transferSourceSectionAndTags(child, wrapper);
      return wrapper;
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      Node child = (Node)(this.childNode instanceof InstrumentableNode.WrapperNode
         ? ((InstrumentableNode.WrapperNode)this.childNode).getDelegateNode()
         : this.childNode);
      return child instanceof JavaScriptNode ? ((JavaScriptNode)child).hasTag(tag) : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      Node child = (Node)(this.childNode instanceof InstrumentableNode.WrapperNode
         ? ((InstrumentableNode.WrapperNode)this.childNode).getDelegateNode()
         : this.childNode);
      return child instanceof JavaScriptNode ? ((JavaScriptNode)child).getNodeObject() : null;
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Node child = this.childNode;
      if (child instanceof InstrumentableNode.WrapperNode) {
         child = ((InstrumentableNode.WrapperNode)child).getDelegateNode();
      }

      if (child instanceof ResumableNode) {
         return ((ResumableNode)child).resume(frame, this.stateSlot);
      } else {
         assert false : child.getClass();

         throw Errors.shouldNotReachHere();
      }
   }

   @Override
   public boolean executeRepeating(VirtualFrame frame) {
      assert this.childNode instanceof ResumableNode && this.childNode instanceof RepeatingNode : this.childNode.getClass();

      return (Boolean)this.execute(frame);
   }

   public JavaScriptNode getResumableNode() {
      return this.childNode;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new GeneratorWrapperNode(cloneUninitialized(this.childNode, materializedTags), this.stateSlot);
   }
}
