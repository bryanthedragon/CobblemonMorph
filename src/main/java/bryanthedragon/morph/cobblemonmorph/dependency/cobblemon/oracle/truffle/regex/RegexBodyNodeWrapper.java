
package com.oracle.truffle.regex;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.regex.RegexBodyNode;

@GeneratedBy(value=RegexBodyNode.class)
final class RegexBodyNodeWrapper
extends RegexBodyNode
implements InstrumentableNode.WrapperNode {
    @Node.Child
    private RegexBodyNode delegateNode;
    @Node.Child
    private ProbeNode probeNode;

    RegexBodyNodeWrapper(RegexBodyNode copy, RegexBodyNode delegateNode, ProbeNode probeNode) {
        super(copy);
        this.delegateNode = delegateNode;
        this.probeNode = probeNode;
    }

    @Override
    public RegexBodyNode getDelegateNode() {
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
    public Object execute(VirtualFrame frame) {
        Object returnValue;
        while (true) {
            boolean wasOnReturnExecuted = false;
            try {
                this.probeNode.onEnter(frame);
                returnValue = this.delegateNode.execute(frame);
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

