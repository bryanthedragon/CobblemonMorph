
package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.nodes.Node;

public interface Truncatable {
    public void setTruncate();

    public static void truncate(Node node) {
        if (node instanceof Truncatable) {
            ((Truncatable)((Object)node)).setTruncate();
        }
    }
}

