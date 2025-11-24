
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.instrumentation.EventBinding;
import java.util.concurrent.atomic.AtomicReference;

final class UnwindException
extends ThreadDeath {
    private static final long serialVersionUID = -8034021436021506591L;
    private final Object info;
    private final boolean hasPreferredBindingSet;
    private EventBinding<?> binding;
    private UnwindException next;
    private final AtomicReference<Thread> thrownThread;
    private boolean thrownFromBindingCalled;
    boolean notifiedOnReturnValue;

    UnwindException(Object info, EventBinding<?> preferredBinding) {
        this.info = info;
        this.hasPreferredBindingSet = preferredBinding != null;
        this.binding = preferredBinding;
        boolean assertions = false;
        if (!$assertionsDisabled) {
            assertions = true;
            if (!true) {
                throw new AssertionError();
            }
        }
        this.thrownThread = assertions ? new AtomicReference() : null;
    }

    void thrownFromBinding(EventBinding<?> unwindBinding) {
        this.thrownFromBindingCalled = true;
        assert (unwindBinding != null);
        assert (this.hasPreferredBindingSet || this.binding == null || this.binding == unwindBinding);
        if (this.binding == null) {
            this.binding = unwindBinding;
        }
        assert (this.thrownThread == null || this.checkThrownConsistency());
    }

    @CompilerDirectives.TruffleBoundary
    private boolean checkThrownConsistency() {
        Thread currentThread = Thread.currentThread();
        Thread oldThread = this.thrownThread.getAndSet(currentThread);
        if (oldThread != null && oldThread != currentThread) {
            throw new IllegalStateException("A single instance of UnwindException thrown in two threads: '" + oldThread + "' and '" + currentThread + "'");
        }
        return true;
    }

    boolean hasPreferredBinding() {
        return this.hasPreferredBindingSet;
    }

    boolean isThrownFromBinding() {
        return this.thrownFromBindingCalled;
    }

    EventBinding<?> getBinding() {
        return this.binding;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

    @CompilerDirectives.TruffleBoundary
    void addNext(UnwindException ex) {
        if (this.next == null) {
            this.next = ex;
        } else {
            this.next.addNext(ex);
        }
    }

    UnwindException getNext() {
        return this.next;
    }

    Object getInfo() {
        return this.info;
    }

    @CompilerDirectives.TruffleBoundary
    void resetBoundary(EventBinding<?> unwindBinding) {
        if (this.binding == unwindBinding) {
            this.resetThread();
        } else if (this.next != null) {
            this.next.resetBoundary(unwindBinding);
            if (this.next.binding == unwindBinding) {
                this.next = this.next.next;
            }
        }
    }

    void resetThread() {
        assert (this.thrownThread == null || this.resetThreadBoundary());
    }

    @CompilerDirectives.TruffleBoundary
    private boolean resetThreadBoundary() {
        this.thrownThread.set(null);
        return true;
    }
}

