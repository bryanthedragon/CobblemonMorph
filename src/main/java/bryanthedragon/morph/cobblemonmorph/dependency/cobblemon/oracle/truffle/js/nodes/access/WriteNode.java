
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInterface;
import com.oracle.truffle.js.nodes.JavaScriptNode;

public interface WriteNode
extends NodeInterface {
    public void executeWrite(VirtualFrame var1, Object var2);

    public JavaScriptNode getRhs();
}

