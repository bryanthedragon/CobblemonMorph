package com.oracle.truffle.js.runtime;

import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public interface PromiseRejectionTracker {
   void promiseRejected(JSDynamicObject promise, Object value);

   void promiseRejectionHandled(JSDynamicObject promise);

   void promiseRejectedAfterResolved(JSDynamicObject promise, Object value);

   void promiseResolvedAfterResolved(JSDynamicObject promise, Object value);

   default void promiseReactionJobsProcessed() {
   }
}
