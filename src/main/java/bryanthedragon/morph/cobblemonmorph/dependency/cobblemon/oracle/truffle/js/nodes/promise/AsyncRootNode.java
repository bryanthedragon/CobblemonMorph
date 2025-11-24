
package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public interface AsyncRootNode {
    public static final int CALL_TARGET_INDEX = 0;
    public static final int GENERATOR_OBJECT_OR_PROMISE_CAPABILITY_INDEX = 1;
    public static final int ASYNC_FRAME_INDEX = 2;
    public static final int STACK_TRACE_INDEX = 3;

    public JSDynamicObject getAsyncFunctionPromise(Frame var1);

    public static Object[] createAsyncContext(CallTarget resumeTarget, Object generatorObjectOrPromiseCapability, MaterializedFrame asyncFrame) {
        return new Object[]{resumeTarget, generatorObjectOrPromiseCapability, asyncFrame, null};
    }
}

