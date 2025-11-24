
package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.BytecodeOSRValidation;
import com.oracle.truffle.api.nodes.NodeAccessor;
import com.oracle.truffle.api.nodes.NodeInterface;

public interface BytecodeOSRNode
extends NodeInterface {
    public Object executeOSR(VirtualFrame var1, int var2, Object var3);

    public Object getOSRMetadata();

    public void setOSRMetadata(Object var1);

    @Deprecated(since="22.2")
    default public void copyIntoOSRFrame(VirtualFrame osrFrame, VirtualFrame parentFrame, int target) {
        NodeAccessor.RUNTIME.transferOSRFrame(this, parentFrame, osrFrame, target);
    }

    default public void copyIntoOSRFrame(VirtualFrame osrFrame, VirtualFrame parentFrame, int target, Object targetMetadata) {
        NodeAccessor.RUNTIME.transferOSRFrame(this, parentFrame, osrFrame, target, targetMetadata);
    }

    default public void restoreParentFrame(VirtualFrame osrFrame, VirtualFrame parentFrame) {
        NodeAccessor.RUNTIME.restoreOSRFrame(this, osrFrame, parentFrame);
    }

    default public void prepareOSR(int target) {
    }

    public static boolean pollOSRBackEdge(BytecodeOSRNode osrNode) {
        if (!CompilerDirectives.inInterpreter()) {
            return false;
        }
        if (!1.$assertionsDisabled && !BytecodeOSRValidation.validateNode(osrNode)) {
            throw new AssertionError();
        }
        return NodeAccessor.RUNTIME.pollBytecodeOSRBackEdge(osrNode);
    }

    public static Object tryOSR(BytecodeOSRNode osrNode, int target, Object interpreterState, Runnable beforeTransfer, VirtualFrame parentFrame) {
        CompilerAsserts.neverPartOfCompilation();
        return NodeAccessor.RUNTIME.tryBytecodeOSR(osrNode, target, interpreterState, beforeTransfer, parentFrame);
    }

    default public Object[] storeParentFrameInArguments(VirtualFrame parentFrame) {
        CompilerAsserts.neverPartOfCompilation();
        return new Object[]{parentFrame};
    }

    default public Frame restoreParentFrameFromArguments(Object[] arguments) {
        return (Frame)arguments[0];
    }

    static {
        if (1.$assertionsDisabled) {
            // empty if block
        }
    }
}

