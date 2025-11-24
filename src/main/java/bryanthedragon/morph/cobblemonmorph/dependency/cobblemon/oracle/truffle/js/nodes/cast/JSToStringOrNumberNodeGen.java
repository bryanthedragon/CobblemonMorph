
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
import com.oracle.truffle.js.nodes.cast.JSToDoubleNode;
import com.oracle.truffle.js.nodes.cast.JSToStringOrNumberNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToStringOrNumberNode.class)
public final class JSToStringOrNumberNodeGen
extends JSToStringOrNumberNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSToDoubleNode jSObject_toDoubleNode_;

    private JSToStringOrNumberNodeGen() {
    }

    @Override
    public Object execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return this.doInteger(arg0Value_);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            return this.doSafeInteger(arg0Value_);
        }
        if ((state_0 & 4) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return this.doBoolean(arg0Value_);
        }
        if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C00) >>> 10, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0x3C00) >>> 10, arg0Value);
            return this.doDouble(arg0Value_);
        }
        if ((state_0 & 0x10) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.doString(arg0Value_);
        }
        if ((state_0 & 0x20) != 0 && arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            return this.doJSObject(arg0Value_, this.jSObject_toDoubleNode_);
        }
        if ((state_0 & 0x40) != 0 && JSGuards.isJSNull(arg0Value)) {
            return this.doNull(arg0Value);
        }
        if ((state_0 & 0x80) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
        }
        if ((state_0 & 0x100) != 0 && JSGuards.isUndefined(arg0Value)) {
            return this.doUndefined(arg0Value);
        }
        if ((state_0 & 0x200) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return JSToStringOrNumberNode.doBigInt(arg0Value_);
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
                Integer n = this.doInteger(arg0Value_);
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
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Integer n = this.doBoolean(arg0Value_);
                return n;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast0 != 0) {
                double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                state_0 |= doubleCast0 << 10;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                Double d = this.doDouble(arg0Value_);
                return d;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.doString(arg0Value_);
                return truffleString;
            }
            if (arg0Value instanceof JSObject) {
                JSObject arg0Value_ = (JSObject)arg0Value;
                this.jSObject_toDoubleNode_ = super.insert(JSToDoubleNode.create());
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                Double d = this.doJSObject(arg0Value_, this.jSObject_toDoubleNode_);
                return d;
            }
            if (JSGuards.isJSNull(arg0Value)) {
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                Integer arg0Value_ = this.doNull(arg0Value);
                return arg0Value_;
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                Object object = this.doSymbol(arg0Value_);
                return object;
            }
            if (JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                Double arg0Value_ = this.doUndefined(arg0Value);
                return arg0Value_;
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                BigInt bigInt = JSToStringOrNumberNode.doBigInt(arg0Value_);
                return bigInt;
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
        if ((state_0 & 0x3FF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x3FF & (state_0 & 0x3FF) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[11];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doInteger";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doString";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doJSObject";
        if ((state_0 & 0x20) != 0) {
            s[1] = (byte)1;
            ArrayList<List<JSToDoubleNode>> cached = new ArrayList<List<JSToDoubleNode>>();
            cached.add(Arrays.asList(this.jSObject_toDoubleNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[6] = s;
        s = new Object[3];
        s[0] = "doNull";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[10] = s;
        return Introspection.Provider.create(data);
    }

    public static JSToStringOrNumberNode create() {
        return new JSToStringOrNumberNodeGen();
    }
}

