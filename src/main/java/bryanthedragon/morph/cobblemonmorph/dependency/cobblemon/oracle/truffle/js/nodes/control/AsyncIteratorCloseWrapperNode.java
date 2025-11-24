
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ControlFlowException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.control.AbstractAwaitNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.YieldException;
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

public class AsyncIteratorCloseWrapperNode
extends AbstractAwaitNode
implements ResumableNode.WithObjectState {
    @Node.Child
    private JavaScriptNode loopNode;
    @Node.Child
    private GetMethodNode getReturnNode;
    @Node.Child
    private JSFunctionCallNode returnMethodCallNode;
    @Node.Child
    private JavaScriptNode iteratorNode;
    private final BranchProfile errorBranch = BranchProfile.create();
    private final BranchProfile throwBranch = BranchProfile.create();
    private final BranchProfile exitBranch = BranchProfile.create();
    private final BranchProfile notDoneBranch = BranchProfile.create();

    protected AsyncIteratorCloseWrapperNode(JSContext context, int stateSlot, JavaScriptNode loopNode, JavaScriptNode iteratorNode, JSReadFrameSlotNode asyncContextNode, JSReadFrameSlotNode asyncResultNode) {
        super(context, stateSlot, null, asyncContextNode, asyncResultNode);
        this.loopNode = loopNode;
        this.iteratorNode = iteratorNode;
        this.getReturnNode = GetMethodNode.create(context, Strings.RETURN);
    }

    public static JavaScriptNode create(JSContext context, int stateSlot, JavaScriptNode loopNode, JavaScriptNode iterator, JSReadFrameSlotNode asyncContextNode, JSReadFrameSlotNode asyncResultNode) {
        return new AsyncIteratorCloseWrapperNode(context, stateSlot, loopNode, iterator, asyncContextNode, asyncResultNode);
    }

    private Object executeBegin(VirtualFrame frame) {
        Completion completion;
        Object innerResult;
        block10: {
            Object result;
            try {
                result = this.loopNode.execute(frame);
            }
            catch (YieldException e) {
                throw e;
            }
            catch (ControlFlowException e) {
                this.exitBranch.enter();
                IteratorRecord iteratorRecord = this.getIteratorRecord(frame);
                JSDynamicObject iterator = iteratorRecord.getIterator();
                Object returnMethod = this.getReturnNode.executeWithTarget(iterator);
                if (returnMethod != Undefined.instance) {
                    innerResult = this.getReturnMethodCallNode().executeCall(JSArguments.createZeroArg(iterator, returnMethod));
                    completion = Completion.forReturn(e);
                    break block10;
                }
                throw e;
            }
            catch (AbstractTruffleException e) {
                block11: {
                    this.throwBranch.enter();
                    IteratorRecord iteratorRecord = this.getIteratorRecord(frame);
                    JSDynamicObject iterator = iteratorRecord.getIterator();
                    try {
                        Object returnMethod = this.getReturnNode.executeWithTarget(iterator);
                        if (returnMethod == Undefined.instance) break block11;
                        innerResult = this.getReturnMethodCallNode().executeCall(JSArguments.createZeroArg(iterator, returnMethod));
                        completion = Completion.forThrow(e);
                        break block10;
                    }
                    catch (AbstractTruffleException abstractTruffleException) {
                        // empty catch block
                    }
                }
                throw e;
            }
            IteratorRecord iteratorRecord = this.getIteratorRecord(frame);
            if (iteratorRecord.isDone()) {
                return result;
            }
            this.notDoneBranch.enter();
            JSDynamicObject iterator = iteratorRecord.getIterator();
            Object returnMethod = this.getReturnNode.executeWithTarget(iterator);
            if (returnMethod != Undefined.instance) {
                innerResult = this.getReturnMethodCallNode().executeCall(JSArguments.createZeroArg(iterator, returnMethod));
                completion = Completion.forNormal(result);
            } else {
                return result;
            }
        }
        this.setState(frame, this.stateSlot, completion);
        return this.suspendAwait(frame, innerResult);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object state = this.getState(frame, this.stateSlot);
        if (state == Undefined.instance) {
            return this.executeBegin(frame);
        }
        this.resetState(frame, this.stateSlot);
        Completion completion = (Completion)state;
        if (completion.isThrow()) {
            throw JSRuntime.rethrow((Throwable)completion.getValue());
        }
        Object innerResult = this.resumeAwait(frame);
        if (!JSDynamicObject.isJSDynamicObject(innerResult)) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorIterResultNotAnObject(innerResult, this);
        }
        if (completion.isAbrupt()) {
            throw JSRuntime.rethrow((Throwable)completion.getValue());
        }
        return completion.getValue();
    }

    private IteratorRecord getIteratorRecord(VirtualFrame frame) {
        return (IteratorRecord)this.iteratorNode.execute(frame);
    }

    private JSFunctionCallNode getReturnMethodCallNode() {
        if (this.returnMethodCallNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.returnMethodCallNode = this.insert(JSFunctionCallNode.createCall());
        }
        return this.returnMethodCallNode;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new AsyncIteratorCloseWrapperNode(this.context, this.stateSlot, AsyncIteratorCloseWrapperNode.cloneUninitialized(this.loopNode, materializedTags), AsyncIteratorCloseWrapperNode.cloneUninitialized(this.iteratorNode, materializedTags), AsyncIteratorCloseWrapperNode.cloneUninitialized(this.readAsyncContextNode, materializedTags), AsyncIteratorCloseWrapperNode.cloneUninitialized(this.readAsyncResultNode, materializedTags));
    }
}

