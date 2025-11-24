
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public interface PromiseRejectionTracker {
    public void promiseRejected(JSDynamicObject var1, Object var2);

    public void promiseRejectionHandled(JSDynamicObject var1);

    public void promiseRejectedAfterResolved(JSDynamicObject var1, Object var2);

    public void promiseResolvedAfterResolved(JSDynamicObject var1, Object var2);

    default public void promiseReactionJobsProcessed() {
    }
}

