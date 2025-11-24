
package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public final class JSWebAssemblyInstanceObject
extends JSNonProxyObject {
    private final Object wasmInstance;
    private final Object exports;

    protected JSWebAssemblyInstanceObject(Shape shape, Object wasmInstance, Object exports) {
        super(shape);
        this.wasmInstance = wasmInstance;
        this.exports = exports;
    }

    public Object getWASMInstance() {
        return this.wasmInstance;
    }

    public Object getExports() {
        return this.exports;
    }
}

