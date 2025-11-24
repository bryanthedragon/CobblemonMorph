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
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=IsCallableNode.class)
public final class IsCallableNodeGen
extends IsCallableNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private Shape jSFunctionShape_shape_;
    @Node.Child
    private TruffleObject0Data truffleObject0_cache;

    private IsCallableNodeGen() {
    }

    @Override
    @ExplodeLoop
    public boolean executeBoolean(Object arg0Value) {
        Object arg0Value_;
        int state_0 = this.state_0_;
        if ((state_0 & 0xF) != 0 && arg0Value instanceof JSDynamicObject) {
            arg0Value_ = (JSDynamicObject)arg0Value;
            if ((state_0 & 1) != 0 && this.jSFunctionShape_shape_.check((DynamicObject)arg0Value_)) {
                assert (JSGuards.isJSFunctionShape(this.jSFunctionShape_shape_));
                return IsCallableNode.doJSFunctionShape((JSDynamicObject)arg0Value_, this.jSFunctionShape_shape_);
            }
            if ((state_0 & 2) != 0 && JSGuards.isJSFunction(arg0Value_)) {
                return IsCallableNode.doJSFunction((JSDynamicObject)arg0Value_);
            }
            if ((state_0 & 4) != 0 && JSGuards.isJSProxy(arg0Value_)) {
                return IsCallableNode.doJSProxy((JSDynamicObject)arg0Value_);
            }
            if ((state_0 & 8) != 0 && JSGuards.isJSDynamicObject(arg0Value_) && !JSGuards.isJSFunction(arg0Value_) && !JSGuards.isJSProxy(arg0Value_)) {
                return IsCallableNode.doJSTypeOther((JSDynamicObject)arg0Value_);
            }
        }
        if ((state_0 & 0x30) != 0) {
            if ((state_0 & 0x10) != 0) {
                TruffleObject0Data s4_ = this.truffleObject0_cache;
                while (s4_ != null) {
                    if (s4_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                        return IsCallableNode.doTruffleObject(arg0Value, s4_.interop_);
                    }
                    s4_ = s4_.next_;
                }
            }
            if ((state_0 & 0x20) != 0 && JSGuards.isForeignObject(arg0Value)) {
                return this.truffleObject1Boundary(state_0, arg0Value);
            }
        }
        if ((state_0 & 0x40) != 0 && arg0Value instanceof TruffleString) {
            arg0Value_ = (TruffleString)arg0Value;
            return IsCallableNode.doString((TruffleString)arg0Value_);
        }
        if ((state_0 & 0x80) != 0 && arg0Value instanceof Number) {
            arg0Value_ = (Number)arg0Value;
            return IsCallableNode.doNumber((Number)arg0Value_);
        }
        if ((state_0 & 0x100) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_2 = (Boolean)arg0Value;
            return IsCallableNode.doBoolean(arg0Value_2);
        }
        if ((state_0 & 0x200) != 0 && arg0Value instanceof Symbol) {
            arg0Value_ = (Symbol)arg0Value;
            return IsCallableNode.doSymbol((Symbol)arg0Value_);
        }
        if ((state_0 & 0x400) != 0 && arg0Value instanceof BigInt) {
            arg0Value_ = (BigInt)arg0Value;
            return IsCallableNode.doBigInt((BigInt)arg0Value_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private boolean truffleObject1Boundary(int state_0, Object arg0Value) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
            boolean bl = IsCallableNode.doTruffleObject(arg0Value, truffleObject1_interop__);
            return bl;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            Object arg0Value_;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof JSDynamicObject) {
                boolean JSFunctionShape_duplicateFound_;
                JSDynamicObject arg0Value_2 = (JSDynamicObject)arg0Value;
                if ((exclude & 1) == 0) {
                    Shape jSFunctionShape_shape__2;
                    JSFunctionShape_duplicateFound_ = false;
                    if ((state_0 & 1) != 0 && this.jSFunctionShape_shape_.check(arg0Value_2)) {
                        assert (JSGuards.isJSFunctionShape(this.jSFunctionShape_shape_));
                        JSFunctionShape_duplicateFound_ = true;
                    }
                    if (!JSFunctionShape_duplicateFound_ && (jSFunctionShape_shape__2 = arg0Value_2.getShape()).check(arg0Value_2) && JSGuards.isJSFunctionShape(jSFunctionShape_shape__2) && (state_0 & 1) == 0) {
                        this.jSFunctionShape_shape_ = jSFunctionShape_shape__2;
                        this.state_0_ = state_0 |= 1;
                        JSFunctionShape_duplicateFound_ = true;
                    }
                    if (JSFunctionShape_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        boolean jSFunctionShape_shape__2 = IsCallableNode.doJSFunctionShape(arg0Value_2, this.jSFunctionShape_shape_);
                        return jSFunctionShape_shape__2;
                    }
                }
                if (JSGuards.isJSFunction(arg0Value_2)) {
                    this.exclude_ = exclude |= 1;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    JSFunctionShape_duplicateFound_ = IsCallableNode.doJSFunction(arg0Value_2);
                    return JSFunctionShape_duplicateFound_;
                }
                if (JSGuards.isJSProxy(arg0Value_2)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    JSFunctionShape_duplicateFound_ = IsCallableNode.doJSProxy(arg0Value_2);
                    return JSFunctionShape_duplicateFound_;
                }
                if (JSGuards.isJSDynamicObject(arg0Value_2) && !JSGuards.isJSFunction(arg0Value_2) && !JSGuards.isJSProxy(arg0Value_2)) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    JSFunctionShape_duplicateFound_ = IsCallableNode.doJSTypeOther(arg0Value_2);
                    return JSFunctionShape_duplicateFound_;
                }
            }
            if ((exclude & 2) == 0) {
                int count4_ = 0;
                TruffleObject0Data s4_ = this.truffleObject0_cache;
                if ((state_0 & 0x10) != 0) {
                    while (!(s4_ == null || s4_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value))) {
                        s4_ = s4_.next_;
                        ++count4_;
                    }
                }
                if (s4_ == null && JSGuards.isForeignObject(arg0Value) && count4_ < 5) {
                    s4_ = super.insert(new TruffleObject0Data(this.truffleObject0_cache));
                    s4_.interop_ = s4_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                    VarHandle.storeStoreFence();
                    this.truffleObject0_cache = s4_;
                    this.state_0_ = state_0 |= 0x10;
                }
                if (s4_ != null) {
                    lock.unlock();
                    hasLock = false;
                    boolean jSFunctionShape_shape__2 = IsCallableNode.doTruffleObject(arg0Value, s4_.interop_);
                    return jSFunctionShape_shape__2;
                }
            }
            InteropLibrary truffleObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (JSGuards.isForeignObject(arg0Value)) {
                    truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                    this.exclude_ = exclude |= 2;
                    this.truffleObject0_cache = null;
                    state_0 &= 0xFFFFFFEF;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = IsCallableNode.doTruffleObject(arg0Value, truffleObject1_interop__);
                    return bl;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            if (arg0Value instanceof TruffleString) {
                arg0Value_ = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                boolean bl = IsCallableNode.doString((TruffleString)arg0Value_);
                return bl;
            }
            if (arg0Value instanceof Number) {
                arg0Value_ = (Number)arg0Value;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                boolean bl = IsCallableNode.doNumber((Number)arg0Value_);
                return bl;
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_3 = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                boolean bl = IsCallableNode.doBoolean(arg0Value_3);
                return bl;
            }
            if (arg0Value instanceof Symbol) {
                arg0Value_ = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                boolean bl = IsCallableNode.doSymbol((Symbol)arg0Value_);
                return bl;
            }
            if (arg0Value instanceof BigInt) {
                arg0Value_ = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                boolean bl = IsCallableNode.doBigInt((BigInt)arg0Value_);
                return bl;
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
        TruffleObject0Data s4_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s4_ = this.truffleObject0_cache) == null || s4_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[12];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doJSFunctionShape";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.jSFunctionShape_shape_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doJSFunction";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doJSProxy";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doJSTypeOther";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doTruffleObject";
        if ((state_0 & 0x10) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            TruffleObject0Data s4_ = this.truffleObject0_cache;
            while (s4_ != null) {
                cached.add(Arrays.asList(s4_.interop_));
                s4_ = s4_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[5] = s;
        s = new Object[3];
        s[0] = "doTruffleObject";
        if ((state_0 & 0x20) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(new Object[0]));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[6] = s;
        s = new Object[3];
        s[0] = "doString";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doNumber";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[10] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[11] = s;
        return Introspection.Provider.create(data);
    }

    public static IsCallableNode create() {
        return new IsCallableNodeGen();
    }

    public static IsCallableNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=IsCallableNode.class)
    @DenyReplace
    private static final class Uncached
    extends IsCallableNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean executeBoolean(Object arg0Value) {
            Object arg0Value_;
            if (arg0Value instanceof JSDynamicObject) {
                arg0Value_ = (JSDynamicObject)arg0Value;
                if (JSGuards.isJSFunction(arg0Value_)) {
                    return IsCallableNode.doJSFunction((JSDynamicObject)arg0Value_);
                }
                if (JSGuards.isJSProxy(arg0Value_)) {
                    return IsCallableNode.doJSProxy((JSDynamicObject)arg0Value_);
                }
                if (JSGuards.isJSDynamicObject(arg0Value_) && !JSGuards.isJSFunction(arg0Value_) && !JSGuards.isJSProxy(arg0Value_)) {
                    return IsCallableNode.doJSTypeOther((JSDynamicObject)arg0Value_);
                }
            }
            if (JSGuards.isForeignObject(arg0Value)) {
                return IsCallableNode.doTruffleObject(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value));
            }
            if (arg0Value instanceof TruffleString) {
                arg0Value_ = (TruffleString)arg0Value;
                return IsCallableNode.doString((TruffleString)arg0Value_);
            }
            if (arg0Value instanceof Number) {
                arg0Value_ = (Number)arg0Value;
                return IsCallableNode.doNumber((Number)arg0Value_);
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_2 = (Boolean)arg0Value;
                return IsCallableNode.doBoolean(arg0Value_2);
            }
            if (arg0Value instanceof Symbol) {
                arg0Value_ = (Symbol)arg0Value;
                return IsCallableNode.doSymbol((Symbol)arg0Value_);
            }
            if (arg0Value instanceof BigInt) {
                arg0Value_ = (BigInt)arg0Value;
                return IsCallableNode.doBigInt((BigInt)arg0Value_);
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

    @GeneratedBy(value=IsCallableNode.class)
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

