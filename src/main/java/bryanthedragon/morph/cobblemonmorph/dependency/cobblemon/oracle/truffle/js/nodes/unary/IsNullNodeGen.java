/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.IsNullNode;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=IsNullNode.class)
public final class IsNullNodeGen
extends IsNullNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private Cached0Data cached0_cache;

    private IsNullNodeGen(JavaScriptNode operand, boolean leftConstant) {
        super(operand, leftConstant);
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isJSNull(operandNodeValue)) {
                return IsNullNode.doNull(operandNodeValue);
            }
            if ((state_0 & 2) != 0 && JSGuards.isUndefined(operandNodeValue)) {
                return IsNullNode.doUndefined(operandNodeValue);
            }
            if ((state_0 & 4) != 0 && JSGuards.isJSObject(operandNodeValue)) {
                return IsNullNode.doObject(operandNodeValue);
            }
            if ((state_0 & 8) != 0) {
                Cached0Data s3_ = this.cached0_cache;
                while (s3_ != null) {
                    if (s3_.interop_.accepts(operandNodeValue) && !JSGuards.isJSDynamicObject(operandNodeValue)) {
                        return IsNullNode.doCached(operandNodeValue, s3_.interop_);
                    }
                    s3_ = s3_.next_;
                }
            }
            if ((state_0 & 0x10) != 0 && !JSGuards.isJSDynamicObject(operandNodeValue)) {
                return this.cached1Boundary(state_0, operandNodeValue);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private Object cached1Boundary(int state_0, Object operandNodeValue) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary cached1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
            Boolean bl = IsNullNode.doCached(operandNodeValue, cached1_interop__);
            return bl;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isJSNull(operandNodeValue_)) {
                return IsNullNode.doNull(operandNodeValue_);
            }
            if ((state_0 & 2) != 0 && JSGuards.isUndefined(operandNodeValue_)) {
                return IsNullNode.doUndefined(operandNodeValue_);
            }
            if ((state_0 & 4) != 0 && JSGuards.isJSObject(operandNodeValue_)) {
                return IsNullNode.doObject(operandNodeValue_);
            }
            if ((state_0 & 8) != 0) {
                Cached0Data s3_ = this.cached0_cache;
                while (s3_ != null) {
                    if (s3_.interop_.accepts(operandNodeValue_) && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
                        return IsNullNode.doCached(operandNodeValue_, s3_.interop_);
                    }
                    s3_ = s3_.next_;
                }
            }
            if ((state_0 & 0x10) != 0 && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
                return this.cached1Boundary0(state_0, operandNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private Object cached1Boundary0(int state_0, Object operandNodeValue_) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary cached1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue_);
            Boolean bl = IsNullNode.doCached(operandNodeValue_, cached1_interop__);
            return bl;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @Override
    @ExplodeLoop
    public boolean executeBoolean(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isJSNull(operandNodeValue_)) {
                return IsNullNode.doNull(operandNodeValue_);
            }
            if ((state_0 & 2) != 0 && JSGuards.isUndefined(operandNodeValue_)) {
                return IsNullNode.doUndefined(operandNodeValue_);
            }
            if ((state_0 & 4) != 0 && JSGuards.isJSObject(operandNodeValue_)) {
                return IsNullNode.doObject(operandNodeValue_);
            }
            if ((state_0 & 8) != 0) {
                Cached0Data s3_ = this.cached0_cache;
                while (s3_ != null) {
                    if (s3_.interop_.accepts(operandNodeValue_) && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
                        return IsNullNode.doCached(operandNodeValue_, s3_.interop_);
                    }
                    s3_ = s3_.next_;
                }
            }
            if ((state_0 & 0x10) != 0 && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
                return this.cached1Boundary1(state_0, operandNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private boolean cached1Boundary1(int state_0, Object operandNodeValue_) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary cached1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue_);
            boolean bl = IsNullNode.doCached(operandNodeValue_, cached1_interop__);
            return bl;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.executeBoolean(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeAndSpecialize(Object operandNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (JSGuards.isJSNull(operandNodeValue)) {
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = IsNullNode.doNull(operandNodeValue);
                return bl;
            }
            if (JSGuards.isUndefined(operandNodeValue)) {
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                boolean bl = IsNullNode.doUndefined(operandNodeValue);
                return bl;
            }
            if (JSGuards.isJSObject(operandNodeValue)) {
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                boolean bl = IsNullNode.doObject(operandNodeValue);
                return bl;
            }
            if (exclude == 0) {
                int count3_ = 0;
                Cached0Data s3_ = this.cached0_cache;
                if ((state_0 & 8) != 0) {
                    while (s3_ != null && (!s3_.interop_.accepts(operandNodeValue) || JSGuards.isJSDynamicObject(operandNodeValue))) {
                        s3_ = s3_.next_;
                        ++count3_;
                    }
                }
                if (s3_ == null && !JSGuards.isJSDynamicObject(operandNodeValue) && count3_ < 5) {
                    s3_ = super.insert(new Cached0Data(this.cached0_cache));
                    s3_.interop_ = s3_.insertAccessor(INTEROP_LIBRARY_.create(operandNodeValue));
                    VarHandle.storeStoreFence();
                    this.cached0_cache = s3_;
                    this.state_0_ = state_0 |= 8;
                }
                if (s3_ != null) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl = IsNullNode.doCached(operandNodeValue, s3_.interop_);
                    return bl;
                }
            }
            InteropLibrary cached1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (!JSGuards.isJSDynamicObject(operandNodeValue)) {
                    cached1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
                    this.exclude_ = exclude |= 1;
                    this.cached0_cache = null;
                    state_0 &= 0xFFFFFFF7;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = IsNullNode.doCached(operandNodeValue, cached1_interop__);
                    return bl;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.operandNode}, operandNodeValue);
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        Cached0Data s3_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s3_ = this.cached0_cache) == null || s3_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[6];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doNull";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doObject";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doCached";
        if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            Cached0Data s3_ = this.cached0_cache;
            while (s3_ != null) {
                cached.add(Arrays.asList(s3_.interop_));
                s3_ = s3_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[4] = s;
        s = new Object[3];
        s[0] = "doCached";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(new Object[0]));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[5] = s;
        return Introspection.Provider.create(data);
    }

    public static IsNullNode create(JavaScriptNode operand, boolean leftConstant) {
        return new IsNullNodeGen(operand, leftConstant);
    }

    @GeneratedBy(value=IsNullNode.class)
    private static final class Cached0Data
    extends Node {
        @Node.Child
        Cached0Data next_;
        @Node.Child
        InteropLibrary interop_;

        Cached0Data(Cached0Data next_) {
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

