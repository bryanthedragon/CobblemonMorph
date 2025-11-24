
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.DeclareGlobalFunctionNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSGlobal;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=DeclareGlobalFunctionNode.class)
public final class DeclareGlobalFunctionNodeGen
extends DeclareGlobalFunctionNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private PropertySetNode cached_cache_;

    private DeclareGlobalFunctionNodeGen(TruffleString varName, boolean configurable, JavaScriptNode valueNode) {
        super(varName, configurable, valueNode);
    }

    @Override
    protected void executeVoid(JSDynamicObject arg0Value, Object arg1Value, PropertyDescriptor arg2Value, JSContext arg3Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg3Value.getPropertyCacheLimit() > 0 && JSGlobal.isJSGlobalObject(arg0Value) && arg2Value == null) {
                this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, this.cached_cache_);
                return;
            }
            if ((state_0 & 2) != 0) {
                this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
                return;
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value, PropertyDescriptor arg2Value, JSContext arg3Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0 && arg3Value.getPropertyCacheLimit() > 0 && JSGlobal.isJSGlobalObject(arg0Value) && arg2Value == null) {
                this.cached_cache_ = super.insert(this.makeDefineOwnPropertyCache(arg3Value));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, this.cached_cache_);
                return;
            }
            this.exclude_ = exclude |= 1;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
            return;
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
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doCached";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<List<PropertySetNode>> cached = new ArrayList<List<PropertySetNode>>();
            cached.add(Arrays.asList(this.cached_cache_));
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doUncached";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static DeclareGlobalFunctionNode create(TruffleString varName, boolean configurable, JavaScriptNode valueNode) {
        return new DeclareGlobalFunctionNodeGen(varName, configurable, valueNode);
    }
}

