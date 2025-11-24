
package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInterface;

public interface RepeatingNode
extends NodeInterface {
    public static final Object CONTINUE_LOOP_STATUS = new Object(){

        public String toString() {
            return "CONTINUE_LOOP_STATUS";
        }
    };
    public static final Object BREAK_LOOP_STATUS = new Object(){

        public String toString() {
            return "BREAK_LOOP_STATUS";
        }
    };

    public boolean executeRepeating(VirtualFrame var1);

    default public Object executeRepeatingWithValue(VirtualFrame frame) {
        if (this.executeRepeating(frame)) {
            return CONTINUE_LOOP_STATUS;
        }
        return BREAK_LOOP_STATUS;
    }

    default public Object initialLoopStatus() {
        return CONTINUE_LOOP_STATUS;
    }

    default public boolean shouldContinue(Object returnValue) {
        return returnValue == this.initialLoopStatus();
    }
}

