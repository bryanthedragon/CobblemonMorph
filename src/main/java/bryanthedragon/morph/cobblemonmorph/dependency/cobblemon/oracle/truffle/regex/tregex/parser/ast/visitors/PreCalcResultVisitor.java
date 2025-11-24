
package com.oracle.truffle.regex.tregex.parser.ast.visitors;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.result.PreCalculatedResultFactory;
import com.oracle.truffle.regex.tregex.parser.ast.BackReference;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.LookAheadAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.SubexpressionCall;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.DepthFirstTraversalRegexASTVisitor;
import com.oracle.truffle.regex.tregex.string.AbstractString;
import com.oracle.truffle.regex.tregex.string.AbstractStringBuffer;

public final class PreCalcResultVisitor
extends DepthFirstTraversalRegexASTVisitor {
    private final boolean extractLiteral;
    private final boolean unrollGroups;
    private final RegexAST ast;
    private int index = 0;
    private int lastGroup = -1;
    private final AbstractStringBuffer literal;
    private final AbstractStringBuffer mask;
    private final PreCalculatedResultFactory result;
    private PreCalcResultVisitor groupUnroller;

    private PreCalcResultVisitor(RegexAST ast, boolean extractLiteral) {
        this.ast = ast;
        this.result = new PreCalculatedResultFactory(ast.getNumberOfCaptureGroups(), ast.getOptions().getFlavor().usesLastGroupResultField());
        this.extractLiteral = extractLiteral;
        if (extractLiteral) {
            this.literal = ast.getEncoding().createStringBuffer(ast.getRoot().getMinPath());
            this.mask = ast.getProperties().hasCharClasses() ? ast.getEncoding().createStringBuffer(ast.getRoot().getMinPath()) : null;
        } else {
            this.literal = null;
            this.mask = null;
        }
        this.unrollGroups = true;
    }

    private PreCalcResultVisitor(RegexAST ast, boolean extractLiteral, boolean unrollGroups, int index, AbstractStringBuffer literal, AbstractStringBuffer mask, PreCalculatedResultFactory result) {
        this.ast = ast;
        this.extractLiteral = extractLiteral;
        this.unrollGroups = unrollGroups;
        this.index = index;
        this.literal = literal;
        this.mask = mask;
        this.result = result;
    }

    public static PreCalcResultVisitor run(RegexAST ast, boolean extractLiteral) {
        PreCalcResultVisitor visitor = new PreCalcResultVisitor(ast, extractLiteral);
        visitor.run(ast.getRoot());
        visitor.result.setLength(visitor.index);
        if (ast.getOptions().getFlavor().usesLastGroupResultField()) {
            visitor.result.setLastGroup(visitor.lastGroup);
        }
        return visitor;
    }

    public static PreCalculatedResultFactory createResultFactory(RegexAST ast) {
        PreCalcResultVisitor visitor = new PreCalcResultVisitor(ast, false);
        visitor.run(ast.getRoot());
        visitor.result.setLength(visitor.index);
        if (ast.getOptions().getFlavor().usesLastGroupResultField()) {
            visitor.result.setLastGroup(visitor.lastGroup);
        }
        return visitor.result;
    }

    public AbstractString getLiteral() {
        return this.literal.materialize();
    }

    public AbstractString getMask() {
        return this.mask == null ? null : this.mask.materialize();
    }

    public PreCalculatedResultFactory getResultFactory() {
        return this.result;
    }

    public boolean isBooleanMatch() {
        return this.ast.getOptions().isBooleanMatch();
    }

    @Override
    protected void visit(BackReference backReference) {
        throw CompilerDirectives.shouldNotReachHere();
    }

    @Override
    protected void visit(Group group) {
        if (group.isCapturing()) {
            this.result.setStart(group.getGroupNumber(), this.index);
        }
    }

    @Override
    protected void leave(Group group) {
        if (group.isCapturing()) {
            this.result.setEnd(group.getGroupNumber(), this.index);
            if (group.getGroupNumber() != 0) {
                this.lastGroup = group.getGroupNumber();
            }
        }
        if (this.unrollGroups && group.hasNotUnrolledQuantifier()) {
            assert (group.getQuantifier().getMin() == group.getQuantifier().getMax());
            if (this.groupUnroller == null) {
                this.groupUnroller = new PreCalcResultVisitor(this.ast, this.extractLiteral, false, this.index, this.literal, this.mask, this.result);
            }
            this.groupUnroller.index = this.index;
            for (int i = 0; i < group.getQuantifier().getMin() - 1; ++i) {
                this.groupUnroller.run(group);
            }
            this.index = this.groupUnroller.index;
            if (this.groupUnroller.lastGroup != -1) {
                this.lastGroup = this.groupUnroller.lastGroup;
            }
        }
    }

    @Override
    protected void visit(Sequence sequence) {
    }

    @Override
    protected void visit(PositionAssertion assertion) {
    }

    @Override
    protected void visit(LookBehindAssertion assertion) {
        throw CompilerDirectives.shouldNotReachHere();
    }

    @Override
    protected void visit(LookAheadAssertion assertion) {
        throw CompilerDirectives.shouldNotReachHere();
    }

    @Override
    protected void visit(CharacterClass characterClass) {
        assert (!characterClass.hasQuantifier() || characterClass.getQuantifier().getMin() == characterClass.getQuantifier().getMax());
        for (int i = 0; i < (characterClass.hasNotUnrolledQuantifier() ? characterClass.getQuantifier().getMin() : 1); ++i) {
            int cp = characterClass.getCharSet().getMin();
            if (this.extractLiteral) {
                if (this.mask == null) {
                    this.literal.append(cp);
                } else {
                    characterClass.extractSingleChar(this.literal, this.mask);
                }
            }
            this.index += this.ast.getEncoding().getEncodedSize(cp);
        }
    }

    @Override
    protected void visit(SubexpressionCall subexpressionCall) {
        throw CompilerDirectives.shouldNotReachHere("subexpression calls should be expanded by the parser");
    }
}

