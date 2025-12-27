package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.instrumentation.EventBinding;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.io.PrintStream;

interface SuspendedContext {
   static SuspendedContext create(EventContext eventContext, TruffleInstrument.Env env) {
      return new SuspendedContext.SuspendedEventContext(eventContext, env);
   }

   static SuspendedContext create(Node node, ThreadDeath unwind) {
      return new SuspendedContext.CallerEventContext(node, unwind);
   }

   int getStackDepth();

   SourceSection getInstrumentedSourceSection();

   Node getInstrumentedNode();

   boolean hasTag(Class<? extends Tag> tag);

   boolean isLanguageContextInitialized();

   ThreadDeath createUnwind(Object info, EventBinding<?> unwindBinding);

   public static final class CallerEventContext implements SuspendedContext {
      private final Node node;
      private final ThreadDeath unwind;

      private CallerEventContext(Node node, ThreadDeath unwind) {
         this.node = node;
         this.unwind = unwind;
      }

      @Override
      public int getStackDepth() {
         return 1;
      }

      @Override
      public SourceSection getInstrumentedSourceSection() {
         return this.node == null ? null : DebugSourcesResolver.findEncapsulatedSourceSection(this.node);
      }

      @Override
      public Node getInstrumentedNode() {
         return this.node;
      }

      @Override
      public boolean hasTag(Class<? extends Tag> tag) {
         return this.node instanceof InstrumentableNode && ((InstrumentableNode)this.node).hasTag(tag);
      }

      @Override
      public boolean isLanguageContextInitialized() {
         return true;
      }

      @Override
      public ThreadDeath createUnwind(Object info, EventBinding<?> unwindBinding) {
         return this.unwind;
      }

      @Override
      public String toString() {
         return "CallerContext[source=" + this.getInstrumentedSourceSection() + "]";
      }
   }

   public static final class SuspendedEventContext implements SuspendedContext {
      private final EventContext eventContext;
      private final TruffleInstrument.Env env;

      private SuspendedEventContext(EventContext eventContext, TruffleInstrument.Env env) {
         this.eventContext = eventContext;
         this.env = env;
      }

      @Override
      public int getStackDepth() {
         return 0;
      }

      @Override
      public SourceSection getInstrumentedSourceSection() {
         SourceSection ss = this.eventContext.getInstrumentedSourceSection();
         if (ss == null) {
            Node node = this.eventContext.getInstrumentedNode();
            PrintStream err = new PrintStream(this.env.err());
            err.print("WARNING: Instrumented node " + node + " of class " + node.getClass() + " has null SourceSection.");
            ss = DebugSourcesResolver.findEncapsulatedSourceSection(node);
            if (ss == null) {
               RootNode root = node.getRootNode();
               err.print("WARNING: and null encapsulating SourceSection under " + root + " of class = " + root.getClass());
            }

            err.flush();
         }

         return ss;
      }

      @Override
      public Node getInstrumentedNode() {
         return this.eventContext.getInstrumentedNode();
      }

      @Override
      public boolean hasTag(Class<? extends Tag> tag) {
         return this.eventContext.hasTag(tag);
      }

      @Override
      public boolean isLanguageContextInitialized() {
         return this.eventContext.isLanguageContextInitialized();
      }

      @Override
      public ThreadDeath createUnwind(Object info, EventBinding<?> unwindBinding) {
         return this.eventContext.createUnwind(info, unwindBinding);
      }

      @Override
      public String toString() {
         return this.eventContext.toString();
      }
   }
}
