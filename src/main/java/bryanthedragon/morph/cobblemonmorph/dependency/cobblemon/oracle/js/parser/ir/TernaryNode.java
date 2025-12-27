package com.oracle.js.parser.ir;

import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class TernaryNode extends Expression {
   private final Expression test;
   private final JoinPredecessorExpression trueExpr;
   private final JoinPredecessorExpression falseExpr;

   public TernaryNode(final long token, final Expression test, final JoinPredecessorExpression trueExpr, final JoinPredecessorExpression falseExpr) {
      super(token, test.getStart(), falseExpr.getFinish());
      this.test = test;
      this.trueExpr = trueExpr;
      this.falseExpr = falseExpr;
   }

   private TernaryNode(
      final TernaryNode ternaryNode, final Expression test, final JoinPredecessorExpression trueExpr, final JoinPredecessorExpression falseExpr
   ) {
      super(ternaryNode);
      this.test = test;
      this.trueExpr = trueExpr;
      this.falseExpr = falseExpr;
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterTernaryNode(this)) {
         Expression newTest = (Expression)this.getTest().accept(visitor);
         JoinPredecessorExpression newTrueExpr = (JoinPredecessorExpression)this.trueExpr.accept(visitor);
         JoinPredecessorExpression newFalseExpr = (JoinPredecessorExpression)this.falseExpr.accept(visitor);
         return visitor.leaveTernaryNode(this.setTest(newTest).setTrueExpression(newTrueExpr).setFalseExpression(newFalseExpr));
      } else {
         return this;
      }
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterTernaryNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      TokenType tokenType = this.tokenType();
      boolean testParen = tokenType.needsParens(this.getTest().tokenType(), true);
      boolean trueParen = tokenType.needsParens(this.getTrueExpression().tokenType(), false);
      boolean falseParen = tokenType.needsParens(this.getFalseExpression().tokenType(), false);
      if (testParen) {
         sb.append('(');
      }

      this.getTest().toString(sb, printType);
      if (testParen) {
         sb.append(')');
      }

      sb.append(" ? ");
      if (trueParen) {
         sb.append('(');
      }

      this.getTrueExpression().toString(sb, printType);
      if (trueParen) {
         sb.append(')');
      }

      sb.append(" : ");
      if (falseParen) {
         sb.append('(');
      }

      this.getFalseExpression().toString(sb, printType);
      if (falseParen) {
         sb.append(')');
      }
   }

   public Expression getTest() {
      return this.test;
   }

   public JoinPredecessorExpression getTrueExpression() {
      return this.trueExpr;
   }

   public JoinPredecessorExpression getFalseExpression() {
      return this.falseExpr;
   }

   public TernaryNode setTest(final Expression test) {
      return this.test == test ? this : new TernaryNode(this, test, this.trueExpr, this.falseExpr);
   }

   public TernaryNode setTrueExpression(final JoinPredecessorExpression trueExpr) {
      return this.trueExpr == trueExpr ? this : new TernaryNode(this, this.test, trueExpr, this.falseExpr);
   }

   public TernaryNode setFalseExpression(final JoinPredecessorExpression falseExpr) {
      return this.falseExpr == falseExpr ? this : new TernaryNode(this, this.test, this.trueExpr, falseExpr);
   }
}
