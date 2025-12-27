package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class WhileNode extends LoopNode {
   private final boolean isDoWhile;

   public WhileNode(final int lineNumber, final long token, final int finish, final boolean isDoWhile, final JoinPredecessorExpression test, final Block body) {
      super(lineNumber, token, finish, body, test, false);
      this.isDoWhile = isDoWhile;
   }

   private WhileNode(final WhileNode whileNode, final JoinPredecessorExpression test, final Block body, final boolean controlFlowEscapes) {
      super(whileNode, test, body, controlFlowEscapes);
      this.isDoWhile = whileNode.isDoWhile;
   }

   @Override
   public boolean hasGoto() {
      return this.test == null;
   }

   @Override
   public Node accept(final LexicalContext lc, final NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterWhileNode(this)) {
         return this.isDoWhile()
            ? visitor.leaveWhileNode(this.setBody(lc, (Block)this.body.accept(visitor)).setTest(lc, (JoinPredecessorExpression)this.test.accept(visitor)))
            : visitor.leaveWhileNode(this.setTest(lc, (JoinPredecessorExpression)this.test.accept(visitor)).setBody(lc, (Block)this.body.accept(visitor)));
      } else {
         return this;
      }
   }

   @Override
   public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterWhileNode(this);
   }

   public WhileNode setTest(final LexicalContext lc, final JoinPredecessorExpression test) {
      return this.test == test ? this : Node.replaceInLexicalContext(lc, this, new WhileNode(this, test, this.body, this.controlFlowEscapes));
   }

   @Override
   public Block getBody() {
      return this.body;
   }

   public WhileNode setBody(final LexicalContext lc, final Block body) {
      return this.body == body ? this : Node.replaceInLexicalContext(lc, this, new WhileNode(this, this.test, body, this.controlFlowEscapes));
   }

   public WhileNode setControlFlowEscapes(final LexicalContext lc, final boolean controlFlowEscapes) {
      return this.controlFlowEscapes == controlFlowEscapes
         ? this
         : Node.replaceInLexicalContext(lc, this, new WhileNode(this, this.test, this.body, controlFlowEscapes));
   }

   public boolean isDoWhile() {
      return this.isDoWhile;
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      sb.append("while (");
      this.test.toString(sb, printType);
      sb.append(')');
   }

   @Override
   public boolean mustEnter() {
      return this.isDoWhile() ? true : this.test == null;
   }

   @Override
   public boolean hasPerIterationScope() {
      return false;
   }
}
