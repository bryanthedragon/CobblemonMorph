
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public class IteratorCloseNode
extends JavaScriptBaseNode {
    @Node.Child
    private GetMethodNode getReturnNode;
    @Node.Child
    private JSFunctionCallNode methodCallNode;
    @Node.Child
    private IsJSObjectNode isObjectNode;

    protected IteratorCloseNode(JSContext context) {
        this.getReturnNode = GetMethodNode.create(context, Strings.RETURN);
        this.methodCallNode = JSFunctionCallNode.createCall();
        this.isObjectNode = IsJSObjectNode.create();
    }

    public static IteratorCloseNode create(JSContext context) {
        return new IteratorCloseNode(context);
    }

    public final void executeVoid(JSDynamicObject iterator) {
        Object innerResult;
        Object returnMethod = this.getReturnNode.executeWithTarget(iterator);
        if (returnMethod != Undefined.instance && !this.isObjectNode.executeBoolean(innerResult = this.methodCallNode.executeCall(JSArguments.createZeroArg(iterator, returnMethod)))) {
            throw Errors.createTypeErrorIterResultNotAnObject(innerResult, this);
        }
    }

    public final Object execute(JSDynamicObject iterator, Object value2) {
        this.executeVoid(iterator);
        return value2;
    }

    public final void executeAbrupt(JSDynamicObject iterator) {
        try {
            Object returnMethod = this.getReturnNode.executeWithTarget(iterator);
            if (returnMethod != Undefined.instance) {
                this.methodCallNode.executeCall(JSArguments.createZeroArg(iterator, returnMethod));
            }
        }
        catch (AbstractTruffleException abstractTruffleException) {
            // empty catch block
        }
    }
}

