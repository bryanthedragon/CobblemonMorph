
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropCallNode;
import com.oracle.truffle.js.nodes.unary.IsConstructorNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GenerateUncached
public abstract class JSInteropInstantiateNode
extends JSInteropCallNode {
    protected JSInteropInstantiateNode() {
    }

    public abstract Object execute(JSDynamicObject var1, Object[] var2) throws UnsupportedMessageException;

    @Specialization
    Object doDefault(JSDynamicObject function, Object[] arguments, @Cached IsConstructorNode isConstructorNode, @Cached(value="createNew()", uncached="getUncachedNew()") JSFunctionCallNode callNode, @Cached ImportValueNode importValueNode) throws UnsupportedMessageException {
        if (!isConstructorNode.executeBoolean(function)) {
            throw UnsupportedMessageException.create();
        }
        Object[] preparedArgs = JSInteropInstantiateNode.prepare(arguments, importValueNode);
        return callNode.executeCall(JSArguments.create(JSFunction.CONSTRUCT, function, preparedArgs));
    }
}

