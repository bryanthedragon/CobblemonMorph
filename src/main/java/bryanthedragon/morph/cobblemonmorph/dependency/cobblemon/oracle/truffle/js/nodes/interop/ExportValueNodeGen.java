
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ExportValueNode.class)
public final class ExportValueNodeGen
extends ExportValueNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private int state_0_;
    @CompilerDirectives.CompilationFinal
    private int exclude_;

    private ExportValueNodeGen() {
    }

    @Override
    public Object execute(Object arg0Value, Object arg1Value, boolean arg2Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 0xFFFF) != 0) {
            Object arg0Value_;
            if ((state_0 & 0x1F) != 0 && arg0Value instanceof JSFunctionObject) {
                arg0Value_ = (JSFunctionObject)arg0Value;
                if (!((state_0 & 1) == 0 || arg2Value || this.isInteropCompletePromises() && JSFunction.isAsyncFunction((JSDynamicObject)arg0Value_))) {
                    return ExportValueNode.doFunctionNoBind((JSFunctionObject)arg0Value_, arg1Value, arg2Value);
                }
                if ((state_0 & 2) != 0 && arg2Value && JSGuards.isUndefined(arg1Value) && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction((JSDynamicObject)arg0Value_))) {
                    return ExportValueNode.doFunctionUndefinedThis((JSFunctionObject)arg0Value_, arg1Value, arg2Value);
                }
                if (!((state_0 & 4) == 0 || !arg2Value || JSGuards.isUndefined(arg1Value) || JSGuards.isBoundJSFunction(arg0Value_) || this.isInteropCompletePromises() && JSFunction.isAsyncFunction((JSDynamicObject)arg0Value_))) {
                    return ExportValueNode.doBindUnboundFunction((JSFunctionObject)arg0Value_, arg1Value, arg2Value);
                }
                if ((state_0 & 8) != 0 && arg2Value && JSGuards.isBoundJSFunction(arg0Value_) && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction((JSDynamicObject)arg0Value_))) {
                    return ExportValueNode.doBoundFunction((JSFunctionObject)arg0Value_, arg1Value, arg2Value);
                }
                if ((state_0 & 0x10) != 0) {
                    assert (this.isInteropCompletePromises());
                    if (JSFunction.isAsyncFunction((JSDynamicObject)arg0Value_)) {
                        return ExportValueNode.doAsyncFunction((JSFunctionObject)arg0Value_, arg1Value, arg2Value);
                    }
                }
            }
            if ((state_0 & 0x20) != 0 && arg0Value instanceof SafeInteger) {
                arg0Value_ = (SafeInteger)arg0Value;
                return ExportValueNode.doSafeInteger((SafeInteger)arg0Value_, arg1Value, arg2Value);
            }
            if ((state_0 & 0x40) != 0 && arg0Value instanceof JSDynamicObject && !JSGuards.isJSFunction(arg0Value_ = (JSDynamicObject)arg0Value)) {
                return ExportValueNode.doObject((JSDynamicObject)arg0Value_, arg1Value, arg2Value);
            }
            if ((state_0 & 0x80) != 0 && arg0Value instanceof Integer) {
                int arg0Value_2 = (Integer)arg0Value;
                return ExportValueNode.doInt(arg0Value_2, arg1Value, arg2Value);
            }
            if ((state_0 & 0x100) != 0 && arg0Value instanceof Long) {
                long arg0Value_3 = (Long)arg0Value;
                return ExportValueNode.doLong(arg0Value_3, arg1Value, arg2Value);
            }
            if ((state_0 & 0x200) != 0 && arg0Value instanceof Float) {
                float arg0Value_4 = ((Float)arg0Value).floatValue();
                return Float.valueOf(ExportValueNode.doFloat(arg0Value_4, arg1Value, arg2Value));
            }
            if ((state_0 & 0x400) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0xF0000) >>> 16, arg0Value)) {
                double arg0Value_5 = JSTypesGen.asImplicitDouble((state_0 & 0xF0000) >>> 16, arg0Value);
                return ExportValueNode.doDouble(arg0Value_5, arg1Value, arg2Value);
            }
            if ((state_0 & 0x800) != 0 && arg0Value instanceof Boolean) {
                boolean arg0Value_6 = (Boolean)arg0Value;
                return ExportValueNode.doBoolean(arg0Value_6, arg1Value, arg2Value);
            }
            if ((state_0 & 0x1000) != 0 && arg0Value instanceof BigInt) {
                arg0Value_ = (BigInt)arg0Value;
                return ExportValueNode.doBigInt((BigInt)arg0Value_, arg1Value, arg2Value);
            }
            if ((state_0 & 0x2000) != 0 && arg0Value instanceof TruffleString) {
                arg0Value_ = (TruffleString)arg0Value;
                return ExportValueNode.doString((TruffleString)arg0Value_, arg1Value, arg2Value);
            }
            if ((state_0 & 0x4000) != 0 && arg0Value instanceof TruffleObject && !JSGuards.isJSFunction(arg0Value_ = (TruffleObject)arg0Value)) {
                return ExportValueNode.doTruffleObject((TruffleObject)arg0Value_, arg1Value, arg2Value);
            }
            if (!((state_0 & 0x8000) == 0 || JSGuards.isTruffleObject(arg1Value) || JSGuards.isString(arg1Value) || JSGuards.isBoolean(arg1Value) || JSGuards.isNumberDouble(arg1Value) || JSGuards.isNumberLong(arg1Value) || JSGuards.isNumberInteger(arg1Value))) {
                return ExportValueNode.doOther(arg0Value, arg1Value, arg2Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
    }

    private Object executeAndSpecialize(Object arg0Value, Object arg1Value, boolean arg2Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            TruffleObject arg0Value_;
            TruffleObject arg0Value_2;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof JSFunctionObject) {
                arg0Value_2 = (JSFunctionObject)arg0Value;
                if (!(arg2Value || this.isInteropCompletePromises() && JSFunction.isAsyncFunction(arg0Value_2))) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = ExportValueNode.doFunctionNoBind(arg0Value_2, arg1Value, arg2Value);
                    return jSDynamicObject;
                }
                if (arg2Value && JSGuards.isUndefined(arg1Value) && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction(arg0Value_2))) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = ExportValueNode.doFunctionUndefinedThis(arg0Value_2, arg1Value, arg2Value);
                    return jSDynamicObject;
                }
                if (!(!arg2Value || JSGuards.isUndefined(arg1Value) || JSGuards.isBoundJSFunction(arg0Value_2) || this.isInteropCompletePromises() && JSFunction.isAsyncFunction(arg0Value_2))) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    TruffleObject truffleObject = ExportValueNode.doBindUnboundFunction(arg0Value_2, arg1Value, arg2Value);
                    return truffleObject;
                }
                if (arg2Value && JSGuards.isBoundJSFunction(arg0Value_2) && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction(arg0Value_2))) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = ExportValueNode.doBoundFunction(arg0Value_2, arg1Value, arg2Value);
                    return jSDynamicObject;
                }
                if (this.isInteropCompletePromises() && JSFunction.isAsyncFunction(arg0Value_2)) {
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    TruffleObject truffleObject = ExportValueNode.doAsyncFunction(arg0Value_2, arg1Value, arg2Value);
                    return truffleObject;
                }
            }
            if (arg0Value instanceof SafeInteger) {
                arg0Value_2 = (SafeInteger)arg0Value;
                this.state_0_ = state_0 |= 0x20;
                lock.unlock();
                hasLock = false;
                Double d = ExportValueNode.doSafeInteger((SafeInteger)arg0Value_2, arg1Value, arg2Value);
                return d;
            }
            if (exclude == 0 && arg0Value instanceof JSDynamicObject && !JSGuards.isJSFunction(arg0Value_2 = (JSDynamicObject)arg0Value)) {
                this.state_0_ = state_0 |= 0x40;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = ExportValueNode.doObject(arg0Value_2, arg1Value, arg2Value);
                return jSDynamicObject;
            }
            if (arg0Value instanceof Integer) {
                int arg0Value_3 = (Integer)arg0Value;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                Integer n = ExportValueNode.doInt(arg0Value_3, arg1Value, arg2Value);
                return n;
            }
            if (arg0Value instanceof Long) {
                long arg0Value_4 = (Long)arg0Value;
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                Long l = ExportValueNode.doLong(arg0Value_4, arg1Value, arg2Value);
                return l;
            }
            if (arg0Value instanceof Float) {
                float arg0Value_5 = ((Float)arg0Value).floatValue();
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                Float f = Float.valueOf(ExportValueNode.doFloat(arg0Value_5, arg1Value, arg2Value));
                return f;
            }
            int doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value);
            if (doubleCast0 != 0) {
                double arg0Value_6 = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                state_0 |= doubleCast0 << 16;
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                Double d = ExportValueNode.doDouble(arg0Value_6, arg1Value, arg2Value);
                return d;
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_7 = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 0x800;
                lock.unlock();
                hasLock = false;
                Boolean bl = ExportValueNode.doBoolean(arg0Value_7, arg1Value, arg2Value);
                return bl;
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_8 = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 0x1000;
                lock.unlock();
                hasLock = false;
                BigInt bigInt = ExportValueNode.doBigInt(arg0Value_8, arg1Value, arg2Value);
                return bigInt;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_9 = (TruffleString)arg0Value;
                this.state_0_ = state_0 |= 0x2000;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = ExportValueNode.doString(arg0Value_9, arg1Value, arg2Value);
                return truffleString;
            }
            if (arg0Value instanceof TruffleObject && !JSGuards.isJSFunction(arg0Value_ = (TruffleObject)arg0Value)) {
                this.exclude_ = exclude |= 1;
                state_0 &= 0xFFFFFFBF;
                this.state_0_ = state_0 |= 0x4000;
                lock.unlock();
                hasLock = false;
                TruffleObject truffleObject = ExportValueNode.doTruffleObject(arg0Value_, arg1Value, arg2Value);
                return truffleObject;
            }
            if (!(JSGuards.isTruffleObject(arg1Value) || JSGuards.isString(arg1Value) || JSGuards.isBoolean(arg1Value) || JSGuards.isNumberDouble(arg1Value) || JSGuards.isNumberLong(arg1Value) || JSGuards.isNumberInteger(arg1Value))) {
                this.state_0_ = state_0 |= 0x8000;
                lock.unlock();
                hasLock = false;
                Object object = ExportValueNode.doOther(arg0Value, arg1Value, arg2Value);
                return object;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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
        if ((state_0 & 0xFFFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0xFFFF & (state_0 & 0xFFFF) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[17];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doFunctionNoBind";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "doFunctionUndefinedThis";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "doBindUnboundFunction";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "doBoundFunction";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "doAsyncFunction";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "doSafeInteger";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "doObject";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : (exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
        data[7] = s;
        s = new Object[3];
        s[0] = "doInt";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "doLong";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "doFloat";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[10] = s;
        s = new Object[3];
        s[0] = "doDouble";
        s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[11] = s;
        s = new Object[3];
        s[0] = "doBoolean";
        s[1] = (state_0 & 0x800) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[12] = s;
        s = new Object[3];
        s[0] = "doBigInt";
        s[1] = (state_0 & 0x1000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[13] = s;
        s = new Object[3];
        s[0] = "doString";
        s[1] = (state_0 & 0x2000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[14] = s;
        s = new Object[3];
        s[0] = "doTruffleObject";
        s[1] = (state_0 & 0x4000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[15] = s;
        s = new Object[3];
        s[0] = "doOther";
        s[1] = (state_0 & 0x8000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[16] = s;
        return Introspection.Provider.create(data);
    }

    public static ExportValueNode create() {
        return new ExportValueNodeGen();
    }

    public static ExportValueNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=ExportValueNode.class)
    @DenyReplace
    private static final class Uncached
    extends ExportValueNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object execute(Object arg0Value, Object arg1Value, boolean arg2Value) {
            Object arg0Value_;
            if (arg0Value instanceof JSFunctionObject) {
                arg0Value_ = (JSFunctionObject)arg0Value;
                if (!(arg2Value || this.isInteropCompletePromises() && JSFunction.isAsyncFunction((JSDynamicObject)arg0Value_))) {
                    return ExportValueNode.doFunctionNoBind((JSFunctionObject)arg0Value_, arg1Value, arg2Value);
                }
                if (arg2Value && JSGuards.isUndefined(arg1Value) && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction((JSDynamicObject)arg0Value_))) {
                    return ExportValueNode.doFunctionUndefinedThis((JSFunctionObject)arg0Value_, arg1Value, arg2Value);
                }
                if (!(!arg2Value || JSGuards.isUndefined(arg1Value) || JSGuards.isBoundJSFunction(arg0Value_) || this.isInteropCompletePromises() && JSFunction.isAsyncFunction((JSDynamicObject)arg0Value_))) {
                    return ExportValueNode.doBindUnboundFunction((JSFunctionObject)arg0Value_, arg1Value, arg2Value);
                }
                if (arg2Value && JSGuards.isBoundJSFunction(arg0Value_) && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction((JSDynamicObject)arg0Value_))) {
                    return ExportValueNode.doBoundFunction((JSFunctionObject)arg0Value_, arg1Value, arg2Value);
                }
                if (this.isInteropCompletePromises() && JSFunction.isAsyncFunction((JSDynamicObject)arg0Value_)) {
                    return ExportValueNode.doAsyncFunction((JSFunctionObject)arg0Value_, arg1Value, arg2Value);
                }
            }
            if (arg0Value instanceof SafeInteger) {
                arg0Value_ = (SafeInteger)arg0Value;
                return ExportValueNode.doSafeInteger((SafeInteger)arg0Value_, arg1Value, arg2Value);
            }
            if (arg0Value instanceof Integer) {
                int arg0Value_2 = (Integer)arg0Value;
                return ExportValueNode.doInt(arg0Value_2, arg1Value, arg2Value);
            }
            if (arg0Value instanceof Long) {
                long arg0Value_3 = (Long)arg0Value;
                return ExportValueNode.doLong(arg0Value_3, arg1Value, arg2Value);
            }
            if (arg0Value instanceof Float) {
                float arg0Value_4 = ((Float)arg0Value).floatValue();
                return Float.valueOf(ExportValueNode.doFloat(arg0Value_4, arg1Value, arg2Value));
            }
            if (JSTypesGen.isImplicitDouble(arg0Value)) {
                double arg0Value_5 = JSTypesGen.asImplicitDouble(arg0Value);
                return ExportValueNode.doDouble(arg0Value_5, arg1Value, arg2Value);
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_6 = (Boolean)arg0Value;
                return ExportValueNode.doBoolean(arg0Value_6, arg1Value, arg2Value);
            }
            if (arg0Value instanceof BigInt) {
                arg0Value_ = (BigInt)arg0Value;
                return ExportValueNode.doBigInt((BigInt)arg0Value_, arg1Value, arg2Value);
            }
            if (arg0Value instanceof TruffleString) {
                arg0Value_ = (TruffleString)arg0Value;
                return ExportValueNode.doString((TruffleString)arg0Value_, arg1Value, arg2Value);
            }
            if (arg0Value instanceof TruffleObject && !JSGuards.isJSFunction(arg0Value_ = (TruffleObject)arg0Value)) {
                return ExportValueNode.doTruffleObject((TruffleObject)arg0Value_, arg1Value, arg2Value);
            }
            if (!(JSGuards.isTruffleObject(arg1Value) || JSGuards.isString(arg1Value) || JSGuards.isBoolean(arg1Value) || JSGuards.isNumberDouble(arg1Value) || JSGuards.isNumberLong(arg1Value) || JSGuards.isNumberInteger(arg1Value))) {
                return ExportValueNode.doOther(arg0Value, arg1Value, arg2Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }
    }
}

