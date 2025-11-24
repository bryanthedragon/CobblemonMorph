
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.tregex.automaton.AbstractState;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.nfa.ASTTransition;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;

public abstract class Term
extends RegexASTNode
implements AbstractState<Term, ASTTransition> {
    private int seqIndex = 0;

    Term() {
    }

    Term(Term copy) {
        super(copy);
    }

    @Override
    public abstract Term copy(RegexAST var1);

    @Override
    public abstract Term copyRecursive(RegexAST var1, CompilationBuffer var2);

    public int getSeqIndex() {
        return this.seqIndex;
    }

    public void setSeqIndex(int seqIndex) {
        this.seqIndex = seqIndex;
        if (seqIndex > Short.MAX_VALUE) {
            throw new UnsupportedRegexException("too many terms in a single sequence");
        }
    }

    @Override
    public RegexASTSubtreeRootNode getSubTreeParent() {
        RegexASTNode current = this;
        while (current.getParent() != null) {
            assert (current instanceof Term);
            if (current.getParent() instanceof RegexASTSubtreeRootNode) {
                return (RegexASTSubtreeRootNode)current.getParent();
            }
            current = current.getParent().getParent();
        }
        return null;
    }
}

