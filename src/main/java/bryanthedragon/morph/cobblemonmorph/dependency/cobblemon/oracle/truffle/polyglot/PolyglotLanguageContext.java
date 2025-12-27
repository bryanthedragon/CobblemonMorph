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
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import org.graalvm.collections.UnmodifiableEconomicSet;
import org.graalvm.polyglot.PolyglotAccess;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.proxy.Proxy;

final class PolyglotLanguageContext implements PolyglotImpl.VMObject {
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
   private volatile PolyglotLanguageContext.Lazy lazy;
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
      } else {
         UnmodifiableEconomicSet<String> accessibleLanguages = this.getAPIAccess().getBindingsAccess(this.context.config.polyglotAccess);
         return accessibleLanguages == null ? true : accessibleLanguages.contains(this.language.getId());
      }
   }

   boolean isPolyglotEvalAllowed(String targetLanguage) {
      if (this.context.config.polyglotAccess == PolyglotAccess.ALL) {
         return true;
      } else if (targetLanguage != null && this.language.getId().equals(targetLanguage)) {
         return true;
      } else {
         UnmodifiableEconomicSet<String> accessibleLanguages = this.getAPIAccess().getEvalAccess(this.context.config.polyglotAccess, this.language.getId());
         if (accessibleLanguages != null && !accessibleLanguages.isEmpty()) {
            return accessibleLanguages.size() <= 1 && accessibleLanguages.iterator().next().equals(this.language.getId())
               ? false
               : targetLanguage == null || accessibleLanguages.contains(targetLanguage);
         } else {
            return false;
         }
      }
   }

   UncaughtExceptionHandler getPolyglotExceptionHandler() {
      assert this.env != null;

      return this.lazy.uncaughtExceptionHandler;
   }

   Map<String, LanguageInfo> getAccessibleLanguages(boolean allowInternalAndDependent) {
      PolyglotLanguageContext.Lazy l = this.lazy;
      if (l != null) {
         return allowInternalAndDependent ? this.lazy.accessibleInternalLanguages : this.lazy.accessiblePublicLanguages;
      } else {
         return null;
      }
   }

   PolyglotLanguageInstance getLanguageInstanceOrNull() {
      PolyglotLanguageContext.Lazy l = this.lazy;
      return l == null ? null : l.languageInstance;
   }

   PolyglotLanguageInstance getLanguageInstance() {
      assert this.env != null;

      return this.lazy.languageInstance;
   }

   private void checkThreadAccess(TruffleLanguage.Env localEnv) {
      assert Thread.holdsLock(this.context);

      boolean singleThreaded = this.context.isSingleThreaded();
      Thread firstFailingThread = null;

      for (PolyglotThreadInfo threadInfo : this.context.getSeenThreads().values()) {
         if (!EngineAccessor.LANGUAGE.isThreadAccessAllowed(localEnv, threadInfo.getThread(), singleThreaded)) {
            firstFailingThread = threadInfo.getThread();
            break;
         }
      }

      if (firstFailingThread != null) {
         throw PolyglotContextImpl.throwDeniedThreadAccess(firstFailingThread, singleThreaded, Arrays.asList(this.language));
      }
   }

   Object getContextImpl() {
      TruffleLanguage.Env localEnv = this.env;
      if (localEnv != null) {
         return EngineAccessor.LANGUAGE.getContext(localEnv);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return null;
      }
   }

   Object getPublicFileSystemContext() {
      PolyglotLanguageContext.Lazy l = this.lazy;
      return l != null ? l.publicFileSystemContext : null;
   }

   Object getInternalFileSystemContext() {
      PolyglotLanguageContext.Lazy l = this.lazy;
      return l != null ? l.internalFileSystemContext : null;
   }

   Value getHostBindings() {
      assert this.initialized;

      if (this.hostBindings == null) {
         synchronized (this) {
            if (this.hostBindings == null) {
               Object prev = this.language.engine.enterIfNeeded(this.context, true);

               try {
                  Object scope = EngineAccessor.LANGUAGE.getScope(this.env);
                  if (scope == null) {
                     scope = new DefaultTopScope();
                  }

                  this.hostBindings = this.asValue(scope);
               } finally {
                  this.language.engine.leaveIfNeeded(prev, this.context);
               }
            }
         }
      }

      return this.hostBindings;
   }

   Object getPolyglotGuestBindings() {
      assert this.isInitialized();

      return this.lazy.polyglotGuestBindings;
   }

   boolean isInitialized() {
      return this.initialized;
   }

   CallTarget parseCached(PolyglotLanguage accessingLanguage, Source source, String[] argumentNames) throws AssertionError {
      this.ensureInitialized(accessingLanguage);
      PolyglotSourceCache cache = this.context.layer.getSourceCache();

      assert cache != null;

      return cache.parseCached(this, source, argumentNames);
   }

   TruffleLanguage.Env requireEnv() {
      TruffleLanguage.Env localEnv = this.env;
      if (localEnv == null) {
         throw CompilerDirectives.shouldNotReachHere("No language context is active on this thread.");
      } else {
         return localEnv;
      }
   }

   boolean finalizeContext(boolean cancelOrExitOperation, boolean notifyInstruments) {
      boolean performFinalize = false;
      ReentrantLock lock = this.lazy.operationLock;
      lock.lock();

      try {
         if (!this.initialized) {
            return false;
         }

         if (!this.finalized) {
            this.finalized = true;
            performFinalize = true;
         }
      } finally {
         lock.unlock();
      }

      if (performFinalize) {
         try {
            EngineAccessor.LANGUAGE.finalizeContext(this.env);
         } catch (Throwable var9) {
            if (!cancelOrExitOperation
               || !(var9 instanceof AbstractTruffleException)
                  && !(var9 instanceof PolyglotEngineImpl.CancelExecution)
                  && !(var9 instanceof PolyglotContextImpl.ExitException)) {
               throw var9;
            }

            assert this.context.state.isClosing();

            assert this.context.state.isInvalidOrClosed();

            this.context
               .engine
               .getEngineLogger()
               .log(
                  Level.FINE,
                  "Exception was thrown while finalizing a polyglot context that is being cancelled or exited. Such exceptions are expected during cancelling or exiting.",
                  var9
               );
         }

         if (this.eventsEnabled && notifyInstruments) {
            EngineAccessor.INSTRUMENT.notifyLanguageContextFinalized(this.context.engine, this.context.creatorTruffleContext, this.language.info);
         }

         return true;
      } else {
         return false;
      }
   }

   boolean exitContext(TruffleLanguage.ExitMode exitMode, int exitCode) {
      boolean performExit = false;
      ReentrantLock lock = this.lazy.operationLock;
      lock.lock();

      try {
         if (!this.initialized) {
            return false;
         }

         if (this.exited == null || exitMode.ordinal() > this.exited.ordinal()) {
            this.exited = exitMode;
            performExit = true;
         }
      } finally {
         lock.unlock();
      }

      if (performExit) {
         try {
            EngineAccessor.LANGUAGE.exitContext(this.env, exitMode, exitCode);
         } catch (Throwable var9) {
            if (exitMode == TruffleLanguage.ExitMode.NATURAL
               || !(var9 instanceof AbstractTruffleException) && !(var9 instanceof PolyglotContextImpl.ExitException)) {
               throw var9;
            }

            if (var9 instanceof AbstractTruffleException && !this.context.state.isCancelling()) {
               this.context
                  .engine
                  .getEngineLogger()
                  .log(Level.WARNING, "TruffleException thrown during exit notification! Languages are supposed to handle this kind of exceptions.", var9);
            } else {
               this.context.engine.getEngineLogger().log(Level.FINE, "Exception thrown during exit notification!", var9);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   boolean dispose() {
      try {
         TruffleLanguage.Env localEnv;
         synchronized (this.context) {
            localEnv = this.env;
            if (localEnv != null) {
               if (!this.lazy.activePolyglotThreads.isEmpty()) {
                  throw new IllegalStateException("The language did not complete all polyglot threads but should have: " + this.lazy.activePolyglotThreads);
               }

               for (PolyglotThreadInfo threadInfo : this.context.getSeenThreads().values()) {
                  assert threadInfo != PolyglotThreadInfo.NULL;

                  Thread thread = threadInfo.getThread();
                  if (thread != null) {
                     assert !threadInfo.isPolyglotThread(this.context) : "Polyglot threads must no longer be active in TruffleLanguage.finalizeContext, but polyglot thread "
                        + thread.getName()
                        + " is still active.";

                     if (!threadInfo.isCurrent() && threadInfo.isActive() && !this.context.state.isInvalidOrClosed()) {
                        throw PolyglotEngineException.illegalState("Another main thread was started while closing a polyglot context!");
                     }

                     EngineAccessor.LANGUAGE.disposeThread(localEnv, thread);
                  }
               }
            }
         }

         if (localEnv != null) {
            EngineAccessor.LANGUAGE.dispose(localEnv);
            return true;
         } else {
            return false;
         }
      } catch (Throwable var8) {
         if (!(var8 instanceof AbstractTruffleException)
            && !(var8 instanceof PolyglotEngineImpl.CancelExecution)
            && !(var8 instanceof PolyglotContextImpl.ExitException)) {
            throw var8;
         } else {
            throw new IllegalStateException("Guest language code was run during language disposal!", var8);
         }
      }
   }

   void notifyDisposed(boolean notifyInstruments) {
      if (this.eventsEnabled && notifyInstruments) {
         EngineAccessor.INSTRUMENT.notifyLanguageContextDisposed(this.context.engine, this.context.creatorTruffleContext, this.language.info);
      }
   }

   Object[] enterThread(PolyglotThread thread) {
      assert this.isInitialized();

      assert Thread.currentThread() == thread;

      synchronized (this.context) {
         Object[] prev = this.context.enterThreadChanged(true, false, true, false, true);
         this.lazy.activePolyglotThreads.add(thread);
         return prev;
      }
   }

   void leaveAndDisposePolyglotThread(Object[] prev, PolyglotThread thread) {
      assert this.isInitialized();

      synchronized (this.context) {
         this.context.leaveThreadChanged(prev, true, true, true);
         boolean removed = this.lazy.activePolyglotThreads.remove(thread);

         assert removed : "thread was not removed";
      }
   }

   boolean isCreated() {
      return this.created;
   }

   void ensureCreated(PolyglotLanguage accessingLanguage) {
      if (this.creatingThread == Thread.currentThread()) {
         throw PolyglotEngineException.illegalState(
            String.format("Cyclic access to language context for language %s. The context is currently being created.", this.language.getId())
         );
      } else {
         if (!this.created) {
            this.checkAccess(accessingLanguage);
            Map<String, Object> creatorConfig = this.context.creator == this.language ? this.context.config.creatorArguments : Collections.emptyMap();
            PolyglotContextConfig contextConfig = this.context.config;
            PolyglotSharingLayer layer = this.context.layer;
            PolyglotLanguageInstance languageInstance;
            synchronized (this.context.engine.lock) {
               if (this.language.isHost()) {
                  languageInstance = layer.allocateHostLanguage(this.language);
               } else {
                  this.context.claimSharingLayer(this.language);
                  languageInstance = layer.allocateInstance(this.context, this.language);
               }
            }

            synchronized (this.context) {
               if (!this.created) {
                  if (this.eventsEnabled) {
                     EngineAccessor.INSTRUMENT.notifyLanguageContextCreate(this.context.engine, this.context.creatorTruffleContext, this.language.info);
                  }

                  boolean wasCreated = false;

                  try {
                     TruffleLanguage.Env localEnv = EngineAccessor.LANGUAGE
                        .createEnv(
                           this,
                           languageInstance.spi,
                           contextConfig.out,
                           contextConfig.err,
                           contextConfig.in,
                           creatorConfig,
                           contextConfig.getLanguageOptionValues(this.language).copy(),
                           contextConfig.getApplicationArguments(this.language)
                        );
                     PolyglotLanguageContext.Lazy localLazy = new PolyglotLanguageContext.Lazy(languageInstance, contextConfig);
                     if (layer.isSingleContext()) {
                        languageInstance.singleLanguageContext.update(this);
                     } else {
                        languageInstance.singleLanguageContext.invalidate();
                     }

                     this.checkThreadAccess(localEnv);
                     this.creatingThread = Thread.currentThread();
                     this.env = localEnv;
                     this.lazy = localLazy;

                     assert EngineAccessor.LANGUAGE.getLanguage(this.env) != null;

                     try {
                        List<Object> languageServicesCollector = new ArrayList<>();
                        Object contextImpl = EngineAccessor.LANGUAGE.createEnvContext(localEnv, languageServicesCollector);
                        this.language.initializeContextClass(contextImpl);
                        String errorMessage = verifyServices(this.language.info, languageServicesCollector, this.language.cache.getServices());
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
                     } catch (Throwable var27) {
                        this.env = null;
                        this.lazy = null;
                        throw var27;
                     } finally {
                        this.creatingThread = null;
                     }

                     this.created = true;
                  } finally {
                     if (!wasCreated && this.eventsEnabled) {
                        EngineAccessor.INSTRUMENT
                           .notifyLanguageContextCreateFailed(this.context.engine, this.context.creatorTruffleContext, this.language.info);
                     }
                  }
               }
            }
         }
      }
   }

   void close() {
      assert Thread.holdsLock(this.context);

      this.created = false;
      this.lazy = null;
      this.env = null;
   }

   private static String verifyServices(LanguageInfo info, List<Object> registeredServices, Collection<String> expectedServices) {
      for (String expectedService : expectedServices) {
         boolean found = false;

         for (Object registeredService : registeredServices) {
            if (isSubType(registeredService.getClass(), expectedService)) {
               found = true;
               break;
            }
         }

         if (!found) {
            return String.format("Language %s declares service %s but doesn't register it", info.getName(), expectedService);
         }
      }

      return null;
   }

   private static boolean isSubType(Class<?> clazz, String serviceClass) {
      if (clazz == null) {
         return false;
      } else if (!serviceClass.equals(clazz.getName()) && !serviceClass.equals(clazz.getCanonicalName())) {
         if (isSubType(clazz.getSuperclass(), serviceClass)) {
            return true;
         } else {
            for (Class<?> implementedInterface : clazz.getInterfaces()) {
               if (isSubType(implementedInterface, serviceClass)) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return true;
      }
   }

   boolean ensureInitialized(PolyglotLanguage accessingLanguage) {
      this.ensureCreated(accessingLanguage);
      if (this.initialized) {
         return false;
      } else {
         boolean initialize = false;
         ReentrantLock lock = this.lazy.operationLock;
         lock.lock();

         try {
            initialize = !this.initialized;
            if (initialize) {
               if (this.eventsEnabled) {
                  EngineAccessor.INSTRUMENT.notifyLanguageContextInitialize(this.context.engine, this.context.creatorTruffleContext, this.language.info);
               }

               this.initialized = true;

               try {
                  EngineAccessor.LANGUAGE.initializeThread(this.env, Thread.currentThread());
                  EngineAccessor.LANGUAGE.postInitEnv(this.env);
               } catch (Throwable var14) {
                  this.initialized = false;

                  try {
                     if (this.eventsEnabled) {
                        EngineAccessor.INSTRUMENT
                           .notifyLanguageContextInitializeFailed(this.context.engine, this.context.creatorTruffleContext, this.language.info);
                     }
                  } catch (Throwable var13) {
                     var14.addSuppressed(var13);
                  }

                  throw var14;
               }

               if (this.eventsEnabled) {
                  EngineAccessor.INSTRUMENT.notifyLanguageContextInitialized(this.context.engine, this.context.creatorTruffleContext, this.language.info);
               }
            }
         } finally {
            lock.unlock();
         }

         if (initialize) {
            synchronized (this.context) {
               this.ensureMultiThreadingInitialized();

               for (PolyglotThreadInfo threadInfo : this.context.getSeenThreads().values()) {
                  Thread thread = threadInfo.getThread();
                  if (thread != Thread.currentThread()) {
                     EngineAccessor.LANGUAGE.initializeThread(this.env, thread);
                  }
               }
            }
         }

         return initialize;
      }
   }

   void ensureMultiThreadingInitialized() {
      assert Thread.holdsLock(this.context);

      PolyglotLanguageContext.Lazy l = this.lazy;

      assert l != null;

      if (!l.multipleThreadsInitialized && !this.context.isSingleThreaded()) {
         EngineAccessor.LANGUAGE.initializeMultiThreading(this.env);
         l.multipleThreadsInitialized = true;
      }
   }

   void checkAccess(PolyglotLanguage accessingLanguage) {
      this.context.checkClosedOrDisposing();
      if (!this.context.config.isAccessPermitted(accessingLanguage, this.language)) {
         throw PolyglotEngineException.illegalArgument(String.format("Access to language '%s' is not permitted. ", this.language.getId()));
      } else {
         RuntimeException initError = this.language.initError;
         if (initError != null) {
            throw PolyglotEngineException.illegalState(String.format("Initialization error: %s", initError.getMessage(), initError));
         }
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
            TruffleLanguage.Env newEnv = EngineAccessor.LANGUAGE
               .patchEnvContext(
                  this.env,
                  newConfig.out,
                  newConfig.err,
                  newConfig.in,
                  Collections.emptyMap(),
                  newOptionValues,
                  newConfig.getApplicationArguments(this.language)
               );
            if (newEnv != null) {
               this.env = newEnv;
               if (!this.language.isHost()) {
                  LOG.log(Level.FINE, "Successfully patched context of language: {0}", this.language.getId());
               }

               return true;
            } else {
               LOG.log(Level.FINE, "Failed to patch context of language: {0}", this.language.getId());
               return false;
            }
         } catch (Throwable var4) {
            LOG.log(Level.FINE, "Exception during patching context of language: {0}", this.language.getId());
            throw silenceException(RuntimeException.class, var4);
         }
      } else {
         return true;
      }
   }

   static <E extends Throwable> RuntimeException silenceException(Class<E> type, Throwable ex) throws E {
      throw ex;
   }

   <S> S lookupService(Class<S> type) {
      for (Object languageService : this.languageServices) {
         if (type.isInstance(languageService)) {
            return type.cast(languageService);
         }
      }

      return null;
   }

   @CompilerDirectives.TruffleBoundary
   Value asValue(Object guestValue) {
      assert this.lazy != null;

      assert guestValue != null;

      assert !(guestValue instanceof Value);

      assert !(guestValue instanceof Proxy);

      PolyglotValueDispatch cache = this.getLanguageInstance().lookupValueCache(this.context, guestValue);
      return this.context.engine.getAPIAccess().newValue(cache, this, guestValue);
   }

   public Object toGuestValue(Node node, Object receiver) {
      return this.context.toGuestValue(node, receiver, false);
   }

   @CompilerDirectives.TruffleBoundary
   Value[] toHostValues(Object[] values, int startIndex) {
      Value[] args = new Value[values.length - startIndex];

      for (int i = startIndex; i < values.length; i++) {
         args[i - startIndex] = this.asValue(values[i]);
      }

      return args;
   }

   @CompilerDirectives.TruffleBoundary
   Value[] toHostValues(Object[] values) {
      Value[] args = new Value[values.length];

      for (int i = 0; i < args.length; i++) {
         args[i] = this.asValue(values[i]);
      }

      return args;
   }

   @Override
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
         } catch (UnsupportedMessageException var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      }

      return this.getLanguageViewNoCheck(receiver);
   }

   private boolean validLanguageView(Object result) {
      InteropLibrary lib = InteropLibrary.getFactory().getUncached(result);
      Class<?> languageClass = EngineAccessor.LANGUAGE.getLanguage(this.env).getClass();

      try {
         assert lib.hasLanguage(result) && lib.getLanguage(result) == languageClass : String.format(
            "The returned language view of language '%s' must return the class '%s' for InteropLibrary.getLanguage.Fix the implementation of %s.getLanguageView to resolve this.",
            languageClass.getTypeName(),
            languageClass.getTypeName(),
            languageClass.getTypeName()
         );

         return true;
      } catch (UnsupportedMessageException var5) {
         throw CompilerDirectives.shouldNotReachHere(var5);
      }
   }

   private boolean validScopedView(Object result, Node location) {
      InteropLibrary lib = InteropLibrary.getFactory().getUncached(result);
      Class<?> languageClass = EngineAccessor.LANGUAGE.getLanguage(this.env).getClass();

      try {
         assert lib.hasLanguage(result) && lib.getLanguage(result) == languageClass : String.format(
            "The returned scoped view of language '%s' must return the class '%s' for InteropLibrary.getLanguage.Fix the implementation of %s.getView to resolve this.",
            languageClass.getTypeName(),
            languageClass.getTypeName(),
            location.getClass().getTypeName()
         );

         return true;
      } catch (UnsupportedMessageException var6) {
         throw CompilerDirectives.shouldNotReachHere(var6);
      }
   }

   public Object getLanguageViewNoCheck(Object receiver) {
      Object result = EngineAccessor.LANGUAGE.getLanguageView(this.env, receiver);

      assert this.validLanguageView(result);

      return result;
   }

   public Object getScopedView(Node location, Frame frame, Object value) {
      validateLocationAndFrame(this.language.info, location, frame);
      Object languageView = this.getLanguageView(value);
      Object result = NodeLibrary.getUncached().getView(location, frame, languageView);

      assert this.validScopedView(result, location);

      return result;
   }

   private static void validateLocationAndFrame(LanguageInfo viewLanguage, Node location, Frame frame) {
      RootNode rootNode = location.getRootNode();
      if (rootNode == null) {
         throw PolyglotEngineException.illegalArgument(String.format("The location '%s' does not have a RootNode.", location));
      } else {
         LanguageInfo nodeLocation = rootNode.getLanguageInfo();
         if (nodeLocation == null) {
            throw PolyglotEngineException.illegalArgument(String.format("The location '%s' does not have a language associated.", location));
         } else if (nodeLocation != viewLanguage) {
            throw PolyglotEngineException.illegalArgument(
               String.format("The view language '%s' must match the language of the location %s.", viewLanguage, nodeLocation)
            );
         } else if (!EngineAccessor.INSTRUMENT.isInstrumentable(location)) {
            throw PolyglotEngineException.illegalArgument(
               String.format("The location '%s' is not instrumentable but must be to request scoped views.", location)
            );
         } else if (!rootNode.getFrameDescriptor().equals(frame.getFrameDescriptor())) {
            throw PolyglotEngineException.illegalArgument(
               String.format(
                  "The frame provided does not originate from the location. Expected frame descriptor '%s' but was '%s'.",
                  rootNode.getFrameDescriptor(),
                  frame.getFrameDescriptor()
               )
            );
         }
      }
   }

   void patchInstance(PolyglotLanguageInstance hostInstance) {
      if (this.lazy != null) {
         this.lazy.languageInstance = hostInstance;
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
      final UncaughtExceptionHandler uncaughtExceptionHandler;
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
         this.activePolyglotThreads = new HashSet<>();
         this.polyglotGuestBindings = new PolyglotBindings(PolyglotLanguageContext.this);
         this.uncaughtExceptionHandler = PolyglotLanguageContext.this.new PolyglotUncaughtExceptionHandler();
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
         PolyglotLanguage thisLanguage = this.languageInstance.language;
         if (thisLanguage.isHost()) {
            return this.languageInstance.getEngine().idToInternalLanguageInfo;
         } else {
            boolean embedderAllAccess = config.allowedPublicLanguages.isEmpty();
            PolyglotEngineImpl engine = this.languageInstance.getEngine();
            Set<String> configuredAccess = null;
            UnmodifiableEconomicSet<String> configured = engine.getAPIAccess().getEvalAccess(config.polyglotAccess, thisLanguage.getId());
            if (configured != null) {
               configuredAccess = new HashSet<>();
               configuredAccess.addAll(Arrays.asList(configured.toArray(new String[configured.size()])));
            }

            Set<String> resolveLanguages;
            if (embedderAllAccess) {
               if (configuredAccess == null) {
                  if (internal) {
                     return engine.idToInternalLanguageInfo;
                  }

                  resolveLanguages = new HashSet<>();
                  resolveLanguages.addAll(engine.idToInternalLanguageInfo.keySet());
               } else {
                  resolveLanguages = new HashSet<>(configuredAccess);
                  resolveLanguages.add(thisLanguage.getId());
               }
            } else {
               if (configuredAccess == null) {
                  configuredAccess = config.allowedPublicLanguages;
               }

               resolveLanguages = new HashSet<>(configuredAccess);
               resolveLanguages.add(thisLanguage.getId());
            }

            Map<String, LanguageInfo> resolvedLanguages = new LinkedHashMap<>();

            for (String id : resolveLanguages) {
               PolyglotLanguage resolvedLanguage = engine.idToLanguage.get(id);
               if (resolvedLanguage != null && (internal || !resolvedLanguage.cache.isInternal())) {
                  resolvedLanguages.put(id, resolvedLanguage.info);
               }
            }

            if (internal) {
               this.addDependentLanguages(engine, resolvedLanguages, thisLanguage);
            }

            if (internal) {
               for (Entry<String, PolyglotLanguage> entry : this.languageInstance.getEngine().idToLanguage.entrySet()) {
                  if (entry.getValue().cache.isInternal()) {
                     resolvedLanguages.put(entry.getKey(), entry.getValue().info);
                  }
               }

               assert this.assertPermissionsConsistent(resolvedLanguages, this.languageInstance.language, config);
            }

            return resolvedLanguages;
         }
      }

      private boolean assertPermissionsConsistent(Map<String, LanguageInfo> resolvedLanguages, PolyglotLanguage thisLanguage, PolyglotContextConfig config) {
         for (Entry<String, PolyglotLanguage> entry : this.languageInstance.getEngine().idToLanguage.entrySet()) {
            boolean permitted = config.isAccessPermitted(thisLanguage, entry.getValue());

            assert permitted == resolvedLanguages.containsKey(entry.getKey()) : "inconsistent access permissions";
         }

         return true;
      }

      private void addDependentLanguages(PolyglotEngineImpl engine, Map<String, LanguageInfo> resolvedLanguages, PolyglotLanguage currentLanguage) {
         for (String dependentLanguage : currentLanguage.cache.getDependentLanguages()) {
            PolyglotLanguage dependent = engine.idToLanguage.get(dependentLanguage);
            if (dependent != null && !resolvedLanguages.containsKey(dependentLanguage)) {
               resolvedLanguages.put(dependentLanguage, dependent.info);
               this.addDependentLanguages(engine, resolvedLanguages, dependent);
            }
         }
      }
   }

   private class PolyglotUncaughtExceptionHandler implements UncaughtExceptionHandler {
      @Override
      public void uncaughtException(Thread t, Throwable e) {
         if (!(e instanceof ThreadDeath)) {
            TruffleLanguage.Env currentEnv = PolyglotLanguageContext.this.env;
            if (currentEnv != null) {
               try {
                  e.printStackTrace(new PrintStream(currentEnv.err()));
               } catch (Throwable var5) {
                  e.printStackTrace();
               }
            } else {
               e.printStackTrace();
            }
         }
      }
   }

   @GenerateUncached
   abstract static class ToGuestValueNode extends Node {
      abstract Object execute(PolyglotLanguageContext context, Object receiver);

      @Specialization(guards = "receiver == null")
      Object doNull(PolyglotLanguageContext context, Object receiver) {
         return context.toGuestValue(this, receiver);
      }

      @Specialization(guards = {"receiver != null", "receiver.getClass() == cachedReceiver"}, limit = "3")
      Object doCached(PolyglotLanguageContext context, Object receiver, @Cached("receiver.getClass()") Class<?> cachedReceiver) {
         return context.toGuestValue(this, cachedReceiver.cast(receiver));
      }

      @Specialization(replaces = "doCached")
      @CompilerDirectives.TruffleBoundary
      Object doUncached(PolyglotLanguageContext context, Object receiver) {
         return context.toGuestValue(this, receiver);
      }
   }

   static final class ToGuestValuesNode extends Node {
      @Node.Children
      private volatile PolyglotLanguageContext.ToGuestValueNode[] toGuestValue;
      @CompilerDirectives.CompilationFinal
      private volatile boolean needsCopy = false;
      @CompilerDirectives.CompilationFinal
      private volatile boolean generic = false;

      private ToGuestValuesNode() {
      }

      public Object[] apply(PolyglotLanguageContext context, Object[] args) {
         PolyglotLanguageContext.ToGuestValueNode[] nodes = this.toGuestValue;
         if (nodes == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            nodes = new PolyglotLanguageContext.ToGuestValueNode[args.length];

            for (int i = 0; i < nodes.length; i++) {
               nodes[i] = this.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
            }

            this.toGuestValue = nodes;
         }

         if (args.length == nodes.length) {
            return nodes.length == 0 ? args : this.fastToGuestValuesUnroll(nodes, context, args);
         } else {
            if (!this.generic || nodes.length != 1) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               nodes = Arrays.copyOf(nodes, 1);
               if (nodes[0] == null) {
                  nodes[0] = this.insert(PolyglotLanguageContextFactory.ToGuestValueNodeGen.create());
               }

               this.toGuestValue = nodes;
               this.generic = true;
            }

            return args.length == 0 ? args : this.fastToGuestValues(nodes[0], context, args);
         }
      }

      @ExplodeLoop
      private Object[] fastToGuestValuesUnroll(PolyglotLanguageContext.ToGuestValueNode[] nodes, PolyglotLanguageContext context, Object[] args) {
         Object[] newArgs = this.needsCopy ? new Object[nodes.length] : args;

         for (int i = 0; i < nodes.length; i++) {
            Object arg = args[i];
            Object newArg = nodes[i].execute(context, arg);
            if (this.needsCopy) {
               newArgs[i] = newArg;
            } else if (arg != newArg) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               newArgs = new Object[nodes.length];
               System.arraycopy(args, 0, newArgs, 0, args.length);
               newArgs[i] = newArg;
               this.needsCopy = true;
            }
         }

         return newArgs;
      }

      private Object[] fastToGuestValues(PolyglotLanguageContext.ToGuestValueNode node, PolyglotLanguageContext context, Object[] args) {
         assert this.toGuestValue[0] != null;

         Object[] newArgs = this.needsCopy ? new Object[args.length] : args;

         for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            Object newArg = node.execute(context, arg);
            if (this.needsCopy) {
               newArgs[i] = newArg;
            } else if (arg != newArg) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               newArgs = new Object[args.length];
               System.arraycopy(args, 0, newArgs, 0, args.length);
               newArgs[i] = newArg;
               this.needsCopy = true;
            }
         }

         return newArgs;
      }

      public static PolyglotLanguageContext.ToGuestValuesNode create() {
         return new PolyglotLanguageContext.ToGuestValuesNode();
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

      Value execute(PolyglotLanguageContext languageContext, Object value) {
         Class<?> cachedClassLocal = this.cachedClass;
         if (cachedClassLocal != PolyglotLanguageContext.Generic.class) {
            if (cachedClassLocal == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.cachedClass = value.getClass();
               this.cachedValue = languageContext.lazy.languageInstance.lookupValueCache(languageContext.context, value);
            } else if (value.getClass() == cachedClassLocal) {
               Object receiver = CompilerDirectives.inInterpreter() ? value : CompilerDirectives.castExact(value, cachedClassLocal);
               PolyglotValueDispatch cache = this.cachedValue;
               if (cache != null) {
                  return this.apiAccess.newValue(cache, languageContext, receiver);
               }

               CompilerDirectives.transferToInterpreterAndInvalidate();
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.cachedClass = PolyglotLanguageContext.Generic.class;
               this.cachedValue = null;
            }
         }

         return languageContext.asValue(value);
      }

      public static PolyglotLanguageContext.ToHostValueNode create(AbstractPolyglotImpl polyglot) {
         return new PolyglotLanguageContext.ToHostValueNode(polyglot);
      }
   }

   static final class ValueMigrationException extends AbstractTruffleException {
      ValueMigrationException(String message, Node location) {
         super(message, location);
      }
   }
}
