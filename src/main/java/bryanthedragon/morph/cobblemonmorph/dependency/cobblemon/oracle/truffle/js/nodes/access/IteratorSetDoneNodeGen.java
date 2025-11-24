
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IteratorSetDoneNode;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;

@GeneratedBy(value=IteratorSetDoneNode.class)
public final class IteratorSetDoneNodeGen
extends IteratorSetDoneNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private IteratorSetDoneNodeGen(JavaScriptNode iteratorNode, JavaScriptNode isDoneNode) {
        super(iteratorNode, isDoneNode);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        boolean isDoneNodeValue_;
        int state_0 = this.state_0_;
        Object iteratorNodeValue_ = this.iteratorNode.execute(frameValue);
        try {
            isDoneNodeValue_ = this.isDoneNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(iteratorNodeValue_, ex.getResult());
        }
        if (state_0 != 0 && iteratorNodeValue_ instanceof IteratorRecord) {
            IteratorRecord iteratorNodeValue__ = (IteratorRecord)iteratorNodeValue_;
            return IteratorSetDoneNode.doIteratorStep(iteratorNodeValue__, isDoneNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(iteratorNodeValue_, isDoneNodeValue_);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frameValue) {
        boolean isDoneNodeValue_;
        int state_0 = this.state_0_;
        Object iteratorNodeValue_ = this.iteratorNode.execute(frameValue);
        try {
            isDoneNodeValue_ = this.isDoneNode.executeBoolean(frameValue);
        }
        catch (UnexpectedResultException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(iteratorNodeValue_, ex.getResult());
        }
        if (state_0 != 0 && iteratorNodeValue_ instanceof IteratorRecord) {
            IteratorRecord iteratorNodeValue__ = (IteratorRecord)iteratorNodeValue_;
            return IteratorSetDoneNode.doIteratorStep(iteratorNodeValue__, isDoneNodeValue_);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(iteratorNodeValue_, isDoneNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.executeBoolean(frameValue);
    }

    private boolean executeAndSpecialize(Object iteratorNodeValue, Object isDoneNodeValue) {
        int state_0 = this.state_0_;
        if (iteratorNodeValue instanceof IteratorRecord) {
            IteratorRecord iteratorNodeValue_ = (IteratorRecord)iteratorNodeValue;
            if (isDoneNodeValue instanceof Boolean) {
                boolean isDoneNodeValue_ = (Boolean)isDoneNodeValue;
                this.state_0_ = state_0 |= 1;
                return IteratorSetDoneNode.doIteratorStep(iteratorNodeValue_, isDoneNodeValue_);
            }
        }
        throw new UnsupportedSpecializationException(this, new Node[]{this.iteratorNode, this.isDoneNode}, iteratorNodeValue, isDoneNodeValue);
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
        s[0] = "doIteratorStep";
        s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static IteratorSetDoneNode create(JavaScriptNode iteratorNode, JavaScriptNode isDoneNode) {
        return new IteratorSetDoneNodeGen(iteratorNode, isDoneNode);
    }
}

