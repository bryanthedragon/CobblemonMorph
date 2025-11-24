
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.access.IsExtensibleNode;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSShape;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=IsExtensibleNode.class)
public final class IsExtensibleNodeGen
extends IsExtensibleNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private ConditionProfile resultProfile;
    @CompilerDirectives.CompilationFinal
    private Shape cachedShape_cachedShape_;
    @CompilerDirectives.CompilationFinal
    private boolean cachedShape_result_;
    @CompilerDirectives.CompilationFinal
    private JSClass cachedJSClass_cachedJSClass_;

    private IsExtensibleNodeGen() {
    }

    @Override
    public boolean executeBoolean(JSDynamicObject arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
                assert (JSShape.getJSClass(this.cachedShape_cachedShape_).usesOrdinaryIsExtensible());
                if (this.cachedShape_cachedShape_.check(arg0Value)) {
                    return IsExtensibleNode.doCachedShape(arg0Value, this.cachedShape_cachedShape_, this.cachedShape_result_);
                }
            }
            if ((state_0 & 2) != 0) {
                assert (this.cachedJSClass_cachedJSClass_.usesOrdinaryIsExtensible());
                if (this.cachedJSClass_cachedJSClass_.isInstance(arg0Value)) {
                    return IsExtensibleNode.doCachedJSClass(arg0Value, this.cachedJSClass_cachedJSClass_, this.resultProfile);
                }
            }
            if ((state_0 & 4) != 0) {
                return IsExtensibleNode.doUncached(arg0Value, this.resultProfile);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeAndSpecialize(JSDynamicObject arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if ((exclude & 1) == 0) {
                Shape cachedShape_cachedShape__2;
                boolean CachedShape_duplicateFound_ = false;
                if ((state_0 & 1) != 0) {
                    assert (JSShape.getJSClass(this.cachedShape_cachedShape_).usesOrdinaryIsExtensible());
                    if (this.cachedShape_cachedShape_.check(arg0Value)) {
                        CachedShape_duplicateFound_ = true;
                    }
                }
                if (!CachedShape_duplicateFound_ && JSShape.getJSClass(cachedShape_cachedShape__2 = arg0Value.getShape()).usesOrdinaryIsExtensible() && cachedShape_cachedShape__2.check(arg0Value) && (state_0 & 1) == 0) {
                    this.cachedShape_cachedShape_ = cachedShape_cachedShape__2;
                    this.cachedShape_result_ = JSShape.isExtensible(cachedShape_cachedShape__2);
                    this.state_0_ = state_0 |= 1;
                    CachedShape_duplicateFound_ = true;
                }
                if (CachedShape_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    boolean cachedShape_cachedShape__2 = IsExtensibleNode.doCachedShape(arg0Value, this.cachedShape_cachedShape_, this.cachedShape_result_);
                    return cachedShape_cachedShape__2;
                }
            }
            if ((exclude & 2) == 0) {
                JSClass cachedJSClass_cachedJSClass__;
                boolean CachedJSClass_duplicateFound_ = false;
                if ((state_0 & 2) != 0) {
                    assert (this.cachedJSClass_cachedJSClass_.usesOrdinaryIsExtensible());
                    if (this.cachedJSClass_cachedJSClass_.isInstance(arg0Value)) {
                        CachedJSClass_duplicateFound_ = true;
                    }
                }
                if (!CachedJSClass_duplicateFound_ && (cachedJSClass_cachedJSClass__ = JSShape.getJSClass(arg0Value.getShape())).usesOrdinaryIsExtensible() && cachedJSClass_cachedJSClass__.isInstance(arg0Value) && (state_0 & 2) == 0) {
                    this.cachedJSClass_cachedJSClass_ = cachedJSClass_cachedJSClass__;
                    this.resultProfile = this.resultProfile == null ? ConditionProfile.createBinaryProfile() : this.resultProfile;
                    this.exclude_ = exclude |= 1;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    CachedJSClass_duplicateFound_ = true;
                }
                if (CachedJSClass_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl = IsExtensibleNode.doCachedJSClass(arg0Value, this.cachedJSClass_cachedJSClass_, this.resultProfile);
                    return bl;
                }
            }
            this.resultProfile = this.resultProfile == null ? ConditionProfile.createBinaryProfile() : this.resultProfile;
            this.exclude_ = exclude |= 3;
            state_0 &= 0xFFFFFFFC;
            this.state_0_ = state_0 |= 4;
            lock.unlock();
            hasLock = false;
            boolean bl = IsExtensibleNode.doUncached(arg0Value, this.resultProfile);
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
        Object[] data = new Object[4];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doCachedShape";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.cachedShape_cachedShape_, this.cachedShape_result_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doCachedJSClass";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.cachedJSClass_cachedJSClass_, this.resultProfile));
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doUncached";
        if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.resultProfile));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static IsExtensibleNode create() {
        return new IsExtensibleNodeGen();
    }

    public static IsExtensibleNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=IsExtensibleNode.class)
    @DenyReplace
    private static final class Uncached
    extends IsExtensibleNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean executeBoolean(JSDynamicObject arg0Value) {
            return IsExtensibleNode.doUncached(arg0Value, ConditionProfile.getUncached());
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

