
package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.control.TryCatchNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.promise.CreateResolvingFunctionNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.Pair;

public class PromiseResolveThenableNode
extends JavaScriptBaseNode {
    private final JSContext context;
    @Node.Child
    private CreateResolvingFunctionNode createResolvingFunctions;
    @Node.Child
    private JSFunctionCallNode callResolveNode;
    @Node.Child
    private JSFunctionCallNode callRejectNode;
    @Node.Child
    private TryCatchNode.GetErrorObjectNode getErrorObjectNode;

    protected PromiseResolveThenableNode(JSContext context) {
        this.context = context;
        this.createResolvingFunctions = CreateResolvingFunctionNode.create(context);
        this.callResolveNode = JSFunctionCallNode.createCall();
    }

    public static PromiseResolveThenableNode create(JSContext context) {
        return new PromiseResolveThenableNode(context);
    }

    public Object execute(JSDynamicObject promiseToResolve, Object thenable, Object then) {
        Pair<JSDynamicObject, JSDynamicObject> resolvingFunctions = this.createResolvingFunctions.execute(promiseToResolve);
        JSDynamicObject resolve = resolvingFunctions.getFirst();
        JSDynamicObject reject = resolvingFunctions.getSecond();
        try {
            return this.callResolveNode.executeCall(JSArguments.create(thenable, then, resolve, reject));
        }
        catch (AbstractTruffleException ex) {
            return this.callReject(reject, ex);
        }
    }

    private Object callReject(JSDynamicObject reject, AbstractTruffleException exception) {
        if (this.getErrorObjectNode == null || this.callRejectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getErrorObjectNode = this.insert(TryCatchNode.GetErrorObjectNode.create(this.context));
            this.callRejectNode = this.insert(JSFunctionCallNode.createCall());
        }
        Object error = this.getErrorObjectNode.execute(exception);
        return this.callRejectNode.executeCall(JSArguments.create(Undefined.instance, reject, error));
    }
}

