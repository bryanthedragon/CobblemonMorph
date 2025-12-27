package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public interface LexicalContextNode {
   Node accept(final LexicalContext lc, final NodeVisitor<? extends LexicalContext> visitor);

   <R> R accept(final LexicalContext lc, final TranslatorNodeVisitor<? extends LexicalContext, R> visitor);

   default Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      LexicalContext lc = visitor.getLexicalContext();
      lc.push(this);

      Node var3;
      try {
         var3 = this.accept(lc, visitor);
      } finally {
         lc.pop(this);
      }

      return var3;
   }

   default <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      LexicalContext lc = visitor.getLexicalContext();
      lc.push(this);

      Object var3;
      try {
         var3 = this.accept(lc, visitor);
      } finally {
         lc.pop(this);
      }

      return (R)var3;
   }
}
