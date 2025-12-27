package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class ThrowNode extends Statement {
   private final Expression expression;
   private final boolean isSyntheticRethrow;

   public ThrowNode(final int lineNumber, final long token, final int finish, final Expression expression, final boolean isSyntheticRethrow) {
      super(lineNumber, token, finish);
      this.expression = expression;
      this.isSyntheticRethrow = isSyntheticRethrow;
   }

   private ThrowNode(final ThrowNode node, final Expression expression, final boolean isSyntheticRethrow) {
      super(node);
      this.expression = expression;
      this.isSyntheticRethrow = isSyntheticRethrow;
   }

   @Override
   public boolean isTerminal() {
      return true;
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterThrowNode(this) ? visitor.leaveThrowNode(this.setExpression((Expression)this.expression.accept(visitor))) : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterThrowNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      sb.append("throw ");
      if (this.expression != null) {
         this.expression.toString(sb, printType);
      }
   }

   public Expression getExpression() {
      return this.expression;
   }

   public ThrowNode setExpression(final Expression expression) {
      return this.expression == expression ? this : new ThrowNode(this, expression, this.isSyntheticRethrow);
   }

   public boolean isSyntheticRethrow() {
      return this.isSyntheticRethrow;
   }

   @Override
   public boolean isCompletionValueNeverEmpty() {
      return true;
   }
}
