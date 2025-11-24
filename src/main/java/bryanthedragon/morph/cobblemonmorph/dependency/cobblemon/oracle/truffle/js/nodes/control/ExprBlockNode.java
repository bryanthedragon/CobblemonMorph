
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.AbstractBlockNode;
import com.oracle.truffle.js.nodes.control.SequenceNode;
import java.util.Set;

public final class ExprBlockNode
extends AbstractBlockNode
implements SequenceNode {
    ExprBlockNode(JavaScriptNode[] statements) {
        super(statements);
    }

    public static JavaScriptNode createExprBlock(JavaScriptNode[] statements) {
        return ExprBlockNode.filterStatements(statements, true);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
        return this.block.executeBoolean(frame, 0);
    }

    @Override
    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        return this.block.executeInt(frame, 0);
    }

    @Override
    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        return this.block.executeDouble(frame, 0);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frame, JavaScriptNode node, int index, int argument) throws UnexpectedResultException {
        return node.executeBoolean(frame);
    }

    @Override
    public int executeInt(VirtualFrame frame, JavaScriptNode node, int index, int argument) throws UnexpectedResultException {
        return node.executeInt(frame);
    }

    @Override
    public double executeDouble(VirtualFrame frame, JavaScriptNode node, int index, int argument) throws UnexpectedResultException {
        return node.executeDouble(frame);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new ExprBlockNode(ExprBlockNode.cloneUninitialized(this.getStatements(), materializedTags));
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return this.getStatements()[this.getStatements().length - 1].isResultAlwaysOfType(clazz);
    }
}

