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
import com.oracle.truffle.js.nodes.access.GetIteratorBaseNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNodeGen;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=GetIteratorBaseNode.class)
public final class GetIteratorBaseNodeGen
extends GetIteratorBaseNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private GetIteratorData getIterator_cache;

    private GetIteratorBaseNodeGen() {
    }

    @Override
    public IteratorRecord execute(Object arg0Value, Object arg1Value) {
        GetIteratorData s0_;
        int state_0 = this.state_0_;
        if (state_0 != 0 && (s0_ = this.getIterator_cache) != null) {
            return this.doGetIterator(arg0Value, arg1Value, s0_.getIteratorMethodNode_, s0_.isCallableNode_, s0_.iteratorCallNode_, s0_.isObjectNode_, s0_.getNextMethodNode_, s0_.errorBranch_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private IteratorRecord executeAndSpecialize(Object arg0Value, Object arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            GetIteratorData s0_ = super.insert(new GetIteratorData());
            s0_.getIteratorMethodNode_ = s0_.insertAccessor(this.createIteratorMethodNode());
            s0_.isCallableNode_ = s0_.insertAccessor(IsCallableNode.create());
            s0_.iteratorCallNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
            s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
            s0_.getNextMethodNode_ = s0_.insertAccessor(this.createNextMethodNode());
            s0_.errorBranch_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.getIterator_cache = s0_;
            this.state_0_ = state_0 |= 1;
            lock.unlock();
            hasLock = false;
            IteratorRecord iteratorRecord = this.doGetIterator(arg0Value, arg1Value, s0_.getIteratorMethodNode_, s0_.isCallableNode_, s0_.iteratorCallNode_, s0_.isObjectNode_, s0_.getNextMethodNode_, s0_.errorBranch_);
            return iteratorRecord;
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
        s[0] = "doGetIterator";
        if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
            GetIteratorData s0_ = this.getIterator_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.getIteratorMethodNode_, s0_.isCallableNode_, s0_.iteratorCallNode_, s0_.isObjectNode_, s0_.getNextMethodNode_, s0_.errorBranch_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static GetIteratorBaseNode create() {
        return new GetIteratorBaseNodeGen();
    }

    public static GetIteratorBaseNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=GetIteratorBaseNode.class)
    @DenyReplace
    private static final class Uncached
    extends GetIteratorBaseNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public IteratorRecord execute(Object arg0Value, Object arg1Value) {
            return this.doGetIterator(arg0Value, arg1Value, GetIteratorBaseNode.uncachedIteratorMethodNode(), IsCallableNodeGen.getUncached(), JSFunctionCallNode.getUncachedCall(), IsJSObjectNode.getUncached(), GetIteratorBaseNode.uncachedNextMethodNode(), BranchProfile.getUncached());
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

    @GeneratedBy(value=GetIteratorBaseNode.class)
    private static final class GetIteratorData
    extends Node {
        @Node.Child
        GetMethodNode getIteratorMethodNode_;
        @Node.Child
        IsCallableNode isCallableNode_;
        @Node.Child
        JSFunctionCallNode iteratorCallNode_;
        @Node.Child
        IsJSObjectNode isObjectNode_;
        @Node.Child
        PropertyGetNode getNextMethodNode_;
        @CompilerDirectives.CompilationFinal
        BranchProfile errorBranch_;

        GetIteratorData() {
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

