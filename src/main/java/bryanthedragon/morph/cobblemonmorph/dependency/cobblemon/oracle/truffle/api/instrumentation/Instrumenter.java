package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class Instrumenter {
   Instrumenter() {
   }

   public final <T extends ExecutionEventListener> EventBinding<T> attachExecutionEventListener(SourceSectionFilter eventFilter, T listener) {
      return this.attachExecutionEventListener(eventFilter, null, listener);
   }

   public final <T extends ExecutionEventNodeFactory> EventBinding<T> attachExecutionEventFactory(SourceSectionFilter eventFilter, T factory) {
      return this.attachExecutionEventFactory(eventFilter, null, factory);
   }

   @Deprecated(since = "20.0")
   public abstract <T extends ExecutionEventListener> EventBinding<T> attachExecutionEventListener(
      SourceSectionFilter eventFilter, SourceSectionFilter inputFilter, T listener
   );

   public abstract <T extends ExecutionEventNodeFactory> EventBinding<T> attachExecutionEventFactory(
      SourceSectionFilter eventFilter, SourceSectionFilter inputFilter, T factory
   );

   @Deprecated(since = "19.0")
   public abstract <T extends LoadSourceListener> EventBinding<T> attachLoadSourceListener(
      SourceSectionFilter filter, T listener, boolean includeExistingSources
   );

   public abstract <T extends LoadSourceListener> EventBinding<T> attachLoadSourceListener(SourceFilter filter, T listener, boolean includeExistingSources);

   public abstract <T extends ExecuteSourceListener> EventBinding<T> attachExecuteSourceListener(
      SourceFilter filter, T listener, boolean includeExecutedSources
   );

   public abstract <T extends LoadSourceSectionListener> EventBinding<T> attachLoadSourceSectionListener(
      SourceSectionFilter filter, T listener, boolean includeExistingSourceSections
   );

   public abstract <T extends LoadSourceListener> EventBinding<T> createLoadSourceBinding(SourceFilter filter, T listener, boolean includeExistingSources);

   public abstract <T extends ExecuteSourceListener> EventBinding<T> createExecuteSourceBinding(SourceFilter filter, T listener, boolean includeExecutedSources);

   public abstract <T extends LoadSourceSectionListener> EventBinding<T> createLoadSourceSectionBinding(
      SourceSectionFilter filter, T listener, boolean includeExistingSourceSections
   );

   public abstract void visitLoadedSourceSections(SourceSectionFilter filter, LoadSourceSectionListener listener);

   public abstract <T extends OutputStream> EventBinding<T> attachOutConsumer(T stream);

   public abstract <T extends OutputStream> EventBinding<T> attachErrConsumer(T stream);

   public abstract <T extends AllocationListener> EventBinding<T> attachAllocationListener(AllocationEventFilter filter, T listener);

   public abstract <T extends ContextsListener> EventBinding<T> attachContextsListener(T listener, boolean includeActiveContexts);

   public abstract <T extends ThreadsListener> EventBinding<T> attachThreadsListener(T listener, boolean includeInitializedThreads);

   public abstract EventBinding<? extends ThreadsActivationListener> attachThreadsActivationListener(ThreadsActivationListener listener);

   public final List<SourceSection> querySourceSections(SourceSectionFilter filter) {
      final List<SourceSection> sourceSectionList = new ArrayList<>();
      this.visitLoadedSourceSections(filter, new LoadSourceSectionListener() {
         @Override
         public void onLoad(LoadSourceSectionEvent event) {
            sourceSectionList.add(event.getSourceSection());
         }
      });
      return Collections.unmodifiableList(sourceSectionList);
   }

   public abstract Set<Class<?>> queryTags(Node node);

   public abstract ExecutionEventNode lookupExecutionEventNode(Node node, EventBinding<?> binding);
}
