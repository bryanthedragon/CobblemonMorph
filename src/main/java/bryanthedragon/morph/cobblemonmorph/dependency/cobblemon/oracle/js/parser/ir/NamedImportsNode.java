
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.ImportSpecifierNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public class NamedImportsNode
extends Node {
    private final List<ImportSpecifierNode> importSpecifiers;

    public NamedImportsNode(long token, int start2, int finish, List<ImportSpecifierNode> importSpecifiers) {
        super(token, start2, finish);
        this.importSpecifiers = List.copyOf(importSpecifiers);
    }

    private NamedImportsNode(NamedImportsNode node, List<ImportSpecifierNode> importSpecifiers) {
        super(node);
        this.importSpecifiers = List.copyOf(importSpecifiers);
    }

    public List<ImportSpecifierNode> getImportSpecifiers() {
        return this.importSpecifiers;
    }

    public NamedImportsNode setImportSpecifiers(List<ImportSpecifierNode> importSpecifiers) {
        if (this.importSpecifiers == importSpecifiers) {
            return this;
        }
        return new NamedImportsNode(this, importSpecifiers);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterNamedImportsNode(this)) {
            return visitor.leaveNamedImportsNode(this.setImportSpecifiers(Node.accept(visitor, this.importSpecifiers)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterNamedImportsNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append('{');
        for (int i = 0; i < this.importSpecifiers.size(); ++i) {
            this.importSpecifiers.get(i).toString(sb, printType);
            if (i >= this.importSpecifiers.size() - 1) continue;
            sb.append(", ");
        }
        sb.append('}');
    }
}

