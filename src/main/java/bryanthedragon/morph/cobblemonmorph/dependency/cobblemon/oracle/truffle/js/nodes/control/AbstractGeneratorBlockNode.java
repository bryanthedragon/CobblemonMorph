
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.AbstractBlockNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class AbstractGeneratorBlockNode
extends AbstractBlockNode {
    protected final int stateSlot;

    protected AbstractGeneratorBlockNode(JavaScriptNode[] statements, int stateSlot) {
        super(statements);
        this.stateSlot = stateSlot;
    }

    protected final int getStateAndReset(VirtualFrame frame) {
        int index = 0;
        if (frame.isInt(this.stateSlot)) {
            index = frame.getInt(this.stateSlot);
        } else assert (frame.isObject(this.stateSlot) && frame.getObject(this.stateSlot) == Undefined.instance);
        this.setState(frame, 0);
        return index;
    }

    protected final void setState(VirtualFrame frame, int index) {
        frame.setInt(this.stateSlot, index);
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        int index = this.getStateAndReset(frame);
        assert (index < this.getStatements().length);
        this.block.executeVoid(frame, index);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        int index = this.getStateAndReset(frame);
        assert (index < this.getStatements().length);
        return this.block.executeGeneric(frame, index);
    }

    @Override
    public void executeVoid(VirtualFrame frame, JavaScriptNode node, int index, int startIndex) {
        if (index < startIndex) {
            return;
        }
        try {
            node.executeVoid(frame);
        }
        catch (YieldException e) {
            this.setState(frame, index);
            throw e;
        }
    }

    @Override
    public Object executeGeneric(VirtualFrame frame, JavaScriptNode node, int index, int startIndex) {
        assert (index == this.getStatements().length - 1);
        try {
            return node.execute(frame);
        }
        catch (YieldException e) {
            this.setState(frame, index);
            throw e;
        }
    }
}

