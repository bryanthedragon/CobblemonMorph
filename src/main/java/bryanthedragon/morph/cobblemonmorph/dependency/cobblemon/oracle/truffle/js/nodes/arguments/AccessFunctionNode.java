
package com.oracle.truffle.js.nodes.arguments;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import java.util.Set;

public final class AccessFunctionNode
extends JavaScriptNode
implements RepeatableNode {
    AccessFunctionNode() {
    }

    public static AccessFunctionNode create() {
        return new AccessFunctionNode();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return JSFrameUtil.getFunctionObject(frame);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return AccessFunctionNode.create();
    }
}

