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
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.access.IsJSClassNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.IsRegExpNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=IsRegExpNode.class)
public final class IsRegExpNodeGen
extends IsRegExpNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private IsObjectData isObject_cache;

    private IsRegExpNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public boolean executeBoolean(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            IsObjectData s0_ = this.isObject_cache;
            if (s0_ != null) {
                return this.doIsObject(arg0Value_, s0_.isObjectNode_, s0_.toBooleanNode_, s0_.isJSRegExpNode_, s0_.hasMatchSymbol_);
            }
        }
        if ((state_0 & 2) != 0 && IsRegExpNodeGen.fallbackGuard_(state_0, arg0Value)) {
            return this.doNonObject(arg0Value);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeAndSpecialize(Object arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (arg0Value instanceof JSDynamicObject) {
                JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                IsObjectData s0_ = super.insert(new IsObjectData());
                s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                s0_.toBooleanNode_ = s0_.insertAccessor(JSToBooleanNode.create());
                s0_.isJSRegExpNode_ = s0_.insertAccessor(IsRegExpNode.createIsJSRegExpNode());
                s0_.hasMatchSymbol_ = ConditionProfile.createBinaryProfile();
                VarHandle.storeStoreFence();
                this.isObject_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = this.doIsObject(arg0Value_, s0_.isObjectNode_, s0_.toBooleanNode_, s0_.isJSRegExpNode_, s0_.hasMatchSymbol_);
                return bl;
            }
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            boolean bl = this.doNonObject(arg0Value);
            return bl;
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
        Object[] s = new Object[3];
        s[0] = "doIsObject";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
            IsObjectData s0_ = this.isObject_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.isObjectNode_, s0_.toBooleanNode_, s0_.isJSRegExpNode_, s0_.hasMatchSymbol_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doNonObject";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    private static boolean fallbackGuard_(int state_0, Object arg0Value) {
        return (state_0 & 1) != 0 || !(arg0Value instanceof JSDynamicObject);
    }

    public static IsRegExpNode create(JSContext context) {
        return new IsRegExpNodeGen(context);
    }

    @GeneratedBy(value=IsRegExpNode.class)
    private static final class IsObjectData
    extends Node {
        @Node.Child
        IsJSObjectNode isObjectNode_;
        @Node.Child
        JSToBooleanNode toBooleanNode_;
        @Node.Child
        IsJSClassNode isJSRegExpNode_;
        @CompilerDirectives.CompilationFinal
        ConditionProfile hasMatchSymbol_;

        IsObjectData() {
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

