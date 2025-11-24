
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.control.AbstractAwaitNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.instrumentation.JSMaterializedInvokeTargetableNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.Set;

public class AwaitNode
extends AbstractAwaitNode
implements ResumableNode.WithIntState {
    @Node.Child
    private JSTargetableNode materializedInputNode;

    protected AwaitNode(JSContext context, int stateSlot, JavaScriptNode expression, JSReadFrameSlotNode readAsyncContextNode, JSReadFrameSlotNode readAsyncResultNode, JSTargetableNode materializedInputNode) {
        super(context, stateSlot, expression, readAsyncContextNode, readAsyncResultNode);
        this.materializedInputNode = materializedInputNode;
    }

    public static JavaScriptNode create(JSContext context, int stateSlot, JavaScriptNode expression, JSReadFrameSlotNode readAsyncContextNode, JSReadFrameSlotNode readAsyncResultNode) {
        return new AwaitNode(context, stateSlot, expression, readAsyncContextNode, readAsyncResultNode, null);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        int index = this.getStateAsInt(frame, this.stateSlot);
        if (index == 0) {
            Object value2 = this.expression.execute(frame);
            this.setStateAsInt(frame, this.stateSlot, 1);
            return this.suspendAwait(frame, value2);
        }
        this.setStateAsInt(frame, this.stateSlot, 0);
        return this.resumeAwait(frame);
    }

    @Override
    protected void echoInput(VirtualFrame frame, Object value2) {
        if (this.materializedInputNode != null) {
            this.materializedInputNode.executeWithTarget(frame, value2);
        }
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        JavaScriptNode expressionCopy = AwaitNode.cloneUninitialized(this.expression, materializedTags);
        JSReadFrameSlotNode asyncResultCopy = AwaitNode.cloneUninitialized(this.readAsyncResultNode, materializedTags);
        JSReadFrameSlotNode asyncContextCopy = AwaitNode.cloneUninitialized(this.readAsyncContextNode, materializedTags);
        return AwaitNode.create(this.context, this.stateSlot, expressionCopy, asyncContextCopy, asyncResultCopy);
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (this.materializationNeeded() && materializedTags.contains(JSTags.ControlFlowBranchTag.class)) {
            JSTargetableNode materializedInput = JSMaterializedInvokeTargetableNode.EchoTargetValueNode.create();
            AwaitNode materialized = new AwaitNode(this.context, this.stateSlot, AwaitNode.cloneUninitialized(this.expression, materializedTags), AwaitNode.cloneUninitialized(this.readAsyncContextNode, materializedTags), AwaitNode.cloneUninitialized(this.readAsyncResultNode, materializedTags), materializedInput);
            AwaitNode.transferSourceSectionAndTags(this, materialized);
            return materialized;
        }
        return this;
    }

    private boolean materializationNeeded() {
        return this.materializedInputNode == null;
    }
}

