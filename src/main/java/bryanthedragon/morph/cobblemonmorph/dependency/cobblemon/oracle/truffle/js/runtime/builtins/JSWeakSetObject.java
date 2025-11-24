
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.util.Map;

public final class JSWeakSetObject
extends JSNonProxyObject {
    private final Map<Object, Object> weakHashMap;

    protected JSWeakSetObject(Shape shape, Map<Object, Object> weakHashMap) {
        super(shape);
        this.weakHashMap = weakHashMap;
    }

    public Map<Object, Object> getWeakHashMap() {
        return this.weakHashMap;
    }
}

