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
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.unary.IsConstructorNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=IsConstructorNode.class)
public final class IsConstructorNodeGen
extends IsConstructorNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private TruffleObject0Data truffleObject0_cache;

    private IsConstructorNodeGen() {
    }

    @Override
    @ExplodeLoop
    public boolean executeBoolean(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof JSFunctionObject) {
            JSFunctionObject arg0Value_ = (JSFunctionObject)arg0Value;
            return IsConstructorNode.doJSFunction(arg0Value_);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof JSProxyObject) {
            JSProxyObject arg0Value_ = (JSProxyObject)arg0Value;
            return IsConstructorNode.doJSProxy(arg0Value_);
        }
        if ((state_0 & 4) != 0 && JSGuards.isJSDynamicObject(arg0Value) && !JSGuards.isJSFunction(arg0Value) && !JSGuards.isJSProxy(arg0Value)) {
            return IsConstructorNode.doOther(arg0Value);
        }
        if ((state_0 & 8) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return IsConstructorNode.doString(arg0Value_);
        }
        if ((state_0 & 0x10) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return IsConstructorNode.doBoolean(arg0Value_);
        }
        if ((state_0 & 0x20) != 0 && arg0Value instanceof Number) {
            Number arg0Value_ = (Number)arg0Value;
            return IsConstructorNode.doNumber(arg0Value_);
        }
        if ((state_0 & 0x40) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return IsConstructorNode.doSymbol(arg0Value_);
        }
        if ((state_0 & 0x80) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return IsConstructorNode.doBigInt(arg0Value_);
        }
        if ((state_0 & 0x300) != 0) {
            if ((state_0 & 0x100) != 0) {
                TruffleObject0Data s8_ = this.truffleObject0_cache;
                while (s8_ != null) {
                    if (s8_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                        return IsConstructorNode.doTruffleObject(arg0Value, s8_.interop_);
                    }
                    s8_ = s8_.next_;
                }
            }
            if ((state_0 & 0x200) != 0 && JSGuards.isForeignObject(arg0Value)) {
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
    private boolean truffleObject1Boundary(int state_0, Object arg0Value) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
            boolean bl = IsConstructorNode.doTruffleObject(arg0Value, truffleObject1_interop__);
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
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof JSFunctionObject) {
                JSFunctionObject arg0Value_ = (JSFunctionObject)arg0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = IsConstructorNode.doJSFunction(arg0Value_);
                return bl;
            }
            if (arg0Value instanceof JSProxyObject) {
                JSProxyObject arg0Value_ = (JSProxyObject)arg0Value;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                boolean bl = IsConstructorNode.doJSProxy(arg0Value_);
                return bl;
            }
            if (JSGuards.isJSDynamicObject(arg0Value) && !JSGuards.isJSFunction(arg0Value) && !JSGuards.isJSProxy(arg0Value)) {
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                boolean arg0Value_ = IsConstructorNode.doOther(arg0Value);
                return arg0Value_;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                boolean bl = IsConstructorNode.doString(arg0Value_);
                return bl;
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                boolean bl = IsConstructorNode.doBoolean(arg0Value_);
                return bl;
            }
            if (arg0Value instanceof Number) {
                Number arg0Value_ = (Number)arg0Value;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                boolean bl = IsConstructorNode.doNumber(arg0Value_);
                return bl;
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                boolean bl = IsConstructorNode.doSymbol(arg0Value_);
                return bl;
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                boolean bl = IsConstructorNode.doBigInt(arg0Value_);
                return bl;
            }
            if (exclude == 0) {
                int count8_ = 0;
                TruffleObject0Data s8_ = this.truffleObject0_cache;
                if ((state_0 & 0x100) != 0) {
                    while (!(s8_ == null || s8_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value))) {
                        s8_ = s8_.next_;
                        ++count8_;
                    }
                }
                if (s8_ == null && JSGuards.isForeignObject(arg0Value) && count8_ < 5) {
                    s8_ = super.insert(new TruffleObject0Data(this.truffleObject0_cache));
                    s8_.interop_ = s8_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                    VarHandle.storeStoreFence();
                    this.truffleObject0_cache = s8_;
                    this.state_0_ = state_0 |= 0x100;
                }
                if (s8_ != null) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl = IsConstructorNode.doTruffleObject(arg0Value, s8_.interop_);
                    return bl;
                }
            }
            InteropLibrary truffleObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (JSGuards.isForeignObject(arg0Value)) {
                    truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                    this.exclude_ = exclude |= 1;
                    this.truffleObject0_cache = null;
                    state_0 &= 0xFFFFFEFF;
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = IsConstructorNode.doTruffleObject(arg0Value, truffleObject1_interop__);
                    return bl;
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
        TruffleObject0Data s8_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s8_ = this.truffleObject0_cache) == null || s8_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[11];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doJSFunction";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doJSProxy";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doOther";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doString";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doNumber";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doTruffleObject";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            TruffleObject0Data s8_ = this.truffleObject0_cache;
            while (s8_ != null) {
                cached.add(Arrays.asList(s8_.interop_));
                s8_ = s8_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[9] = s;
        s = new Object[3];
        s[0] = "doTruffleObject";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(new Object[0]));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[10] = s;
        return Introspection.Provider.create(data);
    }

    public static IsConstructorNode create() {
        return new IsConstructorNodeGen();
    }

    public static IsConstructorNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=IsConstructorNode.class)
    @DenyReplace
    private static final class Uncached
    extends IsConstructorNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean executeBoolean(Object arg0Value) {
            if (arg0Value instanceof JSFunctionObject) {
                JSFunctionObject arg0Value_ = (JSFunctionObject)arg0Value;
                return IsConstructorNode.doJSFunction(arg0Value_);
            }
            if (arg0Value instanceof JSProxyObject) {
                JSProxyObject arg0Value_ = (JSProxyObject)arg0Value;
                return IsConstructorNode.doJSProxy(arg0Value_);
            }
            if (JSGuards.isJSDynamicObject(arg0Value) && !JSGuards.isJSFunction(arg0Value) && !JSGuards.isJSProxy(arg0Value)) {
                return IsConstructorNode.doOther(arg0Value);
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                return IsConstructorNode.doString(arg0Value_);
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                return IsConstructorNode.doBoolean(arg0Value_);
            }
            if (arg0Value instanceof Number) {
                Number arg0Value_ = (Number)arg0Value;
                return IsConstructorNode.doNumber(arg0Value_);
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                return IsConstructorNode.doSymbol(arg0Value_);
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                return IsConstructorNode.doBigInt(arg0Value_);
            }
            if (JSGuards.isForeignObject(arg0Value)) {
                return IsConstructorNode.doTruffleObject(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value));
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

    @GeneratedBy(value=IsConstructorNode.class)
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

