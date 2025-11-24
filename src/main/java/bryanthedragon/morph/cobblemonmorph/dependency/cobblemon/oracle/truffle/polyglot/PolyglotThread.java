
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotEngineOptions;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotLanguageInstance;
import java.util.concurrent.atomic.AtomicInteger;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class PolyglotThread
extends Thread {
    private final PolyglotLanguageContext languageContext;
    private final CallTarget callTarget;
    volatile boolean hardExitNotificationThread;
    private static final AtomicInteger THREAD_INIT_NUMBER = new AtomicInteger(0);

    PolyglotThread(PolyglotLanguageContext languageContext, Runnable runnable, ThreadGroup group, long stackSize) {
        super(group, runnable, PolyglotThread.createDefaultName(languageContext), stackSize);
        this.languageContext = languageContext;
        this.setUncaughtExceptionHandler(languageContext.getPolyglotExceptionHandler());
        this.callTarget = ThreadSpawnRootNode.lookup(languageContext.getLanguageInstance());
    }

    private static String createDefaultName(PolyglotLanguageContext creator) {
        return "Polyglot-" + creator.language.getId() + "-" + THREAD_INIT_NUMBER.getAndIncrement();
    }

    PolyglotContextImpl getOwnerContext() {
        return this.languageContext.context;
    }

    @Override
    public synchronized void start() {
        Thread currentThread;
        PolyglotContextImpl polyglotContext = this.languageContext.context;
        Thread hardExitTriggeringThread = polyglotContext.closeExitedTriggerThread;
        if (hardExitTriggeringThread != null && (hardExitTriggeringThread == (currentThread = PolyglotThread.currentThread()) || currentThread instanceof PolyglotThread && ((PolyglotThread)currentThread).getOwnerContext() == polyglotContext && ((PolyglotThread)currentThread).hardExitNotificationThread)) {
            this.hardExitNotificationThread = true;
        }
        super.start();
    }

    @Override
    public void run() {
        try {
            this.callTarget.call(this.languageContext, this, new PolyglotThreadRunnable(){

                @Override
                @CompilerDirectives.TruffleBoundary
                public void execute() {
                    PolyglotThread.super.run();
                }
            });
        }
        catch (Throwable t) {
            throw PolyglotImpl.engineToLanguageException(t);
        }
    }

    static final class ThreadSpawnRootNode
    extends RootNode {
        ThreadSpawnRootNode(PolyglotLanguageInstance languageInstance) {
            super(languageInstance.spi);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            Object[] args = frame.getArguments();
            return ThreadSpawnRootNode.executeImpl((PolyglotLanguageContext)args[0], (PolyglotThread)args[1], (PolyglotThreadRunnable)args[2]);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @CompilerDirectives.TruffleBoundary
        private static Object executeImpl(PolyglotLanguageContext languageContext, PolyglotThread thread, PolyglotThreadRunnable run2) {
            Object[] prev = languageContext.enterThread(thread);
            assert (prev == null);
            try (AbstractPolyglotImpl.ThreadScope scope = languageContext.getImpl().getRootImpl().createThreadScope();){
                run2.execute();
            }
            catch (PolyglotEngineImpl.CancelExecution cancel2) {
                if (PolyglotEngineOptions.TriggerUncaughtExceptionHandlerForCancel.getValue(languageContext.context.engine.getEngineOptionValues()).booleanValue()) {
                    throw cancel2;
                }
                Object var5_7 = null;
                return var5_7;
            }
            finally {
                languageContext.leaveAndDisposePolyglotThread(prev, thread);
            }
            return null;
        }

        @Override
        public boolean isInternal() {
            return true;
        }

        public static CallTarget lookup(PolyglotLanguageInstance languageInstance) {
            CallTarget target = languageInstance.lookupCallTarget(ThreadSpawnRootNode.class);
            if (target == null) {
                target = languageInstance.installCallTarget(new ThreadSpawnRootNode(languageInstance));
            }
            return target;
        }
    }

    private static interface PolyglotThreadRunnable {
        public void execute();
    }
}

