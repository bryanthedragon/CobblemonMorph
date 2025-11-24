
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropCallNode;
import com.oracle.truffle.js.nodes.interop.JSInteropInvokeNodeGen;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

@GenerateUncached
public abstract class JSInteropInvokeNode
extends JSInteropCallNode {
    JSInteropInvokeNode() {
    }

    public static JSInteropInvokeNode create() {
        return JSInteropInvokeNodeGen.create();
    }

    public abstract Object execute(JSDynamicObject var1, TruffleString var2, Object[] var3) throws UnknownIdentifierException, UnsupportedMessageException;

    @Specialization(guards={"stringEquals(equalNode, cachedName, name)"}, limit="1")
    Object doCached(JSDynamicObject receiver, TruffleString name, Object[] arguments, @Cached(value="name") TruffleString cachedName, @Cached TruffleString.EqualNode equalNode, @Cached(value="createGetProperty(cachedName)") PropertyGetNode functionPropertyGetNode, @Cached.Shared(value="isCallable") @Cached IsCallableNode isCallableNode, @Cached.Shared(value="call") @Cached(value="createCall()", uncached="getUncachedCall()") JSFunctionCallNode callNode, @Cached.Shared(value="importValue") @Cached ImportValueNode importValueNode) throws UnknownIdentifierException, UnsupportedMessageException {
        Object function = functionPropertyGetNode.getValueOrDefault(receiver, null);
        if (function == null) {
            throw UnknownIdentifierException.create(cachedName.toJavaStringUncached());
        }
        if (isCallableNode.executeBoolean(function)) {
            return callNode.executeCall(JSArguments.create(receiver, function, JSInteropInvokeNode.prepare(arguments, importValueNode)));
        }
        throw UnsupportedMessageException.create();
    }

    @Specialization(replaces={"doCached"})
    Object doUncached(JSDynamicObject receiver, TruffleString name, Object[] arguments, @Cached(value="create(getLanguage().getJSContext())", uncached="getUncachedRead()") ReadElementNode readNode, @Cached.Shared(value="isCallable") @Cached IsCallableNode isCallableNode, @Cached.Shared(value="call") @Cached(value="createCall()", uncached="getUncachedCall()") JSFunctionCallNode callNode, @Cached.Shared(value="importValue") @Cached ImportValueNode importValueNode) throws UnknownIdentifierException, UnsupportedMessageException {
        Object function = readNode == null ? JSObject.getOrDefault(receiver, name, (Object)receiver, null) : readNode.executeWithTargetAndIndexOrDefault(receiver, name, null);
        if (function == null) {
            throw UnknownIdentifierException.create(name.toJavaStringUncached());
        }
        if (isCallableNode.executeBoolean(function)) {
            Object[] preparedArgs = JSInteropInvokeNode.prepare(arguments, importValueNode);
            return callNode.executeCall(JSArguments.create(receiver, function, preparedArgs));
        }
        throw UnsupportedMessageException.create();
    }

    PropertyGetNode createGetProperty(TruffleString name) {
        return PropertyGetNode.create(name, false, this.getLanguage().getJSContext());
    }

    static ReadElementNode getUncachedRead() {
        return null;
    }
}

