
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.nodes.Node;

interface InsertableNode {
    public void setParentOf(Node var1);
}

