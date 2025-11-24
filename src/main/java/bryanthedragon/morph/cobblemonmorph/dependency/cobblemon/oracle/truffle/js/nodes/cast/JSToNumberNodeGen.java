
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
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToNumberNode.class)
public final class JSToNumberNodeGen
extends JSToNumberNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSToPrimitiveNode toPrimitiveHintNumberNode;
    @Node.Child
    private JSToNumberNode toNumberNode;
    @Node.Child
    private JSStringToNumberNode string_stringToNumberNode_;

    private JSToNumberNodeGen() {
    }

    @Override
    public Object execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return JSToNumberNode.doInteger(arg0Value_);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return JSToNumberNode.doBoolean(arg0Value_);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x7800) >>> 11, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0x7800) >>> 11, arg0Value);
            return JSToNumberNode.doDouble(arg0Value_);
        }
        if ((state_0 & 0x18) != 0) {
            if ((state_0 & 8) != 0 && JSGuards.isJSNull(arg0Value)) {
                return JSToNumberNode.doNull(arg0Value);
            }
            if ((state_0 & 0x10) != 0 && JSGuards.isUndefined(arg0Value)) {
                return JSToNumberNode.doUndefined(arg0Value);
            }
        }
        if ((state_0 & 0x20) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.doString(arg0Value_, this.string_stringToNumberNode_);
        }
        if ((state_0 & 0x40) != 0 && arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            return this.doJSObject(arg0Value_, this.toPrimitiveHintNumberNode, this.toNumberNode);
        }
        if ((state_0 & 0x80) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
        }
        if ((state_0 & 0x100) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_);
        }
        if ((state_0 & 0x600) != 0) {
            if ((state_0 & 0x200) != 0 && JSGuards.isForeignObject(arg0Value)) {
                return this.doForeignObject(arg0Value, this.toPrimitiveHintNumberNode, this.toNumberNode);
            }
            if ((state_0 & 0x400) != 0 && JSGuards.isJavaNumber(arg0Value)) {
                return JSToNumberNode.doJavaObject(arg0Value);
            }
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
            if (arg0Value instanceof Integer) {
                int arg0Value_ = (Integer)arg0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Integer n = JSToNumberNode.doInteger(arg0Value_);
                return n;
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Integer n = JSToNumberNode.doBoolean(arg0Value_);
                return n;
            }
            int doubleCast02 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast02 != 0) {
                double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast02, arg0Value);
                state_0 |= doubleCast02 << 11;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Double d = JSToNumberNode.doDouble(arg0Value_);
                return d;
            }
            if (JSGuards.isJSNull(arg0Value)) {
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                Integer doubleCast02 = JSToNumberNode.doNull(arg0Value);
                return doubleCast02;
            }
            if (JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                Double doubleCast02 = JSToNumberNode.doUndefined(arg0Value);
                return doubleCast02;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.string_stringToNumberNode_ = super.insert(JSStringToNumberNode.create());
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                Number number = this.doString(arg0Value_, this.string_stringToNumberNode_);
                return number;
            }
            if (arg0Value instanceof JSObject) {
                JSObject arg0Value_ = (JSObject)arg0Value;
                this.toPrimitiveHintNumberNode = super.insert(this.toPrimitiveHintNumberNode == null ? JSToPrimitiveNode.createHintNumber() : this.toPrimitiveHintNumberNode);
                this.toNumberNode = super.insert(this.toNumberNode == null ? JSToNumberNode.create() : this.toNumberNode);
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                Number number = this.doJSObject(arg0Value_, this.toPrimitiveHintNumberNode, this.toNumberNode);
                return number;
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                Number number = this.doSymbol(arg0Value_);
                return number;
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                Number number = this.doBigInt(arg0Value_);
                return number;
            }
            if (JSGuards.isForeignObject(arg0Value)) {
                this.toPrimitiveHintNumberNode = super.insert(this.toPrimitiveHintNumberNode == null ? JSToPrimitiveNode.createHintNumber() : this.toPrimitiveHintNumberNode);
                this.toNumberNode = super.insert(this.toNumberNode == null ? JSToNumberNode.create() : this.toNumberNode);
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                Number number = this.doForeignObject(arg0Value, this.toPrimitiveHintNumberNode, this.toNumberNode);
                return number;
            }
            if (JSGuards.isJavaNumber(arg0Value)) {
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                Double d = JSToNumberNode.doJavaObject(arg0Value);
                return d;
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
        Object[] s = new Object[3];
        s[0] = "doInteger";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doNull";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doString";
        if ((state_0 & 0x20) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<JavaScriptBaseNode>>();
            cached.add(Arrays.asList(this.string_stringToNumberNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[6] = s;
        s = new Object[3];
        s[0] = "doJSObject";
        if ((state_0 & 0x40) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.toPrimitiveHintNumberNode, this.toNumberNode));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[7] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.toPrimitiveHintNumberNode, this.toNumberNode));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[10] = s;
        s = new Object[3];
        s[0] = "doJavaObject";
        s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[11] = s;
        return Introspection.Provider.create(data);
    }

    public static JSToNumberNode create() {
        return new JSToNumberNodeGen();
    }

    @GeneratedBy(value=JSToNumberNode.JSToNumberUnaryNode.class)
    public static final class JSToNumberUnaryNodeGen
    extends JSToNumberNode.JSToNumberUnaryNode
    implements Introspection.Provider {
        private JSToNumberUnaryNodeGen(JavaScriptNode operand) {
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

        public static JSToNumberNode.JSToNumberUnaryNode create(JavaScriptNode operand) {
            return new JSToNumberUnaryNodeGen(operand);
        }
    }
}

