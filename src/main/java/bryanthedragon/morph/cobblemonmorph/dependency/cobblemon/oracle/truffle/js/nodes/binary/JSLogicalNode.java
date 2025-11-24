
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSBinaryNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.YieldException;

public abstract class JSLogicalNode
extends JSBinaryNode
implements ResumableNode.WithIntState {
    private static final int RESUME_RIGHT = 1;
    private static final int RESUME_UNEXECUTED = 0;
    protected final ConditionProfile canShortCircuit = ConditionProfile.createBinaryProfile();

    protected JSLogicalNode(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    protected abstract boolean useLeftValue(Object var1);

    @Override
    public final Object execute(VirtualFrame frame) {
        Object leftValue = this.leftNode.execute(frame);
        if (this.canShortCircuit.profile(this.useLeftValue(leftValue))) {
            return leftValue;
        }
        return this.rightNode.execute(frame);
    }

    @Override
    public Object resume(VirtualFrame frame, int stateSlot) {
        int state = this.getStateAsIntAndReset(frame, stateSlot);
        if (state == 0) {
            Object leftValue = this.leftNode.execute(frame);
            if (this.canShortCircuit.profile(this.useLeftValue(leftValue))) {
                return leftValue;
            }
            try {
                return this.getRight().execute(frame);
            }
            catch (YieldException e) {
                this.setStateAsInt(frame, stateSlot, 1);
                throw e;
            }
        }
        assert (state == 1);
        try {
            return this.rightNode.execute(frame);
        }
        catch (YieldException e) {
            this.setStateAsInt(frame, stateSlot, 1);
            throw e;
        }
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return this.getLeft().isResultAlwaysOfType(clazz) && this.getRight().isResultAlwaysOfType(clazz);
    }
}

