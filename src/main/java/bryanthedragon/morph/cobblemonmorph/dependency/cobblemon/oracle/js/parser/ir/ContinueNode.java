package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class ContinueNode extends JumpStatement {
   public ContinueNode(final int lineNumber, final long token, final int finish, final String labelName) {
      super(lineNumber, token, finish, labelName);
   }

   private ContinueNode(final ContinueNode continueNode) {
      super(continueNode);
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterContinueNode(this) ? visitor.leaveContinueNode(this) : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterContinueNode(this);
   }

   @Override
   String getStatementName() {
      return "continue";
   }
}
