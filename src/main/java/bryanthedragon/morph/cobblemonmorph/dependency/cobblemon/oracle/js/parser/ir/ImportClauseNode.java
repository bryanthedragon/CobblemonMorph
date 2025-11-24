
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.NameSpaceImportNode;
import com.oracle.js.parser.ir.NamedImportsNode;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class ImportClauseNode
extends Node {
    private final IdentNode defaultBinding;
    private final NameSpaceImportNode nameSpaceImport;
    private final NamedImportsNode namedImports;

    public ImportClauseNode(long token, int start2, int finish, IdentNode defaultBinding) {
        this(token, start2, finish, defaultBinding, null, null);
    }

    public ImportClauseNode(long token, int start2, int finish, NameSpaceImportNode nameSpaceImport) {
        this(token, start2, finish, null, nameSpaceImport, null);
    }

    public ImportClauseNode(long token, int start2, int finish, NamedImportsNode namedImportsNode) {
        this(token, start2, finish, null, null, namedImportsNode);
    }

    public ImportClauseNode(long token, int start2, int finish, IdentNode defaultBinding, NameSpaceImportNode nameSpaceImport) {
        this(token, start2, finish, defaultBinding, nameSpaceImport, null);
    }

    public ImportClauseNode(long token, int start2, int finish, IdentNode defaultBinding, NamedImportsNode namedImports) {
        this(token, start2, finish, defaultBinding, null, namedImports);
    }

    private ImportClauseNode(long token, int start2, int finish, IdentNode defaultBinding, NameSpaceImportNode nameSpaceImport, NamedImportsNode namedImports) {
        super(token, start2, finish);
        this.defaultBinding = defaultBinding;
        this.nameSpaceImport = nameSpaceImport;
        this.namedImports = namedImports;
    }

    private ImportClauseNode(ImportClauseNode node, IdentNode defaultBinding, NameSpaceImportNode nameSpaceImport, NamedImportsNode namedImports) {
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
        if (this.defaultBinding == defaultBinding) {
            return this;
        }
        return new ImportClauseNode(this, defaultBinding, this.nameSpaceImport, this.namedImports);
    }

    public ImportClauseNode setNameSpaceImport(NameSpaceImportNode nameSpaceImport) {
        if (this.nameSpaceImport == nameSpaceImport) {
            return this;
        }
        return new ImportClauseNode(this, this.defaultBinding, nameSpaceImport, this.namedImports);
    }

    public ImportClauseNode setNamedImports(NamedImportsNode namedImports) {
        if (this.namedImports == namedImports) {
            return this;
        }
        return new ImportClauseNode(this, this.defaultBinding, this.nameSpaceImport, namedImports);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterImportClauseNode(this)) {
            IdentNode newDefaultBinding = this.defaultBinding == null ? null : (IdentNode)this.defaultBinding.accept(visitor);
            NameSpaceImportNode newNameSpaceImport = this.nameSpaceImport == null ? null : (NameSpaceImportNode)this.nameSpaceImport.accept(visitor);
            NamedImportsNode newNamedImports = this.namedImports == null ? null : (NamedImportsNode)this.namedImports.accept(visitor);
            return visitor.leaveImportClauseNode(this.setDefaultBinding(newDefaultBinding).setNameSpaceImport(newNameSpaceImport).setNamedImports(newNamedImports));
        }
        return this;
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

