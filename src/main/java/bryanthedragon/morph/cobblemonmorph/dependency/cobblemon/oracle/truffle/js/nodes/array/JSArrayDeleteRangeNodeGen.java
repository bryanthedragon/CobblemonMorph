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
import com.oracle.truffle.js.nodes.array.JSArrayDeleteRangeNode;
import com.oracle.truffle.js.nodes.array.JSArrayNextElementIndexNode;
import com.oracle.truffle.js.nodes.control.DeletePropertyNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSArrayDeleteRangeNode.class)
public final class JSArrayDeleteRangeNodeGen
extends JSArrayDeleteRangeNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private DenseArrayData denseArray_cache;
    @Node.Child
    private SparseArrayData sparseArray_cache;
    @Node.Child
    private DeletePropertyNode uncached_deletePropertyNode_;
    @Node.Child
    private JSArrayNextElementIndexNode uncached_nextElementIndexNode_;

    private JSArrayDeleteRangeNodeGen(JSContext context, boolean orThrow) {
        super(context, orThrow);
    }

    @Override
    @ExplodeLoop
    public void execute(JSDynamicObject arg0Value, ScriptArray arg1Value, long arg2Value, long arg3Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
                DenseArrayData s0_ = this.denseArray_cache;
                while (s0_ != null) {
                    if (s0_.cachedArrayType_.isInstance(arg1Value)) {
                        assert (!s0_.cachedArrayType_.isHolesType());
                        this.denseArray(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedArrayType_, s0_.deletePropertyNode_);
                        return;
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0) {
                SparseArrayData s1_ = this.sparseArray_cache;
                while (s1_ != null) {
                    if (s1_.cachedArrayType_.isInstance(arg1Value)) {
                        assert (s1_.cachedArrayType_.isHolesType());
                        this.sparseArray(arg0Value, arg1Value, arg2Value, arg3Value, s1_.cachedArrayType_, s1_.deletePropertyNode_, s1_.nextElementIndexNode_);
                        return;
                    }
                    s1_ = s1_.next_;
                }
            }
            if ((state_0 & 4) != 0) {
                this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, this.uncached_deletePropertyNode_, this.uncached_nextElementIndexNode_);
                return;
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeAndSpecialize(JSDynamicObject arg0Value, ScriptArray arg1Value, long arg2Value, long arg3Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if ((exclude & 1) == 0) {
                ScriptArray cachedArrayType__;
                int count0_ = 0;
                DenseArrayData s0_ = this.denseArray_cache;
                if ((state_0 & 1) != 0) {
                    while (s0_ != null) {
                        if (s0_.cachedArrayType_.isInstance(arg1Value)) {
                            assert (!s0_.cachedArrayType_.isHolesType());
                            break;
                        }
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && (cachedArrayType__ = arg1Value).isInstance(arg1Value) && !cachedArrayType__.isHolesType() && count0_ < 5) {
                    s0_ = super.insert(new DenseArrayData(this.denseArray_cache));
                    s0_.cachedArrayType_ = cachedArrayType__;
                    s0_.deletePropertyNode_ = s0_.insertAccessor(DeletePropertyNode.create(this.orThrow, this.context));
                    VarHandle.storeStoreFence();
                    this.denseArray_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    this.denseArray(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedArrayType_, s0_.deletePropertyNode_);
                    return;
                }
            }
            if ((exclude & 2) == 0) {
                ScriptArray cachedArrayType__1;
                int count1_ = 0;
                SparseArrayData s1_ = this.sparseArray_cache;
                if ((state_0 & 2) != 0) {
                    while (s1_ != null) {
                        if (s1_.cachedArrayType_.isInstance(arg1Value)) {
                            assert (s1_.cachedArrayType_.isHolesType());
                            break;
                        }
                        s1_ = s1_.next_;
                        ++count1_;
                    }
                }
                if (s1_ == null && (cachedArrayType__1 = arg1Value).isInstance(arg1Value) && cachedArrayType__1.isHolesType() && count1_ < 5) {
                    s1_ = super.insert(new SparseArrayData(this.sparseArray_cache));
                    s1_.cachedArrayType_ = cachedArrayType__1;
                    s1_.deletePropertyNode_ = s1_.insertAccessor(DeletePropertyNode.create(this.orThrow, this.context));
                    s1_.nextElementIndexNode_ = s1_.insertAccessor(JSArrayNextElementIndexNode.create(this.context));
                    VarHandle.storeStoreFence();
                    this.sparseArray_cache = s1_;
                    this.state_0_ = state_0 |= 2;
                }
                if (s1_ != null) {
                    lock.unlock();
                    hasLock = false;
                    this.sparseArray(arg0Value, arg1Value, arg2Value, arg3Value, s1_.cachedArrayType_, s1_.deletePropertyNode_, s1_.nextElementIndexNode_);
                    return;
                }
            }
            this.uncached_deletePropertyNode_ = super.insert(DeletePropertyNode.create(this.orThrow, this.context));
            this.uncached_nextElementIndexNode_ = super.insert(JSArrayNextElementIndexNode.create(this.context));
            this.exclude_ = exclude |= 3;
            this.denseArray_cache = null;
            this.sparseArray_cache = null;
            state_0 &= 0xFFFFFFFC;
            this.state_0_ = state_0 |= 4;
            lock.unlock();
            hasLock = false;
            this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value, this.uncached_deletePropertyNode_, this.uncached_nextElementIndexNode_);
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
            DenseArrayData s0_ = this.denseArray_cache;
            SparseArrayData s1_ = this.sparseArray_cache;
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
        s[0] = "denseArray";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            DenseArrayData s0_ = this.denseArray_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.cachedArrayType_, s0_.deletePropertyNode_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "sparseArray";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            SparseArrayData s1_ = this.sparseArray_cache;
            while (s1_ != null) {
                cached.add(Arrays.asList(s1_.cachedArrayType_, s1_.deletePropertyNode_, s1_.nextElementIndexNode_));
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
            cached.add(Arrays.asList(this.uncached_deletePropertyNode_, this.uncached_nextElementIndexNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[3] = s;
        return Introspection.Provider.create(data);
    }

    public static JSArrayDeleteRangeNode create(JSContext context, boolean orThrow) {
        return new JSArrayDeleteRangeNodeGen(context, orThrow);
    }

    @GeneratedBy(value=JSArrayDeleteRangeNode.class)
    private static final class SparseArrayData
    extends Node {
        @Node.Child
        SparseArrayData next_;
        @CompilerDirectives.CompilationFinal
        ScriptArray cachedArrayType_;
        @Node.Child
        DeletePropertyNode deletePropertyNode_;
        @Node.Child
        JSArrayNextElementIndexNode nextElementIndexNode_;

        SparseArrayData(SparseArrayData next_) {
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

    @GeneratedBy(value=JSArrayDeleteRangeNode.class)
    private static final class DenseArrayData
    extends Node {
        @Node.Child
        DenseArrayData next_;
        @CompilerDirectives.CompilationFinal
        ScriptArray cachedArrayType_;
        @Node.Child
        DeletePropertyNode deletePropertyNode_;

        DenseArrayData(DenseArrayData next_) {
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

