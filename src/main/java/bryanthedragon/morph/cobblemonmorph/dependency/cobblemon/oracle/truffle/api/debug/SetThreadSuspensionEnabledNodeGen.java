/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.debug.Breakpoint;
import com.oracle.truffle.api.debug.DebuggerSession;
import com.oracle.truffle.api.debug.SetThreadSuspensionEnabledNode;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=SetThreadSuspensionEnabledNode.class)
final class SetThreadSuspensionEnabledNodeGen
extends SetThreadSuspensionEnabledNode {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @CompilerDirectives.CompilationFinal
    private ExecuteCachedData executeCached_cache;

    private SetThreadSuspensionEnabledNodeGen() {
    }

    @Override
    @ExplodeLoop
    protected void execute(boolean arg0Value, Breakpoint.SessionList arg1Value, long arg2Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg1Value.next == null) {
                ExecuteCachedData s0_ = this.executeCached_cache;
                while (s0_ != null) {
                    if (arg2Value == s0_.currentThreadId_) {
                        this.executeCached(arg0Value, arg1Value, arg2Value, s0_.currentThreadId_, s0_.threadSuspension_);
                        return;
                    }
                    s0_ = s0_.next_;
                }
            }
            if ((state_0 & 2) != 0) {
                this.executeGeneric(arg0Value, arg1Value, arg2Value);
                return;
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void executeAndSpecialize(boolean arg0Value, Breakpoint.SessionList arg1Value, long arg2Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0 && arg1Value.next == null) {
                long currentThreadId__;
                int count0_ = 0;
                ExecuteCachedData s0_ = this.executeCached_cache;
                if ((state_0 & 1) != 0) {
                    while (s0_ != null && arg2Value != s0_.currentThreadId_) {
                        s0_ = s0_.next_;
                        ++count0_;
                    }
                }
                if (s0_ == null && arg2Value == (currentThreadId__ = SetThreadSuspensionEnabledNode.currentThreadId()) && count0_ < 10) {
                    s0_ = new ExecuteCachedData(this.executeCached_cache);
                    s0_.currentThreadId_ = currentThreadId__;
                    s0_.threadSuspension_ = this.getThreadSuspension(arg1Value);
                    VarHandle.storeStoreFence();
                    this.executeCached_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                }
                if (s0_ != null) {
                    lock.unlock();
                    hasLock = false;
                    this.executeCached(arg0Value, arg1Value, arg2Value, s0_.currentThreadId_, s0_.threadSuspension_);
                    return;
                }
            }
            this.exclude_ = exclude |= 1;
            this.executeCached_cache = null;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            this.executeGeneric(arg0Value, arg1Value, arg2Value);
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
        ExecuteCachedData s0_;
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.executeCached_cache) == null || s0_.next_ == null)) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    public static SetThreadSuspensionEnabledNode create() {
        return new SetThreadSuspensionEnabledNodeGen();
    }

    @GeneratedBy(value=SetThreadSuspensionEnabledNode.class)
    private static final class ExecuteCachedData {
        @CompilerDirectives.CompilationFinal
        ExecuteCachedData next_;
        @CompilerDirectives.CompilationFinal
        long currentThreadId_;
        @CompilerDirectives.CompilationFinal
        DebuggerSession.ThreadSuspension threadSuspension_;

        ExecuteCachedData(ExecuteCachedData next_) {
            this.next_ = next_;
        }
    }
}

