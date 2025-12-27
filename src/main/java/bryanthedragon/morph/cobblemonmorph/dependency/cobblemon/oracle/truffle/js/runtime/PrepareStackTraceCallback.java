package com.oracle.truffle.js.runtime;

import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public interface PrepareStackTraceCallback {
   Object prepareStackTrace(JSRealm realm, JSDynamicObject error, JSDynamicObject structuredStackTrace);
}
