/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.array.JSArrayNextElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayToDenseObjectArrayNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSArrayToDenseObjectArrayNode.class)
public final class JSArrayToDenseObjectArrayNodeGen
extends JSArrayToDenseObjectArrayNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private FromDenseArrayData fromDenseArray_cache;
    @Node.Child
    private FromSparseArrayData fromSparseArray_cache;
    @Node.Child
    private UncachedData uncached_cache;

    private JSArrayToDenseObjectArrayNodeGen(JSContext context) {
        super(context);
    }

    @Override
    @ExplodeLoop
    public Object[] executeObjectArray(JSDynamicObject arg0Value, ScriptArray arg1Value, long arg2Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            UncachedData s2_;
            if ((state_0 & 1) != 0) {
                FromDenseArrayData s0_ = this.fromDenseArray_cache;
                while (s0_ != null) {
                    if (s0_.cachedArrayType_.isInstance(arg1Value)) {
                        assert (!s0_.cachedArrayType_.isHolesType());
                        if (!s0_.cachedArrayType_.hasHoles(arg0Value) && s0_.cachedArrayType_.firstElementIndex(arg0Value) == 0L) {
                            return this.fromDenseArray(arg0Value, arg1Value, arg2Value, s0_.cachedArrayType_, s0_.readNode_);
                        }
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0) {
                FromSparseArrayData s1_ = this.fromSparseArray_cache;
                while (s1_ != null) {
                    if (s1_.cachedArrayType_.isInstance(arg1Value) && (s1_.cachedArrayType_.isHolesType() || s1_.cachedArrayType_.hasHoles(arg0Value))) {
                        return this.fromSparseArray(arg0Value, arg1Value, arg2Value, s1_.cachedArrayType_, s1_.nextElementIndexNode_, s1_.growProfile_);
                    }
                    s1_ = s1_.next_;
                }
            }
            if ((state_0 & 4) != 0 && (s2_ = this.uncached_cache) != null) {
                return this.doUncached(arg0Value, arg1Value, arg2Value, s2_.nextElementIndexNode_, s2_.readNode_, s2_.growProfile_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object[] executeAndSpecialize(JSDynamicObject arg0Value, ScriptArray arg1Value, long arg2Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if ((exclude & 1) == 0) {
                Object[] cachedArrayType__;
                int count0_ = 0;
                FromDenseArrayData s0_ = this.fromDenseArray_cache;
                if ((state_0 & 1) != 0) {
                    while (s0_ != null) {
                        if (s0_.cachedArrayType_.isInstance((ScriptArray)arg1Value)) {
                            assert (!s0_.cachedArrayType_.isHolesType());
                            if (!s0_.cachedArrayType_.hasHoles(arg0Value) && s0_.cachedArrayType_.firstElementIndex(arg0Value) == 0L) break;
                        }
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && (cachedArrayType__ = arg1Value).isInstance((ScriptArray)arg1Value) && !cachedArrayType__.isHolesType() && !cachedArrayType__.hasHoles(arg0Value) && cachedArrayType__.firstElementIndex(arg0Value) == 0L && count0_ < 5) {
                    s0_ = super.insert(new FromDenseArrayData(this.fromDenseArray_cache));
                    s0_.cachedArrayType_ = cachedArrayType__;
                    s0_.readNode_ = s0_.insertAccessor(ReadElementNode.create(this.context));
                    VarHandle.storeStoreFence();
                    this.fromDenseArray_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    cachedArrayType__ = this.fromDenseArray(arg0Value, (ScriptArray)arg1Value, arg2Value, s0_.cachedArrayType_, s0_.readNode_);
                    return cachedArrayType__;
                }
            }
            if ((exclude & 2) == 0) {
                Object[] cachedArrayType__1;
                int count1_ = 0;
                FromSparseArrayData s1_ = this.fromSparseArray_cache;
                if ((state_0 & 2) != 0) {
                    while (!(s1_ == null || s1_.cachedArrayType_.isInstance((ScriptArray)arg1Value) && (s1_.cachedArrayType_.isHolesType() || s1_.cachedArrayType_.hasHoles(arg0Value)))) {
                        s1_ = s1_.next_;
                        ++count1_;
                    }
                }
                if (s1_ == null && (cachedArrayType__1 = arg1Value).isInstance((ScriptArray)arg1Value) && (cachedArrayType__1.isHolesType() || cachedArrayType__1.hasHoles(arg0Value)) && count1_ < 5) {
                    s1_ = super.insert(new FromSparseArrayData(this.fromSparseArray_cache));
                    s1_.cachedArrayType_ = cachedArrayType__1;
                    s1_.nextElementIndexNode_ = s1_.insertAccessor(JSArrayNextElementIndexNode.create(this.context));
                    s1_.growProfile_ = BranchProfile.create();
                    VarHandle.storeStoreFence();
                    this.fromSparseArray_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                }
                if (s1_ != null) {
                    lock.unlock();
                    hasLock = false;
                    Object[] objectArray = this.fromSparseArray(arg0Value, (ScriptArray)arg1Value, arg2Value, s1_.cachedArrayType_, s1_.nextElementIndexNode_, s1_.growProfile_);
                    return objectArray;
                }
            }
            UncachedData s2_ = super.insert(new UncachedData());
            s2_.nextElementIndexNode_ = s2_.insertAccessor(JSArrayNextElementIndexNode.create(this.context));
            s2_.readNode_ = s2_.insertAccessor(ReadElementNode.create(this.context));
            s2_.growProfile_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.uncached_cache = s2_;
            this.exclude_ = exclude |= 3;
            this.fromDenseArray_cache = null;
            this.fromSparseArray_cache = null;
            state_0 &= 0xFFFFFFFC;
            this.state_0_ = state_0 |= 4;
            lock.unlock();
            hasLock = false;
            Object[] objectArray = this.doUncached(arg0Value, (ScriptArray)arg1Value, arg2Value, s2_.nextElementIndexNode_, s2_.readNode_, s2_.growProfile_);
            return objectArray;
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
            FromDenseArrayData s0_ = this.fromDenseArray_cache;
            FromSparseArrayData s1_ = this.fromSparseArray_cache;
            if (!(s0_ != null && s0_.next_ != null || s1_ != null && s1_.next_ != null)) {
                return NodeCost.MONOMORPHIC;
            }
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
        s[0] = "fromDenseArray";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            FromDenseArrayData s0_ = this.fromDenseArray_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.cachedArrayType_, s0_.readNode_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "fromSparseArray";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            FromSparseArrayData s1_ = this.fromSparseArray_cache;
            while (s1_ != null) {
                cached.add(Arrays.asList(s1_.cachedArrayType_, s1_.nextElementIndexNode_, s1_.growProfile_));
                s1_ = s1_.next_;
            }
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
            UncachedData s2_ = this.uncached_cache;
            if (s2_ != null) {
                cached.add(Arrays.asList(s2_.nextElementIndexNode_, s2_.readNode_, s2_.growProfile_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static JSArrayToDenseObjectArrayNode create(JSContext context) {
        return new JSArrayToDenseObjectArrayNodeGen(context);
    }

    @GeneratedBy(value=JSArrayToDenseObjectArrayNode.class)
    private static final class UncachedData
    extends Node {
        @Node.Child
        JSArrayNextElementIndexNode nextElementIndexNode_;
        @Node.Child
        ReadElementNode readNode_;
        @CompilerDirectives.CompilationFinal
        BranchProfile growProfile_;

        UncachedData() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }

    @GeneratedBy(value=JSArrayToDenseObjectArrayNode.class)
    private static final class FromSparseArrayData
    extends Node {
        @Node.Child
        FromSparseArrayData next_;
        @CompilerDirectives.CompilationFinal
        ScriptArray cachedArrayType_;
        @Node.Child
        JSArrayNextElementIndexNode nextElementIndexNode_;
        @CompilerDirectives.CompilationFinal
        BranchProfile growProfile_;

        FromSparseArrayData(FromSparseArrayData next_) {
            this.next_ = next_;
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
        }
    }

    @GeneratedBy(value=JSArrayToDenseObjectArrayNode.class)
    private static final class FromDenseArrayData
    extends Node {
        @Node.Child
        FromDenseArrayData next_;
        @CompilerDirectives.CompilationFinal
        ScriptArray cachedArrayType_;
        @Node.Child
        ReadElementNode readNode_;

        FromDenseArrayData(FromDenseArrayData next_) {
            this.next_ = next_;
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

