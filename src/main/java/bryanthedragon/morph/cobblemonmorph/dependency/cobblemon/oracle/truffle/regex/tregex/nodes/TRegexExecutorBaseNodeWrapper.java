
package com.oracle.truffle.regex.tregex.nodes;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorBaseNode;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorLocals;

@GeneratedBy(value=TRegexExecutorBaseNode.class)
final class TRegexExecutorBaseNodeWrapper
extends TRegexExecutorBaseNode
implements InstrumentableNode.WrapperNode {
    @Node.Child
    private TRegexExecutorBaseNode delegateNode;
    @Node.Child
    private ProbeNode probeNode;

    TRegexExecutorBaseNodeWrapper(TRegexExecutorBaseNode delegateNode, ProbeNode probeNode) {
        this.delegateNode = delegateNode;
        this.probeNode = probeNode;
    }

    @Override
    public TRegexExecutorBaseNode getDelegateNode() {
        return this.delegateNode;
    }

    @Override
    public ProbeNode getProbeNode() {
        return this.probeNode;
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.NONE;
    }

    @Override
    public Object execute(VirtualFrame frame, TRegexExecutorLocals locals, TruffleString.CodeRange codeRange, boolean tString) {
        Object returnValue;
        while (true) {
            boolean wasOnReturnExecuted = false;
            try {
                this.probeNode.onEnter(frame);
                returnValue = this.delegateNode.execute(frame, locals, codeRange, tString);
                wasOnReturnExecuted = true;
                this.probeNode.onReturnValue(frame, returnValue);
            }
            catch (Throwable t) {
                Object result;
                if ((result = this.probeNode.onReturnExceptionalOrUnwind(frame, t, wasOnReturnExecuted)) == ProbeNode.UNWIND_ACTION_REENTER) continue;
                if (result != null) {
                    returnValue = result;
                    break;
                }
                throw t;
            }
            break;
        }
        return returnValue;
    }
}

