
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.AbstractGeneratorBlockNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import java.util.Set;

public final class GeneratorExprBlockNode
extends AbstractGeneratorBlockNode {
    GeneratorExprBlockNode(JavaScriptNode[] statements, int stateSlot) {
        super(statements, stateSlot);
    }

    public static JavaScriptNode create(JavaScriptNode[] statements, int stateSlot) {
        return new GeneratorExprBlockNode(statements, stateSlot);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
        int index = this.getStateAndReset(frame);
        assert (index < this.getStatements().length);
        return this.block.executeBoolean(frame, index);
    }

    @Override
    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        int index = this.getStateAndReset(frame);
        assert (index < this.getStatements().length);
        return this.block.executeInt(frame, index);
    }

    @Override
    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        int index = this.getStateAndReset(frame);
        assert (index < this.getStatements().length);
        return this.block.executeDouble(frame, index);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frame, JavaScriptNode node, int index, int argument) throws UnexpectedResultException {
        assert (index == this.getStatements().length - 1);
        try {
            return node.executeBoolean(frame);
        }
        catch (YieldException e) {
            this.setState(frame, index);
            throw e;
        }
    }

    @Override
    public int executeInt(VirtualFrame frame, JavaScriptNode node, int index, int argument) throws UnexpectedResultException {
        assert (index == this.getStatements().length - 1);
        try {
            return node.executeInt(frame);
        }
        catch (YieldException e) {
            this.setState(frame, index);
            throw e;
        }
    }

    @Override
    public double executeDouble(VirtualFrame frame, JavaScriptNode node, int index, int argument) throws UnexpectedResultException {
        assert (index == this.getStatements().length - 1);
        try {
            return node.executeDouble(frame);
        }
        catch (YieldException e) {
            this.setState(frame, index);
            throw e;
        }
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new GeneratorExprBlockNode(GeneratorExprBlockNode.cloneUninitialized(this.getStatements(), materializedTags), this.stateSlot);
    }
}

