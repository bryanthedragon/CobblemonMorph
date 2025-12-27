package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class EmptyNode extends Statement {
   public EmptyNode(final int lineNumber, final long token, final int finish) {
      super(lineNumber, token, finish);
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterEmptyNode(this) ? visitor.leaveEmptyNode(this) : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterEmptyNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printTypes) {
      sb.append(';');
   }
}
