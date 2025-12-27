package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.frame.VirtualFrame;

public interface ExecutionEventListener {
   void onEnter(EventContext context, VirtualFrame frame);

   @Deprecated(since = "20.0")
   default void onInputValue(EventContext context, VirtualFrame frame, EventContext inputContext, int inputIndex, Object inputValue) {
   }

   void onReturnValue(EventContext context, VirtualFrame frame, Object result);

   void onReturnExceptional(EventContext context, VirtualFrame frame, Throwable exception);

   default Object onUnwind(EventContext context, VirtualFrame frame, Object info) {
      return null;
   }
}
