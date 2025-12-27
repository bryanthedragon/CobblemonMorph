package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

abstract class LexicalContextExpression extends Expression implements LexicalContextNode {
   LexicalContextExpression(final LexicalContextExpression expr) {
      super(expr);
   }

   LexicalContextExpression(final long token, final int start, final int finish) {
      super(token, start, finish);
   }

   LexicalContextExpression(final long token, final int finish) {
      super(token, finish);
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
