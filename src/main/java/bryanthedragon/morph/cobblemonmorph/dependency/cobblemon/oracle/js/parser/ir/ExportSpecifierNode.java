package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class ExportSpecifierNode extends Node {
   private final IdentNode identifier;
   private final IdentNode exportIdentifier;

   public ExportSpecifierNode(final long token, final int start, final int finish, final IdentNode identifier, final IdentNode exportIdentifier) {
      super(token, start, finish);
      this.identifier = identifier;
      this.exportIdentifier = exportIdentifier;
   }

   private ExportSpecifierNode(final ExportSpecifierNode node, final IdentNode identifier, final IdentNode exportIdentifier) {
      super(node);
      this.identifier = identifier;
      this.exportIdentifier = exportIdentifier;
   }

   public IdentNode getIdentifier() {
      return this.identifier;
   }

   public IdentNode getExportIdentifier() {
      return this.exportIdentifier;
   }

   public ExportSpecifierNode setIdentifier(IdentNode identifier) {
      return this.identifier == identifier ? this : new ExportSpecifierNode(this, identifier, this.exportIdentifier);
   }

   public ExportSpecifierNode setExportIdentifier(IdentNode exportIdentifier) {
      return this.exportIdentifier == exportIdentifier ? this : new ExportSpecifierNode(this, this.identifier, exportIdentifier);
   }

   @Override
   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterExportSpecifierNode(this)) {
         IdentNode newExportIdentifier = this.exportIdentifier == null ? null : (IdentNode)this.exportIdentifier.accept(visitor);
         return visitor.leaveExportSpecifierNode(this.setIdentifier((IdentNode)this.identifier.accept(visitor)).setExportIdentifier(newExportIdentifier));
      } else {
         return this;
      }
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterExportSpecifierNode(this);
   }

   @Override
   public void toString(StringBuilder sb, boolean printType) {
      if (this.identifier != null) {
         this.identifier.toString(sb, printType);
         sb.append(" as ");
      }

      this.exportIdentifier.toString(sb, printType);
   }
}
