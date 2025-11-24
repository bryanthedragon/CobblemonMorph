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
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.access.HasOnlyShapePropertiesNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=HasOnlyShapePropertiesNode.class)
public final class HasOnlyShapePropertiesNodeGen
extends HasOnlyShapePropertiesNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private CachedData cached_cache;
    @CompilerDirectives.CompilationFinal
    private JSContext objectPrototype_context_;

    private HasOnlyShapePropertiesNodeGen() {
    }

    @Override
    @ExplodeLoop
    public boolean execute(JSDynamicObject arg0Value, JSClass arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    if (arg1Value == s0_.cachedJSClass_) {
                        assert (!HasOnlyShapePropertiesNode.isJSObjectPrototype(s0_.cachedJSClass_));
                        return HasOnlyShapePropertiesNode.doCached(arg0Value, arg1Value, s0_.cachedJSClass_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0 && HasOnlyShapePropertiesNode.isJSObjectPrototype(arg1Value)) {
                return HasOnlyShapePropertiesNode.doObjectPrototype(arg0Value, arg1Value, this.objectPrototype_context_);
            }
            if ((state_0 & 4) != 0) {
                return HasOnlyShapePropertiesNode.doUncached(arg0Value, arg1Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean executeAndSpecialize(JSDynamicObject arg0Value, JSClass arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            boolean bl;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if ((exclude & 1) == 0) {
                JSClass cachedJSClass__;
                int count0_ = 0;
                CachedData s0_ = this.cached_cache;
                if ((state_0 & 1) != 0) {
                    while (s0_ != null) {
                        if (arg1Value == s0_.cachedJSClass_) {
                            assert (!HasOnlyShapePropertiesNode.isJSObjectPrototype(s0_.cachedJSClass_));
                            break;
                        }
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && !HasOnlyShapePropertiesNode.isJSObjectPrototype(cachedJSClass__ = arg1Value) && count0_ < 5) {
                    s0_ = new CachedData(this.cached_cache);
                    s0_.cachedJSClass_ = cachedJSClass__;
                    VarHandle.storeStoreFence();
                    this.cached_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    boolean bl2 = HasOnlyShapePropertiesNode.doCached(arg0Value, arg1Value, s0_.cachedJSClass_);
                    return bl2;
                }
            }
            if ((exclude & 2) == 0 && HasOnlyShapePropertiesNode.isJSObjectPrototype(arg1Value)) {
                this.objectPrototype_context_ = JSObject.getJSContext(arg0Value);
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                bl = HasOnlyShapePropertiesNode.doObjectPrototype(arg0Value, arg1Value, this.objectPrototype_context_);
                return bl;
            }
            this.exclude_ = exclude |= 3;
            this.cached_cache = null;
            state_0 &= 0xFFFFFFFC;
            this.state_0_ = state_0 |= 4;
            lock.unlock();
            hasLock = false;
            bl = HasOnlyShapePropertiesNode.doUncached(arg0Value, arg1Value);
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
        CachedData s0_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.cached_cache) == null || s0_.next_ == null)) {
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
        s[0] = "doCached";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            CachedData s0_ = this.cached_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.cachedJSClass_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doObjectPrototype";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.objectPrototype_context_));
            s[2] = cached;
        } else {
            s[1] = (exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[2] = s;
        s = new Object[3];
        s[0] = "doUncached";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static HasOnlyShapePropertiesNode create() {
        return new HasOnlyShapePropertiesNodeGen();
    }

    @GeneratedBy(value=HasOnlyShapePropertiesNode.class)
    private static final class CachedData {
        @CompilerDirectives.CompilationFinal
        CachedData next_;
        @CompilerDirectives.CompilationFinal
        JSClass cachedJSClass_;

        CachedData(CachedData next_) {
            this.next_ = next_;
        }
    }
}

