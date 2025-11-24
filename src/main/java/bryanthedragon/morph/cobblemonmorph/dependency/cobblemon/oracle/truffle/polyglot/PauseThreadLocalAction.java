
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.ThreadLocalAction;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

final class PauseThreadLocalAction
extends ThreadLocalAction {
    private final Object pauseSync = new Object();
    private volatile boolean pause = true;
    private volatile boolean pauseComplete;
    final PolyglotContextImpl context;

    PauseThreadLocalAction(PolyglotContextImpl context) {
        super(false, true);
        this.context = context;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void perform(ThreadLocalAction.Access access) {
        if (access.getThread() != this.context.closingThread) {
            Object object = this.pauseSync;
            synchronized (object) {
                this.pauseComplete = true;
                this.pauseSync.notifyAll();
            }
            TruffleSafepoint.setBlockedThreadInterruptible(access.getLocation(), new TruffleSafepoint.Interruptible<Object>(){

                /*
                 * WARNING - Removed try catching itself - possible behaviour change.
                 */
                @Override
                public void apply(Object waitObject) throws InterruptedException {
                    Object object = waitObject;
                    synchronized (object) {
                        PolyglotContextImpl.State localContextState = PauseThreadLocalAction.this.context.state;
                        while (PauseThreadLocalAction.this.pause && !localContextState.isClosed() && !localContextState.isCancelling() && !localContextState.isExiting()) {
                            waitObject.wait();
                        }
                    }
                }
            }, this.pauseSync);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void resume() {
        Object object = this.pauseSync;
        synchronized (object) {
            this.pause = false;
            this.pauseSync.notifyAll();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void waitUntilPaused(Future<?> actionFuture) throws InterruptedException {
        Object object = this.pauseSync;
        synchronized (object) {
            while (!this.pauseComplete && !actionFuture.isDone()) {
                this.pauseSync.wait(10L);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void waitUntilPaused(Future<?> actionFuture, long timeout, TimeUnit unit) throws InterruptedException, TimeoutException {
        long timeoutTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(timeout, unit);
        Object object = this.pauseSync;
        synchronized (object) {
            while (!this.pauseComplete && !actionFuture.isDone() && System.currentTimeMillis() < timeoutTime) {
                long remainingTime = timeoutTime - System.currentTimeMillis();
                this.pauseSync.wait(Math.max(1L, Math.min(10L, remainingTime)));
            }
            if (!this.pauseComplete && !actionFuture.isDone()) {
                throw new TimeoutException("Waiting for pause timed out!");
            }
        }
    }

    boolean wasPaused(Future<?> actionFuture) {
        return this.pauseComplete || actionFuture.isDone();
    }

    boolean isPause() {
        return this.pause;
    }
}

