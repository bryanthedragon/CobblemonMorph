
package com.oracle.truffle.api;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameInstanceVisitor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.RepeatingNode;

public interface TruffleRuntime {
    public String getName();

    public DirectCallNode createDirectCallNode(CallTarget var1);

    public LoopNode createLoopNode(RepeatingNode var1);

    public IndirectCallNode createIndirectCallNode();

    public Assumption createAssumption();

    public Assumption createAssumption(String var1);

    public VirtualFrame createVirtualFrame(Object[] var1, FrameDescriptor var2);

    public MaterializedFrame createMaterializedFrame(Object[] var1);

    public MaterializedFrame createMaterializedFrame(Object[] var1, FrameDescriptor var2);

    default public <T> T iterateFrames(FrameInstanceVisitor<T> visitor) {
        return this.iterateFrames(visitor, 0);
    }

    default public <T> T iterateFrames(FrameInstanceVisitor<T> visitor, int skipFrames) {
        throw new AbstractMethodError();
    }

    public <T> T getCapability(Class<T> var1);

    public void notifyTransferToInterpreter();

    public boolean isProfilingEnabled();
}

