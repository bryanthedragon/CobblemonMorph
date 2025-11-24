
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.GraalJSExceptionGen;
import com.oracle.truffle.js.runtime.JSException;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSException.class)
final class JSExceptionGen {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    private JSExceptionGen() {
    }

    static {
        LibraryExport.register(JSException.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSException.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSException.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSException);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSException);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSException.class)
        @DenyReplace
        private static final class Uncached
        extends GraalJSExceptionGen.InteropLibraryExports.Uncached {
            protected Uncached(Object receiver) {
                super(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                return super.accepts(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSException)receiver).getExceptionType();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isExceptionIncompleteSource(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSException)receiver).isExceptionIncompleteSource();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSException)receiver).hasMembers();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSException arg0Value = (JSException)arg0Value_;
                return arg0Value.getMembers(arg1Value, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSException arg0Value = (JSException)arg0Value_;
                return arg0Value.isMemberReadable(arg1Value, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSException arg0Value = (JSException)arg0Value_;
                return arg0Value.isMemberModifiable(arg1Value, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInsertable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSException arg0Value = (JSException)arg0Value_;
                return arg0Value.isMemberInsertable(arg1Value, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSException arg0Value = (JSException)arg0Value_;
                return arg0Value.isMemberRemovable(arg1Value, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMemberInvocable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSException arg0Value = (JSException)arg0Value_;
                return arg0Value.isMemberInvocable(arg1Value, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMemberReadSideEffects(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSException arg0Value = (JSException)arg0Value_;
                return arg0Value.hasMemberReadSideEffects(arg1Value, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMemberWriteSideEffects(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSException arg0Value = (JSException)arg0Value_;
                return arg0Value.hasMemberWriteSideEffects(arg1Value, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readMember(Object arg0Value_, String arg1Value) throws UnknownIdentifierException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSException arg0Value = (JSException)arg0Value_;
                return arg0Value.readMember(arg1Value, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSException arg0Value = (JSException)arg0Value_;
                arg0Value.writeMember(arg1Value, arg2Value, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void removeMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSException arg0Value = (JSException)arg0Value_;
                arg0Value.removeMember(arg1Value, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object invokeMember(Object arg0Value_, String arg1Value, Object ... arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, ArityException, UnsupportedTypeException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSException arg0Value = (JSException)arg0Value_;
                return arg0Value.invokeMember(arg1Value, arg2Value, INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasMetaObject(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSException arg0Value = (JSException)arg0Value_;
                return arg0Value.hasMetaObject(INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaObject(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSException arg0Value = (JSException)arg0Value_;
                return arg0Value.getMetaObject(INTEROP_LIBRARY_.getUncached());
            }
        }

        @GeneratedBy(value=JSException.class)
        private static final class Cached
        extends GraalJSExceptionGen.InteropLibraryExports.Cached {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @Node.Child
            private InteropLibrary thisLib;
            @Node.Child
            private InteropLibrary otherLib;
            @Node.Child
            private InteropLibrary delegateLib;

            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSException)receiver).getExceptionType();
            }

            @Override
            public boolean isExceptionIncompleteSource(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSException)receiver).isExceptionIncompleteSource();
            }

            @Override
            public boolean hasMembers(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSException)receiver).hasMembers();
            }

            @Override
            public Object getMembers(Object arg0Value_, boolean arg1Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSException arg0Value = (JSException)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 1) != 0) {
                    return arg0Value.getMembers(arg1Value, this.delegateLib);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getMembersNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object getMembersNode_AndSpecialize(JSException arg0Value, boolean arg1Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.delegateLib = super.insert(this.delegateLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.getMembers(arg1Value, this.delegateLib);
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
            public boolean isMemberReadable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSException arg0Value = (JSException)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 2) != 0) {
                    return arg0Value.isMemberReadable(arg1Value, this.delegateLib);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberReadableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberReadableNode_AndSpecialize(JSException arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.delegateLib = super.insert(this.delegateLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberReadable(arg1Value, this.delegateLib);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isMemberModifiable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSException arg0Value = (JSException)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 4) != 0) {
                    return arg0Value.isMemberModifiable(arg1Value, this.delegateLib);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberModifiableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberModifiableNode_AndSpecialize(JSException arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.delegateLib = super.insert(this.delegateLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberModifiable(arg1Value, this.delegateLib);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isMemberInsertable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSException arg0Value = (JSException)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 8) != 0) {
                    return arg0Value.isMemberInsertable(arg1Value, this.delegateLib);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberInsertableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberInsertableNode_AndSpecialize(JSException arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.delegateLib = super.insert(this.delegateLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberInsertable(arg1Value, this.delegateLib);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isMemberRemovable(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSException arg0Value = (JSException)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x10) != 0) {
                    return arg0Value.isMemberRemovable(arg1Value, this.delegateLib);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberRemovableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberRemovableNode_AndSpecialize(JSException arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.delegateLib = super.insert(this.delegateLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberRemovable(arg1Value, this.delegateLib);
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
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSException arg0Value = (JSException)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x20) != 0) {
                    return arg0Value.isMemberInvocable(arg1Value, this.delegateLib);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isMemberInvocableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isMemberInvocableNode_AndSpecialize(JSException arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.delegateLib = super.insert(this.delegateLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isMemberInvocable(arg1Value, this.delegateLib);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasMemberReadSideEffects(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSException arg0Value = (JSException)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x40) != 0) {
                    return arg0Value.hasMemberReadSideEffects(arg1Value, this.delegateLib);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.hasMemberReadSideEffectsNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean hasMemberReadSideEffectsNode_AndSpecialize(JSException arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.delegateLib = super.insert(this.delegateLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.hasMemberReadSideEffects(arg1Value, this.delegateLib);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasMemberWriteSideEffects(Object arg0Value_, String arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSException arg0Value = (JSException)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x80) != 0) {
                    return arg0Value.hasMemberWriteSideEffects(arg1Value, this.delegateLib);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.hasMemberWriteSideEffectsNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean hasMemberWriteSideEffectsNode_AndSpecialize(JSException arg0Value, String arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.delegateLib = super.insert(this.delegateLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.hasMemberWriteSideEffects(arg1Value, this.delegateLib);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object readMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSException arg0Value = (JSException)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x100) != 0) {
                    return arg0Value.readMember(arg1Value, this.delegateLib);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readMemberNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object readMemberNode_AndSpecialize(JSException arg0Value, String arg1Value) throws UnknownIdentifierException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.delegateLib = super.insert(this.delegateLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.readMember(arg1Value, this.delegateLib);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeMember(Object arg0Value_, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSException arg0Value = (JSException)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x200) != 0) {
                    arg0Value.writeMember(arg1Value, arg2Value, this.delegateLib);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeMemberNode_AndSpecialize(JSException arg0Value, String arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.delegateLib = super.insert(this.delegateLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeMember(arg1Value, arg2Value, this.delegateLib);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void removeMember(Object arg0Value_, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSException arg0Value = (JSException)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x400) != 0) {
                    arg0Value.removeMember(arg1Value, this.delegateLib);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.removeMemberNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void removeMemberNode_AndSpecialize(JSException arg0Value, String arg1Value) throws UnsupportedMessageException, UnknownIdentifierException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.delegateLib = super.insert(this.delegateLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
                    this.state_0_ = state_0 |= 0x400;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.removeMember(arg1Value, this.delegateLib);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object invokeMember(Object arg0Value_, String arg1Value, Object ... arg2Value) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSException arg0Value = (JSException)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x800) != 0) {
                    return arg0Value.invokeMember(arg1Value, arg2Value, this.delegateLib);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.invokeMemberNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object invokeMemberNode_AndSpecialize(JSException arg0Value, String arg1Value, Object[] arg2Value) throws UnsupportedMessageException, UnknownIdentifierException, ArityException, UnsupportedTypeException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.delegateLib = super.insert(this.delegateLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
                    this.state_0_ = state_0 |= 0x800;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.invokeMember(arg1Value, arg2Value, this.delegateLib);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasMetaObject(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSException arg0Value = (JSException)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x1000) != 0) {
                    return arg0Value.hasMetaObject(this.delegateLib);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.hasMetaObjectNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean hasMetaObjectNode_AndSpecialize(JSException arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.delegateLib = super.insert(this.delegateLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
                    this.state_0_ = state_0 |= 0x1000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.hasMetaObject(this.delegateLib);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object getMetaObject(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSException arg0Value = (JSException)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x2000) != 0) {
                    return arg0Value.getMetaObject(this.delegateLib);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getMetaObjectNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object getMetaObjectNode_AndSpecialize(JSException arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.delegateLib = super.insert(this.delegateLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.delegateLib);
                    this.state_0_ = state_0 |= 0x2000;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.getMetaObject(this.delegateLib);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }
        }
    }
}

