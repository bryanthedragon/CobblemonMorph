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
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.graalvm.polyglot.ResourceLimitEvent;
import org.graalvm.polyglot.Source;

final class PolyglotLimits {
   final long statementLimit;
   final Predicate<Source> statementLimitSourcePredicate;
   final Consumer<ResourceLimitEvent> onEvent;
   static final Object CACHED_CONTEXT = new Object() {
      @Override
      public String toString() {
         return "$$$cached_context$$$";
      }
   };

   PolyglotLimits(long statementLimit, Predicate<Source> statementLimitSourcePredicate, Consumer<ResourceLimitEvent> onEvent) {
      this.statementLimit = statementLimit;
      this.statementLimitSourcePredicate = statementLimitSourcePredicate;
      this.onEvent = onEvent;
   }

   static void reset(PolyglotContextImpl context) {
      synchronized (context) {
         context.statementCounter = context.statementLimit;
         context.volatileStatementCounter.set(context.statementLimit);
      }
   }

   static final class EngineLimits {
      private static final Predicate<Source> NO_PREDICATE = new Predicate<Source>() {
         public boolean test(Source t) {
            return true;
         }
      };
      final PolyglotEngineImpl engine;
      @CompilerDirectives.CompilationFinal
      long statementLimit = -1L;
      @CompilerDirectives.CompilationFinal
      Assumption sameStatementLimit;
      @CompilerDirectives.CompilationFinal
      Predicate<Source> statementLimitSourcePredicate;
      EventBinding<?> statementLimitBinding;

      EngineLimits(PolyglotEngineImpl engine) {
         this.engine = engine;
      }

      void validate(PolyglotLimits limits) {
         if (limits != null && limits.statementLimit != 0L) {
            Predicate<Source> newPredicate = limits.statementLimitSourcePredicate;
            if (newPredicate == null) {
               newPredicate = NO_PREDICATE;
            }

            if (this.statementLimitSourcePredicate != null && newPredicate != this.statementLimitSourcePredicate) {
               throw PolyglotEngineException.illegalArgument(
                  "Using multiple source predicates per engine is not supported. The same statement limit source predicate must be used for all polyglot contexts that are assigned to the same engine. Resolve this by using the same predicate instance when constructing the limits object with ResourceLimits.Builder.statementLimit(long, Predicate)."
               );
            }
         }
      }

      void initialize(PolyglotLimits limits, PolyglotContextImpl context) {
         assert Thread.holdsLock(this.engine.lock);

         if (limits.statementLimit != 0L) {
            Predicate<Source> newPredicate = limits.statementLimitSourcePredicate;
            if (newPredicate == null) {
               newPredicate = NO_PREDICATE;
            }

            if (this.statementLimitSourcePredicate == null) {
               this.statementLimitSourcePredicate = newPredicate;
            }

            assert this.statementLimitSourcePredicate == newPredicate;

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
                  filter.sourceIs(
                     new SourceSectionFilter.SourcePredicate() {
                        @Override
                        public boolean test(com.oracle.truffle.api.source.Source s) {
                           try {
                              return EngineLimits.this.statementLimitSourcePredicate
                                 .test(PolyglotImpl.getOrCreatePolyglotSource(EngineLimits.this.engine.getImpl(), s));
                           } catch (Throwable var3) {
                              throw context.engine.host.toHostException(context.getHostContextImpl(), var3);
                           }
                        }
                     }
                  );
               }

               this.statementLimitBinding = instrumenter.attachExecutionEventFactory(filter.build(), new ExecutionEventNodeFactory() {
                  @Override
                  public ExecutionEventNode create(EventContext eventContext) {
                     return new PolyglotLimits.StatementIncrementNode(eventContext, EngineLimits.this);
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
         } else {
            Consumer<ResourceLimitEvent> onEvent = limits.onEvent;
            if (onEvent == null) {
               return null;
            } else {
               ResourceLimitEvent event = this.engine.getImpl().getAPIAccess().newResourceLimitsEvent(context.api);

               try {
                  onEvent.accept(event);
                  return null;
               } catch (Throwable var6) {
                  throw context.engine.host.toHostException(context.getHostContextImpl(), var6);
               }
            }
         }
      }
   }

   static final class StatementIncrementNode extends ExecutionEventNode {
      final PolyglotLimits.EngineLimits limits;
      final EventContext eventContext;
      final PolyglotEngineImpl engine;
      @CompilerDirectives.CompilationFinal
      private boolean seenInnerContext;

      StatementIncrementNode(EventContext context, PolyglotLimits.EngineLimits limits) {
         this.limits = limits;
         this.eventContext = context;
         this.engine = limits.engine;
      }

      @Override
      protected void onEnter(VirtualFrame frame) {
         PolyglotContextImpl currentContext = this.getLimitContext();
         long count;
         if (this.engine.singleThreadPerContext.isValid()) {
            count = --currentContext.statementCounter;
         } else {
            count = currentContext.volatileStatementCounter.decrementAndGet();
         }

         if (count < 0L) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.notifyStatementLimitReached(currentContext, currentContext.statementLimit - count, currentContext.statementLimit);
         }
      }

      private PolyglotContextImpl getLimitContext() {
         PolyglotContextImpl context = PolyglotFastThreadLocals.getContextWithEngine(this.engine);
         if (!this.engine.noInnerContexts.isValid() && context.parent != null) {
            if (!this.seenInnerContext) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.seenInnerContext = true;
            }

            while (context.parent != null) {
               context = context.parent;
            }

            return context;
         } else {
            return context;
         }
      }

      private void notifyStatementLimitReached(PolyglotContextImpl context, long actualCount, long limit) {
         boolean limitReached = false;
         synchronized (context) {
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
