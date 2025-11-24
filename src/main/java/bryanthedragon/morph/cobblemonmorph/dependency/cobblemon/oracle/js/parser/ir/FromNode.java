
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LiteralNode;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;

public class FromNode
extends Node {
    private final LiteralNode<TruffleString> moduleSpecifier;

    public FromNode(long token, int start2, int finish, LiteralNode<TruffleString> moduleSpecifier) {
        super(token, start2, finish);
        this.moduleSpecifier = moduleSpecifier;
    }

    private FromNode(FromNode node, LiteralNode<TruffleString> moduleSpecifier) {
        super(node);
        this.moduleSpecifier = moduleSpecifier;
    }

    public LiteralNode<TruffleString> getModuleSpecifier() {
        return this.moduleSpecifier;
    }

    public FromNode setModuleSpecifier(LiteralNode<TruffleString> moduleSpecifier) {
        if (this.moduleSpecifier == moduleSpecifier) {
            return this;
        }
        return new FromNode(this, moduleSpecifier);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterFromNode(this)) {
            return visitor.leaveFromNode(this.setModuleSpecifier((LiteralNode)this.moduleSpecifier.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterFromNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("from");
        sb.append(' ');
        this.moduleSpecifier.toString(sb, printType);
    }
}

