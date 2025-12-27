package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class ReturnNode extends Statement {
   private final Expression expression;
   private boolean inTerminalPosition;

   public ReturnNode(final int lineNumber, final long token, final int finish, final Expression expression) {
      super(lineNumber, token, finish);
      this.expression = expression;
   }

   private ReturnNode(final ReturnNode returnNode, final Expression expression) {
      super(returnNode);
      this.expression = expression;
   }

   @Override
   public boolean isTerminal() {
      return true;
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterReturnNode(this)) {
         return this.expression != null
            ? visitor.leaveReturnNode(this.setExpression((Expression)this.expression.accept(visitor)))
            : visitor.leaveReturnNode(this);
      } else {
         return this;
      }
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterReturnNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      sb.append("return");
      if (this.expression != null) {
         sb.append(' ');
         this.expression.toString(sb, printType);
      }
   }

   public Expression getExpression() {
      return this.expression;
   }

   public ReturnNode setExpression(final Expression expression) {
      return this.expression == expression ? this : new ReturnNode(this, expression);
   }

   public boolean isInTerminalPosition() {
      return this.inTerminalPosition;
   }

   public void setInTerminalPosition(boolean inTerminalPosition) {
      this.inTerminalPosition = inTerminalPosition;
   }

   @Override
   public boolean isCompletionValueNeverEmpty() {
      return true;
   }
}
