package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class ImportSpecifierNode extends Node {
   private final IdentNode identifier;
   private final IdentNode bindingIdentifier;

   public ImportSpecifierNode(final long token, final int start, final int finish, final IdentNode bindingIdentifier, final IdentNode identifier) {
      super(token, start, finish);
      this.identifier = identifier;
      this.bindingIdentifier = bindingIdentifier;
   }

   private ImportSpecifierNode(final ImportSpecifierNode node, final IdentNode bindingIdentifier, final IdentNode identifier) {
      super(node);
      this.identifier = identifier;
      this.bindingIdentifier = bindingIdentifier;
   }

   public IdentNode getIdentifier() {
      return this.identifier;
   }

   public IdentNode getBindingIdentifier() {
      return this.bindingIdentifier;
   }

   public ImportSpecifierNode setIdentifier(IdentNode identifier) {
      return this.identifier == identifier ? this : new ImportSpecifierNode(this, identifier, this.bindingIdentifier);
   }

   public ImportSpecifierNode setBindingIdentifier(IdentNode bindingIdentifier) {
      return this.bindingIdentifier == bindingIdentifier ? this : new ImportSpecifierNode(this, this.identifier, bindingIdentifier);
   }

   @Override
   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterImportSpecifierNode(this)) {
         IdentNode newIdentifier = this.identifier == null ? null : (IdentNode)this.identifier.accept(visitor);
         return visitor.leaveImportSpecifierNode(this.setBindingIdentifier((IdentNode)this.bindingIdentifier.accept(visitor)).setIdentifier(newIdentifier));
      } else {
         return this;
      }
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterImportSpecifierNode(this);
   }

   @Override
   public void toString(StringBuilder sb, boolean printType) {
      if (this.identifier != null) {
         this.identifier.toString(sb, printType);
         sb.append(" as ");
      }

      this.bindingIdentifier.toString(sb, printType);
   }
}
