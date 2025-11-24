
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.js.nodes.JavaScriptNode;

public interface SequenceNode {
    public JavaScriptNode[] getStatements();
}

