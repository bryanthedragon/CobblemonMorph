
package com.oracle.truffle.api;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.LanguageAccessor;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class TruffleContext
implements AutoCloseable {
    static final TruffleContext EMPTY = new TruffleContext();
    private static final ThreadLocal<List<Object>> CONTEXT_ASSERT_STACK;
    final Object polyglotContext;
    final boolean creator;

    TruffleContext(Object polyglotContext, boolean creator) {
        this.polyglotContext = polyglotContext;
        this.creator = creator;
    }

    private TruffleContext() {
        this.polyglotContext = null;
        this.creator = false;
    }

    @CompilerDirectives.TruffleBoundary
    public boolean equals(Object obj) {
        if (!(obj instanceof TruffleContext)) {
            return false;
        }
        TruffleContext c = (TruffleContext)obj;
        return this.polyglotContext.equals(c.polyglotContext);
    }

    public int hashCode() {
        return this.polyglotContext.hashCode();
    }

    @CompilerDirectives.TruffleBoundary
    public TruffleContext getParent() {
        try {
            return LanguageAccessor.engineAccess().getParentContext(this.polyglotContext);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    public Object enter(Node node) {
        try {
            CompilerAsserts.partialEvaluationConstant(node);
            Object prev = LanguageAccessor.engineAccess().enterInternalContext(node, this.polyglotContext);
            if (CONTEXT_ASSERT_STACK != null) {
                TruffleContext.verifyEnter(prev);
            }
            return prev;
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    public boolean initializeInternal(Node node, String languageId) {
        Objects.requireNonNull(languageId);
        CompilerAsserts.partialEvaluationConstant(node);
        try {
            return LanguageAccessor.engineAccess().initializeInnerContext(node, this.polyglotContext, languageId, true);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    public boolean initializePublic(Node node, String languageId) {
        Objects.requireNonNull(languageId);
        CompilerAsserts.partialEvaluationConstant(node);
        try {
            return LanguageAccessor.engineAccess().initializeInnerContext(node, this.polyglotContext, languageId, false);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    public Object evalInternal(Node node, Source source) {
        CompilerAsserts.partialEvaluationConstant(node);
        try {
            return LanguageAccessor.engineAccess().evalInternalContext(node, this.polyglotContext, source, true);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    public Object evalPublic(Node node, Source source) {
        CompilerAsserts.partialEvaluationConstant(node);
        try {
            return LanguageAccessor.engineAccess().evalInternalContext(node, this.polyglotContext, source, false);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    public boolean isEntered() {
        try {
            return LanguageAccessor.engineAccess().isContextEntered(this.polyglotContext);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    public boolean isActive() {
        try {
            return LanguageAccessor.engineAccess().isContextActive(this.polyglotContext);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public boolean isClosed() {
        try {
            return LanguageAccessor.engineAccess().isContextClosed(this.polyglotContext);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public boolean isCancelling() {
        try {
            return LanguageAccessor.engineAccess().isContextCancelling(this.polyglotContext);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public boolean isExiting() {
        try {
            return LanguageAccessor.engineAccess().isContextExiting(this.polyglotContext);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public Future<Void> pause() {
        try {
            return LanguageAccessor.engineAccess().pause(this.polyglotContext);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void resume(Future<Void> pauseFuture) {
        try {
            LanguageAccessor.engineAccess().resume(this.polyglotContext, pauseFuture);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    public void leave(Node node, Object prev) {
        try {
            if (CONTEXT_ASSERT_STACK != null) {
                TruffleContext.verifyLeave(prev);
            }
            LanguageAccessor.engineAccess().leaveInternalContext(node, this.polyglotContext, prev);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public <T> T leaveAndEnter(Node node, Supplier<T> runWhileOutsideContext) {
        CompilerAsserts.partialEvaluationConstant(node);
        try {
            LanguageAccessor.engineAccess().leaveInternalContext(node, this.polyglotContext, null);
            try {
                T t = TruffleContext.callSupplier(runWhileOutsideContext);
                return t;
            }
            finally {
                LanguageAccessor.engineAccess().enterInternalContext(node, this.polyglotContext);
            }
        }
        catch (Throwable t2) {
            throw TruffleLanguage.Env.engineToLanguageException(t2);
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static <T> T callSupplier(Supplier<T> supplier) {
        return supplier.get();
    }

    @CompilerDirectives.TruffleBoundary
    private static void verifyEnter(Object prev) {
        assert (CONTEXT_ASSERT_STACK != null);
        CONTEXT_ASSERT_STACK.get().add(prev);
    }

    @CompilerDirectives.TruffleBoundary
    private static void verifyLeave(Object prev) {
        assert (CONTEXT_ASSERT_STACK != null);
        List<Object> list = CONTEXT_ASSERT_STACK.get();
        assert (!list.isEmpty()) : "Assert stack is empty.";
        Object expectedPrev = list.get(list.size() - 1);
        assert (prev == expectedPrev) : "Invalid prev argument provided in TruffleContext.leave(Object).";
        list.remove(list.size() - 1);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public void close() {
        if (!this.creator) {
            throw new UnsupportedOperationException("This context instance has no permission to close. Only the original creator of the truffle context or instruments can close.");
        }
        try {
            LanguageAccessor.engineAccess().closeContext(this.polyglotContext, false, null, false, null);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void closeCancelled(Node closeLocation, String message) {
        if (!this.creator) {
            throw new UnsupportedOperationException("This context instance has no permission to close. Only the original creator of the truffle context or instruments can close.");
        }
        try {
            LanguageAccessor.engineAccess().closeContext(this.polyglotContext, true, closeLocation, false, message);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void closeExited(Node exitLocation, int exitCode) {
        if (!this.isEntered()) {
            throw new IllegalStateException("Exit cannot be initiated for this context because it is not currently entered.");
        }
        try {
            LanguageAccessor.engineAccess().exitContext(this.polyglotContext, exitLocation, exitCode);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public void closeResourceExhausted(Node location, String message) {
        if (!this.creator) {
            throw new UnsupportedOperationException("This context instance has no permission to cancel. Only the original creator of the truffle context or instruments can close.");
        }
        try {
            LanguageAccessor.engineAccess().closeContext(this.polyglotContext, true, location, true, message);
        }
        catch (Throwable t) {
            throw TruffleLanguage.Env.engineToLanguageException(t);
        }
    }

    static {
        boolean assertions = false;
        if (!$assertionsDisabled) {
            assertions = true;
            if (!true) {
                throw new AssertionError();
            }
        }
        CONTEXT_ASSERT_STACK = assertions ? new ThreadLocal<List<Object>>(){

            @Override
            protected List<Object> initialValue() {
                return new ArrayList<Object>();
            }
        } : null;
    }

    public final class Builder {
        private final TruffleLanguage.Env sourceEnvironment;
        private Map<String, Object> config;
        private Map<String, String[]> arguments;
        private boolean initializeCreatorContext;
        private Runnable onCancelled;
        private Consumer<Integer> onExited;
        private Runnable onClosed;
        private Boolean sharingEnabled;
        private Map<String, String> options;
        private Map<String, String> environment;
        private String[] permittedLanguages;
        private OutputStream out;
        private OutputStream err;
        private InputStream in;
        private boolean inheritAccess;
        private Boolean allowCreateThread;
        private Boolean allowNativeAccess;
        private Boolean allowIO;
        private Boolean allowHostLookup;
        private Boolean allowHostClassLoading;
        private Boolean allowCreateProcess;
        private Boolean allowInnerContextOptions;
        private Boolean allowPolyglotAccess;
        private Boolean allowEnvironmentAccess;
        private ZoneId timeZone;

        Builder(TruffleLanguage.Env env) {
            this.sourceEnvironment = env;
        }

        Builder permittedLanguages(String ... permittedLanguages) {
            this.permittedLanguages = permittedLanguages;
            return this;
        }

        @CompilerDirectives.TruffleBoundary
        public Builder config(String key, Object value2) {
            if (this.config == null) {
                this.config = new HashMap<String, Object>();
            }
            this.config.put(key, value2);
            return this;
        }

        public Builder initializeCreatorContext(boolean enabled) {
            this.initializeCreatorContext = enabled;
            return this;
        }

        public Builder out(OutputStream out) {
            this.out = out;
            return this;
        }

        public Builder err(OutputStream err) {
            this.err = err;
            return this;
        }

        public Builder in(InputStream in) {
            this.in = in;
            return this;
        }

        public Builder forceSharing(Boolean enabled) {
            this.sharingEnabled = enabled;
            return this;
        }

        @CompilerDirectives.TruffleBoundary
        public Builder option(String key, String value2) {
            Objects.requireNonNull(key);
            Objects.requireNonNull(value2);
            if (this.options == null) {
                this.options = new HashMap<String, String>();
            }
            this.options.put(key, value2);
            return this;
        }

        public Builder inheritAllAccess(boolean b) {
            this.inheritAccess = b;
            return this;
        }

        public Builder allowCreateThread(boolean b) {
            this.allowCreateThread = b;
            return this;
        }

        public Builder allowNativeAccess(boolean b) {
            this.allowNativeAccess = b;
            return this;
        }

        public Builder allowIO(boolean b) {
            this.allowIO = b;
            return this;
        }

        public Builder allowHostClassLoading(boolean b) {
            this.allowHostClassLoading = b;
            return this;
        }

        public Builder allowHostClassLookup(boolean b) {
            this.allowHostLookup = b;
            return this;
        }

        public Builder allowCreateProcess(boolean b) {
            this.allowCreateProcess = b;
            return this;
        }

        public Builder allowInnerContextOptions(boolean b) {
            this.allowInnerContextOptions = b;
            return this;
        }

        public Builder allowPolyglotAccess(boolean b) {
            this.allowPolyglotAccess = b;
            return this;
        }

        public Builder allowInheritEnvironmentAccess(boolean b) {
            this.allowEnvironmentAccess = b;
            return this;
        }

        public Builder environment(String name, String value2) {
            Objects.requireNonNull(name, "Name must be non null.");
            Objects.requireNonNull(value2, "Value must be non null.");
            if (this.environment == null) {
                this.environment = new HashMap<String, String>();
            }
            this.environment.put(name, value2);
            return this;
        }

        public Builder environment(Map<String, String> env) {
            Objects.requireNonNull(env, "Env must be non null.");
            for (Map.Entry<String, String> e : env.entrySet()) {
                this.environment(e.getKey(), e.getValue());
            }
            return this;
        }

        public Builder timeZone(ZoneId zone) {
            this.timeZone = zone;
            return this;
        }

        public Builder arguments(String language, String[] args) {
            Objects.requireNonNull(language);
            Objects.requireNonNull(args);
            String[] newArgs = args;
            if (args.length > 0) {
                newArgs = new String[args.length];
                for (int i = 0; i < args.length; ++i) {
                    newArgs[i] = Objects.requireNonNull(args[i]);
                }
            }
            if (this.arguments == null) {
                this.arguments = new HashMap<String, String[]>();
            }
            this.arguments.put(language, newArgs);
            return this;
        }

        @CompilerDirectives.TruffleBoundary
        public Builder options(Map<String, String> options) {
            for (Map.Entry<String, String> entry : options.entrySet()) {
                this.option(entry.getKey(), entry.getValue());
            }
            return this;
        }

        public Builder onCancelled(Runnable r) {
            this.onCancelled = r;
            return this;
        }

        public Builder onExited(Consumer<Integer> r) {
            this.onExited = r;
            return this;
        }

        public Builder onClosed(Runnable r) {
            this.onClosed = r;
            return this;
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleContext build() {
            try {
                return LanguageAccessor.engineAccess().createInternalContext(this.sourceEnvironment.getPolyglotLanguageContext(), this.out, this.err, this.in, this.timeZone, this.permittedLanguages, this.config, this.options, this.arguments, this.sharingEnabled, this.initializeCreatorContext, this.onCancelled, this.onExited, this.onClosed, this.inheritAccess, this.allowCreateThread, this.allowNativeAccess, this.allowIO, this.allowHostLookup, this.allowHostClassLoading, this.allowCreateProcess, this.allowPolyglotAccess, this.allowEnvironmentAccess, this.environment, this.allowInnerContextOptions);
            }
            catch (Throwable t) {
                throw TruffleLanguage.Env.engineToLanguageException(t);
            }
        }
    }
}

