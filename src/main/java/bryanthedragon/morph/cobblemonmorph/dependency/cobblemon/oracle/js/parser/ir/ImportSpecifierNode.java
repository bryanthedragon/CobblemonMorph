
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class ImportSpecifierNode
extends Node {
    private final IdentNode identifier;
    private final IdentNode bindingIdentifier;

    public ImportSpecifierNode(long token, int start2, int finish, IdentNode bindingIdentifier, IdentNode identifier) {
        super(token, start2, finish);
        this.identifier = identifier;
        this.bindingIdentifier = bindingIdentifier;
    }

    private ImportSpecifierNode(ImportSpecifierNode node, IdentNode bindingIdentifier, IdentNode identifier) {
        super(node);
        this.identifier = identifier;
        this.bindingIdentifier = bindingIdentifier;
    }

    public IdentNode getIdentifier() {
        return this.identifier;
    }

    public IdentNode getBindingIdentifier() {
        return this.bindingIdentifier;
    }

    public ImportSpecifierNode setIdentifier(IdentNode identifier) {
        if (this.identifier == identifier) {
            return this;
        }
        return new ImportSpecifierNode(this, identifier, this.bindingIdentifier);
    }

    public ImportSpecifierNode setBindingIdentifier(IdentNode bindingIdentifier) {
        if (this.bindingIdentifier == bindingIdentifier) {
            return this;
        }
        return new ImportSpecifierNode(this, this.identifier, bindingIdentifier);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterImportSpecifierNode(this)) {
            IdentNode newIdentifier = this.identifier == null ? null : (IdentNode)this.identifier.accept(visitor);
            return visitor.leaveImportSpecifierNode(this.setBindingIdentifier((IdentNode)this.bindingIdentifier.accept(visitor)).setIdentifier(newIdentifier));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterImportSpecifierNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        if (this.identifier != null) {
            this.identifier.toString(sb, printType);
            sb.append(" as ");
        }
        this.bindingIdentifier.toString(sb, printType);
    }
}

