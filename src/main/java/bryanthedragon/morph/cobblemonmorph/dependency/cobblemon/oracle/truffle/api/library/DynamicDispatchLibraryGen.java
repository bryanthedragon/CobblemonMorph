
package com.oracle.truffle.api.library;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
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

@GeneratedBy(value=DynamicDispatchLibrary.class)
final class DynamicDispatchLibraryGen
extends LibraryFactory<DynamicDispatchLibrary> {
    private static final Class<DynamicDispatchLibrary> LIBRARY_CLASS = DynamicDispatchLibraryGen.lazyLibraryClass();
    private static final Message DISPATCH = new MessageImpl("dispatch", 0, Class.class, Object.class);
    private static final DynamicDispatchLibraryGen INSTANCE = new DynamicDispatchLibraryGen();

    private DynamicDispatchLibraryGen() {
        super(LIBRARY_CLASS, Collections.unmodifiableList(Arrays.asList(DISPATCH)));
    }

    @Override
    protected Class<?> getDefaultClass(Object receiver) {
        return DynamicDispatchLibrary.class;
    }

    @Override
    protected DynamicDispatchLibrary createProxy(ReflectionLibrary library) {
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
    protected DynamicDispatchLibrary createDelegate(DynamicDispatchLibrary delegateLibrary) {
        return new Delegate(delegateLibrary);
    }

    @Override
    protected Object genericDispatch(Library originalLib, Object receiver, Message message, Object[] args, int offset) throws Exception {
        DynamicDispatchLibrary lib = (DynamicDispatchLibrary)originalLib;
        if (message.getParameterCount() - 1 != args.length - offset) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
        switch (message.getId()) {
            case 0: {
                return lib.dispatch(receiver);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new AbstractMethodError(message.toString());
    }

    @Override
    protected DynamicDispatchLibrary createDispatchImpl(int limit) {
        return new CachedDispatchFirst(null, null, limit);
    }

    @Override
    protected DynamicDispatchLibrary createUncachedDispatch() {
        return new UncachedDispatch();
    }

    private static Class<DynamicDispatchLibrary> lazyLibraryClass() {
        try {
            return Class.forName("com.oracle.truffle.api.library.DynamicDispatchLibrary", false, DynamicDispatchLibraryGen.class.getClassLoader());
        }
        catch (ClassNotFoundException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
    }

    static {
        LibraryExport.register(LIBRARY_CLASS, new Default());
        LibraryFactory.register(LIBRARY_CLASS, INSTANCE);
    }

    @GeneratedBy(value=DynamicDispatchLibrary.class)
    private static abstract class CachedDispatch
    extends DynamicDispatchLibrary {
        @Node.Child
        DynamicDispatchLibrary library;
        @Node.Child
        CachedDispatch next;

        CachedDispatch(DynamicDispatchLibrary library, CachedDispatch next) {
            this.library = library;
            this.next = next;
        }

        abstract int getLimit();

        @Override
        @ExplodeLoop
        public Class<?> dispatch(Object receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicDispatchLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.dispatch(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        public boolean accepts(Object receiver_) {
            return true;
        }

        @Override
        public Object cast(Object receiver) {
            return receiver;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void specialize(Object receiver_) {
            Lock lock = this.getLock();
            lock.lock();
            try {
                CachedDispatch current = this;
                DynamicDispatchLibrary thisLibrary = current.library;
                if (thisLibrary == null) {
                    this.library = this.insert((DynamicDispatchLibrary)INSTANCE.create(receiver_));
                } else {
                    int count = 0;
                    do {
                        DynamicDispatchLibrary currentLibrary;
                        if ((currentLibrary = current.library) != null && currentLibrary.accepts(receiver_)) {
                            return;
                        }
                        ++count;
                    } while ((current = current.next) != null);
                    if (count >= this.getLimit()) {
                        this.library = this.insert(new CachedToUncachedDispatch());
                        this.next = null;
                    } else {
                        this.next = this.insert(new CachedDispatchNext((DynamicDispatchLibrary)INSTANCE.create(receiver_), this.next));
                    }
                }
            }
            finally {
                lock.unlock();
            }
        }
    }

    @GeneratedBy(value=DynamicDispatchLibrary.class)
    private static final class CachedDispatchFirst
    extends CachedDispatch {
        private final int limit_;

        CachedDispatchFirst(DynamicDispatchLibrary library, CachedDispatch next, int limit_) {
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

    @GeneratedBy(value=DynamicDispatchLibrary.class)
    private static final class CachedDispatchNext
    extends CachedDispatch {
        CachedDispatchNext(DynamicDispatchLibrary library, CachedDispatch next) {
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

    @GeneratedBy(value=DynamicDispatchLibrary.class)
    @DenyReplace
    private static final class UncachedDispatch
    extends DynamicDispatchLibrary {
        private UncachedDispatch() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Class<?> dispatch(Object receiver_) {
            return ((DynamicDispatchLibrary)INSTANCE.getUncached(receiver_)).dispatch(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean accepts(Object receiver_) {
            return true;
        }

        @Override
        public Object cast(Object receiver) {
            return receiver;
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }
    }

    @GeneratedBy(value=DynamicDispatchLibrary.class)
    private static final class CachedToUncachedDispatch
    extends DynamicDispatchLibrary {
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
        @CompilerDirectives.TruffleBoundary
        public Class<?> dispatch(Object receiver_) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Class<?> clazz = ((DynamicDispatchLibrary)INSTANCE.getUncached(receiver_)).dispatch(receiver_);
                return clazz;
            }
            finally {
                encapsulating_.set(prev_);
            }
        }

        @Override
        public boolean accepts(Object receiver_) {
            return true;
        }

        @Override
        public Object cast(Object receiver) {
            return receiver;
        }
    }

    @GeneratedBy(value=DynamicDispatchLibrary.class)
    private static final class Delegate
    extends DynamicDispatchLibrary {
        @Node.Child
        private DynamicDispatchLibrary delegateLibrary;

        Delegate(DynamicDispatchLibrary delegateLibrary) {
            this.delegateLibrary = delegateLibrary;
        }

        @Override
        public Object cast(Object receiver) {
            return this.delegateLibrary.cast(receiver);
        }

        @Override
        public Class<?> dispatch(Object receiver_) {
            if (LibraryFactory.isDelegated(this.delegateLibrary, 0)) {
                Object delegate = LibraryFactory.readDelegate(this.delegateLibrary, receiver_);
                return LibraryFactory.getDelegateLibrary(this.delegateLibrary, delegate).dispatch(delegate);
            }
            return this.delegateLibrary.dispatch(receiver_);
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

    @GeneratedBy(value=DynamicDispatchLibrary.class)
    private static final class Proxy
    extends DynamicDispatchLibrary {
        @Node.Child
        private ReflectionLibrary lib;

        Proxy(ReflectionLibrary lib) {
            this.lib = lib;
        }

        @Override
        public Object cast(Object receiver) {
            return receiver;
        }

        @Override
        public Class<?> dispatch(Object receiver_) {
            try {
                return (Class)this.lib.send(receiver_, DISPATCH, new Object[0]);
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

    @GeneratedBy(value=DynamicDispatchLibrary.class)
    private static class MessageImpl
    extends Message {
        MessageImpl(String name, int index, Class<?> returnType, Class<?> ... parameters) {
            super(LIBRARY_CLASS, name, index, returnType, parameters);
        }
    }

    @GeneratedBy(value=DynamicDispatchLibrary.class)
    private static final class Default
    extends LibraryExport<DynamicDispatchLibrary> {
        private Default() {
            super(DynamicDispatchLibrary.class, Object.class, false, false, 0);
        }

        @Override
        protected DynamicDispatchLibrary createUncached(Object receiver) {
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected DynamicDispatchLibrary createCached(Object receiver) {
            return new Cached(receiver);
        }

        @GeneratedBy(value=DynamicDispatchLibrary.class)
        @DenyReplace
        private static final class Uncached
        extends DynamicDispatchLibrary {
            protected Uncached(Object receiver) {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                return true;
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object cast(Object receiver) {
                return receiver;
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
            @CompilerDirectives.TruffleBoundary
            public Class<?> dispatch(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.dispatch(receiver);
            }
        }

        @GeneratedBy(value=DynamicDispatchLibrary.class)
        private static final class Cached
        extends DynamicDispatchLibrary {
            private final Class<? extends Object> receiverClass_;

            protected Cached(Object receiver) {
                this.receiverClass_ = receiver.getClass();
            }

            @Override
            public Object cast(Object receiver) {
                return CompilerDirectives.castExact(receiver, this.receiverClass_);
            }

            @Override
            public boolean accepts(Object receiver) {
                return CompilerDirectives.isExact(receiver, this.receiverClass_);
            }

            @Override
            public Class<?> dispatch(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.dispatch(CompilerDirectives.castExact(receiver, this.receiverClass_));
            }
        }
    }
}

