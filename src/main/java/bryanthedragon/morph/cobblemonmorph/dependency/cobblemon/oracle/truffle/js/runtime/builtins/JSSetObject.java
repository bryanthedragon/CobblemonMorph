
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import com.oracle.truffle.js.runtime.util.JSHashMap;

public final class JSSetObject
extends JSNonProxyObject {
    private final JSHashMap map;

    protected JSSetObject(Shape shape, JSHashMap map) {
        super(shape);
        this.map = map;
    }

    public JSHashMap getMap() {
        return this.map;
    }
}

