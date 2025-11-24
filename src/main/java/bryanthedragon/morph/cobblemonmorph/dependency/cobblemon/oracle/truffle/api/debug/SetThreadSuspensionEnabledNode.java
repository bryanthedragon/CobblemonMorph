
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.debug.Breakpoint;
import com.oracle.truffle.api.debug.DebuggerSession;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;

abstract class SetThreadSuspensionEnabledNode
extends Node {
    static final int CACHE_LIMIT = 10;

    SetThreadSuspensionEnabledNode() {
    }

    public final void execute(boolean suspensionEnabled, Breakpoint.SessionList sessions) {
        this.execute(suspensionEnabled, sessions, SetThreadSuspensionEnabledNode.currentThreadId());
    }

    protected abstract void execute(boolean var1, Breakpoint.SessionList var2, long var3);

    @Specialization(guards={"sessions.next == null", "threadId == currentThreadId"}, limit="CACHE_LIMIT")
    protected void executeCached(boolean suspensionEnabled, Breakpoint.SessionList sessions, long threadId, @Cached(value="currentThreadId()") long currentThreadId, @Cached(value="getThreadSuspension(sessions)") DebuggerSession.ThreadSuspension threadSuspension) {
        threadSuspension.enabled = suspensionEnabled;
    }

    @ExplodeLoop
    @Specialization(replaces={"executeCached"})
    protected void executeGeneric(boolean suspensionEnabled, Breakpoint.SessionList sessions, long threadId) {
        Breakpoint.SessionList current = sessions;
        while (current != null) {
            current.session.setThreadSuspendEnabled(suspensionEnabled);
            current = current.next;
        }
    }

    static long currentThreadId() {
        return Thread.currentThread().getId();
    }

    @CompilerDirectives.TruffleBoundary
    protected DebuggerSession.ThreadSuspension getThreadSuspension(Breakpoint.SessionList sessions) {
        assert (sessions.next == null);
        DebuggerSession.ThreadSuspension threadSuspension = new DebuggerSession.ThreadSuspension(true);
        sessions.session.threadSuspensions.set(threadSuspension);
        return threadSuspension;
    }
}

