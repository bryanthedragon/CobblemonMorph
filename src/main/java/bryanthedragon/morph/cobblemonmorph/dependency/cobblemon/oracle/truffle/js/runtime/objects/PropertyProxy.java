
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class PropertyProxy {
    public abstract Object get(JSDynamicObject var1);

    public boolean set(JSDynamicObject store, Object value2) {
        return true;
    }
}

