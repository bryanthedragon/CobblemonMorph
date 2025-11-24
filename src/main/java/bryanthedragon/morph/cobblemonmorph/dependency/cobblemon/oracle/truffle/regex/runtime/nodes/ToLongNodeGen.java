/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.regex.runtime.nodes.ToLongNode;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ToLongNode.class)
public final class ToLongNodeGen
extends ToLongNode {
    private static final Uncached UNCACHED = new Uncached();
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private Boxed0Data boxed0_cache;

    private ToLongNodeGen() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @ExplodeLoop
    public long execute(Object arg0Value) throws UnsupportedTypeException {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return ToLongNode.doPrimitiveInt(arg0Value_);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            return ToLongNode.doPrimitiveLong(arg0Value_);
        }
        if ((state_0 & 0xC) != 0) {
            if ((state_0 & 4) != 0) {
                Boxed0Data s2_ = this.boxed0_cache;
                while (s2_ != null) {
                    if (s2_.args_.accepts(arg0Value) && s2_.args_.fitsInLong(arg0Value)) {
                        return ToLongNode.doBoxed(arg0Value, s2_.args_);
                    }
                    s2_ = s2_.next_;
                }
            }
            if ((state_0 & 8) != 0) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary boxed1_args__ = INTEROP_LIBRARY_.getUncached();
                    if (boxed1_args__.fitsInLong(arg0Value)) {
                        long l = this.boxed1Boundary(state_0, arg0Value);
                        return l;
                    }
                }
                finally {
                    encapsulating_.set(prev_);
                }
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    @CompilerDirectives.TruffleBoundary
    private long boxed1Boundary(int state_0, Object arg0Value) throws UnsupportedTypeException {
        InteropLibrary boxed1_args__ = INTEROP_LIBRARY_.getUncached();
        return ToLongNode.doBoxed(arg0Value, boxed1_args__);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private long executeAndSpecialize(Object arg0Value) throws UnsupportedTypeException {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof Integer) {
                int arg0Value_ = (Integer)arg0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                long l = ToLongNode.doPrimitiveInt(arg0Value_);
                return l;
            }
            if (arg0Value instanceof Long) {
                long arg0Value_ = (Long)arg0Value;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                long l = ToLongNode.doPrimitiveLong(arg0Value_);
                return l;
            }
            if (exclude == 0) {
                InteropLibrary args__2;
                int count2_ = 0;
                Boxed0Data s2_ = this.boxed0_cache;
                if ((state_0 & 4) != 0) {
                    while (!(s2_ == null || s2_.args_.accepts(arg0Value) && s2_.args_.fitsInLong(arg0Value))) {
                        s2_ = s2_.next_;
                        ++count2_;
                    }
                }
                if (s2_ == null && (args__2 = super.insert(INTEROP_LIBRARY_.create(arg0Value))).fitsInLong(arg0Value) && count2_ < 2) {
                    s2_ = super.insert(new Boxed0Data(this.boxed0_cache));
                    s2_.args_ = s2_.insertAccessor(args__2);
                    VarHandle.storeStoreFence();
                    this.boxed0_cache = s2_;
                    this.state_0_ = state_0 |= 4;
                }
                if (s2_ != null) {
                    lock.unlock();
                    hasLock = false;
                    long args__2 = ToLongNode.doBoxed(arg0Value, s2_.args_);
                    return args__2;
                }
            }
            InteropLibrary boxed1_args__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                boxed1_args__ = INTEROP_LIBRARY_.getUncached();
                if (boxed1_args__.fitsInLong(arg0Value)) {
                    this.exclude_ = exclude |= 1;
                    this.boxed0_cache = null;
                    state_0 &= 0xFFFFFFFB;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    long l = ToLongNode.doBoxed(arg0Value, boxed1_args__);
                    return l;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        Boxed0Data s2_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s2_ = this.boxed0_cache) == null || s2_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static ToLongNode create() {
        return new ToLongNodeGen();
    }

    public static ToLongNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=ToLongNode.class)
    @DenyReplace
    private static final class Uncached
    extends ToLongNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public long execute(Object arg0Value) throws UnsupportedTypeException {
            if (arg0Value instanceof Integer) {
                int arg0Value_ = (Integer)arg0Value;
                return ToLongNode.doPrimitiveInt(arg0Value_);
            }
            if (arg0Value instanceof Long) {
                long arg0Value_ = (Long)arg0Value;
                return ToLongNode.doPrimitiveLong(arg0Value_);
            }
            if (INTEROP_LIBRARY_.getUncached(arg0Value).fitsInLong(arg0Value)) {
                return ToLongNode.doBoxed(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value));
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }
    }

    @GeneratedBy(value=ToLongNode.class)
    private static final class Boxed0Data
    extends Node {
        @Node.Child
        Boxed0Data next_;
        @Node.Child
        InteropLibrary args_;

        Boxed0Data(Boxed0Data next_) {
            this.next_ = next_;
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }
}

