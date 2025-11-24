
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.instrumentation.AllocationEvent;

public interface AllocationListener {
    public void onEnter(AllocationEvent var1);

    public void onReturnValue(AllocationEvent var1);
}

