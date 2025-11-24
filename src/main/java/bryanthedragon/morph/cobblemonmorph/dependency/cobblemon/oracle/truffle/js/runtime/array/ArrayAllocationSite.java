
package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.js.runtime.array.ScriptArray;

public interface ArrayAllocationSite {
    default public void notifyArrayTransition(ScriptArray arrayType, int length) {
    }

    default public ScriptArray getInitialArrayType() {
        return null;
    }
}

