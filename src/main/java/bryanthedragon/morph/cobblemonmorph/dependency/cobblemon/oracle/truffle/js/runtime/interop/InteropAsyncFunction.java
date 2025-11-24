
package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropExecuteNode;
import com.oracle.truffle.js.nodes.promise.UnwrapPromiseNode;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.interop.InteropFunction;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ExportLibrary(value=InteropLibrary.class, delegateTo="function")
@ImportStatic(value={JSConfig.class})
public final class InteropAsyncFunction
extends InteropFunction {
    final JSFunctionObject function;

    public InteropAsyncFunction(JSFunctionObject function) {
        super(function);
        this.function = function;
    }

    public int hashCode() {
        return this.function.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof InteropAsyncFunction) {
            InteropAsyncFunction other = (InteropAsyncFunction)obj;
            return this.function.equals(other.function);
        }
        return false;
    }

    @ExportMessage
    boolean isExecutable() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ExportMessage
    Object execute(Object[] arguments, @CachedLibrary(value="this") InteropLibrary self, @Cached JSInteropExecuteNode callNode, @Cached ExportValueNode exportNode, @Cached UnwrapPromiseNode unwrapPromise) throws UnsupportedMessageException {
        Object result;
        JavaScriptLanguage language = JavaScriptLanguage.get(self);
        assert (language.getJSContext().getContextOptions().isMLEMode());
        JSRealm realm = JSRealm.get(self);
        language.interopBoundaryEnter(realm);
        try {
            result = callNode.execute(this.function, Undefined.instance, arguments);
            result = exportNode.execute(result);
        }
        finally {
            language.interopBoundaryExit(realm);
        }
        return unwrapPromise.execute((JSDynamicObject)result);
    }
}

