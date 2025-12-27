package com.oracle.js.parser.ir;

import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class ExpressionStatement extends Statement {
   private final Expression expression;

   public ExpressionStatement(final int lineNumber, final long token, final int finish, final Expression expression) {
      super(lineNumber, token, finish);
      this.expression = expression;
   }

   private ExpressionStatement(final ExpressionStatement expressionStatement, final Expression expression) {
      super(expressionStatement);
      this.expression = expression;
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterExpressionStatement(this)
         ? visitor.leaveExpressionStatement(this.setExpression((Expression)this.expression.accept(visitor)))
         : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterExpressionStatement(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printTypes) {
      this.expression.toString(sb, printTypes);
   }

   public Expression getExpression() {
      return this.expression;
   }

   public ExpressionStatement setExpression(final Expression expression) {
      return this.expression == expression ? this : new ExpressionStatement(this, expression);
   }

   @Override
   public boolean isCompletionValueNeverEmpty() {
      TokenType type = this.tokenType();
      return type != TokenType.ASSIGN_INIT;
   }
}
