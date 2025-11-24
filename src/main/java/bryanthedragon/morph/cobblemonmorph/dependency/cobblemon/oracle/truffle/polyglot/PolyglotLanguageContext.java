
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.NodeLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.polyglot.DefaultTopScope;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.OptionValuesImpl;
import com.oracle.truffle.polyglot.PolyglotBindings;
import com.oracle.truffle.polyglot.PolyglotContextConfig;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineException;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotFastThreadLocals;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotLanguage;
import com.oracle.truffle.polyglot.PolyglotLanguageContextFactory;
import com.oracle.truffle.polyglot.PolyglotLanguageInstance;
import com.oracle.truffle.polyglot.PolyglotSharingLayer;
import com.oracle.truffle.polyglot.PolyglotSourceCache;
import com.oracle.truffle.polyglot.PolyglotThread;
import com.oracle.truffle.polyglot.PolyglotThreadInfo;
import com.oracle.truffle.polyglot.PolyglotValueDispatch;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import org.graalvm.collections.UnmodifiableEconomicSet;
import org.graalvm.polyglot.PolyglotAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.proxy.Proxy;

final class PolyglotLanguageContext
implements PolyglotImpl.VMObject {
    private static final TruffleLogger LOG = TruffleLogger.getLogger("engine", PolyglotLanguageContext.class);
    final PolyglotContextImpl context;
    final PolyglotLanguage language;
    final boolean eventsEnabled;
    private Thread creatingThread;
    private volatile boolean created;
    private volatile boolean initialized;
    volatile boolean finalized;
    volatile TruffleLanguage.ExitMode exited;
    @CompilerDirectives.CompilationFinal
    private volatile Value hostBindings;
    @CompilerDirectives.CompilationFinal
    private volatile Lazy lazy;
    @CompilerDirectives.CompilationFinal
    volatile TruffleLanguage.Env env;
    @CompilerDirectives.CompilationFinal
    private volatile List<Object> languageServices = Collections.emptyList();

    PolyglotLanguageContext(PolyglotContextImpl context, PolyglotLanguage language) {
        this.context = context;
        this.language = language;
        this.eventsEnabled = !language.isHost();
    }

    boolean isPolyglotBindingsAccessAllowed() {
        if (this.context.config.polyglotAccess == PolyglotAccess.ALL) {
            return true;
        }
        UnmodifiableEconomicSet<String> accessibleLanguages = this.getAPIAccess().getBindingsAccess(this.context.config.polyglotAccess);
        if (accessibleLanguages == null) {
            return true;
        }
        return accessibleLanguages.contains(this.language.getId());
    }

    boolean isPolyglotEvalAllowed(String targetLanguage) {
        if (this.context.config.polyglotAccess == PolyglotAccess.ALL) {
            return true;
        }
        if (targetLanguage != null && this.language.getId().equals(targetLanguage)) {
            return true;
        }
        UnmodifiableEconomicSet<String> accessibleLanguages = this.getAPIAccess().getEvalAccess(this.context.config.polyglotAccess, this.language.getId());
        if (accessibleLanguages == null || accessibleLanguages.isEmpty()) {
            return false;
        }
        if (accessibleLanguages.size() > 1 || !((String)accessibleLanguages.iterator().next()).equals(this.language.getId())) {
            return targetLanguage == null || accessibleLanguages.contains(targetLanguage);
        }
        return false;
    }

    Thread.UncaughtExceptionHandler getPolyglotExceptionHandler() {
        assert (this.env != null);
        return this.lazy.uncaughtExceptionHandler;
    }

    Map<String, LanguageInfo> getAccessibleLanguages(boolean allowInternalAndDependent) {
        Lazy l = this.lazy;
        if (l != null) {
            if (allowInternalAndDependent) {
                return this.lazy.accessibleInternalLanguages;
            }
            return this.lazy.accessiblePublicLanguages;
        }
        return null;
    }

    PolyglotLanguageInstance getLanguageInstanceOrNull() {
        Lazy l = this.lazy;
        if (l == null) {
            return null;
        }
        return l.languageInstance;
    }

    PolyglotLanguageInstance getLanguageInstance() {
        assert (this.env != null);
        return this.lazy.languageInstance;
    }

    private void checkThreadAccess(TruffleLanguage.Env localEnv) {
        assert (Thread.holdsLock(this.context));
        boolean singleThreaded = this.context.isSingleThreaded();
        Thread firstFailingThread = null;
        for (PolyglotThreadInfo threadInfo : this.context.getSeenThreads().values()) {
            if (EngineAccessor.LANGUAGE.isThreadAccessAllowed(localEnv, threadInfo.getThread(), singleThreaded)) continue;
            firstFailingThread = threadInfo.getThread();
            break;
        }
        if (firstFailingThread != null) {
            throw PolyglotContextImpl.throwDeniedThreadAccess(firstFailingThread, singleThreaded, Arrays.asList(this.language));
        }
    }

    Object getContextImpl() {
        TruffleLanguage.Env localEnv = this.env;
        if (localEnv != null) {
            return EngineAccessor.LANGUAGE.getContext(localEnv);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return null;
    }

    Object getPublicFileSystemContext() {
        Lazy l = this.lazy;
        if (l != null) {
            return l.publicFileSystemContext;
        }
        return null;
    }

    Object getInternalFileSystemContext() {
        Lazy l = this.lazy;
        if (l != null) {
            return l.internalFileSystemContext;
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Value getHostBindings() {
        assert (this.initialized);
        if (this.hostBindings == null) {
            PolyglotLanguageContext polyglotLanguageContext = this;
            synchronized (polyglotLanguageContext) {
                if (this.hostBindings == null) {
                    Object prev = this.language.engine.enterIfNeeded(this.context, true);
                    try {
                        Object scope = EngineAccessor.LANGUAGE.getScope(this.env);
                        if (scope == null) {
                            scope = new DefaultTopScope();
                        }
                        this.hostBindings = this.asValue(scope);
                    }
                    finally {
                        this.language.engine.leaveIfNeeded(prev, this.context);
                    }
                }
            }
        }
        return this.hostBindings;
    }

    Object getPolyglotGuestBindings() {
        assert (this.isInitialized());
        return this.lazy.polyglotGuestBindings;
    }

    boolean isInitialized() {
        return this.initialized;
    }

    CallTarget parseCached(PolyglotLanguage accessingLanguage, Source source, String[] argumentNames) throws AssertionError {
        this.ensureInitialized(accessingLanguage);
        PolyglotSourceCache cache = this.context.layer.getSourceCache();
        assert (cache != null);
        return cache.parseCached(this, source, argumentNames);
    }

    TruffleLanguage.Env requireEnv() {
        TruffleLanguage.Env localEnv = this.env;
        if (localEnv == null) {
            throw CompilerDirectives.shouldNotReachHere("No language context is active on this thread.");
        }
        return localEnv;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    boolean finalizeContext(boolean cancelOrExitOperation, boolean notifyInstruments) {
        boolean performFinalize = false;
        ReentrantLock lock = this.lazy.operationLock;
        lock.lock();
        try {
            if (!this.initialized) {
                boolean bl = false;
                return bl;
            }
            if (!this.finalized) {
                this.finalized = true;
                performFinalize = true;
            }
        }
        finally {
            lock.unlock();
        }
        if (performFinalize) {
            try {
                EngineAccessor.LANGUAGE.finalizeContext(this.env);
            }
            catch (Throwable t) {
                if (!cancelOrExitOperation || !(t instanceof AbstractTruffleException) && !(t instanceof PolyglotEngineImpl.CancelExecution) && !(t instanceof PolyglotContextImpl.ExitException)) {
                    throw t;
                }
                assert (this.context.state.isClosing());
                assert (this.context.state.isInvalidOrClosed());
                this.context.engine.getEngineLogger().log(Level.FINE, "Exception was thrown while finalizing a polyglot context that is being cancelled or exited. Such exceptions are expected during cancelling or exiting.", t);
            }
            if (this.eventsEnabled && notifyInstruments) {
                EngineAccessor.INSTRUMENT.notifyLanguageContextFinalized(this.context.engine, this.context.creatorTruffleContext, this.language.info);
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    boolean exitContext(TruffleLanguage.ExitMode exitMode, int exitCode) {
        boolean performExit = false;
        ReentrantLock lock = this.lazy.operationLock;
        lock.lock();
        try {
            if (!this.initialized) {
                boolean bl = false;
                return bl;
            }
            if (this.exited == null || exitMode.ordinal() > this.exited.ordinal()) {
                this.exited = exitMode;
                performExit = true;
            }
        }
        finally {
            lock.unlock();
        }
        if (performExit) {
            try {
                EngineAccessor.LANGUAGE.exitContext(this.env, exitMode, exitCode);
            }
            catch (Throwable t) {
                if (exitMode == TruffleLanguage.ExitMode.NATURAL || !(t instanceof AbstractTruffleException) && !(t instanceof PolyglotContextImpl.ExitException)) {
                    throw t;
                }
                if (t instanceof AbstractTruffleException && !this.context.state.isCancelling()) {
                    this.context.engine.getEngineLogger().log(Level.WARNING, "TruffleException thrown during exit notification! Languages are supposed to handle this kind of exceptions.", t);
                }
                this.context.engine.getEngineLogger().log(Level.FINE, "Exception thrown during exit notification!", t);
            }
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    boolean dispose() {
        try {
            TruffleLanguage.Env localEnv;
            PolyglotContextImpl polyglotContextImpl = this.context;
            synchronized (polyglotContextImpl) {
                localEnv = this.env;
                if (localEnv != null) {
                    if (!this.lazy.activePolyglotThreads.isEmpty()) {
                        throw new IllegalStateException("The language did not complete all polyglot threads but should have: " + this.lazy.activePolyglotThreads);
                    }
                    for (PolyglotThreadInfo threadInfo : this.context.getSeenThreads().values()) {
                        assert (threadInfo != PolyglotThreadInfo.NULL);
                        Thread thread = threadInfo.getThread();
                        if (thread == null) continue;
                        assert (!threadInfo.isPolyglotThread(this.context)) : "Polyglot threads must no longer be active in TruffleLanguage.finalizeContext, but polyglot thread " + thread.getName() + " is still active.";
                        if (!threadInfo.isCurrent() && threadInfo.isActive() && !this.context.state.isInvalidOrClosed()) {
                            throw PolyglotEngineException.illegalState("Another main thread was started while closing a polyglot context!");
                        }
                        EngineAccessor.LANGUAGE.disposeThread(localEnv, thread);
                    }
                }
            }
            if (localEnv != null) {
                EngineAccessor.LANGUAGE.dispose(localEnv);
                return true;
            }
            return false;
        }
        catch (Throwable t) {
            if (t instanceof AbstractTruffleException || t instanceof PolyglotEngineImpl.CancelExecution || t instanceof PolyglotContextImpl.ExitException) {
                throw new IllegalStateException("Guest language code was run during language disposal!", t);
            }
            throw t;
        }
    }

    void notifyDisposed(boolean notifyInstruments) {
        if (this.eventsEnabled && notifyInstruments) {
            EngineAccessor.INSTRUMENT.notifyLanguageContextDisposed(this.context.engine, this.context.creatorTruffleContext, this.language.info);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Object[] enterThread(PolyglotThread thread) {
        assert (this.isInitialized());
        assert (Thread.currentThread() == thread);
        PolyglotContextImpl polyglotContextImpl = this.context;
        synchronized (polyglotContextImpl) {
            Object[] prev = this.context.enterThreadChanged(true, false, true, false, true);
            this.lazy.activePolyglotThreads.add(thread);
            return prev;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void leaveAndDisposePolyglotThread(Object[] prev, PolyglotThread thread) {
        assert (this.isInitialized());
        PolyglotContextImpl polyglotContextImpl = this.context;
        synchronized (polyglotContextImpl) {
            this.context.leaveThreadChanged(prev, true, true, true);
            boolean removed = this.lazy.activePolyglotThreads.remove(thread);
            assert (removed) : "thread was not removed";
        }
    }

    boolean isCreated() {
        return this.created;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void ensureCreated(PolyglotLanguage accessingLanguage) {
        if (this.creatingThread == Thread.currentThread()) {
            throw PolyglotEngineException.illegalState(String.format("Cyclic access to language context for language %s. The context is currently being created.", this.language.getId()));
        }
        if (!this.created) {
            PolyglotLanguageInstance languageInstance;
            this.checkAccess(accessingLanguage);
            Map<String, Object> creatorConfig = this.context.creator == this.language ? this.context.config.creatorArguments : Collections.emptyMap();
            PolyglotContextConfig contextConfig = this.context.config;
            PolyglotSharingLayer layer = this.context.layer;
            Object object = this.context.engine.lock;
            synchronized (object) {
                if (this.language.isHost()) {
                    languageInstance = layer.allocateHostLanguage(this.language);
                } else {
                    this.context.claimSharingLayer(this.language);
                    languageInstance = layer.allocateInstance(this.context, this.language);
                }
            }
            object = this.context;
            synchronized (object) {
                if (!this.created) {
                    if (this.eventsEnabled) {
                        EngineAccessor.INSTRUMENT.notifyLanguageContextCreate(this.context.engine, this.context.creatorTruffleContext, this.language.info);
                    }
                    boolean wasCreated = false;
                    try {
                        TruffleLanguage.Env localEnv = EngineAccessor.LANGUAGE.createEnv(this, languageInstance.spi, contextConfig.out, contextConfig.err, contextConfig.in, creatorConfig, contextConfig.getLanguageOptionValues(this.language).copy(), contextConfig.getApplicationArguments(this.language));
                        Lazy localLazy = new Lazy(languageInstance, contextConfig);
                        if (layer.isSingleContext()) {
                            languageInstance.singleLanguageContext.update(this);
                        } else {
                            languageInstance.singleLanguageContext.invalidate();
                        }
                        this.checkThreadAccess(localEnv);
                        this.creatingThread = Thread.currentThread();
                        this.env = localEnv;
                        this.lazy = localLazy;
                        assert (EngineAccessor.LANGUAGE.getLanguage(this.env) != null);
                        try {
                            ArrayList<Object> languageServicesCollector = new ArrayList<Object>();
                            Object contextImpl = EngineAccessor.LANGUAGE.createEnvContext(localEnv, languageServicesCollector);
                            this.language.initializeContextClass(contextImpl);
                            String errorMessage = PolyglotLanguageContext.verifyServices(this.language.info, languageServicesCollector, this.language.cache.getServices());
                            if (errorMessage != null) {
                                throw PolyglotEngineException.illegalState(errorMessage);
                            }
                            PolyglotFastThreadLocals.notifyLanguageCreated(this);
                            this.languageServices = languageServicesCollector;
                            if (this.language.isHost()) {
                                this.context.initializeHostContext(this, this.context.config);
                            }
                            wasCreated = true;
                            if (this.eventsEnabled) {
                                EngineAccessor.INSTRUMENT.notifyLanguageContextCreated(this.context.engine, this.context.creatorTruffleContext, this.language.info);
                            }
                            this.context.invokeContextLocalsFactory(this.context.contextLocals, languageInstance.contextLocalLocations);
                            this.context.invokeContextThreadLocalFactory(languageInstance.contextThreadLocalLocations);
                            languageInstance = null;
                        }
                        catch (Throwable e) {
                            this.env = null;
                            this.lazy = null;
                            throw e;
                        }
                        finally {
                            this.creatingThread = null;
                        }
                        this.created = true;
                    }
                    finally {
                        if (!wasCreated && this.eventsEnabled) {
                            EngineAccessor.INSTRUMENT.notifyLanguageContextCreateFailed(this.context.engine, this.context.creatorTruffleContext, this.language.info);
                        }
                    }
                }
            }
        }
    }

    void close() {
        assert (Thread.holdsLock(this.context));
        this.created = false;
        this.lazy = null;
        this.env = null;
    }

    private static String verifyServices(LanguageInfo info, List<Object> registeredServices, Collection<String> expectedServices) {
        for (String expectedService : expectedServices) {
            boolean found = false;
            for (Object registeredService : registeredServices) {
                if (!PolyglotLanguageContext.isSubType(registeredService.getClass(), expectedService)) continue;
                found = true;
                break;
            }
            if (found) continue;
            return String.format("Language %s declares service %s but doesn't register it", info.getName(), expectedService);
        }
        return null;
    }

    private static boolean isSubType(Class<?> clazz, String serviceClass) {
        if (clazz == null) {
            return false;
        }
        if (serviceClass.equals(clazz.getName()) || serviceClass.equals(clazz.getCanonicalName())) {
            return true;
        }
        if (PolyglotLanguageContext.isSubType(clazz.getSuperclass(), serviceClass)) {
            return true;
        }
        for (Class<?> implementedInterface : clazz.getInterfaces()) {
            if (!PolyglotLanguageContext.isSubType(implementedInterface, serviceClass)) continue;
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    boolean ensureInitialized(PolyglotLanguage accessingLanguage) {
        boolean initialize2;
        block16: {
            this.ensureCreated(accessingLanguage);
            if (this.initialized) {
                return false;
            }
            initialize2 = false;
            ReentrantLock lock = this.lazy.operationLock;
            lock.lock();
            try {
                boolean bl = initialize2 = !this.initialized;
                if (!initialize2) break block16;
                if (this.eventsEnabled) {
                    EngineAccessor.INSTRUMENT.notifyLanguageContextInitialize(this.context.engine, this.context.creatorTruffleContext, this.language.info);
                }
                this.initialized = true;
                try {
                    EngineAccessor.LANGUAGE.initializeThread(this.env, Thread.currentThread());
                    EngineAccessor.LANGUAGE.postInitEnv(this.env);
                }
                catch (Throwable e) {
                    this.initialized = false;
                    try {
                        if (this.eventsEnabled) {
                            EngineAccessor.INSTRUMENT.notifyLanguageContextInitializeFailed(this.context.engine, this.context.creatorTruffleContext, this.language.info);
                        }
                    }
                    catch (Throwable inner) {
                        e.addSuppressed(inner);
                    }
                    throw e;
                }
                if (this.eventsEnabled) {
                    EngineAccessor.INSTRUMENT.notifyLanguageContextInitialized(this.context.engine, this.context.creatorTruffleContext, this.language.info);
                }
            }
            finally {
                lock.unlock();
            }
        }
        if (initialize2) {
            PolyglotContextImpl polyglotContextImpl = this.context;
            synchronized (polyglotContextImpl) {
                this.ensureMultiThreadingInitialized();
                for (PolyglotThreadInfo threadInfo : this.context.getSeenThreads().values()) {
                    Thread thread = threadInfo.getThread();
                    if (thread == Thread.currentThread()) continue;
                    EngineAccessor.LANGUAGE.initializeThread(this.env, thread);
                }
            }
        }
        return initialize2;
    }

    void ensureMultiThreadingInitialized() {
        assert (Thread.holdsLock(this.context));
        Lazy l = this.lazy;
        assert (l != null);
        if (!l.multipleThreadsInitialized && !this.context.isSingleThreaded()) {
            EngineAccessor.LANGUAGE.initializeMultiThreading(this.env);
            l.multipleThreadsInitialized = true;
        }
    }

    void checkAccess(PolyglotLanguage accessingLanguage) {
        this.context.checkClosedOrDisposing();
        if (!this.context.config.isAccessPermitted(accessingLanguage, this.language)) {
            throw PolyglotEngineException.illegalArgument(String.format("Access to language '%s' is not permitted. ", this.language.getId()));
        }
        RuntimeException initError = this.language.initError;
        if (initError != null) {
            throw PolyglotEngineException.illegalState(String.format("Initialization error: %s", initError.getMessage(), initError));
        }
    }

    @Override
    public PolyglotEngineImpl getEngine() {
        return this.context.getEngine();
    }

    boolean patch(PolyglotContextConfig newConfig) {
        if (this.isCreated()) {
            try {
                OptionValuesImpl newOptionValues = newConfig.getLanguageOptionValues(this.language).copy();
                this.lazy.computeAccessPermissions(newConfig);
                TruffleLanguage.Env newEnv = EngineAccessor.LANGUAGE.patchEnvContext(this.env, newConfig.out, newConfig.err, newConfig.in, Collections.emptyMap(), newOptionValues, newConfig.getApplicationArguments(this.language));
                if (newEnv != null) {
                    this.env = newEnv;
                    if (!this.language.isHost()) {
                        LOG.log(Level.FINE, "Successfully patched context of language: {0}", this.language.getId());
                    }
                    return true;
                }
                LOG.log(Level.FINE, "Failed to patch context of language: {0}", this.language.getId());
                return false;
            }
            catch (Throwable t) {
                LOG.log(Level.FINE, "Exception during patching context of language: {0}", this.language.getId());
                throw PolyglotLanguageContext.silenceException(RuntimeException.class, t);
            }
        }
        return true;
    }

    static <E extends Throwable> RuntimeException silenceException(Class<E> type, Throwable ex) throws E {
        throw ex;
    }

    <S> S lookupService(Class<S> type) {
        for (Object languageService : this.languageServices) {
            if (!type.isInstance(languageService)) continue;
            return type.cast(languageService);
        }
        return null;
    }

    @CompilerDirectives.TruffleBoundary
    Value asValue(Object guestValue) {
        assert (this.lazy != null);
        assert (guestValue != null);
        assert (!(guestValue instanceof Value));
        assert (!(guestValue instanceof Proxy));
        PolyglotValueDispatch cache = this.getLanguageInstance().lookupValueCache(this.context, guestValue);
        return this.context.engine.getAPIAccess().newValue(cache, this, guestValue);
    }

    public Object toGuestValue(Node node, Object receiver) {
        return this.context.toGuestValue(node, receiver, false);
    }

    @CompilerDirectives.TruffleBoundary
    Value[] toHostValues(Object[] values, int startIndex) {
        Value[] args = new Value[values.length - startIndex];
        for (int i = startIndex; i < values.length; ++i) {
            args[i - startIndex] = this.asValue(values[i]);
        }
        return args;
    }

    @CompilerDirectives.TruffleBoundary
    Value[] toHostValues(Object[] values) {
        Value[] args = new Value[values.length];
        for (int i = 0; i < args.length; ++i) {
            args[i] = this.asValue(values[i]);
        }
        return args;
    }

    public String toString() {
        return "PolyglotLanguageContext [language=" + this.language + ", initialized=" + (this.env != null) + "]";
    }

    public Object getLanguageView(Object receiver) {
        EngineAccessor.INTEROP.checkInteropType(receiver);
        InteropLibrary lib = InteropLibrary.getFactory().getUncached(receiver);
        if (lib.hasLanguage(receiver)) {
            try {
                if (!this.isCreated()) {
                    throw PolyglotEngineException.illegalState("Language not yet created. Initialize the language first to request a language view.");
                }
                if (lib.getLanguage(receiver) == this.lazy.languageInstance.spi.getClass()) {
                    return receiver;
                }
            }
            catch (UnsupportedMessageException e) {
                throw CompilerDirectives.shouldNotReachHere(e);
            }
        }
        return this.getLanguageViewNoCheck(receiver);
    }

    private boolean validLanguageView(Object result) {
        InteropLibrary lib = InteropLibrary.getFactory().getUncached(result);
        Class<?> languageClass = EngineAccessor.LANGUAGE.getLanguage(this.env).getClass();
        try {
            assert (lib.hasLanguage(result) && lib.getLanguage(result) == languageClass) : String.format("The returned language view of language '%s' must return the class '%s' for InteropLibrary.getLanguage.Fix the implementation of %s.getLanguageView to resolve this.", languageClass.getTypeName(), languageClass.getTypeName(), languageClass.getTypeName());
        }
        catch (UnsupportedMessageException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
        return true;
    }

    private boolean validScopedView(Object result, Node location) {
        InteropLibrary lib = InteropLibrary.getFactory().getUncached(result);
        Class<?> languageClass = EngineAccessor.LANGUAGE.getLanguage(this.env).getClass();
        try {
            assert (lib.hasLanguage(result) && lib.getLanguage(result) == languageClass) : String.format("The returned scoped view of language '%s' must return the class '%s' for InteropLibrary.getLanguage.Fix the implementation of %s.getView to resolve this.", languageClass.getTypeName(), languageClass.getTypeName(), location.getClass().getTypeName());
        }
        catch (UnsupportedMessageException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
        return true;
    }

    public Object getLanguageViewNoCheck(Object receiver) {
        Object result = EngineAccessor.LANGUAGE.getLanguageView(this.env, receiver);
        assert (this.validLanguageView(result));
        return result;
    }

    public Object getScopedView(Node location, Frame frame, Object value2) {
        PolyglotLanguageContext.validateLocationAndFrame(this.language.info, location, frame);
        Object languageView = this.getLanguageView(value2);
        Object result = NodeLibrary.getUncached().getView(location, frame, languageView);
        assert (this.validScopedView(result, location));
        return result;
    }

    private static void validateLocationAndFrame(LanguageInfo viewLanguage, Node location, Frame frame) {
        RootNode rootNode = location.getRootNode();
        if (rootNode == null) {
            throw PolyglotEngineException.illegalArgument(String.format("The location '%s' does not have a RootNode.", location));
        }
        LanguageInfo nodeLocation = rootNode.getLanguageInfo();
        if (nodeLocation == null) {
            throw PolyglotEngineException.illegalArgument(String.format("The location '%s' does not have a language associated.", location));
        }
        if (nodeLocation != viewLanguage) {
            throw PolyglotEngineException.illegalArgument(String.format("The view language '%s' must match the language of the location %s.", viewLanguage, nodeLocation));
        }
        if (!EngineAccessor.INSTRUMENT.isInstrumentable(location)) {
            throw PolyglotEngineException.illegalArgument(String.format("The location '%s' is not instrumentable but must be to request scoped views.", location));
        }
        if (!rootNode.getFrameDescriptor().equals(frame.getFrameDescriptor())) {
            throw PolyglotEngineException.illegalArgument(String.format("The frame provided does not originate from the location. Expected frame descriptor '%s' but was '%s'.", rootNode.getFrameDescriptor(), frame.getFrameDescriptor()));
        }
    }

    void patchInstance(PolyglotLanguageInstance hostInstance) {
        if (this.lazy != null) {
            this.lazy.languageInstance = hostInstance;
        }
    }

    static final class ToGuestValuesNode
    extends Node {
        @Node.Children
        private volatile ToGuestValueNode[] toGuestValue;
        @CompilerDirectives.CompilationFinal
        private volatile boolean needsCopy = false;
        @CompilerDirectives.CompilationFinal
        private volatile boolean generic = false;

        private ToGuestValuesNode() {
        }

        public Object[] apply(PolyglotLanguageContext context, Object[] args) {
            ToGuestValueNode[] nodes = this.toGuestValue;
            if (nodes == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                nodes = new ToGuestValueNode[args.length];
                for (int i = 0; i < nodes.length; ++i) {
                    nodes[i] = this.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                }
                this.toGuestValue = nodes;
            }
            if (args.length == nodes.length) {
                if (nodes.length == 0) {
                    return args;
                }
                Object[] newArgs = this.fastToGuestValuesUnroll(nodes, context, args);
                return newArgs;
            }
            if (!this.generic || nodes.length != 1) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                nodes = Arrays.copyOf(nodes, 1);
                if (nodes[0] == null) {
                    nodes[0] = this.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
                }
                this.toGuestValue = nodes;
                this.generic = true;
            }
            if (args.length == 0) {
                return args;
            }
            return this.fastToGuestValues(nodes[0], context, args);
        }

        @ExplodeLoop
        private Object[] fastToGuestValuesUnroll(ToGuestValueNode[] nodes, PolyglotLanguageContext context, Object[] args) {
            Object[] newArgs = this.needsCopy ? new Object[nodes.length] : args;
            for (int i = 0; i < nodes.length; ++i) {
                Object arg = args[i];
                Object newArg = nodes[i].execute(context, arg);
                if (this.needsCopy) {
                    newArgs[i] = newArg;
                    continue;
                }
                if (arg == newArg) continue;
                CompilerDirectives.transferToInterpreterAndInvalidate();
                newArgs = new Object[nodes.length];
                System.arraycopy(args, 0, newArgs, 0, args.length);
                newArgs[i] = newArg;
                this.needsCopy = true;
            }
            return newArgs;
        }

        private Object[] fastToGuestValues(ToGuestValueNode node, PolyglotLanguageContext context, Object[] args) {
            assert (this.toGuestValue[0] != null);
            Object[] newArgs = this.needsCopy ? new Object[args.length] : args;
            for (int i = 0; i < args.length; ++i) {
                Object arg = args[i];
                Object newArg = node.execute(context, arg);
                if (this.needsCopy) {
                    newArgs[i] = newArg;
                    continue;
                }
                if (arg == newArg) continue;
                CompilerDirectives.transferToInterpreterAndInvalidate();
                newArgs = new Object[args.length];
                System.arraycopy(args, 0, newArgs, 0, args.length);
                newArgs[i] = newArg;
                this.needsCopy = true;
            }
            return newArgs;
        }

        public static ToGuestValuesNode create() {
            return new ToGuestValuesNode();
        }
    }

    @GenerateUncached
    static abstract class ToGuestValueNode
    extends Node {
        ToGuestValueNode() {
        }

        abstract Object execute(PolyglotLanguageContext var1, Object var2);

        @Specialization(guards={"receiver == null"})
        Object doNull(PolyglotLanguageContext context, Object receiver) {
            return context.toGuestValue(this, receiver);
        }

        @Specialization(guards={"receiver != null", "receiver.getClass() == cachedReceiver"}, limit="3")
        Object doCached(PolyglotLanguageContext context, Object receiver, @Cached(value="receiver.getClass()") Class<?> cachedReceiver) {
            return context.toGuestValue(this, cachedReceiver.cast(receiver));
        }

        @Specialization(replaces={"doCached"})
        @CompilerDirectives.TruffleBoundary
        Object doUncached(PolyglotLanguageContext context, Object receiver) {
            return context.toGuestValue(this, receiver);
        }
    }

    private class PolyglotUncaughtExceptionHandler
    implements Thread.UncaughtExceptionHandler {
        private PolyglotUncaughtExceptionHandler() {
        }

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            if (!(e instanceof ThreadDeath)) {
                TruffleLanguage.Env currentEnv = PolyglotLanguageContext.this.env;
                if (currentEnv != null) {
                    try {
                        e.printStackTrace(new PrintStream(currentEnv.err()));
                    }
                    catch (Throwable exc) {
                        e.printStackTrace();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        }
    }

    static final class ValueMigrationException
    extends AbstractTruffleException {
        ValueMigrationException(String message, Node location) {
            super(message, location);
        }
    }

    static final class ToHostValueNode {
        final AbstractPolyglotImpl.APIAccess apiAccess;
        @CompilerDirectives.CompilationFinal
        volatile Class<?> cachedClass;
        @CompilerDirectives.CompilationFinal
        volatile PolyglotValueDispatch cachedValue;

        private ToHostValueNode(AbstractPolyglotImpl polyglot) {
            this.apiAccess = polyglot.getAPIAccess();
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        Value execute(PolyglotLanguageContext languageContext, Object value2) {
            Object receiver = value2;
            Class<?> cachedClassLocal = this.cachedClass;
            if (cachedClassLocal == Generic.class) return languageContext.asValue(value2);
            if (cachedClassLocal == null) {
                PolyglotValueDispatch cache;
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.cachedClass = receiver.getClass();
                this.cachedValue = cache = languageContext.lazy.languageInstance.lookupValueCache(languageContext.context, receiver);
                return languageContext.asValue(value2);
            } else if (value2.getClass() == cachedClassLocal) {
                receiver = CompilerDirectives.inInterpreter() ? receiver : CompilerDirectives.castExact(receiver, cachedClassLocal);
                PolyglotValueDispatch cache = this.cachedValue;
                if (cache != null) return this.apiAccess.newValue(cache, languageContext, receiver);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return languageContext.asValue(value2);
            } else {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.cachedClass = Generic.class;
                this.cachedValue = null;
            }
            return languageContext.asValue(value2);
        }

        public static ToHostValueNode create(AbstractPolyglotImpl polyglot) {
            return new ToHostValueNode(polyglot);
        }
    }

    static final class Generic {
        private Generic() {
            throw CompilerDirectives.shouldNotReachHere("no instances");
        }
    }

    final class Lazy {
        final Set<PolyglotThread> activePolyglotThreads;
        final Object polyglotGuestBindings;
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
        @CompilerDirectives.CompilationFinal
        PolyglotLanguageInstance languageInstance;
        @CompilerDirectives.CompilationFinal
        Map<String, LanguageInfo> accessibleInternalLanguages;
        @CompilerDirectives.CompilationFinal
        Map<String, LanguageInfo> accessiblePublicLanguages;
        final Object internalFileSystemContext;
        final Object publicFileSystemContext;
        final ReentrantLock operationLock;
        private boolean multipleThreadsInitialized;

        Lazy(PolyglotLanguageInstance languageInstance, PolyglotContextConfig config) {
            this.languageInstance = languageInstance;
            this.activePolyglotThreads = new HashSet<PolyglotThread>();
            this.polyglotGuestBindings = new PolyglotBindings(PolyglotLanguageContext.this);
            this.uncaughtExceptionHandler = new PolyglotUncaughtExceptionHandler();
            this.computeAccessPermissions(config);
            this.publicFileSystemContext = EngineAccessor.LANGUAGE.createFileSystemContext(PolyglotLanguageContext.this, config.fileSystem);
            this.internalFileSystemContext = EngineAccessor.LANGUAGE.createFileSystemContext(PolyglotLanguageContext.this, config.internalFileSystem);
            this.operationLock = new ReentrantLock();
        }

        void computeAccessPermissions(PolyglotContextConfig config) {
            this.accessibleInternalLanguages = this.computeAccessibleLanguages(config, true);
            this.accessiblePublicLanguages = this.computeAccessibleLanguages(config, false);
        }

        private Map<String, LanguageInfo> computeAccessibleLanguages(PolyglotContextConfig config, boolean internal) {
            HashSet<String> resolveLanguages;
            PolyglotLanguage thisLanguage = this.languageInstance.language;
            if (thisLanguage.isHost()) {
                return this.languageInstance.getEngine().idToInternalLanguageInfo;
            }
            boolean embedderAllAccess = config.allowedPublicLanguages.isEmpty();
            PolyglotEngineImpl engine = this.languageInstance.getEngine();
            Set<String> configuredAccess = null;
            UnmodifiableEconomicSet<String> configured = engine.getAPIAccess().getEvalAccess(config.polyglotAccess, thisLanguage.getId());
            if (configured != null) {
                configuredAccess = new HashSet<String>();
                configuredAccess.addAll(Arrays.asList(configured.toArray((String[])new String[configured.size()])));
            }
            if (embedderAllAccess) {
                if (configuredAccess == null) {
                    if (internal) {
                        return engine.idToInternalLanguageInfo;
                    }
                    resolveLanguages = new HashSet();
                    resolveLanguages.addAll(engine.idToInternalLanguageInfo.keySet());
                } else {
                    resolveLanguages = new HashSet<String>(configuredAccess);
                    resolveLanguages.add(thisLanguage.getId());
                }
            } else {
                if (configuredAccess == null) {
                    configuredAccess = config.allowedPublicLanguages;
                }
                resolveLanguages = new HashSet(configuredAccess);
                resolveLanguages.add(thisLanguage.getId());
            }
            LinkedHashMap<String, LanguageInfo> resolvedLanguages = new LinkedHashMap<String, LanguageInfo>();
            for (String string : resolveLanguages) {
                PolyglotLanguage resolvedLanguage = engine.idToLanguage.get(string);
                if (resolvedLanguage == null || !internal && resolvedLanguage.cache.isInternal()) continue;
                resolvedLanguages.put(string, resolvedLanguage.info);
            }
            if (internal) {
                this.addDependentLanguages(engine, resolvedLanguages, thisLanguage);
            }
            if (internal) {
                for (Map.Entry entry : this.languageInstance.getEngine().idToLanguage.entrySet()) {
                    if (!((PolyglotLanguage)entry.getValue()).cache.isInternal()) continue;
                    resolvedLanguages.put((String)entry.getKey(), ((PolyglotLanguage)entry.getValue()).info);
                }
                assert (this.assertPermissionsConsistent(resolvedLanguages, this.languageInstance.language, config));
            }
            return resolvedLanguages;
        }

        private boolean assertPermissionsConsistent(Map<String, LanguageInfo> resolvedLanguages, PolyglotLanguage thisLanguage, PolyglotContextConfig config) {
            for (Map.Entry<String, PolyglotLanguage> entry : this.languageInstance.getEngine().idToLanguage.entrySet()) {
                boolean permitted = config.isAccessPermitted(thisLanguage, entry.getValue());
                assert (permitted == resolvedLanguages.containsKey(entry.getKey())) : "inconsistent access permissions";
            }
            return true;
        }

        private void addDependentLanguages(PolyglotEngineImpl engine, Map<String, LanguageInfo> resolvedLanguages, PolyglotLanguage currentLanguage) {
            for (String dependentLanguage : currentLanguage.cache.getDependentLanguages()) {
                PolyglotLanguage dependent = engine.idToLanguage.get(dependentLanguage);
                if (dependent == null || resolvedLanguages.containsKey(dependentLanguage)) continue;
                resolvedLanguages.put(dependentLanguage, dependent.info);
                this.addDependentLanguages(engine, resolvedLanguages, dependent);
            }
        }
    }
}

