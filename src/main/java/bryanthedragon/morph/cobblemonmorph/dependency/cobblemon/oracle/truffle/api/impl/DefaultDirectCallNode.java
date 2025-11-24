
package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.impl.DefaultCallTarget;
import com.oracle.truffle.api.nodes.DirectCallNode;

public final class DefaultDirectCallNode
extends DirectCallNode {
    private boolean inliningForced;

    public DefaultDirectCallNode(CallTarget target) {
        super(target);
    }

    @Override
    public Object call(Object ... arguments) {
        return ((DefaultCallTarget)this.callTarget).callDirectOrIndirect(this, arguments);
    }

    @Override
    public void forceInlining() {
        this.inliningForced = true;
    }

    @Override
    public boolean isInliningForced() {
        return this.inliningForced;
    }

    @Override
    public CallTarget getClonedCallTarget() {
        return null;
    }

    @Override
    public boolean cloneCallTarget() {
        return false;
    }

    @Override
    public boolean isCallTargetCloningAllowed() {
        return false;
    }

    @Override
    public boolean isInlinable() {
        return false;
    }
}

