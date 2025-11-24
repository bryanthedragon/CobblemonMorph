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
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.Symbol;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToPropertyKeyNode.class)
public final class JSToPropertyKeyNodeGen
extends JSToPropertyKeyNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private OtherData other_cache;

    private JSToPropertyKeyNodeGen() {
    }

    @Override
    public Object execute(Object arg0Value) {
        OtherData s2_;
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.doTString(arg0Value_);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
        }
        if ((state_0 & 4) != 0 && (s2_ = this.other_cache) != null && !JSGuards.isSymbol(arg0Value)) {
            return this.doOther(arg0Value, s2_.toPrimitiveNode_, s2_.toStringNode_, s2_.isSymbol_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private Object executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doTString(arg0Value_);
                return truffleString;
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Symbol symbol = this.doSymbol(arg0Value_);
                return symbol;
            }
            if (!JSGuards.isSymbol(arg0Value)) {
                OtherData s2_ = super.insert(new OtherData());
                s2_.toPrimitiveNode_ = s2_.insertAccessor(JSToPrimitiveNode.createHintString());
                s2_.toStringNode_ = s2_.insertAccessor(JSToStringNode.create());
                s2_.isSymbol_ = ConditionProfile.createBinaryProfile();
                VarHandle.storeStoreFence();
                this.other_cache = s2_;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.doOther(arg0Value, s2_.toPrimitiveNode_, s2_.toStringNode_, s2_.isSymbol_);
                return object;
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
        Object[] data = new Object[4];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doTString";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doOther";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
            OtherData s2_ = this.other_cache;
            if (s2_ != null) {
                cached.add(Arrays.asList(s2_.toPrimitiveNode_, s2_.toStringNode_, s2_.isSymbol_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static JSToPropertyKeyNode create() {
        return new JSToPropertyKeyNodeGen();
    }

    @GeneratedBy(value=JSToPropertyKeyNode.JSToPropertyKeyWrapperNode.class)
    public static final class JSToPropertyKeyWrapperNodeGen
    extends JSToPropertyKeyNode.JSToPropertyKeyWrapperNode
    implements Introspection.Provider {
        private JSToPropertyKeyWrapperNodeGen(JavaScriptNode operand) {
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

        public static JSToPropertyKeyNode.JSToPropertyKeyWrapperNode create(JavaScriptNode operand) {
            return new JSToPropertyKeyWrapperNodeGen(operand);
        }
    }

    @GeneratedBy(value=JSToPropertyKeyNode.class)
    private static final class OtherData
    extends Node {
        @Node.Child
        JSToPrimitiveNode toPrimitiveNode_;
        @Node.Child
        JSToStringNode toStringNode_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile isSymbol_;

        OtherData() {
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

