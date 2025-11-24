
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.Constants;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.AtomicGroup;
import com.oracle.truffle.regex.tregex.parser.ast.BackReference;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.LookAheadAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookAroundAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.QuantifiableTerm;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.SubexpressionCall;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.DepthFirstTraversalRegexASTVisitor;
import com.oracle.truffle.regex.tregex.string.Encodings;

public class CalcASTPropsVisitor
extends DepthFirstTraversalRegexASTVisitor {
    private static final int AND_FLAGS = 50;
    private static final int OR_FLAGS = 258060;
    private static final int CHANGED_FLAGS = 258110;
    private final RegexAST ast;
    private final CompilationBuffer compilationBuffer;

    public CalcASTPropsVisitor(RegexAST ast, CompilationBuffer compilationBuffer) {
        this.ast = ast;
        this.compilationBuffer = compilationBuffer;
    }

    public static void run(RegexAST ast, CompilationBuffer compilationBuffer) {
        CalcASTPropsVisitor visitor = new CalcASTPropsVisitor(ast, compilationBuffer);
        visitor.runReverse(ast.getRoot());
        visitor.run(ast.getRoot());
    }

    @Override
    protected void init(RegexASTNode runRoot) {
        runRoot.setMinPath(0);
        runRoot.setMaxPath(0);
    }

    @Override
    protected void visit(BackReference backReference) {
        this.ast.getProperties().setBackReferences();
        backReference.setHasBackReferences();
        backReference.getParent().setHasBackReferences();
        if (backReference.hasQuantifier()) {
            this.setZeroWidthQuantifierIndex(backReference);
        }
        if (backReference.hasNotUnrolledQuantifier()) {
            backReference.getParent().setHasQuantifiers();
            this.setQuantifierIndex(backReference);
        }
        backReference.setMinPath(backReference.getParent().getMinPath());
        backReference.setMaxPath(backReference.getParent().getMaxPath());
    }

    @Override
    protected void visit(Group group) {
        if (group.getParent().isSequence() || group.getParent().isAtomicGroup()) {
            group.setMinPath(group.getParent().getMinPath());
            group.setMaxPath(group.getParent().getMaxPath());
        } else {
            assert (group.getParent().isLookAroundAssertion() || group.getParent().isRoot());
            group.setMinPath(0);
            group.setMaxPath(0);
        }
    }

    @Override
    protected void leave(Group group) {
        if (group.size() > 1) {
            this.ast.getProperties().setAlternations();
        }
        if (group.getGroupNumber() > 0) {
            this.ast.getProperties().setCaptureGroups();
        }
        if (group.isDead()) {
            if (group.getParent() != null) {
                group.getParent().markAsDead();
            }
            return;
        }
        int minPath = Integer.MAX_VALUE;
        int maxPath = 0;
        int flags = (group.isLoop() ? 4096 : 0) | 0x32;
        for (Sequence s : group.getAlternatives()) {
            if (s.isDead()) continue;
            flags = flags & (s.getFlags(50) | 0xFFFFFFCD) | s.getFlags(258060);
            minPath = Math.min(minPath, s.getMinPath());
            maxPath = Math.max(maxPath, s.getMaxPath());
        }
        if (group.hasQuantifier()) {
            if (!group.isExpandedQuantifier()) {
                flags |= 0x4000;
                this.setQuantifierIndex(group);
                if (group.getQuantifier().getMin() == 0) {
                    flags &= 0xFFFFFFCF;
                }
                minPath = group.getMinPath() + (minPath - group.getMinPath()) * group.getQuantifier().getMin();
                if (group.getQuantifier().isInfiniteLoop()) {
                    flags |= 0x1000;
                } else {
                    maxPath = group.getMaxPath() + (maxPath - group.getMaxPath()) * group.getQuantifier().getMax();
                }
            }
            if (this.ast.getOptions().getFlavor().canHaveEmptyLoopIterations() || (flags & 0x38000) != 0) {
                if (group.getFirstAlternative().isExpandedQuantifier()) {
                    assert (group.size() == 2);
                    if (group.getLastAlternative().getMinPath() - group.getMinPath() == 0) {
                        this.setZeroWidthQuantifierIndex(group);
                    }
                } else if (group.getLastAlternative().isExpandedQuantifier()) {
                    assert (group.size() == 2);
                    if (group.getFirstAlternative().getMinPath() - group.getMinPath() == 0) {
                        this.setZeroWidthQuantifierIndex(group);
                    }
                } else if (minPath - group.getMinPath() == 0) {
                    this.setZeroWidthQuantifierIndex(group);
                }
            }
        }
        if (group.isCapturing()) {
            flags |= 0x2000;
            if (group.getMinPath() == minPath && group.getMaxPath() == maxPath) {
                this.ast.getProperties().setEmptyCaptureGroups();
            }
        }
        group.setFlags(flags, 258110);
        group.setMinPath(minPath);
        group.setMaxPath(maxPath);
        if (group.getParent().isSequence() || group.getParent().isAtomicGroup()) {
            group.getParent().setMinPath(minPath);
            group.getParent().setMaxPath(maxPath);
        }
        if (group.getParent() != null) {
            group.getParent().setFlags(group.getParent().getFlags(258110) | flags, 258110);
        }
    }

    @Override
    protected void visit(Sequence sequence) {
        sequence.setMinPath(sequence.getParent().getMinPath());
        sequence.setMaxPath(sequence.getParent().getMaxPath());
    }

    @Override
    protected void leave(Sequence sequence) {
        int i = 0;
        while (i < sequence.size()) {
            LookAroundAssertion lookAround;
            Term term = sequence.get(i);
            if (term.isLookAroundAssertion() && (lookAround = term.asLookAroundAssertion()).isNegated() && lookAround.isDead()) {
                sequence.removeTerm(i, this.compilationBuffer);
                continue;
            }
            ++i;
        }
    }

    @Override
    protected void visit(PositionAssertion assertion) {
        switch (assertion.type) {
            case CARET: {
                if (!this.isForward()) break;
                assertion.getParent().setHasCaret();
                if (assertion.getParent().getMinPath() > 0) {
                    assertion.markAsDead();
                    assertion.getParent().markAsDead();
                    break;
                }
                this.ast.getReachableCarets().add(assertion);
                assertion.getParent().setStartsWithCaret();
                break;
            }
            case DOLLAR: {
                if (!this.isReverse()) break;
                assertion.getParent().setHasDollar();
                if (assertion.getParent().getMinPath() > 0) {
                    assertion.markAsDead();
                    assertion.getParent().markAsDead();
                    break;
                }
                this.ast.getReachableDollars().add(assertion);
                assertion.getParent().setEndsWithDollar();
            }
        }
        assertion.setMinPath(assertion.getParent().getMinPath());
        assertion.setMaxPath(assertion.getParent().getMaxPath());
    }

    @Override
    protected void visit(LookBehindAssertion assertion) {
        assertion.getParent().setHasLookBehinds();
        assertion.setMinPath(assertion.getParent().getMinPath());
        assertion.setMaxPath(assertion.getParent().getMaxPath());
    }

    @Override
    protected void leave(LookBehindAssertion assertion) {
        if (this.isForward() && !assertion.isDead()) {
            if (assertion.isNegated()) {
                this.ast.getProperties().setNegativeLookBehindAssertions();
            } else {
                this.ast.getProperties().setLookBehindAssertions();
            }
            if (!assertion.isLiteral()) {
                this.ast.getProperties().setNonLiteralLookBehindAssertions();
            }
        }
        this.leaveLookAroundAssertion(assertion);
    }

    @Override
    protected void visit(LookAheadAssertion assertion) {
        assertion.getParent().setHasLookAheads();
        assertion.setMinPath(assertion.getParent().getMinPath());
        assertion.setMaxPath(assertion.getParent().getMaxPath());
    }

    @Override
    protected void leave(LookAheadAssertion assertion) {
        if (this.isForward() && !assertion.isDead()) {
            if (assertion.isNegated()) {
                this.ast.getProperties().setNegativeLookAheadAssertions();
            } else {
                this.ast.getProperties().setLookAheadAssertions();
            }
        }
        this.leaveLookAroundAssertion(assertion);
    }

    @Override
    protected void visit(AtomicGroup atomicGroup) {
        atomicGroup.setMinPath(atomicGroup.getParent().getMinPath());
        atomicGroup.setMaxPath(atomicGroup.getParent().getMaxPath());
    }

    @Override
    protected void leave(AtomicGroup atomicGroup) {
        if (this.isForward() && !atomicGroup.isDead()) {
            this.ast.getProperties().setAtomicGroups();
            this.ast.getSubtrees().add(atomicGroup);
            atomicGroup.getSubTreeParent().getSubtrees().add(atomicGroup);
        }
        CalcASTPropsVisitor.leaveSubtreeRootNode(atomicGroup, 258110);
        atomicGroup.getParent().setMinPath(atomicGroup.getMinPath());
        atomicGroup.getParent().setMaxPath(atomicGroup.getMaxPath());
    }

    private void leaveLookAroundAssertion(LookAroundAssertion assertion) {
        if (this.isForward() && !assertion.isDead()) {
            this.ast.getSubtrees().add(assertion);
            assertion.getSubTreeParent().getSubtrees().add(assertion);
        }
        if (assertion.hasCaptureGroups()) {
            this.ast.getProperties().setCaptureGroupsInLookAroundAssertions();
        }
        CalcASTPropsVisitor.leaveSubtreeRootNode(assertion, assertion.isNegated() ? 258060 : (assertion.isLookBehindAssertion() ? 258062 : 258110));
    }

    private static void leaveSubtreeRootNode(RegexASTSubtreeRootNode subtreeRootNode, int flagMask) {
        subtreeRootNode.getParent().setFlags(subtreeRootNode.getFlags(flagMask) | subtreeRootNode.getParent().getFlags(flagMask), flagMask);
    }

    @Override
    protected void visit(CharacterClass characterClass) {
        if (this.isForward()) {
            if (!characterClass.getCharSet().matchesSingleChar()) {
                if (!characterClass.getCharSet().matches2CharsWith1BitDifference()) {
                    this.ast.getProperties().unsetCharClassesCanBeMatchedWithMask();
                }
                if (!this.ast.getEncoding().isFixedCodePointWidth(characterClass.getCharSet())) {
                    this.ast.getProperties().unsetFixedCodePointWidth();
                }
                this.ast.getProperties().setCharClasses();
            }
            if (this.ast.getEncoding() == Encodings.UTF_16 && Constants.SURROGATES.intersects(characterClass.getCharSet())) {
                this.ast.getProperties().setLoneSurrogates();
            }
        }
        if (characterClass.hasNotUnrolledQuantifier()) {
            characterClass.getParent().setHasQuantifiers();
            this.setQuantifierIndex(characterClass);
            characterClass.getParent().incMinPath(characterClass.getQuantifier().getMin());
            if (characterClass.getQuantifier().isInfiniteLoop()) {
                characterClass.setHasLoops();
                characterClass.getParent().setHasLoops();
            } else {
                characterClass.getParent().incMaxPath(characterClass.getQuantifier().getMax());
            }
        } else {
            characterClass.getParent().incMinPath();
            characterClass.getParent().incMaxPath();
        }
        characterClass.setMinPath(characterClass.getParent().getMinPath());
        characterClass.setMaxPath(characterClass.getParent().getMaxPath());
        if (characterClass.getCharSet().matchesNothing()) {
            characterClass.markAsDead();
            characterClass.getParent().markAsDead();
        }
    }

    private void setQuantifierIndex(QuantifiableTerm term) {
        assert (term.hasQuantifier());
        if (this.isForward() && term.getQuantifier().getIndex() < 0) {
            term.getQuantifier().setIndex(this.ast.getQuantifierCount().inc());
        }
    }

    private void setZeroWidthQuantifierIndex(QuantifiableTerm term) {
        if (this.isForward() && term.getQuantifier().getZeroWidthIndex() < 0) {
            this.ast.registerZeroWidthQuantifiable(term);
        }
    }

    @Override
    protected void visit(SubexpressionCall subexpressionCall) {
        throw CompilerDirectives.shouldNotReachHere("subexpression calls should be expanded by the parser");
    }
}

