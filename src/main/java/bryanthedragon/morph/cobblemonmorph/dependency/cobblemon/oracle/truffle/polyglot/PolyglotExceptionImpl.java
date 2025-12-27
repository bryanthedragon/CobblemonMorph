package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleStackTrace;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.SourceSection;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class PolyglotExceptionImpl {
   private static final String CAUSE_CAPTION = "Caused by host exception: ";
   private static final boolean TRACE_STACK_TRACE_WALKING = false;
   private Object api;
   final PolyglotImpl polyglot;
   final PolyglotEngineImpl engine;
   final PolyglotContextImpl context;
   final Throwable exception;
   final boolean showInternalStackFrames;
   private final List<TruffleStackTraceElement> guestFrames;
   private StackTraceElement[] javaStackTrace;
   private List<PolyglotException.StackFrame> materializedFrames;
   private final SourceSection sourceLocation;
   private final boolean internal;
   private final boolean cancelled;
   private final boolean exit;
   private final boolean incompleteSource;
   private final boolean syntaxError;
   private final boolean resourceExhausted;
   private final boolean interrupted;
   private final int exitStatus;
   private final Value guestObject;
   private final String message;

   PolyglotExceptionImpl(
      PolyglotEngineImpl engine, PolyglotContextImpl.State polyglotContextState, boolean polyglotContextResourceExhausted, int exitCode, Throwable original
   ) {
      this(engine.impl, engine, polyglotContextState, polyglotContextResourceExhausted, exitCode, null, original, false, false);
   }

   PolyglotExceptionImpl(PolyglotImpl polyglot, Throwable original) {
      this(polyglot, null, null, false, 0, null, original, true, false);
   }

   PolyglotExceptionImpl(
      PolyglotImpl polyglot,
      PolyglotEngineImpl engine,
      PolyglotContextImpl.State polyglotContextState,
      boolean polyglotContextResourceExhausted,
      int exitCode,
      PolyglotLanguageContext languageContext,
      Throwable original,
      boolean allowInterop,
      boolean entered
   ) {
      this.polyglot = polyglot;
      this.engine = engine;
      this.context = languageContext != null ? languageContext.context : null;
      this.exception = original;
      this.guestFrames = TruffleStackTrace.getStackTrace(original);
      this.showInternalStackFrames = engine == null ? false : engine.engineOptionValues.get(PolyglotEngineOptions.ShowInternalStackFrames);
      Error resourceLimitError = getResourceLimitError(engine, this.exception);
      String exceptionMessage = null;
      InteropLibrary interop;
      if (allowInterop && (interop = InteropLibrary.getUncached()).isException(this.exception)) {
         try {
            ExceptionType exceptionType = interop.getExceptionType(this.exception);
            this.internal = false;
            boolean cancelInducedTruffleException = polyglotContextState != null
               && (polyglotContextState.isCancelling() || polyglotContextState == PolyglotContextImpl.State.CLOSED_CANCELLED);
            this.cancelled = cancelInducedTruffleException;
            this.resourceExhausted = resourceLimitError != null || cancelInducedTruffleException && polyglotContextResourceExhausted;
            this.syntaxError = exceptionType == ExceptionType.PARSE_ERROR;
            this.exit = exceptionType == ExceptionType.EXIT;
            this.exitStatus = this.exit ? interop.getExceptionExitStatus(this.exception) : 0;
            this.incompleteSource = this.syntaxError ? interop.isExceptionIncompleteSource(this.exception) : false;
            this.interrupted = exceptionType == ExceptionType.INTERRUPT && !this.cancelled;
            if (interop.hasExceptionMessage(this.exception)) {
               exceptionMessage = interop.asString(interop.getExceptionMessage(this.exception));
            }

            if (interop.hasSourceLocation(this.exception)) {
               this.sourceLocation = this.newSourceSection(interop.getSourceLocation(this.exception));
            } else {
               this.sourceLocation = null;
            }

            if (entered && languageContext != null && languageContext.isCreated() && !isHostException(engine, this.exception)) {
               this.guestObject = languageContext.asValue(this.exception);
            } else {
               this.guestObject = null;
            }
         } catch (UnsupportedMessageException var18) {
            throw CompilerDirectives.shouldNotReachHere(var18);
         }
      } else {
         boolean interruptException = this.exception instanceof PolyglotEngineImpl.InterruptExecution
            || this.exception != null && this.exception.getCause() instanceof InterruptedException
            || isHostException(engine, this.exception) && this.asHostException() instanceof InterruptedException;
         boolean truffleException = this.exception instanceof AbstractTruffleException;
         boolean cancelInducedTruffleOrInterruptException = polyglotContextState != null
            && (polyglotContextState.isCancelling() || polyglotContextState == PolyglotContextImpl.State.CLOSED_CANCELLED)
            && (interruptException || truffleException);
         this.cancelled = cancelInducedTruffleOrInterruptException || this.exception instanceof PolyglotEngineImpl.CancelExecution;
         this.resourceExhausted = resourceLimitError != null || cancelInducedTruffleOrInterruptException && polyglotContextResourceExhausted;
         this.interrupted = interruptException && !this.cancelled;
         this.syntaxError = false;
         this.incompleteSource = false;
         com.oracle.truffle.api.source.SourceSection location = null;
         boolean exitInducedTruffleOrInterruptException = polyglotContextState != null
            && (polyglotContextState.isExiting() || polyglotContextState == PolyglotContextImpl.State.CLOSED_EXITED)
            && (interruptException || truffleException);
         if (!exitInducedTruffleOrInterruptException && !(this.exception instanceof PolyglotContextImpl.ExitException)) {
            this.exit = false;
            this.exitStatus = 0;
            this.guestObject = null;
         } else {
            this.exit = true;
            this.exitStatus = this.exception instanceof PolyglotContextImpl.ExitException
               ? ((PolyglotContextImpl.ExitException)this.exception).getExitCode()
               : exitCode;
            this.guestObject = null;
            location = this.exception instanceof PolyglotContextImpl.ExitException
               ? ((PolyglotContextImpl.ExitException)this.exception).getSourceLocation()
               : null;
         }

         this.internal = !this.interrupted && !this.cancelled && !this.resourceExhausted && !this.exit && !truffleException;
         if (this.exception instanceof PolyglotEngineImpl.CancelExecution) {
            location = ((PolyglotEngineImpl.CancelExecution)this.exception).getSourceLocation();
         }

         this.sourceLocation = location != null ? this.newSourceSection(location) : null;
      }

      if (exceptionMessage == null) {
         exceptionMessage = this.isHostException()
            ? this.asHostException().getMessage()
            : (this.internal ? this.exception.toString() : this.exception.getMessage());
      }

      if (exceptionMessage != null) {
         this.message = exceptionMessage;
      } else if (resourceLimitError != null) {
         String resourceExhaustedMessage = "Resource exhausted";
         if (resourceLimitError instanceof StackOverflowError) {
            resourceExhaustedMessage = resourceExhaustedMessage + ": Stack overflow";
         }

         if (resourceLimitError instanceof OutOfMemoryError) {
            resourceExhaustedMessage = resourceExhaustedMessage + ": Out of memory";
         }

         this.message = resourceExhaustedMessage;
      } else {
         this.message = null;
      }

      EngineAccessor.LANGUAGE.materializeHostFrames(original);
   }

   private static Error getResourceLimitError(PolyglotEngineImpl engine, Throwable e) {
      if (e instanceof PolyglotEngineImpl.CancelExecution) {
         return ((PolyglotEngineImpl.CancelExecution)e).isResourceLimit() ? (Error)e : null;
      } else if (isHostException(engine, e)) {
         Throwable toCheck = engine.host.toHostResourceError(e);

         assert toCheck == null || toCheck instanceof StackOverflowError || toCheck instanceof OutOfMemoryError;

         return (Error)toCheck;
      } else {
         return !(e instanceof StackOverflowError) && !(e instanceof OutOfMemoryError) ? null : (Error)e;
      }
   }

   private SourceSection newSourceSection(com.oracle.truffle.api.source.SourceSection section) {
      Source truffleSource = section.getSource();
      org.graalvm.polyglot.Source source = this.polyglot.getAPIAccess().newSource(this.polyglot.getSourceDispatch(), truffleSource);
      return this.polyglot.getAPIAccess().newSourceSection(source, this.polyglot.getSourceSectionDispatch(), section);
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof PolyglotExceptionImpl ? this.exception == ((PolyglotExceptionImpl)obj).exception : false;
   }

   @Override
   public int hashCode() {
      return this.exception.hashCode();
   }

   public SourceSection getSourceLocation() {
      return this.sourceLocation;
   }

   public void onCreate(PolyglotException instance) {
      this.api = instance;
   }

   public boolean isResourceExhausted() {
      return this.resourceExhausted;
   }

   public boolean isInterrupted() {
      return this.interrupted;
   }

   public boolean isHostException() {
      return isHostException(this.engine, this.exception);
   }

   public Throwable asHostException() {
      if (!this.isHostException()) {
         throw PolyglotEngineException.unsupported(
            String.format(
               "Unsupported operation %s.%s. You can ensure that the operation is supported using %s.%s.",
               PolyglotException.class.getSimpleName(),
               "asHostException()",
               PolyglotException.class.getSimpleName(),
               "isHostException()"
            )
         );
      } else {
         return this.engine.host.unboxHostException(this.exception);
      }
   }

   public void printStackTrace(PrintWriter s) {
      this.printStackTrace(new PolyglotExceptionImpl.WrappedPrintWriter(s));
   }

   public void printStackTrace(PrintStream s) {
      this.printStackTrace(new PolyglotExceptionImpl.WrappedPrintStream(s));
   }

   private void printStackTrace(PolyglotExceptionImpl.PrintStreamOrWriter s) {
      synchronized (s.lock()) {
         if (!this.isInternalError() || this.guestFrames != null && !this.guestFrames.isEmpty()) {
            if (!this.isInternalError() && this.getMessage() != null && !this.getMessage().isEmpty()) {
               s.println(this.getMessage());
            } else {
               s.println(this.api);
            }

            this.materialize();
            int languageIdLength = 0;

            for (PolyglotException.StackFrame traceElement : this.getPolyglotStackTrace()) {
               if (!traceElement.isHostFrame()) {
                  languageIdLength = Math.max(languageIdLength, this.polyglot.getAPIAccess().getDispatch(traceElement).getLanguage().getId().length());
               }
            }

            for (PolyglotException.StackFrame traceElementx : this.getPolyglotStackTrace()) {
               s.println("\tat " + this.polyglot.getAPIAccess().getDispatch(traceElementx).toStringImpl(languageIdLength));
            }

            if (this.isHostException()) {
               s.println("Caused by host exception: " + this.asHostException());
            }

            if (this.isInternalError()) {
               s.println("Original Internal Error: ");
               s.printStackTrace(this.exception);
            }
         } else {
            s.print(this.api.getClass().getName() + ": ");
            s.printStackTrace(this.exception);
            s.println("Internal GraalVM error, please report at https://github.com/oracle/graal/issues/.");
         }
      }
   }

   public String getMessage() {
      return this.message;
   }

   public StackTraceElement[] getJavaStackTrace() {
      if (this.javaStackTrace == null) {
         this.materialize();
         this.javaStackTrace = new StackTraceElement[this.materializedFrames.size()];

         for (int i = 0; i < this.javaStackTrace.length; i++) {
            this.javaStackTrace[i] = this.materializedFrames.get(i).toHostFrame();
         }
      }

      return this.javaStackTrace;
   }

   private void materialize() {
      if (this.materializedFrames == null) {
         List<PolyglotException.StackFrame> frames = new ArrayList<>();

         for (PolyglotException.StackFrame frame : this.getPolyglotStackTrace()) {
            frames.add(frame);
         }

         this.materializedFrames = Collections.unmodifiableList(frames);
      }
   }

   public StackTraceElement[] getStackTrace() {
      return (StackTraceElement[])this.getJavaStackTrace().clone();
   }

   public boolean isInternalError() {
      return this.internal;
   }

   public Iterable<PolyglotException.StackFrame> getPolyglotStackTrace() {
      return (Iterable<PolyglotException.StackFrame>)(this.materializedFrames != null
         ? this.materializedFrames
         : new Iterable<PolyglotException.StackFrame>() {
            @Override
            public Iterator<PolyglotException.StackFrame> iterator() {
               return PolyglotExceptionImpl.createStackFrameIterator(PolyglotExceptionImpl.this);
            }
         });
   }

   public boolean isCancelled() {
      return this.cancelled;
   }

   public boolean isExit() {
      return this.exit;
   }

   public boolean isIncompleteSource() {
      return this.incompleteSource;
   }

   public int getExitStatus() {
      return this.exitStatus;
   }

   public boolean isSyntaxError() {
      return this.syntaxError;
   }

   public Value getGuestObject() {
      return this.guestObject;
   }

   static String printStackToString(PolyglotLanguageContext context, Node node) {
      PolyglotExceptionImpl.StackTraceException stack = new PolyglotExceptionImpl.StackTraceException(node);
      TruffleStackTrace.fillIn(stack);
      PolyglotException e = PolyglotImpl.guestToHostException(context, stack, true);
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      e.printStackTrace(new PrintStream(out));
      return new String(out.toByteArray());
   }

   Object getFileSystemContext(PolyglotLanguage language) {
      if (this.context == null) {
         return null;
      } else {
         synchronized (this.context) {
            PolyglotLanguageContext languageContext = this.context.getContext(language);
            return !languageContext.isCreated() ? null : languageContext.getInternalFileSystemContext();
         }
      }
   }

   static Iterator<PolyglotException.StackFrame> createStackFrameIterator(PolyglotExceptionImpl impl) {
      final AbstractPolyglotImpl.APIAccess apiAccess = impl.polyglot.getAPIAccess();
      Throwable cause = findCause(impl.engine, impl.exception);
      StackTraceElement[] hostStack;
      if (EngineAccessor.LANGUAGE.isTruffleStackTrace(cause)) {
         hostStack = EngineAccessor.LANGUAGE.getInternalStackTraceElements(cause);
      } else if (cause.getStackTrace() != null && cause.getStackTrace().length != 0) {
         hostStack = cause.getStackTrace();
      } else {
         hostStack = impl.exception.getStackTrace();
      }

      Iterator<TruffleStackTraceElement> guestFrames = impl.guestFrames == null ? Collections.emptyIterator() : impl.guestFrames.iterator();
      boolean inHostLanguage = impl.isHostException() || impl.isInternalError();
      return new PolyglotExceptionImpl.MergedHostGuestIterator<>(
         impl.engine, hostStack, guestFrames, inHostLanguage, new Function<StackTraceElement, PolyglotException.StackFrame>() {
            public PolyglotException.StackFrame apply(StackTraceElement element) {
               return apiAccess.newPolyglotStackTraceElement(PolyglotExceptionFrame.createHost(impl, element), impl.api);
            }
         }, new Function<TruffleStackTraceElement, PolyglotException.StackFrame>() {
            private boolean firstGuestFrame = true;

            public PolyglotException.StackFrame apply(TruffleStackTraceElement guestFrame) {
               boolean first = this.firstGuestFrame;
               this.firstGuestFrame = false;
               PolyglotExceptionFrame guest = PolyglotExceptionFrame.createGuest(impl, guestFrame, first);
               return guest != null ? apiAccess.newPolyglotStackTraceElement(guest, impl.api) : null;
            }
         }
      );
   }

   private static Throwable findCause(PolyglotEngineImpl engine, Throwable throwable) {
      Throwable cause = throwable;
      if (isHostException(engine, throwable)) {
         return findCause(engine, engine.host.unboxHostException(throwable));
      } else if (EngineAccessor.EXCEPTION.isException(throwable)) {
         return EngineAccessor.EXCEPTION.getLazyStackTrace(throwable);
      } else {
         while (cause.getCause() != null && cause.getStackTrace().length == 0) {
            if (isHostException(engine, cause)) {
               cause = engine.host.unboxHostException(cause);
            } else {
               cause = cause.getCause();
            }
         }

         return cause;
      }
   }

   private static boolean isHostException(PolyglotEngineImpl engine, Throwable cause) {
      return engine != null && engine.host != null && engine.host.isHostException(cause);
   }

   static class MergedHostGuestIterator<T, G> implements Iterator<T> {
      private static final String POLYGLOT_PACKAGE = Engine.class.getName().substring(0, Engine.class.getName().lastIndexOf(46) + 1);
      private static final String HOST_INTEROP_PACKAGE = "com.oracle.truffle.polyglot.";
      private static final String[] JAVA_INTEROP_HOST_TO_GUEST = new String[]{
         "com.oracle.truffle.polyglot.PolyglotMap",
         "com.oracle.truffle.polyglot.PolyglotList",
         "com.oracle.truffle.polyglot.PolyglotFunction",
         "com.oracle.truffle.polyglot.PolyglotMapAndFunction",
         "com.oracle.truffle.polyglot.PolyglotFunctionProxyHandler",
         "com.oracle.truffle.polyglot.PolyglotObjectProxyHandler"
      };
      private final PolyglotEngineImpl engine;
      private final Iterator<G> guestFrames;
      private final StackTraceElement[] hostStack;
      private final ListIterator<StackTraceElement> hostFrames;
      private final Function<StackTraceElement, T> hostFrameConvertor;
      private final Function<G, T> guestFrameConvertor;
      private boolean inHostLanguage;
      private T fetchedNext;

      MergedHostGuestIterator(
         PolyglotEngineImpl engine,
         StackTraceElement[] hostStack,
         Iterator<G> guestFrames,
         boolean inHostLanguage,
         Function<StackTraceElement, T> hostFrameConvertor,
         Function<G, T> guestFrameConvertor
      ) {
         this.engine = engine;
         this.hostStack = hostStack;
         this.hostFrames = Arrays.asList(hostStack).listIterator();
         this.guestFrames = guestFrames;
         this.inHostLanguage = inHostLanguage;
         this.hostFrameConvertor = hostFrameConvertor;
         this.guestFrameConvertor = guestFrameConvertor;
      }

      @Override
      public boolean hasNext() {
         return this.fetchNext() != null;
      }

      @Override
      public T next() {
         T next = this.fetchNext();
         if (next == null) {
            throw new NoSuchElementException();
         } else {
            this.fetchedNext = null;
            return next;
         }
      }

      T fetchNext() {
         if (this.fetchedNext != null) {
            return this.fetchedNext;
         } else {
            while (this.hostFrames.hasNext()) {
               StackTraceElement element = this.hostFrames.next();
               this.traceStackTraceElement(element);
               if (this.inHostLanguage) {
                  int guestToHost = findGuestToHostFrame(this.engine, element, this.hostStack, this.hostFrames.nextIndex());
                  if (guestToHost >= 0) {
                     assert !isHostToGuest(element);

                     this.inHostLanguage = false;

                     for (int i = 0; i < guestToHost; i++) {
                        element = this.hostFrames.next();
                        this.traceStackTraceElement(element);
                     }
                  }
               } else if (isHostToGuest(element)) {
                  this.inHostLanguage = true;

                  while (this.hostFrames.hasNext()) {
                     StackTraceElement next = this.hostFrames.next();
                     this.traceStackTraceElement(next);
                     if (!isHostToGuest(next)) {
                        this.hostFrames.previous();
                        break;
                     }

                     element = next;
                  }
               }

               if (isGuestCall(element)) {
                  this.inHostLanguage = false;
                  if (this.guestFrames.hasNext()) {
                     G guestFrame = this.guestFrames.next();
                     T frame = this.guestFrameConvertor.apply(guestFrame);
                     if (frame != null) {
                        this.fetchedNext = frame;
                        return this.fetchedNext;
                     }
                  }
               } else if (this.inHostLanguage) {
                  this.fetchedNext = this.hostFrameConvertor.apply(element);
                  return this.fetchedNext;
               }
            }

            while (this.guestFrames.hasNext()) {
               G guestFrame = this.guestFrames.next();
               T frame = this.guestFrameConvertor.apply(guestFrame);
               if (frame != null) {
                  this.fetchedNext = frame;
                  return this.fetchedNext;
               }
            }

            return null;
         }
      }

      static boolean isLazyStackTraceElement(StackTraceElement element) {
         return element == null;
      }

      static boolean isGuestCall(StackTraceElement element) {
         return isLazyStackTraceElement(element) || EngineAccessor.RUNTIME.isGuestCallStackFrame(element);
      }

      static boolean isHostToGuest(StackTraceElement element) {
         if (isLazyStackTraceElement(element)) {
            return false;
         } else if (element.getClassName().startsWith(POLYGLOT_PACKAGE) && element.getClassName().indexOf(46, POLYGLOT_PACKAGE.length()) < 0) {
            return true;
         } else {
            if (element.getClassName().startsWith("com.oracle.truffle.polyglot.")) {
               for (String hostToGuestClassName : JAVA_INTEROP_HOST_TO_GUEST) {
                  if (element.getClassName().equals(hostToGuestClassName)) {
                     return true;
                  }
               }
            }

            return false;
         }
      }

      static int findGuestToHostFrame(PolyglotEngineImpl engine, StackTraceElement firstElement, StackTraceElement[] hostStack, int nextElementIndex) {
         if (isLazyStackTraceElement(firstElement)) {
            return -1;
         } else {
            return engine != null && engine.host != null ? engine.host.findNextGuestToHostStackTraceElement(firstElement, hostStack, nextElementIndex) : -1;
         }
      }

      private void traceStackTraceElement(StackTraceElement element) {
      }
   }

   private abstract static class PrintStreamOrWriter {
      abstract Object lock();

      abstract void print(Object o);

      abstract void println(Object o);

      abstract void printStackTrace(Throwable t);
   }

   static class StackTraceException extends AbstractTruffleException {
      StackTraceException(Node location) {
         super(location);
      }
   }

   private static class WrappedPrintStream extends PolyglotExceptionImpl.PrintStreamOrWriter {
      private final PrintStream printStream;

      WrappedPrintStream(PrintStream printStream) {
         this.printStream = printStream;
      }

      @Override
      Object lock() {
         return this.printStream;
      }

      @Override
      void print(Object o) {
         this.printStream.print(o);
      }

      @Override
      void println(Object o) {
         this.printStream.println(o);
      }

      @Override
      void printStackTrace(Throwable t) {
         t.printStackTrace(this.printStream);
      }
   }

   private static class WrappedPrintWriter extends PolyglotExceptionImpl.PrintStreamOrWriter {
      private final PrintWriter printWriter;

      WrappedPrintWriter(PrintWriter printWriter) {
         this.printWriter = printWriter;
      }

      @Override
      Object lock() {
         return this.printWriter;
      }

      @Override
      void print(Object o) {
         this.printWriter.print(o);
      }

      @Override
      void println(Object o) {
         this.printWriter.println(o);
      }

      @Override
      void printStackTrace(Throwable t) {
         t.printStackTrace(this.printWriter);
      }
   }
}
