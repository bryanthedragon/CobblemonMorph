
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.EventBinding;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.ExecutionEventListener;
import com.oracle.truffle.api.instrumentation.ExecutionEventNode;
import com.oracle.truffle.api.instrumentation.ExecutionEventNodeFactory;
import com.oracle.truffle.api.instrumentation.InstrumentAccessor;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.InstrumentationHandler;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.instrumentation.UnwindException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.nodes.NodeVisitor;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;

public final class ProbeNode
extends Node {
    private static final int SEEN_UNWIND = 1;
    private static final int SEEN_UNWIND_NEXT = 2;
    private static final int SEEN_RETURN = 4;
    private static final int SEEN_REENTER = 8;
    public static final Object UNWIND_ACTION_REENTER = new Object();
    private static final Object UNWIND_ACTION_IGNORED = new Object();
    private final InstrumentationHandler handler;
    private volatile RetiredNodeReference retiredNodeReference;
    @CompilerDirectives.CompilationFinal
    private volatile EventContext context;
    @Node.Child
    private volatile EventChainNode chain;
    @CompilerDirectives.CompilationFinal
    private volatile Assumption version;
    @CompilerDirectives.CompilationFinal
    private volatile int seen = 0;

    ProbeNode(InstrumentationHandler handler, SourceSection sourceSection) {
        this.handler = handler;
        this.context = new EventContext(this, sourceSection);
    }

    RetiredNodeReference getRetiredNodeReference() {
        return this.retiredNodeReference;
    }

    void clearRetiredNodeReference() {
        this.retiredNodeReference = null;
    }

    private boolean hasNewTags(Node retiredNode, Set<Class<? extends Tag>> materializeTags) {
        Set<Class<? extends Tag>> allSeenMaterializeTags = this.retiredNodeReference.next == null ? this.retiredNodeReference.materializeTags : new HashSet<Class<? extends Tag>>(this.retiredNodeReference.materializeTags);
        RetiredNodeReference nodeRef = this.retiredNodeReference;
        while (nodeRef != null) {
            if (allSeenMaterializeTags != nodeRef.materializeTags) {
                allSeenMaterializeTags.addAll(nodeRef.materializeTags);
            }
            Node nodeRefNode = nodeRef.getNode();
            assert (nodeRefNode == null || nodeRefNode != retiredNode) : "The same retired node must not be set more than once!";
            assert (!nodeRef.materializeTags.equals(materializeTags)) : "Retired node must be set at most once for the same set of tags!";
            nodeRef = nodeRef.next;
        }
        return !allSeenMaterializeTags.containsAll(materializeTags);
    }

    void setRetiredNode(Node retiredNode, Set<Class<? extends Tag>> materializeTags) {
        if (this.retiredNodeReference == null) {
            this.retiredNodeReference = new RetiredNodeReference(retiredNode, materializeTags, null);
        } else {
            RetiredNodeReference newRetiredNodeReference;
            assert (this.hasNewTags(retiredNode, materializeTags)) : "There should always be some new materialize tag!";
            RetiredNodeReference previousRetiredNodeReference = this.retiredNodeReference;
            this.retiredNodeReference = newRetiredNodeReference = new RetiredNodeReference(retiredNode, materializeTags, previousRetiredNodeReference);
        }
    }

    public void onEnter(VirtualFrame frame) {
        EventChainNode localChain = this.lazyUpdate(frame);
        if (localChain != null) {
            EventChainNode.onEnter(localChain, this.context, frame);
        }
    }

    public void onReturnValue(VirtualFrame frame, Object result) {
        EventChainNode localChain = this.lazyUpdate(frame);
        assert (this.isNullOrInteropValue(result));
        if (localChain != null) {
            EventChainNode.onReturnValue(localChain, this.context, frame, result);
        }
    }

    private boolean isNullOrInteropValue(Object result) {
        if (!(this.context.getInstrumentedNode() instanceof InstrumentableNode)) {
            return true;
        }
        if (result == null) {
            return true;
        }
        InstrumentAccessor.interopAccess().checkInteropType(result);
        return true;
    }

    @Override
    public Node copy() {
        ProbeNode pn = (ProbeNode)super.copy();
        pn.context = new EventContext(pn, this.context.getInstrumentedSourceSection());
        return pn;
    }

    public Object onReturnExceptionalOrUnwind(VirtualFrame frame, Throwable exception, boolean isReturnCalled) {
        UnwindException unwind = null;
        if (exception instanceof UnwindException) {
            this.profileBranch(1);
            unwind = (UnwindException)exception;
        } else if (exception instanceof ThreadDeath) {
            throw (ThreadDeath)exception;
        }
        EventChainNode localChain = this.lazyUpdate(frame);
        if (localChain != null) {
            if (!isReturnCalled) {
                try {
                    EventChainNode.onReturnExceptional(localChain, this.context, frame, exception);
                }
                catch (UnwindException ex) {
                    this.profileBranch(1);
                    if (unwind != null && unwind != ex) {
                        this.profileBranch(2);
                        unwind.addNext(ex);
                    }
                    unwind = ex;
                }
            }
            if (unwind != null) {
                Object ret = EventChainNode.onUnwind(localChain, this.context, frame, unwind);
                if (ret == UNWIND_ACTION_REENTER) {
                    this.profileBranch(8);
                    return UNWIND_ACTION_REENTER;
                }
                if (ret != null && ret != UNWIND_ACTION_IGNORED) {
                    this.profileBranch(4);
                    assert (this.isNullOrInteropValue(ret));
                    return ret;
                }
                throw unwind;
            }
        }
        return null;
    }

    private void profileBranch(int flag) {
        if ((this.seen & flag) == 0) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.seen |= flag;
        }
    }

    void onInputValue(VirtualFrame frame, EventBinding<?> targetBinding, EventContext inputContext, int inputIndex, Object inputValue) {
        EventChainNode localChain = this.lazyUpdate(frame);
        if (localChain != null) {
            EventChainNode.onInputValue(localChain, this.context, frame, targetBinding, inputContext, inputIndex, inputValue);
        }
    }

    EventContext getContext() {
        return this.context;
    }

    InstrumentableNode.WrapperNode findWrapper() throws AssertionError {
        Node parent = this.getParent();
        if (!(parent instanceof InstrumentableNode.WrapperNode)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            if (parent == null) {
                throw new AssertionError((Object)"Probe node disconnected from AST.");
            }
            throw new AssertionError((Object)"ProbeNodes must have a parent Node that implements NodeWrapper.");
        }
        return (InstrumentableNode.WrapperNode)((Object)parent);
    }

    synchronized void invalidate() {
        Assumption localVersion = this.version;
        if (localVersion != null) {
            localVersion.invalidate();
        }
    }

    EventChainNode lazyUpdate(VirtualFrame frame) {
        Assumption localVersion = this.version;
        if (localVersion == null || !localVersion.isValid()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.lazyUpdatedImpl(frame);
        }
        return this.chain;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private EventChainNode lazyUpdatedImpl(VirtualFrame frame) {
        EventChainNode oldChain;
        EventChainNode nextChain;
        Lock lock = this.getLock();
        lock.lock();
        try {
            EventBinding.Source<?>[] executionBindingsSnapshot;
            Assumption localVersion = this.version;
            if (localVersion != null && localVersion.isValid()) {
                EventChainNode eventChainNode = this.chain;
                return eventChainNode;
            }
            do {
                if ((nextChain = this.handler.createBindings(frame, this, executionBindingsSnapshot = this.handler.getExecutionBindingsSnapshot())) == null) {
                    if (this.retiredNodeReference == null) {
                        InstrumentationHandler.removeWrapper(this);
                        EventChainNode eventChainNode = null;
                        return eventChainNode;
                    }
                    oldChain = this.chain;
                    this.chain = null;
                } else {
                    oldChain = this.chain;
                    this.chain = this.insert(nextChain);
                }
                this.version = Truffle.getRuntime().createAssumption("Instruments unchanged");
            } while (executionBindingsSnapshot != this.handler.getExecutionBindingsSnapshot());
            assert (this.context.validEventContextOnLazyUpdate());
        }
        finally {
            lock.unlock();
        }
        if (oldChain != null) {
            EventChainNode.onDispose(oldChain, this.context, frame);
        }
        return nextChain;
    }

    ExecutionEventNode lookupExecutionEventNode(EventBinding<?> binding) {
        if (binding.isDisposed()) {
            return null;
        }
        EventChainNode chainNode = this.chain;
        while (chainNode != null) {
            if (chainNode.binding == binding && chainNode instanceof EventProviderChainNode) {
                return ((EventProviderChainNode)chainNode).eventNode;
            }
            chainNode = chainNode.next;
        }
        return null;
    }

    Iterator<ExecutionEventNode> lookupExecutionEventNodes(final Collection<EventBinding<? extends ExecutionEventNodeFactory>> bindings) {
        return new Iterator<ExecutionEventNode>(){
            private EventChainNode chainNode;
            private EventProviderChainNode nextNode;
            {
                this.chainNode = ProbeNode.this.chain;
            }

            @Override
            public boolean hasNext() {
                if (this.nextNode == null) {
                    while (this.chainNode != null) {
                        if (this.chainNode instanceof EventProviderChainNode && bindings.contains(this.chainNode.binding)) {
                            this.nextNode = (EventProviderChainNode)this.chainNode;
                            this.chainNode = this.chainNode.next;
                            break;
                        }
                        this.chainNode = this.chainNode.next;
                    }
                }
                return this.nextNode != null;
            }

            @Override
            public ExecutionEventNode next() {
                EventProviderChainNode node = this.nextNode;
                if (node == null) {
                    throw new NoSuchElementException();
                }
                this.nextNode = null;
                return node.eventNode;
            }
        };
    }

    EventChainNode createParentEventChainCallback(VirtualFrame frame, EventBinding.Source<?> binding, RootNode rootNode, Set<Class<?>> providedTags) {
        EventChainNode parent = this.findParentChain(frame, binding);
        if (!(parent instanceof EventProviderWithInputChainNode)) {
            return null;
        }
        EventContext parentContext = parent.findProbe().getContext();
        EventProviderWithInputChainNode parentChain = (EventProviderWithInputChainNode)parent;
        int index = ProbeNode.indexOfChild(binding, rootNode, providedTags, parentContext.getInstrumentedNode(), parentContext.getInstrumentedSourceSection(), this.context.getInstrumentedNode());
        if (index < 0 || index >= parentChain.inputCount) {
            assert (ProbeNode.throwIllegalASTAssertion(parentChain, parentContext, binding, rootNode, providedTags, index));
            return null;
        }
        ProbeNode probe = parent.findProbe();
        return new InputValueChainNode(binding, probe, this.context, index);
    }

    private static boolean throwIllegalASTAssertion(EventProviderWithInputChainNode parentChain, EventContext parentContext, EventBinding.Source<?> binding, RootNode rootNode, Set<Class<?>> providedTags, int index) {
        StringBuilder msg = new StringBuilder();
        try {
            EventContext eventContext;
            int i;
            int lookupChildrenCount = 10;
            SourceSection parentSourceSection = parentContext.getInstrumentedSourceSection();
            EventContext[] contexts = ProbeNode.findChildContexts(binding, rootNode, providedTags, parentContext.getInstrumentedNode(), parentContext.getInstrumentedSourceSection(), Math.max(parentChain.inputCount, index + 10));
            int contextCount = 0;
            for (i = 0; i < contexts.length; ++i) {
                eventContext = contexts[i];
                if (eventContext == null) continue;
                ++contextCount;
            }
            msg.append("Stable AST assumption violated.  " + parentChain.inputCount + " children expected got " + contextCount);
            msg.append("\n Parent: " + parentSourceSection);
            block3: for (i = 0; i < contexts.length; ++i) {
                eventContext = contexts[i];
                if (eventContext == null) continue;
                msg.append("\nChild[" + i + "] = " + eventContext.getInstrumentedSourceSection());
                Object indent = "  ";
                for (Node node = eventContext.getInstrumentedNode(); node != null; node = node.getParent()) {
                    msg.append("\n");
                    msg.append((String)indent);
                    if (node == parentContext.getInstrumentedNode()) {
                        msg.append("Parent");
                        continue block3;
                    }
                    if (node.getParent() == null) {
                        msg.append("null parent = ");
                    } else {
                        String fieldName = NodeUtil.findChildFieldName(node.getParent(), node);
                        msg.append(node.getParent().getClass().getSimpleName() + "." + fieldName + " = ");
                    }
                    msg.append(node.getClass().getSimpleName() + "#" + System.identityHashCode(node));
                    indent = (String)indent + "  ";
                }
            }
        }
        catch (Throwable e) {
            AssertionError error = new AssertionError((Object)"Stable AST assumption violated");
            ((Throwable)((Object)error)).addSuppressed(e);
            throw error;
        }
        throw new AssertionError((Object)msg.toString());
    }

    EventChainNode createEventChainCallback(VirtualFrame frame, EventBinding.Source<?> binding, RootNode rootNode, Set<Class<?>> providedTags, Node instrumentedNode, SourceSection instrumentedNodeSourceSection) {
        EventChainNode next;
        Object element = binding.getElement();
        if (element instanceof ExecutionEventListener) {
            next = new EventFilterChainNode(binding, (ExecutionEventListener)element);
        } else {
            assert (element instanceof ExecutionEventNodeFactory);
            ExecutionEventNode eventNode = this.createEventNode(binding, element);
            if (eventNode == null) {
                return null;
            }
            if (binding.getInputFilter() != null) {
                int baseInput;
                EventChainNode parent = this.findParentChain(frame, binding);
                EventProviderWithInputChainNode parentChain = (EventProviderWithInputChainNode)parent;
                if (parentChain == null) {
                    baseInput = 0;
                } else {
                    EventContext parentContext = parentChain.findProbe().getContext();
                    int childIndex = ProbeNode.indexOfChild(binding, rootNode, providedTags, parentContext.getInstrumentedNode(), parentContext.getInstrumentedSourceSection(), instrumentedNode);
                    int inputBaseIndex = parentChain.inputBaseIndex;
                    baseInput = childIndex < 0 ? inputBaseIndex + parentChain.inputCount : inputBaseIndex + childIndex;
                }
                int inputCount = ProbeNode.countChildren(binding, rootNode, providedTags, instrumentedNode, instrumentedNodeSourceSection);
                next = new EventProviderWithInputChainNode(binding, eventNode, baseInput, inputCount);
            } else {
                next = new EventProviderChainNode(binding, eventNode);
            }
        }
        return next;
    }

    static EventContext[] findChildContexts(EventBinding.Source<?> binding, RootNode rootNode, Set<Class<?>> providedTags, Node instrumentedNode, SourceSection instrumentedNodeSourceSection, int inputCount) {
        InputChildContextLookup visitor = new InputChildContextLookup(binding, rootNode, providedTags, instrumentedNode, instrumentedNodeSourceSection, inputCount);
        NodeUtil.forEachChild(instrumentedNode, visitor);
        return visitor.foundContexts;
    }

    private static int indexOfChild(EventBinding.Source<?> binding, RootNode rootNode, Set<Class<?>> providedTags, Node instrumentedNode, SourceSection instrumentedNodeSourceSection, Node lookupChild) {
        InputChildIndexLookup visitor = new InputChildIndexLookup(binding, rootNode, providedTags, instrumentedNode, instrumentedNodeSourceSection, lookupChild);
        NodeUtil.forEachChild(instrumentedNode, visitor);
        return visitor.found ? visitor.index : -1;
    }

    private static int countChildren(EventBinding.Source<?> binding, RootNode rootNode, Set<Class<?>> providedTags, Node instrumentedNode, SourceSection instrumentedNodeSourceSection) {
        InputChildIndexLookup visitor = new InputChildIndexLookup(binding, rootNode, providedTags, instrumentedNode, instrumentedNodeSourceSection, null);
        NodeUtil.forEachChild(instrumentedNode, visitor);
        return visitor.index;
    }

    private EventChainNode findParentChain(VirtualFrame frame, EventBinding<?> binding) {
        Node node;
        for (node = this.getParent().getParent(); node != null; node = node.getParent()) {
            if (node instanceof InstrumentableNode.WrapperNode) {
                ProbeNode probe = ((InstrumentableNode.WrapperNode)((Object)node)).getProbeNode();
                EventChainNode c = probe.lazyUpdate(frame);
                if (c != null) {
                    c = c.find(binding);
                }
                if (c == null) continue;
                return c;
            }
            if (node instanceof RootNode) break;
        }
        if (node == null) {
            throw new IllegalStateException("The AST node is not yet adopted. ");
        }
        return null;
    }

    private ExecutionEventNode createEventNode(EventBinding.Source<?> binding, Object element) {
        ExecutionEventNode eventNode;
        try {
            eventNode = ((ExecutionEventNodeFactory)element).create(this.context);
            if (eventNode != null && eventNode.getParent() != null) {
                throw new IllegalStateException(String.format("Returned EventNode %s was already adopted by another AST.", eventNode));
            }
        }
        catch (Throwable t) {
            ProbeNode.exceptionEventForClientInstrument(binding, "ProbeNodeFactory.create", t);
            return null;
        }
        return eventNode;
    }

    @CompilerDirectives.TruffleBoundary
    static void exceptionEventForClientInstrument(EventBinding.Source<?> b, String eventName, Throwable t) {
        if (t instanceof ThreadDeath) {
            throw (ThreadDeath)t;
        }
        if (!(b.getInstrumenter() instanceof InstrumentationHandler.InstrumentClientInstrumenter)) {
            throw ProbeNode.sthrow(RuntimeException.class, t);
        }
        InstrumentationHandler.InstrumentClientInstrumenter instrumenter = (InstrumentationHandler.InstrumentClientInstrumenter)b.getInstrumenter();
        Object probeInstrument = instrumenter.getEnv().getPolyglotInstrument();
        if (InstrumentAccessor.engineAccess().isInstrumentExceptionsAreThrown(probeInstrument)) {
            throw ProbeNode.sthrow(RuntimeException.class, t);
        }
        TruffleLogger logger = InstrumentAccessor.engineAccess().getLogger(probeInstrument, null);
        String message = String.format("Event %s failed for instrument class %s and listener/factory %s.", eventName, instrumenter.getInstrumentClassName(), b.getElement());
        logger.log(Level.SEVERE, message, t);
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.NONE;
    }

    private static boolean checkInteropType(Object value2, EventBinding.Source<?> binding) {
        Class<?> clazz;
        if (value2 != null && value2 != UNWIND_ACTION_REENTER && value2 != UNWIND_ACTION_IGNORED && !InstrumentAccessor.ACCESSOR.isTruffleObject(value2) && (clazz = value2.getClass()) != Byte.class && clazz != Short.class && clazz != Integer.class && clazz != Long.class && clazz != Float.class && clazz != Double.class && clazz != Character.class && clazz != Boolean.class && clazz != String.class && clazz != TruffleString.class) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            ClassCastException ccex = new ClassCastException(clazz.getName() + " isn't allowed Truffle interop type!");
            if (binding.isLanguageBinding()) {
                throw ccex;
            }
            ProbeNode.exceptionEventForClientInstrument(binding, "onUnwind", ccex);
            return false;
        }
        return true;
    }

    private static Object mergePostUnwindReturns(Object r1, Object r2) {
        if (r1 == null || r2 == null) {
            return null;
        }
        if (r1 == UNWIND_ACTION_IGNORED) {
            return r2;
        }
        if (r2 == UNWIND_ACTION_IGNORED) {
            return r1;
        }
        if (r1 == UNWIND_ACTION_REENTER || r2 == UNWIND_ACTION_REENTER) {
            return UNWIND_ACTION_REENTER;
        }
        return r1;
    }

    private static <T extends Throwable> T sthrow(Class<T> type, Throwable t) throws T {
        throw t;
    }

    private static class InputValueChainNode
    extends EventChainNode {
        private final EventBinding<?> targetBinding;
        private final ProbeNode parentProbe;
        private final int inputIndex;
        private final EventContext inputContext;

        InputValueChainNode(EventBinding.Source<?> binding, ProbeNode parentProbe, EventContext inputContext, int inputIndex) {
            super(binding);
            this.targetBinding = binding;
            this.parentProbe = parentProbe;
            this.inputContext = inputContext;
            this.inputIndex = inputIndex;
        }

        @Override
        EventChainNode find(EventBinding<?> b) {
            EventChainNode next = this.getNext();
            if (next == null) {
                return null;
            }
            return next.find(b);
        }

        @Override
        protected Object innerOnUnwind(EventContext context, VirtualFrame frame, Object info) {
            return UNWIND_ACTION_IGNORED;
        }

        @Override
        protected void innerOnInputValue(EventContext context, VirtualFrame frame, EventBinding<?> binding, EventContext inputContext, int inputIndex, Object inputValue) {
        }

        @Override
        protected void innerOnEnter(EventContext context, VirtualFrame frame) {
        }

        @Override
        protected void innerOnDispose(EventContext context, VirtualFrame frame) {
        }

        @Override
        protected void innerOnReturnValue(EventContext context, VirtualFrame frame, Object result) {
            this.parentProbe.onInputValue(frame, this.targetBinding, this.inputContext, this.inputIndex, result);
        }

        @Override
        protected void innerOnReturnExceptional(EventContext context, VirtualFrame frame, Throwable exception) {
        }
    }

    static class EventProviderChainNode
    extends EventChainNode {
        @Node.Child
        private ExecutionEventNode eventNode;

        EventProviderChainNode(EventBinding.Source<?> binding, ExecutionEventNode eventNode) {
            super(binding);
            this.eventNode = eventNode;
        }

        @Override
        protected final void innerOnInputValue(EventContext context, VirtualFrame frame, EventBinding<?> binding, EventContext inputContext, int inputIndex, Object inputValue) {
            this.eventNode.onInputValue(frame, inputContext, inputIndex, inputValue);
        }

        @Override
        protected final void innerOnEnter(EventContext context, VirtualFrame frame) {
            this.eventNode.onEnter(frame);
        }

        @Override
        protected void innerOnReturnExceptional(EventContext context, VirtualFrame frame, Throwable exception) {
            this.eventNode.onReturnExceptional(frame, exception);
        }

        @Override
        protected void innerOnReturnValue(EventContext context, VirtualFrame frame, Object result) {
            this.eventNode.onReturnValue(frame, result);
        }

        @Override
        protected Object innerOnUnwind(EventContext context, VirtualFrame frame, Object info) {
            return this.eventNode.onUnwind(frame, info);
        }

        @Override
        protected void innerOnDispose(EventContext context, VirtualFrame frame) {
            this.eventNode.onDispose(frame);
        }
    }

    static class EventProviderWithInputChainNode
    extends EventProviderChainNode {
        static final Object[] EMPTY_ARRAY = new Object[0];
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private volatile int[] inputSlots;
        @CompilerDirectives.CompilationFinal
        private volatile FrameDescriptor sourceFrameDescriptor;
        final int inputBaseIndex;
        final int inputCount;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        volatile EventContext[] inputContexts;

        EventProviderWithInputChainNode(EventBinding.Source<?> binding, ExecutionEventNode eventNode, int inputBaseIndex, int inputCount) {
            super(binding, eventNode);
            this.inputBaseIndex = inputBaseIndex;
            this.inputCount = inputCount;
        }

        final int getInputCount() {
            return this.inputCount;
        }

        final EventContext getInputContext(int index) {
            EventContext[] contexts = this.inputContexts;
            if (contexts == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                ProbeNode probe = this.findProbe();
                EventContext thisContext = probe.context;
                RootNode rootNode = this.getRootNode();
                Set<Class<?>> providedTags = probe.handler.getProvidedTags(rootNode);
                this.inputContexts = contexts = ProbeNode.findChildContexts(this.getBinding(), rootNode, providedTags, thisContext.getInstrumentedNode(), thisContext.getInstrumentedSourceSection(), this.inputCount);
            }
            if (contexts == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new IllegalStateException("Input event context not yet available. They are only available during event notifications.");
            }
            return contexts[index];
        }

        final void saveInputValue(VirtualFrame frame, int inputIndex, Object value2) {
            this.verifyIndex(inputIndex);
            if (this.inputSlots == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.initializeSlots(frame);
            }
            assert (this.sourceFrameDescriptor == frame.getFrameDescriptor()) : "Unstable frame descriptor used by the language.";
            frame.setAuxiliarySlot(this.inputSlots[inputIndex], value2);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void initializeSlots(VirtualFrame frame) {
            Lock lock = this.getLock();
            lock.lock();
            try {
                if (this.inputSlots == null) {
                    if (InstrumentationHandler.TRACE) {
                        InstrumentationHandler.trace("SLOTS: Adding %s save slots for binding %s%n", this.inputCount, this.getBinding().getElement());
                    }
                    FrameDescriptor frameDescriptor = frame.getFrameDescriptor();
                    int[] slots = new int[this.inputCount];
                    for (int i = 0; i < this.inputCount; ++i) {
                        int slotIndex = this.inputBaseIndex + i;
                        slots[i] = frameDescriptor.findOrAddAuxiliarySlot(new SavedInputValueID(this.getBinding(), slotIndex));
                    }
                    this.sourceFrameDescriptor = frameDescriptor;
                    this.inputSlots = slots;
                }
            }
            finally {
                lock.unlock();
            }
        }

        private void verifyIndex(int inputIndex) {
            if (inputIndex >= this.inputCount || inputIndex < 0) {
                CompilerDirectives.transferToInterpreter();
                throw new IllegalArgumentException("Invalid input index.");
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        protected void innerOnDispose(EventContext context, VirtualFrame frame) {
            Lock lock = this.getLock();
            lock.lock();
            try {
                if (this.inputSlots != null) {
                    this.inputSlots = null;
                    RootNode rootNode = context.getInstrumentedNode().getRootNode();
                    if (rootNode == null) {
                        return;
                    }
                    FrameDescriptor descriptor = rootNode.getFrameDescriptor();
                    assert (descriptor != null);
                    for (int i = 0; i < this.inputCount; ++i) {
                        int slotIndex = this.inputBaseIndex + i;
                        descriptor.disableAuxiliarySlot(new SavedInputValueID(this.getBinding(), slotIndex));
                    }
                }
            }
            finally {
                lock.unlock();
            }
            super.innerOnDispose(context, frame);
        }

        @Override
        protected void innerOnReturnExceptional(EventContext context, VirtualFrame frame, Throwable exception) {
            super.innerOnReturnExceptional(context, frame, exception);
            this.clearSlots(frame);
        }

        @Override
        protected void innerOnReturnValue(EventContext context, VirtualFrame frame, Object result) {
            super.innerOnReturnValue(context, frame, result);
            this.clearSlots(frame);
        }

        @ExplodeLoop
        private void clearSlots(VirtualFrame frame) {
            int[] slots = this.inputSlots;
            if (slots != null && frame.getFrameDescriptor() == this.sourceFrameDescriptor) {
                for (int slot : slots) {
                    frame.setAuxiliarySlot(slot, null);
                }
            }
        }

        protected final Object getSavedInputValue(VirtualFrame frame, int inputIndex) {
            this.verifyIndex(inputIndex);
            if (this.inputSlots == null) {
                return null;
            }
            return frame.getAuxiliarySlot(this.inputSlots[inputIndex]);
        }

        @ExplodeLoop
        protected final Object[] getSavedInputValues(VirtualFrame frame) {
            Object[] inputValues;
            int[] slots = this.inputSlots;
            if (slots == null) {
                return EMPTY_ARRAY;
            }
            if (frame.getFrameDescriptor() == this.sourceFrameDescriptor) {
                inputValues = new Object[slots.length];
                for (int i = 0; i < slots.length; ++i) {
                    inputValues[i] = frame.getAuxiliarySlot(slots[i]);
                }
            } else {
                inputValues = new Object[this.inputSlots.length];
            }
            return inputValues;
        }

        static final class SavedInputValueID {
            private final EventBinding<?> binding;
            private final int index;

            SavedInputValueID(EventBinding<?> binding, int index) {
                this.binding = binding;
                this.index = index;
            }

            public int hashCode() {
                return 31 * this.binding.hashCode() * 31 + this.index;
            }

            public String toString() {
                return "SavedInputValue(binding=" + this.binding.hashCode() + ":" + this.index + ")";
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || this.getClass() != obj.getClass()) {
                    return false;
                }
                SavedInputValueID other = (SavedInputValueID)obj;
                return this.binding == other.binding && this.index == other.index;
            }
        }
    }

    private static class EventFilterChainNode
    extends EventChainNode {
        private final ExecutionEventListener listener;

        EventFilterChainNode(EventBinding.Source<?> binding, ExecutionEventListener listener) {
            super(binding);
            this.listener = listener;
        }

        @Override
        protected void innerOnInputValue(EventContext context, VirtualFrame frame, EventBinding<?> binding, EventContext inputContext, int inputIndex, Object inputValue) {
        }

        @Override
        protected void innerOnEnter(EventContext context, VirtualFrame frame) {
            this.listener.onEnter(context, frame);
        }

        @Override
        protected void innerOnReturnExceptional(EventContext context, VirtualFrame frame, Throwable exception) {
            this.listener.onReturnExceptional(context, frame, exception);
        }

        @Override
        protected void innerOnReturnValue(EventContext context, VirtualFrame frame, Object result) {
            this.listener.onReturnValue(context, frame, result);
        }

        @Override
        protected Object innerOnUnwind(EventContext context, VirtualFrame frame, Object info) {
            return this.listener.onUnwind(context, frame, info);
        }

        @Override
        protected void innerOnDispose(EventContext context, VirtualFrame frame) {
        }
    }

    static abstract class EventChainNode
    extends Node {
        private static final int SEEN_EXCEPTION_ON_ENTER = 1;
        private static final int SEEN_EXCEPTION_ON_RETURN = 2;
        private static final int SEEN_EXCEPTION_ON_RETURN_EXCEPTIONAL = 4;
        private static final int SEEN_EXCEPTION_ON_INPUT_VALUE = 8;
        private static final int SEEN_EXCEPTION_ON_UNWIND = 16;
        private static final int SEEN_EXCEPTION_HAS_NEXT = 32;
        private static final int SEEN_EXCEPTION_OTHER = 128;
        private static final int SEEN_UNWIND_ON_ENTER = 256;
        private static final int SEEN_UNWIND_ON_RETURN = 512;
        private static final int SEEN_UNWIND_ON_RETURN_EXCEPTIONAL = 1024;
        private static final int SEEN_UNWIND_ON_INPUT_VALUE = 2048;
        private static final int SEEN_UNWIND_HAS_NEXT = 4096;
        private final EventBinding.Source<?> binding;
        @Node.Child
        private EventChainNode next;
        @CompilerDirectives.CompilationFinal
        private EventChainNode previous;
        @CompilerDirectives.CompilationFinal
        private int seen;

        EventChainNode(EventBinding.Source<?> binding) {
            this.binding = binding;
        }

        final ProbeNode findProbe() {
            Node parent;
            for (parent = this; parent != null && !(parent instanceof ProbeNode); parent = parent.getParent()) {
            }
            return (ProbeNode)parent;
        }

        final void setNext(EventChainNode next) {
            this.next = this.insert(next);
            next.previous = this;
        }

        EventBinding.Source<?> getBinding() {
            return this.binding;
        }

        EventChainNode getNext() {
            return this.next;
        }

        @Override
        public final NodeCost getCost() {
            return NodeCost.NONE;
        }

        final void profileBranch(int flag) {
            if ((this.seen & flag) == 0) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.seen |= flag;
            }
        }

        static void onDispose(EventChainNode eventChain, EventContext context, VirtualFrame frame) {
            CompilerAsserts.neverPartOfCompilation();
            EventChainNode chainNode = eventChain;
            RuntimeException prevError = null;
            while (chainNode != null) {
                try {
                    chainNode.innerOnDispose(context, frame);
                }
                catch (Throwable t) {
                    prevError = chainNode.handleError("onDispose", prevError, t);
                }
                chainNode = chainNode.next;
            }
            if (prevError != null) {
                throw prevError;
            }
        }

        private RuntimeException handleError(String eventName, RuntimeException previousError, Throwable newError) {
            if (this.binding.isLanguageBinding()) {
                if (previousError != null) {
                    this.profileBranch(32);
                    EventChainNode.addSuppressedException(previousError, newError);
                    return previousError;
                }
                return (RuntimeException)newError;
            }
            this.profileBranch(128);
            ProbeNode.exceptionEventForClientInstrument(this.binding, eventName, newError);
            return previousError;
        }

        @CompilerDirectives.TruffleBoundary
        private static void addSuppressedException(Throwable prev, Throwable t) {
            prev.addSuppressed(t);
        }

        protected abstract void innerOnDispose(EventContext var1, VirtualFrame var2);

        @ExplodeLoop
        static void onEnter(EventChainNode eventChain, EventContext context, VirtualFrame frame) {
            EventChainNode current = eventChain;
            UnwindException unwind = null;
            RuntimeException prevError = null;
            while (current != null) {
                try {
                    current.innerOnEnter(context, frame);
                }
                catch (UnwindException ex) {
                    current.profileBranch(256);
                    unwind = EventChainNode.handleUnwind(current, unwind, ex);
                }
                catch (Throwable t) {
                    current.profileBranch(1);
                    prevError = current.handleError("onEnter", prevError, t);
                }
                current = current.next;
            }
            if (prevError != null) {
                throw prevError;
            }
            if (unwind != null) {
                throw unwind;
            }
        }

        protected abstract void innerOnEnter(EventContext var1, VirtualFrame var2);

        @ExplodeLoop
        static void onInputValue(EventChainNode eventChain, EventContext context, VirtualFrame frame, EventBinding<?> inputBinding, EventContext inputContext, int inputIndex, Object inputValue) {
            EventChainNode current = eventChain.getLast();
            UnwindException unwind = null;
            RuntimeException prevError = null;
            while (current != null) {
                try {
                    if (current.binding == inputBinding) {
                        current.innerOnInputValue(context, frame, current.binding, inputContext, inputIndex, inputValue);
                    }
                }
                catch (UnwindException ex) {
                    current.profileBranch(2048);
                    unwind = EventChainNode.handleUnwind(current, unwind, ex);
                }
                catch (Throwable t) {
                    current.profileBranch(8);
                    prevError = current.handleError("onInputValue", prevError, t);
                }
                current = current.previous;
            }
            if (prevError != null) {
                throw prevError;
            }
            if (unwind != null) {
                throw unwind;
            }
        }

        private static UnwindException handleUnwind(EventChainNode current, UnwindException unwind, UnwindException ex) {
            ex.thrownFromBinding(current.binding);
            return current.mergeUnwind(unwind, ex);
        }

        private UnwindException mergeUnwind(UnwindException unwind, UnwindException other) {
            if (unwind != null && unwind != other) {
                this.profileBranch(4096);
                unwind.addNext(other);
                return unwind;
            }
            return other;
        }

        protected abstract void innerOnInputValue(EventContext var1, VirtualFrame var2, EventBinding<?> var3, EventContext var4, int var5, Object var6);

        @ExplodeLoop
        private EventChainNode getLast() {
            EventChainNode current = this;
            while (current.next != null) {
                current = current.next;
            }
            CompilerAsserts.partialEvaluationConstant(current);
            return current;
        }

        @ExplodeLoop
        static void onReturnValue(EventChainNode chain, EventContext context, VirtualFrame frame, Object result) {
            EventChainNode current = chain.getLast();
            UnwindException unwind = null;
            RuntimeException prevError = null;
            while (current != null) {
                try {
                    current.innerOnReturnValue(context, frame, result);
                }
                catch (UnwindException ex) {
                    current.profileBranch(512);
                    unwind = EventChainNode.handleUnwind(current, unwind, ex);
                }
                catch (Throwable t) {
                    current.profileBranch(2);
                    prevError = current.handleError("onInputValue", prevError, t);
                }
                current = current.previous;
            }
            if (prevError != null) {
                throw prevError;
            }
            if (unwind != null) {
                throw unwind;
            }
        }

        protected abstract void innerOnReturnValue(EventContext var1, VirtualFrame var2, Object var3);

        @ExplodeLoop
        static void onReturnExceptional(EventChainNode chainNode, EventContext context, VirtualFrame frame, Throwable exception) {
            UnwindException unwind = null;
            EventChainNode current = chainNode.getLast();
            RuntimeException prevError = null;
            while (current != null) {
                try {
                    current.innerOnReturnExceptional(context, frame, exception);
                }
                catch (UnwindException ex) {
                    current.profileBranch(1024);
                    unwind = EventChainNode.handleUnwind(current, unwind, ex);
                }
                catch (Throwable t) {
                    current.profileBranch(4);
                    prevError = current.handleError("onInputValue", prevError, t);
                }
                current = current.previous;
            }
            if (prevError != null) {
                throw prevError;
            }
            if (unwind != null) {
                throw unwind;
            }
        }

        protected abstract void innerOnReturnExceptional(EventContext var1, VirtualFrame var2, Throwable var3);

        private boolean containsBinding(UnwindException unwind) {
            if (unwind.getBinding() == this.binding) {
                return true;
            }
            UnwindException nextUnwind = unwind.getNext();
            if (nextUnwind != null) {
                this.profileBranch(4096);
                return this.containsBindingBoundary(nextUnwind);
            }
            return false;
        }

        @CompilerDirectives.TruffleBoundary
        private boolean containsBindingBoundary(UnwindException unwind) {
            return this.containsBinding(unwind);
        }

        private Object getInfo(UnwindException unwind) {
            if (unwind.getBinding() == this.binding) {
                return unwind.getInfo();
            }
            UnwindException nextUnwind = unwind.getNext();
            if (nextUnwind != null) {
                this.profileBranch(4096);
                return this.getInfoBoundary(nextUnwind);
            }
            return false;
        }

        @CompilerDirectives.TruffleBoundary
        private Object getInfoBoundary(UnwindException unwind) {
            return this.getInfo(unwind);
        }

        private void reset(UnwindException unwind) {
            if (unwind.getBinding() == this.binding) {
                unwind.resetThread();
            } else {
                UnwindException nextUnwind = unwind.getNext();
                if (nextUnwind != null) {
                    this.profileBranch(4096);
                    unwind.resetBoundary(this.binding);
                }
            }
        }

        @ExplodeLoop
        static Object onUnwind(EventChainNode eventChain, EventContext context, VirtualFrame frame, UnwindException unwind) {
            EventChainNode current = eventChain;
            RuntimeException prevError = null;
            Object ret = null;
            while (current != null) {
                Object nextRet = null;
                if (current.containsBinding(unwind)) {
                    try {
                        nextRet = current.innerOnUnwind(context, frame, current.getInfo(unwind));
                    }
                    catch (Throwable t) {
                        current.profileBranch(16);
                        prevError = current.handleError("onUnwind", prevError, t);
                    }
                    if (nextRet != null) {
                        assert (ProbeNode.checkInteropType(nextRet, current.binding));
                        current.reset(unwind);
                    }
                } else {
                    nextRet = UNWIND_ACTION_IGNORED;
                }
                ret = current == eventChain ? nextRet : ProbeNode.mergePostUnwindReturns(ret, nextRet);
                current = current.next;
            }
            if (prevError != null) {
                throw prevError;
            }
            return ret;
        }

        protected abstract Object innerOnUnwind(EventContext var1, VirtualFrame var2, Object var3);

        EventChainNode find(EventBinding<?> b) {
            if (this.binding == b) {
                assert (this.next == null || this.next.find(b) == null) : "only one chain entry per binding allowed";
                return this;
            }
            return this.next != null ? this.next.find(b) : null;
        }
    }

    private static abstract class InstrumentableChildVisitor
    implements NodeVisitor {
        private final EventBinding.Source<?> binding;
        private final Set<Class<?>> providedTags;
        private final RootNode rootNode;
        private final Node instrumentedNode;
        private final SourceSection instrumentedNodeSourceSection;

        InstrumentableChildVisitor(EventBinding.Source<?> binding, RootNode rootNode, Set<Class<?>> providedTags, Node instrumentedNode, SourceSection instrumentedNodeSourceSection) {
            this.binding = binding;
            this.providedTags = providedTags;
            this.rootNode = rootNode;
            this.instrumentedNode = instrumentedNode;
            this.instrumentedNodeSourceSection = instrumentedNodeSourceSection;
        }

        @Override
        public final boolean visit(Node node) {
            SourceSection sourceSection = node.getSourceSection();
            if (InstrumentationHandler.isInstrumentableNode(node)) {
                return !this.binding.isChildInstrumentedFull(this.providedTags, this.rootNode, this.instrumentedNode, this.instrumentedNodeSourceSection, node, sourceSection) || this.visitChild(node);
            }
            NodeUtil.forEachChild(node, this);
            return true;
        }

        protected abstract boolean visitChild(Node var1);
    }

    private static class InputChildIndexLookup
    extends InstrumentableChildVisitor {
        private final Node lookupNode;
        boolean found = false;
        int index;

        InputChildIndexLookup(EventBinding.Source<?> binding, RootNode rootNode, Set<Class<?>> providedTags, Node instrumentedNode, SourceSection instrumentedNodeSourceSection, Node lookupNode) {
            super(binding, rootNode, providedTags, instrumentedNode, instrumentedNodeSourceSection);
            this.lookupNode = lookupNode;
        }

        @Override
        protected boolean visitChild(Node child) {
            if (this.found) {
                return false;
            }
            if (this.lookupNode == child) {
                this.found = true;
                return false;
            }
            ++this.index;
            return true;
        }
    }

    private static class InputChildContextLookup
    extends InstrumentableChildVisitor {
        EventContext[] foundContexts;
        int index;

        InputChildContextLookup(EventBinding.Source<?> binding, RootNode rootNode, Set<Class<?>> providedTags, Node instrumentedNode, SourceSection instrumentedNodeSourceSection, int childrenCount) {
            super(binding, rootNode, providedTags, instrumentedNode, instrumentedNodeSourceSection);
            this.foundContexts = new EventContext[childrenCount];
        }

        @Override
        protected boolean visitChild(Node child) {
            ProbeNode probe;
            Node parent = child.getParent();
            if (parent instanceof InstrumentableNode.WrapperNode) {
                probe = ((InstrumentableNode.WrapperNode)((Object)parent)).getProbeNode();
                if (this.index >= this.foundContexts.length) {
                    assert (false);
                    this.foundContexts = null;
                    return false;
                }
            } else {
                assert (false);
                this.foundContexts = null;
                return false;
            }
            this.foundContexts[this.index] = probe.context;
            ++this.index;
            return true;
        }
    }

    static class RetiredNodeReference {
        private final WeakReference<Node> node;
        private final Set<Class<? extends Tag>> materializeTags;
        final RetiredNodeReference next;

        RetiredNodeReference(Node node, Set<Class<? extends Tag>> materializeTags, RetiredNodeReference next) {
            this.node = new WeakReference<Node>(node);
            this.materializeTags = materializeTags;
            this.next = next;
        }

        Node getNode() {
            return (Node)this.node.get();
        }
    }
}

