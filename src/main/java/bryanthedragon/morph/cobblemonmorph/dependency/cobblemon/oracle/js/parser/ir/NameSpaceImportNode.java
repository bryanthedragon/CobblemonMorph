package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class NameSpaceImportNode extends Node {
   private final IdentNode bindingIdentifier;

   public NameSpaceImportNode(final long token, final int start, final int finish, final IdentNode bindingIdentifier) {
      super(token, start, finish);
      this.bindingIdentifier = bindingIdentifier;
   }

   private NameSpaceImportNode(final NameSpaceImportNode node, final IdentNode bindingIdentifier) {
      super(node);
      this.bindingIdentifier = bindingIdentifier;
   }

   public IdentNode getBindingIdentifier() {
      return this.bindingIdentifier;
   }

   public NameSpaceImportNode setBindingIdentifier(IdentNode bindingIdentifier) {
      return this.bindingIdentifier == bindingIdentifier ? this : new NameSpaceImportNode(this, bindingIdentifier);
   }

   @Override
   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterNameSpaceImportNode(this)
         ? visitor.leaveNameSpaceImportNode(this.setBindingIdentifier((IdentNode)this.bindingIdentifier.accept(visitor)))
         : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterNameSpaceImportNode(this);
   }

   @Override
   public void toString(StringBuilder sb, boolean printType) {
      sb.append("* as ");
      this.bindingIdentifier.toString(sb, printType);
   }
}
