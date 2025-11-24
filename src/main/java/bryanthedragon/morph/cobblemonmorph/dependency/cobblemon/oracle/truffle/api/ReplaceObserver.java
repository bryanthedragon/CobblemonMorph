
package com.oracle.truffle.api;

import com.oracle.truffle.api.nodes.Node;

public interface ReplaceObserver {
    public boolean nodeReplaced(Node var1, Node var2, CharSequence var3);
}

