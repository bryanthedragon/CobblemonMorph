
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.EventBinding;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.ExecutionEventNode;
import com.oracle.truffle.api.instrumentation.ExecutionEventNodeFactory;
import com.oracle.truffle.api.instrumentation.Instrumenter;
import com.oracle.truffle.api.instrumentation.SourceSectionFilter;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineException;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotFastThreadLocals;
import com.oracle.truffle.polyglot.PolyglotImpl;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.graalvm.polyglot.ResourceLimitEvent;

final class PolyglotLimits {
    final long statementLimit;
    final Predicate<org.graalvm.polyglot.Source> statementLimitSourcePredicate;
    final Consumer<ResourceLimitEvent> onEvent;
    static final Object CACHED_CONTEXT = new Object(){

        public String toString() {
            return "$$$cached_context$$$";
        }
    };

    PolyglotLimits(long statementLimit, Predicate<org.graalvm.polyglot.Source> statementLimitSourcePredicate, Consumer<ResourceLimitEvent> onEvent) {
        this.statementLimit = statementLimit;
        this.statementLimitSourcePredicate = statementLimitSourcePredicate;
        this.onEvent = onEvent;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void reset(PolyglotContextImpl context) {
        PolyglotContextImpl polyglotContextImpl = context;
        synchronized (polyglotContextImpl) {
            context.statementCounter = context.statementLimit;
            context.volatileStatementCounter.set(context.statementLimit);
        }
    }

    static final class EngineLimits {
        private static final Predicate<org.graalvm.polyglot.Source> NO_PREDICATE = new Predicate<org.graalvm.polyglot.Source>(){

            @Override
            public boolean test(org.graalvm.polyglot.Source t) {
                return true;
            }
        };
        final PolyglotEngineImpl engine;
        @CompilerDirectives.CompilationFinal
        long statementLimit = -1L;
        @CompilerDirectives.CompilationFinal
        Assumption sameStatementLimit;
        @CompilerDirectives.CompilationFinal
        Predicate<org.graalvm.polyglot.Source> statementLimitSourcePredicate;
        EventBinding<?> statementLimitBinding;

        EngineLimits(PolyglotEngineImpl engine) {
            this.engine = engine;
        }

        void validate(PolyglotLimits limits) {
            if (limits != null && limits.statementLimit != 0L) {
                Predicate<org.graalvm.polyglot.Source> newPredicate = limits.statementLimitSourcePredicate;
                if (newPredicate == null) {
                    newPredicate = NO_PREDICATE;
                }
                if (this.statementLimitSourcePredicate != null && newPredicate != this.statementLimitSourcePredicate) {
                    throw PolyglotEngineException.illegalArgument("Using multiple source predicates per engine is not supported. The same statement limit source predicate must be used for all polyglot contexts that are assigned to the same engine. Resolve this by using the same predicate instance when constructing the limits object with ResourceLimits.Builder.statementLimit(long, Predicate).");
                }
            }
        }

        void initialize(PolyglotLimits limits, final PolyglotContextImpl context) {
            assert (Thread.holdsLock(this.engine.lock));
            if (limits.statementLimit != 0L) {
                Predicate<org.graalvm.polyglot.Source> newPredicate = limits.statementLimitSourcePredicate;
                if (newPredicate == null) {
                    newPredicate = NO_PREDICATE;
                }
                if (this.statementLimitSourcePredicate == null) {
                    this.statementLimitSourcePredicate = newPredicate;
                }
                assert (this.statementLimitSourcePredicate == newPredicate);
                Assumption sameLimit = this.sameStatementLimit;
                if (sameLimit != null && sameLimit.isValid() && limits.statementLimit != this.statementLimit) {
                    sameLimit.invalidate();
                } else if (sameLimit == null) {
                    this.sameStatementLimit = Truffle.getRuntime().createAssumption("Same statement limit.");
                    this.statementLimit = limits.statementLimit;
                }
                if (this.statementLimitBinding == null) {
                    Instrumenter instrumenter = (Instrumenter)EngineAccessor.INSTRUMENT.getEngineInstrumenter(this.engine.instrumentationHandler);
                    SourceSectionFilter.Builder filter = SourceSectionFilter.newBuilder().tagIs(StandardTags.StatementTag.class);
                    if (this.statementLimitSourcePredicate != null) {
                        filter.sourceIs(new SourceSectionFilter.SourcePredicate(){

                            @Override
                            public boolean test(Source s) {
                                try {
                                    return statementLimitSourcePredicate.test(PolyglotImpl.getOrCreatePolyglotSource(engine.getImpl(), s));
                                }
                                catch (Throwable e) {
                                    throw context.engine.host.toHostException(context.getHostContextImpl(), e);
                                }
                            }
                        });
                    }
                    this.statementLimitBinding = instrumenter.attachExecutionEventFactory(filter.build(), new ExecutionEventNodeFactory(){

                        @Override
                        public ExecutionEventNode create(EventContext eventContext) {
                            return new StatementIncrementNode(eventContext, this);
                        }
                    });
                }
            }
            PolyglotLimits.reset(context);
        }

        long getStatementLimit() {
            return this.statementLimit;
        }

        RuntimeException notifyEvent(PolyglotContextImpl context) {
            PolyglotLimits limits = context.config.limits;
            if (limits == null) {
                return null;
            }
            Consumer<ResourceLimitEvent> onEvent = limits.onEvent;
            if (onEvent == null) {
                return null;
            }
            ResourceLimitEvent event = this.engine.getImpl().getAPIAccess().newResourceLimitsEvent(context.api);
            try {
                onEvent.accept(event);
            }
            catch (Throwable t) {
                throw context.engine.host.toHostException(context.getHostContextImpl(), t);
            }
            return null;
        }
    }

    static final class StatementIncrementNode
    extends ExecutionEventNode {
        final EngineLimits limits;
        final EventContext eventContext;
        final PolyglotEngineImpl engine;
        @CompilerDirectives.CompilationFinal
        private boolean seenInnerContext;

        StatementIncrementNode(EventContext context, EngineLimits limits) {
            this.limits = limits;
            this.eventContext = context;
            this.engine = limits.engine;
        }

        @Override
        protected void onEnter(VirtualFrame frame) {
            PolyglotContextImpl currentContext = this.getLimitContext();
            long count = this.engine.singleThreadPerContext.isValid() ? --currentContext.statementCounter : currentContext.volatileStatementCounter.decrementAndGet();
            if (count < 0L) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.notifyStatementLimitReached(currentContext, currentContext.statementLimit - count, currentContext.statementLimit);
            }
        }

        private PolyglotContextImpl getLimitContext() {
            PolyglotContextImpl context = PolyglotFastThreadLocals.getContextWithEngine(this.engine);
            if (this.engine.noInnerContexts.isValid() || context.parent == null) {
                return context;
            }
            if (!this.seenInnerContext) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.seenInnerContext = true;
            }
            while (context.parent != null) {
                context = context.parent;
            }
            return context;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void notifyStatementLimitReached(PolyglotContextImpl context, long actualCount, long limit) {
            boolean limitReached = false;
            PolyglotContextImpl polyglotContextImpl = context;
            synchronized (polyglotContextImpl) {
                if (this.limits.engine.singleThreadPerContext.isValid()) {
                    if (context.statementCounter < 0L) {
                        context.statementCounter = limit;
                        limitReached = true;
                    }
                } else if (context.volatileStatementCounter.get() < 0L) {
                    context.volatileStatementCounter.set(limit);
                    limitReached = true;
                }
            }
            if (limitReached) {
                context.cancel(true, String.format("Statement count limit of %s exceeded. Statements executed %s.", limit, actualCount));
                RuntimeException e = this.limits.notifyEvent(context);
                if (e != null) {
                    throw e;
                }
                TruffleSafepoint.pollHere(this.eventContext.getInstrumentedNode());
            }
        }
    }
}

