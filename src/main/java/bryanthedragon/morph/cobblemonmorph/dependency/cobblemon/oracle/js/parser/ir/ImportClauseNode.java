package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class ImportClauseNode extends Node {
   private final IdentNode defaultBinding;
   private final NameSpaceImportNode nameSpaceImport;
   private final NamedImportsNode namedImports;

   public ImportClauseNode(long token, int start, int finish, final IdentNode defaultBinding) {
      this(token, start, finish, defaultBinding, null, null);
   }

   public ImportClauseNode(long token, int start, int finish, final NameSpaceImportNode nameSpaceImport) {
      this(token, start, finish, null, nameSpaceImport, null);
   }

   public ImportClauseNode(long token, int start, int finish, final NamedImportsNode namedImportsNode) {
      this(token, start, finish, null, null, namedImportsNode);
   }

   public ImportClauseNode(long token, int start, int finish, final IdentNode defaultBinding, final NameSpaceImportNode nameSpaceImport) {
      this(token, start, finish, defaultBinding, nameSpaceImport, null);
   }

   public ImportClauseNode(long token, int start, int finish, final IdentNode defaultBinding, final NamedImportsNode namedImports) {
      this(token, start, finish, defaultBinding, null, namedImports);
   }

   private ImportClauseNode(
      long token, int start, int finish, final IdentNode defaultBinding, final NameSpaceImportNode nameSpaceImport, final NamedImportsNode namedImports
   ) {
      super(token, start, finish);
      this.defaultBinding = defaultBinding;
      this.nameSpaceImport = nameSpaceImport;
      this.namedImports = namedImports;
   }

   private ImportClauseNode(
      final ImportClauseNode node, final IdentNode defaultBinding, final NameSpaceImportNode nameSpaceImport, final NamedImportsNode namedImports
   ) {
      super(node);
      this.defaultBinding = defaultBinding;
      this.nameSpaceImport = nameSpaceImport;
      this.namedImports = namedImports;
   }

   public IdentNode getDefaultBinding() {
      return this.defaultBinding;
   }

   public NameSpaceImportNode getNameSpaceImport() {
      return this.nameSpaceImport;
   }

   public NamedImportsNode getNamedImports() {
      return this.namedImports;
   }

   public ImportClauseNode setDefaultBinding(IdentNode defaultBinding) {
      return this.defaultBinding == defaultBinding ? this : new ImportClauseNode(this, defaultBinding, this.nameSpaceImport, this.namedImports);
   }

   public ImportClauseNode setNameSpaceImport(NameSpaceImportNode nameSpaceImport) {
      return this.nameSpaceImport == nameSpaceImport ? this : new ImportClauseNode(this, this.defaultBinding, nameSpaceImport, this.namedImports);
   }

   public ImportClauseNode setNamedImports(NamedImportsNode namedImports) {
      return this.namedImports == namedImports ? this : new ImportClauseNode(this, this.defaultBinding, this.nameSpaceImport, namedImports);
   }

   @Override
   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterImportClauseNode(this)) {
         IdentNode newDefaultBinding = this.defaultBinding == null ? null : (IdentNode)this.defaultBinding.accept(visitor);
         NameSpaceImportNode newNameSpaceImport = this.nameSpaceImport == null ? null : (NameSpaceImportNode)this.nameSpaceImport.accept(visitor);
         NamedImportsNode newNamedImports = this.namedImports == null ? null : (NamedImportsNode)this.namedImports.accept(visitor);
         return visitor.leaveImportClauseNode(this.setDefaultBinding(newDefaultBinding).setNameSpaceImport(newNameSpaceImport).setNamedImports(newNamedImports));
      } else {
         return this;
      }
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterImportClauseNode(this);
   }

   @Override
   public void toString(StringBuilder sb, boolean printType) {
      if (this.defaultBinding != null) {
         this.defaultBinding.toString(sb, printType);
         if (this.nameSpaceImport != null || this.namedImports != null) {
            sb.append(',');
         }
      }

      if (this.nameSpaceImport != null) {
         this.nameSpaceImport.toString(sb, printType);
      } else if (this.namedImports != null) {
         this.namedImports.toString(sb, printType);
      }
   }
}
