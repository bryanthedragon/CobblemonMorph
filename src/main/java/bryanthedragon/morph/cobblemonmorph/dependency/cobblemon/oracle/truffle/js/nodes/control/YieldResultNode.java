
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import java.util.Collections;

public abstract class YieldResultNode
extends JavaScriptBaseNode {
    public abstract YieldException generatorYield(VirtualFrame var1, Object var2);

    public abstract YieldResultNode cloneUninitialized();

    public static final class FrameYieldResultNode
    extends YieldResultNode {
        @Node.Child
        private JSWriteFrameSlotNode writeYieldValueNode;

        public FrameYieldResultNode(JSWriteFrameSlotNode writeYieldValueNode) {
            this.writeYieldValueNode = writeYieldValueNode;
        }

        @Override
        public YieldException generatorYield(VirtualFrame frame, Object value2) {
            this.writeYieldValueNode.executeWrite(frame, value2);
            throw YieldException.YIELD_NULL;
        }

        @Override
        public YieldResultNode cloneUninitialized() {
            return new FrameYieldResultNode(JavaScriptNode.cloneUninitialized(this.writeYieldValueNode, Collections.emptySet()));
        }
    }

    public static final class ExceptionYieldResultNode
    extends YieldResultNode {
        @Override
        public YieldException generatorYield(VirtualFrame frame, Object value2) {
            throw new YieldException(value2);
        }

        @Override
        public YieldResultNode cloneUninitialized() {
            return new ExceptionYieldResultNode();
        }
    }
}

