/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.access.JSProxyPropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSProxyPropertyGetNode.class)
public final class JSProxyPropertyGetNodeGen
extends JSProxyPropertyGetNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private GenericData generic_cache;

    private JSProxyPropertyGetNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public Object executeWithReceiver(Object arg0Value, Object arg1Value, Object arg2Value, Object arg3Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            GenericData s0_ = this.generic_cache;
            if (s0_ != null) {
                return this.doGeneric(arg0Value_, arg1Value, arg2Value, arg3Value, s0_.toPropertyKeyNode_, s0_.hasTrap_, s0_.targetClassProfile_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
    }

    private Object executeAndSpecialize(Object arg0Value, Object arg1Value, Object arg2Value, Object arg3Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                GenericData s0_ = super.insert(new GenericData());
                s0_.toPropertyKeyNode_ = s0_.insertAccessor(JSToPropertyKeyNode.create());
                s0_.hasTrap_ = ConditionProfile.createBinaryProfile();
                s0_.targetClassProfile_ = JSClassProfile.create();
                VarHandle.storeStoreFence();
                this.generic_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.doGeneric(arg0Value_, arg1Value, arg2Value, arg3Value, s0_.toPropertyKeyNode_, s0_.hasTrap_, s0_.targetClassProfile_);
                return object;
            }
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
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
            ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
            GenericData s0_ = this.generic_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.toPropertyKeyNode_, s0_.hasTrap_, s0_.targetClassProfile_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static JSProxyPropertyGetNode create(JSContext context) {
        return new JSProxyPropertyGetNodeGen(context);
    }

    @GeneratedBy(value=JSProxyPropertyGetNode.class)
    private static final class GenericData
    extends Node {
        @Node.Child
        JSToPropertyKeyNode toPropertyKeyNode_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile hasTrap_;
        @CompilerDirectives.CompilationFinal
        JSClassProfile targetClassProfile_;

        GenericData() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }
}

