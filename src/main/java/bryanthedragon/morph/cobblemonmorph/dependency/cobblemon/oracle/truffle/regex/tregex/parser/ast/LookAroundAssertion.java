
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.Term;

public abstract class LookAroundAssertion
extends RegexASTSubtreeRootNode {
    LookAroundAssertion(boolean negated) {
        this.setNegated(negated);
    }

    LookAroundAssertion(LookAroundAssertion copy, RegexAST ast) {
        super(copy, ast);
    }

    LookAroundAssertion(LookAroundAssertion copy, RegexAST ast, CompilationBuffer compilationBuffer) {
        super(copy, ast, compilationBuffer);
    }

    public boolean isNegated() {
        return this.isFlagSet(2048);
    }

    public void setNegated(boolean negated) {
        this.setFlag(2048, negated);
    }

    boolean groupEqualsSemantic(LookAroundAssertion o) {
        return this.isNegated() == o.isNegated() && this.getGroup().equalsSemantic(o.getGroup());
    }

    public boolean startsWithCharClass() {
        if (this.getGroup().size() != 1 || this.getGroup().getFirstAlternative().isEmpty()) {
            return false;
        }
        return this.getGroup().getFirstAlternative().getFirstTerm().isCharacterClass();
    }

    public boolean endsWithCharClass() {
        if (this.getGroup().size() != 1 || this.getGroup().getFirstAlternative().isEmpty()) {
            return false;
        }
        return this.getGroup().getFirstAlternative().getLastTerm().isCharacterClass();
    }

    public boolean isLiteral() {
        if (this.getGroup().size() != 1) {
            return false;
        }
        for (Term t : this.getGroup().getFirstAlternative().getTerms()) {
            if (t.isCharacterClass()) continue;
            return false;
        }
        return true;
    }

    public int getLiteralLength() {
        assert (this.isLiteral());
        return this.getGroup().getFirstAlternative().getTerms().size();
    }

    public boolean isSingleCCNonCapturingLiteral() {
        return this.getGroup().size() == 1 && this.getGroup().getFirstAlternative().size() == 1 && this.getGroup().getFirstAlternative().getFirstTerm().isCharacterClass() && !this.getGroup().isCapturing();
    }
}

