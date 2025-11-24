
package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.impl.DefaultCallTarget;
import com.oracle.truffle.api.nodes.IndirectCallNode;

final class DefaultIndirectCallNode
extends IndirectCallNode {
    DefaultIndirectCallNode() {
    }

    @Override
    public Object call(CallTarget target, Object ... arguments) {
        return ((DefaultCallTarget)target).callDirectOrIndirect(this, arguments);
    }
}

