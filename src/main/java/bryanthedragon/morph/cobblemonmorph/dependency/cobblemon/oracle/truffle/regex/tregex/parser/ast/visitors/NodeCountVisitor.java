package com.oracle.truffle.regex.tregex.parser.ast.visitors;

import com.oracle.truffle.regex.tregex.parser.ast.AtomicGroup;
import com.oracle.truffle.regex.tregex.parser.ast.BackReference;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.Group;
import com.oracle.truffle.regex.tregex.parser.ast.LookAheadAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.SubexpressionCall;

public class NodeCountVisitor extends DepthFirstTraversalRegexASTVisitor {
   private int count = 0;

   public int count(RegexASTNode runRoot) {
      this.count = 0;
      this.run(runRoot);
      return this.count;
   }

   @Override
   protected void visit(BackReference backReference) {
      this.count++;
   }

   @Override
   protected void visit(Group group) {
      this.count++;
      if (group.getParent() instanceof RegexASTSubtreeRootNode) {
         this.count += 4;
      }
   }

   @Override
   protected void visit(Sequence sequence) {
      this.count++;
   }

   @Override
   protected void visit(PositionAssertion assertion) {
      this.count++;
   }

   @Override
   protected void visit(LookBehindAssertion assertion) {
      this.count++;
   }

   @Override
   protected void visit(LookAheadAssertion assertion) {
      this.count++;
   }

   @Override
   protected void visit(AtomicGroup atomicGroup) {
      this.count++;
   }

   @Override
   protected void visit(CharacterClass characterClass) {
      this.count++;
   }

   @Override
   protected void visit(SubexpressionCall subexpressionCall) {
      this.count++;
   }
}
