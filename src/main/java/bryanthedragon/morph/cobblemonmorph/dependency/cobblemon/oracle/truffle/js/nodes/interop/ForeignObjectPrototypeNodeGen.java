/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ForeignObjectPrototypeNode.class)
public final class ForeignObjectPrototypeNodeGen
extends ForeignObjectPrototypeNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private TruffleObject0Data truffleObject0_cache;

    private ForeignObjectPrototypeNodeGen() {
    }

    @Override
    @ExplodeLoop
    public JSDynamicObject execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
                TruffleObject0Data s0_ = this.truffleObject0_cache;
                while (s0_ != null) {
                    if (s0_.interop_.accepts(arg0Value)) {
                        return this.doTruffleObject(arg0Value, s0_.interop_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0) {
                return this.truffleObject1Boundary(state_0, arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private JSDynamicObject truffleObject1Boundary(int state_0, Object arg0Value) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
            JSDynamicObject jSDynamicObject = this.doTruffleObject(arg0Value, truffleObject1_interop__);
            return jSDynamicObject;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private JSDynamicObject executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            JSDynamicObject jSDynamicObject;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
                int count0_ = 0;
                TruffleObject0Data s0_ = this.truffleObject0_cache;
                if ((state_0 & 1) != 0) {
                    while (s0_ != null && !s0_.interop_.accepts(arg0Value)) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && count0_ < 5) {
                    s0_ = super.insert(new TruffleObject0Data(this.truffleObject0_cache));
                    s0_.interop_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                    VarHandle.storeStoreFence();
                    this.truffleObject0_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject2 = this.doTruffleObject(arg0Value, s0_.interop_);
                    return jSDynamicObject2;
                }
            }
            InteropLibrary truffleObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                this.exclude_ = exclude |= 1;
                this.truffleObject0_cache = null;
                state_0 &= 0xFFFFFFFE;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                jSDynamicObject = this.doTruffleObject(arg0Value, truffleObject1_interop__);
                encapsulating_.set(prev_);
            }
            catch (Throwable throwable) {
                encapsulating_.set(prev_);
                throw throwable;
            }
            return jSDynamicObject;
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        TruffleObject0Data s0_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.truffleObject0_cache) == null || s0_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doTruffleObject";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            TruffleObject0Data s0_ = this.truffleObject0_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.interop_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doTruffleObject";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(new Object[0]));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static ForeignObjectPrototypeNode create() {
        return new ForeignObjectPrototypeNodeGen();
    }

    public static ForeignObjectPrototypeNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=ForeignObjectPrototypeNode.class)
    @DenyReplace
    private static final class Uncached
    extends ForeignObjectPrototypeNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public JSDynamicObject execute(Object arg0Value) {
            return this.doTruffleObject(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value));
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

    @GeneratedBy(value=ForeignObjectPrototypeNode.class)
    private static final class TruffleObject0Data
    extends Node {
        @Node.Child
        TruffleObject0Data next_;
        @Node.Child
        InteropLibrary interop_;

        TruffleObject0Data(TruffleObject0Data next_) {
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

