
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSDoubleToStringNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToStringNode.class)
public final class JSToStringNodeGen
extends JSToStringNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private JSToPrimitiveNode toPrimitiveHintStringNode;
    @Node.Child
    private JSToStringNode toStringNode;
    @Node.Child
    private JSDoubleToStringNode double_doubleToStringNode_;

    private JSToStringNodeGen() {
    }

    private JSToStringNodeGen(boolean undefinedToEmpty, boolean symbolToString) {
        super(undefinedToEmpty, symbolToString);
    }

    @Override
    public TruffleString executeString(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.doString(arg0Value_);
        }
        if ((state_0 & 6) != 0) {
            if ((state_0 & 2) != 0 && JSGuards.isJSNull(arg0Value)) {
                return this.doNull(arg0Value);
            }
            if ((state_0 & 4) != 0 && JSGuards.isUndefined(arg0Value)) {
                return this.doUndefined(arg0Value);
            }
        }
        if ((state_0 & 8) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return this.doBoolean(arg0Value_);
        }
        if ((state_0 & 0x10) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return this.doInteger(arg0Value_);
        }
        if ((state_0 & 0x20) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_);
        }
        if ((state_0 & 0x40) != 0 && arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            return this.doLong(arg0Value_);
        }
        if ((state_0 & 0x80) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x7800) >>> 11, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0x7800) >>> 11, arg0Value);
            return this.doDouble(arg0Value_, this.double_doubleToStringNode_);
        }
        if ((state_0 & 0x100) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            return this.doJSObject(arg0Value_, this.toPrimitiveHintStringNode, this.toStringNode);
        }
        if ((state_0 & 0x200) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
        }
        if ((state_0 & 0x400) != 0 && JSGuards.isForeignObject(arg0Value)) {
            return this.doTruffleObject(arg0Value, this.toPrimitiveHintStringNode, this.toStringNode);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private TruffleString executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doString(arg0Value_);
                return truffleString;
            }
            if (JSGuards.isJSNull(arg0Value)) {
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                TruffleString arg0Value_ = this.doNull(arg0Value);
                return arg0Value_;
            }
            if (exclude == 0 && JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                TruffleString arg0Value_ = this.doUndefined(arg0Value);
                return arg0Value_;
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doBoolean(arg0Value_);
                return truffleString;
            }
            if (arg0Value instanceof Integer) {
                int arg0Value_ = (Integer)arg0Value;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doInteger(arg0Value_);
                return truffleString;
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doBigInt(arg0Value_);
                return truffleString;
            }
            if (arg0Value instanceof Long) {
                long arg0Value_ = (Long)arg0Value;
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doLong(arg0Value_);
                return truffleString;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast0 != 0) {
                double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                this.double_doubleToStringNode_ = super.insert(JSDoubleToStringNode.create());
                state_0 |= doubleCast0 << 11;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doDouble(arg0Value_, this.double_doubleToStringNode_);
                return truffleString;
            }
            if (arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                this.toPrimitiveHintStringNode = super.insert(this.toPrimitiveHintStringNode == null ? JSToPrimitiveNode.createHintString() : this.toPrimitiveHintStringNode);
                this.toStringNode = super.insert(this.toStringNode == null ? JSToStringNode.create() : this.toStringNode);
                this.exclude_ = exclude |= 1;
                state_0 &= 0xFFFFFFFB;
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doJSObject(arg0Value_, this.toPrimitiveHintStringNode, this.toStringNode);
                return truffleString;
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doSymbol(arg0Value_);
                return truffleString;
            }
            if (JSGuards.isForeignObject(arg0Value)) {
                this.toPrimitiveHintStringNode = super.insert(this.toPrimitiveHintStringNode == null ? JSToPrimitiveNode.createHintString() : this.toPrimitiveHintStringNode);
                this.toStringNode = super.insert(this.toStringNode == null ? JSToStringNode.create() : this.toStringNode);
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doTruffleObject(arg0Value, this.toPrimitiveHintStringNode, this.toStringNode);
                return truffleString;
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
        if ((state_0 & 0x7FF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x7FF & (state_0 & 0x7FF) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<JavaScriptBaseNode>> cached;
        Object[] data = new Object[12];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doString";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doNull";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : (exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[3] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doInteger";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doLong";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doDouble";
        if ((state_0 & 0x80) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<JavaScriptBaseNode>>();
            cached.add(Arrays.asList(this.double_doubleToStringNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[8] = s;
        s = new Object[3];
        s[0] = "doJSObject";
        if ((state_0 & 0x100) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.toPrimitiveHintStringNode, this.toStringNode));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[9] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[10] = s;
        s = new Object[3];
        s[0] = "doTruffleObject";
        if ((state_0 & 0x400) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.toPrimitiveHintStringNode, this.toStringNode));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[11] = s;
        return Introspection.Provider.create(data);
    }

    public static JSToStringNode create() {
        return new JSToStringNodeGen();
    }

    public static JSToStringNode create(boolean undefinedToEmpty, boolean symbolToString) {
        return new JSToStringNodeGen(undefinedToEmpty, symbolToString);
    }

    @GeneratedBy(value=JSToStringNode.JSToStringWrapperNode.class)
    public static final class JSToStringWrapperNodeGen
    extends JSToStringNode.JSToStringWrapperNode
    implements Introspection.Provider {
        private JSToStringWrapperNodeGen(JavaScriptNode operand) {
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

        public static JSToStringNode.JSToStringWrapperNode create(JavaScriptNode operand) {
            return new JSToStringWrapperNodeGen(operand);
        }
    }
}

