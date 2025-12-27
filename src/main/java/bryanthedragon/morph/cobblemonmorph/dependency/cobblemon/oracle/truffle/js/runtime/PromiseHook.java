package com.oracle.truffle.js.runtime;

import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public interface PromiseHook {
   int TYPE_INIT = 0;
   int TYPE_RESOLVE = 1;
   int TYPE_BEFORE = 2;
   int TYPE_AFTER = 3;

   void promiseChanged(int changeType, JSDynamicObject promise, JSDynamicObject parentPromise);
}
