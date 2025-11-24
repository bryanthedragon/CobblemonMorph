
package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.promise.TriggerPromiseReactionsNode;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.builtins.JSErrorObject;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public class RejectPromiseNode
extends JavaScriptBaseNode {
    private final JSContext context;
    @Node.Child
    private PropertyGetNode getPromiseRejectReactions;
    @Node.Child
    private PropertySetNode setPromiseResult;
    @Node.Child
    private PropertySetNode setPromiseFulfillReactions;
    @Node.Child
    private PropertySetNode setPromiseRejectReactions;
    @Node.Child
    private PropertyGetNode getPromiseIsHandled;
    @Node.Child
    private TriggerPromiseReactionsNode triggerPromiseReactions;
    private final ConditionProfile unhandledProf = ConditionProfile.createBinaryProfile();

    protected RejectPromiseNode(JSContext context) {
        this.context = context;
        this.getPromiseRejectReactions = PropertyGetNode.createGetHidden(JSPromise.PROMISE_REJECT_REACTIONS, context);
        this.setPromiseResult = PropertySetNode.createSetHidden(JSPromise.PROMISE_RESULT, context);
        this.setPromiseFulfillReactions = PropertySetNode.createSetHidden(JSPromise.PROMISE_FULFILL_REACTIONS, context);
        this.setPromiseRejectReactions = PropertySetNode.createSetHidden(JSPromise.PROMISE_REJECT_REACTIONS, context);
        this.getPromiseIsHandled = PropertyGetNode.createGetHidden(JSPromise.PROMISE_IS_HANDLED, context);
        this.triggerPromiseReactions = TriggerPromiseReactionsNode.create(context);
    }

    public static RejectPromiseNode create(JSContext context) {
        return new RejectPromiseNode(context);
    }

    public Object execute(JSDynamicObject promise, Object reason) {
        assert (JSPromise.isPending(promise));
        if (this.context.isOptionAsyncStackTraces() && JSError.isJSError(reason)) {
            RejectPromiseNode.materializeLazyStackTrace((JSErrorObject)reason);
        }
        Object reactions = this.getPromiseRejectReactions.getValue(promise);
        this.setPromiseResult.setValue(promise, reason);
        this.setPromiseFulfillReactions.setValue(promise, Undefined.instance);
        this.setPromiseRejectReactions.setValue(promise, Undefined.instance);
        JSPromise.setPromiseState(promise, 2);
        if (this.unhandledProf.profile(this.getPromiseIsHandled.getValue(promise) != Boolean.TRUE)) {
            this.context.notifyPromiseRejectionTracker(promise, 0, reason);
        }
        return this.triggerPromiseReactions.execute(reactions, reason);
    }

    @CompilerDirectives.TruffleBoundary
    private static void materializeLazyStackTrace(JSErrorObject error) {
        assert (JSError.isJSError(error));
        GraalJSException exception = JSError.getException(error);
        if (exception != null) {
            exception.getJSStackTrace();
        }
    }
}

