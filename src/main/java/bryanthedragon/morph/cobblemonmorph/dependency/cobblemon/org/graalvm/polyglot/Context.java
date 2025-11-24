
package org.graalvm.polyglot;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.time.Duration;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;
import java.util.logging.Handler;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.EnvironmentAccess;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotAccess;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.ResourceLimits;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.io.FileSystem;
import org.graalvm.polyglot.io.MessageTransport;
import org.graalvm.polyglot.io.ProcessHandler;

public final class Context
implements AutoCloseable {
    final AbstractPolyglotImpl.AbstractContextDispatch dispatch;
    final Object receiver;
    final Context currentAPI;
    final Engine engine;
    private static final Context EMPTY = new Context();
    static final Predicate<String> UNSET_HOST_LOOKUP = new Predicate<String>(){

        @Override
        public boolean test(String t) {
            return false;
        }
    };
    static final Predicate<String> NO_HOST_CLASSES = new Predicate<String>(){

        @Override
        public boolean test(String t) {
            return false;
        }
    };
    static final Predicate<String> ALL_HOST_CLASSES = new Predicate<String>(){

        @Override
        public boolean test(String t) {
            return true;
        }
    };

    <T> Context(AbstractPolyglotImpl.AbstractContextDispatch dispatch, T receiver, Engine engine) {
        this.dispatch = dispatch;
        this.receiver = receiver;
        this.engine = engine;
        this.currentAPI = new Context(this);
        dispatch.setAPI(receiver, this);
    }

    private Context() {
        this.dispatch = null;
        this.receiver = null;
        this.engine = null;
        this.currentAPI = null;
    }

    private <T> Context(Context creatorAPI) {
        this.dispatch = creatorAPI.dispatch;
        this.receiver = creatorAPI.receiver;
        this.engine = creatorAPI.engine.currentAPI;
        this.currentAPI = null;
    }

    public Engine getEngine() {
        return this.engine;
    }

    public Value eval(Source source) {
        return this.dispatch.eval(this.receiver, source.getLanguage(), source);
    }

    public Value eval(String languageId, CharSequence source) {
        return this.eval(Source.create(languageId, source));
    }

    public Value parse(Source source) throws PolyglotException {
        return this.dispatch.parse(this.receiver, source.getLanguage(), source);
    }

    public Value parse(String languageId, CharSequence source) {
        return this.parse(Source.create(languageId, source));
    }

    public Value getPolyglotBindings() {
        return this.dispatch.getPolyglotBindings(this.receiver);
    }

    public Value getBindings(String languageId) {
        return this.dispatch.getBindings(this.receiver, languageId);
    }

    public boolean initialize(String languageId) {
        return this.dispatch.initializeLanguage(this.receiver, languageId);
    }

    public void resetLimits() {
        this.dispatch.resetLimits(this.receiver);
    }

    public Value asValue(Object hostValue) {
        return this.dispatch.asValue(this.receiver, hostValue);
    }

    public void enter() {
        this.checkCreatorAccess("entered");
        this.dispatch.explicitEnter(this.receiver);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Context) {
            Context other = (Context)obj;
            return this.receiver.equals(other.receiver);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.receiver);
    }

    public void leave() {
        this.checkCreatorAccess("left");
        this.dispatch.explicitLeave(this.receiver);
    }

    private void checkCreatorAccess(String operation) {
        if (this.currentAPI == null) {
            throw new IllegalStateException(String.format("Context instances that were received using Context.get() cannot be %s.", operation));
        }
    }

    public void close(boolean cancelIfExecuting) {
        this.checkCreatorAccess("closed");
        this.dispatch.close(this.receiver, cancelIfExecuting);
    }

    @Override
    public void close() {
        this.close(false);
    }

    public void interrupt(Duration timeout) throws TimeoutException {
        this.checkCreatorAccess("interrupted");
        if (!this.dispatch.interrupt(this.receiver, timeout)) {
            throw new TimeoutException("Interrupt timed out.");
        }
    }

    public void safepoint() {
        this.dispatch.safepoint(this.receiver);
    }

    public static Context getCurrent() {
        Context context = Engine.getImpl().getCurrentContext();
        if (context.currentAPI == null) {
            return context;
        }
        return context.currentAPI;
    }

    public static Context create(String ... permittedLanguages) {
        return Context.newBuilder(permittedLanguages).build();
    }

    public static Builder newBuilder(String ... permittedLanguages) {
        Context context = EMPTY;
        Objects.requireNonNull(context);
        return context.new Builder(permittedLanguages);
    }

    public final class Builder {
        private Engine sharedEngine;
        private String[] permittedLanguages;
        private OutputStream out;
        private OutputStream err;
        private InputStream in;
        private Map<String, String> options;
        private Map<String, String[]> arguments;
        private Predicate<String> hostClassFilter = UNSET_HOST_LOOKUP;
        private Boolean allowNativeAccess;
        private Boolean allowCreateThread;
        private boolean allowAllAccess;
        private Boolean allowIO;
        private Boolean allowHostClassLoading;
        private Boolean allowExperimentalOptions;
        private Boolean allowHostAccess;
        private boolean allowValueSharing = true;
        private Boolean allowInnerContextOptions;
        private PolyglotAccess polyglotAccess;
        private HostAccess hostAccess;
        private FileSystem customFileSystem;
        private MessageTransport messageTransport;
        private Object customLogHandler;
        private Boolean allowCreateProcess;
        private ProcessHandler processHandler;
        private EnvironmentAccess environmentAccess;
        private ResourceLimits resourceLimits;
        private Map<String, String> environment;
        private ZoneId zone;
        private Path currentWorkingDirectory;
        private ClassLoader hostClassLoader;
        private boolean useSystemExit;

        Builder(String ... permittedLanguages) {
            Objects.requireNonNull(permittedLanguages);
            for (String language : permittedLanguages) {
                Objects.requireNonNull(language);
            }
            this.permittedLanguages = permittedLanguages;
        }

        public Builder engine(Engine engine) {
            Objects.requireNonNull(engine);
            this.sharedEngine = engine;
            return this;
        }

        public Builder out(OutputStream out) {
            Objects.requireNonNull(out);
            this.out = out;
            return this;
        }

        public Builder err(OutputStream err) {
            Objects.requireNonNull(err);
            this.err = err;
            return this;
        }

        public Builder in(InputStream in) {
            Objects.requireNonNull(in);
            this.in = in;
            return this;
        }

        @Deprecated(since="19.0")
        public Builder allowHostAccess(boolean enabled) {
            this.allowHostAccess = enabled;
            return this;
        }

        public Builder allowHostAccess(HostAccess config) {
            this.hostAccess = config;
            return this;
        }

        public Builder allowNativeAccess(boolean enabled) {
            this.allowNativeAccess = enabled;
            return this;
        }

        public Builder allowCreateThread(boolean enabled) {
            this.allowCreateThread = enabled;
            return this;
        }

        public Builder allowAllAccess(boolean enabled) {
            this.allowAllAccess = enabled;
            return this;
        }

        public Builder allowHostClassLoading(boolean enabled) {
            this.allowHostClassLoading = enabled;
            return this;
        }

        public Builder allowHostClassLookup(Predicate<String> classFilter) {
            this.hostClassFilter = classFilter;
            return this;
        }

        public Builder allowExperimentalOptions(boolean enabled) {
            this.allowExperimentalOptions = enabled;
            return this;
        }

        public Builder allowPolyglotAccess(PolyglotAccess accessPolicy) {
            Objects.requireNonNull(accessPolicy);
            this.polyglotAccess = accessPolicy;
            return this;
        }

        public Builder allowValueSharing(boolean enabled) {
            this.allowValueSharing = enabled;
            return this;
        }

        public Builder allowInnerContextOptions(boolean enabled) {
            this.allowInnerContextOptions = enabled;
            return this;
        }

        @Deprecated(since="19.0")
        public Builder hostClassFilter(Predicate<String> classFilter) {
            Objects.requireNonNull(classFilter);
            this.hostClassFilter = classFilter;
            return this;
        }

        public Builder option(String key, String value2) {
            Objects.requireNonNull(key);
            Objects.requireNonNull(value2);
            if (this.options == null) {
                this.options = new HashMap<String, String>();
            }
            this.options.put(key, value2);
            return this;
        }

        public Builder options(Map<String, String> options) {
            for (Map.Entry<String, String> entry : options.entrySet()) {
                this.option(entry.getKey(), entry.getValue());
            }
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

        public Builder allowIO(boolean enabled) {
            this.allowIO = enabled;
            return this;
        }

        public Builder fileSystem(FileSystem fileSystem) {
            Objects.requireNonNull(fileSystem, "FileSystem must be non null.");
            this.customFileSystem = fileSystem;
            return this;
        }

        public Builder serverTransport(MessageTransport serverTransport) {
            Objects.requireNonNull(serverTransport, "MessageTransport must be non null.");
            this.messageTransport = serverTransport;
            return this;
        }

        public Builder logHandler(Handler logHandler) {
            Objects.requireNonNull(logHandler, "Handler must be non null.");
            this.customLogHandler = logHandler;
            return this;
        }

        public Builder timeZone(ZoneId zone) {
            this.zone = zone;
            return this;
        }

        public Builder logHandler(OutputStream logOut) {
            Objects.requireNonNull(logOut, "LogOut must be non null.");
            this.customLogHandler = logOut;
            return this;
        }

        public Builder allowCreateProcess(boolean enabled) {
            this.allowCreateProcess = enabled;
            return this;
        }

        public Builder processHandler(ProcessHandler handler) {
            Objects.requireNonNull(handler, "Handler must be non null.");
            this.processHandler = handler;
            return this;
        }

        public Builder resourceLimits(ResourceLimits limits) {
            this.resourceLimits = limits;
            return this;
        }

        public Builder allowEnvironmentAccess(EnvironmentAccess accessPolicy) {
            Objects.requireNonNull(accessPolicy, "AccessPolicy must be non null.");
            this.environmentAccess = accessPolicy;
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

        public Builder currentWorkingDirectory(Path workingDirectory) {
            Objects.requireNonNull(workingDirectory, "WorkingDirectory must be non null.");
            if (!workingDirectory.isAbsolute()) {
                throw new IllegalArgumentException("WorkingDirectory must be an absolute path.");
            }
            this.currentWorkingDirectory = workingDirectory;
            return this;
        }

        public Builder hostClassLoader(ClassLoader classLoader) {
            Objects.requireNonNull(classLoader, "ClassLoader must be non null.");
            this.hostClassLoader = classLoader;
            return this;
        }

        public Builder useSystemExit(boolean enabled) {
            this.useSystemExit = enabled;
            return this;
        }

        public Context build() {
            InputStream contextIn;
            OutputStream contextErr;
            OutputStream contextOut;
            Map<String, String> contextOptions;
            boolean hostClassLookupEnabled;
            PolyglotAccess polyglotAccess;
            boolean nativeAccess = this.orAllAccess(this.allowNativeAccess);
            boolean createThread = this.orAllAccess(this.allowCreateThread);
            boolean io = this.orAllAccess(this.allowIO);
            boolean hostClassLoading = this.orAllAccess(this.allowHostClassLoading);
            boolean experimentalOptions = this.orAllAccess(this.allowExperimentalOptions);
            boolean innerContextOptions = this.orAllAccess(this.allowInnerContextOptions);
            if (this.allowHostAccess != null && this.hostAccess != null) {
                throw new IllegalArgumentException("The method allowHostAccess with boolean and with HostAccess are mutually exclusive.");
            }
            Predicate<String> localHostLookupFilter = this.hostClassFilter;
            HostAccess hostAccess = this.hostAccess;
            if (this.allowHostAccess != null && this.allowHostAccess.booleanValue()) {
                if (localHostLookupFilter == UNSET_HOST_LOOKUP) {
                    localHostLookupFilter = ALL_HOST_CLASSES;
                }
                hostAccess = HostAccess.ALL;
            }
            if (hostAccess == null) {
                HostAccess hostAccess2 = hostAccess = this.allowAllAccess ? HostAccess.ALL : HostAccess.EXPLICIT;
            }
            if ((polyglotAccess = this.polyglotAccess) == null) {
                PolyglotAccess polyglotAccess2 = polyglotAccess = this.allowAllAccess ? PolyglotAccess.ALL : PolyglotAccess.NONE;
            }
            if (localHostLookupFilter == UNSET_HOST_LOOKUP) {
                localHostLookupFilter = this.allowAllAccess ? ALL_HOST_CLASSES : null;
            }
            boolean bl = hostClassLookupEnabled = localHostLookupFilter != null;
            if (localHostLookupFilter == null) {
                localHostLookupFilter = NO_HOST_CLASSES;
            }
            boolean createProcess = this.orAllAccess(this.allowCreateProcess);
            if (this.environmentAccess == null) {
                this.environmentAccess = this.allowAllAccess ? EnvironmentAccess.INHERIT : EnvironmentAccess.NONE;
            }
            Object limits = this.resourceLimits != null ? this.resourceLimits.receiver : null;
            if (!io && this.customFileSystem != null) {
                throw new IllegalStateException("Cannot install custom FileSystem when IO is disabled.");
            }
            String localCurrentWorkingDirectory = this.currentWorkingDirectory == null ? null : this.currentWorkingDirectory.toString();
            Engine engine = this.sharedEngine;
            if (engine == null) {
                Engine.Builder engineBuilder = Engine.newBuilder(this.permittedLanguages).options(this.options == null ? Collections.emptyMap() : this.options);
                contextOptions = Collections.emptyMap();
                contextOut = null;
                contextErr = null;
                contextIn = null;
                if (this.out != null) {
                    engineBuilder.out(this.out);
                }
                if (this.err != null) {
                    engineBuilder.err(this.err);
                }
                if (this.in != null) {
                    engineBuilder.in(this.in);
                }
                if (this.messageTransport != null) {
                    engineBuilder.serverTransport(this.messageTransport);
                }
                if (this.customLogHandler instanceof Handler) {
                    engineBuilder.logHandler((Handler)this.customLogHandler);
                } else if (this.customLogHandler instanceof OutputStream) {
                    engineBuilder.logHandler((OutputStream)this.customLogHandler);
                }
                engineBuilder.allowExperimentalOptions(experimentalOptions);
                engineBuilder.setBoundEngine(true);
                engine = engineBuilder.build();
            } else {
                if (this.messageTransport != null) {
                    throw new IllegalStateException("Cannot use MessageTransport in a context that shares an Engine.");
                }
                contextOptions = this.options == null ? Collections.emptyMap() : this.options;
                contextOut = this.out;
                contextErr = this.err;
                contextIn = this.in;
            }
            Context ctx = engine.dispatch.createContext(engine.receiver, contextOut, contextErr, contextIn, hostClassLookupEnabled, hostAccess, polyglotAccess, nativeAccess, createThread, io, hostClassLoading, innerContextOptions, experimentalOptions, localHostLookupFilter, contextOptions, this.arguments == null ? Collections.emptyMap() : this.arguments, this.permittedLanguages, this.customFileSystem, this.customLogHandler, createProcess, this.processHandler, this.environmentAccess, this.environment, this.zone, limits, localCurrentWorkingDirectory, this.hostClassLoader, this.allowValueSharing, this.useSystemExit);
            return ctx;
        }

        private boolean orAllAccess(Boolean optionalBoolean) {
            return optionalBoolean != null ? optionalBoolean : this.allowAllAccess;
        }
    }
}

