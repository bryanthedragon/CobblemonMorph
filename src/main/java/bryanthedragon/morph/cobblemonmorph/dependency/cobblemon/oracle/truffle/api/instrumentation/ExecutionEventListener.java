
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.EventContext;

public interface ExecutionEventListener {
    public void onEnter(EventContext var1, VirtualFrame var2);

    @Deprecated(since="20.0")
    default public void onInputValue(EventContext context, VirtualFrame frame, EventContext inputContext, int inputIndex, Object inputValue) {
    }

    public void onReturnValue(EventContext var1, VirtualFrame var2, Object var3);

    public void onReturnExceptional(EventContext var1, VirtualFrame var2, Throwable var3);

    default public Object onUnwind(EventContext context, VirtualFrame frame, Object info) {
        return null;
    }
}

