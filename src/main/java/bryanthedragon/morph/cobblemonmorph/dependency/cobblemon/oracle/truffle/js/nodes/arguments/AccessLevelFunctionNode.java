package com.oracle.truffle.js.nodes.arguments;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.runtime.JSArguments;
import java.util.Set;

public final class AccessLevelFunctionNode extends JavaScriptNode implements RepeatableNode {
   @Node.Child
   private ScopeFrameNode accessFrame;

   private AccessLevelFunctionNode(ScopeFrameNode accessFrame) {
      this.accessFrame = accessFrame;
   }

   public static JavaScriptNode create(int frameLevel) {
      return new AccessLevelFunctionNode(ScopeFrameNode.create(frameLevel));
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Frame parentFrame = this.accessFrame.executeFrame(frame);
      return JSArguments.getFunctionObject(parentFrame.getArguments());
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new AccessLevelFunctionNode(NodeUtil.cloneNode(this.accessFrame));
   }
}
