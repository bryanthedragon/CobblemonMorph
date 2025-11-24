
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.source.Source;

public final class ExecuteSourceEvent {
    private final Source source;

    ExecuteSourceEvent(Source source) {
        this.source = source;
    }

    public Source getSource() {
        return this.source;
    }
}

