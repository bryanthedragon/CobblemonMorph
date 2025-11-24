
package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssembly;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyMemoryObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ExportLibrary(value=InteropLibrary.class)
public final class JSWebAssemblyMemoryGrowCallback
implements TruffleObject {
    private final JSRealm realm;
    private final Object memSetGrowCallbackFunction;

    public JSWebAssemblyMemoryGrowCallback(JSRealm realm, Object memSetGrowCallbackFunction) {
        this.realm = realm;
        this.memSetGrowCallbackFunction = memSetGrowCallbackFunction;
    }

    public void attachToMemory(Object wasmMemory) {
        InteropLibrary lib = InteropLibrary.getUncached();
        try {
            lib.execute(this.memSetGrowCallbackFunction, wasmMemory, this);
        }
        catch (InteropException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
    }

    @ExportMessage
    boolean isExecutable() {
        return true;
    }

    @ExportMessage
    Object execute(Object[] arguments) {
        assert (arguments.length == 1);
        Object embedderData = JSWebAssembly.getEmbedderData(this.realm, arguments[0]);
        if (embedderData instanceof JSWebAssemblyMemoryObject) {
            ((JSWebAssemblyMemoryObject)embedderData).resetBufferObject();
        }
        return Undefined.instance;
    }
}

