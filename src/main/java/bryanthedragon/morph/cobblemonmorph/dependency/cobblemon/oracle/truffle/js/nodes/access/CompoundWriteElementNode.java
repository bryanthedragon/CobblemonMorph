
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.RequireObjectCoercibleNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import java.util.Set;

public class CompoundWriteElementNode
extends WriteElementNode {
    @Node.Child
    private JSWriteFrameSlotNode writeIndexNode;
    @Node.Child
    private RequireObjectCoercibleNode requireObjectCoercibleNode;

    public static CompoundWriteElementNode create(JavaScriptNode targetNode, JavaScriptNode indexNode, JavaScriptNode valueNode, JSWriteFrameSlotNode writeIndexNode, JSContext context, boolean isStrict) {
        return CompoundWriteElementNode.create(targetNode, indexNode, valueNode, writeIndexNode, context, isStrict, false);
    }

    private static CompoundWriteElementNode create(JavaScriptNode targetNode, JavaScriptNode indexNode, JavaScriptNode valueNode, JSWriteFrameSlotNode writeIndexNode, JSContext context, boolean isStrict, boolean writeOwn) {
        return new CompoundWriteElementNode(targetNode, indexNode, valueNode, writeIndexNode, context, isStrict, writeOwn);
    }

    protected CompoundWriteElementNode(JavaScriptNode targetNode, JavaScriptNode indexNode, JavaScriptNode valueNode, JSWriteFrameSlotNode writeIndexNode, JSContext context, boolean isStrict, boolean writeOwn) {
        super(targetNode, indexNode, valueNode, context, isStrict, writeOwn);
        this.writeIndexNode = writeIndexNode;
        this.requireObjectCoercibleNode = RequireObjectCoercibleNode.create();
    }

    @Override
    protected Object executeWithTargetAndIndex(VirtualFrame frame, Object target, Object index, Object receiver) {
        Object convertedIndex = this.toArrayIndex(index);
        this.writeIndex(frame, convertedIndex);
        return super.executeWithTargetAndIndex(frame, target, convertedIndex, receiver);
    }

    @Override
    protected Object executeWithTargetAndIndex(VirtualFrame frame, Object target, int index, Object receiver) {
        this.writeIndex(frame, index);
        return super.executeWithTargetAndIndex(frame, target, index, receiver);
    }

    @Override
    protected int executeWithTargetAndIndexInt(VirtualFrame frame, Object target, Object index, Object receiver) throws UnexpectedResultException {
        Object convertedIndex = this.toArrayIndex(index);
        this.writeIndex(frame, convertedIndex);
        return super.executeWithTargetAndIndexInt(frame, target, convertedIndex, receiver);
    }

    @Override
    protected int executeWithTargetAndIndexInt(VirtualFrame frame, Object target, int index, Object receiver) throws UnexpectedResultException {
        this.writeIndex(frame, index);
        return super.executeWithTargetAndIndexInt(frame, target, index, receiver);
    }

    @Override
    protected double executeWithTargetAndIndexDouble(VirtualFrame frame, Object target, Object index, Object receiver) throws UnexpectedResultException {
        Object convertedIndex = this.toArrayIndex(index);
        this.writeIndex(frame, convertedIndex);
        return super.executeWithTargetAndIndexDouble(frame, target, convertedIndex, receiver);
    }

    @Override
    protected double executeWithTargetAndIndexDouble(VirtualFrame frame, Object target, int index, Object receiver) throws UnexpectedResultException {
        this.writeIndex(frame, index);
        return super.executeWithTargetAndIndexDouble(frame, target, index, receiver);
    }

    private void writeIndex(VirtualFrame frame, Object index) {
        if (this.writeIndexNode != null) {
            this.writeIndexNode.executeWrite(frame, index);
        }
    }

    private void writeIndex(VirtualFrame frame, int index) {
        if (this.writeIndexNode != null) {
            this.writeIndexNode.executeWrite(frame, index);
        }
    }

    @Override
    protected void requireObjectCoercible(Object target, int index) {
        try {
            this.requireObjectCoercibleNode.executeVoid(target);
        }
        catch (JSException e) {
            throw Errors.createTypeErrorCannotSetProperty(index, target, (Node)this);
        }
    }

    @Override
    protected void requireObjectCoercible(Object target, Object index) {
        try {
            this.requireObjectCoercibleNode.executeVoid(target);
        }
        catch (JSException e) {
            throw Errors.createTypeErrorCannotSetProperty(index, target, (Node)this);
        }
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return CompoundWriteElementNode.create(CompoundWriteElementNode.cloneUninitialized(this.targetNode, materializedTags), CompoundWriteElementNode.cloneUninitialized(this.indexNode, materializedTags), CompoundWriteElementNode.cloneUninitialized(this.valueNode, materializedTags), CompoundWriteElementNode.cloneUninitialized(this.writeIndexNode, materializedTags), this.getContext(), this.isStrict(), this.writeOwn());
    }

    @Override
    protected WriteElementNode createMaterialized(JavaScriptNode newTarget, JavaScriptNode newIndex, JavaScriptNode newValue) {
        return CompoundWriteElementNode.create(newTarget, newIndex, newValue, this.writeIndexNode, this.getContext(), this.isStrict(), this.writeOwn());
    }
}

