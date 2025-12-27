package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

abstract class LexicalContextStatement extends Statement implements LexicalContextNode {
   protected LexicalContextStatement(final int lineNumber, final long token, final int finish) {
      super(lineNumber, token, finish);
   }

   protected LexicalContextStatement(final LexicalContextStatement node) {
      super(node);
   }

   @Override
   public final Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return LexicalContextNode.super.accept(visitor);
   }

   @Override
   public final <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return LexicalContextNode.super.accept(visitor);
   }
}
