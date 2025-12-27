package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

@NodeInfo(cost = NodeCost.NONE)
public final class EmptyNode extends StatementNode implements RepeatableNode {
   EmptyNode() {
   }

   public static EmptyNode create() {
      return new EmptyNode();
   }

   @Override
   public Object execute(VirtualFrame frame) {
      return EMPTY;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return this.copy();
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      assert EMPTY == Undefined.instance;

      return clazz == Undefined.class;
   }
}
