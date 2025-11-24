
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
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSToIntegerAsIntNode.class)
public final class JSToIntegerAsIntNodeGen
extends JSToIntegerAsIntNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSToIntegerAsIntNode string_nestedToIntegerNode_;
    @Node.Child
    private JSStringToNumberNode string_stringToNumberNode_;

    private JSToIntegerAsIntNodeGen() {
    }

    @Override
    public int executeInt(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return JSToIntegerAsIntNode.doInteger(arg0Value_);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return JSToIntegerAsIntNode.doBoolean(arg0Value_);
        }
        if ((state_0 & 0xC) != 0 && arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            if ((state_0 & 4) != 0 && JSGuards.isLongRepresentableAsInt32(arg0Value_.longValue())) {
                return JSToIntegerAsIntNode.doSafeIntegerInt32Range(arg0Value_);
            }
            if ((state_0 & 8) != 0 && !JSGuards.isLongRepresentableAsInt32(arg0Value_.longValue())) {
                return JSToIntegerAsIntNode.doSafeIntegerOther(arg0Value_);
            }
        }
        if ((state_0 & 0x30) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x1E000) >>> 13, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 0x1E000) >>> 13, arg0Value);
            if ((state_0 & 0x10) != 0 && JSToIntegerAsIntNode.inInt32Range(arg0Value_)) {
                return JSToIntegerAsIntNode.doDoubleInt32Range(arg0Value_);
            }
            if ((state_0 & 0x20) != 0 && !JSToIntegerAsIntNode.inInt32Range(arg0Value_)) {
                return JSToIntegerAsIntNode.doDoubleOther(arg0Value_);
            }
        }
        if ((state_0 & 0xC0) != 0) {
            if ((state_0 & 0x40) != 0 && JSGuards.isUndefined(arg0Value)) {
                return JSToIntegerAsIntNode.doUndefined(arg0Value);
            }
            if ((state_0 & 0x80) != 0 && JSGuards.isJSNull(arg0Value)) {
                return JSToIntegerAsIntNode.doNull(arg0Value);
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
            return this.doString(arg0Value_, this.string_nestedToIntegerNode_, this.string_stringToNumberNode_);
        }
        if ((state_0 & 0x800) != 0 && arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            return this.doJSObject(arg0Value_);
        }
        if ((state_0 & 0x1000) != 0 && JSGuards.isForeignObject(arg0Value)) {
            return this.doForeignObject(arg0Value);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private int executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int doubleCast0;
            int state_0 = this.state_0_;
            if (arg0Value instanceof Integer) {
                int arg0Value_ = (Integer)arg0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = JSToIntegerAsIntNode.doInteger(arg0Value_);
                return n;
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_ = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                int n = JSToIntegerAsIntNode.doBoolean(arg0Value_);
                return n;
            }
            if (arg0Value instanceof SafeInteger) {
                SafeInteger arg0Value_ = (SafeInteger)arg0Value;
                if (JSGuards.isLongRepresentableAsInt32(arg0Value_.longValue())) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    int n = JSToIntegerAsIntNode.doSafeIntegerInt32Range(arg0Value_);
                    return n;
                }
                if (!JSGuards.isLongRepresentableAsInt32(arg0Value_.longValue())) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    int n = JSToIntegerAsIntNode.doSafeIntegerOther(arg0Value_);
                    return n;
                }
            }
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
                double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                if (JSToIntegerAsIntNode.inInt32Range(arg0Value_)) {
                    state_0 |= doubleCast0 << 13;
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    int n = JSToIntegerAsIntNode.doDoubleInt32Range(arg0Value_);
                    return n;
                }
                if (!JSToIntegerAsIntNode.inInt32Range(arg0Value_)) {
                    state_0 |= doubleCast0 << 13;
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    int n = JSToIntegerAsIntNode.doDoubleOther(arg0Value_);
                    return n;
                }
            }
            if (JSGuards.isUndefined(arg0Value)) {
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                doubleCast0 = JSToIntegerAsIntNode.doUndefined(arg0Value);
                return doubleCast0;
            }
            if (JSGuards.isJSNull(arg0Value)) {
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                doubleCast0 = JSToIntegerAsIntNode.doNull(arg0Value);
                return doubleCast0;
            }
            if (arg0Value instanceof Symbol) {
                Symbol arg0Value_ = (Symbol)arg0Value;
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                int n = this.doSymbol(arg0Value_);
                return n;
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_ = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                int n = this.doBigInt(arg0Value_);
                return n;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_ = (TruffleString)arg0Value;
                this.string_nestedToIntegerNode_ = super.insert(JSToIntegerAsIntNode.create());
                this.string_stringToNumberNode_ = super.insert(JSStringToNumberNode.create());
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                int n = this.doString(arg0Value_, this.string_nestedToIntegerNode_, this.string_stringToNumberNode_);
                return n;
            }
            if (arg0Value instanceof JSObject) {
                JSObject arg0Value_ = (JSObject)arg0Value;
                this.state_0_ = state_0 |= 0x800;
                lock.unlock();
                hasLock = false;
                int n = this.doJSObject(arg0Value_);
                return n;
            }
            if (JSGuards.isForeignObject(arg0Value)) {
                this.state_0_ = state_0 |= 0x1000;
                lock.unlock();
                hasLock = false;
                int n = this.doForeignObject(arg0Value);
                return n;
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
        if ((state_0 & 0x1FFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x1FFF & (state_0 & 0x1FFF) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[14];
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
        s[0] = "doSafeIntegerInt32Range";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doSafeIntegerOther";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doDoubleInt32Range";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doDoubleOther";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doUndefined";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "doNull";
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
            ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
            cached.add(Arrays.asList(this.string_nestedToIntegerNode_, this.string_stringToNumberNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[11] = s;
        s = new Object[3];
        s[0] = "doJSObject";
        s[1] = (state_0 & 0x800) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[12] = s;
        s = new Object[3];
        s[0] = "doForeignObject";
        s[1] = (state_0 & 0x1000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[13] = s;
        return Introspection.Provider.create(data);
    }

    public static JSToIntegerAsIntNode create() {
        return new JSToIntegerAsIntNodeGen();
    }
}

