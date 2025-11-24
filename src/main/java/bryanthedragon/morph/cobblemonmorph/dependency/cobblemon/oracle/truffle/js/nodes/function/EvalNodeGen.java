/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.function;

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
import com.oracle.truffle.js.nodes.function.AbstractFunctionArgumentsNode;
import com.oracle.truffle.js.nodes.function.EvalNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=EvalNode.class)
public final class EvalNodeGen
extends EvalNode
implements Introspection.Provider {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSFunctionCallNode evalOverridden_redirectCall_;

    private EvalNodeGen(JSContext context, JavaScriptNode function, JavaScriptNode[] args, JavaScriptNode thisObject, Object env, int blockScopeSlot) {
        super(context, function, args, thisObject, env, blockScopeSlot);
    }

    private EvalNodeGen(JSContext context, JavaScriptNode functionNode, AbstractFunctionArgumentsNode arguments, EvalNode.DirectEvalNode directEvalNode) {
        super(context, functionNode, arguments, directEvalNode);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object functionNodeValue_ = this.functionNode.execute(frameValue);
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && !this.isEvalOverridden(functionNodeValue_)) {
                return this.evalNotOverridden(frameValue, functionNodeValue_);
            }
            if ((state_0 & 2) != 0 && this.isEvalOverridden(functionNodeValue_)) {
                return this.evalOverridden(frameValue, functionNodeValue_, this.evalOverridden_redirectCall_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(frameValue, functionNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object functionNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (!this.isEvalOverridden(functionNodeValue)) {
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.evalNotOverridden(frameValue, functionNodeValue);
                return object;
            }
            if (this.isEvalOverridden(functionNodeValue)) {
                this.evalOverridden_redirectCall_ = super.insert(JSFunctionCallNode.createCall());
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = this.evalOverridden(frameValue, functionNodeValue, this.evalOverridden_redirectCall_);
                return object;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.functionNode}, functionNodeValue);
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "evalNotOverridden";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "evalOverridden";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<List<JSFunctionCallNode>> cached = new ArrayList<List<JSFunctionCallNode>>();
            cached.add(Arrays.asList(this.evalOverridden_redirectCall_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static EvalNode create(JSContext context, JavaScriptNode function, JavaScriptNode[] args, JavaScriptNode thisObject, Object env, int blockScopeSlot) {
        return new EvalNodeGen(context, function, args, thisObject, env, blockScopeSlot);
    }

    public static EvalNode create(JSContext context, JavaScriptNode functionNode, AbstractFunctionArgumentsNode arguments, EvalNode.DirectEvalNode directEvalNode) {
        return new EvalNodeGen(context, functionNode, arguments, directEvalNode);
    }

    @GeneratedBy(value=EvalNode.DirectEvalNode.class)
    protected static final class DirectEvalNodeGen
    extends EvalNode.DirectEvalNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private DirectEvalForeignObject0Data directEvalForeignObject0_cache;

        private DirectEvalNodeGen(JSContext context, JavaScriptNode thisNode, Object currEnv, int blockScopeSlot) {
            super(context, thisNode, currEnv, blockScopeSlot);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        @ExplodeLoop
        public Object executeWithSource(VirtualFrame frameValue, Object arg0Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
                int arg0Value_ = (Integer)arg0Value;
                return this.directEvalInt(arg0Value_);
            }
            if ((state_0 & 2) != 0 && arg0Value instanceof SafeInteger) {
                SafeInteger arg0Value_ = (SafeInteger)arg0Value;
                return this.directEvalSafeInteger(arg0Value_);
            }
            if ((state_0 & 4) != 0 && arg0Value instanceof Long) {
                long arg0Value_ = (Long)arg0Value;
                return this.directEvalLong(arg0Value_);
            }
            if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x7800) >>> 11, arg0Value)) {
                double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0x7800) >>> 11, arg0Value);
                return this.directEvalDouble(arg0Value_);
            }
            if ((state_0 & 0x10) != 0 && arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                return this.directEvalBoolean(arg0Value_);
            }
            if ((state_0 & 0x20) != 0 && arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                return this.directEvalSymbol(arg0Value_);
            }
            if ((state_0 & 0x40) != 0 && arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                return this.directEvalBigInt(arg0Value_);
            }
            if ((state_0 & 0x80) != 0 && arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                return this.directEvalJSType(arg0Value_);
            }
            if ((state_0 & 0x100) != 0 && arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                return this.directEvalCharSequence(frameValue, arg0Value_);
            }
            if ((state_0 & 0x600) != 0) {
                if ((state_0 & 0x200) != 0) {
                    DirectEvalForeignObject0Data s9_ = this.directEvalForeignObject0_cache;
                    while (s9_ != null) {
                        if (s9_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                            return this.directEvalForeignObject(frameValue, arg0Value, s9_.interop_);
                        }
                        s9_ = s9_.next_;
                    }
                }
                if ((state_0 & 0x400) != 0 && JSGuards.isForeignObject(arg0Value)) {
                    EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                    Node prev_ = encapsulating_.set(this);
                    try {
                        InteropLibrary directEvalForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached();
                        Object object = this.directEvalForeignObject(frameValue, arg0Value, directEvalForeignObject1_interop__);
                        return object;
                    }
                    finally {
                        encapsulating_.set(prev_);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue, arg0Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(VirtualFrame frameValue, Object arg0Value) {
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
                    Integer n = this.directEvalInt(arg0Value_);
                    return n;
                }
                if (arg0Value instanceof SafeInteger) {
                    SafeInteger arg0Value_ = (SafeInteger)arg0Value;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    SafeInteger safeInteger = this.directEvalSafeInteger(arg0Value_);
                    return safeInteger;
                }
                if (arg0Value instanceof Long) {
                    long arg0Value_ = (Long)arg0Value;
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Long l = this.directEvalLong(arg0Value_);
                    return l;
                }
                int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
                if (doubleCast0 != 0) {
                    double arg0Value_2 = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                    state_0 |= doubleCast0 << 11;
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.directEvalDouble(arg0Value_2);
                    return d;
                }
                if (arg0Value instanceof Boolean) {
                    boolean arg0Value_ = (Boolean)arg0Value;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    Boolean arg0Value_2 = this.directEvalBoolean(arg0Value_);
                    return arg0Value_2;
                }
                if (arg0Value instanceof Symbol) {
                    Symbol arg0Value_ = (Symbol)arg0Value;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    Symbol arg0Value_2 = this.directEvalSymbol(arg0Value_);
                    return arg0Value_2;
                }
                if (arg0Value instanceof BigInt) {
                    BigInt arg0Value_ = (BigInt)arg0Value;
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    BigInt arg0Value_2 = this.directEvalBigInt(arg0Value_);
                    return arg0Value_2;
                }
                if (arg0Value instanceof JSDynamicObject) {
                    JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject arg0Value_2 = this.directEvalJSType(arg0Value_);
                    return arg0Value_2;
                }
                if (arg0Value instanceof TruffleString) {
                    TruffleString arg0Value_ = (TruffleString)arg0Value;
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    Object arg0Value_2 = this.directEvalCharSequence(frameValue, arg0Value_);
                    return arg0Value_2;
                }
                if (exclude == 0) {
                    int count9_ = 0;
                    DirectEvalForeignObject0Data s9_ = this.directEvalForeignObject0_cache;
                    if ((state_0 & 0x200) != 0) {
                        while (!(s9_ == null || s9_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value))) {
                            s9_ = s9_.next_;
                            ++count9_;
                        }
                    }
                    if (s9_ == null && JSGuards.isForeignObject(arg0Value) && count9_ < 3) {
                        s9_ = super.insert(new DirectEvalForeignObject0Data(this.directEvalForeignObject0_cache));
                        s9_.interop_ = s9_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                        VarHandle.storeStoreFence();
                        this.directEvalForeignObject0_cache = s9_;
                        this.state_0_ = state_0 |= 0x200;
                    }
                    if (s9_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.directEvalForeignObject(frameValue, arg0Value, s9_.interop_);
                        return object;
                    }
                }
                InteropLibrary directEvalForeignObject1_interop__ = null;
                EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                Node prev_ = encapsulating_.set(this);
                try {
                    if (JSGuards.isForeignObject(arg0Value)) {
                        directEvalForeignObject1_interop__ = INTEROP_LIBRARY_.getUncached();
                        this.exclude_ = exclude |= 1;
                        this.directEvalForeignObject0_cache = null;
                        state_0 &= 0xFFFFFDFF;
                        this.state_0_ = state_0 |= 0x400;
                        lock.unlock();
                        hasLock = false;
                        Object object = this.directEvalForeignObject(frameValue, arg0Value, directEvalForeignObject1_interop__);
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
            DirectEvalForeignObject0Data s9_;
            int state_0 = this.state_0_;
            if ((state_0 & 0x7FF) == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & 0x7FF & (state_0 & 0x7FF) - 1) == 0 && ((s9_ = this.directEvalForeignObject0_cache) == null || s9_.next_ == null)) {
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
            s[0] = "directEvalInt";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "directEvalSafeInteger";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "directEvalLong";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "directEvalDouble";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "directEvalBoolean";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            s = new Object[3];
            s[0] = "directEvalSymbol";
            s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[6] = s;
            s = new Object[3];
            s[0] = "directEvalBigInt";
            s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[7] = s;
            s = new Object[3];
            s[0] = "directEvalJSType";
            s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[8] = s;
            s = new Object[3];
            s[0] = "directEvalCharSequence";
            s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[9] = s;
            s = new Object[3];
            s[0] = "directEvalForeignObject";
            if ((state_0 & 0x200) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                DirectEvalForeignObject0Data s9_ = this.directEvalForeignObject0_cache;
                while (s9_ != null) {
                    cached.add(Arrays.asList(s9_.interop_));
                    s9_ = s9_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[10] = s;
            s = new Object[3];
            s[0] = "directEvalForeignObject";
            if ((state_0 & 0x400) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[11] = s;
            return Introspection.Provider.create(data);
        }

        public static EvalNode.DirectEvalNode create(JSContext context, JavaScriptNode thisNode, Object currEnv, int blockScopeSlot) {
            return new DirectEvalNodeGen(context, thisNode, currEnv, blockScopeSlot);
        }

        @GeneratedBy(value=EvalNode.DirectEvalNode.class)
        private static final class DirectEvalForeignObject0Data
        extends Node {
            @Node.Child
            DirectEvalForeignObject0Data next_;
            @Node.Child
            InteropLibrary interop_;

            DirectEvalForeignObject0Data(DirectEvalForeignObject0Data next_) {
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
}

