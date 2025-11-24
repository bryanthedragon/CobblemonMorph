
package com.oracle.truffle.regex.tregex.parser.ast.visitors;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.parser.ast.AtomicGroup;
import com.oracle.truffle.regex.tregex.parser.ast.BackReference;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.LookAheadAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.SubexpressionCall;

public abstract class RegexASTVisitor {
    protected abstract void visit(BackReference var1);

    protected abstract void visit(Group var1);

    protected abstract void leave(Group var1);

    protected abstract void visit(Sequence var1);

    protected abstract void leave(Sequence var1);

    protected abstract void visit(SubexpressionCall var1);

    protected abstract void visit(PositionAssertion var1);

    protected abstract void visit(LookBehindAssertion var1);

    protected abstract void leave(LookBehindAssertion var1);

    protected abstract void visit(LookAheadAssertion var1);

    protected abstract void leave(LookAheadAssertion var1);

    protected abstract void visit(AtomicGroup var1);

    protected abstract void leave(AtomicGroup var1);

    protected abstract void visit(CharacterClass var1);

    protected void doVisit(RegexASTNode cur) {
        if (cur instanceof Group) {
            this.visit((Group)cur);
        } else if (cur instanceof Sequence) {
            this.visit((Sequence)cur);
        } else if (cur instanceof PositionAssertion) {
            this.visit((PositionAssertion)cur);
        } else if (cur instanceof LookBehindAssertion) {
            this.visit((LookBehindAssertion)cur);
        } else if (cur instanceof LookAheadAssertion) {
            this.visit((LookAheadAssertion)cur);
        } else if (cur instanceof AtomicGroup) {
            this.visit((AtomicGroup)cur);
        } else if (cur instanceof CharacterClass) {
            this.visit((CharacterClass)cur);
        } else if (cur instanceof BackReference) {
            this.visit((BackReference)cur);
        } else if (cur instanceof SubexpressionCall) {
            this.visit((SubexpressionCall)cur);
        } else {
            throw CompilerDirectives.shouldNotReachHere();
        }
    }

    protected void doLeave(RegexASTNode cur) {
        if (cur instanceof Group) {
            this.leave((Group)cur);
        } else if (cur instanceof Sequence) {
            this.leave((Sequence)cur);
        } else if (cur instanceof LookBehindAssertion) {
            this.leave((LookBehindAssertion)cur);
        } else if (cur instanceof LookAheadAssertion) {
            this.leave((LookAheadAssertion)cur);
        } else if (cur instanceof AtomicGroup) {
            this.leave((AtomicGroup)cur);
        } else {
            throw CompilerDirectives.shouldNotReachHere();
        }
    }
}

