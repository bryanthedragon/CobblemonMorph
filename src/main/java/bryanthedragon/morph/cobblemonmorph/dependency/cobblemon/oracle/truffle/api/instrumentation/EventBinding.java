
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.instrumentation.AllocationEventFilter;
import com.oracle.truffle.api.instrumentation.InstrumentationHandler;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.SourceSectionFilter;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReference;

public class EventBinding<T> {
    private final InstrumentationHandler.AbstractInstrumenter instrumenter;
    private final T element;
    private final AtomicReference<Boolean> attached;
    final Semaphore attachedSemaphore = new Semaphore(0);
    volatile boolean disposing;
    private volatile boolean disposed;

    EventBinding(InstrumentationHandler.AbstractInstrumenter instrumenter, T element) {
        this(instrumenter, element, true);
    }

    EventBinding(InstrumentationHandler.AbstractInstrumenter instrumenter, T element, boolean attached) {
        if (element == null) {
            throw new NullPointerException();
        }
        this.instrumenter = instrumenter;
        this.element = element;
        this.attached = new AtomicReference<Boolean>(attached);
        if (attached) {
            this.attachedSemaphore.release();
        }
    }

    final InstrumentationHandler.AbstractInstrumenter getInstrumenter() {
        return this.instrumenter;
    }

    public T getElement() {
        return this.element;
    }

    public final boolean isAttached() {
        return Boolean.TRUE == this.attached.get();
    }

    public final void attach() {
        Boolean wasAttached = this.attached.getAndSet(true);
        if (null == wasAttached) {
            throw new IllegalStateException("The binding is disposed. Create a new binding to attach.");
        }
        if (Boolean.TRUE == wasAttached) {
            throw new IllegalStateException("The binding is attached already.");
        }
        this.doAttach();
        this.attachedSemaphore.release();
    }

    void doAttach() {
        throw CompilerDirectives.shouldNotReachHere(this.toString() + ".doAttach()");
    }

    public boolean isDisposed() {
        return this.disposed;
    }

    public synchronized void dispose() {
        CompilerAsserts.neverPartOfCompilation();
        if (!this.disposed) {
            this.disposing = true;
            Boolean wasSet = this.attached.getAndSet(null);
            if (Boolean.TRUE == wasSet) {
                try {
                    this.attachedSemaphore.acquire();
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
            this.instrumenter.disposeBinding(this);
            this.disposed = true;
        }
    }

    synchronized void setDisposingBulk() {
        this.disposing = true;
    }

    synchronized void disposeBulk() {
        this.disposed = true;
    }

    static final class Allocation<T>
    extends EventBinding<T> {
        private final AllocationEventFilter filterAllocation;

        Allocation(InstrumentationHandler.AbstractInstrumenter instrumenter, AllocationEventFilter filter, T listener) {
            super(instrumenter, listener);
            this.filterAllocation = filter;
        }

        AllocationEventFilter getAllocationFilter() {
            return this.filterAllocation;
        }
    }

    static abstract class Source<T>
    extends EventBinding<T> {
        private final SourceSectionFilter filterSourceSection;
        private final SourceSectionFilter inputFilter;

        Source(InstrumentationHandler.AbstractInstrumenter instrumenter, SourceSectionFilter filterSourceSection, SourceSectionFilter inputFilter, T element) {
            this(instrumenter, filterSourceSection, inputFilter, element, true);
        }

        Source(InstrumentationHandler.AbstractInstrumenter instrumenter, SourceSectionFilter filterSourceSection, SourceSectionFilter inputFilter, T element, boolean attached) {
            super(instrumenter, element, attached);
            this.inputFilter = inputFilter;
            this.filterSourceSection = filterSourceSection;
        }

        SourceSectionFilter getInputFilter() {
            return this.inputFilter;
        }

        Set<Class<?>> getLimitedTags() {
            Set<Class<?>> tags = this.filterSourceSection.getLimitedTags();
            if (this.inputFilter != null) {
                Set<Class<?>> inputTags = this.inputFilter.getLimitedTags();
                if (tags == null) {
                    return inputTags;
                }
                if (inputTags == null) {
                    return tags;
                }
                if (inputTags.equals(tags)) {
                    return tags;
                }
                HashSet compoundTags = new HashSet();
                compoundTags.addAll(tags);
                compoundTags.addAll(inputTags);
                return compoundTags;
            }
            return tags;
        }

        public SourceSectionFilter getFilter() {
            return this.filterSourceSection;
        }

        boolean isInstrumentedFull(Set<Class<?>> providedTags, RootNode rootNode, Node node, SourceSection nodeSourceSection) {
            if (this.isInstrumentedLeaf(providedTags, node, nodeSourceSection)) {
                if (rootNode == null) {
                    return false;
                }
                return this.isInstrumentedRoot(providedTags, rootNode, rootNode.getSourceSection(), 0);
            }
            return false;
        }

        boolean isChildInstrumentedFull(Set<Class<?>> providedTags, RootNode rootNode, Node parent, SourceSection parentSourceSection, Node current, SourceSection currentSourceSection) {
            if (this.inputFilter == null) {
                return false;
            }
            if (rootNode == null) {
                return false;
            }
            if (!InstrumentationHandler.isInstrumentableNode(parent)) {
                return false;
            }
            if (this.isInstrumentedLeaf(providedTags, parent, parentSourceSection) && this.isInstrumentedNodeWithInputFilter(providedTags, current, currentSourceSection)) {
                return this.isInstrumentedRoot(providedTags, rootNode, rootNode.getSourceSection(), 0);
            }
            return false;
        }

        boolean isChildInstrumentedLeaf(Set<Class<?>> providedTags, RootNode rootNode, Node parent, SourceSection parentSourceSection, Node current, SourceSection currentSourceSection) {
            if (this.inputFilter == null) {
                return false;
            }
            if (rootNode == null) {
                return false;
            }
            if (!InstrumentationHandler.isInstrumentableNode(parent)) {
                return false;
            }
            return this.isInstrumentedLeaf(providedTags, parent, parentSourceSection) && this.isInstrumentedNodeWithInputFilter(providedTags, current, currentSourceSection);
        }

        private boolean isInstrumentedNodeWithInputFilter(Set<Class<?>> providedTags, Node current, SourceSection currentSourceSection) {
            try {
                return this.inputFilter.isInstrumentedNode(providedTags, current, currentSourceSection);
            }
            catch (Throwable t) {
                if (this.isLanguageBinding()) {
                    throw t;
                }
                ProbeNode.exceptionEventForClientInstrument(this, this.inputFilter.toString(), t);
                return false;
            }
        }

        boolean isInstrumentedRoot(Set<Class<?>> providedTags, RootNode rootNode, SourceSection rootSourceSection, int rootNodeBits) {
            if (!this.getInstrumenter().isInstrumentableRoot(rootNode)) {
                return false;
            }
            try {
                return this.getFilter().isInstrumentedRoot(providedTags, rootSourceSection, rootNode, rootNodeBits);
            }
            catch (Throwable t) {
                if (this.isLanguageBinding()) {
                    throw t;
                }
                ProbeNode.exceptionEventForClientInstrument(this, this.getFilter().toString(), t);
                return false;
            }
        }

        boolean isInstrumentedLeaf(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection section) {
            try {
                return this.getFilter().isInstrumentedNode(providedTags, instrumentedNode, section);
            }
            catch (Throwable t) {
                if (this.isLanguageBinding()) {
                    throw t;
                }
                ProbeNode.exceptionEventForClientInstrument(this, this.getFilter().toString(), t);
                return false;
            }
        }

        boolean isInstrumentedSource(com.oracle.truffle.api.source.Source source) {
            if (!this.getInstrumenter().isInstrumentableSource(source)) {
                return false;
            }
            try {
                return this.getFilter().isInstrumentedSource(source);
            }
            catch (Throwable t) {
                if (this.isLanguageBinding()) {
                    throw t;
                }
                ProbeNode.exceptionEventForClientInstrument(this, this.getFilter().toString(), t);
                return false;
            }
        }

        abstract boolean isExecutionEvent();

        boolean isLanguageBinding() {
            return this.getInstrumenter() instanceof InstrumentationHandler.LanguageClientInstrumenter;
        }
    }

    static final class Execution<T>
    extends Source<T> {
        Execution(InstrumentationHandler.AbstractInstrumenter instrumenter, SourceSectionFilter filterSourceSection, SourceSectionFilter inputFilter, T element) {
            super(instrumenter, filterSourceSection, inputFilter, element);
        }

        @Override
        boolean isExecutionEvent() {
            return true;
        }
    }

    static abstract class LoadSource<T>
    extends Source<T> {
        private final boolean notifyLoaded;

        LoadSource(InstrumentationHandler.AbstractInstrumenter instrumenter, SourceSectionFilter filterSourceSection, SourceSectionFilter inputFilter, T element, boolean attached, boolean notifyLoaded) {
            super(instrumenter, filterSourceSection, inputFilter, element, attached);
            this.notifyLoaded = notifyLoaded;
        }

        final boolean isNotifyLoaded() {
            return this.notifyLoaded;
        }

        @Override
        final boolean isExecutionEvent() {
            return false;
        }
    }

    static final class SourceSectionLoaded<T>
    extends LoadSource<T> {
        SourceSectionLoaded(InstrumentationHandler.AbstractInstrumenter instrumenter, SourceSectionFilter filterSourceSection, SourceSectionFilter inputFilter, T element, boolean attached, boolean notifyLoaded) {
            super(instrumenter, filterSourceSection, inputFilter, element, attached, notifyLoaded);
        }

        @Override
        void doAttach() {
            this.getInstrumenter().attachSourceSectionBinding(this);
        }
    }

    static final class SourceExecuted<T>
    extends LoadSource<T> {
        SourceExecuted(InstrumentationHandler.AbstractInstrumenter instrumenter, SourceSectionFilter filterSourceSection, SourceSectionFilter inputFilter, T element, boolean attached, boolean notifyLoaded) {
            super(instrumenter, filterSourceSection, inputFilter, element, attached, notifyLoaded);
        }

        @Override
        void doAttach() {
            this.getInstrumenter().attachSourceExecutedBinding(this);
        }
    }

    static final class SourceLoaded<T>
    extends LoadSource<T> {
        SourceLoaded(InstrumentationHandler.AbstractInstrumenter instrumenter, SourceSectionFilter filterSourceSection, SourceSectionFilter inputFilter, T element, boolean attached, boolean notifyLoaded) {
            super(instrumenter, filterSourceSection, inputFilter, element, attached, notifyLoaded);
        }

        @Override
        void doAttach() {
            this.getInstrumenter().attachSourceLoadedBinding(this);
        }
    }
}

