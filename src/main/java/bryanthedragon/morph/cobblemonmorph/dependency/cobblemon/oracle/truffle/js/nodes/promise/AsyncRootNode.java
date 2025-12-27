package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public interface AsyncRootNode {
   int CALL_TARGET_INDEX = 0;
   int GENERATOR_OBJECT_OR_PROMISE_CAPABILITY_INDEX = 1;
   int ASYNC_FRAME_INDEX = 2;
   int STACK_TRACE_INDEX = 3;

   JSDynamicObject getAsyncFunctionPromise(Frame asyncFrame);

   static Object[] createAsyncContext(CallTarget resumeTarget, Object generatorObjectOrPromiseCapability, MaterializedFrame asyncFrame) {
      return new Object[]{resumeTarget, generatorObjectOrPromiseCapability, asyncFrame, null};
   }
}
