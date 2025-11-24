
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.JSAgent;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class JSAgentWaiterList {
    private final Map<Integer, JSAgentWaiterListEntry> waiters = new ConcurrentHashMap<Integer, JSAgentWaiterListEntry>();
    private final ReentrantLock atomicSection = new ReentrantLock();

    @CompilerDirectives.TruffleBoundary
    public JSAgentWaiterList() {
    }

    @CompilerDirectives.TruffleBoundary
    public JSAgentWaiterListEntry getListForIndex(int indexPos) {
        JSAgentWaiterListEntry list = Boundaries.mapPutIfAbsent(this.waiters, indexPos, new JSAgentWaiterListEntry());
        if (list == null) {
            return Boundaries.mapGet(this.waiters, indexPos);
        }
        return list;
    }

    @CompilerDirectives.TruffleBoundary
    public void enterAtomicSection() {
        assert (!this.inAtomicSection());
        this.atomicSection.lock();
    }

    @CompilerDirectives.TruffleBoundary
    public void leaveAtomicSection() {
        assert (this.inAtomicSection());
        this.atomicSection.unlock();
    }

    public boolean inAtomicSection() {
        return this.atomicSection.isHeldByCurrentThread();
    }

    public static final class WaiterRecord {
        private final int agentSignifier;
        private final PromiseCapabilityRecord promiseCapability;
        private final double timeout;
        private TruffleString result;
        private final JSAgentWaiterListEntry wl;
        private final JSAgent agent;
        private long creationTimestamp;
        private boolean notified;

        private WaiterRecord(int agentSignifier, PromiseCapabilityRecord promiseCapability, double timeout, TruffleString result, JSAgentWaiterListEntry wl, JSAgent agent) {
            this.agentSignifier = agentSignifier;
            this.promiseCapability = promiseCapability;
            this.timeout = timeout;
            this.result = result;
            this.wl = wl;
            this.agent = agent;
            this.notified = false;
        }

        public static WaiterRecord create(int agentSignifier, PromiseCapabilityRecord promiseCapability, double timeout, TruffleString result, JSAgentWaiterListEntry wl, JSAgent agent) {
            return new WaiterRecord(agentSignifier, promiseCapability, timeout, result, wl, agent);
        }

        public int getAgentSignifier() {
            return this.agentSignifier;
        }

        public PromiseCapabilityRecord getPromiseCapability() {
            return this.promiseCapability;
        }

        public double getTimeout() {
            return this.timeout;
        }

        public TruffleString getResult() {
            return this.result;
        }

        public void setResult(TruffleString result) {
            this.result = result;
        }

        public JSAgentWaiterListEntry getWaiterListEntry() {
            return this.wl;
        }

        public void setCreationTime(long timeMillis) {
            this.creationTimestamp = timeMillis;
        }

        public long getCreationTime() {
            return this.creationTimestamp;
        }

        public void setNotified() {
            assert (this.wl.inCriticalSection());
            assert (!this.notified);
            this.notified = true;
        }

        public boolean isNotified() {
            assert (this.wl.inCriticalSection());
            return this.notified;
        }

        public boolean isReadyToResolve() {
            assert (this.wl.inCriticalSection());
            return this.notified || this.isTimedOut();
        }

        private boolean isTimedOut() {
            assert (!this.notified);
            long current = System.nanoTime() / 1000000L;
            long elapsed = current - this.creationTimestamp;
            return (double)elapsed >= this.timeout;
        }

        public void enqueueInAgent() {
            this.agent.enqueueWaitAsyncPromiseJob(this);
        }

        public JSAgent getAgent() {
            return this.agent;
        }
    }

    public static final class JSAgentWaiterListEntry
    extends ConcurrentLinkedQueue<WaiterRecord> {
        private static final long serialVersionUID = 2655886588267252886L;
        private final ReentrantLock criticalSection = new ReentrantLock();
        private final Condition waitCondition = this.criticalSection.newCondition();

        @CompilerDirectives.TruffleBoundary
        public JSAgentWaiterListEntry() {
        }

        @CompilerDirectives.TruffleBoundary
        public void enterCriticalSection() {
            assert (!this.inCriticalSection());
            this.criticalSection.lock();
        }

        @CompilerDirectives.TruffleBoundary
        public void leaveCriticalSection() {
            assert (this.inCriticalSection());
            this.criticalSection.unlock();
        }

        public Condition getCondition() {
            return this.waitCondition;
        }

        @CompilerDirectives.TruffleBoundary
        public boolean inCriticalSection() {
            return this.criticalSection.isHeldByCurrentThread();
        }
    }
}

