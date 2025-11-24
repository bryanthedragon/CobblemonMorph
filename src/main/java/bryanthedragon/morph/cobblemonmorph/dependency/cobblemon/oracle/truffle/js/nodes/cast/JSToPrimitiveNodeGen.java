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
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.access.IsPrimitiveNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToPrimitiveNode.class)
public final class JSToPrimitiveNodeGen
extends JSToPrimitiveNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSObjectData jSObject_cache;
    @Node.Child
    private ForeignObject0Data foreignObject0_cache;
    @Node.Child
    private InteropLibrary foreignObject1_resultInterop_;

    private JSToPrimitiveNodeGen(JSToPrimitiveNode.Hint hint) {
        super(hint);
    }

    @Override
    @ExplodeLoop
    public Object execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return this.doInt(arg0Value_);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            return this.doSafeInteger(arg0Value_);
        }
        if ((state_0 & 4) != 0 && arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            return this.doLong(arg0Value_);
        }
        if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C000) >>> 14, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C000) >>> 14, arg0Value);
            return this.doDouble(arg0Value_);
        }
        if ((state_0 & 0x10) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return this.doBoolean(arg0Value_);
        }
        if ((state_0 & 0x20) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.doString(arg0Value_);
        }
        if ((state_0 & 0x40) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
        }
        if ((state_0 & 0x80) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_);
        }
        if ((state_0 & 0x300) != 0) {
            if ((state_0 & 0x100) != 0 && JSGuards.isJSNull(arg0Value)) {
                return this.doNull(arg0Value);
            }
            if ((state_0 & 0x200) != 0 && JSGuards.isUndefined(arg0Value)) {
                return this.doUndefined(arg0Value);
            }
        }
        if ((state_0 & 0x400) != 0 && arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            JSObjectData s10_ = this.jSObject_cache;
            if (s10_ != null) {
                return this.doJSObject(arg0Value_, s10_.getToPrimitive_, s10_.isPrimitive_, s10_.exoticToPrimProfile_, s10_.callExoticToPrim_);
            }
        }
        if ((state_0 & 0x3800) != 0) {
            if ((state_0 & 0x800) != 0) {
                ForeignObject0Data s11_ = this.foreignObject0_cache;
                while (s11_ != null) {
                    if (s11_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                        return this.doForeignObject(arg0Value, s11_.interop_, s11_.resultInterop_);
                    }
                    s11_ = s11_.next_;
                }
            }
            if ((state_0 & 0x1000) != 0 && JSGuards.isForeignObject(arg0Value)) {
                return this.foreignObject1Boundary(state_0, arg0Value);
            }
            if ((state_0 & 0x2000) != 0 && JSToPrimitiveNodeGen.fallbackGuard_(state_0, arg0Value)) {
                return this.doFallback(arg0Value);
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
            Object object = this.doForeignObject(arg0Value, foreignObject1_interop__, this.foreignObject1_resultInterop_);
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
            if (arg0Value instanceof SafeInteger) {
                SafeInteger arg0Value_ = (SafeInteger)arg0Value;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                SafeInteger safeInteger = this.doSafeInteger(arg0Value_);
                return safeInteger;
            }
            if (arg0Value instanceof Long) {
                long arg0Value_ = (Long)arg0Value;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Long l = this.doLong(arg0Value_);
                return l;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast0 != 0) {
                double arg0Value_2 = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                state_0 |= doubleCast0 << 14;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                Double d = this.doDouble(arg0Value_2);
                return d;
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Boolean arg0Value_2 = this.doBoolean(arg0Value_);
                return arg0Value_2;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                Object arg0Value_2 = this.doString(arg0Value_);
                return arg0Value_2;
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                Symbol arg0Value_2 = this.doSymbol(arg0Value_);
                return arg0Value_2;
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                BigInt arg0Value_2 = this.doBigInt(arg0Value_);
                return arg0Value_2;
            }
            if (JSGuards.isJSNull(arg0Value)) {
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                JSDynamicObject arg0Value_ = this.doNull(arg0Value);
                return arg0Value_;
            }
            if (JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                JSDynamicObject arg0Value_ = this.doUndefined(arg0Value);
                return arg0Value_;
            }
            if (arg0Value instanceof JSObject) {
                JSObject arg0Value_ = (JSObject)arg0Value;
                JSObjectData s10_ = super.insert(new JSObjectData());
                s10_.getToPrimitive_ = s10_.insertAccessor(this.createGetToPrimitive());
                s10_.isPrimitive_ = s10_.insertAccessor(IsPrimitiveNode.create());
                s10_.exoticToPrimProfile_ = ConditionProfile.create();
                s10_.callExoticToPrim_ = s10_.insertAccessor(JSFunctionCallNode.createCall());
                VarHandle.storeStoreFence();
                this.jSObject_cache = s10_;
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                Object object = this.doJSObject(arg0Value_, s10_.getToPrimitive_, s10_.isPrimitive_, s10_.exoticToPrimProfile_, s10_.callExoticToPrim_);
                return object;
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
                    s11_.resultInterop_ = s11_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                    VarHandle.storeStoreFence();
                    this.foreignObject0_cache = s11_;
                    this.state_0_ = state_0 |= 0x800;
                }
                if (s11_ != null) {
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doForeignObject(arg0Value, s11_.interop_, s11_.resultInterop_);
                    return object;
                }
            }
            InteropLibrary foreignObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (JSGuards.isForeignObject(arg0Value)) {
                    foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                    this.foreignObject1_resultInterop_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.exclude_ = exclude |= 1;
                    this.foreignObject0_cache = null;
                    state_0 &= 0xFFFFF7FF;
                    this.state_0_ = state_0 |= 0x1000;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doForeignObject(arg0Value, foreignObject1_interop__, this.foreignObject1_resultInterop_);
                    return object;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            this.state_0_ = state_0 |= 0x2000;
            lock.unlock();
            hasLock = false;
            TruffleString truffleString = this.doFallback(arg0Value);
            return truffleString;
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
        if ((state_0 & 0x3FFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x3FFF & (state_0 & 0x3FFF) - 1) == 0 && ((s11_ = this.foreignObject0_cache) == null || s11_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[15];
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
        s[0] = "doBoolean";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doString";
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
        s[0] = "doNull";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[10] = s;
        s = new Object[3];
        s[0] = "doJSObject";
        if ((state_0 & 0x400) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            JSObjectData s10_ = this.jSObject_cache;
            if (s10_ != null) {
                cached.add(Arrays.asList(s10_.getToPrimitive_, s10_.isPrimitive_, s10_.exoticToPrimProfile_, s10_.callExoticToPrim_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[11] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x800) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ForeignObject0Data s11_ = this.foreignObject0_cache;
            while (s11_ != null) {
                cached.add(Arrays.asList(s11_.interop_, s11_.resultInterop_));
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
            cached.add(Arrays.asList(this.foreignObject1_resultInterop_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[13] = s;
        s = new Object[3];
        s[0] = "doFallback";
        s[1] = (state_0 & 0x2000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[14] = s;
        return Introspection.Provider.create(data);
    }

    private static boolean fallbackGuard_(int state_0, Object arg0Value) {
        if (JSTypesGen.isImplicitDouble(arg0Value)) {
            return false;
        }
        if ((state_0 & 0x10) == 0 && arg0Value instanceof Boolean) {
            return false;
        }
        if ((state_0 & 0x20) == 0 && arg0Value instanceof TruffleString) {
            return false;
        }
        if ((state_0 & 0x40) == 0 && arg0Value instanceof Symbol) {
            return false;
        }
        if ((state_0 & 0x80) == 0 && arg0Value instanceof BigInt) {
            return false;
        }
        if ((state_0 & 0x100) == 0 && JSGuards.isJSNull(arg0Value)) {
            return false;
        }
        if ((state_0 & 0x200) == 0 && JSGuards.isUndefined(arg0Value)) {
            return false;
        }
        if ((state_0 & 0x400) == 0 && arg0Value instanceof JSObject) {
            return false;
        }
        return (state_0 & 0x1000) != 0 || !JSGuards.isForeignObject(arg0Value);
    }

    public static JSToPrimitiveNode create(JSToPrimitiveNode.Hint hint) {
        return new JSToPrimitiveNodeGen(hint);
    }

    @GeneratedBy(value=JSToPrimitiveNode.class)
    private static final class ForeignObject0Data
    extends Node {
        @Node.Child
        ForeignObject0Data next_;
        @Node.Child
        InteropLibrary interop_;
        @Node.Child
        InteropLibrary resultInterop_;

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

    @GeneratedBy(value=JSToPrimitiveNode.class)
    private static final class JSObjectData
    extends Node {
        @Node.Child
        PropertyGetNode getToPrimitive_;
        @Node.Child
        IsPrimitiveNode isPrimitive_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile exoticToPrimProfile_;
        @Node.Child
        JSFunctionCallNode callExoticToPrim_;

        JSObjectData() {
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

