
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.builtins.JSWeakRef;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public final class JSWeakRefObject
extends JSNonProxyObject {
    private final JSWeakRef.TruffleWeakReference<Object> weakReference;

    protected JSWeakRefObject(Shape shape, JSWeakRef.TruffleWeakReference<Object> weakReference) {
        super(shape);
        this.weakReference = weakReference;
    }

    public JSWeakRef.TruffleWeakReference<Object> getWeakReference() {
        return this.weakReference;
    }
}

