
package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.nodes.BytecodeOSRNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;

final class BytecodeOSRValidation {
    private BytecodeOSRValidation() {
    }

    static boolean validateNode(BytecodeOSRNode node) {
        if (!(node instanceof Node)) {
            throw new ClassCastException(String.format("%s must be of type Node.", node.getClass()));
        }
        Node osrNode = (Node)((Object)node);
        RootNode root = osrNode.getRootNode();
        if (root == null) {
            throw new AssertionError((Object)String.format("%s was not adopted but executed.", node.getClass()));
        }
        return true;
    }
}

