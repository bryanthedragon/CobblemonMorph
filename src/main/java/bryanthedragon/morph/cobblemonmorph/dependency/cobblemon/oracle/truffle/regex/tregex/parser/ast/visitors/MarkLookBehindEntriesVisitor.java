
package com.oracle.truffle.regex.tregex.parser.ast.visitors;

import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.LookAheadAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.MatchFound;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.NFATraversalRegexASTVisitor;

public class MarkLookBehindEntriesVisitor
extends NFATraversalRegexASTVisitor {
    private StateSet<RegexAST, CharacterClass> curEntriesFound;
    private StateSet<RegexAST, CharacterClass> newEntriesFound;
    private StateSet<RegexAST, LookAheadAssertion> curLookAheadBoundariesHit;
    private StateSet<RegexAST, LookAheadAssertion> newLookAheadBoundariesHit;

    public MarkLookBehindEntriesVisitor(RegexAST ast) {
        super(ast);
        this.curEntriesFound = StateSet.create(ast);
        this.newEntriesFound = StateSet.create(ast);
        this.curLookAheadBoundariesHit = StateSet.create(ast);
        this.newLookAheadBoundariesHit = StateSet.create(ast);
        this.setCanTraverseCaret(false);
        this.setReverse();
    }

    public void run() {
        for (RegexASTSubtreeRootNode subtreeRootNode : this.ast.getSubtrees()) {
            if (!subtreeRootNode.isLookBehindAssertion()) continue;
            LookBehindAssertion lb = subtreeRootNode.asLookBehindAssertion();
            this.run(lb);
            this.movePastLookAheadBoundaries();
            int curDepth = 1;
            while (!this.newEntriesFound.isEmpty() && curDepth < lb.getLiteralLength()) {
                ++curDepth;
                StateSet<RegexAST, CharacterClass> tmp = this.curEntriesFound;
                this.curEntriesFound = this.newEntriesFound;
                this.newEntriesFound = tmp;
                this.newEntriesFound.clear();
                for (CharacterClass cc : this.curEntriesFound) {
                    this.run(cc);
                }
                this.movePastLookAheadBoundaries();
            }
            for (CharacterClass t : this.newEntriesFound) {
                t.addLookBehindEntry(this.ast, lb);
            }
            this.curEntriesFound.clear();
            this.newEntriesFound.clear();
        }
    }

    private void movePastLookAheadBoundaries() {
        while (!this.newLookAheadBoundariesHit.isEmpty()) {
            StateSet<RegexAST, LookAheadAssertion> tmp = this.curLookAheadBoundariesHit;
            this.curLookAheadBoundariesHit = this.newLookAheadBoundariesHit;
            this.newLookAheadBoundariesHit = tmp;
            this.newLookAheadBoundariesHit.clear();
            for (LookAheadAssertion la : this.curLookAheadBoundariesHit) {
                this.run(la);
            }
        }
    }

    @Override
    protected void visit(RegexASTNode target) {
        if (target instanceof CharacterClass) {
            CharacterClass cc = (CharacterClass)target;
            this.newEntriesFound.add(cc);
        } else {
            assert (target instanceof MatchFound);
            MatchFound mf = (MatchFound)target;
            if (!(mf.getSubTreeParent() instanceof RegexASTRootNode)) {
                assert (mf.getSubTreeParent() instanceof LookAheadAssertion) : "this visitor does not support nested look-behind assertions!";
                this.newLookAheadBoundariesHit.add((LookAheadAssertion)mf.getSubTreeParent());
            }
        }
    }

    @Override
    protected void enterLookAhead(LookAheadAssertion assertion) {
    }

    @Override
    protected void leaveLookAhead(LookAheadAssertion assertion) {
    }

    @Override
    protected boolean canTraverseLookArounds() {
        return true;
    }
}

