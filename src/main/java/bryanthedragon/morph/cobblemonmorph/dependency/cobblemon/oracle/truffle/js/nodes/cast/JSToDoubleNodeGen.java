
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToDoubleNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToDoubleNode.class)
public final class JSToDoubleNodeGen
extends JSToDoubleNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSStringToNumberNode stringDouble_stringToNumberNode_;
    @Node.Child
    private JSToPrimitiveNode jSObject_toPrimitiveNode_;
    @Node.Child
    private JSToPrimitiveNode foreignObject_toPrimitiveNode_;

    private JSToDoubleNodeGen() {
    }

    @Override
    public Object execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return JSToDoubleNode.doInteger(arg0Value_);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return JSToDoubleNode.doBoolean(arg0Value_);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x7800) >>> 11, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0x7800) >>> 11, arg0Value);
            return JSToDoubleNode.doDouble(arg0Value_);
        }
        if ((state_0 & 8) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_);
        }
        if ((state_0 & 0x30) != 0) {
            if ((state_0 & 0x10) != 0 && JSGuards.isJSNull(arg0Value)) {
                return JSToDoubleNode.doNull(arg0Value);
            }
            if ((state_0 & 0x20) != 0 && JSGuards.isUndefined(arg0Value)) {
                return JSToDoubleNode.doUndefined(arg0Value);
            }
        }
        if ((state_0 & 0x40) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return JSToDoubleNode.doStringDouble(arg0Value_, this.stringDouble_stringToNumberNode_);
        }
        if ((state_0 & 0x80) != 0 && arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            return this.doJSObject(arg0Value_, this.jSObject_toPrimitiveNode_);
        }
        if ((state_0 & 0x100) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
        }
        if ((state_0 & 0x600) != 0) {
            if ((state_0 & 0x200) != 0 && JSGuards.isForeignObject(arg0Value)) {
                return this.doForeignObject(arg0Value, this.foreignObject_toPrimitiveNode_);
            }
            if ((state_0 & 0x400) != 0 && JSGuards.isJavaNumber(arg0Value)) {
                return JSToDoubleNode.doJavaNumber(arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    @Override
    public double executeDouble(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return JSToDoubleNode.doInteger(arg0Value_);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return JSToDoubleNode.doBoolean(arg0Value_);
        }
        if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x7800) >>> 11, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0x7800) >>> 11, arg0Value);
            return JSToDoubleNode.doDouble(arg0Value_);
        }
        if ((state_0 & 8) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_);
        }
        if ((state_0 & 0x30) != 0) {
            if ((state_0 & 0x10) != 0 && JSGuards.isJSNull(arg0Value)) {
                return JSToDoubleNode.doNull(arg0Value);
            }
            if ((state_0 & 0x20) != 0 && JSGuards.isUndefined(arg0Value)) {
                return JSToDoubleNode.doUndefined(arg0Value);
            }
        }
        if ((state_0 & 0x40) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return JSToDoubleNode.doStringDouble(arg0Value_, this.stringDouble_stringToNumberNode_);
        }
        if ((state_0 & 0x80) != 0 && arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            return this.doJSObject(arg0Value_, this.jSObject_toPrimitiveNode_);
        }
        if ((state_0 & 0x100) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
        }
        if ((state_0 & 0x600) != 0) {
            if ((state_0 & 0x200) != 0 && JSGuards.isForeignObject(arg0Value)) {
                return this.doForeignObject(arg0Value, this.foreignObject_toPrimitiveNode_);
            }
            if ((state_0 & 0x400) != 0 && JSGuards.isJavaNumber(arg0Value)) {
                return JSToDoubleNode.doJavaNumber(arg0Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private double executeAndSpecialize(Object arg0Value) {
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
                double d = JSToDoubleNode.doInteger(arg0Value_);
                return d;
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                double d = JSToDoubleNode.doBoolean(arg0Value_);
                return d;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast0 != 0) {
                double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                state_0 |= doubleCast0 << 11;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                double d = JSToDoubleNode.doDouble(arg0Value_);
                return d;
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                double d = this.doBigInt(arg0Value_);
                return d;
            }
            if (JSGuards.isJSNull(arg0Value)) {
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                double arg0Value_ = JSToDoubleNode.doNull(arg0Value);
                return arg0Value_;
            }
            if (JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                double arg0Value_ = JSToDoubleNode.doUndefined(arg0Value);
                return arg0Value_;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.stringDouble_stringToNumberNode_ = super.insert(JSStringToNumberNode.create());
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                double d = JSToDoubleNode.doStringDouble(arg0Value_, this.stringDouble_stringToNumberNode_);
                return d;
            }
            if (arg0Value instanceof JSObject) {
                JSObject arg0Value_ = (JSObject)arg0Value;
                this.jSObject_toPrimitiveNode_ = super.insert(JSToPrimitiveNode.createHintNumber());
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                double d = this.doJSObject(arg0Value_, this.jSObject_toPrimitiveNode_);
                return d;
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                double d = this.doSymbol(arg0Value_);
                return d;
            }
            if (JSGuards.isForeignObject(arg0Value)) {
                this.foreignObject_toPrimitiveNode_ = super.insert(JSToPrimitiveNode.createHintNumber());
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                double d = this.doForeignObject(arg0Value, this.foreignObject_toPrimitiveNode_);
                return d;
            }
            if (JSGuards.isJavaNumber(arg0Value)) {
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                double d = JSToDoubleNode.doJavaNumber(arg0Value);
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
        s[0] = "doBigInt";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doNull";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doStringDouble";
        if ((state_0 & 0x40) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<JavaScriptBaseNode>>();
            cached.add(Arrays.asList(this.stringDouble_stringToNumberNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[7] = s;
        s = new Object[3];
        s[0] = "doJSObject";
        if ((state_0 & 0x80) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.jSObject_toPrimitiveNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[8] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        if ((state_0 & 0x200) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.foreignObject_toPrimitiveNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[10] = s;
        s = new Object[3];
        s[0] = "doJavaNumber";
        s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[11] = s;
        return Introspection.Provider.create(data);
    }

    public static JSToDoubleNode create() {
        return new JSToDoubleNodeGen();
    }
}

