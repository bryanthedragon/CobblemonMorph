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
import com.oracle.truffle.regex.runtime.nodes.ToIntNode;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ToIntNode.class)
public final class ToIntNodeGen
extends ToIntNode {
    private static final Uncached UNCACHED = new Uncached();
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private Boxed0Data boxed0_cache;

    private ToIntNodeGen() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @ExplodeLoop
    public int execute(Object arg0Value) throws UnsupportedTypeException {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return ToIntNode.doPrimitiveInt(arg0Value_);
        }
        if ((state_0 & 6) != 0) {
            if ((state_0 & 2) != 0) {
                Boxed0Data s1_ = this.boxed0_cache;
                while (s1_ != null) {
                    if (s1_.args_.accepts(arg0Value) && s1_.args_.fitsInInt(arg0Value)) {
                        return ToIntNode.doBoxed(arg0Value, s1_.args_);
                    }
                    s1_ = s1_.next_;
                }
            }
            if ((state_0 & 4) != 0) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary boxed1_args__ = INTEROP_LIBRARY_.getUncached();
                    if (boxed1_args__.fitsInInt(arg0Value)) {
                        int n = this.boxed1Boundary(state_0, arg0Value);
                        return n;
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
    private int boxed1Boundary(int state_0, Object arg0Value) throws UnsupportedTypeException {
        InteropLibrary boxed1_args__ = INTEROP_LIBRARY_.getUncached();
        return ToIntNode.doBoxed(arg0Value, boxed1_args__);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private int executeAndSpecialize(Object arg0Value) throws UnsupportedTypeException {
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
                int n = ToIntNode.doPrimitiveInt(arg0Value_);
                return n;
            }
            if (exclude == 0) {
                InteropLibrary args__2;
                int count1_ = 0;
                Boxed0Data s1_ = this.boxed0_cache;
                if ((state_0 & 2) != 0) {
                    while (!(s1_ == null || s1_.args_.accepts(arg0Value) && s1_.args_.fitsInInt(arg0Value))) {
                        s1_ = s1_.next_;
                        ++count1_;
                    }
                }
                if (s1_ == null && (args__2 = super.insert(INTEROP_LIBRARY_.create(arg0Value))).fitsInInt(arg0Value) && count1_ < 2) {
                    s1_ = super.insert(new Boxed0Data(this.boxed0_cache));
                    s1_.args_ = s1_.insertAccessor(args__2);
                    VarHandle.storeStoreFence();
                    this.boxed0_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                }
                if (s1_ != null) {
                    lock.unlock();
                    hasLock = false;
                    int args__2 = ToIntNode.doBoxed(arg0Value, s1_.args_);
                    return args__2;
                }
            }
            InteropLibrary boxed1_args__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                boxed1_args__ = INTEROP_LIBRARY_.getUncached();
                if (boxed1_args__.fitsInInt(arg0Value)) {
                    this.exclude_ = exclude |= 1;
                    this.boxed0_cache = null;
                    state_0 &= 0xFFFFFFFD;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    int n = ToIntNode.doBoxed(arg0Value, boxed1_args__);
                    return n;
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
        Boxed0Data s1_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s1_ = this.boxed0_cache) == null || s1_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static ToIntNode create() {
        return new ToIntNodeGen();
    }

    public static ToIntNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=ToIntNode.class)
    @DenyReplace
    private static final class Uncached
    extends ToIntNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public int execute(Object arg0Value) throws UnsupportedTypeException {
            if (arg0Value instanceof Integer) {
                int arg0Value_ = (Integer)arg0Value;
                return ToIntNode.doPrimitiveInt(arg0Value_);
            }
            if (INTEROP_LIBRARY_.getUncached(arg0Value).fitsInInt(arg0Value)) {
                return ToIntNode.doBoxed(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value));
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

    @GeneratedBy(value=ToIntNode.class)
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

