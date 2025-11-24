
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.IsJSDynamicObjectNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=IsJSDynamicObjectNode.class)
public final class IsJSDynamicObjectNodeGen
extends IsJSDynamicObjectNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private Class<?> isObjectCached_cachedClass_;
    @CompilerDirectives.CompilationFinal
    private boolean isObjectCached_cachedResult_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile isObject_resultProfile_;

    private IsJSDynamicObjectNodeGen() {
    }

    @Override
    public boolean executeBoolean(Object arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
                assert (this.isObjectCached_cachedClass_ != null);
                if (CompilerDirectives.isExact(arg0Value, this.isObjectCached_cachedClass_)) {
                    return IsJSDynamicObjectNode.isObjectCached(arg0Value, this.isObjectCached_cachedClass_, this.isObjectCached_cachedResult_);
                }
            }
            if ((state_0 & 2) != 0) {
                return this.isObject(arg0Value, this.isObject_resultProfile_);
            }
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
            int exclude = this.exclude_;
            if (exclude == 0) {
                Class<?> isObjectCached_cachedClass__;
                boolean IsObjectCached_duplicateFound_ = false;
                if ((state_0 & 1) != 0) {
                    assert (this.isObjectCached_cachedClass_ != null);
                    if (CompilerDirectives.isExact(arg0Value, this.isObjectCached_cachedClass_)) {
                        IsObjectCached_duplicateFound_ = true;
                    }
                }
                if (!IsObjectCached_duplicateFound_ && (isObjectCached_cachedClass__ = JSGuards.getClassIfJSDynamicObject(arg0Value)) != null && CompilerDirectives.isExact(arg0Value, isObjectCached_cachedClass__) && (state_0 & 1) == 0) {
                    this.isObjectCached_cachedClass_ = isObjectCached_cachedClass__;
                    this.isObjectCached_cachedResult_ = JSGuards.isJSDynamicObject(arg0Value);
                    this.state_0_ = state_0 |= 1;
                    IsObjectCached_duplicateFound_ = true;
                }
                if (IsObjectCached_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl = IsJSDynamicObjectNode.isObjectCached(arg0Value, this.isObjectCached_cachedClass_, this.isObjectCached_cachedResult_);
                    return bl;
                }
            }
            this.isObject_resultProfile_ = ConditionProfile.create();
            this.exclude_ = exclude |= 1;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            boolean bl = this.isObject(arg0Value, this.isObject_resultProfile_);
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
        ArrayList<List<Object>> cached;
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "isObjectCached";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.isObjectCached_cachedClass_, this.isObjectCached_cachedResult_));
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "isObject";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.isObject_resultProfile_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static IsJSDynamicObjectNode create() {
        return new IsJSDynamicObjectNodeGen();
    }

    public static IsJSDynamicObjectNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=IsJSDynamicObjectNode.class)
    @DenyReplace
    private static final class Uncached
    extends IsJSDynamicObjectNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean executeBoolean(Object arg0Value) {
            return this.isObject(arg0Value, ConditionProfile.getUncached());
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

