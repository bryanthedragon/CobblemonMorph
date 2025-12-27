package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Map;

public class ExportNode extends Node {
   private final NamedExportsNode namedExports;
   private final FromNode from;
   private final IdentNode exportIdent;
   private final VarNode var;
   private final Expression expression;
   private final boolean isDefault;
   private final Map<TruffleString, TruffleString> assertions;

   public ExportNode(
      final long token, final int start, final int finish, final IdentNode ident, final FromNode from, Map<TruffleString, TruffleString> assertions
   ) {
      this(token, start, finish, null, from, ident, null, null, false, assertions);
   }

   public ExportNode(
      final long token,
      final int start,
      final int finish,
      final NamedExportsNode exportClause,
      final FromNode from,
      Map<TruffleString, TruffleString> assertions
   ) {
      this(token, start, finish, exportClause, from, null, null, null, false, assertions);
   }

   public ExportNode(final long token, final int start, final int finish, final IdentNode ident, final Expression expression, final boolean isDefault) {
      this(token, start, finish, null, null, ident, null, expression, isDefault, Map.of());
   }

   public ExportNode(final long token, final int start, final int finish, final IdentNode ident, final VarNode var) {
      this(token, start, finish, null, null, ident, var, null, false, Map.of());
   }

   private ExportNode(
      final long token,
      final int start,
      final int finish,
      final NamedExportsNode namedExports,
      final FromNode from,
      final IdentNode exportIdent,
      final VarNode var,
      final Expression expression,
      final boolean isDefault,
      Map<TruffleString, TruffleString> assertions
   ) {
      super(token, start, finish);
      this.namedExports = namedExports;
      this.from = from;
      this.exportIdent = exportIdent;
      this.var = var;
      this.expression = expression;
      this.isDefault = isDefault;
      this.assertions = Map.copyOf(assertions);

      assert namedExports == null || exportIdent == null;

      assert !isDefault || namedExports == null && from == null;

      assert var == null && expression == null || isDefault || exportIdent != null && exportIdent == getIdent(var, expression);
   }

   private ExportNode(
      final ExportNode node,
      final NamedExportsNode namedExports,
      final FromNode from,
      final IdentNode exportIdent,
      final VarNode var,
      final Expression expression,
      Map<TruffleString, TruffleString> assertions
   ) {
      super(node);
      this.isDefault = node.isDefault;
      this.namedExports = namedExports;
      this.from = from;
      this.exportIdent = exportIdent;
      this.var = var;
      this.expression = expression;
      this.assertions = Map.copyOf(assertions);
   }

   public NamedExportsNode getNamedExports() {
      return this.namedExports;
   }

   public FromNode getFrom() {
      return this.from;
   }

   public IdentNode getExportIdentifier() {
      return this.exportIdent;
   }

   public VarNode getVar() {
      return this.var;
   }

   public Expression getExpression() {
      return this.expression;
   }

   public boolean isDefault() {
      return this.isDefault;
   }

   public Map<TruffleString, TruffleString> getAssertions() {
      return this.assertions;
   }

   public ExportNode setExportClause(NamedExportsNode exportClause) {
      assert this.exportIdent == null;

      return this.namedExports == exportClause
         ? this
         : new ExportNode(this, exportClause, this.from, this.exportIdent, this.var, this.expression, this.assertions);
   }

   public ExportNode setFrom(FromNode from) {
      assert this.exportIdent == null;

      return this.from == from ? this : new ExportNode(this, this.namedExports, from, this.exportIdent, this.var, this.expression, this.assertions);
   }

   @Override
   public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
      if (visitor.enterExportNode(this)) {
         NamedExportsNode newExportClause = this.namedExports == null ? null : (NamedExportsNode)this.namedExports.accept(visitor);
         FromNode newFrom = this.from == null ? null : (FromNode)this.from.accept(visitor);
         VarNode newVar = this.var == null ? null : (VarNode)this.var.accept(visitor);
         Expression newExpression = this.expression == null ? null : (Expression)this.expression.accept(visitor);
         IdentNode newIdent = this.exportIdent != null && !this.isDefault() ? getIdent(newVar, newExpression) : this.exportIdent;
         ExportNode newNode = this.namedExports == newExportClause
               && this.from == newFrom
               && this.exportIdent == newIdent
               && this.var == newVar
               && this.expression == newExpression
            ? this
            : new ExportNode(this, this.namedExports, this.from, this.exportIdent, this.var, this.expression, this.assertions);
         return visitor.leaveExportNode(newNode);
      } else {
         return this;
      }
   }

   private static IdentNode getIdent(VarNode newVar, Expression newExpression) {
      if (newVar != null) {
         return newVar.getName();
      } else if (newExpression instanceof FunctionNode) {
         return ((FunctionNode)newExpression).getIdent();
      } else {
         return newExpression instanceof ClassNode ? ((ClassNode)newExpression).getIdent() : null;
      }
   }

   @Override
   public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
      return visitor.enterExportNode(this);
   }

   @Override
   public void toString(StringBuilder sb, boolean printType) {
      sb.append("export ");
      if (this.isDefault) {
         sb.append("default ");
      }

      if (this.expression != null) {
         this.expression.toString(sb, printType);
         if (this.expression.isAssignment()) {
            sb.append(';');
         }
      } else if (this.var != null) {
         this.var.toString(sb, printType);
         sb.append(';');
      } else {
         if (this.namedExports == null) {
            sb.append("* ");
            if (this.exportIdent != null) {
               sb.append("as ").append(this.exportIdent).append(' ');
            }
         } else {
            this.namedExports.toString(sb, printType);
         }

         if (this.from != null) {
            this.from.toString(sb, printType);
         }

         sb.append(';');
      }
   }
}
