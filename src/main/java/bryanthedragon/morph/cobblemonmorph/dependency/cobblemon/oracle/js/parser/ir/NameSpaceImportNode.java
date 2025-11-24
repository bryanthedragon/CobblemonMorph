
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class NameSpaceImportNode
extends Node {
    private final IdentNode bindingIdentifier;

    public NameSpaceImportNode(long token, int start2, int finish, IdentNode bindingIdentifier) {
        super(token, start2, finish);
        this.bindingIdentifier = bindingIdentifier;
    }

    private NameSpaceImportNode(NameSpaceImportNode node, IdentNode bindingIdentifier) {
        super(node);
        this.bindingIdentifier = bindingIdentifier;
    }

    public IdentNode getBindingIdentifier() {
        return this.bindingIdentifier;
    }

    public NameSpaceImportNode setBindingIdentifier(IdentNode bindingIdentifier) {
        if (this.bindingIdentifier == bindingIdentifier) {
            return this;
        }
        return new NameSpaceImportNode(this, bindingIdentifier);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterNameSpaceImportNode(this)) {
            return visitor.leaveNameSpaceImportNode(this.setBindingIdentifier((IdentNode)this.bindingIdentifier.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterNameSpaceImportNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("* as ");
        this.bindingIdentifier.toString(sb, printType);
    }
}

