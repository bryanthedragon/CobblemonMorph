
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.js.nodes.JavaScriptNode;

public abstract class VarWrapperNode
extends JavaScriptNode {
    protected VarWrapperNode() {
    }

    public abstract JavaScriptNode getDelegateNode();
}

