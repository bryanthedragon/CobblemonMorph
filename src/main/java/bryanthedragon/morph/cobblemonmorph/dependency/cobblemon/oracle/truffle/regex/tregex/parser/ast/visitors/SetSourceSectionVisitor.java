package com.oracle.truffle.regex.tregex.parser.ast.visitors;

import com.oracle.truffle.regex.tregex.parser.Token;
import com.oracle.truffle.regex.tregex.parser.ast.AtomicGroup;
import com.oracle.truffle.regex.tregex.parser.ast.BackReference;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.LookAheadAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.SubexpressionCall;

public final class SetSourceSectionVisitor extends DepthFirstTraversalRegexASTVisitor {
   private final RegexAST ast;
   private Token token;

   public SetSourceSectionVisitor(RegexAST ast) {
      this.ast = ast;
   }

   public void run(Group root, Token t) {
      this.token = t;
      this.run(root);
   }

   @Override
   protected void visit(BackReference backReference) {
      this.ast.addSourceSection(backReference, this.token);
   }

   @Override
   protected void visit(Group group) {
   }

   @Override
   protected void leave(Group group) {
   }

   @Override
   protected void visit(Sequence sequence) {
   }

   @Override
   protected void visit(PositionAssertion assertion) {
      this.ast.addSourceSection(assertion, this.token);
   }

   @Override
   protected void visit(LookBehindAssertion assertion) {
      this.ast.addSourceSection(assertion, this.token);
   }

   @Override
   protected void visit(LookAheadAssertion assertion) {
      this.ast.addSourceSection(assertion, this.token);
   }

   @Override
   protected void visit(AtomicGroup atomicGroup) {
      this.ast.addSourceSection(atomicGroup, this.token);
   }

   @Override
   protected void visit(CharacterClass characterClass) {
      this.ast.addSourceSection(characterClass, this.token);
   }

   @Override
   protected void visit(SubexpressionCall subexpressionCall) {
      this.ast.addSourceSection(subexpressionCall, this.token);
   }
}
