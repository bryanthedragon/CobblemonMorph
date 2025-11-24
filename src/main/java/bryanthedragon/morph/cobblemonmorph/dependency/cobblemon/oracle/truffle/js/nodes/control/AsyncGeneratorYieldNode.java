
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.WriteNode;
import com.oracle.truffle.js.nodes.control.AbstractAwaitNode;
import com.oracle.truffle.js.nodes.control.AsyncGeneratorYieldStarNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.ReturnException;
import com.oracle.truffle.js.nodes.control.ReturnNode;
import com.oracle.truffle.js.nodes.control.YieldResultNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.UserScriptException;
import com.oracle.truffle.js.runtime.objects.Completion;
import java.util.Set;

public class AsyncGeneratorYieldNode
extends AbstractAwaitNode
implements ResumableNode.WithIntState {
    @Node.Child
    protected ReturnNode returnNode;
    @Node.Child
    private YieldResultNode generatorYieldNode;

    protected AsyncGeneratorYieldNode(JSContext context, int stateSlot, JavaScriptNode expression, JSReadFrameSlotNode readAsyncContextNode, JSReadFrameSlotNode readYieldResultNode, ReturnNode returnNode) {
        super(context, stateSlot, expression, readAsyncContextNode, readYieldResultNode);
        this.returnNode = returnNode;
        this.generatorYieldNode = new YieldResultNode.ExceptionYieldResultNode();
    }

    public static AsyncGeneratorYieldNode createYield(JSContext context, int stateSlot, JavaScriptNode expression, JSReadFrameSlotNode readAsyncContextNode, JSReadFrameSlotNode readAsyncResultNode, ReturnNode returnNode) {
        return new AsyncGeneratorYieldNode(context, stateSlot, expression, readAsyncContextNode, readAsyncResultNode, returnNode);
    }

    public static AsyncGeneratorYieldNode createYieldStar(JSContext context, int stateSlot, JavaScriptNode expression, JSReadFrameSlotNode readAsyncContextNode, JSReadFrameSlotNode readAsyncResultNode, ReturnNode returnNode, int iteratorTempSlot) {
        return new AsyncGeneratorYieldStarNode(context, expression, stateSlot, readAsyncContextNode, readAsyncResultNode, returnNode, iteratorTempSlot);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        int state = this.getStateAsInt(frame, this.stateSlot);
        boolean awaitValue = true;
        int suspendedYield = 2;
        int awaitResumptionValue = 3;
        if (state == 0) {
            Object value2 = this.expression.execute(frame);
            this.setStateAsInt(frame, this.stateSlot, 1);
            return this.suspendAwait(frame, value2);
        }
        if (state == 1) {
            Object awaited = this.resumeAwait(frame);
            this.setStateAsInt(frame, this.stateSlot, 2);
            return this.suspendYield(frame, awaited);
        }
        assert (state >= 2);
        this.setStateAsInt(frame, this.stateSlot, 0);
        if (state == 2) {
            Completion completion = this.resumeYield(frame);
            if (completion.isNormal()) {
                return completion.getValue();
            }
            if (completion.isThrow()) {
                throw UserScriptException.create(completion.getValue(), this, this.context.getContextOptions().getStackTraceLimit());
            }
            assert (completion.isReturn());
            this.setStateAsInt(frame, this.stateSlot, 3);
            return this.suspendAwait(frame, completion.getValue());
        }
        assert (state == 3);
        Object awaited = this.resumeAwait(frame);
        return this.returnValue(frame, awaited);
    }

    protected final Object suspendYield(VirtualFrame frame, Object awaited) {
        return this.generatorYieldNode.generatorYield(frame, awaited);
    }

    protected final Completion resumeYield(VirtualFrame frame) {
        return (Completion)this.readAsyncResultNode.execute(frame);
    }

    protected final Object returnValue(VirtualFrame frame, Object value2) {
        assert (this.getStateAsInt(frame, this.stateSlot) == 0);
        if (this.returnNode instanceof ReturnNode.FrameReturnNode) {
            ((WriteNode)((Object)this.returnNode.expression)).executeWrite(frame, value2);
        }
        throw new ReturnException(value2);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return AsyncGeneratorYieldNode.createYield(this.context, this.stateSlot, AsyncGeneratorYieldNode.cloneUninitialized(this.expression, materializedTags), AsyncGeneratorYieldNode.cloneUninitialized(this.readAsyncContextNode, materializedTags), AsyncGeneratorYieldNode.cloneUninitialized(this.readAsyncResultNode, materializedTags), AsyncGeneratorYieldNode.cloneUninitialized(this.returnNode, materializedTags));
    }
}

