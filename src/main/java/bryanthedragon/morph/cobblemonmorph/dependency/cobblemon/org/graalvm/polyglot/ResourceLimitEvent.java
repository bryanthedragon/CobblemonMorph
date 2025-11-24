
package org.graalvm.polyglot;

import org.graalvm.polyglot.Context;

public final class ResourceLimitEvent {
    private final Context context;

    ResourceLimitEvent(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return this.context;
    }

    public String toString() {
        StringBuilder b = new StringBuilder("ResourceLimitEvent[");
        b.append(this.getContext().toString());
        b.append("]");
        return b.toString();
    }
}

