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
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=PropertyGetNode.class)
public final class PropertyGetNodeFactory {

    @GeneratedBy(value=PropertyGetNode.GetPropertyFromJSObjectNode.class)
    static final class GetPropertyFromJSObjectNodeGen
    extends PropertyGetNode.GetPropertyFromJSObjectNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @CompilerDirectives.CompilationFinal
        private JSObjectCachedData jSObjectCached_cache;
        @Node.Child
        private JSHasPropertyNode required_hasPropertyNode_;
        @CompilerDirectives.CompilationFinal
        private JSClassProfile required_classProfile_;

        private GetPropertyFromJSObjectNodeGen(PropertyGetNode root) {
            super(root);
        }

        @Override
        @ExplodeLoop
        public Object executeWithJSObject(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value, PropertyGetNode arg3Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    assert (!this.isGlobal());
                    JSObjectCachedData s0_ = this.jSObjectCached_cache;
                    while (s0_ != null) {
                        if (s0_.cachedClass_ == this.getJSClass(arg0Value)) {
                            return this.doJSObjectCached(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedClass_);
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    assert (!this.isGlobal());
                    return this.doJSObjectDirect(arg0Value, arg1Value, arg2Value, arg3Value);
                }
                if ((state_0 & 4) != 0) {
                    assert (this.isGlobal());
                    return this.doRequired(arg0Value, arg1Value, arg2Value, arg3Value, this.required_hasPropertyNode_, this.required_classProfile_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
        }

        private Object executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value, Object arg2Value, PropertyGetNode arg3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0 && !this.isGlobal()) {
                    JSClass cachedClass__;
                    int count0_ = 0;
                    JSObjectCachedData s0_ = this.jSObjectCached_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null && s0_.cachedClass_ != this.getJSClass(arg0Value)) {
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (s0_ == null && (cachedClass__ = this.getJSClass(arg0Value)) == this.getJSClass(arg0Value) && count0_ < 2) {
                        s0_ = new JSObjectCachedData(this.jSObjectCached_cache);
                        s0_.cachedClass_ = cachedClass__;
                        VarHandle.storeStoreFence();
                        this.jSObjectCached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.doJSObjectCached(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedClass_);
                        return object;
                    }
                }
                if (!this.isGlobal()) {
                    this.exclude_ = exclude |= 1;
                    this.jSObjectCached_cache = null;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doJSObjectDirect(arg0Value, arg1Value, arg2Value, arg3Value);
                    return object;
                }
                if (this.isGlobal()) {
                    this.required_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
                    this.required_classProfile_ = JSClassProfile.create();
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doRequired(arg0Value, arg1Value, arg2Value, arg3Value, this.required_hasPropertyNode_, this.required_classProfile_);
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
        public NodeCost getCost() {
            JSObjectCachedData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.jSObjectCached_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Object>> cached;
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doJSObjectCached";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                JSObjectCachedData s0_ = this.jSObjectCached_cache;
                while (s0_ != null) {
                    cached.add(Arrays.asList(s0_.cachedClass_));
                    s0_ = s0_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doJSObjectDirect";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "doRequired";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.required_hasPropertyNode_, this.required_classProfile_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static PropertyGetNode.GetPropertyFromJSObjectNode create(PropertyGetNode root) {
            return new GetPropertyFromJSObjectNodeGen(root);
        }

        @GeneratedBy(value=PropertyGetNode.GetPropertyFromJSObjectNode.class)
        private static final class JSObjectCachedData {
            @CompilerDirectives.CompilationFinal
            JSObjectCachedData next_;
            @CompilerDirectives.CompilationFinal
            JSClass cachedClass_;

            JSObjectCachedData(JSObjectCachedData next_) {
                this.next_ = next_;
            }
        }
    }
}

