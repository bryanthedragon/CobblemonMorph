package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;

public class ImportNode extends Node {
   private final LiteralNode<TruffleString> moduleSpecifier;
   private final ImportClauseNode importClause;
   private final FromNode from;

   public ImportNode(final long token, final int start, final int finish, final LiteralNode<TruffleString> moduleSpecifier) {
      this(token, start, finish, moduleSpecifier, null, null);
   }

   public ImportNode(final long token, final int start, final int finish, final ImportClauseNode importClause, final FromNode from) {
      this(token, start, finish, null, importClause, from);
   }

   private ImportNode(
      final long token, final int start, final int finish, final LiteralNode<TruffleString> moduleSpecifier, ImportClauseNode importClause, FromNode from
   ) {
      super(token, start, finish);
      this.moduleSpecifier = moduleSpecifier;
      this.importClause = importClause;
      this.from = from;
   }

   private ImportNode(final ImportNode node, final LiteralNode<TruffleString> moduleSpecifier, ImportClauseNode importClause, FromNode from) {
      super(node);
      this.moduleSpecifier = moduleSpecifier;
      this.importClause = importClause;
      this.from = from;
   }

   public LiteralNode<TruffleString> getModuleSpecifier() {
      return this.moduleSpecifier;
   }

   public ImportClauseNode getImportClause() {
      return this.importClause;
   }

   public FromNode getFrom() {
      return this.from;
   }

   public ImportNode setModuleSpecifier(LiteralNode<TruffleString> moduleSpecifier) {
      return this.moduleSpecifier == moduleSpecifier ? this : new ImportNode(this, moduleSpecifier, this.importClause, this.from);
   }

   public ImportNode setImportClause(ImportClauseNode importClause) {
      return this.importClause == importClause ? this : new ImportNode(this, this.moduleSpecifier, importClause, this.from);
   }

   public ImportNode setFrom(FromNode from) {
      return this.from == from ? this : new ImportNode(this, this.moduleSpecifier, this.importClause, from);
   }

   @Override
   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterImportNode(this)) {
         LiteralNode<TruffleString> newModuleSpecifier = this.moduleSpecifier == null ? null : (LiteralNode)this.moduleSpecifier.accept(visitor);
         ImportClauseNode newImportClause = this.importClause == null ? null : (ImportClauseNode)this.importClause.accept(visitor);
         FromNode newFrom = this.from == null ? null : (FromNode)this.from.accept(visitor);
         return visitor.leaveImportNode(this.setModuleSpecifier(newModuleSpecifier).setImportClause(newImportClause).setFrom(newFrom));
      } else {
         return this;
      }
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterImportNode(this);
   }

   @Override
   public void toString(StringBuilder sb, boolean printType) {
      sb.append("import");
      sb.append(' ');
      if (this.moduleSpecifier != null) {
         this.moduleSpecifier.toString(sb, printType);
      } else {
         this.importClause.toString(sb, printType);
         sb.append(' ');
         this.from.toString(sb, printType);
      }

      sb.append(';');
   }
}
