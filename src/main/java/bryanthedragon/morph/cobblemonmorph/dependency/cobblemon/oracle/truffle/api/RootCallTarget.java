
package com.oracle.truffle.api;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.nodes.RootNode;

public interface RootCallTarget
extends CallTarget {
    public RootNode getRootNode();
}

