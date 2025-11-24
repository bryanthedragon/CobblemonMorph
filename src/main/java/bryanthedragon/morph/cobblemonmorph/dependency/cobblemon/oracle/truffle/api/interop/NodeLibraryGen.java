
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.interop.DefaultNodeExports;
import com.oracle.truffle.api.interop.NodeLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.library.Message;
import com.oracle.truffle.api.library.ReflectionLibrary;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.FinalBitSet;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=NodeLibrary.class)
final class NodeLibraryGen
extends LibraryFactory<NodeLibrary> {
    private static final Class<NodeLibrary> LIBRARY_CLASS = NodeLibraryGen.lazyLibraryClass();
    private static final Message HAS_SCOPE = new MessageImpl("hasScope", 0, Boolean.TYPE, Object.class, Frame.class);
    private static final Message GET_SCOPE = new MessageImpl("getScope", 1, Object.class, Object.class, Frame.class, Boolean.TYPE);
    private static final Message HAS_RECEIVER_MEMBER = new MessageImpl("hasReceiverMember", 2, Boolean.TYPE, Object.class, Frame.class);
    private static final Message GET_RECEIVER_MEMBER = new MessageImpl("getReceiverMember", 3, Object.class, Object.class, Frame.class);
    private static final Message HAS_ROOT_INSTANCE = new MessageImpl("hasRootInstance", 4, Boolean.TYPE, Object.class, Frame.class);
    private static final Message GET_ROOT_INSTANCE = new MessageImpl("getRootInstance", 5, Object.class, Object.class, Frame.class);
    private static final Message GET_VIEW = new MessageImpl("getView", 6, Object.class, Object.class, Frame.class, Object.class);
    private static final NodeLibraryGen INSTANCE = new NodeLibraryGen();
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

    private NodeLibraryGen() {
        super(LIBRARY_CLASS, Collections.unmodifiableList(Arrays.asList(HAS_SCOPE, GET_SCOPE, HAS_RECEIVER_MEMBER, GET_RECEIVER_MEMBER, HAS_ROOT_INSTANCE, GET_ROOT_INSTANCE, GET_VIEW)));
    }

    @Override
    protected Class<?> getDefaultClass(Object receiver) {
        if (receiver instanceof Node) {
            return DefaultNodeExports.class;
        }
        return NodeLibrary.class;
    }

    @Override
    protected NodeLibrary createAssertions(NodeLibrary delegate) {
        return new NodeLibrary.Asserts(delegate);
    }

    @Override
    protected NodeLibrary createProxy(ReflectionLibrary library) {
        return new Proxy(library);
    }

    @Override
    protected FinalBitSet createMessageBitSet(Message ... messages) {
        BitSet bitSet = new BitSet(2);
        for (Message message : messages) {
            bitSet.set(message.getId());
        }
        return FinalBitSet.valueOf(bitSet);
    }

    @Override
    protected NodeLibrary createDelegate(NodeLibrary delegateLibrary) {
        return new Delegate(delegateLibrary);
    }

    @Override
    protected Object genericDispatch(Library originalLib, Object receiver, Message message, Object[] args, int offset) throws Exception {
        NodeLibrary lib = (NodeLibrary)originalLib;
        if (message.getParameterCount() - 1 != args.length - offset) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
        switch (message.getId()) {
            case 0: {
                return lib.hasScope(receiver, (Frame)args[offset]);
            }
            case 1: {
                return lib.getScope(receiver, (Frame)args[offset], (Boolean)args[offset + 1]);
            }
            case 2: {
                return lib.hasReceiverMember(receiver, (Frame)args[offset]);
            }
            case 3: {
                return lib.getReceiverMember(receiver, (Frame)args[offset]);
            }
            case 4: {
                return lib.hasRootInstance(receiver, (Frame)args[offset]);
            }
            case 5: {
                return lib.getRootInstance(receiver, (Frame)args[offset]);
            }
            case 6: {
                return lib.getView(receiver, (Frame)args[offset], args[offset + 1]);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new AbstractMethodError(message.toString());
    }

    @Override
    protected NodeLibrary createDispatchImpl(int limit) {
        return new CachedDispatchFirst(null, null, limit);
    }

    @Override
    protected NodeLibrary createUncachedDispatch() {
        return new UncachedDispatch();
    }

    private static Class<NodeLibrary> lazyLibraryClass() {
        try {
            return Class.forName("com.oracle.truffle.api.interop.NodeLibrary", false, NodeLibraryGen.class.getClassLoader());
        }
        catch (ClassNotFoundException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
    }

    static {
        LibraryExport.register(LIBRARY_CLASS, new Default());
        LibraryFactory.register(LIBRARY_CLASS, INSTANCE);
    }

    @GeneratedBy(value=NodeLibrary.class)
    private static abstract class CachedDispatch
    extends NodeLibrary {
        @Node.Child
        NodeLibrary library;
        @Node.Child
        CachedDispatch next;

        CachedDispatch(NodeLibrary library, CachedDispatch next) {
            this.library = library;
            this.next = next;
        }

        abstract int getLimit();

        @Override
        @ExplodeLoop
        public boolean hasScope(Object receiver_, Frame frame) {
            while (true) {
                CachedDispatch current = this;
                do {
                    NodeLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasScope(receiver_, frame);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getScope(Object receiver_, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    NodeLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getScope(receiver_, frame, nodeEnter);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasReceiverMember(Object receiver_, Frame frame) {
            while (true) {
                CachedDispatch current = this;
                do {
                    NodeLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasReceiverMember(receiver_, frame);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getReceiverMember(Object receiver_, Frame frame) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    NodeLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getReceiverMember(receiver_, frame);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean hasRootInstance(Object receiver_, Frame frame) {
            while (true) {
                CachedDispatch current = this;
                do {
                    NodeLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.hasRootInstance(receiver_, frame);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getRootInstance(Object receiver_, Frame frame) throws UnsupportedMessageException {
            while (true) {
                CachedDispatch current = this;
                do {
                    NodeLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getRootInstance(receiver_, frame);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getView(Object receiver_, Frame frame, Object value2) {
            while (true) {
                CachedDispatch current = this;
                do {
                    NodeLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getView(receiver_, frame, value2);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        public boolean accepts(Object receiver_) {
            return true;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void specialize(Object receiver_) {
            Lock lock = this.getLock();
            lock.lock();
            try {
                CachedDispatch current = this;
                NodeLibrary thisLibrary = current.library;
                if (thisLibrary == null) {
                    this.library = this.insert((NodeLibrary)INSTANCE.create(receiver_));
                } else {
                    int count = 0;
                    do {
                        NodeLibrary currentLibrary;
                        if ((currentLibrary = current.library) != null && currentLibrary.accepts(receiver_)) {
                            return;
                        }
                        ++count;
                    } while ((current = current.next) != null);
                    if (count >= this.getLimit()) {
                        this.library = this.insert(new CachedToUncachedDispatch());
                        this.next = null;
                    } else {
                        this.next = this.insert(new CachedDispatchNext((NodeLibrary)INSTANCE.create(receiver_), this.next));
                    }
                }
            }
            finally {
                lock.unlock();
            }
        }
    }

    @GeneratedBy(value=NodeLibrary.class)
    private static final class CachedDispatchFirst
    extends CachedDispatch {
        private final int limit_;

        CachedDispatchFirst(NodeLibrary library, CachedDispatch next, int limit_) {
            super(library, next);
            this.limit_ = limit_;
        }

        @Override
        int getLimit() {
            return this.limit_;
        }

        @Override
        public NodeCost getCost() {
            if (this.library instanceof CachedToUncachedDispatch) {
                return NodeCost.MEGAMORPHIC;
            }
            CachedDispatch current = this;
            int count = 0;
            do {
                if (current.library == null) continue;
                ++count;
            } while ((current = current.next) != null);
            return NodeCost.fromCount(count);
        }
    }

    @GeneratedBy(value=NodeLibrary.class)
    private static final class CachedDispatchNext
    extends CachedDispatch {
        CachedDispatchNext(NodeLibrary library, CachedDispatch next) {
            super(library, next);
        }

        @Override
        int getLimit() {
            throw CompilerDirectives.shouldNotReachHere();
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }
    }

    @GeneratedBy(value=NodeLibrary.class)
    @DenyReplace
    private static final class UncachedDispatch
    extends NodeLibrary {
        private UncachedDispatch() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
        }

        @Override
        public boolean hasScope(Object receiver_, Frame frame) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return ((NodeLibrary)INSTANCE.getUncached(receiver_)).hasScope(receiver_, frame);
        }

        @Override
        public Object getScope(Object receiver_, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return ((NodeLibrary)INSTANCE.getUncached(receiver_)).getScope(receiver_, frame, nodeEnter);
        }

        @Override
        public boolean hasReceiverMember(Object receiver_, Frame frame) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return ((NodeLibrary)INSTANCE.getUncached(receiver_)).hasReceiverMember(receiver_, frame);
        }

        @Override
        public Object getReceiverMember(Object receiver_, Frame frame) throws UnsupportedMessageException {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return ((NodeLibrary)INSTANCE.getUncached(receiver_)).getReceiverMember(receiver_, frame);
        }

        @Override
        public boolean hasRootInstance(Object receiver_, Frame frame) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return ((NodeLibrary)INSTANCE.getUncached(receiver_)).hasRootInstance(receiver_, frame);
        }

        @Override
        public Object getRootInstance(Object receiver_, Frame frame) throws UnsupportedMessageException {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return ((NodeLibrary)INSTANCE.getUncached(receiver_)).getRootInstance(receiver_, frame);
        }

        @Override
        public Object getView(Object receiver_, Frame frame, Object value2) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return ((NodeLibrary)INSTANCE.getUncached(receiver_)).getView(receiver_, frame, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean accepts(Object receiver_) {
            return true;
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }
    }

    @GeneratedBy(value=NodeLibrary.class)
    private static final class CachedToUncachedDispatch
    extends NodeLibrary {
        private CachedToUncachedDispatch() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean hasScope(Object receiver_, Frame frame) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            CompilerDirectives.transferToInterpreterAndInvalidate();
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((NodeLibrary)INSTANCE.getUncached(receiver_)).hasScope(receiver_, frame);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object getScope(Object receiver_, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            CompilerDirectives.transferToInterpreterAndInvalidate();
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((NodeLibrary)INSTANCE.getUncached(receiver_)).getScope(receiver_, frame, nodeEnter);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean hasReceiverMember(Object receiver_, Frame frame) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            CompilerDirectives.transferToInterpreterAndInvalidate();
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((NodeLibrary)INSTANCE.getUncached(receiver_)).hasReceiverMember(receiver_, frame);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object getReceiverMember(Object receiver_, Frame frame) throws UnsupportedMessageException {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            CompilerDirectives.transferToInterpreterAndInvalidate();
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((NodeLibrary)INSTANCE.getUncached(receiver_)).getReceiverMember(receiver_, frame);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public boolean hasRootInstance(Object receiver_, Frame frame) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            CompilerDirectives.transferToInterpreterAndInvalidate();
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                boolean bl = ((NodeLibrary)INSTANCE.getUncached(receiver_)).hasRootInstance(receiver_, frame);
                return bl;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object getRootInstance(Object receiver_, Frame frame) throws UnsupportedMessageException {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            CompilerDirectives.transferToInterpreterAndInvalidate();
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((NodeLibrary)INSTANCE.getUncached(receiver_)).getRootInstance(receiver_, frame);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public Object getView(Object receiver_, Frame frame, Object value2) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            CompilerDirectives.transferToInterpreterAndInvalidate();
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((NodeLibrary)INSTANCE.getUncached(receiver_)).getView(receiver_, frame, value2);
                return object;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        @Override
        public boolean accepts(Object receiver_) {
            return true;
        }
    }

    @GeneratedBy(value=NodeLibrary.class)
    private static final class Delegate
    extends NodeLibrary {
        @Node.Child
        private NodeLibrary delegateLibrary;

        Delegate(NodeLibrary delegateLibrary) {
            this.delegateLibrary = delegateLibrary;
        }

        @Override
        public boolean hasScope(Object receiver_, Frame frame) {
            if (NodeLibraryGen.isDelegated(this.delegateLibrary, 0)) {
                Object delegate = NodeLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((NodeLibrary)NodeLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasScope(delegate, frame);
            }
            return this.delegateLibrary.hasScope(receiver_, frame);
        }

        @Override
        public Object getScope(Object receiver_, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
            if (NodeLibraryGen.isDelegated(this.delegateLibrary, 1)) {
                Object delegate = NodeLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((NodeLibrary)NodeLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getScope(delegate, frame, nodeEnter);
            }
            return this.delegateLibrary.getScope(receiver_, frame, nodeEnter);
        }

        @Override
        public boolean hasReceiverMember(Object receiver_, Frame frame) {
            if (NodeLibraryGen.isDelegated(this.delegateLibrary, 2)) {
                Object delegate = NodeLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((NodeLibrary)NodeLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasReceiverMember(delegate, frame);
            }
            return this.delegateLibrary.hasReceiverMember(receiver_, frame);
        }

        @Override
        public Object getReceiverMember(Object receiver_, Frame frame) throws UnsupportedMessageException {
            if (NodeLibraryGen.isDelegated(this.delegateLibrary, 3)) {
                Object delegate = NodeLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((NodeLibrary)NodeLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getReceiverMember(delegate, frame);
            }
            return this.delegateLibrary.getReceiverMember(receiver_, frame);
        }

        @Override
        public boolean hasRootInstance(Object receiver_, Frame frame) {
            if (NodeLibraryGen.isDelegated(this.delegateLibrary, 4)) {
                Object delegate = NodeLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((NodeLibrary)NodeLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).hasRootInstance(delegate, frame);
            }
            return this.delegateLibrary.hasRootInstance(receiver_, frame);
        }

        @Override
        public Object getRootInstance(Object receiver_, Frame frame) throws UnsupportedMessageException {
            if (NodeLibraryGen.isDelegated(this.delegateLibrary, 5)) {
                Object delegate = NodeLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((NodeLibrary)NodeLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getRootInstance(delegate, frame);
            }
            return this.delegateLibrary.getRootInstance(receiver_, frame);
        }

        @Override
        public Object getView(Object receiver_, Frame frame, Object value2) {
            if (NodeLibraryGen.isDelegated(this.delegateLibrary, 6)) {
                Object delegate = NodeLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((NodeLibrary)NodeLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getView(delegate, frame, value2);
            }
            return this.delegateLibrary.getView(receiver_, frame, value2);
        }

        @Override
        public boolean accepts(Object receiver_) {
            return this.delegateLibrary.accepts(receiver_);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        @Override
        public boolean isAdoptable() {
            return this.delegateLibrary.isAdoptable();
        }
    }

    @GeneratedBy(value=NodeLibrary.class)
    private static final class Proxy
    extends NodeLibrary {
        @Node.Child
        private ReflectionLibrary lib;

        Proxy(ReflectionLibrary lib) {
            this.lib = lib;
        }

        @Override
        public boolean hasScope(Object receiver_, Frame frame) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_SCOPE, frame);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getScope(Object receiver_, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_SCOPE, frame, nodeEnter);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasReceiverMember(Object receiver_, Frame frame) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_RECEIVER_MEMBER, frame);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getReceiverMember(Object receiver_, Frame frame) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_RECEIVER_MEMBER, frame);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean hasRootInstance(Object receiver_, Frame frame) {
            try {
                return (Boolean)this.lib.send(receiver_, HAS_ROOT_INSTANCE, frame);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getRootInstance(Object receiver_, Frame frame) throws UnsupportedMessageException {
            try {
                return this.lib.send(receiver_, GET_ROOT_INSTANCE, frame);
            }
            catch (UnsupportedMessageException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getView(Object receiver_, Frame frame, Object value2) {
            try {
                return this.lib.send(receiver_, GET_VIEW, frame, value2);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean accepts(Object receiver_) {
            return this.lib.accepts(receiver_);
        }
    }

    @GeneratedBy(value=NodeLibrary.class)
    private static class MessageImpl
    extends Message {
        MessageImpl(String name, int index, Class<?> returnType, Class<?> ... parameters) {
            super(LIBRARY_CLASS, name, index, returnType, parameters);
        }
    }

    @GeneratedBy(value=NodeLibrary.class)
    private static final class Default
    extends LibraryExport<NodeLibrary> {
        private Default() {
            super(NodeLibrary.class, Object.class, false, false, 0);
        }

        @Override
        protected NodeLibrary createUncached(Object receiver) {
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected NodeLibrary createCached(Object receiver) {
            return new Cached(receiver);
        }

        @GeneratedBy(value=NodeLibrary.class)
        @DenyReplace
        private static final class Uncached
        extends NodeLibrary {
            @Node.Child
            private DynamicDispatchLibrary dynamicDispatch_;
            private final Class<?> dynamicDispatchTarget_;

            protected Uncached(Object receiver) {
                this.dynamicDispatch_ = DYNAMIC_DISPATCH_LIBRARY_.getUncached(receiver);
                this.dynamicDispatchTarget_ = this.dynamicDispatch_.dispatch(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                return this.dynamicDispatch_.accepts(receiver) && this.dynamicDispatch_.dispatch(receiver) == this.dynamicDispatchTarget_;
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @Override
            public boolean hasScope(Object receiver, Frame frame) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasScope(receiver, frame);
            }

            @Override
            public Object getScope(Object receiver, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getScope(receiver, frame, nodeEnter);
            }

            @Override
            public boolean hasReceiverMember(Object receiver, Frame frame) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasReceiverMember(receiver, frame);
            }

            @Override
            public Object getReceiverMember(Object receiver, Frame frame) throws UnsupportedMessageException {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getReceiverMember(receiver, frame);
            }

            @Override
            public boolean hasRootInstance(Object receiver, Frame frame) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasRootInstance(receiver, frame);
            }

            @Override
            public Object getRootInstance(Object receiver, Frame frame) throws UnsupportedMessageException {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getRootInstance(receiver, frame);
            }

            @Override
            public Object getView(Object receiver, Frame frame, Object value2) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getView(receiver, frame, value2);
            }
        }

        @GeneratedBy(value=NodeLibrary.class)
        private static final class Cached
        extends NodeLibrary {
            @Node.Child
            private DynamicDispatchLibrary dynamicDispatch_;
            private final Class<?> dynamicDispatchTarget_;

            protected Cached(Object receiver) {
                this.dynamicDispatch_ = this.insert(DYNAMIC_DISPATCH_LIBRARY_.create(receiver));
                this.dynamicDispatchTarget_ = DYNAMIC_DISPATCH_LIBRARY_.getUncached(receiver).dispatch(receiver);
            }

            @Override
            public boolean accepts(Object receiver) {
                return this.dynamicDispatch_.accepts(receiver) && this.dynamicDispatch_.dispatch(receiver) == this.dynamicDispatchTarget_;
            }

            @Override
            public boolean hasScope(Object receiver, Frame frame) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasScope(this.dynamicDispatch_.cast(receiver), frame);
            }

            @Override
            public Object getScope(Object receiver, Frame frame, boolean nodeEnter) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getScope(this.dynamicDispatch_.cast(receiver), frame, nodeEnter);
            }

            @Override
            public boolean hasReceiverMember(Object receiver, Frame frame) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasReceiverMember(this.dynamicDispatch_.cast(receiver), frame);
            }

            @Override
            public Object getReceiverMember(Object receiver, Frame frame) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getReceiverMember(this.dynamicDispatch_.cast(receiver), frame);
            }

            @Override
            public boolean hasRootInstance(Object receiver, Frame frame) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.hasRootInstance(this.dynamicDispatch_.cast(receiver), frame);
            }

            @Override
            public Object getRootInstance(Object receiver, Frame frame) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getRootInstance(this.dynamicDispatch_.cast(receiver), frame);
            }

            @Override
            public Object getView(Object receiver, Frame frame, Object value2) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getView(this.dynamicDispatch_.cast(receiver), frame, value2);
            }
        }
    }
}

