/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.function.InternalCallNode;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=InternalCallNode.class)
public final class InternalCallNodeGen
extends InternalCallNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private DirectCallData directCall_cache;
    @Node.Child
    private IndirectCallNode indirectCall_indirectCallNode_;

    private InternalCallNodeGen() {
    }

    @Override
    @ExplodeLoop
    public Object execute(CallTarget arg0Value, Object[] arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
                DirectCallData s0_ = this.directCall_cache;
                while (s0_ != null) {
                    if (arg0Value == s0_.cachedCallTarget_) {
                        return InternalCallNode.directCall(arg0Value, arg1Value, s0_.cachedCallTarget_, s0_.directCallNode_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0) {
                return InternalCallNode.indirectCall(arg0Value, arg1Value, this.indirectCall_indirectCallNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(CallTarget arg0Value, Object[] arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int count0_ = 0;
            DirectCallData s0_ = this.directCall_cache;
            if ((state_0 & 1) != 0) {
                while (s0_ != null && arg0Value != s0_.cachedCallTarget_) {
                    s0_ = s0_.next_;
                    ++count0_;
                }
            }
            if (s0_ == null && count0_ < 3) {
                s0_ = super.insert(new DirectCallData(this.directCall_cache));
                s0_.cachedCallTarget_ = arg0Value;
                s0_.directCallNode_ = s0_.insertAccessor(DirectCallNode.create(s0_.cachedCallTarget_));
                VarHandle.storeStoreFence();
                this.directCall_cache = s0_;
                this.state_0_ = state_0 |= 1;
            }
            if (s0_ != null) {
                lock.unlock();
                hasLock = false;
                Object object = InternalCallNode.directCall(arg0Value, arg1Value, s0_.cachedCallTarget_, s0_.directCallNode_);
                return object;
            }
            this.indirectCall_indirectCallNode_ = super.insert(IndirectCallNode.create());
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            Object object = InternalCallNode.indirectCall(arg0Value, arg1Value, this.indirectCall_indirectCallNode_);
            return object;
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        DirectCallData s0_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.directCall_cache) == null || s0_.next_ == null)) {
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
        Object[] s = new Object[3];
        s[0] = "directCall";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            DirectCallData s0_ = this.directCall_cache;
            while (s0_ != null) {
                cached.add(Arrays.asList(s0_.cachedCallTarget_, s0_.directCallNode_));
                s0_ = s0_.next_;
            }
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "indirectCall";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.indirectCall_indirectCallNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static InternalCallNode create() {
        return new InternalCallNodeGen();
    }

    @GeneratedBy(value=InternalCallNode.class)
    private static final class DirectCallData
    extends Node {
        @Node.Child
        DirectCallData next_;
        @CompilerDirectives.CompilationFinal
        CallTarget cachedCallTarget_;
        @Node.Child
        DirectCallNode directCallNode_;

        DirectCallData(DirectCallData next_) {
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

