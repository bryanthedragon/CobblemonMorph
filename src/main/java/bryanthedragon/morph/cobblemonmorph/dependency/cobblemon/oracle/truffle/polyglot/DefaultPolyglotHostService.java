
package com.oracle.truffle.polyglot;

import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

class DefaultPolyglotHostService
extends AbstractPolyglotImpl.AbstractPolyglotHostService {
    DefaultPolyglotHostService(AbstractPolyglotImpl polyglot) {
        super(polyglot);
    }

    @Override
    public void patch(AbstractPolyglotImpl.AbstractPolyglotHostService otherService) {
    }

    @Override
    public void notifyClearExplicitContextStack(Object contextReceiver) {
    }

    @Override
    public void notifyContextCancellingOrExiting(Object contextReceiver, boolean exit, int exitCode, boolean resourceLimit, String message) {
    }

    @Override
    public void notifyContextClosed(Object contextReceiver, boolean cancelIfExecuting, boolean resourceLimit, String message) {
    }

    @Override
    public void notifyEngineClosed(Object engineReceiver, boolean cancelIfExecuting) {
    }
}

