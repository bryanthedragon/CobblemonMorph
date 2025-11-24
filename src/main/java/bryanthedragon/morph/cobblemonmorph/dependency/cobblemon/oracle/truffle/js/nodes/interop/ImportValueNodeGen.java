
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.UserScriptException;
import com.oracle.truffle.js.runtime.interop.InteropFunction;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ImportValueNode.class)
public final class ImportValueNodeGen
extends ImportValueNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private TruffleString.FromJavaStringNode fromString_fromJavaStringNode_;
    @Node.Child
    private TruffleString.SwitchEncodingNode fromTruffleString_switchEncodingNode_;
    @Node.Child
    private TruffleString.FromCodePointNode fromChar_fromCodePointNode_;

    private ImportValueNodeGen() {
    }

    @Override
    public Object executeWithTarget(Object arg0Value) {
        TruffleObject arg0Value_;
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_2 = (Integer)arg0Value;
            return ImportValueNode.fromInt(arg0Value_2);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof String) {
            String arg0Value_3 = (String)arg0Value;
            return ImportValueNode.fromString(arg0Value_3, this.fromString_fromJavaStringNode_);
        }
        if ((state_0 & 4) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_4 = (TruffleString)arg0Value;
            return ImportValueNode.fromTruffleString(arg0Value_4, this.fromTruffleString_switchEncodingNode_);
        }
        if ((state_0 & 8) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_5 = (Boolean)arg0Value;
            return ImportValueNode.fromBoolean(arg0Value_5);
        }
        if ((state_0 & 0x10) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_6 = (BigInt)arg0Value;
            return ImportValueNode.fromBigInt(arg0Value_6);
        }
        if ((state_0 & 0x60) != 0 && arg0Value instanceof Long) {
            long arg0Value_7 = (Long)arg0Value;
            if ((state_0 & 0x20) != 0 && JSGuards.isLongRepresentableAsInt32(arg0Value_7)) {
                return ImportValueNode.fromLongToInt(arg0Value_7);
            }
            if ((state_0 & 0x40) != 0 && !JSGuards.isLongRepresentableAsInt32(arg0Value_7)) {
                return ImportValueNode.fromLong(arg0Value_7);
            }
        }
        if ((state_0 & 0x80) != 0 && JSTypesGen.isImplicitDouble((state_0 & 0x3C0000) >>> 18, arg0Value)) {
            double arg0Value_8 = JSTypesGen.asImplicitDouble((state_0 & 0x3C0000) >>> 18, arg0Value);
            return ImportValueNode.fromDouble(arg0Value_8);
        }
        if ((state_0 & 0x100) != 0 && arg0Value instanceof Byte) {
            byte arg0Value_9 = (Byte)arg0Value;
            return ImportValueNode.fromNumber(arg0Value_9);
        }
        if ((state_0 & 0x200) != 0 && arg0Value instanceof Short) {
            short arg0Value_10 = (Short)arg0Value;
            return ImportValueNode.fromNumber(arg0Value_10);
        }
        if ((state_0 & 0x400) != 0 && arg0Value instanceof Float) {
            float arg0Value_11 = ((Float)arg0Value).floatValue();
            return ImportValueNode.fromNumber(arg0Value_11);
        }
        if ((state_0 & 0x800) != 0 && arg0Value instanceof Character) {
            char arg0Value_12 = ((Character)arg0Value).charValue();
            return ImportValueNode.fromChar(arg0Value_12, this.fromChar_fromCodePointNode_);
        }
        if ((state_0 & 0x1000) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_13 = (JSDynamicObject)arg0Value;
            return ImportValueNode.fromDynamicObject(arg0Value_13);
        }
        if ((state_0 & 0x2000) != 0 && arg0Value instanceof InteropFunction) {
            InteropFunction arg0Value_14 = (InteropFunction)arg0Value;
            return ImportValueNode.fromInteropFunction(arg0Value_14);
        }
        if ((state_0 & 0x4000) != 0 && arg0Value instanceof JSException) {
            JSException arg0Value_15 = (JSException)arg0Value;
            return ImportValueNode.fromJSException(arg0Value_15);
        }
        if ((state_0 & 0x8000) != 0 && arg0Value instanceof UserScriptException) {
            UserScriptException arg0Value_16 = (UserScriptException)arg0Value;
            return ImportValueNode.fromException(arg0Value_16);
        }
        if ((state_0 & 0x10000) != 0 && arg0Value instanceof TruffleObject && !ImportValueNode.isSpecial(arg0Value_ = (TruffleObject)arg0Value)) {
            return ImportValueNode.fromTruffleObject(arg0Value_);
        }
        if ((state_0 & 0x20000) != 0 && ImportValueNodeGen.fallbackGuard_(state_0, arg0Value)) {
            return ImportValueNode.fallbackCase(arg0Value);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            TruffleObject truffleObject;
            int n;
            int state_0 = this.state_0_;
            if (arg0Value instanceof Integer) {
                int n2 = (Integer)arg0Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Integer bl = ImportValueNode.fromInt(n2);
                return bl;
            }
            if (arg0Value instanceof String) {
                String string = (String)arg0Value;
                this.fromString_fromJavaStringNode_ = super.insert(TruffleString.FromJavaStringNode.create());
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                TruffleString bigInt = ImportValueNode.fromString(string, this.fromString_fromJavaStringNode_);
                return bigInt;
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString truffleString = (TruffleString)arg0Value;
                this.fromTruffleString_switchEncodingNode_ = super.insert(TruffleString.SwitchEncodingNode.create());
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString2 = ImportValueNode.fromTruffleString(truffleString, this.fromTruffleString_switchEncodingNode_);
                return truffleString2;
            }
            if (arg0Value instanceof Boolean) {
                boolean bl = (Boolean)arg0Value;
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                Boolean n3 = ImportValueNode.fromBoolean(bl);
                return n3;
            }
            if (arg0Value instanceof BigInt) {
                BigInt bigInt = (BigInt)arg0Value;
                this.state_0_ = state_0 |= 0x10;
                lock.unlock();
                hasLock = false;
                BigInt n4 = ImportValueNode.fromBigInt(bigInt);
                return n4;
            }
            if (arg0Value instanceof Long) {
                long l = (Long)arg0Value;
                if (JSGuards.isLongRepresentableAsInt32(l)) {
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    Integer n5 = ImportValueNode.fromLongToInt(l);
                    return n5;
                }
                if (!JSGuards.isLongRepresentableAsInt32(l)) {
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    Long l2 = ImportValueNode.fromLong(l);
                    return l2;
                }
            }
            if ((n = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
                double arg0Value_9 = JSTypesGen.asImplicitDouble(n, arg0Value);
                state_0 |= n << 18;
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                Double d = ImportValueNode.fromDouble(arg0Value_9);
                return d;
            }
            if (arg0Value instanceof Byte) {
                byte by = (Byte)arg0Value;
                this.state_0_ = state_0 |= 0x100;
                lock.unlock();
                hasLock = false;
                Integer truffleString = ImportValueNode.fromNumber(by);
                return truffleString;
            }
            if (arg0Value instanceof Short) {
                short s = (Short)arg0Value;
                this.state_0_ = state_0 |= 0x200;
                lock.unlock();
                hasLock = false;
                Integer object = ImportValueNode.fromNumber(s);
                return object;
            }
            if (arg0Value instanceof Float) {
                float f = ((Float)arg0Value).floatValue();
                this.state_0_ = state_0 |= 0x400;
                lock.unlock();
                hasLock = false;
                Double object = ImportValueNode.fromNumber(f);
                return object;
            }
            if (arg0Value instanceof Character) {
                char c = ((Character)arg0Value).charValue();
                this.fromChar_fromCodePointNode_ = super.insert(TruffleString.FromCodePointNode.create());
                this.state_0_ = state_0 |= 0x800;
                lock.unlock();
                hasLock = false;
                TruffleString object = ImportValueNode.fromChar(c, this.fromChar_fromCodePointNode_);
                return object;
            }
            if (arg0Value instanceof JSDynamicObject) {
                JSDynamicObject jSDynamicObject = (JSDynamicObject)arg0Value;
                this.state_0_ = state_0 |= 0x1000;
                lock.unlock();
                hasLock = false;
                Object object = ImportValueNode.fromDynamicObject(jSDynamicObject);
                return object;
            }
            if (arg0Value instanceof InteropFunction) {
                InteropFunction interopFunction = (InteropFunction)arg0Value;
                this.state_0_ = state_0 |= 0x2000;
                lock.unlock();
                hasLock = false;
                Object object = ImportValueNode.fromInteropFunction(interopFunction);
                return object;
            }
            if (arg0Value instanceof JSException) {
                JSException truffleString = (JSException)arg0Value;
                this.state_0_ = state_0 |= 0x4000;
                lock.unlock();
                hasLock = false;
                Object object = ImportValueNode.fromJSException(truffleString);
                return object;
            }
            if (arg0Value instanceof UserScriptException) {
                UserScriptException userScriptException = (UserScriptException)arg0Value;
                this.state_0_ = state_0 |= 0x8000;
                lock.unlock();
                hasLock = false;
                Object object = ImportValueNode.fromException(userScriptException);
                return object;
            }
            if (arg0Value instanceof TruffleObject && !ImportValueNode.isSpecial(truffleObject = (TruffleObject)arg0Value)) {
                this.state_0_ = state_0 |= 0x10000;
                lock.unlock();
                hasLock = false;
                Object object = ImportValueNode.fromTruffleObject(truffleObject);
                return object;
            }
            this.state_0_ = state_0 |= 0x20000;
            lock.unlock();
            hasLock = false;
            TruffleString truffleString = ImportValueNode.fallbackCase(arg0Value);
            return truffleString;
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
        if ((state_0 & 0x3FFFF) == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & 0x3FFFF & (state_0 & 0x3FFFF) - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Node>> cached;
        Object[] data = new Object[19];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "fromInt";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "fromString";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Node>>();
            cached.add(Arrays.asList(this.fromString_fromJavaStringNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "fromTruffleString";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.fromTruffleString_switchEncodingNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "fromBoolean";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        s = new Object[3];
        s[0] = "fromBigInt";
        s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[5] = s;
        s = new Object[3];
        s[0] = "fromLongToInt";
        s[1] = (state_0 & 0x20) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[6] = s;
        s = new Object[3];
        s[0] = "fromLong";
        s[1] = (state_0 & 0x40) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[7] = s;
        s = new Object[3];
        s[0] = "fromDouble";
        s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[8] = s;
        s = new Object[3];
        s[0] = "fromNumber";
        s[1] = (state_0 & 0x100) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[9] = s;
        s = new Object[3];
        s[0] = "fromNumber";
        s[1] = (state_0 & 0x200) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[10] = s;
        s = new Object[3];
        s[0] = "fromNumber";
        s[1] = (state_0 & 0x400) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[11] = s;
        s = new Object[3];
        s[0] = "fromChar";
        if ((state_0 & 0x800) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.fromChar_fromCodePointNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[12] = s;
        s = new Object[3];
        s[0] = "fromDynamicObject";
        s[1] = (state_0 & 0x1000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[13] = s;
        s = new Object[3];
        s[0] = "fromInteropFunction";
        s[1] = (state_0 & 0x2000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[14] = s;
        s = new Object[3];
        s[0] = "fromJSException";
        s[1] = (state_0 & 0x4000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[15] = s;
        s = new Object[3];
        s[0] = "fromException";
        s[1] = (state_0 & 0x8000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[16] = s;
        s = new Object[3];
        s[0] = "fromTruffleObject";
        s[1] = (state_0 & 0x10000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[17] = s;
        s = new Object[3];
        s[0] = "fallbackCase";
        s[1] = (state_0 & 0x20000) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[18] = s;
        return Introspection.Provider.create(data);
    }

    private static boolean fallbackGuard_(int state_0, Object arg0Value) {
        TruffleObject arg0Value_;
        if ((state_0 & 2) == 0 && arg0Value instanceof String) {
            return false;
        }
        if ((state_0 & 4) == 0 && arg0Value instanceof TruffleString) {
            return false;
        }
        if ((state_0 & 8) == 0 && arg0Value instanceof Boolean) {
            return false;
        }
        if ((state_0 & 0x10) == 0 && arg0Value instanceof BigInt) {
            return false;
        }
        if (JSTypesGen.isImplicitDouble(arg0Value)) {
            return false;
        }
        if ((state_0 & 0x100) == 0 && arg0Value instanceof Byte) {
            return false;
        }
        if ((state_0 & 0x200) == 0 && arg0Value instanceof Short) {
            return false;
        }
        if ((state_0 & 0x400) == 0 && arg0Value instanceof Float) {
            return false;
        }
        if ((state_0 & 0x800) == 0 && arg0Value instanceof Character) {
            return false;
        }
        if ((state_0 & 0x1000) == 0 && arg0Value instanceof JSDynamicObject) {
            return false;
        }
        if ((state_0 & 0x2000) == 0 && arg0Value instanceof InteropFunction) {
            return false;
        }
        if ((state_0 & 0x4000) == 0 && arg0Value instanceof JSException) {
            return false;
        }
        if ((state_0 & 0x8000) == 0 && arg0Value instanceof UserScriptException) {
            return false;
        }
        return !(arg0Value instanceof TruffleObject) || ImportValueNode.isSpecial(arg0Value_ = (TruffleObject)arg0Value);
    }

    public static ImportValueNode create() {
        return new ImportValueNodeGen();
    }

    public static ImportValueNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=ImportValueNode.class)
    @DenyReplace
    private static final class Uncached
    extends ImportValueNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object executeWithTarget(Object arg0Value) {
            TruffleObject arg0Value_;
            if (arg0Value instanceof Integer) {
                int arg0Value_2 = (Integer)arg0Value;
                return ImportValueNode.fromInt(arg0Value_2);
            }
            if (arg0Value instanceof String) {
                String arg0Value_3 = (String)arg0Value;
                return ImportValueNode.fromString(arg0Value_3, TruffleString.FromJavaStringNode.getUncached());
            }
            if (arg0Value instanceof TruffleString) {
                TruffleString arg0Value_4 = (TruffleString)arg0Value;
                return ImportValueNode.fromTruffleString(arg0Value_4, TruffleString.SwitchEncodingNode.getUncached());
            }
            if (arg0Value instanceof Boolean) {
                boolean arg0Value_5 = (Boolean)arg0Value;
                return ImportValueNode.fromBoolean(arg0Value_5);
            }
            if (arg0Value instanceof BigInt) {
                BigInt arg0Value_6 = (BigInt)arg0Value;
                return ImportValueNode.fromBigInt(arg0Value_6);
            }
            if (arg0Value instanceof Long) {
                long arg0Value_7 = (Long)arg0Value;
                if (JSGuards.isLongRepresentableAsInt32(arg0Value_7)) {
                    return ImportValueNode.fromLongToInt(arg0Value_7);
                }
                if (!JSGuards.isLongRepresentableAsInt32(arg0Value_7)) {
                    return ImportValueNode.fromLong(arg0Value_7);
                }
            }
            if (JSTypesGen.isImplicitDouble(arg0Value)) {
                double arg0Value_8 = JSTypesGen.asImplicitDouble(arg0Value);
                return ImportValueNode.fromDouble(arg0Value_8);
            }
            if (arg0Value instanceof Byte) {
                byte arg0Value_9 = (Byte)arg0Value;
                return ImportValueNode.fromNumber(arg0Value_9);
            }
            if (arg0Value instanceof Short) {
                short arg0Value_10 = (Short)arg0Value;
                return ImportValueNode.fromNumber(arg0Value_10);
            }
            if (arg0Value instanceof Float) {
                float arg0Value_11 = ((Float)arg0Value).floatValue();
                return ImportValueNode.fromNumber(arg0Value_11);
            }
            if (arg0Value instanceof Character) {
                char arg0Value_12 = ((Character)arg0Value).charValue();
                return ImportValueNode.fromChar(arg0Value_12, TruffleString.FromCodePointNode.getUncached());
            }
            if (arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_13 = (JSDynamicObject)arg0Value;
                return ImportValueNode.fromDynamicObject(arg0Value_13);
            }
            if (arg0Value instanceof InteropFunction) {
                InteropFunction arg0Value_14 = (InteropFunction)arg0Value;
                return ImportValueNode.fromInteropFunction(arg0Value_14);
            }
            if (arg0Value instanceof JSException) {
                JSException arg0Value_15 = (JSException)arg0Value;
                return ImportValueNode.fromJSException(arg0Value_15);
            }
            if (arg0Value instanceof UserScriptException) {
                UserScriptException arg0Value_16 = (UserScriptException)arg0Value;
                return ImportValueNode.fromException(arg0Value_16);
            }
            if (arg0Value instanceof TruffleObject && !ImportValueNode.isSpecial(arg0Value_ = (TruffleObject)arg0Value)) {
                return ImportValueNode.fromTruffleObject(arg0Value_);
            }
            return ImportValueNode.fallbackCase(arg0Value);
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

