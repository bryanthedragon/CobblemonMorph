package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class ParameterNode extends Expression {
   private final int index;
   private final boolean rest;

   public ParameterNode(final long token, final int finish, final int index, final boolean rest) {
      super(token, finish);
      this.index = index;
      this.rest = rest;
   }

   public ParameterNode(final long token, final int finish, final int index) {
      this(token, finish, index, false);
   }

   @Override
   public Node accept(final NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterParameterNode(this) ? visitor.leaveParameterNode(this) : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterParameterNode(this);
   }

   @Override
   public void toString(final StringBuilder sb, final boolean printType) {
      if (!this.isRestParameter()) {
         sb.append("arguments[").append(this.index).append("]");
      } else {
         sb.append("arguments.slice(").append(this.index).append(")");
      }
   }

   public int getIndex() {
      return this.index;
   }

   public boolean isRestParameter() {
      return this.rest;
   }
}
