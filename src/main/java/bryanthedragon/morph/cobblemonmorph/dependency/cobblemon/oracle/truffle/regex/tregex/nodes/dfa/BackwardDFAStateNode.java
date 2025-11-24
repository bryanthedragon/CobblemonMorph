
package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.regex.tregex.nodes.dfa.DFASimpleCG;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.Matchers;

public class BackwardDFAStateNode
extends DFAStateNode {
    public BackwardDFAStateNode(short id, byte flags, short loopTransitionIndex, DFAStateNode.IndexOfCall indexOfCall, short[] successors, Matchers matchers, DFASimpleCG simpleCG) {
        super(id, flags, loopTransitionIndex, indexOfCall, successors, matchers, simpleCG);
    }

    protected BackwardDFAStateNode(BackwardDFAStateNode copy, short copyID) {
        super(copy, copyID);
    }

    @Override
    public DFAStateNode createNodeSplitCopy(short copyID) {
        return new BackwardDFAStateNode(this, copyID);
    }

    int getBackwardPrefixStateIndex() {
        assert (this.hasBackwardPrefixState());
        return this.getSuccessors().length - 1;
    }
}

