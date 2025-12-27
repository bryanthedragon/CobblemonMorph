package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public final class ContinueTargetNode extends StatementNode {
   @Node.Child
   private JavaScriptNode block;
   private final ContinueTarget target;

   ContinueTargetNode(JavaScriptNode block, ContinueTarget target) {
      this.target = target;
      this.block = block;
   }

   public static ContinueTargetNode create(JavaScriptNode block, ContinueTarget target) {
      return new ContinueTargetNode(block, target);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      try {
         return this.block.execute(frame);
      } catch (ContinueException var3) {
         if (!var3.matchTarget(this.target)) {
            throw var3;
         } else {
            return Undefined.instance;
         }
      }
   }

   @Override
   public void executeVoid(VirtualFrame frame) {
      try {
         this.block.executeVoid(frame);
      } catch (ContinueException var3) {
         if (!var3.matchTarget(this.target)) {
            throw var3;
         }
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.block, materializedTags), this.target);
   }
}
