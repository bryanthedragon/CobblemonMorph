package com.oracle.truffle.regex.tregex.parser.ast.visitors;

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

public abstract class DepthFirstTraversalRegexASTVisitor extends RegexASTVisitor {
   private RegexASTNode root;
   private RegexASTNode cur;
   private boolean done = false;
   private boolean reverse = false;

   protected void run(RegexASTNode runRoot) {
      this.run(runRoot, false);
   }

   protected void runReverse(RegexASTNode runRoot) {
      this.run(runRoot, true);
   }

   protected boolean isForward() {
      return !this.reverse;
   }

   protected boolean isReverse() {
      return this.reverse;
   }

   private void run(RegexASTNode runRoot, boolean runReverse) {
      this.reverse = runReverse;
      this.root = runRoot;
      this.cur = this.root;
      this.done = false;
      this.init(runRoot);

      while (!this.done) {
         this.doVisit(this.cur);

         while (this.doAdvance()) {
         }
      }
   }

   protected void init(RegexASTNode runRoot) {
   }

   private boolean doAdvance() {
      if (this.cur == null || this.cur == this.root.getParent()) {
         this.done = true;
         return false;
      } else {
         return this.cur instanceof RegexASTVisitorIterable ? this.advance((RegexASTVisitorIterable)this.cur) : this.advanceLeafNode(this.cur);
      }
   }

   @Override
   protected void visit(BackReference backReference) {
   }

   @Override
   protected void visit(Group group) {
   }

   @Override
   protected void visit(Sequence sequence) {
   }

   @Override
   protected void visit(SubexpressionCall subexpressionCall) {
   }

   @Override
   protected void visit(PositionAssertion assertion) {
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
   }

   @Override
   protected void leave(Group group) {
   }

   @Override
   protected void leave(Sequence sequence) {
   }

   @Override
   protected void leave(LookBehindAssertion assertion) {
   }

   @Override
   protected void leave(LookAheadAssertion assertion) {
   }

   @Override
   protected void leave(AtomicGroup atomicGroup) {
   }

   private boolean advance(RegexASTVisitorIterable iterable) {
      if (iterable.visitorHasNext()) {
         this.cur = iterable.visitorGetNext(this.reverse);
         return false;
      } else {
         iterable.resetVisitorIterator();
         this.doLeave(this.cur);
         this.cur = ((RegexASTNode)iterable).getParent();
         return true;
      }
   }

   private boolean advanceLeafNode(RegexASTNode node) {
      this.cur = node.getParent();
      return true;
   }
}
