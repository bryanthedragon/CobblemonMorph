package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class DebuggerNode extends Statement {
   public DebuggerNode(final int lineNumber, final long token, final int finish) {
      super(lineNumber, token, finish);
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterDebuggerNode(this) ? visitor.leaveDebuggerNode(this) : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterDebuggerNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      sb.append("debugger");
   }
}
