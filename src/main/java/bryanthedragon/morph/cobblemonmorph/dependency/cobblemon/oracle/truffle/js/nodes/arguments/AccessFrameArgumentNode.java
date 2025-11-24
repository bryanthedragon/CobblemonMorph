
package com.oracle.truffle.js.nodes.arguments;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.runtime.JSArguments;
import java.util.Set;

public final class AccessFrameArgumentNode
extends JavaScriptNode
implements RepeatableNode {
    @Node.Child
    private ScopeFrameNode accessFrame;
    private final int argIndex;

    private AccessFrameArgumentNode(ScopeFrameNode accessFrame, int argIndex) {
        this.accessFrame = accessFrame;
        this.argIndex = argIndex;
    }

    public static JavaScriptNode create(ScopeFrameNode accessFrame, int argIndex) {
        return new AccessFrameArgumentNode(accessFrame, argIndex);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Frame parentFrame = this.accessFrame.executeFrame(frame);
        Object arg = JSArguments.getUserArgument(parentFrame.getArguments(), this.argIndex);
        assert (arg != null);
        return arg;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new AccessFrameArgumentNode(NodeUtil.cloneNode(this.accessFrame), this.argIndex);
    }
}

