
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.Truffle;
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
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotExecutionListenerDispatch;
import com.oracle.truffle.polyglot.PolyglotImpl;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.ZoneId;
import java.util.ArrayList;
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
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.io.FileSystem;
import org.graalvm.polyglot.io.ProcessHandler;
import org.graalvm.polyglot.management.ExecutionEvent;
import org.graalvm.polyglot.management.ExecutionListener;

final class PolyglotEngineDispatch
extends AbstractPolyglotImpl.AbstractEngineDispatch {
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
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(receiver, t);
        }
    }

    @Override
    public Instrument requirePublicInstrument(Object oreceiver, String id) {
        PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;
        try {
            return receiver.requirePublicInstrument(id);
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(receiver, t);
        }
    }

    @Override
    public void close(Object oreceiver, Object apiObject, boolean cancelIfExecuting) {
        PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;
        try {
            receiver.ensureClosed(cancelIfExecuting, false, false);
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(receiver, t);
        }
    }

    @Override
    public Map<String, Instrument> getInstruments(Object oreceiver) {
        PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;
        try {
            return receiver.getInstruments();
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(receiver, t);
        }
    }

    @Override
    public Map<String, Language> getLanguages(Object oreceiver) {
        PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;
        try {
            return receiver.getLanguages();
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(receiver, t);
        }
    }

    @Override
    public OptionDescriptors getOptions(Object oreceiver) {
        PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;
        try {
            return receiver.getOptions();
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(receiver, t);
        }
    }

    @Override
    public Context createContext(Object oreceiver, OutputStream out, OutputStream err, InputStream in, boolean allowHostLookup, HostAccess hostAccess, PolyglotAccess polyglotAccess, boolean allowNativeAccess, boolean allowCreateThread, boolean allowHostIO, boolean allowHostClassLoading, boolean allowInnerContextOptions, boolean allowExperimentalOptions, Predicate<String> classFilter, Map<String, String> options, Map<String, String[]> arguments, String[] onlyLanguages, FileSystem fileSystem, Object logHandlerOrStream, boolean allowCreateProcess, ProcessHandler processHandler, EnvironmentAccess environmentAccess, Map<String, String> environment, ZoneId zone, Object limitsImpl, String currentWorkingDirectory, ClassLoader hostClassLoader, boolean allowValueSharing, boolean useSystemExit) {
        PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;
        PolyglotContextImpl context = receiver.createContext(out, err, in, allowHostLookup, hostAccess, polyglotAccess, allowNativeAccess, allowCreateThread, allowHostIO, allowHostClassLoading, allowInnerContextOptions, allowExperimentalOptions, classFilter, options, arguments, onlyLanguages, fileSystem, logHandlerOrStream, allowCreateProcess, processHandler, environmentAccess, environment, zone, limitsImpl, currentWorkingDirectory, hostClassLoader, allowValueSharing, useSystemExit);
        return this.polyglot.getAPIAccess().newContext(this.polyglot.contextDispatch, context, context.engine.api);
    }

    @Override
    public String getImplementationName(Object oreceiver) {
        PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;
        try {
            return Truffle.getRuntime().getName();
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(receiver, t);
        }
    }

    @Override
    public Set<org.graalvm.polyglot.Source> getCachedSources(Object oreceiver) {
        PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;
        try {
            return receiver.getCachedSources();
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(receiver, t);
        }
    }

    @Override
    public String getVersion(Object oreceiver) {
        PolyglotEngineImpl receiver = (PolyglotEngineImpl)oreceiver;
        try {
            return receiver.getVersion();
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(receiver, t);
        }
    }

    @Override
    public ExecutionListener attachExecutionListener(Object engineReceiver, Consumer<ExecutionEvent> onEnter, Consumer<ExecutionEvent> onReturn, boolean expressions, boolean statements, boolean roots, final Predicate<org.graalvm.polyglot.Source> sourceFilter, final Predicate<String> rootFilter, boolean collectInputValues, boolean collectReturnValues, boolean collectExceptions) {
        EventBinding<ExecutionEventNodeFactory> binding;
        final PolyglotEngineImpl engine = (PolyglotEngineImpl)engineReceiver;
        Instrumenter instrumenter = (Instrumenter)EngineAccessor.INSTRUMENT.getEngineInstrumenter(engine.instrumentationHandler);
        ArrayList<Class<StandardTags.RootTag>> tags = new ArrayList<Class<StandardTags.RootTag>>();
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
            throw new IllegalArgumentException("No elements specified to listen to for execution listener. Need to specify at least one element kind: expressions, statements or roots.");
        }
        if (onReturn == null && onEnter == null) {
            throw new IllegalArgumentException("At least one event consumer must be provided for onEnter or onReturn.");
        }
        SourceSectionFilter.Builder filterBuilder = SourceSectionFilter.newBuilder().tagIs(tags.toArray(new Class[0]));
        filterBuilder.includeInternal(false);
        final PolyglotExecutionListenerDispatch.ListenerImpl config = new PolyglotExecutionListenerDispatch.ListenerImpl(this.polyglot.getExecutionEventDispatch(), engine, onEnter, onReturn, collectInputValues, collectReturnValues, collectExceptions);
        filterBuilder.sourceIs(new SourceSectionFilter.SourcePredicate(){

            @Override
            public boolean test(Source s) {
                String language = s.getLanguage();
                if (language == null) {
                    return false;
                }
                if (!engine.idToLanguage.containsKey(language)) {
                    return false;
                }
                if (sourceFilter != null) {
                    try {
                        return sourceFilter.test(PolyglotImpl.getOrCreatePolyglotSource(PolyglotEngineDispatch.this.polyglot, s));
                    }
                    catch (Throwable e) {
                        if (config.closing) {
                            return false;
                        }
                        throw engine.host.toHostException(null, e);
                    }
                }
                return true;
            }
        });
        if (rootFilter != null) {
            filterBuilder.rootNameIs(new Predicate<String>(){

                @Override
                public boolean test(String s) {
                    try {
                        return rootFilter.test(s);
                    }
                    catch (Throwable e) {
                        if (config.closing) {
                            return false;
                        }
                        throw engine.host.toHostException(null, e);
                    }
                }
            });
        }
        SourceSectionFilter filter = filterBuilder.build();
        try {
            boolean mayNeedInputValues = config.collectInputValues && config.onReturn != null;
            boolean mayNeedReturnValue = config.collectReturnValues && config.onReturn != null;
            boolean mayNeedExceptions = config.collectExceptions;
            binding = mayNeedInputValues || mayNeedReturnValue || mayNeedExceptions ? instrumenter.attachExecutionEventFactory(filter, mayNeedInputValues ? filter : null, new ExecutionEventNodeFactory(){

                @Override
                public ExecutionEventNode create(EventContext context) {
                    return new PolyglotExecutionListenerDispatch.ProfilingNode(config, context);
                }
            }) : instrumenter.attachExecutionEventFactory(filter, null, new ExecutionEventNodeFactory(){

                @Override
                public ExecutionEventNode create(EventContext context) {
                    return new PolyglotExecutionListenerDispatch.DefaultNode(config, context);
                }
            });
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(engine, t);
        }
        config.binding = binding;
        return this.polyglot.getManagement().newExecutionListener(this.polyglot.getExecutionListenerDispatch(), config);
    }

    @Override
    public void shutdown(Object engine) {
        ((PolyglotEngineImpl)engine).onVMShutdown();
    }
}

