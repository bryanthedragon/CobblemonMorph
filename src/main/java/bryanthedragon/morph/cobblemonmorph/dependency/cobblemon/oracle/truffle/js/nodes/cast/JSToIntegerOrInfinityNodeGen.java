
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
import com.oracle.truffle.js.nodes.cast.JSToIntegerOrInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToIntegerOrInfinityNode.class)
public final class JSToIntegerOrInfinityNodeGen
extends JSToIntegerOrInfinityNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSToIntegerOrInfinityNode recToIntOrInf;
    @Node.Child
    private JSStringToNumberNode string_stringToNumberNode_;
    @Node.Child
    private JSToNumberNode jSOrForeignObject_toNumberNode_;

    private JSToIntegerOrInfinityNodeGen() {
    }

    @Override
    public Object execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return JSToIntegerOrInfinityNode.doInteger(arg0Value_);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            return JSToIntegerOrInfinityNode.doLong(arg0Value_);
        }
        if ((state_0 & 4) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return JSToIntegerOrInfinityNode.doBoolean(arg0Value_);
        }
        if ((state_0 & 8) != 0 && arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            return JSToIntegerOrInfinityNode.doSafeInteger(arg0Value_);
        }
        if ((state_0 & 0x30) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF000) >>> 12, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0xF000) >>> 12, arg0Value);
            if ((state_0 & 0x10) != 0 && JSToIntegerOrInfinityNode.shouldConvertToZero(arg0Value_)) {
                return JSToIntegerOrInfinityNode.doDoubleNegativeZero(arg0Value_);
            }
            if ((state_0 & 0x20) != 0 && !JSToIntegerOrInfinityNode.shouldConvertToZero(arg0Value_)) {
                return this.doDouble(arg0Value_);
            }
        }
        if ((state_0 & 0xC0) != 0) {
            if ((state_0 & 0x40) != 0 && JSGuards.isJSNull(arg0Value)) {
                return JSToIntegerOrInfinityNode.doNull(arg0Value);
            }
            if ((state_0 & 0x80) != 0 && JSGuards.isUndefined(arg0Value)) {
                return JSToIntegerOrInfinityNode.doUndefined(arg0Value);
            }
        }
        if ((state_0 & 0x100) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
        }
        if ((state_0 & 0x200) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_);
        }
        if ((state_0 & 0x400) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.doString(arg0Value_, this.recToIntOrInf, this.string_stringToNumberNode_);
        }
        if ((state_0 & 0x800) != 0 && (JSGuards.isForeignObject(arg0Value) || JSGuards.isJSObject(arg0Value))) {
            return this.doJSOrForeignObject(arg0Value, this.recToIntOrInf, this.jSOrForeignObject_toNumberNode_);
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
                Integer n = JSToIntegerOrInfinityNode.doInteger(arg0Value_);
                return n;
            }
            if (arg0Value instanceof Long) {
                long arg0Value_ = (Long)arg0Value;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Long l = JSToIntegerOrInfinityNode.doLong(arg0Value_);
                return l;
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Integer n = JSToIntegerOrInfinityNode.doBoolean(arg0Value_);
                return n;
            }
            if (arg0Value instanceof SafeInteger) {
                SafeInteger arg0Value_ = (SafeInteger)arg0Value;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                SafeInteger safeInteger = JSToIntegerOrInfinityNode.doSafeInteger(arg0Value_);
                return safeInteger;
            }
            int doubleCast02 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast02 != 0) {
                double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast02, arg0Value);
                if (JSToIntegerOrInfinityNode.shouldConvertToZero(arg0Value_)) {
                    state_0 |= doubleCast02 << 12;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    Integer n = JSToIntegerOrInfinityNode.doDoubleNegativeZero(arg0Value_);
                    return n;
                }
                if (!JSToIntegerOrInfinityNode.shouldConvertToZero(arg0Value_)) {
                    state_0 |= doubleCast02 << 12;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    Double d = this.doDouble(arg0Value_);
                    return d;
                }
            }
            if (JSGuards.isJSNull(arg0Value)) {
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                Integer doubleCast02 = JSToIntegerOrInfinityNode.doNull(arg0Value);
                return doubleCast02;
            }
            if (JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                Integer doubleCast02 = JSToIntegerOrInfinityNode.doUndefined(arg0Value);
                return doubleCast02;
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                Number number = this.doSymbol(arg0Value_);
                return number;
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                Number number = this.doBigInt(arg0Value_);
                return number;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.recToIntOrInf = super.insert(this.recToIntOrInf == null ? JSToIntegerOrInfinityNode.create() : this.recToIntOrInf);
                this.string_stringToNumberNode_ = super.insert(JSStringToNumberNode.create());
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                Number number = this.doString(arg0Value_, this.recToIntOrInf, this.string_stringToNumberNode_);
                return number;
            }
            if (JSGuards.isForeignObject(arg0Value) || JSGuards.isJSObject(arg0Value)) {
                this.recToIntOrInf = super.insert(this.recToIntOrInf == null ? JSToIntegerOrInfinityNode.create() : this.recToIntOrInf);
                this.jSOrForeignObject_toNumberNode_ = super.insert(JSToNumberNode.create());
                this.state_0_ = state_0 |= 0x800;
                lock.unlock();
                hasLock = false;
                Number number = this.doJSOrForeignObject(arg0Value, this.recToIntOrInf, this.jSOrForeignObject_toNumberNode_);
                return number;
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
        if ((state_0 & 0xFFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0xFFF & (state_0 & 0xFFF) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<JavaScriptBaseNode>> cached;
        Object[] data = new Object[13];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doInteger";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doLong";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doDoubleNegativeZero";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doNull";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doSymbol";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[10] = s;
        s = new Object[3];
        s[0] = "doString";
        if ((state_0 & 0x400) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<JavaScriptBaseNode>>();
            cached.add(Arrays.asList(this.recToIntOrInf, this.string_stringToNumberNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[11] = s;
        s = new Object[3];
        s[0] = "doJSOrForeignObject";
        if ((state_0 & 0x800) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.recToIntOrInf, this.jSOrForeignObject_toNumberNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[12] = s;
        return Introspection.Provider.create(data);
    }

    public static JSToIntegerOrInfinityNode create() {
        return new JSToIntegerOrInfinityNodeGen();
    }
}

