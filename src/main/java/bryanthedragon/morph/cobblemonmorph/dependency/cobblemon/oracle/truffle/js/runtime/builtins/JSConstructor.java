
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class JSConstructor {
    private final JSFunctionObject constructor;
    private final JSDynamicObject prototype;

    public JSConstructor(JSFunctionObject constructor, JSDynamicObject prototype) {
        this.constructor = constructor;
        this.prototype = prototype;
    }

    public JSFunctionObject getFunctionObject() {
        return this.constructor;
    }

    public JSDynamicObject getPrototype() {
        return this.prototype;
    }
}

