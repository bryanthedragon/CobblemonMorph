package org.graalvm.polyglot;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Executable;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Handler;
import org.graalvm.collections.UnmodifiableEconomicMap;
import org.graalvm.collections.UnmodifiableEconomicSet;
import org.graalvm.home.HomeFinder;
import org.graalvm.nativeimage.ImageInfo;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.io.FileSystem;
import org.graalvm.polyglot.io.MessageTransport;
import org.graalvm.polyglot.io.ProcessHandler;

public final class Engine implements AutoCloseable {
   private static volatile Throwable initializationException;
   private static volatile boolean shutdownHookInitialized;
   private static final Map<Engine, Void> ENGINES = Collections.synchronizedMap(new WeakHashMap<>());
   final AbstractPolyglotImpl.AbstractEngineDispatch dispatch;
   final Object receiver;
   final Engine currentAPI;
   private static final Engine EMPTY = new Engine(null, null);

   <T> Engine(AbstractPolyglotImpl.AbstractEngineDispatch dispatch, T receiver) {
      this.dispatch = dispatch;
      this.receiver = receiver;
      this.currentAPI = new Engine(this);
      if (dispatch != null) {
         dispatch.setAPI(receiver, this);
      }
   }

   private <T> Engine(Engine engine) {
      this.dispatch = engine.dispatch;
      this.receiver = engine.receiver;
      this.currentAPI = null;
   }

   public Map<String, Language> getLanguages() {
      return this.dispatch.getLanguages(this.receiver);
   }

   public Map<String, Instrument> getInstruments() {
      return this.dispatch.getInstruments(this.receiver);
   }

   public OptionDescriptors getOptions() {
      return this.dispatch.getOptions(this.receiver);
   }

   public String getVersion() {
      return this.dispatch.getVersion(this.receiver);
   }

   public void close(boolean cancelIfExecuting) {
      if (this.currentAPI == null) {
         throw new IllegalStateException("Engine instances that were indirectly received using Context.getCurrent() cannot be closed.");
      } else {
         this.dispatch.close(this.receiver, this, cancelIfExecuting);
      }
   }

   @Override
   public void close() {
      this.close(false);
   }

   public String getImplementationName() {
      return this.dispatch.getImplementationName(this.receiver);
   }

   public static Engine create() {
      return newBuilder().build();
   }

   public static Engine create(String... permittedLanguages) {
      return newBuilder(permittedLanguages).build();
   }

   public static Engine.Builder newBuilder() {
      return EMPTY.new Builder(new String[0]);
   }

   public static Engine.Builder newBuilder(String... permittedLanguages) {
      Objects.requireNonNull(permittedLanguages);
      return EMPTY.new Builder(permittedLanguages);
   }

   public static Path findHome() {
      return HomeFinder.getInstance().getHomeFolder();
   }

   public Set<Source> getCachedSources() {
      return this.dispatch.getCachedSources(this.receiver);
   }

   static AbstractPolyglotImpl getImpl() {
      try {
         return Engine.ImplHolder.IMPL;
      } catch (NoClassDefFoundError var2) {
         Throwable cause = initializationException;
         if (cause != null && var2.getCause() == null) {
            var2.initCause(cause);
         }

         throw var2;
      } catch (Throwable var3) {
         initializationException = var3;
         throw var3;
      }
   }

   static Class<?> loadLanguageClass(String className) {
      return getImpl().loadLanguageClass(className);
   }

   static Collection<Engine> findActiveEngines() {
      synchronized (ENGINES) {
         return new ArrayList<>(ENGINES.keySet());
      }
   }

   private static AbstractPolyglotImpl initEngineImpl() {
      return AccessController.doPrivileged(new PrivilegedAction<AbstractPolyglotImpl>() {
         public AbstractPolyglotImpl run() {
            AbstractPolyglotImpl polyglot = null;
            if (Boolean.getBoolean("graalvm.ForcePolyglotInvalid")) {
               polyglot = this.loadAndValidateProviders(Engine.createInvalidPolyglotImpl());
            } else {
               polyglot = this.loadAndValidateProviders(this.searchServiceLoader());
            }

            if (polyglot == null) {
               polyglot = this.loadAndValidateProviders(Engine.createInvalidPolyglotImpl());
            }

            return polyglot;
         }

         private Iterator<? extends AbstractPolyglotImpl> searchServiceLoader() throws InternalError {
            Class<?> lookupClass = AbstractPolyglotImpl.class;
            ModuleLayer moduleLayer = lookupClass.getModule().getLayer();
            Iterable<? extends AbstractPolyglotImpl> services;
            if (moduleLayer != null) {
               services = ServiceLoader.load(moduleLayer, AbstractPolyglotImpl.class);
            } else {
               services = ServiceLoader.load(AbstractPolyglotImpl.class, lookupClass.getClassLoader());
            }

            Iterator<? extends AbstractPolyglotImpl> iterator = services.iterator();
            if (!iterator.hasNext()) {
               Iterable<? extends AbstractPolyglotImpl> var5 = ServiceLoader.load(AbstractPolyglotImpl.class);
               iterator = var5.iterator();
            }

            return iterator;
         }

         private AbstractPolyglotImpl loadAndValidateProviders(Iterator<? extends AbstractPolyglotImpl> providers) throws AssertionError {
            List<AbstractPolyglotImpl> impls = new ArrayList<>();

            while (providers.hasNext()) {
               AbstractPolyglotImpl found = providers.next();

               for (AbstractPolyglotImpl impl : impls) {
                  if (impl.getClass().getName().equals(found.getClass().getName())) {
                     throw new AssertionError("Same polyglot impl found twice on the classpath.");
                  }
               }

               impls.add(found);
            }

            Collections.sort(impls, Comparator.comparing(AbstractPolyglotImpl::getPriority));
            AbstractPolyglotImpl prev = null;

            for (AbstractPolyglotImpl implx : impls) {
               implx.setNext(prev);
               implx.setConstructors(Engine.APIAccessImpl.INSTANCE);
               prev = implx;
            }

            return prev;
         }
      });
   }

   static Iterator<? extends AbstractPolyglotImpl> createInvalidPolyglotImpl() {
      return Arrays.asList(new Engine.PolyglotInvalid()).iterator();
   }

   static class APIAccessImpl extends AbstractPolyglotImpl.APIAccess {
      private static final Engine.APIAccessImpl INSTANCE = new Engine.APIAccessImpl();

      @Override
      public Context newContext(AbstractPolyglotImpl.AbstractContextDispatch dispatch, Object receiver, Engine engine) {
         return new Context(dispatch, receiver, engine);
      }

      @Override
      public Engine newEngine(AbstractPolyglotImpl.AbstractEngineDispatch dispatch, Object receiver, boolean registerInActiveEngines) {
         Engine engine = new Engine(dispatch, receiver);
         if (registerInActiveEngines) {
            if (!Engine.shutdownHookInitialized) {
               synchronized (Engine.ENGINES) {
                  if (!Engine.shutdownHookInitialized) {
                     Engine.shutdownHookInitialized = true;
                     Runtime.getRuntime().addShutdownHook(new Thread(new Engine.EngineShutDownHook()));
                  }
               }
            }

            Engine.ENGINES.put(engine, null);
         }

         return engine;
      }

      @Override
      public void engineClosed(Engine engine) {
         Engine.ENGINES.remove(engine);
      }

      @Override
      public Language newLanguage(AbstractPolyglotImpl.AbstractLanguageDispatch dispatch, Object receiver) {
         return new Language(dispatch, receiver);
      }

      @Override
      public Instrument newInstrument(AbstractPolyglotImpl.AbstractInstrumentDispatch dispatch, Object receiver) {
         return new Instrument(dispatch, receiver);
      }

      @Override
      public Object getReceiver(Instrument instrument) {
         return instrument.receiver;
      }

      @Override
      public Object getContext(Value value) {
         return value.context;
      }

      @Override
      public Value newValue(AbstractPolyglotImpl.AbstractValueDispatch dispatch, Object context, Object receiver) {
         return new Value(dispatch, context, receiver);
      }

      @Override
      public Source newSource(AbstractPolyglotImpl.AbstractSourceDispatch dispatch, Object receiver) {
         return new Source(dispatch, receiver);
      }

      @Override
      public Object getReceiver(Language language) {
         return language.receiver;
      }

      @Override
      public SourceSection newSourceSection(Source source, AbstractPolyglotImpl.AbstractSourceSectionDispatch dispatch, Object receiver) {
         return new SourceSection(source, dispatch, receiver);
      }

      @Override
      public AbstractPolyglotImpl.AbstractValueDispatch getDispatch(Value value) {
         return value.dispatch;
      }

      @Override
      public AbstractPolyglotImpl.AbstractInstrumentDispatch getDispatch(Instrument value) {
         return value.dispatch;
      }

      @Override
      public AbstractPolyglotImpl.AbstractContextDispatch getDispatch(Context context) {
         return context.dispatch;
      }

      @Override
      public AbstractPolyglotImpl.AbstractEngineDispatch getDispatch(Engine engine) {
         return engine.dispatch;
      }

      @Override
      public AbstractPolyglotImpl.AbstractSourceDispatch getDispatch(Source source) {
         return source.dispatch;
      }

      @Override
      public AbstractPolyglotImpl.AbstractSourceSectionDispatch getDispatch(SourceSection sourceSection) {
         return sourceSection.dispatch;
      }

      @Override
      public ResourceLimitEvent newResourceLimitsEvent(Context context) {
         return new ResourceLimitEvent(context);
      }

      @Override
      public AbstractPolyglotImpl.AbstractLanguageDispatch getDispatch(Language value) {
         return value.dispatch;
      }

      @Override
      public Object getReceiver(ResourceLimits value) {
         return value.receiver;
      }

      @Override
      public Object getReceiver(Source source) {
         return source.receiver;
      }

      @Override
      public Object getReceiver(SourceSection sourceSection) {
         return sourceSection.receiver;
      }

      @Override
      public PolyglotException newLanguageException(String message, AbstractPolyglotImpl.AbstractExceptionDispatch dispatch, Object receiver) {
         return new PolyglotException(message, dispatch, receiver);
      }

      @Override
      public AbstractPolyglotImpl.AbstractStackFrameImpl getDispatch(PolyglotException.StackFrame value) {
         return value.impl;
      }

      @Override
      public Object getReceiver(Value value) {
         return value.receiver;
      }

      @Override
      public Object getReceiver(Context context) {
         return context.receiver;
      }

      @Override
      public Object getReceiver(Engine engine) {
         return engine.receiver;
      }

      @Override
      public Object getReceiver(PolyglotException polyglot) {
         return polyglot.impl;
      }

      @Override
      public PolyglotException.StackFrame newPolyglotStackTraceElement(AbstractPolyglotImpl.AbstractStackFrameImpl dispatch, Object receiver) {
         return (PolyglotException)receiver.new StackFrame(dispatch);
      }

      @Override
      public boolean allowsAccess(HostAccess access, AnnotatedElement element) {
         return access.allowsAccess(element);
      }

      @Override
      public boolean allowsImplementation(HostAccess access, Class<?> type) {
         return access.allowsImplementation(type);
      }

      @Override
      public boolean isMethodScopingEnabled(HostAccess access) {
         return access.isMethodScopingEnabled();
      }

      @Override
      public boolean isMethodScoped(HostAccess access, Executable e) {
         return access.isMethodScoped(e);
      }

      @Override
      public List<Object> getTargetMappings(HostAccess access) {
         return access.getTargetMappings();
      }

      @Override
      public boolean isArrayAccessible(HostAccess access) {
         return access.allowArrayAccess;
      }

      @Override
      public boolean isListAccessible(HostAccess access) {
         return access.allowListAccess;
      }

      @Override
      public boolean isBufferAccessible(HostAccess access) {
         return access.allowBufferAccess;
      }

      @Override
      public boolean isIterableAccessible(HostAccess access) {
         return access.allowIterableAccess;
      }

      @Override
      public boolean isIteratorAccessible(HostAccess access) {
         return access.allowIteratorAccess;
      }

      @Override
      public boolean isMapAccessible(HostAccess access) {
         return access.allowMapAccess;
      }

      @Override
      public boolean allowsPublicAccess(HostAccess access) {
         return access.allowPublic;
      }

      @Override
      public boolean allowsAccessInheritance(HostAccess access) {
         return access.allowAccessInheritance;
      }

      @Override
      public Object getHostAccessImpl(HostAccess conf) {
         return conf.impl;
      }

      @Override
      public void setHostAccessImpl(HostAccess conf, Object impl) {
         conf.impl = impl;
      }

      @Override
      public UnmodifiableEconomicSet<String> getEvalAccess(PolyglotAccess access, String language) {
         return access.getEvalAccess(language);
      }

      @Override
      public UnmodifiableEconomicMap<String, UnmodifiableEconomicSet<String>> getEvalAccess(PolyglotAccess access) {
         return access.getEvalAccess();
      }

      @Override
      public UnmodifiableEconomicSet<String> getBindingsAccess(PolyglotAccess access) {
         return access.getBindingsAccess();
      }

      @Override
      public String validatePolyglotAccess(PolyglotAccess access, Set<String> languages) {
         return access.validate(languages);
      }
   }

   public final class Builder {
      private OutputStream out = System.out;
      private OutputStream err = System.err;
      private InputStream in;
      private Map<String, String> options;
      private boolean allowExperimentalOptions;
      private boolean useSystemProperties;
      private boolean boundEngine;
      private MessageTransport messageTransport;
      private Object customLogHandler;
      private String[] permittedLanguages;

      Builder(String[] permittedLanguages) {
         this.in = System.in;
         this.options = new HashMap<>();
         this.allowExperimentalOptions = false;
         this.useSystemProperties = true;
         Objects.requireNonNull(permittedLanguages);

         for (String language : permittedLanguages) {
            Objects.requireNonNull(language);
         }

         this.permittedLanguages = permittedLanguages;
      }

      Engine.Builder setBoundEngine(boolean boundEngine) {
         this.boundEngine = boundEngine;
         return this;
      }

      public Engine.Builder out(OutputStream out) {
         Objects.requireNonNull(out);
         this.out = out;
         return this;
      }

      public Engine.Builder err(OutputStream err) {
         Objects.requireNonNull(err);
         this.err = err;
         return this;
      }

      public Engine.Builder in(InputStream in) {
         Objects.requireNonNull(in);
         this.in = in;
         return this;
      }

      public Engine.Builder allowExperimentalOptions(boolean enabled) {
         this.allowExperimentalOptions = enabled;
         return this;
      }

      public Engine.Builder useSystemProperties(boolean enabled) {
         this.useSystemProperties = enabled;
         return this;
      }

      public Engine.Builder option(String key, String value) {
         Objects.requireNonNull(key, "Key must not be null.");
         Objects.requireNonNull(value, "Value must not be null.");
         this.options.put(key, value);
         return this;
      }

      public Engine.Builder options(Map<String, String> options) {
         for (String key : options.keySet()) {
            Objects.requireNonNull(options.get(key), "All option values must be non-null.");
         }

         this.options.putAll(options);
         return this;
      }

      public Engine.Builder serverTransport(final MessageTransport serverTransport) {
         Objects.requireNonNull(serverTransport, "MessageTransport must be non null.");
         this.messageTransport = serverTransport;
         return this;
      }

      public Engine.Builder logHandler(final Handler logHandler) {
         Objects.requireNonNull(logHandler, "Handler must be non null.");
         this.customLogHandler = logHandler;
         return this;
      }

      public Engine.Builder logHandler(final OutputStream logOut) {
         Objects.requireNonNull(logOut, "LogOut must be non null.");
         this.customLogHandler = logOut;
         return this;
      }

      public Engine build() {
         AbstractPolyglotImpl polyglot = Engine.getImpl();
         if (polyglot == null) {
            throw new IllegalStateException("The Polyglot API implementation failed to load.");
         } else {
            return polyglot.buildEngine(
               this.permittedLanguages,
               this.out,
               this.err,
               this.in,
               this.options,
               this.useSystemProperties,
               this.allowExperimentalOptions,
               this.boundEngine,
               this.messageTransport,
               this.customLogHandler,
               polyglot.createHostLanguage(polyglot.createHostAccess()),
               false,
               true,
               null
            );
         }
      }
   }

   private static final class EngineShutDownHook implements Runnable {
      @Override
      public void run() {
         Engine[] engines;
         synchronized (Engine.ENGINES) {
            engines = Engine.ENGINES.keySet().toArray(new Engine[0]);
         }

         for (Engine engine : engines) {
            engine.dispatch.shutdown(engine.receiver);
         }
      }
   }

   private static final class ImplHolder {
      private static AbstractPolyglotImpl IMPL = Engine.initEngineImpl();

      private static void preInitializeEngine() {
         IMPL.preInitializeEngine(IMPL.createHostLanguage(IMPL.createHostAccess()));
      }

      private static void resetPreInitializedEngine() {
         IMPL.resetPreInitializedEngine();
      }

      private static void debugContextPreInitialization() {
         if (!ImageInfo.inImageCode() && System.getProperty("polyglot.image-build-time.PreinitializeContexts") != null) {
            IMPL.preInitializeEngine(IMPL.createHostLanguage(IMPL.createHostAccess()));
         }
      }

      static {
         try {
            Class.forName("org.graalvm.polyglot.management.Management", true, Engine.class.getClassLoader());
         } catch (ReflectiveOperationException var1) {
            throw new InternalError(var1);
         }

         debugContextPreInitialization();
      }
   }

   private static class PolyglotInvalid extends AbstractPolyglotImpl {
      static boolean AOT;

      @Override
      public int getPriority() {
         return Integer.MIN_VALUE;
      }

      @Override
      public Context getCurrentContext() {
         throw noPolyglotImplementationFound();
      }

      @Override
      public Engine buildEngine(
         String[] permittedLanguages,
         OutputStream out,
         OutputStream err,
         InputStream in,
         Map<String, String> arguments,
         boolean useSystemProperties,
         boolean allowExperimentalOptions,
         boolean boundEngine,
         MessageTransport messageInterceptor,
         Object logHandlerOrStream,
         Object hostLanguage,
         boolean hostLanguageOnly,
         boolean registerInActiveEngines,
         AbstractPolyglotImpl.AbstractPolyglotHostService polyglotHostService
      ) {
         throw noPolyglotImplementationFound();
      }

      @Override
      public Object createHostLanguage(AbstractPolyglotImpl.AbstractHostAccess access) {
         throw noPolyglotImplementationFound();
      }

      @Override
      public Object buildLimits(long statementLimit, Predicate<Source> statementLimitSourceFilter, Consumer<ResourceLimitEvent> onLimit) {
         throw noPolyglotImplementationFound();
      }

      @Override
      public AbstractPolyglotImpl.AbstractHostAccess createHostAccess() {
         throw noPolyglotImplementationFound();
      }

      private static RuntimeException noPolyglotImplementationFound() {
         String suggestion;
         if (AOT) {
            suggestion = "Make sure a language is added to the classpath (e.g., native-image --language:js).";
         } else {
            suggestion = "Make sure the truffle-api.jar is on the classpath.";
         }

         return new IllegalStateException("No language and polyglot implementation was found on the classpath. " + suggestion);
      }

      @Override
      public Class<?> loadLanguageClass(String className) {
         return null;
      }

      @Override
      public void preInitializeEngine(Object hostLanguage) {
      }

      @Override
      public void resetPreInitializedEngine() {
      }

      @Override
      public Value asValue(Object o) {
         throw noPolyglotImplementationFound();
      }

      @Override
      public FileSystem newDefaultFileSystem() {
         throw noPolyglotImplementationFound();
      }

      @Override
      public FileSystem allowLanguageHomeAccess(FileSystem fileSystem) {
         throw noPolyglotImplementationFound();
      }

      @Override
      public FileSystem newReadOnlyFileSystem(FileSystem fileSystem) {
         throw noPolyglotImplementationFound();
      }

      @Override
      public ProcessHandler newDefaultProcessHandler() {
         throw noPolyglotImplementationFound();
      }

      @Override
      public boolean isDefaultProcessHandler(ProcessHandler processHandler) {
         return false;
      }

      @Override
      public AbstractPolyglotImpl.ThreadScope createThreadScope() {
         return null;
      }

      @Override
      public <S, T> Object newTargetTypeMapping(
         Class<S> sourceType, Class<T> targetType, Predicate<S> acceptsValue, Function<S, T> convertValue, HostAccess.TargetMappingPrecedence precedence
      ) {
         return new Object();
      }

      @Override
      public Source build(
         String language,
         Object origin,
         URI uri,
         String name,
         String mimeType,
         Object content,
         boolean interactive,
         boolean internal,
         boolean cached,
         Charset encoding,
         URL url,
         String path
      ) throws IOException {
         throw noPolyglotImplementationFound();
      }

      @Override
      public String findLanguage(File file) throws IOException {
         return null;
      }

      @Override
      public String findLanguage(URL url) throws IOException {
         return null;
      }

      @Override
      public String findMimeType(File file) throws IOException {
         return null;
      }

      @Override
      public String findMimeType(URL url) throws IOException {
         return null;
      }

      @Override
      public String findLanguage(String mimeType) {
         return null;
      }

      static {
         Boolean aot = AccessController.doPrivileged(new PrivilegedAction<Boolean>() {
            public Boolean run() {
               return Boolean.getBoolean("com.oracle.graalvm.isaot");
            }
         });
         AOT = aot;
      }
   }
}
