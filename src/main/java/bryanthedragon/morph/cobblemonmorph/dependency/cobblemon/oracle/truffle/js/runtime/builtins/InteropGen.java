
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidBufferOffsetException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNode;
import com.oracle.truffle.js.nodes.interop.KeyInfoNode;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.nio.ByteOrder;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSArrayBufferObject.Interop.class)
final class InteropGen {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    private InteropGen() {
    }

    static {
        LibraryExport.register(JSArrayBufferObject.Interop.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSArrayBufferObject.Interop.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSArrayBufferObject.Interop.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSArrayBufferObject.Interop);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSArrayBufferObject.Interop);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSArrayBufferObject.Interop.class)
        @DenyReplace
        private static final class Uncached
        extends JSNonProxyObjectGen.InteropLibraryExports.Uncached {
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
            public boolean hasBufferElements(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSArrayBufferObject.Interop)receiver).hasBufferElements();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getBufferSize(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                return arg0Value.getBufferSize(BranchProfile.getUncached(), INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public byte readBufferByte(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                return arg0Value.readBufferByte(arg1Value, BranchProfile.getUncached(), INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public short readBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                return arg0Value.readBufferShort(arg1Value, arg2Value, BranchProfile.getUncached(), INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int readBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                return arg0Value.readBufferInt(arg1Value, arg2Value, BranchProfile.getUncached(), INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long readBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                return arg0Value.readBufferLong(arg1Value, arg2Value, BranchProfile.getUncached(), INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public float readBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                return arg0Value.readBufferFloat(arg1Value, arg2Value, BranchProfile.getUncached(), INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public double readBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                return arg0Value.readBufferDouble(arg1Value, arg2Value, BranchProfile.getUncached(), INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isBufferWritable(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                return arg0Value.isBufferWritable(INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferByte(Object arg0Value_, long arg1Value, byte arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                arg0Value.writeBufferByte(arg1Value, arg2Value, BranchProfile.getUncached(), INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value, short arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                arg0Value.writeBufferShort(arg1Value, arg2Value, arg3Value, BranchProfile.getUncached(), INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value, int arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                arg0Value.writeBufferInt(arg1Value, arg2Value, arg3Value, BranchProfile.getUncached(), INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value, long arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                arg0Value.writeBufferLong(arg1Value, arg2Value, arg3Value, BranchProfile.getUncached(), INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value, float arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                arg0Value.writeBufferFloat(arg1Value, arg2Value, arg3Value, BranchProfile.getUncached(), INTEROP_LIBRARY_.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value, double arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                arg0Value.writeBufferDouble(arg1Value, arg2Value, arg3Value, BranchProfile.getUncached(), INTEROP_LIBRARY_.getUncached());
            }
        }

        @GeneratedBy(value=JSArrayBufferObject.Interop.class)
        private static final class Cached
        extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @Node.Child
            private KeyInfoNode keyInfo;
            @Node.Child
            private JSInteropGetIteratorNode getIterator;
            @CompilerDirectives.CompilationFinal
            private BranchProfile errorBranch;
            @Node.Child
            private InteropLibrary interop;

            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public boolean hasBufferElements(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSArrayBufferObject.Interop)receiver).hasBufferElements();
            }

            @Override
            public long getBufferSize(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 1) != 0) {
                    return arg0Value.getBufferSize(this.errorBranch, this.interop);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.getBufferSizeNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private long getBufferSizeNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                    this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    long l = arg0Value.getBufferSize(this.errorBranch, this.interop);
                    return l;
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
            public byte readBufferByte(Object arg0Value_, long arg1Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 2) != 0) {
                    return arg0Value.readBufferByte(arg1Value, this.errorBranch, this.interop);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readBufferByteNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private byte readBufferByteNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, long arg1Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                    this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    byte by = arg0Value.readBufferByte(arg1Value, this.errorBranch, this.interop);
                    return by;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public short readBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 4) != 0) {
                    return arg0Value.readBufferShort(arg1Value, arg2Value, this.errorBranch, this.interop);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readBufferShortNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private short readBufferShortNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                    this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    short s = arg0Value.readBufferShort(arg1Value, arg2Value, this.errorBranch, this.interop);
                    return s;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public int readBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 8) != 0) {
                    return arg0Value.readBufferInt(arg1Value, arg2Value, this.errorBranch, this.interop);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readBufferIntNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private int readBufferIntNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                    this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    int n = arg0Value.readBufferInt(arg1Value, arg2Value, this.errorBranch, this.interop);
                    return n;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public long readBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x10) != 0) {
                    return arg0Value.readBufferLong(arg1Value, arg2Value, this.errorBranch, this.interop);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readBufferLongNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private long readBufferLongNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                    this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    long l = arg0Value.readBufferLong(arg1Value, arg2Value, this.errorBranch, this.interop);
                    return l;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public float readBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x20) != 0) {
                    return arg0Value.readBufferFloat(arg1Value, arg2Value, this.errorBranch, this.interop);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readBufferFloatNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private float readBufferFloatNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                    this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    float f = arg0Value.readBufferFloat(arg1Value, arg2Value, this.errorBranch, this.interop);
                    return f;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public double readBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x40) != 0) {
                    return arg0Value.readBufferDouble(arg1Value, arg2Value, this.errorBranch, this.interop);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readBufferDoubleNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private double readBufferDoubleNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                    this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    double d = arg0Value.readBufferDouble(arg1Value, arg2Value, this.errorBranch, this.interop);
                    return d;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isBufferWritable(Object arg0Value_) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x80) != 0) {
                    return arg0Value.isBufferWritable(this.interop);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isBufferWritableNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isBufferWritableNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isBufferWritable(this.interop);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeBufferByte(Object arg0Value_, long arg1Value, byte arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x100) != 0) {
                    arg0Value.writeBufferByte(arg1Value, arg2Value, this.errorBranch, this.interop);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeBufferByteNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeBufferByteNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, long arg1Value, byte arg2Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                    this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeBufferByte(arg1Value, arg2Value, this.errorBranch, this.interop);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeBufferShort(Object arg0Value_, ByteOrder arg1Value, long arg2Value, short arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x200) != 0) {
                    arg0Value.writeBufferShort(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeBufferShortNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeBufferShortNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value, short arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                    this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeBufferShort(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeBufferInt(Object arg0Value_, ByteOrder arg1Value, long arg2Value, int arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x400) != 0) {
                    arg0Value.writeBufferInt(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeBufferIntNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeBufferIntNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value, int arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                    this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                    this.state_0_ = state_0 |= 0x400;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeBufferInt(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeBufferLong(Object arg0Value_, ByteOrder arg1Value, long arg2Value, long arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x800) != 0) {
                    arg0Value.writeBufferLong(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeBufferLongNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeBufferLongNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value, long arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                    this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                    this.state_0_ = state_0 |= 0x800;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeBufferLong(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeBufferFloat(Object arg0Value_, ByteOrder arg1Value, long arg2Value, float arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x1000) != 0) {
                    arg0Value.writeBufferFloat(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeBufferFloatNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeBufferFloatNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value, float arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                    this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                    this.state_0_ = state_0 |= 0x1000;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeBufferFloat(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void writeBufferDouble(Object arg0Value_, ByteOrder arg1Value, long arg2Value, double arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSArrayBufferObject.Interop arg0Value = (JSArrayBufferObject.Interop)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x2000) != 0) {
                    arg0Value.writeBufferDouble(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeBufferDoubleNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeBufferDoubleNode_AndSpecialize(JSArrayBufferObject.Interop arg0Value, ByteOrder arg1Value, long arg2Value, double arg3Value) throws UnsupportedMessageException, InvalidBufferOffsetException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                    this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                    this.state_0_ = state_0 |= 0x2000;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeBufferDouble(arg1Value, arg2Value, arg3Value, this.errorBranch, this.interop);
                    return;
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

