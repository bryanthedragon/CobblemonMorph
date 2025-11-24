
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.access.JSProxyHasPropertyNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSProxyHasPropertyNode.class)
public final class JSProxyHasPropertyNodeGen
extends JSProxyHasPropertyNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile trapFunProfile_;

    private JSProxyHasPropertyNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public boolean executeWithTargetAndKeyBoolean(Object arg0Value, Object arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            return this.doGeneric(arg0Value_, arg1Value, this.trapFunProfile_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    private boolean executeAndSpecialize(Object arg0Value, Object arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                this.trapFunProfile_ = ConditionProfile.createBinaryProfile();
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doGeneric(arg0Value_, arg1Value, this.trapFunProfile_);
                return bl;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[2];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doGeneric";
        if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<List<ConditionProfile>> cached = new ArrayList<List<ConditionProfile>>();
            cached.add(Arrays.asList(this.trapFunProfile_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static JSProxyHasPropertyNode create(JSContext context) {
        return new JSProxyHasPropertyNodeGen(context);
    }
}

