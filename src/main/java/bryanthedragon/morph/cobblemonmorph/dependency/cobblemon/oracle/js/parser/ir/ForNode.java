package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class ForNode extends LoopNode {
   private final Expression init;
   private final JoinPredecessorExpression modify;
   private Symbol iterator;
   public static final int IS_FOR_IN = 1;
   public static final int IS_FOR_EACH = 2;
   public static final int PER_ITERATION_SCOPE = 4;
   public static final int IS_FOR_OF = 8;
   public static final int IS_FOR_AWAIT_OF = 16;
   private final int flags;

   public ForNode(
      final int lineNumber,
      final long token,
      final int finish,
      final Block body,
      final int flags,
      final Expression init,
      final JoinPredecessorExpression test,
      final JoinPredecessorExpression modify
   ) {
      super(lineNumber, token, finish, body, test, false);
      this.flags = flags;
      this.init = init;
      this.modify = modify;
   }

   private ForNode(
      final ForNode forNode,
      final Expression init,
      final JoinPredecessorExpression test,
      final Block body,
      final JoinPredecessorExpression modify,
      final int flags,
      final boolean controlFlowEscapes
   ) {
      super(forNode, test, body, controlFlowEscapes);
      this.init = init;
      this.modify = modify;
      this.flags = flags;
      this.iterator = forNode.iterator;
   }

   @Override
   public Node accept(final LexicalContext lc, final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterForNode(this)
         ? visitor.leaveForNode(
            this.setInit(lc, this.init == null ? null : (Expression)this.init.accept(visitor))
               .setTest(lc, this.test == null ? null : (JoinPredecessorExpression)this.test.accept(visitor))
               .setModify(lc, this.modify == null ? null : (JoinPredecessorExpression)this.modify.accept(visitor))
               .setBody(lc, (Block)this.body.accept(visitor))
         )
         : this);
   }

   @Override
   public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterForNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printTypes) {
      sb.append("for");
      sb.append(' ');
      if (this.isForEach()) {
         sb.append("each ");
      }

      sb.append('(');
      if (this.isForIn()) {
         this.init.toString(sb, printTypes);
         sb.append(" in ");
         this.modify.toString(sb, printTypes);
      } else if (this.isForOf()) {
         this.init.toString(sb, printTypes);
         sb.append(" of ");
         this.modify.toString(sb, printTypes);
      } else {
         if (this.init != null) {
            this.init.toString(sb, printTypes);
         }

         sb.append("; ");
         if (this.test != null) {
            this.test.toString(sb, printTypes);
         }

         sb.append("; ");
         if (this.modify != null) {
            this.modify.toString(sb, printTypes);
         }
      }

      sb.append(')');
   }

   @Override
   public boolean hasGoto() {
      return !this.isForInOrOf() && this.test == null;
   }

   @Override
   public boolean mustEnter() {
      return this.isForInOrOf() ? false : this.test == null;
   }

   public Expression getInit() {
      return this.init;
   }

   public ForNode setInit(final LexicalContext lc, final Expression init) {
      return this.init == init
         ? this
         : Node.replaceInLexicalContext(lc, this, new ForNode(this, init, this.test, this.body, this.modify, this.flags, this.controlFlowEscapes));
   }

   public boolean isForIn() {
      return (this.flags & 1) != 0;
   }

   public boolean isForEach() {
      return (this.flags & 2) != 0;
   }

   public boolean isForOf() {
      return (this.flags & 8) != 0;
   }

   public boolean isForAwaitOf() {
      return (this.flags & 16) != 0;
   }

   public boolean isForInOrOf() {
      return this.isForIn() || this.isForOf() || this.isForAwaitOf();
   }

   public Symbol getIterator() {
      return this.iterator;
   }

   public void setIterator(final Symbol iterator) {
      this.iterator = iterator;
   }

   public JoinPredecessorExpression getModify() {
      return this.modify;
   }

   public ForNode setModify(final LexicalContext lc, final JoinPredecessorExpression modify) {
      return this.modify == modify
         ? this
         : Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, modify, this.flags, this.controlFlowEscapes));
   }

   public ForNode setTest(final LexicalContext lc, final JoinPredecessorExpression test) {
      return this.test == test
         ? this
         : Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, test, this.body, this.modify, this.flags, this.controlFlowEscapes));
   }

   @Override
   public Block getBody() {
      return this.body;
   }

   public ForNode setBody(final LexicalContext lc, final Block body) {
      return this.body == body
         ? this
         : Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, body, this.modify, this.flags, this.controlFlowEscapes));
   }

   public ForNode setControlFlowEscapes(final LexicalContext lc, final boolean controlFlowEscapes) {
      return this.controlFlowEscapes == controlFlowEscapes
         ? this
         : Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, this.modify, this.flags, controlFlowEscapes));
   }

   @Override
   public boolean hasPerIterationScope() {
      return (this.flags & 4) != 0;
   }
}
