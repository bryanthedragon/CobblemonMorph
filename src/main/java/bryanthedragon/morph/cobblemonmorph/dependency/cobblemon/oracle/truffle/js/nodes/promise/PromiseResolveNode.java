
package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.promise.NewPromiseCapabilityNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;

public class PromiseResolveNode
extends JavaScriptBaseNode {
    @Node.Child
    private NewPromiseCapabilityNode newPromiseCapability;
    @Node.Child
    private JSFunctionCallNode callResolve;
    @Node.Child
    private PropertyGetNode getConstructor;

    protected PromiseResolveNode(JSContext context) {
        this.newPromiseCapability = NewPromiseCapabilityNode.create(context);
        this.callResolve = JSFunctionCallNode.createCall();
        this.getConstructor = PropertyGetNode.create(JSObject.CONSTRUCTOR, false, context);
    }

    public static PromiseResolveNode create(JSContext context) {
        return new PromiseResolveNode(context);
    }

    public JSDynamicObject execute(JSDynamicObject constructor, Object value2) {
        Object otherConstructor;
        assert (JSRuntime.isObject(constructor));
        if (JSPromise.isJSPromise(value2) && (otherConstructor = this.getConstructor.getValue(value2)) == constructor) {
            return (JSDynamicObject)value2;
        }
        PromiseCapabilityRecord promiseCapability = this.newPromiseCapability.execute(constructor);
        this.callResolve.executeCall(JSArguments.createOneArg(Undefined.instance, promiseCapability.getResolve(), value2));
        return promiseCapability.getPromise();
    }
}

