
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;

@NodeInfo(cost=NodeCost.NONE)
public abstract class ExecutionEventNode
extends Node {
    protected ExecutionEventNode() {
    }

    protected void onEnter(VirtualFrame frame) {
    }

    protected void onInputValue(VirtualFrame frame, EventContext inputContext, int inputIndex, Object inputValue) {
    }

    protected void onReturnValue(VirtualFrame frame, Object result) {
    }

    protected void onReturnExceptional(VirtualFrame frame, Throwable exception) {
    }

    protected Object onUnwind(VirtualFrame frame, Object info) {
        return null;
    }

    protected void onDispose(VirtualFrame frame) {
    }

    protected final EventContext getInputContext(int index) {
        if (index < 0 || index >= this.getInputCount()) {
            CompilerDirectives.transferToInterpreter();
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        ProbeNode.EventProviderWithInputChainNode node = this.getChainNode();
        if (node == null) {
            CompilerDirectives.transferToInterpreter();
            throw new AssertionError((Object)"should not be reachable as input count should be 0");
        }
        return node.getInputContext(index);
    }

    protected final int getInputCount() {
        ProbeNode.EventProviderWithInputChainNode node = this.getChainNode();
        if (node == null) {
            return 0;
        }
        return node.getInputCount();
    }

    protected final void saveInputValue(VirtualFrame frame, int inputIndex, Object inputValue) {
        ProbeNode.EventProviderWithInputChainNode node = this.getChainNode();
        if (node != null) {
            node.saveInputValue(frame, inputIndex, inputValue);
        }
    }

    protected final Object[] getSavedInputValues(VirtualFrame frame) {
        ProbeNode.EventProviderWithInputChainNode node = this.getChainNode();
        if (node != null) {
            return node.getSavedInputValues(frame);
        }
        return ProbeNode.EventProviderWithInputChainNode.EMPTY_ARRAY;
    }

    private ProbeNode.EventProviderWithInputChainNode getChainNode() {
        Node parent = this.getParent();
        if (parent instanceof ProbeNode.EventProviderWithInputChainNode) {
            return (ProbeNode.EventProviderWithInputChainNode)parent;
        }
        return null;
    }
}

