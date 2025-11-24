
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.parser.Token;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.Term;

public abstract class QuantifiableTerm
extends Term {
    private Token.Quantifier quantifier;

    QuantifiableTerm() {
    }

    QuantifiableTerm(QuantifiableTerm copy) {
        super(copy);
        this.quantifier = copy.quantifier;
    }

    @Override
    public abstract QuantifiableTerm copy(RegexAST var1);

    public boolean hasQuantifier() {
        return this.quantifier != null;
    }

    public boolean hasNotUnrolledQuantifier() {
        return this.hasQuantifier() && !this.isExpandedQuantifier();
    }

    public abstract boolean isUnrollingCandidate();

    public Token.Quantifier getQuantifier() {
        return this.quantifier;
    }

    public void setQuantifier(Token.Quantifier quantifier) {
        this.quantifier = quantifier;
    }

    boolean quantifierEquals(QuantifiableTerm o) {
        if (this.quantifier == null) {
            return o.quantifier == null;
        }
        if (o.quantifier == null) {
            return this.quantifier == null;
        }
        return this.quantifier.equalsSemantic(o.quantifier);
    }

    @Override
    public boolean equalsSemantic(RegexASTNode obj) {
        return this.equalsSemantic(obj, false);
    }

    public abstract boolean equalsSemantic(RegexASTNode var1, boolean var2);

    @CompilerDirectives.TruffleBoundary
    protected String quantifierToString() {
        return this.hasNotUnrolledQuantifier() ? this.quantifier.toString() : "";
    }

    @Override
    public RegexASTSubtreeRootNode getSubTreeParent() {
        RegexASTNode current = this;
        while (current.getParent() != null) {
            assert (current instanceof QuantifiableTerm);
            if (current.getParent() instanceof RegexASTSubtreeRootNode) {
                return (RegexASTSubtreeRootNode)current.getParent();
            }
            current = current.getParent().getParent();
        }
        return null;
    }
}

