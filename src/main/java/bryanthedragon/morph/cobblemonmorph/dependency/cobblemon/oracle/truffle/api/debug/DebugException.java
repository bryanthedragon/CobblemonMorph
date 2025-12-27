package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleStackTrace;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public final class DebugException extends RuntimeException {
   private static final long serialVersionUID = 5017970176581546348L;
   private static final String CAUSE_CAPTION = "Caused by: ";
   private final DebuggerSession session;
   private final Throwable exception;
   private final LanguageInfo preferredLanguage;
   private final Node throwLocation;
   private final StackTraceElement[] rawStackTrace;
   private volatile boolean isCatchNodeComputed;
   private volatile DebugException.CatchLocation catchLocation;
   private SuspendedEvent suspendedEvent;
   private List<DebugStackTraceElement> debugStackTrace;
   private List<List<DebugStackTraceElement>> debugAsyncStacks;
   private StackTraceElement[] javaLikeStackTrace;

   static DebugException create(DebuggerSession session, String message) {
      return new DebugException(session, message, null, null, null, true, null);
   }

   static DebugException create(DebuggerSession session, Throwable exception, LanguageInfo preferredLanguage) {
      return create(session, exception, preferredLanguage, null, true, null);
   }

   static DebugException create(
      DebuggerSession session,
      Throwable exception,
      LanguageInfo preferredLanguage,
      Node throwLocation,
      boolean isCatchNodeComputed,
      DebugException.CatchLocation catchLocation
   ) {
      return new DebugException(session, getTheMessage(exception), exception, preferredLanguage, throwLocation, isCatchNodeComputed, catchLocation);
   }

   private DebugException(
      DebuggerSession session,
      String message,
      Throwable exception,
      LanguageInfo preferredLanguage,
      Node throwLocation,
      boolean isCatchNodeComputed,
      DebugException.CatchLocation catchLocation
   ) {
      super(message);
      StackTraceElement[] exceptionStackTrace = null;
      if (session.isShowHostStackFrames()) {
         if (exception != null) {
            StackTraceElement[] stackTrace = exception.getStackTrace();
            if (stackTrace.length > 0) {
               exceptionStackTrace = stackTrace;
            }
         }

         if (exceptionStackTrace == null) {
            Throwable t = super.fillInStackTrace();

            assert this == t;
         }
      }

      this.rawStackTrace = exceptionStackTrace;
      this.session = session;
      this.exception = exception;
      this.preferredLanguage = preferredLanguage;
      this.throwLocation = throwLocation;
      this.isCatchNodeComputed = isCatchNodeComputed;
      this.catchLocation = catchLocation != null ? catchLocation.cloneFor(session) : null;
      super.setStackTrace(this.getStackTrace());
   }

   private static String getTheMessage(Throwable exception) {
      if (isTruffleException(exception)) {
         try {
            return InteropLibrary.getUncached().asString(InteropLibrary.getUncached().getExceptionMessage(exception));
         } catch (UnsupportedMessageException var2) {
         }
      }

      return exception.getLocalizedMessage();
   }

   void setSuspendedEvent(SuspendedEvent suspendedEvent) {
      assert this.session == suspendedEvent.getSession();

      if (this.catchLocation != null) {
         this.catchLocation.setSuspendedEvent(suspendedEvent);
      }

      this.suspendedEvent = suspendedEvent;
   }

   Throwable getRawException() {
      return this.exception;
   }

   @Override
   public Throwable fillInStackTrace() {
      return this;
   }

   @Override
   public void setStackTrace(StackTraceElement[] stackTrace) {
      for (int i = 0; i < stackTrace.length; i++) {
         if (stackTrace[i] == null) {
            throw new NullPointerException("stackTrace[" + i + "]");
         }
      }
   }

   private StackTraceElement[] getRawStackTrace() {
      return this.rawStackTrace != null ? this.rawStackTrace : super.getStackTrace();
   }

   @Override
   public StackTraceElement[] getStackTrace() {
      if (this.javaLikeStackTrace == null) {
         if (this.isInternalError()) {
            return this.getRawStackTrace();
         }

         List<DebugStackTraceElement> debugStack = this.getDebugStackTrace();
         int size = debugStack.size();
         this.javaLikeStackTrace = new StackTraceElement[size];

         for (int i = 0; i < size; i++) {
            this.javaLikeStackTrace[i] = debugStack.get(i).toTraceElement();
         }
      }

      return (StackTraceElement[])this.javaLikeStackTrace.clone();
   }

   public List<DebugStackTraceElement> getDebugStackTrace() {
      if (this.debugStackTrace == null) {
         if (this.exception != null) {
            List<TruffleStackTraceElement> stackTrace = TruffleStackTrace.getStackTrace(this.exception);
            int n = stackTrace.size();
            List<DebugStackTraceElement> debugStack = new ArrayList<>(n);
            boolean hostInfo = this.session.isShowHostStackFrames();

            for (int i = 0; i < n; i++) {
               TruffleStackTraceElement tframe = stackTrace.get(i);
               RootNode root = tframe.getTarget().getRootNode();
               if (root.getLanguageInfo() != null) {
                  debugStack.add(new DebugStackTraceElement(this.session, tframe));
               } else if (hostInfo) {
                  debugStack.add(null);
               }
            }

            if (hostInfo) {
               StackTraceElement[] stack = SuspendedEvent.cutToHostDepth(this.getRawStackTrace());
               Iterator<DebugStackTraceElement> mergedElements = Debugger.ACCESSOR
                  .engineSupport()
                  .mergeHostGuestFrames(
                     this.session.getDebugger().getEnv(), stack, debugStack.iterator(), true, new Function<StackTraceElement, DebugStackTraceElement>() {
                        public DebugStackTraceElement apply(StackTraceElement element) {
                           return new DebugStackTraceElement(DebugException.this.session, element);
                        }
                     }, Function.identity()
                  );
               List<DebugStackTraceElement> elementsList = new ArrayList<>();

               while (mergedElements.hasNext()) {
                  elementsList.add(mergedElements.next());
               }

               this.debugStackTrace = Collections.unmodifiableList(elementsList);
            } else {
               this.debugStackTrace = Collections.unmodifiableList(debugStack);
            }
         } else {
            this.debugStackTrace = Collections.emptyList();
         }
      }

      return this.debugStackTrace;
   }

   public List<List<DebugStackTraceElement>> getDebugAsynchronousStacks() {
      if (this.debugAsyncStacks == null) {
         int size = this.getDebugStackTrace().size();
         if (size == 0) {
            return Collections.emptyList();
         }

         this.debugAsyncStacks = new SuspendedEvent.DebugAsyncStackFrameLists(this.session, this.getDebugStackTrace());
      }

      return this.debugAsyncStacks;
   }

   @Override
   public void printStackTrace() {
      this.printStackTrace(new PrintStream(this.session.getDebugger().getEnv().err()));
   }

   @Override
   public void printStackTrace(PrintStream s) {
      super.printStackTrace(s);
      if (!isTruffleException(this.exception)) {
         s.print("Caused by: ");
         this.exception.printStackTrace(s);
      }
   }

   @Override
   public void printStackTrace(PrintWriter s) {
      super.printStackTrace(s);
      if (!isTruffleException(this.exception)) {
         s.print("Caused by: ");
         this.exception.printStackTrace(s);
      }
   }

   public boolean isInternalError() {
      if (!isTruffleException(this.exception)) {
         return this.exception instanceof DebugException ? ((DebugException)this.exception).isInternalError() : true;
      } else {
         return false;
      }
   }

   public DebugValue getExceptionObject() {
      if (!isTruffleException(this.exception)) {
         return null;
      } else {
         LanguageInfo language = this.preferredLanguage;
         if (language == null && this.throwLocation != null) {
            RootNode throwRoot = this.throwLocation.getRootNode();
            if (throwRoot != null) {
               language = throwRoot.getLanguageInfo();
            }
         }

         return new DebugValue.HeapValue(this.session, language, null, this.exception);
      }
   }

   public SourceSection getThrowLocation() {
      InteropLibrary interop = InteropLibrary.getUncached();
      if (interop.isException(this.exception) && interop.hasSourceLocation(this.exception)) {
         try {
            return interop.getSourceLocation(this.exception);
         } catch (UnsupportedMessageException var3) {
            CompilerDirectives.shouldNotReachHere(var3);
         }
      }

      return this.throwLocation != null ? this.throwLocation.getSourceSection() : null;
   }

   public DebugException.CatchLocation getCatchLocation() {
      if (!this.isCatchNodeComputed) {
         synchronized (this) {
            if (!this.isCatchNodeComputed) {
               if (isTruffleException(this.exception)) {
                  this.catchLocation = BreakpointExceptionFilter.getCatchNode(this.throwLocation, this.exception);
                  if (this.catchLocation != null) {
                     this.catchLocation.setSuspendedEvent(this.suspendedEvent);
                     this.catchLocation = this.catchLocation.cloneFor(this.session);
                  }
               }

               this.isCatchNodeComputed = true;
            }
         }
      }

      return this.catchLocation;
   }

   public Throwable getRawException(Class<? extends TruffleLanguage<?>> languageClass) {
      Objects.requireNonNull(languageClass);
      RootNode rootNode = this.getThrowLocationNode().getRootNode();
      if (rootNode == null) {
         return null;
      } else {
         TruffleLanguage<?> language = Debugger.ACCESSOR.nodeSupport().getLanguage(rootNode);
         return language != null && language.getClass() == languageClass ? this.getRawException() : null;
      }
   }

   Node getThrowLocationNode() {
      return this.throwLocation;
   }

   private static boolean isTruffleException(Throwable t) {
      return t != null && InteropLibrary.getUncached().isException(t);
   }

   public static final class CatchLocation {
      private final DebuggerSession session;
      private final SourceSection section;
      private final FrameInstance frameInstance;
      private final int depth;
      private DebugStackFrame frame;

      CatchLocation(SourceSection section, FrameInstance frameInstance, int depth) {
         this(null, section, frameInstance, depth);
      }

      private CatchLocation(DebuggerSession session, SourceSection section, FrameInstance frameInstance, int depth) {
         this.session = session;
         this.section = section;
         this.frameInstance = frameInstance;
         this.depth = depth;
      }

      public SourceSection getSourceSection() {
         return this.session.resolveSection(this.section);
      }

      public DebugStackFrame getFrame() {
         return this.frame;
      }

      void setSuspendedEvent(SuspendedEvent suspendedEvent) {
         assert this.session == null || this.session == suspendedEvent.getSession();

         this.frame = new DebugStackFrame(suspendedEvent, this.depth == 0 ? null : this.frameInstance, this.depth);
      }

      private DebugException.CatchLocation cloneFor(DebuggerSession ds) {
         assert this.session == null;

         DebugException.CatchLocation clon = new DebugException.CatchLocation(ds, this.section, this.frameInstance, this.depth);
         if (this.frame != null) {
            assert ds == this.frame.event.getSession();

            clon.frame = this.frame;
         }

         return clon;
      }
   }
}
