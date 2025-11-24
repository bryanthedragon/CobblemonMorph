
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public interface PromiseHook {
    public static final int TYPE_INIT = 0;
    public static final int TYPE_RESOLVE = 1;
    public static final int TYPE_BEFORE = 2;
    public static final int TYPE_AFTER = 3;

    public void promiseChanged(int var1, JSDynamicObject var2, JSDynamicObject var3);
}

