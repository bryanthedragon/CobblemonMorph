
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.FromNode;
import com.oracle.js.parser.ir.ImportClauseNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LiteralNode;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;

public class ImportNode
extends Node {
    private final LiteralNode<TruffleString> moduleSpecifier;
    private final ImportClauseNode importClause;
    private final FromNode from;

    public ImportNode(long token, int start2, int finish, LiteralNode<TruffleString> moduleSpecifier) {
        this(token, start2, finish, moduleSpecifier, null, null);
    }

    public ImportNode(long token, int start2, int finish, ImportClauseNode importClause, FromNode from) {
        this(token, start2, finish, null, importClause, from);
    }

    private ImportNode(long token, int start2, int finish, LiteralNode<TruffleString> moduleSpecifier, ImportClauseNode importClause, FromNode from) {
        super(token, start2, finish);
        this.moduleSpecifier = moduleSpecifier;
        this.importClause = importClause;
        this.from = from;
    }

    private ImportNode(ImportNode node, LiteralNode<TruffleString> moduleSpecifier, ImportClauseNode importClause, FromNode from) {
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
        if (this.moduleSpecifier == moduleSpecifier) {
            return this;
        }
        return new ImportNode(this, moduleSpecifier, this.importClause, this.from);
    }

    public ImportNode setImportClause(ImportClauseNode importClause) {
        if (this.importClause == importClause) {
            return this;
        }
        return new ImportNode(this, this.moduleSpecifier, importClause, this.from);
    }

    public ImportNode setFrom(FromNode from) {
        if (this.from == from) {
            return this;
        }
        return new ImportNode(this, this.moduleSpecifier, this.importClause, from);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterImportNode(this)) {
            LiteralNode newModuleSpecifier = this.moduleSpecifier == null ? null : (LiteralNode)this.moduleSpecifier.accept(visitor);
            ImportClauseNode newImportClause = this.importClause == null ? null : (ImportClauseNode)this.importClause.accept(visitor);
            FromNode newFrom = this.from == null ? null : (FromNode)this.from.accept(visitor);
            return visitor.leaveImportNode(this.setModuleSpecifier(newModuleSpecifier).setImportClause(newImportClause).setFrom(newFrom));
        }
        return this;
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

