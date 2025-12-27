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
   protected abstract void visit(BackReference backReference);

   protected abstract void visit(Group group);

   protected abstract void leave(Group group);

   protected abstract void visit(Sequence sequence);

   protected abstract void leave(Sequence sequence);

   protected abstract void visit(SubexpressionCall subexpressionCall);

   protected abstract void visit(PositionAssertion assertion);

   protected abstract void visit(LookBehindAssertion assertion);

   protected abstract void leave(LookBehindAssertion assertion);

   protected abstract void visit(LookAheadAssertion assertion);

   protected abstract void leave(LookAheadAssertion assertion);

   protected abstract void visit(AtomicGroup atomicGroup);

   protected abstract void leave(AtomicGroup atomicGroup);

   protected abstract void visit(CharacterClass characterClass);

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
      } else {
         if (!(cur instanceof SubexpressionCall)) {
            throw CompilerDirectives.shouldNotReachHere();
         }

         this.visit((SubexpressionCall)cur);
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
      } else {
         if (!(cur instanceof AtomicGroup)) {
            throw CompilerDirectives.shouldNotReachHere();
         }

         this.leave((AtomicGroup)cur);
      }
   }
}
