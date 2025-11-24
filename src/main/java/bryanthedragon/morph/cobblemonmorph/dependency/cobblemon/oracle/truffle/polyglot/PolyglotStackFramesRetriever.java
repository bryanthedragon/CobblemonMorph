
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.ThreadLocalAction;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.frame.FrameInstanceVisitor;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

final class PolyglotStackFramesRetriever {
    PolyglotStackFramesRetriever() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static FrameInstance[][] getStackFrames(PolyglotContextImpl context) {
        Future<Object> future2;
        Thread[] threads;
        final ConcurrentHashMap frameInstancesByThread = new ConcurrentHashMap();
        PolyglotContextImpl polyglotContextImpl = context;
        synchronized (polyglotContextImpl) {
            threads = context.getSeenThreads().keySet().toArray(new Thread[0]);
            future2 = !context.state.isClosed() ? context.threadLocalActions.submit(null, "engine", new ThreadLocalAction(false, false){

                @Override
                protected void perform(ThreadLocalAction.Access access) {
                    final ArrayList frameInstances = new ArrayList();
                    Truffle.getRuntime().iterateFrames(new FrameInstanceVisitor<Object>(){

                        @Override
                        public Object visitFrame(FrameInstance frameInstance) {
                            return frameInstances.add(frameInstance);
                        }
                    });
                    frameInstancesByThread.put(access.getThread(), frameInstances);
                }
            }, false) : CompletableFuture.completedFuture(null);
        }
        TruffleSafepoint.setBlockedThreadInterruptible(context.uncachedLocation, new TruffleSafepoint.Interruptible<Future<Void>>(){

            @Override
            public void apply(Future<Void> arg) throws InterruptedException {
                try {
                    arg.get();
                }
                catch (ExecutionException e) {
                    throw CompilerDirectives.shouldNotReachHere(e);
                }
            }
        }, future2);
        FrameInstance[][] toRet = new FrameInstance[threads.length][];
        for (int i = 0; i < threads.length; ++i) {
            Thread thread = threads[i];
            List frameInstances = (List)frameInstancesByThread.get(thread);
            toRet[i] = frameInstances != null ? frameInstances.toArray(new FrameInstance[0]) : new FrameInstance[0];
        }
        return toRet;
    }
}

