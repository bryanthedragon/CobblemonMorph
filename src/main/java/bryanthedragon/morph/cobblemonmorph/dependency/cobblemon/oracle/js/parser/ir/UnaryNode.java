package com.oracle.js.parser.ir;

import com.oracle.js.parser.Token;
import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class UnaryNode extends Expression implements Assignment<Expression> {
   private final Expression expression;

   public UnaryNode(final long token, final Expression rhs) {
      this(token, Math.min(rhs.getStart(), Token.descPosition(token)), Math.max(Token.descPosition(token) + Token.descLength(token), rhs.getFinish()), rhs);
   }

   public UnaryNode(final long token, final int start, final int finish, final Expression expression) {
      super(token, start, finish);
      this.expression = expression;
   }

   private UnaryNode(final UnaryNode unaryNode, final Expression expression) {
      super(unaryNode);
      this.expression = expression;
   }

   @Override
   public boolean isAssignment() {
      switch (this.tokenType()) {
         case DECPOSTFIX:
         case DECPREFIX:
         case INCPOSTFIX:
         case INCPREFIX:
            return true;
         default:
            return false;
      }
   }

   @Override
   public boolean isSelfModifying() {
      return this.isAssignment();
   }

   @Override
   public Expression getAssignmentDest() {
      return this.isAssignment() ? this.getExpression() : null;
   }

   @Override
   public Expression getAssignmentSource() {
      return this.getAssignmentDest();
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterUnaryNode(this) ? visitor.leaveUnaryNode(this.setExpression((Expression)this.expression.accept(visitor))) : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterUnaryNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      TokenType tokenType = this.tokenType();
      String name = tokenType.getName();
      boolean isPostfix = tokenType == TokenType.DECPOSTFIX || tokenType == TokenType.INCPOSTFIX;
      if (tokenType == TokenType.AWAIT) {
         sb.append("await ");
      } else if (tokenType == TokenType.SPREAD_ARRAY || tokenType == TokenType.SPREAD_OBJECT) {
         sb.append("...");
      }

      boolean rhsParen = tokenType.needsParens(this.getExpression().tokenType(), false);
      if (!isPostfix) {
         if (name == null) {
            sb.append(tokenType.name());
            rhsParen = true;
         } else {
            sb.append(name);
            if (tokenType.ordinal() > TokenType.BIT_NOT.ordinal()) {
               sb.append(' ');
            }
         }
      }

      if (rhsParen) {
         sb.append('(');
      }

      this.getExpression().toString(sb, printType);
      if (rhsParen) {
         sb.append(')');
      }

      if (isPostfix) {
         sb.append(tokenType == TokenType.DECPOSTFIX ? "--" : "++");
      }
   }

   public Expression getExpression() {
      return this.expression;
   }

   public UnaryNode setExpression(final Expression expression) {
      return this.expression == expression ? this : new UnaryNode(this, expression);
   }
}
