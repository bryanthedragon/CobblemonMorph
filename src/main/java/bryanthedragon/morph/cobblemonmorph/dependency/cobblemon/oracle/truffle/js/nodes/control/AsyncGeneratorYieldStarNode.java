
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.GetIteratorNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.IteratorCompleteNode;
import com.oracle.truffle.js.nodes.access.IteratorNextNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.control.AsyncGeneratorYieldNode;
import com.oracle.truffle.js.nodes.control.ReturnNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.Completion;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

class AsyncGeneratorYieldStarNode
extends AsyncGeneratorYieldNode {
    private final int iteratorTempSlot;
    @Node.Child
    private GetIteratorNode getIteratorNode;
    @Node.Child
    private IteratorNextNode iteratorNextNode;
    @Node.Child
    private IteratorCompleteNode iteratorCompleteNode;
    @Node.Child
    private IteratorValueNode iteratorValueNode;
    @Node.Child
    private GetMethodNode getThrowMethodNode;
    @Node.Child
    private GetMethodNode getReturnMethodNode;
    @Node.Child
    private JSFunctionCallNode callThrowNode;
    @Node.Child
    private JSFunctionCallNode callReturnNode;
    private final BranchProfile throwMethodMissingBranch = BranchProfile.create();

    protected AsyncGeneratorYieldStarNode(JSContext context, JavaScriptNode expression, int stateSlot, JSReadFrameSlotNode readAsyncContextNode, JSReadFrameSlotNode readYieldResultNode, ReturnNode returnNode, int iteratorTempSlot) {
        super(context, stateSlot, expression, readAsyncContextNode, readYieldResultNode, returnNode);
        this.iteratorTempSlot = iteratorTempSlot;
        this.getIteratorNode = GetIteratorNode.createAsync(context, null);
        this.iteratorNextNode = IteratorNextNode.create();
        this.iteratorCompleteNode = IteratorCompleteNode.create(context);
        this.iteratorValueNode = IteratorValueNode.create();
        this.getThrowMethodNode = GetMethodNode.create(context, Strings.THROW);
        this.getReturnMethodNode = GetMethodNode.create(context, Strings.RETURN);
        this.callThrowNode = JSFunctionCallNode.createCall();
        this.callReturnNode = JSFunctionCallNode.createCall();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object execute(VirtualFrame frame) {
        IteratorRecord iteratorRecord;
        int state = this.getStateAsInt(frame, this.stateSlot);
        boolean loopBegin = true;
        int normalOrThrowAwaitInnerResult = 2;
        int returnAwaitInnerReturnResult = 3;
        int asyncGeneratorYieldInnerResult = 4;
        int asyncGeneratorYieldInnerResultSuspendedYield = 5;
        int asyncGeneratorYieldInnerResultReturn = 6;
        int returnAwaitReceivedValue = 7;
        int throwAwaitReturnResult = 8;
        if (state == 0) {
            iteratorRecord = this.getIteratorNode.execute(this.expression.execute(frame));
            frame.setObject(this.iteratorTempSlot, iteratorRecord);
            state = 1;
        } else {
            iteratorRecord = (IteratorRecord)frame.getObject(this.iteratorTempSlot);
        }
        JSDynamicObject iterator = iteratorRecord.getIterator();
        Completion received = Completion.forNormal(Undefined.instance);
        while (true) {
            switch (state) {
                case 1: {
                    Object innerReturnResult;
                    Object innerResult;
                    if (received.isNormal()) {
                        Object innerResult2 = this.iteratorNextNode.execute(iteratorRecord, received.getValue());
                        this.awaitWithNext(frame, innerResult2, 2);
                        break;
                    }
                    if (received.isThrow()) {
                        Object returnResult;
                        Object throwMethod = this.getThrowMethodNode.executeWithTarget(iterator);
                        if (throwMethod != Undefined.instance) {
                            innerResult = this.callThrowMethod(throwMethod, iterator, received.getValue());
                            this.awaitWithNext(frame, innerResult, 2);
                            break;
                        }
                        this.throwMethodMissingBranch.enter();
                        Object returnMethod = this.getReturnMethodNode.executeWithTarget(iterator);
                        if (returnMethod == Undefined.instance) throw Errors.createTypeErrorYieldStarThrowMethodMissing(this);
                        try {
                            returnResult = this.callReturnNode.executeCall(JSArguments.createZeroArg(iterator, returnMethod));
                        }
                        catch (AbstractTruffleException e) {
                            throw Errors.createTypeErrorYieldStarThrowMethodMissing(this);
                        }
                        this.awaitWithNext(frame, returnResult, 8);
                        throw Errors.createTypeErrorYieldStarThrowMethodMissing(this);
                    }
                    assert (received.isReturn());
                    Object returnMethod = this.getReturnMethodNode.executeWithTarget(iterator);
                    if (returnMethod != Undefined.instance) {
                        innerReturnResult = this.callReturnMethod(returnMethod, iterator, received.getValue());
                        this.awaitWithNext(frame, innerReturnResult, 3);
                        break;
                    }
                    this.awaitWithNext(frame, received.getValue(), 7);
                    break;
                }
                case 2: {
                    Object awaited = this.resumeAwait(frame);
                    Object innerResult = this.checkcastIterResult(awaited);
                    boolean done = this.iteratorCompleteNode.execute(innerResult);
                    if (done) {
                        this.reset(frame);
                        return this.iteratorValueNode.execute(innerResult);
                    }
                    Object iteratorValue = this.iteratorValueNode.execute(innerResult);
                    this.awaitWithNext(frame, iteratorValue, 4);
                    break;
                }
                case 3: {
                    Object awaited = this.resumeAwait(frame);
                    Object innerReturnResult = this.checkcastIterResult(awaited);
                    boolean done = this.iteratorCompleteNode.execute(innerReturnResult);
                    if (done) {
                        this.reset(frame);
                        return this.returnValue(frame, this.iteratorValueNode.execute(innerReturnResult));
                    }
                    Object iteratorValue = this.iteratorValueNode.execute(innerReturnResult);
                    this.awaitWithNext(frame, iteratorValue, 4);
                    break;
                }
                case 4: {
                    Object awaited = this.resumeAwait(frame);
                    this.yieldWithNext(frame, awaited, 5);
                    break;
                }
                case 5: {
                    Completion resumptionValue = this.resumeYield(frame);
                    if (!resumptionValue.isReturn()) {
                        received = resumptionValue;
                        state = 1;
                        break;
                    }
                    assert (resumptionValue.isReturn());
                    this.awaitWithNext(frame, resumptionValue.getValue(), 6);
                    break;
                }
                case 6: {
                    Completion returnValue = this.resumeYield(frame);
                    if (returnValue.isNormal()) {
                        received = Completion.forReturn(returnValue.getValue());
                    } else {
                        assert (returnValue.isThrow());
                        received = returnValue;
                    }
                    state = 1;
                    break;
                }
                case 7: {
                    Object awaited = this.resumeAwait(frame);
                    this.reset(frame);
                    return this.returnValue(frame, awaited);
                }
                case 8: {
                    this.throwMethodMissingBranch.enter();
                    this.resumeAwait(frame);
                    throw Errors.createTypeErrorYieldStarThrowMethodMissing(this);
                }
                default: {
                    throw Errors.shouldNotReachHere();
                }
            }
            assert (state == 1);
        }
    }

    private void awaitWithNext(VirtualFrame frame, Object value2, int nextState) {
        this.setStateAsInt(frame, this.stateSlot, nextState);
        this.suspendAwait(frame, value2);
    }

    private Object yieldWithNext(VirtualFrame frame, Object value2, int nextState) {
        this.setStateAsInt(frame, this.stateSlot, nextState);
        return this.suspendYield(frame, value2);
    }

    private void reset(VirtualFrame frame) {
        this.setStateAsInt(frame, this.stateSlot, 0);
        frame.setObject(this.iteratorTempSlot, Undefined.instance);
    }

    private Object callThrowMethod(Object throwMethod, JSDynamicObject iterator, Object received) {
        return this.callThrowNode.executeCall(JSArguments.createOneArg(iterator, throwMethod, received));
    }

    private Object callReturnMethod(Object returnMethod, JSDynamicObject iterator, Object received) {
        return this.callReturnNode.executeCall(JSArguments.createOneArg(iterator, returnMethod, received));
    }

    private JSDynamicObject checkcastIterResult(Object iterResult) {
        if (!JSRuntime.isObject(iterResult)) {
            throw Errors.createTypeErrorIterResultNotAnObject(iterResult, this);
        }
        return (JSDynamicObject)iterResult;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return AsyncGeneratorYieldStarNode.createYieldStar(this.context, this.stateSlot, AsyncGeneratorYieldStarNode.cloneUninitialized(this.expression, materializedTags), AsyncGeneratorYieldStarNode.cloneUninitialized(this.readAsyncContextNode, materializedTags), AsyncGeneratorYieldStarNode.cloneUninitialized(this.readAsyncResultNode, materializedTags), AsyncGeneratorYieldStarNode.cloneUninitialized(this.returnNode, materializedTags), this.iteratorTempSlot);
    }
}

