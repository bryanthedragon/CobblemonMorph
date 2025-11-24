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
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.access.IterableToListNode;
import com.oracle.truffle.js.nodes.access.IteratorStepNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=IterableToListNode.class)
public final class IterableToListNodeGen
extends IterableToListNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private IterableToListData iterableToList_cache;

    private IterableToListNodeGen() {
    }

    @Override
    public SimpleArrayList<Object> execute(IteratorRecord arg0Value) {
        IterableToListData s0_;
        int state_0 = this.state_0_;
        if (state_0 != 0 && (s0_ = this.iterableToList_cache) != null) {
            return IterableToListNode.iterableToList(arg0Value, s0_.iteratorStepNode_, s0_.getIteratorValueNode_, s0_.growProfile_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private SimpleArrayList<Object> executeAndSpecialize(IteratorRecord arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            IterableToListData s0_ = super.insert(new IterableToListData());
            s0_.iteratorStepNode_ = s0_.insertAccessor(IteratorStepNode.create());
            s0_.getIteratorValueNode_ = s0_.insertAccessor(IteratorValueNode.create());
            s0_.growProfile_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.iterableToList_cache = s0_;
            this.state_0_ = state_0 |= 1;
            lock.unlock();
            hasLock = false;
            SimpleArrayList<Object> simpleArrayList = IterableToListNode.iterableToList(arg0Value, s0_.iteratorStepNode_, s0_.getIteratorValueNode_, s0_.growProfile_);
            return simpleArrayList;
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
        return NodeCost.MONOMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[2];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "iterableToList";
        if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
            IterableToListData s0_ = this.iterableToList_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.iteratorStepNode_, s0_.getIteratorValueNode_, s0_.growProfile_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static IterableToListNode create() {
        return new IterableToListNodeGen();
    }

    public static IterableToListNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=IterableToListNode.class)
    @DenyReplace
    private static final class Uncached
    extends IterableToListNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public SimpleArrayList<Object> execute(IteratorRecord arg0Value) {
            return IterableToListNode.iterableToList(arg0Value, IteratorStepNode.getUncached(), IteratorValueNode.getUncached(), BranchProfile.getUncached());
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

    @GeneratedBy(value=IterableToListNode.class)
    private static final class IterableToListData
    extends Node {
        @Node.Child
        IteratorStepNode iteratorStepNode_;
        @Node.Child
        IteratorValueNode getIteratorValueNode_;
        @CompilerDirectives.CompilationFinal
        BranchProfile growProfile_;

        IterableToListData() {
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

