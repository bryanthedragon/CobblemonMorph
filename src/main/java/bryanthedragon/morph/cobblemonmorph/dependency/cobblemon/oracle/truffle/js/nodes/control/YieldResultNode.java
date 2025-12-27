package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import java.util.Collections;

public abstract class YieldResultNode extends JavaScriptBaseNode {
   public abstract YieldException generatorYield(VirtualFrame frame, Object value);

   public abstract YieldResultNode cloneUninitialized();

   public static final class ExceptionYieldResultNode extends YieldResultNode {
      @Override
      public YieldException generatorYield(VirtualFrame frame, Object value) {
         throw new YieldException(value);
      }

      @Override
      public YieldResultNode cloneUninitialized() {
         return new YieldResultNode.ExceptionYieldResultNode();
      }
   }

   public static final class FrameYieldResultNode extends YieldResultNode {
      @Node.Child
      private JSWriteFrameSlotNode writeYieldValueNode;

      public FrameYieldResultNode(JSWriteFrameSlotNode writeYieldValueNode) {
         this.writeYieldValueNode = writeYieldValueNode;
      }

      @Override
      public YieldException generatorYield(VirtualFrame frame, Object value) {
         this.writeYieldValueNode.executeWrite(frame, value);
         throw YieldException.YIELD_NULL;
      }

      @Override
      public YieldResultNode cloneUninitialized() {
         return new YieldResultNode.FrameYieldResultNode(JavaScriptNode.cloneUninitialized(this.writeYieldValueNode, Collections.emptySet()));
      }
   }
}
