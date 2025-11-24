
package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.host.HostAdapterSuperMembers;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=HostAdapterSuperMembers.class)
final class HostAdapterSuperMembersGen {
    private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    private HostAdapterSuperMembersGen() {
    }

    static {
        LibraryExport.register(HostAdapterSuperMembers.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=HostAdapterSuperMembers.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, HostAdapterSuperMembers.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof HostAdapterSuperMembers);
            Uncached uncached = new Uncached();
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof HostAdapterSuperMembers);
            return new Cached(receiver);
        }

        @GeneratedBy(value=HostAdapterSuperMembers.class)
        @DenyReplace
        private static final class Uncached
        extends InteropLibrary {
            protected Uncached() {
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof HostAdapterSuperMembers) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                return receiver instanceof HostAdapterSuperMembers;
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
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostAdapterSuperMembers)receiver).hasMembers();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostAdapterSuperMembers arg0Value = (HostAdapterSuperMembers)arg0Value_;
                return arg0Value.readMember(arg1Value, HostAdapterSuperMembers.NameCache.getUncached(), INTEROP_LIBRARY_.getUncached(arg0Value.adapter));
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object invokeMember(Object arg0Value_, String arg1Value, Object ... arg2Value) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostAdapterSuperMembers arg0Value = (HostAdapterSuperMembers)arg0Value_;
                return arg0Value.invokeMember(arg1Value, arg2Value, HostAdapterSuperMembers.NameCache.getUncached(), INTEROP_LIBRARY_.getUncached(arg0Value.adapter));
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostAdapterSuperMembers arg0Value = (HostAdapterSuperMembers)arg0Value_;
                return arg0Value.isMemberReadable(arg1Value, HostAdapterSuperMembers.NameCache.getUncached(), INTEROP_LIBRARY_.getUncached(arg0Value.adapter));
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                HostAdapterSuperMembers arg0Value = (HostAdapterSuperMembers)arg0Value_;
                return arg0Value.isMemberInvocable(arg1Value, HostAdapterSuperMembers.NameCache.getUncached(), INTEROP_LIBRARY_.getUncached(arg0Value.adapter));
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((HostAdapterSuperMembers)receiver).getMembers(includeInternal);
            }
        }

        @GeneratedBy(value=HostAdapterSuperMembers.class)
        private static final class Cached
        extends InteropLibrary {
            @Node.Child
            private InteropLibrary receiverAdapterInteropLibrary_;
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private HostAdapterSuperMembers.NameCache cache;

            protected Cached(Object receiver) {
                HostAdapterSuperMembers castReceiver = (HostAdapterSuperMembers)receiver;
                this.receiverAdapterInteropLibrary_ = super.insert(INTEROP_LIBRARY_.create(castReceiver.adapter));
            }

            @Override
            public boolean accepts(Object receiver) {
                assert (!(receiver instanceof HostAdapterSuperMembers) || DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null) : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";
                if (!(receiver instanceof HostAdapterSuperMembers)) {
                    return false;
                }
                return this.receiverAdapterInteropLibrary_.accepts(((HostAdapterSuperMembers)receiver).adapter);
            }

            @Override
            public boolean hasMembers(Object receiver) {
                assert (receiver instanceof HostAdapterSuperMembers) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostAdapterSuperMembers)receiver).hasMembers();
            }

            @Override
            public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (arg0Value_ instanceof HostAdapterSuperMembers) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostAdapterSuperMembers arg0Value = (HostAdapterSuperMembers)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 1) != 0) {
                    InteropLibrary readMemberNode__readMember_interop__ = this.receiverAdapterInteropLibrary_;
                    return arg0Value.readMember(arg1Value, this.cache, readMemberNode__readMember_interop__);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readMemberNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object readMemberNode_AndSpecialize(HostAdapterSuperMembers arg0Value, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    InteropLibrary readMemberNode__readMember_interop__ = null;
                    this.cache = this.cache == null ? HostAdapterSuperMembers.NameCache.create() : this.cache;
                    readMemberNode__readMember_interop__ = this.receiverAdapterInteropLibrary_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.readMember(arg1Value, this.cache, readMemberNode__readMember_interop__);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public NodeCost getCost() {
                int state_0 = this.state_0_;
                if ((state_0 & 1) == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                return NodeCost.MONOMORPHIC;
            }

            @Override
            public Object invokeMember(Object arg0Value_, String arg1Value, Object ... arg2Value) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
                assert (arg0Value_ instanceof HostAdapterSuperMembers) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostAdapterSuperMembers arg0Value = (HostAdapterSuperMembers)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 2) != 0) {
                    InteropLibrary invokeMemberNode__invokeMember_interop__ = this.receiverAdapterInteropLibrary_;
                    return arg0Value.invokeMember(arg1Value, arg2Value, this.cache, invokeMemberNode__invokeMember_interop__);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.invokeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object invokeMemberNode_AndSpecialize(HostAdapterSuperMembers arg0Value, String arg1Value, Object[] arg2Value) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    InteropLibrary invokeMemberNode__invokeMember_interop__ = null;
                    this.cache = this.cache == null ? HostAdapterSuperMembers.NameCache.create() : this.cache;
                    invokeMemberNode__invokeMember_interop__ = this.receiverAdapterInteropLibrary_;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.invokeMember(arg1Value, arg2Value, this.cache, invokeMemberNode__invokeMember_interop__);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
                assert (arg0Value_ instanceof HostAdapterSuperMembers) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostAdapterSuperMembers arg0Value = (HostAdapterSuperMembers)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 4) != 0) {
                    InteropLibrary isMemberReadableNode__isMemberReadable_interop__ = this.receiverAdapterInteropLibrary_;
                    return arg0Value.isMemberReadable(arg1Value, this.cache, isMemberReadableNode__isMemberReadable_interop__);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberReadableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberReadableNode_AndSpecialize(HostAdapterSuperMembers arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    InteropLibrary isMemberReadableNode__isMemberReadable_interop__ = null;
                    this.cache = this.cache == null ? HostAdapterSuperMembers.NameCache.create() : this.cache;
                    isMemberReadableNode__isMemberReadable_interop__ = this.receiverAdapterInteropLibrary_;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberReadable(arg1Value, this.cache, isMemberReadableNode__isMemberReadable_interop__);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
                assert (arg0Value_ instanceof HostAdapterSuperMembers) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                HostAdapterSuperMembers arg0Value = (HostAdapterSuperMembers)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 8) != 0) {
                    InteropLibrary isMemberInvocableNode__isMemberInvocable_interop__ = this.receiverAdapterInteropLibrary_;
                    return arg0Value.isMemberInvocable(arg1Value, this.cache, isMemberInvocableNode__isMemberInvocable_interop__);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberInvocableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberInvocableNode_AndSpecialize(HostAdapterSuperMembers arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    InteropLibrary isMemberInvocableNode__isMemberInvocable_interop__ = null;
                    this.cache = this.cache == null ? HostAdapterSuperMembers.NameCache.create() : this.cache;
                    isMemberInvocableNode__isMemberInvocable_interop__ = this.receiverAdapterInteropLibrary_;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberInvocable(arg1Value, this.cache, isMemberInvocableNode__isMemberInvocable_interop__);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
                assert (receiver instanceof HostAdapterSuperMembers) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((HostAdapterSuperMembers)receiver).getMembers(includeInternal);
            }
        }
    }
}

