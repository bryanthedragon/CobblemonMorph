
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.js.runtime.JSAgent;
import com.oracle.truffle.js.runtime.PromiseRejectionTracker;

public final class MainJSAgent
extends JSAgent {
    public MainJSAgent(PromiseRejectionTracker promiseRejectionTracker) {
        super(promiseRejectionTracker, false);
    }

    @Override
    public void terminate() {
    }

    @Override
    public void wake() {
    }
}

