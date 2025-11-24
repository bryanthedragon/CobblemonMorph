
package com.oracle.truffle.api.frame;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.nodes.Node;

public interface FrameInstance {
    public Frame getFrame(FrameAccess var1);

    public boolean isVirtualFrame();

    default public int getCompilationTier() {
        return 0;
    }

    default public boolean isCompilationRoot() {
        return true;
    }

    public Node getCallNode();

    public CallTarget getCallTarget();

    public static enum FrameAccess {
        READ_ONLY,
        READ_WRITE,
        MATERIALIZE;

    }
}

