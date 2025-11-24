
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.control.AbstractAwaitNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

public class AsyncIteratorNextNode
extends AbstractAwaitNode
implements ResumableNode.WithIntState {
    @Node.Child
    private JSFunctionCallNode methodCallNode;
    @Node.Child
    private IsObjectNode isObjectNode;
    private final BranchProfile errorBranch = BranchProfile.create();

    protected AsyncIteratorNextNode(JSContext context, int stateSlot, JavaScriptNode iterator, JSReadFrameSlotNode asyncContextNode, JSReadFrameSlotNode asyncResultNode) {
        super(context, stateSlot, iterator, asyncContextNode, asyncResultNode);
        this.methodCallNode = JSFunctionCallNode.createCall();
        this.isObjectNode = IsObjectNode.create();
    }

    public static JavaScriptNode create(JSContext context, int stateSlot, JavaScriptNode iterator, JSReadFrameSlotNode asyncContextNode, JSReadFrameSlotNode asyncResultNode) {
        return new AsyncIteratorNextNode(context, stateSlot, iterator, asyncContextNode, asyncResultNode);
    }

    private Object executeBegin(VirtualFrame frame) {
        IteratorRecord iteratorRecord = (IteratorRecord)this.expression.execute(frame);
        Object next = iteratorRecord.getNextMethod();
        JSDynamicObject iterator = iteratorRecord.getIterator();
        Object nextResult = this.methodCallNode.executeCall(JSArguments.createZeroArg(iterator, next));
        this.setStateAsInt(frame, this.stateSlot, 1);
        return this.suspendAwait(frame, nextResult);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        int index = this.getStateAsIntAndReset(frame, this.stateSlot);
        if (index == 0) {
            return this.executeBegin(frame);
        }
        Object result = this.resumeAwait(frame);
        if (!this.isObjectNode.executeBoolean(result)) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorIterResultNotAnObject(result, this);
        }
        return result;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new AsyncIteratorNextNode(this.context, this.stateSlot, AsyncIteratorNextNode.cloneUninitialized(this.expression, materializedTags), AsyncIteratorNextNode.cloneUninitialized(this.readAsyncContextNode, materializedTags), AsyncIteratorNextNode.cloneUninitialized(this.readAsyncResultNode, materializedTags));
    }
}

