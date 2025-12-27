package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public class NamedExportsNode extends Node {
   private final List<ExportSpecifierNode> exportSpecifiers;

   public NamedExportsNode(final long token, final int start, final int finish, final List<ExportSpecifierNode> exportSpecifiers) {
      super(token, start, finish);
      this.exportSpecifiers = List.copyOf(exportSpecifiers);
   }

   private NamedExportsNode(final NamedExportsNode node, final List<ExportSpecifierNode> exportSpecifiers) {
      super(node);
      this.exportSpecifiers = List.copyOf(exportSpecifiers);
   }

   public List<ExportSpecifierNode> getExportSpecifiers() {
      return this.exportSpecifiers;
   }

   public NamedExportsNode setExportSpecifiers(List<ExportSpecifierNode> exportSpecifiers) {
      return this.exportSpecifiers == exportSpecifiers ? this : new NamedExportsNode(this, exportSpecifiers);
   }

   @Override
   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      return (Node)(visitor.enterNamedExportsNode(this)
         ? visitor.leaveNamedExportsNode(this.setExportSpecifiers(Node.accept(visitor, this.exportSpecifiers)))
         : this);
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterNamedExportsNode(this);
   }

   @Override
   public void toString(StringBuilder sb, boolean printType) {
      sb.append('{');

      for (int i = 0; i < this.exportSpecifiers.size(); i++) {
         this.exportSpecifiers.get(i).toString(sb, printType);
         if (i < this.exportSpecifiers.size() - 1) {
            sb.append(", ");
         }
      }

      sb.append('}');
   }
}
