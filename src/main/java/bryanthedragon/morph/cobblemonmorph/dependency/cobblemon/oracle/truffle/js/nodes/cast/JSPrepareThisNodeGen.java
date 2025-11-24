/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.cast;

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
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSPrepareThisNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSPrepareThisNode.class)
public final class JSPrepareThisNodeGen
extends JSPrepareThisNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private Class<?> jSObjectCached_cachedClass_;
    @Node.Child
    private ForeignObject0Data foreignObject0_cache;

    private JSPrepareThisNodeGen(JSContext context, JavaScriptNode child) {
        super(context, child);
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(operandNodeValue)) {
                return this.doJSObject(operandNodeValue);
            }
            if ((state_0 & 2) != 0) {
                assert (this.jSObjectCached_cachedClass_ != null);
                if (CompilerDirectives.isExact(operandNodeValue, this.jSObjectCached_cachedClass_)) {
                    return this.doJSObjectCached(operandNodeValue, this.jSObjectCached_cachedClass_);
                }
            }
        }
        if ((state_0 & 4) != 0 && operandNodeValue instanceof JSObject) {
            JSObject operandNodeValue_ = (JSObject)operandNodeValue;
            return this.doJSObject(operandNodeValue_);
        }
        if ((state_0 & 8) != 0 && operandNodeValue instanceof Boolean) {
            boolean operandNodeValue_ = (Boolean)operandNodeValue;
            return this.doBoolean(operandNodeValue_);
        }
        if ((state_0 & 0x10) != 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
            return this.doString(operandNodeValue_);
        }
        if ((state_0 & 0x20) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            return this.doInt(operandNodeValue_);
        }
        if ((state_0 & 0x40) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF000) >>> 12, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0xF000) >>> 12, operandNodeValue);
            return this.doDouble(operandNodeValue_);
        }
        if ((state_0 & 0x80) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            return this.doBigInt(operandNodeValue_);
        }
        if ((state_0 & 0x100) != 0 && JSGuards.isJavaNumber(operandNodeValue)) {
            return this.doNumber(operandNodeValue);
        }
        if ((state_0 & 0x200) != 0 && operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_ = (Symbol)operandNodeValue;
            return this.doSymbol(operandNodeValue_);
        }
        if ((state_0 & 0xC00) != 0) {
            if ((state_0 & 0x400) != 0) {
                ForeignObject0Data s10_ = this.foreignObject0_cache;
                while (s10_ != null) {
                    if (s10_.interop_.accepts(operandNodeValue) && JSGuards.isForeignObject(operandNodeValue)) {
                        return this.doForeignObject(operandNodeValue, s10_.interop_);
                    }
                    s10_ = s10_.next_;
                }
            }
            if ((state_0 & 0x800) != 0 && JSGuards.isForeignObject(operandNodeValue)) {
                return this.foreignObject1Boundary(state_0, operandNodeValue);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private Object foreignObject1Boundary(int state_0, Object operandNodeValue) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
            Object object = this.doForeignObject(operandNodeValue, foreignObject1_interop__);
            return object;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0xFF7) == 0 && (state_0 & 0xFFF) != 0) {
            return this.execute_boolean0(state_0, frameValue);
        }
        if ((state_0 & 0xFDF) == 0 && (state_0 & 0xFFF) != 0) {
            return this.execute_int1(state_0, frameValue);
        }
        if ((state_0 & 0xFBF) == 0 && (state_0 & 0xFFF) != 0) {
            return this.execute_double2(state_0, frameValue);
        }
        return this.execute_generic3(state_0, frameValue);
    }

    private Object execute_boolean0(int state_0, VirtualFrame frameValue) {
        boolean operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 8) != 0);
        return this.doBoolean(operandNodeValue_);
    }

    private Object execute_int1(int state_0, VirtualFrame frameValue) {
        int operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 0x20) != 0);
        return this.doInt(operandNodeValue_);
    }

    private Object execute_double2(int state_0, VirtualFrame frameValue) {
        double operandNodeValue_;
        long operandNodeValue_long = 0L;
        int operandNodeValue_int = 0;
        try {
            if ((state_0 & 0xE000) == 0 && (state_0 & 0xFFF) != 0) {
                operandNodeValue_ = this.operandNode.executeDouble(frameValue);
            } else if ((state_0 & 0xD000) == 0 && (state_0 & 0xFFF) != 0) {
                operandNodeValue_int = this.operandNode.executeInt(frameValue);
                operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
            } else if ((state_0 & 0x7000) == 0 && (state_0 & 0xFFF) != 0) {
                operandNodeValue_long = this.operandNode.executeLong(frameValue);
                operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
            } else {
                Object operandNodeValue__ = this.operandNode.execute(frameValue);
                operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0xF000) >>> 12, operandNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 0x40) != 0);
        return this.doDouble(operandNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private Object foreignObject1Boundary0(int state_0, Object operandNodeValue_) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue_);
            Object object = this.doForeignObject(operandNodeValue_, foreignObject1_interop__);
            return object;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @ExplodeLoop
    private Object execute_generic3(int state_0, VirtualFrame frameValue) {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 3) != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(operandNodeValue_)) {
                return this.doJSObject(operandNodeValue_);
            }
            if ((state_0 & 2) != 0) {
                assert (this.jSObjectCached_cachedClass_ != null);
                if (CompilerDirectives.isExact(operandNodeValue_, this.jSObjectCached_cachedClass_)) {
                    return this.doJSObjectCached(operandNodeValue_, this.jSObjectCached_cachedClass_);
                }
            }
        }
        if ((state_0 & 4) != 0 && operandNodeValue_ instanceof JSObject) {
            JSObject operandNodeValue__ = (JSObject)operandNodeValue_;
            return this.doJSObject(operandNodeValue__);
        }
        if ((state_0 & 8) != 0 && operandNodeValue_ instanceof Boolean) {
            boolean operandNodeValue__ = (Boolean)operandNodeValue_;
            return this.doBoolean(operandNodeValue__);
        }
        if ((state_0 & 0x10) != 0 && operandNodeValue_ instanceof TruffleString) {
            TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
            return this.doString(operandNodeValue__);
        }
        if ((state_0 & 0x20) != 0 && operandNodeValue_ instanceof Integer) {
            int operandNodeValue__ = (Integer)operandNodeValue_;
            return this.doInt(operandNodeValue__);
        }
        if ((state_0 & 0x40) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF000) >>> 12, operandNodeValue_)) {
            double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0xF000) >>> 12, operandNodeValue_);
            return this.doDouble(operandNodeValue__);
        }
        if ((state_0 & 0x80) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
            return this.doBigInt(operandNodeValue__);
        }
        if ((state_0 & 0x100) != 0 && JSGuards.isJavaNumber(operandNodeValue_)) {
            return this.doNumber(operandNodeValue_);
        }
        if ((state_0 & 0x200) != 0 && operandNodeValue_ instanceof Symbol) {
            Symbol operandNodeValue__ = (Symbol)operandNodeValue_;
            return this.doSymbol(operandNodeValue__);
        }
        if ((state_0 & 0xC00) != 0) {
            if ((state_0 & 0x400) != 0) {
                ForeignObject0Data s10_ = this.foreignObject0_cache;
                while (s10_ != null) {
                    if (s10_.interop_.accepts(operandNodeValue_) && JSGuards.isForeignObject(operandNodeValue_)) {
                        return this.doForeignObject(operandNodeValue_, s10_.interop_);
                    }
                    s10_ = s10_.next_;
                }
            }
            if ((state_0 & 0x800) != 0 && JSGuards.isForeignObject(operandNodeValue_)) {
                return this.foreignObject1Boundary0(state_0, operandNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Object operandNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            JSObject operandNodeValue_5;
            int operandNodeValue_2;
            Object jSObjectCached_cachedClass__;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (JSGuards.isNullOrUndefined(operandNodeValue)) {
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.doJSObject(operandNodeValue);
                return jSDynamicObject;
            }
            if ((exclude & 1) == 0) {
                boolean JSObjectCached_duplicateFound_ = false;
                if ((state_0 & 2) != 0) {
                    assert (this.jSObjectCached_cachedClass_ != null);
                    if (CompilerDirectives.isExact(operandNodeValue, this.jSObjectCached_cachedClass_)) {
                        JSObjectCached_duplicateFound_ = true;
                    }
                }
                if (!JSObjectCached_duplicateFound_ && (jSObjectCached_cachedClass__ = JSGuards.getClassIfJSObject(operandNodeValue)) != null && CompilerDirectives.isExact(operandNodeValue, jSObjectCached_cachedClass__) && (state_0 & 2) == 0) {
                    this.jSObjectCached_cachedClass_ = jSObjectCached_cachedClass__;
                    this.state_0_ = state_0 |= 2;
                    JSObjectCached_duplicateFound_ = true;
                }
                if (JSObjectCached_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    jSObjectCached_cachedClass__ = this.doJSObjectCached(operandNodeValue, this.jSObjectCached_cachedClass_);
                    return jSObjectCached_cachedClass__;
                }
            }
            if (operandNodeValue instanceof JSObject) {
                JSObject operandNodeValue_3 = (JSObject)operandNodeValue;
                this.exclude_ = exclude |= 1;
                state_0 &= 0xFFFFFFFD;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                jSObjectCached_cachedClass__ = this.doJSObject(operandNodeValue_3);
                return jSObjectCached_cachedClass__;
            }
            if (operandNodeValue instanceof Boolean) {
                operandNodeValue_2 = ((Boolean)operandNodeValue).booleanValue();
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                jSObjectCached_cachedClass__ = this.doBoolean(operandNodeValue_2 != 0);
                return jSObjectCached_cachedClass__;
            }
            if (operandNodeValue instanceof TruffleString) {
                TruffleString operandNodeValue_4 = (TruffleString)operandNodeValue;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                jSObjectCached_cachedClass__ = this.doString(operandNodeValue_4);
                return jSObjectCached_cachedClass__;
            }
            if (operandNodeValue instanceof Integer) {
                operandNodeValue_2 = (Integer)operandNodeValue;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                jSObjectCached_cachedClass__ = this.doInt(operandNodeValue_2);
                return jSObjectCached_cachedClass__;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue);
            if (doubleCast0 != 0) {
                double operandNodeValue_5 = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
                state_0 |= doubleCast0 << 12;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                JSObject jSObject = this.doDouble(operandNodeValue_5);
                return jSObject;
            }
            if (operandNodeValue instanceof BigInt) {
                BigInt operandNodeValue_6 = (BigInt)operandNodeValue;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                operandNodeValue_5 = this.doBigInt(operandNodeValue_6);
                return operandNodeValue_5;
            }
            if (JSGuards.isJavaNumber(operandNodeValue)) {
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                JSObject operandNodeValue_6 = this.doNumber(operandNodeValue);
                return operandNodeValue_6;
            }
            if (operandNodeValue instanceof Symbol) {
                Symbol operandNodeValue_7 = (Symbol)operandNodeValue;
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                operandNodeValue_5 = this.doSymbol(operandNodeValue_7);
                return operandNodeValue_5;
            }
            if ((exclude & 2) == 0) {
                int count10_ = 0;
                ForeignObject0Data s10_ = this.foreignObject0_cache;
                if ((state_0 & 0x400) != 0) {
                    while (!(s10_ == null || s10_.interop_.accepts(operandNodeValue) && JSGuards.isForeignObject(operandNodeValue))) {
                        s10_ = s10_.next_;
                        ++count10_;
                    }
                }
                if (s10_ == null && JSGuards.isForeignObject(operandNodeValue) && count10_ < 5) {
                    s10_ = super.insert(new ForeignObject0Data(this.foreignObject0_cache));
                    s10_.interop_ = s10_.insertAccessor(INTEROP_LIBRARY_.create(operandNodeValue));
                    VarHandle.storeStoreFence();
                    this.foreignObject0_cache = s10_;
                    this.state_0_ = state_0 |= 0x400;
                }
                if (s10_ != null) {
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doForeignObject(operandNodeValue, s10_.interop_);
                    return object;
                }
            }
            InteropLibrary foreignObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (JSGuards.isForeignObject(operandNodeValue)) {
                    foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
                    this.exclude_ = exclude |= 2;
                    this.foreignObject0_cache = null;
                    state_0 &= 0xFFFFFBFF;
                    this.state_0_ = state_0 |= 0x800;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doForeignObject(operandNodeValue, foreignObject1_interop__);
                    return object;
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
        ForeignObject0Data s10_;
        int state_0 = this.state_0_;
        if ((state_0 & 0xFFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0xFFF & (state_0 & 0xFFF) - 1) == 0 && ((s10_ = this.foreignObject0_cache) == null || s10_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[13];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doJSObject";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doJSObjectCached";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.jSObjectCached_cachedClass_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doJSObject";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doString";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doNumber";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[10] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x400) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ForeignObject0Data s10_ = this.foreignObject0_cache;
            while (s10_ != null) {
                cached.add(Arrays.asList(s10_.interop_));
                s10_ = s10_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[11] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x800) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(new Object[0]));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[12] = s;
        return Introspection.Provider.create(data);
    }

    public static JSPrepareThisNode create(JSContext context, JavaScriptNode child) {
        return new JSPrepareThisNodeGen(context, child);
    }

    @GeneratedBy(value=JSPrepareThisNode.class)
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

