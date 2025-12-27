package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.ProbeNode;

abstract class SteppingStrategy {
   private boolean consumed;
   private SteppingStrategy next;

   void consume() {
      this.consumed = true;
   }

   boolean isConsumed() {
      return this.consumed;
   }

   void notifyCallEntry() {
   }

   void notifyCallExit() {
   }

   void notifyNodeEntry(EventContext context) {
   }

   void notifyNodeExit(EventContext context) {
   }

   Object notifyOnUnwind() {
      return null;
   }

   boolean isStopAfterCall() {
      return true;
   }

   boolean isCollectingInputValues() {
      return false;
   }

   final boolean isActiveOnStepTo(EventContext context, SuspendAnchor suspendAnchor) {
      if (SuspendAnchor.BEFORE == suspendAnchor) {
         this.notifyNodeEntry(context);
      } else {
         this.notifyNodeExit(context);
      }

      return this.isActive(context, suspendAnchor);
   }

   boolean isActive(EventContext context, SuspendAnchor suspendAnchor) {
      return true;
   }

   abstract boolean step(DebuggerSession steppingSession, EventContext context, SuspendAnchor suspendAnchor);

   void initialize(SuspendedContext context, SuspendAnchor suspendAnchor) {
   }

   boolean isDone() {
      return false;
   }

   boolean isUnwind() {
      return false;
   }

   boolean isKill() {
      return false;
   }

   boolean isComposable() {
      return false;
   }

   void add(SteppingStrategy nextStrategy) {
      throw new UnsupportedOperationException("Not composable.");
   }

   static SteppingStrategy createKill() {
      return new SteppingStrategy.Kill();
   }

   static SteppingStrategy createAlwaysHalt() {
      return new SteppingStrategy.AlwaysHalt();
   }

   static SteppingStrategy createContinue() {
      return new SteppingStrategy.Continue();
   }

   static SteppingStrategy createStepInto(DebuggerSession session, StepConfig config) {
      return new SteppingStrategy.StepInto(session, config);
   }

   static SteppingStrategy createStepOut(DebuggerSession session, StepConfig config) {
      return new SteppingStrategy.StepOut(session, config);
   }

   static SteppingStrategy createStepOver(DebuggerSession session, StepConfig config) {
      return new SteppingStrategy.StepOver(session, config);
   }

   static SteppingStrategy createUnwind(int depth, DebugValue returnValue) {
      return new SteppingStrategy.Unwind(depth, returnValue);
   }

   static SteppingStrategy createComposed(SteppingStrategy strategy1, SteppingStrategy strategy2) {
      return new SteppingStrategy.ComposedStrategy(strategy1, strategy2);
   }

   private static final class AlwaysHalt extends SteppingStrategy {
      @Override
      boolean isActive(EventContext context, SuspendAnchor suspendAnchor) {
         return SuspendAnchor.BEFORE == suspendAnchor;
      }

      @Override
      boolean step(DebuggerSession steppingSession, EventContext context, SuspendAnchor suspendAnchor) {
         return SuspendAnchor.BEFORE == suspendAnchor;
      }

      @Override
      public String toString() {
         return "HALT";
      }
   }

   static final class ComposedStrategy extends SteppingStrategy {
      private final SteppingStrategy first;
      private SteppingStrategy last;
      private SteppingStrategy current;

      private ComposedStrategy(SteppingStrategy strategy1, SteppingStrategy strategy2) {
         strategy1.next = strategy2;
         this.first = strategy1;
         this.current = this.first;
         this.last = strategy2;
      }

      @Override
      void initialize(SuspendedContext contex, SuspendAnchor suspendAnchor) {
         assert this.current == this.first;

         this.current.initialize(contex, suspendAnchor);
      }

      @Override
      void notifyCallEntry() {
         this.current.notifyCallEntry();
      }

      @Override
      void notifyCallExit() {
         this.current.notifyCallExit();
      }

      @Override
      void notifyNodeEntry(EventContext context) {
         this.current.notifyNodeEntry(context);
      }

      @Override
      void notifyNodeExit(EventContext context) {
         this.current.notifyNodeExit(context);
      }

      @Override
      boolean isStopAfterCall() {
         return this.current.isStopAfterCall();
      }

      @Override
      boolean isActive(EventContext context, SuspendAnchor suspendAnchor) {
         return this.current.isActive(context, suspendAnchor);
      }

      @Override
      boolean step(DebuggerSession steppingSession, EventContext context, SuspendAnchor suspendAnchor) {
         boolean hit = this.current.step(steppingSession, context, suspendAnchor);
         if (hit) {
            if (this.current == this.last) {
               return true;
            }

            this.current = this.current.next;
            this.current.initialize(SuspendedContext.create(context, steppingSession.getDebugger().getEnv()), suspendAnchor);
         }

         return false;
      }

      @Override
      void consume() {
         assert this.current == this.last;

         this.last.consume();
      }

      @Override
      boolean isConsumed() {
         assert this.current == this.last;

         return this.last.isConsumed();
      }

      @Override
      boolean isDone() {
         return this.current == this.last ? this.last.isDone() : false;
      }

      @Override
      boolean isKill() {
         return this.current == this.last ? this.last.isKill() : false;
      }

      @Override
      boolean isComposable() {
         return true;
      }

      @Override
      synchronized void add(SteppingStrategy nextStrategy) {
         this.last.next = nextStrategy;
         this.last = nextStrategy;
      }

      @Override
      public String toString() {
         StringBuilder all = new StringBuilder();

         for (SteppingStrategy s = this.first; s.next != null; s = s.next) {
            if (all.length() > 0) {
               all.append(", ");
            }

            all.append(s.toString());
         }

         return "COMPOSED(" + all + ")";
      }
   }

   private static final class Continue extends SteppingStrategy {
      @Override
      boolean step(DebuggerSession steppingSession, EventContext context, SuspendAnchor suspendAnchor) {
         return false;
      }

      @Override
      public boolean isDone() {
         return true;
      }

      @Override
      public String toString() {
         return "CONTINUE";
      }
   }

   private static final class Kill extends SteppingStrategy {
      @Override
      boolean step(DebuggerSession steppingSession, EventContext context, SuspendAnchor suspendAnchor) {
         return true;
      }

      @Override
      public boolean isDone() {
         return true;
      }

      @Override
      public boolean isKill() {
         return true;
      }

      @Override
      public String toString() {
         return "KILL";
      }
   }

   private static final class StepInto extends SteppingStrategy {
      private final DebuggerSession session;
      private final StepConfig stepConfig;
      private int stackCounter;
      private int unfinishedStepCount;

      StepInto(DebuggerSession session, StepConfig stepConfig) {
         this.session = session;
         this.stepConfig = stepConfig;
         this.unfinishedStepCount = stepConfig.getCount();
      }

      @Override
      void initialize(SuspendedContext context, SuspendAnchor suspendAnchor) {
         this.stackCounter = 0;
      }

      @Override
      void notifyCallEntry() {
         this.stackCounter++;
      }

      @Override
      void notifyCallExit() {
         this.stackCounter--;
      }

      @Override
      boolean isStopAfterCall() {
         return this.stackCounter < 0;
      }

      @Override
      boolean isCollectingInputValues() {
         return this.stepConfig.containsSourceElement(this.session, SourceElement.EXPRESSION);
      }

      @Override
      boolean isActive(EventContext context, SuspendAnchor suspendAnchor) {
         return this.stepConfig.matches(this.session, context, suspendAnchor);
      }

      @Override
      boolean step(DebuggerSession steppingSession, EventContext context, SuspendAnchor suspendAnchor) {
         if (this.stepConfig.matches(this.session, context, suspendAnchor) || SuspendAnchor.AFTER == suspendAnchor && this.stackCounter < 0) {
            this.stackCounter = 0;
            if (--this.unfinishedStepCount <= 0) {
               return true;
            }
         }

         return false;
      }

      @Override
      public String toString() {
         return String.format("STEP_INTO(stackCounter=%s, stepCount=%s)", this.stackCounter, this.unfinishedStepCount);
      }
   }

   private static final class StepOut extends SteppingStrategy {
      private final DebuggerSession session;
      private final StepConfig stepConfig;
      private final boolean exprStepping;
      private int stackCounter;
      private int exprCounter;
      private int unfinishedStepCount;
      private boolean activeFrame = false;
      private boolean activeExpression = false;

      StepOut(DebuggerSession session, StepConfig stepConfig) {
         this.session = session;
         this.stepConfig = stepConfig;
         this.exprStepping = stepConfig.containsSourceElement(session, SourceElement.EXPRESSION);
         this.unfinishedStepCount = stepConfig.getCount();
      }

      @Override
      void initialize(SuspendedContext context, SuspendAnchor suspendAnchor) {
         this.stackCounter = 0;
         this.exprCounter = 0;
      }

      @Override
      void notifyCallEntry() {
         this.stackCounter++;
         this.activeFrame = false;
      }

      @Override
      void notifyCallExit() {
         boolean isOn = --this.stackCounter < 0;
         if (isOn) {
            this.activeFrame = true;
         }
      }

      @Override
      void notifyNodeEntry(EventContext context) {
         if (this.exprStepping && context.hasTag(SourceElement.EXPRESSION.getTag())) {
            this.exprCounter++;
            this.activeExpression = false;
         }
      }

      @Override
      void notifyNodeExit(EventContext context) {
         if (this.exprStepping && context.hasTag(SourceElement.EXPRESSION.getTag())) {
            boolean isOn = --this.exprCounter < 0;
            if (isOn) {
               this.activeExpression = true;
            }
         }
      }

      @Override
      boolean isStopAfterCall() {
         return this.activeFrame;
      }

      @Override
      boolean isCollectingInputValues() {
         return this.stepConfig.containsSourceElement(this.session, SourceElement.EXPRESSION);
      }

      @Override
      boolean isActive(EventContext context, SuspendAnchor suspendAnchor) {
         return (this.activeFrame || this.activeExpression) && this.stepConfig.matches(this.session, context, suspendAnchor);
      }

      @Override
      boolean step(DebuggerSession steppingSession, EventContext context, SuspendAnchor suspendAnchor) {
         this.stackCounter = 0;
         this.exprCounter = 0;
         if (--this.unfinishedStepCount <= 0) {
            return true;
         } else {
            this.activeFrame = false;
            return false;
         }
      }

      @Override
      public String toString() {
         return String.format("STEP_OUT(stackCounter=%s, stepCount=%s)", this.stackCounter, this.unfinishedStepCount);
      }
   }

   private static final class StepOver extends SteppingStrategy {
      private final DebuggerSession session;
      private final StepConfig stepConfig;
      private final boolean exprStepping;
      private int stackCounter;
      private int exprCounter;
      private int unfinishedStepCount;
      private boolean activeFrame = true;
      private boolean activeExpression = true;

      StepOver(DebuggerSession session, StepConfig stepConfig) {
         this.session = session;
         this.stepConfig = stepConfig;
         this.exprStepping = stepConfig.containsSourceElement(session, SourceElement.EXPRESSION);
         this.unfinishedStepCount = stepConfig.getCount();
      }

      @Override
      void initialize(SuspendedContext context, SuspendAnchor suspendAnchor) {
         this.stackCounter = 0;
         this.exprCounter = context.hasTag(SourceElement.EXPRESSION.getTag()) && SuspendAnchor.BEFORE == suspendAnchor ? 0 : -1;
      }

      @Override
      void notifyCallEntry() {
         this.stackCounter++;
         this.activeFrame = this.stackCounter <= 0;
      }

      @Override
      void notifyCallExit() {
         boolean isOn = --this.stackCounter <= 0;
         if (isOn) {
            this.activeFrame = true;
         }
      }

      @Override
      void notifyNodeEntry(EventContext context) {
         if (this.exprStepping && context.hasTag(SourceElement.EXPRESSION.getTag())) {
            this.exprCounter++;
            this.activeExpression = this.exprCounter <= 0;
         }
      }

      @Override
      void notifyNodeExit(EventContext context) {
         if (this.exprStepping && context.hasTag(SourceElement.EXPRESSION.getTag())) {
            boolean isOn = --this.exprCounter < 0;
            if (isOn) {
               this.activeExpression = true;
            }
         }
      }

      @Override
      boolean isStopAfterCall() {
         return this.stackCounter < 0;
      }

      @Override
      boolean isCollectingInputValues() {
         return this.stepConfig.containsSourceElement(this.session, SourceElement.EXPRESSION);
      }

      @Override
      boolean isActive(EventContext context, SuspendAnchor suspendAnchor) {
         return this.activeFrame && this.activeExpression && this.stepConfig.matches(this.session, context, suspendAnchor);
      }

      @Override
      boolean step(DebuggerSession steppingSession, EventContext context, SuspendAnchor suspendAnchor) {
         if (this.stepConfig.matches(this.session, context, suspendAnchor)
            || SuspendAnchor.AFTER == suspendAnchor && (this.stackCounter < 0 || this.exprCounter < 0)) {
            this.stackCounter = 0;
            this.exprCounter = context.hasTag(SourceElement.EXPRESSION.getTag()) && SuspendAnchor.BEFORE == suspendAnchor ? 0 : -1;
            return --this.unfinishedStepCount <= 0;
         } else {
            return false;
         }
      }

      @Override
      public String toString() {
         return String.format("STEP_OVER(stackCounter=%s, stepCount=%s)", this.stackCounter, this.unfinishedStepCount);
      }
   }

   static final class Unwind extends SteppingStrategy {
      private final int depth;
      private final DebugValue returnValue;
      private int stackCounter;
      ThreadDeath unwind;

      Unwind(int depth, DebugValue returnValue) {
         this.depth = -depth;
         this.returnValue = returnValue;
      }

      @Override
      void initialize(SuspendedContext context, SuspendAnchor suspendAnchor) {
         this.stackCounter = 1;
      }

      @Override
      void notifyCallEntry() {
         this.stackCounter++;
      }

      @Override
      void notifyCallExit() {
         this.stackCounter--;
      }

      @Override
      Object notifyOnUnwind() {
         if (this.depth == this.stackCounter) {
            return this.returnValue != null ? this.returnValue.get() : ProbeNode.UNWIND_ACTION_REENTER;
         } else {
            return null;
         }
      }

      @Override
      boolean isActive(EventContext context, SuspendAnchor suspendAnchor) {
         return SuspendAnchor.BEFORE == suspendAnchor;
      }

      @Override
      boolean step(DebuggerSession steppingSession, EventContext context, SuspendAnchor suspendAnchor) {
         return true;
      }

      @Override
      boolean isUnwind() {
         return true;
      }

      @Override
      boolean isStopAfterCall() {
         return false;
      }

      @Override
      public String toString() {
         return String.format("REENTER(stackCounter=%s, depth=%s)", this.stackCounter, this.depth);
      }
   }
}
