
package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.regex.RegexBodyNode;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.result.RegexResult;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorEntryNode;

public class TRegexLazyFindStartRootNode
extends RegexBodyNode {
    private final boolean setResult;
    @Node.Child
    private TRegexExecutorEntryNode entryNode;

    public TRegexLazyFindStartRootNode(RegexLanguage language, RegexSource source, TRegexExecutorEntryNode backwardNode, boolean setResult) {
        super(language, source);
        this.setResult = setResult;
        this.entryNode = this.insert(backwardNode);
    }

    @Override
    public final Object execute(VirtualFrame frame) {
        Object[] args = frame.getArguments();
        assert (args.length == 1);
        RegexResult receiver = (RegexResult)args[0];
        int start2 = (Integer)this.entryNode.execute(frame, receiver.getInput(), receiver.getFromIndex(), receiver.getEnd(), receiver.getEnd());
        if (this.setResult) {
            receiver.setResult(new int[]{start2, receiver.getEnd(), -1});
        }
        return start2;
    }

    @Override
    public String getEngineLabel() {
        return "TRegex bck";
    }
}

