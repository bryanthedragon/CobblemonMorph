
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GenerateUncached
public abstract class JSInteropExecuteNode
extends JSInteropCallNode {
    protected JSInteropExecuteNode() {
    }

    public abstract Object execute(JSDynamicObject var1, Object var2, Object[] var3) throws UnsupportedMessageException;

    @Specialization
    Object doDefault(JSDynamicObject function, Object thisArg, Object[] arguments, @Cached IsCallableNode isCallableNode, @Cached(value="createCall()", uncached="getUncachedCall()") JSFunctionCallNode callNode, @Cached ImportValueNode importValueNode) throws UnsupportedMessageException {
        if (!isCallableNode.executeBoolean(function)) {
            throw UnsupportedMessageException.create();
        }
        Object[] preparedArgs = JSInteropExecuteNode.prepare(arguments, importValueNode);
        return callNode.executeCall(JSArguments.create(thisArg, function, preparedArgs));
    }
}

