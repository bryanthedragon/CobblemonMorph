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
import com.oracle.truffle.js.nodes.unary.TypeOfNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TypeOfNode.class)
public final class TypeOfNodeGen
extends TypeOfNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private TypeOfNode jSProxy_typeofNode_;
    @Node.Child
    private TruffleObject0Data truffleObject0_cache;

    private TypeOfNodeGen(JavaScriptNode operand) {
        super(operand);
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
            return this.doString(operandNodeValue_);
        }
        if ((state_0 & 2) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            return this.doInt(operandNodeValue_);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C000) >>> 14, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C000) >>> 14, operandNodeValue);
            return this.doDouble(operandNodeValue_);
        }
        if ((state_0 & 8) != 0 && operandNodeValue instanceof Boolean) {
            boolean operandNodeValue_ = (Boolean)operandNodeValue;
            return this.doBoolean(operandNodeValue_);
        }
        if ((state_0 & 0x10) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            return this.doBigInt(operandNodeValue_);
        }
        if ((state_0 & 0x1E0) != 0) {
            if ((state_0 & 0x20) != 0 && JSGuards.isJSNull(operandNodeValue)) {
                return this.doNull(operandNodeValue);
            }
            if ((state_0 & 0x40) != 0 && JSGuards.isUndefined(operandNodeValue)) {
                return this.doUndefined(operandNodeValue);
            }
            if ((state_0 & 0x80) != 0 && JSGuards.isJSFunction(operandNodeValue)) {
                return this.doJSFunction(operandNodeValue);
            }
            if ((state_0 & 0x100) != 0 && JSGuards.isJSDynamicObject(operandNodeValue) && !JSGuards.isJSFunction(operandNodeValue) && !JSGuards.isUndefined(operandNodeValue) && !JSGuards.isJSProxy(operandNodeValue)) {
                return this.doJSObjectOnly(operandNodeValue);
            }
        }
        if ((state_0 & 0x200) != 0 && operandNodeValue instanceof JSProxyObject) {
            JSProxyObject operandNodeValue_ = (JSProxyObject)operandNodeValue;
            return this.doJSProxy(operandNodeValue_, this.jSProxy_typeofNode_);
        }
        if ((state_0 & 0x400) != 0 && operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_ = (Symbol)operandNodeValue;
            return this.doSymbol(operandNodeValue_);
        }
        if ((state_0 & 0x3800) != 0) {
            if ((state_0 & 0x800) != 0) {
                TruffleObject0Data s11_ = this.truffleObject0_cache;
                while (s11_ != null) {
                    if (s11_.interop_.accepts(operandNodeValue) && JSRuntime.isForeignObject(operandNodeValue)) {
                        return this.doTruffleObject(operandNodeValue, s11_.interop_);
                    }
                    s11_ = s11_.next_;
                }
            }
            if ((state_0 & 0x1000) != 0 && JSRuntime.isForeignObject(operandNodeValue)) {
                return this.truffleObject1Boundary(state_0, operandNodeValue);
            }
            if ((state_0 & 0x2000) != 0 && TypeOfNodeGen.fallbackGuard_(state_0, operandNodeValue)) {
                return this.doJavaObject(operandNodeValue);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private Object truffleObject1Boundary(int state_0, Object operandNodeValue) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
            TruffleString truffleString = this.doTruffleObject(operandNodeValue, truffleObject1_interop__);
            return truffleString;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @Override
    @ExplodeLoop
    public TruffleString executeString(Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
            return this.doString(operandNodeValue_);
        }
        if ((state_0 & 2) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            return this.doInt(operandNodeValue_);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C000) >>> 14, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C000) >>> 14, operandNodeValue);
            return this.doDouble(operandNodeValue_);
        }
        if ((state_0 & 8) != 0 && operandNodeValue instanceof Boolean) {
            boolean operandNodeValue_ = (Boolean)operandNodeValue;
            return this.doBoolean(operandNodeValue_);
        }
        if ((state_0 & 0x10) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            return this.doBigInt(operandNodeValue_);
        }
        if ((state_0 & 0x1E0) != 0) {
            if ((state_0 & 0x20) != 0 && JSGuards.isJSNull(operandNodeValue)) {
                return this.doNull(operandNodeValue);
            }
            if ((state_0 & 0x40) != 0 && JSGuards.isUndefined(operandNodeValue)) {
                return this.doUndefined(operandNodeValue);
            }
            if ((state_0 & 0x80) != 0 && JSGuards.isJSFunction(operandNodeValue)) {
                return this.doJSFunction(operandNodeValue);
            }
            if ((state_0 & 0x100) != 0 && JSGuards.isJSDynamicObject(operandNodeValue) && !JSGuards.isJSFunction(operandNodeValue) && !JSGuards.isUndefined(operandNodeValue) && !JSGuards.isJSProxy(operandNodeValue)) {
                return this.doJSObjectOnly(operandNodeValue);
            }
        }
        if ((state_0 & 0x200) != 0 && operandNodeValue instanceof JSProxyObject) {
            JSProxyObject operandNodeValue_ = (JSProxyObject)operandNodeValue;
            return this.doJSProxy(operandNodeValue_, this.jSProxy_typeofNode_);
        }
        if ((state_0 & 0x400) != 0 && operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_ = (Symbol)operandNodeValue;
            return this.doSymbol(operandNodeValue_);
        }
        if ((state_0 & 0x3800) != 0) {
            if ((state_0 & 0x800) != 0) {
                TruffleObject0Data s11_ = this.truffleObject0_cache;
                while (s11_ != null) {
                    if (s11_.interop_.accepts(operandNodeValue) && JSRuntime.isForeignObject(operandNodeValue)) {
                        return this.doTruffleObject(operandNodeValue, s11_.interop_);
                    }
                    s11_ = s11_.next_;
                }
            }
            if ((state_0 & 0x1000) != 0 && JSRuntime.isForeignObject(operandNodeValue)) {
                return this.truffleObject1Boundary0(state_0, operandNodeValue);
            }
            if ((state_0 & 0x2000) != 0 && TypeOfNodeGen.fallbackGuard_(state_0, operandNodeValue)) {
                return this.doJavaObject(operandNodeValue);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private TruffleString truffleObject1Boundary0(int state_0, Object operandNodeValue) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
            TruffleString truffleString = this.doTruffleObject(operandNodeValue, truffleObject1_interop__);
            return truffleString;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x3FFD) == 0 && (state_0 & 0x3FFF) != 0) {
            return this.execute_int0(state_0, frameValue);
        }
        if ((state_0 & 0x3FFB) == 0 && (state_0 & 0x3FFF) != 0) {
            return this.execute_double1(state_0, frameValue);
        }
        if ((state_0 & 0x3FF7) == 0 && (state_0 & 0x3FFF) != 0) {
            return this.execute_boolean2(state_0, frameValue);
        }
        return this.execute_generic3(state_0, frameValue);
    }

    private Object execute_int0(int state_0, VirtualFrame frameValue) {
        int operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 2) != 0);
        return this.doInt(operandNodeValue_);
    }

    private Object execute_double1(int state_0, VirtualFrame frameValue) {
        double operandNodeValue_;
        long operandNodeValue_long = 0L;
        int operandNodeValue_int = 0;
        try {
            if ((state_0 & 0x38000) == 0 && (state_0 & 0x3FFF) != 0) {
                operandNodeValue_ = this.operandNode.executeDouble(frameValue);
            } else if ((state_0 & 0x34000) == 0 && (state_0 & 0x3FFF) != 0) {
                operandNodeValue_int = this.operandNode.executeInt(frameValue);
                operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
            } else if ((state_0 & 0x1C000) == 0 && (state_0 & 0x3FFF) != 0) {
                operandNodeValue_long = this.operandNode.executeLong(frameValue);
                operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
            } else {
                Object operandNodeValue__ = this.operandNode.execute(frameValue);
                operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 0x3C000) >>> 14, operandNodeValue__);
            }
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 4) != 0);
        return this.doDouble(operandNodeValue_);
    }

    private Object execute_boolean2(int state_0, VirtualFrame frameValue) {
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private Object truffleObject1Boundary1(int state_0, Object operandNodeValue_) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue_);
            TruffleString truffleString = this.doTruffleObject(operandNodeValue_, truffleObject1_interop__);
            return truffleString;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @ExplodeLoop
    private Object execute_generic3(int state_0, VirtualFrame frameValue) {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof TruffleString) {
            TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
            return this.doString(operandNodeValue__);
        }
        if ((state_0 & 2) != 0 && operandNodeValue_ instanceof Integer) {
            int operandNodeValue__ = (Integer)operandNodeValue_;
            return this.doInt(operandNodeValue__);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C000) >>> 14, operandNodeValue_)) {
            double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C000) >>> 14, operandNodeValue_);
            return this.doDouble(operandNodeValue__);
        }
        if ((state_0 & 8) != 0 && operandNodeValue_ instanceof Boolean) {
            boolean operandNodeValue__ = (Boolean)operandNodeValue_;
            return this.doBoolean(operandNodeValue__);
        }
        if ((state_0 & 0x10) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
            return this.doBigInt(operandNodeValue__);
        }
        if ((state_0 & 0x1E0) != 0) {
            if ((state_0 & 0x20) != 0 && JSGuards.isJSNull(operandNodeValue_)) {
                return this.doNull(operandNodeValue_);
            }
            if ((state_0 & 0x40) != 0 && JSGuards.isUndefined(operandNodeValue_)) {
                return this.doUndefined(operandNodeValue_);
            }
            if ((state_0 & 0x80) != 0 && JSGuards.isJSFunction(operandNodeValue_)) {
                return this.doJSFunction(operandNodeValue_);
            }
            if ((state_0 & 0x100) != 0 && JSGuards.isJSDynamicObject(operandNodeValue_) && !JSGuards.isJSFunction(operandNodeValue_) && !JSGuards.isUndefined(operandNodeValue_) && !JSGuards.isJSProxy(operandNodeValue_)) {
                return this.doJSObjectOnly(operandNodeValue_);
            }
        }
        if ((state_0 & 0x200) != 0 && operandNodeValue_ instanceof JSProxyObject) {
            JSProxyObject operandNodeValue__ = (JSProxyObject)operandNodeValue_;
            return this.doJSProxy(operandNodeValue__, this.jSProxy_typeofNode_);
        }
        if ((state_0 & 0x400) != 0 && operandNodeValue_ instanceof Symbol) {
            Symbol operandNodeValue__ = (Symbol)operandNodeValue_;
            return this.doSymbol(operandNodeValue__);
        }
        if ((state_0 & 0x3800) != 0) {
            if ((state_0 & 0x800) != 0) {
                TruffleObject0Data s11_ = this.truffleObject0_cache;
                while (s11_ != null) {
                    if (s11_.interop_.accepts(operandNodeValue_) && JSRuntime.isForeignObject(operandNodeValue_)) {
                        return this.doTruffleObject(operandNodeValue_, s11_.interop_);
                    }
                    s11_ = s11_.next_;
                }
            }
            if ((state_0 & 0x1000) != 0 && JSRuntime.isForeignObject(operandNodeValue_)) {
                return this.truffleObject1Boundary1(state_0, operandNodeValue_);
            }
            if ((state_0 & 0x2000) != 0 && TypeOfNodeGen.fallbackGuard_(state_0, operandNodeValue_)) {
                return this.doJavaObject(operandNodeValue_);
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
    private TruffleString executeAndSpecialize(Object operandNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (operandNodeValue instanceof TruffleString) {
                TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doString(operandNodeValue_);
                return truffleString;
            }
            if (operandNodeValue instanceof Integer) {
                int operandNodeValue_ = (Integer)operandNodeValue;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doInt(operandNodeValue_);
                return truffleString;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue);
            if (doubleCast0 != 0) {
                double operandNodeValue_2 = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
                state_0 |= doubleCast0 << 14;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doDouble(operandNodeValue_2);
                return truffleString;
            }
            if (operandNodeValue instanceof Boolean) {
                boolean operandNodeValue_ = (Boolean)operandNodeValue;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                TruffleString operandNodeValue_2 = this.doBoolean(operandNodeValue_);
                return operandNodeValue_2;
            }
            if (operandNodeValue instanceof BigInt) {
                BigInt operandNodeValue_ = (BigInt)operandNodeValue;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                TruffleString operandNodeValue_2 = this.doBigInt(operandNodeValue_);
                return operandNodeValue_2;
            }
            if (JSGuards.isJSNull(operandNodeValue)) {
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                TruffleString operandNodeValue_ = this.doNull(operandNodeValue);
                return operandNodeValue_;
            }
            if (JSGuards.isUndefined(operandNodeValue)) {
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                TruffleString operandNodeValue_ = this.doUndefined(operandNodeValue);
                return operandNodeValue_;
            }
            if (JSGuards.isJSFunction(operandNodeValue)) {
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                TruffleString operandNodeValue_ = this.doJSFunction(operandNodeValue);
                return operandNodeValue_;
            }
            if (JSGuards.isJSDynamicObject(operandNodeValue) && !JSGuards.isJSFunction(operandNodeValue) && !JSGuards.isUndefined(operandNodeValue) && !JSGuards.isJSProxy(operandNodeValue)) {
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                TruffleString operandNodeValue_ = this.doJSObjectOnly(operandNodeValue);
                return operandNodeValue_;
            }
            if (operandNodeValue instanceof JSProxyObject) {
                JSProxyObject operandNodeValue_ = (JSProxyObject)operandNodeValue;
                this.jSProxy_typeofNode_ = super.insert(TypeOfNode.create());
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                TruffleString operandNodeValue_2 = this.doJSProxy(operandNodeValue_, this.jSProxy_typeofNode_);
                return operandNodeValue_2;
            }
            if (operandNodeValue instanceof Symbol) {
                Symbol operandNodeValue_ = (Symbol)operandNodeValue;
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                TruffleString operandNodeValue_2 = this.doSymbol(operandNodeValue_);
                return operandNodeValue_2;
            }
            if (exclude == 0) {
                int count11_ = 0;
                TruffleObject0Data s11_ = this.truffleObject0_cache;
                if ((state_0 & 0x800) != 0) {
                    while (!(s11_ == null || s11_.interop_.accepts(operandNodeValue) && JSRuntime.isForeignObject(operandNodeValue))) {
                        s11_ = s11_.next_;
                        ++count11_;
                    }
                }
                if (s11_ == null && JSRuntime.isForeignObject(operandNodeValue) && count11_ < 5) {
                    s11_ = super.insert(new TruffleObject0Data(this.truffleObject0_cache));
                    s11_.interop_ = s11_.insertAccessor(INTEROP_LIBRARY_.create(operandNodeValue));
                    VarHandle.storeStoreFence();
                    this.truffleObject0_cache = s11_;
                    this.state_0_ = state_0 |= 0x800;
                }
                if (s11_ != null) {
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.doTruffleObject(operandNodeValue, s11_.interop_);
                    return truffleString;
                }
            }
            InteropLibrary truffleObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (JSRuntime.isForeignObject(operandNodeValue)) {
                    truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
                    this.exclude_ = exclude |= 1;
                    this.truffleObject0_cache = null;
                    state_0 &= 0xFFFFF7FF;
                    this.state_0_ = state_0 |= 0x1000;
                    lock.unlock();
                    hasLock = false;
                    TruffleString truffleString = this.doTruffleObject(operandNodeValue, truffleObject1_interop__);
                    return truffleString;
                }
            }
            finally {
                encapsulating_.set(prev_);
            }
            this.state_0_ = state_0 |= 0x2000;
            lock.unlock();
            hasLock = false;
            TruffleString truffleString = this.doJavaObject(operandNodeValue);
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
        TruffleObject0Data s11_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x3FFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x3FFF & (state_0 & 0x3FFF) - 1) == 0 && ((s11_ = this.truffleObject0_cache) == null || s11_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Object>> cached;
        Object[] data = new Object[15];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doString";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doNull";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doJSFunction";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doJSObjectOnly";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doJSProxy";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.jSProxy_typeofNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[10] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[11] = s;
        s = new Object[3];
        s[0] = "doTruffleObject";
        if ((state_0 & 0x800) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            TruffleObject0Data s11_ = this.truffleObject0_cache;
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
        s[0] = "doTruffleObject";
        if ((state_0 & 0x1000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(new Object[0]));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[13] = s;
        s = new Object[3];
        s[0] = "doJavaObject";
        s[1] = (state_0 & 0x2000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[14] = s;
        return Introspection.Provider.create(data);
    }

    private static boolean fallbackGuard_(int state_0, Object operandNodeValue) {
        if ((state_0 & 1) == 0 && operandNodeValue instanceof TruffleString) {
            return false;
        }
        if (JSTypesGen.isImplicitDouble(operandNodeValue)) {
            return false;
        }
        if ((state_0 & 8) == 0 && operandNodeValue instanceof Boolean) {
            return false;
        }
        if ((state_0 & 0x10) == 0 && operandNodeValue instanceof BigInt) {
            return false;
        }
        if ((state_0 & 0x20) == 0 && JSGuards.isJSNull(operandNodeValue)) {
            return false;
        }
        if ((state_0 & 0x40) == 0 && JSGuards.isUndefined(operandNodeValue)) {
            return false;
        }
        if ((state_0 & 0x80) == 0 && JSGuards.isJSFunction(operandNodeValue)) {
            return false;
        }
        if ((state_0 & 0x100) == 0 && JSGuards.isJSDynamicObject(operandNodeValue) && !JSGuards.isJSFunction(operandNodeValue) && !JSGuards.isUndefined(operandNodeValue) && !JSGuards.isJSProxy(operandNodeValue)) {
            return false;
        }
        if ((state_0 & 0x200) == 0 && operandNodeValue instanceof JSProxyObject) {
            return false;
        }
        if ((state_0 & 0x400) == 0 && operandNodeValue instanceof Symbol) {
            return false;
        }
        return (state_0 & 0x1000) != 0 || !JSRuntime.isForeignObject(operandNodeValue);
    }

    public static TypeOfNode create(JavaScriptNode operand) {
        return new TypeOfNodeGen(operand);
    }

    @GeneratedBy(value=TypeOfNode.class)
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

