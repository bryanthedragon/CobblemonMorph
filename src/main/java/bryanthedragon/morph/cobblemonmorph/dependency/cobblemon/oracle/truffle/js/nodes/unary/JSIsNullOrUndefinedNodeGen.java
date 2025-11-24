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
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.JSIsNullOrUndefinedNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSIsNullOrUndefinedNode.class)
public final class JSIsNullOrUndefinedNodeGen
extends JSIsNullOrUndefinedNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private Class<?> jSObjectCached_cachedClass_;
    @Node.Child
    private JSValueOrForeign0Data jSValueOrForeign0_cache;

    private JSIsNullOrUndefinedNodeGen(JavaScriptNode operand, boolean isUndefined, boolean isLeft) {
        super(operand, isUndefined, isLeft);
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isJSNull(operandNodeValue)) {
                return JSIsNullOrUndefinedNode.doNull(operandNodeValue);
            }
            if ((state_0 & 2) != 0 && JSGuards.isUndefined(operandNodeValue)) {
                return JSIsNullOrUndefinedNode.doUndefined(operandNodeValue);
            }
        }
        if ((state_0 & 4) != 0 && operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_ = (Symbol)operandNodeValue;
            return JSIsNullOrUndefinedNode.doSymbol(operandNodeValue_);
        }
        if ((state_0 & 8) != 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
            return JSIsNullOrUndefinedNode.doTString(operandNodeValue_);
        }
        if ((state_0 & 0x10) != 0 && operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
            return JSIsNullOrUndefinedNode.doSafeInteger(operandNodeValue_);
        }
        if ((state_0 & 0x20) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            return JSIsNullOrUndefinedNode.doBigInt(operandNodeValue_);
        }
        if ((state_0 & 0x3C0) != 0) {
            if ((state_0 & 0x40) != 0) {
                assert (this.jSObjectCached_cachedClass_ != null);
                if (CompilerDirectives.isExact(operandNodeValue, this.jSObjectCached_cachedClass_)) {
                    return JSIsNullOrUndefinedNode.doJSObjectCached(operandNodeValue, this.jSObjectCached_cachedClass_);
                }
            }
            if ((state_0 & 0x80) != 0 && JSGuards.isJSObject(operandNodeValue)) {
                return JSIsNullOrUndefinedNode.doJSObject(operandNodeValue);
            }
            if ((state_0 & 0x100) != 0) {
                JSValueOrForeign0Data s8_ = this.jSValueOrForeign0_cache;
                while (s8_ != null) {
                    if (s8_.interop_.accepts(operandNodeValue) && !JSGuards.isJSDynamicObject(operandNodeValue)) {
                        return this.doJSValueOrForeign(operandNodeValue, s8_.interop_);
                    }
                    s8_ = s8_.next_;
                }
            }
            if ((state_0 & 0x200) != 0 && !JSGuards.isJSDynamicObject(operandNodeValue)) {
                return this.jSValueOrForeign1Boundary(state_0, operandNodeValue);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private Object jSValueOrForeign1Boundary(int state_0, Object operandNodeValue) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary jSValueOrForeign1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
            Boolean bl = this.doJSValueOrForeign(operandNodeValue, jSValueOrForeign1_interop__);
            return bl;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @Override
    @ExplodeLoop
    public boolean executeBoolean(Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 3) != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isJSNull(operandNodeValue)) {
                return JSIsNullOrUndefinedNode.doNull(operandNodeValue);
            }
            if ((state_0 & 2) != 0 && JSGuards.isUndefined(operandNodeValue)) {
                return JSIsNullOrUndefinedNode.doUndefined(operandNodeValue);
            }
        }
        if ((state_0 & 4) != 0 && operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_ = (Symbol)operandNodeValue;
            return JSIsNullOrUndefinedNode.doSymbol(operandNodeValue_);
        }
        if ((state_0 & 8) != 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
            return JSIsNullOrUndefinedNode.doTString(operandNodeValue_);
        }
        if ((state_0 & 0x10) != 0 && operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
            return JSIsNullOrUndefinedNode.doSafeInteger(operandNodeValue_);
        }
        if ((state_0 & 0x20) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            return JSIsNullOrUndefinedNode.doBigInt(operandNodeValue_);
        }
        if ((state_0 & 0x3C0) != 0) {
            if ((state_0 & 0x40) != 0) {
                assert (this.jSObjectCached_cachedClass_ != null);
                if (CompilerDirectives.isExact(operandNodeValue, this.jSObjectCached_cachedClass_)) {
                    return JSIsNullOrUndefinedNode.doJSObjectCached(operandNodeValue, this.jSObjectCached_cachedClass_);
                }
            }
            if ((state_0 & 0x80) != 0 && JSGuards.isJSObject(operandNodeValue)) {
                return JSIsNullOrUndefinedNode.doJSObject(operandNodeValue);
            }
            if ((state_0 & 0x100) != 0) {
                JSValueOrForeign0Data s8_ = this.jSValueOrForeign0_cache;
                while (s8_ != null) {
                    if (s8_.interop_.accepts(operandNodeValue) && !JSGuards.isJSDynamicObject(operandNodeValue)) {
                        return this.doJSValueOrForeign(operandNodeValue, s8_.interop_);
                    }
                    s8_ = s8_.next_;
                }
            }
            if ((state_0 & 0x200) != 0 && !JSGuards.isJSDynamicObject(operandNodeValue)) {
                return this.jSValueOrForeign1Boundary0(state_0, operandNodeValue);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private boolean jSValueOrForeign1Boundary0(int state_0, Object operandNodeValue) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary jSValueOrForeign1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
            boolean bl = this.doJSValueOrForeign(operandNodeValue, jSValueOrForeign1_interop__);
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
        if ((state_0 & 3) != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isJSNull(operandNodeValue_)) {
                return JSIsNullOrUndefinedNode.doNull(operandNodeValue_);
            }
            if ((state_0 & 2) != 0 && JSGuards.isUndefined(operandNodeValue_)) {
                return JSIsNullOrUndefinedNode.doUndefined(operandNodeValue_);
            }
        }
        if ((state_0 & 4) != 0 && operandNodeValue_ instanceof Symbol) {
            Symbol operandNodeValue__ = (Symbol)operandNodeValue_;
            return JSIsNullOrUndefinedNode.doSymbol(operandNodeValue__);
        }
        if ((state_0 & 8) != 0 && operandNodeValue_ instanceof TruffleString) {
            TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
            return JSIsNullOrUndefinedNode.doTString(operandNodeValue__);
        }
        if ((state_0 & 0x10) != 0 && operandNodeValue_ instanceof SafeInteger) {
            SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;
            return JSIsNullOrUndefinedNode.doSafeInteger(operandNodeValue__);
        }
        if ((state_0 & 0x20) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
            return JSIsNullOrUndefinedNode.doBigInt(operandNodeValue__);
        }
        if ((state_0 & 0x3C0) != 0) {
            if ((state_0 & 0x40) != 0) {
                assert (this.jSObjectCached_cachedClass_ != null);
                if (CompilerDirectives.isExact(operandNodeValue_, this.jSObjectCached_cachedClass_)) {
                    return JSIsNullOrUndefinedNode.doJSObjectCached(operandNodeValue_, this.jSObjectCached_cachedClass_);
                }
            }
            if ((state_0 & 0x80) != 0 && JSGuards.isJSObject(operandNodeValue_)) {
                return JSIsNullOrUndefinedNode.doJSObject(operandNodeValue_);
            }
            if ((state_0 & 0x100) != 0) {
                JSValueOrForeign0Data s8_ = this.jSValueOrForeign0_cache;
                while (s8_ != null) {
                    if (s8_.interop_.accepts(operandNodeValue_) && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
                        return this.doJSValueOrForeign(operandNodeValue_, s8_.interop_);
                    }
                    s8_ = s8_.next_;
                }
            }
            if ((state_0 & 0x200) != 0 && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
                return this.jSValueOrForeign1Boundary1(state_0, operandNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private Object jSValueOrForeign1Boundary1(int state_0, Object operandNodeValue_) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary jSValueOrForeign1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue_);
            Boolean bl = this.doJSValueOrForeign(operandNodeValue_, jSValueOrForeign1_interop__);
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
        if ((state_0 & 3) != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isJSNull(operandNodeValue_)) {
                return JSIsNullOrUndefinedNode.doNull(operandNodeValue_);
            }
            if ((state_0 & 2) != 0 && JSGuards.isUndefined(operandNodeValue_)) {
                return JSIsNullOrUndefinedNode.doUndefined(operandNodeValue_);
            }
        }
        if ((state_0 & 4) != 0 && operandNodeValue_ instanceof Symbol) {
            Symbol operandNodeValue__ = (Symbol)operandNodeValue_;
            return JSIsNullOrUndefinedNode.doSymbol(operandNodeValue__);
        }
        if ((state_0 & 8) != 0 && operandNodeValue_ instanceof TruffleString) {
            TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
            return JSIsNullOrUndefinedNode.doTString(operandNodeValue__);
        }
        if ((state_0 & 0x10) != 0 && operandNodeValue_ instanceof SafeInteger) {
            SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;
            return JSIsNullOrUndefinedNode.doSafeInteger(operandNodeValue__);
        }
        if ((state_0 & 0x20) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
            return JSIsNullOrUndefinedNode.doBigInt(operandNodeValue__);
        }
        if ((state_0 & 0x3C0) != 0) {
            if ((state_0 & 0x40) != 0) {
                assert (this.jSObjectCached_cachedClass_ != null);
                if (CompilerDirectives.isExact(operandNodeValue_, this.jSObjectCached_cachedClass_)) {
                    return JSIsNullOrUndefinedNode.doJSObjectCached(operandNodeValue_, this.jSObjectCached_cachedClass_);
                }
            }
            if ((state_0 & 0x80) != 0 && JSGuards.isJSObject(operandNodeValue_)) {
                return JSIsNullOrUndefinedNode.doJSObject(operandNodeValue_);
            }
            if ((state_0 & 0x100) != 0) {
                JSValueOrForeign0Data s8_ = this.jSValueOrForeign0_cache;
                while (s8_ != null) {
                    if (s8_.interop_.accepts(operandNodeValue_) && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
                        return this.doJSValueOrForeign(operandNodeValue_, s8_.interop_);
                    }
                    s8_ = s8_.next_;
                }
            }
            if ((state_0 & 0x200) != 0 && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
                return this.jSValueOrForeign1Boundary2(state_0, operandNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private boolean jSValueOrForeign1Boundary2(int state_0, Object operandNodeValue_) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary jSValueOrForeign1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue_);
            boolean bl = this.doJSValueOrForeign(operandNodeValue_, jSValueOrForeign1_interop__);
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
            boolean JSObjectCached_duplicateFound_;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (JSGuards.isJSNull(operandNodeValue)) {
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIsNullOrUndefinedNode.doNull(operandNodeValue);
                return bl;
            }
            if (JSGuards.isUndefined(operandNodeValue)) {
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIsNullOrUndefinedNode.doUndefined(operandNodeValue);
                return bl;
            }
            if (operandNodeValue instanceof Symbol) {
                Symbol operandNodeValue_ = (Symbol)operandNodeValue;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIsNullOrUndefinedNode.doSymbol(operandNodeValue_);
                return bl;
            }
            if (operandNodeValue instanceof TruffleString) {
                TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIsNullOrUndefinedNode.doTString(operandNodeValue_);
                return bl;
            }
            if (operandNodeValue instanceof SafeInteger) {
                SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIsNullOrUndefinedNode.doSafeInteger(operandNodeValue_);
                return bl;
            }
            if (operandNodeValue instanceof BigInt) {
                BigInt operandNodeValue_ = (BigInt)operandNodeValue;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                boolean bl = JSIsNullOrUndefinedNode.doBigInt(operandNodeValue_);
                return bl;
            }
            if ((exclude & 1) == 0) {
                Class<?> jSObjectCached_cachedClass__2;
                JSObjectCached_duplicateFound_ = false;
                if ((state_0 & 0x40) != 0) {
                    assert (this.jSObjectCached_cachedClass_ != null);
                    if (CompilerDirectives.isExact(operandNodeValue, this.jSObjectCached_cachedClass_)) {
                        JSObjectCached_duplicateFound_ = true;
                    }
                }
                if (!JSObjectCached_duplicateFound_ && (jSObjectCached_cachedClass__2 = JSGuards.getClassIfJSObject(operandNodeValue)) != null && CompilerDirectives.isExact(operandNodeValue, jSObjectCached_cachedClass__2) && (state_0 & 0x40) == 0) {
                    this.jSObjectCached_cachedClass_ = jSObjectCached_cachedClass__2;
                    this.state_0_ = state_0 |= 0x40;
                    JSObjectCached_duplicateFound_ = true;
                }
                if (JSObjectCached_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    boolean jSObjectCached_cachedClass__2 = JSIsNullOrUndefinedNode.doJSObjectCached(operandNodeValue, this.jSObjectCached_cachedClass_);
                    return jSObjectCached_cachedClass__2;
                }
            }
            if (JSGuards.isJSObject(operandNodeValue)) {
                this.exclude_ = exclude |= 1;
                state_0 &= 0xFFFFFFBF;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                JSObjectCached_duplicateFound_ = JSIsNullOrUndefinedNode.doJSObject(operandNodeValue);
                return JSObjectCached_duplicateFound_;
            }
            if ((exclude & 2) == 0) {
                int count8_ = 0;
                JSValueOrForeign0Data s8_ = this.jSValueOrForeign0_cache;
                if ((state_0 & 0x100) != 0) {
                    while (s8_ != null && (!s8_.interop_.accepts(operandNodeValue) || JSGuards.isJSDynamicObject(operandNodeValue))) {
                        s8_ = s8_.next_;
                        ++count8_;
                    }
                }
                if (s8_ == null && !JSGuards.isJSDynamicObject(operandNodeValue) && count8_ < 5) {
                    s8_ = super.insert(new JSValueOrForeign0Data(this.jSValueOrForeign0_cache));
                    s8_.interop_ = s8_.insertAccessor(INTEROP_LIBRARY_.create(operandNodeValue));
                    VarHandle.storeStoreFence();
                    this.jSValueOrForeign0_cache = s8_;
                    this.state_0_ = state_0 |= 0x100;
                }
                if (s8_ != null) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doJSValueOrForeign(operandNodeValue, s8_.interop_);
                    return bl;
                }
            }
            InteropLibrary jSValueOrForeign1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (!JSGuards.isJSDynamicObject(operandNodeValue)) {
                    jSValueOrForeign1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
                    this.exclude_ = exclude |= 2;
                    this.jSValueOrForeign0_cache = null;
                    state_0 &= 0xFFFFFEFF;
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doJSValueOrForeign(operandNodeValue, jSValueOrForeign1_interop__);
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
        JSValueOrForeign0Data s8_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s8_ = this.jSValueOrForeign0_cache) == null || s8_.next_ == null)) {
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
        s[0] = "doNull";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doTString";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doJSObjectCached";
        if ((state_0 & 0x40) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.jSObjectCached_cachedClass_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[7] = s;
        s = new Object[3];
        s[0] = "doJSObject";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doJSValueOrForeign";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            JSValueOrForeign0Data s8_ = this.jSValueOrForeign0_cache;
            while (s8_ != null) {
                cached.add(Arrays.asList(s8_.interop_));
                s8_ = s8_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[9] = s;
        s = new Object[3];
        s[0] = "doJSValueOrForeign";
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

    public static JSIsNullOrUndefinedNode create(JavaScriptNode operand, boolean isUndefined, boolean isLeft) {
        return new JSIsNullOrUndefinedNodeGen(operand, isUndefined, isLeft);
    }

    @GeneratedBy(value=JSIsNullOrUndefinedNode.class)
    private static final class JSValueOrForeign0Data
    extends Node {
        @Node.Child
        JSValueOrForeign0Data next_;
        @Node.Child
        InteropLibrary interop_;

        JSValueOrForeign0Data(JSValueOrForeign0Data next_) {
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

