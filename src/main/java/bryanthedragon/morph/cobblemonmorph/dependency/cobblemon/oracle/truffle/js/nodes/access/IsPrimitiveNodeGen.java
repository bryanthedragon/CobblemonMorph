/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.access;

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
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.access.IsPrimitiveNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=IsPrimitiveNode.class)
public final class IsPrimitiveNodeGen
extends IsPrimitiveNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private ForeignObject0Data foreignObject0_cache;

    private IsPrimitiveNodeGen() {
    }

    @Override
    @ExplodeLoop
    public boolean executeBoolean(Object arg0Value) {
        JSDynamicObject arg0Value_;
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isJSNull(arg0Value)) {
                return IsPrimitiveNode.doNull(arg0Value);
            }
            if ((state_0 & 2) != 0 && JSGuards.isUndefined(arg0Value)) {
                return IsPrimitiveNode.doUndefined(arg0Value);
            }
        }
        if ((state_0 & 4) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_2 = (Boolean)arg0Value;
            return IsPrimitiveNode.doBoolean(arg0Value_2);
        }
        if ((state_0 & 8) != 0 && arg0Value instanceof Integer) {
            int arg0Value_3 = (Integer)arg0Value;
            return IsPrimitiveNode.doInt(arg0Value_3);
        }
        if ((state_0 & 0x10) != 0 && arg0Value instanceof Long) {
            long arg0Value_4 = (Long)arg0Value;
            return IsPrimitiveNode.doLong(arg0Value_4);
        }
        if ((state_0 & 0x20) != 0 && arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_5 = (SafeInteger)arg0Value;
            return IsPrimitiveNode.doLargeInt(arg0Value_5);
        }
        if ((state_0 & 0x40) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x1E000) >>> 13, arg0Value)) {
            double arg0Value_6 = JSTypesGen.asImplicitDouble((state_0 & 0x1E000) >>> 13, arg0Value);
            return IsPrimitiveNode.doDouble(arg0Value_6);
        }
        if ((state_0 & 0x80) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_7 = (Symbol)arg0Value;
            return IsPrimitiveNode.doSymbol(arg0Value_7);
        }
        if ((state_0 & 0x100) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_8 = (BigInt)arg0Value;
            return IsPrimitiveNode.doBigInt(arg0Value_8);
        }
        if ((state_0 & 0x200) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_9 = (TruffleString)arg0Value;
            return IsPrimitiveNode.doString(arg0Value_9);
        }
        if ((state_0 & 0x400) != 0 && arg0Value instanceof JSDynamicObject && JSGuards.isJSObject(arg0Value_ = (JSDynamicObject)arg0Value)) {
            return IsPrimitiveNode.doIsObject(arg0Value_);
        }
        if ((state_0 & 0x1800) != 0) {
            if ((state_0 & 0x800) != 0) {
                ForeignObject0Data s11_ = this.foreignObject0_cache;
                while (s11_ != null) {
                    if (s11_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                        return IsPrimitiveNode.doForeignObject(arg0Value, s11_.interop_);
                    }
                    s11_ = s11_.next_;
                }
            }
            if ((state_0 & 0x1000) != 0 && JSGuards.isForeignObject(arg0Value)) {
                return this.foreignObject1Boundary(state_0, arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private boolean foreignObject1Boundary(int state_0, Object arg0Value) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
            boolean bl = IsPrimitiveNode.doForeignObject(arg0Value, foreignObject1_interop__);
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
            JSDynamicObject arg0Value_;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (JSGuards.isJSNull(arg0Value)) {
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = IsPrimitiveNode.doNull(arg0Value);
                return bl;
            }
            if (JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                boolean bl = IsPrimitiveNode.doUndefined(arg0Value);
                return bl;
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_2 = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                boolean bl = IsPrimitiveNode.doBoolean(arg0Value_2);
                return bl;
            }
            if (arg0Value instanceof Integer) {
                int arg0Value_3 = (Integer)arg0Value;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                boolean bl = IsPrimitiveNode.doInt(arg0Value_3);
                return bl;
            }
            if (arg0Value instanceof Long) {
                long arg0Value_4 = (Long)arg0Value;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                boolean bl = IsPrimitiveNode.doLong(arg0Value_4);
                return bl;
            }
            if (arg0Value instanceof SafeInteger) {
                SafeInteger arg0Value_5 = (SafeInteger)arg0Value;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                boolean bl = IsPrimitiveNode.doLargeInt(arg0Value_5);
                return bl;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast0 != 0) {
                double arg0Value_6 = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                state_0 |= doubleCast0 << 13;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                boolean bl = IsPrimitiveNode.doDouble(arg0Value_6);
                return bl;
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_7 = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                boolean arg0Value_6 = IsPrimitiveNode.doSymbol(arg0Value_7);
                return arg0Value_6;
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_8 = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                boolean arg0Value_6 = IsPrimitiveNode.doBigInt(arg0Value_8);
                return arg0Value_6;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_9 = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                boolean arg0Value_6 = IsPrimitiveNode.doString(arg0Value_9);
                return arg0Value_6;
            }
            if (arg0Value instanceof JSDynamicObject && JSGuards.isJSObject(arg0Value_ = (JSDynamicObject)arg0Value)) {
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                boolean arg0Value_6 = IsPrimitiveNode.doIsObject(arg0Value_);
                return arg0Value_6;
            }
            if (exclude == 0) {
                int count11_ = 0;
                ForeignObject0Data s11_ = this.foreignObject0_cache;
                if ((state_0 & 0x800) != 0) {
                    while (!(s11_ == null || s11_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value))) {
                        s11_ = s11_.next_;
                        ++count11_;
                    }
                }
                if (s11_ == null && JSGuards.isForeignObject(arg0Value) && count11_ < 5) {
                    s11_ = super.insert(new ForeignObject0Data(this.foreignObject0_cache));
                    s11_.interop_ = s11_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                    VarHandle.storeStoreFence();
                    this.foreignObject0_cache = s11_;
                    this.state_0_ = state_0 |= 0x800;
                }
                if (s11_ != null) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl = IsPrimitiveNode.doForeignObject(arg0Value, s11_.interop_);
                    return bl;
                }
            }
            InteropLibrary foreignObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (JSGuards.isForeignObject(arg0Value)) {
                    foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                    this.exclude_ = exclude |= 1;
                    this.foreignObject0_cache = null;
                    state_0 &= 0xFFFFF7FF;
                    this.state_0_ = state_0 |= 0x1000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = IsPrimitiveNode.doForeignObject(arg0Value, foreignObject1_interop__);
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
        ForeignObject0Data s11_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x1FFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x1FFF & (state_0 & 0x1FFF) - 1) == 0 && ((s11_ = this.foreignObject0_cache) == null || s11_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[14];
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
        s[0] = "doBoolean";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doLong";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doLargeInt";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doString";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[10] = s;
        s = new Object[3];
        s[0] = "doIsObject";
        s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[11] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x800) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            ForeignObject0Data s11_ = this.foreignObject0_cache;
            while (s11_ != null) {
                cached.add(Arrays.asList(s11_.interop_));
                s11_ = s11_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[12] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x1000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(new Object[0]));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[13] = s;
        return Introspection.Provider.create(data);
    }

    public static IsPrimitiveNode create() {
        return new IsPrimitiveNodeGen();
    }

    public static IsPrimitiveNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=IsPrimitiveNode.class)
    @DenyReplace
    private static final class Uncached
    extends IsPrimitiveNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean executeBoolean(Object arg0Value) {
            JSDynamicObject arg0Value_;
            if (JSGuards.isJSNull(arg0Value)) {
                return IsPrimitiveNode.doNull(arg0Value);
            }
            if (JSGuards.isUndefined(arg0Value)) {
                return IsPrimitiveNode.doUndefined(arg0Value);
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_2 = (Boolean)arg0Value;
                return IsPrimitiveNode.doBoolean(arg0Value_2);
            }
            if (arg0Value instanceof Integer) {
                int arg0Value_3 = (Integer)arg0Value;
                return IsPrimitiveNode.doInt(arg0Value_3);
            }
            if (arg0Value instanceof Long) {
                long arg0Value_4 = (Long)arg0Value;
                return IsPrimitiveNode.doLong(arg0Value_4);
            }
            if (arg0Value instanceof SafeInteger) {
                SafeInteger arg0Value_5 = (SafeInteger)arg0Value;
                return IsPrimitiveNode.doLargeInt(arg0Value_5);
            }
            if (JSTypesGen.isImplicitDouble(arg0Value)) {
                double arg0Value_6 = JSTypesGen.asImplicitDouble(arg0Value);
                return IsPrimitiveNode.doDouble(arg0Value_6);
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_7 = (Symbol)arg0Value;
                return IsPrimitiveNode.doSymbol(arg0Value_7);
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_8 = (BigInt)arg0Value;
                return IsPrimitiveNode.doBigInt(arg0Value_8);
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_9 = (TruffleString)arg0Value;
                return IsPrimitiveNode.doString(arg0Value_9);
            }
            if (arg0Value instanceof JSDynamicObject && JSGuards.isJSObject(arg0Value_ = (JSDynamicObject)arg0Value)) {
                return IsPrimitiveNode.doIsObject(arg0Value_);
            }
            if (JSGuards.isForeignObject(arg0Value)) {
                return IsPrimitiveNode.doForeignObject(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value));
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

    @GeneratedBy(value=IsPrimitiveNode.class)
    private static final class ForeignObject0Data
    extends Node {
        @Node.Child
        ForeignObject0Data next_;
        @Node.Child
        InteropLibrary interop_;

        ForeignObject0Data(ForeignObject0Data next_) {
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

