
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.control.AsyncGeneratorResumeNextNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.AsyncGeneratorRequest;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayDeque;

public class AsyncGeneratorRejectNode
extends JavaScriptBaseNode {
    @Node.Child
    private PropertyGetNode getAsyncGeneratorQueueNode;
    @Node.Child
    private JSFunctionCallNode callRejectNode;
    @Node.Child
    private AsyncGeneratorResumeNextNode asyncGeneratorResumeNextNode;

    protected AsyncGeneratorRejectNode(JSContext context) {
        this.getAsyncGeneratorQueueNode = PropertyGetNode.createGetHidden(JSFunction.ASYNC_GENERATOR_QUEUE_ID, context);
        this.callRejectNode = JSFunctionCallNode.createCall();
    }

    public static AsyncGeneratorRejectNode create(JSContext context) {
        return new AsyncGeneratorRejectNode(context);
    }

    public Object execute(VirtualFrame frame, JSDynamicObject generator, Object exception) {
        this.performReject(frame, generator, exception);
        if (this.asyncGeneratorResumeNextNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.asyncGeneratorResumeNextNode = this.insert(AsyncGeneratorResumeNextNode.create(this.getContext()));
        }
        this.asyncGeneratorResumeNextNode.execute(frame, generator);
        return Undefined.instance;
    }

    void performReject(VirtualFrame frame, JSDynamicObject generator, Object exception) {
        ArrayDeque queue = (ArrayDeque)this.getAsyncGeneratorQueueNode.getValue(generator);
        assert (!queue.isEmpty());
        AsyncGeneratorRequest next = (AsyncGeneratorRequest)queue.pollFirst();
        PromiseCapabilityRecord promiseCapability = next.getPromiseCapability();
        Object reject = promiseCapability.getReject();
        this.callRejectNode.executeCall(JSArguments.createOneArg(Undefined.instance, reject, exception));
    }

    private JSContext getContext() {
        return this.getAsyncGeneratorQueueNode.getContext();
    }
}

