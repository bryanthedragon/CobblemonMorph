package com.oracle.truffle.js.runtime.array;

public interface ArrayAllocationSite {
   default void notifyArrayTransition(ScriptArray arrayType, int length) {
   }

   default ScriptArray getInitialArrayType() {
      return null;
   }
}
