
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
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.interop.InteropFunction;
import java.util.Objects;

@ExportLibrary(value=InteropLibrary.class, delegateTo="function")
@ImportStatic(value={JSConfig.class})
public final class InteropBoundFunction
extends InteropFunction {
    final JSFunctionObject function;
    final Object receiver;

    public InteropBoundFunction(JSFunctionObject function, Object receiver) {
        super(function);
        this.function = function;
        this.receiver = receiver;
    }

    public Object getReceiver() {
        return this.receiver;
    }

    public int hashCode() {
        int result = 31 + (this.function == null ? 0 : this.function.hashCode());
        result = 31 * result + (this.receiver == null ? 0 : this.receiver.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof InteropBoundFunction) {
            InteropBoundFunction other = (InteropBoundFunction)obj;
            return Objects.equals(this.function, other.function) && Objects.equals(this.receiver, other.receiver);
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
    Object execute(Object[] arguments, @CachedLibrary(value="this") InteropLibrary self, @Cached JSInteropExecuteNode callNode, @Cached ExportValueNode exportNode) throws UnsupportedMessageException {
        JavaScriptLanguage language = JavaScriptLanguage.get(self);
        JSRealm realm = JSRealm.get(self);
        language.interopBoundaryEnter(realm);
        try {
            Object result = callNode.execute(this.function, this.receiver, arguments);
            Object object = exportNode.execute(result);
            return object;
        }
        finally {
            language.interopBoundaryExit(realm);
        }
    }
}

