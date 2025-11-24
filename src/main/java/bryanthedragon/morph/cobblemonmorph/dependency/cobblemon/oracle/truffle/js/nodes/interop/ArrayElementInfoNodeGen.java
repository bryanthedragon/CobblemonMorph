/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.nodes.interop.ArrayElementInfoNode;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBase;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ArrayElementInfoNode.class)
public final class ArrayElementInfoNodeGen
extends ArrayElementInfoNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private CachedData cached_cache;

    private ArrayElementInfoNodeGen() {
    }

    @Override
    @ExplodeLoop
    public TriState execute(JSArrayBase arg0Value, long arg1Value, int arg2Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
                CachedData s0_ = this.cached_cache;
                while (s0_ != null) {
                    if (s0_.arrayType_.isInstance(arg0Value.getArrayType())) {
                        return ArrayElementInfoNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrayType_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0) {
                return ArrayElementInfoNode.doUncached(arg0Value, arg1Value, arg2Value);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private TriState executeAndSpecialize(JSArrayBase arg0Value, long arg1Value, int arg2Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
                ScriptArray arrayType__;
                int count0_ = 0;
                CachedData s0_ = this.cached_cache;
                if ((state_0 & 1) != 0) {
                    while (s0_ != null && !s0_.arrayType_.isInstance(arg0Value.getArrayType())) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && (arrayType__ = arg0Value.getArrayType()).isInstance(arg0Value.getArrayType()) && count0_ < 5) {
                    s0_ = new CachedData(this.cached_cache);
                    s0_.arrayType_ = arrayType__;
                    VarHandle.storeStoreFence();
                    this.cached_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    TriState triState = ArrayElementInfoNode.doCached(arg0Value, arg1Value, arg2Value, s0_.arrayType_);
                    return triState;
                }
            }
            this.exclude_ = exclude |= 1;
            this.cached_cache = null;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            TriState triState = ArrayElementInfoNode.doUncached(arg0Value, arg1Value, arg2Value);
            return triState;
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
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "doCached";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<List<ScriptArray>> cached = new ArrayList<List<ScriptArray>>();
            CachedData s0_ = this.cached_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.arrayType_));
                s0_ = s0_.next_;
            }
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

    public static ArrayElementInfoNode create() {
        return new ArrayElementInfoNodeGen();
    }

    public static ArrayElementInfoNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=ArrayElementInfoNode.class)
    @DenyReplace
    private static final class Uncached
    extends ArrayElementInfoNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public TriState execute(JSArrayBase arg0Value, long arg1Value, int arg2Value) {
            return ArrayElementInfoNode.doUncached(arg0Value, arg1Value, arg2Value);
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

    @GeneratedBy(value=ArrayElementInfoNode.class)
    private static final class CachedData {
        @CompilerDirectives.CompilationFinal
        CachedData next_;
        @CompilerDirectives.CompilationFinal
        ScriptArray arrayType_;

        CachedData(CachedData next_) {
            this.next_ = next_;
        }
    }
}

