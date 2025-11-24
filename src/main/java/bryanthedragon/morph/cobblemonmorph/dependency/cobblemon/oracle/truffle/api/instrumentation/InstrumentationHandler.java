
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.impl.DispatchOutputStream;
import com.oracle.truffle.api.instrumentation.AllocationEventFilter;
import com.oracle.truffle.api.instrumentation.AllocationListener;
import com.oracle.truffle.api.instrumentation.AllocationReporter;
import com.oracle.truffle.api.instrumentation.ContextsListener;
import com.oracle.truffle.api.instrumentation.EventBinding;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.ExecuteSourceEvent;
import com.oracle.truffle.api.instrumentation.ExecuteSourceListener;
import com.oracle.truffle.api.instrumentation.ExecutionEventListener;
import com.oracle.truffle.api.instrumentation.ExecutionEventNode;
import com.oracle.truffle.api.instrumentation.ExecutionEventNodeFactory;
import com.oracle.truffle.api.instrumentation.InstrumentAccessor;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Instrumenter;
import com.oracle.truffle.api.instrumentation.LoadSourceEvent;
import com.oracle.truffle.api.instrumentation.LoadSourceListener;
import com.oracle.truffle.api.instrumentation.LoadSourceSectionEvent;
import com.oracle.truffle.api.instrumentation.LoadSourceSectionListener;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.RootNodeBits;
import com.oracle.truffle.api.instrumentation.SourceFilter;
import com.oracle.truffle.api.instrumentation.SourceInstrumentationHandler;
import com.oracle.truffle.api.instrumentation.SourceSectionFilter;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.instrumentation.ThreadsActivationListener;
import com.oracle.truffle.api.instrumentation.ThreadsListener;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.nodes.NodeVisitor;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.ref.WeakReference;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.Lock;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import org.graalvm.options.OptionValues;
import org.graalvm.polyglot.io.MessageTransport;

final class InstrumentationHandler {
    static final boolean TRACE = Boolean.getBoolean("truffle.instrumentation.trace");
    private final Object polyglotEngine;
    private final ThreadLocal<Map<Source, Void>> threadLocalNewSourcesLoaded = new ThreadLocal();
    private final ThreadLocal<Map<Source, Void>> threadLocalNewSourcesExecuted = new ThreadLocal();
    private final ThreadLocal<List<BindingLoadSourceSectionEvent>> threadLocalSourceSectionLoadedList = new ThreadLocal();
    final Collection<RootNode> loadedRoots = new WeakAsyncList<RootNode>(256);
    private final Collection<RootNode> executedRoots = new WeakAsyncList<RootNode>(64);
    private final Collection<AllocationReporter> allocationReporters = new WeakAsyncList<AllocationReporter>(16);
    private volatile boolean hasLoadOrExecutionBinding = false;
    private final CopyOnWriteList<EventBinding.Source<?>> executionBindings = new CopyOnWriteList<EventBinding.Source>(new EventBinding.Source[0]);
    private final CopyOnWriteList<EventBinding.Source<?>> sourceSectionBindings = new CopyOnWriteList<EventBinding.Source>(new EventBinding.Source[0]);
    private final SourceInstrumentationHandler sourcesLoaded = new SourceInstrumentationHandler(new BiConsumer<EventBinding.Source<?>[], Source>(){

        @Override
        public void accept(EventBinding.Source<?>[] bindings, Source source) {
            InstrumentationHandler.notifySourceLoadedBindings(bindings, source);
        }
    });
    private final SourceInstrumentationHandler sourcesExecuted = new SourceInstrumentationHandler(new BiConsumer<EventBinding.Source<?>[], Source>(){

        @Override
        public void accept(EventBinding.Source<?>[] bindings, Source source) {
            InstrumentationHandler.notifySourceExecutedBindings(bindings, source);
        }
    });
    private final Collection<EventBinding<? extends OutputStream>> outputStdBindings = new EventBindingList<EventBinding<? extends OutputStream>>(1);
    private final Collection<EventBinding<? extends OutputStream>> outputErrBindings = new EventBindingList<EventBinding<? extends OutputStream>>(1);
    private final Collection<EventBinding.Allocation<? extends AllocationListener>> allocationBindings = new EventBindingList<EventBinding.Allocation<? extends AllocationListener>>(2);
    private final Collection<EventBinding<? extends ContextsListener>> contextsBindings = new EventBindingList<EventBinding<? extends ContextsListener>>(8);
    private final Collection<EventBinding<? extends ThreadsListener>> threadsBindings = new EventBindingList<EventBinding<? extends ThreadsListener>>(8);
    private final Collection<EventBinding<? extends ThreadsActivationListener>> threadsActivationBindings = new EventBindingList<EventBinding<? extends ThreadsActivationListener>>(8);
    @CompilerDirectives.CompilationFinal
    private volatile StableThreadsActivationListeners stableActivationListeners;
    final ConcurrentHashMap<Object, AbstractInstrumenter> instrumenterMap = new ConcurrentHashMap();
    private DispatchOutputStream out;
    private DispatchOutputStream err;
    private InputStream in;
    private MessageTransport messageInterceptor;
    private final Map<Class<?>, Set<Class<?>>> cachedProvidedTags = new ConcurrentHashMap();
    final EngineInstrumenter engineInstrumenter;

    InstrumentationHandler(Object polyglotEngine, DispatchOutputStream out, DispatchOutputStream err, InputStream in, MessageTransport messageInterceptor) {
        this.polyglotEngine = polyglotEngine;
        this.out = out;
        this.err = err;
        this.in = in;
        this.messageInterceptor = messageInterceptor;
        this.engineInstrumenter = new EngineInstrumenter();
    }

    Object getSourceVM() {
        return this.polyglotEngine;
    }

    void onLoad(RootNode root) {
        if (TRACE) {
            String name = root.getName();
            if (name == null) {
                name = root.getClass().getName();
            }
            String lang = "None";
            LanguageInfo info = root.getLanguageInfo();
            if (info != null) {
                lang = info.getId();
            }
            InstrumentationHandler.trace("ON-LOAD: %-5s CallTarget: %s%n", lang, name);
        }
        if (InstrumentAccessor.nodesAccess().getSharingLayer(root) == null) {
            return;
        }
        this.loadedRoots.add(root);
        if (this.hasLoadOrExecutionBinding && (!this.sourceSectionBindings.isEmpty() || this.sourcesLoaded.hasBindings())) {
            VisitorBuilder visitorBuilder = new VisitorBuilder();
            visitorBuilder.addNotifyLoadedOperationForAllBindings(VisitOperation.Scope.ALL);
            visitorBuilder.addFindSourcesOperation(VisitOperation.Scope.ALL);
            InstrumentationHandler.visitRoot(root, root, visitorBuilder.buildVisitor(), false, true);
        }
    }

    void onFirstExecution(RootNode root) {
        if (!InstrumentAccessor.nodesAccess().isInstrumentable(root)) {
            return;
        }
        this.executedRoots.add(root);
        if (this.hasLoadOrExecutionBinding && (!this.executionBindings.isEmpty() || this.sourcesExecuted.hasBindings())) {
            VisitorBuilder visitorBuilder = new VisitorBuilder();
            visitorBuilder.addInsertWrapperOperationForAllBindings(VisitOperation.Scope.ALL);
            visitorBuilder.addNotifyLoadedOperationForAllBindings(VisitOperation.Scope.ONLY_MATERIALIZED);
            visitorBuilder.addFindSourcesOperation(VisitOperation.Scope.ONLY_MATERIALIZED);
            visitorBuilder.addFindSourcesExecutedOperation(VisitOperation.Scope.ALL);
            InstrumentationHandler.visitRoot(root, root, visitorBuilder.buildVisitor(), false, true, true);
        }
    }

    void initializeInstrument(Object polyglotInstrument, String instrumentClassName, Supplier<? extends Object> instrumentSupplier) {
        if (TRACE) {
            InstrumentationHandler.trace("Initialize instrument class %s %n", instrumentClassName);
        }
        TruffleInstrument.Env env = new TruffleInstrument.Env(polyglotInstrument, this.out, this.err, this.in, this.messageInterceptor);
        TruffleInstrument instrument = (TruffleInstrument)instrumentSupplier.get();
        instrument.contextLocals = instrument.contextLocals == null ? Collections.emptyList() : Collections.unmodifiableList(instrument.contextLocals);
        InstrumentAccessor.ENGINE.initializeInstrumentContextLocal(instrument.contextLocals, polyglotInstrument);
        instrument.contextThreadLocals = instrument.contextThreadLocals == null ? Collections.emptyList() : Collections.unmodifiableList(instrument.contextThreadLocals);
        InstrumentAccessor.ENGINE.initializeInstrumentContextThreadLocal(instrument.contextThreadLocals, polyglotInstrument);
        try {
            env.instrumenter = new InstrumentClientInstrumenter(env, instrumentClassName);
            env.instrumenter.instrument = instrument;
        }
        catch (Exception e) {
            InstrumentationHandler.failInstrumentInitialization(env, String.format("Failed to create new instrumenter class %s", instrumentClassName), e);
            return;
        }
        if (TRACE) {
            InstrumentationHandler.trace("Initialized instrument %s class %s %n", env.instrumenter.instrument, instrumentClassName);
        }
        this.addInstrumenter(polyglotInstrument, env.instrumenter);
    }

    void createInstrument(Object vmObject, String[] expectedServices, OptionValues optionValues) {
        InstrumentClientInstrumenter instrumenter = (InstrumentClientInstrumenter)this.instrumenterMap.get(vmObject);
        instrumenter.env.options = optionValues;
        instrumenter.create(expectedServices);
    }

    void finalizeInstrumenter(Object key) {
        AbstractInstrumenter finalisingInstrumenter = this.instrumenterMap.get(key);
        if (finalisingInstrumenter == null) {
            throw new AssertionError((Object)"Instrumenter already disposed.");
        }
        finalisingInstrumenter.doFinalize();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void disposeInstrumenter(Object key, boolean cleanupRequired) {
        AbstractInstrumenter disposedInstrumenter = this.instrumenterMap.remove(key);
        if (disposedInstrumenter == null) {
            throw new AssertionError((Object)"Instrumenter already disposed.");
        }
        if (TRACE) {
            InstrumentationHandler.trace("BEGIN: Dispose instrumenter %n", key);
        }
        disposedInstrumenter.dispose();
        if (cleanupRequired) {
            Collection<EventBinding<?>> disposedExecutionBindings = InstrumentationHandler.filterBindingsForInstrumenter(this.executionBindings, disposedInstrumenter);
            InstrumentationHandler.setDisposingBindingsBulk(disposedExecutionBindings);
            if (!disposedExecutionBindings.isEmpty()) {
                VisitorBuilder visitorBuilder = new VisitorBuilder();
                visitorBuilder.addDisposeWrapperOperationForBindings(new CopyOnWriteList(disposedExecutionBindings.toArray(new EventBinding.Source[0])));
                InstrumentationHandler.visitRoots(this.executedRoots, visitorBuilder.buildVisitor());
            }
            InstrumentationHandler.disposeBindingsBulk(disposedExecutionBindings);
            this.executionBindings.removeAll(disposedExecutionBindings);
            Collection<EventBinding<?>> disposedSourceSectionBindings = InstrumentationHandler.filterBindingsForInstrumenter(this.sourceSectionBindings, disposedInstrumenter);
            InstrumentationHandler.disposeBindingsBulk(disposedSourceSectionBindings);
            this.sourceSectionBindings.removeAll(disposedSourceSectionBindings);
            this.sourcesLoaded.clearForDisposedInstrumenter(disposedInstrumenter);
            this.sourcesExecuted.clearForDisposedInstrumenter(disposedInstrumenter);
            InstrumentationHandler.disposeOutputBindingsBulk(this.out, this.outputStdBindings);
            InstrumentationHandler.disposeOutputBindingsBulk(this.err, this.outputErrBindings);
            Collection<EventBinding<? extends ThreadsActivationListener>> collection = this.threadsActivationBindings;
            synchronized (collection) {
                Collection<EventBinding<?>> disposedThreadsActivationBindings = InstrumentationHandler.filterBindingsForInstrumenter(this.threadsActivationBindings, disposedInstrumenter);
                if (!disposedThreadsActivationBindings.isEmpty()) {
                    InstrumentationHandler.disposeBindingsBulk(disposedThreadsActivationBindings);
                    this.invalidateThreadsActivationListeners();
                }
            }
        }
        if (TRACE) {
            InstrumentationHandler.trace("END: Disposed instrumenter %n", key);
        }
    }

    private static void setDisposingBindingsBulk(Collection<EventBinding<?>> list) {
        for (EventBinding<?> binding : list) {
            binding.setDisposingBulk();
        }
    }

    static void disposeBindingsBulk(Collection<EventBinding<?>> list) {
        for (EventBinding<?> binding : list) {
            binding.disposeBulk();
        }
    }

    private static void disposeOutputBindingsBulk(DispatchOutputStream dos, Collection<EventBinding<? extends OutputStream>> list) {
        for (EventBinding<? extends OutputStream> binding : list) {
            InstrumentAccessor.engineAccess().detachOutputConsumer(dos, binding.getElement());
            binding.disposeBulk();
        }
    }

    Instrumenter forLanguage(TruffleLanguage<?> language) {
        return new LanguageClientInstrumenter(language);
    }

    <T> EventBinding<T> addExecutionBinding(EventBinding.Source<T> binding) {
        if (TRACE) {
            InstrumentationHandler.trace("BEGIN: Adding execution binding %s, %s%n", binding.getFilter(), binding.getElement());
        }
        this.hasLoadOrExecutionBinding = true;
        this.executionBindings.add(binding);
        if (!this.executedRoots.isEmpty()) {
            VisitorBuilder visitorBuilder = new VisitorBuilder();
            visitorBuilder.addInsertWrapperOperationForBinding(VisitOperation.Scope.ONLY_ORIGINAL, binding);
            visitorBuilder.addInsertWrapperOperationForAllBindings(VisitOperation.Scope.ONLY_MATERIALIZED);
            visitorBuilder.addNotifyLoadedOperationForAllBindings(VisitOperation.Scope.ONLY_MATERIALIZED);
            visitorBuilder.addFindSourcesOperation(VisitOperation.Scope.ONLY_MATERIALIZED);
            visitorBuilder.addFindSourcesExecutedOperation(VisitOperation.Scope.ONLY_MATERIALIZED);
            InstrumentationHandler.visitRoots(this.executedRoots, visitorBuilder.buildVisitor(), true);
        }
        if (TRACE) {
            InstrumentationHandler.trace("END: Added execution binding %s, %s%n", binding.getFilter(), binding.getElement());
        }
        return binding;
    }

    <T> EventBinding<T> addSourceSectionBinding(EventBinding.SourceSectionLoaded<T> binding) {
        if (TRACE) {
            InstrumentationHandler.trace("BEGIN: Adding binding %s, %s%n", binding.getFilter(), binding.getElement());
        }
        this.hasLoadOrExecutionBinding = true;
        this.sourceSectionBindings.add(binding);
        if (binding.isNotifyLoaded() && !this.loadedRoots.isEmpty()) {
            VisitorBuilder visitorBuilder = new VisitorBuilder();
            visitorBuilder.addNotifyLoadedOperationForBinding(VisitOperation.Scope.ONLY_ORIGINAL, binding);
            visitorBuilder.addNotifyLoadedOperationForAllBindings(VisitOperation.Scope.ONLY_MATERIALIZED);
            visitorBuilder.addInsertWrapperOperationForAllBindings(VisitOperation.Scope.ONLY_MATERIALIZED);
            visitorBuilder.addFindSourcesOperation(VisitOperation.Scope.ONLY_MATERIALIZED);
            visitorBuilder.addFindSourcesExecutedOperation(VisitOperation.Scope.ONLY_MATERIALIZED);
            InstrumentationHandler.visitRoots(this.loadedRoots, visitorBuilder.buildVisitor());
        }
        if (TRACE) {
            InstrumentationHandler.trace("END: Added binding %s, %s%n", binding.getFilter(), binding.getElement());
        }
        return binding;
    }

    private void visitLoadedSourceSections(EventBinding.Source<?> binding) {
        if (TRACE) {
            InstrumentationHandler.trace("BEGIN: Visiting loaded source sections %s, %s%n", binding.getFilter(), binding.getElement());
        }
        if (!this.loadedRoots.isEmpty()) {
            VisitorBuilder visitorBuilder = new VisitorBuilder();
            visitorBuilder.addNotifyLoadedOperationForBinding(VisitOperation.Scope.ALL, binding);
            visitorBuilder.addNotifyLoadedOperationForAllBindings(VisitOperation.Scope.ONLY_MATERIALIZED);
            visitorBuilder.addInsertWrapperOperationForAllBindings(VisitOperation.Scope.ONLY_MATERIALIZED);
            visitorBuilder.addFindSourcesOperation(VisitOperation.Scope.ONLY_MATERIALIZED);
            visitorBuilder.addFindSourcesExecutedOperation(VisitOperation.Scope.ONLY_MATERIALIZED);
            InstrumentationHandler.visitRoots(this.loadedRoots, visitorBuilder.buildVisitor());
        }
        if (TRACE) {
            InstrumentationHandler.trace("END: Visited loaded source sections %s, %s%n", binding.getFilter(), binding.getElement());
        }
    }

    <T> EventBinding<T> addSourceLoadedBinding(EventBinding.SourceLoaded<T> binding) {
        if (TRACE) {
            InstrumentationHandler.trace("BEGIN: Adding source binding %s, %s%n", binding.getFilter(), binding.getElement());
        }
        this.hasLoadOrExecutionBinding = true;
        SourceInstrumentationHandler.SourcesNotificationQueue notifications = this.sourcesLoaded.addBinding(binding, binding.isNotifyLoaded());
        if (notifications != null) {
            if (notifications.isSourcesInitializationRequired()) {
                this.lazyInitializeSourcesLoadedList();
            }
            notifications.process();
        }
        if (TRACE) {
            InstrumentationHandler.trace("END: Added source binding %s, %s%n", binding.getFilter(), binding.getElement());
        }
        return binding;
    }

    <T> EventBinding<T> addSourceExecutionBinding(EventBinding.SourceExecuted<T> binding) {
        if (TRACE) {
            InstrumentationHandler.trace("BEGIN: Adding source execution binding %s, %s%n", binding.getFilter(), binding.getElement());
        }
        this.hasLoadOrExecutionBinding = true;
        SourceInstrumentationHandler.SourcesNotificationQueue notifications = this.sourcesExecuted.addBinding(binding, binding.isNotifyLoaded());
        if (notifications != null) {
            if (notifications.isSourcesInitializationRequired()) {
                this.lazyInitializeSourcesExecutedList();
            }
            notifications.process();
        }
        if (TRACE) {
            InstrumentationHandler.trace("END: Added source execution binding %s, %s%n", binding.getFilter(), binding.getElement());
        }
        return binding;
    }

    <T extends OutputStream> EventBinding<T> addOutputBinding(EventBinding<T> binding, boolean errorOutput) {
        String kind;
        if (TRACE) {
            kind = errorOutput ? "error" : "standard";
            InstrumentationHandler.trace("BEGIN: Adding " + kind + " output binding %s%n", binding.getElement());
        }
        if (errorOutput) {
            this.outputErrBindings.add(binding);
            InstrumentAccessor.engineAccess().attachOutputConsumer(this.err, (OutputStream)binding.getElement());
        } else {
            this.outputStdBindings.add(binding);
            InstrumentAccessor.engineAccess().attachOutputConsumer(this.out, (OutputStream)binding.getElement());
        }
        if (TRACE) {
            kind = errorOutput ? "error" : "standard";
            InstrumentationHandler.trace("END: Added " + kind + " output binding %s%n", binding.getElement());
        }
        return binding;
    }

    private <T extends AllocationListener> EventBinding<T> addAllocationBinding(EventBinding.Allocation<T> binding) {
        if (TRACE) {
            InstrumentationHandler.trace("BEGIN: Adding allocation binding %s%n", binding.getElement());
        }
        this.allocationBindings.add(binding);
        for (AllocationReporter allocationReporter : this.allocationReporters) {
            if (!binding.getAllocationFilter().contains(allocationReporter.language)) continue;
            allocationReporter.addListener((AllocationListener)binding.getElement());
        }
        if (TRACE) {
            InstrumentationHandler.trace("END: Added allocation binding %s%n", binding.getElement());
        }
        return binding;
    }

    private <T extends ContextsListener> EventBinding<T> addContextsBinding(EventBinding<T> binding, boolean includeActiveContexts) {
        if (TRACE) {
            InstrumentationHandler.trace("BEGIN: Adding contexts binding %s%n", binding.getElement());
        }
        this.contextsBindings.add(binding);
        if (includeActiveContexts) {
            Accessor.EngineSupport engineAccess = InstrumentAccessor.engineAccess();
            engineAccess.reportAllLanguageContexts(this.polyglotEngine, binding.getElement());
        }
        if (TRACE) {
            InstrumentationHandler.trace("END: Added contexts binding %s%n", binding.getElement());
        }
        return binding;
    }

    private <T extends ThreadsListener> EventBinding<T> addThreadsBinding(EventBinding<T> binding, boolean includeStartedThreads) {
        if (TRACE) {
            InstrumentationHandler.trace("BEGIN: Adding threads binding %s%n", binding.getElement());
        }
        this.threadsBindings.add(binding);
        if (includeStartedThreads) {
            Accessor.EngineSupport engineAccess = InstrumentAccessor.engineAccess();
            engineAccess.reportAllContextThreads(this.polyglotEngine, binding.getElement());
        }
        if (TRACE) {
            InstrumentationHandler.trace("END: Added threads binding %s%n", binding.getElement());
        }
        return binding;
    }

    private void lazyInitializeSourcesLoadedList() {
        try {
            VisitorBuilder visitorBuilder = new VisitorBuilder();
            visitorBuilder.addFindSourcesOperation(VisitOperation.Scope.ALL, true);
            InstrumentationHandler.visitRoots(this.loadedRoots, visitorBuilder.buildVisitor(), false);
            this.sourcesLoaded.setInitialized();
        }
        catch (Throwable t) {
            this.sourcesLoaded.clearAll();
            throw t;
        }
    }

    private void lazyInitializeSourcesExecutedList() {
        try {
            VisitorBuilder visitorBuilder = new VisitorBuilder();
            visitorBuilder.addFindSourcesExecutedOperation(VisitOperation.Scope.ALL, true);
            InstrumentationHandler.visitRoots(this.executedRoots, visitorBuilder.buildVisitor(), true);
            this.sourcesExecuted.setInitialized();
        }
        catch (Throwable t) {
            this.sourcesExecuted.clearAll();
            throw t;
        }
    }

    private static void visitRoots(Collection<RootNode> roots, Visitor visitor) {
        for (RootNode root : roots) {
            InstrumentationHandler.visitRoot(root, root, visitor, false, false);
        }
    }

    private static void visitRoots(Collection<RootNode> roots, Visitor visitor, boolean setExecutedRootNodeBit) {
        for (RootNode root : roots) {
            InstrumentationHandler.visitRoot(root, root, visitor, false, false, setExecutedRootNodeBit);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void disposeBinding(EventBinding<?> binding) {
        if (TRACE) {
            InstrumentationHandler.trace("BEGIN: Dispose binding %s%n", binding.getElement());
        }
        if (binding instanceof EventBinding.Source) {
            EventBinding.Source sourceBinding = (EventBinding.Source)binding;
            if (sourceBinding.isExecutionEvent()) {
                VisitorBuilder visitorBuilder = new VisitorBuilder();
                visitorBuilder.addDisposeWrapperOperationForBinding(sourceBinding);
                InstrumentationHandler.visitRoots(this.executedRoots, visitorBuilder.buildVisitor());
                this.executionBindings.remove(sourceBinding);
            } else {
                Object listener = sourceBinding.getElement();
                if (listener instanceof LoadSourceSectionListener) {
                    this.sourceSectionBindings.remove(sourceBinding);
                } else if (listener instanceof LoadSourceListener) {
                    this.sourcesLoaded.clearForDisposedBinding(sourceBinding);
                } else if (listener instanceof ExecuteSourceEvent) {
                    this.sourcesExecuted.clearForDisposedBinding(sourceBinding);
                }
            }
        } else if (binding instanceof EventBinding.Allocation) {
            EventBinding.Allocation allocationBinding = (EventBinding.Allocation)binding;
            AllocationListener l = (AllocationListener)binding.getElement();
            for (AllocationReporter allocationReporter : this.allocationReporters) {
                if (!allocationBinding.getAllocationFilter().contains(allocationReporter.language)) continue;
                allocationReporter.removeListener(l);
            }
        } else {
            Object elm = binding.getElement();
            if (elm instanceof OutputStream) {
                if (this.outputErrBindings.contains(binding)) {
                    InstrumentAccessor.engineAccess().detachOutputConsumer(this.err, (OutputStream)elm);
                } else if (this.outputStdBindings.contains(binding)) {
                    InstrumentAccessor.engineAccess().detachOutputConsumer(this.out, (OutputStream)elm);
                }
            } else if (!(elm instanceof ContextsListener) && !(elm instanceof ThreadsListener)) {
                if (elm instanceof ThreadsActivationListener) {
                    Collection<EventBinding<? extends ThreadsActivationListener>> collection = this.threadsActivationBindings;
                    synchronized (collection) {
                        this.invalidateThreadsActivationListeners();
                    }
                } else assert (false) : "Unexpected binding " + binding + " with element " + elm;
            }
        }
        if (TRACE) {
            InstrumentationHandler.trace("END: Disposed binding %s%n", binding.getElement());
        }
    }

    EventBinding.Source<?>[] getExecutionBindingsSnapshot() {
        return this.executionBindings.getArray();
    }

    ProbeNode.EventChainNode createBindings(VirtualFrame frame, ProbeNode probeNodeImpl, EventBinding.Source<?>[] executionBindingsSnapshot) {
        Node parentNode;
        EventContext context = probeNodeImpl.getContext();
        SourceSection sourceSection = context.getInstrumentedSourceSection();
        if (TRACE) {
            InstrumentationHandler.trace("BEGIN: Lazy update for %s%n", sourceSection);
        }
        Node parentInstrumentable = null;
        SourceSection parentInstrumentableSourceSection = null;
        for (parentNode = probeNodeImpl.getParent(); parentNode != null && parentNode.getParent() != null; parentNode = parentNode.getParent()) {
            if (parentInstrumentable != null) continue;
            SourceSection parentSourceSection = parentNode.getSourceSection();
            if (!InstrumentationHandler.isInstrumentableNode(parentNode)) continue;
            parentInstrumentable = parentNode;
            parentInstrumentableSourceSection = parentSourceSection;
        }
        if (!(parentNode instanceof RootNode)) {
            throw new AssertionError();
        }
        RootNode rootNode = (RootNode)parentNode;
        Node instrumentedNode = probeNodeImpl.getContext().getInstrumentedNode();
        Set<Class<?>> providedTags = this.getProvidedTags(rootNode);
        ProbeNode.EventChainNode root = null;
        ProbeNode.EventChainNode parent = null;
        for (EventBinding.Source<?> binding : executionBindingsSnapshot) {
            ProbeNode.EventChainNode next;
            if (binding.disposing) continue;
            if (binding.isChildInstrumentedFull(providedTags, rootNode, parentInstrumentable, parentInstrumentableSourceSection, instrumentedNode, sourceSection)) {
                if (TRACE) {
                    InstrumentationHandler.trace("  Found input value binding %s, %s%n", binding.getInputFilter(), System.identityHashCode(binding));
                }
                if ((next = probeNodeImpl.createParentEventChainCallback(frame, binding, rootNode, providedTags)) == null) continue;
                if (root == null) {
                    root = next;
                } else {
                    assert (parent != null);
                    parent.setNext(next);
                }
                parent = next;
            }
            if (!binding.isInstrumentedFull(providedTags, rootNode, instrumentedNode, sourceSection)) continue;
            if (TRACE) {
                InstrumentationHandler.trace("  Found binding %s, %s%n", binding.getFilter(), binding.getElement());
            }
            if ((next = probeNodeImpl.createEventChainCallback(frame, binding, rootNode, providedTags, instrumentedNode, sourceSection)) == null) continue;
            if (root == null) {
                root = next;
            } else {
                assert (parent != null);
                parent.setNext(next);
            }
            parent = next;
        }
        if (TRACE) {
            InstrumentationHandler.trace("END: Lazy updated for %s%n", sourceSection);
        }
        return root;
    }

    public void onNodeInserted(RootNode rootNode, Node tree) {
        Node parentInstrumentable = tree;
        while (parentInstrumentable != null && parentInstrumentable.getParent() != null && !InstrumentationHandler.isInstrumentableNode(parentInstrumentable = parentInstrumentable.getParent())) {
        }
        assert (parentInstrumentable != null);
        if (this.hasLoadOrExecutionBinding && (!this.sourceSectionBindings.isEmpty() || !this.executionBindings.isEmpty() || this.sourcesLoaded.hasBindings() || this.sourcesExecuted.hasBindings())) {
            VisitorBuilder visitorBuilder = new VisitorBuilder();
            visitorBuilder.addNotifyLoadedOperationForAllBindings(VisitOperation.Scope.ALL);
            visitorBuilder.addInsertWrapperOperationForAllBindings(VisitOperation.Scope.ALL);
            visitorBuilder.addFindSourcesOperation(VisitOperation.Scope.ALL);
            visitorBuilder.addFindSourcesExecutedOperation(VisitOperation.Scope.ALL);
            InstrumentationHandler.visitRoot(rootNode, parentInstrumentable, visitorBuilder.buildVisitor(), true, false);
        }
    }

    private static void notifySourceLoadedBindings(EventBinding.Source<?>[] bindings, Source source) {
        for (EventBinding.Source<?> binding : bindings) {
            InstrumentationHandler.notifySourceLoadedBinding(binding, source);
        }
    }

    private static void notifySourceLoadedBinding(EventBinding.Source<?> binding, Source source) {
        if (!binding.isDisposed() && binding.isInstrumentedSource(source)) {
            try {
                ((LoadSourceListener)binding.getElement()).onLoad(new LoadSourceEvent(source));
            }
            catch (Throwable t) {
                if (binding.isLanguageBinding()) {
                    throw t;
                }
                ProbeNode.exceptionEventForClientInstrument(binding, "onLoad", t);
            }
        }
    }

    private static void notifySourceExecutedBindings(EventBinding.Source<?>[] bindings, Source source) {
        for (EventBinding.Source<?> binding : bindings) {
            InstrumentationHandler.notifySourceExecutedBinding(binding, source);
        }
    }

    private static void notifySourceExecutedBinding(EventBinding.Source<?> binding, Source source) {
        if (!binding.isDisposed() && binding.isInstrumentedSource(source)) {
            try {
                ((ExecuteSourceListener)binding.getElement()).onExecute(new ExecuteSourceEvent(source));
            }
            catch (Throwable t) {
                if (binding.isLanguageBinding()) {
                    throw t;
                }
                ProbeNode.exceptionEventForClientInstrument(binding, "onExecute", t);
            }
        }
    }

    static void notifySourceSectionLoaded(EventBinding.Source<?> binding, Node node, SourceSection section) {
        if (section == null || binding.isDisposed()) {
            return;
        }
        LoadSourceSectionListener listener = (LoadSourceSectionListener)binding.getElement();
        try {
            listener.onLoad(new LoadSourceSectionEvent(section, node));
        }
        catch (Throwable t) {
            if (binding.isLanguageBinding()) {
                throw t;
            }
            ProbeNode.exceptionEventForClientInstrument(binding, "onLoad", t);
        }
    }

    private void addInstrumenter(Object key, AbstractInstrumenter instrumenter) throws AssertionError {
        AbstractInstrumenter previousKey = this.instrumenterMap.putIfAbsent(key, instrumenter);
        if (previousKey != null) {
            throw new AssertionError((Object)"Instrumenter already present.");
        }
    }

    static Collection<EventBinding<?>> filterBindingsForInstrumenter(Collection<? extends EventBinding<?>> bindings, AbstractInstrumenter instrumenter) {
        if (bindings.isEmpty()) {
            return Collections.emptyList();
        }
        ArrayList newBindings = new ArrayList();
        for (EventBinding<?> binding : bindings) {
            if (binding.getInstrumenter() != instrumenter) continue;
            newBindings.add(binding);
        }
        return newBindings;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void insertWrapper(Node instrumentableNode, SourceSection sourceSection) {
        Lock lock = InstrumentAccessor.nodesAccess().getLock(instrumentableNode);
        try {
            lock.lock();
            this.insertWrapperImpl(instrumentableNode, sourceSection);
        }
        finally {
            lock.unlock();
        }
    }

    private void insertWrapperImpl(Node node, SourceSection sourceSection) {
        InstrumentableNode.WrapperNode wrapper;
        Node parent = node.getParent();
        if (parent instanceof InstrumentableNode.WrapperNode) {
            InstrumentationHandler.invalidateWrapperImpl((InstrumentableNode.WrapperNode)((Object)parent), node);
            return;
        }
        ProbeNode probe = new ProbeNode(this, sourceSection);
        if (node instanceof InstrumentableNode) {
            try {
                wrapper = ((InstrumentableNode)((Object)node)).createWrapper(probe);
            }
            catch (Exception e) {
                throw new IllegalStateException("Failed to create wrapper of " + node, e);
            }
        } else {
            throw new AssertionError();
        }
        Node wrapperNode = InstrumentationHandler.getWrapperNodeChecked(wrapper, node, parent);
        node.replace(wrapperNode, "Insert instrumentation wrapper node.");
        assert (probe.getContext().validEventContextOnWrapperInsert());
    }

    private static Node getWrapperNodeChecked(Object wrapper, Node node, Node parent) {
        if (wrapper == null) {
            throw new IllegalStateException("No wrapper returned for " + node + " of class " + node.getClass().getName());
        }
        if (!(wrapper instanceof Node)) {
            throw new IllegalStateException(String.format("Implementation of %s must be a subclass of %s.", wrapper.getClass().getName(), Node.class.getSimpleName()));
        }
        Node wrapperNode = (Node)wrapper;
        if (wrapperNode.getParent() != null) {
            throw new IllegalStateException(String.format("Instance of provided wrapper %s is already adopted by another parent: %s", wrapper.getClass().getName(), wrapperNode.getParent().getClass().getName()));
        }
        if (parent == null) {
            throw new IllegalStateException(String.format("Instance of instrumentable node %s is not adopted by a parent.", node.getClass().getName()));
        }
        if (!NodeUtil.isReplacementSafe(parent, node, wrapperNode)) {
            throw new IllegalStateException(String.format("WrapperNode implementation %s cannot be safely replaced in parent node class %s.", wrapperNode.getClass().getName(), parent.getClass().getName()));
        }
        return wrapperNode;
    }

    private <T extends ExecutionEventNodeFactory> EventBinding<T> attachFactory(AbstractInstrumenter instrumenter, SourceSectionFilter filter, SourceSectionFilter inputFilter, T factory) {
        return this.addExecutionBinding(new EventBinding.Execution<T>(instrumenter, filter, inputFilter, factory));
    }

    private <T extends ExecutionEventListener> EventBinding<T> attachListener(AbstractInstrumenter instrumenter, SourceSectionFilter filter, SourceSectionFilter inputFilter, T listener) {
        return this.addExecutionBinding(new EventBinding.Execution<T>(instrumenter, filter, inputFilter, listener));
    }

    private <T extends LoadSourceListener> EventBinding<T> attachSourceListener(AbstractInstrumenter abstractInstrumenter, SourceSectionFilter filter, T listener, boolean notifyLoaded) {
        return this.addSourceLoadedBinding(new EventBinding.SourceLoaded<T>(abstractInstrumenter, filter, null, listener, true, notifyLoaded));
    }

    private <T> EventBinding<T> attachSourceSectionListener(AbstractInstrumenter abstractInstrumenter, SourceSectionFilter filter, T listener, boolean notifyLoaded) {
        return this.addSourceSectionBinding(new EventBinding.SourceSectionLoaded<T>(abstractInstrumenter, filter, null, listener, true, notifyLoaded));
    }

    private void visitLoadedSourceSections(AbstractInstrumenter abstractInstrumenter, SourceSectionFilter filter, LoadSourceSectionListener listener) {
        this.visitLoadedSourceSections(new EventBinding.SourceSectionLoaded<LoadSourceSectionListener>(abstractInstrumenter, filter, null, listener, true, true));
    }

    private <T> EventBinding<T> attachExecuteSourceListener(AbstractInstrumenter abstractInstrumenter, SourceSectionFilter filter, T listener, boolean notifyLoaded) {
        return this.addSourceExecutionBinding(new EventBinding.SourceExecuted<T>(abstractInstrumenter, filter, null, listener, true, notifyLoaded));
    }

    private <T extends OutputStream> EventBinding<T> attachOutputConsumer(AbstractInstrumenter instrumenter, T stream, boolean errorOutput) {
        return this.addOutputBinding(new EventBinding<T>(instrumenter, stream), errorOutput);
    }

    private <T extends AllocationListener> EventBinding<T> attachAllocationListener(AbstractInstrumenter instrumenter, AllocationEventFilter filter, T listener) {
        return this.addAllocationBinding(new EventBinding.Allocation<T>(instrumenter, filter, listener));
    }

    private <T extends ContextsListener> EventBinding<T> attachContextsListener(AbstractInstrumenter instrumenter, T listener, boolean includeActiveContexts) {
        assert (listener != null);
        return this.addContextsBinding(new EventBinding<T>(instrumenter, listener), includeActiveContexts);
    }

    private <T extends ThreadsListener> EventBinding<T> attachThreadsListener(AbstractInstrumenter instrumenter, T listener, boolean includeStartedThreads) {
        assert (listener != null);
        return this.addThreadsBinding(new EventBinding<T>(instrumenter, listener), includeStartedThreads);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private <T extends ThreadsActivationListener> EventBinding<T> attachThreadsActivationListener(AbstractInstrumenter instrumenter, T listener) {
        assert (listener != null);
        EventBinding<T> binding = new EventBinding<T>(instrumenter, listener);
        if (TRACE) {
            InstrumentationHandler.trace("BEGIN: Adding threads activaiton binding %s%n", binding.getElement());
        }
        Collection<EventBinding<? extends ThreadsActivationListener>> collection = this.threadsActivationBindings;
        synchronized (collection) {
            this.threadsActivationBindings.add(binding);
            this.invalidateThreadsActivationListeners();
        }
        if (TRACE) {
            InstrumentationHandler.trace("END: Added threads activation binding %s%n", binding.getElement());
        }
        return binding;
    }

    private void invalidateThreadsActivationListeners() {
        assert (Thread.holdsLock(this.threadsActivationBindings));
        StableThreadsActivationListeners stableListeners = this.stableActivationListeners;
        if (stableListeners != null) {
            stableListeners.assumption.invalidate();
            this.stableActivationListeners = null;
        }
    }

    ThreadsActivationListener[] getThreadsActivationListeners() {
        StableThreadsActivationListeners stableListeners = this.stableActivationListeners;
        if (stableListeners == null || !stableListeners.assumption.isValid()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            stableListeners = this.updateStableActivationListeners();
        }
        return stableListeners.listeners;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private StableThreadsActivationListeners updateStableActivationListeners() {
        StableThreadsActivationListeners stableListeners;
        Collection<EventBinding<? extends ThreadsActivationListener>> collection = this.threadsActivationBindings;
        synchronized (collection) {
            stableListeners = this.stableActivationListeners;
            if (stableListeners == null || !stableListeners.assumption.isValid()) {
                ArrayList<ThreadsActivationListener> listeners = new ArrayList<ThreadsActivationListener>();
                for (EventBinding<? extends ThreadsActivationListener> binding : this.threadsActivationBindings) {
                    listeners.add(binding.getElement());
                }
                StableThreadsActivationListeners oldListeners = stableListeners;
                this.stableActivationListeners = stableListeners = new StableThreadsActivationListeners(listeners.toArray(new ThreadsActivationListener[listeners.size()]));
                if (oldListeners != null) {
                    oldListeners.assumption.invalidate();
                }
            }
        }
        return stableListeners;
    }

    boolean hasContextBindings() {
        return !this.contextsBindings.isEmpty();
    }

    boolean hasThreadBindings() {
        return !this.threadsBindings.isEmpty();
    }

    void notifyContextCreated(TruffleContext context) {
        for (EventBinding<? extends ContextsListener> binding : this.contextsBindings) {
            binding.getElement().onContextCreated(context);
        }
    }

    void notifyContextClosed(TruffleContext context) {
        for (EventBinding<? extends ContextsListener> binding : this.contextsBindings) {
            binding.getElement().onContextClosed(context);
        }
    }

    void notifyContextResetLimit(TruffleContext context) {
        for (EventBinding<? extends ContextsListener> binding : this.contextsBindings) {
            binding.getElement().onContextResetLimits(context);
        }
    }

    void notifyLanguageContextCreate(TruffleContext context, LanguageInfo language) {
        for (EventBinding<? extends ContextsListener> binding : this.contextsBindings) {
            binding.getElement().onLanguageContextCreate(context, language);
        }
    }

    void notifyLanguageContextCreated(TruffleContext context, LanguageInfo language) {
        for (EventBinding<? extends ContextsListener> binding : this.contextsBindings) {
            binding.getElement().onLanguageContextCreated(context, language);
        }
    }

    void notifyLanguageContextCreateFailed(TruffleContext context, LanguageInfo language) {
        for (EventBinding<? extends ContextsListener> binding : this.contextsBindings) {
            binding.getElement().onLanguageContextCreateFailed(context, language);
        }
    }

    void notifyLanguageContextInitialize(TruffleContext context, LanguageInfo language) {
        for (EventBinding<? extends ContextsListener> binding : this.contextsBindings) {
            binding.getElement().onLanguageContextInitialize(context, language);
        }
    }

    void notifyLanguageContextInitialized(TruffleContext context, LanguageInfo language) {
        for (EventBinding<? extends ContextsListener> binding : this.contextsBindings) {
            binding.getElement().onLanguageContextInitialized(context, language);
        }
    }

    void notifyLanguageContextInitializeFailed(TruffleContext context, LanguageInfo language) {
        for (EventBinding<? extends ContextsListener> binding : this.contextsBindings) {
            binding.getElement().onLanguageContextInitializeFailed(context, language);
        }
    }

    void notifyLanguageContextFinalized(TruffleContext context, LanguageInfo language) {
        for (EventBinding<? extends ContextsListener> binding : this.contextsBindings) {
            binding.getElement().onLanguageContextFinalized(context, language);
        }
    }

    void notifyLanguageContextDisposed(TruffleContext context, LanguageInfo language) {
        for (EventBinding<? extends ContextsListener> binding : this.contextsBindings) {
            binding.getElement().onLanguageContextDisposed(context, language);
        }
    }

    void notifyThreadStarted(TruffleContext context, Thread thread) {
        for (EventBinding<? extends ThreadsListener> binding : this.threadsBindings) {
            binding.getElement().onThreadInitialized(context, thread);
        }
    }

    void notifyThreadFinished(TruffleContext context, Thread thread) {
        for (EventBinding<? extends ThreadsListener> binding : this.threadsBindings) {
            binding.getElement().onThreadDisposed(context, thread);
        }
    }

    Set<Class<?>> getProvidedTags(TruffleLanguage<?> lang) {
        if (lang == null) {
            return Collections.emptySet();
        }
        Class<?> languageClass = lang.getClass();
        Set<Class<Object>> tags = this.cachedProvidedTags.get(languageClass);
        if (tags == null) {
            ProvidedTags languageTags = languageClass.getAnnotation(ProvidedTags.class);
            List languageTagsList = languageTags != null ? Arrays.asList(languageTags.value()) : Collections.emptyList();
            tags = Collections.unmodifiableSet(new HashSet(languageTagsList));
            this.cachedProvidedTags.put(languageClass, tags);
        }
        return tags;
    }

    Set<Class<?>> getProvidedTags(Node root) {
        return this.getProvidedTags(InstrumentAccessor.nodesAccess().getLanguage(root.getRootNode()));
    }

    static boolean isInstrumentableNode(Node node) {
        if (node instanceof InstrumentableNode.WrapperNode) {
            return false;
        }
        if (node instanceof InstrumentableNode) {
            return ((InstrumentableNode)((Object)node)).isInstrumentable();
        }
        return false;
    }

    static void trace(String message, Object ... args) {
        PrintStream out = System.out;
        out.printf(message, args);
    }

    private static void visitRoot(RootNode root, Node node, Visitor visitor, boolean forceRootBitComputation, boolean firstExecution) {
        InstrumentationHandler.visitRoot(root, node, visitor, forceRootBitComputation, firstExecution, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void visitRoot(RootNode root, Node node, Visitor visitor, boolean forceRootBitComputation, boolean firstExecution, boolean setExecutedRootNodeBit) {
        if (TRACE) {
            InstrumentationHandler.trace("BEGIN: Visit root %s for %s%n", root.toString(), visitor);
        }
        if (InstrumentAccessor.runtimeAccess().isOSRRootNode(root)) {
            return;
        }
        visitor.rootBits = RootNodeBits.get(root);
        visitor.setExecutedRootNodeBit = setExecutedRootNodeBit;
        visitor.preVisit(root, node, firstExecution);
        try {
            Lock lock = InstrumentAccessor.nodesAccess().getLock(node);
            lock.lock();
            try {
                visitor.rootBits = RootNodeBits.get(root);
                if (visitor.shouldVisit() || forceRootBitComputation) {
                    if (TRACE) {
                        InstrumentationHandler.trace("BEGIN: Traverse root %s for %s%n", root.toString(), visitor);
                    }
                    if (forceRootBitComputation) {
                        visitor.computingRootNodeBits = RootNodeBits.isUninitialized(visitor.rootBits) ? RootNodeBits.getAll() : visitor.rootBits;
                    } else if (RootNodeBits.isUninitialized(visitor.rootBits)) {
                        visitor.computingRootNodeBits = RootNodeBits.getAll();
                    }
                    visitor.visit(node);
                    if (!RootNodeBits.isUninitialized(visitor.computingRootNodeBits)) {
                        RootNodeBits.set(visitor.root, visitor.computingRootNodeBits);
                        visitor.rootBits = visitor.computingRootNodeBits;
                    }
                    if (TRACE) {
                        InstrumentationHandler.trace("END: Traverse root %s for %s%n", root.toString(), visitor);
                    }
                }
                if (setExecutedRootNodeBit && RootNodeBits.wasNotExecuted(visitor.rootBits)) {
                    visitor.rootBits = RootNodeBits.setExecuted(visitor.rootBits);
                    RootNodeBits.set(root, visitor.rootBits);
                }
            }
            finally {
                lock.unlock();
            }
        }
        finally {
            visitor.postVisit();
        }
        if (TRACE) {
            InstrumentationHandler.trace("END: Visited root %s for %s%n", root.toString(), visitor);
        }
    }

    static void removeWrapper(ProbeNode node) {
        if (TRACE) {
            InstrumentationHandler.trace("Remove wrapper for %s%n", node.getContext().getInstrumentedSourceSection());
        }
        InstrumentableNode.WrapperNode wrapperNode = node.findWrapper();
        ((Node)((Object)wrapperNode)).replace(wrapperNode.getDelegateNode());
    }

    private static void invalidateWrapper(Node node) {
        Node parent = node.getParent();
        if (!(parent instanceof InstrumentableNode.WrapperNode)) {
            return;
        }
        InstrumentationHandler.invalidateWrapperImpl((InstrumentableNode.WrapperNode)((Object)parent), node);
    }

    private static void invalidateWrapperImpl(InstrumentableNode.WrapperNode parent, Node node) {
        ProbeNode probeNode = parent.getProbeNode();
        if (TRACE) {
            SourceSection section = probeNode.getContext().getInstrumentedSourceSection();
            InstrumentationHandler.trace("Invalidate wrapper for %s, section %s %n", node, section);
        }
        if (probeNode != null) {
            probeNode.invalidate();
        }
    }

    static boolean hasTagImpl(Set<Class<?>> providedTags, Node node, Class<?> tag) {
        if (providedTags.contains(tag)) {
            if (node instanceof InstrumentableNode) {
                return ((InstrumentableNode)((Object)node)).hasTag(tag);
            }
            return false;
        }
        return false;
    }

    <T> T lookup(Object key, Class<T> type) {
        AbstractInstrumenter value2 = this.instrumenterMap.get(key);
        return value2 == null ? null : (T)value2.lookup(this, type);
    }

    AllocationReporter getAllocationReporter(LanguageInfo info) {
        AllocationReporter allocationReporter = new AllocationReporter(info);
        this.allocationReporters.add(allocationReporter);
        for (EventBinding.Allocation<? extends AllocationListener> binding : this.allocationBindings) {
            if (!binding.getAllocationFilter().contains(info)) continue;
            allocationReporter.addListener((AllocationListener)binding.getElement());
        }
        return allocationReporter;
    }

    void finalizeStore() {
        this.out = null;
        this.err = null;
        this.in = null;
    }

    void patch(DispatchOutputStream newOut, DispatchOutputStream newErr, InputStream newIn) {
        this.out = newOut;
        this.err = newErr;
        this.in = newIn;
    }

    static void failInstrumentInitialization(TruffleInstrument.Env env, String message, Throwable t) {
        Exception exception = new Exception(message, t);
        PrintStream stream = new PrintStream(env.err());
        exception.printStackTrace(stream);
    }

    private static InstrumentableNode.WrapperNode getWrapperNode(Node node) {
        Node parent = node.getParent();
        return parent instanceof InstrumentableNode.WrapperNode ? (InstrumentableNode.WrapperNode)((Object)parent) : null;
    }

    private static void clearRetiredNodeReference(Node node) {
        InstrumentableNode.WrapperNode wrapperNode = InstrumentationHandler.getWrapperNode(node);
        if (wrapperNode != null) {
            wrapperNode.getProbeNode().clearRetiredNodeReference();
            InstrumentationHandler.invalidateWrapperImpl(wrapperNode, node);
        }
    }

    private static void traceFilterCheck(String result, Node instrumentableNode, SourceSection sourceSection) {
        InstrumentationHandler.trace("  Filter %4s node:%s section:%s %n", result, instrumentableNode, sourceSection);
    }

    static final class StableThreadsActivationListeners {
        final Assumption assumption = Truffle.getRuntime().createAssumption("Activation listeners stable.");
        @CompilerDirectives.CompilationFinal(dimensions=1)
        final ThreadsActivationListener[] listeners;

        StableThreadsActivationListeners(ThreadsActivationListener[] listeners) {
            this.listeners = listeners;
        }
    }

    static final class WeakAsyncList<T>
    extends AbstractAsyncCollection<WeakReference<T>, T> {
        WeakAsyncList(int initialCapacity) {
            super(initialCapacity);
        }

        @Override
        protected WeakReference<T> wrap(T element) {
            return new WeakReference<T>(element);
        }

        @Override
        protected T unwrap(WeakReference<T> element) {
            return element.get();
        }
    }

    private static final class EventBindingList<EB extends EventBinding<?>>
    extends AbstractAsyncCollection<EB, EB> {
        EventBindingList(int initialCapacity) {
            super(initialCapacity);
        }

        @Override
        protected EB wrap(EB element) {
            return element;
        }

        @Override
        protected EB unwrap(EB element) {
            if (((EventBinding)element).isDisposed()) {
                return null;
            }
            return element;
        }
    }

    private static abstract class AbstractAsyncCollection<T, R>
    extends AbstractCollection<R> {
        private volatile AtomicReferenceArray<T> values;
        private int nextInsertionIndex;
        protected final int initialCapacity;

        AbstractAsyncCollection(int initialCapacity) {
            if (initialCapacity <= 0) {
                throw new IllegalArgumentException("Invalid initial capacity " + initialCapacity);
            }
            this.values = new AtomicReferenceArray(initialCapacity);
            this.initialCapacity = initialCapacity;
        }

        @Override
        public final synchronized void clear() {
            this.values = new AtomicReferenceArray(this.initialCapacity);
            this.nextInsertionIndex = 0;
        }

        @Override
        public final synchronized boolean add(R reference) {
            T wrappedElement = this.wrap(reference);
            if (wrappedElement == null) {
                throw new NullPointerException();
            }
            if (this.nextInsertionIndex >= this.values.length()) {
                this.compact();
            }
            this.values.set(this.nextInsertionIndex++, wrappedElement);
            return true;
        }

        @Override
        public int size() {
            throw new UnsupportedOperationException();
        }

        @Override
        public final boolean isEmpty() {
            return this.values.get(0) == null;
        }

        protected abstract T wrap(R var1);

        protected abstract R unwrap(T var1);

        private void compact() {
            T ref;
            T ref2;
            AtomicReferenceArray<T> localValues = this.values;
            int liveElements = 0;
            for (int i = 0; i < localValues.length() && (ref2 = localValues.get(i)) != null; ++i) {
                if (this.unwrap(ref2) == null) continue;
                ++liveElements;
            }
            AtomicReferenceArray<T> newValues = new AtomicReferenceArray<T>(Math.max(liveElements * 2, this.initialCapacity));
            int index = 0;
            for (int i = 0; i < localValues.length() && (ref = localValues.get(i)) != null; ++i) {
                if (this.unwrap(ref) == null) continue;
                newValues.set(index++, ref);
            }
            this.nextInsertionIndex = index;
            this.values = newValues;
        }

        @Override
        public Iterator<R> iterator() {
            return new Iterator<R>(){
                private final AtomicReferenceArray<T> values;
                private int index;
                private R queuedNext;
                {
                    this.values = values;
                }

                @Override
                public boolean hasNext() {
                    Object next = this.queuedNext;
                    if (next == null) {
                        this.queuedNext = next = this.queueNext();
                    }
                    return next != null;
                }

                private R queueNext() {
                    Object localValue;
                    Object alive;
                    int localIndex = this.index;
                    AtomicReferenceArray array = this.values;
                    do {
                        if (localIndex >= array.length()) {
                            return null;
                        }
                        localValue = array.get(localIndex);
                        if (localValue == null) {
                            return null;
                        }
                        ++localIndex;
                    } while ((alive = this.unwrap(localValue)) == null);
                    this.index = localIndex;
                    return alive;
                }

                @Override
                public R next() {
                    Object next = this.queuedNext;
                    if (next == null && (next = this.queueNext()) == null) {
                        throw new NoSuchElementException();
                    }
                    this.queuedNext = null;
                    return next;
                }

                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        int getNextInsertionIndex() {
            return this.nextInsertionIndex;
        }
    }

    static class CopyOnWriteList<E>
    extends AbstractCollection<E> {
        private volatile E[] array;

        CopyOnWriteList(E[] array) {
            this.array = array;
        }

        @Override
        public synchronized boolean add(E e) {
            if (e == null) {
                throw new NullPointerException();
            }
            E[] oldArray = this.getArray();
            int len = oldArray.length;
            E[] newArray = Arrays.copyOf(oldArray, len + 1);
            newArray[len] = e;
            this.array = newArray;
            return true;
        }

        @Override
        public synchronized void clear() {
            E[] oldArray = this.getArray();
            E[] newArray = Arrays.copyOf(oldArray, 0);
            this.array = newArray;
        }

        @Override
        public Iterator<E> iterator() {
            return new Iterator<E>(){
                private final E[] snapshot;
                private int cursor;
                {
                    this.snapshot = this.getArray();
                    this.cursor = 0;
                }

                @Override
                public boolean hasNext() {
                    return this.cursor < this.snapshot.length;
                }

                @Override
                public E next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return this.snapshot[this.cursor++];
                }
            };
        }

        @Override
        public int size() {
            return this.getArray().length;
        }

        @Override
        public boolean isEmpty() {
            return this.size() == 0;
        }

        public E[] getArray() {
            return this.array;
        }

        @Override
        public synchronized boolean remove(Object o) {
            E[] oldArray = this.getArray();
            int index = -1;
            int len = oldArray.length;
            for (int i = 0; i < len; ++i) {
                if (!oldArray[i].equals(o)) continue;
                index = i;
                break;
            }
            if (index >= 0) {
                E[] newArray = Arrays.copyOf(oldArray, len - 1);
                System.arraycopy(oldArray, index + 1, newArray, index, len - index - 1);
                this.array = newArray;
                return true;
            }
            return false;
        }

        @Override
        public synchronized boolean removeAll(Collection<?> c) {
            E[] oldArray = this.getArray();
            int len = oldArray.length;
            if (len != 0) {
                int newlen = 0;
                E[] temp = Arrays.copyOf(oldArray, len);
                for (int i = 0; i < len; ++i) {
                    E element = oldArray[i];
                    if (c.contains(element)) continue;
                    temp[newlen++] = element;
                }
                if (newlen != len) {
                    E[] newArray = Arrays.copyOf(temp, newlen);
                    this.array = newArray;
                    return true;
                }
            }
            return false;
        }
    }

    abstract class AbstractInstrumenter
    extends Instrumenter {
        AbstractInstrumenter() {
        }

        abstract void doFinalize();

        abstract void dispose();

        abstract <T> T lookup(InstrumentationHandler var1, Class<T> var2);

        void attachSourceLoadedBinding(EventBinding.SourceLoaded<?> binding) {
            InstrumentationHandler.this.addSourceLoadedBinding(binding);
        }

        void attachSourceExecutedBinding(EventBinding.SourceExecuted<?> binding) {
            InstrumentationHandler.this.addSourceExecutionBinding(binding);
        }

        void attachSourceSectionBinding(EventBinding.SourceSectionLoaded<?> binding) {
            InstrumentationHandler.this.addSourceSectionBinding(binding);
        }

        void disposeBinding(EventBinding<?> binding) {
            InstrumentationHandler.this.disposeBinding(binding);
        }

        abstract boolean isInstrumentableRoot(RootNode var1);

        abstract boolean isInstrumentableSource(Source var1);

        final Set<Class<?>> queryTagsImpl(Node node, LanguageInfo onlyLanguage) {
            Objects.requireNonNull(node);
            if (!InstrumentationHandler.isInstrumentableNode(node)) {
                return Collections.emptySet();
            }
            RootNode root = node.getRootNode();
            if (root == null) {
                return Collections.emptySet();
            }
            if (onlyLanguage != null && root.getLanguageInfo() != onlyLanguage) {
                throw new IllegalArgumentException("The language instrumenter cannot query tags of nodes of other languages.");
            }
            Set<Class<?>> providedTags = InstrumentationHandler.this.getProvidedTags(root);
            if (providedTags.isEmpty()) {
                return Collections.emptySet();
            }
            HashSet tags = new HashSet();
            for (Class<?> providedTag : providedTags) {
                if (!InstrumentationHandler.hasTagImpl(providedTags, node, providedTag)) continue;
                tags.add(providedTag);
            }
            return Collections.unmodifiableSet(tags);
        }

        @Override
        public final ExecutionEventNode lookupExecutionEventNode(Node node, EventBinding<?> binding) {
            if (!InstrumentationHandler.isInstrumentableNode(node)) {
                return null;
            }
            Node p = node.getParent();
            if (p instanceof InstrumentableNode.WrapperNode) {
                InstrumentableNode.WrapperNode w = (InstrumentableNode.WrapperNode)((Object)p);
                return w.getProbeNode().lookupExecutionEventNode(binding);
            }
            return null;
        }

        @Override
        public <T extends ExecutionEventNodeFactory> EventBinding<T> attachExecutionEventFactory(SourceSectionFilter filter, SourceSectionFilter inputFilter, T factory) {
            this.verifyFilter(filter);
            return InstrumentationHandler.this.attachFactory(this, filter, inputFilter, factory);
        }

        @Override
        public <T extends ExecutionEventListener> EventBinding<T> attachExecutionEventListener(SourceSectionFilter filter, SourceSectionFilter inputFilter, T listener) {
            this.verifyFilter(filter);
            return InstrumentationHandler.this.attachListener(this, filter, inputFilter, listener);
        }

        @Override
        public <T extends LoadSourceListener> EventBinding<T> attachLoadSourceListener(SourceSectionFilter filter, T listener, boolean includeExistingSources) {
            this.verifySourceOnly(filter);
            this.verifyFilter(filter);
            return InstrumentationHandler.this.attachSourceListener(this, filter, listener, includeExistingSources);
        }

        @Override
        public <T extends LoadSourceListener> EventBinding<T> attachLoadSourceListener(SourceFilter filter, T listener, boolean notifyLoaded) {
            SourceSectionFilter sectionsFilter = SourceSectionFilter.newBuilder().sourceFilter(filter).build();
            return this.attachLoadSourceListener(sectionsFilter, listener, notifyLoaded);
        }

        @Override
        public <T extends LoadSourceSectionListener> EventBinding<T> attachLoadSourceSectionListener(SourceSectionFilter filter, T listener, boolean notifyLoaded) {
            this.verifyFilter(filter);
            return InstrumentationHandler.this.attachSourceSectionListener(this, filter, listener, notifyLoaded);
        }

        @Override
        public void visitLoadedSourceSections(SourceSectionFilter filter, LoadSourceSectionListener listener) {
            this.verifyFilter(filter);
            InstrumentationHandler.this.visitLoadedSourceSections(this, filter, listener);
        }

        @Override
        public <T extends ExecuteSourceListener> EventBinding<T> attachExecuteSourceListener(SourceFilter filter, T listener, boolean notifyLoaded) {
            SourceSectionFilter sectionsFilter = SourceSectionFilter.newBuilder().sourceFilter(filter).build();
            return InstrumentationHandler.this.attachExecuteSourceListener(this, sectionsFilter, listener, notifyLoaded);
        }

        @Override
        public <T extends LoadSourceListener> EventBinding<T> createLoadSourceBinding(SourceFilter filter, T listener, boolean notifyLoaded) {
            SourceSectionFilter sectionsFilter = SourceSectionFilter.newBuilder().sourceFilter(filter).build();
            return new EventBinding.SourceLoaded<T>(this, sectionsFilter, null, listener, false, notifyLoaded);
        }

        @Override
        public <T extends ExecuteSourceListener> EventBinding<T> createExecuteSourceBinding(SourceFilter filter, T listener, boolean notifyLoaded) {
            SourceSectionFilter sectionsFilter = SourceSectionFilter.newBuilder().sourceFilter(filter).build();
            return new EventBinding.SourceExecuted<T>(this, sectionsFilter, null, listener, false, notifyLoaded);
        }

        @Override
        public <T extends LoadSourceSectionListener> EventBinding<T> createLoadSourceSectionBinding(SourceSectionFilter filter, T listener, boolean notifyLoaded) {
            this.verifyFilter(filter);
            return new EventBinding.SourceSectionLoaded<T>(this, filter, null, listener, false, notifyLoaded);
        }

        @Override
        public <T extends AllocationListener> EventBinding<T> attachAllocationListener(AllocationEventFilter filter, T listener) {
            return InstrumentationHandler.this.attachAllocationListener(this, filter, listener);
        }

        @Override
        public <T extends OutputStream> EventBinding<T> attachOutConsumer(T stream) {
            return InstrumentationHandler.this.attachOutputConsumer(this, stream, false);
        }

        @Override
        public <T extends OutputStream> EventBinding<T> attachErrConsumer(T stream) {
            return InstrumentationHandler.this.attachOutputConsumer(this, stream, true);
        }

        private void verifySourceOnly(SourceSectionFilter filter) {
            if (!filter.isSourceOnly()) {
                throw new IllegalArgumentException(String.format("The attached filter %s uses filters that require source sections to verifiy. Source listeners can only use filter critera based on Source objects like mimeTypeIs or sourceIs.", filter));
            }
        }

        abstract void verifyFilter(SourceSectionFilter var1);
    }

    final class LanguageClientInstrumenter<T>
    extends AbstractInstrumenter {
        private final LanguageInfo languageInfo;
        private final TruffleLanguage<?> language;

        LanguageClientInstrumenter(TruffleLanguage<?> language) {
            this.language = language;
            this.languageInfo = InstrumentAccessor.langAccess().getLanguageInfo(language);
        }

        @Override
        boolean isInstrumentableSource(Source source) {
            String mimeType = source.getMimeType();
            if (mimeType == null) {
                return false;
            }
            return this.languageInfo.getMimeTypes().contains(mimeType);
        }

        @Override
        boolean isInstrumentableRoot(RootNode node) {
            LanguageInfo langInfo = node.getLanguageInfo();
            if (langInfo == null) {
                return false;
            }
            return langInfo == this.languageInfo;
        }

        @Override
        public Set<Class<?>> queryTags(Node node) {
            return this.queryTagsImpl(node, this.languageInfo);
        }

        @Override
        void verifyFilter(SourceSectionFilter filter) {
            Set<Class<?>> referencedTags;
            Set<Class<Class<?>>> providedTags = InstrumentationHandler.this.getProvidedTags(this.language);
            if (!providedTags.containsAll(referencedTags = filter.getReferencedTags())) {
                HashSet missingTags = new HashSet(referencedTags);
                missingTags.removeAll(providedTags);
                LinkedHashSet allTags = new LinkedHashSet(providedTags);
                allTags.addAll(missingTags);
                StringBuilder builder = new StringBuilder("{");
                String sep = "";
                for (Class clazz : allTags) {
                    builder.append(sep);
                    builder.append(clazz.getSimpleName());
                    sep = ", ";
                }
                builder.append("}");
                throw new IllegalArgumentException(String.format("The attached filter %s references the following tags %s which are not declared as provided by the language. To fix this annotate the language class %s with @%s(%s).", filter, missingTags, this.language.getClass().getName(), ProvidedTags.class.getSimpleName(), builder));
            }
        }

        public <S extends ContextsListener> EventBinding<S> attachContextsListener(S listener, boolean includeActiveContexts) {
            throw new UnsupportedOperationException("Not supported in language instrumenter.");
        }

        public <S extends ThreadsListener> EventBinding<S> attachThreadsListener(S listener, boolean includeStartedThreads) {
            throw new UnsupportedOperationException("Not supported in language instrumenter.");
        }

        @Override
        public EventBinding<? extends ThreadsActivationListener> attachThreadsActivationListener(ThreadsActivationListener listener) {
            throw new UnsupportedOperationException("Not supported in language instrumenter.");
        }

        @Override
        void doFinalize() {
        }

        @Override
        void dispose() {
        }

        <S> S lookup(InstrumentationHandler handler, Class<S> type) {
            return null;
        }
    }

    final class EngineInstrumenter
    extends AbstractInstrumenter {
        EngineInstrumenter() {
        }

        @Override
        void doFinalize() {
        }

        @Override
        void dispose() {
        }

        @Override
        <T> T lookup(InstrumentationHandler handler, Class<T> type) {
            return null;
        }

        @Override
        boolean isInstrumentableRoot(RootNode rootNode) {
            return true;
        }

        @Override
        boolean isInstrumentableSource(Source source) {
            return true;
        }

        @Override
        void verifyFilter(SourceSectionFilter filter) {
        }

        @Override
        public Set<Class<?>> queryTags(Node node) {
            return this.queryTagsImpl(node, null);
        }

        @Override
        public <T extends ContextsListener> EventBinding<T> attachContextsListener(T listener, boolean includeActiveContexts) {
            return InstrumentationHandler.this.attachContextsListener(this, listener, includeActiveContexts);
        }

        @Override
        public <T extends ThreadsListener> EventBinding<T> attachThreadsListener(T listener, boolean includeStartedThreads) {
            return InstrumentationHandler.this.attachThreadsListener(this, listener, includeStartedThreads);
        }

        @Override
        public EventBinding<? extends ThreadsActivationListener> attachThreadsActivationListener(ThreadsActivationListener listener) {
            throw new UnsupportedOperationException("Not supported in engine instrumenter.");
        }
    }

    final class InstrumentClientInstrumenter
    extends AbstractInstrumenter {
        private final String instrumentClassName;
        private Object[] services;
        TruffleInstrument instrument;
        private final TruffleInstrument.Env env;

        InstrumentClientInstrumenter(TruffleInstrument.Env env, String instrumentClassName) {
            this.instrumentClassName = instrumentClassName;
            this.env = env;
        }

        @Override
        boolean isInstrumentableSource(Source source) {
            return true;
        }

        @Override
        boolean isInstrumentableRoot(RootNode rootNode) {
            return true;
        }

        @Override
        public Set<Class<?>> queryTags(Node node) {
            return this.queryTagsImpl(node, null);
        }

        @Override
        void verifyFilter(SourceSectionFilter filter) {
        }

        String getInstrumentClassName() {
            return this.instrumentClassName;
        }

        TruffleInstrument.Env getEnv() {
            return this.env;
        }

        void create(String[] expectedServices) {
            if (TRACE) {
                InstrumentationHandler.trace("Create instrument %s class %s %n", this.instrument, this.instrumentClassName);
            }
            this.services = this.env.onCreate(this.instrument);
            if (expectedServices != null && !TruffleOptions.AOT) {
                this.checkServices(expectedServices);
            }
            if (TRACE) {
                InstrumentationHandler.trace("Created instrument %s class %s %n", this.instrument, this.instrumentClassName);
            }
        }

        private boolean checkServices(String[] expectedServices) {
            block0: for (String name : expectedServices) {
                for (Object obj : this.services) {
                    if (this.findType(name, obj.getClass())) continue block0;
                }
                InstrumentationHandler.failInstrumentInitialization(this.env, String.format("%s declares service %s but doesn't register it", this.instrumentClassName, name), null);
            }
            return true;
        }

        private boolean findType(String name, Class<?> type) {
            if (type == null) {
                return false;
            }
            if (type.getName().equals(name) || type.getCanonicalName() != null && type.getCanonicalName().equals(name)) {
                return true;
            }
            if (this.findType(name, type.getSuperclass())) {
                return true;
            }
            for (Class<?> inter : type.getInterfaces()) {
                if (!this.findType(name, inter)) continue;
                return true;
            }
            return false;
        }

        boolean isInitialized() {
            return this.instrument != null;
        }

        TruffleInstrument getInstrument() {
            return this.instrument;
        }

        @Override
        public <T extends ContextsListener> EventBinding<T> attachContextsListener(T listener, boolean includeActiveContexts) {
            return InstrumentationHandler.this.attachContextsListener(this, listener, includeActiveContexts);
        }

        @Override
        public <T extends ThreadsListener> EventBinding<T> attachThreadsListener(T listener, boolean includeStartedThreads) {
            return InstrumentationHandler.this.attachThreadsListener(this, listener, includeStartedThreads);
        }

        @Override
        void doFinalize() {
            this.instrument.onFinalize(this.env);
        }

        @Override
        void dispose() {
            this.instrument.onDispose(this.env);
        }

        @Override
        public EventBinding<? extends ThreadsActivationListener> attachThreadsActivationListener(ThreadsActivationListener listener) {
            return InstrumentationHandler.this.attachThreadsActivationListener(this, listener);
        }

        @Override
        <T> T lookup(InstrumentationHandler handler, Class<T> type) {
            if (this.services != null) {
                for (Object service2 : this.services) {
                    if (!type.isInstance(service2)) continue;
                    return type.cast(service2);
                }
            }
            return null;
        }
    }

    private final class Visitor
    implements NodeVisitor {
        RootNode root;
        SourceSection rootSourceSection;
        Set<Class<?>> providedTags;
        Set<?> materializeLimitedTags;
        boolean firstExecution = false;
        boolean setExecutedRootNodeBit = false;
        int rootBits;
        int computingRootNodeBits;
        boolean visitingRetiredNodes;
        boolean visitingMaterialized;
        private final boolean shouldMaterializeSyntaxNodes;
        Set<Class<? extends Tag>> materializeTags;
        private final List<VisitOperation> operations;
        private final boolean singleBindingOptimization;
        private boolean singleBindingOptimizationPass;
        private boolean onlyAlwaysPerformOperationsActive;
        private Node savedParent;
        private SourceSection savedParentSourceSection;

        Visitor(boolean shouldMaterializeSyntaxNodes, List<VisitOperation> operations) {
            this.shouldMaterializeSyntaxNodes = shouldMaterializeSyntaxNodes;
            this.operations = operations;
            int singleBindingOperations = 0;
            int multiBindingOriginalTreeOperations = 0;
            for (VisitOperation operation : operations) {
                if (operation.alwaysPerform) continue;
                if (operation.singleBindingOperation) {
                    ++singleBindingOperations;
                    continue;
                }
                if (operation.scope != VisitOperation.Scope.ALL && operation.scope != VisitOperation.Scope.ONLY_ORIGINAL) continue;
                ++multiBindingOriginalTreeOperations;
            }
            this.singleBindingOptimization = operations.size() == 1 && singleBindingOperations == 1 || singleBindingOperations == 1 && multiBindingOriginalTreeOperations == 0;
            HashSet compoundTags = null;
            block1: for (VisitOperation operation : operations) {
                if (operation.alwaysPerform) continue;
                for (EventBinding.Source<?> sourceBinding : operation.bindingsAtConstructionTime) {
                    Set<Class<?>> limitedTags = sourceBinding.getLimitedTags();
                    if (limitedTags == null) {
                        compoundTags = null;
                        break block1;
                    }
                    if (compoundTags == null) {
                        compoundTags = new HashSet();
                    }
                    compoundTags.addAll(limitedTags);
                }
            }
            this.materializeLimitedTags = compoundTags != null ? Collections.unmodifiableSet(compoundTags) : null;
        }

        boolean shouldVisit() {
            if (this.operations.isEmpty()) {
                return false;
            }
            RootNode localRoot = this.root;
            SourceSection localRootSourceSection = this.rootSourceSection;
            int localRootBits = this.rootBits;
            for (VisitOperation operation : this.operations) {
                boolean pass;
                if (operation.alwaysPerform || this.singleBindingOptimization && !operation.singleBindingOperation || !(pass = operation.shouldVisit(this.providedTags, localRoot, localRootSourceSection, localRootBits))) continue;
                if (this.singleBindingOptimization) {
                    this.singleBindingOptimizationPass = true;
                }
                return true;
            }
            this.onlyAlwaysPerformOperationsActive = true;
            for (VisitOperation operation : this.operations) {
                if (!operation.alwaysPerform || operation.scope == VisitOperation.Scope.ONLY_MATERIALIZED || !operation.shouldVisit(this.providedTags, localRoot, localRootSourceSection, localRootBits)) continue;
                return true;
            }
            this.onlyAlwaysPerformOperationsActive = false;
            return false;
        }

        private void computeRootBits(SourceSection sourceSection) {
            int bits = this.computingRootNodeBits;
            if (RootNodeBits.isUninitialized(bits)) {
                return;
            }
            if (sourceSection != null) {
                if (RootNodeBits.isNoSourceSection(bits)) {
                    bits = RootNodeBits.setHasSourceSection(bits);
                }
                if (this.rootSourceSection != null) {
                    if (RootNodeBits.isSourceSectionsHierachical(bits) && (sourceSection.getCharIndex() < this.rootSourceSection.getCharIndex() || sourceSection.getCharEndIndex() > this.rootSourceSection.getCharEndIndex())) {
                        bits = RootNodeBits.setSourceSectionsUnstructured(bits);
                    }
                    if (RootNodeBits.isSameSource(bits) && this.rootSourceSection.getSource() != sourceSection.getSource()) {
                        bits = RootNodeBits.setHasDifferentSource(bits);
                    }
                } else {
                    bits = RootNodeBits.setSourceSectionsUnstructured(bits);
                    bits = RootNodeBits.setHasDifferentSource(bits);
                }
            }
            this.computingRootNodeBits = bits;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean visit(Node originalNode) {
            Node node = originalNode;
            SourceSection sourceSection = node.getSourceSection();
            boolean instrumentable = InstrumentationHandler.isInstrumentableNode(node);
            Node previousParent = null;
            SourceSection previousParentSourceSection = null;
            if (instrumentable) {
                this.computeRootBits(sourceSection);
                boolean hasRetiredNodes = this.visitPreviouslyRetiredNodes(node);
                if (!this.visitingRetiredNodes) {
                    if (this.saveAndVisitNewlyRetiredNode(node = this.materialize(node, sourceSection, originalNode), sourceSection, originalNode)) {
                        hasRetiredNodes = true;
                    }
                    if (!hasRetiredNodes) {
                        InstrumentationHandler.clearRetiredNodeReference(node);
                    }
                }
                this.visitInstrumentable(this.savedParent, this.savedParentSourceSection, node, sourceSection);
                previousParent = this.savedParent;
                previousParentSourceSection = this.savedParentSourceSection;
                this.savedParent = node;
                this.savedParentSourceSection = sourceSection;
            }
            boolean wasVisitingMaterialized = this.visitingMaterialized;
            if (node != originalNode) {
                this.visitingMaterialized = true;
            }
            try {
                NodeUtil.forEachChild(node, this);
            }
            finally {
                this.visitingMaterialized = wasVisitingMaterialized;
                if (instrumentable) {
                    this.savedParent = previousParent;
                    this.savedParentSourceSection = previousParentSourceSection;
                }
            }
            return true;
        }

        private Node materialize(Node node, SourceSection sourceSection, Node originalNode) {
            Node materializedNode = this.materializeSyntaxNodes(node, sourceSection);
            assert (!this.visitingMaterialized || materializedNode == originalNode) : "New tree should be fully materialized!";
            assert (materializedNode == this.materializeSyntaxNodes(materializedNode, sourceSection)) : "Node must not be materialized multiple times for the same set of tags!";
            return materializedNode;
        }

        private boolean saveAndVisitNewlyRetiredNode(Node node, SourceSection sourceSection, Node originalNode) {
            if (!this.firstExecution && node != originalNode) {
                assert (this.materializeTags != null) : "Materialize tags must not be null when materialization happened.";
                InstrumentableNode.WrapperNode wrapperNode = InstrumentationHandler.getWrapperNode(node);
                if (wrapperNode == null) {
                    InstrumentationHandler.this.insertWrapper(node, sourceSection);
                }
                wrapperNode = InstrumentationHandler.getWrapperNode(node);
                assert (wrapperNode != null) : "Node must have an instrumentation wrapper at this point!";
                wrapperNode.getProbeNode().setRetiredNode(originalNode, this.materializeTags);
                this.visitRetiredNodes(originalNode);
                return true;
            }
            return false;
        }

        private boolean visitPreviouslyRetiredNodes(Node node) {
            if (!this.firstExecution) {
                ProbeNode.RetiredNodeReference retiredNodeReference;
                InstrumentableNode.WrapperNode wrapperNode = InstrumentationHandler.getWrapperNode(node);
                ProbeNode.RetiredNodeReference retiredNodeReference2 = retiredNodeReference = wrapperNode != null ? wrapperNode.getProbeNode().getRetiredNodeReference() : null;
                if (retiredNodeReference != null) {
                    boolean hasRetiredNodes = false;
                    while (retiredNodeReference != null) {
                        Node nodeRefNode = retiredNodeReference.getNode();
                        if (nodeRefNode != null) {
                            hasRetiredNodes = true;
                            this.visitRetiredNodes(nodeRefNode);
                        }
                        retiredNodeReference = retiredNodeReference.next;
                    }
                    return hasRetiredNodes;
                }
            }
            return false;
        }

        private void visitRetiredNodes(Node retiredSubtreeRoot) {
            boolean wasVisitingRetiredNodes = this.visitingRetiredNodes;
            this.visitingRetiredNodes = true;
            try {
                NodeUtil.forEachChild(retiredSubtreeRoot, this);
            }
            finally {
                this.visitingRetiredNodes = wasVisitingRetiredNodes;
            }
        }

        private Node materializeSyntaxNodes(Node instrumentableNode, SourceSection sourceSection) {
            if (!this.shouldMaterializeSyntaxNodes) {
                return instrumentableNode;
            }
            if (instrumentableNode instanceof InstrumentableNode) {
                assert (this.materializeTags != null) : "Materialize tags must not be null before materialization.";
                InstrumentableNode currentNode = (InstrumentableNode)((Object)instrumentableNode);
                assert (currentNode.isInstrumentable());
                InstrumentableNode materializedNode = currentNode.materializeInstrumentableNodes(this.materializeTags);
                if (currentNode != materializedNode) {
                    if (!(materializedNode instanceof Node)) {
                        throw new IllegalStateException("The returned materialized syntax node is not a Truffle Node.");
                    }
                    if (((Node)((Object)materializedNode)).getParent() != null) {
                        throw new IllegalStateException("The returned materialized syntax node is already adopted.");
                    }
                    SourceSection newSourceSection = ((Node)((Object)materializedNode)).getSourceSection();
                    if (!Objects.equals(sourceSection, newSourceSection)) {
                        throw new IllegalStateException(String.format("The source section of the materialized syntax node must match the source section of the original node. %s != %s.", sourceSection, newSourceSection));
                    }
                    Node currentParent = ((Node)((Object)currentNode)).getParent();
                    if (currentParent instanceof InstrumentableNode.WrapperNode && !NodeUtil.isReplacementSafe(currentParent, instrumentableNode, (Node)((Object)materializedNode))) {
                        ProbeNode probe = ((InstrumentableNode.WrapperNode)((Object)currentParent)).getProbeNode();
                        InstrumentableNode.WrapperNode wrapper = materializedNode.createWrapper(probe);
                        Node wrapperNode = InstrumentationHandler.getWrapperNodeChecked(wrapper, (Node)((Object)materializedNode), currentParent.getParent());
                        currentParent.replace(wrapperNode, "Insert instrumentation wrapper node.");
                        return (Node)((Object)materializedNode);
                    }
                    return ((Node)((Object)currentNode)).replace((Node)((Object)materializedNode));
                }
            }
            return instrumentableNode;
        }

        void preVisit(RootNode r, Node visitRoot, boolean firstExec) {
            this.firstExecution = firstExec;
            this.root = r;
            this.providedTags = InstrumentationHandler.this.getProvidedTags(r);
            this.rootSourceSection = r.getSourceSection();
            this.materializeTags = this.materializeLimitedTags == null ? this.providedTags : this.materializeLimitedTags;
            for (VisitOperation operation : this.operations) {
                operation.preVisit(r, this.rootSourceSection, this.setExecutedRootNodeBit || RootNodeBits.wasExecuted(this.rootBits), visitRoot);
            }
        }

        void postVisit() {
            for (VisitOperation operation : this.operations) {
                operation.postVisitCleanup();
            }
            for (VisitOperation operation : this.operations) {
                operation.postVisitNotifications();
            }
        }

        boolean shouldPerformForBinding(VisitOperation operation, EventBinding.Source<?> binding, Node parentInstrumentable, SourceSection parentSourceSection, Node instrumentableNode, SourceSection sourceSection) {
            if (this.singleBindingOptimization && operation.singleBindingOperation) {
                if (this.singleBindingOptimizationPass) {
                    return binding.isInstrumentedLeaf(this.providedTags, instrumentableNode, sourceSection) || binding.isChildInstrumentedLeaf(this.providedTags, this.root, parentInstrumentable, parentSourceSection, instrumentableNode, sourceSection);
                }
                return false;
            }
            return binding.isInstrumentedFull(this.providedTags, this.root, instrumentableNode, sourceSection) || binding.isChildInstrumentedFull(this.providedTags, this.root, parentInstrumentable, parentSourceSection, instrumentableNode, sourceSection);
        }

        void visitInstrumentable(Node parentInstrumentable, SourceSection parentSourceSection, Node instrumentableNode, SourceSection sourceSection) {
            block0: for (VisitOperation operation : this.operations) {
                if (operation.scope != VisitOperation.Scope.ALL && (this.visitingMaterialized || operation.scope != VisitOperation.Scope.ONLY_ORIGINAL) && (!this.visitingMaterialized || operation.scope != VisitOperation.Scope.ONLY_MATERIALIZED)) continue;
                if (!operation.alwaysPerform) {
                    for (EventBinding.Source<?> binding : operation.bindingsAtConstructionTime) {
                        if (this.shouldPerformForBinding(operation, binding, parentInstrumentable, parentSourceSection, instrumentableNode, sourceSection)) {
                            assert (!this.onlyAlwaysPerformOperationsActive) : "No operation that depends on bindings should be performed here!";
                            if (TRACE) {
                                InstrumentationHandler.traceFilterCheck("hit", instrumentableNode, sourceSection);
                            }
                            operation.perform(binding, instrumentableNode, sourceSection, this.setExecutedRootNodeBit || RootNodeBits.wasExecuted(this.rootBits));
                            if (operation.performForEachBinding) continue;
                            continue block0;
                        }
                        if (!TRACE) continue;
                        InstrumentationHandler.traceFilterCheck("miss", instrumentableNode, sourceSection);
                    }
                    continue;
                }
                if (TRACE) {
                    InstrumentationHandler.traceFilterCheck("hit", instrumentableNode, sourceSection);
                }
                operation.perform(null, instrumentableNode, sourceSection, this.setExecutedRootNodeBit || RootNodeBits.wasExecuted(this.rootBits));
            }
        }
    }

    private class VisitorBuilder {
        List<VisitOperation> operations = new ArrayList<VisitOperation>();
        boolean shouldMaterializeSyntaxNodes;
        private boolean hasFindSourcesOperation;
        private boolean hasFindSourcesExecutedOperation;

        private VisitorBuilder() {
        }

        VisitorBuilder addNotifyLoadedOperationForAllBindings(VisitOperation.Scope scope) {
            if (!InstrumentationHandler.this.sourceSectionBindings.isEmpty()) {
                this.operations.add(new NotifyLoadedOperation(scope, InstrumentationHandler.this.sourceSectionBindings));
                this.shouldMaterializeSyntaxNodes = true;
            }
            return this;
        }

        VisitorBuilder addNotifyLoadedOperationForBinding(VisitOperation.Scope scope, EventBinding.Source<?> binding) {
            this.operations.add(new NotifyLoadedOperation(scope, binding));
            this.shouldMaterializeSyntaxNodes = true;
            return this;
        }

        VisitorBuilder addFindSourcesOperation(VisitOperation.Scope scope) {
            return this.addFindSourcesOperation(scope, false);
        }

        VisitorBuilder addFindSourcesOperation(VisitOperation.Scope scope, boolean dontNotifyBindings) {
            if (this.hasFindSourcesOperation) {
                throw new IllegalStateException("Visitor can have at most one find sources operation!");
            }
            this.operations.add(new FindSourcesOperation(scope, InstrumentationHandler.this.threadLocalNewSourcesLoaded, InstrumentationHandler.this.sourcesLoaded, dontNotifyBindings, false));
            this.hasFindSourcesOperation = true;
            return this;
        }

        VisitorBuilder addFindSourcesExecutedOperation(VisitOperation.Scope scope) {
            return this.addFindSourcesExecutedOperation(scope, false);
        }

        VisitorBuilder addFindSourcesExecutedOperation(VisitOperation.Scope scope, boolean dontNotifyBindings) {
            if (this.hasFindSourcesExecutedOperation) {
                throw new IllegalStateException("Visitor can have at most one find executed sources operation!");
            }
            this.operations.add(new FindSourcesOperation(scope, InstrumentationHandler.this.threadLocalNewSourcesExecuted, InstrumentationHandler.this.sourcesExecuted, dontNotifyBindings, true));
            this.hasFindSourcesExecutedOperation = true;
            return this;
        }

        VisitorBuilder addInsertWrapperOperationForAllBindings(VisitOperation.Scope scope) {
            if (!InstrumentationHandler.this.executionBindings.isEmpty()) {
                this.operations.add(new InsertWrapperOperation(scope, InstrumentationHandler.this.executionBindings));
                this.shouldMaterializeSyntaxNodes = true;
            }
            return this;
        }

        VisitorBuilder addInsertWrapperOperationForBinding(VisitOperation.Scope scope, EventBinding.Source<?> binding) {
            this.operations.add(new InsertWrapperOperation(scope, binding));
            this.shouldMaterializeSyntaxNodes = true;
            return this;
        }

        VisitorBuilder addDisposeWrapperOperationForBinding(EventBinding.Source<?> binding) {
            this.operations.add(new DisposeWrapperOperation(VisitOperation.Scope.ALL, binding));
            return this;
        }

        VisitorBuilder addDisposeWrapperOperationForBindings(CopyOnWriteList<EventBinding.Source<?>> bindings) {
            this.operations.add(new DisposeWrapperOperation(VisitOperation.Scope.ALL, bindings));
            return this;
        }

        Visitor buildVisitor() {
            return new Visitor(this.shouldMaterializeSyntaxNodes, Collections.unmodifiableList(this.operations));
        }
    }

    private static class FindSourcesOperation
    extends VisitOperation {
        private final ThreadLocal<Map<Source, Void>> threadLocalNewSources;
        private final boolean dontNotifyBindings;
        private final SourceInstrumentationHandler sourceInstrumentationHandler;
        private final boolean performOnlyOnExecutedAST;
        private Map<Source, Void> newSources;
        private boolean updateGlobalSourceList;

        FindSourcesOperation(VisitOperation.Scope scope, ThreadLocal<Map<Source, Void>> threadLocalNewSources, SourceInstrumentationHandler sourceInstrumentationHandler, boolean dontNotifyBindings, boolean performOnlyOnExecutedAST) {
            super(scope, sourceInstrumentationHandler.getBindingsArray(), false, true);
            this.threadLocalNewSources = threadLocalNewSources;
            this.sourceInstrumentationHandler = sourceInstrumentationHandler;
            this.dontNotifyBindings = dontNotifyBindings;
            this.performOnlyOnExecutedAST = performOnlyOnExecutedAST;
        }

        @Override
        protected boolean shouldVisit(Set<Class<?>> providedTags, RootNode rootNode, SourceSection rootSourceSection, int rootNodeBits) {
            return this.bindingsAtConstructionTime.length > 0 && !RootNodeBits.isNoSourceSection(rootNodeBits) && (!RootNodeBits.isSameSource(rootNodeBits) || rootSourceSection == null);
        }

        @Override
        protected void preVisit(RootNode root, SourceSection rootSourceSection, boolean executedRoot, Node visitRoot) {
            Map<Source, Void> localNewSources = this.threadLocalNewSources.get();
            if (localNewSources == null) {
                localNewSources = new LinkedHashMap<Source, Void>();
                this.threadLocalNewSources.set(localNewSources);
                this.updateGlobalSourceList = true;
            } else {
                this.updateGlobalSourceList = false;
            }
            this.newSources = localNewSources;
            if (rootSourceSection != null && (!this.performOnlyOnExecutedAST || executedRoot) && this.scope != VisitOperation.Scope.ONLY_MATERIALIZED && root == visitRoot) {
                this.adoptSource(rootSourceSection.getSource());
            }
        }

        @Override
        protected void perform(EventBinding.Source<?> binding, Node node, SourceSection section, boolean executedRoot) {
            if ((!this.performOnlyOnExecutedAST || executedRoot) && section != null) {
                this.adoptSource(section.getSource());
            }
        }

        void adoptSource(Source source) {
            if (!this.newSources.containsKey(source)) {
                this.newSources.put(source, null);
            }
        }

        @Override
        protected void postVisitCleanup() {
            if (this.updateGlobalSourceList) {
                this.threadLocalNewSources.set(null);
            }
        }

        @Override
        protected void postVisitNotifications() {
            if (this.updateGlobalSourceList) {
                if (this.newSources.isEmpty()) {
                    return;
                }
                SourceInstrumentationHandler.SourcesNotificationQueue notifications = this.sourceInstrumentationHandler.addNewSources(this.newSources, !this.dontNotifyBindings);
                if (notifications != null) {
                    notifications.process();
                }
            }
        }
    }

    private static class DisposeWrapperOperation
    extends VisitOperation {
        DisposeWrapperOperation(VisitOperation.Scope scope, EventBinding.Source<?> binding) {
            super(scope, binding);
        }

        DisposeWrapperOperation(VisitOperation.Scope scope, CopyOnWriteList<EventBinding.Source<?>> bindings) {
            super(scope, bindings.getArray(), false);
        }

        @Override
        protected void perform(EventBinding.Source<?> binding, Node node, SourceSection section, boolean executedRoot) {
            InstrumentationHandler.invalidateWrapper(node);
        }
    }

    private static class BindingLoadSourceSectionEvent {
        private final EventBinding.Source<?> binding;
        private final Node node;
        private final SourceSection sourceSection;

        BindingLoadSourceSectionEvent(EventBinding.Source<?> binding, Node node, SourceSection sourceSection) {
            this.binding = binding;
            this.node = node;
            this.sourceSection = sourceSection;
        }
    }

    private class NotifyLoadedOperation
    extends VisitOperation {
        List<BindingLoadSourceSectionEvent> sourceSectionLoadedList;
        boolean notifyBindings;

        NotifyLoadedOperation(VisitOperation.Scope scope, EventBinding.Source<?> binding) {
            super(scope, binding);
        }

        NotifyLoadedOperation(VisitOperation.Scope scope, CopyOnWriteList<EventBinding.Source<?>> bindings) {
            super(scope, bindings.getArray(), true);
        }

        @Override
        protected void preVisit(RootNode root, SourceSection rootSourceSection, boolean executedRoot, Node visitRoot) {
            List<BindingLoadSourceSectionEvent> localSourceSectionLoadedList = InstrumentationHandler.this.threadLocalSourceSectionLoadedList.get();
            if (localSourceSectionLoadedList == null) {
                localSourceSectionLoadedList = new ArrayList<BindingLoadSourceSectionEvent>();
                InstrumentationHandler.this.threadLocalSourceSectionLoadedList.set(localSourceSectionLoadedList);
                this.notifyBindings = true;
            } else {
                this.notifyBindings = false;
            }
            this.sourceSectionLoadedList = localSourceSectionLoadedList;
        }

        @Override
        protected void perform(EventBinding.Source<?> binding, Node node, SourceSection section, boolean executedRoot) {
            if (section != null) {
                this.sourceSectionLoadedList.add(new BindingLoadSourceSectionEvent(binding, node, section));
            }
        }

        @Override
        protected void postVisitCleanup() {
            if (this.notifyBindings) {
                InstrumentationHandler.this.threadLocalSourceSectionLoadedList.set(null);
            }
        }

        @Override
        protected void postVisitNotifications() {
            if (this.notifyBindings) {
                for (BindingLoadSourceSectionEvent loadEvent : this.sourceSectionLoadedList) {
                    InstrumentationHandler.notifySourceSectionLoaded(loadEvent.binding, loadEvent.node, loadEvent.sourceSection);
                }
            }
        }
    }

    private class InsertWrapperOperation
    extends VisitOperation {
        InsertWrapperOperation(VisitOperation.Scope scope, EventBinding.Source<?> binding) {
            super(scope, binding);
        }

        InsertWrapperOperation(VisitOperation.Scope scope, CopyOnWriteList<EventBinding.Source<?>> bindings) {
            super(scope, bindings.getArray(), false);
        }

        @Override
        protected void perform(EventBinding.Source<?> binding, Node node, SourceSection section, boolean executedRoot) {
            InstrumentationHandler.this.insertWrapper(node, section);
        }
    }

    private static abstract class VisitOperation {
        protected final Scope scope;
        protected EventBinding.Source<?>[] bindingsAtConstructionTime;
        private final boolean singleBindingOperation;
        private final boolean performForEachBinding;
        private final boolean alwaysPerform;

        VisitOperation(Scope scope, EventBinding.Source<?> binding) {
            this(scope, new EventBinding.Source[]{binding}, true, true, false);
        }

        VisitOperation(Scope scope, EventBinding.Source<?>[] bindingsArray, boolean performForEachBinding) {
            this(scope, bindingsArray, false, performForEachBinding, false);
        }

        VisitOperation(Scope scope, EventBinding.Source<?>[] bindingsArray, boolean performForEachBinding, boolean alwaysPerform) {
            this(scope, bindingsArray, false, performForEachBinding, alwaysPerform);
        }

        VisitOperation(Scope scope, EventBinding.Source<?>[] bindingsArray, boolean singleBindingOperation, boolean performForEachBinding, boolean alwaysPerform) {
            this.scope = scope;
            this.bindingsAtConstructionTime = bindingsArray;
            this.singleBindingOperation = singleBindingOperation;
            this.performForEachBinding = performForEachBinding;
            this.alwaysPerform = alwaysPerform;
        }

        protected abstract void perform(EventBinding.Source<?> var1, Node var2, SourceSection var3, boolean var4);

        protected boolean shouldVisit(Set<Class<?>> providedTags, RootNode rootNode, SourceSection rootSourceSection, int rootNodeBits) {
            for (EventBinding.Source<?> binding : this.bindingsAtConstructionTime) {
                if (!binding.isInstrumentedRoot(providedTags, rootNode, rootSourceSection, rootNodeBits)) continue;
                return true;
            }
            return false;
        }

        protected void preVisit(RootNode root, SourceSection rootSourceSection, boolean executedRoot, Node visitRoot) {
        }

        protected void postVisitCleanup() {
        }

        protected void postVisitNotifications() {
        }

        static enum Scope {
            ALL,
            ONLY_ORIGINAL,
            ONLY_MATERIALIZED;

        }
    }
}

