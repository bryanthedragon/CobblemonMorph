package com.oracle.truffle.api.instrumentation;

public interface AllocationListener {
   void onEnter(AllocationEvent event);

   void onReturnValue(AllocationEvent event);
}
