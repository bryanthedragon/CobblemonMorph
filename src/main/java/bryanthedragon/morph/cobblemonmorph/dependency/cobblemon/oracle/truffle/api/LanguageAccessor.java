package com.oracle.truffle.api;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.nodes.ExecutableNode;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.graalvm.options.OptionDescriptor;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.options.OptionValues;
import org.graalvm.polyglot.io.FileSystem;

final class LanguageAccessor extends Accessor {
   static final LanguageAccessor ACCESSOR = new LanguageAccessor();
   static final Accessor.NodeSupport NODES = ACCESSOR.nodeSupport();
   static final Accessor.SourceSupport SOURCE = ACCESSOR.sourceSupport();
   static final Accessor.InstrumentSupport INSTRUMENT = ACCESSOR.instrumentSupport();
   static final Accessor.EngineSupport ENGINE = ACCESSOR.engineSupport();
   static final Accessor.InteropSupport INTEROP = ACCESSOR.interopSupport();
   static final Accessor.RuntimeSupport RUNTIME = ACCESSOR.runtimeSupport();
   static final Accessor.ExceptionSupport EXCEPTIONS = ACCESSOR.exceptionSupport();

   private LanguageAccessor() {
   }

   static Accessor.EngineSupport engineAccess() {
      return ACCESSOR.engineSupport();
   }

   static Accessor.InstrumentSupport instrumentAccess() {
      return ACCESSOR.instrumentSupport();
   }

   static Accessor.NodeSupport nodesAccess() {
      return ACCESSOR.nodeSupport();
   }

   static Accessor.InteropSupport interopAccess() {
      return ACCESSOR.interopSupport();
   }

   static Accessor.IOSupport ioAccess() {
      return ACCESSOR.ioSupport();
   }

   static final class LanguageImpl extends Accessor.LanguageSupport {
      @Override
      public boolean isTruffleStackTrace(Throwable t) {
         return t instanceof TruffleStackTrace.LazyStackTrace;
      }

      @Override
      public StackTraceElement[] getInternalStackTraceElements(Throwable t) {
         TruffleStackTrace trace = ((TruffleStackTrace.LazyStackTrace)t).getInternalStackTrace();
         return trace == null ? new StackTraceElement[0] : trace.getInternalStackTrace();
      }

      @Override
      public void materializeHostFrames(Throwable original) {
         TruffleStackTrace.materializeHostFrames(original);
      }

      @Override
      public InstrumentInfo createInstrument(Object polyglotInstrument, String id, String name, String version) {
         return new InstrumentInfo(polyglotInstrument, id, name, version);
      }

      @Override
      public Object getPolyglotInstrument(InstrumentInfo info) {
         return info.getPolyglotInstrument();
      }

      @Override
      public void initializeLanguage(TruffleLanguage<?> impl, LanguageInfo language, Object polyglotLanguage, Object polyglotLanguageInstance) {
         impl.languageInfo = language;
         impl.polyglotLanguageInstance = polyglotLanguageInstance;
         if (polyglotLanguageInstance != null) {
            if (impl.contextLocals == null) {
               impl.contextLocals = Collections.emptyList();
            } else {
               LanguageAccessor.ENGINE.initializeLanguageContextLocal(impl.contextLocals, polyglotLanguageInstance);
               impl.contextLocals = Collections.unmodifiableList(impl.contextLocals);
            }

            if (impl.contextThreadLocals == null) {
               impl.contextThreadLocals = Collections.emptyList();
            } else {
               LanguageAccessor.ENGINE.initializeLanguageContextThreadLocal(impl.contextThreadLocals, polyglotLanguageInstance);
               impl.contextThreadLocals = Collections.unmodifiableList(impl.contextThreadLocals);
            }
         }
      }

      @Override
      public void initializeMultiContext(TruffleLanguage<?> language) {
         language.initializeMultipleContexts();
      }

      @Override
      public Object getContext(TruffleLanguage.Env env) {
         Object c = env.getLanguageContext();
         return c != TruffleLanguage.Env.UNSET_CONTEXT ? c : null;
      }

      @Override
      public Object getPolyglotLanguageContext(TruffleLanguage.Env env) {
         return env.getPolyglotLanguageContext();
      }

      @Override
      public Object getFileSystemContext(TruffleFile file) {
         return file.getFileSystemContext();
      }

      @Override
      public Object getLanguageView(TruffleLanguage.Env env, Object value) {
         Object c = env.getLanguageContext();
         if (c == TruffleLanguage.Env.UNSET_CONTEXT) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return null;
         } else {
            Object result = env.getSpi().getLanguageView(c, value);
            return result == null ? LanguageAccessor.engineAccess().getDefaultLanguageView(env.spi, value) : result;
         }
      }

      @Override
      public Object getScope(TruffleLanguage.Env env) {
         Object c = env.getLanguageContext();
         if (c == TruffleLanguage.Env.UNSET_CONTEXT) {
            CompilerDirectives.transferToInterpreter();
            return null;
         } else {
            Object result = env.getSpi().getScope(c);

            assert result == null || LanguageAccessor.ACCESSOR.interopSupport().isScopeObject(result) : String.format("%s is not a scope", result);

            return result;
         }
      }

      @Override
      public Object getPolyglotContext(TruffleContext context) {
         return context.polyglotContext;
      }

      @Override
      public Object invokeContextLocalFactory(Object factory, Object contextImpl) {
         Object result = ((TruffleLanguage.ContextLocalFactory)factory).create(contextImpl);
         if (result == null) {
            throw new IllegalStateException(
               String.format("%s.create is not allowed to return null.", TruffleLanguage.ContextLocalFactory.class.getSimpleName())
            );
         } else {
            return result;
         }
      }

      @Override
      public Object invokeContextThreadLocalFactory(Object factory, Object contextImpl, Thread thread) {
         Object result = ((TruffleLanguage.ContextThreadLocalFactory)factory).create(contextImpl, thread);
         if (result == null) {
            throw new IllegalStateException(
               String.format("%s.create is not allowed to return null.", TruffleLanguage.ContextThreadLocalFactory.class.getSimpleName())
            );
         } else {
            return result;
         }
      }

      @Override
      public TruffleLanguage<?> getSPI(TruffleLanguage.Env env) {
         return env.getSpi();
      }

      @Override
      public TruffleLanguage.Env createEnv(
         Object polyglotLanguageContext,
         TruffleLanguage<?> language,
         OutputStream stdOut,
         OutputStream stdErr,
         InputStream stdIn,
         Map<String, Object> config,
         OptionValues options,
         String[] applicationArguments
      ) {
         TruffleLanguage.Env env = new TruffleLanguage.Env(polyglotLanguageContext, language, stdOut, stdErr, stdIn, config, options, applicationArguments);
         LinkedHashSet<Object> collectedServices = new LinkedHashSet<>();
         LanguageAccessor.instrumentAccess().collectEnvServices(collectedServices, polyglotLanguageContext, language);
         env.services = new ArrayList<>(collectedServices);
         return env;
      }

      @Override
      public Object createEnvContext(TruffleLanguage.Env env, List<Object> servicesCollector) {
         env.languageServicesCollector = servicesCollector;

         Object context;
         try {
            context = env.getSpi().createContext(env);
         } finally {
            env.languageServicesCollector = null;
         }

         env.context = context;
         Assumption contextUnchanged = env.contextUnchangedAssumption;
         env.contextUnchangedAssumption = Truffle.getRuntime().createAssumption("Language context unchanged");
         contextUnchanged.invalidate();
         return context;
      }

      @Override
      public TruffleContext createTruffleContext(Object impl, boolean creator) {
         return new TruffleContext(impl, creator);
      }

      @Override
      public void postInitEnv(TruffleLanguage.Env env) {
         env.postInit();
      }

      @Override
      public boolean isContextInitialized(TruffleLanguage.Env env) {
         return env.isInitialized();
      }

      @Override
      public CallTarget parse(TruffleLanguage.Env env, Source code, Node context, String... argumentNames) {
         return env.getSpi().parse(code, argumentNames);
      }

      @Override
      public ExecutableNode parseInline(TruffleLanguage.Env env, Source code, Node context, MaterializedFrame frame) {
         return env.getSpi().parseInline(code, context, frame);
      }

      @Override
      public LanguageInfo getLanguageInfo(TruffleLanguage.Env env) {
         return env.getSpi().languageInfo;
      }

      @Override
      public void onThrowable(Node callNode, RootCallTarget root, Throwable e, Frame frame) {
         TruffleStackTrace.addStackFrameInfo(callNode, root, e, frame);
      }

      @Override
      public void initializeThread(TruffleLanguage.Env env, Thread current) {
         env.getSpi().initializeThread(env.context, current);
      }

      @Override
      public boolean isThreadAccessAllowed(TruffleLanguage.Env language, Thread thread, boolean singleThread) {
         return language.getSpi().isThreadAccessAllowed(thread, singleThread);
      }

      @Override
      public void initializeMultiThreading(TruffleLanguage.Env env) {
         env.getSpi().initializeMultiThreading(env.context);
      }

      @Override
      public void finalizeContext(TruffleLanguage.Env env) {
         env.getSpi().finalizeContext(env.context);
      }

      @Override
      public void exitContext(TruffleLanguage.Env env, TruffleLanguage.ExitMode exitMode, int exitCode) {
         env.getSpi().exitContext(env.context, exitMode, exitCode);
      }

      @Override
      public void disposeThread(TruffleLanguage.Env env, Thread current) {
         env.getSpi().disposeThread(env.context, current);
      }

      @Override
      public Object evalInContext(Source source, Node node, final MaterializedFrame mFrame) {
         CallTarget target = LanguageAccessor.ACCESSOR.nodeSupport().getLanguage(node.getRootNode()).parse(source);

         try {
            if (target instanceof RootCallTarget) {
               RootNode exec = ((RootCallTarget)target).getRootNode();
               return exec.execute(mFrame);
            } else {
               throw new IllegalStateException(target + "");
            }
         } catch (Exception var6) {
            if (var6 instanceof RuntimeException) {
               throw (RuntimeException)var6;
            } else {
               throw new RuntimeException(var6);
            }
         }
      }

      @Override
      public LanguageInfo getLanguageInfo(TruffleLanguage<?> language) {
         return language.languageInfo;
      }

      @Override
      public Object getPolyglotLanguageInstance(TruffleLanguage<?> language) {
         return language == null ? null : language.polyglotLanguageInstance;
      }

      @Override
      public void dispose(TruffleLanguage.Env env) {
         env.dispose();
      }

      @Override
      public boolean isVisible(TruffleLanguage.Env env, Object value) {
         return env.isVisible(value);
      }

      @Override
      public OptionDescriptors describeOptions(TruffleLanguage<?> language, String requiredGroup) {
         OptionDescriptors descriptors = language.getOptionDescriptors();
         if (descriptors == null) {
            return OptionDescriptors.EMPTY;
         } else {
            assert verifyDescriptors(language, requiredGroup, descriptors);

            return descriptors;
         }
      }

      private static boolean verifyDescriptors(TruffleLanguage<?> language, String requiredGroup, OptionDescriptors descriptors) {
         String groupPlusDot = requiredGroup + ".";

         for (OptionDescriptor descriptor : descriptors) {
            if (!descriptor.getName().equals(requiredGroup) && !descriptor.getName().startsWith(groupPlusDot)) {
               throw new IllegalArgumentException(
                  String.format(
                     "Illegal option prefix in name '%s' specified for option described by language '%s'. The option prefix must match the id of the language '%s'.",
                     descriptor.getName(),
                     language.getClass().getName(),
                     requiredGroup
                  )
               );
            }
         }

         return true;
      }

      @Override
      public TruffleLanguage.Env patchEnvContext(
         TruffleLanguage.Env env,
         OutputStream stdOut,
         OutputStream stdErr,
         InputStream stdIn,
         Map<String, Object> config,
         OptionValues options,
         String[] applicationArguments
      ) {
         assert env.spi != null;

         TruffleLanguage.Env newEnv = this.createEnv(env.polyglotLanguageContext, env.spi, stdOut, stdErr, stdIn, config, options, applicationArguments);
         newEnv.initialized = env.initialized;
         newEnv.context = env.context;
         boolean success = env.getSpi().patchContext(env.context, newEnv);
         if (success) {
            env.valid = false;
            return newEnv;
         } else {
            return null;
         }
      }

      @Override
      public Object createFileSystemContext(Object engineFileSystemContext, FileSystem fileSystem) {
         return new TruffleFile.FileSystemContext(engineFileSystemContext, fileSystem);
      }

      @Override
      public Object getFileSystemEngineObject(Object fileSystemContext) {
         return ((TruffleFile.FileSystemContext)fileSystemContext).engineObject;
      }

      @Override
      public String detectMimeType(TruffleFile file, Set<String> validMimeTypes) {
         return file.detectMimeType(validMimeTypes);
      }

      @Override
      public Charset detectEncoding(TruffleFile file, String mimeType) {
         if (mimeType == null) {
            throw new IllegalArgumentException("MimeType must be non null.");
         } else {
            return file.detectEncoding(mimeType);
         }
      }

      @Override
      public void configureLoggers(Object vmObject, Map<String, Level> logLevels, Object... loggers) {
         for (Object loggerCache : loggers) {
            if (logLevels == null) {
               ((TruffleLogger.LoggerCache)loggerCache).removeLogLevelsForVMObject(vmObject);
            } else {
               ((TruffleLogger.LoggerCache)loggerCache).addLogLevelsForVMObject(vmObject, logLevels);
            }
         }
      }

      @Override
      public boolean areOptionsCompatible(TruffleLanguage<?> language, OptionValues firstContextOptions, OptionValues newContextOptions) {
         return language.areOptionsCompatible(firstContextOptions, newContextOptions);
      }

      @Override
      public TruffleLanguage<?> getLanguage(TruffleLanguage.Env env) {
         return env.getSpi();
      }

      @Override
      public TruffleFile getTruffleFile(String path, Object fileSystemContext) {
         TruffleFile.FileSystemContext ctx = (TruffleFile.FileSystemContext)fileSystemContext;
         return new TruffleFile(ctx, ctx.fileSystem.parsePath(path));
      }

      @Override
      public TruffleFile getTruffleFile(Object fileSystemContext, URI uri) {
         TruffleFile.FileSystemContext ctx = (TruffleFile.FileSystemContext)fileSystemContext;
         return new TruffleFile(ctx, ctx.fileSystem.parsePath(uri));
      }

      @Override
      public boolean hasAllAccess(Object fileSystemContext) {
         TruffleFile.FileSystemContext ctx = (TruffleFile.FileSystemContext)fileSystemContext;
         return LanguageAccessor.engineAccess().hasAllAccess(ctx.fileSystem);
      }

      @Override
      public TruffleFile getTruffleFile(Object context, String path) {
         return this.getTruffleFile(path, context);
      }

      @Override
      public Object getDefaultLoggers() {
         return TruffleLogger.LoggerCache.getInstance();
      }

      @Override
      public Object createEngineLoggers(Object spi) {
         return new TruffleLogger.LoggerCache(spi);
      }

      @Override
      public Object getLoggersSPI(Object loggerCache) {
         return ((TruffleLogger.LoggerCache)loggerCache).getSPI();
      }

      @Override
      public void closeEngineLoggers(Object loggers) {
         ((TruffleLogger.LoggerCache)loggers).close();
      }

      @Override
      public TruffleLogger getLogger(String id, String loggerName, Object loggers) {
         return TruffleLogger.getLogger(id, loggerName, (TruffleLogger.LoggerCache)loggers);
      }

      @Override
      public Object getLoggerCache(TruffleLogger logger) {
         return logger.getLoggerCache();
      }

      @Override
      public FileSystem getFileSystem(TruffleFile truffleFile) {
         return truffleFile.getSPIFileSystem();
      }

      @Override
      public Path getPath(TruffleFile truffleFile) {
         return truffleFile.getSPIPath();
      }

      @Override
      public boolean isSynchronousTLAction(ThreadLocalAction action) {
         return action.isSynchronous();
      }

      @Override
      public boolean isSideEffectingTLAction(ThreadLocalAction action) {
         return action.hasSideEffects();
      }

      @Override
      public boolean isRecurringTLAction(ThreadLocalAction action) {
         return action.isRecurring();
      }

      @Override
      public void performTLAction(ThreadLocalAction action, ThreadLocalAction.Access access) {
         action.perform(access);
      }
   }
}
