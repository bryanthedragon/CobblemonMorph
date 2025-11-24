/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.builtins.helper;

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
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.JSCollectionsNormalizeNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSCollectionsNormalizeNode.class)
public final class JSCollectionsNormalizeNodeGen
extends JSCollectionsNormalizeNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private ForeignObject0Data foreignObject0_cache;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile foreignObject1_primitiveProfile_;
    @Node.Child
    private JSCollectionsNormalizeNode foreignObject1_nestedNormalizeNode_;

    private JSCollectionsNormalizeNodeGen() {
    }

    @Override
    @ExplodeLoop
    public Object execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return this.doInt(arg0Value_);
        }
        if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x1E00) >>> 9, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0x1E00) >>> 9, arg0Value);
            return this.doDouble(arg0Value_);
        }
        if ((state_0 & 4) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.doString(arg0Value_);
        }
        if ((state_0 & 8) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return this.doBoolean(arg0Value_);
        }
        if ((state_0 & 0x10) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            return this.doDynamicObject(arg0Value_);
        }
        if ((state_0 & 0x20) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
        }
        if ((state_0 & 0x40) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_);
        }
        if ((state_0 & 0x180) != 0) {
            if ((state_0 & 0x80) != 0) {
                ForeignObject0Data s7_ = this.foreignObject0_cache;
                while (s7_ != null) {
                    if (s7_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                        return this.doForeignObject(arg0Value, s7_.interop_, s7_.primitiveProfile_, s7_.nestedNormalizeNode_);
                    }
                    s7_ = s7_.next_;
                }
            }
            if ((state_0 & 0x100) != 0 && JSGuards.isForeignObject(arg0Value)) {
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
    private Object foreignObject1Boundary(int state_0, Object arg0Value) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
            Object object = this.doForeignObject(arg0Value, foreignObject1_interop__, this.foreignObject1_primitiveProfile_, this.foreignObject1_nestedNormalizeNode_);
            return object;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Object arg0Value) {
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
                Integer n = this.doInt(arg0Value_);
                return n;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast0 != 0) {
                double arg0Value_2 = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                state_0 |= doubleCast0 << 9;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = this.doDouble(arg0Value_2);
                return object;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                TruffleString arg0Value_2 = this.doString(arg0Value_);
                return arg0Value_2;
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                Boolean arg0Value_2 = this.doBoolean(arg0Value_);
                return arg0Value_2;
            }
            if (arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Object arg0Value_2 = this.doDynamicObject(arg0Value_);
                return arg0Value_2;
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                Symbol arg0Value_2 = this.doSymbol(arg0Value_);
                return arg0Value_2;
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                BigInt arg0Value_2 = this.doBigInt(arg0Value_);
                return arg0Value_2;
            }
            if (exclude == 0) {
                int count7_ = 0;
                ForeignObject0Data s7_ = this.foreignObject0_cache;
                if ((state_0 & 0x80) != 0) {
                    while (!(s7_ == null || s7_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value))) {
                        s7_ = s7_.next_;
                        ++count7_;
                    }
                }
                if (s7_ == null && JSGuards.isForeignObject(arg0Value) && count7_ < 5) {
                    s7_ = super.insert(new ForeignObject0Data(this.foreignObject0_cache));
                    s7_.interop_ = s7_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                    s7_.primitiveProfile_ = ConditionProfile.createBinaryProfile();
                    s7_.nestedNormalizeNode_ = s7_.insertAccessor(JSCollectionsNormalizeNode.create());
                    VarHandle.storeStoreFence();
                    this.foreignObject0_cache = s7_;
                    this.state_0_ = state_0 |= 0x80;
                }
                if (s7_ != null) {
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doForeignObject(arg0Value, s7_.interop_, s7_.primitiveProfile_, s7_.nestedNormalizeNode_);
                    return object;
                }
            }
            InteropLibrary foreignObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (JSGuards.isForeignObject(arg0Value)) {
                    foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                    this.foreignObject1_primitiveProfile_ = ConditionProfile.createBinaryProfile();
                    this.foreignObject1_nestedNormalizeNode_ = super.insert(JSCollectionsNormalizeNode.create());
                    this.exclude_ = exclude |= 1;
                    this.foreignObject0_cache = null;
                    state_0 &= 0xFFFFFF7F;
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doForeignObject(arg0Value, foreignObject1_interop__, this.foreignObject1_primitiveProfile_, this.foreignObject1_nestedNormalizeNode_);
                    return object;
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
        ForeignObject0Data s7_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x1FF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x1FF & (state_0 & 0x1FF) - 1) == 0 && ((s7_ = this.foreignObject0_cache) == null || s7_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[10];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doString";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doDynamicObject";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x80) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            ForeignObject0Data s7_ = this.foreignObject0_cache;
            while (s7_ != null) {
                cached.add(Arrays.asList(s7_.interop_, s7_.primitiveProfile_, s7_.nestedNormalizeNode_));
                s7_ = s7_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[8] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.foreignObject1_primitiveProfile_, this.foreignObject1_nestedNormalizeNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[9] = s;
        return Introspection.Provider.create(data);
    }

    public static JSCollectionsNormalizeNode create() {
        return new JSCollectionsNormalizeNodeGen();
    }

    public static JSCollectionsNormalizeNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=JSCollectionsNormalizeNode.class)
    @DenyReplace
    private static final class Uncached
    extends JSCollectionsNormalizeNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object execute(Object arg0Value) {
            if (arg0Value instanceof Integer) {
                int arg0Value_ = (Integer)arg0Value;
                return this.doInt(arg0Value_);
            }
            if (JSTypesGen.isImplicitDouble(arg0Value)) {
                double arg0Value_ = JSTypesGen.asImplicitDouble(arg0Value);
                return this.doDouble(arg0Value_);
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                return this.doString(arg0Value_);
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                return this.doBoolean(arg0Value_);
            }
            if (arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                return this.doDynamicObject(arg0Value_);
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                return this.doSymbol(arg0Value_);
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                return this.doBigInt(arg0Value_);
            }
            if (JSGuards.isForeignObject(arg0Value)) {
                return this.doForeignObject(arg0Value, INTEROP_LIBRARY_.getUncached(arg0Value), ConditionProfile.getUncached(), JSCollectionsNormalizeNodeGen.getUncached());
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

    @GeneratedBy(value=JSCollectionsNormalizeNode.class)
    private static final class ForeignObject0Data
    extends Node {
        @Node.Child
        ForeignObject0Data next_;
        @Node.Child
        InteropLibrary interop_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile primitiveProfile_;
        @Node.Child
        JSCollectionsNormalizeNode nestedNormalizeNode_;

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

