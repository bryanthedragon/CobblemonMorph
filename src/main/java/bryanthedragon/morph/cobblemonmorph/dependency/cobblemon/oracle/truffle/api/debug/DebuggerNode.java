
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.debug.Breakpoint;
import com.oracle.truffle.api.debug.ChangedReturnInfo;
import com.oracle.truffle.api.debug.DebuggerSession;
import com.oracle.truffle.api.debug.InsertableNode;
import com.oracle.truffle.api.debug.SetThreadSuspensionEnabledNode;
import com.oracle.truffle.api.debug.SuspendAnchor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.instrumentation.ExecutionEventNode;
import com.oracle.truffle.api.nodes.Node;
import java.util.Set;
import java.util.concurrent.Callable;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.EconomicSet;

abstract class DebuggerNode
extends ExecutionEventNode
implements InsertableNode {
    protected final EventContext context;
    private volatile boolean singleThreadSession = true;
    private volatile long cachedThreadId;
    private DebuggerSession cachedSessionDuplicate;
    private volatile EconomicMap<Thread, Object> duplicateInThreads;
    private volatile Assumption noDuplicateAssumption = Truffle.getRuntime().createAssumption("No duplicate node assumption");

    DebuggerNode(EventContext context) {
        this.context = context;
    }

    Breakpoint getBreakpoint() {
        return null;
    }

    abstract boolean isStepNode();

    abstract Set<SuspendAnchor> getSuspendAnchors();

    abstract boolean isActiveAt(SuspendAnchor var1);

    final EventContext getContext() {
        return this.context;
    }

    @Override
    public void setParentOf(Node child) {
        this.insert(child);
    }

    @Override
    protected Object onUnwind(VirtualFrame frame, Object info) {
        if (info instanceof ChangedReturnInfo) {
            return ((ChangedReturnInfo)info).returnValue;
        }
        return super.onUnwind(frame, info);
    }

    void markAsDuplicate(final DebuggerSession session) {
        CompilerAsserts.neverPartOfCompilation();
        this.noDuplicateAssumption.invalidate();
        if (this.singleThreadSession) {
            Boolean marked;
            final long threadId = SetThreadSuspensionEnabledNode.currentThreadId();
            if (this.cachedThreadId == threadId && this.cachedSessionDuplicate == null) {
                this.cachedSessionDuplicate = session;
                return;
            }
            if (this.cachedThreadId == 0L && (marked = this.atomic(new Callable<Boolean>(){

                @Override
                public Boolean call() {
                    if (DebuggerNode.this.cachedThreadId == 0L) {
                        DebuggerNode.this.cachedThreadId = threadId;
                        DebuggerNode.this.cachedSessionDuplicate = session;
                        return true;
                    }
                    return false;
                }
            })).booleanValue()) {
                return;
            }
        }
        this.singleThreadSession = false;
        this.markAsDuplicateSlowPath(session);
    }

    private void markAsDuplicateSlowPath(final DebuggerSession session) {
        this.atomic(new Runnable(){

            @Override
            public void run() {
                Thread thread;
                Object sessions;
                if (DebuggerNode.this.duplicateInThreads == null) {
                    DebuggerNode.this.duplicateInThreads = EconomicMap.create();
                }
                if ((sessions = DebuggerNode.this.duplicateInThreads.get(thread = Thread.currentThread())) == null) {
                    DebuggerNode.this.duplicateInThreads.put(thread, session);
                } else if (sessions instanceof DebuggerSession) {
                    EconomicSet<DebuggerSession> set2 = EconomicSet.create();
                    set2.add((DebuggerSession)sessions);
                    set2.add(session);
                    DebuggerNode.this.duplicateInThreads.put(thread, set2);
                } else {
                    ((EconomicSet)sessions).add(session);
                }
            }
        });
    }

    boolean consumeIsDuplicate(DebuggerSession session) {
        if (this.noDuplicateAssumption.isValid()) {
            return false;
        }
        if (this.cachedThreadId == SetThreadSuspensionEnabledNode.currentThreadId() && this.cachedSessionDuplicate == session) {
            this.cachedSessionDuplicate = null;
            if (this.singleThreadSession) {
                this.noDuplicateAssumption = Truffle.getRuntime().createAssumption("No duplicate node assumption");
            }
            return true;
        }
        if (this.singleThreadSession) {
            return false;
        }
        return this.isDuplicateSlowPath(session);
    }

    @CompilerDirectives.TruffleBoundary
    private boolean isDuplicateSlowPath(final DebuggerSession session) {
        return this.atomic(new Callable<Boolean>(){

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public Boolean call() {
                if (DebuggerNode.this.duplicateInThreads != null) {
                    try {
                        Thread thread = Thread.currentThread();
                        Object sessions = DebuggerNode.this.duplicateInThreads.get(thread);
                        if (sessions == session) {
                            DebuggerNode.this.duplicateInThreads.removeKey(thread);
                            Boolean bl = true;
                            return bl;
                        }
                        if (sessions instanceof EconomicSet) {
                            EconomicSet set2 = (EconomicSet)sessions;
                            boolean contains = set2.contains(session);
                            if (contains) {
                                set2.remove(session);
                                if (set2.isEmpty()) {
                                    DebuggerNode.this.duplicateInThreads.removeKey(thread);
                                }
                            }
                            Boolean bl = contains;
                            return bl;
                        }
                    }
                    finally {
                        if (DebuggerNode.this.duplicateInThreads.isEmpty()) {
                            DebuggerNode.this.duplicateInThreads = null;
                            DebuggerNode.this.singleThreadSession = true;
                            if (DebuggerNode.this.cachedSessionDuplicate == null) {
                                DebuggerNode.this.cachedThreadId = 0L;
                                DebuggerNode.this.noDuplicateAssumption = Truffle.getRuntime().createAssumption("No duplicate node assumption");
                            }
                        }
                    }
                }
                return false;
            }
        });
    }

    static interface InputValuesProvider {
        public Object[] getDebugInputValues(MaterializedFrame var1);
    }
}

