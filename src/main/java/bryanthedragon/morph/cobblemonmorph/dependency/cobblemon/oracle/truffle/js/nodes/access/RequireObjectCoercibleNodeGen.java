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
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.RequireObjectCoercibleNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=RequireObjectCoercibleNode.class)
public final class RequireObjectCoercibleNodeGen
extends RequireObjectCoercibleNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private Class<?> cachedJSClass_cachedClass_;
    @Node.Child
    private ForeignObject0Data foreignObject0_cache;

    private RequireObjectCoercibleNodeGen() {
    }

    @Override
    @ExplodeLoop
    public void executeVoid(Object arg0Value) {
        JSDynamicObject arg0Value_;
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_2 = (Integer)arg0Value;
            RequireObjectCoercibleNode.doInt(arg0Value_2);
            return;
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_3 = (SafeInteger)arg0Value;
            RequireObjectCoercibleNode.doSafeInteger(arg0Value_3);
            return;
        }
        if ((state_0 & 4) != 0 && arg0Value instanceof Long) {
            long arg0Value_4 = (Long)arg0Value;
            RequireObjectCoercibleNode.doLong(arg0Value_4);
            return;
        }
        if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x1E000) >>> 13, arg0Value)) {
            double arg0Value_5 = JSTypesGen.asImplicitDouble((state_0 & 0x1E000) >>> 13, arg0Value);
            RequireObjectCoercibleNode.doDouble(arg0Value_5);
            return;
        }
        if ((state_0 & 0x10) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_6 = (TruffleString)arg0Value;
            RequireObjectCoercibleNode.doTString(arg0Value_6);
            return;
        }
        if ((state_0 & 0x20) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_7 = (Boolean)arg0Value;
            RequireObjectCoercibleNode.doBoolean(arg0Value_7);
            return;
        }
        if ((state_0 & 0x40) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_8 = (Symbol)arg0Value;
            RequireObjectCoercibleNode.doSymbol(arg0Value_8);
            return;
        }
        if ((state_0 & 0x80) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_9 = (BigInt)arg0Value;
            RequireObjectCoercibleNode.doBigInt(arg0Value_9);
            return;
        }
        if ((state_0 & 0xF00) != 0) {
            if ((state_0 & 0x100) != 0) {
                assert (this.cachedJSClass_cachedClass_ != null);
                if (CompilerDirectives.isExact(arg0Value, this.cachedJSClass_cachedClass_)) {
                    RequireObjectCoercibleNode.doCachedJSClass(arg0Value, this.cachedJSClass_cachedClass_);
                    return;
                }
            }
            if ((state_0 & 0x200) != 0 && JSGuards.isJSObject(arg0Value)) {
                RequireObjectCoercibleNode.doJSObject(arg0Value);
                return;
            }
            if ((state_0 & 0x400) != 0) {
                ForeignObject0Data s10_ = this.foreignObject0_cache;
                while (s10_ != null) {
                    if (s10_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                        this.doForeignObject(arg0Value, s10_.interop_);
                        return;
                    }
                    s10_ = s10_.next_;
                }
            }
            if ((state_0 & 0x800) != 0 && JSGuards.isForeignObject(arg0Value)) {
                this.foreignObject1Boundary(state_0, arg0Value);
                return;
            }
        }
        if ((state_0 & 0x1000) != 0 && arg0Value instanceof JSDynamicObject && JSGuards.isNullOrUndefined(arg0Value_ = (JSDynamicObject)arg0Value)) {
            this.doNullOrUndefined(arg0Value_);
            return;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private void foreignObject1Boundary(int state_0, Object arg0Value) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
            this.doForeignObject(arg0Value, foreignObject1_interop__);
            return;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            JSDynamicObject arg0Value_;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof Integer) {
                int arg0Value_2 = (Integer)arg0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                RequireObjectCoercibleNode.doInt(arg0Value_2);
                return;
            }
            if (arg0Value instanceof SafeInteger) {
                SafeInteger arg0Value_3 = (SafeInteger)arg0Value;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                RequireObjectCoercibleNode.doSafeInteger(arg0Value_3);
                return;
            }
            if (arg0Value instanceof Long) {
                long arg0Value_4 = (Long)arg0Value;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                RequireObjectCoercibleNode.doLong(arg0Value_4);
                return;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast0 != 0) {
                double arg0Value_5 = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                state_0 |= doubleCast0 << 13;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                RequireObjectCoercibleNode.doDouble(arg0Value_5);
                return;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_6 = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                RequireObjectCoercibleNode.doTString(arg0Value_6);
                return;
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_7 = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                RequireObjectCoercibleNode.doBoolean(arg0Value_7);
                return;
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_8 = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                RequireObjectCoercibleNode.doSymbol(arg0Value_8);
                return;
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_9 = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                RequireObjectCoercibleNode.doBigInt(arg0Value_9);
                return;
            }
            if ((exclude & 1) == 0) {
                Class<?> cachedJSClass_cachedClass__;
                boolean CachedJSClass_duplicateFound_ = false;
                if ((state_0 & 0x100) != 0) {
                    assert (this.cachedJSClass_cachedClass_ != null);
                    if (CompilerDirectives.isExact(arg0Value, this.cachedJSClass_cachedClass_)) {
                        CachedJSClass_duplicateFound_ = true;
                    }
                }
                if (!CachedJSClass_duplicateFound_ && (cachedJSClass_cachedClass__ = JSGuards.getClassIfJSObject(arg0Value)) != null && CompilerDirectives.isExact(arg0Value, cachedJSClass_cachedClass__) && (state_0 & 0x100) == 0) {
                    this.cachedJSClass_cachedClass_ = cachedJSClass_cachedClass__;
                    this.state_0_ = state_0 |= 0x100;
                    CachedJSClass_duplicateFound_ = true;
                }
                if (CachedJSClass_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    RequireObjectCoercibleNode.doCachedJSClass(arg0Value, this.cachedJSClass_cachedClass_);
                    return;
                }
            }
            if (JSGuards.isJSObject(arg0Value)) {
                this.exclude_ = exclude |= 1;
                state_0 &= 0xFFFFFEFF;
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                RequireObjectCoercibleNode.doJSObject(arg0Value);
                return;
            }
            if ((exclude & 2) == 0) {
                int count10_ = 0;
                ForeignObject0Data s10_ = this.foreignObject0_cache;
                if ((state_0 & 0x400) != 0) {
                    while (!(s10_ == null || s10_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value))) {
                        s10_ = s10_.next_;
                        ++count10_;
                    }
                }
                if (s10_ == null && JSGuards.isForeignObject(arg0Value) && count10_ < 5) {
                    s10_ = super.insert(new ForeignObject0Data(this.foreignObject0_cache));
                    s10_.interop_ = s10_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                    VarHandle.storeStoreFence();
                    this.foreignObject0_cache = s10_;
                    this.state_0_ = state_0 |= 0x400;
                }
                if (s10_ != null) {
                    lock.unlock();
                    hasLock = false;
                    this.doForeignObject(arg0Value, s10_.interop_);
                    return;
                }
            }
            InteropLibrary foreignObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (JSGuards.isForeignObject(arg0Value)) {
                    foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                    this.exclude_ = exclude |= 2;
                    this.foreignObject0_cache = null;
                    state_0 &= 0xFFFFFBFF;
                    this.state_0_ = state_0 |= 0x800;
                    lock.unlock();
                    hasLock = false;
                    this.doForeignObject(arg0Value, foreignObject1_interop__);
                    return;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            if (arg0Value instanceof JSDynamicObject && JSGuards.isNullOrUndefined(arg0Value_ = (JSDynamicObject)arg0Value)) {
                this.state_0_ = state_0 |= 0x1000;
                lock.unlock();
                hasLock = false;
                this.doNullOrUndefined(arg0Value_);
                return;
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
        ForeignObject0Data s10_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x1FFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x1FFF & (state_0 & 0x1FFF) - 1) == 0 && ((s10_ = this.foreignObject0_cache) == null || s10_.next_ == null)) {
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
        s[0] = "doInt";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doLong";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doTString";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doBoolean";
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
        s[0] = "doCachedJSClass";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.cachedJSClass_cachedClass_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[9] = s;
        s = new Object[3];
        s[0] = "doJSObject";
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
        s = new Object[3];
        s[0] = "doNullOrUndefined";
        s[1] = (state_0 & 0x1000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[13] = s;
        return Introspection.Provider.create(data);
    }

    public static RequireObjectCoercibleNode create() {
        return new RequireObjectCoercibleNodeGen();
    }

    @GeneratedBy(value=RequireObjectCoercibleNode.RequireObjectCoercibleWrapperNode.class)
    public static final class RequireObjectCoercibleWrapperNodeGen
    extends RequireObjectCoercibleNode.RequireObjectCoercibleWrapperNode
    implements Introspection.Provider {
        private RequireObjectCoercibleWrapperNodeGen(JavaScriptNode operand) {
            super(operand);
        }

        @Override
        public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
            return this.doDefault(operandNodeValue);
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object operandNodeValue_ = this.operandNode.execute(frameValue);
            return this.doDefault(operandNodeValue_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "doDefault";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RequireObjectCoercibleNode.RequireObjectCoercibleWrapperNode create(JavaScriptNode operand) {
            return new RequireObjectCoercibleWrapperNodeGen(operand);
        }
    }

    @GeneratedBy(value=RequireObjectCoercibleNode.class)
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

