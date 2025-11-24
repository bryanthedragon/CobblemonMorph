
package com.oracle.truffle.api;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.ContextLocal;
import com.oracle.truffle.api.ContextThreadLocal;
import com.oracle.truffle.api.InstrumentInfo;
import com.oracle.truffle.api.LanguageAccessor;
import com.oracle.truffle.api.ThreadLocalAction;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.impl.ReadOnlyArrayList;
import com.oracle.truffle.api.io.TruffleProcessBuilder;
import com.oracle.truffle.api.nodes.ExecutableNode;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.options.OptionValues;

public abstract class TruffleLanguage<C> {
    @CompilerDirectives.CompilationFinal
    LanguageInfo languageInfo;
    @CompilerDirectives.CompilationFinal
    Object polyglotLanguageInstance;
    List<ContextThreadLocal<?>> contextThreadLocals;
    List<ContextLocal<?>> contextLocals;

    protected TruffleLanguage() {
    }

    protected boolean areOptionsCompatible(OptionValues firstOptions, OptionValues newOptions) {
        return true;
    }

    protected abstract C createContext(Env var1);

    protected void initializeContext(C context) throws Exception {
    }

    protected void finalizeContext(C context) {
    }

    protected void exitContext(C context, ExitMode exitMode, int exitCode) {
    }

    protected void initializeMultipleContexts() {
    }

    protected void disposeContext(C context) {
    }

    protected CallTarget parse(ParsingRequest request) throws Exception {
        throw new UnsupportedOperationException(String.format("Override parse method of %s, it will be made abstract in future version of Truffle API!", this.getClass().getName()));
    }

    protected ExecutableNode parse(InlineParsingRequest request) throws Exception {
        return null;
    }

    protected OptionDescriptors getOptionDescriptors() {
        return OptionDescriptors.EMPTY;
    }

    protected boolean patchContext(C context, Env newEnv) {
        return false;
    }

    protected boolean isThreadAccessAllowed(Thread thread, boolean singleThreaded) {
        return singleThreaded;
    }

    protected void initializeMultiThreading(C context) {
    }

    protected void initializeThread(C context, Thread thread) {
    }

    protected void disposeThread(C context, Thread thread) {
    }

    protected Object getScope(C context) {
        return null;
    }

    protected boolean isVisible(C context, Object value2) {
        return true;
    }

    protected Object getLanguageView(C context, Object value2) {
        return null;
    }

    CallTarget parse(Source source, String ... argumentNames) {
        CallTarget target;
        ParsingRequest request = new ParsingRequest(source, argumentNames);
        try {
            target = request.parse(this);
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        finally {
            request.dispose();
        }
        return target;
    }

    ExecutableNode parseInline(Source source, Node context, MaterializedFrame frame) {
        ExecutableNode snippet;
        assert (context != null);
        InlineParsingRequest request = new InlineParsingRequest(source, context, frame);
        try {
            snippet = request.parse(this);
        }
        catch (RuntimeException ex) {
            throw ex;
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        finally {
            request.dispose();
        }
        return snippet;
    }

    @Deprecated(since="21.3")
    protected static <T extends TruffleLanguage<?>> T getCurrentLanguage(Class<T> languageClass) {
        try {
            return LanguageAccessor.engineAccess().getCurrentLanguage(languageClass);
        }
        catch (Throwable t) {
            CompilerDirectives.transferToInterpreter();
            throw Env.engineToLanguageException(t);
        }
    }

    @Deprecated(since="21.3")
    protected static <C, T extends TruffleLanguage<C>> C getCurrentContext(Class<T> languageClass) {
        try {
            return LanguageAccessor.ENGINE.getCurrentContext(languageClass);
        }
        catch (Throwable t) {
            CompilerDirectives.transferToInterpreter();
            throw Env.engineToLanguageException(t);
        }
    }

    protected final <T> ContextLocal<T> createContextLocal(ContextLocalFactory<C, T> factory) {
        ContextLocal local = LanguageAccessor.ENGINE.createLanguageContextLocal(factory);
        if (this.contextLocals == null) {
            this.contextLocals = new ArrayList();
        }
        try {
            this.contextLocals.add(local);
        }
        catch (UnsupportedOperationException e) {
            throw new IllegalStateException("The set of context locals is frozen. Context locals can only be created during construction of the TruffleLanguage subclass.");
        }
        return local;
    }

    protected final <T> ContextThreadLocal<T> createContextThreadLocal(ContextThreadLocalFactory<C, T> factory) {
        ContextThreadLocal local = LanguageAccessor.ENGINE.createLanguageContextThreadLocal(factory);
        if (this.contextThreadLocals == null) {
            this.contextThreadLocals = new ArrayList();
        }
        try {
            this.contextThreadLocals.add(local);
        }
        catch (UnsupportedOperationException e) {
            throw new IllegalStateException("The set of context thread locals is frozen. Context thread locals can only be created during construction of the TruffleLanguage subclass.");
        }
        return local;
    }

    protected final String getLanguageHome() {
        try {
            return LanguageAccessor.ENGINE.getLanguageHome(this.languageInfo);
        }
        catch (Throwable t) {
            throw Env.engineToLanguageException(t);
        }
    }

    protected final int getAsynchronousStackDepth() {
        assert (this.polyglotLanguageInstance != null) : "getAsynchronousStackDepth not supported for host language";
        return LanguageAccessor.engineAccess().getAsynchronousStackDepth(this.polyglotLanguageInstance);
    }

    public static enum ExitMode {
        NATURAL,
        HARD;

    }

    public static enum ContextPolicy {
        EXCLUSIVE,
        REUSE,
        SHARED;

    }

    public static abstract class ContextReference<C> {
        protected ContextReference() {
        }

        public abstract C get(Node var1);

        public static <T extends TruffleLanguage<C>, C> ContextReference<C> create(Class<T> languageClass) {
            Objects.requireNonNull(languageClass);
            return LanguageAccessor.ENGINE.createContextReference(languageClass);
        }
    }

    public static abstract class LanguageReference<L extends TruffleLanguage> {
        protected LanguageReference() {
        }

        public abstract L get(Node var1);

        public static <T extends TruffleLanguage<?>> LanguageReference<T> create(Class<T> languageClass) {
            Objects.requireNonNull(languageClass);
            return LanguageAccessor.ENGINE.createLanguageReference(languageClass);
        }
    }

    public static final class Env {
        static final Object UNSET_CONTEXT = new Object();
        final Object polyglotLanguageContext;
        final TruffleLanguage<Object> spi;
        private final InputStream in;
        private final OutputStream err;
        private final OutputStream out;
        private final Map<String, Object> config;
        private final OptionValues options;
        private final String[] applicationArguments;
        @CompilerDirectives.CompilationFinal
        volatile List<Object> services;
        @CompilerDirectives.CompilationFinal
        volatile Object context = UNSET_CONTEXT;
        @CompilerDirectives.CompilationFinal
        volatile Assumption contextUnchangedAssumption = Truffle.getRuntime().createAssumption("Language context unchanged");
        @CompilerDirectives.CompilationFinal
        volatile boolean initialized = false;
        @CompilerDirectives.CompilationFinal
        private volatile Assumption initializedUnchangedAssumption = Truffle.getRuntime().createAssumption("Language context initialized unchanged");
        @CompilerDirectives.CompilationFinal
        volatile boolean valid;
        volatile List<Object> languageServicesCollector;

        Env(Object polyglotLanguageContext, TruffleLanguage<?> language, OutputStream out, OutputStream err, InputStream in, Map<String, Object> config, OptionValues options, String[] applicationArguments) {
            this.polyglotLanguageContext = polyglotLanguageContext;
            this.spi = language;
            this.in = in;
            this.err = err;
            this.out = out;
            this.config = config;
            this.options = options;
            this.applicationArguments = applicationArguments == null ? new String[]{} : applicationArguments;
            this.valid = true;
        }

        TruffleFile.FileSystemContext getPublicFileSystemContext() {
            return (TruffleFile.FileSystemContext)LanguageAccessor.engineAccess().getPublicFileSystemContext(this.polyglotLanguageContext);
        }

        TruffleFile.FileSystemContext getInternalFileSystemContext() {
            return (TruffleFile.FileSystemContext)LanguageAccessor.engineAccess().getInternalFileSystemContext(this.polyglotLanguageContext);
        }

        Object getPolyglotLanguageContext() {
            return this.polyglotLanguageContext;
        }

        TruffleLanguage<Object> getSpi() {
            return this.spi;
        }

        void checkDisposed() {
            if (LanguageAccessor.engineAccess().isDisposed(this.polyglotLanguageContext)) {
                throw new IllegalStateException("Language environment is already disposed.");
            }
            if (!this.valid) {
                throw new IllegalStateException("Language environment is already invalidated.");
            }
        }

        public OptionValues getOptions() {
            return this.options;
        }

        public String[] getApplicationArguments() {
            return this.applicationArguments;
        }

        public boolean isCreateThreadAllowed() {
            try {
                return LanguageAccessor.engineAccess().isCreateThreadAllowed(this.polyglotLanguageContext);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public Thread createThread(Runnable runnable) {
            return this.createThread(runnable, null);
        }

        @CompilerDirectives.TruffleBoundary
        public Thread createThread(Runnable runnable, TruffleContext context) {
            return this.createThread(runnable, context, null, 0L);
        }

        @CompilerDirectives.TruffleBoundary
        public Thread createThread(Runnable runnable, TruffleContext context, ThreadGroup group) {
            return this.createThread(runnable, context, group, 0L);
        }

        @CompilerDirectives.TruffleBoundary
        public Thread createThread(Runnable runnable, TruffleContext context, ThreadGroup group, long stackSize) {
            try {
                return LanguageAccessor.engineAccess().createThread(this.polyglotLanguageContext, runnable, context != null ? context.polyglotContext : null, group, stackSize);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public Thread createSystemThread(Runnable runnable) {
            return this.createSystemThread(runnable, null);
        }

        @CompilerDirectives.TruffleBoundary
        public Thread createSystemThread(Runnable runnable, ThreadGroup threadGroup) {
            try {
                return LanguageAccessor.engineAccess().createLanguageSystemThread(this.polyglotLanguageContext, runnable, threadGroup);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @Deprecated
        public TruffleContext.Builder newContextBuilder() {
            return this.newInnerContextBuilder(new String[0]).initializeCreatorContext(true).inheritAllAccess(true);
        }

        public TruffleContext.Builder newInnerContextBuilder(String ... permittedLanguages) {
            TruffleContext truffleContext = TruffleContext.EMPTY;
            Objects.requireNonNull(truffleContext);
            return truffleContext.new TruffleContext.Builder(this).permittedLanguages(permittedLanguages);
        }

        @CompilerDirectives.TruffleBoundary
        public Object getPolyglotBindings() {
            try {
                if (!this.isPolyglotBindingsAccessAllowed()) {
                    throw new SecurityException("Polyglot bindings are not accessible for this language. Use --polyglot or allowPolyglotAccess when building the context.");
                }
                return LanguageAccessor.engineAccess().getPolyglotBindingsForLanguage(this.polyglotLanguageContext);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public Object importSymbol(String symbolName) {
            try {
                if (!this.isPolyglotBindingsAccessAllowed()) {
                    throw new SecurityException("Polyglot bindings are not accessible for this language. Use --polyglot or allowPolyglotAccess when building the context.");
                }
                return LanguageAccessor.engineAccess().importSymbol(this.polyglotLanguageContext, this, symbolName);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public void exportSymbol(String symbolName, Object value2) {
            try {
                if (!this.isPolyglotBindingsAccessAllowed()) {
                    throw new SecurityException("Polyglot bindings are not accessible for this language. Use --polyglot or allowPolyglotAccess when building the context.");
                }
                LanguageAccessor.engineAccess().exportSymbol(this.polyglotLanguageContext, symbolName, value2);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public boolean isHostLookupAllowed() {
            try {
                return LanguageAccessor.engineAccess().isHostAccessAllowed(this.polyglotLanguageContext, this);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public void addToHostClassPath(TruffleFile entry) {
            try {
                Objects.requireNonNull(entry);
                LanguageAccessor.engineAccess().addToHostClassPath(this.polyglotLanguageContext, entry);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public Object lookupHostSymbol(String symbolName) {
            try {
                return LanguageAccessor.engineAccess().lookupHostSymbol(this.polyglotLanguageContext, this, symbolName);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        public boolean isHostObject(Object value2) {
            try {
                return LanguageAccessor.engineAccess().isHostObject(this.polyglotLanguageContext, value2);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        public Object asHostObject(Object value2) {
            if (!this.isHostObject(value2)) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new ClassCastException();
            }
            try {
                return LanguageAccessor.engineAccess().asHostObject(this.polyglotLanguageContext, value2);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        public Object asGuestValue(Object hostObject) {
            try {
                return LanguageAccessor.engineAccess().toGuestValue(null, hostObject, this.polyglotLanguageContext);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        public Object asBoxedGuestValue(Object guestObject) {
            try {
                return LanguageAccessor.engineAccess().asBoxedGuestValue(guestObject, this.polyglotLanguageContext);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        public boolean isHostFunction(Object value2) {
            try {
                return LanguageAccessor.engineAccess().isHostFunction(this.polyglotLanguageContext, value2);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        public Object findMetaObject(Object value2) {
            try {
                return LanguageAccessor.engineAccess().findMetaObjectForLanguage(this.polyglotLanguageContext, value2);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        public boolean isHostException(Throwable exception) {
            try {
                return LanguageAccessor.engineAccess().isHostException(this.polyglotLanguageContext, exception);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        public Throwable asHostException(Throwable exception) {
            try {
                return LanguageAccessor.engineAccess().asHostException(this.polyglotLanguageContext, exception);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        public boolean isHostSymbol(Object guestObject) {
            try {
                return LanguageAccessor.engineAccess().isHostSymbol(this.polyglotLanguageContext, guestObject);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public Object asHostSymbol(Class<?> symbolClass) {
            try {
                return LanguageAccessor.engineAccess().asHostSymbol(this.polyglotLanguageContext, symbolClass);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public boolean isInnerContextOptionsAllowed() {
            try {
                return LanguageAccessor.engineAccess().isInnerContextOptionsAllowed(this.polyglotLanguageContext, this);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        public boolean isIOAllowed() {
            try {
                return LanguageAccessor.engineAccess().isIOAllowed(this.polyglotLanguageContext, this);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public boolean isNativeAccessAllowed() {
            try {
                return LanguageAccessor.engineAccess().isNativeAccessAllowed(this.polyglotLanguageContext, this);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public boolean isPolyglotEvalAllowed() {
            try {
                return LanguageAccessor.engineAccess().isPolyglotEvalAllowed(this.polyglotLanguageContext);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public boolean isPolyglotBindingsAccessAllowed() {
            try {
                return LanguageAccessor.engineAccess().isPolyglotBindingsAccessAllowed(this.polyglotLanguageContext);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public boolean isMimeTypeSupported(String mimeType) {
            this.checkDisposed();
            try {
                return LanguageAccessor.engineAccess().isMimeTypeSupported(this.polyglotLanguageContext, mimeType);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public CallTarget parseInternal(Source source, String ... argumentNames) {
            CompilerAsserts.neverPartOfCompilation();
            this.checkDisposed();
            try {
                return LanguageAccessor.engineAccess().parseForLanguage(this.polyglotLanguageContext, source, argumentNames, true);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public CallTarget parsePublic(Source source, String ... argumentNames) {
            CompilerAsserts.neverPartOfCompilation();
            this.checkDisposed();
            try {
                return LanguageAccessor.engineAccess().parseForLanguage(this.polyglotLanguageContext, source, argumentNames, false);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public InputStream in() {
            this.checkDisposed();
            return this.in;
        }

        @CompilerDirectives.TruffleBoundary
        public OutputStream out() {
            this.checkDisposed();
            return this.out;
        }

        @CompilerDirectives.TruffleBoundary
        public OutputStream err() {
            this.checkDisposed();
            return this.err;
        }

        @CompilerDirectives.TruffleBoundary
        public <T> T lookup(Class<T> type) {
            this.checkDisposed();
            for (Object obj : this.services) {
                if (!type.isInstance(obj)) continue;
                return type.cast(obj);
            }
            return null;
        }

        @CompilerDirectives.TruffleBoundary
        public <S> S lookup(InstrumentInfo instrument, Class<S> type) {
            if (this.isPreInitialization()) {
                throw new IllegalStateException("Instrument lookup is not allowed during context pre-initialization.");
            }
            try {
                return LanguageAccessor.engineAccess().lookup(instrument, type);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public <S> S lookup(LanguageInfo language, Class<S> type) {
            if (this.getSpi().languageInfo == language) {
                throw new IllegalArgumentException("Cannot request services from the current language.");
            }
            try {
                Objects.requireNonNull(language);
                return LanguageAccessor.engineAccess().lookupService(this.polyglotLanguageContext, language, this.getSpi().languageInfo, type);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public boolean initializeLanguage(LanguageInfo targetLanguage) {
            Objects.requireNonNull(targetLanguage, "TargetLanguage must be non null.");
            try {
                return LanguageAccessor.engineAccess().initializeLanguage(this.polyglotLanguageContext, targetLanguage);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public Map<String, LanguageInfo> getInternalLanguages() {
            try {
                return LanguageAccessor.engineAccess().getInternalLanguages(this.polyglotLanguageContext);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public Map<String, LanguageInfo> getPublicLanguages() {
            try {
                return LanguageAccessor.engineAccess().getPublicLanguages(this.polyglotLanguageContext);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public Map<String, InstrumentInfo> getInstruments() {
            try {
                return LanguageAccessor.engineAccess().getInstruments(this.polyglotLanguageContext);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        public ZoneId getTimeZone() {
            this.checkDisposed();
            try {
                return LanguageAccessor.engineAccess().getTimeZone(this.polyglotLanguageContext);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public Map<String, Object> getConfig() {
            this.checkDisposed();
            return this.config;
        }

        public TruffleContext getContext() {
            try {
                return LanguageAccessor.engineAccess().getTruffleContext(this.polyglotLanguageContext);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public boolean isPreInitialization() {
            try {
                return LanguageAccessor.engineAccess().inContextPreInitialization(this.polyglotLanguageContext);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleFile getPublicTruffleFile(String path) {
            this.checkDisposed();
            TruffleFile.FileSystemContext fs = this.getPublicFileSystemContext();
            try {
                return new TruffleFile(fs, fs.fileSystem.parsePath(path));
            }
            catch (UnsupportedOperationException e) {
                throw e;
            }
            catch (Throwable t) {
                throw TruffleFile.wrapHostException(t, fs.fileSystem);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleFile getPublicTruffleFile(URI uri) {
            this.checkDisposed();
            TruffleFile.FileSystemContext fs = this.getPublicFileSystemContext();
            try {
                return new TruffleFile(fs, fs.fileSystem.parsePath(uri));
            }
            catch (UnsupportedOperationException e) {
                throw e;
            }
            catch (Throwable t) {
                throw TruffleFile.wrapHostException(t, fs.fileSystem);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleFile getInternalTruffleFile(String path) {
            this.checkDisposed();
            TruffleFile.FileSystemContext fs = this.getInternalFileSystemContext();
            try {
                return new TruffleFile(fs, fs.fileSystem.parsePath(path));
            }
            catch (UnsupportedOperationException e) {
                throw e;
            }
            catch (Throwable t) {
                throw TruffleFile.wrapHostException(t, fs.fileSystem);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleFile getInternalTruffleFile(URI uri) {
            this.checkDisposed();
            TruffleFile.FileSystemContext fs = this.getInternalFileSystemContext();
            try {
                return new TruffleFile(fs, fs.fileSystem.parsePath(uri));
            }
            catch (UnsupportedOperationException e) {
                throw e;
            }
            catch (Throwable t) {
                throw TruffleFile.wrapHostException(t, fs.fileSystem);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleFile getTruffleFileInternal(String path, Predicate<TruffleFile> filter) {
            return this.getTruffleFileInternalImpl(path, filter, TruffleFileFactory.PATH);
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleFile getTruffleFileInternal(URI uri, Predicate<TruffleFile> filter) {
            return this.getTruffleFileInternalImpl(uri, filter, TruffleFileFactory.URI);
        }

        private <P> TruffleFile getTruffleFileInternalImpl(P path, Predicate<TruffleFile> isStdLibFile, TruffleFileFactory<P> truffleFileFactory) {
            this.checkDisposed();
            TruffleFile.FileSystemContext publicFsContext = this.getPublicFileSystemContext();
            if (LanguageAccessor.engineAccess().hasNoAccess(publicFsContext.fileSystem)) {
                TruffleFile.FileSystemContext internalFsContext = this.getInternalFileSystemContext();
                TruffleFile internalFile = truffleFileFactory.apply(path, internalFsContext);
                if (LanguageAccessor.engineAccess().getRelativePathInLanguageHome(internalFile) != null && isStdLibFile.test(internalFile.getAbsoluteFile())) {
                    return internalFile;
                }
            }
            return truffleFileFactory.apply(path, publicFsContext);
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleFile getCurrentWorkingDirectory() {
            return this.getPublicTruffleFile("").getAbsoluteFile();
        }

        @CompilerDirectives.TruffleBoundary
        public void setCurrentWorkingDirectory(TruffleFile currentWorkingDirectory) {
            this.checkDisposed();
            Objects.requireNonNull(currentWorkingDirectory, "Current working directory must be non null.");
            if (!currentWorkingDirectory.isAbsolute()) {
                throw new IllegalArgumentException("Current working directory must be absolute.");
            }
            if (!currentWorkingDirectory.isDirectory(new LinkOption[0])) {
                throw new IllegalArgumentException("Current working directory must be directory.");
            }
            TruffleFile.FileSystemContext fileSystemContext = this.getPublicFileSystemContext();
            TruffleFile.FileSystemContext internalFileSystemContext = this.getInternalFileSystemContext();
            try {
                fileSystemContext.fileSystem.setCurrentWorkingDirectory(currentWorkingDirectory.getSPIPath());
                if (fileSystemContext.fileSystem != internalFileSystemContext.fileSystem) {
                    internalFileSystemContext.fileSystem.setCurrentWorkingDirectory(currentWorkingDirectory.getSPIPath());
                }
            }
            catch (IllegalArgumentException | SecurityException | UnsupportedOperationException e) {
                throw e;
            }
            catch (Throwable t) {
                throw TruffleFile.wrapHostException(t, fileSystemContext.fileSystem);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public String getFileNameSeparator() {
            this.checkDisposed();
            TruffleFile.FileSystemContext fs = this.getPublicFileSystemContext();
            try {
                return fs.fileSystem.getSeparator();
            }
            catch (Throwable t) {
                throw TruffleFile.wrapHostException(t, fs.fileSystem);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public String getPathSeparator() {
            this.checkDisposed();
            TruffleFile.FileSystemContext fs = this.getPublicFileSystemContext();
            try {
                return fs.fileSystem.getPathSeparator();
            }
            catch (Throwable t) {
                throw TruffleFile.wrapHostException(t, fs.fileSystem);
            }
        }

        public void registerService(Object service2) {
            if (this.languageServicesCollector == null) {
                throw new IllegalStateException("The registerService method can only be called during the execution of the Env.createContext method.");
            }
            this.languageServicesCollector.add(service2);
        }

        public boolean isCreateProcessAllowed() {
            try {
                return LanguageAccessor.engineAccess().isCreateProcessAllowed(this.polyglotLanguageContext);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleProcessBuilder newProcessBuilder(String ... command) {
            if (!this.isCreateProcessAllowed()) {
                throw new SecurityException("Process creation is not allowed, to enable it set Context.Builder.allowCreateProcess(true).");
            }
            TruffleFile.FileSystemContext fs = this.getPublicFileSystemContext();
            ArrayList<String> cmd = new ArrayList<String>(command.length);
            Collections.addAll(cmd, command);
            return LanguageAccessor.ioAccess().createProcessBuilder(this.polyglotLanguageContext, fs.fileSystem, cmd);
        }

        @CompilerDirectives.TruffleBoundary
        public Map<String, String> getEnvironment() {
            try {
                return LanguageAccessor.engineAccess().getProcessEnvironment(this.polyglotLanguageContext);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleFile createTempFile(TruffleFile dir, String prefix, String suffix, FileAttribute<?> ... attrs) throws IOException {
            TruffleFile.FileSystemContext fs = this.getPublicFileSystemContext();
            try {
                TruffleFile useDir = dir == null ? new TruffleFile(fs, fs.fileSystem.getTempDirectory()) : dir;
                return TruffleFile.createTempFile(useDir, prefix, suffix, false, attrs);
            }
            catch (IOException | IllegalArgumentException | SecurityException | UnsupportedOperationException e) {
                throw e;
            }
            catch (Throwable t) {
                throw TruffleFile.wrapHostException(t, fs.fileSystem);
            }
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleFile createTempDirectory(TruffleFile dir, String prefix, FileAttribute<?> ... attrs) throws IOException {
            TruffleFile.FileSystemContext fs = this.getPublicFileSystemContext();
            try {
                TruffleFile useDir = dir == null ? new TruffleFile(fs, fs.fileSystem.getTempDirectory()) : dir;
                return TruffleFile.createTempFile(useDir, prefix, null, true, attrs);
            }
            catch (IOException | IllegalArgumentException | SecurityException | UnsupportedOperationException e) {
                throw e;
            }
            catch (Throwable t) {
                throw TruffleFile.wrapHostException(t, fs.fileSystem);
            }
        }

        @Deprecated(since="22.1")
        @CompilerDirectives.TruffleBoundary
        public Object createHostAdapterClass(Class<?>[] types) {
            Objects.requireNonNull(types, "types");
            return this.createHostAdapterClassLegacyImpl(types, null);
        }

        @Deprecated(since="22.1")
        @CompilerDirectives.TruffleBoundary
        public Object createHostAdapterClassWithStaticOverrides(Class<?>[] types, Object classOverrides) {
            Objects.requireNonNull(types, "types");
            Objects.requireNonNull(classOverrides, "classOverrides");
            return this.createHostAdapterClassLegacyImpl(types, classOverrides);
        }

        @CompilerDirectives.TruffleBoundary
        public Object createHostAdapter(Object[] types) {
            Objects.requireNonNull(types, "types");
            return this.createHostAdapterClassImpl(types, null);
        }

        @CompilerDirectives.TruffleBoundary
        public Object createHostAdapterWithClassOverrides(Object[] types, Object classOverrides) {
            Objects.requireNonNull(types, "types");
            Objects.requireNonNull(classOverrides, "classOverrides");
            return this.createHostAdapterClassImpl(types, classOverrides);
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleLogger getLogger(String loggerName) {
            String languageId = this.spi.languageInfo.getId();
            TruffleLogger.LoggerCache loggerCache = (TruffleLogger.LoggerCache)LanguageAccessor.engineAccess().getContextLoggerCache(this.polyglotLanguageContext);
            return TruffleLogger.getLogger(languageId, loggerName, loggerCache);
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleLogger getLogger(Class<?> forClass) {
            Objects.requireNonNull(forClass, "Class must be non null.");
            return this.getLogger(forClass.getName());
        }

        public Future<Void> submitThreadLocal(Thread[] threads, ThreadLocalAction action2) {
            return this.submitThreadLocalInternal(threads, action2, true);
        }

        public void registerOnDispose(Closeable closeable) {
            LanguageAccessor.engineAccess().registerOnDispose(this.polyglotLanguageContext, closeable);
        }

        Future<Void> submitThreadLocalInternal(Thread[] threads, ThreadLocalAction action2, boolean needsEnter) {
            this.checkDisposed();
            try {
                return LanguageAccessor.ENGINE.submitThreadLocal(LanguageAccessor.ENGINE.getContext(this.polyglotLanguageContext), this.polyglotLanguageContext, threads, action2, needsEnter);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        private Object createHostAdapterClassLegacyImpl(Class<?>[] types, Object classOverrides) {
            this.checkDisposed();
            Object[] hostTypes = new Object[types.length];
            for (int i = 0; i < types.length; ++i) {
                Class<?> type = types[i];
                hostTypes[i] = this.asHostSymbol(type);
            }
            return this.createHostAdapterClassImpl(hostTypes, classOverrides);
        }

        private Object createHostAdapterClassImpl(Object[] types, Object classOverrides) {
            this.checkDisposed();
            try {
                if (types.length == 0) {
                    throw new IllegalArgumentException("Expected at least one type.");
                }
                return LanguageAccessor.engineAccess().createHostAdapterClass(this.polyglotLanguageContext, types, classOverrides);
            }
            catch (Throwable t) {
                throw Env.engineToLanguageException(t);
            }
        }

        @CompilerDirectives.TruffleBoundary
        <E extends TruffleLanguage> E getLanguage(Class<E> languageClass) {
            this.checkDisposed();
            if (languageClass != this.getSpi().getClass()) {
                throw new IllegalArgumentException("Invalid access to language " + languageClass + ".");
            }
            return (E)((TruffleLanguage)languageClass.cast(this.getSpi()));
        }

        void dispose() {
            Object c = this.getLanguageContext();
            if (c == UNSET_CONTEXT) {
                throw new IllegalStateException("Disposing while context has not been set yet.");
            }
            this.getSpi().disposeContext(c);
        }

        @CompilerDirectives.TruffleBoundary
        void postInit() {
            try {
                this.getSpi().initializeContext(this.context);
            }
            catch (RuntimeException ex) {
                throw ex;
            }
            catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            finally {
                this.initialized = true;
                Assumption old = this.initializedUnchangedAssumption;
                this.initializedUnchangedAssumption = Truffle.getRuntime().createAssumption("Language context initialized unchanged");
                old.invalidate();
            }
        }

        boolean isInitialized() {
            if (CompilerDirectives.isPartialEvaluationConstant(this)) {
                boolean localInitialized = this.initialized;
                if (this.initializedUnchangedAssumption.isValid()) {
                    return localInitialized;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.initialized;
            }
            return this.initialized;
        }

        boolean isVisible(Object value2) {
            Object c = this.getLanguageContext();
            if (c != UNSET_CONTEXT) {
                return this.getSpi().isVisible(c, value2);
            }
            return false;
        }

        Object getLanguageContext() {
            if (CompilerDirectives.isPartialEvaluationConstant(this)) {
                Object languageContext = this.context;
                if (this.contextUnchangedAssumption.isValid()) {
                    return languageContext;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.context;
            }
            return this.context;
        }

        @CompilerDirectives.TruffleBoundary
        static <T extends RuntimeException> RuntimeException engineToLanguageException(Throwable t) {
            return LanguageAccessor.engineAccess().engineToLanguageException(t);
        }

        private static abstract class TruffleFileFactory<P>
        implements BiFunction<P, TruffleFile.FileSystemContext, TruffleFile> {
            static final TruffleFileFactory<String> PATH = new TruffleFileFactory<String>(){

                @Override
                Path parsePath(String path, TruffleFile.FileSystemContext fileSystemContext) {
                    return fileSystemContext.fileSystem.parsePath(path);
                }
            };
            static final TruffleFileFactory<URI> URI = new TruffleFileFactory<URI>(){

                @Override
                public Path parsePath(URI uri, TruffleFile.FileSystemContext fileSystemContext) {
                    return fileSystemContext.fileSystem.parsePath(uri);
                }
            };

            private TruffleFileFactory() {
            }

            @Override
            public final TruffleFile apply(P p, TruffleFile.FileSystemContext fileSystemContext) {
                try {
                    return new TruffleFile(fileSystemContext, this.parsePath(p, fileSystemContext));
                }
                catch (UnsupportedOperationException e) {
                    throw e;
                }
                catch (Throwable t) {
                    throw TruffleFile.wrapHostException(t, fileSystemContext.fileSystem);
                }
            }

            abstract Path parsePath(P var1, TruffleFile.FileSystemContext var2);
        }
    }

    @FunctionalInterface
    protected static interface ContextThreadLocalFactory<C, T> {
        public T create(C var1, Thread var2);
    }

    @FunctionalInterface
    protected static interface ContextLocalFactory<C, T> {
        public T create(C var1);
    }

    public static final class InlineParsingRequest {
        private final Node node;
        private final MaterializedFrame frame;
        private final Source source;
        private boolean disposed;

        InlineParsingRequest(Source source, Node node, MaterializedFrame frame) {
            Objects.requireNonNull(source);
            this.node = node;
            this.frame = frame;
            this.source = source;
        }

        public Source getSource() {
            if (this.disposed) {
                throw new IllegalStateException();
            }
            return this.source;
        }

        public Node getLocation() {
            if (this.disposed) {
                throw new IllegalStateException();
            }
            return this.node;
        }

        public MaterializedFrame getFrame() {
            if (this.disposed) {
                throw new IllegalStateException();
            }
            return this.frame;
        }

        void dispose() {
            this.disposed = true;
        }

        ExecutableNode parse(TruffleLanguage<?> truffleLanguage) throws Exception {
            return truffleLanguage.parse(this);
        }
    }

    public static final class ParsingRequest {
        private final Source source;
        private final String[] argumentNames;
        private boolean disposed;

        ParsingRequest(Source source, String ... argumentNames) {
            Objects.requireNonNull(source);
            this.source = source;
            this.argumentNames = argumentNames;
        }

        public Source getSource() {
            if (this.disposed) {
                throw new IllegalStateException();
            }
            return this.source;
        }

        public List<String> getArgumentNames() {
            if (this.disposed) {
                throw new IllegalStateException();
            }
            return this.argumentNames == null ? Collections.emptyList() : ReadOnlyArrayList.asList(this.argumentNames, 0, this.argumentNames.length);
        }

        void dispose() {
            this.disposed = true;
        }

        CallTarget parse(TruffleLanguage<?> truffleLanguage) throws Exception {
            return truffleLanguage.parse(this);
        }
    }

    public static interface Provider {
        public String getLanguageClassName();

        public TruffleLanguage<?> create();

        public List<TruffleFile.FileTypeDetector> createFileTypeDetectors();

        public Collection<String> getServicesClassNames();
    }

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.TYPE})
    public static @interface Registration {
        public String id() default "";

        public String name() default "";

        public String implementationName() default "";

        public String version() default "inherit";

        public String defaultMimeType() default "";

        public String[] characterMimeTypes() default {};

        public String[] byteMimeTypes() default {};

        public boolean interactive() default true;

        public boolean internal() default false;

        public String[] dependentLanguages() default {};

        public ContextPolicy contextPolicy() default ContextPolicy.EXCLUSIVE;

        public Class<?>[] services() default {};

        public Class<? extends TruffleFile.FileTypeDetector>[] fileTypeDetectors() default {};

        public boolean needsAllEncodings() default false;

        public String website() default "";
    }
}

