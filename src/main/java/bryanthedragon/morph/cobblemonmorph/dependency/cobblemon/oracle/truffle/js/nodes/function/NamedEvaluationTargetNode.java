
package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.NamedEvaluationTargetNodeWrapper;

@GenerateWrapper
public abstract class NamedEvaluationTargetNode
extends JavaScriptNode {
    public abstract Object executeWithName(VirtualFrame var1, Object var2);

    @Override
    public InstrumentableNode.WrapperNode createWrapper(ProbeNode probe) {
        return new NamedEvaluationTargetNodeWrapper(this, probe);
    }
}

