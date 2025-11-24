
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.ContextLocal;
import com.oracle.truffle.api.ContextThreadLocal;
import com.oracle.truffle.api.InstrumentInfo;
import com.oracle.truffle.api.ThreadLocalAction;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.impl.TruffleLocator;
import com.oracle.truffle.api.instrumentation.ContextsListener;
import com.oracle.truffle.api.instrumentation.ThreadsListener;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.polyglot.DefaultLanguageView;
import com.oracle.truffle.polyglot.FileSystems;
import com.oracle.truffle.polyglot.HostToGuestRootNode;
import com.oracle.truffle.polyglot.InstrumentCache;
import com.oracle.truffle.polyglot.LanguageCache;
import com.oracle.truffle.polyglot.ModuleUtils;
import com.oracle.truffle.polyglot.OptionValuesImpl;
import com.oracle.truffle.polyglot.OtherContextGuestObject;
import com.oracle.truffle.polyglot.PolyglotBindings;
import com.oracle.truffle.polyglot.PolyglotContextConfig;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineException;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotEngineOptions;
import com.oracle.truffle.polyglot.PolyglotExceptionImpl;
import com.oracle.truffle.polyglot.PolyglotFastThreadLocals;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotInstrument;
import com.oracle.truffle.polyglot.PolyglotLanguage;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotLanguageInstance;
import com.oracle.truffle.polyglot.PolyglotLocals;
import com.oracle.truffle.polyglot.PolyglotLoggers;
import com.oracle.truffle.polyglot.PolyglotSharingLayer;
import com.oracle.truffle.polyglot.PolyglotThread;
import com.oracle.truffle.polyglot.ProcessHandlers;
import com.oracle.truffle.polyglot.SystemThread;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.nio.file.Path;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import org.graalvm.collections.Pair;
import org.graalvm.nativeimage.ImageInfo;
import org.graalvm.options.OptionKey;
import org.graalvm.options.OptionValues;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.EnvironmentAccess;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotAccess;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.io.FileSystem;
import org.graalvm.polyglot.io.ProcessHandler;

final class EngineAccessor
extends Accessor {
    static final EngineAccessor ACCESSOR = new EngineAccessor();
    static final Accessor.NodeSupport NODES = ACCESSOR.nodeSupport();
    static final Accessor.SourceSupport SOURCE = ACCESSOR.sourceSupport();
    static final Accessor.InstrumentSupport INSTRUMENT = ACCESSOR.instrumentSupport();
    static final Accessor.LanguageSupport LANGUAGE = ACCESSOR.languageSupport();
    static final Accessor.InteropSupport INTEROP = ACCESSOR.interopSupport();
    static final Accessor.ExceptionSupport EXCEPTION = ACCESSOR.exceptionSupport();
    static final Accessor.RuntimeSupport RUNTIME = ACCESSOR.runtimeSupport();
    static final Accessor.HostSupport HOST = ACCESSOR.hostSupport();

    private static List<AbstractClassLoaderSupplier> locatorLoaders() {
        if (ImageInfo.inImageRuntimeCode()) {
            return Collections.emptyList();
        }
        List<ClassLoader> loaders = TruffleLocator.loaders();
        if (loaders == null) {
            return null;
        }
        ArrayList<AbstractClassLoaderSupplier> suppliers = new ArrayList<AbstractClassLoaderSupplier>(loaders.size());
        for (ClassLoader loader : loaders) {
            suppliers.add(new StrongClassLoaderSupplier(loader));
        }
        return suppliers;
    }

    private static List<AbstractClassLoaderSupplier> defaultLoaders() {
        return Arrays.asList(new StrongClassLoaderSupplier(EngineAccessor.class.getClassLoader()), new StrongClassLoaderSupplier(ClassLoader.getSystemClassLoader()), new WeakClassLoaderSupplier(Thread.currentThread().getContextClassLoader()));
    }

    static List<AbstractClassLoaderSupplier> locatorOrDefaultLoaders() {
        List<AbstractClassLoaderSupplier> loaders = EngineAccessor.locatorLoaders();
        if (loaders == null) {
            loaders = EngineAccessor.defaultLoaders();
        }
        return loaders;
    }

    private EngineAccessor() {
    }

    @Override
    protected void initializeNativeImageTruffleLocator() {
        super.initializeNativeImageTruffleLocator();
    }

    private static final class WeakClassLoaderSupplier
    extends AbstractClassLoaderSupplier {
        private final Reference<ClassLoader> classLoaderRef;

        WeakClassLoaderSupplier(ClassLoader classLoader) {
            super(classLoader);
            this.classLoaderRef = new WeakReference<ClassLoader>(classLoader);
        }

        @Override
        public ClassLoader get() {
            return this.classLoaderRef.get();
        }
    }

    static final class StrongClassLoaderSupplier
    extends AbstractClassLoaderSupplier {
        private final ClassLoader classLoader;

        StrongClassLoaderSupplier(ClassLoader classLoader) {
            super(classLoader);
            this.classLoader = classLoader;
        }

        @Override
        public ClassLoader get() {
            return this.classLoader;
        }
    }

    static abstract class AbstractClassLoaderSupplier
    implements Supplier<ClassLoader> {
        private final int hashCode;

        AbstractClassLoaderSupplier(ClassLoader loader) {
            this.hashCode = loader == null ? 0 : loader.hashCode();
        }

        public final int hashCode() {
            return this.hashCode;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof AbstractClassLoaderSupplier)) {
                return false;
            }
            AbstractClassLoaderSupplier supplier = (AbstractClassLoaderSupplier)obj;
            return Objects.equals(this.get(), supplier.get());
        }
    }

    static final class EngineImpl
    extends Accessor.EngineSupport {
        private EngineImpl() {
        }

        @Override
        public boolean isDisposed(Object polyglotLanguageContext) {
            return EngineImpl.getEngine((Object)polyglotLanguageContext).closed;
        }

        @Override
        public boolean hasCurrentContext() {
            return PolyglotFastThreadLocals.getContext(null) != null;
        }

        @Override
        public boolean isPolyglotEvalAllowed(Object polyglotLanguageContext) {
            PolyglotLanguageContext languageContext = (PolyglotLanguageContext)polyglotLanguageContext;
            return languageContext.isPolyglotEvalAllowed(null);
        }

        @Override
        public boolean isPolyglotBindingsAccessAllowed(Object polyglotLanguageContext) {
            PolyglotLanguageContext languageContext = (PolyglotLanguageContext)polyglotLanguageContext;
            return languageContext.isPolyglotBindingsAccessAllowed();
        }

        @Override
        public ZoneId getTimeZone(Object polyglotLanguageContext) {
            return ((PolyglotLanguageContext)polyglotLanguageContext).context.config.getTimeZone();
        }

        @Override
        public Object getPolyglotEngine(Object polyglotLanguageInstance) {
            return ((PolyglotLanguageInstance)polyglotLanguageInstance).language.engine;
        }

        @Override
        public Object getDefaultLanguageView(TruffleLanguage<?> truffleLanguage, Object value2) {
            return new DefaultLanguageView(truffleLanguage, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getLanguageView(LanguageInfo viewLanguage, Object value2) {
            PolyglotContextImpl context = PolyglotContextImpl.requireContext();
            PolyglotLanguage language = context.engine.idToLanguage.get(viewLanguage.getId());
            PolyglotLanguageContext languageContext = context.getContextInitialized(language, null);
            return languageContext.getLanguageView(value2);
        }

        @Override
        public LanguageInfo getLanguageInfo(Object polyglotInstrument, Class<? extends TruffleLanguage<?>> languageClass) {
            return ((PolyglotInstrument)polyglotInstrument).engine.getLanguage(languageClass, (boolean)true).info;
        }

        @Override
        public CallTarget parseForLanguage(Object sourceLanguageContext, com.oracle.truffle.api.source.Source source, String[] argumentNames, boolean allowInternal) {
            PolyglotLanguageContext sourceContext = (PolyglotLanguageContext)sourceLanguageContext;
            if (PolyglotFastThreadLocals.getContext(null) != sourceContext.context) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw PolyglotEngineException.illegalState("The context is not entered.");
            }
            PolyglotLanguage targetLanguage = sourceContext.context.engine.findLanguage(sourceContext, source.getLanguage(), source.getMimeType(), true, allowInternal);
            PolyglotLanguageContext targetContext = sourceContext.context.getContextInitialized(targetLanguage, sourceContext.language);
            targetContext.checkAccess(sourceContext.getLanguageInstance().language);
            return targetContext.parseCached(sourceContext.language, source, argumentNames);
        }

        @Override
        public TruffleLanguage.Env getEnvForInstrument(String languageId, String mimeType) {
            PolyglotContextImpl context = PolyglotContextImpl.requireContext();
            PolyglotLanguage foundLanguage = context.engine.findLanguage(null, languageId, mimeType, true, true);
            return context.getContextInitialized((PolyglotLanguage)foundLanguage, null).env;
        }

        @Override
        public org.graalvm.polyglot.SourceSection createSourceSection(Object polyglotObject, Source source, SourceSection sectionImpl) {
            return PolyglotImpl.getPolyglotSourceSection(((PolyglotImpl.VMObject)polyglotObject).getImpl(), sectionImpl);
        }

        @Override
        public TruffleFile getTruffleFile(String path) {
            PolyglotContextImpl context = PolyglotContextImpl.requireContext();
            return LANGUAGE.getTruffleFile(context.getHostContext().getPublicFileSystemContext(), path);
        }

        @Override
        public TruffleFile getTruffleFile(URI uri) {
            PolyglotContextImpl context = PolyglotContextImpl.requireContext();
            return LANGUAGE.getTruffleFile(context.getHostContext().getPublicFileSystemContext(), uri);
        }

        @Override
        public <T> Iterable<T> loadServices(Class<T> type) {
            LinkedHashMap found = new LinkedHashMap();
            if (type.getClassLoader() == Truffle.class.getClassLoader()) {
                for (AbstractClassLoaderSupplier service2 : ServiceLoader.load(type, type.getClassLoader())) {
                    found.putIfAbsent(service2.getClass(), service2);
                }
            }
            for (AbstractClassLoaderSupplier loaderSupplier : EngineAccessor.locatorOrDefaultLoaders()) {
                ClassLoader loader = (ClassLoader)loaderSupplier.get();
                if (!EngineImpl.seesTheSameClass(loader, type)) continue;
                ModuleUtils.exportTo(loader, null);
                for (T service3 : ServiceLoader.load(type, loader)) {
                    found.putIfAbsent(service3.getClass(), (AbstractClassLoaderSupplier)service3);
                }
            }
            return found.values();
        }

        private static boolean seesTheSameClass(ClassLoader loader, Class<?> type) {
            try {
                return loader != null && loader.loadClass(type.getName()) == type;
            }
            catch (ClassNotFoundException ex) {
                return false;
            }
        }

        @Override
        public <T> T lookup(InstrumentInfo info, Class<T> serviceClass) {
            PolyglotInstrument instrument = (PolyglotInstrument)LANGUAGE.getPolyglotInstrument(info);
            return instrument.lookupInternal(serviceClass);
        }

        @Override
        public <S> S lookup(LanguageInfo info, Class<S> serviceClass) {
            if (!((LanguageCache)NODES.getLanguageCache(info)).supportsService(serviceClass)) {
                return null;
            }
            PolyglotContextImpl context = PolyglotContextImpl.requireContext();
            PolyglotLanguage language = context.engine.idToLanguage.get(info.getId());
            PolyglotLanguageContext languageContext = context.getContext(language);
            languageContext.ensureCreated(language);
            return languageContext.lookupService(serviceClass);
        }

        @Override
        public <C, T extends TruffleLanguage<C>> C getCurrentContext(Class<T> languageClass) {
            CompilerAsserts.partialEvaluationConstant(languageClass);
            int index = PolyglotFastThreadLocals.computePELanguageIndex(languageClass, 0);
            CompilerAsserts.partialEvaluationConstant(index);
            Object contextImpl = PolyglotFastThreadLocals.getLanguageContext(null, index);
            if (contextImpl == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw PolyglotEngineException.illegalState("There is no current context available.");
            }
            return (C)contextImpl;
        }

        @Override
        public TruffleContext getTruffleContext(Object polyglotLanguageContext) {
            PolyglotLanguageContext languageContext = (PolyglotLanguageContext)polyglotLanguageContext;
            return languageContext.context.currentTruffleContext;
        }

        @Override
        public Object getHostContext(Object polyglotContext) {
            return ((PolyglotContextImpl)polyglotContext).getHostContextImpl();
        }

        @Override
        public Value asValue(Object polyglotContextImpl, Object guestValue) {
            return ((PolyglotContextImpl)polyglotContextImpl).getHostContext().asValue(guestValue);
        }

        @Override
        public Object enterLanguageFromRuntime(TruffleLanguage<?> language) {
            PolyglotLanguageInstance instance = (PolyglotLanguageInstance)LANGUAGE.getPolyglotLanguageInstance(language);
            return PolyglotFastThreadLocals.enterLanguage(instance);
        }

        @Override
        public void leaveLanguageFromRuntime(TruffleLanguage<?> language, Object prev) {
            PolyglotLanguageInstance instance = (PolyglotLanguageInstance)LANGUAGE.getPolyglotLanguageInstance(language);
            PolyglotFastThreadLocals.leaveLanguage(instance, prev);
        }

        @Override
        public TruffleContext getCurrentCreatorTruffleContext() {
            PolyglotContextImpl context = PolyglotFastThreadLocals.getContext(null);
            return context != null ? context.creatorTruffleContext : null;
        }

        @Override
        public <T extends TruffleLanguage<?>> T getCurrentLanguage(Class<T> languageClass) {
            CompilerAsserts.partialEvaluationConstant(languageClass);
            int index = PolyglotFastThreadLocals.computePELanguageIndex(languageClass, 1);
            CompilerAsserts.partialEvaluationConstant(index);
            TruffleLanguage<Object> language = PolyglotFastThreadLocals.getLanguage(null, index, languageClass);
            if (language == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw PolyglotEngineException.illegalState("There is no current context available.");
            }
            return (T)language;
        }

        @Override
        public Map<String, LanguageInfo> getInternalLanguages(Object polyglotObject) {
            if (polyglotObject instanceof PolyglotLanguageContext) {
                return ((PolyglotLanguageContext)polyglotObject).getAccessibleLanguages(true);
            }
            return EngineImpl.getEngine((Object)polyglotObject).idToInternalLanguageInfo;
        }

        @Override
        public Map<String, LanguageInfo> getPublicLanguages(Object polyglotObject) {
            return ((PolyglotLanguageContext)polyglotObject).getAccessibleLanguages(false);
        }

        @Override
        public Map<String, InstrumentInfo> getInstruments(Object polyglotObject) {
            return EngineImpl.getEngine((Object)polyglotObject).idToInternalInstrumentInfo;
        }

        private static PolyglotEngineImpl getEngine(Object polyglotObject) throws AssertionError {
            if (!(polyglotObject instanceof PolyglotImpl.VMObject)) {
                throw CompilerDirectives.shouldNotReachHere();
            }
            return ((PolyglotImpl.VMObject)polyglotObject).getEngine();
        }

        @Override
        public TruffleLanguage.Env getEnvForInstrument(LanguageInfo info) {
            PolyglotContextImpl context = PolyglotContextImpl.requireContext();
            PolyglotLanguage language = context.engine.idToLanguage.get(info.getId());
            return context.getContextInitialized((PolyglotLanguage)language, null).env;
        }

        static PolyglotLanguage findObjectLanguage(PolyglotEngineImpl engine, Object value2) {
            InteropLibrary lib = InteropLibrary.getFactory().getUncached(value2);
            if (lib.hasLanguage(value2)) {
                try {
                    return engine.getLanguage(lib.getLanguage(value2), false);
                }
                catch (UnsupportedMessageException e) {
                    throw CompilerDirectives.shouldNotReachHere(e);
                }
            }
            return null;
        }

        static PolyglotLanguage getLanguageView(PolyglotEngineImpl engine, Object value2) {
            InteropLibrary lib = InteropLibrary.getFactory().getUncached(value2);
            if (lib.hasLanguage(value2)) {
                try {
                    return engine.getLanguage(lib.getLanguage(value2), false);
                }
                catch (UnsupportedMessageException e) {
                    throw CompilerDirectives.shouldNotReachHere(e);
                }
            }
            return null;
        }

        static boolean isPrimitive(Object value2) {
            Class<?> valueClass = value2.getClass();
            return valueClass == Boolean.class || valueClass == Byte.class || valueClass == Short.class || valueClass == Integer.class || valueClass == Long.class || valueClass == Float.class || valueClass == Double.class || valueClass == Character.class || valueClass == String.class;
        }

        @Override
        public Object getCurrentSharingLayer() {
            PolyglotContextImpl context = PolyglotFastThreadLocals.getContext(null);
            if (context == null) {
                return null;
            }
            return context.layer;
        }

        @Override
        public Object getCurrentPolyglotEngine() {
            PolyglotContextImpl context = PolyglotFastThreadLocals.getContext(null);
            if (context == null) {
                return null;
            }
            return context.engine;
        }

        @Override
        public boolean isMultiThreaded(Object guestObject) {
            PolyglotContextImpl context = PolyglotFastThreadLocals.getContext(null);
            if (context == null) {
                return true;
            }
            if (EngineImpl.isPrimitive(guestObject)) {
                return false;
            }
            if (context.engine.host.isHostValue(guestObject) || guestObject instanceof PolyglotBindings) {
                return true;
            }
            PolyglotLanguage language = EngineImpl.findObjectLanguage(context.engine, guestObject);
            if (language == null) {
                return true;
            }
            return !context.singleThreaded;
        }

        @Override
        public boolean isEvalRoot(RootNode target) {
            return false;
        }

        @Override
        public RuntimeException engineToLanguageException(Throwable t) {
            return PolyglotImpl.engineToLanguageException(t);
        }

        @Override
        public RuntimeException engineToInstrumentException(Throwable t) {
            return PolyglotImpl.engineToInstrumentException(t);
        }

        @Override
        public Object getCurrentFileSystemContext() {
            return PolyglotContextImpl.requireContext().getHostContext().getPublicFileSystemContext();
        }

        @Override
        public Object getPublicFileSystemContext(Object polyglotLanguageContext) {
            return ((PolyglotLanguageContext)polyglotLanguageContext).getPublicFileSystemContext();
        }

        @Override
        public Object getInternalFileSystemContext(Object polyglotLanguageContext) {
            return ((PolyglotLanguageContext)polyglotLanguageContext).getInternalFileSystemContext();
        }

        @Override
        public Map<String, Collection<? extends TruffleFile.FileTypeDetector>> getEngineFileTypeDetectors(Object engineFileSystemObject) {
            if (engineFileSystemObject instanceof PolyglotLanguageContext) {
                return ((PolyglotLanguageContext)engineFileSystemObject).context.engine.getFileTypeDetectorsSupplier().get();
            }
            if (engineFileSystemObject instanceof PolyglotImpl.EmbedderFileSystemContext) {
                return ((PolyglotImpl.EmbedderFileSystemContext)engineFileSystemObject).fileTypeDetectors.get();
            }
            throw new AssertionError();
        }

        @Override
        public Set<String> getValidMimeTypes(Object engineObject, String language) {
            LanguageCache lang = EngineImpl.getLanguageCache(engineObject, language);
            if (lang != null) {
                return lang.getMimeTypes();
            }
            return Collections.emptySet();
        }

        private static LanguageCache getLanguageCache(Object engineObject, String language) throws AssertionError {
            if (engineObject instanceof PolyglotLanguageContext) {
                PolyglotLanguage polyglotLanguage = ((PolyglotLanguageContext)engineObject).context.engine.idToLanguage.get(language);
                if (polyglotLanguage != null) {
                    return polyglotLanguage.cache;
                }
                return null;
            }
            if (engineObject instanceof PolyglotImpl.EmbedderFileSystemContext) {
                return ((PolyglotImpl.EmbedderFileSystemContext)engineObject).cachedLanguages.get(language);
            }
            throw new AssertionError();
        }

        @Override
        public boolean isCharacterBasedSource(Object fsEngineObject, String language, String mimeType) {
            LanguageCache cache = EngineImpl.getLanguageCache(fsEngineObject, language);
            if (cache == null) {
                return true;
            }
            String useMimeType = mimeType;
            if (useMimeType == null) {
                useMimeType = cache.getDefaultMimeType();
            }
            if (useMimeType == null || !cache.getMimeTypes().contains(useMimeType)) {
                return true;
            }
            return cache.isCharacterMimeType(useMimeType);
        }

        @Override
        public boolean isMimeTypeSupported(Object polyglotLanguageContext, String mimeType) {
            PolyglotEngineImpl engine = EngineImpl.getEngine(polyglotLanguageContext);
            for (PolyglotLanguage language : engine.idToLanguage.values()) {
                if (!language.cache.getMimeTypes().contains(mimeType)) continue;
                return true;
            }
            return false;
        }

        @Override
        public Object getInstrumentationHandler(Object polyglotObject) {
            return EngineImpl.getEngine((Object)polyglotObject).instrumentationHandler;
        }

        @Override
        public Object getInstrumentationHandler(RootNode rootNode) {
            PolyglotSharingLayer sharing = (PolyglotSharingLayer)NODES.getSharingLayer(rootNode);
            if (sharing == null) {
                return null;
            }
            return this.getInstrumentationHandler(sharing.engine);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object importSymbol(Object polyglotLanguageContext, TruffleLanguage.Env env, String symbolName) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)polyglotLanguageContext;
            Value value2 = context.context.polyglotBindings.get(symbolName);
            if (value2 != null) {
                return context.getAPIAccess().getReceiver(value2);
            }
            return null;
        }

        @Override
        public Object lookupHostSymbol(Object polyglotLanguageContext, TruffleLanguage.Env env, String symbolName) {
            PolyglotContextImpl context = ((PolyglotLanguageContext)polyglotLanguageContext).context;
            if (!context.config.hostLookupAllowed) {
                context.engine.host.throwHostLanguageException("Host class access is not allowed.");
            }
            return context.engine.host.findStaticClass(context.getHostContextImpl(), symbolName);
        }

        @Override
        public Object asHostSymbol(Object polyglotLanguageContext, Class<?> symbolClass) {
            PolyglotContextImpl context = ((PolyglotLanguageContext)polyglotLanguageContext).context;
            return context.engine.host.asHostStaticClass(context.getHostContextImpl(), symbolClass);
        }

        @Override
        public boolean isHostAccessAllowed(Object polyglotLanguageContext, TruffleLanguage.Env env) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)polyglotLanguageContext;
            return context.context.config.hostLookupAllowed;
        }

        @Override
        public boolean isNativeAccessAllowed(Object polyglotLanguageContext, TruffleLanguage.Env env) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)polyglotLanguageContext;
            return context.context.config.nativeAccessAllowed;
        }

        @Override
        public boolean isIOAllowed(Object polyglotLanguageContext, TruffleLanguage.Env env) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)polyglotLanguageContext;
            return context.context.config.isAllowIO();
        }

        @Override
        public boolean isInnerContextOptionsAllowed(Object polyglotLanguageContext, TruffleLanguage.Env env) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)polyglotLanguageContext;
            return context.context.config.innerContextOptionsAllowed;
        }

        @Override
        public boolean isCurrentNativeAccessAllowed(Node node) {
            PolyglotContextImpl context = PolyglotFastThreadLocals.getContext(PolyglotFastThreadLocals.resolveLayer(node));
            if (context == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new IllegalStateException("No current context entered.");
            }
            return context.config.nativeAccessAllowed;
        }

        @Override
        public boolean inContextPreInitialization(Object polyglotObject) {
            PolyglotContextImpl polyglotContext;
            if (polyglotObject instanceof PolyglotContextImpl) {
                polyglotContext = (PolyglotContextImpl)polyglotObject;
            } else if (polyglotObject instanceof PolyglotLanguageContext) {
                polyglotContext = ((PolyglotLanguageContext)polyglotObject).context;
            } else {
                if (polyglotObject instanceof PolyglotImpl.EmbedderFileSystemContext) {
                    return false;
                }
                throw CompilerDirectives.shouldNotReachHere();
            }
            return polyglotContext.inContextPreInitialization;
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void exportSymbol(Object polyglotLanguageContext, String symbolName, Object value2) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)polyglotLanguageContext;
            if (value2 == null) {
                context.context.getPolyglotGuestBindings().remove(symbolName);
                return;
            }
            if (!PolyglotImpl.isGuestPrimitive(value2) && !(value2 instanceof TruffleObject)) {
                throw new IllegalArgumentException("Invalid exported value. Must be an interop value.");
            }
            context.context.getPolyglotGuestBindings().put(symbolName, context.asValue(value2));
        }

        @Override
        public Map<String, ? extends Object> getExportedSymbols() {
            PolyglotContextImpl currentContext = PolyglotFastThreadLocals.getContext(null);
            return currentContext.getPolyglotBindings().as(Map.class);
        }

        @Override
        public Object getPolyglotBindingsObject() {
            PolyglotContextImpl currentContext = PolyglotFastThreadLocals.getContext(null);
            return currentContext.getPolyglotBindingsObject();
        }

        @Override
        public Object toGuestValue(Node node, Object obj, Object languageContext) {
            PolyglotContextImpl context = ((PolyglotLanguageContext)languageContext).context;
            return context.toGuestValue(node, obj, false);
        }

        @Override
        public Object asBoxedGuestValue(Object guestObject, Object polyglotLanguageContext) {
            PolyglotContextImpl context = ((PolyglotLanguageContext)polyglotLanguageContext).context;
            if (PolyglotImpl.isGuestPrimitive(guestObject)) {
                return context.engine.host.toHostObject(context.getHostContextImpl(), guestObject);
            }
            if (guestObject instanceof TruffleObject) {
                return guestObject;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Provided value not an interop value.");
        }

        @Override
        public void reportAllLanguageContexts(Object polyglotEngine, Object contextsListener) {
            ((PolyglotEngineImpl)polyglotEngine).reportAllLanguageContexts((ContextsListener)contextsListener);
        }

        @Override
        public void reportAllContextThreads(Object polyglotEngine, Object threadsListener) {
            ((PolyglotEngineImpl)polyglotEngine).reportAllContextThreads((ThreadsListener)threadsListener);
        }

        @Override
        public TruffleContext getParentContext(Object polyglotContext) {
            PolyglotContextImpl parent = ((PolyglotContextImpl)polyglotContext).parent;
            if (parent != null) {
                return parent.currentTruffleContext;
            }
            return null;
        }

        @Override
        public Object enterInternalContext(Node location, Object polyglotLanguageContext) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotLanguageContext;
            PolyglotEngineImpl engine = EngineImpl.resolveEngine(location, context);
            return engine.enter(context);
        }

        @Override
        public Object[] enterContextAsPolyglotThread(Object polyglotContext) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            return context.enterThreadChanged(true, false, true, false, true);
        }

        @Override
        public void leaveContextAsPolyglotThread(Object polyglotContext, Object[] prev) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            context.leaveThreadChanged(prev, true, true, true);
        }

        @Override
        public Object enterIfNeeded(Object polyglotContext) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            try {
                return context.engine.enterIfNeeded(context, true);
            }
            catch (Throwable t) {
                throw PolyglotImpl.guestToHostException(context.getHostContext(), t, false);
            }
        }

        @Override
        public void leaveIfNeeded(Object polyglotContext, Object prev) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            try {
                context.engine.leaveIfNeeded(prev, context);
            }
            catch (Throwable t) {
                throw PolyglotImpl.guestToHostException(context.getHostContext(), t, false);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean initializeInnerContext(Node location, Object polyglotContext, String languageId, boolean allowInternal) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            if (context.parent == null) {
                throw PolyglotEngineException.illegalState("Only created inner contexts can be used to initialize language contexts. Use TruffleLanguage.Env.initializeLanguage(LanguageInfo) instead.");
            }
            PolyglotEngineImpl engine = EngineImpl.resolveEngine(location, context);
            Object[] prev = engine.enter(context);
            try {
                PolyglotLanguage language = engine.requireLanguage(languageId, allowInternal);
                PolyglotLanguageContext targetLanguageContext = context.getContext(language);
                boolean bl = targetLanguageContext.ensureInitialized(null);
                engine.leave(prev, context);
                return bl;
            }
            catch (Throwable throwable) {
                try {
                    engine.leave(prev, context);
                    throw throwable;
                }
                catch (Throwable t) {
                    throw OtherContextGuestObject.toHostOrInnerContextBoundaryException(context.parent, t, context);
                }
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object evalInternalContext(Node location, Object polyglotContext, com.oracle.truffle.api.source.Source source, boolean allowInternal) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            if (context.parent == null) {
                throw PolyglotEngineException.illegalState("Only created inner contexts can be used to evaluate sources. Use TruffleLanguage.Env.parseInternal(Source) or TruffleInstrument.Env.parse(Source) instead.");
            }
            PolyglotEngineImpl engine = EngineImpl.resolveEngine(location, context);
            Object[] prev = engine.enter(context);
            try {
                Object object = EngineImpl.evalBoundary(source, prev, context, allowInternal);
                engine.leave(prev, context);
                return object;
            }
            catch (Throwable throwable) {
                try {
                    engine.leave(prev, context);
                    throw throwable;
                }
                catch (Throwable t) {
                    throw OtherContextGuestObject.toHostOrInnerContextBoundaryException(context.parent, t, context);
                }
            }
        }

        @CompilerDirectives.TruffleBoundary
        private static Object evalBoundary(com.oracle.truffle.api.source.Source source, Object[] prev, PolyglotContextImpl context, boolean allowInternal) {
            Object result;
            PolyglotContextImpl parentEnteredContext = (PolyglotContextImpl)prev[1];
            if (parentEnteredContext != null && parentEnteredContext != context.parent && parentEnteredContext.engine == context.engine) {
                throw PolyglotEngineException.illegalState("Invalid parent context entered. The parent creator context or no context must be entered to evaluate code in an inner context.");
            }
            PolyglotLanguageContext targetContext = context.getContext(context.engine.requireLanguage(source.getLanguage(), allowInternal));
            PolyglotLanguage accessingLanguage = context.creator;
            targetContext.checkAccess(accessingLanguage);
            try {
                CallTarget target = targetContext.parseCached(accessingLanguage, source, null);
                result = target.call(PolyglotImpl.EMPTY_ARGS);
            }
            catch (Throwable e) {
                throw OtherContextGuestObject.migrateException(context.parent, e, context);
            }
            assert (InteropLibrary.isValidValue(result)) : "invalid call target return value";
            return context.parent.migrateValue(result, context);
        }

        @Override
        public void leaveInternalContext(Node node, Object impl, Object prev) {
            CompilerAsserts.partialEvaluationConstant(node);
            PolyglotContextImpl context = (PolyglotContextImpl)impl;
            PolyglotEngineImpl engine = EngineImpl.resolveEngine(node, context);
            if (CompilerDirectives.isPartialEvaluationConstant(engine)) {
                engine.leave((Object[])prev, context);
            } else {
                EngineImpl.leaveInternalContextBoundary(prev, context, engine);
            }
        }

        @CompilerDirectives.TruffleBoundary
        private static void leaveInternalContextBoundary(Object prev, PolyglotContextImpl context, PolyglotEngineImpl engine) {
            engine.leave((Object[])prev, context);
        }

        private static PolyglotEngineImpl resolveEngine(Node node, PolyglotContextImpl context) {
            PolyglotEngineImpl engine;
            if (CompilerDirectives.inCompiledCode() && node != null) {
                RootNode root = node.getRootNode();
                if (root == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    throw new IllegalStateException("Passed node is not yet adopted. Adopt it first.");
                }
                CompilerAsserts.partialEvaluationConstant(root);
                PolyglotSharingLayer sharing = (PolyglotSharingLayer)NODES.getSharingLayer(root);
                engine = sharing.engine;
                CompilerAsserts.partialEvaluationConstant(engine);
                assert (engine != null) : "root node engine must not be null";
            } else {
                engine = context.engine;
            }
            return engine;
        }

        @Override
        public boolean isContextEntered(Object impl) {
            return PolyglotFastThreadLocals.getContext(null) == impl;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public TruffleContext createInternalContext(Object sourcePolyglotLanguageContext, OutputStream out, OutputStream err, InputStream in, ZoneId timeZone, String[] onlyLanguagesArray, Map<String, Object> config, Map<String, String> options, Map<String, String[]> arguments, Boolean sharingEnabled, boolean initializeCreatorContext, Runnable onCancelledRunnable, Consumer<Integer> onExitedRunnable, Runnable onClosedRunnable, boolean inheritAccess, Boolean allowCreateThreads, Boolean allowNativeAccess, Boolean allowIO, Boolean allowHostLookup, Boolean allowHostClassLoading, Boolean allowCreateProcess, Boolean allowPolyglotAccess, Boolean allowEnvironmentAccess, Map<String, String> customEnvironment, Boolean allowInnerContextOptions) {
            PolyglotContextImpl impl;
            Map<String, String> useCustomEnvironment;
            FileSystem useInternalFileSystem;
            FileSystem useFileSystem;
            Map<String, String> useOptions;
            Set<String> allowedLanguages;
            PolyglotLanguageContext creator = (PolyglotLanguageContext)sourcePolyglotLanguageContext;
            PolyglotEngineImpl engine = creator.context.engine;
            PolyglotContextConfig creatorConfig = creator.context.config;
            if (onlyLanguagesArray.length == 0) {
                allowedLanguages = creatorConfig.onlyLanguages;
            } else {
                allowedLanguages = new HashSet<String>();
                allowedLanguages.addAll(Arrays.asList(onlyLanguagesArray));
                if (initializeCreatorContext) {
                    allowedLanguages.add(creator.language.getId());
                }
                for (String language : allowedLanguages) {
                    if (creatorConfig.allowedPublicLanguages.contains(language)) continue;
                    throw PolyglotEngineException.illegalArgument(String.format("The language %s permitted for the created inner context is not installed or was not permitted by the parent context. The parent context only permits the use of the following languages: %s. Ensure the context has access to the permitted language or remove the language from the permitted language list.", language, creatorConfig.allowedPublicLanguages.toString(), language));
                }
            }
            if (options == null) {
                useOptions = creatorConfig.originalOptions;
            } else {
                useOptions = new HashMap<String, String>(creatorConfig.originalOptions);
                useOptions.putAll(options);
            }
            if (options != null && !options.isEmpty() && !creatorConfig.innerContextOptionsAllowed) {
                throw PolyglotEngineException.illegalArgument(String.format("Language options were specified for the inner context but the outer context does not have the required context options privilege for this operation. Use TruffleLanguage.Env.isInnerContextOptionsAllowed() to check whether the inner context has this privilege. Use Context.Builder.allowInnerContextOptions(true) to grant inner context option privilege for inner contexts.", new Object[0]));
            }
            OutputStream useOut = out;
            useOut = useOut == null ? creatorConfig.out : INSTRUMENT.createDelegatingOutput(out, engine.out);
            OutputStream useErr = err;
            useErr = useErr == null ? creatorConfig.err : INSTRUMENT.createDelegatingOutput(useErr, engine.out);
            InputStream useIn = in == null ? creatorConfig.in : in;
            boolean useAllowCreateThread = EngineImpl.inheritAccess(inheritAccess, allowCreateThreads, creatorConfig.createThreadAllowed);
            boolean useAllowNativeAccess = EngineImpl.inheritAccess(inheritAccess, allowNativeAccess, creatorConfig.nativeAccessAllowed);
            if (EngineImpl.inheritAccess(inheritAccess, allowIO, true)) {
                useFileSystem = creatorConfig.fileSystem;
                useInternalFileSystem = creatorConfig.internalFileSystem;
            } else {
                useFileSystem = FileSystems.newNoIOFileSystem();
                useInternalFileSystem = PolyglotEngineImpl.ALLOW_IO ? FileSystems.newLanguageHomeFileSystem() : useFileSystem;
            }
            HostAccess useAllowHostAccess = creatorConfig.hostAccess;
            boolean useAllowHostClassLoading = EngineImpl.inheritAccess(inheritAccess, allowHostClassLoading, creatorConfig.hostClassLoadingAllowed);
            boolean useAllowHostLookup = EngineImpl.inheritAccess(inheritAccess, allowHostLookup, creatorConfig.hostLookupAllowed);
            Predicate<String> useClassFilter = useAllowHostLookup ? creatorConfig.classFilter : null;
            boolean useAllowCreateProcess = EngineImpl.inheritAccess(inheritAccess, allowCreateProcess, creatorConfig.createProcessAllowed);
            ProcessHandler useProcessHandler = useAllowCreateProcess ? creatorConfig.processHandler : null;
            PolyglotAccess usePolyglotAccess = EngineImpl.inheritAccess(inheritAccess, allowPolyglotAccess, true) ? creatorConfig.polyglotAccess : PolyglotAccess.NONE;
            EnvironmentAccess useEnvironmentAccess = EngineImpl.inheritAccess(inheritAccess, allowEnvironmentAccess, true) ? creatorConfig.environmentAccess : EnvironmentAccess.NONE;
            if (useEnvironmentAccess == EnvironmentAccess.INHERIT && !creatorConfig.customEnvironment.isEmpty()) {
                useCustomEnvironment = new HashMap<String, String>(creatorConfig.customEnvironment);
                if (customEnvironment != null) {
                    useCustomEnvironment.putAll(customEnvironment);
                }
            } else {
                useCustomEnvironment = customEnvironment;
            }
            boolean useAllowInnerContextOptions = EngineImpl.inheritAccess(inheritAccess, allowInnerContextOptions, creatorConfig.innerContextOptionsAllowed);
            ZoneId useTimeZone = timeZone == null ? creatorConfig.timeZone : timeZone;
            Map<Object, Object> useArguments = arguments == null ? Collections.emptyMap() : arguments;
            PolyglotContextConfig innerConfig = new PolyglotContextConfig(engine, sharingEnabled, useOut, useErr, useIn, useAllowHostLookup, usePolyglotAccess, useAllowNativeAccess, useAllowCreateThread, useAllowHostClassLoading, useAllowInnerContextOptions, creatorConfig.allowExperimentalOptions, useClassFilter, useArguments, allowedLanguages, useOptions, useFileSystem, useInternalFileSystem, creatorConfig.logHandler, useAllowCreateProcess, useProcessHandler, useEnvironmentAccess, useCustomEnvironment, useTimeZone, creatorConfig.limits, creatorConfig.hostClassLoader, useAllowHostAccess, creatorConfig.allowValueSharing, false, config, onCancelledRunnable, onExitedRunnable, onClosedRunnable);
            PolyglotContextImpl polyglotContextImpl = creator.context;
            synchronized (polyglotContextImpl) {
                impl = new PolyglotContextImpl(creator, innerConfig);
                impl.api = creator.getImpl().getAPIAccess().newContext(creator.getImpl().contextDispatch, impl, creator.context.engine.api);
                creator.context.addChildContext(impl);
            }
            polyglotContextImpl = impl;
            synchronized (polyglotContextImpl) {
                impl.initializeContextLocals();
                impl.notifyContextCreated();
            }
            if (initializeCreatorContext) {
                impl.initializeInnerContextLanguage(creator.language.getId());
            }
            return impl.creatorTruffleContext;
        }

        private static boolean inheritAccess(boolean inheritAccess, Boolean newPrivilege, boolean creatorPrivilege) {
            if (newPrivilege != null) {
                return newPrivilege != false && creatorPrivilege;
            }
            return inheritAccess && creatorPrivilege;
        }

        @Override
        public boolean isCreateThreadAllowed(Object polyglotLanguageContext) {
            return ((PolyglotLanguageContext)polyglotLanguageContext).context.config.createThreadAllowed;
        }

        @Override
        public Thread createThread(Object polyglotLanguageContext, Runnable runnable, Object innerContextImpl, ThreadGroup group, long stackSize) {
            if (!this.isCreateThreadAllowed(polyglotLanguageContext)) {
                throw PolyglotEngineException.illegalState("Creating threads is not allowed.");
            }
            PolyglotLanguageContext threadContext = (PolyglotLanguageContext)polyglotLanguageContext;
            if (innerContextImpl != null) {
                PolyglotContextImpl innerContext = (PolyglotContextImpl)innerContextImpl;
                threadContext = innerContext.getContext(threadContext.language);
            }
            PolyglotThread newThread = new PolyglotThread(threadContext, runnable, group, stackSize);
            threadContext.context.checkMultiThreadedAccess(newThread);
            return newThread;
        }

        @Override
        public RuntimeException wrapHostException(Node location, Object languageContext, Throwable exception) {
            PolyglotContextImpl context = ((PolyglotLanguageContext)languageContext).context;
            return context.engine.host.toHostException(context.getHostContextImpl(), exception);
        }

        @Override
        public boolean isHostException(Object languageContext, Throwable exception) {
            PolyglotContextImpl context = ((PolyglotLanguageContext)languageContext).context;
            return context.engine.host.isHostException(exception);
        }

        @Override
        public Throwable asHostException(Object languageContext, Throwable exception) {
            PolyglotContextImpl context = ((PolyglotLanguageContext)languageContext).context;
            Throwable host = context.engine.host.unboxHostException(exception);
            if (host == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new IllegalArgumentException("Provided value not a host exception.");
            }
            return host;
        }

        @Override
        public Object getCurrentHostContext() {
            PolyglotContextImpl polyglotContext = PolyglotFastThreadLocals.getContext(null);
            return polyglotContext == null ? null : polyglotContext.getHostContext();
        }

        @Override
        public Object getPolyglotBindingsForLanguage(Object polyglotLanguageContext) {
            return ((PolyglotLanguageContext)polyglotLanguageContext).getPolyglotGuestBindings();
        }

        @Override
        public Object findMetaObjectForLanguage(Object polyglotLanguageContext, Object value2) {
            InteropLibrary lib = InteropLibrary.getFactory().getUncached(value2);
            if (lib.hasMetaObject(value2)) {
                try {
                    return lib.getMetaObject(value2);
                }
                catch (UnsupportedMessageException e) {
                    throw CompilerDirectives.shouldNotReachHere("Unexpected unsupported message.", e);
                }
            }
            return null;
        }

        @Override
        public PolyglotException wrapGuestException(String languageId, Throwable e) {
            PolyglotContextImpl pc = PolyglotFastThreadLocals.getContext(null);
            if (pc == null) {
                return null;
            }
            PolyglotLanguage language = pc.engine.findLanguage(null, languageId, null, true, true);
            PolyglotLanguageContext languageContext = pc.getContextInitialized(language, null);
            return PolyglotImpl.guestToHostException(languageContext, e, true);
        }

        @Override
        public PolyglotException wrapGuestException(Object polyglotObject, Throwable e) {
            if (polyglotObject instanceof PolyglotContextImpl) {
                PolyglotContextImpl polyglotContext = (PolyglotContextImpl)polyglotObject;
                PolyglotLanguageContext hostLanguageContext = polyglotContext.getHostContext();
                if (polyglotContext.state.isInvalidOrClosed()) {
                    return PolyglotImpl.guestToHostException(hostLanguageContext, e, false);
                }
                PolyglotLanguage language = polyglotContext.getHostContext().language;
                PolyglotLanguageContext languageContext = polyglotContext.getContextInitialized(language, null);
                return PolyglotImpl.guestToHostException(languageContext, e, true);
            }
            if (polyglotObject instanceof PolyglotEngineImpl) {
                return PolyglotImpl.guestToHostException((PolyglotEngineImpl)polyglotObject, e);
            }
            return PolyglotImpl.guestToHostException((PolyglotImpl)polyglotObject, e);
        }

        @Override
        public Set<? extends Class<?>> getProvidedTags(LanguageInfo language) {
            return ((LanguageCache)NODES.getLanguageCache(language)).getProvidedTags();
        }

        @Override
        public <T> T getOrCreateRuntimeData(Object layer) {
            PolyglotEngineImpl engine;
            PolyglotSharingLayer useLayer = (PolyglotSharingLayer)layer;
            PolyglotEngineImpl polyglotEngineImpl = engine = useLayer != null ? useLayer.engine : null;
            if (engine == null) {
                engine = PolyglotEngineImpl.getFallbackEngine();
            }
            return (T)engine.runtimeData;
        }

        @Override
        public OptionValues getEngineOptionValues(Object polyglotEngine) {
            return ((PolyglotEngineImpl)polyglotEngine).engineOptionValues;
        }

        public Collection<CallTarget> findCallTargets(Object polyglotEngine) {
            return INSTRUMENT.getLoadedCallTargets(((PolyglotEngineImpl)polyglotEngine).instrumentationHandler);
        }

        @Override
        public void preinitializeContext(Object polyglotEngine) {
            ((PolyglotEngineImpl)polyglotEngine).preInitialize();
        }

        @Override
        public void finalizeStore(Object polyglotEngine) {
            ((PolyglotEngineImpl)polyglotEngine).finalizeStore();
        }

        @Override
        public Object getEngineLock(Object polyglotEngine) {
            return ((PolyglotEngineImpl)polyglotEngine).lock;
        }

        @Override
        public boolean isInternal(FileSystem fs) {
            return FileSystems.isInternal(fs);
        }

        @Override
        public boolean hasAllAccess(FileSystem fs) {
            return FileSystems.hasAllAccess(fs);
        }

        @Override
        public boolean hasNoAccess(FileSystem fs) {
            return FileSystems.hasNoAccess(fs);
        }

        @Override
        public boolean isInternal(TruffleFile file) {
            Object fsContext = LANGUAGE.getFileSystemContext(file);
            Object engineObject = LANGUAGE.getFileSystemEngineObject(fsContext);
            if (engineObject instanceof PolyglotLanguageContext) {
                return fsContext == ((PolyglotLanguageContext)engineObject).getInternalFileSystemContext();
            }
            return false;
        }

        @Override
        public void addToHostClassPath(Object polyglotLanguageContext, TruffleFile entry) {
            PolyglotContextImpl context = ((PolyglotLanguageContext)polyglotLanguageContext).context;
            if (!context.config.hostLookupAllowed) {
                context.engine.host.throwHostLanguageException("Host class access is not allowed.");
            }
            if (!context.config.hostClassLoadingAllowed) {
                context.engine.host.throwHostLanguageException("Host class loading is not allowed.");
            }
            context.engine.host.addToHostClassPath(context.getHostContextImpl(), entry);
        }

        @Override
        public String getLanguageHome(LanguageInfo languageInfo) {
            return ((LanguageCache)NODES.getLanguageCache(languageInfo)).getLanguageHome();
        }

        @Override
        public boolean isInstrumentExceptionsAreThrown(Object polyglotInstrument) {
            OptionValuesImpl engineOptionValues = EngineImpl.getEngine((Object)polyglotInstrument).engineOptionValues;
            return EngineImpl.areAssertionsEnabled() && !engineOptionValues.hasBeenSet(PolyglotEngineOptions.InstrumentExceptionsAreThrown) || engineOptionValues.get(PolyglotEngineOptions.InstrumentExceptionsAreThrown) != false;
        }

        private static boolean areAssertionsEnabled() {
            boolean assertsEnabled = false;
            if (!$assertionsDisabled) {
                assertsEnabled = true;
                if (!true) {
                    throw new AssertionError();
                }
            }
            return assertsEnabled;
        }

        @Override
        public Object createDefaultLoggerCache() {
            return PolyglotLoggers.LoggerCache.DEFAULT;
        }

        @Override
        public Object getContextLoggerCache(Object polyglotLanguageContext) {
            return ((PolyglotLanguageContext)polyglotLanguageContext).context.getOrCreateContextLoggers();
        }

        @Override
        public Handler getLogHandler(Object loggerCache) {
            return ((PolyglotLoggers.LoggerCache)loggerCache).getLogHandler();
        }

        @Override
        public LogRecord createLogRecord(Object loggerCache, Level level, String loggerName, String message, String className, String methodName, Object[] parameters, Throwable thrown) {
            return ((PolyglotLoggers.LoggerCache)loggerCache).createLogRecord(level, loggerName, message, className, methodName, parameters, thrown);
        }

        @Override
        public Object getOuterContext(Object polyglotContext) {
            return EngineImpl.getOuterContext((PolyglotContextImpl)polyglotContext);
        }

        static PolyglotContextImpl getOuterContext(PolyglotContextImpl context) {
            PolyglotContextImpl res = context;
            if (res != null) {
                while (res.parent != null) {
                    res = res.parent;
                }
            }
            return res;
        }

        @Override
        public Map<String, Level> getLogLevels(Object loggerCache) {
            return ((PolyglotLoggers.LoggerCache)loggerCache).getLogLevels();
        }

        @Override
        public Object getLoggerOwner(Object loggerCache) {
            return ((PolyglotLoggers.LoggerCache)loggerCache).getOwner();
        }

        @Override
        public Set<String> getLanguageIds() {
            return LanguageCache.languages().keySet();
        }

        @Override
        public Set<String> getInstrumentIds() {
            HashSet<String> ids = new HashSet<String>();
            for (InstrumentCache cache : InstrumentCache.load()) {
                ids.add(cache.getId());
            }
            return ids;
        }

        @Override
        public Set<String> getInternalIds() {
            return PolyglotLoggers.getInternalIds();
        }

        @Override
        public Object asHostObject(Object languageContext, Object obj) {
            PolyglotContextImpl context = ((PolyglotLanguageContext)languageContext).context;
            assert (this.isHostObject(languageContext, obj));
            return context.engine.host.unboxHostObject(obj);
        }

        @Override
        public boolean isHostFunction(Object languageContext, Object obj) {
            PolyglotContextImpl context = ((PolyglotLanguageContext)languageContext).context;
            return context.engine.host.isHostFunction(obj);
        }

        @Override
        public boolean isHostObject(Object languageContext, Object obj) {
            PolyglotContextImpl context = ((PolyglotLanguageContext)languageContext).context;
            return context.engine.host.isHostObject(obj);
        }

        @Override
        public boolean isHostSymbol(Object languageContext, Object obj) {
            PolyglotContextImpl context = ((PolyglotLanguageContext)languageContext).context;
            return context.engine.host.isHostSymbol(obj);
        }

        @Override
        public <S> S lookupService(Object polyglotLanguageContext, LanguageInfo language, LanguageInfo accessingLanguage, Class<S> type) {
            PolyglotLanguage lang = ((PolyglotLanguageContext)polyglotLanguageContext).context.engine.idToLanguage.get(language.getId());
            if (!lang.cache.supportsService(type)) {
                return null;
            }
            PolyglotLanguageContext context = ((PolyglotLanguageContext)polyglotLanguageContext).context.getContext(lang);
            PolyglotLanguage accessingLang = ((PolyglotLanguageContext)polyglotLanguageContext).context.engine.idToLanguage.get(accessingLanguage.getId());
            context.ensureCreated(accessingLang);
            return context.lookupService(type);
        }

        @Override
        public TruffleLogger getLogger(Object polyglotInstrument, String loggerName) {
            PolyglotInstrument instrument = (PolyglotInstrument)polyglotInstrument;
            String id = instrument.getId();
            PolyglotEngineImpl engine = EngineImpl.getEngine(polyglotInstrument);
            Object loggerCache = engine.getOrCreateEngineLoggers();
            return LANGUAGE.getLogger(id, loggerName, loggerCache);
        }

        @Override
        public <T extends TruffleLanguage<C>, C> TruffleLanguage.ContextReference<C> createContextReference(Class<T> languageClass) {
            return PolyglotFastThreadLocals.createContextReference(languageClass);
        }

        @Override
        public <T extends TruffleLanguage<?>> TruffleLanguage.LanguageReference<T> createLanguageReference(Class<T> targetLanguageClass) {
            return PolyglotFastThreadLocals.createLanguageReference(targetLanguageClass);
        }

        @Override
        public FileSystem getFileSystem(Object polyglotContext) {
            return ((PolyglotContextImpl)polyglotContext).config.fileSystem;
        }

        @Override
        public int getAsynchronousStackDepth(Object polyglotLanguageInstance) {
            return ((PolyglotLanguageInstance)polyglotLanguageInstance).getEngine().getAsynchronousStackDepth();
        }

        @Override
        public void setAsynchronousStackDepth(Object polyglotInstrument, int depth) {
            EngineImpl.getEngine(polyglotInstrument).setAsynchronousStackDepth((PolyglotInstrument)polyglotInstrument, depth);
        }

        @Override
        public boolean isCreateProcessAllowed(Object polylgotLanguageContext) {
            return ((PolyglotLanguageContext)polylgotLanguageContext).context.config.createProcessAllowed;
        }

        @Override
        public Map<String, String> getProcessEnvironment(Object polyglotLanguageContext) {
            return ((PolyglotLanguageContext)polyglotLanguageContext).context.config.getEnvironment();
        }

        @Override
        public Process createSubProcess(Object polyglotLanguageContext, List<String> cmd, String cwd, Map<String, String> environment, boolean redirectErrorStream, ProcessHandler.Redirect inputRedirect, ProcessHandler.Redirect outputRedirect, ProcessHandler.Redirect errorRedirect) throws IOException {
            PolyglotLanguageContext languageContext = (PolyglotLanguageContext)polyglotLanguageContext;
            OutputStream stdOut = languageContext.getImpl().getIO().getOutputStream(outputRedirect);
            OutputStream stdErr = languageContext.getImpl().getIO().getOutputStream(errorRedirect);
            ProcessHandler.Redirect useOutputRedirect = stdOut == null ? outputRedirect : ProcessHandler.Redirect.PIPE;
            ProcessHandler.Redirect useErrorRedirect = stdErr == null ? errorRedirect : ProcessHandler.Redirect.PIPE;
            ProcessHandler.ProcessCommand command = languageContext.getImpl().getIO().newProcessCommand(cmd, cwd, environment, redirectErrorStream, inputRedirect, useOutputRedirect, useErrorRedirect);
            Process process = languageContext.context.config.processHandler.start(command);
            return ProcessHandlers.decorate(languageContext, cmd, process, stdOut, stdErr);
        }

        @Override
        public boolean hasDefaultProcessHandler(Object polyglotLanguageContext) {
            PolyglotLanguageContext context = (PolyglotLanguageContext)polyglotLanguageContext;
            return context.getImpl().getRootImpl().isDefaultProcessHandler(context.context.config.processHandler);
        }

        @Override
        public ProcessHandler.Redirect createRedirectToOutputStream(Object polyglotLanguageContext, OutputStream stream) {
            return ((PolyglotLanguageContext)polyglotLanguageContext).getImpl().getIO().createRedirectToStream(stream);
        }

        @Override
        public boolean isIOSupported() {
            return PolyglotEngineImpl.ALLOW_IO;
        }

        @Override
        public boolean isCreateProcessSupported() {
            return PolyglotEngineImpl.ALLOW_CREATE_PROCESS;
        }

        @Override
        public String getUnparsedOptionValue(OptionValues optionValues, OptionKey<?> optionKey) {
            if (!(optionValues instanceof OptionValuesImpl)) {
                throw new IllegalArgumentException(String.format("Only %s is supported.", OptionValuesImpl.class.getName()));
            }
            return ((OptionValuesImpl)optionValues).getUnparsedOptionValue(optionKey);
        }

        @Override
        public String getRelativePathInLanguageHome(TruffleFile truffleFile) {
            return FileSystems.getRelativePathInLanguageHome(truffleFile);
        }

        @Override
        public void onSourceCreated(com.oracle.truffle.api.source.Source source) {
            PolyglotContextImpl currentContext = PolyglotFastThreadLocals.getContext(null);
            if (currentContext != null && currentContext.sourcesToInvalidate != null) {
                currentContext.sourcesToInvalidate.add(source);
            }
        }

        @Override
        public void registerOnDispose(Object engineObject, Closeable closeable) {
            if (!(engineObject instanceof PolyglotLanguageContext)) {
                throw CompilerDirectives.shouldNotReachHere("EngineObject must be PolyglotLanguageContext.");
            }
            ((PolyglotLanguageContext)engineObject).context.registerOnDispose(closeable);
        }

        @Override
        public String getReinitializedPath(TruffleFile truffleFile) {
            FileSystem fs = LANGUAGE.getFileSystem(truffleFile);
            Path path = LANGUAGE.getPath(truffleFile);
            return ((FileSystems.PreInitializeContextFileSystem)fs).pathToString(path);
        }

        @Override
        public URI getReinitializedURI(TruffleFile truffleFile) {
            FileSystem fs = LANGUAGE.getFileSystem(truffleFile);
            Path path = LANGUAGE.getPath(truffleFile);
            return ((FileSystems.PreInitializeContextFileSystem)fs).absolutePathtoURI(path);
        }

        @Override
        public boolean initializeLanguage(Object polyglotLanguageContext, LanguageInfo targetLanguage) {
            PolyglotLanguage targetPolyglotLanguage = ((PolyglotLanguageContext)polyglotLanguageContext).context.engine.idToLanguage.get(targetLanguage.getId());
            PolyglotLanguageContext targetLanguageContext = ((PolyglotLanguageContext)polyglotLanguageContext).context.getContext(targetPolyglotLanguage);
            PolyglotLanguage accessingPolyglotLanguage = ((PolyglotLanguageContext)polyglotLanguageContext).language;
            try {
                targetLanguageContext.checkAccess(accessingPolyglotLanguage);
            }
            catch (PolyglotEngineException notAccessible) {
                if (notAccessible.e instanceof IllegalArgumentException) {
                    throw new SecurityException(notAccessible.e.getMessage());
                }
                throw notAccessible;
            }
            return targetLanguageContext.ensureInitialized(accessingPolyglotLanguage);
        }

        @Override
        public boolean skipEngineValidation(RootNode rootNode) {
            return rootNode instanceof HostToGuestRootNode || rootNode instanceof PolyglotThread.ThreadSpawnRootNode;
        }

        @Override
        public AssertionError invalidSharingError(Node node, Object previousSharingLayer, Object newSharingLayer) throws AssertionError {
            return PolyglotSharingLayer.invalidSharingError(node, (PolyglotSharingLayer)previousSharingLayer, (PolyglotSharingLayer)newSharingLayer);
        }

        @Override
        public <T> ContextLocal<T> createInstrumentContextLocal(Object factory) {
            return PolyglotLocals.createInstrumentContextLocal(factory);
        }

        @Override
        public <T> ContextThreadLocal<T> createInstrumentContextThreadLocal(Object factory) {
            return PolyglotLocals.createInstrumentContextThreadLocal(factory);
        }

        @Override
        public <T> ContextLocal<T> createLanguageContextLocal(Object factory) {
            return PolyglotLocals.createLanguageContextLocal(factory);
        }

        @Override
        public <T> ContextThreadLocal<T> createLanguageContextThreadLocal(Object factory) {
            return PolyglotLocals.createLanguageContextThreadLocal(factory);
        }

        @Override
        public void initializeInstrumentContextLocal(List<? extends ContextLocal<?>> locals, Object polyglotInstrument) {
            PolyglotLocals.initializeInstrumentContextLocals(locals, (PolyglotInstrument)polyglotInstrument);
        }

        @Override
        public void initializeInstrumentContextThreadLocal(List<? extends ContextThreadLocal<?>> local, Object polyglotInstrument) {
            PolyglotLocals.initializeInstrumentContextThreadLocals(local, (PolyglotInstrument)polyglotInstrument);
        }

        @Override
        public boolean isPolyglotObject(Object polyglotObject) {
            return PolyglotImpl.getInstance() == polyglotObject;
        }

        @Override
        public void initializeLanguageContextLocal(List<? extends ContextLocal<?>> locals, Object polyglotLanguageInstance) {
            PolyglotLocals.initializeLanguageContextLocals(locals, (PolyglotLanguageInstance)polyglotLanguageInstance);
        }

        @Override
        public void initializeLanguageContextThreadLocal(List<? extends ContextThreadLocal<?>> local, Object polyglotLanguageInstance) {
            PolyglotLocals.initializeLanguageContextThreadLocals(local, (PolyglotLanguageInstance)polyglotLanguageInstance);
        }

        @Override
        public OptionValues getInstrumentContextOptions(Object polyglotInstrument, Object polyglotContext) {
            PolyglotInstrument instrument = (PolyglotInstrument)polyglotInstrument;
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            return context.getInstrumentContextOptions(instrument);
        }

        @Override
        public boolean isContextClosed(Object polyglotContext) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            PolyglotContextImpl.State localContextState = context.state;
            return localContextState.isInvalidOrClosed();
        }

        @Override
        public boolean isContextCancelling(Object polyglotContext) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            PolyglotContextImpl.State localContextState = context.state;
            return localContextState.isCancelling();
        }

        @Override
        public boolean isContextExiting(Object polyglotContext) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            PolyglotContextImpl.State contextState = context.state;
            return contextState.isExiting();
        }

        @Override
        public Future<Void> pause(Object polyglotContext) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            return context.pause();
        }

        @Override
        public void resume(Object polyglotContext, Future<Void> pauseFuture) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            context.resume(pauseFuture);
        }

        @Override
        public boolean isContextActive(Object polyglotContext) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            return context.isActive(Thread.currentThread());
        }

        @Override
        public void clearExplicitContextStack(Object polyglotContext) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            context.clearExplicitContextStack();
        }

        @Override
        public void initiateCancelOrExit(Object polyglotContext, boolean exit, int exitCode, boolean resourceLimit, String message) {
            PolyglotContextImpl context = (PolyglotContextImpl)polyglotContext;
            context.initiateCancelOrExit(exit, exitCode, resourceLimit, message);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @CompilerDirectives.TruffleBoundary
        public void closeContext(Object impl, boolean force, Node closeLocation, boolean resourceExhaused, String resourceExhausedReason) {
            PolyglotContextImpl context = (PolyglotContextImpl)impl;
            if (force) {
                boolean isActive = this.isContextActive(context);
                boolean entered = this.isContextEntered(context);
                if (isActive && !entered) {
                    throw PolyglotEngineException.illegalState(String.format("The context is currently active on the current thread but another different context is entered as top-most context. Leave or close the top-most context first or close the context on a separate thread to resolve this problem.", new Object[0]));
                }
                context.cancel(resourceExhaused, resourceExhausedReason);
                if (entered) {
                    TruffleSafepoint.pollHere(closeLocation != null ? closeLocation : context.uncachedLocation);
                }
            } else {
                PolyglotContextImpl polyglotContextImpl = context;
                synchronized (polyglotContextImpl) {
                    PolyglotContextImpl.State localContextState = context.state;
                    if (context.isActiveNotCancelled(false) && !localContextState.isCancelling() && !localContextState.isExiting()) {
                        throw new IllegalStateException("The context is currently active and cannot be closed. Make sure no thread is running or call closeCancelled on the context to resolve this.");
                    }
                }
                context.closeImpl(true);
                context.finishCleanup();
            }
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void closeContext(Object impl, boolean force, boolean resourceExhaused, String resourceExhausedReason) {
            PolyglotContextImpl context = (PolyglotContextImpl)impl;
            if (force) {
                context.cancel(resourceExhaused, resourceExhausedReason);
            } else {
                context.closeAndMaybeWait(false, null);
            }
        }

        @Override
        public void closeEngine(Object polyglotEngine, boolean force) {
            PolyglotEngineImpl engine = (PolyglotEngineImpl)polyglotEngine;
            engine.ensureClosed(force, false, false);
        }

        @Override
        public <T, G> Iterator<T> mergeHostGuestFrames(Object instrumentEnv, StackTraceElement[] hostStack, Iterator<G> guestFrames, boolean inHostLanguage, Function<StackTraceElement, T> hostFrameConvertor, Function<G, T> guestFrameConvertor) {
            PolyglotInstrument instrument = (PolyglotInstrument)INSTRUMENT.getPolyglotInstrument(instrumentEnv);
            return new PolyglotExceptionImpl.MergedHostGuestIterator<T, G>(instrument.engine, hostStack, guestFrames, inHostLanguage, hostFrameConvertor, guestFrameConvertor);
        }

        @Override
        public Object createHostAdapterClass(Object languageContext, Object[] types, Object classOverrides) {
            CompilerAsserts.neverPartOfCompilation();
            PolyglotContextImpl context = ((PolyglotLanguageContext)languageContext).context;
            return context.engine.host.createHostAdapter(context.getHostContextImpl(), types, classOverrides);
        }

        @Override
        public long calculateContextHeapSize(Object polyglotContext, long stopAtBytes, AtomicBoolean cancelled) {
            return ((PolyglotContextImpl)polyglotContext).calculateHeapSize(stopAtBytes, cancelled);
        }

        @Override
        public Future<Void> submitThreadLocal(Object polyglotContext, Object sourcePolyglotObject, Thread[] threads, ThreadLocalAction action2, boolean needsEnter) {
            String componentId;
            if (sourcePolyglotObject instanceof PolyglotInstrument) {
                componentId = ((PolyglotInstrument)sourcePolyglotObject).getId();
            } else if (sourcePolyglotObject instanceof PolyglotLanguageContext) {
                componentId = ((PolyglotLanguageContext)sourcePolyglotObject).language.getId();
            } else {
                throw CompilerDirectives.shouldNotReachHere("Invalid source component");
            }
            return ((PolyglotContextImpl)polyglotContext).threadLocalActions.submit(threads, componentId, action2, needsEnter);
        }

        @Override
        public Object getContext(Object polyglotLanguageContext) {
            return ((PolyglotLanguageContext)polyglotLanguageContext).context;
        }

        @Override
        public ClassLoader getStaticObjectClassLoader(Object polyglotLanguageInstance, Class<?> referenceClass) {
            return ((PolyglotLanguageInstance)polyglotLanguageInstance).staticObjectClassLoaders.get(referenceClass);
        }

        @Override
        public void setStaticObjectClassLoader(Object polyglotLanguageInstance, Class<?> referenceClass, ClassLoader cl) {
            ((PolyglotLanguageInstance)polyglotLanguageInstance).staticObjectClassLoaders.put(referenceClass, cl);
        }

        @Override
        public ConcurrentHashMap<Pair<Class<?>, Class<?>>, Object> getGeneratorCache(Object polyglotLanguageInstance) {
            return ((PolyglotLanguageInstance)polyglotLanguageInstance).generatorCache;
        }

        @Override
        public boolean areStaticObjectSafetyChecksRelaxed(Object polyglotLanguageInstance) {
            return ((PolyglotLanguageInstance)polyglotLanguageInstance).getEngine().getEngineOptionValues().get(PolyglotEngineOptions.RelaxStaticObjectSafetyChecks);
        }

        @Override
        public String getStaticObjectStorageStrategy(Object polyglotLanguageInstance) {
            return ((PolyglotLanguageInstance)polyglotLanguageInstance).getEngine().getEngineOptionValues().get(PolyglotEngineOptions.StaticObjectStorageStrategy).name();
        }

        @Override
        public void exitContext(Object impl, Node exitLocation, int exitCode) {
            PolyglotContextImpl context = (PolyglotContextImpl)impl;
            context.closeExited(exitLocation, exitCode);
        }

        @Override
        public Throwable getPolyglotExceptionCause(Object polyglotExceptionImpl) {
            return ((PolyglotExceptionImpl)polyglotExceptionImpl).exception;
        }

        @Override
        public Object getPolyglotExceptionContext(Object polyglotExceptionImpl) {
            return ((PolyglotExceptionImpl)polyglotExceptionImpl).context;
        }

        @Override
        public Object getPolyglotExceptionEngine(Object polyglotExceptionImpl) {
            return ((PolyglotExceptionImpl)polyglotExceptionImpl).engine;
        }

        @Override
        public boolean isCancelExecution(Throwable throwable) {
            return throwable instanceof PolyglotEngineImpl.CancelExecution;
        }

        @Override
        public boolean isExitException(Throwable throwable) {
            return throwable instanceof PolyglotContextImpl.ExitException;
        }

        @Override
        public boolean isInterruptExecution(Throwable throwable) {
            return throwable instanceof PolyglotEngineImpl.InterruptExecution;
        }

        @Override
        public boolean isResourceLimitCancelExecution(Throwable cancelExecution) {
            return ((PolyglotEngineImpl.CancelExecution)cancelExecution).isResourceLimit();
        }

        @Override
        public boolean isPolyglotEngineException(Throwable throwable) {
            return throwable instanceof PolyglotEngineException;
        }

        @Override
        public RuntimeException getPolyglotEngineExceptionCause(Throwable polyglotEngineException) {
            return ((PolyglotEngineException)polyglotEngineException).e;
        }

        @Override
        public RuntimeException createPolyglotEngineException(RuntimeException cause) {
            return new PolyglotEngineException(cause);
        }

        @Override
        public int getExitExceptionExitCode(Throwable exitException) {
            return ((PolyglotContextImpl.ExitException)exitException).getExitCode();
        }

        @Override
        public SourceSection getCancelExecutionSourceLocation(Throwable cancelExecution) {
            return ((PolyglotEngineImpl.CancelExecution)cancelExecution).getSourceLocation();
        }

        @Override
        public ThreadDeath createCancelExecution(SourceSection sourceSection, String message, boolean resourceLimit) {
            return new PolyglotEngineImpl.CancelExecution(sourceSection, message, resourceLimit);
        }

        @Override
        public SourceSection getExitExceptionSourceLocation(Throwable exitException) {
            return ((PolyglotContextImpl.ExitException)exitException).getSourceLocation();
        }

        @Override
        public ThreadDeath createExitException(SourceSection sourceSection, String message, int exitCode) {
            return new PolyglotContextImpl.ExitException(sourceSection, exitCode, message);
        }

        @Override
        public Throwable createInterruptExecution(SourceSection sourceSection) {
            return new PolyglotEngineImpl.InterruptExecution(sourceSection);
        }

        @Override
        public Map<String, String> readOptionsFromSystemProperties(Map<String, String> options) {
            return PolyglotEngineImpl.readOptionsFromSystemProperties(options);
        }

        @Override
        public AbstractPolyglotImpl.AbstractHostLanguageService getHostService(Object polyglotEngineImpl) {
            assert (polyglotEngineImpl instanceof PolyglotEngineImpl);
            return ((PolyglotEngineImpl)polyglotEngineImpl).host;
        }

        @Override
        public Handler getEngineLogHandler(Object polyglotEngineImpl) {
            return ((PolyglotEngineImpl)polyglotEngineImpl).logHandler;
        }

        @Override
        public Handler getContextLogHandler(Object polyglotContextImpl) {
            return ((PolyglotContextImpl)polyglotContextImpl).config.logHandler;
        }

        @Override
        public LogRecord createLogRecord(Level level, String loggerName, String message, String className, String methodName, Object[] parameters, Throwable thrown, String formatKind) {
            return PolyglotLoggers.createLogRecord(level, loggerName, message, className, methodName, parameters, thrown, formatKind);
        }

        @Override
        public String getFormatKind(LogRecord logRecord) {
            return PolyglotLoggers.getFormatKind(logRecord);
        }

        @Override
        public boolean isPolyglotThread(Thread thread) {
            return thread instanceof PolyglotThread;
        }

        @Override
        public Object getHostNull() {
            return HOST.getHostNull();
        }

        @Override
        public Object getPolyglotSharingLayer(Object polyglotLanguageInstance) {
            return ((PolyglotLanguageInstance)polyglotLanguageInstance).sharing;
        }

        @Override
        public boolean getNeedsAllEncodings() {
            return LanguageCache.getNeedsAllEncodings();
        }

        @Override
        public boolean requireLanguageWithAllEncodings(Object encoding) {
            PolyglotContextImpl context = PolyglotFastThreadLocals.getContext(null);
            if (context == null) {
                return true;
            }
            boolean needsAllEncodingsFound = false;
            for (PolyglotLanguage language : context.engine.languages) {
                if (language == null || language.isFirstInstance() || !language.cache.isNeedsAllEncodings()) continue;
                needsAllEncodingsFound = true;
                break;
            }
            if (!needsAllEncodingsFound) {
                throw new AssertionError((Object)String.format("The encoding %s for a new TruffleString was requested but was not configured by any language. In order to use this encoding configure your language using %s.%s(...needsAllEncodings=true).", encoding, TruffleLanguage.class.getSimpleName(), TruffleLanguage.Registration.class.getSimpleName()));
            }
            return true;
        }

        @Override
        public Object getGuestToHostCodeCache(Object polyglotContextImpl) {
            return ((PolyglotContextImpl)polyglotContextImpl).getHostContext().getLanguageInstance().getGuestToHostCodeCache();
        }

        @Override
        public Object installGuestToHostCodeCache(Object polyglotContextImpl, Object cache) {
            return ((PolyglotContextImpl)polyglotContextImpl).getHostContext().getLanguageInstance().installGuestToHostCodeCache(cache);
        }

        @Override
        public AutoCloseable createPolyglotThreadScope() {
            return PolyglotImpl.getInstance().getRootImpl().createThreadScope();
        }

        @Override
        public Engine getPolyglotEngineAPI(Object polyglotEngineImpl) {
            return ((PolyglotEngineImpl)polyglotEngineImpl).api;
        }

        @Override
        public Context getPolyglotContextAPI(Object polyglotContextImpl) {
            return ((PolyglotContextImpl)polyglotContextImpl).api;
        }

        @Override
        public EncapsulatingNodeReference getEncapsulatingNodeReference(boolean invalidateOnNull) {
            return PolyglotFastThreadLocals.getEncapsulatingNodeReference(invalidateOnNull);
        }

        @Override
        public Thread createInstrumentSystemThread(Object polyglotInstrument, Runnable runnable, ThreadGroup threadGroup) {
            return new SystemThread.InstrumentSystemThread((PolyglotInstrument)polyglotInstrument, runnable, threadGroup);
        }

        @Override
        public Thread createLanguageSystemThread(Object polyglotLanguageContext, Runnable runnable, ThreadGroup threadGroup) {
            PolyglotLanguageContext languageContext = (PolyglotLanguageContext)polyglotLanguageContext;
            if (PolyglotContextImpl.requireContext() != languageContext.context) {
                throw new IllegalStateException("Not entered in an Env's context.");
            }
            return new SystemThread.LanguageSystemThread(languageContext, runnable, threadGroup);
        }
    }
}

