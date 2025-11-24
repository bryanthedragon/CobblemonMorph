
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.instrumentation.Tag;

public final class DebuggerTags {
    private DebuggerTags() {
    }

    public final class AlwaysHalt
    extends Tag {
        private AlwaysHalt() {
        }
    }
}

