
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

public abstract class JSInteropCallNode
extends JavaScriptBaseNode {
    protected JSInteropCallNode() {
    }

    protected static Object[] prepare(Object[] args, ImportValueNode importValueNode) {
        Object[] newArgs = args;
        boolean copy = false;
        for (int i = 0; i < args.length; ++i) {
            Object arg = args[i];
            Object newArg = importValueNode.executeWithTarget(arg);
            if (copy) {
                newArgs[i] = newArg;
                continue;
            }
            if (newArg == arg) continue;
            newArgs = new Object[args.length];
            System.arraycopy(args, 0, newArgs, 0, i);
            newArgs[i] = newArg;
            copy = true;
        }
        return newArgs;
    }

    protected static PropertyGetNode getUncachedProperty() {
        return null;
    }

    protected static Object getProperty(JSObject receiver, PropertyGetNode propertyGetNode, Object key, Object defaultValue) {
        Object method;
        assert (JSRuntime.isPropertyKey(key));
        if (propertyGetNode == null) {
            method = JSObject.getOrDefault((JSDynamicObject)receiver, key, (Object)receiver, defaultValue);
        } else {
            assert (JSRuntime.propertyKeyEquals(TruffleString.EqualNode.getUncached(), propertyGetNode.getKey(), key));
            method = propertyGetNode.getValueOrDefault(receiver, defaultValue);
        }
        return method;
    }
}

