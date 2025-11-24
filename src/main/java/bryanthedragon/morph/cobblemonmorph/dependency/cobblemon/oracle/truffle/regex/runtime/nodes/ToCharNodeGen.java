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
import com.oracle.truffle.regex.runtime.nodes.ToCharNode;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ToCharNode.class)
public final class ToCharNodeGen
extends ToCharNode {
    private static final Uncached UNCACHED = new Uncached();
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private Long0Data long0_cache;

    private ToCharNodeGen() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    @ExplodeLoop
    public char execute(Object arg0Value) throws UnsupportedTypeException {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Byte) {
            byte arg0Value_ = (Byte)arg0Value;
            return ToCharNode.doByte(arg0Value_);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof Character) {
            char arg0Value_ = ((Character)arg0Value).charValue();
            return ToCharNode.doChar(arg0Value_);
        }
        if ((state_0 & 0xC) != 0) {
            if ((state_0 & 4) != 0) {
                Long0Data s2_ = this.long0_cache;
                while (s2_ != null) {
                    if (s2_.args_.accepts(arg0Value) && s2_.args_.fitsInInt(arg0Value)) {
                        return ToCharNode.doLong(arg0Value, s2_.args_);
                    }
                    s2_ = s2_.next_;
                }
            }
            if ((state_0 & 8) != 0) {
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    InteropLibrary long1_args__ = INTEROP_LIBRARY_.getUncached();
                    if (long1_args__.fitsInInt(arg0Value)) {
                        char c = this.long1Boundary(state_0, arg0Value);
                        return c;
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
    private char long1Boundary(int state_0, Object arg0Value) throws UnsupportedTypeException {
        InteropLibrary long1_args__ = INTEROP_LIBRARY_.getUncached();
        return ToCharNode.doLong(arg0Value, long1_args__);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private char executeAndSpecialize(Object arg0Value) throws UnsupportedTypeException {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof Byte) {
                byte arg0Value_ = (Byte)arg0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                char c = ToCharNode.doByte(arg0Value_);
                return c;
            }
            if (arg0Value instanceof Character) {
                char arg0Value_ = ((Character)arg0Value).charValue();
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                char c = ToCharNode.doChar(arg0Value_);
                return c;
            }
            if (exclude == 0) {
                InteropLibrary args__2;
                int count2_ = 0;
                Long0Data s2_ = this.long0_cache;
                if ((state_0 & 4) != 0) {
                    while (!(s2_ == null || s2_.args_.accepts(arg0Value) && s2_.args_.fitsInInt(arg0Value))) {
                        s2_ = s2_.next_;
                        ++count2_;
                    }
                }
                if (s2_ == null && (args__2 = super.insert(INTEROP_LIBRARY_.create(arg0Value))).fitsInInt(arg0Value) && count2_ < 2) {
                    s2_ = super.insert(new Long0Data(this.long0_cache));
                    s2_.args_ = s2_.insertAccessor(args__2);
                    VarHandle.storeStoreFence();
                    this.long0_cache = s2_;
                    this.state_0_ = state_0 |= 4;
                }
                if (s2_ != null) {
                    lock.unlock();
                    hasLock = false;
                    char args__2 = ToCharNode.doLong(arg0Value, s2_.args_);
                    return args__2;
                }
            }
            InteropLibrary long1_args__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                long1_args__ = INTEROP_LIBRARY_.getUncached();
                if (long1_args__.fitsInInt(arg0Value)) {
                    this.exclude_ = exclude |= 1;
                    this.long0_cache = null;
                    state_0 &= 0xFFFFFFFB;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    char c = ToCharNode.doLong(arg0Value, long1_args__);
                    return c;
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
        Long0Data s2_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s2_ = this.long0_cache) == null || s2_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static ToCharNode create() {
        return new ToCharNodeGen();
    }

    public static ToCharNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=ToCharNode.class)
    @DenyReplace
    private static final class Uncached
    extends ToCharNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public char execute(Object arg0Value) throws UnsupportedTypeException {
            if (arg0Value instanceof Byte) {
                byte arg0Value_ = (Byte)arg0Value;
                return ToCharNode.doByte(arg0Value_);
            }
            if (arg0Value instanceof Character) {
                char arg0Value_ = ((Character)arg0Value).charValue();
                return ToCharNode.doChar(arg0Value_);
            }
            if (INTEROP_LIBRARY_.getUncached(arg0Value).fitsInInt(arg0Value)) {
                return ToCharNode.doLong(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value));
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

    @GeneratedBy(value=ToCharNode.class)
    private static final class Long0Data
    extends Node {
        @Node.Child
        Long0Data next_;
        @Node.Child
        InteropLibrary args_;

        Long0Data(Long0Data next_) {
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

