
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import java.util.Set;

public class JSTargetableWrapperNode
extends JSTargetableNode {
    @Node.Child
    private JavaScriptNode delegate;
    @Node.Child
    private JavaScriptNode targetNode;

    protected JSTargetableWrapperNode(JavaScriptNode delegate, JavaScriptNode targetNode) {
        this.delegate = delegate;
        this.targetNode = targetNode;
    }

    public static JSTargetableNode create(JavaScriptNode delegate, JavaScriptNode target) {
        return new JSTargetableWrapperNode(delegate, target);
    }

    public JavaScriptNode getDelegate() {
        return this.delegate;
    }

    @Override
    public Object executeWithTarget(VirtualFrame frame, Object target) {
        return this.delegate.execute(frame);
    }

    @Override
    public int executeIntWithTarget(VirtualFrame frame, Object target) throws UnexpectedResultException {
        return this.delegate.executeInt(frame);
    }

    @Override
    public double executeDoubleWithTarget(VirtualFrame frame, Object target) throws UnexpectedResultException {
        return this.delegate.executeDouble(frame);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.delegate.execute(frame);
    }

    @Override
    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        return this.delegate.executeInt(frame);
    }

    @Override
    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        return this.delegate.executeDouble(frame);
    }

    @Override
    public Object evaluateTarget(VirtualFrame frame) {
        return this.targetNode.execute(frame);
    }

    @Override
    public JavaScriptNode getTarget() {
        return this.targetNode;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new JSTargetableWrapperNode(JSTargetableWrapperNode.cloneUninitialized(this.delegate, materializedTags), JSTargetableWrapperNode.cloneUninitialized(this.targetNode, materializedTags));
    }
}

