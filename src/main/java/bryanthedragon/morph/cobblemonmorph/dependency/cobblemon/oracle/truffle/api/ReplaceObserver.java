package com.oracle.truffle.api;

import com.oracle.truffle.api.nodes.Node;

public interface ReplaceObserver {
   boolean nodeReplaced(Node oldNode, Node newNode, CharSequence reason);
}
