package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class JoinPredecessorExpression extends Expression {
   private final Expression expression;

   public JoinPredecessorExpression() {
      this(null);
   }

   public JoinPredecessorExpression(final Expression expression) {
      super(expression == null ? 0L : expression.getToken(), expression == null ? 0 : expression.getStart(), expression == null ? 0 : expression.getFinish());
      this.expression = expression;
   }

   @Override
   public boolean isAlwaysFalse() {
      return this.expression != null && this.expression.isAlwaysFalse();
   }

   @Override
   public boolean isAlwaysTrue() {
      return this.expression != null && this.expression.isAlwaysTrue();
   }

   public Expression getExpression() {
      return this.expression;
   }

   public JoinPredecessorExpression setExpression(final Expression expression) {
      return expression == this.expression ? this : new JoinPredecessorExpression(expression);
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterJoinPredecessorExpression(this)) {
         Expression expr = this.getExpression();
         return visitor.leaveJoinPredecessorExpression(expr == null ? this : this.setExpression((Expression)expr.accept(visitor)));
      } else {
         return this;
      }
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterJoinPredecessorExpression(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      if (this.expression != null) {
         this.expression.toString(sb, printType);
      }
   }
}
