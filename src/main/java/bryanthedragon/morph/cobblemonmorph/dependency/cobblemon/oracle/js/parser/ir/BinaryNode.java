package com.oracle.js.parser.ir;

import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class BinaryNode extends Expression implements Assignment<Expression> {
   private final Expression lhs;
   private final Expression rhs;

   public BinaryNode(final long token, final Expression lhs, final Expression rhs) {
      super(token, Math.min(lhs.getStart(), rhs.getStart()), Math.max(rhs.getFinish(), lhs.getFinish()));

      assert !this.isLogical() || lhs instanceof JoinPredecessorExpression;

      this.lhs = lhs;
      this.rhs = rhs;
   }

   private BinaryNode(final BinaryNode binaryNode, final Expression lhs, final Expression rhs) {
      super(binaryNode);
      this.lhs = lhs;
      this.rhs = rhs;
   }

   public boolean isComparison() {
      switch (this.tokenType()) {
         case EQ:
         case EQ_STRICT:
         case NE:
         case NE_STRICT:
         case LE:
         case LT:
         case GE:
         case GT:
            return true;
         default:
            return false;
      }
   }

   public boolean isRelational() {
      switch (this.tokenType()) {
         case LE:
         case LT:
         case GE:
         case GT:
            return true;
         default:
            return false;
      }
   }

   public boolean isLogical() {
      return isLogical(this.tokenType());
   }

   public static boolean isLogical(final TokenType tokenType) {
      switch (tokenType) {
         case AND:
         case OR:
         case NULLISHCOALESC:
            return true;
         default:
            return false;
      }
   }

   @Override
   public boolean isAssignment() {
      return this.tokenType().isAssignment();
   }

   @Override
   public boolean isSelfModifying() {
      return this.isAssignment() && !this.isTokenType(TokenType.ASSIGN);
   }

   @Override
   public Expression getAssignmentDest() {
      return this.isAssignment() ? this.getLhs() : null;
   }

   @Override
   public Expression getAssignmentSource() {
      return this.getRhs();
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterBinaryNode(this)
         ? visitor.leaveBinaryNode(this.setLHS((Expression)this.lhs.accept(visitor)).setRHS((Expression)this.rhs.accept(visitor)))
         : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterBinaryNode(this);
   }

   @Override
   public boolean isAlwaysFalse() {
      switch (this.tokenType()) {
         case COMMALEFT:
            return this.lhs.isAlwaysFalse();
         case COMMARIGHT:
            return this.rhs.isAlwaysFalse();
         default:
            return false;
      }
   }

   @Override
   public boolean isAlwaysTrue() {
      switch (this.tokenType()) {
         case COMMALEFT:
            return this.lhs.isAlwaysTrue();
         case COMMARIGHT:
            return this.rhs.isAlwaysTrue();
         default:
            return false;
      }
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      TokenType tokenType = this.tokenType();
      boolean lhsParen = tokenType.needsParens(this.getLhs().tokenType(), true);
      boolean rhsParen = tokenType.needsParens(this.getRhs().tokenType(), false);
      if (lhsParen) {
         sb.append('(');
      }

      this.getLhs().toString(sb, printType);
      if (lhsParen) {
         sb.append(')');
      }

      sb.append(' ');
      switch (tokenType) {
         case COMMALEFT:
            sb.append(",<");
            break;
         case COMMARIGHT:
            sb.append(",>");
            break;
         case INCPREFIX:
         case DECPREFIX:
            sb.append("++");
            break;
         case ASSIGN_INIT:
            sb.append(":=");
            break;
         default:
            sb.append(tokenType.getName());
      }

      sb.append(' ');
      if (rhsParen) {
         sb.append('(');
      }

      this.getRhs().toString(sb, printType);
      if (rhsParen) {
         sb.append(')');
      }
   }

   public Expression getLhs() {
      return this.lhs;
   }

   public Expression getRhs() {
      return this.rhs;
   }

   public BinaryNode setLHS(final Expression lhs) {
      return this.lhs == lhs ? this : new BinaryNode(this, lhs, this.rhs);
   }

   public BinaryNode setRHS(final Expression rhs) {
      return this.rhs == rhs ? this : new BinaryNode(this, this.lhs, rhs);
   }
}
