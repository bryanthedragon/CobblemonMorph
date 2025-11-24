
package com.oracle.truffle.polyglot;

import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotInstrument;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

abstract class SystemThread
extends Thread {
    private final PolyglotImpl polyglot;

    SystemThread(Runnable runnable, ThreadGroup threadGroup, PolyglotImpl polyglot) {
        super(threadGroup, runnable);
        this.polyglot = polyglot;
    }

    abstract void beforeExecute();

    abstract void afterExecute();

    @Override
    public final void run() {
        AbstractPolyglotImpl rootPolyglot = this.polyglot.getRootImpl();
        try (AbstractPolyglotImpl.ThreadScope threadScope = rootPolyglot.createThreadScope();){
            this.beforeExecute();
            try {
                super.run();
            }
            finally {
                this.afterExecute();
            }
        }
    }

    static final class LanguageSystemThread
    extends SystemThread {
        final String languageId;
        private final PolyglotContextImpl polyglotContext;

        LanguageSystemThread(PolyglotLanguageContext polyglotLanguageContext, Runnable runnable, ThreadGroup threadGroup) {
            super(runnable, threadGroup, polyglotLanguageContext.context.engine.impl);
            this.languageId = polyglotLanguageContext.language.getId();
            this.polyglotContext = polyglotLanguageContext.context;
            this.checkClosed();
        }

        @Override
        void beforeExecute() {
            this.polyglotContext.addSystemThread(this);
            this.checkClosed();
        }

        @Override
        void afterExecute() {
            this.polyglotContext.removeSystemThread(this);
        }

        private void checkClosed() {
            if (this.polyglotContext.state.isClosed()) {
                throw new IllegalStateException(String.format("Context is already closed. Cannot start a new system thread for language %s.", this.languageId));
            }
        }
    }

    static final class InstrumentSystemThread
    extends SystemThread {
        final String instrumentId;
        private final PolyglotEngineImpl engine;

        InstrumentSystemThread(PolyglotInstrument polyglotInstrument, Runnable runnable, ThreadGroup threadGroup) {
            super(runnable, threadGroup, polyglotInstrument.engine.impl);
            this.instrumentId = polyglotInstrument.getId();
            this.engine = polyglotInstrument.engine;
            this.checkClosed();
        }

        @Override
        void beforeExecute() {
            this.engine.addSystemThread(this);
            this.checkClosed();
        }

        @Override
        void afterExecute() {
            this.engine.removeSystemThread(this);
        }

        private void checkClosed() {
            if (this.engine.closed) {
                throw new IllegalStateException(String.format("Engine is already closed. Cannot start a new system thread for instrument %s.", this.instrumentId));
            }
        }
    }
}

