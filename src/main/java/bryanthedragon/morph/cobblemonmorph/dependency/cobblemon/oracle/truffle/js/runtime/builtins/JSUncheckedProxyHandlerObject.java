
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public class JSUncheckedProxyHandlerObject
extends JSNonProxyObject {
    JSUncheckedProxyHandlerObject(Shape shape) {
        super(shape);
    }
}

