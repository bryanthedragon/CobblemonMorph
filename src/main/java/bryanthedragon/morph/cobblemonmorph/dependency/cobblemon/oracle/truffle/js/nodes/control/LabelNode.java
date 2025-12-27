package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public class LabelNode extends StatementNode {
   @Node.Child
   private JavaScriptNode block;
   private final BreakTarget target;

   LabelNode(JavaScriptNode block, BreakTarget target) {
      this.block = block;
      this.target = target;
   }

   public static LabelNode create(JavaScriptNode block, BreakTarget target) {
      return new LabelNode(block, target);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      try {
         return this.block.execute(frame);
      } catch (LabelBreakException var3) {
         if (!var3.matchTarget(this.target)) {
            throw var3;
         } else {
            return EMPTY;
         }
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.block, materializedTags), this.target);
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      assert EMPTY == Undefined.instance;

      return clazz == Undefined.class;
   }
}
