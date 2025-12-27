package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import java.util.Set;

@NodeInfo(shortName = "&&")
public final class JSAndNode extends JSLogicalNode {
   @Node.Child
   private JSToBooleanNode toBooleanCast = JSToBooleanNode.create();

   JSAndNode(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
      return new JSAndNode(left, right);
   }

   @Override
   protected boolean useLeftValue(Object leftValue) {
      return !this.toBooleanCast.executeBoolean(leftValue);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new JSAndNode(cloneUninitialized(this.getLeft(), materializedTags), cloneUninitialized(this.getRight(), materializedTags));
   }
}
