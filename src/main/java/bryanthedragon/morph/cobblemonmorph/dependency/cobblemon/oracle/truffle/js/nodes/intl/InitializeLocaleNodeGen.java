
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.intl.InitializeLocaleNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=InitializeLocaleNode.class)
public final class InitializeLocaleNodeGen
extends InitializeLocaleNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private JSToStringNode initializeLocaleUsingObject_toStringNode_;

    private InitializeLocaleNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public JSDynamicObject executeInit(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg1Value instanceof TruffleString) {
                TruffleString arg1Value_ = (TruffleString)arg1Value;
                return this.initializeLocaleUsingString(arg0Value, arg1Value_, arg2Value);
            }
            if ((state_0 & 6) != 0 && arg1Value instanceof JSDynamicObject) {
                JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                if ((state_0 & 2) != 0 && JSGuards.isJSLocale(arg1Value_)) {
                    return this.initializeLocaleUsingLocale(arg0Value, arg1Value_, arg2Value);
                }
                if ((state_0 & 4) != 0 && JSGuards.isJSObject(arg1Value_) && !JSGuards.isJSLocale(arg1Value_)) {
                    return this.initializeLocaleUsingObject(arg0Value, arg1Value_, arg2Value, this.initializeLocaleUsingObject_toStringNode_);
                }
            }
            if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arg1Value) && !JSGuards.isString(arg1Value)) {
                return this.initializeLocaleOther(arg0Value, arg1Value, arg2Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
    }

    private JSDynamicObject executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (arg1Value instanceof TruffleString) {
                TruffleString arg1Value_ = (TruffleString)arg1Value;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.initializeLocaleUsingString(arg0Value, arg1Value_, arg2Value);
                return jSDynamicObject;
            }
            if (arg1Value instanceof JSDynamicObject) {
                JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                if (JSGuards.isJSLocale(arg1Value_)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.initializeLocaleUsingLocale(arg0Value, arg1Value_, arg2Value);
                    return jSDynamicObject;
                }
                if (JSGuards.isJSObject(arg1Value_) && !JSGuards.isJSLocale(arg1Value_)) {
                    this.initializeLocaleUsingObject_toStringNode_ = super.insert(JSToStringNode.create());
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.initializeLocaleUsingObject(arg0Value, arg1Value_, arg2Value, this.initializeLocaleUsingObject_toStringNode_);
                    return jSDynamicObject;
                }
            }
            if (!JSGuards.isJSObject(arg1Value) && !JSGuards.isString(arg1Value)) {
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.initializeLocaleOther(arg0Value, arg1Value, arg2Value);
                return jSDynamicObject;
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
        Object[] data = new Object[5];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "initializeLocaleUsingString";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "initializeLocaleUsingLocale";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "initializeLocaleUsingObject";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<List<JSToStringNode>> cached = new ArrayList<List<JSToStringNode>>();
            cached.add(Arrays.asList(this.initializeLocaleUsingObject_toStringNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        s = new Object[3];
        s[0] = "initializeLocaleOther";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        return Introspection.Provider.create(data);
    }

    public static InitializeLocaleNode create(JSContext context) {
        return new InitializeLocaleNodeGen(context);
    }
}

