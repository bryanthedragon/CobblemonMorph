package com.oracle.truffle.api;

import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.frame.FrameInstanceVisitor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.nodes.ControlFlowException;
import com.oracle.truffle.api.nodes.Node;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.graalvm.polyglot.PolyglotException;
import sun.misc.Unsafe;

public final class TruffleStackTrace extends Exception {
   private static final long causeFieldIndex;
   private static final Unsafe UNSAFE;
   private static final TruffleStackTrace EMPTY;
   private List<TruffleStackTraceElement> frames;
   private final int lazyFrames;
   private Exception materializedHostException;

   private static Throwable getCause(Throwable t) {
      try {
         Throwable result = (Throwable)UNSAFE.getObject(t, causeFieldIndex);
         return result == t ? null : result;
      } catch (IllegalArgumentException var2) {
         CompilerDirectives.transferToInterpreter();
         throw new RuntimeException(var2);
      }
   }

   private static void initCause(Throwable t, Throwable value) {
      try {
         UNSAFE.putObject(t, causeFieldIndex, value);
      } catch (IllegalArgumentException var3) {
         CompilerDirectives.transferToInterpreter();
         throw new RuntimeException(var3);
      }
   }

   private TruffleStackTrace(List<TruffleStackTraceElement> frames, int lazyFrames) {
      this.frames = frames;
      this.lazyFrames = lazyFrames;
   }

   private void materializeHostException() {
      if (this.materializedHostException == null) {
         this.materializedHostException = new Exception();
      }
   }

   @Override
   public Throwable fillInStackTrace() {
      return this;
   }

   StackTraceElement[] getInternalStackTrace() {
      Throwable hostException = this.materializedHostException;
      if (hostException == null) {
         hostException = this;
      }

      StackTraceElement[] hostFrames = hostException.getStackTrace();
      if (this.lazyFrames == 0) {
         return hostFrames;
      } else {
         StackTraceElement[] extended = new StackTraceElement[hostFrames.length + this.lazyFrames];
         System.arraycopy(hostFrames, 0, extended, this.lazyFrames, hostFrames.length);
         return extended;
      }
   }

   @Override
   public String toString() {
      return "Attached Guest Language Frames (" + this.frames.size() + ")";
   }

   @CompilerDirectives.TruffleBoundary
   public static List<TruffleStackTraceElement> getStackTrace(Throwable throwable) {
      TruffleStackTrace stack = fillIn(throwable);
      return stack != null ? stack.frames : null;
   }

   @CompilerDirectives.TruffleBoundary
   public static List<TruffleStackTraceElement> getAsynchronousStackTrace(CallTarget target, Frame frame) {
      Objects.requireNonNull(target, "CallTarget must not be null");
      Objects.requireNonNull(frame, "Frame must not be null");

      assert LanguageAccessor.ENGINE.hasCurrentContext();

      return LanguageAccessor.ACCESSOR.nodeSupport().findAsynchronousFrames(target, frame);
   }

   static void materializeHostFrames(Throwable t) {
      TruffleStackTrace stack = fillIn(t);
      if (stack != null) {
         stack.materializeHostException();
      }
   }

   private static TruffleStackTrace.LazyStackTrace findImpl(Throwable t) {
      assert !(t instanceof ControlFlowException);

      for (Throwable cause = getCause(t); cause != null; cause = getCause(cause)) {
         if (cause instanceof TruffleStackTrace.LazyStackTrace) {
            return (TruffleStackTrace.LazyStackTrace)cause;
         }
      }

      return null;
   }

   private static Throwable findInsertCause(Throwable t) {
      Throwable lastException = t;

      while (lastException != null) {
         Throwable parentCause = getCause(lastException);
         if (parentCause == null) {
            break;
         }

         lastException = parentCause;
      }

      return lastException;
   }

   private static void insert(Throwable t, TruffleStackTrace.LazyStackTrace trace) {
      if (getCause(t) != null) {
         CompilerDirectives.transferToInterpreter();
      } else {
         initCause(t, trace);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static TruffleStackTrace fillIn(Throwable throwable) {
      if (throwable instanceof ControlFlowException) {
         return EMPTY;
      } else {
         TruffleStackTrace.LazyStackTrace lazy = getOrCreateLazyStackTrace(throwable);
         if (lazy.stackTrace != null) {
            return lazy.stackTrace;
         } else {
            int stackFrameLimit;
            Node topCallSite;
            if (LanguageAccessor.EXCEPTIONS.isException(throwable)) {
               topCallSite = LanguageAccessor.EXCEPTIONS.getLocation(throwable);
               stackFrameLimit = LanguageAccessor.EXCEPTIONS.getStackTraceElementLimit(throwable);
            } else {
               topCallSite = null;
               stackFrameLimit = -1;
            }

            ArrayList<TruffleStackTrace.TracebackElement> elements = new ArrayList<>();

            for (TruffleStackTrace.TracebackElement currentElement = lazy.current; currentElement != null; currentElement = currentElement.last) {
               elements.add(currentElement);
            }

            Collections.reverse(elements);
            List<TruffleStackTraceElement> frames = new ArrayList<>();

            for (TruffleStackTrace.TracebackElement element : elements) {
               if (element.root != null) {
                  frames.add(new TruffleStackTraceElement(topCallSite, element.root, element.frame));
                  topCallSite = null;
               }

               if (element.callNode != null) {
                  topCallSite = element.callNode;
               }
            }

            int lazyFrames = frames.size();
            addStackFrames(stackFrameLimit, lazyFrames, topCallSite, frames);
            lazy.stackTrace = new TruffleStackTrace(frames, lazyFrames);
            if (throwable.getStackTrace().length == 0) {
               lazy.stackTrace.materializeHostException();
            }

            return lazy.stackTrace;
         }
      }
   }

   static void addStackFrameInfo(Node callNode, RootCallTarget root, Throwable t, Frame currentFrame) {
      if (!(t instanceof ControlFlowException)) {
         if (!(t instanceof PolyglotException)) {
            boolean isTProfiled = CompilerDirectives.isPartialEvaluationConstant(t.getClass());
            if (currentFrame != null && root.getRootNode().isCaptureFramesForTrace()) {
               callInnerAddStackFrameInfo(isTProfiled, callNode, root, t, currentFrame.materialize());
            } else {
               callInnerAddStackFrameInfo(isTProfiled, callNode, root, t, null);
            }
         }
      }
   }

   private static void callInnerAddStackFrameInfo(boolean isTProfiled, Node callNode, RootCallTarget root, Throwable t, MaterializedFrame currentFrame) {
      if (isTProfiled) {
         innerAddStackFrameInfo(callNode, root, t, currentFrame);
      } else {
         innerAddStackFrameInfoBoundary(callNode, root, t, currentFrame);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static void innerAddStackFrameInfoBoundary(Node callNode, RootCallTarget root, Throwable t, MaterializedFrame currentFrame) {
      innerAddStackFrameInfo(callNode, root, t, currentFrame);
   }

   private static void innerAddStackFrameInfo(Node callNode, RootCallTarget root, Throwable t, MaterializedFrame currentFrame) {
      if (!LanguageAccessor.EXCEPTIONS.isException(t)) {
         fillIn(t);
      } else {
         int stackTraceElementLimit = LanguageAccessor.EXCEPTIONS.getStackTraceElementLimit(t);
         TruffleStackTrace.LazyStackTrace lazy = (TruffleStackTrace.LazyStackTrace)LanguageAccessor.EXCEPTIONS.getLazyStackTrace(t);
         if (lazy == null) {
            lazy = new TruffleStackTrace.LazyStackTrace();
            LanguageAccessor.EXCEPTIONS.setLazyStackTrace(t, lazy);
         }

         appendLazyStackTrace(callNode, root, currentFrame, lazy, stackTraceElementLimit);
      }
   }

   private static TruffleStackTrace.LazyStackTrace getOrCreateLazyStackTrace(Throwable throwable) {
      if (LanguageAccessor.EXCEPTIONS.isException(throwable)) {
         TruffleStackTrace.LazyStackTrace lazy = (TruffleStackTrace.LazyStackTrace)LanguageAccessor.EXCEPTIONS.getLazyStackTrace(throwable);
         if (lazy == null) {
            lazy = new TruffleStackTrace.LazyStackTrace();
            LanguageAccessor.EXCEPTIONS.setLazyStackTrace(throwable, lazy);
         }

         return lazy;
      } else {
         TruffleStackTrace.LazyStackTrace lazy = findImpl(throwable);
         if (lazy == null) {
            Throwable insertCause = findInsertCause(throwable);
            if (insertCause == null) {
               return null;
            }

            insert(insertCause, lazy = new TruffleStackTrace.LazyStackTrace());
         }

         return lazy;
      }
   }

   private static void appendLazyStackTrace(
      Node callNode, RootCallTarget root, MaterializedFrame currentFrame, TruffleStackTrace.LazyStackTrace lazy, int stackTraceElementLimit
   ) {
      if (lazy.stackTrace == null) {
         if (stackTraceElementLimit >= 0 && lazy.frameCount >= stackTraceElementLimit) {
            return;
         }

         lazy.current = new TruffleStackTrace.TracebackElement(lazy.current, callNode, root, currentFrame);
         if (root != null && LanguageAccessor.ACCESSOR.nodeSupport().countsTowardsStackTraceLimit(root.getRootNode())) {
            lazy.frameCount++;
         }
      }
   }

   private static void addStackFrames(int stackFrameLimit, int lazyFrames, final Node topCallSite, List<TruffleStackTraceElement> frames) {
      if (stackFrameLimit < 0 || lazyFrames < stackFrameLimit) {
         Truffle.getRuntime().iterateFrames(new FrameInstanceVisitor<FrameInstance>() {
            boolean first = true;
            int stackFrameIndex = lazyFrames;

            public FrameInstance visitFrame(FrameInstance frameInstance) {
               if (stackFrameLimit >= 0 && this.stackFrameIndex >= stackFrameLimit) {
                  return frameInstance;
               } else {
                  Node location = frameInstance.getCallNode();
                  RootCallTarget target = (RootCallTarget)frameInstance.getCallTarget();
                  if (this.first) {
                     location = topCallSite;
                     this.first = false;
                  }

                  boolean captureFrames = target != null && target.getRootNode().isCaptureFramesForTrace();
                  Frame frame = captureFrames ? frameInstance.getFrame(FrameInstance.FrameAccess.READ_ONLY) : null;
                  frames.add(new TruffleStackTraceElement(location, target, frame));
                  this.first = false;
                  if (target != null && LanguageAccessor.ACCESSOR.nodeSupport().countsTowardsStackTraceLimit(target.getRootNode())) {
                     this.stackFrameIndex++;
                  }

                  return null;
               }
            }
         });
      }
   }

   static {
      Unsafe unsafe;
      try {
         unsafe = Unsafe.getUnsafe();
      } catch (SecurityException var5) {
         try {
            Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeInstance.setAccessible(true);
            unsafe = (Unsafe)theUnsafeInstance.get(Unsafe.class);
         } catch (Exception var4) {
            throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", var4);
         }
      }

      UNSAFE = unsafe;

      try {
         Field causeField = Throwable.class.getDeclaredField("cause");
         causeFieldIndex = ArrayUtils.getObjectFieldOffset(causeField);
      } catch (SecurityException | NoSuchFieldException var3) {
         throw new RuntimeException(var3);
      }

      EMPTY = new TruffleStackTrace(Collections.emptyList(), 0);
   }

   static final class LazyStackTrace extends Throwable {
      private TruffleStackTrace.TracebackElement current;
      private TruffleStackTrace stackTrace;
      public int frameCount;

      @Override
      public Throwable fillInStackTrace() {
         return null;
      }

      public TruffleStackTrace getInternalStackTrace() {
         return this.stackTrace;
      }

      @Override
      public Throwable initCause(Throwable cause) {
         throw new IllegalAccessError("cannot change cause of AbstractTruffleException stacktrace");
      }

      @Override
      public String toString() {
         return "Attached Guest Language Frames (" + (this.frameCount + (this.stackTrace != null ? this.stackTrace.frames.size() : 0)) + ")";
      }
   }

   private static final class TracebackElement {
      private final TruffleStackTrace.TracebackElement last;
      private final Node callNode;
      private final RootCallTarget root;
      private final MaterializedFrame frame;

      TracebackElement(TruffleStackTrace.TracebackElement last, Node callNode, RootCallTarget root, MaterializedFrame frame) {
         this.last = last;
         this.callNode = callNode;
         this.root = root;
         this.frame = frame;
      }
   }
}
