
package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public final class JSWebAssemblyModuleObject
extends JSNonProxyObject {
    private final Object wasmModule;

    protected JSWebAssemblyModuleObject(Shape shape, Object wasmModule) {
        super(shape);
        this.wasmModule = wasmModule;
    }

    public Object getWASMModule() {
        return this.wasmModule;
    }
}

