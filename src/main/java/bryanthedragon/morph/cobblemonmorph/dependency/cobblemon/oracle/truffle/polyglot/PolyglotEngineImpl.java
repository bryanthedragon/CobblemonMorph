
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.InstrumentInfo;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.dsl.SpecializationStatistics;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.impl.DefaultTruffleRuntime;
import com.oracle.truffle.api.impl.DispatchOutputStream;
import com.oracle.truffle.api.instrumentation.ContextsListener;
import com.oracle.truffle.api.instrumentation.ThreadsListener;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.polyglot.DefaultPolyglotHostService;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.FileSystems;
import com.oracle.truffle.polyglot.ImageBuildTimeOptions;
import com.oracle.truffle.polyglot.InstrumentCache;
import com.oracle.truffle.polyglot.LanguageCache;
import com.oracle.truffle.polyglot.OptionValuesImpl;
import com.oracle.truffle.polyglot.PolyglotContextConfig;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineException;
import com.oracle.truffle.polyglot.PolyglotEngineOptions;
import com.oracle.truffle.polyglot.PolyglotEngineOptionsOptionDescriptors;
import com.oracle.truffle.polyglot.PolyglotFastThreadLocals;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotInstrument;
import com.oracle.truffle.polyglot.PolyglotLanguage;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotLimits;
import com.oracle.truffle.polyglot.PolyglotLocals;
import com.oracle.truffle.polyglot.PolyglotLoggers;
import com.oracle.truffle.polyglot.PolyglotSharingLayer;
import com.oracle.truffle.polyglot.PolyglotThreadInfo;
import com.oracle.truffle.polyglot.SystemThread;
import com.oracle.truffle.polyglot.WeakAssumedValue;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.time.Duration;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Handler;
import java.util.logging.Level;
import org.graalvm.collections.EconomicSet;
import org.graalvm.collections.Equivalence;
import org.graalvm.home.HomeFinder;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.EnvironmentAccess;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Instrument;
import org.graalvm.polyglot.Language;
import org.graalvm.polyglot.PolyglotAccess;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.io.FileSystem;
import org.graalvm.polyglot.io.MessageTransport;
import org.graalvm.polyglot.io.ProcessHandler;

final class PolyglotEngineImpl
implements PolyglotImpl.VMObject {
    static final int HOST_LANGUAGE_INDEX = 0;
    static final String HOST_LANGUAGE_ID = "host";
    static final String ENGINE_ID = "engine";
    static final String OPTION_GROUP_ENGINE = "engine";
    static final String OPTION_GROUP_LOG = "log";
    static final String OPTION_GROUP_IMAGE_BUILD_TIME = "image-build-time";
    static final String LOG_FILE_OPTION = "log.file";
    private static final Set<String> RESERVED_IDS = new HashSet<String>(Arrays.asList("host", "graal", "truffle", "language", "instrument", "graalvm", "context", "polyglot", "compiler", "vm", "file", "engine", "log", "image-build-time"));
    private static final boolean DEBUG_MISSING_CLOSE = Boolean.getBoolean("polyglotimpl.DebugMissingClose");
    static final PolyglotLocals.LocalLocation[] EMPTY_LOCATIONS = new PolyglotLocals.LocalLocation[0];
    final Object lock = new Object();
    private Thread closingThread;
    final Object instrumentationHandler;
    final String[] permittedLanguages;
    final PolyglotImpl impl;
    DispatchOutputStream out;
    DispatchOutputStream err;
    InputStream in;
    Engine api;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    final PolyglotLanguage[] languages;
    final Map<String, PolyglotLanguage> idToLanguage;
    final Map<String, PolyglotLanguage> classToLanguage;
    final Map<String, Language> idToPublicLanguage;
    final Map<String, LanguageInfo> idToInternalLanguageInfo;
    final Map<String, PolyglotInstrument> idToInstrument;
    final Map<String, Instrument> idToPublicInstrument;
    final Map<String, InstrumentInfo> idToInternalInstrumentInfo;
    @CompilerDirectives.CompilationFinal
    OptionValuesImpl engineOptionValues;
    boolean boundEngine;
    boolean storeEngine;
    Handler logHandler;
    final Exception createdLocation = DEBUG_MISSING_CLOSE ? new Exception() : null;
    private final EconomicSet<PolyglotContextImpl.ContextWeakReference> contexts = EconomicSet.create(Equivalence.IDENTITY);
    final ReferenceQueue<PolyglotContextImpl> contextsReferenceQueue = new ReferenceQueue();
    private final AtomicReference<PolyglotContextImpl> preInitializedContext = new AtomicReference();
    final Assumption singleThreadPerContext = Truffle.getRuntime().createAssumption("Single thread per context of an engine.");
    final Assumption noInnerContexts = Truffle.getRuntime().createAssumption("No inner contexts.");
    final Assumption customHostClassLoader = Truffle.getRuntime().createAssumption("No custom host class loader needed.");
    volatile OptionDescriptors allOptions;
    volatile boolean closed;
    final Object runtimeData;
    Map<String, Level> logLevels;
    private volatile Object engineLoggers;
    private volatile Supplier<Map<String, Collection<? extends TruffleFile.FileTypeDetector>>> fileTypeDetectorsSupplier;
    final int languageCount;
    private volatile PolyglotLimits.EngineLimits limits;
    private final MessageTransport messageInterceptor;
    private volatile int asynchronousStackDepth = 0;
    final SpecializationStatistics specializationStatistics;
    Function<String, TruffleLogger> engineLoggerSupplier;
    private volatile TruffleLogger engineLogger;
    final WeakAssumedValue<PolyglotContextImpl> singleContextValue = new WeakAssumedValue("single context");
    @CompilerDirectives.CompilationFinal
    volatile StableLocalLocations contextLocalLocations = new StableLocalLocations(EMPTY_LOCATIONS);
    @CompilerDirectives.CompilationFinal
    volatile StableLocalLocations contextThreadLocalLocations = new StableLocalLocations(EMPTY_LOCATIONS);
    final PolyglotLanguage hostLanguage;
    @CompilerDirectives.CompilationFinal
    AbstractPolyglotImpl.AbstractHostLanguageService host;
    final boolean hostLanguageOnly;
    final List<PolyglotSharingLayer> sharedLayers = new ArrayList<PolyglotSharingLayer>();
    private boolean runtimeInitialized;
    final AbstractPolyglotImpl.AbstractPolyglotHostService polyglotHostService;
    private final Set<SystemThread.InstrumentSystemThread> activeSystemThreads = Collections.newSetFromMap(new HashMap());
    private static final String DISABLE_PRIVILEGES_VALUE = ImageBuildTimeOptions.get("DisablePrivileges");
    private static final String[] DISABLED_PRIVILEGES = DISABLE_PRIVILEGES_VALUE.isEmpty() ? new String[]{} : DISABLE_PRIVILEGES_VALUE.split(",");
    static final boolean ALLOW_CREATE_PROCESS;
    static final boolean ALLOW_ENVIRONMENT_ACCESS;
    static final boolean ALLOW_IO;
    private static final Object NO_ENTER;
    private static volatile PolyglotEngineImpl fallbackEngine;

    PolyglotEngineImpl(PolyglotImpl impl, String[] permittedLanguages, DispatchOutputStream out, DispatchOutputStream err, InputStream in, OptionValuesImpl engineOptions, Map<String, Level> logLevels, PolyglotLoggers.EngineLoggerProvider engineLogger, Map<String, String> options, boolean allowExperimentalOptions, boolean boundEngine, boolean preInitialization, MessageTransport messageInterceptor, Handler logHandler, TruffleLanguage<Object> hostImpl, boolean hostLanguageOnly, AbstractPolyglotImpl.AbstractPolyglotHostService polyglotHostService) {
        this.messageInterceptor = messageInterceptor;
        this.impl = impl;
        this.permittedLanguages = permittedLanguages;
        this.out = out;
        this.err = err;
        this.in = in;
        this.logHandler = logHandler;
        this.logLevels = logLevels;
        this.hostLanguage = this.createLanguage(LanguageCache.createHostLanguageCache(hostImpl, new String[0]), 0, null);
        this.boundEngine = boundEngine;
        this.storeEngine = EngineAccessor.RUNTIME.isStoreEnabled(engineOptions);
        this.hostLanguageOnly = hostLanguageOnly;
        this.polyglotHostService = polyglotHostService != null ? polyglotHostService : new DefaultPolyglotHostService(impl);
        LinkedHashMap<String, LanguageInfo> languageInfos = new LinkedHashMap<String, LanguageInfo>();
        this.idToLanguage = Collections.unmodifiableMap(this.initializeLanguages(languageInfos));
        this.idToInternalLanguageInfo = Collections.unmodifiableMap(languageInfos);
        this.languageCount = this.idToLanguage.values().size() + 1;
        this.languages = this.createLanguageStaticIndex();
        LinkedHashMap<String, InstrumentInfo> instrumentInfos = new LinkedHashMap<String, InstrumentInfo>();
        this.idToInstrument = Collections.unmodifiableMap(this.initializeInstruments(instrumentInfos));
        this.idToInternalInstrumentInfo = Collections.unmodifiableMap(instrumentInfos);
        this.runtimeData = EngineAccessor.RUNTIME.createRuntimeData(engineOptions, engineLogger);
        this.classToLanguage = new HashMap<String, PolyglotLanguage>();
        for (PolyglotLanguage polyglotLanguage : this.idToLanguage.values()) {
            this.classToLanguage.put(polyglotLanguage.cache.getClassName(), polyglotLanguage);
        }
        for (String string : this.idToLanguage.keySet()) {
            if (!this.idToInstrument.containsKey(string)) continue;
            throw PolyglotEngineImpl.failDuplicateId(string, this.idToLanguage.get((Object)string).cache.getClassName(), this.idToInstrument.get((Object)string).cache.getClassName());
        }
        this.engineLoggerSupplier = engineLogger;
        this.engineOptionValues = engineOptions;
        LinkedHashMap<String, Language> publicLanguages = new LinkedHashMap<String, Language>();
        for (String string : this.idToLanguage.keySet()) {
            PolyglotLanguage languageImpl = this.idToLanguage.get(string);
            if (languageImpl.cache.isInternal()) continue;
            publicLanguages.put(string, languageImpl.api);
        }
        this.idToPublicLanguage = Collections.unmodifiableMap(publicLanguages);
        LinkedHashMap<String, Instrument> linkedHashMap = new LinkedHashMap<String, Instrument>();
        for (String key : this.idToInstrument.keySet()) {
            PolyglotInstrument instrumentImpl = this.idToInstrument.get(key);
            if (instrumentImpl.cache.isInternal()) continue;
            linkedHashMap.put(key, instrumentImpl.api);
        }
        this.idToPublicInstrument = Collections.unmodifiableMap(linkedHashMap);
        this.instrumentationHandler = EngineAccessor.INSTRUMENT.createInstrumentationHandler(this, out, err, in, messageInterceptor, this.storeEngine);
        if (this.isSharingEnabled(null)) {
            this.initializeMultiContext();
        }
        HashMap<PolyglotLanguage, Map<String, String>> hashMap = new HashMap<PolyglotLanguage, Map<String, String>>();
        HashMap<PolyglotInstrument, Map<String, String>> instrumentsOptions = new HashMap<PolyglotInstrument, Map<String, String>>();
        this.parseOptions(options, hashMap, instrumentsOptions);
        for (PolyglotLanguage language : hashMap.keySet()) {
            language.getOptionValues().putAll(this, (Map)hashMap.get(language), allowExperimentalOptions);
        }
        this.specializationStatistics = this.engineOptionValues.get(PolyglotEngineOptions.SpecializationStatistics) != false ? SpecializationStatistics.create() : null;
        this.notifyCreated();
        if (!preInitialization) {
            this.createInstruments(instrumentsOptions, allowExperimentalOptions);
        }
    }

    boolean isSharingEnabled(PolyglotContextConfig config) {
        boolean forced = this.engineOptionValues.get(PolyglotEngineOptions.ForceCodeSharing);
        boolean disabled = this.engineOptionValues.get(PolyglotEngineOptions.DisableCodeSharing);
        if (forced && disabled) {
            throw PolyglotEngineException.illegalState("Option engine.ForceCodeSharing can not be true at the same time as engine.DisableCodeSahring.");
        }
        return !(disabled |= config != null && config.isCodeSharingDisabled()) && ((forced |= config != null && config.isCodeSharingForced()) || !this.boundEngine || this.storeEngine);
    }

    boolean isStoreEngine() {
        return this.storeEngine;
    }

    TruffleLanguage<?> getHostLanguageSPI() {
        assert (this.hostLanguage.cache.loadLanguage() == this.hostLanguage.cache.loadLanguage()) : "host language caches must always return the same instance";
        return this.hostLanguage.cache.loadLanguage();
    }

    void claimSharingLayer(PolyglotSharingLayer layer, PolyglotContextImpl context, PolyglotLanguage requestingLanguage) {
        assert (Thread.holdsLock(this.lock));
        assert (!layer.isClaimed());
        for (PolyglotSharingLayer sharableLayer : this.sharedLayers) {
            if (!layer.claimLayerForContext(sharableLayer, context, Collections.singleton(requestingLanguage))) continue;
            assert (layer.isClaimed());
            return;
        }
        boolean result = layer.claimLayerForContext(null, context, Collections.singleton(requestingLanguage));
        assert (result) : "new layer must be compatible";
        switch (layer.getContextPolicy()) {
            case EXCLUSIVE: 
            case REUSE: {
                break;
            }
            case SHARED: {
                assert (layer.isClaimed());
                this.sharedLayers.add(layer);
                break;
            }
            default: {
                throw CompilerDirectives.shouldNotReachHere();
            }
        }
    }

    void freeSharingLayer(PolyglotSharingLayer layer, PolyglotContextImpl context) {
        assert (Thread.holdsLock(this.lock));
        assert (layer.isClaimed());
        layer.freeSharingLayer(context);
        switch (layer.getContextPolicy()) {
            case EXCLUSIVE: {
                break;
            }
            case REUSE: {
                this.sharedLayers.add(layer);
                break;
            }
            case SHARED: {
                assert (this.sharedLayers.contains(layer));
                break;
            }
            default: {
                throw CompilerDirectives.shouldNotReachHere();
            }
        }
    }

    void ensureRuntimeInitialized(PolyglotContextImpl context) {
        assert (Thread.holdsLock(this.lock));
        if (this.runtimeInitialized) {
            return;
        }
        this.runtimeInitialized = true;
        if (TruffleOptions.AOT) {
            return;
        }
        RootNode node = RootNode.createConstantNode(42);
        EngineAccessor.NODES.setSharingLayer(node, context.layer);
        node.getCallTarget();
    }

    private PolyglotLanguage[] createLanguageStaticIndex() {
        int maxLanguageStaticId = 0;
        for (PolyglotLanguage language : this.idToLanguage.values()) {
            maxLanguageStaticId = Math.max(maxLanguageStaticId, language.cache.getStaticIndex());
        }
        PolyglotLanguage[] list = new PolyglotLanguage[maxLanguageStaticId + 1];
        list[0] = this.hostLanguage;
        for (PolyglotLanguage language : this.idToLanguage.values()) {
            assert (list[language.cache.getStaticIndex()] == null) : "language index used twice";
            list[language.cache.getStaticIndex()] = language;
        }
        return list;
    }

    void notifyCreated() {
        EngineAccessor.RUNTIME.onEngineCreate(this, this.runtimeData);
    }

    PolyglotEngineImpl(PolyglotEngineImpl prototype) {
        this.messageInterceptor = prototype.messageInterceptor;
        this.instrumentationHandler = EngineAccessor.INSTRUMENT.createInstrumentationHandler(this, EngineAccessor.INSTRUMENT.createDispatchOutput(EngineAccessor.INSTRUMENT.getOut(prototype.out)), EngineAccessor.INSTRUMENT.createDispatchOutput(EngineAccessor.INSTRUMENT.getOut(prototype.err)), prototype.in, prototype.messageInterceptor, prototype.storeEngine);
        this.impl = prototype.impl;
        this.permittedLanguages = prototype.permittedLanguages;
        this.out = prototype.out;
        this.err = prototype.err;
        this.in = prototype.in;
        this.host = prototype.host;
        this.boundEngine = prototype.boundEngine;
        this.logHandler = prototype.logHandler;
        this.hostLanguage = this.createLanguage(LanguageCache.createHostLanguageCache(prototype.getHostLanguageSPI(), new String[0]), 0, null);
        this.runtimeData = EngineAccessor.RUNTIME.createRuntimeData(prototype.engineOptionValues, prototype.engineLoggerSupplier);
        this.engineLoggerSupplier = prototype.engineLoggerSupplier;
        this.polyglotHostService = prototype.polyglotHostService;
        LinkedHashMap<String, LanguageInfo> languageInfos = new LinkedHashMap<String, LanguageInfo>();
        this.hostLanguageOnly = prototype.hostLanguageOnly;
        this.idToLanguage = Collections.unmodifiableMap(this.initializeLanguages(languageInfos));
        this.idToInternalLanguageInfo = Collections.unmodifiableMap(languageInfos);
        this.languageCount = this.idToLanguage.size() + 1;
        this.languages = this.createLanguageStaticIndex();
        LinkedHashMap<String, InstrumentInfo> instrumentInfos = new LinkedHashMap<String, InstrumentInfo>();
        this.idToInstrument = Collections.unmodifiableMap(this.initializeInstruments(instrumentInfos));
        this.idToInternalInstrumentInfo = Collections.unmodifiableMap(instrumentInfos);
        this.classToLanguage = new HashMap<String, PolyglotLanguage>();
        for (PolyglotLanguage polyglotLanguage : this.idToLanguage.values()) {
            this.classToLanguage.put(polyglotLanguage.cache.getClassName(), polyglotLanguage);
        }
        for (String string : this.idToLanguage.keySet()) {
            if (!this.idToInstrument.containsKey(string)) continue;
            throw PolyglotEngineImpl.failDuplicateId(string, this.idToLanguage.get((Object)string).cache.getClassName(), this.idToInstrument.get((Object)string).cache.getClassName());
        }
        LinkedHashMap<String, Language> publicLanguages = new LinkedHashMap<String, Language>();
        for (String string : this.idToLanguage.keySet()) {
            PolyglotLanguage languageImpl = this.idToLanguage.get(string);
            if (languageImpl.cache.isInternal()) continue;
            publicLanguages.put(string, languageImpl.api);
        }
        this.idToPublicLanguage = Collections.unmodifiableMap(publicLanguages);
        LinkedHashMap<String, Instrument> linkedHashMap = new LinkedHashMap<String, Instrument>();
        for (String key : this.idToInstrument.keySet()) {
            PolyglotInstrument instrumentImpl = this.idToInstrument.get(key);
            if (instrumentImpl.cache.isInternal()) continue;
            linkedHashMap.put(key, instrumentImpl.api);
        }
        this.idToPublicInstrument = Collections.unmodifiableMap(linkedHashMap);
        this.logLevels = prototype.logLevels;
        this.engineOptionValues = prototype.engineOptionValues.copy();
        if (this.isSharingEnabled(null)) {
            this.initializeMultiContext();
        }
        for (String languageId : this.idToLanguage.keySet()) {
            OptionValuesImpl prototypeOptions = prototype.idToLanguage.get(languageId).getOptionValuesIfExists();
            if (prototypeOptions == null) continue;
            prototypeOptions.copyInto(this.idToLanguage.get(languageId).getOptionValues());
        }
        this.specializationStatistics = this.engineOptionValues.get(PolyglotEngineOptions.SpecializationStatistics) != false ? SpecializationStatistics.create() : null;
        ArrayList<PolyglotInstrument> arrayList = new ArrayList<PolyglotInstrument>();
        for (String instrumentId : this.idToInstrument.keySet()) {
            OptionValuesImpl prototypeOptions = prototype.idToInstrument.get(instrumentId).getOptionValuesIfExists();
            if (prototypeOptions == null) continue;
            PolyglotInstrument instrument = this.idToInstrument.get(instrumentId);
            prototypeOptions.copyInto(instrument.getEngineOptionValues());
            arrayList.add(instrument);
        }
        this.api = this.getAPIAccess().newEngine(this.impl.engineDispatch, this, true);
        PolyglotEngineImpl.ensureInstrumentsCreated(arrayList);
        this.notifyCreated();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    TruffleLogger getEngineLogger() {
        TruffleLogger result = this.engineLogger;
        if (result == null) {
            Object object = this.lock;
            synchronized (object) {
                result = this.engineLogger;
                if (result == null) {
                    result = this.engineLoggerSupplier.apply("engine");
                    Object logger = EngineAccessor.LANGUAGE.getLoggerCache(result);
                    PolyglotLoggers.LoggerCache loggerCache = (PolyglotLoggers.LoggerCache)EngineAccessor.LANGUAGE.getLoggersSPI(logger);
                    loggerCache.setOwner(this);
                    if (!this.logLevels.isEmpty()) {
                        EngineAccessor.LANGUAGE.configureLoggers(this, this.logLevels, logger);
                    }
                    this.engineLogger = result;
                }
            }
        }
        return result;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Object getOrCreateEngineLoggers() {
        Object res = this.engineLoggers;
        if (res == null) {
            Object object = this.lock;
            synchronized (object) {
                res = this.engineLoggers;
                if (res == null) {
                    PolyglotLoggers.LoggerCache loggerCache = PolyglotLoggers.LoggerCache.newEngineLoggerCache(this);
                    loggerCache.setOwner(this);
                    res = EngineAccessor.LANGUAGE.createEngineLoggers(loggerCache);
                    if (!this.logLevels.isEmpty()) {
                        EngineAccessor.LANGUAGE.configureLoggers(this, this.logLevels, res);
                    }
                    for (PolyglotContextImpl.ContextWeakReference contextRef : this.contexts) {
                        PolyglotContextImpl context = (PolyglotContextImpl)contextRef.get();
                        if (context == null || context.config.logLevels.isEmpty()) continue;
                        EngineAccessor.LANGUAGE.configureLoggers(context, context.config.logLevels, res);
                    }
                    this.engineLoggers = res;
                }
            }
        }
        return res;
    }

    static OptionDescriptors createEngineOptionDescriptors() {
        PolyglotEngineOptionsOptionDescriptors engineOptionDescriptors = new PolyglotEngineOptionsOptionDescriptors();
        OptionDescriptors compilerOptionDescriptors = EngineAccessor.RUNTIME.getEngineOptionDescriptors();
        return OptionDescriptors.createUnion(engineOptionDescriptors, compilerOptionDescriptors);
    }

    void patch(DispatchOutputStream newOut, DispatchOutputStream newErr, InputStream newIn, OptionValuesImpl engineOptions, LogConfig newLogConfig, PolyglotLoggers.EngineLoggerProvider logSupplier, Map<String, String> newOptions, boolean newAllowExperimentalOptions, boolean newBoundEngine, Handler newLogHandler, AbstractPolyglotImpl.AbstractPolyglotHostService newPolyglotHostService) {
        CompilerAsserts.neverPartOfCompilation();
        this.out = newOut;
        this.err = newErr;
        this.in = newIn;
        this.boundEngine = newBoundEngine;
        this.logHandler = newLogHandler;
        this.engineOptionValues = engineOptions;
        this.logLevels = newLogConfig.logLevels;
        this.polyglotHostService.patch(newPolyglotHostService);
        this.storeEngine = this.storeEngine || EngineAccessor.RUNTIME.isStoreEnabled(engineOptions);
        this.engineLoggerSupplier = logSupplier;
        this.engineLogger = null;
        EngineAccessor.INSTRUMENT.patchInstrumentationHandler(this.instrumentationHandler, newOut, newErr, newIn);
        HashMap<PolyglotLanguage, Map<String, String>> languagesOptions = new HashMap<PolyglotLanguage, Map<String, String>>();
        HashMap<PolyglotInstrument, Map<String, String>> instrumentsOptions = new HashMap<PolyglotInstrument, Map<String, String>>();
        this.parseOptions(newOptions, languagesOptions, instrumentsOptions);
        EngineAccessor.RUNTIME.onEnginePatch(this.runtimeData, engineOptions, logSupplier);
        for (PolyglotLanguage language : languagesOptions.keySet()) {
            language.getOptionValues().putAll(this, (Map)languagesOptions.get(language), newAllowExperimentalOptions);
        }
        for (PolyglotInstrument instrument : instrumentsOptions.keySet()) {
            instrument.getEngineOptionValues().putAll(this, (Map)instrumentsOptions.get(instrument), newAllowExperimentalOptions);
        }
    }

    static Handler createLogHandler(LogConfig logConfig, DispatchOutputStream errDispatchOutputStream) {
        if (logConfig.logFile != null) {
            if (ALLOW_IO) {
                return PolyglotLoggers.getFileHandler(logConfig.logFile);
            }
            throw PolyglotEngineException.illegalState("The `log.file` option is not allowed when the allowIO() privilege is removed at image build time.");
        }
        return PolyglotLoggers.createDefaultHandler(EngineAccessor.INSTRUMENT.getOut(errDispatchOutputStream));
    }

    private void createInstruments(Map<PolyglotInstrument, Map<String, String>> instrumentsOptions, boolean allowExperimentalOptions) {
        for (PolyglotInstrument instrument : instrumentsOptions.keySet()) {
            instrument.getEngineOptionValues().putAll(this, instrumentsOptions.get(instrument), allowExperimentalOptions);
        }
        PolyglotEngineImpl.ensureInstrumentsCreated(instrumentsOptions.keySet());
    }

    static void ensureInstrumentsCreated(Collection<? extends PolyglotInstrument> instruments) {
        for (PolyglotInstrument polyglotInstrument : instruments) {
            polyglotInstrument.ensureCreated();
        }
    }

    void initializeMultiContext() {
        this.singleContextValue.invalidate();
    }

    static void parseEngineOptions(Map<String, String> allOptions, Map<String, String> engineOptions, LogConfig logOptions) {
        Iterator<Map.Entry<String, String>> iterator = allOptions.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String key = entry.getKey();
            String value2 = entry.getValue();
            String group = PolyglotEngineImpl.parseOptionGroup(key);
            if (group.equals("engine")) {
                engineOptions.put(entry.getKey(), entry.getValue());
                iterator.remove();
                continue;
            }
            if (!group.equals(OPTION_GROUP_LOG)) continue;
            if (LOG_FILE_OPTION.equals(key)) {
                logOptions.logFile = value2;
            } else {
                logOptions.logLevels.put(PolyglotEngineImpl.parseLoggerName(key), Level.parse(value2));
            }
            iterator.remove();
        }
    }

    private void parseOptions(Map<String, String> options, Map<PolyglotLanguage, Map<String, String>> languagesOptions, Map<PolyglotInstrument, Map<String, String>> instrumentsOptions) {
        for (String key : options.keySet()) {
            String group = PolyglotEngineImpl.parseOptionGroup(key);
            String value2 = options.get(key);
            PolyglotLanguage language = this.idToLanguage.get(group);
            if (language != null && !language.cache.isInternal()) {
                Map<String, String> languageOptions = languagesOptions.get(language);
                if (languageOptions == null) {
                    languageOptions = new HashMap<String, String>();
                    languagesOptions.put(language, languageOptions);
                }
                languageOptions.put(key, value2);
                continue;
            }
            PolyglotInstrument instrument = this.idToInstrument.get(group);
            if (instrument != null && !instrument.cache.isInternal()) {
                Map<String, String> instrumentOptions = instrumentsOptions.get(instrument);
                if (instrumentOptions == null) {
                    instrumentOptions = new HashMap<String, String>();
                    instrumentsOptions.put(instrument, instrumentOptions);
                }
                instrumentOptions.put(key, value2);
                continue;
            }
            switch (group) {
                case "engine": 
                case "log": {
                    throw new AssertionError((Object)"Log or engine options should already be parsed.");
                }
                case "image-build-time": {
                    throw PolyglotEngineException.illegalArgument("Image build-time option '" + key + "' cannot be set at runtime");
                }
            }
            throw OptionValuesImpl.failNotFound(this.getAllOptions(), key);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static Map<String, String> readOptionsFromSystemProperties(Map<String, String> options) {
        Properties properties2 = System.getProperties();
        HashMap<String, String> newOptions = null;
        Properties properties3 = properties2;
        synchronized (properties3) {
            for (Object systemKey : properties2.keySet()) {
                String optionKey;
                String key;
                if ("polyglot.engine.AllowExperimentalOptions".equals(systemKey) || !(key = (String)systemKey).startsWith("polyglot.") || (optionKey = key.substring("polyglot.".length())).startsWith(OPTION_GROUP_IMAGE_BUILD_TIME) || options.containsKey(optionKey)) continue;
                if (newOptions == null) {
                    newOptions = new HashMap<String, String>(options);
                }
                newOptions.put(optionKey, System.getProperty(key));
            }
        }
        if (newOptions == null) {
            return options;
        }
        return newOptions;
    }

    static String parseOptionGroup(String key) {
        int groupIndex = key.indexOf(46);
        String group = groupIndex != -1 ? key.substring(0, groupIndex) : key;
        return group;
    }

    static String parseLoggerName(String optionKey) {
        int end2;
        String prefix = "log.";
        String suffix = ".level";
        if (!optionKey.startsWith("log.") || !optionKey.endsWith(".level")) {
            throw PolyglotEngineException.illegalArgument(optionKey);
        }
        int start2 = "log.".length();
        return start2 < (end2 = optionKey.length() - ".level".length()) ? optionKey.substring(start2, end2) : "";
    }

    @Override
    public PolyglotEngineImpl getEngine() {
        return this;
    }

    PolyglotLanguage findLanguage(PolyglotLanguageContext accessingLanguage, String languageId, String mimeType, boolean failIfNotFound, boolean allowInternalAndDependent) {
        Map<String, LanguageInfo> languageMap;
        assert (languageId != null || mimeType != null) : Objects.toString(languageId) + ", " + Objects.toString(mimeType);
        if (accessingLanguage != null) {
            languageMap = accessingLanguage.getAccessibleLanguages(allowInternalAndDependent);
        } else {
            assert (allowInternalAndDependent) : "non internal access is not yet supported for instrument lookups";
            languageMap = this.idToInternalLanguageInfo;
        }
        LanguageInfo foundLanguage = null;
        if (languageId != null) {
            foundLanguage = languageMap.get(languageId);
        }
        if (mimeType != null && foundLanguage == null && (foundLanguage = languageMap.get(mimeType)) == null) {
            for (LanguageInfo searchLanguage : languageMap.values()) {
                if (!searchLanguage.getMimeTypes().contains(mimeType)) continue;
                foundLanguage = searchLanguage;
                break;
            }
        }
        assert (allowInternalAndDependent || foundLanguage == null || !foundLanguage.isInternal() && accessingLanguage.isPolyglotEvalAllowed(languageId));
        if (foundLanguage != null) {
            return this.idToLanguage.get(foundLanguage.getId());
        }
        if (failIfNotFound) {
            if (languageId != null) {
                LinkedHashSet<String> ids = new LinkedHashSet<String>();
                for (LanguageInfo language : languageMap.values()) {
                    ids.add(language.getId());
                }
                throw PolyglotEngineException.illegalState("No language for id " + languageId + " found. Supported languages are: " + ids);
            }
            LinkedHashSet<String> mimeTypes = new LinkedHashSet<String>();
            for (LanguageInfo language : languageMap.values()) {
                mimeTypes.addAll(language.getMimeTypes());
            }
            throw PolyglotEngineException.illegalState("No language for MIME type " + mimeType + " found. Supported languages are: " + mimeTypes);
        }
        return null;
    }

    private Map<String, PolyglotInstrument> initializeInstruments(Map<String, InstrumentInfo> infos) {
        if (this.hostLanguageOnly) {
            return Collections.emptyMap();
        }
        LinkedHashMap<String, PolyglotInstrument> instruments = new LinkedHashMap<String, PolyglotInstrument>();
        List<InstrumentCache> cachedInstruments = InstrumentCache.load();
        for (InstrumentCache instrumentCache : cachedInstruments) {
            Instrument instrument;
            PolyglotInstrument instrumentImpl = new PolyglotInstrument(this, instrumentCache);
            instrumentImpl.info = EngineAccessor.LANGUAGE.createInstrument(instrumentImpl, instrumentCache.getId(), instrumentCache.getName(), instrumentCache.getVersion());
            instrumentImpl.api = instrument = this.impl.getAPIAccess().newInstrument(this.impl.instrumentDispatch, instrumentImpl);
            String id = instrumentImpl.cache.getId();
            PolyglotEngineImpl.verifyId(id, instrumentCache.getClassName());
            if (instruments.containsKey(id)) {
                throw PolyglotEngineImpl.failDuplicateId(id, instrumentImpl.cache.getClassName(), ((PolyglotInstrument)instruments.get((Object)id)).cache.getClassName());
            }
            instruments.put(id, instrumentImpl);
            infos.put(id, instrumentImpl.info);
        }
        return instruments;
    }

    private Map<String, PolyglotLanguage> initializeLanguages(Map<String, LanguageInfo> infos) {
        if (this.hostLanguageOnly) {
            return Collections.emptyMap();
        }
        LinkedHashMap<String, PolyglotLanguage> polyglotLanguages = new LinkedHashMap<String, PolyglotLanguage>();
        HashMap<String, LanguageCache> cachedLanguages = new HashMap<String, LanguageCache>();
        ArrayList<LanguageCache> sortedLanguages = new ArrayList<LanguageCache>();
        for (LanguageCache lang : LanguageCache.languages().values()) {
            String id = lang.getId();
            if (cachedLanguages.containsKey(id)) continue;
            sortedLanguages.add(lang);
            cachedLanguages.put(id, lang);
        }
        Collections.sort(sortedLanguages);
        LinkedHashSet<LanguageCache> serializedLanguages = new LinkedHashSet<LanguageCache>();
        HashSet<String> languageReferences = new HashSet<String>();
        HashMap<String, RuntimeException> initErrors = new HashMap<String, RuntimeException>();
        for (LanguageCache language : sortedLanguages) {
            languageReferences.addAll(language.getDependentLanguages());
        }
        for (LanguageCache language : sortedLanguages) {
            if (!language.isInternal() || languageReferences.contains(language.getId())) continue;
            this.visitLanguage(initErrors, cachedLanguages, serializedLanguages, language);
        }
        for (LanguageCache language : sortedLanguages) {
            if (language.isInternal() || languageReferences.contains(language.getId())) continue;
            this.visitLanguage(initErrors, cachedLanguages, serializedLanguages, language);
        }
        int index = 1;
        for (LanguageCache cache : serializedLanguages) {
            PolyglotLanguage languageImpl = this.createLanguage(cache, index, (RuntimeException)initErrors.get(cache.getId()));
            String id = languageImpl.cache.getId();
            PolyglotEngineImpl.verifyId(id, cache.getClassName());
            if (polyglotLanguages.containsKey(id)) {
                throw PolyglotEngineImpl.failDuplicateId(id, languageImpl.cache.getClassName(), ((PolyglotLanguage)polyglotLanguages.get((Object)id)).cache.getClassName());
            }
            polyglotLanguages.put(id, languageImpl);
            infos.put(id, languageImpl.info);
            ++index;
        }
        return polyglotLanguages;
    }

    private void visitLanguage(Map<String, RuntimeException> initErrors, Map<String, LanguageCache> cachedLanguages, LinkedHashSet<LanguageCache> serializedLanguages, LanguageCache language) {
        this.visitLanguageImpl(new HashSet<String>(), initErrors, cachedLanguages, serializedLanguages, language);
    }

    private void visitLanguageImpl(Set<String> visitedIds, Map<String, RuntimeException> initErrors, Map<String, LanguageCache> cachedLanguages, LinkedHashSet<LanguageCache> serializedLanguages, LanguageCache language) {
        Set<String> dependencies = language.getDependentLanguages();
        for (String dependency : dependencies) {
            LanguageCache dependentLanguage = cachedLanguages.get(dependency);
            if (dependentLanguage == null) continue;
            if (visitedIds.contains(dependency)) {
                initErrors.put(language.getId(), PolyglotEngineException.illegalState("Illegal cyclic language dependency found:" + language.getId() + " -> " + dependency));
                continue;
            }
            visitedIds.add(dependency);
            this.visitLanguageImpl(visitedIds, initErrors, cachedLanguages, serializedLanguages, dependentLanguage);
            visitedIds.remove(dependency);
        }
        serializedLanguages.add(language);
    }

    private PolyglotLanguage createLanguage(LanguageCache cache, int index, RuntimeException initError) {
        Language language;
        PolyglotLanguage languageImpl = new PolyglotLanguage(this, cache, index, initError);
        languageImpl.api = language = this.impl.getAPIAccess().newLanguage(this.impl.languageDispatch, languageImpl);
        return languageImpl;
    }

    private static void verifyId(String id, String className) {
        if (RESERVED_IDS.contains(id)) {
            throw new IllegalStateException(String.format("The language or instrument with class '%s' uses a reserved id '%s'. Resolve this by using a not reserved id for the language or instrument. The following ids are reserved %s for internal use.", className, id, RESERVED_IDS));
        }
        if (id.contains(".")) {
            throw new IllegalStateException(String.format("The language '%s' must not contain a period in its id '%s'. Remove all periods from the id to resolve this issue. ", className, id));
        }
    }

    private static RuntimeException failDuplicateId(String duplicateId, String className1, String className2) {
        return new IllegalStateException(String.format("Duplicate id '%s' specified by language or instrument with class '%s' and '%s'. Resolve this by specifying a unique id for each language or instrument.", duplicateId, className1, className2));
    }

    void checkState() {
        if (this.closed) {
            throw PolyglotEngineException.illegalState("Engine is already closed.");
        }
    }

    void addContext(PolyglotContextImpl context) {
        assert (Thread.holdsLock(this.lock));
        this.ensureRuntimeInitialized(context);
        if (this.limits != null) {
            this.limits.validate(context.config.limits);
        }
        this.workContextReferenceQueue();
        this.contexts.add(context.weakReference);
        if (context.config.limits != null) {
            PolyglotLimits.EngineLimits l = this.limits;
            if (l == null) {
                this.limits = l = new PolyglotLimits.EngineLimits(this);
            }
            l.initialize(context.config.limits, context);
        }
        if (context.config.hostClassLoader != null) {
            context.engine.customHostClassLoader.invalidate();
        }
        this.singleContextValue.update(context);
    }

    void removeContext(PolyglotContextImpl context) {
        assert (Thread.holdsLock(this.lock)) : "Must hold PolyglotEngineImpl.lock";
        this.contexts.remove(context.weakReference);
        this.workContextReferenceQueue();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void disposeContext(PolyglotContextImpl context) {
        Object object = this.lock;
        synchronized (object) {
            assert (!context.weakReference.removed);
            context.weakReference.removed = true;
            context.weakReference.freeSharing(context);
            this.removeContext(context);
        }
    }

    private void workContextReferenceQueue() {
        Reference<PolyglotContextImpl> ref;
        while ((ref = this.contextsReferenceQueue.poll()) != null) {
            PolyglotContextImpl.ContextWeakReference contextRef = (PolyglotContextImpl.ContextWeakReference)ref;
            if (contextRef.removed) continue;
            contextRef.freeSharing(null);
            this.contexts.remove(contextRef);
            contextRef.removed = true;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void reportAllLanguageContexts(ContextsListener listener) {
        List<PolyglotContextImpl> allContexts;
        Iterator<PolyglotContextImpl> iterator = this.lock;
        synchronized (iterator) {
            if (this.contexts.isEmpty()) {
                return;
            }
            allContexts = this.collectAliveContexts();
        }
        for (PolyglotContextImpl context : allContexts) {
            listener.onContextCreated(context.creatorTruffleContext);
            for (PolyglotLanguageContext lc : context.contexts) {
                LanguageInfo language = lc.language.info;
                if (!lc.eventsEnabled || lc.env == null) continue;
                listener.onLanguageContextCreate(context.creatorTruffleContext, language);
                listener.onLanguageContextCreated(context.creatorTruffleContext, language);
                if (!lc.isInitialized()) continue;
                listener.onLanguageContextInitialize(context.creatorTruffleContext, language);
                listener.onLanguageContextInitialized(context.creatorTruffleContext, language);
                if (!lc.finalized) continue;
                listener.onLanguageContextFinalized(context.creatorTruffleContext, language);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void reportAllContextThreads(ThreadsListener listener) {
        Iterator<PolyglotContextImpl> iterator = this.lock;
        // MONITORENTER : iterator
        if (this.contexts.isEmpty()) {
            // MONITOREXIT : iterator
            return;
        }
        List<PolyglotContextImpl> allContexts = this.collectAliveContexts();
        // MONITOREXIT : iterator
        iterator = allContexts.iterator();
        block4: while (iterator.hasNext()) {
            Thread[] context;
            Thread[] threadArray = context = iterator.next();
            // MONITORENTER : context
            Thread[] threads = context.getSeenThreads().keySet().toArray(new Thread[0]);
            // MONITOREXIT : threadArray
            threadArray = threads;
            int n = threadArray.length;
            int n2 = 0;
            while (true) {
                if (n2 >= n) continue block4;
                Thread thread = threadArray[n2];
                listener.onThreadInitialized(context.creatorTruffleContext, thread);
                ++n2;
            }
            break;
        }
        return;
    }

    PolyglotLanguage requireLanguage(String id, boolean allowInternal) {
        this.checkState();
        PolyglotLanguage language = this.idToLanguage.get(id);
        if (language == null || !allowInternal && !this.idToPublicLanguage.containsKey(id)) {
            throw PolyglotEngineImpl.throwNotInstalled(id, this.idToLanguage.keySet());
        }
        return language;
    }

    private static RuntimeException throwNotInstalled(String id, Set<String> allLanguages) {
        String misspelledGuess = PolyglotEngineImpl.matchSpellingError(allLanguages, id);
        String didYouMean = "";
        if (misspelledGuess != null) {
            didYouMean = String.format("Did you mean '%s'? ", misspelledGuess);
        }
        throw PolyglotEngineException.illegalArgument(String.format("A language with id '%s' is not installed. %sInstalled languages are: %s.", id, didYouMean, allLanguages));
    }

    public Language requirePublicLanguage(String id) {
        this.checkState();
        Language language = this.idToPublicLanguage.get(id);
        if (language == null) {
            throw PolyglotEngineImpl.throwNotInstalled(id, this.idToPublicLanguage.keySet());
        }
        return language;
    }

    private static String matchSpellingError(Set<String> allIds, String enteredId) {
        String lowerCaseEnteredId = enteredId.toLowerCase();
        for (String id : allIds) {
            if (!id.toLowerCase().equals(lowerCaseEnteredId)) continue;
            return id;
        }
        return null;
    }

    public Instrument requirePublicInstrument(String id) {
        this.checkState();
        Instrument instrument = this.idToPublicInstrument.get(id);
        if (instrument == null) {
            String misspelledGuess = PolyglotEngineImpl.matchSpellingError(this.idToPublicInstrument.keySet(), id);
            String didYouMean = "";
            if (misspelledGuess != null) {
                didYouMean = String.format("Did you mean '%s'? ", misspelledGuess);
            }
            throw PolyglotEngineException.illegalState(String.format("An instrument with id '%s' is not installed. %sInstalled instruments are: %s.", id, didYouMean, this.getInstruments().keySet()));
        }
        return instrument;
    }

    @CompilerDirectives.TruffleBoundary
    <T extends TruffleLanguage<?>> PolyglotLanguage getLanguage(Class<T> languageClass, boolean fail) {
        PolyglotLanguage foundLanguage = this.classToLanguage.get(languageClass.getName());
        if (foundLanguage == null) {
            if (languageClass == this.getHostLanguageSPI().getClass()) {
                return this.hostLanguage;
            }
            if (fail) {
                Set<String> languageNames = this.classToLanguage.keySet();
                throw PolyglotEngineException.illegalArgument("Cannot find language " + languageClass + " among " + languageNames);
            }
        }
        return foundLanguage;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void ensureClosed(boolean force, boolean inShutdownHook, boolean initiatedByContext) {
        Object object;
        block50: {
            PolyglotInstrument instrumentImpl;
            block49: {
                object = this.lock;
                synchronized (object) {
                    Thread currentThread = Thread.currentThread();
                    boolean interrupted = false;
                    while (this.closingThread != null && this.closingThread != currentThread) {
                        try {
                            this.lock.wait();
                        }
                        catch (InterruptedException ie) {
                            interrupted = true;
                        }
                    }
                    if (interrupted) {
                        currentThread.interrupt();
                    }
                    if (this.closed) {
                        return;
                    }
                    this.workContextReferenceQueue();
                    List<PolyglotContextImpl> localContexts = this.collectAliveContexts();
                    if (!inShutdownHook) {
                        if (!force) {
                            for (PolyglotContextImpl context : localContexts) {
                                assert (!Thread.holdsLock(context));
                                PolyglotContextImpl polyglotContextImpl = context;
                                synchronized (polyglotContextImpl) {
                                    if (context.hasActiveOtherThread(false) && !context.state.isClosing()) {
                                        throw PolyglotEngineException.illegalState(String.format("One of the context instances is currently executing. Set cancelIfExecuting to true to stop the execution on this thread.", new Object[0]));
                                    }
                                }
                            }
                        }
                        if (!initiatedByContext) {
                            for (PolyglotContextImpl context : localContexts) {
                                assert (!Thread.holdsLock(context));
                                assert (context.parent == null);
                                if (force) {
                                    context.cancel(false, null);
                                    continue;
                                }
                                context.closeAndMaybeWait(false, null);
                            }
                        }
                    }
                    if (!inShutdownHook) {
                        this.contexts.clear();
                        if (EngineAccessor.RUNTIME.onEngineClosing(this.runtimeData)) {
                            return;
                        }
                    }
                    this.closingThread = currentThread;
                }
                object = this.idToInstrument.values().iterator();
                while (true) {
                    instrumentImpl = object.next();
                    instrumentImpl.notifyClosing();
                    continue;
                    break;
                }
                finally {
                    if (!object.hasNext()) break block49;
                }
            }
            object = this.idToInstrument.values().iterator();
            while (true) {
                instrumentImpl = object.next();
                instrumentImpl.ensureClosed();
                continue;
                break;
            }
            finally {
                if (!object.hasNext()) break block50;
            }
        }
        object = this.lock;
        synchronized (object) {
            this.closed = true;
            this.closingThread = null;
            this.lock.notifyAll();
            if (!this.activeSystemThreads.isEmpty()) {
                SystemThread.InstrumentSystemThread thread = this.activeSystemThreads.iterator().next();
                throw new IllegalStateException(String.format("The engine has an alive system thread %s created by instrument %s.", thread.getName(), thread.instrumentId));
            }
            if (this.specializationStatistics != null) {
                StringWriter logMessage = new StringWriter();
                try (PrintWriter writer = new PrintWriter(logMessage);){
                    if (!this.specializationStatistics.hasData()) {
                        writer.printf("No specialization statistics data was collected. Either no node with @%s annotations was executed or the interpreter was not compiled with -J-Dtruffle.dsl.GenerateSpecializationStatistics=true e.g as parameter to the javac tool.", Specialization.class.getSimpleName());
                    } else {
                        this.specializationStatistics.printHistogram(writer);
                    }
                }
                this.getEngineLogger().log(Level.INFO, String.format("Specialization histogram: %n%s", logMessage.toString()));
            }
            if (!inShutdownHook) {
                EngineAccessor.RUNTIME.onEngineClosed(this.runtimeData);
                Object loggers = this.getEngineLoggers();
                if (loggers != null) {
                    EngineAccessor.LANGUAGE.closeEngineLoggers(loggers);
                }
                if (this.logHandler != null) {
                    this.logHandler.close();
                }
                this.polyglotHostService.notifyEngineClosed(this, force);
                if (this.runtimeData != null) {
                    EngineAccessor.RUNTIME.flushCompileQueue(this.runtimeData);
                }
                this.getAPIAccess().engineClosed(this.api);
            } else if (this.logHandler != null) {
                this.logHandler.flush();
            }
        }
    }

    List<PolyglotContextImpl> collectAliveContexts() {
        assert (Thread.holdsLock(this.lock));
        ArrayList<PolyglotContextImpl> localContexts = new ArrayList<PolyglotContextImpl>(this.contexts.size());
        for (PolyglotContextImpl.ContextWeakReference ref : this.contexts) {
            PolyglotContextImpl context = (PolyglotContextImpl)ref.get();
            if (context != null) {
                localContexts.add(context);
                continue;
            }
            this.contexts.remove(ref);
        }
        return localContexts;
    }

    public Map<String, Instrument> getInstruments() {
        this.checkState();
        return this.idToPublicInstrument;
    }

    public Map<String, Language> getLanguages() {
        this.checkState();
        return this.idToPublicLanguage;
    }

    public OptionDescriptors getOptions() {
        this.checkState();
        return this.engineOptionValues.getDescriptors();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Set<Source> getCachedSources() {
        List<PolyglotContextImpl> activeContexts;
        this.checkState();
        HashSet<Source> sources = new HashSet<Source>();
        Object object = this.lock;
        synchronized (object) {
            activeContexts = this.collectAliveContexts();
        }
        for (PolyglotContextImpl context : activeContexts) {
            context.layer.listCachedSources(sources);
        }
        object = this.lock;
        synchronized (object) {
            for (PolyglotSharingLayer layer : this.sharedLayers) {
                layer.listCachedSources(sources);
            }
        }
        return sources;
    }

    Collection<CallTarget> getCallTargets() {
        return EngineAccessor.INSTRUMENT.getLoadedCallTargets(this.instrumentationHandler);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    OptionDescriptors getAllOptions() {
        this.checkState();
        if (this.allOptions == null) {
            Object object = this.lock;
            synchronized (object) {
                if (this.allOptions == null) {
                    ArrayList<OptionDescriptors> allDescriptors = new ArrayList<OptionDescriptors>();
                    allDescriptors.add(this.engineOptionValues.getDescriptors());
                    for (PolyglotLanguage language : this.idToLanguage.values()) {
                        allDescriptors.add(language.getOptionsInternal());
                    }
                    for (PolyglotInstrument instrument : this.idToInstrument.values()) {
                        allDescriptors.add(instrument.getAllOptionsInternal());
                    }
                    this.allOptions = OptionDescriptors.createUnion(allDescriptors.toArray(new OptionDescriptors[0]));
                }
            }
        }
        return this.allOptions;
    }

    PolyglotContextImpl getPreInitializedContext() {
        return this.preInitializedContext.get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void preInitialize() {
        Object object = this.lock;
        synchronized (object) {
            if (this.isSharingEnabled(null)) {
                for (PolyglotSharingLayer layer : this.sharedLayers) {
                    if (!layer.isClaimed()) continue;
                    layer.preInitialize();
                }
            } else {
                String oldOption = this.engineOptionValues.get(PolyglotEngineOptions.PreinitializeContexts);
                String newOption = ImageBuildTimeOptions.get("PreinitializeContexts");
                String optionValue = !oldOption.isEmpty() && !newOption.isEmpty() ? oldOption + "," + newOption : oldOption + newOption;
                HashSet languageIds = new HashSet();
                if (!optionValue.isEmpty()) {
                    Collections.addAll(languageIds, optionValue.split(","));
                }
                HashSet<PolyglotLanguage> preinitLanguages = new HashSet<PolyglotLanguage>();
                for (String id : languageIds) {
                    PolyglotLanguage language = this.idToLanguage.get(id);
                    if (language == null || language.cache.isInternal()) continue;
                    preinitLanguages.add(language);
                }
                boolean allowNativeAccess = ImageBuildTimeOptions.getBoolean("PreinitializeContextsWithNative");
                PolyglotContextConfig.PreinitConfig preinitConfig = allowNativeAccess ? PolyglotContextConfig.PreinitConfig.DEFAULT_WITH_NATIVE_ACCESS : PolyglotContextConfig.PreinitConfig.DEFAULT;
                this.preInitializedContext.set(PolyglotContextImpl.preinitialize(this, preinitConfig, null, preinitLanguages, true));
            }
        }
    }

    void finalizeStore() {
        assert (Thread.holdsLock(this.lock));
        this.out = null;
        this.err = null;
        this.in = null;
        this.logHandler = null;
        AbstractPolyglotImpl.AbstractHostLanguageService hostLanguageService = this.host;
        if (hostLanguageService != null) {
            hostLanguageService.release();
        }
        EngineAccessor.INSTRUMENT.finalizeStoreInstrumentationHandler(this.instrumentationHandler);
    }

    @CompilerDirectives.TruffleBoundary
    int getAsynchronousStackDepth() {
        return this.asynchronousStackDepth;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    void setAsynchronousStackDepth(PolyglotInstrument polyglotInstrument, int depth) {
        assert (depth >= 0) : String.format("Wrong depth: %d", depth);
        int newDepth = 0;
        Object object = this.lock;
        synchronized (object) {
            polyglotInstrument.requestedAsyncStackDepth = depth;
            for (PolyglotInstrument instrument : this.idToInstrument.values()) {
                if (instrument.requestedAsyncStackDepth <= newDepth) continue;
                newDepth = instrument.requestedAsyncStackDepth;
            }
        }
        this.asynchronousStackDepth = newDepth;
    }

    static void cancelOrExit(PolyglotContextImpl context, List<Future<Void>> cancelationFutures) {
        PolyglotEngineImpl.cancelOrExitOrInterrupt(context, cancelationFutures, 0L, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static boolean cancelOrExitOrInterrupt(PolyglotContextImpl context, List<Future<Void>> futures, long startMillis, Duration timeout) {
        block23: {
            try {
                assert (PolyglotEngineImpl.singleThreadedOrNotActive(context)) : "Cancel while active is only allowed for single-threaded contexts!";
                if (timeout == null) {
                    boolean closeCompleted = context.closeImpl(true);
                    assert (closeCompleted) : "Close was not completed!";
                    break block23;
                }
                boolean bl = PolyglotEngineImpl.waitForThreads(context, startMillis, timeout);
                return bl;
            }
            finally {
                for (Future<Void> future2 : futures) {
                    boolean timedOut = false;
                    try {
                        if (timeout != null) {
                            long timeoutMillis;
                            long timeElapsed = System.currentTimeMillis() - startMillis;
                            if (timeElapsed < (timeoutMillis = timeout.toMillis())) {
                                try {
                                    future2.get(timeoutMillis - timeElapsed, TimeUnit.MILLISECONDS);
                                }
                                catch (TimeoutException te) {
                                    timedOut = true;
                                }
                            } else {
                                timedOut = true;
                            }
                        } else {
                            future2.get();
                        }
                    }
                    catch (InterruptedException | ExecutionException e) {
                        throw CompilerDirectives.shouldNotReachHere(e);
                    }
                    if (!timedOut) continue;
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static boolean singleThreadedOrNotActive(PolyglotContextImpl context) {
        PolyglotContextImpl polyglotContextImpl = context;
        synchronized (polyglotContextImpl) {
            return context.singleThreaded || !context.isActive(Thread.currentThread());
        }
    }

    private static boolean waitForThreads(PolyglotContextImpl context, long startMillis, Duration timeout) {
        long cancelTimeoutMillis = timeout != Duration.ZERO ? timeout.toMillis() : 0L;
        boolean success = true;
        if (!context.waitForThreads(startMillis, cancelTimeoutMillis)) {
            success = false;
        }
        return success;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public PolyglotContextImpl createContext(OutputStream configOut, OutputStream configErr, InputStream configIn, boolean allowHostLookup, HostAccess hostAccess, PolyglotAccess polyglotAccess, boolean allowNativeAccess, boolean allowCreateThread, boolean allowHostIO, boolean allowHostClassLoading, boolean allowContextOptions, boolean allowExperimentalOptions, Predicate<String> classFilter, Map<String, String> options, Map<String, String[]> arguments, String[] onlyLanguagesArray, FileSystem fileSystem, Object logHandlerOrStream, boolean allowCreateProcess, ProcessHandler processHandler, EnvironmentAccess environmentAccess, Map<String, String> environment, ZoneId zone, Object limitsImpl, String currentWorkingDirectory, ClassLoader hostClassLoader, boolean allowValueSharing, boolean useSystemExit) {
        boolean hasThreadBindings;
        boolean hasContextBindings;
        boolean contextAddedToEngine;
        boolean replayEvents;
        PolyglotContextImpl context;
        Object fs;
        block53: {
            try {
                ProcessHandler useProcessHandler;
                InputStream useIn;
                FileSystem internalFs;
                String error;
                Object object = this.lock;
                synchronized (object) {
                    this.checkState();
                    if (this.boundEngine && !this.contexts.isEmpty()) {
                        throw PolyglotEngineException.illegalArgument("Automatically created engines cannot be used to create more than one context. Use Engine.newBuilder().build() to construct a new engine and pass it using Context.newBuilder().engine(engine).build().");
                    }
                }
                Set<String> allowedLanguages = Collections.emptySet();
                if (onlyLanguagesArray.length == 0) {
                    if (this.permittedLanguages.length != 0) {
                        allowedLanguages = new HashSet();
                        allowedLanguages.addAll(Arrays.asList(this.permittedLanguages));
                    }
                } else if (this.permittedLanguages.length == 0) {
                    allowedLanguages = new HashSet();
                    allowedLanguages.addAll(Arrays.asList(onlyLanguagesArray));
                } else {
                    EconomicSet<String> engineLanguages = EconomicSet.create();
                    engineLanguages.addAll(Arrays.asList(this.permittedLanguages));
                    allowedLanguages = new HashSet<String>();
                    allowedLanguages.addAll(Arrays.asList(onlyLanguagesArray));
                    for (String language : allowedLanguages) {
                        if (engineLanguages.contains(language)) continue;
                        throw PolyglotEngineException.illegalArgument(String.format("The language %s permitted for the created polyglot context was not permitted by the explicit engine. The engine only permits the use of the following languages: %s. Use Engine.newBuilder(\"%s\").build() to construct an engine with this language permitted or remove the language from the set of permitted languages when constructing the context in Context.newBuilder(...) to resolve this.", language, engineLanguages.toString(), language));
                    }
                }
                if ((error = this.getAPIAccess().validatePolyglotAccess(polyglotAccess, allowedLanguages.isEmpty() ? this.getLanguages().keySet() : allowedLanguages)) != null) {
                    throw PolyglotEngineException.illegalArgument(error);
                }
                if (!ALLOW_IO) {
                    if (fileSystem == null) {
                        fileSystem = FileSystems.newNoIOFileSystem();
                    }
                    fs = fileSystem;
                    internalFs = fileSystem;
                } else if (allowHostIO) {
                    fs = fileSystem != null ? fileSystem : FileSystems.newDefaultFileSystem();
                    internalFs = fs;
                } else {
                    fs = FileSystems.newNoIOFileSystem();
                    internalFs = FileSystems.newLanguageHomeFileSystem();
                }
                if (currentWorkingDirectory != null) {
                    fs.setCurrentWorkingDirectory(fs.parsePath(currentWorkingDirectory));
                    internalFs.setCurrentWorkingDirectory(internalFs.parsePath(currentWorkingDirectory));
                }
                OutputStream useOut = configOut == null || configOut == EngineAccessor.INSTRUMENT.getOut(this.out) ? this.out : EngineAccessor.INSTRUMENT.createDelegatingOutput(configOut, this.out);
                OutputStream useErr = configErr == null || configErr == EngineAccessor.INSTRUMENT.getOut(this.err) ? this.err : EngineAccessor.INSTRUMENT.createDelegatingOutput(configErr, this.err);
                Handler useHandler = PolyglotLoggers.asHandler(logHandlerOrStream);
                Handler handler = useHandler = useHandler != null ? useHandler : this.logHandler;
                useHandler = useHandler != null ? useHandler : PolyglotLoggers.createDefaultHandler(configErr == null ? EngineAccessor.INSTRUMENT.getOut(this.err) : configErr);
                InputStream inputStream = useIn = configIn == null ? this.in : configIn;
                if (allowCreateProcess) {
                    if (!ALLOW_CREATE_PROCESS) {
                        throw PolyglotEngineException.illegalArgument("Cannot allowCreateProcess() because the privilege is removed at image build time");
                    }
                    useProcessHandler = processHandler != null ? processHandler : this.getImpl().newDefaultProcessHandler();
                } else {
                    useProcessHandler = null;
                }
                if (!ALLOW_ENVIRONMENT_ACCESS && environmentAccess != EnvironmentAccess.NONE) {
                    throw PolyglotEngineException.illegalArgument("Cannot allow EnvironmentAccess because the privilege is removed at image build time");
                }
                PolyglotLimits polyglotLimits = (PolyglotLimits)limitsImpl;
                PolyglotContextConfig config = new PolyglotContextConfig(this, null, useOut, useErr, useIn, allowHostLookup, polyglotAccess, allowNativeAccess, allowCreateThread, allowHostClassLoading, allowContextOptions, allowExperimentalOptions, classFilter, arguments, allowedLanguages, options, (FileSystem)fs, internalFs, useHandler, allowCreateProcess, useProcessHandler, environmentAccess, environment, zone, polyglotLimits, hostClassLoader, hostAccess, allowValueSharing, useSystemExit, null, null, null, null);
                context = this.loadPreinitializedContext(config);
                replayEvents = false;
                contextAddedToEngine = false;
                if (context == null) {
                    Object object2 = this.lock;
                    synchronized (object2) {
                        this.checkState();
                        context = new PolyglotContextImpl(this, config);
                        this.addContext(context);
                        contextAddedToEngine = true;
                        break block53;
                    }
                }
                if (context.engine == this) {
                    replayEvents = true;
                }
            }
            catch (Throwable t) {
                throw PolyglotImpl.guestToHostException(this, t);
            }
        }
        try {
            if (replayEvents) {
                fs = context;
                synchronized (fs) {
                    context.resizeContextLocals(this.contextLocalLocations);
                    context.initializeInstrumentContextLocals(context.contextLocals);
                    context.resizeContextThreadLocals(this.contextThreadLocalLocations);
                    context.initializeInstrumentContextThreadLocals();
                }
            }
            try {
                fs = context;
                synchronized (fs) {
                    context.initializeContextLocals();
                    context.notifyContextCreated();
                }
            }
            catch (Throwable t) {
                if (contextAddedToEngine) {
                    this.disposeContext(context);
                    if (this.boundEngine) {
                        this.ensureClosed(false, false, false);
                    }
                }
                throw t;
            }
            hasContextBindings = EngineAccessor.INSTRUMENT.hasContextBindings(this);
            hasThreadBindings = EngineAccessor.INSTRUMENT.hasThreadBindings(this);
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(context.getHostContext(), t, false);
        }
        if (replayEvents && (hasContextBindings || hasThreadBindings)) {
            Object[] prev;
            try {
                prev = this.enter(context);
            }
            catch (Throwable t) {
                throw PolyglotImpl.guestToHostException(context.getHostContext(), t, false);
            }
            try {
                context.replayInstrumentationEvents();
            }
            catch (Throwable t) {
                throw PolyglotImpl.guestToHostException(context.getHostContext(), t, true);
            }
            finally {
                try {
                    this.leave(prev, context);
                }
                catch (Throwable t) {
                    throw PolyglotImpl.guestToHostException(context.getHostContext(), t, false);
                }
            }
        }
        this.checkTruffleRuntime();
        return context;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private PolyglotContextImpl loadPreinitializedContext(PolyglotContextConfig config) {
        PolyglotContextImpl context = null;
        boolean sharing = this.isSharingEnabled(config);
        if (sharing) {
            assert (this.preInitializedContext.get() == null) : "sharing enabled with preinitialized regular context. sharing requires context preinit per layer.";
            Object object = this.lock;
            synchronized (object) {
                PolyglotSharingLayer sharedLayer;
                Iterator<PolyglotSharingLayer> iterator = this.sharedLayers.iterator();
                while (iterator.hasNext() && (context = (sharedLayer = iterator.next()).loadPreinitializedContext(config)) == null) {
                }
            }
        } else {
            context = this.preInitializedContext.getAndSet(null);
        }
        if (context == null) {
            return null;
        }
        if (!this.getEngineOptionValues().get(PolyglotEngineOptions.UsePreInitializedContext).booleanValue()) {
            return null;
        }
        if (this.getEngineOptionValues().get(PolyglotEngineOptions.StaticObjectStorageStrategy) != PolyglotEngineOptions.StaticObjectStorageStrategy.getDefaultValue()) {
            return null;
        }
        FileSystems.PreInitializeContextFileSystem preInitFs = (FileSystems.PreInitializeContextFileSystem)context.config.fileSystem;
        preInitFs.onLoadPreinitializedContext(config.fileSystem);
        FileSystem oldFileSystem = config.fileSystem;
        config.fileSystem = preInitFs;
        preInitFs = (FileSystems.PreInitializeContextFileSystem)context.config.internalFileSystem;
        preInitFs.onLoadPreinitializedContext(config.internalFileSystem);
        FileSystem oldInternalFileSystem = config.internalFileSystem;
        config.internalFileSystem = preInitFs;
        boolean patchResult = false;
        Object object = this.lock;
        synchronized (object) {
            this.addContext(context);
        }
        try {
            patchResult = context.patch(config);
            object = this.lock;
        }
        catch (Throwable throwable) {
            Object object2 = this.lock;
            synchronized (object2) {
                this.removeContext(context);
            }
            if (patchResult && PolyglotEngineImpl.arePreInitializedLanguagesCompatible(context, config)) {
                HashSet<PolyglotInstrument> toCreate = null;
                for (PolyglotInstrument instrument : this.idToInstrument.values()) {
                    if (instrument.getOptionValuesIfExists() == null) continue;
                    if (toCreate == null) {
                        toCreate = new HashSet<PolyglotInstrument>();
                    }
                    toCreate.add(instrument);
                }
                if (toCreate != null) {
                    PolyglotEngineImpl.ensureInstrumentsCreated(toCreate);
                }
                Object object3 = this.lock;
                synchronized (object3) {
                    this.addContext(context);
                }
            }
            context.closeImpl(false);
            config.fileSystem = oldFileSystem;
            config.internalFileSystem = oldInternalFileSystem;
            if (sharing) {
                context = null;
            } else {
                PolyglotEngineImpl engine = new PolyglotEngineImpl(this);
                this.ensureClosed(true, false, false);
                Object object4 = engine.lock;
                synchronized (object4) {
                    context = new PolyglotContextImpl(engine, config);
                    engine.addContext(context);
                }
            }
            throw throwable;
        }
        synchronized (object) {
            this.removeContext(context);
        }
        if (patchResult && PolyglotEngineImpl.arePreInitializedLanguagesCompatible(context, config)) {
            HashSet<PolyglotInstrument> toCreate = null;
            for (PolyglotInstrument instrument : this.idToInstrument.values()) {
                if (instrument.getOptionValuesIfExists() == null) continue;
                if (toCreate == null) {
                    toCreate = new HashSet<PolyglotInstrument>();
                }
                toCreate.add(instrument);
            }
            if (toCreate != null) {
                PolyglotEngineImpl.ensureInstrumentsCreated(toCreate);
            }
            Object object5 = this.lock;
            synchronized (object5) {
                this.addContext(context);
            }
        } else {
            context.closeImpl(false);
            config.fileSystem = oldFileSystem;
            config.internalFileSystem = oldInternalFileSystem;
            if (sharing) {
                context = null;
            } else {
                PolyglotEngineImpl engine = new PolyglotEngineImpl(this);
                this.ensureClosed(true, false, false);
                Object object6 = engine.lock;
                synchronized (object6) {
                    context = new PolyglotContextImpl(engine, config);
                    engine.addContext(context);
                }
            }
        }
        return context;
    }

    private static boolean arePreInitializedLanguagesCompatible(PolyglotContextImpl context, PolyglotContextConfig config) {
        HashMap<String, PolyglotLanguageContext> preInitializedLanguages = new HashMap<String, PolyglotLanguageContext>();
        for (PolyglotLanguageContext languageContext : context.contexts) {
            if (!languageContext.isInitialized() || languageContext.language.isHost()) continue;
            preInitializedLanguages.put(languageContext.language.getId(), languageContext);
        }
        for (String allowedLanguage : config.allowedPublicLanguages) {
            PolyglotLanguageContext languageContext = (PolyglotLanguageContext)preInitializedLanguages.remove(allowedLanguage);
            if (languageContext == null) continue;
            preInitializedLanguages.keySet().removeAll(languageContext.getAccessibleLanguages(true).keySet());
        }
        return preInitializedLanguages.isEmpty();
    }

    private void checkTruffleRuntime() {
        if (this.getEngineOptionValues().get(PolyglotEngineOptions.WarnInterpreterOnly).booleanValue() && Truffle.getRuntime().getClass() == DefaultTruffleRuntime.class) {
            this.getEngineLogger().log(Level.WARNING, "The polyglot context is using an implementation that does not support runtime compilation.\nThe guest application code will therefore be executed in interpreted mode only.\nExecution only in interpreted mode will strongly impact the guest application performance.\nFor more information on using GraalVM see https://www.graalvm.org/java/quickstart/.\nTo disable this warning the '--engine.WarnInterpreterOnly=false' option or use the '-Dpolyglot.engine.WarnInterpreterOnly=false' system property.");
        }
    }

    OptionValuesImpl getEngineOptionValues() {
        return this.engineOptionValues;
    }

    Object getEngineLoggers() {
        return this.engineLoggers;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Supplier<Map<String, Collection<? extends TruffleFile.FileTypeDetector>>> getFileTypeDetectorsSupplier() {
        Supplier<Map<String, Collection<? extends TruffleFile.FileTypeDetector>>> res = this.fileTypeDetectorsSupplier;
        if (res == null) {
            Object object = this.lock;
            synchronized (object) {
                res = this.fileTypeDetectorsSupplier;
                if (res == null) {
                    ArrayList<LanguageCache> languageCaches = new ArrayList<LanguageCache>(this.idToLanguage.size());
                    for (PolyglotLanguage language : this.idToLanguage.values()) {
                        languageCaches.add(language.cache);
                    }
                    this.fileTypeDetectorsSupplier = res = FileSystems.newFileTypeDetectorsSupplier(languageCaches);
                }
            }
        }
        return res;
    }

    boolean needsEnter(PolyglotContextImpl context) {
        return PolyglotFastThreadLocals.needsEnter(context);
    }

    Object enterIfNeeded(PolyglotContextImpl context, boolean pollSafepoint) {
        CompilerAsserts.neverPartOfCompilation("not designed for compilation");
        if (this.needsEnter(context)) {
            return this.enterCached(context, pollSafepoint);
        }
        assert (PolyglotFastThreadLocals.getContext(null) != null);
        return NO_ENTER;
    }

    void leaveIfNeeded(Object prev, PolyglotContextImpl context) {
        CompilerAsserts.neverPartOfCompilation("not designed for compilation");
        if (prev != NO_ENTER) {
            this.leave((Object[])prev, context);
        }
    }

    Object[] enter(PolyglotContextImpl context) {
        if (CompilerDirectives.isPartialEvaluationConstant(this)) {
            return this.enterCached(context, true);
        }
        return this.enterBoundary(context);
    }

    @CompilerDirectives.TruffleBoundary
    private Object[] enterBoundary(PolyglotContextImpl context) {
        return this.enterCached(context, true);
    }

    Object[] enterCached(PolyglotContextImpl context, boolean pollSafepoint) {
        Object[] prev;
        PolyglotThreadInfo info = context.getCachedThread();
        boolean enterReverted = false;
        if (CompilerDirectives.injectBranchProbability(0.75, info.getThread() == Thread.currentThread())) {
            prev = info.enterInternal();
            if (CompilerDirectives.injectBranchProbability(0.9999, info == context.getCachedThread())) {
                try {
                    info.notifyEnter(this, context);
                }
                catch (Throwable e) {
                    info.leaveInternal(prev);
                    throw e;
                }
                return prev;
            }
            info.leaveInternal(prev);
            enterReverted = true;
        }
        prev = context.enterThreadChanged(true, enterReverted, pollSafepoint, false, false);
        assert (PolyglotEngineImpl.verifyContext(context));
        return prev;
    }

    private static boolean verifyContext(PolyglotContextImpl context) {
        PolyglotContextImpl.State localState = context.state;
        return context == PolyglotFastThreadLocals.getContext(null) || localState.isInvalidOrClosed();
    }

    void leave(Object[] prev, PolyglotContextImpl context) {
        if (CompilerDirectives.isPartialEvaluationConstant(this)) {
            this.leaveCached(prev, context);
        } else {
            this.leaveBoundary(prev, context);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private void leaveBoundary(Object[] prev, PolyglotContextImpl context) {
        this.leaveCached(prev, context);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void leaveCached(Object[] prev, PolyglotContextImpl context) {
        assert (context.state.isClosed() || PolyglotFastThreadLocals.getContext(null) == context) : "Cannot leave context that is currently not entered. Forgot to enter or leave a context?";
        boolean entered = true;
        PolyglotThreadInfo info = context.getCachedThread();
        if (CompilerDirectives.injectBranchProbability(0.75, info.getThread() == Thread.currentThread())) {
            try {
                info.notifyLeave(this, context);
            }
            finally {
                info.leaveInternal(prev);
                entered = false;
            }
            if (CompilerDirectives.injectBranchProbability(0.9999, info == context.getCachedThread())) {
                return;
            }
        }
        context.leaveThreadChanged(prev, true, entered, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    PolyglotLocals.LocalLocation[] addContextLocals(List<? extends PolyglotLocals.AbstractContextLocal<?>> newLocals) {
        PolyglotLocals.LocalLocation[] newLocations;
        StableLocalLocations newStableLocations;
        List<PolyglotContextImpl> aliveContexts;
        Iterator<PolyglotContextImpl> iterator = this.lock;
        synchronized (iterator) {
            StableLocalLocations stableLocations = this.contextLocalLocations;
            int index = stableLocations.locations.length;
            PolyglotLocals.LocalLocation[] locationsCopy = Arrays.copyOf(stableLocations.locations, stableLocations.locations.length + newLocals.size());
            for (PolyglotLocals.AbstractContextLocal<?> newLocal : newLocals) {
                locationsCopy[index] = newLocal.createLocation(index);
                newLocal.initializeLocation(locationsCopy[index]);
                ++index;
            }
            aliveContexts = this.collectAliveContexts();
            this.contextLocalLocations = newStableLocations = new StableLocalLocations(locationsCopy);
            stableLocations.assumption.invalidate("Context local added");
            newLocations = Arrays.copyOfRange(locationsCopy, stableLocations.locations.length, index);
        }
        for (PolyglotContextImpl context : aliveContexts) {
            context.resizeLocals(newStableLocations);
        }
        return newLocations;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    PolyglotLocals.LocalLocation[] addContextThreadLocals(List<? extends PolyglotLocals.AbstractContextThreadLocal<?>> newLocals) {
        PolyglotLocals.LocalLocation[] newLocations;
        StableLocalLocations newStableLocations;
        List<PolyglotContextImpl> aliveContexts;
        Iterator<PolyglotContextImpl> iterator = this.lock;
        synchronized (iterator) {
            StableLocalLocations stableLocations = this.contextThreadLocalLocations;
            int index = stableLocations.locations.length;
            PolyglotLocals.LocalLocation[] locationsCopy = Arrays.copyOf(stableLocations.locations, stableLocations.locations.length + newLocals.size());
            for (PolyglotLocals.AbstractContextThreadLocal<?> newLocal : newLocals) {
                locationsCopy[index] = newLocal.createLocation(index);
                newLocal.initializeLocation(locationsCopy[index]);
                ++index;
            }
            aliveContexts = this.collectAliveContexts();
            this.contextThreadLocalLocations = newStableLocations = new StableLocalLocations(locationsCopy);
            stableLocations.assumption.invalidate("Context thread local added");
            newLocations = Arrays.copyOfRange(locationsCopy, stableLocations.locations.length, index);
        }
        for (PolyglotContextImpl context : aliveContexts) {
            context.resizeThreadLocals(newStableLocations);
        }
        return newLocations;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void onVMShutdown() {
        if (DEBUG_MISSING_CLOSE) {
            PrintStream log = System.out;
            log.println("Missing close on vm shutdown: ");
            log.print(" InitializedLanguages:");
            Object object = this.lock;
            synchronized (object) {
                for (PolyglotContextImpl context : this.collectAliveContexts()) {
                    for (PolyglotLanguageContext langContext : context.contexts) {
                        if (langContext.env == null) continue;
                        log.print(langContext.language.getId());
                        log.print(", ");
                    }
                }
            }
            log.println();
            this.createdLocation.printStackTrace();
        }
        this.ensureClosed(false, true, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void addSystemThread(SystemThread.InstrumentSystemThread thread) {
        Object object = this.lock;
        synchronized (object) {
            if (!this.closed) {
                this.activeSystemThreads.add(thread);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void removeSystemThread(SystemThread.InstrumentSystemThread thread) {
        Object object = this.lock;
        synchronized (object) {
            this.activeSystemThreads.remove(thread);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static PolyglotEngineImpl getFallbackEngine() {
        if (fallbackEngine != null) return fallbackEngine;
        Class<PolyglotImpl> clazz = PolyglotImpl.class;
        synchronized (PolyglotImpl.class) {
            if (fallbackEngine != null) return fallbackEngine;
            PolyglotImpl polyglot = PolyglotImpl.getInstance();
            Object hostLanguage = polyglot.createHostLanguage(polyglot.createHostAccess());
            fallbackEngine = PolyglotImpl.getInstance().createDefaultEngine((TruffleLanguage<Object>)hostLanguage);
            // ** MonitorExit[var0] (shouldn't be in output)
            return fallbackEngine;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static void resetFallbackEngine() {
        PolyglotEngineImpl engineToClose = null;
        Class<PolyglotImpl> clazz = PolyglotImpl.class;
        synchronized (PolyglotImpl.class) {
            if (fallbackEngine != null) {
                engineToClose = fallbackEngine;
                fallbackEngine = null;
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            if (engineToClose != null) {
                engineToClose.ensureClosed(false, false, false);
            }
            return;
        }
    }

    String getVersion() {
        String version = HomeFinder.getInstance().getVersion();
        if (version.equals("snapshot")) {
            return "Development Build";
        }
        return version;
    }

    static {
        boolean createProcess = true;
        boolean environmentAccess = true;
        boolean io = true;
        String[] stringArray = DISABLED_PRIVILEGES;
        int n = stringArray.length;
        block10: for (int i = 0; i < n; ++i) {
            String privilege;
            switch (privilege = stringArray[i]) {
                case "createProcess": {
                    createProcess = false;
                    continue block10;
                }
                case "environmentAccess": {
                    environmentAccess = false;
                    continue block10;
                }
                case "io": {
                    io = false;
                    continue block10;
                }
                default: {
                    throw new Error("Invalid privilege name for DisablePrivileges: " + privilege);
                }
            }
        }
        ALLOW_CREATE_PROCESS = createProcess;
        ALLOW_ENVIRONMENT_ACCESS = environmentAccess;
        ALLOW_IO = io;
        NO_ENTER = new Object();
    }

    static final class StableLocalLocations {
        @CompilerDirectives.CompilationFinal(dimensions=1)
        final PolyglotLocals.LocalLocation[] locations;
        final Assumption assumption = Truffle.getRuntime().createAssumption();

        StableLocalLocations(PolyglotLocals.LocalLocation[] locations) {
            this.locations = locations;
        }
    }

    static final class LogConfig {
        final Map<String, Level> logLevels = new HashMap<String, Level>();
        String logFile;

        LogConfig() {
        }
    }

    @ExportLibrary(value=InteropLibrary.class)
    static final class InterruptExecution
    extends AbstractTruffleException {
        private static final long serialVersionUID = 8652484189010224048L;
        private final SourceSection sourceSection;

        InterruptExecution(Node location) {
            this(location, null);
        }

        InterruptExecution(SourceSection sourceSection) {
            this(null, sourceSection);
        }

        private InterruptExecution(Node location, SourceSection sourceSection) {
            super("Execution got interrupted.", location);
            this.sourceSection = sourceSection;
        }

        @ExportMessage
        ExceptionType getExceptionType() {
            return ExceptionType.INTERRUPT;
        }

        @ExportMessage
        public boolean hasSourceLocation() {
            if (this.sourceSection != null) {
                return true;
            }
            Node location = this.getLocation();
            return location != null && location.getEncapsulatingSourceSection() != null;
        }

        @ExportMessage(name="getSourceLocation")
        @CompilerDirectives.TruffleBoundary
        public SourceSection getSourceSection() throws UnsupportedMessageException {
            SourceSection section;
            if (this.sourceSection != null) {
                return this.sourceSection;
            }
            Node location = this.getLocation();
            SourceSection sourceSection = section = location != null ? location.getEncapsulatingSourceSection() : null;
            if (section == null) {
                throw UnsupportedMessageException.create();
            }
            return section;
        }
    }

    static final class CancelExecution
    extends ThreadDeath {
        private final Node location;
        private final SourceSection sourceSection;
        private final String cancelMessage;
        private final boolean resourceLimit;

        CancelExecution(Node location, String cancelMessage, boolean resourceLimit) {
            this(location, null, cancelMessage, resourceLimit);
        }

        CancelExecution(SourceSection sourceSection, String cancelMessage, boolean resourceLimit) {
            this(null, sourceSection, cancelMessage, resourceLimit);
        }

        private CancelExecution(Node location, SourceSection sourceSection, String cancelMessage, boolean resourceLimit) {
            this.location = location;
            this.sourceSection = sourceSection;
            this.cancelMessage = cancelMessage;
            this.resourceLimit = resourceLimit;
        }

        Node getLocation() {
            return this.location;
        }

        SourceSection getSourceLocation() {
            if (this.sourceSection != null) {
                return this.sourceSection;
            }
            return this.location == null ? null : this.location.getEncapsulatingSourceSection();
        }

        public boolean isResourceLimit() {
            return this.resourceLimit;
        }

        @Override
        public String getMessage() {
            if (this.cancelMessage == null) {
                return "Execution got cancelled.";
            }
            return this.cancelMessage;
        }
    }
}

