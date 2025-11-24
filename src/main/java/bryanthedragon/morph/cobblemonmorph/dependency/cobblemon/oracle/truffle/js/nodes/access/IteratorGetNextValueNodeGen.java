
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IteratorGetNextValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;

@GeneratedBy(value=IteratorGetNextValueNode.class)
public final class IteratorGetNextValueNodeGen
extends IteratorGetNextValueNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private IteratorGetNextValueNodeGen(JSContext context, JavaScriptNode iteratorNode, JavaScriptNode doneNode, boolean setDone, boolean readValue) {
        super(context, iteratorNode, doneNode, setDone, readValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue, IteratorRecord iteratorNodeValue) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            return this.iteratorStepAndGetValue(frameValue, iteratorNodeValue);
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
            return this.iteratorStepAndGetValue(frameValue, iteratorNodeValue__);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(frameValue, iteratorNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    private Object executeAndSpecialize(VirtualFrame frameValue, Object iteratorNodeValue) {
        int state_0 = this.state_0_;
        if (iteratorNodeValue instanceof IteratorRecord) {
            IteratorRecord iteratorNodeValue_ = (IteratorRecord)iteratorNodeValue;
            this.state_0_ = state_0 |= 1;
            return this.iteratorStepAndGetValue(frameValue, iteratorNodeValue_);
        }
        throw new UnsupportedSpecializationException(this, new Node[]{this.iteratorNode}, iteratorNodeValue);
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
        s[0] = "iteratorStepAndGetValue";
        s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static IteratorGetNextValueNode create(JSContext context, JavaScriptNode iteratorNode, JavaScriptNode doneNode, boolean setDone, boolean readValue) {
        return new IteratorGetNextValueNodeGen(context, iteratorNode, doneNode, setDone, readValue);
    }
}

