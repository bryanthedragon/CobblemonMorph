
package com.oracle.truffle.api.library;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.library.Message;
import com.oracle.truffle.api.library.ReflectionLibrary;
import com.oracle.truffle.api.library.ReflectionLibraryDefault;
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

@GeneratedBy(value=ReflectionLibrary.class)
final class ReflectionLibraryGen
extends LibraryFactory<ReflectionLibrary> {
    private static final Class<ReflectionLibrary> LIBRARY_CLASS = ReflectionLibraryGen.lazyLibraryClass();
    private static final Message SEND = new MessageImpl("send", 0, Object.class, Object.class, Message.class, Object[].class);
    private static final ReflectionLibraryGen INSTANCE = new ReflectionLibraryGen();

    private ReflectionLibraryGen() {
        super(LIBRARY_CLASS, Collections.unmodifiableList(Arrays.asList(SEND)));
    }

    @Override
    protected Class<?> getDefaultClass(Object receiver) {
        return ReflectionLibraryDefault.class;
    }

    @Override
    protected ReflectionLibrary createProxy(ReflectionLibrary library) {
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
    protected ReflectionLibrary createDelegate(ReflectionLibrary delegateLibrary) {
        return new Delegate(delegateLibrary);
    }

    @Override
    protected Object genericDispatch(Library originalLib, Object receiver, Message message, Object[] args, int offset) throws Exception {
        ReflectionLibrary lib = (ReflectionLibrary)originalLib;
        if (message.getParameterCount() - 1 != args.length - offset) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
        switch (message.getId()) {
            case 0: {
                return lib.send(receiver, (Message)args[offset], (Object[])args[offset + 1]);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new AbstractMethodError(message.toString());
    }

    @Override
    protected ReflectionLibrary createDispatchImpl(int limit) {
        return new CachedDispatchFirst(null, null, limit);
    }

    @Override
    protected ReflectionLibrary createUncachedDispatch() {
        return new UncachedDispatch();
    }

    private static Class<ReflectionLibrary> lazyLibraryClass() {
        try {
            return Class.forName("com.oracle.truffle.api.library.ReflectionLibrary", false, ReflectionLibraryGen.class.getClassLoader());
        }
        catch (ClassNotFoundException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
    }

    static {
        LibraryFactory.register(LIBRARY_CLASS, INSTANCE);
    }

    @GeneratedBy(value=ReflectionLibrary.class)
    private static abstract class CachedDispatch
    extends ReflectionLibrary {
        @Node.Child
        ReflectionLibrary library;
        @Node.Child
        CachedDispatch next;

        CachedDispatch(ReflectionLibrary library, CachedDispatch next) {
            this.library = library;
            this.next = next;
        }

        abstract int getLimit();

        @Override
        @ExplodeLoop
        public Object send(Object receiver_, Message message, Object ... args) throws Exception {
            while (true) {
                CachedDispatch current = this;
                do {
                    ReflectionLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.send(receiver_, message, args);
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
                ReflectionLibrary thisLibrary = current.library;
                if (thisLibrary == null) {
                    this.library = this.insert((ReflectionLibrary)INSTANCE.create(receiver_));
                } else {
                    int count = 0;
                    do {
                        ReflectionLibrary currentLibrary;
                        if ((currentLibrary = current.library) != null && currentLibrary.accepts(receiver_)) {
                            return;
                        }
                        ++count;
                    } while ((current = current.next) != null);
                    if (count >= this.getLimit()) {
                        this.library = this.insert(new CachedToUncachedDispatch());
                        this.next = null;
                    } else {
                        this.next = this.insert(new CachedDispatchNext((ReflectionLibrary)INSTANCE.create(receiver_), this.next));
                    }
                }
            }
            finally {
                lock.unlock();
            }
        }
    }

    @GeneratedBy(value=ReflectionLibrary.class)
    private static final class CachedDispatchFirst
    extends CachedDispatch {
        private final int limit_;

        CachedDispatchFirst(ReflectionLibrary library, CachedDispatch next, int limit_) {
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

    @GeneratedBy(value=ReflectionLibrary.class)
    private static final class CachedDispatchNext
    extends CachedDispatch {
        CachedDispatchNext(ReflectionLibrary library, CachedDispatch next) {
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

    @GeneratedBy(value=ReflectionLibrary.class)
    @DenyReplace
    private static final class UncachedDispatch
    extends ReflectionLibrary {
        private UncachedDispatch() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object send(Object receiver_, Message message, Object ... args) throws Exception {
            return ((ReflectionLibrary)INSTANCE.getUncached(receiver_)).send(receiver_, message, args);
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

    @GeneratedBy(value=ReflectionLibrary.class)
    private static final class CachedToUncachedDispatch
    extends ReflectionLibrary {
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
        public Object send(Object receiver_, Message message, Object ... args) throws Exception {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this.getParent());
            try {
                Object object = ((ReflectionLibrary)INSTANCE.getUncached(receiver_)).send(receiver_, message, args);
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

    @GeneratedBy(value=ReflectionLibrary.class)
    private static final class Delegate
    extends ReflectionLibrary {
        @Node.Child
        private ReflectionLibrary delegateLibrary;

        Delegate(ReflectionLibrary delegateLibrary) {
            this.delegateLibrary = delegateLibrary;
        }

        @Override
        public Object send(Object receiver_, Message message, Object ... args) throws Exception {
            if (LibraryFactory.isDelegated(this.delegateLibrary, 0)) {
                Object delegate = LibraryFactory.readDelegate(this.delegateLibrary, receiver_);
                return LibraryFactory.getDelegateLibrary(this.delegateLibrary, delegate).send(delegate, message, args);
            }
            return this.delegateLibrary.send(receiver_, message, args);
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

    @GeneratedBy(value=ReflectionLibrary.class)
    private static final class Proxy
    extends ReflectionLibrary {
        @Node.Child
        private ReflectionLibrary lib;

        Proxy(ReflectionLibrary lib) {
            this.lib = lib;
        }

        @Override
        public Object send(Object receiver_, Message message, Object ... args) throws Exception {
            return this.lib.send(receiver_, SEND, message, args);
        }

        @Override
        public boolean accepts(Object receiver_) {
            return this.lib.accepts(receiver_);
        }
    }

    @GeneratedBy(value=ReflectionLibrary.class)
    private static class MessageImpl
    extends Message {
        MessageImpl(String name, int index, Class<?> returnType, Class<?> ... parameters) {
            super(LIBRARY_CLASS, name, index, returnType, parameters);
        }
    }
}

