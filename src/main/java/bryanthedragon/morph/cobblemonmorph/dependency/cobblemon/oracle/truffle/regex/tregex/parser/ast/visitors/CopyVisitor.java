
package com.oracle.truffle.regex.tregex.parser.ast.visitors;

import com.oracle.truffle.regex.tregex.parser.ast.AtomicGroup;
import com.oracle.truffle.regex.tregex.parser.ast.BackReference;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.LookAheadAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.SubexpressionCall;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.DepthFirstTraversalRegexASTVisitor;

public class CopyVisitor
extends DepthFirstTraversalRegexASTVisitor {
    private final RegexAST ast;
    private Term copyRoot;
    private RegexASTNode curParent;

    public CopyVisitor(RegexAST ast) {
        this.ast = ast;
    }

    public Term copy(Term term) {
        this.run(term);
        assert (this.copyRoot != null);
        Term result = this.copyRoot;
        this.copyRoot = null;
        return result;
    }

    @Override
    protected void visit(BackReference backReference) {
        this.doCopy(backReference);
    }

    @Override
    protected void visit(Group group) {
        this.curParent = this.doCopy(group);
    }

    @Override
    protected void leave(Group group) {
        this.goToUpperParent();
    }

    @Override
    protected void visit(Sequence sequence) {
        Sequence copy = sequence.copy(this.ast);
        ((Group)this.curParent).add(copy);
        this.curParent = copy;
    }

    @Override
    protected void leave(Sequence sequence) {
        this.goToUpperParent();
    }

    @Override
    protected void visit(PositionAssertion assertion) {
        this.doCopy(assertion);
    }

    @Override
    protected void visit(LookBehindAssertion assertion) {
        this.curParent = this.doCopy(assertion);
    }

    @Override
    protected void leave(LookBehindAssertion assertion) {
        this.goToUpperParent();
    }

    @Override
    protected void visit(LookAheadAssertion assertion) {
        this.curParent = this.doCopy(assertion);
    }

    @Override
    protected void leave(LookAheadAssertion assertion) {
        this.goToUpperParent();
    }

    @Override
    protected void visit(AtomicGroup atomicGroup) {
        this.curParent = this.doCopy(atomicGroup);
    }

    @Override
    protected void leave(AtomicGroup atomicGroup) {
        this.goToUpperParent();
    }

    @Override
    protected void visit(CharacterClass characterClass) {
        this.doCopy(characterClass);
    }

    @Override
    protected void visit(SubexpressionCall subexpressionCall) {
        this.doCopy(subexpressionCall);
    }

    private void goToUpperParent() {
        assert (this.curParent != null);
        this.curParent = this.curParent.getParent();
    }

    private Term doCopy(Term t) {
        Term copy = t.copy(this.ast);
        this.ast.addSourceSections(copy, this.ast.getSourceSections(t));
        if (this.curParent == null) {
            assert (this.copyRoot == null);
            this.copyRoot = copy;
        } else if (this.curParent instanceof RegexASTSubtreeRootNode) {
            assert (copy instanceof Group);
            ((RegexASTSubtreeRootNode)this.curParent).setGroup((Group)copy);
        } else {
            ((Sequence)this.curParent).add(copy);
        }
        return copy;
    }
}

