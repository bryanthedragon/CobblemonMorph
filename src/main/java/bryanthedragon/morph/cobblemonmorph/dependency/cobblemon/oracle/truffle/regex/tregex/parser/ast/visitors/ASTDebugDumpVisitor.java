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
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.SubexpressionCall;

public final class ASTDebugDumpVisitor extends DepthFirstTraversalRegexASTVisitor {
   private final StringBuilder dump = new StringBuilder();
   private boolean dead;

   private ASTDebugDumpVisitor() {
   }

   @CompilerDirectives.TruffleBoundary
   public static String getDump(Group root) {
      ASTDebugDumpVisitor visitor = new ASTDebugDumpVisitor();
      visitor.run(root);
      if (visitor.dead) {
         visitor.dump.append("\u001b[0m");
      }

      return visitor.dump.toString();
   }

   private void append(RegexASTNode node) {
      this.checkDead(node);
      this.dump.append(node.toString());
   }

   private void checkDead(RegexASTNode node) {
      if (!this.dead && node.isDead()) {
         this.dump.append("\u001b[41m");
         this.dead = true;
      }

      if (this.dead && !node.isDead()) {
         this.dump.append("\u001b[0m");
         this.dead = false;
      }
   }

   @Override
   protected void visit(BackReference backReference) {
      this.append(backReference);
   }

   @Override
   protected void visit(Group group) {
      this.checkDead(group);
      this.dump.append("(");
      if (group.getParent() instanceof RegexASTSubtreeRootNode) {
         this.dump.append(((RegexASTSubtreeRootNode)group.getParent()).getPrefix());
      } else if (!group.isCapturing()) {
         this.dump.append("?:");
      }
   }

   @Override
   protected void leave(Group group) {
      this.dump.append(")").append(group.loopToString());
   }

   @Override
   protected void visit(Sequence sequence) {
      if (sequence != sequence.getParent().getFirstAlternative()) {
         this.dump.append("|");
      }
   }

   @Override
   protected void visit(PositionAssertion assertion) {
      this.append(assertion);
   }

   @Override
   protected void visit(LookBehindAssertion assertion) {
   }

   @Override
   protected void visit(LookAheadAssertion assertion) {
   }

   @Override
   protected void visit(AtomicGroup atomicGroup) {
   }

   @Override
   protected void visit(CharacterClass characterClass) {
      this.append(characterClass);
   }

   @Override
   protected void visit(SubexpressionCall subexpressionCall) {
      this.append(subexpressionCall);
   }
}
