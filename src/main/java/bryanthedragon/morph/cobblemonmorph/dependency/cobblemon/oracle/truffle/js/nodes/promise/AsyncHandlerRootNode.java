
package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public interface AsyncHandlerRootNode {
    public AsyncStackTraceInfo getAsyncStackTraceInfo(JSFunctionObject var1);

    public static final class AsyncStackTraceInfo {
        public final JSDynamicObject promise;
        public final TruffleStackTraceElement stackTraceElement;

        public AsyncStackTraceInfo(JSDynamicObject promise, TruffleStackTraceElement stackTraceElement) {
            this.promise = promise;
            this.stackTraceElement = stackTraceElement;
        }

        public AsyncStackTraceInfo() {
            this(null, null);
        }
    }
}

