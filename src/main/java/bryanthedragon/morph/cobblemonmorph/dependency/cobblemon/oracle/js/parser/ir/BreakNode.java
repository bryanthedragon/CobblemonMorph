package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class BreakNode extends JumpStatement {
   public BreakNode(final int lineNumber, final long token, final int finish, final String labelName) {
      super(lineNumber, token, finish, labelName);
   }

   private BreakNode(final BreakNode breakNode) {
      super(breakNode);
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterBreakNode(this) ? visitor.leaveBreakNode(this) : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterBreakNode(this);
   }

   @Override
   String getStatementName() {
      return "break";
   }
}
