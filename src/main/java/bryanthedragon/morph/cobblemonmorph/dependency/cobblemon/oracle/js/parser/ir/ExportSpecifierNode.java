
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class ExportSpecifierNode
extends Node {
    private final IdentNode identifier;
    private final IdentNode exportIdentifier;

    public ExportSpecifierNode(long token, int start2, int finish, IdentNode identifier, IdentNode exportIdentifier) {
        super(token, start2, finish);
        this.identifier = identifier;
        this.exportIdentifier = exportIdentifier;
    }

    private ExportSpecifierNode(ExportSpecifierNode node, IdentNode identifier, IdentNode exportIdentifier) {
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
        if (this.identifier == identifier) {
            return this;
        }
        return new ExportSpecifierNode(this, identifier, this.exportIdentifier);
    }

    public ExportSpecifierNode setExportIdentifier(IdentNode exportIdentifier) {
        if (this.exportIdentifier == exportIdentifier) {
            return this;
        }
        return new ExportSpecifierNode(this, this.identifier, exportIdentifier);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterExportSpecifierNode(this)) {
            IdentNode newExportIdentifier = this.exportIdentifier == null ? null : (IdentNode)this.exportIdentifier.accept(visitor);
            return visitor.leaveExportSpecifierNode(this.setIdentifier((IdentNode)this.identifier.accept(visitor)).setExportIdentifier(newExportIdentifier));
        }
        return this;
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

