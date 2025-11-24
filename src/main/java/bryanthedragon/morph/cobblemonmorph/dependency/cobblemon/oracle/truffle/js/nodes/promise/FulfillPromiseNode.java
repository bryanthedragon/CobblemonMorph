
package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.promise.TriggerPromiseReactionsNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public class FulfillPromiseNode
extends JavaScriptBaseNode {
    @Node.Child
    private PropertyGetNode getPromiseFulfillReactions;
    @Node.Child
    private PropertySetNode setPromiseResult;
    @Node.Child
    private PropertySetNode setPromiseFulfillReactions;
    @Node.Child
    private PropertySetNode setPromiseRejectReactions;
    @Node.Child
    private TriggerPromiseReactionsNode triggerPromiseReactions;

    protected FulfillPromiseNode(JSContext context) {
        this.getPromiseFulfillReactions = PropertyGetNode.createGetHidden(JSPromise.PROMISE_FULFILL_REACTIONS, context);
        this.setPromiseResult = PropertySetNode.createSetHidden(JSPromise.PROMISE_RESULT, context);
        this.setPromiseFulfillReactions = PropertySetNode.createSetHidden(JSPromise.PROMISE_FULFILL_REACTIONS, context);
        this.setPromiseRejectReactions = PropertySetNode.createSetHidden(JSPromise.PROMISE_REJECT_REACTIONS, context);
        this.triggerPromiseReactions = TriggerPromiseReactionsNode.create(context);
    }

    public static FulfillPromiseNode create(JSContext context) {
        return new FulfillPromiseNode(context);
    }

    public Object execute(JSDynamicObject promise, Object value2) {
        assert (JSPromise.isPending(promise));
        Object reactions = this.getPromiseFulfillReactions.getValue(promise);
        this.setPromiseResult.setValue(promise, value2);
        this.setPromiseFulfillReactions.setValue(promise, Undefined.instance);
        this.setPromiseRejectReactions.setValue(promise, Undefined.instance);
        JSPromise.setPromiseState(promise, 1);
        return this.triggerPromiseReactions.execute(reactions, value2);
    }
}

