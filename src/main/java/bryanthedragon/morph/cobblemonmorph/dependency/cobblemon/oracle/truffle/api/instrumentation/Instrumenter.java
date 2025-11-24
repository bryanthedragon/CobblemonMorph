
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.instrumentation.AllocationEventFilter;
import com.oracle.truffle.api.instrumentation.AllocationListener;
import com.oracle.truffle.api.instrumentation.ContextsListener;
import com.oracle.truffle.api.instrumentation.EventBinding;
import com.oracle.truffle.api.instrumentation.ExecuteSourceListener;
import com.oracle.truffle.api.instrumentation.ExecutionEventListener;
import com.oracle.truffle.api.instrumentation.ExecutionEventNode;
import com.oracle.truffle.api.instrumentation.ExecutionEventNodeFactory;
import com.oracle.truffle.api.instrumentation.LoadSourceListener;
import com.oracle.truffle.api.instrumentation.LoadSourceSectionEvent;
import com.oracle.truffle.api.instrumentation.LoadSourceSectionListener;
import com.oracle.truffle.api.instrumentation.SourceFilter;
import com.oracle.truffle.api.instrumentation.SourceSectionFilter;
import com.oracle.truffle.api.instrumentation.ThreadsActivationListener;
import com.oracle.truffle.api.instrumentation.ThreadsListener;
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

    @Deprecated(since="20.0")
    public abstract <T extends ExecutionEventListener> EventBinding<T> attachExecutionEventListener(SourceSectionFilter var1, SourceSectionFilter var2, T var3);

    public abstract <T extends ExecutionEventNodeFactory> EventBinding<T> attachExecutionEventFactory(SourceSectionFilter var1, SourceSectionFilter var2, T var3);

    @Deprecated(since="19.0")
    public abstract <T extends LoadSourceListener> EventBinding<T> attachLoadSourceListener(SourceSectionFilter var1, T var2, boolean var3);

    public abstract <T extends LoadSourceListener> EventBinding<T> attachLoadSourceListener(SourceFilter var1, T var2, boolean var3);

    public abstract <T extends ExecuteSourceListener> EventBinding<T> attachExecuteSourceListener(SourceFilter var1, T var2, boolean var3);

    public abstract <T extends LoadSourceSectionListener> EventBinding<T> attachLoadSourceSectionListener(SourceSectionFilter var1, T var2, boolean var3);

    public abstract <T extends LoadSourceListener> EventBinding<T> createLoadSourceBinding(SourceFilter var1, T var2, boolean var3);

    public abstract <T extends ExecuteSourceListener> EventBinding<T> createExecuteSourceBinding(SourceFilter var1, T var2, boolean var3);

    public abstract <T extends LoadSourceSectionListener> EventBinding<T> createLoadSourceSectionBinding(SourceSectionFilter var1, T var2, boolean var3);

    public abstract void visitLoadedSourceSections(SourceSectionFilter var1, LoadSourceSectionListener var2);

    public abstract <T extends OutputStream> EventBinding<T> attachOutConsumer(T var1);

    public abstract <T extends OutputStream> EventBinding<T> attachErrConsumer(T var1);

    public abstract <T extends AllocationListener> EventBinding<T> attachAllocationListener(AllocationEventFilter var1, T var2);

    public abstract <T extends ContextsListener> EventBinding<T> attachContextsListener(T var1, boolean var2);

    public abstract <T extends ThreadsListener> EventBinding<T> attachThreadsListener(T var1, boolean var2);

    public abstract EventBinding<? extends ThreadsActivationListener> attachThreadsActivationListener(ThreadsActivationListener var1);

    public final List<SourceSection> querySourceSections(SourceSectionFilter filter) {
        final ArrayList sourceSectionList = new ArrayList();
        this.visitLoadedSourceSections(filter, new LoadSourceSectionListener(){

            @Override
            public void onLoad(LoadSourceSectionEvent event) {
                sourceSectionList.add(event.getSourceSection());
            }
        });
        return Collections.unmodifiableList(sourceSectionList);
    }

    public abstract Set<Class<?>> queryTags(Node var1);

    public abstract ExecutionEventNode lookupExecutionEventNode(Node var1, EventBinding<?> var2);
}

