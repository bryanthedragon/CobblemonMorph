package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class WithNode extends LexicalContextStatement {
   private final Expression expression;
   private final Block body;

   public WithNode(final int lineNumber, final long token, final int finish, final Expression expression, final Block body) {
      super(lineNumber, token, finish);
      this.expression = expression;
      this.body = body;
   }

   private WithNode(final WithNode node, final Expression expression, final Block body) {
      super(node);
      this.expression = expression;
      this.body = body;
   }

   @Override
   public Node accept(final LexicalContext lc, final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterWithNode(this)
         ? visitor.leaveWithNode(this.setExpression(lc, (Expression)this.expression.accept(visitor)).setBody(lc, (Block)this.body.accept(visitor)))
         : this);
   }

   @Override
   public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterWithNode(this);
   }

   @Override
   public boolean isTerminal() {
      return this.body.isTerminal();
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      sb.append("with (");
      this.expression.toString(sb, printType);
      sb.append(')');
   }

   public Block getBody() {
      return this.body;
   }

   public WithNode setBody(final LexicalContext lc, final Block body) {
      return this.body == body ? this : Node.replaceInLexicalContext(lc, this, new WithNode(this, this.expression, body));
   }

   public Expression getExpression() {
      return this.expression;
   }

   public WithNode setExpression(final LexicalContext lc, final Expression expression) {
      return this.expression == expression ? this : Node.replaceInLexicalContext(lc, this, new WithNode(this, expression, this.body));
   }

   @Override
   public boolean isCompletionValueNeverEmpty() {
      return true;
   }
}
