
package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public final class JSWebAssemblyMemoryObject
extends JSNonProxyObject {
    private final Object wasmMemory;
    private JSDynamicObject bufferObject;

    protected JSWebAssemblyMemoryObject(Shape shape, Object wasmMemory) {
        super(shape);
        this.wasmMemory = wasmMemory;
    }

    public Object getWASMMemory() {
        return this.wasmMemory;
    }

    public JSDynamicObject getBufferObject(JSContext context, JSRealm realm) {
        if (this.bufferObject == null) {
            this.bufferObject = JSArrayBuffer.createInteropArrayBuffer(context, realm, this.wasmMemory);
        }
        return this.bufferObject;
    }

    public void resetBufferObject() {
        if (this.bufferObject != null) {
            JSArrayBuffer.detachArrayBuffer(this.bufferObject);
        }
        this.bufferObject = null;
    }
}

