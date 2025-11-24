
package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public final class JSWebAssemblyTableObject
extends JSNonProxyObject {
    private final Object wasmTable;

    protected JSWebAssemblyTableObject(Shape shape, Object wasmTable) {
        super(shape);
        this.wasmTable = wasmTable;
    }

    public Object getWASMTable() {
        return this.wasmTable;
    }
}

