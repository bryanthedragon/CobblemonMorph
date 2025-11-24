
package com.oracle.truffle.regex.tregex.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorBaseNodeWrapper;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorLocals;

@GenerateWrapper
public abstract class TRegexExecutorBaseNode
extends Node
implements InstrumentableNode {
    public abstract Object execute(VirtualFrame var1, TRegexExecutorLocals var2, TruffleString.CodeRange var3, boolean var4);

    @Override
    public final boolean isInstrumentable() {
        return true;
    }

    @Override
    public InstrumentableNode.WrapperNode createWrapper(ProbeNode probeNode) {
        return new TRegexExecutorBaseNodeWrapper(this, probeNode);
    }
}

