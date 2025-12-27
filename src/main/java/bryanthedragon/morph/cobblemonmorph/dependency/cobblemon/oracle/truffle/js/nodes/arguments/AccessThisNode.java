package com.oracle.truffle.js.nodes.arguments;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import java.util.Set;

@NodeInfo(shortName = "this")
public final class AccessThisNode extends JavaScriptNode implements RepeatableNode {
   AccessThisNode() {
   }

   public static AccessThisNode create() {
      return new AccessThisNode();
   }

   @Override
   public Object execute(VirtualFrame frame) {
      return JSFrameUtil.getThisObj(frame);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create();
   }
}
