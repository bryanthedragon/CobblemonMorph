
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IteratorGetNextValueNode;
import com.oracle.truffle.js.nodes.access.IteratorToArrayNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=IteratorToArrayNode.class)
public final class IteratorToArrayNodeGen
extends IteratorToArrayNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private BranchProfile iterator_growProfile_;

    private IteratorToArrayNodeGen(JSContext context, JavaScriptNode iteratorNode, IteratorGetNextValueNode iteratorStepNode) {
        super(context, iteratorNode, iteratorStepNode);
    }

    @Override
    public Object execute(VirtualFrame frameValue, IteratorRecord iteratorNodeValue) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && !iteratorNodeValue.isDone()) {
                return this.doIterator(frameValue, iteratorNodeValue, this.iterator_growProfile_);
            }
            if ((state_0 & 2) != 0 && iteratorNodeValue.isDone()) {
                return this.doDoneIterator(iteratorNodeValue);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(frameValue, iteratorNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object iteratorNodeValue_ = this.iteratorNode.execute(frameValue);
        if (state_0 != 0 && iteratorNodeValue_ instanceof IteratorRecord) {
            IteratorRecord iteratorNodeValue__ = (IteratorRecord)iteratorNodeValue_;
            if ((state_0 & 1) != 0 && !iteratorNodeValue__.isDone()) {
                return this.doIterator(frameValue, iteratorNodeValue__, this.iterator_growProfile_);
            }
            if ((state_0 & 2) != 0 && iteratorNodeValue__.isDone()) {
                return this.doDoneIterator(iteratorNodeValue__);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(frameValue, iteratorNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object iteratorNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (iteratorNodeValue instanceof IteratorRecord) {
                IteratorRecord iteratorNodeValue_ = (IteratorRecord)iteratorNodeValue;
                if (!iteratorNodeValue_.isDone()) {
                    this.iterator_growProfile_ = BranchProfile.create();
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doIterator(frameValue, iteratorNodeValue_, this.iterator_growProfile_);
                    return object;
                }
                if (iteratorNodeValue_.isDone()) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doDoneIterator(iteratorNodeValue_);
                    return object;
                }
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.iteratorNode}, iteratorNodeValue);
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
        if ((state_0 & state_0 - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "doIterator";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<List<BranchProfile>> cached = new ArrayList<List<BranchProfile>>();
            cached.add(Arrays.asList(this.iterator_growProfile_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "doDoneIterator";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static IteratorToArrayNode create(JSContext context, JavaScriptNode iteratorNode, IteratorGetNextValueNode iteratorStepNode) {
        return new IteratorToArrayNodeGen(context, iteratorNode, iteratorStepNode);
    }
}

