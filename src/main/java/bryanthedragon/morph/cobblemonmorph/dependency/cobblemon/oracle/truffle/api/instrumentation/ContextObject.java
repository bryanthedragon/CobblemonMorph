
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.instrumentation.AllocationReporter;
import com.oracle.truffle.api.nodes.Node;

class ContextObject {
    private final AllocationReporter reporter;

    ContextObject(AllocationReporter reporter) {
        this.reporter = reporter;
    }

    public AllocationReporter getReporter() {
        return this.reporter;
    }

    static ContextObject get(Node node) {
        return null;
    }
}

