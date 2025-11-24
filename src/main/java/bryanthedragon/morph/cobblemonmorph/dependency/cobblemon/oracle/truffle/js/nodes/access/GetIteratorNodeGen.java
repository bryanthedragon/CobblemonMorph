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
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.GetIteratorNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=GetIteratorNode.class)
public final class GetIteratorNodeGen
extends GetIteratorNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private GetIteratorData getIterator_cache;

    private GetIteratorNodeGen(JSContext context, JavaScriptNode objectNode) {
        super(context, objectNode);
    }

    @Override
    public IteratorRecord execute(Object objectNodeValue) {
        GetIteratorData s0_;
        int state_0 = this.state_0_;
        if (state_0 != 0 && (s0_ = this.getIterator_cache) != null) {
            return this.doGetIterator(objectNodeValue, s0_.isCallableNode_, s0_.methodCallNode_, s0_.isObjectNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(objectNodeValue);
    }

    @Override
    public IteratorRecord execute(VirtualFrame frameValue) {
        GetIteratorData s0_;
        int state_0 = this.state_0_;
        Object objectNodeValue_ = this.objectNode.execute(frameValue);
        if (state_0 != 0 && (s0_ = this.getIterator_cache) != null) {
            return this.doGetIterator(objectNodeValue_, s0_.isCallableNode_, s0_.methodCallNode_, s0_.isObjectNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(objectNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private IteratorRecord executeAndSpecialize(Object objectNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            GetIteratorData s0_ = super.insert(new GetIteratorData());
            s0_.isCallableNode_ = s0_.insertAccessor(IsCallableNode.create());
            s0_.methodCallNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
            s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
            VarHandle.storeStoreFence();
            this.getIterator_cache = s0_;
            this.state_0_ = state_0 |= 1;
            lock.unlock();
            hasLock = false;
            IteratorRecord iteratorRecord = this.doGetIterator(objectNodeValue, s0_.isCallableNode_, s0_.methodCallNode_, s0_.isObjectNode_);
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
            ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
            GetIteratorData s0_ = this.getIterator_cache;
            if (s0_ != null) {
                cached.add(Arrays.asList(s0_.isCallableNode_, s0_.methodCallNode_, s0_.isObjectNode_));
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static GetIteratorNode create(JSContext context, JavaScriptNode objectNode) {
        return new GetIteratorNodeGen(context, objectNode);
    }

    @GeneratedBy(value=GetIteratorNode.class)
    private static final class GetIteratorData
    extends Node {
        @Node.Child
        IsCallableNode isCallableNode_;
        @Node.Child
        JSFunctionCallNode methodCallNode_;
        @Node.Child
        IsJSObjectNode isObjectNode_;

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

