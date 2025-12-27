package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import java.util.Set;

@NodeInfo(shortName = "break")
public final class BreakNode extends StatementNode {
   private final BreakException breakException;

   BreakNode(BreakTarget breakTarget) {
      this.breakException = breakTarget.getBreakException();
   }

   public static BreakNode create(BreakTarget breakTarget) {
      return new BreakNode(breakTarget);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.ControlFlowBranchTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowBranchTag.Type.Break.name());
   }

   @Override
   public Object execute(VirtualFrame frame) {
      throw this.breakException;
   }

   @Override
   public void executeVoid(VirtualFrame frame) {
      throw this.breakException;
   }

   public boolean isDirectBreak() {
      return this.breakException instanceof DirectBreakException;
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
