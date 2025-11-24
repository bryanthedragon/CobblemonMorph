
package com.oracle.truffle.regex.tregex.nodes.nfa;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;

public abstract class TRegexBacktrackerSubExecutorNode
extends TRegexExecutorNode {
    public static final TRegexBacktrackerSubExecutorNode[] NO_SUB_EXECUTORS = new TRegexBacktrackerSubExecutorNode[0];
    @Node.Children
    protected final TRegexBacktrackerSubExecutorNode[] subExecutors;

    TRegexBacktrackerSubExecutorNode(RegexAST ast, int numberOfTransitions, TRegexBacktrackerSubExecutorNode[] subExecutors) {
        super(ast, numberOfTransitions);
        this.subExecutors = subExecutors;
    }

    TRegexBacktrackerSubExecutorNode(TRegexBacktrackerSubExecutorNode copy) {
        super(copy);
        if (copy.subExecutors == null) {
            this.subExecutors = null;
        } else {
            TRegexBacktrackerSubExecutorNode[] subExecutorsCopy = new TRegexBacktrackerSubExecutorNode[copy.subExecutors.length];
            for (int i = 0; i < copy.subExecutors.length; ++i) {
                subExecutorsCopy[i] = copy.subExecutors[i].shallowCopy();
            }
            this.subExecutors = subExecutorsCopy;
        }
    }

    @Override
    public abstract TRegexBacktrackerSubExecutorNode shallowCopy();
}

