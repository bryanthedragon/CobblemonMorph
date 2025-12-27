package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.impl.DispatchOutputStream;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.strings.TruffleString;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Handler;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.ResourceLimitEvent;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.SourceSection;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.io.ByteSequence;
import org.graalvm.polyglot.io.FileSystem;
import org.graalvm.polyglot.io.MessageTransport;
import org.graalvm.polyglot.io.ProcessHandler;
import org.graalvm.polyglot.proxy.Proxy;

public final class PolyglotImpl extends AbstractPolyglotImpl {
   static final Object[] EMPTY_ARGS = new Object[0];
   static final String OPTION_GROUP_ENGINE = "engine";
   static final String PROP_ALLOW_EXPERIMENTAL_OPTIONS = "polyglot.engine.AllowExperimentalOptions";
   private final PolyglotSourceDispatch sourceDispatch = new PolyglotSourceDispatch(this);
   private final PolyglotSourceSectionDispatch sourceSectionDispatch = new PolyglotSourceSectionDispatch(this);
   private final PolyglotExecutionListenerDispatch executionListenerDispatch = new PolyglotExecutionListenerDispatch(this);
   private final PolyglotExecutionEventDispatch executionEventDispatch = new PolyglotExecutionEventDispatch(this);
   final PolyglotEngineDispatch engineDispatch = new PolyglotEngineDispatch(this);
   final PolyglotContextDispatch contextDispatch = new PolyglotContextDispatch(this);
   private final PolyglotExceptionDispatch exceptionDispatch = new PolyglotExceptionDispatch(this);
   final PolyglotInstrumentDispatch instrumentDispatch = new PolyglotInstrumentDispatch(this);
   final PolyglotLanguageDispatch languageDispatch = new PolyglotLanguageDispatch(this);
   private final AtomicReference<PolyglotEngineImpl> preInitializedEngineRef = new AtomicReference<>();
   private final Map<Class<?>, PolyglotValueDispatch> primitiveValues = new HashMap<>();
   Value hostNull;
   private PolyglotValueDispatch disconnectedHostValue;
   private volatile Object defaultFileSystemContext;
   private static volatile AbstractPolyglotImpl abstractImpl;

   @Override
   public int getPriority() {
      return 0;
   }

   private static AbstractPolyglotImpl getImpl() {
      AbstractPolyglotImpl local = abstractImpl;
      if (local == null) {
         try {
            Method f = Engine.class.getDeclaredMethod("getImpl");
            f.setAccessible(true);
            abstractImpl = local = (AbstractPolyglotImpl)f.invoke(null);

            assert local != null : "polyglot impl not found";
         } catch (Exception var2) {
            throw new AssertionError(var2);
         }
      }

      return local;
   }

   static PolyglotImpl getInstance() {
      AbstractPolyglotImpl polyglot = getImpl();

      while (polyglot != null && !(polyglot instanceof PolyglotImpl)) {
         polyglot = polyglot.getNext();
      }

      if (polyglot == null) {
         throw new AssertionError(String.format("%s not found or installed but required.", PolyglotImpl.class.getSimpleName()));
      } else {
         return (PolyglotImpl)polyglot;
      }
   }

   PolyglotEngineImpl getPreinitializedEngine() {
      return this.preInitializedEngineRef.get();
   }

   @Override
   protected void initialize() {
      this.hostNull = this.getAPIAccess().newValue(PolyglotValueDispatch.createHostNull(this), null, EngineAccessor.HOST.getHostNull());
      this.disconnectedHostValue = new PolyglotValueDispatch.HostValue(this);
      PolyglotValueDispatch.createDefaultValues(this, null, this.primitiveValues);
   }

   @Override
   public Object buildLimits(long statementLimit, Predicate<Source> statementLimitSourceFilter, Consumer<ResourceLimitEvent> onLimit) {
      try {
         return new PolyglotLimits(statementLimit, statementLimitSourceFilter, onLimit);
      } catch (Throwable var6) {
         throw guestToHostException(this, var6);
      }
   }

   AbstractPolyglotImpl.AbstractSourceDispatch getSourceDispatch() {
      return this.sourceDispatch;
   }

   AbstractPolyglotImpl.AbstractSourceSectionDispatch getSourceSectionDispatch() {
      return this.sourceSectionDispatch;
   }

   AbstractPolyglotImpl.AbstractExecutionListenerDispatch getExecutionListenerDispatch() {
      return this.executionListenerDispatch;
   }

   AbstractPolyglotImpl.AbstractExecutionEventDispatch getExecutionEventDispatch() {
      return this.executionEventDispatch;
   }

   @Override
   public Context getCurrentContext() {
      try {
         PolyglotContextImpl context = PolyglotFastThreadLocals.getContext(null);
         if (context == null) {
            throw PolyglotEngineException.illegalState(
               "No current context is available. Make sure the Java method is invoked by a Graal guest language or a context is entered using Context.enter()."
            );
         } else {
            Context api = context.api;
            if (api == null) {
               context.api = api = this.getAPIAccess().newContext(this.contextDispatch, context, context.engine.api);
            }

            return api;
         }
      } catch (Throwable var3) {
         throw guestToHostException(this, var3);
      }
   }

   @Override
   public Engine buildEngine(
      String[] permittedLanguages,
      OutputStream out,
      OutputStream err,
      InputStream in,
      Map<String, String> originalOptions,
      boolean useSystemProperties,
      final boolean allowExperimentalOptions,
      boolean boundEngine,
      MessageTransport messageInterceptor,
      Object logHandlerOrStream,
      Object hostLanguage,
      boolean hostLanguageOnly,
      boolean registerInActiveEngines,
      AbstractPolyglotImpl.AbstractPolyglotHostService polyglotHostService
   ) {
      PolyglotEngineImpl impl = null;

      try {
         if (TruffleOptions.AOT) {
            EngineAccessor.ACCESSOR.initializeNativeImageTruffleLocator();
         }

         OutputStream resolvedOut = (OutputStream)(out == null ? System.out : out);
         OutputStream resolvedErr = (OutputStream)(err == null ? System.err : err);
         InputStream resolvedIn = in == null ? System.in : in;
         DispatchOutputStream dispatchOut = EngineAccessor.INSTRUMENT.createDispatchOutput(resolvedOut);
         DispatchOutputStream dispatchErr = EngineAccessor.INSTRUMENT.createDispatchOutput(resolvedErr);
         Handler logHandler = PolyglotLoggers.asHandler(logHandlerOrStream);
         boolean useAllowExperimentalOptions = allowExperimentalOptions
            || Boolean.parseBoolean(EngineAccessor.RUNTIME.getSavedProperty("polyglot.engine.AllowExperimentalOptions"));
         Map<String, String> options = originalOptions;
         if (useSystemProperties) {
            options = PolyglotEngineImpl.readOptionsFromSystemProperties(originalOptions);
         }

         PolyglotEngineImpl.LogConfig logConfig = new PolyglotEngineImpl.LogConfig();
         OptionValuesImpl engineOptions = createEngineOptions(options, logConfig, useAllowExperimentalOptions);
         logHandler = logHandler != null ? logHandler : PolyglotEngineImpl.createLogHandler(logConfig, dispatchErr);
         PolyglotLoggers.EngineLoggerProvider loggerProvider = new PolyglotLoggers.EngineLoggerProvider(logHandler, logConfig.logLevels);
         impl = (PolyglotEngineImpl)EngineAccessor.RUNTIME.tryLoadCachedEngine(engineOptions, loggerProvider);
         if (impl == null && boundEngine && !hostLanguageOnly && !EngineAccessor.RUNTIME.isStoreEnabled(engineOptions)) {
            impl = this.preInitializedEngineRef.getAndSet(null);
         }

         if (impl != null) {
            if (hostLanguage.getClass() != impl.getHostLanguageSPI().getClass()) {
               impl = null;
            } else {
               impl.patch(
                  dispatchOut,
                  dispatchErr,
                  resolvedIn,
                  engineOptions,
                  logConfig,
                  loggerProvider,
                  options,
                  useAllowExperimentalOptions,
                  boundEngine,
                  logHandler,
                  polyglotHostService
               );
            }
         }

         if (impl == null) {
            impl = new PolyglotEngineImpl(
               this,
               permittedLanguages,
               dispatchOut,
               dispatchErr,
               resolvedIn,
               engineOptions,
               logConfig.logLevels,
               loggerProvider,
               options,
               useAllowExperimentalOptions,
               boundEngine,
               false,
               messageInterceptor,
               logHandler,
               (TruffleLanguage<Object>)hostLanguage,
               hostLanguageOnly,
               polyglotHostService
            );
         }

         return this.getAPIAccess().newEngine(this.engineDispatch, impl, registerInActiveEngines);
      } catch (Throwable var27) {
         if (impl == null) {
            throw guestToHostException(this, var27);
         } else {
            throw guestToHostException(impl, var27);
         }
      }
   }

   @Override
   protected OptionDescriptors createEngineOptionDescriptors() {
      return PolyglotEngineImpl.createEngineOptionDescriptors();
   }

   static OptionValuesImpl createEngineOptions(Map<String, String> options, PolyglotEngineImpl.LogConfig logOptions, boolean allowExperimentalOptions) {
      OptionDescriptors engineOptionDescriptors = getInstance().createAllEngineOptionDescriptors();
      Map<String, String> engineOptions = new HashMap<>();
      PolyglotEngineImpl.parseEngineOptions(options, engineOptions, logOptions);
      OptionValuesImpl values = new OptionValuesImpl(engineOptionDescriptors, true);
      values.putAll(null, engineOptions, allowExperimentalOptions);
      return values;
   }

   @Override
   public void preInitializeEngine(Object hostLanguage) {
      PolyglotEngineImpl engine = this.createDefaultEngine((TruffleLanguage<Object>)hostLanguage);
      this.getAPIAccess().newEngine(this.engineDispatch, engine, false);

      try {
         engine.preInitialize();
      } finally {
         LanguageCache.resetNativeImageCacheLanguageHomes();
         engine.logLevels.clear();
         engine.logHandler.close();
         engine.logHandler = null;
      }

      this.preInitializedEngineRef.set(engine);
   }

   PolyglotEngineImpl createDefaultEngine(TruffleLanguage<Object> hostLanguage) {
      Map<String, String> options = PolyglotEngineImpl.readOptionsFromSystemProperties(new HashMap<>());
      PolyglotEngineImpl.LogConfig logConfig = new PolyglotEngineImpl.LogConfig();
      OptionValuesImpl engineOptions = createEngineOptions(options, logConfig, true);
      DispatchOutputStream out = EngineAccessor.INSTRUMENT.createDispatchOutput(System.out);
      DispatchOutputStream err = EngineAccessor.INSTRUMENT.createDispatchOutput(System.err);
      Handler logHandler = PolyglotEngineImpl.createLogHandler(logConfig, err);
      PolyglotLoggers.EngineLoggerProvider loggerProvider = new PolyglotLoggers.EngineLoggerProvider(logHandler, logConfig.logLevels);
      PolyglotEngineImpl engine = new PolyglotEngineImpl(
         this,
         new String[0],
         out,
         err,
         System.in,
         engineOptions,
         logConfig.logLevels,
         loggerProvider,
         options,
         true,
         true,
         true,
         null,
         logHandler,
         hostLanguage,
         false,
         null
      );
      this.getAPIAccess().newEngine(this.engineDispatch, engine, false);
      return engine;
   }

   public TruffleLanguage<Object> createHostLanguage(AbstractPolyglotImpl.AbstractHostAccess access) {
      return (TruffleLanguage<Object>)EngineAccessor.HOST.createDefaultHostLanguage(this, access);
   }

   @Override
   public void resetPreInitializedEngine() {
      this.preInitializedEngineRef.set(null);
   }

   @Override
   public Class<?> loadLanguageClass(String className) {
      for (Supplier<ClassLoader> supplier : EngineAccessor.locatorOrDefaultLoaders()) {
         ClassLoader loader = supplier.get();
         if (loader != null) {
            try {
               Class<?> c = loader.loadClass(className);
               if (!TruffleOptions.AOT) {
                  ModuleUtils.exportTo(loader, null);
               }

               return c;
            } catch (ClassNotFoundException var6) {
            }
         }
      }

      return null;
   }

   @Override
   public <S, T> Object newTargetTypeMapping(
      Class<S> sourceType, Class<T> targetType, Predicate<S> acceptsValue, Function<S, T> convertValue, HostAccess.TargetMappingPrecedence precedence
   ) {
      return EngineAccessor.HOST.newTargetTypeMapping(sourceType, targetType, acceptsValue, convertValue, precedence);
   }

   Value asValue(PolyglotContextImpl currentContext, Object hostValue) {
      if (currentContext != null) {
         return currentContext.asValue(hostValue);
      } else {
         assert !(hostValue instanceof Value);

         Object guestValue = null;
         if (hostValue == null) {
            return this.hostNull;
         } else if (isGuestPrimitive(hostValue)) {
            return this.getAPIAccess().newValue(this.primitiveValues.get(hostValue.getClass()), null, hostValue);
         } else if (PolyglotWrapper.isInstance(hostValue)) {
            PolyglotWrapper hostWrapper = PolyglotWrapper.asInstance(hostValue);
            PolyglotLanguageContext languageContext = hostWrapper.getLanguageContext();

            assert languageContext != null : "HostWrappers must be guaranteed to have non-null language context.";

            guestValue = hostWrapper.getGuestObject();
            return languageContext.asValue(guestValue);
         } else {
            if (hostValue instanceof TruffleObject) {
               guestValue = hostValue;
            } else if (hostValue instanceof Proxy) {
               guestValue = EngineAccessor.HOST.toDisconnectedHostProxy((Proxy)hostValue);
            } else {
               guestValue = EngineAccessor.HOST.toDisconnectedHostObject(hostValue);
            }

            return this.getAPIAccess().newValue(this.disconnectedHostValue, null, guestValue);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Value asValue(Object hostValue) {
      try {
         PolyglotContextImpl currentContext = PolyglotFastThreadLocals.getContext(null);
         return this.asValue(currentContext, hostValue);
      } catch (Throwable var3) {
         throw guestToHostException(this, var3);
      }
   }

   @Override
   public FileSystem newDefaultFileSystem() {
      return FileSystems.newDefaultFileSystem();
   }

   @Override
   public FileSystem allowLanguageHomeAccess(FileSystem fileSystem) {
      return FileSystems.allowLanguageHomeAccess(fileSystem);
   }

   @Override
   public FileSystem newReadOnlyFileSystem(FileSystem fileSystem) {
      return FileSystems.newReadOnlyFileSystem(fileSystem);
   }

   @Override
   public ProcessHandler newDefaultProcessHandler() {
      return PolyglotEngineImpl.ALLOW_CREATE_PROCESS ? ProcessHandlers.newDefaultProcessHandler() : null;
   }

   @Override
   public boolean isDefaultProcessHandler(ProcessHandler processHandler) {
      return ProcessHandlers.isDefault(processHandler);
   }

   @Override
   public AbstractPolyglotImpl.ThreadScope createThreadScope() {
      return null;
   }

   @Override
   public AbstractPolyglotImpl.AbstractHostAccess createHostAccess() {
      return new PolyglotHostAccess(this);
   }

   @Override
   public String findLanguage(File file) throws IOException {
      Objects.requireNonNull(file);
      String mimeType = this.findMimeType(file);
      return mimeType != null ? this.findLanguage(mimeType) : null;
   }

   @Override
   public String findLanguage(URL url) throws IOException {
      String mimeType = this.findMimeType(url);
      return mimeType != null ? this.findLanguage(mimeType) : null;
   }

   @Override
   public String findMimeType(File file) throws IOException {
      Objects.requireNonNull(file);
      TruffleFile truffleFile = EngineAccessor.LANGUAGE.getTruffleFile(file.toPath().toString(), this.getDefaultFileSystemContext());
      return truffleFile.detectMimeType();
   }

   @Override
   public String findMimeType(URL url) throws IOException {
      Objects.requireNonNull(url);
      return EngineAccessor.SOURCE.findMimeType(url, this.getDefaultFileSystemContext());
   }

   @Override
   public String findLanguage(String mimeType) {
      Objects.requireNonNull(mimeType);
      LanguageCache cache = LanguageCache.languageMimes().get(mimeType);
      return cache != null ? cache.getId() : null;
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
      assert language != null;

      com.oracle.truffle.api.source.Source.SourceBuilder builder;
      if (origin instanceof File) {
         builder = EngineAccessor.SOURCE.newBuilder(language, (File)origin);
      } else if (origin instanceof CharSequence) {
         builder = com.oracle.truffle.api.source.Source.newBuilder(language, (CharSequence)origin, name);
      } else if (origin instanceof ByteSequence) {
         builder = com.oracle.truffle.api.source.Source.newBuilder(language, (ByteSequence)origin, name);
      } else if (origin instanceof Reader) {
         builder = com.oracle.truffle.api.source.Source.newBuilder(language, (Reader)origin, name);
      } else if (origin instanceof URL) {
         builder = com.oracle.truffle.api.source.Source.newBuilder(language, (URL)origin);
      } else {
         if (origin != com.oracle.truffle.api.source.Source.CONTENT_NONE) {
            throw CompilerDirectives.shouldNotReachHere();
         }

         builder = com.oracle.truffle.api.source.Source.newBuilder(language, "", name).content(com.oracle.truffle.api.source.Source.CONTENT_NONE);
      }

      if (origin instanceof File || origin instanceof URL) {
         EngineAccessor.SOURCE.setFileSystemContext(builder, this.getDefaultFileSystemContext());
      }

      EngineAccessor.SOURCE.setEmbedderSource(builder, true);
      if (url != null) {
         EngineAccessor.SOURCE.setURL(builder, url);
      }

      if (path != null) {
         EngineAccessor.SOURCE.setPath(builder, path);
      }

      if (content instanceof CharSequence) {
         builder.content((CharSequence)content);
      } else if (content instanceof ByteSequence) {
         builder.content((ByteSequence)content);
      }

      builder.uri(uri);
      builder.name(name);
      builder.internal(internal);
      builder.interactive(interactive);
      builder.mimeType(mimeType);
      builder.cached(cached);
      builder.encoding(encoding);

      try {
         return getOrCreatePolyglotSource(this, builder.build());
      } catch (RuntimeException | IOException var15) {
         throw var15;
      } catch (Exception var16) {
         throw CompilerDirectives.shouldNotReachHere(var16);
      }
   }

   private Object getDefaultFileSystemContext() {
      Object res = this.defaultFileSystemContext;
      if (res == null) {
         synchronized (this) {
            res = this.defaultFileSystemContext;
            if (res == null) {
               PolyglotImpl.EmbedderFileSystemContext context = new PolyglotImpl.EmbedderFileSystemContext();
               res = EngineAccessor.LANGUAGE.createFileSystemContext(context, context.fileSystem);
               this.defaultFileSystemContext = res;
            }
         }
      }

      return res;
   }

   static Source getOrCreatePolyglotSource(PolyglotImpl polyglot, com.oracle.truffle.api.source.Source source) {
      return EngineAccessor.SOURCE.getOrCreatePolyglotSource(source, t -> polyglot.getAPIAccess().newSource(polyglot.sourceDispatch, t));
   }

   static SourceSection getPolyglotSourceSection(PolyglotImpl polyglot, com.oracle.truffle.api.source.SourceSection sourceSection) {
      if (sourceSection == null) {
         return null;
      } else {
         Source polyglotSource = getOrCreatePolyglotSource(polyglot, sourceSection.getSource());
         return polyglot.getAPIAccess().newSourceSection(polyglotSource, polyglot.sourceSectionDispatch, sourceSection);
      }
   }

   @CompilerDirectives.TruffleBoundary
   static <T extends Throwable> RuntimeException engineToLanguageException(Throwable t) throws T {
      assert !(t instanceof PolyglotException) : "polyglot exceptions must not be thrown to the guest language";

      PolyglotEngineException.rethrow(t);
      throw t;
   }

   @CompilerDirectives.TruffleBoundary
   static <T extends Throwable> RuntimeException engineToInstrumentException(Throwable t) throws T {
      assert !(t instanceof PolyglotException) : "polyglot exceptions must not be thrown to the guest instrument";

      PolyglotEngineException.rethrow(t);
      throw t;
   }

   @CompilerDirectives.TruffleBoundary
   static <T extends Throwable> PolyglotException guestToHostException(PolyglotLanguageContext languageContext, T e, boolean entered) {
      assert !(e instanceof PolyglotException) : "polyglot exceptions must not be thrown to the host: " + e;

      PolyglotEngineException.rethrow(e);
      if (languageContext == null) {
         throw new RuntimeException(e);
      } else {
         PolyglotContextImpl context = languageContext.context;
         PolyglotExceptionImpl suppressedImpl = null;
         PolyglotContextImpl.State localContextState = context.state;
         PolyglotExceptionImpl exceptionImpl;
         if (localContextState.isInvalidOrClosed()) {
            exceptionImpl = new PolyglotExceptionImpl(
               context.engine.impl, context.engine, localContextState, context.invalidResourceLimit, context.exitCode, languageContext, e, false, false
            );
         } else {
            try {
               exceptionImpl = new PolyglotExceptionImpl(
                  languageContext.getImpl(), languageContext.context.engine, localContextState, false, 0, languageContext, e, true, entered
               );
            } catch (Throwable var9) {
               exceptionImpl = new PolyglotExceptionImpl(context.engine, localContextState, false, 0, e);
               suppressedImpl = new PolyglotExceptionImpl(context.engine, localContextState, false, 0, var9);
            }
         }

         AbstractPolyglotImpl.APIAccess access = getInstance().getAPIAccess();
         PolyglotException polyglotException = access.newLanguageException(exceptionImpl.getMessage(), getInstance().exceptionDispatch, exceptionImpl);
         if (suppressedImpl != null) {
            polyglotException.addSuppressed(access.newLanguageException(exceptionImpl.getMessage(), getInstance().exceptionDispatch, suppressedImpl));
         }

         return polyglotException;
      }
   }

   static <T extends Throwable> PolyglotException guestToHostException(PolyglotEngineImpl engine, T e) {
      assert !(e instanceof PolyglotException) : "polyglot exceptions must not be thrown to the host: " + e;

      PolyglotEngineException.rethrow(e);
      AbstractPolyglotImpl.APIAccess access = engine.getAPIAccess();
      PolyglotExceptionImpl exceptionImpl = new PolyglotExceptionImpl(engine, null, false, 0, e);
      return access.newLanguageException(exceptionImpl.getMessage(), getInstance().exceptionDispatch, exceptionImpl);
   }

   @CompilerDirectives.TruffleBoundary
   static <T extends Throwable> PolyglotException guestToHostException(PolyglotImpl polyglot, T e) {
      assert !(e instanceof PolyglotException) : "polyglot exceptions must not be thrown to the host: " + e;

      PolyglotEngineException.rethrow(e);
      AbstractPolyglotImpl.APIAccess access = polyglot.getAPIAccess();
      PolyglotExceptionImpl exceptionImpl = new PolyglotExceptionImpl(polyglot, e);
      return access.newLanguageException(exceptionImpl.getMessage(), getInstance().exceptionDispatch, exceptionImpl);
   }

   static boolean isGuestPrimitive(Object receiver) {
      return receiver instanceof Integer
         || receiver instanceof Double
         || receiver instanceof Long
         || receiver instanceof Float
         || receiver instanceof Boolean
         || receiver instanceof Character
         || receiver instanceof Byte
         || receiver instanceof Short
         || receiver instanceof String
         || receiver instanceof TruffleString;
   }

   static final class EmbedderFileSystemContext {
      final FileSystem fileSystem = FileSystems.newDefaultFileSystem();
      final Map<String, LanguageCache> cachedLanguages = LanguageCache.languages();
      final Supplier<Map<String, Collection<? extends TruffleFile.FileTypeDetector>>> fileTypeDetectors = FileSystems.newFileTypeDetectorsSupplier(
         this.cachedLanguages.values()
      );
   }

   interface VMObject {
      PolyglotEngineImpl getEngine();

      default PolyglotImpl getImpl() {
         return this.getEngine().impl;
      }

      default AbstractPolyglotImpl.APIAccess getAPIAccess() {
         return this.getEngine().impl.getAPIAccess();
      }
   }
}
