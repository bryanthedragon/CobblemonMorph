package com.oracle.truffle.api;

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
import java.util.Map.Entry;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class TruffleContext implements AutoCloseable {
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
   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof TruffleContext)) {
         return false;
      } else {
         TruffleContext c = (TruffleContext)obj;
         return this.polyglotContext.equals(c.polyglotContext);
      }
   }

   @Override
   public int hashCode() {
      return this.polyglotContext.hashCode();
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleContext getParent() {
      try {
         return LanguageAccessor.engineAccess().getParentContext(this.polyglotContext);
      } catch (Throwable var2) {
         throw TruffleLanguage.Env.engineToLanguageException(var2);
      }
   }

   public Object enter(Node node) {
      try {
         CompilerAsserts.partialEvaluationConstant(node);
         Object prev = LanguageAccessor.engineAccess().enterInternalContext(node, this.polyglotContext);
         if (CONTEXT_ASSERT_STACK != null) {
            verifyEnter(prev);
         }

         return prev;
      } catch (Throwable var3) {
         throw TruffleLanguage.Env.engineToLanguageException(var3);
      }
   }

   public boolean initializeInternal(Node node, String languageId) {
      Objects.requireNonNull(languageId);
      CompilerAsserts.partialEvaluationConstant(node);

      try {
         return LanguageAccessor.engineAccess().initializeInnerContext(node, this.polyglotContext, languageId, true);
      } catch (Throwable var4) {
         throw TruffleLanguage.Env.engineToLanguageException(var4);
      }
   }

   public boolean initializePublic(Node node, String languageId) {
      Objects.requireNonNull(languageId);
      CompilerAsserts.partialEvaluationConstant(node);

      try {
         return LanguageAccessor.engineAccess().initializeInnerContext(node, this.polyglotContext, languageId, false);
      } catch (Throwable var4) {
         throw TruffleLanguage.Env.engineToLanguageException(var4);
      }
   }

   public Object evalInternal(Node node, Source source) {
      CompilerAsserts.partialEvaluationConstant(node);

      try {
         return LanguageAccessor.engineAccess().evalInternalContext(node, this.polyglotContext, source, true);
      } catch (Throwable var4) {
         throw TruffleLanguage.Env.engineToLanguageException(var4);
      }
   }

   public Object evalPublic(Node node, Source source) {
      CompilerAsserts.partialEvaluationConstant(node);

      try {
         return LanguageAccessor.engineAccess().evalInternalContext(node, this.polyglotContext, source, false);
      } catch (Throwable var4) {
         throw TruffleLanguage.Env.engineToLanguageException(var4);
      }
   }

   public boolean isEntered() {
      try {
         return LanguageAccessor.engineAccess().isContextEntered(this.polyglotContext);
      } catch (Throwable var2) {
         throw TruffleLanguage.Env.engineToLanguageException(var2);
      }
   }

   public boolean isActive() {
      try {
         return LanguageAccessor.engineAccess().isContextActive(this.polyglotContext);
      } catch (Throwable var2) {
         throw TruffleLanguage.Env.engineToLanguageException(var2);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean isClosed() {
      try {
         return LanguageAccessor.engineAccess().isContextClosed(this.polyglotContext);
      } catch (Throwable var2) {
         throw TruffleLanguage.Env.engineToLanguageException(var2);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean isCancelling() {
      try {
         return LanguageAccessor.engineAccess().isContextCancelling(this.polyglotContext);
      } catch (Throwable var2) {
         throw TruffleLanguage.Env.engineToLanguageException(var2);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean isExiting() {
      try {
         return LanguageAccessor.engineAccess().isContextExiting(this.polyglotContext);
      } catch (Throwable var2) {
         throw TruffleLanguage.Env.engineToLanguageException(var2);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public Future<Void> pause() {
      try {
         return LanguageAccessor.engineAccess().pause(this.polyglotContext);
      } catch (Throwable var2) {
         throw TruffleLanguage.Env.engineToLanguageException(var2);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void resume(Future<Void> pauseFuture) {
      try {
         LanguageAccessor.engineAccess().resume(this.polyglotContext, pauseFuture);
      } catch (Throwable var3) {
         throw TruffleLanguage.Env.engineToLanguageException(var3);
      }
   }

   public void leave(Node node, Object prev) {
      try {
         if (CONTEXT_ASSERT_STACK != null) {
            verifyLeave(prev);
         }

         LanguageAccessor.engineAccess().leaveInternalContext(node, this.polyglotContext, prev);
      } catch (Throwable var4) {
         throw TruffleLanguage.Env.engineToLanguageException(var4);
      }
   }

   public <T> T leaveAndEnter(Node node, Supplier<T> runWhileOutsideContext) {
      CompilerAsserts.partialEvaluationConstant(node);

      try {
         LanguageAccessor.engineAccess().leaveInternalContext(node, this.polyglotContext, null);

         Object t;
         try {
            t = callSupplier(runWhileOutsideContext);
         } finally {
            LanguageAccessor.engineAccess().enterInternalContext(node, this.polyglotContext);
         }

         return (T)t;
      } catch (Throwable var8) {
         throw TruffleLanguage.Env.engineToLanguageException(var8);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static <T> T callSupplier(Supplier<T> supplier) {
      return supplier.get();
   }

   @CompilerDirectives.TruffleBoundary
   private static void verifyEnter(Object prev) {
      assert CONTEXT_ASSERT_STACK != null;

      CONTEXT_ASSERT_STACK.get().add(prev);
   }

   @CompilerDirectives.TruffleBoundary
   private static void verifyLeave(Object prev) {
      assert CONTEXT_ASSERT_STACK != null;

      List<Object> list = CONTEXT_ASSERT_STACK.get();

      assert !list.isEmpty() : "Assert stack is empty.";

      Object expectedPrev = list.get(list.size() - 1);

      assert prev == expectedPrev : "Invalid prev argument provided in TruffleContext.leave(Object).";

      list.remove(list.size() - 1);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void close() {
      if (!this.creator) {
         throw new UnsupportedOperationException(
            "This context instance has no permission to close. Only the original creator of the truffle context or instruments can close."
         );
      } else {
         try {
            LanguageAccessor.engineAccess().closeContext(this.polyglotContext, false, null, false, null);
         } catch (Throwable var2) {
            throw TruffleLanguage.Env.engineToLanguageException(var2);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void closeCancelled(Node closeLocation, String message) {
      if (!this.creator) {
         throw new UnsupportedOperationException(
            "This context instance has no permission to close. Only the original creator of the truffle context or instruments can close."
         );
      } else {
         try {
            LanguageAccessor.engineAccess().closeContext(this.polyglotContext, true, closeLocation, false, message);
         } catch (Throwable var4) {
            throw TruffleLanguage.Env.engineToLanguageException(var4);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void closeExited(Node exitLocation, int exitCode) {
      if (!this.isEntered()) {
         throw new IllegalStateException("Exit cannot be initiated for this context because it is not currently entered.");
      } else {
         try {
            LanguageAccessor.engineAccess().exitContext(this.polyglotContext, exitLocation, exitCode);
         } catch (Throwable var4) {
            throw TruffleLanguage.Env.engineToLanguageException(var4);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   public void closeResourceExhausted(Node location, String message) {
      if (!this.creator) {
         throw new UnsupportedOperationException(
            "This context instance has no permission to cancel. Only the original creator of the truffle context or instruments can close."
         );
      } else {
         try {
            LanguageAccessor.engineAccess().closeContext(this.polyglotContext, true, location, true, message);
         } catch (Throwable var4) {
            throw TruffleLanguage.Env.engineToLanguageException(var4);
         }
      }
   }

   static {
      boolean assertions = false;
      if (!$assertionsDisabled) {
         assertions = true;
         if (false) {
            throw new AssertionError();
         }
      }

      CONTEXT_ASSERT_STACK = assertions ? new ThreadLocal<List<Object>>() {
         protected List<Object> initialValue() {
            return new ArrayList<>();
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

      TruffleContext.Builder permittedLanguages(String... permittedLanguages) {
         this.permittedLanguages = permittedLanguages;
         return this;
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleContext.Builder config(String key, Object value) {
         if (this.config == null) {
            this.config = new HashMap<>();
         }

         this.config.put(key, value);
         return this;
      }

      public TruffleContext.Builder initializeCreatorContext(boolean enabled) {
         this.initializeCreatorContext = enabled;
         return this;
      }

      public TruffleContext.Builder out(OutputStream out) {
         this.out = out;
         return this;
      }

      public TruffleContext.Builder err(OutputStream err) {
         this.err = err;
         return this;
      }

      public TruffleContext.Builder in(InputStream in) {
         this.in = in;
         return this;
      }

      public TruffleContext.Builder forceSharing(Boolean enabled) {
         this.sharingEnabled = enabled;
         return this;
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleContext.Builder option(String key, String value) {
         Objects.requireNonNull(key);
         Objects.requireNonNull(value);
         if (this.options == null) {
            this.options = new HashMap<>();
         }

         this.options.put(key, value);
         return this;
      }

      public TruffleContext.Builder inheritAllAccess(boolean b) {
         this.inheritAccess = b;
         return this;
      }

      public TruffleContext.Builder allowCreateThread(boolean b) {
         this.allowCreateThread = b;
         return this;
      }

      public TruffleContext.Builder allowNativeAccess(boolean b) {
         this.allowNativeAccess = b;
         return this;
      }

      public TruffleContext.Builder allowIO(boolean b) {
         this.allowIO = b;
         return this;
      }

      public TruffleContext.Builder allowHostClassLoading(boolean b) {
         this.allowHostClassLoading = b;
         return this;
      }

      public TruffleContext.Builder allowHostClassLookup(boolean b) {
         this.allowHostLookup = b;
         return this;
      }

      public TruffleContext.Builder allowCreateProcess(boolean b) {
         this.allowCreateProcess = b;
         return this;
      }

      public TruffleContext.Builder allowInnerContextOptions(boolean b) {
         this.allowInnerContextOptions = b;
         return this;
      }

      public TruffleContext.Builder allowPolyglotAccess(boolean b) {
         this.allowPolyglotAccess = b;
         return this;
      }

      public TruffleContext.Builder allowInheritEnvironmentAccess(boolean b) {
         this.allowEnvironmentAccess = b;
         return this;
      }

      public TruffleContext.Builder environment(String name, String value) {
         Objects.requireNonNull(name, "Name must be non null.");
         Objects.requireNonNull(value, "Value must be non null.");
         if (this.environment == null) {
            this.environment = new HashMap<>();
         }

         this.environment.put(name, value);
         return this;
      }

      public TruffleContext.Builder environment(Map<String, String> env) {
         Objects.requireNonNull(env, "Env must be non null.");

         for (Entry<String, String> e : env.entrySet()) {
            this.environment(e.getKey(), e.getValue());
         }

         return this;
      }

      public TruffleContext.Builder timeZone(ZoneId zone) {
         this.timeZone = zone;
         return this;
      }

      public TruffleContext.Builder arguments(String language, String[] args) {
         Objects.requireNonNull(language);
         Objects.requireNonNull(args);
         String[] newArgs = args;
         if (args.length > 0) {
            newArgs = new String[args.length];

            for (int i = 0; i < args.length; i++) {
               newArgs[i] = Objects.requireNonNull(args[i]);
            }
         }

         if (this.arguments == null) {
            this.arguments = new HashMap<>();
         }

         this.arguments.put(language, newArgs);
         return this;
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleContext.Builder options(Map<String, String> options) {
         for (Entry<String, String> entry : options.entrySet()) {
            this.option(entry.getKey(), entry.getValue());
         }

         return this;
      }

      public TruffleContext.Builder onCancelled(Runnable r) {
         this.onCancelled = r;
         return this;
      }

      public TruffleContext.Builder onExited(Consumer<Integer> r) {
         this.onExited = r;
         return this;
      }

      public TruffleContext.Builder onClosed(Runnable r) {
         this.onClosed = r;
         return this;
      }

      @CompilerDirectives.TruffleBoundary
      public TruffleContext build() {
         try {
            return LanguageAccessor.engineAccess()
               .createInternalContext(
                  this.sourceEnvironment.getPolyglotLanguageContext(),
                  this.out,
                  this.err,
                  this.in,
                  this.timeZone,
                  this.permittedLanguages,
                  this.config,
                  this.options,
                  this.arguments,
                  this.sharingEnabled,
                  this.initializeCreatorContext,
                  this.onCancelled,
                  this.onExited,
                  this.onClosed,
                  this.inheritAccess,
                  this.allowCreateThread,
                  this.allowNativeAccess,
                  this.allowIO,
                  this.allowHostLookup,
                  this.allowHostClassLoading,
                  this.allowCreateProcess,
                  this.allowPolyglotAccess,
                  this.allowEnvironmentAccess,
                  this.environment,
                  this.allowInnerContextOptions
               );
         } catch (Throwable var2) {
            throw TruffleLanguage.Env.engineToLanguageException(var2);
         }
      }
   }
}
