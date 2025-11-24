
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.GeneratorWrapperNode;
import com.oracle.truffle.js.nodes.control.SuspendNode;
import com.oracle.truffle.js.runtime.objects.Undefined;

public interface ResumableNode {
    default public Object resume(VirtualFrame frame, int stateSlot) {
        throw CompilerDirectives.shouldNotReachHere();
    }

    public static JavaScriptNode createResumableNode(ResumableNode node, int stateSlot) {
        if (!1.$assertionsDisabled && node instanceof SuspendNode) {
            throw new AssertionError(node);
        }
        JavaScriptNode original = (JavaScriptNode)((Object)node);
        return GeneratorWrapperNode.createWrapper(original, stateSlot);
    }

    default public void resetState(VirtualFrame frame, int stateSlot) {
        frame.setObject(stateSlot, Undefined.instance);
    }

    default public FrameSlotKind getStateSlotKind() {
        return FrameSlotKind.Illegal;
    }

    static {
        if (1.$assertionsDisabled) {
            // empty if block
        }
    }

    public static interface WithIntState
    extends ResumableNode {
        default public void setStateAsInt(VirtualFrame frame, int stateSlot, int state) {
            if (!1.$assertionsDisabled && frame.getFrameDescriptor().getSlotKind(stateSlot) != FrameSlotKind.Int) {
                throw new AssertionError();
            }
            frame.setInt(stateSlot, state);
        }

        default public int getStateAsInt(VirtualFrame frame, int stateSlot) {
            if (!1.$assertionsDisabled && frame.getFrameDescriptor().getSlotKind(stateSlot) != FrameSlotKind.Int) {
                throw new AssertionError();
            }
            if (frame.isInt(stateSlot)) {
                return frame.getInt(stateSlot);
            }
            if (!(1.$assertionsDisabled || frame.isObject(stateSlot) && frame.getObject(stateSlot) == Undefined.instance)) {
                throw new AssertionError();
            }
            return 0;
        }

        default public int getStateAsIntAndReset(VirtualFrame frame, int stateSlot) {
            int state = this.getStateAsInt(frame, stateSlot);
            this.resetState(frame, stateSlot);
            return state;
        }

        @Override
        default public FrameSlotKind getStateSlotKind() {
            return FrameSlotKind.Int;
        }
    }

    public static interface WithObjectState
    extends ResumableNode {
        default public void setState(VirtualFrame frame, int stateSlot, Object state) {
            if (!1.$assertionsDisabled && frame.getFrameDescriptor().getSlotKind(stateSlot) != FrameSlotKind.Object) {
                throw new AssertionError();
            }
            frame.setObject(stateSlot, state);
        }

        default public Object getState(VirtualFrame frame, int stateSlot) {
            if (!1.$assertionsDisabled && frame.getFrameDescriptor().getSlotKind(stateSlot) != FrameSlotKind.Object) {
                throw new AssertionError();
            }
            return frame.getObject(stateSlot);
        }

        default public Object getStateAndReset(VirtualFrame frame, int stateSlot) {
            Object state = this.getState(frame, stateSlot);
            this.resetState(frame, stateSlot);
            return state;
        }

        @Override
        default public FrameSlotKind getStateSlotKind() {
            return FrameSlotKind.Object;
        }
    }
}

