package com.oracle.truffle.api;

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

   protected abstract C createContext(TruffleLanguage.Env env);

   protected void initializeContext(C context) throws Exception {
   }

   protected void finalizeContext(C context) {
   }

   protected void exitContext(C context, TruffleLanguage.ExitMode exitMode, int exitCode) {
   }

   protected void initializeMultipleContexts() {
   }

   protected void disposeContext(C context) {
   }

   protected CallTarget parse(TruffleLanguage.ParsingRequest request) throws Exception {
      throw new UnsupportedOperationException(
         String.format("Override parse method of %s, it will be made abstract in future version of Truffle API!", this.getClass().getName())
      );
   }

   protected ExecutableNode parse(TruffleLanguage.InlineParsingRequest request) throws Exception {
      return null;
   }

   protected OptionDescriptors getOptionDescriptors() {
      return OptionDescriptors.EMPTY;
   }

   protected boolean patchContext(C context, TruffleLanguage.Env newEnv) {
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

   protected boolean isVisible(C context, Object value) {
      return true;
   }

   protected Object getLanguageView(C context, Object value) {
      return null;
   }

   CallTarget parse(Source source, String... argumentNames) {
      TruffleLanguage.ParsingRequest request = new TruffleLanguage.ParsingRequest(source, argumentNames);

      CallTarget target;
      try {
         target = request.parse(this);
      } catch (RuntimeException var10) {
         throw var10;
      } catch (Exception var11) {
         throw new RuntimeException(var11);
      } finally {
         request.dispose();
      }

      return target;
   }

   ExecutableNode parseInline(Source source, Node context, MaterializedFrame frame) {
      assert context != null;

      TruffleLanguage.InlineParsingRequest request = new TruffleLanguage.InlineParsingRequest(source, context, frame);

      ExecutableNode snippet;
      try {
         snippet = request.parse(this);
      } catch (RuntimeException var11) {
         throw var11;
      } catch (Exception var12) {
         throw new RuntimeException(var12);
      } finally {
         request.dispose();
      }

      return snippet;
   }

   @Deprecated(since = "21.3")
   protected static <T extends TruffleLanguage<?>> T getCurrentLanguage(Class<T> languageClass) {
      try {
         return LanguageAccessor.engineAccess().getCurrentLanguage(languageClass);
      } catch (Throwable var2) {
         CompilerDirectives.transferToInterpreter();
         throw TruffleLanguage.Env.engineToLanguageException(var2);
      }
   }

   @Deprecated(since = "21.3")
   protected static <C, T extends TruffleLanguage<C>> C getCurrentContext(Class<T> languageClass) {
      try {
         return LanguageAccessor.ENGINE.getCurrentContext(languageClass);
      } catch (Throwable var2) {
         CompilerDirectives.transferToInterpreter();
         throw TruffleLanguage.Env.engineToLanguageException(var2);
      }
   }

   protected final <T> ContextLocal<T> createContextLocal(TruffleLanguage.ContextLocalFactory<C, T> factory) {
      ContextLocal<T> local = LanguageAccessor.ENGINE.createLanguageContextLocal(factory);
      if (this.contextLocals == null) {
         this.contextLocals = new ArrayList<>();
      }

      try {
         this.contextLocals.add(local);
         return local;
      } catch (UnsupportedOperationException var4) {
         throw new IllegalStateException(
            "The set of context locals is frozen. Context locals can only be created during construction of the TruffleLanguage subclass."
         );
      }
   }

   protected final <T> ContextThreadLocal<T> createContextThreadLocal(TruffleLanguage.ContextThreadLocalFactory<C, T> factory) {
      ContextThreadLocal<T> local = LanguageAccessor.ENGINE.createLanguageContextThreadLocal(factory);
      if (this.contextThreadLocals == null) {
         this.contextThreadLocals = new ArrayList<>();
      }

      try {
         this.contextThreadLocals.add(local);
         return local;
      } catch (UnsupportedOperationException var4) {
         throw new IllegalStateException(
            "The set of context thread locals is frozen. Context thread locals can only be created during construction of the TruffleLanguage subclass."
         );
      }
   }

   protected final String getLanguageHome() {
      try {
         return LanguageAccessor.ENGINE.getLanguageHome(this.languageInfo);
      } catch (Throwable var2) {
         throw TruffleLanguage.Env.engineToLanguageException(var2);
      }
   }

   protected final int getAsynchronousStackDepth() {
      assert this.polyglotLanguageInstance != null : "getAsynchronousStackDepth not supported for host language";

      return LanguageAccessor.engineAccess().getAsynchronousStackDepth(this.polyglotLanguageInstance);
   }

   @FunctionalInterface
   protected interface ContextLocalFactory<C, T> {
      T create(C context);
   }

   public static enum ContextPolicy {
      EXCLUSIVE,
      REUSE,
      SHARED;
   }

   public abstract static class ContextReference<C> {
      protected ContextReference() {
      }

      public abstract C get(Node node);

      public static <T extends TruffleLanguage<C>, C> TruffleLanguage.ContextReference<C> create(Class<T> languageClass) {
         Objects.requireNonNull(languageClass);
         return LanguageAccessor.ENGINE.createContextReference(languageClass);
      }
   }

   @FunctionalInterface
   protected interface ContextThreadLocalFactory<C, T> {
      T create(C context, Thread thread);
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

      Env(
         Object polyglotLanguageContext,
         TruffleLanguage<?> language,
         OutputStream out,
         OutputStream err,
         InputStream in,
         Map<String, Object> config,
         OptionValues options,
         String[] applicationArguments
      ) {
         this.polyglotLanguageContext = polyglotLanguageContext;
         this.spi = (TruffleLanguage<Object>)language;
         this.in = in;
         this.err = err;
         this.out = out;
         this.config = config;
         this.options = options;
         this.applicationArguments = applicationArguments == null ? new String[0] : applicationArguments;
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
         } else if (!this.valid) {
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
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
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
            return LanguageAccessor.engineAccess()
               .createThread(this.polyglotLanguageContext, runnable, context != null ? context.polyglotContext : null, group, stackSize);
         } catch (Throwable var7) {
            throw engineToLanguageException(var7);
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
         } catch (Throwable var4) {
            throw engineToLanguageException(var4);
         }
      }

      @Deprecated
      public TruffleContext.Builder newContextBuilder() {
         return this.newInnerContextBuilder().initializeCreatorContext(true).inheritAllAccess(true);
      }

      public TruffleContext.Builder newInnerContextBuilder(String... permittedLanguages) {
         return TruffleContext.EMPTY.new Builder(this).permittedLanguages(permittedLanguages);
      }

      @CompilerDirectives.TruffleBoundary
      public Object getPolyglotBindings() {
         try {
            if (!this.isPolyglotBindingsAccessAllowed()) {
               throw new SecurityException(
                  "Polyglot bindings are not accessible for this language. Use --polyglot or allowPolyglotAccess when building the context."
               );
            } else {
               return LanguageAccessor.engineAccess().getPolyglotBindingsForLanguage(this.polyglotLanguageContext);
            }
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public Object importSymbol(String symbolName) {
         try {
            if (!this.isPolyglotBindingsAccessAllowed()) {
               throw new SecurityException(
                  "Polyglot bindings are not accessible for this language. Use --polyglot or allowPolyglotAccess when building the context."
               );
            } else {
               return LanguageAccessor.engineAccess().importSymbol(this.polyglotLanguageContext, this, symbolName);
            }
         } catch (Throwable var3) {
            throw engineToLanguageException(var3);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public void exportSymbol(String symbolName, Object value) {
         try {
            if (!this.isPolyglotBindingsAccessAllowed()) {
               throw new SecurityException(
                  "Polyglot bindings are not accessible for this language. Use --polyglot or allowPolyglotAccess when building the context."
               );
            } else {
               LanguageAccessor.engineAccess().exportSymbol(this.polyglotLanguageContext, symbolName, value);
            }
         } catch (Throwable var4) {
            throw engineToLanguageException(var4);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public boolean isHostLookupAllowed() {
         try {
            return LanguageAccessor.engineAccess().isHostAccessAllowed(this.polyglotLanguageContext, this);
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public void addToHostClassPath(TruffleFile entry) {
         try {
            Objects.requireNonNull(entry);
            LanguageAccessor.engineAccess().addToHostClassPath(this.polyglotLanguageContext, entry);
         } catch (Throwable var3) {
            throw engineToLanguageException(var3);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public Object lookupHostSymbol(String symbolName) {
         try {
            return LanguageAccessor.engineAccess().lookupHostSymbol(this.polyglotLanguageContext, this, symbolName);
         } catch (Throwable var3) {
            throw engineToLanguageException(var3);
         }
      }

      public boolean isHostObject(Object value) {
         try {
            return LanguageAccessor.engineAccess().isHostObject(this.polyglotLanguageContext, value);
         } catch (Throwable var3) {
            throw engineToLanguageException(var3);
         }
      }

      public Object asHostObject(Object value) {
         if (!this.isHostObject(value)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new ClassCastException();
         } else {
            try {
               return LanguageAccessor.engineAccess().asHostObject(this.polyglotLanguageContext, value);
            } catch (Throwable var3) {
               throw engineToLanguageException(var3);
            }
         }
      }

      public Object asGuestValue(Object hostObject) {
         try {
            return LanguageAccessor.engineAccess().toGuestValue(null, hostObject, this.polyglotLanguageContext);
         } catch (Throwable var3) {
            throw engineToLanguageException(var3);
         }
      }

      public Object asBoxedGuestValue(Object guestObject) {
         try {
            return LanguageAccessor.engineAccess().asBoxedGuestValue(guestObject, this.polyglotLanguageContext);
         } catch (Throwable var3) {
            throw engineToLanguageException(var3);
         }
      }

      public boolean isHostFunction(Object value) {
         try {
            return LanguageAccessor.engineAccess().isHostFunction(this.polyglotLanguageContext, value);
         } catch (Throwable var3) {
            throw engineToLanguageException(var3);
         }
      }

      public Object findMetaObject(Object value) {
         try {
            return LanguageAccessor.engineAccess().findMetaObjectForLanguage(this.polyglotLanguageContext, value);
         } catch (Throwable var3) {
            throw engineToLanguageException(var3);
         }
      }

      public boolean isHostException(Throwable exception) {
         try {
            return LanguageAccessor.engineAccess().isHostException(this.polyglotLanguageContext, exception);
         } catch (Throwable var3) {
            throw engineToLanguageException(var3);
         }
      }

      public Throwable asHostException(Throwable exception) {
         try {
            return LanguageAccessor.engineAccess().asHostException(this.polyglotLanguageContext, exception);
         } catch (Throwable var3) {
            throw engineToLanguageException(var3);
         }
      }

      public boolean isHostSymbol(Object guestObject) {
         try {
            return LanguageAccessor.engineAccess().isHostSymbol(this.polyglotLanguageContext, guestObject);
         } catch (Throwable var3) {
            throw engineToLanguageException(var3);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public Object asHostSymbol(Class<?> symbolClass) {
         try {
            return LanguageAccessor.engineAccess().asHostSymbol(this.polyglotLanguageContext, symbolClass);
         } catch (Throwable var3) {
            throw engineToLanguageException(var3);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public boolean isInnerContextOptionsAllowed() {
         try {
            return LanguageAccessor.engineAccess().isInnerContextOptionsAllowed(this.polyglotLanguageContext, this);
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
         }
      }

      public boolean isIOAllowed() {
         try {
            return LanguageAccessor.engineAccess().isIOAllowed(this.polyglotLanguageContext, this);
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public boolean isNativeAccessAllowed() {
         try {
            return LanguageAccessor.engineAccess().isNativeAccessAllowed(this.polyglotLanguageContext, this);
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public boolean isPolyglotEvalAllowed() {
         try {
            return LanguageAccessor.engineAccess().isPolyglotEvalAllowed(this.polyglotLanguageContext);
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public boolean isPolyglotBindingsAccessAllowed() {
         try {
            return LanguageAccessor.engineAccess().isPolyglotBindingsAccessAllowed(this.polyglotLanguageContext);
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public boolean isMimeTypeSupported(String mimeType) {
         this.checkDisposed();

         try {
            return LanguageAccessor.engineAccess().isMimeTypeSupported(this.polyglotLanguageContext, mimeType);
         } catch (Throwable var3) {
            throw engineToLanguageException(var3);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public CallTarget parseInternal(Source source, String... argumentNames) {
         CompilerAsserts.neverPartOfCompilation();
         this.checkDisposed();

         try {
            return LanguageAccessor.engineAccess().parseForLanguage(this.polyglotLanguageContext, source, argumentNames, true);
         } catch (Throwable var4) {
            throw engineToLanguageException(var4);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public CallTarget parsePublic(Source source, String... argumentNames) {
         CompilerAsserts.neverPartOfCompilation();
         this.checkDisposed();

         try {
            return LanguageAccessor.engineAccess().parseForLanguage(this.polyglotLanguageContext, source, argumentNames, false);
         } catch (Throwable var4) {
            throw engineToLanguageException(var4);
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
            if (type.isInstance(obj)) {
               return type.cast(obj);
            }
         }

         return null;
      }

      @CompilerDirectives.TruffleBoundary
      public <S> S lookup(InstrumentInfo instrument, Class<S> type) {
         if (this.isPreInitialization()) {
            throw new IllegalStateException("Instrument lookup is not allowed during context pre-initialization.");
         } else {
            try {
               return LanguageAccessor.engineAccess().lookup(instrument, type);
            } catch (Throwable var4) {
               throw engineToLanguageException(var4);
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      public <S> S lookup(LanguageInfo language, Class<S> type) {
         if (this.getSpi().languageInfo == language) {
            throw new IllegalArgumentException("Cannot request services from the current language.");
         } else {
            try {
               Objects.requireNonNull(language);
               return LanguageAccessor.engineAccess().lookupService(this.polyglotLanguageContext, language, this.getSpi().languageInfo, type);
            } catch (Throwable var4) {
               throw engineToLanguageException(var4);
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      public boolean initializeLanguage(LanguageInfo targetLanguage) {
         Objects.requireNonNull(targetLanguage, "TargetLanguage must be non null.");

         try {
            return LanguageAccessor.engineAccess().initializeLanguage(this.polyglotLanguageContext, targetLanguage);
         } catch (Throwable var3) {
            throw engineToLanguageException(var3);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public Map<String, LanguageInfo> getInternalLanguages() {
         try {
            return LanguageAccessor.engineAccess().getInternalLanguages(this.polyglotLanguageContext);
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public Map<String, LanguageInfo> getPublicLanguages() {
         try {
            return LanguageAccessor.engineAccess().getPublicLanguages(this.polyglotLanguageContext);
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public Map<String, InstrumentInfo> getInstruments() {
         try {
            return LanguageAccessor.engineAccess().getInstruments(this.polyglotLanguageContext);
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
         }
      }

      public ZoneId getTimeZone() {
         this.checkDisposed();

         try {
            return LanguageAccessor.engineAccess().getTimeZone(this.polyglotLanguageContext);
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
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
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public boolean isPreInitialization() {
         try {
            return LanguageAccessor.engineAccess().inContextPreInitialization(this.polyglotLanguageContext);
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleFile getPublicTruffleFile(String path) {
         this.checkDisposed();
         TruffleFile.FileSystemContext fs = this.getPublicFileSystemContext();

         try {
            return new TruffleFile(fs, fs.fileSystem.parsePath(path));
         } catch (UnsupportedOperationException var4) {
            throw var4;
         } catch (Throwable var5) {
            throw TruffleFile.wrapHostException(var5, fs.fileSystem);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleFile getPublicTruffleFile(URI uri) {
         this.checkDisposed();
         TruffleFile.FileSystemContext fs = this.getPublicFileSystemContext();

         try {
            return new TruffleFile(fs, fs.fileSystem.parsePath(uri));
         } catch (UnsupportedOperationException var4) {
            throw var4;
         } catch (Throwable var5) {
            throw TruffleFile.wrapHostException(var5, fs.fileSystem);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleFile getInternalTruffleFile(String path) {
         this.checkDisposed();
         TruffleFile.FileSystemContext fs = this.getInternalFileSystemContext();

         try {
            return new TruffleFile(fs, fs.fileSystem.parsePath(path));
         } catch (UnsupportedOperationException var4) {
            throw var4;
         } catch (Throwable var5) {
            throw TruffleFile.wrapHostException(var5, fs.fileSystem);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleFile getInternalTruffleFile(URI uri) {
         this.checkDisposed();
         TruffleFile.FileSystemContext fs = this.getInternalFileSystemContext();

         try {
            return new TruffleFile(fs, fs.fileSystem.parsePath(uri));
         } catch (UnsupportedOperationException var4) {
            throw var4;
         } catch (Throwable var5) {
            throw TruffleFile.wrapHostException(var5, fs.fileSystem);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleFile getTruffleFileInternal(String path, Predicate<TruffleFile> filter) {
         return this.getTruffleFileInternalImpl(path, filter, TruffleLanguage.Env.TruffleFileFactory.PATH);
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleFile getTruffleFileInternal(URI uri, Predicate<TruffleFile> filter) {
         return this.getTruffleFileInternalImpl(uri, filter, TruffleLanguage.Env.TruffleFileFactory.URI);
      }

      private <P> TruffleFile getTruffleFileInternalImpl(
         P path, Predicate<TruffleFile> isStdLibFile, TruffleLanguage.Env.TruffleFileFactory<P> truffleFileFactory
      ) {
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
         } else if (!currentWorkingDirectory.isDirectory()) {
            throw new IllegalArgumentException("Current working directory must be directory.");
         } else {
            TruffleFile.FileSystemContext fileSystemContext = this.getPublicFileSystemContext();
            TruffleFile.FileSystemContext internalFileSystemContext = this.getInternalFileSystemContext();

            try {
               fileSystemContext.fileSystem.setCurrentWorkingDirectory(currentWorkingDirectory.getSPIPath());
               if (fileSystemContext.fileSystem != internalFileSystemContext.fileSystem) {
                  internalFileSystemContext.fileSystem.setCurrentWorkingDirectory(currentWorkingDirectory.getSPIPath());
               }
            } catch (IllegalArgumentException | SecurityException | UnsupportedOperationException var5) {
               throw var5;
            } catch (Throwable var6) {
               throw TruffleFile.wrapHostException(var6, fileSystemContext.fileSystem);
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      public String getFileNameSeparator() {
         this.checkDisposed();
         TruffleFile.FileSystemContext fs = this.getPublicFileSystemContext();

         try {
            return fs.fileSystem.getSeparator();
         } catch (Throwable var3) {
            throw TruffleFile.wrapHostException(var3, fs.fileSystem);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public String getPathSeparator() {
         this.checkDisposed();
         TruffleFile.FileSystemContext fs = this.getPublicFileSystemContext();

         try {
            return fs.fileSystem.getPathSeparator();
         } catch (Throwable var3) {
            throw TruffleFile.wrapHostException(var3, fs.fileSystem);
         }
      }

      public void registerService(Object service) {
         if (this.languageServicesCollector == null) {
            throw new IllegalStateException("The registerService method can only be called during the execution of the Env.createContext method.");
         } else {
            this.languageServicesCollector.add(service);
         }
      }

      public boolean isCreateProcessAllowed() {
         try {
            return LanguageAccessor.engineAccess().isCreateProcessAllowed(this.polyglotLanguageContext);
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleProcessBuilder newProcessBuilder(String... command) {
         if (!this.isCreateProcessAllowed()) {
            throw new SecurityException("Process creation is not allowed, to enable it set Context.Builder.allowCreateProcess(true).");
         } else {
            TruffleFile.FileSystemContext fs = this.getPublicFileSystemContext();
            List<String> cmd = new ArrayList<>(command.length);
            Collections.addAll(cmd, command);
            return LanguageAccessor.ioAccess().createProcessBuilder(this.polyglotLanguageContext, fs.fileSystem, cmd);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public Map<String, String> getEnvironment() {
         try {
            return LanguageAccessor.engineAccess().getProcessEnvironment(this.polyglotLanguageContext);
         } catch (Throwable var2) {
            throw engineToLanguageException(var2);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleFile createTempFile(TruffleFile dir, String prefix, String suffix, FileAttribute<?>... attrs) throws IOException {
         TruffleFile.FileSystemContext fs = this.getPublicFileSystemContext();

         try {
            TruffleFile useDir = dir == null ? new TruffleFile(fs, fs.fileSystem.getTempDirectory()) : dir;
            return TruffleFile.createTempFile(useDir, prefix, suffix, false, attrs);
         } catch (IllegalArgumentException | IOException | SecurityException | UnsupportedOperationException var7) {
            throw var7;
         } catch (Throwable var8) {
            throw TruffleFile.wrapHostException(var8, fs.fileSystem);
         }
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleFile createTempDirectory(TruffleFile dir, String prefix, FileAttribute<?>... attrs) throws IOException {
         TruffleFile.FileSystemContext fs = this.getPublicFileSystemContext();

         try {
            TruffleFile useDir = dir == null ? new TruffleFile(fs, fs.fileSystem.getTempDirectory()) : dir;
            return TruffleFile.createTempFile(useDir, prefix, null, true, attrs);
         } catch (IllegalArgumentException | IOException | SecurityException | UnsupportedOperationException var6) {
            throw var6;
         } catch (Throwable var7) {
            throw TruffleFile.wrapHostException(var7, fs.fileSystem);
         }
      }

      @Deprecated(since = "22.1")
      @CompilerDirectives.TruffleBoundary
      public Object createHostAdapterClass(Class<?>[] types) {
         Objects.requireNonNull(types, "types");
         return this.createHostAdapterClassLegacyImpl(types, null);
      }

      @Deprecated(since = "22.1")
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

      public Future<Void> submitThreadLocal(Thread[] threads, ThreadLocalAction action) {
         return this.submitThreadLocalInternal(threads, action, true);
      }

      public void registerOnDispose(Closeable closeable) {
         LanguageAccessor.engineAccess().registerOnDispose(this.polyglotLanguageContext, closeable);
      }

      Future<Void> submitThreadLocalInternal(Thread[] threads, ThreadLocalAction action, boolean needsEnter) {
         this.checkDisposed();

         try {
            return LanguageAccessor.ENGINE
               .submitThreadLocal(LanguageAccessor.ENGINE.getContext(this.polyglotLanguageContext), this.polyglotLanguageContext, threads, action, needsEnter);
         } catch (Throwable var5) {
            throw engineToLanguageException(var5);
         }
      }

      private Object createHostAdapterClassLegacyImpl(Class<?>[] types, Object classOverrides) {
         this.checkDisposed();
         Object[] hostTypes = new Object[types.length];

         for (int i = 0; i < types.length; i++) {
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
            } else {
               return LanguageAccessor.engineAccess().createHostAdapterClass(this.polyglotLanguageContext, types, classOverrides);
            }
         } catch (Throwable var4) {
            throw engineToLanguageException(var4);
         }
      }

      @CompilerDirectives.TruffleBoundary
      <E extends TruffleLanguage> E getLanguage(Class<E> languageClass) {
         this.checkDisposed();
         if (languageClass != this.getSpi().getClass()) {
            throw new IllegalArgumentException("Invalid access to language " + languageClass + ".");
         } else {
            return languageClass.cast(this.getSpi());
         }
      }

      void dispose() {
         Object c = this.getLanguageContext();
         if (c != UNSET_CONTEXT) {
            this.getSpi().disposeContext(c);
         } else {
            throw new IllegalStateException("Disposing while context has not been set yet.");
         }
      }

      @CompilerDirectives.TruffleBoundary
      void postInit() {
         try {
            this.getSpi().initializeContext(this.context);
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw new RuntimeException(var8);
         } finally {
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
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.initialized;
            }
         } else {
            return this.initialized;
         }
      }

      boolean isVisible(Object value) {
         Object c = this.getLanguageContext();
         return c != UNSET_CONTEXT ? this.getSpi().isVisible(c, value) : false;
      }

      Object getLanguageContext() {
         if (CompilerDirectives.isPartialEvaluationConstant(this)) {
            Object languageContext = this.context;
            if (this.contextUnchangedAssumption.isValid()) {
               return languageContext;
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.context;
            }
         } else {
            return this.context;
         }
      }

      @CompilerDirectives.TruffleBoundary
      static <T extends RuntimeException> RuntimeException engineToLanguageException(Throwable t) {
         return LanguageAccessor.engineAccess().engineToLanguageException(t);
      }

      private abstract static class TruffleFileFactory<P> implements BiFunction<P, TruffleFile.FileSystemContext, TruffleFile> {
         static final TruffleLanguage.Env.TruffleFileFactory<String> PATH = new TruffleLanguage.Env.TruffleFileFactory<String>() {
            Path parsePath(String path, TruffleFile.FileSystemContext fileSystemContext) {
               return fileSystemContext.fileSystem.parsePath(path);
            }
         };
         static final TruffleLanguage.Env.TruffleFileFactory<URI> URI = new TruffleLanguage.Env.TruffleFileFactory<URI>() {
            public Path parsePath(URI uri, TruffleFile.FileSystemContext fileSystemContext) {
               return fileSystemContext.fileSystem.parsePath(uri);
            }
         };

         public final TruffleFile apply(P p, TruffleFile.FileSystemContext fileSystemContext) {
            try {
               return new TruffleFile(fileSystemContext, this.parsePath(p, fileSystemContext));
            } catch (UnsupportedOperationException var4) {
               throw var4;
            } catch (Throwable var5) {
               throw TruffleFile.wrapHostException(var5, fileSystemContext.fileSystem);
            }
         }

         abstract Path parsePath(P p, TruffleFile.FileSystemContext fileSystemContext);
      }
   }

   public static enum ExitMode {
      NATURAL,
      HARD;
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
         } else {
            return this.source;
         }
      }

      public Node getLocation() {
         if (this.disposed) {
            throw new IllegalStateException();
         } else {
            return this.node;
         }
      }

      public MaterializedFrame getFrame() {
         if (this.disposed) {
            throw new IllegalStateException();
         } else {
            return this.frame;
         }
      }

      void dispose() {
         this.disposed = true;
      }

      ExecutableNode parse(TruffleLanguage<?> truffleLanguage) throws Exception {
         return truffleLanguage.parse(this);
      }
   }

   public abstract static class LanguageReference<L extends TruffleLanguage> {
      protected LanguageReference() {
      }

      public abstract L get(Node node);

      public static <T extends TruffleLanguage<?>> TruffleLanguage.LanguageReference<T> create(Class<T> languageClass) {
         Objects.requireNonNull(languageClass);
         return LanguageAccessor.ENGINE.createLanguageReference(languageClass);
      }
   }

   public static final class ParsingRequest {
      private final Source source;
      private final String[] argumentNames;
      private boolean disposed;

      ParsingRequest(Source source, String... argumentNames) {
         Objects.requireNonNull(source);
         this.source = source;
         this.argumentNames = argumentNames;
      }

      public Source getSource() {
         if (this.disposed) {
            throw new IllegalStateException();
         } else {
            return this.source;
         }
      }

      public List<String> getArgumentNames() {
         if (this.disposed) {
            throw new IllegalStateException();
         } else {
            return this.argumentNames == null ? Collections.emptyList() : ReadOnlyArrayList.asList(this.argumentNames, 0, this.argumentNames.length);
         }
      }

      void dispose() {
         this.disposed = true;
      }

      CallTarget parse(TruffleLanguage<?> truffleLanguage) throws Exception {
         return truffleLanguage.parse(this);
      }
   }

   public interface Provider {
      String getLanguageClassName();

      TruffleLanguage<?> create();

      List<TruffleFile.FileTypeDetector> createFileTypeDetectors();

      Collection<String> getServicesClassNames();
   }

   @Retention(RetentionPolicy.RUNTIME)
   @Target(ElementType.TYPE)
   public @interface Registration {
      String id() default "";

      String name() default "";

      String implementationName() default "";

      String version() default "inherit";

      String defaultMimeType() default "";

      String[] characterMimeTypes() default {};

      String[] byteMimeTypes() default {};

      boolean interactive() default true;

      boolean internal() default false;

      String[] dependentLanguages() default {};

      TruffleLanguage.ContextPolicy contextPolicy() default TruffleLanguage.ContextPolicy.EXCLUSIVE;

      Class<?>[] services() default {};

      Class<? extends TruffleFile.FileTypeDetector>[] fileTypeDetectors() default {};

      boolean needsAllEncodings() default false;

      String website() default "";
   }
}
