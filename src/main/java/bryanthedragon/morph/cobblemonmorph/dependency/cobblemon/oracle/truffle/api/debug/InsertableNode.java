package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.nodes.Node;

interface InsertableNode {
   void setParentOf(Node child);
}
