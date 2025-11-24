
package com.oracle.truffle.api.utilities;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class CyclicAssumption {
    private final String name;
    private volatile Assumption assumption;
    private static final AtomicReferenceFieldUpdater<CyclicAssumption, Assumption> ASSUMPTION_UPDATER = AtomicReferenceFieldUpdater.newUpdater(CyclicAssumption.class, Assumption.class, "assumption");

    public CyclicAssumption(String name) {
        this.name = name;
        this.assumption = Truffle.getRuntime().createAssumption(name);
    }

    @CompilerDirectives.TruffleBoundary
    public void invalidate() {
        this.invalidate("");
    }

    @CompilerDirectives.TruffleBoundary
    public void invalidate(String message) {
        Assumption newAssumption = Truffle.getRuntime().createAssumption(this.name);
        Assumption oldAssumption = ASSUMPTION_UPDATER.getAndSet(this, newAssumption);
        oldAssumption.invalidate(message);
    }

    public Assumption getAssumption() {
        CompilerAsserts.neverPartOfCompilation("Cache the Assumption and do not call getAssumption() on the fast path");
        return this.assumption;
    }
}

