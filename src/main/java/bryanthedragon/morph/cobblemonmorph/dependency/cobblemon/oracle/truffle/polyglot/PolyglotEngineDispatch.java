package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.instrumentation.EventBinding;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.ExecutionEventNode;
import com.oracle.truffle.api.instrumentation.ExecutionEventNodeFactory;
import com.oracle.truffle.api.instrumentation.Instrumenter;
import com.oracle.truffle.api.instrumentation.SourceSectionFilter;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.EnvironmentAccess;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Instrument;
import org.graalvm.polyglot.Language;
import org.graalvm.polyglot.PolyglotAccess;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.io.FileSystem;
import org.graalvm.polyglot.io.ProcessHandler;
import org.graalvm.polyglot.management.ExecutionEvent;
import org.graalvm.polyglot.management.ExecutionListener;

final class PolyglotEngineDispatch extends AbstractPolyglotImpl.AbstractEngineDispatch {
   private final PolyglotImpl polyglot;

   protected PolyglotEngineDispatch(PolyglotImpl polyglot) {
      super(polyglot);
      this.polyglot = polyglot;
   }

   @Override
   public void setAPI(Object oreceiver, Engine engine) {
      ((PolyglotEngineImpl)oreceiver).api = engine;
   }

   @Override
   public Language requirePublicLanguage(Object oreceiver, String id) {
      PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;

      try {
         return receiver.requirePublicLanguage(id);
      } catch (Throwable var5) {
         throw PolyglotImpl.guestToHostException(receiver, var5);
      }
   }

   @Override
   public Instrument requirePublicInstrument(Object oreceiver, String id) {
      PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;

      try {
         return receiver.requirePublicInstrument(id);
      } catch (Throwable var5) {
         throw PolyglotImpl.guestToHostException(receiver, var5);
      }
   }

   @Override
   public void close(Object oreceiver, Object apiObject, boolean cancelIfExecuting) {
      PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;

      try {
         receiver.ensureClosed(cancelIfExecuting, false, false);
      } catch (Throwable var6) {
         throw PolyglotImpl.guestToHostException(receiver, var6);
      }
   }

   @Override
   public Map<String, Instrument> getInstruments(Object oreceiver) {
      PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;

      try {
         return receiver.getInstruments();
      } catch (Throwable var4) {
         throw PolyglotImpl.guestToHostException(receiver, var4);
      }
   }

   @Override
   public Map<String, Language> getLanguages(Object oreceiver) {
      PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;

      try {
         return receiver.getLanguages();
      } catch (Throwable var4) {
         throw PolyglotImpl.guestToHostException(receiver, var4);
      }
   }

   @Override
   public OptionDescriptors getOptions(Object oreceiver) {
      PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;

      try {
         return receiver.getOptions();
      } catch (Throwable var4) {
         throw PolyglotImpl.guestToHostException(receiver, var4);
      }
   }

   @Override
   public Context createContext(
      Object oreceiver,
      OutputStream out,
      OutputStream err,
      InputStream in,
      boolean allowHostLookup,
      HostAccess hostAccess,
      PolyglotAccess polyglotAccess,
      boolean allowNativeAccess,
      boolean allowCreateThread,
      boolean allowHostIO,
      boolean allowHostClassLoading,
      boolean allowInnerContextOptions,
      boolean allowExperimentalOptions,
      Predicate<String> classFilter,
      Map<String, String> options,
      Map<String, String[]> arguments,
      String[] onlyLanguages,
      FileSystem fileSystem,
      Object logHandlerOrStream,
      boolean allowCreateProcess,
      ProcessHandler processHandler,
      EnvironmentAccess environmentAccess,
      Map<String, String> environment,
      ZoneId zone,
      Object limitsImpl,
      String currentWorkingDirectory,
      ClassLoader hostClassLoader,
      boolean allowValueSharing,
      boolean useSystemExit
   ) {
      PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;
      PolyglotContextImpl context = receiver.createContext(
         out,
         err,
         in,
         allowHostLookup,
         hostAccess,
         polyglotAccess,
         allowNativeAccess,
         allowCreateThread,
         allowHostIO,
         allowHostClassLoading,
         allowInnerContextOptions,
         allowExperimentalOptions,
         classFilter,
         options,
         arguments,
         onlyLanguages,
         fileSystem,
         logHandlerOrStream,
         allowCreateProcess,
         processHandler,
         environmentAccess,
         environment,
         zone,
         limitsImpl,
         currentWorkingDirectory,
         hostClassLoader,
         allowValueSharing,
         useSystemExit
      );
      return this.polyglot.getAPIAccess().newContext(this.polyglot.contextDispatch, context, context.engine.api);
   }

   @Override
   public String getImplementationName(Object oreceiver) {
      PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;

      try {
         return Truffle.getRuntime().getName();
      } catch (Throwable var4) {
         throw PolyglotImpl.guestToHostException(receiver, var4);
      }
   }

   @Override
   public Set<Source> getCachedSources(Object oreceiver) {
      PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;

      try {
         return receiver.getCachedSources();
      } catch (Throwable var4) {
         throw PolyglotImpl.guestToHostException(receiver, var4);
      }
   }

   @Override
   public String getVersion(Object oreceiver) {
      PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;

      try {
         return receiver.getVersion();
      } catch (Throwable var4) {
         throw PolyglotImpl.guestToHostException(receiver, var4);
      }
   }

   @Override
   public ExecutionListener attachExecutionListener(
      Object engineReceiver,
      Consumer<ExecutionEvent> onEnter,
      Consumer<ExecutionEvent> onReturn,
      boolean expressions,
      boolean statements,
      boolean roots,
      Predicate<Source> sourceFilter,
      Predicate<String> rootFilter,
      boolean collectInputValues,
      boolean collectReturnValues,
      boolean collectExceptions
   ) {
      final PolyglotEngineImpl engine = (PolyglotEngineImpl)engineReceiver;
      Instrumenter instrumenter = (Instrumenter)EngineAccessor.INSTRUMENT.getEngineInstrumenter(engine.instrumentationHandler);
      List<Class<? extends Tag>> tags = new ArrayList<>();
      if (expressions) {
         tags.add(StandardTags.ExpressionTag.class);
      }

      if (statements) {
         tags.add(StandardTags.StatementTag.class);
      }

      if (roots) {
         tags.add(StandardTags.RootTag.class);
      }

      if (tags.isEmpty()) {
         throw new IllegalArgumentException(
            "No elements specified to listen to for execution listener. Need to specify at least one element kind: expressions, statements or roots."
         );
      } else if (onReturn == null && onEnter == null) {
         throw new IllegalArgumentException("At least one event consumer must be provided for onEnter or onReturn.");
      } else {
         SourceSectionFilter.Builder filterBuilder = SourceSectionFilter.newBuilder().tagIs(tags.toArray(new Class[0]));
         filterBuilder.includeInternal(false);
         final PolyglotExecutionListenerDispatch.ListenerImpl config = new PolyglotExecutionListenerDispatch.ListenerImpl(
            this.polyglot.getExecutionEventDispatch(), engine, onEnter, onReturn, collectInputValues, collectReturnValues, collectExceptions
         );
         filterBuilder.sourceIs(new SourceSectionFilter.SourcePredicate() {
            @Override
            public boolean test(com.oracle.truffle.api.source.Source s) {
               String language = s.getLanguage();
               if (language == null) {
                  return false;
               } else if (!engine.idToLanguage.containsKey(language)) {
                  return false;
               } else if (sourceFilter != null) {
                  try {
                     return sourceFilter.test(PolyglotImpl.getOrCreatePolyglotSource(PolyglotEngineDispatch.this.polyglot, s));
                  } catch (Throwable var4) {
                     if (config.closing) {
                        return false;
                     } else {
                        throw engine.host.toHostException(null, var4);
                     }
                  }
               } else {
                  return true;
               }
            }
         });
         if (rootFilter != null) {
            filterBuilder.rootNameIs(new Predicate<String>() {
               public boolean test(String s) {
                  try {
                     return rootFilter.test(s);
                  } catch (Throwable var3) {
                     if (config.closing) {
                        return false;
                     } else {
                        throw engine.host.toHostException(null, var3);
                     }
                  }
               }
            });
         }

         SourceSectionFilter filter = filterBuilder.build();

         EventBinding<?> binding;
         try {
            boolean mayNeedInputValues = config.collectInputValues && config.onReturn != null;
            boolean mayNeedReturnValue = config.collectReturnValues && config.onReturn != null;
            boolean mayNeedExceptions = config.collectExceptions;
            if (!mayNeedInputValues && !mayNeedReturnValue && !mayNeedExceptions) {
               binding = instrumenter.attachExecutionEventFactory(filter, null, new ExecutionEventNodeFactory() {
                  @Override
                  public ExecutionEventNode create(EventContext context) {
                     return new PolyglotExecutionListenerDispatch.DefaultNode(config, context);
                  }
               });
            } else {
               binding = instrumenter.attachExecutionEventFactory(filter, mayNeedInputValues ? filter : null, new ExecutionEventNodeFactory() {
                  @Override
                  public ExecutionEventNode create(EventContext context) {
                     return new PolyglotExecutionListenerDispatch.ProfilingNode(config, context);
                  }
               });
            }
         } catch (Throwable var22) {
            throw PolyglotImpl.guestToHostException(engine, var22);
         }

         config.binding = binding;
         return this.polyglot.getManagement().newExecutionListener(this.polyglot.getExecutionListenerDispatch(), config);
      }
   }

   @Override
   public void shutdown(Object engine) {
      ((PolyglotEngineImpl)engine).onVMShutdown();
   }
}
