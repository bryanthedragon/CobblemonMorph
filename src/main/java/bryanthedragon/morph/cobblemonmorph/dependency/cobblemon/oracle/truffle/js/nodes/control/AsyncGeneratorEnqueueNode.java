
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.HasHiddenKeyCacheNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.control.AsyncGeneratorResumeNextNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.promise.NewPromiseCapabilityNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.AsyncGeneratorRequest;
import com.oracle.truffle.js.runtime.objects.Completion;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayDeque;

public class AsyncGeneratorEnqueueNode
extends JavaScriptBaseNode {
    @Node.Child
    private PropertyGetNode getGeneratorStateNode;
    @Node.Child
    private PropertyGetNode getAsyncGeneratorQueueNode;
    @Node.Child
    private HasHiddenKeyCacheNode hasAsyncGeneratorInternalSlotsNode;
    @Node.Child
    private JSFunctionCallNode callPromiseRejectNode;
    @Node.Child
    private NewPromiseCapabilityNode newPromiseCapabilityNode;
    @Node.Child
    private AsyncGeneratorResumeNextNode asyncGeneratorResumeNextNode;
    private final ConditionProfile notExecutingProf = ConditionProfile.createBinaryProfile();

    protected AsyncGeneratorEnqueueNode(JSContext context) {
        this.getGeneratorStateNode = PropertyGetNode.createGetHidden(JSFunction.ASYNC_GENERATOR_STATE_ID, context);
        this.getAsyncGeneratorQueueNode = PropertyGetNode.createGetHidden(JSFunction.ASYNC_GENERATOR_QUEUE_ID, context);
        this.hasAsyncGeneratorInternalSlotsNode = HasHiddenKeyCacheNode.create(JSFunction.ASYNC_GENERATOR_QUEUE_ID);
        this.newPromiseCapabilityNode = NewPromiseCapabilityNode.create(context);
        this.asyncGeneratorResumeNextNode = AsyncGeneratorResumeNextNode.create(context);
    }

    public static AsyncGeneratorEnqueueNode create(JSContext context) {
        return new AsyncGeneratorEnqueueNode(context);
    }

    public Object execute(VirtualFrame frame, Object generator, Completion completion) {
        PromiseCapabilityRecord promiseCapability = this.newPromiseCapability();
        if (!JSGuards.isJSObject(generator) || !this.hasAsyncGeneratorInternalSlotsNode.executeHasHiddenKey(generator)) {
            this.enterErrorBranch();
            return this.badGeneratorError(promiseCapability);
        }
        ArrayDeque queue = (ArrayDeque)this.getAsyncGeneratorQueueNode.getValue(generator);
        AsyncGeneratorRequest request = AsyncGeneratorRequest.create(completion, promiseCapability);
        AsyncGeneratorEnqueueNode.queueAdd(queue, request);
        JSFunction.AsyncGeneratorState state = (JSFunction.AsyncGeneratorState)((Object)this.getGeneratorStateNode.getValue(generator));
        if (this.notExecutingProf.profile(state != JSFunction.AsyncGeneratorState.Executing)) {
            this.asyncGeneratorResumeNextNode.execute(frame, (JSDynamicObject)generator);
        }
        return promiseCapability.getPromise();
    }

    private PromiseCapabilityRecord newPromiseCapability() {
        return this.newPromiseCapabilityNode.executeDefault();
    }

    @CompilerDirectives.TruffleBoundary
    private static void queueAdd(ArrayDeque<AsyncGeneratorRequest> queue, AsyncGeneratorRequest request) {
        queue.addLast(request);
    }

    private void enterErrorBranch() {
        if (this.callPromiseRejectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callPromiseRejectNode = this.insert(JSFunctionCallNode.createCall());
        }
    }

    private Object badGeneratorError(PromiseCapabilityRecord promiseCapability) {
        Object badGeneratorError = Errors.createTypeErrorAsyncGeneratorObjectExpected().getErrorObject();
        Object reject = promiseCapability.getReject();
        this.callPromiseRejectNode.executeCall(JSArguments.createOneArg(Undefined.instance, reject, badGeneratorError));
        return promiseCapability.getPromise();
    }
}

