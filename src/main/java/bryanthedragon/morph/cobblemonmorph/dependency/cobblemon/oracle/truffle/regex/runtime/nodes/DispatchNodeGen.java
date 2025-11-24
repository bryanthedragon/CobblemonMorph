/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.regex.runtime.nodes;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.regex.result.RegexResult;
import com.oracle.truffle.regex.runtime.nodes.DispatchNode;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=DispatchNode.class)
public final class DispatchNodeGen
extends DispatchNode {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private ExecuteDirectData executeDirect_cache;
    @Node.Child
    private IndirectCallNode executeIndirect_callNode_;

    private DispatchNodeGen() {
    }

    @Override
    @ExplodeLoop
    public Object execute(CallTarget arg0Value, RegexResult arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
                ExecuteDirectData s0_ = this.executeDirect_cache;
                while (s0_ != null) {
                    if (arg0Value == s0_.cachedTarget_) {
                        return DispatchNode.executeDirect(arg0Value, arg1Value, s0_.cachedTarget_, s0_.callNode_);
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0) {
                return DispatchNode.executeIndirect(arg0Value, arg1Value, this.executeIndirect_callNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(CallTarget arg0Value, RegexResult arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
                int count0_ = 0;
                ExecuteDirectData s0_ = this.executeDirect_cache;
                if ((state_0 & 1) != 0) {
                    while (s0_ != null && arg0Value != s0_.cachedTarget_) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && count0_ < 3) {
                    s0_ = super.insert(new ExecuteDirectData(this.executeDirect_cache));
                    s0_.cachedTarget_ = arg0Value;
                    s0_.callNode_ = s0_.insertAccessor(DirectCallNode.create(s0_.cachedTarget_));
                    VarHandle.storeStoreFence();
                    this.executeDirect_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    Object object = DispatchNode.executeDirect(arg0Value, arg1Value, s0_.cachedTarget_, s0_.callNode_);
                    return object;
                }
            }
            this.executeIndirect_callNode_ = super.insert(IndirectCallNode.create());
            this.exclude_ = exclude |= 1;
            this.executeDirect_cache = null;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            Object object = DispatchNode.executeIndirect(arg0Value, arg1Value, this.executeIndirect_callNode_);
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
        ExecuteDirectData s0_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.executeDirect_cache) == null || s0_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static DispatchNode create() {
        return new DispatchNodeGen();
    }

    public static DispatchNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=DispatchNode.class)
    @DenyReplace
    private static final class Uncached
    extends DispatchNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object execute(CallTarget arg0Value, RegexResult arg1Value) {
            return DispatchNode.executeIndirect(arg0Value, arg1Value, IndirectCallNode.getUncached());
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

    @GeneratedBy(value=DispatchNode.class)
    private static final class ExecuteDirectData
    extends Node {
        @Node.Child
        ExecuteDirectData next_;
        @CompilerDirectives.CompilationFinal
        CallTarget cachedTarget_;
        @Node.Child
        DirectCallNode callNode_;

        ExecuteDirectData(ExecuteDirectData next_) {
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

