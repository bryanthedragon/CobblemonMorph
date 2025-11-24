
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public interface PrepareStackTraceCallback {
    public Object prepareStackTrace(JSRealm var1, JSDynamicObject var2, JSDynamicObject var3);
}

