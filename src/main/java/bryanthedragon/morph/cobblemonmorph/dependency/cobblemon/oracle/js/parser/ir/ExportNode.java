
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.ClassNode;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.FromNode;
import com.oracle.js.parser.ir.FunctionNode;
import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.NamedExportsNode;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.VarNode;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.Map;

public class ExportNode
extends Node {
    private final NamedExportsNode namedExports;
    private final FromNode from;
    private final IdentNode exportIdent;
    private final VarNode var;
    private final Expression expression;
    private final boolean isDefault;
    private final Map<TruffleString, TruffleString> assertions;

    public ExportNode(long token, int start2, int finish, IdentNode ident, FromNode from, Map<TruffleString, TruffleString> assertions) {
        this(token, start2, finish, null, from, ident, null, null, false, assertions);
    }

    public ExportNode(long token, int start2, int finish, NamedExportsNode exportClause, FromNode from, Map<TruffleString, TruffleString> assertions) {
        this(token, start2, finish, exportClause, from, null, null, null, false, assertions);
    }

    public ExportNode(long token, int start2, int finish, IdentNode ident, Expression expression, boolean isDefault) {
        this(token, start2, finish, null, null, ident, null, expression, isDefault, Map.of());
    }

    public ExportNode(long token, int start2, int finish, IdentNode ident, VarNode var) {
        this(token, start2, finish, null, null, ident, var, null, false, Map.of());
    }

    private ExportNode(long token, int start2, int finish, NamedExportsNode namedExports, FromNode from, IdentNode exportIdent, VarNode var, Expression expression, boolean isDefault, Map<TruffleString, TruffleString> assertions) {
        super(token, start2, finish);
        this.namedExports = namedExports;
        this.from = from;
        this.exportIdent = exportIdent;
        this.var = var;
        this.expression = expression;
        this.isDefault = isDefault;
        this.assertions = Map.copyOf(assertions);
        assert (namedExports == null || exportIdent == null);
        assert (!isDefault || namedExports == null && from == null);
        assert (var == null && expression == null || isDefault || exportIdent != null && exportIdent == ExportNode.getIdent(var, expression));
    }

    private ExportNode(ExportNode node, NamedExportsNode namedExports, FromNode from, IdentNode exportIdent, VarNode var, Expression expression, Map<TruffleString, TruffleString> assertions) {
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
        assert (this.exportIdent == null);
        if (this.namedExports == exportClause) {
            return this;
        }
        return new ExportNode(this, exportClause, this.from, this.exportIdent, this.var, this.expression, this.assertions);
    }

    public ExportNode setFrom(FromNode from) {
        assert (this.exportIdent == null);
        if (this.from == from) {
            return this;
        }
        return new ExportNode(this, this.namedExports, from, this.exportIdent, this.var, this.expression, this.assertions);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterExportNode(this)) {
            NamedExportsNode newExportClause = this.namedExports == null ? null : (NamedExportsNode)this.namedExports.accept(visitor);
            FromNode newFrom = this.from == null ? null : (FromNode)this.from.accept(visitor);
            VarNode newVar = this.var == null ? null : (VarNode)this.var.accept(visitor);
            Expression newExpression = this.expression == null ? null : (Expression)this.expression.accept(visitor);
            IdentNode newIdent = this.exportIdent == null || this.isDefault() ? this.exportIdent : ExportNode.getIdent(newVar, newExpression);
            ExportNode newNode = this.namedExports == newExportClause && this.from == newFrom && this.exportIdent == newIdent && this.var == newVar && this.expression == newExpression ? this : new ExportNode(this, this.namedExports, this.from, this.exportIdent, this.var, this.expression, this.assertions);
            return visitor.leaveExportNode(newNode);
        }
        return this;
    }

    private static IdentNode getIdent(VarNode newVar, Expression newExpression) {
        if (newVar != null) {
            return newVar.getName();
        }
        if (newExpression instanceof FunctionNode) {
            return ((FunctionNode)newExpression).getIdent();
        }
        if (newExpression instanceof ClassNode) {
            return ((ClassNode)newExpression).getIdent();
        }
        return null;
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

