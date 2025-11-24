
package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.promise.PromiseReactionJobNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.PromiseReactionRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;

public class PerformPromiseThenNode
extends JavaScriptBaseNode {
    private final JSContext context;
    @Node.Child
    private IsCallableNode isCallableFulfillNode = IsCallableNode.create();
    @Node.Child
    private IsCallableNode isCallableRejectNode = IsCallableNode.create();
    @Node.Child
    private PropertyGetNode getPromiseFulfillReactionsNode;
    @Node.Child
    private PropertyGetNode getPromiseRejectReactionsNode;
    @Node.Child
    private PropertyGetNode getPromiseResultNode;
    @Node.Child
    private PropertyGetNode getPromiseIsHandledNode;
    @Node.Child
    private PropertySetNode setPromiseIsHandledNode;
    @Node.Child
    private PromiseReactionJobNode promiseReactionJobNode;
    private final ConditionProfile pendingProf = ConditionProfile.createBinaryProfile();
    private final ConditionProfile fulfilledProf = ConditionProfile.createBinaryProfile();
    private final ConditionProfile unhandledProf = ConditionProfile.createBinaryProfile();
    private final BranchProfile growProfile = BranchProfile.create();

    protected PerformPromiseThenNode(JSContext context) {
        this.context = context;
        this.getPromiseFulfillReactionsNode = PropertyGetNode.createGetHidden(JSPromise.PROMISE_FULFILL_REACTIONS, context);
        this.getPromiseRejectReactionsNode = PropertyGetNode.createGetHidden(JSPromise.PROMISE_REJECT_REACTIONS, context);
        this.setPromiseIsHandledNode = PropertySetNode.createSetHidden(JSPromise.PROMISE_IS_HANDLED, context);
    }

    public static PerformPromiseThenNode create(JSContext context) {
        return new PerformPromiseThenNode(context);
    }

    public JSDynamicObject execute(JSDynamicObject promise, Object onFulfilled, Object onRejected, PromiseCapabilityRecord resultCapability) {
        Object onRejectedHandler;
        assert (JSPromise.isJSPromise(promise));
        Object onFulfilledHandler = this.isCallableFulfillNode.executeBoolean(onFulfilled) ? onFulfilled : Undefined.instance;
        Object object = onRejectedHandler = this.isCallableRejectNode.executeBoolean(onRejected) ? onRejected : Undefined.instance;
        assert (resultCapability != null || onFulfilledHandler != Undefined.instance && onRejectedHandler != Undefined.instance);
        PromiseReactionRecord fulfillReaction = PromiseReactionRecord.create(resultCapability, onFulfilledHandler, true);
        PromiseReactionRecord rejectReaction = PromiseReactionRecord.create(resultCapability, onRejectedHandler, false);
        int promiseState = JSPromise.getPromiseState(promise);
        if (this.pendingProf.profile(promiseState == 0)) {
            ((SimpleArrayList)this.getPromiseFulfillReactionsNode.getValue(promise)).add(fulfillReaction, this.growProfile);
            ((SimpleArrayList)this.getPromiseRejectReactionsNode.getValue(promise)).add(rejectReaction, this.growProfile);
        } else if (this.fulfilledProf.profile(promiseState == 1)) {
            Object value2 = this.getPromiseResult(promise);
            JSFunctionObject job = this.getPromiseReactionJob(fulfillReaction, value2);
            this.context.promiseEnqueueJob(this.getRealm(), job);
        } else {
            assert (promiseState == 2);
            Object reason = this.getPromiseResult(promise);
            if (this.unhandledProf.profile(!this.getPromiseIsHandled(promise))) {
                this.context.notifyPromiseRejectionTracker(promise, 1, Undefined.instance);
            }
            JSFunctionObject job = this.getPromiseReactionJob(rejectReaction, reason);
            this.context.promiseEnqueueJob(this.getRealm(), job);
        }
        this.setPromiseIsHandledNode.setValueBoolean(promise, true);
        if (resultCapability == null) {
            return Undefined.instance;
        }
        return resultCapability.getPromise();
    }

    private JSFunctionObject getPromiseReactionJob(PromiseReactionRecord reaction, Object value2) {
        if (this.promiseReactionJobNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.promiseReactionJobNode = this.insert(PromiseReactionJobNode.create(this.context));
        }
        return this.promiseReactionJobNode.execute(reaction, value2);
    }

    private Object getPromiseResult(JSDynamicObject promise) {
        if (this.getPromiseResultNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getPromiseResultNode = this.insert(PropertyGetNode.createGetHidden(JSPromise.PROMISE_RESULT, this.context));
        }
        return this.getPromiseResultNode.getValue(promise);
    }

    private boolean getPromiseIsHandled(JSDynamicObject promise) {
        try {
            if (this.getPromiseIsHandledNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getPromiseIsHandledNode = this.insert(PropertyGetNode.createGetHidden(JSPromise.PROMISE_IS_HANDLED, this.context));
            }
            return this.getPromiseIsHandledNode.getValueBoolean(promise);
        }
        catch (UnexpectedResultException e) {
            throw Errors.shouldNotReachHere();
        }
    }
}

