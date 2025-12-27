package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class IndexNode extends BaseNode {
   private final Expression index;

   public IndexNode(long token, int finish, Expression base, Expression index, boolean isSuper, boolean optional, boolean optionalChain) {
      super(token, finish, base, isSuper, optional, optionalChain);
      this.index = index;
   }

   public IndexNode(final long token, final int finish, final Expression base, final Expression index) {
      this(token, finish, base, index, false, false, false);
   }

   private IndexNode(final IndexNode indexNode, final Expression base, final Expression index, final boolean isSuper) {
      super(indexNode, base, isSuper, indexNode.isOptional(), indexNode.isOptionalChain());
      this.index = index;
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterIndexNode(this)
         ? visitor.leaveIndexNode(this.setBase((Expression)this.base.accept(visitor)).setIndex((Expression)this.index.accept(visitor)))
         : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterIndexNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      boolean needsParen = this.tokenType().needsParens(this.base.tokenType(), true);
      if (needsParen) {
         sb.append('(');
      }

      this.base.toString(sb, printType);
      if (needsParen) {
         sb.append(')');
      }

      if (this.isOptional()) {
         sb.append('?').append('.');
      }

      sb.append('[');
      this.index.toString(sb, printType);
      sb.append(']');
   }

   public Expression getIndex() {
      return this.index;
   }

   private IndexNode setBase(final Expression base) {
      return this.base == base ? this : new IndexNode(this, base, this.index, this.isSuper());
   }

   public IndexNode setIndex(final Expression index) {
      return this.index == index ? this : new IndexNode(this, this.base, index, this.isSuper());
   }

   public IndexNode setIsSuper() {
      return this.isSuper() ? this : new IndexNode(this, this.base, this.index, true);
   }
}
