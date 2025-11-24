
package com.oracle.truffle.api;

import com.oracle.truffle.api.TruffleRuntime;

public interface TruffleRuntimeAccess {
    public TruffleRuntime getRuntime();

    default public int getPriority() {
        return 0;
    }
}

