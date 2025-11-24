/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.binary;

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
import com.oracle.truffle.js.nodes.binary.JSTypeofIdenticalNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSTypeofIdenticalNode.class)
public final class JSTypeofIdenticalNodeGen
extends JSTypeofIdenticalNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private IsCallableNode typeObjectOrFunctionJSProxy_isCallableNode_;
    @Node.Child
    private ForeignObject0Data foreignObject0_cache;

    private JSTypeofIdenticalNodeGen(JavaScriptNode childNode, JSTypeofIdenticalNode.Type type) {
        super(childNode, type);
    }

    @Override
    @ExplodeLoop
    public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && operandNodeValue instanceof Boolean) {
            boolean operandNodeValue_ = (Boolean)operandNodeValue;
            return this.doBoolean(operandNodeValue_);
        }
        if ((state_0 & 2) != 0 && operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            return this.doNumber(operandNodeValue_);
        }
        if ((state_0 & 4) != 0 && operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
            return this.doNumber(operandNodeValue_);
        }
        if ((state_0 & 8) != 0 && operandNodeValue instanceof Long) {
            long operandNodeValue_ = (Long)operandNodeValue;
            return this.doNumber(operandNodeValue_);
        }
        if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C000) >>> 14, operandNodeValue)) {
            double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C000) >>> 14, operandNodeValue);
            return this.doNumber(operandNodeValue_);
        }
        if ((state_0 & 0x20) != 0 && operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_ = (Symbol)operandNodeValue;
            return this.doSymbol(operandNodeValue_);
        }
        if ((state_0 & 0x40) != 0 && operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            return this.doBigInt(operandNodeValue_);
        }
        if ((state_0 & 0x80) != 0 && operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
            return this.doString(operandNodeValue_);
        }
        if ((state_0 & 0x100) != 0) {
            assert (this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function);
            if (JSGuards.isJSFunction(operandNodeValue)) {
                return this.doTypeObjectOrFunctionJSFunction(operandNodeValue);
            }
        }
        if ((state_0 & 0x200) != 0 && operandNodeValue instanceof JSProxyObject) {
            JSProxyObject operandNodeValue_ = (JSProxyObject)operandNodeValue;
            assert (this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function);
            return this.doTypeObjectOrFunctionJSProxy(operandNodeValue_, this.typeObjectOrFunctionJSProxy_isCallableNode_);
        }
        if ((state_0 & 0xC00) != 0 && operandNodeValue instanceof JSDynamicObject) {
            JSDynamicObject operandNodeValue_ = (JSDynamicObject)operandNodeValue;
            if ((state_0 & 0x400) != 0) {
                assert (this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function);
                if (!JSGuards.isJSFunction(operandNodeValue_) && !JSGuards.isJSProxy(operandNodeValue_)) {
                    return this.doTypeObjectOrFunctionOther(operandNodeValue_);
                }
            }
            if ((state_0 & 0x800) != 0) {
                assert (this.type != JSTypeofIdenticalNode.Type.Object);
                assert (this.type != JSTypeofIdenticalNode.Type.Function);
                return this.doTypePrimitive(operandNodeValue_);
            }
        }
        if ((state_0 & 0x3000) != 0) {
            if ((state_0 & 0x1000) != 0) {
                ForeignObject0Data s12_ = this.foreignObject0_cache;
                while (s12_ != null) {
                    if (s12_.interop_.accepts(operandNodeValue) && JSGuards.isForeignObject(operandNodeValue)) {
                        return this.doForeignObject(operandNodeValue, s12_.interop_);
                    }
                    s12_ = s12_.next_;
                }
            }
            if ((state_0 & 0x2000) != 0 && JSGuards.isForeignObject(operandNodeValue)) {
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
            Boolean bl = this.doForeignObject(operandNodeValue, foreignObject1_interop__);
            return bl;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        if ((state_0 & 0x3FFE) == 0 && (state_0 & 0x3FFF) != 0) {
            return this.executeBoolean_boolean0(state_0, frameValue);
        }
        if ((state_0 & 0x3FFD) == 0 && (state_0 & 0x3FFF) != 0) {
            return this.executeBoolean_int1(state_0, frameValue);
        }
        if ((state_0 & 0x3FF7) == 0 && (state_0 & 0x3FFF) != 0) {
            return this.executeBoolean_long2(state_0, frameValue);
        }
        if ((state_0 & 0x3FEF) == 0 && (state_0 & 0x3FFF) != 0) {
            return this.executeBoolean_double3(state_0, frameValue);
        }
        return this.executeBoolean_generic4(state_0, frameValue);
    }

    private boolean executeBoolean_boolean0(int state_0, VirtualFrame frameValue) {
        boolean operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 1) != 0);
        return this.doBoolean(operandNodeValue_);
    }

    private boolean executeBoolean_int1(int state_0, VirtualFrame frameValue) {
        int operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeInt(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 2) != 0);
        return this.doNumber(operandNodeValue_);
    }

    private boolean executeBoolean_long2(int state_0, VirtualFrame frameValue) {
        long operandNodeValue_;
        try {
            operandNodeValue_ = this.operandNode.executeLong(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(ex.getResult());
        }
        assert ((state_0 & 8) != 0);
        return this.doNumber(operandNodeValue_);
    }

    private boolean executeBoolean_double3(int state_0, VirtualFrame frameValue) {
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
        assert ((state_0 & 0x10) != 0);
        return this.doNumber(operandNodeValue_);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    private boolean foreignObject1Boundary0(int state_0, Object operandNodeValue_) {
        EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
        Node prev_ = encapsulating_.set(this);
        try {
            InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue_);
            boolean bl = this.doForeignObject(operandNodeValue_, foreignObject1_interop__);
            return bl;
        }
        finally {
            encapsulating_.set(prev_);
        }
    }

    @ExplodeLoop
    private boolean executeBoolean_generic4(int state_0, VirtualFrame frameValue) {
        Object operandNodeValue_ = this.operandNode.execute(frameValue);
        if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Boolean) {
            boolean operandNodeValue__ = (Boolean)operandNodeValue_;
            return this.doBoolean(operandNodeValue__);
        }
        if ((state_0 & 2) != 0 && operandNodeValue_ instanceof Integer) {
            int operandNodeValue__ = (Integer)operandNodeValue_;
            return this.doNumber(operandNodeValue__);
        }
        if ((state_0 & 4) != 0 && operandNodeValue_ instanceof SafeInteger) {
            SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;
            return this.doNumber(operandNodeValue__);
        }
        if ((state_0 & 8) != 0 && operandNodeValue_ instanceof Long) {
            long operandNodeValue__ = (Long)operandNodeValue_;
            return this.doNumber(operandNodeValue__);
        }
        if ((state_0 & 0x10) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C000) >>> 14, operandNodeValue_)) {
            double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 0x3C000) >>> 14, operandNodeValue_);
            return this.doNumber(operandNodeValue__);
        }
        if ((state_0 & 0x20) != 0 && operandNodeValue_ instanceof Symbol) {
            Symbol operandNodeValue__ = (Symbol)operandNodeValue_;
            return this.doSymbol(operandNodeValue__);
        }
        if ((state_0 & 0x40) != 0 && operandNodeValue_ instanceof BigInt) {
            BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
            return this.doBigInt(operandNodeValue__);
        }
        if ((state_0 & 0x80) != 0 && operandNodeValue_ instanceof TruffleString) {
            TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
            return this.doString(operandNodeValue__);
        }
        if ((state_0 & 0x100) != 0) {
            assert (this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function);
            if (JSGuards.isJSFunction(operandNodeValue_)) {
                return this.doTypeObjectOrFunctionJSFunction(operandNodeValue_);
            }
        }
        if ((state_0 & 0x200) != 0 && operandNodeValue_ instanceof JSProxyObject) {
            JSProxyObject operandNodeValue__ = (JSProxyObject)operandNodeValue_;
            assert (this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function);
            return this.doTypeObjectOrFunctionJSProxy(operandNodeValue__, this.typeObjectOrFunctionJSProxy_isCallableNode_);
        }
        if ((state_0 & 0xC00) != 0 && operandNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject operandNodeValue__ = (JSDynamicObject)operandNodeValue_;
            if ((state_0 & 0x400) != 0) {
                assert (this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function);
                if (!JSGuards.isJSFunction(operandNodeValue__) && !JSGuards.isJSProxy(operandNodeValue__)) {
                    return this.doTypeObjectOrFunctionOther(operandNodeValue__);
                }
            }
            if ((state_0 & 0x800) != 0) {
                assert (this.type != JSTypeofIdenticalNode.Type.Object);
                assert (this.type != JSTypeofIdenticalNode.Type.Function);
                return this.doTypePrimitive(operandNodeValue__);
            }
        }
        if ((state_0 & 0x3000) != 0) {
            if ((state_0 & 0x1000) != 0) {
                ForeignObject0Data s12_ = this.foreignObject0_cache;
                while (s12_ != null) {
                    if (s12_.interop_.accepts(operandNodeValue_) && JSGuards.isForeignObject(operandNodeValue_)) {
                        return this.doForeignObject(operandNodeValue_, s12_.interop_);
                    }
                    s12_ = s12_.next_;
                }
            }
            if ((state_0 & 0x2000) != 0 && JSGuards.isForeignObject(operandNodeValue_)) {
                return this.foreignObject1Boundary0(state_0, operandNodeValue_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(operandNodeValue_);
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
            if (operandNodeValue instanceof Boolean) {
                boolean operandNodeValue_ = (Boolean)operandNodeValue;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doBoolean(operandNodeValue_);
                return bl;
            }
            if (operandNodeValue instanceof Integer) {
                int operandNodeValue_ = (Integer)operandNodeValue;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doNumber(operandNodeValue_);
                return bl;
            }
            if (operandNodeValue instanceof SafeInteger) {
                SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doNumber(operandNodeValue_);
                return bl;
            }
            if (operandNodeValue instanceof Long) {
                long operandNodeValue_ = (Long)operandNodeValue;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doNumber(operandNodeValue_);
                return bl;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue);
            if (doubleCast0 != 0) {
                double operandNodeValue_2 = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
                state_0 |= doubleCast0 << 14;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doNumber(operandNodeValue_2);
                return bl;
            }
            if (operandNodeValue instanceof Symbol) {
                Symbol operandNodeValue_ = (Symbol)operandNodeValue;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                boolean operandNodeValue_2 = this.doSymbol(operandNodeValue_);
                return operandNodeValue_2;
            }
            if (operandNodeValue instanceof BigInt) {
                BigInt operandNodeValue_ = (BigInt)operandNodeValue;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                boolean operandNodeValue_2 = this.doBigInt(operandNodeValue_);
                return operandNodeValue_2;
            }
            if (operandNodeValue instanceof TruffleString) {
                TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                boolean operandNodeValue_2 = this.doString(operandNodeValue_);
                return operandNodeValue_2;
            }
            if ((this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function) && JSGuards.isJSFunction(operandNodeValue)) {
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                boolean operandNodeValue_ = this.doTypeObjectOrFunctionJSFunction(operandNodeValue);
                return operandNodeValue_;
            }
            if (operandNodeValue instanceof JSProxyObject) {
                JSProxyObject operandNodeValue_ = (JSProxyObject)operandNodeValue;
                if (this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function) {
                    this.typeObjectOrFunctionJSProxy_isCallableNode_ = super.insert(IsCallableNode.create());
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    boolean operandNodeValue_2 = this.doTypeObjectOrFunctionJSProxy(operandNodeValue_, this.typeObjectOrFunctionJSProxy_isCallableNode_);
                    return operandNodeValue_2;
                }
            }
            if (operandNodeValue instanceof JSDynamicObject) {
                JSDynamicObject operandNodeValue_ = (JSDynamicObject)operandNodeValue;
                if (!(this.type != JSTypeofIdenticalNode.Type.Object && this.type != JSTypeofIdenticalNode.Type.Function || JSGuards.isJSFunction(operandNodeValue_) || JSGuards.isJSProxy(operandNodeValue_))) {
                    this.state_0_ = state_0 |= 0x400;
                    lock.unlock();
                    hasLock = false;
                    boolean operandNodeValue_2 = this.doTypeObjectOrFunctionOther(operandNodeValue_);
                    return operandNodeValue_2;
                }
                if (this.type != JSTypeofIdenticalNode.Type.Object && this.type != JSTypeofIdenticalNode.Type.Function) {
                    this.state_0_ = state_0 |= 0x800;
                    lock.unlock();
                    hasLock = false;
                    boolean operandNodeValue_2 = this.doTypePrimitive(operandNodeValue_);
                    return operandNodeValue_2;
                }
            }
            if (exclude == 0) {
                int count12_ = 0;
                ForeignObject0Data s12_ = this.foreignObject0_cache;
                if ((state_0 & 0x1000) != 0) {
                    while (!(s12_ == null || s12_.interop_.accepts(operandNodeValue) && JSGuards.isForeignObject(operandNodeValue))) {
                        s12_ = s12_.next_;
                        ++count12_;
                    }
                }
                if (s12_ == null && JSGuards.isForeignObject(operandNodeValue) && count12_ < 5) {
                    s12_ = super.insert(new ForeignObject0Data(this.foreignObject0_cache));
                    s12_.interop_ = s12_.insertAccessor(INTEROP_LIBRARY_.create(operandNodeValue));
                    VarHandle.storeStoreFence();
                    this.foreignObject0_cache = s12_;
                    this.state_0_ = state_0 |= 0x1000;
                }
                if (s12_ != null) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doForeignObject(operandNodeValue, s12_.interop_);
                    return bl;
                }
            }
            InteropLibrary foreignObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);
            try {
                if (JSGuards.isForeignObject(operandNodeValue)) {
                    foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
                    this.exclude_ = exclude |= 1;
                    this.foreignObject0_cache = null;
                    state_0 &= 0xFFFFEFFF;
                    this.state_0_ = state_0 |= 0x2000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doForeignObject(operandNodeValue, foreignObject1_interop__);
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
        ForeignObject0Data s12_;
        int state_0 = this.state_0_;
        if ((state_0 & 0x3FFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x3FFF & (state_0 & 0x3FFF) - 1) == 0 && ((s12_ = this.foreignObject0_cache) == null || s12_.next_ == null)) {
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
        s[0] = "doBoolean";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doNumber";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doNumber";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doNumber";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doNumber";
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
        s[0] = "doString";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doTypeObjectOrFunctionJSFunction";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doTypeObjectOrFunctionJSProxy";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.typeObjectOrFunctionJSProxy_isCallableNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[10] = s;
        s = new Object[3];
        s[0] = "doTypeObjectOrFunctionOther";
        s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[11] = s;
        s = new Object[3];
        s[0] = "doTypePrimitive";
        s[1] = (state_0 & 0x800) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[12] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x1000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            ForeignObject0Data s12_ = this.foreignObject0_cache;
            while (s12_ != null) {
                cached.add(Arrays.asList(s12_.interop_));
                s12_ = s12_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[13] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x2000) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(new Object[0]));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[14] = s;
        return Introspection.Provider.create(data);
    }

    public static JSTypeofIdenticalNode create(JavaScriptNode childNode, JSTypeofIdenticalNode.Type type) {
        return new JSTypeofIdenticalNodeGen(childNode, type);
    }

    @GeneratedBy(value=JSTypeofIdenticalNode.class)
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

