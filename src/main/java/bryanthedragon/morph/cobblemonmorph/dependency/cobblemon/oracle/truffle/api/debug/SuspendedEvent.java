package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleStackTrace;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.frame.FrameInstanceVisitor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public final class SuspendedEvent {
   private final SourceSection sourceSection;
   private final SuspendAnchor suspendAnchor;
   private final Thread thread;
   private DebuggerSession session;
   private SuspendedContext context;
   private MaterializedFrame materializedFrame;
   private InsertableNode insertableNode;
   private List<Breakpoint> breakpoints;
   private DebuggerNode.InputValuesProvider inputValuesProvider;
   private volatile Object returnValue;
   private DebugException exception;
   private volatile boolean disposed;
   private volatile SteppingStrategy nextStrategy;
   private final Map<Breakpoint, Throwable> conditionFailures;
   private SuspendedEvent.DebugStackFrameIterable cachedFrames;
   private List<List<DebugStackTraceElement>> cachedAsyncFrames;
   private static final String HOST_INTEROP_NODE_NAME = "com.oracle.truffle.polyglot.HostToGuestRootNode";

   SuspendedEvent(
      DebuggerSession session,
      Thread thread,
      SuspendedContext context,
      MaterializedFrame frame,
      SuspendAnchor suspendAnchor,
      InsertableNode insertableNode,
      DebuggerNode.InputValuesProvider inputValuesProvider,
      Object returnValue,
      DebugException exception,
      List<Breakpoint> breakpoints,
      Map<Breakpoint, Throwable> conditionFailures
   ) {
      Objects.requireNonNull(session, "session");
      Objects.requireNonNull(thread, "thread");
      Objects.requireNonNull(context, "context");
      Objects.requireNonNull(frame, "frame");
      Objects.requireNonNull(suspendAnchor, "suspendAnchor");
      Objects.requireNonNull(breakpoints, "breakpoints");
      Objects.requireNonNull(conditionFailures, "conditionFailures");
      this.session = session;
      this.context = context;
      this.suspendAnchor = suspendAnchor;
      this.materializedFrame = frame;
      this.insertableNode = insertableNode;
      this.inputValuesProvider = inputValuesProvider;
      this.returnValue = returnValue;
      this.exception = exception;
      this.conditionFailures = conditionFailures;
      this.breakpoints = Collections.unmodifiableList(breakpoints);
      this.thread = thread;
      this.sourceSection = context.getInstrumentedSourceSection();
   }

   boolean isDisposed() {
      return this.disposed;
   }

   void clearLeakingReferences() {
      this.disposed = true;
      this.inputValuesProvider = null;
      this.returnValue = null;
      this.exception = null;
      this.breakpoints = null;
      this.materializedFrame = null;
      this.cachedFrames = null;
      this.session = null;
      this.context = null;
      this.insertableNode = null;
   }

   void verifyValidState(boolean allowDifferentThread) {
      if (this.disposed) {
         throw new IllegalStateException("Not in a suspended state.");
      } else if (!allowDifferentThread && Thread.currentThread() != this.thread) {
         throw new IllegalStateException("Illegal thread access.");
      }
   }

   SteppingStrategy getNextStrategy() {
      SteppingStrategy strategy = this.nextStrategy;
      return strategy == null ? SteppingStrategy.createContinue() : strategy;
   }

   private synchronized void setNextStrategy(SteppingStrategy nextStrategy) {
      this.verifyValidState(true);
      if (this.nextStrategy == null) {
         this.nextStrategy = nextStrategy;
      } else {
         if (this.nextStrategy.isKill()) {
            throw new IllegalStateException("Calls to prepareKill() cannot be followed by any other preparation call.");
         }

         if (this.nextStrategy.isDone()) {
            throw new IllegalStateException("Calls to prepareContinue() cannot be followed by any other preparation call.");
         }

         if (this.nextStrategy.isComposable()) {
            this.nextStrategy.add(nextStrategy);
         } else {
            this.nextStrategy = SteppingStrategy.createComposed(this.nextStrategy, nextStrategy);
         }
      }
   }

   public DebuggerSession getSession() {
      this.verifyValidState(true);
      return this.session;
   }

   Thread getThread() {
      return this.thread;
   }

   SuspendedContext getContext() {
      return this.context;
   }

   InsertableNode getInsertableNode() {
      return this.insertableNode;
   }

   public SourceSection getSourceSection() {
      this.verifyValidState(true);
      return this.session.resolveSection(this.sourceSection);
   }

   public SuspendAnchor getSuspendAnchor() {
      this.verifyValidState(true);
      return this.suspendAnchor;
   }

   public boolean hasSourceElement(SourceElement sourceElement) {
      return this.context.hasTag(sourceElement.getTag());
   }

   public boolean isLanguageContextInitialized() {
      this.verifyValidState(true);
      return this.context.isLanguageContextInitialized();
   }

   public DebugValue[] getInputValues() {
      if (this.inputValuesProvider == null) {
         return null;
      } else {
         Object[] inputValues = this.inputValuesProvider.getDebugInputValues(this.materializedFrame);
         int n = inputValues.length;
         DebugValue[] values = new DebugValue[n];

         for (int i = 0; i < n; i++) {
            if (inputValues[i] != null) {
               values[i] = this.getTopStackFrame().wrapHeapValue(inputValues[i]);
            } else {
               values[i] = null;
            }
         }

         return values;
      }
   }

   public DebugValue getReturnValue() {
      this.verifyValidState(false);
      Object ret = this.returnValue;
      return ret == null ? null : this.getTopStackFrame().wrapHeapValue(ret);
   }

   Object getReturnObject() {
      return this.returnValue;
   }

   public void setReturnValue(DebugValue newValue) {
      this.verifyValidState(false);
      if (this.returnValue == null) {
         throw new IllegalStateException("Can not set return value when there is no current return value.");
      } else {
         this.returnValue = newValue.get();
      }
   }

   public DebugException getException() {
      return this.exception;
   }

   MaterializedFrame getMaterializedFrame() {
      return this.materializedFrame;
   }

   public Throwable getBreakpointConditionException(Breakpoint breakpoint) {
      this.verifyValidState(true);
      return this.conditionFailures.get(breakpoint);
   }

   public List<Breakpoint> getBreakpoints() {
      this.verifyValidState(true);
      return this.breakpoints;
   }

   public DebugStackFrame getTopStackFrame() {
      return this.getStackFrames().iterator().next();
   }

   public Iterable<DebugStackFrame> getStackFrames() {
      this.verifyValidState(false);
      if (this.cachedFrames == null) {
         this.cachedFrames = new SuspendedEvent.DebugStackFrameIterable(this.session.isShowHostStackFrames());
      }

      return this.cachedFrames;
   }

   public List<List<DebugStackTraceElement>> getAsynchronousStacks() {
      this.verifyValidState(false);
      if (this.cachedAsyncFrames == null) {
         this.cachedAsyncFrames = new SuspendedEvent.DebugAsyncStackFrameLists(this.session, this.getStackFrames());
      }

      return this.cachedAsyncFrames;
   }

   static boolean isEvalRootStackFrame(DebuggerSession session, FrameInstance instance) {
      CallTarget target = instance.getCallTarget();
      RootNode root = null;
      if (target instanceof RootCallTarget) {
         root = ((RootCallTarget)target).getRootNode();
      }

      return root != null && session.getDebugger().getEnv().isEngineRoot(root);
   }

   public void prepareContinue() {
      this.setNextStrategy(SteppingStrategy.createContinue());
   }

   public SuspendedEvent prepareStepInto(int stepCount) {
      return this.prepareStepInto(StepConfig.newBuilder().count(stepCount).build());
   }

   public SuspendedEvent prepareStepOut(int stepCount) {
      return this.prepareStepOut(StepConfig.newBuilder().count(stepCount).build());
   }

   public SuspendedEvent prepareStepOver(int stepCount) {
      return this.prepareStepOver(StepConfig.newBuilder().count(stepCount).build());
   }

   public SuspendedEvent prepareStepInto(StepConfig stepConfig) {
      this.verifyConfig(stepConfig);
      this.setNextStrategy(SteppingStrategy.createStepInto(this.session, stepConfig));
      return this;
   }

   public SuspendedEvent prepareStepOut(StepConfig stepConfig) {
      this.verifyConfig(stepConfig);
      this.setNextStrategy(SteppingStrategy.createStepOut(this.session, stepConfig));
      return this;
   }

   public SuspendedEvent prepareStepOver(StepConfig stepConfig) {
      this.verifyConfig(stepConfig);
      this.setNextStrategy(SteppingStrategy.createStepOver(this.session, stepConfig));
      return this;
   }

   private void verifyConfig(StepConfig stepConfig) {
      Set<SourceElement> sessionElements = this.session.getSourceElements();
      if (sessionElements.isEmpty()) {
         throw new IllegalStateException("No source elements are enabled for stepping in the debugger session.");
      } else {
         Set<SourceElement> stepElements = stepConfig.getSourceElements();
         if (stepElements != null && !sessionElements.containsAll(stepElements)) {
            Set<SourceElement> extraElements = new HashSet<>(stepElements);
            extraElements.removeAll(sessionElements);
            throw new IllegalArgumentException("The step source elements " + extraElements + " are not enabled in the session.");
         }
      }
   }

   public void prepareUnwindFrame(DebugStackFrame frame) throws IllegalArgumentException {
      this.prepareUnwindFrame(frame, null);
   }

   public void prepareUnwindFrame(DebugStackFrame frame, DebugValue immediateReturnValue) throws IllegalArgumentException {
      if (frame.event != this) {
         throw new IllegalArgumentException("The stack frame is not in the scope of this event.");
      } else {
         this.setNextStrategy(SteppingStrategy.createUnwind(frame.getDepth(), immediateReturnValue));
      }
   }

   public void prepareKill() {
      this.setNextStrategy(SteppingStrategy.createKill());
   }

   @Override
   public String toString() {
      return "Suspended at " + this.getSourceSection() + " for thread " + this.getThread();
   }

   private static Integer findHostDepth() {
      return Truffle.getRuntime().iterateFrames(new FrameInstanceVisitor<Integer>() {
         private int hostDepth = 0;

         public Integer visitFrame(FrameInstance frameInstance) {
            RootNode root = ((RootCallTarget)frameInstance.getCallTarget()).getRootNode();
            if (SuspendedEvent.instanceOf("com.oracle.truffle.polyglot.HostToGuestRootNode", root.getClass())) {
               return this.hostDepth;
            } else {
               this.hostDepth++;
               return null;
            }
         }
      });
   }

   private static boolean instanceOf(String name, Class<?> clazz) {
      if (clazz.getName().equals(name)) {
         return true;
      } else {
         Class<?> sClazz = clazz.getSuperclass();
         return sClazz != null ? instanceOf(name, sClazz) : false;
      }
   }

   static StackTraceElement[] cutToHostDepth(StackTraceElement[] stack) {
      Integer hostDepth = findHostDepth();
      if (hostDepth == null) {
         return stack;
      } else {
         int guestCutIndex = 0;

         for (int i = 0; i < stack.length; i++) {
            if ("com.oracle.truffle.polyglot.HostToGuestRootNode".equals(stack[i].getClassName())) {
               guestCutIndex = i;
               break;
            }
         }

         StackTraceElement[] newStack = new StackTraceElement[hostDepth + stack.length - guestCutIndex];
         System.arraycopy(stack, guestCutIndex, newStack, hostDepth, stack.length - guestCutIndex);
         return newStack;
      }
   }

   static final class DebugAsyncStackFrameLists extends AbstractList<List<DebugStackTraceElement>> {
      private final DebuggerSession session;
      private final List<List<DebugStackTraceElement>> stacks = new LinkedList<>();
      private int size = -1;

      DebugAsyncStackFrameLists(DebuggerSession session, Iterable<DebugStackFrame> callStack) {
         this.session = session;

         for (DebugStackFrame dFrame : callStack) {
            if (!dFrame.isHost()) {
               RootCallTarget target = dFrame.getCallTarget();
               Frame frame = dFrame.findTruffleFrame(FrameInstance.FrameAccess.READ_ONLY);
               List<DebugStackTraceElement> asyncStack = getAsynchronousStackFrames(session, target, frame);
               if (asyncStack != null && !asyncStack.isEmpty()) {
                  this.stacks.add(asyncStack);
                  break;
               }
            }
         }

         if (this.stacks.isEmpty()) {
            this.size = 0;
         }
      }

      DebugAsyncStackFrameLists(DebuggerSession session, List<DebugStackTraceElement> stackTrace) {
         this.session = session;

         for (DebugStackTraceElement tElement : stackTrace) {
            RootCallTarget target = tElement.traceElement.getTarget();
            Frame frame = tElement.traceElement.getFrame();
            List<DebugStackTraceElement> asyncStack = getAsynchronousStackFrames(session, target, frame);
            if (asyncStack != null && !asyncStack.isEmpty()) {
               this.stacks.add(asyncStack);
               break;
            }
         }

         if (this.stacks.isEmpty()) {
            this.size = 0;
         }
      }

      public List<DebugStackTraceElement> get(int index) {
         int filledLevel = this.fillStacks(index);
         if (filledLevel >= index) {
            return this.stacks.get(index);
         } else {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
         }
      }

      @Override
      public int size() {
         if (this.size < 0) {
            this.fillStacks(Integer.MAX_VALUE);
         }

         return this.size;
      }

      @Override
      public Iterator<List<DebugStackTraceElement>> iterator() {
         return new SuspendedEvent.DebugAsyncStackFrameLists.Itr();
      }

      private int fillStacks(int level) {
         int lastLevel = this.stacks.size() - 1;
         if (this.size > 0 && level >= this.size) {
            return this.size - 1;
         } else if (lastLevel >= level) {
            return level;
         } else {
            while (lastLevel < level) {
               boolean added = false;

               for (DebugStackTraceElement tElement : this.stacks.get(lastLevel)) {
                  if (!tElement.isHost()) {
                     RootCallTarget target = tElement.traceElement.getTarget();
                     Frame frame = tElement.traceElement.getFrame();
                     List<DebugStackTraceElement> asyncStack = getAsynchronousStackFrames(this.session, target, frame);
                     if (asyncStack != null && !asyncStack.isEmpty()) {
                        this.stacks.add(asyncStack);
                        added = true;
                        break;
                     }
                  }
               }

               if (!added) {
                  this.size = lastLevel + 1;
                  break;
               }

               lastLevel++;
            }

            return lastLevel;
         }
      }

      private static List<DebugStackTraceElement> getAsynchronousStackFrames(DebuggerSession session, RootCallTarget target, Frame frame) {
         if (frame == null) {
            return null;
         } else {
            List<TruffleStackTraceElement> stack = TruffleStackTrace.getAsynchronousStackTrace(target, frame);
            if (stack == null) {
               return null;
            } else {
               Iterator<TruffleStackTraceElement> stackIterator = stack.iterator();
               if (!stackIterator.hasNext()) {
                  return Collections.emptyList();
               } else {
                  List<DebugStackTraceElement> debugStack = new ArrayList<>();

                  while (stackIterator.hasNext()) {
                     TruffleStackTraceElement tframe = stackIterator.next();
                     debugStack.add(new DebugStackTraceElement(session, tframe));
                  }

                  return Collections.unmodifiableList(debugStack);
               }
            }
         }
      }

      private class Itr implements Iterator<List<DebugStackTraceElement>> {
         int cursor = 0;

         @Override
         public boolean hasNext() {
            return DebugAsyncStackFrameLists.this.fillStacks(this.cursor) == this.cursor;
         }

         public List<DebugStackTraceElement> next() {
            try {
               int i = this.cursor;
               List<DebugStackTraceElement> next = DebugAsyncStackFrameLists.this.get(i);
               this.cursor = i + 1;
               return next;
            } catch (IndexOutOfBoundsException var3) {
               throw new NoSuchElementException();
            }
         }

         @Override
         public void remove() {
            throw new UnsupportedOperationException();
         }
      }
   }

   private final class DebugStackFrameIterable implements Iterable<DebugStackFrame> {
      private final StackTraceElement[] hostStack;
      private DebugStackFrame topStackFrame;
      private List<DebugStackFrame> otherFrames;

      private DebugStackFrameIterable(boolean hostIncluded) {
         this.hostStack = hostIncluded ? SuspendedEvent.cutToHostDepth(Thread.currentThread().getStackTrace()) : null;
      }

      private DebugStackFrame getTopStackFrame() {
         if (this.topStackFrame == null) {
            this.topStackFrame = new DebugStackFrame(SuspendedEvent.this, (FrameInstance)null, 0);
         }

         return this.topStackFrame;
      }

      private List<DebugStackFrame> getOtherFrames(boolean raw) {
         if (this.otherFrames == null) {
            final List<DebugStackFrame> frameInstances = new ArrayList<>();
            Truffle.getRuntime().iterateFrames(new FrameInstanceVisitor<FrameInstance>() {
               private int depth = -SuspendedEvent.this.context.getStackDepth() - 1 + DebugStackFrameIterable.this.getTopFrameIndex();

               public FrameInstance visitFrame(FrameInstance frameInstance) {
                  if (SuspendedEvent.isEvalRootStackFrame(SuspendedEvent.this.session, frameInstance)) {
                     return frameInstance;
                  } else {
                     Node callNode = frameInstance.getCallNode();
                     if (callNode != null && !DebugStackFrameIterable.this.hasRootTag(callNode)) {
                        if (raw) {
                           frameInstances.add(null);
                        }

                        return null;
                     } else {
                        if (callNode == null) {
                           RootNode root = ((RootCallTarget)frameInstance.getCallTarget()).getRootNode();
                           if (root.getLanguageInfo() == null) {
                              if (raw) {
                                 frameInstances.add(null);
                              }

                              return null;
                           }
                        }

                        if (++this.depth <= 0) {
                           return null;
                        } else {
                           frameInstances.add(new DebugStackFrame(SuspendedEvent.this, frameInstance, this.depth));
                           return null;
                        }
                     }
                  }
               }
            });
            this.otherFrames = frameInstances;
         }

         return this.otherFrames;
      }

      private boolean hasRootTag(Node callNode) {
         Node node = callNode;

         while (!(node instanceof InstrumentableNode) || !((InstrumentableNode)node).hasTag(StandardTags.RootTag.class)) {
            node = node.getParent();
            if (node == null) {
               return false;
            }
         }

         return true;
      }

      private int getTopFrameIndex() {
         if (SuspendedEvent.this.context.getStackDepth() == 0) {
            return 0;
         } else {
            Node node = SuspendedEvent.this.context.getInstrumentedNode();
            return !(node instanceof RootNode) && !this.hasRootTag(node) ? 1 : 0;
         }
      }

      @Override
      public Iterator<DebugStackFrame> iterator() {
         if (this.hostStack != null) {
            final AtomicInteger frameDepth = new AtomicInteger(0);
            return Debugger.ACCESSOR
               .engineSupport()
               .mergeHostGuestFrames(
                  SuspendedEvent.this.session.getDebugger().getEnv(), this.hostStack, new SuspendedEvent.DebugStackFrameIterable.GuestIterator(true) {
                     @Override
                     public DebugStackFrame next() {
                        DebugStackFrame frame = super.next();
                        if (frame != null) {
                           frameDepth.set(frame.getDepth());
                        }

                        return frame;
                     }
                  }, false, new Function<StackTraceElement, DebugStackFrame>() {
                     public DebugStackFrame apply(StackTraceElement element) {
                        return new DebugStackFrame(SuspendedEvent.this, element, frameDepth.get());
                     }
                  }, Function.identity()
               );
         } else {
            return new SuspendedEvent.DebugStackFrameIterable.GuestIterator(false);
         }
      }

      private class GuestIterator implements Iterator<DebugStackFrame> {
         private final boolean raw;
         private int index = DebugStackFrameIterable.this.getTopFrameIndex();
         private Iterator<DebugStackFrame> otherIterator;

         GuestIterator(boolean raw) {
            this.raw = raw;
         }

         @Override
         public boolean hasNext() {
            SuspendedEvent.this.verifyValidState(false);
            return this.index == 0 ? true : this.getOtherStackFrames().hasNext();
         }

         public DebugStackFrame next() {
            SuspendedEvent.this.verifyValidState(false);
            if (this.index == 0) {
               this.index++;
               return DebugStackFrameIterable.this.getTopStackFrame();
            } else {
               return this.getOtherStackFrames().next();
            }
         }

         private Iterator<DebugStackFrame> getOtherStackFrames() {
            if (this.otherIterator == null) {
               this.otherIterator = DebugStackFrameIterable.this.getOtherFrames(this.raw).iterator();
            }

            return this.otherIterator;
         }
      }
   }
}
