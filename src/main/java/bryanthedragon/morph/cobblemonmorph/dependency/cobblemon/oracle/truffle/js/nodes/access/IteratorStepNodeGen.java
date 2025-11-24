
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IteratorCompleteNode;
import com.oracle.truffle.js.nodes.access.IteratorNextNode;
import com.oracle.truffle.js.nodes.access.IteratorStepNode;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=IteratorStepNode.class)
public final class IteratorStepNodeGen
extends IteratorStepNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private IteratorNextNode iteratorNextNode_;
    @Node.Child
    private IteratorCompleteNode iteratorCompleteNode_;

    private IteratorStepNodeGen() {
    }

    @Override
    public Object execute(IteratorRecord arg0Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            return IteratorStepNode.step(arg0Value, this.iteratorNextNode_, this.iteratorCompleteNode_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(IteratorRecord arg0Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            this.iteratorNextNode_ = super.insert(IteratorNextNode.create());
            this.iteratorCompleteNode_ = super.insert(IteratorCompleteNode.create(this.getLanguage().getJSContext()));
            this.state_0_ = state_0 |= 1;
            lock.unlock();
            hasLock = false;
            Object object = IteratorStepNode.step(arg0Value, this.iteratorNextNode_, this.iteratorCompleteNode_);
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
        s[0] = "step";
        if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
            cached.add(Arrays.asList(this.iteratorNextNode_, this.iteratorCompleteNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static IteratorStepNode create() {
        return new IteratorStepNodeGen();
    }

    public static IteratorStepNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=IteratorStepNode.class)
    @DenyReplace
    private static final class Uncached
    extends IteratorStepNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object execute(IteratorRecord arg0Value) {
            return IteratorStepNode.step(arg0Value, IteratorNextNode.getUncached(), IteratorCompleteNode.getUncached());
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
}

