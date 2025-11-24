
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.impl.DispatchOutputStream;
import com.oracle.truffle.api.instrumentation.AllocationReporter;
import com.oracle.truffle.api.instrumentation.InstrumentationHandler;
import com.oracle.truffle.api.instrumentation.Instrumenter;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.ThreadsActivationListener;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import org.graalvm.options.OptionDescriptor;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.options.OptionValues;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.io.MessageTransport;

final class InstrumentAccessor
extends Accessor {
    static final InstrumentAccessor ACCESSOR = new InstrumentAccessor();
    static final Accessor.NodeSupport NODES = ACCESSOR.nodeSupport();
    static final Accessor.SourceSupport SOURCE = ACCESSOR.sourceSupport();
    static final Accessor.LanguageSupport LANGUAGE = ACCESSOR.languageSupport();
    static final Accessor.EngineSupport ENGINE = ACCESSOR.engineSupport();
    static final Accessor.InteropSupport INTEROP = ACCESSOR.interopSupport();

    private InstrumentAccessor() {
    }

    static Accessor.NodeSupport nodesAccess() {
        return ACCESSOR.nodeSupport();
    }

    static Accessor.LanguageSupport langAccess() {
        return ACCESSOR.languageSupport();
    }

    static Accessor.EngineSupport engineAccess() {
        return ACCESSOR.engineSupport();
    }

    static Accessor.InteropSupport interopAccess() {
        return ACCESSOR.interopSupport();
    }

    static Accessor.RuntimeSupport runtimeAccess() {
        return ACCESSOR.runtimeSupport();
    }

    protected boolean isTruffleObject(Object value2) {
        return this.interopSupport().isTruffleObject(value2);
    }

    static final class InstrumentImpl
    extends Accessor.InstrumentSupport {
        InstrumentImpl() {
        }

        @Override
        public Object createInstrumentationHandler(Object polyglotEngine, DispatchOutputStream out, DispatchOutputStream err, InputStream in, MessageTransport messageInterceptor, boolean strongReferences) {
            return new InstrumentationHandler(polyglotEngine, out, err, in, messageInterceptor);
        }

        @Override
        public void initializeInstrument(Object instrumentationHandler, Object polyglotInstrument, String instrumentClassName, Supplier<? extends Object> instrumentSupplier) {
            ((InstrumentationHandler)instrumentationHandler).initializeInstrument(polyglotInstrument, instrumentClassName, instrumentSupplier);
        }

        @Override
        public void createInstrument(Object instrumentationHandler, Object polyglotInstrument, String[] expectedServices, OptionValues options) {
            ((InstrumentationHandler)instrumentationHandler).createInstrument(polyglotInstrument, expectedServices, options);
        }

        @Override
        public Object getEngineInstrumenter(Object instrumentationHandler) {
            return ((InstrumentationHandler)instrumentationHandler).engineInstrumenter;
        }

        @Override
        public void onNodeInserted(RootNode rootNode, Node tree) {
            InstrumentationHandler handler = InstrumentImpl.getHandler(rootNode);
            if (handler != null) {
                handler.onNodeInserted(rootNode, tree);
            }
        }

        @Override
        public OptionDescriptors describeEngineOptions(Object instrumentationHandler, Object key, String requiredGroup) {
            InstrumentationHandler.InstrumentClientInstrumenter instrumenter = (InstrumentationHandler.InstrumentClientInstrumenter)((InstrumentationHandler)instrumentationHandler).instrumenterMap.get(key);
            OptionDescriptors descriptors = instrumenter.instrument.getOptionDescriptors();
            return InstrumentImpl.validateOptions(requiredGroup, instrumenter, descriptors);
        }

        @Override
        public OptionDescriptors describeContextOptions(Object instrumentationHandler, Object key, String requiredGroup) {
            InstrumentationHandler.InstrumentClientInstrumenter instrumenter = (InstrumentationHandler.InstrumentClientInstrumenter)((InstrumentationHandler)instrumentationHandler).instrumenterMap.get(key);
            OptionDescriptors descriptors = instrumenter.instrument.getContextOptionDescriptors();
            return InstrumentImpl.validateOptions(requiredGroup, instrumenter, descriptors);
        }

        private static OptionDescriptors validateOptions(String requiredGroup, InstrumentationHandler.InstrumentClientInstrumenter instrumenter, OptionDescriptors descriptors) {
            if (descriptors == null) {
                return OptionDescriptors.EMPTY;
            }
            String groupPlusDot = requiredGroup + ".";
            for (OptionDescriptor descriptor : descriptors) {
                if (descriptor.getName().equals(requiredGroup) || descriptor.getName().startsWith(groupPlusDot)) continue;
                throw new IllegalArgumentException(String.format("Illegal option prefix in name '%s' specified for option described by instrument '%s'. The option prefix must match the id of the instrument '%s'.", descriptor.getName(), instrumenter.instrument.getClass().getName(), requiredGroup));
            }
            return descriptors;
        }

        @Override
        public void finalizeInstrument(Object instrumentationHandler, Object polyglotInstrument) {
            ((InstrumentationHandler)instrumentationHandler).finalizeInstrumenter(polyglotInstrument);
        }

        @Override
        public void disposeInstrument(Object instrumentationHandler, Object polyglotInstrument, boolean cleanupRequired) {
            ((InstrumentationHandler)instrumentationHandler).disposeInstrumenter(polyglotInstrument, cleanupRequired);
        }

        @Override
        public void collectEnvServices(Set<Object> collectTo, Object polyglotLanguageContext, TruffleLanguage<?> language) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(polyglotLanguageContext);
            Instrumenter instrumenter = instrumentationHandler.forLanguage(language);
            collectTo.add(instrumenter);
            AllocationReporter allocationReporter = instrumentationHandler.getAllocationReporter(InstrumentAccessor.langAccess().getLanguageInfo(language));
            collectTo.add(allocationReporter);
        }

        @Override
        public <T> T getInstrumentationHandlerService(Object instrumentationHandler, Object key, Class<T> type) {
            return ((InstrumentationHandler)instrumentationHandler).lookup(key, type);
        }

        @Override
        public void onFirstExecution(RootNode rootNode, boolean validate) {
            assert (!validate || InstrumentImpl.validEngine(rootNode));
            InstrumentationHandler handler = InstrumentImpl.getHandler(rootNode);
            if (handler != null) {
                handler.onFirstExecution(rootNode);
            }
        }

        @Override
        public void onLoad(RootNode rootNode) {
            InstrumentationHandler handler = InstrumentImpl.getHandler(rootNode);
            if (handler != null) {
                handler.onLoad(rootNode);
            }
        }

        @Override
        public boolean hasContextBindings(Object engine) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            return instrumentationHandler.hasContextBindings();
        }

        @Override
        public boolean hasThreadBindings(Object engine) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            return instrumentationHandler.hasThreadBindings();
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void notifyContextCreated(Object engine, TruffleContext context) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            instrumentationHandler.notifyContextCreated(context);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void notifyContextClosed(Object engine, TruffleContext context) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            instrumentationHandler.notifyContextClosed(context);
        }

        @Override
        public void notifyContextResetLimit(Object engine, TruffleContext context) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            instrumentationHandler.notifyContextResetLimit(context);
        }

        @Override
        public void notifyLanguageContextCreate(Object engine, TruffleContext context, LanguageInfo info) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            instrumentationHandler.notifyLanguageContextCreate(context, info);
        }

        @Override
        public void notifyLanguageContextCreated(Object engine, TruffleContext context, LanguageInfo info) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            instrumentationHandler.notifyLanguageContextCreated(context, info);
        }

        @Override
        public void notifyLanguageContextCreateFailed(Object engine, TruffleContext context, LanguageInfo info) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            instrumentationHandler.notifyLanguageContextCreateFailed(context, info);
        }

        @Override
        public void notifyLanguageContextInitialize(Object engine, TruffleContext context, LanguageInfo info) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            instrumentationHandler.notifyLanguageContextInitialize(context, info);
        }

        @Override
        public void notifyLanguageContextInitialized(Object engine, TruffleContext context, LanguageInfo info) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            instrumentationHandler.notifyLanguageContextInitialized(context, info);
        }

        @Override
        public void notifyLanguageContextInitializeFailed(Object engine, TruffleContext context, LanguageInfo info) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            instrumentationHandler.notifyLanguageContextInitializeFailed(context, info);
        }

        @Override
        public void notifyLanguageContextFinalized(Object engine, TruffleContext context, LanguageInfo info) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            instrumentationHandler.notifyLanguageContextFinalized(context, info);
        }

        @Override
        public void notifyLanguageContextDisposed(Object engine, TruffleContext context, LanguageInfo info) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            instrumentationHandler.notifyLanguageContextDisposed(context, info);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void notifyThreadStarted(Object engine, TruffleContext context, Thread thread) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            instrumentationHandler.notifyThreadStarted(context, thread);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void notifyThreadFinished(Object engine, TruffleContext context, Thread thread) {
            InstrumentationHandler instrumentationHandler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(engine);
            instrumentationHandler.notifyThreadFinished(context, thread);
        }

        @Override
        public org.graalvm.polyglot.SourceSection createSourceSection(Object instrumentEnv, Source source, SourceSection ss) {
            TruffleInstrument.Env env = (TruffleInstrument.Env)instrumentEnv;
            return InstrumentAccessor.engineAccess().createSourceSection(env.getPolyglotInstrument(), source, ss);
        }

        @Override
        public void patchInstrumentationHandler(Object instrumentationHandler, DispatchOutputStream out, DispatchOutputStream err, InputStream in) {
            ((InstrumentationHandler)instrumentationHandler).patch(out, err, in);
        }

        @Override
        public void finalizeStoreInstrumentationHandler(Object instrumentationHandler) {
            ((InstrumentationHandler)instrumentationHandler).finalizeStore();
        }

        @Override
        public boolean isInputValueSlotIdentifier(Object identifier) {
            return identifier instanceof ProbeNode.EventProviderWithInputChainNode.SavedInputValueID;
        }

        @Override
        public Collection<CallTarget> getLoadedCallTargets(Object instrumentationHandler) {
            Collection<RootNode> roots = ((InstrumentationHandler)instrumentationHandler).loadedRoots;
            ArrayList<CallTarget> targets = new ArrayList<CallTarget>();
            for (RootNode root : roots) {
                RootCallTarget target = root.getCallTarget();
                if (target == null) continue;
                targets.add(target);
            }
            return targets;
        }

        @Override
        public Object getPolyglotInstrument(Object instrumentEnv) {
            return ((TruffleInstrument.Env)instrumentEnv).getPolyglotInstrument();
        }

        private static InstrumentationHandler getHandler(RootNode rootNode) {
            return (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(rootNode);
        }

        @Override
        public boolean isInstrumentable(Node node) {
            return InstrumentationHandler.isInstrumentableNode(node);
        }

        private static boolean validEngine(RootNode rootNode) {
            if (InstrumentAccessor.engineAccess().skipEngineValidation(rootNode)) {
                return true;
            }
            Object currentSharingLayer = InstrumentAccessor.engineAccess().getCurrentSharingLayer();
            Object previousSharingLayer = InstrumentAccessor.nodesAccess().getSharingLayer(rootNode);
            if (!Objects.equals(previousSharingLayer, currentSharingLayer)) {
                throw InstrumentAccessor.engineAccess().invalidSharingError(rootNode, previousSharingLayer, currentSharingLayer);
            }
            return true;
        }

        @Override
        public Object invokeContextLocalFactory(Object factory, TruffleContext truffleContext) {
            Object result = ((TruffleInstrument.ContextLocalFactory)factory).create(truffleContext);
            if (result == null) {
                throw new IllegalStateException(String.format("%s.create is not allowed to return null.", TruffleInstrument.ContextLocalFactory.class.getSimpleName()));
            }
            return result;
        }

        @Override
        public Object invokeContextThreadLocalFactory(Object factory, TruffleContext truffleContext, Thread t) {
            Object result = ((TruffleInstrument.ContextThreadLocalFactory)factory).create(truffleContext, t);
            if (result == null) {
                throw new IllegalStateException(String.format("%s.create is not allowed to return null.", TruffleInstrument.ContextThreadLocalFactory.class.getSimpleName()));
            }
            return result;
        }

        @Override
        @ExplodeLoop
        public void notifyEnter(Object instrumentationHandler, TruffleContext truffleContext) {
            InstrumentationHandler handler = (InstrumentationHandler)instrumentationHandler;
            CompilerAsserts.partialEvaluationConstant(handler);
            for (ThreadsActivationListener listener : handler.getThreadsActivationListeners()) {
                listener.onEnterThread(truffleContext);
            }
        }

        @Override
        @ExplodeLoop
        public void notifyLeave(Object instrumentationHandler, TruffleContext truffleContext) {
            InstrumentationHandler handler = (InstrumentationHandler)instrumentationHandler;
            CompilerAsserts.partialEvaluationConstant(handler);
            for (ThreadsActivationListener listener : handler.getThreadsActivationListeners()) {
                listener.onLeaveThread(truffleContext);
            }
        }
    }
}

