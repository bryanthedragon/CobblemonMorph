package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public class NamedImportsNode extends Node {
   private final List<ImportSpecifierNode> importSpecifiers;

   public NamedImportsNode(final long token, final int start, final int finish, final List<ImportSpecifierNode> importSpecifiers) {
      super(token, start, finish);
      this.importSpecifiers = List.copyOf(importSpecifiers);
   }

   private NamedImportsNode(final NamedImportsNode node, final List<ImportSpecifierNode> importSpecifiers) {
      super(node);
      this.importSpecifiers = List.copyOf(importSpecifiers);
   }

   public List<ImportSpecifierNode> getImportSpecifiers() {
      return this.importSpecifiers;
   }

   public NamedImportsNode setImportSpecifiers(List<ImportSpecifierNode> importSpecifiers) {
      return this.importSpecifiers == importSpecifiers ? this : new NamedImportsNode(this, importSpecifiers);
   }

   @Override
   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterNamedImportsNode(this)
         ? visitor.leaveNamedImportsNode(this.setImportSpecifiers(Node.accept(visitor, this.importSpecifiers)))
         : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterNamedImportsNode(this);
   }

   @Override
   public void toString(StringBuilder sb, boolean printType) {
      sb.append('{');

      for (int i = 0; i < this.importSpecifiers.size(); i++) {
         this.importSpecifiers.get(i).toString(sb, printType);
         if (i < this.importSpecifiers.size() - 1) {
            sb.append(", ");
         }
      }

      sb.append('}');
   }
}
