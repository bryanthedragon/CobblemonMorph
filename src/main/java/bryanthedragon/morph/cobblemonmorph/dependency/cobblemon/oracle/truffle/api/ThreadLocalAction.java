
package com.oracle.truffle.api;

import com.oracle.truffle.api.nodes.Node;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

public abstract class ThreadLocalAction {
    private final boolean hasSideEffects;
    private final boolean synchronous;
    private final boolean recurring;

    protected ThreadLocalAction(boolean hasSideEffects, boolean synchronous) {
        this(hasSideEffects, synchronous, false);
    }

    protected ThreadLocalAction(boolean hasSideEffects, boolean synchronous, boolean recurring) {
        this.hasSideEffects = hasSideEffects;
        this.synchronous = synchronous;
        this.recurring = recurring;
    }

    final boolean isSynchronous() {
        return this.synchronous;
    }

    final boolean hasSideEffects() {
        return this.hasSideEffects;
    }

    final boolean isRecurring() {
        return this.recurring;
    }

    protected abstract void perform(Access var1);

    public static abstract class Access {
        protected Access(AbstractPolyglotImpl impl) {
            if (impl == null) {
                throw new AssertionError((Object)"Constructor for framework use only.");
            }
        }

        public abstract Node getLocation();

        public abstract Thread getThread();
    }
}

