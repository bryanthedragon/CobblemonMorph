package com.oracle.truffle.regex.tregex.parser.ast.visitors;

import com.oracle.truffle.api.CompilerDirectives;
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

public final class InitIDVisitor extends DepthFirstTraversalRegexASTVisitor {
   public static final int REGEX_AST_ROOT_PARENT_ID = 0;
   private final RegexASTNode[] index;
   private int nextID;

   private InitIDVisitor(RegexASTNode[] index, int nextID) {
      this.index = index;
      this.nextID = nextID;
   }

   public static void init(RegexAST ast) {
      int initialID = 3 + ast.getWrappedPrefixLength() * 2;
      InitIDVisitor visitor = new InitIDVisitor(new RegexASTNode[initialID + ast.getNumberOfNodes() + 1], initialID);

      assert ast.getWrappedRoot().getSubTreeParent().getId() == 0;

      visitor.index[0] = ast.getWrappedRoot().getSubTreeParent();
      visitor.run(ast.getWrappedRoot());
      ast.setIndex(visitor.index);
   }

   private void initID(RegexASTNode node) {
      assert !node.idInitialized();

      node.setId(this.nextID++);
      this.index[node.getId()] = node;
   }

   @Override
   protected void visit(BackReference backReference) {
      this.initID(backReference);
   }

   @Override
   protected void visit(Group group) {
      if (group.getParent() instanceof RegexASTSubtreeRootNode) {
         this.initID(group.getSubTreeParent().getAnchoredInitialState());
         this.initID(group.getSubTreeParent().getUnAnchoredInitialState());
      }

      this.initID(group);
   }

   @Override
   protected void leave(Group group) {
      if (group.getParent() instanceof RegexASTSubtreeRootNode) {
         this.initID(group.getSubTreeParent().getAnchoredFinalState());
         this.initID(group.getSubTreeParent().getMatchFound());
      }
   }

   @Override
   protected void visit(Sequence sequence) {
      this.initID(sequence);
   }

   @Override
   protected void visit(PositionAssertion assertion) {
      this.initID(assertion);
   }

   @Override
   protected void visit(LookBehindAssertion assertion) {
      this.initID(assertion);
   }

   @Override
   protected void visit(LookAheadAssertion assertion) {
      this.initID(assertion);
   }

   @Override
   protected void visit(AtomicGroup atomicGroup) {
      this.initID(atomicGroup);
   }

   @Override
   protected void visit(CharacterClass characterClass) {
      this.initID(characterClass);
   }

   @Override
   protected void visit(SubexpressionCall subexpressionCall) {
      throw CompilerDirectives.shouldNotReachHere("subexpression calls should be expanded by the parser");
   }
}
