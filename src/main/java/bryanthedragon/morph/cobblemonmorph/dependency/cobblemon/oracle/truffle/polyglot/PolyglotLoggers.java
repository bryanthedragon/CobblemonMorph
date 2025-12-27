package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

final class PolyglotLoggers {
   private static final Map<Path, PolyglotLoggers.SharedFileHandler> fileHandlers = new HashMap<>();
   private static final String GRAAL_COMPILER_LOG_ID = "graal";
   private static final Set<String> INTERNAL_IDS;

   private PolyglotLoggers() {
   }

   static Set<String> getInternalIds() {
      return INTERNAL_IDS;
   }

   static PolyglotContextImpl getCurrentOuterContext() {
      return EngineAccessor.EngineImpl.getOuterContext(PolyglotFastThreadLocals.getContext(null));
   }

   static boolean isSameLogSink(Handler h1, Handler h2) {
      if (h1 == h2) {
         return true;
      } else {
         return h1 instanceof PolyglotLoggers.PolyglotStreamHandler && h2 instanceof PolyglotLoggers.PolyglotStreamHandler
            ? ((PolyglotLoggers.PolyglotStreamHandler)h1).sink == ((PolyglotLoggers.PolyglotStreamHandler)h2).sink
            : false;
      }
   }

   static Handler asHandler(Object logHandlerOrStream) {
      if (logHandlerOrStream == null) {
         return null;
      } else if (logHandlerOrStream instanceof Handler) {
         return (Handler)logHandlerOrStream;
      } else if (logHandlerOrStream instanceof OutputStream) {
         return createStreamHandler((OutputStream)logHandlerOrStream, true, true);
      } else {
         throw new IllegalArgumentException("Unexpected logHandlerOrStream parameter: " + logHandlerOrStream);
      }
   }

   static Handler createDefaultHandler(final OutputStream out) {
      return new PolyglotLoggers.PolyglotStreamHandler(new PolyglotLoggers.RedirectNotificationOutputStream(out), false, true, true);
   }

   static Handler getFileHandler(String path) {
      Path absolutePath = Paths.get(path).toAbsolutePath().normalize();
      synchronized (fileHandlers) {
         PolyglotLoggers.SharedFileHandler handler = fileHandlers.get(absolutePath);
         if (handler == null) {
            try {
               handler = new PolyglotLoggers.SharedFileHandler(absolutePath);
               fileHandlers.put(absolutePath, handler);
            } catch (IOException var6) {
               throw PolyglotEngineException.illegalArgument(
                  "Cannot open log file " + path + " for writing, IO error: " + (var6.getMessage() != null ? var6.getMessage() : null)
               );
            }
         }

         return handler.retain();
      }
   }

   static Set<Path> getActiveFileHandlers() {
      synchronized (fileHandlers) {
         return new HashSet<>(fileHandlers.keySet());
      }
   }

   static Handler createStreamHandler(final OutputStream out, final boolean closeStream, final boolean flushOnPublish) {
      return new PolyglotLoggers.PolyglotStreamHandler(out, closeStream, flushOnPublish, false);
   }

   static boolean isDefaultHandler(Handler handler) {
      if (!(handler instanceof PolyglotLoggers.PolyglotStreamHandler)) {
         return false;
      } else {
         PolyglotLoggers.PolyglotStreamHandler phandler = (PolyglotLoggers.PolyglotStreamHandler)handler;
         return phandler.isDefault;
      }
   }

   static LogRecord createLogRecord(
      Level level, String loggerName, String message, String className, String methodName, Object[] parameters, Throwable thrown, String formatKind
   ) {
      return new PolyglotLoggers.ImmutableLogRecord(
         level, loggerName, message, className, methodName, parameters, thrown, PolyglotLoggers.ImmutableLogRecord.FormatKind.valueOf(formatKind)
      );
   }

   static String getFormatKind(LogRecord logRecord) {
      return (logRecord instanceof PolyglotLoggers.ImmutableLogRecord
            ? ((PolyglotLoggers.ImmutableLogRecord)logRecord).getFormatKind()
            : PolyglotLoggers.ImmutableLogRecord.FormatKind.DEFAULT)
         .name();
   }

   static {
      Set<String> s = new HashSet<>();
      Collections.addAll(s, "engine", "graal");
      INTERNAL_IDS = Collections.unmodifiableSet(s);
   }

   private static final class ContextLogHandler extends Handler {
      private final WeakReference<PolyglotContextImpl> contextRef;

      ContextLogHandler(PolyglotContextImpl context) {
         this.contextRef = new WeakReference<>(context);
      }

      @Override
      public void publish(final LogRecord record) {
         this.findDelegate().publish(record);
      }

      @Override
      public void flush() {
         this.findDelegate().flush();
      }

      @Override
      public void close() throws SecurityException {
         this.findDelegate().close();
      }

      private Handler findDelegate() {
         PolyglotContextImpl context = this.contextRef.get();
         if (context == null) {
            throw invalidSharing();
         } else {
            return context.config.logHandler;
         }
      }

      static AssertionError invalidSharing() {
         throw new AssertionError("Invalid sharing of bound TruffleLogger in AST nodes detected.");
      }
   }

   static final class EngineLoggerProvider implements Function<String, TruffleLogger> {
      private volatile Object loggers;
      private final Handler logHandler;
      private final Map<String, Level> logLevels;

      EngineLoggerProvider(Handler logHandler, Map<String, Level> logLevels) {
         this.logHandler = logHandler;
         this.logLevels = logLevels;
      }

      public TruffleLogger apply(String loggerId) {
         Object loggersCache = this.loggers;
         if (loggersCache == null) {
            synchronized (this) {
               loggersCache = this.loggers;
               if (loggersCache == null) {
                  Handler useHandler = resolveHandler(this.logHandler);
                  PolyglotLoggers.LoggerCache spi = PolyglotLoggers.LoggerCache.newEngineLoggerCache(
                     useHandler, this.logLevels, false, Collections.singleton("graal"), Level.INFO
                  );
                  this.loggers = loggersCache = EngineAccessor.LANGUAGE.createEngineLoggers(spi);
               }
            }
         }

         return EngineAccessor.LANGUAGE.getLogger(loggerId, null, loggersCache);
      }

      private static Handler resolveHandler(Handler handler) {
         return (Handler)(PolyglotLoggers.isDefaultHandler(handler) ? handler : new PolyglotLoggers.SafeHandler(handler));
      }
   }

   private static final class ImmutableLogRecord extends LogRecord {
      private static final long serialVersionUID = 1L;
      private final PolyglotLoggers.ImmutableLogRecord.FormatKind formatKind;

      ImmutableLogRecord(
         final Level level,
         final String loggerName,
         final String message,
         final String className,
         final String methodName,
         final Object[] parameters,
         final Throwable thrown,
         PolyglotLoggers.ImmutableLogRecord.FormatKind formatKind
      ) {
         super(level, message);
         super.setLoggerName(loggerName);
         if (className != null) {
            super.setSourceClassName(className);
         }

         if (methodName != null) {
            super.setSourceMethodName(methodName);
         }

         Object[] copy = parameters;
         if (parameters != null && parameters.length > 0) {
            copy = new Object[parameters.length];

            for (int i = 0; i < parameters.length; i++) {
               copy[i] = safeValue(parameters[i]);
            }
         }

         super.setParameters(copy);
         super.setThrown(thrown);
         this.formatKind = formatKind;
      }

      @Override
      public void setLevel(Level level) {
         throw new UnsupportedOperationException("Setting Level is not supported.");
      }

      @Override
      public void setLoggerName(String name) {
         throw new UnsupportedOperationException("Setting Logger Name is not supported.");
      }

      @Override
      public void setMessage(String message) {
         throw new UnsupportedOperationException("Setting Messag is not supported.");
      }

      @Override
      public void setMillis(long millis) {
         throw new UnsupportedOperationException("Setting Millis is not supported.");
      }

      @Override
      public void setParameters(Object[] parameters) {
         throw new UnsupportedOperationException("Setting Parameters is not supported.");
      }

      @Override
      public void setResourceBundle(ResourceBundle bundle) {
         throw new UnsupportedOperationException("Setting Resource Bundle is not supported.");
      }

      @Override
      public void setResourceBundleName(String name) {
         throw new UnsupportedOperationException("Setting Resource Bundle Name is not supported.");
      }

      @Override
      public void setSequenceNumber(long seq) {
         throw new UnsupportedOperationException("Setting Sequence Number is not supported.");
      }

      @Override
      public void setSourceClassName(String sourceClassName) {
         throw new UnsupportedOperationException("Setting Parameters is not supported.");
      }

      @Override
      public void setSourceMethodName(String sourceMethodName) {
         throw new UnsupportedOperationException("Setting Source Method Name is not supported.");
      }

      @Override
      public void setThreadID(int threadID) {
         throw new UnsupportedOperationException("Setting Thread ID is not supported.");
      }

      @Override
      public void setThrown(Throwable thrown) {
         throw new UnsupportedOperationException("Setting Throwable is not supported.");
      }

      PolyglotLoggers.ImmutableLogRecord.FormatKind getFormatKind() {
         return this.formatKind;
      }

      private static Object safeValue(final Object param) {
         if (param != null && !EngineAccessor.EngineImpl.isPrimitive(param)) {
            try {
               return InteropLibrary.getFactory().getUncached().asString(InteropLibrary.getFactory().getUncached().toDisplayString(param));
            } catch (UnsupportedMessageException var2) {
               throw CompilerDirectives.shouldNotReachHere(var2);
            }
         } else {
            return param;
         }
      }

      static enum FormatKind {
         RAW,
         NO_LEVEL,
         DEFAULT;
      }
   }

   static final class LoggerCache {
      static final PolyglotLoggers.LoggerCache DEFAULT = new PolyglotLoggers.LoggerCache(
         PolyglotLoggers.PolyglotLogHandler.INSTANCE, true, null, Collections.emptySet()
      );
      private final Handler handler;
      private final boolean useCurrentContext;
      private final Map<String, Level> ownerLogLevels;
      private final Set<String> rawLoggerIds;
      private final Set<Level> implicitLevels;
      private volatile WeakReference<PolyglotImpl.VMObject> ownerRef;

      private LoggerCache(Handler handler, boolean useCurrentContext, Map<String, Level> ownerLogLevels, Set<String> rawLoggerIds, Level... implicitLevels) {
         Objects.requireNonNull(handler);
         this.handler = handler;
         this.useCurrentContext = useCurrentContext;
         this.ownerLogLevels = ownerLogLevels;
         this.rawLoggerIds = rawLoggerIds;
         if (implicitLevels.length == 0) {
            this.implicitLevels = Collections.emptySet();
         } else {
            this.implicitLevels = new HashSet<>();
            Collections.addAll(this.implicitLevels, implicitLevels);
         }
      }

      void setOwner(PolyglotImpl.VMObject owner) {
         if (this.ownerRef != null) {
            throw new IllegalStateException("owner can only be set once");
         } else {
            this.ownerRef = new WeakReference<>(owner);
         }
      }

      static PolyglotLoggers.LoggerCache newEngineLoggerCache(PolyglotEngineImpl engine) {
         return newEngineLoggerCache(new PolyglotLoggers.PolyglotLogHandler(engine.logHandler), engine.logLevels, true, Collections.emptySet());
      }

      static PolyglotLoggers.LoggerCache newEngineLoggerCache(
         Handler handler, Map<String, Level> logLevels, boolean useCurrentContext, Set<String> rawLoggerIds, Level... implicitLevels
      ) {
         return new PolyglotLoggers.LoggerCache(handler, useCurrentContext, logLevels, rawLoggerIds, implicitLevels);
      }

      static PolyglotLoggers.LoggerCache newContextLoggerCache(PolyglotContextImpl context) {
         PolyglotLoggers.LoggerCache cache = new PolyglotLoggers.LoggerCache(
            new PolyglotLoggers.ContextLogHandler(context), false, context.config.logLevels, Collections.emptySet()
         );
         cache.setOwner(context);
         return cache;
      }

      public PolyglotImpl.VMObject getOwner() {
         return this.ownerRef == null ? null : this.ownerRef.get();
      }

      public Handler getLogHandler() {
         return this.handler;
      }

      public Map<String, Level> getLogLevels() {
         if (this.useCurrentContext) {
            PolyglotContextImpl context = PolyglotLoggers.getCurrentOuterContext();
            if (context != null) {
               return context.config.logLevels;
            }
         }

         if (this.ownerLogLevels != null) {
            if (this.ownerRef != null && this.ownerRef.get() == null) {
               throw PolyglotLoggers.ContextLogHandler.invalidSharing();
            } else {
               return this.ownerLogLevels;
            }
         } else {
            return null;
         }
      }

      public LogRecord createLogRecord(
         Level level, String loggerName, String message, String className, String methodName, Object[] parameters, Throwable thrown
      ) {
         PolyglotLoggers.ImmutableLogRecord.FormatKind formaterKind;
         if (this.rawLoggerIds.contains(loggerName)) {
            formaterKind = PolyglotLoggers.ImmutableLogRecord.FormatKind.RAW;
         } else if (this.implicitLevels.contains(level)) {
            formaterKind = PolyglotLoggers.ImmutableLogRecord.FormatKind.NO_LEVEL;
         } else {
            formaterKind = PolyglotLoggers.ImmutableLogRecord.FormatKind.DEFAULT;
         }

         return new PolyglotLoggers.ImmutableLogRecord(level, loggerName, message, className, methodName, parameters, thrown, formaterKind);
      }
   }

   private static final class PolyglotLogHandler extends Handler {
      private static final Handler INSTANCE = new PolyglotLoggers.PolyglotLogHandler();
      private final Handler fallBackHandler;

      PolyglotLogHandler() {
         this.fallBackHandler = null;
      }

      PolyglotLogHandler(Handler fallbackHandler) {
         this.fallBackHandler = fallbackHandler;
      }

      @Override
      public void publish(final LogRecord record) {
         Handler handler = findDelegate();
         if (handler == null) {
            handler = this.fallBackHandler;
         }

         if (handler != null) {
            handler.publish(record);
         }
      }

      @Override
      public void flush() {
         Handler handler = findDelegate();
         if (handler != null) {
            handler.flush();
         }
      }

      @Override
      public void close() throws SecurityException {
         Handler handler = findDelegate();
         if (handler != null) {
            handler.close();
         }
      }

      private static Handler findDelegate() {
         PolyglotContextImpl currentContext = PolyglotLoggers.getCurrentOuterContext();
         return currentContext != null ? currentContext.config.logHandler : null;
      }
   }

   private static class PolyglotStreamHandler extends StreamHandler {
      private final OutputStream sink;
      private final boolean closeStream;
      private final boolean flushOnPublish;
      private final boolean isDefault;

      PolyglotStreamHandler(final OutputStream out, final boolean closeStream, final boolean flushOnPublish, final boolean defaultHandler) {
         super(out, PolyglotLoggers.PolyglotStreamHandler.FormatterImpl.INSTANCE);
         this.setLevel(Level.ALL);
         this.sink = out;
         this.closeStream = closeStream;
         this.flushOnPublish = flushOnPublish;
         this.isDefault = defaultHandler;
      }

      @Override
      public synchronized void publish(LogRecord record) {
         super.publish(record);
         if (this.flushOnPublish) {
            this.flush();
         }
      }

      @Override
      public void close() {
         if (this.closeStream) {
            super.close();
         } else {
            this.flush();
         }
      }

      private static final class FormatterImpl extends Formatter {
         private static final String FORMAT_FULL = "[%1$s] %2$s: %3$s%4$s%n";
         private static final String FORMAT_NO_LEVEL = "[%1$s] %2$s%3$s%n";
         static final Formatter INSTANCE = new PolyglotLoggers.PolyglotStreamHandler.FormatterImpl();

         @Override
         public String format(LogRecord record) {
            String loggerName = formatLoggerName(record.getLoggerName());
            String message = this.formatMessage(record);
            String stackTrace = "";
            Throwable exception = record.getThrown();
            if (exception != null) {
               StringWriter str = new StringWriter();

               try (PrintWriter out = new PrintWriter(str)) {
                  out.println();
                  exception.printStackTrace(out);
               }

               stackTrace = str.toString();
            }

            PolyglotLoggers.ImmutableLogRecord.FormatKind formatKind = ((PolyglotLoggers.ImmutableLogRecord)record).getFormatKind();
            String logEntry;
            switch (formatKind) {
               case DEFAULT:
                  logEntry = String.format("[%1$s] %2$s: %3$s%4$s%n", loggerName, record.getLevel().getName(), message, stackTrace);
                  break;
               case NO_LEVEL:
                  logEntry = String.format("[%1$s] %2$s%3$s%n", loggerName, message, stackTrace);
                  break;
               case RAW:
                  logEntry = message;
                  break;
               default:
                  throw new IllegalArgumentException("Unsupported FormatKind " + formatKind);
            }

            return logEntry;
         }

         private static String formatLoggerName(final String loggerName) {
            int index = loggerName.indexOf(46);
            String id;
            String name;
            if (index < 0) {
               id = loggerName;
               name = "";
            } else {
               id = loggerName.substring(0, index);
               name = loggerName.substring(index + 1);
            }

            if (name.isEmpty()) {
               return id;
            } else {
               StringBuilder sb = new StringBuilder(id);
               sb.append("::");
               sb.append(possibleSimpleName(name));
               return sb.toString();
            }
         }

         private static String possibleSimpleName(final String loggerName) {
            int index = -1;
            int i = 0;

            while (i >= 0) {
               if (i + 1 < loggerName.length() && Character.isUpperCase(loggerName.charAt(i + 1))) {
                  index = i + 1;
                  break;
               }

               i = loggerName.indexOf(46, i + 1);
            }

            return index < 0 ? loggerName : loggerName.substring(index);
         }
      }
   }

   private static final class RedirectNotificationOutputStream extends OutputStream {
      private static final String REDIRECT_FORMAT = "[To redirect Truffle log output to a file use one of the following options:%n* '--log.file=<path>' if the option is passed using a guest language launcher.%n* '-Dpolyglot.log.file=<path>' if the option is passed using the host Java launcher.%n* Configure logging using the polyglot embedding API.]%n";
      private final OutputStream delegate;
      private volatile boolean notificationPrinted;

      RedirectNotificationOutputStream(OutputStream delegate) {
         this.delegate = Objects.requireNonNull(delegate, "Delegate must be non null.");
      }

      @Override
      public void write(int b) throws IOException {
         this.printNotification();
         this.delegate.write(b);
      }

      @Override
      public void write(byte[] b, int off, int len) throws IOException {
         this.printNotification();
         this.delegate.write(b, off, len);
      }

      private void printNotification() {
         if (!this.notificationPrinted) {
            synchronized (this) {
               if (!this.notificationPrinted) {
                  PrintStream ps = new PrintStream(this.delegate);
                  ps.printf(
                     "[To redirect Truffle log output to a file use one of the following options:%n* '--log.file=<path>' if the option is passed using a guest language launcher.%n* '-Dpolyglot.log.file=<path>' if the option is passed using the host Java launcher.%n* Configure logging using the polyglot embedding API.]%n"
                  );
                  ps.flush();
                  this.notificationPrinted = true;
               }
            }
         }
      }

      @Override
      public void close() throws IOException {
         this.delegate.close();
      }
   }

   private static final class SafeHandler extends Handler {
      private final Handler delegate;

      SafeHandler(Handler delegate) {
         Objects.requireNonNull(delegate);
         this.delegate = delegate;
      }

      @Override
      public void publish(LogRecord lr) {
         try {
            this.delegate.publish(lr);
         } catch (Throwable var3) {
         }
      }

      @Override
      public void flush() {
         this.delegate.flush();
      }

      @Override
      public void close() throws SecurityException {
         this.delegate.close();
      }
   }

   private static final class SharedFileHandler extends PolyglotLoggers.PolyglotStreamHandler {
      private final Path path;
      private int refCount;

      SharedFileHandler(Path path) throws IOException {
         super(new FileOutputStream(path.toFile(), true), true, true, false);
         this.path = path;
      }

      PolyglotLoggers.SharedFileHandler retain() {
         assert Thread.holdsLock(PolyglotLoggers.fileHandlers);

         this.refCount++;
         return this;
      }

      @Override
      public void close() {
         synchronized (PolyglotLoggers.fileHandlers) {
            this.refCount--;
            if (this.refCount == 0) {
               PolyglotLoggers.fileHandlers.remove(this.path);
               super.close();
            }
         }
      }
   }
}
