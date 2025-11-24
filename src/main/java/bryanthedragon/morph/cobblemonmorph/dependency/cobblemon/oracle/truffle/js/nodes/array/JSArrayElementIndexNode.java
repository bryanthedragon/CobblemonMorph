
package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsArrayNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;

public abstract class JSArrayElementIndexNode
extends JavaScriptBaseNode {
    protected static final int MAX_CACHED_ARRAY_TYPES = 4;
    protected final JSContext context;
    @Node.Child
    private IsArrayNode isArrayNode;

    protected JSArrayElementIndexNode(JSContext context) {
        this.context = context;
    }

    protected static boolean hasHoles(JSDynamicObject object) {
        return JSObject.getArray(object).hasHoles(object);
    }

    protected static ScriptArray getArrayType(JSDynamicObject object) {
        return JSObject.getArray(object);
    }

    protected static ScriptArray getArrayTypeIfArray(JSDynamicObject object, boolean isArray) {
        if (!isArray) {
            return null;
        }
        return JSArrayElementIndexNode.getArrayType(object);
    }

    protected final boolean isSuitableForEnumBasedProcessingUsingOwnKeys(Object object, long length) {
        return length > 10000L && !JSArrayBufferView.isJSArrayBufferView(object) && !JSProxy.isJSProxy(object) && (JSArray.isJSArray(object) && this.context.getArrayPrototypeNoElementsAssumption().isValid() || !JSDynamicObject.isJSDynamicObject(object) || JSObject.getPrototype((JSDynamicObject)object) == Null.instance);
    }

    protected static final boolean isSuitableForEnumBasedProcessing(Object object, long length) {
        if (length <= 10000L || !JSDynamicObject.isJSDynamicObject(object)) {
            return false;
        }
        JSDynamicObject chainObject = (JSDynamicObject)object;
        do {
            if (!JSArrayBufferView.isJSArrayBufferView(chainObject) && !JSProxy.isJSProxy(chainObject)) continue;
            return false;
        } while ((chainObject = JSObject.getPrototype(chainObject)) != Null.instance);
        return true;
    }

    protected final boolean hasPrototypeElements(JSDynamicObject object) {
        return !this.context.getArrayPrototypeNoElementsAssumption().isValid();
    }

    protected final boolean isArray(Object obj) {
        if (this.isArrayNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.isArrayNode = this.insert(IsArrayNode.createIsFastOrTypedArray());
        }
        return this.isArrayNode.execute(obj);
    }

    protected static boolean isSupportedArray(JSDynamicObject object) {
        return JSArray.isJSFastArray(object) || JSArgumentsArray.isJSFastArgumentsObject(object) || JSArrayBufferView.isJSArrayBufferView(object);
    }
}

