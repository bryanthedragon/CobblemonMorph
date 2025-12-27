package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import java.util.Set;

public class DirectBreakTargetNode extends StatementNode {
   @Node.Child
   protected JavaScriptNode block;

   DirectBreakTargetNode(JavaScriptNode block) {
      this.block = block;
   }

   public static DirectBreakTargetNode create(JavaScriptNode block) {
      return new DirectBreakTargetNode(block);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      try {
         return this.block.execute(frame);
      } catch (DirectBreakException var3) {
         return EMPTY;
      }
   }

   @Override
   public void executeVoid(VirtualFrame frame) {
      try {
         this.block.executeVoid(frame);
      } catch (DirectBreakException var3) {
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.block, materializedTags));
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return this.block.isResultAlwaysOfType(clazz);
   }
}
