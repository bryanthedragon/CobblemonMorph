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
   private final ThreadLocal<Map<Source, Void>> threadLocalNewSourcesLoaded = new ThreadLocal<>();
   private final ThreadLocal<Map<Source, Void>> threadLocalNewSourcesExecuted = new ThreadLocal<>();
   private final ThreadLocal<List<InstrumentationHandler.BindingLoadSourceSectionEvent>> threadLocalSourceSectionLoadedList = new ThreadLocal<>();
   final Collection<RootNode> loadedRoots = new InstrumentationHandler.WeakAsyncList<>(256);
   private final Collection<RootNode> executedRoots = new InstrumentationHandler.WeakAsyncList<>(64);
   private final Collection<AllocationReporter> allocationReporters = new InstrumentationHandler.WeakAsyncList<>(16);
   private volatile boolean hasLoadOrExecutionBinding = false;
   private final InstrumentationHandler.CopyOnWriteList<EventBinding.Source<?>> executionBindings = new InstrumentationHandler.CopyOnWriteList<>(
      new EventBinding.Source[0]
   );
   private final InstrumentationHandler.CopyOnWriteList<EventBinding.Source<?>> sourceSectionBindings = new InstrumentationHandler.CopyOnWriteList<>(
      new EventBinding.Source[0]
   );
   private final SourceInstrumentationHandler sourcesLoaded = new SourceInstrumentationHandler(new BiConsumer<EventBinding.Source<?>[], Source>() {
      public void accept(EventBinding.Source<?>[] bindings, Source source) {
         InstrumentationHandler.notifySourceLoadedBindings(bindings, source);
      }
   });
   private final SourceInstrumentationHandler sourcesExecuted = new SourceInstrumentationHandler(new BiConsumer<EventBinding.Source<?>[], Source>() {
      public void accept(EventBinding.Source<?>[] bindings, Source source) {
         InstrumentationHandler.notifySourceExecutedBindings(bindings, source);
      }
   });
   private final Collection<EventBinding<? extends OutputStream>> outputStdBindings = new InstrumentationHandler.EventBindingList<>(1);
   private final Collection<EventBinding<? extends OutputStream>> outputErrBindings = new InstrumentationHandler.EventBindingList<>(1);
   private final Collection<EventBinding.Allocation<? extends AllocationListener>> allocationBindings = new InstrumentationHandler.EventBindingList<>(2);
   private final Collection<EventBinding<? extends ContextsListener>> contextsBindings = new InstrumentationHandler.EventBindingList<>(8);
   private final Collection<EventBinding<? extends ThreadsListener>> threadsBindings = new InstrumentationHandler.EventBindingList<>(8);
   private final Collection<EventBinding<? extends ThreadsActivationListener>> threadsActivationBindings = new InstrumentationHandler.EventBindingList<>(8);
   @CompilerDirectives.CompilationFinal
   private volatile InstrumentationHandler.StableThreadsActivationListeners stableActivationListeners;
   final ConcurrentHashMap<Object, InstrumentationHandler.AbstractInstrumenter> instrumenterMap = new ConcurrentHashMap<>();
   private DispatchOutputStream out;
   private DispatchOutputStream err;
   private InputStream in;
   private MessageTransport messageInterceptor;
   private final Map<Class<?>, Set<Class<?>>> cachedProvidedTags = new ConcurrentHashMap<>();
   final InstrumentationHandler.EngineInstrumenter engineInstrumenter;

   InstrumentationHandler(Object polyglotEngine, DispatchOutputStream out, DispatchOutputStream err, InputStream in, MessageTransport messageInterceptor) {
      this.polyglotEngine = polyglotEngine;
      this.out = out;
      this.err = err;
      this.in = in;
      this.messageInterceptor = messageInterceptor;
      this.engineInstrumenter = new InstrumentationHandler.EngineInstrumenter();
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

         trace("ON-LOAD: %-5s CallTarget: %s%n", lang, name);
      }

      if (InstrumentAccessor.nodesAccess().getSharingLayer(root) != null) {
         this.loadedRoots.add(root);
         if (this.hasLoadOrExecutionBinding && (!this.sourceSectionBindings.isEmpty() || this.sourcesLoaded.hasBindings())) {
            InstrumentationHandler.VisitorBuilder visitorBuilder = new InstrumentationHandler.VisitorBuilder();
            visitorBuilder.addNotifyLoadedOperationForAllBindings(InstrumentationHandler.VisitOperation.Scope.ALL);
            visitorBuilder.addFindSourcesOperation(InstrumentationHandler.VisitOperation.Scope.ALL);
            visitRoot(root, root, visitorBuilder.buildVisitor(), false, true);
         }
      }
   }

   void onFirstExecution(RootNode root) {
      if (InstrumentAccessor.nodesAccess().isInstrumentable(root)) {
         this.executedRoots.add(root);
         if (this.hasLoadOrExecutionBinding && (!this.executionBindings.isEmpty() || this.sourcesExecuted.hasBindings())) {
            InstrumentationHandler.VisitorBuilder visitorBuilder = new InstrumentationHandler.VisitorBuilder();
            visitorBuilder.addInsertWrapperOperationForAllBindings(InstrumentationHandler.VisitOperation.Scope.ALL);
            visitorBuilder.addNotifyLoadedOperationForAllBindings(InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED);
            visitorBuilder.addFindSourcesOperation(InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED);
            visitorBuilder.addFindSourcesExecutedOperation(InstrumentationHandler.VisitOperation.Scope.ALL);
            visitRoot(root, root, visitorBuilder.buildVisitor(), false, true, true);
         }
      }
   }

   void initializeInstrument(Object polyglotInstrument, String instrumentClassName, Supplier<? extends Object> instrumentSupplier) {
      if (TRACE) {
         trace("Initialize instrument class %s %n", instrumentClassName);
      }

      TruffleInstrument.Env env = new TruffleInstrument.Env(polyglotInstrument, this.out, this.err, this.in, this.messageInterceptor);
      TruffleInstrument instrument = (TruffleInstrument)instrumentSupplier.get();
      if (instrument.contextLocals == null) {
         instrument.contextLocals = Collections.emptyList();
      } else {
         instrument.contextLocals = Collections.unmodifiableList(instrument.contextLocals);
      }

      InstrumentAccessor.ENGINE.initializeInstrumentContextLocal(instrument.contextLocals, polyglotInstrument);
      if (instrument.contextThreadLocals == null) {
         instrument.contextThreadLocals = Collections.emptyList();
      } else {
         instrument.contextThreadLocals = Collections.unmodifiableList(instrument.contextThreadLocals);
      }

      InstrumentAccessor.ENGINE.initializeInstrumentContextThreadLocal(instrument.contextThreadLocals, polyglotInstrument);

      try {
         env.instrumenter = new InstrumentationHandler.InstrumentClientInstrumenter(env, instrumentClassName);
         env.instrumenter.instrument = instrument;
      } catch (Exception var7) {
         failInstrumentInitialization(env, String.format("Failed to create new instrumenter class %s", instrumentClassName), var7);
         return;
      }

      if (TRACE) {
         trace("Initialized instrument %s class %s %n", env.instrumenter.instrument, instrumentClassName);
      }

      this.addInstrumenter(polyglotInstrument, env.instrumenter);
   }

   void createInstrument(Object vmObject, String[] expectedServices, OptionValues optionValues) {
      InstrumentationHandler.InstrumentClientInstrumenter instrumenter = (InstrumentationHandler.InstrumentClientInstrumenter)this.instrumenterMap
         .get(vmObject);
      instrumenter.env.options = optionValues;
      instrumenter.create(expectedServices);
   }

   void finalizeInstrumenter(Object key) {
      InstrumentationHandler.AbstractInstrumenter finalisingInstrumenter = this.instrumenterMap.get(key);
      if (finalisingInstrumenter == null) {
         throw new AssertionError("Instrumenter already disposed.");
      } else {
         finalisingInstrumenter.doFinalize();
      }
   }

   void disposeInstrumenter(Object key, boolean cleanupRequired) {
      InstrumentationHandler.AbstractInstrumenter disposedInstrumenter = this.instrumenterMap.remove(key);
      if (disposedInstrumenter == null) {
         throw new AssertionError("Instrumenter already disposed.");
      } else {
         if (TRACE) {
            trace("BEGIN: Dispose instrumenter %n", key);
         }

         disposedInstrumenter.dispose();
         if (cleanupRequired) {
            Collection<EventBinding<?>> disposedExecutionBindings = filterBindingsForInstrumenter(this.executionBindings, disposedInstrumenter);
            setDisposingBindingsBulk(disposedExecutionBindings);
            if (!disposedExecutionBindings.isEmpty()) {
               InstrumentationHandler.VisitorBuilder visitorBuilder = new InstrumentationHandler.VisitorBuilder();
               visitorBuilder.addDisposeWrapperOperationForBindings(
                  new InstrumentationHandler.CopyOnWriteList<>(disposedExecutionBindings.toArray(new EventBinding.Source[0]))
               );
               visitRoots(this.executedRoots, visitorBuilder.buildVisitor());
            }

            disposeBindingsBulk(disposedExecutionBindings);
            this.executionBindings.removeAll(disposedExecutionBindings);
            Collection<EventBinding<?>> disposedSourceSectionBindings = filterBindingsForInstrumenter(this.sourceSectionBindings, disposedInstrumenter);
            disposeBindingsBulk(disposedSourceSectionBindings);
            this.sourceSectionBindings.removeAll(disposedSourceSectionBindings);
            this.sourcesLoaded.clearForDisposedInstrumenter(disposedInstrumenter);
            this.sourcesExecuted.clearForDisposedInstrumenter(disposedInstrumenter);
            disposeOutputBindingsBulk(this.out, this.outputStdBindings);
            disposeOutputBindingsBulk(this.err, this.outputErrBindings);
            synchronized (this.threadsActivationBindings) {
               Collection<EventBinding<?>> disposedThreadsActivationBindings = filterBindingsForInstrumenter(
                  this.threadsActivationBindings, disposedInstrumenter
               );
               if (!disposedThreadsActivationBindings.isEmpty()) {
                  disposeBindingsBulk(disposedThreadsActivationBindings);
                  this.invalidateThreadsActivationListeners();
               }
            }
         }

         if (TRACE) {
            trace("END: Disposed instrumenter %n", key);
         }
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
      return new InstrumentationHandler.LanguageClientInstrumenter(language);
   }

   <T> EventBinding<T> addExecutionBinding(EventBinding.Source<T> binding) {
      if (TRACE) {
         trace("BEGIN: Adding execution binding %s, %s%n", binding.getFilter(), binding.getElement());
      }

      this.hasLoadOrExecutionBinding = true;
      this.executionBindings.add(binding);
      if (!this.executedRoots.isEmpty()) {
         InstrumentationHandler.VisitorBuilder visitorBuilder = new InstrumentationHandler.VisitorBuilder();
         visitorBuilder.addInsertWrapperOperationForBinding(InstrumentationHandler.VisitOperation.Scope.ONLY_ORIGINAL, binding);
         visitorBuilder.addInsertWrapperOperationForAllBindings(InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED);
         visitorBuilder.addNotifyLoadedOperationForAllBindings(InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED);
         visitorBuilder.addFindSourcesOperation(InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED);
         visitorBuilder.addFindSourcesExecutedOperation(InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED);
         visitRoots(this.executedRoots, visitorBuilder.buildVisitor(), true);
      }

      if (TRACE) {
         trace("END: Added execution binding %s, %s%n", binding.getFilter(), binding.getElement());
      }

      return binding;
   }

   <T> EventBinding<T> addSourceSectionBinding(EventBinding.SourceSectionLoaded<T> binding) {
      if (TRACE) {
         trace("BEGIN: Adding binding %s, %s%n", binding.getFilter(), binding.getElement());
      }

      this.hasLoadOrExecutionBinding = true;
      this.sourceSectionBindings.add(binding);
      if (binding.isNotifyLoaded() && !this.loadedRoots.isEmpty()) {
         InstrumentationHandler.VisitorBuilder visitorBuilder = new InstrumentationHandler.VisitorBuilder();
         visitorBuilder.addNotifyLoadedOperationForBinding(InstrumentationHandler.VisitOperation.Scope.ONLY_ORIGINAL, binding);
         visitorBuilder.addNotifyLoadedOperationForAllBindings(InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED);
         visitorBuilder.addInsertWrapperOperationForAllBindings(InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED);
         visitorBuilder.addFindSourcesOperation(InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED);
         visitorBuilder.addFindSourcesExecutedOperation(InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED);
         visitRoots(this.loadedRoots, visitorBuilder.buildVisitor());
      }

      if (TRACE) {
         trace("END: Added binding %s, %s%n", binding.getFilter(), binding.getElement());
      }

      return binding;
   }

   private void visitLoadedSourceSections(EventBinding.Source<?> binding) {
      if (TRACE) {
         trace("BEGIN: Visiting loaded source sections %s, %s%n", binding.getFilter(), binding.getElement());
      }

      if (!this.loadedRoots.isEmpty()) {
         InstrumentationHandler.VisitorBuilder visitorBuilder = new InstrumentationHandler.VisitorBuilder();
         visitorBuilder.addNotifyLoadedOperationForBinding(InstrumentationHandler.VisitOperation.Scope.ALL, binding);
         visitorBuilder.addNotifyLoadedOperationForAllBindings(InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED);
         visitorBuilder.addInsertWrapperOperationForAllBindings(InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED);
         visitorBuilder.addFindSourcesOperation(InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED);
         visitorBuilder.addFindSourcesExecutedOperation(InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED);
         visitRoots(this.loadedRoots, visitorBuilder.buildVisitor());
      }

      if (TRACE) {
         trace("END: Visited loaded source sections %s, %s%n", binding.getFilter(), binding.getElement());
      }
   }

   <T> EventBinding<T> addSourceLoadedBinding(EventBinding.SourceLoaded<T> binding) {
      if (TRACE) {
         trace("BEGIN: Adding source binding %s, %s%n", binding.getFilter(), binding.getElement());
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
         trace("END: Added source binding %s, %s%n", binding.getFilter(), binding.getElement());
      }

      return binding;
   }

   <T> EventBinding<T> addSourceExecutionBinding(EventBinding.SourceExecuted<T> binding) {
      if (TRACE) {
         trace("BEGIN: Adding source execution binding %s, %s%n", binding.getFilter(), binding.getElement());
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
         trace("END: Added source execution binding %s, %s%n", binding.getFilter(), binding.getElement());
      }

      return binding;
   }

   <T extends OutputStream> EventBinding<T> addOutputBinding(EventBinding<T> binding, boolean errorOutput) {
      if (TRACE) {
         String kind = errorOutput ? "error" : "standard";
         trace("BEGIN: Adding " + kind + " output binding %s%n", binding.getElement());
      }

      if (errorOutput) {
         this.outputErrBindings.add(binding);
         InstrumentAccessor.engineAccess().attachOutputConsumer(this.err, binding.getElement());
      } else {
         this.outputStdBindings.add(binding);
         InstrumentAccessor.engineAccess().attachOutputConsumer(this.out, binding.getElement());
      }

      if (TRACE) {
         String kind = errorOutput ? "error" : "standard";
         trace("END: Added " + kind + " output binding %s%n", binding.getElement());
      }

      return binding;
   }

   private <T extends AllocationListener> EventBinding<T> addAllocationBinding(EventBinding.Allocation<T> binding) {
      if (TRACE) {
         trace("BEGIN: Adding allocation binding %s%n", binding.getElement());
      }

      this.allocationBindings.add(binding);

      for (AllocationReporter allocationReporter : this.allocationReporters) {
         if (binding.getAllocationFilter().contains(allocationReporter.language)) {
            allocationReporter.addListener(binding.getElement());
         }
      }

      if (TRACE) {
         trace("END: Added allocation binding %s%n", binding.getElement());
      }

      return binding;
   }

   private <T extends ContextsListener> EventBinding<T> addContextsBinding(EventBinding<T> binding, boolean includeActiveContexts) {
      if (TRACE) {
         trace("BEGIN: Adding contexts binding %s%n", binding.getElement());
      }

      this.contextsBindings.add(binding);
      if (includeActiveContexts) {
         Accessor.EngineSupport engineAccess = InstrumentAccessor.engineAccess();
         engineAccess.reportAllLanguageContexts(this.polyglotEngine, binding.getElement());
      }

      if (TRACE) {
         trace("END: Added contexts binding %s%n", binding.getElement());
      }

      return binding;
   }

   private <T extends ThreadsListener> EventBinding<T> addThreadsBinding(EventBinding<T> binding, boolean includeStartedThreads) {
      if (TRACE) {
         trace("BEGIN: Adding threads binding %s%n", binding.getElement());
      }

      this.threadsBindings.add(binding);
      if (includeStartedThreads) {
         Accessor.EngineSupport engineAccess = InstrumentAccessor.engineAccess();
         engineAccess.reportAllContextThreads(this.polyglotEngine, binding.getElement());
      }

      if (TRACE) {
         trace("END: Added threads binding %s%n", binding.getElement());
      }

      return binding;
   }

   private void lazyInitializeSourcesLoadedList() {
      try {
         InstrumentationHandler.VisitorBuilder visitorBuilder = new InstrumentationHandler.VisitorBuilder();
         visitorBuilder.addFindSourcesOperation(InstrumentationHandler.VisitOperation.Scope.ALL, true);
         visitRoots(this.loadedRoots, visitorBuilder.buildVisitor(), false);
         this.sourcesLoaded.setInitialized();
      } catch (Throwable var2) {
         this.sourcesLoaded.clearAll();
         throw var2;
      }
   }

   private void lazyInitializeSourcesExecutedList() {
      try {
         InstrumentationHandler.VisitorBuilder visitorBuilder = new InstrumentationHandler.VisitorBuilder();
         visitorBuilder.addFindSourcesExecutedOperation(InstrumentationHandler.VisitOperation.Scope.ALL, true);
         visitRoots(this.executedRoots, visitorBuilder.buildVisitor(), true);
         this.sourcesExecuted.setInitialized();
      } catch (Throwable var2) {
         this.sourcesExecuted.clearAll();
         throw var2;
      }
   }

   private static void visitRoots(Collection<RootNode> roots, InstrumentationHandler.Visitor visitor) {
      for (RootNode root : roots) {
         visitRoot(root, root, visitor, false, false);
      }
   }

   private static void visitRoots(Collection<RootNode> roots, InstrumentationHandler.Visitor visitor, boolean setExecutedRootNodeBit) {
      for (RootNode root : roots) {
         visitRoot(root, root, visitor, false, false, setExecutedRootNodeBit);
      }
   }

   void disposeBinding(EventBinding<?> binding) {
      if (TRACE) {
         trace("BEGIN: Dispose binding %s%n", binding.getElement());
      }

      if (binding instanceof EventBinding.Source) {
         EventBinding.Source<?> sourceBinding = (EventBinding.Source<?>)binding;
         if (sourceBinding.isExecutionEvent()) {
            InstrumentationHandler.VisitorBuilder visitorBuilder = new InstrumentationHandler.VisitorBuilder();
            visitorBuilder.addDisposeWrapperOperationForBinding(sourceBinding);
            visitRoots(this.executedRoots, visitorBuilder.buildVisitor());
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
         EventBinding.Allocation<?> allocationBinding = (EventBinding.Allocation<?>)binding;
         AllocationListener l = (AllocationListener)binding.getElement();

         for (AllocationReporter allocationReporter : this.allocationReporters) {
            if (allocationBinding.getAllocationFilter().contains(allocationReporter.language)) {
               allocationReporter.removeListener(l);
            }
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
               synchronized (this.threadsActivationBindings) {
                  this.invalidateThreadsActivationListeners();
               }
            } else {
               assert false : "Unexpected binding " + binding + " with element " + elm;
            }
         }
      }

      if (TRACE) {
         trace("END: Disposed binding %s%n", binding.getElement());
      }
   }

   EventBinding.Source<?>[] getExecutionBindingsSnapshot() {
      return this.executionBindings.getArray();
   }

   ProbeNode.EventChainNode createBindings(VirtualFrame frame, ProbeNode probeNodeImpl, EventBinding.Source<?>[] executionBindingsSnapshot) {
      EventContext context = probeNodeImpl.getContext();
      SourceSection sourceSection = context.getInstrumentedSourceSection();
      if (TRACE) {
         trace("BEGIN: Lazy update for %s%n", sourceSection);
      }

      Node parentInstrumentable = null;
      SourceSection parentInstrumentableSourceSection = null;

      Node parentNode;
      for (parentNode = probeNodeImpl.getParent(); parentNode != null && parentNode.getParent() != null; parentNode = parentNode.getParent()) {
         if (parentInstrumentable == null) {
            SourceSection parentSourceSection = parentNode.getSourceSection();
            if (isInstrumentableNode(parentNode)) {
               parentInstrumentable = parentNode;
               parentInstrumentableSourceSection = parentSourceSection;
            }
         }
      }

      if (!(parentNode instanceof RootNode)) {
         throw new AssertionError();
      } else {
         RootNode rootNode = (RootNode)parentNode;
         Node var19 = probeNodeImpl.getContext().getInstrumentedNode();
         Set providedTags = this.getProvidedTags(rootNode);
         ProbeNode.EventChainNode root = null;
         ProbeNode.EventChainNode parent = null;

         for (EventBinding.Source<?> binding : executionBindingsSnapshot) {
            if (!binding.disposing) {
               if (binding.isChildInstrumentedFull(providedTags, rootNode, parentInstrumentable, parentInstrumentableSourceSection, var19, sourceSection)) {
                  if (TRACE) {
                     trace("  Found input value binding %s, %s%n", binding.getInputFilter(), System.identityHashCode(binding));
                  }

                  ProbeNode.EventChainNode next = probeNodeImpl.createParentEventChainCallback(frame, binding, rootNode, providedTags);
                  if (next == null) {
                     continue;
                  }

                  if (root == null) {
                     root = next;
                  } else {
                     assert parent != null;

                     parent.setNext(next);
                  }

                  parent = next;
               }

               if (binding.isInstrumentedFull(providedTags, rootNode, var19, sourceSection)) {
                  if (TRACE) {
                     trace("  Found binding %s, %s%n", binding.getFilter(), binding.getElement());
                  }

                  ProbeNode.EventChainNode nextx = probeNodeImpl.createEventChainCallback(frame, binding, rootNode, providedTags, var19, sourceSection);
                  if (nextx != null) {
                     if (root == null) {
                        root = nextx;
                     } else {
                        assert parent != null;

                        parent.setNext(nextx);
                     }

                     parent = nextx;
                  }
               }
            }
         }

         if (TRACE) {
            trace("END: Lazy updated for %s%n", sourceSection);
         }

         return root;
      }
   }

   public void onNodeInserted(RootNode rootNode, Node tree) {
      Node parentInstrumentable = tree;

      while (parentInstrumentable != null && parentInstrumentable.getParent() != null) {
         parentInstrumentable = parentInstrumentable.getParent();
         if (isInstrumentableNode(parentInstrumentable)) {
            break;
         }
      }

      assert parentInstrumentable != null;

      if (this.hasLoadOrExecutionBinding
         && (
            !this.sourceSectionBindings.isEmpty()
               || !this.executionBindings.isEmpty()
               || this.sourcesLoaded.hasBindings()
               || this.sourcesExecuted.hasBindings()
         )) {
         InstrumentationHandler.VisitorBuilder visitorBuilder = new InstrumentationHandler.VisitorBuilder();
         visitorBuilder.addNotifyLoadedOperationForAllBindings(InstrumentationHandler.VisitOperation.Scope.ALL);
         visitorBuilder.addInsertWrapperOperationForAllBindings(InstrumentationHandler.VisitOperation.Scope.ALL);
         visitorBuilder.addFindSourcesOperation(InstrumentationHandler.VisitOperation.Scope.ALL);
         visitorBuilder.addFindSourcesExecutedOperation(InstrumentationHandler.VisitOperation.Scope.ALL);
         visitRoot(rootNode, parentInstrumentable, visitorBuilder.buildVisitor(), true, false);
      }
   }

   private static void notifySourceLoadedBindings(EventBinding.Source<?>[] bindings, Source source) {
      for (EventBinding.Source<?> binding : bindings) {
         notifySourceLoadedBinding(binding, source);
      }
   }

   private static void notifySourceLoadedBinding(EventBinding.Source<?> binding, Source source) {
      if (!binding.isDisposed() && binding.isInstrumentedSource(source)) {
         try {
            ((LoadSourceListener)binding.getElement()).onLoad(new LoadSourceEvent(source));
         } catch (Throwable var3) {
            if (binding.isLanguageBinding()) {
               throw var3;
            }

            ProbeNode.exceptionEventForClientInstrument(binding, "onLoad", var3);
         }
      }
   }

   private static void notifySourceExecutedBindings(EventBinding.Source<?>[] bindings, Source source) {
      for (EventBinding.Source<?> binding : bindings) {
         notifySourceExecutedBinding(binding, source);
      }
   }

   private static void notifySourceExecutedBinding(EventBinding.Source<?> binding, Source source) {
      if (!binding.isDisposed() && binding.isInstrumentedSource(source)) {
         try {
            ((ExecuteSourceListener)binding.getElement()).onExecute(new ExecuteSourceEvent(source));
         } catch (Throwable var3) {
            if (binding.isLanguageBinding()) {
               throw var3;
            }

            ProbeNode.exceptionEventForClientInstrument(binding, "onExecute", var3);
         }
      }
   }

   static void notifySourceSectionLoaded(EventBinding.Source<?> binding, Node node, SourceSection section) {
      if (section != null && !binding.isDisposed()) {
         LoadSourceSectionListener listener = (LoadSourceSectionListener)binding.getElement();

         try {
            listener.onLoad(new LoadSourceSectionEvent(section, node));
         } catch (Throwable var5) {
            if (binding.isLanguageBinding()) {
               throw var5;
            }

            ProbeNode.exceptionEventForClientInstrument(binding, "onLoad", var5);
         }
      }
   }

   private void addInstrumenter(Object key, InstrumentationHandler.AbstractInstrumenter instrumenter) throws AssertionError {
      Object previousKey = this.instrumenterMap.putIfAbsent(key, instrumenter);
      if (previousKey != null) {
         throw new AssertionError("Instrumenter already present.");
      }
   }

   static Collection<EventBinding<?>> filterBindingsForInstrumenter(
      Collection<? extends EventBinding<?>> bindings, InstrumentationHandler.AbstractInstrumenter instrumenter
   ) {
      if (bindings.isEmpty()) {
         return Collections.emptyList();
      } else {
         Collection<EventBinding<?>> newBindings = new ArrayList<>();

         for (EventBinding<?> binding : bindings) {
            if (binding.getInstrumenter() == instrumenter) {
               newBindings.add(binding);
            }
         }

         return newBindings;
      }
   }

   private void insertWrapper(Node instrumentableNode, SourceSection sourceSection) {
      Lock lock = InstrumentAccessor.nodesAccess().getLock(instrumentableNode);

      try {
         lock.lock();
         this.insertWrapperImpl(instrumentableNode, sourceSection);
      } finally {
         lock.unlock();
      }
   }

   private void insertWrapperImpl(Node node, SourceSection sourceSection) {
      Node parent = node.getParent();
      if (parent instanceof InstrumentableNode.WrapperNode) {
         invalidateWrapperImpl((InstrumentableNode.WrapperNode)parent, node);
      } else {
         ProbeNode probe = new ProbeNode(this, sourceSection);
         if (node instanceof InstrumentableNode) {
            InstrumentableNode.WrapperNode wrapper;
            try {
               wrapper = ((InstrumentableNode)node).createWrapper(probe);
            } catch (Exception var7) {
               throw new IllegalStateException("Failed to create wrapper of " + node, var7);
            }

            Node wrapperNode = getWrapperNodeChecked(wrapper, node, parent);
            node.replace(wrapperNode, "Insert instrumentation wrapper node.");

            assert probe.getContext().validEventContextOnWrapperInsert();
         } else {
            throw new AssertionError();
         }
      }
   }

   private static Node getWrapperNodeChecked(Object wrapper, Node node, Node parent) {
      if (wrapper == null) {
         throw new IllegalStateException("No wrapper returned for " + node + " of class " + node.getClass().getName());
      } else if (!(wrapper instanceof Node)) {
         throw new IllegalStateException(
            String.format("Implementation of %s must be a subclass of %s.", wrapper.getClass().getName(), Node.class.getSimpleName())
         );
      } else {
         Node wrapperNode = (Node)wrapper;
         if (wrapperNode.getParent() != null) {
            throw new IllegalStateException(
               String.format(
                  "Instance of provided wrapper %s is already adopted by another parent: %s",
                  wrapper.getClass().getName(),
                  wrapperNode.getParent().getClass().getName()
               )
            );
         } else if (parent == null) {
            throw new IllegalStateException(String.format("Instance of instrumentable node %s is not adopted by a parent.", node.getClass().getName()));
         } else if (!NodeUtil.isReplacementSafe(parent, node, wrapperNode)) {
            throw new IllegalStateException(
               String.format(
                  "WrapperNode implementation %s cannot be safely replaced in parent node class %s.",
                  wrapperNode.getClass().getName(),
                  parent.getClass().getName()
               )
            );
         } else {
            return wrapperNode;
         }
      }
   }

   private <T extends ExecutionEventNodeFactory> EventBinding<T> attachFactory(
      InstrumentationHandler.AbstractInstrumenter instrumenter, SourceSectionFilter filter, SourceSectionFilter inputFilter, T factory
   ) {
      return this.addExecutionBinding(new EventBinding.Execution<>(instrumenter, filter, inputFilter, factory));
   }

   private <T extends ExecutionEventListener> EventBinding<T> attachListener(
      InstrumentationHandler.AbstractInstrumenter instrumenter, SourceSectionFilter filter, SourceSectionFilter inputFilter, T listener
   ) {
      return this.addExecutionBinding(new EventBinding.Execution<>(instrumenter, filter, inputFilter, listener));
   }

   private <T extends LoadSourceListener> EventBinding<T> attachSourceListener(
      InstrumentationHandler.AbstractInstrumenter abstractInstrumenter, SourceSectionFilter filter, T listener, boolean notifyLoaded
   ) {
      return this.addSourceLoadedBinding(new EventBinding.SourceLoaded<>(abstractInstrumenter, filter, null, listener, true, notifyLoaded));
   }

   private <T> EventBinding<T> attachSourceSectionListener(
      InstrumentationHandler.AbstractInstrumenter abstractInstrumenter, SourceSectionFilter filter, T listener, boolean notifyLoaded
   ) {
      return this.addSourceSectionBinding(new EventBinding.SourceSectionLoaded<>(abstractInstrumenter, filter, null, listener, true, notifyLoaded));
   }

   private void visitLoadedSourceSections(
      InstrumentationHandler.AbstractInstrumenter abstractInstrumenter, SourceSectionFilter filter, LoadSourceSectionListener listener
   ) {
      this.visitLoadedSourceSections(new EventBinding.SourceSectionLoaded<>(abstractInstrumenter, filter, null, listener, true, true));
   }

   private <T> EventBinding<T> attachExecuteSourceListener(
      InstrumentationHandler.AbstractInstrumenter abstractInstrumenter, SourceSectionFilter filter, T listener, boolean notifyLoaded
   ) {
      return this.addSourceExecutionBinding(new EventBinding.SourceExecuted<>(abstractInstrumenter, filter, null, listener, true, notifyLoaded));
   }

   private <T extends OutputStream> EventBinding<T> attachOutputConsumer(
      InstrumentationHandler.AbstractInstrumenter instrumenter, T stream, boolean errorOutput
   ) {
      return this.addOutputBinding(new EventBinding<>(instrumenter, stream), errorOutput);
   }

   private <T extends AllocationListener> EventBinding<T> attachAllocationListener(
      InstrumentationHandler.AbstractInstrumenter instrumenter, AllocationEventFilter filter, T listener
   ) {
      return this.addAllocationBinding(new EventBinding.Allocation<>(instrumenter, filter, listener));
   }

   private <T extends ContextsListener> EventBinding<T> attachContextsListener(
      InstrumentationHandler.AbstractInstrumenter instrumenter, T listener, boolean includeActiveContexts
   ) {
      assert listener != null;

      return this.addContextsBinding(new EventBinding<>(instrumenter, listener), includeActiveContexts);
   }

   private <T extends ThreadsListener> EventBinding<T> attachThreadsListener(
      InstrumentationHandler.AbstractInstrumenter instrumenter, T listener, boolean includeStartedThreads
   ) {
      assert listener != null;

      return this.addThreadsBinding(new EventBinding<>(instrumenter, listener), includeStartedThreads);
   }

   private <T extends ThreadsActivationListener> EventBinding<T> attachThreadsActivationListener(
      InstrumentationHandler.AbstractInstrumenter instrumenter, T listener
   ) {
      assert listener != null;

      EventBinding<T> binding = new EventBinding<>(instrumenter, listener);
      if (TRACE) {
         trace("BEGIN: Adding threads activaiton binding %s%n", binding.getElement());
      }

      synchronized (this.threadsActivationBindings) {
         this.threadsActivationBindings.add(binding);
         this.invalidateThreadsActivationListeners();
      }

      if (TRACE) {
         trace("END: Added threads activation binding %s%n", binding.getElement());
      }

      return binding;
   }

   private void invalidateThreadsActivationListeners() {
      assert Thread.holdsLock(this.threadsActivationBindings);

      InstrumentationHandler.StableThreadsActivationListeners stableListeners = this.stableActivationListeners;
      if (stableListeners != null) {
         stableListeners.assumption.invalidate();
         this.stableActivationListeners = null;
      }
   }

   ThreadsActivationListener[] getThreadsActivationListeners() {
      InstrumentationHandler.StableThreadsActivationListeners stableListeners = this.stableActivationListeners;
      if (stableListeners == null || !stableListeners.assumption.isValid()) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         stableListeners = this.updateStableActivationListeners();
      }

      return stableListeners.listeners;
   }

   private InstrumentationHandler.StableThreadsActivationListeners updateStableActivationListeners() {
      synchronized (this.threadsActivationBindings) {
         InstrumentationHandler.StableThreadsActivationListeners stableListeners = this.stableActivationListeners;
         if (stableListeners == null || !stableListeners.assumption.isValid()) {
            List<ThreadsActivationListener> listeners = new ArrayList<>();

            for (EventBinding<? extends ThreadsActivationListener> binding : this.threadsActivationBindings) {
               listeners.add(binding.getElement());
            }

            InstrumentationHandler.StableThreadsActivationListeners oldListeners = stableListeners;
            this.stableActivationListeners = stableListeners = new InstrumentationHandler.StableThreadsActivationListeners(
               listeners.toArray(new ThreadsActivationListener[listeners.size()])
            );
            if (oldListeners != null) {
               oldListeners.assumption.invalidate();
            }
         }

         return stableListeners;
      }
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
      } else {
         Class<?> languageClass = lang.getClass();
         Set<Class<?>> tags = this.cachedProvidedTags.get(languageClass);
         if (tags == null) {
            ProvidedTags languageTags = languageClass.getAnnotation(ProvidedTags.class);
            List<Class<?>> languageTagsList = languageTags != null ? Arrays.asList(languageTags.value()) : Collections.emptyList();
            tags = Collections.unmodifiableSet(new HashSet<>(languageTagsList));
            this.cachedProvidedTags.put(languageClass, tags);
         }

         return tags;
      }
   }

   Set<Class<?>> getProvidedTags(Node root) {
      return this.getProvidedTags(InstrumentAccessor.nodesAccess().getLanguage(root.getRootNode()));
   }

   static boolean isInstrumentableNode(Node node) {
      if (node instanceof InstrumentableNode.WrapperNode) {
         return false;
      } else {
         return node instanceof InstrumentableNode ? ((InstrumentableNode)node).isInstrumentable() : false;
      }
   }

   static void trace(String message, Object... args) {
      PrintStream out = System.out;
      out.printf(message, args);
   }

   private static void visitRoot(
      RootNode root, final Node node, final InstrumentationHandler.Visitor visitor, boolean forceRootBitComputation, boolean firstExecution
   ) {
      visitRoot(root, node, visitor, forceRootBitComputation, firstExecution, false);
   }

   private static void visitRoot(
      RootNode root,
      final Node node,
      final InstrumentationHandler.Visitor visitor,
      boolean forceRootBitComputation,
      boolean firstExecution,
      boolean setExecutedRootNodeBit
   ) {
      if (TRACE) {
         trace("BEGIN: Visit root %s for %s%n", root.toString(), visitor);
      }

      if (!InstrumentAccessor.runtimeAccess().isOSRRootNode(root)) {
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
                     trace("BEGIN: Traverse root %s for %s%n", root.toString(), visitor);
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
                     trace("END: Traverse root %s for %s%n", root.toString(), visitor);
                  }
               }

               if (setExecutedRootNodeBit && RootNodeBits.wasNotExecuted(visitor.rootBits)) {
                  visitor.rootBits = RootNodeBits.setExecuted(visitor.rootBits);
                  RootNodeBits.set(root, visitor.rootBits);
               }
            } finally {
               lock.unlock();
            }
         } finally {
            visitor.postVisit();
         }

         if (TRACE) {
            trace("END: Visited root %s for %s%n", root.toString(), visitor);
         }
      }
   }

   static void removeWrapper(ProbeNode node) {
      if (TRACE) {
         trace("Remove wrapper for %s%n", node.getContext().getInstrumentedSourceSection());
      }

      InstrumentableNode.WrapperNode wrapperNode = node.findWrapper();
      ((Node)wrapperNode).replace(wrapperNode.getDelegateNode());
   }

   private static void invalidateWrapper(Node node) {
      Node parent = node.getParent();
      if (parent instanceof InstrumentableNode.WrapperNode) {
         invalidateWrapperImpl((InstrumentableNode.WrapperNode)parent, node);
      }
   }

   private static void invalidateWrapperImpl(InstrumentableNode.WrapperNode parent, Node node) {
      ProbeNode probeNode = parent.getProbeNode();
      if (TRACE) {
         SourceSection section = probeNode.getContext().getInstrumentedSourceSection();
         trace("Invalidate wrapper for %s, section %s %n", node, section);
      }

      if (probeNode != null) {
         probeNode.invalidate();
      }
   }

   static boolean hasTagImpl(Set<Class<?>> providedTags, Node node, Class<?> tag) {
      if (providedTags.contains(tag)) {
         return node instanceof InstrumentableNode ? ((InstrumentableNode)node).hasTag((Class<? extends Tag>)tag) : false;
      } else {
         return false;
      }
   }

   <T> T lookup(Object key, Class<T> type) {
      InstrumentationHandler.AbstractInstrumenter value = this.instrumenterMap.get(key);
      return value == null ? null : value.lookup(this, type);
   }

   AllocationReporter getAllocationReporter(LanguageInfo info) {
      AllocationReporter allocationReporter = new AllocationReporter(info);
      this.allocationReporters.add(allocationReporter);

      for (EventBinding.Allocation<? extends AllocationListener> binding : this.allocationBindings) {
         if (binding.getAllocationFilter().contains(info)) {
            allocationReporter.addListener(binding.getElement());
         }
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
      return parent instanceof InstrumentableNode.WrapperNode ? (InstrumentableNode.WrapperNode)parent : null;
   }

   private static void clearRetiredNodeReference(Node node) {
      InstrumentableNode.WrapperNode wrapperNode = getWrapperNode(node);
      if (wrapperNode != null) {
         wrapperNode.getProbeNode().clearRetiredNodeReference();
         invalidateWrapperImpl(wrapperNode, node);
      }
   }

   private static void traceFilterCheck(String result, Node instrumentableNode, SourceSection sourceSection) {
      trace("  Filter %4s node:%s section:%s %n", result, instrumentableNode, sourceSection);
   }

   private abstract static class AbstractAsyncCollection<T, R> extends AbstractCollection<R> {
      private volatile AtomicReferenceArray<T> values;
      private int nextInsertionIndex;
      protected final int initialCapacity;

      AbstractAsyncCollection(int initialCapacity) {
         if (initialCapacity <= 0) {
            throw new IllegalArgumentException("Invalid initial capacity " + initialCapacity);
         } else {
            this.values = new AtomicReferenceArray<>(initialCapacity);
            this.initialCapacity = initialCapacity;
         }
      }

      @Override
      public final synchronized void clear() {
         this.values = new AtomicReferenceArray<>(this.initialCapacity);
         this.nextInsertionIndex = 0;
      }

      @Override
      public final synchronized boolean add(R reference) {
         T wrappedElement = this.wrap(reference);
         if (wrappedElement == null) {
            throw new NullPointerException();
         } else {
            if (this.nextInsertionIndex >= this.values.length()) {
               this.compact();
            }

            this.values.set(this.nextInsertionIndex++, wrappedElement);
            return true;
         }
      }

      @Override
      public int size() {
         throw new UnsupportedOperationException();
      }

      @Override
      public final boolean isEmpty() {
         return this.values.get(0) == null;
      }

      protected abstract T wrap(R element);

      protected abstract R unwrap(T element);

      private void compact() {
         AtomicReferenceArray<T> localValues = this.values;
         int liveElements = 0;

         for (int i = 0; i < localValues.length(); i++) {
            T ref = localValues.get(i);
            if (ref == null) {
               break;
            }

            if (this.unwrap(ref) != null) {
               liveElements++;
            }
         }

         AtomicReferenceArray<T> newValues = new AtomicReferenceArray<>(Math.max(liveElements * 2, this.initialCapacity));
         int index = 0;

         for (int i = 0; i < localValues.length(); i++) {
            T refx = localValues.get(i);
            if (refx == null) {
               break;
            }

            if (this.unwrap(refx) != null) {
               newValues.set(index++, refx);
            }
         }

         this.nextInsertionIndex = index;
         this.values = newValues;
      }

      @Override
      public Iterator<R> iterator() {
         return new Iterator<R>() {
            private final AtomicReferenceArray<T> values;
            private int index;
            private R queuedNext;

            {
               this.values = AbstractAsyncCollection.this.values;
            }

            @Override
            public boolean hasNext() {
               R next = this.queuedNext;
               if (next == null) {
                  next = (R)this.queueNext();
                  this.queuedNext = next;
               }

               return next != null;
            }

            private R queueNext() {
               int localIndex = this.index;
               AtomicReferenceArray<T> array = this.values;

               while (localIndex < array.length()) {
                  T localValue = array.get(localIndex);
                  if (localValue == null) {
                     return null;
                  }

                  localIndex++;
                  R alive = (R)AbstractAsyncCollection.this.unwrap(localValue);
                  if (alive != null) {
                     this.index = localIndex;
                     return alive;
                  }
               }

               return null;
            }

            @Override
            public R next() {
               R next = this.queuedNext;
               if (next == null) {
                  next = (R)this.queueNext();
                  if (next == null) {
                     throw new NoSuchElementException();
                  }
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

   abstract class AbstractInstrumenter extends Instrumenter {
      abstract void doFinalize();

      abstract void dispose();

      abstract <T> T lookup(InstrumentationHandler handler, Class<T> type);

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

      abstract boolean isInstrumentableRoot(RootNode rootNode);

      abstract boolean isInstrumentableSource(Source source);

      final Set<Class<?>> queryTagsImpl(Node node, LanguageInfo onlyLanguage) {
         Objects.requireNonNull(node);
         if (!InstrumentationHandler.isInstrumentableNode(node)) {
            return Collections.emptySet();
         } else {
            RootNode root = node.getRootNode();
            if (root == null) {
               return Collections.emptySet();
            } else if (onlyLanguage != null && root.getLanguageInfo() != onlyLanguage) {
               throw new IllegalArgumentException("The language instrumenter cannot query tags of nodes of other languages.");
            } else {
               Set<Class<?>> providedTags = InstrumentationHandler.this.getProvidedTags(root);
               if (providedTags.isEmpty()) {
                  return Collections.emptySet();
               } else {
                  Set<Class<?>> tags = new HashSet<>();

                  for (Class<?> providedTag : providedTags) {
                     if (InstrumentationHandler.hasTagImpl(providedTags, node, providedTag)) {
                        tags.add(providedTag);
                     }
                  }

                  return Collections.unmodifiableSet(tags);
               }
            }
         }
      }

      @Override
      public final ExecutionEventNode lookupExecutionEventNode(Node node, EventBinding<?> binding) {
         if (!InstrumentationHandler.isInstrumentableNode(node)) {
            return null;
         } else {
            Node p = node.getParent();
            if (p instanceof InstrumentableNode.WrapperNode) {
               InstrumentableNode.WrapperNode w = (InstrumentableNode.WrapperNode)p;
               return w.getProbeNode().lookupExecutionEventNode(binding);
            } else {
               return null;
            }
         }
      }

      @Override
      public <T extends ExecutionEventNodeFactory> EventBinding<T> attachExecutionEventFactory(
         SourceSectionFilter filter, SourceSectionFilter inputFilter, T factory
      ) {
         this.verifyFilter(filter);
         return InstrumentationHandler.this.attachFactory(this, filter, inputFilter, factory);
      }

      @Override
      public <T extends ExecutionEventListener> EventBinding<T> attachExecutionEventListener(
         SourceSectionFilter filter, SourceSectionFilter inputFilter, T listener
      ) {
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
         return new EventBinding.SourceLoaded<>(this, sectionsFilter, null, listener, false, notifyLoaded);
      }

      @Override
      public <T extends ExecuteSourceListener> EventBinding<T> createExecuteSourceBinding(SourceFilter filter, T listener, boolean notifyLoaded) {
         SourceSectionFilter sectionsFilter = SourceSectionFilter.newBuilder().sourceFilter(filter).build();
         return new EventBinding.SourceExecuted<>(this, sectionsFilter, null, listener, false, notifyLoaded);
      }

      @Override
      public <T extends LoadSourceSectionListener> EventBinding<T> createLoadSourceSectionBinding(SourceSectionFilter filter, T listener, boolean notifyLoaded) {
         this.verifyFilter(filter);
         return new EventBinding.SourceSectionLoaded<>(this, filter, null, listener, false, notifyLoaded);
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
            throw new IllegalArgumentException(
               String.format(
                  "The attached filter %s uses filters that require source sections to verifiy. Source listeners can only use filter critera based on Source objects like mimeTypeIs or sourceIs.",
                  filter
               )
            );
         }
      }

      abstract void verifyFilter(SourceSectionFilter filter);
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

   static class CopyOnWriteList<E> extends AbstractCollection<E> {
      private volatile E[] array;

      CopyOnWriteList(E[] array) {
         this.array = array;
      }

      @Override
      public synchronized boolean add(E e) {
         if (e == null) {
            throw new NullPointerException();
         } else {
            E[] oldArray = this.getArray();
            int len = oldArray.length;
            E[] newArray = Arrays.copyOf(oldArray, len + 1);
            newArray[len] = e;
            this.array = newArray;
            return true;
         }
      }

      @Override
      public synchronized void clear() {
         E[] oldArray = this.getArray();
         E[] newArray = Arrays.copyOf(oldArray, 0);
         this.array = newArray;
      }

      @Override
      public Iterator<E> iterator() {
         return new Iterator<E>() {
            private final E[] snapshot = (E[])CopyOnWriteList.this.getArray();
            private int cursor = 0;

            @Override
            public boolean hasNext() {
               return this.cursor < this.snapshot.length;
            }

            @Override
            public E next() {
               if (!this.hasNext()) {
                  throw new NoSuchElementException();
               } else {
                  return this.snapshot[this.cursor++];
               }
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

         for (int i = 0; i < len; i++) {
            if (oldArray[i].equals(o)) {
               index = i;
               break;
            }
         }

         if (index >= 0) {
            E[] newArray = Arrays.copyOf(oldArray, len - 1);
            System.arraycopy(oldArray, index + 1, newArray, index, len - index - 1);
            this.array = newArray;
            return true;
         } else {
            return false;
         }
      }

      @Override
      public synchronized boolean removeAll(Collection<?> c) {
         E[] oldArray = this.getArray();
         int len = oldArray.length;
         if (len != 0) {
            int newlen = 0;
            E[] temp = Arrays.copyOf(oldArray, len);

            for (int i = 0; i < len; i++) {
               E element = oldArray[i];
               if (!c.contains(element)) {
                  temp[newlen++] = element;
               }
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

   private static class DisposeWrapperOperation extends InstrumentationHandler.VisitOperation {
      DisposeWrapperOperation(InstrumentationHandler.VisitOperation.Scope scope, EventBinding.Source<?> binding) {
         super(scope, binding);
      }

      DisposeWrapperOperation(InstrumentationHandler.VisitOperation.Scope scope, InstrumentationHandler.CopyOnWriteList<EventBinding.Source<?>> bindings) {
         super(scope, bindings.getArray(), false);
      }

      @Override
      protected void perform(EventBinding.Source<?> binding, Node node, SourceSection section, boolean executedRoot) {
         InstrumentationHandler.invalidateWrapper(node);
      }
   }

   final class EngineInstrumenter extends InstrumentationHandler.AbstractInstrumenter {
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

   private static final class EventBindingList<EB extends EventBinding<?>> extends InstrumentationHandler.AbstractAsyncCollection<EB, EB> {
      EventBindingList(int initialCapacity) {
         super(initialCapacity);
      }

      protected EB wrap(EB element) {
         return element;
      }

      protected EB unwrap(EB element) {
         return element.isDisposed() ? null : element;
      }
   }

   private static class FindSourcesOperation extends InstrumentationHandler.VisitOperation {
      private final ThreadLocal<Map<Source, Void>> threadLocalNewSources;
      private final boolean dontNotifyBindings;
      private final SourceInstrumentationHandler sourceInstrumentationHandler;
      private final boolean performOnlyOnExecutedAST;
      private Map<Source, Void> newSources;
      private boolean updateGlobalSourceList;

      FindSourcesOperation(
         InstrumentationHandler.VisitOperation.Scope scope,
         ThreadLocal<Map<Source, Void>> threadLocalNewSources,
         SourceInstrumentationHandler sourceInstrumentationHandler,
         boolean dontNotifyBindings,
         boolean performOnlyOnExecutedAST
      ) {
         super(scope, sourceInstrumentationHandler.getBindingsArray(), false, true);
         this.threadLocalNewSources = threadLocalNewSources;
         this.sourceInstrumentationHandler = sourceInstrumentationHandler;
         this.dontNotifyBindings = dontNotifyBindings;
         this.performOnlyOnExecutedAST = performOnlyOnExecutedAST;
      }

      @Override
      protected boolean shouldVisit(Set<Class<?>> providedTags, RootNode rootNode, SourceSection rootSourceSection, int rootNodeBits) {
         return this.bindingsAtConstructionTime.length > 0
            && !RootNodeBits.isNoSourceSection(rootNodeBits)
            && (!RootNodeBits.isSameSource(rootNodeBits) || rootSourceSection == null);
      }

      @Override
      protected void preVisit(RootNode root, SourceSection rootSourceSection, boolean executedRoot, Node visitRoot) {
         Map<Source, Void> localNewSources = this.threadLocalNewSources.get();
         if (localNewSources == null) {
            localNewSources = new LinkedHashMap<>();
            this.threadLocalNewSources.set(localNewSources);
            this.updateGlobalSourceList = true;
         } else {
            this.updateGlobalSourceList = false;
         }

         this.newSources = localNewSources;
         if (rootSourceSection != null
            && (!this.performOnlyOnExecutedAST || executedRoot)
            && this.scope != InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED
            && root == visitRoot) {
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

            SourceInstrumentationHandler.SourcesNotificationQueue notifications = this.sourceInstrumentationHandler
               .addNewSources(this.newSources, !this.dontNotifyBindings);
            if (notifications != null) {
               notifications.process();
            }
         }
      }
   }

   private class InsertWrapperOperation extends InstrumentationHandler.VisitOperation {
      InsertWrapperOperation(InstrumentationHandler.VisitOperation.Scope scope, EventBinding.Source<?> binding) {
         super(scope, binding);
      }

      InsertWrapperOperation(InstrumentationHandler.VisitOperation.Scope scope, InstrumentationHandler.CopyOnWriteList<EventBinding.Source<?>> bindings) {
         super(scope, bindings.getArray(), false);
      }

      @Override
      protected void perform(EventBinding.Source<?> binding, Node node, SourceSection section, boolean executedRoot) {
         InstrumentationHandler.this.insertWrapper(node, section);
      }
   }

   final class InstrumentClientInstrumenter extends InstrumentationHandler.AbstractInstrumenter {
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
         if (InstrumentationHandler.TRACE) {
            InstrumentationHandler.trace("Create instrument %s class %s %n", this.instrument, this.instrumentClassName);
         }

         this.services = this.env.onCreate(this.instrument);
         if (expectedServices != null && !TruffleOptions.AOT) {
            this.checkServices(expectedServices);
         }

         if (InstrumentationHandler.TRACE) {
            InstrumentationHandler.trace("Created instrument %s class %s %n", this.instrument, this.instrumentClassName);
         }
      }

      private boolean checkServices(String[] expectedServices) {
         label24:
         for (String name : expectedServices) {
            for (Object obj : this.services) {
               if (this.findType(name, obj.getClass())) {
                  continue label24;
               }
            }

            InstrumentationHandler.failInstrumentInitialization(
               this.env, String.format("%s declares service %s but doesn't register it", this.instrumentClassName, name), null
            );
         }

         return true;
      }

      private boolean findType(String name, Class<?> type) {
         if (type == null) {
            return false;
         } else if (!type.getName().equals(name) && (type.getCanonicalName() == null || !type.getCanonicalName().equals(name))) {
            if (this.findType(name, type.getSuperclass())) {
               return true;
            } else {
               for (Class<?> inter : type.getInterfaces()) {
                  if (this.findType(name, inter)) {
                     return true;
                  }
               }

               return false;
            }
         } else {
            return true;
         }
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
            for (Object service : this.services) {
               if (type.isInstance(service)) {
                  return type.cast(service);
               }
            }
         }

         return null;
      }
   }

   final class LanguageClientInstrumenter<T> extends InstrumentationHandler.AbstractInstrumenter {
      private final LanguageInfo languageInfo;
      private final TruffleLanguage<?> language;

      LanguageClientInstrumenter(TruffleLanguage<?> language) {
         this.language = language;
         this.languageInfo = InstrumentAccessor.langAccess().getLanguageInfo(language);
      }

      @Override
      boolean isInstrumentableSource(Source source) {
         String mimeType = source.getMimeType();
         return mimeType == null ? false : this.languageInfo.getMimeTypes().contains(mimeType);
      }

      @Override
      boolean isInstrumentableRoot(RootNode node) {
         LanguageInfo langInfo = node.getLanguageInfo();
         return langInfo == null ? false : langInfo == this.languageInfo;
      }

      @Override
      public Set<Class<?>> queryTags(Node node) {
         return this.queryTagsImpl(node, this.languageInfo);
      }

      @Override
      void verifyFilter(SourceSectionFilter filter) {
         Set<Class<?>> providedTags = InstrumentationHandler.this.getProvidedTags(this.language);
         Set<Class<?>> referencedTags = filter.getReferencedTags();
         if (!providedTags.containsAll(referencedTags)) {
            Set<Class<?>> missingTags = new HashSet<>(referencedTags);
            missingTags.removeAll(providedTags);
            Set<Class<?>> allTags = new LinkedHashSet<>(providedTags);
            allTags.addAll(missingTags);
            StringBuilder builder = new StringBuilder("{");
            String sep = "";

            for (Class<?> tag : allTags) {
               builder.append(sep);
               builder.append(tag.getSimpleName());
               sep = ", ";
            }

            builder.append("}");
            throw new IllegalArgumentException(
               String.format(
                  "The attached filter %s references the following tags %s which are not declared as provided by the language. To fix this annotate the language class %s with @%s(%s).",
                  filter,
                  missingTags,
                  this.language.getClass().getName(),
                  ProvidedTags.class.getSimpleName(),
                  builder
               )
            );
         }
      }

      @Override
      public <S extends ContextsListener> EventBinding<S> attachContextsListener(S listener, boolean includeActiveContexts) {
         throw new UnsupportedOperationException("Not supported in language instrumenter.");
      }

      @Override
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

      @Override
      <S> S lookup(InstrumentationHandler handler, Class<S> type) {
         return null;
      }
   }

   private class NotifyLoadedOperation extends InstrumentationHandler.VisitOperation {
      List<InstrumentationHandler.BindingLoadSourceSectionEvent> sourceSectionLoadedList;
      boolean notifyBindings;

      NotifyLoadedOperation(InstrumentationHandler.VisitOperation.Scope scope, EventBinding.Source<?> binding) {
         super(scope, binding);
      }

      NotifyLoadedOperation(InstrumentationHandler.VisitOperation.Scope scope, InstrumentationHandler.CopyOnWriteList<EventBinding.Source<?>> bindings) {
         super(scope, bindings.getArray(), true);
      }

      @Override
      protected void preVisit(RootNode root, SourceSection rootSourceSection, boolean executedRoot, Node visitRoot) {
         List<InstrumentationHandler.BindingLoadSourceSectionEvent> localSourceSectionLoadedList = InstrumentationHandler.this.threadLocalSourceSectionLoadedList
            .get();
         if (localSourceSectionLoadedList == null) {
            localSourceSectionLoadedList = new ArrayList<>();
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
            this.sourceSectionLoadedList.add(new InstrumentationHandler.BindingLoadSourceSectionEvent(binding, node, section));
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
            for (InstrumentationHandler.BindingLoadSourceSectionEvent loadEvent : this.sourceSectionLoadedList) {
               InstrumentationHandler.notifySourceSectionLoaded(loadEvent.binding, loadEvent.node, loadEvent.sourceSection);
            }
         }
      }
   }

   static final class StableThreadsActivationListeners {
      final Assumption assumption = Truffle.getRuntime().createAssumption("Activation listeners stable.");
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      final ThreadsActivationListener[] listeners;

      StableThreadsActivationListeners(ThreadsActivationListener[] listeners) {
         this.listeners = listeners;
      }
   }

   private abstract static class VisitOperation {
      protected final InstrumentationHandler.VisitOperation.Scope scope;
      protected EventBinding.Source<?>[] bindingsAtConstructionTime;
      private final boolean singleBindingOperation;
      private final boolean performForEachBinding;
      private final boolean alwaysPerform;

      VisitOperation(InstrumentationHandler.VisitOperation.Scope scope, EventBinding.Source<?> binding) {
         this(scope, new EventBinding.Source[]{binding}, true, true, false);
      }

      VisitOperation(InstrumentationHandler.VisitOperation.Scope scope, EventBinding.Source<?>[] bindingsArray, boolean performForEachBinding) {
         this(scope, bindingsArray, false, performForEachBinding, false);
      }

      VisitOperation(
         InstrumentationHandler.VisitOperation.Scope scope, EventBinding.Source<?>[] bindingsArray, boolean performForEachBinding, boolean alwaysPerform
      ) {
         this(scope, bindingsArray, false, performForEachBinding, alwaysPerform);
      }

      VisitOperation(
         InstrumentationHandler.VisitOperation.Scope scope,
         EventBinding.Source<?>[] bindingsArray,
         boolean singleBindingOperation,
         boolean performForEachBinding,
         boolean alwaysPerform
      ) {
         this.scope = scope;
         this.bindingsAtConstructionTime = bindingsArray;
         this.singleBindingOperation = singleBindingOperation;
         this.performForEachBinding = performForEachBinding;
         this.alwaysPerform = alwaysPerform;
      }

      protected abstract void perform(EventBinding.Source<?> binding, Node node, SourceSection section, boolean executedRoot);

      protected boolean shouldVisit(Set<Class<?>> providedTags, RootNode rootNode, SourceSection rootSourceSection, int rootNodeBits) {
         for (EventBinding.Source<?> binding : this.bindingsAtConstructionTime) {
            if (binding.isInstrumentedRoot(providedTags, rootNode, rootSourceSection, rootNodeBits)) {
               return true;
            }
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

   private final class Visitor implements NodeVisitor {
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
      private final List<InstrumentationHandler.VisitOperation> operations;
      private final boolean singleBindingOptimization;
      private boolean singleBindingOptimizationPass;
      private boolean onlyAlwaysPerformOperationsActive;
      private Node savedParent;
      private SourceSection savedParentSourceSection;

      Visitor(boolean shouldMaterializeSyntaxNodes, List<InstrumentationHandler.VisitOperation> operations) {
         this.shouldMaterializeSyntaxNodes = shouldMaterializeSyntaxNodes;
         this.operations = operations;
         int singleBindingOperations = 0;
         int multiBindingOriginalTreeOperations = 0;

         for (InstrumentationHandler.VisitOperation operation : operations) {
            if (!operation.alwaysPerform) {
               if (operation.singleBindingOperation) {
                  singleBindingOperations++;
               } else if (operation.scope == InstrumentationHandler.VisitOperation.Scope.ALL
                  || operation.scope == InstrumentationHandler.VisitOperation.Scope.ONLY_ORIGINAL) {
                  multiBindingOriginalTreeOperations++;
               }
            }
         }

         this.singleBindingOptimization = operations.size() == 1 && singleBindingOperations == 1
            || singleBindingOperations == 1 && multiBindingOriginalTreeOperations == 0;
         Set<Class<?>> compoundTags = null;

         label49:
         for (InstrumentationHandler.VisitOperation operationx : operations) {
            if (!operationx.alwaysPerform) {
               for (EventBinding.Source<?> sourceBinding : operationx.bindingsAtConstructionTime) {
                  Set<Class<?>> limitedTags = sourceBinding.getLimitedTags();
                  if (limitedTags == null) {
                     compoundTags = null;
                     break label49;
                  }

                  if (compoundTags == null) {
                     compoundTags = new HashSet<>();
                  }

                  compoundTags.addAll(limitedTags);
               }
            }
         }

         this.materializeLimitedTags = compoundTags != null ? Collections.unmodifiableSet(compoundTags) : null;
      }

      boolean shouldVisit() {
         if (this.operations.isEmpty()) {
            return false;
         } else {
            RootNode localRoot = this.root;
            SourceSection localRootSourceSection = this.rootSourceSection;
            int localRootBits = this.rootBits;

            for (InstrumentationHandler.VisitOperation operation : this.operations) {
               if (!operation.alwaysPerform && (!this.singleBindingOptimization || operation.singleBindingOperation)) {
                  boolean pass = operation.shouldVisit(this.providedTags, localRoot, localRootSourceSection, localRootBits);
                  if (pass) {
                     if (this.singleBindingOptimization) {
                        this.singleBindingOptimizationPass = true;
                     }

                     return true;
                  }
               }
            }

            this.onlyAlwaysPerformOperationsActive = true;

            for (InstrumentationHandler.VisitOperation operationx : this.operations) {
               if (operationx.alwaysPerform
                  && operationx.scope != InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED
                  && operationx.shouldVisit(this.providedTags, localRoot, localRootSourceSection, localRootBits)) {
                  return true;
               }
            }

            this.onlyAlwaysPerformOperationsActive = false;
            return false;
         }
      }

      private void computeRootBits(SourceSection sourceSection) {
         int bits = this.computingRootNodeBits;
         if (!RootNodeBits.isUninitialized(bits)) {
            if (sourceSection != null) {
               if (RootNodeBits.isNoSourceSection(bits)) {
                  bits = RootNodeBits.setHasSourceSection(bits);
               }

               if (this.rootSourceSection != null) {
                  if (RootNodeBits.isSourceSectionsHierachical(bits)
                     && (
                        sourceSection.getCharIndex() < this.rootSourceSection.getCharIndex()
                           || sourceSection.getCharEndIndex() > this.rootSourceSection.getCharEndIndex()
                     )) {
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
      }

      @Override
      public boolean visit(Node originalNode) {
         Node node = originalNode;
         SourceSection sourceSection = originalNode.getSourceSection();
         boolean instrumentable = InstrumentationHandler.isInstrumentableNode(originalNode);
         Node previousParent = null;
         SourceSection previousParentSourceSection = null;
         if (instrumentable) {
            this.computeRootBits(sourceSection);
            boolean hasRetiredNodes = this.visitPreviouslyRetiredNodes(originalNode);
            if (!this.visitingRetiredNodes) {
               node = this.materialize(originalNode, sourceSection, originalNode);
               if (this.saveAndVisitNewlyRetiredNode(node, sourceSection, originalNode)) {
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
         } finally {
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

         assert !this.visitingMaterialized || materializedNode == originalNode : "New tree should be fully materialized!";

         assert materializedNode == this.materializeSyntaxNodes(materializedNode, sourceSection) : "Node must not be materialized multiple times for the same set of tags!";

         return materializedNode;
      }

      private boolean saveAndVisitNewlyRetiredNode(Node node, SourceSection sourceSection, Node originalNode) {
         if (this.firstExecution || node == originalNode) {
            return false;
         } else {
            assert this.materializeTags != null : "Materialize tags must not be null when materialization happened.";

            InstrumentableNode.WrapperNode wrapperNode = InstrumentationHandler.getWrapperNode(node);
            if (wrapperNode == null) {
               InstrumentationHandler.this.insertWrapper(node, sourceSection);
            }

            wrapperNode = InstrumentationHandler.getWrapperNode(node);

            assert wrapperNode != null : "Node must have an instrumentation wrapper at this point!";

            wrapperNode.getProbeNode().setRetiredNode(originalNode, this.materializeTags);
            this.visitRetiredNodes(originalNode);
            return true;
         }
      }

      private boolean visitPreviouslyRetiredNodes(Node node) {
         if (!this.firstExecution) {
            InstrumentableNode.WrapperNode wrapperNode = InstrumentationHandler.getWrapperNode(node);
            ProbeNode.RetiredNodeReference retiredNodeReference = wrapperNode != null ? wrapperNode.getProbeNode().getRetiredNodeReference() : null;
            if (retiredNodeReference != null) {
               boolean hasRetiredNodes;
               for (hasRetiredNodes = false; retiredNodeReference != null; retiredNodeReference = retiredNodeReference.next) {
                  Node nodeRefNode = retiredNodeReference.getNode();
                  if (nodeRefNode != null) {
                     hasRetiredNodes = true;
                     this.visitRetiredNodes(nodeRefNode);
                  }
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
         } finally {
            this.visitingRetiredNodes = wasVisitingRetiredNodes;
         }
      }

      private Node materializeSyntaxNodes(Node instrumentableNode, SourceSection sourceSection) {
         if (!this.shouldMaterializeSyntaxNodes) {
            return instrumentableNode;
         } else {
            if (instrumentableNode instanceof InstrumentableNode) {
               assert this.materializeTags != null : "Materialize tags must not be null before materialization.";

               var currentNode = (Node & InstrumentableNode)instrumentableNode;

               assert currentNode.isInstrumentable();

               InstrumentableNode materializedNode = currentNode.materializeInstrumentableNodes(this.materializeTags);
               if (currentNode != materializedNode) {
                  if (!(materializedNode instanceof Node)) {
                     throw new IllegalStateException("The returned materialized syntax node is not a Truffle Node.");
                  }

                  if (((Node)materializedNode).getParent() != null) {
                     throw new IllegalStateException("The returned materialized syntax node is already adopted.");
                  }

                  SourceSection newSourceSection = ((Node)materializedNode).getSourceSection();
                  if (!Objects.equals(sourceSection, newSourceSection)) {
                     throw new IllegalStateException(
                        String.format(
                           "The source section of the materialized syntax node must match the source section of the original node. %s != %s.",
                           sourceSection,
                           newSourceSection
                        )
                     );
                  }

                  Node currentParent = ((Node)currentNode).getParent();
                  if (currentParent instanceof InstrumentableNode.WrapperNode
                     && !NodeUtil.isReplacementSafe(currentParent, instrumentableNode, (Node)materializedNode)) {
                     ProbeNode probe = ((InstrumentableNode.WrapperNode)currentParent).getProbeNode();
                     InstrumentableNode.WrapperNode wrapper = materializedNode.createWrapper(probe);
                     Node wrapperNode = InstrumentationHandler.getWrapperNodeChecked(wrapper, (Node)materializedNode, currentParent.getParent());
                     currentParent.replace(wrapperNode, "Insert instrumentation wrapper node.");
                     return (Node)materializedNode;
                  }

                  return ((Node)currentNode).replace((Node)materializedNode);
               }
            }

            return instrumentableNode;
         }
      }

      void preVisit(RootNode r, Node visitRoot, boolean firstExec) {
         this.firstExecution = firstExec;
         this.root = r;
         this.providedTags = InstrumentationHandler.this.getProvidedTags(r);
         this.rootSourceSection = r.getSourceSection();
         this.materializeTags = this.materializeLimitedTags == null ? this.providedTags : this.materializeLimitedTags;

         for (InstrumentationHandler.VisitOperation operation : this.operations) {
            operation.preVisit(r, this.rootSourceSection, this.setExecutedRootNodeBit || RootNodeBits.wasExecuted(this.rootBits), visitRoot);
         }
      }

      void postVisit() {
         for (InstrumentationHandler.VisitOperation operation : this.operations) {
            operation.postVisitCleanup();
         }

         for (InstrumentationHandler.VisitOperation operation : this.operations) {
            operation.postVisitNotifications();
         }
      }

      boolean shouldPerformForBinding(
         InstrumentationHandler.VisitOperation operation,
         EventBinding.Source<?> binding,
         Node parentInstrumentable,
         SourceSection parentSourceSection,
         Node instrumentableNode,
         SourceSection sourceSection
      ) {
         if (this.singleBindingOptimization && operation.singleBindingOperation) {
            return !this.singleBindingOptimizationPass
               ? false
               : binding.isInstrumentedLeaf(this.providedTags, instrumentableNode, sourceSection)
                  || binding.isChildInstrumentedLeaf(this.providedTags, this.root, parentInstrumentable, parentSourceSection, instrumentableNode, sourceSection);
         } else {
            return binding.isInstrumentedFull(this.providedTags, this.root, instrumentableNode, sourceSection)
               || binding.isChildInstrumentedFull(this.providedTags, this.root, parentInstrumentable, parentSourceSection, instrumentableNode, sourceSection);
         }
      }

      void visitInstrumentable(Node parentInstrumentable, SourceSection parentSourceSection, Node instrumentableNode, SourceSection sourceSection) {
         for (InstrumentationHandler.VisitOperation operation : this.operations) {
            if (operation.scope == InstrumentationHandler.VisitOperation.Scope.ALL
               || !this.visitingMaterialized && operation.scope == InstrumentationHandler.VisitOperation.Scope.ONLY_ORIGINAL
               || this.visitingMaterialized && operation.scope == InstrumentationHandler.VisitOperation.Scope.ONLY_MATERIALIZED) {
               if (!operation.alwaysPerform) {
                  for (EventBinding.Source<?> binding : operation.bindingsAtConstructionTime) {
                     if (this.shouldPerformForBinding(operation, binding, parentInstrumentable, parentSourceSection, instrumentableNode, sourceSection)) {
                        assert !this.onlyAlwaysPerformOperationsActive : "No operation that depends on bindings should be performed here!";

                        if (InstrumentationHandler.TRACE) {
                           InstrumentationHandler.traceFilterCheck("hit", instrumentableNode, sourceSection);
                        }

                        operation.perform(binding, instrumentableNode, sourceSection, this.setExecutedRootNodeBit || RootNodeBits.wasExecuted(this.rootBits));
                        if (!operation.performForEachBinding) {
                           break;
                        }
                     } else if (InstrumentationHandler.TRACE) {
                        InstrumentationHandler.traceFilterCheck("miss", instrumentableNode, sourceSection);
                     }
                  }
               } else {
                  if (InstrumentationHandler.TRACE) {
                     InstrumentationHandler.traceFilterCheck("hit", instrumentableNode, sourceSection);
                  }

                  operation.perform(null, instrumentableNode, sourceSection, this.setExecutedRootNodeBit || RootNodeBits.wasExecuted(this.rootBits));
               }
            }
         }
      }
   }

   private class VisitorBuilder {
      List<InstrumentationHandler.VisitOperation> operations = new ArrayList<>();
      boolean shouldMaterializeSyntaxNodes;
      private boolean hasFindSourcesOperation;
      private boolean hasFindSourcesExecutedOperation;

      InstrumentationHandler.VisitorBuilder addNotifyLoadedOperationForAllBindings(InstrumentationHandler.VisitOperation.Scope scope) {
         if (!InstrumentationHandler.this.sourceSectionBindings.isEmpty()) {
            this.operations.add(InstrumentationHandler.this.new NotifyLoadedOperation(scope, InstrumentationHandler.this.sourceSectionBindings));
            this.shouldMaterializeSyntaxNodes = true;
         }

         return this;
      }

      InstrumentationHandler.VisitorBuilder addNotifyLoadedOperationForBinding(
         InstrumentationHandler.VisitOperation.Scope scope, EventBinding.Source<?> binding
      ) {
         this.operations.add(InstrumentationHandler.this.new NotifyLoadedOperation(scope, binding));
         this.shouldMaterializeSyntaxNodes = true;
         return this;
      }

      InstrumentationHandler.VisitorBuilder addFindSourcesOperation(InstrumentationHandler.VisitOperation.Scope scope) {
         return this.addFindSourcesOperation(scope, false);
      }

      InstrumentationHandler.VisitorBuilder addFindSourcesOperation(InstrumentationHandler.VisitOperation.Scope scope, boolean dontNotifyBindings) {
         if (this.hasFindSourcesOperation) {
            throw new IllegalStateException("Visitor can have at most one find sources operation!");
         } else {
            this.operations
               .add(
                  new InstrumentationHandler.FindSourcesOperation(
                     scope, InstrumentationHandler.this.threadLocalNewSourcesLoaded, InstrumentationHandler.this.sourcesLoaded, dontNotifyBindings, false
                  )
               );
            this.hasFindSourcesOperation = true;
            return this;
         }
      }

      InstrumentationHandler.VisitorBuilder addFindSourcesExecutedOperation(InstrumentationHandler.VisitOperation.Scope scope) {
         return this.addFindSourcesExecutedOperation(scope, false);
      }

      InstrumentationHandler.VisitorBuilder addFindSourcesExecutedOperation(InstrumentationHandler.VisitOperation.Scope scope, boolean dontNotifyBindings) {
         if (this.hasFindSourcesExecutedOperation) {
            throw new IllegalStateException("Visitor can have at most one find executed sources operation!");
         } else {
            this.operations
               .add(
                  new InstrumentationHandler.FindSourcesOperation(
                     scope, InstrumentationHandler.this.threadLocalNewSourcesExecuted, InstrumentationHandler.this.sourcesExecuted, dontNotifyBindings, true
                  )
               );
            this.hasFindSourcesExecutedOperation = true;
            return this;
         }
      }

      InstrumentationHandler.VisitorBuilder addInsertWrapperOperationForAllBindings(InstrumentationHandler.VisitOperation.Scope scope) {
         if (!InstrumentationHandler.this.executionBindings.isEmpty()) {
            this.operations.add(InstrumentationHandler.this.new InsertWrapperOperation(scope, InstrumentationHandler.this.executionBindings));
            this.shouldMaterializeSyntaxNodes = true;
         }

         return this;
      }

      InstrumentationHandler.VisitorBuilder addInsertWrapperOperationForBinding(
         InstrumentationHandler.VisitOperation.Scope scope, EventBinding.Source<?> binding
      ) {
         this.operations.add(InstrumentationHandler.this.new InsertWrapperOperation(scope, binding));
         this.shouldMaterializeSyntaxNodes = true;
         return this;
      }

      InstrumentationHandler.VisitorBuilder addDisposeWrapperOperationForBinding(EventBinding.Source<?> binding) {
         this.operations.add(new InstrumentationHandler.DisposeWrapperOperation(InstrumentationHandler.VisitOperation.Scope.ALL, binding));
         return this;
      }

      InstrumentationHandler.VisitorBuilder addDisposeWrapperOperationForBindings(InstrumentationHandler.CopyOnWriteList<EventBinding.Source<?>> bindings) {
         this.operations.add(new InstrumentationHandler.DisposeWrapperOperation(InstrumentationHandler.VisitOperation.Scope.ALL, bindings));
         return this;
      }

      InstrumentationHandler.Visitor buildVisitor() {
         return InstrumentationHandler.this.new Visitor(this.shouldMaterializeSyntaxNodes, Collections.unmodifiableList(this.operations));
      }
   }

   static final class WeakAsyncList<T> extends InstrumentationHandler.AbstractAsyncCollection<WeakReference<T>, T> {
      WeakAsyncList(int initialCapacity) {
         super(initialCapacity);
      }

      protected WeakReference<T> wrap(T element) {
         return new WeakReference<>(element);
      }

      protected T unwrap(WeakReference<T> element) {
         return element.get();
      }
   }
}
