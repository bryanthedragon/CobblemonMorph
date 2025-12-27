package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import java.util.Set;

@NodeInfo(shortName = "continue")
public final class ContinueNode extends StatementNode {
   private final ContinueException continueException;

   ContinueNode(ContinueTarget continueTarget) {
      this.continueException = continueTarget.getContinueException();
   }

   public static ContinueNode create(ContinueTarget continueTarget) {
      return new ContinueNode(continueTarget);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.ControlFlowBranchTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowBranchTag.Type.Continue.name());
   }

   @Override
   public Object execute(VirtualFrame frame) {
      throw this.continueException;
   }

   @Override
   public void executeVoid(VirtualFrame frame) {
      throw this.continueException;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return this.copy();
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return true;
   }
}
